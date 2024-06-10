/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ public final class DoubleSortedSets
/*     */ {
/*     */   public static class EmptySet
/*     */     extends DoubleSets.EmptySet
/*     */     implements DoubleSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public DoubleBidirectionalIterator iterator(double from) {
/*  47 */       return DoubleIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet subSet(double from, double to) {
/*  52 */       return DoubleSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet headSet(double from) {
/*  57 */       return DoubleSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet tailSet(double to) {
/*  62 */       return DoubleSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public double firstDouble() {
/*  66 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public double lastDouble() {
/*  70 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public DoubleComparator comparator() {
/*  74 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public DoubleSortedSet subSet(Double from, Double to) {
/*  84 */       return DoubleSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public DoubleSortedSet headSet(Double from) {
/*  94 */       return DoubleSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public DoubleSortedSet tailSet(Double to) {
/* 104 */       return DoubleSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double first() {
/* 114 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double last() {
/* 124 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 128 */       return DoubleSortedSets.EMPTY_SET;
/*     */     }
/*     */     private Object readResolve() {
/* 131 */       return DoubleSortedSets.EMPTY_SET;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   public static final EmptySet EMPTY_SET = new EmptySet();
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton
/*     */     extends DoubleSets.Singleton
/*     */     implements DoubleSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     final DoubleComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(double element, DoubleComparator comparator) {
/* 155 */       super(element);
/* 156 */       this.comparator = comparator;
/*     */     }
/*     */     private Singleton(double element) {
/* 159 */       this(element, (DoubleComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(double k1, double k2) {
/* 163 */       return (this.comparator == null) ? Double.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public DoubleBidirectionalIterator iterator(double from) {
/* 167 */       DoubleBidirectionalIterator i = iterator();
/* 168 */       if (compare(this.element, from) <= 0)
/* 169 */         i.nextDouble(); 
/* 170 */       return i;
/*     */     }
/*     */     
/*     */     public DoubleComparator comparator() {
/* 174 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet subSet(double from, double to) {
/* 179 */       if (compare(from, this.element) <= 0 && compare(this.element, to) < 0)
/* 180 */         return this; 
/* 181 */       return DoubleSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet headSet(double to) {
/* 186 */       if (compare(this.element, to) < 0)
/* 187 */         return this; 
/* 188 */       return DoubleSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet tailSet(double from) {
/* 193 */       if (compare(from, this.element) <= 0)
/* 194 */         return this; 
/* 195 */       return DoubleSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public double firstDouble() {
/* 199 */       return this.element;
/*     */     }
/*     */     
/*     */     public double lastDouble() {
/* 203 */       return this.element;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public DoubleSortedSet subSet(Double from, Double to) {
/* 213 */       return subSet(from.doubleValue(), to.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public DoubleSortedSet headSet(Double to) {
/* 223 */       return headSet(to.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public DoubleSortedSet tailSet(Double from) {
/* 233 */       return tailSet(from.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double first() {
/* 243 */       return Double.valueOf(this.element);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double last() {
/* 253 */       return Double.valueOf(this.element);
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
/*     */   public static DoubleSortedSet singleton(double element) {
/* 265 */     return new Singleton(element);
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
/*     */   public static DoubleSortedSet singleton(double element, DoubleComparator comparator) {
/* 279 */     return new Singleton(element, comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DoubleSortedSet singleton(Object element) {
/* 290 */     return new Singleton(((Double)element).doubleValue());
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
/*     */   public static DoubleSortedSet singleton(Object element, DoubleComparator comparator) {
/* 304 */     return new Singleton(((Double)element).doubleValue(), comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedSet
/*     */     extends DoubleSets.SynchronizedSet
/*     */     implements DoubleSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final DoubleSortedSet sortedSet;
/*     */     
/*     */     protected SynchronizedSortedSet(DoubleSortedSet s, Object sync) {
/* 314 */       super(s, sync);
/* 315 */       this.sortedSet = s;
/*     */     }
/*     */     protected SynchronizedSortedSet(DoubleSortedSet s) {
/* 318 */       super(s);
/* 319 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     public DoubleComparator comparator() {
/* 323 */       synchronized (this.sync) {
/* 324 */         return this.sortedSet.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public DoubleSortedSet subSet(double from, double to) {
/* 329 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public DoubleSortedSet headSet(double to) {
/* 333 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */     
/*     */     public DoubleSortedSet tailSet(double from) {
/* 337 */       return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
/*     */     }
/*     */     
/*     */     public DoubleBidirectionalIterator iterator() {
/* 341 */       return this.sortedSet.iterator();
/*     */     }
/*     */     
/*     */     public DoubleBidirectionalIterator iterator(double from) {
/* 345 */       return this.sortedSet.iterator(from);
/*     */     }
/*     */     
/*     */     public double firstDouble() {
/* 349 */       synchronized (this.sync) {
/* 350 */         return this.sortedSet.firstDouble();
/*     */       } 
/*     */     }
/*     */     
/*     */     public double lastDouble() {
/* 355 */       synchronized (this.sync) {
/* 356 */         return this.sortedSet.lastDouble();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double first() {
/* 367 */       synchronized (this.sync) {
/* 368 */         return this.sortedSet.first();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double last() {
/* 379 */       synchronized (this.sync) {
/* 380 */         return this.sortedSet.last();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public DoubleSortedSet subSet(Double from, Double to) {
/* 391 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public DoubleSortedSet headSet(Double to) {
/* 401 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public DoubleSortedSet tailSet(Double from) {
/* 411 */       return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
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
/*     */   public static DoubleSortedSet synchronize(DoubleSortedSet s) {
/* 424 */     return new SynchronizedSortedSet(s);
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
/*     */   public static DoubleSortedSet synchronize(DoubleSortedSet s, Object sync) {
/* 439 */     return new SynchronizedSortedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedSet
/*     */     extends DoubleSets.UnmodifiableSet
/*     */     implements DoubleSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final DoubleSortedSet sortedSet;
/*     */     
/*     */     protected UnmodifiableSortedSet(DoubleSortedSet s) {
/* 449 */       super(s);
/* 450 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     public DoubleComparator comparator() {
/* 454 */       return this.sortedSet.comparator();
/*     */     }
/*     */     
/*     */     public DoubleSortedSet subSet(double from, double to) {
/* 458 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */     
/*     */     public DoubleSortedSet headSet(double to) {
/* 462 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */     
/*     */     public DoubleSortedSet tailSet(double from) {
/* 466 */       return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
/*     */     }
/*     */     
/*     */     public DoubleBidirectionalIterator iterator() {
/* 470 */       return DoubleIterators.unmodifiable(this.sortedSet.iterator());
/*     */     }
/*     */     
/*     */     public DoubleBidirectionalIterator iterator(double from) {
/* 474 */       return DoubleIterators.unmodifiable(this.sortedSet.iterator(from));
/*     */     }
/*     */     
/*     */     public double firstDouble() {
/* 478 */       return this.sortedSet.firstDouble();
/*     */     }
/*     */     
/*     */     public double lastDouble() {
/* 482 */       return this.sortedSet.lastDouble();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double first() {
/* 492 */       return this.sortedSet.first();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double last() {
/* 502 */       return this.sortedSet.last();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public DoubleSortedSet subSet(Double from, Double to) {
/* 512 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public DoubleSortedSet headSet(Double to) {
/* 522 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public DoubleSortedSet tailSet(Double from) {
/* 532 */       return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
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
/*     */   public static DoubleSortedSet unmodifiable(DoubleSortedSet s) {
/* 545 */     return new UnmodifiableSortedSet(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleSortedSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */