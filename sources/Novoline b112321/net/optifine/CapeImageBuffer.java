package net.optifine;

import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;
import net.asJ;
import net.rU;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.util.ResourceLocation;
import net.optifine.MatchBlock;

public class CapeImageBuffer extends ImageBufferDownload {
   public ImageBufferDownload imageBufferDownload;
   public final WeakReference playerRef;
   public final ResourceLocation resourceLocation;

   public CapeImageBuffer(asJ var1, ResourceLocation var2) {
      this.playerRef = new WeakReference(var1);
      this.resourceLocation = var2;
      this.imageBufferDownload = new ImageBufferDownload();
   }

   public BufferedImage parseUserSkin(BufferedImage var1) {
      return rU.a(var1);
   }

   private static BufferedImage parseCape(BufferedImage var0) {
      return null;
   }

   public void skinAvailable() {
      MatchBlock.b();
      asJ var2 = (asJ)this.playerRef.get();
      if(var2 != null) {
         a(var2, this.resourceLocation);
      }

   }

   private static void a(asJ var0, ResourceLocation var1) {
      var0.a(var1);
   }
}
