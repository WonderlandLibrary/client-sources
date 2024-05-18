package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Predicate;

public interface IMob extends IAnimals
{
    public static final Predicate a_ = new Predicate() {
        private static final String HorizonCode_Horizon_È = "CL_00001688";
        
        public boolean HorizonCode_Horizon_È(final Entity p_179983_1_) {
            return p_179983_1_ instanceof IMob;
        }
        
        public boolean apply(final Object p_apply_1_) {
            return this.HorizonCode_Horizon_È((Entity)p_apply_1_);
        }
    };
    public static final Predicate b_ = new Predicate() {
        private static final String HorizonCode_Horizon_È = "CL_00002218";
        
        public boolean HorizonCode_Horizon_È(final Entity p_179982_1_) {
            return p_179982_1_ instanceof IMob && !p_179982_1_.áŒŠÏ();
        }
        
        public boolean apply(final Object p_apply_1_) {
            return this.HorizonCode_Horizon_È((Entity)p_apply_1_);
        }
    };
}
