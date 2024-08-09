/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.BlockModel;
import net.minecraft.client.renderer.model.BlockModelDefinition;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemModelGenerator;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.model.ModelRotation;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.model.VariantList;
import net.minecraft.client.renderer.model.multipart.Multipart;
import net.minecraft.client.renderer.model.multipart.Selector;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.SpriteMap;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.BellTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.ConduitTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.EnchantmentTableTileEntityRenderer;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.util.registry.Registry;
import net.optifine.reflect.Reflector;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelBakery {
    public static final RenderMaterial LOCATION_FIRE_0 = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("block/fire_0"));
    public static final RenderMaterial LOCATION_FIRE_1 = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("block/fire_1"));
    public static final RenderMaterial LOCATION_LAVA_FLOW = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("block/lava_flow"));
    public static final RenderMaterial LOCATION_WATER_FLOW = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("block/water_flow"));
    public static final RenderMaterial LOCATION_WATER_OVERLAY = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("block/water_overlay"));
    public static final RenderMaterial LOCATION_BANNER_BASE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/banner_base"));
    public static final RenderMaterial LOCATION_SHIELD_BASE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/shield_base"));
    public static final RenderMaterial LOCATION_SHIELD_NO_PATTERN = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/shield_base_nopattern"));
    public static final List<ResourceLocation> DESTROY_STAGES = IntStream.range(0, 10).mapToObj(ModelBakery::lambda$static$0).collect(Collectors.toList());
    public static final List<ResourceLocation> DESTROY_LOCATIONS = DESTROY_STAGES.stream().map(ModelBakery::lambda$static$1).collect(Collectors.toList());
    public static final List<RenderType> DESTROY_RENDER_TYPES = DESTROY_LOCATIONS.stream().map(RenderType::getCrumbling).collect(Collectors.toList());
    private static final Set<RenderMaterial> LOCATIONS_BUILTIN_TEXTURES = Util.make(Sets.newHashSet(), ModelBakery::lambda$static$2);
    private static final Logger LOGGER = LogManager.getLogger();
    public static final ModelResourceLocation MODEL_MISSING = new ModelResourceLocation("builtin/missing", "missing");
    private static final String MODEL_MISSING_STRING = MODEL_MISSING.toString();
    @VisibleForTesting
    public static final String MISSING_MODEL_MESH = ("{    'textures': {       'particle': '" + MissingTextureSprite.getLocation().getPath() + "',       'missingno': '" + MissingTextureSprite.getLocation().getPath() + "'    },    'elements': [         {  'from': [ 0, 0, 0 ],            'to': [ 16, 16, 16 ],            'faces': {                'down':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'down',  'texture': '#missingno' },                'up':    { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'up',    'texture': '#missingno' },                'north': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'north', 'texture': '#missingno' },                'south': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'south', 'texture': '#missingno' },                'west':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'west',  'texture': '#missingno' },                'east':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'east',  'texture': '#missingno' }            }        }    ]}").replace('\'', '\"');
    private static final Map<String, String> BUILT_IN_MODELS = Maps.newHashMap(ImmutableMap.of("missing", MISSING_MODEL_MESH));
    private static final Splitter SPLITTER_COMMA = Splitter.on(',');
    private static final Splitter EQUALS_SPLITTER = Splitter.on('=').limit(2);
    public static final BlockModel MODEL_GENERATED = Util.make(BlockModel.deserialize("{\"gui_light\": \"front\"}"), ModelBakery::lambda$static$3);
    public static final BlockModel MODEL_ENTITY = Util.make(BlockModel.deserialize("{\"gui_light\": \"side\"}"), ModelBakery::lambda$static$4);
    private static final StateContainer<Block, BlockState> STATE_CONTAINER_ITEM_FRAME = new StateContainer.Builder(Blocks.AIR).add(BooleanProperty.create("map")).func_235882_a_(Block::getDefaultState, BlockState::new);
    private static final ItemModelGenerator ITEM_MODEL_GENERATOR = new ItemModelGenerator();
    private static final Map<ResourceLocation, StateContainer<Block, BlockState>> STATE_CONTAINER_OVERRIDES = ImmutableMap.of(new ResourceLocation("item_frame"), STATE_CONTAINER_ITEM_FRAME);
    private final IResourceManager resourceManager;
    @Nullable
    private SpriteMap spriteMap;
    private final BlockColors blockColors;
    private final Set<ResourceLocation> unbakedModelLoadingQueue = Sets.newHashSet();
    private final BlockModelDefinition.ContainerHolder containerHolder = new BlockModelDefinition.ContainerHolder();
    private final Map<ResourceLocation, IUnbakedModel> unbakedModels = Maps.newHashMap();
    private final Map<Triple<ResourceLocation, TransformationMatrix, Boolean>, IBakedModel> bakedModels = Maps.newHashMap();
    private final Map<ResourceLocation, IUnbakedModel> topUnbakedModels = Maps.newHashMap();
    private final Map<ResourceLocation, IBakedModel> topBakedModels = Maps.newHashMap();
    private Map<ResourceLocation, Pair<AtlasTexture, AtlasTexture.SheetData>> sheetData;
    private int counterModelId = 1;
    private final Object2IntMap<BlockState> stateModelIds = Util.make(new Object2IntOpenHashMap(), ModelBakery::lambda$new$5);
    public Map<ResourceLocation, IUnbakedModel> mapUnbakedModels;

    public ModelBakery(IResourceManager iResourceManager, BlockColors blockColors, IProfiler iProfiler, int n) {
        this(iResourceManager, blockColors, true);
        this.processLoading(iProfiler, n);
    }

    protected ModelBakery(IResourceManager iResourceManager, BlockColors blockColors, boolean bl) {
        this.resourceManager = iResourceManager;
        this.blockColors = blockColors;
    }

    protected void processLoading(IProfiler iProfiler, int n) {
        Reflector.ModelLoaderRegistry_onModelLoadingStart.callVoid(new Object[0]);
        iProfiler.startSection("missing_model");
        try {
            this.unbakedModels.put(MODEL_MISSING, this.loadModel(MODEL_MISSING));
            this.loadTopModel(MODEL_MISSING);
        } catch (IOException iOException) {
            LOGGER.error("Error loading missing model, should never happen :(", (Throwable)iOException);
            throw new RuntimeException(iOException);
        }
        iProfiler.endStartSection("static_definitions");
        STATE_CONTAINER_OVERRIDES.forEach(this::lambda$processLoading$7);
        iProfiler.endStartSection("blocks");
        for (Block set2 : Registry.BLOCK) {
            set2.getStateContainer().getValidStates().forEach(this::lambda$processLoading$8);
        }
        iProfiler.endStartSection("items");
        for (ResourceLocation resourceLocation : Registry.ITEM.keySet()) {
            this.loadTopModel(new ModelResourceLocation(resourceLocation, "inventory"));
        }
        iProfiler.endStartSection("special");
        this.loadTopModel(new ModelResourceLocation("minecraft:trident_in_hand#inventory"));
        for (ResourceLocation resourceLocation : this.getSpecialModels()) {
            this.addModelToCache(resourceLocation);
        }
        iProfiler.endStartSection("textures");
        this.mapUnbakedModels = this.unbakedModels;
        TextureUtils.registerCustomModels(this);
        LinkedHashSet linkedHashSet = Sets.newLinkedHashSet();
        Set set = this.topUnbakedModels.values().stream().flatMap(arg_0 -> this.lambda$processLoading$9(linkedHashSet, arg_0)).collect(Collectors.toSet());
        set.addAll(LOCATIONS_BUILTIN_TEXTURES);
        Reflector.call(Reflector.ForgeHooksClient_gatherFluidTextures, set);
        linkedHashSet.stream().filter(ModelBakery::lambda$processLoading$10).forEach(ModelBakery::lambda$processLoading$11);
        Map<ResourceLocation, List<RenderMaterial>> map = set.stream().collect(Collectors.groupingBy(RenderMaterial::getAtlasLocation));
        iProfiler.endStartSection("stitching");
        this.sheetData = Maps.newHashMap();
        for (Map.Entry<ResourceLocation, List<RenderMaterial>> entry : map.entrySet()) {
            AtlasTexture atlasTexture = new AtlasTexture(entry.getKey());
            AtlasTexture.SheetData sheetData = atlasTexture.stitch(this.resourceManager, entry.getValue().stream().map(RenderMaterial::getTextureLocation), iProfiler, n);
            this.sheetData.put(entry.getKey(), Pair.of(atlasTexture, sheetData));
        }
        iProfiler.endSection();
    }

    public SpriteMap uploadTextures(TextureManager textureManager, IProfiler iProfiler) {
        iProfiler.startSection("atlas");
        for (Pair<AtlasTexture, AtlasTexture.SheetData> pair : this.sheetData.values()) {
            AtlasTexture atlasTexture = pair.getFirst();
            AtlasTexture.SheetData sheetData = pair.getSecond();
            atlasTexture.upload(sheetData);
            textureManager.loadTexture(atlasTexture.getTextureLocation(), atlasTexture);
            textureManager.bindTexture(atlasTexture.getTextureLocation());
            atlasTexture.setBlurMipmap(sheetData);
        }
        this.spriteMap = new SpriteMap(this.sheetData.values().stream().map(Pair::getFirst).collect(Collectors.toList()));
        iProfiler.endStartSection("baking");
        this.topUnbakedModels.keySet().forEach(this::lambda$uploadTextures$12);
        iProfiler.endSection();
        return this.spriteMap;
    }

    private static Predicate<BlockState> parseVariantKey(StateContainer<Block, BlockState> stateContainer, String string) {
        HashMap<Property<?>, ?> hashMap = Maps.newHashMap();
        for (String string2 : SPLITTER_COMMA.split(string)) {
            Iterator<String> iterator2 = EQUALS_SPLITTER.split(string2).iterator();
            if (!iterator2.hasNext()) continue;
            String string3 = iterator2.next();
            Property<?> property = stateContainer.getProperty(string3);
            if (property != null && iterator2.hasNext()) {
                String string4 = iterator2.next();
                Object obj = ModelBakery.parseValue(property, string4);
                if (obj == null) {
                    throw new RuntimeException("Unknown value: '" + string4 + "' for blockstate property: '" + string3 + "' " + property.getAllowedValues());
                }
                hashMap.put(property, obj);
                continue;
            }
            if (string3.isEmpty()) continue;
            throw new RuntimeException("Unknown blockstate property: '" + string3 + "'");
        }
        Block block = stateContainer.getOwner();
        return arg_0 -> ModelBakery.lambda$parseVariantKey$13(block, hashMap, arg_0);
    }

    @Nullable
    static <T extends Comparable<T>> T parseValue(Property<T> property, String string) {
        return (T)property.parseValue(string).orElse(null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public IUnbakedModel getUnbakedModel(ResourceLocation resourceLocation) {
        if (this.unbakedModels.containsKey(resourceLocation)) {
            return this.unbakedModels.get(resourceLocation);
        }
        if (this.unbakedModelLoadingQueue.contains(resourceLocation)) {
            throw new IllegalStateException("Circular reference while loading " + resourceLocation);
        }
        this.unbakedModelLoadingQueue.add(resourceLocation);
        IUnbakedModel iUnbakedModel = this.unbakedModels.get(MODEL_MISSING);
        while (!this.unbakedModelLoadingQueue.isEmpty()) {
            ResourceLocation resourceLocation2 = this.unbakedModelLoadingQueue.iterator().next();
            try {
                if (this.unbakedModels.containsKey(resourceLocation2)) continue;
                this.loadBlockstate(resourceLocation2);
            } catch (BlockStateDefinitionException blockStateDefinitionException) {
                LOGGER.warn(blockStateDefinitionException.getMessage());
                this.unbakedModels.put(resourceLocation2, iUnbakedModel);
            } catch (Exception exception) {
                LOGGER.warn("Unable to load model: '{}' referenced from: {}: {}", (Object)resourceLocation2, (Object)resourceLocation);
                LOGGER.warn(exception.getClass().getName() + ": " + exception.getMessage());
                this.unbakedModels.put(resourceLocation2, iUnbakedModel);
            } finally {
                this.unbakedModelLoadingQueue.remove(resourceLocation2);
            }
        }
        return this.unbakedModels.getOrDefault(resourceLocation, iUnbakedModel);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void loadBlockstate(ResourceLocation resourceLocation) throws Exception {
        List list;
        if (!(resourceLocation instanceof ModelResourceLocation)) {
            this.putModel(resourceLocation, this.loadModel(resourceLocation));
            return;
        }
        ModelResourceLocation modelResourceLocation = (ModelResourceLocation)resourceLocation;
        if (Objects.equals(modelResourceLocation.getVariant(), "inventory")) {
            ResourceLocation resourceLocation2 = new ResourceLocation(resourceLocation.getNamespace(), "item/" + resourceLocation.getPath());
            String string = resourceLocation.getPath();
            if (string.startsWith("optifine/") || string.startsWith("item/")) {
                resourceLocation2 = resourceLocation;
            }
            BlockModel blockModel = this.loadModel(resourceLocation2);
            this.putModel(modelResourceLocation, blockModel);
            this.unbakedModels.put(resourceLocation2, blockModel);
            return;
        }
        ResourceLocation resourceLocation3 = new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath());
        StateContainer stateContainer = Optional.ofNullable(STATE_CONTAINER_OVERRIDES.get(resourceLocation3)).orElseGet(() -> ModelBakery.lambda$loadBlockstate$14(resourceLocation3));
        this.containerHolder.setStateContainer(stateContainer);
        ImmutableList<Property<?>> immutableList = ImmutableList.copyOf(this.blockColors.getColorProperties((Block)stateContainer.getOwner()));
        ImmutableList immutableList2 = stateContainer.getValidStates();
        HashMap<ModelResourceLocation, BlockState> hashMap = Maps.newHashMap();
        immutableList2.forEach(arg_0 -> ModelBakery.lambda$loadBlockstate$15(hashMap, resourceLocation3, arg_0));
        HashMap hashMap2 = Maps.newHashMap();
        ResourceLocation resourceLocation4 = new ResourceLocation(resourceLocation.getNamespace(), "blockstates/" + resourceLocation.getPath() + ".json");
        IUnbakedModel iUnbakedModel = this.unbakedModels.get(MODEL_MISSING);
        ModelListWrapper modelListWrapper = new ModelListWrapper(ImmutableList.of(iUnbakedModel), ImmutableList.of());
        Pair<IUnbakedModel, Supplier<ModelListWrapper>> pair = Pair.of(iUnbakedModel, () -> ModelBakery.lambda$loadBlockstate$16(modelListWrapper));
        try {
            try {
                list = this.resourceManager.getAllResources(resourceLocation4).stream().map(this::lambda$loadBlockstate$17).collect(Collectors.toList());
            } catch (IOException iOException) {
                LOGGER.warn("Exception loading blockstate definition: {}: {}", (Object)resourceLocation4, (Object)iOException);
                HashMap<Object, Object> hashMap3 = Maps.newHashMap();
                hashMap.forEach((arg_0, arg_1) -> this.lambda$loadBlockstate$25(hashMap2, resourceLocation4, pair, hashMap3, arg_0, arg_1));
                hashMap3.forEach(this::lambda$loadBlockstate$26);
                return;
            }
        } catch (BlockStateDefinitionException blockStateDefinitionException) {
            try {
                throw blockStateDefinitionException;
                catch (Exception exception) {
                    throw new BlockStateDefinitionException(String.format("Exception loading blockstate definition: '%s': %s", resourceLocation4, exception));
                }
            } catch (Throwable throwable) {
                HashMap<Object, Object> hashMap4 = Maps.newHashMap();
                hashMap.forEach((arg_0, arg_1) -> this.lambda$loadBlockstate$25(hashMap2, resourceLocation4, pair, hashMap4, arg_0, arg_1));
                hashMap4.forEach(this::lambda$loadBlockstate$26);
                throw throwable;
            }
        }
        {
            for (Pair pair2 : list) {
                Multipart multipart;
                BlockModelDefinition blockModelDefinition = (BlockModelDefinition)pair2.getSecond();
                IdentityHashMap identityHashMap = Maps.newIdentityHashMap();
                if (blockModelDefinition.hasMultipartData()) {
                    multipart = blockModelDefinition.getMultipartData();
                    immutableList2.forEach(arg_0 -> ModelBakery.lambda$loadBlockstate$19(identityHashMap, multipart, immutableList, arg_0));
                } else {
                    multipart = null;
                }
                blockModelDefinition.getVariants().forEach((arg_0, arg_1) -> ModelBakery.lambda$loadBlockstate$23(immutableList2, stateContainer, identityHashMap, immutableList, multipart, pair, blockModelDefinition, resourceLocation4, pair2, arg_0, arg_1));
                hashMap2.putAll(identityHashMap);
            }
        }
        HashMap hashMap5 = Maps.newHashMap();
        hashMap.forEach((arg_0, arg_1) -> this.lambda$loadBlockstate$25(hashMap2, resourceLocation4, pair, hashMap5, arg_0, arg_1));
        hashMap5.forEach(this::lambda$loadBlockstate$26);
    }

    private void putModel(ResourceLocation resourceLocation, IUnbakedModel iUnbakedModel) {
        this.unbakedModels.put(resourceLocation, iUnbakedModel);
        this.unbakedModelLoadingQueue.addAll(iUnbakedModel.getDependencies());
    }

    private void addModelToCache(ResourceLocation resourceLocation) {
        IUnbakedModel iUnbakedModel = this.getUnbakedModel(resourceLocation);
        this.unbakedModels.put(resourceLocation, iUnbakedModel);
        this.topUnbakedModels.put(resourceLocation, iUnbakedModel);
    }

    public void loadTopModel(ModelResourceLocation modelResourceLocation) {
        IUnbakedModel iUnbakedModel = this.getUnbakedModel(modelResourceLocation);
        this.unbakedModels.put(modelResourceLocation, iUnbakedModel);
        this.topUnbakedModels.put(modelResourceLocation, iUnbakedModel);
    }

    private void registerModelIds(Iterable<BlockState> iterable) {
        int n = this.counterModelId++;
        iterable.forEach(arg_0 -> this.lambda$registerModelIds$27(n, arg_0));
    }

    @Nullable
    public IBakedModel bake(ResourceLocation resourceLocation, IModelTransform iModelTransform) {
        return this.getBakedModel(resourceLocation, iModelTransform, this.spriteMap::getSprite);
    }

    public IBakedModel getBakedModel(ResourceLocation resourceLocation, IModelTransform iModelTransform, Function<RenderMaterial, TextureAtlasSprite> function) {
        Object object;
        Triple<ResourceLocation, TransformationMatrix, Boolean> triple = Triple.of(resourceLocation, iModelTransform.getRotation(), iModelTransform.isUvLock());
        if (this.bakedModels.containsKey(triple)) {
            return this.bakedModels.get(triple);
        }
        if (this.spriteMap == null) {
            throw new IllegalStateException("bake called too early");
        }
        IUnbakedModel iUnbakedModel = this.getUnbakedModel(resourceLocation);
        if (iUnbakedModel instanceof BlockModel && ((BlockModel)(object = (BlockModel)iUnbakedModel)).getRootModel() == MODEL_GENERATED) {
            if (Reflector.ForgeHooksClient.exists()) {
                return ITEM_MODEL_GENERATOR.makeItemModel(function, (BlockModel)object).bakeModel(this, (BlockModel)object, function, iModelTransform, resourceLocation, true);
            }
            return ITEM_MODEL_GENERATOR.makeItemModel(this.spriteMap::getSprite, (BlockModel)object).bakeModel(this, (BlockModel)object, this.spriteMap::getSprite, iModelTransform, resourceLocation, true);
        }
        object = iUnbakedModel.bakeModel(this, this.spriteMap::getSprite, iModelTransform, resourceLocation);
        if (Reflector.ForgeHooksClient.exists()) {
            object = iUnbakedModel.bakeModel(this, function, iModelTransform, resourceLocation);
        }
        this.bakedModels.put(triple, (IBakedModel)object);
        return object;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private BlockModel loadModel(ResourceLocation resourceLocation) throws IOException {
        BlockModel blockModel;
        IResource iResource;
        Reader reader;
        block9: {
            String string;
            Object object;
            ResourceLocation resourceLocation2;
            String string2;
            block8: {
                reader = null;
                iResource = null;
                string2 = resourceLocation.getPath();
                resourceLocation2 = resourceLocation;
                if (!"builtin/generated".equals(string2)) break block8;
                BlockModel blockModel2 = MODEL_GENERATED;
                IOUtils.closeQuietly(reader);
                IOUtils.closeQuietly(iResource);
                return blockModel2;
            }
            if ("builtin/entity".equals(string2)) break block9;
            if (string2.startsWith("builtin/")) {
                object = string2.substring(8);
                string = BUILT_IN_MODELS.get(object);
                if (string == null) {
                    throw new FileNotFoundException(resourceLocation.toString());
                }
                reader = new StringReader(string);
            } else {
                resourceLocation2 = this.getModelLocation(resourceLocation);
                iResource = this.resourceManager.getResource(resourceLocation2);
                reader = new InputStreamReader(iResource.getInputStream(), StandardCharsets.UTF_8);
            }
            object = BlockModel.deserialize(reader);
            ((BlockModel)object).name = resourceLocation.toString();
            string = TextureUtils.getBasePath(resourceLocation2.getPath());
            ModelBakery.fixModelLocations((BlockModel)object, string);
            Object object2 = object;
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly((Closeable)iResource);
            return object2;
        }
        try {
            blockModel = MODEL_ENTITY;
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(iResource);
        }
        return blockModel;
    }

    public Map<ResourceLocation, IBakedModel> getTopBakedModels() {
        return this.topBakedModels;
    }

    public Object2IntMap<BlockState> getStateModelIds() {
        return this.stateModelIds;
    }

    private ResourceLocation getModelLocation(ResourceLocation resourceLocation) {
        String string = resourceLocation.getPath();
        if (string.startsWith("optifine/")) {
            if (!string.endsWith(".json")) {
                resourceLocation = new ResourceLocation(resourceLocation.getNamespace(), string + ".json");
            }
            return resourceLocation;
        }
        return new ResourceLocation(resourceLocation.getNamespace(), "models/" + resourceLocation.getPath() + ".json");
    }

    public static void fixModelLocations(BlockModel blockModel, String string) {
        ResourceLocation resourceLocation = ModelBakery.fixModelLocation(blockModel.parentLocation, string);
        if (resourceLocation != blockModel.parentLocation) {
            blockModel.parentLocation = resourceLocation;
        }
        if (blockModel.textures != null) {
            for (Map.Entry<String, Either<RenderMaterial, String>> entry : blockModel.textures.entrySet()) {
                RenderMaterial renderMaterial;
                ResourceLocation resourceLocation2;
                String string2;
                String string3;
                Either<RenderMaterial, String> either = entry.getValue();
                Optional<RenderMaterial> optional = either.left();
                if (!optional.isPresent() || (string3 = ModelBakery.fixResourcePath(string2 = (resourceLocation2 = (renderMaterial = optional.get()).getTextureLocation()).getPath(), string)).equals(string2)) continue;
                ResourceLocation resourceLocation3 = new ResourceLocation(resourceLocation2.getNamespace(), string3);
                RenderMaterial renderMaterial2 = new RenderMaterial(renderMaterial.getAtlasLocation(), resourceLocation3);
                Either either2 = Either.left(renderMaterial2);
                entry.setValue(either2);
            }
        }
    }

    public static ResourceLocation fixModelLocation(ResourceLocation resourceLocation, String string) {
        if (resourceLocation != null && string != null) {
            if (!resourceLocation.getNamespace().equals("minecraft")) {
                return resourceLocation;
            }
            String string2 = resourceLocation.getPath();
            String string3 = ModelBakery.fixResourcePath(string2, string);
            if (string3 != string2) {
                resourceLocation = new ResourceLocation(resourceLocation.getNamespace(), string3);
            }
            return resourceLocation;
        }
        return resourceLocation;
    }

    private static String fixResourcePath(String string, String string2) {
        string = TextureUtils.fixResourcePath(string, string2);
        string = StrUtils.removeSuffix(string, ".json");
        return StrUtils.removeSuffix(string, ".png");
    }

    public Set<ResourceLocation> getSpecialModels() {
        return Collections.emptySet();
    }

    public SpriteMap getSpriteMap() {
        return this.spriteMap;
    }

    private void lambda$registerModelIds$27(int n, BlockState blockState) {
        this.stateModelIds.put(blockState, n);
    }

    private void lambda$loadBlockstate$26(Object object, Object object2) {
        Iterator iterator2 = ((Set)object2).iterator();
        while (iterator2.hasNext()) {
            BlockState blockState = (BlockState)iterator2.next();
            if (blockState.getRenderType() == BlockRenderType.MODEL) continue;
            iterator2.remove();
            this.stateModelIds.put(blockState, 0);
        }
        if (((Set)object2).size() > 1) {
            this.registerModelIds((Set)object2);
        }
    }

    private void lambda$loadBlockstate$25(Map map, ResourceLocation resourceLocation, Pair pair, HashMap hashMap, ModelResourceLocation modelResourceLocation, BlockState blockState) {
        Pair pair2 = (Pair)map.get(blockState);
        if (pair2 == null) {
            LOGGER.warn("Exception loading blockstate definition: '{}' missing model for variant: '{}'", (Object)resourceLocation, (Object)modelResourceLocation);
            pair2 = pair;
        }
        this.putModel(modelResourceLocation, (IUnbakedModel)pair2.getFirst());
        try {
            ModelListWrapper modelListWrapper = (ModelListWrapper)((Supplier)pair2.getSecond()).get();
            ((Set)hashMap.computeIfAbsent(modelListWrapper, ModelBakery::lambda$loadBlockstate$24)).add(blockState);
        } catch (Exception exception) {
            LOGGER.warn("Exception evaluating model definition: '{}'", (Object)modelResourceLocation, (Object)exception);
        }
    }

    private static Object lambda$loadBlockstate$24(Object object) {
        return Sets.newIdentityHashSet();
    }

    private static void lambda$loadBlockstate$23(ImmutableList immutableList, StateContainer stateContainer, Map map, List list, Multipart multipart, Pair pair, BlockModelDefinition blockModelDefinition, ResourceLocation resourceLocation, Pair pair2, String string, VariantList variantList) {
        try {
            immutableList.stream().filter(ModelBakery.parseVariantKey(stateContainer, string)).forEach(arg_0 -> ModelBakery.lambda$loadBlockstate$22(map, variantList, list, multipart, pair, blockModelDefinition, arg_0));
        } catch (Exception exception) {
            LOGGER.warn("Exception loading blockstate definition: '{}' in resourcepack: '{}' for variant: '{}': {}", (Object)resourceLocation, pair2.getFirst(), (Object)string, (Object)exception.getMessage());
        }
    }

    private static void lambda$loadBlockstate$22(Map map, VariantList variantList, List list, Multipart multipart, Pair pair, BlockModelDefinition blockModelDefinition, BlockState blockState) {
        Pair<VariantList, Supplier<ModelListWrapper>> pair2 = map.put(blockState, Pair.of(variantList, () -> ModelBakery.lambda$loadBlockstate$20(blockState, variantList, list)));
        if (pair2 != null && pair2.getFirst() != multipart) {
            map.put(blockState, pair);
            throw new RuntimeException("Overlapping definition with: " + (String)blockModelDefinition.getVariants().entrySet().stream().filter(arg_0 -> ModelBakery.lambda$loadBlockstate$21(pair2, arg_0)).findFirst().get().getKey());
        }
    }

    private static boolean lambda$loadBlockstate$21(Pair pair, Map.Entry entry) {
        return entry.getValue() == pair.getFirst();
    }

    private static ModelListWrapper lambda$loadBlockstate$20(BlockState blockState, VariantList variantList, List list) {
        return ModelListWrapper.makeWrapper(blockState, variantList, list);
    }

    private static void lambda$loadBlockstate$19(Map map, Multipart multipart, List list, BlockState blockState) {
        Pair<Multipart, Supplier<ModelListWrapper>> pair = map.put(blockState, Pair.of(multipart, () -> ModelBakery.lambda$loadBlockstate$18(blockState, multipart, list)));
    }

    private static ModelListWrapper lambda$loadBlockstate$18(BlockState blockState, Multipart multipart, List list) {
        return ModelListWrapper.makeWrapper(blockState, multipart, list);
    }

    private Pair lambda$loadBlockstate$17(IResource iResource) {
        Pair<String, BlockModelDefinition> pair;
        block8: {
            InputStream inputStream = iResource.getInputStream();
            try {
                pair = Pair.of(iResource.getPackName(), BlockModelDefinition.fromJson(this.containerHolder, new InputStreamReader(inputStream, StandardCharsets.UTF_8)));
                if (inputStream == null) break block8;
            } catch (Throwable throwable) {
                try {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                } catch (Exception exception) {
                    throw new BlockStateDefinitionException(String.format("Exception loading blockstate definition: '%s' in resourcepack: '%s': %s", iResource.getLocation(), iResource.getPackName(), exception.getMessage()));
                }
            }
            inputStream.close();
        }
        return pair;
    }

    private static ModelListWrapper lambda$loadBlockstate$16(ModelListWrapper modelListWrapper) {
        return modelListWrapper;
    }

    private static void lambda$loadBlockstate$15(Map map, ResourceLocation resourceLocation, BlockState blockState) {
        BlockState blockState2 = map.put(BlockModelShapes.getModelLocation(resourceLocation, blockState), blockState);
    }

    private static StateContainer lambda$loadBlockstate$14(ResourceLocation resourceLocation) {
        return Registry.BLOCK.getOrDefault(resourceLocation).getStateContainer();
    }

    private static boolean lambda$parseVariantKey$13(Block block, Map map, BlockState blockState) {
        if (blockState != null && block == blockState.getBlock()) {
            for (Map.Entry entry : map.entrySet()) {
                if (Objects.equals(blockState.get((Property)entry.getKey()), entry.getValue())) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    private void lambda$uploadTextures$12(ResourceLocation resourceLocation) {
        IBakedModel iBakedModel = null;
        try {
            iBakedModel = this.bake(resourceLocation, ModelRotation.X0_Y0);
        } catch (Exception exception) {
            LOGGER.warn("Unable to bake model: '{}': {}", (Object)resourceLocation, (Object)exception);
        }
        if (iBakedModel != null) {
            this.topBakedModels.put(resourceLocation, iBakedModel);
        }
    }

    private static void lambda$processLoading$11(Pair pair) {
        LOGGER.warn("Unable to resolve texture reference: {} in {}", pair.getFirst(), pair.getSecond());
    }

    private static boolean lambda$processLoading$10(Pair pair) {
        return !((String)pair.getSecond()).equals(MODEL_MISSING_STRING);
    }

    private Stream lambda$processLoading$9(Set set, IUnbakedModel iUnbakedModel) {
        return iUnbakedModel.getTextures(this::getUnbakedModel, set).stream();
    }

    private void lambda$processLoading$8(BlockState blockState) {
        this.loadTopModel(BlockModelShapes.getModelLocation(blockState));
    }

    private void lambda$processLoading$7(ResourceLocation resourceLocation, StateContainer stateContainer) {
        stateContainer.getValidStates().forEach(arg_0 -> this.lambda$processLoading$6(resourceLocation, arg_0));
    }

    private void lambda$processLoading$6(ResourceLocation resourceLocation, BlockState blockState) {
        this.loadTopModel(BlockModelShapes.getModelLocation(resourceLocation, blockState));
    }

    private static void lambda$new$5(Object2IntOpenHashMap object2IntOpenHashMap) {
        object2IntOpenHashMap.defaultReturnValue(-1);
    }

    private static void lambda$static$4(BlockModel blockModel) {
        blockModel.name = "block entity marker";
    }

    private static void lambda$static$3(BlockModel blockModel) {
        blockModel.name = "generation marker";
    }

    private static void lambda$static$2(HashSet hashSet) {
        hashSet.add(LOCATION_WATER_FLOW);
        hashSet.add(LOCATION_LAVA_FLOW);
        hashSet.add(LOCATION_WATER_OVERLAY);
        hashSet.add(LOCATION_FIRE_0);
        hashSet.add(LOCATION_FIRE_1);
        hashSet.add(BellTileEntityRenderer.BELL_BODY_TEXTURE);
        hashSet.add(ConduitTileEntityRenderer.BASE_TEXTURE);
        hashSet.add(ConduitTileEntityRenderer.CAGE_TEXTURE);
        hashSet.add(ConduitTileEntityRenderer.WIND_TEXTURE);
        hashSet.add(ConduitTileEntityRenderer.VERTICAL_WIND_TEXTURE);
        hashSet.add(ConduitTileEntityRenderer.OPEN_EYE_TEXTURE);
        hashSet.add(ConduitTileEntityRenderer.CLOSED_EYE_TEXTURE);
        hashSet.add(EnchantmentTableTileEntityRenderer.TEXTURE_BOOK);
        hashSet.add(LOCATION_BANNER_BASE);
        hashSet.add(LOCATION_SHIELD_BASE);
        hashSet.add(LOCATION_SHIELD_NO_PATTERN);
        for (ResourceLocation resourceLocation : DESTROY_STAGES) {
            hashSet.add(new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, resourceLocation));
        }
        hashSet.add(new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, PlayerContainer.EMPTY_ARMOR_SLOT_HELMET));
        hashSet.add(new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, PlayerContainer.EMPTY_ARMOR_SLOT_CHESTPLATE));
        hashSet.add(new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, PlayerContainer.EMPTY_ARMOR_SLOT_LEGGINGS));
        hashSet.add(new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, PlayerContainer.EMPTY_ARMOR_SLOT_BOOTS));
        hashSet.add(new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, PlayerContainer.EMPTY_ARMOR_SLOT_SHIELD));
        Atlases.collectAllMaterials(hashSet::add);
    }

    private static ResourceLocation lambda$static$1(ResourceLocation resourceLocation) {
        return new ResourceLocation("textures/" + resourceLocation.getPath() + ".png");
    }

    private static ResourceLocation lambda$static$0(int n) {
        return new ResourceLocation("block/destroy_stage_" + n);
    }

    static class BlockStateDefinitionException
    extends RuntimeException {
        public BlockStateDefinitionException(String string) {
            super(string);
        }
    }

    static class ModelListWrapper {
        private final List<IUnbakedModel> models;
        private final List<Object> colorValues;

        public ModelListWrapper(List<IUnbakedModel> list, List<Object> list2) {
            this.models = list;
            this.colorValues = list2;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof ModelListWrapper)) {
                return true;
            }
            ModelListWrapper modelListWrapper = (ModelListWrapper)object;
            return Objects.equals(this.models, modelListWrapper.models) && Objects.equals(this.colorValues, modelListWrapper.colorValues);
        }

        public int hashCode() {
            return 31 * this.models.hashCode() + this.colorValues.hashCode();
        }

        public static ModelListWrapper makeWrapper(BlockState blockState, Multipart multipart, Collection<Property<?>> collection) {
            StateContainer<Block, BlockState> stateContainer = blockState.getBlock().getStateContainer();
            List list = multipart.getSelectors().stream().filter(arg_0 -> ModelListWrapper.lambda$makeWrapper$0(stateContainer, blockState, arg_0)).map(Selector::getVariantList).collect(ImmutableList.toImmutableList());
            List<Object> list2 = ModelListWrapper.getColorValues(blockState, collection);
            return new ModelListWrapper(list, list2);
        }

        public static ModelListWrapper makeWrapper(BlockState blockState, IUnbakedModel iUnbakedModel, Collection<Property<?>> collection) {
            List<Object> list = ModelListWrapper.getColorValues(blockState, collection);
            return new ModelListWrapper(ImmutableList.of(iUnbakedModel), list);
        }

        private static List<Object> getColorValues(BlockState blockState, Collection<Property<?>> collection) {
            return collection.stream().map(blockState::get).collect(ImmutableList.toImmutableList());
        }

        private static boolean lambda$makeWrapper$0(StateContainer stateContainer, BlockState blockState, Selector selector) {
            return selector.getPredicate(stateContainer).test(blockState);
        }
    }
}

