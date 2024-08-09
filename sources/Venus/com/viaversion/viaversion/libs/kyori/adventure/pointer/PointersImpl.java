/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.pointer;

import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointer;
import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointers;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class PointersImpl
implements Pointers {
    static final Pointers EMPTY = new Pointers(){

        @Override
        @NotNull
        public <T> Optional<T> get(@NotNull Pointer<T> pointer) {
            return Optional.empty();
        }

        @Override
        public <T> boolean supports(@NotNull Pointer<T> pointer) {
            return true;
        }

        @Override
        public @NotNull Pointers.Builder toBuilder() {
            return new BuilderImpl();
        }

        public String toString() {
            return "EmptyPointers";
        }

        @Override
        public @NotNull Buildable.Builder toBuilder() {
            return this.toBuilder();
        }
    };
    private final Map<Pointer<?>, Supplier<?>> pointers;

    PointersImpl(@NotNull BuilderImpl builderImpl) {
        this.pointers = new HashMap(BuilderImpl.access$000(builderImpl));
    }

    @Override
    @NotNull
    public <T> Optional<T> get(@NotNull Pointer<T> pointer) {
        Objects.requireNonNull(pointer, "pointer");
        Supplier<?> supplier = this.pointers.get(pointer);
        if (supplier == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(supplier.get());
    }

    @Override
    public <T> boolean supports(@NotNull Pointer<T> pointer) {
        Objects.requireNonNull(pointer, "pointer");
        return this.pointers.containsKey(pointer);
    }

    @Override
    public @NotNull Pointers.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @Override
    public @NotNull Buildable.Builder toBuilder() {
        return this.toBuilder();
    }

    static Map access$100(PointersImpl pointersImpl) {
        return pointersImpl.pointers;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class BuilderImpl
    implements Pointers.Builder {
        private final Map<Pointer<?>, Supplier<?>> pointers;

        BuilderImpl() {
            this.pointers = new HashMap();
        }

        BuilderImpl(@NotNull PointersImpl pointersImpl) {
            this.pointers = new HashMap(PointersImpl.access$100(pointersImpl));
        }

        @Override
        @NotNull
        public <T> Pointers.Builder withDynamic(@NotNull Pointer<T> pointer, @NotNull Supplier<@Nullable T> supplier) {
            this.pointers.put(Objects.requireNonNull(pointer, "pointer"), Objects.requireNonNull(supplier, "value"));
            return this;
        }

        @Override
        @NotNull
        public Pointers build() {
            return new PointersImpl(this);
        }

        @Override
        @NotNull
        public Object build() {
            return this.build();
        }

        static Map access$000(BuilderImpl builderImpl) {
            return builderImpl.pointers;
        }
    }
}

