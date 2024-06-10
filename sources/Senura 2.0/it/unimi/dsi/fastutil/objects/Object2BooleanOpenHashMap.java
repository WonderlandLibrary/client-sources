/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
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
/*      */ import java.util.function.Predicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2BooleanOpenHashMap<K>
/*      */   extends AbstractObject2BooleanMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Object2BooleanMap.FastEntrySet<K> entries;
/*      */   protected transient ObjectSet<K> keys;
/*      */   protected transient BooleanCollection values;
/*      */   
/*      */   public Object2BooleanOpenHashMap(int expected, float f) {
/*  101 */     if (f <= 0.0F || f > 1.0F)
/*  102 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  103 */     if (expected < 0)
/*  104 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  105 */     this.f = f;
/*  106 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  107 */     this.mask = this.n - 1;
/*  108 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  109 */     this.key = (K[])new Object[this.n + 1];
/*  110 */     this.value = new boolean[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanOpenHashMap(int expected) {
/*  119 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanOpenHashMap() {
/*  127 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanOpenHashMap(Map<? extends K, ? extends Boolean> m, float f) {
/*  138 */     this(m.size(), f);
/*  139 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanOpenHashMap(Map<? extends K, ? extends Boolean> m) {
/*  149 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanOpenHashMap(Object2BooleanMap<K> m, float f) {
/*  160 */     this(m.size(), f);
/*  161 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanOpenHashMap(Object2BooleanMap<K> m) {
/*  171 */     this(m, 0.75F);
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
/*      */   public Object2BooleanOpenHashMap(K[] k, boolean[] v, float f) {
/*  186 */     this(k.length, f);
/*  187 */     if (k.length != v.length) {
/*  188 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  190 */     for (int i = 0; i < k.length; i++) {
/*  191 */       put(k[i], v[i]);
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
/*      */   public Object2BooleanOpenHashMap(K[] k, boolean[] v) {
/*  205 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  208 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  211 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  212 */     if (needed > this.n)
/*  213 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  216 */     int needed = (int)Math.min(1073741824L, 
/*  217 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  218 */     if (needed > this.n)
/*  219 */       rehash(needed); 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  222 */     boolean oldValue = this.value[pos];
/*  223 */     this.size--;
/*  224 */     shiftKeys(pos);
/*  225 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  226 */       rehash(this.n / 2); 
/*  227 */     return oldValue;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  230 */     this.containsNullKey = false;
/*  231 */     this.key[this.n] = null;
/*  232 */     boolean oldValue = this.value[this.n];
/*  233 */     this.size--;
/*  234 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  235 */       rehash(this.n / 2); 
/*  236 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Boolean> m) {
/*  240 */     if (this.f <= 0.5D) {
/*  241 */       ensureCapacity(m.size());
/*      */     } else {
/*  243 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  245 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  249 */     if (k == null) {
/*  250 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  252 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  255 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  256 */       return -(pos + 1); 
/*  257 */     if (k.equals(curr)) {
/*  258 */       return pos;
/*      */     }
/*      */     while (true) {
/*  261 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  262 */         return -(pos + 1); 
/*  263 */       if (k.equals(curr))
/*  264 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, boolean v) {
/*  268 */     if (pos == this.n)
/*  269 */       this.containsNullKey = true; 
/*  270 */     this.key[pos] = k;
/*  271 */     this.value[pos] = v;
/*  272 */     if (this.size++ >= this.maxFill) {
/*  273 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean put(K k, boolean v) {
/*  279 */     int pos = find(k);
/*  280 */     if (pos < 0) {
/*  281 */       insert(-pos - 1, k, v);
/*  282 */       return this.defRetValue;
/*      */     } 
/*  284 */     boolean oldValue = this.value[pos];
/*  285 */     this.value[pos] = v;
/*  286 */     return oldValue;
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
/*  299 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  301 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  303 */         if ((curr = key[pos]) == null) {
/*  304 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  307 */         int slot = HashCommon.mix(curr.hashCode()) & this.mask;
/*  308 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  310 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  312 */       key[last] = curr;
/*  313 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean removeBoolean(Object k) {
/*  319 */     if (k == null) {
/*  320 */       if (this.containsNullKey)
/*  321 */         return removeNullEntry(); 
/*  322 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  325 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  328 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  329 */       return this.defRetValue; 
/*  330 */     if (k.equals(curr))
/*  331 */       return removeEntry(pos); 
/*      */     while (true) {
/*  333 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  334 */         return this.defRetValue; 
/*  335 */       if (k.equals(curr)) {
/*  336 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getBoolean(Object k) {
/*  342 */     if (k == null) {
/*  343 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  345 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  348 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  349 */       return this.defRetValue; 
/*  350 */     if (k.equals(curr)) {
/*  351 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  354 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  355 */         return this.defRetValue; 
/*  356 */       if (k.equals(curr)) {
/*  357 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  363 */     if (k == null) {
/*  364 */       return this.containsNullKey;
/*      */     }
/*  366 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  369 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  370 */       return false; 
/*  371 */     if (k.equals(curr)) {
/*  372 */       return true;
/*      */     }
/*      */     while (true) {
/*  375 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  376 */         return false; 
/*  377 */       if (k.equals(curr))
/*  378 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  383 */     boolean[] value = this.value;
/*  384 */     K[] key = this.key;
/*  385 */     if (this.containsNullKey && value[this.n] == v)
/*  386 */       return true; 
/*  387 */     for (int i = this.n; i-- != 0;) {
/*  388 */       if (key[i] != null && value[i] == v)
/*  389 */         return true; 
/*  390 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(Object k, boolean defaultValue) {
/*  396 */     if (k == null) {
/*  397 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  399 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  402 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  403 */       return defaultValue; 
/*  404 */     if (k.equals(curr)) {
/*  405 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  408 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  409 */         return defaultValue; 
/*  410 */       if (k.equals(curr)) {
/*  411 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean putIfAbsent(K k, boolean v) {
/*  417 */     int pos = find(k);
/*  418 */     if (pos >= 0)
/*  419 */       return this.value[pos]; 
/*  420 */     insert(-pos - 1, k, v);
/*  421 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, boolean v) {
/*  427 */     if (k == null) {
/*  428 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  429 */         removeNullEntry();
/*  430 */         return true;
/*      */       } 
/*  432 */       return false;
/*      */     } 
/*      */     
/*  435 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  438 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  439 */       return false; 
/*  440 */     if (k.equals(curr) && v == this.value[pos]) {
/*  441 */       removeEntry(pos);
/*  442 */       return true;
/*      */     } 
/*      */     while (true) {
/*  445 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  446 */         return false; 
/*  447 */       if (k.equals(curr) && v == this.value[pos]) {
/*  448 */         removeEntry(pos);
/*  449 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean oldValue, boolean v) {
/*  456 */     int pos = find(k);
/*  457 */     if (pos < 0 || oldValue != this.value[pos])
/*  458 */       return false; 
/*  459 */     this.value[pos] = v;
/*  460 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean v) {
/*  465 */     int pos = find(k);
/*  466 */     if (pos < 0)
/*  467 */       return this.defRetValue; 
/*  468 */     boolean oldValue = this.value[pos];
/*  469 */     this.value[pos] = v;
/*  470 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean computeBooleanIfAbsent(K k, Predicate<? super K> mappingFunction) {
/*  475 */     Objects.requireNonNull(mappingFunction);
/*  476 */     int pos = find(k);
/*  477 */     if (pos >= 0)
/*  478 */       return this.value[pos]; 
/*  479 */     boolean newValue = mappingFunction.test(k);
/*  480 */     insert(-pos - 1, k, newValue);
/*  481 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBooleanIfPresent(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  487 */     Objects.requireNonNull(remappingFunction);
/*  488 */     int pos = find(k);
/*  489 */     if (pos < 0)
/*  490 */       return this.defRetValue; 
/*  491 */     Boolean newValue = remappingFunction.apply(k, Boolean.valueOf(this.value[pos]));
/*  492 */     if (newValue == null) {
/*  493 */       if (k == null) {
/*  494 */         removeNullEntry();
/*      */       } else {
/*  496 */         removeEntry(pos);
/*  497 */       }  return this.defRetValue;
/*      */     } 
/*  499 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBoolean(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  505 */     Objects.requireNonNull(remappingFunction);
/*  506 */     int pos = find(k);
/*  507 */     Boolean newValue = remappingFunction.apply(k, (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  508 */     if (newValue == null) {
/*  509 */       if (pos >= 0)
/*  510 */         if (k == null) {
/*  511 */           removeNullEntry();
/*      */         } else {
/*  513 */           removeEntry(pos);
/*      */         }  
/*  515 */       return this.defRetValue;
/*      */     } 
/*  517 */     boolean newVal = newValue.booleanValue();
/*  518 */     if (pos < 0) {
/*  519 */       insert(-pos - 1, k, newVal);
/*  520 */       return newVal;
/*      */     } 
/*  522 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean mergeBoolean(K k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  528 */     Objects.requireNonNull(remappingFunction);
/*  529 */     int pos = find(k);
/*  530 */     if (pos < 0) {
/*  531 */       insert(-pos - 1, k, v);
/*  532 */       return v;
/*      */     } 
/*  534 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  535 */     if (newValue == null) {
/*  536 */       if (k == null) {
/*  537 */         removeNullEntry();
/*      */       } else {
/*  539 */         removeEntry(pos);
/*  540 */       }  return this.defRetValue;
/*      */     } 
/*  542 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
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
/*  553 */     if (this.size == 0)
/*      */       return; 
/*  555 */     this.size = 0;
/*  556 */     this.containsNullKey = false;
/*  557 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  561 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  565 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2BooleanMap.Entry<K>, Map.Entry<K, Boolean>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  577 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  583 */       return Object2BooleanOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public boolean getBooleanValue() {
/*  587 */       return Object2BooleanOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  591 */       boolean oldValue = Object2BooleanOpenHashMap.this.value[this.index];
/*  592 */       Object2BooleanOpenHashMap.this.value[this.index] = v;
/*  593 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  603 */       return Boolean.valueOf(Object2BooleanOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean setValue(Boolean v) {
/*  613 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  618 */       if (!(o instanceof Map.Entry))
/*  619 */         return false; 
/*  620 */       Map.Entry<K, Boolean> e = (Map.Entry<K, Boolean>)o;
/*  621 */       return (Objects.equals(Object2BooleanOpenHashMap.this.key[this.index], e.getKey()) && Object2BooleanOpenHashMap.this.value[this.index] == ((Boolean)e
/*  622 */         .getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  626 */       return ((Object2BooleanOpenHashMap.this.key[this.index] == null) ? 0 : Object2BooleanOpenHashMap.this.key[this.index].hashCode()) ^ (Object2BooleanOpenHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  630 */       return (new StringBuilder()).append(Object2BooleanOpenHashMap.this.key[this.index]).append("=>").append(Object2BooleanOpenHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  640 */     int pos = Object2BooleanOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  647 */     int last = -1;
/*      */     
/*  649 */     int c = Object2BooleanOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  653 */     boolean mustReturnNullKey = Object2BooleanOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     ObjectArrayList<K> wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  660 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  663 */       if (!hasNext())
/*  664 */         throw new NoSuchElementException(); 
/*  665 */       this.c--;
/*  666 */       if (this.mustReturnNullKey) {
/*  667 */         this.mustReturnNullKey = false;
/*  668 */         return this.last = Object2BooleanOpenHashMap.this.n;
/*      */       } 
/*  670 */       K[] key = Object2BooleanOpenHashMap.this.key;
/*      */       while (true) {
/*  672 */         if (--this.pos < 0) {
/*      */           
/*  674 */           this.last = Integer.MIN_VALUE;
/*  675 */           K k = this.wrapped.get(-this.pos - 1);
/*  676 */           int p = HashCommon.mix(k.hashCode()) & Object2BooleanOpenHashMap.this.mask;
/*  677 */           while (!k.equals(key[p]))
/*  678 */             p = p + 1 & Object2BooleanOpenHashMap.this.mask; 
/*  679 */           return p;
/*      */         } 
/*  681 */         if (key[this.pos] != null) {
/*  682 */           return this.last = this.pos;
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
/*  696 */       K[] key = Object2BooleanOpenHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  698 */         pos = (last = pos) + 1 & Object2BooleanOpenHashMap.this.mask;
/*      */         while (true) {
/*  700 */           if ((curr = key[pos]) == null) {
/*  701 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  704 */           int slot = HashCommon.mix(curr.hashCode()) & Object2BooleanOpenHashMap.this.mask;
/*  705 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  707 */           pos = pos + 1 & Object2BooleanOpenHashMap.this.mask;
/*      */         } 
/*  709 */         if (pos < last) {
/*  710 */           if (this.wrapped == null)
/*  711 */             this.wrapped = new ObjectArrayList<>(2); 
/*  712 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  714 */         key[last] = curr;
/*  715 */         Object2BooleanOpenHashMap.this.value[last] = Object2BooleanOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  719 */       if (this.last == -1)
/*  720 */         throw new IllegalStateException(); 
/*  721 */       if (this.last == Object2BooleanOpenHashMap.this.n) {
/*  722 */         Object2BooleanOpenHashMap.this.containsNullKey = false;
/*  723 */         Object2BooleanOpenHashMap.this.key[Object2BooleanOpenHashMap.this.n] = null;
/*  724 */       } else if (this.pos >= 0) {
/*  725 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  728 */         Object2BooleanOpenHashMap.this.removeBoolean(this.wrapped.set(-this.pos - 1, null));
/*  729 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  732 */       Object2BooleanOpenHashMap.this.size--;
/*  733 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  738 */       int i = n;
/*  739 */       while (i-- != 0 && hasNext())
/*  740 */         nextEntry(); 
/*  741 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Object2BooleanMap.Entry<K>> { private Object2BooleanOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public Object2BooleanOpenHashMap<K>.MapEntry next() {
/*  748 */       return this.entry = new Object2BooleanOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  752 */       super.remove();
/*  753 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Object2BooleanMap.Entry<K>> { private FastEntryIterator() {
/*  757 */       this.entry = new Object2BooleanOpenHashMap.MapEntry();
/*      */     } private final Object2BooleanOpenHashMap<K>.MapEntry entry;
/*      */     public Object2BooleanOpenHashMap<K>.MapEntry next() {
/*  760 */       this.entry.index = nextEntry();
/*  761 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Object2BooleanMap.Entry<K>> implements Object2BooleanMap.FastEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Object2BooleanMap.Entry<K>> iterator() {
/*  767 */       return new Object2BooleanOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Object2BooleanMap.Entry<K>> fastIterator() {
/*  771 */       return new Object2BooleanOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  776 */       if (!(o instanceof Map.Entry))
/*  777 */         return false; 
/*  778 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  779 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  780 */         return false; 
/*  781 */       K k = (K)e.getKey();
/*  782 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  783 */       if (k == null) {
/*  784 */         return (Object2BooleanOpenHashMap.this.containsNullKey && Object2BooleanOpenHashMap.this.value[Object2BooleanOpenHashMap.this.n] == v);
/*      */       }
/*  786 */       K[] key = Object2BooleanOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  789 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2BooleanOpenHashMap.this.mask]) == null)
/*  790 */         return false; 
/*  791 */       if (k.equals(curr)) {
/*  792 */         return (Object2BooleanOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  795 */         if ((curr = key[pos = pos + 1 & Object2BooleanOpenHashMap.this.mask]) == null)
/*  796 */           return false; 
/*  797 */         if (k.equals(curr)) {
/*  798 */           return (Object2BooleanOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  804 */       if (!(o instanceof Map.Entry))
/*  805 */         return false; 
/*  806 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  807 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  808 */         return false; 
/*  809 */       K k = (K)e.getKey();
/*  810 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  811 */       if (k == null) {
/*  812 */         if (Object2BooleanOpenHashMap.this.containsNullKey && Object2BooleanOpenHashMap.this.value[Object2BooleanOpenHashMap.this.n] == v) {
/*  813 */           Object2BooleanOpenHashMap.this.removeNullEntry();
/*  814 */           return true;
/*      */         } 
/*  816 */         return false;
/*      */       } 
/*      */       
/*  819 */       K[] key = Object2BooleanOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  822 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2BooleanOpenHashMap.this.mask]) == null)
/*  823 */         return false; 
/*  824 */       if (curr.equals(k)) {
/*  825 */         if (Object2BooleanOpenHashMap.this.value[pos] == v) {
/*  826 */           Object2BooleanOpenHashMap.this.removeEntry(pos);
/*  827 */           return true;
/*      */         } 
/*  829 */         return false;
/*      */       } 
/*      */       while (true) {
/*  832 */         if ((curr = key[pos = pos + 1 & Object2BooleanOpenHashMap.this.mask]) == null)
/*  833 */           return false; 
/*  834 */         if (curr.equals(k) && 
/*  835 */           Object2BooleanOpenHashMap.this.value[pos] == v) {
/*  836 */           Object2BooleanOpenHashMap.this.removeEntry(pos);
/*  837 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  844 */       return Object2BooleanOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  848 */       Object2BooleanOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2BooleanMap.Entry<K>> consumer) {
/*  853 */       if (Object2BooleanOpenHashMap.this.containsNullKey)
/*  854 */         consumer.accept(new AbstractObject2BooleanMap.BasicEntry<>(Object2BooleanOpenHashMap.this.key[Object2BooleanOpenHashMap.this.n], Object2BooleanOpenHashMap.this.value[Object2BooleanOpenHashMap.this.n])); 
/*  855 */       for (int pos = Object2BooleanOpenHashMap.this.n; pos-- != 0;) {
/*  856 */         if (Object2BooleanOpenHashMap.this.key[pos] != null)
/*  857 */           consumer.accept(new AbstractObject2BooleanMap.BasicEntry<>(Object2BooleanOpenHashMap.this.key[pos], Object2BooleanOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2BooleanMap.Entry<K>> consumer) {
/*  862 */       AbstractObject2BooleanMap.BasicEntry<K> entry = new AbstractObject2BooleanMap.BasicEntry<>();
/*  863 */       if (Object2BooleanOpenHashMap.this.containsNullKey) {
/*  864 */         entry.key = Object2BooleanOpenHashMap.this.key[Object2BooleanOpenHashMap.this.n];
/*  865 */         entry.value = Object2BooleanOpenHashMap.this.value[Object2BooleanOpenHashMap.this.n];
/*  866 */         consumer.accept(entry);
/*      */       } 
/*  868 */       for (int pos = Object2BooleanOpenHashMap.this.n; pos-- != 0;) {
/*  869 */         if (Object2BooleanOpenHashMap.this.key[pos] != null) {
/*  870 */           entry.key = Object2BooleanOpenHashMap.this.key[pos];
/*  871 */           entry.value = Object2BooleanOpenHashMap.this.value[pos];
/*  872 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Object2BooleanMap.FastEntrySet<K> object2BooleanEntrySet() {
/*  878 */     if (this.entries == null)
/*  879 */       this.entries = new MapEntrySet(); 
/*  880 */     return this.entries;
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
/*      */     implements ObjectIterator<K>
/*      */   {
/*      */     public K next() {
/*  897 */       return Object2BooleanOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/*  903 */       return new Object2BooleanOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/*  908 */       if (Object2BooleanOpenHashMap.this.containsNullKey)
/*  909 */         consumer.accept(Object2BooleanOpenHashMap.this.key[Object2BooleanOpenHashMap.this.n]); 
/*  910 */       for (int pos = Object2BooleanOpenHashMap.this.n; pos-- != 0; ) {
/*  911 */         K k = Object2BooleanOpenHashMap.this.key[pos];
/*  912 */         if (k != null)
/*  913 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  918 */       return Object2BooleanOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/*  922 */       return Object2BooleanOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/*  926 */       int oldSize = Object2BooleanOpenHashMap.this.size;
/*  927 */       Object2BooleanOpenHashMap.this.removeBoolean(k);
/*  928 */       return (Object2BooleanOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  932 */       Object2BooleanOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSet<K> keySet() {
/*  937 */     if (this.keys == null)
/*  938 */       this.keys = new KeySet(); 
/*  939 */     return this.keys;
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
/*      */     implements BooleanIterator
/*      */   {
/*      */     public boolean nextBoolean() {
/*  956 */       return Object2BooleanOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public BooleanCollection values() {
/*  961 */     if (this.values == null)
/*  962 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/*  965 */             return new Object2BooleanOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/*  969 */             return Object2BooleanOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(boolean v) {
/*  973 */             return Object2BooleanOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/*  977 */             Object2BooleanOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(BooleanConsumer consumer)
/*      */           {
/*  982 */             if (Object2BooleanOpenHashMap.this.containsNullKey)
/*  983 */               consumer.accept(Object2BooleanOpenHashMap.this.value[Object2BooleanOpenHashMap.this.n]); 
/*  984 */             for (int pos = Object2BooleanOpenHashMap.this.n; pos-- != 0;) {
/*  985 */               if (Object2BooleanOpenHashMap.this.key[pos] != null)
/*  986 */                 consumer.accept(Object2BooleanOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/*  989 */     return this.values;
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
/* 1006 */     return trim(this.size);
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
/* 1030 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1031 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1032 */       return true; 
/*      */     try {
/* 1034 */       rehash(l);
/* 1035 */     } catch (OutOfMemoryError cantDoIt) {
/* 1036 */       return false;
/*      */     } 
/* 1038 */     return true;
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
/* 1054 */     K[] key = this.key;
/* 1055 */     boolean[] value = this.value;
/* 1056 */     int mask = newN - 1;
/* 1057 */     K[] newKey = (K[])new Object[newN + 1];
/* 1058 */     boolean[] newValue = new boolean[newN + 1];
/* 1059 */     int i = this.n;
/* 1060 */     for (int j = realSize(); j-- != 0; ) {
/* 1061 */       while (key[--i] == null); int pos;
/* 1062 */       if (newKey[pos = HashCommon.mix(key[i].hashCode()) & mask] != null)
/* 1063 */         while (newKey[pos = pos + 1 & mask] != null); 
/* 1064 */       newKey[pos] = key[i];
/* 1065 */       newValue[pos] = value[i];
/*      */     } 
/* 1067 */     newValue[newN] = value[this.n];
/* 1068 */     this.n = newN;
/* 1069 */     this.mask = mask;
/* 1070 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1071 */     this.key = newKey;
/* 1072 */     this.value = newValue;
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
/*      */   public Object2BooleanOpenHashMap<K> clone() {
/*      */     Object2BooleanOpenHashMap<K> c;
/*      */     try {
/* 1089 */       c = (Object2BooleanOpenHashMap<K>)super.clone();
/* 1090 */     } catch (CloneNotSupportedException cantHappen) {
/* 1091 */       throw new InternalError();
/*      */     } 
/* 1093 */     c.keys = null;
/* 1094 */     c.values = null;
/* 1095 */     c.entries = null;
/* 1096 */     c.containsNullKey = this.containsNullKey;
/* 1097 */     c.key = (K[])this.key.clone();
/* 1098 */     c.value = (boolean[])this.value.clone();
/* 1099 */     return c;
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
/* 1112 */     int h = 0;
/* 1113 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1114 */       while (this.key[i] == null)
/* 1115 */         i++; 
/* 1116 */       if (this != this.key[i])
/* 1117 */         t = this.key[i].hashCode(); 
/* 1118 */       t ^= this.value[i] ? 1231 : 1237;
/* 1119 */       h += t;
/* 1120 */       i++;
/*      */     } 
/*      */     
/* 1123 */     if (this.containsNullKey)
/* 1124 */       h += this.value[this.n] ? 1231 : 1237; 
/* 1125 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1128 */     K[] key = this.key;
/* 1129 */     boolean[] value = this.value;
/* 1130 */     MapIterator i = new MapIterator();
/* 1131 */     s.defaultWriteObject();
/* 1132 */     for (int j = this.size; j-- != 0; ) {
/* 1133 */       int e = i.nextEntry();
/* 1134 */       s.writeObject(key[e]);
/* 1135 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1140 */     s.defaultReadObject();
/* 1141 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1142 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1143 */     this.mask = this.n - 1;
/* 1144 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1145 */     boolean[] value = this.value = new boolean[this.n + 1];
/*      */ 
/*      */     
/* 1148 */     for (int i = this.size; i-- != 0; ) {
/* 1149 */       int pos; K k = (K)s.readObject();
/* 1150 */       boolean v = s.readBoolean();
/* 1151 */       if (k == null) {
/* 1152 */         pos = this.n;
/* 1153 */         this.containsNullKey = true;
/*      */       } else {
/* 1155 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 1156 */         while (key[pos] != null)
/* 1157 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1159 */       key[pos] = k;
/* 1160 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2BooleanOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */