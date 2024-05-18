package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonArray;
import java.util.UUID;
import com.mojang.authlib.GameProfile;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;

public class ServerStatusResponse
{
    private IChatComponent HorizonCode_Horizon_È;
    private Â Â;
    private HorizonCode_Horizon_È Ý;
    private String Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001385";
    
    public IChatComponent HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void HorizonCode_Horizon_È(final IChatComponent motd) {
        this.HorizonCode_Horizon_È = motd;
    }
    
    public Â Â() {
        return this.Â;
    }
    
    public void HorizonCode_Horizon_È(final Â countData) {
        this.Â = countData;
    }
    
    public HorizonCode_Horizon_È Ý() {
        return this.Ý;
    }
    
    public void HorizonCode_Horizon_È(final HorizonCode_Horizon_È protocolVersionData) {
        this.Ý = protocolVersionData;
    }
    
    public void HorizonCode_Horizon_È(final String faviconBlob) {
        this.Ø­áŒŠá = faviconBlob;
    }
    
    public String Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    public static class Ý implements JsonDeserializer, JsonSerializer
    {
        private static final String HorizonCode_Horizon_È = "CL_00001388";
        
        public ServerStatusResponse HorizonCode_Horizon_È(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
            final JsonObject var4 = JsonUtils.Âµá€(p_deserialize_1_, "status");
            final ServerStatusResponse var5 = new ServerStatusResponse();
            if (var4.has("description")) {
                var5.HorizonCode_Horizon_È((IChatComponent)p_deserialize_3_.deserialize(var4.get("description"), (Type)IChatComponent.class));
            }
            if (var4.has("players")) {
                var5.HorizonCode_Horizon_È((Â)p_deserialize_3_.deserialize(var4.get("players"), (Type)Â.class));
            }
            if (var4.has("version")) {
                var5.HorizonCode_Horizon_È((HorizonCode_Horizon_È)p_deserialize_3_.deserialize(var4.get("version"), (Type)HorizonCode_Horizon_È.class));
            }
            if (var4.has("favicon")) {
                var5.HorizonCode_Horizon_È(JsonUtils.Ó(var4, "favicon"));
            }
            return var5;
        }
        
        public JsonElement HorizonCode_Horizon_È(final ServerStatusResponse p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
            final JsonObject var4 = new JsonObject();
            if (p_serialize_1_.HorizonCode_Horizon_È() != null) {
                var4.add("description", p_serialize_3_.serialize((Object)p_serialize_1_.HorizonCode_Horizon_È()));
            }
            if (p_serialize_1_.Â() != null) {
                var4.add("players", p_serialize_3_.serialize((Object)p_serialize_1_.Â()));
            }
            if (p_serialize_1_.Ý() != null) {
                var4.add("version", p_serialize_3_.serialize((Object)p_serialize_1_.Ý()));
            }
            if (p_serialize_1_.Ø­áŒŠá() != null) {
                var4.addProperty("favicon", p_serialize_1_.Ø­áŒŠá());
            }
            return (JsonElement)var4;
        }
        
        public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
            return this.HorizonCode_Horizon_È((ServerStatusResponse)p_serialize_1_, p_serialize_2_, p_serialize_3_);
        }
    }
    
    public static class HorizonCode_Horizon_È
    {
        private final String HorizonCode_Horizon_È;
        private final int Â;
        private static final String Ý = "CL_00001389";
        
        public HorizonCode_Horizon_È(final String nameIn, final int protocolIn) {
            this.HorizonCode_Horizon_È = nameIn;
            this.Â = protocolIn;
        }
        
        public String HorizonCode_Horizon_È() {
            return this.HorizonCode_Horizon_È;
        }
        
        public int Â() {
            return this.Â;
        }
        
        public static class HorizonCode_Horizon_È implements JsonDeserializer, JsonSerializer
        {
            private static final String HorizonCode_Horizon_È = "CL_00001390";
            
            public ServerStatusResponse.HorizonCode_Horizon_È HorizonCode_Horizon_È(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
                final JsonObject var4 = JsonUtils.Âµá€(p_deserialize_1_, "version");
                return new ServerStatusResponse.HorizonCode_Horizon_È(JsonUtils.Ó(var4, "name"), JsonUtils.áŒŠÆ(var4, "protocol"));
            }
            
            public JsonElement HorizonCode_Horizon_È(final ServerStatusResponse.HorizonCode_Horizon_È p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
                final JsonObject var4 = new JsonObject();
                var4.addProperty("name", p_serialize_1_.HorizonCode_Horizon_È());
                var4.addProperty("protocol", (Number)p_serialize_1_.Â());
                return (JsonElement)var4;
            }
            
            public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
                return this.HorizonCode_Horizon_È((ServerStatusResponse.HorizonCode_Horizon_È)p_serialize_1_, p_serialize_2_, p_serialize_3_);
            }
        }
    }
    
    public static class Â
    {
        private final int HorizonCode_Horizon_È;
        private final int Â;
        private GameProfile[] Ý;
        private static final String Ø­áŒŠá = "CL_00001386";
        
        public Â(final int p_i45274_1_, final int p_i45274_2_) {
            this.HorizonCode_Horizon_È = p_i45274_1_;
            this.Â = p_i45274_2_;
        }
        
        public int HorizonCode_Horizon_È() {
            return this.HorizonCode_Horizon_È;
        }
        
        public int Â() {
            return this.Â;
        }
        
        public GameProfile[] Ý() {
            return this.Ý;
        }
        
        public void HorizonCode_Horizon_È(final GameProfile[] playersIn) {
            this.Ý = playersIn;
        }
        
        public static class HorizonCode_Horizon_È implements JsonDeserializer, JsonSerializer
        {
            private static final String HorizonCode_Horizon_È = "CL_00001387";
            
            public Â HorizonCode_Horizon_È(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
                final JsonObject var4 = JsonUtils.Âµá€(p_deserialize_1_, "players");
                final Â var5 = new Â(JsonUtils.áŒŠÆ(var4, "max"), JsonUtils.áŒŠÆ(var4, "online"));
                if (JsonUtils.Ý(var4, "sample")) {
                    final JsonArray var6 = JsonUtils.ÂµÈ(var4, "sample");
                    if (var6.size() > 0) {
                        final GameProfile[] var7 = new GameProfile[var6.size()];
                        for (int var8 = 0; var8 < var7.length; ++var8) {
                            final JsonObject var9 = JsonUtils.Âµá€(var6.get(var8), "player[" + var8 + "]");
                            final String var10 = JsonUtils.Ó(var9, "id");
                            var7[var8] = new GameProfile(UUID.fromString(var10), JsonUtils.Ó(var9, "name"));
                        }
                        var5.HorizonCode_Horizon_È(var7);
                    }
                }
                return var5;
            }
            
            public JsonElement HorizonCode_Horizon_È(final Â p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
                final JsonObject var4 = new JsonObject();
                var4.addProperty("max", (Number)p_serialize_1_.HorizonCode_Horizon_È());
                var4.addProperty("online", (Number)p_serialize_1_.Â());
                if (p_serialize_1_.Ý() != null && p_serialize_1_.Ý().length > 0) {
                    final JsonArray var5 = new JsonArray();
                    for (int var6 = 0; var6 < p_serialize_1_.Ý().length; ++var6) {
                        final JsonObject var7 = new JsonObject();
                        final UUID var8 = p_serialize_1_.Ý()[var6].getId();
                        var7.addProperty("id", (var8 == null) ? "" : var8.toString());
                        var7.addProperty("name", p_serialize_1_.Ý()[var6].getName());
                        var5.add((JsonElement)var7);
                    }
                    var4.add("sample", (JsonElement)var5);
                }
                return (JsonElement)var4;
            }
            
            public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
                return this.HorizonCode_Horizon_È((Â)p_serialize_1_, p_serialize_2_, p_serialize_3_);
            }
        }
    }
}
