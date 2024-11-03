package com.viaversion.viabackwards.listener;

import com.viaversion.viabackwards.BukkitPlugin;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FireExtinguishListener extends ViaBukkitListener {
   public FireExtinguishListener(BukkitPlugin plugin) {
      super(plugin, Protocol1_15_2To1_16.class);
   }

   @EventHandler(
      priority = EventPriority.HIGHEST,
      ignoreCancelled = true
   )
   public void onFireExtinguish(PlayerInteractEvent event) {
      if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
         Block block = event.getClickedBlock();
         if (block != null) {
            Player player = event.getPlayer();
            if (this.isOnPipe(player)) {
               Block relative = block.getRelative(event.getBlockFace());
               if (relative.getType() == Material.FIRE) {
                  event.setCancelled(true);
                  relative.setType(Material.AIR);
               }
            }
         }
      }
   }
}
