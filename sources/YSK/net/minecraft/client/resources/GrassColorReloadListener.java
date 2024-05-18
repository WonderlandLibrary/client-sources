package net.minecraft.client.resources;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import java.io.*;

public class GrassColorReloadListener implements IResourceManagerReloadListener
{
    private static final ResourceLocation LOC_GRASS_PNG;
    private static final String[] I;
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        try {
            ColorizerGrass.setGrassBiomeColorizer(TextureUtil.readImageData(resourceManager, GrassColorReloadListener.LOC_GRASS_PNG));
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        catch (IOException ex) {}
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I(":'\u0016\u001c\r<'\u001dG\u001b!.\u0001\u001a\u0015/2A\u000f\n/1\u001dF\b %", "NBnhx");
    }
    
    static {
        I();
        LOC_GRASS_PNG = new ResourceLocation(GrassColorReloadListener.I["".length()]);
    }
}
