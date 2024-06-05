package net;

import cc.novoline.modules.misc.WindowedFullscreen;
import net.UW;
import net.a2V;
import net.aSt;
import net.abf;
import net.agu;
import net.as0;

public class avo extends as0 {
   public avo(UW var1) {
      super(var1, a2V.MISC, "Debug");
   }

   @agu
   public void a(aSt var1) {
      WindowedFullscreen.a();
      byte var3 = 121;

      try {
         Object[] var4 = new Object[]{"Timer: " + this.w.timer.i, "blocks/s: " + this.w.thePlayer.ay() * 20.0D, Float.valueOf(this.w.thePlayer.fallDistance), this.w.theWorld.getScoreboard().getObjectiveInDisplaySlot(1).getDisplayName()};
         int var5 = var4.length;
         int var6 = 0;
         if(var6 < var5) {
            Object var7 = var4[var6];
            abf.a.a(String.valueOf(var7), 1.0D, (double)var3, 16777215, true);
            int var10000 = var3 + abf.a.c() + 2;
            ++var6;
         }
      } catch (NullPointerException var8) {
         ;
      }

   }

   private static NullPointerException a(NullPointerException var0) {
      return var0;
   }
}
