package net.minecraft.client.stream;

import net.minecraft.client.stream.BroadcastController$BroadcastState;

// $FF: synthetic class
class BroadcastController$3 {
   static final int[] $SwitchMap$net$minecraft$client$stream$BroadcastController$BroadcastState = new int[BroadcastController$BroadcastState.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$client$stream$BroadcastController$BroadcastState[BroadcastController$BroadcastState.Authenticated.ordinal()] = 1;
      } catch (NoSuchFieldError var12) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$BroadcastController$BroadcastState[BroadcastController$BroadcastState.LoggedIn.ordinal()] = 2;
      } catch (NoSuchFieldError var11) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$BroadcastController$BroadcastState[BroadcastController$BroadcastState.ReceivedIngestServers.ordinal()] = 3;
      } catch (NoSuchFieldError var10) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$BroadcastController$BroadcastState[BroadcastController$BroadcastState.Starting.ordinal()] = 4;
      } catch (NoSuchFieldError var9) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$BroadcastController$BroadcastState[BroadcastController$BroadcastState.Stopping.ordinal()] = 5;
      } catch (NoSuchFieldError var8) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$BroadcastController$BroadcastState[BroadcastController$BroadcastState.FindingIngestServer.ordinal()] = 6;
      } catch (NoSuchFieldError var7) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$BroadcastController$BroadcastState[BroadcastController$BroadcastState.Authenticating.ordinal()] = 7;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$BroadcastController$BroadcastState[BroadcastController$BroadcastState.Initialized.ordinal()] = 8;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$BroadcastController$BroadcastState[BroadcastController$BroadcastState.Uninitialized.ordinal()] = 9;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$BroadcastController$BroadcastState[BroadcastController$BroadcastState.IngestTesting.ordinal()] = 10;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$BroadcastController$BroadcastState[BroadcastController$BroadcastState.Paused.ordinal()] = 11;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$BroadcastController$BroadcastState[BroadcastController$BroadcastState.Broadcasting.ordinal()] = 12;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
