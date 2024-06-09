package HORIZON-6-0-SKIDPROTECTION;

public class EntityDiggingFX extends EntityFX
{
    private IBlockState HorizonCode_Horizon_È;
    private static final String ÇŽá = "CL_00000932";
    
    protected EntityDiggingFX(final World worldIn, final double p_i46280_2_, final double p_i46280_4_, final double p_i46280_6_, final double p_i46280_8_, final double p_i46280_10_, final double p_i46280_12_, final IBlockState p_i46280_14_) {
        super(worldIn, p_i46280_2_, p_i46280_4_, p_i46280_6_, p_i46280_8_, p_i46280_10_, p_i46280_12_);
        this.HorizonCode_Horizon_È = p_i46280_14_;
        this.HorizonCode_Horizon_È(Minecraft.áŒŠà().Ô().HorizonCode_Horizon_È().HorizonCode_Horizon_È(p_i46280_14_));
        this.áŒŠÆ = p_i46280_14_.Ý().ÇŽÕ;
        final float áˆºÑ¢Õ = 0.6f;
        this.á = áˆºÑ¢Õ;
        this.ÂµÈ = áˆºÑ¢Õ;
        this.áˆºÑ¢Õ = áˆºÑ¢Õ;
        this.Ø /= 2.0f;
    }
    
    public EntityDiggingFX HorizonCode_Horizon_È(final BlockPos p_174846_1_) {
        if (this.HorizonCode_Horizon_È.Ý() == Blocks.Ø­áŒŠá) {
            return this;
        }
        final int var2 = this.HorizonCode_Horizon_È.Ý().Ø­áŒŠá((IBlockAccess)this.Ï­Ðƒà, p_174846_1_);
        this.áˆºÑ¢Õ *= (var2 >> 16 & 0xFF) / 255.0f;
        this.ÂµÈ *= (var2 >> 8 & 0xFF) / 255.0f;
        this.á *= (var2 & 0xFF) / 255.0f;
        return this;
    }
    
    public EntityDiggingFX Âµá€() {
        final Block var1 = this.HorizonCode_Horizon_È.Ý();
        if (var1 == Blocks.Ø­áŒŠá) {
            return this;
        }
        final int var2 = var1.Âµá€(this.HorizonCode_Horizon_È);
        this.áˆºÑ¢Õ *= (var2 >> 16 & 0xFF) / 255.0f;
        this.ÂµÈ *= (var2 >> 8 & 0xFF) / 255.0f;
        this.á *= (var2 & 0xFF) / 255.0f;
        return this;
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
        final float var14 = (float)(this.áŒŠà + (this.ŒÏ - this.áŒŠà) * p_180434_3_ - EntityDiggingFX.Å);
        final float var15 = (float)(this.ŠÄ + (this.Çªà¢ - this.ŠÄ) * p_180434_3_ - EntityDiggingFX.£à);
        final float var16 = (float)(this.Ñ¢á + (this.Ê - this.Ñ¢á) * p_180434_3_ - EntityDiggingFX.µà);
        p_180434_1_.Â(this.áˆºÑ¢Õ, this.ÂµÈ, this.á);
        p_180434_1_.HorizonCode_Horizon_È(var14 - p_180434_4_ * var13 - p_180434_7_ * var13, var15 - p_180434_5_ * var13, var16 - p_180434_6_ * var13 - p_180434_8_ * var13, var9, var12);
        p_180434_1_.HorizonCode_Horizon_È(var14 - p_180434_4_ * var13 + p_180434_7_ * var13, var15 + p_180434_5_ * var13, var16 - p_180434_6_ * var13 + p_180434_8_ * var13, var9, var11);
        p_180434_1_.HorizonCode_Horizon_È(var14 + p_180434_4_ * var13 + p_180434_7_ * var13, var15 + p_180434_5_ * var13, var16 + p_180434_6_ * var13 + p_180434_8_ * var13, var10, var11);
        p_180434_1_.HorizonCode_Horizon_È(var14 + p_180434_4_ * var13 - p_180434_7_ * var13, var15 - p_180434_5_ * var13, var16 + p_180434_6_ * var13 - p_180434_8_ * var13, var10, var12);
    }
    
    public static class HorizonCode_Horizon_È implements IParticleFactory
    {
        private static final String HorizonCode_Horizon_È = "CL_00002575";
        
        @Override
        public EntityFX HorizonCode_Horizon_È(final int p_178902_1_, final World worldIn, final double p_178902_3_, final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_, final double p_178902_13_, final int... p_178902_15_) {
            return new EntityDiggingFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_, Block.Â(p_178902_15_[0])).Âµá€();
        }
    }
}
