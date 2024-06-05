package net.minecraft.client.network;

import net.minecraft.network.play.server.S38PacketPlayerListItem$Action;
import net.minecraft.network.play.server.S45PacketTitle$Type;

// $FF: synthetic class
class NetHandlerPlayClient$4 {
   static final int[] $SwitchMap$net$minecraft$network$play$server$S45PacketTitle$Type;
   static final int[] $SwitchMap$net$minecraft$network$play$server$S38PacketPlayerListItem$Action = new int[S38PacketPlayerListItem$Action.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$network$play$server$S38PacketPlayerListItem$Action[S38PacketPlayerListItem$Action.ADD_PLAYER.ordinal()] = 1;
      } catch (NoSuchFieldError var7) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$network$play$server$S38PacketPlayerListItem$Action[S38PacketPlayerListItem$Action.UPDATE_GAME_MODE.ordinal()] = 2;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$network$play$server$S38PacketPlayerListItem$Action[S38PacketPlayerListItem$Action.UPDATE_LATENCY.ordinal()] = 3;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$network$play$server$S38PacketPlayerListItem$Action[S38PacketPlayerListItem$Action.UPDATE_DISPLAY_NAME.ordinal()] = 4;
      } catch (NoSuchFieldError var4) {
         ;
      }

      $SwitchMap$net$minecraft$network$play$server$S45PacketTitle$Type = new int[S45PacketTitle$Type.values().length];

      try {
         $SwitchMap$net$minecraft$network$play$server$S45PacketTitle$Type[S45PacketTitle$Type.TITLE.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$network$play$server$S45PacketTitle$Type[S45PacketTitle$Type.SUBTITLE.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$network$play$server$S45PacketTitle$Type[S45PacketTitle$Type.RESET.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
