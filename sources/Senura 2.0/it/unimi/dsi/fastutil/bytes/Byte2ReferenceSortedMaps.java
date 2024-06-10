/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public final class Byte2ReferenceSortedMaps
/*     */ {
/*     */   public static Comparator<? super Map.Entry<Byte, ?>> entryComparator(ByteComparator comparator) {
/*  43 */     return (x, y) -> comparator.compare(((Byte)x.getKey()).byteValue(), ((Byte)y.getKey()).byteValue());
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
/*     */   public static <V> ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>> fastIterator(Byte2ReferenceSortedMap<V> map) {
/*  61 */     ObjectSortedSet<Byte2ReferenceMap.Entry<V>> entries = map.byte2ReferenceEntrySet();
/*  62 */     return (entries instanceof Byte2ReferenceSortedMap.FastSortedEntrySet) ? (
/*  63 */       (Byte2ReferenceSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static <V> ObjectBidirectionalIterable<Byte2ReferenceMap.Entry<V>> fastIterable(Byte2ReferenceSortedMap<V> map) {
/*  81 */     ObjectSortedSet<Byte2ReferenceMap.Entry<V>> entries = map.byte2ReferenceEntrySet();
/*     */     
/*  83 */     Objects.requireNonNull((Byte2ReferenceSortedMap.FastSortedEntrySet)entries); return (entries instanceof Byte2ReferenceSortedMap.FastSortedEntrySet) ? (Byte2ReferenceSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  84 */       (ObjectBidirectionalIterable<Byte2ReferenceMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<V>
/*     */     extends Byte2ReferenceMaps.EmptyMap<V>
/*     */     implements Byte2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ByteComparator comparator() {
/* 103 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Byte2ReferenceMap.Entry<V>> byte2ReferenceEntrySet() {
/* 108 */       return (ObjectSortedSet<Byte2ReferenceMap.Entry<V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Byte, V>> entrySet() {
/* 119 */       return (ObjectSortedSet<Map.Entry<Byte, V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet keySet() {
/* 124 */       return ByteSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte2ReferenceSortedMap<V> subMap(byte from, byte to) {
/* 129 */       return Byte2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte2ReferenceSortedMap<V> headMap(byte to) {
/* 134 */       return Byte2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte2ReferenceSortedMap<V> tailMap(byte from) {
/* 139 */       return Byte2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public byte firstByteKey() {
/* 143 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public byte lastByteKey() {
/* 147 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ReferenceSortedMap<V> headMap(Byte oto) {
/* 157 */       return headMap(oto.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ReferenceSortedMap<V> tailMap(Byte ofrom) {
/* 167 */       return tailMap(ofrom.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ReferenceSortedMap<V> subMap(Byte ofrom, Byte oto) {
/* 177 */       return subMap(ofrom.byteValue(), oto.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte firstKey() {
/* 187 */       return Byte.valueOf(firstByteKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte lastKey() {
/* 197 */       return Byte.valueOf(lastByteKey());
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
/*     */   public static <V> Byte2ReferenceSortedMap<V> emptyMap() {
/* 215 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Byte2ReferenceMaps.Singleton<V>
/*     */     implements Byte2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final ByteComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(byte key, V value, ByteComparator comparator) {
/* 232 */       super(key, value);
/* 233 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(byte key, V value) {
/* 236 */       this(key, value, (ByteComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(byte k1, byte k2) {
/* 240 */       return (this.comparator == null) ? Byte.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public ByteComparator comparator() {
/* 244 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Byte2ReferenceMap.Entry<V>> byte2ReferenceEntrySet() {
/* 249 */       if (this.entries == null)
/* 250 */         this.entries = (ObjectSet<Byte2ReferenceMap.Entry<V>>)ObjectSortedSets.singleton(new AbstractByte2ReferenceMap.BasicEntry<>(this.key, this.value), 
/* 251 */             Byte2ReferenceSortedMaps.entryComparator(this.comparator)); 
/* 252 */       return (ObjectSortedSet<Byte2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Byte, V>> entrySet() {
/* 263 */       return (ObjectSortedSet)byte2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public ByteSortedSet keySet() {
/* 267 */       if (this.keys == null)
/* 268 */         this.keys = ByteSortedSets.singleton(this.key, this.comparator); 
/* 269 */       return (ByteSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte2ReferenceSortedMap<V> subMap(byte from, byte to) {
/* 274 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 275 */         return this; 
/* 276 */       return Byte2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte2ReferenceSortedMap<V> headMap(byte to) {
/* 281 */       if (compare(this.key, to) < 0)
/* 282 */         return this; 
/* 283 */       return Byte2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte2ReferenceSortedMap<V> tailMap(byte from) {
/* 288 */       if (compare(from, this.key) <= 0)
/* 289 */         return this; 
/* 290 */       return Byte2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public byte firstByteKey() {
/* 294 */       return this.key;
/*     */     }
/*     */     
/*     */     public byte lastByteKey() {
/* 298 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ReferenceSortedMap<V> headMap(Byte oto) {
/* 308 */       return headMap(oto.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ReferenceSortedMap<V> tailMap(Byte ofrom) {
/* 318 */       return tailMap(ofrom.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ReferenceSortedMap<V> subMap(Byte ofrom, Byte oto) {
/* 328 */       return subMap(ofrom.byteValue(), oto.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte firstKey() {
/* 338 */       return Byte.valueOf(firstByteKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte lastKey() {
/* 348 */       return Byte.valueOf(lastByteKey());
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
/*     */   public static <V> Byte2ReferenceSortedMap<V> singleton(Byte key, V value) {
/* 367 */     return new Singleton<>(key.byteValue(), value);
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
/*     */   public static <V> Byte2ReferenceSortedMap<V> singleton(Byte key, V value, ByteComparator comparator) {
/* 387 */     return new Singleton<>(key.byteValue(), value, comparator);
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
/*     */   public static <V> Byte2ReferenceSortedMap<V> singleton(byte key, V value) {
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
/*     */   public static <V> Byte2ReferenceSortedMap<V> singleton(byte key, V value, ByteComparator comparator) {
/* 425 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<V>
/*     */     extends Byte2ReferenceMaps.SynchronizedMap<V>
/*     */     implements Byte2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Byte2ReferenceSortedMap<V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Byte2ReferenceSortedMap<V> m, Object sync) {
/* 435 */       super(m, sync);
/* 436 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Byte2ReferenceSortedMap<V> m) {
/* 439 */       super(m);
/* 440 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public ByteComparator comparator() {
/* 444 */       synchronized (this.sync) {
/* 445 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Byte2ReferenceMap.Entry<V>> byte2ReferenceEntrySet() {
/* 450 */       if (this.entries == null)
/* 451 */         this.entries = (ObjectSet<Byte2ReferenceMap.Entry<V>>)ObjectSortedSets.synchronize(this.sortedMap.byte2ReferenceEntrySet(), this.sync); 
/* 452 */       return (ObjectSortedSet<Byte2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Byte, V>> entrySet() {
/* 463 */       return (ObjectSortedSet)byte2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public ByteSortedSet keySet() {
/* 467 */       if (this.keys == null)
/* 468 */         this.keys = ByteSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 469 */       return (ByteSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Byte2ReferenceSortedMap<V> subMap(byte from, byte to) {
/* 473 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Byte2ReferenceSortedMap<V> headMap(byte to) {
/* 477 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Byte2ReferenceSortedMap<V> tailMap(byte from) {
/* 481 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public byte firstByteKey() {
/* 485 */       synchronized (this.sync) {
/* 486 */         return this.sortedMap.firstByteKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public byte lastByteKey() {
/* 491 */       synchronized (this.sync) {
/* 492 */         return this.sortedMap.lastByteKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte firstKey() {
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
/*     */     public Byte lastKey() {
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
/*     */     public Byte2ReferenceSortedMap<V> subMap(Byte from, Byte to) {
/* 527 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ReferenceSortedMap<V> headMap(Byte to) {
/* 537 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ReferenceSortedMap<V> tailMap(Byte from) {
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
/*     */   public static <V> Byte2ReferenceSortedMap<V> synchronize(Byte2ReferenceSortedMap<V> m) {
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
/*     */   public static <V> Byte2ReferenceSortedMap<V> synchronize(Byte2ReferenceSortedMap<V> m, Object sync) {
/* 575 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<V>
/*     */     extends Byte2ReferenceMaps.UnmodifiableMap<V>
/*     */     implements Byte2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Byte2ReferenceSortedMap<V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Byte2ReferenceSortedMap<V> m) {
/* 585 */       super(m);
/* 586 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public ByteComparator comparator() {
/* 590 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Byte2ReferenceMap.Entry<V>> byte2ReferenceEntrySet() {
/* 594 */       if (this.entries == null)
/* 595 */         this.entries = (ObjectSet<Byte2ReferenceMap.Entry<V>>)ObjectSortedSets.unmodifiable(this.sortedMap.byte2ReferenceEntrySet()); 
/* 596 */       return (ObjectSortedSet<Byte2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Byte, V>> entrySet() {
/* 607 */       return (ObjectSortedSet)byte2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public ByteSortedSet keySet() {
/* 611 */       if (this.keys == null)
/* 612 */         this.keys = ByteSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 613 */       return (ByteSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Byte2ReferenceSortedMap<V> subMap(byte from, byte to) {
/* 617 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Byte2ReferenceSortedMap<V> headMap(byte to) {
/* 621 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Byte2ReferenceSortedMap<V> tailMap(byte from) {
/* 625 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public byte firstByteKey() {
/* 629 */       return this.sortedMap.firstByteKey();
/*     */     }
/*     */     
/*     */     public byte lastByteKey() {
/* 633 */       return this.sortedMap.lastByteKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte firstKey() {
/* 643 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte lastKey() {
/* 653 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ReferenceSortedMap<V> subMap(Byte from, Byte to) {
/* 663 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ReferenceSortedMap<V> headMap(Byte to) {
/* 673 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ReferenceSortedMap<V> tailMap(Byte from) {
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
/*     */   public static <V> Byte2ReferenceSortedMap<V> unmodifiable(Byte2ReferenceSortedMap<V> m) {
/* 696 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2ReferenceSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */