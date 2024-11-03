package vestige.util.util;

public class TimeUtil {
   public static long currentMs;

   public TimeUtil() {
      reset();
   }

   public static boolean hasReached(int milliseconds) {
      return elapsed() >= (long)milliseconds;
   }

   public static boolean hasReached(long milliseconds) {
      return elapsed() >= milliseconds;
   }

   public static void resetWithOffset(long offset) {
      currentMs = getTime() + offset;
   }

   public static long elapsed() {
      return System.currentTimeMillis() - currentMs;
   }

   public static void reset() {
      currentMs = System.currentTimeMillis();
   }

   public static boolean isDelayComplete(float delay) {
      return (float)(System.currentTimeMillis() - currentMs) > delay;
   }

   public static long getTime() {
      return System.nanoTime() / 1000000L;
   }
}
