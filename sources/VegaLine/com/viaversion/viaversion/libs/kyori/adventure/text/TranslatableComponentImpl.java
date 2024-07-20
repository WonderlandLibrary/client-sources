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
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class TranslatableComponentImpl
extends AbstractComponent
implements TranslatableComponent {
    private final String key;
    @Nullable
    private final String fallback;
    private final List<Component> args;

    static TranslatableComponent create(@NotNull List<Component> children, @NotNull Style style, @NotNull String key, @Nullable String fallback, @NotNull @NotNull ComponentLike @NotNull [] args) {
        Objects.requireNonNull(args, "args");
        return TranslatableComponentImpl.create(children, style, key, fallback, Arrays.asList(args));
    }

    static TranslatableComponent create(@NotNull List<? extends ComponentLike> children, @NotNull Style style, @NotNull String key, @Nullable String fallback, @NotNull List<? extends ComponentLike> args) {
        return new TranslatableComponentImpl(ComponentLike.asComponents(children, IS_NOT_EMPTY), Objects.requireNonNull(style, "style"), Objects.requireNonNull(key, "key"), fallback, ComponentLike.asComponents(args));
    }

    TranslatableComponentImpl(@NotNull List<Component> children, @NotNull Style style, @NotNull String key, @Nullable String fallback, @NotNull List<Component> args) {
        super(children, style);
        this.key = key;
        this.fallback = fallback;
        this.args = args;
    }

    @Override
    @NotNull
    public String key() {
        return this.key;
    }

    @Override
    @NotNull
    public TranslatableComponent key(@NotNull String key) {
        if (Objects.equals(this.key, key)) {
            return this;
        }
        return TranslatableComponentImpl.create(this.children, this.style, key, this.fallback, this.args);
    }

    @Override
    @NotNull
    public List<Component> args() {
        return this.args;
    }

    @Override
    @NotNull
    public TranslatableComponent args(@NotNull @NotNull ComponentLike @NotNull ... args) {
        return TranslatableComponentImpl.create(this.children, this.style, this.key, this.fallback, args);
    }

    @Override
    @NotNull
    public TranslatableComponent args(@NotNull List<? extends ComponentLike> args) {
        return TranslatableComponentImpl.create(this.children, this.style, this.key, this.fallback, args);
    }

    @Override
    @Nullable
    public String fallback() {
        return this.fallback;
    }

    @Override
    @NotNull
    public TranslatableComponent fallback(@Nullable String fallback) {
        return TranslatableComponentImpl.create(this.children, this.style, this.key, fallback, this.args);
    }

    @Override
    @NotNull
    public TranslatableComponent children(@NotNull List<? extends ComponentLike> children) {
        return TranslatableComponentImpl.create(children, this.style, this.key, this.fallback, this.args);
    }

    @Override
    @NotNull
    public TranslatableComponent style(@NotNull Style style) {
        return TranslatableComponentImpl.create(this.children, style, this.key, this.fallback, this.args);
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TranslatableComponent)) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        TranslatableComponent that = (TranslatableComponent)other;
        return Objects.equals(this.key, that.key()) && Objects.equals(this.fallback, that.fallback()) && Objects.equals(this.args, that.args());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + this.key.hashCode();
        result = 31 * result + Objects.hashCode(this.fallback);
        result = 31 * result + this.args.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @Override
    @NotNull
    public TranslatableComponent.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static final class BuilderImpl
    extends AbstractComponentBuilder<TranslatableComponent, TranslatableComponent.Builder>
    implements TranslatableComponent.Builder {
        @Nullable
        private String key;
        @Nullable
        private String fallback;
        private List<? extends Component> args = Collections.emptyList();

        BuilderImpl() {
        }

        BuilderImpl(@NotNull TranslatableComponent component) {
            super(component);
            this.key = component.key();
            this.args = component.args();
            this.fallback = component.fallback();
        }

        @Override
        @NotNull
        public TranslatableComponent.Builder key(@NotNull String key) {
            this.key = key;
            return this;
        }

        @Override
        @NotNull
        public TranslatableComponent.Builder args(@NotNull ComponentBuilder<?, ?> arg) {
            return this.args(Collections.singletonList(Objects.requireNonNull(arg, "arg").build()));
        }

        @Override
        @NotNull
        public TranslatableComponent.Builder args(@NotNull @NotNull ComponentBuilder<?, ?> @NotNull ... args) {
            Objects.requireNonNull(args, "args");
            if (args.length == 0) {
                return this.args(Collections.emptyList());
            }
            return this.args(Stream.of(args).map(ComponentBuilder::build).collect(Collectors.toList()));
        }

        @Override
        @NotNull
        public TranslatableComponent.Builder args(@NotNull Component arg) {
            return this.args(Collections.singletonList(Objects.requireNonNull(arg, "arg")));
        }

        @Override
        @NotNull
        public TranslatableComponent.Builder args(@NotNull @NotNull ComponentLike @NotNull ... args) {
            Objects.requireNonNull(args, "args");
            if (args.length == 0) {
                return this.args(Collections.emptyList());
            }
            return this.args(Arrays.asList(args));
        }

        @Override
        @NotNull
        public TranslatableComponent.Builder args(@NotNull List<? extends ComponentLike> args) {
            this.args = ComponentLike.asComponents(Objects.requireNonNull(args, "args"));
            return this;
        }

        @Override
        @NotNull
        public TranslatableComponent.Builder fallback(@Nullable String fallback) {
            this.fallback = fallback;
            return this;
        }

        @Override
        @NotNull
        public TranslatableComponent build() {
            if (this.key == null) {
                throw new IllegalStateException("key must be set");
            }
            return TranslatableComponentImpl.create(this.children, this.buildStyle(), this.key, this.fallback, this.args);
        }
    }
}

