package com.example.editme.util.client;

public class Timer {
   private long time = -1L;

   public void reset() {
      this.time = System.currentTimeMillis();
   }

   public void setTime(long var1) {
      this.time = var1;
   }

   public long getTime() {
      return this.time;
   }

   public void resetTimeSkipTo(long var1) {
      this.time = System.currentTimeMillis() + var1;
   }

   public boolean passed(double var1) {
      return (double)(System.currentTimeMillis() - this.time) >= var1;
   }
}
