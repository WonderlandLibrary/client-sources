package HORIZON-6-0-SKIDPROTECTION;

public class EntityAIOpenDoor extends EntityAIDoorInteract
{
    boolean à;
    int Ø;
    private static final String áŒŠÆ = "CL_00001603";
    
    public EntityAIOpenDoor(final EntityLiving p_i1644_1_, final boolean p_i1644_2_) {
        super(p_i1644_1_);
        this.HorizonCode_Horizon_È = p_i1644_1_;
        this.à = p_i1644_2_;
    }
    
    @Override
    public boolean Â() {
        return this.à && this.Ø > 0 && super.Â();
    }
    
    @Override
    public void Âµá€() {
        this.Ø = 20;
        this.Ý.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.Ï­Ðƒà, this.Â, true);
    }
    
    @Override
    public void Ý() {
        if (this.à) {
            this.Ý.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.Ï­Ðƒà, this.Â, false);
        }
    }
    
    @Override
    public void Ø­áŒŠá() {
        --this.Ø;
        super.Ø­áŒŠá();
    }
}
