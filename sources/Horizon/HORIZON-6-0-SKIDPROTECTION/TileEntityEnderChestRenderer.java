package HORIZON-6-0-SKIDPROTECTION;

public class TileEntityEnderChestRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private ModelChest Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000967";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/chest/ender.png");
    }
    
    public TileEntityEnderChestRenderer() {
        this.Ø­áŒŠá = new ModelChest();
    }
    
    public void HorizonCode_Horizon_È(final TileEntityEnderChest p_180540_1_, final double p_180540_2_, final double p_180540_4_, final double p_180540_6_, final float p_180540_8_, final int p_180540_9_) {
        int var10 = 0;
        if (p_180540_1_.Ø()) {
            var10 = p_180540_1_.áˆºÑ¢Õ();
        }
        if (p_180540_9_ >= 0) {
            this.HorizonCode_Horizon_È(TileEntityEnderChestRenderer.Â[p_180540_9_]);
            GlStateManager.á(5890);
            GlStateManager.Çªà¢();
            GlStateManager.HorizonCode_Horizon_È(4.0f, 4.0f, 1.0f);
            GlStateManager.Â(0.0625f, 0.0625f, 0.0625f);
            GlStateManager.á(5888);
        }
        else {
            this.HorizonCode_Horizon_È(TileEntityEnderChestRenderer.HorizonCode_Horizon_È);
        }
        GlStateManager.Çªà¢();
        GlStateManager.ŠÄ();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.Â((float)p_180540_2_, (float)p_180540_4_ + 1.0f, (float)p_180540_6_ + 1.0f);
        GlStateManager.HorizonCode_Horizon_È(1.0f, -1.0f, -1.0f);
        GlStateManager.Â(0.5f, 0.5f, 0.5f);
        short var11 = 0;
        if (var10 == 2) {
            var11 = 180;
        }
        if (var10 == 3) {
            var11 = 0;
        }
        if (var10 == 4) {
            var11 = 90;
        }
        if (var10 == 5) {
            var11 = -90;
        }
        GlStateManager.Â(var11, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(-0.5f, -0.5f, -0.5f);
        float var12 = p_180540_1_.Ó + (p_180540_1_.Âµá€ - p_180540_1_.Ó) * p_180540_8_;
        var12 = 1.0f - var12;
        var12 = 1.0f - var12 * var12 * var12;
        this.Ø­áŒŠá.HorizonCode_Horizon_È.Ó = -(var12 * 3.1415927f / 2.0f);
        this.Ø­áŒŠá.HorizonCode_Horizon_È();
        GlStateManager.Ñ¢á();
        GlStateManager.Ê();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        if (p_180540_9_ >= 0) {
            GlStateManager.á(5890);
            GlStateManager.Ê();
            GlStateManager.á(5888);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final TileEntity p_180535_1_, final double p_180535_2_, final double p_180535_4_, final double p_180535_6_, final float p_180535_8_, final int p_180535_9_) {
        this.HorizonCode_Horizon_È((TileEntityEnderChest)p_180535_1_, p_180535_2_, p_180535_4_, p_180535_6_, p_180535_8_, p_180535_9_);
    }
}
