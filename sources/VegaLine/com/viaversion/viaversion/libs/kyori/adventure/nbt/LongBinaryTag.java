/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 *  org.jetbrains.annotations.NotNull
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagTypes;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.LongBinaryTagImpl;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public interface LongBinaryTag
extends NumberBinaryTag {
    @NotNull
    public static LongBinaryTag longBinaryTag(long value) {
        return new LongBinaryTagImpl(value);
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @NotNull
    public static LongBinaryTag of(long value) {
        return new LongBinaryTagImpl(value);
    }

    @NotNull
    default public BinaryTagType<LongBinaryTag> type() {
        return BinaryTagTypes.LONG;
    }

    public long value();
}

