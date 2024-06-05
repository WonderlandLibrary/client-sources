package net;

import cc.novoline.events.events.Event;
import net.minecraft.scoreboard.ScoreObjective;

public class aE6 implements Event {
   private int b;
   private String c;
   private ScoreObjective a;

   public aE6(ScoreObjective var1, int var2, String var3) {
      this.b = var2;
      this.c = var3;
      this.a = var1;
   }

   public int c() {
      return this.b;
   }

   public String a() {
      return this.c;
   }

   public ScoreObjective b() {
      return this.a;
   }
}
