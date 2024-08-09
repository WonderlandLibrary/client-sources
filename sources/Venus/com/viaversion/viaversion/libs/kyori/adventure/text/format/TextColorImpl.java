/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Debug$Renderer
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.Nullable;

@Debug.Renderer(text="asHexString()")
final class TextColorImpl
implements TextColor {
    private final int value;

    TextColorImpl(int n) {
        this.value = n;
    }

    @Override
    public int value() {
        return this.value;
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof TextColorImpl)) {
            return true;
        }
        TextColorImpl textColorImpl = (TextColorImpl)object;
        return this.value == textColorImpl.value;
    }

    public int hashCode() {
        return this.value;
    }

    public String toString() {
        return this.asHexString();
    }
}

