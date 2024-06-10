/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterable;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSets;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceCollections;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceSets;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Byte2ReferenceMaps
/*     */ {
/*     */   public static <V> ObjectIterator<Byte2ReferenceMap.Entry<V>> fastIterator(Byte2ReferenceMap<V> map) {
/*  49 */     ObjectSet<Byte2ReferenceMap.Entry<V>> entries = map.byte2ReferenceEntrySet();
/*  50 */     return (entries instanceof Byte2ReferenceMap.FastEntrySet) ? (
/*  51 */       (Byte2ReferenceMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   
/*     */   public static <V> void fastForEach(Byte2ReferenceMap<V> map, Consumer<? super Byte2ReferenceMap.Entry<V>> consumer) {
/*  71 */     ObjectSet<Byte2ReferenceMap.Entry<V>> entries = map.byte2ReferenceEntrySet();
/*  72 */     if (entries instanceof Byte2ReferenceMap.FastEntrySet) {
/*  73 */       ((Byte2ReferenceMap.FastEntrySet)entries).fastForEach(consumer);
/*     */     } else {
/*  75 */       entries.forEach(consumer);
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
/*     */   public static <V> ObjectIterable<Byte2ReferenceMap.Entry<V>> fastIterable(Byte2ReferenceMap<V> map) {
/*  91 */     final ObjectSet<Byte2ReferenceMap.Entry<V>> entries = map.byte2ReferenceEntrySet();
/*  92 */     return (entries instanceof Byte2ReferenceMap.FastEntrySet) ? new ObjectIterable<Byte2ReferenceMap.Entry<V>>() {
/*     */         public ObjectIterator<Byte2ReferenceMap.Entry<V>> iterator() {
/*  94 */           return ((Byte2ReferenceMap.FastEntrySet<V>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Byte2ReferenceMap.Entry<V>> consumer) {
/*  97 */           ((Byte2ReferenceMap.FastEntrySet<V>)entries).fastForEach(consumer);
/*     */         }
/*  99 */       } : (ObjectIterable<Byte2ReferenceMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<V>
/*     */     extends Byte2ReferenceFunctions.EmptyFunction<V>
/*     */     implements Byte2ReferenceMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 118 */       return false;
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Byte, ? extends V> m) {
/* 122 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Byte2ReferenceMap.Entry<V>> byte2ReferenceEntrySet() {
/* 127 */       return (ObjectSet<Byte2ReferenceMap.Entry<V>>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSet keySet() {
/* 132 */       return ByteSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 137 */       return (ReferenceCollection<V>)ReferenceSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 141 */       return Byte2ReferenceMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 145 */       return true;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 149 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 153 */       if (!(o instanceof Map))
/* 154 */         return false; 
/* 155 */       return ((Map)o).isEmpty();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 159 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Byte2ReferenceMap<V> emptyMap() {
/* 177 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Byte2ReferenceFunctions.Singleton<V>
/*     */     implements Byte2ReferenceMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Byte2ReferenceMap.Entry<V>> entries;
/*     */     
/*     */     protected transient ByteSet keys;
/*     */     
/*     */     protected transient ReferenceCollection<V> values;
/*     */ 
/*     */     
/*     */     protected Singleton(byte key, V value) {
/* 196 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 200 */       return (this.value == v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Byte, ? extends V> m) {
/* 204 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Byte2ReferenceMap.Entry<V>> byte2ReferenceEntrySet() {
/* 208 */       if (this.entries == null)
/* 209 */         this.entries = ObjectSets.singleton(new AbstractByte2ReferenceMap.BasicEntry<>(this.key, this.value)); 
/* 210 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<Byte, V>> entrySet() {
/* 221 */       return (ObjectSet)byte2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public ByteSet keySet() {
/* 225 */       if (this.keys == null)
/* 226 */         this.keys = ByteSets.singleton(this.key); 
/* 227 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 231 */       if (this.values == null)
/* 232 */         this.values = (ReferenceCollection<V>)ReferenceSets.singleton(this.value); 
/* 233 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 237 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 241 */       return this.key ^ ((this.value == null) ? 0 : System.identityHashCode(this.value));
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 245 */       if (o == this)
/* 246 */         return true; 
/* 247 */       if (!(o instanceof Map))
/* 248 */         return false; 
/* 249 */       Map<?, ?> m = (Map<?, ?>)o;
/* 250 */       if (m.size() != 1)
/* 251 */         return false; 
/* 252 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 256 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static <V> Byte2ReferenceMap<V> singleton(byte key, V value) {
/* 275 */     return new Singleton<>(key, value);
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
/*     */   public static <V> Byte2ReferenceMap<V> singleton(Byte key, V value) {
/* 293 */     return new Singleton<>(key.byteValue(), value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<V>
/*     */     extends Byte2ReferenceFunctions.SynchronizedFunction<V>
/*     */     implements Byte2ReferenceMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Byte2ReferenceMap<V> map;
/*     */     protected transient ObjectSet<Byte2ReferenceMap.Entry<V>> entries;
/*     */     protected transient ByteSet keys;
/*     */     protected transient ReferenceCollection<V> values;
/*     */     
/*     */     protected SynchronizedMap(Byte2ReferenceMap<V> m, Object sync) {
/* 306 */       super(m, sync);
/* 307 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Byte2ReferenceMap<V> m) {
/* 310 */       super(m);
/* 311 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 315 */       synchronized (this.sync) {
/* 316 */         return this.map.containsValue(v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Byte, ? extends V> m) {
/* 321 */       synchronized (this.sync) {
/* 322 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Byte2ReferenceMap.Entry<V>> byte2ReferenceEntrySet() {
/* 327 */       synchronized (this.sync) {
/* 328 */         if (this.entries == null)
/* 329 */           this.entries = ObjectSets.synchronize(this.map.byte2ReferenceEntrySet(), this.sync); 
/* 330 */         return this.entries;
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
/*     */     public ObjectSet<Map.Entry<Byte, V>> entrySet() {
/* 342 */       return (ObjectSet)byte2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public ByteSet keySet() {
/* 346 */       synchronized (this.sync) {
/* 347 */         if (this.keys == null)
/* 348 */           this.keys = ByteSets.synchronize(this.map.keySet(), this.sync); 
/* 349 */         return this.keys;
/*     */       } 
/*     */     }
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 354 */       synchronized (this.sync) {
/* 355 */         if (this.values == null)
/* 356 */           return ReferenceCollections.synchronize(this.map.values(), this.sync); 
/* 357 */         return this.values;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 362 */       synchronized (this.sync) {
/* 363 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 368 */       synchronized (this.sync) {
/* 369 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 374 */       if (o == this)
/* 375 */         return true; 
/* 376 */       synchronized (this.sync) {
/* 377 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 381 */       synchronized (this.sync) {
/* 382 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(byte key, V defaultValue) {
/* 388 */       synchronized (this.sync) {
/* 389 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Byte, ? super V> action) {
/* 394 */       synchronized (this.sync) {
/* 395 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super Byte, ? super V, ? extends V> function) {
/* 400 */       synchronized (this.sync) {
/* 401 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V putIfAbsent(byte key, V value) {
/* 406 */       synchronized (this.sync) {
/* 407 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(byte key, Object value) {
/* 412 */       synchronized (this.sync) {
/* 413 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V replace(byte key, V value) {
/* 418 */       synchronized (this.sync) {
/* 419 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(byte key, V oldValue, V newValue) {
/* 424 */       synchronized (this.sync) {
/* 425 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V computeIfAbsent(byte key, IntFunction<? extends V> mappingFunction) {
/* 430 */       synchronized (this.sync) {
/* 431 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V computeIfAbsentPartial(byte key, Byte2ReferenceFunction<? extends V> mappingFunction) {
/* 436 */       synchronized (this.sync) {
/* 437 */         return this.map.computeIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(byte key, BiFunction<? super Byte, ? super V, ? extends V> remappingFunction) {
/* 443 */       synchronized (this.sync) {
/* 444 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(byte key, BiFunction<? super Byte, ? super V, ? extends V> remappingFunction) {
/* 450 */       synchronized (this.sync) {
/* 451 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(byte key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 457 */       synchronized (this.sync) {
/* 458 */         return this.map.merge(key, value, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 469 */       synchronized (this.sync) {
/* 470 */         return this.map.getOrDefault(key, defaultValue);
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
/* 481 */       synchronized (this.sync) {
/* 482 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V replace(Byte key, V value) {
/* 493 */       synchronized (this.sync) {
/* 494 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Byte key, V oldValue, V newValue) {
/* 505 */       synchronized (this.sync) {
/* 506 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V putIfAbsent(Byte key, V value) {
/* 517 */       synchronized (this.sync) {
/* 518 */         return this.map.putIfAbsent(key, value);
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
/*     */     public V computeIfAbsent(Byte key, Function<? super Byte, ? extends V> mappingFunction) {
/* 530 */       synchronized (this.sync) {
/* 531 */         return this.map.computeIfAbsent(key, mappingFunction);
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
/*     */     public V computeIfPresent(Byte key, BiFunction<? super Byte, ? super V, ? extends V> remappingFunction) {
/* 543 */       synchronized (this.sync) {
/* 544 */         return this.map.computeIfPresent(key, remappingFunction);
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
/*     */     public V compute(Byte key, BiFunction<? super Byte, ? super V, ? extends V> remappingFunction) {
/* 556 */       synchronized (this.sync) {
/* 557 */         return this.map.compute(key, remappingFunction);
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
/*     */     public V merge(Byte key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 569 */       synchronized (this.sync) {
/* 570 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <V> Byte2ReferenceMap<V> synchronize(Byte2ReferenceMap<V> m) {
/* 584 */     return new SynchronizedMap<>(m);
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
/*     */   public static <V> Byte2ReferenceMap<V> synchronize(Byte2ReferenceMap<V> m, Object sync) {
/* 598 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<V>
/*     */     extends Byte2ReferenceFunctions.UnmodifiableFunction<V>
/*     */     implements Byte2ReferenceMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Byte2ReferenceMap<V> map;
/*     */     protected transient ObjectSet<Byte2ReferenceMap.Entry<V>> entries;
/*     */     protected transient ByteSet keys;
/*     */     protected transient ReferenceCollection<V> values;
/*     */     
/*     */     protected UnmodifiableMap(Byte2ReferenceMap<V> m) {
/* 611 */       super(m);
/* 612 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 616 */       return this.map.containsValue(v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Byte, ? extends V> m) {
/* 620 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Byte2ReferenceMap.Entry<V>> byte2ReferenceEntrySet() {
/* 624 */       if (this.entries == null)
/* 625 */         this.entries = ObjectSets.unmodifiable(this.map.byte2ReferenceEntrySet()); 
/* 626 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<Byte, V>> entrySet() {
/* 637 */       return (ObjectSet)byte2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public ByteSet keySet() {
/* 641 */       if (this.keys == null)
/* 642 */         this.keys = ByteSets.unmodifiable(this.map.keySet()); 
/* 643 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 647 */       if (this.values == null)
/* 648 */         return ReferenceCollections.unmodifiable(this.map.values()); 
/* 649 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 653 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 657 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 661 */       if (o == this)
/* 662 */         return true; 
/* 663 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(byte key, V defaultValue) {
/* 668 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Byte, ? super V> action) {
/* 672 */       this.map.forEach(action);
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super Byte, ? super V, ? extends V> function) {
/* 676 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V putIfAbsent(byte key, V value) {
/* 680 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(byte key, Object value) {
/* 684 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V replace(byte key, V value) {
/* 688 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(byte key, V oldValue, V newValue) {
/* 692 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V computeIfAbsent(byte key, IntFunction<? extends V> mappingFunction) {
/* 696 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V computeIfAbsentPartial(byte key, Byte2ReferenceFunction<? extends V> mappingFunction) {
/* 700 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(byte key, BiFunction<? super Byte, ? super V, ? extends V> remappingFunction) {
/* 705 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(byte key, BiFunction<? super Byte, ? super V, ? extends V> remappingFunction) {
/* 710 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(byte key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 715 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 725 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 735 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V replace(Byte key, V value) {
/* 745 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Byte key, V oldValue, V newValue) {
/* 755 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V putIfAbsent(Byte key, V value) {
/* 765 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V computeIfAbsent(Byte key, Function<? super Byte, ? extends V> mappingFunction) {
/* 776 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V computeIfPresent(Byte key, BiFunction<? super Byte, ? super V, ? extends V> remappingFunction) {
/* 787 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V compute(Byte key, BiFunction<? super Byte, ? super V, ? extends V> remappingFunction) {
/* 798 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V merge(Byte key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 809 */       throw new UnsupportedOperationException();
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
/*     */   public static <V> Byte2ReferenceMap<V> unmodifiable(Byte2ReferenceMap<V> m) {
/* 822 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2ReferenceMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */