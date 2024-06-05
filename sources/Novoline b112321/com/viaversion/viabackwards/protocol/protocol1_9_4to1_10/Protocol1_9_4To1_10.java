package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10$1;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10$2;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10$3;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10$4;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import net.Wx;
import net.aAX;
import net.agN;
import net.aq1;
import net.aqR;
import net.bgR;
import net.cA;
import net.cQ;
import net.cT;
import net.rL;

public class Protocol1_9_4To1_10 extends BackwardsProtocol {
   public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.10", "1.9.4", (Class)null, true);
   private static final ValueTransformer TO_OLD_PITCH = new Protocol1_9_4To1_10$1(Type.M);
   private aq1 k;
   private aqR l;

   public Protocol1_9_4To1_10() {
      super(agN.class, agN.class, Wx.class, Wx.class);
   }

   protected void registerPackets() {
      rL.a();
      (this.k = new aq1(this)).f();
      (this.l = new aqR(this)).f();
      aAX var2 = new aAX(this);
      this.a(agN.NAMED_SOUND, new Protocol1_9_4To1_10$2(this, var2));
      this.a(agN.SOUND, new Protocol1_9_4To1_10$3(this, var2));
      this.a(Wx.RESOURCE_PACK_STATUS, new Protocol1_9_4To1_10$4(this));
   }

   public void a(bgR var1) {
      boolean var2 = rL.a();
      if(!var1.a(cT.class)) {
         var1.a((cA)(new cT(var1)));
      }

      if(!var1.a(cQ.class)) {
         var1.a((cA)(new cQ(var1)));
      }

      ((cQ)var1.b(cQ.class)).b(this);
   }

   public aq1 b() {
      return this.k;
   }

   public aqR c() {
      return this.l;
   }

   public BackwardsMappings getMappingData() {
      return MAPPINGS;
   }

   public boolean hasMappingDataToLoad() {
      return true;
   }

   static ValueTransformer access$000() {
      return TO_OLD_PITCH;
   }
}
