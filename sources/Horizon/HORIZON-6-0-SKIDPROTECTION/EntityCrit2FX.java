package HORIZON-6-0-SKIDPROTECTION;

public class EntityCrit2FX extends EntityFX
{
    float HorizonCode_Horizon_È;
    private static final String ÇŽá = "CL_00000899";
    
    protected EntityCrit2FX(final World worldIn, final double p_i46284_2_, final double p_i46284_4_, final double p_i46284_6_, final double p_i46284_8_, final double p_i46284_10_, final double p_i46284_12_) {
        this(worldIn, p_i46284_2_, p_i46284_4_, p_i46284_6_, p_i46284_8_, p_i46284_10_, p_i46284_12_, 1.0f);
    }
    
    protected EntityCrit2FX(final World worldIn, final double p_i46285_2_, final double p_i46285_4_, final double p_i46285_6_, final double p_i46285_8_, final double p_i46285_10_, final double p_i46285_12_, final float p_i46285_14_) {
        super(worldIn, p_i46285_2_, p_i46285_4_, p_i46285_6_, 0.0, 0.0, 0.0);
        this.ÇŽÉ *= 0.10000000149011612;
        this.ˆá *= 0.10000000149011612;
        this.ÇŽÕ *= 0.10000000149011612;
        this.ÇŽÉ += p_i46285_8_ * 0.4;
        this.ˆá += p_i46285_10_ * 0.4;
        this.ÇŽÕ += p_i46285_12_ * 0.4;
        final float áˆºÑ¢Õ = (float)(Math.random() * 0.30000001192092896 + 0.6000000238418579);
        this.á = áˆºÑ¢Õ;
        this.ÂµÈ = áˆºÑ¢Õ;
        this.áˆºÑ¢Õ = áˆºÑ¢Õ;
        this.Ø *= 0.75f;
        this.Ø *= p_i46285_14_;
        this.HorizonCode_Horizon_È = this.Ø;
        this.à = (int)(6.0 / (Math.random() * 0.8 + 0.6));
        this.à *= (int)p_i46285_14_;
        this.ÇªÓ = false;
        this.HorizonCode_Horizon_È(65);
        this.á();
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
        this.ÂµÈ *= 0.96;
        this.á *= 0.9;
        this.ÇŽÉ *= 0.699999988079071;
        this.ˆá *= 0.699999988079071;
        this.ÇŽÕ *= 0.699999988079071;
        this.ˆá -= 0.019999999552965164;
        if (this.ŠÂµà) {
            this.ÇŽÉ *= 0.699999988079071;
            this.ÇŽÕ *= 0.699999988079071;
        }
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002608";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityCrit2FX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        }
    }
    
    public static class Â implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002609";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            final EntityCrit2FX var16 = new EntityCrit2FX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
            var16.HorizonCode_Horizon_È(var16.Ó() * 0.3f, var16.à() * 0.8f, var16.Ø());
            var16.ˆÏ­();
            return var16;
        }
    }
}
