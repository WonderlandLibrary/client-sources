/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 *  org.jetbrains.annotations.Range
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import com.viaversion.viaversion.libs.kyori.adventure.util.HSVLikeImpl;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface HSVLike
extends Examinable {
    @NotNull
    public static HSVLike hsvLike(float f, float f2, float f3) {
        return new HSVLikeImpl(f, f2, f3);
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @NotNull
    public static HSVLike of(float f, float f2, float f3) {
        return new HSVLikeImpl(f, f2, f3);
    }

    @NotNull
    public static HSVLike fromRGB(@Range(from=0L, to=255L) int n, @Range(from=0L, to=255L) int n2, @Range(from=0L, to=255L) int n3) {
        float f = (float)n / 255.0f;
        float f2 = (float)n2 / 255.0f;
        float f3 = (float)n3 / 255.0f;
        float f4 = Math.min(f, Math.min(f2, f3));
        float f5 = Math.max(f, Math.max(f2, f3));
        float f6 = f5 - f4;
        float f7 = f5 != 0.0f ? f6 / f5 : 0.0f;
        if (f7 == 0.0f) {
            return new HSVLikeImpl(0.0f, f7, f5);
        }
        float f8 = f == f5 ? (f2 - f3) / f6 : (f2 == f5 ? 2.0f + (f3 - f) / f6 : 4.0f + (f - f2) / f6);
        if ((f8 *= 60.0f) < 0.0f) {
            f8 += 360.0f;
        }
        return new HSVLikeImpl(f8 / 360.0f, f7, f5);
    }

    public float h();

    public float s();

    public float v();

    @Override
    @NotNull
    default public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("h", this.h()), ExaminableProperty.of("s", this.s()), ExaminableProperty.of("v", this.v()));
    }
}

