package github.minersStudios.msBlock.listeners.player;

import github.minersStudios.msBlock.Main;
import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import github.minersStudios.msBlock.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;

import javax.annotation.Nonnull;

public class InventoryCreativeListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onInventoryCreative(@Nonnull InventoryCreativeEvent event) {
		if (!event.getClick().isCreativeAction()) return;
		Player player = (Player) event.getWhoClicked();
		Block targetBlock = PlayerUtils.getTargetBlock(player);
		if (
				targetBlock == null
				|| event.getCursor().getType() != Material.NOTE_BLOCK
				|| !(targetBlock.getBlockData() instanceof NoteBlock noteBlock)
		) return;
		event.setCancelled(true);
		Bukkit.getScheduler().runTask(Main.plugin, () -> player.getInventory().setItem(
				event.getSlot(),
				CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered()).getItemStack()
		));
	}
}