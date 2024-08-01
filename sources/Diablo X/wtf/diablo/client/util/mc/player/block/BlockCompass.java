package wtf.diablo.client.util.mc.player.block;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;

import java.util.HashMap;
import java.util.Map;

public enum BlockCompass {
    DOWN(0, new Vec3i(0, -1, 0), EnumFacing.DOWN),
    UP(1, new Vec3i(0, 1, 0), EnumFacing.UP),
    NORTH(2, new Vec3i(0, 0, -1), EnumFacing.NORTH),
    SOUTH(3, new Vec3i(0, 0, 1), EnumFacing.SOUTH),
    WEST(4, new Vec3i(-1, 0, 0), EnumFacing.WEST),
    EAST(5, new Vec3i(1, 0, 0), EnumFacing.EAST),
    NORTH_EAST(6, new Vec3i(1, 0, -1), EnumFacing.NORTH),
    SOUTH_EAST(7, new Vec3i(1, 0, 1), EnumFacing.EAST),
    SOUTH_WEST(8, new Vec3i(-1, 0, 1), EnumFacing.SOUTH),
    NORTH_WEST(9, new Vec3i(-1, 0, -1), EnumFacing.WEST);

    /** Ordering index for D-U-N-S-W-E */
    private final int index;

    /** Normalized Vector that points in the direction of this Facing */
    private final Vec3i directionVec;

    private final EnumFacing cardinal;

    private static final Map<Integer, BlockCompass> COMPASS = new HashMap<>();

    BlockCompass(final int indexIn, final Vec3i directionVecIn, final EnumFacing cardinal)
    {
        this.index = indexIn;
        this.directionVec = directionVecIn;
        this.cardinal = cardinal;
    }

    public int getIndex() {
        return index;
    }

    public Vec3i getDirectionVec() {
        return directionVec;
    }

    public EnumFacing getCardinal() {
        return cardinal;
    }

    public static BlockCompass getDirectionFromIndex(final int intIn) {
        if(!COMPASS.containsKey(intIn)) {
            for(final BlockCompass dir : values()) {
                final int index = dir.getIndex();
                if(index == intIn)
                    COMPASS.put(index, dir);
            }
        }
        return COMPASS.get(intIn);
    }
}
