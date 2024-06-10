/*      */ package it.unimi.dsi.fastutil.chars;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Char2ReferenceLinkedOpenHashMap<V>
/*      */   extends AbstractChar2ReferenceSortedMap<V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient char[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*  106 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  111 */   protected transient int last = -1;
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
/*      */   protected transient Char2ReferenceSortedMap.FastSortedEntrySet<V> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient CharSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ReferenceCollection<V> values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ReferenceLinkedOpenHashMap(int expected, float f) {
/*  152 */     if (f <= 0.0F || f > 1.0F)
/*  153 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  154 */     if (expected < 0)
/*  155 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  156 */     this.f = f;
/*  157 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  158 */     this.mask = this.n - 1;
/*  159 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  160 */     this.key = new char[this.n + 1];
/*  161 */     this.value = (V[])new Object[this.n + 1];
/*  162 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ReferenceLinkedOpenHashMap(int expected) {
/*  171 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ReferenceLinkedOpenHashMap() {
/*  179 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ReferenceLinkedOpenHashMap(Map<? extends Character, ? extends V> m, float f) {
/*  190 */     this(m.size(), f);
/*  191 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ReferenceLinkedOpenHashMap(Map<? extends Character, ? extends V> m) {
/*  201 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ReferenceLinkedOpenHashMap(Char2ReferenceMap<V> m, float f) {
/*  212 */     this(m.size(), f);
/*  213 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ReferenceLinkedOpenHashMap(Char2ReferenceMap<V> m) {
/*  223 */     this(m, 0.75F);
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
/*      */   public Char2ReferenceLinkedOpenHashMap(char[] k, V[] v, float f) {
/*  238 */     this(k.length, f);
/*  239 */     if (k.length != v.length) {
/*  240 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  242 */     for (int i = 0; i < k.length; i++) {
/*  243 */       put(k[i], v[i]);
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
/*      */   public Char2ReferenceLinkedOpenHashMap(char[] k, V[] v) {
/*  257 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  260 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  263 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  264 */     if (needed > this.n)
/*  265 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  268 */     int needed = (int)Math.min(1073741824L, 
/*  269 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  270 */     if (needed > this.n)
/*  271 */       rehash(needed); 
/*      */   }
/*      */   private V removeEntry(int pos) {
/*  274 */     V oldValue = this.value[pos];
/*  275 */     this.value[pos] = null;
/*  276 */     this.size--;
/*  277 */     fixPointers(pos);
/*  278 */     shiftKeys(pos);
/*  279 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  280 */       rehash(this.n / 2); 
/*  281 */     return oldValue;
/*      */   }
/*      */   private V removeNullEntry() {
/*  284 */     this.containsNullKey = false;
/*  285 */     V oldValue = this.value[this.n];
/*  286 */     this.value[this.n] = null;
/*  287 */     this.size--;
/*  288 */     fixPointers(this.n);
/*  289 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  290 */       rehash(this.n / 2); 
/*  291 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Character, ? extends V> m) {
/*  295 */     if (this.f <= 0.5D) {
/*  296 */       ensureCapacity(m.size());
/*      */     } else {
/*  298 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  300 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(char k) {
/*  304 */     if (k == '\000') {
/*  305 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  307 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  310 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  311 */       return -(pos + 1); 
/*  312 */     if (k == curr) {
/*  313 */       return pos;
/*      */     }
/*      */     while (true) {
/*  316 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  317 */         return -(pos + 1); 
/*  318 */       if (k == curr)
/*  319 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, char k, V v) {
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
/*      */   public V put(char k, V v) {
/*  343 */     int pos = find(k);
/*  344 */     if (pos < 0) {
/*  345 */       insert(-pos - 1, k, v);
/*  346 */       return this.defRetValue;
/*      */     } 
/*  348 */     V oldValue = this.value[pos];
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
/*  363 */     char[] key = this.key; while (true) {
/*      */       char curr; int last;
/*  365 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  367 */         if ((curr = key[pos]) == '\000') {
/*  368 */           key[last] = Character.MIN_VALUE;
/*  369 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  372 */         int slot = HashCommon.mix(curr) & this.mask;
/*  373 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  375 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  377 */       key[last] = curr;
/*  378 */       this.value[last] = this.value[pos];
/*  379 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V remove(char k) {
/*  385 */     if (k == '\000') {
/*  386 */       if (this.containsNullKey)
/*  387 */         return removeNullEntry(); 
/*  388 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  391 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  394 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  395 */       return this.defRetValue; 
/*  396 */     if (k == curr)
/*  397 */       return removeEntry(pos); 
/*      */     while (true) {
/*  399 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  400 */         return this.defRetValue; 
/*  401 */       if (k == curr)
/*  402 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private V setValue(int pos, V v) {
/*  406 */     V oldValue = this.value[pos];
/*  407 */     this.value[pos] = v;
/*  408 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V removeFirst() {
/*  419 */     if (this.size == 0)
/*  420 */       throw new NoSuchElementException(); 
/*  421 */     int pos = this.first;
/*      */     
/*  423 */     this.first = (int)this.link[pos];
/*  424 */     if (0 <= this.first)
/*      */     {
/*  426 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  428 */     this.size--;
/*  429 */     V v = this.value[pos];
/*  430 */     if (pos == this.n) {
/*  431 */       this.containsNullKey = false;
/*  432 */       this.value[this.n] = null;
/*      */     } else {
/*  434 */       shiftKeys(pos);
/*  435 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  436 */       rehash(this.n / 2); 
/*  437 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V removeLast() {
/*  447 */     if (this.size == 0)
/*  448 */       throw new NoSuchElementException(); 
/*  449 */     int pos = this.last;
/*      */     
/*  451 */     this.last = (int)(this.link[pos] >>> 32L);
/*  452 */     if (0 <= this.last)
/*      */     {
/*  454 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  456 */     this.size--;
/*  457 */     V v = this.value[pos];
/*  458 */     if (pos == this.n) {
/*  459 */       this.containsNullKey = false;
/*  460 */       this.value[this.n] = null;
/*      */     } else {
/*  462 */       shiftKeys(pos);
/*  463 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  464 */       rehash(this.n / 2); 
/*  465 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  468 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  470 */     if (this.last == i) {
/*  471 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  473 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  475 */       long linki = this.link[i];
/*  476 */       int prev = (int)(linki >>> 32L);
/*  477 */       int next = (int)linki;
/*  478 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  479 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  481 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  482 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  483 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  486 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  488 */     if (this.first == i) {
/*  489 */       this.first = (int)this.link[i];
/*      */       
/*  491 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  493 */       long linki = this.link[i];
/*  494 */       int prev = (int)(linki >>> 32L);
/*  495 */       int next = (int)linki;
/*  496 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  497 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  499 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  500 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  501 */     this.last = i;
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
/*      */   public V getAndMoveToFirst(char k) {
/*  513 */     if (k == '\000') {
/*  514 */       if (this.containsNullKey) {
/*  515 */         moveIndexToFirst(this.n);
/*  516 */         return this.value[this.n];
/*      */       } 
/*  518 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  521 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  524 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  525 */       return this.defRetValue; 
/*  526 */     if (k == curr) {
/*  527 */       moveIndexToFirst(pos);
/*  528 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  532 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  533 */         return this.defRetValue; 
/*  534 */       if (k == curr) {
/*  535 */         moveIndexToFirst(pos);
/*  536 */         return this.value[pos];
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
/*      */   public V getAndMoveToLast(char k) {
/*  550 */     if (k == '\000') {
/*  551 */       if (this.containsNullKey) {
/*  552 */         moveIndexToLast(this.n);
/*  553 */         return this.value[this.n];
/*      */       } 
/*  555 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  558 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  561 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  562 */       return this.defRetValue; 
/*  563 */     if (k == curr) {
/*  564 */       moveIndexToLast(pos);
/*  565 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  569 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  570 */         return this.defRetValue; 
/*  571 */       if (k == curr) {
/*  572 */         moveIndexToLast(pos);
/*  573 */         return this.value[pos];
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
/*      */   public V putAndMoveToFirst(char k, V v) {
/*      */     int pos;
/*  590 */     if (k == '\000') {
/*  591 */       if (this.containsNullKey) {
/*  592 */         moveIndexToFirst(this.n);
/*  593 */         return setValue(this.n, v);
/*      */       } 
/*  595 */       this.containsNullKey = true;
/*  596 */       pos = this.n;
/*      */     } else {
/*      */       
/*  599 */       char[] key = this.key;
/*      */       char curr;
/*  601 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != '\000') {
/*  602 */         if (curr == k) {
/*  603 */           moveIndexToFirst(pos);
/*  604 */           return setValue(pos, v);
/*      */         } 
/*  606 */         while ((curr = key[pos = pos + 1 & this.mask]) != '\000') {
/*  607 */           if (curr == k) {
/*  608 */             moveIndexToFirst(pos);
/*  609 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  613 */     }  this.key[pos] = k;
/*  614 */     this.value[pos] = v;
/*  615 */     if (this.size == 0) {
/*  616 */       this.first = this.last = pos;
/*      */       
/*  618 */       this.link[pos] = -1L;
/*      */     } else {
/*  620 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  621 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  622 */       this.first = pos;
/*      */     } 
/*  624 */     if (this.size++ >= this.maxFill) {
/*  625 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  628 */     return this.defRetValue;
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
/*      */   public V putAndMoveToLast(char k, V v) {
/*      */     int pos;
/*  643 */     if (k == '\000') {
/*  644 */       if (this.containsNullKey) {
/*  645 */         moveIndexToLast(this.n);
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
/*  656 */           moveIndexToLast(pos);
/*  657 */           return setValue(pos, v);
/*      */         } 
/*  659 */         while ((curr = key[pos = pos + 1 & this.mask]) != '\000') {
/*  660 */           if (curr == k) {
/*  661 */             moveIndexToLast(pos);
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
/*  673 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  674 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  675 */       this.last = pos;
/*      */     } 
/*  677 */     if (this.size++ >= this.maxFill) {
/*  678 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  681 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(char k) {
/*  686 */     if (k == '\000') {
/*  687 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  689 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  692 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  693 */       return this.defRetValue; 
/*  694 */     if (k == curr) {
/*  695 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  698 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  699 */         return this.defRetValue; 
/*  700 */       if (k == curr) {
/*  701 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(char k) {
/*  707 */     if (k == '\000') {
/*  708 */       return this.containsNullKey;
/*      */     }
/*  710 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  713 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  714 */       return false; 
/*  715 */     if (k == curr) {
/*  716 */       return true;
/*      */     }
/*      */     while (true) {
/*  719 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  720 */         return false; 
/*  721 */       if (k == curr)
/*  722 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  727 */     V[] value = this.value;
/*  728 */     char[] key = this.key;
/*  729 */     if (this.containsNullKey && value[this.n] == v)
/*  730 */       return true; 
/*  731 */     for (int i = this.n; i-- != 0;) {
/*  732 */       if (key[i] != '\000' && value[i] == v)
/*  733 */         return true; 
/*  734 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(char k, V defaultValue) {
/*  740 */     if (k == '\000') {
/*  741 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  743 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  746 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  747 */       return defaultValue; 
/*  748 */     if (k == curr) {
/*  749 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  752 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  753 */         return defaultValue; 
/*  754 */       if (k == curr) {
/*  755 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V putIfAbsent(char k, V v) {
/*  761 */     int pos = find(k);
/*  762 */     if (pos >= 0)
/*  763 */       return this.value[pos]; 
/*  764 */     insert(-pos - 1, k, v);
/*  765 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(char k, Object v) {
/*  771 */     if (k == '\000') {
/*  772 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  773 */         removeNullEntry();
/*  774 */         return true;
/*      */       } 
/*  776 */       return false;
/*      */     } 
/*      */     
/*  779 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  782 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == '\000')
/*  783 */       return false; 
/*  784 */     if (k == curr && v == this.value[pos]) {
/*  785 */       removeEntry(pos);
/*  786 */       return true;
/*      */     } 
/*      */     while (true) {
/*  789 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  790 */         return false; 
/*  791 */       if (k == curr && v == this.value[pos]) {
/*  792 */         removeEntry(pos);
/*  793 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(char k, V oldValue, V v) {
/*  800 */     int pos = find(k);
/*  801 */     if (pos < 0 || oldValue != this.value[pos])
/*  802 */       return false; 
/*  803 */     this.value[pos] = v;
/*  804 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(char k, V v) {
/*  809 */     int pos = find(k);
/*  810 */     if (pos < 0)
/*  811 */       return this.defRetValue; 
/*  812 */     V oldValue = this.value[pos];
/*  813 */     this.value[pos] = v;
/*  814 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(char k, IntFunction<? extends V> mappingFunction) {
/*  819 */     Objects.requireNonNull(mappingFunction);
/*  820 */     int pos = find(k);
/*  821 */     if (pos >= 0)
/*  822 */       return this.value[pos]; 
/*  823 */     V newValue = mappingFunction.apply(k);
/*  824 */     insert(-pos - 1, k, newValue);
/*  825 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfPresent(char k, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/*  831 */     Objects.requireNonNull(remappingFunction);
/*  832 */     int pos = find(k);
/*  833 */     if (pos < 0)
/*  834 */       return this.defRetValue; 
/*  835 */     V newValue = remappingFunction.apply(Character.valueOf(k), this.value[pos]);
/*  836 */     if (newValue == null) {
/*  837 */       if (k == '\000') {
/*  838 */         removeNullEntry();
/*      */       } else {
/*  840 */         removeEntry(pos);
/*  841 */       }  return this.defRetValue;
/*      */     } 
/*  843 */     this.value[pos] = newValue; return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V compute(char k, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/*  849 */     Objects.requireNonNull(remappingFunction);
/*  850 */     int pos = find(k);
/*  851 */     V newValue = remappingFunction.apply(Character.valueOf(k), (pos >= 0) ? this.value[pos] : null);
/*  852 */     if (newValue == null) {
/*  853 */       if (pos >= 0)
/*  854 */         if (k == '\000') {
/*  855 */           removeNullEntry();
/*      */         } else {
/*  857 */           removeEntry(pos);
/*      */         }  
/*  859 */       return this.defRetValue;
/*      */     } 
/*  861 */     V newVal = newValue;
/*  862 */     if (pos < 0) {
/*  863 */       insert(-pos - 1, k, newVal);
/*  864 */       return newVal;
/*      */     } 
/*  866 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V merge(char k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*  872 */     Objects.requireNonNull(remappingFunction);
/*  873 */     int pos = find(k);
/*  874 */     if (pos < 0 || this.value[pos] == null) {
/*  875 */       if (v == null)
/*  876 */         return this.defRetValue; 
/*  877 */       insert(-pos - 1, k, v);
/*  878 */       return v;
/*      */     } 
/*  880 */     V newValue = remappingFunction.apply(this.value[pos], v);
/*  881 */     if (newValue == null) {
/*  882 */       if (k == '\000') {
/*  883 */         removeNullEntry();
/*      */       } else {
/*  885 */         removeEntry(pos);
/*  886 */       }  return this.defRetValue;
/*      */     } 
/*  888 */     this.value[pos] = newValue; return newValue;
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
/*  899 */     if (this.size == 0)
/*      */       return; 
/*  901 */     this.size = 0;
/*  902 */     this.containsNullKey = false;
/*  903 */     Arrays.fill(this.key, false);
/*  904 */     Arrays.fill((Object[])this.value, (Object)null);
/*  905 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  909 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  913 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Char2ReferenceMap.Entry<V>, Map.Entry<Character, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  925 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public char getCharKey() {
/*  931 */       return Char2ReferenceLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  935 */       return Char2ReferenceLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  939 */       V oldValue = Char2ReferenceLinkedOpenHashMap.this.value[this.index];
/*  940 */       Char2ReferenceLinkedOpenHashMap.this.value[this.index] = v;
/*  941 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getKey() {
/*  951 */       return Character.valueOf(Char2ReferenceLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  956 */       if (!(o instanceof Map.Entry))
/*  957 */         return false; 
/*  958 */       Map.Entry<Character, V> e = (Map.Entry<Character, V>)o;
/*  959 */       return (Char2ReferenceLinkedOpenHashMap.this.key[this.index] == ((Character)e.getKey()).charValue() && Char2ReferenceLinkedOpenHashMap.this.value[this.index] == e.getValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  963 */       return Char2ReferenceLinkedOpenHashMap.this.key[this.index] ^ ((Char2ReferenceLinkedOpenHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Char2ReferenceLinkedOpenHashMap.this.value[this.index]));
/*      */     }
/*      */     
/*      */     public String toString() {
/*  967 */       return Char2ReferenceLinkedOpenHashMap.this.key[this.index] + "=>" + Char2ReferenceLinkedOpenHashMap.this.value[this.index];
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
/*  978 */     if (this.size == 0) {
/*  979 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  982 */     if (this.first == i) {
/*  983 */       this.first = (int)this.link[i];
/*  984 */       if (0 <= this.first)
/*      */       {
/*  986 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  990 */     if (this.last == i) {
/*  991 */       this.last = (int)(this.link[i] >>> 32L);
/*  992 */       if (0 <= this.last)
/*      */       {
/*  994 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  998 */     long linki = this.link[i];
/*  999 */     int prev = (int)(linki >>> 32L);
/* 1000 */     int next = (int)linki;
/* 1001 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1002 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1015 */     if (this.size == 1) {
/* 1016 */       this.first = this.last = d;
/*      */       
/* 1018 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1021 */     if (this.first == s) {
/* 1022 */       this.first = d;
/* 1023 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1024 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1027 */     if (this.last == s) {
/* 1028 */       this.last = d;
/* 1029 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1030 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1033 */     long links = this.link[s];
/* 1034 */     int prev = (int)(links >>> 32L);
/* 1035 */     int next = (int)links;
/* 1036 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1037 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1038 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char firstCharKey() {
/* 1047 */     if (this.size == 0)
/* 1048 */       throw new NoSuchElementException(); 
/* 1049 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char lastCharKey() {
/* 1058 */     if (this.size == 0)
/* 1059 */       throw new NoSuchElementException(); 
/* 1060 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ReferenceSortedMap<V> tailMap(char from) {
/* 1069 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ReferenceSortedMap<V> headMap(char to) {
/* 1078 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2ReferenceSortedMap<V> subMap(char from, char to) {
/* 1087 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharComparator comparator() {
/* 1096 */     return null;
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
/* 1111 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1117 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1122 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1128 */     int index = -1;
/*      */     protected MapIterator() {
/* 1130 */       this.next = Char2ReferenceLinkedOpenHashMap.this.first;
/* 1131 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(char from) {
/* 1134 */       if (from == '\000') {
/* 1135 */         if (Char2ReferenceLinkedOpenHashMap.this.containsNullKey) {
/* 1136 */           this.next = (int)Char2ReferenceLinkedOpenHashMap.this.link[Char2ReferenceLinkedOpenHashMap.this.n];
/* 1137 */           this.prev = Char2ReferenceLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1140 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1142 */       if (Char2ReferenceLinkedOpenHashMap.this.key[Char2ReferenceLinkedOpenHashMap.this.last] == from) {
/* 1143 */         this.prev = Char2ReferenceLinkedOpenHashMap.this.last;
/* 1144 */         this.index = Char2ReferenceLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1148 */       int pos = HashCommon.mix(from) & Char2ReferenceLinkedOpenHashMap.this.mask;
/*      */       
/* 1150 */       while (Char2ReferenceLinkedOpenHashMap.this.key[pos] != '\000') {
/* 1151 */         if (Char2ReferenceLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1153 */           this.next = (int)Char2ReferenceLinkedOpenHashMap.this.link[pos];
/* 1154 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1157 */         pos = pos + 1 & Char2ReferenceLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1159 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1162 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1165 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1168 */       if (this.index >= 0)
/*      */         return; 
/* 1170 */       if (this.prev == -1) {
/* 1171 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1174 */       if (this.next == -1) {
/* 1175 */         this.index = Char2ReferenceLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1178 */       int pos = Char2ReferenceLinkedOpenHashMap.this.first;
/* 1179 */       this.index = 1;
/* 1180 */       while (pos != this.prev) {
/* 1181 */         pos = (int)Char2ReferenceLinkedOpenHashMap.this.link[pos];
/* 1182 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1186 */       ensureIndexKnown();
/* 1187 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1190 */       ensureIndexKnown();
/* 1191 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1194 */       if (!hasNext())
/* 1195 */         throw new NoSuchElementException(); 
/* 1196 */       this.curr = this.next;
/* 1197 */       this.next = (int)Char2ReferenceLinkedOpenHashMap.this.link[this.curr];
/* 1198 */       this.prev = this.curr;
/* 1199 */       if (this.index >= 0)
/* 1200 */         this.index++; 
/* 1201 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1204 */       if (!hasPrevious())
/* 1205 */         throw new NoSuchElementException(); 
/* 1206 */       this.curr = this.prev;
/* 1207 */       this.prev = (int)(Char2ReferenceLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1208 */       this.next = this.curr;
/* 1209 */       if (this.index >= 0)
/* 1210 */         this.index--; 
/* 1211 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1214 */       ensureIndexKnown();
/* 1215 */       if (this.curr == -1)
/* 1216 */         throw new IllegalStateException(); 
/* 1217 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1222 */         this.index--;
/* 1223 */         this.prev = (int)(Char2ReferenceLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1225 */         this.next = (int)Char2ReferenceLinkedOpenHashMap.this.link[this.curr];
/* 1226 */       }  Char2ReferenceLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1231 */       if (this.prev == -1) {
/* 1232 */         Char2ReferenceLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1234 */         Char2ReferenceLinkedOpenHashMap.this.link[this.prev] = Char2ReferenceLinkedOpenHashMap.this.link[this.prev] ^ (Char2ReferenceLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1235 */       }  if (this.next == -1) {
/* 1236 */         Char2ReferenceLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1238 */         Char2ReferenceLinkedOpenHashMap.this.link[this.next] = Char2ReferenceLinkedOpenHashMap.this.link[this.next] ^ (Char2ReferenceLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1239 */       }  int pos = this.curr;
/* 1240 */       this.curr = -1;
/* 1241 */       if (pos == Char2ReferenceLinkedOpenHashMap.this.n) {
/* 1242 */         Char2ReferenceLinkedOpenHashMap.this.containsNullKey = false;
/* 1243 */         Char2ReferenceLinkedOpenHashMap.this.value[Char2ReferenceLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1246 */         char[] key = Char2ReferenceLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           char curr;
/*      */           int last;
/* 1250 */           pos = (last = pos) + 1 & Char2ReferenceLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1252 */             if ((curr = key[pos]) == '\000') {
/* 1253 */               key[last] = Character.MIN_VALUE;
/* 1254 */               Char2ReferenceLinkedOpenHashMap.this.value[last] = null;
/*      */               return;
/*      */             } 
/* 1257 */             int slot = HashCommon.mix(curr) & Char2ReferenceLinkedOpenHashMap.this.mask;
/* 1258 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1260 */             pos = pos + 1 & Char2ReferenceLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1262 */           key[last] = curr;
/* 1263 */           Char2ReferenceLinkedOpenHashMap.this.value[last] = Char2ReferenceLinkedOpenHashMap.this.value[pos];
/* 1264 */           if (this.next == pos)
/* 1265 */             this.next = last; 
/* 1266 */           if (this.prev == pos)
/* 1267 */             this.prev = last; 
/* 1268 */           Char2ReferenceLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1273 */       int i = n;
/* 1274 */       while (i-- != 0 && hasNext())
/* 1275 */         nextEntry(); 
/* 1276 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1279 */       int i = n;
/* 1280 */       while (i-- != 0 && hasPrevious())
/* 1281 */         previousEntry(); 
/* 1282 */       return n - i - 1;
/*      */     }
/*      */     public void set(Char2ReferenceMap.Entry<V> ok) {
/* 1285 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Char2ReferenceMap.Entry<V> ok) {
/* 1288 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Char2ReferenceMap.Entry<V>> { private Char2ReferenceLinkedOpenHashMap<V>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(char from) {
/* 1296 */       super(from);
/*      */     }
/*      */     
/*      */     public Char2ReferenceLinkedOpenHashMap<V>.MapEntry next() {
/* 1300 */       return this.entry = new Char2ReferenceLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Char2ReferenceLinkedOpenHashMap<V>.MapEntry previous() {
/* 1304 */       return this.entry = new Char2ReferenceLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1308 */       super.remove();
/* 1309 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1313 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Char2ReferenceMap.Entry<V>> { final Char2ReferenceLinkedOpenHashMap<V>.MapEntry entry = new Char2ReferenceLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(char from) {
/* 1317 */       super(from);
/*      */     }
/*      */     
/*      */     public Char2ReferenceLinkedOpenHashMap<V>.MapEntry next() {
/* 1321 */       this.entry.index = nextEntry();
/* 1322 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Char2ReferenceLinkedOpenHashMap<V>.MapEntry previous() {
/* 1326 */       this.entry.index = previousEntry();
/* 1327 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Char2ReferenceMap.Entry<V>> implements Char2ReferenceSortedMap.FastSortedEntrySet<V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Char2ReferenceMap.Entry<V>> iterator() {
/* 1335 */       return (ObjectBidirectionalIterator<Char2ReferenceMap.Entry<V>>)new Char2ReferenceLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Char2ReferenceMap.Entry<V>> comparator() {
/* 1339 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Char2ReferenceMap.Entry<V>> subSet(Char2ReferenceMap.Entry<V> fromElement, Char2ReferenceMap.Entry<V> toElement) {
/* 1344 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Char2ReferenceMap.Entry<V>> headSet(Char2ReferenceMap.Entry<V> toElement) {
/* 1348 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Char2ReferenceMap.Entry<V>> tailSet(Char2ReferenceMap.Entry<V> fromElement) {
/* 1352 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Char2ReferenceMap.Entry<V> first() {
/* 1356 */       if (Char2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1357 */         throw new NoSuchElementException(); 
/* 1358 */       return new Char2ReferenceLinkedOpenHashMap.MapEntry(Char2ReferenceLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Char2ReferenceMap.Entry<V> last() {
/* 1362 */       if (Char2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1363 */         throw new NoSuchElementException(); 
/* 1364 */       return new Char2ReferenceLinkedOpenHashMap.MapEntry(Char2ReferenceLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1369 */       if (!(o instanceof Map.Entry))
/* 1370 */         return false; 
/* 1371 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1372 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/* 1373 */         return false; 
/* 1374 */       char k = ((Character)e.getKey()).charValue();
/* 1375 */       V v = (V)e.getValue();
/* 1376 */       if (k == '\000') {
/* 1377 */         return (Char2ReferenceLinkedOpenHashMap.this.containsNullKey && Char2ReferenceLinkedOpenHashMap.this.value[Char2ReferenceLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1379 */       char[] key = Char2ReferenceLinkedOpenHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/* 1382 */       if ((curr = key[pos = HashCommon.mix(k) & Char2ReferenceLinkedOpenHashMap.this.mask]) == '\000')
/* 1383 */         return false; 
/* 1384 */       if (k == curr) {
/* 1385 */         return (Char2ReferenceLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1388 */         if ((curr = key[pos = pos + 1 & Char2ReferenceLinkedOpenHashMap.this.mask]) == '\000')
/* 1389 */           return false; 
/* 1390 */         if (k == curr) {
/* 1391 */           return (Char2ReferenceLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1397 */       if (!(o instanceof Map.Entry))
/* 1398 */         return false; 
/* 1399 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1400 */       if (e.getKey() == null || !(e.getKey() instanceof Character))
/* 1401 */         return false; 
/* 1402 */       char k = ((Character)e.getKey()).charValue();
/* 1403 */       V v = (V)e.getValue();
/* 1404 */       if (k == '\000') {
/* 1405 */         if (Char2ReferenceLinkedOpenHashMap.this.containsNullKey && Char2ReferenceLinkedOpenHashMap.this.value[Char2ReferenceLinkedOpenHashMap.this.n] == v) {
/* 1406 */           Char2ReferenceLinkedOpenHashMap.this.removeNullEntry();
/* 1407 */           return true;
/*      */         } 
/* 1409 */         return false;
/*      */       } 
/*      */       
/* 1412 */       char[] key = Char2ReferenceLinkedOpenHashMap.this.key;
/*      */       char curr;
/*      */       int pos;
/* 1415 */       if ((curr = key[pos = HashCommon.mix(k) & Char2ReferenceLinkedOpenHashMap.this.mask]) == '\000')
/* 1416 */         return false; 
/* 1417 */       if (curr == k) {
/* 1418 */         if (Char2ReferenceLinkedOpenHashMap.this.value[pos] == v) {
/* 1419 */           Char2ReferenceLinkedOpenHashMap.this.removeEntry(pos);
/* 1420 */           return true;
/*      */         } 
/* 1422 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1425 */         if ((curr = key[pos = pos + 1 & Char2ReferenceLinkedOpenHashMap.this.mask]) == '\000')
/* 1426 */           return false; 
/* 1427 */         if (curr == k && 
/* 1428 */           Char2ReferenceLinkedOpenHashMap.this.value[pos] == v) {
/* 1429 */           Char2ReferenceLinkedOpenHashMap.this.removeEntry(pos);
/* 1430 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1437 */       return Char2ReferenceLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1441 */       Char2ReferenceLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Char2ReferenceMap.Entry<V>> iterator(Char2ReferenceMap.Entry<V> from) {
/* 1456 */       return new Char2ReferenceLinkedOpenHashMap.EntryIterator(from.getCharKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Char2ReferenceMap.Entry<V>> fastIterator() {
/* 1467 */       return new Char2ReferenceLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Char2ReferenceMap.Entry<V>> fastIterator(Char2ReferenceMap.Entry<V> from) {
/* 1482 */       return new Char2ReferenceLinkedOpenHashMap.FastEntryIterator(from.getCharKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Char2ReferenceMap.Entry<V>> consumer) {
/* 1487 */       for (int i = Char2ReferenceLinkedOpenHashMap.this.size, next = Char2ReferenceLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1488 */         int curr = next;
/* 1489 */         next = (int)Char2ReferenceLinkedOpenHashMap.this.link[curr];
/* 1490 */         consumer.accept(new AbstractChar2ReferenceMap.BasicEntry<>(Char2ReferenceLinkedOpenHashMap.this.key[curr], Char2ReferenceLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Char2ReferenceMap.Entry<V>> consumer) {
/* 1496 */       AbstractChar2ReferenceMap.BasicEntry<V> entry = new AbstractChar2ReferenceMap.BasicEntry<>();
/* 1497 */       for (int i = Char2ReferenceLinkedOpenHashMap.this.size, next = Char2ReferenceLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1498 */         int curr = next;
/* 1499 */         next = (int)Char2ReferenceLinkedOpenHashMap.this.link[curr];
/* 1500 */         entry.key = Char2ReferenceLinkedOpenHashMap.this.key[curr];
/* 1501 */         entry.value = Char2ReferenceLinkedOpenHashMap.this.value[curr];
/* 1502 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Char2ReferenceSortedMap.FastSortedEntrySet<V> char2ReferenceEntrySet() {
/* 1508 */     if (this.entries == null)
/* 1509 */       this.entries = new MapEntrySet(); 
/* 1510 */     return this.entries;
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
/* 1523 */       super(k);
/*      */     }
/*      */     
/*      */     public char previousChar() {
/* 1527 */       return Char2ReferenceLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public char nextChar() {
/* 1534 */       return Char2ReferenceLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractCharSortedSet { private KeySet() {}
/*      */     
/*      */     public CharListIterator iterator(char from) {
/* 1540 */       return new Char2ReferenceLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public CharListIterator iterator() {
/* 1544 */       return new Char2ReferenceLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1549 */       if (Char2ReferenceLinkedOpenHashMap.this.containsNullKey)
/* 1550 */         consumer.accept(Char2ReferenceLinkedOpenHashMap.this.key[Char2ReferenceLinkedOpenHashMap.this.n]); 
/* 1551 */       for (int pos = Char2ReferenceLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1552 */         char k = Char2ReferenceLinkedOpenHashMap.this.key[pos];
/* 1553 */         if (k != '\000')
/* 1554 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1559 */       return Char2ReferenceLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(char k) {
/* 1563 */       return Char2ReferenceLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(char k) {
/* 1567 */       int oldSize = Char2ReferenceLinkedOpenHashMap.this.size;
/* 1568 */       Char2ReferenceLinkedOpenHashMap.this.remove(k);
/* 1569 */       return (Char2ReferenceLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1573 */       Char2ReferenceLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public char firstChar() {
/* 1577 */       if (Char2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1578 */         throw new NoSuchElementException(); 
/* 1579 */       return Char2ReferenceLinkedOpenHashMap.this.key[Char2ReferenceLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public char lastChar() {
/* 1583 */       if (Char2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1584 */         throw new NoSuchElementException(); 
/* 1585 */       return Char2ReferenceLinkedOpenHashMap.this.key[Char2ReferenceLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public CharComparator comparator() {
/* 1589 */       return null;
/*      */     }
/*      */     
/*      */     public CharSortedSet tailSet(char from) {
/* 1593 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public CharSortedSet headSet(char to) {
/* 1597 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public CharSortedSet subSet(char from, char to) {
/* 1601 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public CharSortedSet keySet() {
/* 1606 */     if (this.keys == null)
/* 1607 */       this.keys = new KeySet(); 
/* 1608 */     return this.keys;
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
/*      */     implements ObjectListIterator<V>
/*      */   {
/*      */     public V previous() {
/* 1622 */       return Char2ReferenceLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V next() {
/* 1629 */       return Char2ReferenceLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ReferenceCollection<V> values() {
/* 1634 */     if (this.values == null)
/* 1635 */       this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1638 */             return (ObjectIterator<V>)new Char2ReferenceLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1642 */             return Char2ReferenceLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/* 1646 */             return Char2ReferenceLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1650 */             Char2ReferenceLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer) {
/* 1655 */             if (Char2ReferenceLinkedOpenHashMap.this.containsNullKey)
/* 1656 */               consumer.accept(Char2ReferenceLinkedOpenHashMap.this.value[Char2ReferenceLinkedOpenHashMap.this.n]); 
/* 1657 */             for (int pos = Char2ReferenceLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1658 */               if (Char2ReferenceLinkedOpenHashMap.this.key[pos] != '\000')
/* 1659 */                 consumer.accept(Char2ReferenceLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1662 */     return this.values;
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
/* 1679 */     return trim(this.size);
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
/* 1703 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1704 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1705 */       return true; 
/*      */     try {
/* 1707 */       rehash(l);
/* 1708 */     } catch (OutOfMemoryError cantDoIt) {
/* 1709 */       return false;
/*      */     } 
/* 1711 */     return true;
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
/* 1727 */     char[] key = this.key;
/* 1728 */     V[] value = this.value;
/* 1729 */     int mask = newN - 1;
/* 1730 */     char[] newKey = new char[newN + 1];
/* 1731 */     V[] newValue = (V[])new Object[newN + 1];
/* 1732 */     int i = this.first, prev = -1, newPrev = -1;
/* 1733 */     long[] link = this.link;
/* 1734 */     long[] newLink = new long[newN + 1];
/* 1735 */     this.first = -1;
/* 1736 */     for (int j = this.size; j-- != 0; ) {
/* 1737 */       int pos; if (key[i] == '\000') {
/* 1738 */         pos = newN;
/*      */       } else {
/* 1740 */         pos = HashCommon.mix(key[i]) & mask;
/* 1741 */         while (newKey[pos] != '\000')
/* 1742 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1744 */       newKey[pos] = key[i];
/* 1745 */       newValue[pos] = value[i];
/* 1746 */       if (prev != -1) {
/* 1747 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1748 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1749 */         newPrev = pos;
/*      */       } else {
/* 1751 */         newPrev = this.first = pos;
/*      */         
/* 1753 */         newLink[pos] = -1L;
/*      */       } 
/* 1755 */       int t = i;
/* 1756 */       i = (int)link[i];
/* 1757 */       prev = t;
/*      */     } 
/* 1759 */     this.link = newLink;
/* 1760 */     this.last = newPrev;
/* 1761 */     if (newPrev != -1)
/*      */     {
/* 1763 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1764 */     this.n = newN;
/* 1765 */     this.mask = mask;
/* 1766 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1767 */     this.key = newKey;
/* 1768 */     this.value = newValue;
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
/*      */   public Char2ReferenceLinkedOpenHashMap<V> clone() {
/*      */     Char2ReferenceLinkedOpenHashMap<V> c;
/*      */     try {
/* 1785 */       c = (Char2ReferenceLinkedOpenHashMap<V>)super.clone();
/* 1786 */     } catch (CloneNotSupportedException cantHappen) {
/* 1787 */       throw new InternalError();
/*      */     } 
/* 1789 */     c.keys = null;
/* 1790 */     c.values = null;
/* 1791 */     c.entries = null;
/* 1792 */     c.containsNullKey = this.containsNullKey;
/* 1793 */     c.key = (char[])this.key.clone();
/* 1794 */     c.value = (V[])this.value.clone();
/* 1795 */     c.link = (long[])this.link.clone();
/* 1796 */     return c;
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
/* 1809 */     int h = 0;
/* 1810 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1811 */       while (this.key[i] == '\000')
/* 1812 */         i++; 
/* 1813 */       t = this.key[i];
/* 1814 */       if (this != this.value[i])
/* 1815 */         t ^= (this.value[i] == null) ? 0 : System.identityHashCode(this.value[i]); 
/* 1816 */       h += t;
/* 1817 */       i++;
/*      */     } 
/*      */     
/* 1820 */     if (this.containsNullKey)
/* 1821 */       h += (this.value[this.n] == null) ? 0 : System.identityHashCode(this.value[this.n]); 
/* 1822 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1825 */     char[] key = this.key;
/* 1826 */     V[] value = this.value;
/* 1827 */     MapIterator i = new MapIterator();
/* 1828 */     s.defaultWriteObject();
/* 1829 */     for (int j = this.size; j-- != 0; ) {
/* 1830 */       int e = i.nextEntry();
/* 1831 */       s.writeChar(key[e]);
/* 1832 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1837 */     s.defaultReadObject();
/* 1838 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1839 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1840 */     this.mask = this.n - 1;
/* 1841 */     char[] key = this.key = new char[this.n + 1];
/* 1842 */     V[] value = this.value = (V[])new Object[this.n + 1];
/* 1843 */     long[] link = this.link = new long[this.n + 1];
/* 1844 */     int prev = -1;
/* 1845 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1848 */     for (int i = this.size; i-- != 0; ) {
/* 1849 */       int pos; char k = s.readChar();
/* 1850 */       V v = (V)s.readObject();
/* 1851 */       if (k == '\000') {
/* 1852 */         pos = this.n;
/* 1853 */         this.containsNullKey = true;
/*      */       } else {
/* 1855 */         pos = HashCommon.mix(k) & this.mask;
/* 1856 */         while (key[pos] != '\000')
/* 1857 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1859 */       key[pos] = k;
/* 1860 */       value[pos] = v;
/* 1861 */       if (this.first != -1) {
/* 1862 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1863 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1864 */         prev = pos; continue;
/*      */       } 
/* 1866 */       prev = this.first = pos;
/*      */       
/* 1868 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1871 */     this.last = prev;
/* 1872 */     if (prev != -1)
/*      */     {
/* 1874 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2ReferenceLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */