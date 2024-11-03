package com.viaversion.viaversion.bukkit.listeners.protocol1_19_4To1_19_3;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.Protocol1_19_4To1_19_3;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public final class ArmorToggleListener extends ViaBukkitListener {
   public ArmorToggleListener(ViaVersionPlugin plugin) {
      super(plugin, Protocol1_19_4To1_19_3.class);
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void itemUse(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      ItemStack item = event.getItem();
      if (item != null && event.getHand() != null) {
         EquipmentSlot armorItemSlot = item.getType().getEquipmentSlot();
         if (armorItemSlot != EquipmentSlot.HAND && armorItemSlot != EquipmentSlot.OFF_HAND && !item.getType().isBlock()) {
            if (this.isOnPipe(player)) {
               PlayerInventory inventory = player.getInventory();
               ItemStack armor = inventory.getItem(armorItemSlot);
               if (armor != null && armor.getType() != Material.AIR && !armor.equals(item)) {
                  inventory.setItem(event.getHand(), inventory.getItem(event.getHand()));
                  inventory.setItem(armorItemSlot, armor);
               }
            }
         }
      }
   }
}
