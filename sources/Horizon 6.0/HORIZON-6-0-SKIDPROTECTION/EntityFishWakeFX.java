package HORIZON-6-0-SKIDPROTECTION;

public class EntityFishWakeFX extends EntityFX
{
    private static final String HorizonCode_Horizon_È = "CL_00000933";
    
    protected EntityFishWakeFX(final World worldIn, final double p_i45073_2_, final double p_i45073_4_, final double p_i45073_6_, final double p_i45073_8_, final double p_i45073_10_, final double p_i45073_12_) {
        super(worldIn, p_i45073_2_, p_i45073_4_, p_i45073_6_, 0.0, 0.0, 0.0);
        this.ÇŽÉ *= 0.30000001192092896;
        this.ˆá = Math.random() * 0.20000000298023224 + 0.10000000149011612;
        this.ÇŽÕ *= 0.30000001192092896;
        this.áˆºÑ¢Õ = 1.0f;
        this.ÂµÈ = 1.0f;
        this.á = 1.0f;
        this.HorizonCode_Horizon_È(19);
        this.HorizonCode_Horizon_È(0.01f, 0.01f);
        this.à = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.áŒŠÆ = 0.0f;
        this.ÇŽÉ = p_i45073_8_;
        this.ˆá = p_i45073_10_;
        this.ÇŽÕ = p_i45073_12_;
    }
    
    @Override
    public void á() {
        this.áŒŠà = this.ŒÏ;
        this.ŠÄ = this.Çªà¢;
        this.Ñ¢á = this.Ê;
        this.ˆá -= this.áŒŠÆ;
        this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
        this.ÇŽÉ *= 0.9800000190734863;
        this.ˆá *= 0.9800000190734863;
        this.ÇŽÕ *= 0.9800000190734863;
        final int var1 = 60 - this.à;
        final float var2 = var1 * 0.001f;
        this.HorizonCode_Horizon_È(var2, var2);
        this.HorizonCode_Horizon_È(19 + var1 % 4);
        if (this.à-- <= 0) {
            this.á€();
        }
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002573";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityFishWakeFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        }
    }
}
