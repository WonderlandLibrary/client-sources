package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.*;
import java.io.*;

public interface ITextureObject
{
    void setBlurMipmap(final boolean p0, final boolean p1);
    
    void restoreLastBlurMipmap();
    
    void loadTexture(final IResourceManager p0) throws IOException;
    
    int getGlTextureId();
}
