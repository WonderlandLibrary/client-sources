package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;

public class EntityAIPlay extends EntityAIBase
{
    private EntityVillager HorizonCode_Horizon_È;
    private EntityLivingBase Â;
    private double Ý;
    private int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001605";
    
    public EntityAIPlay(final EntityVillager p_i1646_1_, final double p_i1646_2_) {
        this.HorizonCode_Horizon_È = p_i1646_1_;
        this.Ý = p_i1646_2_;
        this.HorizonCode_Horizon_È(1);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (this.HorizonCode_Horizon_È.à() >= 0) {
            return false;
        }
        if (this.HorizonCode_Horizon_È.ˆÐƒØ().nextInt(400) != 0) {
            return false;
        }
        final List var1 = this.HorizonCode_Horizon_È.Ï­Ðƒà.HorizonCode_Horizon_È(EntityVillager.class, this.HorizonCode_Horizon_È.£É().Â(6.0, 3.0, 6.0));
        double var2 = Double.MAX_VALUE;
        for (final EntityVillager var4 : var1) {
            if (var4 != this.HorizonCode_Horizon_È && !var4.¥Ðƒá() && var4.à() < 0) {
                final double var5 = var4.Âµá€(this.HorizonCode_Horizon_È);
                if (var5 > var2) {
                    continue;
                }
                var2 = var5;
                this.Â = var4;
            }
        }
        if (this.Â == null) {
            final Vec3 var6 = RandomPositionGenerator.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, 16, 3);
            if (var6 == null) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean Â() {
        return this.Ø­áŒŠá > 0;
    }
    
    @Override
    public void Âµá€() {
        if (this.Â != null) {
            this.HorizonCode_Horizon_È.ˆÏ­(true);
        }
        this.Ø­áŒŠá = 1000;
    }
    
    @Override
    public void Ý() {
        this.HorizonCode_Horizon_È.ˆÏ­(false);
        this.Â = null;
    }
    
    @Override
    public void Ø­áŒŠá() {
        --this.Ø­áŒŠá;
        if (this.Â != null) {
            if (this.HorizonCode_Horizon_È.Âµá€(this.Â) > 4.0) {
                this.HorizonCode_Horizon_È.Š().HorizonCode_Horizon_È(this.Â, this.Ý);
            }
        }
        else if (this.HorizonCode_Horizon_È.Š().Ó()) {
            final Vec3 var1 = RandomPositionGenerator.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, 16, 3);
            if (var1 == null) {
                return;
            }
            this.HorizonCode_Horizon_È.Š().HorizonCode_Horizon_È(var1.HorizonCode_Horizon_È, var1.Â, var1.Ý, this.Ý);
        }
    }
}
