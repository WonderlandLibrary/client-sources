package HORIZON-6-0-SKIDPROTECTION;

public class EntityEnchantmentTableParticleFX extends EntityFX
{
    private float HorizonCode_Horizon_È;
    private double ÇŽá;
    private double Ñ¢à;
    private double ÇªØ­;
    private static final String £áŒŠá = "CL_00000902";
    
    protected EntityEnchantmentTableParticleFX(final World worldIn, final double p_i1204_2_, final double p_i1204_4_, final double p_i1204_6_, final double p_i1204_8_, final double p_i1204_10_, final double p_i1204_12_) {
        super(worldIn, p_i1204_2_, p_i1204_4_, p_i1204_6_, p_i1204_8_, p_i1204_10_, p_i1204_12_);
        this.ÇŽÉ = p_i1204_8_;
        this.ˆá = p_i1204_10_;
        this.ÇŽÕ = p_i1204_12_;
        this.ÇŽá = p_i1204_2_;
        this.Ñ¢à = p_i1204_4_;
        this.ÇªØ­ = p_i1204_6_;
        final double n = p_i1204_2_ + p_i1204_8_;
        this.áŒŠà = n;
        this.ŒÏ = n;
        final double n2 = p_i1204_4_ + p_i1204_10_;
        this.ŠÄ = n2;
        this.Çªà¢ = n2;
        final double n3 = p_i1204_6_ + p_i1204_12_;
        this.Ñ¢á = n3;
        this.Ê = n3;
        final float var14 = this.ˆáƒ.nextFloat() * 0.6f + 0.4f;
        final float n4 = this.ˆáƒ.nextFloat() * 0.5f + 0.2f;
        this.Ø = n4;
        this.HorizonCode_Horizon_È = n4;
        final float áˆºÑ¢Õ = 1.0f * var14;
        this.á = áˆºÑ¢Õ;
        this.ÂµÈ = áˆºÑ¢Õ;
        this.áˆºÑ¢Õ = áˆºÑ¢Õ;
        this.ÂµÈ *= 0.9f;
        this.áˆºÑ¢Õ *= 0.9f;
        this.à = (int)(Math.random() * 10.0) + 30;
        this.ÇªÓ = true;
        this.HorizonCode_Horizon_È((int)(Math.random() * 26.0 + 1.0 + 224.0));
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
        var3 *= var3;
        var3 *= var3;
        return var2 * (1.0f - var3) + var3;
    }
    
    @Override
    public void á() {
        this.áŒŠà = this.ŒÏ;
        this.ŠÄ = this.Çªà¢;
        this.Ñ¢á = this.Ê;
        float var1 = this.Ó / this.à;
        var1 = 1.0f - var1;
        float var2 = 1.0f - var1;
        var2 *= var2;
        var2 *= var2;
        this.ŒÏ = this.ÇŽá + this.ÇŽÉ * var1;
        this.Çªà¢ = this.Ñ¢à + this.ˆá * var1 - var2 * 1.2f;
        this.Ê = this.ÇªØ­ + this.ÇŽÕ * var1;
        if (this.Ó++ >= this.à) {
            this.á€();
        }
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002605";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityEnchantmentTableParticleFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        }
    }
}
