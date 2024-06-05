package hawk.util;

public class Timer {
   public long lastMS = System.currentTimeMillis();

   public void reset() {
      this.lastMS = System.currentTimeMillis();
   }

   public boolean hasTimeElapsed(long var1, boolean var3) {
      if (System.currentTimeMillis() - this.lastMS > var1) {
         if (var3) {
            this.reset();
         }

         return true;
      } else {
         return false;
      }
   }
}
