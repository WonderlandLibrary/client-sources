/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
import org.jetbrains.annotations.NotNull;

public interface ListTagSetter<R, T extends BinaryTag> {
    @NotNull
    public R add(T var1);

    @NotNull
    public R add(Iterable<? extends T> var1);
}

