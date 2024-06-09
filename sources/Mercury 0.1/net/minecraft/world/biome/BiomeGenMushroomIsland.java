/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.world.biome;

import java.util.List;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenMushroomIsland
extends BiomeGenBase {
    private static final String __OBFID = "CL_00000177";

    public BiomeGenMushroomIsland(int p_i1984_1_) {
        super(p_i1984_1_);
        this.theBiomeDecorator.treesPerChunk = -100;
        this.theBiomeDecorator.flowersPerChunk = -100;
        this.theBiomeDecorator.grassPerChunk = -100;
        this.theBiomeDecorator.mushroomsPerChunk = 1;
        this.theBiomeDecorator.bigMushroomsPerChunk = 1;
        this.topBlock = Blocks.mycelium.getDefaultState();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityMooshroom.class, 8, 4, 8));
    }
}

