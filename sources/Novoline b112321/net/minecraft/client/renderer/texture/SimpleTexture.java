package net.minecraft.client.renderer.texture;

import java.io.IOException;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleTexture extends AbstractTexture {
   private static final Logger LOGGER = LogManager.getLogger();
   protected final ResourceLocation textureLocation;

   public SimpleTexture(ResourceLocation var1) {
      this.textureLocation = var1;
   }

   public void loadTexture(IResourceManager param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
