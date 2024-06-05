package net.minecraft.world.gen.layer;

static final class SwitchMode
{
    static final int[] field_151642_a;
    
    static {
        field_151642_a = new int[Mode.values().length];
        try {
            SwitchMode.field_151642_a[Mode.COOL_WARM.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchMode.field_151642_a[Mode.HEAT_ICE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchMode.field_151642_a[Mode.SPECIAL.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
    }
}
