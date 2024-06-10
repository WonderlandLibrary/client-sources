/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.SortedSet;
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
/*     */ public final class ObjectSortedSets
/*     */ {
/*     */   public static class EmptySet<K>
/*     */     extends ObjectSets.EmptySet<K>
/*     */     implements ObjectSortedSet<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/*  48 */       return ObjectIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> subSet(K from, K to) {
/*  53 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> headSet(K from) {
/*  58 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> tailSet(K to) {
/*  63 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public K first() {
/*  67 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public K last() {
/*  71 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/*  75 */       return null;
/*     */     }
/*     */     
/*     */     public Object clone() {
/*  79 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */     private Object readResolve() {
/*  82 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public static final EmptySet EMPTY_SET = new EmptySet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ObjectSet<K> emptySet() {
/* 101 */     return EMPTY_SET;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends ObjectSets.Singleton<K>
/*     */     implements ObjectSortedSet<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     final Comparator<? super K> comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(K element, Comparator<? super K> comparator) {
/* 118 */       super(element);
/* 119 */       this.comparator = comparator;
/*     */     }
/*     */     private Singleton(K element) {
/* 122 */       this(element, (Comparator<? super K>)null);
/*     */     }
/*     */     
/*     */     final int compare(K k1, K k2) {
/* 126 */       return (this.comparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 130 */       ObjectBidirectionalIterator<K> i = iterator();
/* 131 */       if (compare(this.element, from) <= 0)
/* 132 */         i.next(); 
/* 133 */       return i;
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 137 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 142 */       if (compare(from, this.element) <= 0 && compare(this.element, to) < 0)
/* 143 */         return this; 
/* 144 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> headSet(K to) {
/* 149 */       if (compare(this.element, to) < 0)
/* 150 */         return this; 
/* 151 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> tailSet(K from) {
/* 156 */       if (compare(from, this.element) <= 0)
/* 157 */         return this; 
/* 158 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public K first() {
/* 162 */       return this.element;
/*     */     }
/*     */     
/*     */     public K last() {
/* 166 */       return this.element;
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
/*     */   public static <K> ObjectSortedSet<K> singleton(K element) {
/* 178 */     return new Singleton<>(element);
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
/*     */   public static <K> ObjectSortedSet<K> singleton(K element, Comparator<? super K> comparator) {
/* 192 */     return new Singleton<>(element, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedSet<K>
/*     */     extends ObjectSets.SynchronizedSet<K>
/*     */     implements ObjectSortedSet<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ObjectSortedSet<K> sortedSet;
/*     */     
/*     */     protected SynchronizedSortedSet(ObjectSortedSet<K> s, Object sync) {
/* 202 */       super(s, sync);
/* 203 */       this.sortedSet = s;
/*     */     }
/*     */     protected SynchronizedSortedSet(ObjectSortedSet<K> s) {
/* 206 */       super(s);
/* 207 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 211 */       synchronized (this.sync) {
/* 212 */         return this.sortedSet.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 217 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> headSet(K to) {
/* 221 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> tailSet(K from) {
/* 225 */       return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/* 229 */       return this.sortedSet.iterator();
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 233 */       return this.sortedSet.iterator(from);
/*     */     }
/*     */     
/*     */     public K first() {
/* 237 */       synchronized (this.sync) {
/* 238 */         return this.sortedSet.first();
/*     */       } 
/*     */     }
/*     */     
/*     */     public K last() {
/* 243 */       synchronized (this.sync) {
/* 244 */         return this.sortedSet.last();
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
/*     */   
/*     */   public static <K> ObjectSortedSet<K> synchronize(ObjectSortedSet<K> s) {
/* 258 */     return new SynchronizedSortedSet<>(s);
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
/*     */   public static <K> ObjectSortedSet<K> synchronize(ObjectSortedSet<K> s, Object sync) {
/* 273 */     return new SynchronizedSortedSet<>(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedSet<K>
/*     */     extends ObjectSets.UnmodifiableSet<K>
/*     */     implements ObjectSortedSet<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ObjectSortedSet<K> sortedSet;
/*     */     
/*     */     protected UnmodifiableSortedSet(ObjectSortedSet<K> s) {
/* 283 */       super(s);
/* 284 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 288 */       return this.sortedSet.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 292 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> headSet(K to) {
/* 296 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<K> tailSet(K from) {
/* 300 */       return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/* 304 */       return ObjectIterators.unmodifiable(this.sortedSet.iterator());
/*     */     }
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 308 */       return ObjectIterators.unmodifiable(this.sortedSet.iterator(from));
/*     */     }
/*     */     
/*     */     public K first() {
/* 312 */       return this.sortedSet.first();
/*     */     }
/*     */     
/*     */     public K last() {
/* 316 */       return this.sortedSet.last();
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
/*     */   public static <K> ObjectSortedSet<K> unmodifiable(ObjectSortedSet<K> s) {
/* 329 */     return new UnmodifiableSortedSet<>(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectSortedSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */