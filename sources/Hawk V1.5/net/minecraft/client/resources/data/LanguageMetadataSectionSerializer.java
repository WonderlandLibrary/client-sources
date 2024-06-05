package net.minecraft.client.resources.data;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.client.resources.Language;
import net.minecraft.util.JsonUtils;

public class LanguageMetadataSectionSerializer extends BaseMetadataSectionSerializer {
   private static final String __OBFID = "CL_00001111";

   public String getSectionName() {
      return "language";
   }

   public LanguageMetadataSection deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) {
      JsonObject var4 = var1.getAsJsonObject();
      HashSet var5 = Sets.newHashSet();
      Iterator var6 = var4.entrySet().iterator();

      String var7;
      String var8;
      String var9;
      boolean var10;
      do {
         if (!var6.hasNext()) {
            return new LanguageMetadataSection(var5);
         }

         Entry var11 = (Entry)var6.next();
         var7 = (String)var11.getKey();
         JsonObject var12 = JsonUtils.getElementAsJsonObject((JsonElement)var11.getValue(), "language");
         var8 = JsonUtils.getJsonObjectStringFieldValue(var12, "region");
         var9 = JsonUtils.getJsonObjectStringFieldValue(var12, "name");
         var10 = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var12, "bidirectional", false);
         if (var8.isEmpty()) {
            throw new JsonParseException(String.valueOf((new StringBuilder("Invalid language->'")).append(var7).append("'->region: empty value")));
         }

         if (var9.isEmpty()) {
            throw new JsonParseException(String.valueOf((new StringBuilder("Invalid language->'")).append(var7).append("'->name: empty value")));
         }
      } while(var5.add(new Language(var7, var8, var9, var10)));

      throw new JsonParseException(String.valueOf((new StringBuilder("Duplicate language->'")).append(var7).append("' defined")));
   }

   public Object deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      return this.deserialize(var1, var2, var3);
   }
}
