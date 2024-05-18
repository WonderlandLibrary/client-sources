/*    */ package io.netty.util.concurrent;
/*    */ 
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.concurrent.TimeoutException;
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
/*    */ public abstract class AbstractFuture<V>
/*    */   implements Future<V>
/*    */ {
/*    */   public V get() throws InterruptedException, ExecutionException {
/* 31 */     await();
/*    */     
/* 33 */     Throwable cause = cause();
/* 34 */     if (cause == null) {
/* 35 */       return getNow();
/*    */     }
/* 37 */     throw new ExecutionException(cause);
/*    */   }
/*    */ 
/*    */   
/*    */   public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/* 42 */     if (await(timeout, unit)) {
/* 43 */       Throwable cause = cause();
/* 44 */       if (cause == null) {
/* 45 */         return getNow();
/*    */       }
/* 47 */       throw new ExecutionException(cause);
/*    */     } 
/* 49 */     throw new TimeoutException();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\concurrent\AbstractFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */