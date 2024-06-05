package net.minecraft.src;

import java.util.*;

public class BiomeGenJungle extends BiomeGenBase
{
    public BiomeGenJungle(final int par1) {
        super(par1);
        this.theBiomeDecorator.treesPerChunk = 50;
        this.theBiomeDecorator.grassPerChunk = 25;
        this.theBiomeDecorator.flowersPerChunk = 4;
        this.spawnableMonsterList.add(new SpawnListEntry(EntityOcelot.class, 2, 1, 1));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10, 4, 4));
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForTrees(final Random par1Random) {
        return (par1Random.nextInt(10) == 0) ? this.worldGeneratorBigTree : ((par1Random.nextInt(2) == 0) ? new WorldGenShrub(3, 0) : ((par1Random.nextInt(3) == 0) ? new WorldGenHugeTrees(false, 10 + par1Random.nextInt(20), 3, 3) : new WorldGenTrees(false, 4 + par1Random.nextInt(7), 3, 3, true)));
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForGrass(final Random par1Random) {
        return (par1Random.nextInt(4) == 0) ? new WorldGenTallGrass(Block.tallGrass.blockID, 2) : new WorldGenTallGrass(Block.tallGrass.blockID, 1);
    }
    
    @Override
    public void decorate(final World par1World, final Random par2Random, final int par3, final int par4) {
        super.decorate(par1World, par2Random, par3, par4);
        final WorldGenVines var5 = new WorldGenVines();
        for (int var6 = 0; var6 < 50; ++var6) {
            final int var7 = par3 + par2Random.nextInt(16) + 8;
            final byte var8 = 64;
            final int var9 = par4 + par2Random.nextInt(16) + 8;
            var5.generate(par1World, par2Random, var7, var8, var9);
        }
    }
}
