package HORIZON-6-0-SKIDPROTECTION;

public class EntityHeartFX extends EntityFX
{
    float HorizonCode_Horizon_È;
    private static final String ÇŽá = "CL_00000909";
    
    protected EntityHeartFX(final World worldIn, final double p_i1211_2_, final double p_i1211_4_, final double p_i1211_6_, final double p_i1211_8_, final double p_i1211_10_, final double p_i1211_12_) {
        this(worldIn, p_i1211_2_, p_i1211_4_, p_i1211_6_, p_i1211_8_, p_i1211_10_, p_i1211_12_, 2.0f);
    }
    
    protected EntityHeartFX(final World worldIn, final double p_i46354_2_, final double p_i46354_4_, final double p_i46354_6_, final double p_i46354_8_, final double p_i46354_10_, final double p_i46354_12_, final float p_i46354_14_) {
        super(worldIn, p_i46354_2_, p_i46354_4_, p_i46354_6_, 0.0, 0.0, 0.0);
        this.ÇŽÉ *= 0.009999999776482582;
        this.ˆá *= 0.009999999776482582;
        this.ÇŽÕ *= 0.009999999776482582;
        this.ˆá += 0.1;
        this.Ø *= 0.75f;
        this.Ø *= p_i46354_14_;
        this.HorizonCode_Horizon_È = this.Ø;
        this.à = 16;
        this.ÇªÓ = false;
        this.HorizonCode_Horizon_È(80);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_, final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_, final float p_180434_8_) {
        float var9 = (this.Ó + p_180434_3_) / this.à * 32.0f;
        var9 = MathHelper.HorizonCode_Horizon_È(var9, 0.0f, 1.0f);
        this.Ø = this.HorizonCode_Horizon_È * var9;
        super.HorizonCode_Horizon_È(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
    }
    
    @Override
    public void á() {
        this.áŒŠà = this.ŒÏ;
        this.ŠÄ = this.Çªà¢;
        this.Ñ¢á = this.Ê;
        if (this.Ó++ >= this.à) {
            this.á€();
        }
        this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
        if (this.Çªà¢ == this.ŠÄ) {
            this.ÇŽÉ *= 1.1;
            this.ÇŽÕ *= 1.1;
        }
        this.ÇŽÉ *= 0.8600000143051147;
        this.ˆá *= 0.8600000143051147;
        this.ÇŽÕ *= 0.8600000143051147;
        if (this.ŠÂµà) {
            this.ÇŽÉ *= 0.699999988079071;
            this.ÇŽÕ *= 0.699999988079071;
        }
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002600";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            final EntityHeartFX var16 = new EntityHeartFX(worldIn, p_178902_3_, p_178902_5_ + 0.5, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
            var16.HorizonCode_Horizon_È(81);
            var16.HorizonCode_Horizon_È(1.0f, 1.0f, 1.0f);
            return var16;
        }
    }
    
    public static class Â implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002599";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityHeartFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        }
    }
}
