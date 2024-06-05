package net.minecraft.block;

static final class SwitchEnumType
{
    static final int[] field_180178_a;
    
    static {
        field_180178_a = new int[EnumType.values().length];
        try {
            SwitchEnumType.field_180178_a[EnumType.COBBLESTONE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumType.field_180178_a[EnumType.STONEBRICK.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumType.field_180178_a[EnumType.MOSSY_STONEBRICK.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchEnumType.field_180178_a[EnumType.CRACKED_STONEBRICK.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchEnumType.field_180178_a[EnumType.CHISELED_STONEBRICK.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
    }
}
