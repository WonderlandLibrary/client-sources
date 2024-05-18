package HORIZON-6-0-SKIDPROTECTION;

import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import com.google.common.base.Predicates;
import com.google.common.base.Predicate;

public class EntityAINearestAttackableTarget extends EntityAITarget
{
    protected final Class HorizonCode_Horizon_È;
    private final int à;
    protected final HorizonCode_Horizon_È Â;
    protected Predicate Ý;
    protected EntityLivingBase Ø­áŒŠá;
    private static final String Ø = "CL_00001620";
    
    public EntityAINearestAttackableTarget(final EntityCreature p_i45878_1_, final Class p_i45878_2_, final boolean p_i45878_3_) {
        this(p_i45878_1_, p_i45878_2_, p_i45878_3_, false);
    }
    
    public EntityAINearestAttackableTarget(final EntityCreature p_i45879_1_, final Class p_i45879_2_, final boolean p_i45879_3_, final boolean p_i45879_4_) {
        this(p_i45879_1_, p_i45879_2_, 10, p_i45879_3_, p_i45879_4_, null);
    }
    
    public EntityAINearestAttackableTarget(final EntityCreature p_i45880_1_, final Class p_i45880_2_, final int p_i45880_3_, final boolean p_i45880_4_, final boolean p_i45880_5_, final Predicate p_i45880_6_) {
        super(p_i45880_1_, p_i45880_4_, p_i45880_5_);
        this.HorizonCode_Horizon_È = p_i45880_2_;
        this.à = p_i45880_3_;
        this.Â = new HorizonCode_Horizon_È(p_i45880_1_);
        this.HorizonCode_Horizon_È(1);
        this.Ý = (Predicate)new Predicate() {
            private static final String Â = "CL_00001621";
            
            public boolean HorizonCode_Horizon_È(final EntityLivingBase p_179878_1_) {
                if (p_i45880_6_ != null && !p_i45880_6_.apply((Object)p_179878_1_)) {
                    return false;
                }
                if (p_179878_1_ instanceof EntityPlayer) {
                    double var2 = EntityAINearestAttackableTarget.this.Ø();
                    if (p_179878_1_.Çªà¢()) {
                        var2 *= 0.800000011920929;
                    }
                    if (p_179878_1_.áŒŠÏ()) {
                        float var3 = ((EntityPlayer)p_179878_1_).ÂµÂ();
                        if (var3 < 0.1f) {
                            var3 = 0.1f;
                        }
                        var2 *= 0.7f * var3;
                    }
                    if (p_179878_1_.Ø­áŒŠá(EntityAINearestAttackableTarget.this.Âµá€) > var2) {
                        return false;
                    }
                }
                return EntityAINearestAttackableTarget.this.HorizonCode_Horizon_È(p_179878_1_, false);
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((EntityLivingBase)p_apply_1_);
            }
        };
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (this.à > 0 && this.Âµá€.ˆÐƒØ().nextInt(this.à) != 0) {
            return false;
        }
        final double var1 = this.Ø();
        final List var2 = this.Âµá€.Ï­Ðƒà.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Âµá€.£É().Â(var1, 4.0, var1), Predicates.and(this.Ý, IEntitySelector.Ø­áŒŠá));
        Collections.sort((List<Object>)var2, this.Â);
        if (var2.isEmpty()) {
            return false;
        }
        this.Ø­áŒŠá = var2.get(0);
        return true;
    }
    
    @Override
    public void Âµá€() {
        this.Âµá€.Â(this.Ø­áŒŠá);
        super.Âµá€();
    }
    
    public static class HorizonCode_Horizon_È implements Comparator
    {
        private final Entity HorizonCode_Horizon_È;
        private static final String Â = "CL_00001622";
        
        public HorizonCode_Horizon_È(final Entity p_i1662_1_) {
            this.HorizonCode_Horizon_È = p_i1662_1_;
        }
        
        public int HorizonCode_Horizon_È(final Entity p_compare_1_, final Entity p_compare_2_) {
            final double var3 = this.HorizonCode_Horizon_È.Âµá€(p_compare_1_);
            final double var4 = this.HorizonCode_Horizon_È.Âµá€(p_compare_2_);
            return (var3 < var4) ? -1 : ((var3 > var4) ? 1 : 0);
        }
        
        @Override
        public int compare(final Object p_compare_1_, final Object p_compare_2_) {
            return this.HorizonCode_Horizon_È((Entity)p_compare_1_, (Entity)p_compare_2_);
        }
    }
}
