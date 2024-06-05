package net.minecraft.server.management;

import java.lang.reflect.*;
import com.google.gson.*;
import java.text.*;
import com.mojang.authlib.*;
import java.util.*;

class Serializer implements JsonDeserializer, JsonSerializer
{
    private Serializer() {
    }
    
    Serializer(final PlayerProfileCache this$0, final Object p_i46332_2_) {
        this(this$0);
    }
    
    public JsonElement func_152676_a(final ProfileEntry p_152676_1_, final Type p_152676_2_, final JsonSerializationContext p_152676_3_) {
        final JsonObject var4 = new JsonObject();
        var4.addProperty("name", p_152676_1_.func_152668_a().getName());
        final UUID var5 = p_152676_1_.func_152668_a().getId();
        var4.addProperty("uuid", (var5 == null) ? "" : var5.toString());
        var4.addProperty("expiresOn", PlayerProfileCache.dateFormat.format(p_152676_1_.func_152670_b()));
        return (JsonElement)var4;
    }
    
    public ProfileEntry func_152675_a(final JsonElement p_152675_1_, final Type p_152675_2_, final JsonDeserializationContext p_152675_3_) {
        if (!p_152675_1_.isJsonObject()) {
            return null;
        }
        final JsonObject var4 = p_152675_1_.getAsJsonObject();
        final JsonElement var5 = var4.get("name");
        final JsonElement var6 = var4.get("uuid");
        final JsonElement var7 = var4.get("expiresOn");
        if (var5 == null || var6 == null) {
            return null;
        }
        final String var8 = var6.getAsString();
        final String var9 = var5.getAsString();
        Date var10 = null;
        if (var7 != null) {
            try {
                var10 = PlayerProfileCache.dateFormat.parse(var7.getAsString());
            }
            catch (ParseException var13) {
                var10 = null;
            }
        }
        if (var9 != null && var8 != null) {
            UUID var11;
            try {
                var11 = UUID.fromString(var8);
            }
            catch (Throwable var14) {
                return null;
            }
            final PlayerProfileCache this$0 = PlayerProfileCache.this;
            this$0.getClass();
            final ProfileEntry var12 = this$0.new ProfileEntry(new GameProfile(var11, var9), var10, null);
            return var12;
        }
        return null;
    }
    
    public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
        return this.func_152676_a((ProfileEntry)p_serialize_1_, p_serialize_2_, p_serialize_3_);
    }
    
    public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
        return this.func_152675_a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
    }
}
