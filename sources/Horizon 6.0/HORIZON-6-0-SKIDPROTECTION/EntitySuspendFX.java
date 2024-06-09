package HORIZON-6-0-SKIDPROTECTION;

public class EntitySuspendFX extends EntityFX
{
    private static final String HorizonCode_Horizon_È = "CL_00000928";
    
    protected EntitySuspendFX(final World worldIn, final double p_i1231_2_, final double p_i1231_4_, final double p_i1231_6_, final double p_i1231_8_, final double p_i1231_10_, final double p_i1231_12_) {
        super(worldIn, p_i1231_2_, p_i1231_4_ - 0.125, p_i1231_6_, p_i1231_8_, p_i1231_10_, p_i1231_12_);
        this.áˆºÑ¢Õ = 0.4f;
        this.ÂµÈ = 0.4f;
        this.á = 0.7f;
        this.HorizonCode_Horizon_È(0);
        this.HorizonCode_Horizon_È(0.01f, 0.01f);
        this.Ø *= this.ˆáƒ.nextFloat() * 0.6f + 0.2f;
        this.ÇŽÉ = p_i1231_8_ * 0.0;
        this.ˆá = p_i1231_10_ * 0.0;
        this.ÇŽÕ = p_i1231_12_ * 0.0;
        this.à = (int)(16.0 / (Math.random() * 0.8 + 0.2));
    }
    
    @Override
    public void á() {
        this.áŒŠà = this.ŒÏ;
        this.ŠÄ = this.Çªà¢;
        this.Ñ¢á = this.Ê;
        this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
        if (this.Ï­Ðƒà.Â(new BlockPos(this)).Ý().Ó() != Material.Ø) {
            this.á€();
        }
        if (this.à-- <= 0) {
            this.á€();
        }
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002579";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntitySuspendFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        }
    }
}
