/*      */ package it.unimi.dsi.fastutil.chars;
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
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.IntPredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Char2BooleanOpenHashMap
/*      */   extends AbstractChar2BooleanMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient char[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Char2BooleanMap.FastEntrySet entries;
/*      */   protected transient CharSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   
/*      */   public Char2BooleanOpenHashMap(int expected, float f) {
/*   97 */     if (f <= 0.0F || f > 1.0F)
/*   98 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*   99 */     if (expected < 0)
/*  100 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  101 */     this.f = f;
/*  102 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  103 */     this.mask = this.n - 1;
/*  104 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  105 */     this.key = new char[this.n + 1];
/*  106 */     this.value = new boolean[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2BooleanOpenHashMap(int expected) {
/*  115 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2BooleanOpenHashMap() {
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
/*      */   public Char2BooleanOpenHashMap(Map<? extends Character, ? extends Boolean> m, float f) {
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
/*      */   public Char2BooleanOpenHashMap(Map<? extends Character, ? extends Boolean> m) {
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
/*      */   public Char2BooleanOpenHashMap(Char2BooleanMap m, float f) {
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
/*      */   public Char2BooleanOpenHashMap(Char2BooleanMap m) {
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
/*      */   public Char2BooleanOpenHashMap(char[] k, boolean[] v, float f) {
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
/*      */   public Char2BooleanOpenHashMap(char[] k, boolean[] v) {
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
/*      */   public void putAll(Map<? extends Character, ? extends Boolean> m) {
/*  235 */     if (this.f <= 0.5D) {
/*  236 */       ensureCapacity(m.size());
/*      */     } else {
/*  238 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  240 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(char k) {
/*  244 */     if (k == '\000') {
/*  245 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  247 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  250 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  251 */       return -(pos + 1); 
/*  252 */     if (k == curr) {
/*  253 */       return pos;
/*      */     }
/*      */     while (true) {
/*  256 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  257 */         return -(pos + 1); 
/*  258 */       if (k == curr)
/*  259 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, char k, boolean v) {
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
/*      */   public boolean put(char k, boolean v) {
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
/*  294 */     char[] key = this.key; while (true) {
/*      */       char curr; int last;
/*  296 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  298 */         if ((curr = key[pos]) == '\000') {
/*  299 */           key[last] = Character.MIN_VALUE;
/*      */           return;
/*      */         } 
/*  302 */         int slot = HashCommon.mix(curr) & this.mask;
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
/*      */   public boolean remove(char k) {
/*  314 */     if (k == '\000') {
/*  315 */       if (this.containsNullKey)
/*  316 */         return removeNullEntry(); 
/*  317 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  320 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  323 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  324 */       return this.defRetValue; 
/*  325 */     if (k == curr)
/*  326 */       return removeEntry(pos); 
/*      */     while (true) {
/*  328 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  329 */         return this.defRetValue; 
/*  330 */       if (k == curr) {
/*  331 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean get(char k) {
/*  337 */     if (k == '\000') {
/*  338 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  340 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  343 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  344 */       return this.defRetValue; 
/*  345 */     if (k == curr) {
/*  346 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  349 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  350 */         return this.defRetValue; 
/*  351 */       if (k == curr) {
/*  352 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(char k) {
/*  358 */     if (k == '\000') {
/*  359 */       return this.containsNullKey;
/*      */     }
/*  361 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  364 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  365 */       return false; 
/*  366 */     if (k == curr) {
/*  367 */       return true;
/*      */     }
/*      */     while (true) {
/*  370 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  371 */         return false; 
/*  372 */       if (k == curr)
/*  373 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  378 */     boolean[] value = this.value;
/*  379 */     char[] key = this.key;
/*  380 */     if (this.containsNullKey && value[this.n] == v)
/*  381 */       return true; 
/*  382 */     for (int i = this.n; i-- != 0;) {
/*  383 */       if (key[i] != '\000' && value[i] == v)
/*  384 */         return true; 
/*  385 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(char k, boolean defaultValue) {
/*  391 */     if (k == '\000') {
/*  392 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  394 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  397 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  398 */       return defaultValue; 
/*  399 */     if (k == curr) {
/*  400 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  403 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  404 */         return defaultValue; 
/*  405 */       if (k == curr) {
/*  406 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean putIfAbsent(char k, boolean v) {
/*  412 */     int pos = find(k);
/*  413 */     if (pos >= 0)
/*  414 */       return this.value[pos]; 
/*  415 */     insert(-pos - 1, k, v);
/*  416 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(char k, boolean v) {
/*  422 */     if (k == '\000') {
/*  423 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  424 */         removeNullEntry();
/*  425 */         return true;
/*      */       } 
/*  427 */       return false;
/*      */     } 
/*      */     
/*  430 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  433 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  434 */       return false; 
/*  435 */     if (k == curr && v == this.value[pos]) {
/*  436 */       removeEntry(pos);
/*  437 */       return true;
/*      */     } 
/*      */     while (true) {
/*  440 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  441 */         return false; 
/*  442 */       if (k == curr && v == this.value[pos]) {
/*  443 */         removeEntry(pos);
/*  444 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(char k, boolean oldValue, boolean v) {
/*  451 */     int pos = find(k);
/*  452 */     if (pos < 0 || oldValue != this.value[pos])
/*  453 */       return false; 
/*  454 */     this.value[pos] = v;
/*  455 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(char k, boolean v) {
/*  460 */     int pos = find(k);
/*  461 */     if (pos < 0)
/*  462 */       return this.defRetValue; 
/*  463 */     boolean oldValue = this.value[pos];
/*  464 */     this.value[pos] = v;
/*  465 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(char k, IntPredicate mappingFunction) {
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
/*      */   public boolean computeIfAbsentNullable(char k, IntFunction<? extends Boolean> mappingFunction) {
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
/*      */   public boolean computeIfPresent(char k, BiFunction<? super Character, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  497 */     Objects.requireNonNull(remappingFunction);
/*  498 */     int pos = find(k);
/*  499 */     if (pos < 0)
/*  500 */       return this.defRetValue; 
/*  501 */     Boolean newValue = remappingFunction.apply(Character.valueOf(k), Boolean.valueOf(this.value[pos]));
/*  502 */     if (newValue == null) {
/*  503 */       if (k == '\000') {
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
/*      */   public boolean compute(char k, BiFunction<? super Character, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  515 */     Objects.requireNonNull(remappingFunction);
/*  516 */     int pos = find(k);
/*  517 */     Boolean newValue = remappingFunction.apply(Character.valueOf(k), 
/*  518 */         (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  519 */     if (newValue == null) {
/*  520 */       if (pos >= 0)
/*  521 */         if (k == '\000') {
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
/*      */   public boolean merge(char k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  539 */     Objects.requireNonNull(remappingFunction);
/*  540 */     int pos = find(k);
/*  541 */     if (pos < 0) {
/*  542 */       insert(-pos - 1, k, v);
/*  543 */       return v;
/*      */     } 
/*  545 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  546 */     if (newValue == null) {
/*  547 */       if (k == '\000') {
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
/*  568 */     Arrays.fill(this.key, false);
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
/*      */     implements Char2BooleanMap.Entry, Map.Entry<Character, Boolean>
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
/*      */     public char getCharKey() {
/*  594 */       return Char2BooleanOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public boolean getBooleanValue() {
/*  598 */       return Char2BooleanOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  602 */       boolean oldValue = Char2BooleanOpenHashMap.this.value[this.index];
/*  603 */       Char2BooleanOpenHashMap.this.value[this.index] = v;
/*  604 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getKey() {
/*  614 */       return Character.valueOf(Char2BooleanOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  624 */       return Boolean.valueOf(Char2BooleanOpenHashMap.this.value[this.index]);
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
/*  641 */       Map.Entry<Character, Boolean> e = (Map.Entry<Character, Boolean>)o;
/*  642 */       return (Char2BooleanOpenHashMap.this.key[this.index] == ((Character)e.getKey()).charValue() && Char2BooleanOpenHashMap.this.value[this.index] == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  646 */       return Char2BooleanOpenHashMap.this.key[this.index] ^ (Char2BooleanOpenHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  650 */       return Char2BooleanOpenHashMap.this.key[this.index] + "=>" + Char2BooleanOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  660 */     int pos = Char2BooleanOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  667 */     int last = -1;
/*      */     
/*  669 */     int c = Char2BooleanOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  673 */     boolean mustReturnNullKey = Char2BooleanOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     CharArrayList wrapped;
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
/*  688 */         return this.last = Char2BooleanOpenHashMap.this.n;
/*      */       } 
/*  690 */       char[] key = Char2BooleanOpenHashMap.this.key;
/*      */       while (true) {
/*  692 */         if (--this.pos < 0) {
/*      */           
/*  694 */           this.last = Integer.MIN_VALUE;
/*  695 */           char k = this.wrapped.getChar(-this.pos - 1);
/*  696 */           int p = HashCommon.mix(k) & Char2BooleanOpenHashMap.this.mask;
/*  697 */           while (k != key[p])
/*  698 */             p = p + 1 & Char2BooleanOpenHashMap.this.mask; 
/*  699 */           return p;
/*      */         } 
/*  701 */         if (key[this.pos] != '\000') {
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
/*  716 */       char[] key = Char2BooleanOpenHashMap.this.key; while (true) {
/*      */         char curr; int last;
/*  718 */         pos = (last = pos) + 1 & Char2BooleanOpenHashMap.this.mask;
/*      */         while (true) {
/*  720 */           if ((curr = key[pos]) == '\000') {
/*  721 */             key[last] = Character.MIN_VALUE;
/*      */             return;
/*      */           } 
/*  724 */           int slot = HashCommon.mix(curr) & Char2BooleanOpenHashMap.this.mask;
/*  725 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  727 */           pos = pos + 1 & Char2BooleanOpenHashMap.this.mask;
/*      */         } 
/*  729 */         if (pos < last) {
/*  730 */           if (this.wrapped == null)
/*  731 */             this.wrapped = new CharArrayList(2); 
/*  732 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  734 */         key[last] = curr;
/*  735 */         Char2BooleanOpenHashMap.this.value[last] = Char2BooleanOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  739 */       if (this.last == -1)
/*  740 */         throw new IllegalStateException(); 
/*  741 */       if (this.last == Char2BooleanOpenHashMap.this.n) {
/*  742 */         Char2BooleanOpenHashMap.this.containsNullKey = false;
/*  743 */       } else if (this.pos >= 0) {
/*  744 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  747 */         Char2BooleanOpenHashMap.this.remove(this.wrapped.getChar(-this.pos - 1));
/*  748 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  751 */       Char2BooleanOpenHashMap.this.size--;
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
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Char2BooleanMap.Entry> { private Char2BooleanOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Char2BooleanOpenHashMap.MapEntry next() {
/*  767 */       return this.entry = new Char2BooleanOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  771 */       super.remove();
/*  772 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Char2BooleanMap.Entry> { private FastEntryIterator() {
/*  776 */       this.entry = new Char2BooleanOpenHashMap.MapEntry();
/*      */     } private final Char2BooleanOpenHashMap.MapEntry entry;
/*      */     public Char2BooleanOpenHashMap.MapEntry next() {
/*  779 */       this.entry.index = nextEntry();
/*  780 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Char2BooleanMap.Entry> implements Char2BooleanMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Char2BooleanMap.Entry> iterator() {
/*  786 */       return new Char2BooleanOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Char2BooleanMap.Entry> fastIterator() {
/*  790 */       return new Char2BooleanOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  795 */       if (!(o instanceof Map.Entry))
/*  796 */         return false; 
/*  797 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  798 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/*  799 */         return false; 
/*  800 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  801 */         return false; 
/*  802 */       char k = ((Character)e.getKey()).charValue();
/*  803 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  804 */       if (k == '\000') {
/*  805 */         return (Char2BooleanOpenHashMap.this.containsNullKey && Char2BooleanOpenHashMap.this.value[Char2BooleanOpenHashMap.this.n] == v);
/*      */       }
/*  807 */       char[] key = Char2BooleanOpenHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/*  810 */       if ((curr = key[pos = HashCommon.mix(k) & Char2BooleanOpenHashMap.this.mask]) == '\000')
/*  811 */         return false; 
/*  812 */       if (k == curr) {
/*  813 */         return (Char2BooleanOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  816 */         if ((curr = key[pos = pos + 1 & Char2BooleanOpenHashMap.this.mask]) == '\000')
/*  817 */           return false; 
/*  818 */         if (k == curr) {
/*  819 */           return (Char2BooleanOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  825 */       if (!(o instanceof Map.Entry))
/*  826 */         return false; 
/*  827 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  828 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/*  829 */         return false; 
/*  830 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/*  831 */         return false; 
/*  832 */       char k = ((Character)e.getKey()).charValue();
/*  833 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  834 */       if (k == '\000') {
/*  835 */         if (Char2BooleanOpenHashMap.this.containsNullKey && Char2BooleanOpenHashMap.this.value[Char2BooleanOpenHashMap.this.n] == v) {
/*  836 */           Char2BooleanOpenHashMap.this.removeNullEntry();
/*  837 */           return true;
/*      */         } 
/*  839 */         return false;
/*      */       } 
/*      */       
/*  842 */       char[] key = Char2BooleanOpenHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/*  845 */       if ((curr = key[pos = HashCommon.mix(k) & Char2BooleanOpenHashMap.this.mask]) == '\000')
/*  846 */         return false; 
/*  847 */       if (curr == k) {
/*  848 */         if (Char2BooleanOpenHashMap.this.value[pos] == v) {
/*  849 */           Char2BooleanOpenHashMap.this.removeEntry(pos);
/*  850 */           return true;
/*      */         } 
/*  852 */         return false;
/*      */       } 
/*      */       while (true) {
/*  855 */         if ((curr = key[pos = pos + 1 & Char2BooleanOpenHashMap.this.mask]) == '\000')
/*  856 */           return false; 
/*  857 */         if (curr == k && 
/*  858 */           Char2BooleanOpenHashMap.this.value[pos] == v) {
/*  859 */           Char2BooleanOpenHashMap.this.removeEntry(pos);
/*  860 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  867 */       return Char2BooleanOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  871 */       Char2BooleanOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Char2BooleanMap.Entry> consumer) {
/*  876 */       if (Char2BooleanOpenHashMap.this.containsNullKey)
/*  877 */         consumer.accept(new AbstractChar2BooleanMap.BasicEntry(Char2BooleanOpenHashMap.this.key[Char2BooleanOpenHashMap.this.n], Char2BooleanOpenHashMap.this.value[Char2BooleanOpenHashMap.this.n])); 
/*  878 */       for (int pos = Char2BooleanOpenHashMap.this.n; pos-- != 0;) {
/*  879 */         if (Char2BooleanOpenHashMap.this.key[pos] != '\000')
/*  880 */           consumer.accept(new AbstractChar2BooleanMap.BasicEntry(Char2BooleanOpenHashMap.this.key[pos], Char2BooleanOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Char2BooleanMap.Entry> consumer) {
/*  885 */       AbstractChar2BooleanMap.BasicEntry entry = new AbstractChar2BooleanMap.BasicEntry();
/*  886 */       if (Char2BooleanOpenHashMap.this.containsNullKey) {
/*  887 */         entry.key = Char2BooleanOpenHashMap.this.key[Char2BooleanOpenHashMap.this.n];
/*  888 */         entry.value = Char2BooleanOpenHashMap.this.value[Char2BooleanOpenHashMap.this.n];
/*  889 */         consumer.accept(entry);
/*      */       } 
/*  891 */       for (int pos = Char2BooleanOpenHashMap.this.n; pos-- != 0;) {
/*  892 */         if (Char2BooleanOpenHashMap.this.key[pos] != '\000') {
/*  893 */           entry.key = Char2BooleanOpenHashMap.this.key[pos];
/*  894 */           entry.value = Char2BooleanOpenHashMap.this.value[pos];
/*  895 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Char2BooleanMap.FastEntrySet char2BooleanEntrySet() {
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
/*      */     implements CharIterator
/*      */   {
/*      */     public char nextChar() {
/*  920 */       return Char2BooleanOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractCharSet { private KeySet() {}
/*      */     
/*      */     public CharIterator iterator() {
/*  926 */       return new Char2BooleanOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/*  931 */       if (Char2BooleanOpenHashMap.this.containsNullKey)
/*  932 */         consumer.accept(Char2BooleanOpenHashMap.this.key[Char2BooleanOpenHashMap.this.n]); 
/*  933 */       for (int pos = Char2BooleanOpenHashMap.this.n; pos-- != 0; ) {
/*  934 */         char k = Char2BooleanOpenHashMap.this.key[pos];
/*  935 */         if (k != '\000')
/*  936 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  941 */       return Char2BooleanOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(char k) {
/*  945 */       return Char2BooleanOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(char k) {
/*  949 */       int oldSize = Char2BooleanOpenHashMap.this.size;
/*  950 */       Char2BooleanOpenHashMap.this.remove(k);
/*  951 */       return (Char2BooleanOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  955 */       Char2BooleanOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public CharSet keySet() {
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
/*  979 */       return Char2BooleanOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public BooleanCollection values() {
/*  984 */     if (this.values == null)
/*  985 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/*  988 */             return new Char2BooleanOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/*  992 */             return Char2BooleanOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(boolean v) {
/*  996 */             return Char2BooleanOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1000 */             Char2BooleanOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(BooleanConsumer consumer)
/*      */           {
/* 1005 */             if (Char2BooleanOpenHashMap.this.containsNullKey)
/* 1006 */               consumer.accept(Char2BooleanOpenHashMap.this.value[Char2BooleanOpenHashMap.this.n]); 
/* 1007 */             for (int pos = Char2BooleanOpenHashMap.this.n; pos-- != 0;) {
/* 1008 */               if (Char2BooleanOpenHashMap.this.key[pos] != '\000')
/* 1009 */                 consumer.accept(Char2BooleanOpenHashMap.this.value[pos]); 
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
/* 1077 */     char[] key = this.key;
/* 1078 */     boolean[] value = this.value;
/* 1079 */     int mask = newN - 1;
/* 1080 */     char[] newKey = new char[newN + 1];
/* 1081 */     boolean[] newValue = new boolean[newN + 1];
/* 1082 */     int i = this.n;
/* 1083 */     for (int j = realSize(); j-- != 0; ) {
/* 1084 */       while (key[--i] == '\000'); int pos;
/* 1085 */       if (newKey[pos = HashCommon.mix(key[i]) & mask] != '\000')
/* 1086 */         while (newKey[pos = pos + 1 & mask] != '\000'); 
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
/*      */   public Char2BooleanOpenHashMap clone() {
/*      */     Char2BooleanOpenHashMap c;
/*      */     try {
/* 1112 */       c = (Char2BooleanOpenHashMap)super.clone();
/* 1113 */     } catch (CloneNotSupportedException cantHappen) {
/* 1114 */       throw new InternalError();
/*      */     } 
/* 1116 */     c.keys = null;
/* 1117 */     c.values = null;
/* 1118 */     c.entries = null;
/* 1119 */     c.containsNullKey = this.containsNullKey;
/* 1120 */     c.key = (char[])this.key.clone();
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
/* 1137 */       while (this.key[i] == '\000')
/* 1138 */         i++; 
/* 1139 */       t = this.key[i];
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
/* 1150 */     char[] key = this.key;
/* 1151 */     boolean[] value = this.value;
/* 1152 */     MapIterator i = new MapIterator();
/* 1153 */     s.defaultWriteObject();
/* 1154 */     for (int j = this.size; j-- != 0; ) {
/* 1155 */       int e = i.nextEntry();
/* 1156 */       s.writeChar(key[e]);
/* 1157 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1162 */     s.defaultReadObject();
/* 1163 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1164 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1165 */     this.mask = this.n - 1;
/* 1166 */     char[] key = this.key = new char[this.n + 1];
/* 1167 */     boolean[] value = this.value = new boolean[this.n + 1];
/*      */ 
/*      */     
/* 1170 */     for (int i = this.size; i-- != 0; ) {
/* 1171 */       int pos; char k = s.readChar();
/* 1172 */       boolean v = s.readBoolean();
/* 1173 */       if (k == '\000') {
/* 1174 */         pos = this.n;
/* 1175 */         this.containsNullKey = true;
/*      */       } else {
/* 1177 */         pos = HashCommon.mix(k) & this.mask;
/* 1178 */         while (key[pos] != '\000')
/* 1179 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1181 */       key[pos] = k;
/* 1182 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2BooleanOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */