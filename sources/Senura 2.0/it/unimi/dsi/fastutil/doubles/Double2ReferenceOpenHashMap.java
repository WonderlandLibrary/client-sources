/*      */ package it.unimi.dsi.fastutil.doubles;
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
/*      */ public class Double2ReferenceOpenHashMap<V>
/*      */   extends AbstractDouble2ReferenceMap<V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Double2ReferenceMap.FastEntrySet<V> entries;
/*      */   protected transient DoubleSet keys;
/*      */   protected transient ReferenceCollection<V> values;
/*      */   
/*      */   public Double2ReferenceOpenHashMap(int expected, float f) {
/*   99 */     if (f <= 0.0F || f > 1.0F)
/*  100 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  101 */     if (expected < 0)
/*  102 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  103 */     this.f = f;
/*  104 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  105 */     this.mask = this.n - 1;
/*  106 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  107 */     this.key = new double[this.n + 1];
/*  108 */     this.value = (V[])new Object[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ReferenceOpenHashMap(int expected) {
/*  117 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ReferenceOpenHashMap() {
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
/*      */   public Double2ReferenceOpenHashMap(Map<? extends Double, ? extends V> m, float f) {
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
/*      */   public Double2ReferenceOpenHashMap(Map<? extends Double, ? extends V> m) {
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
/*      */   public Double2ReferenceOpenHashMap(Double2ReferenceMap<V> m, float f) {
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
/*      */   public Double2ReferenceOpenHashMap(Double2ReferenceMap<V> m) {
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
/*      */   public Double2ReferenceOpenHashMap(double[] k, V[] v, float f) {
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
/*      */   public Double2ReferenceOpenHashMap(double[] k, V[] v) {
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
/*      */   public void putAll(Map<? extends Double, ? extends V> m) {
/*  239 */     if (this.f <= 0.5D) {
/*  240 */       ensureCapacity(m.size());
/*      */     } else {
/*  242 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  244 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(double k) {
/*  248 */     if (Double.doubleToLongBits(k) == 0L) {
/*  249 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  251 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  254 */     if (Double.doubleToLongBits(
/*  255 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  257 */       return -(pos + 1); } 
/*  258 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  259 */       return pos;
/*      */     }
/*      */     while (true) {
/*  262 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  263 */         return -(pos + 1); 
/*  264 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  265 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, double k, V v) {
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
/*      */   public V put(double k, V v) {
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
/*  300 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  302 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  304 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  305 */           key[last] = 0.0D;
/*  306 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  309 */         int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & this.mask;
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
/*      */   public V remove(double k) {
/*  321 */     if (Double.doubleToLongBits(k) == 0L) {
/*  322 */       if (this.containsNullKey)
/*  323 */         return removeNullEntry(); 
/*  324 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  327 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  330 */     if (Double.doubleToLongBits(
/*  331 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  333 */       return this.defRetValue; } 
/*  334 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  335 */       return removeEntry(pos); 
/*      */     while (true) {
/*  337 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  338 */         return this.defRetValue; 
/*  339 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  340 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V get(double k) {
/*  346 */     if (Double.doubleToLongBits(k) == 0L) {
/*  347 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  349 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  352 */     if (Double.doubleToLongBits(
/*  353 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  355 */       return this.defRetValue; } 
/*  356 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  357 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  360 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  361 */         return this.defRetValue; 
/*  362 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  363 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(double k) {
/*  369 */     if (Double.doubleToLongBits(k) == 0L) {
/*  370 */       return this.containsNullKey;
/*      */     }
/*  372 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  375 */     if (Double.doubleToLongBits(
/*  376 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  378 */       return false; } 
/*  379 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  380 */       return true;
/*      */     }
/*      */     while (true) {
/*  383 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  384 */         return false; 
/*  385 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  386 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  391 */     V[] value = this.value;
/*  392 */     double[] key = this.key;
/*  393 */     if (this.containsNullKey && value[this.n] == v)
/*  394 */       return true; 
/*  395 */     for (int i = this.n; i-- != 0;) {
/*  396 */       if (Double.doubleToLongBits(key[i]) != 0L && value[i] == v)
/*  397 */         return true; 
/*  398 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(double k, V defaultValue) {
/*  404 */     if (Double.doubleToLongBits(k) == 0L) {
/*  405 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  407 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  410 */     if (Double.doubleToLongBits(
/*  411 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  413 */       return defaultValue; } 
/*  414 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  415 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  418 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  419 */         return defaultValue; 
/*  420 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  421 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V putIfAbsent(double k, V v) {
/*  427 */     int pos = find(k);
/*  428 */     if (pos >= 0)
/*  429 */       return this.value[pos]; 
/*  430 */     insert(-pos - 1, k, v);
/*  431 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, Object v) {
/*  437 */     if (Double.doubleToLongBits(k) == 0L) {
/*  438 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  439 */         removeNullEntry();
/*  440 */         return true;
/*      */       } 
/*  442 */       return false;
/*      */     } 
/*      */     
/*  445 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  448 */     if (Double.doubleToLongBits(
/*  449 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  451 */       return false; } 
/*  452 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  453 */       removeEntry(pos);
/*  454 */       return true;
/*      */     } 
/*      */     while (true) {
/*  457 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  458 */         return false; 
/*  459 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  460 */         removeEntry(pos);
/*  461 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, V oldValue, V v) {
/*  468 */     int pos = find(k);
/*  469 */     if (pos < 0 || oldValue != this.value[pos])
/*  470 */       return false; 
/*  471 */     this.value[pos] = v;
/*  472 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(double k, V v) {
/*  477 */     int pos = find(k);
/*  478 */     if (pos < 0)
/*  479 */       return this.defRetValue; 
/*  480 */     V oldValue = this.value[pos];
/*  481 */     this.value[pos] = v;
/*  482 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(double k, DoubleFunction<? extends V> mappingFunction) {
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
/*      */   public V computeIfPresent(double k, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/*  499 */     Objects.requireNonNull(remappingFunction);
/*  500 */     int pos = find(k);
/*  501 */     if (pos < 0)
/*  502 */       return this.defRetValue; 
/*  503 */     V newValue = remappingFunction.apply(Double.valueOf(k), this.value[pos]);
/*  504 */     if (newValue == null) {
/*  505 */       if (Double.doubleToLongBits(k) == 0L) {
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
/*      */   public V compute(double k, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/*  517 */     Objects.requireNonNull(remappingFunction);
/*  518 */     int pos = find(k);
/*  519 */     V newValue = remappingFunction.apply(Double.valueOf(k), (pos >= 0) ? this.value[pos] : null);
/*  520 */     if (newValue == null) {
/*  521 */       if (pos >= 0)
/*  522 */         if (Double.doubleToLongBits(k) == 0L) {
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
/*      */   public V merge(double k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
/*  550 */       if (Double.doubleToLongBits(k) == 0L) {
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
/*  571 */     Arrays.fill(this.key, 0.0D);
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
/*      */     implements Double2ReferenceMap.Entry<V>, Map.Entry<Double, V>
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
/*      */     public double getDoubleKey() {
/*  598 */       return Double2ReferenceOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  602 */       return Double2ReferenceOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  606 */       V oldValue = Double2ReferenceOpenHashMap.this.value[this.index];
/*  607 */       Double2ReferenceOpenHashMap.this.value[this.index] = v;
/*  608 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  618 */       return Double.valueOf(Double2ReferenceOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  623 */       if (!(o instanceof Map.Entry))
/*  624 */         return false; 
/*  625 */       Map.Entry<Double, V> e = (Map.Entry<Double, V>)o;
/*  626 */       return (Double.doubleToLongBits(Double2ReferenceOpenHashMap.this.key[this.index]) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && Double2ReferenceOpenHashMap.this.value[this.index] == e
/*  627 */         .getValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  631 */       return HashCommon.double2int(Double2ReferenceOpenHashMap.this.key[this.index]) ^ (
/*  632 */         (Double2ReferenceOpenHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Double2ReferenceOpenHashMap.this.value[this.index]));
/*      */     }
/*      */     
/*      */     public String toString() {
/*  636 */       return Double2ReferenceOpenHashMap.this.key[this.index] + "=>" + Double2ReferenceOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  646 */     int pos = Double2ReferenceOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  653 */     int last = -1;
/*      */     
/*  655 */     int c = Double2ReferenceOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  659 */     boolean mustReturnNullKey = Double2ReferenceOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     DoubleArrayList wrapped;
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
/*  674 */         return this.last = Double2ReferenceOpenHashMap.this.n;
/*      */       } 
/*  676 */       double[] key = Double2ReferenceOpenHashMap.this.key;
/*      */       while (true) {
/*  678 */         if (--this.pos < 0) {
/*      */           
/*  680 */           this.last = Integer.MIN_VALUE;
/*  681 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  682 */           int p = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ReferenceOpenHashMap.this.mask;
/*  683 */           while (Double.doubleToLongBits(k) != Double.doubleToLongBits(key[p]))
/*  684 */             p = p + 1 & Double2ReferenceOpenHashMap.this.mask; 
/*  685 */           return p;
/*      */         } 
/*  687 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
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
/*  702 */       double[] key = Double2ReferenceOpenHashMap.this.key; while (true) {
/*      */         double curr; int last;
/*  704 */         pos = (last = pos) + 1 & Double2ReferenceOpenHashMap.this.mask;
/*      */         while (true) {
/*  706 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  707 */             key[last] = 0.0D;
/*  708 */             Double2ReferenceOpenHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  711 */           int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2ReferenceOpenHashMap.this.mask;
/*  712 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  714 */           pos = pos + 1 & Double2ReferenceOpenHashMap.this.mask;
/*      */         } 
/*  716 */         if (pos < last) {
/*  717 */           if (this.wrapped == null)
/*  718 */             this.wrapped = new DoubleArrayList(2); 
/*  719 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  721 */         key[last] = curr;
/*  722 */         Double2ReferenceOpenHashMap.this.value[last] = Double2ReferenceOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  726 */       if (this.last == -1)
/*  727 */         throw new IllegalStateException(); 
/*  728 */       if (this.last == Double2ReferenceOpenHashMap.this.n) {
/*  729 */         Double2ReferenceOpenHashMap.this.containsNullKey = false;
/*  730 */         Double2ReferenceOpenHashMap.this.value[Double2ReferenceOpenHashMap.this.n] = null;
/*  731 */       } else if (this.pos >= 0) {
/*  732 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  735 */         Double2ReferenceOpenHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
/*  736 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  739 */       Double2ReferenceOpenHashMap.this.size--;
/*  740 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  745 */       int i = n;
/*  746 */       while (i-- != 0 && hasNext())
/*  747 */         nextEntry(); 
/*  748 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Double2ReferenceMap.Entry<V>> { private Double2ReferenceOpenHashMap<V>.MapEntry entry;
/*      */     
/*      */     public Double2ReferenceOpenHashMap<V>.MapEntry next() {
/*  755 */       return this.entry = new Double2ReferenceOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  759 */       super.remove();
/*  760 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Double2ReferenceMap.Entry<V>> { private FastEntryIterator() {
/*  764 */       this.entry = new Double2ReferenceOpenHashMap.MapEntry();
/*      */     } private final Double2ReferenceOpenHashMap<V>.MapEntry entry;
/*      */     public Double2ReferenceOpenHashMap<V>.MapEntry next() {
/*  767 */       this.entry.index = nextEntry();
/*  768 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Double2ReferenceMap.Entry<V>> implements Double2ReferenceMap.FastEntrySet<V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Double2ReferenceMap.Entry<V>> iterator() {
/*  774 */       return new Double2ReferenceOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Double2ReferenceMap.Entry<V>> fastIterator() {
/*  778 */       return new Double2ReferenceOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  783 */       if (!(o instanceof Map.Entry))
/*  784 */         return false; 
/*  785 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  786 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  787 */         return false; 
/*  788 */       double k = ((Double)e.getKey()).doubleValue();
/*  789 */       V v = (V)e.getValue();
/*  790 */       if (Double.doubleToLongBits(k) == 0L) {
/*  791 */         return (Double2ReferenceOpenHashMap.this.containsNullKey && Double2ReferenceOpenHashMap.this.value[Double2ReferenceOpenHashMap.this.n] == v);
/*      */       }
/*  793 */       double[] key = Double2ReferenceOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  796 */       if (Double.doubleToLongBits(
/*  797 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ReferenceOpenHashMap.this.mask]) == 0L)
/*      */       {
/*  799 */         return false; } 
/*  800 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  801 */         return (Double2ReferenceOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  804 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ReferenceOpenHashMap.this.mask]) == 0L)
/*  805 */           return false; 
/*  806 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  807 */           return (Double2ReferenceOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  813 */       if (!(o instanceof Map.Entry))
/*  814 */         return false; 
/*  815 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  816 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  817 */         return false; 
/*  818 */       double k = ((Double)e.getKey()).doubleValue();
/*  819 */       V v = (V)e.getValue();
/*  820 */       if (Double.doubleToLongBits(k) == 0L) {
/*  821 */         if (Double2ReferenceOpenHashMap.this.containsNullKey && Double2ReferenceOpenHashMap.this.value[Double2ReferenceOpenHashMap.this.n] == v) {
/*  822 */           Double2ReferenceOpenHashMap.this.removeNullEntry();
/*  823 */           return true;
/*      */         } 
/*  825 */         return false;
/*      */       } 
/*      */       
/*  828 */       double[] key = Double2ReferenceOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  831 */       if (Double.doubleToLongBits(
/*  832 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ReferenceOpenHashMap.this.mask]) == 0L)
/*      */       {
/*  834 */         return false; } 
/*  835 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  836 */         if (Double2ReferenceOpenHashMap.this.value[pos] == v) {
/*  837 */           Double2ReferenceOpenHashMap.this.removeEntry(pos);
/*  838 */           return true;
/*      */         } 
/*  840 */         return false;
/*      */       } 
/*      */       while (true) {
/*  843 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ReferenceOpenHashMap.this.mask]) == 0L)
/*  844 */           return false; 
/*  845 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && 
/*  846 */           Double2ReferenceOpenHashMap.this.value[pos] == v) {
/*  847 */           Double2ReferenceOpenHashMap.this.removeEntry(pos);
/*  848 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  855 */       return Double2ReferenceOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  859 */       Double2ReferenceOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2ReferenceMap.Entry<V>> consumer) {
/*  864 */       if (Double2ReferenceOpenHashMap.this.containsNullKey)
/*  865 */         consumer.accept(new AbstractDouble2ReferenceMap.BasicEntry<>(Double2ReferenceOpenHashMap.this.key[Double2ReferenceOpenHashMap.this.n], Double2ReferenceOpenHashMap.this.value[Double2ReferenceOpenHashMap.this.n])); 
/*  866 */       for (int pos = Double2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/*  867 */         if (Double.doubleToLongBits(Double2ReferenceOpenHashMap.this.key[pos]) != 0L)
/*  868 */           consumer.accept(new AbstractDouble2ReferenceMap.BasicEntry<>(Double2ReferenceOpenHashMap.this.key[pos], Double2ReferenceOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2ReferenceMap.Entry<V>> consumer) {
/*  873 */       AbstractDouble2ReferenceMap.BasicEntry<V> entry = new AbstractDouble2ReferenceMap.BasicEntry<>();
/*  874 */       if (Double2ReferenceOpenHashMap.this.containsNullKey) {
/*  875 */         entry.key = Double2ReferenceOpenHashMap.this.key[Double2ReferenceOpenHashMap.this.n];
/*  876 */         entry.value = Double2ReferenceOpenHashMap.this.value[Double2ReferenceOpenHashMap.this.n];
/*  877 */         consumer.accept(entry);
/*      */       } 
/*  879 */       for (int pos = Double2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/*  880 */         if (Double.doubleToLongBits(Double2ReferenceOpenHashMap.this.key[pos]) != 0L) {
/*  881 */           entry.key = Double2ReferenceOpenHashMap.this.key[pos];
/*  882 */           entry.value = Double2ReferenceOpenHashMap.this.value[pos];
/*  883 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Double2ReferenceMap.FastEntrySet<V> double2ReferenceEntrySet() {
/*  889 */     if (this.entries == null)
/*  890 */       this.entries = new MapEntrySet(); 
/*  891 */     return this.entries;
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
/*      */     implements DoubleIterator
/*      */   {
/*      */     public double nextDouble() {
/*  908 */       return Double2ReferenceOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSet { private KeySet() {}
/*      */     
/*      */     public DoubleIterator iterator() {
/*  914 */       return new Double2ReferenceOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/*  919 */       if (Double2ReferenceOpenHashMap.this.containsNullKey)
/*  920 */         consumer.accept(Double2ReferenceOpenHashMap.this.key[Double2ReferenceOpenHashMap.this.n]); 
/*  921 */       for (int pos = Double2ReferenceOpenHashMap.this.n; pos-- != 0; ) {
/*  922 */         double k = Double2ReferenceOpenHashMap.this.key[pos];
/*  923 */         if (Double.doubleToLongBits(k) != 0L)
/*  924 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  929 */       return Double2ReferenceOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/*  933 */       return Double2ReferenceOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/*  937 */       int oldSize = Double2ReferenceOpenHashMap.this.size;
/*  938 */       Double2ReferenceOpenHashMap.this.remove(k);
/*  939 */       return (Double2ReferenceOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  943 */       Double2ReferenceOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSet keySet() {
/*  948 */     if (this.keys == null)
/*  949 */       this.keys = new KeySet(); 
/*  950 */     return this.keys;
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
/*  967 */       return Double2ReferenceOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ReferenceCollection<V> values() {
/*  972 */     if (this.values == null)
/*  973 */       this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/*  976 */             return new Double2ReferenceOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/*  980 */             return Double2ReferenceOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/*  984 */             return Double2ReferenceOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/*  988 */             Double2ReferenceOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer)
/*      */           {
/*  993 */             if (Double2ReferenceOpenHashMap.this.containsNullKey)
/*  994 */               consumer.accept(Double2ReferenceOpenHashMap.this.value[Double2ReferenceOpenHashMap.this.n]); 
/*  995 */             for (int pos = Double2ReferenceOpenHashMap.this.n; pos-- != 0;) {
/*  996 */               if (Double.doubleToLongBits(Double2ReferenceOpenHashMap.this.key[pos]) != 0L)
/*  997 */                 consumer.accept(Double2ReferenceOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1000 */     return this.values;
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
/* 1017 */     return trim(this.size);
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
/* 1041 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1042 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1043 */       return true; 
/*      */     try {
/* 1045 */       rehash(l);
/* 1046 */     } catch (OutOfMemoryError cantDoIt) {
/* 1047 */       return false;
/*      */     } 
/* 1049 */     return true;
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
/* 1065 */     double[] key = this.key;
/* 1066 */     V[] value = this.value;
/* 1067 */     int mask = newN - 1;
/* 1068 */     double[] newKey = new double[newN + 1];
/* 1069 */     V[] newValue = (V[])new Object[newN + 1];
/* 1070 */     int i = this.n;
/* 1071 */     for (int j = realSize(); j-- != 0; ) {
/* 1072 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 1073 */       if (Double.doubleToLongBits(newKey[
/* 1074 */             pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask]) != 0L)
/*      */       {
/* 1076 */         while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); } 
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
/*      */   public Double2ReferenceOpenHashMap<V> clone() {
/*      */     Double2ReferenceOpenHashMap<V> c;
/*      */     try {
/* 1102 */       c = (Double2ReferenceOpenHashMap<V>)super.clone();
/* 1103 */     } catch (CloneNotSupportedException cantHappen) {
/* 1104 */       throw new InternalError();
/*      */     } 
/* 1106 */     c.keys = null;
/* 1107 */     c.values = null;
/* 1108 */     c.entries = null;
/* 1109 */     c.containsNullKey = this.containsNullKey;
/* 1110 */     c.key = (double[])this.key.clone();
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
/* 1127 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1128 */         i++; 
/* 1129 */       t = HashCommon.double2int(this.key[i]);
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
/* 1141 */     double[] key = this.key;
/* 1142 */     V[] value = this.value;
/* 1143 */     MapIterator i = new MapIterator();
/* 1144 */     s.defaultWriteObject();
/* 1145 */     for (int j = this.size; j-- != 0; ) {
/* 1146 */       int e = i.nextEntry();
/* 1147 */       s.writeDouble(key[e]);
/* 1148 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1153 */     s.defaultReadObject();
/* 1154 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1155 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1156 */     this.mask = this.n - 1;
/* 1157 */     double[] key = this.key = new double[this.n + 1];
/* 1158 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1161 */     for (int i = this.size; i-- != 0; ) {
/* 1162 */       int pos; double k = s.readDouble();
/* 1163 */       V v = (V)s.readObject();
/* 1164 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1165 */         pos = this.n;
/* 1166 */         this.containsNullKey = true;
/*      */       } else {
/* 1168 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/* 1169 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1170 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1172 */       key[pos] = k;
/* 1173 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2ReferenceOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */