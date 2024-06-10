/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollections;
/*     */ import it.unimi.dsi.fastutil.longs.LongSets;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterable;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSets;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.IntToLongFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Byte2LongMaps
/*     */ {
/*     */   public static ObjectIterator<Byte2LongMap.Entry> fastIterator(Byte2LongMap map) {
/*  49 */     ObjectSet<Byte2LongMap.Entry> entries = map.byte2LongEntrySet();
/*  50 */     return (entries instanceof Byte2LongMap.FastEntrySet) ? (
/*  51 */       (Byte2LongMap.FastEntrySet)entries).fastIterator() : 
/*  52 */       entries.iterator();
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
/*     */   public static void fastForEach(Byte2LongMap map, Consumer<? super Byte2LongMap.Entry> consumer) {
/*  70 */     ObjectSet<Byte2LongMap.Entry> entries = map.byte2LongEntrySet();
/*  71 */     if (entries instanceof Byte2LongMap.FastEntrySet) {
/*  72 */       ((Byte2LongMap.FastEntrySet)entries).fastForEach(consumer);
/*     */     } else {
/*  74 */       entries.forEach(consumer);
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
/*     */   public static ObjectIterable<Byte2LongMap.Entry> fastIterable(Byte2LongMap map) {
/*  90 */     final ObjectSet<Byte2LongMap.Entry> entries = map.byte2LongEntrySet();
/*  91 */     return (entries instanceof Byte2LongMap.FastEntrySet) ? new ObjectIterable<Byte2LongMap.Entry>() {
/*     */         public ObjectIterator<Byte2LongMap.Entry> iterator() {
/*  93 */           return ((Byte2LongMap.FastEntrySet)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Byte2LongMap.Entry> consumer) {
/*  96 */           ((Byte2LongMap.FastEntrySet)entries).fastForEach(consumer);
/*     */         }
/*  98 */       } : (ObjectIterable<Byte2LongMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap
/*     */     extends Byte2LongFunctions.EmptyFunction
/*     */     implements Byte2LongMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(long v) {
/* 117 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 127 */       return false;
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Byte, ? extends Long> m) {
/* 131 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Byte2LongMap.Entry> byte2LongEntrySet() {
/* 136 */       return (ObjectSet<Byte2LongMap.Entry>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSet keySet() {
/* 141 */       return ByteSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongCollection values() {
/* 146 */       return (LongCollection)LongSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 150 */       return Byte2LongMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 154 */       return true;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 158 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 162 */       if (!(o instanceof Map))
/* 163 */         return false; 
/* 164 */       return ((Map)o).isEmpty();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 168 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 175 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton
/*     */     extends Byte2LongFunctions.Singleton
/*     */     implements Byte2LongMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Byte2LongMap.Entry> entries;
/*     */     
/*     */     protected transient ByteSet keys;
/*     */     
/*     */     protected transient LongCollection values;
/*     */ 
/*     */     
/*     */     protected Singleton(byte key, long value) {
/* 193 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(long v) {
/* 197 */       return (this.value == v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 207 */       return (((Long)ov).longValue() == this.value);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Byte, ? extends Long> m) {
/* 211 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Byte2LongMap.Entry> byte2LongEntrySet() {
/* 215 */       if (this.entries == null)
/* 216 */         this.entries = ObjectSets.singleton(new AbstractByte2LongMap.BasicEntry(this.key, this.value)); 
/* 217 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<Byte, Long>> entrySet() {
/* 228 */       return (ObjectSet)byte2LongEntrySet();
/*     */     }
/*     */     
/*     */     public ByteSet keySet() {
/* 232 */       if (this.keys == null)
/* 233 */         this.keys = ByteSets.singleton(this.key); 
/* 234 */       return this.keys;
/*     */     }
/*     */     
/*     */     public LongCollection values() {
/* 238 */       if (this.values == null)
/* 239 */         this.values = (LongCollection)LongSets.singleton(this.value); 
/* 240 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 244 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 248 */       return this.key ^ HashCommon.long2int(this.value);
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 252 */       if (o == this)
/* 253 */         return true; 
/* 254 */       if (!(o instanceof Map))
/* 255 */         return false; 
/* 256 */       Map<?, ?> m = (Map<?, ?>)o;
/* 257 */       if (m.size() != 1)
/* 258 */         return false; 
/* 259 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 263 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static Byte2LongMap singleton(byte key, long value) {
/* 282 */     return new Singleton(key, value);
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
/*     */   public static Byte2LongMap singleton(Byte key, Long value) {
/* 300 */     return new Singleton(key.byteValue(), value.longValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap
/*     */     extends Byte2LongFunctions.SynchronizedFunction
/*     */     implements Byte2LongMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Byte2LongMap map;
/*     */     protected transient ObjectSet<Byte2LongMap.Entry> entries;
/*     */     protected transient ByteSet keys;
/*     */     protected transient LongCollection values;
/*     */     
/*     */     protected SynchronizedMap(Byte2LongMap m, Object sync) {
/* 313 */       super(m, sync);
/* 314 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Byte2LongMap m) {
/* 317 */       super(m);
/* 318 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(long v) {
/* 322 */       synchronized (this.sync) {
/* 323 */         return this.map.containsValue(v);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 334 */       synchronized (this.sync) {
/* 335 */         return this.map.containsValue(ov);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Byte, ? extends Long> m) {
/* 340 */       synchronized (this.sync) {
/* 341 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Byte2LongMap.Entry> byte2LongEntrySet() {
/* 346 */       synchronized (this.sync) {
/* 347 */         if (this.entries == null)
/* 348 */           this.entries = ObjectSets.synchronize(this.map.byte2LongEntrySet(), this.sync); 
/* 349 */         return this.entries;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<Byte, Long>> entrySet() {
/* 361 */       return (ObjectSet)byte2LongEntrySet();
/*     */     }
/*     */     
/*     */     public ByteSet keySet() {
/* 365 */       synchronized (this.sync) {
/* 366 */         if (this.keys == null)
/* 367 */           this.keys = ByteSets.synchronize(this.map.keySet(), this.sync); 
/* 368 */         return this.keys;
/*     */       } 
/*     */     }
/*     */     
/*     */     public LongCollection values() {
/* 373 */       synchronized (this.sync) {
/* 374 */         if (this.values == null)
/* 375 */           return LongCollections.synchronize(this.map.values(), this.sync); 
/* 376 */         return this.values;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 381 */       synchronized (this.sync) {
/* 382 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 387 */       synchronized (this.sync) {
/* 388 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 393 */       if (o == this)
/* 394 */         return true; 
/* 395 */       synchronized (this.sync) {
/* 396 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 400 */       synchronized (this.sync) {
/* 401 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long getOrDefault(byte key, long defaultValue) {
/* 407 */       synchronized (this.sync) {
/* 408 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Byte, ? super Long> action) {
/* 413 */       synchronized (this.sync) {
/* 414 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Byte, ? super Long, ? extends Long> function) {
/* 420 */       synchronized (this.sync) {
/* 421 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long putIfAbsent(byte key, long value) {
/* 426 */       synchronized (this.sync) {
/* 427 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(byte key, long value) {
/* 432 */       synchronized (this.sync) {
/* 433 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long replace(byte key, long value) {
/* 438 */       synchronized (this.sync) {
/* 439 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(byte key, long oldValue, long newValue) {
/* 444 */       synchronized (this.sync) {
/* 445 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long computeIfAbsent(byte key, IntToLongFunction mappingFunction) {
/* 450 */       synchronized (this.sync) {
/* 451 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfAbsentNullable(byte key, IntFunction<? extends Long> mappingFunction) {
/* 457 */       synchronized (this.sync) {
/* 458 */         return this.map.computeIfAbsentNullable(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long computeIfAbsentPartial(byte key, Byte2LongFunction mappingFunction) {
/* 463 */       synchronized (this.sync) {
/* 464 */         return this.map.computeIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfPresent(byte key, BiFunction<? super Byte, ? super Long, ? extends Long> remappingFunction) {
/* 470 */       synchronized (this.sync) {
/* 471 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long compute(byte key, BiFunction<? super Byte, ? super Long, ? extends Long> remappingFunction) {
/* 477 */       synchronized (this.sync) {
/* 478 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long merge(byte key, long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 484 */       synchronized (this.sync) {
/* 485 */         return this.map.merge(key, value, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long getOrDefault(Object key, Long defaultValue) {
/* 496 */       synchronized (this.sync) {
/* 497 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 508 */       synchronized (this.sync) {
/* 509 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long replace(Byte key, Long value) {
/* 520 */       synchronized (this.sync) {
/* 521 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Byte key, Long oldValue, Long newValue) {
/* 532 */       synchronized (this.sync) {
/* 533 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long putIfAbsent(Byte key, Long value) {
/* 544 */       synchronized (this.sync) {
/* 545 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long computeIfAbsent(Byte key, Function<? super Byte, ? extends Long> mappingFunction) {
/* 557 */       synchronized (this.sync) {
/* 558 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long computeIfPresent(Byte key, BiFunction<? super Byte, ? super Long, ? extends Long> remappingFunction) {
/* 570 */       synchronized (this.sync) {
/* 571 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long compute(Byte key, BiFunction<? super Byte, ? super Long, ? extends Long> remappingFunction) {
/* 583 */       synchronized (this.sync) {
/* 584 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long merge(Byte key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 596 */       synchronized (this.sync) {
/* 597 */         return this.map.merge(key, value, remappingFunction);
/*     */       } 
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
/*     */   public static Byte2LongMap synchronize(Byte2LongMap m) {
/* 611 */     return new SynchronizedMap(m);
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
/*     */   public static Byte2LongMap synchronize(Byte2LongMap m, Object sync) {
/* 625 */     return new SynchronizedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap
/*     */     extends Byte2LongFunctions.UnmodifiableFunction
/*     */     implements Byte2LongMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Byte2LongMap map;
/*     */     protected transient ObjectSet<Byte2LongMap.Entry> entries;
/*     */     protected transient ByteSet keys;
/*     */     protected transient LongCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Byte2LongMap m) {
/* 638 */       super(m);
/* 639 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(long v) {
/* 643 */       return this.map.containsValue(v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 653 */       return this.map.containsValue(ov);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Byte, ? extends Long> m) {
/* 657 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Byte2LongMap.Entry> byte2LongEntrySet() {
/* 661 */       if (this.entries == null)
/* 662 */         this.entries = ObjectSets.unmodifiable(this.map.byte2LongEntrySet()); 
/* 663 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<Byte, Long>> entrySet() {
/* 674 */       return (ObjectSet)byte2LongEntrySet();
/*     */     }
/*     */     
/*     */     public ByteSet keySet() {
/* 678 */       if (this.keys == null)
/* 679 */         this.keys = ByteSets.unmodifiable(this.map.keySet()); 
/* 680 */       return this.keys;
/*     */     }
/*     */     
/*     */     public LongCollection values() {
/* 684 */       if (this.values == null)
/* 685 */         return LongCollections.unmodifiable(this.map.values()); 
/* 686 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 690 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 694 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 698 */       if (o == this)
/* 699 */         return true; 
/* 700 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public long getOrDefault(byte key, long defaultValue) {
/* 705 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Byte, ? super Long> action) {
/* 709 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Byte, ? super Long, ? extends Long> function) {
/* 714 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public long putIfAbsent(byte key, long value) {
/* 718 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(byte key, long value) {
/* 722 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public long replace(byte key, long value) {
/* 726 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(byte key, long oldValue, long newValue) {
/* 730 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public long computeIfAbsent(byte key, IntToLongFunction mappingFunction) {
/* 734 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfAbsentNullable(byte key, IntFunction<? extends Long> mappingFunction) {
/* 739 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public long computeIfAbsentPartial(byte key, Byte2LongFunction mappingFunction) {
/* 743 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfPresent(byte key, BiFunction<? super Byte, ? super Long, ? extends Long> remappingFunction) {
/* 748 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long compute(byte key, BiFunction<? super Byte, ? super Long, ? extends Long> remappingFunction) {
/* 753 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long merge(byte key, long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 758 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long getOrDefault(Object key, Long defaultValue) {
/* 768 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 778 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long replace(Byte key, Long value) {
/* 788 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Byte key, Long oldValue, Long newValue) {
/* 798 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long putIfAbsent(Byte key, Long value) {
/* 808 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long computeIfAbsent(Byte key, Function<? super Byte, ? extends Long> mappingFunction) {
/* 819 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long computeIfPresent(Byte key, BiFunction<? super Byte, ? super Long, ? extends Long> remappingFunction) {
/* 830 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long compute(Byte key, BiFunction<? super Byte, ? super Long, ? extends Long> remappingFunction) {
/* 841 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long merge(Byte key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 852 */       throw new UnsupportedOperationException();
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
/*     */   public static Byte2LongMap unmodifiable(Byte2LongMap m) {
/* 865 */     return new UnmodifiableMap(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2LongMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */