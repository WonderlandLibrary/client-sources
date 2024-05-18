package HORIZON-6-0-SKIDPROTECTION;

import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import org.apache.logging.log4j.LogManager;
import com.google.common.base.Predicate;
import org.apache.logging.log4j.Logger;

public class EntityAIFindEntityNearest extends EntityAIBase
{
    private static final Logger HorizonCode_Horizon_È;
    private EntityLiving Â;
    private final Predicate Ý;
    private final EntityAINearestAttackableTarget.HorizonCode_Horizon_È Ø­áŒŠá;
    private EntityLivingBase Âµá€;
    private Class Ó;
    private static final String à = "CL_00002250";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public EntityAIFindEntityNearest(final EntityLiving p_i45884_1_, final Class p_i45884_2_) {
        this.Â = p_i45884_1_;
        this.Ó = p_i45884_2_;
        if (p_i45884_1_ instanceof EntityCreature) {
            EntityAIFindEntityNearest.HorizonCode_Horizon_È.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
        }
        this.Ý = (Predicate)new Predicate() {
            private static final String Â = "CL_00002249";
            
            public boolean HorizonCode_Horizon_È(final EntityLivingBase p_179876_1_) {
                double var2 = EntityAIFindEntityNearest.this.Ø();
                if (p_179876_1_.Çªà¢()) {
                    var2 *= 0.800000011920929;
                }
                return !p_179876_1_.áŒŠÏ() && p_179876_1_.Ø­áŒŠá(EntityAIFindEntityNearest.this.Â) <= var2 && EntityAITarget.HorizonCode_Horizon_È(EntityAIFindEntityNearest.this.Â, p_179876_1_, false, true);
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((EntityLivingBase)p_apply_1_);
            }
        };
        this.Ø­áŒŠá = new EntityAINearestAttackableTarget.HorizonCode_Horizon_È(p_i45884_1_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        final double var1 = this.Ø();
        final List var2 = this.Â.Ï­Ðƒà.HorizonCode_Horizon_È(this.Ó, this.Â.£É().Â(var1, 4.0, var1), this.Ý);
        Collections.sort((List<Object>)var2, this.Ø­áŒŠá);
        if (var2.isEmpty()) {
            return false;
        }
        this.Âµá€ = var2.get(0);
        return true;
    }
    
    @Override
    public boolean Â() {
        final EntityLivingBase var1 = this.Â.Ñ¢Ó();
        if (var1 == null) {
            return false;
        }
        if (!var1.Œ()) {
            return false;
        }
        final double var2 = this.Ø();
        return this.Â.Âµá€(var1) <= var2 * var2 && (!(var1 instanceof EntityPlayerMP) || !((EntityPlayerMP)var1).Ý.Ý());
    }
    
    @Override
    public void Âµá€() {
        this.Â.Â(this.Âµá€);
        super.Âµá€();
    }
    
    @Override
    public void Ý() {
        this.Â.Â((EntityLivingBase)null);
        super.Âµá€();
    }
    
    protected double Ø() {
        final IAttributeInstance var1 = this.Â.HorizonCode_Horizon_È(SharedMonsterAttributes.Â);
        return (var1 == null) ? 16.0 : var1.Âµá€();
    }
}
