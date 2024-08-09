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

    protected AbstractFenceConnectionHandler(String string) {
        this.blockConnectionsTypeId = string != null ? BlockData.connectionTypeId(string) : -1;
        Arrays.fill(this.connectedBlockStates, -1);
    }

    public ConnectionData.ConnectorInitAction getInitAction(String string) {
        AbstractFenceConnectionHandler abstractFenceConnectionHandler = this;
        return arg_0 -> this.lambda$getInitAction$0(string, abstractFenceConnectionHandler, arg_0);
    }

    protected byte getStates(WrappedBlockData wrappedBlockData) {
        byte by = 0;
        if (wrappedBlockData.getValue("east").equals("true")) {
            by = (byte)(by | 1);
        }
        if (wrappedBlockData.getValue("north").equals("true")) {
            by = (byte)(by | 2);
        }
        if (wrappedBlockData.getValue("south").equals("true")) {
            by = (byte)(by | 4);
        }
        if (wrappedBlockData.getValue("west").equals("true")) {
            by = (byte)(by | 8);
        }
        return by;
    }

    protected byte getStates(UserConnection userConnection, Position position, int n) {
        boolean bl;
        byte by = 0;
        boolean bl2 = bl = userConnection.getProtocolInfo().getServerProtocolVersion() < ProtocolVersion.v1_12.getVersion();
        if (this.connects(BlockFace.EAST, this.getBlockData(userConnection, position.getRelative(BlockFace.EAST)), bl)) {
            by = (byte)(by | 1);
        }
        if (this.connects(BlockFace.NORTH, this.getBlockData(userConnection, position.getRelative(BlockFace.NORTH)), bl)) {
            by = (byte)(by | 2);
        }
        if (this.connects(BlockFace.SOUTH, this.getBlockData(userConnection, position.getRelative(BlockFace.SOUTH)), bl)) {
            by = (byte)(by | 4);
        }
        if (this.connects(BlockFace.WEST, this.getBlockData(userConnection, position.getRelative(BlockFace.WEST)), bl)) {
            by = (byte)(by | 8);
        }
        return by;
    }

    protected byte statesSize() {
        return 1;
    }

    @Override
    public int getBlockData(UserConnection userConnection, Position position) {
        return STAIR_CONNECTION_HANDLER.connect(userConnection, position, super.getBlockData(userConnection, position));
    }

    @Override
    public int connect(UserConnection userConnection, Position position, int n) {
        int n2 = this.connectedBlockStates[this.getStates(userConnection, position, n)];
        return n2 == -1 ? n : n2;
    }

    protected boolean connects(BlockFace blockFace, int n, boolean bl) {
        if (this.blockStates.contains(n)) {
            return false;
        }
        if (this.blockConnectionsTypeId == -1) {
            return true;
        }
        BlockData blockData = (BlockData)ConnectionData.blockConnectionData.get(n);
        return blockData != null && blockData.connectsTo(this.blockConnectionsTypeId, blockFace.opposite(), bl);
    }

    public IntSet getBlockStates() {
        return this.blockStates;
    }

    private void lambda$getInitAction$0(String string, AbstractFenceConnectionHandler abstractFenceConnectionHandler, WrappedBlockData wrappedBlockData) {
        if (string.equals(wrappedBlockData.getMinecraftKey())) {
            if (wrappedBlockData.hasData("waterlogged") && wrappedBlockData.getValue("waterlogged").equals("true")) {
                return;
            }
            this.blockStates.add(wrappedBlockData.getSavedBlockStateId());
            ConnectionData.connectionHandlerMap.put(wrappedBlockData.getSavedBlockStateId(), (ConnectionHandler)abstractFenceConnectionHandler);
            byte by = this.getStates(wrappedBlockData);
            this.connectedBlockStates[by] = wrappedBlockData.getSavedBlockStateId();
        }
    }
}

