package net.optifine;

import net.acE;
import net.optifine.Lagometer;
import net.optifine.MatchBlock;

public class Lagometer$TimerNano {
   public long timeStartNano = 0L;
   public long timeNano = 0L;

   public void start() {
      acE[] var1 = MatchBlock.b();
      if(Lagometer.active && this.timeStartNano == 0L) {
         this.timeStartNano = System.nanoTime();
      }

   }

   public void end() {
      acE[] var1 = MatchBlock.b();
      if(Lagometer.active && this.timeStartNano != 0L) {
         this.timeNano += System.nanoTime() - this.timeStartNano;
         this.timeStartNano = 0L;
      }

   }

   private void reset() {
      this.timeNano = 0L;
      this.timeStartNano = 0L;
   }

   static void access$000(Lagometer$TimerNano var0) {
      var0.reset();
   }
}
