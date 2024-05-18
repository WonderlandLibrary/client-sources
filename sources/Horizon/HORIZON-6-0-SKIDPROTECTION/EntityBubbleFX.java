package HORIZON-6-0-SKIDPROTECTION;

public class EntityBubbleFX extends EntityFX
{
    private static final String HorizonCode_Horizon_È = "CL_00000898";
    
    protected EntityBubbleFX(final World worldIn, final double p_i1198_2_, final double p_i1198_4_, final double p_i1198_6_, final double p_i1198_8_, final double p_i1198_10_, final double p_i1198_12_) {
        super(worldIn, p_i1198_2_, p_i1198_4_, p_i1198_6_, p_i1198_8_, p_i1198_10_, p_i1198_12_);
        this.áˆºÑ¢Õ = 1.0f;
        this.ÂµÈ = 1.0f;
        this.á = 1.0f;
        this.HorizonCode_Horizon_È(32);
        this.HorizonCode_Horizon_È(0.02f, 0.02f);
        this.Ø *= this.ˆáƒ.nextFloat() * 0.6f + 0.2f;
        this.ÇŽÉ = p_i1198_8_ * 0.20000000298023224 + (Math.random() * 2.0 - 1.0) * 0.019999999552965164;
        this.ˆá = p_i1198_10_ * 0.20000000298023224 + (Math.random() * 2.0 - 1.0) * 0.019999999552965164;
        this.ÇŽÕ = p_i1198_12_ * 0.20000000298023224 + (Math.random() * 2.0 - 1.0) * 0.019999999552965164;
        this.à = (int)(8.0 / (Math.random() * 0.8 + 0.2));
    }
    
    @Override
    public void á() {
        this.áŒŠà = this.ŒÏ;
        this.ŠÄ = this.Çªà¢;
        this.Ñ¢á = this.Ê;
        this.ˆá += 0.002;
        this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
        this.ÇŽÉ *= 0.8500000238418579;
        this.ˆá *= 0.8500000238418579;
        this.ÇŽÕ *= 0.8500000238418579;
        if (this.Ï­Ðƒà.Â(new BlockPos(this)).Ý().Ó() != Material.Ø) {
            this.á€();
        }
        if (this.à-- <= 0) {
            this.á€();
        }
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002610";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityBubbleFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
        }
    }
}
