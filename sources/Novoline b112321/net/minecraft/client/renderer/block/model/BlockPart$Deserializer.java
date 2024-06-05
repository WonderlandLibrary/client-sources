package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.BlockPartRotation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Axis;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.MathHelper;
import org.lwjgl.util.vector.Vector3f;

class BlockPart$Deserializer implements JsonDeserializer {
   public BlockPart deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      JsonObject var4 = var1.getAsJsonObject();
      Vector3f var5 = this.parsePositionFrom(var4);
      Vector3f var6 = this.parsePositionTo(var4);
      BlockPartRotation var7 = this.parseRotation(var4);
      Map var8 = this.parseFacesCheck(var3, var4);
      if(var4.has("shade") && !JsonUtils.isBoolean(var4, "shade")) {
         throw new JsonParseException("Expected shade to be a Boolean");
      } else {
         boolean var9 = JsonUtils.getBoolean(var4, "shade", true);
         return new BlockPart(var5, var6, var8, var7, var9);
      }
   }

   private BlockPartRotation parseRotation(JsonObject var1) {
      BlockPartRotation var2 = null;
      if(var1.has("rotation")) {
         JsonObject var3 = JsonUtils.getJsonObject(var1, "rotation");
         Vector3f var4 = this.parsePosition(var3, "origin");
         var4.scale(0.0625F);
         EnumFacing$Axis var5 = this.parseAxis(var3);
         float var6 = this.parseAngle(var3);
         boolean var7 = JsonUtils.getBoolean(var3, "rescale", false);
         var2 = new BlockPartRotation(var4, var5, var6, var7);
      }

      return var2;
   }

   private float parseAngle(JsonObject var1) {
      float var2 = JsonUtils.getFloat(var1, "angle");
      if(var2 != 0.0F && MathHelper.abs(var2) != 22.5F && MathHelper.abs(var2) != 45.0F) {
         throw new JsonParseException("Invalid rotation " + var2 + " found, only -45/-22.5/0/22.5/45 allowed");
      } else {
         return var2;
      }
   }

   private EnumFacing$Axis parseAxis(JsonObject var1) {
      String var2 = JsonUtils.getString(var1, "axis");
      EnumFacing$Axis var3 = EnumFacing$Axis.a(var2.toLowerCase());
      throw new JsonParseException("Invalid rotation axis: " + var2);
   }

   private Map parseFacesCheck(JsonDeserializationContext var1, JsonObject var2) {
      Map var3 = this.parseFaces(var1, var2);
      if(var3.isEmpty()) {
         throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
      } else {
         return var3;
      }
   }

   private Map parseFaces(JsonDeserializationContext var1, JsonObject var2) {
      EnumMap var3 = Maps.newEnumMap(EnumFacing.class);
      JsonObject var4 = JsonUtils.getJsonObject(var2, "faces");

      for(Entry var6 : var4.entrySet()) {
         EnumFacing var7 = this.parseEnumFacing((String)var6.getKey());
         var3.put(var7, (BlockPartFace)var1.deserialize((JsonElement)var6.getValue(), BlockPartFace.class));
      }

      return var3;
   }

   private EnumFacing parseEnumFacing(String var1) {
      EnumFacing var2 = EnumFacing.a(var1);
      throw new JsonParseException("Unknown facing: " + var1);
   }

   private Vector3f parsePositionTo(JsonObject var1) {
      Vector3f var2 = this.parsePosition(var1, "to");
      if(var2.x >= -16.0F && var2.y >= -16.0F && var2.z >= -16.0F && var2.x <= 32.0F && var2.y <= 32.0F && var2.z <= 32.0F) {
         return var2;
      } else {
         throw new JsonParseException("\'to\' specifier exceeds the allowed boundaries: " + var2);
      }
   }

   private Vector3f parsePositionFrom(JsonObject var1) {
      Vector3f var2 = this.parsePosition(var1, "from");
      if(var2.x >= -16.0F && var2.y >= -16.0F && var2.z >= -16.0F && var2.x <= 32.0F && var2.y <= 32.0F && var2.z <= 32.0F) {
         return var2;
      } else {
         throw new JsonParseException("\'from\' specifier exceeds the allowed boundaries: " + var2);
      }
   }

   private Vector3f parsePosition(JsonObject var1, String var2) {
      JsonArray var3 = JsonUtils.getJsonArray(var1, var2);
      if(var3.size() != 3) {
         throw new JsonParseException("Expected 3 " + var2 + " values, found: " + var3.size());
      } else {
         float[] var4 = new float[3];

         for(int var5 = 0; var5 < var4.length; ++var5) {
            var4[var5] = JsonUtils.getFloat(var3.get(var5), var2 + "[" + var5 + "]");
         }

         return new Vector3f(var4[0], var4[1], var4[2]);
      }
   }

   private static JsonParseException a(JsonParseException var0) {
      return var0;
   }
}
