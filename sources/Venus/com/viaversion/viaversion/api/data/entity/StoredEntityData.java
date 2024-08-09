/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.data.entity;

import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface StoredEntityData {
    public EntityType type();

    public boolean has(Class<?> var1);

    public <T> @Nullable T get(Class<T> var1);

    public <T> @Nullable T remove(Class<T> var1);

    public void put(Object var1);
}

