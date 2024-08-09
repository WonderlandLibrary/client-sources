/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome.provider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.provider.CheckerboardBiomeProvider;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraft.world.biome.provider.NetherBiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.biome.provider.SingleBiomeProvider;
import net.minecraft.world.gen.feature.structure.Structure;

public abstract class BiomeProvider
implements BiomeManager.IBiomeReader {
    public static final Codec<BiomeProvider> CODEC = Registry.BIOME_PROVIDER_CODEC.dispatchStable(BiomeProvider::getBiomeProviderCodec, Function.identity());
    protected final Map<Structure<?>, Boolean> hasStructureCache = Maps.newHashMap();
    protected final Set<BlockState> topBlocksCache = Sets.newHashSet();
    protected final List<Biome> biomes;

    protected BiomeProvider(Stream<Supplier<Biome>> stream) {
        this(stream.map(Supplier::get).collect(ImmutableList.toImmutableList()));
    }

    protected BiomeProvider(List<Biome> list) {
        this.biomes = list;
    }

    protected abstract Codec<? extends BiomeProvider> getBiomeProviderCodec();

    public abstract BiomeProvider getBiomeProvider(long var1);

    public List<Biome> getBiomes() {
        return this.biomes;
    }

    public Set<Biome> getBiomes(int n, int n2, int n3, int n4) {
        int n5 = n - n4 >> 2;
        int n6 = n2 - n4 >> 2;
        int n7 = n3 - n4 >> 2;
        int n8 = n + n4 >> 2;
        int n9 = n2 + n4 >> 2;
        int n10 = n3 + n4 >> 2;
        int n11 = n8 - n5 + 1;
        int n12 = n9 - n6 + 1;
        int n13 = n10 - n7 + 1;
        HashSet<Biome> hashSet = Sets.newHashSet();
        for (int i = 0; i < n13; ++i) {
            for (int j = 0; j < n11; ++j) {
                for (int k = 0; k < n12; ++k) {
                    int n14 = n5 + j;
                    int n15 = n6 + k;
                    int n16 = n7 + i;
                    hashSet.add(this.getNoiseBiome(n14, n15, n16));
                }
            }
        }
        return hashSet;
    }

    @Nullable
    public BlockPos findBiomePosition(int n, int n2, int n3, int n4, Predicate<Biome> predicate, Random random2) {
        return this.findBiomePosition(n, n2, n3, n4, 1, predicate, random2, true);
    }

    @Nullable
    public BlockPos findBiomePosition(int n, int n2, int n3, int n4, int n5, Predicate<Biome> predicate, Random random2, boolean bl) {
        int n6;
        int n7 = n >> 2;
        int n8 = n3 >> 2;
        int n9 = n4 >> 2;
        int n10 = n2 >> 2;
        BlockPos blockPos = null;
        int n11 = 0;
        for (int i = n6 = bl ? 0 : n9; i <= n9; i += n5) {
            for (int j = -i; j <= i; j += n5) {
                boolean bl2 = Math.abs(j) == i;
                for (int k = -i; k <= i; k += n5) {
                    int n12;
                    int n13;
                    if (bl) {
                        int n14 = n13 = Math.abs(k) == i ? 1 : 0;
                        if (n13 == 0 && !bl2) continue;
                    }
                    if (!predicate.test(this.getNoiseBiome(n13 = n7 + k, n10, n12 = n8 + j))) continue;
                    if (blockPos == null || random2.nextInt(n11 + 1) == 0) {
                        blockPos = new BlockPos(n13 << 2, n2, n12 << 2);
                        if (bl) {
                            return blockPos;
                        }
                    }
                    ++n11;
                }
            }
        }
        return blockPos;
    }

    public boolean hasStructure(Structure<?> structure) {
        return this.hasStructureCache.computeIfAbsent(structure, this::lambda$hasStructure$1);
    }

    public Set<BlockState> getSurfaceBlocks() {
        if (this.topBlocksCache.isEmpty()) {
            for (Biome biome : this.biomes) {
                this.topBlocksCache.add(biome.getGenerationSettings().getSurfaceBuilderConfig().getTop());
            }
        }
        return this.topBlocksCache;
    }

    private Boolean lambda$hasStructure$1(Structure structure) {
        return this.biomes.stream().anyMatch(arg_0 -> BiomeProvider.lambda$hasStructure$0(structure, arg_0));
    }

    private static boolean lambda$hasStructure$0(Structure structure, Biome biome) {
        return biome.getGenerationSettings().hasStructure(structure);
    }

    static {
        Registry.register(Registry.BIOME_PROVIDER_CODEC, "fixed", SingleBiomeProvider.field_235260_e_);
        Registry.register(Registry.BIOME_PROVIDER_CODEC, "multi_noise", NetherBiomeProvider.CODEC);
        Registry.register(Registry.BIOME_PROVIDER_CODEC, "checkerboard", CheckerboardBiomeProvider.CODEC);
        Registry.register(Registry.BIOME_PROVIDER_CODEC, "vanilla_layered", OverworldBiomeProvider.CODEC);
        Registry.register(Registry.BIOME_PROVIDER_CODEC, "the_end", EndBiomeProvider.CODEC);
    }
}

