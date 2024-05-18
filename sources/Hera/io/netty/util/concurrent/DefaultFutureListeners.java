/*    */ package io.netty.util.concurrent;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ final class DefaultFutureListeners
/*    */ {
/*    */   private GenericFutureListener<? extends Future<?>>[] listeners;
/*    */   private int size;
/*    */   private int progressiveSize;
/*    */   
/*    */   DefaultFutureListeners(GenericFutureListener<? extends Future<?>> first, GenericFutureListener<? extends Future<?>> second) {
/* 29 */     this.listeners = (GenericFutureListener<? extends Future<?>>[])new GenericFutureListener[2];
/* 30 */     this.listeners[0] = first;
/* 31 */     this.listeners[1] = second;
/* 32 */     this.size = 2;
/* 33 */     if (first instanceof GenericProgressiveFutureListener) {
/* 34 */       this.progressiveSize++;
/*    */     }
/* 36 */     if (second instanceof GenericProgressiveFutureListener)
/* 37 */       this.progressiveSize++; 
/*    */   }
/*    */   
/*    */   public void add(GenericFutureListener<? extends Future<?>> l) {
/*    */     GenericFutureListener[] arrayOfGenericFutureListener;
/* 42 */     GenericFutureListener<? extends Future<?>>[] listeners = this.listeners;
/* 43 */     int size = this.size;
/* 44 */     if (size == listeners.length) {
/* 45 */       this.listeners = (GenericFutureListener<? extends Future<?>>[])(arrayOfGenericFutureListener = Arrays.copyOf((GenericFutureListener[])listeners, size << 1));
/*    */     }
/* 47 */     arrayOfGenericFutureListener[size] = l;
/* 48 */     this.size = size + 1;
/*    */     
/* 50 */     if (l instanceof GenericProgressiveFutureListener) {
/* 51 */       this.progressiveSize++;
/*    */     }
/*    */   }
/*    */   
/*    */   public void remove(GenericFutureListener<? extends Future<?>> l) {
/* 56 */     GenericFutureListener<? extends Future<?>>[] listeners = this.listeners;
/* 57 */     int size = this.size;
/* 58 */     for (int i = 0; i < size; i++) {
/* 59 */       if (listeners[i] == l) {
/* 60 */         int listenersToMove = size - i - 1;
/* 61 */         if (listenersToMove > 0) {
/* 62 */           System.arraycopy(listeners, i + 1, listeners, i, listenersToMove);
/*    */         }
/* 64 */         listeners[--size] = null;
/* 65 */         this.size = size;
/*    */         
/* 67 */         if (l instanceof GenericProgressiveFutureListener) {
/* 68 */           this.progressiveSize--;
/*    */         }
/*    */         return;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public GenericFutureListener<? extends Future<?>>[] listeners() {
/* 76 */     return this.listeners;
/*    */   }
/*    */   
/*    */   public int size() {
/* 80 */     return this.size;
/*    */   }
/*    */   
/*    */   public int progressiveSize() {
/* 84 */     return this.progressiveSize;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\concurrent\DefaultFutureListeners.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */