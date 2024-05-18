package HORIZON-6-0-SKIDPROTECTION;

public class EntityFlameFX extends EntityFX
{
    private float HorizonCode_Horizon_È;
    private static final String ÇŽá = "CL_00000907";
    
    protected EntityFlameFX(final World worldIn, final double p_i1209_2_, final double p_i1209_4_, final double p_i1209_6_, final double p_i1209_8_, final double p_i1209_10_, final double p_i1209_12_) {
        super(worldIn, p_i1209_2_, p_i1209_4_, p_i1209_6_, p_i1209_8_, p_i1209_10_, p_i1209_12_);
        this.ÇŽÉ = this.ÇŽÉ * 0.009999999776482582 + p_i1209_8_;
        this.ˆá = this.ˆá * 0.009999999776482582 + p_i1209_10_;
        this.ÇŽÕ = this.ÇŽÕ * 0.009999999776482582 + p_i1209_12_;
        double var10000 = p_i1209_2_ + (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.05f;
        var10000 = p_i1209_4_ + (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.05f;
        var10000 = p_i1209_6_ + (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.05f;
        this.HorizonCode_Horizon_È = this.Ø;
        final float áˆºÑ¢Õ = 1.0f;
        this.á = áˆºÑ¢Õ;
        this.ÂµÈ = áˆºÑ¢Õ;
        this.áˆºÑ¢Õ = áˆºÑ¢Õ;
        this.à = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + 4;
        this.ÇªÓ = true;
        this.HorizonCode_Horizon_È(48);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_, final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_, final float p_180434_8_) {
        final float var9 = (this.Ó + p_180434_3_) / this.à;
        this.Ø = this.HorizonCode_Horizon_È * (1.0f - var9 * var9 * 0.5f);
        super.HorizonCode_Horizon_È(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float p_70070_1_) {
        float var2 = (this.Ó + p_70070_1_) / this.à;
        var2 = MathHelper.HorizonCode_Horizon_È(var2, 0.0f, 1.0f);
        final int var3 = super.HorizonCode_Horizon_È(p_70070_1_);
        int var4 = var3 & 0xFF;
        final int var5 = var3 >> 16 & 0xFF;
        var4 += (int)(var2 * 15.0f * 16.0f);
        if (var4 > 240) {
            var4 = 240;
        }
        return var4 | var5 << 16;
    }
    
    @Override
    public float Â(final float p_70013_1_) {
        float var2 = (this.Ó + p_70013_1_) / this.à;
        var2 = MathHelper.HorizonCode_Horizon_È(var2, 0.0f, 1.0f);
        final float var3 = super.Â(p_70013_1_);
        return var3 * var2 + (1.0f - var2);
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
        this.ÇŽÉ *= 0.9599999785423279;
        this.ˆá *= 0.9599999785423279;
        this.ÇŽÕ *= 0.9599999785423279;
        if (this.ŠÂµà) {
            this.ÇŽÉ *= 0.699999988079071;
            this.ÇŽÕ *= 0.699999988079071;
        }
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002602";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityFlameFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        }
    }
}
