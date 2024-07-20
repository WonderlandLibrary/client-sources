/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.Plugin
 */
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
import org.bukkit.plugin.Plugin;

public final class ArmorToggleListener
extends ViaBukkitListener {
    public ArmorToggleListener(ViaVersionPlugin plugin) {
        super((Plugin)plugin, Protocol1_19_4To1_19_3.class);
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void itemUse(PlayerInteractEvent event) {
        PlayerInventory inventory;
        ItemStack armor;
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null || event.getHand() == null) {
            return;
        }
        EquipmentSlot armorItemSlot = item.getType().getEquipmentSlot();
        if (armorItemSlot == EquipmentSlot.HAND || armorItemSlot == EquipmentSlot.OFF_HAND || item.getType().isBlock()) {
            return;
        }
        if (this.isOnPipe(player) && (armor = (inventory = player.getInventory()).getItem(armorItemSlot)) != null && armor.getType() != Material.AIR && !armor.equals((Object)item)) {
            inventory.setItem(event.getHand(), inventory.getItem(event.getHand()));
            inventory.setItem(armorItemSlot, armor);
        }
    }
}

