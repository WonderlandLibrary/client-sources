package net.minecraft.client.renderer.block.model;

import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.gson.*;

static class Deserializer implements JsonDeserializer
{
    public BlockFaceUV func_178293_a(final JsonElement p_178293_1_, final Type p_178293_2_, final JsonDeserializationContext p_178293_3_) {
        final JsonObject var4 = p_178293_1_.getAsJsonObject();
        final float[] var5 = this.func_178292_b(var4);
        final int var6 = this.func_178291_a(var4);
        return new BlockFaceUV(var5, var6);
    }
    
    protected int func_178291_a(final JsonObject p_178291_1_) {
        final int var2 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(p_178291_1_, "rotation", 0);
        if (var2 >= 0 && var2 % 90 == 0 && var2 / 90 <= 3) {
            return var2;
        }
        throw new JsonParseException("Invalid rotation " + var2 + " found, only 0/90/180/270 allowed");
    }
    
    private float[] func_178292_b(final JsonObject p_178292_1_) {
        if (!p_178292_1_.has("uv")) {
            return null;
        }
        final JsonArray var2 = JsonUtils.getJsonObjectJsonArrayField(p_178292_1_, "uv");
        if (var2.size() != 4) {
            throw new JsonParseException("Expected 4 uv values, found: " + var2.size());
        }
        final float[] var3 = new float[4];
        for (int var4 = 0; var4 < var3.length; ++var4) {
            var3[var4] = JsonUtils.getJsonElementFloatValue(var2.get(var4), "uv[" + var4 + "]");
        }
        return var3;
    }
    
    public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
        return this.func_178293_a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
    }
}
