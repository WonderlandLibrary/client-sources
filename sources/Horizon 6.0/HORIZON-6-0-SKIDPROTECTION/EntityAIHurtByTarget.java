package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;

public class EntityAIHurtByTarget extends EntityAITarget
{
    private boolean HorizonCode_Horizon_È;
    private int Â;
    private final Class[] Ý;
    private static final String Ø­áŒŠá = "CL_00001619";
    
    public EntityAIHurtByTarget(final EntityCreature p_i45885_1_, final boolean p_i45885_2_, final Class... p_i45885_3_) {
        super(p_i45885_1_, false);
        this.HorizonCode_Horizon_È = p_i45885_2_;
        this.Ý = p_i45885_3_;
        this.HorizonCode_Horizon_È(1);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        final int var1 = this.Âµá€.¥Å();
        return var1 != this.Â && this.HorizonCode_Horizon_È(this.Âµá€.Çªà(), false);
    }
    
    @Override
    public void Âµá€() {
        this.Âµá€.Â(this.Âµá€.Çªà());
        this.Â = this.Âµá€.¥Å();
        if (this.HorizonCode_Horizon_È) {
            final double var1 = this.Ø();
            final List var2 = this.Âµá€.Ï­Ðƒà.HorizonCode_Horizon_È(this.Âµá€.getClass(), new AxisAlignedBB(this.Âµá€.ŒÏ, this.Âµá€.Çªà¢, this.Âµá€.Ê, this.Âµá€.ŒÏ + 1.0, this.Âµá€.Çªà¢ + 1.0, this.Âµá€.Ê + 1.0).Â(var1, 10.0, var1));
            for (final EntityCreature var4 : var2) {
                if (this.Âµá€ != var4 && var4.Ñ¢Ó() == null && !var4.Ø­áŒŠá(this.Âµá€.Çªà())) {
                    boolean var5 = false;
                    for (final Class var9 : this.Ý) {
                        if (var4.getClass() == var9) {
                            var5 = true;
                            break;
                        }
                    }
                    if (var5) {
                        continue;
                    }
                    this.HorizonCode_Horizon_È(var4, this.Âµá€.Çªà());
                }
            }
        }
        super.Âµá€();
    }
    
    protected void HorizonCode_Horizon_È(final EntityCreature p_179446_1_, final EntityLivingBase p_179446_2_) {
        p_179446_1_.Â(p_179446_2_);
    }
}
