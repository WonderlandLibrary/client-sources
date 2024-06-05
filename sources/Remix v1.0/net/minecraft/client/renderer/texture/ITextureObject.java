package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.*;
import java.io.*;
import shadersmod.client.*;

public interface ITextureObject
{
    void func_174936_b(final boolean p0, final boolean p1);
    
    void func_174935_a();
    
    void loadTexture(final IResourceManager p0) throws IOException;
    
    int getGlTextureId();
    
    MultiTexID getMultiTexID();
}
