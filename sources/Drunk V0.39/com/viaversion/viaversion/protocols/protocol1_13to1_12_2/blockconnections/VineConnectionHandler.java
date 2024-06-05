/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler;
import java.util.HashSet;
import java.util.Set;

class VineConnectionHandler
extends ConnectionHandler {
    private static final Set<Integer> vines = new HashSet<Integer>();

    VineConnectionHandler() {
    }

    static ConnectionData.ConnectorInitAction init() {
        VineConnectionHandler connectionHandler = new VineConnectionHandler();
        return blockData -> {
            if (!blockData.getMinecraftKey().equals("minecraft:vine")) {
                return;
            }
            vines.add(blockData.getSavedBlockStateId());
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
        if (!vines.contains(upperBlock)) return 0;
        if (!this.isAttachedToBlock(user, upperPos)) return 0;
        return blockState;
    }

    private boolean isAttachedToBlock(UserConnection user, Position position) {
        if (this.isAttachedToBlock(user, position, BlockFace.EAST)) return true;
        if (this.isAttachedToBlock(user, position, BlockFace.WEST)) return true;
        if (this.isAttachedToBlock(user, position, BlockFace.NORTH)) return true;
        if (this.isAttachedToBlock(user, position, BlockFace.SOUTH)) return true;
        return false;
    }

    private boolean isAttachedToBlock(UserConnection user, Position position, BlockFace blockFace) {
        return ConnectionData.occludingStates.contains(this.getBlockData(user, position.getRelative(blockFace)));
    }
}

