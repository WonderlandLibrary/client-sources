package HORIZON-6-0-SKIDPROTECTION;

public class TileEntityEnchantmentTableRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private ModelBook Ø­áŒŠá;
    private static final String Âµá€ = "CL_00002470";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/enchanting_table_book.png");
    }
    
    public TileEntityEnchantmentTableRenderer() {
        this.Ø­áŒŠá = new ModelBook();
    }
    
    public void HorizonCode_Horizon_È(final TileEntityEnchantmentTable p_180537_1_, final double p_180537_2_, final double p_180537_4_, final double p_180537_6_, final float p_180537_8_, final int p_180537_9_) {
        GlStateManager.Çªà¢();
        GlStateManager.Â((float)p_180537_2_ + 0.5f, (float)p_180537_4_ + 0.75f, (float)p_180537_6_ + 0.5f);
        final float var10 = p_180537_1_.Âµá€ + p_180537_8_;
        GlStateManager.Â(0.0f, 0.1f + MathHelper.HorizonCode_Horizon_È(var10 * 0.1f) * 0.01f, 0.0f);
        float var11;
        for (var11 = p_180537_1_.á - p_180537_1_.ˆÏ­; var11 >= 3.1415927f; var11 -= 6.2831855f) {}
        while (var11 < -3.1415927f) {
            var11 += 6.2831855f;
        }
        final float var12 = p_180537_1_.ˆÏ­ + var11 * p_180537_8_;
        GlStateManager.Â(-var12 * 180.0f / 3.1415927f, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(80.0f, 0.0f, 0.0f, 1.0f);
        this.HorizonCode_Horizon_È(TileEntityEnchantmentTableRenderer.HorizonCode_Horizon_È);
        float var13 = p_180537_1_.à + (p_180537_1_.Ó - p_180537_1_.à) * p_180537_8_ + 0.25f;
        float var14 = p_180537_1_.à + (p_180537_1_.Ó - p_180537_1_.à) * p_180537_8_ + 0.75f;
        var13 = (var13 - MathHelper.Â((double)var13)) * 1.6f - 0.3f;
        var14 = (var14 - MathHelper.Â((double)var14)) * 1.6f - 0.3f;
        if (var13 < 0.0f) {
            var13 = 0.0f;
        }
        if (var14 < 0.0f) {
            var14 = 0.0f;
        }
        if (var13 > 1.0f) {
            var13 = 1.0f;
        }
        if (var14 > 1.0f) {
            var14 = 1.0f;
        }
        final float var15 = p_180537_1_.ÂµÈ + (p_180537_1_.áˆºÑ¢Õ - p_180537_1_.ÂµÈ) * p_180537_8_;
        GlStateManager.Å();
        this.Ø­áŒŠá.HorizonCode_Horizon_È(null, var10, var13, var14, var15, 0.0f, 0.0625f);
        GlStateManager.Ê();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final TileEntity p_180535_1_, final double p_180535_2_, final double p_180535_4_, final double p_180535_6_, final float p_180535_8_, final int p_180535_9_) {
        this.HorizonCode_Horizon_È((TileEntityEnchantmentTable)p_180535_1_, p_180535_2_, p_180535_4_, p_180535_6_, p_180535_8_, p_180535_9_);
    }
}
