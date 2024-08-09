/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
import java.util.Arrays;

public final class ReceivedMessagesStorage
implements StorableObject {
    private final PlayerMessageSignature[] signatures = new PlayerMessageSignature[5];
    private PlayerMessageSignature lastSignature;
    private int size;
    private int unacknowledged;

    public boolean add(PlayerMessageSignature playerMessageSignature) {
        if (playerMessageSignature.equals(this.lastSignature)) {
            return true;
        }
        this.lastSignature = playerMessageSignature;
        PlayerMessageSignature playerMessageSignature2 = playerMessageSignature;
        for (int i = 0; i < this.size; ++i) {
            PlayerMessageSignature playerMessageSignature3 = this.signatures[i];
            this.signatures[i] = playerMessageSignature2;
            playerMessageSignature2 = playerMessageSignature3;
            if (!playerMessageSignature3.uuid().equals(playerMessageSignature.uuid())) continue;
            return false;
        }
        if (this.size < this.signatures.length) {
            this.signatures[this.size++] = playerMessageSignature2;
        }
        return false;
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

