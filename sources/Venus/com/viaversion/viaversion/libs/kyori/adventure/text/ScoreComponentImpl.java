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
import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class ScoreComponentImpl
extends AbstractComponent
implements ScoreComponent {
    private final String name;
    private final String objective;
    @Deprecated
    @Nullable
    private final String value;

    static ScoreComponent create(@NotNull List<? extends ComponentLike> list, @NotNull Style style, @NotNull String string, @NotNull String string2, @Nullable String string3) {
        return new ScoreComponentImpl(ComponentLike.asComponents(list, IS_NOT_EMPTY), Objects.requireNonNull(style, "style"), Objects.requireNonNull(string, "name"), Objects.requireNonNull(string2, "objective"), string3);
    }

    ScoreComponentImpl(@NotNull List<Component> list, @NotNull Style style, @NotNull String string, @NotNull String string2, @Nullable String string3) {
        super(list, style);
        this.name = string;
        this.objective = string2;
        this.value = string3;
    }

    @Override
    @NotNull
    public String name() {
        return this.name;
    }

    @Override
    @NotNull
    public ScoreComponent name(@NotNull String string) {
        if (Objects.equals(this.name, string)) {
            return this;
        }
        return ScoreComponentImpl.create(this.children, this.style, string, this.objective, this.value);
    }

    @Override
    @NotNull
    public String objective() {
        return this.objective;
    }

    @Override
    @NotNull
    public ScoreComponent objective(@NotNull String string) {
        if (Objects.equals(this.objective, string)) {
            return this;
        }
        return ScoreComponentImpl.create(this.children, this.style, this.name, string, this.value);
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
    public ScoreComponent value(@Nullable String string) {
        if (Objects.equals(this.value, string)) {
            return this;
        }
        return ScoreComponentImpl.create(this.children, this.style, this.name, this.objective, string);
    }

    @Override
    @NotNull
    public ScoreComponent children(@NotNull List<? extends ComponentLike> list) {
        return ScoreComponentImpl.create(list, this.style, this.name, this.objective, this.value);
    }

    @Override
    @NotNull
    public ScoreComponent style(@NotNull Style style) {
        return ScoreComponentImpl.create(this.children, style, this.name, this.objective, this.value);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof ScoreComponent)) {
            return true;
        }
        if (!super.equals(object)) {
            return true;
        }
        ScoreComponent scoreComponent = (ScoreComponent)object;
        return Objects.equals(this.name, scoreComponent.name()) && Objects.equals(this.objective, scoreComponent.objective()) && Objects.equals(this.value, scoreComponent.value());
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = 31 * n + this.name.hashCode();
        n = 31 * n + this.objective.hashCode();
        n = 31 * n + Objects.hashCode(this.value);
        return n;
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

        BuilderImpl(@NotNull ScoreComponent scoreComponent) {
            super(scoreComponent);
            this.name = scoreComponent.name();
            this.objective = scoreComponent.objective();
            this.value = scoreComponent.value();
        }

        @Override
        @NotNull
        public ScoreComponent.Builder name(@NotNull String string) {
            this.name = Objects.requireNonNull(string, "name");
            return this;
        }

        @Override
        @NotNull
        public ScoreComponent.Builder objective(@NotNull String string) {
            this.objective = Objects.requireNonNull(string, "objective");
            return this;
        }

        @Override
        @Deprecated
        @NotNull
        public ScoreComponent.Builder value(@Nullable String string) {
            this.value = string;
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

