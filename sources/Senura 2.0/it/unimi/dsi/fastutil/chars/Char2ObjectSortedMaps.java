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
/*     */ public final class Char2ObjectSortedMaps
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
/*     */   public static <V> ObjectBidirectionalIterator<Char2ObjectMap.Entry<V>> fastIterator(Char2ObjectSortedMap<V> map) {
/*  60 */     ObjectSortedSet<Char2ObjectMap.Entry<V>> entries = map.char2ObjectEntrySet();
/*  61 */     return (entries instanceof Char2ObjectSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Char2ObjectSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static <V> ObjectBidirectionalIterable<Char2ObjectMap.Entry<V>> fastIterable(Char2ObjectSortedMap<V> map) {
/*  79 */     ObjectSortedSet<Char2ObjectMap.Entry<V>> entries = map.char2ObjectEntrySet();
/*     */     
/*  81 */     Objects.requireNonNull((Char2ObjectSortedMap.FastSortedEntrySet)entries); return (entries instanceof Char2ObjectSortedMap.FastSortedEntrySet) ? (Char2ObjectSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  82 */       (ObjectBidirectionalIterable<Char2ObjectMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<V>
/*     */     extends Char2ObjectMaps.EmptyMap<V>
/*     */     implements Char2ObjectSortedMap<V>, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Char2ObjectMap.Entry<V>> char2ObjectEntrySet() {
/* 106 */       return (ObjectSortedSet<Char2ObjectMap.Entry<V>>)ObjectSortedSets.EMPTY_SET;
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
/* 117 */       return (ObjectSortedSet<Map.Entry<Character, V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet keySet() {
/* 122 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ObjectSortedMap<V> subMap(char from, char to) {
/* 127 */       return Char2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ObjectSortedMap<V> headMap(char to) {
/* 132 */       return Char2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ObjectSortedMap<V> tailMap(char from) {
/* 137 */       return Char2ObjectSortedMaps.EMPTY_MAP;
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
/*     */     public Char2ObjectSortedMap<V> headMap(Character oto) {
/* 155 */       return headMap(oto.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ObjectSortedMap<V> tailMap(Character ofrom) {
/* 165 */       return tailMap(ofrom.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ObjectSortedMap<V> subMap(Character ofrom, Character oto) {
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Char2ObjectSortedMap<V> emptyMap() {
/* 213 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Char2ObjectMaps.Singleton<V>
/*     */     implements Char2ObjectSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final CharComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(char key, V value, CharComparator comparator) {
/* 230 */       super(key, value);
/* 231 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(char key, V value) {
/* 234 */       this(key, value, (CharComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(char k1, char k2) {
/* 238 */       return (this.comparator == null) ? Character.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public CharComparator comparator() {
/* 242 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Char2ObjectMap.Entry<V>> char2ObjectEntrySet() {
/* 247 */       if (this.entries == null)
/* 248 */         this.entries = (ObjectSet<Char2ObjectMap.Entry<V>>)ObjectSortedSets.singleton(new AbstractChar2ObjectMap.BasicEntry<>(this.key, this.value), 
/* 249 */             Char2ObjectSortedMaps.entryComparator(this.comparator)); 
/* 250 */       return (ObjectSortedSet<Char2ObjectMap.Entry<V>>)this.entries;
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
/* 261 */       return (ObjectSortedSet)char2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public CharSortedSet keySet() {
/* 265 */       if (this.keys == null)
/* 266 */         this.keys = CharSortedSets.singleton(this.key, this.comparator); 
/* 267 */       return (CharSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ObjectSortedMap<V> subMap(char from, char to) {
/* 272 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 273 */         return this; 
/* 274 */       return Char2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ObjectSortedMap<V> headMap(char to) {
/* 279 */       if (compare(this.key, to) < 0)
/* 280 */         return this; 
/* 281 */       return Char2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ObjectSortedMap<V> tailMap(char from) {
/* 286 */       if (compare(from, this.key) <= 0)
/* 287 */         return this; 
/* 288 */       return Char2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public char firstCharKey() {
/* 292 */       return this.key;
/*     */     }
/*     */     
/*     */     public char lastCharKey() {
/* 296 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ObjectSortedMap<V> headMap(Character oto) {
/* 306 */       return headMap(oto.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ObjectSortedMap<V> tailMap(Character ofrom) {
/* 316 */       return tailMap(ofrom.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ObjectSortedMap<V> subMap(Character ofrom, Character oto) {
/* 326 */       return subMap(ofrom.charValue(), oto.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character firstKey() {
/* 336 */       return Character.valueOf(firstCharKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character lastKey() {
/* 346 */       return Character.valueOf(lastCharKey());
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
/*     */   public static <V> Char2ObjectSortedMap<V> singleton(Character key, V value) {
/* 365 */     return new Singleton<>(key.charValue(), value);
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
/*     */   public static <V> Char2ObjectSortedMap<V> singleton(Character key, V value, CharComparator comparator) {
/* 385 */     return new Singleton<>(key.charValue(), value, comparator);
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
/*     */   public static <V> Char2ObjectSortedMap<V> singleton(char key, V value) {
/* 403 */     return new Singleton<>(key, value);
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
/*     */   public static <V> Char2ObjectSortedMap<V> singleton(char key, V value, CharComparator comparator) {
/* 423 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<V>
/*     */     extends Char2ObjectMaps.SynchronizedMap<V>
/*     */     implements Char2ObjectSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2ObjectSortedMap<V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Char2ObjectSortedMap<V> m, Object sync) {
/* 433 */       super(m, sync);
/* 434 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Char2ObjectSortedMap<V> m) {
/* 437 */       super(m);
/* 438 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public CharComparator comparator() {
/* 442 */       synchronized (this.sync) {
/* 443 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Char2ObjectMap.Entry<V>> char2ObjectEntrySet() {
/* 448 */       if (this.entries == null)
/* 449 */         this.entries = (ObjectSet<Char2ObjectMap.Entry<V>>)ObjectSortedSets.synchronize(this.sortedMap.char2ObjectEntrySet(), this.sync); 
/* 450 */       return (ObjectSortedSet<Char2ObjectMap.Entry<V>>)this.entries;
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
/* 461 */       return (ObjectSortedSet)char2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public CharSortedSet keySet() {
/* 465 */       if (this.keys == null)
/* 466 */         this.keys = CharSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 467 */       return (CharSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Char2ObjectSortedMap<V> subMap(char from, char to) {
/* 471 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Char2ObjectSortedMap<V> headMap(char to) {
/* 475 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Char2ObjectSortedMap<V> tailMap(char from) {
/* 479 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public char firstCharKey() {
/* 483 */       synchronized (this.sync) {
/* 484 */         return this.sortedMap.firstCharKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public char lastCharKey() {
/* 489 */       synchronized (this.sync) {
/* 490 */         return this.sortedMap.lastCharKey();
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
/* 501 */       synchronized (this.sync) {
/* 502 */         return this.sortedMap.firstKey();
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
/* 513 */       synchronized (this.sync) {
/* 514 */         return this.sortedMap.lastKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ObjectSortedMap<V> subMap(Character from, Character to) {
/* 525 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ObjectSortedMap<V> headMap(Character to) {
/* 535 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ObjectSortedMap<V> tailMap(Character from) {
/* 545 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
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
/*     */   public static <V> Char2ObjectSortedMap<V> synchronize(Char2ObjectSortedMap<V> m) {
/* 558 */     return new SynchronizedSortedMap<>(m);
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
/*     */   public static <V> Char2ObjectSortedMap<V> synchronize(Char2ObjectSortedMap<V> m, Object sync) {
/* 573 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<V>
/*     */     extends Char2ObjectMaps.UnmodifiableMap<V>
/*     */     implements Char2ObjectSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2ObjectSortedMap<V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Char2ObjectSortedMap<V> m) {
/* 583 */       super(m);
/* 584 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public CharComparator comparator() {
/* 588 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Char2ObjectMap.Entry<V>> char2ObjectEntrySet() {
/* 592 */       if (this.entries == null)
/* 593 */         this.entries = (ObjectSet<Char2ObjectMap.Entry<V>>)ObjectSortedSets.unmodifiable(this.sortedMap.char2ObjectEntrySet()); 
/* 594 */       return (ObjectSortedSet<Char2ObjectMap.Entry<V>>)this.entries;
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
/* 605 */       return (ObjectSortedSet)char2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public CharSortedSet keySet() {
/* 609 */       if (this.keys == null)
/* 610 */         this.keys = CharSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 611 */       return (CharSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Char2ObjectSortedMap<V> subMap(char from, char to) {
/* 615 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Char2ObjectSortedMap<V> headMap(char to) {
/* 619 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Char2ObjectSortedMap<V> tailMap(char from) {
/* 623 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public char firstCharKey() {
/* 627 */       return this.sortedMap.firstCharKey();
/*     */     }
/*     */     
/*     */     public char lastCharKey() {
/* 631 */       return this.sortedMap.lastCharKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character firstKey() {
/* 641 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character lastKey() {
/* 651 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ObjectSortedMap<V> subMap(Character from, Character to) {
/* 661 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ObjectSortedMap<V> headMap(Character to) {
/* 671 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ObjectSortedMap<V> tailMap(Character from) {
/* 681 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
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
/*     */   public static <V> Char2ObjectSortedMap<V> unmodifiable(Char2ObjectSortedMap<V> m) {
/* 694 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2ObjectSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */