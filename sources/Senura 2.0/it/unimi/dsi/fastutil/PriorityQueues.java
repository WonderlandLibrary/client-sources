/*     */ package it.unimi.dsi.fastutil;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import java.util.NoSuchElementException;
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
/*     */ public class PriorityQueues
/*     */ {
/*     */   public static class EmptyPriorityQueue
/*     */     implements PriorityQueue, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     public void enqueue(Object o) {
/*  46 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     public Object dequeue() {
/*  49 */       throw new NoSuchElementException();
/*     */     }
/*     */     public boolean isEmpty() {
/*  52 */       return true;
/*     */     }
/*     */     public int size() {
/*  55 */       return 0;
/*     */     }
/*     */     
/*     */     public void clear() {}
/*     */     
/*     */     public Object first() {
/*  61 */       throw new NoSuchElementException();
/*     */     }
/*     */     public Object last() {
/*  64 */       throw new NoSuchElementException();
/*     */     }
/*     */     public void changed() {
/*  67 */       throw new NoSuchElementException();
/*     */     }
/*     */     public Comparator<?> comparator() {
/*  70 */       return null;
/*     */     }
/*     */     public Object clone() {
/*  73 */       return PriorityQueues.EMPTY_QUEUE;
/*     */     }
/*     */     public int hashCode() {
/*  76 */       return 0;
/*     */     }
/*     */     public boolean equals(Object o) {
/*  79 */       return (o instanceof PriorityQueue && ((PriorityQueue)o).isEmpty());
/*     */     } private Object readResolve() {
/*  81 */       return PriorityQueues.EMPTY_QUEUE;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  86 */   public static final EmptyPriorityQueue EMPTY_QUEUE = new EmptyPriorityQueue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> PriorityQueue<K> emptyQueue() {
/*  96 */     return EMPTY_QUEUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SynchronizedPriorityQueue<K>
/*     */     implements PriorityQueue<K>, Serializable
/*     */   {
/*     */     public static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final PriorityQueue<K> q;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedPriorityQueue(PriorityQueue<K> q, Object sync) {
/* 108 */       this.q = q;
/* 109 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedPriorityQueue(PriorityQueue<K> q) {
/* 113 */       this.q = q;
/* 114 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public void enqueue(K x) {
/* 118 */       synchronized (this.sync) { this.q.enqueue(x); }
/*     */     
/*     */     } public K dequeue() {
/* 121 */       synchronized (this.sync) { return this.q.dequeue(); }
/*     */     
/*     */     } public K first() {
/* 124 */       synchronized (this.sync) { return this.q.first(); }
/*     */     
/*     */     } public K last() {
/* 127 */       synchronized (this.sync) { return this.q.last(); }
/*     */     
/*     */     } public boolean isEmpty() {
/* 130 */       synchronized (this.sync) { return this.q.isEmpty(); }
/*     */     
/*     */     } public int size() {
/* 133 */       synchronized (this.sync) { return this.q.size(); }
/*     */     
/*     */     } public void clear() {
/* 136 */       synchronized (this.sync) { this.q.clear(); }
/*     */     
/*     */     } public void changed() {
/* 139 */       synchronized (this.sync) { this.q.changed(); }
/*     */     
/*     */     } public Comparator<? super K> comparator() {
/* 142 */       synchronized (this.sync) { return this.q.comparator(); }
/*     */     
/*     */     } public String toString() {
/* 145 */       synchronized (this.sync) { return this.q.toString(); }
/*     */     
/*     */     } public int hashCode() {
/* 148 */       synchronized (this.sync) { return this.q.hashCode(); }
/*     */     
/*     */     } public boolean equals(Object o) {
/* 151 */       if (o == this) return true;  synchronized (this.sync) { return this.q.equals(o); }
/*     */     
/*     */     } private void writeObject(ObjectOutputStream s) throws IOException {
/* 154 */       synchronized (this.sync) { s.defaultWriteObject(); }
/*     */     
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> PriorityQueue<K> synchronize(PriorityQueue<K> q) {
/* 165 */     return new SynchronizedPriorityQueue<>(q);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> PriorityQueue<K> synchronize(PriorityQueue<K> q, Object sync) {
/* 175 */     return new SynchronizedPriorityQueue<>(q, sync);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\PriorityQueues.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */