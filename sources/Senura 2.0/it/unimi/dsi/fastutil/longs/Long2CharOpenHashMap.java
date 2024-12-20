/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
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
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongFunction;
/*      */ import java.util.function.LongToIntFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Long2CharOpenHashMap
/*      */   extends AbstractLong2CharMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient char[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Long2CharMap.FastEntrySet entries;
/*      */   protected transient LongSet keys;
/*      */   protected transient CharCollection values;
/*      */   
/*      */   public Long2CharOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f > 1.0F)
/*   97 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*   98 */     if (expected < 0)
/*   99 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  100 */     this.f = f;
/*  101 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  102 */     this.mask = this.n - 1;
/*  103 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  104 */     this.key = new long[this.n + 1];
/*  105 */     this.value = new char[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2CharOpenHashMap(int expected) {
/*  114 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2CharOpenHashMap() {
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
/*      */   public Long2CharOpenHashMap(Map<? extends Long, ? extends Character> m, float f) {
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
/*      */   public Long2CharOpenHashMap(Map<? extends Long, ? extends Character> m) {
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
/*      */   public Long2CharOpenHashMap(Long2CharMap m, float f) {
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
/*      */   public Long2CharOpenHashMap(Long2CharMap m) {
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
/*      */   public Long2CharOpenHashMap(long[] k, char[] v, float f) {
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
/*      */   public Long2CharOpenHashMap(long[] k, char[] v) {
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
/*      */   private char removeEntry(int pos) {
/*  217 */     char oldValue = this.value[pos];
/*  218 */     this.size--;
/*  219 */     shiftKeys(pos);
/*  220 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  221 */       rehash(this.n / 2); 
/*  222 */     return oldValue;
/*      */   }
/*      */   private char removeNullEntry() {
/*  225 */     this.containsNullKey = false;
/*  226 */     char oldValue = this.value[this.n];
/*  227 */     this.size--;
/*  228 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  229 */       rehash(this.n / 2); 
/*  230 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Long, ? extends Character> m) {
/*  234 */     if (this.f <= 0.5D) {
/*  235 */       ensureCapacity(m.size());
/*      */     } else {
/*  237 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  239 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(long k) {
/*  243 */     if (k == 0L) {
/*  244 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  246 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  249 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  250 */       return -(pos + 1); 
/*  251 */     if (k == curr) {
/*  252 */       return pos;
/*      */     }
/*      */     while (true) {
/*  255 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  256 */         return -(pos + 1); 
/*  257 */       if (k == curr)
/*  258 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, long k, char v) {
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
/*      */   public char put(long k, char v) {
/*  273 */     int pos = find(k);
/*  274 */     if (pos < 0) {
/*  275 */       insert(-pos - 1, k, v);
/*  276 */       return this.defRetValue;
/*      */     } 
/*  278 */     char oldValue = this.value[pos];
/*  279 */     this.value[pos] = v;
/*  280 */     return oldValue;
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
/*  293 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  295 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  297 */         if ((curr = key[pos]) == 0L) {
/*  298 */           key[last] = 0L;
/*      */           return;
/*      */         } 
/*  301 */         int slot = (int)HashCommon.mix(curr) & this.mask;
/*  302 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  304 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  306 */       key[last] = curr;
/*  307 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char remove(long k) {
/*  313 */     if (k == 0L) {
/*  314 */       if (this.containsNullKey)
/*  315 */         return removeNullEntry(); 
/*  316 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  319 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  322 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  323 */       return this.defRetValue; 
/*  324 */     if (k == curr)
/*  325 */       return removeEntry(pos); 
/*      */     while (true) {
/*  327 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  328 */         return this.defRetValue; 
/*  329 */       if (k == curr) {
/*  330 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char get(long k) {
/*  336 */     if (k == 0L) {
/*  337 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  339 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  342 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  343 */       return this.defRetValue; 
/*  344 */     if (k == curr) {
/*  345 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  348 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  349 */         return this.defRetValue; 
/*  350 */       if (k == curr) {
/*  351 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(long k) {
/*  357 */     if (k == 0L) {
/*  358 */       return this.containsNullKey;
/*      */     }
/*  360 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  363 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  364 */       return false; 
/*  365 */     if (k == curr) {
/*  366 */       return true;
/*      */     }
/*      */     while (true) {
/*  369 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  370 */         return false; 
/*  371 */       if (k == curr)
/*  372 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  377 */     char[] value = this.value;
/*  378 */     long[] key = this.key;
/*  379 */     if (this.containsNullKey && value[this.n] == v)
/*  380 */       return true; 
/*  381 */     for (int i = this.n; i-- != 0;) {
/*  382 */       if (key[i] != 0L && value[i] == v)
/*  383 */         return true; 
/*  384 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(long k, char defaultValue) {
/*  390 */     if (k == 0L) {
/*  391 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  393 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  396 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  397 */       return defaultValue; 
/*  398 */     if (k == curr) {
/*  399 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  402 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  403 */         return defaultValue; 
/*  404 */       if (k == curr) {
/*  405 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char putIfAbsent(long k, char v) {
/*  411 */     int pos = find(k);
/*  412 */     if (pos >= 0)
/*  413 */       return this.value[pos]; 
/*  414 */     insert(-pos - 1, k, v);
/*  415 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, char v) {
/*  421 */     if (k == 0L) {
/*  422 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  423 */         removeNullEntry();
/*  424 */         return true;
/*      */       } 
/*  426 */       return false;
/*      */     } 
/*      */     
/*  429 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  432 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  433 */       return false; 
/*  434 */     if (k == curr && v == this.value[pos]) {
/*  435 */       removeEntry(pos);
/*  436 */       return true;
/*      */     } 
/*      */     while (true) {
/*  439 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  440 */         return false; 
/*  441 */       if (k == curr && v == this.value[pos]) {
/*  442 */         removeEntry(pos);
/*  443 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(long k, char oldValue, char v) {
/*  450 */     int pos = find(k);
/*  451 */     if (pos < 0 || oldValue != this.value[pos])
/*  452 */       return false; 
/*  453 */     this.value[pos] = v;
/*  454 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public char replace(long k, char v) {
/*  459 */     int pos = find(k);
/*  460 */     if (pos < 0)
/*  461 */       return this.defRetValue; 
/*  462 */     char oldValue = this.value[pos];
/*  463 */     this.value[pos] = v;
/*  464 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char computeIfAbsent(long k, LongToIntFunction mappingFunction) {
/*  469 */     Objects.requireNonNull(mappingFunction);
/*  470 */     int pos = find(k);
/*  471 */     if (pos >= 0)
/*  472 */       return this.value[pos]; 
/*  473 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
/*  474 */     insert(-pos - 1, k, newValue);
/*  475 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfAbsentNullable(long k, LongFunction<? extends Character> mappingFunction) {
/*  481 */     Objects.requireNonNull(mappingFunction);
/*  482 */     int pos = find(k);
/*  483 */     if (pos >= 0)
/*  484 */       return this.value[pos]; 
/*  485 */     Character newValue = mappingFunction.apply(k);
/*  486 */     if (newValue == null)
/*  487 */       return this.defRetValue; 
/*  488 */     char v = newValue.charValue();
/*  489 */     insert(-pos - 1, k, v);
/*  490 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfPresent(long k, BiFunction<? super Long, ? super Character, ? extends Character> remappingFunction) {
/*  496 */     Objects.requireNonNull(remappingFunction);
/*  497 */     int pos = find(k);
/*  498 */     if (pos < 0)
/*  499 */       return this.defRetValue; 
/*  500 */     Character newValue = remappingFunction.apply(Long.valueOf(k), Character.valueOf(this.value[pos]));
/*  501 */     if (newValue == null) {
/*  502 */       if (k == 0L) {
/*  503 */         removeNullEntry();
/*      */       } else {
/*  505 */         removeEntry(pos);
/*  506 */       }  return this.defRetValue;
/*      */     } 
/*  508 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char compute(long k, BiFunction<? super Long, ? super Character, ? extends Character> remappingFunction) {
/*  514 */     Objects.requireNonNull(remappingFunction);
/*  515 */     int pos = find(k);
/*  516 */     Character newValue = remappingFunction.apply(Long.valueOf(k), 
/*  517 */         (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  518 */     if (newValue == null) {
/*  519 */       if (pos >= 0)
/*  520 */         if (k == 0L) {
/*  521 */           removeNullEntry();
/*      */         } else {
/*  523 */           removeEntry(pos);
/*      */         }  
/*  525 */       return this.defRetValue;
/*      */     } 
/*  527 */     char newVal = newValue.charValue();
/*  528 */     if (pos < 0) {
/*  529 */       insert(-pos - 1, k, newVal);
/*  530 */       return newVal;
/*      */     } 
/*  532 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char merge(long k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  538 */     Objects.requireNonNull(remappingFunction);
/*  539 */     int pos = find(k);
/*  540 */     if (pos < 0) {
/*  541 */       insert(-pos - 1, k, v);
/*  542 */       return v;
/*      */     } 
/*  544 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  545 */     if (newValue == null) {
/*  546 */       if (k == 0L) {
/*  547 */         removeNullEntry();
/*      */       } else {
/*  549 */         removeEntry(pos);
/*  550 */       }  return this.defRetValue;
/*      */     } 
/*  552 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
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
/*  563 */     if (this.size == 0)
/*      */       return; 
/*  565 */     this.size = 0;
/*  566 */     this.containsNullKey = false;
/*  567 */     Arrays.fill(this.key, 0L);
/*      */   }
/*      */   
/*      */   public int size() {
/*  571 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  575 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Long2CharMap.Entry, Map.Entry<Long, Character>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  587 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public long getLongKey() {
/*  593 */       return Long2CharOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public char getCharValue() {
/*  597 */       return Long2CharOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public char setValue(char v) {
/*  601 */       char oldValue = Long2CharOpenHashMap.this.value[this.index];
/*  602 */       Long2CharOpenHashMap.this.value[this.index] = v;
/*  603 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getKey() {
/*  613 */       return Long.valueOf(Long2CharOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getValue() {
/*  623 */       return Character.valueOf(Long2CharOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character setValue(Character v) {
/*  633 */       return Character.valueOf(setValue(v.charValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  638 */       if (!(o instanceof Map.Entry))
/*  639 */         return false; 
/*  640 */       Map.Entry<Long, Character> e = (Map.Entry<Long, Character>)o;
/*  641 */       return (Long2CharOpenHashMap.this.key[this.index] == ((Long)e.getKey()).longValue() && Long2CharOpenHashMap.this.value[this.index] == ((Character)e.getValue()).charValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  645 */       return HashCommon.long2int(Long2CharOpenHashMap.this.key[this.index]) ^ Long2CharOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  649 */       return Long2CharOpenHashMap.this.key[this.index] + "=>" + Long2CharOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  659 */     int pos = Long2CharOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  666 */     int last = -1;
/*      */     
/*  668 */     int c = Long2CharOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  672 */     boolean mustReturnNullKey = Long2CharOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     LongArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  679 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  682 */       if (!hasNext())
/*  683 */         throw new NoSuchElementException(); 
/*  684 */       this.c--;
/*  685 */       if (this.mustReturnNullKey) {
/*  686 */         this.mustReturnNullKey = false;
/*  687 */         return this.last = Long2CharOpenHashMap.this.n;
/*      */       } 
/*  689 */       long[] key = Long2CharOpenHashMap.this.key;
/*      */       while (true) {
/*  691 */         if (--this.pos < 0) {
/*      */           
/*  693 */           this.last = Integer.MIN_VALUE;
/*  694 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  695 */           int p = (int)HashCommon.mix(k) & Long2CharOpenHashMap.this.mask;
/*  696 */           while (k != key[p])
/*  697 */             p = p + 1 & Long2CharOpenHashMap.this.mask; 
/*  698 */           return p;
/*      */         } 
/*  700 */         if (key[this.pos] != 0L) {
/*  701 */           return this.last = this.pos;
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
/*  715 */       long[] key = Long2CharOpenHashMap.this.key; while (true) {
/*      */         long curr; int last;
/*  717 */         pos = (last = pos) + 1 & Long2CharOpenHashMap.this.mask;
/*      */         while (true) {
/*  719 */           if ((curr = key[pos]) == 0L) {
/*  720 */             key[last] = 0L;
/*      */             return;
/*      */           } 
/*  723 */           int slot = (int)HashCommon.mix(curr) & Long2CharOpenHashMap.this.mask;
/*  724 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  726 */           pos = pos + 1 & Long2CharOpenHashMap.this.mask;
/*      */         } 
/*  728 */         if (pos < last) {
/*  729 */           if (this.wrapped == null)
/*  730 */             this.wrapped = new LongArrayList(2); 
/*  731 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  733 */         key[last] = curr;
/*  734 */         Long2CharOpenHashMap.this.value[last] = Long2CharOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  738 */       if (this.last == -1)
/*  739 */         throw new IllegalStateException(); 
/*  740 */       if (this.last == Long2CharOpenHashMap.this.n) {
/*  741 */         Long2CharOpenHashMap.this.containsNullKey = false;
/*  742 */       } else if (this.pos >= 0) {
/*  743 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  746 */         Long2CharOpenHashMap.this.remove(this.wrapped.getLong(-this.pos - 1));
/*  747 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  750 */       Long2CharOpenHashMap.this.size--;
/*  751 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  756 */       int i = n;
/*  757 */       while (i-- != 0 && hasNext())
/*  758 */         nextEntry(); 
/*  759 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Long2CharMap.Entry> { private Long2CharOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Long2CharOpenHashMap.MapEntry next() {
/*  766 */       return this.entry = new Long2CharOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  770 */       super.remove();
/*  771 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Long2CharMap.Entry> { private FastEntryIterator() {
/*  775 */       this.entry = new Long2CharOpenHashMap.MapEntry();
/*      */     } private final Long2CharOpenHashMap.MapEntry entry;
/*      */     public Long2CharOpenHashMap.MapEntry next() {
/*  778 */       this.entry.index = nextEntry();
/*  779 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Long2CharMap.Entry> implements Long2CharMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Long2CharMap.Entry> iterator() {
/*  785 */       return new Long2CharOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Long2CharMap.Entry> fastIterator() {
/*  789 */       return new Long2CharOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  794 */       if (!(o instanceof Map.Entry))
/*  795 */         return false; 
/*  796 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  797 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  798 */         return false; 
/*  799 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  800 */         return false; 
/*  801 */       long k = ((Long)e.getKey()).longValue();
/*  802 */       char v = ((Character)e.getValue()).charValue();
/*  803 */       if (k == 0L) {
/*  804 */         return (Long2CharOpenHashMap.this.containsNullKey && Long2CharOpenHashMap.this.value[Long2CharOpenHashMap.this.n] == v);
/*      */       }
/*  806 */       long[] key = Long2CharOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  809 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2CharOpenHashMap.this.mask]) == 0L)
/*  810 */         return false; 
/*  811 */       if (k == curr) {
/*  812 */         return (Long2CharOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  815 */         if ((curr = key[pos = pos + 1 & Long2CharOpenHashMap.this.mask]) == 0L)
/*  816 */           return false; 
/*  817 */         if (k == curr) {
/*  818 */           return (Long2CharOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  824 */       if (!(o instanceof Map.Entry))
/*  825 */         return false; 
/*  826 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  827 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/*  828 */         return false; 
/*  829 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/*  830 */         return false; 
/*  831 */       long k = ((Long)e.getKey()).longValue();
/*  832 */       char v = ((Character)e.getValue()).charValue();
/*  833 */       if (k == 0L) {
/*  834 */         if (Long2CharOpenHashMap.this.containsNullKey && Long2CharOpenHashMap.this.value[Long2CharOpenHashMap.this.n] == v) {
/*  835 */           Long2CharOpenHashMap.this.removeNullEntry();
/*  836 */           return true;
/*      */         } 
/*  838 */         return false;
/*      */       } 
/*      */       
/*  841 */       long[] key = Long2CharOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/*  844 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2CharOpenHashMap.this.mask]) == 0L)
/*  845 */         return false; 
/*  846 */       if (curr == k) {
/*  847 */         if (Long2CharOpenHashMap.this.value[pos] == v) {
/*  848 */           Long2CharOpenHashMap.this.removeEntry(pos);
/*  849 */           return true;
/*      */         } 
/*  851 */         return false;
/*      */       } 
/*      */       while (true) {
/*  854 */         if ((curr = key[pos = pos + 1 & Long2CharOpenHashMap.this.mask]) == 0L)
/*  855 */           return false; 
/*  856 */         if (curr == k && 
/*  857 */           Long2CharOpenHashMap.this.value[pos] == v) {
/*  858 */           Long2CharOpenHashMap.this.removeEntry(pos);
/*  859 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  866 */       return Long2CharOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  870 */       Long2CharOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2CharMap.Entry> consumer) {
/*  875 */       if (Long2CharOpenHashMap.this.containsNullKey)
/*  876 */         consumer.accept(new AbstractLong2CharMap.BasicEntry(Long2CharOpenHashMap.this.key[Long2CharOpenHashMap.this.n], Long2CharOpenHashMap.this.value[Long2CharOpenHashMap.this.n])); 
/*  877 */       for (int pos = Long2CharOpenHashMap.this.n; pos-- != 0;) {
/*  878 */         if (Long2CharOpenHashMap.this.key[pos] != 0L)
/*  879 */           consumer.accept(new AbstractLong2CharMap.BasicEntry(Long2CharOpenHashMap.this.key[pos], Long2CharOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2CharMap.Entry> consumer) {
/*  884 */       AbstractLong2CharMap.BasicEntry entry = new AbstractLong2CharMap.BasicEntry();
/*  885 */       if (Long2CharOpenHashMap.this.containsNullKey) {
/*  886 */         entry.key = Long2CharOpenHashMap.this.key[Long2CharOpenHashMap.this.n];
/*  887 */         entry.value = Long2CharOpenHashMap.this.value[Long2CharOpenHashMap.this.n];
/*  888 */         consumer.accept(entry);
/*      */       } 
/*  890 */       for (int pos = Long2CharOpenHashMap.this.n; pos-- != 0;) {
/*  891 */         if (Long2CharOpenHashMap.this.key[pos] != 0L) {
/*  892 */           entry.key = Long2CharOpenHashMap.this.key[pos];
/*  893 */           entry.value = Long2CharOpenHashMap.this.value[pos];
/*  894 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Long2CharMap.FastEntrySet long2CharEntrySet() {
/*  900 */     if (this.entries == null)
/*  901 */       this.entries = new MapEntrySet(); 
/*  902 */     return this.entries;
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
/*  919 */       return Long2CharOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSet { private KeySet() {}
/*      */     
/*      */     public LongIterator iterator() {
/*  925 */       return new Long2CharOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/*  930 */       if (Long2CharOpenHashMap.this.containsNullKey)
/*  931 */         consumer.accept(Long2CharOpenHashMap.this.key[Long2CharOpenHashMap.this.n]); 
/*  932 */       for (int pos = Long2CharOpenHashMap.this.n; pos-- != 0; ) {
/*  933 */         long k = Long2CharOpenHashMap.this.key[pos];
/*  934 */         if (k != 0L)
/*  935 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  940 */       return Long2CharOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(long k) {
/*  944 */       return Long2CharOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(long k) {
/*  948 */       int oldSize = Long2CharOpenHashMap.this.size;
/*  949 */       Long2CharOpenHashMap.this.remove(k);
/*  950 */       return (Long2CharOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  954 */       Long2CharOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public LongSet keySet() {
/*  959 */     if (this.keys == null)
/*  960 */       this.keys = new KeySet(); 
/*  961 */     return this.keys;
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
/*      */     implements CharIterator
/*      */   {
/*      */     public char nextChar() {
/*  978 */       return Long2CharOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public CharCollection values() {
/*  983 */     if (this.values == null)
/*  984 */       this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/*  987 */             return new Long2CharOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/*  991 */             return Long2CharOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(char v) {
/*  995 */             return Long2CharOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/*  999 */             Long2CharOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1004 */             if (Long2CharOpenHashMap.this.containsNullKey)
/* 1005 */               consumer.accept(Long2CharOpenHashMap.this.value[Long2CharOpenHashMap.this.n]); 
/* 1006 */             for (int pos = Long2CharOpenHashMap.this.n; pos-- != 0;) {
/* 1007 */               if (Long2CharOpenHashMap.this.key[pos] != 0L)
/* 1008 */                 consumer.accept(Long2CharOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1011 */     return this.values;
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
/* 1028 */     return trim(this.size);
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
/* 1052 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1053 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1054 */       return true; 
/*      */     try {
/* 1056 */       rehash(l);
/* 1057 */     } catch (OutOfMemoryError cantDoIt) {
/* 1058 */       return false;
/*      */     } 
/* 1060 */     return true;
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
/* 1076 */     long[] key = this.key;
/* 1077 */     char[] value = this.value;
/* 1078 */     int mask = newN - 1;
/* 1079 */     long[] newKey = new long[newN + 1];
/* 1080 */     char[] newValue = new char[newN + 1];
/* 1081 */     int i = this.n;
/* 1082 */     for (int j = realSize(); j-- != 0; ) {
/* 1083 */       while (key[--i] == 0L); int pos;
/* 1084 */       if (newKey[pos = (int)HashCommon.mix(key[i]) & mask] != 0L)
/* 1085 */         while (newKey[pos = pos + 1 & mask] != 0L); 
/* 1086 */       newKey[pos] = key[i];
/* 1087 */       newValue[pos] = value[i];
/*      */     } 
/* 1089 */     newValue[newN] = value[this.n];
/* 1090 */     this.n = newN;
/* 1091 */     this.mask = mask;
/* 1092 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1093 */     this.key = newKey;
/* 1094 */     this.value = newValue;
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
/*      */   public Long2CharOpenHashMap clone() {
/*      */     Long2CharOpenHashMap c;
/*      */     try {
/* 1111 */       c = (Long2CharOpenHashMap)super.clone();
/* 1112 */     } catch (CloneNotSupportedException cantHappen) {
/* 1113 */       throw new InternalError();
/*      */     } 
/* 1115 */     c.keys = null;
/* 1116 */     c.values = null;
/* 1117 */     c.entries = null;
/* 1118 */     c.containsNullKey = this.containsNullKey;
/* 1119 */     c.key = (long[])this.key.clone();
/* 1120 */     c.value = (char[])this.value.clone();
/* 1121 */     return c;
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
/* 1134 */     int h = 0;
/* 1135 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1136 */       while (this.key[i] == 0L)
/* 1137 */         i++; 
/* 1138 */       t = HashCommon.long2int(this.key[i]);
/* 1139 */       t ^= this.value[i];
/* 1140 */       h += t;
/* 1141 */       i++;
/*      */     } 
/*      */     
/* 1144 */     if (this.containsNullKey)
/* 1145 */       h += this.value[this.n]; 
/* 1146 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1149 */     long[] key = this.key;
/* 1150 */     char[] value = this.value;
/* 1151 */     MapIterator i = new MapIterator();
/* 1152 */     s.defaultWriteObject();
/* 1153 */     for (int j = this.size; j-- != 0; ) {
/* 1154 */       int e = i.nextEntry();
/* 1155 */       s.writeLong(key[e]);
/* 1156 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1161 */     s.defaultReadObject();
/* 1162 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1163 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1164 */     this.mask = this.n - 1;
/* 1165 */     long[] key = this.key = new long[this.n + 1];
/* 1166 */     char[] value = this.value = new char[this.n + 1];
/*      */ 
/*      */     
/* 1169 */     for (int i = this.size; i-- != 0; ) {
/* 1170 */       int pos; long k = s.readLong();
/* 1171 */       char v = s.readChar();
/* 1172 */       if (k == 0L) {
/* 1173 */         pos = this.n;
/* 1174 */         this.containsNullKey = true;
/*      */       } else {
/* 1176 */         pos = (int)HashCommon.mix(k) & this.mask;
/* 1177 */         while (key[pos] != 0L)
/* 1178 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1180 */       key[pos] = k;
/* 1181 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2CharOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */