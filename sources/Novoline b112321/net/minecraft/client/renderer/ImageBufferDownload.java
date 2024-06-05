package net.minecraft.client.renderer;

import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.IImageBuffer;

public class ImageBufferDownload implements IImageBuffer {
   private int[] b;
   private int a;
   private int c;

   public BufferedImage parseUserSkin(BufferedImage var1) {
      return null;
   }

   public void skinAvailable() {
   }

   private void setAreaTransparent(int var1, int var2, int var3, int var4) {
      if(!this.hasTransparency(var1, var2, var3, var4)) {
         for(int var5 = var1; var5 < var3; ++var5) {
            for(int var6 = var2; var6 < var4; ++var6) {
               this.b[var5 + var6 * this.a] &= 16777215;
            }
         }
      }

   }

   private void setAreaOpaque(int var1, int var2, int var3, int var4) {
      for(int var5 = var1; var5 < var3; ++var5) {
         for(int var6 = var2; var6 < var4; ++var6) {
            this.b[var5 + var6 * this.a] |= -16777216;
         }
      }

   }

   private boolean hasTransparency(int var1, int var2, int var3, int var4) {
      for(int var5 = var1; var5 < var3; ++var5) {
         for(int var6 = var2; var6 < var4; ++var6) {
            int var7 = this.b[var5 + var6 * this.a];
            if((var7 >> 24 & 255) < 128) {
               return true;
            }
         }
      }

      return false;
   }
}
