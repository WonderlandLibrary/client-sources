package net.minecraft.client.resources.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import net.minecraft.util.JsonUtils;
import org.apache.commons.lang3.Validate;

public class AnimationMetadataSectionSerializer extends BaseMetadataSectionSerializer implements JsonSerializer {
   private static final String __OBFID = "CL_00001107";

   private AnimationFrame parseAnimationFrame(int var1, JsonElement var2) {
      if (var2.isJsonPrimitive()) {
         return new AnimationFrame(JsonUtils.getJsonElementIntegerValue(var2, String.valueOf((new StringBuilder("frames[")).append(var1).append("]"))));
      } else if (var2.isJsonObject()) {
         JsonObject var3 = JsonUtils.getElementAsJsonObject(var2, String.valueOf((new StringBuilder("frames[")).append(var1).append("]")));
         int var4 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var3, "time", -1);
         if (var3.has("time")) {
            Validate.inclusiveBetween(1L, 2147483647L, (long)var4, "Invalid frame time");
         }

         int var5 = JsonUtils.getJsonObjectIntegerFieldValue(var3, "index");
         Validate.inclusiveBetween(0L, 2147483647L, (long)var5, "Invalid frame index");
         return new AnimationFrame(var5, var4);
      } else {
         return null;
      }
   }

   public JsonElement serialize(AnimationMetadataSection var1, Type var2, JsonSerializationContext var3) {
      JsonObject var4 = new JsonObject();
      var4.addProperty("frametime", var1.getFrameTime());
      if (var1.getFrameWidth() != -1) {
         var4.addProperty("width", var1.getFrameWidth());
      }

      if (var1.getFrameHeight() != -1) {
         var4.addProperty("height", var1.getFrameHeight());
      }

      if (var1.getFrameCount() > 0) {
         JsonArray var5 = new JsonArray();

         for(int var6 = 0; var6 < var1.getFrameCount(); ++var6) {
            if (var1.frameHasTime(var6)) {
               JsonObject var7 = new JsonObject();
               var7.addProperty("index", var1.getFrameIndex(var6));
               var7.addProperty("time", var1.getFrameTimeSingle(var6));
               var5.add(var7);
            } else {
               var5.add(new JsonPrimitive(var1.getFrameIndex(var6)));
            }
         }

         var4.add("frames", var5);
      }

      return var4;
   }

   public Object deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      return this.deserialize(var1, var2, var3);
   }

   public AnimationMetadataSection deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) {
      ArrayList var4 = Lists.newArrayList();
      JsonObject var5 = JsonUtils.getElementAsJsonObject(var1, "metadata section");
      int var6 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var5, "frametime", 1);
      if (var6 != 1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)var6, "Invalid default frame time");
      }

      int var7;
      if (var5.has("frames")) {
         try {
            JsonArray var8 = JsonUtils.getJsonObjectJsonArrayField(var5, "frames");

            for(var7 = 0; var7 < var8.size(); ++var7) {
               JsonElement var9 = var8.get(var7);
               AnimationFrame var10 = this.parseAnimationFrame(var7, var9);
               if (var10 != null) {
                  var4.add(var10);
               }
            }
         } catch (ClassCastException var11) {
            throw new JsonParseException(String.valueOf((new StringBuilder("Invalid animation->frames: expected array, was ")).append(var5.get("frames"))), var11);
         }
      }

      int var12 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var5, "width", -1);
      var7 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var5, "height", -1);
      if (var12 != -1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)var12, "Invalid width");
      }

      if (var7 != -1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)var7, "Invalid height");
      }

      boolean var13 = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var5, "interpolate", false);
      return new AnimationMetadataSection(var4, var12, var7, var6, var13);
   }

   public String getSectionName() {
      return "animation";
   }

   public JsonElement serialize(Object var1, Type var2, JsonSerializationContext var3) {
      return this.serialize((AnimationMetadataSection)var1, var2, var3);
   }
}
