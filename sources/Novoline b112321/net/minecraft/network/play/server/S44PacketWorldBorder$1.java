package net.minecraft.network.play.server;

import net.minecraft.network.play.server.S44PacketWorldBorder$Action;

// $FF: synthetic class
class S44PacketWorldBorder$1 {
   static final int[] $SwitchMap$net$minecraft$network$play$server$S44PacketWorldBorder$Action = new int[S44PacketWorldBorder$Action.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$network$play$server$S44PacketWorldBorder$Action[S44PacketWorldBorder$Action.SET_SIZE.ordinal()] = 1;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$network$play$server$S44PacketWorldBorder$Action[S44PacketWorldBorder$Action.LERP_SIZE.ordinal()] = 2;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$network$play$server$S44PacketWorldBorder$Action[S44PacketWorldBorder$Action.SET_CENTER.ordinal()] = 3;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$network$play$server$S44PacketWorldBorder$Action[S44PacketWorldBorder$Action.SET_WARNING_BLOCKS.ordinal()] = 4;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$network$play$server$S44PacketWorldBorder$Action[S44PacketWorldBorder$Action.SET_WARNING_TIME.ordinal()] = 5;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$network$play$server$S44PacketWorldBorder$Action[S44PacketWorldBorder$Action.INITIALIZE.ordinal()] = 6;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
