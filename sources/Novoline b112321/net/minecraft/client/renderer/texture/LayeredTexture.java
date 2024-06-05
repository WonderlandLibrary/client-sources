package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LayeredTexture extends AbstractTexture {
   private static final Logger LOGGER = LogManager.getLogger();
   public final List layeredTextureNames;

   public LayeredTexture(String... var1) {
      this.layeredTextureNames = Lists.newArrayList(var1);
   }

   public void loadTexture(IResourceManager var1) {
      this.deleteGlTexture();
      BufferedImage var2 = null;

      try {
         for(String var4 : this.layeredTextureNames) {
            InputStream var5 = var1.getResource(new ResourceLocation(var4)).getInputStream();
            BufferedImage var6 = TextureUtil.a(var5);
            var2 = new BufferedImage(var6.getWidth(), var6.getHeight(), 2);
            var2.getGraphics().drawImage(var6, 0, 0, (ImageObserver)null);
         }
      } catch (IOException var7) {
         LOGGER.error("Couldn\'t load layered image", var7);
         return;
      }

      TextureUtil.uploadTextureImage(this.getGlTextureId(), var2);
   }
}
