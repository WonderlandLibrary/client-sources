package net.minecraft.client.renderer.texture;

import java.io.IOException;
import net.minecraft.client.resources.IResourceManager;

public interface ITextureObject {
  void setBlurMipmap(boolean paramBoolean1, boolean paramBoolean2);
  
  void restoreLastBlurMipmap();
  
  void loadTexture(IResourceManager paramIResourceManager) throws IOException;
  
  int getGlTextureId();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\texture\ITextureObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */