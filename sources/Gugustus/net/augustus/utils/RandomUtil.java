package net.augustus.utils;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {
   public static int nextInt(int origin, int bound) {
      return origin == bound ? origin : ThreadLocalRandom.current().nextInt(origin, bound);
   }

   public static long nextLong(long origin, long bound) {
      return origin == bound ? origin : ThreadLocalRandom.current().nextLong(origin, bound);
   }

   public static float nextFloat(double origin, double bound) {
      return origin == bound ? (float)origin : (float)ThreadLocalRandom.current().nextDouble((double)((float)origin), (double)((float)bound));
   }

   public static float nextFloat(float origin, float bound) {
      return origin == bound ? origin : (float)ThreadLocalRandom.current().nextDouble((double)origin, (double)bound);
   }

   public static double nextDouble(double origin, double bound) {
      return origin == bound ? origin : ThreadLocalRandom.current().nextDouble(origin, bound);
   }

   public static double nextSecureInt(int origin, int bound) {
      if (origin == bound) {
         return (double)origin;
      } else {
         SecureRandom secureRandom = new SecureRandom();
         int difference = bound - origin;
         return (double)(origin + secureRandom.nextInt(difference));
      }
   }

   public static double nextSecureDouble(double origin, double bound) {
      if (origin == bound) {
         return origin;
      } else {
         SecureRandom secureRandom = new SecureRandom();
         double difference = bound - origin;
         return origin + secureRandom.nextDouble() * difference;
      }
   }

   public static float nextSecureFloat(double origin, double bound) {
      if (origin == bound) {
         return (float)origin;
      } else {
         SecureRandom secureRandom = new SecureRandom();
         float difference = (float)(bound - origin);
         return (float)(origin + (double)(secureRandom.nextFloat() * difference));
      }
   }

   public static double randomSin() {
      return Math.sin(nextDouble(0.0, Math.PI * 2));
   }
}
