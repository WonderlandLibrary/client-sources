/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;
import java.lang.reflect.Type;
import java.util.UUID;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.text.ITextComponent;

public class ServerStatusResponse {
    private ITextComponent description;
    private Players players;
    private Version version;
    private String favicon;

    public ITextComponent getServerDescription() {
        return this.description;
    }

    public void setServerDescription(ITextComponent descriptionIn) {
        this.description = descriptionIn;
    }

    public Players getPlayers() {
        return this.players;
    }

    public void setPlayers(Players playersIn) {
        this.players = playersIn;
    }

    public Version getVersion() {
        return this.version;
    }

    public void setVersion(Version versionIn) {
        this.version = versionIn;
    }

    public void setFavicon(String faviconBlob) {
        this.favicon = faviconBlob;
    }

    public String getFavicon() {
        return this.favicon;
    }

    public static class Players {
        private final int maxPlayers;
        private final int onlinePlayerCount;
        private GameProfile[] players;

        public Players(int maxOnlinePlayers, int onlinePlayers) {
            this.maxPlayers = maxOnlinePlayers;
            this.onlinePlayerCount = onlinePlayers;
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

        public void setPlayers(GameProfile[] playersIn) {
            this.players = playersIn;
        }

        public static class Serializer
        implements JsonDeserializer<Players>,
        JsonSerializer<Players> {
            @Override
            public Players deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
                JsonArray jsonarray;
                JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "players");
                Players serverstatusresponse$players = new Players(JsonUtils.getInt(jsonobject, "max"), JsonUtils.getInt(jsonobject, "online"));
                if (JsonUtils.isJsonArray(jsonobject, "sample") && (jsonarray = JsonUtils.getJsonArray(jsonobject, "sample")).size() > 0) {
                    GameProfile[] agameprofile = new GameProfile[jsonarray.size()];
                    for (int i = 0; i < agameprofile.length; ++i) {
                        JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonarray.get(i), "player[" + i + "]");
                        String s = JsonUtils.getString(jsonobject1, "id");
                        agameprofile[i] = new GameProfile(UUID.fromString(s), JsonUtils.getString(jsonobject1, "name"));
                    }
                    serverstatusresponse$players.setPlayers(agameprofile);
                }
                return serverstatusresponse$players;
            }

            @Override
            public JsonElement serialize(Players p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
                JsonObject jsonobject = new JsonObject();
                jsonobject.addProperty("max", p_serialize_1_.getMaxPlayers());
                jsonobject.addProperty("online", p_serialize_1_.getOnlinePlayerCount());
                if (p_serialize_1_.getPlayers() != null && p_serialize_1_.getPlayers().length > 0) {
                    JsonArray jsonarray = new JsonArray();
                    for (int i = 0; i < p_serialize_1_.getPlayers().length; ++i) {
                        JsonObject jsonobject1 = new JsonObject();
                        UUID uuid = p_serialize_1_.getPlayers()[i].getId();
                        jsonobject1.addProperty("id", uuid == null ? "" : uuid.toString());
                        jsonobject1.addProperty("name", p_serialize_1_.getPlayers()[i].getName());
                        jsonarray.add(jsonobject1);
                    }
                    jsonobject.add("sample", jsonarray);
                }
                return jsonobject;
            }
        }
    }

    public static class Version {
        private final String name;
        private final int protocol;

        public Version(String nameIn, int protocolIn) {
            this.name = nameIn;
            this.protocol = protocolIn;
        }

        public String getName() {
            return this.name;
        }

        public int getProtocol() {
            return this.protocol;
        }

        public static class Serializer
        implements JsonDeserializer<Version>,
        JsonSerializer<Version> {
            @Override
            public Version deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
                JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "version");
                return new Version(JsonUtils.getString(jsonobject, "name"), JsonUtils.getInt(jsonobject, "protocol"));
            }

            @Override
            public JsonElement serialize(Version p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
                JsonObject jsonobject = new JsonObject();
                jsonobject.addProperty("name", p_serialize_1_.getName());
                jsonobject.addProperty("protocol", p_serialize_1_.getProtocol());
                return jsonobject;
            }
        }
    }

    public static class Serializer
    implements JsonDeserializer<ServerStatusResponse>,
    JsonSerializer<ServerStatusResponse> {
        @Override
        public ServerStatusResponse deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
            JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "status");
            ServerStatusResponse serverstatusresponse = new ServerStatusResponse();
            if (jsonobject.has("description")) {
                serverstatusresponse.setServerDescription((ITextComponent)p_deserialize_3_.deserialize(jsonobject.get("description"), (Type)((Object)ITextComponent.class)));
            }
            if (jsonobject.has("players")) {
                serverstatusresponse.setPlayers((Players)p_deserialize_3_.deserialize(jsonobject.get("players"), (Type)((Object)Players.class)));
            }
            if (jsonobject.has("version")) {
                serverstatusresponse.setVersion((Version)p_deserialize_3_.deserialize(jsonobject.get("version"), (Type)((Object)Version.class)));
            }
            if (jsonobject.has("favicon")) {
                serverstatusresponse.setFavicon(JsonUtils.getString(jsonobject, "favicon"));
            }
            return serverstatusresponse;
        }

        @Override
        public JsonElement serialize(ServerStatusResponse p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
            JsonObject jsonobject = new JsonObject();
            if (p_serialize_1_.getServerDescription() != null) {
                jsonobject.add("description", p_serialize_3_.serialize(p_serialize_1_.getServerDescription()));
            }
            if (p_serialize_1_.getPlayers() != null) {
                jsonobject.add("players", p_serialize_3_.serialize(p_serialize_1_.getPlayers()));
            }
            if (p_serialize_1_.getVersion() != null) {
                jsonobject.add("version", p_serialize_3_.serialize(p_serialize_1_.getVersion()));
            }
            if (p_serialize_1_.getFavicon() != null) {
                jsonobject.addProperty("favicon", p_serialize_1_.getFavicon());
            }
            return jsonobject;
        }
    }
}

