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
import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class ScoreComponentImpl
extends AbstractComponent
implements ScoreComponent {
    private final String name;
    private final String objective;
    @Deprecated
    @Nullable
    private final String value;

    static ScoreComponent create(@NotNull List<? extends ComponentLike> children, @NotNull Style style, @NotNull String name, @NotNull String objective, @Nullable String value) {
        return new ScoreComponentImpl(ComponentLike.asComponents(children, IS_NOT_EMPTY), Objects.requireNonNull(style, "style"), Objects.requireNonNull(name, "name"), Objects.requireNonNull(objective, "objective"), value);
    }

    ScoreComponentImpl(@NotNull List<Component> children, @NotNull Style style, @NotNull String name, @NotNull String objective, @Nullable String value) {
        super(children, style);
        this.name = name;
        this.objective = objective;
        this.value = value;
    }

    @Override
    @NotNull
    public String name() {
        return this.name;
    }

    @Override
    @NotNull
    public ScoreComponent name(@NotNull String name) {
        if (Objects.equals(this.name, name)) {
            return this;
        }
        return ScoreComponentImpl.create(this.children, this.style, name, this.objective, this.value);
    }

    @Override
    @NotNull
    public String objective() {
        return this.objective;
    }

    @Override
    @NotNull
    public ScoreComponent objective(@NotNull String objective) {
        if (Objects.equals(this.objective, objective)) {
            return this;
        }
        return ScoreComponentImpl.create(this.children, this.style, this.name, objective, this.value);
    }

    @Override
    @Deprecated
    @Nullable
    public String value() {
        return this.value;
    }

    @Override
    @Deprecated
    @NotNull
    public ScoreComponent value(@Nullable String value) {
        if (Objects.equals(this.value, value)) {
            return this;
        }
        return ScoreComponentImpl.create(this.children, this.style, this.name, this.objective, value);
    }

    @Override
    @NotNull
    public ScoreComponent children(@NotNull List<? extends ComponentLike> children) {
        return ScoreComponentImpl.create(children, this.style, this.name, this.objective, this.value);
    }

    @Override
    @NotNull
    public ScoreComponent style(@NotNull Style style) {
        return ScoreComponentImpl.create(this.children, style, this.name, this.objective, this.value);
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScoreComponent)) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        ScoreComponent that = (ScoreComponent)other;
        return Objects.equals(this.name, that.name()) && Objects.equals(this.objective, that.objective()) && Objects.equals(this.value, that.value());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + this.name.hashCode();
        result = 31 * result + this.objective.hashCode();
        result = 31 * result + Objects.hashCode(this.value);
        return result;
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @Override
    @NotNull
    public ScoreComponent.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static final class BuilderImpl
    extends AbstractComponentBuilder<ScoreComponent, ScoreComponent.Builder>
    implements ScoreComponent.Builder {
        @Nullable
        private String name;
        @Nullable
        private String objective;
        @Nullable
        private String value;

        BuilderImpl() {
        }

        BuilderImpl(@NotNull ScoreComponent component) {
            super(component);
            this.name = component.name();
            this.objective = component.objective();
            this.value = component.value();
        }

        @Override
        @NotNull
        public ScoreComponent.Builder name(@NotNull String name) {
            this.name = Objects.requireNonNull(name, "name");
            return this;
        }

        @Override
        @NotNull
        public ScoreComponent.Builder objective(@NotNull String objective) {
            this.objective = Objects.requireNonNull(objective, "objective");
            return this;
        }

        @Override
        @Deprecated
        @NotNull
        public ScoreComponent.Builder value(@Nullable String value) {
            this.value = value;
            return this;
        }

        @Override
        @NotNull
        public ScoreComponent build() {
            if (this.name == null) {
                throw new IllegalStateException("name must be set");
            }
            if (this.objective == null) {
                throw new IllegalStateException("objective must be set");
            }
            return ScoreComponentImpl.create(this.children, this.buildStyle(), this.name, this.objective, this.value);
        }
    }
}

