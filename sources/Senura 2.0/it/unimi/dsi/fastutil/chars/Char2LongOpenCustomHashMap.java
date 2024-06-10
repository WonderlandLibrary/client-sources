/*      */ package it.unimi.dsi.fastutil.chars;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterator;
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
/*      */ import java.util.function.IntToLongFunction;
/*      */ import java.util.function.LongConsumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Char2LongOpenCustomHashMap
/*      */   extends AbstractChar2LongMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient char[] key;
/*      */   protected transient long[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected CharHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Char2LongMap.FastEntrySet entries;
/*      */   protected transient CharSet keys;
/*      */   protected transient LongCollection values;
/*      */   
/*      */   public Char2LongOpenCustomHashMap(int expected, float f, CharHash.Strategy strategy) {
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
/*  113 */     this.value = new long[this.n + 1];
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
/*      */   public Char2LongOpenCustomHashMap(int expected, CharHash.Strategy strategy) {
/*  125 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2LongOpenCustomHashMap(CharHash.Strategy strategy) {
/*  136 */     this(16, 0.75F, strategy);
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
/*      */   public Char2LongOpenCustomHashMap(Map<? extends Character, ? extends Long> m, float f, CharHash.Strategy strategy) {
/*  150 */     this(m.size(), f, strategy);
/*  151 */     putAll(m);
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
/*      */   public Char2LongOpenCustomHashMap(Map<? extends Character, ? extends Long> m, CharHash.Strategy strategy) {
/*  164 */     this(m, 0.75F, strategy);
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
/*      */   public Char2LongOpenCustomHashMap(Char2LongMap m, float f, CharHash.Strategy strategy) {
/*  178 */     this(m.size(), f, strategy);
/*  179 */     putAll(m);
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
/*      */   public Char2LongOpenCustomHashMap(Char2LongMap m, CharHash.Strategy strategy) {
/*  192 */     this(m, 0.75F, strategy);
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
/*      */   public Char2LongOpenCustomHashMap(char[] k, long[] v, float f, CharHash.Strategy strategy) {
/*  210 */     this(k.length, f, strategy);
/*  211 */     if (k.length != v.length) {
/*  212 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  214 */     for (int i = 0; i < k.length; i++) {
/*  215 */       put(k[i], v[i]);
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
/*      */   public Char2LongOpenCustomHashMap(char[] k, long[] v, CharHash.Strategy strategy) {
/*  232 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharHash.Strategy strategy() {
/*  240 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  243 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  246 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  247 */     if (needed > this.n)
/*  248 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  251 */     int needed = (int)Math.min(1073741824L, 
/*  252 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  253 */     if (needed > this.n)
/*  254 */       rehash(needed); 
/*      */   }
/*      */   private long removeEntry(int pos) {
/*  257 */     long oldValue = this.value[pos];
/*  258 */     this.size--;
/*  259 */     shiftKeys(pos);
/*  260 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  261 */       rehash(this.n / 2); 
/*  262 */     return oldValue;
/*      */   }
/*      */   private long removeNullEntry() {
/*  265 */     this.containsNullKey = false;
/*  266 */     long oldValue = this.value[this.n];
/*  267 */     this.size--;
/*  268 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  269 */       rehash(this.n / 2); 
/*  270 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Character, ? extends Long> m) {
/*  274 */     if (this.f <= 0.5D) {
/*  275 */       ensureCapacity(m.size());
/*      */     } else {
/*  277 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  279 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(char k) {
/*  283 */     if (this.strategy.equals(k, false)) {
/*  284 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  286 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  289 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  290 */       return -(pos + 1); 
/*  291 */     if (this.strategy.equals(k, curr)) {
/*  292 */       return pos;
/*      */     }
/*      */     while (true) {
/*  295 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  296 */         return -(pos + 1); 
/*  297 */       if (this.strategy.equals(k, curr))
/*  298 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, char k, long v) {
/*  302 */     if (pos == this.n)
/*  303 */       this.containsNullKey = true; 
/*  304 */     this.key[pos] = k;
/*  305 */     this.value[pos] = v;
/*  306 */     if (this.size++ >= this.maxFill) {
/*  307 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public long put(char k, long v) {
/*  313 */     int pos = find(k);
/*  314 */     if (pos < 0) {
/*  315 */       insert(-pos - 1, k, v);
/*  316 */       return this.defRetValue;
/*      */     } 
/*  318 */     long oldValue = this.value[pos];
/*  319 */     this.value[pos] = v;
/*  320 */     return oldValue;
/*      */   }
/*      */   private long addToValue(int pos, long incr) {
/*  323 */     long oldValue = this.value[pos];
/*  324 */     this.value[pos] = oldValue + incr;
/*  325 */     return oldValue;
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
/*      */   public long addTo(char k, long incr) {
/*      */     int pos;
/*  345 */     if (this.strategy.equals(k, false)) {
/*  346 */       if (this.containsNullKey)
/*  347 */         return addToValue(this.n, incr); 
/*  348 */       pos = this.n;
/*  349 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  352 */       char[] key = this.key;
/*      */       char curr;
/*  354 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != '\000') {
/*      */         
/*  356 */         if (this.strategy.equals(curr, k))
/*  357 */           return addToValue(pos, incr); 
/*  358 */         while ((curr = key[pos = pos + 1 & this.mask]) != '\000') {
/*  359 */           if (this.strategy.equals(curr, k))
/*  360 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  363 */     }  this.key[pos] = k;
/*  364 */     this.value[pos] = this.defRetValue + incr;
/*  365 */     if (this.size++ >= this.maxFill) {
/*  366 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  369 */     return this.defRetValue;
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
/*  382 */     char[] key = this.key; while (true) {
/*      */       char curr; int last;
/*  384 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  386 */         if ((curr = key[pos]) == '\000') {
/*  387 */           key[last] = Character.MIN_VALUE;
/*      */           return;
/*      */         } 
/*  390 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  391 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  393 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  395 */       key[last] = curr;
/*  396 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long remove(char k) {
/*  402 */     if (this.strategy.equals(k, false)) {
/*  403 */       if (this.containsNullKey)
/*  404 */         return removeNullEntry(); 
/*  405 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  408 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  411 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  412 */       return this.defRetValue; 
/*  413 */     if (this.strategy.equals(k, curr))
/*  414 */       return removeEntry(pos); 
/*      */     while (true) {
/*  416 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  417 */         return this.defRetValue; 
/*  418 */       if (this.strategy.equals(k, curr)) {
/*  419 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public long get(char k) {
/*  425 */     if (this.strategy.equals(k, false)) {
/*  426 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  428 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  431 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  432 */       return this.defRetValue; 
/*  433 */     if (this.strategy.equals(k, curr)) {
/*  434 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  437 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  438 */         return this.defRetValue; 
/*  439 */       if (this.strategy.equals(k, curr)) {
/*  440 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(char k) {
/*  446 */     if (this.strategy.equals(k, false)) {
/*  447 */       return this.containsNullKey;
/*      */     }
/*  449 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  452 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  453 */       return false; 
/*  454 */     if (this.strategy.equals(k, curr)) {
/*  455 */       return true;
/*      */     }
/*      */     while (true) {
/*  458 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  459 */         return false; 
/*  460 */       if (this.strategy.equals(k, curr))
/*  461 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(long v) {
/*  466 */     long[] value = this.value;
/*  467 */     char[] key = this.key;
/*  468 */     if (this.containsNullKey && value[this.n] == v)
/*  469 */       return true; 
/*  470 */     for (int i = this.n; i-- != 0;) {
/*  471 */       if (key[i] != '\000' && value[i] == v)
/*  472 */         return true; 
/*  473 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long getOrDefault(char k, long defaultValue) {
/*  479 */     if (this.strategy.equals(k, false)) {
/*  480 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  482 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  485 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  486 */       return defaultValue; 
/*  487 */     if (this.strategy.equals(k, curr)) {
/*  488 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  491 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  492 */         return defaultValue; 
/*  493 */       if (this.strategy.equals(k, curr)) {
/*  494 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public long putIfAbsent(char k, long v) {
/*  500 */     int pos = find(k);
/*  501 */     if (pos >= 0)
/*  502 */       return this.value[pos]; 
/*  503 */     insert(-pos - 1, k, v);
/*  504 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(char k, long v) {
/*  510 */     if (this.strategy.equals(k, false)) {
/*  511 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  512 */         removeNullEntry();
/*  513 */         return true;
/*      */       } 
/*  515 */       return false;
/*      */     } 
/*      */     
/*  518 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  521 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  522 */       return false; 
/*  523 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  524 */       removeEntry(pos);
/*  525 */       return true;
/*      */     } 
/*      */     while (true) {
/*  528 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  529 */         return false; 
/*  530 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  531 */         removeEntry(pos);
/*  532 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(char k, long oldValue, long v) {
/*  539 */     int pos = find(k);
/*  540 */     if (pos < 0 || oldValue != this.value[pos])
/*  541 */       return false; 
/*  542 */     this.value[pos] = v;
/*  543 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public long replace(char k, long v) {
/*  548 */     int pos = find(k);
/*  549 */     if (pos < 0)
/*  550 */       return this.defRetValue; 
/*  551 */     long oldValue = this.value[pos];
/*  552 */     this.value[pos] = v;
/*  553 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(char k, IntToLongFunction mappingFunction) {
/*  558 */     Objects.requireNonNull(mappingFunction);
/*  559 */     int pos = find(k);
/*  560 */     if (pos >= 0)
/*  561 */       return this.value[pos]; 
/*  562 */     long newValue = mappingFunction.applyAsLong(k);
/*  563 */     insert(-pos - 1, k, newValue);
/*  564 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsentNullable(char k, IntFunction<? extends Long> mappingFunction) {
/*  570 */     Objects.requireNonNull(mappingFunction);
/*  571 */     int pos = find(k);
/*  572 */     if (pos >= 0)
/*  573 */       return this.value[pos]; 
/*  574 */     Long newValue = mappingFunction.apply(k);
/*  575 */     if (newValue == null)
/*  576 */       return this.defRetValue; 
/*  577 */     long v = newValue.longValue();
/*  578 */     insert(-pos - 1, k, v);
/*  579 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfPresent(char k, BiFunction<? super Character, ? super Long, ? extends Long> remappingFunction) {
/*  585 */     Objects.requireNonNull(remappingFunction);
/*  586 */     int pos = find(k);
/*  587 */     if (pos < 0)
/*  588 */       return this.defRetValue; 
/*  589 */     Long newValue = remappingFunction.apply(Character.valueOf(k), Long.valueOf(this.value[pos]));
/*  590 */     if (newValue == null) {
/*  591 */       if (this.strategy.equals(k, false)) {
/*  592 */         removeNullEntry();
/*      */       } else {
/*  594 */         removeEntry(pos);
/*  595 */       }  return this.defRetValue;
/*      */     } 
/*  597 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long compute(char k, BiFunction<? super Character, ? super Long, ? extends Long> remappingFunction) {
/*  603 */     Objects.requireNonNull(remappingFunction);
/*  604 */     int pos = find(k);
/*  605 */     Long newValue = remappingFunction.apply(Character.valueOf(k), (pos >= 0) ? Long.valueOf(this.value[pos]) : null);
/*  606 */     if (newValue == null) {
/*  607 */       if (pos >= 0)
/*  608 */         if (this.strategy.equals(k, false)) {
/*  609 */           removeNullEntry();
/*      */         } else {
/*  611 */           removeEntry(pos);
/*      */         }  
/*  613 */       return this.defRetValue;
/*      */     } 
/*  615 */     long newVal = newValue.longValue();
/*  616 */     if (pos < 0) {
/*  617 */       insert(-pos - 1, k, newVal);
/*  618 */       return newVal;
/*      */     } 
/*  620 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long merge(char k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  626 */     Objects.requireNonNull(remappingFunction);
/*  627 */     int pos = find(k);
/*  628 */     if (pos < 0) {
/*  629 */       insert(-pos - 1, k, v);
/*  630 */       return v;
/*      */     } 
/*  632 */     Long newValue = remappingFunction.apply(Long.valueOf(this.value[pos]), Long.valueOf(v));
/*  633 */     if (newValue == null) {
/*  634 */       if (this.strategy.equals(k, false)) {
/*  635 */         removeNullEntry();
/*      */       } else {
/*  637 */         removeEntry(pos);
/*  638 */       }  return this.defRetValue;
/*      */     } 
/*  640 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
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
/*      */     implements Char2LongMap.Entry, Map.Entry<Character, Long>
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
/*  681 */       return Char2LongOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public long getLongValue() {
/*  685 */       return Char2LongOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public long setValue(long v) {
/*  689 */       long oldValue = Char2LongOpenCustomHashMap.this.value[this.index];
/*  690 */       Char2LongOpenCustomHashMap.this.value[this.index] = v;
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
/*  701 */       return Character.valueOf(Char2LongOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getValue() {
/*  711 */       return Long.valueOf(Char2LongOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long setValue(Long v) {
/*  721 */       return Long.valueOf(setValue(v.longValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  726 */       if (!(o instanceof Map.Entry))
/*  727 */         return false; 
/*  728 */       Map.Entry<Character, Long> e = (Map.Entry<Character, Long>)o;
/*  729 */       return (Char2LongOpenCustomHashMap.this.strategy.equals(Char2LongOpenCustomHashMap.this.key[this.index], ((Character)e.getKey()).charValue()) && Char2LongOpenCustomHashMap.this.value[this.index] == ((Long)e
/*  730 */         .getValue()).longValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  734 */       return Char2LongOpenCustomHashMap.this.strategy.hashCode(Char2LongOpenCustomHashMap.this.key[this.index]) ^ HashCommon.long2int(Char2LongOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  738 */       return Char2LongOpenCustomHashMap.this.key[this.index] + "=>" + Char2LongOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  748 */     int pos = Char2LongOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  755 */     int last = -1;
/*      */     
/*  757 */     int c = Char2LongOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  761 */     boolean mustReturnNullKey = Char2LongOpenCustomHashMap.this.containsNullKey;
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
/*  776 */         return this.last = Char2LongOpenCustomHashMap.this.n;
/*      */       } 
/*  778 */       char[] key = Char2LongOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  780 */         if (--this.pos < 0) {
/*      */           
/*  782 */           this.last = Integer.MIN_VALUE;
/*  783 */           char k = this.wrapped.getChar(-this.pos - 1);
/*  784 */           int p = HashCommon.mix(Char2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Char2LongOpenCustomHashMap.this.mask;
/*  785 */           while (!Char2LongOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  786 */             p = p + 1 & Char2LongOpenCustomHashMap.this.mask; 
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
/*  804 */       char[] key = Char2LongOpenCustomHashMap.this.key; while (true) {
/*      */         char curr; int last;
/*  806 */         pos = (last = pos) + 1 & Char2LongOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  808 */           if ((curr = key[pos]) == '\000') {
/*  809 */             key[last] = Character.MIN_VALUE;
/*      */             return;
/*      */           } 
/*  812 */           int slot = HashCommon.mix(Char2LongOpenCustomHashMap.this.strategy.hashCode(curr)) & Char2LongOpenCustomHashMap.this.mask;
/*  813 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  815 */           pos = pos + 1 & Char2LongOpenCustomHashMap.this.mask;
/*      */         } 
/*  817 */         if (pos < last) {
/*  818 */           if (this.wrapped == null)
/*  819 */             this.wrapped = new CharArrayList(2); 
/*  820 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  822 */         key[last] = curr;
/*  823 */         Char2LongOpenCustomHashMap.this.value[last] = Char2LongOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  827 */       if (this.last == -1)
/*  828 */         throw new IllegalStateException(); 
/*  829 */       if (this.last == Char2LongOpenCustomHashMap.this.n) {
/*  830 */         Char2LongOpenCustomHashMap.this.containsNullKey = false;
/*  831 */       } else if (this.pos >= 0) {
/*  832 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  835 */         Char2LongOpenCustomHashMap.this.remove(this.wrapped.getChar(-this.pos - 1));
/*  836 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  839 */       Char2LongOpenCustomHashMap.this.size--;
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
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Char2LongMap.Entry> { private Char2LongOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Char2LongOpenCustomHashMap.MapEntry next() {
/*  855 */       return this.entry = new Char2LongOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  859 */       super.remove();
/*  860 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Char2LongMap.Entry> { private FastEntryIterator() {
/*  864 */       this.entry = new Char2LongOpenCustomHashMap.MapEntry();
/*      */     } private final Char2LongOpenCustomHashMap.MapEntry entry;
/*      */     public Char2LongOpenCustomHashMap.MapEntry next() {
/*  867 */       this.entry.index = nextEntry();
/*  868 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Char2LongMap.Entry> implements Char2LongMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Char2LongMap.Entry> iterator() {
/*  874 */       return new Char2LongOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Char2LongMap.Entry> fastIterator() {
/*  878 */       return new Char2LongOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  883 */       if (!(o instanceof Map.Entry))
/*  884 */         return false; 
/*  885 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  886 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/*  887 */         return false; 
/*  888 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/*  889 */         return false; 
/*  890 */       char k = ((Character)e.getKey()).charValue();
/*  891 */       long v = ((Long)e.getValue()).longValue();
/*  892 */       if (Char2LongOpenCustomHashMap.this.strategy.equals(k, false)) {
/*  893 */         return (Char2LongOpenCustomHashMap.this.containsNullKey && Char2LongOpenCustomHashMap.this.value[Char2LongOpenCustomHashMap.this.n] == v);
/*      */       }
/*  895 */       char[] key = Char2LongOpenCustomHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/*  898 */       if ((curr = key[pos = HashCommon.mix(Char2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Char2LongOpenCustomHashMap.this.mask]) == '\000')
/*  899 */         return false; 
/*  900 */       if (Char2LongOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  901 */         return (Char2LongOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  904 */         if ((curr = key[pos = pos + 1 & Char2LongOpenCustomHashMap.this.mask]) == '\000')
/*  905 */           return false; 
/*  906 */         if (Char2LongOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  907 */           return (Char2LongOpenCustomHashMap.this.value[pos] == v);
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
/*  918 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/*  919 */         return false; 
/*  920 */       char k = ((Character)e.getKey()).charValue();
/*  921 */       long v = ((Long)e.getValue()).longValue();
/*  922 */       if (Char2LongOpenCustomHashMap.this.strategy.equals(k, false)) {
/*  923 */         if (Char2LongOpenCustomHashMap.this.containsNullKey && Char2LongOpenCustomHashMap.this.value[Char2LongOpenCustomHashMap.this.n] == v) {
/*  924 */           Char2LongOpenCustomHashMap.this.removeNullEntry();
/*  925 */           return true;
/*      */         } 
/*  927 */         return false;
/*      */       } 
/*      */       
/*  930 */       char[] key = Char2LongOpenCustomHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/*  933 */       if ((curr = key[pos = HashCommon.mix(Char2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Char2LongOpenCustomHashMap.this.mask]) == '\000')
/*  934 */         return false; 
/*  935 */       if (Char2LongOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  936 */         if (Char2LongOpenCustomHashMap.this.value[pos] == v) {
/*  937 */           Char2LongOpenCustomHashMap.this.removeEntry(pos);
/*  938 */           return true;
/*      */         } 
/*  940 */         return false;
/*      */       } 
/*      */       while (true) {
/*  943 */         if ((curr = key[pos = pos + 1 & Char2LongOpenCustomHashMap.this.mask]) == '\000')
/*  944 */           return false; 
/*  945 */         if (Char2LongOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  946 */           Char2LongOpenCustomHashMap.this.value[pos] == v) {
/*  947 */           Char2LongOpenCustomHashMap.this.removeEntry(pos);
/*  948 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  955 */       return Char2LongOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  959 */       Char2LongOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Char2LongMap.Entry> consumer) {
/*  964 */       if (Char2LongOpenCustomHashMap.this.containsNullKey)
/*  965 */         consumer.accept(new AbstractChar2LongMap.BasicEntry(Char2LongOpenCustomHashMap.this.key[Char2LongOpenCustomHashMap.this.n], Char2LongOpenCustomHashMap.this.value[Char2LongOpenCustomHashMap.this.n])); 
/*  966 */       for (int pos = Char2LongOpenCustomHashMap.this.n; pos-- != 0;) {
/*  967 */         if (Char2LongOpenCustomHashMap.this.key[pos] != '\000')
/*  968 */           consumer.accept(new AbstractChar2LongMap.BasicEntry(Char2LongOpenCustomHashMap.this.key[pos], Char2LongOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Char2LongMap.Entry> consumer) {
/*  973 */       AbstractChar2LongMap.BasicEntry entry = new AbstractChar2LongMap.BasicEntry();
/*  974 */       if (Char2LongOpenCustomHashMap.this.containsNullKey) {
/*  975 */         entry.key = Char2LongOpenCustomHashMap.this.key[Char2LongOpenCustomHashMap.this.n];
/*  976 */         entry.value = Char2LongOpenCustomHashMap.this.value[Char2LongOpenCustomHashMap.this.n];
/*  977 */         consumer.accept(entry);
/*      */       } 
/*  979 */       for (int pos = Char2LongOpenCustomHashMap.this.n; pos-- != 0;) {
/*  980 */         if (Char2LongOpenCustomHashMap.this.key[pos] != '\000') {
/*  981 */           entry.key = Char2LongOpenCustomHashMap.this.key[pos];
/*  982 */           entry.value = Char2LongOpenCustomHashMap.this.value[pos];
/*  983 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Char2LongMap.FastEntrySet char2LongEntrySet() {
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
/* 1008 */       return Char2LongOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractCharSet { private KeySet() {}
/*      */     
/*      */     public CharIterator iterator() {
/* 1014 */       return new Char2LongOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1019 */       if (Char2LongOpenCustomHashMap.this.containsNullKey)
/* 1020 */         consumer.accept(Char2LongOpenCustomHashMap.this.key[Char2LongOpenCustomHashMap.this.n]); 
/* 1021 */       for (int pos = Char2LongOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1022 */         char k = Char2LongOpenCustomHashMap.this.key[pos];
/* 1023 */         if (k != '\000')
/* 1024 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1029 */       return Char2LongOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(char k) {
/* 1033 */       return Char2LongOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(char k) {
/* 1037 */       int oldSize = Char2LongOpenCustomHashMap.this.size;
/* 1038 */       Char2LongOpenCustomHashMap.this.remove(k);
/* 1039 */       return (Char2LongOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1043 */       Char2LongOpenCustomHashMap.this.clear();
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
/*      */     implements LongIterator
/*      */   {
/*      */     public long nextLong() {
/* 1067 */       return Char2LongOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public LongCollection values() {
/* 1072 */     if (this.values == null)
/* 1073 */       this.values = (LongCollection)new AbstractLongCollection()
/*      */         {
/*      */           public LongIterator iterator() {
/* 1076 */             return new Char2LongOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1080 */             return Char2LongOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(long v) {
/* 1084 */             return Char2LongOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1088 */             Char2LongOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(LongConsumer consumer)
/*      */           {
/* 1093 */             if (Char2LongOpenCustomHashMap.this.containsNullKey)
/* 1094 */               consumer.accept(Char2LongOpenCustomHashMap.this.value[Char2LongOpenCustomHashMap.this.n]); 
/* 1095 */             for (int pos = Char2LongOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1096 */               if (Char2LongOpenCustomHashMap.this.key[pos] != '\000')
/* 1097 */                 consumer.accept(Char2LongOpenCustomHashMap.this.value[pos]); 
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
/* 1166 */     long[] value = this.value;
/* 1167 */     int mask = newN - 1;
/* 1168 */     char[] newKey = new char[newN + 1];
/* 1169 */     long[] newValue = new long[newN + 1];
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
/*      */   public Char2LongOpenCustomHashMap clone() {
/*      */     Char2LongOpenCustomHashMap c;
/*      */     try {
/* 1201 */       c = (Char2LongOpenCustomHashMap)super.clone();
/* 1202 */     } catch (CloneNotSupportedException cantHappen) {
/* 1203 */       throw new InternalError();
/*      */     } 
/* 1205 */     c.keys = null;
/* 1206 */     c.values = null;
/* 1207 */     c.entries = null;
/* 1208 */     c.containsNullKey = this.containsNullKey;
/* 1209 */     c.key = (char[])this.key.clone();
/* 1210 */     c.value = (long[])this.value.clone();
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
/* 1230 */       t ^= HashCommon.long2int(this.value[i]);
/* 1231 */       h += t;
/* 1232 */       i++;
/*      */     } 
/*      */     
/* 1235 */     if (this.containsNullKey)
/* 1236 */       h += HashCommon.long2int(this.value[this.n]); 
/* 1237 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1240 */     char[] key = this.key;
/* 1241 */     long[] value = this.value;
/* 1242 */     MapIterator i = new MapIterator();
/* 1243 */     s.defaultWriteObject();
/* 1244 */     for (int j = this.size; j-- != 0; ) {
/* 1245 */       int e = i.nextEntry();
/* 1246 */       s.writeChar(key[e]);
/* 1247 */       s.writeLong(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1252 */     s.defaultReadObject();
/* 1253 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1254 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1255 */     this.mask = this.n - 1;
/* 1256 */     char[] key = this.key = new char[this.n + 1];
/* 1257 */     long[] value = this.value = new long[this.n + 1];
/*      */ 
/*      */     
/* 1260 */     for (int i = this.size; i-- != 0; ) {
/* 1261 */       int pos; char k = s.readChar();
/* 1262 */       long v = s.readLong();
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2LongOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */