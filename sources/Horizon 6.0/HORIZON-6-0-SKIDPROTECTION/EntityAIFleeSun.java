package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class EntityAIFleeSun extends EntityAIBase
{
    private EntityCreature HorizonCode_Horizon_È;
    private double Â;
    private double Ý;
    private double Ø­áŒŠá;
    private double Âµá€;
    private World Ó;
    private static final String à = "CL_00001583";
    
    public EntityAIFleeSun(final EntityCreature p_i1623_1_, final double p_i1623_2_) {
        this.HorizonCode_Horizon_È = p_i1623_1_;
        this.Âµá€ = p_i1623_2_;
        this.Ó = p_i1623_1_.Ï­Ðƒà;
        this.HorizonCode_Horizon_È(1);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (!this.Ó.ÂµÈ()) {
            return false;
        }
        if (!this.HorizonCode_Horizon_È.ˆÏ()) {
            return false;
        }
        if (!this.Ó.áˆºÑ¢Õ(new BlockPos(this.HorizonCode_Horizon_È.ŒÏ, this.HorizonCode_Horizon_È.£É().Â, this.HorizonCode_Horizon_È.Ê))) {
            return false;
        }
        final Vec3 var1 = this.Ø();
        if (var1 == null) {
            return false;
        }
        this.Â = var1.HorizonCode_Horizon_È;
        this.Ý = var1.Â;
        this.Ø­áŒŠá = var1.Ý;
        return true;
    }
    
    @Override
    public boolean Â() {
        return !this.HorizonCode_Horizon_È.Š().Ó();
    }
    
    @Override
    public void Âµá€() {
        this.HorizonCode_Horizon_È.Š().HorizonCode_Horizon_È(this.Â, this.Ý, this.Ø­áŒŠá, this.Âµá€);
    }
    
    private Vec3 Ø() {
        final Random var1 = this.HorizonCode_Horizon_È.ˆÐƒØ();
        final BlockPos var2 = new BlockPos(this.HorizonCode_Horizon_È.ŒÏ, this.HorizonCode_Horizon_È.£É().Â, this.HorizonCode_Horizon_È.Ê);
        for (int var3 = 0; var3 < 10; ++var3) {
            final BlockPos var4 = var2.Â(var1.nextInt(20) - 10, var1.nextInt(6) - 3, var1.nextInt(20) - 10);
            if (!this.Ó.áˆºÑ¢Õ(var4) && this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var4) < 0.0f) {
                return new Vec3(var4.HorizonCode_Horizon_È(), var4.Â(), var4.Ý());
            }
        }
        return null;
    }
}
