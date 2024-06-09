package HORIZON-6-0-SKIDPROTECTION;

public class TextureClock extends TextureAtlasSprite
{
    private double áˆºÑ¢Õ;
    private double ÂµÈ;
    private static final String á = "CL_00001070";
    
    public TextureClock(final String p_i1285_1_) {
        super(p_i1285_1_);
    }
    
    @Override
    public void áˆºÑ¢Õ() {
        if (!this.HorizonCode_Horizon_È.isEmpty()) {
            final Minecraft var1 = Minecraft.áŒŠà();
            double var2 = 0.0;
            if (var1.áŒŠÆ != null && var1.á != null) {
                final float var3 = var1.áŒŠÆ.Ý(1.0f);
                var2 = var3;
                if (!var1.áŒŠÆ.£à.Ø­áŒŠá()) {
                    var2 = Math.random();
                }
            }
            double var4;
            for (var4 = var2 - this.áˆºÑ¢Õ; var4 < -0.5; ++var4) {}
            while (var4 >= 0.5) {
                --var4;
            }
            var4 = MathHelper.HorizonCode_Horizon_È(var4, -1.0, 1.0);
            this.ÂµÈ += var4 * 0.1;
            this.ÂµÈ *= 0.8;
            this.áˆºÑ¢Õ += this.ÂµÈ;
            int var5;
            for (var5 = (int)((this.áˆºÑ¢Õ + 1.0) * this.HorizonCode_Horizon_È.size()) % this.HorizonCode_Horizon_È.size(); var5 < 0; var5 = (var5 + this.HorizonCode_Horizon_È.size()) % this.HorizonCode_Horizon_È.size()) {}
            if (var5 != this.Ø) {
                this.Ø = var5;
                TextureUtil.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.get(this.Ø), this.Ó, this.à, this.Ø­áŒŠá, this.Âµá€, false, false);
            }
        }
    }
}
