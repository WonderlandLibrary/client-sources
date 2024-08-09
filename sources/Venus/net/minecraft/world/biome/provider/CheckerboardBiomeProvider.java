/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome.provider;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

public class CheckerboardBiomeProvider
extends BiomeProvider {
    public static final Codec<CheckerboardBiomeProvider> CODEC = RecordCodecBuilder.create(CheckerboardBiomeProvider::lambda$static$2);
    private final List<Supplier<Biome>> biomes;
    private final int biomeScaleShift;
    private final int biomeScale;

    public CheckerboardBiomeProvider(List<Supplier<Biome>> list, int n) {
        super(list.stream());
        this.biomes = list;
        this.biomeScaleShift = n + 2;
        this.biomeScale = n;
    }

    @Override
    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return CODEC;
    }

    @Override
    public BiomeProvider getBiomeProvider(long l) {
        return this;
    }

    @Override
    public Biome getNoiseBiome(int n, int n2, int n3) {
        return this.biomes.get(Math.floorMod((n >> this.biomeScaleShift) + (n3 >> this.biomeScaleShift), this.biomes.size())).get();
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Biome.BIOMES_CODEC.fieldOf("biomes")).forGetter(CheckerboardBiomeProvider::lambda$static$0), ((MapCodec)Codec.intRange(0, 62).fieldOf("scale")).orElse(2).forGetter(CheckerboardBiomeProvider::lambda$static$1)).apply(instance, CheckerboardBiomeProvider::new);
    }

    private static Integer lambda$static$1(CheckerboardBiomeProvider checkerboardBiomeProvider) {
        return checkerboardBiomeProvider.biomeScale;
    }

    private static List lambda$static$0(CheckerboardBiomeProvider checkerboardBiomeProvider) {
        return checkerboardBiomeProvider.biomes;
    }
}

