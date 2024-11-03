package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.CommandRewriter;

public class CommandRewriter1_19_4<C extends ClientboundPacketType> extends CommandRewriter<C> {
   public CommandRewriter1_19_4(Protocol<C, ?, ?, ?> protocol) {
      super(protocol);
      this.parserHandlers.put("minecraft:time", wrapper -> {
         Integer var10000 = wrapper.passthrough(Type.INT);
      });
   }
}
