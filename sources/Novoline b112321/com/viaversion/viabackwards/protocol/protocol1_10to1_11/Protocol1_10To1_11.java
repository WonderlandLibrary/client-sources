package com.viaversion.viabackwards.protocol.protocol1_10to1_11;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets.PlayerPackets1_11;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.Wx;
import net.aAX;
import net.agN;
import net.aqT;
import net.aqp;
import net.bgR;
import net.cA;
import net.cQ;
import net.cT;
import net.co;
import net.y7;

public class Protocol1_10To1_11 extends BackwardsProtocol {
   public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.11", "1.10", (Class)null, true);
   private aqp m;
   private aqT l;
   private static String k;

   public Protocol1_10To1_11() {
      super(agN.class, agN.class, Wx.class, Wx.class);
   }

   protected void registerPackets() {
      a();
      (this.m = new aqp(this)).f();
      (new PlayerPackets1_11()).register(this);
      (this.l = new aqT(this)).f();
      aAX var2 = new aAX(this);
      var2.b((y7)agN.NAMED_SOUND);
      var2.a(agN.SOUND);
   }

   public void a(bgR var1) {
      String var2 = a();
      if(!var1.a(cT.class)) {
         var1.a((cA)(new cT(var1)));
      }

      if(!var1.a(cQ.class)) {
         var1.a((cA)(new cQ(var1)));
      }

      if(!var1.a(co.class)) {
         var1.a((cA)(new co(var1)));
      }

      ((cQ)var1.b(cQ.class)).b(this);
      if(PacketRemapper.b() == null) {
         b("LebTuc");
      }

   }

   public aqp b() {
      return this.m;
   }

   public aqT c() {
      return this.l;
   }

   public BackwardsMappings getMappingData() {
      return MAPPINGS;
   }

   public boolean hasMappingDataToLoad() {
      return true;
   }

   static {
      b("iqnIw");
   }

   public static void b(String var0) {
      k = var0;
   }

   public static String a() {
      return k;
   }
}
