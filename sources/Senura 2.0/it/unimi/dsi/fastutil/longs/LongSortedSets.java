/*     */ package it.unimi.dsi.fastutil.longs;
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
/*     */ public final class LongSortedSets
/*     */ {
/*     */   public static class EmptySet
/*     */     extends LongSets.EmptySet
/*     */     implements LongSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public LongBidirectionalIterator iterator(long from) {
/*  43 */       return LongIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet subSet(long from, long to) {
/*  48 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet headSet(long from) {
/*  53 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet tailSet(long to) {
/*  58 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public long firstLong() {
/*  62 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public long lastLong() {
/*  66 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public LongComparator comparator() {
/*  70 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet subSet(Long from, Long to) {
/*  80 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet headSet(Long from) {
/*  90 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet tailSet(Long to) {
/* 100 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long first() {
/* 110 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long last() {
/* 120 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 124 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */     private Object readResolve() {
/* 127 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   public static final EmptySet EMPTY_SET = new EmptySet();
/*     */ 
/*     */   
/*     */   public static class Singleton
/*     */     extends LongSets.Singleton
/*     */     implements LongSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     final LongComparator comparator;
/*     */     
/*     */     protected Singleton(long element, LongComparator comparator) {
/* 147 */       super(element);
/* 148 */       this.comparator = comparator;
/*     */     }
/*     */     private Singleton(long element) {
/* 151 */       this(element, (LongComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(long k1, long k2) {
/* 155 */       return (this.comparator == null) ? Long.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public LongBidirectionalIterator iterator(long from) {
/* 159 */       LongBidirectionalIterator i = iterator();
/* 160 */       if (compare(this.element, from) <= 0)
/* 161 */         i.nextLong(); 
/* 162 */       return i;
/*     */     }
/*     */     
/*     */     public LongComparator comparator() {
/* 166 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet subSet(long from, long to) {
/* 171 */       if (compare(from, this.element) <= 0 && compare(this.element, to) < 0)
/* 172 */         return this; 
/* 173 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet headSet(long to) {
/* 178 */       if (compare(this.element, to) < 0)
/* 179 */         return this; 
/* 180 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet tailSet(long from) {
/* 185 */       if (compare(from, this.element) <= 0)
/* 186 */         return this; 
/* 187 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public long firstLong() {
/* 191 */       return this.element;
/*     */     }
/*     */     
/*     */     public long lastLong() {
/* 195 */       return this.element;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet subSet(Long from, Long to) {
/* 205 */       return subSet(from.longValue(), to.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet headSet(Long to) {
/* 215 */       return headSet(to.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet tailSet(Long from) {
/* 225 */       return tailSet(from.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long first() {
/* 235 */       return Long.valueOf(this.element);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long last() {
/* 245 */       return Long.valueOf(this.element);
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
/*     */   public static LongSortedSet singleton(long element) {
/* 257 */     return new Singleton(element);
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
/*     */   public static LongSortedSet singleton(long element, LongComparator comparator) {
/* 271 */     return new Singleton(element, comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LongSortedSet singleton(Object element) {
/* 282 */     return new Singleton(((Long)element).longValue());
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
/*     */   public static LongSortedSet singleton(Object element, LongComparator comparator) {
/* 296 */     return new Singleton(((Long)element).longValue(), comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedSet
/*     */     extends LongSets.SynchronizedSet
/*     */     implements LongSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final LongSortedSet sortedSet;
/*     */     
/*     */     protected SynchronizedSortedSet(LongSortedSet s, Object sync) {
/* 306 */       super(s, sync);
/* 307 */       this.sortedSet = s;
/*     */     }
/*     */     protected SynchronizedSortedSet(LongSortedSet s) {
/* 310 */       super(s);
/* 311 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     public LongComparator comparator() {
/* 315 */       synchronized (this.sync) {
/* 316 */         return this.sortedSet.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public LongSortedSet subSet(long from, long to) {
/* 321 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public LongSortedSet headSet(long to) {
/* 325 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */     
/*     */     public LongSortedSet tailSet(long from) {
/* 329 */       return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
/*     */     }
/*     */     
/*     */     public LongBidirectionalIterator iterator() {
/* 333 */       return this.sortedSet.iterator();
/*     */     }
/*     */     
/*     */     public LongBidirectionalIterator iterator(long from) {
/* 337 */       return this.sortedSet.iterator(from);
/*     */     }
/*     */     
/*     */     public long firstLong() {
/* 341 */       synchronized (this.sync) {
/* 342 */         return this.sortedSet.firstLong();
/*     */       } 
/*     */     }
/*     */     
/*     */     public long lastLong() {
/* 347 */       synchronized (this.sync) {
/* 348 */         return this.sortedSet.lastLong();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long first() {
/* 359 */       synchronized (this.sync) {
/* 360 */         return this.sortedSet.first();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long last() {
/* 371 */       synchronized (this.sync) {
/* 372 */         return this.sortedSet.last();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet subSet(Long from, Long to) {
/* 383 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet headSet(Long to) {
/* 393 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet tailSet(Long from) {
/* 403 */       return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
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
/*     */   public static LongSortedSet synchronize(LongSortedSet s) {
/* 416 */     return new SynchronizedSortedSet(s);
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
/*     */   public static LongSortedSet synchronize(LongSortedSet s, Object sync) {
/* 431 */     return new SynchronizedSortedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedSet
/*     */     extends LongSets.UnmodifiableSet
/*     */     implements LongSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final LongSortedSet sortedSet;
/*     */     
/*     */     protected UnmodifiableSortedSet(LongSortedSet s) {
/* 441 */       super(s);
/* 442 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     public LongComparator comparator() {
/* 446 */       return this.sortedSet.comparator();
/*     */     }
/*     */     
/*     */     public LongSortedSet subSet(long from, long to) {
/* 450 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */     
/*     */     public LongSortedSet headSet(long to) {
/* 454 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */     
/*     */     public LongSortedSet tailSet(long from) {
/* 458 */       return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
/*     */     }
/*     */     
/*     */     public LongBidirectionalIterator iterator() {
/* 462 */       return LongIterators.unmodifiable(this.sortedSet.iterator());
/*     */     }
/*     */     
/*     */     public LongBidirectionalIterator iterator(long from) {
/* 466 */       return LongIterators.unmodifiable(this.sortedSet.iterator(from));
/*     */     }
/*     */     
/*     */     public long firstLong() {
/* 470 */       return this.sortedSet.firstLong();
/*     */     }
/*     */     
/*     */     public long lastLong() {
/* 474 */       return this.sortedSet.lastLong();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long first() {
/* 484 */       return this.sortedSet.first();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long last() {
/* 494 */       return this.sortedSet.last();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet subSet(Long from, Long to) {
/* 504 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet headSet(Long to) {
/* 514 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet tailSet(Long from) {
/* 524 */       return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
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
/*     */   public static LongSortedSet unmodifiable(LongSortedSet s) {
/* 537 */     return new UnmodifiableSortedSet(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongSortedSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */