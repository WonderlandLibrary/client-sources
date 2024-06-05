package net.minecraft.client.renderer;

static final class SwitchTexGen
{
    static final int[] field_179175_a;
    
    static {
        field_179175_a = new int[TexGen.values().length];
        try {
            SwitchTexGen.field_179175_a[TexGen.S.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchTexGen.field_179175_a[TexGen.T.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchTexGen.field_179175_a[TexGen.R.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchTexGen.field_179175_a[TexGen.Q.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
    }
}
