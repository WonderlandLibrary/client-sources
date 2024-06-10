/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
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
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongFunction;
/*      */ import java.util.function.LongPredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Long2BooleanOpenHashMap
/*      */   extends AbstractLong2BooleanMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Long2BooleanMap.FastEntrySet entries;
/*      */   protected transient LongSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   
/*      */   public Long2BooleanOpenHashMap(int expected, float f) {
/*   97 */     if (f <= 0.0F || f > 1.0F)
/*   98 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*   99 */     if (expected < 0)
/*  100 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  101 */     this.f = f;
/*  102 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  103 */     this.mask = this.n - 1;
/*  104 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  105 */     this.key = new long[this.n + 1];
/*  106 */     this.value = new boolean[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2BooleanOpenHashMap(int expected) {
/*  115 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2BooleanOpenHashMap() {
/*  123 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2BooleanOpenHashMap(Map<? extends Long, ? extends Boolean> m, float f) {
/*  134 */     this(m.size(), f);
/*  135 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2BooleanOpenHashMap(Map<? extends Long, ? extends Boolean> m) {
/*  145 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2BooleanOpenHashMap(Long2BooleanMap m, float f) {
/*  156 */     this(m.size(), f);
/*  157 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2BooleanOpenHashMap(Long2BooleanMap m) {
/*  167 */     this(m, 0.75F);
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
/*      */   public Long2BooleanOpenHashMap(long[] k, boolean[] v, float f) {
/*  182 */     this(k.length, f);
/*  183 */     if (k.length != v.length) {
/*  184 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  186 */     for (int i = 0; i < k.length; i++) {
/*  187 */       put(k[i], v[i]);
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
/*      */   public Long2BooleanOpenHashMap(long[] k, boolean[] v) {
/*  201 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  204 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  207 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  208 */     if (needed > this.n)
/*  209 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  212 */     int needed = (int)Math.min(1073741824L, 
/*  213 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  214 */     if (needed > this.n)
/*  215 */       rehash(needed); 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  218 */     boolean oldValue = this.value[pos];
/*  219 */     this.size--;
/*  220 */     shiftKeys(pos);
/*  221 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  222 */       rehash(this.n / 2); 
/*  223 */     return oldValue;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  226 */     this.containsNullKey = false;
/*  227 */     boolean oldValue = this.value[this.n];
/*  228 */     this.size--;
/*  229 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  230 */       rehash(this.n / 2); 
/*  231 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Long, ? extends Boolean> m) {
/*  235 */     if (this.f <= 0.5D) {
/*  236 */       ensureCapacity(m.size());
/*      */     } else {
/*  238 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  240 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(long k) {
/*  244 */     if (k == 0L) {
/*  245 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  247 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  250 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  251 */       return -(pos + 1); 
/*  252 */     if (k == curr) {
/*  253 */       return pos;
/*      */     }
/*      */     while (true) {
/*  256 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  257 */         return -(pos + 1); 
/*  258 */       if (k == curr)
/*  259 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, long k, boolean v) {
/*  263 */     if (pos == this.n)
/*  264 */       this.containsNullKey = true; 
/*  265 */     this.key[pos] = k;
/*  266 */     this.value[pos] = v;
/*  267 */     if (this.size++ >= this.maxFill) {
/*  268 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean put(long k, boolean v) {
/*  274 */     int pos = find(k);
/*  275 */     if (pos < 0) {
/*  276 */       insert(-pos - 1, k, v);
/*  277 */       return this.defRetValue;
/*      */     } 
/*  279 */     boolean oldValue = this.value[pos];
/*  280 */     this.value[pos] = v;
/*  281 */     return oldValue;
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
/*  294 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  296 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  298 */         if ((curr = key[pos]) == 0L) {
/*  299 */           key[last] = 0L;
/*      */           return;
/*      */         } 
/*  302 */         int slot = (int)HashCommon.mix(curr) & this.mask;
/*  303 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  305 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  307 */       key[last] = curr;
/*  308 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(long k) {
/*  314 */     if (k == 0L) {
/*  315 */       if (this.containsNullKey)
/*  316 */         return removeNullEntry(); 
/*  317 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  320 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  323 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  324 */       return this.defRetValue; 
/*  325 */     if (k == curr)
/*  326 */       return removeEntry(pos); 
/*      */     while (true) {
/*  328 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  329 */         return this.defRetValue; 
/*  330 */       if (k == curr) {
/*  331 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean get(long k) {
/*  337 */     if (k == 0L) {
/*  338 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  340 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  343 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  344 */       return this.defRetValue; 
/*  345 */     if (k == curr) {
/*  346 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  349 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  350 */         return this.defRetValue; 
/*  351 */       if (k == curr) {
/*  352 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(long k) {
/*  358 */     if (k == 0L) {
/*  359 */       return this.containsNullKey;
/*      */     }
/*  361 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  364 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  365 */       return false; 
/*  366 */     if (k == curr) {
/*  367 */       return true;
/*      */     }
/*      */     while (true) {
/*  370 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  371 */         return false; 
/*  372 */       if (k == curr)
/*  373 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  378 */     boolean[] value = this.value;
/*  379 */     long[] key = this.key;
/*  380 */     if (this.containsNullKey && value[this.n] == v)
/*  381 */       return true; 
/*  382 */     for (int i = this.n; i-- != 0;) {
/*  383 */       if (key[i] != 0L && value[i] == v)
/*  384 */         return true; 
/*  385 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(long k, boolean defaultValue) {
/*  391 */     if (k == 0L) {
/*  392 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  394 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  397 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  398 */       return defaultValue; 
/*  399 */     if (k == curr) {
/*  400 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  403 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  404 */         return defaultValue; 
/*  405 */       if (k == curr) {
/*  406 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean putIfAbsent(long k, boolean v) {
/*  412 */     int pos = find(k);
/*  413 */     if (pos >= 0)
/*  414 */       return this.value[pos]; 
/*  415 */     insert(-pos - 1, k, v);
/*  416 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, boolean v) {
/*  422 */     if (k == 0L) {
/*  423 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  424 */         removeNullEntry();
/*  425 */         return true;
/*      */       } 
/*  427 */       return false;
/*      */     } 
/*      */     
/*  430 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  433 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  434 */       return false; 
/*  435 */     if (k == curr && v == this.value[pos]) {
/*  436 */       removeEntry(pos);
/*  437 */       return true;
/*      */     } 
/*      */     while (true) {
/*  440 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  441 */         return false; 
/*  442 */       if (k == curr && v == this.value[pos]) {
/*  443 */         removeEntry(pos);
/*  444 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(long k, boolean oldValue, boolean v) {
/*  451 */     int pos = find(k);
/*  452 */     if (pos < 0 || oldValue != this.value[pos])
/*  453 */       return false; 
/*  454 */     this.value[pos] = v;
/*  455 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(long k, boolean v) {
/*  460 */     int pos = find(k);
/*  461 */     if (pos < 0)
/*  462 */       return this.defRetValue; 
/*  463 */     boolean oldValue = this.value[pos];
/*  464 */     this.value[pos] = v;
/*  465 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(long k, LongPredicate mappingFunction) {
/*  470 */     Objects.requireNonNull(mappingFunction);
/*  471 */     int pos = find(k);
/*  472 */     if (pos >= 0)
/*  473 */       return this.value[pos]; 
/*  474 */     boolean newValue = mappingFunction.test(k);
/*  475 */     insert(-pos - 1, k, newValue);
/*  476 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsentNullable(long k, LongFunction<? extends Boolean> mappingFunction) {
/*  482 */     Objects.requireNonNull(mappingFunction);
/*  483 */     int pos = find(k);
/*  484 */     if (pos >= 0)
/*  485 */       return this.value[pos]; 
/*  486 */     Boolean newValue = mappingFunction.apply(k);
/*  487 */     if (newValue == null)
/*  488 */       return this.defRetValue; 
/*  489 */     boolean v = newValue.booleanValue();
/*  490 */     insert(-pos - 1, k, v);
/*  491 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfPresent(long k, BiFunction<? super Long, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  497 */     Objects.requireNonNull(remappingFunction);
/*  498 */     int pos = find(k);
/*  499 */     if (pos < 0)
/*  500 */       return this.defRetValue; 
/*  501 */     Boolean newValue = remappingFunction.apply(Long.valueOf(k), Boolean.valueOf(this.value[pos]));
/*  502 */     if (newValue == null) {
/*  503 */       if (k == 0L) {
/*  504 */         removeNullEntry();
/*      */       } else {
/*  506 */         removeEntry(pos);
/*  507 */       }  return this.defRetValue;
/*      */     } 
/*  509 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compute(long k, BiFunction<? super Long, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  515 */     Objects.requireNonNull(remappingFunction);
/*  516 */     int pos = find(k);
/*  517 */     Boolean newValue = remappingFunction.apply(Long.valueOf(k), 
/*  518 */         (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  519 */     if (newValue == null) {
/*  520 */       if (pos >= 0)
/*  521 */         if (k == 0L) {
/*  522 */           removeNullEntry();
/*      */         } else {
/*  524 */           removeEntry(pos);
/*      */         }  
/*  526 */       return this.defRetValue;
/*      */     } 
/*  528 */     boolean newVal = newValue.booleanValue();
/*  529 */     if (pos < 0) {
/*  530 */       insert(-pos - 1, k, newVal);
/*  531 */       return newVal;
/*      */     } 
/*  533 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean merge(long k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  539 */     Objects.requireNonNull(remappingFunction);
/*  540 */     int pos = find(k);
/*  541 */     if (pos < 0) {
/*  542 */       insert(-pos - 1, k, v);
/*  543 */       return v;
/*      */     } 
/*  545 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  546 */     if (newValue == null) {
/*  547 */       if (k == 0L) {
/*  548 */         removeNullEntry();
/*      */       } else {
/*  550 */         removeEntry(pos);
/*  551 */       }  return this.defRetValue;
/*      */     } 
/*  553 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
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
/*  564 */     if (this.size == 0)
/*      */       return; 
/*  566 */     this.size = 0;
/*  567 */     this.containsNullKey = false;
/*  568 */     Arrays.fill(this.key, 0L);
/*      */   }
/*      */   
/*      */   public int size() {
/*  572 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  576 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Long2BooleanMap.Entry, Map.Entry<Long, Boolean>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  588 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public long getLongKey() {
/*  594 */       return Long2BooleanOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public boolean getBooleanValue() {
/*  598 */       return Long2BooleanOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  602 */       boolean oldValue = Long2BooleanOpenHashMap.this.value[this.index];
/*  603 */       Long2BooleanOpenHashMap.this.value[this.index] = v;
/*  604 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getKey() {
/*  614 */       return Long.valueOf(Long2BooleanOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  624 */       return Boolean.valueOf(Long2BooleanOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean setValue(Boolean v) {
/*  634 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  639 */       if (!(o instanceof Map.Entry))
/*  640 */         return false; 
/*  641 */       Map.Entry<Long, Boolean> e = (Map.Entry<Long, Boolean>)o;
/*  642 */       return (Long2BooleanOpenHashMap.this.key[this.index] == ((Long)e.getKey()).longValue() && Long2BooleanOpenHashMap.this.value[this.index] == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  646 */       return HashCommon.long2int(Long2BooleanOpenHashMap.this.key[this.index]) ^ (Long2BooleanOpenHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  650 */       return Long2BooleanOpenHashMap.this.key[this.index] + "=>" + Long2BooleanOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  660 */     int pos = Long2BooleanOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  667 */     int last = -1;
/*      */     
/*  669 */     int c = Long2BooleanOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  673 */     boolean mustReturnNullKey = Long2BooleanOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     LongArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  680 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  683 */       if (!hasNext())
/*  684 */         throw new NoSuchElementException(); 
/*  685 */       this.c--;
/*  686 */       if (this.mustReturnNullKey) {
/*  687 */         this.mustReturnNullKey = false;
/*  688 */         return this.last = Long2BooleanOpenHashMap.this.n;
/*      */       } 
/*  690 */       long[] key = Long2BooleanOpenHashMap.this.key;
/*      */       while (true) {
/*  692 */         if (--this.pos < 0) {
/*      */           
/*  694 */           this.last = Integer.MIN_VALUE;
/*  695 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  696 */           int p = (int)HashCommon.mix(k) & Long2BooleanOpenHashMap.this.mask;
/*  697 */           while (k != key[p])
/*  698 */             p = p + 1 & Long2BooleanOpenHashMap.this.mask; 
/*  699 */           return p;
/*      */         } 
/*  701 */         if (key[this.pos] != 0L) {
/*  702 */           return this.last = this.pos;
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
/*  716 */       long[] key = Long2BooleanOpenHashMap.this.key; while (true) {
/*      */         long curr; int last;
/*  718 */         pos = (last = pos) + 1 & Long2BooleanOpenHashMap.this.mask;
/*      */         while (true) {
/*  720 */           if ((curr = key[pos]) == 0L) {
/*  721 */             key[last] = 0L;
/*      */             return;
/*      */           } 
/*  724 */           int slot = (int)HashCommon.mix(curr) & Long2BooleanOpenHashMap.this.mask;
/*  725 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  727 */           pos = pos + 1 & Long2BooleanOpenHashMap.this.mask;
/*      */         } 
/*  729 */         if (pos < last) {
/*  730 */           if (this.wrapped == null)
/*  731 */             this.wrapped = new LongArrayList(2); 
/*  732 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  734 */         key[last] = curr;
/*  735 */         Long2BooleanOpenHashMap.this.value[last] = Long2BooleanOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  739 */       if (this.last == -1)
/*  740 */         throw new IllegalStateException(); 
/*  741 */       if (this.last == Long2BooleanOpenHashMap.this.n) {
/*  742 */         Long2BooleanOpenHashMap.this.containsNullKey = false;
/*  743 */       } else if (this.pos >= 0) {
/*  744 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  747 */         Long2BooleanOpenHashMap.this.remove(this.wrapped.getLong(-this.pos - 1));
/*  748 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  751 */       Long2BooleanOpenHashMap.this.size--;
/*  752 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  757 */       int i = n;
/*  758 */       while (i-- != 0 && hasNext())
/*  759 */         nextEntry(); 
/*  760 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Long2BooleanMap.Entry> { private Long2BooleanOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Long2BooleanOpenHashMap.MapEntry next() {
/*  767 */       return this.entry = new Long2BooleanOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  771 */       super.remove();
/*  772 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Long2BooleanMap.Entry> { private FastEntryIterator() {
/*  776 */       this.entry = new Long2BooleanOpenHashMap.MapEntry();
/*      */     } private final Long2BooleanOpenHashMap.MapEntry entry;
/*      */     public Long2BooleanOpenHashMap.MapEntry next() {
/*  779 */       this.entry.index = nextEntry();
/*  780 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Long2BooleanMap.Entry> implements Long2BooleanMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Long2BooleanMap.Entry> iterator() {
/*  786 */       return new Long2BooleanOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Long2BooleanMap.Entry> fastIterator() {
/*  790 */       return new Long2BooleanOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  795 */       if (!(o instanceof Map.Entry))
/*  796 */         return false; 
/*  797 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  798 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  799 */         return false; 
/*  800 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  801 */         return false; 
/*  802 */       long k = ((Long)e.getKey()).longValue();
/*  803 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  804 */       if (k == 0L) {
/*  805 */         return (Long2BooleanOpenHashMap.this.containsNullKey && Long2BooleanOpenHashMap.this.value[Long2BooleanOpenHashMap.this.n] == v);
/*      */       }
/*  807 */       long[] key = Long2BooleanOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  810 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2BooleanOpenHashMap.this.mask]) == 0L)
/*  811 */         return false; 
/*  812 */       if (k == curr) {
/*  813 */         return (Long2BooleanOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  816 */         if ((curr = key[pos = pos + 1 & Long2BooleanOpenHashMap.this.mask]) == 0L)
/*  817 */           return false; 
/*  818 */         if (k == curr) {
/*  819 */           return (Long2BooleanOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  825 */       if (!(o instanceof Map.Entry))
/*  826 */         return false; 
/*  827 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  828 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  829 */         return false; 
/*  830 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  831 */         return false; 
/*  832 */       long k = ((Long)e.getKey()).longValue();
/*  833 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  834 */       if (k == 0L) {
/*  835 */         if (Long2BooleanOpenHashMap.this.containsNullKey && Long2BooleanOpenHashMap.this.value[Long2BooleanOpenHashMap.this.n] == v) {
/*  836 */           Long2BooleanOpenHashMap.this.removeNullEntry();
/*  837 */           return true;
/*      */         } 
/*  839 */         return false;
/*      */       } 
/*      */       
/*  842 */       long[] key = Long2BooleanOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  845 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2BooleanOpenHashMap.this.mask]) == 0L)
/*  846 */         return false; 
/*  847 */       if (curr == k) {
/*  848 */         if (Long2BooleanOpenHashMap.this.value[pos] == v) {
/*  849 */           Long2BooleanOpenHashMap.this.removeEntry(pos);
/*  850 */           return true;
/*      */         } 
/*  852 */         return false;
/*      */       } 
/*      */       while (true) {
/*  855 */         if ((curr = key[pos = pos + 1 & Long2BooleanOpenHashMap.this.mask]) == 0L)
/*  856 */           return false; 
/*  857 */         if (curr == k && 
/*  858 */           Long2BooleanOpenHashMap.this.value[pos] == v) {
/*  859 */           Long2BooleanOpenHashMap.this.removeEntry(pos);
/*  860 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  867 */       return Long2BooleanOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  871 */       Long2BooleanOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2BooleanMap.Entry> consumer) {
/*  876 */       if (Long2BooleanOpenHashMap.this.containsNullKey)
/*  877 */         consumer.accept(new AbstractLong2BooleanMap.BasicEntry(Long2BooleanOpenHashMap.this.key[Long2BooleanOpenHashMap.this.n], Long2BooleanOpenHashMap.this.value[Long2BooleanOpenHashMap.this.n])); 
/*  878 */       for (int pos = Long2BooleanOpenHashMap.this.n; pos-- != 0;) {
/*  879 */         if (Long2BooleanOpenHashMap.this.key[pos] != 0L)
/*  880 */           consumer.accept(new AbstractLong2BooleanMap.BasicEntry(Long2BooleanOpenHashMap.this.key[pos], Long2BooleanOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2BooleanMap.Entry> consumer) {
/*  885 */       AbstractLong2BooleanMap.BasicEntry entry = new AbstractLong2BooleanMap.BasicEntry();
/*  886 */       if (Long2BooleanOpenHashMap.this.containsNullKey) {
/*  887 */         entry.key = Long2BooleanOpenHashMap.this.key[Long2BooleanOpenHashMap.this.n];
/*  888 */         entry.value = Long2BooleanOpenHashMap.this.value[Long2BooleanOpenHashMap.this.n];
/*  889 */         consumer.accept(entry);
/*      */       } 
/*  891 */       for (int pos = Long2BooleanOpenHashMap.this.n; pos-- != 0;) {
/*  892 */         if (Long2BooleanOpenHashMap.this.key[pos] != 0L) {
/*  893 */           entry.key = Long2BooleanOpenHashMap.this.key[pos];
/*  894 */           entry.value = Long2BooleanOpenHashMap.this.value[pos];
/*  895 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Long2BooleanMap.FastEntrySet long2BooleanEntrySet() {
/*  901 */     if (this.entries == null)
/*  902 */       this.entries = new MapEntrySet(); 
/*  903 */     return this.entries;
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
/*      */     implements LongIterator
/*      */   {
/*      */     public long nextLong() {
/*  920 */       return Long2BooleanOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSet { private KeySet() {}
/*      */     
/*      */     public LongIterator iterator() {
/*  926 */       return new Long2BooleanOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/*  931 */       if (Long2BooleanOpenHashMap.this.containsNullKey)
/*  932 */         consumer.accept(Long2BooleanOpenHashMap.this.key[Long2BooleanOpenHashMap.this.n]); 
/*  933 */       for (int pos = Long2BooleanOpenHashMap.this.n; pos-- != 0; ) {
/*  934 */         long k = Long2BooleanOpenHashMap.this.key[pos];
/*  935 */         if (k != 0L)
/*  936 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  941 */       return Long2BooleanOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(long k) {
/*  945 */       return Long2BooleanOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(long k) {
/*  949 */       int oldSize = Long2BooleanOpenHashMap.this.size;
/*  950 */       Long2BooleanOpenHashMap.this.remove(k);
/*  951 */       return (Long2BooleanOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  955 */       Long2BooleanOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public LongSet keySet() {
/*  960 */     if (this.keys == null)
/*  961 */       this.keys = new KeySet(); 
/*  962 */     return this.keys;
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
/*  979 */       return Long2BooleanOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public BooleanCollection values() {
/*  984 */     if (this.values == null)
/*  985 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/*  988 */             return new Long2BooleanOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/*  992 */             return Long2BooleanOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(boolean v) {
/*  996 */             return Long2BooleanOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1000 */             Long2BooleanOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(BooleanConsumer consumer)
/*      */           {
/* 1005 */             if (Long2BooleanOpenHashMap.this.containsNullKey)
/* 1006 */               consumer.accept(Long2BooleanOpenHashMap.this.value[Long2BooleanOpenHashMap.this.n]); 
/* 1007 */             for (int pos = Long2BooleanOpenHashMap.this.n; pos-- != 0;) {
/* 1008 */               if (Long2BooleanOpenHashMap.this.key[pos] != 0L)
/* 1009 */                 consumer.accept(Long2BooleanOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1012 */     return this.values;
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
/* 1029 */     return trim(this.size);
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
/* 1053 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1054 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1055 */       return true; 
/*      */     try {
/* 1057 */       rehash(l);
/* 1058 */     } catch (OutOfMemoryError cantDoIt) {
/* 1059 */       return false;
/*      */     } 
/* 1061 */     return true;
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
/* 1077 */     long[] key = this.key;
/* 1078 */     boolean[] value = this.value;
/* 1079 */     int mask = newN - 1;
/* 1080 */     long[] newKey = new long[newN + 1];
/* 1081 */     boolean[] newValue = new boolean[newN + 1];
/* 1082 */     int i = this.n;
/* 1083 */     for (int j = realSize(); j-- != 0; ) {
/* 1084 */       while (key[--i] == 0L); int pos;
/* 1085 */       if (newKey[pos = (int)HashCommon.mix(key[i]) & mask] != 0L)
/* 1086 */         while (newKey[pos = pos + 1 & mask] != 0L); 
/* 1087 */       newKey[pos] = key[i];
/* 1088 */       newValue[pos] = value[i];
/*      */     } 
/* 1090 */     newValue[newN] = value[this.n];
/* 1091 */     this.n = newN;
/* 1092 */     this.mask = mask;
/* 1093 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1094 */     this.key = newKey;
/* 1095 */     this.value = newValue;
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
/*      */   public Long2BooleanOpenHashMap clone() {
/*      */     Long2BooleanOpenHashMap c;
/*      */     try {
/* 1112 */       c = (Long2BooleanOpenHashMap)super.clone();
/* 1113 */     } catch (CloneNotSupportedException cantHappen) {
/* 1114 */       throw new InternalError();
/*      */     } 
/* 1116 */     c.keys = null;
/* 1117 */     c.values = null;
/* 1118 */     c.entries = null;
/* 1119 */     c.containsNullKey = this.containsNullKey;
/* 1120 */     c.key = (long[])this.key.clone();
/* 1121 */     c.value = (boolean[])this.value.clone();
/* 1122 */     return c;
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
/* 1135 */     int h = 0;
/* 1136 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1137 */       while (this.key[i] == 0L)
/* 1138 */         i++; 
/* 1139 */       t = HashCommon.long2int(this.key[i]);
/* 1140 */       t ^= this.value[i] ? 1231 : 1237;
/* 1141 */       h += t;
/* 1142 */       i++;
/*      */     } 
/*      */     
/* 1145 */     if (this.containsNullKey)
/* 1146 */       h += this.value[this.n] ? 1231 : 1237; 
/* 1147 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1150 */     long[] key = this.key;
/* 1151 */     boolean[] value = this.value;
/* 1152 */     MapIterator i = new MapIterator();
/* 1153 */     s.defaultWriteObject();
/* 1154 */     for (int j = this.size; j-- != 0; ) {
/* 1155 */       int e = i.nextEntry();
/* 1156 */       s.writeLong(key[e]);
/* 1157 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1162 */     s.defaultReadObject();
/* 1163 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1164 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1165 */     this.mask = this.n - 1;
/* 1166 */     long[] key = this.key = new long[this.n + 1];
/* 1167 */     boolean[] value = this.value = new boolean[this.n + 1];
/*      */ 
/*      */     
/* 1170 */     for (int i = this.size; i-- != 0; ) {
/* 1171 */       int pos; long k = s.readLong();
/* 1172 */       boolean v = s.readBoolean();
/* 1173 */       if (k == 0L) {
/* 1174 */         pos = this.n;
/* 1175 */         this.containsNullKey = true;
/*      */       } else {
/* 1177 */         pos = (int)HashCommon.mix(k) & this.mask;
/* 1178 */         while (key[pos] != 0L)
/* 1179 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1181 */       key[pos] = k;
/* 1182 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2BooleanOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */