/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.ArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagTypes;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.LongArrayBinaryTagImpl;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;
import org.jetbrains.annotations.NotNull;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface LongArrayBinaryTag
extends ArrayBinaryTag,
Iterable<Long> {
    @NotNull
    public static LongArrayBinaryTag of(long @NotNull ... lArray) {
        return new LongArrayBinaryTagImpl(lArray);
    }

    @NotNull
    default public BinaryTagType<LongArrayBinaryTag> type() {
        return BinaryTagTypes.LONG_ARRAY;
    }

    public long @NotNull [] value();

    public int size();

    public long get(int var1);

    public  @NotNull PrimitiveIterator.OfLong iterator();

    public @NotNull Spliterator.OfLong spliterator();

    @NotNull
    public LongStream stream();

    public void forEachLong(@NotNull LongConsumer var1);

    @Override
    default public @NotNull Spliterator spliterator() {
        return this.spliterator();
    }

    @Override
    default public @NotNull Iterator iterator() {
        return this.iterator();
    }
}

