package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIRestrictSun extends EntityAIBase
{
    private EntityCreature HorizonCode_Horizon_È;
    private static final String Â = "CL_00001611";
    
    public EntityAIRestrictSun(final EntityCreature p_i1652_1_) {
        this.HorizonCode_Horizon_È = p_i1652_1_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È.Ï­Ðƒà.ÂµÈ();
    }
    
    @Override
    public void Âµá€() {
        ((PathNavigateGround)this.HorizonCode_Horizon_È.Š()).Âµá€(true);
    }
    
    @Override
    public void Ý() {
        ((PathNavigateGround)this.HorizonCode_Horizon_È.Š()).Âµá€(false);
    }
}
