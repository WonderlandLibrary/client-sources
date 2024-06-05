package net.minecraft.client.audio;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import net.minecraft.util.JsonUtils;
import org.apache.commons.lang3.Validate;

public class SoundListSerializer implements JsonDeserializer {
   private static final String __OBFID = "CL_00001124";

   public Object deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) {
      return this.deserialize1(var1, var2, var3);
   }

   public SoundList deserialize1(JsonElement var1, Type var2, JsonDeserializationContext var3) {
      JsonObject var4 = JsonUtils.getElementAsJsonObject(var1, "entry");
      SoundList var5 = new SoundList();
      var5.setReplaceExisting(JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "replace", false));
      SoundCategory var6 = SoundCategory.func_147154_a(JsonUtils.getJsonObjectStringFieldValueOrDefault(var4, "category", SoundCategory.MASTER.getCategoryName()));
      var5.setSoundCategory(var6);
      Validate.notNull(var6, "Invalid category", new Object[0]);
      if (var4.has("sounds")) {
         JsonArray var7 = JsonUtils.getJsonObjectJsonArrayField(var4, "sounds");

         for(int var8 = 0; var8 < var7.size(); ++var8) {
            JsonElement var9 = var7.get(var8);
            SoundList.SoundEntry var10 = new SoundList.SoundEntry();
            if (JsonUtils.jsonElementTypeIsString(var9)) {
               var10.setSoundEntryName(JsonUtils.getJsonElementStringValue(var9, "sound"));
            } else {
               JsonObject var11 = JsonUtils.getElementAsJsonObject(var9, "sound");
               var10.setSoundEntryName(JsonUtils.getJsonObjectStringFieldValue(var11, "name"));
               if (var11.has("type")) {
                  SoundList.SoundEntry.Type var12 = SoundList.SoundEntry.Type.getType(JsonUtils.getJsonObjectStringFieldValue(var11, "type"));
                  Validate.notNull(var12, "Invalid type", new Object[0]);
                  var10.setSoundEntryType(var12);
               }

               float var14;
               if (var11.has("volume")) {
                  var14 = JsonUtils.getJsonObjectFloatFieldValue(var11, "volume");
                  Validate.isTrue(var14 > 0.0F, "Invalid volume", new Object[0]);
                  var10.setSoundEntryVolume(var14);
               }

               if (var11.has("pitch")) {
                  var14 = JsonUtils.getJsonObjectFloatFieldValue(var11, "pitch");
                  Validate.isTrue(var14 > 0.0F, "Invalid pitch", new Object[0]);
                  var10.setSoundEntryPitch(var14);
               }

               if (var11.has("weight")) {
                  int var13 = JsonUtils.getJsonObjectIntegerFieldValue(var11, "weight");
                  Validate.isTrue(var13 > 0, "Invalid weight", new Object[0]);
                  var10.setSoundEntryWeight(var13);
               }

               if (var11.has("stream")) {
                  var10.setStreaming(JsonUtils.getJsonObjectBooleanFieldValue(var11, "stream"));
               }
            }

            var5.getSoundList().add(var10);
         }
      }

      return var5;
   }
}
