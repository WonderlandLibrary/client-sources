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
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteArrayBinaryTagImpl;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public interface ByteArrayBinaryTag
extends ArrayBinaryTag,
Iterable<Byte> {
    @NotNull
    public static ByteArrayBinaryTag byteArrayBinaryTag(byte @NotNull ... value) {
        return new ByteArrayBinaryTagImpl(value);
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @NotNull
    public static ByteArrayBinaryTag of(byte @NotNull ... value) {
        return new ByteArrayBinaryTagImpl(value);
    }

    @NotNull
    default public BinaryTagType<ByteArrayBinaryTag> type() {
        return BinaryTagTypes.BYTE_ARRAY;
    }

    public byte @NotNull [] value();

    public int size();

    public byte get(int var1);
}

