/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Comparator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class IntPriorityQueues
/*     */ {
/*     */   public static class SynchronizedPriorityQueue
/*     */     implements IntPriorityQueue
/*     */   {
/*     */     protected final IntPriorityQueue q;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedPriorityQueue(IntPriorityQueue q, Object sync) {
/*  31 */       this.q = q;
/*  32 */       this.sync = sync;
/*     */     }
/*     */     protected SynchronizedPriorityQueue(IntPriorityQueue q) {
/*  35 */       this.q = q;
/*  36 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public void enqueue(int x) {
/*  40 */       synchronized (this.sync) {
/*  41 */         this.q.enqueue(x);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int dequeueInt() {
/*  46 */       synchronized (this.sync) {
/*  47 */         return this.q.dequeueInt();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int firstInt() {
/*  52 */       synchronized (this.sync) {
/*  53 */         return this.q.firstInt();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int lastInt() {
/*  58 */       synchronized (this.sync) {
/*  59 */         return this.q.lastInt();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/*  64 */       synchronized (this.sync) {
/*  65 */         return this.q.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int size() {
/*  70 */       synchronized (this.sync) {
/*  71 */         return this.q.size();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void clear() {
/*  76 */       synchronized (this.sync) {
/*  77 */         this.q.clear();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void changed() {
/*  82 */       synchronized (this.sync) {
/*  83 */         this.q.changed();
/*     */       } 
/*     */     }
/*     */     
/*     */     public IntComparator comparator() {
/*  88 */       synchronized (this.sync) {
/*  89 */         return this.q.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public void enqueue(Integer x) {
/*  95 */       synchronized (this.sync) {
/*  96 */         this.q.enqueue(x);
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public Integer dequeue() {
/* 102 */       synchronized (this.sync) {
/* 103 */         return this.q.dequeue();
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public Integer first() {
/* 109 */       synchronized (this.sync) {
/* 110 */         return this.q.first();
/*     */       } 
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public Integer last() {
/* 116 */       synchronized (this.sync) {
/* 117 */         return this.q.last();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 122 */       synchronized (this.sync) {
/* 123 */         return this.q.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 128 */       if (o == this)
/* 129 */         return true; 
/* 130 */       synchronized (this.sync) {
/* 131 */         return this.q.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 135 */       synchronized (this.sync) {
/* 136 */         s.defaultWriteObject();
/*     */       } 
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
/*     */   public static IntPriorityQueue synchronize(IntPriorityQueue q) {
/* 149 */     return new SynchronizedPriorityQueue(q);
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
/*     */   public static IntPriorityQueue synchronize(IntPriorityQueue q, Object sync) {
/* 163 */     return new SynchronizedPriorityQueue(q, sync);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntPriorityQueues.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */