/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.util.HSVLike;
import com.viaversion.viaversion.libs.kyori.adventure.util.ShadyPines;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

final class HSVLikeImpl
implements HSVLike {
    private final float h;
    private final float s;
    private final float v;

    HSVLikeImpl(float f, float f2, float f3) {
        HSVLikeImpl.requireInsideRange(f, "h");
        HSVLikeImpl.requireInsideRange(f2, "s");
        HSVLikeImpl.requireInsideRange(f3, "v");
        this.h = f;
        this.s = f2;
        this.v = f3;
    }

    @Override
    public float h() {
        return this.h;
    }

    @Override
    public float s() {
        return this.s;
    }

    @Override
    public float v() {
        return this.v;
    }

    private static void requireInsideRange(float f, String string) throws IllegalArgumentException {
        if (f < 0.0f || 1.0f < f) {
            throw new IllegalArgumentException(string + " (" + f + ") is not inside the required range: [0,1]");
        }
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof HSVLikeImpl)) {
            return true;
        }
        HSVLikeImpl hSVLikeImpl = (HSVLikeImpl)object;
        return ShadyPines.equals(hSVLikeImpl.h, this.h) && ShadyPines.equals(hSVLikeImpl.s, this.s) && ShadyPines.equals(hSVLikeImpl.v, this.v);
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.h), Float.valueOf(this.s), Float.valueOf(this.v));
    }

    public String toString() {
        return Internals.toString(this);
    }
}

