/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.data.entity;

import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class StoredEntityDataImpl
implements StoredEntityData {
    private final Map<Class<?>, Object> storedObjects = new ConcurrentHashMap();
    private final EntityType type;

    public StoredEntityDataImpl(EntityType entityType) {
        this.type = entityType;
    }

    @Override
    public EntityType type() {
        return this.type;
    }

    @Override
    public <T> @Nullable T get(Class<T> clazz) {
        return (T)this.storedObjects.get(clazz);
    }

    @Override
    public <T> @Nullable T remove(Class<T> clazz) {
        return (T)this.storedObjects.remove(clazz);
    }

    @Override
    public boolean has(Class<?> clazz) {
        return this.storedObjects.containsKey(clazz);
    }

    @Override
    public void put(Object object) {
        this.storedObjects.put(object.getClass(), object);
    }
}

