package net.minecraft.client.renderer.block.model;

import java.lang.reflect.*;
import com.google.gson.*;

static class Deserializer implements JsonDeserializer
{
    public ItemCameraTransforms func_178352_a(final JsonElement p_178352_1_, final Type p_178352_2_, final JsonDeserializationContext p_178352_3_) {
        final JsonObject var4 = p_178352_1_.getAsJsonObject();
        ItemTransformVec3f var5 = ItemTransformVec3f.field_178366_a;
        ItemTransformVec3f var6 = ItemTransformVec3f.field_178366_a;
        ItemTransformVec3f var7 = ItemTransformVec3f.field_178366_a;
        ItemTransformVec3f var8 = ItemTransformVec3f.field_178366_a;
        if (var4.has("thirdperson")) {
            var5 = (ItemTransformVec3f)p_178352_3_.deserialize(var4.get("thirdperson"), (Type)ItemTransformVec3f.class);
        }
        if (var4.has("firstperson")) {
            var6 = (ItemTransformVec3f)p_178352_3_.deserialize(var4.get("firstperson"), (Type)ItemTransformVec3f.class);
        }
        if (var4.has("head")) {
            var7 = (ItemTransformVec3f)p_178352_3_.deserialize(var4.get("head"), (Type)ItemTransformVec3f.class);
        }
        if (var4.has("gui")) {
            var8 = (ItemTransformVec3f)p_178352_3_.deserialize(var4.get("gui"), (Type)ItemTransformVec3f.class);
        }
        return new ItemCameraTransforms(var5, var6, var7, var8);
    }
    
    public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
        return this.func_178352_a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
    }
}
