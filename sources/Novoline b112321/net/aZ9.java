package net;

import java.util.Optional;
import net.acE;
import net.amg;
import net.t4;
import viaversion.viaversion.api.Via;

public class aZ9 {
   private static String b;

   public static t4 a(int var0, boolean var1) {
      boolean var2 = t4.d();
      if(var1) {
         Optional var3 = amg.a(var0);
      }

      Optional var4 = t4.a(var0);
      if(!var4.isPresent()) {
         Via.getPlatform().getLogger().severe("Could not find 1.10 type id " + var0 + " isObject=" + var1);
         return t4.ENTITY;
      } else {
         t4 var10000 = (t4)var4.get();
         if(acE.b() == null) {
            t4.b(false);
         }

         return var10000;
      }
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      b((String)null);
   }
}
