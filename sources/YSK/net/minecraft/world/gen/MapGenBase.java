package net.minecraft.world.gen;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;

public class MapGenBase
{
    protected Random rand;
    protected int range;
    protected World worldObj;
    
    public MapGenBase() {
        this.range = (0xD ^ 0x5);
        this.rand = new Random();
    }
    
    protected void recursiveGenerate(final World world, final int n, final int n2, final int n3, final int n4, final ChunkPrimer chunkPrimer) {
    }
    
    public void generate(final IChunkProvider chunkProvider, final World worldObj, final int n, final int n2, final ChunkPrimer chunkPrimer) {
        final int range = this.range;
        this.worldObj = worldObj;
        this.rand.setSeed(worldObj.getSeed());
        final long nextLong = this.rand.nextLong();
        final long nextLong2 = this.rand.nextLong();
        int i = n - range;
        "".length();
        if (-1 == 1) {
            throw null;
        }
        while (i <= n + range) {
            int j = n2 - range;
            "".length();
            if (3 <= -1) {
                throw null;
            }
            while (j <= n2 + range) {
                this.rand.setSeed(i * nextLong ^ j * nextLong2 ^ worldObj.getSeed());
                this.recursiveGenerate(worldObj, i, j, n, n2, chunkPrimer);
                ++j;
            }
            ++i;
        }
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
            if (2 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
