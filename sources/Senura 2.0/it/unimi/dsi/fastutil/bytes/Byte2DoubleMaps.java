/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleCollections;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleSets;
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
/*     */ import java.util.function.IntToDoubleFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Byte2DoubleMaps
/*     */ {
/*     */   public static ObjectIterator<Byte2DoubleMap.Entry> fastIterator(Byte2DoubleMap map) {
/*  49 */     ObjectSet<Byte2DoubleMap.Entry> entries = map.byte2DoubleEntrySet();
/*  50 */     return (entries instanceof Byte2DoubleMap.FastEntrySet) ? (
/*  51 */       (Byte2DoubleMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static void fastForEach(Byte2DoubleMap map, Consumer<? super Byte2DoubleMap.Entry> consumer) {
/*  70 */     ObjectSet<Byte2DoubleMap.Entry> entries = map.byte2DoubleEntrySet();
/*  71 */     if (entries instanceof Byte2DoubleMap.FastEntrySet) {
/*  72 */       ((Byte2DoubleMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static ObjectIterable<Byte2DoubleMap.Entry> fastIterable(Byte2DoubleMap map) {
/*  90 */     final ObjectSet<Byte2DoubleMap.Entry> entries = map.byte2DoubleEntrySet();
/*  91 */     return (entries instanceof Byte2DoubleMap.FastEntrySet) ? new ObjectIterable<Byte2DoubleMap.Entry>() {
/*     */         public ObjectIterator<Byte2DoubleMap.Entry> iterator() {
/*  93 */           return ((Byte2DoubleMap.FastEntrySet)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Byte2DoubleMap.Entry> consumer) {
/*  96 */           ((Byte2DoubleMap.FastEntrySet)entries).fastForEach(consumer);
/*     */         }
/*  98 */       } : (ObjectIterable<Byte2DoubleMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap
/*     */     extends Byte2DoubleFunctions.EmptyFunction
/*     */     implements Byte2DoubleMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(double v) {
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
/*     */     public void putAll(Map<? extends Byte, ? extends Double> m) {
/* 131 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Byte2DoubleMap.Entry> byte2DoubleEntrySet() {
/* 136 */       return (ObjectSet<Byte2DoubleMap.Entry>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSet keySet() {
/* 141 */       return ByteSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleCollection values() {
/* 146 */       return (DoubleCollection)DoubleSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 150 */       return Byte2DoubleMaps.EMPTY_MAP;
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
/*     */     extends Byte2DoubleFunctions.Singleton
/*     */     implements Byte2DoubleMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Byte2DoubleMap.Entry> entries;
/*     */     
/*     */     protected transient ByteSet keys;
/*     */     
/*     */     protected transient DoubleCollection values;
/*     */ 
/*     */     
/*     */     protected Singleton(byte key, double value) {
/* 193 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(double v) {
/* 197 */       return (Double.doubleToLongBits(this.value) == Double.doubleToLongBits(v));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 207 */       return (Double.doubleToLongBits(((Double)ov).doubleValue()) == Double.doubleToLongBits(this.value));
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Byte, ? extends Double> m) {
/* 211 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Byte2DoubleMap.Entry> byte2DoubleEntrySet() {
/* 215 */       if (this.entries == null)
/* 216 */         this.entries = ObjectSets.singleton(new AbstractByte2DoubleMap.BasicEntry(this.key, this.value)); 
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
/*     */     public ObjectSet<Map.Entry<Byte, Double>> entrySet() {
/* 228 */       return (ObjectSet)byte2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public ByteSet keySet() {
/* 232 */       if (this.keys == null)
/* 233 */         this.keys = ByteSets.singleton(this.key); 
/* 234 */       return this.keys;
/*     */     }
/*     */     
/*     */     public DoubleCollection values() {
/* 238 */       if (this.values == null)
/* 239 */         this.values = (DoubleCollection)DoubleSets.singleton(this.value); 
/* 240 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 244 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 248 */       return this.key ^ HashCommon.double2int(this.value);
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
/*     */   public static Byte2DoubleMap singleton(byte key, double value) {
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
/*     */   public static Byte2DoubleMap singleton(Byte key, Double value) {
/* 300 */     return new Singleton(key.byteValue(), value.doubleValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap
/*     */     extends Byte2DoubleFunctions.SynchronizedFunction
/*     */     implements Byte2DoubleMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Byte2DoubleMap map;
/*     */     protected transient ObjectSet<Byte2DoubleMap.Entry> entries;
/*     */     protected transient ByteSet keys;
/*     */     protected transient DoubleCollection values;
/*     */     
/*     */     protected SynchronizedMap(Byte2DoubleMap m, Object sync) {
/* 313 */       super(m, sync);
/* 314 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Byte2DoubleMap m) {
/* 317 */       super(m);
/* 318 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(double v) {
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
/*     */     public void putAll(Map<? extends Byte, ? extends Double> m) {
/* 340 */       synchronized (this.sync) {
/* 341 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Byte2DoubleMap.Entry> byte2DoubleEntrySet() {
/* 346 */       synchronized (this.sync) {
/* 347 */         if (this.entries == null)
/* 348 */           this.entries = ObjectSets.synchronize(this.map.byte2DoubleEntrySet(), this.sync); 
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
/*     */     public ObjectSet<Map.Entry<Byte, Double>> entrySet() {
/* 361 */       return (ObjectSet)byte2DoubleEntrySet();
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
/*     */     public DoubleCollection values() {
/* 373 */       synchronized (this.sync) {
/* 374 */         if (this.values == null)
/* 375 */           return DoubleCollections.synchronize(this.map.values(), this.sync); 
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
/*     */     public double getOrDefault(byte key, double defaultValue) {
/* 407 */       synchronized (this.sync) {
/* 408 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Byte, ? super Double> action) {
/* 413 */       synchronized (this.sync) {
/* 414 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Byte, ? super Double, ? extends Double> function) {
/* 420 */       synchronized (this.sync) {
/* 421 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public double putIfAbsent(byte key, double value) {
/* 426 */       synchronized (this.sync) {
/* 427 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(byte key, double value) {
/* 432 */       synchronized (this.sync) {
/* 433 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public double replace(byte key, double value) {
/* 438 */       synchronized (this.sync) {
/* 439 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(byte key, double oldValue, double newValue) {
/* 444 */       synchronized (this.sync) {
/* 445 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public double computeIfAbsent(byte key, IntToDoubleFunction mappingFunction) {
/* 450 */       synchronized (this.sync) {
/* 451 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeIfAbsentNullable(byte key, IntFunction<? extends Double> mappingFunction) {
/* 457 */       synchronized (this.sync) {
/* 458 */         return this.map.computeIfAbsentNullable(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */     
/*     */     public double computeIfAbsentPartial(byte key, Byte2DoubleFunction mappingFunction) {
/* 463 */       synchronized (this.sync) {
/* 464 */         return this.map.computeIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeIfPresent(byte key, BiFunction<? super Byte, ? super Double, ? extends Double> remappingFunction) {
/* 470 */       synchronized (this.sync) {
/* 471 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double compute(byte key, BiFunction<? super Byte, ? super Double, ? extends Double> remappingFunction) {
/* 477 */       synchronized (this.sync) {
/* 478 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double merge(byte key, double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
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
/*     */     public Double getOrDefault(Object key, Double defaultValue) {
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
/*     */     public Double replace(Byte key, Double value) {
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
/*     */     public boolean replace(Byte key, Double oldValue, Double newValue) {
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
/*     */     public Double putIfAbsent(Byte key, Double value) {
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
/*     */     public Double computeIfAbsent(Byte key, Function<? super Byte, ? extends Double> mappingFunction) {
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
/*     */     public Double computeIfPresent(Byte key, BiFunction<? super Byte, ? super Double, ? extends Double> remappingFunction) {
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
/*     */     public Double compute(Byte key, BiFunction<? super Byte, ? super Double, ? extends Double> remappingFunction) {
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
/*     */     public Double merge(Byte key, Double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
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
/*     */   public static Byte2DoubleMap synchronize(Byte2DoubleMap m) {
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
/*     */   public static Byte2DoubleMap synchronize(Byte2DoubleMap m, Object sync) {
/* 625 */     return new SynchronizedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap
/*     */     extends Byte2DoubleFunctions.UnmodifiableFunction
/*     */     implements Byte2DoubleMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Byte2DoubleMap map;
/*     */     protected transient ObjectSet<Byte2DoubleMap.Entry> entries;
/*     */     protected transient ByteSet keys;
/*     */     protected transient DoubleCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Byte2DoubleMap m) {
/* 638 */       super(m);
/* 639 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(double v) {
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
/*     */     public void putAll(Map<? extends Byte, ? extends Double> m) {
/* 657 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Byte2DoubleMap.Entry> byte2DoubleEntrySet() {
/* 661 */       if (this.entries == null)
/* 662 */         this.entries = ObjectSets.unmodifiable(this.map.byte2DoubleEntrySet()); 
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
/*     */     public ObjectSet<Map.Entry<Byte, Double>> entrySet() {
/* 674 */       return (ObjectSet)byte2DoubleEntrySet();
/*     */     }
/*     */     
/*     */     public ByteSet keySet() {
/* 678 */       if (this.keys == null)
/* 679 */         this.keys = ByteSets.unmodifiable(this.map.keySet()); 
/* 680 */       return this.keys;
/*     */     }
/*     */     
/*     */     public DoubleCollection values() {
/* 684 */       if (this.values == null)
/* 685 */         return DoubleCollections.unmodifiable(this.map.values()); 
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
/*     */     public double getOrDefault(byte key, double defaultValue) {
/* 705 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Byte, ? super Double> action) {
/* 709 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Byte, ? super Double, ? extends Double> function) {
/* 714 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public double putIfAbsent(byte key, double value) {
/* 718 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(byte key, double value) {
/* 722 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public double replace(byte key, double value) {
/* 726 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(byte key, double oldValue, double newValue) {
/* 730 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public double computeIfAbsent(byte key, IntToDoubleFunction mappingFunction) {
/* 734 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeIfAbsentNullable(byte key, IntFunction<? extends Double> mappingFunction) {
/* 739 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public double computeIfAbsentPartial(byte key, Byte2DoubleFunction mappingFunction) {
/* 743 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeIfPresent(byte key, BiFunction<? super Byte, ? super Double, ? extends Double> remappingFunction) {
/* 748 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double compute(byte key, BiFunction<? super Byte, ? super Double, ? extends Double> remappingFunction) {
/* 753 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double merge(byte key, double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 758 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double getOrDefault(Object key, Double defaultValue) {
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
/*     */     public Double replace(Byte key, Double value) {
/* 788 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Byte key, Double oldValue, Double newValue) {
/* 798 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double putIfAbsent(Byte key, Double value) {
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
/*     */     public Double computeIfAbsent(Byte key, Function<? super Byte, ? extends Double> mappingFunction) {
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
/*     */     public Double computeIfPresent(Byte key, BiFunction<? super Byte, ? super Double, ? extends Double> remappingFunction) {
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
/*     */     public Double compute(Byte key, BiFunction<? super Byte, ? super Double, ? extends Double> remappingFunction) {
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
/*     */     public Double merge(Byte key, Double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
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
/*     */   public static Byte2DoubleMap unmodifiable(Byte2DoubleMap m) {
/* 865 */     return new UnmodifiableMap(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2DoubleMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */