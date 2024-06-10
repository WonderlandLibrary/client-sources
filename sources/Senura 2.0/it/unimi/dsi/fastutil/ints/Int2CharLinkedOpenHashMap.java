/*      */ package it.unimi.dsi.fastutil.ints;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharListIterator;
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
/*      */ public class Int2CharLinkedOpenHashMap
/*      */   extends AbstractInt2CharSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient int[] key;
/*      */   protected transient char[] value;
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
/*      */   protected transient Int2CharSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient IntSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient CharCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2CharLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new int[this.n + 1];
/*  162 */     this.value = new char[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2CharLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2CharLinkedOpenHashMap() {
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
/*      */   public Int2CharLinkedOpenHashMap(Map<? extends Integer, ? extends Character> m, float f) {
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
/*      */   public Int2CharLinkedOpenHashMap(Map<? extends Integer, ? extends Character> m) {
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
/*      */   public Int2CharLinkedOpenHashMap(Int2CharMap m, float f) {
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
/*      */   public Int2CharLinkedOpenHashMap(Int2CharMap m) {
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
/*      */   public Int2CharLinkedOpenHashMap(int[] k, char[] v, float f) {
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
/*      */   public Int2CharLinkedOpenHashMap(int[] k, char[] v) {
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
/*      */   private char removeEntry(int pos) {
/*  275 */     char oldValue = this.value[pos];
/*  276 */     this.size--;
/*  277 */     fixPointers(pos);
/*  278 */     shiftKeys(pos);
/*  279 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  280 */       rehash(this.n / 2); 
/*  281 */     return oldValue;
/*      */   }
/*      */   private char removeNullEntry() {
/*  284 */     this.containsNullKey = false;
/*  285 */     char oldValue = this.value[this.n];
/*  286 */     this.size--;
/*  287 */     fixPointers(this.n);
/*  288 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  289 */       rehash(this.n / 2); 
/*  290 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Integer, ? extends Character> m) {
/*  294 */     if (this.f <= 0.5D) {
/*  295 */       ensureCapacity(m.size());
/*      */     } else {
/*  297 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  299 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(int k) {
/*  303 */     if (k == 0) {
/*  304 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  306 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  309 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  310 */       return -(pos + 1); 
/*  311 */     if (k == curr) {
/*  312 */       return pos;
/*      */     }
/*      */     while (true) {
/*  315 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  316 */         return -(pos + 1); 
/*  317 */       if (k == curr)
/*  318 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, int k, char v) {
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
/*      */   public char put(int k, char v) {
/*  342 */     int pos = find(k);
/*  343 */     if (pos < 0) {
/*  344 */       insert(-pos - 1, k, v);
/*  345 */       return this.defRetValue;
/*      */     } 
/*  347 */     char oldValue = this.value[pos];
/*  348 */     this.value[pos] = v;
/*  349 */     return oldValue;
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
/*  362 */     int[] key = this.key; while (true) {
/*      */       int curr, last;
/*  364 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  366 */         if ((curr = key[pos]) == 0) {
/*  367 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  370 */         int slot = HashCommon.mix(curr) & this.mask;
/*  371 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  373 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  375 */       key[last] = curr;
/*  376 */       this.value[last] = this.value[pos];
/*  377 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char remove(int k) {
/*  383 */     if (k == 0) {
/*  384 */       if (this.containsNullKey)
/*  385 */         return removeNullEntry(); 
/*  386 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  389 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  392 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  393 */       return this.defRetValue; 
/*  394 */     if (k == curr)
/*  395 */       return removeEntry(pos); 
/*      */     while (true) {
/*  397 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  398 */         return this.defRetValue; 
/*  399 */       if (k == curr)
/*  400 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private char setValue(int pos, char v) {
/*  404 */     char oldValue = this.value[pos];
/*  405 */     this.value[pos] = v;
/*  406 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char removeFirstChar() {
/*  417 */     if (this.size == 0)
/*  418 */       throw new NoSuchElementException(); 
/*  419 */     int pos = this.first;
/*      */     
/*  421 */     this.first = (int)this.link[pos];
/*  422 */     if (0 <= this.first)
/*      */     {
/*  424 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  426 */     this.size--;
/*  427 */     char v = this.value[pos];
/*  428 */     if (pos == this.n) {
/*  429 */       this.containsNullKey = false;
/*      */     } else {
/*  431 */       shiftKeys(pos);
/*  432 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  433 */       rehash(this.n / 2); 
/*  434 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char removeLastChar() {
/*  444 */     if (this.size == 0)
/*  445 */       throw new NoSuchElementException(); 
/*  446 */     int pos = this.last;
/*      */     
/*  448 */     this.last = (int)(this.link[pos] >>> 32L);
/*  449 */     if (0 <= this.last)
/*      */     {
/*  451 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  453 */     this.size--;
/*  454 */     char v = this.value[pos];
/*  455 */     if (pos == this.n) {
/*  456 */       this.containsNullKey = false;
/*      */     } else {
/*  458 */       shiftKeys(pos);
/*  459 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  460 */       rehash(this.n / 2); 
/*  461 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  464 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  466 */     if (this.last == i) {
/*  467 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  469 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  471 */       long linki = this.link[i];
/*  472 */       int prev = (int)(linki >>> 32L);
/*  473 */       int next = (int)linki;
/*  474 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  475 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  477 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  478 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  479 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  482 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  484 */     if (this.first == i) {
/*  485 */       this.first = (int)this.link[i];
/*      */       
/*  487 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  489 */       long linki = this.link[i];
/*  490 */       int prev = (int)(linki >>> 32L);
/*  491 */       int next = (int)linki;
/*  492 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  493 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  495 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  496 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  497 */     this.last = i;
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
/*      */   public char getAndMoveToFirst(int k) {
/*  509 */     if (k == 0) {
/*  510 */       if (this.containsNullKey) {
/*  511 */         moveIndexToFirst(this.n);
/*  512 */         return this.value[this.n];
/*      */       } 
/*  514 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  517 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  520 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  521 */       return this.defRetValue; 
/*  522 */     if (k == curr) {
/*  523 */       moveIndexToFirst(pos);
/*  524 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  528 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  529 */         return this.defRetValue; 
/*  530 */       if (k == curr) {
/*  531 */         moveIndexToFirst(pos);
/*  532 */         return this.value[pos];
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
/*      */   public char getAndMoveToLast(int k) {
/*  546 */     if (k == 0) {
/*  547 */       if (this.containsNullKey) {
/*  548 */         moveIndexToLast(this.n);
/*  549 */         return this.value[this.n];
/*      */       } 
/*  551 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  554 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  557 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  558 */       return this.defRetValue; 
/*  559 */     if (k == curr) {
/*  560 */       moveIndexToLast(pos);
/*  561 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  565 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  566 */         return this.defRetValue; 
/*  567 */       if (k == curr) {
/*  568 */         moveIndexToLast(pos);
/*  569 */         return this.value[pos];
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
/*      */   public char putAndMoveToFirst(int k, char v) {
/*      */     int pos;
/*  586 */     if (k == 0) {
/*  587 */       if (this.containsNullKey) {
/*  588 */         moveIndexToFirst(this.n);
/*  589 */         return setValue(this.n, v);
/*      */       } 
/*  591 */       this.containsNullKey = true;
/*  592 */       pos = this.n;
/*      */     } else {
/*      */       
/*  595 */       int[] key = this.key;
/*      */       int curr;
/*  597 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  598 */         if (curr == k) {
/*  599 */           moveIndexToFirst(pos);
/*  600 */           return setValue(pos, v);
/*      */         } 
/*  602 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  603 */           if (curr == k) {
/*  604 */             moveIndexToFirst(pos);
/*  605 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  609 */     }  this.key[pos] = k;
/*  610 */     this.value[pos] = v;
/*  611 */     if (this.size == 0) {
/*  612 */       this.first = this.last = pos;
/*      */       
/*  614 */       this.link[pos] = -1L;
/*      */     } else {
/*  616 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  617 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  618 */       this.first = pos;
/*      */     } 
/*  620 */     if (this.size++ >= this.maxFill) {
/*  621 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  624 */     return this.defRetValue;
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
/*      */   public char putAndMoveToLast(int k, char v) {
/*      */     int pos;
/*  639 */     if (k == 0) {
/*  640 */       if (this.containsNullKey) {
/*  641 */         moveIndexToLast(this.n);
/*  642 */         return setValue(this.n, v);
/*      */       } 
/*  644 */       this.containsNullKey = true;
/*  645 */       pos = this.n;
/*      */     } else {
/*      */       
/*  648 */       int[] key = this.key;
/*      */       int curr;
/*  650 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  651 */         if (curr == k) {
/*  652 */           moveIndexToLast(pos);
/*  653 */           return setValue(pos, v);
/*      */         } 
/*  655 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  656 */           if (curr == k) {
/*  657 */             moveIndexToLast(pos);
/*  658 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  662 */     }  this.key[pos] = k;
/*  663 */     this.value[pos] = v;
/*  664 */     if (this.size == 0) {
/*  665 */       this.first = this.last = pos;
/*      */       
/*  667 */       this.link[pos] = -1L;
/*      */     } else {
/*  669 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  670 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  671 */       this.last = pos;
/*      */     } 
/*  673 */     if (this.size++ >= this.maxFill) {
/*  674 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  677 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char get(int k) {
/*  682 */     if (k == 0) {
/*  683 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  685 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  688 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  689 */       return this.defRetValue; 
/*  690 */     if (k == curr) {
/*  691 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  694 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  695 */         return this.defRetValue; 
/*  696 */       if (k == curr) {
/*  697 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(int k) {
/*  703 */     if (k == 0) {
/*  704 */       return this.containsNullKey;
/*      */     }
/*  706 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  709 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  710 */       return false; 
/*  711 */     if (k == curr) {
/*  712 */       return true;
/*      */     }
/*      */     while (true) {
/*  715 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  716 */         return false; 
/*  717 */       if (k == curr)
/*  718 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  723 */     char[] value = this.value;
/*  724 */     int[] key = this.key;
/*  725 */     if (this.containsNullKey && value[this.n] == v)
/*  726 */       return true; 
/*  727 */     for (int i = this.n; i-- != 0;) {
/*  728 */       if (key[i] != 0 && value[i] == v)
/*  729 */         return true; 
/*  730 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(int k, char defaultValue) {
/*  736 */     if (k == 0) {
/*  737 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  739 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  742 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  743 */       return defaultValue; 
/*  744 */     if (k == curr) {
/*  745 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  748 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  749 */         return defaultValue; 
/*  750 */       if (k == curr) {
/*  751 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char putIfAbsent(int k, char v) {
/*  757 */     int pos = find(k);
/*  758 */     if (pos >= 0)
/*  759 */       return this.value[pos]; 
/*  760 */     insert(-pos - 1, k, v);
/*  761 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(int k, char v) {
/*  767 */     if (k == 0) {
/*  768 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  769 */         removeNullEntry();
/*  770 */         return true;
/*      */       } 
/*  772 */       return false;
/*      */     } 
/*      */     
/*  775 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  778 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  779 */       return false; 
/*  780 */     if (k == curr && v == this.value[pos]) {
/*  781 */       removeEntry(pos);
/*  782 */       return true;
/*      */     } 
/*      */     while (true) {
/*  785 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  786 */         return false; 
/*  787 */       if (k == curr && v == this.value[pos]) {
/*  788 */         removeEntry(pos);
/*  789 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(int k, char oldValue, char v) {
/*  796 */     int pos = find(k);
/*  797 */     if (pos < 0 || oldValue != this.value[pos])
/*  798 */       return false; 
/*  799 */     this.value[pos] = v;
/*  800 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public char replace(int k, char v) {
/*  805 */     int pos = find(k);
/*  806 */     if (pos < 0)
/*  807 */       return this.defRetValue; 
/*  808 */     char oldValue = this.value[pos];
/*  809 */     this.value[pos] = v;
/*  810 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char computeIfAbsent(int k, IntUnaryOperator mappingFunction) {
/*  815 */     Objects.requireNonNull(mappingFunction);
/*  816 */     int pos = find(k);
/*  817 */     if (pos >= 0)
/*  818 */       return this.value[pos]; 
/*  819 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
/*  820 */     insert(-pos - 1, k, newValue);
/*  821 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfAbsentNullable(int k, IntFunction<? extends Character> mappingFunction) {
/*  827 */     Objects.requireNonNull(mappingFunction);
/*  828 */     int pos = find(k);
/*  829 */     if (pos >= 0)
/*  830 */       return this.value[pos]; 
/*  831 */     Character newValue = mappingFunction.apply(k);
/*  832 */     if (newValue == null)
/*  833 */       return this.defRetValue; 
/*  834 */     char v = newValue.charValue();
/*  835 */     insert(-pos - 1, k, v);
/*  836 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfPresent(int k, BiFunction<? super Integer, ? super Character, ? extends Character> remappingFunction) {
/*  842 */     Objects.requireNonNull(remappingFunction);
/*  843 */     int pos = find(k);
/*  844 */     if (pos < 0)
/*  845 */       return this.defRetValue; 
/*  846 */     Character newValue = remappingFunction.apply(Integer.valueOf(k), Character.valueOf(this.value[pos]));
/*  847 */     if (newValue == null) {
/*  848 */       if (k == 0) {
/*  849 */         removeNullEntry();
/*      */       } else {
/*  851 */         removeEntry(pos);
/*  852 */       }  return this.defRetValue;
/*      */     } 
/*  854 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char compute(int k, BiFunction<? super Integer, ? super Character, ? extends Character> remappingFunction) {
/*  860 */     Objects.requireNonNull(remappingFunction);
/*  861 */     int pos = find(k);
/*  862 */     Character newValue = remappingFunction.apply(Integer.valueOf(k), 
/*  863 */         (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  864 */     if (newValue == null) {
/*  865 */       if (pos >= 0)
/*  866 */         if (k == 0) {
/*  867 */           removeNullEntry();
/*      */         } else {
/*  869 */           removeEntry(pos);
/*      */         }  
/*  871 */       return this.defRetValue;
/*      */     } 
/*  873 */     char newVal = newValue.charValue();
/*  874 */     if (pos < 0) {
/*  875 */       insert(-pos - 1, k, newVal);
/*  876 */       return newVal;
/*      */     } 
/*  878 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char merge(int k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  884 */     Objects.requireNonNull(remappingFunction);
/*  885 */     int pos = find(k);
/*  886 */     if (pos < 0) {
/*  887 */       insert(-pos - 1, k, v);
/*  888 */       return v;
/*      */     } 
/*  890 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  891 */     if (newValue == null) {
/*  892 */       if (k == 0) {
/*  893 */         removeNullEntry();
/*      */       } else {
/*  895 */         removeEntry(pos);
/*  896 */       }  return this.defRetValue;
/*      */     } 
/*  898 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
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
/*  909 */     if (this.size == 0)
/*      */       return; 
/*  911 */     this.size = 0;
/*  912 */     this.containsNullKey = false;
/*  913 */     Arrays.fill(this.key, 0);
/*  914 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  918 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  922 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Int2CharMap.Entry, Map.Entry<Integer, Character>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  934 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public int getIntKey() {
/*  940 */       return Int2CharLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public char getCharValue() {
/*  944 */       return Int2CharLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public char setValue(char v) {
/*  948 */       char oldValue = Int2CharLinkedOpenHashMap.this.value[this.index];
/*  949 */       Int2CharLinkedOpenHashMap.this.value[this.index] = v;
/*  950 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getKey() {
/*  960 */       return Integer.valueOf(Int2CharLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getValue() {
/*  970 */       return Character.valueOf(Int2CharLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character setValue(Character v) {
/*  980 */       return Character.valueOf(setValue(v.charValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  985 */       if (!(o instanceof Map.Entry))
/*  986 */         return false; 
/*  987 */       Map.Entry<Integer, Character> e = (Map.Entry<Integer, Character>)o;
/*  988 */       return (Int2CharLinkedOpenHashMap.this.key[this.index] == ((Integer)e.getKey()).intValue() && Int2CharLinkedOpenHashMap.this.value[this.index] == ((Character)e.getValue()).charValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  992 */       return Int2CharLinkedOpenHashMap.this.key[this.index] ^ Int2CharLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  996 */       return Int2CharLinkedOpenHashMap.this.key[this.index] + "=>" + Int2CharLinkedOpenHashMap.this.value[this.index];
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
/* 1007 */     if (this.size == 0) {
/* 1008 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1011 */     if (this.first == i) {
/* 1012 */       this.first = (int)this.link[i];
/* 1013 */       if (0 <= this.first)
/*      */       {
/* 1015 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1019 */     if (this.last == i) {
/* 1020 */       this.last = (int)(this.link[i] >>> 32L);
/* 1021 */       if (0 <= this.last)
/*      */       {
/* 1023 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1027 */     long linki = this.link[i];
/* 1028 */     int prev = (int)(linki >>> 32L);
/* 1029 */     int next = (int)linki;
/* 1030 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1031 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1044 */     if (this.size == 1) {
/* 1045 */       this.first = this.last = d;
/*      */       
/* 1047 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1050 */     if (this.first == s) {
/* 1051 */       this.first = d;
/* 1052 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1053 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1056 */     if (this.last == s) {
/* 1057 */       this.last = d;
/* 1058 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1059 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1062 */     long links = this.link[s];
/* 1063 */     int prev = (int)(links >>> 32L);
/* 1064 */     int next = (int)links;
/* 1065 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1066 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1067 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int firstIntKey() {
/* 1076 */     if (this.size == 0)
/* 1077 */       throw new NoSuchElementException(); 
/* 1078 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIntKey() {
/* 1087 */     if (this.size == 0)
/* 1088 */       throw new NoSuchElementException(); 
/* 1089 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2CharSortedMap tailMap(int from) {
/* 1098 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2CharSortedMap headMap(int to) {
/* 1107 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2CharSortedMap subMap(int from, int to) {
/* 1116 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntComparator comparator() {
/* 1125 */     return null;
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
/* 1140 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1146 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1151 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1157 */     int index = -1;
/*      */     protected MapIterator() {
/* 1159 */       this.next = Int2CharLinkedOpenHashMap.this.first;
/* 1160 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(int from) {
/* 1163 */       if (from == 0) {
/* 1164 */         if (Int2CharLinkedOpenHashMap.this.containsNullKey) {
/* 1165 */           this.next = (int)Int2CharLinkedOpenHashMap.this.link[Int2CharLinkedOpenHashMap.this.n];
/* 1166 */           this.prev = Int2CharLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1169 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1171 */       if (Int2CharLinkedOpenHashMap.this.key[Int2CharLinkedOpenHashMap.this.last] == from) {
/* 1172 */         this.prev = Int2CharLinkedOpenHashMap.this.last;
/* 1173 */         this.index = Int2CharLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1177 */       int pos = HashCommon.mix(from) & Int2CharLinkedOpenHashMap.this.mask;
/*      */       
/* 1179 */       while (Int2CharLinkedOpenHashMap.this.key[pos] != 0) {
/* 1180 */         if (Int2CharLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1182 */           this.next = (int)Int2CharLinkedOpenHashMap.this.link[pos];
/* 1183 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1186 */         pos = pos + 1 & Int2CharLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1188 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1191 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1194 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1197 */       if (this.index >= 0)
/*      */         return; 
/* 1199 */       if (this.prev == -1) {
/* 1200 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1203 */       if (this.next == -1) {
/* 1204 */         this.index = Int2CharLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1207 */       int pos = Int2CharLinkedOpenHashMap.this.first;
/* 1208 */       this.index = 1;
/* 1209 */       while (pos != this.prev) {
/* 1210 */         pos = (int)Int2CharLinkedOpenHashMap.this.link[pos];
/* 1211 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1215 */       ensureIndexKnown();
/* 1216 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1219 */       ensureIndexKnown();
/* 1220 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1223 */       if (!hasNext())
/* 1224 */         throw new NoSuchElementException(); 
/* 1225 */       this.curr = this.next;
/* 1226 */       this.next = (int)Int2CharLinkedOpenHashMap.this.link[this.curr];
/* 1227 */       this.prev = this.curr;
/* 1228 */       if (this.index >= 0)
/* 1229 */         this.index++; 
/* 1230 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1233 */       if (!hasPrevious())
/* 1234 */         throw new NoSuchElementException(); 
/* 1235 */       this.curr = this.prev;
/* 1236 */       this.prev = (int)(Int2CharLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1237 */       this.next = this.curr;
/* 1238 */       if (this.index >= 0)
/* 1239 */         this.index--; 
/* 1240 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1243 */       ensureIndexKnown();
/* 1244 */       if (this.curr == -1)
/* 1245 */         throw new IllegalStateException(); 
/* 1246 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1251 */         this.index--;
/* 1252 */         this.prev = (int)(Int2CharLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1254 */         this.next = (int)Int2CharLinkedOpenHashMap.this.link[this.curr];
/* 1255 */       }  Int2CharLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1260 */       if (this.prev == -1) {
/* 1261 */         Int2CharLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1263 */         Int2CharLinkedOpenHashMap.this.link[this.prev] = Int2CharLinkedOpenHashMap.this.link[this.prev] ^ (Int2CharLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1264 */       }  if (this.next == -1) {
/* 1265 */         Int2CharLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1267 */         Int2CharLinkedOpenHashMap.this.link[this.next] = Int2CharLinkedOpenHashMap.this.link[this.next] ^ (Int2CharLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1268 */       }  int pos = this.curr;
/* 1269 */       this.curr = -1;
/* 1270 */       if (pos == Int2CharLinkedOpenHashMap.this.n) {
/* 1271 */         Int2CharLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1274 */         int[] key = Int2CharLinkedOpenHashMap.this.key;
/*      */         
/*      */         while (true) {
/*      */           int curr, last;
/* 1278 */           pos = (last = pos) + 1 & Int2CharLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1280 */             if ((curr = key[pos]) == 0) {
/* 1281 */               key[last] = 0;
/*      */               return;
/*      */             } 
/* 1284 */             int slot = HashCommon.mix(curr) & Int2CharLinkedOpenHashMap.this.mask;
/* 1285 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1287 */             pos = pos + 1 & Int2CharLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1289 */           key[last] = curr;
/* 1290 */           Int2CharLinkedOpenHashMap.this.value[last] = Int2CharLinkedOpenHashMap.this.value[pos];
/* 1291 */           if (this.next == pos)
/* 1292 */             this.next = last; 
/* 1293 */           if (this.prev == pos)
/* 1294 */             this.prev = last; 
/* 1295 */           Int2CharLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1300 */       int i = n;
/* 1301 */       while (i-- != 0 && hasNext())
/* 1302 */         nextEntry(); 
/* 1303 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1306 */       int i = n;
/* 1307 */       while (i-- != 0 && hasPrevious())
/* 1308 */         previousEntry(); 
/* 1309 */       return n - i - 1;
/*      */     }
/*      */     public void set(Int2CharMap.Entry ok) {
/* 1312 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Int2CharMap.Entry ok) {
/* 1315 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Int2CharMap.Entry> {
/*      */     private Int2CharLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator(int from) {
/* 1323 */       super(from);
/*      */     }
/*      */     
/*      */     public Int2CharLinkedOpenHashMap.MapEntry next() {
/* 1327 */       return this.entry = new Int2CharLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     public EntryIterator() {}
/*      */     public Int2CharLinkedOpenHashMap.MapEntry previous() {
/* 1331 */       return this.entry = new Int2CharLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1335 */       super.remove();
/* 1336 */       this.entry.index = -1;
/*      */     }
/*      */   }
/*      */   
/* 1340 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Int2CharMap.Entry> { final Int2CharLinkedOpenHashMap.MapEntry entry = new Int2CharLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(int from) {
/* 1344 */       super(from);
/*      */     }
/*      */     
/*      */     public Int2CharLinkedOpenHashMap.MapEntry next() {
/* 1348 */       this.entry.index = nextEntry();
/* 1349 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Int2CharLinkedOpenHashMap.MapEntry previous() {
/* 1353 */       this.entry.index = previousEntry();
/* 1354 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Int2CharMap.Entry> implements Int2CharSortedMap.FastSortedEntrySet { public ObjectBidirectionalIterator<Int2CharMap.Entry> iterator() {
/* 1360 */       return (ObjectBidirectionalIterator<Int2CharMap.Entry>)new Int2CharLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     private MapEntrySet() {}
/*      */     public Comparator<? super Int2CharMap.Entry> comparator() {
/* 1364 */       return null;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Int2CharMap.Entry> subSet(Int2CharMap.Entry fromElement, Int2CharMap.Entry toElement) {
/* 1368 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Int2CharMap.Entry> headSet(Int2CharMap.Entry toElement) {
/* 1372 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Int2CharMap.Entry> tailSet(Int2CharMap.Entry fromElement) {
/* 1376 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Int2CharMap.Entry first() {
/* 1380 */       if (Int2CharLinkedOpenHashMap.this.size == 0)
/* 1381 */         throw new NoSuchElementException(); 
/* 1382 */       return new Int2CharLinkedOpenHashMap.MapEntry(Int2CharLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Int2CharMap.Entry last() {
/* 1386 */       if (Int2CharLinkedOpenHashMap.this.size == 0)
/* 1387 */         throw new NoSuchElementException(); 
/* 1388 */       return new Int2CharLinkedOpenHashMap.MapEntry(Int2CharLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1393 */       if (!(o instanceof Map.Entry))
/* 1394 */         return false; 
/* 1395 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1396 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1397 */         return false; 
/* 1398 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 1399 */         return false; 
/* 1400 */       int k = ((Integer)e.getKey()).intValue();
/* 1401 */       char v = ((Character)e.getValue()).charValue();
/* 1402 */       if (k == 0) {
/* 1403 */         return (Int2CharLinkedOpenHashMap.this.containsNullKey && Int2CharLinkedOpenHashMap.this.value[Int2CharLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1405 */       int[] key = Int2CharLinkedOpenHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/* 1408 */       if ((curr = key[pos = HashCommon.mix(k) & Int2CharLinkedOpenHashMap.this.mask]) == 0)
/* 1409 */         return false; 
/* 1410 */       if (k == curr) {
/* 1411 */         return (Int2CharLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1414 */         if ((curr = key[pos = pos + 1 & Int2CharLinkedOpenHashMap.this.mask]) == 0)
/* 1415 */           return false; 
/* 1416 */         if (k == curr) {
/* 1417 */           return (Int2CharLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1423 */       if (!(o instanceof Map.Entry))
/* 1424 */         return false; 
/* 1425 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1426 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1427 */         return false; 
/* 1428 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 1429 */         return false; 
/* 1430 */       int k = ((Integer)e.getKey()).intValue();
/* 1431 */       char v = ((Character)e.getValue()).charValue();
/* 1432 */       if (k == 0) {
/* 1433 */         if (Int2CharLinkedOpenHashMap.this.containsNullKey && Int2CharLinkedOpenHashMap.this.value[Int2CharLinkedOpenHashMap.this.n] == v) {
/* 1434 */           Int2CharLinkedOpenHashMap.this.removeNullEntry();
/* 1435 */           return true;
/*      */         } 
/* 1437 */         return false;
/*      */       } 
/*      */       
/* 1440 */       int[] key = Int2CharLinkedOpenHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/* 1443 */       if ((curr = key[pos = HashCommon.mix(k) & Int2CharLinkedOpenHashMap.this.mask]) == 0)
/* 1444 */         return false; 
/* 1445 */       if (curr == k) {
/* 1446 */         if (Int2CharLinkedOpenHashMap.this.value[pos] == v) {
/* 1447 */           Int2CharLinkedOpenHashMap.this.removeEntry(pos);
/* 1448 */           return true;
/*      */         } 
/* 1450 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1453 */         if ((curr = key[pos = pos + 1 & Int2CharLinkedOpenHashMap.this.mask]) == 0)
/* 1454 */           return false; 
/* 1455 */         if (curr == k && 
/* 1456 */           Int2CharLinkedOpenHashMap.this.value[pos] == v) {
/* 1457 */           Int2CharLinkedOpenHashMap.this.removeEntry(pos);
/* 1458 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1465 */       return Int2CharLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1469 */       Int2CharLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Int2CharMap.Entry> iterator(Int2CharMap.Entry from) {
/* 1484 */       return new Int2CharLinkedOpenHashMap.EntryIterator(from.getIntKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Int2CharMap.Entry> fastIterator() {
/* 1495 */       return new Int2CharLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Int2CharMap.Entry> fastIterator(Int2CharMap.Entry from) {
/* 1510 */       return new Int2CharLinkedOpenHashMap.FastEntryIterator(from.getIntKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Int2CharMap.Entry> consumer) {
/* 1515 */       for (int i = Int2CharLinkedOpenHashMap.this.size, next = Int2CharLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1516 */         int curr = next;
/* 1517 */         next = (int)Int2CharLinkedOpenHashMap.this.link[curr];
/* 1518 */         consumer.accept(new AbstractInt2CharMap.BasicEntry(Int2CharLinkedOpenHashMap.this.key[curr], Int2CharLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Int2CharMap.Entry> consumer) {
/* 1524 */       AbstractInt2CharMap.BasicEntry entry = new AbstractInt2CharMap.BasicEntry();
/* 1525 */       for (int i = Int2CharLinkedOpenHashMap.this.size, next = Int2CharLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1526 */         int curr = next;
/* 1527 */         next = (int)Int2CharLinkedOpenHashMap.this.link[curr];
/* 1528 */         entry.key = Int2CharLinkedOpenHashMap.this.key[curr];
/* 1529 */         entry.value = Int2CharLinkedOpenHashMap.this.value[curr];
/* 1530 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Int2CharSortedMap.FastSortedEntrySet int2CharEntrySet() {
/* 1536 */     if (this.entries == null)
/* 1537 */       this.entries = new MapEntrySet(); 
/* 1538 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends MapIterator
/*      */     implements IntListIterator
/*      */   {
/*      */     public KeyIterator(int k) {
/* 1551 */       super(k);
/*      */     }
/*      */     
/*      */     public int previousInt() {
/* 1555 */       return Int2CharLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public int nextInt() {
/* 1562 */       return Int2CharLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractIntSortedSet { private KeySet() {}
/*      */     
/*      */     public IntListIterator iterator(int from) {
/* 1568 */       return new Int2CharLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public IntListIterator iterator() {
/* 1572 */       return new Int2CharLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1577 */       if (Int2CharLinkedOpenHashMap.this.containsNullKey)
/* 1578 */         consumer.accept(Int2CharLinkedOpenHashMap.this.key[Int2CharLinkedOpenHashMap.this.n]); 
/* 1579 */       for (int pos = Int2CharLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1580 */         int k = Int2CharLinkedOpenHashMap.this.key[pos];
/* 1581 */         if (k != 0)
/* 1582 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1587 */       return Int2CharLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(int k) {
/* 1591 */       return Int2CharLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(int k) {
/* 1595 */       int oldSize = Int2CharLinkedOpenHashMap.this.size;
/* 1596 */       Int2CharLinkedOpenHashMap.this.remove(k);
/* 1597 */       return (Int2CharLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1601 */       Int2CharLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public int firstInt() {
/* 1605 */       if (Int2CharLinkedOpenHashMap.this.size == 0)
/* 1606 */         throw new NoSuchElementException(); 
/* 1607 */       return Int2CharLinkedOpenHashMap.this.key[Int2CharLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public int lastInt() {
/* 1611 */       if (Int2CharLinkedOpenHashMap.this.size == 0)
/* 1612 */         throw new NoSuchElementException(); 
/* 1613 */       return Int2CharLinkedOpenHashMap.this.key[Int2CharLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public IntComparator comparator() {
/* 1617 */       return null;
/*      */     }
/*      */     
/*      */     public IntSortedSet tailSet(int from) {
/* 1621 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public IntSortedSet headSet(int to) {
/* 1625 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public IntSortedSet subSet(int from, int to) {
/* 1629 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public IntSortedSet keySet() {
/* 1634 */     if (this.keys == null)
/* 1635 */       this.keys = new KeySet(); 
/* 1636 */     return this.keys;
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
/*      */     implements CharListIterator
/*      */   {
/*      */     public char previousChar() {
/* 1650 */       return Int2CharLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1657 */       return Int2CharLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public CharCollection values() {
/* 1662 */     if (this.values == null)
/* 1663 */       this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/* 1666 */             return (CharIterator)new Int2CharLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1670 */             return Int2CharLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(char v) {
/* 1674 */             return Int2CharLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1678 */             Int2CharLinkedOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1683 */             if (Int2CharLinkedOpenHashMap.this.containsNullKey)
/* 1684 */               consumer.accept(Int2CharLinkedOpenHashMap.this.value[Int2CharLinkedOpenHashMap.this.n]); 
/* 1685 */             for (int pos = Int2CharLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1686 */               if (Int2CharLinkedOpenHashMap.this.key[pos] != 0)
/* 1687 */                 consumer.accept(Int2CharLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1690 */     return this.values;
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
/* 1707 */     return trim(this.size);
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
/* 1731 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1732 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1733 */       return true; 
/*      */     try {
/* 1735 */       rehash(l);
/* 1736 */     } catch (OutOfMemoryError cantDoIt) {
/* 1737 */       return false;
/*      */     } 
/* 1739 */     return true;
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
/* 1755 */     int[] key = this.key;
/* 1756 */     char[] value = this.value;
/* 1757 */     int mask = newN - 1;
/* 1758 */     int[] newKey = new int[newN + 1];
/* 1759 */     char[] newValue = new char[newN + 1];
/* 1760 */     int i = this.first, prev = -1, newPrev = -1;
/* 1761 */     long[] link = this.link;
/* 1762 */     long[] newLink = new long[newN + 1];
/* 1763 */     this.first = -1;
/* 1764 */     for (int j = this.size; j-- != 0; ) {
/* 1765 */       int pos; if (key[i] == 0) {
/* 1766 */         pos = newN;
/*      */       } else {
/* 1768 */         pos = HashCommon.mix(key[i]) & mask;
/* 1769 */         while (newKey[pos] != 0)
/* 1770 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1772 */       newKey[pos] = key[i];
/* 1773 */       newValue[pos] = value[i];
/* 1774 */       if (prev != -1) {
/* 1775 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1776 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1777 */         newPrev = pos;
/*      */       } else {
/* 1779 */         newPrev = this.first = pos;
/*      */         
/* 1781 */         newLink[pos] = -1L;
/*      */       } 
/* 1783 */       int t = i;
/* 1784 */       i = (int)link[i];
/* 1785 */       prev = t;
/*      */     } 
/* 1787 */     this.link = newLink;
/* 1788 */     this.last = newPrev;
/* 1789 */     if (newPrev != -1)
/*      */     {
/* 1791 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1792 */     this.n = newN;
/* 1793 */     this.mask = mask;
/* 1794 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1795 */     this.key = newKey;
/* 1796 */     this.value = newValue;
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
/*      */   public Int2CharLinkedOpenHashMap clone() {
/*      */     Int2CharLinkedOpenHashMap c;
/*      */     try {
/* 1813 */       c = (Int2CharLinkedOpenHashMap)super.clone();
/* 1814 */     } catch (CloneNotSupportedException cantHappen) {
/* 1815 */       throw new InternalError();
/*      */     } 
/* 1817 */     c.keys = null;
/* 1818 */     c.values = null;
/* 1819 */     c.entries = null;
/* 1820 */     c.containsNullKey = this.containsNullKey;
/* 1821 */     c.key = (int[])this.key.clone();
/* 1822 */     c.value = (char[])this.value.clone();
/* 1823 */     c.link = (long[])this.link.clone();
/* 1824 */     return c;
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
/* 1837 */     int h = 0;
/* 1838 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1839 */       while (this.key[i] == 0)
/* 1840 */         i++; 
/* 1841 */       t = this.key[i];
/* 1842 */       t ^= this.value[i];
/* 1843 */       h += t;
/* 1844 */       i++;
/*      */     } 
/*      */     
/* 1847 */     if (this.containsNullKey)
/* 1848 */       h += this.value[this.n]; 
/* 1849 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1852 */     int[] key = this.key;
/* 1853 */     char[] value = this.value;
/* 1854 */     MapIterator i = new MapIterator();
/* 1855 */     s.defaultWriteObject();
/* 1856 */     for (int j = this.size; j-- != 0; ) {
/* 1857 */       int e = i.nextEntry();
/* 1858 */       s.writeInt(key[e]);
/* 1859 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1864 */     s.defaultReadObject();
/* 1865 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1866 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1867 */     this.mask = this.n - 1;
/* 1868 */     int[] key = this.key = new int[this.n + 1];
/* 1869 */     char[] value = this.value = new char[this.n + 1];
/* 1870 */     long[] link = this.link = new long[this.n + 1];
/* 1871 */     int prev = -1;
/* 1872 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1875 */     for (int i = this.size; i-- != 0; ) {
/* 1876 */       int pos, k = s.readInt();
/* 1877 */       char v = s.readChar();
/* 1878 */       if (k == 0) {
/* 1879 */         pos = this.n;
/* 1880 */         this.containsNullKey = true;
/*      */       } else {
/* 1882 */         pos = HashCommon.mix(k) & this.mask;
/* 1883 */         while (key[pos] != 0)
/* 1884 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1886 */       key[pos] = k;
/* 1887 */       value[pos] = v;
/* 1888 */       if (this.first != -1) {
/* 1889 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1890 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1891 */         prev = pos; continue;
/*      */       } 
/* 1893 */       prev = this.first = pos;
/*      */       
/* 1895 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1898 */     this.last = prev;
/* 1899 */     if (prev != -1)
/*      */     {
/* 1901 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2CharLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */