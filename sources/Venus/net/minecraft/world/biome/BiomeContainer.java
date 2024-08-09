/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome;

import javax.annotation.Nullable;
import net.minecraft.util.IObjectIntIterable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.provider.BiomeProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeContainer
implements BiomeManager.IBiomeReader {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int WIDTH_BITS = (int)Math.round(Math.log(16.0) / Math.log(2.0)) - 2;
    private static final int HEIGHT_BITS = (int)Math.round(Math.log(256.0) / Math.log(2.0)) - 2;
    public static final int BIOMES_SIZE = 1 << WIDTH_BITS + WIDTH_BITS + HEIGHT_BITS;
    public static final int HORIZONTAL_MASK = (1 << WIDTH_BITS) - 1;
    public static final int VERTICAL_MASK = (1 << HEIGHT_BITS) - 1;
    private final IObjectIntIterable<Biome> biomeRegistry;
    private final Biome[] biomes;

    public BiomeContainer(IObjectIntIterable<Biome> iObjectIntIterable, Biome[] biomeArray) {
        this.biomeRegistry = iObjectIntIterable;
        this.biomes = biomeArray;
    }

    private BiomeContainer(IObjectIntIterable<Biome> iObjectIntIterable) {
        this(iObjectIntIterable, new Biome[BIOMES_SIZE]);
    }

    public BiomeContainer(IObjectIntIterable<Biome> iObjectIntIterable, int[] nArray) {
        this(iObjectIntIterable);
        for (int i = 0; i < this.biomes.length; ++i) {
            int n = nArray[i];
            Biome biome = iObjectIntIterable.getByValue(n);
            if (biome == null) {
                LOGGER.warn("Received invalid biome id: " + n);
                this.biomes[i] = iObjectIntIterable.getByValue(0);
                continue;
            }
            this.biomes[i] = biome;
        }
    }

    public BiomeContainer(IObjectIntIterable<Biome> iObjectIntIterable, ChunkPos chunkPos, BiomeProvider biomeProvider) {
        this(iObjectIntIterable);
        int n = chunkPos.getXStart() >> 2;
        int n2 = chunkPos.getZStart() >> 2;
        for (int i = 0; i < this.biomes.length; ++i) {
            int n3 = i & HORIZONTAL_MASK;
            int n4 = i >> WIDTH_BITS + WIDTH_BITS & VERTICAL_MASK;
            int n5 = i >> WIDTH_BITS & HORIZONTAL_MASK;
            this.biomes[i] = biomeProvider.getNoiseBiome(n + n3, n4, n2 + n5);
        }
    }

    public BiomeContainer(IObjectIntIterable<Biome> iObjectIntIterable, ChunkPos chunkPos, BiomeProvider biomeProvider, @Nullable int[] nArray) {
        this(iObjectIntIterable);
        int n = chunkPos.getXStart() >> 2;
        int n2 = chunkPos.getZStart() >> 2;
        if (nArray != null) {
            for (int i = 0; i < nArray.length; ++i) {
                this.biomes[i] = iObjectIntIterable.getByValue(nArray[i]);
                if (this.biomes[i] != null) continue;
                int n3 = i & HORIZONTAL_MASK;
                int n4 = i >> WIDTH_BITS + WIDTH_BITS & VERTICAL_MASK;
                int n5 = i >> WIDTH_BITS & HORIZONTAL_MASK;
                this.biomes[i] = biomeProvider.getNoiseBiome(n + n3, n4, n2 + n5);
            }
        } else {
            for (int i = 0; i < this.biomes.length; ++i) {
                int n6 = i & HORIZONTAL_MASK;
                int n7 = i >> WIDTH_BITS + WIDTH_BITS & VERTICAL_MASK;
                int n8 = i >> WIDTH_BITS & HORIZONTAL_MASK;
                this.biomes[i] = biomeProvider.getNoiseBiome(n + n6, n7, n2 + n8);
            }
        }
    }

    public int[] getBiomeIds() {
        int[] nArray = new int[this.biomes.length];
        for (int i = 0; i < this.biomes.length; ++i) {
            nArray[i] = this.biomeRegistry.getId(this.biomes[i]);
        }
        return nArray;
    }

    @Override
    public Biome getNoiseBiome(int n, int n2, int n3) {
        int n4 = n & HORIZONTAL_MASK;
        int n5 = MathHelper.clamp(n2, 0, VERTICAL_MASK);
        int n6 = n3 & HORIZONTAL_MASK;
        return this.biomes[n5 << WIDTH_BITS + WIDTH_BITS | n6 << WIDTH_BITS | n4];
    }
}

