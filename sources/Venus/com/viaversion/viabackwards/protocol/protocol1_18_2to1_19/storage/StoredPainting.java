/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.Position;
import java.util.UUID;

public final class StoredPainting
implements StorableObject {
    private final int entityId;
    private final UUID uuid;
    private final Position position;
    private final byte direction;

    public StoredPainting(int n, UUID uUID, Position position, int n2) {
        this.entityId = n;
        this.uuid = uUID;
        this.position = position;
        this.direction = this.to2dDirection(n2);
    }

    public int entityId() {
        return this.entityId;
    }

    public UUID uuid() {
        return this.uuid;
    }

    public Position position() {
        return this.position;
    }

    public byte direction() {
        return this.direction;
    }

    private byte to2dDirection(int n) {
        switch (n) {
            case 0: 
            case 1: {
                return 1;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 1;
            }
            case 4: {
                return 0;
            }
            case 5: {
                return 0;
            }
        }
        throw new IllegalArgumentException("Invalid direction: " + n);
    }
}

