package net.minecraft.block;

import net.minecraft.util.*;

static final class SwitchEnumFacing
{
    static final int[] FACINGARRAY;
    
    static {
        FACINGARRAY = new int[EnumFacing.values().length];
        try {
            SwitchEnumFacing.FACINGARRAY[EnumFacing.SOUTH.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumFacing.FACINGARRAY[EnumFacing.NORTH.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumFacing.FACINGARRAY[EnumFacing.WEST.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchEnumFacing.FACINGARRAY[EnumFacing.EAST.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
    }
}
