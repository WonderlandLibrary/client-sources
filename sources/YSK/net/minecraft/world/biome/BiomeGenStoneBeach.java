package net.minecraft.world.biome;

import net.minecraft.init.*;

public class BiomeGenStoneBeach extends BiomeGenBase
{
    public BiomeGenStoneBeach(final int n) {
        super(n);
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.stone.getDefaultState();
        this.fillerBlock = Blocks.stone.getDefaultState();
        this.theBiomeDecorator.treesPerChunk = -(942 + 174 - 141 + 24);
        this.theBiomeDecorator.deadBushPerChunk = "".length();
        this.theBiomeDecorator.reedsPerChunk = "".length();
        this.theBiomeDecorator.cactiPerChunk = "".length();
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
            if (4 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
