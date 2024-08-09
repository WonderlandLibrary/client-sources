/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public enum BlockDir {
    DOWN(Direction.DOWN),
    UP(Direction.UP),
    NORTH(Direction.NORTH),
    SOUTH(Direction.SOUTH),
    WEST(Direction.WEST),
    EAST(Direction.EAST),
    NORTH_WEST(Direction.NORTH, Direction.WEST),
    NORTH_EAST(Direction.NORTH, Direction.EAST),
    SOUTH_WEST(Direction.SOUTH, Direction.WEST),
    SOUTH_EAST(Direction.SOUTH, Direction.EAST),
    DOWN_NORTH(Direction.DOWN, Direction.NORTH),
    DOWN_SOUTH(Direction.DOWN, Direction.SOUTH),
    UP_NORTH(Direction.UP, Direction.NORTH),
    UP_SOUTH(Direction.UP, Direction.SOUTH),
    DOWN_WEST(Direction.DOWN, Direction.WEST),
    DOWN_EAST(Direction.DOWN, Direction.EAST),
    UP_WEST(Direction.UP, Direction.WEST),
    UP_EAST(Direction.UP, Direction.EAST);

    private Direction facing1;
    private Direction facing2;

    private BlockDir(Direction direction) {
        this.facing1 = direction;
    }

    private BlockDir(Direction direction, Direction direction2) {
        this.facing1 = direction;
        this.facing2 = direction2;
    }

    public Direction getFacing1() {
        return this.facing1;
    }

    public Direction getFacing2() {
        return this.facing2;
    }

    BlockPos offset(BlockPos blockPos) {
        blockPos = blockPos.offset(this.facing1, 1);
        if (this.facing2 != null) {
            blockPos = blockPos.offset(this.facing2, 1);
        }
        return blockPos;
    }

    public int getOffsetX() {
        int n = this.facing1.getXOffset();
        if (this.facing2 != null) {
            n += this.facing2.getXOffset();
        }
        return n;
    }

    public int getOffsetY() {
        int n = this.facing1.getYOffset();
        if (this.facing2 != null) {
            n += this.facing2.getYOffset();
        }
        return n;
    }

    public int getOffsetZ() {
        int n = this.facing1.getZOffset();
        if (this.facing2 != null) {
            n += this.facing2.getZOffset();
        }
        return n;
    }

    public boolean isDouble() {
        return this.facing2 != null;
    }
}

