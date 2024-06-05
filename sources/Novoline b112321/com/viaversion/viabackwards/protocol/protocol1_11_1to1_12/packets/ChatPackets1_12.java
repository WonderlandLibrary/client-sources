package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.ChatPackets1_12$2;
import net.aqu;
import net.ay_;
import net.k3;
import net.km;
import net.lV;

public class ChatPackets1_12 extends aqu {
   private final km a = new k3(this);

   public ChatPackets1_12(ay_ var1) {
      super(var1);
   }

   protected void registerPackets() {
      ((ay_)this.c).a(lV.CHAT_MESSAGE, new ChatPackets1_12$2(this));
   }

   static km a(ChatPackets1_12 var0) {
      return var0.a;
   }
}
