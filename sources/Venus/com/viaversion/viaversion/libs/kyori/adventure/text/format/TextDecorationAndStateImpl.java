/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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

    TextDecorationAndStateImpl(TextDecoration textDecoration, TextDecoration.State state) {
        this.decoration = textDecoration;
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

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        TextDecorationAndStateImpl textDecorationAndStateImpl = (TextDecorationAndStateImpl)object;
        return this.decoration == textDecorationAndStateImpl.decoration && this.state == textDecorationAndStateImpl.state;
    }

    public int hashCode() {
        int n = this.decoration.hashCode();
        n = 31 * n + this.state.hashCode();
        return n;
    }
}

