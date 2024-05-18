package HORIZON-6-0-SKIDPROTECTION;

public class TextureCompass extends TextureAtlasSprite
{
    public double áˆºÑ¢Õ;
    public double ÂµÈ;
    public static String á;
    private static final String ˆÏ­ = "CL_00001071";
    
    public TextureCompass(final String p_i1286_1_) {
        super(p_i1286_1_);
        TextureCompass.á = p_i1286_1_;
    }
    
    @Override
    public void áˆºÑ¢Õ() {
        final Minecraft var1 = Minecraft.áŒŠà();
        if (var1.áŒŠÆ != null && var1.á != null) {
            this.HorizonCode_Horizon_È(var1.áŒŠÆ, var1.á.ŒÏ, var1.á.Ê, var1.á.É, false, false);
        }
        else {
            this.HorizonCode_Horizon_È(null, 0.0, 0.0, 0.0, true, false);
        }
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final double p_94241_2_, final double p_94241_4_, double p_94241_6_, final boolean p_94241_8_, final boolean p_94241_9_) {
        if (!this.HorizonCode_Horizon_È.isEmpty()) {
            double var10 = 0.0;
            if (worldIn != null && !p_94241_8_) {
                final BlockPos var11 = worldIn.áŒŠà();
                final double var12 = var11.HorizonCode_Horizon_È() - p_94241_2_;
                final double var13 = var11.Ý() - p_94241_4_;
                p_94241_6_ %= 360.0;
                var10 = -((p_94241_6_ - 90.0) * 3.141592653589793 / 180.0 - Math.atan2(var13, var12));
                if (!worldIn.£à.Ø­áŒŠá()) {
                    var10 = Math.random() * 3.141592653589793 * 2.0;
                }
            }
            if (p_94241_9_) {
                this.áˆºÑ¢Õ = var10;
            }
            else {
                double var14;
                for (var14 = var10 - this.áˆºÑ¢Õ; var14 < -3.141592653589793; var14 += 6.283185307179586) {}
                while (var14 >= 3.141592653589793) {
                    var14 -= 6.283185307179586;
                }
                var14 = MathHelper.HorizonCode_Horizon_È(var14, -1.0, 1.0);
                this.ÂµÈ += var14 * 0.1;
                this.ÂµÈ *= 0.8;
                this.áˆºÑ¢Õ += this.ÂµÈ;
            }
            int var15;
            for (var15 = (int)((this.áˆºÑ¢Õ / 6.283185307179586 + 1.0) * this.HorizonCode_Horizon_È.size()) % this.HorizonCode_Horizon_È.size(); var15 < 0; var15 = (var15 + this.HorizonCode_Horizon_È.size()) % this.HorizonCode_Horizon_È.size()) {}
            if (var15 != this.Ø) {
                this.Ø = var15;
                TextureUtil.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.get(this.Ø), this.Ó, this.à, this.Ø­áŒŠá, this.Âµá€, false, false);
            }
        }
    }
}
