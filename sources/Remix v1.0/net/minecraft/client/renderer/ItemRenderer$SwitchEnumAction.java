package net.minecraft.client.renderer;

import net.minecraft.item.*;

static final class SwitchEnumAction
{
    static final int[] field_178094_a;
    
    static {
        field_178094_a = new int[EnumAction.values().length];
        try {
            SwitchEnumAction.field_178094_a[EnumAction.NONE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumAction.field_178094_a[EnumAction.EAT.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumAction.field_178094_a[EnumAction.DRINK.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchEnumAction.field_178094_a[EnumAction.BLOCK.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchEnumAction.field_178094_a[EnumAction.BOW.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
    }
}
