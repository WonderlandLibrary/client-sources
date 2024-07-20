/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class KeybindComponentImpl
extends AbstractComponent
implements KeybindComponent {
    private final String keybind;

    static KeybindComponent create(@NotNull List<? extends ComponentLike> children, @NotNull Style style, @NotNull String keybind) {
        return new KeybindComponentImpl(ComponentLike.asComponents(children, IS_NOT_EMPTY), Objects.requireNonNull(style, "style"), Objects.requireNonNull(keybind, "keybind"));
    }

    KeybindComponentImpl(@NotNull List<Component> children, @NotNull Style style, @NotNull String keybind) {
        super(children, style);
        this.keybind = keybind;
    }

    @Override
    @NotNull
    public String keybind() {
        return this.keybind;
    }

    @Override
    @NotNull
    public KeybindComponent keybind(@NotNull String keybind) {
        if (Objects.equals(this.keybind, keybind)) {
            return this;
        }
        return KeybindComponentImpl.create(this.children, this.style, keybind);
    }

    @Override
    @NotNull
    public KeybindComponent children(@NotNull List<? extends ComponentLike> children) {
        return KeybindComponentImpl.create(children, this.style, this.keybind);
    }

    @Override
    @NotNull
    public KeybindComponent style(@NotNull Style style) {
        return KeybindComponentImpl.create(this.children, style, this.keybind);
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KeybindComponent)) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        KeybindComponent that = (KeybindComponent)other;
        return Objects.equals(this.keybind, that.keybind());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + this.keybind.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @Override
    @NotNull
    public KeybindComponent.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static final class BuilderImpl
    extends AbstractComponentBuilder<KeybindComponent, KeybindComponent.Builder>
    implements KeybindComponent.Builder {
        @Nullable
        private String keybind;

        BuilderImpl() {
        }

        BuilderImpl(@NotNull KeybindComponent component) {
            super(component);
            this.keybind = component.keybind();
        }

        @Override
        @NotNull
        public KeybindComponent.Builder keybind(@NotNull String keybind) {
            this.keybind = Objects.requireNonNull(keybind, "keybind");
            return this;
        }

        @Override
        @NotNull
        public KeybindComponent build() {
            if (this.keybind == null) {
                throw new IllegalStateException("keybind must be set");
            }
            return KeybindComponentImpl.create(this.children, this.buildStyle(), this.keybind);
        }
    }
}

