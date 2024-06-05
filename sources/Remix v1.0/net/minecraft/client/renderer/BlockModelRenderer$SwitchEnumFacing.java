package net.minecraft.client.renderer;

import net.minecraft.util.*;

static final class SwitchEnumFacing
{
    static final int[] field_178290_a;
    
    static {
        field_178290_a = new int[EnumFacing.values().length];
        try {
            SwitchEnumFacing.field_178290_a[EnumFacing.DOWN.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumFacing.field_178290_a[EnumFacing.UP.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumFacing.field_178290_a[EnumFacing.NORTH.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchEnumFacing.field_178290_a[EnumFacing.SOUTH.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchEnumFacing.field_178290_a[EnumFacing.WEST.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            SwitchEnumFacing.field_178290_a[EnumFacing.EAST.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
    }
}
