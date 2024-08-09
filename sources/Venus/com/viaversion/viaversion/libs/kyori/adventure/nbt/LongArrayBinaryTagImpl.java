/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Debug$Renderer
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.ArrayBinaryTagImpl;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.LongArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Debug.Renderer(text="\"long[\" + this.value.length + \"]\"", childrenArray="this.value", hasChildren="this.value.length > 0")
final class LongArrayBinaryTagImpl
extends ArrayBinaryTagImpl
implements LongArrayBinaryTag {
    final long[] value;

    LongArrayBinaryTagImpl(long[] lArray) {
        this.value = Arrays.copyOf(lArray, lArray.length);
    }

    @Override
    public long @NotNull [] value() {
        return Arrays.copyOf(this.value, this.value.length);
    }

    @Override
    public int size() {
        return this.value.length;
    }

    @Override
    public long get(int n) {
        LongArrayBinaryTagImpl.checkIndex(n, this.value.length);
        return this.value[n];
    }

    @Override
    public  @NotNull PrimitiveIterator.OfLong iterator() {
        return new PrimitiveIterator.OfLong(this){
            private int index;
            final LongArrayBinaryTagImpl this$0;
            {
                this.this$0 = longArrayBinaryTagImpl;
            }

            @Override
            public boolean hasNext() {
                return this.index < this.this$0.value.length - 1;
            }

            @Override
            public long nextLong() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return this.this$0.value[this.index++];
            }
        };
    }

    @Override
    public @NotNull Spliterator.OfLong spliterator() {
        return Arrays.spliterator(this.value);
    }

    @Override
    @NotNull
    public LongStream stream() {
        return Arrays.stream(this.value);
    }

    @Override
    public void forEachLong(@NotNull LongConsumer longConsumer) {
        int n = this.value.length;
        for (int i = 0; i < n; ++i) {
            longConsumer.accept(this.value[i]);
        }
    }

    static long[] value(LongArrayBinaryTag longArrayBinaryTag) {
        return longArrayBinaryTag instanceof LongArrayBinaryTagImpl ? ((LongArrayBinaryTagImpl)longArrayBinaryTag).value : longArrayBinaryTag.value();
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        LongArrayBinaryTagImpl longArrayBinaryTagImpl = (LongArrayBinaryTagImpl)object;
        return Arrays.equals(this.value, longArrayBinaryTagImpl.value);
    }

    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.value));
    }

    @Override
    public @NotNull Spliterator spliterator() {
        return this.spliterator();
    }

    @Override
    public @NotNull Iterator iterator() {
        return this.iterator();
    }
}

