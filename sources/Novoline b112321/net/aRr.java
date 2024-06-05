package net;

import net.aKn;
import net.aKo;
import net.azW;
import net.r;
import viaversion.viaversion.api.protocol.Protocol;

public class aRr extends Protocol {
   private static boolean i;

   public aRr() {
      super(azW.class, azW.class, r.class, r.class);
   }

   protected void registerPackets() {
      this.a(azW.KEEP_ALIVE, new aKo(this));
      a();
      this.a(r.KEEP_ALIVE, new aKn(this));
   }

   public static void b(boolean var0) {
      i = var0;
   }

   public static boolean a() {
      return i;
   }

   public static boolean b() {
      boolean var0 = a();
      return true;
   }

   static {
      if(!b()) {
         b(true);
      }

   }
}
