package net.augustus.utils;

public class TimeHelper {
   public long time = System.currentTimeMillis();

   public boolean reached(long currentTime) {
      return Math.max(0L, System.currentTimeMillis() - this.time) >= currentTime;
   }

   public boolean reached(long lastTime, long currentTime) {
      return Math.max(0L, System.currentTimeMillis() - this.time + lastTime) >= currentTime;
   }

   public void reset() {
      this.time = System.currentTimeMillis();
   }

   public long getTime() {
      return Math.max(0L, System.currentTimeMillis() - this.time);
   }
}
