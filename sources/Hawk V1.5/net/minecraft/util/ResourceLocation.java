package net.minecraft.util;

import org.apache.commons.lang3.Validate;

public class ResourceLocation {
   protected final String resourceDomain;
   protected final String resourcePath;
   private static final String __OBFID = "CL_00001082";

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ResourceLocation)) {
         return false;
      } else {
         ResourceLocation var2 = (ResourceLocation)var1;
         return this.resourceDomain.equals(var2.resourceDomain) && this.resourcePath.equals(var2.resourcePath);
      }
   }

   public String getResourceDomain() {
      return this.resourceDomain;
   }

   public String getResourcePath() {
      return this.resourcePath;
   }

   public int hashCode() {
      return 31 * this.resourceDomain.hashCode() + this.resourcePath.hashCode();
   }

   public ResourceLocation(String var1, String var2) {
      this(0, var1, var2);
   }

   public String toString() {
      return String.valueOf((new StringBuilder(String.valueOf(this.resourceDomain))).append(':').append(this.resourcePath));
   }

   public ResourceLocation(String var1) {
      this(0, func_177516_a(var1));
   }

   protected static String[] func_177516_a(String var0) {
      String[] var1 = new String[]{null, var0};
      int var2 = var0.indexOf(58);
      if (var2 >= 0) {
         var1[1] = var0.substring(var2 + 1, var0.length());
         if (var2 > 1) {
            var1[0] = var0.substring(0, var2);
         }
      }

      return var1;
   }

   protected ResourceLocation(int var1, String... var2) {
      this.resourceDomain = org.apache.commons.lang3.StringUtils.isEmpty(var2[0]) ? "minecraft" : var2[0].toLowerCase();
      this.resourcePath = var2[1];
      Validate.notNull(this.resourcePath);
   }
}
