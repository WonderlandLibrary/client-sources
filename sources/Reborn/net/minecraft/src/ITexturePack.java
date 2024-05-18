package net.minecraft.src;

import java.io.*;

public interface ITexturePack
{
    void deleteTexturePack(final RenderEngine p0);
    
    void bindThumbnailTexture(final RenderEngine p0);
    
    InputStream func_98137_a(final String p0, final boolean p1) throws IOException;
    
    InputStream getResourceAsStream(final String p0) throws IOException;
    
    String getTexturePackID();
    
    String getTexturePackFileName();
    
    String getFirstDescriptionLine();
    
    String getSecondDescriptionLine();
    
    boolean func_98138_b(final String p0, final boolean p1);
    
    boolean isCompatible();
}
