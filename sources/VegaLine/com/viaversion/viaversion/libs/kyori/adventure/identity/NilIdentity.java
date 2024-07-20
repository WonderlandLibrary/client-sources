/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.kyori.adventure.identity;

import com.viaversion.viaversion.libs.kyori.adventure.identity.Identity;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class NilIdentity
implements Identity {
    static final UUID NIL_UUID = new UUID(0L, 0L);
    static final Identity INSTANCE = new NilIdentity();

    NilIdentity() {
    }

    @Override
    @NotNull
    public UUID uuid() {
        return NIL_UUID;
    }

    public String toString() {
        return "Identity.nil()";
    }

    public boolean equals(@Nullable Object that) {
        return this == that;
    }

    public int hashCode() {
        return 0;
    }
}

