package net.minecraft.world.biome;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;

public class BiomeProviderSingle extends BiomeProvider
{
    /** The biome generator object. */
    private final Biome biomeGenerator;

    public BiomeProviderSingle(Biome biomeIn)
    {
        this.biomeGenerator = biomeIn;
    }

    /**
     * Returns the biome generator
     */
    public Biome getBiomeGenerator(BlockPos pos)
    {
        return this.biomeGenerator;
    }

    /**
     * Returns an array of biomes for the location input.
     */
    public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height)
    {
        if (biomes == null || biomes.length < width * height)
        {
            biomes = new Biome[width * height];
        }

        Arrays.fill(biomes, 0, width * height, this.biomeGenerator);
        return biomes;
    }

    /**
     * Gets biomes to use for the blocks and loads the other data like temperature and humidity onto the
     * WorldChunkManager.
     */
    public Biome[] loadBlockGeneratorData(@Nullable Biome[] oldBiomeList, int x, int z, int width, int depth)
    {
        if (oldBiomeList == null || oldBiomeList.length < width * depth)
        {
            oldBiomeList = new Biome[width * depth];
        }

        Arrays.fill(oldBiomeList, 0, width * depth, this.biomeGenerator);
        return oldBiomeList;
    }

    /**
     * Gets a list of biomes for the specified blocks.
     */
    public Biome[] getBiomeGenAt(@Nullable Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag)
    {
        return this.loadBlockGeneratorData(listToReuse, x, z, width, length);
    }

    @Nullable
    public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random)
    {
        return biomes.contains(this.biomeGenerator) ? new BlockPos(x - range + random.nextInt(range * 2 + 1), 0, z - range + random.nextInt(range * 2 + 1)) : null;
    }

    /**
     * checks given Chunk's Biomes against List of allowed ones
     */
    public boolean areBiomesViable(int x, int z, int radius, List<Biome> allowed)
    {
        return allowed.contains(this.biomeGenerator);
    }
}
