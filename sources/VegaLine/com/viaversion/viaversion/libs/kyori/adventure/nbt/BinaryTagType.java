/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.NumberBinaryTag;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BinaryTagType<T extends BinaryTag>
implements Predicate<BinaryTagType<? extends BinaryTag>> {
    private static final List<BinaryTagType<? extends BinaryTag>> TYPES = new ArrayList<BinaryTagType<? extends BinaryTag>>();

    public abstract byte id();

    abstract boolean numeric();

    @NotNull
    public abstract T read(@NotNull DataInput var1) throws IOException;

    public abstract void write(@NotNull T var1, @NotNull DataOutput var2) throws IOException;

    static <T extends BinaryTag> void writeUntyped(BinaryTagType<? extends BinaryTag> type2, T tag, DataOutput output) throws IOException {
        type2.write(tag, output);
    }

    @NotNull
    static BinaryTagType<? extends BinaryTag> binaryTagType(byte id) {
        for (int i = 0; i < TYPES.size(); ++i) {
            BinaryTagType<? extends BinaryTag> type2 = TYPES.get(i);
            if (type2.id() != id) continue;
            return type2;
        }
        throw new IllegalArgumentException(String.valueOf(id));
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @NotNull
    static BinaryTagType<? extends BinaryTag> of(byte id) {
        return BinaryTagType.binaryTagType(id);
    }

    @NotNull
    static <T extends BinaryTag> BinaryTagType<T> register(Class<T> type2, byte id, Reader<T> reader, @Nullable Writer<T> writer) {
        return BinaryTagType.register(new Impl<T>(type2, id, reader, writer));
    }

    @NotNull
    static <T extends NumberBinaryTag> BinaryTagType<T> registerNumeric(Class<T> type2, byte id, Reader<T> reader, Writer<T> writer) {
        return BinaryTagType.register(new Impl.Numeric<T>(type2, id, reader, writer));
    }

    private static <T extends BinaryTag, Y extends BinaryTagType<T>> Y register(Y type2) {
        TYPES.add(type2);
        return type2;
    }

    @Override
    public boolean test(BinaryTagType<? extends BinaryTag> that) {
        return this == that || this.numeric() && that.numeric();
    }

    static class Impl<T extends BinaryTag>
    extends BinaryTagType<T> {
        final Class<T> type;
        final byte id;
        private final Reader<T> reader;
        @Nullable
        private final Writer<T> writer;

        Impl(Class<T> type2, byte id, Reader<T> reader, @Nullable Writer<T> writer) {
            this.type = type2;
            this.id = id;
            this.reader = reader;
            this.writer = writer;
        }

        @Override
        @NotNull
        public final T read(@NotNull DataInput input) throws IOException {
            return this.reader.read(input);
        }

        @Override
        public final void write(@NotNull T tag, @NotNull DataOutput output) throws IOException {
            if (this.writer != null) {
                this.writer.write(tag, output);
            }
        }

        @Override
        public final byte id() {
            return this.id;
        }

        @Override
        boolean numeric() {
            return false;
        }

        public String toString() {
            return BinaryTagType.class.getSimpleName() + '[' + this.type.getSimpleName() + " " + this.id + "]";
        }

        static class Numeric<T extends BinaryTag>
        extends Impl<T> {
            Numeric(Class<T> type2, byte id, Reader<T> reader, @Nullable Writer<T> writer) {
                super(type2, id, reader, writer);
            }

            @Override
            boolean numeric() {
                return true;
            }

            @Override
            public String toString() {
                return BinaryTagType.class.getSimpleName() + '[' + this.type.getSimpleName() + " " + this.id + " (numeric)]";
            }
        }
    }

    static interface Writer<T extends BinaryTag> {
        public void write(@NotNull T var1, @NotNull DataOutput var2) throws IOException;
    }

    static interface Reader<T extends BinaryTag> {
        @NotNull
        public T read(@NotNull DataInput var1) throws IOException;
    }
}

