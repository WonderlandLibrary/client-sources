package net.minecraft.network;

import com.mojang.authlib.*;
import java.lang.reflect.*;
import java.util.*;
import com.google.gson.*;
import net.minecraft.util.*;

public class ServerStatusResponse
{
    private IChatComponent serverMotd;
    private String favicon;
    private MinecraftProtocolVersionIdentifier protocolVersion;
    private PlayerCountData playerCount;
    
    public IChatComponent getServerDescription() {
        return this.serverMotd;
    }
    
    public void setFavicon(final String favicon) {
        this.favicon = favicon;
    }
    
    public MinecraftProtocolVersionIdentifier getProtocolVersionInfo() {
        return this.protocolVersion;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setServerDescription(final IChatComponent serverMotd) {
        this.serverMotd = serverMotd;
    }
    
    public PlayerCountData getPlayerCountData() {
        return this.playerCount;
    }
    
    public void setProtocolVersionInfo(final MinecraftProtocolVersionIdentifier protocolVersion) {
        this.protocolVersion = protocolVersion;
    }
    
    public String getFavicon() {
        return this.favicon;
    }
    
    public void setPlayerCountData(final PlayerCountData playerCount) {
        this.playerCount = playerCount;
    }
    
    public static class PlayerCountData
    {
        private GameProfile[] players;
        private final int maxPlayers;
        private final int onlinePlayerCount;
        
        public void setPlayers(final GameProfile[] players) {
            this.players = players;
        }
        
        public int getOnlinePlayerCount() {
            return this.onlinePlayerCount;
        }
        
        public GameProfile[] getPlayers() {
            return this.players;
        }
        
        public int getMaxPlayers() {
            return this.maxPlayers;
        }
        
        public PlayerCountData(final int maxPlayers, final int onlinePlayerCount) {
            this.maxPlayers = maxPlayers;
            this.onlinePlayerCount = onlinePlayerCount;
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public static class Serializer implements JsonDeserializer<PlayerCountData>, JsonSerializer<PlayerCountData>
        {
            private static final String[] I;
            
            public JsonElement serialize(final PlayerCountData playerCountData, final Type type, final JsonSerializationContext jsonSerializationContext) {
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(Serializer.I[0x74 ^ 0x7D], (Number)playerCountData.getMaxPlayers());
                jsonObject.addProperty(Serializer.I[0x60 ^ 0x6A], (Number)playerCountData.getOnlinePlayerCount());
                if (playerCountData.getPlayers() != null && playerCountData.getPlayers().length > 0) {
                    final JsonArray jsonArray = new JsonArray();
                    int i = "".length();
                    "".length();
                    if (0 >= 3) {
                        throw null;
                    }
                    while (i < playerCountData.getPlayers().length) {
                        final JsonObject jsonObject2 = new JsonObject();
                        final UUID id = playerCountData.getPlayers()[i].getId();
                        final JsonObject jsonObject3 = jsonObject2;
                        final String s = Serializer.I[0x1 ^ 0xA];
                        String string;
                        if (id == null) {
                            string = Serializer.I[0xAA ^ 0xA6];
                            "".length();
                            if (true != true) {
                                throw null;
                            }
                        }
                        else {
                            string = id.toString();
                        }
                        jsonObject3.addProperty(s, string);
                        jsonObject2.addProperty(Serializer.I[0x51 ^ 0x5C], playerCountData.getPlayers()[i].getName());
                        jsonArray.add((JsonElement)jsonObject2);
                        ++i;
                    }
                    jsonObject.add(Serializer.I[0xA2 ^ 0xAC], (JsonElement)jsonArray);
                }
                return (JsonElement)jsonObject;
            }
            
            public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
                return this.serialize((PlayerCountData)o, type, jsonSerializationContext);
            }
            
            static {
                I();
            }
            
            private static void I() {
                (I = new String[0x9D ^ 0x92])["".length()] = I("#\n\t\u0011\u0001!\u0015", "Sfhhd");
                Serializer.I[" ".length()] = I("5\u0018*", "XyRTU");
                Serializer.I["  ".length()] = I("\r\b<\u0005\u0007\u0007", "bfPli");
                Serializer.I["   ".length()] = I("\u000b-;\u001a9\u001d", "xLVjU");
                Serializer.I[0x3A ^ 0x3E] = I("4\u0010:4\u000f\"", "GqWDc");
                Serializer.I[0x5F ^ 0x5A] = I("$*$1\u000b&\u001d", "TFEHn");
                Serializer.I[0x59 ^ 0x5F] = I("\r", "PbKCy");
                Serializer.I[0x84 ^ 0x83] = I("\u0000\t", "imTeH");
                Serializer.I[0xAB ^ 0xA3] = I("\u001e5\u000b+", "pTfNC");
                Serializer.I[0x24 ^ 0x2D] = I(": \f", "WAtgj");
                Serializer.I[0x16 ^ 0x1C] = I("\u0005/8\u000b/\u000f", "jATbA");
                Serializer.I[0x1A ^ 0x11] = I("\u001b\u0000", "rdXfo");
                Serializer.I[0x52 ^ 0x5E] = I("", "GPHZi");
                Serializer.I[0xCD ^ 0xC0] = I(",\u0011\u00004", "BpmQK");
                Serializer.I[0x9D ^ 0x93] = I("+%\u0006\u001e\u001f=", "XDkns");
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (0 == 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return this.deserialize(jsonElement, type, jsonDeserializationContext);
            }
            
            public PlayerCountData deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, Serializer.I["".length()]);
                final PlayerCountData playerCountData = new PlayerCountData(JsonUtils.getInt(jsonObject, Serializer.I[" ".length()]), JsonUtils.getInt(jsonObject, Serializer.I["  ".length()]));
                if (JsonUtils.isJsonArray(jsonObject, Serializer.I["   ".length()])) {
                    final JsonArray jsonArray = JsonUtils.getJsonArray(jsonObject, Serializer.I[0x2 ^ 0x6]);
                    if (jsonArray.size() > 0) {
                        final GameProfile[] players = new GameProfile[jsonArray.size()];
                        int i = "".length();
                        "".length();
                        if (2 == -1) {
                            throw null;
                        }
                        while (i < players.length) {
                            final JsonObject jsonObject2 = JsonUtils.getJsonObject(jsonArray.get(i), Serializer.I[0xC5 ^ 0xC0] + i + Serializer.I[0x5B ^ 0x5D]);
                            players[i] = new GameProfile(UUID.fromString(JsonUtils.getString(jsonObject2, Serializer.I[0x61 ^ 0x66])), JsonUtils.getString(jsonObject2, Serializer.I[0x54 ^ 0x5C]));
                            ++i;
                        }
                        playerCountData.setPlayers(players);
                    }
                }
                return playerCountData;
            }
        }
    }
    
