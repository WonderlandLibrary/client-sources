/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public final class CharSortedSets
/*     */ {
/*     */   public static class EmptySet
/*     */     extends CharSets.EmptySet
/*     */     implements CharSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public CharBidirectionalIterator iterator(char from) {
/*  43 */       return CharIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet subSet(char from, char to) {
/*  48 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet headSet(char from) {
/*  53 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet tailSet(char to) {
/*  58 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public char firstChar() {
/*  62 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public char lastChar() {
/*  66 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public CharComparator comparator() {
/*  70 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public CharSortedSet subSet(Character from, Character to) {
/*  80 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public CharSortedSet headSet(Character from) {
/*  90 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public CharSortedSet tailSet(Character to) {
/* 100 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character first() {
/* 110 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character last() {
/* 120 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 124 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */     private Object readResolve() {
/* 127 */       return CharSortedSets.EMPTY_SET;
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
/*     */     extends CharSets.Singleton
/*     */     implements CharSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     final CharComparator comparator;
/*     */     
/*     */     protected Singleton(char element, CharComparator comparator) {
/* 147 */       super(element);
/* 148 */       this.comparator = comparator;
/*     */     }
/*     */     private Singleton(char element) {
/* 151 */       this(element, (CharComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(char k1, char k2) {
/* 155 */       return (this.comparator == null) ? Character.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public CharBidirectionalIterator iterator(char from) {
/* 159 */       CharBidirectionalIterator i = iterator();
/* 160 */       if (compare(this.element, from) <= 0)
/* 161 */         i.nextChar(); 
/* 162 */       return i;
/*     */     }
/*     */     
/*     */     public CharComparator comparator() {
/* 166 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet subSet(char from, char to) {
/* 171 */       if (compare(from, this.element) <= 0 && compare(this.element, to) < 0)
/* 172 */         return this; 
/* 173 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet headSet(char to) {
/* 178 */       if (compare(this.element, to) < 0)
/* 179 */         return this; 
/* 180 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet tailSet(char from) {
/* 185 */       if (compare(from, this.element) <= 0)
/* 186 */         return this; 
/* 187 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public char firstChar() {
/* 191 */       return this.element;
/*     */     }
/*     */     
/*     */     public char lastChar() {
/* 195 */       return this.element;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public CharSortedSet subSet(Character from, Character to) {
/* 205 */       return subSet(from.charValue(), to.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public CharSortedSet headSet(Character to) {
/* 215 */       return headSet(to.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public CharSortedSet tailSet(Character from) {
/* 225 */       return tailSet(from.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character first() {
/* 235 */       return Character.valueOf(this.element);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character last() {
/* 245 */       return Character.valueOf(this.element);
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
/*     */   public static CharSortedSet singleton(char element) {
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
/*     */   public static CharSortedSet singleton(char element, CharComparator comparator) {
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
/*     */   public static CharSortedSet singleton(Object element) {
/* 282 */     return new Singleton(((Character)element).charValue());
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
/*     */   public static CharSortedSet singleton(Object element, CharComparator comparator) {
/* 296 */     return new Singleton(((Character)element).charValue(), comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedSet
/*     */     extends CharSets.SynchronizedSet
/*     */     implements CharSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final CharSortedSet sortedSet;
/*     */     
/*     */     protected SynchronizedSortedSet(CharSortedSet s, Object sync) {
/* 306 */       super(s, sync);
/* 307 */       this.sortedSet = s;
/*     */     }
/*     */     protected SynchronizedSortedSet(CharSortedSet s) {
/* 310 */       super(s);
/* 311 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     public CharComparator comparator() {
/* 315 */       synchronized (this.sync) {
/* 316 */         return this.sortedSet.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public CharSortedSet subSet(char from, char to) {
/* 321 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public CharSortedSet headSet(char to) {
/* 325 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */     
/*     */     public CharSortedSet tailSet(char from) {
/* 329 */       return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
/*     */     }
/*     */     
/*     */     public CharBidirectionalIterator iterator() {
/* 333 */       return this.sortedSet.iterator();
/*     */     }
/*     */     
/*     */     public CharBidirectionalIterator iterator(char from) {
/* 337 */       return this.sortedSet.iterator(from);
/*     */     }
/*     */     
/*     */     public char firstChar() {
/* 341 */       synchronized (this.sync) {
/* 342 */         return this.sortedSet.firstChar();
/*     */       } 
/*     */     }
/*     */     
/*     */     public char lastChar() {
/* 347 */       synchronized (this.sync) {
/* 348 */         return this.sortedSet.lastChar();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character first() {
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
/*     */     public Character last() {
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
/*     */     public CharSortedSet subSet(Character from, Character to) {
/* 383 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public CharSortedSet headSet(Character to) {
/* 393 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public CharSortedSet tailSet(Character from) {
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
/*     */   public static CharSortedSet synchronize(CharSortedSet s) {
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
/*     */   public static CharSortedSet synchronize(CharSortedSet s, Object sync) {
/* 431 */     return new SynchronizedSortedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedSet
/*     */     extends CharSets.UnmodifiableSet
/*     */     implements CharSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final CharSortedSet sortedSet;
/*     */     
/*     */     protected UnmodifiableSortedSet(CharSortedSet s) {
/* 441 */       super(s);
/* 442 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     public CharComparator comparator() {
/* 446 */       return this.sortedSet.comparator();
/*     */     }
/*     */     
/*     */     public CharSortedSet subSet(char from, char to) {
/* 450 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */     
/*     */     public CharSortedSet headSet(char to) {
/* 454 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */     
/*     */     public CharSortedSet tailSet(char from) {
/* 458 */       return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
/*     */     }
/*     */     
/*     */     public CharBidirectionalIterator iterator() {
/* 462 */       return CharIterators.unmodifiable(this.sortedSet.iterator());
/*     */     }
/*     */     
/*     */     public CharBidirectionalIterator iterator(char from) {
/* 466 */       return CharIterators.unmodifiable(this.sortedSet.iterator(from));
/*     */     }
/*     */     
/*     */     public char firstChar() {
/* 470 */       return this.sortedSet.firstChar();
/*     */     }
/*     */     
/*     */     public char lastChar() {
/* 474 */       return this.sortedSet.lastChar();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character first() {
/* 484 */       return this.sortedSet.first();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character last() {
/* 494 */       return this.sortedSet.last();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public CharSortedSet subSet(Character from, Character to) {
/* 504 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public CharSortedSet headSet(Character to) {
/* 514 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public CharSortedSet tailSet(Character from) {
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
/*     */   public static CharSortedSet unmodifiable(CharSortedSet s) {
/* 537 */     return new UnmodifiableSortedSet(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\CharSortedSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */