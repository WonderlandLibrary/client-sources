package net;

import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import java.util.List;
import net.aHK;
import net.acE;
import net.akP;

public class xi {
   private static acE[] b;

   public static aHK a(akP var0) {
      return var0.a();
   }

   public static List c(akP var0) {
      return var0.d();
   }

   public static EnumModuleType b(akP var0) {
      return var0.c();
   }

   public static boolean a(akP var0, AbstractModule var1) {
      return var0.a(var1);
   }

   public static boolean d(akP var0) {
      return var0.f();
   }

   public static String e(akP var0) {
      return var0.h();
   }

   public static void a(akP var0, boolean var1) {
      var0.a(var1);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[4]);
      }

   }
}
