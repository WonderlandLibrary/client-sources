package net.optifine;

import java.nio.ByteBuffer;
import java.util.Properties;
import net.acE;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.MatchBlock;
import net.optifine.TextureAnimationFrame;
import net.optifine.TextureUtils;
import org.lwjgl.opengl.GL11;

public class TextureAnimation {
   private String srcTex = null;
   private String dstTex = null;
   ResourceLocation dstTexLoc = null;
   private int dstTextId = -1;
   private int dstX = 0;
   private int dstY;
   private int frameWidth;
   private int frameHeight;
   private TextureAnimationFrame[] frames;
   private int activeFrame;
   byte[] srcData;
   private ByteBuffer imageData;

   public TextureAnimation(String var1, byte[] var2, String var3, ResourceLocation var4, int var5, int var6, int var7, int var8, Properties var9, int var10) {
      MatchBlock.b();
      this.dstY = 0;
      this.frameWidth = 0;
      this.frameHeight = 0;
      this.frames = null;
      this.activeFrame = 0;
      this.srcData = null;
      this.imageData = null;
      this.srcTex = var1;
      this.dstTex = var3;
      this.dstTexLoc = var4;
      this.dstX = var5;
      this.dstY = var6;
      this.frameWidth = var7;
      this.frameHeight = var8;
      int var12 = var7 * var8 * 4;
      if(var2.length % var12 != 0) {
         Config.warn("Invalid animated texture length: " + var2.length + ", frameWidth: " + var7 + ", frameHeight: " + var8);
      }

      this.srcData = var2;
      int var13 = var2.length / var12;
      if(var9.get("tile.0") != null) {
         int var14 = 0;
         if(var9.get("tile." + var14) != null) {
            var13 = var14 + 1;
            ++var14;
         }
      }

      String var23 = (String)var9.get("duration");
      int var15 = Config.parseInt(var23, var10);
      this.frames = new TextureAnimationFrame[var13];
      int var16 = 0;
      if(var16 < this.frames.length) {
         String var17 = (String)var9.get("tile." + var16);
         int var18 = Config.parseInt(var17, var16);
         String var19 = (String)var9.get("duration." + var16);
         int var20 = Config.parseInt(var19, var15);
         TextureAnimationFrame var21 = new TextureAnimationFrame(var18, var20);
         this.frames[var16] = var21;
         ++var16;
      }

   }

   public boolean nextFrame() {
      acE[] var1 = MatchBlock.b();
      if(this.frames.length <= 0) {
         return false;
      } else {
         if(this.activeFrame >= this.frames.length) {
            this.activeFrame = 0;
         }

         TextureAnimationFrame var2 = this.frames[this.activeFrame];
         ++var2.counter;
         if(var2.counter < var2.duration) {
            return false;
         } else {
            var2.counter = 0;
            ++this.activeFrame;
            if(this.activeFrame >= this.frames.length) {
               this.activeFrame = 0;
            }

            return true;
         }
      }
   }

   public int getActiveFrameIndex() {
      acE[] var1 = MatchBlock.b();
      if(this.frames.length <= 0) {
         return 0;
      } else {
         if(this.activeFrame >= this.frames.length) {
            this.activeFrame = 0;
         }

         TextureAnimationFrame var2 = this.frames[this.activeFrame];
         return var2.index;
      }
   }

   public int getFrameCount() {
      return this.frames.length;
   }

   public boolean updateTexture() {
      acE[] var1 = MatchBlock.b();
      if(this.dstTextId < 0) {
         ITextureObject var5 = TextureUtils.getTexture(this.dstTexLoc);
         return false;
      } else {
         if(this.imageData == null) {
            this.imageData = GLAllocation.createDirectByteBuffer(this.srcData.length);
            this.imageData.put(this.srcData);
            this.srcData = null;
         }

         if(!this.nextFrame()) {
            return false;
         } else {
            int var2 = this.frameWidth * this.frameHeight * 4;
            int var3 = this.getActiveFrameIndex();
            int var4 = var2 * var3;
            if(var4 + var2 > this.imageData.capacity()) {
               return false;
            } else {
               this.imageData.position(var4);
               GlStateManager.bindTexture(this.dstTextId);
               GL11.glTexSubImage2D(3553, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, 6408, 5121, this.imageData);
               return true;
            }
         }
      }
   }

   public String getSrcTex() {
      return this.srcTex;
   }

   public String getDstTex() {
      return this.dstTex;
   }

   public ResourceLocation getDstTexLoc() {
      return this.dstTexLoc;
   }
}
