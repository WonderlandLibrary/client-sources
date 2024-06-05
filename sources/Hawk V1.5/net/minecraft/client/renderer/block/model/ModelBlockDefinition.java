package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class ModelBlockDefinition {
   private static final String __OBFID = "CL_00002498";
   static final Gson field_178333_a = (new GsonBuilder()).registerTypeAdapter(ModelBlockDefinition.class, new ModelBlockDefinition.Deserializer()).registerTypeAdapter(ModelBlockDefinition.Variant.class, new ModelBlockDefinition.Variant.Deserializer()).create();
   private final Map field_178332_b = Maps.newHashMap();

   public ModelBlockDefinition(List var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         ModelBlockDefinition var3 = (ModelBlockDefinition)var2.next();
         this.field_178332_b.putAll(var3.field_178332_b);
      }

   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 instanceof ModelBlockDefinition) {
         ModelBlockDefinition var2 = (ModelBlockDefinition)var1;
         return this.field_178332_b.equals(var2.field_178332_b);
      } else {
         return false;
      }
   }

   public static ModelBlockDefinition func_178331_a(Reader var0) {
      return (ModelBlockDefinition)field_178333_a.fromJson(var0, ModelBlockDefinition.class);
   }

   public int hashCode() {
      return this.field_178332_b.hashCode();
   }

   public ModelBlockDefinition.Variants func_178330_b(String var1) {
      ModelBlockDefinition.Variants var2 = (ModelBlockDefinition.Variants)this.field_178332_b.get(var1);
      if (var2 == null) {
         throw new ModelBlockDefinition.MissingVariantException(this);
      } else {
         return var2;
      }
   }

   public ModelBlockDefinition(Collection var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         ModelBlockDefinition.Variants var3 = (ModelBlockDefinition.Variants)var2.next();
         this.field_178332_b.put(ModelBlockDefinition.Variants.access$0(var3), var3);
      }

   }

   public static class Variant {
      private static final String __OBFID = "CL_00002495";
      private final boolean field_178436_c;
      private final int field_178434_d;
      private final ModelRotation field_178435_b;
      private final ResourceLocation field_178437_a;

      public int getWeight() {
         return this.field_178434_d;
      }

      public int hashCode() {
         int var1 = this.field_178437_a.hashCode();
         var1 = 31 * var1 + (this.field_178435_b != null ? this.field_178435_b.hashCode() : 0);
         var1 = 31 * var1 + (this.field_178436_c ? 1 : 0);
         return var1;
      }

      public Variant(ResourceLocation var1, ModelRotation var2, boolean var3, int var4) {
         this.field_178437_a = var1;
         this.field_178435_b = var2;
         this.field_178436_c = var3;
         this.field_178434_d = var4;
      }

      public ModelRotation getRotation() {
         return this.field_178435_b;
      }

      public boolean isUvLocked() {
         return this.field_178436_c;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof ModelBlockDefinition.Variant)) {
            return false;
         } else {
            ModelBlockDefinition.Variant var2 = (ModelBlockDefinition.Variant)var1;
            return this.field_178437_a.equals(var2.field_178437_a) && this.field_178435_b == var2.field_178435_b && this.field_178436_c == var2.field_178436_c;
         }
      }

      public ResourceLocation getModelLocation() {
         return this.field_178437_a;
      }

      public static class Deserializer implements JsonDeserializer {
         private static final String __OBFID = "CL_00002494";

         private ResourceLocation func_178426_a(String var1) {
            ResourceLocation var2 = new ResourceLocation(var1);
            var2 = new ResourceLocation(var2.getResourceDomain(), String.valueOf((new StringBuilder("block/")).append(var2.getResourcePath())));
            return var2;
         }

         protected ModelRotation func_178428_a(JsonObject var1) {
            int var2 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var1, "x", 0);
            int var3 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var1, "y", 0);
            ModelRotation var4 = ModelRotation.func_177524_a(var2, var3);
            if (var4 == null) {
               throw new JsonParseException(String.valueOf((new StringBuilder("Invalid BlockModelRotation x: ")).append(var2).append(", y: ").append(var3)));
            } else {
               return var4;
            }
         }

         public Object deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) {
            return this.func_178425_a(var1, var2, var3);
         }

         protected String func_178424_b(JsonObject var1) {
            return JsonUtils.getJsonObjectStringFieldValue(var1, "model");
         }

         private boolean func_178429_d(JsonObject var1) {
            return JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var1, "uvlock", false);
         }

         public ModelBlockDefinition.Variant func_178425_a(JsonElement var1, Type var2, JsonDeserializationContext var3) {
            JsonObject var4 = var1.getAsJsonObject();
            String var5 = this.func_178424_b(var4);
            ModelRotation var6 = this.func_178428_a(var4);
            boolean var7 = this.func_178429_d(var4);
            int var8 = this.func_178427_c(var4);
            return new ModelBlockDefinition.Variant(this.func_178426_a(var5), var6, var7, var8);
         }

         protected int func_178427_c(JsonObject var1) {
            return JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var1, "weight", 1);
         }
      }
   }

   public static class Deserializer implements JsonDeserializer {
      private static final String __OBFID = "CL_00002497";

      public ModelBlockDefinition func_178336_a(JsonElement var1, Type var2, JsonDeserializationContext var3) {
         JsonObject var4 = var1.getAsJsonObject();
         List var5 = this.func_178334_a(var3, var4);
         return new ModelBlockDefinition(var5);
      }

      protected ModelBlockDefinition.Variants func_178335_a(JsonDeserializationContext var1, Entry var2) {
         String var3 = (String)var2.getKey();
         ArrayList var4 = Lists.newArrayList();
         JsonElement var5 = (JsonElement)var2.getValue();
         if (var5.isJsonArray()) {
            Iterator var6 = var5.getAsJsonArray().iterator();

            while(var6.hasNext()) {
               JsonElement var7 = (JsonElement)var6.next();
               var4.add((ModelBlockDefinition.Variant)var1.deserialize(var7, ModelBlockDefinition.Variant.class));
            }
         } else {
            var4.add((ModelBlockDefinition.Variant)var1.deserialize(var5, ModelBlockDefinition.Variant.class));
         }

         return new ModelBlockDefinition.Variants(var3, var4);
      }

      protected List func_178334_a(JsonDeserializationContext var1, JsonObject var2) {
         JsonObject var3 = JsonUtils.getJsonObject(var2, "variants");
         ArrayList var4 = Lists.newArrayList();
         Iterator var5 = var3.entrySet().iterator();

         while(var5.hasNext()) {
            Entry var6 = (Entry)var5.next();
            var4.add(this.func_178335_a(var1, var6));
         }

         return var4;
      }

      public Object deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) {
         return this.func_178336_a(var1, var2, var3);
      }
   }

   public class MissingVariantException extends RuntimeException {
      final ModelBlockDefinition this$0;
      private static final String __OBFID = "CL_00002496";

      public MissingVariantException(ModelBlockDefinition var1) {
         this.this$0 = var1;
      }
   }

   public static class Variants {
      private static final String __OBFID = "CL_00002493";
      private final String field_178423_a;
      private final List field_178422_b;

      public int hashCode() {
         int var1 = this.field_178423_a.hashCode();
         var1 = 31 * var1 + this.field_178422_b.hashCode();
         return var1;
      }

      public Variants(String var1, List var2) {
         this.field_178423_a = var1;
         this.field_178422_b = var2;
      }

      static String access$0(ModelBlockDefinition.Variants var0) {
         return var0.field_178423_a;
      }

      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof ModelBlockDefinition.Variants)) {
            return false;
         } else {
            ModelBlockDefinition.Variants var2 = (ModelBlockDefinition.Variants)var1;
            return !this.field_178423_a.equals(var2.field_178423_a) ? false : this.field_178422_b.equals(var2.field_178422_b);
         }
      }

      public List getVariants() {
         return this.field_178422_b;
      }
   }
}
