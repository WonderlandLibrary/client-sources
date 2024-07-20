/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecorationAndState;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class TextDecorationAndStateImpl
implements TextDecorationAndState {
    private final TextDecoration decoration;
    private final TextDecoration.State state;

    TextDecorationAndStateImpl(TextDecoration decoration, TextDecoration.State state) {
        this.decoration = decoration;
        this.state = Objects.requireNonNull(state, "state");
    }

    @Override
    @NotNull
    public TextDecoration decoration() {
        return this.decoration;
    }

    @Override
    public @NotNull TextDecoration.State state() {
        return this.state;
    }

    public String toString() {
        return Internals.toString(this);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        TextDecorationAndStateImpl that = (TextDecorationAndStateImpl)other;
        return this.decoration == that.decoration && this.state == that.state;
    }

    public int hashCode() {
        int result = this.decoration.hashCode();
        result = 31 * result + this.state.hashCode();
        return result;
    }
}

