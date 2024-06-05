package net;

import java.util.List;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;

public class i5 {
   public static void c(Score var0, int var1) {
      var0.setScorePoints(var1);
   }

   public static void a(Score var0, boolean var1) {
      var0.setLocked(var1);
   }

   public static ScoreObjective e(Score var0) {
      return var0.getObjective();
   }

   public static String c(Score var0) {
      return var0.getPlayerName();
   }

   public static int b(Score var0) {
      return var0.getScorePoints();
   }

   public static boolean a(Score var0) {
      return var0.isLocked();
   }

   public static void d(Score var0) {
      var0.func_96648_a();
   }

   public static void a(Score var0, int var1) {
      var0.increseScore(var1);
   }

   public static void a(Score var0, List var1) {
      var0.func_96651_a(var1);
   }

   public static void b(Score var0, int var1) {
      var0.decreaseScore(var1);
   }
}
