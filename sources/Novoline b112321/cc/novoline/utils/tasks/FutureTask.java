package cc.novoline.utils.tasks;

import net.acE;

public abstract class FutureTask {
   private final int delay;
   private long lastTime;
   private static String[] b;

   protected FutureTask(int var1) {
      this.delay = var1;
      c();
      this.lastTime = System.nanoTime() / 1000000L;
      if(acE.b() == null) {
         b(new String[2]);
      }

   }

   public final boolean delay() {
      String[] var1 = c();
      return System.nanoTime() / 1000000L - this.lastTime >= (long)this.delay;
   }

   public abstract void execute();

   public abstract void run();

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] c() {
      return b;
   }

   static {
      if(c() == null) {
         b(new String[4]);
      }

   }
}
