package net;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.ary;
import net.at_;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.JsonUtils;

public class bg1 implements JsonDeserializer, JsonSerializer {
   public ServerStatusResponse a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      JsonObject var4 = JsonUtils.getJsonObject(var1, "status");
      ServerStatusResponse var5 = new ServerStatusResponse();
      if(var4.has("description")) {
         var5.setServerDescription((IChatComponent)var3.deserialize(var4.get("description"), IChatComponent.class));
      }

      if(var4.has("players")) {
         var5.a((ary)var3.deserialize(var4.get("players"), ary.class));
      }

      if(var4.has("version")) {
         var5.a((at_)var3.deserialize(var4.get("version"), at_.class));
      }

      if(var4.has("favicon")) {
         var5.setFavicon(JsonUtils.getString(var4, "favicon"));
      }

      return var5;
   }

   public JsonElement a(ServerStatusResponse var1, Type var2, JsonSerializationContext var3) {
      JsonObject var4 = new JsonObject();
      if(var1.getServerDescription() != null) {
         var4.add("description", var3.serialize(var1.getServerDescription()));
      }

      if(var1.c() != null) {
         var4.add("players", var3.serialize(var1.c()));
      }

      if(var1.d() != null) {
         var4.add("version", var3.serialize(var1.d()));
      }

      if(var1.getFavicon() != null) {
         var4.addProperty("favicon", var1.getFavicon());
      }

      return var4;
   }

   private static JsonParseException a(JsonParseException var0) {
      return var0;
   }
}
