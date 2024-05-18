package net.minecraft.src;

class EnumEntitySizeHelper
{
    static final int[] field_96565_a;
    
    static {
        field_96565_a = new int[EnumEntitySize.values().length];
        try {
            EnumEntitySizeHelper.field_96565_a[EnumEntitySize.SIZE_1.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            EnumEntitySizeHelper.field_96565_a[EnumEntitySize.SIZE_2.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            EnumEntitySizeHelper.field_96565_a[EnumEntitySize.SIZE_3.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            EnumEntitySizeHelper.field_96565_a[EnumEntitySize.SIZE_4.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            EnumEntitySizeHelper.field_96565_a[EnumEntitySize.SIZE_5.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            EnumEntitySizeHelper.field_96565_a[EnumEntitySize.SIZE_6.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
    }
}
