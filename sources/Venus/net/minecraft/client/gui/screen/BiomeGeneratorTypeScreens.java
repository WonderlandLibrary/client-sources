/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.client.gui.screen.CreateBuffetWorldScreen;
import net.minecraft.client.gui.screen.CreateFlatWorldScreen;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.biome.provider.SingleBiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DebugChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;

public abstract class BiomeGeneratorTypeScreens {
    public static final BiomeGeneratorTypeScreens field_239066_a_ = new BiomeGeneratorTypeScreens("default"){

        @Override
        protected ChunkGenerator func_241869_a(Registry<Biome> registry, Registry<DimensionSettings> registry2, long l) {
            return new NoiseChunkGenerator(new OverworldBiomeProvider(l, false, false, registry), l, () -> 1.lambda$func_241869_a$0(registry2));
        }

        private static DimensionSettings lambda$func_241869_a$0(Registry registry) {
            return registry.getOrThrow(DimensionSettings.field_242734_c);
        }
    };
    private static final BiomeGeneratorTypeScreens field_239070_e_ = new BiomeGeneratorTypeScreens("flat"){

        @Override
        protected ChunkGenerator func_241869_a(Registry<Biome> registry, Registry<DimensionSettings> registry2, long l) {
            return new FlatChunkGenerator(FlatGenerationSettings.func_242869_a(registry));
        }
    };
    private static final BiomeGeneratorTypeScreens field_239071_f_ = new BiomeGeneratorTypeScreens("large_biomes"){

        @Override
        protected ChunkGenerator func_241869_a(Registry<Biome> registry, Registry<DimensionSettings> registry2, long l) {
            return new NoiseChunkGenerator(new OverworldBiomeProvider(l, false, true, registry), l, () -> 3.lambda$func_241869_a$0(registry2));
        }

        private static DimensionSettings lambda$func_241869_a$0(Registry registry) {
            return registry.getOrThrow(DimensionSettings.field_242734_c);
        }
    };
    public static final BiomeGeneratorTypeScreens field_239067_b_ = new BiomeGeneratorTypeScreens("amplified"){

        @Override
        protected ChunkGenerator func_241869_a(Registry<Biome> registry, Registry<DimensionSettings> registry2, long l) {
            return new NoiseChunkGenerator(new OverworldBiomeProvider(l, false, false, registry), l, () -> 4.lambda$func_241869_a$0(registry2));
        }

        private static DimensionSettings lambda$func_241869_a$0(Registry registry) {
            return registry.getOrThrow(DimensionSettings.field_242735_d);
        }
    };
    private static final BiomeGeneratorTypeScreens field_239072_g_ = new BiomeGeneratorTypeScreens("single_biome_surface"){

        @Override
        protected ChunkGenerator func_241869_a(Registry<Biome> registry, Registry<DimensionSettings> registry2, long l) {
            return new NoiseChunkGenerator(new SingleBiomeProvider(registry.getOrThrow(Biomes.PLAINS)), l, () -> 5.lambda$func_241869_a$0(registry2));
        }

        private static DimensionSettings lambda$func_241869_a$0(Registry registry) {
            return registry.getOrThrow(DimensionSettings.field_242734_c);
        }
    };
    private static final BiomeGeneratorTypeScreens field_239073_h_ = new BiomeGeneratorTypeScreens("single_biome_caves"){

        @Override
        public DimensionGeneratorSettings func_241220_a_(DynamicRegistries.Impl impl, long l, boolean bl, boolean bl2) {
            MutableRegistry<Biome> mutableRegistry = impl.getRegistry(Registry.BIOME_KEY);
            MutableRegistry<DimensionType> mutableRegistry2 = impl.getRegistry(Registry.DIMENSION_TYPE_KEY);
            MutableRegistry<DimensionSettings> mutableRegistry3 = impl.getRegistry(Registry.NOISE_SETTINGS_KEY);
            return new DimensionGeneratorSettings(l, bl, bl2, DimensionGeneratorSettings.func_241520_a_(DimensionType.getDefaultSimpleRegistry(mutableRegistry2, mutableRegistry, mutableRegistry3, l), () -> 6.lambda$func_241220_a_$0(mutableRegistry2), this.func_241869_a(mutableRegistry, mutableRegistry3, l)));
        }

        @Override
        protected ChunkGenerator func_241869_a(Registry<Biome> registry, Registry<DimensionSettings> registry2, long l) {
            return new NoiseChunkGenerator(new SingleBiomeProvider(registry.getOrThrow(Biomes.PLAINS)), l, () -> 6.lambda$func_241869_a$1(registry2));
        }

        private static DimensionSettings lambda$func_241869_a$1(Registry registry) {
            return registry.getOrThrow(DimensionSettings.field_242738_g);
        }

        private static DimensionType lambda$func_241220_a_$0(Registry registry) {
            return registry.getOrThrow(DimensionType.OVERWORLD_CAVES);
        }
    };
    private static final BiomeGeneratorTypeScreens field_239074_i_ = new BiomeGeneratorTypeScreens("single_biome_floating_islands"){

        @Override
        protected ChunkGenerator func_241869_a(Registry<Biome> registry, Registry<DimensionSettings> registry2, long l) {
            return new NoiseChunkGenerator(new SingleBiomeProvider(registry.getOrThrow(Biomes.PLAINS)), l, () -> 7.lambda$func_241869_a$0(registry2));
        }

        private static DimensionSettings lambda$func_241869_a$0(Registry registry) {
            return registry.getOrThrow(DimensionSettings.field_242739_h);
        }
    };
    private static final BiomeGeneratorTypeScreens field_239075_j_ = new BiomeGeneratorTypeScreens("debug_all_block_states"){

        @Override
        protected ChunkGenerator func_241869_a(Registry<Biome> registry, Registry<DimensionSettings> registry2, long l) {
            return new DebugChunkGenerator(registry);
        }
    };
    protected static final List<BiomeGeneratorTypeScreens> field_239068_c_ = Lists.newArrayList(field_239066_a_, field_239070_e_, field_239071_f_, field_239067_b_, field_239072_g_, field_239073_h_, field_239074_i_, field_239075_j_);
    protected static final Map<Optional<BiomeGeneratorTypeScreens>, IFactory> field_239069_d_ = ImmutableMap.of(Optional.of(field_239070_e_), BiomeGeneratorTypeScreens::lambda$static$1, Optional.of(field_239072_g_), BiomeGeneratorTypeScreens::lambda$static$3, Optional.of(field_239073_h_), BiomeGeneratorTypeScreens::lambda$static$5, Optional.of(field_239074_i_), BiomeGeneratorTypeScreens::lambda$static$7);
    private final ITextComponent field_239076_k_;

