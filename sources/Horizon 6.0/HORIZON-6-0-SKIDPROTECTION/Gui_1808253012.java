package HORIZON-6-0-SKIDPROTECTION;

public class Gui_1808253012
{
    public static final ResourceLocation_1975012498 Šáƒ;
    public static final ResourceLocation_1975012498 Ï­Ðƒà;
    public static final ResourceLocation_1975012498 áŒŠà;
    protected static float ŠÄ;
    private static final String HorizonCode_Horizon_È = "CL_00000662";
    
    static {
        Šáƒ = new ResourceLocation_1975012498("textures/horizon/gui/gray.png");
        Ï­Ðƒà = new ResourceLocation_1975012498("textures/gui/container/stats_icons.png");
        áŒŠà = new ResourceLocation_1975012498("textures/gui/icons.png");
    }
    
    protected void HorizonCode_Horizon_È(int startX, int endX, final int y, final int color) {
        if (endX < startX) {
            final int var5 = startX;
            startX = endX;
            endX = var5;
        }
        HorizonCode_Horizon_È(startX, y, endX + 1, y + 1, color);
    }
    
    protected void Â(final int x, int startY, int endY, final int color) {
        if (endY < startY) {
            final int var5 = startY;
            startY = endY;
            endY = var5;
        }
        HorizonCode_Horizon_È(x, startY + 1, x + 1, endY, color);
    }
    
    public static void HorizonCode_Horizon_È(int left, int top, int right, int bottom, final int color) {
        if (left < right) {
            final int var5 = left;
            left = right;
            right = var5;
        }
        if (top < bottom) {
            final int var5 = top;
            top = bottom;
            bottom = var5;
        }
        final float var6 = (color >> 24 & 0xFF) / 255.0f;
        final float var7 = (color >> 16 & 0xFF) / 255.0f;
        final float var8 = (color >> 8 & 0xFF) / 255.0f;
        final float var9 = (color & 0xFF) / 255.0f;
        final Tessellator var10 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var11 = var10.Ý();
        GlStateManager.á();
        GlStateManager.Æ();
        GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
        GlStateManager.Ý(var7, var8, var9, var6);
        var11.Â();
        var11.Â(left, bottom, 0.0);
        var11.Â(right, bottom, 0.0);
        var11.Â(right, top, 0.0);
        var11.Â(left, top, 0.0);
        var10.Â();
        GlStateManager.µÕ();
        GlStateManager.ÂµÈ();
    }
    
    protected static void HorizonCode_Horizon_È(final int left, final int top, final int right, final int bottom, final int startColor, final int endColor) {
        final float var7 = (startColor >> 24 & 0xFF) / 255.0f;
        final float var8 = (startColor >> 16 & 0xFF) / 255.0f;
        final float var9 = (startColor >> 8 & 0xFF) / 255.0f;
        final float var10 = (startColor & 0xFF) / 255.0f;
        final float var11 = (endColor >> 24 & 0xFF) / 255.0f;
        final float var12 = (endColor >> 16 & 0xFF) / 255.0f;
        final float var13 = (endColor >> 8 & 0xFF) / 255.0f;
        final float var14 = (endColor & 0xFF) / 255.0f;
        GlStateManager.Æ();
        GlStateManager.á();
        GlStateManager.Ý();
        GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
        GlStateManager.áˆºÑ¢Õ(7425);
        final Tessellator var15 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var16 = var15.Ý();
        var16.Â();
        var16.HorizonCode_Horizon_È(var8, var9, var10, var7);
        var16.Â(right, top, (double)Gui_1808253012.ŠÄ);
        var16.Â(left, top, (double)Gui_1808253012.ŠÄ);
        var16.HorizonCode_Horizon_È(var12, var13, var14, var11);
        var16.Â(left, bottom, (double)Gui_1808253012.ŠÄ);
        var16.Â(right, bottom, (double)Gui_1808253012.ŠÄ);
        var15.Â();
        GlStateManager.áˆºÑ¢Õ(7424);
        GlStateManager.ÂµÈ();
        GlStateManager.Ø­áŒŠá();
        GlStateManager.µÕ();
    }
    
    public void HorizonCode_Horizon_È(final FontRenderer fontRendererIn, final String text, final int x, final int y, final int color) {
        fontRendererIn.HorizonCode_Horizon_È(text, x - fontRendererIn.HorizonCode_Horizon_È(text) / 2, (float)y, color);
    }
    
    public static void Â(final FontRenderer fontRendererIn, final String text, final int x, final int y, final int color) {
        fontRendererIn.HorizonCode_Horizon_È(text, x, (float)y, color);
    }
    
