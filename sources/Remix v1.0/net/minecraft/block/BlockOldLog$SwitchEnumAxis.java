package net.minecraft.block;

static final class SwitchEnumAxis
{
    static final int[] field_180203_a;
    
    static {
        field_180203_a = new int[EnumAxis.values().length];
        try {
            SwitchEnumAxis.field_180203_a[EnumAxis.X.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumAxis.field_180203_a[EnumAxis.Z.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumAxis.field_180203_a[EnumAxis.NONE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
    }
}
