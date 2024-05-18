package HORIZON-6-0-SKIDPROTECTION;

public class EntityExplodeFX extends EntityFX
{
    private static final String HorizonCode_Horizon_È = "CL_00000903";
    
    protected EntityExplodeFX(final World worldIn, final double p_i1205_2_, final double p_i1205_4_, final double p_i1205_6_, final double p_i1205_8_, final double p_i1205_10_, final double p_i1205_12_) {
        super(worldIn, p_i1205_2_, p_i1205_4_, p_i1205_6_, p_i1205_8_, p_i1205_10_, p_i1205_12_);
        this.ÇŽÉ = p_i1205_8_ + (Math.random() * 2.0 - 1.0) * 0.05000000074505806;
        this.ˆá = p_i1205_10_ + (Math.random() * 2.0 - 1.0) * 0.05000000074505806;
        this.ÇŽÕ = p_i1205_12_ + (Math.random() * 2.0 - 1.0) * 0.05000000074505806;
        final float áˆºÑ¢Õ = this.ˆáƒ.nextFloat() * 0.3f + 0.7f;
        this.á = áˆºÑ¢Õ;
        this.ÂµÈ = áˆºÑ¢Õ;
        this.áˆºÑ¢Õ = áˆºÑ¢Õ;
        this.Ø = this.ˆáƒ.nextFloat() * this.ˆáƒ.nextFloat() * 6.0f + 1.0f;
        this.à = (int)(16.0 / (this.ˆáƒ.nextFloat() * 0.8 + 0.2)) + 2;
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
        this.ÇŽÉ *= 0.8999999761581421;
        this.ˆá *= 0.8999999761581421;
        this.ÇŽÕ *= 0.8999999761581421;
        if (this.ŠÂµà) {
            this.ÇŽÉ *= 0.699999988079071;
            this.ÇŽÕ *= 0.699999988079071;
        }
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002604";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityExplodeFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        }
    }
}
