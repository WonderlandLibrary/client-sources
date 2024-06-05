package net.minecraft.block;

static final class SwitchEnumType
{
    static final int[] field_177065_a;
    
    static {
        field_177065_a = new int[BlockPlanks.EnumType.values().length];
        try {
            SwitchEnumType.field_177065_a[BlockPlanks.EnumType.SPRUCE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumType.field_177065_a[BlockPlanks.EnumType.BIRCH.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumType.field_177065_a[BlockPlanks.EnumType.JUNGLE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchEnumType.field_177065_a[BlockPlanks.EnumType.ACACIA.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchEnumType.field_177065_a[BlockPlanks.EnumType.DARK_OAK.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            SwitchEnumType.field_177065_a[BlockPlanks.EnumType.OAK.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
    }
}
