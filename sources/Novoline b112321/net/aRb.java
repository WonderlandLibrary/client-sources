package net;

import net.aTV;
import net.acE;
import net.ahW;
import net.awj;
import net.cA;
import net.cb;
import net.zK;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.protocol.Protocol;

public class aRb extends Protocol {
   private static String i;

   public aRb() {
      super(awj.class, awj.class, ahW.class, ahW.class);
   }

   protected void registerPackets() {
      a();
      new aTV(this);
      zK.a(this);
   }

   public void init(UserConnection var1) {
      a();
      var1.a((cA)(new cb(var1)));
      if(acE.b() == null) {
         b("B5UN1b");
      }

   }

   public static void b(String var0) {
      i = var0;
   }

   public static String a() {
      return i;
   }

   static {
      if(a() != null) {
         b("Nri2Gc");
      }

   }
}
