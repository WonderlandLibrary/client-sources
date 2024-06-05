package net.minecraft.client.renderer;

import net.minecraft.util.*;

public static final class Constants
{
    public static final int SOUTH_INDEX;
    public static final int UP_INDEX;
    public static final int EAST_INDEX;
    public static final int NORTH_INDEX;
    public static final int DOWN_INDEX;
    public static final int WEST_INDEX;
    
    static {
        SOUTH_INDEX = EnumFacing.SOUTH.getIndex();
        UP_INDEX = EnumFacing.UP.getIndex();
        EAST_INDEX = EnumFacing.EAST.getIndex();
        NORTH_INDEX = EnumFacing.NORTH.getIndex();
        DOWN_INDEX = EnumFacing.DOWN.getIndex();
        WEST_INDEX = EnumFacing.WEST.getIndex();
    }
}
