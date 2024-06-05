package net.minecraft.tileentity;

import net.minecraft.util.*;

static final class SwitchEnumFacing
{
    static final int[] field_177366_a;
    
    static {
        field_177366_a = new int[EnumFacing.values().length];
        try {
            SwitchEnumFacing.field_177366_a[EnumFacing.NORTH.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumFacing.field_177366_a[EnumFacing.SOUTH.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumFacing.field_177366_a[EnumFacing.EAST.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchEnumFacing.field_177366_a[EnumFacing.WEST.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
    }
}
