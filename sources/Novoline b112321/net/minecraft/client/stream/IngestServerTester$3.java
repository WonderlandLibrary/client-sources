package net.minecraft.client.stream;

import net.minecraft.client.stream.IngestServerTester$IngestTestState;
import tv.twitch.broadcast.StatType;

// $FF: synthetic class
class IngestServerTester$3 {
   static final int[] $SwitchMap$tv$twitch$broadcast$StatType;
   static final int[] $SwitchMap$net$minecraft$client$stream$IngestServerTester$IngestTestState = new int[IngestServerTester$IngestTestState.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$client$stream$IngestServerTester$IngestTestState[IngestServerTester$IngestTestState.Starting.ordinal()] = 1;
      } catch (NoSuchFieldError var11) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$IngestServerTester$IngestTestState[IngestServerTester$IngestTestState.DoneTestingServer.ordinal()] = 2;
      } catch (NoSuchFieldError var10) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$IngestServerTester$IngestTestState[IngestServerTester$IngestTestState.ConnectingToServer.ordinal()] = 3;
      } catch (NoSuchFieldError var9) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$IngestServerTester$IngestTestState[IngestServerTester$IngestTestState.TestingServer.ordinal()] = 4;
      } catch (NoSuchFieldError var8) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$IngestServerTester$IngestTestState[IngestServerTester$IngestTestState.Cancelling.ordinal()] = 5;
      } catch (NoSuchFieldError var7) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$IngestServerTester$IngestTestState[IngestServerTester$IngestTestState.Uninitalized.ordinal()] = 6;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$IngestServerTester$IngestTestState[IngestServerTester$IngestTestState.Finished.ordinal()] = 7;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$IngestServerTester$IngestTestState[IngestServerTester$IngestTestState.Cancelled.ordinal()] = 8;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$stream$IngestServerTester$IngestTestState[IngestServerTester$IngestTestState.Failed.ordinal()] = 9;
      } catch (NoSuchFieldError var3) {
         ;
      }

      $SwitchMap$tv$twitch$broadcast$StatType = new int[StatType.values().length];

      try {
         $SwitchMap$tv$twitch$broadcast$StatType[StatType.TTV_ST_RTMPSTATE.ordinal()] = 1;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$tv$twitch$broadcast$StatType[StatType.TTV_ST_RTMPDATASENT.ordinal()] = 2;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
