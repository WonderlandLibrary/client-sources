/*     */ package io.netty.channel.rxtx;
/*     */ 
/*     */ public interface RxtxChannelConfig extends ChannelConfig {
/*     */   RxtxChannelConfig setBaudrate(int paramInt);
/*     */   
/*     */   RxtxChannelConfig setStopbits(Stopbits paramStopbits);
/*     */   
/*     */   RxtxChannelConfig setDatabits(Databits paramDatabits);
/*     */   
/*     */   RxtxChannelConfig setParitybit(Paritybit paramParitybit);
/*     */   
/*     */   int getBaudrate();
/*     */   
/*     */   Stopbits getStopbits();
/*     */   
/*     */   Databits getDatabits();
/*     */   
/*     */   Paritybit getParitybit();
/*     */   
/*     */   boolean isDtr();
/*     */   
/*     */   RxtxChannelConfig setDtr(boolean paramBoolean);
/*     */   
/*     */   boolean isRts();
/*     */   
/*     */   RxtxChannelConfig setRts(boolean paramBoolean);
/*     */   
/*     */   int getWaitTimeMillis();
/*     */   
/*     */   RxtxChannelConfig setWaitTimeMillis(int paramInt);
/*     */   
/*     */   RxtxChannelConfig setReadTimeout(int paramInt);
/*     */   
/*     */   int getReadTimeout();
/*     */   
/*     */   RxtxChannelConfig setConnectTimeoutMillis(int paramInt);
/*     */   
/*     */   RxtxChannelConfig setMaxMessagesPerRead(int paramInt);
/*     */   
/*     */   RxtxChannelConfig setWriteSpinCount(int paramInt);
/*     */   
/*     */   RxtxChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator);
/*     */   
/*     */   RxtxChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator);
/*     */   
/*     */   RxtxChannelConfig setAutoRead(boolean paramBoolean);
/*     */   
/*     */   RxtxChannelConfig setAutoClose(boolean paramBoolean);
/*     */   
/*     */   RxtxChannelConfig setWriteBufferHighWaterMark(int paramInt);
/*     */   
/*     */   RxtxChannelConfig setWriteBufferLowWaterMark(int paramInt);
/*     */   
/*     */   RxtxChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator);
/*     */   
/*     */   public enum Stopbits {
/*  57 */     STOPBITS_1(1),
/*     */ 
/*     */ 
/*     */     
/*  61 */     STOPBITS_2(2),
/*     */ 
/*     */ 
/*     */     
/*  65 */     STOPBITS_1_5(3);
/*     */     
/*     */     private final int value;
/*     */     
/*     */     Stopbits(int value) {
/*  70 */       this.value = value;
/*     */     }
/*     */     
/*     */     public int value() {
/*  74 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Databits
/*     */   {
/*  91 */     DATABITS_5(5),
/*     */ 
/*     */ 
/*     */     
/*  95 */     DATABITS_6(6),
/*     */ 
/*     */ 
/*     */     
/*  99 */     DATABITS_7(7),
/*     */ 
/*     */ 
/*     */     
/* 103 */     DATABITS_8(8);
/*     */     
/*     */     private final int value;
/*     */     
/*     */     Databits(int value) {
/* 108 */       this.value = value;
/*     */     }
/*     */     
/*     */     public int value() {
/* 112 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Paritybit
/*     */   {
/* 129 */     NONE(0),
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     ODD(1),
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     EVEN(2),
/*     */ 
/*     */ 
/*     */     
/* 143 */     MARK(3),
/*     */ 
/*     */ 
/*     */     
/* 147 */     SPACE(4);
/*     */     
/*     */     private final int value;
/*     */     
/*     */     Paritybit(int value) {
/* 152 */       this.value = value;
/*     */     }
/*     */     
/*     */     public int value() {
/* 156 */       return this.value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\rxtx\RxtxChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */