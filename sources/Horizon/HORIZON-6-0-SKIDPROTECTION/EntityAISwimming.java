package HORIZON-6-0-SKIDPROTECTION;

public class EntityAISwimming extends EntityAIBase
{
    private EntityLiving HorizonCode_Horizon_È;
    private static final String Â = "CL_00001584";
    
    public EntityAISwimming(final EntityLiving p_i1624_1_) {
        this.HorizonCode_Horizon_È = p_i1624_1_;
        this.HorizonCode_Horizon_È(4);
        ((PathNavigateGround)p_i1624_1_.Š()).Ø­áŒŠá(true);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È.£ÂµÄ() || this.HorizonCode_Horizon_È.ÇŽá€();
    }
    
    @Override
    public void Ø­áŒŠá() {
        if (this.HorizonCode_Horizon_È.ˆÐƒØ().nextFloat() < 0.8f) {
            this.HorizonCode_Horizon_È.ÇŽÉ().HorizonCode_Horizon_È();
        }
    }
}
