package net;

import net.aWN;
import net.acE;
import net.awj;
import viaversion.viaversion.api.protocol.Protocol;

public class aR1 extends Protocol {
   private static boolean i;

   public aR1() {
      super(awj.class, awj.class, (Class)null, (Class)null);
   }

   protected void registerPackets() {
      a();
      this.a(awj.TRADE_LIST, new aWN(this));
      if(acE.b() == null) {
         b(false);
      }

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
      if(b()) {
         b(true);
      }

   }
}
