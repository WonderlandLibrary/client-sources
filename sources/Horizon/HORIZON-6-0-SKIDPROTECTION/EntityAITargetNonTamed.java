package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Predicate;

public class EntityAITargetNonTamed extends EntityAINearestAttackableTarget
{
    private EntityTameable à;
    private static final String Ø = "CL_00001623";
    
    public EntityAITargetNonTamed(final EntityTameable p_i45876_1_, final Class p_i45876_2_, final boolean p_i45876_3_, final Predicate p_i45876_4_) {
        super(p_i45876_1_, p_i45876_2_, 10, p_i45876_3_, false, p_i45876_4_);
        this.à = p_i45876_1_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return !this.à.ÐƒÓ() && super.HorizonCode_Horizon_È();
    }
}
