package vestige.util.misc;

public class TimerUtil {
   private long lastTime;

   public TimerUtil() {
      this.reset();
   }

   public long getTimeElapsed() {
      return System.currentTimeMillis() - this.lastTime;
   }

   public void setTimeElapsed(long time) {
      this.lastTime = System.currentTimeMillis() - time;
   }

   public void reset() {
      this.lastTime = System.currentTimeMillis();
   }
}
