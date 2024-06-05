package net;

import java.util.List;
import net.aTi;
import viaversion.viaversion.api.data.UserConnection;

public class akK {
   private static int b;

   public static void a(aTi var0, int var1, List var2, UserConnection var3) {
      var0.handleMetadata(var1, var2, var3);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 4;
   }

   static {
      if(b() != 0) {
         b(59);
      }

   }
}
