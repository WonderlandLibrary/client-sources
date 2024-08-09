/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagTypes;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.StringBinaryTagImpl;
import org.jetbrains.annotations.NotNull;

public interface StringBinaryTag
extends BinaryTag {
    @NotNull
    public static StringBinaryTag of(@NotNull String string) {
        return new StringBinaryTagImpl(string);
    }

    @NotNull
    default public BinaryTagType<StringBinaryTag> type() {
        return BinaryTagTypes.STRING;
    }

    @NotNull
    public String value();
}

