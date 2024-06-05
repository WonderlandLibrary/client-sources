package net.minecraft.client.resources.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import net.minecraft.util.JsonUtils;

public class TextureMetadataSectionSerializer extends BaseMetadataSectionSerializer {
   private static final String __OBFID = "CL_00001115";

   public TextureMetadataSection deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) {
      JsonObject var4 = var1.getAsJsonObject();
      boolean var5 = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "blur", false);
      boolean var6 = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "clamp", false);
      ArrayList var7 = Lists.newArrayList();
      if (var4.has("mipmaps")) {
         try {
            JsonArray var8 = var4.getAsJsonArray("mipmaps");

            for(int var9 = 0; var9 < var8.size(); ++var9) {
               JsonElement var10 = var8.get(var9);
               if (var10.isJsonPrimitive()) {
                  try {
                     var7.add(var10.getAsInt());
                  } catch (NumberFormatException var12) {
                     throw new JsonParseException(String.valueOf((new StringBuilder("Invalid texture->mipmap->")).append(var9).append(": expected number, was ").append(var10)), var12);
                  }
               } else if (var10.isJsonObject()) {
                  throw new JsonParseException(String.valueOf((new StringBuilder("Invalid texture->mipmap->")).append(var9).append(": expected number, was ").append(var10)));
               }
            }
         } catch (ClassCastException var13) {
            throw new JsonParseException(String.valueOf((new StringBuilder("Invalid texture->mipmaps: expected array, was ")).append(var4.get("mipmaps"))), var13);
         }
      }

      return new TextureMetadataSection(var5, var6, var7);
   }

   public Object deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      return this.deserialize(var1, var2, var3);
   }

   public String getSectionName() {
      return "texture";
   }
}
