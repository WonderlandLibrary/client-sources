package net.minecraft.client.renderer.texture;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LayeredColorMaskTexture extends AbstractTexture {
   private static final Logger LOGGER = LogManager.getLogger();
   private final ResourceLocation textureLocation;
   private final List field_174949_h;
   private final List field_174950_i;

   public LayeredColorMaskTexture(ResourceLocation var1, List var2, List var3) {
      this.textureLocation = var1;
      this.field_174949_h = var2;
      this.field_174950_i = var3;
   }

   public void loadTexture(IResourceManager param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
