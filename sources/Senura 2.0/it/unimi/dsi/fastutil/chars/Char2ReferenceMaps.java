/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public final class Char2ReferenceMaps
/*     */ {
/*     */   public static <V> ObjectIterator<Char2ReferenceMap.Entry<V>> fastIterator(Char2ReferenceMap<V> map) {
/*  49 */     ObjectSet<Char2ReferenceMap.Entry<V>> entries = map.char2ReferenceEntrySet();
/*  50 */     return (entries instanceof Char2ReferenceMap.FastEntrySet) ? (
/*  51 */       (Char2ReferenceMap.FastEntrySet)entries).fastIterator() : 
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
/*     */   public static <V> void fastForEach(Char2ReferenceMap<V> map, Consumer<? super Char2ReferenceMap.Entry<V>> consumer) {
/*  71 */     ObjectSet<Char2ReferenceMap.Entry<V>> entries = map.char2ReferenceEntrySet();
/*  72 */     if (entries instanceof Char2ReferenceMap.FastEntrySet) {
/*  73 */       ((Char2ReferenceMap.FastEntrySet)entries).fastForEach(consumer);
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
/*     */   public static <V> ObjectIterable<Char2ReferenceMap.Entry<V>> fastIterable(Char2ReferenceMap<V> map) {
/*  91 */     final ObjectSet<Char2ReferenceMap.Entry<V>> entries = map.char2ReferenceEntrySet();
/*  92 */     return (entries instanceof Char2ReferenceMap.FastEntrySet) ? new ObjectIterable<Char2ReferenceMap.Entry<V>>() {
/*     */         public ObjectIterator<Char2ReferenceMap.Entry<V>> iterator() {
/*  94 */           return ((Char2ReferenceMap.FastEntrySet<V>)entries).fastIterator();
/*     */         }
/*     */         public void forEach(Consumer<? super Char2ReferenceMap.Entry<V>> consumer) {
/*  97 */           ((Char2ReferenceMap.FastEntrySet<V>)entries).fastForEach(consumer);
/*     */         }
/*  99 */       } : (ObjectIterable<Char2ReferenceMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<V>
/*     */     extends Char2ReferenceFunctions.EmptyFunction<V>
/*     */     implements Char2ReferenceMap<V>, Serializable, Cloneable
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
/*     */     public void putAll(Map<? extends Character, ? extends V> m) {
/* 122 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Char2ReferenceMap.Entry<V>> char2ReferenceEntrySet() {
/* 127 */       return (ObjectSet<Char2ReferenceMap.Entry<V>>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSet keySet() {
/* 132 */       return CharSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 137 */       return (ReferenceCollection<V>)ReferenceSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 141 */       return Char2ReferenceMaps.EMPTY_MAP;
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
/*     */   public static <V> Char2ReferenceMap<V> emptyMap() {
/* 177 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Char2ReferenceFunctions.Singleton<V>
/*     */     implements Char2ReferenceMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Char2ReferenceMap.Entry<V>> entries;
/*     */     
/*     */     protected transient CharSet keys;
/*     */     
/*     */     protected transient ReferenceCollection<V> values;
/*     */ 
/*     */     
/*     */     protected Singleton(char key, V value) {
/* 196 */       super(key, value);
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 200 */       return (this.value == v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Character, ? extends V> m) {
/* 204 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Char2ReferenceMap.Entry<V>> char2ReferenceEntrySet() {
/* 208 */       if (this.entries == null)
/* 209 */         this.entries = ObjectSets.singleton(new AbstractChar2ReferenceMap.BasicEntry<>(this.key, this.value)); 
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
/*     */     public ObjectSet<Map.Entry<Character, V>> entrySet() {
/* 221 */       return (ObjectSet)char2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public CharSet keySet() {
/* 225 */       if (this.keys == null)
/* 226 */         this.keys = CharSets.singleton(this.key); 
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
/*     */   public static <V> Char2ReferenceMap<V> singleton(char key, V value) {
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
/*     */   public static <V> Char2ReferenceMap<V> singleton(Character key, V value) {
/* 293 */     return new Singleton<>(key.charValue(), value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<V>
/*     */     extends Char2ReferenceFunctions.SynchronizedFunction<V>
/*     */     implements Char2ReferenceMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2ReferenceMap<V> map;
/*     */     protected transient ObjectSet<Char2ReferenceMap.Entry<V>> entries;
/*     */     protected transient CharSet keys;
/*     */     protected transient ReferenceCollection<V> values;
/*     */     
/*     */     protected SynchronizedMap(Char2ReferenceMap<V> m, Object sync) {
/* 306 */       super(m, sync);
/* 307 */       this.map = m;
/*     */     }
/*     */     protected SynchronizedMap(Char2ReferenceMap<V> m) {
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
/*     */     public void putAll(Map<? extends Character, ? extends V> m) {
/* 321 */       synchronized (this.sync) {
/* 322 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectSet<Char2ReferenceMap.Entry<V>> char2ReferenceEntrySet() {
/* 327 */       synchronized (this.sync) {
/* 328 */         if (this.entries == null)
/* 329 */           this.entries = ObjectSets.synchronize(this.map.char2ReferenceEntrySet(), this.sync); 
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
/*     */     public ObjectSet<Map.Entry<Character, V>> entrySet() {
/* 342 */       return (ObjectSet)char2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public CharSet keySet() {
/* 346 */       synchronized (this.sync) {
/* 347 */         if (this.keys == null)
/* 348 */           this.keys = CharSets.synchronize(this.map.keySet(), this.sync); 
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
/*     */     public V getOrDefault(char key, V defaultValue) {
/* 388 */       synchronized (this.sync) {
/* 389 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Character, ? super V> action) {
/* 394 */       synchronized (this.sync) {
/* 395 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Character, ? super V, ? extends V> function) {
/* 401 */       synchronized (this.sync) {
/* 402 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V putIfAbsent(char key, V value) {
/* 407 */       synchronized (this.sync) {
/* 408 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean remove(char key, Object value) {
/* 413 */       synchronized (this.sync) {
/* 414 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V replace(char key, V value) {
/* 419 */       synchronized (this.sync) {
/* 420 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean replace(char key, V oldValue, V newValue) {
/* 425 */       synchronized (this.sync) {
/* 426 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V computeIfAbsent(char key, IntFunction<? extends V> mappingFunction) {
/* 431 */       synchronized (this.sync) {
/* 432 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */     
/*     */     public V computeIfAbsentPartial(char key, Char2ReferenceFunction<? extends V> mappingFunction) {
/* 437 */       synchronized (this.sync) {
/* 438 */         return this.map.computeIfAbsentPartial(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 444 */       synchronized (this.sync) {
/* 445 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 451 */       synchronized (this.sync) {
/* 452 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(char key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 458 */       synchronized (this.sync) {
/* 459 */         return this.map.merge(key, value, remappingFunction);
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
/* 470 */       synchronized (this.sync) {
/* 471 */         return this.map.getOrDefault(key, defaultValue);
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
/* 482 */       synchronized (this.sync) {
/* 483 */         return this.map.remove(key, value);
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
/* 494 */       synchronized (this.sync) {
/* 495 */         return this.map.replace(key, value);
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
/* 506 */       synchronized (this.sync) {
/* 507 */         return this.map.replace(key, oldValue, newValue);
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
/* 518 */       synchronized (this.sync) {
/* 519 */         return this.map.putIfAbsent(key, value);
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
/* 531 */       synchronized (this.sync) {
/* 532 */         return this.map.computeIfAbsent(key, mappingFunction);
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
/* 544 */       synchronized (this.sync) {
/* 545 */         return this.map.computeIfPresent(key, remappingFunction);
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
/* 557 */       synchronized (this.sync) {
/* 558 */         return this.map.compute(key, remappingFunction);
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
/* 570 */       synchronized (this.sync) {
/* 571 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <V> Char2ReferenceMap<V> synchronize(Char2ReferenceMap<V> m) {
/* 585 */     return new SynchronizedMap<>(m);
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
/*     */   public static <V> Char2ReferenceMap<V> synchronize(Char2ReferenceMap<V> m, Object sync) {
/* 599 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<V>
/*     */     extends Char2ReferenceFunctions.UnmodifiableFunction<V>
/*     */     implements Char2ReferenceMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2ReferenceMap<V> map;
/*     */     protected transient ObjectSet<Char2ReferenceMap.Entry<V>> entries;
/*     */     protected transient CharSet keys;
/*     */     protected transient ReferenceCollection<V> values;
/*     */     
/*     */     protected UnmodifiableMap(Char2ReferenceMap<V> m) {
/* 612 */       super(m);
/* 613 */       this.map = m;
/*     */     }
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 617 */       return this.map.containsValue(v);
/*     */     }
/*     */     
/*     */     public void putAll(Map<? extends Character, ? extends V> m) {
/* 621 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public ObjectSet<Char2ReferenceMap.Entry<V>> char2ReferenceEntrySet() {
/* 625 */       if (this.entries == null)
/* 626 */         this.entries = ObjectSets.unmodifiable(this.map.char2ReferenceEntrySet()); 
/* 627 */       return this.entries;
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
/* 638 */       return (ObjectSet)char2ReferenceEntrySet();
/*     */     }
/*     */     
/*     */     public CharSet keySet() {
/* 642 */       if (this.keys == null)
/* 643 */         this.keys = CharSets.unmodifiable(this.map.keySet()); 
/* 644 */       return this.keys;
/*     */     }
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 648 */       if (this.values == null)
/* 649 */         return ReferenceCollections.unmodifiable(this.map.values()); 
/* 650 */       return this.values;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 654 */       return this.map.isEmpty();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 658 */       return this.map.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 662 */       if (o == this)
/* 663 */         return true; 
/* 664 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(char key, V defaultValue) {
/* 669 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */     
/*     */     public void forEach(BiConsumer<? super Character, ? super V> action) {
/* 673 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Character, ? super V, ? extends V> function) {
/* 678 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V putIfAbsent(char key, V value) {
/* 682 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean remove(char key, Object value) {
/* 686 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V replace(char key, V value) {
/* 690 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean replace(char key, V oldValue, V newValue) {
/* 694 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V computeIfAbsent(char key, IntFunction<? extends V> mappingFunction) {
/* 698 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public V computeIfAbsentPartial(char key, Char2ReferenceFunction<? extends V> mappingFunction) {
/* 702 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 707 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 712 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(char key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 717 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 727 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 737 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V replace(Character key, V value) {
/* 747 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Character key, V oldValue, V newValue) {
/* 757 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V putIfAbsent(Character key, V value) {
/* 767 */       throw new UnsupportedOperationException();
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
/* 778 */       throw new UnsupportedOperationException();
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
/* 789 */       throw new UnsupportedOperationException();
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
/* 800 */       throw new UnsupportedOperationException();
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
/* 811 */       throw new UnsupportedOperationException();
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
/*     */   public static <V> Char2ReferenceMap<V> unmodifiable(Char2ReferenceMap<V> m) {
/* 824 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2ReferenceMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */