/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.Lectern
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.BookMeta
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 */
package com.viaversion.viabackwards.listener;

import com.viaversion.viabackwards.BukkitPlugin;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Lectern;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class LecternInteractListener
extends ViaBukkitListener {
    public LecternInteractListener(BukkitPlugin bukkitPlugin) {
        super((Plugin)bukkitPlugin, Protocol1_13_2To1_14.class);
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onLecternInteract(PlayerInteractEvent playerInteractEvent) {
        Block block = playerInteractEvent.getClickedBlock();
        if (block == null || block.getType() != Material.LECTERN) {
            return;
        }
        Player player = playerInteractEvent.getPlayer();
        if (!this.isOnPipe(player)) {
            return;
        }
        Lectern lectern = (Lectern)block.getState();
        ItemStack itemStack = lectern.getInventory().getItem(0);
        if (itemStack == null) {
            return;
        }
        BookMeta bookMeta = (BookMeta)itemStack.getItemMeta();
        ItemStack itemStack2 = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta2 = (BookMeta)itemStack2.getItemMeta();
        bookMeta2.setPages(bookMeta.getPages());
        bookMeta2.setAuthor("an upsidedown person");
        bookMeta2.setTitle("buk");
        itemStack2.setItemMeta((ItemMeta)bookMeta2);
        player.openBook(itemStack2);
        playerInteractEvent.setCancelled(false);
    }
}

