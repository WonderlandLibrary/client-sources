/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IModelTransform;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.model.Variant;
import net.minecraft.client.renderer.model.WeightedBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public class VariantList
implements IUnbakedModel {
    private final List<Variant> variantList;

    public VariantList(List<Variant> list) {
        this.variantList = list;
    }

    public List<Variant> getVariantList() {
        return this.variantList;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof VariantList) {
            VariantList variantList = (VariantList)object;
            return this.variantList.equals(variantList.variantList);
        }
        return true;
    }

    public int hashCode() {
        return this.variantList.hashCode();
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return this.getVariantList().stream().map(Variant::getModelLocation).collect(Collectors.toSet());
    }

    @Override
    public Collection<RenderMaterial> getTextures(Function<ResourceLocation, IUnbakedModel> function, Set<Pair<String, String>> set) {
        return this.getVariantList().stream().map(Variant::getModelLocation).distinct().flatMap(arg_0 -> VariantList.lambda$getTextures$0(function, set, arg_0)).collect(Collectors.toSet());
    }

    @Override
    @Nullable
    public IBakedModel bakeModel(ModelBakery modelBakery, Function<RenderMaterial, TextureAtlasSprite> function, IModelTransform iModelTransform, ResourceLocation resourceLocation) {
        if (this.getVariantList().isEmpty()) {
            return null;
        }
        WeightedBakedModel.Builder builder = new WeightedBakedModel.Builder();
        for (Variant variant : this.getVariantList()) {
            IBakedModel iBakedModel = modelBakery.bake(variant.getModelLocation(), variant);
            builder.add(iBakedModel, variant.getWeight());
        }
        return builder.build();
    }

    private static Stream lambda$getTextures$0(Function function, Set set, ResourceLocation resourceLocation) {
        return ((IUnbakedModel)function.apply(resourceLocation)).getTextures(function, set).stream();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Deserializer
    implements JsonDeserializer<VariantList> {
        @Override
        public VariantList deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            ArrayList<Variant> arrayList = Lists.newArrayList();
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                if (jsonArray.size() == 0) {
                    throw new JsonParseException("Empty variant array");
                }
                for (JsonElement jsonElement2 : jsonArray) {
                    arrayList.add((Variant)jsonDeserializationContext.deserialize(jsonElement2, (Type)((Object)Variant.class)));
                }
            } else {
                arrayList.add((Variant)jsonDeserializationContext.deserialize(jsonElement, (Type)((Object)Variant.class)));
            }
            return new VariantList(arrayList);
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

