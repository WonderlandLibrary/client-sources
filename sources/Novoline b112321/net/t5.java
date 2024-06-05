package net;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.minecraft.util.ChatStyle;

public class t5 implements JsonDeserializer, JsonSerializer {
   public ChatStyle a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      if(var1.isJsonObject()) {
         new ChatStyle();
         JsonObject var5 = var1.getAsJsonObject();
         return null;
      } else {
         return null;
      }
   }

   public JsonElement a(ChatStyle var1, Type var2, JsonSerializationContext var3) {
      if(var1.isEmpty()) {
         return null;
      } else {
         JsonObject var4 = new JsonObject();
         if(ChatStyle.access$000(var1) != null) {
            var4.addProperty("bold", ChatStyle.access$000(var1));
         }

         if(ChatStyle.access$100(var1) != null) {
            var4.addProperty("italic", ChatStyle.access$100(var1));
         }

         if(ChatStyle.access$200(var1) != null) {
            var4.addProperty("underlined", ChatStyle.access$200(var1));
         }

         if(ChatStyle.access$300(var1) != null) {
            var4.addProperty("strikethrough", ChatStyle.access$300(var1));
         }

         if(ChatStyle.access$400(var1) != null) {
            var4.addProperty("obfuscated", ChatStyle.access$400(var1));
         }

         if(ChatStyle.access$500(var1) != null) {
            var4.add("color", var3.serialize(ChatStyle.access$500(var1)));
         }

         if(ChatStyle.access$600(var1) != null) {
            var4.add("insertion", var3.serialize(ChatStyle.access$600(var1)));
         }

         if(ChatStyle.access$700(var1) != null) {
            JsonObject var5 = new JsonObject();
            var5.addProperty("action", ChatStyle.access$700(var1).getAction().getCanonicalName());
            var5.addProperty("value", ChatStyle.access$700(var1).getValue());
            var4.add("clickEvent", var5);
         }

         if(ChatStyle.access$800(var1) != null) {
            JsonObject var6 = new JsonObject();
            var6.addProperty("action", ChatStyle.access$800(var1).getAction().getCanonicalName());
            var6.add("value", var3.serialize(ChatStyle.access$800(var1).getValue()));
            var4.add("hoverEvent", var6);
         }

         return var4;
      }
   }

   private static JsonParseException a(JsonParseException var0) {
      return var0;
   }
}