    private BiomeGeneratorTypeScreens(String string) {
        this.field_239076_k_ = new TranslationTextComponent("generator." + string);
    }

    private static DimensionGeneratorSettings func_243452_a(DynamicRegistries dynamicRegistries, DimensionGeneratorSettings dimensionGeneratorSettings, BiomeGeneratorTypeScreens biomeGeneratorTypeScreens, Biome biome) {
        SingleBiomeProvider singleBiomeProvider = new SingleBiomeProvider(biome);
        MutableRegistry<DimensionType> mutableRegistry = dynamicRegistries.getRegistry(Registry.DIMENSION_TYPE_KEY);
        MutableRegistry<DimensionSettings> mutableRegistry2 = dynamicRegistries.getRegistry(Registry.NOISE_SETTINGS_KEY);
        Supplier<DimensionSettings> supplier = biomeGeneratorTypeScreens == field_239073_h_ ? () -> BiomeGeneratorTypeScreens.lambda$func_243452_a$8(mutableRegistry2) : (biomeGeneratorTypeScreens == field_239074_i_ ? () -> BiomeGeneratorTypeScreens.lambda$func_243452_a$9(mutableRegistry2) : () -> BiomeGeneratorTypeScreens.lambda$func_243452_a$10(mutableRegistry2));
        return new DimensionGeneratorSettings(dimensionGeneratorSettings.getSeed(), dimensionGeneratorSettings.doesGenerateFeatures(), dimensionGeneratorSettings.hasBonusChest(), DimensionGeneratorSettings.func_242749_a(mutableRegistry, dimensionGeneratorSettings.func_236224_e_(), new NoiseChunkGenerator(singleBiomeProvider, dimensionGeneratorSettings.getSeed(), supplier)));
    }

