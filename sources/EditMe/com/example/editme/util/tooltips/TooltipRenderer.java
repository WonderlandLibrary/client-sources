package com.example.editme.util.tooltips;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityItem;

public class TooltipRenderer {
   private static void renderTooltipText(Tooltip var0) {
      if ((var0.alpha & -67108864) != 0) {
         int var1 = -var0.getWidth() / 2;
         int var2 = -var0.getHeight() / 2;
         GlStateManager.func_179094_E();
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);

         for(int var3 = 0; var3 < var0.getText().size(); ++var3) {
            String var4 = (String)var0.getText().get(var3);
            if (var3 == 0) {
               var4 = String.valueOf((new StringBuilder()).append(var0.formattingColor()).append(var4));
            }

            float var10002 = (float)var1;
            float var10003 = (float)var2;
            int var10004 = 16777215 | var0.alpha;
            Minecraft.func_71410_x().field_71466_p.func_175065_a(var4, var10002, var10003, var10004, true);
            if (var3 == 0) {
               var2 += 2;
            }

            var2 += 10;
         }

         GlStateManager.func_179084_k();
         GlStateManager.func_179121_F();
      }
   }

   private static void renderBackgroundAndOutline(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      drawRect((double)(var0 - 3), (double)(var1 - 4), 0.0D, (double)(var2 + 6), 1.0D, var4);
      drawRect((double)(var0 + var2 + 3), (double)(var1 - 3), 0.0D, 1.0D, (double)(var3 + 6), var4);
      drawRect((double)(var0 - 3), (double)(var1 + var3 + 3), 0.0D, (double)(var2 + 6), 1.0D, var4);
      drawRect((double)(var0 - 4), (double)(var1 - 3), 0.0D, 1.0D, (double)(var3 + 6), var4);
      drawRect((double)(var0 - 2), (double)(var1 - 2), 0.0D, (double)(var2 + 4), (double)(var3 + 4), var4);
      drawRect((double)(var0 - 3), (double)(var1 - 3), 0.0D, (double)(var2 + 6), 1.0D, var5);
      drawGradientRect((double)(var0 + var2 + 2), (double)(var1 - 2), 0.0D, 1.0D, (double)(var3 + 4), var5, var6);
      drawRect((double)(var0 - 3), (double)(var1 + var3 + 2), 0.0D, (double)(var2 + 6), 1.0D, var6);
      drawGradientRect((double)(var0 - 3), (double)(var1 - 2), 0.0D, 1.0D, (double)(var3 + 4), var5, var6);
   }

   private static void renderBackgroundAndOutline(Tooltip var0) {
      int var1 = -var0.getWidth() / 2;
      int var2 = -var0.getHeight() / 2;
      int var3 = var0.getWidth();
      int var4 = var0.getHeight();
      int var5 = var0.colorBackground;
      int var6 = var0.colorOutline;
      int var7 = var0.colorOutlineShade;
      renderBackgroundAndOutline(var1, var2, var3, var4, var5, var6, var7);
   }

   public static void drawGradientRect(double var0, double var2, double var4, double var6, double var8, int var10) {
      int var11 = var10 >> 24 & 255;
      int var12 = (var10 & 16711422) >> 1 | var11;
      drawGradientRect(var0, var2, var4, var6, var8, var10, var12, var12, var10);
   }

   public static void drawGradientRect(double var0, double var2, double var4, double var6, double var8, int var10, int var11) {
      drawGradientRect(var0, var2, var4, var6, var8, var10, var11, var11, var10);
   }

   public static void renderTooltip(Tooltip var0, double var1) {
      RenderManager var3 = Minecraft.func_71410_x().func_175598_ae();
      EntityItem var4 = var0.getEntity();
      double var5 = var3.field_78730_l - (var4.field_70165_t - (var4.field_70169_q - var4.field_70165_t) * var1);
      double var7 = var3.field_78731_m - 0.65D - (var4.field_70163_u - (var4.field_70167_r - var4.field_70163_u) * var1);
      double var9 = var3.field_78728_n - (var4.field_70161_v - (var4.field_70166_s - var4.field_70161_v) * var1);
      GlStateManager.func_179094_E();
      GlStateManager.func_179123_a();
      GlStateManager.func_179091_B();
      GlStateManager.func_179141_d();
      GlStateManager.func_179092_a(516, 0.1F);
      GlStateManager.func_179097_i();
      GlStateManager.func_179137_b(-var5, -var7, -var9);
      GlStateManager.func_179114_b(var3.field_78735_i + 180.0F, 0.0F, -1.0F, 0.0F);
      GlStateManager.func_179114_b(var3.field_78732_j, -1.0F, 0.0F, 0.0F);
      GlStateManager.func_179139_a(var0.scale, -var0.scale, var0.scale);
      renderBackgroundAndOutline(var0);
      renderTooltipText(var0);
      GlStateManager.func_179139_a(1.0D / var0.scale, 1.0D / -var0.scale, 1.0D / var0.scale);
      GlStateManager.func_179114_b(var3.field_78732_j, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179114_b(var3.field_78735_i - 180.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179137_b(var5, var7, var9);
      GlStateManager.func_179126_j();
      GlStateManager.func_179118_c();
      GlStateManager.func_179101_C();
      GlStateManager.func_179140_f();
      GlStateManager.func_179099_b();
      GlStateManager.func_179121_F();
   }

   public static void drawRect(double var0, double var2, double var4, double var6, double var8, int var10) {
      drawGradientRect(var0, var2, var4, var6, var8, var10, var10, var10, var10);
   }

   public static void drawGradientRect(double var0, double var2, double var4, double var6, double var8, int var10, int var11, int var12, int var13) {
      int var14 = var10 >> 24 & 255;
      int var15 = var10 >> 16 & 255;
      int var16 = var10 >> 8 & 255;
      int var17 = var10 >> 0 & 255;
      int var18 = var11 >> 24 & 255;
      int var19 = var11 >> 16 & 255;
      int var20 = var11 >> 8 & 255;
      int var21 = var11 >> 0 & 255;
      int var22 = var12 >> 24 & 255;
      int var23 = var12 >> 16 & 255;
      int var24 = var12 >> 8 & 255;
      int var25 = var12 >> 0 & 255;
      int var26 = var13 >> 24 & 255;
      int var27 = var13 >> 16 & 255;
      int var28 = var13 >> 8 & 255;
      int var29 = var13 >> 0 & 255;
      GlStateManager.func_179090_x();
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_187428_a(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.func_179103_j(7425);
      Tessellator var30 = Tessellator.func_178181_a();
      BufferBuilder var31 = var30.func_178180_c();
      var31.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      var31.func_181662_b(var0 + 0.0D, var2 + 0.0D, var4).func_181669_b(var15, var16, var17, var14).func_181675_d();
      var31.func_181662_b(var0 + 0.0D, var2 + var8, var4).func_181669_b(var19, var20, var21, var18).func_181675_d();
      var31.func_181662_b(var0 + var6, var2 + var8, var4).func_181669_b(var23, var24, var25, var22).func_181675_d();
      var31.func_181662_b(var0 + var6, var2 + 0.0D, var4).func_181669_b(var27, var28, var29, var26).func_181675_d();
      var30.func_78381_a();
      GlStateManager.func_179103_j(7424);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
      GlStateManager.func_179098_w();
   }
}
