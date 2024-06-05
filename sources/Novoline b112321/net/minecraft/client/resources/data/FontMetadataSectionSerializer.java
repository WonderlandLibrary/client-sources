package net.minecraft.client.resources.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.client.resources.data.BaseMetadataSectionSerializer;
import net.minecraft.client.resources.data.FontMetadataSection;
import net.minecraft.util.JsonUtils;
import org.apache.commons.lang3.Validate;

public class FontMetadataSectionSerializer extends BaseMetadataSectionSerializer {
   public FontMetadataSection deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      JsonObject var4 = var1.getAsJsonObject();
      float[] var5 = new float[256];
      float[] var6 = new float[256];
      float[] var7 = new float[256];
      float var8 = 1.0F;
      float var9 = 0.0F;
      float var10 = 0.0F;
      if(var4.has("characters")) {
         if(!var4.get("characters").isJsonObject()) {
            throw new JsonParseException("Invalid font->characters: expected object, was " + var4.get("characters"));
         }

         JsonObject var11 = var4.getAsJsonObject("characters");
         if(var11.has("default")) {
            if(!var11.get("default").isJsonObject()) {
               throw new JsonParseException("Invalid font->characters->default: expected object, was " + var11.get("default"));
            }

            JsonObject var12 = var11.getAsJsonObject("default");
            var8 = JsonUtils.getFloat(var12, "width", var8);
            Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, (double)var8, "Invalid default width");
            var9 = JsonUtils.getFloat(var12, "spacing", var9);
            Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, (double)var9, "Invalid default spacing");
            var10 = JsonUtils.getFloat(var12, "left", var9);
            Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, (double)var10, "Invalid default left");
         }

         for(int var18 = 0; var18 < 256; ++var18) {
            JsonElement var13 = var11.get(Integer.toString(var18));
            JsonObject var17 = JsonUtils.getJsonObject(var13, "characters[" + var18 + "]");
            float var14 = JsonUtils.getFloat(var17, "width", var8);
            Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, (double)var14, "Invalid width");
            float var15 = JsonUtils.getFloat(var17, "spacing", var9);
            Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, (double)var15, "Invalid spacing");
            float var16 = JsonUtils.getFloat(var17, "left", var10);
            Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, (double)var16, "Invalid left");
            var5[var18] = var14;
            var6[var18] = var15;
            var7[var18] = var16;
         }
      }

      return new FontMetadataSection(var5, var7, var6);
   }

   public String getSectionName() {
      return "font";
   }

   private static JsonParseException a(JsonParseException var0) {
      return var0;
   }
}
