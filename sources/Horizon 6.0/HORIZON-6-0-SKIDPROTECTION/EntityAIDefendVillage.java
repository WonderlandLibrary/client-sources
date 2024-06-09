package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIDefendVillage extends EntityAITarget
{
    EntityIronGolem HorizonCode_Horizon_È;
    EntityLivingBase Â;
    private static final String Ý = "CL_00001618";
    
    public EntityAIDefendVillage(final EntityIronGolem p_i1659_1_) {
        super(p_i1659_1_, false, true);
        this.HorizonCode_Horizon_È = p_i1659_1_;
        this.HorizonCode_Horizon_È(1);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        final Village var1 = this.HorizonCode_Horizon_È.Ø();
        if (var1 == null) {
            return false;
        }
        this.Â = var1.Â(this.HorizonCode_Horizon_È);
        if (this.HorizonCode_Horizon_È(this.Â, false)) {
            return true;
        }
        if (this.Âµá€.ˆÐƒØ().nextInt(20) == 0) {
            this.Â = var1.Ý(this.HorizonCode_Horizon_È);
            return this.HorizonCode_Horizon_È(this.Â, false);
        }
        return false;
    }
    
    @Override
    public void Âµá€() {
        this.HorizonCode_Horizon_È.Â(this.Â);
        super.Âµá€();
    }
}
