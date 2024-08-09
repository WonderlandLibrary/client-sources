/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.management;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public abstract class UserListEntry<T> {
    @Nullable
    private final T value;

    public UserListEntry(@Nullable T t) {
        this.value = t;
    }

    @Nullable
    T getValue() {
        return this.value;
    }

    boolean hasBanExpired() {
        return true;
    }

    protected abstract void onSerialization(JsonObject var1);
}

