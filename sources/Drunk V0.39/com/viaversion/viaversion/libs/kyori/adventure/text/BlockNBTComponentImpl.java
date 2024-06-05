/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.util.ShadyPines;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class BlockNBTComponentImpl
extends NBTComponentImpl<BlockNBTComponent, BlockNBTComponent.Builder>
implements BlockNBTComponent {
    private final BlockNBTComponent.Pos pos;

    BlockNBTComponentImpl(@NotNull List<? extends ComponentLike> children, @NotNull Style style, String nbtPath, boolean interpret, @Nullable ComponentLike separator, @NotNull BlockNBTComponent.Pos pos) {
        super(children, style, nbtPath, interpret, separator);
        this.pos = pos;
    }

    @Override
    @NotNull
    public BlockNBTComponent nbtPath(@NotNull String nbtPath) {
        if (!Objects.equals(this.nbtPath, nbtPath)) return new BlockNBTComponentImpl(this.children, this.style, nbtPath, this.interpret, this.separator, this.pos);
        return this;
    }

    @Override
    @NotNull
    public BlockNBTComponent interpret(boolean interpret) {
        if (this.interpret != interpret) return new BlockNBTComponentImpl(this.children, this.style, this.nbtPath, interpret, this.separator, this.pos);
        return this;
    }

    @Override
    @Nullable
    public Component separator() {
        return this.separator;
    }

    @Override
    @NotNull
    public BlockNBTComponent separator(@Nullable ComponentLike separator) {
        return new BlockNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, separator, this.pos);
    }

    @Override
    @NotNull
    public BlockNBTComponent.Pos pos() {
        return this.pos;
    }

    @Override
    @NotNull
    public BlockNBTComponent pos(@NotNull BlockNBTComponent.Pos pos) {
        return new BlockNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, this.separator, pos);
    }

    @Override
    @NotNull
    public BlockNBTComponent children(@NotNull List<? extends ComponentLike> children) {
        return new BlockNBTComponentImpl(children, this.style, this.nbtPath, this.interpret, this.separator, this.pos);
    }

    @Override
    @NotNull
    public BlockNBTComponent style(@NotNull Style style) {
        return new BlockNBTComponentImpl(this.children, style, this.nbtPath, this.interpret, this.separator, this.pos);
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BlockNBTComponent)) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        BlockNBTComponent that = (BlockNBTComponent)other;
        return Objects.equals(this.pos, that.pos());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        return 31 * result + this.pos.hashCode();
    }

    @Override
    @NotNull
    protected Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
        return Stream.concat(Stream.of(ExaminableProperty.of("pos", this.pos)), super.examinablePropertiesWithoutChildren());
    }

    @Override
    public @NotNull BlockNBTComponent.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static final class Tokens {
        static final Pattern LOCAL_PATTERN = Pattern.compile("^\\^(\\d+(\\.\\d+)?) \\^(\\d+(\\.\\d+)?) \\^(\\d+(\\.\\d+)?)$");
        static final Pattern WORLD_PATTERN = Pattern.compile("^(~?)(\\d+) (~?)(\\d+) (~?)(\\d+)$");
        static final String LOCAL_SYMBOL = "^";
        static final String RELATIVE_SYMBOL = "~";
        static final String ABSOLUTE_SYMBOL = "";

        private Tokens() {
        }

        static BlockNBTComponent.WorldPos.Coordinate deserializeCoordinate(String prefix, String value) {
            int i = Integer.parseInt(value);
            if (prefix.equals(ABSOLUTE_SYMBOL)) {
                return BlockNBTComponent.WorldPos.Coordinate.absolute(i);
            }
            if (!prefix.equals(RELATIVE_SYMBOL)) throw new AssertionError();
            return BlockNBTComponent.WorldPos.Coordinate.relative(i);
        }

        static String serializeLocal(double value) {
            return LOCAL_SYMBOL + value;
        }

        static String serializeCoordinate(BlockNBTComponent.WorldPos.Coordinate coordinate) {
            String string;
            StringBuilder stringBuilder = new StringBuilder();
            if (coordinate.type() == BlockNBTComponent.WorldPos.Coordinate.Type.RELATIVE) {
                string = RELATIVE_SYMBOL;
                return stringBuilder.append(string).append(coordinate.value()).toString();
            }
            string = ABSOLUTE_SYMBOL;
            return stringBuilder.append(string).append(coordinate.value()).toString();
        }
    }

    static final class WorldPosImpl
    implements BlockNBTComponent.WorldPos {
        private final BlockNBTComponent.WorldPos.Coordinate x;
        private final BlockNBTComponent.WorldPos.Coordinate y;
        private final BlockNBTComponent.WorldPos.Coordinate z;

        WorldPosImpl(BlockNBTComponent.WorldPos.Coordinate x, BlockNBTComponent.WorldPos.Coordinate y, BlockNBTComponent.WorldPos.Coordinate z) {
            this.x = Objects.requireNonNull(x, "x");
            this.y = Objects.requireNonNull(y, "y");
            this.z = Objects.requireNonNull(z, "z");
        }

        @Override
        @NotNull
        public BlockNBTComponent.WorldPos.Coordinate x() {
            return this.x;
        }

        @Override
        @NotNull
        public BlockNBTComponent.WorldPos.Coordinate y() {
            return this.y;
        }

        @Override
        @NotNull
        public BlockNBTComponent.WorldPos.Coordinate z() {
            return this.z;
        }

        @Override
        @NotNull
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(ExaminableProperty.of("x", this.x), ExaminableProperty.of("y", this.y), ExaminableProperty.of("z", this.z));
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof BlockNBTComponent.WorldPos)) {
                return false;
            }
            BlockNBTComponent.WorldPos that = (BlockNBTComponent.WorldPos)other;
            if (!this.x.equals(that.x())) return false;
            if (!this.y.equals(that.y())) return false;
            if (!this.z.equals(that.z())) return false;
            return true;
        }

        public int hashCode() {
            int result = this.x.hashCode();
            result = 31 * result + this.y.hashCode();
            return 31 * result + this.z.hashCode();
        }

        public String toString() {
            return this.x.toString() + ' ' + this.y.toString() + ' ' + this.z.toString();
        }

        @Override
        @NotNull
        public String asString() {
            return Tokens.serializeCoordinate(this.x()) + ' ' + Tokens.serializeCoordinate(this.y()) + ' ' + Tokens.serializeCoordinate(this.z());
        }

        static final class CoordinateImpl
        implements BlockNBTComponent.WorldPos.Coordinate {
            private final int value;
            private final BlockNBTComponent.WorldPos.Coordinate.Type type;

            CoordinateImpl(int value, @NotNull BlockNBTComponent.WorldPos.Coordinate.Type type) {
                this.value = value;
                this.type = Objects.requireNonNull(type, "type");
            }

            @Override
            public int value() {
                return this.value;
            }

            @Override
            @NotNull
            public BlockNBTComponent.WorldPos.Coordinate.Type type() {
                return this.type;
            }

            @Override
            @NotNull
            public Stream<? extends ExaminableProperty> examinableProperties() {
                return Stream.of(ExaminableProperty.of("value", this.value), ExaminableProperty.of("type", (Object)this.type));
            }

            public boolean equals(@Nullable Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof BlockNBTComponent.WorldPos.Coordinate)) {
                    return false;
                }
                BlockNBTComponent.WorldPos.Coordinate that = (BlockNBTComponent.WorldPos.Coordinate)other;
                if (this.value() != that.value()) return false;
                if (this.type() != that.type()) return false;
                return true;
            }

            public int hashCode() {
                int result = this.value;
                return 31 * result + this.type.hashCode();
            }

            public String toString() {
                String string;
                StringBuilder stringBuilder = new StringBuilder();
                if (this.type == BlockNBTComponent.WorldPos.Coordinate.Type.RELATIVE) {
                    string = "~";
                    return stringBuilder.append(string).append(this.value).toString();
                }
                string = "";
                return stringBuilder.append(string).append(this.value).toString();
            }
        }
    }

    static final class LocalPosImpl
    implements BlockNBTComponent.LocalPos {
        private final double left;
        private final double up;
        private final double forwards;

        LocalPosImpl(double left, double up, double forwards) {
            this.left = left;
            this.up = up;
            this.forwards = forwards;
        }

        @Override
        public double left() {
            return this.left;
        }

        @Override
        public double up() {
            return this.up;
        }

        @Override
        public double forwards() {
            return this.forwards;
        }

        @Override
        @NotNull
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(ExaminableProperty.of("left", this.left), ExaminableProperty.of("up", this.up), ExaminableProperty.of("forwards", this.forwards));
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof BlockNBTComponent.LocalPos)) {
                return false;
            }
            BlockNBTComponent.LocalPos that = (BlockNBTComponent.LocalPos)other;
            if (!ShadyPines.equals(that.left(), this.left())) return false;
            if (!ShadyPines.equals(that.up(), this.up())) return false;
            if (!ShadyPines.equals(that.forwards(), this.forwards())) return false;
            return true;
        }

        public int hashCode() {
            int result = Double.hashCode(this.left);
            result = 31 * result + Double.hashCode(this.up);
            return 31 * result + Double.hashCode(this.forwards);
        }

        public String toString() {
            return String.format("^%f ^%f ^%f", this.left, this.up, this.forwards);
        }

        @Override
        @NotNull
        public String asString() {
            return Tokens.serializeLocal(this.left) + ' ' + Tokens.serializeLocal(this.up) + ' ' + Tokens.serializeLocal(this.forwards);
        }
    }

    static final class BuilderImpl
    extends NBTComponentImpl.BuilderImpl<BlockNBTComponent, BlockNBTComponent.Builder>
    implements BlockNBTComponent.Builder {
        @Nullable
        private BlockNBTComponent.Pos pos;

        BuilderImpl() {
        }

        BuilderImpl(@NotNull BlockNBTComponent component) {
            super(component);
            this.pos = component.pos();
        }

        @Override
        public @NotNull BlockNBTComponent.Builder pos(@NotNull BlockNBTComponent.Pos pos) {
            this.pos = pos;
            return this;
        }

        @Override
        @NotNull
        public BlockNBTComponent build() {
            if (this.nbtPath == null) {
                throw new IllegalStateException("nbt path must be set");
            }
            if (this.pos != null) return new BlockNBTComponentImpl(this.children, this.buildStyle(), this.nbtPath, this.interpret, this.separator, this.pos);
            throw new IllegalStateException("pos must be set");
        }
    }
}

