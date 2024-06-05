package net.minecraft.server.network;

enum NetHandlerLoginServer$LoginState {
   HELLO,
   KEY,
   AUTHENTICATING,
   READY_TO_ACCEPT,
   DELAY_ACCEPT,
   ACCEPTED;

   private static final NetHandlerLoginServer$LoginState[] $VALUES = new NetHandlerLoginServer$LoginState[]{HELLO, KEY, AUTHENTICATING, READY_TO_ACCEPT, DELAY_ACCEPT, ACCEPTED};
}
