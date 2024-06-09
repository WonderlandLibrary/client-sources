package HORIZON-6-0-SKIDPROTECTION;

public class EntityDragonPart extends Entity
{
    public final IEntityMultiPart HorizonCode_Horizon_È;
    public final String Â;
    private static final String Ý = "CL_00001657";
    
    public EntityDragonPart(final IEntityMultiPart p_i1697_1_, final String p_i1697_2_, final float p_i1697_3_, final float p_i1697_4_) {
        super(p_i1697_1_.HorizonCode_Horizon_È());
        this.HorizonCode_Horizon_È(p_i1697_3_, p_i1697_4_);
        this.HorizonCode_Horizon_È = p_i1697_1_;
        this.Â = p_i1697_2_;
    }
    
    @Override
    protected void ÂµÈ() {
    }
    
    @Override
    protected void Â(final NBTTagCompound tagCompund) {
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
    }
    
    @Override
    public boolean Ô() {
        return true;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        return !this.HorizonCode_Horizon_È(source) && this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this, source, amount);
    }
    
    @Override
    public boolean Ø(final Entity entityIn) {
        return this == entityIn || this.HorizonCode_Horizon_È == entityIn;
    }
}
