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
import com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class SelectorComponentImpl
extends AbstractComponent
implements SelectorComponent {
    private final String pattern;
    @Nullable
    private final Component separator;

    static SelectorComponent create(@NotNull List<? extends ComponentLike> list, @NotNull Style style, @NotNull String string, @Nullable ComponentLike componentLike) {
        return new SelectorComponentImpl(ComponentLike.asComponents(list, IS_NOT_EMPTY), Objects.requireNonNull(style, "style"), Objects.requireNonNull(string, "pattern"), ComponentLike.unbox(componentLike));
    }

    SelectorComponentImpl(@NotNull List<Component> list, @NotNull Style style, @NotNull String string, @Nullable Component component) {
        super(list, style);
        this.pattern = string;
        this.separator = component;
    }

    @Override
    @NotNull
    public String pattern() {
        return this.pattern;
    }

    @Override
    @NotNull
    public SelectorComponent pattern(@NotNull String string) {
        if (Objects.equals(this.pattern, string)) {
            return this;
        }
        return SelectorComponentImpl.create(this.children, this.style, string, this.separator);
    }

    @Override
    @Nullable
    public Component separator() {
        return this.separator;
    }

    @Override
    @NotNull
    public SelectorComponent separator(@Nullable ComponentLike componentLike) {
        return SelectorComponentImpl.create(this.children, this.style, this.pattern, componentLike);
    }

    @Override
    @NotNull
    public SelectorComponent children(@NotNull List<? extends ComponentLike> list) {
        return SelectorComponentImpl.create(list, this.style, this.pattern, this.separator);
    }

    @Override
    @NotNull
    public SelectorComponent style(@NotNull Style style) {
        return SelectorComponentImpl.create(this.children, style, this.pattern, this.separator);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof SelectorComponent)) {
            return true;
        }
        if (!super.equals(object)) {
            return true;
        }
        SelectorComponent selectorComponent = (SelectorComponent)object;
        return Objects.equals(this.pattern, selectorComponent.pattern()) && Objects.equals(this.separator, selectorComponent.separator());
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = 31 * n + this.pattern.hashCode();
        n = 31 * n + Objects.hashCode(this.separator);
        return n;
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @Override
    @NotNull
    public SelectorComponent.Builder toBuilder() {
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
    extends AbstractComponentBuilder<SelectorComponent, SelectorComponent.Builder>
    implements SelectorComponent.Builder {
        @Nullable
        private String pattern;
        @Nullable
        private Component separator;

        BuilderImpl() {
        }

        BuilderImpl(@NotNull SelectorComponent selectorComponent) {
            super(selectorComponent);
            this.pattern = selectorComponent.pattern();
            this.separator = selectorComponent.separator();
        }

        @Override
        @NotNull
        public SelectorComponent.Builder pattern(@NotNull String string) {
            this.pattern = Objects.requireNonNull(string, "pattern");
            return this;
        }

        @Override
        @NotNull
        public SelectorComponent.Builder separator(@Nullable ComponentLike componentLike) {
            this.separator = ComponentLike.unbox(componentLike);
            return this;
        }

        @Override
        @NotNull
        public SelectorComponent build() {
            if (this.pattern == null) {
                throw new IllegalStateException("pattern must be set");
            }
            return SelectorComponentImpl.create(this.children, this.buildStyle(), this.pattern, this.separator);
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

