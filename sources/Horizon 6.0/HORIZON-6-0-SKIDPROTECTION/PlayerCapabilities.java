package HORIZON-6-0-SKIDPROTECTION;

public class PlayerCapabilities
{
    public boolean HorizonCode_Horizon_È;
    public boolean Â;
    public boolean Ý;
    public boolean Ø­áŒŠá;
    public boolean Âµá€;
    private float Ó;
    private float à;
    private static final String Ø = "CL_00001708";
    
    public PlayerCapabilities() {
        this.Âµá€ = true;
        this.Ó = 0.05f;
        this.à = 0.1f;
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound p_75091_1_) {
        final NBTTagCompound var2 = new NBTTagCompound();
        var2.HorizonCode_Horizon_È("invulnerable", this.HorizonCode_Horizon_È);
        var2.HorizonCode_Horizon_È("flying", this.Â);
        var2.HorizonCode_Horizon_È("mayfly", this.Ý);
        var2.HorizonCode_Horizon_È("instabuild", this.Ø­áŒŠá);
        var2.HorizonCode_Horizon_È("mayBuild", this.Âµá€);
        var2.HorizonCode_Horizon_È("flySpeed", this.Ó);
        var2.HorizonCode_Horizon_È("walkSpeed", this.à);
        p_75091_1_.HorizonCode_Horizon_È("abilities", var2);
    }
    
    public void Â(final NBTTagCompound p_75095_1_) {
        if (p_75095_1_.Â("abilities", 10)) {
            final NBTTagCompound var2 = p_75095_1_.ˆÏ­("abilities");
            this.HorizonCode_Horizon_È = var2.£á("invulnerable");
            this.Â = var2.£á("flying");
            this.Ý = var2.£á("mayfly");
            this.Ø­áŒŠá = var2.£á("instabuild");
            if (var2.Â("flySpeed", 99)) {
                this.Ó = var2.Ø("flySpeed");
                this.à = var2.Ø("walkSpeed");
            }
            if (var2.Â("mayBuild", 1)) {
                this.Âµá€ = var2.£á("mayBuild");
            }
        }
    }
    
    public float HorizonCode_Horizon_È() {
        return this.Ó;
    }
    
    public void HorizonCode_Horizon_È(final float p_75092_1_) {
        this.Ó = p_75092_1_;
    }
    
    public float Â() {
        return this.à;
    }
    
    public void Â(final float p_82877_1_) {
        this.à = p_82877_1_;
    }
}
