/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.ArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagTypes;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTagImpl;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntArrayBinaryTag
extends ArrayBinaryTag,
Iterable<Integer> {
    @NotNull
    public static IntArrayBinaryTag of(int @NotNull ... nArray) {
        return new IntArrayBinaryTagImpl(nArray);
    }

    @NotNull
    default public BinaryTagType<IntArrayBinaryTag> type() {
        return BinaryTagTypes.INT_ARRAY;
    }

    public int @NotNull [] value();

    public int size();

    public int get(int var1);

    public  @NotNull PrimitiveIterator.OfInt iterator();

    public @NotNull Spliterator.OfInt spliterator();

    @NotNull
    public IntStream stream();

    public void forEachInt(@NotNull IntConsumer var1);

    @Override
    default public @NotNull Spliterator spliterator() {
        return this.spliterator();
    }

    @Override
    default public @NotNull Iterator iterator() {
        return this.iterator();
    }
}

