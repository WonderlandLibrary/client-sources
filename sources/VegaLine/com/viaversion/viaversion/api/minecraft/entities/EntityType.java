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
    default public boolean is(EntityType ... types) {
        for (EntityType type2 : types) {
            if (this != type2) continue;
            return true;
        }
        return false;
    }

    default public boolean is(EntityType type2) {
        return this == type2;
    }

    default public boolean isOrHasParent(EntityType type2) {
        EntityType parent = this;
        do {
            if (parent != type2) continue;
            return true;
        } while ((parent = parent.getParent()) != null);
        return false;
    }
}

