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
/*     */ public final class Char2ReferenceSortedMaps
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
/*     */   
/*     */   public static <V> ObjectBidirectionalIterator<Char2ReferenceMap.Entry<V>> fastIterator(Char2ReferenceSortedMap<V> map) {
/*  61 */     ObjectSortedSet<Char2ReferenceMap.Entry<V>> entries = map.char2ReferenceEntrySet();
/*  62 */     return (entries instanceof Char2ReferenceSortedMap.FastSortedEntrySet) ? (
/*  63 */       (Char2ReferenceSortedMap.FastSortedEntrySet)entries).fastIterator() : 
/*  64 */       entries.iterator();
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
/*     */   public static <V> ObjectBidirectionalIterable<Char2ReferenceMap.Entry<V>> fastIterable(Char2ReferenceSortedMap<V> map) {
/*  81 */     ObjectSortedSet<Char2ReferenceMap.Entry<V>> entries = map.char2ReferenceEntrySet();
/*     */     
/*  83 */     Objects.requireNonNull((Char2ReferenceSortedMap.FastSortedEntrySet)entries); return (entries instanceof Char2ReferenceSortedMap.FastSortedEntrySet) ? (Char2ReferenceSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  84 */       (ObjectBidirectionalIterable<Char2ReferenceMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<V>
/*     */     extends Char2ReferenceMaps.EmptyMap<V>
/*     */     implements Char2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CharComparator comparator() {
/* 103 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Char2ReferenceMap.Entry<V>> char2ReferenceEntrySet() {
/* 108 */       return (ObjectSortedSet<Char2ReferenceMap.Entry<V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Character, V>> entrySet() {
/* 119 */       return (ObjectSortedSet<Map.Entry<Character, V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet keySet() {
/* 124 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ReferenceSortedMap<V> subMap(char from, char to) {
/* 129 */       return Char2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ReferenceSortedMap<V> headMap(char to) {
/* 134 */       return Char2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ReferenceSortedMap<V> tailMap(char from) {
/* 139 */       return Char2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public char firstCharKey() {
/* 143 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public char lastCharKey() {
/* 147 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ReferenceSortedMap<V> headMap(Character oto) {
/* 157 */       return headMap(oto.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ReferenceSortedMap<V> tailMap(Character ofrom) {
/* 167 */       return tailMap(ofrom.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ReferenceSortedMap<V> subMap(Character ofrom, Character oto) {
/* 177 */       return subMap(ofrom.charValue(), oto.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character firstKey() {
/* 187 */       return Character.valueOf(firstCharKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character lastKey() {
/* 197 */       return Character.valueOf(lastCharKey());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 204 */   public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Char2ReferenceSortedMap<V> emptyMap() {
/* 215 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Char2ReferenceMaps.Singleton<V>
/*     */     implements Char2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final CharComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(char key, V value, CharComparator comparator) {
/* 232 */       super(key, value);
/* 233 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(char key, V value) {
/* 236 */       this(key, value, (CharComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(char k1, char k2) {
/* 240 */       return (this.comparator == null) ? Character.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public CharComparator comparator() {
/* 244 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Char2ReferenceMap.Entry<V>> char2ReferenceEntrySet() {
/* 249 */       if (this.entries == null)
/* 250 */         this.entries = (ObjectSet<Char2ReferenceMap.Entry<V>>)ObjectSortedSets.singleton(new AbstractChar2ReferenceMap.BasicEntry<>(this.key, this.value), 
/* 251 */             Char2ReferenceSortedMaps.entryComparator(this.comparator)); 
/* 252 */       return (ObjectSortedSet<Char2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Character, V>> entrySet() {
/* 263 */       return (ObjectSortedSet)char2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public CharSortedSet keySet() {
/* 267 */       if (this.keys == null)
/* 268 */         this.keys = CharSortedSets.singleton(this.key, this.comparator); 
/* 269 */       return (CharSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ReferenceSortedMap<V> subMap(char from, char to) {
/* 274 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 275 */         return this; 
/* 276 */       return Char2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ReferenceSortedMap<V> headMap(char to) {
/* 281 */       if (compare(this.key, to) < 0)
/* 282 */         return this; 
/* 283 */       return Char2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ReferenceSortedMap<V> tailMap(char from) {
/* 288 */       if (compare(from, this.key) <= 0)
/* 289 */         return this; 
/* 290 */       return Char2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public char firstCharKey() {
/* 294 */       return this.key;
/*     */     }
/*     */     
/*     */     public char lastCharKey() {
/* 298 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ReferenceSortedMap<V> headMap(Character oto) {
/* 308 */       return headMap(oto.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ReferenceSortedMap<V> tailMap(Character ofrom) {
/* 318 */       return tailMap(ofrom.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ReferenceSortedMap<V> subMap(Character ofrom, Character oto) {
/* 328 */       return subMap(ofrom.charValue(), oto.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character firstKey() {
/* 338 */       return Character.valueOf(firstCharKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character lastKey() {
/* 348 */       return Character.valueOf(lastCharKey());
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
/*     */   public static <V> Char2ReferenceSortedMap<V> singleton(Character key, V value) {
/* 367 */     return new Singleton<>(key.charValue(), value);
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
/*     */   public static <V> Char2ReferenceSortedMap<V> singleton(Character key, V value, CharComparator comparator) {
/* 387 */     return new Singleton<>(key.charValue(), value, comparator);
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
/*     */   public static <V> Char2ReferenceSortedMap<V> singleton(char key, V value) {
/* 405 */     return new Singleton<>(key, value);
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
/*     */   public static <V> Char2ReferenceSortedMap<V> singleton(char key, V value, CharComparator comparator) {
/* 425 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<V>
/*     */     extends Char2ReferenceMaps.SynchronizedMap<V>
/*     */     implements Char2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2ReferenceSortedMap<V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Char2ReferenceSortedMap<V> m, Object sync) {
/* 435 */       super(m, sync);
/* 436 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Char2ReferenceSortedMap<V> m) {
/* 439 */       super(m);
/* 440 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public CharComparator comparator() {
/* 444 */       synchronized (this.sync) {
/* 445 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Char2ReferenceMap.Entry<V>> char2ReferenceEntrySet() {
/* 450 */       if (this.entries == null)
/* 451 */         this.entries = (ObjectSet<Char2ReferenceMap.Entry<V>>)ObjectSortedSets.synchronize(this.sortedMap.char2ReferenceEntrySet(), this.sync); 
/* 452 */       return (ObjectSortedSet<Char2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Character, V>> entrySet() {
/* 463 */       return (ObjectSortedSet)char2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public CharSortedSet keySet() {
/* 467 */       if (this.keys == null)
/* 468 */         this.keys = CharSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 469 */       return (CharSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Char2ReferenceSortedMap<V> subMap(char from, char to) {
/* 473 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Char2ReferenceSortedMap<V> headMap(char to) {
/* 477 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Char2ReferenceSortedMap<V> tailMap(char from) {
/* 481 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public char firstCharKey() {
/* 485 */       synchronized (this.sync) {
/* 486 */         return this.sortedMap.firstCharKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public char lastCharKey() {
/* 491 */       synchronized (this.sync) {
/* 492 */         return this.sortedMap.lastCharKey();
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
/* 503 */       synchronized (this.sync) {
/* 504 */         return this.sortedMap.firstKey();
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
/* 515 */       synchronized (this.sync) {
/* 516 */         return this.sortedMap.lastKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ReferenceSortedMap<V> subMap(Character from, Character to) {
/* 527 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ReferenceSortedMap<V> headMap(Character to) {
/* 537 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ReferenceSortedMap<V> tailMap(Character from) {
/* 547 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
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
/*     */   public static <V> Char2ReferenceSortedMap<V> synchronize(Char2ReferenceSortedMap<V> m) {
/* 560 */     return new SynchronizedSortedMap<>(m);
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
/*     */   public static <V> Char2ReferenceSortedMap<V> synchronize(Char2ReferenceSortedMap<V> m, Object sync) {
/* 575 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<V>
/*     */     extends Char2ReferenceMaps.UnmodifiableMap<V>
/*     */     implements Char2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2ReferenceSortedMap<V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Char2ReferenceSortedMap<V> m) {
/* 585 */       super(m);
/* 586 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public CharComparator comparator() {
/* 590 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Char2ReferenceMap.Entry<V>> char2ReferenceEntrySet() {
/* 594 */       if (this.entries == null)
/* 595 */         this.entries = (ObjectSet<Char2ReferenceMap.Entry<V>>)ObjectSortedSets.unmodifiable(this.sortedMap.char2ReferenceEntrySet()); 
/* 596 */       return (ObjectSortedSet<Char2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Character, V>> entrySet() {
/* 607 */       return (ObjectSortedSet)char2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public CharSortedSet keySet() {
/* 611 */       if (this.keys == null)
/* 612 */         this.keys = CharSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 613 */       return (CharSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Char2ReferenceSortedMap<V> subMap(char from, char to) {
/* 617 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Char2ReferenceSortedMap<V> headMap(char to) {
/* 621 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Char2ReferenceSortedMap<V> tailMap(char from) {
/* 625 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public char firstCharKey() {
/* 629 */       return this.sortedMap.firstCharKey();
/*     */     }
/*     */     
/*     */     public char lastCharKey() {
/* 633 */       return this.sortedMap.lastCharKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character firstKey() {
/* 643 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character lastKey() {
/* 653 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ReferenceSortedMap<V> subMap(Character from, Character to) {
/* 663 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ReferenceSortedMap<V> headMap(Character to) {
/* 673 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ReferenceSortedMap<V> tailMap(Character from) {
/* 683 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
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
/*     */   public static <V> Char2ReferenceSortedMap<V> unmodifiable(Char2ReferenceSortedMap<V> m) {
/* 696 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2ReferenceSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */