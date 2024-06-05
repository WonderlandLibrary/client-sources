package net;

import java.util.UUID;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.provider.TitleRenderProvider;

public class E1 {
   private static String b;

   public static void b(TitleRenderProvider var0, UUID var1, String var2) {
      var0.setTitle(var1, var2);
   }

   public static void a(TitleRenderProvider var0, UUID var1, String var2) {
      var0.setSubTitle(var1, var2);
   }

   public static void a(TitleRenderProvider var0, UUID var1, int var2, int var3, int var4) {
      var0.setTimings(var1, var2, var3, var4);
   }

   public static void a(TitleRenderProvider var0, UUID var1) {
      var0.clear(var1);
   }

   public static void b(TitleRenderProvider var0, UUID var1) {
      var0.reset(var1);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("Q8c4S");
      }

   }
}
