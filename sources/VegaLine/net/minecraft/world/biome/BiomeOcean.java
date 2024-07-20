/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome;

import net.minecraft.world.biome.Biome;

public class BiomeOcean
extends Biome {
    public BiomeOcean(Biome.BiomeProperties properties) {
        super(properties);
        this.spawnableCreatureList.clear();
    }

    @Override
    public Biome.TempCategory getTempCategory() {
        return Biome.TempCategory.OCEAN;
    }
}

