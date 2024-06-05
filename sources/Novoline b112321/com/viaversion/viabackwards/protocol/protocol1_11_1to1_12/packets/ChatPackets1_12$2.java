package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.google.gson.JsonElement;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.ChatPackets1_12;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;

class ChatPackets1_12$2 extends PacketRemapper {
   final ChatPackets1_12 this$0;

   ChatPackets1_12$2(ChatPackets1_12 var1) {
      this.this$0 = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapperImpl var1) throws Exception {
      JsonElement var2 = (JsonElement)var1.a(Type.p);
      ChatPackets1_12.a(this.this$0).a((JsonElement)var2);
   }
}
