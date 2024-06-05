package net.optifine;

import java.io.IOException;
import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.resources.IResourceManager;
import net.optifine.TextureAnimations;
import net.shadersmod.client.MultiTexID;

final class TextureUtils$2 implements ITickableTextureObject {
   public void tick() {
      TextureAnimations.updateCustomAnimations();
   }

   public void loadTexture(IResourceManager var1) throws IOException {
   }

   public int getGlTextureId() {
      return 0;
   }

   public void setBlurMipmap(boolean var1, boolean var2) {
   }

   public void restoreLastBlurMipmap() {
   }

   public MultiTexID getMultiTexID() {
      return null;
   }
}
