package dev.luvbeeq.baritone.utils.pathing;

/**
 * @author Brady
 * @since 8/4/2018
 */
public enum PathingBlockType {

    AIR(0b00),
    WATER(0b01),
    AVOID(0b10),
    SOLID(0b11);

    private final boolean[] bits;

    PathingBlockType(int bits) {
        this.bits = new boolean[]{
                (bits & 0b10) != 0,
                (bits & 0b01) != 0
        };
    }

    public final boolean[] getBits() {
        return this.bits;
    }

    public static PathingBlockType fromBits(boolean b1, boolean b2) {
        return b1 ? b2 ? SOLID : AVOID : b2 ? WATER : AIR;
    }
}
