/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.entities;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface EntityType {
    public int getId();

    public @Nullable EntityType getParent();

    public String name();

    default public boolean is(EntityType ... types) {
        EntityType[] entityTypeArray = types;
        int n = entityTypeArray.length;
        int n2 = 0;
        while (n2 < n) {
            EntityType type = entityTypeArray[n2];
            if (this == type) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    default public boolean is(EntityType type) {
        if (this != type) return false;
        return true;
    }

    default public boolean isOrHasParent(EntityType type) {
        EntityType parent = this;
        do {
            if (parent != type) continue;
            return true;
        } while ((parent = parent.getParent()) != null);
        return false;
    }
}

