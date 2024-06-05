package net.minecraft.block;

static final class SwitchEnumRailDirection
{
    static final int[] field_180121_a;
    
    static {
        field_180121_a = new int[EnumRailDirection.values().length];
        try {
            SwitchEnumRailDirection.field_180121_a[EnumRailDirection.NORTH_SOUTH.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumRailDirection.field_180121_a[EnumRailDirection.EAST_WEST.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumRailDirection.field_180121_a[EnumRailDirection.ASCENDING_EAST.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchEnumRailDirection.field_180121_a[EnumRailDirection.ASCENDING_WEST.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchEnumRailDirection.field_180121_a[EnumRailDirection.ASCENDING_NORTH.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            SwitchEnumRailDirection.field_180121_a[EnumRailDirection.ASCENDING_SOUTH.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
    }
}
