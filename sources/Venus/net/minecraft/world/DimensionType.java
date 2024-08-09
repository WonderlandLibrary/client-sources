/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.io.File;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKeyCodec;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.ColumnFuzzedBiomeMagnifier;
import net.minecraft.world.biome.FuzzedBiomeMagnifier;
import net.minecraft.world.biome.IBiomeMagnifier;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraft.world.biome.provider.NetherBiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;

public class DimensionType {
    public static final ResourceLocation OVERWORLD_ID = new ResourceLocation("overworld");
    public static final ResourceLocation THE_NETHER_ID = new ResourceLocation("the_nether");
    public static final ResourceLocation THE_END_ID = new ResourceLocation("the_end");
    public static final Codec<DimensionType> CODEC = RecordCodecBuilder.create(DimensionType::lambda$static$6);
    public static final float[] MOON_PHASE_FACTORS = new float[]{1.0f, 0.75f, 0.5f, 0.25f, 0.0f, 0.25f, 0.5f, 0.75f};
    public static final RegistryKey<DimensionType> OVERWORLD = RegistryKey.getOrCreateKey(Registry.DIMENSION_TYPE_KEY, new ResourceLocation("overworld"));
    public static final RegistryKey<DimensionType> THE_NETHER = RegistryKey.getOrCreateKey(Registry.DIMENSION_TYPE_KEY, new ResourceLocation("the_nether"));
    public static final RegistryKey<DimensionType> THE_END = RegistryKey.getOrCreateKey(Registry.DIMENSION_TYPE_KEY, new ResourceLocation("the_end"));
    protected static final DimensionType OVERWORLD_TYPE = new DimensionType(OptionalLong.empty(), true, false, false, true, 1.0, false, false, true, false, true, 256, ColumnFuzzedBiomeMagnifier.INSTANCE, BlockTags.INFINIBURN_OVERWORLD.getName(), OVERWORLD_ID, 0.0f);
    protected static final DimensionType NETHER_TYPE = new DimensionType(OptionalLong.of(18000L), false, true, true, false, 8.0, false, true, false, true, false, 128, FuzzedBiomeMagnifier.INSTANCE, BlockTags.INFINIBURN_NETHER.getName(), THE_NETHER_ID, 0.1f);
    protected static final DimensionType END_TYPE = new DimensionType(OptionalLong.of(6000L), false, false, false, false, 1.0, true, false, false, false, true, 256, FuzzedBiomeMagnifier.INSTANCE, BlockTags.INFINIBURN_END.getName(), THE_END_ID, 0.0f);
    public static final RegistryKey<DimensionType> OVERWORLD_CAVES = RegistryKey.getOrCreateKey(Registry.DIMENSION_TYPE_KEY, new ResourceLocation("overworld_caves"));
    protected static final DimensionType OVERWORLD_CAVES_TYPE = new DimensionType(OptionalLong.empty(), true, true, false, true, 1.0, false, false, true, false, true, 256, ColumnFuzzedBiomeMagnifier.INSTANCE, BlockTags.INFINIBURN_OVERWORLD.getName(), OVERWORLD_ID, 0.0f);
    public static final Codec<Supplier<DimensionType>> DIMENSION_TYPE_CODEC = RegistryKeyCodec.create(Registry.DIMENSION_TYPE_KEY, CODEC);
    private final OptionalLong fixedTime;
    private final boolean hasSkyLight;
    private final boolean hasCeiling;
    private final boolean ultrawarm;
    private final boolean natural;
    private final double coordinateScale;
    private final boolean hasDragonFight;
    private final boolean piglinSafe;
    private final boolean bedWorks;
    private final boolean respawnAnchorWorks;
    private final boolean hasRaids;
    private final int logicalHeight;
    private final IBiomeMagnifier magnifier;
    private final ResourceLocation infiniburn;
    private final ResourceLocation effects;
    private final float ambientLight;
    private final transient float[] ambientWorldLight;

    protected DimensionType(OptionalLong optionalLong, boolean bl, boolean bl2, boolean bl3, boolean bl4, double d, boolean bl5, boolean bl6, boolean bl7, boolean bl8, int n, ResourceLocation resourceLocation, ResourceLocation resourceLocation2, float f) {
        this(optionalLong, bl, bl2, bl3, bl4, d, false, bl5, bl6, bl7, bl8, n, FuzzedBiomeMagnifier.INSTANCE, resourceLocation, resourceLocation2, f);
    }

    protected DimensionType(OptionalLong optionalLong, boolean bl, boolean bl2, boolean bl3, boolean bl4, double d, boolean bl5, boolean bl6, boolean bl7, boolean bl8, boolean bl9, int n, IBiomeMagnifier iBiomeMagnifier, ResourceLocation resourceLocation, ResourceLocation resourceLocation2, float f) {
        this.fixedTime = optionalLong;
        this.hasSkyLight = bl;
        this.hasCeiling = bl2;
        this.ultrawarm = bl3;
        this.natural = bl4;
        this.coordinateScale = d;
        this.hasDragonFight = bl5;
        this.piglinSafe = bl6;
        this.bedWorks = bl7;
        this.respawnAnchorWorks = bl8;
        this.hasRaids = bl9;
        this.logicalHeight = n;
        this.magnifier = iBiomeMagnifier;
        this.infiniburn = resourceLocation;
        this.effects = resourceLocation2;
        this.ambientLight = f;
        this.ambientWorldLight = DimensionType.defaultAmbientLightWorld(f);
    }

    private static float[] defaultAmbientLightWorld(float f) {
        float[] fArray = new float[16];
        for (int i = 0; i <= 15; ++i) {
            float f2 = (float)i / 15.0f;
            float f3 = f2 / (4.0f - 3.0f * f2);
            fArray[i] = MathHelper.lerp(f, f3, 1.0f);
        }
        return fArray;
    }

    @Deprecated
    public static DataResult<RegistryKey<World>> decodeWorldKey(Dynamic<?> dynamic) {
        Optional<Number> optional = dynamic.asNumber().result();
        if (optional.isPresent()) {
            int n = optional.get().intValue();
            if (n == -1) {
                return DataResult.success(World.THE_NETHER);
            }
            if (n == 0) {
                return DataResult.success(World.OVERWORLD);
            }
            if (n == 1) {
                return DataResult.success(World.THE_END);
            }
        }
        return World.CODEC.parse(dynamic);
    }

    public static DynamicRegistries.Impl registerTypes(DynamicRegistries.Impl impl) {
        MutableRegistry<DimensionType> mutableRegistry = impl.getRegistry(Registry.DIMENSION_TYPE_KEY);
        mutableRegistry.register(OVERWORLD, OVERWORLD_TYPE, Lifecycle.stable());
        mutableRegistry.register(OVERWORLD_CAVES, OVERWORLD_CAVES_TYPE, Lifecycle.stable());
        mutableRegistry.register(THE_NETHER, NETHER_TYPE, Lifecycle.stable());
        mutableRegistry.register(THE_END, END_TYPE, Lifecycle.stable());
        return impl;
    }

    private static ChunkGenerator getEndChunkGenerator(Registry<Biome> registry, Registry<DimensionSettings> registry2, long l) {
        return new NoiseChunkGenerator(new EndBiomeProvider(registry, l), l, () -> DimensionType.lambda$getEndChunkGenerator$7(registry2));
    }

    private static ChunkGenerator getNetherChunkGenerator(Registry<Biome> registry, Registry<DimensionSettings> registry2, long l) {
        return new NoiseChunkGenerator(NetherBiomeProvider.Preset.DEFAULT_NETHER_PROVIDER_PRESET.build(registry, l), l, () -> DimensionType.lambda$getNetherChunkGenerator$8(registry2));
    }

    public static SimpleRegistry<Dimension> getDefaultSimpleRegistry(Registry<DimensionType> registry, Registry<Biome> registry2, Registry<DimensionSettings> registry3, long l) {
        SimpleRegistry<Dimension> simpleRegistry = new SimpleRegistry<Dimension>(Registry.DIMENSION_KEY, Lifecycle.experimental());
        simpleRegistry.register(Dimension.THE_NETHER, new Dimension(() -> DimensionType.lambda$getDefaultSimpleRegistry$9(registry), DimensionType.getNetherChunkGenerator(registry2, registry3, l)), Lifecycle.stable());
        simpleRegistry.register(Dimension.THE_END, new Dimension(() -> DimensionType.lambda$getDefaultSimpleRegistry$10(registry), DimensionType.getEndChunkGenerator(registry2, registry3, l)), Lifecycle.stable());
        return simpleRegistry;
    }

    public static double getCoordinateDifference(DimensionType dimensionType, DimensionType dimensionType2) {
        double d = dimensionType.getCoordinateScale();
        double d2 = dimensionType2.getCoordinateScale();
        return d / d2;
    }

    @Deprecated
    public String getSuffix() {
        return this.isSame(END_TYPE) ? "_end" : "";
    }

    public static File getDimensionFolder(RegistryKey<World> registryKey, File file) {
        if (registryKey == World.OVERWORLD) {
            return file;
        }
        if (registryKey == World.THE_END) {
            return new File(file, "DIM1");
        }
        return registryKey == World.THE_NETHER ? new File(file, "DIM-1") : new File(file, "dimensions/" + registryKey.getLocation().getNamespace() + "/" + registryKey.getLocation().getPath());
    }

    public boolean hasSkyLight() {
        return this.hasSkyLight;
    }

    public boolean getHasCeiling() {
        return this.hasCeiling;
    }

    public boolean isUltrawarm() {
        return this.ultrawarm;
    }

    public boolean isNatural() {
        return this.natural;
    }

    public double getCoordinateScale() {
        return this.coordinateScale;
    }

    public boolean isPiglinSafe() {
        return this.piglinSafe;
    }

    public boolean doesBedWork() {
        return this.bedWorks;
    }

    public boolean doesRespawnAnchorWorks() {
        return this.respawnAnchorWorks;
    }

    public boolean isHasRaids() {
        return this.hasRaids;
    }

    public int getLogicalHeight() {
        return this.logicalHeight;
    }

    public boolean doesHasDragonFight() {
        return this.hasDragonFight;
    }

    public IBiomeMagnifier getMagnifier() {
        return this.magnifier;
    }

    public boolean doesFixedTimeExist() {
        return this.fixedTime.isPresent();
    }

    public float getCelestrialAngleByTime(long l) {
        double d = MathHelper.frac((double)this.fixedTime.orElse(l) / 24000.0 - 0.25);
        double d2 = 0.5 - Math.cos(d * Math.PI) / 2.0;
        return (float)(d * 2.0 + d2) / 3.0f;
    }

    public int getMoonPhase(long l) {
        return (int)(l / 24000L % 8L + 8L) % 8;
    }

    public float getAmbientLight(int n) {
        return this.ambientWorldLight[n];
    }

    public ITag<Block> isInfiniBurn() {
        ITag<Block> iTag = BlockTags.getCollection().get(this.infiniburn);
        return iTag != null ? iTag : BlockTags.INFINIBURN_OVERWORLD;
    }

    public ResourceLocation getEffects() {
        return this.effects;
    }

    public boolean isSame(DimensionType dimensionType) {
        if (this == dimensionType) {
            return false;
        }
        return this.hasSkyLight == dimensionType.hasSkyLight && this.hasCeiling == dimensionType.hasCeiling && this.ultrawarm == dimensionType.ultrawarm && this.natural == dimensionType.natural && this.coordinateScale == dimensionType.coordinateScale && this.hasDragonFight == dimensionType.hasDragonFight && this.piglinSafe == dimensionType.piglinSafe && this.bedWorks == dimensionType.bedWorks && this.respawnAnchorWorks == dimensionType.respawnAnchorWorks && this.hasRaids == dimensionType.hasRaids && this.logicalHeight == dimensionType.logicalHeight && Float.compare(dimensionType.ambientLight, this.ambientLight) == 0 && this.fixedTime.equals(dimensionType.fixedTime) && this.magnifier.equals(dimensionType.magnifier) && this.infiniburn.equals(dimensionType.infiniburn) && this.effects.equals(dimensionType.effects);
    }

