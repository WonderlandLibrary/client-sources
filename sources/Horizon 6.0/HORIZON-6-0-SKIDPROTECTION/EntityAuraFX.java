package HORIZON-6-0-SKIDPROTECTION;

public class EntityAuraFX extends EntityFX
{
    private static final String HorizonCode_Horizon_È = "CL_00000929";
    
    protected EntityAuraFX(final World worldIn, final double p_i1232_2_, final double p_i1232_4_, final double p_i1232_6_, final double p_i1232_8_, final double p_i1232_10_, final double p_i1232_12_) {
        super(worldIn, p_i1232_2_, p_i1232_4_, p_i1232_6_, p_i1232_8_, p_i1232_10_, p_i1232_12_);
        final float var14 = this.ˆáƒ.nextFloat() * 0.1f + 0.2f;
        this.áˆºÑ¢Õ = var14;
        this.ÂµÈ = var14;
        this.á = var14;
        this.HorizonCode_Horizon_È(0);
        this.HorizonCode_Horizon_È(0.02f, 0.02f);
        this.Ø *= this.ˆáƒ.nextFloat() * 0.6f + 0.5f;
        this.ÇŽÉ *= 0.019999999552965164;
        this.ˆá *= 0.019999999552965164;
        this.ÇŽÕ *= 0.019999999552965164;
        this.à = (int)(20.0 / (Math.random() * 0.8 + 0.2));
        this.ÇªÓ = true;
    }
    
    @Override
    public void á() {
        this.áŒŠà = this.ŒÏ;
        this.ŠÄ = this.Çªà¢;
        this.Ñ¢á = this.Ê;
        this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
        this.ÇŽÉ *= 0.99;
        this.ˆá *= 0.99;
        this.ÇŽÕ *= 0.99;
        if (this.à-- <= 0) {
            this.á€();
        }
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002577";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityAuraFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        }
    }
    
    public static class Â implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002578";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            final EntityAuraFX var16 = new EntityAuraFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
            var16.HorizonCode_Horizon_È(82);
            var16.HorizonCode_Horizon_È(1.0f, 1.0f, 1.0f);
            return var16;
        }
    }
}
