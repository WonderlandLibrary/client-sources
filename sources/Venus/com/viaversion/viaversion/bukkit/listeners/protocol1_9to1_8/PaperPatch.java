/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.plugin.Plugin
 */
package com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8;

import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

public class PaperPatch
extends ViaBukkitListener {
    public PaperPatch(Plugin plugin) {
        super(plugin, Protocol1_9To1_8.class);
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    public void onPlace(BlockPlaceEvent blockPlaceEvent) {
        if (this.isOnPipe(blockPlaceEvent.getPlayer())) {
            Material material = blockPlaceEvent.getBlockPlaced().getType();
            if (this.isPlacable(material)) {
                return;
            }
            Location location = blockPlaceEvent.getPlayer().getLocation();
            Block block = location.getBlock();
            if (block.equals(blockPlaceEvent.getBlock())) {
                blockPlaceEvent.setCancelled(false);
            } else if (block.getRelative(BlockFace.UP).equals(blockPlaceEvent.getBlock())) {
                blockPlaceEvent.setCancelled(false);
            } else {
                Location location2 = location.clone().subtract(blockPlaceEvent.getBlock().getLocation().add(0.5, 0.0, 0.5));
                if (Math.abs(location2.getX()) <= 0.8 && Math.abs(location2.getZ()) <= 0.8) {
                    if (location2.getY() <= 0.1 && location2.getY() >= -0.1) {
                        blockPlaceEvent.setCancelled(false);
                        return;
                    }
                    BlockFace blockFace = blockPlaceEvent.getBlockAgainst().getFace(blockPlaceEvent.getBlock());
                    if (blockFace == BlockFace.UP && location2.getY() < 1.0 && location2.getY() >= 0.0) {
                        blockPlaceEvent.setCancelled(false);
                    }
                }
            }
        }
    }

    private boolean isPlacable(Material material) {
        if (!material.isSolid()) {
            return false;
        }
        switch (material.getId()) {
            case 63: 
            case 68: 
            case 176: 
            case 177: {
                return false;
            }
        }
        return true;
    }
}

