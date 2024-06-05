package net.minecraft.block;

import net.minecraft.util.*;

static final class SwitchEnumFacing
{
    static final int[] field_180420_a;
    
    static {
        field_180420_a = new int[EnumFacing.values().length];
        try {
            SwitchEnumFacing.field_180420_a[EnumFacing.EAST.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumFacing.field_180420_a[EnumFacing.WEST.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumFacing.field_180420_a[EnumFacing.SOUTH.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchEnumFacing.field_180420_a[EnumFacing.NORTH.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchEnumFacing.field_180420_a[EnumFacing.UP.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            SwitchEnumFacing.field_180420_a[EnumFacing.DOWN.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
    }
}
