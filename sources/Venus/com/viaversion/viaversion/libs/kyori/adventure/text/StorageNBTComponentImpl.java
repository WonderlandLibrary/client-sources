/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.AbstractNBTComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.StorageNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class StorageNBTComponentImpl
extends NBTComponentImpl<StorageNBTComponent, StorageNBTComponent.Builder>
implements StorageNBTComponent {
    private final Key storage;

    @NotNull
    static StorageNBTComponent create(@NotNull List<? extends ComponentLike> list, @NotNull Style style, String string, boolean bl, @Nullable ComponentLike componentLike, @NotNull Key key) {
        return new StorageNBTComponentImpl(ComponentLike.asComponents(list, IS_NOT_EMPTY), Objects.requireNonNull(style, "style"), Objects.requireNonNull(string, "nbtPath"), bl, ComponentLike.unbox(componentLike), Objects.requireNonNull(key, "storage"));
    }

    StorageNBTComponentImpl(@NotNull List<Component> list, @NotNull Style style, String string, boolean bl, @Nullable Component component, Key key) {
        super(list, style, string, bl, component);
        this.storage = key;
    }

    @Override
    @NotNull
    public StorageNBTComponent nbtPath(@NotNull String string) {
        if (Objects.equals(this.nbtPath, string)) {
            return this;
        }
        return StorageNBTComponentImpl.create(this.children, this.style, string, this.interpret, this.separator, this.storage);
    }

    @Override
    @NotNull
    public StorageNBTComponent interpret(boolean bl) {
        if (this.interpret == bl) {
            return this;
        }
        return StorageNBTComponentImpl.create(this.children, this.style, this.nbtPath, bl, this.separator, this.storage);
    }

    @Override
    @Nullable
    public Component separator() {
        return this.separator;
    }

    @Override
    @NotNull
    public StorageNBTComponent separator(@Nullable ComponentLike componentLike) {
        return StorageNBTComponentImpl.create(this.children, this.style, this.nbtPath, this.interpret, componentLike, this.storage);
    }

    @Override
    @NotNull
    public Key storage() {
        return this.storage;
    }

    @Override
    @NotNull
    public StorageNBTComponent storage(@NotNull Key key) {
        if (Objects.equals(this.storage, key)) {
            return this;
        }
        return StorageNBTComponentImpl.create(this.children, this.style, this.nbtPath, this.interpret, this.separator, key);
    }

    @Override
    @NotNull
    public StorageNBTComponent children(@NotNull List<? extends ComponentLike> list) {
        return StorageNBTComponentImpl.create(list, this.style, this.nbtPath, this.interpret, this.separator, this.storage);
    }

    @Override
    @NotNull
    public StorageNBTComponent style(@NotNull Style style) {
        return StorageNBTComponentImpl.create(this.children, style, this.nbtPath, this.interpret, this.separator, this.storage);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof StorageNBTComponent)) {
            return true;
        }
        if (!super.equals(object)) {
            return true;
        }
        StorageNBTComponentImpl storageNBTComponentImpl = (StorageNBTComponentImpl)object;
        return Objects.equals(this.storage, storageNBTComponentImpl.storage());
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = 31 * n + this.storage.hashCode();
        return n;
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @Override
    public @NotNull StorageNBTComponent.Builder toBuilder() {
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
    static class BuilderImpl
    extends AbstractNBTComponentBuilder<StorageNBTComponent, StorageNBTComponent.Builder>
    implements StorageNBTComponent.Builder {
        @Nullable
        private Key storage;

        BuilderImpl() {
        }

        BuilderImpl(@NotNull StorageNBTComponent storageNBTComponent) {
            super(storageNBTComponent);
            this.storage = storageNBTComponent.storage();
        }

        @Override
        public @NotNull StorageNBTComponent.Builder storage(@NotNull Key key) {
            this.storage = Objects.requireNonNull(key, "storage");
            return this;
        }

        @Override
        @NotNull
        public StorageNBTComponent build() {
            if (this.nbtPath == null) {
                throw new IllegalStateException("nbt path must be set");
            }
            if (this.storage == null) {
                throw new IllegalStateException("storage must be set");
            }
            return StorageNBTComponentImpl.create(this.children, this.buildStyle(), this.nbtPath, this.interpret, this.separator, this.storage);
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

