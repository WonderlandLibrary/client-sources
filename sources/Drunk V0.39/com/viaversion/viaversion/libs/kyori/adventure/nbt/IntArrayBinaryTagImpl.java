/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.ArrayBinaryTagImpl;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Debug.Renderer(text="\"int[\" + this.value.length + \"]\"", childrenArray="this.value", hasChildren="this.value.length > 0")
final class IntArrayBinaryTagImpl
extends ArrayBinaryTagImpl
implements IntArrayBinaryTag {
    final int[] value;

    IntArrayBinaryTagImpl(int ... value) {
        this.value = Arrays.copyOf(value, value.length);
    }

    @Override
    public int @NotNull [] value() {
        return Arrays.copyOf(this.value, this.value.length);
    }

    @Override
    public int size() {
        return this.value.length;
    }

    @Override
    public int get(int index) {
        IntArrayBinaryTagImpl.checkIndex(index, this.value.length);
        return this.value[index];
    }

    @Override
    public  @NotNull PrimitiveIterator.OfInt iterator() {
        return new PrimitiveIterator.OfInt(){
            private int index;

            @Override
            public boolean hasNext() {
                if (this.index >= IntArrayBinaryTagImpl.this.value.length - 1) return false;
                return true;
            }

            @Override
            public int nextInt() {
                if (this.hasNext()) return IntArrayBinaryTagImpl.this.value[this.index++];
                throw new NoSuchElementException();
            }
        };
    }

    @Override
    public  @NotNull Spliterator.OfInt spliterator() {
        return Arrays.spliterator(this.value);
    }

    @Override
    @NotNull
    public IntStream stream() {
        return Arrays.stream(this.value);
    }

    @Override
    public void forEachInt(@NotNull IntConsumer action) {
        int i = 0;
        int length = this.value.length;
        while (i < length) {
            action.accept(this.value[i]);
            ++i;
        }
    }

    static int[] value(IntArrayBinaryTag tag) {
        int[] nArray;
        if (tag instanceof IntArrayBinaryTagImpl) {
            nArray = ((IntArrayBinaryTagImpl)tag).value;
            return nArray;
        }
        nArray = tag.value();
        return nArray;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) return false;
        if (this.getClass() != other.getClass()) {
            return false;
        }
        IntArrayBinaryTagImpl that = (IntArrayBinaryTagImpl)other;
        return Arrays.equals(this.value, that.value);
    }

    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.value));
    }
}

