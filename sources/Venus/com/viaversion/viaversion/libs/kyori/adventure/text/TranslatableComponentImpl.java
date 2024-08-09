/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class TranslatableComponentImpl
extends AbstractComponent
implements TranslatableComponent {
    private final String key;
    @Nullable
    private final String fallback;
    private final List<Component> args;

    static TranslatableComponent create(@NotNull List<Component> list, @NotNull Style style, @NotNull String string, @Nullable String string2, @NotNull @NotNull ComponentLike @NotNull [] componentLikeArray) {
        Objects.requireNonNull(componentLikeArray, "args");
        return TranslatableComponentImpl.create(list, style, string, string2, Arrays.asList(componentLikeArray));
    }

    static TranslatableComponent create(@NotNull List<? extends ComponentLike> list, @NotNull Style style, @NotNull String string, @Nullable String string2, @NotNull List<? extends ComponentLike> list2) {
        return new TranslatableComponentImpl(ComponentLike.asComponents(list, IS_NOT_EMPTY), Objects.requireNonNull(style, "style"), Objects.requireNonNull(string, "key"), string2, ComponentLike.asComponents(list2));
    }

    TranslatableComponentImpl(@NotNull List<Component> list, @NotNull Style style, @NotNull String string, @Nullable String string2, @NotNull List<Component> list2) {
        super(list, style);
        this.key = string;
        this.fallback = string2;
        this.args = list2;
    }

    @Override
    @NotNull
    public String key() {
        return this.key;
    }

    @Override
    @NotNull
    public TranslatableComponent key(@NotNull String string) {
        if (Objects.equals(this.key, string)) {
            return this;
        }
        return TranslatableComponentImpl.create(this.children, this.style, string, this.fallback, this.args);
    }

    @Override
    @NotNull
    public List<Component> args() {
        return this.args;
    }

    @Override
    @NotNull
    public TranslatableComponent args(@NotNull @NotNull ComponentLike @NotNull ... componentLikeArray) {
        return TranslatableComponentImpl.create(this.children, this.style, this.key, this.fallback, componentLikeArray);
    }

    @Override
    @NotNull
    public TranslatableComponent args(@NotNull List<? extends ComponentLike> list) {
        return TranslatableComponentImpl.create(this.children, this.style, this.key, this.fallback, list);
    }

    @Override
    @Nullable
    public String fallback() {
        return this.fallback;
    }

    @Override
    @NotNull
    public TranslatableComponent fallback(@Nullable String string) {
        return TranslatableComponentImpl.create(this.children, this.style, this.key, string, this.args);
    }

    @Override
    @NotNull
    public TranslatableComponent children(@NotNull List<? extends ComponentLike> list) {
        return TranslatableComponentImpl.create(list, this.style, this.key, this.fallback, this.args);
    }

    @Override
    @NotNull
    public TranslatableComponent style(@NotNull Style style) {
        return TranslatableComponentImpl.create(this.children, style, this.key, this.fallback, this.args);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof TranslatableComponent)) {
            return true;
        }
        if (!super.equals(object)) {
            return true;
        }
        TranslatableComponent translatableComponent = (TranslatableComponent)object;
        return Objects.equals(this.key, translatableComponent.key()) && Objects.equals(this.fallback, translatableComponent.fallback()) && Objects.equals(this.args, translatableComponent.args());
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = 31 * n + this.key.hashCode();
        n = 31 * n + Objects.hashCode(this.fallback);
        n = 31 * n + this.args.hashCode();
        return n;
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

    @Override
    @NotNull
    public Component style(@NotNull Style style) {
        return this.style(style);
    }

    @Override
    @NotNull
    public Component children(@NotNull List list) {
        return this.children(list);
    }

    @Override
    @NotNull
    public ComponentBuilder toBuilder() {
        return this.toBuilder();
    }

    @Override
    @NotNull
    public Buildable.Builder toBuilder() {
        return this.toBuilder();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
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

        BuilderImpl(@NotNull TranslatableComponent translatableComponent) {
            super(translatableComponent);
            this.key = translatableComponent.key();
            this.args = translatableComponent.args();
            this.fallback = translatableComponent.fallback();
        }

        @Override
        @NotNull
        public TranslatableComponent.Builder key(@NotNull String string) {
            this.key = string;
            return this;
        }

        @Override
        @NotNull
        public TranslatableComponent.Builder args(@NotNull ComponentBuilder<?, ?> componentBuilder) {
            return this.args(Collections.singletonList(Objects.requireNonNull(componentBuilder, "arg").build()));
        }

        @Override
        @NotNull
        public TranslatableComponent.Builder args(@NotNull @NotNull ComponentBuilder<?, ?> @NotNull ... componentBuilderArray) {
            Objects.requireNonNull(componentBuilderArray, "args");
            if (componentBuilderArray.length == 0) {
                return this.args(Collections.emptyList());
            }
            return this.args(Stream.of(componentBuilderArray).map(ComponentBuilder::build).collect(Collectors.toList()));
        }

        @Override
        @NotNull
        public TranslatableComponent.Builder args(@NotNull Component component) {
            return this.args(Collections.singletonList(Objects.requireNonNull(component, "arg")));
        }

        @Override
        @NotNull
        public TranslatableComponent.Builder args(@NotNull @NotNull ComponentLike @NotNull ... componentLikeArray) {
            Objects.requireNonNull(componentLikeArray, "args");
            if (componentLikeArray.length == 0) {
                return this.args(Collections.emptyList());
            }
            return this.args(Arrays.asList(componentLikeArray));
        }

        @Override
        @NotNull
        public TranslatableComponent.Builder args(@NotNull List<? extends ComponentLike> list) {
            this.args = ComponentLike.asComponents(Objects.requireNonNull(list, "args"));
            return this;
        }

        @Override
        @NotNull
        public TranslatableComponent.Builder fallback(@Nullable String string) {
            this.fallback = string;
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

        @Override
        @NotNull
        public BuildableComponent build() {
            return this.build();
        }

        @Override
        @NotNull
        public Object build() {
            return this.build();
        }
    }
}

