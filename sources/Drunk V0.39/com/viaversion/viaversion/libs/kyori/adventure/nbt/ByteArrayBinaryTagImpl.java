/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.ArrayBinaryTagImpl;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Debug.Renderer(text="\"byte[\" + this.value.length + \"]\"", childrenArray="this.value", hasChildren="this.value.length > 0")
final class ByteArrayBinaryTagImpl
extends ArrayBinaryTagImpl
implements ByteArrayBinaryTag {
    final byte[] value;

    ByteArrayBinaryTagImpl(byte[] value) {
        this.value = Arrays.copyOf(value, value.length);
    }

    @Override
    public byte @NotNull [] value() {
        return Arrays.copyOf(this.value, this.value.length);
    }

    @Override
    public int size() {
        return this.value.length;
    }

    @Override
    public byte get(int index) {
        ByteArrayBinaryTagImpl.checkIndex(index, this.value.length);
        return this.value[index];
    }

    static byte[] value(ByteArrayBinaryTag tag) {
        byte[] byArray;
        if (tag instanceof ByteArrayBinaryTagImpl) {
            byArray = ((ByteArrayBinaryTagImpl)tag).value;
            return byArray;
        }
        byArray = tag.value();
        return byArray;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) return false;
        if (this.getClass() != other.getClass()) {
            return false;
        }
        ByteArrayBinaryTagImpl that = (ByteArrayBinaryTagImpl)other;
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

    @Override
    public Iterator<Byte> iterator() {
        return new Iterator<Byte>(){
            private int index;

            @Override
            public boolean hasNext() {
                if (this.index >= ByteArrayBinaryTagImpl.this.value.length - 1) return false;
                return true;
            }

            @Override
            public Byte next() {
                return ByteArrayBinaryTagImpl.this.value[this.index++];
            }
        };
    }
}

