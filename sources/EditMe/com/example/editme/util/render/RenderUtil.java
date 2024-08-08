package com.example.editme.util.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public final class RenderUtil {
   private static final FloatBuffer MODELVIEW = GLAllocation.func_74529_h(16);
   private static final FloatBuffer PROJECTION = GLAllocation.func_74529_h(16);
   private static final IntBuffer VIEWPORT = GLAllocation.func_74527_f(16);

   public static void glBillboardDistanceScaled(float var0, float var1, float var2, EntityPlayer var3, float var4) {
      glBillboard(var0, var1, var2);
      int var5 = (int)var3.func_70011_f((double)var0, (double)var1, (double)var2);
      float var6 = (float)var5 / 2.0F / (2.0F + (2.0F - var4));
      if (var6 < 1.0F) {
         var6 = 1.0F;
      }

      GlStateManager.func_179152_a(var6, var6, var6);
   }

   public static void drawLine3D(float var0, float var1, float var2, float var3, float var4, float var5, float var6, int var7) {
      float var8 = (float)(var7 >> 16 & 255) / 255.0F;
      float var9 = (float)(var7 >> 8 & 255) / 255.0F;
      float var10 = (float)(var7 & 255) / 255.0F;
      float var11 = (float)(var7 >> 24 & 255) / 255.0F;
      GlStateManager.func_179094_E();
      GlStateManager.func_179090_x();
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179103_j(7425);
      GL11.glLineWidth(var6);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GlStateManager.func_179097_i();
      GL11.glEnable(34383);
      Tessellator var12 = Tessellator.func_178181_a();
      BufferBuilder var13 = var12.func_178180_c();
      var13.func_181668_a(1, DefaultVertexFormats.field_181706_f);
      var13.func_181662_b((double)var0, (double)var1, (double)var2).func_181666_a(var8, var9, var10, var11).func_181675_d();
      var13.func_181662_b((double)var3, (double)var4, (double)var5).func_181666_a(var8, var9, var10, var11).func_181675_d();
      var12.func_78381_a();
      GlStateManager.func_179103_j(7424);
      GL11.glDisable(2848);
      GlStateManager.func_179126_j();
      GL11.glDisable(34383);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
      GlStateManager.func_179098_w();
      GlStateManager.func_179121_F();
   }

   public static void drawRect(float var0, float var1, float var2, float var3, int var4) {
      float var5 = var0 + var2;
      float var6 = var1 + var3;
      float var7 = (float)(var4 >> 24 & 255) / 255.0F;
      float var8 = (float)(var4 >> 16 & 255) / 255.0F;
      float var9 = (float)(var4 >> 8 & 255) / 255.0F;
      float var10 = (float)(var4 & 255) / 255.0F;
      Tessellator var11 = Tessellator.func_178181_a();
      BufferBuilder var12 = var11.func_178180_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_187428_a(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.func_179131_c(var8, var9, var10, var7);
      var12.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      var12.func_181662_b((double)var0, (double)var6, 0.0D).func_181675_d();
      var12.func_181662_b((double)var5, (double)var6, 0.0D).func_181675_d();
      var12.func_181662_b((double)var5, (double)var1, 0.0D).func_181675_d();
      var12.func_181662_b((double)var0, (double)var1, 0.0D).func_181675_d();
      var11.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawTriangle(float var0, float var1, float var2, float var3, int var4) {
      GL11.glTranslated((double)var0, (double)var1, 0.0D);
      GL11.glRotatef(180.0F + var3, 0.0F, 0.0F, 1.0F);
      float var5 = (float)(var4 >> 24 & 255) / 255.0F;
      float var6 = (float)(var4 >> 16 & 255) / 255.0F;
      float var7 = (float)(var4 >> 8 & 255) / 255.0F;
      float var8 = (float)(var4 & 255) / 255.0F;
      GL11.glColor4f(var6, var7, var8, var5);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glBlendFunc(770, 771);
      GL11.glLineWidth(1.0F);
      GL11.glBegin(6);
      GL11.glVertex2d(0.0D, (double)(1.0F * var2));
      GL11.glVertex2d((double)(1.0F * var2), (double)(-(1.0F * var2)));
      GL11.glVertex2d((double)(-(1.0F * var2)), (double)(-(1.0F * var2)));
      GL11.glEnd();
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glRotatef(-180.0F - var3, 0.0F, 0.0F, 1.0F);
      GL11.glTranslated((double)(-var0), (double)(-var1), 0.0D);
   }

   public static void drawBoundingBox(AxisAlignedBB var0, float var1, float var2, float var3, float var4, float var5) {
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
      var7.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181666_a(var2, var3, var4, 0.0F).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181666_a(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181666_a(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181666_a(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181666_a(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181666_a(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181666_a(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181666_a(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181666_a(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181666_a(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181666_a(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181666_a(var2, var3, var4, 0.0F).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181666_a(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181666_a(var2, var3, var4, 0.0F).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181666_a(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181666_a(var2, var3, var4, 0.0F).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181666_a(var2, var3, var4, var5).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181666_a(var2, var3, var4, 0.0F).func_181675_d();
      var6.func_78381_a();
      GL11.glDisable(2848);
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179126_j();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179121_F();
   }

   public static void prepareScissorBox(ScaledResolution var0, float var1, float var2, float var3, float var4) {
      float var5 = var1 + var3;
      float var6 = var2 + var4;
      int var7 = var0.func_78325_e();
      GL11.glScissor((int)(var1 * (float)var7), (int)(((float)var0.func_78328_b() - var6) * (float)var7), (int)((var5 - var1) * (float)var7), (int)((var6 - var2) * (float)var7));
   }

   public static void drawGradientRect(float var0, float var1, float var2, float var3, int var4, int var5) {
      float var6 = (float)(var4 >> 24 & 255) / 255.0F;
      float var7 = (float)(var4 >> 16 & 255) / 255.0F;
      float var8 = (float)(var4 >> 8 & 255) / 255.0F;
      float var9 = (float)(var4 & 255) / 255.0F;
      float var10 = (float)(var5 >> 24 & 255) / 255.0F;
      float var11 = (float)(var5 >> 16 & 255) / 255.0F;
      float var12 = (float)(var5 >> 8 & 255) / 255.0F;
      float var13 = (float)(var5 & 255) / 255.0F;
      GlStateManager.func_179090_x();
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_187428_a(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.func_179103_j(7425);
      Tessellator var14 = Tessellator.func_178181_a();
      BufferBuilder var15 = var14.func_178180_c();
      var15.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      var15.func_181662_b((double)var2, (double)var1, 0.0D).func_181666_a(var7, var8, var9, var6).func_181675_d();
      var15.func_181662_b((double)var0, (double)var1, 0.0D).func_181666_a(var7, var8, var9, var6).func_181675_d();
      var15.func_181662_b((double)var0, (double)var3, 0.0D).func_181666_a(var11, var12, var13, var10).func_181675_d();
      var15.func_181662_b((double)var2, (double)var3, 0.0D).func_181666_a(var11, var12, var13, var10).func_181675_d();
      var14.func_78381_a();
      GlStateManager.func_179103_j(7424);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
      GlStateManager.func_179098_w();
   }

   public static void drawPlane(AxisAlignedBB var0, int var1) {
      float var2 = (float)(var1 >> 24 & 255) / 255.0F;
      float var3 = (float)(var1 >> 16 & 255) / 255.0F;
      float var4 = (float)(var1 >> 8 & 255) / 255.0F;
      float var5 = (float)(var1 & 255) / 255.0F;
      double var6 = var0.field_72340_a;
      double var8 = var0.field_72338_b;
      double var10 = var0.field_72339_c;
      double var12 = var0.field_72336_d;
      double var14 = var0.field_72337_e;
      double var16 = var0.field_72334_f;
      Tessellator var18 = Tessellator.func_178181_a();
      BufferBuilder var19 = var18.func_178180_c();
      var19.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      var19.func_181662_b(var6, var8, var10).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var19.func_181662_b(var12, var8, var16).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var19.func_181662_b(var6, var8, var16).func_181666_a(var3, var4, var5, 0.0F).func_181675_d();
      var19.func_181662_b(var16, var8, var10).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var18.func_78381_a();
   }

   public static void drawOutlineRect(float var0, float var1, float var2, float var3, float var4, int var5) {
      drawRect(var0, var1, var0 - var4, var3, var5);
      drawRect(var2 + var4, var1, var2, var3, var5);
      drawRect(var0, var1, var2, var1 - var4, var5);
      drawRect(var0, var3 + var4, var2, var3, var5);
   }

   public static void glBillboard(float var0, float var1, float var2) {
      float var3 = 0.02666667F;
      GlStateManager.func_179137_b((double)var0 - Minecraft.func_71410_x().func_175598_ae().field_78725_b, (double)var1 - Minecraft.func_71410_x().func_175598_ae().field_78726_c, (double)var2 - Minecraft.func_71410_x().func_175598_ae().field_78723_d);
      GlStateManager.func_187432_a(0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(-Minecraft.func_71410_x().field_71439_g.field_70177_z, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(Minecraft.func_71410_x().field_71439_g.field_70125_A, Minecraft.func_71410_x().field_71474_y.field_74320_O == 2 ? -1.0F : 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179152_a(-var3, -var3, var3);
   }

   public static void glScissor(float var0, float var1, float var2, float var3, ScaledResolution var4) {
      GL11.glScissor((int)(var0 * (float)var4.func_78325_e()), (int)((float)Minecraft.func_71410_x().field_71440_d - var3 * (float)var4.func_78325_e()), (int)((var2 - var0) * (float)var4.func_78325_e()), (int)((var3 - var1) * (float)var4.func_78325_e()));
   }

   public static void drawLine(float var0, float var1, float var2, float var3, float var4, int var5) {
      float var6 = (float)(var5 >> 16 & 255) / 255.0F;
      float var7 = (float)(var5 >> 8 & 255) / 255.0F;
      float var8 = (float)(var5 & 255) / 255.0F;
      float var9 = (float)(var5 >> 24 & 255) / 255.0F;
      GlStateManager.func_179094_E();
      GlStateManager.func_179090_x();
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_179120_a(770, 771, 1, 0);
      GlStateManager.func_179103_j(7425);
      GL11.glLineWidth(var4);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      Tessellator var10 = Tessellator.func_178181_a();
      BufferBuilder var11 = var10.func_178180_c();
      var11.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      var11.func_181662_b((double)var0, (double)var1, 0.0D).func_181666_a(var6, var7, var8, var9).func_181675_d();
      var11.func_181662_b((double)var2, (double)var3, 0.0D).func_181666_a(var6, var7, var8, var9).func_181675_d();
      var10.func_78381_a();
      GlStateManager.func_179103_j(7424);
      GL11.glDisable(2848);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
      GlStateManager.func_179098_w();
      GlStateManager.func_179121_F();
   }

   public static void drawTexture(float var0, float var1, float var2, float var3, float var4, float var5) {
      float var6 = 0.00390625F;
      float var7 = 0.00390625F;
      Tessellator var8 = Tessellator.func_178181_a();
      BufferBuilder var9 = var8.func_178180_c();
      var9.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      var9.func_181662_b((double)var0, (double)(var1 + var5), 0.0D).func_187315_a((double)(var2 * var6), (double)((var3 + var5) * var7)).func_181675_d();
      var9.func_181662_b((double)(var0 + var4), (double)(var1 + var5), 0.0D).func_187315_a((double)((var2 + var4) * var6), (double)((var3 + var5) * var7)).func_181675_d();
      var9.func_181662_b((double)(var0 + var4), (double)var1, 0.0D).func_187315_a((double)((var2 + var4) * var6), (double)(var3 * var7)).func_181675_d();
      var9.func_181662_b((double)var0, (double)var1, 0.0D).func_187315_a((double)(var2 * var6), (double)(var3 * var7)).func_181675_d();
      var8.func_78381_a();
   }

   public static void updateModelViewProjectionMatrix() {
      GL11.glGetFloat(2982, MODELVIEW);
      GL11.glGetFloat(2983, PROJECTION);
      GL11.glGetInteger(2978, VIEWPORT);
      ScaledResolution var0 = new ScaledResolution(Minecraft.func_71410_x());
      GLUProjectionUtil.getInstance().updateMatrices(VIEWPORT, MODELVIEW, PROJECTION, (double)((float)var0.func_78326_a() / (float)Minecraft.func_71410_x().field_71443_c), (double)((float)var0.func_78328_b() / (float)Minecraft.func_71410_x().field_71440_d));
   }

   public static void drawBoundingBox(AxisAlignedBB var0, float var1, int var2) {
      float var3 = (float)(var2 >> 24 & 255) / 255.0F;
      float var4 = (float)(var2 >> 16 & 255) / 255.0F;
      float var5 = (float)(var2 >> 8 & 255) / 255.0F;
      float var6 = (float)(var2 & 255) / 255.0F;
      drawBoundingBox(var0, var1, var4, var5, var6, var3);
   }

   public static void drawTexture(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      Tessellator var8 = Tessellator.func_178181_a();
      BufferBuilder var9 = var8.func_178180_c();
      var9.func_181668_a(4, DefaultVertexFormats.field_181707_g);
      var9.func_181662_b((double)(var0 + var2), (double)var1, 0.0D).func_187315_a((double)var6, (double)var5).func_181675_d();
      var9.func_181662_b((double)var0, (double)var1, 0.0D).func_187315_a((double)var4, (double)var5).func_181675_d();
      var9.func_181662_b((double)var0, (double)(var1 + var3), 0.0D).func_187315_a((double)var4, (double)var7).func_181675_d();
      var9.func_181662_b((double)var0, (double)(var1 + var3), 0.0D).func_187315_a((double)var4, (double)var7).func_181675_d();
      var9.func_181662_b((double)(var0 + var2), (double)(var1 + var3), 0.0D).func_187315_a((double)var6, (double)var7).func_181675_d();
      var9.func_181662_b((double)(var0 + var2), (double)var1, 0.0D).func_187315_a((double)var6, (double)var5).func_181675_d();
      var8.func_78381_a();
   }

   public static void drawFilledBox(AxisAlignedBB var0, int var1) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179147_l();
      GlStateManager.func_179097_i();
      GlStateManager.func_179120_a(770, 771, 0, 1);
      GlStateManager.func_179090_x();
      GlStateManager.func_179132_a(false);
      float var2 = (float)(var1 >> 24 & 255) / 255.0F;
      float var3 = (float)(var1 >> 16 & 255) / 255.0F;
      float var4 = (float)(var1 >> 8 & 255) / 255.0F;
      float var5 = (float)(var1 & 255) / 255.0F;
      Tessellator var6 = Tessellator.func_178181_a();
      BufferBuilder var7 = var6.func_178180_c();
      var7.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      var7.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var7.func_181662_b(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c).func_181666_a(var3, var4, var5, var2).func_181675_d();
      var6.func_78381_a();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179126_j();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179121_F();
   }

   public static void drawPlane(AxisAlignedBB var0, float var1, int var2) {
      GlStateManager.func_179094_E();
      GlStateManager.func_187441_d(var1);
      GlStateManager.func_179147_l();
      GlStateManager.func_179097_i();
      GlStateManager.func_187428_a(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ONE);
      GlStateManager.func_179090_x();
      GlStateManager.func_179132_a(false);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      drawPlane(var0, var2);
      GL11.glDisable(2848);
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179126_j();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179121_F();
   }

   public static void drawPlane(double var0, double var2, double var4, AxisAlignedBB var6, float var7, int var8) {
      GL11.glPushMatrix();
      GL11.glTranslated(var0, var2, var4);
      drawPlane(var6, var7, var8);
      GL11.glPopMatrix();
   }
}
