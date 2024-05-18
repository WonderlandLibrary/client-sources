package HORIZON-6-0-SKIDPROTECTION;

public class EntitySplashFX extends EntityRainFX
{
    private static final String HorizonCode_Horizon_È = "CL_00000927";
    
    protected EntitySplashFX(final World worldIn, final double p_i1230_2_, final double p_i1230_4_, final double p_i1230_6_, final double p_i1230_8_, final double p_i1230_10_, final double p_i1230_12_) {
        super(worldIn, p_i1230_2_, p_i1230_4_, p_i1230_6_);
        this.áŒŠÆ = 0.04f;
        this.ˆÏ­();
        if (p_i1230_10_ == 0.0 && (p_i1230_8_ != 0.0 || p_i1230_12_ != 0.0)) {
            this.ÇŽÉ = p_i1230_8_;
            this.ˆá = p_i1230_10_ + 0.1;
            this.ÇŽÕ = p_i1230_12_;
        }
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002580";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntitySplashFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        }
    }
}