    private static Biome func_243451_a(DynamicRegistries dynamicRegistries, DimensionGeneratorSettings dimensionGeneratorSettings) {
        return dimensionGeneratorSettings.func_236225_f_().getBiomeProvider().getBiomes().stream().findFirst().orElse(dynamicRegistries.getRegistry(Registry.BIOME_KEY).getOrThrow(Biomes.PLAINS));
    }

    public static Optional<BiomeGeneratorTypeScreens> func_239079_a_(DimensionGeneratorSettings dimensionGeneratorSettings) {
        ChunkGenerator chunkGenerator = dimensionGeneratorSettings.func_236225_f_();
        if (chunkGenerator instanceof FlatChunkGenerator) {
            return Optional.of(field_239070_e_);
        }
        return chunkGenerator instanceof DebugChunkGenerator ? Optional.of(field_239075_j_) : Optional.empty();
    }

    public ITextComponent func_239077_a_() {
        return this.field_239076_k_;
    }

    public DimensionGeneratorSettings func_241220_a_(DynamicRegistries.Impl impl, long l, boolean bl, boolean bl2) {
        MutableRegistry<Biome> mutableRegistry = impl.getRegistry(Registry.BIOME_KEY);
        MutableRegistry<DimensionType> mutableRegistry2 = impl.getRegistry(Registry.DIMENSION_TYPE_KEY);
        MutableRegistry<DimensionSettings> mutableRegistry3 = impl.getRegistry(Registry.NOISE_SETTINGS_KEY);
        return new DimensionGeneratorSettings(l, bl, bl2, DimensionGeneratorSettings.func_242749_a(mutableRegistry2, DimensionType.getDefaultSimpleRegistry(mutableRegistry2, mutableRegistry, mutableRegistry3, l), this.func_241869_a(mutableRegistry, mutableRegistry3, l)));
    }

    protected abstract ChunkGenerator func_241869_a(Registry<Biome> var1, Registry<DimensionSettings> var2, long var3);

    private static DimensionSettings lambda$func_243452_a$10(Registry registry) {
        return registry.getOrThrow(DimensionSettings.field_242734_c);
    }

    private static DimensionSettings lambda$func_243452_a$9(Registry registry) {
        return registry.getOrThrow(DimensionSettings.field_242739_h);
    }

    private static DimensionSettings lambda$func_243452_a$8(Registry registry) {
        return registry.getOrThrow(DimensionSettings.field_242738_g);
    }

    private static Screen lambda$static$7(CreateWorldScreen createWorldScreen, DimensionGeneratorSettings dimensionGeneratorSettings) {
        return new CreateBuffetWorldScreen(createWorldScreen, createWorldScreen.field_238934_c_.func_239055_b_(), arg_0 -> BiomeGeneratorTypeScreens.lambda$static$6(createWorldScreen, dimensionGeneratorSettings, arg_0), BiomeGeneratorTypeScreens.func_243451_a(createWorldScreen.field_238934_c_.func_239055_b_(), dimensionGeneratorSettings));
    }

