/*    */ package io.netty.channel.group;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.NoSuchElementException;
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
/*    */ final class CombinedIterator<E>
/*    */   implements Iterator<E>
/*    */ {
/*    */   private final Iterator<E> i1;
/*    */   private final Iterator<E> i2;
/*    */   private Iterator<E> currentIterator;
/*    */   
/*    */   CombinedIterator(Iterator<E> i1, Iterator<E> i2) {
/* 30 */     if (i1 == null) {
/* 31 */       throw new NullPointerException("i1");
/*    */     }
/* 33 */     if (i2 == null) {
/* 34 */       throw new NullPointerException("i2");
/*    */     }
/* 36 */     this.i1 = i1;
/* 37 */     this.i2 = i2;
/* 38 */     this.currentIterator = i1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/*    */     while (true) {
/* 44 */       if (this.currentIterator.hasNext()) {
/* 45 */         return true;
/*    */       }
/*    */       
/* 48 */       if (this.currentIterator == this.i1) {
/* 49 */         this.currentIterator = this.i2; continue;
/*    */       }  break;
/* 51 */     }  return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public E next() {
/*    */     while (true) {
/*    */       try {
/* 60 */         return this.currentIterator.next();
/* 61 */       } catch (NoSuchElementException e) {
/* 62 */         if (this.currentIterator == this.i1)
/* 63 */         { this.currentIterator = this.i2; continue; }  break;
/*    */       } 
/* 65 */     }  throw e;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void remove() {
/* 73 */     this.currentIterator.remove();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\group\CombinedIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */