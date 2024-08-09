/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.settings;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Properties;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DebugChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DimensionGeneratorSettings {
    public static final Codec<DimensionGeneratorSettings> field_236201_a_ = RecordCodecBuilder.create(DimensionGeneratorSettings::lambda$static$1).comapFlatMap(DimensionGeneratorSettings::func_236233_n_, Function.identity());
    private static final Logger LOGGER = LogManager.getLogger();
    private final long seed;
    private final boolean generateFeatures;
    private final boolean bonusChest;
    private final SimpleRegistry<Dimension> field_236208_h_;
    private final Optional<String> field_236209_i_;

    private DataResult<DimensionGeneratorSettings> func_236233_n_() {
        Dimension dimension = this.field_236208_h_.getValueForKey(Dimension.OVERWORLD);
        if (dimension == null) {
            return DataResult.error("Overworld settings missing");
        }
        return this.func_236234_o_() ? DataResult.success(this, Lifecycle.stable()) : DataResult.success(this);
    }

    private boolean func_236234_o_() {
        return Dimension.func_236060_a_(this.seed, this.field_236208_h_);
    }

    public DimensionGeneratorSettings(long l, boolean bl, boolean bl2, SimpleRegistry<Dimension> simpleRegistry) {
        this(l, bl, bl2, simpleRegistry, Optional.empty());
        Dimension dimension = simpleRegistry.getValueForKey(Dimension.OVERWORLD);
        if (dimension == null) {
            throw new IllegalStateException("Overworld settings missing");
        }
    }

    private DimensionGeneratorSettings(long l, boolean bl, boolean bl2, SimpleRegistry<Dimension> simpleRegistry, Optional<String> optional) {
        this.seed = l;
        this.generateFeatures = bl;
        this.bonusChest = bl2;
        this.field_236208_h_ = simpleRegistry;
        this.field_236209_i_ = optional;
    }

    public static DimensionGeneratorSettings func_242752_a(DynamicRegistries dynamicRegistries) {
        MutableRegistry<Biome> mutableRegistry = dynamicRegistries.getRegistry(Registry.BIOME_KEY);
        int n = "North Carolina".hashCode();
        MutableRegistry<DimensionType> mutableRegistry2 = dynamicRegistries.getRegistry(Registry.DIMENSION_TYPE_KEY);
        MutableRegistry<DimensionSettings> mutableRegistry3 = dynamicRegistries.getRegistry(Registry.NOISE_SETTINGS_KEY);
        return new DimensionGeneratorSettings(n, true, true, DimensionGeneratorSettings.func_242749_a(mutableRegistry2, DimensionType.getDefaultSimpleRegistry(mutableRegistry2, mutableRegistry, mutableRegistry3, n), DimensionGeneratorSettings.func_242750_a(mutableRegistry, mutableRegistry3, n)));
    }

    public static DimensionGeneratorSettings func_242751_a(Registry<DimensionType> registry, Registry<Biome> registry2, Registry<DimensionSettings> registry3) {
        long l = new Random().nextLong();
        return new DimensionGeneratorSettings(l, true, false, DimensionGeneratorSettings.func_242749_a(registry, DimensionType.getDefaultSimpleRegistry(registry, registry2, registry3, l), DimensionGeneratorSettings.func_242750_a(registry2, registry3, l)));
    }

    public static NoiseChunkGenerator func_242750_a(Registry<Biome> registry, Registry<DimensionSettings> registry2, long l) {
        return new NoiseChunkGenerator(new OverworldBiomeProvider(l, false, false, registry), l, () -> DimensionGeneratorSettings.lambda$func_242750_a$2(registry2));
    }

    public long getSeed() {
        return this.seed;
    }

    public boolean doesGenerateFeatures() {
        return this.generateFeatures;
    }

    public boolean hasBonusChest() {
        return this.bonusChest;
    }

    public static SimpleRegistry<Dimension> func_242749_a(Registry<DimensionType> registry, SimpleRegistry<Dimension> simpleRegistry, ChunkGenerator chunkGenerator) {
        Dimension dimension = simpleRegistry.getValueForKey(Dimension.OVERWORLD);
        Supplier<DimensionType> supplier = () -> DimensionGeneratorSettings.lambda$func_242749_a$3(dimension, registry);
        return DimensionGeneratorSettings.func_241520_a_(simpleRegistry, supplier, chunkGenerator);
    }

    public static SimpleRegistry<Dimension> func_241520_a_(SimpleRegistry<Dimension> simpleRegistry, Supplier<DimensionType> supplier, ChunkGenerator chunkGenerator) {
        SimpleRegistry<Dimension> simpleRegistry2 = new SimpleRegistry<Dimension>(Registry.DIMENSION_KEY, Lifecycle.experimental());
        simpleRegistry2.register(Dimension.OVERWORLD, new Dimension(supplier, chunkGenerator), Lifecycle.stable());
        for (Map.Entry<RegistryKey<Dimension>, Dimension> entry : simpleRegistry.getEntries()) {
            RegistryKey<Dimension> registryKey = entry.getKey();
            if (registryKey == Dimension.OVERWORLD) continue;
            simpleRegistry2.register(registryKey, entry.getValue(), simpleRegistry.getLifecycleByRegistry(entry.getValue()));
        }
        return simpleRegistry2;
    }

    public SimpleRegistry<Dimension> func_236224_e_() {
        return this.field_236208_h_;
    }

    public ChunkGenerator func_236225_f_() {
        Dimension dimension = this.field_236208_h_.getValueForKey(Dimension.OVERWORLD);
        if (dimension == null) {
            throw new IllegalStateException("Overworld settings missing");
        }
        return dimension.getChunkGenerator();
    }

    public ImmutableSet<RegistryKey<World>> func_236226_g_() {
        return this.func_236224_e_().getEntries().stream().map(DimensionGeneratorSettings::lambda$func_236226_g_$4).collect(ImmutableSet.toImmutableSet());
    }

    public boolean func_236227_h_() {
        return this.func_236225_f_() instanceof DebugChunkGenerator;
    }

    public boolean func_236228_i_() {
        return this.func_236225_f_() instanceof FlatChunkGenerator;
    }

    public boolean func_236229_j_() {
        return this.field_236209_i_.isPresent();
    }

    public DimensionGeneratorSettings func_236230_k_() {
        return new DimensionGeneratorSettings(this.seed, this.generateFeatures, true, this.field_236208_h_, this.field_236209_i_);
    }

    public DimensionGeneratorSettings func_236231_l_() {
        return new DimensionGeneratorSettings(this.seed, !this.generateFeatures, this.bonusChest, this.field_236208_h_);
    }

    public DimensionGeneratorSettings func_236232_m_() {
        return new DimensionGeneratorSettings(this.seed, this.generateFeatures, !this.bonusChest, this.field_236208_h_);
    }

    public static DimensionGeneratorSettings func_242753_a(DynamicRegistries dynamicRegistries, Properties properties) {
        String string = MoreObjects.firstNonNull((String)properties.get("generator-settings"), "");
        properties.put("generator-settings", string);
        String string2 = MoreObjects.firstNonNull((String)properties.get("level-seed"), "");
        properties.put("level-seed", string2);
        String string3 = (String)properties.get("generate-structures");
        boolean bl = string3 == null || Boolean.parseBoolean(string3);
        properties.put("generate-structures", Objects.toString(bl));
        String string4 = (String)properties.get("level-type");
        String string5 = Optional.ofNullable(string4).map(DimensionGeneratorSettings::lambda$func_242753_a$5).orElse("default");
        properties.put("level-type", string5);
        long l = new Random().nextLong();
        if (!string2.isEmpty()) {
            try {
                long l2 = Long.parseLong(string2);
                if (l2 != 0L) {
                    l = l2;
                }
            } catch (NumberFormatException numberFormatException) {
                l = string2.hashCode();
            }
        }
        MutableRegistry<DimensionType> mutableRegistry = dynamicRegistries.getRegistry(Registry.DIMENSION_TYPE_KEY);
        MutableRegistry<Biome> mutableRegistry2 = dynamicRegistries.getRegistry(Registry.BIOME_KEY);
        MutableRegistry<DimensionSettings> mutableRegistry3 = dynamicRegistries.getRegistry(Registry.NOISE_SETTINGS_KEY);
        SimpleRegistry<Dimension> simpleRegistry = DimensionType.getDefaultSimpleRegistry(mutableRegistry, mutableRegistry2, mutableRegistry3, l);
        int n = -1;
        switch (string5.hashCode()) {
            case -1100099890: {
                if (!string5.equals("largebiomes")) break;
                n = 3;
                break;
            }
            case 3145593: {
                if (!string5.equals("flat")) break;
                n = 0;
                break;
            }
            case 1045526590: {
                if (!string5.equals("debug_all_block_states")) break;
                n = 1;
                break;
            }
            case 1271599715: {
                if (!string5.equals("amplified")) break;
                n = 2;
            }
        }
        switch (n) {
            case 0: {
                JsonObject jsonObject = !string.isEmpty() ? JSONUtils.fromJson(string) : new JsonObject();
                Dynamic<JsonObject> dynamic = new Dynamic<JsonObject>(JsonOps.INSTANCE, jsonObject);
                return new DimensionGeneratorSettings(l, bl, false, DimensionGeneratorSettings.func_242749_a(mutableRegistry, simpleRegistry, new FlatChunkGenerator(FlatGenerationSettings.field_236932_a_.parse(dynamic).resultOrPartial(LOGGER::error).orElseGet(() -> DimensionGeneratorSettings.lambda$func_242753_a$6(mutableRegistry2)))));
            }
            case 1: {
                return new DimensionGeneratorSettings(l, bl, false, DimensionGeneratorSettings.func_242749_a(mutableRegistry, simpleRegistry, new DebugChunkGenerator(mutableRegistry2)));
            }
            case 2: {
                return new DimensionGeneratorSettings(l, bl, false, DimensionGeneratorSettings.func_242749_a(mutableRegistry, simpleRegistry, new NoiseChunkGenerator(new OverworldBiomeProvider(l, false, false, mutableRegistry2), l, () -> DimensionGeneratorSettings.lambda$func_242753_a$7(mutableRegistry3))));
            }
            case 3: {
                return new DimensionGeneratorSettings(l, bl, false, DimensionGeneratorSettings.func_242749_a(mutableRegistry, simpleRegistry, new NoiseChunkGenerator(new OverworldBiomeProvider(l, false, true, mutableRegistry2), l, () -> DimensionGeneratorSettings.lambda$func_242753_a$8(mutableRegistry3))));
            }
        }
        return new DimensionGeneratorSettings(l, bl, false, DimensionGeneratorSettings.func_242749_a(mutableRegistry, simpleRegistry, DimensionGeneratorSettings.func_242750_a(mutableRegistry2, mutableRegistry3, l)));
    }

    public DimensionGeneratorSettings create(boolean bl, OptionalLong optionalLong) {
        SimpleRegistry<Dimension> simpleRegistry;
        long l = optionalLong.orElse(this.seed);
        if (optionalLong.isPresent()) {
            simpleRegistry = new SimpleRegistry<Dimension>(Registry.DIMENSION_KEY, Lifecycle.experimental());
            long l2 = optionalLong.getAsLong();
            for (Map.Entry<RegistryKey<Dimension>, Dimension> entry : this.field_236208_h_.getEntries()) {
                RegistryKey<Dimension> registryKey = entry.getKey();
                simpleRegistry.register(registryKey, new Dimension(entry.getValue().getDimensionTypeSupplier(), entry.getValue().getChunkGenerator().func_230349_a_(l2)), this.field_236208_h_.getLifecycleByRegistry(entry.getValue()));
            }
        } else {
            simpleRegistry = this.field_236208_h_;
        }
        DimensionGeneratorSettings dimensionGeneratorSettings = this.func_236227_h_() ? new DimensionGeneratorSettings(l, false, false, simpleRegistry) : new DimensionGeneratorSettings(l, this.doesGenerateFeatures(), this.hasBonusChest() && !bl, simpleRegistry);
        return dimensionGeneratorSettings;
    }

    private static DimensionSettings lambda$func_242753_a$8(Registry registry) {
        return registry.getOrThrow(DimensionSettings.field_242734_c);
    }

    private static DimensionSettings lambda$func_242753_a$7(Registry registry) {
        return registry.getOrThrow(DimensionSettings.field_242735_d);
    }

    private static FlatGenerationSettings lambda$func_242753_a$6(Registry registry) {
        return FlatGenerationSettings.func_242869_a(registry);
    }

    private static String lambda$func_242753_a$5(String string) {
        return string.toLowerCase(Locale.ROOT);
    }

    private static RegistryKey lambda$func_236226_g_$4(Map.Entry entry) {
        return RegistryKey.getOrCreateKey(Registry.WORLD_KEY, ((RegistryKey)entry.getKey()).getLocation());
    }

    private static DimensionType lambda$func_242749_a$3(Dimension dimension, Registry registry) {
        return dimension == null ? registry.getOrThrow(DimensionType.OVERWORLD) : dimension.getDimensionType();
    }

    private static DimensionSettings lambda$func_242750_a$2(Registry registry) {
        return registry.getOrThrow(DimensionSettings.field_242734_c);
    }

    private static App lambda$static$1(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.LONG.fieldOf("seed")).stable().forGetter(DimensionGeneratorSettings::getSeed), ((MapCodec)Codec.BOOL.fieldOf("generate_features")).orElse(true).stable().forGetter(DimensionGeneratorSettings::doesGenerateFeatures), ((MapCodec)Codec.BOOL.fieldOf("bonus_chest")).orElse(false).stable().forGetter(DimensionGeneratorSettings::hasBonusChest), ((MapCodec)SimpleRegistry.getSimpleRegistryCodec(Registry.DIMENSION_KEY, Lifecycle.stable(), Dimension.CODEC).xmap(Dimension::func_236062_a_, Function.identity()).fieldOf("dimensions")).forGetter(DimensionGeneratorSettings::func_236224_e_), Codec.STRING.optionalFieldOf("legacy_custom_options").stable().forGetter(DimensionGeneratorSettings::lambda$static$0)).apply(instance, instance.stable(DimensionGeneratorSettings::new));
    }

    private static Optional lambda$static$0(DimensionGeneratorSettings dimensionGeneratorSettings) {
        return dimensionGeneratorSettings.field_236209_i_;
    }
}

