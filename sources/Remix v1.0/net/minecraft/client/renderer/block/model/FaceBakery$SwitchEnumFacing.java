package net.minecraft.client.renderer.block.model;

import net.minecraft.util.*;

static final class SwitchEnumFacing
{
    static final int[] field_178400_a;
    static final int[] field_178399_b;
    
    static {
        field_178399_b = new int[EnumFacing.Axis.values().length];
        try {
            SwitchEnumFacing.field_178399_b[EnumFacing.Axis.X.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumFacing.field_178399_b[EnumFacing.Axis.Y.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumFacing.field_178399_b[EnumFacing.Axis.Z.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        field_178400_a = new int[EnumFacing.values().length];
        try {
            SwitchEnumFacing.field_178400_a[EnumFacing.DOWN.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchEnumFacing.field_178400_a[EnumFacing.UP.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            SwitchEnumFacing.field_178400_a[EnumFacing.NORTH.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            SwitchEnumFacing.field_178400_a[EnumFacing.SOUTH.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            SwitchEnumFacing.field_178400_a[EnumFacing.WEST.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            SwitchEnumFacing.field_178400_a[EnumFacing.EAST.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
    }
}
