/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public final class FloatSortedSets
/*     */ {
/*     */   public static class EmptySet
/*     */     extends FloatSets.EmptySet
/*     */     implements FloatSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public FloatBidirectionalIterator iterator(float from) {
/*  43 */       return FloatIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet subSet(float from, float to) {
/*  48 */       return FloatSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet headSet(float from) {
/*  53 */       return FloatSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet tailSet(float to) {
/*  58 */       return FloatSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public float firstFloat() {
/*  62 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public float lastFloat() {
/*  66 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public FloatComparator comparator() {
/*  70 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public FloatSortedSet subSet(Float from, Float to) {
/*  80 */       return FloatSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public FloatSortedSet headSet(Float from) {
/*  90 */       return FloatSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public FloatSortedSet tailSet(Float to) {
/* 100 */       return FloatSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float first() {
/* 110 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float last() {
/* 120 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 124 */       return FloatSortedSets.EMPTY_SET;
/*     */     }
/*     */     private Object readResolve() {
/* 127 */       return FloatSortedSets.EMPTY_SET;
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
/*     */     extends FloatSets.Singleton
/*     */     implements FloatSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     final FloatComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(float element, FloatComparator comparator) {
/* 151 */       super(element);
/* 152 */       this.comparator = comparator;
/*     */     }
/*     */     private Singleton(float element) {
/* 155 */       this(element, (FloatComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(float k1, float k2) {
/* 159 */       return (this.comparator == null) ? Float.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public FloatBidirectionalIterator iterator(float from) {
/* 163 */       FloatBidirectionalIterator i = iterator();
/* 164 */       if (compare(this.element, from) <= 0)
/* 165 */         i.nextFloat(); 
/* 166 */       return i;
/*     */     }
/*     */     
/*     */     public FloatComparator comparator() {
/* 170 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet subSet(float from, float to) {
/* 175 */       if (compare(from, this.element) <= 0 && compare(this.element, to) < 0)
/* 176 */         return this; 
/* 177 */       return FloatSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet headSet(float to) {
/* 182 */       if (compare(this.element, to) < 0)
/* 183 */         return this; 
/* 184 */       return FloatSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet tailSet(float from) {
/* 189 */       if (compare(from, this.element) <= 0)
/* 190 */         return this; 
/* 191 */       return FloatSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public float firstFloat() {
/* 195 */       return this.element;
/*     */     }
/*     */     
/*     */     public float lastFloat() {
/* 199 */       return this.element;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public FloatSortedSet subSet(Float from, Float to) {
/* 209 */       return subSet(from.floatValue(), to.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public FloatSortedSet headSet(Float to) {
/* 219 */       return headSet(to.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public FloatSortedSet tailSet(Float from) {
/* 229 */       return tailSet(from.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float first() {
/* 239 */       return Float.valueOf(this.element);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float last() {
/* 249 */       return Float.valueOf(this.element);
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
/*     */   public static FloatSortedSet singleton(float element) {
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
/*     */   public static FloatSortedSet singleton(float element, FloatComparator comparator) {
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
/*     */   public static FloatSortedSet singleton(Object element) {
/* 286 */     return new Singleton(((Float)element).floatValue());
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
/*     */   public static FloatSortedSet singleton(Object element, FloatComparator comparator) {
/* 300 */     return new Singleton(((Float)element).floatValue(), comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedSet
/*     */     extends FloatSets.SynchronizedSet
/*     */     implements FloatSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final FloatSortedSet sortedSet;
/*     */     
/*     */     protected SynchronizedSortedSet(FloatSortedSet s, Object sync) {
/* 310 */       super(s, sync);
/* 311 */       this.sortedSet = s;
/*     */     }
/*     */     protected SynchronizedSortedSet(FloatSortedSet s) {
/* 314 */       super(s);
/* 315 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     public FloatComparator comparator() {
/* 319 */       synchronized (this.sync) {
/* 320 */         return this.sortedSet.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public FloatSortedSet subSet(float from, float to) {
/* 325 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public FloatSortedSet headSet(float to) {
/* 329 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */     
/*     */     public FloatSortedSet tailSet(float from) {
/* 333 */       return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
/*     */     }
/*     */     
/*     */     public FloatBidirectionalIterator iterator() {
/* 337 */       return this.sortedSet.iterator();
/*     */     }
/*     */     
/*     */     public FloatBidirectionalIterator iterator(float from) {
/* 341 */       return this.sortedSet.iterator(from);
/*     */     }
/*     */     
/*     */     public float firstFloat() {
/* 345 */       synchronized (this.sync) {
/* 346 */         return this.sortedSet.firstFloat();
/*     */       } 
/*     */     }
/*     */     
/*     */     public float lastFloat() {
/* 351 */       synchronized (this.sync) {
/* 352 */         return this.sortedSet.lastFloat();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float first() {
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
/*     */     public Float last() {
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
/*     */     public FloatSortedSet subSet(Float from, Float to) {
/* 387 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public FloatSortedSet headSet(Float to) {
/* 397 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public FloatSortedSet tailSet(Float from) {
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
/*     */   public static FloatSortedSet synchronize(FloatSortedSet s) {
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
/*     */   public static FloatSortedSet synchronize(FloatSortedSet s, Object sync) {
/* 435 */     return new SynchronizedSortedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedSet
/*     */     extends FloatSets.UnmodifiableSet
/*     */     implements FloatSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final FloatSortedSet sortedSet;
/*     */     
/*     */     protected UnmodifiableSortedSet(FloatSortedSet s) {
/* 445 */       super(s);
/* 446 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     public FloatComparator comparator() {
/* 450 */       return this.sortedSet.comparator();
/*     */     }
/*     */     
/*     */     public FloatSortedSet subSet(float from, float to) {
/* 454 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */     
/*     */     public FloatSortedSet headSet(float to) {
/* 458 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */     
/*     */     public FloatSortedSet tailSet(float from) {
/* 462 */       return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
/*     */     }
/*     */     
/*     */     public FloatBidirectionalIterator iterator() {
/* 466 */       return FloatIterators.unmodifiable(this.sortedSet.iterator());
/*     */     }
/*     */     
/*     */     public FloatBidirectionalIterator iterator(float from) {
/* 470 */       return FloatIterators.unmodifiable(this.sortedSet.iterator(from));
/*     */     }
/*     */     
/*     */     public float firstFloat() {
/* 474 */       return this.sortedSet.firstFloat();
/*     */     }
/*     */     
/*     */     public float lastFloat() {
/* 478 */       return this.sortedSet.lastFloat();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float first() {
/* 488 */       return this.sortedSet.first();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float last() {
/* 498 */       return this.sortedSet.last();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public FloatSortedSet subSet(Float from, Float to) {
/* 508 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public FloatSortedSet headSet(Float to) {
/* 518 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public FloatSortedSet tailSet(Float from) {
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
/*     */   public static FloatSortedSet unmodifiable(FloatSortedSet s) {
/* 541 */     return new UnmodifiableSortedSet(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatSortedSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */