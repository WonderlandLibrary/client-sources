package net.minecraft.block;

static final class SwitchEnumType
{
    static final int[] field_180353_a;
    static final int[] field_180352_b;
    
    static {
        field_180352_b = new int[BlockFlower.EnumFlowerType.values().length];
        try {
            SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType.POPPY.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType.BLUE_ORCHID.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType.ALLIUM.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType.HOUSTONIA.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType.RED_TULIP.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType.ORANGE_TULIP.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType.WHITE_TULIP.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType.PINK_TULIP.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType.OXEYE_DAISY.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        field_180353_a = new int[BlockPlanks.EnumType.values().length];
        try {
            SwitchEnumType.field_180353_a[BlockPlanks.EnumType.OAK.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError10) {}
        try {
            SwitchEnumType.field_180353_a[BlockPlanks.EnumType.SPRUCE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError11) {}
        try {
            SwitchEnumType.field_180353_a[BlockPlanks.EnumType.BIRCH.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError12) {}
        try {
            SwitchEnumType.field_180353_a[BlockPlanks.EnumType.JUNGLE.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError13) {}
        try {
            SwitchEnumType.field_180353_a[BlockPlanks.EnumType.ACACIA.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError14) {}
        try {
            SwitchEnumType.field_180353_a[BlockPlanks.EnumType.DARK_OAK.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError15) {}
    }
}
