/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.UserBlockData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockConnectionStorage;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PacketBlockConnectionProvider
extends BlockConnectionProvider {
    @Override
    public void storeBlock(UserConnection userConnection, int n, int n2, int n3, int n4) {
        userConnection.get(BlockConnectionStorage.class).store(n, n2, n3, n4);
    }

    @Override
    public void removeBlock(UserConnection userConnection, int n, int n2, int n3) {
        userConnection.get(BlockConnectionStorage.class).remove(n, n2, n3);
    }

    @Override
    public int getBlockData(UserConnection userConnection, int n, int n2, int n3) {
        return userConnection.get(BlockConnectionStorage.class).get(n, n2, n3);
    }

    @Override
    public void clearStorage(UserConnection userConnection) {
        userConnection.get(BlockConnectionStorage.class).clear();
    }

    @Override
    public void modifiedBlock(UserConnection userConnection, Position position) {
        userConnection.get(BlockConnectionStorage.class).markModified(position);
    }

    @Override
    public void unloadChunk(UserConnection userConnection, int n, int n2) {
        userConnection.get(BlockConnectionStorage.class).unloadChunk(n, n2);
    }

    @Override
    public void unloadChunkSection(UserConnection userConnection, int n, int n2, int n3) {
        userConnection.get(BlockConnectionStorage.class).unloadSection(n, n2, n3);
    }

    @Override
    public boolean storesBlocks(UserConnection userConnection, @Nullable Position position) {
        if (position == null || userConnection == null) {
            return false;
        }
        return !userConnection.get(BlockConnectionStorage.class).recentlyModified(position);
    }

    @Override
    public UserBlockData forUser(UserConnection userConnection) {
        BlockConnectionStorage blockConnectionStorage = userConnection.get(BlockConnectionStorage.class);
        return (arg_0, arg_1, arg_2) -> PacketBlockConnectionProvider.lambda$forUser$0(blockConnectionStorage, arg_0, arg_1, arg_2);
    }

    private static int lambda$forUser$0(BlockConnectionStorage blockConnectionStorage, int n, int n2, int n3) {
        return blockConnectionStorage.get(n, n2, n3);
    }
}

