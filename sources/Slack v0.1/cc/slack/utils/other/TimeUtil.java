package cc.slack.utils.other;

public class TimeUtil {
   private long currentMs;

   public TimeUtil() {
      this.reset();
   }

   public boolean hasReached(int milliseconds) {
      return this.elapsed() >= (long)milliseconds;
   }

   public boolean hasReached(long milliseconds) {
      return this.elapsed() >= milliseconds;
   }

   public void resetWithOffset(long offset) {
      this.currentMs = this.getTime() + offset;
   }

   public long elapsed() {
      return System.currentTimeMillis() - this.currentMs;
   }

   public void reset() {
      this.currentMs = System.currentTimeMillis();
   }

   private long getTime() {
      return System.nanoTime() / 1000000L;
   }
}
