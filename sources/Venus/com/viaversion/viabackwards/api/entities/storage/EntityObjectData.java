/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.entities.storage.EntityData;

public class EntityObjectData
extends EntityData {
    private final int objectData;

    public EntityObjectData(BackwardsProtocol<?, ?, ?, ?> backwardsProtocol, String string, int n, int n2, int n3) {
        super(backwardsProtocol, string, n, n2);
        this.objectData = n3;
    }

    @Override
    public boolean isObjectType() {
        return false;
    }

    @Override
    public int objectData() {
        return this.objectData;
    }
}

