package net;

import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3CPacketUpdateScore$Action;

public class bgm {
   public static String c(S3CPacketUpdateScore var0) {
      return var0.getObjectiveName();
   }

   public static S3CPacketUpdateScore$Action d(S3CPacketUpdateScore var0) {
      return var0.getScoreAction();
   }

   public static String b(S3CPacketUpdateScore var0) {
      return var0.getPlayerName();
   }

   public static int a(S3CPacketUpdateScore var0) {
      return var0.getScoreValue();
   }
}
