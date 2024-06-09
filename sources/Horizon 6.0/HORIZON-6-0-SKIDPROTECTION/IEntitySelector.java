package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Predicate;

public final class IEntitySelector
{
    public static final Predicate HorizonCode_Horizon_È;
    public static final Predicate Â;
    public static final Predicate Ý;
    public static final Predicate Ø­áŒŠá;
    private static final String Âµá€ = "CL_00002257";
    
    static {
        HorizonCode_Horizon_È = (Predicate)new Predicate() {
            private static final String HorizonCode_Horizon_È = "CL_00001541";
            
            public boolean HorizonCode_Horizon_È(final Entity p_180131_1_) {
                return p_180131_1_.Œ();
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((Entity)p_apply_1_);
            }
        };
        Â = (Predicate)new Predicate() {
            private static final String HorizonCode_Horizon_È = "CL_00001542";
            
            public boolean HorizonCode_Horizon_È(final Entity p_180130_1_) {
                return p_180130_1_.Œ() && p_180130_1_.µÕ == null && p_180130_1_.Æ == null;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((Entity)p_apply_1_);
            }
        };
        Ý = (Predicate)new Predicate() {
            private static final String HorizonCode_Horizon_È = "CL_00001867";
            
            public boolean HorizonCode_Horizon_È(final Entity p_180102_1_) {
                return p_180102_1_ instanceof IInventory && p_180102_1_.Œ();
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((Entity)p_apply_1_);
            }
        };
        Ø­áŒŠá = (Predicate)new Predicate() {
            private static final String HorizonCode_Horizon_È = "CL_00002256";
            
            public boolean HorizonCode_Horizon_È(final Entity p_180103_1_) {
                return !(p_180103_1_ instanceof EntityPlayer) || !((EntityPlayer)p_180103_1_).Ø­áŒŠá();
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((Entity)p_apply_1_);
            }
        };
    }
    
    public static class HorizonCode_Horizon_È implements Predicate
    {
        private final ItemStack HorizonCode_Horizon_È;
        private static final String Â = "CL_00001543";
        
        public HorizonCode_Horizon_È(final ItemStack p_i1584_1_) {
            this.HorizonCode_Horizon_È = p_i1584_1_;
        }
        
        public boolean HorizonCode_Horizon_È(final Entity p_180100_1_) {
            if (!p_180100_1_.Œ()) {
                return false;
            }
            if (!(p_180100_1_ instanceof EntityLivingBase)) {
                return false;
            }
            final EntityLivingBase var2 = (EntityLivingBase)p_180100_1_;
            return var2.Ý(EntityLiving.Â(this.HorizonCode_Horizon_È)) == null && ((var2 instanceof EntityLiving) ? ((EntityLiving)var2).ˆÅ() : (var2 instanceof EntityArmorStand || var2 instanceof EntityPlayer));
        }
        
        public boolean apply(final Object p_apply_1_) {
            return this.HorizonCode_Horizon_È((Entity)p_apply_1_);
        }
    }
}
