package net.minecraft.world.biome;

import java.util.*;
import net.minecraft.util.*;

public class WorldChunkManagerHell extends WorldChunkManager
{
    private float rainfall;
    private BiomeGenBase biomeGenerator;
    
    @Override
    public BiomeGenBase[] getBiomeGenAt(final BiomeGenBase[] array, final int n, final int n2, final int n3, final int n4, final boolean b) {
        return this.loadBlockGeneratorData(array, n, n2, n3, n4);
    }
    
    @Override
    public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] array, final int n, final int n2, final int n3, final int n4) {
        if (array == null || array.length < n3 * n4) {
            array = new BiomeGenBase[n3 * n4];
        }
        Arrays.fill(array, "".length(), n3 * n4, this.biomeGenerator);
        return array;
    }
    
    public WorldChunkManagerHell(final BiomeGenBase biomeGenerator, final float rainfall) {
        this.biomeGenerator = biomeGenerator;
        this.rainfall = rainfall;
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
            if (0 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean areBiomesViable(final int n, final int n2, final int n3, final List<BiomeGenBase> list) {
        return list.contains(this.biomeGenerator);
    }
    
    @Override
    public BlockPos findBiomePosition(final int n, final int n2, final int n3, final List<BiomeGenBase> list, final Random random) {
        BlockPos blockPos;
        if (list.contains(this.biomeGenerator)) {
            blockPos = new BlockPos(n - n3 + random.nextInt(n3 * "  ".length() + " ".length()), "".length(), n2 - n3 + random.nextInt(n3 * "  ".length() + " ".length()));
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            blockPos = null;
        }
        return blockPos;
    }
    
    @Override
    public float[] getRainfall(float[] array, final int n, final int n2, final int n3, final int n4) {
        if (array == null || array.length < n3 * n4) {
            array = new float[n3 * n4];
        }
        Arrays.fill(array, "".length(), n3 * n4, this.rainfall);
        return array;
    }
    
    @Override
    public BiomeGenBase getBiomeGenerator(final BlockPos blockPos) {
        return this.biomeGenerator;
    }
    
    @Override
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] array, final int n, final int n2, final int n3, final int n4) {
        if (array == null || array.length < n3 * n4) {
            array = new BiomeGenBase[n3 * n4];
        }
        Arrays.fill(array, "".length(), n3 * n4, this.biomeGenerator);
        return array;
    }
}
