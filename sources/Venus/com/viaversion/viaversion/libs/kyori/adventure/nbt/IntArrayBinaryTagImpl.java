/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Debug$Renderer
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.ArrayBinaryTagImpl;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Debug.Renderer(text="\"int[\" + this.value.length + \"]\"", childrenArray="this.value", hasChildren="this.value.length > 0")
final class IntArrayBinaryTagImpl
extends ArrayBinaryTagImpl
implements IntArrayBinaryTag {
    final int[] value;

    IntArrayBinaryTagImpl(int ... nArray) {
        this.value = Arrays.copyOf(nArray, nArray.length);
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
    public int get(int n) {
        IntArrayBinaryTagImpl.checkIndex(n, this.value.length);
        return this.value[n];
    }

    @Override
    public  @NotNull PrimitiveIterator.OfInt iterator() {
        return new PrimitiveIterator.OfInt(this){
            private int index;
            final IntArrayBinaryTagImpl this$0;
            {
                this.this$0 = intArrayBinaryTagImpl;
            }

            @Override
            public boolean hasNext() {
                return this.index < this.this$0.value.length - 1;
            }

            @Override
            public int nextInt() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return this.this$0.value[this.index++];
            }
        };
    }

    @Override
    public @NotNull Spliterator.OfInt spliterator() {
        return Arrays.spliterator(this.value);
    }

    @Override
    @NotNull
    public IntStream stream() {
        return Arrays.stream(this.value);
    }

    @Override
    public void forEachInt(@NotNull IntConsumer intConsumer) {
        int n = this.value.length;
        for (int i = 0; i < n; ++i) {
            intConsumer.accept(this.value[i]);
        }
    }

    static int[] value(IntArrayBinaryTag intArrayBinaryTag) {
        return intArrayBinaryTag instanceof IntArrayBinaryTagImpl ? ((IntArrayBinaryTagImpl)intArrayBinaryTag).value : intArrayBinaryTag.value();
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        IntArrayBinaryTagImpl intArrayBinaryTagImpl = (IntArrayBinaryTagImpl)object;
        return Arrays.equals(this.value, intArrayBinaryTagImpl.value);
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

