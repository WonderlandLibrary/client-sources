package optifine;

import java.awt.image.BufferedImage;
import net.aTu;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.util.ResourceLocation;
import optifine.CapeUtils;

final class CapeUtils$1 implements IImageBuffer {
   aTu c;
   final AbstractClientPlayer val$p_downloadCape_0_;
   final ResourceLocation val$resourcelocation;

   CapeUtils$1(AbstractClientPlayer var1, ResourceLocation var2) {
      this.val$p_downloadCape_0_ = var1;
      this.val$resourcelocation = var2;
      this.c = new aTu();
   }

   public BufferedImage parseUserSkin(BufferedImage var1) {
      return CapeUtils.parseCape(var1);
   }

   public void skinAvailable() {
      this.val$p_downloadCape_0_.setLocationOfCape(this.val$resourcelocation);
   }
}
