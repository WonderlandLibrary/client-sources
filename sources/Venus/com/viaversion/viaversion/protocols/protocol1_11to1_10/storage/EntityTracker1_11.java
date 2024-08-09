/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_11to1_10.storage;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_11Types;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.flare.fastutil.Int2ObjectSyncMap;

public class EntityTracker1_11
extends EntityTrackerBase {
    private final IntSet holograms = Int2ObjectSyncMap.hashset();

    public EntityTracker1_11(UserConnection userConnection) {
        super(userConnection, Entity1_11Types.EntityType.PLAYER);
    }

    @Override
    public void removeEntity(int n) {
        super.removeEntity(n);
        this.removeHologram(n);
    }

    public boolean addHologram(int n) {
        return this.holograms.add(n);
    }

    public boolean isHologram(int n) {
        return this.holograms.contains(n);
    }

    public void removeHologram(int n) {
        this.holograms.remove(n);
    }
}

