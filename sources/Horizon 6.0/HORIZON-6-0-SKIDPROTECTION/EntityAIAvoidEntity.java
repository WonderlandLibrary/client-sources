package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import com.google.common.base.Predicates;
import com.google.common.base.Predicate;

public class EntityAIAvoidEntity extends EntityAIBase
{
    public final Predicate HorizonCode_Horizon_È;
    protected EntityCreature Â;
    private double Ø­áŒŠá;
    private double Âµá€;
    protected Entity Ý;
    private float Ó;
    private PathEntity à;
    private PathNavigate Ø;
    private Predicate áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00001574";
    
    public EntityAIAvoidEntity(final EntityCreature p_i45890_1_, final Predicate p_i45890_2_, final float p_i45890_3_, final double p_i45890_4_, final double p_i45890_6_) {
        this.HorizonCode_Horizon_È = (Predicate)new Predicate() {
            private static final String Â = "CL_00001575";
            
            public boolean HorizonCode_Horizon_È(final Entity p_180419_1_) {
                return p_180419_1_.Œ() && EntityAIAvoidEntity.this.Â.Ø­Ñ¢á€().HorizonCode_Horizon_È(p_180419_1_);
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((Entity)p_apply_1_);
            }
        };
        this.Â = p_i45890_1_;
        this.áŒŠÆ = p_i45890_2_;
        this.Ó = p_i45890_3_;
        this.Ø­áŒŠá = p_i45890_4_;
        this.Âµá€ = p_i45890_6_;
        this.Ø = p_i45890_1_.Š();
        this.HorizonCode_Horizon_È(1);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        final List var1 = this.Â.Ï­Ðƒà.HorizonCode_Horizon_È(this.Â, this.Â.£É().Â(this.Ó, 3.0, this.Ó), Predicates.and(new Predicate[] { IEntitySelector.Ø­áŒŠá, this.HorizonCode_Horizon_È, this.áŒŠÆ }));
        if (var1.isEmpty()) {
            return false;
        }
        this.Ý = var1.get(0);
        final Vec3 var2 = RandomPositionGenerator.Â(this.Â, 16, 7, new Vec3(this.Ý.ŒÏ, this.Ý.Çªà¢, this.Ý.Ê));
        if (var2 == null) {
            return false;
        }
        if (this.Ý.Âµá€(var2.HorizonCode_Horizon_È, var2.Â, var2.Ý) < this.Ý.Âµá€(this.Â)) {
            return false;
        }
        this.à = this.Ø.HorizonCode_Horizon_È(var2.HorizonCode_Horizon_È, var2.Â, var2.Ý);
        return this.à != null && this.à.HorizonCode_Horizon_È(var2);
    }
    
    @Override
    public boolean Â() {
        return !this.Ø.Ó();
    }
    
    @Override
    public void Âµá€() {
        this.Ø.HorizonCode_Horizon_È(this.à, this.Ø­áŒŠá);
    }
    
    @Override
    public void Ý() {
        this.Ý = null;
    }
    
    @Override
    public void Ø­áŒŠá() {
        if (this.Â.Âµá€(this.Ý) < 49.0) {
            this.Â.Š().HorizonCode_Horizon_È(this.Âµá€);
        }
        else {
            this.Â.Š().HorizonCode_Horizon_È(this.Ø­áŒŠá);
        }
    }
}
