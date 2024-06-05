package net.minecraft.scoreboard;

import java.util.Comparator;
import net.minecraft.scoreboard.Score;

final class Score$1 implements Comparator {
   public int compare(Score var1, Score var2) {
      return var1.getScorePoints() > var2.getScorePoints()?1:(var1.getScorePoints() < var2.getScorePoints()?-1:var2.getPlayerName().compareToIgnoreCase(var1.getPlayerName()));
   }
}
