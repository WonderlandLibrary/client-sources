/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler;

class VineConnectionHandler
extends ConnectionHandler {
    private static final IntSet VINES = new IntOpenHashSet();

    VineConnectionHandler() {
    }

    static ConnectionData.ConnectorInitAction init() {
        VineConnectionHandler connectionHandler = new VineConnectionHandler();
        return blockData -> {
            if (!blockData.getMinecraftKey().equals("minecraft:vine")) {
                return;
            }
            VINES.add(blockData.getSavedBlockStateId());
            ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (ConnectionHandler)connectionHandler);
        };
    }

    @Override
    public int connect(UserConnection user, Position position, int blockState) {
        if (this.isAttachedToBlock(user, position)) {
            return blockState;
        }
        Position upperPos = position.getRelative(BlockFace.TOP);
        int upperBlock = this.getBlockData(user, upperPos);
        if (VINES.contains(upperBlock) && this.isAttachedToBlock(user, upperPos)) {
            return blockState;
        }
        return 0;
    }

    private boolean isAttachedToBlock(UserConnection user, Position position) {
        return this.isAttachedToBlock(user, position, BlockFace.EAST) || this.isAttachedToBlock(user, position, BlockFace.WEST) || this.isAttachedToBlock(user, position, BlockFace.NORTH) || this.isAttachedToBlock(user, position, BlockFace.SOUTH);
    }

    private boolean isAttachedToBlock(UserConnection user, Position position, BlockFace blockFace) {
        return ConnectionData.OCCLUDING_STATES.contains(this.getBlockData(user, position.getRelative(blockFace)));
    }
}

