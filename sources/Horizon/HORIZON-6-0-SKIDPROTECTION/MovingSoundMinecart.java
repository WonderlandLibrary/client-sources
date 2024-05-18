package HORIZON-6-0-SKIDPROTECTION;

public class MovingSoundMinecart extends MovingSound
{
    private final EntityMinecart ÂµÈ;
    private float á;
    private static final String ˆÏ­ = "CL_00001118";
    
    public MovingSoundMinecart(final EntityMinecart p_i45105_1_) {
        super(new ResourceLocation_1975012498("minecraft:minecart.base"));
        this.á = 0.0f;
        this.ÂµÈ = p_i45105_1_;
        this.Ø = true;
        this.áŒŠÆ = 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        if (this.ÂµÈ.ˆáŠ) {
            this.HorizonCode_Horizon_È = true;
        }
        else {
            this.Âµá€ = (float)this.ÂµÈ.ŒÏ;
            this.Ó = (float)this.ÂµÈ.Çªà¢;
            this.à = (float)this.ÂµÈ.Ê;
            final float var1 = MathHelper.HorizonCode_Horizon_È(this.ÂµÈ.ÇŽÉ * this.ÂµÈ.ÇŽÉ + this.ÂµÈ.ÇŽÕ * this.ÂµÈ.ÇŽÕ);
            if (var1 >= 0.01) {
                this.á = MathHelper.HorizonCode_Horizon_È(this.á + 0.0025f, 0.0f, 1.0f);
                this.Ý = 0.0f + MathHelper.HorizonCode_Horizon_È(var1, 0.0f, 0.5f) * 0.7f;
            }
            else {
                this.á = 0.0f;
                this.Ý = 0.0f;
            }
        }
    }
}
