/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.Variant;
import net.minecraft.client.renderer.model.VariantList;
import net.minecraft.client.renderer.model.multipart.Multipart;
import net.minecraft.client.renderer.model.multipart.Selector;
import net.minecraft.state.StateContainer;
import net.minecraft.util.JSONUtils;

public class BlockModelDefinition {
    private final Map<String, VariantList> mapVariants = Maps.newLinkedHashMap();
    private Multipart multipart;

    public static BlockModelDefinition fromJson(ContainerHolder containerHolder, Reader reader) {
        return JSONUtils.fromJson(containerHolder.gson, reader, BlockModelDefinition.class);
    }

    public BlockModelDefinition(Map<String, VariantList> map, Multipart multipart) {
        this.multipart = multipart;
        this.mapVariants.putAll(map);
    }

    public BlockModelDefinition(List<BlockModelDefinition> list) {
        BlockModelDefinition blockModelDefinition = null;
        for (BlockModelDefinition blockModelDefinition2 : list) {
            if (blockModelDefinition2.hasMultipartData()) {
                this.mapVariants.clear();
                blockModelDefinition = blockModelDefinition2;
            }
            this.mapVariants.putAll(blockModelDefinition2.mapVariants);
        }
        if (blockModelDefinition != null) {
            this.multipart = blockModelDefinition.multipart;
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof BlockModelDefinition) {
            BlockModelDefinition blockModelDefinition = (BlockModelDefinition)object;
            if (this.mapVariants.equals(blockModelDefinition.mapVariants)) {
                return this.hasMultipartData() ? this.multipart.equals(blockModelDefinition.multipart) : !blockModelDefinition.hasMultipartData();
            }
        }
        return true;
    }

    public int hashCode() {
        return 31 * this.mapVariants.hashCode() + (this.hasMultipartData() ? this.multipart.hashCode() : 0);
    }

    public Map<String, VariantList> getVariants() {
        return this.mapVariants;
    }

    public boolean hasMultipartData() {
        return this.multipart != null;
    }

    public Multipart getMultipartData() {
        return this.multipart;
    }

    public static final class ContainerHolder {
        protected final Gson gson = new GsonBuilder().registerTypeAdapter((Type)((Object)BlockModelDefinition.class), new Deserializer()).registerTypeAdapter((Type)((Object)Variant.class), new Variant.Deserializer()).registerTypeAdapter((Type)((Object)VariantList.class), new VariantList.Deserializer()).registerTypeAdapter((Type)((Object)Multipart.class), new Multipart.Deserializer(this)).registerTypeAdapter((Type)((Object)Selector.class), new Selector.Deserializer()).create();
        private StateContainer<Block, BlockState> stateContainer;

        public StateContainer<Block, BlockState> getStateContainer() {
            return this.stateContainer;
        }

        public void setStateContainer(StateContainer<Block, BlockState> stateContainer) {
            this.stateContainer = stateContainer;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Deserializer
    implements JsonDeserializer<BlockModelDefinition> {
        @Override
        public BlockModelDefinition deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Map<String, VariantList> map = this.parseMapVariants(jsonDeserializationContext, jsonObject);
            Multipart multipart = this.parseMultipart(jsonDeserializationContext, jsonObject);
            if (!map.isEmpty() || multipart != null && !multipart.getVariants().isEmpty()) {
                return new BlockModelDefinition(map, multipart);
            }
            throw new JsonParseException("Neither 'variants' nor 'multipart' found");
        }

        protected Map<String, VariantList> parseMapVariants(JsonDeserializationContext jsonDeserializationContext, JsonObject jsonObject) {
            HashMap<String, VariantList> hashMap = Maps.newHashMap();
            if (jsonObject.has("variants")) {
                JsonObject jsonObject2 = JSONUtils.getJsonObject(jsonObject, "variants");
                for (Map.Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
                    hashMap.put(entry.getKey(), (VariantList)jsonDeserializationContext.deserialize(entry.getValue(), (Type)((Object)VariantList.class)));
                }
            }
            return hashMap;
        }

        @Nullable
        protected Multipart parseMultipart(JsonDeserializationContext jsonDeserializationContext, JsonObject jsonObject) {
            if (!jsonObject.has("multipart")) {
                return null;
            }
            JsonArray jsonArray = JSONUtils.getJsonArray(jsonObject, "multipart");
            return (Multipart)jsonDeserializationContext.deserialize(jsonArray, (Type)((Object)Multipart.class));
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

