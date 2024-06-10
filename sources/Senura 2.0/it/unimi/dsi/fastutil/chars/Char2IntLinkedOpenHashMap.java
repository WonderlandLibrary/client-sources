/*      */ package it.unimi.dsi.fastutil.chars;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntListIterator;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Char2IntLinkedOpenHashMap
/*      */   extends AbstractChar2IntSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient char[] key;
/*      */   protected transient int[] value;
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
/*      */   protected transient Char2IntSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient CharSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient IntCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2IntLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new char[this.n + 1];
/*  162 */     this.value = new int[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2IntLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2IntLinkedOpenHashMap() {
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
/*      */   public Char2IntLinkedOpenHashMap(Map<? extends Character, ? extends Integer> m, float f) {
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
/*      */   public Char2IntLinkedOpenHashMap(Map<? extends Character, ? extends Integer> m) {
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
/*      */   public Char2IntLinkedOpenHashMap(Char2IntMap m, float f) {
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
/*      */   public Char2IntLinkedOpenHashMap(Char2IntMap m) {
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
/*      */   public Char2IntLinkedOpenHashMap(char[] k, int[] v, float f) {
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
/*      */   public Char2IntLinkedOpenHashMap(char[] k, int[] v) {
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
/*      */   private int removeEntry(int pos) {
/*  275 */     int oldValue = this.value[pos];
/*  276 */     this.size--;
/*  277 */     fixPointers(pos);
/*  278 */     shiftKeys(pos);
/*  279 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  280 */       rehash(this.n / 2); 
/*  281 */     return oldValue;
/*      */   }
/*      */   private int removeNullEntry() {
/*  284 */     this.containsNullKey = false;
/*  285 */     int oldValue = this.value[this.n];
/*  286 */     this.size--;
/*  287 */     fixPointers(this.n);
/*  288 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  289 */       rehash(this.n / 2); 
/*  290 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Character, ? extends Integer> m) {
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
/*      */   private void insert(int pos, char k, int v) {
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
/*      */   public int put(char k, int v) {
/*  342 */     int pos = find(k);
/*  343 */     if (pos < 0) {
/*  344 */       insert(-pos - 1, k, v);
/*  345 */       return this.defRetValue;
/*      */     } 
/*  347 */     int oldValue = this.value[pos];
/*  348 */     this.value[pos] = v;
/*  349 */     return oldValue;
/*      */   }
/*      */   private int addToValue(int pos, int incr) {
/*  352 */     int oldValue = this.value[pos];
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
/*      */   public int addTo(char k, int incr) {
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
/*      */   public int remove(char k) {
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
/*      */   private int setValue(int pos, int v) {
/*  461 */     int oldValue = this.value[pos];
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
/*      */   public int removeFirstInt() {
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
/*  484 */     int v = this.value[pos];
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
/*      */   public int removeLastInt() {
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
/*  511 */     int v = this.value[pos];
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
/*      */   public int getAndMoveToFirst(char k) {
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
/*      */   public int getAndMoveToLast(char k) {
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
/*      */   public int putAndMoveToFirst(char k, int v) {
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
/*      */   public int putAndMoveToLast(char k, int v) {
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
/*      */   public int get(char k) {
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
/*      */   public boolean containsValue(int v) {
/*  780 */     int[] value = this.value;
/*  781 */     char[] key = this.key;
/*  782 */     if (this.containsNullKey && value[this.n] == v)
/*  783 */       return true; 
/*  784 */     for (int i = this.n; i-- != 0;) {
/*  785 */       if (key[i] != '\000' && value[i] == v)
/*  786 */         return true; 
/*  787 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOrDefault(char k, int defaultValue) {
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
/*      */   public int putIfAbsent(char k, int v) {
/*  814 */     int pos = find(k);
/*  815 */     if (pos >= 0)
/*  816 */       return this.value[pos]; 
/*  817 */     insert(-pos - 1, k, v);
/*  818 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(char k, int v) {
/*  824 */     if (k == '\000') {
/*  825 */       if (this.containsNullKey && v == this.value[this.n]) {
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
/*  837 */     if (k == curr && v == this.value[pos]) {
/*  838 */       removeEntry(pos);
/*  839 */       return true;
/*      */     } 
/*      */     while (true) {
/*  842 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  843 */         return false; 
/*  844 */       if (k == curr && v == this.value[pos]) {
/*  845 */         removeEntry(pos);
/*  846 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(char k, int oldValue, int v) {
/*  853 */     int pos = find(k);
/*  854 */     if (pos < 0 || oldValue != this.value[pos])
/*  855 */       return false; 
/*  856 */     this.value[pos] = v;
/*  857 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int replace(char k, int v) {
/*  862 */     int pos = find(k);
/*  863 */     if (pos < 0)
/*  864 */       return this.defRetValue; 
/*  865 */     int oldValue = this.value[pos];
/*  866 */     this.value[pos] = v;
/*  867 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(char k, IntUnaryOperator mappingFunction) {
/*  872 */     Objects.requireNonNull(mappingFunction);
/*  873 */     int pos = find(k);
/*  874 */     if (pos >= 0)
/*  875 */       return this.value[pos]; 
/*  876 */     int newValue = mappingFunction.applyAsInt(k);
/*  877 */     insert(-pos - 1, k, newValue);
/*  878 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsentNullable(char k, IntFunction<? extends Integer> mappingFunction) {
/*  884 */     Objects.requireNonNull(mappingFunction);
/*  885 */     int pos = find(k);
/*  886 */     if (pos >= 0)
/*  887 */       return this.value[pos]; 
/*  888 */     Integer newValue = mappingFunction.apply(k);
/*  889 */     if (newValue == null)
/*  890 */       return this.defRetValue; 
/*  891 */     int v = newValue.intValue();
/*  892 */     insert(-pos - 1, k, v);
/*  893 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfPresent(char k, BiFunction<? super Character, ? super Integer, ? extends Integer> remappingFunction) {
/*  899 */     Objects.requireNonNull(remappingFunction);
/*  900 */     int pos = find(k);
/*  901 */     if (pos < 0)
/*  902 */       return this.defRetValue; 
/*  903 */     Integer newValue = remappingFunction.apply(Character.valueOf(k), Integer.valueOf(this.value[pos]));
/*  904 */     if (newValue == null) {
/*  905 */       if (k == '\000') {
/*  906 */         removeNullEntry();
/*      */       } else {
/*  908 */         removeEntry(pos);
/*  909 */       }  return this.defRetValue;
/*      */     } 
/*  911 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int compute(char k, BiFunction<? super Character, ? super Integer, ? extends Integer> remappingFunction) {
/*  917 */     Objects.requireNonNull(remappingFunction);
/*  918 */     int pos = find(k);
/*  919 */     Integer newValue = remappingFunction.apply(Character.valueOf(k), 
/*  920 */         (pos >= 0) ? Integer.valueOf(this.value[pos]) : null);
/*  921 */     if (newValue == null) {
/*  922 */       if (pos >= 0)
/*  923 */         if (k == '\000') {
/*  924 */           removeNullEntry();
/*      */         } else {
/*  926 */           removeEntry(pos);
/*      */         }  
/*  928 */       return this.defRetValue;
/*      */     } 
/*  930 */     int newVal = newValue.intValue();
/*  931 */     if (pos < 0) {
/*  932 */       insert(-pos - 1, k, newVal);
/*  933 */       return newVal;
/*      */     } 
/*  935 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int merge(char k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  941 */     Objects.requireNonNull(remappingFunction);
/*  942 */     int pos = find(k);
/*  943 */     if (pos < 0) {
/*  944 */       insert(-pos - 1, k, v);
/*  945 */       return v;
/*      */     } 
/*  947 */     Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
/*  948 */     if (newValue == null) {
/*  949 */       if (k == '\000') {
/*  950 */         removeNullEntry();
/*      */       } else {
/*  952 */         removeEntry(pos);
/*  953 */       }  return this.defRetValue;
/*      */     } 
/*  955 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
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
/*      */     implements Char2IntMap.Entry, Map.Entry<Character, Integer>
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
/*  997 */       return Char2IntLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public int getIntValue() {
/* 1001 */       return Char2IntLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public int setValue(int v) {
/* 1005 */       int oldValue = Char2IntLinkedOpenHashMap.this.value[this.index];
/* 1006 */       Char2IntLinkedOpenHashMap.this.value[this.index] = v;
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
/* 1017 */       return Character.valueOf(Char2IntLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getValue() {
/* 1027 */       return Integer.valueOf(Char2IntLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer setValue(Integer v) {
/* 1037 */       return Integer.valueOf(setValue(v.intValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1042 */       if (!(o instanceof Map.Entry))
/* 1043 */         return false; 
/* 1044 */       Map.Entry<Character, Integer> e = (Map.Entry<Character, Integer>)o;
/* 1045 */       return (Char2IntLinkedOpenHashMap.this.key[this.index] == ((Character)e.getKey()).charValue() && Char2IntLinkedOpenHashMap.this.value[this.index] == ((Integer)e.getValue()).intValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1049 */       return Char2IntLinkedOpenHashMap.this.key[this.index] ^ Char2IntLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1053 */       return Char2IntLinkedOpenHashMap.this.key[this.index] + "=>" + Char2IntLinkedOpenHashMap.this.value[this.index];
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
/* 1064 */     if (this.size == 0) {
/* 1065 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1068 */     if (this.first == i) {
/* 1069 */       this.first = (int)this.link[i];
/* 1070 */       if (0 <= this.first)
/*      */       {
/* 1072 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1076 */     if (this.last == i) {
/* 1077 */       this.last = (int)(this.link[i] >>> 32L);
/* 1078 */       if (0 <= this.last)
/*      */       {
/* 1080 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1084 */     long linki = this.link[i];
/* 1085 */     int prev = (int)(linki >>> 32L);
/* 1086 */     int next = (int)linki;
/* 1087 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1088 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1101 */     if (this.size == 1) {
/* 1102 */       this.first = this.last = d;
/*      */       
/* 1104 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1107 */     if (this.first == s) {
/* 1108 */       this.first = d;
/* 1109 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1110 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1113 */     if (this.last == s) {
/* 1114 */       this.last = d;
/* 1115 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1116 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1119 */     long links = this.link[s];
/* 1120 */     int prev = (int)(links >>> 32L);
/* 1121 */     int next = (int)links;
/* 1122 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1123 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1124 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char firstCharKey() {
/* 1133 */     if (this.size == 0)
/* 1134 */       throw new NoSuchElementException(); 
/* 1135 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char lastCharKey() {
/* 1144 */     if (this.size == 0)
/* 1145 */       throw new NoSuchElementException(); 
/* 1146 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2IntSortedMap tailMap(char from) {
/* 1155 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2IntSortedMap headMap(char to) {
/* 1164 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2IntSortedMap subMap(char from, char to) {
/* 1173 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharComparator comparator() {
/* 1182 */     return null;
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
/* 1197 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1203 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1208 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1214 */     int index = -1;
/*      */     protected MapIterator() {
/* 1216 */       this.next = Char2IntLinkedOpenHashMap.this.first;
/* 1217 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(char from) {
/* 1220 */       if (from == '\000') {
/* 1221 */         if (Char2IntLinkedOpenHashMap.this.containsNullKey) {
/* 1222 */           this.next = (int)Char2IntLinkedOpenHashMap.this.link[Char2IntLinkedOpenHashMap.this.n];
/* 1223 */           this.prev = Char2IntLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1226 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1228 */       if (Char2IntLinkedOpenHashMap.this.key[Char2IntLinkedOpenHashMap.this.last] == from) {
/* 1229 */         this.prev = Char2IntLinkedOpenHashMap.this.last;
/* 1230 */         this.index = Char2IntLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1234 */       int pos = HashCommon.mix(from) & Char2IntLinkedOpenHashMap.this.mask;
/*      */       
/* 1236 */       while (Char2IntLinkedOpenHashMap.this.key[pos] != '\000') {
/* 1237 */         if (Char2IntLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1239 */           this.next = (int)Char2IntLinkedOpenHashMap.this.link[pos];
/* 1240 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1243 */         pos = pos + 1 & Char2IntLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1245 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1248 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1251 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1254 */       if (this.index >= 0)
/*      */         return; 
/* 1256 */       if (this.prev == -1) {
/* 1257 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1260 */       if (this.next == -1) {
/* 1261 */         this.index = Char2IntLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1264 */       int pos = Char2IntLinkedOpenHashMap.this.first;
/* 1265 */       this.index = 1;
/* 1266 */       while (pos != this.prev) {
/* 1267 */         pos = (int)Char2IntLinkedOpenHashMap.this.link[pos];
/* 1268 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1272 */       ensureIndexKnown();
/* 1273 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1276 */       ensureIndexKnown();
/* 1277 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1280 */       if (!hasNext())
/* 1281 */         throw new NoSuchElementException(); 
/* 1282 */       this.curr = this.next;
/* 1283 */       this.next = (int)Char2IntLinkedOpenHashMap.this.link[this.curr];
/* 1284 */       this.prev = this.curr;
/* 1285 */       if (this.index >= 0)
/* 1286 */         this.index++; 
/* 1287 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1290 */       if (!hasPrevious())
/* 1291 */         throw new NoSuchElementException(); 
/* 1292 */       this.curr = this.prev;
/* 1293 */       this.prev = (int)(Char2IntLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1294 */       this.next = this.curr;
/* 1295 */       if (this.index >= 0)
/* 1296 */         this.index--; 
/* 1297 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1300 */       ensureIndexKnown();
/* 1301 */       if (this.curr == -1)
/* 1302 */         throw new IllegalStateException(); 
/* 1303 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1308 */         this.index--;
/* 1309 */         this.prev = (int)(Char2IntLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1311 */         this.next = (int)Char2IntLinkedOpenHashMap.this.link[this.curr];
/* 1312 */       }  Char2IntLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1317 */       if (this.prev == -1) {
/* 1318 */         Char2IntLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1320 */         Char2IntLinkedOpenHashMap.this.link[this.prev] = Char2IntLinkedOpenHashMap.this.link[this.prev] ^ (Char2IntLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1321 */       }  if (this.next == -1) {
/* 1322 */         Char2IntLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1324 */         Char2IntLinkedOpenHashMap.this.link[this.next] = Char2IntLinkedOpenHashMap.this.link[this.next] ^ (Char2IntLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1325 */       }  int pos = this.curr;
/* 1326 */       this.curr = -1;
/* 1327 */       if (pos == Char2IntLinkedOpenHashMap.this.n) {
/* 1328 */         Char2IntLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1331 */         char[] key = Char2IntLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           char curr;
/*      */           int last;
/* 1335 */           pos = (last = pos) + 1 & Char2IntLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1337 */             if ((curr = key[pos]) == '\000') {
/* 1338 */               key[last] = Character.MIN_VALUE;
/*      */               return;
/*      */             } 
/* 1341 */             int slot = HashCommon.mix(curr) & Char2IntLinkedOpenHashMap.this.mask;
/* 1342 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1344 */             pos = pos + 1 & Char2IntLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1346 */           key[last] = curr;
/* 1347 */           Char2IntLinkedOpenHashMap.this.value[last] = Char2IntLinkedOpenHashMap.this.value[pos];
/* 1348 */           if (this.next == pos)
/* 1349 */             this.next = last; 
/* 1350 */           if (this.prev == pos)
/* 1351 */             this.prev = last; 
/* 1352 */           Char2IntLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1357 */       int i = n;
/* 1358 */       while (i-- != 0 && hasNext())
/* 1359 */         nextEntry(); 
/* 1360 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1363 */       int i = n;
/* 1364 */       while (i-- != 0 && hasPrevious())
/* 1365 */         previousEntry(); 
/* 1366 */       return n - i - 1;
/*      */     }
/*      */     public void set(Char2IntMap.Entry ok) {
/* 1369 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Char2IntMap.Entry ok) {
/* 1372 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Char2IntMap.Entry> { private Char2IntLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(char from) {
/* 1380 */       super(from);
/*      */     }
/*      */     
/*      */     public Char2IntLinkedOpenHashMap.MapEntry next() {
/* 1384 */       return this.entry = new Char2IntLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Char2IntLinkedOpenHashMap.MapEntry previous() {
/* 1388 */       return this.entry = new Char2IntLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1392 */       super.remove();
/* 1393 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1397 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Char2IntMap.Entry> { final Char2IntLinkedOpenHashMap.MapEntry entry = new Char2IntLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(char from) {
/* 1401 */       super(from);
/*      */     }
/*      */     
/*      */     public Char2IntLinkedOpenHashMap.MapEntry next() {
/* 1405 */       this.entry.index = nextEntry();
/* 1406 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Char2IntLinkedOpenHashMap.MapEntry previous() {
/* 1410 */       this.entry.index = previousEntry();
/* 1411 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Char2IntMap.Entry> implements Char2IntSortedMap.FastSortedEntrySet { public ObjectBidirectionalIterator<Char2IntMap.Entry> iterator() {
/* 1417 */       return (ObjectBidirectionalIterator<Char2IntMap.Entry>)new Char2IntLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     private MapEntrySet() {}
/*      */     public Comparator<? super Char2IntMap.Entry> comparator() {
/* 1421 */       return null;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Char2IntMap.Entry> subSet(Char2IntMap.Entry fromElement, Char2IntMap.Entry toElement) {
/* 1425 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Char2IntMap.Entry> headSet(Char2IntMap.Entry toElement) {
/* 1429 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Char2IntMap.Entry> tailSet(Char2IntMap.Entry fromElement) {
/* 1433 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Char2IntMap.Entry first() {
/* 1437 */       if (Char2IntLinkedOpenHashMap.this.size == 0)
/* 1438 */         throw new NoSuchElementException(); 
/* 1439 */       return new Char2IntLinkedOpenHashMap.MapEntry(Char2IntLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Char2IntMap.Entry last() {
/* 1443 */       if (Char2IntLinkedOpenHashMap.this.size == 0)
/* 1444 */         throw new NoSuchElementException(); 
/* 1445 */       return new Char2IntLinkedOpenHashMap.MapEntry(Char2IntLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1450 */       if (!(o instanceof Map.Entry))
/* 1451 */         return false; 
/* 1452 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1453 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/* 1454 */         return false; 
/* 1455 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 1456 */         return false; 
/* 1457 */       char k = ((Character)e.getKey()).charValue();
/* 1458 */       int v = ((Integer)e.getValue()).intValue();
/* 1459 */       if (k == '\000') {
/* 1460 */         return (Char2IntLinkedOpenHashMap.this.containsNullKey && Char2IntLinkedOpenHashMap.this.value[Char2IntLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1462 */       char[] key = Char2IntLinkedOpenHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/* 1465 */       if ((curr = key[pos = HashCommon.mix(k) & Char2IntLinkedOpenHashMap.this.mask]) == '\000')
/* 1466 */         return false; 
/* 1467 */       if (k == curr) {
/* 1468 */         return (Char2IntLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1471 */         if ((curr = key[pos = pos + 1 & Char2IntLinkedOpenHashMap.this.mask]) == '\000')
/* 1472 */           return false; 
/* 1473 */         if (k == curr) {
/* 1474 */           return (Char2IntLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1480 */       if (!(o instanceof Map.Entry))
/* 1481 */         return false; 
/* 1482 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1483 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/* 1484 */         return false; 
/* 1485 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 1486 */         return false; 
/* 1487 */       char k = ((Character)e.getKey()).charValue();
/* 1488 */       int v = ((Integer)e.getValue()).intValue();
/* 1489 */       if (k == '\000') {
/* 1490 */         if (Char2IntLinkedOpenHashMap.this.containsNullKey && Char2IntLinkedOpenHashMap.this.value[Char2IntLinkedOpenHashMap.this.n] == v) {
/* 1491 */           Char2IntLinkedOpenHashMap.this.removeNullEntry();
/* 1492 */           return true;
/*      */         } 
/* 1494 */         return false;
/*      */       } 
/*      */       
/* 1497 */       char[] key = Char2IntLinkedOpenHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/* 1500 */       if ((curr = key[pos = HashCommon.mix(k) & Char2IntLinkedOpenHashMap.this.mask]) == '\000')
/* 1501 */         return false; 
/* 1502 */       if (curr == k) {
/* 1503 */         if (Char2IntLinkedOpenHashMap.this.value[pos] == v) {
/* 1504 */           Char2IntLinkedOpenHashMap.this.removeEntry(pos);
/* 1505 */           return true;
/*      */         } 
/* 1507 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1510 */         if ((curr = key[pos = pos + 1 & Char2IntLinkedOpenHashMap.this.mask]) == '\000')
/* 1511 */           return false; 
/* 1512 */         if (curr == k && 
/* 1513 */           Char2IntLinkedOpenHashMap.this.value[pos] == v) {
/* 1514 */           Char2IntLinkedOpenHashMap.this.removeEntry(pos);
/* 1515 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1522 */       return Char2IntLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1526 */       Char2IntLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Char2IntMap.Entry> iterator(Char2IntMap.Entry from) {
/* 1541 */       return new Char2IntLinkedOpenHashMap.EntryIterator(from.getCharKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Char2IntMap.Entry> fastIterator() {
/* 1552 */       return new Char2IntLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Char2IntMap.Entry> fastIterator(Char2IntMap.Entry from) {
/* 1567 */       return new Char2IntLinkedOpenHashMap.FastEntryIterator(from.getCharKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Char2IntMap.Entry> consumer) {
/* 1572 */       for (int i = Char2IntLinkedOpenHashMap.this.size, next = Char2IntLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1573 */         int curr = next;
/* 1574 */         next = (int)Char2IntLinkedOpenHashMap.this.link[curr];
/* 1575 */         consumer.accept(new AbstractChar2IntMap.BasicEntry(Char2IntLinkedOpenHashMap.this.key[curr], Char2IntLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Char2IntMap.Entry> consumer) {
/* 1581 */       AbstractChar2IntMap.BasicEntry entry = new AbstractChar2IntMap.BasicEntry();
/* 1582 */       for (int i = Char2IntLinkedOpenHashMap.this.size, next = Char2IntLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1583 */         int curr = next;
/* 1584 */         next = (int)Char2IntLinkedOpenHashMap.this.link[curr];
/* 1585 */         entry.key = Char2IntLinkedOpenHashMap.this.key[curr];
/* 1586 */         entry.value = Char2IntLinkedOpenHashMap.this.value[curr];
/* 1587 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Char2IntSortedMap.FastSortedEntrySet char2IntEntrySet() {
/* 1593 */     if (this.entries == null)
/* 1594 */       this.entries = new MapEntrySet(); 
/* 1595 */     return this.entries;
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
/* 1608 */       super(k);
/*      */     }
/*      */     
/*      */     public char previousChar() {
/* 1612 */       return Char2IntLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public char nextChar() {
/* 1619 */       return Char2IntLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractCharSortedSet { private KeySet() {}
/*      */     
/*      */     public CharListIterator iterator(char from) {
/* 1625 */       return new Char2IntLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public CharListIterator iterator() {
/* 1629 */       return new Char2IntLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1634 */       if (Char2IntLinkedOpenHashMap.this.containsNullKey)
/* 1635 */         consumer.accept(Char2IntLinkedOpenHashMap.this.key[Char2IntLinkedOpenHashMap.this.n]); 
/* 1636 */       for (int pos = Char2IntLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1637 */         char k = Char2IntLinkedOpenHashMap.this.key[pos];
/* 1638 */         if (k != '\000')
/* 1639 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1644 */       return Char2IntLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(char k) {
/* 1648 */       return Char2IntLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(char k) {
/* 1652 */       int oldSize = Char2IntLinkedOpenHashMap.this.size;
/* 1653 */       Char2IntLinkedOpenHashMap.this.remove(k);
/* 1654 */       return (Char2IntLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1658 */       Char2IntLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public char firstChar() {
/* 1662 */       if (Char2IntLinkedOpenHashMap.this.size == 0)
/* 1663 */         throw new NoSuchElementException(); 
/* 1664 */       return Char2IntLinkedOpenHashMap.this.key[Char2IntLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public char lastChar() {
/* 1668 */       if (Char2IntLinkedOpenHashMap.this.size == 0)
/* 1669 */         throw new NoSuchElementException(); 
/* 1670 */       return Char2IntLinkedOpenHashMap.this.key[Char2IntLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public CharComparator comparator() {
/* 1674 */       return null;
/*      */     }
/*      */     
/*      */     public CharSortedSet tailSet(char from) {
/* 1678 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public CharSortedSet headSet(char to) {
/* 1682 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public CharSortedSet subSet(char from, char to) {
/* 1686 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public CharSortedSet keySet() {
/* 1691 */     if (this.keys == null)
/* 1692 */       this.keys = new KeySet(); 
/* 1693 */     return this.keys;
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
/*      */     implements IntListIterator
/*      */   {
/*      */     public int previousInt() {
/* 1707 */       return Char2IntLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1714 */       return Char2IntLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public IntCollection values() {
/* 1719 */     if (this.values == null)
/* 1720 */       this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1723 */             return (IntIterator)new Char2IntLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1727 */             return Char2IntLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(int v) {
/* 1731 */             return Char2IntLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1735 */             Char2IntLinkedOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1740 */             if (Char2IntLinkedOpenHashMap.this.containsNullKey)
/* 1741 */               consumer.accept(Char2IntLinkedOpenHashMap.this.value[Char2IntLinkedOpenHashMap.this.n]); 
/* 1742 */             for (int pos = Char2IntLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1743 */               if (Char2IntLinkedOpenHashMap.this.key[pos] != '\000')
/* 1744 */                 consumer.accept(Char2IntLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1747 */     return this.values;
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
/* 1764 */     return trim(this.size);
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
/* 1788 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1789 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1790 */       return true; 
/*      */     try {
/* 1792 */       rehash(l);
/* 1793 */     } catch (OutOfMemoryError cantDoIt) {
/* 1794 */       return false;
/*      */     } 
/* 1796 */     return true;
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
/* 1812 */     char[] key = this.key;
/* 1813 */     int[] value = this.value;
/* 1814 */     int mask = newN - 1;
/* 1815 */     char[] newKey = new char[newN + 1];
/* 1816 */     int[] newValue = new int[newN + 1];
/* 1817 */     int i = this.first, prev = -1, newPrev = -1;
/* 1818 */     long[] link = this.link;
/* 1819 */     long[] newLink = new long[newN + 1];
/* 1820 */     this.first = -1;
/* 1821 */     for (int j = this.size; j-- != 0; ) {
/* 1822 */       int pos; if (key[i] == '\000') {
/* 1823 */         pos = newN;
/*      */       } else {
/* 1825 */         pos = HashCommon.mix(key[i]) & mask;
/* 1826 */         while (newKey[pos] != '\000')
/* 1827 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1829 */       newKey[pos] = key[i];
/* 1830 */       newValue[pos] = value[i];
/* 1831 */       if (prev != -1) {
/* 1832 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1833 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1834 */         newPrev = pos;
/*      */       } else {
/* 1836 */         newPrev = this.first = pos;
/*      */         
/* 1838 */         newLink[pos] = -1L;
/*      */       } 
/* 1840 */       int t = i;
/* 1841 */       i = (int)link[i];
/* 1842 */       prev = t;
/*      */     } 
/* 1844 */     this.link = newLink;
/* 1845 */     this.last = newPrev;
/* 1846 */     if (newPrev != -1)
/*      */     {
/* 1848 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1849 */     this.n = newN;
/* 1850 */     this.mask = mask;
/* 1851 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1852 */     this.key = newKey;
/* 1853 */     this.value = newValue;
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
/*      */   public Char2IntLinkedOpenHashMap clone() {
/*      */     Char2IntLinkedOpenHashMap c;
/*      */     try {
/* 1870 */       c = (Char2IntLinkedOpenHashMap)super.clone();
/* 1871 */     } catch (CloneNotSupportedException cantHappen) {
/* 1872 */       throw new InternalError();
/*      */     } 
/* 1874 */     c.keys = null;
/* 1875 */     c.values = null;
/* 1876 */     c.entries = null;
/* 1877 */     c.containsNullKey = this.containsNullKey;
/* 1878 */     c.key = (char[])this.key.clone();
/* 1879 */     c.value = (int[])this.value.clone();
/* 1880 */     c.link = (long[])this.link.clone();
/* 1881 */     return c;
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
/* 1894 */     int h = 0;
/* 1895 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1896 */       while (this.key[i] == '\000')
/* 1897 */         i++; 
/* 1898 */       t = this.key[i];
/* 1899 */       t ^= this.value[i];
/* 1900 */       h += t;
/* 1901 */       i++;
/*      */     } 
/*      */     
/* 1904 */     if (this.containsNullKey)
/* 1905 */       h += this.value[this.n]; 
/* 1906 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1909 */     char[] key = this.key;
/* 1910 */     int[] value = this.value;
/* 1911 */     MapIterator i = new MapIterator();
/* 1912 */     s.defaultWriteObject();
/* 1913 */     for (int j = this.size; j-- != 0; ) {
/* 1914 */       int e = i.nextEntry();
/* 1915 */       s.writeChar(key[e]);
/* 1916 */       s.writeInt(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1921 */     s.defaultReadObject();
/* 1922 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1923 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1924 */     this.mask = this.n - 1;
/* 1925 */     char[] key = this.key = new char[this.n + 1];
/* 1926 */     int[] value = this.value = new int[this.n + 1];
/* 1927 */     long[] link = this.link = new long[this.n + 1];
/* 1928 */     int prev = -1;
/* 1929 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1932 */     for (int i = this.size; i-- != 0; ) {
/* 1933 */       int pos; char k = s.readChar();
/* 1934 */       int v = s.readInt();
/* 1935 */       if (k == '\000') {
/* 1936 */         pos = this.n;
/* 1937 */         this.containsNullKey = true;
/*      */       } else {
/* 1939 */         pos = HashCommon.mix(k) & this.mask;
/* 1940 */         while (key[pos] != '\000')
/* 1941 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1943 */       key[pos] = k;
/* 1944 */       value[pos] = v;
/* 1945 */       if (this.first != -1) {
/* 1946 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1947 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1948 */         prev = pos; continue;
/*      */       } 
/* 1950 */       prev = this.first = pos;
/*      */       
/* 1952 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1955 */     this.last = prev;
/* 1956 */     if (prev != -1)
/*      */     {
/* 1958 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2IntLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */