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
/*      */ public class Reference2CharLinkedOpenHashMap<K>
/*      */   extends AbstractReference2CharSortedMap<K>
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
/*      */   protected transient Reference2CharSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ReferenceSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient CharCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2CharLinkedOpenHashMap(int expected, float f) {
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
/*      */   public Reference2CharLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2CharLinkedOpenHashMap() {
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
/*      */   public Reference2CharLinkedOpenHashMap(Map<? extends K, ? extends Character> m, float f) {
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
/*      */   public Reference2CharLinkedOpenHashMap(Map<? extends K, ? extends Character> m) {
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
/*      */   public Reference2CharLinkedOpenHashMap(Reference2CharMap<K> m, float f) {
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
/*      */   public Reference2CharLinkedOpenHashMap(Reference2CharMap<K> m) {
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
/*      */   public Reference2CharLinkedOpenHashMap(K[] k, char[] v, float f) {
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
/*      */   public Reference2CharLinkedOpenHashMap(K[] k, char[] v) {
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
/*  310 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  311 */       return -(pos + 1); 
/*  312 */     if (k == curr) {
/*  313 */       return pos;
/*      */     }
/*      */     while (true) {
/*  316 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  317 */         return -(pos + 1); 
/*  318 */       if (k == curr)
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
/*  371 */         int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
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
/*  393 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  394 */       return this.defRetValue; 
/*  395 */     if (k == curr)
/*  396 */       return removeEntry(pos); 
/*      */     while (true) {
/*  398 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  399 */         return this.defRetValue; 
/*  400 */       if (k == curr)
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
/*  523 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  524 */       return this.defRetValue; 
/*  525 */     if (k == curr) {
/*  526 */       moveIndexToFirst(pos);
/*  527 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  531 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  532 */         return this.defRetValue; 
/*  533 */       if (k == curr) {
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
/*  560 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  561 */       return this.defRetValue; 
/*  562 */     if (k == curr) {
/*  563 */       moveIndexToLast(pos);
/*  564 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  568 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  569 */         return this.defRetValue; 
/*  570 */       if (k == curr) {
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
/*  600 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*      */         
/*  602 */         if (curr == k) {
/*  603 */           moveIndexToFirst(pos);
/*  604 */           return setValue(pos, v);
/*      */         } 
/*  606 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
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
/*      */   public char putAndMoveToLast(K k, char v) {
/*      */     int pos;
/*  643 */     if (k == null) {
/*  644 */       if (this.containsNullKey) {
/*  645 */         moveIndexToLast(this.n);
/*  646 */         return setValue(this.n, v);
/*      */       } 
/*  648 */       this.containsNullKey = true;
/*  649 */       pos = this.n;
/*      */     } else {
/*      */       
/*  652 */       K[] key = this.key;
/*      */       K curr;
/*  654 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*      */         
/*  656 */         if (curr == k) {
/*  657 */           moveIndexToLast(pos);
/*  658 */           return setValue(pos, v);
/*      */         } 
/*  660 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  661 */           if (curr == k) {
/*  662 */             moveIndexToLast(pos);
/*  663 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  667 */     }  this.key[pos] = k;
/*  668 */     this.value[pos] = v;
/*  669 */     if (this.size == 0) {
/*  670 */       this.first = this.last = pos;
/*      */       
/*  672 */       this.link[pos] = -1L;
/*      */     } else {
/*  674 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  675 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  676 */       this.last = pos;
/*      */     } 
/*  678 */     if (this.size++ >= this.maxFill) {
/*  679 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  682 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char getChar(Object k) {
/*  687 */     if (k == null) {
/*  688 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  690 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  693 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  694 */       return this.defRetValue; 
/*  695 */     if (k == curr) {
/*  696 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  699 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  700 */         return this.defRetValue; 
/*  701 */       if (k == curr) {
/*  702 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  708 */     if (k == null) {
/*  709 */       return this.containsNullKey;
/*      */     }
/*  711 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  714 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  715 */       return false; 
/*  716 */     if (k == curr) {
/*  717 */       return true;
/*      */     }
/*      */     while (true) {
/*  720 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  721 */         return false; 
/*  722 */       if (k == curr)
/*  723 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  728 */     char[] value = this.value;
/*  729 */     K[] key = this.key;
/*  730 */     if (this.containsNullKey && value[this.n] == v)
/*  731 */       return true; 
/*  732 */     for (int i = this.n; i-- != 0;) {
/*  733 */       if (key[i] != null && value[i] == v)
/*  734 */         return true; 
/*  735 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(Object k, char defaultValue) {
/*  741 */     if (k == null) {
/*  742 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  744 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  747 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  748 */       return defaultValue; 
/*  749 */     if (k == curr) {
/*  750 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  753 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  754 */         return defaultValue; 
/*  755 */       if (k == curr) {
/*  756 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char putIfAbsent(K k, char v) {
/*  762 */     int pos = find(k);
/*  763 */     if (pos >= 0)
/*  764 */       return this.value[pos]; 
/*  765 */     insert(-pos - 1, k, v);
/*  766 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, char v) {
/*  772 */     if (k == null) {
/*  773 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  774 */         removeNullEntry();
/*  775 */         return true;
/*      */       } 
/*  777 */       return false;
/*      */     } 
/*      */     
/*  780 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  783 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  784 */       return false; 
/*  785 */     if (k == curr && v == this.value[pos]) {
/*  786 */       removeEntry(pos);
/*  787 */       return true;
/*      */     } 
/*      */     while (true) {
/*  790 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  791 */         return false; 
/*  792 */       if (k == curr && v == this.value[pos]) {
/*  793 */         removeEntry(pos);
/*  794 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, char oldValue, char v) {
/*  801 */     int pos = find(k);
/*  802 */     if (pos < 0 || oldValue != this.value[pos])
/*  803 */       return false; 
/*  804 */     this.value[pos] = v;
/*  805 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public char replace(K k, char v) {
/*  810 */     int pos = find(k);
/*  811 */     if (pos < 0)
/*  812 */       return this.defRetValue; 
/*  813 */     char oldValue = this.value[pos];
/*  814 */     this.value[pos] = v;
/*  815 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char computeCharIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
/*  820 */     Objects.requireNonNull(mappingFunction);
/*  821 */     int pos = find(k);
/*  822 */     if (pos >= 0)
/*  823 */       return this.value[pos]; 
/*  824 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
/*  825 */     insert(-pos - 1, k, newValue);
/*  826 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeCharIfPresent(K k, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
/*  832 */     Objects.requireNonNull(remappingFunction);
/*  833 */     int pos = find(k);
/*  834 */     if (pos < 0)
/*  835 */       return this.defRetValue; 
/*  836 */     Character newValue = remappingFunction.apply(k, Character.valueOf(this.value[pos]));
/*  837 */     if (newValue == null) {
/*  838 */       if (k == null) {
/*  839 */         removeNullEntry();
/*      */       } else {
/*  841 */         removeEntry(pos);
/*  842 */       }  return this.defRetValue;
/*      */     } 
/*  844 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeChar(K k, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
/*  850 */     Objects.requireNonNull(remappingFunction);
/*  851 */     int pos = find(k);
/*  852 */     Character newValue = remappingFunction.apply(k, (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  853 */     if (newValue == null) {
/*  854 */       if (pos >= 0)
/*  855 */         if (k == null) {
/*  856 */           removeNullEntry();
/*      */         } else {
/*  858 */           removeEntry(pos);
/*      */         }  
/*  860 */       return this.defRetValue;
/*      */     } 
/*  862 */     char newVal = newValue.charValue();
/*  863 */     if (pos < 0) {
/*  864 */       insert(-pos - 1, k, newVal);
/*  865 */       return newVal;
/*      */     } 
/*  867 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char mergeChar(K k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  873 */     Objects.requireNonNull(remappingFunction);
/*  874 */     int pos = find(k);
/*  875 */     if (pos < 0) {
/*  876 */       insert(-pos - 1, k, v);
/*  877 */       return v;
/*      */     } 
/*  879 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  880 */     if (newValue == null) {
/*  881 */       if (k == null) {
/*  882 */         removeNullEntry();
/*      */       } else {
/*  884 */         removeEntry(pos);
/*  885 */       }  return this.defRetValue;
/*      */     } 
/*  887 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
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
/*  898 */     if (this.size == 0)
/*      */       return; 
/*  900 */     this.size = 0;
/*  901 */     this.containsNullKey = false;
/*  902 */     Arrays.fill((Object[])this.key, (Object)null);
/*  903 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  907 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  911 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2CharMap.Entry<K>, Map.Entry<K, Character>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  923 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  929 */       return Reference2CharLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public char getCharValue() {
/*  933 */       return Reference2CharLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public char setValue(char v) {
/*  937 */       char oldValue = Reference2CharLinkedOpenHashMap.this.value[this.index];
/*  938 */       Reference2CharLinkedOpenHashMap.this.value[this.index] = v;
/*  939 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getValue() {
/*  949 */       return Character.valueOf(Reference2CharLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character setValue(Character v) {
/*  959 */       return Character.valueOf(setValue(v.charValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  964 */       if (!(o instanceof Map.Entry))
/*  965 */         return false; 
/*  966 */       Map.Entry<K, Character> e = (Map.Entry<K, Character>)o;
/*  967 */       return (Reference2CharLinkedOpenHashMap.this.key[this.index] == e.getKey() && Reference2CharLinkedOpenHashMap.this.value[this.index] == ((Character)e.getValue()).charValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  971 */       return System.identityHashCode(Reference2CharLinkedOpenHashMap.this.key[this.index]) ^ Reference2CharLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  975 */       return (new StringBuilder()).append(Reference2CharLinkedOpenHashMap.this.key[this.index]).append("=>").append(Reference2CharLinkedOpenHashMap.this.value[this.index]).toString();
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
/*  986 */     if (this.size == 0) {
/*  987 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  990 */     if (this.first == i) {
/*  991 */       this.first = (int)this.link[i];
/*  992 */       if (0 <= this.first)
/*      */       {
/*  994 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  998 */     if (this.last == i) {
/*  999 */       this.last = (int)(this.link[i] >>> 32L);
/* 1000 */       if (0 <= this.last)
/*      */       {
/* 1002 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1006 */     long linki = this.link[i];
/* 1007 */     int prev = (int)(linki >>> 32L);
/* 1008 */     int next = (int)linki;
/* 1009 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1010 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1023 */     if (this.size == 1) {
/* 1024 */       this.first = this.last = d;
/*      */       
/* 1026 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1029 */     if (this.first == s) {
/* 1030 */       this.first = d;
/* 1031 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1032 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1035 */     if (this.last == s) {
/* 1036 */       this.last = d;
/* 1037 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1038 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1041 */     long links = this.link[s];
/* 1042 */     int prev = (int)(links >>> 32L);
/* 1043 */     int next = (int)links;
/* 1044 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1045 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1046 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1055 */     if (this.size == 0)
/* 1056 */       throw new NoSuchElementException(); 
/* 1057 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1066 */     if (this.size == 0)
/* 1067 */       throw new NoSuchElementException(); 
/* 1068 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2CharSortedMap<K> tailMap(K from) {
/* 1077 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2CharSortedMap<K> headMap(K to) {
/* 1086 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2CharSortedMap<K> subMap(K from, K to) {
/* 1095 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1104 */     return null;
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
/* 1119 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1125 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1130 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1136 */     int index = -1;
/*      */     protected MapIterator() {
/* 1138 */       this.next = Reference2CharLinkedOpenHashMap.this.first;
/* 1139 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/* 1142 */       if (from == null) {
/* 1143 */         if (Reference2CharLinkedOpenHashMap.this.containsNullKey) {
/* 1144 */           this.next = (int)Reference2CharLinkedOpenHashMap.this.link[Reference2CharLinkedOpenHashMap.this.n];
/* 1145 */           this.prev = Reference2CharLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1148 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1150 */       if (Reference2CharLinkedOpenHashMap.this.key[Reference2CharLinkedOpenHashMap.this.last] == from) {
/* 1151 */         this.prev = Reference2CharLinkedOpenHashMap.this.last;
/* 1152 */         this.index = Reference2CharLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1156 */       int pos = HashCommon.mix(System.identityHashCode(from)) & Reference2CharLinkedOpenHashMap.this.mask;
/*      */       
/* 1158 */       while (Reference2CharLinkedOpenHashMap.this.key[pos] != null) {
/* 1159 */         if (Reference2CharLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1161 */           this.next = (int)Reference2CharLinkedOpenHashMap.this.link[pos];
/* 1162 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1165 */         pos = pos + 1 & Reference2CharLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1167 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1170 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1173 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1176 */       if (this.index >= 0)
/*      */         return; 
/* 1178 */       if (this.prev == -1) {
/* 1179 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1182 */       if (this.next == -1) {
/* 1183 */         this.index = Reference2CharLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1186 */       int pos = Reference2CharLinkedOpenHashMap.this.first;
/* 1187 */       this.index = 1;
/* 1188 */       while (pos != this.prev) {
/* 1189 */         pos = (int)Reference2CharLinkedOpenHashMap.this.link[pos];
/* 1190 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1194 */       ensureIndexKnown();
/* 1195 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1198 */       ensureIndexKnown();
/* 1199 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1202 */       if (!hasNext())
/* 1203 */         throw new NoSuchElementException(); 
/* 1204 */       this.curr = this.next;
/* 1205 */       this.next = (int)Reference2CharLinkedOpenHashMap.this.link[this.curr];
/* 1206 */       this.prev = this.curr;
/* 1207 */       if (this.index >= 0)
/* 1208 */         this.index++; 
/* 1209 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1212 */       if (!hasPrevious())
/* 1213 */         throw new NoSuchElementException(); 
/* 1214 */       this.curr = this.prev;
/* 1215 */       this.prev = (int)(Reference2CharLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1216 */       this.next = this.curr;
/* 1217 */       if (this.index >= 0)
/* 1218 */         this.index--; 
/* 1219 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1222 */       ensureIndexKnown();
/* 1223 */       if (this.curr == -1)
/* 1224 */         throw new IllegalStateException(); 
/* 1225 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1230 */         this.index--;
/* 1231 */         this.prev = (int)(Reference2CharLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1233 */         this.next = (int)Reference2CharLinkedOpenHashMap.this.link[this.curr];
/* 1234 */       }  Reference2CharLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1239 */       if (this.prev == -1) {
/* 1240 */         Reference2CharLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1242 */         Reference2CharLinkedOpenHashMap.this.link[this.prev] = Reference2CharLinkedOpenHashMap.this.link[this.prev] ^ (Reference2CharLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1243 */       }  if (this.next == -1) {
/* 1244 */         Reference2CharLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1246 */         Reference2CharLinkedOpenHashMap.this.link[this.next] = Reference2CharLinkedOpenHashMap.this.link[this.next] ^ (Reference2CharLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1247 */       }  int pos = this.curr;
/* 1248 */       this.curr = -1;
/* 1249 */       if (pos == Reference2CharLinkedOpenHashMap.this.n) {
/* 1250 */         Reference2CharLinkedOpenHashMap.this.containsNullKey = false;
/* 1251 */         Reference2CharLinkedOpenHashMap.this.key[Reference2CharLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1254 */         K[] key = Reference2CharLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1258 */           pos = (last = pos) + 1 & Reference2CharLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1260 */             if ((curr = key[pos]) == null) {
/* 1261 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1264 */             int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2CharLinkedOpenHashMap.this.mask;
/* 1265 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1267 */             pos = pos + 1 & Reference2CharLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1269 */           key[last] = curr;
/* 1270 */           Reference2CharLinkedOpenHashMap.this.value[last] = Reference2CharLinkedOpenHashMap.this.value[pos];
/* 1271 */           if (this.next == pos)
/* 1272 */             this.next = last; 
/* 1273 */           if (this.prev == pos)
/* 1274 */             this.prev = last; 
/* 1275 */           Reference2CharLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1280 */       int i = n;
/* 1281 */       while (i-- != 0 && hasNext())
/* 1282 */         nextEntry(); 
/* 1283 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1286 */       int i = n;
/* 1287 */       while (i-- != 0 && hasPrevious())
/* 1288 */         previousEntry(); 
/* 1289 */       return n - i - 1;
/*      */     }
/*      */     public void set(Reference2CharMap.Entry<K> ok) {
/* 1292 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Reference2CharMap.Entry<K> ok) {
/* 1295 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Reference2CharMap.Entry<K>> { private Reference2CharLinkedOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1303 */       super(from);
/*      */     }
/*      */     
/*      */     public Reference2CharLinkedOpenHashMap<K>.MapEntry next() {
/* 1307 */       return this.entry = new Reference2CharLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Reference2CharLinkedOpenHashMap<K>.MapEntry previous() {
/* 1311 */       return this.entry = new Reference2CharLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1315 */       super.remove();
/* 1316 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1320 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Reference2CharMap.Entry<K>> { final Reference2CharLinkedOpenHashMap<K>.MapEntry entry = new Reference2CharLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1324 */       super(from);
/*      */     }
/*      */     
/*      */     public Reference2CharLinkedOpenHashMap<K>.MapEntry next() {
/* 1328 */       this.entry.index = nextEntry();
/* 1329 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Reference2CharLinkedOpenHashMap<K>.MapEntry previous() {
/* 1333 */       this.entry.index = previousEntry();
/* 1334 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Reference2CharMap.Entry<K>> implements Reference2CharSortedMap.FastSortedEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Reference2CharMap.Entry<K>> iterator() {
/* 1342 */       return new Reference2CharLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Reference2CharMap.Entry<K>> comparator() {
/* 1346 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Reference2CharMap.Entry<K>> subSet(Reference2CharMap.Entry<K> fromElement, Reference2CharMap.Entry<K> toElement) {
/* 1351 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Reference2CharMap.Entry<K>> headSet(Reference2CharMap.Entry<K> toElement) {
/* 1355 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Reference2CharMap.Entry<K>> tailSet(Reference2CharMap.Entry<K> fromElement) {
/* 1359 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Reference2CharMap.Entry<K> first() {
/* 1363 */       if (Reference2CharLinkedOpenHashMap.this.size == 0)
/* 1364 */         throw new NoSuchElementException(); 
/* 1365 */       return new Reference2CharLinkedOpenHashMap.MapEntry(Reference2CharLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Reference2CharMap.Entry<K> last() {
/* 1369 */       if (Reference2CharLinkedOpenHashMap.this.size == 0)
/* 1370 */         throw new NoSuchElementException(); 
/* 1371 */       return new Reference2CharLinkedOpenHashMap.MapEntry(Reference2CharLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1376 */       if (!(o instanceof Map.Entry))
/* 1377 */         return false; 
/* 1378 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1379 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 1380 */         return false; 
/* 1381 */       K k = (K)e.getKey();
/* 1382 */       char v = ((Character)e.getValue()).charValue();
/* 1383 */       if (k == null) {
/* 1384 */         return (Reference2CharLinkedOpenHashMap.this.containsNullKey && Reference2CharLinkedOpenHashMap.this.value[Reference2CharLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1386 */       K[] key = Reference2CharLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1389 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2CharLinkedOpenHashMap.this.mask]) == null)
/*      */       {
/* 1391 */         return false; } 
/* 1392 */       if (k == curr) {
/* 1393 */         return (Reference2CharLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1396 */         if ((curr = key[pos = pos + 1 & Reference2CharLinkedOpenHashMap.this.mask]) == null)
/* 1397 */           return false; 
/* 1398 */         if (k == curr) {
/* 1399 */           return (Reference2CharLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1405 */       if (!(o instanceof Map.Entry))
/* 1406 */         return false; 
/* 1407 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1408 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 1409 */         return false; 
/* 1410 */       K k = (K)e.getKey();
/* 1411 */       char v = ((Character)e.getValue()).charValue();
/* 1412 */       if (k == null) {
/* 1413 */         if (Reference2CharLinkedOpenHashMap.this.containsNullKey && Reference2CharLinkedOpenHashMap.this.value[Reference2CharLinkedOpenHashMap.this.n] == v) {
/* 1414 */           Reference2CharLinkedOpenHashMap.this.removeNullEntry();
/* 1415 */           return true;
/*      */         } 
/* 1417 */         return false;
/*      */       } 
/*      */       
/* 1420 */       K[] key = Reference2CharLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1423 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2CharLinkedOpenHashMap.this.mask]) == null)
/*      */       {
/* 1425 */         return false; } 
/* 1426 */       if (curr == k) {
/* 1427 */         if (Reference2CharLinkedOpenHashMap.this.value[pos] == v) {
/* 1428 */           Reference2CharLinkedOpenHashMap.this.removeEntry(pos);
/* 1429 */           return true;
/*      */         } 
/* 1431 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1434 */         if ((curr = key[pos = pos + 1 & Reference2CharLinkedOpenHashMap.this.mask]) == null)
/* 1435 */           return false; 
/* 1436 */         if (curr == k && 
/* 1437 */           Reference2CharLinkedOpenHashMap.this.value[pos] == v) {
/* 1438 */           Reference2CharLinkedOpenHashMap.this.removeEntry(pos);
/* 1439 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1446 */       return Reference2CharLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1450 */       Reference2CharLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Reference2CharMap.Entry<K>> iterator(Reference2CharMap.Entry<K> from) {
/* 1465 */       return new Reference2CharLinkedOpenHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Reference2CharMap.Entry<K>> fastIterator() {
/* 1476 */       return new Reference2CharLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Reference2CharMap.Entry<K>> fastIterator(Reference2CharMap.Entry<K> from) {
/* 1491 */       return new Reference2CharLinkedOpenHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2CharMap.Entry<K>> consumer) {
/* 1496 */       for (int i = Reference2CharLinkedOpenHashMap.this.size, next = Reference2CharLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1497 */         int curr = next;
/* 1498 */         next = (int)Reference2CharLinkedOpenHashMap.this.link[curr];
/* 1499 */         consumer.accept(new AbstractReference2CharMap.BasicEntry<>(Reference2CharLinkedOpenHashMap.this.key[curr], Reference2CharLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2CharMap.Entry<K>> consumer) {
/* 1505 */       AbstractReference2CharMap.BasicEntry<K> entry = new AbstractReference2CharMap.BasicEntry<>();
/* 1506 */       for (int i = Reference2CharLinkedOpenHashMap.this.size, next = Reference2CharLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1507 */         int curr = next;
/* 1508 */         next = (int)Reference2CharLinkedOpenHashMap.this.link[curr];
/* 1509 */         entry.key = Reference2CharLinkedOpenHashMap.this.key[curr];
/* 1510 */         entry.value = Reference2CharLinkedOpenHashMap.this.value[curr];
/* 1511 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Reference2CharSortedMap.FastSortedEntrySet<K> reference2CharEntrySet() {
/* 1517 */     if (this.entries == null)
/* 1518 */       this.entries = new MapEntrySet(); 
/* 1519 */     return this.entries;
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
/* 1532 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1536 */       return Reference2CharLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1543 */       return Reference2CharLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1549 */       return new Reference2CharLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1553 */       return new Reference2CharLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1558 */       if (Reference2CharLinkedOpenHashMap.this.containsNullKey)
/* 1559 */         consumer.accept(Reference2CharLinkedOpenHashMap.this.key[Reference2CharLinkedOpenHashMap.this.n]); 
/* 1560 */       for (int pos = Reference2CharLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1561 */         K k = Reference2CharLinkedOpenHashMap.this.key[pos];
/* 1562 */         if (k != null)
/* 1563 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1568 */       return Reference2CharLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1572 */       return Reference2CharLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1576 */       int oldSize = Reference2CharLinkedOpenHashMap.this.size;
/* 1577 */       Reference2CharLinkedOpenHashMap.this.removeChar(k);
/* 1578 */       return (Reference2CharLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1582 */       Reference2CharLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1586 */       if (Reference2CharLinkedOpenHashMap.this.size == 0)
/* 1587 */         throw new NoSuchElementException(); 
/* 1588 */       return Reference2CharLinkedOpenHashMap.this.key[Reference2CharLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1592 */       if (Reference2CharLinkedOpenHashMap.this.size == 0)
/* 1593 */         throw new NoSuchElementException(); 
/* 1594 */       return Reference2CharLinkedOpenHashMap.this.key[Reference2CharLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1598 */       return null;
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> tailSet(K from) {
/* 1602 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> headSet(K to) {
/* 1606 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> subSet(K from, K to) {
/* 1610 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ReferenceSortedSet<K> keySet() {
/* 1615 */     if (this.keys == null)
/* 1616 */       this.keys = new KeySet(); 
/* 1617 */     return this.keys;
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
/* 1631 */       return Reference2CharLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1638 */       return Reference2CharLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public CharCollection values() {
/* 1643 */     if (this.values == null)
/* 1644 */       this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/* 1647 */             return (CharIterator)new Reference2CharLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1651 */             return Reference2CharLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(char v) {
/* 1655 */             return Reference2CharLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1659 */             Reference2CharLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(IntConsumer consumer) {
/* 1664 */             if (Reference2CharLinkedOpenHashMap.this.containsNullKey)
/* 1665 */               consumer.accept(Reference2CharLinkedOpenHashMap.this.value[Reference2CharLinkedOpenHashMap.this.n]); 
/* 1666 */             for (int pos = Reference2CharLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1667 */               if (Reference2CharLinkedOpenHashMap.this.key[pos] != null)
/* 1668 */                 consumer.accept(Reference2CharLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1671 */     return this.values;
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
/* 1688 */     return trim(this.size);
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
/* 1712 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1713 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1714 */       return true; 
/*      */     try {
/* 1716 */       rehash(l);
/* 1717 */     } catch (OutOfMemoryError cantDoIt) {
/* 1718 */       return false;
/*      */     } 
/* 1720 */     return true;
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
/* 1736 */     K[] key = this.key;
/* 1737 */     char[] value = this.value;
/* 1738 */     int mask = newN - 1;
/* 1739 */     K[] newKey = (K[])new Object[newN + 1];
/* 1740 */     char[] newValue = new char[newN + 1];
/* 1741 */     int i = this.first, prev = -1, newPrev = -1;
/* 1742 */     long[] link = this.link;
/* 1743 */     long[] newLink = new long[newN + 1];
/* 1744 */     this.first = -1;
/* 1745 */     for (int j = this.size; j-- != 0; ) {
/* 1746 */       int pos; if (key[i] == null) {
/* 1747 */         pos = newN;
/*      */       } else {
/* 1749 */         pos = HashCommon.mix(System.identityHashCode(key[i])) & mask;
/* 1750 */         while (newKey[pos] != null)
/* 1751 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1753 */       newKey[pos] = key[i];
/* 1754 */       newValue[pos] = value[i];
/* 1755 */       if (prev != -1) {
/* 1756 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1757 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1758 */         newPrev = pos;
/*      */       } else {
/* 1760 */         newPrev = this.first = pos;
/*      */         
/* 1762 */         newLink[pos] = -1L;
/*      */       } 
/* 1764 */       int t = i;
/* 1765 */       i = (int)link[i];
/* 1766 */       prev = t;
/*      */     } 
/* 1768 */     this.link = newLink;
/* 1769 */     this.last = newPrev;
/* 1770 */     if (newPrev != -1)
/*      */     {
/* 1772 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1773 */     this.n = newN;
/* 1774 */     this.mask = mask;
/* 1775 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1776 */     this.key = newKey;
/* 1777 */     this.value = newValue;
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
/*      */   public Reference2CharLinkedOpenHashMap<K> clone() {
/*      */     Reference2CharLinkedOpenHashMap<K> c;
/*      */     try {
/* 1794 */       c = (Reference2CharLinkedOpenHashMap<K>)super.clone();
/* 1795 */     } catch (CloneNotSupportedException cantHappen) {
/* 1796 */       throw new InternalError();
/*      */     } 
/* 1798 */     c.keys = null;
/* 1799 */     c.values = null;
/* 1800 */     c.entries = null;
/* 1801 */     c.containsNullKey = this.containsNullKey;
/* 1802 */     c.key = (K[])this.key.clone();
/* 1803 */     c.value = (char[])this.value.clone();
/* 1804 */     c.link = (long[])this.link.clone();
/* 1805 */     return c;
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
/* 1818 */     int h = 0;
/* 1819 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1820 */       while (this.key[i] == null)
/* 1821 */         i++; 
/* 1822 */       if (this != this.key[i])
/* 1823 */         t = System.identityHashCode(this.key[i]); 
/* 1824 */       t ^= this.value[i];
/* 1825 */       h += t;
/* 1826 */       i++;
/*      */     } 
/*      */     
/* 1829 */     if (this.containsNullKey)
/* 1830 */       h += this.value[this.n]; 
/* 1831 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1834 */     K[] key = this.key;
/* 1835 */     char[] value = this.value;
/* 1836 */     MapIterator i = new MapIterator();
/* 1837 */     s.defaultWriteObject();
/* 1838 */     for (int j = this.size; j-- != 0; ) {
/* 1839 */       int e = i.nextEntry();
/* 1840 */       s.writeObject(key[e]);
/* 1841 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1846 */     s.defaultReadObject();
/* 1847 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1848 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1849 */     this.mask = this.n - 1;
/* 1850 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1851 */     char[] value = this.value = new char[this.n + 1];
/* 1852 */     long[] link = this.link = new long[this.n + 1];
/* 1853 */     int prev = -1;
/* 1854 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1857 */     for (int i = this.size; i-- != 0; ) {
/* 1858 */       int pos; K k = (K)s.readObject();
/* 1859 */       char v = s.readChar();
/* 1860 */       if (k == null) {
/* 1861 */         pos = this.n;
/* 1862 */         this.containsNullKey = true;
/*      */       } else {
/* 1864 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 1865 */         while (key[pos] != null)
/* 1866 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1868 */       key[pos] = k;
/* 1869 */       value[pos] = v;
/* 1870 */       if (this.first != -1) {
/* 1871 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1872 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1873 */         prev = pos; continue;
/*      */       } 
/* 1875 */       prev = this.first = pos;
/*      */       
/* 1877 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1880 */     this.last = prev;
/* 1881 */     if (prev != -1)
/*      */     {
/* 1883 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2CharLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */