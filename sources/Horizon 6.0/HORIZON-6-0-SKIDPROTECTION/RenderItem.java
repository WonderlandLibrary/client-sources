package HORIZON-6-0-SKIDPROTECTION;

import java.util.concurrent.Callable;
import java.util.Iterator;
import java.util.List;

public class RenderItem implements IResourceManagerReloadListener
{
    private static final ResourceLocation_1975012498 ÂµÈ;
    private boolean á;
    public float HorizonCode_Horizon_È;
    private final ItemModelMesher ˆÏ­;
    private final TextureManager £á;
    public static float Â;
    public static float Ý;
    public static float Ø­áŒŠá;
    public static float Âµá€;
    public static float Ó;
    public static float à;
    public static float Ø;
    public static float áŒŠÆ;
    public static float áˆºÑ¢Õ;
    private static final String Å = "CL_00001003";
    
    static {
        ÂµÈ = new ResourceLocation_1975012498("textures/misc/enchanted_item_glint.png");
        RenderItem.Â = 0.0f;
        RenderItem.Ý = 0.0f;
        RenderItem.Ø­áŒŠá = 0.0f;
        RenderItem.Âµá€ = 0.0f;
        RenderItem.Ó = 0.0f;
        RenderItem.à = 0.0f;
        RenderItem.Ø = 0.0f;
        RenderItem.áŒŠÆ = 0.0f;
        RenderItem.áˆºÑ¢Õ = 0.0f;
    }
    
    public RenderItem(final TextureManager p_i46165_1_, final ModelManager p_i46165_2_) {
        this.á = true;
        this.£á = p_i46165_1_;
        this.ˆÏ­ = new ItemModelMesher(p_i46165_2_);
        this.Â();
    }
    
    public void HorizonCode_Horizon_È(final boolean p_175039_1_) {
        this.á = p_175039_1_;
    }
    
    public ItemModelMesher HorizonCode_Horizon_È() {
        return this.ˆÏ­;
    }
    
    protected void HorizonCode_Horizon_È(final Item_1028566121 p_175048_1_, final int p_175048_2_, final String p_175048_3_) {
        this.ˆÏ­.HorizonCode_Horizon_È(p_175048_1_, p_175048_2_, new ModelResourceLocation(p_175048_3_, "inventory"));
    }
    
    protected void HorizonCode_Horizon_È(final Block p_175029_1_, final int p_175029_2_, final String p_175029_3_) {
        this.HorizonCode_Horizon_È(Item_1028566121.HorizonCode_Horizon_È(p_175029_1_), p_175029_2_, p_175029_3_);
    }
    
    private void HorizonCode_Horizon_È(final Block p_175031_1_, final String p_175031_2_) {
        this.HorizonCode_Horizon_È(p_175031_1_, 0, p_175031_2_);
    }
    
    private void HorizonCode_Horizon_È(final Item_1028566121 p_175047_1_, final String p_175047_2_) {
        this.HorizonCode_Horizon_È(p_175047_1_, 0, p_175047_2_);
    }
    
    private void HorizonCode_Horizon_È(final IBakedModel p_175036_1_, final ItemStack p_175036_2_) {
        this.HorizonCode_Horizon_È(p_175036_1_, -1, p_175036_2_);
    }
    
    private void HorizonCode_Horizon_È(final IBakedModel p_175035_1_, final int p_175035_2_) {
        this.HorizonCode_Horizon_È(p_175035_1_, p_175035_2_, null);
    }
    
    private void HorizonCode_Horizon_È(final IBakedModel p_175045_1_, final int p_175045_2_, final ItemStack p_175045_3_) {
        final Tessellator var4 = Tessellator.HorizonCode_Horizon_È();
        final WorldRenderer var5 = var4.Ý();
        var5.Â();
        var5.HorizonCode_Horizon_È(DefaultVertexFormats.Â);
        for (final EnumFacing var9 : EnumFacing.values()) {
            this.HorizonCode_Horizon_È(var5, p_175045_1_.HorizonCode_Horizon_È(var9), p_175045_2_, p_175045_3_);
        }
        this.HorizonCode_Horizon_È(var5, p_175045_1_.HorizonCode_Horizon_È(), p_175045_2_, p_175045_3_);
        var4.Â();
    }
    
    public void HorizonCode_Horizon_È(final ItemStack p_180454_1_, final IBakedModel p_180454_2_) {
        GlStateManager.Çªà¢();
        GlStateManager.HorizonCode_Horizon_È(0.5f, 0.5f, 0.5f);
        if (p_180454_2_.Ø­áŒŠá()) {
            GlStateManager.Â(180.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.Â(-0.5f, -0.5f, -0.5f);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.ŠÄ();
            TileEntityRendererChestHelper.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_180454_1_);
        }
        else {
            GlStateManager.Â(-0.5f, -0.5f, -0.5f);
            this.HorizonCode_Horizon_È(p_180454_2_, p_180454_1_);
            if (p_180454_1_.Ø­à()) {
                this.HorizonCode_Horizon_È(p_180454_2_);
            }
        }
        GlStateManager.Ê();
    }
    
    private void HorizonCode_Horizon_È(final IBakedModel p_180451_1_) {
        GlStateManager.HorizonCode_Horizon_È(false);
        GlStateManager.Ý(514);
        GlStateManager.Ó();
        GlStateManager.Â(768, 1);
        this.£á.HorizonCode_Horizon_È(RenderItem.ÂµÈ);
        GlStateManager.á(5890);
        GlStateManager.Çªà¢();
        GlStateManager.HorizonCode_Horizon_È(8.0f, 8.0f, 8.0f);
        final float var2 = Minecraft.áƒ() % 3000L / 3000.0f / 8.0f;
        GlStateManager.Â(var2, 0.0f, 0.0f);
        GlStateManager.Â(-50.0f, 0.0f, 0.0f, 1.0f);
        this.HorizonCode_Horizon_È(p_180451_1_, -8372020);
        GlStateManager.Ê();
        GlStateManager.Çªà¢();
        GlStateManager.HorizonCode_Horizon_È(8.0f, 8.0f, 8.0f);
        final float var3 = Minecraft.áƒ() % 4873L / 4873.0f / 8.0f;
        GlStateManager.Â(-var3, 0.0f, 0.0f);
        GlStateManager.Â(10.0f, 0.0f, 0.0f, 1.0f);
        this.HorizonCode_Horizon_È(p_180451_1_, -8372020);
        GlStateManager.Ê();
        GlStateManager.á(5888);
        GlStateManager.Â(770, 771);
        GlStateManager.Âµá€();
        GlStateManager.Ý(515);
        GlStateManager.HorizonCode_Horizon_È(true);
        this.£á.HorizonCode_Horizon_È(TextureMap.à);
    }
    
    private void HorizonCode_Horizon_È(final WorldRenderer p_175038_1_, final BakedQuad p_175038_2_) {
        final Vec3i var3 = p_175038_2_.Âµá€().ˆÏ­();
        p_175038_1_.Ø­áŒŠá(var3.HorizonCode_Horizon_È(), var3.Â(), var3.Ý());
    }
    
    private void HorizonCode_Horizon_È(final WorldRenderer p_175033_1_, final BakedQuad p_175033_2_, final int p_175033_3_) {
        p_175033_1_.HorizonCode_Horizon_È(p_175033_2_.Â());
        p_175033_1_.Ø­áŒŠá(p_175033_3_);
        this.HorizonCode_Horizon_È(p_175033_1_, p_175033_2_);
    }
    
    private void HorizonCode_Horizon_È(final WorldRenderer p_175032_1_, final List p_175032_2_, final int p_175032_3_, final ItemStack p_175032_4_) {
        final boolean var5 = p_175032_3_ == -1 && p_175032_4_ != null;
        for (final BakedQuad var7 : p_175032_2_) {
            int var8 = p_175032_3_;
            if (var5 && var7.Ý()) {
                var8 = p_175032_4_.HorizonCode_Horizon_È().HorizonCode_Horizon_È(p_175032_4_, var7.Ø­áŒŠá());
                if (EntityRenderer.HorizonCode_Horizon_È) {
                    var8 = TextureUtil.Ý(var8);
                }
                var8 |= 0xFF000000;
            }
            this.HorizonCode_Horizon_È(p_175032_1_, var7, var8);
        }
    }
    
    public boolean HorizonCode_Horizon_È(final ItemStack p_175050_1_) {
        final IBakedModel var2 = this.ˆÏ­.HorizonCode_Horizon_È(p_175050_1_);
        return var2 != null && var2.Ý();
    }
    
    private void Ý(final ItemStack p_175046_1_) {
        final IBakedModel var2 = this.ˆÏ­.HorizonCode_Horizon_È(p_175046_1_);
        final Item_1028566121 var3 = p_175046_1_.HorizonCode_Horizon_È();
        if (var3 != null) {
            final boolean var4 = var2.Ý();
            if (!var4) {
                GlStateManager.HorizonCode_Horizon_È(2.0f, 2.0f, 2.0f);
            }
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    public void Â(final ItemStack p_175043_1_) {
        final IBakedModel var2 = this.ˆÏ­.HorizonCode_Horizon_È(p_175043_1_);
        this.HorizonCode_Horizon_È(p_175043_1_, var2, ItemCameraTransforms.Â.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final ItemStack p_175049_1_, final EntityLivingBase p_175049_2_, final ItemCameraTransforms.Â p_175049_3_) {
        IBakedModel var4 = this.ˆÏ­.HorizonCode_Horizon_È(p_175049_1_);
        if (p_175049_2_ instanceof EntityPlayer) {
            final EntityPlayer var5 = (EntityPlayer)p_175049_2_;
            final Item_1028566121 var6 = p_175049_1_.HorizonCode_Horizon_È();
            ModelResourceLocation var7 = null;
            if (var6 == Items.ÂµÕ && var5.µÏ != null) {
                var7 = new ModelResourceLocation("fishing_rod_cast", "inventory");
            }
            else if (var6 == Items.Ó && var5.Š() != null) {
                final int var8 = p_175049_1_.á() - var5.Ø­Ñ¢á€();
                if (var8 >= 18) {
                    var7 = new ModelResourceLocation("bow_pulling_2", "inventory");
                }
                else if (var8 > 13) {
                    var7 = new ModelResourceLocation("bow_pulling_1", "inventory");
                }
                else if (var8 > 0) {
                    var7 = new ModelResourceLocation("bow_pulling_0", "inventory");
                }
            }
            if (var7 != null) {
                var4 = this.ˆÏ­.HorizonCode_Horizon_È().HorizonCode_Horizon_È(var7);
            }
        }
        this.HorizonCode_Horizon_È(p_175049_1_, var4, p_175049_3_);
    }
    
    protected void HorizonCode_Horizon_È(final ItemTransformVec3f p_175034_1_) {
        if (p_175034_1_ != ItemTransformVec3f.HorizonCode_Horizon_È) {
            GlStateManager.Â(p_175034_1_.Ý.x + RenderItem.Â, p_175034_1_.Ý.y + RenderItem.Ý, p_175034_1_.Ý.z + RenderItem.Ø­áŒŠá);
            GlStateManager.Â(p_175034_1_.Â.y + RenderItem.Ó, 0.0f, 1.0f, 0.0f);
            GlStateManager.Â(p_175034_1_.Â.x + RenderItem.Âµá€, 1.0f, 0.0f, 0.0f);
            GlStateManager.Â(p_175034_1_.Â.z + RenderItem.à, 0.0f, 0.0f, 1.0f);
            GlStateManager.HorizonCode_Horizon_È(p_175034_1_.Ø­áŒŠá.x + RenderItem.Ø, p_175034_1_.Ø­áŒŠá.y + RenderItem.áŒŠÆ, p_175034_1_.Ø­áŒŠá.z + RenderItem.áˆºÑ¢Õ);
        }
    }
    
    protected void HorizonCode_Horizon_È(final ItemStack p_175040_1_, final IBakedModel p_175040_2_, final ItemCameraTransforms.Â p_175040_3_) {
        this.£á.HorizonCode_Horizon_È(TextureMap.à);
        this.£á.Â(TextureMap.à).Â(false, false);
        this.Ý(p_175040_1_);
        GlStateManager.ŠÄ();
        GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
        GlStateManager.á();
        GlStateManager.HorizonCode_Horizon_È(770, 771, 1, 0);
        GlStateManager.Çªà¢();
        switch (RenderItem.HorizonCode_Horizon_È.HorizonCode_Horizon_È[p_175040_3_.ordinal()]) {
            case 2: {
                this.HorizonCode_Horizon_È(p_175040_2_.Ó().Â);
                break;
            }
            case 3: {
                this.HorizonCode_Horizon_È(p_175040_2_.Ó().Ý);
                break;
            }
            case 4: {
                this.HorizonCode_Horizon_È(p_175040_2_.Ó().Ø­áŒŠá);
                break;
            }
            case 5: {
                this.HorizonCode_Horizon_È(p_175040_2_.Ó().Âµá€);
                break;
            }
        }
        this.HorizonCode_Horizon_È(p_175040_1_, p_175040_2_);
        GlStateManager.Ê();
        GlStateManager.Ñ¢á();
        GlStateManager.ÂµÈ();
        this.£á.HorizonCode_Horizon_È(TextureMap.à);
        this.£á.Â(TextureMap.à).Ø­áŒŠá();
    }
    
    public void HorizonCode_Horizon_È(final ItemStack p_175042_1_, final int p_175042_2_, final int p_175042_3_) {
        final IBakedModel var4 = this.ˆÏ­.HorizonCode_Horizon_È(p_175042_1_);
        GlStateManager.Çªà¢();
        this.£á.HorizonCode_Horizon_È(TextureMap.à);
        this.£á.Â(TextureMap.à).Â(false, false);
        GlStateManager.ŠÄ();
        GlStateManager.Ø­áŒŠá();
        GlStateManager.HorizonCode_Horizon_È(516, 0.1f);
        GlStateManager.á();
        GlStateManager.Â(770, 771);
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        this.HorizonCode_Horizon_È(p_175042_2_, p_175042_3_, var4.Ý());
        this.HorizonCode_Horizon_È(var4.Ó().Âµá€);
        this.HorizonCode_Horizon_È(p_175042_1_, var4);
        GlStateManager.Ý();
        GlStateManager.Ñ¢á();
        GlStateManager.Ó();
        GlStateManager.Ê();
        this.£á.HorizonCode_Horizon_È(TextureMap.à);
        this.£á.Â(TextureMap.à).Ø­áŒŠá();
    }
    
    private void HorizonCode_Horizon_È(final int p_180452_1_, final int p_180452_2_, final boolean p_180452_3_) {
        GlStateManager.Â(p_180452_1_, p_180452_2_, 100.0f + this.HorizonCode_Horizon_È);
        GlStateManager.Â(8.0f, 8.0f, 0.0f);
        GlStateManager.HorizonCode_Horizon_È(1.0f, 1.0f, -1.0f);
        GlStateManager.HorizonCode_Horizon_È(0.5f, 0.5f, 0.5f);
        if (p_180452_3_) {
            GlStateManager.HorizonCode_Horizon_È(40.0f, 40.0f, 40.0f);
            GlStateManager.Â(210.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.Â(-135.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.Âµá€();
        }
        else {
            GlStateManager.HorizonCode_Horizon_È(64.0f, 64.0f, 64.0f);
            GlStateManager.Â(180.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.Ó();
        }
    }
    
    public void Â(final ItemStack p_180450_1_, final int p_180450_2_, final int p_180450_3_) {
        if (p_180450_1_ != null) {
            this.HorizonCode_Horizon_È += 50.0f;
            try {
                this.HorizonCode_Horizon_È(p_180450_1_, p_180450_2_, p_180450_3_);
            }
            catch (Throwable var6) {
                final CrashReport var5 = CrashReport.HorizonCode_Horizon_È(var6, "Rendering item");
                final CrashReportCategory var7 = var5.HorizonCode_Horizon_È("Item being rendered");
                var7.HorizonCode_Horizon_È("Item Type", new Callable() {
                    private static final String Â = "CL_00001004";
                    
                    public String HorizonCode_Horizon_È() {
                        return String.valueOf(p_180450_1_.HorizonCode_Horizon_È());
                    }
                });
                var7.HorizonCode_Horizon_È("Item Aux", new Callable() {
                    private static final String Â = "CL_00001005";
                    
                    public String HorizonCode_Horizon_È() {
                        return String.valueOf(p_180450_1_.Ø());
                    }
                });
                var7.HorizonCode_Horizon_È("Item NBT", new Callable() {
                    private static final String Â = "CL_00001006";
                    
                    public String HorizonCode_Horizon_È() {
                        return String.valueOf(p_180450_1_.Å());
                    }
                });
                var7.HorizonCode_Horizon_È("Item Foil", new Callable() {
                    private static final String Â = "CL_00001007";
                    
                    public String HorizonCode_Horizon_È() {
                        return String.valueOf(p_180450_1_.Ø­à());
                    }
                });
                throw new ReportedException(var5);
            }
            this.HorizonCode_Horizon_È -= 50.0f;
        }
    }
    
    public void HorizonCode_Horizon_È(final FontRenderer p_175030_1_, final ItemStack p_175030_2_, final int p_175030_3_, final int p_175030_4_) {
        this.HorizonCode_Horizon_È(p_175030_1_, p_175030_2_, p_175030_3_, p_175030_4_, null);
    }
    
    public void HorizonCode_Horizon_È(final FontRenderer p_180453_1_, final ItemStack p_180453_2_, final int p_180453_3_, final int p_180453_4_, final String p_180453_5_) {
        if (p_180453_2_ != null) {
            if (p_180453_2_.Â != 1 || p_180453_5_ != null) {
                String var6 = (p_180453_5_ == null) ? String.valueOf(p_180453_2_.Â) : p_180453_5_;
                if (p_180453_5_ == null && p_180453_2_.Â < 1) {
                    var6 = EnumChatFormatting.ˆÏ­ + String.valueOf(p_180453_2_.Â);
                }
                GlStateManager.Ó();
                GlStateManager.áŒŠÆ();
                GlStateManager.ÂµÈ();
                p_180453_1_.HorizonCode_Horizon_È(var6, p_180453_3_ + 19 - 2 - p_180453_1_.HorizonCode_Horizon_È(var6), (float)(p_180453_4_ + 6 + 3), 16777215);
                GlStateManager.Âµá€();
                GlStateManager.áˆºÑ¢Õ();
            }
            if (p_180453_2_.Ó()) {
                final int var7 = (int)Math.round(13.0 - p_180453_2_.à() * 13.0 / p_180453_2_.áŒŠÆ());
                final int var8 = (int)Math.round(255.0 - p_180453_2_.à() * 255.0 / p_180453_2_.áŒŠÆ());
                GlStateManager.Ó();
                GlStateManager.áŒŠÆ();
                GlStateManager.Æ();
                GlStateManager.Ý();
                GlStateManager.ÂµÈ();
                final Tessellator var9 = Tessellator.HorizonCode_Horizon_È();
                final WorldRenderer var10 = var9.Ý();
                final int var11 = 255 - var8 << 16 | var8 << 8;
                final int var12 = (255 - var8) / 4 << 16 | 0x3F00;
                this.HorizonCode_Horizon_È(var10, p_180453_3_ + 2, p_180453_4_ + 13, 13, 2, 0);
                this.HorizonCode_Horizon_È(var10, p_180453_3_ + 2, p_180453_4_ + 13, 12, 1, var12);
                this.HorizonCode_Horizon_È(var10, p_180453_3_ + 2, p_180453_4_ + 13, var7, 1, var11);
                GlStateManager.á();
                GlStateManager.Ø­áŒŠá();
                GlStateManager.µÕ();
                GlStateManager.Âµá€();
                GlStateManager.áˆºÑ¢Õ();
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final WorldRenderer p_175044_1_, final int p_175044_2_, final int p_175044_3_, final int p_175044_4_, final int p_175044_5_, final int p_175044_6_) {
        p_175044_1_.Â();
        p_175044_1_.Ý(p_175044_6_);
        p_175044_1_.Â(p_175044_2_ + 0, p_175044_3_ + 0, 0.0);
        p_175044_1_.Â(p_175044_2_ + 0, p_175044_3_ + p_175044_5_, 0.0);
        p_175044_1_.Â(p_175044_2_ + p_175044_4_, p_175044_3_ + p_175044_5_, 0.0);
        p_175044_1_.Â(p_175044_2_ + p_175044_4_, p_175044_3_ + 0, 0.0);
        Tessellator.HorizonCode_Horizon_È().Â();
    }
    
    private void Â() {
        this.HorizonCode_Horizon_È(Blocks.ÇªÅ, "anvil_intact");
        this.HorizonCode_Horizon_È(Blocks.ÇªÅ, 1, "anvil_slightly_damaged");
        this.HorizonCode_Horizon_È(Blocks.ÇªÅ, 2, "anvil_very_damaged");
        this.HorizonCode_Horizon_È(Blocks.áˆºÂ, EnumDyeColor.£à.Â(), "black_carpet");
        this.HorizonCode_Horizon_È(Blocks.áˆºÂ, EnumDyeColor.á.Â(), "blue_carpet");
        this.HorizonCode_Horizon_È(Blocks.áˆºÂ, EnumDyeColor.ˆÏ­.Â(), "brown_carpet");
        this.HorizonCode_Horizon_È(Blocks.áˆºÂ, EnumDyeColor.áˆºÑ¢Õ.Â(), "cyan_carpet");
        this.HorizonCode_Horizon_È(Blocks.áˆºÂ, EnumDyeColor.Ø.Â(), "gray_carpet");
        this.HorizonCode_Horizon_È(Blocks.áˆºÂ, EnumDyeColor.£á.Â(), "green_carpet");
        this.HorizonCode_Horizon_È(Blocks.áˆºÂ, EnumDyeColor.Ø­áŒŠá.Â(), "light_blue_carpet");
        this.HorizonCode_Horizon_È(Blocks.áˆºÂ, EnumDyeColor.Ó.Â(), "lime_carpet");
        this.HorizonCode_Horizon_È(Blocks.áˆºÂ, EnumDyeColor.Ý.Â(), "magenta_carpet");
        this.HorizonCode_Horizon_È(Blocks.áˆºÂ, EnumDyeColor.Â.Â(), "orange_carpet");
        this.HorizonCode_Horizon_È(Blocks.áˆºÂ, EnumDyeColor.à.Â(), "pink_carpet");
        this.HorizonCode_Horizon_È(Blocks.áˆºÂ, EnumDyeColor.ÂµÈ.Â(), "purple_carpet");
        this.HorizonCode_Horizon_È(Blocks.áˆºÂ, EnumDyeColor.Å.Â(), "red_carpet");
        this.HorizonCode_Horizon_È(Blocks.áˆºÂ, EnumDyeColor.áŒŠÆ.Â(), "silver_carpet");
        this.HorizonCode_Horizon_È(Blocks.áˆºÂ, EnumDyeColor.HorizonCode_Horizon_È.Â(), "white_carpet");
        this.HorizonCode_Horizon_È(Blocks.áˆºÂ, EnumDyeColor.Âµá€.Â(), "yellow_carpet");
        this.HorizonCode_Horizon_È(Blocks.Ï­Ó, BlockWall.HorizonCode_Horizon_È.Â.Â(), "mossy_cobblestone_wall");
        this.HorizonCode_Horizon_È(Blocks.Ï­Ó, BlockWall.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â(), "cobblestone_wall");
        this.HorizonCode_Horizon_È(Blocks.Âµá€, BlockDirt.HorizonCode_Horizon_È.Â.Â(), "coarse_dirt");
        this.HorizonCode_Horizon_È(Blocks.Âµá€, BlockDirt.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â(), "dirt");
        this.HorizonCode_Horizon_È(Blocks.Âµá€, BlockDirt.HorizonCode_Horizon_È.Ý.Â(), "podzol");
        this.HorizonCode_Horizon_È(Blocks.ÇªÇªÉ, BlockDoublePlant.Â.Ø­áŒŠá.Â(), "double_fern");
        this.HorizonCode_Horizon_È(Blocks.ÇªÇªÉ, BlockDoublePlant.Â.Ý.Â(), "double_grass");
        this.HorizonCode_Horizon_È(Blocks.ÇªÇªÉ, BlockDoublePlant.Â.Ó.Â(), "paeonia");
        this.HorizonCode_Horizon_È(Blocks.ÇªÇªÉ, BlockDoublePlant.Â.Âµá€.Â(), "double_rose");
        this.HorizonCode_Horizon_È(Blocks.ÇªÇªÉ, BlockDoublePlant.Â.HorizonCode_Horizon_È.Â(), "sunflower");
        this.HorizonCode_Horizon_È(Blocks.ÇªÇªÉ, BlockDoublePlant.Â.Â.Â(), "syringa");
        this.HorizonCode_Horizon_È(Blocks.µÕ, BlockPlanks.HorizonCode_Horizon_È.Ý.Â(), "birch_leaves");
        this.HorizonCode_Horizon_È(Blocks.µÕ, BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â(), "jungle_leaves");
        this.HorizonCode_Horizon_È(Blocks.µÕ, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â(), "oak_leaves");
        this.HorizonCode_Horizon_È(Blocks.µÕ, BlockPlanks.HorizonCode_Horizon_È.Â.Â(), "spruce_leaves");
        this.HorizonCode_Horizon_È(Blocks.Æ, BlockPlanks.HorizonCode_Horizon_È.Âµá€.Â() - 4, "acacia_leaves");
        this.HorizonCode_Horizon_È(Blocks.Æ, BlockPlanks.HorizonCode_Horizon_È.Ó.Â() - 4, "dark_oak_leaves");
        this.HorizonCode_Horizon_È(Blocks.¥Æ, BlockPlanks.HorizonCode_Horizon_È.Ý.Â(), "birch_log");
        this.HorizonCode_Horizon_È(Blocks.¥Æ, BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â(), "jungle_log");
        this.HorizonCode_Horizon_È(Blocks.¥Æ, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â(), "oak_log");
        this.HorizonCode_Horizon_È(Blocks.¥Æ, BlockPlanks.HorizonCode_Horizon_È.Â.Â(), "spruce_log");
        this.HorizonCode_Horizon_È(Blocks.Ø­à, BlockPlanks.HorizonCode_Horizon_È.Âµá€.Â() - 4, "acacia_log");
        this.HorizonCode_Horizon_È(Blocks.Ø­à, BlockPlanks.HorizonCode_Horizon_È.Ó.Â() - 4, "dark_oak_log");
        this.HorizonCode_Horizon_È(Blocks.ÐƒÂ, BlockSilverfish.HorizonCode_Horizon_È.Ó.Â(), "chiseled_brick_monster_egg");
        this.HorizonCode_Horizon_È(Blocks.ÐƒÂ, BlockSilverfish.HorizonCode_Horizon_È.Â.Â(), "cobblestone_monster_egg");
        this.HorizonCode_Horizon_È(Blocks.ÐƒÂ, BlockSilverfish.HorizonCode_Horizon_È.Âµá€.Â(), "cracked_brick_monster_egg");
        this.HorizonCode_Horizon_È(Blocks.ÐƒÂ, BlockSilverfish.HorizonCode_Horizon_È.Ø­áŒŠá.Â(), "mossy_brick_monster_egg");
        this.HorizonCode_Horizon_È(Blocks.ÐƒÂ, BlockSilverfish.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â(), "stone_monster_egg");
        this.HorizonCode_Horizon_È(Blocks.ÐƒÂ, BlockSilverfish.HorizonCode_Horizon_È.Ý.Â(), "stone_brick_monster_egg");
        this.HorizonCode_Horizon_È(Blocks.à, BlockPlanks.HorizonCode_Horizon_È.Âµá€.Â(), "acacia_planks");
        this.HorizonCode_Horizon_È(Blocks.à, BlockPlanks.HorizonCode_Horizon_È.Ý.Â(), "birch_planks");
        this.HorizonCode_Horizon_È(Blocks.à, BlockPlanks.HorizonCode_Horizon_È.Ó.Â(), "dark_oak_planks");
        this.HorizonCode_Horizon_È(Blocks.à, BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â(), "jungle_planks");
        this.HorizonCode_Horizon_È(Blocks.à, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â(), "oak_planks");
        this.HorizonCode_Horizon_È(Blocks.à, BlockPlanks.HorizonCode_Horizon_È.Â.Â(), "spruce_planks");
        this.HorizonCode_Horizon_È(Blocks.ÇŽáˆºÈ, BlockPrismarine.HorizonCode_Horizon_È.Â.Â(), "prismarine_bricks");
        this.HorizonCode_Horizon_È(Blocks.ÇŽáˆºÈ, BlockPrismarine.HorizonCode_Horizon_È.Ý.Â(), "dark_prismarine");
        this.HorizonCode_Horizon_È(Blocks.ÇŽáˆºÈ, BlockPrismarine.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â(), "prismarine");
        this.HorizonCode_Horizon_È(Blocks.Ø­È, BlockQuartz.HorizonCode_Horizon_È.Â.Â(), "chiseled_quartz_block");
        this.HorizonCode_Horizon_È(Blocks.Ø­È, BlockQuartz.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â(), "quartz_block");
        this.HorizonCode_Horizon_È(Blocks.Ø­È, BlockQuartz.HorizonCode_Horizon_È.Ý.Â(), "quartz_column");
        this.HorizonCode_Horizon_È(Blocks.Ç, BlockFlower.Â.Ø­áŒŠá.Ý(), "allium");
        this.HorizonCode_Horizon_È(Blocks.Ç, BlockFlower.Â.Ý.Ý(), "blue_orchid");
        this.HorizonCode_Horizon_È(Blocks.Ç, BlockFlower.Â.Âµá€.Ý(), "houstonia");
        this.HorizonCode_Horizon_È(Blocks.Ç, BlockFlower.Â.à.Ý(), "orange_tulip");
        this.HorizonCode_Horizon_È(Blocks.Ç, BlockFlower.Â.áˆºÑ¢Õ.Ý(), "oxeye_daisy");
        this.HorizonCode_Horizon_È(Blocks.Ç, BlockFlower.Â.áŒŠÆ.Ý(), "pink_tulip");
        this.HorizonCode_Horizon_È(Blocks.Ç, BlockFlower.Â.Â.Ý(), "poppy");
        this.HorizonCode_Horizon_È(Blocks.Ç, BlockFlower.Â.Ó.Ý(), "red_tulip");
        this.HorizonCode_Horizon_È(Blocks.Ç, BlockFlower.Â.Ø.Ý(), "white_tulip");
        this.HorizonCode_Horizon_È(Blocks.£á, BlockSand.HorizonCode_Horizon_È.Â.Â(), "red_sand");
        this.HorizonCode_Horizon_È(Blocks.£á, BlockSand.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â(), "sand");
        this.HorizonCode_Horizon_È(Blocks.ŒÏ, BlockSandStone.HorizonCode_Horizon_È.Â.Â(), "chiseled_sandstone");
        this.HorizonCode_Horizon_È(Blocks.ŒÏ, BlockSandStone.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â(), "sandstone");
        this.HorizonCode_Horizon_È(Blocks.ŒÏ, BlockSandStone.HorizonCode_Horizon_È.Ý.Â(), "smooth_sandstone");
        this.HorizonCode_Horizon_È(Blocks.áˆºÛ, BlockRedSandstone.HorizonCode_Horizon_È.Â.Â(), "chiseled_red_sandstone");
        this.HorizonCode_Horizon_È(Blocks.áˆºÛ, BlockRedSandstone.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â(), "red_sandstone");
        this.HorizonCode_Horizon_È(Blocks.áˆºÛ, BlockRedSandstone.HorizonCode_Horizon_È.Ý.Â(), "smooth_red_sandstone");
        this.HorizonCode_Horizon_È(Blocks.Ø, BlockPlanks.HorizonCode_Horizon_È.Âµá€.Â(), "acacia_sapling");
        this.HorizonCode_Horizon_È(Blocks.Ø, BlockPlanks.HorizonCode_Horizon_È.Ý.Â(), "birch_sapling");
        this.HorizonCode_Horizon_È(Blocks.Ø, BlockPlanks.HorizonCode_Horizon_È.Ó.Â(), "dark_oak_sapling");
        this.HorizonCode_Horizon_È(Blocks.Ø, BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â(), "jungle_sapling");
        this.HorizonCode_Horizon_È(Blocks.Ø, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â(), "oak_sapling");
        this.HorizonCode_Horizon_È(Blocks.Ø, BlockPlanks.HorizonCode_Horizon_È.Â.Â(), "spruce_sapling");
        this.HorizonCode_Horizon_È(Blocks.Šáƒ, 0, "sponge");
        this.HorizonCode_Horizon_È(Blocks.Šáƒ, 1, "sponge_wet");
        this.HorizonCode_Horizon_È(Blocks.ÐƒáŒŠÂµÐƒÕ, EnumDyeColor.£à.Â(), "black_stained_glass");
        this.HorizonCode_Horizon_È(Blocks.ÐƒáŒŠÂµÐƒÕ, EnumDyeColor.á.Â(), "blue_stained_glass");
        this.HorizonCode_Horizon_È(Blocks.ÐƒáŒŠÂµÐƒÕ, EnumDyeColor.ˆÏ­.Â(), "brown_stained_glass");
        this.HorizonCode_Horizon_È(Blocks.ÐƒáŒŠÂµÐƒÕ, EnumDyeColor.áˆºÑ¢Õ.Â(), "cyan_stained_glass");
        this.HorizonCode_Horizon_È(Blocks.ÐƒáŒŠÂµÐƒÕ, EnumDyeColor.Ø.Â(), "gray_stained_glass");
        this.HorizonCode_Horizon_È(Blocks.ÐƒáŒŠÂµÐƒÕ, EnumDyeColor.£á.Â(), "green_stained_glass");
        this.HorizonCode_Horizon_È(Blocks.ÐƒáŒŠÂµÐƒÕ, EnumDyeColor.Ø­áŒŠá.Â(), "light_blue_stained_glass");
        this.HorizonCode_Horizon_È(Blocks.ÐƒáŒŠÂµÐƒÕ, EnumDyeColor.Ó.Â(), "lime_stained_glass");
        this.HorizonCode_Horizon_È(Blocks.ÐƒáŒŠÂµÐƒÕ, EnumDyeColor.Ý.Â(), "magenta_stained_glass");
        this.HorizonCode_Horizon_È(Blocks.ÐƒáŒŠÂµÐƒÕ, EnumDyeColor.Â.Â(), "orange_stained_glass");
        this.HorizonCode_Horizon_È(Blocks.ÐƒáŒŠÂµÐƒÕ, EnumDyeColor.à.Â(), "pink_stained_glass");
        this.HorizonCode_Horizon_È(Blocks.ÐƒáŒŠÂµÐƒÕ, EnumDyeColor.ÂµÈ.Â(), "purple_stained_glass");
        this.HorizonCode_Horizon_È(Blocks.ÐƒáŒŠÂµÐƒÕ, EnumDyeColor.Å.Â(), "red_stained_glass");
        this.HorizonCode_Horizon_È(Blocks.ÐƒáŒŠÂµÐƒÕ, EnumDyeColor.áŒŠÆ.Â(), "silver_stained_glass");
        this.HorizonCode_Horizon_È(Blocks.ÐƒáŒŠÂµÐƒÕ, EnumDyeColor.HorizonCode_Horizon_È.Â(), "white_stained_glass");
        this.HorizonCode_Horizon_È(Blocks.ÐƒáŒŠÂµÐƒÕ, EnumDyeColor.Âµá€.Â(), "yellow_stained_glass");
        this.HorizonCode_Horizon_È(Blocks.Ø­áƒ, EnumDyeColor.£à.Â(), "black_stained_glass_pane");
        this.HorizonCode_Horizon_È(Blocks.Ø­áƒ, EnumDyeColor.á.Â(), "blue_stained_glass_pane");
        this.HorizonCode_Horizon_È(Blocks.Ø­áƒ, EnumDyeColor.ˆÏ­.Â(), "brown_stained_glass_pane");
        this.HorizonCode_Horizon_È(Blocks.Ø­áƒ, EnumDyeColor.áˆºÑ¢Õ.Â(), "cyan_stained_glass_pane");
        this.HorizonCode_Horizon_È(Blocks.Ø­áƒ, EnumDyeColor.Ø.Â(), "gray_stained_glass_pane");
        this.HorizonCode_Horizon_È(Blocks.Ø­áƒ, EnumDyeColor.£á.Â(), "green_stained_glass_pane");
        this.HorizonCode_Horizon_È(Blocks.Ø­áƒ, EnumDyeColor.Ø­áŒŠá.Â(), "light_blue_stained_glass_pane");
        this.HorizonCode_Horizon_È(Blocks.Ø­áƒ, EnumDyeColor.Ó.Â(), "lime_stained_glass_pane");
        this.HorizonCode_Horizon_È(Blocks.Ø­áƒ, EnumDyeColor.Ý.Â(), "magenta_stained_glass_pane");
        this.HorizonCode_Horizon_È(Blocks.Ø­áƒ, EnumDyeColor.Â.Â(), "orange_stained_glass_pane");
        this.HorizonCode_Horizon_È(Blocks.Ø­áƒ, EnumDyeColor.à.Â(), "pink_stained_glass_pane");
        this.HorizonCode_Horizon_È(Blocks.Ø­áƒ, EnumDyeColor.ÂµÈ.Â(), "purple_stained_glass_pane");
        this.HorizonCode_Horizon_È(Blocks.Ø­áƒ, EnumDyeColor.Å.Â(), "red_stained_glass_pane");
        this.HorizonCode_Horizon_È(Blocks.Ø­áƒ, EnumDyeColor.áŒŠÆ.Â(), "silver_stained_glass_pane");
        this.HorizonCode_Horizon_È(Blocks.Ø­áƒ, EnumDyeColor.HorizonCode_Horizon_È.Â(), "white_stained_glass_pane");
        this.HorizonCode_Horizon_È(Blocks.Ø­áƒ, EnumDyeColor.Âµá€.Â(), "yellow_stained_glass_pane");
        this.HorizonCode_Horizon_È(Blocks.Ø­Â, EnumDyeColor.£à.Â(), "black_stained_hardened_clay");
        this.HorizonCode_Horizon_È(Blocks.Ø­Â, EnumDyeColor.á.Â(), "blue_stained_hardened_clay");
        this.HorizonCode_Horizon_È(Blocks.Ø­Â, EnumDyeColor.ˆÏ­.Â(), "brown_stained_hardened_clay");
        this.HorizonCode_Horizon_È(Blocks.Ø­Â, EnumDyeColor.áˆºÑ¢Õ.Â(), "cyan_stained_hardened_clay");
        this.HorizonCode_Horizon_È(Blocks.Ø­Â, EnumDyeColor.Ø.Â(), "gray_stained_hardened_clay");
        this.HorizonCode_Horizon_È(Blocks.Ø­Â, EnumDyeColor.£á.Â(), "green_stained_hardened_clay");
        this.HorizonCode_Horizon_È(Blocks.Ø­Â, EnumDyeColor.Ø­áŒŠá.Â(), "light_blue_stained_hardened_clay");
        this.HorizonCode_Horizon_È(Blocks.Ø­Â, EnumDyeColor.Ó.Â(), "lime_stained_hardened_clay");
        this.HorizonCode_Horizon_È(Blocks.Ø­Â, EnumDyeColor.Ý.Â(), "magenta_stained_hardened_clay");
        this.HorizonCode_Horizon_È(Blocks.Ø­Â, EnumDyeColor.Â.Â(), "orange_stained_hardened_clay");
        this.HorizonCode_Horizon_È(Blocks.Ø­Â, EnumDyeColor.à.Â(), "pink_stained_hardened_clay");
        this.HorizonCode_Horizon_È(Blocks.Ø­Â, EnumDyeColor.ÂµÈ.Â(), "purple_stained_hardened_clay");
        this.HorizonCode_Horizon_È(Blocks.Ø­Â, EnumDyeColor.Å.Â(), "red_stained_hardened_clay");
        this.HorizonCode_Horizon_È(Blocks.Ø­Â, EnumDyeColor.áŒŠÆ.Â(), "silver_stained_hardened_clay");
        this.HorizonCode_Horizon_È(Blocks.Ø­Â, EnumDyeColor.HorizonCode_Horizon_È.Â(), "white_stained_hardened_clay");
        this.HorizonCode_Horizon_È(Blocks.Ø­Â, EnumDyeColor.Âµá€.Â(), "yellow_stained_hardened_clay");
        this.HorizonCode_Horizon_È(Blocks.Ý, BlockStone.HorizonCode_Horizon_È.Ó.Â(), "andesite");
        this.HorizonCode_Horizon_È(Blocks.Ý, BlockStone.HorizonCode_Horizon_È.à.Â(), "andesite_smooth");
        this.HorizonCode_Horizon_È(Blocks.Ý, BlockStone.HorizonCode_Horizon_È.Ø­áŒŠá.Â(), "diorite");
        this.HorizonCode_Horizon_È(Blocks.Ý, BlockStone.HorizonCode_Horizon_È.Âµá€.Â(), "diorite_smooth");
        this.HorizonCode_Horizon_È(Blocks.Ý, BlockStone.HorizonCode_Horizon_È.Â.Â(), "granite");
        this.HorizonCode_Horizon_È(Blocks.Ý, BlockStone.HorizonCode_Horizon_È.Ý.Â(), "granite_smooth");
        this.HorizonCode_Horizon_È(Blocks.Ý, BlockStone.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â(), "stone");
        this.HorizonCode_Horizon_È(Blocks.£áƒ, BlockStoneBrick.HorizonCode_Horizon_È.Ý.Â(), "cracked_stonebrick");
        this.HorizonCode_Horizon_È(Blocks.£áƒ, BlockStoneBrick.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â(), "stonebrick");
        this.HorizonCode_Horizon_È(Blocks.£áƒ, BlockStoneBrick.HorizonCode_Horizon_È.Ø­áŒŠá.Â(), "chiseled_stonebrick");
        this.HorizonCode_Horizon_È(Blocks.£áƒ, BlockStoneBrick.HorizonCode_Horizon_È.Â.Â(), "mossy_stonebrick");
        this.HorizonCode_Horizon_È(Blocks.Ø­Âµ, BlockStoneSlab.HorizonCode_Horizon_È.Âµá€.Â(), "brick_slab");
        this.HorizonCode_Horizon_È(Blocks.Ø­Âµ, BlockStoneSlab.HorizonCode_Horizon_È.Ø­áŒŠá.Â(), "cobblestone_slab");
        this.HorizonCode_Horizon_È(Blocks.Ø­Âµ, BlockStoneSlab.HorizonCode_Horizon_È.Ý.Â(), "old_wood_slab");
        this.HorizonCode_Horizon_È(Blocks.Ø­Âµ, BlockStoneSlab.HorizonCode_Horizon_È.à.Â(), "nether_brick_slab");
        this.HorizonCode_Horizon_È(Blocks.Ø­Âµ, BlockStoneSlab.HorizonCode_Horizon_È.Ø.Â(), "quartz_slab");
        this.HorizonCode_Horizon_È(Blocks.Ø­Âµ, BlockStoneSlab.HorizonCode_Horizon_È.Â.Â(), "sandstone_slab");
        this.HorizonCode_Horizon_È(Blocks.Ø­Âµ, BlockStoneSlab.HorizonCode_Horizon_È.Ó.Â(), "stone_brick_slab");
        this.HorizonCode_Horizon_È(Blocks.Ø­Âµ, BlockStoneSlab.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â(), "stone_slab");
        this.HorizonCode_Horizon_È(Blocks.µØ, BlockStoneSlabNew.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â(), "red_sandstone_slab");
        this.HorizonCode_Horizon_È(Blocks.áƒ, BlockTallGrass.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â(), "dead_bush");
        this.HorizonCode_Horizon_È(Blocks.áƒ, BlockTallGrass.HorizonCode_Horizon_È.Ý.Â(), "fern");
        this.HorizonCode_Horizon_È(Blocks.áƒ, BlockTallGrass.HorizonCode_Horizon_È.Â.Â(), "tall_grass");
        this.HorizonCode_Horizon_È(Blocks.ÇŽÊ, BlockPlanks.HorizonCode_Horizon_È.Âµá€.Â(), "acacia_slab");
        this.HorizonCode_Horizon_È(Blocks.ÇŽÊ, BlockPlanks.HorizonCode_Horizon_È.Ý.Â(), "birch_slab");
        this.HorizonCode_Horizon_È(Blocks.ÇŽÊ, BlockPlanks.HorizonCode_Horizon_È.Ó.Â(), "dark_oak_slab");
        this.HorizonCode_Horizon_È(Blocks.ÇŽÊ, BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â(), "jungle_slab");
        this.HorizonCode_Horizon_È(Blocks.ÇŽÊ, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â(), "oak_slab");
        this.HorizonCode_Horizon_È(Blocks.ÇŽÊ, BlockPlanks.HorizonCode_Horizon_È.Â.Â(), "spruce_slab");
        this.HorizonCode_Horizon_È(Blocks.ŠÂµà, EnumDyeColor.£à.Â(), "black_wool");
        this.HorizonCode_Horizon_È(Blocks.ŠÂµà, EnumDyeColor.á.Â(), "blue_wool");
        this.HorizonCode_Horizon_È(Blocks.ŠÂµà, EnumDyeColor.ˆÏ­.Â(), "brown_wool");
        this.HorizonCode_Horizon_È(Blocks.ŠÂµà, EnumDyeColor.áˆºÑ¢Õ.Â(), "cyan_wool");
        this.HorizonCode_Horizon_È(Blocks.ŠÂµà, EnumDyeColor.Ø.Â(), "gray_wool");
        this.HorizonCode_Horizon_È(Blocks.ŠÂµà, EnumDyeColor.£á.Â(), "green_wool");
        this.HorizonCode_Horizon_È(Blocks.ŠÂµà, EnumDyeColor.Ø­áŒŠá.Â(), "light_blue_wool");
        this.HorizonCode_Horizon_È(Blocks.ŠÂµà, EnumDyeColor.Ó.Â(), "lime_wool");
        this.HorizonCode_Horizon_È(Blocks.ŠÂµà, EnumDyeColor.Ý.Â(), "magenta_wool");
        this.HorizonCode_Horizon_È(Blocks.ŠÂµà, EnumDyeColor.Â.Â(), "orange_wool");
        this.HorizonCode_Horizon_È(Blocks.ŠÂµà, EnumDyeColor.à.Â(), "pink_wool");
        this.HorizonCode_Horizon_È(Blocks.ŠÂµà, EnumDyeColor.ÂµÈ.Â(), "purple_wool");
        this.HorizonCode_Horizon_È(Blocks.ŠÂµà, EnumDyeColor.Å.Â(), "red_wool");
        this.HorizonCode_Horizon_È(Blocks.ŠÂµà, EnumDyeColor.áŒŠÆ.Â(), "silver_wool");
        this.HorizonCode_Horizon_È(Blocks.ŠÂµà, EnumDyeColor.HorizonCode_Horizon_È.Â(), "white_wool");
        this.HorizonCode_Horizon_È(Blocks.ŠÂµà, EnumDyeColor.Âµá€.Â(), "yellow_wool");
        this.HorizonCode_Horizon_È(Blocks.Ðƒ, "acacia_stairs");
        this.HorizonCode_Horizon_È(Blocks.Ø­à¢, "activator_rail");
        this.HorizonCode_Horizon_È(Blocks.áˆºá, "beacon");
        this.HorizonCode_Horizon_È(Blocks.áŒŠÆ, "bedrock");
        this.HorizonCode_Horizon_È(Blocks.ŠÏ, "birch_stairs");
        this.HorizonCode_Horizon_È(Blocks.Ï­à, "bookshelf");
        this.HorizonCode_Horizon_È(Blocks.Ä, "brick_block");
        this.HorizonCode_Horizon_È(Blocks.Ä, "brick_block");
        this.HorizonCode_Horizon_È(Blocks.Çªà, "brick_stairs");
        this.HorizonCode_Horizon_È(Blocks.È, "brown_mushroom");
        this.HorizonCode_Horizon_È(Blocks.Ñ¢Ç, "cactus");
        this.HorizonCode_Horizon_È(Blocks.£É, "clay");
        this.HorizonCode_Horizon_È(Blocks.ÐƒÉ, "coal_block");
        this.HorizonCode_Horizon_È(Blocks.ˆà, "coal_ore");
        this.HorizonCode_Horizon_È(Blocks.Ó, "cobblestone");
        this.HorizonCode_Horizon_È(Blocks.ˆÉ, "crafting_table");
        this.HorizonCode_Horizon_È(Blocks.£Ç, "dark_oak_stairs");
        this.HorizonCode_Horizon_È(Blocks.ÐƒÓ, "daylight_detector");
        this.HorizonCode_Horizon_È(Blocks.á€, "dead_bush");
        this.HorizonCode_Horizon_È(Blocks.ˆá, "detector_rail");
        this.HorizonCode_Horizon_È(Blocks.Ø­á, "diamond_block");
        this.HorizonCode_Horizon_È(Blocks.£Ï, "diamond_ore");
        this.HorizonCode_Horizon_È(Blocks.Ñ¢á, "dispenser");
        this.HorizonCode_Horizon_È(Blocks.áŒŠÓ, "dropper");
        this.HorizonCode_Horizon_È(Blocks.ˆØ­áˆº, "emerald_block");
        this.HorizonCode_Horizon_È(Blocks.µÐƒÓ, "emerald_ore");
        this.HorizonCode_Horizon_È(Blocks.¥Âµá€, "enchanting_table");
        this.HorizonCode_Horizon_È(Blocks.¥áŠ, "end_portal_frame");
        this.HorizonCode_Horizon_È(Blocks.µÊ, "end_stone");
        this.HorizonCode_Horizon_È(Blocks.¥É, "oak_fence");
        this.HorizonCode_Horizon_È(Blocks.£ÇªÓ, "spruce_fence");
        this.HorizonCode_Horizon_È(Blocks.ÂµÕ, "birch_fence");
        this.HorizonCode_Horizon_È(Blocks.Š, "jungle_fence");
        this.HorizonCode_Horizon_È(Blocks.Ø­Ñ¢á€, "dark_oak_fence");
        this.HorizonCode_Horizon_È(Blocks.Ñ¢Ó, "acacia_fence");
        this.HorizonCode_Horizon_È(Blocks.ŠáˆºÂ, "oak_fence_gate");
        this.HorizonCode_Horizon_È(Blocks.Ø­Ñ¢Ï­Ø­áˆº, "spruce_fence_gate");
        this.HorizonCode_Horizon_È(Blocks.ŒÂ, "birch_fence_gate");
        this.HorizonCode_Horizon_È(Blocks.Ï­Ï, "jungle_fence_gate");
        this.HorizonCode_Horizon_È(Blocks.ŠØ, "dark_oak_fence_gate");
        this.HorizonCode_Horizon_È(Blocks.ˆÐƒØ, "acacia_fence_gate");
        this.HorizonCode_Horizon_È(Blocks.£Ó, "furnace");
        this.HorizonCode_Horizon_È(Blocks.Ï­Ðƒà, "glass");
        this.HorizonCode_Horizon_È(Blocks.ˆÈ, "glass_pane");
        this.HorizonCode_Horizon_È(Blocks.£Ø­à, "glowstone");
        this.HorizonCode_Horizon_È(Blocks.ÇŽÉ, "golden_rail");
        this.HorizonCode_Horizon_È(Blocks.ˆáŠ, "gold_block");
        this.HorizonCode_Horizon_È(Blocks.£à, "gold_ore");
        this.HorizonCode_Horizon_È(Blocks.Ø­áŒŠá, "grass");
        this.HorizonCode_Horizon_È(Blocks.Å, "gravel");
        this.HorizonCode_Horizon_È(Blocks.Ø­, "hardened_clay");
        this.HorizonCode_Horizon_È(Blocks.ÂµÊ, "hay_block");
        this.HorizonCode_Horizon_È(Blocks.¥Ðƒá, "heavy_weighted_pressure_plate");
        this.HorizonCode_Horizon_È(Blocks.áˆºÉ, "hopper");
        this.HorizonCode_Horizon_È(Blocks.¥Ï, "ice");
        this.HorizonCode_Horizon_È(Blocks.ÇŽÄ, "iron_bars");
        this.HorizonCode_Horizon_È(Blocks.áŒŠ, "iron_block");
        this.HorizonCode_Horizon_È(Blocks.µà, "iron_ore");
        this.HorizonCode_Horizon_È(Blocks.áˆºÓ, "iron_trapdoor");
        this.HorizonCode_Horizon_È(Blocks.Ðƒà, "jukebox");
        this.HorizonCode_Horizon_È(Blocks.ˆ, "jungle_stairs");
        this.HorizonCode_Horizon_È(Blocks.áŒŠÏ, "ladder");
        this.HorizonCode_Horizon_È(Blocks.ŠÄ, "lapis_block");
        this.HorizonCode_Horizon_È(Blocks.áŒŠà, "lapis_ore");
        this.HorizonCode_Horizon_È(Blocks.ÇªÔ, "lever");
        this.HorizonCode_Horizon_È(Blocks.ÇŽÅ, "light_weighted_pressure_plate");
        this.HorizonCode_Horizon_È(Blocks.áŒŠÕ, "lit_pumpkin");
        this.HorizonCode_Horizon_È(Blocks.ˆÅ, "melon_block");
        this.HorizonCode_Horizon_È(Blocks.áˆºáˆºÈ, "mossy_cobblestone");
        this.HorizonCode_Horizon_È(Blocks.Œáƒ, "mycelium");
        this.HorizonCode_Horizon_È(Blocks.áŒŠÔ, "netherrack");
        this.HorizonCode_Horizon_È(Blocks.µÂ, "nether_brick");
        this.HorizonCode_Horizon_È(Blocks.Ñ¢ÇŽÏ, "nether_brick_fence");
        this.HorizonCode_Horizon_È(Blocks.ÇªÂ, "nether_brick_stairs");
        this.HorizonCode_Horizon_È(Blocks.Çªà¢, "noteblock");
        this.HorizonCode_Horizon_È(Blocks.áˆºÏ, "oak_stairs");
        this.HorizonCode_Horizon_È(Blocks.ÇŽá€, "obsidian");
        this.HorizonCode_Horizon_È(Blocks.ŠÂµÏ, "packed_ice");
        this.HorizonCode_Horizon_È(Blocks.Õ, "piston");
        this.HorizonCode_Horizon_È(Blocks.Ø­Æ, "pumpkin");
        this.HorizonCode_Horizon_È(Blocks.ÐƒáˆºÄ, "quartz_ore");
        this.HorizonCode_Horizon_È(Blocks.Ñ¢Õ, "quartz_stairs");
        this.HorizonCode_Horizon_È(Blocks.áŒŠáŠ, "rail");
        this.HorizonCode_Horizon_È(Blocks.ŒÐƒà, "redstone_block");
        this.HorizonCode_Horizon_È(Blocks.áŒŠÉ, "redstone_lamp");
        this.HorizonCode_Horizon_È(Blocks.Ñ¢à, "redstone_ore");
        this.HorizonCode_Horizon_È(Blocks.áˆº, "redstone_torch");
        this.HorizonCode_Horizon_È(Blocks.áŠ, "red_mushroom");
        this.HorizonCode_Horizon_È(Blocks.µÏ, "sandstone_stairs");
        this.HorizonCode_Horizon_È(Blocks.ˆÇªÓ, "red_sandstone_stairs");
        this.HorizonCode_Horizon_È(Blocks.Ï­È, "sea_lantern");
        this.HorizonCode_Horizon_È(Blocks.ÇŽØ­à, "slime");
        this.HorizonCode_Horizon_È(Blocks.ˆà¢, "snow");
        this.HorizonCode_Horizon_È(Blocks.áŒŠá€, "snow_layer");
        this.HorizonCode_Horizon_È(Blocks.ŠÕ, "soul_sand");
        this.HorizonCode_Horizon_È(Blocks.£Ô, "spruce_stairs");
        this.HorizonCode_Horizon_È(Blocks.ÇŽÕ, "sticky_piston");
        this.HorizonCode_Horizon_È(Blocks.¥Å, "stone_brick_stairs");
        this.HorizonCode_Horizon_È(Blocks.Šà, "stone_button");
        this.HorizonCode_Horizon_È(Blocks.Û, "stone_pressure_plate");
        this.HorizonCode_Horizon_È(Blocks.ˆÓ, "stone_stairs");
        this.HorizonCode_Horizon_È(Blocks.Ñ¢Â, "tnt");
        this.HorizonCode_Horizon_È(Blocks.Ï, "torch");
        this.HorizonCode_Horizon_È(Blocks.áˆºà, "trapdoor");
        this.HorizonCode_Horizon_È(Blocks.ˆÂ, "tripwire_hook");
        this.HorizonCode_Horizon_È(Blocks.ÇŽà, "vine");
        this.HorizonCode_Horizon_È(Blocks.Œá, "waterlily");
        this.HorizonCode_Horizon_È(Blocks.É, "web");
        this.HorizonCode_Horizon_È(Blocks.ˆÕ, "wooden_button");
        this.HorizonCode_Horizon_È(Blocks.ÇŽá, "wooden_pressure_plate");
        this.HorizonCode_Horizon_È(Blocks.Âµà, BlockFlower.Â.HorizonCode_Horizon_È.Ý(), "dandelion");
        this.HorizonCode_Horizon_È(Blocks.ˆáƒ, "chest");
        this.HorizonCode_Horizon_È(Blocks.ÇŽ, "trapped_chest");
        this.HorizonCode_Horizon_È(Blocks.¥áŒŠà, "ender_chest");
        this.HorizonCode_Horizon_È(Items.HorizonCode_Horizon_È, "iron_shovel");
        this.HorizonCode_Horizon_È(Items.Â, "iron_pickaxe");
        this.HorizonCode_Horizon_È(Items.Ý, "iron_axe");
        this.HorizonCode_Horizon_È(Items.Ø­áŒŠá, "flint_and_steel");
        this.HorizonCode_Horizon_È(Items.Âµá€, "apple");
        this.HorizonCode_Horizon_È(Items.Ó, 0, "bow");
        this.HorizonCode_Horizon_È(Items.Ó, 1, "bow_pulling_0");
        this.HorizonCode_Horizon_È(Items.Ó, 2, "bow_pulling_1");
        this.HorizonCode_Horizon_È(Items.Ó, 3, "bow_pulling_2");
        this.HorizonCode_Horizon_È(Items.à, "arrow");
        this.HorizonCode_Horizon_È(Items.Ø, 0, "coal");
        this.HorizonCode_Horizon_È(Items.Ø, 1, "charcoal");
        this.HorizonCode_Horizon_È(Items.áŒŠÆ, "diamond");
        this.HorizonCode_Horizon_È(Items.áˆºÑ¢Õ, "iron_ingot");
        this.HorizonCode_Horizon_È(Items.ÂµÈ, "gold_ingot");
        this.HorizonCode_Horizon_È(Items.á, "iron_sword");
        this.HorizonCode_Horizon_È(Items.ˆÏ­, "wooden_sword");
        this.HorizonCode_Horizon_È(Items.£á, "wooden_shovel");
        this.HorizonCode_Horizon_È(Items.Å, "wooden_pickaxe");
        this.HorizonCode_Horizon_È(Items.£à, "wooden_axe");
        this.HorizonCode_Horizon_È(Items.µà, "stone_sword");
        this.HorizonCode_Horizon_È(Items.ˆà, "stone_shovel");
        this.HorizonCode_Horizon_È(Items.¥Æ, "stone_pickaxe");
        this.HorizonCode_Horizon_È(Items.Ø­à, "stone_axe");
        this.HorizonCode_Horizon_È(Items.µÕ, "diamond_sword");
        this.HorizonCode_Horizon_È(Items.Æ, "diamond_shovel");
        this.HorizonCode_Horizon_È(Items.Šáƒ, "diamond_pickaxe");
        this.HorizonCode_Horizon_È(Items.Ï­Ðƒà, "diamond_axe");
        this.HorizonCode_Horizon_È(Items.áŒŠà, "stick");
        this.HorizonCode_Horizon_È(Items.ŠÄ, "bowl");
        this.HorizonCode_Horizon_È(Items.Ñ¢á, "mushroom_stew");
        this.HorizonCode_Horizon_È(Items.ŒÏ, "golden_sword");
        this.HorizonCode_Horizon_È(Items.Çªà¢, "golden_shovel");
        this.HorizonCode_Horizon_È(Items.Ê, "golden_pickaxe");
        this.HorizonCode_Horizon_È(Items.ÇŽÉ, "golden_axe");
        this.HorizonCode_Horizon_È(Items.ˆá, "string");
        this.HorizonCode_Horizon_È(Items.ÇŽÕ, "feather");
        this.HorizonCode_Horizon_È(Items.É, "gunpowder");
        this.HorizonCode_Horizon_È(Items.áƒ, "wooden_hoe");
        this.HorizonCode_Horizon_È(Items.á€, "stone_hoe");
        this.HorizonCode_Horizon_È(Items.Õ, "iron_hoe");
        this.HorizonCode_Horizon_È(Items.à¢, "diamond_hoe");
        this.HorizonCode_Horizon_È(Items.ŠÂµà, "golden_hoe");
        this.HorizonCode_Horizon_È(Items.¥à, "wheat_seeds");
        this.HorizonCode_Horizon_È(Items.Âµà, "wheat");
        this.HorizonCode_Horizon_È(Items.Ç, "bread");
        this.HorizonCode_Horizon_È(Items.È, "leather_helmet");
        this.HorizonCode_Horizon_È(Items.áŠ, "leather_chestplate");
        this.HorizonCode_Horizon_È(Items.ˆáŠ, "leather_leggings");
        this.HorizonCode_Horizon_È(Items.áŒŠ, "leather_boots");
        this.HorizonCode_Horizon_È(Items.£ÂµÄ, "chainmail_helmet");
        this.HorizonCode_Horizon_È(Items.Ø­Âµ, "chainmail_chestplate");
        this.HorizonCode_Horizon_È(Items.Ä, "chainmail_leggings");
        this.HorizonCode_Horizon_È(Items.Ñ¢Â, "chainmail_boots");
        this.HorizonCode_Horizon_È(Items.Ï­à, "iron_helmet");
        this.HorizonCode_Horizon_È(Items.áˆºáˆºÈ, "iron_chestplate");
        this.HorizonCode_Horizon_È(Items.ÇŽá€, "iron_leggings");
        this.HorizonCode_Horizon_È(Items.Ï, "iron_boots");
        this.HorizonCode_Horizon_È(Items.Ô, "diamond_helmet");
        this.HorizonCode_Horizon_È(Items.ÇªÓ, "diamond_chestplate");
        this.HorizonCode_Horizon_È(Items.áˆºÏ, "diamond_leggings");
        this.HorizonCode_Horizon_È(Items.ˆáƒ, "diamond_boots");
        this.HorizonCode_Horizon_È(Items.Œ, "golden_helmet");
        this.HorizonCode_Horizon_È(Items.£Ï, "golden_chestplate");
        this.HorizonCode_Horizon_È(Items.Ø­á, "golden_leggings");
        this.HorizonCode_Horizon_È(Items.ˆÉ, "golden_boots");
        this.HorizonCode_Horizon_È(Items.Ï­Ï­Ï, "flint");
        this.HorizonCode_Horizon_È(Items.£Â, "porkchop");
        this.HorizonCode_Horizon_È(Items.£Ó, "cooked_porkchop");
        this.HorizonCode_Horizon_È(Items.ˆÐƒØ­à, "painting");
        this.HorizonCode_Horizon_È(Items.£Õ, "golden_apple");
        this.HorizonCode_Horizon_È(Items.£Õ, 1, "golden_apple");
        this.HorizonCode_Horizon_È(Items.Ï­Ô, "sign");
        this.HorizonCode_Horizon_È(Items.Œà, "oak_door");
        this.HorizonCode_Horizon_È(Items.Ðƒá, "spruce_door");
        this.HorizonCode_Horizon_È(Items.ˆÏ, "birch_door");
        this.HorizonCode_Horizon_È(Items.áˆºÇŽØ, "jungle_door");
        this.HorizonCode_Horizon_È(Items.ÇªÂµÕ, "acacia_door");
        this.HorizonCode_Horizon_È(Items.áŒŠÏ, "dark_oak_door");
        this.HorizonCode_Horizon_È(Items.áŒŠáŠ, "bucket");
        this.HorizonCode_Horizon_È(Items.ˆÓ, "water_bucket");
        this.HorizonCode_Horizon_È(Items.¥Ä, "lava_bucket");
        this.HorizonCode_Horizon_È(Items.ÇªÔ, "minecart");
        this.HorizonCode_Horizon_È(Items.Û, "saddle");
        this.HorizonCode_Horizon_È(Items.ŠÓ, "iron_door");
        this.HorizonCode_Horizon_È(Items.ÇŽá, "redstone");
        this.HorizonCode_Horizon_È(Items.Ñ¢à, "snowball");
        this.HorizonCode_Horizon_È(Items.ÇªØ­, "boat");
        this.HorizonCode_Horizon_È(Items.£áŒŠá, "leather");
        this.HorizonCode_Horizon_È(Items.áˆº, "milk_bucket");
        this.HorizonCode_Horizon_È(Items.Šà, "brick");
        this.HorizonCode_Horizon_È(Items.áŒŠá€, "clay_ball");
        this.HorizonCode_Horizon_È(Items.¥Ï, "reeds");
        this.HorizonCode_Horizon_È(Items.ˆà¢, "paper");
        this.HorizonCode_Horizon_È(Items.Ñ¢Ç, "book");
        this.HorizonCode_Horizon_È(Items.£É, "slime_ball");
        this.HorizonCode_Horizon_È(Items.Ðƒáƒ, "chest_minecart");
        this.HorizonCode_Horizon_È(Items.Ðƒà, "furnace_minecart");
        this.HorizonCode_Horizon_È(Items.¥É, "egg");
        this.HorizonCode_Horizon_È(Items.£ÇªÓ, "compass");
        this.HorizonCode_Horizon_È(Items.ÂµÕ, "fishing_rod");
        this.HorizonCode_Horizon_È(Items.ÂµÕ, 1, "fishing_rod_cast");
        this.HorizonCode_Horizon_È(Items.Š, "clock");
        this.HorizonCode_Horizon_È(Items.Ø­Ñ¢á€, "glowstone_dust");
        this.HorizonCode_Horizon_È(Items.Ñ¢Ó, ItemFishFood.HorizonCode_Horizon_È.HorizonCode_Horizon_È.HorizonCode_Horizon_È(), "cod");
        this.HorizonCode_Horizon_È(Items.Ñ¢Ó, ItemFishFood.HorizonCode_Horizon_È.Â.HorizonCode_Horizon_È(), "salmon");
        this.HorizonCode_Horizon_È(Items.Ñ¢Ó, ItemFishFood.HorizonCode_Horizon_È.Ý.HorizonCode_Horizon_È(), "clownfish");
        this.HorizonCode_Horizon_È(Items.Ñ¢Ó, ItemFishFood.HorizonCode_Horizon_È.Ø­áŒŠá.HorizonCode_Horizon_È(), "pufferfish");
        this.HorizonCode_Horizon_È(Items.Ø­Æ, ItemFishFood.HorizonCode_Horizon_È.HorizonCode_Horizon_È.HorizonCode_Horizon_È(), "cooked_cod");
        this.HorizonCode_Horizon_È(Items.Ø­Æ, ItemFishFood.HorizonCode_Horizon_È.Â.HorizonCode_Horizon_È(), "cooked_salmon");
        this.HorizonCode_Horizon_È(Items.áŒŠÔ, EnumDyeColor.£à.Ý(), "dye_black");
        this.HorizonCode_Horizon_È(Items.áŒŠÔ, EnumDyeColor.Å.Ý(), "dye_red");
        this.HorizonCode_Horizon_È(Items.áŒŠÔ, EnumDyeColor.£á.Ý(), "dye_green");
        this.HorizonCode_Horizon_È(Items.áŒŠÔ, EnumDyeColor.ˆÏ­.Ý(), "dye_brown");
        this.HorizonCode_Horizon_È(Items.áŒŠÔ, EnumDyeColor.á.Ý(), "dye_blue");
        this.HorizonCode_Horizon_È(Items.áŒŠÔ, EnumDyeColor.ÂµÈ.Ý(), "dye_purple");
        this.HorizonCode_Horizon_È(Items.áŒŠÔ, EnumDyeColor.áˆºÑ¢Õ.Ý(), "dye_cyan");
        this.HorizonCode_Horizon_È(Items.áŒŠÔ, EnumDyeColor.áŒŠÆ.Ý(), "dye_silver");
        this.HorizonCode_Horizon_È(Items.áŒŠÔ, EnumDyeColor.Ø.Ý(), "dye_gray");
        this.HorizonCode_Horizon_È(Items.áŒŠÔ, EnumDyeColor.à.Ý(), "dye_pink");
        this.HorizonCode_Horizon_È(Items.áŒŠÔ, EnumDyeColor.Ó.Ý(), "dye_lime");
        this.HorizonCode_Horizon_È(Items.áŒŠÔ, EnumDyeColor.Âµá€.Ý(), "dye_yellow");
        this.HorizonCode_Horizon_È(Items.áŒŠÔ, EnumDyeColor.Ø­áŒŠá.Ý(), "dye_light_blue");
        this.HorizonCode_Horizon_È(Items.áŒŠÔ, EnumDyeColor.Ý.Ý(), "dye_magenta");
        this.HorizonCode_Horizon_È(Items.áŒŠÔ, EnumDyeColor.Â.Ý(), "dye_orange");
        this.HorizonCode_Horizon_È(Items.áŒŠÔ, EnumDyeColor.HorizonCode_Horizon_È.Ý(), "dye_white");
        this.HorizonCode_Horizon_È(Items.ŠÕ, "bone");
        this.HorizonCode_Horizon_È(Items.£Ø­à, "sugar");
        this.HorizonCode_Horizon_È(Items.µÐƒáƒ, "cake");
        this.HorizonCode_Horizon_È(Items.áŒŠÕ, "bed");
        this.HorizonCode_Horizon_È(Items.ÂµÂ, "repeater");
        this.HorizonCode_Horizon_È(Items.áŒŠá, "cookie");
        this.HorizonCode_Horizon_È(Items.áˆºà, "shears");
        this.HorizonCode_Horizon_È(Items.ÐƒÂ, "melon");
        this.HorizonCode_Horizon_È(Items.£áƒ, "pumpkin_seeds");
        this.HorizonCode_Horizon_È(Items.Ï­áˆºÓ, "melon_seeds");
        this.HorizonCode_Horizon_È(Items.Çª, "beef");
        this.HorizonCode_Horizon_È(Items.ÇŽÄ, "cooked_beef");
        this.HorizonCode_Horizon_È(Items.ˆÈ, "chicken");
        this.HorizonCode_Horizon_È(Items.ˆÅ, "cooked_chicken");
        this.HorizonCode_Horizon_È(Items.ÇŽà, "rabbit");
        this.HorizonCode_Horizon_È(Items.ŠáˆºÂ, "cooked_rabbit");
        this.HorizonCode_Horizon_È(Items.ÇªÉ, "mutton");
        this.HorizonCode_Horizon_È(Items.ŠÏ­áˆºá, "cooked_mutton");
        this.HorizonCode_Horizon_È(Items.ŒÂ, "rabbit_foot");
        this.HorizonCode_Horizon_È(Items.Ï­Ï, "rabbit_hide");
        this.HorizonCode_Horizon_È(Items.Ø­Ñ¢Ï­Ø­áˆº, "rabbit_stew");
        this.HorizonCode_Horizon_È(Items.ŠØ, "rotten_flesh");
        this.HorizonCode_Horizon_È(Items.ˆÐƒØ, "ender_pearl");
        this.HorizonCode_Horizon_È(Items.Çªà, "blaze_rod");
        this.HorizonCode_Horizon_È(Items.¥Å, "ghast_tear");
        this.HorizonCode_Horizon_È(Items.Œáƒ, "gold_nugget");
        this.HorizonCode_Horizon_È(Items.Œá, "nether_wart");
        this.ˆÏ­.HorizonCode_Horizon_È(Items.µÂ, new ItemMeshDefinition() {
            private static final String Â = "CL_00002440";
            
            @Override
            public ModelResourceLocation HorizonCode_Horizon_È(final ItemStack p_178113_1_) {
                return ItemPotion.Ó(p_178113_1_.Ø()) ? new ModelResourceLocation("bottle_splash", "inventory") : new ModelResourceLocation("bottle_drinkable", "inventory");
            }
        });
        this.HorizonCode_Horizon_È(Items.Ñ¢ÇŽÏ, "glass_bottle");
        this.HorizonCode_Horizon_È(Items.ÇªÂ, "spider_eye");
        this.HorizonCode_Horizon_È(Items.ÂµáˆºÂ, "fermented_spider_eye");
        this.HorizonCode_Horizon_È(Items.¥Âµá€, "blaze_powder");
        this.HorizonCode_Horizon_È(Items.ÇŽÈ, "magma_cream");
        this.HorizonCode_Horizon_È(Items.ÇªáˆºÕ, "brewing_stand");
        this.HorizonCode_Horizon_È(Items.Ï­Ä, "cauldron");
        this.HorizonCode_Horizon_È(Items.¥áŠ, "ender_eye");
        this.HorizonCode_Horizon_È(Items.µÊ, "speckled_melon");
        this.ˆÏ­.HorizonCode_Horizon_È(Items.áˆºáˆºáŠ, new ItemMeshDefinition() {
            private static final String Â = "CL_00002439";
            
            @Override
            public ModelResourceLocation HorizonCode_Horizon_È(final ItemStack p_178113_1_) {
                return new ModelResourceLocation("spawn_egg", "inventory");
            }
        });
        this.HorizonCode_Horizon_È(Items.áŒŠÉ, "experience_bottle");
        this.HorizonCode_Horizon_È(Items.ÇŽØ, "fire_charge");
        this.HorizonCode_Horizon_È(Items.ŒÓ, "writable_book");
        this.HorizonCode_Horizon_È(Items.µ, "emerald");
        this.HorizonCode_Horizon_È(Items.µÏ, "item_frame");
        this.HorizonCode_Horizon_È(Items.µÐƒÓ, "flower_pot");
        this.HorizonCode_Horizon_È(Items.¥áŒŠà, "carrot");
        this.HorizonCode_Horizon_È(Items.ˆÂ, "potato");
        this.HorizonCode_Horizon_È(Items.áŒŠÈ, "baked_potato");
        this.HorizonCode_Horizon_È(Items.ˆØ­áˆº, "poisonous_potato");
        this.HorizonCode_Horizon_È(Items.£Ô, "map");
        this.HorizonCode_Horizon_È(Items.ŠÏ, "golden_carrot");
        this.HorizonCode_Horizon_È(Items.ˆ, 0, "skull_skeleton");
        this.HorizonCode_Horizon_È(Items.ˆ, 1, "skull_wither");
        this.HorizonCode_Horizon_È(Items.ˆ, 2, "skull_zombie");
        this.HorizonCode_Horizon_È(Items.ˆ, 3, "skull_char");
        this.HorizonCode_Horizon_È(Items.ˆ, 4, "skull_creeper");
        this.HorizonCode_Horizon_È(Items.ŠÑ¢Ó, "carrot_on_a_stick");
        this.HorizonCode_Horizon_È(Items.áˆºá, "nether_star");
        this.HorizonCode_Horizon_È(Items.Ï­Ó, "pumpkin_pie");
        this.HorizonCode_Horizon_È(Items.Ñ¢È, "firework_charge");
        this.HorizonCode_Horizon_È(Items.ˆÕ, "comparator");
        this.HorizonCode_Horizon_È(Items.ÇªÈ, "netherbrick");
        this.HorizonCode_Horizon_È(Items.ÇªÅ, "quartz");
        this.HorizonCode_Horizon_È(Items.ÇŽ, "tnt_minecart");
        this.HorizonCode_Horizon_È(Items.ÇŽÅ, "hopper_minecart");
        this.HorizonCode_Horizon_È(Items.¥Ðƒá, "armor_stand");
        this.HorizonCode_Horizon_È(Items.ÐƒÇŽà, "iron_horse_armor");
        this.HorizonCode_Horizon_È(Items.¥Ê, "golden_horse_armor");
        this.HorizonCode_Horizon_È(Items.ÐƒÓ, "diamond_horse_armor");
        this.HorizonCode_Horizon_È(Items.áˆºÕ, "lead");
        this.HorizonCode_Horizon_È(Items.ŒÐƒà, "name_tag");
        this.ˆÏ­.HorizonCode_Horizon_È(Items.£Ç, new ItemMeshDefinition() {
            private static final String Â = "CL_00002438";
            
            @Override
            public ModelResourceLocation HorizonCode_Horizon_È(final ItemStack p_178113_1_) {
                return new ModelResourceLocation("banner", "inventory");
            }
        });
        this.HorizonCode_Horizon_È(Items.áˆºÉ, "record_13");
        this.HorizonCode_Horizon_È(Items.Ø­È, "record_cat");
        this.HorizonCode_Horizon_È(Items.Ñ¢Õ, "record_blocks");
        this.HorizonCode_Horizon_È(Items.Ø­à¢, "record_chirp");
        this.HorizonCode_Horizon_È(Items.áŒŠÓ, "record_far");
        this.HorizonCode_Horizon_È(Items.Ø­Â, "record_mall");
        this.HorizonCode_Horizon_È(Items.¥ÇªÅ, "record_mellohi");
        this.HorizonCode_Horizon_È(Items.áˆºÓ, "record_stal");
        this.HorizonCode_Horizon_È(Items.ÂµÊ, "record_strad");
        this.HorizonCode_Horizon_È(Items.áˆºÂ, "record_ward");
        this.HorizonCode_Horizon_È(Items.Ø­, "record_11");
        this.HorizonCode_Horizon_È(Items.ÐƒÉ, "record_wait");
        this.HorizonCode_Horizon_È(Items.ŠÂµÏ, "prismarine_shard");
        this.HorizonCode_Horizon_È(Items.Ðƒ, "prismarine_crystals");
        this.ˆÏ­.HorizonCode_Horizon_È(Items.Çªáˆºá, new ItemMeshDefinition() {
            private static final String Â = "CL_00002437";
            
            @Override
            public ModelResourceLocation HorizonCode_Horizon_È(final ItemStack p_178113_1_) {
                return new ModelResourceLocation("enchanted_book", "inventory");
            }
        });
        this.ˆÏ­.HorizonCode_Horizon_È(Items.ˆØ, new ItemMeshDefinition() {
            private static final String Â = "CL_00002436";
            
            @Override
            public ModelResourceLocation HorizonCode_Horizon_È(final ItemStack p_178113_1_) {
                return new ModelResourceLocation("filled_map", "inventory");
            }
        });
        this.HorizonCode_Horizon_È(Blocks.ŠÑ¢Ó, "command_block");
        this.HorizonCode_Horizon_È(Items.ŠáŒŠà¢, "fireworks");
        this.HorizonCode_Horizon_È(Items.ÐƒáˆºÄ, "command_block_minecart");
        this.HorizonCode_Horizon_È(Blocks.¥ÇªÅ, "barrier");
        this.HorizonCode_Horizon_È(Blocks.ÇªÓ, "mob_spawner");
        this.HorizonCode_Horizon_È(Items.ÇŽÊ, "written_book");
        this.HorizonCode_Horizon_È(Blocks.Ï­áˆºÓ, BlockHugeMushroom.HorizonCode_Horizon_È.ÂµÈ.Â(), "brown_mushroom_block");
        this.HorizonCode_Horizon_È(Blocks.Çª, BlockHugeMushroom.HorizonCode_Horizon_È.ÂµÈ.Â(), "red_mushroom_block");
        this.HorizonCode_Horizon_È(Blocks.áˆºáˆºáŠ, "dragon_egg");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IResourceManager p_110549_1_) {
        this.ˆÏ­.Â();
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002441";
        
        static {
            HorizonCode_Horizon_È = new int[ItemCameraTransforms.Â.values().length];
            try {
                RenderItem.HorizonCode_Horizon_È.HorizonCode_Horizon_È[ItemCameraTransforms.Â.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                RenderItem.HorizonCode_Horizon_È.HorizonCode_Horizon_È[ItemCameraTransforms.Â.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                RenderItem.HorizonCode_Horizon_È.HorizonCode_Horizon_È[ItemCameraTransforms.Â.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                RenderItem.HorizonCode_Horizon_È.HorizonCode_Horizon_È[ItemCameraTransforms.Â.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                RenderItem.HorizonCode_Horizon_È.HorizonCode_Horizon_È[ItemCameraTransforms.Â.Âµá€.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
        }
    }
}
