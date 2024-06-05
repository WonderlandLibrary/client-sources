package net.minecraft.client.renderer.block.model;

import java.lang.reflect.*;
import com.google.gson.*;
import net.minecraft.util.*;

static class Deserializer implements JsonDeserializer
{
    public BlockPartFace func_178338_a(final JsonElement p_178338_1_, final Type p_178338_2_, final JsonDeserializationContext p_178338_3_) {
        final JsonObject var4 = p_178338_1_.getAsJsonObject();
        final EnumFacing var5 = this.func_178339_c(var4);
        final int var6 = this.func_178337_a(var4);
        final String var7 = this.func_178340_b(var4);
        final BlockFaceUV var8 = (BlockFaceUV)p_178338_3_.deserialize((JsonElement)var4, (Type)BlockFaceUV.class);
        return new BlockPartFace(var5, var6, var7, var8);
    }
    
    protected int func_178337_a(final JsonObject p_178337_1_) {
        return JsonUtils.getJsonObjectIntegerFieldValueOrDefault(p_178337_1_, "tintindex", -1);
    }
    
    private String func_178340_b(final JsonObject p_178340_1_) {
        return JsonUtils.getJsonObjectStringFieldValue(p_178340_1_, "texture");
    }
    
    private EnumFacing func_178339_c(final JsonObject p_178339_1_) {
        final String var2 = JsonUtils.getJsonObjectStringFieldValueOrDefault(p_178339_1_, "cullface", "");
        return EnumFacing.byName(var2);
    }
    
    public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
        return this.func_178338_a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
    }
}
