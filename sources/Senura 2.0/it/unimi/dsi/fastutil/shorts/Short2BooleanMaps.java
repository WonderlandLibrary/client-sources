/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollections;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanSets;
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
/*     */ import java.util.function.IntPredicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Short2BooleanMaps
/*     */ {
/*     */   public static ObjectIterator<Short2BooleanMap.Entry> fastIterator(Short2BooleanMap map) {
/*  49 */     ObjectSet<Short2BooleanMap.Entry> entries = map.short2BooleanEntrySet();
/*  50 */     return (entries instanceof Short2BooleanMap.FastEntrySet) ? (
/*  51 */       (Short2BooleanMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static void fastForEach(Short2BooleanMap map, Consumer<? super Short2BooleanMap.Entry> consumer) {
/*  70 */     ObjectSet<Short2BooleanMap.Entry> entries = map.short2BooleanEntrySet();
/*  71 */     if (entries instanceof Short2BooleanMap.FastEntrySet) {
/*  72 */       ((Short2BooleanMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static ObjectIterable<Short2BooleanMap.Entry> fastIterable(Short2BooleanMap map) {
/*  90 */     final ObjectSet<Short2BooleanMap.Entry> entries = map.short2BooleanEntrySet();
/*  91 */     return (entries instanceof Short2BooleanMap.FastEntrySet) ? new ObjectIterable<Short2BooleanMap.Entry>() {
/*     */         public ObjectIterator<Short2BooleanMap.Entry> iterator() {
/*  93 */           return ((Short2BooleanMap.FastEntrySet)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Short2BooleanMap.Entry> consumer) {
/*  96 */           ((Short2BooleanMap.FastEntrySet)entries).fastForEach(consumer);
/*     */         }
/*  98 */       } : (ObjectIterable<Short2BooleanMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap
/*     */     extends Short2BooleanFunctions.EmptyFunction
/*     */     implements Short2BooleanMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(boolean v) {
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
/*     */     public void putAll(Map<? extends Short, ? extends Boolean> m) {
/* 131 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Short2BooleanMap.Entry> short2BooleanEntrySet() {
/* 136 */       return (ObjectSet<Short2BooleanMap.Entry>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSet keySet() {
/* 141 */       return ShortSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public BooleanCollection values() {
/* 146 */       return (BooleanCollection)BooleanSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 150 */       return Short2BooleanMaps.EMPTY_MAP;
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
/*     */     extends Short2BooleanFunctions.Singleton
/*     */     implements Short2BooleanMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Short2BooleanMap.Entry> entries;
/*     */     
/*     */     protected transient ShortSet keys;
/*     */     
/*     */     protected transient BooleanCollection values;
/*     */ 
/*     */     
/*     */     protected Singleton(short key, boolean value) {
/* 193 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(boolean v) {
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
/* 207 */       return (((Boolean)ov).booleanValue() == this.value);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Short, ? extends Boolean> m) {
/* 211 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Short2BooleanMap.Entry> short2BooleanEntrySet() {
/* 215 */       if (this.entries == null)
/* 216 */         this.entries = ObjectSets.singleton(new AbstractShort2BooleanMap.BasicEntry(this.key, this.value)); 
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
/*     */     public ObjectSet<Map.Entry<Short, Boolean>> entrySet() {
/* 228 */       return (ObjectSet)short2BooleanEntrySet();
/*     */     }
/*     */     
/*     */     public ShortSet keySet() {
/* 232 */       if (this.keys == null)
/* 233 */         this.keys = ShortSets.singleton(this.key); 
/* 234 */       return this.keys;
/*     */     }
/*     */     
/*     */     public BooleanCollection values() {
/* 238 */       if (this.values == null)
/* 239 */         this.values = (BooleanCollection)BooleanSets.singleton(this.value); 
/* 240 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 244 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 248 */       return this.key ^ (this.value ? 1231 : 1237);
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
/*     */   public static Short2BooleanMap singleton(short key, boolean value) {
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
/*     */   public static Short2BooleanMap singleton(Short key, Boolean value) {
/* 300 */     return new Singleton(key.shortValue(), value.booleanValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap
/*     */     extends Short2BooleanFunctions.SynchronizedFunction
/*     */     implements Short2BooleanMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2BooleanMap map;
/*     */     protected transient ObjectSet<Short2BooleanMap.Entry> entries;
/*     */     protected transient ShortSet keys;
/*     */     protected transient BooleanCollection values;
/*     */     
/*     */     protected SynchronizedMap(Short2BooleanMap m, Object sync) {
/* 313 */       super(m, sync);
/* 314 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Short2BooleanMap m) {
/* 317 */       super(m);
/* 318 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(boolean v) {
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
/*     */     public void putAll(Map<? extends Short, ? extends Boolean> m) {
/* 340 */       synchronized (this.sync) {
/* 341 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Short2BooleanMap.Entry> short2BooleanEntrySet() {
/* 346 */       synchronized (this.sync) {
/* 347 */         if (this.entries == null)
/* 348 */           this.entries = ObjectSets.synchronize(this.map.short2BooleanEntrySet(), this.sync); 
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
/*     */     public ObjectSet<Map.Entry<Short, Boolean>> entrySet() {
/* 361 */       return (ObjectSet)short2BooleanEntrySet();
/*     */     }
/*     */     
/*     */     public ShortSet keySet() {
/* 365 */       synchronized (this.sync) {
/* 366 */         if (this.keys == null)
/* 367 */           this.keys = ShortSets.synchronize(this.map.keySet(), this.sync); 
/* 368 */         return this.keys;
/*     */       } 
/*     */     }
/*     */     
/*     */     public BooleanCollection values() {
/* 373 */       synchronized (this.sync) {
/* 374 */         if (this.values == null)
/* 375 */           return BooleanCollections.synchronize(this.map.values(), this.sync); 
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
/*     */     public boolean getOrDefault(short key, boolean defaultValue) {
/* 407 */       synchronized (this.sync) {
/* 408 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Short, ? super Boolean> action) {
/* 413 */       synchronized (this.sync) {
/* 414 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Short, ? super Boolean, ? extends Boolean> function) {
/* 420 */       synchronized (this.sync) {
/* 421 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean putIfAbsent(short key, boolean value) {
/* 426 */       synchronized (this.sync) {
/* 427 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(short key, boolean value) {
/* 432 */       synchronized (this.sync) {
/* 433 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(short key, boolean value) {
/* 438 */       synchronized (this.sync) {
/* 439 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(short key, boolean oldValue, boolean newValue) {
/* 444 */       synchronized (this.sync) {
/* 445 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean computeIfAbsent(short key, IntPredicate mappingFunction) {
/* 450 */       synchronized (this.sync) {
/* 451 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean computeIfAbsentNullable(short key, IntFunction<? extends Boolean> mappingFunction) {
/* 457 */       synchronized (this.sync) {
/* 458 */         return this.map.computeIfAbsentNullable(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean computeIfAbsentPartial(short key, Short2BooleanFunction mappingFunction) {
/* 463 */       synchronized (this.sync) {
/* 464 */         return this.map.computeIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean computeIfPresent(short key, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 470 */       synchronized (this.sync) {
/* 471 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean compute(short key, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 477 */       synchronized (this.sync) {
/* 478 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean merge(short key, boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
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
/*     */     public Boolean getOrDefault(Object key, Boolean defaultValue) {
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
/*     */     public Boolean replace(Short key, Boolean value) {
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
/*     */     public boolean replace(Short key, Boolean oldValue, Boolean newValue) {
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
/*     */     public Boolean putIfAbsent(Short key, Boolean value) {
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
/*     */     public Boolean computeIfAbsent(Short key, Function<? super Short, ? extends Boolean> mappingFunction) {
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
/*     */     public Boolean computeIfPresent(Short key, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
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
/*     */     public Boolean compute(Short key, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
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
/*     */     public Boolean merge(Short key, Boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
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
/*     */   public static Short2BooleanMap synchronize(Short2BooleanMap m) {
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
/*     */   public static Short2BooleanMap synchronize(Short2BooleanMap m, Object sync) {
/* 625 */     return new SynchronizedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap
/*     */     extends Short2BooleanFunctions.UnmodifiableFunction
/*     */     implements Short2BooleanMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2BooleanMap map;
/*     */     protected transient ObjectSet<Short2BooleanMap.Entry> entries;
/*     */     protected transient ShortSet keys;
/*     */     protected transient BooleanCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Short2BooleanMap m) {
/* 638 */       super(m);
/* 639 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(boolean v) {
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
/*     */     public void putAll(Map<? extends Short, ? extends Boolean> m) {
/* 657 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Short2BooleanMap.Entry> short2BooleanEntrySet() {
/* 661 */       if (this.entries == null)
/* 662 */         this.entries = ObjectSets.unmodifiable(this.map.short2BooleanEntrySet()); 
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
/*     */     public ObjectSet<Map.Entry<Short, Boolean>> entrySet() {
/* 674 */       return (ObjectSet)short2BooleanEntrySet();
/*     */     }
/*     */     
/*     */     public ShortSet keySet() {
/* 678 */       if (this.keys == null)
/* 679 */         this.keys = ShortSets.unmodifiable(this.map.keySet()); 
/* 680 */       return this.keys;
/*     */     }
/*     */     
/*     */     public BooleanCollection values() {
/* 684 */       if (this.values == null)
/* 685 */         return BooleanCollections.unmodifiable(this.map.values()); 
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
/*     */     public boolean getOrDefault(short key, boolean defaultValue) {
/* 705 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Short, ? super Boolean> action) {
/* 709 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Short, ? super Boolean, ? extends Boolean> function) {
/* 714 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean putIfAbsent(short key, boolean value) {
/* 718 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(short key, boolean value) {
/* 722 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(short key, boolean value) {
/* 726 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(short key, boolean oldValue, boolean newValue) {
/* 730 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean computeIfAbsent(short key, IntPredicate mappingFunction) {
/* 734 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean computeIfAbsentNullable(short key, IntFunction<? extends Boolean> mappingFunction) {
/* 739 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean computeIfAbsentPartial(short key, Short2BooleanFunction mappingFunction) {
/* 743 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean computeIfPresent(short key, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 748 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean compute(short key, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 753 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean merge(short key, boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 758 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean getOrDefault(Object key, Boolean defaultValue) {
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
/*     */     public Boolean replace(Short key, Boolean value) {
/* 788 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Short key, Boolean oldValue, Boolean newValue) {
/* 798 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Boolean putIfAbsent(Short key, Boolean value) {
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
/*     */     public Boolean computeIfAbsent(Short key, Function<? super Short, ? extends Boolean> mappingFunction) {
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
/*     */     public Boolean computeIfPresent(Short key, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
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
/*     */     public Boolean compute(Short key, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
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
/*     */     public Boolean merge(Short key, Boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
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
/*     */   public static Short2BooleanMap unmodifiable(Short2BooleanMap m) {
/* 865 */     return new UnmodifiableMap(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2BooleanMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */