package net.minecraft.client.resources.model;

import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

public class ModelResourceLocation extends ResourceLocation {
   private final String field_177519_c;
   private static final String __OBFID = "CL_00002387";

   protected static String[] func_177517_b(String var0) {
      String[] var1 = new String[]{null, var0, null};
      int var2 = var0.indexOf(35);
      String var3 = var0;
      if (var2 >= 0) {
         var1[2] = var0.substring(var2 + 1, var0.length());
         if (var2 > 1) {
            var3 = var0.substring(0, var2);
         }
      }

      System.arraycopy(ResourceLocation.func_177516_a(var3), 0, var1, 0, 2);
      return var1;
   }

   public String toString() {
      return String.valueOf((new StringBuilder(String.valueOf(super.toString()))).append('#').append(this.field_177519_c));
   }

   public String func_177518_c() {
      return this.field_177519_c;
   }

   public ModelResourceLocation(ResourceLocation var1, String var2) {
      this(var1.toString(), var2);
   }

   public ModelResourceLocation(String var1) {
      this(0, func_177517_b(var1));
   }

   protected ModelResourceLocation(int var1, String... var2) {
      super(0, var2[0], var2[1]);
      this.field_177519_c = StringUtils.isEmpty(var2[2]) ? "normal" : var2[2].toLowerCase();
   }

   public ModelResourceLocation(String var1, String var2) {
      this(0, func_177517_b(String.valueOf((new StringBuilder(String.valueOf(var1))).append('#').append(var2 == null ? "normal" : var2))));
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 instanceof ModelResourceLocation && super.equals(var1)) {
         ModelResourceLocation var2 = (ModelResourceLocation)var1;
         return this.field_177519_c.equals(var2.field_177519_c);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return 31 * super.hashCode() + this.field_177519_c.hashCode();
   }
}
