package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;

public class ItemRenderer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final ResourceLocation_1975012498 Â;
    private final Minecraft Ý;
    private ItemStack Ø­áŒŠá;
    private float Âµá€;
    private float Ó;
    private final RenderManager à;
    private final RenderItem Ø;
    private int áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00000953";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/map/map_background.png");
        Â = new ResourceLocation_1975012498("textures/misc/underwater.png");
    }
    
    public ItemRenderer(final Minecraft mcIn) {
        this.áŒŠÆ = -1;
        this.Ý = mcIn;
        this.à = mcIn.ÇªÓ();
        this.Ø = mcIn.áˆºÏ();
    }
    
    public void HorizonCode_Horizon_È(final EntityLivingBase p_178099_1_, final ItemStack p_178099_2_, final ItemCameraTransforms.Â p_178099_3_) {
        if (p_178099_2_ != null) {
            final Item_1028566121 var4 = p_178099_2_.HorizonCode_Horizon_È();
            final Block var5 = Block.HorizonCode_Horizon_È(var4);
            GlStateManager.Çªà¢();
            if (this.Ø.HorizonCode_Horizon_È(p_178099_2_)) {
                GlStateManager.HorizonCode_Horizon_È(2.0f, 2.0f, 2.0f);
                if (this.HorizonCode_Horizon_È(var5)) {
                    GlStateManager.HorizonCode_Horizon_È(false);
                }
            }
            this.Ø.HorizonCode_Horizon_È(p_178099_2_, p_178099_1_, p_178099_3_);
            if (this.HorizonCode_Horizon_È(var5)) {
                GlStateManager.HorizonCode_Horizon_È(true);
            }
            GlStateManager.Ê();
        }
    }
    
    private boolean HorizonCode_Horizon_È(final Block p_178107_1_) {
        return p_178107_1_ != null && p_178107_1_.µà() == EnumWorldBlockLayer.Ø­áŒŠá;
    }
    
    private void HorizonCode_Horizon_È(final float p_178101_1_, final float p_178101_2_) {
        GlStateManager.Çªà¢();
        GlStateManager.Â(p_178101_1_, 1.0f, 0.0f, 0.0f);
        GlStateManager.Â(p_178101_2_, 0.0f, 1.0f, 0.0f);
        RenderHelper.Â();
        GlStateManager.Ê();
    }
    
    private void HorizonCode_Horizon_È(final AbstractClientPlayer p_178109_1_) {
        final int var2 = this.Ý.áŒŠÆ.HorizonCode_Horizon_È(new BlockPos(p_178109_1_.ŒÏ, p_178109_1_.Çªà¢ + p_178109_1_.Ðƒáƒ(), p_178109_1_.Ê), 0);
        final float var3 = var2 & 0xFFFF;
        final float var4 = var2 >> 16;
        OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.µà, var3, var4);
    }
    
    private void HorizonCode_Horizon_È(final EntityPlayerSP p_178110_1_, final float p_178110_2_) {
        final float var3 = p_178110_1_.ÂµÈ + (p_178110_1_.áŒŠÆ - p_178110_1_.ÂµÈ) * p_178110_2_;
        final float var4 = p_178110_1_.áˆºÑ¢Õ + (p_178110_1_.Ø - p_178110_1_.áˆºÑ¢Õ) * p_178110_2_;
        GlStateManager.Â((p_178110_1_.áƒ - var3) * 0.1f, 1.0f, 0.0f, 0.0f);
        GlStateManager.Â((p_178110_1_.É - var4) * 0.1f, 0.0f, 1.0f, 0.0f);
    }
    
    private float Ý(final float p_178100_1_) {
        float var2 = 1.0f - p_178100_1_ / 45.0f + 0.1f;
        var2 = MathHelper.HorizonCode_Horizon_È(var2, 0.0f, 1.0f);
        var2 = -MathHelper.Â(var2 * 3.1415927f) * 0.5f + 0.5f;
        return var2;
    }
    
    private void HorizonCode_Horizon_È(final RenderPlayer p_180534_1_) {
        GlStateManager.Çªà¢();
        GlStateManager.Â(54.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(64.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.Â(-62.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.Â(0.25f, -0.85f, 0.75f);
        p_180534_1_.Â(this.Ý.á);
        GlStateManager.Ê();
    }
    
    private void Â(final RenderPlayer p_178106_1_) {
        GlStateManager.Çªà¢();
        GlStateManager.Â(92.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(45.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.Â(41.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.Â(-0.3f, -1.1f, 0.45f);
        p_178106_1_.Ý(this.Ý.á);
        GlStateManager.Ê();
    }
    
    private void Â(final AbstractClientPlayer p_178102_1_) {
        this.Ý.¥à().HorizonCode_Horizon_È(p_178102_1_.Ø());
        final Render var2 = this.à.HorizonCode_Horizon_È(this.Ý.á);
        final RenderPlayer var3 = (RenderPlayer)var2;
        if (!p_178102_1_.áŒŠÏ()) {
            this.HorizonCode_Horizon_È(var3);
            this.Â(var3);
        }
    }
    
    private void HorizonCode_Horizon_È(final AbstractClientPlayer p_178097_1_, final float p_178097_2_, final float p_178097_3_, final float p_178097_4_) {
        final float var5 = -0.4f * MathHelper.HorizonCode_Horizon_È(MathHelper.Ý(p_178097_4_) * 3.1415927f);
        final float var6 = 0.2f * MathHelper.HorizonCode_Horizon_È(MathHelper.Ý(p_178097_4_) * 3.1415927f * 2.0f);
        final float var7 = -0.2f * MathHelper.HorizonCode_Horizon_È(p_178097_4_ * 3.1415927f);
        GlStateManager.Â(var5, var6, var7);
        final float var8 = this.Ý(p_178097_2_);
        GlStateManager.Â(0.0f, 0.04f, -0.72f);
        GlStateManager.Â(0.0f, p_178097_3_ * -1.2f, 0.0f);
        GlStateManager.Â(0.0f, var8 * -0.5f, 0.0f);
        GlStateManager.Â(90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(var8 * -85.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.Â(0.0f, 1.0f, 0.0f, 0.0f);
        this.Â(p_178097_1_);
        final float var9 = MathHelper.HorizonCode_Horizon_È(p_178097_4_ * p_178097_4_ * 3.1415927f);
        final float var10 = MathHelper.HorizonCode_Horizon_È(MathHelper.Ý(p_178097_4_) * 3.1415927f);
        GlStateManager.Â(var9 * -20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(var10 * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.Â(var10 * -80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.HorizonCode_Horizon_È(0.38f, 0.38f, 0.38f);
        GlStateManager.Â(90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.Â(0.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.Â(-1.0f, -1.0f, 0.0f);
        GlStateManager.HorizonCode_Horizon_È(0.015625f, 0.015625f, 0.015625f);
        this.Ý.¥à().HorizonCode_Horizon_È(ItemRenderer.HorizonCode_Horizon_È);
        final Tessellator var11 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var12 = var11.Ý();
        GL11.glNormal3f(0.0f, 0.0f, -1.0f);
        var12.Â();
        var12.HorizonCode_Horizon_È(-7.0, 135.0, 0.0, 0.0, 1.0);
        var12.HorizonCode_Horizon_È(135.0, 135.0, 0.0, 1.0, 1.0);
        var12.HorizonCode_Horizon_È(135.0, -7.0, 0.0, 1.0, 0.0);
        var12.HorizonCode_Horizon_È(-7.0, -7.0, 0.0, 0.0, 0.0);
        var11.Â();
        final MapData var13 = Items.ˆØ.HorizonCode_Horizon_È(this.Ø­áŒŠá, this.Ý.áŒŠÆ);
        if (var13 != null) {
            this.Ý.µÕ.áŒŠÆ().HorizonCode_Horizon_È(var13, false);
        }
    }
    
    private void HorizonCode_Horizon_È(final AbstractClientPlayer p_178095_1_, final float p_178095_2_, final float p_178095_3_) {
        final float var4 = -0.3f * MathHelper.HorizonCode_Horizon_È(MathHelper.Ý(p_178095_3_) * 3.1415927f);
        final float var5 = 0.4f * MathHelper.HorizonCode_Horizon_È(MathHelper.Ý(p_178095_3_) * 3.1415927f * 2.0f);
        final float var6 = -0.4f * MathHelper.HorizonCode_Horizon_È(p_178095_3_ * 3.1415927f);
        GlStateManager.Â(var4, var5, var6);
        GlStateManager.Â(0.64000005f, -0.6f, -0.71999997f);
        GlStateManager.Â(0.0f, p_178095_2_ * -0.6f, 0.0f);
        GlStateManager.Â(45.0f, 0.0f, 1.0f, 0.0f);
        final float var7 = MathHelper.HorizonCode_Horizon_È(p_178095_3_ * p_178095_3_ * 3.1415927f);
        final float var8 = MathHelper.HorizonCode_Horizon_È(MathHelper.Ý(p_178095_3_) * 3.1415927f);
        GlStateManager.Â(var8 * 70.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(var7 * -20.0f, 0.0f, 0.0f, 1.0f);
        this.Ý.¥à().HorizonCode_Horizon_È(p_178095_1_.Ø());
        GlStateManager.Â(-1.0f, 3.6f, 3.5f);
        GlStateManager.Â(120.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.Â(200.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.Â(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.HorizonCode_Horizon_È(1.0f, 1.0f, 1.0f);
        GlStateManager.Â(5.6f, 0.0f, 0.0f);
        final Render var9 = this.à.HorizonCode_Horizon_È(this.Ý.á);
        final RenderPlayer var10 = (RenderPlayer)var9;
        var10.Â(this.Ý.á);
    }
    
    private void Ø­áŒŠá(final float p_178105_1_) {
        final float var2 = -0.4f * MathHelper.HorizonCode_Horizon_È(MathHelper.Ý(p_178105_1_) * 3.1415927f);
        final float var3 = 0.2f * MathHelper.HorizonCode_Horizon_È(MathHelper.Ý(p_178105_1_) * 3.1415927f * 2.0f);
        final float var4 = -0.2f * MathHelper.HorizonCode_Horizon_È(p_178105_1_ * 3.1415927f);
        GlStateManager.Â(var2, var3, var4);
    }
    
    private void HorizonCode_Horizon_È(final AbstractClientPlayer p_178104_1_, final float p_178104_2_) {
        final float var3 = p_178104_1_.Ø­Ñ¢á€() - p_178104_2_ + 1.0f;
        final float var4 = var3 / this.Ø­áŒŠá.á();
        float var5 = MathHelper.Âµá€(MathHelper.Â(var3 / 4.0f * 3.1415927f) * 0.1f);
        if (var4 >= 0.8f) {
            var5 = 0.0f;
        }
        GlStateManager.Â(0.0f, var5, 0.0f);
        final float var6 = 1.0f - (float)Math.pow(var4, 27.0);
        GlStateManager.Â(var6 * 0.6f, var6 * -0.5f, var6 * 0.0f);
        GlStateManager.Â(var6 * 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(var6 * 10.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.Â(var6 * 30.0f, 0.0f, 0.0f, 1.0f);
    }
    
    private void Â(final float p_178096_1_, final float p_178096_2_) {
        GlStateManager.Â(0.56f, -0.52f, -0.71999997f);
        GlStateManager.Â(0.0f, p_178096_1_ * -0.6f, 0.0f);
        GlStateManager.Â(45.0f, 0.0f, 1.0f, 0.0f);
        final float var3 = MathHelper.HorizonCode_Horizon_È(p_178096_2_ * p_178096_2_ * 3.1415927f);
        final float var4 = MathHelper.HorizonCode_Horizon_È(MathHelper.Ý(p_178096_2_) * 3.1415927f);
        GlStateManager.Â(var3 * -20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(var4 * -20.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.Â(var4 * -80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.HorizonCode_Horizon_È(0.4f, 0.4f, 0.4f);
    }
    
    private void HorizonCode_Horizon_È(final float p_178098_1_, final AbstractClientPlayer p_178098_2_) {
        GlStateManager.Â(-18.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.Â(-12.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(-8.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.Â(-0.9f, 0.2f, 0.0f);
        final float var3 = this.Ø­áŒŠá.á() - (p_178098_2_.Ø­Ñ¢á€() - p_178098_1_ + 1.0f);
        float var4 = var3 / 20.0f;
        var4 = (var4 * var4 + var4 * 2.0f) / 3.0f;
        if (var4 > 1.0f) {
            var4 = 1.0f;
        }
        if (var4 > 0.1f) {
            final float var5 = MathHelper.HorizonCode_Horizon_È((var3 - 0.1f) * 1.3f);
            final float var6 = var4 - 0.1f;
            final float var7 = var5 * var6;
            GlStateManager.Â(var7 * 0.0f, var7 * 0.01f, var7 * 0.0f);
        }
        GlStateManager.Â(var4 * 0.0f, var4 * 0.0f, var4 * 0.1f);
        GlStateManager.HorizonCode_Horizon_È(1.0f, 1.0f, 1.0f + var4 * 0.2f);
    }
    
    private void Ø­áŒŠá() {
        GlStateManager.Â(-0.5f, 0.2f, 0.0f);
        GlStateManager.Â(30.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(-80.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.Â(60.0f, 0.0f, 1.0f, 0.0f);
    }
    
    public void HorizonCode_Horizon_È(final float p_78440_1_) {
        final float var2 = 1.0f - (this.Ó + (this.Âµá€ - this.Ó) * p_78440_1_);
        final EntityPlayerSP var3 = this.Ý.á;
        final float var4 = var3.á(p_78440_1_);
        final float var5 = var3.Õ + (var3.áƒ - var3.Õ) * p_78440_1_;
        final float var6 = var3.á€ + (var3.É - var3.á€) * p_78440_1_;
        this.HorizonCode_Horizon_È(var5, var6);
        this.HorizonCode_Horizon_È(var3);
        this.HorizonCode_Horizon_È(var3, p_78440_1_);
        GlStateManager.ŠÄ();
        GlStateManager.Çªà¢();
        if (this.Ø­áŒŠá != null) {
            if (this.Ø­áŒŠá.HorizonCode_Horizon_È() == Items.ˆØ) {
                this.HorizonCode_Horizon_È(var3, var5, var2, var4);
            }
            else if (var3.Ø­Ñ¢á€() > 0) {
                final EnumAction var7 = this.Ø­áŒŠá.ˆÏ­();
                switch (ItemRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È[var7.ordinal()]) {
                    case 1: {
                        this.Â(var2, 0.0f);
                        break;
                    }
                    case 2:
                    case 3: {
                        this.HorizonCode_Horizon_È((AbstractClientPlayer)var3, p_78440_1_);
                        this.Â(var2, 0.0f);
                        break;
                    }
                    case 4: {
                        this.Â(0.2f, var4);
                        this.Ø­áŒŠá();
                        if (var7 == EnumAction.Ø­áŒŠá) {
                            GL11.glTranslatef(-0.75f, 0.2f, 0.0f);
                            break;
                        }
                        break;
                    }
                    case 5: {
                        this.Â(var2, 0.0f);
                        this.HorizonCode_Horizon_È(p_78440_1_, var3);
                        break;
                    }
                }
            }
            else {
                this.Ø­áŒŠá(var4);
                this.Â(var2, var4);
            }
            this.HorizonCode_Horizon_È(var3, this.Ø­áŒŠá, ItemCameraTransforms.Â.Ý);
        }
        else if (!var3.áŒŠÏ()) {
            this.HorizonCode_Horizon_È(var3, var2, var4);
        }
        GlStateManager.Ê();
        GlStateManager.Ñ¢á();
        RenderHelper.HorizonCode_Horizon_È();
    }
    
    public void Â(final float p_78447_1_) {
        GlStateManager.Ý();
        if (this.Ý.á.£Ï()) {
            IBlockState var2 = this.Ý.áŒŠÆ.Â(new BlockPos(this.Ý.á));
            final EntityPlayerSP var3 = this.Ý.á;
            for (int var4 = 0; var4 < 8; ++var4) {
                final double var5 = var3.ŒÏ + ((var4 >> 0) % 2 - 0.5f) * var3.áŒŠ * 0.8f;
                final double var6 = var3.Çªà¢ + ((var4 >> 1) % 2 - 0.5f) * 0.1f;
                final double var7 = var3.Ê + ((var4 >> 2) % 2 - 0.5f) * var3.áŒŠ * 0.8f;
                final BlockPos var8 = new BlockPos(var5, var6 + var3.Ðƒáƒ(), var7);
                final IBlockState var9 = this.Ý.áŒŠÆ.Â(var8);
                if (var9.Ý().áŒŠÆ()) {
                    var2 = var9;
                }
            }
            if (var2.Ý().ÂµÈ() != -1) {
                this.HorizonCode_Horizon_È(p_78447_1_, this.Ý.Ô().HorizonCode_Horizon_È().HorizonCode_Horizon_È(var2));
            }
        }
        if (!this.Ý.á.Ø­áŒŠá()) {
            if (this.Ý.á.HorizonCode_Horizon_È(Material.Ø)) {
                this.Âµá€(p_78447_1_);
            }
            if (this.Ý.á.ˆÏ()) {
                this.Ó(p_78447_1_);
            }
        }
        GlStateManager.Ø­áŒŠá();
    }
    
    private void HorizonCode_Horizon_È(final float p_178108_1_, final TextureAtlasSprite p_178108_2_) {
        this.Ý.¥à().HorizonCode_Horizon_È(TextureMap.à);
        final Tessellator var3 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var4 = var3.Ý();
        final float var5 = 0.1f;
        GlStateManager.Ý(var5, var5, var5, 0.5f);
        GlStateManager.Çªà¢();
        final float var6 = -1.0f;
        final float var7 = 1.0f;
        final float var8 = -1.0f;
        final float var9 = 1.0f;
        final float var10 = -0.5f;
        final float var11 = p_178108_2_.Âµá€();
        final float var12 = p_178108_2_.Ó();
        final float var13 = p_178108_2_.à();
        final float var14 = p_178108_2_.Ø();
        var4.Â();
        var4.HorizonCode_Horizon_È(var6, var8, var10, var12, var14);
        var4.HorizonCode_Horizon_È(var7, var8, var10, var11, var14);
        var4.HorizonCode_Horizon_È(var7, var9, var10, var11, var13);
        var4.HorizonCode_Horizon_È(var6, var9, var10, var12, var13);
        var3.Â();
        GlStateManager.Ê();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void Âµá€(final float p_78448_1_) {
        this.Ý.¥à().HorizonCode_Horizon_È(ItemRenderer.Â);
        final Tessellator var2 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var3 = var2.Ý();
        final float var4 = this.Ý.á.Â(p_78448_1_);
        GlStateManager.Ý(var4, var4, var4, 0.5f);
        GlStateManager.á();
        GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
        GlStateManager.Çªà¢();
        final float var5 = 4.0f;
        final float var6 = -1.0f;
        final float var7 = 1.0f;
        final float var8 = -1.0f;
        final float var9 = 1.0f;
        final float var10 = -0.5f;
        final float var11 = -this.Ý.á.É / 64.0f;
        final float var12 = this.Ý.á.áƒ / 64.0f;
        var3.Â();
        var3.HorizonCode_Horizon_È(var6, var8, var10, var5 + var11, var5 + var12);
        var3.HorizonCode_Horizon_È(var7, var8, var10, 0.0f + var11, var5 + var12);
        var3.HorizonCode_Horizon_È(var7, var9, var10, 0.0f + var11, 0.0f + var12);
        var3.HorizonCode_Horizon_È(var6, var9, var10, var5 + var11, 0.0f + var12);
        var2.Â();
        GlStateManager.Ê();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.ÂµÈ();
    }
    
    private void Ó(final float p_78442_1_) {
        final Tessellator var2 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var3 = var2.Ý();
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 0.9f);
        GlStateManager.Ý(519);
        GlStateManager.HorizonCode_Horizon_È(false);
        GlStateManager.á();
        GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
        final float var4 = 1.0f;
        for (int var5 = 0; var5 < 2; ++var5) {
            GlStateManager.Çªà¢();
            final TextureAtlasSprite var6 = this.Ý.áŠ().HorizonCode_Horizon_È("minecraft:blocks/fire_layer_1");
            this.Ý.¥à().HorizonCode_Horizon_È(TextureMap.à);
            final float var7 = var6.Âµá€();
            final float var8 = var6.Ó();
            final float var9 = var6.à();
            final float var10 = var6.Ø();
            final float var11 = (0.0f - var4) / 2.0f;
            final float var12 = var11 + var4;
            final float var13 = 0.0f - var4 / 2.0f;
            final float var14 = var13 + var4;
            final float var15 = -0.5f;
            GlStateManager.Â(-(var5 * 2 - 1) * 0.24f, -0.3f, 0.0f);
            GlStateManager.Â((var5 * 2 - 1) * 10.0f, 0.0f, 1.0f, 0.0f);
            var3.Â();
            var3.HorizonCode_Horizon_È(var11, var13, var15, var8, var10);
            var3.HorizonCode_Horizon_È(var12, var13, var15, var7, var10);
            var3.HorizonCode_Horizon_È(var12, var14, var15, var7, var9);
            var3.HorizonCode_Horizon_È(var11, var14, var15, var8, var9);
            var2.Â();
            GlStateManager.Ê();
        }
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.ÂµÈ();
        GlStateManager.HorizonCode_Horizon_È(true);
        GlStateManager.Ý(515);
    }
    
    public void HorizonCode_Horizon_È() {
        this.Ó = this.Âµá€;
        final EntityPlayerSP var1 = this.Ý.á;
        final ItemStack var2 = var1.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        boolean var3 = false;
        if (this.Ø­áŒŠá != null && var2 != null) {
            if (!this.Ø­áŒŠá.Ý(var2)) {
                var3 = true;
            }
        }
        else {
            var3 = (this.Ø­áŒŠá != null || var2 != null);
        }
        final float var4 = 0.4f;
        final float var5 = var3 ? 0.0f : 1.0f;
        final float var6 = MathHelper.HorizonCode_Horizon_È(var5 - this.Âµá€, -var4, var4);
        this.Âµá€ += var6;
        if (this.Âµá€ < 0.6f) {
            this.Ø­áŒŠá = var2;
            this.áŒŠÆ = var1.Ø­Ñ¢Ï­Ø­áˆº.Ý;
        }
    }
    
    public void Â() {
        this.Âµá€ = 0.0f;
    }
    
    public void Ý() {
        this.Âµá€ = 0.0f;
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002537";
        
        static {
            HorizonCode_Horizon_È = new int[EnumAction.values().length];
            try {
                ItemRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumAction.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                ItemRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumAction.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                ItemRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumAction.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                ItemRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumAction.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                ItemRenderer.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumAction.Âµá€.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
        }
    }
}
