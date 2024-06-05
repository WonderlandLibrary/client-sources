package net;

import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardSaveData;

public class a8J {
   public static void a(ScoreboardSaveData var0) {
      var0.markDirty();
   }

   public static void a(ScoreboardSaveData var0, Scoreboard var1) {
      var0.setScoreboard(var1);
   }
}
