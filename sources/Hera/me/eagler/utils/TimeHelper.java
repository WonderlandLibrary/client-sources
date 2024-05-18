/*    */ package me.eagler.utils;
/*    */ 
/*    */ public class TimeHelper {
/*  4 */   private long lastMS = 0L;
/*    */   
/*    */   public boolean isDelayComplete(long delay) {
/*  7 */     if (System.currentTimeMillis() - this.lastMS >= delay)
/*  8 */       return true; 
/*  9 */     return false;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 13 */     this.lastMS = getCurrentMS();
/*    */   }
/*    */   
/*    */   public long getCurrentMS() {
/* 17 */     return System.nanoTime() / 1000000L;
/*    */   }
/*    */   
/*    */   public void setLastMS(long lastMS) {
/* 21 */     this.lastMS = lastMS;
/*    */   }
/*    */   
/*    */   public boolean hasReached(long milliseconds) {
/* 25 */     return (getCurrentMS() - this.lastMS >= milliseconds);
/*    */   }
/*    */   
/*    */   public boolean hasReachedfloat(float timeLeft) {
/* 29 */     return ((float)(getCurrentMS() - this.lastMS) >= timeLeft);
/*    */   }
/*    */   
/*    */   public void setLastMS() {
/* 33 */     this.lastMS = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public int convertToMS(int perSecond) {
/* 37 */     return 1000 / perSecond;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagle\\utils\TimeHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */