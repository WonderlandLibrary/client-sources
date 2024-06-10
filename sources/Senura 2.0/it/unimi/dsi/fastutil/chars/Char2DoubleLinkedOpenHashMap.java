/*      */ package it.unimi.dsi.fastutil.chars;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.SortedSet;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.IntToDoubleFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Char2DoubleLinkedOpenHashMap
/*      */   extends AbstractChar2DoubleSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient char[] key;
/*      */   protected transient double[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*  107 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  112 */   protected transient int last = -1;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient long[] link;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient int n;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient int maxFill;
/*      */ 
/*      */ 
/*      */   
/*      */   protected final transient int minN;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int size;
/*      */ 
/*      */ 
/*      */   
/*      */   protected final float f;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient Char2DoubleSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient CharSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient DoubleCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2DoubleLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new char[this.n + 1];
/*  162 */     this.value = new double[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2DoubleLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2DoubleLinkedOpenHashMap() {
/*  180 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2DoubleLinkedOpenHashMap(Map<? extends Character, ? extends Double> m, float f) {
/*  191 */     this(m.size(), f);
/*  192 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2DoubleLinkedOpenHashMap(Map<? extends Character, ? extends Double> m) {
/*  202 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2DoubleLinkedOpenHashMap(Char2DoubleMap m, float f) {
/*  213 */     this(m.size(), f);
/*  214 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2DoubleLinkedOpenHashMap(Char2DoubleMap m) {
/*  224 */     this(m, 0.75F);
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
/*      */   public Char2DoubleLinkedOpenHashMap(char[] k, double[] v, float f) {
/*  239 */     this(k.length, f);
/*  240 */     if (k.length != v.length) {
/*  241 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  243 */     for (int i = 0; i < k.length; i++) {
/*  244 */       put(k[i], v[i]);
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
/*      */   public Char2DoubleLinkedOpenHashMap(char[] k, double[] v) {
/*  258 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  261 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  264 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  265 */     if (needed > this.n)
/*  266 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  269 */     int needed = (int)Math.min(1073741824L, 
/*  270 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  271 */     if (needed > this.n)
/*  272 */       rehash(needed); 
/*      */   }
/*      */   private double removeEntry(int pos) {
/*  275 */     double oldValue = this.value[pos];
/*  276 */     this.size--;
/*  277 */     fixPointers(pos);
/*  278 */     shiftKeys(pos);
/*  279 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  280 */       rehash(this.n / 2); 
/*  281 */     return oldValue;
/*      */   }
/*      */   private double removeNullEntry() {
/*  284 */     this.containsNullKey = false;
/*  285 */     double oldValue = this.value[this.n];
/*  286 */     this.size--;
/*  287 */     fixPointers(this.n);
/*  288 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  289 */       rehash(this.n / 2); 
/*  290 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Character, ? extends Double> m) {
/*  294 */     if (this.f <= 0.5D) {
/*  295 */       ensureCapacity(m.size());
/*      */     } else {
/*  297 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  299 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(char k) {
/*  303 */     if (k == '\000') {
/*  304 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  306 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  309 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  310 */       return -(pos + 1); 
/*  311 */     if (k == curr) {
/*  312 */       return pos;
/*      */     }
/*      */     while (true) {
/*  315 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  316 */         return -(pos + 1); 
/*  317 */       if (k == curr)
/*  318 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, char k, double v) {
/*  322 */     if (pos == this.n)
/*  323 */       this.containsNullKey = true; 
/*  324 */     this.key[pos] = k;
/*  325 */     this.value[pos] = v;
/*  326 */     if (this.size == 0) {
/*  327 */       this.first = this.last = pos;
/*      */       
/*  329 */       this.link[pos] = -1L;
/*      */     } else {
/*  331 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  332 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  333 */       this.last = pos;
/*      */     } 
/*  335 */     if (this.size++ >= this.maxFill) {
/*  336 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public double put(char k, double v) {
/*  342 */     int pos = find(k);
/*  343 */     if (pos < 0) {
/*  344 */       insert(-pos - 1, k, v);
/*  345 */       return this.defRetValue;
/*      */     } 
/*  347 */     double oldValue = this.value[pos];
/*  348 */     this.value[pos] = v;
/*  349 */     return oldValue;
/*      */   }
/*      */   private double addToValue(int pos, double incr) {
/*  352 */     double oldValue = this.value[pos];
/*  353 */     this.value[pos] = oldValue + incr;
/*  354 */     return oldValue;
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
/*      */   public double addTo(char k, double incr) {
/*      */     int pos;
/*  374 */     if (k == '\000') {
/*  375 */       if (this.containsNullKey)
/*  376 */         return addToValue(this.n, incr); 
/*  377 */       pos = this.n;
/*  378 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  381 */       char[] key = this.key;
/*      */       char curr;
/*  383 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != '\000') {
/*  384 */         if (curr == k)
/*  385 */           return addToValue(pos, incr); 
/*  386 */         while ((curr = key[pos = pos + 1 & this.mask]) != '\000') {
/*  387 */           if (curr == k)
/*  388 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  391 */     }  this.key[pos] = k;
/*  392 */     this.value[pos] = this.defRetValue + incr;
/*  393 */     if (this.size == 0) {
/*  394 */       this.first = this.last = pos;
/*      */       
/*  396 */       this.link[pos] = -1L;
/*      */     } else {
/*  398 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  399 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  400 */       this.last = pos;
/*      */     } 
/*  402 */     if (this.size++ >= this.maxFill) {
/*  403 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  406 */     return this.defRetValue;
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
/*  419 */     char[] key = this.key; while (true) {
/*      */       char curr; int last;
/*  421 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  423 */         if ((curr = key[pos]) == '\000') {
/*  424 */           key[last] = Character.MIN_VALUE;
/*      */           return;
/*      */         } 
/*  427 */         int slot = HashCommon.mix(curr) & this.mask;
/*  428 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  430 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  432 */       key[last] = curr;
/*  433 */       this.value[last] = this.value[pos];
/*  434 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double remove(char k) {
/*  440 */     if (k == '\000') {
/*  441 */       if (this.containsNullKey)
/*  442 */         return removeNullEntry(); 
/*  443 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  446 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  449 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  450 */       return this.defRetValue; 
/*  451 */     if (k == curr)
/*  452 */       return removeEntry(pos); 
/*      */     while (true) {
/*  454 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  455 */         return this.defRetValue; 
/*  456 */       if (k == curr)
/*  457 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private double setValue(int pos, double v) {
/*  461 */     double oldValue = this.value[pos];
/*  462 */     this.value[pos] = v;
/*  463 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double removeFirstDouble() {
/*  474 */     if (this.size == 0)
/*  475 */       throw new NoSuchElementException(); 
/*  476 */     int pos = this.first;
/*      */     
/*  478 */     this.first = (int)this.link[pos];
/*  479 */     if (0 <= this.first)
/*      */     {
/*  481 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  483 */     this.size--;
/*  484 */     double v = this.value[pos];
/*  485 */     if (pos == this.n) {
/*  486 */       this.containsNullKey = false;
/*      */     } else {
/*  488 */       shiftKeys(pos);
/*  489 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  490 */       rehash(this.n / 2); 
/*  491 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double removeLastDouble() {
/*  501 */     if (this.size == 0)
/*  502 */       throw new NoSuchElementException(); 
/*  503 */     int pos = this.last;
/*      */     
/*  505 */     this.last = (int)(this.link[pos] >>> 32L);
/*  506 */     if (0 <= this.last)
/*      */     {
/*  508 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  510 */     this.size--;
/*  511 */     double v = this.value[pos];
/*  512 */     if (pos == this.n) {
/*  513 */       this.containsNullKey = false;
/*      */     } else {
/*  515 */       shiftKeys(pos);
/*  516 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  517 */       rehash(this.n / 2); 
/*  518 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  521 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  523 */     if (this.last == i) {
/*  524 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  526 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  528 */       long linki = this.link[i];
/*  529 */       int prev = (int)(linki >>> 32L);
/*  530 */       int next = (int)linki;
/*  531 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  532 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  534 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  535 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  536 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  539 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  541 */     if (this.first == i) {
/*  542 */       this.first = (int)this.link[i];
/*      */       
/*  544 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  546 */       long linki = this.link[i];
/*  547 */       int prev = (int)(linki >>> 32L);
/*  548 */       int next = (int)linki;
/*  549 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  550 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  552 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  553 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  554 */     this.last = i;
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
/*      */   public double getAndMoveToFirst(char k) {
/*  566 */     if (k == '\000') {
/*  567 */       if (this.containsNullKey) {
/*  568 */         moveIndexToFirst(this.n);
/*  569 */         return this.value[this.n];
/*      */       } 
/*  571 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  574 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  577 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  578 */       return this.defRetValue; 
/*  579 */     if (k == curr) {
/*  580 */       moveIndexToFirst(pos);
/*  581 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  585 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  586 */         return this.defRetValue; 
/*  587 */       if (k == curr) {
/*  588 */         moveIndexToFirst(pos);
/*  589 */         return this.value[pos];
/*      */       } 
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
/*      */   public double getAndMoveToLast(char k) {
/*  603 */     if (k == '\000') {
/*  604 */       if (this.containsNullKey) {
/*  605 */         moveIndexToLast(this.n);
/*  606 */         return this.value[this.n];
/*      */       } 
/*  608 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  611 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  614 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  615 */       return this.defRetValue; 
/*  616 */     if (k == curr) {
/*  617 */       moveIndexToLast(pos);
/*  618 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  622 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  623 */         return this.defRetValue; 
/*  624 */       if (k == curr) {
/*  625 */         moveIndexToLast(pos);
/*  626 */         return this.value[pos];
/*      */       } 
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
/*      */   public double putAndMoveToFirst(char k, double v) {
/*      */     int pos;
/*  643 */     if (k == '\000') {
/*  644 */       if (this.containsNullKey) {
/*  645 */         moveIndexToFirst(this.n);
/*  646 */         return setValue(this.n, v);
/*      */       } 
/*  648 */       this.containsNullKey = true;
/*  649 */       pos = this.n;
/*      */     } else {
/*      */       
/*  652 */       char[] key = this.key;
/*      */       char curr;
/*  654 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != '\000') {
/*  655 */         if (curr == k) {
/*  656 */           moveIndexToFirst(pos);
/*  657 */           return setValue(pos, v);
/*      */         } 
/*  659 */         while ((curr = key[pos = pos + 1 & this.mask]) != '\000') {
/*  660 */           if (curr == k) {
/*  661 */             moveIndexToFirst(pos);
/*  662 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  666 */     }  this.key[pos] = k;
/*  667 */     this.value[pos] = v;
/*  668 */     if (this.size == 0) {
/*  669 */       this.first = this.last = pos;
/*      */       
/*  671 */       this.link[pos] = -1L;
/*      */     } else {
/*  673 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  674 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  675 */       this.first = pos;
/*      */     } 
/*  677 */     if (this.size++ >= this.maxFill) {
/*  678 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  681 */     return this.defRetValue;
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
/*      */   public double putAndMoveToLast(char k, double v) {
/*      */     int pos;
/*  696 */     if (k == '\000') {
/*  697 */       if (this.containsNullKey) {
/*  698 */         moveIndexToLast(this.n);
/*  699 */         return setValue(this.n, v);
/*      */       } 
/*  701 */       this.containsNullKey = true;
/*  702 */       pos = this.n;
/*      */     } else {
/*      */       
/*  705 */       char[] key = this.key;
/*      */       char curr;
/*  707 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != '\000') {
/*  708 */         if (curr == k) {
/*  709 */           moveIndexToLast(pos);
/*  710 */           return setValue(pos, v);
/*      */         } 
/*  712 */         while ((curr = key[pos = pos + 1 & this.mask]) != '\000') {
/*  713 */           if (curr == k) {
/*  714 */             moveIndexToLast(pos);
/*  715 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  719 */     }  this.key[pos] = k;
/*  720 */     this.value[pos] = v;
/*  721 */     if (this.size == 0) {
/*  722 */       this.first = this.last = pos;
/*      */       
/*  724 */       this.link[pos] = -1L;
/*      */     } else {
/*  726 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  727 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  728 */       this.last = pos;
/*      */     } 
/*  730 */     if (this.size++ >= this.maxFill) {
/*  731 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  734 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public double get(char k) {
/*  739 */     if (k == '\000') {
/*  740 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  742 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  745 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  746 */       return this.defRetValue; 
/*  747 */     if (k == curr) {
/*  748 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  751 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  752 */         return this.defRetValue; 
/*  753 */       if (k == curr) {
/*  754 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(char k) {
/*  760 */     if (k == '\000') {
/*  761 */       return this.containsNullKey;
/*      */     }
/*  763 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  766 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  767 */       return false; 
/*  768 */     if (k == curr) {
/*  769 */       return true;
/*      */     }
/*      */     while (true) {
/*  772 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  773 */         return false; 
/*  774 */       if (k == curr)
/*  775 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(double v) {
/*  780 */     double[] value = this.value;
/*  781 */     char[] key = this.key;
/*  782 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v))
/*  783 */       return true; 
/*  784 */     for (int i = this.n; i-- != 0;) {
/*  785 */       if (key[i] != '\000' && Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v))
/*  786 */         return true; 
/*  787 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(char k, double defaultValue) {
/*  793 */     if (k == '\000') {
/*  794 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  796 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  799 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  800 */       return defaultValue; 
/*  801 */     if (k == curr) {
/*  802 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  805 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  806 */         return defaultValue; 
/*  807 */       if (k == curr) {
/*  808 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double putIfAbsent(char k, double v) {
/*  814 */     int pos = find(k);
/*  815 */     if (pos >= 0)
/*  816 */       return this.value[pos]; 
/*  817 */     insert(-pos - 1, k, v);
/*  818 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(char k, double v) {
/*  824 */     if (k == '\000') {
/*  825 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
/*  826 */         removeNullEntry();
/*  827 */         return true;
/*      */       } 
/*  829 */       return false;
/*      */     } 
/*      */     
/*  832 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  835 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  836 */       return false; 
/*  837 */     if (k == curr && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  838 */       removeEntry(pos);
/*  839 */       return true;
/*      */     } 
/*      */     while (true) {
/*  842 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  843 */         return false; 
/*  844 */       if (k == curr && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  845 */         removeEntry(pos);
/*  846 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(char k, double oldValue, double v) {
/*  853 */     int pos = find(k);
/*  854 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos]))
/*  855 */       return false; 
/*  856 */     this.value[pos] = v;
/*  857 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public double replace(char k, double v) {
/*  862 */     int pos = find(k);
/*  863 */     if (pos < 0)
/*  864 */       return this.defRetValue; 
/*  865 */     double oldValue = this.value[pos];
/*  866 */     this.value[pos] = v;
/*  867 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(char k, IntToDoubleFunction mappingFunction) {
/*  872 */     Objects.requireNonNull(mappingFunction);
/*  873 */     int pos = find(k);
/*  874 */     if (pos >= 0)
/*  875 */       return this.value[pos]; 
/*  876 */     double newValue = mappingFunction.applyAsDouble(k);
/*  877 */     insert(-pos - 1, k, newValue);
/*  878 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsentNullable(char k, IntFunction<? extends Double> mappingFunction) {
/*  884 */     Objects.requireNonNull(mappingFunction);
/*  885 */     int pos = find(k);
/*  886 */     if (pos >= 0)
/*  887 */       return this.value[pos]; 
/*  888 */     Double newValue = mappingFunction.apply(k);
/*  889 */     if (newValue == null)
/*  890 */       return this.defRetValue; 
/*  891 */     double v = newValue.doubleValue();
/*  892 */     insert(-pos - 1, k, v);
/*  893 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfPresent(char k, BiFunction<? super Character, ? super Double, ? extends Double> remappingFunction) {
/*  899 */     Objects.requireNonNull(remappingFunction);
/*  900 */     int pos = find(k);
/*  901 */     if (pos < 0)
/*  902 */       return this.defRetValue; 
/*  903 */     Double newValue = remappingFunction.apply(Character.valueOf(k), Double.valueOf(this.value[pos]));
/*  904 */     if (newValue == null) {
/*  905 */       if (k == '\000') {
/*  906 */         removeNullEntry();
/*      */       } else {
/*  908 */         removeEntry(pos);
/*  909 */       }  return this.defRetValue;
/*      */     } 
/*  911 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double compute(char k, BiFunction<? super Character, ? super Double, ? extends Double> remappingFunction) {
/*  917 */     Objects.requireNonNull(remappingFunction);
/*  918 */     int pos = find(k);
/*  919 */     Double newValue = remappingFunction.apply(Character.valueOf(k), 
/*  920 */         (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  921 */     if (newValue == null) {
/*  922 */       if (pos >= 0)
/*  923 */         if (k == '\000') {
/*  924 */           removeNullEntry();
/*      */         } else {
/*  926 */           removeEntry(pos);
/*      */         }  
/*  928 */       return this.defRetValue;
/*      */     } 
/*  930 */     double newVal = newValue.doubleValue();
/*  931 */     if (pos < 0) {
/*  932 */       insert(-pos - 1, k, newVal);
/*  933 */       return newVal;
/*      */     } 
/*  935 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double merge(char k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  941 */     Objects.requireNonNull(remappingFunction);
/*  942 */     int pos = find(k);
/*  943 */     if (pos < 0) {
/*  944 */       insert(-pos - 1, k, v);
/*  945 */       return v;
/*      */     } 
/*  947 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  948 */     if (newValue == null) {
/*  949 */       if (k == '\000') {
/*  950 */         removeNullEntry();
/*      */       } else {
/*  952 */         removeEntry(pos);
/*  953 */       }  return this.defRetValue;
/*      */     } 
/*  955 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  966 */     if (this.size == 0)
/*      */       return; 
/*  968 */     this.size = 0;
/*  969 */     this.containsNullKey = false;
/*  970 */     Arrays.fill(this.key, false);
/*  971 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  975 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  979 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Char2DoubleMap.Entry, Map.Entry<Character, Double>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  991 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public char getCharKey() {
/*  997 */       return Char2DoubleLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public double getDoubleValue() {
/* 1001 */       return Char2DoubleLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public double setValue(double v) {
/* 1005 */       double oldValue = Char2DoubleLinkedOpenHashMap.this.value[this.index];
/* 1006 */       Char2DoubleLinkedOpenHashMap.this.value[this.index] = v;
/* 1007 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getKey() {
/* 1017 */       return Character.valueOf(Char2DoubleLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getValue() {
/* 1027 */       return Double.valueOf(Char2DoubleLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double setValue(Double v) {
/* 1037 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1042 */       if (!(o instanceof Map.Entry))
/* 1043 */         return false; 
/* 1044 */       Map.Entry<Character, Double> e = (Map.Entry<Character, Double>)o;
/* 1045 */       return (Char2DoubleLinkedOpenHashMap.this.key[this.index] == ((Character)e.getKey()).charValue() && 
/* 1046 */         Double.doubleToLongBits(Char2DoubleLinkedOpenHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1050 */       return Char2DoubleLinkedOpenHashMap.this.key[this.index] ^ HashCommon.double2int(Char2DoubleLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1054 */       return Char2DoubleLinkedOpenHashMap.this.key[this.index] + "=>" + Char2DoubleLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int i) {
/* 1065 */     if (this.size == 0) {
/* 1066 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1069 */     if (this.first == i) {
/* 1070 */       this.first = (int)this.link[i];
/* 1071 */       if (0 <= this.first)
/*      */       {
/* 1073 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1077 */     if (this.last == i) {
/* 1078 */       this.last = (int)(this.link[i] >>> 32L);
/* 1079 */       if (0 <= this.last)
/*      */       {
/* 1081 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1085 */     long linki = this.link[i];
/* 1086 */     int prev = (int)(linki >>> 32L);
/* 1087 */     int next = (int)linki;
/* 1088 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1089 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*      */   protected void fixPointers(int s, int d) {
/* 1102 */     if (this.size == 1) {
/* 1103 */       this.first = this.last = d;
/*      */       
/* 1105 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1108 */     if (this.first == s) {
/* 1109 */       this.first = d;
/* 1110 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1111 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1114 */     if (this.last == s) {
/* 1115 */       this.last = d;
/* 1116 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1117 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1120 */     long links = this.link[s];
/* 1121 */     int prev = (int)(links >>> 32L);
/* 1122 */     int next = (int)links;
/* 1123 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1124 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1125 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char firstCharKey() {
/* 1134 */     if (this.size == 0)
/* 1135 */       throw new NoSuchElementException(); 
/* 1136 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char lastCharKey() {
/* 1145 */     if (this.size == 0)
/* 1146 */       throw new NoSuchElementException(); 
/* 1147 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2DoubleSortedMap tailMap(char from) {
/* 1156 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2DoubleSortedMap headMap(char to) {
/* 1165 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2DoubleSortedMap subMap(char from, char to) {
/* 1174 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharComparator comparator() {
/* 1183 */     return null;
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
/*      */   private class MapIterator
/*      */   {
/* 1198 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1204 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1209 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1215 */     int index = -1;
/*      */     protected MapIterator() {
/* 1217 */       this.next = Char2DoubleLinkedOpenHashMap.this.first;
/* 1218 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(char from) {
/* 1221 */       if (from == '\000') {
/* 1222 */         if (Char2DoubleLinkedOpenHashMap.this.containsNullKey) {
/* 1223 */           this.next = (int)Char2DoubleLinkedOpenHashMap.this.link[Char2DoubleLinkedOpenHashMap.this.n];
/* 1224 */           this.prev = Char2DoubleLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1227 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1229 */       if (Char2DoubleLinkedOpenHashMap.this.key[Char2DoubleLinkedOpenHashMap.this.last] == from) {
/* 1230 */         this.prev = Char2DoubleLinkedOpenHashMap.this.last;
/* 1231 */         this.index = Char2DoubleLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1235 */       int pos = HashCommon.mix(from) & Char2DoubleLinkedOpenHashMap.this.mask;
/*      */       
/* 1237 */       while (Char2DoubleLinkedOpenHashMap.this.key[pos] != '\000') {
/* 1238 */         if (Char2DoubleLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1240 */           this.next = (int)Char2DoubleLinkedOpenHashMap.this.link[pos];
/* 1241 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1244 */         pos = pos + 1 & Char2DoubleLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1246 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1249 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1252 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1255 */       if (this.index >= 0)
/*      */         return; 
/* 1257 */       if (this.prev == -1) {
/* 1258 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1261 */       if (this.next == -1) {
/* 1262 */         this.index = Char2DoubleLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1265 */       int pos = Char2DoubleLinkedOpenHashMap.this.first;
/* 1266 */       this.index = 1;
/* 1267 */       while (pos != this.prev) {
/* 1268 */         pos = (int)Char2DoubleLinkedOpenHashMap.this.link[pos];
/* 1269 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1273 */       ensureIndexKnown();
/* 1274 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1277 */       ensureIndexKnown();
/* 1278 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1281 */       if (!hasNext())
/* 1282 */         throw new NoSuchElementException(); 
/* 1283 */       this.curr = this.next;
/* 1284 */       this.next = (int)Char2DoubleLinkedOpenHashMap.this.link[this.curr];
/* 1285 */       this.prev = this.curr;
/* 1286 */       if (this.index >= 0)
/* 1287 */         this.index++; 
/* 1288 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1291 */       if (!hasPrevious())
/* 1292 */         throw new NoSuchElementException(); 
/* 1293 */       this.curr = this.prev;
/* 1294 */       this.prev = (int)(Char2DoubleLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1295 */       this.next = this.curr;
/* 1296 */       if (this.index >= 0)
/* 1297 */         this.index--; 
/* 1298 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1301 */       ensureIndexKnown();
/* 1302 */       if (this.curr == -1)
/* 1303 */         throw new IllegalStateException(); 
/* 1304 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1309 */         this.index--;
/* 1310 */         this.prev = (int)(Char2DoubleLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1312 */         this.next = (int)Char2DoubleLinkedOpenHashMap.this.link[this.curr];
/* 1313 */       }  Char2DoubleLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1318 */       if (this.prev == -1) {
/* 1319 */         Char2DoubleLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1321 */         Char2DoubleLinkedOpenHashMap.this.link[this.prev] = Char2DoubleLinkedOpenHashMap.this.link[this.prev] ^ (Char2DoubleLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1322 */       }  if (this.next == -1) {
/* 1323 */         Char2DoubleLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1325 */         Char2DoubleLinkedOpenHashMap.this.link[this.next] = Char2DoubleLinkedOpenHashMap.this.link[this.next] ^ (Char2DoubleLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1326 */       }  int pos = this.curr;
/* 1327 */       this.curr = -1;
/* 1328 */       if (pos == Char2DoubleLinkedOpenHashMap.this.n) {
/* 1329 */         Char2DoubleLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1332 */         char[] key = Char2DoubleLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           char curr;
/*      */           int last;
/* 1336 */           pos = (last = pos) + 1 & Char2DoubleLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1338 */             if ((curr = key[pos]) == '\000') {
/* 1339 */               key[last] = Character.MIN_VALUE;
/*      */               return;
/*      */             } 
/* 1342 */             int slot = HashCommon.mix(curr) & Char2DoubleLinkedOpenHashMap.this.mask;
/* 1343 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1345 */             pos = pos + 1 & Char2DoubleLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1347 */           key[last] = curr;
/* 1348 */           Char2DoubleLinkedOpenHashMap.this.value[last] = Char2DoubleLinkedOpenHashMap.this.value[pos];
/* 1349 */           if (this.next == pos)
/* 1350 */             this.next = last; 
/* 1351 */           if (this.prev == pos)
/* 1352 */             this.prev = last; 
/* 1353 */           Char2DoubleLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1358 */       int i = n;
/* 1359 */       while (i-- != 0 && hasNext())
/* 1360 */         nextEntry(); 
/* 1361 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1364 */       int i = n;
/* 1365 */       while (i-- != 0 && hasPrevious())
/* 1366 */         previousEntry(); 
/* 1367 */       return n - i - 1;
/*      */     }
/*      */     public void set(Char2DoubleMap.Entry ok) {
/* 1370 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Char2DoubleMap.Entry ok) {
/* 1373 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Char2DoubleMap.Entry> { private Char2DoubleLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(char from) {
/* 1381 */       super(from);
/*      */     }
/*      */     
/*      */     public Char2DoubleLinkedOpenHashMap.MapEntry next() {
/* 1385 */       return this.entry = new Char2DoubleLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Char2DoubleLinkedOpenHashMap.MapEntry previous() {
/* 1389 */       return this.entry = new Char2DoubleLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1393 */       super.remove();
/* 1394 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1398 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Char2DoubleMap.Entry> { final Char2DoubleLinkedOpenHashMap.MapEntry entry = new Char2DoubleLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(char from) {
/* 1402 */       super(from);
/*      */     }
/*      */     
/*      */     public Char2DoubleLinkedOpenHashMap.MapEntry next() {
/* 1406 */       this.entry.index = nextEntry();
/* 1407 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Char2DoubleLinkedOpenHashMap.MapEntry previous() {
/* 1411 */       this.entry.index = previousEntry();
/* 1412 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Char2DoubleMap.Entry> implements Char2DoubleSortedMap.FastSortedEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Char2DoubleMap.Entry> iterator() {
/* 1420 */       return (ObjectBidirectionalIterator<Char2DoubleMap.Entry>)new Char2DoubleLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Char2DoubleMap.Entry> comparator() {
/* 1424 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Char2DoubleMap.Entry> subSet(Char2DoubleMap.Entry fromElement, Char2DoubleMap.Entry toElement) {
/* 1429 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Char2DoubleMap.Entry> headSet(Char2DoubleMap.Entry toElement) {
/* 1433 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Char2DoubleMap.Entry> tailSet(Char2DoubleMap.Entry fromElement) {
/* 1437 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Char2DoubleMap.Entry first() {
/* 1441 */       if (Char2DoubleLinkedOpenHashMap.this.size == 0)
/* 1442 */         throw new NoSuchElementException(); 
/* 1443 */       return new Char2DoubleLinkedOpenHashMap.MapEntry(Char2DoubleLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Char2DoubleMap.Entry last() {
/* 1447 */       if (Char2DoubleLinkedOpenHashMap.this.size == 0)
/* 1448 */         throw new NoSuchElementException(); 
/* 1449 */       return new Char2DoubleLinkedOpenHashMap.MapEntry(Char2DoubleLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1454 */       if (!(o instanceof Map.Entry))
/* 1455 */         return false; 
/* 1456 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1457 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/* 1458 */         return false; 
/* 1459 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1460 */         return false; 
/* 1461 */       char k = ((Character)e.getKey()).charValue();
/* 1462 */       double v = ((Double)e.getValue()).doubleValue();
/* 1463 */       if (k == '\000') {
/* 1464 */         return (Char2DoubleLinkedOpenHashMap.this.containsNullKey && 
/* 1465 */           Double.doubleToLongBits(Char2DoubleLinkedOpenHashMap.this.value[Char2DoubleLinkedOpenHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       }
/* 1467 */       char[] key = Char2DoubleLinkedOpenHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/* 1470 */       if ((curr = key[pos = HashCommon.mix(k) & Char2DoubleLinkedOpenHashMap.this.mask]) == '\000')
/* 1471 */         return false; 
/* 1472 */       if (k == curr) {
/* 1473 */         return (Double.doubleToLongBits(Char2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       }
/*      */       while (true) {
/* 1476 */         if ((curr = key[pos = pos + 1 & Char2DoubleLinkedOpenHashMap.this.mask]) == '\000')
/* 1477 */           return false; 
/* 1478 */         if (k == curr) {
/* 1479 */           return (Double.doubleToLongBits(Char2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1485 */       if (!(o instanceof Map.Entry))
/* 1486 */         return false; 
/* 1487 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1488 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/* 1489 */         return false; 
/* 1490 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1491 */         return false; 
/* 1492 */       char k = ((Character)e.getKey()).charValue();
/* 1493 */       double v = ((Double)e.getValue()).doubleValue();
/* 1494 */       if (k == '\000') {
/* 1495 */         if (Char2DoubleLinkedOpenHashMap.this.containsNullKey && Double.doubleToLongBits(Char2DoubleLinkedOpenHashMap.this.value[Char2DoubleLinkedOpenHashMap.this.n]) == Double.doubleToLongBits(v)) {
/* 1496 */           Char2DoubleLinkedOpenHashMap.this.removeNullEntry();
/* 1497 */           return true;
/*      */         } 
/* 1499 */         return false;
/*      */       } 
/*      */       
/* 1502 */       char[] key = Char2DoubleLinkedOpenHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/* 1505 */       if ((curr = key[pos = HashCommon.mix(k) & Char2DoubleLinkedOpenHashMap.this.mask]) == '\000')
/* 1506 */         return false; 
/* 1507 */       if (curr == k) {
/* 1508 */         if (Double.doubleToLongBits(Char2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1509 */           Char2DoubleLinkedOpenHashMap.this.removeEntry(pos);
/* 1510 */           return true;
/*      */         } 
/* 1512 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1515 */         if ((curr = key[pos = pos + 1 & Char2DoubleLinkedOpenHashMap.this.mask]) == '\000')
/* 1516 */           return false; 
/* 1517 */         if (curr == k && 
/* 1518 */           Double.doubleToLongBits(Char2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1519 */           Char2DoubleLinkedOpenHashMap.this.removeEntry(pos);
/* 1520 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1527 */       return Char2DoubleLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1531 */       Char2DoubleLinkedOpenHashMap.this.clear();
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
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Char2DoubleMap.Entry> iterator(Char2DoubleMap.Entry from) {
/* 1546 */       return new Char2DoubleLinkedOpenHashMap.EntryIterator(from.getCharKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Char2DoubleMap.Entry> fastIterator() {
/* 1557 */       return new Char2DoubleLinkedOpenHashMap.FastEntryIterator();
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
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Char2DoubleMap.Entry> fastIterator(Char2DoubleMap.Entry from) {
/* 1572 */       return new Char2DoubleLinkedOpenHashMap.FastEntryIterator(from.getCharKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Char2DoubleMap.Entry> consumer) {
/* 1577 */       for (int i = Char2DoubleLinkedOpenHashMap.this.size, next = Char2DoubleLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1578 */         int curr = next;
/* 1579 */         next = (int)Char2DoubleLinkedOpenHashMap.this.link[curr];
/* 1580 */         consumer.accept(new AbstractChar2DoubleMap.BasicEntry(Char2DoubleLinkedOpenHashMap.this.key[curr], Char2DoubleLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Char2DoubleMap.Entry> consumer) {
/* 1586 */       AbstractChar2DoubleMap.BasicEntry entry = new AbstractChar2DoubleMap.BasicEntry();
/* 1587 */       for (int i = Char2DoubleLinkedOpenHashMap.this.size, next = Char2DoubleLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1588 */         int curr = next;
/* 1589 */         next = (int)Char2DoubleLinkedOpenHashMap.this.link[curr];
/* 1590 */         entry.key = Char2DoubleLinkedOpenHashMap.this.key[curr];
/* 1591 */         entry.value = Char2DoubleLinkedOpenHashMap.this.value[curr];
/* 1592 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Char2DoubleSortedMap.FastSortedEntrySet char2DoubleEntrySet() {
/* 1598 */     if (this.entries == null)
/* 1599 */       this.entries = new MapEntrySet(); 
/* 1600 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends MapIterator
/*      */     implements CharListIterator
/*      */   {
/*      */     public KeyIterator(char k) {
/* 1613 */       super(k);
/*      */     }
/*      */     
/*      */     public char previousChar() {
/* 1617 */       return Char2DoubleLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public char nextChar() {
/* 1624 */       return Char2DoubleLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractCharSortedSet { private KeySet() {}
/*      */     
/*      */     public CharListIterator iterator(char from) {
/* 1630 */       return new Char2DoubleLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public CharListIterator iterator() {
/* 1634 */       return new Char2DoubleLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1639 */       if (Char2DoubleLinkedOpenHashMap.this.containsNullKey)
/* 1640 */         consumer.accept(Char2DoubleLinkedOpenHashMap.this.key[Char2DoubleLinkedOpenHashMap.this.n]); 
/* 1641 */       for (int pos = Char2DoubleLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1642 */         char k = Char2DoubleLinkedOpenHashMap.this.key[pos];
/* 1643 */         if (k != '\000')
/* 1644 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1649 */       return Char2DoubleLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(char k) {
/* 1653 */       return Char2DoubleLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(char k) {
/* 1657 */       int oldSize = Char2DoubleLinkedOpenHashMap.this.size;
/* 1658 */       Char2DoubleLinkedOpenHashMap.this.remove(k);
/* 1659 */       return (Char2DoubleLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1663 */       Char2DoubleLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public char firstChar() {
/* 1667 */       if (Char2DoubleLinkedOpenHashMap.this.size == 0)
/* 1668 */         throw new NoSuchElementException(); 
/* 1669 */       return Char2DoubleLinkedOpenHashMap.this.key[Char2DoubleLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public char lastChar() {
/* 1673 */       if (Char2DoubleLinkedOpenHashMap.this.size == 0)
/* 1674 */         throw new NoSuchElementException(); 
/* 1675 */       return Char2DoubleLinkedOpenHashMap.this.key[Char2DoubleLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public CharComparator comparator() {
/* 1679 */       return null;
/*      */     }
/*      */     
/*      */     public CharSortedSet tailSet(char from) {
/* 1683 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public CharSortedSet headSet(char to) {
/* 1687 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public CharSortedSet subSet(char from, char to) {
/* 1691 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public CharSortedSet keySet() {
/* 1696 */     if (this.keys == null)
/* 1697 */       this.keys = new KeySet(); 
/* 1698 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends MapIterator
/*      */     implements DoubleListIterator
/*      */   {
/*      */     public double previousDouble() {
/* 1712 */       return Char2DoubleLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1719 */       return Char2DoubleLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public DoubleCollection values() {
/* 1724 */     if (this.values == null)
/* 1725 */       this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1728 */             return (DoubleIterator)new Char2DoubleLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1732 */             return Char2DoubleLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(double v) {
/* 1736 */             return Char2DoubleLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1740 */             Char2DoubleLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(DoubleConsumer consumer) {
/* 1745 */             if (Char2DoubleLinkedOpenHashMap.this.containsNullKey)
/* 1746 */               consumer.accept(Char2DoubleLinkedOpenHashMap.this.value[Char2DoubleLinkedOpenHashMap.this.n]); 
/* 1747 */             for (int pos = Char2DoubleLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1748 */               if (Char2DoubleLinkedOpenHashMap.this.key[pos] != '\000')
/* 1749 */                 consumer.accept(Char2DoubleLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1752 */     return this.values;
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
/* 1769 */     return trim(this.size);
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
/* 1793 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1794 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1795 */       return true; 
/*      */     try {
/* 1797 */       rehash(l);
/* 1798 */     } catch (OutOfMemoryError cantDoIt) {
/* 1799 */       return false;
/*      */     } 
/* 1801 */     return true;
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
/* 1817 */     char[] key = this.key;
/* 1818 */     double[] value = this.value;
/* 1819 */     int mask = newN - 1;
/* 1820 */     char[] newKey = new char[newN + 1];
/* 1821 */     double[] newValue = new double[newN + 1];
/* 1822 */     int i = this.first, prev = -1, newPrev = -1;
/* 1823 */     long[] link = this.link;
/* 1824 */     long[] newLink = new long[newN + 1];
/* 1825 */     this.first = -1;
/* 1826 */     for (int j = this.size; j-- != 0; ) {
/* 1827 */       int pos; if (key[i] == '\000') {
/* 1828 */         pos = newN;
/*      */       } else {
/* 1830 */         pos = HashCommon.mix(key[i]) & mask;
/* 1831 */         while (newKey[pos] != '\000')
/* 1832 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1834 */       newKey[pos] = key[i];
/* 1835 */       newValue[pos] = value[i];
/* 1836 */       if (prev != -1) {
/* 1837 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1838 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1839 */         newPrev = pos;
/*      */       } else {
/* 1841 */         newPrev = this.first = pos;
/*      */         
/* 1843 */         newLink[pos] = -1L;
/*      */       } 
/* 1845 */       int t = i;
/* 1846 */       i = (int)link[i];
/* 1847 */       prev = t;
/*      */     } 
/* 1849 */     this.link = newLink;
/* 1850 */     this.last = newPrev;
/* 1851 */     if (newPrev != -1)
/*      */     {
/* 1853 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1854 */     this.n = newN;
/* 1855 */     this.mask = mask;
/* 1856 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1857 */     this.key = newKey;
/* 1858 */     this.value = newValue;
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
/*      */   public Char2DoubleLinkedOpenHashMap clone() {
/*      */     Char2DoubleLinkedOpenHashMap c;
/*      */     try {
/* 1875 */       c = (Char2DoubleLinkedOpenHashMap)super.clone();
/* 1876 */     } catch (CloneNotSupportedException cantHappen) {
/* 1877 */       throw new InternalError();
/*      */     } 
/* 1879 */     c.keys = null;
/* 1880 */     c.values = null;
/* 1881 */     c.entries = null;
/* 1882 */     c.containsNullKey = this.containsNullKey;
/* 1883 */     c.key = (char[])this.key.clone();
/* 1884 */     c.value = (double[])this.value.clone();
/* 1885 */     c.link = (long[])this.link.clone();
/* 1886 */     return c;
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
/* 1899 */     int h = 0;
/* 1900 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1901 */       while (this.key[i] == '\000')
/* 1902 */         i++; 
/* 1903 */       t = this.key[i];
/* 1904 */       t ^= HashCommon.double2int(this.value[i]);
/* 1905 */       h += t;
/* 1906 */       i++;
/*      */     } 
/*      */     
/* 1909 */     if (this.containsNullKey)
/* 1910 */       h += HashCommon.double2int(this.value[this.n]); 
/* 1911 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1914 */     char[] key = this.key;
/* 1915 */     double[] value = this.value;
/* 1916 */     MapIterator i = new MapIterator();
/* 1917 */     s.defaultWriteObject();
/* 1918 */     for (int j = this.size; j-- != 0; ) {
/* 1919 */       int e = i.nextEntry();
/* 1920 */       s.writeChar(key[e]);
/* 1921 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1926 */     s.defaultReadObject();
/* 1927 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1928 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1929 */     this.mask = this.n - 1;
/* 1930 */     char[] key = this.key = new char[this.n + 1];
/* 1931 */     double[] value = this.value = new double[this.n + 1];
/* 1932 */     long[] link = this.link = new long[this.n + 1];
/* 1933 */     int prev = -1;
/* 1934 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1937 */     for (int i = this.size; i-- != 0; ) {
/* 1938 */       int pos; char k = s.readChar();
/* 1939 */       double v = s.readDouble();
/* 1940 */       if (k == '\000') {
/* 1941 */         pos = this.n;
/* 1942 */         this.containsNullKey = true;
/*      */       } else {
/* 1944 */         pos = HashCommon.mix(k) & this.mask;
/* 1945 */         while (key[pos] != '\000')
/* 1946 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1948 */       key[pos] = k;
/* 1949 */       value[pos] = v;
/* 1950 */       if (this.first != -1) {
/* 1951 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1952 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1953 */         prev = pos; continue;
/*      */       } 
/* 1955 */       prev = this.first = pos;
/*      */       
/* 1957 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1960 */     this.last = prev;
/* 1961 */     if (prev != -1)
/*      */     {
/* 1963 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2DoubleLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */