/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;

import com.google.common.collect.Sets;
import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import java.util.Set;

public class ClientChunks
extends StoredObject {
    private final Set<Long> loadedChunks = Sets.newConcurrentHashSet();

    public ClientChunks(UserConnection userConnection) {
        super(userConnection);
    }

    public static long toLong(int n, int n2) {
        return ((long)n << 32) + (long)n2 + 0x80000000L;
    }

    public Set<Long> getLoadedChunks() {
        return this.loadedChunks;
    }
}

