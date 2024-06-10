/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteListIterator;
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
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongFunction;
/*      */ import java.util.function.LongToIntFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Long2ByteLinkedOpenHashMap
/*      */   extends AbstractLong2ByteSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient byte[] value;
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
/*      */   protected transient Long2ByteSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient LongSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ByteCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ByteLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new long[this.n + 1];
/*  162 */     this.value = new byte[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ByteLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ByteLinkedOpenHashMap() {
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
/*      */   public Long2ByteLinkedOpenHashMap(Map<? extends Long, ? extends Byte> m, float f) {
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
/*      */   public Long2ByteLinkedOpenHashMap(Map<? extends Long, ? extends Byte> m) {
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
/*      */   public Long2ByteLinkedOpenHashMap(Long2ByteMap m, float f) {
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
/*      */   public Long2ByteLinkedOpenHashMap(Long2ByteMap m) {
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
/*      */   public Long2ByteLinkedOpenHashMap(long[] k, byte[] v, float f) {
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
/*      */   public Long2ByteLinkedOpenHashMap(long[] k, byte[] v) {
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
/*      */   private byte removeEntry(int pos) {
/*  275 */     byte oldValue = this.value[pos];
/*  276 */     this.size--;
/*  277 */     fixPointers(pos);
/*  278 */     shiftKeys(pos);
/*  279 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  280 */       rehash(this.n / 2); 
/*  281 */     return oldValue;
/*      */   }
/*      */   private byte removeNullEntry() {
/*  284 */     this.containsNullKey = false;
/*  285 */     byte oldValue = this.value[this.n];
/*  286 */     this.size--;
/*  287 */     fixPointers(this.n);
/*  288 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  289 */       rehash(this.n / 2); 
/*  290 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Long, ? extends Byte> m) {
/*  294 */     if (this.f <= 0.5D) {
/*  295 */       ensureCapacity(m.size());
/*      */     } else {
/*  297 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  299 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(long k) {
/*  303 */     if (k == 0L) {
/*  304 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  306 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  309 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  310 */       return -(pos + 1); 
/*  311 */     if (k == curr) {
/*  312 */       return pos;
/*      */     }
/*      */     while (true) {
/*  315 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  316 */         return -(pos + 1); 
/*  317 */       if (k == curr)
/*  318 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, long k, byte v) {
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
/*      */   public byte put(long k, byte v) {
/*  342 */     int pos = find(k);
/*  343 */     if (pos < 0) {
/*  344 */       insert(-pos - 1, k, v);
/*  345 */       return this.defRetValue;
/*      */     } 
/*  347 */     byte oldValue = this.value[pos];
/*  348 */     this.value[pos] = v;
/*  349 */     return oldValue;
/*      */   }
/*      */   private byte addToValue(int pos, byte incr) {
/*  352 */     byte oldValue = this.value[pos];
/*  353 */     this.value[pos] = (byte)(oldValue + incr);
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
/*      */   public byte addTo(long k, byte incr) {
/*      */     int pos;
/*  374 */     if (k == 0L) {
/*  375 */       if (this.containsNullKey)
/*  376 */         return addToValue(this.n, incr); 
/*  377 */       pos = this.n;
/*  378 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  381 */       long[] key = this.key;
/*      */       long curr;
/*  383 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  384 */         if (curr == k)
/*  385 */           return addToValue(pos, incr); 
/*  386 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  387 */           if (curr == k)
/*  388 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  391 */     }  this.key[pos] = k;
/*  392 */     this.value[pos] = (byte)(this.defRetValue + incr);
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
/*  419 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  421 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  423 */         if ((curr = key[pos]) == 0L) {
/*  424 */           key[last] = 0L;
/*      */           return;
/*      */         } 
/*  427 */         int slot = (int)HashCommon.mix(curr) & this.mask;
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
/*      */   public byte remove(long k) {
/*  440 */     if (k == 0L) {
/*  441 */       if (this.containsNullKey)
/*  442 */         return removeNullEntry(); 
/*  443 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  446 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  449 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  450 */       return this.defRetValue; 
/*  451 */     if (k == curr)
/*  452 */       return removeEntry(pos); 
/*      */     while (true) {
/*  454 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  455 */         return this.defRetValue; 
/*  456 */       if (k == curr)
/*  457 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private byte setValue(int pos, byte v) {
/*  461 */     byte oldValue = this.value[pos];
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
/*      */   public byte removeFirstByte() {
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
/*  484 */     byte v = this.value[pos];
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
/*      */   public byte removeLastByte() {
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
/*  511 */     byte v = this.value[pos];
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
/*      */   public byte getAndMoveToFirst(long k) {
/*  566 */     if (k == 0L) {
/*  567 */       if (this.containsNullKey) {
/*  568 */         moveIndexToFirst(this.n);
/*  569 */         return this.value[this.n];
/*      */       } 
/*  571 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  574 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  577 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  578 */       return this.defRetValue; 
/*  579 */     if (k == curr) {
/*  580 */       moveIndexToFirst(pos);
/*  581 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  585 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
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
/*      */   public byte getAndMoveToLast(long k) {
/*  603 */     if (k == 0L) {
/*  604 */       if (this.containsNullKey) {
/*  605 */         moveIndexToLast(this.n);
/*  606 */         return this.value[this.n];
/*      */       } 
/*  608 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  611 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  614 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  615 */       return this.defRetValue; 
/*  616 */     if (k == curr) {
/*  617 */       moveIndexToLast(pos);
/*  618 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  622 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
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
/*      */   public byte putAndMoveToFirst(long k, byte v) {
/*      */     int pos;
/*  643 */     if (k == 0L) {
/*  644 */       if (this.containsNullKey) {
/*  645 */         moveIndexToFirst(this.n);
/*  646 */         return setValue(this.n, v);
/*      */       } 
/*  648 */       this.containsNullKey = true;
/*  649 */       pos = this.n;
/*      */     } else {
/*      */       
/*  652 */       long[] key = this.key;
/*      */       long curr;
/*  654 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  655 */         if (curr == k) {
/*  656 */           moveIndexToFirst(pos);
/*  657 */           return setValue(pos, v);
/*      */         } 
/*  659 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
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
/*      */   public byte putAndMoveToLast(long k, byte v) {
/*      */     int pos;
/*  696 */     if (k == 0L) {
/*  697 */       if (this.containsNullKey) {
/*  698 */         moveIndexToLast(this.n);
/*  699 */         return setValue(this.n, v);
/*      */       } 
/*  701 */       this.containsNullKey = true;
/*  702 */       pos = this.n;
/*      */     } else {
/*      */       
/*  705 */       long[] key = this.key;
/*      */       long curr;
/*  707 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  708 */         if (curr == k) {
/*  709 */           moveIndexToLast(pos);
/*  710 */           return setValue(pos, v);
/*      */         } 
/*  712 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
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
/*      */   public byte get(long k) {
/*  739 */     if (k == 0L) {
/*  740 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  742 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  745 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  746 */       return this.defRetValue; 
/*  747 */     if (k == curr) {
/*  748 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  751 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  752 */         return this.defRetValue; 
/*  753 */       if (k == curr) {
/*  754 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(long k) {
/*  760 */     if (k == 0L) {
/*  761 */       return this.containsNullKey;
/*      */     }
/*  763 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  766 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  767 */       return false; 
/*  768 */     if (k == curr) {
/*  769 */       return true;
/*      */     }
/*      */     while (true) {
/*  772 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  773 */         return false; 
/*  774 */       if (k == curr)
/*  775 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(byte v) {
/*  780 */     byte[] value = this.value;
/*  781 */     long[] key = this.key;
/*  782 */     if (this.containsNullKey && value[this.n] == v)
/*  783 */       return true; 
/*  784 */     for (int i = this.n; i-- != 0;) {
/*  785 */       if (key[i] != 0L && value[i] == v)
/*  786 */         return true; 
/*  787 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getOrDefault(long k, byte defaultValue) {
/*  793 */     if (k == 0L) {
/*  794 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  796 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  799 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  800 */       return defaultValue; 
/*  801 */     if (k == curr) {
/*  802 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  805 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  806 */         return defaultValue; 
/*  807 */       if (k == curr) {
/*  808 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public byte putIfAbsent(long k, byte v) {
/*  814 */     int pos = find(k);
/*  815 */     if (pos >= 0)
/*  816 */       return this.value[pos]; 
/*  817 */     insert(-pos - 1, k, v);
/*  818 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, byte v) {
/*  824 */     if (k == 0L) {
/*  825 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  826 */         removeNullEntry();
/*  827 */         return true;
/*      */       } 
/*  829 */       return false;
/*      */     } 
/*      */     
/*  832 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  835 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  836 */       return false; 
/*  837 */     if (k == curr && v == this.value[pos]) {
/*  838 */       removeEntry(pos);
/*  839 */       return true;
/*      */     } 
/*      */     while (true) {
/*  842 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  843 */         return false; 
/*  844 */       if (k == curr && v == this.value[pos]) {
/*  845 */         removeEntry(pos);
/*  846 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(long k, byte oldValue, byte v) {
/*  853 */     int pos = find(k);
/*  854 */     if (pos < 0 || oldValue != this.value[pos])
/*  855 */       return false; 
/*  856 */     this.value[pos] = v;
/*  857 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte replace(long k, byte v) {
/*  862 */     int pos = find(k);
/*  863 */     if (pos < 0)
/*  864 */       return this.defRetValue; 
/*  865 */     byte oldValue = this.value[pos];
/*  866 */     this.value[pos] = v;
/*  867 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte computeIfAbsent(long k, LongToIntFunction mappingFunction) {
/*  872 */     Objects.requireNonNull(mappingFunction);
/*  873 */     int pos = find(k);
/*  874 */     if (pos >= 0)
/*  875 */       return this.value[pos]; 
/*  876 */     byte newValue = SafeMath.safeIntToByte(mappingFunction.applyAsInt(k));
/*  877 */     insert(-pos - 1, k, newValue);
/*  878 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsentNullable(long k, LongFunction<? extends Byte> mappingFunction) {
/*  884 */     Objects.requireNonNull(mappingFunction);
/*  885 */     int pos = find(k);
/*  886 */     if (pos >= 0)
/*  887 */       return this.value[pos]; 
/*  888 */     Byte newValue = mappingFunction.apply(k);
/*  889 */     if (newValue == null)
/*  890 */       return this.defRetValue; 
/*  891 */     byte v = newValue.byteValue();
/*  892 */     insert(-pos - 1, k, v);
/*  893 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfPresent(long k, BiFunction<? super Long, ? super Byte, ? extends Byte> remappingFunction) {
/*  899 */     Objects.requireNonNull(remappingFunction);
/*  900 */     int pos = find(k);
/*  901 */     if (pos < 0)
/*  902 */       return this.defRetValue; 
/*  903 */     Byte newValue = remappingFunction.apply(Long.valueOf(k), Byte.valueOf(this.value[pos]));
/*  904 */     if (newValue == null) {
/*  905 */       if (k == 0L) {
/*  906 */         removeNullEntry();
/*      */       } else {
/*  908 */         removeEntry(pos);
/*  909 */       }  return this.defRetValue;
/*      */     } 
/*  911 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte compute(long k, BiFunction<? super Long, ? super Byte, ? extends Byte> remappingFunction) {
/*  917 */     Objects.requireNonNull(remappingFunction);
/*  918 */     int pos = find(k);
/*  919 */     Byte newValue = remappingFunction.apply(Long.valueOf(k), (pos >= 0) ? Byte.valueOf(this.value[pos]) : null);
/*  920 */     if (newValue == null) {
/*  921 */       if (pos >= 0)
/*  922 */         if (k == 0L) {
/*  923 */           removeNullEntry();
/*      */         } else {
/*  925 */           removeEntry(pos);
/*      */         }  
/*  927 */       return this.defRetValue;
/*      */     } 
/*  929 */     byte newVal = newValue.byteValue();
/*  930 */     if (pos < 0) {
/*  931 */       insert(-pos - 1, k, newVal);
/*  932 */       return newVal;
/*      */     } 
/*  934 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte merge(long k, byte v, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*  940 */     Objects.requireNonNull(remappingFunction);
/*  941 */     int pos = find(k);
/*  942 */     if (pos < 0) {
/*  943 */       insert(-pos - 1, k, v);
/*  944 */       return v;
/*      */     } 
/*  946 */     Byte newValue = remappingFunction.apply(Byte.valueOf(this.value[pos]), Byte.valueOf(v));
/*  947 */     if (newValue == null) {
/*  948 */       if (k == 0L) {
/*  949 */         removeNullEntry();
/*      */       } else {
/*  951 */         removeEntry(pos);
/*  952 */       }  return this.defRetValue;
/*      */     } 
/*  954 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
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
/*  965 */     if (this.size == 0)
/*      */       return; 
/*  967 */     this.size = 0;
/*  968 */     this.containsNullKey = false;
/*  969 */     Arrays.fill(this.key, 0L);
/*  970 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  974 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  978 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Long2ByteMap.Entry, Map.Entry<Long, Byte>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  990 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public long getLongKey() {
/*  996 */       return Long2ByteLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public byte getByteValue() {
/* 1000 */       return Long2ByteLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public byte setValue(byte v) {
/* 1004 */       byte oldValue = Long2ByteLinkedOpenHashMap.this.value[this.index];
/* 1005 */       Long2ByteLinkedOpenHashMap.this.value[this.index] = v;
/* 1006 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getKey() {
/* 1016 */       return Long.valueOf(Long2ByteLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte getValue() {
/* 1026 */       return Byte.valueOf(Long2ByteLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte setValue(Byte v) {
/* 1036 */       return Byte.valueOf(setValue(v.byteValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1041 */       if (!(o instanceof Map.Entry))
/* 1042 */         return false; 
/* 1043 */       Map.Entry<Long, Byte> e = (Map.Entry<Long, Byte>)o;
/* 1044 */       return (Long2ByteLinkedOpenHashMap.this.key[this.index] == ((Long)e.getKey()).longValue() && Long2ByteLinkedOpenHashMap.this.value[this.index] == ((Byte)e.getValue()).byteValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1048 */       return HashCommon.long2int(Long2ByteLinkedOpenHashMap.this.key[this.index]) ^ Long2ByteLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1052 */       return Long2ByteLinkedOpenHashMap.this.key[this.index] + "=>" + Long2ByteLinkedOpenHashMap.this.value[this.index];
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
/* 1063 */     if (this.size == 0) {
/* 1064 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1067 */     if (this.first == i) {
/* 1068 */       this.first = (int)this.link[i];
/* 1069 */       if (0 <= this.first)
/*      */       {
/* 1071 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1075 */     if (this.last == i) {
/* 1076 */       this.last = (int)(this.link[i] >>> 32L);
/* 1077 */       if (0 <= this.last)
/*      */       {
/* 1079 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1083 */     long linki = this.link[i];
/* 1084 */     int prev = (int)(linki >>> 32L);
/* 1085 */     int next = (int)linki;
/* 1086 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1087 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1100 */     if (this.size == 1) {
/* 1101 */       this.first = this.last = d;
/*      */       
/* 1103 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1106 */     if (this.first == s) {
/* 1107 */       this.first = d;
/* 1108 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1109 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1112 */     if (this.last == s) {
/* 1113 */       this.last = d;
/* 1114 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1115 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1118 */     long links = this.link[s];
/* 1119 */     int prev = (int)(links >>> 32L);
/* 1120 */     int next = (int)links;
/* 1121 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1122 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1123 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long firstLongKey() {
/* 1132 */     if (this.size == 0)
/* 1133 */       throw new NoSuchElementException(); 
/* 1134 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long lastLongKey() {
/* 1143 */     if (this.size == 0)
/* 1144 */       throw new NoSuchElementException(); 
/* 1145 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ByteSortedMap tailMap(long from) {
/* 1154 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ByteSortedMap headMap(long to) {
/* 1163 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ByteSortedMap subMap(long from, long to) {
/* 1172 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongComparator comparator() {
/* 1181 */     return null;
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
/* 1196 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1202 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1207 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1213 */     int index = -1;
/*      */     protected MapIterator() {
/* 1215 */       this.next = Long2ByteLinkedOpenHashMap.this.first;
/* 1216 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(long from) {
/* 1219 */       if (from == 0L) {
/* 1220 */         if (Long2ByteLinkedOpenHashMap.this.containsNullKey) {
/* 1221 */           this.next = (int)Long2ByteLinkedOpenHashMap.this.link[Long2ByteLinkedOpenHashMap.this.n];
/* 1222 */           this.prev = Long2ByteLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1225 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1227 */       if (Long2ByteLinkedOpenHashMap.this.key[Long2ByteLinkedOpenHashMap.this.last] == from) {
/* 1228 */         this.prev = Long2ByteLinkedOpenHashMap.this.last;
/* 1229 */         this.index = Long2ByteLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1233 */       int pos = (int)HashCommon.mix(from) & Long2ByteLinkedOpenHashMap.this.mask;
/*      */       
/* 1235 */       while (Long2ByteLinkedOpenHashMap.this.key[pos] != 0L) {
/* 1236 */         if (Long2ByteLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1238 */           this.next = (int)Long2ByteLinkedOpenHashMap.this.link[pos];
/* 1239 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1242 */         pos = pos + 1 & Long2ByteLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1244 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1247 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1250 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1253 */       if (this.index >= 0)
/*      */         return; 
/* 1255 */       if (this.prev == -1) {
/* 1256 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1259 */       if (this.next == -1) {
/* 1260 */         this.index = Long2ByteLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1263 */       int pos = Long2ByteLinkedOpenHashMap.this.first;
/* 1264 */       this.index = 1;
/* 1265 */       while (pos != this.prev) {
/* 1266 */         pos = (int)Long2ByteLinkedOpenHashMap.this.link[pos];
/* 1267 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1271 */       ensureIndexKnown();
/* 1272 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1275 */       ensureIndexKnown();
/* 1276 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1279 */       if (!hasNext())
/* 1280 */         throw new NoSuchElementException(); 
/* 1281 */       this.curr = this.next;
/* 1282 */       this.next = (int)Long2ByteLinkedOpenHashMap.this.link[this.curr];
/* 1283 */       this.prev = this.curr;
/* 1284 */       if (this.index >= 0)
/* 1285 */         this.index++; 
/* 1286 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1289 */       if (!hasPrevious())
/* 1290 */         throw new NoSuchElementException(); 
/* 1291 */       this.curr = this.prev;
/* 1292 */       this.prev = (int)(Long2ByteLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1293 */       this.next = this.curr;
/* 1294 */       if (this.index >= 0)
/* 1295 */         this.index--; 
/* 1296 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1299 */       ensureIndexKnown();
/* 1300 */       if (this.curr == -1)
/* 1301 */         throw new IllegalStateException(); 
/* 1302 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1307 */         this.index--;
/* 1308 */         this.prev = (int)(Long2ByteLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1310 */         this.next = (int)Long2ByteLinkedOpenHashMap.this.link[this.curr];
/* 1311 */       }  Long2ByteLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1316 */       if (this.prev == -1) {
/* 1317 */         Long2ByteLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1319 */         Long2ByteLinkedOpenHashMap.this.link[this.prev] = Long2ByteLinkedOpenHashMap.this.link[this.prev] ^ (Long2ByteLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1320 */       }  if (this.next == -1) {
/* 1321 */         Long2ByteLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1323 */         Long2ByteLinkedOpenHashMap.this.link[this.next] = Long2ByteLinkedOpenHashMap.this.link[this.next] ^ (Long2ByteLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1324 */       }  int pos = this.curr;
/* 1325 */       this.curr = -1;
/* 1326 */       if (pos == Long2ByteLinkedOpenHashMap.this.n) {
/* 1327 */         Long2ByteLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1330 */         long[] key = Long2ByteLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           long curr;
/*      */           int last;
/* 1334 */           pos = (last = pos) + 1 & Long2ByteLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1336 */             if ((curr = key[pos]) == 0L) {
/* 1337 */               key[last] = 0L;
/*      */               return;
/*      */             } 
/* 1340 */             int slot = (int)HashCommon.mix(curr) & Long2ByteLinkedOpenHashMap.this.mask;
/* 1341 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1343 */             pos = pos + 1 & Long2ByteLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1345 */           key[last] = curr;
/* 1346 */           Long2ByteLinkedOpenHashMap.this.value[last] = Long2ByteLinkedOpenHashMap.this.value[pos];
/* 1347 */           if (this.next == pos)
/* 1348 */             this.next = last; 
/* 1349 */           if (this.prev == pos)
/* 1350 */             this.prev = last; 
/* 1351 */           Long2ByteLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1356 */       int i = n;
/* 1357 */       while (i-- != 0 && hasNext())
/* 1358 */         nextEntry(); 
/* 1359 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1362 */       int i = n;
/* 1363 */       while (i-- != 0 && hasPrevious())
/* 1364 */         previousEntry(); 
/* 1365 */       return n - i - 1;
/*      */     }
/*      */     public void set(Long2ByteMap.Entry ok) {
/* 1368 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Long2ByteMap.Entry ok) {
/* 1371 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Long2ByteMap.Entry> { private Long2ByteLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(long from) {
/* 1379 */       super(from);
/*      */     }
/*      */     
/*      */     public Long2ByteLinkedOpenHashMap.MapEntry next() {
/* 1383 */       return this.entry = new Long2ByteLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Long2ByteLinkedOpenHashMap.MapEntry previous() {
/* 1387 */       return this.entry = new Long2ByteLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1391 */       super.remove();
/* 1392 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1396 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Long2ByteMap.Entry> { final Long2ByteLinkedOpenHashMap.MapEntry entry = new Long2ByteLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(long from) {
/* 1400 */       super(from);
/*      */     }
/*      */     
/*      */     public Long2ByteLinkedOpenHashMap.MapEntry next() {
/* 1404 */       this.entry.index = nextEntry();
/* 1405 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Long2ByteLinkedOpenHashMap.MapEntry previous() {
/* 1409 */       this.entry.index = previousEntry();
/* 1410 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Long2ByteMap.Entry> implements Long2ByteSortedMap.FastSortedEntrySet { public ObjectBidirectionalIterator<Long2ByteMap.Entry> iterator() {
/* 1416 */       return (ObjectBidirectionalIterator<Long2ByteMap.Entry>)new Long2ByteLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     private MapEntrySet() {}
/*      */     public Comparator<? super Long2ByteMap.Entry> comparator() {
/* 1420 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Long2ByteMap.Entry> subSet(Long2ByteMap.Entry fromElement, Long2ByteMap.Entry toElement) {
/* 1425 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Long2ByteMap.Entry> headSet(Long2ByteMap.Entry toElement) {
/* 1429 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Long2ByteMap.Entry> tailSet(Long2ByteMap.Entry fromElement) {
/* 1433 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Long2ByteMap.Entry first() {
/* 1437 */       if (Long2ByteLinkedOpenHashMap.this.size == 0)
/* 1438 */         throw new NoSuchElementException(); 
/* 1439 */       return new Long2ByteLinkedOpenHashMap.MapEntry(Long2ByteLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Long2ByteMap.Entry last() {
/* 1443 */       if (Long2ByteLinkedOpenHashMap.this.size == 0)
/* 1444 */         throw new NoSuchElementException(); 
/* 1445 */       return new Long2ByteLinkedOpenHashMap.MapEntry(Long2ByteLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1450 */       if (!(o instanceof Map.Entry))
/* 1451 */         return false; 
/* 1452 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1453 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 1454 */         return false; 
/* 1455 */       if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1456 */         return false; 
/* 1457 */       long k = ((Long)e.getKey()).longValue();
/* 1458 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1459 */       if (k == 0L) {
/* 1460 */         return (Long2ByteLinkedOpenHashMap.this.containsNullKey && Long2ByteLinkedOpenHashMap.this.value[Long2ByteLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1462 */       long[] key = Long2ByteLinkedOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1465 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2ByteLinkedOpenHashMap.this.mask]) == 0L)
/* 1466 */         return false; 
/* 1467 */       if (k == curr) {
/* 1468 */         return (Long2ByteLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1471 */         if ((curr = key[pos = pos + 1 & Long2ByteLinkedOpenHashMap.this.mask]) == 0L)
/* 1472 */           return false; 
/* 1473 */         if (k == curr) {
/* 1474 */           return (Long2ByteLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1480 */       if (!(o instanceof Map.Entry))
/* 1481 */         return false; 
/* 1482 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1483 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 1484 */         return false; 
/* 1485 */       if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1486 */         return false; 
/* 1487 */       long k = ((Long)e.getKey()).longValue();
/* 1488 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1489 */       if (k == 0L) {
/* 1490 */         if (Long2ByteLinkedOpenHashMap.this.containsNullKey && Long2ByteLinkedOpenHashMap.this.value[Long2ByteLinkedOpenHashMap.this.n] == v) {
/* 1491 */           Long2ByteLinkedOpenHashMap.this.removeNullEntry();
/* 1492 */           return true;
/*      */         } 
/* 1494 */         return false;
/*      */       } 
/*      */       
/* 1497 */       long[] key = Long2ByteLinkedOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1500 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2ByteLinkedOpenHashMap.this.mask]) == 0L)
/* 1501 */         return false; 
/* 1502 */       if (curr == k) {
/* 1503 */         if (Long2ByteLinkedOpenHashMap.this.value[pos] == v) {
/* 1504 */           Long2ByteLinkedOpenHashMap.this.removeEntry(pos);
/* 1505 */           return true;
/*      */         } 
/* 1507 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1510 */         if ((curr = key[pos = pos + 1 & Long2ByteLinkedOpenHashMap.this.mask]) == 0L)
/* 1511 */           return false; 
/* 1512 */         if (curr == k && 
/* 1513 */           Long2ByteLinkedOpenHashMap.this.value[pos] == v) {
/* 1514 */           Long2ByteLinkedOpenHashMap.this.removeEntry(pos);
/* 1515 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1522 */       return Long2ByteLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1526 */       Long2ByteLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Long2ByteMap.Entry> iterator(Long2ByteMap.Entry from) {
/* 1541 */       return new Long2ByteLinkedOpenHashMap.EntryIterator(from.getLongKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Long2ByteMap.Entry> fastIterator() {
/* 1552 */       return new Long2ByteLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Long2ByteMap.Entry> fastIterator(Long2ByteMap.Entry from) {
/* 1567 */       return new Long2ByteLinkedOpenHashMap.FastEntryIterator(from.getLongKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2ByteMap.Entry> consumer) {
/* 1572 */       for (int i = Long2ByteLinkedOpenHashMap.this.size, next = Long2ByteLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1573 */         int curr = next;
/* 1574 */         next = (int)Long2ByteLinkedOpenHashMap.this.link[curr];
/* 1575 */         consumer.accept(new AbstractLong2ByteMap.BasicEntry(Long2ByteLinkedOpenHashMap.this.key[curr], Long2ByteLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2ByteMap.Entry> consumer) {
/* 1581 */       AbstractLong2ByteMap.BasicEntry entry = new AbstractLong2ByteMap.BasicEntry();
/* 1582 */       for (int i = Long2ByteLinkedOpenHashMap.this.size, next = Long2ByteLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1583 */         int curr = next;
/* 1584 */         next = (int)Long2ByteLinkedOpenHashMap.this.link[curr];
/* 1585 */         entry.key = Long2ByteLinkedOpenHashMap.this.key[curr];
/* 1586 */         entry.value = Long2ByteLinkedOpenHashMap.this.value[curr];
/* 1587 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Long2ByteSortedMap.FastSortedEntrySet long2ByteEntrySet() {
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
/*      */     implements LongListIterator
/*      */   {
/*      */     public KeyIterator(long k) {
/* 1608 */       super(k);
/*      */     }
/*      */     
/*      */     public long previousLong() {
/* 1612 */       return Long2ByteLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public long nextLong() {
/* 1619 */       return Long2ByteLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSortedSet { private KeySet() {}
/*      */     
/*      */     public LongListIterator iterator(long from) {
/* 1625 */       return new Long2ByteLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public LongListIterator iterator() {
/* 1629 */       return new Long2ByteLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/* 1634 */       if (Long2ByteLinkedOpenHashMap.this.containsNullKey)
/* 1635 */         consumer.accept(Long2ByteLinkedOpenHashMap.this.key[Long2ByteLinkedOpenHashMap.this.n]); 
/* 1636 */       for (int pos = Long2ByteLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1637 */         long k = Long2ByteLinkedOpenHashMap.this.key[pos];
/* 1638 */         if (k != 0L)
/* 1639 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1644 */       return Long2ByteLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(long k) {
/* 1648 */       return Long2ByteLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(long k) {
/* 1652 */       int oldSize = Long2ByteLinkedOpenHashMap.this.size;
/* 1653 */       Long2ByteLinkedOpenHashMap.this.remove(k);
/* 1654 */       return (Long2ByteLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1658 */       Long2ByteLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public long firstLong() {
/* 1662 */       if (Long2ByteLinkedOpenHashMap.this.size == 0)
/* 1663 */         throw new NoSuchElementException(); 
/* 1664 */       return Long2ByteLinkedOpenHashMap.this.key[Long2ByteLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public long lastLong() {
/* 1668 */       if (Long2ByteLinkedOpenHashMap.this.size == 0)
/* 1669 */         throw new NoSuchElementException(); 
/* 1670 */       return Long2ByteLinkedOpenHashMap.this.key[Long2ByteLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public LongComparator comparator() {
/* 1674 */       return null;
/*      */     }
/*      */     
/*      */     public LongSortedSet tailSet(long from) {
/* 1678 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public LongSortedSet headSet(long to) {
/* 1682 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public LongSortedSet subSet(long from, long to) {
/* 1686 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public LongSortedSet keySet() {
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
/*      */     implements ByteListIterator
/*      */   {
/*      */     public byte previousByte() {
/* 1707 */       return Long2ByteLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1714 */       return Long2ByteLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ByteCollection values() {
/* 1719 */     if (this.values == null)
/* 1720 */       this.values = (ByteCollection)new AbstractByteCollection()
/*      */         {
/*      */           public ByteIterator iterator() {
/* 1723 */             return (ByteIterator)new Long2ByteLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1727 */             return Long2ByteLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(byte v) {
/* 1731 */             return Long2ByteLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1735 */             Long2ByteLinkedOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1740 */             if (Long2ByteLinkedOpenHashMap.this.containsNullKey)
/* 1741 */               consumer.accept(Long2ByteLinkedOpenHashMap.this.value[Long2ByteLinkedOpenHashMap.this.n]); 
/* 1742 */             for (int pos = Long2ByteLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1743 */               if (Long2ByteLinkedOpenHashMap.this.key[pos] != 0L)
/* 1744 */                 consumer.accept(Long2ByteLinkedOpenHashMap.this.value[pos]); 
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
/* 1812 */     long[] key = this.key;
/* 1813 */     byte[] value = this.value;
/* 1814 */     int mask = newN - 1;
/* 1815 */     long[] newKey = new long[newN + 1];
/* 1816 */     byte[] newValue = new byte[newN + 1];
/* 1817 */     int i = this.first, prev = -1, newPrev = -1;
/* 1818 */     long[] link = this.link;
/* 1819 */     long[] newLink = new long[newN + 1];
/* 1820 */     this.first = -1;
/* 1821 */     for (int j = this.size; j-- != 0; ) {
/* 1822 */       int pos; if (key[i] == 0L) {
/* 1823 */         pos = newN;
/*      */       } else {
/* 1825 */         pos = (int)HashCommon.mix(key[i]) & mask;
/* 1826 */         while (newKey[pos] != 0L)
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
/*      */   public Long2ByteLinkedOpenHashMap clone() {
/*      */     Long2ByteLinkedOpenHashMap c;
/*      */     try {
/* 1870 */       c = (Long2ByteLinkedOpenHashMap)super.clone();
/* 1871 */     } catch (CloneNotSupportedException cantHappen) {
/* 1872 */       throw new InternalError();
/*      */     } 
/* 1874 */     c.keys = null;
/* 1875 */     c.values = null;
/* 1876 */     c.entries = null;
/* 1877 */     c.containsNullKey = this.containsNullKey;
/* 1878 */     c.key = (long[])this.key.clone();
/* 1879 */     c.value = (byte[])this.value.clone();
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
/* 1896 */       while (this.key[i] == 0L)
/* 1897 */         i++; 
/* 1898 */       t = HashCommon.long2int(this.key[i]);
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
/* 1909 */     long[] key = this.key;
/* 1910 */     byte[] value = this.value;
/* 1911 */     MapIterator i = new MapIterator();
/* 1912 */     s.defaultWriteObject();
/* 1913 */     for (int j = this.size; j-- != 0; ) {
/* 1914 */       int e = i.nextEntry();
/* 1915 */       s.writeLong(key[e]);
/* 1916 */       s.writeByte(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1921 */     s.defaultReadObject();
/* 1922 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1923 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1924 */     this.mask = this.n - 1;
/* 1925 */     long[] key = this.key = new long[this.n + 1];
/* 1926 */     byte[] value = this.value = new byte[this.n + 1];
/* 1927 */     long[] link = this.link = new long[this.n + 1];
/* 1928 */     int prev = -1;
/* 1929 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1932 */     for (int i = this.size; i-- != 0; ) {
/* 1933 */       int pos; long k = s.readLong();
/* 1934 */       byte v = s.readByte();
/* 1935 */       if (k == 0L) {
/* 1936 */         pos = this.n;
/* 1937 */         this.containsNullKey = true;
/*      */       } else {
/* 1939 */         pos = (int)HashCommon.mix(k) & this.mask;
/* 1940 */         while (key[pos] != 0L)
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2ByteLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */