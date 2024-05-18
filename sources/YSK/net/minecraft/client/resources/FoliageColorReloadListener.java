package net.minecraft.client.resources;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import java.io.*;

public class FoliageColorReloadListener implements IResourceManagerReloadListener
{
    private static final ResourceLocation LOC_FOLIAGE_PNG;
    private static final String[] I;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("0\u0016/\u0017\u00116\u0016$L\u0007+\u001f8\u0011\t%\u0003x\u0005\u000b(\u001a6\u0004\u0001j\u00039\u0004", "DsWcd");
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
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        try {
            ColorizerFoliage.setFoliageBiomeColorizer(TextureUtil.readImageData(resourceManager, FoliageColorReloadListener.LOC_FOLIAGE_PNG));
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        catch (IOException ex) {}
    }
    
    static {
        I();
        LOC_FOLIAGE_PNG = new ResourceLocation(FoliageColorReloadListener.I["".length()]);
    }
}
