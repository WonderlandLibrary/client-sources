package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets;

import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.BlockItemPackets1_15;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.aMz;

class BlockItemPackets1_15$1 extends PacketRemapper {
   final BlockItemPackets1_15 this$0;

   BlockItemPackets1_15$1(BlockItemPackets1_15 var1) {
      this.this$0 = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapperImpl var1) throws Exception {
      this.this$0.c((aMz)var1.a(Type.FLAT_VAR_INT_ITEM));
   }
}
