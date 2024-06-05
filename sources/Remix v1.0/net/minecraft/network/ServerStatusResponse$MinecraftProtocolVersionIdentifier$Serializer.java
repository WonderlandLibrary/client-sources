package net.minecraft.network;

import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.gson.*;

public static class Serializer implements JsonDeserializer, JsonSerializer
{
    public MinecraftProtocolVersionIdentifier deserialize1(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
        final JsonObject var4 = JsonUtils.getElementAsJsonObject(p_deserialize_1_, "version");
        return new MinecraftProtocolVersionIdentifier(JsonUtils.getJsonObjectStringFieldValue(var4, "name"), JsonUtils.getJsonObjectIntegerFieldValue(var4, "protocol"));
    }
    
    public JsonElement serialize(final MinecraftProtocolVersionIdentifier p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
        final JsonObject var4 = new JsonObject();
        var4.addProperty("name", p_serialize_1_.getName());
        var4.addProperty("protocol", (Number)p_serialize_1_.getProtocol());
        return (JsonElement)var4;
    }
    
    public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
        return this.serialize((MinecraftProtocolVersionIdentifier)p_serialize_1_, p_serialize_2_, p_serialize_3_);
    }
    
    public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
        return this.deserialize1(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
    }
}
