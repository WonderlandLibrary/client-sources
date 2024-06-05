package net.minecraft.client.audio;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.ER;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundList;
import net.minecraft.client.audio.SoundList$SoundEntry$Type;
import net.minecraft.util.JsonUtils;
import org.apache.commons.lang3.Validate;

public class SoundListSerializer implements JsonDeserializer {
   public SoundList deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      JsonObject var4 = JsonUtils.getJsonObject(var1, "entry");
      SoundList var5 = new SoundList();
      var5.setReplaceExisting(JsonUtils.getBoolean(var4, "replace", false));
      SoundCategory var6 = SoundCategory.getCategory(JsonUtils.getString(var4, "category", SoundCategory.MASTER.getCategoryName()));
      var5.setSoundCategory(var6);
      Validate.notNull(var6, "Invalid category", new Object[0]);
      if(var4.has("sounds")) {
         JsonArray var7 = JsonUtils.getJsonArray(var4, "sounds");

         for(int var8 = 0; var8 < var7.size(); ++var8) {
            JsonElement var9 = var7.get(var8);
            ER var10 = new ER();
            if(JsonUtils.isString(var9)) {
               var10.a(JsonUtils.getString(var9, "sound"));
            } else {
               JsonObject var11 = JsonUtils.getJsonObject(var9, "sound");
               var10.a(JsonUtils.getString(var11, "name"));
               if(var11.has("type")) {
                  SoundList$SoundEntry$Type var12 = SoundList$SoundEntry$Type.getType(JsonUtils.getString(var11, "type"));
                  Validate.notNull(var12, "Invalid type", new Object[0]);
                  var10.a(var12);
               }

               if(var11.has("volume")) {
                  float var13 = JsonUtils.getFloat(var11, "volume");
                  Validate.isTrue(var13 > 0.0F, "Invalid volume", new Object[0]);
                  var10.b(var13);
               }

               if(var11.has("pitch")) {
                  float var14 = JsonUtils.getFloat(var11, "pitch");
                  Validate.isTrue(var14 > 0.0F, "Invalid pitch", new Object[0]);
                  var10.a(var14);
               }

               if(var11.has("weight")) {
                  int var15 = JsonUtils.getInt(var11, "weight");
                  Validate.isTrue(true, "Invalid weight", new Object[0]);
                  var10.a(var15);
               }

               if(var11.has("stream")) {
                  var10.a(JsonUtils.getBoolean(var11, "stream"));
               }
            }

            var5.getSoundList().add(var10);
         }
      }

      return var5;
   }

   private static JsonParseException a(JsonParseException var0) {
      return var0;
   }
}
