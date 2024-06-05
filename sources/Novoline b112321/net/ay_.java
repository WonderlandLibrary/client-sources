package net;

import net.Wx;
import net.aEb;
import net.acE;
import net.act;
import net.agN;
import net.aq0;
import net.aqw;
import net.ayd;
import net.cA;
import net.cQ;
import net.cT;
import net.lV;
import viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.ShoulderTracker;
import viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.ChatPackets1_12;
import viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.SoundPackets1_12;
import viaversion.viaversion.api.data.UserConnection;

public class ay_ extends ayd {
   private aqw l;
   private aq0 k;
   private static int j;

   public ay_() {
      super(lV.class, agN.class, aEb.class, Wx.class);
   }

   protected void registerPackets() {
      d();
      (this.l = new aqw(this)).f();
      (this.k = new aq0(this)).f();
      (new SoundPackets1_12(this)).f();
      (new ChatPackets1_12(this)).f();
      this.a(lV.TITLE, new act(this));
      this.cancelOutgoing(lV.ADVANCEMENTS);
      this.cancelOutgoing(lV.UNLOCK_RECIPES);
      this.cancelOutgoing(lV.SELECT_ADVANCEMENTS_TAB);
   }

   public void init(UserConnection var1) {
      int var2 = d();
      if(!var1.has(cT.class)) {
         var1.a((cA)(new cT(var1)));
      }

      if(!var1.has(cQ.class)) {
         var1.a((cA)(new cQ(var1)));
      }

      var1.a((cA)(new ShoulderTracker(var1)));
      ((cQ)var1.b(cQ.class)).b(this);
      if(acE.b() == null) {
         ++var2;
         b(var2);
      }

   }

   public aqw e() {
      return this.l;
   }

   public aq0 a() {
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
      return 59;
   }

   static {
      if(b() != 0) {
         b(120);
      }

   }
}