    public void Â(final int x, final int y, final int textureX, final int textureY, final int width, final int height) {
        final float var7 = 0.00390625f;
        final float var8 = 0.00390625f;
        final Tessellator var9 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var10 = var9.Ý();
        var10.Â();
        var10.HorizonCode_Horizon_È(x + 0, y + height, Gui_1808253012.ŠÄ, (textureX + 0) * var7, (textureY + height) * var8);
        var10.HorizonCode_Horizon_È(x + width, y + height, Gui_1808253012.ŠÄ, (textureX + width) * var7, (textureY + height) * var8);
        var10.HorizonCode_Horizon_È(x + width, y + 0, Gui_1808253012.ŠÄ, (textureX + width) * var7, (textureY + 0) * var8);
        var10.HorizonCode_Horizon_È(x + 0, y + 0, Gui_1808253012.ŠÄ, (textureX + 0) * var7, (textureY + 0) * var8);
        var9.Â();
    }
    
    public void HorizonCode_Horizon_È(final float p_175174_1_, final float p_175174_2_, final int p_175174_3_, final int p_175174_4_, final int p_175174_5_, final int p_175174_6_) {
        final float var7 = 0.00390625f;
        final float var8 = 0.00390625f;
        final Tessellator var9 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var10 = var9.Ý();
        var10.Â();
        var10.HorizonCode_Horizon_È(p_175174_1_ + 0.0f, p_175174_2_ + p_175174_6_, Gui_1808253012.ŠÄ, (p_175174_3_ + 0) * var7, (p_175174_4_ + p_175174_6_) * var8);
        var10.HorizonCode_Horizon_È(p_175174_1_ + p_175174_5_, p_175174_2_ + p_175174_6_, Gui_1808253012.ŠÄ, (p_175174_3_ + p_175174_5_) * var7, (p_175174_4_ + p_175174_6_) * var8);
        var10.HorizonCode_Horizon_È(p_175174_1_ + p_175174_5_, p_175174_2_ + 0.0f, Gui_1808253012.ŠÄ, (p_175174_3_ + p_175174_5_) * var7, (p_175174_4_ + 0) * var8);
        var10.HorizonCode_Horizon_È(p_175174_1_ + 0.0f, p_175174_2_ + 0.0f, Gui_1808253012.ŠÄ, (p_175174_3_ + 0) * var7, (p_175174_4_ + 0) * var8);
        var9.Â();
    }
    
    public void HorizonCode_Horizon_È(final int p_175175_1_, final int p_175175_2_, final TextureAtlasSprite p_175175_3_, final int p_175175_4_, final int p_175175_5_) {
        final Tessellator var6 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var7 = var6.Ý();
        var7.Â();
        var7.HorizonCode_Horizon_È(p_175175_1_ + 0, p_175175_2_ + p_175175_5_, Gui_1808253012.ŠÄ, p_175175_3_.Âµá€(), p_175175_3_.Ø());
        var7.HorizonCode_Horizon_È(p_175175_1_ + p_175175_4_, p_175175_2_ + p_175175_5_, Gui_1808253012.ŠÄ, p_175175_3_.Ó(), p_175175_3_.Ø());
        var7.HorizonCode_Horizon_È(p_175175_1_ + p_175175_4_, p_175175_2_ + 0, Gui_1808253012.ŠÄ, p_175175_3_.Ó(), p_175175_3_.à());
        var7.HorizonCode_Horizon_È(p_175175_1_ + 0, p_175175_2_ + 0, Gui_1808253012.ŠÄ, p_175175_3_.Âµá€(), p_175175_3_.à());
        var6.Â();
    }
    
    public static void HorizonCode_Horizon_È(final int x, final int y, final float u, final float v, final int width, final int height, final float textureWidth, final float textureHeight) {
        final float var8 = 1.0f / textureWidth;
        final float var9 = 1.0f / textureHeight;
        final Tessellator var10 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var11 = var10.Ý();
        var11.Â();
        var11.HorizonCode_Horizon_È(x, y + height, 0.0, u * var8, (v + height) * var9);
        var11.HorizonCode_Horizon_È(x + width, y + height, 0.0, (u + width) * var8, (v + height) * var9);
        var11.HorizonCode_Horizon_È(x + width, y, 0.0, (u + width) * var8, v * var9);
        var11.HorizonCode_Horizon_È(x, y, 0.0, u * var8, v * var9);
        var10.Â();
    }
    
    public static void HorizonCode_Horizon_È(final int x, final int y, final float u, final float v, final int uWidth, final int vHeight, final int width, final int height, final float tileWidth, final float tileHeight) {
        final float var10 = 1.0f / tileWidth;
        final float var11 = 1.0f / tileHeight;
        final Tessellator var12 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var13 = var12.Ý();
        var13.Â();
        var13.HorizonCode_Horizon_È(x, y + height, 0.0, u * var10, (v + vHeight) * var11);
        var13.HorizonCode_Horizon_È(x + width, y + height, 0.0, (u + uWidth) * var10, (v + vHeight) * var11);
        var13.HorizonCode_Horizon_È(x + width, y, 0.0, (u + uWidth) * var10, v * var11);
        var13.HorizonCode_Horizon_È(x, y, 0.0, u * var10, v * var11);
        var12.Â();
    }
}
