package net.minecraft.network;

import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.gson.*;

public static class Serializer implements JsonDeserializer, JsonSerializer
{
    public ServerStatusResponse deserialize1(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
        final JsonObject var4 = JsonUtils.getElementAsJsonObject(p_deserialize_1_, "status");
        final ServerStatusResponse var5 = new ServerStatusResponse();
        if (var4.has("description")) {
            var5.setServerDescription((IChatComponent)p_deserialize_3_.deserialize(var4.get("description"), (Type)IChatComponent.class));
        }
        if (var4.has("players")) {
            var5.setPlayerCountData((PlayerCountData)p_deserialize_3_.deserialize(var4.get("players"), (Type)PlayerCountData.class));
        }
        if (var4.has("version")) {
            var5.setProtocolVersionInfo((MinecraftProtocolVersionIdentifier)p_deserialize_3_.deserialize(var4.get("version"), (Type)MinecraftProtocolVersionIdentifier.class));
        }
        if (var4.has("favicon")) {
            var5.setFavicon(JsonUtils.getJsonObjectStringFieldValue(var4, "favicon"));
        }
        return var5;
    }
    
    public JsonElement serialize(final ServerStatusResponse p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
        final JsonObject var4 = new JsonObject();
        if (p_serialize_1_.getServerDescription() != null) {
            var4.add("description", p_serialize_3_.serialize((Object)p_serialize_1_.getServerDescription()));
        }
        if (p_serialize_1_.getPlayerCountData() != null) {
            var4.add("players", p_serialize_3_.serialize((Object)p_serialize_1_.getPlayerCountData()));
        }
        if (p_serialize_1_.getProtocolVersionInfo() != null) {
            var4.add("version", p_serialize_3_.serialize((Object)p_serialize_1_.getProtocolVersionInfo()));
        }
        if (p_serialize_1_.getFavicon() != null) {
            var4.addProperty("favicon", p_serialize_1_.getFavicon());
        }
        return (JsonElement)var4;
    }
    
    public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
        return this.serialize((ServerStatusResponse)p_serialize_1_, p_serialize_2_, p_serialize_3_);
    }
    
    public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
        return this.deserialize1(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
    }
}
