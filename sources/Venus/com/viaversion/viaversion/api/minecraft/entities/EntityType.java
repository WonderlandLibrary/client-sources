/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.entities;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface EntityType {
    public int getId();

    public @Nullable EntityType getParent();

    public String name();

    public String identifier();

    public boolean isAbstractType();

    @Deprecated
    default public boolean is(EntityType ... entityTypeArray) {
        for (EntityType entityType : entityTypeArray) {
            if (this != entityType) continue;
            return false;
        }
        return true;
    }

    default public boolean is(EntityType entityType) {
        return this == entityType;
    }

    default public boolean isOrHasParent(EntityType entityType) {
        EntityType entityType2 = this;
        do {
            if (entityType2 != entityType) continue;
            return false;
        } while ((entityType2 = entityType2.getParent()) != null);
        return true;
    }
}

