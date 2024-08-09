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
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public abstract class AbstractStempConnectionHandler
extends ConnectionHandler {
    private static final BlockFace[] BLOCK_FACES = new BlockFace[]{BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST};
    private final IntSet blockId = new IntOpenHashSet();
    private final int baseStateId;
    private final Map<BlockFace, Integer> stemps = new EnumMap<BlockFace, Integer>(BlockFace.class);

    protected AbstractStempConnectionHandler(String string) {
        this.baseStateId = ConnectionData.getId(string);
    }

    public ConnectionData.ConnectorInitAction getInitAction(String string, String string2) {
        AbstractStempConnectionHandler abstractStempConnectionHandler = this;
        return arg_0 -> this.lambda$getInitAction$0(string, abstractStempConnectionHandler, string2, arg_0);
    }

    @Override
    public int connect(UserConnection userConnection, Position position, int n) {
        if (n != this.baseStateId) {
            return n;
        }
        for (BlockFace blockFace : BLOCK_FACES) {
            if (!this.blockId.contains(this.getBlockData(userConnection, position.getRelative(blockFace)))) continue;
            return this.stemps.get((Object)blockFace);
        }
        return this.baseStateId;
    }

    private void lambda$getInitAction$0(String string, AbstractStempConnectionHandler abstractStempConnectionHandler, String string2, WrappedBlockData wrappedBlockData) {
        if (wrappedBlockData.getSavedBlockStateId() == this.baseStateId || string.equals(wrappedBlockData.getMinecraftKey())) {
            if (wrappedBlockData.getSavedBlockStateId() != this.baseStateId) {
                abstractStempConnectionHandler.blockId.add(wrappedBlockData.getSavedBlockStateId());
            }
            ConnectionData.connectionHandlerMap.put(wrappedBlockData.getSavedBlockStateId(), (ConnectionHandler)abstractStempConnectionHandler);
        }
        if (wrappedBlockData.getMinecraftKey().equals(string2)) {
            String string3 = wrappedBlockData.getValue("facing").toUpperCase(Locale.ROOT);
            this.stemps.put(BlockFace.valueOf(string3), wrappedBlockData.getSavedBlockStateId());
        }
    }
}

