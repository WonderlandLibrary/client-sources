package net;

import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.IScoreObjectiveCriteria$EnumRenderType;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;

public class arU {
   public static void a(ScoreObjective var0, String var1) {
      var0.setDisplayName(var1);
   }

   public static void a(ScoreObjective var0, IScoreObjectiveCriteria$EnumRenderType var1) {
      var0.setRenderType(var1);
   }

   public static String a(ScoreObjective var0) {
      return var0.getName();
   }

   public static IScoreObjectiveCriteria b(ScoreObjective var0) {
      return var0.getCriteria();
   }

   public static String d(ScoreObjective var0) {
      return var0.getDisplayName();
   }

   public static IScoreObjectiveCriteria$EnumRenderType c(ScoreObjective var0) {
      return var0.getRenderType();
   }

   public static Scoreboard e(ScoreObjective var0) {
      return var0.getScoreboard();
   }
}
