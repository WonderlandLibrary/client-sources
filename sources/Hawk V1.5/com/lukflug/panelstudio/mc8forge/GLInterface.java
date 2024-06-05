package com.lukflug.panelstudio.mc8forge;

import com.lukflug.panelstudio.Interface;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Stack;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public abstract class GLInterface implements Interface {
   protected boolean clipX;
   private static final IntBuffer VIEWPORT = GLAllocation.createDirectIntBuffer(16);
   private Stack<Rectangle> clipRect = new Stack();
   private static final FloatBuffer PROJECTION = GLAllocation.createDirectFloatBuffer(16);
   private static final FloatBuffer MODELVIEW = GLAllocation.createDirectFloatBuffer(16);
   private static final FloatBuffer COORDS = GLAllocation.createDirectFloatBuffer(3);

   public GLInterface(boolean var1) {
      this.clipX = var1;
   }

   public static void begin() {
      GlStateManager.enableBlend();
      GL11.glDisable(3553);
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.shadeModel(7425);
      GL11.glLineWidth(2.0F);
   }

   protected void scissor(Rectangle var1) {
      if (var1 == null) {
         GL11.glScissor(0, 0, 0, 0);
         GL11.glEnable(3089);
      } else {
         GLU.gluProject((float)var1.x, (float)var1.y, this.getZLevel(), MODELVIEW, PROJECTION, VIEWPORT, COORDS);
         float var2 = COORDS.get(0);
         float var3 = COORDS.get(1);
         GLU.gluProject((float)(var1.x + var1.width), (float)(var1.y + var1.height), this.getZLevel(), MODELVIEW, PROJECTION, VIEWPORT, COORDS);
         float var4 = COORDS.get(0);
         float var5 = COORDS.get(1);
         if (!this.clipX) {
            var2 = (float)VIEWPORT.get(0);
            var4 = var2 + (float)VIEWPORT.get(2);
         }

         GL11.glScissor(Math.round(Math.min(var2, var4)), Math.round(Math.min(var3, var5)), Math.round(Math.abs(var4 - var2)), Math.round(Math.abs(var5 - var3)));
         GL11.glEnable(3089);
      }
   }

   public static void end() {
      GlStateManager.shadeModel(7424);
      GL11.glEnable(3553);
      GlStateManager.disableBlend();
   }

   public void getMatrices() {
      GlStateManager.getFloat(2982, MODELVIEW);
      GlStateManager.getFloat(2983, PROJECTION);
      GL11.glGetInteger(2978, VIEWPORT);
   }

   protected abstract String getResourcePrefix();

   protected abstract float getZLevel();

   public void drawRect(Rectangle var1, Color var2, Color var3, Color var4, Color var5) {
      Tessellator var6 = Tessellator.getInstance();
      WorldRenderer var7 = var6.getWorldRenderer();
      var7.startDrawing(2);
      var7.func_178960_a((float)var5.getRed() / 255.0F, (float)var5.getGreen() / 255.0F, (float)var5.getBlue() / 255.0F, (float)var5.getAlpha() / 255.0F);
      var7.addVertex((double)var1.x, (double)(var1.y + var1.height), (double)this.getZLevel());
      var7.func_178960_a((float)var4.getRed() / 255.0F, (float)var4.getGreen() / 255.0F, (float)var4.getBlue() / 255.0F, (float)var4.getAlpha() / 255.0F);
      var7.addVertex((double)(var1.x + var1.width), (double)(var1.y + var1.height), (double)this.getZLevel());
      var7.func_178960_a((float)var3.getRed() / 255.0F, (float)var3.getGreen() / 255.0F, (float)var3.getBlue() / 255.0F, (float)var3.getAlpha() / 255.0F);
      var7.addVertex((double)(var1.x + var1.width), (double)var1.y, (double)this.getZLevel());
      var7.func_178960_a((float)var2.getRed() / 255.0F, (float)var2.getGreen() / 255.0F, (float)var2.getBlue() / 255.0F, (float)var2.getAlpha() / 255.0F);
      var7.addVertex((double)var1.x, (double)var1.y, (double)this.getZLevel());
      var6.draw();
   }

   public void drawImage(Rectangle var1, int var2, boolean var3, int var4) {
      if (var4 != 0) {
         int[][] var5 = new int[][]{{0, 1}, {1, 1}, {1, 0}, new int[2]};

         int var6;
         int var7;
         for(var6 = 0; var6 < var2 % 4; ++var6) {
            var7 = var5[3][0];
            int var8 = var5[3][1];
            var5[3][0] = var5[2][0];
            var5[3][1] = var5[2][1];
            var5[2][0] = var5[1][0];
            var5[2][1] = var5[1][1];
            var5[1][0] = var5[0][0];
            var5[1][1] = var5[0][1];
            var5[0][0] = var7;
            var5[0][1] = var8;
         }

         if (var3) {
            var6 = var5[3][0];
            var7 = var5[3][1];
            var5[3][0] = var5[0][0];
            var5[3][1] = var5[0][1];
            var5[0][0] = var6;
            var5[0][1] = var7;
            var6 = var5[2][0];
            var7 = var5[2][1];
            var5[2][0] = var5[1][0];
            var5[2][1] = var5[1][1];
            var5[1][0] = var6;
            var5[1][1] = var7;
         }

         Tessellator var9 = Tessellator.getInstance();
         WorldRenderer var10 = var9.getWorldRenderer();
         GL11.glBindTexture(3553, var4);
         GL11.glEnable(3553);
         var10.startDrawingQuads();
         var10.addVertex((double)var1.x, (double)(var1.y + var1.height), (double)this.getZLevel());
         var10.setTextureUV((double)var5[0][0], (double)var5[0][1]);
         var10.addVertexWithUV((double)(var1.x + var1.width), (double)(var1.y + var1.height), (double)this.getZLevel(), (double)var5[1][0], (double)var5[1][1]);
         var10.addVertexWithUV((double)(var1.x + var1.width), (double)var1.y, (double)this.getZLevel(), (double)var5[2][0], (double)var5[2][1]);
         var10.addVertexWithUV((double)var1.x, (double)var1.y, (double)this.getZLevel(), (double)var5[3][0], (double)var5[3][1]);
         var9.draw();
         GL11.glDisable(3553);
      }
   }

   public void window(Rectangle var1) {
      if (this.clipRect.isEmpty()) {
         this.scissor(var1);
         this.clipRect.push(var1);
      } else {
         Rectangle var2 = (Rectangle)this.clipRect.peek();
         if (var2 == null) {
            this.scissor((Rectangle)null);
            this.clipRect.push((Object)null);
         } else {
            int var3 = Math.max(var1.x, var2.x);
            int var4 = Math.max(var1.y, var2.y);
            int var5 = Math.min(var1.x + var1.width, var2.x + var2.width);
            int var6 = Math.min(var1.y + var1.height, var2.y + var2.height);
            if (var5 > var3 && var6 > var4) {
               Rectangle var7 = new Rectangle(var3, var4, var5 - var3, var6 - var4);
               this.scissor(var7);
               this.clipRect.push(var7);
            } else {
               this.scissor((Rectangle)null);
               this.clipRect.push((Object)null);
            }
         }
      }

   }

   public void restore() {
      if (!this.clipRect.isEmpty()) {
         this.clipRect.pop();
         if (this.clipRect.isEmpty()) {
            GL11.glDisable(3089);
         } else {
            this.scissor((Rectangle)this.clipRect.peek());
         }
      }

   }

   public void fillTriangle(Point var1, Point var2, Point var3, Color var4, Color var5, Color var6) {
      Tessellator var7 = Tessellator.getInstance();
      WorldRenderer var8 = var7.getWorldRenderer();
      var8.startDrawing(4);
      var8.func_178960_a((float)var4.getRed() / 255.0F, (float)var4.getGreen() / 255.0F, (float)var4.getBlue() / 255.0F, (float)var4.getAlpha() / 255.0F);
      var8.addVertex((double)var1.x, (double)var1.y, (double)this.getZLevel());
      var8.func_178960_a((float)var5.getRed() / 255.0F, (float)var5.getGreen() / 255.0F, (float)var5.getBlue() / 255.0F, (float)var5.getAlpha() / 255.0F);
      var8.addVertex((double)var2.x, (double)var2.y, (double)this.getZLevel());
      var8.func_178960_a((float)var6.getRed() / 255.0F, (float)var6.getGreen() / 255.0F, (float)var6.getBlue() / 255.0F, (float)var6.getAlpha() / 255.0F);
      var8.addVertex((double)var3.x, (double)var3.y, (double)this.getZLevel());
      var7.draw();
   }

   public void drawLine(Point var1, Point var2, Color var3, Color var4) {
      Tessellator var5 = Tessellator.getInstance();
      WorldRenderer var6 = var5.getWorldRenderer();
      var6.startDrawing(1);
      var6.func_178960_a((float)var3.getRed() / 255.0F, (float)var3.getGreen() / 255.0F, (float)var3.getBlue() / 255.0F, (float)var3.getAlpha() / 255.0F);
      var6.addVertex((double)var1.x, (double)var1.y, (double)this.getZLevel());
      var6.func_178960_a((float)var4.getRed() / 255.0F, (float)var4.getGreen() / 255.0F, (float)var4.getBlue() / 255.0F, (float)var4.getAlpha() / 255.0F);
      var6.addVertex((double)var2.x, (double)var2.y, (double)this.getZLevel());
      var5.draw();
   }

   public synchronized int loadImage(String var1) {
      try {
         ResourceLocation var2 = new ResourceLocation(String.valueOf((new StringBuilder(String.valueOf(this.getResourcePrefix()))).append(var1)));
         InputStream var3 = Minecraft.getMinecraft().getResourceManager().getResource(var2).getInputStream();
         BufferedImage var4 = ImageIO.read(var3);
         int var5 = TextureUtil.glGenTextures();
         TextureUtil.uploadTextureImage(var5, var4);
         return var5;
      } catch (IOException var6) {
         var6.printStackTrace();
         return 0;
      }
   }

   public void fillRect(Rectangle var1, Color var2, Color var3, Color var4, Color var5) {
      Tessellator var6 = Tessellator.getInstance();
      WorldRenderer var7 = var6.getWorldRenderer();
      var7.startDrawingQuads();
      var7.func_178960_a((float)var5.getRed() / 255.0F, (float)var5.getGreen() / 255.0F, (float)var5.getBlue() / 255.0F, (float)var5.getAlpha() / 255.0F);
      var7.addVertex((double)var1.x, (double)(var1.y + var1.height), (double)this.getZLevel());
      var7.func_178960_a((float)var4.getRed() / 255.0F, (float)var4.getGreen() / 255.0F, (float)var4.getBlue() / 255.0F, (float)var4.getAlpha() / 255.0F);
      var7.addVertex((double)(var1.x + var1.width), (double)(var1.y + var1.height), (double)this.getZLevel());
      var7.func_178960_a((float)var3.getRed() / 255.0F, (float)var3.getGreen() / 255.0F, (float)var3.getBlue() / 255.0F, (float)var3.getAlpha() / 255.0F);
      var7.addVertex((double)(var1.x + var1.width), (double)var1.y, (double)this.getZLevel());
      var7.func_178960_a((float)var2.getRed() / 255.0F, (float)var2.getGreen() / 255.0F, (float)var2.getBlue() / 255.0F, (float)var2.getAlpha() / 255.0F);
      var7.addVertex((double)var1.x, (double)var1.y, (double)this.getZLevel());
      var6.draw();
   }
}
