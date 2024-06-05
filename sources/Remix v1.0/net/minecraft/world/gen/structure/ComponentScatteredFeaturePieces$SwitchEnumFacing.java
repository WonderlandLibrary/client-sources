package net.minecraft.world.gen.structure;

import net.minecraft.util.*;

static final class SwitchEnumFacing
{
    static final int[] field_175956_a;
    
    static {
        field_175956_a = new int[EnumFacing.values().length];
        try {
            SwitchEnumFacing.field_175956_a[EnumFacing.NORTH.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumFacing.field_175956_a[EnumFacing.SOUTH.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
    }
}
