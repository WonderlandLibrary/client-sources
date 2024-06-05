package net.minecraft.client.resources.data;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Map.Entry;
import net.a0R;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.data.BaseMetadataSectionSerializer;
import net.minecraft.util.JsonUtils;

public class LanguageMetadataSectionSerializer extends BaseMetadataSectionSerializer {
   public a0R a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      JsonObject var4 = var1.getAsJsonObject();
      HashSet var5 = Sets.newHashSet();

      for(Entry var7 : var4.entrySet()) {
         String var8 = (String)var7.getKey();
         JsonObject var9 = JsonUtils.getJsonObject((JsonElement)var7.getValue(), "language");
         String var10 = JsonUtils.getString(var9, "region");
         String var11 = JsonUtils.getString(var9, "name");
         boolean var12 = JsonUtils.getBoolean(var9, "bidirectional", false);
         if(var10.isEmpty()) {
            throw new JsonParseException("Invalid language->\'" + var8 + "\'->region: empty value");
         }

         if(var11.isEmpty()) {
            throw new JsonParseException("Invalid language->\'" + var8 + "\'->name: empty value");
         }

         if(!var5.add(new Language(var8, var10, var11, var12))) {
            throw new JsonParseException("Duplicate language->\'" + var8 + "\' defined");
         }
      }

      return new a0R(var5);
   }

   public String getSectionName() {
      return "language";
   }

   private static JsonParseException a(JsonParseException var0) {
      return var0;
   }
}
