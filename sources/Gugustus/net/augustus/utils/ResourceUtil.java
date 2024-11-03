package net.augustus.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.augustus.utils.interfaces.MC;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class ResourceUtil implements MC {
   public static ResourceLocation loadResourceLocation(String path, String name) {
      BufferedImage img = null;
      InputStream inputStream = ResourceUtil.class.getClassLoader().getResourceAsStream(path);
      if (inputStream == null) {
         return null;
      } else {
         try {
            img = ImageIO.read(inputStream);
         } catch (IOException var5) {
            var5.printStackTrace();
         }

         return mc.getRenderManager().renderEngine.getDynamicTextureLocation(name, new DynamicTexture(img));
      }
   }
}
