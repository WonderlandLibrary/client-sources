/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt.api;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.api.BinaryTagHolderImpl;
import com.viaversion.viaversion.libs.kyori.adventure.util.Codec;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public interface BinaryTagHolder {
    @NotNull
    public static <T, EX extends Exception> BinaryTagHolder encode(@NotNull T t, @NotNull Codec<? super T, String, ?, EX> codec) throws EX {
        return new BinaryTagHolderImpl(codec.encode(t));
    }

    @NotNull
    public static BinaryTagHolder binaryTagHolder(@NotNull String string) {
        return new BinaryTagHolderImpl(string);
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @NotNull
    public static BinaryTagHolder of(@NotNull String string) {
        return new BinaryTagHolderImpl(string);
    }

    @NotNull
    public String string();

    @NotNull
    public <T, DX extends Exception> T get(@NotNull Codec<T, String, DX, ?> var1) throws DX;
}

