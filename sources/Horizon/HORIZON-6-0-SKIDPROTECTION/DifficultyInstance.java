package HORIZON-6-0-SKIDPROTECTION;

public class DifficultyInstance
{
    private final EnumDifficulty HorizonCode_Horizon_È;
    private final float Â;
    private static final String Ý = "CL_00002261";
    
    public DifficultyInstance(final EnumDifficulty p_i45904_1_, final long p_i45904_2_, final long p_i45904_4_, final float p_i45904_6_) {
        this.HorizonCode_Horizon_È = p_i45904_1_;
        this.Â = this.HorizonCode_Horizon_È(p_i45904_1_, p_i45904_2_, p_i45904_4_, p_i45904_6_);
    }
    
    public float HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public float Â() {
        return (this.Â < 2.0f) ? 0.0f : ((this.Â > 4.0f) ? 1.0f : ((this.Â - 2.0f) / 2.0f));
    }
    
    private float HorizonCode_Horizon_È(final EnumDifficulty p_180169_1_, final long p_180169_2_, final long p_180169_4_, final float p_180169_6_) {
        if (p_180169_1_ == EnumDifficulty.HorizonCode_Horizon_È) {
            return 0.0f;
        }
        final boolean var7 = p_180169_1_ == EnumDifficulty.Ø­áŒŠá;
        float var8 = 0.75f;
        final float var9 = MathHelper.HorizonCode_Horizon_È((p_180169_2_ - 72000.0f) / 1440000.0f, 0.0f, 1.0f) * 0.25f;
        var8 += var9;
        float var10 = 0.0f;
        var10 += MathHelper.HorizonCode_Horizon_È(p_180169_4_ / 3600000.0f, 0.0f, 1.0f) * (var7 ? 1.0f : 0.75f);
        var10 += MathHelper.HorizonCode_Horizon_È(p_180169_6_ * 0.25f, 0.0f, var9);
        if (p_180169_1_ == EnumDifficulty.Â) {
            var10 *= 0.5f;
        }
        var8 += var10;
        return p_180169_1_.HorizonCode_Horizon_È() * var8;
    }
}
