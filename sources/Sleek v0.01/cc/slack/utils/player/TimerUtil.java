package cc.slack.utils.player;

import cc.slack.utils.client.mc;

public class TimerUtil extends mc {
   private static final float DEFAULT_TIMER = 1.0F;

   public static void set(float timer) {
      setTimer(timer, 0);
   }

   public static void setTick(float timer, int tick) {
      setTimer(timer, tick);
   }

   public static void setTickOnGround(float timer, int tick) {
      setTimer(getPlayer().onGround ? timer : 1.0F, tick);
   }

   public static void setOnGround(float timer) {
      setTimer(getPlayer().onGround ? timer : 1.0F, 0);
   }

   private static void setTimer(float timer, int tick) {
      if (!shouldTimer()) {
         reset();
      } else {
         if (tick == 0) {
            getTimer().timerSpeed = timer;
         } else {
            getTimer().timerSpeed = mc.getPlayer().ticksExisted % tick == 0 ? timer : 1.0F;
         }

      }
   }

   private static boolean shouldTimer() {
      return getPlayer() != null && getWorld() != null && getPlayer().isEntityAlive();
   }

   public static void reset() {
      getTimer().timerSpeed = 1.0F;
   }
}
