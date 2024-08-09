/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.data.ModelTextures;
import net.minecraft.data.ModelsResourceUtil;
import net.minecraft.data.StockTextureAliases;
import net.minecraft.util.ResourceLocation;

public class ModelsUtil {
    private final Optional<ResourceLocation> field_240225_a_;
    private final Set<StockTextureAliases> field_240226_b_;
    private Optional<String> field_240227_c_;

    public ModelsUtil(Optional<ResourceLocation> optional, Optional<String> optional2, StockTextureAliases ... stockTextureAliasesArray) {
        this.field_240225_a_ = optional;
        this.field_240227_c_ = optional2;
        this.field_240226_b_ = ImmutableSet.copyOf(stockTextureAliasesArray);
    }

    public ResourceLocation func_240228_a_(Block block, ModelTextures modelTextures, BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
        return this.func_240234_a_(ModelsResourceUtil.func_240222_a_(block, this.field_240227_c_.orElse("")), modelTextures, biConsumer);
    }

    public ResourceLocation func_240229_a_(Block block, String string, ModelTextures modelTextures, BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
        return this.func_240234_a_(ModelsResourceUtil.func_240222_a_(block, string + this.field_240227_c_.orElse("")), modelTextures, biConsumer);
    }

    public ResourceLocation func_240235_b_(Block block, String string, ModelTextures modelTextures, BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
        return this.func_240234_a_(ModelsResourceUtil.func_240222_a_(block, string), modelTextures, biConsumer);
    }

    public ResourceLocation func_240234_a_(ResourceLocation resourceLocation, ModelTextures modelTextures, BiConsumer<ResourceLocation, Supplier<JsonElement>> biConsumer) {
        Map<StockTextureAliases, ResourceLocation> map = this.func_240232_a_(modelTextures);
        biConsumer.accept(resourceLocation, () -> this.lambda$func_240234_a_$2(map));
        return resourceLocation;
    }

    private Map<StockTextureAliases, ResourceLocation> func_240232_a_(ModelTextures modelTextures) {
        return Streams.concat(this.field_240226_b_.stream(), modelTextures.func_240342_a_()).collect(ImmutableMap.toImmutableMap(Function.identity(), modelTextures::func_240348_a_));
    }

    private JsonElement lambda$func_240234_a_$2(Map map) {
        JsonObject jsonObject = new JsonObject();
        this.field_240225_a_.ifPresent(arg_0 -> ModelsUtil.lambda$func_240234_a_$0(jsonObject, arg_0));
        if (!map.isEmpty()) {
            JsonObject jsonObject2 = new JsonObject();
            map.forEach((arg_0, arg_1) -> ModelsUtil.lambda$func_240234_a_$1(jsonObject2, arg_0, arg_1));
            jsonObject.add("textures", jsonObject2);
        }
        return jsonObject;
    }

    private static void lambda$func_240234_a_$1(JsonObject jsonObject, StockTextureAliases stockTextureAliases, ResourceLocation resourceLocation) {
        jsonObject.addProperty(stockTextureAliases.getName(), resourceLocation.toString());
    }

    private static void lambda$func_240234_a_$0(JsonObject jsonObject, ResourceLocation resourceLocation) {
        jsonObject.addProperty("parent", resourceLocation.toString());
    }
}

