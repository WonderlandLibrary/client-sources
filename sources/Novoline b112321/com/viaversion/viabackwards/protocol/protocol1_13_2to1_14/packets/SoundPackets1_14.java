package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import net.aAX;
import net.aoq;
import net.aqu;
import net.awj;
import net.y7;

public class SoundPackets1_14 extends aqu {
   public SoundPackets1_14(Protocol1_13_2To1_14 var1) {
      super(var1);
   }

   protected void registerPackets() {
      aAX var1 = new aAX(this.c);
      var1.a(awj.SOUND);
      var1.b((y7)awj.NAMED_SOUND);
      var1.c(awj.STOP_SOUND);
      ((Protocol1_13_2To1_14)this.c).a(awj.ENTITY_SOUND, (y7)null, new aoq(this));
   }

   static BackwardsProtocol a(SoundPackets1_14 var0) {
      return var0.c;
   }

   static BackwardsProtocol b(SoundPackets1_14 var0) {
      return var0.c;
   }
}
