package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.Iterator;
import java.util.List;

public class EntityAIMate extends EntityAIBase
{
    private EntityAnimal Ø­áŒŠá;
    World HorizonCode_Horizon_È;
    private EntityAnimal Âµá€;
    int Â;
    double Ý;
    private static final String Ó = "CL_00001578";
    
    public EntityAIMate(final EntityAnimal p_i1619_1_, final double p_i1619_2_) {
        this.Ø­áŒŠá = p_i1619_1_;
        this.HorizonCode_Horizon_È = p_i1619_1_.Ï­Ðƒà;
        this.Ý = p_i1619_2_;
        this.HorizonCode_Horizon_È(3);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (!this.Ø­áŒŠá.ÇŽÅ()) {
            return false;
        }
        this.Âµá€ = this.Ø();
        return this.Âµá€ != null;
    }
    
    @Override
    public boolean Â() {
        return this.Âµá€.Œ() && this.Âµá€.ÇŽÅ() && this.Â < 60;
    }
    
    @Override
    public void Ý() {
        this.Âµá€ = null;
        this.Â = 0;
    }
    
    @Override
    public void Ø­áŒŠá() {
        this.Ø­áŒŠá.Ñ¢á().HorizonCode_Horizon_È(this.Âµá€, 10.0f, this.Ø­áŒŠá.áˆºà());
        this.Ø­áŒŠá.Š().HorizonCode_Horizon_È(this.Âµá€, this.Ý);
        ++this.Â;
        if (this.Â >= 60 && this.Ø­áŒŠá.Âµá€(this.Âµá€) < 9.0) {
            this.áŒŠÆ();
        }
    }
    
    private EntityAnimal Ø() {
        final float var1 = 8.0f;
        final List var2 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ø­áŒŠá.getClass(), this.Ø­áŒŠá.£É().Â(var1, var1, var1));
        double var3 = Double.MAX_VALUE;
        EntityAnimal var4 = null;
        for (final EntityAnimal var6 : var2) {
            if (this.Ø­áŒŠá.HorizonCode_Horizon_È(var6) && this.Ø­áŒŠá.Âµá€(var6) < var3) {
                var4 = var6;
                var3 = this.Ø­áŒŠá.Âµá€(var6);
            }
        }
        return var4;
    }
    
    private void áŒŠÆ() {
        final EntityAgeable var1 = this.Ø­áŒŠá.HorizonCode_Horizon_È(this.Âµá€);
        if (var1 != null) {
            EntityPlayer var2 = this.Ø­áŒŠá.ÇŽ();
            if (var2 == null && this.Âµá€.ÇŽ() != null) {
                var2 = this.Âµá€.ÇŽ();
            }
            if (var2 != null) {
                var2.HorizonCode_Horizon_È(StatList.Ñ¢á);
                if (this.Ø­áŒŠá instanceof EntityCow) {
                    var2.HorizonCode_Horizon_È(AchievementList.É);
                }
            }
            this.Ø­áŒŠá.Â(6000);
            this.Âµá€.Â(6000);
            this.Ø­áŒŠá.¥Ðƒá();
            this.Âµá€.¥Ðƒá();
            var1.Â(-24000);
            var1.Â(this.Ø­áŒŠá.ŒÏ, this.Ø­áŒŠá.Çªà¢, this.Ø­áŒŠá.Ê, 0.0f, 0.0f);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var1);
            final Random var3 = this.Ø­áŒŠá.ˆÐƒØ();
            for (int var4 = 0; var4 < 7; ++var4) {
                final double var5 = var3.nextGaussian() * 0.02;
                final double var6 = var3.nextGaussian() * 0.02;
                final double var7 = var3.nextGaussian() * 0.02;
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(EnumParticleTypes.áƒ, this.Ø­áŒŠá.ŒÏ + var3.nextFloat() * this.Ø­áŒŠá.áŒŠ * 2.0f - this.Ø­áŒŠá.áŒŠ, this.Ø­áŒŠá.Çªà¢ + 0.5 + var3.nextFloat() * this.Ø­áŒŠá.£ÂµÄ, this.Ø­áŒŠá.Ê + var3.nextFloat() * this.Ø­áŒŠá.áŒŠ * 2.0f - this.Ø­áŒŠá.áŒŠ, var5, var6, var7, new int[0]);
            }
            if (this.HorizonCode_Horizon_È.Çªà¢().Â("doMobLoot")) {
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new EntityXPOrb(this.HorizonCode_Horizon_È, this.Ø­áŒŠá.ŒÏ, this.Ø­áŒŠá.Çªà¢, this.Ø­áŒŠá.Ê, var3.nextInt(7) + 1));
            }
        }
    }
}
