/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.concurrent.Future;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ChannelPromiseNotifier
/*    */   implements ChannelFutureListener
/*    */ {
/*    */   private final ChannelPromise[] promises;
/*    */   
/*    */   public ChannelPromiseNotifier(ChannelPromise... promises) {
/* 31 */     if (promises == null) {
/* 32 */       throw new NullPointerException("promises");
/*    */     }
/* 34 */     for (ChannelPromise promise : promises) {
/* 35 */       if (promise == null) {
/* 36 */         throw new IllegalArgumentException("promises contains null ChannelPromise");
/*    */       }
/*    */     } 
/* 39 */     this.promises = (ChannelPromise[])promises.clone();
/*    */   }
/*    */ 
/*    */   
/*    */   public void operationComplete(ChannelFuture cf) throws Exception {
/* 44 */     if (cf.isSuccess()) {
/* 45 */       for (ChannelPromise p : this.promises) {
/* 46 */         p.setSuccess();
/*    */       }
/*    */       
/*    */       return;
/*    */     } 
/* 51 */     Throwable cause = cf.cause();
/* 52 */     for (ChannelPromise p : this.promises)
/* 53 */       p.setFailure(cause); 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\ChannelPromiseNotifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */