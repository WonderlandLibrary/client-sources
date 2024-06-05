package net.minecraft.client.resources;

import java.io.IOException;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ColorizerGrass;

public class GrassColorReloadListener implements IResourceManagerReloadListener {
   private static final String __OBFID = "CL_00001078";
   private static final ResourceLocation field_130078_a = new ResourceLocation("textures/colormap/grass.png");

   public void onResourceManagerReload(IResourceManager var1) {
      try {
         ColorizerGrass.setGrassBiomeColorizer(TextureUtil.readImageData(var1, field_130078_a));
      } catch (IOException var3) {
      }

   }
}
