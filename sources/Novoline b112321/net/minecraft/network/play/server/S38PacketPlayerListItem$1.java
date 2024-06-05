package net.minecraft.network.play.server;

import net.minecraft.network.play.server.S38PacketPlayerListItem$Action;

// $FF: synthetic class
class S38PacketPlayerListItem$1 {
   static final int[] $SwitchMap$net$minecraft$network$play$server$S38PacketPlayerListItem$Action = new int[S38PacketPlayerListItem$Action.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$network$play$server$S38PacketPlayerListItem$Action[S38PacketPlayerListItem$Action.ADD_PLAYER.ordinal()] = 1;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$network$play$server$S38PacketPlayerListItem$Action[S38PacketPlayerListItem$Action.UPDATE_GAME_MODE.ordinal()] = 2;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$network$play$server$S38PacketPlayerListItem$Action[S38PacketPlayerListItem$Action.UPDATE_LATENCY.ordinal()] = 3;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$network$play$server$S38PacketPlayerListItem$Action[S38PacketPlayerListItem$Action.UPDATE_DISPLAY_NAME.ordinal()] = 4;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$network$play$server$S38PacketPlayerListItem$Action[S38PacketPlayerListItem$Action.REMOVE_PLAYER.ordinal()] = 5;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
