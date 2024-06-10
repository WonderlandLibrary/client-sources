/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollections;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteSets;
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
/*     */ import java.util.function.ToIntFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Object2ByteMaps
/*     */ {
/*     */   public static <K> ObjectIterator<Object2ByteMap.Entry<K>> fastIterator(Object2ByteMap<K> map) {
/*  49 */     ObjectSet<Object2ByteMap.Entry<K>> entries = map.object2ByteEntrySet();
/*  50 */     return (entries instanceof Object2ByteMap.FastEntrySet) ? (
/*  51 */       (Object2ByteMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static <K> void fastForEach(Object2ByteMap<K> map, Consumer<? super Object2ByteMap.Entry<K>> consumer) {
/*  71 */     ObjectSet<Object2ByteMap.Entry<K>> entries = map.object2ByteEntrySet();
/*  72 */     if (entries instanceof Object2ByteMap.FastEntrySet) {
/*  73 */       ((Object2ByteMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static <K> ObjectIterable<Object2ByteMap.Entry<K>> fastIterable(Object2ByteMap<K> map) {
/*  91 */     final ObjectSet<Object2ByteMap.Entry<K>> entries = map.object2ByteEntrySet();
/*  92 */     return (entries instanceof Object2ByteMap.FastEntrySet) ? (ObjectIterable)new ObjectIterable<Object2ByteMap.Entry<Object2ByteMap.Entry<K>>>() {
/*     */         public ObjectIterator<Object2ByteMap.Entry<K>> iterator() {
/*  94 */           return ((Object2ByteMap.FastEntrySet<K>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Object2ByteMap.Entry<K>> consumer) {
/*  97 */           ((Object2ByteMap.FastEntrySet<K>)entries).fastForEach(consumer);
/*     */         }
/*  99 */       } : entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<K>
/*     */     extends Object2ByteFunctions.EmptyFunction<K>
/*     */     implements Object2ByteMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(byte v) {
/* 118 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 128 */       return false;
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Byte> m) {
/* 132 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Object2ByteMap.Entry<K>> object2ByteEntrySet() {
/* 137 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 142 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteCollection values() {
/* 147 */       return (ByteCollection)ByteSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 151 */       return Object2ByteMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 155 */       return true;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 159 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 163 */       if (!(o instanceof Map))
/* 164 */         return false; 
/* 165 */       return ((Map)o).isEmpty();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 169 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Object2ByteMap<K> emptyMap() {
/* 187 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Object2ByteFunctions.Singleton<K>
/*     */     implements Object2ByteMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Object2ByteMap.Entry<K>> entries;
/*     */     
/*     */     protected transient ObjectSet<K> keys;
/*     */     
/*     */     protected transient ByteCollection values;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, byte value) {
/* 206 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(byte v) {
/* 210 */       return (this.value == v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 220 */       return (((Byte)ov).byteValue() == this.value);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Byte> m) {
/* 224 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2ByteMap.Entry<K>> object2ByteEntrySet() {
/* 228 */       if (this.entries == null)
/* 229 */         this.entries = ObjectSets.singleton(new AbstractObject2ByteMap.BasicEntry<>(this.key, this.value)); 
/* 230 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<K, Byte>> entrySet() {
/* 241 */       return (ObjectSet)object2ByteEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 245 */       if (this.keys == null)
/* 246 */         this.keys = ObjectSets.singleton(this.key); 
/* 247 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ByteCollection values() {
/* 251 */       if (this.values == null)
/* 252 */         this.values = (ByteCollection)ByteSets.singleton(this.value); 
/* 253 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 257 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 261 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ this.value;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 265 */       if (o == this)
/* 266 */         return true; 
/* 267 */       if (!(o instanceof Map))
/* 268 */         return false; 
/* 269 */       Map<?, ?> m = (Map<?, ?>)o;
/* 270 */       if (m.size() != 1)
/* 271 */         return false; 
/* 272 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 276 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static <K> Object2ByteMap<K> singleton(K key, byte value) {
/* 295 */     return new Singleton<>(key, value);
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
/*     */   public static <K> Object2ByteMap<K> singleton(K key, Byte value) {
/* 313 */     return new Singleton<>(key, value.byteValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<K>
/*     */     extends Object2ByteFunctions.SynchronizedFunction<K>
/*     */     implements Object2ByteMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ByteMap<K> map;
/*     */     protected transient ObjectSet<Object2ByteMap.Entry<K>> entries;
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient ByteCollection values;
/*     */     
/*     */     protected SynchronizedMap(Object2ByteMap<K> m, Object sync) {
/* 326 */       super(m, sync);
/* 327 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Object2ByteMap<K> m) {
/* 330 */       super(m);
/* 331 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(byte v) {
/* 335 */       synchronized (this.sync) {
/* 336 */         return this.map.containsValue(v);
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
/* 347 */       synchronized (this.sync) {
/* 348 */         return this.map.containsValue(ov);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Byte> m) {
/* 353 */       synchronized (this.sync) {
/* 354 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2ByteMap.Entry<K>> object2ByteEntrySet() {
/* 359 */       synchronized (this.sync) {
/* 360 */         if (this.entries == null)
/* 361 */           this.entries = ObjectSets.synchronize(this.map.object2ByteEntrySet(), this.sync); 
/* 362 */         return this.entries;
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
/*     */     public ObjectSet<Map.Entry<K, Byte>> entrySet() {
/* 374 */       return (ObjectSet)object2ByteEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 378 */       synchronized (this.sync) {
/* 379 */         if (this.keys == null)
/* 380 */           this.keys = ObjectSets.synchronize(this.map.keySet(), this.sync); 
/* 381 */         return this.keys;
/*     */       } 
/*     */     }
/*     */     
/*     */     public ByteCollection values() {
/* 386 */       synchronized (this.sync) {
/* 387 */         if (this.values == null)
/* 388 */           return ByteCollections.synchronize(this.map.values(), this.sync); 
/* 389 */         return this.values;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 394 */       synchronized (this.sync) {
/* 395 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 400 */       synchronized (this.sync) {
/* 401 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 406 */       if (o == this)
/* 407 */         return true; 
/* 408 */       synchronized (this.sync) {
/* 409 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 413 */       synchronized (this.sync) {
/* 414 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public byte getOrDefault(Object key, byte defaultValue) {
/* 420 */       synchronized (this.sync) {
/* 421 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Byte> action) {
/* 426 */       synchronized (this.sync) {
/* 427 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Byte, ? extends Byte> function) {
/* 432 */       synchronized (this.sync) {
/* 433 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public byte putIfAbsent(K key, byte value) {
/* 438 */       synchronized (this.sync) {
/* 439 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, byte value) {
/* 444 */       synchronized (this.sync) {
/* 445 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public byte replace(K key, byte value) {
/* 450 */       synchronized (this.sync) {
/* 451 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(K key, byte oldValue, byte newValue) {
/* 456 */       synchronized (this.sync) {
/* 457 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public byte computeByteIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
/* 463 */       synchronized (this.sync) {
/* 464 */         return this.map.computeByteIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */     
/*     */     public byte computeByteIfAbsentPartial(K key, Object2ByteFunction<? super K> mappingFunction) {
/* 469 */       synchronized (this.sync) {
/* 470 */         return this.map.computeByteIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public byte computeByteIfPresent(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
/* 476 */       synchronized (this.sync) {
/* 477 */         return this.map.computeByteIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public byte computeByte(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
/* 483 */       synchronized (this.sync) {
/* 484 */         return this.map.computeByte(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public byte mergeByte(K key, byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/* 490 */       synchronized (this.sync) {
/* 491 */         return this.map.mergeByte(key, value, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte getOrDefault(Object key, Byte defaultValue) {
/* 502 */       synchronized (this.sync) {
/* 503 */         return this.map.getOrDefault(key, defaultValue);
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
/* 514 */       synchronized (this.sync) {
/* 515 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte replace(K key, Byte value) {
/* 526 */       synchronized (this.sync) {
/* 527 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(K key, Byte oldValue, Byte newValue) {
/* 538 */       synchronized (this.sync) {
/* 539 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte putIfAbsent(K key, Byte value) {
/* 550 */       synchronized (this.sync) {
/* 551 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte computeIfAbsent(K key, Function<? super K, ? extends Byte> mappingFunction) {
/* 557 */       synchronized (this.sync) {
/* 558 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte computeIfPresent(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
/* 564 */       synchronized (this.sync) {
/* 565 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte compute(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
/* 571 */       synchronized (this.sync) {
/* 572 */         return this.map.compute(key, remappingFunction);
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
/*     */     public Byte merge(K key, Byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/* 584 */       synchronized (this.sync) {
/* 585 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <K> Object2ByteMap<K> synchronize(Object2ByteMap<K> m) {
/* 599 */     return new SynchronizedMap<>(m);
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
/*     */   public static <K> Object2ByteMap<K> synchronize(Object2ByteMap<K> m, Object sync) {
/* 613 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<K>
/*     */     extends Object2ByteFunctions.UnmodifiableFunction<K>
/*     */     implements Object2ByteMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ByteMap<K> map;
/*     */     protected transient ObjectSet<Object2ByteMap.Entry<K>> entries;
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient ByteCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Object2ByteMap<K> m) {
/* 626 */       super(m);
/* 627 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(byte v) {
/* 631 */       return this.map.containsValue(v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 641 */       return this.map.containsValue(ov);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Byte> m) {
/* 645 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Object2ByteMap.Entry<K>> object2ByteEntrySet() {
/* 649 */       if (this.entries == null)
/* 650 */         this.entries = ObjectSets.unmodifiable(this.map.object2ByteEntrySet()); 
/* 651 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<K, Byte>> entrySet() {
/* 662 */       return (ObjectSet)object2ByteEntrySet();
/*     */     }
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 666 */       if (this.keys == null)
/* 667 */         this.keys = ObjectSets.unmodifiable(this.map.keySet()); 
/* 668 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ByteCollection values() {
/* 672 */       if (this.values == null)
/* 673 */         return ByteCollections.unmodifiable(this.map.values()); 
/* 674 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 678 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 682 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 686 */       if (o == this)
/* 687 */         return true; 
/* 688 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte getOrDefault(Object key, byte defaultValue) {
/* 693 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Byte> action) {
/* 697 */       this.map.forEach(action);
/*     */     }
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Byte, ? extends Byte> function) {
/* 701 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public byte putIfAbsent(K key, byte value) {
/* 705 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object key, byte value) {
/* 709 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public byte replace(K key, byte value) {
/* 713 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(K key, byte oldValue, byte newValue) {
/* 717 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte computeByteIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
/* 722 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public byte computeByteIfAbsentPartial(K key, Object2ByteFunction<? super K> mappingFunction) {
/* 726 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte computeByteIfPresent(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
/* 731 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte computeByte(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
/* 736 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte mergeByte(K key, byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/* 741 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte getOrDefault(Object key, Byte defaultValue) {
/* 751 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 761 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte replace(K key, Byte value) {
/* 771 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(K key, Byte oldValue, Byte newValue) {
/* 781 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte putIfAbsent(K key, Byte value) {
/* 791 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte computeIfAbsent(K key, Function<? super K, ? extends Byte> mappingFunction) {
/* 796 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte computeIfPresent(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
/* 801 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte compute(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
/* 806 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte merge(K key, Byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/* 817 */       throw new UnsupportedOperationException();
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
/*     */   public static <K> Object2ByteMap<K> unmodifiable(Object2ByteMap<K> m) {
/* 830 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ByteMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */