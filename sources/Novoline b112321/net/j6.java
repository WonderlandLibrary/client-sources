package net;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;

class j6 implements JsonDeserializer {
   public ItemCameraTransforms a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      JsonObject var4 = var1.getAsJsonObject();
      ItemTransformVec3f var5 = this.a(var3, var4, "thirdperson");
      ItemTransformVec3f var6 = this.a(var3, var4, "firstperson");
      ItemTransformVec3f var7 = this.a(var3, var4, "head");
      ItemTransformVec3f var8 = this.a(var3, var4, "gui");
      ItemTransformVec3f var9 = this.a(var3, var4, "ground");
      ItemTransformVec3f var10 = this.a(var3, var4, "fixed");
      return new ItemCameraTransforms(var5, var6, var7, var8, var9, var10);
   }

   private ItemTransformVec3f a(JsonDeserializationContext var1, JsonObject var2, String var3) {
      return var2.has(var3)?(ItemTransformVec3f)var1.deserialize(var2.get(var3), ItemTransformVec3f.class):ItemTransformVec3f.DEFAULT;
   }

   private static JsonParseException a(JsonParseException var0) {
      return var0;
   }
}
