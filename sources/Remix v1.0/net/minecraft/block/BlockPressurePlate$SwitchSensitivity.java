package net.minecraft.block;

static final class SwitchSensitivity
{
    static final int[] SENSITIVITY_ARRAY;
    
    static {
        SENSITIVITY_ARRAY = new int[Sensitivity.values().length];
        try {
            SwitchSensitivity.SENSITIVITY_ARRAY[Sensitivity.EVERYTHING.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchSensitivity.SENSITIVITY_ARRAY[Sensitivity.MOBS.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
    }
}
