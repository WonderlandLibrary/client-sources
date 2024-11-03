package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.data;

import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.Protocol1_18_2To1_19;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
import com.viaversion.viaversion.rewriter.CommandRewriter;

public final class CommandRewriter1_19 extends CommandRewriter<ClientboundPackets1_19> {
   public CommandRewriter1_19(Protocol1_18_2To1_19 protocol) {
      super(protocol);
      this.parserHandlers.put("minecraft:template_mirror", wrapper -> wrapper.write(Type.VAR_INT, 0));
      this.parserHandlers.put("minecraft:template_rotation", wrapper -> wrapper.write(Type.VAR_INT, 0));
   }
}
