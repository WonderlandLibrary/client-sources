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

    public BlockBreakListener(ViaVersionPlugin viaVersionPlugin) {
        super((Plugin)viaVersionPlugin, Protocol1_19To1_18_2.class);
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void blockBreak(BlockBreakEvent blockBreakEvent) {
        Block block = blockBreakEvent.getBlock();
        if (!blockBreakEvent.isCancelled() || !this.isBlockEntity(block.getState())) {
            return;
        }
        int n = Via.getAPI().getServerVersion().highestSupportedVersion();
        long l = n > ProtocolVersion.v1_8.getVersion() && n < ProtocolVersion.v1_14.getVersion() ? 2L : 1L;
        this.getPlugin().getServer().getScheduler().runTaskLater(this.getPlugin(), () -> this.lambda$blockBreak$0(block), l);
    }

    private boolean isBlockEntity(BlockState blockState) {
        return blockState.getClass() != CRAFT_BLOCK_STATE_CLASS;
    }

    private void lambda$blockBreak$0(Block block) {
        BlockState blockState = block.getState();
        if (this.isBlockEntity(blockState)) {
            blockState.update(true, false);
        }
    }

    static {
        try {
            CRAFT_BLOCK_STATE_CLASS = NMSUtil.obc("block.CraftBlockState");
        } catch (ClassNotFoundException classNotFoundException) {
            throw new RuntimeException(classNotFoundException);
        }
    }
}

