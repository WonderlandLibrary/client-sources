package net;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ThreadFactory;

public class aS9 {
   private static int b;

   public static ThreadFactoryBuilder a(ThreadFactoryBuilder var0, String var1) {
      return var0.setNameFormat(var1);
   }

   public static ThreadFactoryBuilder a(ThreadFactoryBuilder var0, boolean var1) {
      return var0.setDaemon(var1);
   }

   public static ThreadFactory a(ThreadFactoryBuilder var0) {
      return var0.build();
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 110;
   }

   static {
      if(b() == 0) {
         b(9);
      }

   }
}
