/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.DoubleBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.FloatBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.IntBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.LongArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.LongBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ShortBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.StringBinaryTag;
import java.util.Map;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CompoundTagSetter<R> {
    @NotNull
    public R put(@NotNull String var1, @NotNull BinaryTag var2);

    @NotNull
    public R put(@NotNull CompoundBinaryTag var1);

    @NotNull
    public R put(@NotNull Map<String, ? extends BinaryTag> var1);

    @NotNull
    default public R remove(@NotNull String string) {
        return this.remove(string, null);
    }

    @NotNull
    public R remove(@NotNull String var1, @Nullable Consumer<? super BinaryTag> var2);

    @NotNull
    default public R putBoolean(@NotNull String string, boolean bl) {
        return this.put(string, bl ? ByteBinaryTag.ONE : ByteBinaryTag.ZERO);
    }

    @NotNull
    default public R putByte(@NotNull String string, byte by) {
        return this.put(string, ByteBinaryTag.of(by));
    }

    @NotNull
    default public R putShort(@NotNull String string, short s) {
        return this.put(string, ShortBinaryTag.of(s));
    }

    @NotNull
    default public R putInt(@NotNull String string, int n) {
        return this.put(string, IntBinaryTag.of(n));
    }

    @NotNull
    default public R putLong(@NotNull String string, long l) {
        return this.put(string, LongBinaryTag.of(l));
    }

    @NotNull
    default public R putFloat(@NotNull String string, float f) {
        return this.put(string, FloatBinaryTag.of(f));
    }

    @NotNull
    default public R putDouble(@NotNull String string, double d) {
        return this.put(string, DoubleBinaryTag.of(d));
    }

    @NotNull
    default public R putByteArray(@NotNull String string, byte @NotNull [] byArray) {
        return this.put(string, ByteArrayBinaryTag.of(byArray));
    }

    @NotNull
    default public R putString(@NotNull String string, @NotNull String string2) {
        return this.put(string, StringBinaryTag.of(string2));
    }

    @NotNull
    default public R putIntArray(@NotNull String string, int @NotNull [] nArray) {
        return this.put(string, IntArrayBinaryTag.of(nArray));
    }

    @NotNull
    default public R putLongArray(@NotNull String string, long @NotNull [] lArray) {
        return this.put(string, LongArrayBinaryTag.of(lArray));
    }
}

