package exhibition.util.misc;

public class Timer {
   private long previousTime = -1L;

   public boolean check(float milliseconds) {
      return (float)this.getTime() >= milliseconds;
   }

   public long getTime() {
      return this.getCurrentTime() - this.previousTime;
   }

   public void reset() {
      this.previousTime = this.getCurrentTime();
   }

   public long getCurrentTime() {
      return System.currentTimeMillis();
   }
}
