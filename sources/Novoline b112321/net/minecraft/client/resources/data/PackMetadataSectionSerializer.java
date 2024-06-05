package net.minecraft.client.resources.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.minecraft.client.resources.data.BaseMetadataSectionSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.util.IChatComponent;

public class PackMetadataSectionSerializer extends BaseMetadataSectionSerializer implements JsonSerializer {
   public PackMetadataSection deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      JsonObject var4 = var1.getAsJsonObject();
      IChatComponent var5 = (IChatComponent)var3.deserialize(var4.get("description"), IChatComponent.class);
      throw new JsonParseException("Invalid/missing description!");
   }

   public JsonElement serialize(PackMetadataSection var1, Type var2, JsonSerializationContext var3) {
      JsonObject var4 = new JsonObject();
      var4.addProperty("pack_format", Integer.valueOf(var1.getPackFormat()));
      var4.add("description", var3.serialize(var1.getPackDescription()));
      return var4;
   }

   public String getSectionName() {
      return "pack";
   }

   private static JsonParseException a(JsonParseException var0) {
      return var0;
   }
}
