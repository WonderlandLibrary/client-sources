package HORIZON-6-0-SKIDPROTECTION;

public class EntityBlockDustFX extends EntityDiggingFX
{
    private static final String HorizonCode_Horizon_È = "CL_00000931";
    
    protected EntityBlockDustFX(final World worldIn, final double p_i46281_2_, final double p_i46281_4_, final double p_i46281_6_, final double p_i46281_8_, final double p_i46281_10_, final double p_i46281_12_, final IBlockState p_i46281_14_) {
        super(worldIn, p_i46281_2_, p_i46281_4_, p_i46281_6_, p_i46281_8_, p_i46281_10_, p_i46281_12_, p_i46281_14_);
        this.ÇŽÉ = p_i46281_8_;
        this.ˆá = p_i46281_10_;
        this.ÇŽÕ = p_i46281_12_;
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002576";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            final IBlockState var16 = Block.Â(p_178902_15_[0]);
            return (var16.Ý().ÂµÈ() == -1) ? null : new EntityBlockDustFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_, var16).Âµá€();
        }
    }
}
