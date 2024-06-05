package net.optifine;

import java.awt.image.BufferedImage;
import net.asJ;
import net.rU;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.util.ResourceLocation;

final class CapeUtils$1 implements IImageBuffer {
   ImageBufferDownload ibd;
   final asJ b;
   final ResourceLocation val$resourcelocation;

   CapeUtils$1(asJ var1, ResourceLocation var2) {
      this.b = var1;
      this.val$resourcelocation = var2;
      this.ibd = new ImageBufferDownload();
   }

   public BufferedImage parseUserSkin(BufferedImage var1) {
      return rU.a(var1);
   }

   public void skinAvailable() {
      this.b.a(this.val$resourcelocation);
   }
}
