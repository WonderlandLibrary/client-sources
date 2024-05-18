package HORIZON-6-0-SKIDPROTECTION;

public class EntityBreakingFX extends EntityFX
{
    private static final String HorizonCode_Horizon_È = "CL_00000897";
    
    protected EntityBreakingFX(final World worldIn, final double p_i1195_2_, final double p_i1195_4_, final double p_i1195_6_, final Item_1028566121 p_i1195_8_) {
        this(worldIn, p_i1195_2_, p_i1195_4_, p_i1195_6_, p_i1195_8_, 0);
    }
    
    protected EntityBreakingFX(final World worldIn, final double p_i1197_2_, final double p_i1197_4_, final double p_i1197_6_, final double p_i1197_8_, final double p_i1197_10_, final double p_i1197_12_, final Item_1028566121 p_i1197_14_, final int p_i1197_15_) {
        this(worldIn, p_i1197_2_, p_i1197_4_, p_i1197_6_, p_i1197_14_, p_i1197_15_);
        this.ÇŽÉ *= 0.10000000149011612;
        this.ˆá *= 0.10000000149011612;
        this.ÇŽÕ *= 0.10000000149011612;
        this.ÇŽÉ += p_i1197_8_;
        this.ˆá += p_i1197_10_;
        this.ÇŽÕ += p_i1197_12_;
    }
    
    protected EntityBreakingFX(final World worldIn, final double p_i1196_2_, final double p_i1196_4_, final double p_i1196_6_, final Item_1028566121 p_i1196_8_, final int p_i1196_9_) {
        super(worldIn, p_i1196_2_, p_i1196_4_, p_i1196_6_, 0.0, 0.0, 0.0);
        this.HorizonCode_Horizon_È(Minecraft.áŒŠà().áˆºÏ().HorizonCode_Horizon_È().HorizonCode_Horizon_È(p_i1196_8_, p_i1196_9_));
        final float áˆºÑ¢Õ = 1.0f;
        this.á = áˆºÑ¢Õ;
        this.ÂµÈ = áˆºÑ¢Õ;
        this.áˆºÑ¢Õ = áˆºÑ¢Õ;
        this.áŒŠÆ = Blocks.ˆà¢.ÇŽÕ;
        this.Ø /= 2.0f;
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 1;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_, final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_, final float p_180434_8_) {
        float var9 = (this.Â + this.Ø­áŒŠá / 4.0f) / 16.0f;
        float var10 = var9 + 0.015609375f;
        float var11 = (this.Ý + this.Âµá€ / 4.0f) / 16.0f;
        float var12 = var11 + 0.015609375f;
        final float var13 = 0.1f * this.Ø;
        if (this.£á != null) {
            var9 = this.£á.HorizonCode_Horizon_È((double)(this.Ø­áŒŠá / 4.0f * 16.0f));
            var10 = this.£á.HorizonCode_Horizon_È((double)((this.Ø­áŒŠá + 1.0f) / 4.0f * 16.0f));
            var11 = this.£á.Â((double)(this.Âµá€ / 4.0f * 16.0f));
            var12 = this.£á.Â((double)((this.Âµá€ + 1.0f) / 4.0f * 16.0f));
        }
        final float var14 = (float)(this.áŒŠà + (this.ŒÏ - this.áŒŠà) * p_180434_3_ - EntityBreakingFX.Å);
        final float var15 = (float)(this.ŠÄ + (this.Çªà¢ - this.ŠÄ) * p_180434_3_ - EntityBreakingFX.£à);
        final float var16 = (float)(this.Ñ¢á + (this.Ê - this.Ñ¢á) * p_180434_3_ - EntityBreakingFX.µà);
        p_180434_1_.Â(this.áˆºÑ¢Õ, this.ÂµÈ, this.á);
        p_180434_1_.HorizonCode_Horizon_È(var14 - p_180434_4_ * var13 - p_180434_7_ * var13, var15 - p_180434_5_ * var13, var16 - p_180434_6_ * var13 - p_180434_8_ * var13, var9, var12);
        p_180434_1_.HorizonCode_Horizon_È(var14 - p_180434_4_ * var13 + p_180434_7_ * var13, var15 + p_180434_5_ * var13, var16 - p_180434_6_ * var13 + p_180434_8_ * var13, var9, var11);
        p_180434_1_.HorizonCode_Horizon_È(var14 + p_180434_4_ * var13 + p_180434_7_ * var13, var15 + p_180434_5_ * var13, var16 + p_180434_6_ * var13 + p_180434_8_ * var13, var10, var11);
        p_180434_1_.HorizonCode_Horizon_È(var14 + p_180434_4_ * var13 - p_180434_7_ * var13, var15 - p_180434_5_ * var13, var16 + p_180434_6_ * var13 - p_180434_8_ * var13, var10, var12);
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002613";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            final int var16 = (p_178902_15_.length > 1) ? p_178902_15_[1] : 0;
            return new EntityBreakingFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_, Item_1028566121.HorizonCode_Horizon_È(p_178902_15_[0]), var16);
        }
    }
    
    public static class Â implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002612";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityBreakingFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, Items.£É);
        }
    }
    
    public static class Ý implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002611";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityBreakingFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, Items.Ñ¢à);
        }
    }
}
