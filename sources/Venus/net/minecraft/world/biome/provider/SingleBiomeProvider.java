/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome.provider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

public class SingleBiomeProvider
extends BiomeProvider {
    public static final Codec<SingleBiomeProvider> field_235260_e_ = ((MapCodec)Biome.BIOME_CODEC.fieldOf("biome")).xmap(SingleBiomeProvider::new, SingleBiomeProvider::lambda$static$0).stable().codec();
    private final Supplier<Biome> biome;

    public SingleBiomeProvider(Biome biome) {
        this(() -> SingleBiomeProvider.lambda$new$1(biome));
    }

    public SingleBiomeProvider(Supplier<Biome> supplier) {
        super(ImmutableList.of(supplier.get()));
        this.biome = supplier;
    }

    @Override
    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return field_235260_e_;
    }

    @Override
    public BiomeProvider getBiomeProvider(long l) {
        return this;
    }

    @Override
    public Biome getNoiseBiome(int n, int n2, int n3) {
        return this.biome.get();
    }

    @Override
    @Nullable
    public BlockPos findBiomePosition(int n, int n2, int n3, int n4, int n5, Predicate<Biome> predicate, Random random2, boolean bl) {
        if (predicate.test(this.biome.get())) {
            return bl ? new BlockPos(n, n2, n3) : new BlockPos(n - n4 + random2.nextInt(n4 * 2 + 1), n2, n3 - n4 + random2.nextInt(n4 * 2 + 1));
        }
        return null;
    }

    @Override
    public Set<Biome> getBiomes(int n, int n2, int n3, int n4) {
        return Sets.newHashSet(this.biome.get());
    }

    private static Biome lambda$new$1(Biome biome) {
        return biome;
    }

    private static Supplier lambda$static$0(SingleBiomeProvider singleBiomeProvider) {
        return singleBiomeProvider.biome;
    }
}

