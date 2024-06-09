package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;

public class EntityAIFollowParent extends EntityAIBase
{
    EntityAnimal HorizonCode_Horizon_È;
    EntityAnimal Â;
    double Ý;
    private int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001586";
    
    public EntityAIFollowParent(final EntityAnimal p_i1626_1_, final double p_i1626_2_) {
        this.HorizonCode_Horizon_È = p_i1626_1_;
        this.Ý = p_i1626_2_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (this.HorizonCode_Horizon_È.à() >= 0) {
            return false;
        }
        final List var1 = this.HorizonCode_Horizon_È.Ï­Ðƒà.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.getClass(), this.HorizonCode_Horizon_È.£É().Â(8.0, 4.0, 8.0));
        EntityAnimal var2 = null;
        double var3 = Double.MAX_VALUE;
        for (final EntityAnimal var5 : var1) {
            if (var5.à() >= 0) {
                final double var6 = this.HorizonCode_Horizon_È.Âµá€(var5);
                if (var6 > var3) {
                    continue;
                }
                var3 = var6;
                var2 = var5;
            }
        }
        if (var2 == null) {
            return false;
        }
        if (var3 < 9.0) {
            return false;
        }
        this.Â = var2;
        return true;
    }
    
    @Override
    public boolean Â() {
        if (this.HorizonCode_Horizon_È.à() >= 0) {
            return false;
        }
        if (!this.Â.Œ()) {
            return false;
        }
        final double var1 = this.HorizonCode_Horizon_È.Âµá€(this.Â);
        return var1 >= 9.0 && var1 <= 256.0;
    }
    
    @Override
    public void Âµá€() {
        this.Ø­áŒŠá = 0;
    }
    
    @Override
    public void Ý() {
        this.Â = null;
    }
    
    @Override
    public void Ø­áŒŠá() {
        final int ø­áŒŠá = this.Ø­áŒŠá - 1;
        this.Ø­áŒŠá = ø­áŒŠá;
        if (ø­áŒŠá <= 0) {
            this.Ø­áŒŠá = 10;
            this.HorizonCode_Horizon_È.Š().HorizonCode_Horizon_È(this.Â, this.Ý);
        }
    }
}
