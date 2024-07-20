/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEndDecorator;

public class BiomeEnd
extends Biome {
    public BiomeEnd(Biome.BiomeProperties properties) {
        super(properties);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 10, 4, 4));
        this.topBlock = Blocks.DIRT.getDefaultState();
        this.fillerBlock = Blocks.DIRT.getDefaultState();
        this.theBiomeDecorator = new BiomeEndDecorator();
    }

    @Override
    public int getSkyColorByTemp(float currentTemperature) {
        return 0;
    }
}

