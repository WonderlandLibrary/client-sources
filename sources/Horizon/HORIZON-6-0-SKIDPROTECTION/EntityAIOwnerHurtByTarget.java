package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIOwnerHurtByTarget extends EntityAITarget
{
    EntityTameable HorizonCode_Horizon_È;
    EntityLivingBase Â;
    private int Ý;
    private static final String Ø­áŒŠá = "CL_00001624";
    
    public EntityAIOwnerHurtByTarget(final EntityTameable p_i1667_1_) {
        super(p_i1667_1_, false);
        this.HorizonCode_Horizon_È = p_i1667_1_;
        this.HorizonCode_Horizon_È(1);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (!this.HorizonCode_Horizon_È.ÐƒÓ()) {
            return false;
        }
        final EntityLivingBase var1 = this.HorizonCode_Horizon_È.ŒÐƒà();
        if (var1 == null) {
            return false;
        }
        this.Â = var1.Çªà();
        final int var2 = var1.¥Å();
        return var2 != this.Ý && this.HorizonCode_Horizon_È(this.Â, false) && this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â, var1);
    }
    
    @Override
    public void Âµá€() {
        this.Âµá€.Â(this.Â);
        final EntityLivingBase var1 = this.HorizonCode_Horizon_È.ŒÐƒà();
        if (var1 != null) {
            this.Ý = var1.¥Å();
        }
        super.Âµá€();
    }
}
