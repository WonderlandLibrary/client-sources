package net.minecraft.client.renderer.chunk;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class ListChunkFactory implements IRenderChunkFactory
{
    @Override
    public RenderChunk makeRenderChunk(final World world, final RenderGlobal renderGlobal, final BlockPos blockPos, final int n) {
        return new ListedRenderChunk(world, renderGlobal, blockPos, n);
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
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}
