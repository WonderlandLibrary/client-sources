/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class NonceStorage
implements StorableObject {
    private final byte[] nonce;

    public NonceStorage(byte @Nullable [] nonce) {
        this.nonce = nonce;
    }

    public byte @Nullable [] nonce() {
        return this.nonce;
    }
}

