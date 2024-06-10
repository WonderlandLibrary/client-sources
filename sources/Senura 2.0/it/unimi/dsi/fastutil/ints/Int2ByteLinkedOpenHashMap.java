/*      */ package it.unimi.dsi.fastutil.ints;
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
/*      */ public class Int2ByteLinkedOpenHashMap
/*      */   extends AbstractInt2ByteSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient int[] key;
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
/*      */   protected transient Int2ByteSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient IntSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ByteCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ByteLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new int[this.n + 1];
/*  162 */     this.value = new byte[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ByteLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ByteLinkedOpenHashMap() {
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
/*      */   public Int2ByteLinkedOpenHashMap(Map<? extends Integer, ? extends Byte> m, float f) {
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
/*      */   public Int2ByteLinkedOpenHashMap(Map<? extends Integer, ? extends Byte> m) {
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
/*      */   public Int2ByteLinkedOpenHashMap(Int2ByteMap m, float f) {
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
/*      */   public Int2ByteLinkedOpenHashMap(Int2ByteMap m) {
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
/*      */   public Int2ByteLinkedOpenHashMap(int[] k, byte[] v, float f) {
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
/*      */   public Int2ByteLinkedOpenHashMap(int[] k, byte[] v) {
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
/*      */   public void putAll(Map<? extends Integer, ? extends Byte> m) {
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
/*      */   private void insert(int pos, int k, byte v) {
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
/*      */   public byte put(int k, byte v) {
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
/*      */   public byte addTo(int k, byte incr) {
/*      */     int pos;
/*  374 */     if (k == 0) {
/*  375 */       if (this.containsNullKey)
/*  376 */         return addToValue(this.n, incr); 
/*  377 */       pos = this.n;
/*  378 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  381 */       int[] key = this.key;
/*      */       int curr;
/*  383 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  384 */         if (curr == k)
/*  385 */           return addToValue(pos, incr); 
/*  386 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
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
/*  419 */     int[] key = this.key; while (true) {
/*      */       int curr, last;
/*  421 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  423 */         if ((curr = key[pos]) == 0) {
/*  424 */           key[last] = 0;
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
/*      */   public byte remove(int k) {
/*  440 */     if (k == 0) {
/*  441 */       if (this.containsNullKey)
/*  442 */         return removeNullEntry(); 
/*  443 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  446 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  449 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  450 */       return this.defRetValue; 
/*  451 */     if (k == curr)
/*  452 */       return removeEntry(pos); 
/*      */     while (true) {
/*  454 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
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
/*      */   public byte getAndMoveToFirst(int k) {
/*  566 */     if (k == 0) {
/*  567 */       if (this.containsNullKey) {
/*  568 */         moveIndexToFirst(this.n);
/*  569 */         return this.value[this.n];
/*      */       } 
/*  571 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  574 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  577 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  578 */       return this.defRetValue; 
/*  579 */     if (k == curr) {
/*  580 */       moveIndexToFirst(pos);
/*  581 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  585 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
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
/*      */   public byte getAndMoveToLast(int k) {
/*  603 */     if (k == 0) {
/*  604 */       if (this.containsNullKey) {
/*  605 */         moveIndexToLast(this.n);
/*  606 */         return this.value[this.n];
/*      */       } 
/*  608 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  611 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  614 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  615 */       return this.defRetValue; 
/*  616 */     if (k == curr) {
/*  617 */       moveIndexToLast(pos);
/*  618 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  622 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
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
/*      */   public byte putAndMoveToFirst(int k, byte v) {
/*      */     int pos;
/*  643 */     if (k == 0) {
/*  644 */       if (this.containsNullKey) {
/*  645 */         moveIndexToFirst(this.n);
/*  646 */         return setValue(this.n, v);
/*      */       } 
/*  648 */       this.containsNullKey = true;
/*  649 */       pos = this.n;
/*      */     } else {
/*      */       
/*  652 */       int[] key = this.key;
/*      */       int curr;
/*  654 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  655 */         if (curr == k) {
/*  656 */           moveIndexToFirst(pos);
/*  657 */           return setValue(pos, v);
/*      */         } 
/*  659 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
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
/*      */   public byte putAndMoveToLast(int k, byte v) {
/*      */     int pos;
/*  696 */     if (k == 0) {
/*  697 */       if (this.containsNullKey) {
/*  698 */         moveIndexToLast(this.n);
/*  699 */         return setValue(this.n, v);
/*      */       } 
/*  701 */       this.containsNullKey = true;
/*  702 */       pos = this.n;
/*      */     } else {
/*      */       
/*  705 */       int[] key = this.key;
/*      */       int curr;
/*  707 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  708 */         if (curr == k) {
/*  709 */           moveIndexToLast(pos);
/*  710 */           return setValue(pos, v);
/*      */         } 
/*  712 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
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
/*      */   public byte get(int k) {
/*  739 */     if (k == 0) {
/*  740 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  742 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  745 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  746 */       return this.defRetValue; 
/*  747 */     if (k == curr) {
/*  748 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  751 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  752 */         return this.defRetValue; 
/*  753 */       if (k == curr) {
/*  754 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(int k) {
/*  760 */     if (k == 0) {
/*  761 */       return this.containsNullKey;
/*      */     }
/*  763 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  766 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  767 */       return false; 
/*  768 */     if (k == curr) {
/*  769 */       return true;
/*      */     }
/*      */     while (true) {
/*  772 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  773 */         return false; 
/*  774 */       if (k == curr)
/*  775 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(byte v) {
/*  780 */     byte[] value = this.value;
/*  781 */     int[] key = this.key;
/*  782 */     if (this.containsNullKey && value[this.n] == v)
/*  783 */       return true; 
/*  784 */     for (int i = this.n; i-- != 0;) {
/*  785 */       if (key[i] != 0 && value[i] == v)
/*  786 */         return true; 
/*  787 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getOrDefault(int k, byte defaultValue) {
/*  793 */     if (k == 0) {
/*  794 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  796 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  799 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  800 */       return defaultValue; 
/*  801 */     if (k == curr) {
/*  802 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  805 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  806 */         return defaultValue; 
/*  807 */       if (k == curr) {
/*  808 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public byte putIfAbsent(int k, byte v) {
/*  814 */     int pos = find(k);
/*  815 */     if (pos >= 0)
/*  816 */       return this.value[pos]; 
/*  817 */     insert(-pos - 1, k, v);
/*  818 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(int k, byte v) {
/*  824 */     if (k == 0) {
/*  825 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  826 */         removeNullEntry();
/*  827 */         return true;
/*      */       } 
/*  829 */       return false;
/*      */     } 
/*      */     
/*  832 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  835 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  836 */       return false; 
/*  837 */     if (k == curr && v == this.value[pos]) {
/*  838 */       removeEntry(pos);
/*  839 */       return true;
/*      */     } 
/*      */     while (true) {
/*  842 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  843 */         return false; 
/*  844 */       if (k == curr && v == this.value[pos]) {
/*  845 */         removeEntry(pos);
/*  846 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(int k, byte oldValue, byte v) {
/*  853 */     int pos = find(k);
/*  854 */     if (pos < 0 || oldValue != this.value[pos])
/*  855 */       return false; 
/*  856 */     this.value[pos] = v;
/*  857 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte replace(int k, byte v) {
/*  862 */     int pos = find(k);
/*  863 */     if (pos < 0)
/*  864 */       return this.defRetValue; 
/*  865 */     byte oldValue = this.value[pos];
/*  866 */     this.value[pos] = v;
/*  867 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte computeIfAbsent(int k, IntUnaryOperator mappingFunction) {
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
/*      */   public byte computeIfAbsentNullable(int k, IntFunction<? extends Byte> mappingFunction) {
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
/*      */   public byte computeIfPresent(int k, BiFunction<? super Integer, ? super Byte, ? extends Byte> remappingFunction) {
/*  899 */     Objects.requireNonNull(remappingFunction);
/*  900 */     int pos = find(k);
/*  901 */     if (pos < 0)
/*  902 */       return this.defRetValue; 
/*  903 */     Byte newValue = remappingFunction.apply(Integer.valueOf(k), Byte.valueOf(this.value[pos]));
/*  904 */     if (newValue == null) {
/*  905 */       if (k == 0) {
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
/*      */   public byte compute(int k, BiFunction<? super Integer, ? super Byte, ? extends Byte> remappingFunction) {
/*  917 */     Objects.requireNonNull(remappingFunction);
/*  918 */     int pos = find(k);
/*  919 */     Byte newValue = remappingFunction.apply(Integer.valueOf(k), (pos >= 0) ? Byte.valueOf(this.value[pos]) : null);
/*  920 */     if (newValue == null) {
/*  921 */       if (pos >= 0)
/*  922 */         if (k == 0) {
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
/*      */   public byte merge(int k, byte v, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*  940 */     Objects.requireNonNull(remappingFunction);
/*  941 */     int pos = find(k);
/*  942 */     if (pos < 0) {
/*  943 */       insert(-pos - 1, k, v);
/*  944 */       return v;
/*      */     } 
/*  946 */     Byte newValue = remappingFunction.apply(Byte.valueOf(this.value[pos]), Byte.valueOf(v));
/*  947 */     if (newValue == null) {
/*  948 */       if (k == 0) {
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
/*  969 */     Arrays.fill(this.key, 0);
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
/*      */     implements Int2ByteMap.Entry, Map.Entry<Integer, Byte>
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
/*      */     public int getIntKey() {
/*  996 */       return Int2ByteLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public byte getByteValue() {
/* 1000 */       return Int2ByteLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public byte setValue(byte v) {
/* 1004 */       byte oldValue = Int2ByteLinkedOpenHashMap.this.value[this.index];
/* 1005 */       Int2ByteLinkedOpenHashMap.this.value[this.index] = v;
/* 1006 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getKey() {
/* 1016 */       return Integer.valueOf(Int2ByteLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte getValue() {
/* 1026 */       return Byte.valueOf(Int2ByteLinkedOpenHashMap.this.value[this.index]);
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
/* 1043 */       Map.Entry<Integer, Byte> e = (Map.Entry<Integer, Byte>)o;
/* 1044 */       return (Int2ByteLinkedOpenHashMap.this.key[this.index] == ((Integer)e.getKey()).intValue() && Int2ByteLinkedOpenHashMap.this.value[this.index] == ((Byte)e.getValue()).byteValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1048 */       return Int2ByteLinkedOpenHashMap.this.key[this.index] ^ Int2ByteLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1052 */       return Int2ByteLinkedOpenHashMap.this.key[this.index] + "=>" + Int2ByteLinkedOpenHashMap.this.value[this.index];
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
/*      */   public int firstIntKey() {
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
/*      */   public int lastIntKey() {
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
/*      */   public Int2ByteSortedMap tailMap(int from) {
/* 1154 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ByteSortedMap headMap(int to) {
/* 1163 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ByteSortedMap subMap(int from, int to) {
/* 1172 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntComparator comparator() {
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
/* 1215 */       this.next = Int2ByteLinkedOpenHashMap.this.first;
/* 1216 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(int from) {
/* 1219 */       if (from == 0) {
/* 1220 */         if (Int2ByteLinkedOpenHashMap.this.containsNullKey) {
/* 1221 */           this.next = (int)Int2ByteLinkedOpenHashMap.this.link[Int2ByteLinkedOpenHashMap.this.n];
/* 1222 */           this.prev = Int2ByteLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1225 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1227 */       if (Int2ByteLinkedOpenHashMap.this.key[Int2ByteLinkedOpenHashMap.this.last] == from) {
/* 1228 */         this.prev = Int2ByteLinkedOpenHashMap.this.last;
/* 1229 */         this.index = Int2ByteLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1233 */       int pos = HashCommon.mix(from) & Int2ByteLinkedOpenHashMap.this.mask;
/*      */       
/* 1235 */       while (Int2ByteLinkedOpenHashMap.this.key[pos] != 0) {
/* 1236 */         if (Int2ByteLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1238 */           this.next = (int)Int2ByteLinkedOpenHashMap.this.link[pos];
/* 1239 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1242 */         pos = pos + 1 & Int2ByteLinkedOpenHashMap.this.mask;
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
/* 1260 */         this.index = Int2ByteLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1263 */       int pos = Int2ByteLinkedOpenHashMap.this.first;
/* 1264 */       this.index = 1;
/* 1265 */       while (pos != this.prev) {
/* 1266 */         pos = (int)Int2ByteLinkedOpenHashMap.this.link[pos];
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
/* 1282 */       this.next = (int)Int2ByteLinkedOpenHashMap.this.link[this.curr];
/* 1283 */       this.prev = this.curr;
/* 1284 */       if (this.index >= 0)
/* 1285 */         this.index++; 
/* 1286 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1289 */       if (!hasPrevious())
/* 1290 */         throw new NoSuchElementException(); 
/* 1291 */       this.curr = this.prev;
/* 1292 */       this.prev = (int)(Int2ByteLinkedOpenHashMap.this.link[this.curr] >>> 32L);
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
/* 1308 */         this.prev = (int)(Int2ByteLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1310 */         this.next = (int)Int2ByteLinkedOpenHashMap.this.link[this.curr];
/* 1311 */       }  Int2ByteLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1316 */       if (this.prev == -1) {
/* 1317 */         Int2ByteLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1319 */         Int2ByteLinkedOpenHashMap.this.link[this.prev] = Int2ByteLinkedOpenHashMap.this.link[this.prev] ^ (Int2ByteLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1320 */       }  if (this.next == -1) {
/* 1321 */         Int2ByteLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1323 */         Int2ByteLinkedOpenHashMap.this.link[this.next] = Int2ByteLinkedOpenHashMap.this.link[this.next] ^ (Int2ByteLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1324 */       }  int pos = this.curr;
/* 1325 */       this.curr = -1;
/* 1326 */       if (pos == Int2ByteLinkedOpenHashMap.this.n) {
/* 1327 */         Int2ByteLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1330 */         int[] key = Int2ByteLinkedOpenHashMap.this.key;
/*      */         
/*      */         while (true) {
/*      */           int curr, last;
/* 1334 */           pos = (last = pos) + 1 & Int2ByteLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1336 */             if ((curr = key[pos]) == 0) {
/* 1337 */               key[last] = 0;
/*      */               return;
/*      */             } 
/* 1340 */             int slot = HashCommon.mix(curr) & Int2ByteLinkedOpenHashMap.this.mask;
/* 1341 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1343 */             pos = pos + 1 & Int2ByteLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1345 */           key[last] = curr;
/* 1346 */           Int2ByteLinkedOpenHashMap.this.value[last] = Int2ByteLinkedOpenHashMap.this.value[pos];
/* 1347 */           if (this.next == pos)
/* 1348 */             this.next = last; 
/* 1349 */           if (this.prev == pos)
/* 1350 */             this.prev = last; 
/* 1351 */           Int2ByteLinkedOpenHashMap.this.fixPointers(pos, last);
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
/*      */     public void set(Int2ByteMap.Entry ok) {
/* 1368 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Int2ByteMap.Entry ok) {
/* 1371 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Int2ByteMap.Entry> {
/*      */     private Int2ByteLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator(int from) {
/* 1379 */       super(from);
/*      */     }
/*      */     
/*      */     public Int2ByteLinkedOpenHashMap.MapEntry next() {
/* 1383 */       return this.entry = new Int2ByteLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     public EntryIterator() {}
/*      */     public Int2ByteLinkedOpenHashMap.MapEntry previous() {
/* 1387 */       return this.entry = new Int2ByteLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1391 */       super.remove();
/* 1392 */       this.entry.index = -1;
/*      */     }
/*      */   }
/*      */   
/* 1396 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Int2ByteMap.Entry> { final Int2ByteLinkedOpenHashMap.MapEntry entry = new Int2ByteLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(int from) {
/* 1400 */       super(from);
/*      */     }
/*      */     
/*      */     public Int2ByteLinkedOpenHashMap.MapEntry next() {
/* 1404 */       this.entry.index = nextEntry();
/* 1405 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Int2ByteLinkedOpenHashMap.MapEntry previous() {
/* 1409 */       this.entry.index = previousEntry();
/* 1410 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Int2ByteMap.Entry> implements Int2ByteSortedMap.FastSortedEntrySet { public ObjectBidirectionalIterator<Int2ByteMap.Entry> iterator() {
/* 1416 */       return (ObjectBidirectionalIterator<Int2ByteMap.Entry>)new Int2ByteLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     private MapEntrySet() {}
/*      */     public Comparator<? super Int2ByteMap.Entry> comparator() {
/* 1420 */       return null;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Int2ByteMap.Entry> subSet(Int2ByteMap.Entry fromElement, Int2ByteMap.Entry toElement) {
/* 1424 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Int2ByteMap.Entry> headSet(Int2ByteMap.Entry toElement) {
/* 1428 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Int2ByteMap.Entry> tailSet(Int2ByteMap.Entry fromElement) {
/* 1432 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Int2ByteMap.Entry first() {
/* 1436 */       if (Int2ByteLinkedOpenHashMap.this.size == 0)
/* 1437 */         throw new NoSuchElementException(); 
/* 1438 */       return new Int2ByteLinkedOpenHashMap.MapEntry(Int2ByteLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Int2ByteMap.Entry last() {
/* 1442 */       if (Int2ByteLinkedOpenHashMap.this.size == 0)
/* 1443 */         throw new NoSuchElementException(); 
/* 1444 */       return new Int2ByteLinkedOpenHashMap.MapEntry(Int2ByteLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1449 */       if (!(o instanceof Map.Entry))
/* 1450 */         return false; 
/* 1451 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1452 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1453 */         return false; 
/* 1454 */       if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1455 */         return false; 
/* 1456 */       int k = ((Integer)e.getKey()).intValue();
/* 1457 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1458 */       if (k == 0) {
/* 1459 */         return (Int2ByteLinkedOpenHashMap.this.containsNullKey && Int2ByteLinkedOpenHashMap.this.value[Int2ByteLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1461 */       int[] key = Int2ByteLinkedOpenHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/* 1464 */       if ((curr = key[pos = HashCommon.mix(k) & Int2ByteLinkedOpenHashMap.this.mask]) == 0)
/* 1465 */         return false; 
/* 1466 */       if (k == curr) {
/* 1467 */         return (Int2ByteLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1470 */         if ((curr = key[pos = pos + 1 & Int2ByteLinkedOpenHashMap.this.mask]) == 0)
/* 1471 */           return false; 
/* 1472 */         if (k == curr) {
/* 1473 */           return (Int2ByteLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1479 */       if (!(o instanceof Map.Entry))
/* 1480 */         return false; 
/* 1481 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1482 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1483 */         return false; 
/* 1484 */       if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1485 */         return false; 
/* 1486 */       int k = ((Integer)e.getKey()).intValue();
/* 1487 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1488 */       if (k == 0) {
/* 1489 */         if (Int2ByteLinkedOpenHashMap.this.containsNullKey && Int2ByteLinkedOpenHashMap.this.value[Int2ByteLinkedOpenHashMap.this.n] == v) {
/* 1490 */           Int2ByteLinkedOpenHashMap.this.removeNullEntry();
/* 1491 */           return true;
/*      */         } 
/* 1493 */         return false;
/*      */       } 
/*      */       
/* 1496 */       int[] key = Int2ByteLinkedOpenHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/* 1499 */       if ((curr = key[pos = HashCommon.mix(k) & Int2ByteLinkedOpenHashMap.this.mask]) == 0)
/* 1500 */         return false; 
/* 1501 */       if (curr == k) {
/* 1502 */         if (Int2ByteLinkedOpenHashMap.this.value[pos] == v) {
/* 1503 */           Int2ByteLinkedOpenHashMap.this.removeEntry(pos);
/* 1504 */           return true;
/*      */         } 
/* 1506 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1509 */         if ((curr = key[pos = pos + 1 & Int2ByteLinkedOpenHashMap.this.mask]) == 0)
/* 1510 */           return false; 
/* 1511 */         if (curr == k && 
/* 1512 */           Int2ByteLinkedOpenHashMap.this.value[pos] == v) {
/* 1513 */           Int2ByteLinkedOpenHashMap.this.removeEntry(pos);
/* 1514 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1521 */       return Int2ByteLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1525 */       Int2ByteLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Int2ByteMap.Entry> iterator(Int2ByteMap.Entry from) {
/* 1540 */       return new Int2ByteLinkedOpenHashMap.EntryIterator(from.getIntKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Int2ByteMap.Entry> fastIterator() {
/* 1551 */       return new Int2ByteLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Int2ByteMap.Entry> fastIterator(Int2ByteMap.Entry from) {
/* 1566 */       return new Int2ByteLinkedOpenHashMap.FastEntryIterator(from.getIntKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Int2ByteMap.Entry> consumer) {
/* 1571 */       for (int i = Int2ByteLinkedOpenHashMap.this.size, next = Int2ByteLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1572 */         int curr = next;
/* 1573 */         next = (int)Int2ByteLinkedOpenHashMap.this.link[curr];
/* 1574 */         consumer.accept(new AbstractInt2ByteMap.BasicEntry(Int2ByteLinkedOpenHashMap.this.key[curr], Int2ByteLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Int2ByteMap.Entry> consumer) {
/* 1580 */       AbstractInt2ByteMap.BasicEntry entry = new AbstractInt2ByteMap.BasicEntry();
/* 1581 */       for (int i = Int2ByteLinkedOpenHashMap.this.size, next = Int2ByteLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1582 */         int curr = next;
/* 1583 */         next = (int)Int2ByteLinkedOpenHashMap.this.link[curr];
/* 1584 */         entry.key = Int2ByteLinkedOpenHashMap.this.key[curr];
/* 1585 */         entry.value = Int2ByteLinkedOpenHashMap.this.value[curr];
/* 1586 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Int2ByteSortedMap.FastSortedEntrySet int2ByteEntrySet() {
/* 1592 */     if (this.entries == null)
/* 1593 */       this.entries = new MapEntrySet(); 
/* 1594 */     return this.entries;
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
/* 1607 */       super(k);
/*      */     }
/*      */     
/*      */     public int previousInt() {
/* 1611 */       return Int2ByteLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public int nextInt() {
/* 1618 */       return Int2ByteLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractIntSortedSet { private KeySet() {}
/*      */     
/*      */     public IntListIterator iterator(int from) {
/* 1624 */       return new Int2ByteLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public IntListIterator iterator() {
/* 1628 */       return new Int2ByteLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1633 */       if (Int2ByteLinkedOpenHashMap.this.containsNullKey)
/* 1634 */         consumer.accept(Int2ByteLinkedOpenHashMap.this.key[Int2ByteLinkedOpenHashMap.this.n]); 
/* 1635 */       for (int pos = Int2ByteLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1636 */         int k = Int2ByteLinkedOpenHashMap.this.key[pos];
/* 1637 */         if (k != 0)
/* 1638 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1643 */       return Int2ByteLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(int k) {
/* 1647 */       return Int2ByteLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(int k) {
/* 1651 */       int oldSize = Int2ByteLinkedOpenHashMap.this.size;
/* 1652 */       Int2ByteLinkedOpenHashMap.this.remove(k);
/* 1653 */       return (Int2ByteLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1657 */       Int2ByteLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public int firstInt() {
/* 1661 */       if (Int2ByteLinkedOpenHashMap.this.size == 0)
/* 1662 */         throw new NoSuchElementException(); 
/* 1663 */       return Int2ByteLinkedOpenHashMap.this.key[Int2ByteLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public int lastInt() {
/* 1667 */       if (Int2ByteLinkedOpenHashMap.this.size == 0)
/* 1668 */         throw new NoSuchElementException(); 
/* 1669 */       return Int2ByteLinkedOpenHashMap.this.key[Int2ByteLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public IntComparator comparator() {
/* 1673 */       return null;
/*      */     }
/*      */     
/*      */     public IntSortedSet tailSet(int from) {
/* 1677 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public IntSortedSet headSet(int to) {
/* 1681 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public IntSortedSet subSet(int from, int to) {
/* 1685 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public IntSortedSet keySet() {
/* 1690 */     if (this.keys == null)
/* 1691 */       this.keys = new KeySet(); 
/* 1692 */     return this.keys;
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
/* 1706 */       return Int2ByteLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1713 */       return Int2ByteLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ByteCollection values() {
/* 1718 */     if (this.values == null)
/* 1719 */       this.values = (ByteCollection)new AbstractByteCollection()
/*      */         {
/*      */           public ByteIterator iterator() {
/* 1722 */             return (ByteIterator)new Int2ByteLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1726 */             return Int2ByteLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(byte v) {
/* 1730 */             return Int2ByteLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1734 */             Int2ByteLinkedOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1739 */             if (Int2ByteLinkedOpenHashMap.this.containsNullKey)
/* 1740 */               consumer.accept(Int2ByteLinkedOpenHashMap.this.value[Int2ByteLinkedOpenHashMap.this.n]); 
/* 1741 */             for (int pos = Int2ByteLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1742 */               if (Int2ByteLinkedOpenHashMap.this.key[pos] != 0)
/* 1743 */                 consumer.accept(Int2ByteLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1746 */     return this.values;
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
/* 1763 */     return trim(this.size);
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
/* 1787 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1788 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1789 */       return true; 
/*      */     try {
/* 1791 */       rehash(l);
/* 1792 */     } catch (OutOfMemoryError cantDoIt) {
/* 1793 */       return false;
/*      */     } 
/* 1795 */     return true;
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
/* 1811 */     int[] key = this.key;
/* 1812 */     byte[] value = this.value;
/* 1813 */     int mask = newN - 1;
/* 1814 */     int[] newKey = new int[newN + 1];
/* 1815 */     byte[] newValue = new byte[newN + 1];
/* 1816 */     int i = this.first, prev = -1, newPrev = -1;
/* 1817 */     long[] link = this.link;
/* 1818 */     long[] newLink = new long[newN + 1];
/* 1819 */     this.first = -1;
/* 1820 */     for (int j = this.size; j-- != 0; ) {
/* 1821 */       int pos; if (key[i] == 0) {
/* 1822 */         pos = newN;
/*      */       } else {
/* 1824 */         pos = HashCommon.mix(key[i]) & mask;
/* 1825 */         while (newKey[pos] != 0)
/* 1826 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1828 */       newKey[pos] = key[i];
/* 1829 */       newValue[pos] = value[i];
/* 1830 */       if (prev != -1) {
/* 1831 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1832 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1833 */         newPrev = pos;
/*      */       } else {
/* 1835 */         newPrev = this.first = pos;
/*      */         
/* 1837 */         newLink[pos] = -1L;
/*      */       } 
/* 1839 */       int t = i;
/* 1840 */       i = (int)link[i];
/* 1841 */       prev = t;
/*      */     } 
/* 1843 */     this.link = newLink;
/* 1844 */     this.last = newPrev;
/* 1845 */     if (newPrev != -1)
/*      */     {
/* 1847 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1848 */     this.n = newN;
/* 1849 */     this.mask = mask;
/* 1850 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1851 */     this.key = newKey;
/* 1852 */     this.value = newValue;
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
/*      */   public Int2ByteLinkedOpenHashMap clone() {
/*      */     Int2ByteLinkedOpenHashMap c;
/*      */     try {
/* 1869 */       c = (Int2ByteLinkedOpenHashMap)super.clone();
/* 1870 */     } catch (CloneNotSupportedException cantHappen) {
/* 1871 */       throw new InternalError();
/*      */     } 
/* 1873 */     c.keys = null;
/* 1874 */     c.values = null;
/* 1875 */     c.entries = null;
/* 1876 */     c.containsNullKey = this.containsNullKey;
/* 1877 */     c.key = (int[])this.key.clone();
/* 1878 */     c.value = (byte[])this.value.clone();
/* 1879 */     c.link = (long[])this.link.clone();
/* 1880 */     return c;
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
/* 1893 */     int h = 0;
/* 1894 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1895 */       while (this.key[i] == 0)
/* 1896 */         i++; 
/* 1897 */       t = this.key[i];
/* 1898 */       t ^= this.value[i];
/* 1899 */       h += t;
/* 1900 */       i++;
/*      */     } 
/*      */     
/* 1903 */     if (this.containsNullKey)
/* 1904 */       h += this.value[this.n]; 
/* 1905 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1908 */     int[] key = this.key;
/* 1909 */     byte[] value = this.value;
/* 1910 */     MapIterator i = new MapIterator();
/* 1911 */     s.defaultWriteObject();
/* 1912 */     for (int j = this.size; j-- != 0; ) {
/* 1913 */       int e = i.nextEntry();
/* 1914 */       s.writeInt(key[e]);
/* 1915 */       s.writeByte(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1920 */     s.defaultReadObject();
/* 1921 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1922 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1923 */     this.mask = this.n - 1;
/* 1924 */     int[] key = this.key = new int[this.n + 1];
/* 1925 */     byte[] value = this.value = new byte[this.n + 1];
/* 1926 */     long[] link = this.link = new long[this.n + 1];
/* 1927 */     int prev = -1;
/* 1928 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1931 */     for (int i = this.size; i-- != 0; ) {
/* 1932 */       int pos, k = s.readInt();
/* 1933 */       byte v = s.readByte();
/* 1934 */       if (k == 0) {
/* 1935 */         pos = this.n;
/* 1936 */         this.containsNullKey = true;
/*      */       } else {
/* 1938 */         pos = HashCommon.mix(k) & this.mask;
/* 1939 */         while (key[pos] != 0)
/* 1940 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1942 */       key[pos] = k;
/* 1943 */       value[pos] = v;
/* 1944 */       if (this.first != -1) {
/* 1945 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1946 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1947 */         prev = pos; continue;
/*      */       } 
/* 1949 */       prev = this.first = pos;
/*      */       
/* 1951 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1954 */     this.last = prev;
/* 1955 */     if (prev != -1)
/*      */     {
/* 1957 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2ByteLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */