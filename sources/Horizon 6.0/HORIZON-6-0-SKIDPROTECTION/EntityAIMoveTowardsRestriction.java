package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIMoveTowardsRestriction extends EntityAIBase
{
    private EntityCreature HorizonCode_Horizon_È;
    private double Â;
    private double Ý;
    private double Ø­áŒŠá;
    private double Âµá€;
    private static final String Ó = "CL_00001598";
    
    public EntityAIMoveTowardsRestriction(final EntityCreature p_i2347_1_, final double p_i2347_2_) {
        this.HorizonCode_Horizon_È = p_i2347_1_;
        this.Âµá€ = p_i2347_2_;
        this.HorizonCode_Horizon_È(1);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (this.HorizonCode_Horizon_È.¥Æ()) {
            return false;
        }
        final BlockPos var1 = this.HorizonCode_Horizon_È.Ø­à();
        final Vec3 var2 = RandomPositionGenerator.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, 16, 7, new Vec3(var1.HorizonCode_Horizon_È(), var1.Â(), var1.Ý()));
        if (var2 == null) {
            return false;
        }
        this.Â = var2.HorizonCode_Horizon_È;
        this.Ý = var2.Â;
        this.Ø­áŒŠá = var2.Ý;
        return true;
    }
    
    @Override
    public boolean Â() {
        return !this.HorizonCode_Horizon_È.Š().Ó();
    }
    
    @Override
    public void Âµá€() {
        this.HorizonCode_Horizon_È.Š().HorizonCode_Horizon_È(this.Â, this.Ý, this.Ø­áŒŠá, this.Âµá€);
    }
}
