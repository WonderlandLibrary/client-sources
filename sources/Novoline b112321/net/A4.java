package net;

import java.util.Timer;
import java.util.TimerTask;

public class A4 {
   public static void a(Timer var0, TimerTask var1, long var2) {
      var0.schedule(var1, var2);
   }

   public static void a(Timer var0, TimerTask var1, long var2, long var4) {
      var0.schedule(var1, var2, var4);
   }

   public static void a(Timer var0) {
      var0.cancel();
   }
}
