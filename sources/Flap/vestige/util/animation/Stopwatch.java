package vestige.util.animation;

public class Stopwatch {
   private static long startTime;
   private static long elapsedTime;

   public void start() {
      startTime = System.currentTimeMillis();
      elapsedTime = 0L;
   }

   public static boolean finished(long duration) {
      elapsedTime = System.currentTimeMillis() - startTime;
      return elapsedTime >= duration;
   }

   public void reset() {
      startTime = 0L;
      elapsedTime = 0L;
   }
}
