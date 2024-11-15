package exhibition.util;

public class Timer {
   private long prevMS = 0L;

   public boolean delay(float milliSec) {
      return (float)MathUtils.getIncremental((double)(this.getTime() - this.prevMS), 50.0D) >= milliSec;
   }

   public void reset() {
      this.prevMS = this.getTime();
   }

   public long getTime() {
      return System.nanoTime() / 1000000L;
   }

   public long getDifference() {
      return this.getTime() - this.prevMS;
   }

   public void setDifference(long difference) {
      this.prevMS = this.getTime() - difference;
   }
}
