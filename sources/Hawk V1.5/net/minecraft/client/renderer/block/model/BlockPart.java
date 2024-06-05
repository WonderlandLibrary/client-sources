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
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.vecmath.Vector3f;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.MathHelper;

public class BlockPart {
   public final BlockPartRotation field_178237_d;
   private static final String __OBFID = "CL_00002511";
   public final Map field_178240_c;
   public final Vector3f field_178239_b;
   public final boolean field_178238_e;
   public final Vector3f field_178241_a;

   private float[] func_178236_a(EnumFacing var1) {
      float[] var2;
      switch(var1) {
      case DOWN:
      case UP:
         var2 = new float[]{this.field_178241_a.x, this.field_178241_a.z, this.field_178239_b.x, this.field_178239_b.z};
         break;
      case NORTH:
      case SOUTH:
         var2 = new float[]{this.field_178241_a.x, 16.0F - this.field_178239_b.y, this.field_178239_b.x, 16.0F - this.field_178241_a.y};
         break;
      case WEST:
      case EAST:
         var2 = new float[]{this.field_178241_a.z, 16.0F - this.field_178239_b.y, this.field_178239_b.z, 16.0F - this.field_178241_a.y};
         break;
      default:
         throw new NullPointerException();
      }

      return var2;
   }

   public BlockPart(Vector3f var1, Vector3f var2, Map var3, BlockPartRotation var4, boolean var5) {
      this.field_178241_a = var1;
      this.field_178239_b = var2;
      this.field_178240_c = var3;
      this.field_178237_d = var4;
      this.field_178238_e = var5;
      this.func_178235_a();
   }

   private void func_178235_a() {
      Iterator var1 = this.field_178240_c.entrySet().iterator();

      while(var1.hasNext()) {
         Entry var2 = (Entry)var1.next();
         float[] var3 = this.func_178236_a((EnumFacing)var2.getKey());
         ((BlockPartFace)var2.getValue()).field_178243_e.func_178349_a(var3);
      }

   }

   static final class SwitchEnumFacing {
      private static final String __OBFID = "CL_00002510";
      static final int[] field_178234_a = new int[EnumFacing.values().length];

      static {
         try {
            field_178234_a[EnumFacing.DOWN.ordinal()] = 1;
         } catch (NoSuchFieldError var6) {
         }

         try {
            field_178234_a[EnumFacing.UP.ordinal()] = 2;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_178234_a[EnumFacing.NORTH.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_178234_a[EnumFacing.SOUTH.ordinal()] = 4;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_178234_a[EnumFacing.WEST.ordinal()] = 5;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_178234_a[EnumFacing.EAST.ordinal()] = 6;
         } catch (NoSuchFieldError var1) {
         }

      }
   }

   static class Deserializer implements JsonDeserializer {
      private static final String __OBFID = "CL_00002509";

      private EnumFacing func_178248_a(String var1) {
         EnumFacing var2 = EnumFacing.byName(var1);
         if (var2 == null) {
            throw new JsonParseException(String.valueOf((new StringBuilder("Unknown facing: ")).append(var1)));
         } else {
            return var2;
         }
      }

      public BlockPart func_178254_a(JsonElement var1, Type var2, JsonDeserializationContext var3) {
         JsonObject var4 = var1.getAsJsonObject();
         Vector3f var5 = this.func_178249_e(var4);
         Vector3f var6 = this.func_178247_d(var4);
         BlockPartRotation var7 = this.func_178256_a(var4);
         Map var8 = this.func_178250_a(var3, var4);
         if (var4.has("shade") && !JsonUtils.func_180199_c(var4, "shade")) {
            throw new JsonParseException("Expected shade to be a Boolean");
         } else {
            boolean var9 = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "shade", true);
            return new BlockPart(var5, var6, var8, var7, var9);
         }
      }

