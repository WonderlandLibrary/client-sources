/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.registry;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import com.mojang.serialization.Lifecycle;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootEntryManager;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootPoolEntryType;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IObjectIntIterable;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSizeType;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.jigsaw.IJigsawDeserializer;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.IPosRuleTests;
import net.minecraft.world.gen.feature.template.IRuleTestType;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.StructureProcessorList;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Registry<T>
implements Codec<T>,
Keyable,
IObjectIntIterable<T> {
    protected static final Logger LOGGER = LogManager.getLogger();
    private static final Map<ResourceLocation, Supplier<?>> LOCATION_TO_SUPPLIER = Maps.newLinkedHashMap();
    public static final ResourceLocation ROOT = new ResourceLocation("root");
    protected static final MutableRegistry<MutableRegistry<?>> ROOT_REGISTRY = new SimpleRegistry(Registry.createKey("root"), Lifecycle.experimental());
    public static final Registry<? extends Registry<?>> REGISTRY = ROOT_REGISTRY;
    public static final RegistryKey<Registry<SoundEvent>> SOUND_EVENT_KEY = Registry.createKey("sound_event");
    public static final RegistryKey<Registry<Fluid>> FLUID_KEY = Registry.createKey("fluid");
    public static final RegistryKey<Registry<Effect>> MOB_EFFECT_KEY = Registry.createKey("mob_effect");
    public static final RegistryKey<Registry<Block>> BLOCK_KEY = Registry.createKey("block");
    public static final RegistryKey<Registry<Enchantment>> ENCHANTMENT_KEY = Registry.createKey("enchantment");
    public static final RegistryKey<Registry<EntityType<?>>> ENTITY_TYPE_KEY = Registry.createKey("entity_type");
    public static final RegistryKey<Registry<Item>> ITEM_KEY = Registry.createKey("item");
    public static final RegistryKey<Registry<Potion>> POTION_KEY = Registry.createKey("potion");
    public static final RegistryKey<Registry<ParticleType<?>>> PARTICLE_TYPE_KEY = Registry.createKey("particle_type");
    public static final RegistryKey<Registry<TileEntityType<?>>> BLOCK_ENTITY_TYPE_KEY = Registry.createKey("block_entity_type");
    public static final RegistryKey<Registry<PaintingType>> MOTIVE_KEY = Registry.createKey("motive");
    public static final RegistryKey<Registry<ResourceLocation>> CUSTOM_STAT_KEY = Registry.createKey("custom_stat");
    public static final RegistryKey<Registry<ChunkStatus>> CHUNK_STATUS_KEY = Registry.createKey("chunk_status");
    public static final RegistryKey<Registry<IRuleTestType<?>>> RULE_TEST_KEY = Registry.createKey("rule_test");
    public static final RegistryKey<Registry<IPosRuleTests<?>>> POS_RULE_TEST_KEY = Registry.createKey("pos_rule_test");
    public static final RegistryKey<Registry<ContainerType<?>>> MENU_KEY = Registry.createKey("menu");
    public static final RegistryKey<Registry<IRecipeType<?>>> RECIPE_TYPE_KEY = Registry.createKey("recipe_type");
    public static final RegistryKey<Registry<IRecipeSerializer<?>>> RECIPE_SERIALIZER_KEY = Registry.createKey("recipe_serializer");
    public static final RegistryKey<Registry<Attribute>> ATTRIBUTE_KEY = Registry.createKey("attribute");
    public static final RegistryKey<Registry<StatType<?>>> STAT_TYPE_KEY = Registry.createKey("stat_type");
    public static final RegistryKey<Registry<VillagerType>> VILLAGER_TYPE_KEY = Registry.createKey("villager_type");
    public static final RegistryKey<Registry<VillagerProfession>> VILLAGER_PROFESSION_KEY = Registry.createKey("villager_profession");
    public static final RegistryKey<Registry<PointOfInterestType>> POINT_OF_INTEREST_TYPE_KEY = Registry.createKey("point_of_interest_type");
    public static final RegistryKey<Registry<MemoryModuleType<?>>> MEMORY_MODULE_TYPE_KEY = Registry.createKey("memory_module_type");
    public static final RegistryKey<Registry<SensorType<?>>> SENSOR_TYPE_KEY = Registry.createKey("sensor_type");
    public static final RegistryKey<Registry<Schedule>> SCHEDULE_KEY = Registry.createKey("schedule");
    public static final RegistryKey<Registry<Activity>> ACTIVITY_KEY = Registry.createKey("activity");
    public static final RegistryKey<Registry<LootPoolEntryType>> LOOT_POOL_ENTRY_TYPE_KEY = Registry.createKey("loot_pool_entry_type");
    public static final RegistryKey<Registry<LootFunctionType>> LOOT_FUNCTION_TYPE_KEY = Registry.createKey("loot_function_type");
    public static final RegistryKey<Registry<LootConditionType>> LOOT_CONDITION_TYPE_KEY = Registry.createKey("loot_condition_type");
    public static final RegistryKey<Registry<DimensionType>> DIMENSION_TYPE_KEY = Registry.createKey("dimension_type");
    public static final RegistryKey<Registry<World>> WORLD_KEY = Registry.createKey("dimension");
    public static final RegistryKey<Registry<Dimension>> DIMENSION_KEY = Registry.createKey("dimension");
    public static final Registry<SoundEvent> SOUND_EVENT = Registry.createRegistry(SOUND_EVENT_KEY, Registry::lambda$static$0);
    public static final DefaultedRegistry<Fluid> FLUID = Registry.registerDefaulted(FLUID_KEY, "empty", Registry::lambda$static$1);
    public static final Registry<Effect> EFFECTS = Registry.createRegistry(MOB_EFFECT_KEY, Registry::lambda$static$2);
    public static final DefaultedRegistry<Block> BLOCK = Registry.registerDefaulted(BLOCK_KEY, "air", Registry::lambda$static$3);
    public static final Registry<Enchantment> ENCHANTMENT = Registry.createRegistry(ENCHANTMENT_KEY, Registry::lambda$static$4);
    public static final DefaultedRegistry<EntityType<?>> ENTITY_TYPE = Registry.registerDefaulted(ENTITY_TYPE_KEY, "pig", Registry::lambda$static$5);
    public static final DefaultedRegistry<Item> ITEM = Registry.registerDefaulted(ITEM_KEY, "air", Registry::lambda$static$6);
    public static final DefaultedRegistry<Potion> POTION = Registry.registerDefaulted(POTION_KEY, "empty", Registry::lambda$static$7);
    public static final Registry<ParticleType<?>> PARTICLE_TYPE = Registry.createRegistry(PARTICLE_TYPE_KEY, Registry::lambda$static$8);
    public static final Registry<TileEntityType<?>> BLOCK_ENTITY_TYPE = Registry.createRegistry(BLOCK_ENTITY_TYPE_KEY, Registry::lambda$static$9);
    public static final DefaultedRegistry<PaintingType> MOTIVE = Registry.registerDefaulted(MOTIVE_KEY, "kebab", Registry::lambda$static$10);
    public static final Registry<ResourceLocation> CUSTOM_STAT = Registry.createRegistry(CUSTOM_STAT_KEY, Registry::lambda$static$11);
    public static final DefaultedRegistry<ChunkStatus> CHUNK_STATUS = Registry.registerDefaulted(CHUNK_STATUS_KEY, "empty", Registry::lambda$static$12);
    public static final Registry<IRuleTestType<?>> RULE_TEST = Registry.createRegistry(RULE_TEST_KEY, Registry::lambda$static$13);
    public static final Registry<IPosRuleTests<?>> POS_RULE_TEST = Registry.createRegistry(POS_RULE_TEST_KEY, Registry::lambda$static$14);
    public static final Registry<ContainerType<?>> MENU = Registry.createRegistry(MENU_KEY, Registry::lambda$static$15);
    public static final Registry<IRecipeType<?>> RECIPE_TYPE = Registry.createRegistry(RECIPE_TYPE_KEY, Registry::lambda$static$16);
    public static final Registry<IRecipeSerializer<?>> RECIPE_SERIALIZER = Registry.createRegistry(RECIPE_SERIALIZER_KEY, Registry::lambda$static$17);
    public static final Registry<Attribute> ATTRIBUTE = Registry.createRegistry(ATTRIBUTE_KEY, Registry::lambda$static$18);
    public static final Registry<StatType<?>> STATS = Registry.createRegistry(STAT_TYPE_KEY, Registry::lambda$static$19);
    public static final DefaultedRegistry<VillagerType> VILLAGER_TYPE = Registry.registerDefaulted(VILLAGER_TYPE_KEY, "plains", Registry::lambda$static$20);
    public static final DefaultedRegistry<VillagerProfession> VILLAGER_PROFESSION = Registry.registerDefaulted(VILLAGER_PROFESSION_KEY, "none", Registry::lambda$static$21);
    public static final DefaultedRegistry<PointOfInterestType> POINT_OF_INTEREST_TYPE = Registry.registerDefaulted(POINT_OF_INTEREST_TYPE_KEY, "unemployed", Registry::lambda$static$22);
    public static final DefaultedRegistry<MemoryModuleType<?>> MEMORY_MODULE_TYPE = Registry.registerDefaulted(MEMORY_MODULE_TYPE_KEY, "dummy", Registry::lambda$static$23);
    public static final DefaultedRegistry<SensorType<?>> SENSOR_TYPE = Registry.registerDefaulted(SENSOR_TYPE_KEY, "dummy", Registry::lambda$static$24);
    public static final Registry<Schedule> SCHEDULE = Registry.createRegistry(SCHEDULE_KEY, Registry::lambda$static$25);
    public static final Registry<Activity> ACTIVITY = Registry.createRegistry(ACTIVITY_KEY, Registry::lambda$static$26);
    public static final Registry<LootPoolEntryType> LOOT_POOL_ENTRY_TYPE = Registry.createRegistry(LOOT_POOL_ENTRY_TYPE_KEY, Registry::lambda$static$27);
    public static final Registry<LootFunctionType> LOOT_FUNCTION_TYPE = Registry.createRegistry(LOOT_FUNCTION_TYPE_KEY, Registry::lambda$static$28);
    public static final Registry<LootConditionType> LOOT_CONDITION_TYPE = Registry.createRegistry(LOOT_CONDITION_TYPE_KEY, Registry::lambda$static$29);
    public static final RegistryKey<Registry<DimensionSettings>> NOISE_SETTINGS_KEY = Registry.createKey("worldgen/noise_settings");
    public static final RegistryKey<Registry<ConfiguredSurfaceBuilder<?>>> CONFIGURED_SURFACE_BUILDER_KEY = Registry.createKey("worldgen/configured_surface_builder");
    public static final RegistryKey<Registry<ConfiguredCarver<?>>> CONFIGURED_CARVER_KEY = Registry.createKey("worldgen/configured_carver");
    public static final RegistryKey<Registry<ConfiguredFeature<?, ?>>> CONFIGURED_FEATURE_KEY = Registry.createKey("worldgen/configured_feature");
    public static final RegistryKey<Registry<StructureFeature<?, ?>>> CONFIGURED_STRUCTURE_FEATURE_KEY = Registry.createKey("worldgen/configured_structure_feature");
    public static final RegistryKey<Registry<StructureProcessorList>> STRUCTURE_PROCESSOR_LIST_KEY = Registry.createKey("worldgen/processor_list");
    public static final RegistryKey<Registry<JigsawPattern>> JIGSAW_POOL_KEY = Registry.createKey("worldgen/template_pool");
    public static final RegistryKey<Registry<Biome>> BIOME_KEY = Registry.createKey("worldgen/biome");
    public static final RegistryKey<Registry<SurfaceBuilder<?>>> SURFACE_BUILDER_KEY = Registry.createKey("worldgen/surface_builder");
    public static final Registry<SurfaceBuilder<?>> SURFACE_BUILDER = Registry.createRegistry(SURFACE_BUILDER_KEY, Registry::lambda$static$30);
    public static final RegistryKey<Registry<WorldCarver<?>>> CARVER_KEY = Registry.createKey("worldgen/carver");
    public static final Registry<WorldCarver<?>> CARVER = Registry.createRegistry(CARVER_KEY, Registry::lambda$static$31);
    public static final RegistryKey<Registry<Feature<?>>> FEATURE_KEY = Registry.createKey("worldgen/feature");
    public static final Registry<Feature<?>> FEATURE = Registry.createRegistry(FEATURE_KEY, Registry::lambda$static$32);
    public static final RegistryKey<Registry<Structure<?>>> STRUCTURE_FEATURE_KEY = Registry.createKey("worldgen/structure_feature");
    public static final Registry<Structure<?>> STRUCTURE_FEATURE = Registry.createRegistry(STRUCTURE_FEATURE_KEY, Registry::lambda$static$33);
    public static final RegistryKey<Registry<IStructurePieceType>> STRUCTURE_PIECE_KEY = Registry.createKey("worldgen/structure_piece");
    public static final Registry<IStructurePieceType> STRUCTURE_PIECE = Registry.createRegistry(STRUCTURE_PIECE_KEY, Registry::lambda$static$34);
    public static final RegistryKey<Registry<Placement<?>>> DECORATOR_KEY = Registry.createKey("worldgen/decorator");
    public static final Registry<Placement<?>> DECORATOR = Registry.createRegistry(DECORATOR_KEY, Registry::lambda$static$35);
    public static final RegistryKey<Registry<BlockStateProviderType<?>>> BLOCK_STATE_PROVIDER_TYPE_KEY = Registry.createKey("worldgen/block_state_provider_type");
    public static final RegistryKey<Registry<BlockPlacerType<?>>> BLOCK_PLACER_TYPE_KEY = Registry.createKey("worldgen/block_placer_type");
    public static final RegistryKey<Registry<FoliagePlacerType<?>>> FOLIAGE_PLACER_TYPE_KEY = Registry.createKey("worldgen/foliage_placer_type");
    public static final RegistryKey<Registry<TrunkPlacerType<?>>> TRUNK_PLACER_TYPE_KEY = Registry.createKey("worldgen/trunk_placer_type");
    public static final RegistryKey<Registry<TreeDecoratorType<?>>> TREE_DECORATOR_TYPE_KEY = Registry.createKey("worldgen/tree_decorator_type");
    public static final RegistryKey<Registry<FeatureSizeType<?>>> FEATURE_SIZE_TYPE_KEY = Registry.createKey("worldgen/feature_size_type");
    public static final RegistryKey<Registry<Codec<? extends BiomeProvider>>> BIOME_SOURCE_KEY = Registry.createKey("worldgen/biome_source");
    public static final RegistryKey<Registry<Codec<? extends ChunkGenerator>>> CHUNK_GENERATOR_KEY = Registry.createKey("worldgen/chunk_generator");
    public static final RegistryKey<Registry<IStructureProcessorType<?>>> STRUCTURE_PROCESSOR_KEY = Registry.createKey("worldgen/structure_processor");
    public static final RegistryKey<Registry<IJigsawDeserializer<?>>> STRUCTURE_POOL_ELEMENT_KEY = Registry.createKey("worldgen/structure_pool_element");
    public static final Registry<BlockStateProviderType<?>> BLOCK_STATE_PROVIDER_TYPE = Registry.createRegistry(BLOCK_STATE_PROVIDER_TYPE_KEY, Registry::lambda$static$36);
    public static final Registry<BlockPlacerType<?>> BLOCK_PLACER_TYPE = Registry.createRegistry(BLOCK_PLACER_TYPE_KEY, Registry::lambda$static$37);
    public static final Registry<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPE = Registry.createRegistry(FOLIAGE_PLACER_TYPE_KEY, Registry::lambda$static$38);
    public static final Registry<TrunkPlacerType<?>> TRUNK_REPLACER = Registry.createRegistry(TRUNK_PLACER_TYPE_KEY, Registry::lambda$static$39);
    public static final Registry<TreeDecoratorType<?>> TREE_DECORATOR_TYPE = Registry.createRegistry(TREE_DECORATOR_TYPE_KEY, Registry::lambda$static$40);
    public static final Registry<FeatureSizeType<?>> FEATURE_SIZE_TYPE = Registry.createRegistry(FEATURE_SIZE_TYPE_KEY, Registry::lambda$static$41);
    public static final Registry<Codec<? extends BiomeProvider>> BIOME_PROVIDER_CODEC = Registry.register(BIOME_SOURCE_KEY, Lifecycle.stable(), Registry::lambda$static$42);
    public static final Registry<Codec<? extends ChunkGenerator>> CHUNK_GENERATOR_CODEC = Registry.register(CHUNK_GENERATOR_KEY, Lifecycle.stable(), Registry::lambda$static$43);
    public static final Registry<IStructureProcessorType<?>> STRUCTURE_PROCESSOR = Registry.createRegistry(STRUCTURE_PROCESSOR_KEY, Registry::lambda$static$44);
    public static final Registry<IJigsawDeserializer<?>> STRUCTURE_POOL_ELEMENT = Registry.createRegistry(STRUCTURE_POOL_ELEMENT_KEY, Registry::lambda$static$45);
    private final RegistryKey<? extends Registry<T>> registryKey;
    private final Lifecycle lifecycle;

    private static <T> RegistryKey<Registry<T>> createKey(String string) {
        return RegistryKey.getOrCreateRootKey(new ResourceLocation(string));
    }

    public static <T extends MutableRegistry<?>> void validateMutableRegistry(MutableRegistry<T> mutableRegistry) {
        mutableRegistry.forEach(arg_0 -> Registry.lambda$validateMutableRegistry$46(mutableRegistry, arg_0));
    }

    private static <T> Registry<T> createRegistry(RegistryKey<? extends Registry<T>> registryKey, Supplier<T> supplier) {
        return Registry.register(registryKey, Lifecycle.experimental(), supplier);
    }

    private static <T> DefaultedRegistry<T> registerDefaulted(RegistryKey<? extends Registry<T>> registryKey, String string, Supplier<T> supplier) {
        return Registry.registerDefaulted(registryKey, string, Lifecycle.experimental(), supplier);
    }

    private static <T> Registry<T> register(RegistryKey<? extends Registry<T>> registryKey, Lifecycle lifecycle, Supplier<T> supplier) {
        return Registry.addRegistry(registryKey, new SimpleRegistry(registryKey, lifecycle), supplier, lifecycle);
    }

    private static <T> DefaultedRegistry<T> registerDefaulted(RegistryKey<? extends Registry<T>> registryKey, String string, Lifecycle lifecycle, Supplier<T> supplier) {
        return Registry.addRegistry(registryKey, new DefaultedRegistry(string, registryKey, lifecycle), supplier, lifecycle);
    }

    private static <T, R extends MutableRegistry<T>> R addRegistry(RegistryKey<? extends Registry<T>> registryKey, R r, Supplier<T> supplier, Lifecycle lifecycle) {
        ResourceLocation resourceLocation = registryKey.getLocation();
        LOCATION_TO_SUPPLIER.put(resourceLocation, supplier);
        MutableRegistry<MutableRegistry<?>> mutableRegistry = ROOT_REGISTRY;
        return mutableRegistry.register(registryKey, r, lifecycle);
    }

    protected Registry(RegistryKey<? extends Registry<T>> registryKey, Lifecycle lifecycle) {
        this.registryKey = registryKey;
        this.lifecycle = lifecycle;
    }

    public RegistryKey<? extends Registry<T>> getRegistryKey() {
        return this.registryKey;
    }

    public String toString() {
        return "Registry[" + this.registryKey + " (" + this.lifecycle + ")]";
    }

    @Override
    public <U> DataResult<Pair<T, U>> decode(DynamicOps<U> dynamicOps, U u) {
        return dynamicOps.compressMaps() ? dynamicOps.getNumberValue(u).flatMap(this::lambda$decode$47).map(arg_0 -> Registry.lambda$decode$48(dynamicOps, arg_0)) : ResourceLocation.CODEC.decode(dynamicOps, u).flatMap(this::lambda$decode$49);
    }

    @Override
    public <U> DataResult<U> encode(T t, DynamicOps<U> dynamicOps, U u) {
        ResourceLocation resourceLocation = this.getKey(t);
        if (resourceLocation == null) {
            return DataResult.error("Unknown registry element " + t);
        }
        return dynamicOps.compressMaps() ? dynamicOps.mergeToPrimitive(u, dynamicOps.createInt(this.getId(t))).setLifecycle(this.lifecycle) : dynamicOps.mergeToPrimitive(u, dynamicOps.createString(resourceLocation.toString())).setLifecycle(this.lifecycle);
    }

    public <U> Stream<U> keys(DynamicOps<U> dynamicOps) {
        return this.keySet().stream().map(arg_0 -> Registry.lambda$keys$50(dynamicOps, arg_0));
    }

    @Nullable
    public abstract ResourceLocation getKey(T var1);

    public abstract Optional<RegistryKey<T>> getOptionalKey(T var1);

    @Override
    public abstract int getId(@Nullable T var1);

    @Nullable
    public abstract T getValueForKey(@Nullable RegistryKey<T> var1);

    @Nullable
    public abstract T getOrDefault(@Nullable ResourceLocation var1);

    protected abstract Lifecycle getLifecycleByRegistry(T var1);

    public abstract Lifecycle getLifecycle();

    public Optional<T> getOptional(@Nullable ResourceLocation resourceLocation) {
        return Optional.ofNullable(this.getOrDefault(resourceLocation));
    }

    public Optional<T> getOptionalValue(@Nullable RegistryKey<T> registryKey) {
        return Optional.ofNullable(this.getValueForKey(registryKey));
    }

    public T getOrThrow(RegistryKey<T> registryKey) {
        T t = this.getValueForKey(registryKey);
        if (t == null) {
            throw new IllegalStateException("Missing: " + registryKey);
        }
        return t;
    }

    public abstract Set<ResourceLocation> keySet();

    public abstract Set<Map.Entry<RegistryKey<T>, T>> getEntries();

    public Stream<T> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public abstract boolean containsKey(ResourceLocation var1);

    public static <T> T register(Registry<? super T> registry, String string, T t) {
        return Registry.register(registry, new ResourceLocation(string), t);
    }

    public static <V, T extends V> T register(Registry<V> registry, ResourceLocation resourceLocation, T t) {
        return ((MutableRegistry)registry).register(RegistryKey.getOrCreateKey(registry.registryKey, resourceLocation), t, Lifecycle.stable());
    }

    public static <V, T extends V> T register(Registry<V> registry, int n, String string, T t) {
        return ((MutableRegistry)registry).register(n, RegistryKey.getOrCreateKey(registry.registryKey, new ResourceLocation(string)), t, Lifecycle.stable());
    }

    private static void lambda$static$51(ResourceLocation resourceLocation, Supplier supplier) {
        if (supplier.get() == null) {
            LOGGER.error("Unable to bootstrap registry '{}'", (Object)resourceLocation);
        }
    }

    private static Object lambda$keys$50(DynamicOps dynamicOps, ResourceLocation resourceLocation) {
        return dynamicOps.createString(resourceLocation.toString());
    }

    private DataResult lambda$decode$49(Pair pair) {
        T t = this.getOrDefault((ResourceLocation)pair.getFirst());
        return t == null ? DataResult.error("Unknown registry key: " + pair.getFirst()) : DataResult.success(Pair.of(t, pair.getSecond()), this.getLifecycleByRegistry(t));
    }

    private static Pair lambda$decode$48(DynamicOps dynamicOps, Object object) {
        return Pair.of(object, dynamicOps.empty());
    }

    private DataResult lambda$decode$47(Number number) {
        Object t = this.getByValue(number.intValue());
        return t == null ? DataResult.error("Unknown registry id: " + number) : DataResult.success(t, this.getLifecycleByRegistry(t));
    }

    private static void lambda$validateMutableRegistry$46(MutableRegistry mutableRegistry, MutableRegistry mutableRegistry2) {
        if (mutableRegistry2.keySet().isEmpty()) {
            LOGGER.error("Registry '{}' was empty after loading", (Object)mutableRegistry.getKey(mutableRegistry2));
            if (SharedConstants.developmentMode) {
                throw new IllegalStateException("Registry: '" + mutableRegistry.getKey(mutableRegistry2) + "' is empty, not allowed, fix me!");
            }
        }
        if (mutableRegistry2 instanceof DefaultedRegistry) {
            ResourceLocation resourceLocation = ((DefaultedRegistry)mutableRegistry2).getDefaultKey();
            Validate.notNull(mutableRegistry2.getOrDefault(resourceLocation), "Missing default of DefaultedMappedRegistry: " + resourceLocation, new Object[0]);
        }
    }

    private static IJigsawDeserializer lambda$static$45() {
        return IJigsawDeserializer.EMPTY_POOL_ELEMENT;
    }

    private static IStructureProcessorType lambda$static$44() {
        return IStructureProcessorType.BLOCK_IGNORE;
    }

    private static Codec lambda$static$43() {
        return ChunkGenerator.field_235948_a_;
    }

    private static Codec lambda$static$42() {
        return BiomeProvider.CODEC;
    }

    private static FeatureSizeType lambda$static$41() {
        return FeatureSizeType.TWO_LAYERS_FEATURE_SIZE;
    }

    private static TreeDecoratorType lambda$static$40() {
        return TreeDecoratorType.LEAVE_VINE;
    }

    private static TrunkPlacerType lambda$static$39() {
        return TrunkPlacerType.STRAIGHT_TRUNK_PLACER;
    }

    private static FoliagePlacerType lambda$static$38() {
        return FoliagePlacerType.BLOB;
    }

    private static BlockPlacerType lambda$static$37() {
        return BlockPlacerType.SIMPLE_BLOCK;
    }

    private static BlockStateProviderType lambda$static$36() {
        return BlockStateProviderType.SIMPLE_STATE_PROVIDER;
    }

    private static Placement lambda$static$35() {
        return Placement.NOPE;
    }

    private static IStructurePieceType lambda$static$34() {
        return IStructurePieceType.MSROOM;
    }

    private static Structure lambda$static$33() {
        return Structure.field_236367_c_;
    }

    private static Feature lambda$static$32() {
        return Feature.ORE;
    }

    private static WorldCarver lambda$static$31() {
        return WorldCarver.CAVE;
    }

    private static SurfaceBuilder lambda$static$30() {
        return SurfaceBuilder.DEFAULT;
    }

    private static LootConditionType lambda$static$29() {
        return LootConditionManager.INVERTED;
    }

    private static LootFunctionType lambda$static$28() {
        return LootFunctionManager.SET_COUNT;
    }

    private static LootPoolEntryType lambda$static$27() {
        return LootEntryManager.EMPTY;
    }

    private static Activity lambda$static$26() {
        return Activity.IDLE;
    }

    private static Schedule lambda$static$25() {
        return Schedule.EMPTY;
    }

    private static SensorType lambda$static$24() {
        return SensorType.DUMMY;
    }

    private static MemoryModuleType lambda$static$23() {
        return MemoryModuleType.DUMMY;
    }

    private static PointOfInterestType lambda$static$22() {
        return PointOfInterestType.UNEMPLOYED;
    }

    private static VillagerProfession lambda$static$21() {
        return VillagerProfession.NONE;
    }

    private static VillagerType lambda$static$20() {
        return VillagerType.PLAINS;
    }

    private static StatType lambda$static$19() {
        return Stats.ITEM_USED;
    }

    private static Attribute lambda$static$18() {
        return Attributes.LUCK;
    }

    private static IRecipeSerializer lambda$static$17() {
        return IRecipeSerializer.CRAFTING_SHAPELESS;
    }

    private static IRecipeType lambda$static$16() {
        return IRecipeType.CRAFTING;
    }

    private static ContainerType lambda$static$15() {
        return ContainerType.ANVIL;
    }

    private static IPosRuleTests lambda$static$14() {
        return IPosRuleTests.field_237103_a_;
    }

    private static IRuleTestType lambda$static$13() {
        return IRuleTestType.ALWAYS_TRUE;
    }

    private static ChunkStatus lambda$static$12() {
        return ChunkStatus.EMPTY;
    }

    private static ResourceLocation lambda$static$11() {
        return Stats.JUMP;
    }

    private static PaintingType lambda$static$10() {
        return PaintingType.KEBAB;
    }

    private static TileEntityType lambda$static$9() {
        return TileEntityType.FURNACE;
    }

    private static ParticleType lambda$static$8() {
        return ParticleTypes.BLOCK;
    }

    private static Potion lambda$static$7() {
        return Potions.EMPTY;
    }

    private static Item lambda$static$6() {
        return Items.AIR;
    }

    private static EntityType lambda$static$5() {
        return EntityType.PIG;
    }

    private static Enchantment lambda$static$4() {
        return Enchantments.FORTUNE;
    }

    private static Block lambda$static$3() {
        return Blocks.AIR;
    }

    private static Effect lambda$static$2() {
        return Effects.LUCK;
    }

    private static Fluid lambda$static$1() {
        return Fluids.EMPTY;
    }

    private static SoundEvent lambda$static$0() {
        return SoundEvents.ENTITY_ITEM_PICKUP;
    }

    static {
        WorldGenRegistries.init();
        LOCATION_TO_SUPPLIER.forEach(Registry::lambda$static$51);
        Registry.validateMutableRegistry(ROOT_REGISTRY);
    }
}

