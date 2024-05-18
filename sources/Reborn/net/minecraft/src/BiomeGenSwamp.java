package net.minecraft.src;

import java.util.*;

public class BiomeGenSwamp extends BiomeGenBase
{
    protected BiomeGenSwamp(final int par1) {
        super(par1);
        this.theBiomeDecorator.treesPerChunk = 2;
        this.theBiomeDecorator.flowersPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 1;
        this.theBiomeDecorator.mushroomsPerChunk = 8;
        this.theBiomeDecorator.reedsPerChunk = 10;
        this.theBiomeDecorator.clayPerChunk = 1;
        this.theBiomeDecorator.waterlilyPerChunk = 4;
        this.waterColorMultiplier = 14745518;
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 1, 1, 1));
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForTrees(final Random par1Random) {
        return this.worldGeneratorSwamp;
    }
    
    @Override
    public int getBiomeGrassColor() {
        final double var1 = this.getFloatTemperature();
        final double var2 = this.getFloatRainfall();
        return ((ColorizerGrass.getGrassColor(var1, var2) & 0xFEFEFE) + 5115470) / 2;
    }
    
    @Override
    public int getBiomeFoliageColor() {
        final double var1 = this.getFloatTemperature();
        final double var2 = this.getFloatRainfall();
        return ((ColorizerFoliage.getFoliageColor(var1, var2) & 0xFEFEFE) + 5115470) / 2;
    }
}
