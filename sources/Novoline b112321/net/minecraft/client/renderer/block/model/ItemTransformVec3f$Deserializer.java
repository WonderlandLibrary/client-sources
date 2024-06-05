package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.vector.Vector3f;

class ItemTransformVec3f$Deserializer implements JsonDeserializer {
   private static final Vector3f ROTATION_DEFAULT = new Vector3f(0.0F, 0.0F, 0.0F);
   private static final Vector3f TRANSLATION_DEFAULT = new Vector3f(0.0F, 0.0F, 0.0F);
   private static final Vector3f SCALE_DEFAULT = new Vector3f(1.0F, 1.0F, 1.0F);

   public ItemTransformVec3f deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      JsonObject var4 = var1.getAsJsonObject();
      Vector3f var5 = this.parseVector3f(var4, "rotation", ROTATION_DEFAULT);
      Vector3f var6 = this.parseVector3f(var4, "translation", TRANSLATION_DEFAULT);
      var6.scale(0.0625F);
      var6.x = MathHelper.clamp_float(var6.x, -1.5F, 1.5F);
      var6.y = MathHelper.clamp_float(var6.y, -1.5F, 1.5F);
      var6.z = MathHelper.clamp_float(var6.z, -1.5F, 1.5F);
      Vector3f var7 = this.parseVector3f(var4, "scale", SCALE_DEFAULT);
      var7.x = MathHelper.clamp_float(var7.x, -4.0F, 4.0F);
      var7.y = MathHelper.clamp_float(var7.y, -4.0F, 4.0F);
      var7.z = MathHelper.clamp_float(var7.z, -4.0F, 4.0F);
      return new ItemTransformVec3f(var5, var6, var7);
   }

   private Vector3f parseVector3f(JsonObject var1, String var2, Vector3f var3) {
      if(!var1.has(var2)) {
         return var3;
      } else {
         JsonArray var4 = JsonUtils.getJsonArray(var1, var2);
         if(var4.size() != 3) {
            throw new JsonParseException("Expected 3 " + var2 + " values, found: " + var4.size());
         } else {
            float[] var5 = new float[3];

            for(int var6 = 0; var6 < var5.length; ++var6) {
               var5[var6] = JsonUtils.getFloat(var4.get(var6), var2 + "[" + var6 + "]");
            }

            return new Vector3f(var5[0], var5[1], var5[2]);
         }
      }
   }

   private static JsonParseException a(JsonParseException var0) {
      return var0;
   }
}
