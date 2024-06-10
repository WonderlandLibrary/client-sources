/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharListIterator;
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
/*      */ import java.util.SortedMap;
/*      */ import java.util.SortedSet;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.ToIntFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2CharLinkedOpenHashMap<K>
/*      */   extends AbstractObject2CharSortedMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
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
/*      */   protected transient Object2CharSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient CharCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2CharLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = (K[])new Object[this.n + 1];
/*  162 */     this.value = new char[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2CharLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2CharLinkedOpenHashMap() {
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
/*      */   public Object2CharLinkedOpenHashMap(Map<? extends K, ? extends Character> m, float f) {
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
/*      */   public Object2CharLinkedOpenHashMap(Map<? extends K, ? extends Character> m) {
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
/*      */   public Object2CharLinkedOpenHashMap(Object2CharMap<K> m, float f) {
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
/*      */   public Object2CharLinkedOpenHashMap(Object2CharMap<K> m) {
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
/*      */   public Object2CharLinkedOpenHashMap(K[] k, char[] v, float f) {
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
/*      */   public Object2CharLinkedOpenHashMap(K[] k, char[] v) {
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
/*  285 */     this.key[this.n] = null;
/*  286 */     char oldValue = this.value[this.n];
/*  287 */     this.size--;
/*  288 */     fixPointers(this.n);
/*  289 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  290 */       rehash(this.n / 2); 
/*  291 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Character> m) {
/*  295 */     if (this.f <= 0.5D) {
/*  296 */       ensureCapacity(m.size());
/*      */     } else {
/*  298 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  300 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  304 */     if (k == null) {
/*  305 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  307 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  310 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  311 */       return -(pos + 1); 
/*  312 */     if (k.equals(curr)) {
/*  313 */       return pos;
/*      */     }
/*      */     while (true) {
/*  316 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  317 */         return -(pos + 1); 
/*  318 */       if (k.equals(curr))
/*  319 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, char v) {
/*  323 */     if (pos == this.n)
/*  324 */       this.containsNullKey = true; 
/*  325 */     this.key[pos] = k;
/*  326 */     this.value[pos] = v;
/*  327 */     if (this.size == 0) {
/*  328 */       this.first = this.last = pos;
/*      */       
/*  330 */       this.link[pos] = -1L;
/*      */     } else {
/*  332 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  333 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  334 */       this.last = pos;
/*      */     } 
/*  336 */     if (this.size++ >= this.maxFill) {
/*  337 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public char put(K k, char v) {
/*  343 */     int pos = find(k);
/*  344 */     if (pos < 0) {
/*  345 */       insert(-pos - 1, k, v);
/*  346 */       return this.defRetValue;
/*      */     } 
/*  348 */     char oldValue = this.value[pos];
/*  349 */     this.value[pos] = v;
/*  350 */     return oldValue;
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
/*  363 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  365 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  367 */         if ((curr = key[pos]) == null) {
/*  368 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  371 */         int slot = HashCommon.mix(curr.hashCode()) & this.mask;
/*  372 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  374 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  376 */       key[last] = curr;
/*  377 */       this.value[last] = this.value[pos];
/*  378 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char removeChar(Object k) {
/*  384 */     if (k == null) {
/*  385 */       if (this.containsNullKey)
/*  386 */         return removeNullEntry(); 
/*  387 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  390 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  393 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  394 */       return this.defRetValue; 
/*  395 */     if (k.equals(curr))
/*  396 */       return removeEntry(pos); 
/*      */     while (true) {
/*  398 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  399 */         return this.defRetValue; 
/*  400 */       if (k.equals(curr))
/*  401 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private char setValue(int pos, char v) {
/*  405 */     char oldValue = this.value[pos];
/*  406 */     this.value[pos] = v;
/*  407 */     return oldValue;
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
/*  418 */     if (this.size == 0)
/*  419 */       throw new NoSuchElementException(); 
/*  420 */     int pos = this.first;
/*      */     
/*  422 */     this.first = (int)this.link[pos];
/*  423 */     if (0 <= this.first)
/*      */     {
/*  425 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  427 */     this.size--;
/*  428 */     char v = this.value[pos];
/*  429 */     if (pos == this.n) {
/*  430 */       this.containsNullKey = false;
/*  431 */       this.key[this.n] = null;
/*      */     } else {
/*  433 */       shiftKeys(pos);
/*  434 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  435 */       rehash(this.n / 2); 
/*  436 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char removeLastChar() {
/*  446 */     if (this.size == 0)
/*  447 */       throw new NoSuchElementException(); 
/*  448 */     int pos = this.last;
/*      */     
/*  450 */     this.last = (int)(this.link[pos] >>> 32L);
/*  451 */     if (0 <= this.last)
/*      */     {
/*  453 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  455 */     this.size--;
/*  456 */     char v = this.value[pos];
/*  457 */     if (pos == this.n) {
/*  458 */       this.containsNullKey = false;
/*  459 */       this.key[this.n] = null;
/*      */     } else {
/*  461 */       shiftKeys(pos);
/*  462 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  463 */       rehash(this.n / 2); 
/*  464 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  467 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  469 */     if (this.last == i) {
/*  470 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  472 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  474 */       long linki = this.link[i];
/*  475 */       int prev = (int)(linki >>> 32L);
/*  476 */       int next = (int)linki;
/*  477 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  478 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  480 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  481 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  482 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  485 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  487 */     if (this.first == i) {
/*  488 */       this.first = (int)this.link[i];
/*      */       
/*  490 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  492 */       long linki = this.link[i];
/*  493 */       int prev = (int)(linki >>> 32L);
/*  494 */       int next = (int)linki;
/*  495 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  496 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  498 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  499 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  500 */     this.last = i;
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
/*      */   public char getAndMoveToFirst(K k) {
/*  512 */     if (k == null) {
/*  513 */       if (this.containsNullKey) {
/*  514 */         moveIndexToFirst(this.n);
/*  515 */         return this.value[this.n];
/*      */       } 
/*  517 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  520 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  523 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  524 */       return this.defRetValue; 
/*  525 */     if (k.equals(curr)) {
/*  526 */       moveIndexToFirst(pos);
/*  527 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  531 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  532 */         return this.defRetValue; 
/*  533 */       if (k.equals(curr)) {
/*  534 */         moveIndexToFirst(pos);
/*  535 */         return this.value[pos];
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
/*      */   public char getAndMoveToLast(K k) {
/*  549 */     if (k == null) {
/*  550 */       if (this.containsNullKey) {
/*  551 */         moveIndexToLast(this.n);
/*  552 */         return this.value[this.n];
/*      */       } 
/*  554 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  557 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  560 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  561 */       return this.defRetValue; 
/*  562 */     if (k.equals(curr)) {
/*  563 */       moveIndexToLast(pos);
/*  564 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  568 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  569 */         return this.defRetValue; 
/*  570 */       if (k.equals(curr)) {
/*  571 */         moveIndexToLast(pos);
/*  572 */         return this.value[pos];
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
/*      */   public char putAndMoveToFirst(K k, char v) {
/*      */     int pos;
/*  589 */     if (k == null) {
/*  590 */       if (this.containsNullKey) {
/*  591 */         moveIndexToFirst(this.n);
/*  592 */         return setValue(this.n, v);
/*      */       } 
/*  594 */       this.containsNullKey = true;
/*  595 */       pos = this.n;
/*      */     } else {
/*      */       
/*  598 */       K[] key = this.key;
/*      */       K curr;
/*  600 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  601 */         if (curr.equals(k)) {
/*  602 */           moveIndexToFirst(pos);
/*  603 */           return setValue(pos, v);
/*      */         } 
/*  605 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  606 */           if (curr.equals(k)) {
/*  607 */             moveIndexToFirst(pos);
/*  608 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  612 */     }  this.key[pos] = k;
/*  613 */     this.value[pos] = v;
/*  614 */     if (this.size == 0) {
/*  615 */       this.first = this.last = pos;
/*      */       
/*  617 */       this.link[pos] = -1L;
/*      */     } else {
/*  619 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  620 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  621 */       this.first = pos;
/*      */     } 
/*  623 */     if (this.size++ >= this.maxFill) {
/*  624 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  627 */     return this.defRetValue;
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
/*      */   public char putAndMoveToLast(K k, char v) {
/*      */     int pos;
/*  642 */     if (k == null) {
/*  643 */       if (this.containsNullKey) {
/*  644 */         moveIndexToLast(this.n);
/*  645 */         return setValue(this.n, v);
/*      */       } 
/*  647 */       this.containsNullKey = true;
/*  648 */       pos = this.n;
/*      */     } else {
/*      */       
/*  651 */       K[] key = this.key;
/*      */       K curr;
/*  653 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  654 */         if (curr.equals(k)) {
/*  655 */           moveIndexToLast(pos);
/*  656 */           return setValue(pos, v);
/*      */         } 
/*  658 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  659 */           if (curr.equals(k)) {
/*  660 */             moveIndexToLast(pos);
/*  661 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  665 */     }  this.key[pos] = k;
/*  666 */     this.value[pos] = v;
/*  667 */     if (this.size == 0) {
/*  668 */       this.first = this.last = pos;
/*      */       
/*  670 */       this.link[pos] = -1L;
/*      */     } else {
/*  672 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  673 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  674 */       this.last = pos;
/*      */     } 
/*  676 */     if (this.size++ >= this.maxFill) {
/*  677 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  680 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char getChar(Object k) {
/*  685 */     if (k == null) {
/*  686 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  688 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  691 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  692 */       return this.defRetValue; 
/*  693 */     if (k.equals(curr)) {
/*  694 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  697 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  698 */         return this.defRetValue; 
/*  699 */       if (k.equals(curr)) {
/*  700 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  706 */     if (k == null) {
/*  707 */       return this.containsNullKey;
/*      */     }
/*  709 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  712 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  713 */       return false; 
/*  714 */     if (k.equals(curr)) {
/*  715 */       return true;
/*      */     }
/*      */     while (true) {
/*  718 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  719 */         return false; 
/*  720 */       if (k.equals(curr))
/*  721 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  726 */     char[] value = this.value;
/*  727 */     K[] key = this.key;
/*  728 */     if (this.containsNullKey && value[this.n] == v)
/*  729 */       return true; 
/*  730 */     for (int i = this.n; i-- != 0;) {
/*  731 */       if (key[i] != null && value[i] == v)
/*  732 */         return true; 
/*  733 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(Object k, char defaultValue) {
/*  739 */     if (k == null) {
/*  740 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  742 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  745 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  746 */       return defaultValue; 
/*  747 */     if (k.equals(curr)) {
/*  748 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  751 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  752 */         return defaultValue; 
/*  753 */       if (k.equals(curr)) {
/*  754 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char putIfAbsent(K k, char v) {
/*  760 */     int pos = find(k);
/*  761 */     if (pos >= 0)
/*  762 */       return this.value[pos]; 
/*  763 */     insert(-pos - 1, k, v);
/*  764 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, char v) {
/*  770 */     if (k == null) {
/*  771 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  772 */         removeNullEntry();
/*  773 */         return true;
/*      */       } 
/*  775 */       return false;
/*      */     } 
/*      */     
/*  778 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  781 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  782 */       return false; 
/*  783 */     if (k.equals(curr) && v == this.value[pos]) {
/*  784 */       removeEntry(pos);
/*  785 */       return true;
/*      */     } 
/*      */     while (true) {
/*  788 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  789 */         return false; 
/*  790 */       if (k.equals(curr) && v == this.value[pos]) {
/*  791 */         removeEntry(pos);
/*  792 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, char oldValue, char v) {
/*  799 */     int pos = find(k);
/*  800 */     if (pos < 0 || oldValue != this.value[pos])
/*  801 */       return false; 
/*  802 */     this.value[pos] = v;
/*  803 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public char replace(K k, char v) {
/*  808 */     int pos = find(k);
/*  809 */     if (pos < 0)
/*  810 */       return this.defRetValue; 
/*  811 */     char oldValue = this.value[pos];
/*  812 */     this.value[pos] = v;
/*  813 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char computeCharIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
/*  818 */     Objects.requireNonNull(mappingFunction);
/*  819 */     int pos = find(k);
/*  820 */     if (pos >= 0)
/*  821 */       return this.value[pos]; 
/*  822 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
/*  823 */     insert(-pos - 1, k, newValue);
/*  824 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeCharIfPresent(K k, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
/*  830 */     Objects.requireNonNull(remappingFunction);
/*  831 */     int pos = find(k);
/*  832 */     if (pos < 0)
/*  833 */       return this.defRetValue; 
/*  834 */     Character newValue = remappingFunction.apply(k, Character.valueOf(this.value[pos]));
/*  835 */     if (newValue == null) {
/*  836 */       if (k == null) {
/*  837 */         removeNullEntry();
/*      */       } else {
/*  839 */         removeEntry(pos);
/*  840 */       }  return this.defRetValue;
/*      */     } 
/*  842 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeChar(K k, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
/*  848 */     Objects.requireNonNull(remappingFunction);
/*  849 */     int pos = find(k);
/*  850 */     Character newValue = remappingFunction.apply(k, (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  851 */     if (newValue == null) {
/*  852 */       if (pos >= 0)
/*  853 */         if (k == null) {
/*  854 */           removeNullEntry();
/*      */         } else {
/*  856 */           removeEntry(pos);
/*      */         }  
/*  858 */       return this.defRetValue;
/*      */     } 
/*  860 */     char newVal = newValue.charValue();
/*  861 */     if (pos < 0) {
/*  862 */       insert(-pos - 1, k, newVal);
/*  863 */       return newVal;
/*      */     } 
/*  865 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char mergeChar(K k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  871 */     Objects.requireNonNull(remappingFunction);
/*  872 */     int pos = find(k);
/*  873 */     if (pos < 0) {
/*  874 */       insert(-pos - 1, k, v);
/*  875 */       return v;
/*      */     } 
/*  877 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  878 */     if (newValue == null) {
/*  879 */       if (k == null) {
/*  880 */         removeNullEntry();
/*      */       } else {
/*  882 */         removeEntry(pos);
/*  883 */       }  return this.defRetValue;
/*      */     } 
/*  885 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
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
/*  896 */     if (this.size == 0)
/*      */       return; 
/*  898 */     this.size = 0;
/*  899 */     this.containsNullKey = false;
/*  900 */     Arrays.fill((Object[])this.key, (Object)null);
/*  901 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  905 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  909 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2CharMap.Entry<K>, Map.Entry<K, Character>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  921 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  927 */       return Object2CharLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public char getCharValue() {
/*  931 */       return Object2CharLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public char setValue(char v) {
/*  935 */       char oldValue = Object2CharLinkedOpenHashMap.this.value[this.index];
/*  936 */       Object2CharLinkedOpenHashMap.this.value[this.index] = v;
/*  937 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getValue() {
/*  947 */       return Character.valueOf(Object2CharLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character setValue(Character v) {
/*  957 */       return Character.valueOf(setValue(v.charValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  962 */       if (!(o instanceof Map.Entry))
/*  963 */         return false; 
/*  964 */       Map.Entry<K, Character> e = (Map.Entry<K, Character>)o;
/*  965 */       return (Objects.equals(Object2CharLinkedOpenHashMap.this.key[this.index], e.getKey()) && Object2CharLinkedOpenHashMap.this.value[this.index] == ((Character)e
/*  966 */         .getValue()).charValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  970 */       return ((Object2CharLinkedOpenHashMap.this.key[this.index] == null) ? 0 : Object2CharLinkedOpenHashMap.this.key[this.index].hashCode()) ^ Object2CharLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  974 */       return (new StringBuilder()).append(Object2CharLinkedOpenHashMap.this.key[this.index]).append("=>").append(Object2CharLinkedOpenHashMap.this.value[this.index]).toString();
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
/*  985 */     if (this.size == 0) {
/*  986 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  989 */     if (this.first == i) {
/*  990 */       this.first = (int)this.link[i];
/*  991 */       if (0 <= this.first)
/*      */       {
/*  993 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  997 */     if (this.last == i) {
/*  998 */       this.last = (int)(this.link[i] >>> 32L);
/*  999 */       if (0 <= this.last)
/*      */       {
/* 1001 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1005 */     long linki = this.link[i];
/* 1006 */     int prev = (int)(linki >>> 32L);
/* 1007 */     int next = (int)linki;
/* 1008 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1009 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1022 */     if (this.size == 1) {
/* 1023 */       this.first = this.last = d;
/*      */       
/* 1025 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1028 */     if (this.first == s) {
/* 1029 */       this.first = d;
/* 1030 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1031 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1034 */     if (this.last == s) {
/* 1035 */       this.last = d;
/* 1036 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1037 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1040 */     long links = this.link[s];
/* 1041 */     int prev = (int)(links >>> 32L);
/* 1042 */     int next = (int)links;
/* 1043 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1044 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1045 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1054 */     if (this.size == 0)
/* 1055 */       throw new NoSuchElementException(); 
/* 1056 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1065 */     if (this.size == 0)
/* 1066 */       throw new NoSuchElementException(); 
/* 1067 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2CharSortedMap<K> tailMap(K from) {
/* 1076 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2CharSortedMap<K> headMap(K to) {
/* 1085 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2CharSortedMap<K> subMap(K from, K to) {
/* 1094 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1103 */     return null;
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
/* 1118 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1124 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1129 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1135 */     int index = -1;
/*      */     protected MapIterator() {
/* 1137 */       this.next = Object2CharLinkedOpenHashMap.this.first;
/* 1138 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/* 1141 */       if (from == null) {
/* 1142 */         if (Object2CharLinkedOpenHashMap.this.containsNullKey) {
/* 1143 */           this.next = (int)Object2CharLinkedOpenHashMap.this.link[Object2CharLinkedOpenHashMap.this.n];
/* 1144 */           this.prev = Object2CharLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1147 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1149 */       if (Objects.equals(Object2CharLinkedOpenHashMap.this.key[Object2CharLinkedOpenHashMap.this.last], from)) {
/* 1150 */         this.prev = Object2CharLinkedOpenHashMap.this.last;
/* 1151 */         this.index = Object2CharLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1155 */       int pos = HashCommon.mix(from.hashCode()) & Object2CharLinkedOpenHashMap.this.mask;
/*      */       
/* 1157 */       while (Object2CharLinkedOpenHashMap.this.key[pos] != null) {
/* 1158 */         if (Object2CharLinkedOpenHashMap.this.key[pos].equals(from)) {
/*      */           
/* 1160 */           this.next = (int)Object2CharLinkedOpenHashMap.this.link[pos];
/* 1161 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1164 */         pos = pos + 1 & Object2CharLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1166 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1169 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1172 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1175 */       if (this.index >= 0)
/*      */         return; 
/* 1177 */       if (this.prev == -1) {
/* 1178 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1181 */       if (this.next == -1) {
/* 1182 */         this.index = Object2CharLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1185 */       int pos = Object2CharLinkedOpenHashMap.this.first;
/* 1186 */       this.index = 1;
/* 1187 */       while (pos != this.prev) {
/* 1188 */         pos = (int)Object2CharLinkedOpenHashMap.this.link[pos];
/* 1189 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1193 */       ensureIndexKnown();
/* 1194 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1197 */       ensureIndexKnown();
/* 1198 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1201 */       if (!hasNext())
/* 1202 */         throw new NoSuchElementException(); 
/* 1203 */       this.curr = this.next;
/* 1204 */       this.next = (int)Object2CharLinkedOpenHashMap.this.link[this.curr];
/* 1205 */       this.prev = this.curr;
/* 1206 */       if (this.index >= 0)
/* 1207 */         this.index++; 
/* 1208 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1211 */       if (!hasPrevious())
/* 1212 */         throw new NoSuchElementException(); 
/* 1213 */       this.curr = this.prev;
/* 1214 */       this.prev = (int)(Object2CharLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1215 */       this.next = this.curr;
/* 1216 */       if (this.index >= 0)
/* 1217 */         this.index--; 
/* 1218 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1221 */       ensureIndexKnown();
/* 1222 */       if (this.curr == -1)
/* 1223 */         throw new IllegalStateException(); 
/* 1224 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1229 */         this.index--;
/* 1230 */         this.prev = (int)(Object2CharLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1232 */         this.next = (int)Object2CharLinkedOpenHashMap.this.link[this.curr];
/* 1233 */       }  Object2CharLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1238 */       if (this.prev == -1) {
/* 1239 */         Object2CharLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1241 */         Object2CharLinkedOpenHashMap.this.link[this.prev] = Object2CharLinkedOpenHashMap.this.link[this.prev] ^ (Object2CharLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1242 */       }  if (this.next == -1) {
/* 1243 */         Object2CharLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1245 */         Object2CharLinkedOpenHashMap.this.link[this.next] = Object2CharLinkedOpenHashMap.this.link[this.next] ^ (Object2CharLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1246 */       }  int pos = this.curr;
/* 1247 */       this.curr = -1;
/* 1248 */       if (pos == Object2CharLinkedOpenHashMap.this.n) {
/* 1249 */         Object2CharLinkedOpenHashMap.this.containsNullKey = false;
/* 1250 */         Object2CharLinkedOpenHashMap.this.key[Object2CharLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1253 */         K[] key = Object2CharLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1257 */           pos = (last = pos) + 1 & Object2CharLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1259 */             if ((curr = key[pos]) == null) {
/* 1260 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1263 */             int slot = HashCommon.mix(curr.hashCode()) & Object2CharLinkedOpenHashMap.this.mask;
/* 1264 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1266 */             pos = pos + 1 & Object2CharLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1268 */           key[last] = curr;
/* 1269 */           Object2CharLinkedOpenHashMap.this.value[last] = Object2CharLinkedOpenHashMap.this.value[pos];
/* 1270 */           if (this.next == pos)
/* 1271 */             this.next = last; 
/* 1272 */           if (this.prev == pos)
/* 1273 */             this.prev = last; 
/* 1274 */           Object2CharLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1279 */       int i = n;
/* 1280 */       while (i-- != 0 && hasNext())
/* 1281 */         nextEntry(); 
/* 1282 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1285 */       int i = n;
/* 1286 */       while (i-- != 0 && hasPrevious())
/* 1287 */         previousEntry(); 
/* 1288 */       return n - i - 1;
/*      */     }
/*      */     public void set(Object2CharMap.Entry<K> ok) {
/* 1291 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Object2CharMap.Entry<K> ok) {
/* 1294 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Object2CharMap.Entry<K>> { private Object2CharLinkedOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1302 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2CharLinkedOpenHashMap<K>.MapEntry next() {
/* 1306 */       return this.entry = new Object2CharLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Object2CharLinkedOpenHashMap<K>.MapEntry previous() {
/* 1310 */       return this.entry = new Object2CharLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1314 */       super.remove();
/* 1315 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1319 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Object2CharMap.Entry<K>> { final Object2CharLinkedOpenHashMap<K>.MapEntry entry = new Object2CharLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1323 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2CharLinkedOpenHashMap<K>.MapEntry next() {
/* 1327 */       this.entry.index = nextEntry();
/* 1328 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Object2CharLinkedOpenHashMap<K>.MapEntry previous() {
/* 1332 */       this.entry.index = previousEntry();
/* 1333 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2CharMap.Entry<K>> implements Object2CharSortedMap.FastSortedEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2CharMap.Entry<K>> iterator() {
/* 1341 */       return new Object2CharLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Object2CharMap.Entry<K>> comparator() {
/* 1345 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2CharMap.Entry<K>> subSet(Object2CharMap.Entry<K> fromElement, Object2CharMap.Entry<K> toElement) {
/* 1350 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2CharMap.Entry<K>> headSet(Object2CharMap.Entry<K> toElement) {
/* 1354 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2CharMap.Entry<K>> tailSet(Object2CharMap.Entry<K> fromElement) {
/* 1358 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Object2CharMap.Entry<K> first() {
/* 1362 */       if (Object2CharLinkedOpenHashMap.this.size == 0)
/* 1363 */         throw new NoSuchElementException(); 
/* 1364 */       return new Object2CharLinkedOpenHashMap.MapEntry(Object2CharLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Object2CharMap.Entry<K> last() {
/* 1368 */       if (Object2CharLinkedOpenHashMap.this.size == 0)
/* 1369 */         throw new NoSuchElementException(); 
/* 1370 */       return new Object2CharLinkedOpenHashMap.MapEntry(Object2CharLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1375 */       if (!(o instanceof Map.Entry))
/* 1376 */         return false; 
/* 1377 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1378 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 1379 */         return false; 
/* 1380 */       K k = (K)e.getKey();
/* 1381 */       char v = ((Character)e.getValue()).charValue();
/* 1382 */       if (k == null) {
/* 1383 */         return (Object2CharLinkedOpenHashMap.this.containsNullKey && Object2CharLinkedOpenHashMap.this.value[Object2CharLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1385 */       K[] key = Object2CharLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1388 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2CharLinkedOpenHashMap.this.mask]) == null)
/* 1389 */         return false; 
/* 1390 */       if (k.equals(curr)) {
/* 1391 */         return (Object2CharLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1394 */         if ((curr = key[pos = pos + 1 & Object2CharLinkedOpenHashMap.this.mask]) == null)
/* 1395 */           return false; 
/* 1396 */         if (k.equals(curr)) {
/* 1397 */           return (Object2CharLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1403 */       if (!(o instanceof Map.Entry))
/* 1404 */         return false; 
/* 1405 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1406 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 1407 */         return false; 
/* 1408 */       K k = (K)e.getKey();
/* 1409 */       char v = ((Character)e.getValue()).charValue();
/* 1410 */       if (k == null) {
/* 1411 */         if (Object2CharLinkedOpenHashMap.this.containsNullKey && Object2CharLinkedOpenHashMap.this.value[Object2CharLinkedOpenHashMap.this.n] == v) {
/* 1412 */           Object2CharLinkedOpenHashMap.this.removeNullEntry();
/* 1413 */           return true;
/*      */         } 
/* 1415 */         return false;
/*      */       } 
/*      */       
/* 1418 */       K[] key = Object2CharLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1421 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2CharLinkedOpenHashMap.this.mask]) == null)
/* 1422 */         return false; 
/* 1423 */       if (curr.equals(k)) {
/* 1424 */         if (Object2CharLinkedOpenHashMap.this.value[pos] == v) {
/* 1425 */           Object2CharLinkedOpenHashMap.this.removeEntry(pos);
/* 1426 */           return true;
/*      */         } 
/* 1428 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1431 */         if ((curr = key[pos = pos + 1 & Object2CharLinkedOpenHashMap.this.mask]) == null)
/* 1432 */           return false; 
/* 1433 */         if (curr.equals(k) && 
/* 1434 */           Object2CharLinkedOpenHashMap.this.value[pos] == v) {
/* 1435 */           Object2CharLinkedOpenHashMap.this.removeEntry(pos);
/* 1436 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1443 */       return Object2CharLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1447 */       Object2CharLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2CharMap.Entry<K>> iterator(Object2CharMap.Entry<K> from) {
/* 1462 */       return new Object2CharLinkedOpenHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2CharMap.Entry<K>> fastIterator() {
/* 1473 */       return new Object2CharLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2CharMap.Entry<K>> fastIterator(Object2CharMap.Entry<K> from) {
/* 1488 */       return new Object2CharLinkedOpenHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2CharMap.Entry<K>> consumer) {
/* 1493 */       for (int i = Object2CharLinkedOpenHashMap.this.size, next = Object2CharLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1494 */         int curr = next;
/* 1495 */         next = (int)Object2CharLinkedOpenHashMap.this.link[curr];
/* 1496 */         consumer.accept(new AbstractObject2CharMap.BasicEntry<>(Object2CharLinkedOpenHashMap.this.key[curr], Object2CharLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2CharMap.Entry<K>> consumer) {
/* 1502 */       AbstractObject2CharMap.BasicEntry<K> entry = new AbstractObject2CharMap.BasicEntry<>();
/* 1503 */       for (int i = Object2CharLinkedOpenHashMap.this.size, next = Object2CharLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1504 */         int curr = next;
/* 1505 */         next = (int)Object2CharLinkedOpenHashMap.this.link[curr];
/* 1506 */         entry.key = Object2CharLinkedOpenHashMap.this.key[curr];
/* 1507 */         entry.value = Object2CharLinkedOpenHashMap.this.value[curr];
/* 1508 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Object2CharSortedMap.FastSortedEntrySet<K> object2CharEntrySet() {
/* 1514 */     if (this.entries == null)
/* 1515 */       this.entries = new MapEntrySet(); 
/* 1516 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends MapIterator
/*      */     implements ObjectListIterator<K>
/*      */   {
/*      */     public KeyIterator(K k) {
/* 1529 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1533 */       return Object2CharLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1540 */       return Object2CharLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1546 */       return new Object2CharLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1550 */       return new Object2CharLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1555 */       if (Object2CharLinkedOpenHashMap.this.containsNullKey)
/* 1556 */         consumer.accept(Object2CharLinkedOpenHashMap.this.key[Object2CharLinkedOpenHashMap.this.n]); 
/* 1557 */       for (int pos = Object2CharLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1558 */         K k = Object2CharLinkedOpenHashMap.this.key[pos];
/* 1559 */         if (k != null)
/* 1560 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1565 */       return Object2CharLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1569 */       return Object2CharLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1573 */       int oldSize = Object2CharLinkedOpenHashMap.this.size;
/* 1574 */       Object2CharLinkedOpenHashMap.this.removeChar(k);
/* 1575 */       return (Object2CharLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1579 */       Object2CharLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1583 */       if (Object2CharLinkedOpenHashMap.this.size == 0)
/* 1584 */         throw new NoSuchElementException(); 
/* 1585 */       return Object2CharLinkedOpenHashMap.this.key[Object2CharLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1589 */       if (Object2CharLinkedOpenHashMap.this.size == 0)
/* 1590 */         throw new NoSuchElementException(); 
/* 1591 */       return Object2CharLinkedOpenHashMap.this.key[Object2CharLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1595 */       return null;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1599 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1603 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1607 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1612 */     if (this.keys == null)
/* 1613 */       this.keys = new KeySet(); 
/* 1614 */     return this.keys;
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
/* 1628 */       return Object2CharLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1635 */       return Object2CharLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public CharCollection values() {
/* 1640 */     if (this.values == null)
/* 1641 */       this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/* 1644 */             return (CharIterator)new Object2CharLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1648 */             return Object2CharLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(char v) {
/* 1652 */             return Object2CharLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1656 */             Object2CharLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(IntConsumer consumer) {
/* 1661 */             if (Object2CharLinkedOpenHashMap.this.containsNullKey)
/* 1662 */               consumer.accept(Object2CharLinkedOpenHashMap.this.value[Object2CharLinkedOpenHashMap.this.n]); 
/* 1663 */             for (int pos = Object2CharLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1664 */               if (Object2CharLinkedOpenHashMap.this.key[pos] != null)
/* 1665 */                 consumer.accept(Object2CharLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1668 */     return this.values;
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
/* 1685 */     return trim(this.size);
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
/* 1709 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1710 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1711 */       return true; 
/*      */     try {
/* 1713 */       rehash(l);
/* 1714 */     } catch (OutOfMemoryError cantDoIt) {
/* 1715 */       return false;
/*      */     } 
/* 1717 */     return true;
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
/* 1733 */     K[] key = this.key;
/* 1734 */     char[] value = this.value;
/* 1735 */     int mask = newN - 1;
/* 1736 */     K[] newKey = (K[])new Object[newN + 1];
/* 1737 */     char[] newValue = new char[newN + 1];
/* 1738 */     int i = this.first, prev = -1, newPrev = -1;
/* 1739 */     long[] link = this.link;
/* 1740 */     long[] newLink = new long[newN + 1];
/* 1741 */     this.first = -1;
/* 1742 */     for (int j = this.size; j-- != 0; ) {
/* 1743 */       int pos; if (key[i] == null) {
/* 1744 */         pos = newN;
/*      */       } else {
/* 1746 */         pos = HashCommon.mix(key[i].hashCode()) & mask;
/* 1747 */         while (newKey[pos] != null)
/* 1748 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1750 */       newKey[pos] = key[i];
/* 1751 */       newValue[pos] = value[i];
/* 1752 */       if (prev != -1) {
/* 1753 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1754 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1755 */         newPrev = pos;
/*      */       } else {
/* 1757 */         newPrev = this.first = pos;
/*      */         
/* 1759 */         newLink[pos] = -1L;
/*      */       } 
/* 1761 */       int t = i;
/* 1762 */       i = (int)link[i];
/* 1763 */       prev = t;
/*      */     } 
/* 1765 */     this.link = newLink;
/* 1766 */     this.last = newPrev;
/* 1767 */     if (newPrev != -1)
/*      */     {
/* 1769 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1770 */     this.n = newN;
/* 1771 */     this.mask = mask;
/* 1772 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1773 */     this.key = newKey;
/* 1774 */     this.value = newValue;
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
/*      */   public Object2CharLinkedOpenHashMap<K> clone() {
/*      */     Object2CharLinkedOpenHashMap<K> c;
/*      */     try {
/* 1791 */       c = (Object2CharLinkedOpenHashMap<K>)super.clone();
/* 1792 */     } catch (CloneNotSupportedException cantHappen) {
/* 1793 */       throw new InternalError();
/*      */     } 
/* 1795 */     c.keys = null;
/* 1796 */     c.values = null;
/* 1797 */     c.entries = null;
/* 1798 */     c.containsNullKey = this.containsNullKey;
/* 1799 */     c.key = (K[])this.key.clone();
/* 1800 */     c.value = (char[])this.value.clone();
/* 1801 */     c.link = (long[])this.link.clone();
/* 1802 */     return c;
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
/* 1815 */     int h = 0;
/* 1816 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1817 */       while (this.key[i] == null)
/* 1818 */         i++; 
/* 1819 */       if (this != this.key[i])
/* 1820 */         t = this.key[i].hashCode(); 
/* 1821 */       t ^= this.value[i];
/* 1822 */       h += t;
/* 1823 */       i++;
/*      */     } 
/*      */     
/* 1826 */     if (this.containsNullKey)
/* 1827 */       h += this.value[this.n]; 
/* 1828 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1831 */     K[] key = this.key;
/* 1832 */     char[] value = this.value;
/* 1833 */     MapIterator i = new MapIterator();
/* 1834 */     s.defaultWriteObject();
/* 1835 */     for (int j = this.size; j-- != 0; ) {
/* 1836 */       int e = i.nextEntry();
/* 1837 */       s.writeObject(key[e]);
/* 1838 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1843 */     s.defaultReadObject();
/* 1844 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1845 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1846 */     this.mask = this.n - 1;
/* 1847 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1848 */     char[] value = this.value = new char[this.n + 1];
/* 1849 */     long[] link = this.link = new long[this.n + 1];
/* 1850 */     int prev = -1;
/* 1851 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1854 */     for (int i = this.size; i-- != 0; ) {
/* 1855 */       int pos; K k = (K)s.readObject();
/* 1856 */       char v = s.readChar();
/* 1857 */       if (k == null) {
/* 1858 */         pos = this.n;
/* 1859 */         this.containsNullKey = true;
/*      */       } else {
/* 1861 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 1862 */         while (key[pos] != null)
/* 1863 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1865 */       key[pos] = k;
/* 1866 */       value[pos] = v;
/* 1867 */       if (this.first != -1) {
/* 1868 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1869 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1870 */         prev = pos; continue;
/*      */       } 
/* 1872 */       prev = this.first = pos;
/*      */       
/* 1874 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1877 */     this.last = prev;
/* 1878 */     if (prev != -1)
/*      */     {
/* 1880 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2CharLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */