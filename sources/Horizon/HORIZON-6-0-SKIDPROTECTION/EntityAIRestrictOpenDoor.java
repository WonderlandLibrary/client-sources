package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIRestrictOpenDoor extends EntityAIBase
{
    private EntityCreature HorizonCode_Horizon_È;
    private VillageDoorInfo Â;
    private static final String Ý = "CL_00001610";
    
    public EntityAIRestrictOpenDoor(final EntityCreature p_i1651_1_) {
        this.HorizonCode_Horizon_È = p_i1651_1_;
        if (!(p_i1651_1_.Š() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob type for RestrictOpenDoorGoal");
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (this.HorizonCode_Horizon_È.Ï­Ðƒà.ÂµÈ()) {
            return false;
        }
        final BlockPos var1 = new BlockPos(this.HorizonCode_Horizon_È);
        final Village var2 = this.HorizonCode_Horizon_È.Ï­Ðƒà.È().HorizonCode_Horizon_È(var1, 16);
        if (var2 == null) {
            return false;
        }
        this.Â = var2.Â(var1);
        return this.Â != null && this.Â.Â(var1) < 2.25;
    }
    
    @Override
    public boolean Â() {
        return !this.HorizonCode_Horizon_È.Ï­Ðƒà.ÂµÈ() && (!this.Â.áŒŠÆ() && this.Â.Ý(new BlockPos(this.HorizonCode_Horizon_È)));
    }
    
    @Override
    public void Âµá€() {
        ((PathNavigateGround)this.HorizonCode_Horizon_È.Š()).Â(false);
        ((PathNavigateGround)this.HorizonCode_Horizon_È.Š()).Ý(false);
    }
    
    @Override
    public void Ý() {
        ((PathNavigateGround)this.HorizonCode_Horizon_È.Š()).Â(true);
        ((PathNavigateGround)this.HorizonCode_Horizon_È.Š()).Ý(true);
        this.Â = null;
    }
    
    @Override
    public void Ø­áŒŠá() {
        this.Â.Â();
    }
}
