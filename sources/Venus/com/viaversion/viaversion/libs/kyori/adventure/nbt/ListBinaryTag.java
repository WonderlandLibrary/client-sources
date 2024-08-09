/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Range
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagTypes;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ListBinaryTagImpl;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ListTagBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ListTagSetter;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.LongArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.StringBinaryTag;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

public interface ListBinaryTag
extends ListTagSetter<ListBinaryTag, BinaryTag>,
BinaryTag,
Iterable<BinaryTag> {
    @NotNull
    public static ListBinaryTag empty() {
        return ListBinaryTagImpl.EMPTY;
    }

    @NotNull
    public static ListBinaryTag from(@NotNull Iterable<? extends BinaryTag> iterable) {
        return ((Builder)ListBinaryTag.builder().add(iterable)).build();
    }

    @NotNull
    public static Builder<BinaryTag> builder() {
        return new ListTagBuilder<BinaryTag>();
    }

    @NotNull
    public static <T extends BinaryTag> Builder<T> builder(@NotNull BinaryTagType<T> binaryTagType) {
        if (binaryTagType == BinaryTagTypes.END) {
            throw new IllegalArgumentException("Cannot create a list of " + BinaryTagTypes.END);
        }
        return new ListTagBuilder(binaryTagType);
    }

    @NotNull
    public static ListBinaryTag of(@NotNull BinaryTagType<? extends BinaryTag> binaryTagType, @NotNull List<BinaryTag> list) {
        if (list.isEmpty()) {
            return ListBinaryTag.empty();
        }
        if (binaryTagType == BinaryTagTypes.END) {
            throw new IllegalArgumentException("Cannot create a list of " + BinaryTagTypes.END);
        }
        return new ListBinaryTagImpl(binaryTagType, list);
    }

    @NotNull
    default public BinaryTagType<ListBinaryTag> type() {
        return BinaryTagTypes.LIST;
    }

    @Deprecated
    @NotNull
    default public BinaryTagType<? extends BinaryTag> listType() {
        return this.elementType();
    }

    @NotNull
    public BinaryTagType<? extends BinaryTag> elementType();

    public int size();

    @NotNull
    public BinaryTag get(@Range(from=0L, to=0x7FFFFFFFL) int var1);

    @NotNull
    public ListBinaryTag set(int var1, @NotNull BinaryTag var2, @Nullable Consumer<? super BinaryTag> var3);

    @NotNull
    public ListBinaryTag remove(int var1, @Nullable Consumer<? super BinaryTag> var2);

    default public byte getByte(@Range(from=0L, to=0x7FFFFFFFL) int n) {
        return this.getByte(n, (byte)0);
    }

    default public byte getByte(@Range(from=0L, to=0x7FFFFFFFL) int n, byte by) {
        BinaryTag binaryTag = this.get(n);
        if (binaryTag.type().numeric()) {
            return ((NumberBinaryTag)binaryTag).byteValue();
        }
        return by;
    }

    default public short getShort(@Range(from=0L, to=0x7FFFFFFFL) int n) {
        return this.getShort(n, (short)0);
    }

    default public short getShort(@Range(from=0L, to=0x7FFFFFFFL) int n, short s) {
        BinaryTag binaryTag = this.get(n);
        if (binaryTag.type().numeric()) {
            return ((NumberBinaryTag)binaryTag).shortValue();
        }
        return s;
    }

    default public int getInt(@Range(from=0L, to=0x7FFFFFFFL) int n) {
        return this.getInt(n, 0);
    }

    default public int getInt(@Range(from=0L, to=0x7FFFFFFFL) int n, int n2) {
        BinaryTag binaryTag = this.get(n);
        if (binaryTag.type().numeric()) {
            return ((NumberBinaryTag)binaryTag).intValue();
        }
        return n2;
    }

    default public long getLong(@Range(from=0L, to=0x7FFFFFFFL) int n) {
        return this.getLong(n, 0L);
    }

    default public long getLong(@Range(from=0L, to=0x7FFFFFFFL) int n, long l) {
        BinaryTag binaryTag = this.get(n);
        if (binaryTag.type().numeric()) {
            return ((NumberBinaryTag)binaryTag).longValue();
        }
        return l;
    }

    default public float getFloat(@Range(from=0L, to=0x7FFFFFFFL) int n) {
        return this.getFloat(n, 0.0f);
    }

    default public float getFloat(@Range(from=0L, to=0x7FFFFFFFL) int n, float f) {
        BinaryTag binaryTag = this.get(n);
        if (binaryTag.type().numeric()) {
            return ((NumberBinaryTag)binaryTag).floatValue();
        }
        return f;
    }

    default public double getDouble(@Range(from=0L, to=0x7FFFFFFFL) int n) {
        return this.getDouble(n, 0.0);
    }

    default public double getDouble(@Range(from=0L, to=0x7FFFFFFFL) int n, double d) {
        BinaryTag binaryTag = this.get(n);
        if (binaryTag.type().numeric()) {
            return ((NumberBinaryTag)binaryTag).doubleValue();
        }
        return d;
    }

    default public byte @NotNull [] getByteArray(@Range(from=0L, to=0x7FFFFFFFL) int n) {
        BinaryTag binaryTag = this.get(n);
        if (binaryTag.type() == BinaryTagTypes.BYTE_ARRAY) {
            return ((ByteArrayBinaryTag)binaryTag).value();
        }
        return new byte[0];
    }

    default public byte @NotNull [] getByteArray(@Range(from=0L, to=0x7FFFFFFFL) int n, byte @NotNull [] byArray) {
        BinaryTag binaryTag = this.get(n);
        if (binaryTag.type() == BinaryTagTypes.BYTE_ARRAY) {
            return ((ByteArrayBinaryTag)binaryTag).value();
        }
        return byArray;
    }

    @NotNull
    default public String getString(@Range(from=0L, to=0x7FFFFFFFL) int n) {
        return this.getString(n, "");
    }

    @NotNull
    default public String getString(@Range(from=0L, to=0x7FFFFFFFL) int n, @NotNull String string) {
        BinaryTag binaryTag = this.get(n);
        if (binaryTag.type() == BinaryTagTypes.STRING) {
            return ((StringBinaryTag)binaryTag).value();
        }
        return string;
    }

    @NotNull
    default public ListBinaryTag getList(@Range(from=0L, to=0x7FFFFFFFL) int n) {
        return this.getList(n, null, ListBinaryTag.empty());
    }

    @NotNull
    default public ListBinaryTag getList(@Range(from=0L, to=0x7FFFFFFFL) int n, @Nullable BinaryTagType<?> binaryTagType) {
        return this.getList(n, binaryTagType, ListBinaryTag.empty());
    }

    @NotNull
    default public ListBinaryTag getList(@Range(from=0L, to=0x7FFFFFFFL) int n, @NotNull ListBinaryTag listBinaryTag) {
        return this.getList(n, null, listBinaryTag);
    }

    @NotNull
    default public ListBinaryTag getList(@Range(from=0L, to=0x7FFFFFFFL) int n, @Nullable BinaryTagType<?> binaryTagType, @NotNull ListBinaryTag listBinaryTag) {
        BinaryTag binaryTag = this.get(n);
        if (binaryTag.type() == BinaryTagTypes.LIST) {
            ListBinaryTag listBinaryTag2 = (ListBinaryTag)binaryTag;
            if (binaryTagType == null || listBinaryTag2.elementType() == binaryTagType) {
                return listBinaryTag2;
            }
        }
        return listBinaryTag;
    }

    @NotNull
    default public CompoundBinaryTag getCompound(@Range(from=0L, to=0x7FFFFFFFL) int n) {
        return this.getCompound(n, CompoundBinaryTag.empty());
    }

    @NotNull
    default public CompoundBinaryTag getCompound(@Range(from=0L, to=0x7FFFFFFFL) int n, @NotNull CompoundBinaryTag compoundBinaryTag) {
        BinaryTag binaryTag = this.get(n);
        if (binaryTag.type() == BinaryTagTypes.COMPOUND) {
            return (CompoundBinaryTag)binaryTag;
        }
        return compoundBinaryTag;
    }

    default public int @NotNull [] getIntArray(@Range(from=0L, to=0x7FFFFFFFL) int n) {
        BinaryTag binaryTag = this.get(n);
        if (binaryTag.type() == BinaryTagTypes.INT_ARRAY) {
            return ((IntArrayBinaryTag)binaryTag).value();
        }
        return new int[0];
    }

    default public int @NotNull [] getIntArray(@Range(from=0L, to=0x7FFFFFFFL) int n, int @NotNull [] nArray) {
        BinaryTag binaryTag = this.get(n);
        if (binaryTag.type() == BinaryTagTypes.INT_ARRAY) {
            return ((IntArrayBinaryTag)binaryTag).value();
        }
        return nArray;
    }

    default public long @NotNull [] getLongArray(@Range(from=0L, to=0x7FFFFFFFL) int n) {
        BinaryTag binaryTag = this.get(n);
        if (binaryTag.type() == BinaryTagTypes.LONG_ARRAY) {
            return ((LongArrayBinaryTag)binaryTag).value();
        }
        return new long[0];
    }

    default public long @NotNull [] getLongArray(@Range(from=0L, to=0x7FFFFFFFL) int n, long @NotNull [] lArray) {
        BinaryTag binaryTag = this.get(n);
        if (binaryTag.type() == BinaryTagTypes.LONG_ARRAY) {
            return ((LongArrayBinaryTag)binaryTag).value();
        }
        return lArray;
    }

    @NotNull
    public Stream<BinaryTag> stream();

    public static interface Builder<T extends BinaryTag>
    extends ListTagSetter<Builder<T>, T> {
        @NotNull
        public ListBinaryTag build();
    }
}

