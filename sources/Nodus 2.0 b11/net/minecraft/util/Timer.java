/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.Minecraft;
/*   4:    */ 
/*   5:    */ public class Timer
/*   6:    */ {
/*   7:    */   float ticksPerSecond;
/*   8:    */   private double lastHRTime;
/*   9:    */   public int elapsedTicks;
/*  10:    */   public float renderPartialTicks;
/*  11: 30 */   public float timerSpeed = 1.0F;
/*  12:    */   public float elapsedPartialTicks;
/*  13:    */   private long lastSyncSysClock;
/*  14:    */   private long lastSyncHRClock;
/*  15:    */   private long field_74285_i;
/*  16: 51 */   private double timeSyncAdjustment = 1.0D;
/*  17:    */   private static final String __OBFID = "CL_00000658";
/*  18:    */   
/*  19:    */   public Timer(float par1)
/*  20:    */   {
/*  21: 56 */     this.ticksPerSecond = par1;
/*  22: 57 */     this.lastSyncSysClock = Minecraft.getSystemTime();
/*  23: 58 */     this.lastSyncHRClock = (System.nanoTime() / 1000000L);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void updateTimer()
/*  27:    */   {
/*  28: 66 */     long var1 = Minecraft.getSystemTime();
/*  29: 67 */     long var3 = var1 - this.lastSyncSysClock;
/*  30: 68 */     long var5 = System.nanoTime() / 1000000L;
/*  31: 69 */     double var7 = var5 / 1000.0D;
/*  32: 71 */     if ((var3 <= 1000L) && (var3 >= 0L))
/*  33:    */     {
/*  34: 73 */       this.field_74285_i += var3;
/*  35: 75 */       if (this.field_74285_i > 1000L)
/*  36:    */       {
/*  37: 77 */         long var9 = var5 - this.lastSyncHRClock;
/*  38: 78 */         double var11 = this.field_74285_i / var9;
/*  39: 79 */         this.timeSyncAdjustment += (var11 - this.timeSyncAdjustment) * 0.2000000029802322D;
/*  40: 80 */         this.lastSyncHRClock = var5;
/*  41: 81 */         this.field_74285_i = 0L;
/*  42:    */       }
/*  43: 84 */       if (this.field_74285_i < 0L) {
/*  44: 86 */         this.lastSyncHRClock = var5;
/*  45:    */       }
/*  46:    */     }
/*  47:    */     else
/*  48:    */     {
/*  49: 91 */       this.lastHRTime = var7;
/*  50:    */     }
/*  51: 94 */     this.lastSyncSysClock = var1;
/*  52: 95 */     double var13 = (var7 - this.lastHRTime) * this.timeSyncAdjustment;
/*  53: 96 */     this.lastHRTime = var7;
/*  54: 98 */     if (var13 < 0.0D) {
/*  55:100 */       var13 = 0.0D;
/*  56:    */     }
/*  57:103 */     if (var13 > 1.0D) {
/*  58:105 */       var13 = 1.0D;
/*  59:    */     }
/*  60:108 */     this.elapsedPartialTicks = ((float)(this.elapsedPartialTicks + var13 * this.timerSpeed * this.ticksPerSecond));
/*  61:109 */     this.elapsedTicks = ((int)this.elapsedPartialTicks);
/*  62:110 */     this.elapsedPartialTicks -= this.elapsedTicks;
/*  63:112 */     if (this.elapsedTicks > 10) {
/*  64:114 */       this.elapsedTicks = 10;
/*  65:    */     }
/*  66:117 */     this.renderPartialTicks = this.elapsedPartialTicks;
/*  67:    */   }
/*  68:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.Timer
 * JD-Core Version:    0.7.0.1
 */