/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public final class IntSortedSets
/*     */ {
/*     */   public static class EmptySet
/*     */     extends IntSets.EmptySet
/*     */     implements IntSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public IntBidirectionalIterator iterator(int from) {
/*  43 */       return IntIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet subSet(int from, int to) {
/*  48 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet headSet(int from) {
/*  53 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet tailSet(int to) {
/*  58 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public int firstInt() {
/*  62 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public int lastInt() {
/*  66 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public IntComparator comparator() {
/*  70 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public IntSortedSet subSet(Integer from, Integer to) {
/*  80 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public IntSortedSet headSet(Integer from) {
/*  90 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public IntSortedSet tailSet(Integer to) {
/* 100 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer first() {
/* 110 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer last() {
/* 120 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 124 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */     private Object readResolve() {
/* 127 */       return IntSortedSets.EMPTY_SET;
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
/*     */     extends IntSets.Singleton
/*     */     implements IntSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     final IntComparator comparator;
/*     */     
/*     */     protected Singleton(int element, IntComparator comparator) {
/* 147 */       super(element);
/* 148 */       this.comparator = comparator;
/*     */     }
/*     */     private Singleton(int element) {
/* 151 */       this(element, (IntComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(int k1, int k2) {
/* 155 */       return (this.comparator == null) ? Integer.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public IntBidirectionalIterator iterator(int from) {
/* 159 */       IntBidirectionalIterator i = iterator();
/* 160 */       if (compare(this.element, from) <= 0)
/* 161 */         i.nextInt(); 
/* 162 */       return i;
/*     */     }
/*     */     
/*     */     public IntComparator comparator() {
/* 166 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet subSet(int from, int to) {
/* 171 */       if (compare(from, this.element) <= 0 && compare(this.element, to) < 0)
/* 172 */         return this; 
/* 173 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet headSet(int to) {
/* 178 */       if (compare(this.element, to) < 0)
/* 179 */         return this; 
/* 180 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet tailSet(int from) {
/* 185 */       if (compare(from, this.element) <= 0)
/* 186 */         return this; 
/* 187 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public int firstInt() {
/* 191 */       return this.element;
/*     */     }
/*     */     
/*     */     public int lastInt() {
/* 195 */       return this.element;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public IntSortedSet subSet(Integer from, Integer to) {
/* 205 */       return subSet(from.intValue(), to.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public IntSortedSet headSet(Integer to) {
/* 215 */       return headSet(to.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public IntSortedSet tailSet(Integer from) {
/* 225 */       return tailSet(from.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer first() {
/* 235 */       return Integer.valueOf(this.element);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer last() {
/* 245 */       return Integer.valueOf(this.element);
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
/*     */   public static IntSortedSet singleton(int element) {
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
/*     */   public static IntSortedSet singleton(int element, IntComparator comparator) {
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
/*     */   public static IntSortedSet singleton(Object element) {
/* 282 */     return new Singleton(((Integer)element).intValue());
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
/*     */   public static IntSortedSet singleton(Object element, IntComparator comparator) {
/* 296 */     return new Singleton(((Integer)element).intValue(), comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedSet
/*     */     extends IntSets.SynchronizedSet
/*     */     implements IntSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final IntSortedSet sortedSet;
/*     */     
/*     */     protected SynchronizedSortedSet(IntSortedSet s, Object sync) {
/* 306 */       super(s, sync);
/* 307 */       this.sortedSet = s;
/*     */     }
/*     */     protected SynchronizedSortedSet(IntSortedSet s) {
/* 310 */       super(s);
/* 311 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     public IntComparator comparator() {
/* 315 */       synchronized (this.sync) {
/* 316 */         return this.sortedSet.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public IntSortedSet subSet(int from, int to) {
/* 321 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public IntSortedSet headSet(int to) {
/* 325 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */     
/*     */     public IntSortedSet tailSet(int from) {
/* 329 */       return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
/*     */     }
/*     */     
/*     */     public IntBidirectionalIterator iterator() {
/* 333 */       return this.sortedSet.iterator();
/*     */     }
/*     */     
/*     */     public IntBidirectionalIterator iterator(int from) {
/* 337 */       return this.sortedSet.iterator(from);
/*     */     }
/*     */     
/*     */     public int firstInt() {
/* 341 */       synchronized (this.sync) {
/* 342 */         return this.sortedSet.firstInt();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int lastInt() {
/* 347 */       synchronized (this.sync) {
/* 348 */         return this.sortedSet.lastInt();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer first() {
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
/*     */     public Integer last() {
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
/*     */     public IntSortedSet subSet(Integer from, Integer to) {
/* 383 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public IntSortedSet headSet(Integer to) {
/* 393 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public IntSortedSet tailSet(Integer from) {
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
/*     */   public static IntSortedSet synchronize(IntSortedSet s) {
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
/*     */   public static IntSortedSet synchronize(IntSortedSet s, Object sync) {
/* 431 */     return new SynchronizedSortedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedSet
/*     */     extends IntSets.UnmodifiableSet
/*     */     implements IntSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final IntSortedSet sortedSet;
/*     */     
/*     */     protected UnmodifiableSortedSet(IntSortedSet s) {
/* 441 */       super(s);
/* 442 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     public IntComparator comparator() {
/* 446 */       return this.sortedSet.comparator();
/*     */     }
/*     */     
/*     */     public IntSortedSet subSet(int from, int to) {
/* 450 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */     
/*     */     public IntSortedSet headSet(int to) {
/* 454 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */     
/*     */     public IntSortedSet tailSet(int from) {
/* 458 */       return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
/*     */     }
/*     */     
/*     */     public IntBidirectionalIterator iterator() {
/* 462 */       return IntIterators.unmodifiable(this.sortedSet.iterator());
/*     */     }
/*     */     
/*     */     public IntBidirectionalIterator iterator(int from) {
/* 466 */       return IntIterators.unmodifiable(this.sortedSet.iterator(from));
/*     */     }
/*     */     
/*     */     public int firstInt() {
/* 470 */       return this.sortedSet.firstInt();
/*     */     }
/*     */     
/*     */     public int lastInt() {
/* 474 */       return this.sortedSet.lastInt();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer first() {
/* 484 */       return this.sortedSet.first();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer last() {
/* 494 */       return this.sortedSet.last();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public IntSortedSet subSet(Integer from, Integer to) {
/* 504 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public IntSortedSet headSet(Integer to) {
/* 514 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public IntSortedSet tailSet(Integer from) {
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
/*     */   public static IntSortedSet unmodifiable(IntSortedSet s) {
/* 537 */     return new UnmodifiableSortedSet(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntSortedSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */