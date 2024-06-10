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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Char2IntOpenCustomHashMap
/*      */   extends AbstractChar2IntMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient char[] key;
/*      */   protected transient int[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected CharHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Char2IntMap.FastEntrySet entries;
/*      */   protected transient CharSet keys;
/*      */   protected transient IntCollection values;
/*      */   
/*      */   public Char2IntOpenCustomHashMap(int expected, float f, CharHash.Strategy strategy) {
/*  103 */     this.strategy = strategy;
/*  104 */     if (f <= 0.0F || f > 1.0F)
/*  105 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  106 */     if (expected < 0)
/*  107 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  108 */     this.f = f;
/*  109 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  110 */     this.mask = this.n - 1;
/*  111 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  112 */     this.key = new char[this.n + 1];
/*  113 */     this.value = new int[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2IntOpenCustomHashMap(int expected, CharHash.Strategy strategy) {
/*  124 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2IntOpenCustomHashMap(CharHash.Strategy strategy) {
/*  135 */     this(16, 0.75F, strategy);
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
/*      */   public Char2IntOpenCustomHashMap(Map<? extends Character, ? extends Integer> m, float f, CharHash.Strategy strategy) {
/*  149 */     this(m.size(), f, strategy);
/*  150 */     putAll(m);
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
/*      */   public Char2IntOpenCustomHashMap(Map<? extends Character, ? extends Integer> m, CharHash.Strategy strategy) {
/*  163 */     this(m, 0.75F, strategy);
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
/*      */   public Char2IntOpenCustomHashMap(Char2IntMap m, float f, CharHash.Strategy strategy) {
/*  177 */     this(m.size(), f, strategy);
/*  178 */     putAll(m);
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
/*      */   public Char2IntOpenCustomHashMap(Char2IntMap m, CharHash.Strategy strategy) {
/*  191 */     this(m, 0.75F, strategy);
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
/*      */   public Char2IntOpenCustomHashMap(char[] k, int[] v, float f, CharHash.Strategy strategy) {
/*  209 */     this(k.length, f, strategy);
/*  210 */     if (k.length != v.length) {
/*  211 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  213 */     for (int i = 0; i < k.length; i++) {
/*  214 */       put(k[i], v[i]);
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
/*      */   public Char2IntOpenCustomHashMap(char[] k, int[] v, CharHash.Strategy strategy) {
/*  231 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharHash.Strategy strategy() {
/*  239 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  242 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  245 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  246 */     if (needed > this.n)
/*  247 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  250 */     int needed = (int)Math.min(1073741824L, 
/*  251 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  252 */     if (needed > this.n)
/*  253 */       rehash(needed); 
/*      */   }
/*      */   private int removeEntry(int pos) {
/*  256 */     int oldValue = this.value[pos];
/*  257 */     this.size--;
/*  258 */     shiftKeys(pos);
/*  259 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  260 */       rehash(this.n / 2); 
/*  261 */     return oldValue;
/*      */   }
/*      */   private int removeNullEntry() {
/*  264 */     this.containsNullKey = false;
/*  265 */     int oldValue = this.value[this.n];
/*  266 */     this.size--;
/*  267 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  268 */       rehash(this.n / 2); 
/*  269 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Character, ? extends Integer> m) {
/*  273 */     if (this.f <= 0.5D) {
/*  274 */       ensureCapacity(m.size());
/*      */     } else {
/*  276 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  278 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(char k) {
/*  282 */     if (this.strategy.equals(k, false)) {
/*  283 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  285 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  288 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  289 */       return -(pos + 1); 
/*  290 */     if (this.strategy.equals(k, curr)) {
/*  291 */       return pos;
/*      */     }
/*      */     while (true) {
/*  294 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  295 */         return -(pos + 1); 
/*  296 */       if (this.strategy.equals(k, curr))
/*  297 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, char k, int v) {
/*  301 */     if (pos == this.n)
/*  302 */       this.containsNullKey = true; 
/*  303 */     this.key[pos] = k;
/*  304 */     this.value[pos] = v;
/*  305 */     if (this.size++ >= this.maxFill) {
/*  306 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public int put(char k, int v) {
/*  312 */     int pos = find(k);
/*  313 */     if (pos < 0) {
/*  314 */       insert(-pos - 1, k, v);
/*  315 */       return this.defRetValue;
/*      */     } 
/*  317 */     int oldValue = this.value[pos];
/*  318 */     this.value[pos] = v;
/*  319 */     return oldValue;
/*      */   }
/*      */   private int addToValue(int pos, int incr) {
/*  322 */     int oldValue = this.value[pos];
/*  323 */     this.value[pos] = oldValue + incr;
/*  324 */     return oldValue;
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
/*  344 */     if (this.strategy.equals(k, false)) {
/*  345 */       if (this.containsNullKey)
/*  346 */         return addToValue(this.n, incr); 
/*  347 */       pos = this.n;
/*  348 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  351 */       char[] key = this.key;
/*      */       char curr;
/*  353 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != '\000') {
/*      */         
/*  355 */         if (this.strategy.equals(curr, k))
/*  356 */           return addToValue(pos, incr); 
/*  357 */         while ((curr = key[pos = pos + 1 & this.mask]) != '\000') {
/*  358 */           if (this.strategy.equals(curr, k))
/*  359 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  362 */     }  this.key[pos] = k;
/*  363 */     this.value[pos] = this.defRetValue + incr;
/*  364 */     if (this.size++ >= this.maxFill) {
/*  365 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  368 */     return this.defRetValue;
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
/*  381 */     char[] key = this.key; while (true) {
/*      */       char curr; int last;
/*  383 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  385 */         if ((curr = key[pos]) == '\000') {
/*  386 */           key[last] = Character.MIN_VALUE;
/*      */           return;
/*      */         } 
/*  389 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  390 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  392 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  394 */       key[last] = curr;
/*  395 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int remove(char k) {
/*  401 */     if (this.strategy.equals(k, false)) {
/*  402 */       if (this.containsNullKey)
/*  403 */         return removeNullEntry(); 
/*  404 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  407 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  410 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  411 */       return this.defRetValue; 
/*  412 */     if (this.strategy.equals(k, curr))
/*  413 */       return removeEntry(pos); 
/*      */     while (true) {
/*  415 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  416 */         return this.defRetValue; 
/*  417 */       if (this.strategy.equals(k, curr)) {
/*  418 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int get(char k) {
/*  424 */     if (this.strategy.equals(k, false)) {
/*  425 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  427 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  430 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  431 */       return this.defRetValue; 
/*  432 */     if (this.strategy.equals(k, curr)) {
/*  433 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  436 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  437 */         return this.defRetValue; 
/*  438 */       if (this.strategy.equals(k, curr)) {
/*  439 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(char k) {
/*  445 */     if (this.strategy.equals(k, false)) {
/*  446 */       return this.containsNullKey;
/*      */     }
/*  448 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  451 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  452 */       return false; 
/*  453 */     if (this.strategy.equals(k, curr)) {
/*  454 */       return true;
/*      */     }
/*      */     while (true) {
/*  457 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  458 */         return false; 
/*  459 */       if (this.strategy.equals(k, curr))
/*  460 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(int v) {
/*  465 */     int[] value = this.value;
/*  466 */     char[] key = this.key;
/*  467 */     if (this.containsNullKey && value[this.n] == v)
/*  468 */       return true; 
/*  469 */     for (int i = this.n; i-- != 0;) {
/*  470 */       if (key[i] != '\000' && value[i] == v)
/*  471 */         return true; 
/*  472 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOrDefault(char k, int defaultValue) {
/*  478 */     if (this.strategy.equals(k, false)) {
/*  479 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  481 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  484 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  485 */       return defaultValue; 
/*  486 */     if (this.strategy.equals(k, curr)) {
/*  487 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  490 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  491 */         return defaultValue; 
/*  492 */       if (this.strategy.equals(k, curr)) {
/*  493 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int putIfAbsent(char k, int v) {
/*  499 */     int pos = find(k);
/*  500 */     if (pos >= 0)
/*  501 */       return this.value[pos]; 
/*  502 */     insert(-pos - 1, k, v);
/*  503 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(char k, int v) {
/*  509 */     if (this.strategy.equals(k, false)) {
/*  510 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  511 */         removeNullEntry();
/*  512 */         return true;
/*      */       } 
/*  514 */       return false;
/*      */     } 
/*      */     
/*  517 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  520 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  521 */       return false; 
/*  522 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  523 */       removeEntry(pos);
/*  524 */       return true;
/*      */     } 
/*      */     while (true) {
/*  527 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  528 */         return false; 
/*  529 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  530 */         removeEntry(pos);
/*  531 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(char k, int oldValue, int v) {
/*  538 */     int pos = find(k);
/*  539 */     if (pos < 0 || oldValue != this.value[pos])
/*  540 */       return false; 
/*  541 */     this.value[pos] = v;
/*  542 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int replace(char k, int v) {
/*  547 */     int pos = find(k);
/*  548 */     if (pos < 0)
/*  549 */       return this.defRetValue; 
/*  550 */     int oldValue = this.value[pos];
/*  551 */     this.value[pos] = v;
/*  552 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(char k, IntUnaryOperator mappingFunction) {
/*  557 */     Objects.requireNonNull(mappingFunction);
/*  558 */     int pos = find(k);
/*  559 */     if (pos >= 0)
/*  560 */       return this.value[pos]; 
/*  561 */     int newValue = mappingFunction.applyAsInt(k);
/*  562 */     insert(-pos - 1, k, newValue);
/*  563 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsentNullable(char k, IntFunction<? extends Integer> mappingFunction) {
/*  569 */     Objects.requireNonNull(mappingFunction);
/*  570 */     int pos = find(k);
/*  571 */     if (pos >= 0)
/*  572 */       return this.value[pos]; 
/*  573 */     Integer newValue = mappingFunction.apply(k);
/*  574 */     if (newValue == null)
/*  575 */       return this.defRetValue; 
/*  576 */     int v = newValue.intValue();
/*  577 */     insert(-pos - 1, k, v);
/*  578 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfPresent(char k, BiFunction<? super Character, ? super Integer, ? extends Integer> remappingFunction) {
/*  584 */     Objects.requireNonNull(remappingFunction);
/*  585 */     int pos = find(k);
/*  586 */     if (pos < 0)
/*  587 */       return this.defRetValue; 
/*  588 */     Integer newValue = remappingFunction.apply(Character.valueOf(k), Integer.valueOf(this.value[pos]));
/*  589 */     if (newValue == null) {
/*  590 */       if (this.strategy.equals(k, false)) {
/*  591 */         removeNullEntry();
/*      */       } else {
/*  593 */         removeEntry(pos);
/*  594 */       }  return this.defRetValue;
/*      */     } 
/*  596 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int compute(char k, BiFunction<? super Character, ? super Integer, ? extends Integer> remappingFunction) {
/*  602 */     Objects.requireNonNull(remappingFunction);
/*  603 */     int pos = find(k);
/*  604 */     Integer newValue = remappingFunction.apply(Character.valueOf(k), 
/*  605 */         (pos >= 0) ? Integer.valueOf(this.value[pos]) : null);
/*  606 */     if (newValue == null) {
/*  607 */       if (pos >= 0)
/*  608 */         if (this.strategy.equals(k, false)) {
/*  609 */           removeNullEntry();
/*      */         } else {
/*  611 */           removeEntry(pos);
/*      */         }  
/*  613 */       return this.defRetValue;
/*      */     } 
/*  615 */     int newVal = newValue.intValue();
/*  616 */     if (pos < 0) {
/*  617 */       insert(-pos - 1, k, newVal);
/*  618 */       return newVal;
/*      */     } 
/*  620 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int merge(char k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  626 */     Objects.requireNonNull(remappingFunction);
/*  627 */     int pos = find(k);
/*  628 */     if (pos < 0) {
/*  629 */       insert(-pos - 1, k, v);
/*  630 */       return v;
/*      */     } 
/*  632 */     Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
/*  633 */     if (newValue == null) {
/*  634 */       if (this.strategy.equals(k, false)) {
/*  635 */         removeNullEntry();
/*      */       } else {
/*  637 */         removeEntry(pos);
/*  638 */       }  return this.defRetValue;
/*      */     } 
/*  640 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
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
/*  651 */     if (this.size == 0)
/*      */       return; 
/*  653 */     this.size = 0;
/*  654 */     this.containsNullKey = false;
/*  655 */     Arrays.fill(this.key, false);
/*      */   }
/*      */   
/*      */   public int size() {
/*  659 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  663 */     return (this.size == 0);
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
/*  675 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public char getCharKey() {
/*  681 */       return Char2IntOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public int getIntValue() {
/*  685 */       return Char2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public int setValue(int v) {
/*  689 */       int oldValue = Char2IntOpenCustomHashMap.this.value[this.index];
/*  690 */       Char2IntOpenCustomHashMap.this.value[this.index] = v;
/*  691 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getKey() {
/*  701 */       return Character.valueOf(Char2IntOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getValue() {
/*  711 */       return Integer.valueOf(Char2IntOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer setValue(Integer v) {
/*  721 */       return Integer.valueOf(setValue(v.intValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  726 */       if (!(o instanceof Map.Entry))
/*  727 */         return false; 
/*  728 */       Map.Entry<Character, Integer> e = (Map.Entry<Character, Integer>)o;
/*  729 */       return (Char2IntOpenCustomHashMap.this.strategy.equals(Char2IntOpenCustomHashMap.this.key[this.index], ((Character)e.getKey()).charValue()) && Char2IntOpenCustomHashMap.this.value[this.index] == ((Integer)e
/*  730 */         .getValue()).intValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  734 */       return Char2IntOpenCustomHashMap.this.strategy.hashCode(Char2IntOpenCustomHashMap.this.key[this.index]) ^ Char2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  738 */       return Char2IntOpenCustomHashMap.this.key[this.index] + "=>" + Char2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  748 */     int pos = Char2IntOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  755 */     int last = -1;
/*      */     
/*  757 */     int c = Char2IntOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  761 */     boolean mustReturnNullKey = Char2IntOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     CharArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  768 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  771 */       if (!hasNext())
/*  772 */         throw new NoSuchElementException(); 
/*  773 */       this.c--;
/*  774 */       if (this.mustReturnNullKey) {
/*  775 */         this.mustReturnNullKey = false;
/*  776 */         return this.last = Char2IntOpenCustomHashMap.this.n;
/*      */       } 
/*  778 */       char[] key = Char2IntOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  780 */         if (--this.pos < 0) {
/*      */           
/*  782 */           this.last = Integer.MIN_VALUE;
/*  783 */           char k = this.wrapped.getChar(-this.pos - 1);
/*  784 */           int p = HashCommon.mix(Char2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Char2IntOpenCustomHashMap.this.mask;
/*  785 */           while (!Char2IntOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  786 */             p = p + 1 & Char2IntOpenCustomHashMap.this.mask; 
/*  787 */           return p;
/*      */         } 
/*  789 */         if (key[this.pos] != '\000') {
/*  790 */           return this.last = this.pos;
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
/*  804 */       char[] key = Char2IntOpenCustomHashMap.this.key; while (true) {
/*      */         char curr; int last;
/*  806 */         pos = (last = pos) + 1 & Char2IntOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  808 */           if ((curr = key[pos]) == '\000') {
/*  809 */             key[last] = Character.MIN_VALUE;
/*      */             return;
/*      */           } 
/*  812 */           int slot = HashCommon.mix(Char2IntOpenCustomHashMap.this.strategy.hashCode(curr)) & Char2IntOpenCustomHashMap.this.mask;
/*  813 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  815 */           pos = pos + 1 & Char2IntOpenCustomHashMap.this.mask;
/*      */         } 
/*  817 */         if (pos < last) {
/*  818 */           if (this.wrapped == null)
/*  819 */             this.wrapped = new CharArrayList(2); 
/*  820 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  822 */         key[last] = curr;
/*  823 */         Char2IntOpenCustomHashMap.this.value[last] = Char2IntOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  827 */       if (this.last == -1)
/*  828 */         throw new IllegalStateException(); 
/*  829 */       if (this.last == Char2IntOpenCustomHashMap.this.n) {
/*  830 */         Char2IntOpenCustomHashMap.this.containsNullKey = false;
/*  831 */       } else if (this.pos >= 0) {
/*  832 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  835 */         Char2IntOpenCustomHashMap.this.remove(this.wrapped.getChar(-this.pos - 1));
/*  836 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  839 */       Char2IntOpenCustomHashMap.this.size--;
/*  840 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  845 */       int i = n;
/*  846 */       while (i-- != 0 && hasNext())
/*  847 */         nextEntry(); 
/*  848 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Char2IntMap.Entry> { private Char2IntOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Char2IntOpenCustomHashMap.MapEntry next() {
/*  855 */       return this.entry = new Char2IntOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  859 */       super.remove();
/*  860 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Char2IntMap.Entry> { private FastEntryIterator() {
/*  864 */       this.entry = new Char2IntOpenCustomHashMap.MapEntry();
/*      */     } private final Char2IntOpenCustomHashMap.MapEntry entry;
/*      */     public Char2IntOpenCustomHashMap.MapEntry next() {
/*  867 */       this.entry.index = nextEntry();
/*  868 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Char2IntMap.Entry> implements Char2IntMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Char2IntMap.Entry> iterator() {
/*  874 */       return new Char2IntOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Char2IntMap.Entry> fastIterator() {
/*  878 */       return new Char2IntOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  883 */       if (!(o instanceof Map.Entry))
/*  884 */         return false; 
/*  885 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  886 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/*  887 */         return false; 
/*  888 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  889 */         return false; 
/*  890 */       char k = ((Character)e.getKey()).charValue();
/*  891 */       int v = ((Integer)e.getValue()).intValue();
/*  892 */       if (Char2IntOpenCustomHashMap.this.strategy.equals(k, false)) {
/*  893 */         return (Char2IntOpenCustomHashMap.this.containsNullKey && Char2IntOpenCustomHashMap.this.value[Char2IntOpenCustomHashMap.this.n] == v);
/*      */       }
/*  895 */       char[] key = Char2IntOpenCustomHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/*  898 */       if ((curr = key[pos = HashCommon.mix(Char2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Char2IntOpenCustomHashMap.this.mask]) == '\000')
/*  899 */         return false; 
/*  900 */       if (Char2IntOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  901 */         return (Char2IntOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  904 */         if ((curr = key[pos = pos + 1 & Char2IntOpenCustomHashMap.this.mask]) == '\000')
/*  905 */           return false; 
/*  906 */         if (Char2IntOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  907 */           return (Char2IntOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  913 */       if (!(o instanceof Map.Entry))
/*  914 */         return false; 
/*  915 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  916 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/*  917 */         return false; 
/*  918 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/*  919 */         return false; 
/*  920 */       char k = ((Character)e.getKey()).charValue();
/*  921 */       int v = ((Integer)e.getValue()).intValue();
/*  922 */       if (Char2IntOpenCustomHashMap.this.strategy.equals(k, false)) {
/*  923 */         if (Char2IntOpenCustomHashMap.this.containsNullKey && Char2IntOpenCustomHashMap.this.value[Char2IntOpenCustomHashMap.this.n] == v) {
/*  924 */           Char2IntOpenCustomHashMap.this.removeNullEntry();
/*  925 */           return true;
/*      */         } 
/*  927 */         return false;
/*      */       } 
/*      */       
/*  930 */       char[] key = Char2IntOpenCustomHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/*  933 */       if ((curr = key[pos = HashCommon.mix(Char2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Char2IntOpenCustomHashMap.this.mask]) == '\000')
/*  934 */         return false; 
/*  935 */       if (Char2IntOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  936 */         if (Char2IntOpenCustomHashMap.this.value[pos] == v) {
/*  937 */           Char2IntOpenCustomHashMap.this.removeEntry(pos);
/*  938 */           return true;
/*      */         } 
/*  940 */         return false;
/*      */       } 
/*      */       while (true) {
/*  943 */         if ((curr = key[pos = pos + 1 & Char2IntOpenCustomHashMap.this.mask]) == '\000')
/*  944 */           return false; 
/*  945 */         if (Char2IntOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  946 */           Char2IntOpenCustomHashMap.this.value[pos] == v) {
/*  947 */           Char2IntOpenCustomHashMap.this.removeEntry(pos);
/*  948 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  955 */       return Char2IntOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  959 */       Char2IntOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Char2IntMap.Entry> consumer) {
/*  964 */       if (Char2IntOpenCustomHashMap.this.containsNullKey)
/*  965 */         consumer.accept(new AbstractChar2IntMap.BasicEntry(Char2IntOpenCustomHashMap.this.key[Char2IntOpenCustomHashMap.this.n], Char2IntOpenCustomHashMap.this.value[Char2IntOpenCustomHashMap.this.n])); 
/*  966 */       for (int pos = Char2IntOpenCustomHashMap.this.n; pos-- != 0;) {
/*  967 */         if (Char2IntOpenCustomHashMap.this.key[pos] != '\000')
/*  968 */           consumer.accept(new AbstractChar2IntMap.BasicEntry(Char2IntOpenCustomHashMap.this.key[pos], Char2IntOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Char2IntMap.Entry> consumer) {
/*  973 */       AbstractChar2IntMap.BasicEntry entry = new AbstractChar2IntMap.BasicEntry();
/*  974 */       if (Char2IntOpenCustomHashMap.this.containsNullKey) {
/*  975 */         entry.key = Char2IntOpenCustomHashMap.this.key[Char2IntOpenCustomHashMap.this.n];
/*  976 */         entry.value = Char2IntOpenCustomHashMap.this.value[Char2IntOpenCustomHashMap.this.n];
/*  977 */         consumer.accept(entry);
/*      */       } 
/*  979 */       for (int pos = Char2IntOpenCustomHashMap.this.n; pos-- != 0;) {
/*  980 */         if (Char2IntOpenCustomHashMap.this.key[pos] != '\000') {
/*  981 */           entry.key = Char2IntOpenCustomHashMap.this.key[pos];
/*  982 */           entry.value = Char2IntOpenCustomHashMap.this.value[pos];
/*  983 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Char2IntMap.FastEntrySet char2IntEntrySet() {
/*  989 */     if (this.entries == null)
/*  990 */       this.entries = new MapEntrySet(); 
/*  991 */     return this.entries;
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
/* 1008 */       return Char2IntOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractCharSet { private KeySet() {}
/*      */     
/*      */     public CharIterator iterator() {
/* 1014 */       return new Char2IntOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1019 */       if (Char2IntOpenCustomHashMap.this.containsNullKey)
/* 1020 */         consumer.accept(Char2IntOpenCustomHashMap.this.key[Char2IntOpenCustomHashMap.this.n]); 
/* 1021 */       for (int pos = Char2IntOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1022 */         char k = Char2IntOpenCustomHashMap.this.key[pos];
/* 1023 */         if (k != '\000')
/* 1024 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1029 */       return Char2IntOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(char k) {
/* 1033 */       return Char2IntOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(char k) {
/* 1037 */       int oldSize = Char2IntOpenCustomHashMap.this.size;
/* 1038 */       Char2IntOpenCustomHashMap.this.remove(k);
/* 1039 */       return (Char2IntOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1043 */       Char2IntOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public CharSet keySet() {
/* 1048 */     if (this.keys == null)
/* 1049 */       this.keys = new KeySet(); 
/* 1050 */     return this.keys;
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
/* 1067 */       return Char2IntOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public IntCollection values() {
/* 1072 */     if (this.values == null)
/* 1073 */       this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1076 */             return new Char2IntOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1080 */             return Char2IntOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(int v) {
/* 1084 */             return Char2IntOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1088 */             Char2IntOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1093 */             if (Char2IntOpenCustomHashMap.this.containsNullKey)
/* 1094 */               consumer.accept(Char2IntOpenCustomHashMap.this.value[Char2IntOpenCustomHashMap.this.n]); 
/* 1095 */             for (int pos = Char2IntOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1096 */               if (Char2IntOpenCustomHashMap.this.key[pos] != '\000')
/* 1097 */                 consumer.accept(Char2IntOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1100 */     return this.values;
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
/* 1117 */     return trim(this.size);
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
/* 1141 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1142 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1143 */       return true; 
/*      */     try {
/* 1145 */       rehash(l);
/* 1146 */     } catch (OutOfMemoryError cantDoIt) {
/* 1147 */       return false;
/*      */     } 
/* 1149 */     return true;
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
/* 1165 */     char[] key = this.key;
/* 1166 */     int[] value = this.value;
/* 1167 */     int mask = newN - 1;
/* 1168 */     char[] newKey = new char[newN + 1];
/* 1169 */     int[] newValue = new int[newN + 1];
/* 1170 */     int i = this.n;
/* 1171 */     for (int j = realSize(); j-- != 0; ) {
/* 1172 */       while (key[--i] == '\000'); int pos;
/* 1173 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != '\000')
/*      */       {
/* 1175 */         while (newKey[pos = pos + 1 & mask] != '\000'); } 
/* 1176 */       newKey[pos] = key[i];
/* 1177 */       newValue[pos] = value[i];
/*      */     } 
/* 1179 */     newValue[newN] = value[this.n];
/* 1180 */     this.n = newN;
/* 1181 */     this.mask = mask;
/* 1182 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1183 */     this.key = newKey;
/* 1184 */     this.value = newValue;
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
/*      */   public Char2IntOpenCustomHashMap clone() {
/*      */     Char2IntOpenCustomHashMap c;
/*      */     try {
/* 1201 */       c = (Char2IntOpenCustomHashMap)super.clone();
/* 1202 */     } catch (CloneNotSupportedException cantHappen) {
/* 1203 */       throw new InternalError();
/*      */     } 
/* 1205 */     c.keys = null;
/* 1206 */     c.values = null;
/* 1207 */     c.entries = null;
/* 1208 */     c.containsNullKey = this.containsNullKey;
/* 1209 */     c.key = (char[])this.key.clone();
/* 1210 */     c.value = (int[])this.value.clone();
/* 1211 */     c.strategy = this.strategy;
/* 1212 */     return c;
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
/* 1225 */     int h = 0;
/* 1226 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1227 */       while (this.key[i] == '\000')
/* 1228 */         i++; 
/* 1229 */       t = this.strategy.hashCode(this.key[i]);
/* 1230 */       t ^= this.value[i];
/* 1231 */       h += t;
/* 1232 */       i++;
/*      */     } 
/*      */     
/* 1235 */     if (this.containsNullKey)
/* 1236 */       h += this.value[this.n]; 
/* 1237 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1240 */     char[] key = this.key;
/* 1241 */     int[] value = this.value;
/* 1242 */     MapIterator i = new MapIterator();
/* 1243 */     s.defaultWriteObject();
/* 1244 */     for (int j = this.size; j-- != 0; ) {
/* 1245 */       int e = i.nextEntry();
/* 1246 */       s.writeChar(key[e]);
/* 1247 */       s.writeInt(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1252 */     s.defaultReadObject();
/* 1253 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1254 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1255 */     this.mask = this.n - 1;
/* 1256 */     char[] key = this.key = new char[this.n + 1];
/* 1257 */     int[] value = this.value = new int[this.n + 1];
/*      */ 
/*      */     
/* 1260 */     for (int i = this.size; i-- != 0; ) {
/* 1261 */       int pos; char k = s.readChar();
/* 1262 */       int v = s.readInt();
/* 1263 */       if (this.strategy.equals(k, false)) {
/* 1264 */         pos = this.n;
/* 1265 */         this.containsNullKey = true;
/*      */       } else {
/* 1267 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1268 */         while (key[pos] != '\000')
/* 1269 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1271 */       key[pos] = k;
/* 1272 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2IntOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */