package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.rewriter.RecipeRewriter1_19_3;

public class RecipeRewriter1_19_4<C extends ClientboundPacketType> extends RecipeRewriter1_19_3<C> {
   public RecipeRewriter1_19_4(Protocol<C, ?, ?, ?> protocol) {
      super(protocol);
   }

   @Override
   public void handleCraftingShaped(PacketWrapper wrapper) throws Exception {
      super.handleCraftingShaped(wrapper);
      wrapper.passthrough(Type.BOOLEAN);
   }
}
