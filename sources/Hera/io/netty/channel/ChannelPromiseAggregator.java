/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.concurrent.Future;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ChannelPromiseAggregator
/*    */   implements ChannelFutureListener
/*    */ {
/*    */   private final ChannelPromise aggregatePromise;
/*    */   private Set<ChannelPromise> pendingPromises;
/*    */   
/*    */   public ChannelPromiseAggregator(ChannelPromise aggregatePromise) {
/* 39 */     if (aggregatePromise == null) {
/* 40 */       throw new NullPointerException("aggregatePromise");
/*    */     }
/* 42 */     this.aggregatePromise = aggregatePromise;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChannelPromiseAggregator add(ChannelPromise... promises) {
/* 49 */     if (promises == null) {
/* 50 */       throw new NullPointerException("promises");
/*    */     }
/* 52 */     if (promises.length == 0) {
/* 53 */       return this;
/*    */     }
/* 55 */     synchronized (this) {
/* 56 */       if (this.pendingPromises == null) {
/*    */         int size;
/* 58 */         if (promises.length > 1) {
/* 59 */           size = promises.length;
/*    */         } else {
/* 61 */           size = 2;
/*    */         } 
/* 63 */         this.pendingPromises = new LinkedHashSet<ChannelPromise>(size);
/*    */       } 
/* 65 */       for (ChannelPromise p : promises) {
/* 66 */         if (p != null) {
/*    */ 
/*    */           
/* 69 */           this.pendingPromises.add(p);
/* 70 */           p.addListener(this);
/*    */         } 
/*    */       } 
/* 73 */     }  return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized void operationComplete(ChannelFuture future) throws Exception {
/* 78 */     if (this.pendingPromises == null) {
/* 79 */       this.aggregatePromise.setSuccess();
/*    */     } else {
/* 81 */       this.pendingPromises.remove(future);
/* 82 */       if (!future.isSuccess()) {
/* 83 */         this.aggregatePromise.setFailure(future.cause());
/* 84 */         for (ChannelPromise pendingFuture : this.pendingPromises) {
/* 85 */           pendingFuture.setFailure(future.cause());
/*    */         }
/*    */       }
/* 88 */       else if (this.pendingPromises.isEmpty()) {
/* 89 */         this.aggregatePromise.setSuccess();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\ChannelPromiseAggregator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */