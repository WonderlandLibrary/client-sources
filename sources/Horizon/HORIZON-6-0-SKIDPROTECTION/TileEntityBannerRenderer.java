package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Map;

public class TileEntityBannerRenderer extends TileEntitySpecialRenderer
{
    private static final Map HorizonCode_Horizon_È;
    private static final ResourceLocation_1975012498 Ø­áŒŠá;
    private ModelBanner Âµá€;
    private static final String Ó = "CL_00002473";
    
    static {
        HorizonCode_Horizon_È = Maps.newHashMap();
        Ø­áŒŠá = new ResourceLocation_1975012498("textures/entity/banner_base.png");
    }
    
    public TileEntityBannerRenderer() {
        this.Âµá€ = new ModelBanner();
    }
    
    public void HorizonCode_Horizon_È(final TileEntityBanner p_180545_1_, final double p_180545_2_, final double p_180545_4_, final double p_180545_6_, final float p_180545_8_, final int p_180545_9_) {
        final boolean var10 = p_180545_1_.ÇŽÉ() != null;
        final boolean var11 = !var10 || p_180545_1_.ˆÏ­() == Blocks.Ï­áŠ;
        final int var12 = var10 ? p_180545_1_.áˆºÑ¢Õ() : 0;
        final long var13 = var10 ? p_180545_1_.ÇŽÉ().Šáƒ() : 0L;
        GlStateManager.Çªà¢();
        final float var14 = 0.6666667f;
        if (var11) {
            GlStateManager.Â((float)p_180545_2_ + 0.5f, (float)p_180545_4_ + 0.75f * var14, (float)p_180545_6_ + 0.5f);
            final float var15 = var12 * 360 / 16.0f;
            GlStateManager.Â(-var15, 0.0f, 1.0f, 0.0f);
            this.Âµá€.Â.áˆºÑ¢Õ = true;
        }
        else {
            float var16 = 0.0f;
            if (var12 == 2) {
                var16 = 180.0f;
            }
            if (var12 == 4) {
                var16 = 90.0f;
            }
            if (var12 == 5) {
                var16 = -90.0f;
            }
            GlStateManager.Â((float)p_180545_2_ + 0.5f, (float)p_180545_4_ - 0.25f * var14, (float)p_180545_6_ + 0.5f);
            GlStateManager.Â(-var16, 0.0f, 1.0f, 0.0f);
            GlStateManager.Â(0.0f, -0.3125f, -0.4375f);
            this.Âµá€.Â.áˆºÑ¢Õ = false;
        }
        final BlockPos var17 = p_180545_1_.á();
        float var16 = var17.HorizonCode_Horizon_È() * 7 + var17.Â() * 9 + var17.Ý() * 13 + var13 + p_180545_8_;
        this.Âµá€.HorizonCode_Horizon_È.Ó = (-0.0125f + 0.01f * MathHelper.Â(var16 * 3.1415927f * 0.02f)) * 3.1415927f;
        GlStateManager.ŠÄ();
        final ResourceLocation_1975012498 var18 = this.HorizonCode_Horizon_È(p_180545_1_);
        if (var18 != null) {
            this.HorizonCode_Horizon_È(var18);
            GlStateManager.Çªà¢();
            GlStateManager.HorizonCode_Horizon_È(var14, -var14, -var14);
            this.Âµá€.HorizonCode_Horizon_È();
            GlStateManager.Ê();
        }
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.Ê();
    }
    
    private ResourceLocation_1975012498 HorizonCode_Horizon_È(final TileEntityBanner p_178463_1_) {
        final String var2 = p_178463_1_.Ø­áŒŠá();
        if (var2.isEmpty()) {
            return null;
        }
        HorizonCode_Horizon_È var3 = TileEntityBannerRenderer.HorizonCode_Horizon_È.get(var2);
        if (var3 == null) {
            if (TileEntityBannerRenderer.HorizonCode_Horizon_È.size() >= 256) {
                final long var4 = System.currentTimeMillis();
                final Iterator var5 = TileEntityBannerRenderer.HorizonCode_Horizon_È.keySet().iterator();
                while (var5.hasNext()) {
                    final String var6 = var5.next();
                    final HorizonCode_Horizon_È var7 = TileEntityBannerRenderer.HorizonCode_Horizon_È.get(var6);
                    if (var4 - var7.HorizonCode_Horizon_È > 60000L) {
                        Minecraft.áŒŠà().¥à().Ý(var7.Â);
                        var5.remove();
                    }
                }
                if (TileEntityBannerRenderer.HorizonCode_Horizon_È.size() >= 256) {
                    return null;
                }
            }
            final List var8 = p_178463_1_.Â();
            final List var9 = p_178463_1_.Ý();
            final ArrayList var10 = Lists.newArrayList();
            for (final TileEntityBanner.HorizonCode_Horizon_È var12 : var8) {
                var10.add("textures/entity/banner/" + var12.HorizonCode_Horizon_È() + ".png");
            }
            var3 = new HorizonCode_Horizon_È(null);
            var3.Â = new ResourceLocation_1975012498(var2);
            Minecraft.áŒŠà().¥à().HorizonCode_Horizon_È(var3.Â, new LayeredColorMaskTexture(TileEntityBannerRenderer.Ø­áŒŠá, var10, var9));
            TileEntityBannerRenderer.HorizonCode_Horizon_È.put(var2, var3);
        }
        var3.HorizonCode_Horizon_È = System.currentTimeMillis();
        return var3.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final TileEntity p_180535_1_, final double p_180535_2_, final double p_180535_4_, final double p_180535_6_, final float p_180535_8_, final int p_180535_9_) {
        this.HorizonCode_Horizon_È((TileEntityBanner)p_180535_1_, p_180535_2_, p_180535_4_, p_180535_6_, p_180535_8_, p_180535_9_);
    }
    
    static class HorizonCode_Horizon_È
    {
        public long HorizonCode_Horizon_È;
        public ResourceLocation_1975012498 Â;
        private static final String Ý = "CL_00002471";
        
        private HorizonCode_Horizon_È() {
        }
        
        HorizonCode_Horizon_È(final Object p_i46209_1_) {
            this();
        }
    }
}
