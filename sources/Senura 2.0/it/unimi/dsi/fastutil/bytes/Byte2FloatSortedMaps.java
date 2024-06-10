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
/*     */ public final class Byte2FloatSortedMaps
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
/*     */   public static ObjectBidirectionalIterator<Byte2FloatMap.Entry> fastIterator(Byte2FloatSortedMap map) {
/*  60 */     ObjectSortedSet<Byte2FloatMap.Entry> entries = map.byte2FloatEntrySet();
/*  61 */     return (entries instanceof Byte2FloatSortedMap.FastSortedEntrySet) ? (
/*  62 */       (Byte2FloatSortedMap.FastSortedEntrySet)entries).fastIterator() : 
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
/*     */   public static ObjectBidirectionalIterable<Byte2FloatMap.Entry> fastIterable(Byte2FloatSortedMap map) {
/*  79 */     ObjectSortedSet<Byte2FloatMap.Entry> entries = map.byte2FloatEntrySet();
/*     */     
/*  81 */     Objects.requireNonNull((Byte2FloatSortedMap.FastSortedEntrySet)entries); return (entries instanceof Byte2FloatSortedMap.FastSortedEntrySet) ? (Byte2FloatSortedMap.FastSortedEntrySet)entries::fastIterator : 
/*  82 */       (ObjectBidirectionalIterable<Byte2FloatMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap
/*     */     extends Byte2FloatMaps.EmptyMap
/*     */     implements Byte2FloatSortedMap, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Byte2FloatMap.Entry> byte2FloatEntrySet() {
/* 106 */       return (ObjectSortedSet<Byte2FloatMap.Entry>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Byte, Float>> entrySet() {
/* 117 */       return (ObjectSortedSet<Map.Entry<Byte, Float>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSortedSet keySet() {
/* 122 */       return ByteSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte2FloatSortedMap subMap(byte from, byte to) {
/* 127 */       return Byte2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte2FloatSortedMap headMap(byte to) {
/* 132 */       return Byte2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte2FloatSortedMap tailMap(byte from) {
/* 137 */       return Byte2FloatSortedMaps.EMPTY_MAP;
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
/*     */     public Byte2FloatSortedMap headMap(Byte oto) {
/* 155 */       return headMap(oto.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2FloatSortedMap tailMap(Byte ofrom) {
/* 165 */       return tailMap(ofrom.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2FloatSortedMap subMap(Byte ofrom, Byte oto) {
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
/*     */   public static class Singleton
/*     */     extends Byte2FloatMaps.Singleton
/*     */     implements Byte2FloatSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     protected final ByteComparator comparator;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(byte key, float value, ByteComparator comparator) {
/* 218 */       super(key, value);
/* 219 */       this.comparator = comparator;
/*     */     }
/*     */     protected Singleton(byte key, float value) {
/* 222 */       this(key, value, (ByteComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(byte k1, byte k2) {
/* 226 */       return (this.comparator == null) ? Byte.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */     
/*     */     public ByteComparator comparator() {
/* 230 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Byte2FloatMap.Entry> byte2FloatEntrySet() {
/* 235 */       if (this.entries == null)
/* 236 */         this.entries = (ObjectSet<Byte2FloatMap.Entry>)ObjectSortedSets.singleton(new AbstractByte2FloatMap.BasicEntry(this.key, this.value), 
/* 237 */             Byte2FloatSortedMaps.entryComparator(this.comparator)); 
/* 238 */       return (ObjectSortedSet<Byte2FloatMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Byte, Float>> entrySet() {
/* 249 */       return (ObjectSortedSet)byte2FloatEntrySet();
/*     */     }
/*     */     
/*     */     public ByteSortedSet keySet() {
/* 253 */       if (this.keys == null)
/* 254 */         this.keys = ByteSortedSets.singleton(this.key, this.comparator); 
/* 255 */       return (ByteSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte2FloatSortedMap subMap(byte from, byte to) {
/* 260 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0)
/* 261 */         return this; 
/* 262 */       return Byte2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte2FloatSortedMap headMap(byte to) {
/* 267 */       if (compare(this.key, to) < 0)
/* 268 */         return this; 
/* 269 */       return Byte2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte2FloatSortedMap tailMap(byte from) {
/* 274 */       if (compare(from, this.key) <= 0)
/* 275 */         return this; 
/* 276 */       return Byte2FloatSortedMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public byte firstByteKey() {
/* 280 */       return this.key;
/*     */     }
/*     */     
/*     */     public byte lastByteKey() {
/* 284 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2FloatSortedMap headMap(Byte oto) {
/* 294 */       return headMap(oto.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2FloatSortedMap tailMap(Byte ofrom) {
/* 304 */       return tailMap(ofrom.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2FloatSortedMap subMap(Byte ofrom, Byte oto) {
/* 314 */       return subMap(ofrom.byteValue(), oto.byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte firstKey() {
/* 324 */       return Byte.valueOf(firstByteKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte lastKey() {
/* 334 */       return Byte.valueOf(lastByteKey());
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
/*     */   public static Byte2FloatSortedMap singleton(Byte key, Float value) {
/* 353 */     return new Singleton(key.byteValue(), value.floatValue());
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
/*     */   public static Byte2FloatSortedMap singleton(Byte key, Float value, ByteComparator comparator) {
/* 373 */     return new Singleton(key.byteValue(), value.floatValue(), comparator);
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
/*     */   public static Byte2FloatSortedMap singleton(byte key, float value) {
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
/*     */   public static Byte2FloatSortedMap singleton(byte key, float value, ByteComparator comparator) {
/* 411 */     return new Singleton(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap
/*     */     extends Byte2FloatMaps.SynchronizedMap
/*     */     implements Byte2FloatSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Byte2FloatSortedMap sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Byte2FloatSortedMap m, Object sync) {
/* 421 */       super(m, sync);
/* 422 */       this.sortedMap = m;
/*     */     }
/*     */     protected SynchronizedSortedMap(Byte2FloatSortedMap m) {
/* 425 */       super(m);
/* 426 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public ByteComparator comparator() {
/* 430 */       synchronized (this.sync) {
/* 431 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Byte2FloatMap.Entry> byte2FloatEntrySet() {
/* 436 */       if (this.entries == null)
/* 437 */         this.entries = (ObjectSet<Byte2FloatMap.Entry>)ObjectSortedSets.synchronize(this.sortedMap.byte2FloatEntrySet(), this.sync); 
/* 438 */       return (ObjectSortedSet<Byte2FloatMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Byte, Float>> entrySet() {
/* 449 */       return (ObjectSortedSet)byte2FloatEntrySet();
/*     */     }
/*     */     
/*     */     public ByteSortedSet keySet() {
/* 453 */       if (this.keys == null)
/* 454 */         this.keys = ByteSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 455 */       return (ByteSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Byte2FloatSortedMap subMap(byte from, byte to) {
/* 459 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */     
/*     */     public Byte2FloatSortedMap headMap(byte to) {
/* 463 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */     
/*     */     public Byte2FloatSortedMap tailMap(byte from) {
/* 467 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */     
/*     */     public byte firstByteKey() {
/* 471 */       synchronized (this.sync) {
/* 472 */         return this.sortedMap.firstByteKey();
/*     */       } 
/*     */     }
/*     */     
/*     */     public byte lastByteKey() {
/* 477 */       synchronized (this.sync) {
/* 478 */         return this.sortedMap.lastByteKey();
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
/*     */     public Byte lastKey() {
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
/*     */     public Byte2FloatSortedMap subMap(Byte from, Byte to) {
/* 513 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2FloatSortedMap headMap(Byte to) {
/* 523 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2FloatSortedMap tailMap(Byte from) {
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
/*     */   public static Byte2FloatSortedMap synchronize(Byte2FloatSortedMap m) {
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
/*     */   public static Byte2FloatSortedMap synchronize(Byte2FloatSortedMap m, Object sync) {
/* 561 */     return new SynchronizedSortedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap
/*     */     extends Byte2FloatMaps.UnmodifiableMap
/*     */     implements Byte2FloatSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Byte2FloatSortedMap sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Byte2FloatSortedMap m) {
/* 571 */       super(m);
/* 572 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     public ByteComparator comparator() {
/* 576 */       return this.sortedMap.comparator();
/*     */     }
/*     */     
/*     */     public ObjectSortedSet<Byte2FloatMap.Entry> byte2FloatEntrySet() {
/* 580 */       if (this.entries == null)
/* 581 */         this.entries = (ObjectSet<Byte2FloatMap.Entry>)ObjectSortedSets.unmodifiable(this.sortedMap.byte2FloatEntrySet()); 
/* 582 */       return (ObjectSortedSet<Byte2FloatMap.Entry>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Byte, Float>> entrySet() {
/* 593 */       return (ObjectSortedSet)byte2FloatEntrySet();
/*     */     }
/*     */     
/*     */     public ByteSortedSet keySet() {
/* 597 */       if (this.keys == null)
/* 598 */         this.keys = ByteSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 599 */       return (ByteSortedSet)this.keys;
/*     */     }
/*     */     
/*     */     public Byte2FloatSortedMap subMap(byte from, byte to) {
/* 603 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */     
/*     */     public Byte2FloatSortedMap headMap(byte to) {
/* 607 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */     
/*     */     public Byte2FloatSortedMap tailMap(byte from) {
/* 611 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */     
/*     */     public byte firstByteKey() {
/* 615 */       return this.sortedMap.firstByteKey();
/*     */     }
/*     */     
/*     */     public byte lastByteKey() {
/* 619 */       return this.sortedMap.lastByteKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte firstKey() {
/* 629 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte lastKey() {
/* 639 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2FloatSortedMap subMap(Byte from, Byte to) {
/* 649 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2FloatSortedMap headMap(Byte to) {
/* 659 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte2FloatSortedMap tailMap(Byte from) {
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
/*     */   public static Byte2FloatSortedMap unmodifiable(Byte2FloatSortedMap m) {
/* 682 */     return new UnmodifiableSortedMap(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2FloatSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */