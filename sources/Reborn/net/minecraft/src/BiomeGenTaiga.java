package net.minecraft.src;

import java.util.*;

public class BiomeGenTaiga extends BiomeGenBase
{
    public BiomeGenTaiga(final int par1) {
        super(par1);
        this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 8, 4, 4));
        this.theBiomeDecorator.treesPerChunk = 10;
        this.theBiomeDecorator.grassPerChunk = 1;
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForTrees(final Random par1Random) {
        return (par1Random.nextInt(3) == 0) ? new WorldGenTaiga1() : new WorldGenTaiga2(false);
    }
}
