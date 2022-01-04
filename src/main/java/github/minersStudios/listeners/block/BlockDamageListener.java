package github.minersStudios.listeners.block;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlockDamageListener implements Listener {

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        if (event.getBlock().getType() == Material.NOTE_BLOCK) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1, true, false, false));
            return;
        }
        if (event.getPlayer().hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
            event.setCancelled(true);
            event.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING);
        }
    }

}