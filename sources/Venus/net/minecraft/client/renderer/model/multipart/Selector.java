/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model.multipart;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Streams;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.VariantList;
import net.minecraft.client.renderer.model.multipart.AndCondition;
import net.minecraft.client.renderer.model.multipart.ICondition;
import net.minecraft.client.renderer.model.multipart.OrCondition;
import net.minecraft.client.renderer.model.multipart.PropertyValueCondition;
import net.minecraft.state.StateContainer;
import net.minecraft.util.JSONUtils;

public class Selector {
    private final ICondition condition;
    private final VariantList variantList;

    public Selector(ICondition iCondition, VariantList variantList) {
        if (iCondition == null) {
            throw new IllegalArgumentException("Missing condition for selector");
        }
        if (variantList == null) {
            throw new IllegalArgumentException("Missing variant for selector");
        }
        this.condition = iCondition;
        this.variantList = variantList;
    }

    public VariantList getVariantList() {
        return this.variantList;
    }

    public Predicate<BlockState> getPredicate(StateContainer<Block, BlockState> stateContainer) {
        return this.condition.getPredicate(stateContainer);
    }

    public boolean equals(Object object) {
        return this == object;
    }

    public int hashCode() {
        return System.identityHashCode(this);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Deserializer
    implements JsonDeserializer<Selector> {
        @Override
        public Selector deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            return new Selector(this.getWhenCondition(jsonObject), (VariantList)jsonDeserializationContext.deserialize(jsonObject.get("apply"), (Type)((Object)VariantList.class)));
        }

        private ICondition getWhenCondition(JsonObject jsonObject) {
            return jsonObject.has("when") ? Deserializer.getOrAndCondition(JSONUtils.getJsonObject(jsonObject, "when")) : ICondition.TRUE;
        }

        @VisibleForTesting
        static ICondition getOrAndCondition(JsonObject jsonObject) {
            Set<Map.Entry<String, JsonElement>> set = jsonObject.entrySet();
            if (set.isEmpty()) {
                throw new JsonParseException("No elements found in selector");
            }
            if (set.size() == 1) {
                if (jsonObject.has("OR")) {
                    List list = Streams.stream(JSONUtils.getJsonArray(jsonObject, "OR")).map(Deserializer::lambda$getOrAndCondition$0).collect(Collectors.toList());
                    return new OrCondition(list);
                }
                if (jsonObject.has("AND")) {
                    List list = Streams.stream(JSONUtils.getJsonArray(jsonObject, "AND")).map(Deserializer::lambda$getOrAndCondition$1).collect(Collectors.toList());
                    return new AndCondition(list);
                }
                return Deserializer.makePropertyValue(set.iterator().next());
            }
            return new AndCondition(set.stream().map(Deserializer::makePropertyValue).collect(Collectors.toList()));
        }

        private static ICondition makePropertyValue(Map.Entry<String, JsonElement> entry) {
            return new PropertyValueCondition(entry.getKey(), entry.getValue().getAsString());
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }

        private static ICondition lambda$getOrAndCondition$1(JsonElement jsonElement) {
            return Deserializer.getOrAndCondition(jsonElement.getAsJsonObject());
        }

        private static ICondition lambda$getOrAndCondition$0(JsonElement jsonElement) {
            return Deserializer.getOrAndCondition(jsonElement.getAsJsonObject());
        }
    }
}

