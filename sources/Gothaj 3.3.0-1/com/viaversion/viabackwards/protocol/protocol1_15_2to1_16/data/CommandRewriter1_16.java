package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data;

import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter1_16 extends CommandRewriter<ClientboundPackets1_16> {
   public CommandRewriter1_16(Protocol1_15_2To1_16 protocol) {
      super(protocol);
   }

   @Nullable
   @Override
   public String handleArgumentType(String argumentType) {
      return argumentType.equals("minecraft:uuid") ? "minecraft:game_profile" : super.handleArgumentType(argumentType);
   }
}
