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
/*     */ public final class Byte2ObjectSortedMaps
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
/*     */   public static <V> ObjectBidirectionalIterator<Byte2ObjectMap.Entry<V>> fastIterator(Byte2ObjectSortedMap<V> map) {
/*  60 */     ObjectSortedSet<Byte2ObjectMap.Entry<V>> entries = map.byte2ObjectEntrySet();
/*  61 */     return (entries instanceof Byte2ObjectSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Byte2ObjectSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static <V> ObjectBidirectionalIterable<Byte2ObjectMap.Entry<V>> fastIterable(Byte2ObjectSortedMap<V> map) {
/*  79 */     ObjectSortedSet<Byte2ObjectMap.Entry<V>> entries = map.byte2ObjectEntrySet();
/*     */     
/*  81 */     Objects.requireNonNull((Byte2ObjectSortedMap.FastSortedEntrySet)entries); return (entries instanceof Byte2ObjectSortedMap.FastSortedEntrySet) ? (Byte2ObjectSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  82 */       (ObjectBidirectionalIterable<Byte2ObjectMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<V>
/*     */     extends Byte2ObjectMaps.EmptyMap<V>
/*     */     implements Byte2ObjectSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ByteComparator comparator() {
/* 101 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Byte2ObjectMap.Entry<V>> byte2ObjectEntrySet() {
/* 106 */       return (ObjectSortedSet<Byte2ObjectMap.Entry<V>>)ObjectSortedSets.EMPTY_SET;
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
/* 117 */       return (ObjectSortedSet<Map.Entry<Byte, V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet keySet() {
/* 122 */       return ByteSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte2ObjectSortedMap<V> subMap(byte from, byte to) {
/* 127 */       return Byte2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte2ObjectSortedMap<V> headMap(byte to) {
/* 132 */       return Byte2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte2ObjectSortedMap<V> tailMap(byte from) {
/* 137 */       return Byte2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public byte firstByteKey() {
/* 141 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public byte lastByteKey() {
/* 145 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ObjectSortedMap<V> headMap(Byte oto) {
/* 155 */       return headMap(oto.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ObjectSortedMap<V> tailMap(Byte ofrom) {
/* 165 */       return tailMap(ofrom.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ObjectSortedMap<V> subMap(Byte ofrom, Byte oto) {
/* 175 */       return subMap(ofrom.byteValue(), oto.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte firstKey() {
/* 185 */       return Byte.valueOf(firstByteKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte lastKey() {
/* 195 */       return Byte.valueOf(lastByteKey());
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
/*     */   public static <V> Byte2ObjectSortedMap<V> emptyMap() {
/* 213 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Byte2ObjectMaps.Singleton<V>
/*     */     implements Byte2ObjectSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final ByteComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(byte key, V value, ByteComparator comparator) {
/* 230 */       super(key, value);
/* 231 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(byte key, V value) {
/* 234 */       this(key, value, (ByteComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(byte k1, byte k2) {
/* 238 */       return (this.comparator == null) ? Byte.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public ByteComparator comparator() {
/* 242 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Byte2ObjectMap.Entry<V>> byte2ObjectEntrySet() {
/* 247 */       if (this.entries == null)
/* 248 */         this.entries = (ObjectSet<Byte2ObjectMap.Entry<V>>)ObjectSortedSets.singleton(new AbstractByte2ObjectMap.BasicEntry<>(this.key, this.value), 
/* 249 */             Byte2ObjectSortedMaps.entryComparator(this.comparator)); 
/* 250 */       return (ObjectSortedSet<Byte2ObjectMap.Entry<V>>)this.entries;
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
/* 261 */       return (ObjectSortedSet)byte2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public ByteSortedSet keySet() {
/* 265 */       if (this.keys == null)
/* 266 */         this.keys = ByteSortedSets.singleton(this.key, this.comparator); 
/* 267 */       return (ByteSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte2ObjectSortedMap<V> subMap(byte from, byte to) {
/* 272 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 273 */         return this; 
/* 274 */       return Byte2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte2ObjectSortedMap<V> headMap(byte to) {
/* 279 */       if (compare(this.key, to) < 0)
/* 280 */         return this; 
/* 281 */       return Byte2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte2ObjectSortedMap<V> tailMap(byte from) {
/* 286 */       if (compare(from, this.key) <= 0)
/* 287 */         return this; 
/* 288 */       return Byte2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public byte firstByteKey() {
/* 292 */       return this.key;
/*     */     }
/*     */     
/*     */     public byte lastByteKey() {
/* 296 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ObjectSortedMap<V> headMap(Byte oto) {
/* 306 */       return headMap(oto.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ObjectSortedMap<V> tailMap(Byte ofrom) {
/* 316 */       return tailMap(ofrom.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ObjectSortedMap<V> subMap(Byte ofrom, Byte oto) {
/* 326 */       return subMap(ofrom.byteValue(), oto.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte firstKey() {
/* 336 */       return Byte.valueOf(firstByteKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte lastKey() {
/* 346 */       return Byte.valueOf(lastByteKey());
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
/*     */   public static <V> Byte2ObjectSortedMap<V> singleton(Byte key, V value) {
/* 365 */     return new Singleton<>(key.byteValue(), value);
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
/*     */   public static <V> Byte2ObjectSortedMap<V> singleton(Byte key, V value, ByteComparator comparator) {
/* 385 */     return new Singleton<>(key.byteValue(), value, comparator);
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
/*     */   public static <V> Byte2ObjectSortedMap<V> singleton(byte key, V value) {
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
/*     */   public static <V> Byte2ObjectSortedMap<V> singleton(byte key, V value, ByteComparator comparator) {
/* 423 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<V>
/*     */     extends Byte2ObjectMaps.SynchronizedMap<V>
/*     */     implements Byte2ObjectSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Byte2ObjectSortedMap<V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Byte2ObjectSortedMap<V> m, Object sync) {
/* 433 */       super(m, sync);
/* 434 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Byte2ObjectSortedMap<V> m) {
/* 437 */       super(m);
/* 438 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public ByteComparator comparator() {
/* 442 */       synchronized (this.sync) {
/* 443 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Byte2ObjectMap.Entry<V>> byte2ObjectEntrySet() {
/* 448 */       if (this.entries == null)
/* 449 */         this.entries = (ObjectSet<Byte2ObjectMap.Entry<V>>)ObjectSortedSets.synchronize(this.sortedMap.byte2ObjectEntrySet(), this.sync); 
/* 450 */       return (ObjectSortedSet<Byte2ObjectMap.Entry<V>>)this.entries;
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
/* 461 */       return (ObjectSortedSet)byte2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public ByteSortedSet keySet() {
/* 465 */       if (this.keys == null)
/* 466 */         this.keys = ByteSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 467 */       return (ByteSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Byte2ObjectSortedMap<V> subMap(byte from, byte to) {
/* 471 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Byte2ObjectSortedMap<V> headMap(byte to) {
/* 475 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Byte2ObjectSortedMap<V> tailMap(byte from) {
/* 479 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public byte firstByteKey() {
/* 483 */       synchronized (this.sync) {
/* 484 */         return this.sortedMap.firstByteKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public byte lastByteKey() {
/* 489 */       synchronized (this.sync) {
/* 490 */         return this.sortedMap.lastByteKey();
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
/*     */     public Byte lastKey() {
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
/*     */     public Byte2ObjectSortedMap<V> subMap(Byte from, Byte to) {
/* 525 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ObjectSortedMap<V> headMap(Byte to) {
/* 535 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ObjectSortedMap<V> tailMap(Byte from) {
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
/*     */   public static <V> Byte2ObjectSortedMap<V> synchronize(Byte2ObjectSortedMap<V> m) {
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
/*     */   public static <V> Byte2ObjectSortedMap<V> synchronize(Byte2ObjectSortedMap<V> m, Object sync) {
/* 573 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<V>
/*     */     extends Byte2ObjectMaps.UnmodifiableMap<V>
/*     */     implements Byte2ObjectSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Byte2ObjectSortedMap<V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Byte2ObjectSortedMap<V> m) {
/* 583 */       super(m);
/* 584 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public ByteComparator comparator() {
/* 588 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Byte2ObjectMap.Entry<V>> byte2ObjectEntrySet() {
/* 592 */       if (this.entries == null)
/* 593 */         this.entries = (ObjectSet<Byte2ObjectMap.Entry<V>>)ObjectSortedSets.unmodifiable(this.sortedMap.byte2ObjectEntrySet()); 
/* 594 */       return (ObjectSortedSet<Byte2ObjectMap.Entry<V>>)this.entries;
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
/* 605 */       return (ObjectSortedSet)byte2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public ByteSortedSet keySet() {
/* 609 */       if (this.keys == null)
/* 610 */         this.keys = ByteSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 611 */       return (ByteSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Byte2ObjectSortedMap<V> subMap(byte from, byte to) {
/* 615 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Byte2ObjectSortedMap<V> headMap(byte to) {
/* 619 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Byte2ObjectSortedMap<V> tailMap(byte from) {
/* 623 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public byte firstByteKey() {
/* 627 */       return this.sortedMap.firstByteKey();
/*     */     }
/*     */     
/*     */     public byte lastByteKey() {
/* 631 */       return this.sortedMap.lastByteKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte firstKey() {
/* 641 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte lastKey() {
/* 651 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ObjectSortedMap<V> subMap(Byte from, Byte to) {
/* 661 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ObjectSortedMap<V> headMap(Byte to) {
/* 671 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2ObjectSortedMap<V> tailMap(Byte from) {
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
/*     */   public static <V> Byte2ObjectSortedMap<V> unmodifiable(Byte2ObjectSortedMap<V> m) {
/* 694 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2ObjectSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */