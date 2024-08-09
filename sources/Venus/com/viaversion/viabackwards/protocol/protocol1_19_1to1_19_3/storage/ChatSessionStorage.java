/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.UUID;

public final class ChatSessionStorage
implements StorableObject {
    private final UUID uuid = UUID.randomUUID();

    public UUID uuid() {
        return this.uuid;
    }
}

