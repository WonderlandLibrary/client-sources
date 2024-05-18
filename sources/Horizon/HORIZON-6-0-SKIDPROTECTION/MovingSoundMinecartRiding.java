package HORIZON-6-0-SKIDPROTECTION;

public class MovingSoundMinecartRiding extends MovingSound
{
    private final EntityPlayer ÂµÈ;
    private final EntityMinecart á;
    private static final String ˆÏ­ = "CL_00001119";
    
    public MovingSoundMinecartRiding(final EntityPlayer p_i45106_1_, final EntityMinecart minecart) {
        super(new ResourceLocation_1975012498("minecraft:minecart.inside"));
        this.ÂµÈ = p_i45106_1_;
        this.á = minecart;
        this.áˆºÑ¢Õ = ISound.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        this.Ø = true;
        this.áŒŠÆ = 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        if (!this.á.ˆáŠ && this.ÂµÈ.áˆºÇŽØ() && this.ÂµÈ.Æ == this.á) {
            final float var1 = MathHelper.HorizonCode_Horizon_È(this.á.ÇŽÉ * this.á.ÇŽÉ + this.á.ÇŽÕ * this.á.ÇŽÕ);
            if (var1 >= 0.01) {
                this.Ý = 0.0f + MathHelper.HorizonCode_Horizon_È(var1, 0.0f, 1.0f) * 0.75f;
            }
            else {
                this.Ý = 0.0f;
            }
        }
        else {
            this.HorizonCode_Horizon_È = true;
        }
    }
}
