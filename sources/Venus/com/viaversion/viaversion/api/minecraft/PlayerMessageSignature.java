/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft;

import java.util.UUID;

public final class PlayerMessageSignature {
    private final UUID uuid;
    private final byte[] signatureBytes;

    public PlayerMessageSignature(UUID uUID, byte[] byArray) {
        this.uuid = uUID;
        this.signatureBytes = byArray;
    }

    public UUID uuid() {
        return this.uuid;
    }

    public byte[] signatureBytes() {
        return this.signatureBytes;
    }
}

