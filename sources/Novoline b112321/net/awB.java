package net;

import viaversion.viarewind.replacement.Replacement;
import viaversion.viarewind.replacement.ReplacementRegistry;

public class awB {
   public static void c(ReplacementRegistry var0, int var1, Replacement var2) {
      var0.registerBlock(var1, var2);
   }

   public static void a(ReplacementRegistry var0, int var1, int var2, Replacement var3) {
      var0.registerBlock(var1, var2, var3);
   }

   public static void b(ReplacementRegistry var0, int var1, Replacement var2) {
      var0.registerItem(var1, var2);
   }

   public static void b(ReplacementRegistry var0, int var1, int var2, Replacement var3) {
      var0.registerItem(var1, var2, var3);
   }

   public static void a(ReplacementRegistry var0, int var1, Replacement var2) {
      var0.registerItemBlock(var1, var2);
   }

   public static void c(ReplacementRegistry var0, int var1, int var2, Replacement var3) {
      var0.registerItemBlock(var1, var2, var3);
   }
}
