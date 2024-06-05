package net.minecraft.client.resources;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import java.io.*;

public class GrassColorReloadListener implements IResourceManagerReloadListener
{
    private static final ResourceLocation field_130078_a;
    
    @Override
    public void onResourceManagerReload(final IResourceManager p_110549_1_) {
        try {
            ColorizerGrass.setGrassBiomeColorizer(TextureUtil.readImageData(p_110549_1_, GrassColorReloadListener.field_130078_a));
        }
        catch (IOException ex) {}
    }
    
    static {
        field_130078_a = new ResourceLocation("textures/colormap/grass.png");
    }
}
