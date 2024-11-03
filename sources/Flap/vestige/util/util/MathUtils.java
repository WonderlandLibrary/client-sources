package vestige.util.util;

public class MathUtils {
   public static double randomizeDouble(double min, double max) {
      return Math.random() * (max - min) + min;
   }

   public static int toPercentage(int atual, int max) {
      double percentageunresolved = (double)(atual / max);
      int percentage = (int)(percentageunresolved * 100.0D);
      return percentage;
   }

   public static double toPercentage(double atual, double max) {
      double percentageunresolved = atual / max;
      int percentage = (int)(percentageunresolved * 100.0D);
      return (double)percentage;
   }

   public static float toPercentage(float atual, float max) {
      double percentageunresolved = (double)(atual / max);
      int percentage = (int)(percentageunresolved * 100.0D);
      return (float)percentage;
   }

   public static int toPercentagerounded(double atual, double max) {
      double percentageUnresolved = atual / max;
      int percentage = (int)(percentageUnresolved * 100.0D);
      return percentage;
   }
}
