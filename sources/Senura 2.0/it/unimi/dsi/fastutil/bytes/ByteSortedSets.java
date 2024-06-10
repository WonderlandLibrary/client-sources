/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public final class ByteSortedSets
/*     */ {
/*     */   public static class EmptySet
/*     */     extends ByteSets.EmptySet
/*     */     implements ByteSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public ByteBidirectionalIterator iterator(byte from) {
/*  43 */       return ByteIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet subSet(byte from, byte to) {
/*  48 */       return ByteSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet headSet(byte from) {
/*  53 */       return ByteSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet tailSet(byte to) {
/*  58 */       return ByteSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public byte firstByte() {
/*  62 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public byte lastByte() {
/*  66 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public ByteComparator comparator() {
/*  70 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ByteSortedSet subSet(Byte from, Byte to) {
/*  80 */       return ByteSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ByteSortedSet headSet(Byte from) {
/*  90 */       return ByteSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ByteSortedSet tailSet(Byte to) {
/* 100 */       return ByteSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte first() {
/* 110 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte last() {
/* 120 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 124 */       return ByteSortedSets.EMPTY_SET;
/*     */     }
/*     */     private Object readResolve() {
/* 127 */       return ByteSortedSets.EMPTY_SET;
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
/*     */     extends ByteSets.Singleton
/*     */     implements ByteSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     final ByteComparator comparator;
/*     */     
/*     */     protected Singleton(byte element, ByteComparator comparator) {
/* 147 */       super(element);
/* 148 */       this.comparator = comparator;
/*     */     }
/*     */     private Singleton(byte element) {
/* 151 */       this(element, (ByteComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(byte k1, byte k2) {
/* 155 */       return (this.comparator == null) ? Byte.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public ByteBidirectionalIterator iterator(byte from) {
/* 159 */       ByteBidirectionalIterator i = iterator();
/* 160 */       if (compare(this.element, from) <= 0)
/* 161 */         i.nextByte(); 
/* 162 */       return i;
/*     */     }
/*     */     
/*     */     public ByteComparator comparator() {
/* 166 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet subSet(byte from, byte to) {
/* 171 */       if (compare(from, this.element) <= 0 && compare(this.element, to) < 0)
/* 172 */         return this; 
/* 173 */       return ByteSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet headSet(byte to) {
/* 178 */       if (compare(this.element, to) < 0)
/* 179 */         return this; 
/* 180 */       return ByteSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet tailSet(byte from) {
/* 185 */       if (compare(from, this.element) <= 0)
/* 186 */         return this; 
/* 187 */       return ByteSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public byte firstByte() {
/* 191 */       return this.element;
/*     */     }
/*     */     
/*     */     public byte lastByte() {
/* 195 */       return this.element;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ByteSortedSet subSet(Byte from, Byte to) {
/* 205 */       return subSet(from.byteValue(), to.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ByteSortedSet headSet(Byte to) {
/* 215 */       return headSet(to.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ByteSortedSet tailSet(Byte from) {
/* 225 */       return tailSet(from.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte first() {
/* 235 */       return Byte.valueOf(this.element);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte last() {
/* 245 */       return Byte.valueOf(this.element);
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
/*     */   public static ByteSortedSet singleton(byte element) {
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
/*     */   public static ByteSortedSet singleton(byte element, ByteComparator comparator) {
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
/*     */   public static ByteSortedSet singleton(Object element) {
/* 282 */     return new Singleton(((Byte)element).byteValue());
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
/*     */   public static ByteSortedSet singleton(Object element, ByteComparator comparator) {
/* 296 */     return new Singleton(((Byte)element).byteValue(), comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedSet
/*     */     extends ByteSets.SynchronizedSet
/*     */     implements ByteSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ByteSortedSet sortedSet;
/*     */     
/*     */     protected SynchronizedSortedSet(ByteSortedSet s, Object sync) {
/* 306 */       super(s, sync);
/* 307 */       this.sortedSet = s;
/*     */     }
/*     */     protected SynchronizedSortedSet(ByteSortedSet s) {
/* 310 */       super(s);
/* 311 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     public ByteComparator comparator() {
/* 315 */       synchronized (this.sync) {
/* 316 */         return this.sortedSet.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ByteSortedSet subSet(byte from, byte to) {
/* 321 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public ByteSortedSet headSet(byte to) {
/* 325 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */     
/*     */     public ByteSortedSet tailSet(byte from) {
/* 329 */       return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
/*     */     }
/*     */     
/*     */     public ByteBidirectionalIterator iterator() {
/* 333 */       return this.sortedSet.iterator();
/*     */     }
/*     */     
/*     */     public ByteBidirectionalIterator iterator(byte from) {
/* 337 */       return this.sortedSet.iterator(from);
/*     */     }
/*     */     
/*     */     public byte firstByte() {
/* 341 */       synchronized (this.sync) {
/* 342 */         return this.sortedSet.firstByte();
/*     */       } 
/*     */     }
/*     */     
/*     */     public byte lastByte() {
/* 347 */       synchronized (this.sync) {
/* 348 */         return this.sortedSet.lastByte();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte first() {
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
/*     */     public Byte last() {
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
/*     */     public ByteSortedSet subSet(Byte from, Byte to) {
/* 383 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ByteSortedSet headSet(Byte to) {
/* 393 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ByteSortedSet tailSet(Byte from) {
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
/*     */   public static ByteSortedSet synchronize(ByteSortedSet s) {
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
/*     */   public static ByteSortedSet synchronize(ByteSortedSet s, Object sync) {
/* 431 */     return new SynchronizedSortedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedSet
/*     */     extends ByteSets.UnmodifiableSet
/*     */     implements ByteSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ByteSortedSet sortedSet;
/*     */     
/*     */     protected UnmodifiableSortedSet(ByteSortedSet s) {
/* 441 */       super(s);
/* 442 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     public ByteComparator comparator() {
/* 446 */       return this.sortedSet.comparator();
/*     */     }
/*     */     
/*     */     public ByteSortedSet subSet(byte from, byte to) {
/* 450 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */     
/*     */     public ByteSortedSet headSet(byte to) {
/* 454 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */     
/*     */     public ByteSortedSet tailSet(byte from) {
/* 458 */       return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
/*     */     }
/*     */     
/*     */     public ByteBidirectionalIterator iterator() {
/* 462 */       return ByteIterators.unmodifiable(this.sortedSet.iterator());
/*     */     }
/*     */     
/*     */     public ByteBidirectionalIterator iterator(byte from) {
/* 466 */       return ByteIterators.unmodifiable(this.sortedSet.iterator(from));
/*     */     }
/*     */     
/*     */     public byte firstByte() {
/* 470 */       return this.sortedSet.firstByte();
/*     */     }
/*     */     
/*     */     public byte lastByte() {
/* 474 */       return this.sortedSet.lastByte();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte first() {
/* 484 */       return this.sortedSet.first();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte last() {
/* 494 */       return this.sortedSet.last();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ByteSortedSet subSet(Byte from, Byte to) {
/* 504 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ByteSortedSet headSet(Byte to) {
/* 514 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ByteSortedSet tailSet(Byte from) {
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
/*     */   public static ByteSortedSet unmodifiable(ByteSortedSet s) {
/* 537 */     return new UnmodifiableSortedSet(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\ByteSortedSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */