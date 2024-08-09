/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome;

import com.google.common.hash.Hashing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.IBiomeMagnifier;
import net.minecraft.world.biome.provider.BiomeProvider;

public class BiomeManager {
    private final IBiomeReader reader;
    private final long seed;
    private final IBiomeMagnifier magnifier;

    public BiomeManager(IBiomeReader iBiomeReader, long l, IBiomeMagnifier iBiomeMagnifier) {
        this.reader = iBiomeReader;
        this.seed = l;
        this.magnifier = iBiomeMagnifier;
    }

    public static long getHashedSeed(long l) {
        return Hashing.sha256().hashLong(l).asLong();
    }

    public BiomeManager copyWithProvider(BiomeProvider biomeProvider) {
        return new BiomeManager(biomeProvider, this.seed, this.magnifier);
    }

    public Biome getBiome(BlockPos blockPos) {
        return this.magnifier.getBiome(this.seed, blockPos.getX(), blockPos.getY(), blockPos.getZ(), this.reader);
    }

    public Biome getBiomeAtPosition(double d, double d2, double d3) {
        int n = MathHelper.floor(d) >> 2;
        int n2 = MathHelper.floor(d2) >> 2;
        int n3 = MathHelper.floor(d3) >> 2;
        return this.getBiomeAtPosition(n, n2, n3);
    }

    public Biome getBiomeAtPosition(BlockPos blockPos) {
        int n = blockPos.getX() >> 2;
        int n2 = blockPos.getY() >> 2;
        int n3 = blockPos.getZ() >> 2;
        return this.getBiomeAtPosition(n, n2, n3);
    }

    public Biome getBiomeAtPosition(int n, int n2, int n3) {
        return this.reader.getNoiseBiome(n, n2, n3);
    }

    public static interface IBiomeReader {
        public Biome getNoiseBiome(int var1, int var2, int var3);
    }
}

