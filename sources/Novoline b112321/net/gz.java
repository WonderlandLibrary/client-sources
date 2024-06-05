package net;

import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.IImageBuffer;

public class gz {
   public static BufferedImage a(IImageBuffer var0, BufferedImage var1) {
      return var0.parseUserSkin(var1);
   }

   public static void a(IImageBuffer var0) {
      var0.skinAvailable();
   }
}
