/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public final class ShortSortedSets
/*     */ {
/*     */   public static class EmptySet
/*     */     extends ShortSets.EmptySet
/*     */     implements ShortSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public ShortBidirectionalIterator iterator(short from) {
/*  43 */       return ShortIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet subSet(short from, short to) {
/*  48 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet headSet(short from) {
/*  53 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet tailSet(short to) {
/*  58 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public short firstShort() {
/*  62 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public short lastShort() {
/*  66 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public ShortComparator comparator() {
/*  70 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet subSet(Short from, Short to) {
/*  80 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet headSet(Short from) {
/*  90 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet tailSet(Short to) {
/* 100 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short first() {
/* 110 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short last() {
/* 120 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 124 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */     private Object readResolve() {
/* 127 */       return ShortSortedSets.EMPTY_SET;
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
/*     */   
/*     */   public static class Singleton
/*     */     extends ShortSets.Singleton
/*     */     implements ShortSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     final ShortComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(short element, ShortComparator comparator) {
/* 151 */       super(element);
/* 152 */       this.comparator = comparator;
/*     */     }
/*     */     private Singleton(short element) {
/* 155 */       this(element, (ShortComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(short k1, short k2) {
/* 159 */       return (this.comparator == null) ? Short.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public ShortBidirectionalIterator iterator(short from) {
/* 163 */       ShortBidirectionalIterator i = iterator();
/* 164 */       if (compare(this.element, from) <= 0)
/* 165 */         i.nextShort(); 
/* 166 */       return i;
/*     */     }
/*     */     
/*     */     public ShortComparator comparator() {
/* 170 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet subSet(short from, short to) {
/* 175 */       if (compare(from, this.element) <= 0 && compare(this.element, to) < 0)
/* 176 */         return this; 
/* 177 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet headSet(short to) {
/* 182 */       if (compare(this.element, to) < 0)
/* 183 */         return this; 
/* 184 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet tailSet(short from) {
/* 189 */       if (compare(from, this.element) <= 0)
/* 190 */         return this; 
/* 191 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public short firstShort() {
/* 195 */       return this.element;
/*     */     }
/*     */     
/*     */     public short lastShort() {
/* 199 */       return this.element;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet subSet(Short from, Short to) {
/* 209 */       return subSet(from.shortValue(), to.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet headSet(Short to) {
/* 219 */       return headSet(to.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet tailSet(Short from) {
/* 229 */       return tailSet(from.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short first() {
/* 239 */       return Short.valueOf(this.element);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short last() {
/* 249 */       return Short.valueOf(this.element);
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
/*     */   public static ShortSortedSet singleton(short element) {
/* 261 */     return new Singleton(element);
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
/*     */   public static ShortSortedSet singleton(short element, ShortComparator comparator) {
/* 275 */     return new Singleton(element, comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShortSortedSet singleton(Object element) {
/* 286 */     return new Singleton(((Short)element).shortValue());
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
/*     */   public static ShortSortedSet singleton(Object element, ShortComparator comparator) {
/* 300 */     return new Singleton(((Short)element).shortValue(), comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedSet
/*     */     extends ShortSets.SynchronizedSet
/*     */     implements ShortSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ShortSortedSet sortedSet;
/*     */     
/*     */     protected SynchronizedSortedSet(ShortSortedSet s, Object sync) {
/* 310 */       super(s, sync);
/* 311 */       this.sortedSet = s;
/*     */     }
/*     */     protected SynchronizedSortedSet(ShortSortedSet s) {
/* 314 */       super(s);
/* 315 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     public ShortComparator comparator() {
/* 319 */       synchronized (this.sync) {
/* 320 */         return this.sortedSet.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ShortSortedSet subSet(short from, short to) {
/* 325 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public ShortSortedSet headSet(short to) {
/* 329 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */     
/*     */     public ShortSortedSet tailSet(short from) {
/* 333 */       return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
/*     */     }
/*     */     
/*     */     public ShortBidirectionalIterator iterator() {
/* 337 */       return this.sortedSet.iterator();
/*     */     }
/*     */     
/*     */     public ShortBidirectionalIterator iterator(short from) {
/* 341 */       return this.sortedSet.iterator(from);
/*     */     }
/*     */     
/*     */     public short firstShort() {
/* 345 */       synchronized (this.sync) {
/* 346 */         return this.sortedSet.firstShort();
/*     */       } 
/*     */     }
/*     */     
/*     */     public short lastShort() {
/* 351 */       synchronized (this.sync) {
/* 352 */         return this.sortedSet.lastShort();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short first() {
/* 363 */       synchronized (this.sync) {
/* 364 */         return this.sortedSet.first();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short last() {
/* 375 */       synchronized (this.sync) {
/* 376 */         return this.sortedSet.last();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet subSet(Short from, Short to) {
/* 387 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet headSet(Short to) {
/* 397 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet tailSet(Short from) {
/* 407 */       return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
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
/*     */   public static ShortSortedSet synchronize(ShortSortedSet s) {
/* 420 */     return new SynchronizedSortedSet(s);
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
/*     */   public static ShortSortedSet synchronize(ShortSortedSet s, Object sync) {
/* 435 */     return new SynchronizedSortedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedSet
/*     */     extends ShortSets.UnmodifiableSet
/*     */     implements ShortSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ShortSortedSet sortedSet;
/*     */     
/*     */     protected UnmodifiableSortedSet(ShortSortedSet s) {
/* 445 */       super(s);
/* 446 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     public ShortComparator comparator() {
/* 450 */       return this.sortedSet.comparator();
/*     */     }
/*     */     
/*     */     public ShortSortedSet subSet(short from, short to) {
/* 454 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */     
/*     */     public ShortSortedSet headSet(short to) {
/* 458 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */     
/*     */     public ShortSortedSet tailSet(short from) {
/* 462 */       return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
/*     */     }
/*     */     
/*     */     public ShortBidirectionalIterator iterator() {
/* 466 */       return ShortIterators.unmodifiable(this.sortedSet.iterator());
/*     */     }
/*     */     
/*     */     public ShortBidirectionalIterator iterator(short from) {
/* 470 */       return ShortIterators.unmodifiable(this.sortedSet.iterator(from));
/*     */     }
/*     */     
/*     */     public short firstShort() {
/* 474 */       return this.sortedSet.firstShort();
/*     */     }
/*     */     
/*     */     public short lastShort() {
/* 478 */       return this.sortedSet.lastShort();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short first() {
/* 488 */       return this.sortedSet.first();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short last() {
/* 498 */       return this.sortedSet.last();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet subSet(Short from, Short to) {
/* 508 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet headSet(Short to) {
/* 518 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet tailSet(Short from) {
/* 528 */       return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
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
/*     */   public static ShortSortedSet unmodifiable(ShortSortedSet s) {
/* 541 */     return new UnmodifiableSortedSet(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\ShortSortedSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */