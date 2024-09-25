/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package us.myles.ViaVersion.api.entities;

import org.jetbrains.annotations.Nullable;

public interface EntityType {
    public int getId();

    @Nullable
    public EntityType getParent();

    public String name();

    default public boolean is(EntityType ... types) {
        for (EntityType type : types) {
            if (!this.is(type)) continue;
            return true;
        }
        return false;
    }

    default public boolean is(EntityType type) {
        return this == type;
    }

    default public boolean isOrHasParent(EntityType type) {
        EntityType parent = this;
        do {
            if (!parent.equals(type)) continue;
            return true;
        } while ((parent = parent.getParent()) != null);
        return false;
    }
}

