/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.block.model;

import com.google.common.annotations.VisibleForTesting;
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
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelBlock {
    private static final Logger LOGGER = LogManager.getLogger();
    @VisibleForTesting
    static final Gson SERIALIZER = new GsonBuilder().registerTypeAdapter((Type)((Object)ModelBlock.class), new Deserializer()).registerTypeAdapter((Type)((Object)BlockPart.class), new BlockPart.Deserializer()).registerTypeAdapter((Type)((Object)BlockPartFace.class), new BlockPartFace.Deserializer()).registerTypeAdapter((Type)((Object)BlockFaceUV.class), new BlockFaceUV.Deserializer()).registerTypeAdapter((Type)((Object)ItemTransformVec3f.class), new ItemTransformVec3f.Deserializer()).registerTypeAdapter((Type)((Object)ItemCameraTransforms.class), new ItemCameraTransforms.Deserializer()).registerTypeAdapter((Type)((Object)ItemOverride.class), new ItemOverride.Deserializer()).create();
    private final List<BlockPart> elements;
    private final boolean gui3d;
    private final boolean ambientOcclusion;
    private final ItemCameraTransforms cameraTransforms;
    private final List<ItemOverride> overrides;
    public String name = "";
    @VisibleForTesting
    protected final Map<String, String> textures;
    @VisibleForTesting
    protected ModelBlock parent;
    @VisibleForTesting
    protected ResourceLocation parentLocation;

    public static ModelBlock deserialize(Reader readerIn) {
        return JsonUtils.gsonDeserialize(SERIALIZER, readerIn, ModelBlock.class, false);
    }

    public static ModelBlock deserialize(String jsonString) {
        return ModelBlock.deserialize(new StringReader(jsonString));
    }

    public ModelBlock(@Nullable ResourceLocation parentLocationIn, List<BlockPart> elementsIn, Map<String, String> texturesIn, boolean ambientOcclusionIn, boolean gui3dIn, ItemCameraTransforms cameraTransformsIn, List<ItemOverride> overridesIn) {
        this.elements = elementsIn;
        this.ambientOcclusion = ambientOcclusionIn;
        this.gui3d = gui3dIn;
        this.textures = texturesIn;
        this.parentLocation = parentLocationIn;
        this.cameraTransforms = cameraTransformsIn;
        this.overrides = overridesIn;
    }

    public List<BlockPart> getElements() {
        return this.elements.isEmpty() && this.hasParent() ? this.parent.getElements() : this.elements;
    }

    private boolean hasParent() {
        return this.parent != null;
    }

    public boolean isAmbientOcclusion() {
        return this.hasParent() ? this.parent.isAmbientOcclusion() : this.ambientOcclusion;
    }

    public boolean isGui3d() {
        return this.gui3d;
    }

    public boolean isResolved() {
        return this.parentLocation == null || this.parent != null && this.parent.isResolved();
    }

    public void getParentFromMap(Map<ResourceLocation, ModelBlock> p_178299_1_) {
        if (this.parentLocation != null) {
            this.parent = p_178299_1_.get(this.parentLocation);
        }
    }

    public Collection<ResourceLocation> getOverrideLocations() {
        HashSet<ResourceLocation> set = Sets.newHashSet();
        for (ItemOverride itemoverride : this.overrides) {
            set.add(itemoverride.getLocation());
        }
        return set;
    }

    protected List<ItemOverride> getOverrides() {
        return this.overrides;
    }

    public ItemOverrideList createOverrides() {
        return this.overrides.isEmpty() ? ItemOverrideList.NONE : new ItemOverrideList(this.overrides);
    }

    public boolean isTexturePresent(String textureName) {
        return !"missingno".equals(this.resolveTextureName(textureName));
    }

    public String resolveTextureName(String textureName) {
        if (!this.startsWithHash((String)textureName)) {
            textureName = "#" + (String)textureName;
        }
        return this.resolveTextureName((String)textureName, new Bookkeep(this));
    }

    private String resolveTextureName(String textureName, Bookkeep p_178302_2_) {
        if (this.startsWithHash(textureName)) {
            if (this == p_178302_2_.modelExt) {
                LOGGER.warn("Unable to resolve texture due to upward reference: {} in {}", (Object)textureName, (Object)this.name);
                return "missingno";
            }
            String s = this.textures.get(textureName.substring(1));
            if (s == null && this.hasParent()) {
                s = this.parent.resolveTextureName(textureName, p_178302_2_);
            }
            p_178302_2_.modelExt = this;
            if (s != null && this.startsWithHash(s)) {
                s = p_178302_2_.model.resolveTextureName(s, p_178302_2_);
            }
            return s != null && !this.startsWithHash(s) ? s : "missingno";
        }
        return textureName;
    }

    private boolean startsWithHash(String hash) {
        return hash.charAt(0) == '#';
    }

    @Nullable
    public ResourceLocation getParentLocation() {
        return this.parentLocation;
    }

    public ModelBlock getRootModel() {
        return this.hasParent() ? this.parent.getRootModel() : this;
    }

    public ItemCameraTransforms getAllTransforms() {
        ItemTransformVec3f itemtransformvec3f = this.getTransform(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND);
        ItemTransformVec3f itemtransformvec3f1 = this.getTransform(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
        ItemTransformVec3f itemtransformvec3f2 = this.getTransform(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND);
        ItemTransformVec3f itemtransformvec3f3 = this.getTransform(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND);
        ItemTransformVec3f itemtransformvec3f4 = this.getTransform(ItemCameraTransforms.TransformType.HEAD);
        ItemTransformVec3f itemtransformvec3f5 = this.getTransform(ItemCameraTransforms.TransformType.GUI);
        ItemTransformVec3f itemtransformvec3f6 = this.getTransform(ItemCameraTransforms.TransformType.GROUND);
        ItemTransformVec3f itemtransformvec3f7 = this.getTransform(ItemCameraTransforms.TransformType.FIXED);
        return new ItemCameraTransforms(itemtransformvec3f, itemtransformvec3f1, itemtransformvec3f2, itemtransformvec3f3, itemtransformvec3f4, itemtransformvec3f5, itemtransformvec3f6, itemtransformvec3f7);
    }

    private ItemTransformVec3f getTransform(ItemCameraTransforms.TransformType type2) {
        return this.parent != null && !this.cameraTransforms.hasCustomTransform(type2) ? this.parent.getTransform(type2) : this.cameraTransforms.getTransform(type2);
    }

    public static void checkModelHierarchy(Map<ResourceLocation, ModelBlock> p_178312_0_) {
        for (ModelBlock modelblock : p_178312_0_.values()) {
            try {
                ModelBlock modelblock1 = modelblock.parent;
                ModelBlock modelblock2 = modelblock1.parent;
                while (modelblock1 != modelblock2) {
                    modelblock1 = modelblock1.parent;
                    modelblock2 = modelblock2.parent.parent;
                }
                throw new LoopException();
            } catch (NullPointerException nullPointerException) {
            }
        }
    }

    static final class Bookkeep {
        public final ModelBlock model;
        public ModelBlock modelExt;

        private Bookkeep(ModelBlock modelIn) {
            this.model = modelIn;
        }
    }

    public static class LoopException
    extends RuntimeException {
    }

    public static class Deserializer
    implements JsonDeserializer<ModelBlock> {
        @Override
        public ModelBlock deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
            JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
            List<BlockPart> list = this.getModelElements(p_deserialize_3_, jsonobject);
            String s = this.getParent(jsonobject);
            Map<String, String> map = this.getTextures(jsonobject);
            boolean flag = this.getAmbientOcclusionEnabled(jsonobject);
            ItemCameraTransforms itemcameratransforms = ItemCameraTransforms.DEFAULT;
            if (jsonobject.has("display")) {
                JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonobject, "display");
                itemcameratransforms = (ItemCameraTransforms)p_deserialize_3_.deserialize(jsonobject1, (Type)((Object)ItemCameraTransforms.class));
            }
            List<ItemOverride> list1 = this.getItemOverrides(p_deserialize_3_, jsonobject);
            ResourceLocation resourcelocation = s.isEmpty() ? null : new ResourceLocation(s);
            return new ModelBlock(resourcelocation, list, map, flag, true, itemcameratransforms, list1);
        }

        protected List<ItemOverride> getItemOverrides(JsonDeserializationContext deserializationContext, JsonObject object) {
            ArrayList<ItemOverride> list = Lists.newArrayList();
            if (object.has("overrides")) {
                for (JsonElement jsonelement : JsonUtils.getJsonArray(object, "overrides")) {
                    list.add((ItemOverride)deserializationContext.deserialize(jsonelement, (Type)((Object)ItemOverride.class)));
                }
            }
            return list;
        }

        private Map<String, String> getTextures(JsonObject object) {
            HashMap<String, String> map = Maps.newHashMap();
            if (object.has("textures")) {
                JsonObject jsonobject = object.getAsJsonObject("textures");
                for (Map.Entry<String, JsonElement> entry : jsonobject.entrySet()) {
                    map.put(entry.getKey(), entry.getValue().getAsString());
                }
            }
            return map;
        }

        private String getParent(JsonObject object) {
            return JsonUtils.getString(object, "parent", "");
        }

        protected boolean getAmbientOcclusionEnabled(JsonObject object) {
            return JsonUtils.getBoolean(object, "ambientocclusion", true);
        }

        protected List<BlockPart> getModelElements(JsonDeserializationContext deserializationContext, JsonObject object) {
            ArrayList<BlockPart> list = Lists.newArrayList();
            if (object.has("elements")) {
                for (JsonElement jsonelement : JsonUtils.getJsonArray(object, "elements")) {
                    list.add((BlockPart)deserializationContext.deserialize(jsonelement, (Type)((Object)BlockPart.class)));
                }
            }
            return list;
        }
    }
}

