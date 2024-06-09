package HORIZON-6-0-SKIDPROTECTION;

public class EntityLavaFX extends EntityFX
{
    private float HorizonCode_Horizon_È;
    private static final String ÇŽá = "CL_00000912";
    
    protected EntityLavaFX(final World worldIn, final double p_i1215_2_, final double p_i1215_4_, final double p_i1215_6_) {
        super(worldIn, p_i1215_2_, p_i1215_4_, p_i1215_6_, 0.0, 0.0, 0.0);
        this.ÇŽÉ *= 0.800000011920929;
        this.ˆá *= 0.800000011920929;
        this.ÇŽÕ *= 0.800000011920929;
        this.ˆá = this.ˆáƒ.nextFloat() * 0.4f + 0.05f;
        final float áˆºÑ¢Õ = 1.0f;
        this.á = áˆºÑ¢Õ;
        this.ÂµÈ = áˆºÑ¢Õ;
        this.áˆºÑ¢Õ = áˆºÑ¢Õ;
        this.Ø *= this.ˆáƒ.nextFloat() * 2.0f + 0.2f;
        this.HorizonCode_Horizon_È = this.Ø;
        this.à = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.ÇªÓ = false;
        this.HorizonCode_Horizon_È(49);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float p_70070_1_) {
        float var2 = (this.Ó + p_70070_1_) / this.à;
        var2 = MathHelper.HorizonCode_Horizon_È(var2, 0.0f, 1.0f);
        final int var3 = super.HorizonCode_Horizon_È(p_70070_1_);
        final short var4 = 240;
        final int var5 = var3 >> 16 & 0xFF;
        return var4 | var5 << 16;
    }
    
    @Override
    public float Â(final float p_70013_1_) {
        return 1.0f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_, final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_, final float p_180434_8_) {
        final float var9 = (this.Ó + p_180434_3_) / this.à;
        this.Ø = this.HorizonCode_Horizon_È * (1.0f - var9 * var9);
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
        final float var1 = this.Ó / this.à;
        if (this.ˆáƒ.nextFloat() > var1) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.á, this.ŒÏ, this.Çªà¢, this.Ê, this.ÇŽÉ, this.ˆá, this.ÇŽÕ, new int[0]);
        }
        this.ˆá -= 0.03;
        this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
        this.ÇŽÉ *= 0.9990000128746033;
        this.ˆá *= 0.9990000128746033;
        this.ÇŽÕ *= 0.9990000128746033;
        if (this.ŠÂµà) {
            this.ÇŽÉ *= 0.699999988079071;
            this.ÇŽÕ *= 0.699999988079071;
        }
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002595";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityLavaFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_);
        }
    }
}
