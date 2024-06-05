package net.minecraft.network;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.at_;
import net.minecraft.util.JsonUtils;

public class ServerStatusResponse$MinecraftProtocolVersionIdentifier$Serializer implements JsonDeserializer, JsonSerializer {
   public at_ a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      JsonObject var4 = JsonUtils.getJsonObject(var1, "version");
      return new at_(JsonUtils.getString(var4, "name"), JsonUtils.getInt(var4, "protocol"));
   }

   public JsonElement a(at_ var1, Type var2, JsonSerializationContext var3) {
      JsonObject var4 = new JsonObject();
      var4.addProperty("name", var1.a());
      var4.addProperty("protocol", Integer.valueOf(var1.b()));
      return var4;
   }
}
