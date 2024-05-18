package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;

public class RenderItemFrame extends Render
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private final Minecraft Âµá€;
    private final ModelResourceLocation Ó;
    private final ModelResourceLocation à;
    private RenderItem Ø;
    private static final String áŒŠÆ = "CL_00001002";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/map/map_background.png");
    }
    
    public RenderItemFrame(final RenderManager p_i46166_1_, final RenderItem p_i46166_2_) {
        super(p_i46166_1_);
        this.Âµá€ = Minecraft.áŒŠà();
        this.Ó = new ModelResourceLocation("item_frame", "normal");
        this.à = new ModelResourceLocation("item_frame", "map");
        this.Ø = p_i46166_2_;
    }
    
    public void HorizonCode_Horizon_È(final EntityItemFrame p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        GlStateManager.Çªà¢();
        final BlockPos var10 = p_76986_1_.ˆÏ­();
        final double var11 = var10.HorizonCode_Horizon_È() - p_76986_1_.ŒÏ + p_76986_2_;
        final double var12 = var10.Â() - p_76986_1_.Çªà¢ + p_76986_4_;
        final double var13 = var10.Ý() - p_76986_1_.Ê + p_76986_6_;
        GlStateManager.Â(var11 + 0.5, var12 + 0.5, var13 + 0.5);
        GlStateManager.Â(180.0f - p_76986_1_.É, 0.0f, 1.0f, 0.0f);
        this.Â.Ø­áŒŠá.HorizonCode_Horizon_È(TextureMap.à);
        final BlockRendererDispatcher var14 = this.Âµá€.Ô();
        final ModelManager var15 = var14.HorizonCode_Horizon_È().Â();
        IBakedModel var16;
        if (p_76986_1_.µà() != null && p_76986_1_.µà().HorizonCode_Horizon_È() == Items.ˆØ) {
            var16 = var15.HorizonCode_Horizon_È(this.à);
        }
        else {
            var16 = var15.HorizonCode_Horizon_È(this.Ó);
        }
        GlStateManager.Çªà¢();
        GlStateManager.Â(-0.5f, -0.5f, -0.5f);
        var14.Â().HorizonCode_Horizon_È(var16, 1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.Ê();
        GlStateManager.Â(0.0f, 0.0f, 0.4375f);
        this.Â(p_76986_1_);
        GlStateManager.Ê();
        this.HorizonCode_Horizon_È(p_76986_1_, p_76986_2_ + p_76986_1_.Â.Ø() * 0.3f, p_76986_4_ - 0.25, p_76986_6_ + p_76986_1_.Â.áˆºÑ¢Õ() * 0.3f);
    }
    
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final EntityItemFrame p_110775_1_) {
        return null;
    }
    
    private void Â(final EntityItemFrame p_82402_1_) {
        final ItemStack var2 = p_82402_1_.µà();
        if (var2 != null) {
            final EntityItem var3 = new EntityItem(p_82402_1_.Ï­Ðƒà, 0.0, 0.0, 0.0, var2);
            final Item_1028566121 var4 = var3.Ø().HorizonCode_Horizon_È();
            var3.Ø().Â = 1;
            var3.HorizonCode_Horizon_È = 0.0f;
            GlStateManager.Çªà¢();
            GlStateManager.Ó();
            int var5 = p_82402_1_.ˆà();
            if (var4 == Items.ˆØ) {
                var5 = var5 % 4 * 2;
            }
            GlStateManager.Â(var5 * 360.0f / 8.0f, 0.0f, 0.0f, 1.0f);
            if (var4 == Items.ˆØ) {
                this.Â.Ø­áŒŠá.HorizonCode_Horizon_È(RenderItemFrame.HorizonCode_Horizon_È);
                GlStateManager.Â(180.0f, 0.0f, 0.0f, 1.0f);
                final float var6 = 0.0078125f;
                GlStateManager.HorizonCode_Horizon_È(var6, var6, var6);
                GlStateManager.Â(-64.0f, -64.0f, 0.0f);
                final MapData var7 = Items.ˆØ.HorizonCode_Horizon_È(var3.Ø(), p_82402_1_.Ï­Ðƒà);
                GlStateManager.Â(0.0f, 0.0f, -1.0f);
                if (var7 != null) {
                    this.Âµá€.µÕ.áŒŠÆ().HorizonCode_Horizon_È(var7, true);
                }
            }
            else {
                TextureAtlasSprite var8 = null;
                if (var4 == Items.£ÇªÓ) {
                    var8 = this.Âµá€.áŠ().HorizonCode_Horizon_È(TextureCompass.á);
                    this.Âµá€.¥à().HorizonCode_Horizon_È(TextureMap.à);
                    if (var8 instanceof TextureCompass) {
                        final TextureCompass var9 = (TextureCompass)var8;
                        final double var10 = var9.áˆºÑ¢Õ;
                        final double var11 = var9.ÂµÈ;
                        var9.áˆºÑ¢Õ = 0.0;
                        var9.ÂµÈ = 0.0;
                        var9.HorizonCode_Horizon_È(p_82402_1_.Ï­Ðƒà, p_82402_1_.ŒÏ, p_82402_1_.Ê, MathHelper.à(180 + p_82402_1_.Â.Ý() * 90), false, true);
                        var9.áˆºÑ¢Õ = var10;
                        var9.ÂµÈ = var11;
                    }
                    else {
                        var8 = null;
                    }
                }
                GlStateManager.HorizonCode_Horizon_È(0.5f, 0.5f, 0.5f);
                if (!this.Ø.HorizonCode_Horizon_È(var3.Ø()) || var4 instanceof ItemSkull) {
                    GlStateManager.Â(180.0f, 0.0f, 1.0f, 0.0f);
                }
                GlStateManager.HorizonCode_Horizon_È();
                RenderHelper.Â();
                this.Ø.Â(var3.Ø());
                RenderHelper.HorizonCode_Horizon_È();
                GlStateManager.Â();
                if (var8 != null && var8.ÂµÈ() > 0) {
                    var8.áˆºÑ¢Õ();
                }
            }
            GlStateManager.Âµá€();
            GlStateManager.Ê();
        }
    }
    
    protected void HorizonCode_Horizon_È(final EntityItemFrame p_147914_1_, final double p_147914_2_, final double p_147914_4_, final double p_147914_6_) {
        if (Minecraft.Æ() && p_147914_1_.µà() != null && p_147914_1_.µà().¥Æ() && this.Â.à == p_147914_1_) {
            final float var8 = 1.6f;
            final float var9 = 0.016666668f * var8;
            final double var10 = p_147914_1_.Âµá€(this.Â.Ó);
            final float var11 = p_147914_1_.Çªà¢() ? 32.0f : 64.0f;
            if (var10 < var11 * var11) {
                final String var12 = p_147914_1_.µà().µà();
                if (p_147914_1_.Çªà¢()) {
                    final FontRenderer var13 = Render.Ý();
                    GlStateManager.Çªà¢();
                    GlStateManager.Â((float)p_147914_2_ + 0.0f, (float)p_147914_4_ + p_147914_1_.£ÂµÄ + 0.5f, (float)p_147914_6_);
                    GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                    GlStateManager.Â(-RenderManager.Ø, 0.0f, 1.0f, 0.0f);
                    GlStateManager.Â(RenderManager.áŒŠÆ, 1.0f, 0.0f, 0.0f);
                    GlStateManager.HorizonCode_Horizon_È(-var9, -var9, var9);
                    GlStateManager.Ó();
                    GlStateManager.Â(0.0f, 0.25f / var9, 0.0f);
                    GlStateManager.HorizonCode_Horizon_È(false);
                    GlStateManager.á();
                    GlStateManager.Â(770, 771);
                    final Tessellator var14 = Tessellator.HorizonCode_Horizon_È();
                    final WorldRenderer var15 = var14.Ý();
                    GlStateManager.Æ();
                    var15.Â();
                    final int var16 = var13.HorizonCode_Horizon_È(var12) / 2;
                    var15.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 0.25f);
                    var15.Â(-var16 - 1, -1.0, 0.0);
                    var15.Â(-var16 - 1, 8.0, 0.0);
                    var15.Â(var16 + 1, 8.0, 0.0);
                    var15.Â(var16 + 1, -1.0, 0.0);
                    var14.Â();
                    GlStateManager.µÕ();
                    GlStateManager.HorizonCode_Horizon_È(true);
                    var13.HorizonCode_Horizon_È(var12, -var13.HorizonCode_Horizon_È(var12) / 2, 0, 553648127);
                    GlStateManager.Âµá€();
                    GlStateManager.ÂµÈ();
                    GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
                    GlStateManager.Ê();
                }
                else {
                    this.HorizonCode_Horizon_È(p_147914_1_, var12, p_147914_2_, p_147914_4_, p_147914_6_, 64);
                }
            }
        }
    }
    
    @Override
    protected ResourceLocation_1975012498 HorizonCode_Horizon_È(final Entity p_110775_1_) {
        return this.HorizonCode_Horizon_È((EntityItemFrame)p_110775_1_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final Entity p_177067_1_, final double p_177067_2_, final double p_177067_4_, final double p_177067_6_) {
        this.HorizonCode_Horizon_È((EntityItemFrame)p_177067_1_, p_177067_2_, p_177067_4_, p_177067_6_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.HorizonCode_Horizon_È((EntityItemFrame)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
