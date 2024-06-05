package net;

import cc.novoline.utils.RenderUtils;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.vecmath.Vector3d;
import net.Ls;
import net.aIj;
import net.aSv;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class o0 {
   private final IntBuffer c = GLAllocation.createDirectIntBuffer(16);
   private final FloatBuffer d;
   private final FloatBuffer b;
   private final FloatBuffer a;

   public o0() {
      aIj.c();
      this.d = GLAllocation.createDirectFloatBuffer(16);
      this.b = GLAllocation.createDirectFloatBuffer(16);
      this.a = GLAllocation.createDirectFloatBuffer(4);
   }

   @aSv
   public void drawRainbowBox(double var1, double var3, double var5, double var7, float var9, boolean var10, boolean var11, boolean var12) {
      RenderUtils.drawRainbowBox(var1, var3, var5, var7, var9, var10, var11, var12);
   }

   @aSv
   public void drawBlockBox(double var1, double var3, double var5, int var7) {
      RenderUtils.a(var1, var3, var5, var7);
   }

   @aSv
   public void drawBlockBox(BlockPos var1, int var2) {
      RenderUtils.drawSolidBlockESP(var1, var2);
   }

   @aSv
   public void drawEntityOnScreen(int var1, int var2, float var3, EntityLivingBase var4) {
      RenderUtils.a(var1, var2, var3, var4);
   }

   @aSv
   public void drawBorderedRectangle(float var1, float var2, float var3, float var4, float var5, int var6, int var7) {
      RenderUtils.drawBorderedRect(var1, var2, var3, var4, var5, var6, var7);
   }

   @aSv
   public void drawRoundedRectangle(float var1, float var2, float var3, float var4, float var5, int var6) {
      RenderUtils.drawRoundedRect(var1, var2, var3, var4, var5, var6);
   }

   @aSv
   public void drawFilledRectangle(double var1, double var3, double var5, double var7, int var9) {
      Gui.drawRect(var1, var3, var5, var7, var9);
   }

   @aSv
   public void drawCircle(float var1, float var2, float var3, int var4) {
      RenderUtils.drawCircle(var1, var2, var3, 360.0F, var4);
   }

   @aSv
   public double getRenderPosX() {
      return Minecraft.getInstance().getRenderManager().renderPosX;
   }

   @aSv
   public double getRenderPosY() {
      return Minecraft.getInstance().getRenderManager().renderPosY;
   }

   @aSv
   public double getRenderPosZ() {
      return Minecraft.getInstance().getRenderManager().renderPosZ;
   }

   @aSv
   private Vector3d worldToScreen(ScaledResolution var1, double var2, double var4, double var6) {
      GL11.glGetFloat(2982, this.d);
      GL11.glGetFloat(2983, this.b);
      GL11.glGetInteger(2978, this.c);
      return Ls.a((float)var2, (float)var4, (float)var6, this.d, this.b, this.c, this.a)?new Vector3d((double)(this.a.get(0) / (float)var1.getScaleFactor()), (double)(((float)Display.getHeight() - this.a.get(1)) / (float)var1.getScaleFactor()), (double)this.a.get(2)):null;
   }

   @aSv
   public void renderString(String var1, float var2, float var3, int var4, boolean var5) {
      Minecraft.getInstance().fontRendererObj.drawString(var1, var2, var3, var4, var5);
   }
}
