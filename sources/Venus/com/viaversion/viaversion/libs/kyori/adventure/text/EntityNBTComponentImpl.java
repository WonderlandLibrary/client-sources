/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.text.AbstractNBTComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.EntityNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class EntityNBTComponentImpl
extends NBTComponentImpl<EntityNBTComponent, EntityNBTComponent.Builder>
implements EntityNBTComponent {
    private final String selector;

    static EntityNBTComponent create(@NotNull List<? extends ComponentLike> list, @NotNull Style style, String string, boolean bl, @Nullable ComponentLike componentLike, String string2) {
        return new EntityNBTComponentImpl(ComponentLike.asComponents(list, IS_NOT_EMPTY), Objects.requireNonNull(style, "style"), Objects.requireNonNull(string, "nbtPath"), bl, ComponentLike.unbox(componentLike), Objects.requireNonNull(string2, "selector"));
    }

    EntityNBTComponentImpl(@NotNull List<Component> list, @NotNull Style style, String string, boolean bl, @Nullable Component component, String string2) {
        super(list, style, string, bl, component);
        this.selector = string2;
    }

    @Override
    @NotNull
    public EntityNBTComponent nbtPath(@NotNull String string) {
        if (Objects.equals(this.nbtPath, string)) {
            return this;
        }
        return EntityNBTComponentImpl.create(this.children, this.style, string, this.interpret, this.separator, this.selector);
    }

    @Override
    @NotNull
    public EntityNBTComponent interpret(boolean bl) {
        if (this.interpret == bl) {
            return this;
        }
        return EntityNBTComponentImpl.create(this.children, this.style, this.nbtPath, bl, this.separator, this.selector);
    }

    @Override
    @Nullable
    public Component separator() {
        return this.separator;
    }

    @Override
    @NotNull
    public EntityNBTComponent separator(@Nullable ComponentLike componentLike) {
        return EntityNBTComponentImpl.create(this.children, this.style, this.nbtPath, this.interpret, componentLike, this.selector);
    }

    @Override
    @NotNull
    public String selector() {
        return this.selector;
    }

    @Override
    @NotNull
    public EntityNBTComponent selector(@NotNull String string) {
        if (Objects.equals(this.selector, string)) {
            return this;
        }
        return EntityNBTComponentImpl.create(this.children, this.style, this.nbtPath, this.interpret, this.separator, string);
    }

    @Override
    @NotNull
    public EntityNBTComponent children(@NotNull List<? extends ComponentLike> list) {
        return EntityNBTComponentImpl.create(list, this.style, this.nbtPath, this.interpret, this.separator, this.selector);
    }

    @Override
    @NotNull
    public EntityNBTComponent style(@NotNull Style style) {
        return EntityNBTComponentImpl.create(this.children, style, this.nbtPath, this.interpret, this.separator, this.selector);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof EntityNBTComponent)) {
            return true;
        }
        if (!super.equals(object)) {
            return true;
        }
        EntityNBTComponentImpl entityNBTComponentImpl = (EntityNBTComponentImpl)object;
        return Objects.equals(this.selector, entityNBTComponentImpl.selector());
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = 31 * n + this.selector.hashCode();
        return n;
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @Override
    public @NotNull EntityNBTComponent.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @Override
    @NotNull
    public NBTComponent separator(@Nullable ComponentLike componentLike) {
        return this.separator(componentLike);
    }

    @Override
    @NotNull
    public NBTComponent interpret(boolean bl) {
        return this.interpret(bl);
    }

    @Override
    @NotNull
    public NBTComponent nbtPath(@NotNull String string) {
        return this.nbtPath(string);
    }

    @Override
    public @NotNull ComponentBuilder toBuilder() {
        return this.toBuilder();
    }

    @Override
    public @NotNull Buildable.Builder toBuilder() {
        return this.toBuilder();
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

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class BuilderImpl
    extends AbstractNBTComponentBuilder<EntityNBTComponent, EntityNBTComponent.Builder>
    implements EntityNBTComponent.Builder {
        @Nullable
        private String selector;

        BuilderImpl() {
        }

        BuilderImpl(@NotNull EntityNBTComponent entityNBTComponent) {
            super(entityNBTComponent);
            this.selector = entityNBTComponent.selector();
        }

        @Override
        public @NotNull EntityNBTComponent.Builder selector(@NotNull String string) {
            this.selector = Objects.requireNonNull(string, "selector");
            return this;
        }

        @Override
        @NotNull
        public EntityNBTComponent build() {
            if (this.nbtPath == null) {
                throw new IllegalStateException("nbt path must be set");
            }
            if (this.selector == null) {
                throw new IllegalStateException("selector must be set");
            }
            return EntityNBTComponentImpl.create(this.children, this.buildStyle(), this.nbtPath, this.interpret, this.separator, this.selector);
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

