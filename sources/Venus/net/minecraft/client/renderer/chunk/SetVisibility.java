/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.chunk;

import java.util.Set;
import net.minecraft.util.Direction;

public class SetVisibility {
    private static final int COUNT_FACES = Direction.values().length;
    private long bits;

    public void setManyVisible(Set<Direction> set) {
        for (Direction direction : set) {
            for (Direction direction2 : set) {
                this.setVisible(direction, direction2, false);
            }
        }
    }

    public void setVisible(Direction direction, Direction direction2, boolean bl) {
        this.setBit(direction.ordinal() + direction2.ordinal() * COUNT_FACES, bl);
        this.setBit(direction2.ordinal() + direction.ordinal() * COUNT_FACES, bl);
    }

    public void setAllVisible(boolean bl) {
        this.bits = bl ? -1L : 0L;
    }

    public boolean isVisible(Direction direction, Direction direction2) {
        return this.getBit(direction.ordinal() + direction2.ordinal() * COUNT_FACES);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(' ');
        for (Direction direction : Direction.values()) {
            stringBuilder.append(' ').append(direction.toString().toUpperCase().charAt(0));
        }
        stringBuilder.append('\n');
        for (Direction direction : Direction.values()) {
            stringBuilder.append(direction.toString().toUpperCase().charAt(0));
            for (Direction direction2 : Direction.values()) {
                if (direction == direction2) {
                    stringBuilder.append("  ");
                    continue;
                }
                boolean bl = this.isVisible(direction, direction2);
                stringBuilder.append(' ').append(bl ? (char)'Y' : 'n');
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    private boolean getBit(int n) {
        return (this.bits & (long)(1 << n)) != 0L;
    }

    private void setBit(int n, boolean bl) {
        if (bl) {
            this.setBit(n);
        } else {
            this.clearBit(n);
        }
    }

    private void setBit(int n) {
        this.bits |= (long)(1 << n);
    }

    private void clearBit(int n) {
        this.bits &= (long)(~(1 << n));
    }
}

