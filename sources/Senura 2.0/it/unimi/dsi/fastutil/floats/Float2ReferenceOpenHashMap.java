/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.DoubleFunction;
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
/*      */ public class Float2ReferenceOpenHashMap<V>
/*      */   extends AbstractFloat2ReferenceMap<V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Float2ReferenceMap.FastEntrySet<V> entries;
/*      */   protected transient FloatSet keys;
/*      */   protected transient ReferenceCollection<V> values;
/*      */   
/*      */   public Float2ReferenceOpenHashMap(int expected, float f) {
/*   99 */     if (f <= 0.0F || f > 1.0F)
/*  100 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  101 */     if (expected < 0)
/*  102 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  103 */     this.f = f;
/*  104 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  105 */     this.mask = this.n - 1;
/*  106 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  107 */     this.key = new float[this.n + 1];
/*  108 */     this.value = (V[])new Object[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ReferenceOpenHashMap(int expected) {
/*  117 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ReferenceOpenHashMap() {
/*  125 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ReferenceOpenHashMap(Map<? extends Float, ? extends V> m, float f) {
/*  136 */     this(m.size(), f);
/*  137 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ReferenceOpenHashMap(Map<? extends Float, ? extends V> m) {
/*  147 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ReferenceOpenHashMap(Float2ReferenceMap<V> m, float f) {
/*  158 */     this(m.size(), f);
/*  159 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ReferenceOpenHashMap(Float2ReferenceMap<V> m) {
/*  169 */     this(m, 0.75F);
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
/*      */   public Float2ReferenceOpenHashMap(float[] k, V[] v, float f) {
/*  184 */     this(k.length, f);
/*  185 */     if (k.length != v.length) {
/*  186 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  188 */     for (int i = 0; i < k.length; i++) {
/*  189 */       put(k[i], v[i]);
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
/*      */   public Float2ReferenceOpenHashMap(float[] k, V[] v) {
/*  203 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  206 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  209 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  210 */     if (needed > this.n)
/*  211 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  214 */     int needed = (int)Math.min(1073741824L, 
/*  215 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  216 */     if (needed > this.n)
/*  217 */       rehash(needed); 
/*      */   }
/*      */   private V removeEntry(int pos) {
/*  220 */     V oldValue = this.value[pos];
/*  221 */     this.value[pos] = null;
/*  222 */     this.size--;
/*  223 */     shiftKeys(pos);
/*  224 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  225 */       rehash(this.n / 2); 
/*  226 */     return oldValue;
/*      */   }
/*      */   private V removeNullEntry() {
/*  229 */     this.containsNullKey = false;
/*  230 */     V oldValue = this.value[this.n];
/*  231 */     this.value[this.n] = null;
/*  232 */     this.size--;
/*  233 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  234 */       rehash(this.n / 2); 
/*  235 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Float, ? extends V> m) {
/*  239 */     if (this.f <= 0.5D) {
/*  240 */       ensureCapacity(m.size());
/*      */     } else {
/*  242 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  244 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(float k) {
/*  248 */     if (Float.floatToIntBits(k) == 0) {
/*  249 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  251 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  254 */     if (Float.floatToIntBits(
/*  255 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  257 */       return -(pos + 1); } 
/*  258 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  259 */       return pos;
/*      */     }
/*      */     while (true) {
/*  262 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  263 */         return -(pos + 1); 
/*  264 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  265 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, float k, V v) {
/*  269 */     if (pos == this.n)
/*  270 */       this.containsNullKey = true; 
/*  271 */     this.key[pos] = k;
/*  272 */     this.value[pos] = v;
/*  273 */     if (this.size++ >= this.maxFill) {
/*  274 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(float k, V v) {
/*  280 */     int pos = find(k);
/*  281 */     if (pos < 0) {
/*  282 */       insert(-pos - 1, k, v);
/*  283 */       return this.defRetValue;
/*      */     } 
/*  285 */     V oldValue = this.value[pos];
/*  286 */     this.value[pos] = v;
/*  287 */     return oldValue;
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
/*  300 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  302 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  304 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  305 */           key[last] = 0.0F;
/*  306 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  309 */         int slot = HashCommon.mix(HashCommon.float2int(curr)) & this.mask;
/*  310 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  312 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  314 */       key[last] = curr;
/*  315 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V remove(float k) {
/*  321 */     if (Float.floatToIntBits(k) == 0) {
/*  322 */       if (this.containsNullKey)
/*  323 */         return removeNullEntry(); 
/*  324 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  327 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  330 */     if (Float.floatToIntBits(
/*  331 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  333 */       return this.defRetValue; } 
/*  334 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  335 */       return removeEntry(pos); 
/*      */     while (true) {
/*  337 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  338 */         return this.defRetValue; 
/*  339 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  340 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V get(float k) {
/*  346 */     if (Float.floatToIntBits(k) == 0) {
/*  347 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  349 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  352 */     if (Float.floatToIntBits(
/*  353 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  355 */       return this.defRetValue; } 
/*  356 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  357 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  360 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  361 */         return this.defRetValue; 
/*  362 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  363 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(float k) {
/*  369 */     if (Float.floatToIntBits(k) == 0) {
/*  370 */       return this.containsNullKey;
/*      */     }
/*  372 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  375 */     if (Float.floatToIntBits(
/*  376 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  378 */       return false; } 
/*  379 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  380 */       return true;
/*      */     }
/*      */     while (true) {
/*  383 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  384 */         return false; 
/*  385 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  386 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  391 */     V[] value = this.value;
/*  392 */     float[] key = this.key;
/*  393 */     if (this.containsNullKey && value[this.n] == v)
/*  394 */       return true; 
/*  395 */     for (int i = this.n; i-- != 0;) {
/*  396 */       if (Float.floatToIntBits(key[i]) != 0 && value[i] == v)
/*  397 */         return true; 
/*  398 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(float k, V defaultValue) {
/*  404 */     if (Float.floatToIntBits(k) == 0) {
/*  405 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  407 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  410 */     if (Float.floatToIntBits(
/*  411 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  413 */       return defaultValue; } 
/*  414 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  415 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  418 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  419 */         return defaultValue; 
/*  420 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  421 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V putIfAbsent(float k, V v) {
/*  427 */     int pos = find(k);
/*  428 */     if (pos >= 0)
/*  429 */       return this.value[pos]; 
/*  430 */     insert(-pos - 1, k, v);
/*  431 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, Object v) {
/*  437 */     if (Float.floatToIntBits(k) == 0) {
/*  438 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  439 */         removeNullEntry();
/*  440 */         return true;
/*      */       } 
/*  442 */       return false;
/*      */     } 
/*      */     
/*  445 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  448 */     if (Float.floatToIntBits(
/*  449 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  451 */       return false; } 
/*  452 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  453 */       removeEntry(pos);
/*  454 */       return true;
/*      */     } 
/*      */     while (true) {
/*  457 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  458 */         return false; 
/*  459 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  460 */         removeEntry(pos);
/*  461 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(float k, V oldValue, V v) {
/*  468 */     int pos = find(k);
/*  469 */     if (pos < 0 || oldValue != this.value[pos])
/*  470 */       return false; 
/*  471 */     this.value[pos] = v;
/*  472 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(float k, V v) {
/*  477 */     int pos = find(k);
/*  478 */     if (pos < 0)
/*  479 */       return this.defRetValue; 
/*  480 */     V oldValue = this.value[pos];
/*  481 */     this.value[pos] = v;
/*  482 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(float k, DoubleFunction<? extends V> mappingFunction) {
/*  487 */     Objects.requireNonNull(mappingFunction);
/*  488 */     int pos = find(k);
/*  489 */     if (pos >= 0)
/*  490 */       return this.value[pos]; 
/*  491 */     V newValue = mappingFunction.apply(k);
/*  492 */     insert(-pos - 1, k, newValue);
/*  493 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfPresent(float k, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/*  499 */     Objects.requireNonNull(remappingFunction);
/*  500 */     int pos = find(k);
/*  501 */     if (pos < 0)
/*  502 */       return this.defRetValue; 
/*  503 */     V newValue = remappingFunction.apply(Float.valueOf(k), this.value[pos]);
/*  504 */     if (newValue == null) {
/*  505 */       if (Float.floatToIntBits(k) == 0) {
/*  506 */         removeNullEntry();
/*      */       } else {
/*  508 */         removeEntry(pos);
/*  509 */       }  return this.defRetValue;
/*      */     } 
/*  511 */     this.value[pos] = newValue; return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V compute(float k, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/*  517 */     Objects.requireNonNull(remappingFunction);
/*  518 */     int pos = find(k);
/*  519 */     V newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? this.value[pos] : null);
/*  520 */     if (newValue == null) {
/*  521 */       if (pos >= 0)
/*  522 */         if (Float.floatToIntBits(k) == 0) {
/*  523 */           removeNullEntry();
/*      */         } else {
/*  525 */           removeEntry(pos);
/*      */         }  
/*  527 */       return this.defRetValue;
/*      */     } 
/*  529 */     V newVal = newValue;
/*  530 */     if (pos < 0) {
/*  531 */       insert(-pos - 1, k, newVal);
/*  532 */       return newVal;
/*      */     } 
/*  534 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V merge(float k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*  540 */     Objects.requireNonNull(remappingFunction);
/*  541 */     int pos = find(k);
/*  542 */     if (pos < 0 || this.value[pos] == null) {
/*  543 */       if (v == null)
/*  544 */         return this.defRetValue; 
/*  545 */       insert(-pos - 1, k, v);
/*  546 */       return v;
/*      */     } 
/*  548 */     V newValue = remappingFunction.apply(this.value[pos], v);
/*  549 */     if (newValue == null) {
/*  550 */       if (Float.floatToIntBits(k) == 0) {
/*  551 */         removeNullEntry();
/*      */       } else {
/*  553 */         removeEntry(pos);
/*  554 */       }  return this.defRetValue;
/*      */     } 
/*  556 */     this.value[pos] = newValue; return newValue;
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
/*  567 */     if (this.size == 0)
/*      */       return; 
/*  569 */     this.size = 0;
/*  570 */     this.containsNullKey = false;
/*  571 */     Arrays.fill(this.key, 0.0F);
/*  572 */     Arrays.fill((Object[])this.value, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  576 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  580 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Float2ReferenceMap.Entry<V>, Map.Entry<Float, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  592 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public float getFloatKey() {
/*  598 */       return Float2ReferenceOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  602 */       return Float2ReferenceOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  606 */       V oldValue = Float2ReferenceOpenHashMap.this.value[this.index];
/*  607 */       Float2ReferenceOpenHashMap.this.value[this.index] = v;
/*  608 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getKey() {
/*  618 */       return Float.valueOf(Float2ReferenceOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  623 */       if (!(o instanceof Map.Entry))
/*  624 */         return false; 
/*  625 */       Map.Entry<Float, V> e = (Map.Entry<Float, V>)o;
/*  626 */       return (Float.floatToIntBits(Float2ReferenceOpenHashMap.this.key[this.index]) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && Float2ReferenceOpenHashMap.this.value[this.index] == e
/*  627 */         .getValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  631 */       return HashCommon.float2int(Float2ReferenceOpenHashMap.this.key[this.index]) ^ (
/*  632 */         (Float2ReferenceOpenHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Float2ReferenceOpenHashMap.this.value[this.index]));
/*      */     }
/*      */     
/*      */     public String toString() {
/*  636 */       return Float2ReferenceOpenHashMap.this.key[this.index] + "=>" + Float2ReferenceOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  646 */     int pos = Float2ReferenceOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  653 */     int last = -1;
/*      */     
/*  655 */     int c = Float2ReferenceOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  659 */     boolean mustReturnNullKey = Float2ReferenceOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     FloatArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  666 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  669 */       if (!hasNext())
/*  670 */         throw new NoSuchElementException(); 
/*  671 */       this.c--;
/*  672 */       if (this.mustReturnNullKey) {
/*  673 */         this.mustReturnNullKey = false;
/*  674 */         return this.last = Float2ReferenceOpenHashMap.this.n;
/*      */       } 
/*  676 */       float[] key = Float2ReferenceOpenHashMap.this.key;
/*      */       while (true) {
/*  678 */         if (--this.pos < 0) {
/*      */           
/*  680 */           this.last = Integer.MIN_VALUE;
/*  681 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  682 */           int p = HashCommon.mix(HashCommon.float2int(k)) & Float2ReferenceOpenHashMap.this.mask;
/*  683 */           while (Float.floatToIntBits(k) != Float.floatToIntBits(key[p]))
/*  684 */             p = p + 1 & Float2ReferenceOpenHashMap.this.mask; 
/*  685 */           return p;
/*      */         } 
/*  687 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
/*  688 */           return this.last = this.pos;
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
/*  702 */       float[] key = Float2ReferenceOpenHashMap.this.key; while (true) {
/*      */         float curr; int last;
/*  704 */         pos = (last = pos) + 1 & Float2ReferenceOpenHashMap.this.mask;
/*      */         while (true) {
/*  706 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  707 */             key[last] = 0.0F;
/*  708 */             Float2ReferenceOpenHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  711 */           int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2ReferenceOpenHashMap.this.mask;
/*      */           
/*  713 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  715 */           pos = pos + 1 & Float2ReferenceOpenHashMap.this.mask;
/*      */         } 
/*  717 */         if (pos < last) {
/*  718 */           if (this.wrapped == null)
/*  719 */             this.wrapped = new FloatArrayList(2); 
/*  720 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  722 */         key[last] = curr;
/*  723 */         Float2ReferenceOpenHashMap.this.value[last] = Float2ReferenceOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  727 */       if (this.last == -1)
/*  728 */         throw new IllegalStateException(); 
/*  729 */       if (this.last == Float2ReferenceOpenHashMap.this.n) {
/*  730 */         Float2ReferenceOpenHashMap.this.containsNullKey = false;
/*  731 */         Float2ReferenceOpenHashMap.this.value[Float2ReferenceOpenHashMap.this.n] = null;
/*  732 */       } else if (this.pos >= 0) {
/*  733 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  736 */         Float2ReferenceOpenHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
/*  737 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  740 */       Float2ReferenceOpenHashMap.this.size--;
/*  741 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  746 */       int i = n;
/*  747 */       while (i-- != 0 && hasNext())
/*  748 */         nextEntry(); 
/*  749 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Float2ReferenceMap.Entry<V>> { private Float2ReferenceOpenHashMap<V>.MapEntry entry;
/*      */     
/*      */     public Float2ReferenceOpenHashMap<V>.MapEntry next() {
/*  756 */       return this.entry = new Float2ReferenceOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  760 */       super.remove();
/*  761 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Float2ReferenceMap.Entry<V>> { private FastEntryIterator() {
/*  765 */       this.entry = new Float2ReferenceOpenHashMap.MapEntry();
/*      */     } private final Float2ReferenceOpenHashMap<V>.MapEntry entry;
/*      */     public Float2ReferenceOpenHashMap<V>.MapEntry next() {
/*  768 */       this.entry.index = nextEntry();
/*  769 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Float2ReferenceMap.Entry<V>> implements Float2ReferenceMap.FastEntrySet<V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Float2ReferenceMap.Entry<V>> iterator() {
/*  775 */       return new Float2ReferenceOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Float2ReferenceMap.Entry<V>> fastIterator() {
/*  779 */       return new Float2ReferenceOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  784 */       if (!(o instanceof Map.Entry))
/*  785 */         return false; 
/*  786 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  787 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  788 */         return false; 
/*  789 */       float k = ((Float)e.getKey()).floatValue();
/*  790 */       V v = (V)e.getValue();
/*  791 */       if (Float.floatToIntBits(k) == 0) {
/*  792 */         return (Float2ReferenceOpenHashMap.this.containsNullKey && Float2ReferenceOpenHashMap.this.value[Float2ReferenceOpenHashMap.this.n] == v);
/*      */       }
/*  794 */       float[] key = Float2ReferenceOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  797 */       if (Float.floatToIntBits(
/*  798 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2ReferenceOpenHashMap.this.mask]) == 0)
/*      */       {
/*  800 */         return false; } 
/*  801 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  802 */         return (Float2ReferenceOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  805 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ReferenceOpenHashMap.this.mask]) == 0)
/*  806 */           return false; 
/*  807 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  808 */           return (Float2ReferenceOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  814 */       if (!(o instanceof Map.Entry))
/*  815 */         return false; 
/*  816 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  817 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/*  818 */         return false; 
/*  819 */       float k = ((Float)e.getKey()).floatValue();
/*  820 */       V v = (V)e.getValue();
/*  821 */       if (Float.floatToIntBits(k) == 0) {
/*  822 */         if (Float2ReferenceOpenHashMap.this.containsNullKey && Float2ReferenceOpenHashMap.this.value[Float2ReferenceOpenHashMap.this.n] == v) {
/*  823 */           Float2ReferenceOpenHashMap.this.removeNullEntry();
/*  824 */           return true;
/*      */         } 
/*  826 */         return false;
/*      */       } 
/*      */       
/*  829 */       float[] key = Float2ReferenceOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  832 */       if (Float.floatToIntBits(
/*  833 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2ReferenceOpenHashMap.this.mask]) == 0)
/*      */       {
/*  835 */         return false; } 
/*  836 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  837 */         if (Float2ReferenceOpenHashMap.this.value[pos] == v) {
/*  838 */           Float2ReferenceOpenHashMap.this.removeEntry(pos);
/*  839 */           return true;
/*      */         } 
/*  841 */         return false;
/*      */       } 
/*      */       while (true) {
/*  844 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ReferenceOpenHashMap.this.mask]) == 0)
/*  845 */           return false; 
/*  846 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && 
/*  847 */           Float2ReferenceOpenHashMap.this.value[pos] == v) {
/*  848 */           Float2ReferenceOpenHashMap.this.removeEntry(pos);
/*  849 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  856 */       return Float2ReferenceOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  860 */       Float2ReferenceOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2ReferenceMap.Entry<V>> consumer) {
/*  865 */       if (Float2ReferenceOpenHashMap.this.containsNullKey)
/*  866 */         consumer.accept(new AbstractFloat2ReferenceMap.BasicEntry<>(Float2ReferenceOpenHashMap.this.key[Float2ReferenceOpenHashMap.this.n], Float2ReferenceOpenHashMap.this.value[Float2ReferenceOpenHashMap.this.n])); 
/*  867 */       for (int pos = Float2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/*  868 */         if (Float.floatToIntBits(Float2ReferenceOpenHashMap.this.key[pos]) != 0)
/*  869 */           consumer.accept(new AbstractFloat2ReferenceMap.BasicEntry<>(Float2ReferenceOpenHashMap.this.key[pos], Float2ReferenceOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2ReferenceMap.Entry<V>> consumer) {
/*  874 */       AbstractFloat2ReferenceMap.BasicEntry<V> entry = new AbstractFloat2ReferenceMap.BasicEntry<>();
/*  875 */       if (Float2ReferenceOpenHashMap.this.containsNullKey) {
/*  876 */         entry.key = Float2ReferenceOpenHashMap.this.key[Float2ReferenceOpenHashMap.this.n];
/*  877 */         entry.value = Float2ReferenceOpenHashMap.this.value[Float2ReferenceOpenHashMap.this.n];
/*  878 */         consumer.accept(entry);
/*      */       } 
/*  880 */       for (int pos = Float2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/*  881 */         if (Float.floatToIntBits(Float2ReferenceOpenHashMap.this.key[pos]) != 0) {
/*  882 */           entry.key = Float2ReferenceOpenHashMap.this.key[pos];
/*  883 */           entry.value = Float2ReferenceOpenHashMap.this.value[pos];
/*  884 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Float2ReferenceMap.FastEntrySet<V> float2ReferenceEntrySet() {
/*  890 */     if (this.entries == null)
/*  891 */       this.entries = new MapEntrySet(); 
/*  892 */     return this.entries;
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
/*      */     implements FloatIterator
/*      */   {
/*      */     public float nextFloat() {
/*  909 */       return Float2ReferenceOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSet { private KeySet() {}
/*      */     
/*      */     public FloatIterator iterator() {
/*  915 */       return new Float2ReferenceOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/*  920 */       if (Float2ReferenceOpenHashMap.this.containsNullKey)
/*  921 */         consumer.accept(Float2ReferenceOpenHashMap.this.key[Float2ReferenceOpenHashMap.this.n]); 
/*  922 */       for (int pos = Float2ReferenceOpenHashMap.this.n; pos-- != 0; ) {
/*  923 */         float k = Float2ReferenceOpenHashMap.this.key[pos];
/*  924 */         if (Float.floatToIntBits(k) != 0)
/*  925 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  930 */       return Float2ReferenceOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(float k) {
/*  934 */       return Float2ReferenceOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(float k) {
/*  938 */       int oldSize = Float2ReferenceOpenHashMap.this.size;
/*  939 */       Float2ReferenceOpenHashMap.this.remove(k);
/*  940 */       return (Float2ReferenceOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  944 */       Float2ReferenceOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public FloatSet keySet() {
/*  949 */     if (this.keys == null)
/*  950 */       this.keys = new KeySet(); 
/*  951 */     return this.keys;
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
/*  968 */       return Float2ReferenceOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ReferenceCollection<V> values() {
/*  973 */     if (this.values == null)
/*  974 */       this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/*  977 */             return new Float2ReferenceOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/*  981 */             return Float2ReferenceOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/*  985 */             return Float2ReferenceOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/*  989 */             Float2ReferenceOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer)
/*      */           {
/*  994 */             if (Float2ReferenceOpenHashMap.this.containsNullKey)
/*  995 */               consumer.accept(Float2ReferenceOpenHashMap.this.value[Float2ReferenceOpenHashMap.this.n]); 
/*  996 */             for (int pos = Float2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/*  997 */               if (Float.floatToIntBits(Float2ReferenceOpenHashMap.this.key[pos]) != 0)
/*  998 */                 consumer.accept(Float2ReferenceOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1001 */     return this.values;
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
/* 1018 */     return trim(this.size);
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
/* 1042 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1043 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1044 */       return true; 
/*      */     try {
/* 1046 */       rehash(l);
/* 1047 */     } catch (OutOfMemoryError cantDoIt) {
/* 1048 */       return false;
/*      */     } 
/* 1050 */     return true;
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
/* 1066 */     float[] key = this.key;
/* 1067 */     V[] value = this.value;
/* 1068 */     int mask = newN - 1;
/* 1069 */     float[] newKey = new float[newN + 1];
/* 1070 */     V[] newValue = (V[])new Object[newN + 1];
/* 1071 */     int i = this.n;
/* 1072 */     for (int j = realSize(); j-- != 0; ) {
/* 1073 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 1074 */       if (Float.floatToIntBits(newKey[
/* 1075 */             pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask]) != 0)
/* 1076 */         while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
/* 1077 */       newKey[pos] = key[i];
/* 1078 */       newValue[pos] = value[i];
/*      */     } 
/* 1080 */     newValue[newN] = value[this.n];
/* 1081 */     this.n = newN;
/* 1082 */     this.mask = mask;
/* 1083 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1084 */     this.key = newKey;
/* 1085 */     this.value = newValue;
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
/*      */   public Float2ReferenceOpenHashMap<V> clone() {
/*      */     Float2ReferenceOpenHashMap<V> c;
/*      */     try {
/* 1102 */       c = (Float2ReferenceOpenHashMap<V>)super.clone();
/* 1103 */     } catch (CloneNotSupportedException cantHappen) {
/* 1104 */       throw new InternalError();
/*      */     } 
/* 1106 */     c.keys = null;
/* 1107 */     c.values = null;
/* 1108 */     c.entries = null;
/* 1109 */     c.containsNullKey = this.containsNullKey;
/* 1110 */     c.key = (float[])this.key.clone();
/* 1111 */     c.value = (V[])this.value.clone();
/* 1112 */     return c;
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
/* 1125 */     int h = 0;
/* 1126 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1127 */       while (Float.floatToIntBits(this.key[i]) == 0)
/* 1128 */         i++; 
/* 1129 */       t = HashCommon.float2int(this.key[i]);
/* 1130 */       if (this != this.value[i])
/* 1131 */         t ^= (this.value[i] == null) ? 0 : System.identityHashCode(this.value[i]); 
/* 1132 */       h += t;
/* 1133 */       i++;
/*      */     } 
/*      */     
/* 1136 */     if (this.containsNullKey)
/* 1137 */       h += (this.value[this.n] == null) ? 0 : System.identityHashCode(this.value[this.n]); 
/* 1138 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1141 */     float[] key = this.key;
/* 1142 */     V[] value = this.value;
/* 1143 */     MapIterator i = new MapIterator();
/* 1144 */     s.defaultWriteObject();
/* 1145 */     for (int j = this.size; j-- != 0; ) {
/* 1146 */       int e = i.nextEntry();
/* 1147 */       s.writeFloat(key[e]);
/* 1148 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1153 */     s.defaultReadObject();
/* 1154 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1155 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1156 */     this.mask = this.n - 1;
/* 1157 */     float[] key = this.key = new float[this.n + 1];
/* 1158 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1161 */     for (int i = this.size; i-- != 0; ) {
/* 1162 */       int pos; float k = s.readFloat();
/* 1163 */       V v = (V)s.readObject();
/* 1164 */       if (Float.floatToIntBits(k) == 0) {
/* 1165 */         pos = this.n;
/* 1166 */         this.containsNullKey = true;
/*      */       } else {
/* 1168 */         pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/* 1169 */         while (Float.floatToIntBits(key[pos]) != 0)
/* 1170 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1172 */       key[pos] = k;
/* 1173 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2ReferenceOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */