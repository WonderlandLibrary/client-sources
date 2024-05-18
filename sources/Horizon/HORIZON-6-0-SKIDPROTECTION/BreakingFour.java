package HORIZON-6-0-SKIDPROTECTION;

import java.util.Arrays;

public class BreakingFour extends BakedQuad
{
    private final TextureAtlasSprite Ø­áŒŠá;
    private static final String Âµá€ = "CL_00002492";
    
    public BreakingFour(final BakedQuad p_i46217_1_, final TextureAtlasSprite p_i46217_2_) {
        super(Arrays.copyOf(p_i46217_1_.Â(), p_i46217_1_.Â().length), p_i46217_1_.Â, FaceBakery.HorizonCode_Horizon_È(p_i46217_1_.Â()));
        this.Ø­áŒŠá = p_i46217_2_;
        this.Ó();
    }
    
    private void Ó() {
        for (int var1 = 0; var1 < 4; ++var1) {
            this.HorizonCode_Horizon_È(var1);
        }
    }
    
    private void HorizonCode_Horizon_È(final int p_178216_1_) {
        final int var2 = 7 * p_178216_1_;
        final float var3 = Float.intBitsToFloat(this.HorizonCode_Horizon_È[var2]);
        final float var4 = Float.intBitsToFloat(this.HorizonCode_Horizon_È[var2 + 1]);
        final float var5 = Float.intBitsToFloat(this.HorizonCode_Horizon_È[var2 + 2]);
        float var6 = 0.0f;
        float var7 = 0.0f;
        switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[this.Ý.ordinal()]) {
            case 1: {
                var6 = var3 * 16.0f;
                var7 = (1.0f - var5) * 16.0f;
                break;
            }
            case 2: {
                var6 = var3 * 16.0f;
                var7 = var5 * 16.0f;
                break;
            }
            case 3: {
                var6 = (1.0f - var3) * 16.0f;
                var7 = (1.0f - var4) * 16.0f;
                break;
            }
            case 4: {
                var6 = var3 * 16.0f;
                var7 = (1.0f - var4) * 16.0f;
                break;
            }
            case 5: {
                var6 = var5 * 16.0f;
                var7 = (1.0f - var4) * 16.0f;
                break;
            }
            case 6: {
                var6 = (1.0f - var5) * 16.0f;
                var7 = (1.0f - var4) * 16.0f;
                break;
            }
        }
        this.HorizonCode_Horizon_È[var2 + 4] = Float.floatToRawIntBits(this.Ø­áŒŠá.HorizonCode_Horizon_È((double)var6));
        this.HorizonCode_Horizon_È[var2 + 4 + 1] = Float.floatToRawIntBits(this.Ø­áŒŠá.Â((double)var7));
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002491";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                BreakingFour.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BreakingFour.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BreakingFour.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BreakingFour.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                BreakingFour.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                BreakingFour.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
    }
}
