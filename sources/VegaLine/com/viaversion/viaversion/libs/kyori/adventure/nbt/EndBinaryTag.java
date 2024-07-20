/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 *  org.jetbrains.annotations.NotNull
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagTypes;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.EndBinaryTagImpl;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public interface EndBinaryTag
extends BinaryTag {
    @NotNull
    public static EndBinaryTag endBinaryTag() {
        return EndBinaryTagImpl.INSTANCE;
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @NotNull
    public static EndBinaryTag get() {
        return EndBinaryTagImpl.INSTANCE;
    }

    @NotNull
    default public BinaryTagType<EndBinaryTag> type() {
        return BinaryTagTypes.END;
    }
}

