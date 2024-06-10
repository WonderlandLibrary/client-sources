/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectCollections;
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
/*     */ import java.util.Objects;
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
/*     */ public final class Char2ObjectMaps
/*     */ {
/*     */   public static <V> ObjectIterator<Char2ObjectMap.Entry<V>> fastIterator(Char2ObjectMap<V> map) {
/*  48 */     ObjectSet<Char2ObjectMap.Entry<V>> entries = map.char2ObjectEntrySet();
/*  49 */     return (entries instanceof Char2ObjectMap.FastEntrySet) ? (
/*  50 */       (Char2ObjectMap.FastEntrySet)entries).fastIterator() : 
/*  51 */       entries.iterator();
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
/*     */   public static <V> void fastForEach(Char2ObjectMap<V> map, Consumer<? super Char2ObjectMap.Entry<V>> consumer) {
/*  70 */     ObjectSet<Char2ObjectMap.Entry<V>> entries = map.char2ObjectEntrySet();
/*  71 */     if (entries instanceof Char2ObjectMap.FastEntrySet) {
/*  72 */       ((Char2ObjectMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static <V> ObjectIterable<Char2ObjectMap.Entry<V>> fastIterable(Char2ObjectMap<V> map) {
/*  90 */     final ObjectSet<Char2ObjectMap.Entry<V>> entries = map.char2ObjectEntrySet();
/*  91 */     return (entries instanceof Char2ObjectMap.FastEntrySet) ? new ObjectIterable<Char2ObjectMap.Entry<V>>() {
/*     */         public ObjectIterator<Char2ObjectMap.Entry<V>> iterator() {
/*  93 */           return ((Char2ObjectMap.FastEntrySet<V>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Char2ObjectMap.Entry<V>> consumer) {
/*  96 */           ((Char2ObjectMap.FastEntrySet<V>)entries).fastForEach(consumer);
/*     */         }
/*  98 */       } : (ObjectIterable<Char2ObjectMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<V>
/*     */     extends Char2ObjectFunctions.EmptyFunction<V>
/*     */     implements Char2ObjectMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 117 */       return false;
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Character, ? extends V> m) {
/* 121 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Char2ObjectMap.Entry<V>> char2ObjectEntrySet() {
/* 126 */       return (ObjectSet<Char2ObjectMap.Entry<V>>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSet keySet() {
/* 131 */       return CharSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 136 */       return (ObjectCollection<V>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 140 */       return Char2ObjectMaps.EMPTY_MAP;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 144 */       return true;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 148 */       return 0;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 152 */       if (!(o instanceof Map))
/* 153 */         return false; 
/* 154 */       return ((Map)o).isEmpty();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 158 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Char2ObjectMap<V> emptyMap() {
/* 176 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Char2ObjectFunctions.Singleton<V>
/*     */     implements Char2ObjectMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Char2ObjectMap.Entry<V>> entries;
/*     */     
/*     */     protected transient CharSet keys;
/*     */     
/*     */     protected transient ObjectCollection<V> values;
/*     */ 
/*     */     
/*     */     protected Singleton(char key, V value) {
/* 195 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 199 */       return Objects.equals(this.value, v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Character, ? extends V> m) {
/* 203 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Char2ObjectMap.Entry<V>> char2ObjectEntrySet() {
/* 207 */       if (this.entries == null)
/* 208 */         this.entries = ObjectSets.singleton(new AbstractChar2ObjectMap.BasicEntry<>(this.key, this.value)); 
/* 209 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSet<Map.Entry<Character, V>> entrySet() {
/* 220 */       return (ObjectSet)char2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public CharSet keySet() {
/* 224 */       if (this.keys == null)
/* 225 */         this.keys = CharSets.singleton(this.key); 
/* 226 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 230 */       if (this.values == null)
/* 231 */         this.values = (ObjectCollection<V>)ObjectSets.singleton(this.value); 
/* 232 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 236 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 240 */       return this.key ^ ((this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 244 */       if (o == this)
/* 245 */         return true; 
/* 246 */       if (!(o instanceof Map))
/* 247 */         return false; 
/* 248 */       Map<?, ?> m = (Map<?, ?>)o;
/* 249 */       if (m.size() != 1)
/* 250 */         return false; 
/* 251 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 255 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static <V> Char2ObjectMap<V> singleton(char key, V value) {
/* 274 */     return new Singleton<>(key, value);
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
/*     */   public static <V> Char2ObjectMap<V> singleton(Character key, V value) {
/* 292 */     return new Singleton<>(key.charValue(), value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<V>
/*     */     extends Char2ObjectFunctions.SynchronizedFunction<V>
/*     */     implements Char2ObjectMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2ObjectMap<V> map;
/*     */     protected transient ObjectSet<Char2ObjectMap.Entry<V>> entries;
/*     */     protected transient CharSet keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected SynchronizedMap(Char2ObjectMap<V> m, Object sync) {
/* 305 */       super(m, sync);
/* 306 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Char2ObjectMap<V> m) {
/* 309 */       super(m);
/* 310 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 314 */       synchronized (this.sync) {
/* 315 */         return this.map.containsValue(v);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Character, ? extends V> m) {
/* 320 */       synchronized (this.sync) {
/* 321 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Char2ObjectMap.Entry<V>> char2ObjectEntrySet() {
/* 326 */       synchronized (this.sync) {
/* 327 */         if (this.entries == null)
/* 328 */           this.entries = ObjectSets.synchronize(this.map.char2ObjectEntrySet(), this.sync); 
/* 329 */         return this.entries;
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
/*     */     public ObjectSet<Map.Entry<Character, V>> entrySet() {
/* 341 */       return (ObjectSet)char2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public CharSet keySet() {
/* 345 */       synchronized (this.sync) {
/* 346 */         if (this.keys == null)
/* 347 */           this.keys = CharSets.synchronize(this.map.keySet(), this.sync); 
/* 348 */         return this.keys;
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 353 */       synchronized (this.sync) {
/* 354 */         if (this.values == null)
/* 355 */           return ObjectCollections.synchronize(this.map.values(), this.sync); 
/* 356 */         return this.values;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 361 */       synchronized (this.sync) {
/* 362 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 367 */       synchronized (this.sync) {
/* 368 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 373 */       if (o == this)
/* 374 */         return true; 
/* 375 */       synchronized (this.sync) {
/* 376 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 380 */       synchronized (this.sync) {
/* 381 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(char key, V defaultValue) {
/* 387 */       synchronized (this.sync) {
/* 388 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Character, ? super V> action) {
/* 393 */       synchronized (this.sync) {
/* 394 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Character, ? super V, ? extends V> function) {
/* 400 */       synchronized (this.sync) {
/* 401 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V putIfAbsent(char key, V value) {
/* 406 */       synchronized (this.sync) {
/* 407 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(char key, Object value) {
/* 412 */       synchronized (this.sync) {
/* 413 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V replace(char key, V value) {
/* 418 */       synchronized (this.sync) {
/* 419 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(char key, V oldValue, V newValue) {
/* 424 */       synchronized (this.sync) {
/* 425 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V computeIfAbsent(char key, IntFunction<? extends V> mappingFunction) {
/* 430 */       synchronized (this.sync) {
/* 431 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V computeIfAbsentPartial(char key, Char2ObjectFunction<? extends V> mappingFunction) {
/* 436 */       synchronized (this.sync) {
/* 437 */         return this.map.computeIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 443 */       synchronized (this.sync) {
/* 444 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 450 */       synchronized (this.sync) {
/* 451 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(char key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
/*     */     public V replace(Character key, V value) {
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
/*     */     public boolean replace(Character key, V oldValue, V newValue) {
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
/*     */     public V putIfAbsent(Character key, V value) {
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
/*     */     public V computeIfAbsent(Character key, Function<? super Character, ? extends V> mappingFunction) {
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
/*     */     public V computeIfPresent(Character key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
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
/*     */     public V compute(Character key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
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
/*     */     public V merge(Character key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
/*     */   public static <V> Char2ObjectMap<V> synchronize(Char2ObjectMap<V> m) {
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
/*     */   public static <V> Char2ObjectMap<V> synchronize(Char2ObjectMap<V> m, Object sync) {
/* 598 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<V>
/*     */     extends Char2ObjectFunctions.UnmodifiableFunction<V>
/*     */     implements Char2ObjectMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2ObjectMap<V> map;
/*     */     protected transient ObjectSet<Char2ObjectMap.Entry<V>> entries;
/*     */     protected transient CharSet keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected UnmodifiableMap(Char2ObjectMap<V> m) {
/* 611 */       super(m);
/* 612 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 616 */       return this.map.containsValue(v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Character, ? extends V> m) {
/* 620 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Char2ObjectMap.Entry<V>> char2ObjectEntrySet() {
/* 624 */       if (this.entries == null)
/* 625 */         this.entries = ObjectSets.unmodifiable(this.map.char2ObjectEntrySet()); 
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
/*     */     public ObjectSet<Map.Entry<Character, V>> entrySet() {
/* 637 */       return (ObjectSet)char2ObjectEntrySet();
/*     */     }
/*     */     
/*     */     public CharSet keySet() {
/* 641 */       if (this.keys == null)
/* 642 */         this.keys = CharSets.unmodifiable(this.map.keySet()); 
/* 643 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 647 */       if (this.values == null)
/* 648 */         return ObjectCollections.unmodifiable(this.map.values()); 
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
/*     */     public V getOrDefault(char key, V defaultValue) {
/* 668 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Character, ? super V> action) {
/* 672 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Character, ? super V, ? extends V> function) {
/* 677 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V putIfAbsent(char key, V value) {
/* 681 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(char key, Object value) {
/* 685 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V replace(char key, V value) {
/* 689 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(char key, V oldValue, V newValue) {
/* 693 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V computeIfAbsent(char key, IntFunction<? extends V> mappingFunction) {
/* 697 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V computeIfAbsentPartial(char key, Char2ObjectFunction<? extends V> mappingFunction) {
/* 701 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 706 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 711 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(char key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 716 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 726 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 736 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V replace(Character key, V value) {
/* 746 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Character key, V oldValue, V newValue) {
/* 756 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V putIfAbsent(Character key, V value) {
/* 766 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V computeIfAbsent(Character key, Function<? super Character, ? extends V> mappingFunction) {
/* 777 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V computeIfPresent(Character key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 788 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V compute(Character key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 799 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V merge(Character key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 810 */       throw new UnsupportedOperationException();
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
/*     */   public static <V> Char2ObjectMap<V> unmodifiable(Char2ObjectMap<V> m) {
/* 823 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2ObjectMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */