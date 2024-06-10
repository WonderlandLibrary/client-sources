/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortListIterator;
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
/*      */ public class Reference2ShortLinkedOpenHashMap<K>
/*      */   extends AbstractReference2ShortSortedMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient short[] value;
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
/*      */   protected transient Reference2ShortSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ReferenceSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ShortCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ShortLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = (K[])new Object[this.n + 1];
/*  162 */     this.value = new short[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ShortLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ShortLinkedOpenHashMap() {
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
/*      */   public Reference2ShortLinkedOpenHashMap(Map<? extends K, ? extends Short> m, float f) {
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
/*      */   public Reference2ShortLinkedOpenHashMap(Map<? extends K, ? extends Short> m) {
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
/*      */   public Reference2ShortLinkedOpenHashMap(Reference2ShortMap<K> m, float f) {
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
/*      */   public Reference2ShortLinkedOpenHashMap(Reference2ShortMap<K> m) {
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
/*      */   public Reference2ShortLinkedOpenHashMap(K[] k, short[] v, float f) {
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
/*      */   public Reference2ShortLinkedOpenHashMap(K[] k, short[] v) {
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
/*      */   private short removeEntry(int pos) {
/*  275 */     short oldValue = this.value[pos];
/*  276 */     this.size--;
/*  277 */     fixPointers(pos);
/*  278 */     shiftKeys(pos);
/*  279 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  280 */       rehash(this.n / 2); 
/*  281 */     return oldValue;
/*      */   }
/*      */   private short removeNullEntry() {
/*  284 */     this.containsNullKey = false;
/*  285 */     this.key[this.n] = null;
/*  286 */     short oldValue = this.value[this.n];
/*  287 */     this.size--;
/*  288 */     fixPointers(this.n);
/*  289 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  290 */       rehash(this.n / 2); 
/*  291 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Short> m) {
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
/*      */   private void insert(int pos, K k, short v) {
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
/*      */   public short put(K k, short v) {
/*  343 */     int pos = find(k);
/*  344 */     if (pos < 0) {
/*  345 */       insert(-pos - 1, k, v);
/*  346 */       return this.defRetValue;
/*      */     } 
/*  348 */     short oldValue = this.value[pos];
/*  349 */     this.value[pos] = v;
/*  350 */     return oldValue;
/*      */   }
/*      */   private short addToValue(int pos, short incr) {
/*  353 */     short oldValue = this.value[pos];
/*  354 */     this.value[pos] = (short)(oldValue + incr);
/*  355 */     return oldValue;
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
/*      */   public short addTo(K k, short incr) {
/*      */     int pos;
/*  375 */     if (k == null) {
/*  376 */       if (this.containsNullKey)
/*  377 */         return addToValue(this.n, incr); 
/*  378 */       pos = this.n;
/*  379 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  382 */       K[] key = this.key;
/*      */       K curr;
/*  384 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*      */         
/*  386 */         if (curr == k)
/*  387 */           return addToValue(pos, incr); 
/*  388 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  389 */           if (curr == k)
/*  390 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  393 */     }  this.key[pos] = k;
/*  394 */     this.value[pos] = (short)(this.defRetValue + incr);
/*  395 */     if (this.size == 0) {
/*  396 */       this.first = this.last = pos;
/*      */       
/*  398 */       this.link[pos] = -1L;
/*      */     } else {
/*  400 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  401 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  402 */       this.last = pos;
/*      */     } 
/*  404 */     if (this.size++ >= this.maxFill) {
/*  405 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  408 */     return this.defRetValue;
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
/*  421 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  423 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  425 */         if ((curr = key[pos]) == null) {
/*  426 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  429 */         int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
/*  430 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  432 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  434 */       key[last] = curr;
/*  435 */       this.value[last] = this.value[pos];
/*  436 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public short removeShort(Object k) {
/*  442 */     if (k == null) {
/*  443 */       if (this.containsNullKey)
/*  444 */         return removeNullEntry(); 
/*  445 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  448 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  451 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  452 */       return this.defRetValue; 
/*  453 */     if (k == curr)
/*  454 */       return removeEntry(pos); 
/*      */     while (true) {
/*  456 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  457 */         return this.defRetValue; 
/*  458 */       if (k == curr)
/*  459 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private short setValue(int pos, short v) {
/*  463 */     short oldValue = this.value[pos];
/*  464 */     this.value[pos] = v;
/*  465 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short removeFirstShort() {
/*  476 */     if (this.size == 0)
/*  477 */       throw new NoSuchElementException(); 
/*  478 */     int pos = this.first;
/*      */     
/*  480 */     this.first = (int)this.link[pos];
/*  481 */     if (0 <= this.first)
/*      */     {
/*  483 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  485 */     this.size--;
/*  486 */     short v = this.value[pos];
/*  487 */     if (pos == this.n) {
/*  488 */       this.containsNullKey = false;
/*  489 */       this.key[this.n] = null;
/*      */     } else {
/*  491 */       shiftKeys(pos);
/*  492 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  493 */       rehash(this.n / 2); 
/*  494 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short removeLastShort() {
/*  504 */     if (this.size == 0)
/*  505 */       throw new NoSuchElementException(); 
/*  506 */     int pos = this.last;
/*      */     
/*  508 */     this.last = (int)(this.link[pos] >>> 32L);
/*  509 */     if (0 <= this.last)
/*      */     {
/*  511 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  513 */     this.size--;
/*  514 */     short v = this.value[pos];
/*  515 */     if (pos == this.n) {
/*  516 */       this.containsNullKey = false;
/*  517 */       this.key[this.n] = null;
/*      */     } else {
/*  519 */       shiftKeys(pos);
/*  520 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  521 */       rehash(this.n / 2); 
/*  522 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  525 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  527 */     if (this.last == i) {
/*  528 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  530 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  532 */       long linki = this.link[i];
/*  533 */       int prev = (int)(linki >>> 32L);
/*  534 */       int next = (int)linki;
/*  535 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  536 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  538 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  539 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  540 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  543 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  545 */     if (this.first == i) {
/*  546 */       this.first = (int)this.link[i];
/*      */       
/*  548 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  550 */       long linki = this.link[i];
/*  551 */       int prev = (int)(linki >>> 32L);
/*  552 */       int next = (int)linki;
/*  553 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  554 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  556 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  557 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  558 */     this.last = i;
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
/*      */   public short getAndMoveToFirst(K k) {
/*  570 */     if (k == null) {
/*  571 */       if (this.containsNullKey) {
/*  572 */         moveIndexToFirst(this.n);
/*  573 */         return this.value[this.n];
/*      */       } 
/*  575 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  578 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  581 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  582 */       return this.defRetValue; 
/*  583 */     if (k == curr) {
/*  584 */       moveIndexToFirst(pos);
/*  585 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  589 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  590 */         return this.defRetValue; 
/*  591 */       if (k == curr) {
/*  592 */         moveIndexToFirst(pos);
/*  593 */         return this.value[pos];
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
/*      */   public short getAndMoveToLast(K k) {
/*  607 */     if (k == null) {
/*  608 */       if (this.containsNullKey) {
/*  609 */         moveIndexToLast(this.n);
/*  610 */         return this.value[this.n];
/*      */       } 
/*  612 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  615 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  618 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  619 */       return this.defRetValue; 
/*  620 */     if (k == curr) {
/*  621 */       moveIndexToLast(pos);
/*  622 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  626 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  627 */         return this.defRetValue; 
/*  628 */       if (k == curr) {
/*  629 */         moveIndexToLast(pos);
/*  630 */         return this.value[pos];
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
/*      */   public short putAndMoveToFirst(K k, short v) {
/*      */     int pos;
/*  647 */     if (k == null) {
/*  648 */       if (this.containsNullKey) {
/*  649 */         moveIndexToFirst(this.n);
/*  650 */         return setValue(this.n, v);
/*      */       } 
/*  652 */       this.containsNullKey = true;
/*  653 */       pos = this.n;
/*      */     } else {
/*      */       
/*  656 */       K[] key = this.key;
/*      */       K curr;
/*  658 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*      */         
/*  660 */         if (curr == k) {
/*  661 */           moveIndexToFirst(pos);
/*  662 */           return setValue(pos, v);
/*      */         } 
/*  664 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  665 */           if (curr == k) {
/*  666 */             moveIndexToFirst(pos);
/*  667 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  671 */     }  this.key[pos] = k;
/*  672 */     this.value[pos] = v;
/*  673 */     if (this.size == 0) {
/*  674 */       this.first = this.last = pos;
/*      */       
/*  676 */       this.link[pos] = -1L;
/*      */     } else {
/*  678 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  679 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  680 */       this.first = pos;
/*      */     } 
/*  682 */     if (this.size++ >= this.maxFill) {
/*  683 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  686 */     return this.defRetValue;
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
/*      */   public short putAndMoveToLast(K k, short v) {
/*      */     int pos;
/*  701 */     if (k == null) {
/*  702 */       if (this.containsNullKey) {
/*  703 */         moveIndexToLast(this.n);
/*  704 */         return setValue(this.n, v);
/*      */       } 
/*  706 */       this.containsNullKey = true;
/*  707 */       pos = this.n;
/*      */     } else {
/*      */       
/*  710 */       K[] key = this.key;
/*      */       K curr;
/*  712 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*      */         
/*  714 */         if (curr == k) {
/*  715 */           moveIndexToLast(pos);
/*  716 */           return setValue(pos, v);
/*      */         } 
/*  718 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  719 */           if (curr == k) {
/*  720 */             moveIndexToLast(pos);
/*  721 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  725 */     }  this.key[pos] = k;
/*  726 */     this.value[pos] = v;
/*  727 */     if (this.size == 0) {
/*  728 */       this.first = this.last = pos;
/*      */       
/*  730 */       this.link[pos] = -1L;
/*      */     } else {
/*  732 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  733 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  734 */       this.last = pos;
/*      */     } 
/*  736 */     if (this.size++ >= this.maxFill) {
/*  737 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  740 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShort(Object k) {
/*  745 */     if (k == null) {
/*  746 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  748 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  751 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  752 */       return this.defRetValue; 
/*  753 */     if (k == curr) {
/*  754 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  757 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  758 */         return this.defRetValue; 
/*  759 */       if (k == curr) {
/*  760 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  766 */     if (k == null) {
/*  767 */       return this.containsNullKey;
/*      */     }
/*  769 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  772 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  773 */       return false; 
/*  774 */     if (k == curr) {
/*  775 */       return true;
/*      */     }
/*      */     while (true) {
/*  778 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  779 */         return false; 
/*  780 */       if (k == curr)
/*  781 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(short v) {
/*  786 */     short[] value = this.value;
/*  787 */     K[] key = this.key;
/*  788 */     if (this.containsNullKey && value[this.n] == v)
/*  789 */       return true; 
/*  790 */     for (int i = this.n; i-- != 0;) {
/*  791 */       if (key[i] != null && value[i] == v)
/*  792 */         return true; 
/*  793 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short getOrDefault(Object k, short defaultValue) {
/*  799 */     if (k == null) {
/*  800 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  802 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  805 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  806 */       return defaultValue; 
/*  807 */     if (k == curr) {
/*  808 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  811 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  812 */         return defaultValue; 
/*  813 */       if (k == curr) {
/*  814 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public short putIfAbsent(K k, short v) {
/*  820 */     int pos = find(k);
/*  821 */     if (pos >= 0)
/*  822 */       return this.value[pos]; 
/*  823 */     insert(-pos - 1, k, v);
/*  824 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, short v) {
/*  830 */     if (k == null) {
/*  831 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  832 */         removeNullEntry();
/*  833 */         return true;
/*      */       } 
/*  835 */       return false;
/*      */     } 
/*      */     
/*  838 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  841 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  842 */       return false; 
/*  843 */     if (k == curr && v == this.value[pos]) {
/*  844 */       removeEntry(pos);
/*  845 */       return true;
/*      */     } 
/*      */     while (true) {
/*  848 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  849 */         return false; 
/*  850 */       if (k == curr && v == this.value[pos]) {
/*  851 */         removeEntry(pos);
/*  852 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, short oldValue, short v) {
/*  859 */     int pos = find(k);
/*  860 */     if (pos < 0 || oldValue != this.value[pos])
/*  861 */       return false; 
/*  862 */     this.value[pos] = v;
/*  863 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public short replace(K k, short v) {
/*  868 */     int pos = find(k);
/*  869 */     if (pos < 0)
/*  870 */       return this.defRetValue; 
/*  871 */     short oldValue = this.value[pos];
/*  872 */     this.value[pos] = v;
/*  873 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public short computeShortIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
/*  878 */     Objects.requireNonNull(mappingFunction);
/*  879 */     int pos = find(k);
/*  880 */     if (pos >= 0)
/*  881 */       return this.value[pos]; 
/*  882 */     short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(k));
/*  883 */     insert(-pos - 1, k, newValue);
/*  884 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeShortIfPresent(K k, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/*  890 */     Objects.requireNonNull(remappingFunction);
/*  891 */     int pos = find(k);
/*  892 */     if (pos < 0)
/*  893 */       return this.defRetValue; 
/*  894 */     Short newValue = remappingFunction.apply(k, Short.valueOf(this.value[pos]));
/*  895 */     if (newValue == null) {
/*  896 */       if (k == null) {
/*  897 */         removeNullEntry();
/*      */       } else {
/*  899 */         removeEntry(pos);
/*  900 */       }  return this.defRetValue;
/*      */     } 
/*  902 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeShort(K k, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/*  908 */     Objects.requireNonNull(remappingFunction);
/*  909 */     int pos = find(k);
/*  910 */     Short newValue = remappingFunction.apply(k, (pos >= 0) ? Short.valueOf(this.value[pos]) : null);
/*  911 */     if (newValue == null) {
/*  912 */       if (pos >= 0)
/*  913 */         if (k == null) {
/*  914 */           removeNullEntry();
/*      */         } else {
/*  916 */           removeEntry(pos);
/*      */         }  
/*  918 */       return this.defRetValue;
/*      */     } 
/*  920 */     short newVal = newValue.shortValue();
/*  921 */     if (pos < 0) {
/*  922 */       insert(-pos - 1, k, newVal);
/*  923 */       return newVal;
/*      */     } 
/*  925 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short mergeShort(K k, short v, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  931 */     Objects.requireNonNull(remappingFunction);
/*  932 */     int pos = find(k);
/*  933 */     if (pos < 0) {
/*  934 */       insert(-pos - 1, k, v);
/*  935 */       return v;
/*      */     } 
/*  937 */     Short newValue = remappingFunction.apply(Short.valueOf(this.value[pos]), Short.valueOf(v));
/*  938 */     if (newValue == null) {
/*  939 */       if (k == null) {
/*  940 */         removeNullEntry();
/*      */       } else {
/*  942 */         removeEntry(pos);
/*  943 */       }  return this.defRetValue;
/*      */     } 
/*  945 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
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
/*  956 */     if (this.size == 0)
/*      */       return; 
/*  958 */     this.size = 0;
/*  959 */     this.containsNullKey = false;
/*  960 */     Arrays.fill((Object[])this.key, (Object)null);
/*  961 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  965 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  969 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2ShortMap.Entry<K>, Map.Entry<K, Short>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  981 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  987 */       return Reference2ShortLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public short getShortValue() {
/*  991 */       return Reference2ShortLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public short setValue(short v) {
/*  995 */       short oldValue = Reference2ShortLinkedOpenHashMap.this.value[this.index];
/*  996 */       Reference2ShortLinkedOpenHashMap.this.value[this.index] = v;
/*  997 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getValue() {
/* 1007 */       return Short.valueOf(Reference2ShortLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short setValue(Short v) {
/* 1017 */       return Short.valueOf(setValue(v.shortValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1022 */       if (!(o instanceof Map.Entry))
/* 1023 */         return false; 
/* 1024 */       Map.Entry<K, Short> e = (Map.Entry<K, Short>)o;
/* 1025 */       return (Reference2ShortLinkedOpenHashMap.this.key[this.index] == e.getKey() && Reference2ShortLinkedOpenHashMap.this.value[this.index] == ((Short)e.getValue()).shortValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1029 */       return System.identityHashCode(Reference2ShortLinkedOpenHashMap.this.key[this.index]) ^ Reference2ShortLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1033 */       return (new StringBuilder()).append(Reference2ShortLinkedOpenHashMap.this.key[this.index]).append("=>").append(Reference2ShortLinkedOpenHashMap.this.value[this.index]).toString();
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
/* 1044 */     if (this.size == 0) {
/* 1045 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1048 */     if (this.first == i) {
/* 1049 */       this.first = (int)this.link[i];
/* 1050 */       if (0 <= this.first)
/*      */       {
/* 1052 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1056 */     if (this.last == i) {
/* 1057 */       this.last = (int)(this.link[i] >>> 32L);
/* 1058 */       if (0 <= this.last)
/*      */       {
/* 1060 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1064 */     long linki = this.link[i];
/* 1065 */     int prev = (int)(linki >>> 32L);
/* 1066 */     int next = (int)linki;
/* 1067 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1068 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1081 */     if (this.size == 1) {
/* 1082 */       this.first = this.last = d;
/*      */       
/* 1084 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1087 */     if (this.first == s) {
/* 1088 */       this.first = d;
/* 1089 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1090 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1093 */     if (this.last == s) {
/* 1094 */       this.last = d;
/* 1095 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1096 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1099 */     long links = this.link[s];
/* 1100 */     int prev = (int)(links >>> 32L);
/* 1101 */     int next = (int)links;
/* 1102 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1103 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1104 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1113 */     if (this.size == 0)
/* 1114 */       throw new NoSuchElementException(); 
/* 1115 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1124 */     if (this.size == 0)
/* 1125 */       throw new NoSuchElementException(); 
/* 1126 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ShortSortedMap<K> tailMap(K from) {
/* 1135 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ShortSortedMap<K> headMap(K to) {
/* 1144 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ShortSortedMap<K> subMap(K from, K to) {
/* 1153 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1162 */     return null;
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
/* 1177 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1183 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1188 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1194 */     int index = -1;
/*      */     protected MapIterator() {
/* 1196 */       this.next = Reference2ShortLinkedOpenHashMap.this.first;
/* 1197 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/* 1200 */       if (from == null) {
/* 1201 */         if (Reference2ShortLinkedOpenHashMap.this.containsNullKey) {
/* 1202 */           this.next = (int)Reference2ShortLinkedOpenHashMap.this.link[Reference2ShortLinkedOpenHashMap.this.n];
/* 1203 */           this.prev = Reference2ShortLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1206 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1208 */       if (Reference2ShortLinkedOpenHashMap.this.key[Reference2ShortLinkedOpenHashMap.this.last] == from) {
/* 1209 */         this.prev = Reference2ShortLinkedOpenHashMap.this.last;
/* 1210 */         this.index = Reference2ShortLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1214 */       int pos = HashCommon.mix(System.identityHashCode(from)) & Reference2ShortLinkedOpenHashMap.this.mask;
/*      */       
/* 1216 */       while (Reference2ShortLinkedOpenHashMap.this.key[pos] != null) {
/* 1217 */         if (Reference2ShortLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1219 */           this.next = (int)Reference2ShortLinkedOpenHashMap.this.link[pos];
/* 1220 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1223 */         pos = pos + 1 & Reference2ShortLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1225 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1228 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1231 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1234 */       if (this.index >= 0)
/*      */         return; 
/* 1236 */       if (this.prev == -1) {
/* 1237 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1240 */       if (this.next == -1) {
/* 1241 */         this.index = Reference2ShortLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1244 */       int pos = Reference2ShortLinkedOpenHashMap.this.first;
/* 1245 */       this.index = 1;
/* 1246 */       while (pos != this.prev) {
/* 1247 */         pos = (int)Reference2ShortLinkedOpenHashMap.this.link[pos];
/* 1248 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1252 */       ensureIndexKnown();
/* 1253 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1256 */       ensureIndexKnown();
/* 1257 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1260 */       if (!hasNext())
/* 1261 */         throw new NoSuchElementException(); 
/* 1262 */       this.curr = this.next;
/* 1263 */       this.next = (int)Reference2ShortLinkedOpenHashMap.this.link[this.curr];
/* 1264 */       this.prev = this.curr;
/* 1265 */       if (this.index >= 0)
/* 1266 */         this.index++; 
/* 1267 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1270 */       if (!hasPrevious())
/* 1271 */         throw new NoSuchElementException(); 
/* 1272 */       this.curr = this.prev;
/* 1273 */       this.prev = (int)(Reference2ShortLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1274 */       this.next = this.curr;
/* 1275 */       if (this.index >= 0)
/* 1276 */         this.index--; 
/* 1277 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1280 */       ensureIndexKnown();
/* 1281 */       if (this.curr == -1)
/* 1282 */         throw new IllegalStateException(); 
/* 1283 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1288 */         this.index--;
/* 1289 */         this.prev = (int)(Reference2ShortLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1291 */         this.next = (int)Reference2ShortLinkedOpenHashMap.this.link[this.curr];
/* 1292 */       }  Reference2ShortLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1297 */       if (this.prev == -1) {
/* 1298 */         Reference2ShortLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1300 */         Reference2ShortLinkedOpenHashMap.this.link[this.prev] = Reference2ShortLinkedOpenHashMap.this.link[this.prev] ^ (Reference2ShortLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1301 */       }  if (this.next == -1) {
/* 1302 */         Reference2ShortLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1304 */         Reference2ShortLinkedOpenHashMap.this.link[this.next] = Reference2ShortLinkedOpenHashMap.this.link[this.next] ^ (Reference2ShortLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1305 */       }  int pos = this.curr;
/* 1306 */       this.curr = -1;
/* 1307 */       if (pos == Reference2ShortLinkedOpenHashMap.this.n) {
/* 1308 */         Reference2ShortLinkedOpenHashMap.this.containsNullKey = false;
/* 1309 */         Reference2ShortLinkedOpenHashMap.this.key[Reference2ShortLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1312 */         K[] key = Reference2ShortLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1316 */           pos = (last = pos) + 1 & Reference2ShortLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1318 */             if ((curr = key[pos]) == null) {
/* 1319 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1322 */             int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2ShortLinkedOpenHashMap.this.mask;
/* 1323 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1325 */             pos = pos + 1 & Reference2ShortLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1327 */           key[last] = curr;
/* 1328 */           Reference2ShortLinkedOpenHashMap.this.value[last] = Reference2ShortLinkedOpenHashMap.this.value[pos];
/* 1329 */           if (this.next == pos)
/* 1330 */             this.next = last; 
/* 1331 */           if (this.prev == pos)
/* 1332 */             this.prev = last; 
/* 1333 */           Reference2ShortLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1338 */       int i = n;
/* 1339 */       while (i-- != 0 && hasNext())
/* 1340 */         nextEntry(); 
/* 1341 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1344 */       int i = n;
/* 1345 */       while (i-- != 0 && hasPrevious())
/* 1346 */         previousEntry(); 
/* 1347 */       return n - i - 1;
/*      */     }
/*      */     public void set(Reference2ShortMap.Entry<K> ok) {
/* 1350 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Reference2ShortMap.Entry<K> ok) {
/* 1353 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Reference2ShortMap.Entry<K>> { private Reference2ShortLinkedOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1361 */       super(from);
/*      */     }
/*      */     
/*      */     public Reference2ShortLinkedOpenHashMap<K>.MapEntry next() {
/* 1365 */       return this.entry = new Reference2ShortLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Reference2ShortLinkedOpenHashMap<K>.MapEntry previous() {
/* 1369 */       return this.entry = new Reference2ShortLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1373 */       super.remove();
/* 1374 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1378 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Reference2ShortMap.Entry<K>> { final Reference2ShortLinkedOpenHashMap<K>.MapEntry entry = new Reference2ShortLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1382 */       super(from);
/*      */     }
/*      */     
/*      */     public Reference2ShortLinkedOpenHashMap<K>.MapEntry next() {
/* 1386 */       this.entry.index = nextEntry();
/* 1387 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Reference2ShortLinkedOpenHashMap<K>.MapEntry previous() {
/* 1391 */       this.entry.index = previousEntry();
/* 1392 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Reference2ShortMap.Entry<K>> implements Reference2ShortSortedMap.FastSortedEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Reference2ShortMap.Entry<K>> iterator() {
/* 1400 */       return new Reference2ShortLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Reference2ShortMap.Entry<K>> comparator() {
/* 1404 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Reference2ShortMap.Entry<K>> subSet(Reference2ShortMap.Entry<K> fromElement, Reference2ShortMap.Entry<K> toElement) {
/* 1409 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Reference2ShortMap.Entry<K>> headSet(Reference2ShortMap.Entry<K> toElement) {
/* 1413 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Reference2ShortMap.Entry<K>> tailSet(Reference2ShortMap.Entry<K> fromElement) {
/* 1417 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Reference2ShortMap.Entry<K> first() {
/* 1421 */       if (Reference2ShortLinkedOpenHashMap.this.size == 0)
/* 1422 */         throw new NoSuchElementException(); 
/* 1423 */       return new Reference2ShortLinkedOpenHashMap.MapEntry(Reference2ShortLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Reference2ShortMap.Entry<K> last() {
/* 1427 */       if (Reference2ShortLinkedOpenHashMap.this.size == 0)
/* 1428 */         throw new NoSuchElementException(); 
/* 1429 */       return new Reference2ShortLinkedOpenHashMap.MapEntry(Reference2ShortLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1434 */       if (!(o instanceof Map.Entry))
/* 1435 */         return false; 
/* 1436 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1437 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1438 */         return false; 
/* 1439 */       K k = (K)e.getKey();
/* 1440 */       short v = ((Short)e.getValue()).shortValue();
/* 1441 */       if (k == null) {
/* 1442 */         return (Reference2ShortLinkedOpenHashMap.this.containsNullKey && Reference2ShortLinkedOpenHashMap.this.value[Reference2ShortLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1444 */       K[] key = Reference2ShortLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1447 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ShortLinkedOpenHashMap.this.mask]) == null)
/*      */       {
/* 1449 */         return false; } 
/* 1450 */       if (k == curr) {
/* 1451 */         return (Reference2ShortLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1454 */         if ((curr = key[pos = pos + 1 & Reference2ShortLinkedOpenHashMap.this.mask]) == null)
/* 1455 */           return false; 
/* 1456 */         if (k == curr) {
/* 1457 */           return (Reference2ShortLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1463 */       if (!(o instanceof Map.Entry))
/* 1464 */         return false; 
/* 1465 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1466 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1467 */         return false; 
/* 1468 */       K k = (K)e.getKey();
/* 1469 */       short v = ((Short)e.getValue()).shortValue();
/* 1470 */       if (k == null) {
/* 1471 */         if (Reference2ShortLinkedOpenHashMap.this.containsNullKey && Reference2ShortLinkedOpenHashMap.this.value[Reference2ShortLinkedOpenHashMap.this.n] == v) {
/* 1472 */           Reference2ShortLinkedOpenHashMap.this.removeNullEntry();
/* 1473 */           return true;
/*      */         } 
/* 1475 */         return false;
/*      */       } 
/*      */       
/* 1478 */       K[] key = Reference2ShortLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1481 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ShortLinkedOpenHashMap.this.mask]) == null)
/*      */       {
/* 1483 */         return false; } 
/* 1484 */       if (curr == k) {
/* 1485 */         if (Reference2ShortLinkedOpenHashMap.this.value[pos] == v) {
/* 1486 */           Reference2ShortLinkedOpenHashMap.this.removeEntry(pos);
/* 1487 */           return true;
/*      */         } 
/* 1489 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1492 */         if ((curr = key[pos = pos + 1 & Reference2ShortLinkedOpenHashMap.this.mask]) == null)
/* 1493 */           return false; 
/* 1494 */         if (curr == k && 
/* 1495 */           Reference2ShortLinkedOpenHashMap.this.value[pos] == v) {
/* 1496 */           Reference2ShortLinkedOpenHashMap.this.removeEntry(pos);
/* 1497 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1504 */       return Reference2ShortLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1508 */       Reference2ShortLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Reference2ShortMap.Entry<K>> iterator(Reference2ShortMap.Entry<K> from) {
/* 1523 */       return new Reference2ShortLinkedOpenHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Reference2ShortMap.Entry<K>> fastIterator() {
/* 1534 */       return new Reference2ShortLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Reference2ShortMap.Entry<K>> fastIterator(Reference2ShortMap.Entry<K> from) {
/* 1549 */       return new Reference2ShortLinkedOpenHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2ShortMap.Entry<K>> consumer) {
/* 1554 */       for (int i = Reference2ShortLinkedOpenHashMap.this.size, next = Reference2ShortLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1555 */         int curr = next;
/* 1556 */         next = (int)Reference2ShortLinkedOpenHashMap.this.link[curr];
/* 1557 */         consumer.accept(new AbstractReference2ShortMap.BasicEntry<>(Reference2ShortLinkedOpenHashMap.this.key[curr], Reference2ShortLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2ShortMap.Entry<K>> consumer) {
/* 1563 */       AbstractReference2ShortMap.BasicEntry<K> entry = new AbstractReference2ShortMap.BasicEntry<>();
/* 1564 */       for (int i = Reference2ShortLinkedOpenHashMap.this.size, next = Reference2ShortLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1565 */         int curr = next;
/* 1566 */         next = (int)Reference2ShortLinkedOpenHashMap.this.link[curr];
/* 1567 */         entry.key = Reference2ShortLinkedOpenHashMap.this.key[curr];
/* 1568 */         entry.value = Reference2ShortLinkedOpenHashMap.this.value[curr];
/* 1569 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Reference2ShortSortedMap.FastSortedEntrySet<K> reference2ShortEntrySet() {
/* 1575 */     if (this.entries == null)
/* 1576 */       this.entries = new MapEntrySet(); 
/* 1577 */     return this.entries;
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
/* 1590 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1594 */       return Reference2ShortLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1601 */       return Reference2ShortLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1607 */       return new Reference2ShortLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1611 */       return new Reference2ShortLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1616 */       if (Reference2ShortLinkedOpenHashMap.this.containsNullKey)
/* 1617 */         consumer.accept(Reference2ShortLinkedOpenHashMap.this.key[Reference2ShortLinkedOpenHashMap.this.n]); 
/* 1618 */       for (int pos = Reference2ShortLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1619 */         K k = Reference2ShortLinkedOpenHashMap.this.key[pos];
/* 1620 */         if (k != null)
/* 1621 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1626 */       return Reference2ShortLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1630 */       return Reference2ShortLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1634 */       int oldSize = Reference2ShortLinkedOpenHashMap.this.size;
/* 1635 */       Reference2ShortLinkedOpenHashMap.this.removeShort(k);
/* 1636 */       return (Reference2ShortLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1640 */       Reference2ShortLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1644 */       if (Reference2ShortLinkedOpenHashMap.this.size == 0)
/* 1645 */         throw new NoSuchElementException(); 
/* 1646 */       return Reference2ShortLinkedOpenHashMap.this.key[Reference2ShortLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1650 */       if (Reference2ShortLinkedOpenHashMap.this.size == 0)
/* 1651 */         throw new NoSuchElementException(); 
/* 1652 */       return Reference2ShortLinkedOpenHashMap.this.key[Reference2ShortLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1656 */       return null;
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> tailSet(K from) {
/* 1660 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> headSet(K to) {
/* 1664 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> subSet(K from, K to) {
/* 1668 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ReferenceSortedSet<K> keySet() {
/* 1673 */     if (this.keys == null)
/* 1674 */       this.keys = new KeySet(); 
/* 1675 */     return this.keys;
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
/*      */     implements ShortListIterator
/*      */   {
/*      */     public short previousShort() {
/* 1689 */       return Reference2ShortLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public short nextShort() {
/* 1696 */       return Reference2ShortLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ShortCollection values() {
/* 1701 */     if (this.values == null)
/* 1702 */       this.values = (ShortCollection)new AbstractShortCollection()
/*      */         {
/*      */           public ShortIterator iterator() {
/* 1705 */             return (ShortIterator)new Reference2ShortLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1709 */             return Reference2ShortLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(short v) {
/* 1713 */             return Reference2ShortLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1717 */             Reference2ShortLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(IntConsumer consumer) {
/* 1722 */             if (Reference2ShortLinkedOpenHashMap.this.containsNullKey)
/* 1723 */               consumer.accept(Reference2ShortLinkedOpenHashMap.this.value[Reference2ShortLinkedOpenHashMap.this.n]); 
/* 1724 */             for (int pos = Reference2ShortLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1725 */               if (Reference2ShortLinkedOpenHashMap.this.key[pos] != null)
/* 1726 */                 consumer.accept(Reference2ShortLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1729 */     return this.values;
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
/* 1746 */     return trim(this.size);
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
/* 1770 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1771 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1772 */       return true; 
/*      */     try {
/* 1774 */       rehash(l);
/* 1775 */     } catch (OutOfMemoryError cantDoIt) {
/* 1776 */       return false;
/*      */     } 
/* 1778 */     return true;
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
/* 1794 */     K[] key = this.key;
/* 1795 */     short[] value = this.value;
/* 1796 */     int mask = newN - 1;
/* 1797 */     K[] newKey = (K[])new Object[newN + 1];
/* 1798 */     short[] newValue = new short[newN + 1];
/* 1799 */     int i = this.first, prev = -1, newPrev = -1;
/* 1800 */     long[] link = this.link;
/* 1801 */     long[] newLink = new long[newN + 1];
/* 1802 */     this.first = -1;
/* 1803 */     for (int j = this.size; j-- != 0; ) {
/* 1804 */       int pos; if (key[i] == null) {
/* 1805 */         pos = newN;
/*      */       } else {
/* 1807 */         pos = HashCommon.mix(System.identityHashCode(key[i])) & mask;
/* 1808 */         while (newKey[pos] != null)
/* 1809 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1811 */       newKey[pos] = key[i];
/* 1812 */       newValue[pos] = value[i];
/* 1813 */       if (prev != -1) {
/* 1814 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1815 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1816 */         newPrev = pos;
/*      */       } else {
/* 1818 */         newPrev = this.first = pos;
/*      */         
/* 1820 */         newLink[pos] = -1L;
/*      */       } 
/* 1822 */       int t = i;
/* 1823 */       i = (int)link[i];
/* 1824 */       prev = t;
/*      */     } 
/* 1826 */     this.link = newLink;
/* 1827 */     this.last = newPrev;
/* 1828 */     if (newPrev != -1)
/*      */     {
/* 1830 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1831 */     this.n = newN;
/* 1832 */     this.mask = mask;
/* 1833 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1834 */     this.key = newKey;
/* 1835 */     this.value = newValue;
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
/*      */   public Reference2ShortLinkedOpenHashMap<K> clone() {
/*      */     Reference2ShortLinkedOpenHashMap<K> c;
/*      */     try {
/* 1852 */       c = (Reference2ShortLinkedOpenHashMap<K>)super.clone();
/* 1853 */     } catch (CloneNotSupportedException cantHappen) {
/* 1854 */       throw new InternalError();
/*      */     } 
/* 1856 */     c.keys = null;
/* 1857 */     c.values = null;
/* 1858 */     c.entries = null;
/* 1859 */     c.containsNullKey = this.containsNullKey;
/* 1860 */     c.key = (K[])this.key.clone();
/* 1861 */     c.value = (short[])this.value.clone();
/* 1862 */     c.link = (long[])this.link.clone();
/* 1863 */     return c;
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
/* 1876 */     int h = 0;
/* 1877 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1878 */       while (this.key[i] == null)
/* 1879 */         i++; 
/* 1880 */       if (this != this.key[i])
/* 1881 */         t = System.identityHashCode(this.key[i]); 
/* 1882 */       t ^= this.value[i];
/* 1883 */       h += t;
/* 1884 */       i++;
/*      */     } 
/*      */     
/* 1887 */     if (this.containsNullKey)
/* 1888 */       h += this.value[this.n]; 
/* 1889 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1892 */     K[] key = this.key;
/* 1893 */     short[] value = this.value;
/* 1894 */     MapIterator i = new MapIterator();
/* 1895 */     s.defaultWriteObject();
/* 1896 */     for (int j = this.size; j-- != 0; ) {
/* 1897 */       int e = i.nextEntry();
/* 1898 */       s.writeObject(key[e]);
/* 1899 */       s.writeShort(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1904 */     s.defaultReadObject();
/* 1905 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1906 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1907 */     this.mask = this.n - 1;
/* 1908 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1909 */     short[] value = this.value = new short[this.n + 1];
/* 1910 */     long[] link = this.link = new long[this.n + 1];
/* 1911 */     int prev = -1;
/* 1912 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1915 */     for (int i = this.size; i-- != 0; ) {
/* 1916 */       int pos; K k = (K)s.readObject();
/* 1917 */       short v = s.readShort();
/* 1918 */       if (k == null) {
/* 1919 */         pos = this.n;
/* 1920 */         this.containsNullKey = true;
/*      */       } else {
/* 1922 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 1923 */         while (key[pos] != null)
/* 1924 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1926 */       key[pos] = k;
/* 1927 */       value[pos] = v;
/* 1928 */       if (this.first != -1) {
/* 1929 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1930 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1931 */         prev = pos; continue;
/*      */       } 
/* 1933 */       prev = this.first = pos;
/*      */       
/* 1935 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1938 */     this.last = prev;
/* 1939 */     if (prev != -1)
/*      */     {
/* 1941 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2ShortLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */