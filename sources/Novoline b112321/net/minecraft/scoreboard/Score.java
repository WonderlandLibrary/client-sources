package net.minecraft.scoreboard;

import java.util.Comparator;
import java.util.List;
import net.minecraft.scoreboard.Score$1;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;

public class Score {
   public static final Comparator scoreComparator = new Score$1();
   private final Scoreboard theScoreboard;
   private final ScoreObjective theScoreObjective;
   private final String scorePlayerName;
   private int scorePoints;
   private boolean locked;
   private boolean field_178818_g;

   public Score(Scoreboard var1, ScoreObjective var2, String var3) {
      this.theScoreboard = var1;
      this.theScoreObjective = var2;
      this.scorePlayerName = var3;
      this.field_178818_g = true;
   }

   public void increseScore(int var1) {
      if(this.theScoreObjective.getCriteria().isReadOnly()) {
         throw new IllegalStateException("Cannot modify read-only score");
      } else {
         this.setScorePoints(this.getScorePoints() + var1);
      }
   }

   public void decreaseScore(int var1) {
      if(this.theScoreObjective.getCriteria().isReadOnly()) {
         throw new IllegalStateException("Cannot modify read-only score");
      } else {
         this.setScorePoints(this.getScorePoints() - var1);
      }
   }

   public void func_96648_a() {
      if(this.theScoreObjective.getCriteria().isReadOnly()) {
         throw new IllegalStateException("Cannot modify read-only score");
      } else {
         this.increseScore(1);
      }
   }

   public int getScorePoints() {
      return this.scorePoints;
   }

   public void setScorePoints(int var1) {
      int var2 = this.scorePoints;
      this.scorePoints = var1;
      if(var2 != var1 || this.field_178818_g) {
         this.field_178818_g = false;
         this.getScoreScoreboard().func_96536_a(this);
      }

   }

   public ScoreObjective getObjective() {
      return this.theScoreObjective;
   }

   public String getPlayerName() {
      return this.scorePlayerName;
   }

   public Scoreboard getScoreScoreboard() {
      return this.theScoreboard;
   }

   public boolean isLocked() {
      return this.locked;
   }

   public void setLocked(boolean var1) {
      this.locked = var1;
   }

   public void func_96651_a(List var1) {
      this.setScorePoints(this.theScoreObjective.getCriteria().func_96635_a(var1));
   }

   private static IllegalStateException a(IllegalStateException var0) {
      return var0;
   }
}
