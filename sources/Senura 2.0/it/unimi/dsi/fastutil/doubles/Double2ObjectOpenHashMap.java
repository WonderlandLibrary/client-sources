/*      */ package it.unimi.dsi.fastutil.doubles;
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
/*      */ public class Double2ObjectOpenHashMap<V>
/*      */   extends AbstractDouble2ObjectMap<V>
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
/*      */   protected transient Double2ObjectMap.FastEntrySet<V> entries;
/*      */   protected transient DoubleSet keys;
/*      */   protected transient ObjectCollection<V> values;
/*      */   
/*      */   public Double2ObjectOpenHashMap(int expected, float f) {
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
/*      */   public Double2ObjectOpenHashMap(int expected) {
/*  117 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ObjectOpenHashMap() {
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
/*      */   public Double2ObjectOpenHashMap(Map<? extends Double, ? extends V> m, float f) {
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
/*      */   public Double2ObjectOpenHashMap(Map<? extends Double, ? extends V> m) {
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
/*      */   public Double2ObjectOpenHashMap(Double2ObjectMap<V> m, float f) {
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
/*      */   public Double2ObjectOpenHashMap(Double2ObjectMap<V> m) {
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
/*      */   public Double2ObjectOpenHashMap(double[] k, V[] v, float f) {
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
/*      */   public Double2ObjectOpenHashMap(double[] k, V[] v) {
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
/*  393 */     if (this.containsNullKey && Objects.equals(value[this.n], v))
/*  394 */       return true; 
/*  395 */     for (int i = this.n; i-- != 0;) {
/*  396 */       if (Double.doubleToLongBits(key[i]) != 0L && Objects.equals(value[i], v))
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
/*  438 */       if (this.containsNullKey && Objects.equals(v, this.value[this.n])) {
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
/*  452 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && Objects.equals(v, this.value[pos])) {
/*  453 */       removeEntry(pos);
/*  454 */       return true;
/*      */     } 
/*      */     while (true) {
/*  457 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  458 */         return false; 
/*  459 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && 
/*  460 */         Objects.equals(v, this.value[pos])) {
/*  461 */         removeEntry(pos);
/*  462 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, V oldValue, V v) {
/*  469 */     int pos = find(k);
/*  470 */     if (pos < 0 || !Objects.equals(oldValue, this.value[pos]))
/*  471 */       return false; 
/*  472 */     this.value[pos] = v;
/*  473 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(double k, V v) {
/*  478 */     int pos = find(k);
/*  479 */     if (pos < 0)
/*  480 */       return this.defRetValue; 
/*  481 */     V oldValue = this.value[pos];
/*  482 */     this.value[pos] = v;
/*  483 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(double k, DoubleFunction<? extends V> mappingFunction) {
/*  488 */     Objects.requireNonNull(mappingFunction);
/*  489 */     int pos = find(k);
/*  490 */     if (pos >= 0)
/*  491 */       return this.value[pos]; 
/*  492 */     V newValue = mappingFunction.apply(k);
/*  493 */     insert(-pos - 1, k, newValue);
/*  494 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfPresent(double k, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/*  500 */     Objects.requireNonNull(remappingFunction);
/*  501 */     int pos = find(k);
/*  502 */     if (pos < 0)
/*  503 */       return this.defRetValue; 
/*  504 */     V newValue = remappingFunction.apply(Double.valueOf(k), this.value[pos]);
/*  505 */     if (newValue == null) {
/*  506 */       if (Double.doubleToLongBits(k) == 0L) {
/*  507 */         removeNullEntry();
/*      */       } else {
/*  509 */         removeEntry(pos);
/*  510 */       }  return this.defRetValue;
/*      */     } 
/*  512 */     this.value[pos] = newValue; return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V compute(double k, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/*  518 */     Objects.requireNonNull(remappingFunction);
/*  519 */     int pos = find(k);
/*  520 */     V newValue = remappingFunction.apply(Double.valueOf(k), (pos >= 0) ? this.value[pos] : null);
/*  521 */     if (newValue == null) {
/*  522 */       if (pos >= 0)
/*  523 */         if (Double.doubleToLongBits(k) == 0L) {
/*  524 */           removeNullEntry();
/*      */         } else {
/*  526 */           removeEntry(pos);
/*      */         }  
/*  528 */       return this.defRetValue;
/*      */     } 
/*  530 */     V newVal = newValue;
/*  531 */     if (pos < 0) {
/*  532 */       insert(-pos - 1, k, newVal);
/*  533 */       return newVal;
/*      */     } 
/*  535 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V merge(double k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*  541 */     Objects.requireNonNull(remappingFunction);
/*  542 */     int pos = find(k);
/*  543 */     if (pos < 0 || this.value[pos] == null) {
/*  544 */       if (v == null)
/*  545 */         return this.defRetValue; 
/*  546 */       insert(-pos - 1, k, v);
/*  547 */       return v;
/*      */     } 
/*  549 */     V newValue = remappingFunction.apply(this.value[pos], v);
/*  550 */     if (newValue == null) {
/*  551 */       if (Double.doubleToLongBits(k) == 0L) {
/*  552 */         removeNullEntry();
/*      */       } else {
/*  554 */         removeEntry(pos);
/*  555 */       }  return this.defRetValue;
/*      */     } 
/*  557 */     this.value[pos] = newValue; return newValue;
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
/*  568 */     if (this.size == 0)
/*      */       return; 
/*  570 */     this.size = 0;
/*  571 */     this.containsNullKey = false;
/*  572 */     Arrays.fill(this.key, 0.0D);
/*  573 */     Arrays.fill((Object[])this.value, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  577 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  581 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2ObjectMap.Entry<V>, Map.Entry<Double, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  593 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/*  599 */       return Double2ObjectOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  603 */       return Double2ObjectOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  607 */       V oldValue = Double2ObjectOpenHashMap.this.value[this.index];
/*  608 */       Double2ObjectOpenHashMap.this.value[this.index] = v;
/*  609 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  619 */       return Double.valueOf(Double2ObjectOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  624 */       if (!(o instanceof Map.Entry))
/*  625 */         return false; 
/*  626 */       Map.Entry<Double, V> e = (Map.Entry<Double, V>)o;
/*  627 */       return (Double.doubleToLongBits(Double2ObjectOpenHashMap.this.key[this.index]) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && 
/*  628 */         Objects.equals(Double2ObjectOpenHashMap.this.value[this.index], e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  632 */       return HashCommon.double2int(Double2ObjectOpenHashMap.this.key[this.index]) ^ (
/*  633 */         (Double2ObjectOpenHashMap.this.value[this.index] == null) ? 0 : Double2ObjectOpenHashMap.this.value[this.index].hashCode());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  637 */       return Double2ObjectOpenHashMap.this.key[this.index] + "=>" + Double2ObjectOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  647 */     int pos = Double2ObjectOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  654 */     int last = -1;
/*      */     
/*  656 */     int c = Double2ObjectOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  660 */     boolean mustReturnNullKey = Double2ObjectOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     DoubleArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  667 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  670 */       if (!hasNext())
/*  671 */         throw new NoSuchElementException(); 
/*  672 */       this.c--;
/*  673 */       if (this.mustReturnNullKey) {
/*  674 */         this.mustReturnNullKey = false;
/*  675 */         return this.last = Double2ObjectOpenHashMap.this.n;
/*      */       } 
/*  677 */       double[] key = Double2ObjectOpenHashMap.this.key;
/*      */       while (true) {
/*  679 */         if (--this.pos < 0) {
/*      */           
/*  681 */           this.last = Integer.MIN_VALUE;
/*  682 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  683 */           int p = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ObjectOpenHashMap.this.mask;
/*  684 */           while (Double.doubleToLongBits(k) != Double.doubleToLongBits(key[p]))
/*  685 */             p = p + 1 & Double2ObjectOpenHashMap.this.mask; 
/*  686 */           return p;
/*      */         } 
/*  688 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  689 */           return this.last = this.pos;
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
/*  703 */       double[] key = Double2ObjectOpenHashMap.this.key; while (true) {
/*      */         double curr; int last;
/*  705 */         pos = (last = pos) + 1 & Double2ObjectOpenHashMap.this.mask;
/*      */         while (true) {
/*  707 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  708 */             key[last] = 0.0D;
/*  709 */             Double2ObjectOpenHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  712 */           int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2ObjectOpenHashMap.this.mask;
/*  713 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  715 */           pos = pos + 1 & Double2ObjectOpenHashMap.this.mask;
/*      */         } 
/*  717 */         if (pos < last) {
/*  718 */           if (this.wrapped == null)
/*  719 */             this.wrapped = new DoubleArrayList(2); 
/*  720 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  722 */         key[last] = curr;
/*  723 */         Double2ObjectOpenHashMap.this.value[last] = Double2ObjectOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  727 */       if (this.last == -1)
/*  728 */         throw new IllegalStateException(); 
/*  729 */       if (this.last == Double2ObjectOpenHashMap.this.n) {
/*  730 */         Double2ObjectOpenHashMap.this.containsNullKey = false;
/*  731 */         Double2ObjectOpenHashMap.this.value[Double2ObjectOpenHashMap.this.n] = null;
/*  732 */       } else if (this.pos >= 0) {
/*  733 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  736 */         Double2ObjectOpenHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
/*  737 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  740 */       Double2ObjectOpenHashMap.this.size--;
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
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Double2ObjectMap.Entry<V>> { private Double2ObjectOpenHashMap<V>.MapEntry entry;
/*      */     
/*      */     public Double2ObjectOpenHashMap<V>.MapEntry next() {
/*  756 */       return this.entry = new Double2ObjectOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  760 */       super.remove();
/*  761 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Double2ObjectMap.Entry<V>> { private FastEntryIterator() {
/*  765 */       this.entry = new Double2ObjectOpenHashMap.MapEntry();
/*      */     } private final Double2ObjectOpenHashMap<V>.MapEntry entry;
/*      */     public Double2ObjectOpenHashMap<V>.MapEntry next() {
/*  768 */       this.entry.index = nextEntry();
/*  769 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Double2ObjectMap.Entry<V>> implements Double2ObjectMap.FastEntrySet<V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Double2ObjectMap.Entry<V>> iterator() {
/*  775 */       return new Double2ObjectOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Double2ObjectMap.Entry<V>> fastIterator() {
/*  779 */       return new Double2ObjectOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  784 */       if (!(o instanceof Map.Entry))
/*  785 */         return false; 
/*  786 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  787 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  788 */         return false; 
/*  789 */       double k = ((Double)e.getKey()).doubleValue();
/*  790 */       V v = (V)e.getValue();
/*  791 */       if (Double.doubleToLongBits(k) == 0L) {
/*  792 */         return (Double2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Double2ObjectOpenHashMap.this.value[Double2ObjectOpenHashMap.this.n], v));
/*      */       }
/*  794 */       double[] key = Double2ObjectOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  797 */       if (Double.doubleToLongBits(
/*  798 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ObjectOpenHashMap.this.mask]) == 0L)
/*      */       {
/*  800 */         return false; } 
/*  801 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  802 */         return Objects.equals(Double2ObjectOpenHashMap.this.value[pos], v);
/*      */       }
/*      */       while (true) {
/*  805 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ObjectOpenHashMap.this.mask]) == 0L)
/*  806 */           return false; 
/*  807 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  808 */           return Objects.equals(Double2ObjectOpenHashMap.this.value[pos], v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  814 */       if (!(o instanceof Map.Entry))
/*  815 */         return false; 
/*  816 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  817 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  818 */         return false; 
/*  819 */       double k = ((Double)e.getKey()).doubleValue();
/*  820 */       V v = (V)e.getValue();
/*  821 */       if (Double.doubleToLongBits(k) == 0L) {
/*  822 */         if (Double2ObjectOpenHashMap.this.containsNullKey && Objects.equals(Double2ObjectOpenHashMap.this.value[Double2ObjectOpenHashMap.this.n], v)) {
/*  823 */           Double2ObjectOpenHashMap.this.removeNullEntry();
/*  824 */           return true;
/*      */         } 
/*  826 */         return false;
/*      */       } 
/*      */       
/*  829 */       double[] key = Double2ObjectOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  832 */       if (Double.doubleToLongBits(
/*  833 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ObjectOpenHashMap.this.mask]) == 0L)
/*      */       {
/*  835 */         return false; } 
/*  836 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  837 */         if (Objects.equals(Double2ObjectOpenHashMap.this.value[pos], v)) {
/*  838 */           Double2ObjectOpenHashMap.this.removeEntry(pos);
/*  839 */           return true;
/*      */         } 
/*  841 */         return false;
/*      */       } 
/*      */       while (true) {
/*  844 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ObjectOpenHashMap.this.mask]) == 0L)
/*  845 */           return false; 
/*  846 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && 
/*  847 */           Objects.equals(Double2ObjectOpenHashMap.this.value[pos], v)) {
/*  848 */           Double2ObjectOpenHashMap.this.removeEntry(pos);
/*  849 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  856 */       return Double2ObjectOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  860 */       Double2ObjectOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2ObjectMap.Entry<V>> consumer) {
/*  865 */       if (Double2ObjectOpenHashMap.this.containsNullKey)
/*  866 */         consumer.accept(new AbstractDouble2ObjectMap.BasicEntry<>(Double2ObjectOpenHashMap.this.key[Double2ObjectOpenHashMap.this.n], Double2ObjectOpenHashMap.this.value[Double2ObjectOpenHashMap.this.n])); 
/*  867 */       for (int pos = Double2ObjectOpenHashMap.this.n; pos-- != 0;) {
/*  868 */         if (Double.doubleToLongBits(Double2ObjectOpenHashMap.this.key[pos]) != 0L)
/*  869 */           consumer.accept(new AbstractDouble2ObjectMap.BasicEntry<>(Double2ObjectOpenHashMap.this.key[pos], Double2ObjectOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2ObjectMap.Entry<V>> consumer) {
/*  874 */       AbstractDouble2ObjectMap.BasicEntry<V> entry = new AbstractDouble2ObjectMap.BasicEntry<>();
/*  875 */       if (Double2ObjectOpenHashMap.this.containsNullKey) {
/*  876 */         entry.key = Double2ObjectOpenHashMap.this.key[Double2ObjectOpenHashMap.this.n];
/*  877 */         entry.value = Double2ObjectOpenHashMap.this.value[Double2ObjectOpenHashMap.this.n];
/*  878 */         consumer.accept(entry);
/*      */       } 
/*  880 */       for (int pos = Double2ObjectOpenHashMap.this.n; pos-- != 0;) {
/*  881 */         if (Double.doubleToLongBits(Double2ObjectOpenHashMap.this.key[pos]) != 0L) {
/*  882 */           entry.key = Double2ObjectOpenHashMap.this.key[pos];
/*  883 */           entry.value = Double2ObjectOpenHashMap.this.value[pos];
/*  884 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Double2ObjectMap.FastEntrySet<V> double2ObjectEntrySet() {
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
/*      */     implements DoubleIterator
/*      */   {
/*      */     public double nextDouble() {
/*  909 */       return Double2ObjectOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSet { private KeySet() {}
/*      */     
/*      */     public DoubleIterator iterator() {
/*  915 */       return new Double2ObjectOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/*  920 */       if (Double2ObjectOpenHashMap.this.containsNullKey)
/*  921 */         consumer.accept(Double2ObjectOpenHashMap.this.key[Double2ObjectOpenHashMap.this.n]); 
/*  922 */       for (int pos = Double2ObjectOpenHashMap.this.n; pos-- != 0; ) {
/*  923 */         double k = Double2ObjectOpenHashMap.this.key[pos];
/*  924 */         if (Double.doubleToLongBits(k) != 0L)
/*  925 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  930 */       return Double2ObjectOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/*  934 */       return Double2ObjectOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/*  938 */       int oldSize = Double2ObjectOpenHashMap.this.size;
/*  939 */       Double2ObjectOpenHashMap.this.remove(k);
/*  940 */       return (Double2ObjectOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  944 */       Double2ObjectOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSet keySet() {
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
/*  968 */       return Double2ObjectOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectCollection<V> values() {
/*  973 */     if (this.values == null)
/*  974 */       this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/*  977 */             return new Double2ObjectOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/*  981 */             return Double2ObjectOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/*  985 */             return Double2ObjectOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/*  989 */             Double2ObjectOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer)
/*      */           {
/*  994 */             if (Double2ObjectOpenHashMap.this.containsNullKey)
/*  995 */               consumer.accept(Double2ObjectOpenHashMap.this.value[Double2ObjectOpenHashMap.this.n]); 
/*  996 */             for (int pos = Double2ObjectOpenHashMap.this.n; pos-- != 0;) {
/*  997 */               if (Double.doubleToLongBits(Double2ObjectOpenHashMap.this.key[pos]) != 0L)
/*  998 */                 consumer.accept(Double2ObjectOpenHashMap.this.value[pos]); 
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
/* 1066 */     double[] key = this.key;
/* 1067 */     V[] value = this.value;
/* 1068 */     int mask = newN - 1;
/* 1069 */     double[] newKey = new double[newN + 1];
/* 1070 */     V[] newValue = (V[])new Object[newN + 1];
/* 1071 */     int i = this.n;
/* 1072 */     for (int j = realSize(); j-- != 0; ) {
/* 1073 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 1074 */       if (Double.doubleToLongBits(newKey[
/* 1075 */             pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask]) != 0L)
/*      */       {
/* 1077 */         while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); } 
/* 1078 */       newKey[pos] = key[i];
/* 1079 */       newValue[pos] = value[i];
/*      */     } 
/* 1081 */     newValue[newN] = value[this.n];
/* 1082 */     this.n = newN;
/* 1083 */     this.mask = mask;
/* 1084 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1085 */     this.key = newKey;
/* 1086 */     this.value = newValue;
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
/*      */   public Double2ObjectOpenHashMap<V> clone() {
/*      */     Double2ObjectOpenHashMap<V> c;
/*      */     try {
/* 1103 */       c = (Double2ObjectOpenHashMap<V>)super.clone();
/* 1104 */     } catch (CloneNotSupportedException cantHappen) {
/* 1105 */       throw new InternalError();
/*      */     } 
/* 1107 */     c.keys = null;
/* 1108 */     c.values = null;
/* 1109 */     c.entries = null;
/* 1110 */     c.containsNullKey = this.containsNullKey;
/* 1111 */     c.key = (double[])this.key.clone();
/* 1112 */     c.value = (V[])this.value.clone();
/* 1113 */     return c;
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
/* 1126 */     int h = 0;
/* 1127 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1128 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1129 */         i++; 
/* 1130 */       t = HashCommon.double2int(this.key[i]);
/* 1131 */       if (this != this.value[i])
/* 1132 */         t ^= (this.value[i] == null) ? 0 : this.value[i].hashCode(); 
/* 1133 */       h += t;
/* 1134 */       i++;
/*      */     } 
/*      */     
/* 1137 */     if (this.containsNullKey)
/* 1138 */       h += (this.value[this.n] == null) ? 0 : this.value[this.n].hashCode(); 
/* 1139 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1142 */     double[] key = this.key;
/* 1143 */     V[] value = this.value;
/* 1144 */     MapIterator i = new MapIterator();
/* 1145 */     s.defaultWriteObject();
/* 1146 */     for (int j = this.size; j-- != 0; ) {
/* 1147 */       int e = i.nextEntry();
/* 1148 */       s.writeDouble(key[e]);
/* 1149 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1154 */     s.defaultReadObject();
/* 1155 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1156 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1157 */     this.mask = this.n - 1;
/* 1158 */     double[] key = this.key = new double[this.n + 1];
/* 1159 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1162 */     for (int i = this.size; i-- != 0; ) {
/* 1163 */       int pos; double k = s.readDouble();
/* 1164 */       V v = (V)s.readObject();
/* 1165 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1166 */         pos = this.n;
/* 1167 */         this.containsNullKey = true;
/*      */       } else {
/* 1169 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/* 1170 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1171 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1173 */       key[pos] = k;
/* 1174 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2ObjectOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */