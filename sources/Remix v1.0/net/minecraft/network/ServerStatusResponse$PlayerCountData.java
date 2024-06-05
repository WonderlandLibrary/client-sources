package net.minecraft.network;

import com.mojang.authlib.*;
import java.lang.reflect.*;
import net.minecraft.util.*;
import java.util.*;
import com.google.gson.*;

public static class PlayerCountData
{
    private final int maxPlayers;
    private final int onlinePlayerCount;
    private GameProfile[] players;
    
    public PlayerCountData(final int p_i45274_1_, final int p_i45274_2_) {
        this.maxPlayers = p_i45274_1_;
        this.onlinePlayerCount = p_i45274_2_;
    }
    
    public int getMaxPlayers() {
        return this.maxPlayers;
    }
    
    public int getOnlinePlayerCount() {
        return this.onlinePlayerCount;
    }
    
    public GameProfile[] getPlayers() {
        return this.players;
    }
    
    public void setPlayers(final GameProfile[] playersIn) {
        this.players = playersIn;
    }
    
    public static class Serializer implements JsonDeserializer, JsonSerializer
    {
        public PlayerCountData deserialize1(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
            final JsonObject var4 = JsonUtils.getElementAsJsonObject(p_deserialize_1_, "players");
            final PlayerCountData var5 = new PlayerCountData(JsonUtils.getJsonObjectIntegerFieldValue(var4, "max"), JsonUtils.getJsonObjectIntegerFieldValue(var4, "online"));
            if (JsonUtils.jsonObjectFieldTypeIsArray(var4, "sample")) {
                final JsonArray var6 = JsonUtils.getJsonObjectJsonArrayField(var4, "sample");
                if (var6.size() > 0) {
                    final GameProfile[] var7 = new GameProfile[var6.size()];
                    for (int var8 = 0; var8 < var7.length; ++var8) {
                        final JsonObject var9 = JsonUtils.getElementAsJsonObject(var6.get(var8), "player[" + var8 + "]");
                        final String var10 = JsonUtils.getJsonObjectStringFieldValue(var9, "id");
                        var7[var8] = new GameProfile(UUID.fromString(var10), JsonUtils.getJsonObjectStringFieldValue(var9, "name"));
                    }
                    var5.setPlayers(var7);
                }
            }
            return var5;
        }
        
        public JsonElement serialize(final PlayerCountData p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
            final JsonObject var4 = new JsonObject();
            var4.addProperty("max", (Number)p_serialize_1_.getMaxPlayers());
            var4.addProperty("online", (Number)p_serialize_1_.getOnlinePlayerCount());
            if (p_serialize_1_.getPlayers() != null && p_serialize_1_.getPlayers().length > 0) {
                final JsonArray var5 = new JsonArray();
                for (int var6 = 0; var6 < p_serialize_1_.getPlayers().length; ++var6) {
                    final JsonObject var7 = new JsonObject();
                    final UUID var8 = p_serialize_1_.getPlayers()[var6].getId();
                    var7.addProperty("id", (var8 == null) ? "" : var8.toString());
                    var7.addProperty("name", p_serialize_1_.getPlayers()[var6].getName());
                    var5.add((JsonElement)var7);
                }
                var4.add("sample", (JsonElement)var5);
            }
            return (JsonElement)var4;
        }
        
        public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
            return this.serialize((PlayerCountData)p_serialize_1_, p_serialize_2_, p_serialize_3_);
        }
        
        public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
            return this.deserialize1(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
        }
    }
}
