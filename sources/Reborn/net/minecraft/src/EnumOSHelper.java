package net.minecraft.src;

public class EnumOSHelper
{
    public static final int[] field_90049_a;
    
    static {
        field_90049_a = new int[EnumOS.values().length];
        try {
            EnumOSHelper.field_90049_a[EnumOS.LINUX.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            EnumOSHelper.field_90049_a[EnumOS.SOLARIS.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            EnumOSHelper.field_90049_a[EnumOS.WINDOWS.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            EnumOSHelper.field_90049_a[EnumOS.MACOS.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
    }
}
