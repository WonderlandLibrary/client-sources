/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.plugin.Plugin
 */
package com.viaversion.viaversion.bukkit.listeners.protocol1_19to1_18_2;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.Protocol1_19To1_18_2;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

public final class BlockBreakListener
extends ViaBukkitListener {
    private static final Class<?> CRAFT_BLOCK_STATE_CLASS;

    public BlockBreakListener(ViaVersionPlugin plugin) {
        super((Plugin)plugin, Protocol1_19To1_18_2.class);
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void blockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (!event.isCancelled() || !this.isBlockEntity(block.getState())) {
            return;
        }
        int serverProtocolVersion = Via.getAPI().getServerVersion().highestSupportedVersion();
        long delay = serverProtocolVersion > ProtocolVersion.v1_8.getVersion() && serverProtocolVersion < ProtocolVersion.v1_14.getVersion() ? 2L : 1L;
        this.getPlugin().getServer().getScheduler().runTaskLater(this.getPlugin(), () -> {
            BlockState state = block.getState();
            if (this.isBlockEntity(state)) {
                state.update(true, false);
            }
        }, delay);
    }

    private boolean isBlockEntity(BlockState state) {
        return state.getClass() != CRAFT_BLOCK_STATE_CLASS;
    }

    static {
        try {
            CRAFT_BLOCK_STATE_CLASS = NMSUtil.obc("block.CraftBlockState");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

