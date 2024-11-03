package xyz.cucumber.base.utils;

public class Timer {
   public long time = System.currentTimeMillis();

   public boolean hasTimeElapsed(double delay, boolean reset) {
      if ((double)(System.currentTimeMillis() - this.time) >= delay) {
         if (reset) {
            this.reset();
         }

         return true;
      } else {
         return false;
      }
   }

   public long getTime() {
      return System.currentTimeMillis() - this.time;
   }

   public void reset() {
      this.time = System.currentTimeMillis();
   }

   public void setTime(long time) {
      this.time = time;
   }
}
