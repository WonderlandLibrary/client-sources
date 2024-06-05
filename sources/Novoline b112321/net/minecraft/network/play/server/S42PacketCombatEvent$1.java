package net.minecraft.network.play.server;

import net.minecraft.network.play.server.S42PacketCombatEvent$Event;

// $FF: synthetic class
class S42PacketCombatEvent$1 {
   static final int[] $SwitchMap$net$minecraft$network$play$server$S42PacketCombatEvent$Event = new int[S42PacketCombatEvent$Event.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$network$play$server$S42PacketCombatEvent$Event[S42PacketCombatEvent$Event.END_COMBAT.ordinal()] = 1;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$network$play$server$S42PacketCombatEvent$Event[S42PacketCombatEvent$Event.ENTITY_DIED.ordinal()] = 2;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
