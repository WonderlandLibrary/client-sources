package net.minecraft.server.network;

import net.minecraft.network.EnumConnectionState;

// $FF: synthetic class
class NetHandlerHandshakeTCP$1 {
   static final int[] $SwitchMap$net$minecraft$network$EnumConnectionState = new int[EnumConnectionState.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$network$EnumConnectionState[EnumConnectionState.LOGIN.ordinal()] = 1;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$network$EnumConnectionState[EnumConnectionState.STATUS.ordinal()] = 2;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
