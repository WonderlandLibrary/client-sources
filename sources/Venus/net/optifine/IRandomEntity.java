/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public interface IRandomEntity {
    public int getId();

    public BlockPos getSpawnPosition();

    public Biome getSpawnBiome();

    public String getName();

    public int getHealth();

    public int getMaxHealth();
}

