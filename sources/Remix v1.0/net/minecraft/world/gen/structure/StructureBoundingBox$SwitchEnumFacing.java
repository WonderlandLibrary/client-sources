package net.minecraft.world.gen.structure;

import net.minecraft.util.*;

static final class SwitchEnumFacing
{
    static final int[] field_175895_a;
    
    static {
        field_175895_a = new int[EnumFacing.values().length];
        try {
            SwitchEnumFacing.field_175895_a[EnumFacing.NORTH.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumFacing.field_175895_a[EnumFacing.SOUTH.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumFacing.field_175895_a[EnumFacing.WEST.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchEnumFacing.field_175895_a[EnumFacing.EAST.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
    }
}
