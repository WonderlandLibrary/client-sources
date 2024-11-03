package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.data;

import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter1_13_1 extends CommandRewriter<ClientboundPackets1_13> {
   public CommandRewriter1_13_1(Protocol1_13To1_13_1 protocol) {
      super(protocol);
      this.parserHandlers.put("minecraft:dimension", wrapper -> wrapper.write(Type.VAR_INT, 0));
   }

   @Nullable
   @Override
   public String handleArgumentType(String argumentType) {
      if (argumentType.equals("minecraft:column_pos")) {
         return "minecraft:vec2";
      } else {
         return argumentType.equals("minecraft:dimension") ? "brigadier:string" : super.handleArgumentType(argumentType);
      }
   }
}
