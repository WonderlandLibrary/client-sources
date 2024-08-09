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
import net.minecraft.util.JSONUtils;
import net.minecraft.util.text.ITextComponent;

public class ServerStatusResponse {
    private ITextComponent description;
    private Players players;
    private Version version;
    private String favicon;

    public ITextComponent getServerDescription() {
        return this.description;
    }

    public void setServerDescription(ITextComponent iTextComponent) {
        this.description = iTextComponent;
    }

    public Players getPlayers() {
        return this.players;
    }

    public void setPlayers(Players players) {
        this.players = players;
    }

    public Version getVersion() {
        return this.version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public void setFavicon(String string) {
        this.favicon = string;
    }

    public String getFavicon() {
        return this.favicon;
    }

    public static class Players {
        private final int maxPlayers;
        private final int onlinePlayerCount;
        private GameProfile[] players;

        public Players(int n, int n2) {
            this.maxPlayers = n;
            this.onlinePlayerCount = n2;
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

        public void setPlayers(GameProfile[] gameProfileArray) {
            this.players = gameProfileArray;
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        public static class Serializer
        implements JsonDeserializer<Players>,
        JsonSerializer<Players> {
            @Override
            public Players deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                JsonArray jsonArray;
                JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "players");
                Players players = new Players(JSONUtils.getInt(jsonObject, "max"), JSONUtils.getInt(jsonObject, "online"));
                if (JSONUtils.isJsonArray(jsonObject, "sample") && (jsonArray = JSONUtils.getJsonArray(jsonObject, "sample")).size() > 0) {
                    GameProfile[] gameProfileArray = new GameProfile[jsonArray.size()];
                    for (int i = 0; i < gameProfileArray.length; ++i) {
                        JsonObject jsonObject2 = JSONUtils.getJsonObject(jsonArray.get(i), "player[" + i + "]");
                        String string = JSONUtils.getString(jsonObject2, "id");
                        gameProfileArray[i] = new GameProfile(UUID.fromString(string), JSONUtils.getString(jsonObject2, "name"));
                    }
                    players.setPlayers(gameProfileArray);
                }
                return players;
            }

            @Override
            public JsonElement serialize(Players players, Type type, JsonSerializationContext jsonSerializationContext) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("max", players.getMaxPlayers());
                jsonObject.addProperty("online", players.getOnlinePlayerCount());
                if (players.getPlayers() != null && players.getPlayers().length > 0) {
                    JsonArray jsonArray = new JsonArray();
                    for (int i = 0; i < players.getPlayers().length; ++i) {
                        JsonObject jsonObject2 = new JsonObject();
                        UUID uUID = players.getPlayers()[i].getId();
                        jsonObject2.addProperty("id", uUID == null ? "" : uUID.toString());
                        jsonObject2.addProperty("name", players.getPlayers()[i].getName());
                        jsonArray.add(jsonObject2);
                    }
                    jsonObject.add("sample", jsonArray);
                }
                return jsonObject;
            }

            @Override
            public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return this.deserialize(jsonElement, type, jsonDeserializationContext);
            }

            @Override
            public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
                return this.serialize((Players)object, type, jsonSerializationContext);
            }
        }
    }

    public static class Version {
        private final String name;
        private final int protocol;

        public Version(String string, int n) {
            this.name = string;
            this.protocol = n;
        }

        public String getName() {
            return this.name;
        }

        public int getProtocol() {
            return this.protocol;
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        public static class Serializer
        implements JsonDeserializer<Version>,
        JsonSerializer<Version> {
            @Override
            public Version deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "version");
                return new Version(JSONUtils.getString(jsonObject, "name"), JSONUtils.getInt(jsonObject, "protocol"));
            }

            @Override
            public JsonElement serialize(Version version, Type type, JsonSerializationContext jsonSerializationContext) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name", version.getName());
                jsonObject.addProperty("protocol", version.getProtocol());
                return jsonObject;
            }

            @Override
            public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return this.deserialize(jsonElement, type, jsonDeserializationContext);
            }

            @Override
            public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
                return this.serialize((Version)object, type, jsonSerializationContext);
            }
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements JsonDeserializer<ServerStatusResponse>,
    JsonSerializer<ServerStatusResponse> {
        @Override
        public ServerStatusResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "status");
            ServerStatusResponse serverStatusResponse = new ServerStatusResponse();
            if (jsonObject.has("description")) {
                serverStatusResponse.setServerDescription((ITextComponent)jsonDeserializationContext.deserialize(jsonObject.get("description"), (Type)((Object)ITextComponent.class)));
            }
            if (jsonObject.has("players")) {
                serverStatusResponse.setPlayers((Players)jsonDeserializationContext.deserialize(jsonObject.get("players"), (Type)((Object)Players.class)));
            }
            if (jsonObject.has("version")) {
                serverStatusResponse.setVersion((Version)jsonDeserializationContext.deserialize(jsonObject.get("version"), (Type)((Object)Version.class)));
            }
            if (jsonObject.has("favicon")) {
                serverStatusResponse.setFavicon(JSONUtils.getString(jsonObject, "favicon"));
            }
            return serverStatusResponse;
        }

        @Override
        public JsonElement serialize(ServerStatusResponse serverStatusResponse, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject = new JsonObject();
            if (serverStatusResponse.getServerDescription() != null) {
                jsonObject.add("description", jsonSerializationContext.serialize(serverStatusResponse.getServerDescription()));
            }
            if (serverStatusResponse.getPlayers() != null) {
                jsonObject.add("players", jsonSerializationContext.serialize(serverStatusResponse.getPlayers()));
            }
            if (serverStatusResponse.getVersion() != null) {
                jsonObject.add("version", jsonSerializationContext.serialize(serverStatusResponse.getVersion()));
            }
            if (serverStatusResponse.getFavicon() != null) {
                jsonObject.addProperty("favicon", serverStatusResponse.getFavicon());
            }
            return jsonObject;
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }

        @Override
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((ServerStatusResponse)object, type, jsonSerializationContext);
        }
    }
}

