package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.*;

public class GenLayerRiverMix extends GenLayer
{
    private GenLayer riverPatternGeneratorChain;
    private GenLayer biomePatternGeneratorChain;
    
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
            if (1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int[] ints = this.biomePatternGeneratorChain.getInts(n, n2, n3, n4);
        final int[] ints2 = this.riverPatternGeneratorChain.getInts(n, n2, n3, n4);
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        int i = "".length();
        "".length();
        if (-1 == 0) {
            throw null;
        }
        while (i < n3 * n4) {
            if (ints[i] != BiomeGenBase.ocean.biomeID && ints[i] != BiomeGenBase.deepOcean.biomeID) {
                if (ints2[i] == BiomeGenBase.river.biomeID) {
                    if (ints[i] == BiomeGenBase.icePlains.biomeID) {
                        intCache[i] = BiomeGenBase.frozenRiver.biomeID;
                        "".length();
                        if (1 <= -1) {
                            throw null;
                        }
                    }
                    else if (ints[i] != BiomeGenBase.mushroomIsland.biomeID && ints[i] != BiomeGenBase.mushroomIslandShore.biomeID) {
                        intCache[i] = (ints2[i] & 27 + 187 + 28 + 13);
                        "".length();
                        if (4 <= -1) {
                            throw null;
                        }
                    }
                    else {
                        intCache[i] = BiomeGenBase.mushroomIslandShore.biomeID;
                        "".length();
                        if (1 <= 0) {
                            throw null;
                        }
                    }
                }
                else {
                    intCache[i] = ints[i];
                    "".length();
                    if (2 < 0) {
                        throw null;
                    }
                }
            }
            else {
                intCache[i] = ints[i];
            }
            ++i;
        }
        return intCache;
    }
    
    public GenLayerRiverMix(final long n, final GenLayer biomePatternGeneratorChain, final GenLayer riverPatternGeneratorChain) {
        super(n);
        this.biomePatternGeneratorChain = biomePatternGeneratorChain;
        this.riverPatternGeneratorChain = riverPatternGeneratorChain;
    }
    
    @Override
    public void initWorldGenSeed(final long n) {
        this.biomePatternGeneratorChain.initWorldGenSeed(n);
        this.riverPatternGeneratorChain.initWorldGenSeed(n);
        super.initWorldGenSeed(n);
    }
}
