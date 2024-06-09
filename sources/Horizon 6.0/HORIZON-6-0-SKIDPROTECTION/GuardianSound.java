package HORIZON-6-0-SKIDPROTECTION;

public class GuardianSound extends MovingSound
{
    private final EntityGuardian ÂµÈ;
    private static final String á = "CL_00002381";
    
    public GuardianSound(final EntityGuardian guardian) {
        super(new ResourceLocation_1975012498("minecraft:mob.guardian.attack"));
        this.ÂµÈ = guardian;
        this.áˆºÑ¢Õ = ISound.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        this.Ø = true;
        this.áŒŠÆ = 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        if (!this.ÂµÈ.ˆáŠ && this.ÂµÈ.¥Ê()) {
            this.Âµá€ = (float)this.ÂµÈ.ŒÏ;
            this.Ó = (float)this.ÂµÈ.Çªà¢;
            this.à = (float)this.ÂµÈ.Ê;
            final float var1 = this.ÂµÈ.Å(0.0f);
            this.Ý = 0.0f + 1.0f * var1 * var1;
            this.Ø­áŒŠá = 0.7f + 0.5f * var1;
        }
        else {
            this.HorizonCode_Horizon_È = true;
        }
    }
}
