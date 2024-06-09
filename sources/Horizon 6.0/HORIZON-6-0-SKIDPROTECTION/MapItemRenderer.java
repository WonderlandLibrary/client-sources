package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Maps;
import java.util.Map;

public class MapItemRenderer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private final TextureManager Â;
    private final Map Ý;
    private static final String Ø­áŒŠá = "CL_00000663";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/map/map_icons.png");
    }
    
    public MapItemRenderer(final TextureManager p_i45009_1_) {
        this.Ý = Maps.newHashMap();
        this.Â = p_i45009_1_;
    }
    
    public void HorizonCode_Horizon_È(final MapData p_148246_1_) {
        this.Â(p_148246_1_).HorizonCode_Horizon_È();
    }
    
    public void HorizonCode_Horizon_È(final MapData p_148250_1_, final boolean p_148250_2_) {
        this.Â(p_148250_1_).HorizonCode_Horizon_È(p_148250_2_);
    }
    
    private HorizonCode_Horizon_È Â(final MapData p_148248_1_) {
        HorizonCode_Horizon_È var2 = this.Ý.get(p_148248_1_.HorizonCode_Horizon_È);
        if (var2 == null) {
            var2 = new HorizonCode_Horizon_È(p_148248_1_, null);
            this.Ý.put(p_148248_1_.HorizonCode_Horizon_È, var2);
        }
        return var2;
    }
    
    public void HorizonCode_Horizon_È() {
        for (final HorizonCode_Horizon_È var2 : this.Ý.values()) {
            this.Â.Ý(var2.Ø­áŒŠá);
        }
        this.Ý.clear();
    }
    
    class HorizonCode_Horizon_È
    {
        private final MapData Â;
        private final DynamicTexture Ý;
        private final ResourceLocation_1975012498 Ø­áŒŠá;
        private final int[] Âµá€;
        private static final String Ó = "CL_00000665";
        
        private HorizonCode_Horizon_È(final MapData p_i45007_2_) {
            this.Â = p_i45007_2_;
            this.Ý = new DynamicTexture(128, 128);
            this.Âµá€ = this.Ý.Ý();
            this.Ø­áŒŠá = MapItemRenderer.this.Â.HorizonCode_Horizon_È("map/" + p_i45007_2_.HorizonCode_Horizon_È, this.Ý);
            for (int var3 = 0; var3 < this.Âµá€.length; ++var3) {
                this.Âµá€[var3] = 0;
            }
        }
        
        private void HorizonCode_Horizon_È() {
            for (int var1 = 0; var1 < 16384; ++var1) {
                final int var2 = this.Â.Ó[var1] & 0xFF;
                if (var2 / 4 == 0) {
                    this.Âµá€[var1] = (var1 + var1 / 128 & 0x1) * 8 + 16 << 24;
                }
                else {
                    this.Âµá€[var1] = MapColor.HorizonCode_Horizon_È[var2 / 4].HorizonCode_Horizon_È(var2 & 0x3);
                }
            }
            this.Ý.Â();
        }
        
        private void HorizonCode_Horizon_È(final boolean p_148237_1_) {
            final byte var2 = 0;
            final byte var3 = 0;
            final Tessellator var4 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer var5 = var4.Ý();
            final float var6 = 0.0f;
            MapItemRenderer.this.Â.HorizonCode_Horizon_È(this.Ø­áŒŠá);
            GlStateManager.á();
            GlStateManager.HorizonCode_Horizon_È(1, 771, 0, 1);
            GlStateManager.Ý();
            var5.Â();
            var5.HorizonCode_Horizon_È(var2 + 0 + var6, var3 + 128 - var6, -0.009999999776482582, 0.0, 1.0);
            var5.HorizonCode_Horizon_È(var2 + 128 - var6, var3 + 128 - var6, -0.009999999776482582, 1.0, 1.0);
            var5.HorizonCode_Horizon_È(var2 + 128 - var6, var3 + 0 + var6, -0.009999999776482582, 1.0, 0.0);
            var5.HorizonCode_Horizon_È(var2 + 0 + var6, var3 + 0 + var6, -0.009999999776482582, 0.0, 0.0);
            var4.Â();
            GlStateManager.Ø­áŒŠá();
            GlStateManager.ÂµÈ();
            MapItemRenderer.this.Â.HorizonCode_Horizon_È(MapItemRenderer.HorizonCode_Horizon_È);
            int var7 = 0;
            for (final Vec4b var9 : this.Â.Ø.values()) {
                if (!p_148237_1_ || var9.HorizonCode_Horizon_È() == 1) {
                    GlStateManager.Çªà¢();
                    GlStateManager.Â(var2 + var9.Â() / 2.0f + 64.0f, var3 + var9.Ý() / 2.0f + 64.0f, -0.02f);
                    GlStateManager.Â(var9.Ø­áŒŠá() * 360 / 16.0f, 0.0f, 0.0f, 1.0f);
                    GlStateManager.HorizonCode_Horizon_È(4.0f, 4.0f, 3.0f);
                    GlStateManager.Â(-0.125f, 0.125f, 0.0f);
                    final byte var10 = var9.HorizonCode_Horizon_È();
                    final float var11 = (var10 % 4 + 0) / 4.0f;
                    final float var12 = (var10 / 4 + 0) / 4.0f;
                    final float var13 = (var10 % 4 + 1) / 4.0f;
                    final float var14 = (var10 / 4 + 1) / 4.0f;
                    var5.Â();
                    var5.HorizonCode_Horizon_È(-1.0, 1.0, var7 * 0.001f, var11, var12);
                    var5.HorizonCode_Horizon_È(1.0, 1.0, var7 * 0.001f, var13, var12);
                    var5.HorizonCode_Horizon_È(1.0, -1.0, var7 * 0.001f, var13, var14);
                    var5.HorizonCode_Horizon_È(-1.0, -1.0, var7 * 0.001f, var11, var14);
                    var4.Â();
                    GlStateManager.Ê();
                    ++var7;
                }
            }
            GlStateManager.Çªà¢();
            GlStateManager.Â(0.0f, 0.0f, -0.04f);
            GlStateManager.HorizonCode_Horizon_È(1.0f, 1.0f, 1.0f);
            GlStateManager.Ê();
        }
        
        HorizonCode_Horizon_È(final MapItemRenderer mapItemRenderer, final MapData p_i45008_2_, final Object p_i45008_3_) {
            this(mapItemRenderer, p_i45008_2_);
        }
    }
}
