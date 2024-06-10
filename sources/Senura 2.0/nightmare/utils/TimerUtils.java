/*    */ package nightmare.utils;
/*    */ 
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimerUtils
/*    */ {
/* 11 */   private long lastMS = 0L;
/* 12 */   private long previousTime = -1L;
/*    */ 
/*    */   
/*    */   public boolean sleep(long time) {
/* 16 */     if (time() >= time) {
/* 17 */       reset();
/* 18 */       return true;
/*    */     } 
/*    */     
/* 21 */     return false;
/*    */   }
/*    */   
/*    */   public boolean check(float milliseconds) {
/* 25 */     return ((float)(System.currentTimeMillis() - this.previousTime) >= milliseconds);
/*    */   }
/*    */   
/*    */   public boolean delay(double milliseconds) {
/* 29 */     return (MathHelper.func_76131_a((float)(getCurrentMS() - this.lastMS), 0.0F, (float)milliseconds) >= milliseconds);
/*    */   }
/*    */   
/*    */   public void reset() {
/* 33 */     this.previousTime = System.currentTimeMillis();
/* 34 */     this.lastMS = getCurrentMS();
/*    */   }
/*    */   
/*    */   public long time() {
/* 38 */     return System.nanoTime() / 1000000L - this.lastMS;
/*    */   }
/*    */   
/*    */   public long getCurrentMS() {
/* 42 */     return System.nanoTime() / 1000000L;
/*    */   }
/*    */   
/*    */   public double getLastDelay() {
/* 46 */     return (getCurrentMS() - getLastMS());
/*    */   }
/*    */   
/*    */   public boolean hasReached(double d) {
/* 50 */     return ((getCurrentMS() - this.lastMS) >= d);
/*    */   }
/*    */   
/*    */   public long getLastMS() {
/* 54 */     return this.lastMS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmar\\utils\TimerUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */