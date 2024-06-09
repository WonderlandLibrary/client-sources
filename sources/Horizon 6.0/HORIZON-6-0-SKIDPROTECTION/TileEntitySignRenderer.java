package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import org.lwjgl.opengl.GL11;

public class TileEntitySignRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private final ModelSign Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000970";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/sign.png");
    }
    
    public TileEntitySignRenderer() {
        this.Ø­áŒŠá = new ModelSign();
    }
    
    public void HorizonCode_Horizon_È(final TileEntitySign p_180541_1_, final double p_180541_2_, final double p_180541_4_, final double p_180541_6_, final float p_180541_8_, final int p_180541_9_) {
        final Block var10 = p_180541_1_.ˆÏ­();
        GlStateManager.Çªà¢();
        final float var11 = 0.6666667f;
        if (var10 == Blocks.£Õ) {
            GlStateManager.Â((float)p_180541_2_ + 0.5f, (float)p_180541_4_ + 0.75f * var11, (float)p_180541_6_ + 0.5f);
            final float var12 = p_180541_1_.áˆºÑ¢Õ() * 360 / 16.0f;
            GlStateManager.Â(-var12, 0.0f, 1.0f, 0.0f);
            this.Ø­áŒŠá.Â.áˆºÑ¢Õ = true;
        }
        else {
            final int var13 = p_180541_1_.áˆºÑ¢Õ();
            float var14 = 0.0f;
            if (var13 == 2) {
                var14 = 180.0f;
            }
            if (var13 == 4) {
                var14 = 90.0f;
            }
            if (var13 == 5) {
                var14 = -90.0f;
            }
            GlStateManager.Â((float)p_180541_2_ + 0.5f, (float)p_180541_4_ + 0.75f * var11, (float)p_180541_6_ + 0.5f);
            GlStateManager.Â(-var14, 0.0f, 1.0f, 0.0f);
            GlStateManager.Â(0.0f, -0.3125f, -0.4375f);
            this.Ø­áŒŠá.Â.áˆºÑ¢Õ = false;
        }
        if (p_180541_9_ >= 0) {
            this.HorizonCode_Horizon_È(TileEntitySignRenderer.Â[p_180541_9_]);
            GlStateManager.á(5890);
            GlStateManager.Çªà¢();
            GlStateManager.HorizonCode_Horizon_È(4.0f, 2.0f, 1.0f);
            GlStateManager.Â(0.0625f, 0.0625f, 0.0625f);
            GlStateManager.á(5888);
        }
        else {
            this.HorizonCode_Horizon_È(TileEntitySignRenderer.HorizonCode_Horizon_È);
        }
        GlStateManager.ŠÄ();
        GlStateManager.Çªà¢();
        GlStateManager.HorizonCode_Horizon_È(var11, -var11, -var11);
        this.Ø­áŒŠá.HorizonCode_Horizon_È();
        GlStateManager.Ê();
        final FontRenderer var15 = this.Â();
        float var14 = 0.015625f * var11;
        GlStateManager.Â(0.0f, 0.5f * var11, 0.07f * var11);
        GlStateManager.HorizonCode_Horizon_È(var14, -var14, var14);
        GL11.glNormal3f(0.0f, 0.0f, -1.0f * var14);
        GlStateManager.HorizonCode_Horizon_È(false);
        final byte var16 = 0;
        if (p_180541_9_ < 0) {
            for (int var17 = 0; var17 < p_180541_1_.Âµá€.length; ++var17) {
                if (p_180541_1_.Âµá€[var17] != null) {
                    final IChatComponent var18 = p_180541_1_.Âµá€[var17];
                    final List var19 = GuiUtilRenderComponents.HorizonCode_Horizon_È(var18, 90, var15, false, true);
                    String var20 = (var19 != null && var19.size() > 0) ? var19.get(0).áŒŠÆ() : "";
                    if (var17 == p_180541_1_.Ó) {
                        var20 = "> " + var20 + " <";
                        var15.HorizonCode_Horizon_È(var20, -var15.HorizonCode_Horizon_È(var20) / 2, var17 * 10 - p_180541_1_.Âµá€.length * 5, var16);
                    }
                    else {
                        var15.HorizonCode_Horizon_È(var20, -var15.HorizonCode_Horizon_È(var20) / 2, var17 * 10 - p_180541_1_.Âµá€.length * 5, var16);
                    }
                }
            }
        }
        GlStateManager.HorizonCode_Horizon_È(true);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.Ê();
        if (p_180541_9_ >= 0) {
            GlStateManager.á(5890);
            GlStateManager.Ê();
            GlStateManager.á(5888);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final TileEntity p_180535_1_, final double p_180535_2_, final double p_180535_4_, final double p_180535_6_, final float p_180535_8_, final int p_180535_9_) {
        this.HorizonCode_Horizon_È((TileEntitySign)p_180535_1_, p_180535_2_, p_180535_4_, p_180535_6_, p_180535_8_, p_180535_9_);
    }
}
