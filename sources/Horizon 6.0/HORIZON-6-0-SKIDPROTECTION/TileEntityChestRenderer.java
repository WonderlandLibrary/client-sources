package HORIZON-6-0-SKIDPROTECTION;

import java.util.Calendar;

public class TileEntityChestRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final ResourceLocation_1975012498 Ø­áŒŠá;
    private static final ResourceLocation_1975012498 Âµá€;
    private static final ResourceLocation_1975012498 Ó;
    private static final ResourceLocation_1975012498 à;
    private static final ResourceLocation_1975012498 Ø;
    private ModelChest áŒŠÆ;
    private ModelChest áˆºÑ¢Õ;
    private boolean ÂµÈ;
    private static final String á = "CL_00000965";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/entity/chest/trapped_double.png");
        Ø­áŒŠá = new ResourceLocation_1975012498("textures/entity/chest/christmas_double.png");
        Âµá€ = new ResourceLocation_1975012498("textures/entity/chest/normal_double.png");
        Ó = new ResourceLocation_1975012498("textures/entity/chest/trapped.png");
        à = new ResourceLocation_1975012498("textures/entity/chest/christmas.png");
        Ø = new ResourceLocation_1975012498("textures/entity/chest/normal.png");
    }
    
    public TileEntityChestRenderer() {
        this.áŒŠÆ = new ModelChest();
        this.áˆºÑ¢Õ = new ModelLargeChest();
        final Calendar var1 = Calendar.getInstance();
        if (var1.get(2) + 1 == 12 && var1.get(5) >= 24 && var1.get(5) <= 26) {
            this.ÂµÈ = true;
        }
    }
    
    public void HorizonCode_Horizon_È(final TileEntityChest p_180538_1_, final double p_180538_2_, final double p_180538_4_, final double p_180538_6_, final float p_180538_8_, final int p_180538_9_) {
        int var10;
        if (!p_180538_1_.Ø()) {
            var10 = 0;
        }
        else {
            final Block var11 = p_180538_1_.ˆÏ­();
            var10 = p_180538_1_.áˆºÑ¢Õ();
            if (var11 instanceof BlockChest && var10 == 0) {
                ((BlockChest)var11).Âµá€(p_180538_1_.ÇŽÉ(), p_180538_1_.á(), p_180538_1_.ÇŽÉ().Â(p_180538_1_.á()));
                var10 = p_180538_1_.áˆºÑ¢Õ();
            }
            p_180538_1_.Ø­à();
        }
        if (p_180538_1_.Ó == null && p_180538_1_.Ø == null) {
            ModelChest var12;
            if (p_180538_1_.à == null && p_180538_1_.áŒŠÆ == null) {
                var12 = this.áŒŠÆ;
                if (p_180538_9_ >= 0) {
                    this.HorizonCode_Horizon_È(TileEntityChestRenderer.Â[p_180538_9_]);
                    GlStateManager.á(5890);
                    GlStateManager.Çªà¢();
                    GlStateManager.HorizonCode_Horizon_È(4.0f, 4.0f, 1.0f);
                    GlStateManager.Â(0.0625f, 0.0625f, 0.0625f);
                    GlStateManager.á(5888);
                }
                else if (p_180538_1_.µÕ() == 1) {
                    this.HorizonCode_Horizon_È(TileEntityChestRenderer.Ó);
                }
                else if (this.ÂµÈ) {
                    this.HorizonCode_Horizon_È(TileEntityChestRenderer.à);
                }
                else {
                    this.HorizonCode_Horizon_È(TileEntityChestRenderer.Ø);
                }
            }
            else {
                var12 = this.áˆºÑ¢Õ;
                if (p_180538_9_ >= 0) {
                    this.HorizonCode_Horizon_È(TileEntityChestRenderer.Â[p_180538_9_]);
                    GlStateManager.á(5890);
                    GlStateManager.Çªà¢();
                    GlStateManager.HorizonCode_Horizon_È(8.0f, 4.0f, 1.0f);
                    GlStateManager.Â(0.0625f, 0.0625f, 0.0625f);
                    GlStateManager.á(5888);
                }
                else if (p_180538_1_.µÕ() == 1) {
                    this.HorizonCode_Horizon_È(TileEntityChestRenderer.HorizonCode_Horizon_È);
                }
                else if (this.ÂµÈ) {
                    this.HorizonCode_Horizon_È(TileEntityChestRenderer.Ø­áŒŠá);
                }
                else {
                    this.HorizonCode_Horizon_È(TileEntityChestRenderer.Âµá€);
                }
            }
            GlStateManager.Çªà¢();
            GlStateManager.ŠÄ();
            if (p_180538_9_ < 0) {
                GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            }
            GlStateManager.Â((float)p_180538_2_, (float)p_180538_4_ + 1.0f, (float)p_180538_6_ + 1.0f);
            GlStateManager.HorizonCode_Horizon_È(1.0f, -1.0f, -1.0f);
            GlStateManager.Â(0.5f, 0.5f, 0.5f);
            short var13 = 0;
            if (var10 == 2) {
                var13 = 180;
            }
            if (var10 == 3) {
                var13 = 0;
            }
            if (var10 == 4) {
                var13 = 90;
            }
            if (var10 == 5) {
                var13 = -90;
            }
            if (var10 == 2 && p_180538_1_.à != null) {
                GlStateManager.Â(1.0f, 0.0f, 0.0f);
            }
            if (var10 == 5 && p_180538_1_.áŒŠÆ != null) {
                GlStateManager.Â(0.0f, 0.0f, -1.0f);
            }
            GlStateManager.Â(var13, 0.0f, 1.0f, 0.0f);
            GlStateManager.Â(-0.5f, -0.5f, -0.5f);
            float var14 = p_180538_1_.ÂµÈ + (p_180538_1_.áˆºÑ¢Õ - p_180538_1_.ÂµÈ) * p_180538_8_;
            if (p_180538_1_.Ó != null) {
                final float var15 = p_180538_1_.Ó.ÂµÈ + (p_180538_1_.Ó.áˆºÑ¢Õ - p_180538_1_.Ó.ÂµÈ) * p_180538_8_;
                if (var15 > var14) {
                    var14 = var15;
                }
            }
            if (p_180538_1_.Ø != null) {
                final float var15 = p_180538_1_.Ø.ÂµÈ + (p_180538_1_.Ø.áˆºÑ¢Õ - p_180538_1_.Ø.ÂµÈ) * p_180538_8_;
                if (var15 > var14) {
                    var14 = var15;
                }
            }
            var14 = 1.0f - var14;
            var14 = 1.0f - var14 * var14 * var14;
            var12.HorizonCode_Horizon_È.Ó = -(var14 * 3.1415927f / 2.0f);
            var12.HorizonCode_Horizon_È();
            GlStateManager.Ñ¢á();
            GlStateManager.Ê();
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            if (p_180538_9_ >= 0) {
                GlStateManager.á(5890);
                GlStateManager.Ê();
                GlStateManager.á(5888);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final TileEntity p_180535_1_, final double p_180535_2_, final double p_180535_4_, final double p_180535_6_, final float p_180535_8_, final int p_180535_9_) {
        this.HorizonCode_Horizon_È((TileEntityChest)p_180535_1_, p_180535_2_, p_180535_4_, p_180535_6_, p_180535_8_, p_180535_9_);
    }
}
