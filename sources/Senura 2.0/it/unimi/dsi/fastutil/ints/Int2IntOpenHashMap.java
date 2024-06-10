/*      */ package it.unimi.dsi.fastutil.ints;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
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
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.IntUnaryOperator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Int2IntOpenHashMap
/*      */   extends AbstractInt2IntMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient int[] key;
/*      */   protected transient int[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Int2IntMap.FastEntrySet entries;
/*      */   protected transient IntSet keys;
/*      */   protected transient IntCollection values;
/*      */   
/*      */   public Int2IntOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f > 1.0F)
/*   97 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*   98 */     if (expected < 0)
/*   99 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  100 */     this.f = f;
/*  101 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  102 */     this.mask = this.n - 1;
/*  103 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  104 */     this.key = new int[this.n + 1];
/*  105 */     this.value = new int[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2IntOpenHashMap(int expected) {
/*  114 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2IntOpenHashMap() {
/*  122 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2IntOpenHashMap(Map<? extends Integer, ? extends Integer> m, float f) {
/*  133 */     this(m.size(), f);
/*  134 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2IntOpenHashMap(Map<? extends Integer, ? extends Integer> m) {
/*  144 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2IntOpenHashMap(Int2IntMap m, float f) {
/*  155 */     this(m.size(), f);
/*  156 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2IntOpenHashMap(Int2IntMap m) {
/*  166 */     this(m, 0.75F);
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
/*      */   public Int2IntOpenHashMap(int[] k, int[] v, float f) {
/*  181 */     this(k.length, f);
/*  182 */     if (k.length != v.length) {
/*  183 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  185 */     for (int i = 0; i < k.length; i++) {
/*  186 */       put(k[i], v[i]);
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
/*      */   public Int2IntOpenHashMap(int[] k, int[] v) {
/*  200 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  203 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  206 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  207 */     if (needed > this.n)
/*  208 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  211 */     int needed = (int)Math.min(1073741824L, 
/*  212 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  213 */     if (needed > this.n)
/*  214 */       rehash(needed); 
/*      */   }
/*      */   private int removeEntry(int pos) {
/*  217 */     int oldValue = this.value[pos];
/*  218 */     this.size--;
/*  219 */     shiftKeys(pos);
/*  220 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  221 */       rehash(this.n / 2); 
/*  222 */     return oldValue;
/*      */   }
/*      */   private int removeNullEntry() {
/*  225 */     this.containsNullKey = false;
/*  226 */     int oldValue = this.value[this.n];
/*  227 */     this.size--;
/*  228 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  229 */       rehash(this.n / 2); 
/*  230 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Integer, ? extends Integer> m) {
/*  234 */     if (this.f <= 0.5D) {
/*  235 */       ensureCapacity(m.size());
/*      */     } else {
/*  237 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  239 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(int k) {
/*  243 */     if (k == 0) {
/*  244 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  246 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  249 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  250 */       return -(pos + 1); 
/*  251 */     if (k == curr) {
/*  252 */       return pos;
/*      */     }
/*      */     while (true) {
/*  255 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  256 */         return -(pos + 1); 
/*  257 */       if (k == curr)
/*  258 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, int k, int v) {
/*  262 */     if (pos == this.n)
/*  263 */       this.containsNullKey = true; 
/*  264 */     this.key[pos] = k;
/*  265 */     this.value[pos] = v;
/*  266 */     if (this.size++ >= this.maxFill) {
/*  267 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public int put(int k, int v) {
/*  273 */     int pos = find(k);
/*  274 */     if (pos < 0) {
/*  275 */       insert(-pos - 1, k, v);
/*  276 */       return this.defRetValue;
/*      */     } 
/*  278 */     int oldValue = this.value[pos];
/*  279 */     this.value[pos] = v;
/*  280 */     return oldValue;
/*      */   }
/*      */   private int addToValue(int pos, int incr) {
/*  283 */     int oldValue = this.value[pos];
/*  284 */     this.value[pos] = oldValue + incr;
/*  285 */     return oldValue;
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
/*      */   public int addTo(int k, int incr) {
/*      */     int pos;
/*  305 */     if (k == 0) {
/*  306 */       if (this.containsNullKey)
/*  307 */         return addToValue(this.n, incr); 
/*  308 */       pos = this.n;
/*  309 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  312 */       int[] key = this.key;
/*      */       int curr;
/*  314 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  315 */         if (curr == k)
/*  316 */           return addToValue(pos, incr); 
/*  317 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  318 */           if (curr == k)
/*  319 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  322 */     }  this.key[pos] = k;
/*  323 */     this.value[pos] = this.defRetValue + incr;
/*  324 */     if (this.size++ >= this.maxFill) {
/*  325 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  328 */     return this.defRetValue;
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
/*  341 */     int[] key = this.key; while (true) {
/*      */       int curr, last;
/*  343 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  345 */         if ((curr = key[pos]) == 0) {
/*  346 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  349 */         int slot = HashCommon.mix(curr) & this.mask;
/*  350 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  352 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  354 */       key[last] = curr;
/*  355 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int remove(int k) {
/*  361 */     if (k == 0) {
/*  362 */       if (this.containsNullKey)
/*  363 */         return removeNullEntry(); 
/*  364 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  367 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  370 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  371 */       return this.defRetValue; 
/*  372 */     if (k == curr)
/*  373 */       return removeEntry(pos); 
/*      */     while (true) {
/*  375 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  376 */         return this.defRetValue; 
/*  377 */       if (k == curr) {
/*  378 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int get(int k) {
/*  384 */     if (k == 0) {
/*  385 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  387 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  390 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  391 */       return this.defRetValue; 
/*  392 */     if (k == curr) {
/*  393 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  396 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  397 */         return this.defRetValue; 
/*  398 */       if (k == curr) {
/*  399 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(int k) {
/*  405 */     if (k == 0) {
/*  406 */       return this.containsNullKey;
/*      */     }
/*  408 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  411 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  412 */       return false; 
/*  413 */     if (k == curr) {
/*  414 */       return true;
/*      */     }
/*      */     while (true) {
/*  417 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  418 */         return false; 
/*  419 */       if (k == curr)
/*  420 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(int v) {
/*  425 */     int[] value = this.value;
/*  426 */     int[] key = this.key;
/*  427 */     if (this.containsNullKey && value[this.n] == v)
/*  428 */       return true; 
/*  429 */     for (int i = this.n; i-- != 0;) {
/*  430 */       if (key[i] != 0 && value[i] == v)
/*  431 */         return true; 
/*  432 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOrDefault(int k, int defaultValue) {
/*  438 */     if (k == 0) {
/*  439 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  441 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  444 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  445 */       return defaultValue; 
/*  446 */     if (k == curr) {
/*  447 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  450 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  451 */         return defaultValue; 
/*  452 */       if (k == curr) {
/*  453 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int putIfAbsent(int k, int v) {
/*  459 */     int pos = find(k);
/*  460 */     if (pos >= 0)
/*  461 */       return this.value[pos]; 
/*  462 */     insert(-pos - 1, k, v);
/*  463 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(int k, int v) {
/*  469 */     if (k == 0) {
/*  470 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  471 */         removeNullEntry();
/*  472 */         return true;
/*      */       } 
/*  474 */       return false;
/*      */     } 
/*      */     
/*  477 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  480 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  481 */       return false; 
/*  482 */     if (k == curr && v == this.value[pos]) {
/*  483 */       removeEntry(pos);
/*  484 */       return true;
/*      */     } 
/*      */     while (true) {
/*  487 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  488 */         return false; 
/*  489 */       if (k == curr && v == this.value[pos]) {
/*  490 */         removeEntry(pos);
/*  491 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(int k, int oldValue, int v) {
/*  498 */     int pos = find(k);
/*  499 */     if (pos < 0 || oldValue != this.value[pos])
/*  500 */       return false; 
/*  501 */     this.value[pos] = v;
/*  502 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int replace(int k, int v) {
/*  507 */     int pos = find(k);
/*  508 */     if (pos < 0)
/*  509 */       return this.defRetValue; 
/*  510 */     int oldValue = this.value[pos];
/*  511 */     this.value[pos] = v;
/*  512 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(int k, IntUnaryOperator mappingFunction) {
/*  517 */     Objects.requireNonNull(mappingFunction);
/*  518 */     int pos = find(k);
/*  519 */     if (pos >= 0)
/*  520 */       return this.value[pos]; 
/*  521 */     int newValue = mappingFunction.applyAsInt(k);
/*  522 */     insert(-pos - 1, k, newValue);
/*  523 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsentNullable(int k, IntFunction<? extends Integer> mappingFunction) {
/*  529 */     Objects.requireNonNull(mappingFunction);
/*  530 */     int pos = find(k);
/*  531 */     if (pos >= 0)
/*  532 */       return this.value[pos]; 
/*  533 */     Integer newValue = mappingFunction.apply(k);
/*  534 */     if (newValue == null)
/*  535 */       return this.defRetValue; 
/*  536 */     int v = newValue.intValue();
/*  537 */     insert(-pos - 1, k, v);
/*  538 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfPresent(int k, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  544 */     Objects.requireNonNull(remappingFunction);
/*  545 */     int pos = find(k);
/*  546 */     if (pos < 0)
/*  547 */       return this.defRetValue; 
/*  548 */     Integer newValue = remappingFunction.apply(Integer.valueOf(k), Integer.valueOf(this.value[pos]));
/*  549 */     if (newValue == null) {
/*  550 */       if (k == 0) {
/*  551 */         removeNullEntry();
/*      */       } else {
/*  553 */         removeEntry(pos);
/*  554 */       }  return this.defRetValue;
/*      */     } 
/*  556 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int compute(int k, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  562 */     Objects.requireNonNull(remappingFunction);
/*  563 */     int pos = find(k);
/*  564 */     Integer newValue = remappingFunction.apply(Integer.valueOf(k), 
/*  565 */         (pos >= 0) ? Integer.valueOf(this.value[pos]) : null);
/*  566 */     if (newValue == null) {
/*  567 */       if (pos >= 0)
/*  568 */         if (k == 0) {
/*  569 */           removeNullEntry();
/*      */         } else {
/*  571 */           removeEntry(pos);
/*      */         }  
/*  573 */       return this.defRetValue;
/*      */     } 
/*  575 */     int newVal = newValue.intValue();
/*  576 */     if (pos < 0) {
/*  577 */       insert(-pos - 1, k, newVal);
/*  578 */       return newVal;
/*      */     } 
/*  580 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int merge(int k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  586 */     Objects.requireNonNull(remappingFunction);
/*  587 */     int pos = find(k);
/*  588 */     if (pos < 0) {
/*  589 */       insert(-pos - 1, k, v);
/*  590 */       return v;
/*      */     } 
/*  592 */     Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
/*  593 */     if (newValue == null) {
/*  594 */       if (k == 0) {
/*  595 */         removeNullEntry();
/*      */       } else {
/*  597 */         removeEntry(pos);
/*  598 */       }  return this.defRetValue;
/*      */     } 
/*  600 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
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
/*  611 */     if (this.size == 0)
/*      */       return; 
/*  613 */     this.size = 0;
/*  614 */     this.containsNullKey = false;
/*  615 */     Arrays.fill(this.key, 0);
/*      */   }
/*      */   
/*      */   public int size() {
/*  619 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  623 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Int2IntMap.Entry, Map.Entry<Integer, Integer>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  635 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public int getIntKey() {
/*  641 */       return Int2IntOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public int getIntValue() {
/*  645 */       return Int2IntOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public int setValue(int v) {
/*  649 */       int oldValue = Int2IntOpenHashMap.this.value[this.index];
/*  650 */       Int2IntOpenHashMap.this.value[this.index] = v;
/*  651 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getKey() {
/*  661 */       return Integer.valueOf(Int2IntOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getValue() {
/*  671 */       return Integer.valueOf(Int2IntOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer setValue(Integer v) {
/*  681 */       return Integer.valueOf(setValue(v.intValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  686 */       if (!(o instanceof Map.Entry))
/*  687 */         return false; 
/*  688 */       Map.Entry<Integer, Integer> e = (Map.Entry<Integer, Integer>)o;
/*  689 */       return (Int2IntOpenHashMap.this.key[this.index] == ((Integer)e.getKey()).intValue() && Int2IntOpenHashMap.this.value[this.index] == ((Integer)e.getValue()).intValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  693 */       return Int2IntOpenHashMap.this.key[this.index] ^ Int2IntOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  697 */       return Int2IntOpenHashMap.this.key[this.index] + "=>" + Int2IntOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  707 */     int pos = Int2IntOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  714 */     int last = -1;
/*      */     
/*  716 */     int c = Int2IntOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  720 */     boolean mustReturnNullKey = Int2IntOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     IntArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  727 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  730 */       if (!hasNext())
/*  731 */         throw new NoSuchElementException(); 
/*  732 */       this.c--;
/*  733 */       if (this.mustReturnNullKey) {
/*  734 */         this.mustReturnNullKey = false;
/*  735 */         return this.last = Int2IntOpenHashMap.this.n;
/*      */       } 
/*  737 */       int[] key = Int2IntOpenHashMap.this.key;
/*      */       while (true) {
/*  739 */         if (--this.pos < 0) {
/*      */           
/*  741 */           this.last = Integer.MIN_VALUE;
/*  742 */           int k = this.wrapped.getInt(-this.pos - 1);
/*  743 */           int p = HashCommon.mix(k) & Int2IntOpenHashMap.this.mask;
/*  744 */           while (k != key[p])
/*  745 */             p = p + 1 & Int2IntOpenHashMap.this.mask; 
/*  746 */           return p;
/*      */         } 
/*  748 */         if (key[this.pos] != 0) {
/*  749 */           return this.last = this.pos;
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
/*  763 */       int[] key = Int2IntOpenHashMap.this.key; while (true) {
/*      */         int curr, last;
/*  765 */         pos = (last = pos) + 1 & Int2IntOpenHashMap.this.mask;
/*      */         while (true) {
/*  767 */           if ((curr = key[pos]) == 0) {
/*  768 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  771 */           int slot = HashCommon.mix(curr) & Int2IntOpenHashMap.this.mask;
/*  772 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  774 */           pos = pos + 1 & Int2IntOpenHashMap.this.mask;
/*      */         } 
/*  776 */         if (pos < last) {
/*  777 */           if (this.wrapped == null)
/*  778 */             this.wrapped = new IntArrayList(2); 
/*  779 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  781 */         key[last] = curr;
/*  782 */         Int2IntOpenHashMap.this.value[last] = Int2IntOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  786 */       if (this.last == -1)
/*  787 */         throw new IllegalStateException(); 
/*  788 */       if (this.last == Int2IntOpenHashMap.this.n) {
/*  789 */         Int2IntOpenHashMap.this.containsNullKey = false;
/*  790 */       } else if (this.pos >= 0) {
/*  791 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  794 */         Int2IntOpenHashMap.this.remove(this.wrapped.getInt(-this.pos - 1));
/*  795 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  798 */       Int2IntOpenHashMap.this.size--;
/*  799 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  804 */       int i = n;
/*  805 */       while (i-- != 0 && hasNext())
/*  806 */         nextEntry(); 
/*  807 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Int2IntMap.Entry> { private Int2IntOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Int2IntOpenHashMap.MapEntry next() {
/*  814 */       return this.entry = new Int2IntOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  818 */       super.remove();
/*  819 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Int2IntMap.Entry> { private FastEntryIterator() {
/*  823 */       this.entry = new Int2IntOpenHashMap.MapEntry();
/*      */     } private final Int2IntOpenHashMap.MapEntry entry;
/*      */     public Int2IntOpenHashMap.MapEntry next() {
/*  826 */       this.entry.index = nextEntry();
/*  827 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Int2IntMap.Entry> implements Int2IntMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Int2IntMap.Entry> iterator() {
/*  833 */       return new Int2IntOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Int2IntMap.Entry> fastIterator() {
/*  837 */       return new Int2IntOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  842 */       if (!(o instanceof Map.Entry))
/*  843 */         return false; 
/*  844 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  845 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/*  846 */         return false; 
/*  847 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  848 */         return false; 
/*  849 */       int k = ((Integer)e.getKey()).intValue();
/*  850 */       int v = ((Integer)e.getValue()).intValue();
/*  851 */       if (k == 0) {
/*  852 */         return (Int2IntOpenHashMap.this.containsNullKey && Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.n] == v);
/*      */       }
/*  854 */       int[] key = Int2IntOpenHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/*  857 */       if ((curr = key[pos = HashCommon.mix(k) & Int2IntOpenHashMap.this.mask]) == 0)
/*  858 */         return false; 
/*  859 */       if (k == curr) {
/*  860 */         return (Int2IntOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  863 */         if ((curr = key[pos = pos + 1 & Int2IntOpenHashMap.this.mask]) == 0)
/*  864 */           return false; 
/*  865 */         if (k == curr) {
/*  866 */           return (Int2IntOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  872 */       if (!(o instanceof Map.Entry))
/*  873 */         return false; 
/*  874 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  875 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/*  876 */         return false; 
/*  877 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  878 */         return false; 
/*  879 */       int k = ((Integer)e.getKey()).intValue();
/*  880 */       int v = ((Integer)e.getValue()).intValue();
/*  881 */       if (k == 0) {
/*  882 */         if (Int2IntOpenHashMap.this.containsNullKey && Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.n] == v) {
/*  883 */           Int2IntOpenHashMap.this.removeNullEntry();
/*  884 */           return true;
/*      */         } 
/*  886 */         return false;
/*      */       } 
/*      */       
/*  889 */       int[] key = Int2IntOpenHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/*  892 */       if ((curr = key[pos = HashCommon.mix(k) & Int2IntOpenHashMap.this.mask]) == 0)
/*  893 */         return false; 
/*  894 */       if (curr == k) {
/*  895 */         if (Int2IntOpenHashMap.this.value[pos] == v) {
/*  896 */           Int2IntOpenHashMap.this.removeEntry(pos);
/*  897 */           return true;
/*      */         } 
/*  899 */         return false;
/*      */       } 
/*      */       while (true) {
/*  902 */         if ((curr = key[pos = pos + 1 & Int2IntOpenHashMap.this.mask]) == 0)
/*  903 */           return false; 
/*  904 */         if (curr == k && 
/*  905 */           Int2IntOpenHashMap.this.value[pos] == v) {
/*  906 */           Int2IntOpenHashMap.this.removeEntry(pos);
/*  907 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  914 */       return Int2IntOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  918 */       Int2IntOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Int2IntMap.Entry> consumer) {
/*  923 */       if (Int2IntOpenHashMap.this.containsNullKey)
/*  924 */         consumer.accept(new AbstractInt2IntMap.BasicEntry(Int2IntOpenHashMap.this.key[Int2IntOpenHashMap.this.n], Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.n])); 
/*  925 */       for (int pos = Int2IntOpenHashMap.this.n; pos-- != 0;) {
/*  926 */         if (Int2IntOpenHashMap.this.key[pos] != 0)
/*  927 */           consumer.accept(new AbstractInt2IntMap.BasicEntry(Int2IntOpenHashMap.this.key[pos], Int2IntOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Int2IntMap.Entry> consumer) {
/*  932 */       AbstractInt2IntMap.BasicEntry entry = new AbstractInt2IntMap.BasicEntry();
/*  933 */       if (Int2IntOpenHashMap.this.containsNullKey) {
/*  934 */         entry.key = Int2IntOpenHashMap.this.key[Int2IntOpenHashMap.this.n];
/*  935 */         entry.value = Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.n];
/*  936 */         consumer.accept(entry);
/*      */       } 
/*  938 */       for (int pos = Int2IntOpenHashMap.this.n; pos-- != 0;) {
/*  939 */         if (Int2IntOpenHashMap.this.key[pos] != 0) {
/*  940 */           entry.key = Int2IntOpenHashMap.this.key[pos];
/*  941 */           entry.value = Int2IntOpenHashMap.this.value[pos];
/*  942 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Int2IntMap.FastEntrySet int2IntEntrySet() {
/*  948 */     if (this.entries == null)
/*  949 */       this.entries = new MapEntrySet(); 
/*  950 */     return this.entries;
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
/*      */     implements IntIterator
/*      */   {
/*      */     public int nextInt() {
/*  967 */       return Int2IntOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractIntSet { private KeySet() {}
/*      */     
/*      */     public IntIterator iterator() {
/*  973 */       return new Int2IntOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/*  978 */       if (Int2IntOpenHashMap.this.containsNullKey)
/*  979 */         consumer.accept(Int2IntOpenHashMap.this.key[Int2IntOpenHashMap.this.n]); 
/*  980 */       for (int pos = Int2IntOpenHashMap.this.n; pos-- != 0; ) {
/*  981 */         int k = Int2IntOpenHashMap.this.key[pos];
/*  982 */         if (k != 0)
/*  983 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  988 */       return Int2IntOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(int k) {
/*  992 */       return Int2IntOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(int k) {
/*  996 */       int oldSize = Int2IntOpenHashMap.this.size;
/*  997 */       Int2IntOpenHashMap.this.remove(k);
/*  998 */       return (Int2IntOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1002 */       Int2IntOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public IntSet keySet() {
/* 1007 */     if (this.keys == null)
/* 1008 */       this.keys = new KeySet(); 
/* 1009 */     return this.keys;
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
/*      */     implements IntIterator
/*      */   {
/*      */     public int nextInt() {
/* 1026 */       return Int2IntOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public IntCollection values() {
/* 1031 */     if (this.values == null)
/* 1032 */       this.values = new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1035 */             return new Int2IntOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1039 */             return Int2IntOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(int v) {
/* 1043 */             return Int2IntOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1047 */             Int2IntOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1052 */             if (Int2IntOpenHashMap.this.containsNullKey)
/* 1053 */               consumer.accept(Int2IntOpenHashMap.this.value[Int2IntOpenHashMap.this.n]); 
/* 1054 */             for (int pos = Int2IntOpenHashMap.this.n; pos-- != 0;) {
/* 1055 */               if (Int2IntOpenHashMap.this.key[pos] != 0)
/* 1056 */                 consumer.accept(Int2IntOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1059 */     return this.values;
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
/* 1076 */     return trim(this.size);
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
/* 1100 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1101 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1102 */       return true; 
/*      */     try {
/* 1104 */       rehash(l);
/* 1105 */     } catch (OutOfMemoryError cantDoIt) {
/* 1106 */       return false;
/*      */     } 
/* 1108 */     return true;
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
/* 1124 */     int[] key = this.key;
/* 1125 */     int[] value = this.value;
/* 1126 */     int mask = newN - 1;
/* 1127 */     int[] newKey = new int[newN + 1];
/* 1128 */     int[] newValue = new int[newN + 1];
/* 1129 */     int i = this.n;
/* 1130 */     for (int j = realSize(); j-- != 0; ) {
/* 1131 */       while (key[--i] == 0); int pos;
/* 1132 */       if (newKey[pos = HashCommon.mix(key[i]) & mask] != 0)
/* 1133 */         while (newKey[pos = pos + 1 & mask] != 0); 
/* 1134 */       newKey[pos] = key[i];
/* 1135 */       newValue[pos] = value[i];
/*      */     } 
/* 1137 */     newValue[newN] = value[this.n];
/* 1138 */     this.n = newN;
/* 1139 */     this.mask = mask;
/* 1140 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1141 */     this.key = newKey;
/* 1142 */     this.value = newValue;
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
/*      */   public Int2IntOpenHashMap clone() {
/*      */     Int2IntOpenHashMap c;
/*      */     try {
/* 1159 */       c = (Int2IntOpenHashMap)super.clone();
/* 1160 */     } catch (CloneNotSupportedException cantHappen) {
/* 1161 */       throw new InternalError();
/*      */     } 
/* 1163 */     c.keys = null;
/* 1164 */     c.values = null;
/* 1165 */     c.entries = null;
/* 1166 */     c.containsNullKey = this.containsNullKey;
/* 1167 */     c.key = (int[])this.key.clone();
/* 1168 */     c.value = (int[])this.value.clone();
/* 1169 */     return c;
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
/* 1182 */     int h = 0;
/* 1183 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1184 */       while (this.key[i] == 0)
/* 1185 */         i++; 
/* 1186 */       t = this.key[i];
/* 1187 */       t ^= this.value[i];
/* 1188 */       h += t;
/* 1189 */       i++;
/*      */     } 
/*      */     
/* 1192 */     if (this.containsNullKey)
/* 1193 */       h += this.value[this.n]; 
/* 1194 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1197 */     int[] key = this.key;
/* 1198 */     int[] value = this.value;
/* 1199 */     MapIterator i = new MapIterator();
/* 1200 */     s.defaultWriteObject();
/* 1201 */     for (int j = this.size; j-- != 0; ) {
/* 1202 */       int e = i.nextEntry();
/* 1203 */       s.writeInt(key[e]);
/* 1204 */       s.writeInt(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1209 */     s.defaultReadObject();
/* 1210 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1211 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1212 */     this.mask = this.n - 1;
/* 1213 */     int[] key = this.key = new int[this.n + 1];
/* 1214 */     int[] value = this.value = new int[this.n + 1];
/*      */ 
/*      */     
/* 1217 */     for (int i = this.size; i-- != 0; ) {
/* 1218 */       int pos, k = s.readInt();
/* 1219 */       int v = s.readInt();
/* 1220 */       if (k == 0) {
/* 1221 */         pos = this.n;
/* 1222 */         this.containsNullKey = true;
/*      */       } else {
/* 1224 */         pos = HashCommon.mix(k) & this.mask;
/* 1225 */         while (key[pos] != 0)
/* 1226 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1228 */       key[pos] = k;
/* 1229 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2IntOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */