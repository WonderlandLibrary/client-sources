/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.BlockFaceUV;
import net.minecraft.client.renderer.model.BlockPart;
import net.minecraft.client.renderer.model.BlockPartFace;
import net.minecraft.client.renderer.model.BuiltInModel;
import net.minecraft.client.renderer.model.FaceBakery;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemModelGenerator;
import net.minecraft.client.renderer.model.ItemOverride;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.ItemTransformVec3f;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.model.SimpleBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockModel
implements IUnbakedModel {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final FaceBakery FACE_BAKERY = new FaceBakery();
    @VisibleForTesting
    static final Gson SERIALIZER = new GsonBuilder().registerTypeAdapter((Type)((Object)BlockModel.class), new Deserializer()).registerTypeAdapter((Type)((Object)BlockPart.class), new BlockPart.Deserializer()).registerTypeAdapter((Type)((Object)BlockPartFace.class), new BlockPartFace.Deserializer()).registerTypeAdapter((Type)((Object)BlockFaceUV.class), new BlockFaceUV.Deserializer()).registerTypeAdapter((Type)((Object)ItemTransformVec3f.class), new ItemTransformVec3f.Deserializer()).registerTypeAdapter((Type)((Object)ItemCameraTransforms.class), new ItemCameraTransforms.Deserializer()).registerTypeAdapter((Type)((Object)ItemOverride.class), new ItemOverride.Deserializer()).create();
    private final List<BlockPart> elements;
    @Nullable
    private final GuiLight guiLight3d;
    private final boolean ambientOcclusion;
    private final ItemCameraTransforms cameraTransforms;
    private final List<ItemOverride> overrides;
    public String name = "";
    @VisibleForTesting
    protected final Map<String, Either<RenderMaterial, String>> textures;
    @Nullable
    protected BlockModel parent;
    @Nullable
    protected ResourceLocation parentLocation;

    public static BlockModel deserialize(Reader reader) {
        return JSONUtils.fromJson(SERIALIZER, reader, BlockModel.class);
    }

    public static BlockModel deserialize(String string) {
        return BlockModel.deserialize(new StringReader(string));
    }

    public BlockModel(@Nullable ResourceLocation resourceLocation, List<BlockPart> list, Map<String, Either<RenderMaterial, String>> map, boolean bl, @Nullable GuiLight guiLight, ItemCameraTransforms itemCameraTransforms, List<ItemOverride> list2) {
        this.elements = list;
        this.ambientOcclusion = bl;
        this.guiLight3d = guiLight;
        this.textures = map;
        this.parentLocation = resourceLocation;
        this.cameraTransforms = itemCameraTransforms;
        this.overrides = list2;
    }

    public List<BlockPart> getElements() {
        return this.elements.isEmpty() && this.parent != null ? this.parent.getElements() : this.elements;
    }

    public boolean isAmbientOcclusion() {
        return this.parent != null ? this.parent.isAmbientOcclusion() : this.ambientOcclusion;
    }

    public GuiLight getGuiLight() {
        if (this.guiLight3d != null) {
            return this.guiLight3d;
        }
        return this.parent != null ? this.parent.getGuiLight() : GuiLight.SIDE;
    }

    public List<ItemOverride> getOverrides() {
        return this.overrides;
    }

    private ItemOverrideList getItemOverrideList(ModelBakery modelBakery, BlockModel blockModel) {
        return this.overrides.isEmpty() ? ItemOverrideList.EMPTY : new ItemOverrideList(modelBakery, blockModel, modelBakery::getUnbakedModel, this.overrides);
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        HashSet<ResourceLocation> hashSet = Sets.newHashSet();
        for (ItemOverride itemOverride : this.overrides) {
            hashSet.add(itemOverride.getLocation());
        }
        if (this.parentLocation != null) {
            hashSet.add(this.parentLocation);
        }
        return hashSet;
    }

    @Override
    public Collection<RenderMaterial> getTextures(Function<ResourceLocation, IUnbakedModel> function, Set<Pair<String, String>> set) {
        LinkedHashSet<BlockModel> linkedHashSet = Sets.newLinkedHashSet();
        Object object = this;
        while (((BlockModel)object).parentLocation != null && ((BlockModel)object).parent == null) {
            linkedHashSet.add((BlockModel)object);
            Object object2 = function.apply(((BlockModel)object).parentLocation);
            if (object2 == null) {
                LOGGER.warn("No parent '{}' while loading model '{}'", (Object)this.parentLocation, object);
            }
            if (linkedHashSet.contains(object2)) {
                LOGGER.warn("Found 'parent' loop while loading model '{}' in chain: {} -> {}", object, (Object)linkedHashSet.stream().map(Object::toString).collect(Collectors.joining(" -> ")), (Object)this.parentLocation);
                object2 = null;
            }
            if (object2 == null) {
                ((BlockModel)object).parentLocation = ModelBakery.MODEL_MISSING;
                object2 = function.apply(((BlockModel)object).parentLocation);
            }
            if (!(object2 instanceof BlockModel)) {
                throw new IllegalStateException("BlockModel parent has to be a block model.");
            }
            ((BlockModel)object).parent = (BlockModel)object2;
            object = ((BlockModel)object).parent;
        }
        object = Sets.newHashSet(this.resolveTextureName("particle"));
        for (BlockPart blockPart : this.getElements()) {
            for (BlockPartFace blockPartFace : blockPart.mapFaces.values()) {
                RenderMaterial renderMaterial = this.resolveTextureName(blockPartFace.texture);
                if (Objects.equals(renderMaterial.getTextureLocation(), MissingTextureSprite.getLocation())) {
                    set.add(Pair.of(blockPartFace.texture, this.name));
                }
                object.add(renderMaterial);
            }
        }
        this.overrides.forEach(arg_0 -> this.lambda$getTextures$0(function, (Set)object, set, arg_0));
        if (this.getRootModel() == ModelBakery.MODEL_GENERATED) {
            ItemModelGenerator.LAYERS.forEach(arg_0 -> this.lambda$getTextures$1((Set)object, arg_0));
        }
        return object;
    }

    @Override
    public IBakedModel bakeModel(ModelBakery modelBakery, Function<RenderMaterial, TextureAtlasSprite> function, IModelTransform iModelTransform, ResourceLocation resourceLocation) {
        return this.bakeModel(modelBakery, this, function, iModelTransform, resourceLocation, false);
    }

    public IBakedModel bakeModel(ModelBakery modelBakery, BlockModel blockModel, Function<RenderMaterial, TextureAtlasSprite> function, IModelTransform iModelTransform, ResourceLocation resourceLocation, boolean bl) {
        TextureAtlasSprite textureAtlasSprite = function.apply(this.resolveTextureName("particle"));
        if (this.getRootModel() == ModelBakery.MODEL_ENTITY) {
            return new BuiltInModel(this.getAllTransforms(), this.getItemOverrideList(modelBakery, blockModel), textureAtlasSprite, this.getGuiLight().isSideLit());
        }
        SimpleBakedModel.Builder builder = new SimpleBakedModel.Builder(this, this.getItemOverrideList(modelBakery, blockModel), bl).setTexture(textureAtlasSprite);
        for (BlockPart blockPart : this.getElements()) {
            for (Direction direction : blockPart.mapFaces.keySet()) {
                BlockPartFace blockPartFace = blockPart.mapFaces.get(direction);
                TextureAtlasSprite textureAtlasSprite2 = function.apply(this.resolveTextureName(blockPartFace.texture));
                if (blockPartFace.cullFace == null) {
                    builder.addGeneralQuad(BlockModel.bakeFace(blockPart, blockPartFace, textureAtlasSprite2, direction, iModelTransform, resourceLocation));
                    continue;
                }
                builder.addFaceQuad(Direction.rotateFace(iModelTransform.getRotation().getMatrix(), blockPartFace.cullFace), BlockModel.bakeFace(blockPart, blockPartFace, textureAtlasSprite2, direction, iModelTransform, resourceLocation));
            }
        }
        return builder.build();
    }

    private static BakedQuad bakeFace(BlockPart blockPart, BlockPartFace blockPartFace, TextureAtlasSprite textureAtlasSprite, Direction direction, IModelTransform iModelTransform, ResourceLocation resourceLocation) {
        return FACE_BAKERY.bakeQuad(blockPart.positionFrom, blockPart.positionTo, blockPartFace, textureAtlasSprite, direction, iModelTransform, blockPart.partRotation, blockPart.shade, resourceLocation);
    }

    public boolean isTexturePresent(String string) {
        return !MissingTextureSprite.getLocation().equals(this.resolveTextureName(string).getTextureLocation());
    }

    public RenderMaterial resolveTextureName(String string) {
        if (BlockModel.startsWithHash(string)) {
            string = string.substring(1);
        }
        ArrayList<String> arrayList = Lists.newArrayList();
        Either<RenderMaterial, String> either;
        Optional<RenderMaterial> optional;
        while (!(optional = (either = this.findTexture(string)).left()).isPresent()) {
            string = either.right().get();
            if (arrayList.contains(string)) {
                LOGGER.warn("Unable to resolve texture due to reference chain {}->{} in {}", (Object)Joiner.on("->").join(arrayList), (Object)string, (Object)this.name);
                return new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, MissingTextureSprite.getLocation());
            }
            arrayList.add(string);
        }
        return optional.get();
    }

    private Either<RenderMaterial, String> findTexture(String string) {
        BlockModel blockModel = this;
        while (blockModel != null) {
            Either<RenderMaterial, String> either = blockModel.textures.get(string);
            if (either != null) {
                return either;
            }
            blockModel = blockModel.parent;
        }
        return Either.left(new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, MissingTextureSprite.getLocation()));
    }

    private static boolean startsWithHash(String string) {
        return string.charAt(0) == '#';
    }

    public BlockModel getRootModel() {
        return this.parent == null ? this : this.parent.getRootModel();
    }

    public ItemCameraTransforms getAllTransforms() {
        ItemTransformVec3f itemTransformVec3f = this.getTransform(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND);
        ItemTransformVec3f itemTransformVec3f2 = this.getTransform(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
        ItemTransformVec3f itemTransformVec3f3 = this.getTransform(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND);
        ItemTransformVec3f itemTransformVec3f4 = this.getTransform(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND);
        ItemTransformVec3f itemTransformVec3f5 = this.getTransform(ItemCameraTransforms.TransformType.HEAD);
        ItemTransformVec3f itemTransformVec3f6 = this.getTransform(ItemCameraTransforms.TransformType.GUI);
        ItemTransformVec3f itemTransformVec3f7 = this.getTransform(ItemCameraTransforms.TransformType.GROUND);
        ItemTransformVec3f itemTransformVec3f8 = this.getTransform(ItemCameraTransforms.TransformType.FIXED);
        return new ItemCameraTransforms(itemTransformVec3f, itemTransformVec3f2, itemTransformVec3f3, itemTransformVec3f4, itemTransformVec3f5, itemTransformVec3f6, itemTransformVec3f7, itemTransformVec3f8);
    }

    private ItemTransformVec3f getTransform(ItemCameraTransforms.TransformType transformType) {
        return this.parent != null && !this.cameraTransforms.hasCustomTransform(transformType) ? this.parent.getTransform(transformType) : this.cameraTransforms.getTransform(transformType);
    }

    public String toString() {
        return this.name;
    }

    private void lambda$getTextures$1(Set set, String string) {
        set.add(this.resolveTextureName(string));
    }

    private void lambda$getTextures$0(Function function, Set set, Set set2, ItemOverride itemOverride) {
        IUnbakedModel iUnbakedModel = (IUnbakedModel)function.apply(itemOverride.getLocation());
        if (!Objects.equals(iUnbakedModel, this)) {
            set.addAll(iUnbakedModel.getTextures(function, set2));
        }
    }

    public static enum GuiLight {
        FRONT("front"),
        SIDE("side");

        private final String name;

        private GuiLight(String string2) {
            this.name = string2;
        }

        public static GuiLight getLightFromName(String string) {
            for (GuiLight guiLight : GuiLight.values()) {
                if (!guiLight.name.equals(string)) continue;
                return guiLight;
            }
            throw new IllegalArgumentException("Invalid gui light: " + string);
        }

        public boolean isSideLit() {
            return this == SIDE;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Deserializer
    implements JsonDeserializer<BlockModel> {
        @Override
        public BlockModel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            Object object;
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            List<BlockPart> list = this.getModelElements(jsonDeserializationContext, jsonObject);
            String string = this.getParent(jsonObject);
            Map<String, Either<RenderMaterial, String>> map = this.getTextures(jsonObject);
            boolean bl = this.getAmbientOcclusionEnabled(jsonObject);
            ItemCameraTransforms itemCameraTransforms = ItemCameraTransforms.DEFAULT;
            if (jsonObject.has("display")) {
                object = JSONUtils.getJsonObject(jsonObject, "display");
                itemCameraTransforms = (ItemCameraTransforms)jsonDeserializationContext.deserialize((JsonElement)object, (Type)((Object)ItemCameraTransforms.class));
            }
            object = this.getItemOverrides(jsonDeserializationContext, jsonObject);
            GuiLight guiLight = null;
            if (jsonObject.has("gui_light")) {
                guiLight = GuiLight.getLightFromName(JSONUtils.getString(jsonObject, "gui_light"));
            }
            ResourceLocation resourceLocation = string.isEmpty() ? null : new ResourceLocation(string);
            return new BlockModel(resourceLocation, list, map, bl, guiLight, itemCameraTransforms, (List<ItemOverride>)object);
        }

        protected List<ItemOverride> getItemOverrides(JsonDeserializationContext jsonDeserializationContext, JsonObject jsonObject) {
            ArrayList<ItemOverride> arrayList = Lists.newArrayList();
            if (jsonObject.has("overrides")) {
                for (JsonElement jsonElement : JSONUtils.getJsonArray(jsonObject, "overrides")) {
                    arrayList.add((ItemOverride)jsonDeserializationContext.deserialize(jsonElement, (Type)((Object)ItemOverride.class)));
                }
            }
            return arrayList;
        }

        private Map<String, Either<RenderMaterial, String>> getTextures(JsonObject jsonObject) {
            ResourceLocation resourceLocation = AtlasTexture.LOCATION_BLOCKS_TEXTURE;
            HashMap<String, Either<RenderMaterial, String>> hashMap = Maps.newHashMap();
            if (jsonObject.has("textures")) {
                JsonObject jsonObject2 = JSONUtils.getJsonObject(jsonObject, "textures");
                for (Map.Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
                    hashMap.put(entry.getKey(), Deserializer.findTexture(resourceLocation, entry.getValue().getAsString()));
                }
            }
            return hashMap;
        }

        private static Either<RenderMaterial, String> findTexture(ResourceLocation resourceLocation, String string) {
            if (BlockModel.startsWithHash(string)) {
                return Either.right(string.substring(1));
            }
            ResourceLocation resourceLocation2 = ResourceLocation.tryCreate(string);
            if (resourceLocation2 == null) {
                throw new JsonParseException(string + " is not valid resource location");
            }
            return Either.left(new RenderMaterial(resourceLocation, resourceLocation2));
        }

        private String getParent(JsonObject jsonObject) {
            return JSONUtils.getString(jsonObject, "parent", "");
        }

        protected boolean getAmbientOcclusionEnabled(JsonObject jsonObject) {
            return JSONUtils.getBoolean(jsonObject, "ambientocclusion", true);
        }

        protected List<BlockPart> getModelElements(JsonDeserializationContext jsonDeserializationContext, JsonObject jsonObject) {
            ArrayList<BlockPart> arrayList = Lists.newArrayList();
            if (jsonObject.has("elements")) {
                for (JsonElement jsonElement : JSONUtils.getJsonArray(jsonObject, "elements")) {
                    arrayList.add((BlockPart)jsonDeserializationContext.deserialize(jsonElement, (Type)((Object)BlockPart.class)));
                }
            }
            return arrayList;
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

