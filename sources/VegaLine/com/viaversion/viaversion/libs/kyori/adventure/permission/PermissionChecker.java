/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.viaversion.viaversion.libs.kyori.adventure.permission;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.permission.PermissionCheckers;
import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointer;
import com.viaversion.viaversion.libs.kyori.adventure.util.TriState;
import java.util.Objects;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

public interface PermissionChecker
extends Predicate<String> {
    public static final Pointer<PermissionChecker> POINTER = Pointer.pointer(PermissionChecker.class, Key.key("adventure", "permission"));

    @NotNull
    public static PermissionChecker always(@NotNull TriState state) {
        Objects.requireNonNull(state);
        if (state == TriState.TRUE) {
            return PermissionCheckers.TRUE;
        }
        if (state == TriState.FALSE) {
            return PermissionCheckers.FALSE;
        }
        return PermissionCheckers.NOT_SET;
    }

    @NotNull
    public TriState value(@NotNull String var1);

    @Override
    default public boolean test(@NotNull String permission) {
        return this.value(permission) == TriState.TRUE;
    }
}

