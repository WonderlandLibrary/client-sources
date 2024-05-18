package HORIZON-6-0-SKIDPROTECTION;

public class EntityCloudFX extends EntityFX
{
    float HorizonCode_Horizon_È;
    private static final String ÇŽá = "CL_00000920";
    
    protected EntityCloudFX(final World worldIn, final double p_i1221_2_, final double p_i1221_4_, final double p_i1221_6_, final double p_i1221_8_, final double p_i1221_10_, final double p_i1221_12_) {
        super(worldIn, p_i1221_2_, p_i1221_4_, p_i1221_6_, 0.0, 0.0, 0.0);
        final float var14 = 2.5f;
        this.ÇŽÉ *= 0.10000000149011612;
        this.ˆá *= 0.10000000149011612;
        this.ÇŽÕ *= 0.10000000149011612;
        this.ÇŽÉ += p_i1221_8_;
        this.ˆá += p_i1221_10_;
        this.ÇŽÕ += p_i1221_12_;
        final float áˆºÑ¢Õ = 1.0f - (float)(Math.random() * 0.30000001192092896);
        this.á = áˆºÑ¢Õ;
        this.ÂµÈ = áˆºÑ¢Õ;
        this.áˆºÑ¢Õ = áˆºÑ¢Õ;
        this.Ø *= 0.75f;
        this.Ø *= var14;
        this.HorizonCode_Horizon_È = this.Ø;
        this.à = (int)(8.0 / (Math.random() * 0.8 + 0.3));
        this.à *= (int)var14;
        this.ÇªÓ = false;
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
        this.HorizonCode_Horizon_È(7 - this.Ó * 8 / this.à);
        this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
        this.ÇŽÉ *= 0.9599999785423279;
        this.ˆá *= 0.9599999785423279;
        this.ÇŽÕ *= 0.9599999785423279;
        final EntityPlayer var1 = this.Ï­Ðƒà.HorizonCode_Horizon_È(this, 2.0);
        if (var1 != null && this.Çªà¢ > var1.£É().Â) {
            this.Çªà¢ += (var1.£É().Â - this.Çªà¢) * 0.2;
            this.ˆá += (var1.ˆá - this.ˆá) * 0.2;
            this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
        }
        if (this.ŠÂµà) {
            this.ÇŽÉ *= 0.699999988079071;
            this.ÇŽÕ *= 0.699999988079071;
        }
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002591";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityCloudFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        }
    }
}
