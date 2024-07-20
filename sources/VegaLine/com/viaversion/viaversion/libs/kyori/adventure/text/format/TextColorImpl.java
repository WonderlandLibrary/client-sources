/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Debug$Renderer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.util.HSVLike;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Debug.Renderer(text="asHexString()")
final class TextColorImpl
implements TextColor {
    private final int value;

    TextColorImpl(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return this.value;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TextColorImpl)) {
            return false;
        }
        TextColorImpl that = (TextColorImpl)other;
        return this.value == that.value;
    }

    public int hashCode() {
        return this.value;
    }

    public String toString() {
        return this.asHexString();
    }

    static float distance(@NotNull HSVLike self, @NotNull HSVLike other) {
        float hueDistance = 3.0f * Math.min(Math.abs(self.h() - other.h()), 1.0f - Math.abs(self.h() - other.h()));
        float saturationDiff = self.s() - other.s();
        float valueDiff = self.v() - other.v();
        return hueDistance * hueDistance + saturationDiff * saturationDiff + valueDiff * valueDiff;
    }
}

