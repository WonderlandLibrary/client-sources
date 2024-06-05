package net.minecraft.client.renderer;

import net.minecraft.block.*;

static final class SwitchEnumType
{
    static final int[] field_178257_a;
    
    static {
        field_178257_a = new int[BlockQuartz.EnumType.values().length];
        try {
            SwitchEnumType.field_178257_a[BlockQuartz.EnumType.DEFAULT.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumType.field_178257_a[BlockQuartz.EnumType.CHISELED.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumType.field_178257_a[BlockQuartz.EnumType.LINES_Y.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchEnumType.field_178257_a[BlockQuartz.EnumType.LINES_X.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchEnumType.field_178257_a[BlockQuartz.EnumType.LINES_Z.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
    }
}
