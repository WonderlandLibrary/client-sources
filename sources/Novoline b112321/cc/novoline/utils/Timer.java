package cc.novoline.utils;

import net.minecraft.util.MathHelper;

public final class Timer {
   private long lastMS = 0L;
   private long previousTime = -1L;
   private static String[] b;

   public boolean sleep(long var1) {
      String[] var3 = e();
      if(this.time() >= var1) {
         this.reset();
         return true;
      } else {
         return false;
      }
   }

   public boolean check(float var1) {
      String[] var2 = e();
      return (float)(System.currentTimeMillis() - this.previousTime) >= var1;
   }

   public boolean delay(double var1) {
      String[] var3 = e();
      return (double)MathHelper.clamp_float((float)(this.getCurrentMS() - this.lastMS), 0.0F, (float)var1) >= var1;
   }

   public void reset() {
      this.previousTime = System.currentTimeMillis();
      this.lastMS = this.getCurrentMS();
   }

   public long time() {
      return System.nanoTime() / 1000000L - this.lastMS;
   }

   public long getCurrentMS() {
      return System.nanoTime() / 1000000L;
   }

   public double getLastDelay() {
      return (double)(this.getCurrentMS() - this.getLastMS());
   }

   public long getLastMS() {
      return this.lastMS;
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] e() {
      return b;
   }

   static {
      if(e() != null) {
         b(new String[4]);
      }

   }
}
