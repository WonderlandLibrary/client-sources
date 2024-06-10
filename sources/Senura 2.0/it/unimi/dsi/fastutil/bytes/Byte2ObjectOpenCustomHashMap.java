/*      */ package it.unimi.dsi.fastutil.bytes;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Byte2ObjectOpenCustomHashMap<V>
/*      */   extends AbstractByte2ObjectMap<V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient byte[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected ByteHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Byte2ObjectMap.FastEntrySet<V> entries;
/*      */   protected transient ByteSet keys;
/*      */   protected transient ObjectCollection<V> values;
/*      */   
/*      */   public Byte2ObjectOpenCustomHashMap(int expected, float f, ByteHash.Strategy strategy) {
/*  106 */     this.strategy = strategy;
/*  107 */     if (f <= 0.0F || f > 1.0F)
/*  108 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  109 */     if (expected < 0)
/*  110 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  111 */     this.f = f;
/*  112 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  113 */     this.mask = this.n - 1;
/*  114 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  115 */     this.key = new byte[this.n + 1];
/*  116 */     this.value = (V[])new Object[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ObjectOpenCustomHashMap(int expected, ByteHash.Strategy strategy) {
/*  128 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ObjectOpenCustomHashMap(ByteHash.Strategy strategy) {
/*  139 */     this(16, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ObjectOpenCustomHashMap(Map<? extends Byte, ? extends V> m, float f, ByteHash.Strategy strategy) {
/*  153 */     this(m.size(), f, strategy);
/*  154 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ObjectOpenCustomHashMap(Map<? extends Byte, ? extends V> m, ByteHash.Strategy strategy) {
/*  167 */     this(m, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ObjectOpenCustomHashMap(Byte2ObjectMap<V> m, float f, ByteHash.Strategy strategy) {
/*  181 */     this(m.size(), f, strategy);
/*  182 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ObjectOpenCustomHashMap(Byte2ObjectMap<V> m, ByteHash.Strategy strategy) {
/*  195 */     this(m, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ObjectOpenCustomHashMap(byte[] k, V[] v, float f, ByteHash.Strategy strategy) {
/*  213 */     this(k.length, f, strategy);
/*  214 */     if (k.length != v.length) {
/*  215 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  217 */     for (int i = 0; i < k.length; i++) {
/*  218 */       put(k[i], v[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ObjectOpenCustomHashMap(byte[] k, V[] v, ByteHash.Strategy strategy) {
/*  235 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteHash.Strategy strategy() {
/*  243 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  246 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  249 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  250 */     if (needed > this.n)
/*  251 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  254 */     int needed = (int)Math.min(1073741824L, 
/*  255 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  256 */     if (needed > this.n)
/*  257 */       rehash(needed); 
/*      */   }
/*      */   private V removeEntry(int pos) {
/*  260 */     V oldValue = this.value[pos];
/*  261 */     this.value[pos] = null;
/*  262 */     this.size--;
/*  263 */     shiftKeys(pos);
/*  264 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  265 */       rehash(this.n / 2); 
/*  266 */     return oldValue;
/*      */   }
/*      */   private V removeNullEntry() {
/*  269 */     this.containsNullKey = false;
/*  270 */     V oldValue = this.value[this.n];
/*  271 */     this.value[this.n] = null;
/*  272 */     this.size--;
/*  273 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  274 */       rehash(this.n / 2); 
/*  275 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Byte, ? extends V> m) {
/*  279 */     if (this.f <= 0.5D) {
/*  280 */       ensureCapacity(m.size());
/*      */     } else {
/*  282 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  284 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(byte k) {
/*  288 */     if (this.strategy.equals(k, (byte)0)) {
/*  289 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  291 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  294 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  295 */       return -(pos + 1); 
/*  296 */     if (this.strategy.equals(k, curr)) {
/*  297 */       return pos;
/*      */     }
/*      */     while (true) {
/*  300 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  301 */         return -(pos + 1); 
/*  302 */       if (this.strategy.equals(k, curr))
/*  303 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, byte k, V v) {
/*  307 */     if (pos == this.n)
/*  308 */       this.containsNullKey = true; 
/*  309 */     this.key[pos] = k;
/*  310 */     this.value[pos] = v;
/*  311 */     if (this.size++ >= this.maxFill) {
/*  312 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(byte k, V v) {
/*  318 */     int pos = find(k);
/*  319 */     if (pos < 0) {
/*  320 */       insert(-pos - 1, k, v);
/*  321 */       return this.defRetValue;
/*      */     } 
/*  323 */     V oldValue = this.value[pos];
/*  324 */     this.value[pos] = v;
/*  325 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void shiftKeys(int pos) {
/*  338 */     byte[] key = this.key; while (true) {
/*      */       byte curr; int last;
/*  340 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  342 */         if ((curr = key[pos]) == 0) {
/*  343 */           key[last] = 0;
/*  344 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  347 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  348 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  350 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  352 */       key[last] = curr;
/*  353 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V remove(byte k) {
/*  359 */     if (this.strategy.equals(k, (byte)0)) {
/*  360 */       if (this.containsNullKey)
/*  361 */         return removeNullEntry(); 
/*  362 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  365 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  368 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  369 */       return this.defRetValue; 
/*  370 */     if (this.strategy.equals(k, curr))
/*  371 */       return removeEntry(pos); 
/*      */     while (true) {
/*  373 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  374 */         return this.defRetValue; 
/*  375 */       if (this.strategy.equals(k, curr)) {
/*  376 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V get(byte k) {
/*  382 */     if (this.strategy.equals(k, (byte)0)) {
/*  383 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  385 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  388 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  389 */       return this.defRetValue; 
/*  390 */     if (this.strategy.equals(k, curr)) {
/*  391 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  394 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  395 */         return this.defRetValue; 
/*  396 */       if (this.strategy.equals(k, curr)) {
/*  397 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(byte k) {
/*  403 */     if (this.strategy.equals(k, (byte)0)) {
/*  404 */       return this.containsNullKey;
/*      */     }
/*  406 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  409 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  410 */       return false; 
/*  411 */     if (this.strategy.equals(k, curr)) {
/*  412 */       return true;
/*      */     }
/*      */     while (true) {
/*  415 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  416 */         return false; 
/*  417 */       if (this.strategy.equals(k, curr))
/*  418 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  423 */     V[] value = this.value;
/*  424 */     byte[] key = this.key;
/*  425 */     if (this.containsNullKey && Objects.equals(value[this.n], v))
/*  426 */       return true; 
/*  427 */     for (int i = this.n; i-- != 0;) {
/*  428 */       if (key[i] != 0 && Objects.equals(value[i], v))
/*  429 */         return true; 
/*  430 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(byte k, V defaultValue) {
/*  436 */     if (this.strategy.equals(k, (byte)0)) {
/*  437 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  439 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  442 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  443 */       return defaultValue; 
/*  444 */     if (this.strategy.equals(k, curr)) {
/*  445 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  448 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  449 */         return defaultValue; 
/*  450 */       if (this.strategy.equals(k, curr)) {
/*  451 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V putIfAbsent(byte k, V v) {
/*  457 */     int pos = find(k);
/*  458 */     if (pos >= 0)
/*  459 */       return this.value[pos]; 
/*  460 */     insert(-pos - 1, k, v);
/*  461 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(byte k, Object v) {
/*  467 */     if (this.strategy.equals(k, (byte)0)) {
/*  468 */       if (this.containsNullKey && Objects.equals(v, this.value[this.n])) {
/*  469 */         removeNullEntry();
/*  470 */         return true;
/*      */       } 
/*  472 */       return false;
/*      */     } 
/*      */     
/*  475 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  478 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  479 */       return false; 
/*  480 */     if (this.strategy.equals(k, curr) && Objects.equals(v, this.value[pos])) {
/*  481 */       removeEntry(pos);
/*  482 */       return true;
/*      */     } 
/*      */     while (true) {
/*  485 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  486 */         return false; 
/*  487 */       if (this.strategy.equals(k, curr) && Objects.equals(v, this.value[pos])) {
/*  488 */         removeEntry(pos);
/*  489 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(byte k, V oldValue, V v) {
/*  496 */     int pos = find(k);
/*  497 */     if (pos < 0 || !Objects.equals(oldValue, this.value[pos]))
/*  498 */       return false; 
/*  499 */     this.value[pos] = v;
/*  500 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(byte k, V v) {
/*  505 */     int pos = find(k);
/*  506 */     if (pos < 0)
/*  507 */       return this.defRetValue; 
/*  508 */     V oldValue = this.value[pos];
/*  509 */     this.value[pos] = v;
/*  510 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(byte k, IntFunction<? extends V> mappingFunction) {
/*  515 */     Objects.requireNonNull(mappingFunction);
/*  516 */     int pos = find(k);
/*  517 */     if (pos >= 0)
/*  518 */       return this.value[pos]; 
/*  519 */     V newValue = mappingFunction.apply(k);
/*  520 */     insert(-pos - 1, k, newValue);
/*  521 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfPresent(byte k, BiFunction<? super Byte, ? super V, ? extends V> remappingFunction) {
/*  527 */     Objects.requireNonNull(remappingFunction);
/*  528 */     int pos = find(k);
/*  529 */     if (pos < 0)
/*  530 */       return this.defRetValue; 
/*  531 */     V newValue = remappingFunction.apply(Byte.valueOf(k), this.value[pos]);
/*  532 */     if (newValue == null) {
/*  533 */       if (this.strategy.equals(k, (byte)0)) {
/*  534 */         removeNullEntry();
/*      */       } else {
/*  536 */         removeEntry(pos);
/*  537 */       }  return this.defRetValue;
/*      */     } 
/*  539 */     this.value[pos] = newValue; return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V compute(byte k, BiFunction<? super Byte, ? super V, ? extends V> remappingFunction) {
/*  545 */     Objects.requireNonNull(remappingFunction);
/*  546 */     int pos = find(k);
/*  547 */     V newValue = remappingFunction.apply(Byte.valueOf(k), (pos >= 0) ? this.value[pos] : null);
/*  548 */     if (newValue == null) {
/*  549 */       if (pos >= 0)
/*  550 */         if (this.strategy.equals(k, (byte)0)) {
/*  551 */           removeNullEntry();
/*      */         } else {
/*  553 */           removeEntry(pos);
/*      */         }  
/*  555 */       return this.defRetValue;
/*      */     } 
/*  557 */     V newVal = newValue;
/*  558 */     if (pos < 0) {
/*  559 */       insert(-pos - 1, k, newVal);
/*  560 */       return newVal;
/*      */     } 
/*  562 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V merge(byte k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*  568 */     Objects.requireNonNull(remappingFunction);
/*  569 */     int pos = find(k);
/*  570 */     if (pos < 0 || this.value[pos] == null) {
/*  571 */       if (v == null)
/*  572 */         return this.defRetValue; 
/*  573 */       insert(-pos - 1, k, v);
/*  574 */       return v;
/*      */     } 
/*  576 */     V newValue = remappingFunction.apply(this.value[pos], v);
/*  577 */     if (newValue == null) {
/*  578 */       if (this.strategy.equals(k, (byte)0)) {
/*  579 */         removeNullEntry();
/*      */       } else {
/*  581 */         removeEntry(pos);
/*  582 */       }  return this.defRetValue;
/*      */     } 
/*  584 */     this.value[pos] = newValue; return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  595 */     if (this.size == 0)
/*      */       return; 
/*  597 */     this.size = 0;
/*  598 */     this.containsNullKey = false;
/*  599 */     Arrays.fill(this.key, (byte)0);
/*  600 */     Arrays.fill((Object[])this.value, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  604 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  608 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Byte2ObjectMap.Entry<V>, Map.Entry<Byte, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  620 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public byte getByteKey() {
/*  626 */       return Byte2ObjectOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  630 */       return Byte2ObjectOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  634 */       V oldValue = Byte2ObjectOpenCustomHashMap.this.value[this.index];
/*  635 */       Byte2ObjectOpenCustomHashMap.this.value[this.index] = v;
/*  636 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte getKey() {
/*  646 */       return Byte.valueOf(Byte2ObjectOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  651 */       if (!(o instanceof Map.Entry))
/*  652 */         return false; 
/*  653 */       Map.Entry<Byte, V> e = (Map.Entry<Byte, V>)o;
/*  654 */       return (Byte2ObjectOpenCustomHashMap.this.strategy.equals(Byte2ObjectOpenCustomHashMap.this.key[this.index], ((Byte)e.getKey()).byteValue()) && 
/*  655 */         Objects.equals(Byte2ObjectOpenCustomHashMap.this.value[this.index], e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  659 */       return Byte2ObjectOpenCustomHashMap.this.strategy.hashCode(Byte2ObjectOpenCustomHashMap.this.key[this.index]) ^ ((Byte2ObjectOpenCustomHashMap.this.value[this.index] == null) ? 0 : Byte2ObjectOpenCustomHashMap.this.value[this.index].hashCode());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  663 */       return Byte2ObjectOpenCustomHashMap.this.key[this.index] + "=>" + Byte2ObjectOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  673 */     int pos = Byte2ObjectOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  680 */     int last = -1;
/*      */     
/*  682 */     int c = Byte2ObjectOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  686 */     boolean mustReturnNullKey = Byte2ObjectOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ByteArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  693 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  696 */       if (!hasNext())
/*  697 */         throw new NoSuchElementException(); 
/*  698 */       this.c--;
/*  699 */       if (this.mustReturnNullKey) {
/*  700 */         this.mustReturnNullKey = false;
/*  701 */         return this.last = Byte2ObjectOpenCustomHashMap.this.n;
/*      */       } 
/*  703 */       byte[] key = Byte2ObjectOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  705 */         if (--this.pos < 0) {
/*      */           
/*  707 */           this.last = Integer.MIN_VALUE;
/*  708 */           byte k = this.wrapped.getByte(-this.pos - 1);
/*  709 */           int p = HashCommon.mix(Byte2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Byte2ObjectOpenCustomHashMap.this.mask;
/*  710 */           while (!Byte2ObjectOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  711 */             p = p + 1 & Byte2ObjectOpenCustomHashMap.this.mask; 
/*  712 */           return p;
/*      */         } 
/*  714 */         if (key[this.pos] != 0) {
/*  715 */           return this.last = this.pos;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void shiftKeys(int pos) {
/*  729 */       byte[] key = Byte2ObjectOpenCustomHashMap.this.key; while (true) {
/*      */         byte curr; int last;
/*  731 */         pos = (last = pos) + 1 & Byte2ObjectOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  733 */           if ((curr = key[pos]) == 0) {
/*  734 */             key[last] = 0;
/*  735 */             Byte2ObjectOpenCustomHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  738 */           int slot = HashCommon.mix(Byte2ObjectOpenCustomHashMap.this.strategy.hashCode(curr)) & Byte2ObjectOpenCustomHashMap.this.mask;
/*  739 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  741 */           pos = pos + 1 & Byte2ObjectOpenCustomHashMap.this.mask;
/*      */         } 
/*  743 */         if (pos < last) {
/*  744 */           if (this.wrapped == null)
/*  745 */             this.wrapped = new ByteArrayList(2); 
/*  746 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  748 */         key[last] = curr;
/*  749 */         Byte2ObjectOpenCustomHashMap.this.value[last] = Byte2ObjectOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  753 */       if (this.last == -1)
/*  754 */         throw new IllegalStateException(); 
/*  755 */       if (this.last == Byte2ObjectOpenCustomHashMap.this.n) {
/*  756 */         Byte2ObjectOpenCustomHashMap.this.containsNullKey = false;
/*  757 */         Byte2ObjectOpenCustomHashMap.this.value[Byte2ObjectOpenCustomHashMap.this.n] = null;
/*  758 */       } else if (this.pos >= 0) {
/*  759 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  762 */         Byte2ObjectOpenCustomHashMap.this.remove(this.wrapped.getByte(-this.pos - 1));
/*  763 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  766 */       Byte2ObjectOpenCustomHashMap.this.size--;
/*  767 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  772 */       int i = n;
/*  773 */       while (i-- != 0 && hasNext())
/*  774 */         nextEntry(); 
/*  775 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Byte2ObjectMap.Entry<V>> { private Byte2ObjectOpenCustomHashMap<V>.MapEntry entry;
/*      */     
/*      */     public Byte2ObjectOpenCustomHashMap<V>.MapEntry next() {
/*  782 */       return this.entry = new Byte2ObjectOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  786 */       super.remove();
/*  787 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Byte2ObjectMap.Entry<V>> { private FastEntryIterator() {
/*  791 */       this.entry = new Byte2ObjectOpenCustomHashMap.MapEntry();
/*      */     } private final Byte2ObjectOpenCustomHashMap<V>.MapEntry entry;
/*      */     public Byte2ObjectOpenCustomHashMap<V>.MapEntry next() {
/*  794 */       this.entry.index = nextEntry();
/*  795 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Byte2ObjectMap.Entry<V>> implements Byte2ObjectMap.FastEntrySet<V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Byte2ObjectMap.Entry<V>> iterator() {
/*  801 */       return new Byte2ObjectOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Byte2ObjectMap.Entry<V>> fastIterator() {
/*  805 */       return new Byte2ObjectOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  810 */       if (!(o instanceof Map.Entry))
/*  811 */         return false; 
/*  812 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  813 */       if (e.getKey() == null || !(e.getKey() instanceof Byte))
/*  814 */         return false; 
/*  815 */       byte k = ((Byte)e.getKey()).byteValue();
/*  816 */       V v = (V)e.getValue();
/*  817 */       if (Byte2ObjectOpenCustomHashMap.this.strategy.equals(k, (byte)0)) {
/*  818 */         return (Byte2ObjectOpenCustomHashMap.this.containsNullKey && Objects.equals(Byte2ObjectOpenCustomHashMap.this.value[Byte2ObjectOpenCustomHashMap.this.n], v));
/*      */       }
/*  820 */       byte[] key = Byte2ObjectOpenCustomHashMap.this.key;
/*      */       byte curr;
/*      */       int pos;
/*  823 */       if ((curr = key[pos = HashCommon.mix(Byte2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Byte2ObjectOpenCustomHashMap.this.mask]) == 0)
/*  824 */         return false; 
/*  825 */       if (Byte2ObjectOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  826 */         return Objects.equals(Byte2ObjectOpenCustomHashMap.this.value[pos], v);
/*      */       }
/*      */       while (true) {
/*  829 */         if ((curr = key[pos = pos + 1 & Byte2ObjectOpenCustomHashMap.this.mask]) == 0)
/*  830 */           return false; 
/*  831 */         if (Byte2ObjectOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  832 */           return Objects.equals(Byte2ObjectOpenCustomHashMap.this.value[pos], v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  838 */       if (!(o instanceof Map.Entry))
/*  839 */         return false; 
/*  840 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  841 */       if (e.getKey() == null || !(e.getKey() instanceof Byte))
/*  842 */         return false; 
/*  843 */       byte k = ((Byte)e.getKey()).byteValue();
/*  844 */       V v = (V)e.getValue();
/*  845 */       if (Byte2ObjectOpenCustomHashMap.this.strategy.equals(k, (byte)0)) {
/*  846 */         if (Byte2ObjectOpenCustomHashMap.this.containsNullKey && Objects.equals(Byte2ObjectOpenCustomHashMap.this.value[Byte2ObjectOpenCustomHashMap.this.n], v)) {
/*  847 */           Byte2ObjectOpenCustomHashMap.this.removeNullEntry();
/*  848 */           return true;
/*      */         } 
/*  850 */         return false;
/*      */       } 
/*      */       
/*  853 */       byte[] key = Byte2ObjectOpenCustomHashMap.this.key;
/*      */       byte curr;
/*      */       int pos;
/*  856 */       if ((curr = key[pos = HashCommon.mix(Byte2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Byte2ObjectOpenCustomHashMap.this.mask]) == 0)
/*  857 */         return false; 
/*  858 */       if (Byte2ObjectOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  859 */         if (Objects.equals(Byte2ObjectOpenCustomHashMap.this.value[pos], v)) {
/*  860 */           Byte2ObjectOpenCustomHashMap.this.removeEntry(pos);
/*  861 */           return true;
/*      */         } 
/*  863 */         return false;
/*      */       } 
/*      */       while (true) {
/*  866 */         if ((curr = key[pos = pos + 1 & Byte2ObjectOpenCustomHashMap.this.mask]) == 0)
/*  867 */           return false; 
/*  868 */         if (Byte2ObjectOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  869 */           Objects.equals(Byte2ObjectOpenCustomHashMap.this.value[pos], v)) {
/*  870 */           Byte2ObjectOpenCustomHashMap.this.removeEntry(pos);
/*  871 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  878 */       return Byte2ObjectOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  882 */       Byte2ObjectOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Byte2ObjectMap.Entry<V>> consumer) {
/*  887 */       if (Byte2ObjectOpenCustomHashMap.this.containsNullKey)
/*  888 */         consumer.accept(new AbstractByte2ObjectMap.BasicEntry<>(Byte2ObjectOpenCustomHashMap.this.key[Byte2ObjectOpenCustomHashMap.this.n], Byte2ObjectOpenCustomHashMap.this.value[Byte2ObjectOpenCustomHashMap.this.n])); 
/*  889 */       for (int pos = Byte2ObjectOpenCustomHashMap.this.n; pos-- != 0;) {
/*  890 */         if (Byte2ObjectOpenCustomHashMap.this.key[pos] != 0)
/*  891 */           consumer.accept(new AbstractByte2ObjectMap.BasicEntry<>(Byte2ObjectOpenCustomHashMap.this.key[pos], Byte2ObjectOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Byte2ObjectMap.Entry<V>> consumer) {
/*  896 */       AbstractByte2ObjectMap.BasicEntry<V> entry = new AbstractByte2ObjectMap.BasicEntry<>();
/*  897 */       if (Byte2ObjectOpenCustomHashMap.this.containsNullKey) {
/*  898 */         entry.key = Byte2ObjectOpenCustomHashMap.this.key[Byte2ObjectOpenCustomHashMap.this.n];
/*  899 */         entry.value = Byte2ObjectOpenCustomHashMap.this.value[Byte2ObjectOpenCustomHashMap.this.n];
/*  900 */         consumer.accept(entry);
/*      */       } 
/*  902 */       for (int pos = Byte2ObjectOpenCustomHashMap.this.n; pos-- != 0;) {
/*  903 */         if (Byte2ObjectOpenCustomHashMap.this.key[pos] != 0) {
/*  904 */           entry.key = Byte2ObjectOpenCustomHashMap.this.key[pos];
/*  905 */           entry.value = Byte2ObjectOpenCustomHashMap.this.value[pos];
/*  906 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Byte2ObjectMap.FastEntrySet<V> byte2ObjectEntrySet() {
/*  912 */     if (this.entries == null)
/*  913 */       this.entries = new MapEntrySet(); 
/*  914 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends MapIterator
/*      */     implements ByteIterator
/*      */   {
/*      */     public byte nextByte() {
/*  931 */       return Byte2ObjectOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractByteSet { private KeySet() {}
/*      */     
/*      */     public ByteIterator iterator() {
/*  937 */       return new Byte2ObjectOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/*  942 */       if (Byte2ObjectOpenCustomHashMap.this.containsNullKey)
/*  943 */         consumer.accept(Byte2ObjectOpenCustomHashMap.this.key[Byte2ObjectOpenCustomHashMap.this.n]); 
/*  944 */       for (int pos = Byte2ObjectOpenCustomHashMap.this.n; pos-- != 0; ) {
/*  945 */         byte k = Byte2ObjectOpenCustomHashMap.this.key[pos];
/*  946 */         if (k != 0)
/*  947 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  952 */       return Byte2ObjectOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(byte k) {
/*  956 */       return Byte2ObjectOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(byte k) {
/*  960 */       int oldSize = Byte2ObjectOpenCustomHashMap.this.size;
/*  961 */       Byte2ObjectOpenCustomHashMap.this.remove(k);
/*  962 */       return (Byte2ObjectOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  966 */       Byte2ObjectOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ByteSet keySet() {
/*  971 */     if (this.keys == null)
/*  972 */       this.keys = new KeySet(); 
/*  973 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends MapIterator
/*      */     implements ObjectIterator<V>
/*      */   {
/*      */     public V next() {
/*  990 */       return Byte2ObjectOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectCollection<V> values() {
/*  995 */     if (this.values == null)
/*  996 */       this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/*  999 */             return new Byte2ObjectOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1003 */             return Byte2ObjectOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/* 1007 */             return Byte2ObjectOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1011 */             Byte2ObjectOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer)
/*      */           {
/* 1016 */             if (Byte2ObjectOpenCustomHashMap.this.containsNullKey)
/* 1017 */               consumer.accept(Byte2ObjectOpenCustomHashMap.this.value[Byte2ObjectOpenCustomHashMap.this.n]); 
/* 1018 */             for (int pos = Byte2ObjectOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1019 */               if (Byte2ObjectOpenCustomHashMap.this.key[pos] != 0)
/* 1020 */                 consumer.accept(Byte2ObjectOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1023 */     return this.values;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean trim() {
/* 1040 */     return trim(this.size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean trim(int n) {
/* 1064 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1065 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1066 */       return true; 
/*      */     try {
/* 1068 */       rehash(l);
/* 1069 */     } catch (OutOfMemoryError cantDoIt) {
/* 1070 */       return false;
/*      */     } 
/* 1072 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void rehash(int newN) {
/* 1088 */     byte[] key = this.key;
/* 1089 */     V[] value = this.value;
/* 1090 */     int mask = newN - 1;
/* 1091 */     byte[] newKey = new byte[newN + 1];
/* 1092 */     V[] newValue = (V[])new Object[newN + 1];
/* 1093 */     int i = this.n;
/* 1094 */     for (int j = realSize(); j-- != 0; ) {
/* 1095 */       while (key[--i] == 0); int pos;
/* 1096 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0)
/*      */       {
/* 1098 */         while (newKey[pos = pos + 1 & mask] != 0); } 
/* 1099 */       newKey[pos] = key[i];
/* 1100 */       newValue[pos] = value[i];
/*      */     } 
/* 1102 */     newValue[newN] = value[this.n];
/* 1103 */     this.n = newN;
/* 1104 */     this.mask = mask;
/* 1105 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1106 */     this.key = newKey;
/* 1107 */     this.value = newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ObjectOpenCustomHashMap<V> clone() {
/*      */     Byte2ObjectOpenCustomHashMap<V> c;
/*      */     try {
/* 1124 */       c = (Byte2ObjectOpenCustomHashMap<V>)super.clone();
/* 1125 */     } catch (CloneNotSupportedException cantHappen) {
/* 1126 */       throw new InternalError();
/*      */     } 
/* 1128 */     c.keys = null;
/* 1129 */     c.values = null;
/* 1130 */     c.entries = null;
/* 1131 */     c.containsNullKey = this.containsNullKey;
/* 1132 */     c.key = (byte[])this.key.clone();
/* 1133 */     c.value = (V[])this.value.clone();
/* 1134 */     c.strategy = this.strategy;
/* 1135 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1148 */     int h = 0;
/* 1149 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1150 */       while (this.key[i] == 0)
/* 1151 */         i++; 
/* 1152 */       t = this.strategy.hashCode(this.key[i]);
/* 1153 */       if (this != this.value[i])
/* 1154 */         t ^= (this.value[i] == null) ? 0 : this.value[i].hashCode(); 
/* 1155 */       h += t;
/* 1156 */       i++;
/*      */     } 
/*      */     
/* 1159 */     if (this.containsNullKey)
/* 1160 */       h += (this.value[this.n] == null) ? 0 : this.value[this.n].hashCode(); 
/* 1161 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1164 */     byte[] key = this.key;
/* 1165 */     V[] value = this.value;
/* 1166 */     MapIterator i = new MapIterator();
/* 1167 */     s.defaultWriteObject();
/* 1168 */     for (int j = this.size; j-- != 0; ) {
/* 1169 */       int e = i.nextEntry();
/* 1170 */       s.writeByte(key[e]);
/* 1171 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1176 */     s.defaultReadObject();
/* 1177 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1178 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1179 */     this.mask = this.n - 1;
/* 1180 */     byte[] key = this.key = new byte[this.n + 1];
/* 1181 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1184 */     for (int i = this.size; i-- != 0; ) {
/* 1185 */       int pos; byte k = s.readByte();
/* 1186 */       V v = (V)s.readObject();
/* 1187 */       if (this.strategy.equals(k, (byte)0)) {
/* 1188 */         pos = this.n;
/* 1189 */         this.containsNullKey = true;
/*      */       } else {
/* 1191 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1192 */         while (key[pos] != 0)
/* 1193 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1195 */       key[pos] = k;
/* 1196 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2ObjectOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */