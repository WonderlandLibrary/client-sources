package org.spongepowered.asm.util;

public abstract class ObfuscationUtil {
   private ObfuscationUtil() {
   }

   public static String mapDescriptor(String var0, ObfuscationUtil.IClassRemapper var1) {
      return remapDescriptor(var0, var1, false);
   }

   public static String unmapDescriptor(String var0, ObfuscationUtil.IClassRemapper var1) {
      return remapDescriptor(var0, var1, true);
   }

   private static String remapDescriptor(String var0, ObfuscationUtil.IClassRemapper var1, boolean var2) {
      StringBuilder var3 = new StringBuilder();
      StringBuilder var4 = null;

      for(int var5 = 0; var5 < var0.length(); ++var5) {
         char var6 = var0.charAt(var5);
         if (var4 != null) {
            if (var6 == ';') {
               var3.append('L').append(remap(var4.toString(), var1, var2)).append(';');
               var4 = null;
            } else {
               var4.append(var6);
            }
         } else if (var6 == 'L') {
            var4 = new StringBuilder();
         } else {
            var3.append(var6);
         }
      }

      if (var4 != null) {
         throw new IllegalArgumentException("Invalid descriptor '" + var0 + "', missing ';'");
      } else {
         return var3.toString();
      }
   }

   private static Object remap(String var0, ObfuscationUtil.IClassRemapper var1, boolean var2) {
      String var3 = var2 ? var1.unmap(var0) : var1.map(var0);
      return var3 != null ? var3 : var0;
   }

   public interface IClassRemapper {
      String map(String var1);

      String unmap(String var1);
   }
}
