package net.minecraft.client.renderer;

import net.minecraft.util.*;

public enum Orientation
{
    DOWN("DOWN", 0, "DOWN", 0, EnumFacing.DOWN, false), 
    UP("UP", 1, "UP", 1, EnumFacing.UP, false), 
    NORTH("NORTH", 2, "NORTH", 2, EnumFacing.NORTH, false), 
    SOUTH("SOUTH", 3, "SOUTH", 3, EnumFacing.SOUTH, false), 
    WEST("WEST", 4, "WEST", 4, EnumFacing.WEST, false), 
    EAST("EAST", 5, "EAST", 5, EnumFacing.EAST, false), 
    FLIP_DOWN("FLIP_DOWN", 6, "FLIP_DOWN", 6, EnumFacing.DOWN, true), 
    FLIP_UP("FLIP_UP", 7, "FLIP_UP", 7, EnumFacing.UP, true), 
    FLIP_NORTH("FLIP_NORTH", 8, "FLIP_NORTH", 8, EnumFacing.NORTH, true), 
    FLIP_SOUTH("FLIP_SOUTH", 9, "FLIP_SOUTH", 9, EnumFacing.SOUTH, true), 
    FLIP_WEST("FLIP_WEST", 10, "FLIP_WEST", 10, EnumFacing.WEST, true), 
    FLIP_EAST("FLIP_EAST", 11, "FLIP_EAST", 11, EnumFacing.EAST, true);
    
    private static final Orientation[] $VALUES;
    protected final int field_178229_m;
    
    private Orientation(final String p_i46383_1_, final int p_i46383_2_, final String p_i46233_1_, final int p_i46233_2_, final EnumFacing p_i46233_3_, final boolean p_i46233_4_) {
        this.field_178229_m = p_i46233_3_.getIndex() + (p_i46233_4_ ? EnumFacing.values().length : 0);
    }
    
    static {
        $VALUES = new Orientation[] { Orientation.DOWN, Orientation.UP, Orientation.NORTH, Orientation.SOUTH, Orientation.WEST, Orientation.EAST, Orientation.FLIP_DOWN, Orientation.FLIP_UP, Orientation.FLIP_NORTH, Orientation.FLIP_SOUTH, Orientation.FLIP_WEST, Orientation.FLIP_EAST };
    }
}
