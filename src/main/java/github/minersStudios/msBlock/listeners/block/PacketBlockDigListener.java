package github.minersStudios.msBlock.listeners.block;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import github.minersStudios.msBlock.Main;
import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.AbstractMap;

import static github.minersStudios.msBlock.Main.protocolManager;
import static github.minersStudios.msBlock.utils.BlockUtils.*;
import static github.minersStudios.msBlock.utils.PlayerUtils.*;

public class PacketBlockDigListener extends PacketAdapter {

	public PacketBlockDigListener() {
		super(Main.plugin, PacketType.Play.Client.BLOCK_DIG);
	}

	@Override
	public void onPacketReceiving(@Nonnull PacketEvent event) {
		Player player = event.getPlayer();
		if (player == null || !player.isOnline() || player.getGameMode() != GameMode.SURVIVAL) return;
		PacketContainer packet = event.getPacket();
		EnumWrappers.PlayerDigType digType = packet.getPlayerDigTypes().read(0);
		BlockPosition blockPosition = packet.getBlockPositionModifier().read(0);
		Block block = blockPosition.toLocation(player.getWorld()).getBlock();
		boolean hasSlowDigging = player.hasPotionEffect(PotionEffectType.SLOW_DIGGING);

		Bukkit.getScheduler().runTask(this.plugin, () -> {
			if (digType == EnumWrappers.PlayerDigType.START_DESTROY_BLOCK) {
				if (block.getType() != Material.NOTE_BLOCK) {
					if (hasPlayer(player) && !isWoodenSound(block.getType()))
						cancelAllTasksWithThisPlayer(player);
					if (hasSlowDigging)
						player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
				} else if (block.getBlockData() instanceof NoteBlock noteBlock) {
					if (hasPlayer(player))
						cancelAllTasksWithThisPlayer(player);
					CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered());
					float digSpeed = CustomBlockMaterial.getDigSpeed(player, customBlockMaterial);
					blocks.put(new AbstractMap.SimpleEntry<>(block, player), Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
						float ticks, progress = 0.0f;
						int currentStage = 0;
						static boolean swing = true;

						@Override
						public void run() {
							Block targetBlock = getTargetBlock(player);
							if (getTargetEntity(player, targetBlock) != null || targetBlock == null) {
								farAway.add(player);
								return;
							} else {
								farAway.remove(player);
							}

							if (!hasSlowDigging)
								player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1, true, false, false));

							if (!targetBlock.equals(block)) return;

							if (!farAway.contains(player)) {
								protocolManager.addPacketListener(new PacketAdapter(plugin, PacketType.Play.Client.ARM_ANIMATION) {
									@Override
									public void onPacketReceiving(PacketEvent event) {
										swing = true;
									}
								});
							}

							if (!swing) {
								playZeroBreakStage(blockPosition);
								cancelAllTasksWithThisPlayer(player);
							}

							this.ticks++;
							this.progress += digSpeed;

							if (this.ticks % 4.0f == 0.0f && !farAway.contains(player)) {
								customBlockMaterial.playHitSound(block);
								swing = false;
							}

							if (this.progress > this.currentStage++ * 0.1f) {
								this.currentStage = (int) Math.floor(this.progress * 10.0f);
								if (this.currentStage <= 9) {
									PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
									packetContainer.getIntegers().write(0, 0).write(1, this.currentStage - 1);
									packetContainer.getBlockPositionModifier().write(0, blockPosition);
									protocolManager.broadcastServerPacket(packetContainer);
								}
							}

							if (this.progress > 1.0f) {
								playZeroBreakStage(blockPosition);
								customBlockMaterial.breakCustomBlock(block, player);
							}
						}
					}, 0L, 1L));
				}
				if (isWoodenSound(block.getType())) {
					if (hasPlayer(player))
						cancelAllTasksWithThisPlayer(player);
					blocks.put(new AbstractMap.SimpleEntry<>(block, player), Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
						static boolean swing = true;
						float ticks = 0.0f;

						@Override
						public void run() {
							Block targetBlock = getTargetBlock(player);
							if (getTargetEntity(player, targetBlock) != null || targetBlock == null) {
								farAway.add(player);
								return;
							} else {
								farAway.remove(player);
							}

							if (!targetBlock.equals(block)) return;

							if (!farAway.contains(player)) {
								protocolManager.addPacketListener(new PacketAdapter(plugin, PacketType.Play.Client.ARM_ANIMATION) {
									@Override
									public void onPacketReceiving(PacketEvent event) {
										swing = true;
									}
								});
							}

							if (!swing) {
								playZeroBreakStage(blockPosition);
								cancelAllTasksWithThisPlayer(player);
							}

							this.ticks++;

							if (this.ticks % 4.0f == 0.0f) {
								block.getWorld().playSound(block.getLocation().clone().add(0.5d, 0.5d, 0.5d), "custom.block.wood.hit", 0.5f, 0.5f);
								swing = false;
							}
						}
					}, 0L, 1L));
				}
			} else if (digType == EnumWrappers.PlayerDigType.STOP_DESTROY_BLOCK && getEntryByBlock(block) != null) {
				playZeroBreakStage(blockPosition);
				cancelAllTasksWithThisBlock(block);
			} else if (
					digType == EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK
					&& getEntryByBlock(block) != null
					&& !farAway.contains(player)
			) {
				Block targetBlock = getTargetBlock(player);
				if (getTargetEntity(player, targetBlock) == null && targetBlock != null) {
					playZeroBreakStage(blockPosition);
					cancelAllTasksWithThisPlayer(player);
				}
			}
		});
	}

	private static void playZeroBreakStage(@Nonnull BlockPosition blockPosition) {
		PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
		packetContainer.getIntegers().write(0, 0).write(1, -1);
		packetContainer.getBlockPositionModifier().write(0, blockPosition);
		protocolManager.broadcastServerPacket(packetContainer);
	}
}