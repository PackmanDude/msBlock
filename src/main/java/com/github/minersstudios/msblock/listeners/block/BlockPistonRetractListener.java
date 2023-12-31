package com.github.minersstudios.msblock.listeners.block;

import com.github.minersstudios.mscore.listener.MSListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.jetbrains.annotations.NotNull;

@MSListener
public class BlockPistonRetractListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockPistonRetract(@NotNull BlockPistonRetractEvent event) {
		for (Block block : event.getBlocks()) {
			if (block.getType() == Material.NOTE_BLOCK) {
				event.setCancelled(true);
			}
		}
	}
}