      private BlockPartRotation func_178256_a(JsonObject var1) {
         BlockPartRotation var2 = null;
         if (var1.has("rotation")) {
            JsonObject var3 = JsonUtils.getJsonObject(var1, "rotation");
            Vector3f var4 = this.func_178251_a(var3, "origin");
            var4.scale(0.0625F);
            EnumFacing.Axis var5 = this.func_178252_c(var3);
            float var6 = this.func_178255_b(var3);
            boolean var7 = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var3, "rescale", false);
            var2 = new BlockPartRotation(var4, var5, var6, var7);
         }

         return var2;
      }

      private Vector3f func_178251_a(JsonObject var1, String var2) {
         JsonArray var3 = JsonUtils.getJsonObjectJsonArrayField(var1, var2);
         if (var3.size() != 3) {
            throw new JsonParseException(String.valueOf((new StringBuilder("Expected 3 ")).append(var2).append(" values, found: ").append(var3.size())));
         } else {
            float[] var4 = new float[3];

            for(int var5 = 0; var5 < var4.length; ++var5) {
               var4[var5] = JsonUtils.getJsonElementFloatValue(var3.get(var5), String.valueOf((new StringBuilder(String.valueOf(var2))).append("[").append(var5).append("]")));
            }

            return new Vector3f(var4);
         }
      }

      private Vector3f func_178249_e(JsonObject var1) {
         Vector3f var2 = this.func_178251_a(var1, "from");
         if (var2.x >= -16.0F && var2.y >= -16.0F && var2.z >= -16.0F && var2.x <= 32.0F && var2.y <= 32.0F && var2.z <= 32.0F) {
            return var2;
         } else {
            throw new JsonParseException(String.valueOf((new StringBuilder("'from' specifier exceeds the allowed boundaries: ")).append(var2)));
         }
      }

      private EnumFacing.Axis func_178252_c(JsonObject var1) {
         String var2 = JsonUtils.getJsonObjectStringFieldValue(var1, "axis");
         EnumFacing.Axis var3 = EnumFacing.Axis.byName(var2.toLowerCase());
         if (var3 == null) {
            throw new JsonParseException(String.valueOf((new StringBuilder("Invalid rotation axis: ")).append(var2)));
         } else {
            return var3;
         }
      }

      private float func_178255_b(JsonObject var1) {
         float var2 = JsonUtils.getJsonObjectFloatFieldValue(var1, "angle");
         if (var2 != 0.0F && MathHelper.abs(var2) != 22.5F && MathHelper.abs(var2) != 45.0F) {
            throw new JsonParseException(String.valueOf((new StringBuilder("Invalid rotation ")).append(var2).append(" found, only -45/-22.5/0/22.5/45 allowed")));
         } else {
            return var2;
         }
      }

      private Map func_178250_a(JsonDeserializationContext var1, JsonObject var2) {
         Map var3 = this.func_178253_b(var1, var2);
         if (var3.isEmpty()) {
            throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
         } else {
            return var3;
         }
      }

      private Vector3f func_178247_d(JsonObject var1) {
         Vector3f var2 = this.func_178251_a(var1, "to");
         if (var2.x >= -16.0F && var2.y >= -16.0F && var2.z >= -16.0F && var2.x <= 32.0F && var2.y <= 32.0F && var2.z <= 32.0F) {
            return var2;
         } else {
            throw new JsonParseException(String.valueOf((new StringBuilder("'to' specifier exceeds the allowed boundaries: ")).append(var2)));
         }
      }

      private Map func_178253_b(JsonDeserializationContext var1, JsonObject var2) {
         EnumMap var3 = Maps.newEnumMap(EnumFacing.class);
         JsonObject var4 = JsonUtils.getJsonObject(var2, "faces");
         Iterator var5 = var4.entrySet().iterator();

         while(var5.hasNext()) {
            Entry var6 = (Entry)var5.next();
            EnumFacing var7 = this.func_178248_a((String)var6.getKey());
            var3.put(var7, (BlockPartFace)var1.deserialize((JsonElement)var6.getValue(), BlockPartFace.class));
         }

         return var3;
      }

      public Object deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) {
         return this.func_178254_a(var1, var2, var3);
      }
   }
}
