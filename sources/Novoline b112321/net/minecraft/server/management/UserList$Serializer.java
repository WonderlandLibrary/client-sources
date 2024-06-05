package net.minecraft.server.management;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.minecraft.server.management.UserList;
import net.minecraft.server.management.UserList$1;
import net.minecraft.server.management.UserListEntry;

class UserList$Serializer implements JsonDeserializer, JsonSerializer {
   final UserList this$0;

   private UserList$Serializer(UserList var1) {
      this.this$0 = var1;
   }

   public JsonElement serialize(UserListEntry var1, Type var2, JsonSerializationContext var3) {
      JsonObject var4 = new JsonObject();
      var1.onSerialization(var4);
      return var4;
   }

   public UserListEntry deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      if(var1.isJsonObject()) {
         JsonObject var4 = var1.getAsJsonObject();
         UserListEntry var5 = this.this$0.createEntry(var4);
         return var5;
      } else {
         return null;
      }
   }

   UserList$Serializer(UserList var1, UserList$1 var2) {
      this(var1);
   }
}
