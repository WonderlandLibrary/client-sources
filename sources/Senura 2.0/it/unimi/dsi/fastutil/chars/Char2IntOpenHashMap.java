/*      */ package it.unimi.dsi.fastutil.chars;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
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
/*      */ public class Char2IntOpenHashMap
/*      */   extends AbstractChar2IntMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient char[] key;
/*      */   protected transient int[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Char2IntMap.FastEntrySet entries;
/*      */   protected transient CharSet keys;
/*      */   protected transient IntCollection values;
/*      */   
/*      */   public Char2IntOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f > 1.0F)
/*   97 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*   98 */     if (expected < 0)
/*   99 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  100 */     this.f = f;
/*  101 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  102 */     this.mask = this.n - 1;
/*  103 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  104 */     this.key = new char[this.n + 1];
/*  105 */     this.value = new int[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2IntOpenHashMap(int expected) {
/*  114 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2IntOpenHashMap() {
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
/*      */   public Char2IntOpenHashMap(Map<? extends Character, ? extends Integer> m, float f) {
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
/*      */   public Char2IntOpenHashMap(Map<? extends Character, ? extends Integer> m) {
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
/*      */   public Char2IntOpenHashMap(Char2IntMap m, float f) {
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
/*      */   public Char2IntOpenHashMap(Char2IntMap m) {
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
/*      */   public Char2IntOpenHashMap(char[] k, int[] v, float f) {
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
/*      */   public Char2IntOpenHashMap(char[] k, int[] v) {
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
/*      */   public void putAll(Map<? extends Character, ? extends Integer> m) {
/*  234 */     if (this.f <= 0.5D) {
/*  235 */       ensureCapacity(m.size());
/*      */     } else {
/*  237 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  239 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(char k) {
/*  243 */     if (k == '\000') {
/*  244 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  246 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  249 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  250 */       return -(pos + 1); 
/*  251 */     if (k == curr) {
/*  252 */       return pos;
/*      */     }
/*      */     while (true) {
/*  255 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  256 */         return -(pos + 1); 
/*  257 */       if (k == curr)
/*  258 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, char k, int v) {
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
/*      */   public int put(char k, int v) {
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
/*      */   public int addTo(char k, int incr) {
/*      */     int pos;
/*  305 */     if (k == '\000') {
/*  306 */       if (this.containsNullKey)
/*  307 */         return addToValue(this.n, incr); 
/*  308 */       pos = this.n;
/*  309 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  312 */       char[] key = this.key;
/*      */       char curr;
/*  314 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != '\000') {
/*  315 */         if (curr == k)
/*  316 */           return addToValue(pos, incr); 
/*  317 */         while ((curr = key[pos = pos + 1 & this.mask]) != '\000') {
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
/*  341 */     char[] key = this.key; while (true) {
/*      */       char curr; int last;
/*  343 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  345 */         if ((curr = key[pos]) == '\000') {
/*  346 */           key[last] = Character.MIN_VALUE;
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
/*      */   public int remove(char k) {
/*  361 */     if (k == '\000') {
/*  362 */       if (this.containsNullKey)
/*  363 */         return removeNullEntry(); 
/*  364 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  367 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  370 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  371 */       return this.defRetValue; 
/*  372 */     if (k == curr)
/*  373 */       return removeEntry(pos); 
/*      */     while (true) {
/*  375 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  376 */         return this.defRetValue; 
/*  377 */       if (k == curr) {
/*  378 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int get(char k) {
/*  384 */     if (k == '\000') {
/*  385 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  387 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  390 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  391 */       return this.defRetValue; 
/*  392 */     if (k == curr) {
/*  393 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  396 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  397 */         return this.defRetValue; 
/*  398 */       if (k == curr) {
/*  399 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(char k) {
/*  405 */     if (k == '\000') {
/*  406 */       return this.containsNullKey;
/*      */     }
/*  408 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  411 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  412 */       return false; 
/*  413 */     if (k == curr) {
/*  414 */       return true;
/*      */     }
/*      */     while (true) {
/*  417 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  418 */         return false; 
/*  419 */       if (k == curr)
/*  420 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(int v) {
/*  425 */     int[] value = this.value;
/*  426 */     char[] key = this.key;
/*  427 */     if (this.containsNullKey && value[this.n] == v)
/*  428 */       return true; 
/*  429 */     for (int i = this.n; i-- != 0;) {
/*  430 */       if (key[i] != '\000' && value[i] == v)
/*  431 */         return true; 
/*  432 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOrDefault(char k, int defaultValue) {
/*  438 */     if (k == '\000') {
/*  439 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  441 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  444 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  445 */       return defaultValue; 
/*  446 */     if (k == curr) {
/*  447 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  450 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  451 */         return defaultValue; 
/*  452 */       if (k == curr) {
/*  453 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int putIfAbsent(char k, int v) {
/*  459 */     int pos = find(k);
/*  460 */     if (pos >= 0)
/*  461 */       return this.value[pos]; 
/*  462 */     insert(-pos - 1, k, v);
/*  463 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(char k, int v) {
/*  469 */     if (k == '\000') {
/*  470 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  471 */         removeNullEntry();
/*  472 */         return true;
/*      */       } 
/*  474 */       return false;
/*      */     } 
/*      */     
/*  477 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  480 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  481 */       return false; 
/*  482 */     if (k == curr && v == this.value[pos]) {
/*  483 */       removeEntry(pos);
/*  484 */       return true;
/*      */     } 
/*      */     while (true) {
/*  487 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  488 */         return false; 
/*  489 */       if (k == curr && v == this.value[pos]) {
/*  490 */         removeEntry(pos);
/*  491 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(char k, int oldValue, int v) {
/*  498 */     int pos = find(k);
/*  499 */     if (pos < 0 || oldValue != this.value[pos])
/*  500 */       return false; 
/*  501 */     this.value[pos] = v;
/*  502 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int replace(char k, int v) {
/*  507 */     int pos = find(k);
/*  508 */     if (pos < 0)
/*  509 */       return this.defRetValue; 
/*  510 */     int oldValue = this.value[pos];
/*  511 */     this.value[pos] = v;
/*  512 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(char k, IntUnaryOperator mappingFunction) {
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
/*      */   public int computeIfAbsentNullable(char k, IntFunction<? extends Integer> mappingFunction) {
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
/*      */   public int computeIfPresent(char k, BiFunction<? super Character, ? super Integer, ? extends Integer> remappingFunction) {
/*  544 */     Objects.requireNonNull(remappingFunction);
/*  545 */     int pos = find(k);
/*  546 */     if (pos < 0)
/*  547 */       return this.defRetValue; 
/*  548 */     Integer newValue = remappingFunction.apply(Character.valueOf(k), Integer.valueOf(this.value[pos]));
/*  549 */     if (newValue == null) {
/*  550 */       if (k == '\000') {
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
/*      */   public int compute(char k, BiFunction<? super Character, ? super Integer, ? extends Integer> remappingFunction) {
/*  562 */     Objects.requireNonNull(remappingFunction);
/*  563 */     int pos = find(k);
/*  564 */     Integer newValue = remappingFunction.apply(Character.valueOf(k), 
/*  565 */         (pos >= 0) ? Integer.valueOf(this.value[pos]) : null);
/*  566 */     if (newValue == null) {
/*  567 */       if (pos >= 0)
/*  568 */         if (k == '\000') {
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
/*      */   public int merge(char k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  586 */     Objects.requireNonNull(remappingFunction);
/*  587 */     int pos = find(k);
/*  588 */     if (pos < 0) {
/*  589 */       insert(-pos - 1, k, v);
/*  590 */       return v;
/*      */     } 
/*  592 */     Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
/*  593 */     if (newValue == null) {
/*  594 */       if (k == '\000') {
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
/*  615 */     Arrays.fill(this.key, false);
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
/*      */     implements Char2IntMap.Entry, Map.Entry<Character, Integer>
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
/*      */     public char getCharKey() {
/*  641 */       return Char2IntOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public int getIntValue() {
/*  645 */       return Char2IntOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public int setValue(int v) {
/*  649 */       int oldValue = Char2IntOpenHashMap.this.value[this.index];
/*  650 */       Char2IntOpenHashMap.this.value[this.index] = v;
/*  651 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getKey() {
/*  661 */       return Character.valueOf(Char2IntOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getValue() {
/*  671 */       return Integer.valueOf(Char2IntOpenHashMap.this.value[this.index]);
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
/*  688 */       Map.Entry<Character, Integer> e = (Map.Entry<Character, Integer>)o;
/*  689 */       return (Char2IntOpenHashMap.this.key[this.index] == ((Character)e.getKey()).charValue() && Char2IntOpenHashMap.this.value[this.index] == ((Integer)e.getValue()).intValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  693 */       return Char2IntOpenHashMap.this.key[this.index] ^ Char2IntOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  697 */       return Char2IntOpenHashMap.this.key[this.index] + "=>" + Char2IntOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  707 */     int pos = Char2IntOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  714 */     int last = -1;
/*      */     
/*  716 */     int c = Char2IntOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  720 */     boolean mustReturnNullKey = Char2IntOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     CharArrayList wrapped;
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
/*  735 */         return this.last = Char2IntOpenHashMap.this.n;
/*      */       } 
/*  737 */       char[] key = Char2IntOpenHashMap.this.key;
/*      */       while (true) {
/*  739 */         if (--this.pos < 0) {
/*      */           
/*  741 */           this.last = Integer.MIN_VALUE;
/*  742 */           char k = this.wrapped.getChar(-this.pos - 1);
/*  743 */           int p = HashCommon.mix(k) & Char2IntOpenHashMap.this.mask;
/*  744 */           while (k != key[p])
/*  745 */             p = p + 1 & Char2IntOpenHashMap.this.mask; 
/*  746 */           return p;
/*      */         } 
/*  748 */         if (key[this.pos] != '\000') {
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
/*  763 */       char[] key = Char2IntOpenHashMap.this.key; while (true) {
/*      */         char curr; int last;
/*  765 */         pos = (last = pos) + 1 & Char2IntOpenHashMap.this.mask;
/*      */         while (true) {
/*  767 */           if ((curr = key[pos]) == '\000') {
/*  768 */             key[last] = Character.MIN_VALUE;
/*      */             return;
/*      */           } 
/*  771 */           int slot = HashCommon.mix(curr) & Char2IntOpenHashMap.this.mask;
/*  772 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  774 */           pos = pos + 1 & Char2IntOpenHashMap.this.mask;
/*      */         } 
/*  776 */         if (pos < last) {
/*  777 */           if (this.wrapped == null)
/*  778 */             this.wrapped = new CharArrayList(2); 
/*  779 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  781 */         key[last] = curr;
/*  782 */         Char2IntOpenHashMap.this.value[last] = Char2IntOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  786 */       if (this.last == -1)
/*  787 */         throw new IllegalStateException(); 
/*  788 */       if (this.last == Char2IntOpenHashMap.this.n) {
/*  789 */         Char2IntOpenHashMap.this.containsNullKey = false;
/*  790 */       } else if (this.pos >= 0) {
/*  791 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  794 */         Char2IntOpenHashMap.this.remove(this.wrapped.getChar(-this.pos - 1));
/*  795 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  798 */       Char2IntOpenHashMap.this.size--;
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
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Char2IntMap.Entry> { private Char2IntOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Char2IntOpenHashMap.MapEntry next() {
/*  814 */       return this.entry = new Char2IntOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  818 */       super.remove();
/*  819 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Char2IntMap.Entry> { private FastEntryIterator() {
/*  823 */       this.entry = new Char2IntOpenHashMap.MapEntry();
/*      */     } private final Char2IntOpenHashMap.MapEntry entry;
/*      */     public Char2IntOpenHashMap.MapEntry next() {
/*  826 */       this.entry.index = nextEntry();
/*  827 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Char2IntMap.Entry> implements Char2IntMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Char2IntMap.Entry> iterator() {
/*  833 */       return new Char2IntOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Char2IntMap.Entry> fastIterator() {
/*  837 */       return new Char2IntOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  842 */       if (!(o instanceof Map.Entry))
/*  843 */         return false; 
/*  844 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  845 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/*  846 */         return false; 
/*  847 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  848 */         return false; 
/*  849 */       char k = ((Character)e.getKey()).charValue();
/*  850 */       int v = ((Integer)e.getValue()).intValue();
/*  851 */       if (k == '\000') {
/*  852 */         return (Char2IntOpenHashMap.this.containsNullKey && Char2IntOpenHashMap.this.value[Char2IntOpenHashMap.this.n] == v);
/*      */       }
/*  854 */       char[] key = Char2IntOpenHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/*  857 */       if ((curr = key[pos = HashCommon.mix(k) & Char2IntOpenHashMap.this.mask]) == '\000')
/*  858 */         return false; 
/*  859 */       if (k == curr) {
/*  860 */         return (Char2IntOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  863 */         if ((curr = key[pos = pos + 1 & Char2IntOpenHashMap.this.mask]) == '\000')
/*  864 */           return false; 
/*  865 */         if (k == curr) {
/*  866 */           return (Char2IntOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  872 */       if (!(o instanceof Map.Entry))
/*  873 */         return false; 
/*  874 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  875 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/*  876 */         return false; 
/*  877 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  878 */         return false; 
/*  879 */       char k = ((Character)e.getKey()).charValue();
/*  880 */       int v = ((Integer)e.getValue()).intValue();
/*  881 */       if (k == '\000') {
/*  882 */         if (Char2IntOpenHashMap.this.containsNullKey && Char2IntOpenHashMap.this.value[Char2IntOpenHashMap.this.n] == v) {
/*  883 */           Char2IntOpenHashMap.this.removeNullEntry();
/*  884 */           return true;
/*      */         } 
/*  886 */         return false;
/*      */       } 
/*      */       
/*  889 */       char[] key = Char2IntOpenHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/*  892 */       if ((curr = key[pos = HashCommon.mix(k) & Char2IntOpenHashMap.this.mask]) == '\000')
/*  893 */         return false; 
/*  894 */       if (curr == k) {
/*  895 */         if (Char2IntOpenHashMap.this.value[pos] == v) {
/*  896 */           Char2IntOpenHashMap.this.removeEntry(pos);
/*  897 */           return true;
/*      */         } 
/*  899 */         return false;
/*      */       } 
/*      */       while (true) {
/*  902 */         if ((curr = key[pos = pos + 1 & Char2IntOpenHashMap.this.mask]) == '\000')
/*  903 */           return false; 
/*  904 */         if (curr == k && 
/*  905 */           Char2IntOpenHashMap.this.value[pos] == v) {
/*  906 */           Char2IntOpenHashMap.this.removeEntry(pos);
/*  907 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  914 */       return Char2IntOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  918 */       Char2IntOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Char2IntMap.Entry> consumer) {
/*  923 */       if (Char2IntOpenHashMap.this.containsNullKey)
/*  924 */         consumer.accept(new AbstractChar2IntMap.BasicEntry(Char2IntOpenHashMap.this.key[Char2IntOpenHashMap.this.n], Char2IntOpenHashMap.this.value[Char2IntOpenHashMap.this.n])); 
/*  925 */       for (int pos = Char2IntOpenHashMap.this.n; pos-- != 0;) {
/*  926 */         if (Char2IntOpenHashMap.this.key[pos] != '\000')
/*  927 */           consumer.accept(new AbstractChar2IntMap.BasicEntry(Char2IntOpenHashMap.this.key[pos], Char2IntOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Char2IntMap.Entry> consumer) {
/*  932 */       AbstractChar2IntMap.BasicEntry entry = new AbstractChar2IntMap.BasicEntry();
/*  933 */       if (Char2IntOpenHashMap.this.containsNullKey) {
/*  934 */         entry.key = Char2IntOpenHashMap.this.key[Char2IntOpenHashMap.this.n];
/*  935 */         entry.value = Char2IntOpenHashMap.this.value[Char2IntOpenHashMap.this.n];
/*  936 */         consumer.accept(entry);
/*      */       } 
/*  938 */       for (int pos = Char2IntOpenHashMap.this.n; pos-- != 0;) {
/*  939 */         if (Char2IntOpenHashMap.this.key[pos] != '\000') {
/*  940 */           entry.key = Char2IntOpenHashMap.this.key[pos];
/*  941 */           entry.value = Char2IntOpenHashMap.this.value[pos];
/*  942 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Char2IntMap.FastEntrySet char2IntEntrySet() {
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
/*      */     implements CharIterator
/*      */   {
/*      */     public char nextChar() {
/*  967 */       return Char2IntOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractCharSet { private KeySet() {}
/*      */     
/*      */     public CharIterator iterator() {
/*  973 */       return new Char2IntOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/*  978 */       if (Char2IntOpenHashMap.this.containsNullKey)
/*  979 */         consumer.accept(Char2IntOpenHashMap.this.key[Char2IntOpenHashMap.this.n]); 
/*  980 */       for (int pos = Char2IntOpenHashMap.this.n; pos-- != 0; ) {
/*  981 */         char k = Char2IntOpenHashMap.this.key[pos];
/*  982 */         if (k != '\000')
/*  983 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/*  988 */       return Char2IntOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(char k) {
/*  992 */       return Char2IntOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(char k) {
/*  996 */       int oldSize = Char2IntOpenHashMap.this.size;
/*  997 */       Char2IntOpenHashMap.this.remove(k);
/*  998 */       return (Char2IntOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1002 */       Char2IntOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public CharSet keySet() {
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
/* 1026 */       return Char2IntOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public IntCollection values() {
/* 1031 */     if (this.values == null)
/* 1032 */       this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1035 */             return new Char2IntOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1039 */             return Char2IntOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(int v) {
/* 1043 */             return Char2IntOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1047 */             Char2IntOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1052 */             if (Char2IntOpenHashMap.this.containsNullKey)
/* 1053 */               consumer.accept(Char2IntOpenHashMap.this.value[Char2IntOpenHashMap.this.n]); 
/* 1054 */             for (int pos = Char2IntOpenHashMap.this.n; pos-- != 0;) {
/* 1055 */               if (Char2IntOpenHashMap.this.key[pos] != '\000')
/* 1056 */                 consumer.accept(Char2IntOpenHashMap.this.value[pos]); 
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
/* 1124 */     char[] key = this.key;
/* 1125 */     int[] value = this.value;
/* 1126 */     int mask = newN - 1;
/* 1127 */     char[] newKey = new char[newN + 1];
/* 1128 */     int[] newValue = new int[newN + 1];
/* 1129 */     int i = this.n;
/* 1130 */     for (int j = realSize(); j-- != 0; ) {
/* 1131 */       while (key[--i] == '\000'); int pos;
/* 1132 */       if (newKey[pos = HashCommon.mix(key[i]) & mask] != '\000')
/* 1133 */         while (newKey[pos = pos + 1 & mask] != '\000'); 
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
/*      */   public Char2IntOpenHashMap clone() {
/*      */     Char2IntOpenHashMap c;
/*      */     try {
/* 1159 */       c = (Char2IntOpenHashMap)super.clone();
/* 1160 */     } catch (CloneNotSupportedException cantHappen) {
/* 1161 */       throw new InternalError();
/*      */     } 
/* 1163 */     c.keys = null;
/* 1164 */     c.values = null;
/* 1165 */     c.entries = null;
/* 1166 */     c.containsNullKey = this.containsNullKey;
/* 1167 */     c.key = (char[])this.key.clone();
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
/* 1184 */       while (this.key[i] == '\000')
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
/* 1197 */     char[] key = this.key;
/* 1198 */     int[] value = this.value;
/* 1199 */     MapIterator i = new MapIterator();
/* 1200 */     s.defaultWriteObject();
/* 1201 */     for (int j = this.size; j-- != 0; ) {
/* 1202 */       int e = i.nextEntry();
/* 1203 */       s.writeChar(key[e]);
/* 1204 */       s.writeInt(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1209 */     s.defaultReadObject();
/* 1210 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1211 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1212 */     this.mask = this.n - 1;
/* 1213 */     char[] key = this.key = new char[this.n + 1];
/* 1214 */     int[] value = this.value = new int[this.n + 1];
/*      */ 
/*      */     
/* 1217 */     for (int i = this.size; i-- != 0; ) {
/* 1218 */       int pos; char k = s.readChar();
/* 1219 */       int v = s.readInt();
/* 1220 */       if (k == '\000') {
/* 1221 */         pos = this.n;
/* 1222 */         this.containsNullKey = true;
/*      */       } else {
/* 1224 */         pos = HashCommon.mix(k) & this.mask;
/* 1225 */         while (key[pos] != '\000')
/* 1226 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1228 */       key[pos] = k;
/* 1229 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2IntOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */