package net.minecraft.item;

import net.minecraft.block.*;

static final class SwitchDirtType
{
    static final int[] field_179590_a;
    
    static {
        field_179590_a = new int[BlockDirt.DirtType.values().length];
        try {
            SwitchDirtType.field_179590_a[BlockDirt.DirtType.DIRT.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchDirtType.field_179590_a[BlockDirt.DirtType.COARSE_DIRT.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
    }
}
