/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagTypes;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.FloatBinaryTagImpl;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag;
import org.jetbrains.annotations.NotNull;

public interface FloatBinaryTag
extends NumberBinaryTag {
    @NotNull
    public static FloatBinaryTag of(float f) {
        return new FloatBinaryTagImpl(f);
    }

    @NotNull
    default public BinaryTagType<FloatBinaryTag> type() {
        return BinaryTagTypes.FLOAT;
    }

    public float value();
}

