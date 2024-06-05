package net.minecraft.block;

import net.minecraft.util.*;

public enum EnumOrientation implements IStringSerializable
{
    DOWN_X("DOWN_X", 0, 0, "down_x", EnumFacing.DOWN), 
    EAST("EAST", 1, 1, "east", EnumFacing.EAST), 
    WEST("WEST", 2, 2, "west", EnumFacing.WEST), 
    SOUTH("SOUTH", 3, 3, "south", EnumFacing.SOUTH), 
    NORTH("NORTH", 4, 4, "north", EnumFacing.NORTH), 
    UP_Z("UP_Z", 5, 5, "up_z", EnumFacing.UP), 
    UP_X("UP_X", 6, 6, "up_x", EnumFacing.UP), 
    DOWN_Z("DOWN_Z", 7, 7, "down_z", EnumFacing.DOWN);
    
    private static final EnumOrientation[] field_176869_i;
    private static final EnumOrientation[] $VALUES;
    private final int field_176866_j;
    private final String field_176867_k;
    private final EnumFacing field_176864_l;
    
    private EnumOrientation(final String p_i45709_1_, final int p_i45709_2_, final int p_i45709_3_, final String p_i45709_4_, final EnumFacing p_i45709_5_) {
        this.field_176866_j = p_i45709_3_;
        this.field_176867_k = p_i45709_4_;
        this.field_176864_l = p_i45709_5_;
    }
    
    public static EnumOrientation func_176853_a(int p_176853_0_) {
        if (p_176853_0_ < 0 || p_176853_0_ >= EnumOrientation.field_176869_i.length) {
            p_176853_0_ = 0;
        }
        return EnumOrientation.field_176869_i[p_176853_0_];
    }
    
    public static EnumOrientation func_176856_a(final EnumFacing p_176856_0_, final EnumFacing p_176856_1_) {
        switch (SwitchEnumFacing.FACING_LOOKUP[p_176856_0_.ordinal()]) {
            case 1: {
                switch (SwitchEnumFacing.AXIS_LOOKUP[p_176856_1_.getAxis().ordinal()]) {
                    case 1: {
                        return EnumOrientation.DOWN_X;
                    }
                    case 2: {
                        return EnumOrientation.DOWN_Z;
                    }
                    default: {
                        throw new IllegalArgumentException("Invalid entityFacing " + p_176856_1_ + " for facing " + p_176856_0_);
                    }
                }
                break;
            }
            case 2: {
                switch (SwitchEnumFacing.AXIS_LOOKUP[p_176856_1_.getAxis().ordinal()]) {
                    case 1: {
                        return EnumOrientation.UP_X;
                    }
                    case 2: {
                        return EnumOrientation.UP_Z;
                    }
                    default: {
                        throw new IllegalArgumentException("Invalid entityFacing " + p_176856_1_ + " for facing " + p_176856_0_);
                    }
                }
                break;
            }
            case 3: {
                return EnumOrientation.NORTH;
            }
            case 4: {
                return EnumOrientation.SOUTH;
            }
            case 5: {
                return EnumOrientation.WEST;
            }
            case 6: {
                return EnumOrientation.EAST;
            }
            default: {
                throw new IllegalArgumentException("Invalid facing: " + p_176856_0_);
            }
        }
    }
    
    public int func_176855_a() {
        return this.field_176866_j;
    }
    
    public EnumFacing func_176852_c() {
        return this.field_176864_l;
    }
    
    @Override
    public String toString() {
        return this.field_176867_k;
    }
    
    @Override
    public String getName() {
        return this.field_176867_k;
    }
    
    static {
        field_176869_i = new EnumOrientation[values().length];
        $VALUES = new EnumOrientation[] { EnumOrientation.DOWN_X, EnumOrientation.EAST, EnumOrientation.WEST, EnumOrientation.SOUTH, EnumOrientation.NORTH, EnumOrientation.UP_Z, EnumOrientation.UP_X, EnumOrientation.DOWN_Z };
        for (final EnumOrientation var4 : values()) {
            EnumOrientation.field_176869_i[var4.func_176855_a()] = var4;
        }
    }
}
