package net.minecraft.tileentity;

import net.minecraft.util.*;

static final class SwitchAxis
{
    static final int[] field_177248_a;
    
    static {
        field_177248_a = new int[EnumFacing.Axis.values().length];
        try {
            SwitchAxis.field_177248_a[EnumFacing.Axis.X.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchAxis.field_177248_a[EnumFacing.Axis.Y.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchAxis.field_177248_a[EnumFacing.Axis.Z.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
    }
}
