/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.IBiomeMagnifier;

public enum DefaultBiomeMagnifier implements IBiomeMagnifier
{
    INSTANCE;


    @Override
    public Biome getBiome(long l, int n, int n2, int n3, BiomeManager.IBiomeReader iBiomeReader) {
        return iBiomeReader.getNoiseBiome(n >> 2, n2 >> 2, n3 >> 2);
    }
}