    public static class Serializer implements JsonDeserializer<ServerStatusResponse>, JsonSerializer<ServerStatusResponse>
    {
        private static final String[] I;
        
        public JsonElement serialize(final ServerStatusResponse serverStatusResponse, final Type type, final JsonSerializationContext jsonSerializationContext) {
            final JsonObject jsonObject = new JsonObject();
            if (serverStatusResponse.getServerDescription() != null) {
                jsonObject.add(Serializer.I[0xB0 ^ 0xB9], jsonSerializationContext.serialize((Object)serverStatusResponse.getServerDescription()));
            }
            if (serverStatusResponse.getPlayerCountData() != null) {
                jsonObject.add(Serializer.I[0xBB ^ 0xB1], jsonSerializationContext.serialize((Object)serverStatusResponse.getPlayerCountData()));
            }
            if (serverStatusResponse.getProtocolVersionInfo() != null) {
                jsonObject.add(Serializer.I[0x86 ^ 0x8D], jsonSerializationContext.serialize((Object)serverStatusResponse.getProtocolVersionInfo()));
            }
            if (serverStatusResponse.getFavicon() != null) {
                jsonObject.addProperty(Serializer.I[0x26 ^ 0x2A], serverStatusResponse.getFavicon());
            }
            return (JsonElement)jsonObject;
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
            return this.serialize((ServerStatusResponse)o, type, jsonSerializationContext);
        }
        
        static {
            I();
        }
        
