/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKeyCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

public class StructureFeature<FC extends IFeatureConfig, F extends Structure<FC>> {
    public static final Codec<StructureFeature<?, ?>> field_236267_a_ = Registry.STRUCTURE_FEATURE.dispatch(StructureFeature::lambda$static$0, Structure::func_236398_h_);
    public static final Codec<Supplier<StructureFeature<?, ?>>> field_244391_b_ = RegistryKeyCodec.create(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, field_236267_a_);
    public static final Codec<List<Supplier<StructureFeature<?, ?>>>> field_242770_c = RegistryKeyCodec.getValueCodecs(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, field_236267_a_);
    public final F field_236268_b_;
    public final FC field_236269_c_;

    public StructureFeature(F f, FC FC) {
        this.field_236268_b_ = f;
        this.field_236269_c_ = FC;
    }

    public StructureStart<?> func_242771_a(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, TemplateManager templateManager, long l, ChunkPos chunkPos, Biome biome, int n, StructureSeparationSettings structureSeparationSettings) {
        return ((Structure)this.field_236268_b_).func_242785_a(dynamicRegistries, chunkGenerator, biomeProvider, templateManager, l, chunkPos, biome, n, new SharedSeedRandom(), structureSeparationSettings, this.field_236269_c_);
    }

    private static Structure lambda$static$0(StructureFeature structureFeature) {
        return structureFeature.field_236268_b_;
    }
}

