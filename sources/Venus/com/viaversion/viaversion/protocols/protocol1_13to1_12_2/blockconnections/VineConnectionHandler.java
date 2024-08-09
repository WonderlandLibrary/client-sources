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
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;

class VineConnectionHandler
extends ConnectionHandler {
    private static final IntSet VINES = new IntOpenHashSet();

    VineConnectionHandler() {
    }

    static ConnectionData.ConnectorInitAction init() {
        VineConnectionHandler vineConnectionHandler = new VineConnectionHandler();
        return arg_0 -> VineConnectionHandler.lambda$init$0(vineConnectionHandler, arg_0);
    }

    @Override
    public int connect(UserConnection userConnection, Position position, int n) {
        if (this.isAttachedToBlock(userConnection, position)) {
            return n;
        }
        Position position2 = position.getRelative(BlockFace.TOP);
        int n2 = this.getBlockData(userConnection, position2);
        if (VINES.contains(n2) && this.isAttachedToBlock(userConnection, position2)) {
            return n;
        }
        return 1;
    }

    private boolean isAttachedToBlock(UserConnection userConnection, Position position) {
        return this.isAttachedToBlock(userConnection, position, BlockFace.EAST) || this.isAttachedToBlock(userConnection, position, BlockFace.WEST) || this.isAttachedToBlock(userConnection, position, BlockFace.NORTH) || this.isAttachedToBlock(userConnection, position, BlockFace.SOUTH);
    }

    private boolean isAttachedToBlock(UserConnection userConnection, Position position, BlockFace blockFace) {
        return ConnectionData.OCCLUDING_STATES.contains(this.getBlockData(userConnection, position.getRelative(blockFace)));
    }

    private static void lambda$init$0(VineConnectionHandler vineConnectionHandler, WrappedBlockData wrappedBlockData) {
        if (!wrappedBlockData.getMinecraftKey().equals("minecraft:vine")) {
            return;
        }
        VINES.add(wrappedBlockData.getSavedBlockStateId());
        ConnectionData.connectionHandlerMap.put(wrappedBlockData.getSavedBlockStateId(), (ConnectionHandler)vineConnectionHandler);
    }
}

