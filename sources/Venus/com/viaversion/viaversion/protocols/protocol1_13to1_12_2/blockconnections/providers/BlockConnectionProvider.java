/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.UserBlockData;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BlockConnectionProvider
implements Provider {
    public int getBlockData(UserConnection userConnection, int n, int n2, int n3) {
        int n4 = this.getWorldBlockData(userConnection, n, n2, n3);
        return Protocol1_13To1_12_2.MAPPINGS.getBlockMappings().getNewId(n4);
    }

    public int getWorldBlockData(UserConnection userConnection, int n, int n2, int n3) {
        return 1;
    }

    public void storeBlock(UserConnection userConnection, int n, int n2, int n3, int n4) {
    }

    public void removeBlock(UserConnection userConnection, int n, int n2, int n3) {
    }

    public void clearStorage(UserConnection userConnection) {
    }

    public void modifiedBlock(UserConnection userConnection, Position position) {
    }

    public void unloadChunk(UserConnection userConnection, int n, int n2) {
    }

    public void unloadChunkSection(UserConnection userConnection, int n, int n2, int n3) {
    }

    public boolean storesBlocks(UserConnection userConnection, @Nullable Position position) {
        return true;
    }

    public UserBlockData forUser(UserConnection userConnection) {
        return (arg_0, arg_1, arg_2) -> this.lambda$forUser$0(userConnection, arg_0, arg_1, arg_2);
    }

    private int lambda$forUser$0(UserConnection userConnection, int n, int n2, int n3) {
        return this.getBlockData(userConnection, n, n2, n3);
    }
}

