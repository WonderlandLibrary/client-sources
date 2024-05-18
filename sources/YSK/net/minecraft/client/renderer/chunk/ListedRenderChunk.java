package net.minecraft.client.renderer.chunk;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class ListedRenderChunk extends RenderChunk
{
    private final int baseDisplayList;
    
    public int getDisplayList(final EnumWorldBlockLayer enumWorldBlockLayer, final CompiledChunk compiledChunk) {
        int n;
        if (!compiledChunk.isLayerEmpty(enumWorldBlockLayer)) {
            n = this.baseDisplayList + enumWorldBlockLayer.ordinal();
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            n = -" ".length();
        }
        return n;
    }
    
    @Override
    public void deleteGlResources() {
        super.deleteGlResources();
        GLAllocation.deleteDisplayLists(this.baseDisplayList, EnumWorldBlockLayer.values().length);
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ListedRenderChunk(final World world, final RenderGlobal renderGlobal, final BlockPos blockPos, final int n) {
        super(world, renderGlobal, blockPos, n);
        this.baseDisplayList = GLAllocation.generateDisplayLists(EnumWorldBlockLayer.values().length);
    }
}
