package net.minecraft.block;

import net.minecraft.util.*;

static final class SwitchEnumFacing
{
    static final int[] FACING_LOOKUP;
    static final int[] ORIENTATION_LOOKUP;
    static final int[] AXIS_LOOKUP;
    
    static {
        AXIS_LOOKUP = new int[EnumFacing.Axis.values().length];
        try {
            SwitchEnumFacing.AXIS_LOOKUP[EnumFacing.Axis.X.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumFacing.AXIS_LOOKUP[EnumFacing.Axis.Z.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        ORIENTATION_LOOKUP = new int[EnumOrientation.values().length];
        try {
            SwitchEnumFacing.ORIENTATION_LOOKUP[EnumOrientation.EAST.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchEnumFacing.ORIENTATION_LOOKUP[EnumOrientation.WEST.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchEnumFacing.ORIENTATION_LOOKUP[EnumOrientation.SOUTH.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            SwitchEnumFacing.ORIENTATION_LOOKUP[EnumOrientation.NORTH.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            SwitchEnumFacing.ORIENTATION_LOOKUP[EnumOrientation.UP_Z.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            SwitchEnumFacing.ORIENTATION_LOOKUP[EnumOrientation.UP_X.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            SwitchEnumFacing.ORIENTATION_LOOKUP[EnumOrientation.DOWN_X.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        try {
            SwitchEnumFacing.ORIENTATION_LOOKUP[EnumOrientation.DOWN_Z.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError10) {}
        FACING_LOOKUP = new int[EnumFacing.values().length];
        try {
            SwitchEnumFacing.FACING_LOOKUP[EnumFacing.DOWN.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError11) {}
        try {
            SwitchEnumFacing.FACING_LOOKUP[EnumFacing.UP.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError12) {}
        try {
            SwitchEnumFacing.FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError13) {}
        try {
            SwitchEnumFacing.FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError14) {}
        try {
            SwitchEnumFacing.FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError15) {}
        try {
            SwitchEnumFacing.FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError16) {}
    }
}
