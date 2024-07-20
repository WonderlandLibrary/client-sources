/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19to1_18_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public final class NonceStorage
implements StorableObject {
    private final byte[] nonce;

    public NonceStorage(byte[] nonce) {
        this.nonce = nonce;
    }

    public byte[] nonce() {
        return this.nonce;
    }
}

