/*    */ package io.netty.util.concurrent;
/*    */ 
/*    */ import io.netty.util.internal.PlatformDependent;
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
/*    */ public final class FailedFuture<V>
/*    */   extends CompleteFuture<V>
/*    */ {
/*    */   private final Throwable cause;
/*    */   
/*    */   public FailedFuture(EventExecutor executor, Throwable cause) {
/* 36 */     super(executor);
/* 37 */     if (cause == null) {
/* 38 */       throw new NullPointerException("cause");
/*    */     }
/* 40 */     this.cause = cause;
/*    */   }
/*    */ 
/*    */   
/*    */   public Throwable cause() {
/* 45 */     return this.cause;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSuccess() {
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public Future<V> sync() {
/* 55 */     PlatformDependent.throwException(this.cause);
/* 56 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public Future<V> syncUninterruptibly() {
/* 61 */     PlatformDependent.throwException(this.cause);
/* 62 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public V getNow() {
/* 67 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\concurrent\FailedFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */