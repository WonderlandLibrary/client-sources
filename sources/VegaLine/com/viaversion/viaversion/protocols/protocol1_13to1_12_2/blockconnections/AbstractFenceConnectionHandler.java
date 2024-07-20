/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.BlockData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.StairConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import java.util.Arrays;

public abstract class AbstractFenceConnectionHandler
extends ConnectionHandler {
    private static final StairConnectionHandler STAIR_CONNECTION_HANDLER = new StairConnectionHandler();
    private final IntSet blockStates = new IntOpenHashSet();
    private final int[] connectedBlockStates = new int[this.statesSize()];
    private final int blockConnectionsTypeId;

    protected AbstractFenceConnectionHandler(String blockConnections) {
        this.blockConnectionsTypeId = blockConnections != null ? BlockData.connectionTypeId(blockConnections) : -1;
        Arrays.fill(this.connectedBlockStates, -1);
    }

    public ConnectionData.ConnectorInitAction getInitAction(String key) {
        AbstractFenceConnectionHandler handler = this;
        return blockData -> {
            if (key.equals(blockData.getMinecraftKey())) {
                if (blockData.hasData("waterlogged") && blockData.getValue("waterlogged").equals("true")) {
                    return;
                }
                this.blockStates.add(blockData.getSavedBlockStateId());
                ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), (ConnectionHandler)handler);
                byte internalStateId = this.getStates(blockData);
                this.connectedBlockStates[internalStateId] = blockData.getSavedBlockStateId();
            }
        };
    }

    protected byte getStates(WrappedBlockData blockData) {
        byte states = 0;
        if (blockData.getValue("east").equals("true")) {
            states = (byte)(states | 1);
        }
        if (blockData.getValue("north").equals("true")) {
            states = (byte)(states | 2);
        }
        if (blockData.getValue("south").equals("true")) {
            states = (byte)(states | 4);
        }
        if (blockData.getValue("west").equals("true")) {
            states = (byte)(states | 8);
        }
        return states;
    }

    protected byte getStates(UserConnection user, Position position, int blockState) {
        boolean pre1_12;
        byte states = 0;
        boolean bl = pre1_12 = user.getProtocolInfo().getServerProtocolVersion() < ProtocolVersion.v1_12.getVersion();
        if (this.connects(BlockFace.EAST, this.getBlockData(user, position.getRelative(BlockFace.EAST)), pre1_12)) {
            states = (byte)(states | 1);
        }
        if (this.connects(BlockFace.NORTH, this.getBlockData(user, position.getRelative(BlockFace.NORTH)), pre1_12)) {
            states = (byte)(states | 2);
        }
        if (this.connects(BlockFace.SOUTH, this.getBlockData(user, position.getRelative(BlockFace.SOUTH)), pre1_12)) {
            states = (byte)(states | 4);
        }
        if (this.connects(BlockFace.WEST, this.getBlockData(user, position.getRelative(BlockFace.WEST)), pre1_12)) {
            states = (byte)(states | 8);
        }
        return states;
    }

    protected byte statesSize() {
        return 16;
    }

    @Override
    public int getBlockData(UserConnection user, Position position) {
        return STAIR_CONNECTION_HANDLER.connect(user, position, super.getBlockData(user, position));
    }

    @Override
    public int connect(UserConnection user, Position position, int blockState) {
        int newBlockState = this.connectedBlockStates[this.getStates(user, position, blockState)];
        return newBlockState == -1 ? blockState : newBlockState;
    }

    protected boolean connects(BlockFace side, int blockState, boolean pre1_12) {
        if (this.blockStates.contains(blockState)) {
            return true;
        }
        if (this.blockConnectionsTypeId == -1) {
            return false;
        }
        BlockData blockData = (BlockData)ConnectionData.blockConnectionData.get(blockState);
        return blockData != null && blockData.connectsTo(this.blockConnectionsTypeId, side.opposite(), pre1_12);
    }

    public IntSet getBlockStates() {
        return this.blockStates;
    }
}

