/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections;

import java.util.HashSet;
import java.util.Set;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.minecraft.BlockFace;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler;

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
        if (vines.contains(upperBlock) && this.isAttachedToBlock(user, upperPos)) {
            return blockState;
        }
        return 0;
    }

    private boolean isAttachedToBlock(UserConnection user, Position position) {
        return this.isAttachedToBlock(user, position, BlockFace.EAST) || this.isAttachedToBlock(user, position, BlockFace.WEST) || this.isAttachedToBlock(user, position, BlockFace.NORTH) || this.isAttachedToBlock(user, position, BlockFace.SOUTH);
    }

    private boolean isAttachedToBlock(UserConnection user, Position position, BlockFace blockFace) {
        return ConnectionData.occludingStates.contains(this.getBlockData(user, position.getRelative(blockFace)));
    }
}

