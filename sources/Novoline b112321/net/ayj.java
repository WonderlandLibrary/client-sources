package net;

import net.Wx;
import net.acE;
import net.agN;
import net.aqL;
import net.aqy;
import net.ayd;
import net.cA;
import net.cQ;
import net.cT;
import viaversion.viaversion.api.data.UserConnection;

public class ayj extends ayd {
   private aqL k;
   private static int j;

   public ayj() {
      super(agN.class, agN.class, Wx.class, Wx.class);
   }

   protected void registerPackets() {
      int var10000 = b();
      (this.k = new aqL(this)).f();
      int var1 = var10000;
      (new aqy(this)).f();
      if(acE.b() == null) {
         ++var1;
         b(var1);
      }

   }

   public void init(UserConnection var1) {
      int var2 = d();
      if(!var1.has(cT.class)) {
         var1.a((cA)(new cT(var1)));
      }

      if(!var1.has(cQ.class)) {
         var1.a((cA)(new cQ(var1)));
      }

      ((cQ)var1.b(cQ.class)).b(this);
   }

   public aqL a() {
      return this.k;
   }

   public static void b(int var0) {
      j = var0;
   }

   public static int d() {
      return j;
   }

   public static int b() {
      int var0 = d();
      return 4;
   }

   static {
      if(b() != 0) {
         b(55);
      }

   }
}