        public ServerStatusResponse deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, Serializer.I["".length()]);
            final ServerStatusResponse serverStatusResponse = new ServerStatusResponse();
            if (jsonObject.has(Serializer.I[" ".length()])) {
                serverStatusResponse.setServerDescription((IChatComponent)jsonDeserializationContext.deserialize(jsonObject.get(Serializer.I["  ".length()]), (Type)IChatComponent.class));
            }
            if (jsonObject.has(Serializer.I["   ".length()])) {
                serverStatusResponse.setPlayerCountData((PlayerCountData)jsonDeserializationContext.deserialize(jsonObject.get(Serializer.I[0xA5 ^ 0xA1]), (Type)PlayerCountData.class));
            }
            if (jsonObject.has(Serializer.I[0x4F ^ 0x4A])) {
                serverStatusResponse.setProtocolVersionInfo((MinecraftProtocolVersionIdentifier)jsonDeserializationContext.deserialize(jsonObject.get(Serializer.I[0xB2 ^ 0xB4]), (Type)MinecraftProtocolVersionIdentifier.class));
            }
            if (jsonObject.has(Serializer.I[0x47 ^ 0x40])) {
                serverStatusResponse.setFavicon(JsonUtils.getString(jsonObject, Serializer.I[0xE ^ 0x6]));
            }
            return serverStatusResponse;
        }
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        private static void I() {
            (I = new String[0x58 ^ 0x55])["".length()] = I("*\u001a\u0004!\u0004*", "YneUq");
            Serializer.I[" ".length()] = I("\n,8+\u0017\u00079?!\n\u0000", "nIKHe");
            Serializer.I["  ".length()] = I("&\f1\"++\u00196(6,", "BiBAY");
            Serializer.I["   ".length()] = I("#\u000e/3$!\u0011", "SbNJA");
            Serializer.I[0x38 ^ 0x3C] = I("\u0000>43\u000f\u0002!", "pRUJj");
            Serializer.I[0x29 ^ 0x2C] = I("!\u000797\u000e8\f", "WbKDg");
            Serializer.I[0x38 ^ 0x3E] = I("\u0001\u00067\u0007\u0004\u0018\r", "wcEtm");
            Serializer.I[0x45 ^ 0x42] = I("\u0014\u0004\u0019>\u0014\u001d\u000b", "reoWw");
            Serializer.I[0x5A ^ 0x52] = I("\u0010\n\u00001\u0001\u0019\u0005", "vkvXb");
            Serializer.I[0x2E ^ 0x27] = I("\u001c\u0007\u00141>\u0011\u0012\u0013;#\u0016", "xbgRL");
            Serializer.I[0xB4 ^ 0xBE] = I("!+\u000f\u000e\u0007#4", "QGnwb");
            Serializer.I[0x6 ^ 0xD] = I("\u0003\u0015<\t\u0004\u001a\u001e", "upNzm");
            Serializer.I[0x2F ^ 0x23] = I("\u0016/!.\u0002\u001f ", "pNWGa");
        }
    }
    
    public static class MinecraftProtocolVersionIdentifier
    {
        private final String name;
        private final int protocol;
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public MinecraftProtocolVersionIdentifier(final String name, final int protocol) {
            this.name = name;
            this.protocol = protocol;
        }
        
        public int getProtocol() {
            return this.protocol;
        }
        
        public String getName() {
            return this.name;
        }
        
        public static class Serializer implements JsonDeserializer<MinecraftProtocolVersionIdentifier>, JsonSerializer<MinecraftProtocolVersionIdentifier>
        {
            private static final String[] I;
            
            static {
                I();
            }
            
            public JsonElement serialize(final MinecraftProtocolVersionIdentifier minecraftProtocolVersionIdentifier, final Type type, final JsonSerializationContext jsonSerializationContext) {
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(Serializer.I["   ".length()], minecraftProtocolVersionIdentifier.getName());
                jsonObject.addProperty(Serializer.I[0x8C ^ 0x88], (Number)minecraftProtocolVersionIdentifier.getProtocol());
                return (JsonElement)jsonObject;
            }
            
            public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return this.deserialize(jsonElement, type, jsonDeserializationContext);
            }
            
            public MinecraftProtocolVersionIdentifier deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, Serializer.I["".length()]);
                return new MinecraftProtocolVersionIdentifier(JsonUtils.getString(jsonObject, Serializer.I[" ".length()]), JsonUtils.getInt(jsonObject, Serializer.I["  ".length()]));
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (2 >= 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
                return this.serialize((MinecraftProtocolVersionIdentifier)o, type, jsonSerializationContext);
            }
            
            private static void I() {
                (I = new String[0x2 ^ 0x7])["".length()] = I("\u000f31\u0007\u0013\u00168", "yVCtz");
                Serializer.I[" ".length()] = I("\u001a\u0002! ", "tcLEX");
                Serializer.I["  ".length()] = I(">>\u0001\u0006$-#\u0002", "NLnrK");
                Serializer.I["   ".length()] = I("\t\u0007!3", "gfLVf");
                Serializer.I[0x78 ^ 0x7C] = I("=\u0000=\r?.\u001d>", "MrRyP");
            }
        }
    }
}
