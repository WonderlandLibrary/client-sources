package HORIZON-6-0-SKIDPROTECTION;

public class EntitySmokeFX extends EntityFX
{
    float HorizonCode_Horizon_È;
    private static final String ÇŽá = "CL_00000924";
    
    private EntitySmokeFX(final World worldIn, final double p_i46347_2_, final double p_i46347_4_, final double p_i46347_6_, final double p_i46347_8_, final double p_i46347_10_, final double p_i46347_12_) {
        this(worldIn, p_i46347_2_, p_i46347_4_, p_i46347_6_, p_i46347_8_, p_i46347_10_, p_i46347_12_, 1.0f);
    }
    
    protected EntitySmokeFX(final World worldIn, final double p_i46348_2_, final double p_i46348_4_, final double p_i46348_6_, final double p_i46348_8_, final double p_i46348_10_, final double p_i46348_12_, final float p_i46348_14_) {
        super(worldIn, p_i46348_2_, p_i46348_4_, p_i46348_6_, 0.0, 0.0, 0.0);
        this.ÇŽÉ *= 0.10000000149011612;
        this.ˆá *= 0.10000000149011612;
        this.ÇŽÕ *= 0.10000000149011612;
        this.ÇŽÉ += p_i46348_8_;
        this.ˆá += p_i46348_10_;
        this.ÇŽÕ += p_i46348_12_;
        final float áˆºÑ¢Õ = (float)(Math.random() * 0.30000001192092896);
        this.á = áˆºÑ¢Õ;
        this.ÂµÈ = áˆºÑ¢Õ;
        this.áˆºÑ¢Õ = áˆºÑ¢Õ;
        this.Ø *= 0.75f;
        this.Ø *= p_i46348_14_;
        this.HorizonCode_Horizon_È = this.Ø;
        this.à = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.à *= (int)p_i46348_14_;
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
        this.ˆá += 0.004;
        this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
        if (this.Çªà¢ == this.ŠÄ) {
            this.ÇŽÉ *= 1.1;
            this.ÇŽÕ *= 1.1;
        }
        this.ÇŽÉ *= 0.9599999785423279;
        this.ˆá *= 0.9599999785423279;
        this.ÇŽÕ *= 0.9599999785423279;
        if (this.ŠÂµà) {
            this.ÇŽÉ *= 0.699999988079071;
            this.ÇŽÕ *= 0.699999988079071;
        }
    }
    
    EntitySmokeFX(final World p_i46282_1_, final double p_i46282_2_, final double p_i46282_4_, final double p_i46282_6_, final double p_i46282_8_, final double p_i46282_10_, final double p_i46282_12_, final Object p_i46282_14_) {
        this(p_i46282_1_, p_i46282_2_, p_i46282_4_, p_i46282_6_, p_i46282_8_, p_i46282_10_, p_i46282_12_);
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002587";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntitySmokeFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_, null);
        }
    }
}
