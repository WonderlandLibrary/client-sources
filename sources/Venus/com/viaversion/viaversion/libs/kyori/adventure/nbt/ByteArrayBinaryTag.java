/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.ArrayBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagTypes;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteArrayBinaryTagImpl;
import org.jetbrains.annotations.NotNull;

public interface ByteArrayBinaryTag
extends ArrayBinaryTag,
Iterable<Byte> {
    @NotNull
    public static ByteArrayBinaryTag of(byte @NotNull ... byArray) {
        return new ByteArrayBinaryTagImpl(byArray);
    }

    @NotNull
    default public BinaryTagType<ByteArrayBinaryTag> type() {
        return BinaryTagTypes.BYTE_ARRAY;
    }

    public byte @NotNull [] value();

    public int size();

    public byte get(int var1);
}

