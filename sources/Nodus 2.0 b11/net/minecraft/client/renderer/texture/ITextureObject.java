package net.minecraft.client.renderer.texture;

import java.io.IOException;
import net.minecraft.client.resources.IResourceManager;

public abstract interface ITextureObject
{
  public abstract void loadTexture(IResourceManager paramIResourceManager)
    throws IOException;
  
  public abstract int getGlTextureId();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.texture.ITextureObject
 * JD-Core Version:    0.7.0.1
 */