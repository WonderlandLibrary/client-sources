/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
import java.util.Arrays;

public final class ReceivedMessagesStorage
implements StorableObject {
    private final PlayerMessageSignature[] signatures = new PlayerMessageSignature[5];
    private int size;
    private int unacknowledged;

    public void add(PlayerMessageSignature playerMessageSignature) {
        PlayerMessageSignature playerMessageSignature2 = playerMessageSignature;
        for (int i = 0; i < this.size; ++i) {
            PlayerMessageSignature playerMessageSignature3 = this.signatures[i];
            this.signatures[i] = playerMessageSignature2;
            playerMessageSignature2 = playerMessageSignature3;
            if (!playerMessageSignature3.uuid().equals(playerMessageSignature.uuid())) continue;
            return;
        }
        if (this.size < this.signatures.length) {
            this.signatures[this.size++] = playerMessageSignature2;
        }
    }

    public PlayerMessageSignature[] lastSignatures() {
        return Arrays.copyOf(this.signatures, this.size);
    }

    public int tickUnacknowledged() {
        return this.unacknowledged++;
    }

    public void resetUnacknowledgedCount() {
        this.unacknowledged = 0;
    }
}

