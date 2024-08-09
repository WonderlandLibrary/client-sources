/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ReportedException;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKeyCodec;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.biome.ParticleEffectAmbience;
import net.minecraft.world.biome.SoundAdditionsAmbience;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Biome {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Codec<Biome> CODEC = RecordCodecBuilder.create(Biome::lambda$static$7);
    public static final Codec<Biome> PACKET_CODEC = RecordCodecBuilder.create(Biome::lambda$static$14);
    public static final Codec<Supplier<Biome>> BIOME_CODEC = RegistryKeyCodec.create(Registry.BIOME_KEY, CODEC);
    public static final Codec<List<Supplier<Biome>>> BIOMES_CODEC = RegistryKeyCodec.getValueCodecs(Registry.BIOME_KEY, CODEC);
    private final Map<Integer, List<Structure<?>>> biomeStructures = Registry.STRUCTURE_FEATURE.stream().collect(Collectors.groupingBy(Biome::lambda$new$15));
    private static final PerlinNoiseGenerator TEMPERATURE_NOISE = new PerlinNoiseGenerator(new SharedSeedRandom(1234L), ImmutableList.of(0));
    private static final PerlinNoiseGenerator FROZEN_TEMPERATURE_NOISE = new PerlinNoiseGenerator(new SharedSeedRandom(3456L), ImmutableList.of(-2, -1, 0));
    public static final PerlinNoiseGenerator INFO_NOISE = new PerlinNoiseGenerator(new SharedSeedRandom(2345L), ImmutableList.of(0));
    private final Climate climate;
    private final BiomeGenerationSettings biomeGenerationSettings;
    private final MobSpawnInfo mobSpawnInfo;
    private final float depth;
    private final float scale;
    private final Category category;
    private final BiomeAmbience effects;
    private final ThreadLocal<Long2FloatLinkedOpenHashMap> temperatureCache = ThreadLocal.withInitial(this::lambda$new$17);

    private Biome(Climate climate, Category category, float f, float f2, BiomeAmbience biomeAmbience, BiomeGenerationSettings biomeGenerationSettings, MobSpawnInfo mobSpawnInfo) {
        this.climate = climate;
        this.biomeGenerationSettings = biomeGenerationSettings;
        this.mobSpawnInfo = mobSpawnInfo;
        this.category = category;
        this.depth = f;
        this.scale = f2;
        this.effects = biomeAmbience;
    }

    public int getSkyColor() {
        return this.effects.getSkyColor();
    }

    public MobSpawnInfo getMobSpawnInfo() {
        return this.mobSpawnInfo;
    }

    public RainType getPrecipitation() {
        return this.climate.precipitation;
    }

    public boolean isHighHumidity() {
        return this.getDownfall() > 0.85f;
    }

    private float getTemperatureAtPosition(BlockPos blockPos) {
        float f = this.climate.temperatureModifier.getTemperatureAtPosition(blockPos, this.getTemperature());
        if (blockPos.getY() > 64) {
            float f2 = (float)(TEMPERATURE_NOISE.noiseAt((float)blockPos.getX() / 8.0f, (float)blockPos.getZ() / 8.0f, true) * 4.0);
            return f - (f2 + (float)blockPos.getY() - 64.0f) * 0.05f / 30.0f;
        }
        return f;
    }

    public final float getTemperature(BlockPos blockPos) {
        long l = blockPos.toLong();
        Long2FloatLinkedOpenHashMap long2FloatLinkedOpenHashMap = this.temperatureCache.get();
        float f = long2FloatLinkedOpenHashMap.get(l);
        if (!Float.isNaN(f)) {
            return f;
        }
        float f2 = this.getTemperatureAtPosition(blockPos);
        if (long2FloatLinkedOpenHashMap.size() == 1024) {
            long2FloatLinkedOpenHashMap.removeFirstFloat();
        }
        long2FloatLinkedOpenHashMap.put(l, f2);
        return f2;
    }

    public boolean doesWaterFreeze(IWorldReader iWorldReader, BlockPos blockPos) {
        return this.doesWaterFreeze(iWorldReader, blockPos, false);
    }

    public boolean doesWaterFreeze(IWorldReader iWorldReader, BlockPos blockPos, boolean bl) {
        if (this.getTemperature(blockPos) >= 0.15f) {
            return true;
        }
        if (blockPos.getY() >= 0 && blockPos.getY() < 256 && iWorldReader.getLightFor(LightType.BLOCK, blockPos) < 10) {
            BlockState blockState = iWorldReader.getBlockState(blockPos);
            FluidState fluidState = iWorldReader.getFluidState(blockPos);
            if (fluidState.getFluid() == Fluids.WATER && blockState.getBlock() instanceof FlowingFluidBlock) {
                boolean bl2;
                if (!bl) {
                    return false;
                }
                boolean bl3 = bl2 = iWorldReader.hasWater(blockPos.west()) && iWorldReader.hasWater(blockPos.east()) && iWorldReader.hasWater(blockPos.north()) && iWorldReader.hasWater(blockPos.south());
                if (!bl2) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean doesSnowGenerate(IWorldReader iWorldReader, BlockPos blockPos) {
        BlockState blockState;
        if (this.getTemperature(blockPos) >= 0.15f) {
            return true;
        }
        return blockPos.getY() < 0 || blockPos.getY() >= 256 || iWorldReader.getLightFor(LightType.BLOCK, blockPos) >= 10 || !(blockState = iWorldReader.getBlockState(blockPos)).isAir() || !Blocks.SNOW.getDefaultState().isValidPosition(iWorldReader, blockPos);
    }

    public BiomeGenerationSettings getGenerationSettings() {
        return this.biomeGenerationSettings;
    }

    public void generateFeatures(StructureManager structureManager, ChunkGenerator chunkGenerator, WorldGenRegion worldGenRegion, long l, SharedSeedRandom sharedSeedRandom, BlockPos blockPos) {
        List<List<Supplier<ConfiguredFeature<?, ?>>>> list = this.biomeGenerationSettings.getFeatures();
        int n = GenerationStage.Decoration.values().length;
        for (int i = 0; i < n; ++i) {
            int n2 = 0;
            if (structureManager.func_235005_a_()) {
                for (Structure structure : this.biomeStructures.getOrDefault(i, Collections.emptyList())) {
                    sharedSeedRandom.setFeatureSeed(l, n2, i);
                    int n3 = blockPos.getX() >> 4;
                    int n4 = blockPos.getZ() >> 4;
                    int n5 = n3 << 4;
                    int n6 = n4 << 4;
                    try {
                        structureManager.func_235011_a_(SectionPos.from(blockPos), structure).forEach(arg_0 -> Biome.lambda$generateFeatures$18(worldGenRegion, structureManager, chunkGenerator, sharedSeedRandom, n5, n6, n3, n4, arg_0));
                    } catch (Exception exception) {
                        CrashReport crashReport = CrashReport.makeCrashReport(exception, "Feature placement");
                        crashReport.makeCategory("Feature").addDetail("Id", Registry.STRUCTURE_FEATURE.getKey(structure)).addDetail("Description", () -> Biome.lambda$generateFeatures$19(structure));
                        throw new ReportedException(crashReport);
                    }
                    ++n2;
                }
            }
            if (list.size() <= i) continue;
            for (Supplier supplier : list.get(i)) {
                ConfiguredFeature configuredFeature = (ConfiguredFeature)supplier.get();
                sharedSeedRandom.setFeatureSeed(l, n2, i);
                try {
                    configuredFeature.func_242765_a(worldGenRegion, chunkGenerator, sharedSeedRandom, blockPos);
                } catch (Exception exception) {
                    CrashReport crashReport = CrashReport.makeCrashReport(exception, "Feature placement");
                    crashReport.makeCategory("Feature").addDetail("Id", Registry.FEATURE.getKey((Feature<?>)configuredFeature.feature)).addDetail("Config", configuredFeature.config).addDetail("Description", () -> Biome.lambda$generateFeatures$20(configuredFeature));
                    throw new ReportedException(crashReport);
                }
                ++n2;
            }
        }
    }

    public int getFogColor() {
        return this.effects.getFogColor();
    }

    public int getGrassColor(double d, double d2) {
        int n = this.effects.getGrassColor().orElseGet(this::getGrassColorByClimate);
        return this.effects.getGrassColorModifier().getModifiedGrassColor(d, d2, n);
    }

    private int getGrassColorByClimate() {
        double d = MathHelper.clamp(this.climate.temperature, 0.0f, 1.0f);
        double d2 = MathHelper.clamp(this.climate.downfall, 0.0f, 1.0f);
        return GrassColors.get(d, d2);
    }

    public int getFoliageColor() {
        return this.effects.getFoliageColor().orElseGet(this::getFoliageColorByClimate);
    }

    private int getFoliageColorByClimate() {
        double d = MathHelper.clamp(this.climate.temperature, 0.0f, 1.0f);
        double d2 = MathHelper.clamp(this.climate.downfall, 0.0f, 1.0f);
        return FoliageColors.get(d, d2);
    }

    public void buildSurface(Random random2, IChunk iChunk, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l) {
        ConfiguredSurfaceBuilder<?> configuredSurfaceBuilder = this.biomeGenerationSettings.getSurfaceBuilder().get();
        configuredSurfaceBuilder.setSeed(l);
        configuredSurfaceBuilder.buildSurface(random2, iChunk, this, n, n2, n3, d, blockState, blockState2, n4, l);
    }

    public final float getDepth() {
        return this.depth;
    }

    public final float getDownfall() {
        return this.climate.downfall;
    }

    public final float getScale() {
        return this.scale;
    }

    public final float getTemperature() {
        return this.climate.temperature;
    }

    public BiomeAmbience getAmbience() {
        return this.effects;
    }

    public final int getWaterColor() {
        return this.effects.getWaterColor();
    }

    public final int getWaterFogColor() {
        return this.effects.getWaterFogColor();
    }

    public Optional<ParticleEffectAmbience> getAmbientParticle() {
        return this.effects.getParticle();
    }

    public Optional<SoundEvent> getAmbientSound() {
        return this.effects.getAmbientSound();
    }

    public Optional<MoodSoundAmbience> getMoodSound() {
        return this.effects.getMoodSound();
    }

    public Optional<SoundAdditionsAmbience> getAdditionalAmbientSound() {
        return this.effects.getAdditionsSound();
    }

    public Optional<BackgroundMusicSelector> getBackgroundMusic() {
        return this.effects.getMusic();
    }

    public final Category getCategory() {
        return this.category;
    }

    public String toString() {
        ResourceLocation resourceLocation = WorldGenRegistries.BIOME.getKey(this);
        return resourceLocation == null ? super.toString() : resourceLocation.toString();
    }

    private static String lambda$generateFeatures$20(ConfiguredFeature configuredFeature) throws Exception {
        return configuredFeature.feature.toString();
    }

    private static String lambda$generateFeatures$19(Structure structure) throws Exception {
        return structure.toString();
    }

    private static void lambda$generateFeatures$18(WorldGenRegion worldGenRegion, StructureManager structureManager, ChunkGenerator chunkGenerator, SharedSeedRandom sharedSeedRandom, int n, int n2, int n3, int n4, StructureStart structureStart) {
        structureStart.func_230366_a_(worldGenRegion, structureManager, chunkGenerator, sharedSeedRandom, new MutableBoundingBox(n, n2, n + 15, n2 + 15), new ChunkPos(n3, n4));
    }

    private Long2FloatLinkedOpenHashMap lambda$new$17() {
        return Util.make(this::lambda$new$16);
    }

    private Long2FloatLinkedOpenHashMap lambda$new$16() {
        Long2FloatLinkedOpenHashMap long2FloatLinkedOpenHashMap = new Long2FloatLinkedOpenHashMap(this, 1024, 0.25f){
            final Biome this$0;
            {
                this.this$0 = biome;
                super(n, f);
            }

            @Override
            protected void rehash(int n) {
            }
        };
        long2FloatLinkedOpenHashMap.defaultReturnValue(Float.NaN);
        return long2FloatLinkedOpenHashMap;
    }

    private static Integer lambda$new$15(Structure structure) {
        return structure.func_236396_f_().ordinal();
    }

    private static App lambda$static$14(RecordCodecBuilder.Instance instance) {
        return instance.group(Climate.CODEC.forGetter(Biome::lambda$static$8), ((MapCodec)Category.CODEC.fieldOf("category")).forGetter(Biome::lambda$static$9), ((MapCodec)Codec.FLOAT.fieldOf("depth")).forGetter(Biome::lambda$static$10), ((MapCodec)Codec.FLOAT.fieldOf("scale")).forGetter(Biome::lambda$static$11), ((MapCodec)BiomeAmbience.CODEC.fieldOf("effects")).forGetter(Biome::lambda$static$12)).apply(instance, Biome::lambda$static$13);
    }

    private static Biome lambda$static$13(Climate climate, Category category, Float f, Float f2, BiomeAmbience biomeAmbience) {
        return new Biome(climate, category, f.floatValue(), f2.floatValue(), biomeAmbience, BiomeGenerationSettings.DEFAULT_SETTINGS, MobSpawnInfo.EMPTY);
    }

    private static BiomeAmbience lambda$static$12(Biome biome) {
        return biome.effects;
    }

    private static Float lambda$static$11(Biome biome) {
        return Float.valueOf(biome.scale);
    }

    private static Float lambda$static$10(Biome biome) {
        return Float.valueOf(biome.depth);
    }

    private static Category lambda$static$9(Biome biome) {
        return biome.category;
    }

    private static Climate lambda$static$8(Biome biome) {
        return biome.climate;
    }

    private static App lambda$static$7(RecordCodecBuilder.Instance instance) {
        return instance.group(Climate.CODEC.forGetter(Biome::lambda$static$0), ((MapCodec)Category.CODEC.fieldOf("category")).forGetter(Biome::lambda$static$1), ((MapCodec)Codec.FLOAT.fieldOf("depth")).forGetter(Biome::lambda$static$2), ((MapCodec)Codec.FLOAT.fieldOf("scale")).forGetter(Biome::lambda$static$3), ((MapCodec)BiomeAmbience.CODEC.fieldOf("effects")).forGetter(Biome::lambda$static$4), BiomeGenerationSettings.CODEC.forGetter(Biome::lambda$static$5), MobSpawnInfo.CODEC.forGetter(Biome::lambda$static$6)).apply(instance, Biome::new);
    }

    private static MobSpawnInfo lambda$static$6(Biome biome) {
        return biome.mobSpawnInfo;
    }

    private static BiomeGenerationSettings lambda$static$5(Biome biome) {
        return biome.biomeGenerationSettings;
    }

    private static BiomeAmbience lambda$static$4(Biome biome) {
        return biome.effects;
    }

    private static Float lambda$static$3(Biome biome) {
        return Float.valueOf(biome.scale);
    }

    private static Float lambda$static$2(Biome biome) {
        return Float.valueOf(biome.depth);
    }

    private static Category lambda$static$1(Biome biome) {
        return biome.category;
    }

    private static Climate lambda$static$0(Biome biome) {
        return biome.climate;
    }

    static class Climate {
        public static final MapCodec<Climate> CODEC = RecordCodecBuilder.mapCodec(Climate::lambda$static$4);
        private final RainType precipitation;
        private final float temperature;
        private final TemperatureModifier temperatureModifier;
        private final float downfall;

        private Climate(RainType rainType, float f, TemperatureModifier temperatureModifier, float f2) {
            this.precipitation = rainType;
            this.temperature = f;
            this.temperatureModifier = temperatureModifier;
            this.downfall = f2;
        }

        private static App lambda$static$4(RecordCodecBuilder.Instance instance) {
            return instance.group(((MapCodec)RainType.CODEC.fieldOf("precipitation")).forGetter(Climate::lambda$static$0), ((MapCodec)Codec.FLOAT.fieldOf("temperature")).forGetter(Climate::lambda$static$1), TemperatureModifier.CODEC.optionalFieldOf("temperature_modifier", TemperatureModifier.NONE).forGetter(Climate::lambda$static$2), ((MapCodec)Codec.FLOAT.fieldOf("downfall")).forGetter(Climate::lambda$static$3)).apply(instance, Climate::new);
        }

        private static Float lambda$static$3(Climate climate) {
            return Float.valueOf(climate.downfall);
        }

        private static TemperatureModifier lambda$static$2(Climate climate) {
            return climate.temperatureModifier;
        }

        private static Float lambda$static$1(Climate climate) {
            return Float.valueOf(climate.temperature);
        }

        private static RainType lambda$static$0(Climate climate) {
            return climate.precipitation;
        }
    }

    public static enum Category implements IStringSerializable
    {
        NONE("none"),
        TAIGA("taiga"),
        EXTREME_HILLS("extreme_hills"),
        JUNGLE("jungle"),
        MESA("mesa"),
        PLAINS("plains"),
        SAVANNA("savanna"),
        ICY("icy"),
        THEEND("the_end"),
        BEACH("beach"),
        FOREST("forest"),
        OCEAN("ocean"),
        DESERT("desert"),
        RIVER("river"),
        SWAMP("swamp"),
        MUSHROOM("mushroom"),
        NETHER("nether");

        public static final Codec<Category> CODEC;
        private static final Map<String, Category> BY_NAME;
        private final String name;

        private Category(String string2) {
            this.name = string2;
        }

        public String getName() {
            return this.name;
        }

        public static Category byName(String string) {
            return BY_NAME.get(string);
        }

        @Override
        public String getString() {
            return this.name;
        }

        private static Category lambda$static$0(Category category) {
            return category;
        }

        static {
            CODEC = IStringSerializable.createEnumCodec(Category::values, Category::byName);
            BY_NAME = Arrays.stream(Category.values()).collect(Collectors.toMap(Category::getName, Category::lambda$static$0));
        }
    }

    public static enum RainType implements IStringSerializable
    {
        NONE("none"),
        RAIN("rain"),
        SNOW("snow");

        public static final Codec<RainType> CODEC;
        private static final Map<String, RainType> BY_NAME;
        private final String name;

        private RainType(String string2) {
            this.name = string2;
        }

        public String getName() {
            return this.name;
        }

        public static RainType getRainType(String string) {
            return BY_NAME.get(string);
        }

        @Override
        public String getString() {
            return this.name;
        }

        private static RainType lambda$static$0(RainType rainType) {
            return rainType;
        }

        static {
            CODEC = IStringSerializable.createEnumCodec(RainType::values, RainType::getRainType);
            BY_NAME = Arrays.stream(RainType.values()).collect(Collectors.toMap(RainType::getName, RainType::lambda$static$0));
        }
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static enum TemperatureModifier implements IStringSerializable
    {
        NONE("none"){

            @Override
            public float getTemperatureAtPosition(BlockPos blockPos, float f) {
                return f;
            }
        }
        ,
        FROZEN("frozen"){

            @Override
            public float getTemperatureAtPosition(BlockPos blockPos, float f) {
                double d;
                double d2;
                double d3 = FROZEN_TEMPERATURE_NOISE.noiseAt((double)blockPos.getX() * 0.05, (double)blockPos.getZ() * 0.05, true) * 7.0;
                double d4 = d3 + (d2 = INFO_NOISE.noiseAt((double)blockPos.getX() * 0.2, (double)blockPos.getZ() * 0.2, true));
                if (d4 < 0.3 && (d = INFO_NOISE.noiseAt((double)blockPos.getX() * 0.09, (double)blockPos.getZ() * 0.09, true)) < 0.8) {
                    return 0.2f;
                }
                return f;
            }
        };

        private final String name;
        public static final Codec<TemperatureModifier> CODEC;
        private static final Map<String, TemperatureModifier> NAME_TO_MODIFIER_MAP;

        public abstract float getTemperatureAtPosition(BlockPos var1, float var2);

        private TemperatureModifier(String string2) {
            this.name = string2;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String getString() {
            return this.name;
        }

        public static TemperatureModifier byName(String string) {
            return NAME_TO_MODIFIER_MAP.get(string);
        }

        private static TemperatureModifier lambda$static$0(TemperatureModifier temperatureModifier) {
            return temperatureModifier;
        }

        static {
            CODEC = IStringSerializable.createEnumCodec(TemperatureModifier::values, TemperatureModifier::byName);
            NAME_TO_MODIFIER_MAP = Arrays.stream(TemperatureModifier.values()).collect(Collectors.toMap(TemperatureModifier::getName, TemperatureModifier::lambda$static$0));
        }
    }

    public static class Builder {
        @Nullable
        private RainType precipitation;
        @Nullable
        private Category category;
        @Nullable
        private Float depth;
        @Nullable
        private Float scale;
        @Nullable
        private Float temperature;
        private TemperatureModifier temperatureModifier = TemperatureModifier.NONE;
        @Nullable
        private Float downfall;
        @Nullable
        private BiomeAmbience effects;
        @Nullable
        private MobSpawnInfo mobSpawnSettings;
        @Nullable
        private BiomeGenerationSettings generationSettings;

        public Builder precipitation(RainType rainType) {
            this.precipitation = rainType;
            return this;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder depth(float f) {
            this.depth = Float.valueOf(f);
            return this;
        }

        public Builder scale(float f) {
            this.scale = Float.valueOf(f);
            return this;
        }

        public Builder temperature(float f) {
            this.temperature = Float.valueOf(f);
            return this;
        }

        public Builder downfall(float f) {
            this.downfall = Float.valueOf(f);
            return this;
        }

        public Builder setEffects(BiomeAmbience biomeAmbience) {
            this.effects = biomeAmbience;
            return this;
        }

        public Builder withMobSpawnSettings(MobSpawnInfo mobSpawnInfo) {
            this.mobSpawnSettings = mobSpawnInfo;
            return this;
        }

        public Builder withGenerationSettings(BiomeGenerationSettings biomeGenerationSettings) {
            this.generationSettings = biomeGenerationSettings;
            return this;
        }

        public Builder withTemperatureModifier(TemperatureModifier temperatureModifier) {
            this.temperatureModifier = temperatureModifier;
            return this;
        }

        public Biome build() {
            if (this.precipitation != null && this.category != null && this.depth != null && this.scale != null && this.temperature != null && this.downfall != null && this.effects != null && this.mobSpawnSettings != null && this.generationSettings != null) {
                return new Biome(new Climate(this.precipitation, this.temperature.floatValue(), this.temperatureModifier, this.downfall.floatValue()), this.category, this.depth.floatValue(), this.scale.floatValue(), this.effects, this.generationSettings, this.mobSpawnSettings);
            }
            throw new IllegalStateException("You are missing parameters to build a proper biome\n" + this);
        }

        public String toString() {
            return "BiomeBuilder{\nprecipitation=" + this.precipitation + ",\nbiomeCategory=" + this.category + ",\ndepth=" + this.depth + ",\nscale=" + this.scale + ",\ntemperature=" + this.temperature + ",\ntemperatureModifier=" + this.temperatureModifier + ",\ndownfall=" + this.downfall + ",\nspecialEffects=" + this.effects + ",\nmobSpawnSettings=" + this.mobSpawnSettings + ",\ngenerationSettings=" + this.generationSettings + ",\n}";
        }
    }

    public static class Attributes {
        public static final Codec<Attributes> CODEC = RecordCodecBuilder.create(Attributes::lambda$static$5);
        private final float temperature;
        private final float humidity;
        private final float altitude;
        private final float weirdness;
        private final float offset;

        public Attributes(float f, float f2, float f3, float f4, float f5) {
            this.temperature = f;
            this.humidity = f2;
            this.altitude = f3;
            this.weirdness = f4;
            this.offset = f5;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object != null && this.getClass() == object.getClass()) {
                Attributes attributes = (Attributes)object;
                if (Float.compare(attributes.temperature, this.temperature) != 0) {
                    return true;
                }
                if (Float.compare(attributes.humidity, this.humidity) != 0) {
                    return true;
                }
                if (Float.compare(attributes.altitude, this.altitude) != 0) {
                    return true;
                }
                return Float.compare(attributes.weirdness, this.weirdness) == 0;
            }
            return true;
        }

        public int hashCode() {
            int n = this.temperature != 0.0f ? Float.floatToIntBits(this.temperature) : 0;
            n = 31 * n + (this.humidity != 0.0f ? Float.floatToIntBits(this.humidity) : 0);
            n = 31 * n + (this.altitude != 0.0f ? Float.floatToIntBits(this.altitude) : 0);
            return 31 * n + (this.weirdness != 0.0f ? Float.floatToIntBits(this.weirdness) : 0);
        }

        public float getAttributeDifference(Attributes attributes) {
            return (this.temperature - attributes.temperature) * (this.temperature - attributes.temperature) + (this.humidity - attributes.humidity) * (this.humidity - attributes.humidity) + (this.altitude - attributes.altitude) * (this.altitude - attributes.altitude) + (this.weirdness - attributes.weirdness) * (this.weirdness - attributes.weirdness) + (this.offset - attributes.offset) * (this.offset - attributes.offset);
        }

        private static App lambda$static$5(RecordCodecBuilder.Instance instance) {
            return instance.group(((MapCodec)Codec.floatRange(-2.0f, 2.0f).fieldOf("temperature")).forGetter(Attributes::lambda$static$0), ((MapCodec)Codec.floatRange(-2.0f, 2.0f).fieldOf("humidity")).forGetter(Attributes::lambda$static$1), ((MapCodec)Codec.floatRange(-2.0f, 2.0f).fieldOf("altitude")).forGetter(Attributes::lambda$static$2), ((MapCodec)Codec.floatRange(-2.0f, 2.0f).fieldOf("weirdness")).forGetter(Attributes::lambda$static$3), ((MapCodec)Codec.floatRange(0.0f, 1.0f).fieldOf("offset")).forGetter(Attributes::lambda$static$4)).apply(instance, Attributes::new);
        }

        private static Float lambda$static$4(Attributes attributes) {
            return Float.valueOf(attributes.offset);
        }

        private static Float lambda$static$3(Attributes attributes) {
            return Float.valueOf(attributes.weirdness);
        }

        private static Float lambda$static$2(Attributes attributes) {
            return Float.valueOf(attributes.altitude);
        }

        private static Float lambda$static$1(Attributes attributes) {
            return Float.valueOf(attributes.humidity);
        }

        private static Float lambda$static$0(Attributes attributes) {
            return Float.valueOf(attributes.temperature);
        }
    }
}

