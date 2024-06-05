package net.minecraft.util;

import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class JsonSerializableSet extends ForwardingSet implements IJsonSerializable {
   private static final String __OBFID = "CL_00001482";
   private final Set underlyingSet = Sets.newHashSet();

   protected Object delegate() {
      return this.delegate();
   }

   protected Collection delegate() {
      return this.delegate();
   }

   public void func_152753_a(JsonElement var1) {
      if (var1.isJsonArray()) {
         Iterator var2 = var1.getAsJsonArray().iterator();

         while(var2.hasNext()) {
            JsonElement var3 = (JsonElement)var2.next();
            this.add(var3.getAsString());
         }
      }

   }

   public JsonElement getSerializableElement() {
      JsonArray var1 = new JsonArray();
      Iterator var2 = this.iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         var1.add(new JsonPrimitive(var3));
      }

      return var1;
   }

   protected Set delegate() {
      return this.underlyingSet;
   }
}
