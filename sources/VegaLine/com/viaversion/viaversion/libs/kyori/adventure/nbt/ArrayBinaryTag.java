/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType;
import org.jetbrains.annotations.NotNull;

public interface ArrayBinaryTag
extends BinaryTag {
    @NotNull
    public BinaryTagType<? extends ArrayBinaryTag> type();
}

