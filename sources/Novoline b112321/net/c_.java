package net;

import cc.novoline.utils.thealtening.TheAlteningAuthentication;
import cc.novoline.utils.thealtening.service.AlteningServiceType;
import net.acE;

public class c_ {
   private static acE[] b;

   public static void a(TheAlteningAuthentication var0, AlteningServiceType var1) {
      var0.updateService(var1);
   }

   public static TheAlteningAuthentication c() {
      return TheAlteningAuthentication.mojang();
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[5]);
      }

   }
}
