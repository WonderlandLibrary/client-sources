package com.example.editme.util.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class EditmeTessellator extends Tessellator {
   public static EditmeTessellator INSTANCE = new EditmeTessellator();

   public static void drawBox(BlockPos var0, int var1, int var2) {
      int var3 = var1 >>> 24 & 255;
      int var4 = var1 >>> 16 & 255;
      int var5 = var1 >>> 8 & 255;
      int var6 = var1 & 255;
      drawBox(var0, var4, var5, var6, var3, var2);
   }

   public static void drawBoundingBox(AxisAlignedBB var0, float var1, int var2, int var3, int var4, int var5) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179147_l();
      GlStateManager.func_179097_i();
      GlStateManager.func_179120_a(770, 771, 0, 1);
      GlStateManager.func_179090_x();
      GlStateManager.func_179132_a(false);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glLineWidth(var1);
      Tessellator var6 = Tessellator.func_178181_a();
      BufferBuilder var7 = var6.func_178180_c();
      var7.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      var7.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var6.func_78381_a();
      var7.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      var7.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var6.func_78381_a();
      var7.func_181668_a(1, DefaultVertexFormats.field_181706_f);
      var7.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var6.func_78381_a();
      GL11.glDisable(2848);
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179126_j();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179121_F();
   }

   public static void prepareGL() {
      GL11.glBlendFunc(770, 771);
      GlStateManager.func_187428_a(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.func_187441_d(1.5F);
      GlStateManager.func_179090_x();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179147_l();
      GlStateManager.func_179097_i();
      GlStateManager.func_179140_f();
      GlStateManager.func_179129_p();
      GlStateManager.func_179141_d();
      GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
   }

   public static void begin(int var0) {
      INSTANCE.func_178180_c().func_181668_a(var0, DefaultVertexFormats.field_181706_f);
   }

   public static BufferBuilder getBufferBuilder() {
      return INSTANCE.func_178180_c();
   }

   public static void releaseGL() {
      GlStateManager.func_179089_o();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179098_w();
      GlStateManager.func_179147_l();
      GlStateManager.func_179126_j();
   }

   public static void render() {
      INSTANCE.func_78381_a();
   }

   public static void drawBox(BufferBuilder var0, float var1, float var2, float var3, float var4, float var5, float var6, int var7, int var8, int var9, int var10, int var11) {
      if ((var11 & 1) != 0) {
         var0.func_181662_b((double)(var1 + var4), (double)var2, (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)(var1 + var4), (double)var2, (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)var1, (double)var2, (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)var1, (double)var2, (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
      }

      if ((var11 & 2) != 0) {
         var0.func_181662_b((double)(var1 + var4), (double)(var2 + var5), (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)var1, (double)(var2 + var5), (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)var1, (double)(var2 + var5), (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)(var1 + var4), (double)(var2 + var5), (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
      }

      if ((var11 & 4) != 0) {
         var0.func_181662_b((double)(var1 + var4), (double)var2, (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)var1, (double)var2, (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)var1, (double)(var2 + var5), (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)(var1 + var4), (double)(var2 + var5), (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
      }

      if ((var11 & 8) != 0) {
         var0.func_181662_b((double)var1, (double)var2, (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)(var1 + var4), (double)var2, (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)(var1 + var4), (double)(var2 + var5), (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)var1, (double)(var2 + var5), (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
      }

      if ((var11 & 16) != 0) {
         var0.func_181662_b((double)var1, (double)var2, (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)var1, (double)var2, (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)var1, (double)(var2 + var5), (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)var1, (double)(var2 + var5), (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
      }

      if ((var11 & 32) != 0) {
         var0.func_181662_b((double)(var1 + var4), (double)var2, (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)(var1 + var4), (double)var2, (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)(var1 + var4), (double)(var2 + var5), (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)(var1 + var4), (double)(var2 + var5), (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
      }

   }

   public static void release() {
      render();
      releaseGL();
   }

   public static void drawBox(BlockPos var0, int var1, int var2, int var3, int var4, int var5) {
      drawBox(INSTANCE.func_178180_c(), (float)var0.field_177962_a, (float)var0.field_177960_b, (float)var0.field_177961_c, 1.0F, 1.0F, 1.0F, var1, var2, var3, var4, var5);
   }

   public static void drawRectangle(float var0, float var1, float var2, float var3, int var4) {
      float var5 = (float)(var4 >> 16 & 255) / 255.0F;
      float var6 = (float)(var4 >> 8 & 255) / 255.0F;
      float var7 = (float)(var4 & 255) / 255.0F;
      float var8 = (float)(var4 >> 24 & 255) / 255.0F;
      Tessellator var9 = Tessellator.func_178181_a();
      BufferBuilder var10 = var9.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      var10.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      var10.func_181662_b((double)var0, (double)var3, 0.0D).func_181666_a(var5, var6, var7, var8).func_181675_d();
      var10.func_181662_b((double)var2, (double)var3, 0.0D).func_181666_a(var5, var6, var7, var8).func_181675_d();
      var10.func_181662_b((double)var2, (double)var1, 0.0D).func_181666_a(var5, var6, var7, var8).func_181675_d();
      var10.func_181662_b((double)var0, (double)var1, 0.0D).func_181666_a(var5, var6, var7, var8).func_181675_d();
      var9.func_78381_a();
      GlStateManager.func_179084_k();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawLines(BufferBuilder var0, float var1, float var2, float var3, float var4, float var5, float var6, int var7, int var8, int var9, int var10, int var11) {
      if ((var11 & 17) != 0) {
         var0.func_181662_b((double)var1, (double)var2, (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)var1, (double)var2, (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
      }

      if ((var11 & 18) != 0) {
         var0.func_181662_b((double)var1, (double)(var2 + var5), (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)var1, (double)(var2 + var5), (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
      }

      if ((var11 & 33) != 0) {
         var0.func_181662_b((double)(var1 + var4), (double)var2, (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)(var1 + var4), (double)var2, (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
      }

      if ((var11 & 34) != 0) {
         var0.func_181662_b((double)(var1 + var4), (double)(var2 + var5), (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)(var1 + var4), (double)(var2 + var5), (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
      }

      if ((var11 & 5) != 0) {
         var0.func_181662_b((double)var1, (double)var2, (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)(var1 + var4), (double)var2, (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
      }

      if ((var11 & 6) != 0) {
         var0.func_181662_b((double)var1, (double)(var2 + var5), (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)(var1 + var4), (double)(var2 + var5), (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
      }

      if ((var11 & 9) != 0) {
         var0.func_181662_b((double)var1, (double)var2, (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)(var1 + var4), (double)var2, (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
      }

      if ((var11 & 10) != 0) {
         var0.func_181662_b((double)var1, (double)(var2 + var5), (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)(var1 + var4), (double)(var2 + var5), (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
      }

      if ((var11 & 20) != 0) {
         var0.func_181662_b((double)var1, (double)var2, (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)var1, (double)(var2 + var5), (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
      }

      if ((var11 & 36) != 0) {
         var0.func_181662_b((double)(var1 + var4), (double)var2, (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)(var1 + var4), (double)(var2 + var5), (double)var3).func_181669_b(var7, var8, var9, var10).func_181675_d();
      }

      if ((var11 & 24) != 0) {
         var0.func_181662_b((double)var1, (double)var2, (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)var1, (double)(var2 + var5), (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
      }

      if ((var11 & 40) != 0) {
         var0.func_181662_b((double)(var1 + var4), (double)var2, (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
         var0.func_181662_b((double)(var1 + var4), (double)(var2 + var5), (double)(var3 + var6)).func_181669_b(var7, var8, var9, var10).func_181675_d();
      }

   }

   public EditmeTessellator() {
      super(2097152);
   }

   public static void drawBoundingBoxBlockPos(BlockPos var0, float var1, int var2, int var3, int var4, int var5) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179147_l();
      GlStateManager.func_179097_i();
      GlStateManager.func_179120_a(770, 771, 0, 1);
      GlStateManager.func_179090_x();
      GlStateManager.func_179132_a(false);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glLineWidth(var1);
      Minecraft var6 = Minecraft.func_71410_x();
      double var7 = (double)var0.field_177962_a - var6.func_175598_ae().field_78730_l;
      double var9 = (double)var0.field_177960_b - var6.func_175598_ae().field_78731_m;
      double var11 = (double)var0.field_177961_c - var6.func_175598_ae().field_78728_n;
      AxisAlignedBB var13 = new AxisAlignedBB(var7, var9, var11, var7 + 1.0D, var9 + 1.0D, var11 + 1.0D);
      Tessellator var14 = Tessellator.func_178181_a();
      BufferBuilder var15 = var14.func_178180_c();
      var15.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      var15.func_181662_b(var13.field_72340_a, var13.field_72338_b, var13.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var15.func_181662_b(var13.field_72336_d, var13.field_72338_b, var13.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var15.func_181662_b(var13.field_72336_d, var13.field_72338_b, var13.field_72334_f).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var15.func_181662_b(var13.field_72340_a, var13.field_72338_b, var13.field_72334_f).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var15.func_181662_b(var13.field_72340_a, var13.field_72338_b, var13.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var14.func_78381_a();
      var15.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      var15.func_181662_b(var13.field_72340_a, var13.field_72337_e, var13.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var15.func_181662_b(var13.field_72336_d, var13.field_72337_e, var13.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var15.func_181662_b(var13.field_72336_d, var13.field_72337_e, var13.field_72334_f).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var15.func_181662_b(var13.field_72340_a, var13.field_72337_e, var13.field_72334_f).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var15.func_181662_b(var13.field_72340_a, var13.field_72337_e, var13.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var14.func_78381_a();
      var15.func_181668_a(1, DefaultVertexFormats.field_181706_f);
      var15.func_181662_b(var13.field_72340_a, var13.field_72338_b, var13.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var15.func_181662_b(var13.field_72340_a, var13.field_72337_e, var13.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var15.func_181662_b(var13.field_72336_d, var13.field_72338_b, var13.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var15.func_181662_b(var13.field_72336_d, var13.field_72337_e, var13.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var15.func_181662_b(var13.field_72336_d, var13.field_72338_b, var13.field_72334_f).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var15.func_181662_b(var13.field_72336_d, var13.field_72337_e, var13.field_72334_f).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var15.func_181662_b(var13.field_72340_a, var13.field_72338_b, var13.field_72334_f).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var15.func_181662_b(var13.field_72340_a, var13.field_72337_e, var13.field_72334_f).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var14.func_78381_a();
      GL11.glDisable(2848);
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179126_j();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179121_F();
   }

   public static void drawBox(float var0, float var1, float var2, int var3, int var4) {
      int var5 = var3 >>> 24 & 255;
      int var6 = var3 >>> 16 & 255;
      int var7 = var3 >>> 8 & 255;
      int var8 = var3 & 255;
      drawBox(INSTANCE.func_178180_c(), var0, var1, var2, 1.0F, 1.0F, 1.0F, var6, var7, var8, var5, var4);
   }

   public static void drawBoundingBoxBottomBlockPos(BlockPos var0, float var1, int var2, int var3, int var4, int var5) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179147_l();
      GlStateManager.func_179097_i();
      GlStateManager.func_179120_a(770, 771, 0, 1);
      GlStateManager.func_179090_x();
      GlStateManager.func_179132_a(false);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glLineWidth(var1);
      Minecraft var6 = Minecraft.func_71410_x();
      double var7 = (double)var0.field_177962_a - var6.func_175598_ae().field_78730_l;
      double var9 = (double)var0.field_177960_b - var6.func_175598_ae().field_78731_m;
      double var11 = (double)var0.field_177961_c - var6.func_175598_ae().field_78728_n;
      AxisAlignedBB var13 = new AxisAlignedBB(var7, var9, var11, var7 + 1.0D, var9 + 1.0D, var11 + 1.0D);
      Tessellator var14 = Tessellator.func_178181_a();
      BufferBuilder var15 = var14.func_178180_c();
      var15.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      var15.func_181662_b(var13.field_72340_a, var13.field_72338_b, var13.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var15.func_181662_b(var13.field_72336_d, var13.field_72338_b, var13.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var15.func_181662_b(var13.field_72336_d, var13.field_72338_b, var13.field_72334_f).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var15.func_181662_b(var13.field_72340_a, var13.field_72338_b, var13.field_72334_f).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var15.func_181662_b(var13.field_72340_a, var13.field_72338_b, var13.field_72339_c).func_181669_b(var2, var3, var4, var5).func_181675_d();
      var14.func_78381_a();
      GL11.glDisable(2848);
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179126_j();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179121_F();
   }

   public static void prepare(int var0) {
      prepareGL();
      begin(var0);
   }
}
