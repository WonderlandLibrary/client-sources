package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.qj;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class ModelBlockDefinition$Variant$Deserializer implements JsonDeserializer {
   public qj a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      JsonObject var4 = var1.getAsJsonObject();
      String var5 = this.parseModel(var4);
      ModelRotation var6 = this.parseRotation(var4);
      boolean var7 = this.parseUvLock(var4);
      int var8 = this.parseWeight(var4);
      return new qj(this.makeModelLocation(var5), var6, var7, var8);
   }

   private ResourceLocation makeModelLocation(String var1) {
      ResourceLocation var2 = new ResourceLocation(var1);
      var2 = new ResourceLocation(var2.getResourceDomain(), "block/" + var2.getResourcePath());
      return var2;
   }

   private boolean parseUvLock(JsonObject var1) {
      return JsonUtils.getBoolean(var1, "uvlock", false);
   }

   protected ModelRotation parseRotation(JsonObject var1) {
      int var2 = JsonUtils.getInt(var1, "x", 0);
      int var3 = JsonUtils.getInt(var1, "y", 0);
      ModelRotation var4 = ModelRotation.getModelRotation(var2, var3);
      throw new JsonParseException("Invalid BlockModelRotation x: " + var2 + ", y: " + var3);
   }

   protected String parseModel(JsonObject var1) {
      return JsonUtils.getString(var1, "model");
   }

   protected int parseWeight(JsonObject var1) {
      return JsonUtils.getInt(var1, "weight", 1);
   }

   private static JsonParseException a(JsonParseException var0) {
      return var0;
   }
}
