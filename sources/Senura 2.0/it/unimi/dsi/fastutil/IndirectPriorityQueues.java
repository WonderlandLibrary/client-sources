/*     */ package it.unimi.dsi.fastutil;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IndirectPriorityQueues
/*     */ {
/*     */   public static class EmptyIndirectPriorityQueue
/*     */     implements IndirectPriorityQueue
/*     */   {
/*     */     public void enqueue(int i) {
/*  44 */       throw new UnsupportedOperationException();
/*     */     } public int dequeue() {
/*  46 */       throw new NoSuchElementException();
/*     */     } public boolean isEmpty() {
/*  48 */       return true;
/*     */     } public int size() {
/*  50 */       return 0;
/*     */     } public boolean contains(int index) {
/*  52 */       return false;
/*     */     }
/*     */     public void clear() {}
/*     */     public int first() {
/*  56 */       throw new NoSuchElementException();
/*     */     } public int last() {
/*  58 */       throw new NoSuchElementException();
/*     */     } public void changed() {
/*  60 */       throw new NoSuchElementException();
/*     */     }
/*     */     public void allChanged() {}
/*     */     public Comparator<?> comparator() {
/*  64 */       return null;
/*     */     } public void changed(int i) {
/*  66 */       throw new IllegalArgumentException("Index " + i + " is not in the queue");
/*     */     } public boolean remove(int i) {
/*  68 */       return false;
/*     */     } public int front(int[] a) {
/*  70 */       return 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public static final EmptyIndirectPriorityQueue EMPTY_QUEUE = new EmptyIndirectPriorityQueue();
/*     */ 
/*     */   
/*     */   public static class SynchronizedIndirectPriorityQueue<K>
/*     */     implements IndirectPriorityQueue<K>
/*     */   {
/*     */     public static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final IndirectPriorityQueue<K> q;
/*     */     
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedIndirectPriorityQueue(IndirectPriorityQueue<K> q, Object sync) {
/*  90 */       this.q = q;
/*  91 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedIndirectPriorityQueue(IndirectPriorityQueue<K> q) {
/*  95 */       this.q = q;
/*  96 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public void enqueue(int x) {
/* 100 */       synchronized (this.sync) { this.q.enqueue(x); }
/*     */     
/* 102 */     } public int dequeue() { synchronized (this.sync) { return this.q.dequeue(); }
/*     */        }
/* 104 */     public boolean contains(int index) { synchronized (this.sync) { return this.q.contains(index); }
/*     */        }
/* 106 */     public int first() { synchronized (this.sync) { return this.q.first(); }
/*     */        }
/* 108 */     public int last() { synchronized (this.sync) { return this.q.last(); }
/*     */        }
/* 110 */     public boolean isEmpty() { synchronized (this.sync) { return this.q.isEmpty(); }
/*     */        }
/* 112 */     public int size() { synchronized (this.sync) { return this.q.size(); }
/*     */        }
/* 114 */     public void clear() { synchronized (this.sync) { this.q.clear(); }
/*     */        }
/* 116 */     public void changed() { synchronized (this.sync) { this.q.changed(); }
/*     */        }
/* 118 */     public void allChanged() { synchronized (this.sync) { this.q.allChanged(); }
/*     */        }
/* 120 */     public void changed(int i) { synchronized (this.sync) { this.q.changed(i); }
/*     */        }
/* 122 */     public boolean remove(int i) { synchronized (this.sync) { return this.q.remove(i); }
/*     */        }
/* 124 */     public Comparator<? super K> comparator() { synchronized (this.sync) { return this.q.comparator(); }
/*     */        } public int front(int[] a) {
/* 126 */       return this.q.front(a);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> IndirectPriorityQueue<K> synchronize(IndirectPriorityQueue<K> q) {
/* 135 */     return new SynchronizedIndirectPriorityQueue<>(q);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> IndirectPriorityQueue<K> synchronize(IndirectPriorityQueue<K> q, Object sync) {
/* 144 */     return new SynchronizedIndirectPriorityQueue<>(q, sync);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\IndirectPriorityQueues.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */