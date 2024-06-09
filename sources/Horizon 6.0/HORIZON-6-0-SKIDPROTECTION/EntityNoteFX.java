package HORIZON-6-0-SKIDPROTECTION;

public class EntityNoteFX extends EntityFX
{
    float HorizonCode_Horizon_È;
    private static final String ÇŽá = "CL_00000913";
    
    protected EntityNoteFX(final World worldIn, final double p_i46353_2_, final double p_i46353_4_, final double p_i46353_6_, final double p_i46353_8_, final double p_i46353_10_, final double p_i46353_12_) {
        this(worldIn, p_i46353_2_, p_i46353_4_, p_i46353_6_, p_i46353_8_, p_i46353_10_, p_i46353_12_, 2.0f);
    }
    
    protected EntityNoteFX(final World worldIn, final double p_i1217_2_, final double p_i1217_4_, final double p_i1217_6_, final double p_i1217_8_, final double p_i1217_10_, final double p_i1217_12_, final float p_i1217_14_) {
        super(worldIn, p_i1217_2_, p_i1217_4_, p_i1217_6_, 0.0, 0.0, 0.0);
        this.ÇŽÉ *= 0.009999999776482582;
        this.ˆá *= 0.009999999776482582;
        this.ÇŽÕ *= 0.009999999776482582;
        this.ˆá += 0.2;
        this.áˆºÑ¢Õ = MathHelper.HorizonCode_Horizon_È(((float)p_i1217_8_ + 0.0f) * 3.1415927f * 2.0f) * 0.65f + 0.35f;
        this.ÂµÈ = MathHelper.HorizonCode_Horizon_È(((float)p_i1217_8_ + 0.33333334f) * 3.1415927f * 2.0f) * 0.65f + 0.35f;
        this.á = MathHelper.HorizonCode_Horizon_È(((float)p_i1217_8_ + 0.6666667f) * 3.1415927f * 2.0f) * 0.65f + 0.35f;
        this.Ø *= 0.75f;
        this.Ø *= p_i1217_14_;
        this.HorizonCode_Horizon_È = this.Ø;
        this.à = 6;
        this.ÇªÓ = false;
        this.HorizonCode_Horizon_È(64);
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
        this.ÇŽÉ *= 0.6600000262260437;
        this.ˆá *= 0.6600000262260437;
        this.ÇŽÕ *= 0.6600000262260437;
        if (this.ŠÂµà) {
            this.ÇŽÉ *= 0.699999988079071;
            this.ÇŽÕ *= 0.699999988079071;
        }
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002592";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityNoteFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        }
    }
}
