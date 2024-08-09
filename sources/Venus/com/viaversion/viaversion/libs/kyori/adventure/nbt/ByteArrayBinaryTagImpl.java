/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Debug$Renderer
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

    ByteArrayBinaryTagImpl(byte[] byArray) {
        this.value = Arrays.copyOf(byArray, byArray.length);
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
    public byte get(int n) {
        ByteArrayBinaryTagImpl.checkIndex(n, this.value.length);
        return this.value[n];
    }

    static byte[] value(ByteArrayBinaryTag byteArrayBinaryTag) {
        return byteArrayBinaryTag instanceof ByteArrayBinaryTagImpl ? ((ByteArrayBinaryTagImpl)byteArrayBinaryTag).value : byteArrayBinaryTag.value();
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ByteArrayBinaryTagImpl byteArrayBinaryTagImpl = (ByteArrayBinaryTagImpl)object;
        return Arrays.equals(this.value, byteArrayBinaryTagImpl.value);
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
        return new Iterator<Byte>(this){
            private int index;
            final ByteArrayBinaryTagImpl this$0;
            {
                this.this$0 = byteArrayBinaryTagImpl;
            }

            @Override
            public boolean hasNext() {
                return this.index < this.this$0.value.length - 1;
            }

            @Override
            public Byte next() {
                return this.this$0.value[this.index++];
            }

            @Override
            public Object next() {
                return this.next();
            }
        };
    }
}

