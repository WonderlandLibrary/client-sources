package net;

import cc.novoline.utils.tasks.FutureTask;
import cc.novoline.utils.tasks.TaskManager;
import java.util.List;

public class h2 {
   private static int b;

   public static List a(TaskManager var0) {
      return var0.getFutureTasks();
   }

   public static void a(TaskManager var0, FutureTask var1) {
      var0.queue(var1);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 20;
   }

   static {
      if(a() == 0) {
         b(43);
      }

   }
}
