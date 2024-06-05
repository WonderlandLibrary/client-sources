/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import com.viaversion.viaversion.libs.kyori.adventure.util.HSVLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface RGBLike {
    public @Range(from=0L, to=255L) int red();

    public @Range(from=0L, to=255L) int green();

    public @Range(from=0L, to=255L) int blue();

    @NotNull
    default public HSVLike asHSV() {
        return HSVLike.fromRGB(this.red(), this.green(), this.blue());
    }
}

