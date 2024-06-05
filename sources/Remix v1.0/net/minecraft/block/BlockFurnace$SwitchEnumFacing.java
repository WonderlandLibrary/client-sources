package net.minecraft.block;

import net.minecraft.util.*;

static final class SwitchEnumFacing
{
    static final int[] field_180356_a;
    
    static {
        field_180356_a = new int[EnumFacing.values().length];
        try {
            SwitchEnumFacing.field_180356_a[EnumFacing.WEST.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumFacing.field_180356_a[EnumFacing.EAST.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumFacing.field_180356_a[EnumFacing.NORTH.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchEnumFacing.field_180356_a[EnumFacing.SOUTH.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
    }
}
