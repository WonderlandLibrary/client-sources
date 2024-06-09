package HORIZON-6-0-SKIDPROTECTION;

public class FoodStats
{
    private int HorizonCode_Horizon_È;
    private float Â;
    private float Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private static final String Ó = "CL_00001729";
    
    public FoodStats() {
        this.HorizonCode_Horizon_È = 20;
        this.Â = 5.0f;
        this.Âµá€ = 20;
    }
    
    public void HorizonCode_Horizon_È(final int p_75122_1_, final float p_75122_2_) {
        this.HorizonCode_Horizon_È = Math.min(p_75122_1_ + this.HorizonCode_Horizon_È, 20);
        this.Â = Math.min(this.Â + p_75122_1_ * p_75122_2_ * 2.0f, this.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final ItemFood p_151686_1_, final ItemStack p_151686_2_) {
        this.HorizonCode_Horizon_È(p_151686_1_.ÂµÈ(p_151686_2_), p_151686_1_.á(p_151686_2_));
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayer p_75118_1_) {
        final EnumDifficulty var2 = p_75118_1_.Ï­Ðƒà.ŠÂµà();
        this.Âµá€ = this.HorizonCode_Horizon_È;
        if (this.Ý > 4.0f) {
            this.Ý -= 4.0f;
            if (this.Â > 0.0f) {
                this.Â = Math.max(this.Â - 1.0f, 0.0f);
            }
            else if (var2 != EnumDifficulty.HorizonCode_Horizon_È) {
                this.HorizonCode_Horizon_È = Math.max(this.HorizonCode_Horizon_È - 1, 0);
            }
        }
        if (p_75118_1_.Ï­Ðƒà.Çªà¢().Â("naturalRegeneration") && this.HorizonCode_Horizon_È >= 18 && p_75118_1_.ÇŽà()) {
            ++this.Ø­áŒŠá;
            if (this.Ø­áŒŠá >= 80) {
                p_75118_1_.a_(1.0f);
                this.HorizonCode_Horizon_È(3.0f);
                this.Ø­áŒŠá = 0;
            }
        }
        else if (this.HorizonCode_Horizon_È <= 0) {
            ++this.Ø­áŒŠá;
            if (this.Ø­áŒŠá >= 80) {
                if (p_75118_1_.Ï­Ä() > 10.0f || var2 == EnumDifficulty.Ø­áŒŠá || (p_75118_1_.Ï­Ä() > 1.0f && var2 == EnumDifficulty.Ý)) {
                    p_75118_1_.HorizonCode_Horizon_È(DamageSource.à, 1.0f);
                }
                this.Ø­áŒŠá = 0;
            }
        }
        else {
            this.Ø­áŒŠá = 0;
        }
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound p_75112_1_) {
        if (p_75112_1_.Â("foodLevel", 99)) {
            this.HorizonCode_Horizon_È = p_75112_1_.Ó("foodLevel");
            this.Ø­áŒŠá = p_75112_1_.Ó("foodTickTimer");
            this.Â = p_75112_1_.Ø("foodSaturationLevel");
            this.Ý = p_75112_1_.Ø("foodExhaustionLevel");
        }
    }
    
    public void Â(final NBTTagCompound p_75117_1_) {
        p_75117_1_.HorizonCode_Horizon_È("foodLevel", this.HorizonCode_Horizon_È);
        p_75117_1_.HorizonCode_Horizon_È("foodTickTimer", this.Ø­áŒŠá);
        p_75117_1_.HorizonCode_Horizon_È("foodSaturationLevel", this.Â);
        p_75117_1_.HorizonCode_Horizon_È("foodExhaustionLevel", this.Ý);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Â() {
        return this.Âµá€;
    }
    
    public boolean Ý() {
        return this.HorizonCode_Horizon_È < 20;
    }
    
    public void HorizonCode_Horizon_È(final float p_75113_1_) {
        this.Ý = Math.min(this.Ý + p_75113_1_, 40.0f);
    }
    
    public float Ø­áŒŠá() {
        return this.Â;
    }
    
    public void HorizonCode_Horizon_È(final int p_75114_1_) {
        this.HorizonCode_Horizon_È = p_75114_1_;
    }
    
    public void Â(final float p_75119_1_) {
        this.Â = p_75119_1_;
    }
}
