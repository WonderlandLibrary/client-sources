package HORIZON-6-0-SKIDPROTECTION;

public class EntityPortalFX extends EntityFX
{
    private float HorizonCode_Horizon_È;
    private double ÇŽá;
    private double Ñ¢à;
    private double ÇªØ­;
    private static final String £áŒŠá = "CL_00000921";
    
    protected EntityPortalFX(final World worldIn, final double p_i46351_2_, final double p_i46351_4_, final double p_i46351_6_, final double p_i46351_8_, final double p_i46351_10_, final double p_i46351_12_) {
        super(worldIn, p_i46351_2_, p_i46351_4_, p_i46351_6_, p_i46351_8_, p_i46351_10_, p_i46351_12_);
        this.ÇŽÉ = p_i46351_8_;
        this.ˆá = p_i46351_10_;
        this.ÇŽÕ = p_i46351_12_;
        this.ŒÏ = p_i46351_2_;
        this.ÇŽá = p_i46351_2_;
        this.Çªà¢ = p_i46351_4_;
        this.Ñ¢à = p_i46351_4_;
        this.Ê = p_i46351_6_;
        this.ÇªØ­ = p_i46351_6_;
        final float var14 = this.ˆáƒ.nextFloat() * 0.6f + 0.4f;
        final float n = this.ˆáƒ.nextFloat() * 0.2f + 0.5f;
        this.Ø = n;
        this.HorizonCode_Horizon_È = n;
        final float áˆºÑ¢Õ = 1.0f * var14;
        this.á = áˆºÑ¢Õ;
        this.ÂµÈ = áˆºÑ¢Õ;
        this.áˆºÑ¢Õ = áˆºÑ¢Õ;
        this.ÂµÈ *= 0.3f;
        this.áˆºÑ¢Õ *= 0.9f;
        this.à = (int)(Math.random() * 10.0) + 40;
        this.ÇªÓ = true;
        this.HorizonCode_Horizon_È((int)(Math.random() * 8.0));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_, final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_, final float p_180434_8_) {
        float var9 = (this.Ó + p_180434_3_) / this.à;
        var9 = 1.0f - var9;
        var9 *= var9;
        var9 = 1.0f - var9;
        this.Ø = this.HorizonCode_Horizon_È * var9;
        super.HorizonCode_Horizon_È(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float p_70070_1_) {
        final int var2 = super.HorizonCode_Horizon_È(p_70070_1_);
        float var3 = this.Ó / this.à;
        var3 *= var3;
        var3 *= var3;
        final int var4 = var2 & 0xFF;
        int var5 = var2 >> 16 & 0xFF;
        var5 += (int)(var3 * 15.0f * 16.0f);
        if (var5 > 240) {
            var5 = 240;
        }
        return var4 | var5 << 16;
    }
    
    @Override
    public float Â(final float p_70013_1_) {
        final float var2 = super.Â(p_70013_1_);
        float var3 = this.Ó / this.à;
        var3 *= var3 * var3 * var3;
        return var2 * (1.0f - var3) + var3;
    }
    
    @Override
    public void á() {
        this.áŒŠà = this.ŒÏ;
        this.ŠÄ = this.Çªà¢;
        this.Ñ¢á = this.Ê;
        final float var2;
        float var1 = var2 = this.Ó / this.à;
        var1 = -var1 + var1 * var1 * 2.0f;
        var1 = 1.0f - var1;
        this.ŒÏ = this.ÇŽá + this.ÇŽÉ * var1;
        this.Çªà¢ = this.Ñ¢à + this.ˆá * var1 + (1.0f - var2);
        this.Ê = this.ÇªØ­ + this.ÇŽÕ * var1;
        if (this.Ó++ >= this.à) {
            this.á€();
        }
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002590";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityPortalFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        }
    }
}
