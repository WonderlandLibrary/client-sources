package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIMoveTowardsTarget extends EntityAIBase
{
    private EntityCreature HorizonCode_Horizon_È;
    private EntityLivingBase Â;
    private double Ý;
    private double Ø­áŒŠá;
    private double Âµá€;
    private double Ó;
    private float à;
    private static final String Ø = "CL_00001599";
    
    public EntityAIMoveTowardsTarget(final EntityCreature p_i1640_1_, final double p_i1640_2_, final float p_i1640_4_) {
        this.HorizonCode_Horizon_È = p_i1640_1_;
        this.Ó = p_i1640_2_;
        this.à = p_i1640_4_;
        this.HorizonCode_Horizon_È(1);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        this.Â = this.HorizonCode_Horizon_È.Ñ¢Ó();
        if (this.Â == null) {
            return false;
        }
        if (this.Â.Âµá€(this.HorizonCode_Horizon_È) > this.à * this.à) {
            return false;
        }
        final Vec3 var1 = RandomPositionGenerator.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, 16, 7, new Vec3(this.Â.ŒÏ, this.Â.Çªà¢, this.Â.Ê));
        if (var1 == null) {
            return false;
        }
        this.Ý = var1.HorizonCode_Horizon_È;
        this.Ø­áŒŠá = var1.Â;
        this.Âµá€ = var1.Ý;
        return true;
    }
    
    @Override
    public boolean Â() {
        return !this.HorizonCode_Horizon_È.Š().Ó() && this.Â.Œ() && this.Â.Âµá€(this.HorizonCode_Horizon_È) < this.à * this.à;
    }
    
    @Override
    public void Ý() {
        this.Â = null;
    }
    
    @Override
    public void Âµá€() {
        this.HorizonCode_Horizon_È.Š().HorizonCode_Horizon_È(this.Ý, this.Ø­áŒŠá, this.Âµá€, this.Ó);
    }
}
