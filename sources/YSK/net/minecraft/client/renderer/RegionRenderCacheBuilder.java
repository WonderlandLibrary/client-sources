package net.minecraft.client.renderer;

import net.minecraft.util.*;

public class RegionRenderCacheBuilder
{
    private final WorldRenderer[] worldRenderers;
    
    public WorldRenderer getWorldRendererByLayer(final EnumWorldBlockLayer enumWorldBlockLayer) {
        return this.worldRenderers[enumWorldBlockLayer.ordinal()];
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
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public WorldRenderer getWorldRendererByLayerId(final int n) {
        return this.worldRenderers[n];
    }
    
    public RegionRenderCacheBuilder() {
        (this.worldRenderers = new WorldRenderer[EnumWorldBlockLayer.values().length])[EnumWorldBlockLayer.SOLID.ordinal()] = new WorldRenderer(1760905 + 2079322 - 1866997 + 123922);
        this.worldRenderers[EnumWorldBlockLayer.CUTOUT.ordinal()] = new WorldRenderer(58253 + 111660 - 52152 + 13311);
        this.worldRenderers[EnumWorldBlockLayer.CUTOUT_MIPPED.ordinal()] = new WorldRenderer(69122 + 74415 - 23202 + 10737);
        this.worldRenderers[EnumWorldBlockLayer.TRANSLUCENT.ordinal()] = new WorldRenderer(166986 + 28201 - 130731 + 197688);
    }
}
