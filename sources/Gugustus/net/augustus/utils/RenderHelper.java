package net.augustus.utils;

import net.augustus.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

public class RenderHelper {
   public static void drawOutlinedBoundingBox(AxisAlignedBB var0) {
      Tessellator var1 = Tessellator.getInstance();
      WorldRenderer var2 = var1.getWorldRenderer();
      var2.begin(3, DefaultVertexFormats.POSITION);
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var1.draw();
      var2.begin(3, DefaultVertexFormats.POSITION);
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var1.draw();
      var2.begin(1, DefaultVertexFormats.POSITION);
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.maxZ).endVertex();
      var1.draw();
   }

   public static void drawBoundingBox(AxisAlignedBB var0) {
      Tessellator var1 = Tessellator.getInstance();
      WorldRenderer var2 = var1.getWorldRenderer();
      var2.begin(7, DefaultVertexFormats.POSITION);
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.maxZ).endVertex();
      var1.draw();
      var2.begin(7, DefaultVertexFormats.POSITION);
      var2.pos(var0.maxX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.maxZ).endVertex();
      var1.draw();
      var2.begin(7, DefaultVertexFormats.POSITION);
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.minZ).endVertex();
      var1.draw();
      var2.begin(7, DefaultVertexFormats.POSITION);
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.minZ).endVertex();
      var1.draw();
      var2.begin(7, DefaultVertexFormats.POSITION);
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.minZ).endVertex();
      var1.draw();
      var2.begin(7, DefaultVertexFormats.POSITION);
      var2.pos(var0.minX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.maxZ).endVertex();
      var1.draw();
   }

   public static void drawRect(float var0, float var1, float var2, float var3, int var4) {
      float var5 = (float)(var4 >> 24 & 0xFF) / 255.0F;
      float var6 = (float)(var4 >> 16 & 0xFF) / 255.0F;
      float var7 = (float)(var4 >> 8 & 0xFF) / 255.0F;
      float var8 = (float)(var4 & 0xFF) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glPushMatrix();
      GL11.glColor4f(var6, var7, var8, var5);
      GL11.glBegin(7);
      GL11.glVertex2d((double)var2, (double)var1);
      GL11.glVertex2d((double)var0, (double)var1);
      GL11.glVertex2d((double)var0, (double)var3);
      GL11.glVertex2d((double)var2, (double)var3);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   public static void drawRect2(float var0, float var1, float var2, float var3, int var4) {
      float var5 = (float)(var4 >> 24 & 0xFF) / 0.0F;
      float var6 = (float)(var4 >> 16 & 0xFF) / 255.0F;
      float var7 = (float)(var4 >> 8 & 0xFF) / 255.0F;
      float var8 = (float)(var4 & 0xFF) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glPushMatrix();
      GL11.glColor4f(var6, var7, var8, var5);
      GL11.glBegin(7);
      GL11.glVertex2d((double)var2, (double)var1);
      GL11.glVertex2d((double)var0, (double)var1);
      GL11.glVertex2d((double)var0, (double)var3);
      GL11.glVertex2d((double)var2, (double)var3);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   public static void drawOutlinedBlockESP(double var0, double var2, double var4, float var6, float var7, float var8, float var9, float var10) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glLineWidth(var10);
      GL11.glColor4f(var6, var7, var8, var9);
      drawOutlinedBoundingBox(new AxisAlignedBB(var0, var2, var4, var0 + 1.0, var2 + 1.0, var4 + 1.0));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawBlockESP(
      double var0, double var2, double var4, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13, float var14
   ) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(var6, var7, var8, var9);
      drawBoundingBox(new AxisAlignedBB(var0, var2, var4, var0 + 1.0, var2 + 1.0, var4 + 1.0));
      GL11.glLineWidth(var14);
      GL11.glColor4f(var10, var11, var12, var13);
      drawOutlinedBoundingBox(new AxisAlignedBB(var0, var2, var4, var0 + 1.0, var2 + 1.0, var4 + 1.0));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawSolidBlockESP(double var0, double var2, double var4, float var6, float var7, float var8, float var9) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(var6, var7, var8, var9);
      drawBoundingBox(new AxisAlignedBB(var0, var2, var4, var0 + 1.0, var2 + 1.0, var4 + 1.0));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawOutlinedEntityESP(
      double var0, double var2, double var4, double var6, double var8, float var10, float var11, float var12, float var13
   ) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(var10, var11, var12, var13);
      drawOutlinedBoundingBox(new AxisAlignedBB(var0 - var6, var2, var4 - var6, var0 + var6, var2 + var8, var4 + var6));
      GL11.glColor4f(var10, var11, var12, 0.2F);
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawSolidEntityESP(double var0, double var2, double var4, double var6, double var8, float var10, float var11, float var12, float var13) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(var10, var11, var12, var13);
      drawBoundingBox(new AxisAlignedBB(var0 - var6, var2, var4 - var6, var0 + var6, var2 + var8, var4 + var6));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawEntityESP(
      double var0,
      double var2,
      double var4,
      double var6,
      double var8,
      float var10,
      float var11,
      float var12,
      float var13,
      float var14,
      float var15,
      float var16,
      float var17,
      float var18
   ) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(var10, var11, var12, var13);
      drawBoundingBox(new AxisAlignedBB(var0 - var6, var2, var4 - var6, var0 + var6, var2 + var8, var4 + var6));
      GL11.glLineWidth(var18);
      GL11.glColor4f(var14, var15, var16, var17);
      drawOutlinedBoundingBox(new AxisAlignedBB(var0 - var6, var2, var4 - var6, var0 + var6, var2 + var8, var4 + var6));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void render2DESP(EntityPlayer var0, Module var1, boolean var2, int var3, int var4) {
      Minecraft var5 = Minecraft.getMinecraft();
      double var6 = var0.lastTickPosX + (var0.posX - var0.lastTickPosX) * (double)var5.timer.renderPartialTicks - var5.getRenderManager().renderPosX;
      double var8 = var0.lastTickPosY + (var0.posY - var0.lastTickPosY) * (double)var5.timer.renderPartialTicks - var5.getRenderManager().renderPosY;
      double var10 = var0.lastTickPosZ + (var0.posZ - var0.lastTickPosZ) * (double)var5.timer.renderPartialTicks - var5.getRenderManager().renderPosZ;
      GL11.glPushMatrix();
      GL11.glTranslated(var6, var8 - 0.2, var10);
      GL11.glScalef(0.03F, 0.03F, 0.03F);
      GL11.glRotated((double)(-var5.getRenderManager().playerViewY), 0.0, 1.0, 0.0);
      GlStateManager.disableDepth();
      drawRect(21.0F, -1.0F, 26.0F, 20.0F, var4);
      drawRect(6.0F, -1.0F, 25.0F, 3.0F, var4);
      drawRect(21.0F, 0.0F, 25.0F, 21.0F, var3);
      drawRect(5.0F, 0.0F, 25.0F, 3.0F, var3);
      drawRect(21.0F, 56.0F, 26.0F, 77.0F, var4);
      drawRect(6.0F, 73.0F, 25.0F, 77.0F, var4);
      drawRect(21.0F, 55.0F, 25.0F, 76.0F, var3);
      drawRect(5.0F, 73.0F, 25.0F, 76.0F, var3);
      drawRect(-21.0F, 77.0F, -22.0F, 56.0F, var4);
      drawRect(-22.0F, 73.0F, -2.0F, 77.0F, var4);
      drawRect(-21.0F, 55.0F, -17.0F, 76.0F, var3);
      drawRect(-21.0F, 73.0F, -1.0F, 76.0F, var3);
      drawRect(-22.0F, -1.0F, -19.0F, 20.0F, var4);
      drawRect(-22.0F, -1.0F, -2.0F, 3.0F, var4);
      drawRect(-21.0F, 0.0F, -17.0F, 21.0F, var3);
      drawRect(-21.0F, 0.0F, -1.0F, 3.0F, var3);
      GlStateManager.enableDepth();
      GL11.glPopMatrix();
   }

   public static void drawTracerLine(double var0, double var2, double var4, float var6, float var7, float var8, float var9, float var10) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(var10);
      GL11.glColor4f(var6, var7, var8, var9);
      GL11.glBegin(2);
      GL11.glVertex3d(0.0, 0.0 + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0);
      GL11.glVertex3d(var0, var2, var4);
      GL11.glEnd();
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawCircle(float var0, float var1, double var2, int var4, int var5) {
      float var6 = (float)(var5 >> 24 & 0xFF) / 255.0F;
      float var7 = (float)(var5 >> 16 & 0xFF) / 255.0F;
      float var8 = (float)(var5 >> 8 & 0xFF) / 255.0F;
      float var9 = (float)(var5 & 0xFF) / 255.0F;
      float var10 = (float)(var4 >> 24 & 0xFF) / 255.0F;
      float var11 = (float)(var4 >> 16 & 0xFF) / 255.0F;
      float var12 = (float)(var4 >> 8 & 0xFF) / 255.0F;
      float var13 = (float)(var4 & 0xFF) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(var11, var12, var13, var10);
      GL11.glBegin(2);

      for(int var14 = 0; var14 <= 360; ++var14) {
         double var15 = Math.sin((double)var14 * Math.PI / 180.0) * var2;
         double var17 = Math.cos((double)var14 * Math.PI / 180.0) * var2;
         GL11.glVertex2d((double)var0 + var15, (double)var1 + var17);
      }

      GL11.glEnd();
      GL11.glColor4f(var7, var8, var9, var6);
      GL11.glBegin(9);

      for(int var19 = 0; var19 <= 360; ++var19) {
         double var20 = Math.sin((double)var19 * Math.PI / 180.0) * var2;
         double var21 = Math.cos((double)var19 * Math.PI / 180.0) * var2;
         GL11.glVertex2d((double)var0 + var20, (double)var1 + var21);
      }

      GL11.glEnd();
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   public static void drawString(double var0, String var2, float var3, float var4, int var5) {
      GlStateManager.pushMatrix();
      GlStateManager.scale(var0, var0, var0);
      Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(var2, var3, var4, var5);
      GlStateManager.popMatrix();
   }

   public static void DrawFontWithShadow(double var0, String var2, int var3, int var4, int var5, int var6) {
      GlStateManager.pushMatrix();
      GlStateManager.scale(var0, var0, var0);
      Minecraft.getMinecraft().fontRendererObj.drawString(var2, var3 - 1, var4 - 1, var6);
      Minecraft.getMinecraft().fontRendererObj.drawString(var2, var3, var4, var5);
      GlStateManager.popMatrix();
   }

   public static void drawLine(Point var0, Point var1) {
      GL11.glColor3f(0.0F, 1.0F, 0.2F);
      GL11.glBegin(3);
      GL11.glVertex2d((double)var0.getX(), (double)var0.getY());
      GL11.glVertex2d((double)var1.getX(), (double)var1.getY());
      GL11.glEnd();
   }
}
