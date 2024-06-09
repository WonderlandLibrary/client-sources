package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIPanic extends EntityAIBase
{
    private EntityCreature Â;
    protected double HorizonCode_Horizon_È;
    private double Ý;
    private double Ø­áŒŠá;
    private double Âµá€;
    private static final String Ó = "CL_00001604";
    
    public EntityAIPanic(final EntityCreature p_i1645_1_, final double p_i1645_2_) {
        this.Â = p_i1645_1_;
        this.HorizonCode_Horizon_È = p_i1645_2_;
        this.HorizonCode_Horizon_È(1);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (this.Â.Çªà() == null && !this.Â.ˆÏ()) {
            return false;
        }
        final Vec3 var1 = RandomPositionGenerator.HorizonCode_Horizon_È(this.Â, 5, 4);
        if (var1 == null) {
            return false;
        }
        this.Ý = var1.HorizonCode_Horizon_È;
        this.Ø­áŒŠá = var1.Â;
        this.Âµá€ = var1.Ý;
        return true;
    }
    
    @Override
    public void Âµá€() {
        this.Â.Š().HorizonCode_Horizon_È(this.Ý, this.Ø­áŒŠá, this.Âµá€, this.HorizonCode_Horizon_È);
    }
    
    @Override
    public boolean Â() {
        return !this.Â.Š().Ó();
    }
}