    private static void lambda$static$6(CreateWorldScreen createWorldScreen, DimensionGeneratorSettings dimensionGeneratorSettings, Biome biome) {
        createWorldScreen.field_238934_c_.func_239043_a_(BiomeGeneratorTypeScreens.func_243452_a(createWorldScreen.field_238934_c_.func_239055_b_(), dimensionGeneratorSettings, field_239074_i_, biome));
    }

    private static Screen lambda$static$5(CreateWorldScreen createWorldScreen, DimensionGeneratorSettings dimensionGeneratorSettings) {
        return new CreateBuffetWorldScreen(createWorldScreen, createWorldScreen.field_238934_c_.func_239055_b_(), arg_0 -> BiomeGeneratorTypeScreens.lambda$static$4(createWorldScreen, dimensionGeneratorSettings, arg_0), BiomeGeneratorTypeScreens.func_243451_a(createWorldScreen.field_238934_c_.func_239055_b_(), dimensionGeneratorSettings));
    }

    private static void lambda$static$4(CreateWorldScreen createWorldScreen, DimensionGeneratorSettings dimensionGeneratorSettings, Biome biome) {
        createWorldScreen.field_238934_c_.func_239043_a_(BiomeGeneratorTypeScreens.func_243452_a(createWorldScreen.field_238934_c_.func_239055_b_(), dimensionGeneratorSettings, field_239073_h_, biome));
    }

    private static Screen lambda$static$3(CreateWorldScreen createWorldScreen, DimensionGeneratorSettings dimensionGeneratorSettings) {
        return new CreateBuffetWorldScreen(createWorldScreen, createWorldScreen.field_238934_c_.func_239055_b_(), arg_0 -> BiomeGeneratorTypeScreens.lambda$static$2(createWorldScreen, dimensionGeneratorSettings, arg_0), BiomeGeneratorTypeScreens.func_243451_a(createWorldScreen.field_238934_c_.func_239055_b_(), dimensionGeneratorSettings));
    }

    private static void lambda$static$2(CreateWorldScreen createWorldScreen, DimensionGeneratorSettings dimensionGeneratorSettings, Biome biome) {
        createWorldScreen.field_238934_c_.func_239043_a_(BiomeGeneratorTypeScreens.func_243452_a(createWorldScreen.field_238934_c_.func_239055_b_(), dimensionGeneratorSettings, field_239072_g_, biome));
    }

    private static Screen lambda$static$1(CreateWorldScreen createWorldScreen, DimensionGeneratorSettings dimensionGeneratorSettings) {
        ChunkGenerator chunkGenerator = dimensionGeneratorSettings.func_236225_f_();
        return new CreateFlatWorldScreen(createWorldScreen, arg_0 -> BiomeGeneratorTypeScreens.lambda$static$0(createWorldScreen, dimensionGeneratorSettings, arg_0), chunkGenerator instanceof FlatChunkGenerator ? ((FlatChunkGenerator)chunkGenerator).func_236073_g_() : FlatGenerationSettings.func_242869_a(createWorldScreen.field_238934_c_.func_239055_b_().getRegistry(Registry.BIOME_KEY)));
    }

    private static void lambda$static$0(CreateWorldScreen createWorldScreen, DimensionGeneratorSettings dimensionGeneratorSettings, FlatGenerationSettings flatGenerationSettings) {
        createWorldScreen.field_238934_c_.func_239043_a_(new DimensionGeneratorSettings(dimensionGeneratorSettings.getSeed(), dimensionGeneratorSettings.doesGenerateFeatures(), dimensionGeneratorSettings.hasBonusChest(), DimensionGeneratorSettings.func_242749_a(createWorldScreen.field_238934_c_.func_239055_b_().getRegistry(Registry.DIMENSION_TYPE_KEY), dimensionGeneratorSettings.func_236224_e_(), new FlatChunkGenerator(flatGenerationSettings))));
    }

    public static interface IFactory {
        public Screen createEditScreen(CreateWorldScreen var1, DimensionGeneratorSettings var2);
    }
}

