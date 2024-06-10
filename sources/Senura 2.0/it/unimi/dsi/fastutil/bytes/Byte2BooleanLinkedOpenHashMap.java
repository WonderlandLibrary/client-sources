/*      */ package it.unimi.dsi.fastutil.bytes;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
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
/*      */ import java.util.function.IntPredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Byte2BooleanLinkedOpenHashMap
/*      */   extends AbstractByte2BooleanSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient byte[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*  108 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  113 */   protected transient int last = -1;
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
/*      */   protected transient Byte2BooleanSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ByteSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanLinkedOpenHashMap(int expected, float f) {
/*  154 */     if (f <= 0.0F || f > 1.0F)
/*  155 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  156 */     if (expected < 0)
/*  157 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  158 */     this.f = f;
/*  159 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  160 */     this.mask = this.n - 1;
/*  161 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  162 */     this.key = new byte[this.n + 1];
/*  163 */     this.value = new boolean[this.n + 1];
/*  164 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanLinkedOpenHashMap(int expected) {
/*  173 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanLinkedOpenHashMap() {
/*  181 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanLinkedOpenHashMap(Map<? extends Byte, ? extends Boolean> m, float f) {
/*  192 */     this(m.size(), f);
/*  193 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanLinkedOpenHashMap(Map<? extends Byte, ? extends Boolean> m) {
/*  203 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanLinkedOpenHashMap(Byte2BooleanMap m, float f) {
/*  214 */     this(m.size(), f);
/*  215 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanLinkedOpenHashMap(Byte2BooleanMap m) {
/*  225 */     this(m, 0.75F);
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
/*      */   public Byte2BooleanLinkedOpenHashMap(byte[] k, boolean[] v, float f) {
/*  240 */     this(k.length, f);
/*  241 */     if (k.length != v.length) {
/*  242 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  244 */     for (int i = 0; i < k.length; i++) {
/*  245 */       put(k[i], v[i]);
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
/*      */   public Byte2BooleanLinkedOpenHashMap(byte[] k, boolean[] v) {
/*  259 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  262 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  265 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  266 */     if (needed > this.n)
/*  267 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  270 */     int needed = (int)Math.min(1073741824L, 
/*  271 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  272 */     if (needed > this.n)
/*  273 */       rehash(needed); 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  276 */     boolean oldValue = this.value[pos];
/*  277 */     this.size--;
/*  278 */     fixPointers(pos);
/*  279 */     shiftKeys(pos);
/*  280 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  281 */       rehash(this.n / 2); 
/*  282 */     return oldValue;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  285 */     this.containsNullKey = false;
/*  286 */     boolean oldValue = this.value[this.n];
/*  287 */     this.size--;
/*  288 */     fixPointers(this.n);
/*  289 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  290 */       rehash(this.n / 2); 
/*  291 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Byte, ? extends Boolean> m) {
/*  295 */     if (this.f <= 0.5D) {
/*  296 */       ensureCapacity(m.size());
/*      */     } else {
/*  298 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  300 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(byte k) {
/*  304 */     if (k == 0) {
/*  305 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  307 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  310 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  311 */       return -(pos + 1); 
/*  312 */     if (k == curr) {
/*  313 */       return pos;
/*      */     }
/*      */     while (true) {
/*  316 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  317 */         return -(pos + 1); 
/*  318 */       if (k == curr)
/*  319 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, byte k, boolean v) {
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
/*      */   public boolean put(byte k, boolean v) {
/*  343 */     int pos = find(k);
/*  344 */     if (pos < 0) {
/*  345 */       insert(-pos - 1, k, v);
/*  346 */       return this.defRetValue;
/*      */     } 
/*  348 */     boolean oldValue = this.value[pos];
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
/*  363 */     byte[] key = this.key; while (true) {
/*      */       byte curr; int last;
/*  365 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  367 */         if ((curr = key[pos]) == 0) {
/*  368 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  371 */         int slot = HashCommon.mix(curr) & this.mask;
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
/*      */   public boolean remove(byte k) {
/*  384 */     if (k == 0) {
/*  385 */       if (this.containsNullKey)
/*  386 */         return removeNullEntry(); 
/*  387 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  390 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  393 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  394 */       return this.defRetValue; 
/*  395 */     if (k == curr)
/*  396 */       return removeEntry(pos); 
/*      */     while (true) {
/*  398 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  399 */         return this.defRetValue; 
/*  400 */       if (k == curr)
/*  401 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private boolean setValue(int pos, boolean v) {
/*  405 */     boolean oldValue = this.value[pos];
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
/*      */   public boolean removeFirstBoolean() {
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
/*  428 */     boolean v = this.value[pos];
/*  429 */     if (pos == this.n) {
/*  430 */       this.containsNullKey = false;
/*      */     } else {
/*  432 */       shiftKeys(pos);
/*  433 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  434 */       rehash(this.n / 2); 
/*  435 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeLastBoolean() {
/*  445 */     if (this.size == 0)
/*  446 */       throw new NoSuchElementException(); 
/*  447 */     int pos = this.last;
/*      */     
/*  449 */     this.last = (int)(this.link[pos] >>> 32L);
/*  450 */     if (0 <= this.last)
/*      */     {
/*  452 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  454 */     this.size--;
/*  455 */     boolean v = this.value[pos];
/*  456 */     if (pos == this.n) {
/*  457 */       this.containsNullKey = false;
/*      */     } else {
/*  459 */       shiftKeys(pos);
/*  460 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  461 */       rehash(this.n / 2); 
/*  462 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  465 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  467 */     if (this.last == i) {
/*  468 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  470 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  472 */       long linki = this.link[i];
/*  473 */       int prev = (int)(linki >>> 32L);
/*  474 */       int next = (int)linki;
/*  475 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  476 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  478 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  479 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  480 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  483 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  485 */     if (this.first == i) {
/*  486 */       this.first = (int)this.link[i];
/*      */       
/*  488 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  490 */       long linki = this.link[i];
/*  491 */       int prev = (int)(linki >>> 32L);
/*  492 */       int next = (int)linki;
/*  493 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  494 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  496 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  497 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  498 */     this.last = i;
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
/*      */   public boolean getAndMoveToFirst(byte k) {
/*  510 */     if (k == 0) {
/*  511 */       if (this.containsNullKey) {
/*  512 */         moveIndexToFirst(this.n);
/*  513 */         return this.value[this.n];
/*      */       } 
/*  515 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  518 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  521 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  522 */       return this.defRetValue; 
/*  523 */     if (k == curr) {
/*  524 */       moveIndexToFirst(pos);
/*  525 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  529 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  530 */         return this.defRetValue; 
/*  531 */       if (k == curr) {
/*  532 */         moveIndexToFirst(pos);
/*  533 */         return this.value[pos];
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
/*      */   public boolean getAndMoveToLast(byte k) {
/*  547 */     if (k == 0) {
/*  548 */       if (this.containsNullKey) {
/*  549 */         moveIndexToLast(this.n);
/*  550 */         return this.value[this.n];
/*      */       } 
/*  552 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  555 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  558 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  559 */       return this.defRetValue; 
/*  560 */     if (k == curr) {
/*  561 */       moveIndexToLast(pos);
/*  562 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  566 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  567 */         return this.defRetValue; 
/*  568 */       if (k == curr) {
/*  569 */         moveIndexToLast(pos);
/*  570 */         return this.value[pos];
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
/*      */   public boolean putAndMoveToFirst(byte k, boolean v) {
/*      */     int pos;
/*  587 */     if (k == 0) {
/*  588 */       if (this.containsNullKey) {
/*  589 */         moveIndexToFirst(this.n);
/*  590 */         return setValue(this.n, v);
/*      */       } 
/*  592 */       this.containsNullKey = true;
/*  593 */       pos = this.n;
/*      */     } else {
/*      */       
/*  596 */       byte[] key = this.key;
/*      */       byte curr;
/*  598 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  599 */         if (curr == k) {
/*  600 */           moveIndexToFirst(pos);
/*  601 */           return setValue(pos, v);
/*      */         } 
/*  603 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  604 */           if (curr == k) {
/*  605 */             moveIndexToFirst(pos);
/*  606 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  610 */     }  this.key[pos] = k;
/*  611 */     this.value[pos] = v;
/*  612 */     if (this.size == 0) {
/*  613 */       this.first = this.last = pos;
/*      */       
/*  615 */       this.link[pos] = -1L;
/*      */     } else {
/*  617 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  618 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  619 */       this.first = pos;
/*      */     } 
/*  621 */     if (this.size++ >= this.maxFill) {
/*  622 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  625 */     return this.defRetValue;
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
/*      */   public boolean putAndMoveToLast(byte k, boolean v) {
/*      */     int pos;
/*  640 */     if (k == 0) {
/*  641 */       if (this.containsNullKey) {
/*  642 */         moveIndexToLast(this.n);
/*  643 */         return setValue(this.n, v);
/*      */       } 
/*  645 */       this.containsNullKey = true;
/*  646 */       pos = this.n;
/*      */     } else {
/*      */       
/*  649 */       byte[] key = this.key;
/*      */       byte curr;
/*  651 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  652 */         if (curr == k) {
/*  653 */           moveIndexToLast(pos);
/*  654 */           return setValue(pos, v);
/*      */         } 
/*  656 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  657 */           if (curr == k) {
/*  658 */             moveIndexToLast(pos);
/*  659 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  663 */     }  this.key[pos] = k;
/*  664 */     this.value[pos] = v;
/*  665 */     if (this.size == 0) {
/*  666 */       this.first = this.last = pos;
/*      */       
/*  668 */       this.link[pos] = -1L;
/*      */     } else {
/*  670 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  671 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  672 */       this.last = pos;
/*      */     } 
/*  674 */     if (this.size++ >= this.maxFill) {
/*  675 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  678 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean get(byte k) {
/*  683 */     if (k == 0) {
/*  684 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  686 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  689 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  690 */       return this.defRetValue; 
/*  691 */     if (k == curr) {
/*  692 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  695 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  696 */         return this.defRetValue; 
/*  697 */       if (k == curr) {
/*  698 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(byte k) {
/*  704 */     if (k == 0) {
/*  705 */       return this.containsNullKey;
/*      */     }
/*  707 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  710 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  711 */       return false; 
/*  712 */     if (k == curr) {
/*  713 */       return true;
/*      */     }
/*      */     while (true) {
/*  716 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  717 */         return false; 
/*  718 */       if (k == curr)
/*  719 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  724 */     boolean[] value = this.value;
/*  725 */     byte[] key = this.key;
/*  726 */     if (this.containsNullKey && value[this.n] == v)
/*  727 */       return true; 
/*  728 */     for (int i = this.n; i-- != 0;) {
/*  729 */       if (key[i] != 0 && value[i] == v)
/*  730 */         return true; 
/*  731 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(byte k, boolean defaultValue) {
/*  737 */     if (k == 0) {
/*  738 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  740 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  743 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  744 */       return defaultValue; 
/*  745 */     if (k == curr) {
/*  746 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  749 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  750 */         return defaultValue; 
/*  751 */       if (k == curr) {
/*  752 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean putIfAbsent(byte k, boolean v) {
/*  758 */     int pos = find(k);
/*  759 */     if (pos >= 0)
/*  760 */       return this.value[pos]; 
/*  761 */     insert(-pos - 1, k, v);
/*  762 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(byte k, boolean v) {
/*  768 */     if (k == 0) {
/*  769 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  770 */         removeNullEntry();
/*  771 */         return true;
/*      */       } 
/*  773 */       return false;
/*      */     } 
/*      */     
/*  776 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  779 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  780 */       return false; 
/*  781 */     if (k == curr && v == this.value[pos]) {
/*  782 */       removeEntry(pos);
/*  783 */       return true;
/*      */     } 
/*      */     while (true) {
/*  786 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  787 */         return false; 
/*  788 */       if (k == curr && v == this.value[pos]) {
/*  789 */         removeEntry(pos);
/*  790 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(byte k, boolean oldValue, boolean v) {
/*  797 */     int pos = find(k);
/*  798 */     if (pos < 0 || oldValue != this.value[pos])
/*  799 */       return false; 
/*  800 */     this.value[pos] = v;
/*  801 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(byte k, boolean v) {
/*  806 */     int pos = find(k);
/*  807 */     if (pos < 0)
/*  808 */       return this.defRetValue; 
/*  809 */     boolean oldValue = this.value[pos];
/*  810 */     this.value[pos] = v;
/*  811 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(byte k, IntPredicate mappingFunction) {
/*  816 */     Objects.requireNonNull(mappingFunction);
/*  817 */     int pos = find(k);
/*  818 */     if (pos >= 0)
/*  819 */       return this.value[pos]; 
/*  820 */     boolean newValue = mappingFunction.test(k);
/*  821 */     insert(-pos - 1, k, newValue);
/*  822 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsentNullable(byte k, IntFunction<? extends Boolean> mappingFunction) {
/*  828 */     Objects.requireNonNull(mappingFunction);
/*  829 */     int pos = find(k);
/*  830 */     if (pos >= 0)
/*  831 */       return this.value[pos]; 
/*  832 */     Boolean newValue = mappingFunction.apply(k);
/*  833 */     if (newValue == null)
/*  834 */       return this.defRetValue; 
/*  835 */     boolean v = newValue.booleanValue();
/*  836 */     insert(-pos - 1, k, v);
/*  837 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfPresent(byte k, BiFunction<? super Byte, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  843 */     Objects.requireNonNull(remappingFunction);
/*  844 */     int pos = find(k);
/*  845 */     if (pos < 0)
/*  846 */       return this.defRetValue; 
/*  847 */     Boolean newValue = remappingFunction.apply(Byte.valueOf(k), Boolean.valueOf(this.value[pos]));
/*  848 */     if (newValue == null) {
/*  849 */       if (k == 0) {
/*  850 */         removeNullEntry();
/*      */       } else {
/*  852 */         removeEntry(pos);
/*  853 */       }  return this.defRetValue;
/*      */     } 
/*  855 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compute(byte k, BiFunction<? super Byte, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  861 */     Objects.requireNonNull(remappingFunction);
/*  862 */     int pos = find(k);
/*  863 */     Boolean newValue = remappingFunction.apply(Byte.valueOf(k), 
/*  864 */         (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  865 */     if (newValue == null) {
/*  866 */       if (pos >= 0)
/*  867 */         if (k == 0) {
/*  868 */           removeNullEntry();
/*      */         } else {
/*  870 */           removeEntry(pos);
/*      */         }  
/*  872 */       return this.defRetValue;
/*      */     } 
/*  874 */     boolean newVal = newValue.booleanValue();
/*  875 */     if (pos < 0) {
/*  876 */       insert(-pos - 1, k, newVal);
/*  877 */       return newVal;
/*      */     } 
/*  879 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean merge(byte k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  885 */     Objects.requireNonNull(remappingFunction);
/*  886 */     int pos = find(k);
/*  887 */     if (pos < 0) {
/*  888 */       insert(-pos - 1, k, v);
/*  889 */       return v;
/*      */     } 
/*  891 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  892 */     if (newValue == null) {
/*  893 */       if (k == 0) {
/*  894 */         removeNullEntry();
/*      */       } else {
/*  896 */         removeEntry(pos);
/*  897 */       }  return this.defRetValue;
/*      */     } 
/*  899 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
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
/*  910 */     if (this.size == 0)
/*      */       return; 
/*  912 */     this.size = 0;
/*  913 */     this.containsNullKey = false;
/*  914 */     Arrays.fill(this.key, (byte)0);
/*  915 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  919 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  923 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Byte2BooleanMap.Entry, Map.Entry<Byte, Boolean>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  935 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public byte getByteKey() {
/*  941 */       return Byte2BooleanLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public boolean getBooleanValue() {
/*  945 */       return Byte2BooleanLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  949 */       boolean oldValue = Byte2BooleanLinkedOpenHashMap.this.value[this.index];
/*  950 */       Byte2BooleanLinkedOpenHashMap.this.value[this.index] = v;
/*  951 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte getKey() {
/*  961 */       return Byte.valueOf(Byte2BooleanLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  971 */       return Boolean.valueOf(Byte2BooleanLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean setValue(Boolean v) {
/*  981 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  986 */       if (!(o instanceof Map.Entry))
/*  987 */         return false; 
/*  988 */       Map.Entry<Byte, Boolean> e = (Map.Entry<Byte, Boolean>)o;
/*  989 */       return (Byte2BooleanLinkedOpenHashMap.this.key[this.index] == ((Byte)e.getKey()).byteValue() && Byte2BooleanLinkedOpenHashMap.this.value[this.index] == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  993 */       return Byte2BooleanLinkedOpenHashMap.this.key[this.index] ^ (Byte2BooleanLinkedOpenHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  997 */       return Byte2BooleanLinkedOpenHashMap.this.key[this.index] + "=>" + Byte2BooleanLinkedOpenHashMap.this.value[this.index];
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
/* 1008 */     if (this.size == 0) {
/* 1009 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1012 */     if (this.first == i) {
/* 1013 */       this.first = (int)this.link[i];
/* 1014 */       if (0 <= this.first)
/*      */       {
/* 1016 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1020 */     if (this.last == i) {
/* 1021 */       this.last = (int)(this.link[i] >>> 32L);
/* 1022 */       if (0 <= this.last)
/*      */       {
/* 1024 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1028 */     long linki = this.link[i];
/* 1029 */     int prev = (int)(linki >>> 32L);
/* 1030 */     int next = (int)linki;
/* 1031 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1032 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1045 */     if (this.size == 1) {
/* 1046 */       this.first = this.last = d;
/*      */       
/* 1048 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1051 */     if (this.first == s) {
/* 1052 */       this.first = d;
/* 1053 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1054 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1057 */     if (this.last == s) {
/* 1058 */       this.last = d;
/* 1059 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1060 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1063 */     long links = this.link[s];
/* 1064 */     int prev = (int)(links >>> 32L);
/* 1065 */     int next = (int)links;
/* 1066 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1067 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1068 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte firstByteKey() {
/* 1077 */     if (this.size == 0)
/* 1078 */       throw new NoSuchElementException(); 
/* 1079 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte lastByteKey() {
/* 1088 */     if (this.size == 0)
/* 1089 */       throw new NoSuchElementException(); 
/* 1090 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanSortedMap tailMap(byte from) {
/* 1099 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanSortedMap headMap(byte to) {
/* 1108 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2BooleanSortedMap subMap(byte from, byte to) {
/* 1117 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteComparator comparator() {
/* 1126 */     return null;
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
/* 1141 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1147 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1152 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1158 */     int index = -1;
/*      */     protected MapIterator() {
/* 1160 */       this.next = Byte2BooleanLinkedOpenHashMap.this.first;
/* 1161 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(byte from) {
/* 1164 */       if (from == 0) {
/* 1165 */         if (Byte2BooleanLinkedOpenHashMap.this.containsNullKey) {
/* 1166 */           this.next = (int)Byte2BooleanLinkedOpenHashMap.this.link[Byte2BooleanLinkedOpenHashMap.this.n];
/* 1167 */           this.prev = Byte2BooleanLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1170 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1172 */       if (Byte2BooleanLinkedOpenHashMap.this.key[Byte2BooleanLinkedOpenHashMap.this.last] == from) {
/* 1173 */         this.prev = Byte2BooleanLinkedOpenHashMap.this.last;
/* 1174 */         this.index = Byte2BooleanLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1178 */       int pos = HashCommon.mix(from) & Byte2BooleanLinkedOpenHashMap.this.mask;
/*      */       
/* 1180 */       while (Byte2BooleanLinkedOpenHashMap.this.key[pos] != 0) {
/* 1181 */         if (Byte2BooleanLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1183 */           this.next = (int)Byte2BooleanLinkedOpenHashMap.this.link[pos];
/* 1184 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1187 */         pos = pos + 1 & Byte2BooleanLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1189 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1192 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1195 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1198 */       if (this.index >= 0)
/*      */         return; 
/* 1200 */       if (this.prev == -1) {
/* 1201 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1204 */       if (this.next == -1) {
/* 1205 */         this.index = Byte2BooleanLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1208 */       int pos = Byte2BooleanLinkedOpenHashMap.this.first;
/* 1209 */       this.index = 1;
/* 1210 */       while (pos != this.prev) {
/* 1211 */         pos = (int)Byte2BooleanLinkedOpenHashMap.this.link[pos];
/* 1212 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1216 */       ensureIndexKnown();
/* 1217 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1220 */       ensureIndexKnown();
/* 1221 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1224 */       if (!hasNext())
/* 1225 */         throw new NoSuchElementException(); 
/* 1226 */       this.curr = this.next;
/* 1227 */       this.next = (int)Byte2BooleanLinkedOpenHashMap.this.link[this.curr];
/* 1228 */       this.prev = this.curr;
/* 1229 */       if (this.index >= 0)
/* 1230 */         this.index++; 
/* 1231 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1234 */       if (!hasPrevious())
/* 1235 */         throw new NoSuchElementException(); 
/* 1236 */       this.curr = this.prev;
/* 1237 */       this.prev = (int)(Byte2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1238 */       this.next = this.curr;
/* 1239 */       if (this.index >= 0)
/* 1240 */         this.index--; 
/* 1241 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1244 */       ensureIndexKnown();
/* 1245 */       if (this.curr == -1)
/* 1246 */         throw new IllegalStateException(); 
/* 1247 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1252 */         this.index--;
/* 1253 */         this.prev = (int)(Byte2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1255 */         this.next = (int)Byte2BooleanLinkedOpenHashMap.this.link[this.curr];
/* 1256 */       }  Byte2BooleanLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1261 */       if (this.prev == -1) {
/* 1262 */         Byte2BooleanLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1264 */         Byte2BooleanLinkedOpenHashMap.this.link[this.prev] = Byte2BooleanLinkedOpenHashMap.this.link[this.prev] ^ (Byte2BooleanLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1265 */       }  if (this.next == -1) {
/* 1266 */         Byte2BooleanLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1268 */         Byte2BooleanLinkedOpenHashMap.this.link[this.next] = Byte2BooleanLinkedOpenHashMap.this.link[this.next] ^ (Byte2BooleanLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1269 */       }  int pos = this.curr;
/* 1270 */       this.curr = -1;
/* 1271 */       if (pos == Byte2BooleanLinkedOpenHashMap.this.n) {
/* 1272 */         Byte2BooleanLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1275 */         byte[] key = Byte2BooleanLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           byte curr;
/*      */           int last;
/* 1279 */           pos = (last = pos) + 1 & Byte2BooleanLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1281 */             if ((curr = key[pos]) == 0) {
/* 1282 */               key[last] = 0;
/*      */               return;
/*      */             } 
/* 1285 */             int slot = HashCommon.mix(curr) & Byte2BooleanLinkedOpenHashMap.this.mask;
/* 1286 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1288 */             pos = pos + 1 & Byte2BooleanLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1290 */           key[last] = curr;
/* 1291 */           Byte2BooleanLinkedOpenHashMap.this.value[last] = Byte2BooleanLinkedOpenHashMap.this.value[pos];
/* 1292 */           if (this.next == pos)
/* 1293 */             this.next = last; 
/* 1294 */           if (this.prev == pos)
/* 1295 */             this.prev = last; 
/* 1296 */           Byte2BooleanLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1301 */       int i = n;
/* 1302 */       while (i-- != 0 && hasNext())
/* 1303 */         nextEntry(); 
/* 1304 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1307 */       int i = n;
/* 1308 */       while (i-- != 0 && hasPrevious())
/* 1309 */         previousEntry(); 
/* 1310 */       return n - i - 1;
/*      */     }
/*      */     public void set(Byte2BooleanMap.Entry ok) {
/* 1313 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Byte2BooleanMap.Entry ok) {
/* 1316 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Byte2BooleanMap.Entry> { private Byte2BooleanLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(byte from) {
/* 1324 */       super(from);
/*      */     }
/*      */     
/*      */     public Byte2BooleanLinkedOpenHashMap.MapEntry next() {
/* 1328 */       return this.entry = new Byte2BooleanLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Byte2BooleanLinkedOpenHashMap.MapEntry previous() {
/* 1332 */       return this.entry = new Byte2BooleanLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1336 */       super.remove();
/* 1337 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1341 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Byte2BooleanMap.Entry> { final Byte2BooleanLinkedOpenHashMap.MapEntry entry = new Byte2BooleanLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(byte from) {
/* 1345 */       super(from);
/*      */     }
/*      */     
/*      */     public Byte2BooleanLinkedOpenHashMap.MapEntry next() {
/* 1349 */       this.entry.index = nextEntry();
/* 1350 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Byte2BooleanLinkedOpenHashMap.MapEntry previous() {
/* 1354 */       this.entry.index = previousEntry();
/* 1355 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Byte2BooleanMap.Entry> implements Byte2BooleanSortedMap.FastSortedEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Byte2BooleanMap.Entry> iterator() {
/* 1363 */       return (ObjectBidirectionalIterator<Byte2BooleanMap.Entry>)new Byte2BooleanLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Byte2BooleanMap.Entry> comparator() {
/* 1367 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Byte2BooleanMap.Entry> subSet(Byte2BooleanMap.Entry fromElement, Byte2BooleanMap.Entry toElement) {
/* 1372 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Byte2BooleanMap.Entry> headSet(Byte2BooleanMap.Entry toElement) {
/* 1376 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Byte2BooleanMap.Entry> tailSet(Byte2BooleanMap.Entry fromElement) {
/* 1380 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Byte2BooleanMap.Entry first() {
/* 1384 */       if (Byte2BooleanLinkedOpenHashMap.this.size == 0)
/* 1385 */         throw new NoSuchElementException(); 
/* 1386 */       return new Byte2BooleanLinkedOpenHashMap.MapEntry(Byte2BooleanLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Byte2BooleanMap.Entry last() {
/* 1390 */       if (Byte2BooleanLinkedOpenHashMap.this.size == 0)
/* 1391 */         throw new NoSuchElementException(); 
/* 1392 */       return new Byte2BooleanLinkedOpenHashMap.MapEntry(Byte2BooleanLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1397 */       if (!(o instanceof Map.Entry))
/* 1398 */         return false; 
/* 1399 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1400 */       if (e.getKey() == null || !(e.getKey() instanceof Byte))
/* 1401 */         return false; 
/* 1402 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1403 */         return false; 
/* 1404 */       byte k = ((Byte)e.getKey()).byteValue();
/* 1405 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1406 */       if (k == 0) {
/* 1407 */         return (Byte2BooleanLinkedOpenHashMap.this.containsNullKey && Byte2BooleanLinkedOpenHashMap.this.value[Byte2BooleanLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1409 */       byte[] key = Byte2BooleanLinkedOpenHashMap.this.key;
/*      */       byte curr;
/*      */       int pos;
/* 1412 */       if ((curr = key[pos = HashCommon.mix(k) & Byte2BooleanLinkedOpenHashMap.this.mask]) == 0)
/* 1413 */         return false; 
/* 1414 */       if (k == curr) {
/* 1415 */         return (Byte2BooleanLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1418 */         if ((curr = key[pos = pos + 1 & Byte2BooleanLinkedOpenHashMap.this.mask]) == 0)
/* 1419 */           return false; 
/* 1420 */         if (k == curr) {
/* 1421 */           return (Byte2BooleanLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1427 */       if (!(o instanceof Map.Entry))
/* 1428 */         return false; 
/* 1429 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1430 */       if (e.getKey() == null || !(e.getKey() instanceof Byte))
/* 1431 */         return false; 
/* 1432 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1433 */         return false; 
/* 1434 */       byte k = ((Byte)e.getKey()).byteValue();
/* 1435 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1436 */       if (k == 0) {
/* 1437 */         if (Byte2BooleanLinkedOpenHashMap.this.containsNullKey && Byte2BooleanLinkedOpenHashMap.this.value[Byte2BooleanLinkedOpenHashMap.this.n] == v) {
/* 1438 */           Byte2BooleanLinkedOpenHashMap.this.removeNullEntry();
/* 1439 */           return true;
/*      */         } 
/* 1441 */         return false;
/*      */       } 
/*      */       
/* 1444 */       byte[] key = Byte2BooleanLinkedOpenHashMap.this.key;
/*      */       byte curr;
/*      */       int pos;
/* 1447 */       if ((curr = key[pos = HashCommon.mix(k) & Byte2BooleanLinkedOpenHashMap.this.mask]) == 0)
/* 1448 */         return false; 
/* 1449 */       if (curr == k) {
/* 1450 */         if (Byte2BooleanLinkedOpenHashMap.this.value[pos] == v) {
/* 1451 */           Byte2BooleanLinkedOpenHashMap.this.removeEntry(pos);
/* 1452 */           return true;
/*      */         } 
/* 1454 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1457 */         if ((curr = key[pos = pos + 1 & Byte2BooleanLinkedOpenHashMap.this.mask]) == 0)
/* 1458 */           return false; 
/* 1459 */         if (curr == k && 
/* 1460 */           Byte2BooleanLinkedOpenHashMap.this.value[pos] == v) {
/* 1461 */           Byte2BooleanLinkedOpenHashMap.this.removeEntry(pos);
/* 1462 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1469 */       return Byte2BooleanLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1473 */       Byte2BooleanLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Byte2BooleanMap.Entry> iterator(Byte2BooleanMap.Entry from) {
/* 1488 */       return new Byte2BooleanLinkedOpenHashMap.EntryIterator(from.getByteKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Byte2BooleanMap.Entry> fastIterator() {
/* 1499 */       return new Byte2BooleanLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Byte2BooleanMap.Entry> fastIterator(Byte2BooleanMap.Entry from) {
/* 1514 */       return new Byte2BooleanLinkedOpenHashMap.FastEntryIterator(from.getByteKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Byte2BooleanMap.Entry> consumer) {
/* 1519 */       for (int i = Byte2BooleanLinkedOpenHashMap.this.size, next = Byte2BooleanLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1520 */         int curr = next;
/* 1521 */         next = (int)Byte2BooleanLinkedOpenHashMap.this.link[curr];
/* 1522 */         consumer.accept(new AbstractByte2BooleanMap.BasicEntry(Byte2BooleanLinkedOpenHashMap.this.key[curr], Byte2BooleanLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Byte2BooleanMap.Entry> consumer) {
/* 1528 */       AbstractByte2BooleanMap.BasicEntry entry = new AbstractByte2BooleanMap.BasicEntry();
/* 1529 */       for (int i = Byte2BooleanLinkedOpenHashMap.this.size, next = Byte2BooleanLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1530 */         int curr = next;
/* 1531 */         next = (int)Byte2BooleanLinkedOpenHashMap.this.link[curr];
/* 1532 */         entry.key = Byte2BooleanLinkedOpenHashMap.this.key[curr];
/* 1533 */         entry.value = Byte2BooleanLinkedOpenHashMap.this.value[curr];
/* 1534 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Byte2BooleanSortedMap.FastSortedEntrySet byte2BooleanEntrySet() {
/* 1540 */     if (this.entries == null)
/* 1541 */       this.entries = new MapEntrySet(); 
/* 1542 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends MapIterator
/*      */     implements ByteListIterator
/*      */   {
/*      */     public KeyIterator(byte k) {
/* 1555 */       super(k);
/*      */     }
/*      */     
/*      */     public byte previousByte() {
/* 1559 */       return Byte2BooleanLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public byte nextByte() {
/* 1566 */       return Byte2BooleanLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractByteSortedSet { private KeySet() {}
/*      */     
/*      */     public ByteListIterator iterator(byte from) {
/* 1572 */       return new Byte2BooleanLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ByteListIterator iterator() {
/* 1576 */       return new Byte2BooleanLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1581 */       if (Byte2BooleanLinkedOpenHashMap.this.containsNullKey)
/* 1582 */         consumer.accept(Byte2BooleanLinkedOpenHashMap.this.key[Byte2BooleanLinkedOpenHashMap.this.n]); 
/* 1583 */       for (int pos = Byte2BooleanLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1584 */         byte k = Byte2BooleanLinkedOpenHashMap.this.key[pos];
/* 1585 */         if (k != 0)
/* 1586 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1591 */       return Byte2BooleanLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(byte k) {
/* 1595 */       return Byte2BooleanLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(byte k) {
/* 1599 */       int oldSize = Byte2BooleanLinkedOpenHashMap.this.size;
/* 1600 */       Byte2BooleanLinkedOpenHashMap.this.remove(k);
/* 1601 */       return (Byte2BooleanLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1605 */       Byte2BooleanLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public byte firstByte() {
/* 1609 */       if (Byte2BooleanLinkedOpenHashMap.this.size == 0)
/* 1610 */         throw new NoSuchElementException(); 
/* 1611 */       return Byte2BooleanLinkedOpenHashMap.this.key[Byte2BooleanLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public byte lastByte() {
/* 1615 */       if (Byte2BooleanLinkedOpenHashMap.this.size == 0)
/* 1616 */         throw new NoSuchElementException(); 
/* 1617 */       return Byte2BooleanLinkedOpenHashMap.this.key[Byte2BooleanLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public ByteComparator comparator() {
/* 1621 */       return null;
/*      */     }
/*      */     
/*      */     public ByteSortedSet tailSet(byte from) {
/* 1625 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ByteSortedSet headSet(byte to) {
/* 1629 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ByteSortedSet subSet(byte from, byte to) {
/* 1633 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ByteSortedSet keySet() {
/* 1638 */     if (this.keys == null)
/* 1639 */       this.keys = new KeySet(); 
/* 1640 */     return this.keys;
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
/*      */     implements BooleanListIterator
/*      */   {
/*      */     public boolean previousBoolean() {
/* 1654 */       return Byte2BooleanLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1661 */       return Byte2BooleanLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public BooleanCollection values() {
/* 1666 */     if (this.values == null)
/* 1667 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1670 */             return (BooleanIterator)new Byte2BooleanLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1674 */             return Byte2BooleanLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1678 */             return Byte2BooleanLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1682 */             Byte2BooleanLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(BooleanConsumer consumer) {
/* 1687 */             if (Byte2BooleanLinkedOpenHashMap.this.containsNullKey)
/* 1688 */               consumer.accept(Byte2BooleanLinkedOpenHashMap.this.value[Byte2BooleanLinkedOpenHashMap.this.n]); 
/* 1689 */             for (int pos = Byte2BooleanLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1690 */               if (Byte2BooleanLinkedOpenHashMap.this.key[pos] != 0)
/* 1691 */                 consumer.accept(Byte2BooleanLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1694 */     return this.values;
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
/* 1711 */     return trim(this.size);
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
/* 1735 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1736 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1737 */       return true; 
/*      */     try {
/* 1739 */       rehash(l);
/* 1740 */     } catch (OutOfMemoryError cantDoIt) {
/* 1741 */       return false;
/*      */     } 
/* 1743 */     return true;
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
/* 1759 */     byte[] key = this.key;
/* 1760 */     boolean[] value = this.value;
/* 1761 */     int mask = newN - 1;
/* 1762 */     byte[] newKey = new byte[newN + 1];
/* 1763 */     boolean[] newValue = new boolean[newN + 1];
/* 1764 */     int i = this.first, prev = -1, newPrev = -1;
/* 1765 */     long[] link = this.link;
/* 1766 */     long[] newLink = new long[newN + 1];
/* 1767 */     this.first = -1;
/* 1768 */     for (int j = this.size; j-- != 0; ) {
/* 1769 */       int pos; if (key[i] == 0) {
/* 1770 */         pos = newN;
/*      */       } else {
/* 1772 */         pos = HashCommon.mix(key[i]) & mask;
/* 1773 */         while (newKey[pos] != 0)
/* 1774 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1776 */       newKey[pos] = key[i];
/* 1777 */       newValue[pos] = value[i];
/* 1778 */       if (prev != -1) {
/* 1779 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1780 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1781 */         newPrev = pos;
/*      */       } else {
/* 1783 */         newPrev = this.first = pos;
/*      */         
/* 1785 */         newLink[pos] = -1L;
/*      */       } 
/* 1787 */       int t = i;
/* 1788 */       i = (int)link[i];
/* 1789 */       prev = t;
/*      */     } 
/* 1791 */     this.link = newLink;
/* 1792 */     this.last = newPrev;
/* 1793 */     if (newPrev != -1)
/*      */     {
/* 1795 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1796 */     this.n = newN;
/* 1797 */     this.mask = mask;
/* 1798 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1799 */     this.key = newKey;
/* 1800 */     this.value = newValue;
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
/*      */   public Byte2BooleanLinkedOpenHashMap clone() {
/*      */     Byte2BooleanLinkedOpenHashMap c;
/*      */     try {
/* 1817 */       c = (Byte2BooleanLinkedOpenHashMap)super.clone();
/* 1818 */     } catch (CloneNotSupportedException cantHappen) {
/* 1819 */       throw new InternalError();
/*      */     } 
/* 1821 */     c.keys = null;
/* 1822 */     c.values = null;
/* 1823 */     c.entries = null;
/* 1824 */     c.containsNullKey = this.containsNullKey;
/* 1825 */     c.key = (byte[])this.key.clone();
/* 1826 */     c.value = (boolean[])this.value.clone();
/* 1827 */     c.link = (long[])this.link.clone();
/* 1828 */     return c;
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
/* 1841 */     int h = 0;
/* 1842 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1843 */       while (this.key[i] == 0)
/* 1844 */         i++; 
/* 1845 */       t = this.key[i];
/* 1846 */       t ^= this.value[i] ? 1231 : 1237;
/* 1847 */       h += t;
/* 1848 */       i++;
/*      */     } 
/*      */     
/* 1851 */     if (this.containsNullKey)
/* 1852 */       h += this.value[this.n] ? 1231 : 1237; 
/* 1853 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1856 */     byte[] key = this.key;
/* 1857 */     boolean[] value = this.value;
/* 1858 */     MapIterator i = new MapIterator();
/* 1859 */     s.defaultWriteObject();
/* 1860 */     for (int j = this.size; j-- != 0; ) {
/* 1861 */       int e = i.nextEntry();
/* 1862 */       s.writeByte(key[e]);
/* 1863 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1868 */     s.defaultReadObject();
/* 1869 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1870 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1871 */     this.mask = this.n - 1;
/* 1872 */     byte[] key = this.key = new byte[this.n + 1];
/* 1873 */     boolean[] value = this.value = new boolean[this.n + 1];
/* 1874 */     long[] link = this.link = new long[this.n + 1];
/* 1875 */     int prev = -1;
/* 1876 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1879 */     for (int i = this.size; i-- != 0; ) {
/* 1880 */       int pos; byte k = s.readByte();
/* 1881 */       boolean v = s.readBoolean();
/* 1882 */       if (k == 0) {
/* 1883 */         pos = this.n;
/* 1884 */         this.containsNullKey = true;
/*      */       } else {
/* 1886 */         pos = HashCommon.mix(k) & this.mask;
/* 1887 */         while (key[pos] != 0)
/* 1888 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1890 */       key[pos] = k;
/* 1891 */       value[pos] = v;
/* 1892 */       if (this.first != -1) {
/* 1893 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1894 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1895 */         prev = pos; continue;
/*      */       } 
/* 1897 */       prev = this.first = pos;
/*      */       
/* 1899 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1902 */     this.last = prev;
/* 1903 */     if (prev != -1)
/*      */     {
/* 1905 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2BooleanLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */