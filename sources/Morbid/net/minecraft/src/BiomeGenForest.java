package net.minecraft.src;

import java.util.*;

public class BiomeGenForest extends BiomeGenBase
{
    public BiomeGenForest(final int par1) {
        super(par1);
        this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 4, 4));
        this.theBiomeDecorator.treesPerChunk = 10;
        this.theBiomeDecorator.grassPerChunk = 2;
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForTrees(final Random par1Random) {
        return (par1Random.nextInt(5) == 0) ? this.worldGeneratorForest : ((par1Random.nextInt(10) == 0) ? this.worldGeneratorBigTree : this.worldGeneratorTrees);
    }
}
