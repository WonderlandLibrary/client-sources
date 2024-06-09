package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIWander extends EntityAIBase
{
    private EntityCreature HorizonCode_Horizon_È;
    private double Â;
    private double Ý;
    private double Ø­áŒŠá;
    private double Âµá€;
    private int Ó;
    private boolean à;
    private static final String Ø = "CL_00001608";
    
    public EntityAIWander(final EntityCreature p_i1648_1_, final double p_i1648_2_) {
        this(p_i1648_1_, p_i1648_2_, 120);
    }
    
    public EntityAIWander(final EntityCreature p_i45887_1_, final double p_i45887_2_, final int p_i45887_4_) {
        this.HorizonCode_Horizon_È = p_i45887_1_;
        this.Âµá€ = p_i45887_2_;
        this.Ó = p_i45887_4_;
        this.HorizonCode_Horizon_È(1);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (!this.à) {
            if (this.HorizonCode_Horizon_È.µÂ() >= 100) {
                return false;
            }
            if (this.HorizonCode_Horizon_È.ˆÐƒØ().nextInt(this.Ó) != 0) {
                return false;
            }
        }
        final Vec3 var1 = RandomPositionGenerator.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, 10, 7);
        if (var1 == null) {
            return false;
        }
        this.Â = var1.HorizonCode_Horizon_È;
        this.Ý = var1.Â;
        this.Ø­áŒŠá = var1.Ý;
        this.à = false;
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
    
    public void Ø() {
        this.à = true;
    }
    
    public void Â(final int p_179479_1_) {
        this.Ó = p_179479_1_;
    }
}
