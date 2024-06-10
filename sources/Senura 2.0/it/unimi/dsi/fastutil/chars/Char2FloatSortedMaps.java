/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
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
/*     */ public final class Char2FloatSortedMaps
/*     */ {
/*     */   public static Comparator<? super Map.Entry<Character, ?>> entryComparator(CharComparator comparator) {
/*  43 */     return (x, y) -> comparator.compare(((Character)x.getKey()).charValue(), ((Character)y.getKey()).charValue());
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
/*     */ 
/*     */   
/*     */   public static ObjectBidirectionalIterator<Char2FloatMap.Entry> fastIterator(Char2FloatSortedMap map) {
/*  60 */     ObjectSortedSet<Char2FloatMap.Entry> entries = map.char2FloatEntrySet();
/*  61 */     return (entries instanceof Char2FloatSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Char2FloatSortedMap.FastSortedEntrySet)entries).fastIterator() : 
/*  63 */       entries.iterator();
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
/*     */   
/*     */   public static ObjectBidirectionalIterable<Char2FloatMap.Entry> fastIterable(Char2FloatSortedMap map) {
/*  79 */     ObjectSortedSet<Char2FloatMap.Entry> entries = map.char2FloatEntrySet();
/*     */     
/*  81 */     Objects.requireNonNull((Char2FloatSortedMap.FastSortedEntrySet)entries); return (entries instanceof Char2FloatSortedMap.FastSortedEntrySet) ? (Char2FloatSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  82 */       (ObjectBidirectionalIterable<Char2FloatMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap
/*     */     extends Char2FloatMaps.EmptyMap
/*     */     implements Char2FloatSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CharComparator comparator() {
/* 101 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Char2FloatMap.Entry> char2FloatEntrySet() {
/* 106 */       return (ObjectSortedSet<Char2FloatMap.Entry>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Character, Float>> entrySet() {
/* 117 */       return (ObjectSortedSet<Map.Entry<Character, Float>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet keySet() {
/* 122 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2FloatSortedMap subMap(char from, char to) {
/* 127 */       return Char2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2FloatSortedMap headMap(char to) {
/* 132 */       return Char2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2FloatSortedMap tailMap(char from) {
/* 137 */       return Char2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public char firstCharKey() {
/* 141 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public char lastCharKey() {
/* 145 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2FloatSortedMap headMap(Character oto) {
/* 155 */       return headMap(oto.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2FloatSortedMap tailMap(Character ofrom) {
/* 165 */       return tailMap(ofrom.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2FloatSortedMap subMap(Character ofrom, Character oto) {
/* 175 */       return subMap(ofrom.charValue(), oto.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character firstKey() {
/* 185 */       return Character.valueOf(firstCharKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character lastKey() {
/* 195 */       return Character.valueOf(lastCharKey());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 202 */   public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton
/*     */     extends Char2FloatMaps.Singleton
/*     */     implements Char2FloatSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final CharComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(char key, float value, CharComparator comparator) {
/* 218 */       super(key, value);
/* 219 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(char key, float value) {
/* 222 */       this(key, value, (CharComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(char k1, char k2) {
/* 226 */       return (this.comparator == null) ? Character.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public CharComparator comparator() {
/* 230 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Char2FloatMap.Entry> char2FloatEntrySet() {
/* 235 */       if (this.entries == null)
/* 236 */         this.entries = (ObjectSet<Char2FloatMap.Entry>)ObjectSortedSets.singleton(new AbstractChar2FloatMap.BasicEntry(this.key, this.value), 
/* 237 */             Char2FloatSortedMaps.entryComparator(this.comparator)); 
/* 238 */       return (ObjectSortedSet<Char2FloatMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Character, Float>> entrySet() {
/* 249 */       return (ObjectSortedSet)char2FloatEntrySet();
/*     */     }
/*     */     
/*     */     public CharSortedSet keySet() {
/* 253 */       if (this.keys == null)
/* 254 */         this.keys = CharSortedSets.singleton(this.key, this.comparator); 
/* 255 */       return (CharSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2FloatSortedMap subMap(char from, char to) {
/* 260 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 261 */         return this; 
/* 262 */       return Char2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2FloatSortedMap headMap(char to) {
/* 267 */       if (compare(this.key, to) < 0)
/* 268 */         return this; 
/* 269 */       return Char2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2FloatSortedMap tailMap(char from) {
/* 274 */       if (compare(from, this.key) <= 0)
/* 275 */         return this; 
/* 276 */       return Char2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public char firstCharKey() {
/* 280 */       return this.key;
/*     */     }
/*     */     
/*     */     public char lastCharKey() {
/* 284 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2FloatSortedMap headMap(Character oto) {
/* 294 */       return headMap(oto.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2FloatSortedMap tailMap(Character ofrom) {
/* 304 */       return tailMap(ofrom.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2FloatSortedMap subMap(Character ofrom, Character oto) {
/* 314 */       return subMap(ofrom.charValue(), oto.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character firstKey() {
/* 324 */       return Character.valueOf(firstCharKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character lastKey() {
/* 334 */       return Character.valueOf(lastCharKey());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Char2FloatSortedMap singleton(Character key, Float value) {
/* 353 */     return new Singleton(key.charValue(), value.floatValue());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Char2FloatSortedMap singleton(Character key, Float value, CharComparator comparator) {
/* 373 */     return new Singleton(key.charValue(), value.floatValue(), comparator);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static Char2FloatSortedMap singleton(char key, float value) {
/* 391 */     return new Singleton(key, value);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Char2FloatSortedMap singleton(char key, float value, CharComparator comparator) {
/* 411 */     return new Singleton(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap
/*     */     extends Char2FloatMaps.SynchronizedMap
/*     */     implements Char2FloatSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2FloatSortedMap sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Char2FloatSortedMap m, Object sync) {
/* 421 */       super(m, sync);
/* 422 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Char2FloatSortedMap m) {
/* 425 */       super(m);
/* 426 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public CharComparator comparator() {
/* 430 */       synchronized (this.sync) {
/* 431 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Char2FloatMap.Entry> char2FloatEntrySet() {
/* 436 */       if (this.entries == null)
/* 437 */         this.entries = (ObjectSet<Char2FloatMap.Entry>)ObjectSortedSets.synchronize(this.sortedMap.char2FloatEntrySet(), this.sync); 
/* 438 */       return (ObjectSortedSet<Char2FloatMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Character, Float>> entrySet() {
/* 449 */       return (ObjectSortedSet)char2FloatEntrySet();
/*     */     }
/*     */     
/*     */     public CharSortedSet keySet() {
/* 453 */       if (this.keys == null)
/* 454 */         this.keys = CharSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 455 */       return (CharSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Char2FloatSortedMap subMap(char from, char to) {
/* 459 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Char2FloatSortedMap headMap(char to) {
/* 463 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Char2FloatSortedMap tailMap(char from) {
/* 467 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public char firstCharKey() {
/* 471 */       synchronized (this.sync) {
/* 472 */         return this.sortedMap.firstCharKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public char lastCharKey() {
/* 477 */       synchronized (this.sync) {
/* 478 */         return this.sortedMap.lastCharKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character firstKey() {
/* 489 */       synchronized (this.sync) {
/* 490 */         return this.sortedMap.firstKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character lastKey() {
/* 501 */       synchronized (this.sync) {
/* 502 */         return this.sortedMap.lastKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2FloatSortedMap subMap(Character from, Character to) {
/* 513 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2FloatSortedMap headMap(Character to) {
/* 523 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2FloatSortedMap tailMap(Character from) {
/* 533 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
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
/*     */   public static Char2FloatSortedMap synchronize(Char2FloatSortedMap m) {
/* 546 */     return new SynchronizedSortedMap(m);
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
/*     */   public static Char2FloatSortedMap synchronize(Char2FloatSortedMap m, Object sync) {
/* 561 */     return new SynchronizedSortedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap
/*     */     extends Char2FloatMaps.UnmodifiableMap
/*     */     implements Char2FloatSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2FloatSortedMap sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Char2FloatSortedMap m) {
/* 571 */       super(m);
/* 572 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public CharComparator comparator() {
/* 576 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Char2FloatMap.Entry> char2FloatEntrySet() {
/* 580 */       if (this.entries == null)
/* 581 */         this.entries = (ObjectSet<Char2FloatMap.Entry>)ObjectSortedSets.unmodifiable(this.sortedMap.char2FloatEntrySet()); 
/* 582 */       return (ObjectSortedSet<Char2FloatMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Character, Float>> entrySet() {
/* 593 */       return (ObjectSortedSet)char2FloatEntrySet();
/*     */     }
/*     */     
/*     */     public CharSortedSet keySet() {
/* 597 */       if (this.keys == null)
/* 598 */         this.keys = CharSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 599 */       return (CharSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Char2FloatSortedMap subMap(char from, char to) {
/* 603 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Char2FloatSortedMap headMap(char to) {
/* 607 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Char2FloatSortedMap tailMap(char from) {
/* 611 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public char firstCharKey() {
/* 615 */       return this.sortedMap.firstCharKey();
/*     */     }
/*     */     
/*     */     public char lastCharKey() {
/* 619 */       return this.sortedMap.lastCharKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character firstKey() {
/* 629 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character lastKey() {
/* 639 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2FloatSortedMap subMap(Character from, Character to) {
/* 649 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2FloatSortedMap headMap(Character to) {
/* 659 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2FloatSortedMap tailMap(Character from) {
/* 669 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
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
/*     */   public static Char2FloatSortedMap unmodifiable(Char2FloatSortedMap m) {
/* 682 */     return new UnmodifiableSortedMap(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2FloatSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */