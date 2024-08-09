/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;

public class DifficultyStorage
extends StoredObject {
    private byte difficulty;

    public DifficultyStorage(UserConnection userConnection) {
        super(userConnection);
    }

    public byte getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(byte by) {
        this.difficulty = by;
    }
}

