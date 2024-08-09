/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.ScopedComponent;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.regex.Matcher;
import java.util.stream.Stream;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface BlockNBTComponent
extends NBTComponent<BlockNBTComponent, Builder>,
ScopedComponent<BlockNBTComponent> {
    @NotNull
    public Pos pos();

    @Contract(pure=true)
    @NotNull
    public BlockNBTComponent pos(@NotNull Pos var1);

    @Contract(pure=true)
    @NotNull
    default public BlockNBTComponent localPos(double d, double d2, double d3) {
        return this.pos(LocalPos.localPos(d, d2, d3));
    }

    @Contract(pure=true)
    @NotNull
    default public BlockNBTComponent worldPos(@NotNull WorldPos.Coordinate coordinate, @NotNull WorldPos.Coordinate coordinate2, @NotNull WorldPos.Coordinate coordinate3) {
        return this.pos(WorldPos.worldPos(coordinate, coordinate2, coordinate3));
    }

    @Contract(pure=true)
    @NotNull
    default public BlockNBTComponent absoluteWorldPos(int n, int n2, int n3) {
        return this.worldPos(WorldPos.Coordinate.absolute(n), WorldPos.Coordinate.absolute(n2), WorldPos.Coordinate.absolute(n3));
    }

    @Contract(pure=true)
    @NotNull
    default public BlockNBTComponent relativeWorldPos(int n, int n2, int n3) {
        return this.worldPos(WorldPos.Coordinate.relative(n), WorldPos.Coordinate.relative(n2), WorldPos.Coordinate.relative(n3));
    }

    @Override
    @NotNull
    default public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(Stream.of(ExaminableProperty.of("pos", this.pos())), NBTComponent.super.examinableProperties());
    }

    public static interface WorldPos
    extends Pos {
        @NotNull
        public static WorldPos worldPos(@NotNull Coordinate coordinate, @NotNull Coordinate coordinate2, @NotNull Coordinate coordinate3) {
            return new BlockNBTComponentImpl.WorldPosImpl(coordinate, coordinate2, coordinate3);
        }

        @Deprecated
        @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
        @NotNull
        public static WorldPos of(@NotNull Coordinate coordinate, @NotNull Coordinate coordinate2, @NotNull Coordinate coordinate3) {
            return new BlockNBTComponentImpl.WorldPosImpl(coordinate, coordinate2, coordinate3);
        }

        @NotNull
        public Coordinate x();

        @NotNull
        public Coordinate y();

        @NotNull
        public Coordinate z();

        public static interface Coordinate
        extends Examinable {
            @NotNull
            public static Coordinate absolute(int n) {
                return Coordinate.coordinate(n, Type.ABSOLUTE);
            }

            @NotNull
            public static Coordinate relative(int n) {
                return Coordinate.coordinate(n, Type.RELATIVE);
            }

            @NotNull
            public static Coordinate coordinate(int n, @NotNull Type type) {
                return new BlockNBTComponentImpl.WorldPosImpl.CoordinateImpl(n, type);
            }

            @Deprecated
            @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
            @NotNull
            public static Coordinate of(int n, @NotNull Type type) {
                return new BlockNBTComponentImpl.WorldPosImpl.CoordinateImpl(n, type);
            }

            public int value();

            @NotNull
            public Type type();

            public static enum Type {
                ABSOLUTE,
                RELATIVE;

            }
        }
    }

    public static interface LocalPos
    extends Pos {
        @NotNull
        public static LocalPos localPos(double d, double d2, double d3) {
            return new BlockNBTComponentImpl.LocalPosImpl(d, d2, d3);
        }

        @Deprecated
        @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
        @NotNull
        public static LocalPos of(double d, double d2, double d3) {
            return new BlockNBTComponentImpl.LocalPosImpl(d, d2, d3);
        }

        public double left();

        public double up();

        public double forwards();
    }

    public static interface Pos
    extends Examinable {
        @NotNull
        public static Pos fromString(@NotNull String string) throws IllegalArgumentException {
            Matcher matcher = BlockNBTComponentImpl.Tokens.LOCAL_PATTERN.matcher(string);
            if (matcher.matches()) {
                return LocalPos.localPos(Double.parseDouble(matcher.group(1)), Double.parseDouble(matcher.group(3)), Double.parseDouble(matcher.group(5)));
            }
            Matcher matcher2 = BlockNBTComponentImpl.Tokens.WORLD_PATTERN.matcher(string);
            if (matcher2.matches()) {
                return WorldPos.worldPos(BlockNBTComponentImpl.Tokens.deserializeCoordinate(matcher2.group(1), matcher2.group(2)), BlockNBTComponentImpl.Tokens.deserializeCoordinate(matcher2.group(3), matcher2.group(4)), BlockNBTComponentImpl.Tokens.deserializeCoordinate(matcher2.group(5), matcher2.group(6)));
            }
            throw new IllegalArgumentException("Cannot convert position specification '" + string + "' into a position");
        }

        @NotNull
        public String asString();
    }

    public static interface Builder
    extends NBTComponentBuilder<BlockNBTComponent, Builder> {
        @Contract(value="_ -> this")
        @NotNull
        public Builder pos(@NotNull Pos var1);

        @Contract(value="_, _, _ -> this")
        @NotNull
        default public Builder localPos(double d, double d2, double d3) {
            return this.pos(LocalPos.localPos(d, d2, d3));
        }

        @Contract(value="_, _, _ -> this")
        @NotNull
        default public Builder worldPos(@NotNull WorldPos.Coordinate coordinate, @NotNull WorldPos.Coordinate coordinate2, @NotNull WorldPos.Coordinate coordinate3) {
            return this.pos(WorldPos.worldPos(coordinate, coordinate2, coordinate3));
        }

        @Contract(value="_, _, _ -> this")
        @NotNull
        default public Builder absoluteWorldPos(int n, int n2, int n3) {
            return this.worldPos(WorldPos.Coordinate.absolute(n), WorldPos.Coordinate.absolute(n2), WorldPos.Coordinate.absolute(n3));
        }

        @Contract(value="_, _, _ -> this")
        @NotNull
        default public Builder relativeWorldPos(int n, int n2, int n3) {
            return this.worldPos(WorldPos.Coordinate.relative(n), WorldPos.Coordinate.relative(n2), WorldPos.Coordinate.relative(n3));
        }
    }
}

