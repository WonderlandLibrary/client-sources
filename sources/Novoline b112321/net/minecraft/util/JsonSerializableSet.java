package net.minecraft.util;

import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Set;
import net.minecraft.util.IJsonSerializable;

public class JsonSerializableSet extends ForwardingSet implements IJsonSerializable {
   private final Set underlyingSet = Sets.newHashSet();

   public void fromJson(JsonElement var1) {
      if(var1.isJsonArray()) {
         for(JsonElement var3 : var1.getAsJsonArray()) {
            this.add(var3.getAsString());
         }
      }

   }

   public JsonElement getSerializableElement() {
      JsonArray var1 = new JsonArray();

      for(String var3 : this) {
         var1.add(new JsonPrimitive(var3));
      }

      return var1;
   }

   protected Set delegate() {
      return this.underlyingSet;
   }
}
