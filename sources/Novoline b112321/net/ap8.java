package net;

import java.util.Calendar;
import java.util.Date;

public class ap8 {
   public static Calendar a() {
      return Calendar.getInstance();
   }

   public static void a(Calendar var0, Date var1) {
      var0.setTime(var1);
   }

   public static void a(Calendar var0, int var1, int var2) {
      var0.add(var1, var2);
   }

   public static Date a(Calendar var0) {
      return var0.getTime();
   }

   public static int a(Calendar var0, int var1) {
      return var0.get(var1);
   }

   public static void a(Calendar var0, long var1) {
      var0.setTimeInMillis(var1);
   }
}
