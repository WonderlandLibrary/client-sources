package net.minecraft.client.renderer.block.model;

import java.lang.reflect.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import com.google.gson.*;

public static class Deserializer implements JsonDeserializer
{
    public Variant func_178425_a(final JsonElement p_178425_1_, final Type p_178425_2_, final JsonDeserializationContext p_178425_3_) {
        final JsonObject var4 = p_178425_1_.getAsJsonObject();
        final String var5 = this.func_178424_b(var4);
        final ModelRotation var6 = this.func_178428_a(var4);
        final boolean var7 = this.func_178429_d(var4);
        final int var8 = this.func_178427_c(var4);
        return new Variant(this.func_178426_a(var5), var6, var7, var8);
    }
    
    private ResourceLocation func_178426_a(final String p_178426_1_) {
        ResourceLocation var2 = new ResourceLocation(p_178426_1_);
        var2 = new ResourceLocation(var2.getResourceDomain(), "block/" + var2.getResourcePath());
        return var2;
    }
    
    private boolean func_178429_d(final JsonObject p_178429_1_) {
        return JsonUtils.getJsonObjectBooleanFieldValueOrDefault(p_178429_1_, "uvlock", false);
    }
    
    protected ModelRotation func_178428_a(final JsonObject p_178428_1_) {
        final int var2 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(p_178428_1_, "x", 0);
        final int var3 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(p_178428_1_, "y", 0);
        final ModelRotation var4 = ModelRotation.func_177524_a(var2, var3);
        if (var4 == null) {
            throw new JsonParseException("Invalid BlockModelRotation x: " + var2 + ", y: " + var3);
        }
        return var4;
    }
    
    protected String func_178424_b(final JsonObject p_178424_1_) {
        return JsonUtils.getJsonObjectStringFieldValue(p_178424_1_, "model");
    }
    
    protected int func_178427_c(final JsonObject p_178427_1_) {
        return JsonUtils.getJsonObjectIntegerFieldValueOrDefault(p_178427_1_, "weight", 1);
    }
    
    public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
        return this.func_178425_a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
    }
}
