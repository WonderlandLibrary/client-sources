/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;

public interface IBiomeMagnifier {
    public Biome getBiome(long var1, int var3, int var4, int var5, BiomeManager.IBiomeReader var6);
}