    private static DimensionType lambda$getDefaultSimpleRegistry$10(Registry registry) {
        return registry.getOrThrow(THE_END);
    }

    private static DimensionType lambda$getDefaultSimpleRegistry$9(Registry registry) {
        return registry.getOrThrow(THE_NETHER);
    }

    private static DimensionSettings lambda$getNetherChunkGenerator$8(Registry registry) {
        return registry.getOrThrow(DimensionSettings.field_242736_e);
    }

    private static DimensionSettings lambda$getEndChunkGenerator$7(Registry registry) {
        return registry.getOrThrow(DimensionSettings.field_242737_f);
    }

    private static App lambda$static$6(RecordCodecBuilder.Instance instance) {
        return instance.group(Codec.LONG.optionalFieldOf("fixed_time").xmap(DimensionType::lambda$static$0, DimensionType::lambda$static$1).forGetter(DimensionType::lambda$static$2), ((MapCodec)Codec.BOOL.fieldOf("has_skylight")).forGetter(DimensionType::hasSkyLight), ((MapCodec)Codec.BOOL.fieldOf("has_ceiling")).forGetter(DimensionType::getHasCeiling), ((MapCodec)Codec.BOOL.fieldOf("ultrawarm")).forGetter(DimensionType::isUltrawarm), ((MapCodec)Codec.BOOL.fieldOf("natural")).forGetter(DimensionType::isNatural), ((MapCodec)Codec.doubleRange(1.0E-5f, 3.0E7).fieldOf("coordinate_scale")).forGetter(DimensionType::getCoordinateScale), ((MapCodec)Codec.BOOL.fieldOf("piglin_safe")).forGetter(DimensionType::isPiglinSafe), ((MapCodec)Codec.BOOL.fieldOf("bed_works")).forGetter(DimensionType::doesBedWork), ((MapCodec)Codec.BOOL.fieldOf("respawn_anchor_works")).forGetter(DimensionType::doesRespawnAnchorWorks), ((MapCodec)Codec.BOOL.fieldOf("has_raids")).forGetter(DimensionType::isHasRaids), ((MapCodec)Codec.intRange(0, 256).fieldOf("logical_height")).forGetter(DimensionType::getLogicalHeight), ((MapCodec)ResourceLocation.CODEC.fieldOf("infiniburn")).forGetter(DimensionType::lambda$static$3), ((MapCodec)ResourceLocation.CODEC.fieldOf("effects")).orElse(OVERWORLD_ID).forGetter(DimensionType::lambda$static$4), ((MapCodec)Codec.FLOAT.fieldOf("ambient_light")).forGetter(DimensionType::lambda$static$5)).apply(instance, DimensionType::new);
    }

    private static Float lambda$static$5(DimensionType dimensionType) {
        return Float.valueOf(dimensionType.ambientLight);
    }

    private static ResourceLocation lambda$static$4(DimensionType dimensionType) {
        return dimensionType.effects;
    }

    private static ResourceLocation lambda$static$3(DimensionType dimensionType) {
        return dimensionType.infiniburn;
    }

    private static OptionalLong lambda$static$2(DimensionType dimensionType) {
        return dimensionType.fixedTime;
    }

    private static Optional lambda$static$1(OptionalLong optionalLong) {
        return optionalLong.isPresent() ? Optional.of(optionalLong.getAsLong()) : Optional.empty();
    }

    private static OptionalLong lambda$static$0(Optional optional) {
        return optional.map(OptionalLong::of).orElseGet(OptionalLong::empty);
    }
}

