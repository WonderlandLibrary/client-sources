/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 *  org.jetbrains.annotations.NotNull
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.ArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagTypes;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTagImpl;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public interface IntArrayBinaryTag
extends ArrayBinaryTag,
Iterable<Integer> {
    @NotNull
    public static IntArrayBinaryTag intArrayBinaryTag(int @NotNull ... value) {
        return new IntArrayBinaryTagImpl(value);
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @NotNull
    public static IntArrayBinaryTag of(int @NotNull ... value) {
        return new IntArrayBinaryTagImpl(value);
    }

    @NotNull
    default public BinaryTagType<IntArrayBinaryTag> type() {
        return BinaryTagTypes.INT_ARRAY;
    }

    public int @NotNull [] value();

    public int size();

    public int get(int var1);

    public  @NotNull PrimitiveIterator.OfInt iterator();

    public  @NotNull Spliterator.OfInt spliterator();

    @NotNull
    public IntStream stream();

    public void forEachInt(@NotNull IntConsumer var1);
}

