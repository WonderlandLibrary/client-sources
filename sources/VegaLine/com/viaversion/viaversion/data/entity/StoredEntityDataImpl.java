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

    public StoredEntityDataImpl(EntityType type2) {
        this.type = type2;
    }

    @Override
    public EntityType type() {
        return this.type;
    }

    @Override
    public <T> @Nullable T get(Class<T> objectClass) {
        return (T)this.storedObjects.get(objectClass);
    }

    @Override
    public <T> @Nullable T remove(Class<T> objectClass) {
        return (T)this.storedObjects.remove(objectClass);
    }

    @Override
    public boolean has(Class<?> objectClass) {
        return this.storedObjects.containsKey(objectClass);
    }

    @Override
    public void put(Object object) {
        this.storedObjects.put(object.getClass(), object);
    }
}

