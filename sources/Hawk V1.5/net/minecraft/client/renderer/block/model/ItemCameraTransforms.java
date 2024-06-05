package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;

public class ItemCameraTransforms {
   public final ItemTransformVec3f field_178354_e;
   public final ItemTransformVec3f field_178356_c;
   public static final ItemCameraTransforms field_178357_a;
   public final ItemTransformVec3f field_178353_d;
   public final ItemTransformVec3f field_178355_b;
   private static final String __OBFID = "CL_00002482";

   static {
      field_178357_a = new ItemCameraTransforms(ItemTransformVec3f.field_178366_a, ItemTransformVec3f.field_178366_a, ItemTransformVec3f.field_178366_a, ItemTransformVec3f.field_178366_a);
   }

   public ItemCameraTransforms(ItemTransformVec3f var1, ItemTransformVec3f var2, ItemTransformVec3f var3, ItemTransformVec3f var4) {
      this.field_178355_b = var1;
      this.field_178356_c = var2;
      this.field_178353_d = var3;
      this.field_178354_e = var4;
   }

   public static enum TransformType {
      private static final ItemCameraTransforms.TransformType[] $VALUES = new ItemCameraTransforms.TransformType[]{NONE, THIRD_PERSON, FIRST_PERSON, HEAD, GUI};
      private static final String __OBFID = "CL_00002480";
      THIRD_PERSON("THIRD_PERSON", 1),
      GUI("GUI", 4),
      HEAD("HEAD", 3),
      NONE("NONE", 0);

      private static final ItemCameraTransforms.TransformType[] ENUM$VALUES = new ItemCameraTransforms.TransformType[]{NONE, THIRD_PERSON, FIRST_PERSON, HEAD, GUI};
      FIRST_PERSON("FIRST_PERSON", 2);

      private TransformType(String var3, int var4) {
      }
   }

   static class Deserializer implements JsonDeserializer {
      private static final String __OBFID = "CL_00002481";

      public Object deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) {
         return this.func_178352_a(var1, var2, var3);
      }

      public ItemCameraTransforms func_178352_a(JsonElement var1, Type var2, JsonDeserializationContext var3) {
         JsonObject var4 = var1.getAsJsonObject();
         ItemTransformVec3f var5 = ItemTransformVec3f.field_178366_a;
         ItemTransformVec3f var6 = ItemTransformVec3f.field_178366_a;
         ItemTransformVec3f var7 = ItemTransformVec3f.field_178366_a;
         ItemTransformVec3f var8 = ItemTransformVec3f.field_178366_a;
         if (var4.has("thirdperson")) {
            var5 = (ItemTransformVec3f)var3.deserialize(var4.get("thirdperson"), ItemTransformVec3f.class);
         }

         if (var4.has("firstperson")) {
            var6 = (ItemTransformVec3f)var3.deserialize(var4.get("firstperson"), ItemTransformVec3f.class);
         }

         if (var4.has("head")) {
            var7 = (ItemTransformVec3f)var3.deserialize(var4.get("head"), ItemTransformVec3f.class);
         }

         if (var4.has("gui")) {
            var8 = (ItemTransformVec3f)var3.deserialize(var4.get("gui"), ItemTransformVec3f.class);
         }

         return new ItemCameraTransforms(var5, var6, var7, var8);
      }
   }
}
