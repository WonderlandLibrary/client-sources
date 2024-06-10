/*      */ package it.unimi.dsi.fastutil.doubles;
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
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.DoubleFunction;
/*      */ import java.util.function.DoubleToIntFunction;
/*      */ import java.util.function.IntConsumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Double2ByteLinkedOpenHashMap
/*      */   extends AbstractDouble2ByteSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
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
/*      */   protected transient Double2ByteSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient DoubleSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ByteCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ByteLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new double[this.n + 1];
/*  162 */     this.value = new byte[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ByteLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ByteLinkedOpenHashMap() {
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
/*      */   public Double2ByteLinkedOpenHashMap(Map<? extends Double, ? extends Byte> m, float f) {
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
/*      */   public Double2ByteLinkedOpenHashMap(Map<? extends Double, ? extends Byte> m) {
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
/*      */   public Double2ByteLinkedOpenHashMap(Double2ByteMap m, float f) {
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
/*      */   public Double2ByteLinkedOpenHashMap(Double2ByteMap m) {
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
/*      */   public Double2ByteLinkedOpenHashMap(double[] k, byte[] v, float f) {
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
/*      */   public Double2ByteLinkedOpenHashMap(double[] k, byte[] v) {
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
/*      */   public void putAll(Map<? extends Double, ? extends Byte> m) {
/*  294 */     if (this.f <= 0.5D) {
/*  295 */       ensureCapacity(m.size());
/*      */     } else {
/*  297 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  299 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(double k) {
/*  303 */     if (Double.doubleToLongBits(k) == 0L) {
/*  304 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  306 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  309 */     if (Double.doubleToLongBits(
/*  310 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  312 */       return -(pos + 1); } 
/*  313 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  314 */       return pos;
/*      */     }
/*      */     while (true) {
/*  317 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  318 */         return -(pos + 1); 
/*  319 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  320 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, double k, byte v) {
/*  324 */     if (pos == this.n)
/*  325 */       this.containsNullKey = true; 
/*  326 */     this.key[pos] = k;
/*  327 */     this.value[pos] = v;
/*  328 */     if (this.size == 0) {
/*  329 */       this.first = this.last = pos;
/*      */       
/*  331 */       this.link[pos] = -1L;
/*      */     } else {
/*  333 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  334 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  335 */       this.last = pos;
/*      */     } 
/*  337 */     if (this.size++ >= this.maxFill) {
/*  338 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public byte put(double k, byte v) {
/*  344 */     int pos = find(k);
/*  345 */     if (pos < 0) {
/*  346 */       insert(-pos - 1, k, v);
/*  347 */       return this.defRetValue;
/*      */     } 
/*  349 */     byte oldValue = this.value[pos];
/*  350 */     this.value[pos] = v;
/*  351 */     return oldValue;
/*      */   }
/*      */   private byte addToValue(int pos, byte incr) {
/*  354 */     byte oldValue = this.value[pos];
/*  355 */     this.value[pos] = (byte)(oldValue + incr);
/*  356 */     return oldValue;
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
/*      */   public byte addTo(double k, byte incr) {
/*      */     int pos;
/*  376 */     if (Double.doubleToLongBits(k) == 0L) {
/*  377 */       if (this.containsNullKey)
/*  378 */         return addToValue(this.n, incr); 
/*  379 */       pos = this.n;
/*  380 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  383 */       double[] key = this.key;
/*      */       double curr;
/*  385 */       if (Double.doubleToLongBits(
/*  386 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
/*      */         
/*  388 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k))
/*  389 */           return addToValue(pos, incr); 
/*  390 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  391 */           if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k))
/*  392 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  395 */     }  this.key[pos] = k;
/*  396 */     this.value[pos] = (byte)(this.defRetValue + incr);
/*  397 */     if (this.size == 0) {
/*  398 */       this.first = this.last = pos;
/*      */       
/*  400 */       this.link[pos] = -1L;
/*      */     } else {
/*  402 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  403 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  404 */       this.last = pos;
/*      */     } 
/*  406 */     if (this.size++ >= this.maxFill) {
/*  407 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  410 */     return this.defRetValue;
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
/*  423 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  425 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  427 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  428 */           key[last] = 0.0D;
/*      */           return;
/*      */         } 
/*  431 */         int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & this.mask;
/*  432 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  434 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  436 */       key[last] = curr;
/*  437 */       this.value[last] = this.value[pos];
/*  438 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public byte remove(double k) {
/*  444 */     if (Double.doubleToLongBits(k) == 0L) {
/*  445 */       if (this.containsNullKey)
/*  446 */         return removeNullEntry(); 
/*  447 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  450 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  453 */     if (Double.doubleToLongBits(
/*  454 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  456 */       return this.defRetValue; } 
/*  457 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  458 */       return removeEntry(pos); 
/*      */     while (true) {
/*  460 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  461 */         return this.defRetValue; 
/*  462 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  463 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private byte setValue(int pos, byte v) {
/*  467 */     byte oldValue = this.value[pos];
/*  468 */     this.value[pos] = v;
/*  469 */     return oldValue;
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
/*  480 */     if (this.size == 0)
/*  481 */       throw new NoSuchElementException(); 
/*  482 */     int pos = this.first;
/*      */     
/*  484 */     this.first = (int)this.link[pos];
/*  485 */     if (0 <= this.first)
/*      */     {
/*  487 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  489 */     this.size--;
/*  490 */     byte v = this.value[pos];
/*  491 */     if (pos == this.n) {
/*  492 */       this.containsNullKey = false;
/*      */     } else {
/*  494 */       shiftKeys(pos);
/*  495 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  496 */       rehash(this.n / 2); 
/*  497 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte removeLastByte() {
/*  507 */     if (this.size == 0)
/*  508 */       throw new NoSuchElementException(); 
/*  509 */     int pos = this.last;
/*      */     
/*  511 */     this.last = (int)(this.link[pos] >>> 32L);
/*  512 */     if (0 <= this.last)
/*      */     {
/*  514 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  516 */     this.size--;
/*  517 */     byte v = this.value[pos];
/*  518 */     if (pos == this.n) {
/*  519 */       this.containsNullKey = false;
/*      */     } else {
/*  521 */       shiftKeys(pos);
/*  522 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  523 */       rehash(this.n / 2); 
/*  524 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  527 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  529 */     if (this.last == i) {
/*  530 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  532 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  534 */       long linki = this.link[i];
/*  535 */       int prev = (int)(linki >>> 32L);
/*  536 */       int next = (int)linki;
/*  537 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  538 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  540 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  541 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  542 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  545 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  547 */     if (this.first == i) {
/*  548 */       this.first = (int)this.link[i];
/*      */       
/*  550 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  552 */       long linki = this.link[i];
/*  553 */       int prev = (int)(linki >>> 32L);
/*  554 */       int next = (int)linki;
/*  555 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  556 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  558 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  559 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  560 */     this.last = i;
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
/*      */   public byte getAndMoveToFirst(double k) {
/*  572 */     if (Double.doubleToLongBits(k) == 0L) {
/*  573 */       if (this.containsNullKey) {
/*  574 */         moveIndexToFirst(this.n);
/*  575 */         return this.value[this.n];
/*      */       } 
/*  577 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  580 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  583 */     if (Double.doubleToLongBits(
/*  584 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  586 */       return this.defRetValue; } 
/*  587 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  588 */       moveIndexToFirst(pos);
/*  589 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  593 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  594 */         return this.defRetValue; 
/*  595 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  596 */         moveIndexToFirst(pos);
/*  597 */         return this.value[pos];
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
/*      */   public byte getAndMoveToLast(double k) {
/*  611 */     if (Double.doubleToLongBits(k) == 0L) {
/*  612 */       if (this.containsNullKey) {
/*  613 */         moveIndexToLast(this.n);
/*  614 */         return this.value[this.n];
/*      */       } 
/*  616 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  619 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  622 */     if (Double.doubleToLongBits(
/*  623 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  625 */       return this.defRetValue; } 
/*  626 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  627 */       moveIndexToLast(pos);
/*  628 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  632 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  633 */         return this.defRetValue; 
/*  634 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  635 */         moveIndexToLast(pos);
/*  636 */         return this.value[pos];
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
/*      */   public byte putAndMoveToFirst(double k, byte v) {
/*      */     int pos;
/*  653 */     if (Double.doubleToLongBits(k) == 0L) {
/*  654 */       if (this.containsNullKey) {
/*  655 */         moveIndexToFirst(this.n);
/*  656 */         return setValue(this.n, v);
/*      */       } 
/*  658 */       this.containsNullKey = true;
/*  659 */       pos = this.n;
/*      */     } else {
/*      */       
/*  662 */       double[] key = this.key;
/*      */       double curr;
/*  664 */       if (Double.doubleToLongBits(
/*  665 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
/*      */         
/*  667 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  668 */           moveIndexToFirst(pos);
/*  669 */           return setValue(pos, v);
/*      */         } 
/*  671 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  672 */           if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  673 */             moveIndexToFirst(pos);
/*  674 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  678 */     }  this.key[pos] = k;
/*  679 */     this.value[pos] = v;
/*  680 */     if (this.size == 0) {
/*  681 */       this.first = this.last = pos;
/*      */       
/*  683 */       this.link[pos] = -1L;
/*      */     } else {
/*  685 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  686 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  687 */       this.first = pos;
/*      */     } 
/*  689 */     if (this.size++ >= this.maxFill) {
/*  690 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  693 */     return this.defRetValue;
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
/*      */   public byte putAndMoveToLast(double k, byte v) {
/*      */     int pos;
/*  708 */     if (Double.doubleToLongBits(k) == 0L) {
/*  709 */       if (this.containsNullKey) {
/*  710 */         moveIndexToLast(this.n);
/*  711 */         return setValue(this.n, v);
/*      */       } 
/*  713 */       this.containsNullKey = true;
/*  714 */       pos = this.n;
/*      */     } else {
/*      */       
/*  717 */       double[] key = this.key;
/*      */       double curr;
/*  719 */       if (Double.doubleToLongBits(
/*  720 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
/*      */         
/*  722 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  723 */           moveIndexToLast(pos);
/*  724 */           return setValue(pos, v);
/*      */         } 
/*  726 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  727 */           if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  728 */             moveIndexToLast(pos);
/*  729 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  733 */     }  this.key[pos] = k;
/*  734 */     this.value[pos] = v;
/*  735 */     if (this.size == 0) {
/*  736 */       this.first = this.last = pos;
/*      */       
/*  738 */       this.link[pos] = -1L;
/*      */     } else {
/*  740 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  741 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  742 */       this.last = pos;
/*      */     } 
/*  744 */     if (this.size++ >= this.maxFill) {
/*  745 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  748 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte get(double k) {
/*  753 */     if (Double.doubleToLongBits(k) == 0L) {
/*  754 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  756 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  759 */     if (Double.doubleToLongBits(
/*  760 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  762 */       return this.defRetValue; } 
/*  763 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  764 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  767 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  768 */         return this.defRetValue; 
/*  769 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  770 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(double k) {
/*  776 */     if (Double.doubleToLongBits(k) == 0L) {
/*  777 */       return this.containsNullKey;
/*      */     }
/*  779 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  782 */     if (Double.doubleToLongBits(
/*  783 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  785 */       return false; } 
/*  786 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  787 */       return true;
/*      */     }
/*      */     while (true) {
/*  790 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  791 */         return false; 
/*  792 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  793 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(byte v) {
/*  798 */     byte[] value = this.value;
/*  799 */     double[] key = this.key;
/*  800 */     if (this.containsNullKey && value[this.n] == v)
/*  801 */       return true; 
/*  802 */     for (int i = this.n; i-- != 0;) {
/*  803 */       if (Double.doubleToLongBits(key[i]) != 0L && value[i] == v)
/*  804 */         return true; 
/*  805 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getOrDefault(double k, byte defaultValue) {
/*  811 */     if (Double.doubleToLongBits(k) == 0L) {
/*  812 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  814 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  817 */     if (Double.doubleToLongBits(
/*  818 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  820 */       return defaultValue; } 
/*  821 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  822 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  825 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  826 */         return defaultValue; 
/*  827 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  828 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public byte putIfAbsent(double k, byte v) {
/*  834 */     int pos = find(k);
/*  835 */     if (pos >= 0)
/*  836 */       return this.value[pos]; 
/*  837 */     insert(-pos - 1, k, v);
/*  838 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, byte v) {
/*  844 */     if (Double.doubleToLongBits(k) == 0L) {
/*  845 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  846 */         removeNullEntry();
/*  847 */         return true;
/*      */       } 
/*  849 */       return false;
/*      */     } 
/*      */     
/*  852 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  855 */     if (Double.doubleToLongBits(
/*  856 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  858 */       return false; } 
/*  859 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  860 */       removeEntry(pos);
/*  861 */       return true;
/*      */     } 
/*      */     while (true) {
/*  864 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  865 */         return false; 
/*  866 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  867 */         removeEntry(pos);
/*  868 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, byte oldValue, byte v) {
/*  875 */     int pos = find(k);
/*  876 */     if (pos < 0 || oldValue != this.value[pos])
/*  877 */       return false; 
/*  878 */     this.value[pos] = v;
/*  879 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte replace(double k, byte v) {
/*  884 */     int pos = find(k);
/*  885 */     if (pos < 0)
/*  886 */       return this.defRetValue; 
/*  887 */     byte oldValue = this.value[pos];
/*  888 */     this.value[pos] = v;
/*  889 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte computeIfAbsent(double k, DoubleToIntFunction mappingFunction) {
/*  894 */     Objects.requireNonNull(mappingFunction);
/*  895 */     int pos = find(k);
/*  896 */     if (pos >= 0)
/*  897 */       return this.value[pos]; 
/*  898 */     byte newValue = SafeMath.safeIntToByte(mappingFunction.applyAsInt(k));
/*  899 */     insert(-pos - 1, k, newValue);
/*  900 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsentNullable(double k, DoubleFunction<? extends Byte> mappingFunction) {
/*  906 */     Objects.requireNonNull(mappingFunction);
/*  907 */     int pos = find(k);
/*  908 */     if (pos >= 0)
/*  909 */       return this.value[pos]; 
/*  910 */     Byte newValue = mappingFunction.apply(k);
/*  911 */     if (newValue == null)
/*  912 */       return this.defRetValue; 
/*  913 */     byte v = newValue.byteValue();
/*  914 */     insert(-pos - 1, k, v);
/*  915 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfPresent(double k, BiFunction<? super Double, ? super Byte, ? extends Byte> remappingFunction) {
/*  921 */     Objects.requireNonNull(remappingFunction);
/*  922 */     int pos = find(k);
/*  923 */     if (pos < 0)
/*  924 */       return this.defRetValue; 
/*  925 */     Byte newValue = remappingFunction.apply(Double.valueOf(k), Byte.valueOf(this.value[pos]));
/*  926 */     if (newValue == null) {
/*  927 */       if (Double.doubleToLongBits(k) == 0L) {
/*  928 */         removeNullEntry();
/*      */       } else {
/*  930 */         removeEntry(pos);
/*  931 */       }  return this.defRetValue;
/*      */     } 
/*  933 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte compute(double k, BiFunction<? super Double, ? super Byte, ? extends Byte> remappingFunction) {
/*  939 */     Objects.requireNonNull(remappingFunction);
/*  940 */     int pos = find(k);
/*  941 */     Byte newValue = remappingFunction.apply(Double.valueOf(k), (pos >= 0) ? Byte.valueOf(this.value[pos]) : null);
/*  942 */     if (newValue == null) {
/*  943 */       if (pos >= 0)
/*  944 */         if (Double.doubleToLongBits(k) == 0L) {
/*  945 */           removeNullEntry();
/*      */         } else {
/*  947 */           removeEntry(pos);
/*      */         }  
/*  949 */       return this.defRetValue;
/*      */     } 
/*  951 */     byte newVal = newValue.byteValue();
/*  952 */     if (pos < 0) {
/*  953 */       insert(-pos - 1, k, newVal);
/*  954 */       return newVal;
/*      */     } 
/*  956 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte merge(double k, byte v, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*  962 */     Objects.requireNonNull(remappingFunction);
/*  963 */     int pos = find(k);
/*  964 */     if (pos < 0) {
/*  965 */       insert(-pos - 1, k, v);
/*  966 */       return v;
/*      */     } 
/*  968 */     Byte newValue = remappingFunction.apply(Byte.valueOf(this.value[pos]), Byte.valueOf(v));
/*  969 */     if (newValue == null) {
/*  970 */       if (Double.doubleToLongBits(k) == 0L) {
/*  971 */         removeNullEntry();
/*      */       } else {
/*  973 */         removeEntry(pos);
/*  974 */       }  return this.defRetValue;
/*      */     } 
/*  976 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
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
/*  987 */     if (this.size == 0)
/*      */       return; 
/*  989 */     this.size = 0;
/*  990 */     this.containsNullKey = false;
/*  991 */     Arrays.fill(this.key, 0.0D);
/*  992 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  996 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/* 1000 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2ByteMap.Entry, Map.Entry<Double, Byte>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/* 1012 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/* 1018 */       return Double2ByteLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public byte getByteValue() {
/* 1022 */       return Double2ByteLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public byte setValue(byte v) {
/* 1026 */       byte oldValue = Double2ByteLinkedOpenHashMap.this.value[this.index];
/* 1027 */       Double2ByteLinkedOpenHashMap.this.value[this.index] = v;
/* 1028 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/* 1038 */       return Double.valueOf(Double2ByteLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte getValue() {
/* 1048 */       return Byte.valueOf(Double2ByteLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte setValue(Byte v) {
/* 1058 */       return Byte.valueOf(setValue(v.byteValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1063 */       if (!(o instanceof Map.Entry))
/* 1064 */         return false; 
/* 1065 */       Map.Entry<Double, Byte> e = (Map.Entry<Double, Byte>)o;
/* 1066 */       return (Double.doubleToLongBits(Double2ByteLinkedOpenHashMap.this.key[this.index]) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && Double2ByteLinkedOpenHashMap.this.value[this.index] == ((Byte)e
/* 1067 */         .getValue()).byteValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1071 */       return HashCommon.double2int(Double2ByteLinkedOpenHashMap.this.key[this.index]) ^ Double2ByteLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1075 */       return Double2ByteLinkedOpenHashMap.this.key[this.index] + "=>" + Double2ByteLinkedOpenHashMap.this.value[this.index];
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
/* 1086 */     if (this.size == 0) {
/* 1087 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1090 */     if (this.first == i) {
/* 1091 */       this.first = (int)this.link[i];
/* 1092 */       if (0 <= this.first)
/*      */       {
/* 1094 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1098 */     if (this.last == i) {
/* 1099 */       this.last = (int)(this.link[i] >>> 32L);
/* 1100 */       if (0 <= this.last)
/*      */       {
/* 1102 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1106 */     long linki = this.link[i];
/* 1107 */     int prev = (int)(linki >>> 32L);
/* 1108 */     int next = (int)linki;
/* 1109 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1110 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1123 */     if (this.size == 1) {
/* 1124 */       this.first = this.last = d;
/*      */       
/* 1126 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1129 */     if (this.first == s) {
/* 1130 */       this.first = d;
/* 1131 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1132 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1135 */     if (this.last == s) {
/* 1136 */       this.last = d;
/* 1137 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1138 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1141 */     long links = this.link[s];
/* 1142 */     int prev = (int)(links >>> 32L);
/* 1143 */     int next = (int)links;
/* 1144 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1145 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1146 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double firstDoubleKey() {
/* 1155 */     if (this.size == 0)
/* 1156 */       throw new NoSuchElementException(); 
/* 1157 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double lastDoubleKey() {
/* 1166 */     if (this.size == 0)
/* 1167 */       throw new NoSuchElementException(); 
/* 1168 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ByteSortedMap tailMap(double from) {
/* 1177 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ByteSortedMap headMap(double to) {
/* 1186 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ByteSortedMap subMap(double from, double to) {
/* 1195 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1204 */     return null;
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
/* 1219 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1225 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1230 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1236 */     int index = -1;
/*      */     protected MapIterator() {
/* 1238 */       this.next = Double2ByteLinkedOpenHashMap.this.first;
/* 1239 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(double from) {
/* 1242 */       if (Double.doubleToLongBits(from) == 0L) {
/* 1243 */         if (Double2ByteLinkedOpenHashMap.this.containsNullKey) {
/* 1244 */           this.next = (int)Double2ByteLinkedOpenHashMap.this.link[Double2ByteLinkedOpenHashMap.this.n];
/* 1245 */           this.prev = Double2ByteLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1248 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1250 */       if (Double.doubleToLongBits(Double2ByteLinkedOpenHashMap.this.key[Double2ByteLinkedOpenHashMap.this.last]) == Double.doubleToLongBits(from)) {
/* 1251 */         this.prev = Double2ByteLinkedOpenHashMap.this.last;
/* 1252 */         this.index = Double2ByteLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1256 */       int pos = (int)HashCommon.mix(Double.doubleToRawLongBits(from)) & Double2ByteLinkedOpenHashMap.this.mask;
/*      */       
/* 1258 */       while (Double.doubleToLongBits(Double2ByteLinkedOpenHashMap.this.key[pos]) != 0L) {
/* 1259 */         if (Double.doubleToLongBits(Double2ByteLinkedOpenHashMap.this.key[pos]) == Double.doubleToLongBits(from)) {
/*      */           
/* 1261 */           this.next = (int)Double2ByteLinkedOpenHashMap.this.link[pos];
/* 1262 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1265 */         pos = pos + 1 & Double2ByteLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1267 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1270 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1273 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1276 */       if (this.index >= 0)
/*      */         return; 
/* 1278 */       if (this.prev == -1) {
/* 1279 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1282 */       if (this.next == -1) {
/* 1283 */         this.index = Double2ByteLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1286 */       int pos = Double2ByteLinkedOpenHashMap.this.first;
/* 1287 */       this.index = 1;
/* 1288 */       while (pos != this.prev) {
/* 1289 */         pos = (int)Double2ByteLinkedOpenHashMap.this.link[pos];
/* 1290 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1294 */       ensureIndexKnown();
/* 1295 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1298 */       ensureIndexKnown();
/* 1299 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1302 */       if (!hasNext())
/* 1303 */         throw new NoSuchElementException(); 
/* 1304 */       this.curr = this.next;
/* 1305 */       this.next = (int)Double2ByteLinkedOpenHashMap.this.link[this.curr];
/* 1306 */       this.prev = this.curr;
/* 1307 */       if (this.index >= 0)
/* 1308 */         this.index++; 
/* 1309 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1312 */       if (!hasPrevious())
/* 1313 */         throw new NoSuchElementException(); 
/* 1314 */       this.curr = this.prev;
/* 1315 */       this.prev = (int)(Double2ByteLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1316 */       this.next = this.curr;
/* 1317 */       if (this.index >= 0)
/* 1318 */         this.index--; 
/* 1319 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1322 */       ensureIndexKnown();
/* 1323 */       if (this.curr == -1)
/* 1324 */         throw new IllegalStateException(); 
/* 1325 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1330 */         this.index--;
/* 1331 */         this.prev = (int)(Double2ByteLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1333 */         this.next = (int)Double2ByteLinkedOpenHashMap.this.link[this.curr];
/* 1334 */       }  Double2ByteLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1339 */       if (this.prev == -1) {
/* 1340 */         Double2ByteLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1342 */         Double2ByteLinkedOpenHashMap.this.link[this.prev] = Double2ByteLinkedOpenHashMap.this.link[this.prev] ^ (Double2ByteLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1343 */       }  if (this.next == -1) {
/* 1344 */         Double2ByteLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1346 */         Double2ByteLinkedOpenHashMap.this.link[this.next] = Double2ByteLinkedOpenHashMap.this.link[this.next] ^ (Double2ByteLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1347 */       }  int pos = this.curr;
/* 1348 */       this.curr = -1;
/* 1349 */       if (pos == Double2ByteLinkedOpenHashMap.this.n) {
/* 1350 */         Double2ByteLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1353 */         double[] key = Double2ByteLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           double curr;
/*      */           int last;
/* 1357 */           pos = (last = pos) + 1 & Double2ByteLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1359 */             if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/* 1360 */               key[last] = 0.0D;
/*      */               return;
/*      */             } 
/* 1363 */             int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2ByteLinkedOpenHashMap.this.mask;
/* 1364 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1366 */             pos = pos + 1 & Double2ByteLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1368 */           key[last] = curr;
/* 1369 */           Double2ByteLinkedOpenHashMap.this.value[last] = Double2ByteLinkedOpenHashMap.this.value[pos];
/* 1370 */           if (this.next == pos)
/* 1371 */             this.next = last; 
/* 1372 */           if (this.prev == pos)
/* 1373 */             this.prev = last; 
/* 1374 */           Double2ByteLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1379 */       int i = n;
/* 1380 */       while (i-- != 0 && hasNext())
/* 1381 */         nextEntry(); 
/* 1382 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1385 */       int i = n;
/* 1386 */       while (i-- != 0 && hasPrevious())
/* 1387 */         previousEntry(); 
/* 1388 */       return n - i - 1;
/*      */     }
/*      */     public void set(Double2ByteMap.Entry ok) {
/* 1391 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Double2ByteMap.Entry ok) {
/* 1394 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Double2ByteMap.Entry> { private Double2ByteLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(double from) {
/* 1402 */       super(from);
/*      */     }
/*      */     
/*      */     public Double2ByteLinkedOpenHashMap.MapEntry next() {
/* 1406 */       return this.entry = new Double2ByteLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Double2ByteLinkedOpenHashMap.MapEntry previous() {
/* 1410 */       return this.entry = new Double2ByteLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1414 */       super.remove();
/* 1415 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1419 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Double2ByteMap.Entry> { final Double2ByteLinkedOpenHashMap.MapEntry entry = new Double2ByteLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(double from) {
/* 1423 */       super(from);
/*      */     }
/*      */     
/*      */     public Double2ByteLinkedOpenHashMap.MapEntry next() {
/* 1427 */       this.entry.index = nextEntry();
/* 1428 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Double2ByteLinkedOpenHashMap.MapEntry previous() {
/* 1432 */       this.entry.index = previousEntry();
/* 1433 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Double2ByteMap.Entry> implements Double2ByteSortedMap.FastSortedEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Double2ByteMap.Entry> iterator() {
/* 1441 */       return (ObjectBidirectionalIterator<Double2ByteMap.Entry>)new Double2ByteLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Double2ByteMap.Entry> comparator() {
/* 1445 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Double2ByteMap.Entry> subSet(Double2ByteMap.Entry fromElement, Double2ByteMap.Entry toElement) {
/* 1450 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2ByteMap.Entry> headSet(Double2ByteMap.Entry toElement) {
/* 1454 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2ByteMap.Entry> tailSet(Double2ByteMap.Entry fromElement) {
/* 1458 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Double2ByteMap.Entry first() {
/* 1462 */       if (Double2ByteLinkedOpenHashMap.this.size == 0)
/* 1463 */         throw new NoSuchElementException(); 
/* 1464 */       return new Double2ByteLinkedOpenHashMap.MapEntry(Double2ByteLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Double2ByteMap.Entry last() {
/* 1468 */       if (Double2ByteLinkedOpenHashMap.this.size == 0)
/* 1469 */         throw new NoSuchElementException(); 
/* 1470 */       return new Double2ByteLinkedOpenHashMap.MapEntry(Double2ByteLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1475 */       if (!(o instanceof Map.Entry))
/* 1476 */         return false; 
/* 1477 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1478 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1479 */         return false; 
/* 1480 */       if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1481 */         return false; 
/* 1482 */       double k = ((Double)e.getKey()).doubleValue();
/* 1483 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1484 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1485 */         return (Double2ByteLinkedOpenHashMap.this.containsNullKey && Double2ByteLinkedOpenHashMap.this.value[Double2ByteLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1487 */       double[] key = Double2ByteLinkedOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1490 */       if (Double.doubleToLongBits(
/* 1491 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ByteLinkedOpenHashMap.this.mask]) == 0L)
/*      */       {
/* 1493 */         return false; } 
/* 1494 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/* 1495 */         return (Double2ByteLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1498 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ByteLinkedOpenHashMap.this.mask]) == 0L)
/* 1499 */           return false; 
/* 1500 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/* 1501 */           return (Double2ByteLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1507 */       if (!(o instanceof Map.Entry))
/* 1508 */         return false; 
/* 1509 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1510 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1511 */         return false; 
/* 1512 */       if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1513 */         return false; 
/* 1514 */       double k = ((Double)e.getKey()).doubleValue();
/* 1515 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1516 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1517 */         if (Double2ByteLinkedOpenHashMap.this.containsNullKey && Double2ByteLinkedOpenHashMap.this.value[Double2ByteLinkedOpenHashMap.this.n] == v) {
/* 1518 */           Double2ByteLinkedOpenHashMap.this.removeNullEntry();
/* 1519 */           return true;
/*      */         } 
/* 1521 */         return false;
/*      */       } 
/*      */       
/* 1524 */       double[] key = Double2ByteLinkedOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1527 */       if (Double.doubleToLongBits(
/* 1528 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ByteLinkedOpenHashMap.this.mask]) == 0L)
/*      */       {
/* 1530 */         return false; } 
/* 1531 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/* 1532 */         if (Double2ByteLinkedOpenHashMap.this.value[pos] == v) {
/* 1533 */           Double2ByteLinkedOpenHashMap.this.removeEntry(pos);
/* 1534 */           return true;
/*      */         } 
/* 1536 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1539 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ByteLinkedOpenHashMap.this.mask]) == 0L)
/* 1540 */           return false; 
/* 1541 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && 
/* 1542 */           Double2ByteLinkedOpenHashMap.this.value[pos] == v) {
/* 1543 */           Double2ByteLinkedOpenHashMap.this.removeEntry(pos);
/* 1544 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1551 */       return Double2ByteLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1555 */       Double2ByteLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Double2ByteMap.Entry> iterator(Double2ByteMap.Entry from) {
/* 1570 */       return new Double2ByteLinkedOpenHashMap.EntryIterator(from.getDoubleKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Double2ByteMap.Entry> fastIterator() {
/* 1581 */       return new Double2ByteLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Double2ByteMap.Entry> fastIterator(Double2ByteMap.Entry from) {
/* 1596 */       return new Double2ByteLinkedOpenHashMap.FastEntryIterator(from.getDoubleKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2ByteMap.Entry> consumer) {
/* 1601 */       for (int i = Double2ByteLinkedOpenHashMap.this.size, next = Double2ByteLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1602 */         int curr = next;
/* 1603 */         next = (int)Double2ByteLinkedOpenHashMap.this.link[curr];
/* 1604 */         consumer.accept(new AbstractDouble2ByteMap.BasicEntry(Double2ByteLinkedOpenHashMap.this.key[curr], Double2ByteLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2ByteMap.Entry> consumer) {
/* 1610 */       AbstractDouble2ByteMap.BasicEntry entry = new AbstractDouble2ByteMap.BasicEntry();
/* 1611 */       for (int i = Double2ByteLinkedOpenHashMap.this.size, next = Double2ByteLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1612 */         int curr = next;
/* 1613 */         next = (int)Double2ByteLinkedOpenHashMap.this.link[curr];
/* 1614 */         entry.key = Double2ByteLinkedOpenHashMap.this.key[curr];
/* 1615 */         entry.value = Double2ByteLinkedOpenHashMap.this.value[curr];
/* 1616 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Double2ByteSortedMap.FastSortedEntrySet double2ByteEntrySet() {
/* 1622 */     if (this.entries == null)
/* 1623 */       this.entries = new MapEntrySet(); 
/* 1624 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends MapIterator
/*      */     implements DoubleListIterator
/*      */   {
/*      */     public KeyIterator(double k) {
/* 1637 */       super(k);
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1641 */       return Double2ByteLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public double nextDouble() {
/* 1648 */       return Double2ByteLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSortedSet { private KeySet() {}
/*      */     
/*      */     public DoubleListIterator iterator(double from) {
/* 1654 */       return new Double2ByteLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public DoubleListIterator iterator() {
/* 1658 */       return new Double2ByteLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1663 */       if (Double2ByteLinkedOpenHashMap.this.containsNullKey)
/* 1664 */         consumer.accept(Double2ByteLinkedOpenHashMap.this.key[Double2ByteLinkedOpenHashMap.this.n]); 
/* 1665 */       for (int pos = Double2ByteLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1666 */         double k = Double2ByteLinkedOpenHashMap.this.key[pos];
/* 1667 */         if (Double.doubleToLongBits(k) != 0L)
/* 1668 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1673 */       return Double2ByteLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/* 1677 */       return Double2ByteLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/* 1681 */       int oldSize = Double2ByteLinkedOpenHashMap.this.size;
/* 1682 */       Double2ByteLinkedOpenHashMap.this.remove(k);
/* 1683 */       return (Double2ByteLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1687 */       Double2ByteLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public double firstDouble() {
/* 1691 */       if (Double2ByteLinkedOpenHashMap.this.size == 0)
/* 1692 */         throw new NoSuchElementException(); 
/* 1693 */       return Double2ByteLinkedOpenHashMap.this.key[Double2ByteLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public double lastDouble() {
/* 1697 */       if (Double2ByteLinkedOpenHashMap.this.size == 0)
/* 1698 */         throw new NoSuchElementException(); 
/* 1699 */       return Double2ByteLinkedOpenHashMap.this.key[Double2ByteLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1703 */       return null;
/*      */     }
/*      */     
/*      */     public DoubleSortedSet tailSet(double from) {
/* 1707 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public DoubleSortedSet headSet(double to) {
/* 1711 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public DoubleSortedSet subSet(double from, double to) {
/* 1715 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSortedSet keySet() {
/* 1720 */     if (this.keys == null)
/* 1721 */       this.keys = new KeySet(); 
/* 1722 */     return this.keys;
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
/* 1736 */       return Double2ByteLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1743 */       return Double2ByteLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ByteCollection values() {
/* 1748 */     if (this.values == null)
/* 1749 */       this.values = (ByteCollection)new AbstractByteCollection()
/*      */         {
/*      */           public ByteIterator iterator() {
/* 1752 */             return (ByteIterator)new Double2ByteLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1756 */             return Double2ByteLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(byte v) {
/* 1760 */             return Double2ByteLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1764 */             Double2ByteLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(IntConsumer consumer) {
/* 1769 */             if (Double2ByteLinkedOpenHashMap.this.containsNullKey)
/* 1770 */               consumer.accept(Double2ByteLinkedOpenHashMap.this.value[Double2ByteLinkedOpenHashMap.this.n]); 
/* 1771 */             for (int pos = Double2ByteLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1772 */               if (Double.doubleToLongBits(Double2ByteLinkedOpenHashMap.this.key[pos]) != 0L)
/* 1773 */                 consumer.accept(Double2ByteLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1776 */     return this.values;
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
/* 1793 */     return trim(this.size);
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
/* 1817 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1818 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1819 */       return true; 
/*      */     try {
/* 1821 */       rehash(l);
/* 1822 */     } catch (OutOfMemoryError cantDoIt) {
/* 1823 */       return false;
/*      */     } 
/* 1825 */     return true;
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
/* 1841 */     double[] key = this.key;
/* 1842 */     byte[] value = this.value;
/* 1843 */     int mask = newN - 1;
/* 1844 */     double[] newKey = new double[newN + 1];
/* 1845 */     byte[] newValue = new byte[newN + 1];
/* 1846 */     int i = this.first, prev = -1, newPrev = -1;
/* 1847 */     long[] link = this.link;
/* 1848 */     long[] newLink = new long[newN + 1];
/* 1849 */     this.first = -1;
/* 1850 */     for (int j = this.size; j-- != 0; ) {
/* 1851 */       int pos; if (Double.doubleToLongBits(key[i]) == 0L) {
/* 1852 */         pos = newN;
/*      */       } else {
/* 1854 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask;
/* 1855 */         while (Double.doubleToLongBits(newKey[pos]) != 0L)
/* 1856 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1858 */       newKey[pos] = key[i];
/* 1859 */       newValue[pos] = value[i];
/* 1860 */       if (prev != -1) {
/* 1861 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1862 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1863 */         newPrev = pos;
/*      */       } else {
/* 1865 */         newPrev = this.first = pos;
/*      */         
/* 1867 */         newLink[pos] = -1L;
/*      */       } 
/* 1869 */       int t = i;
/* 1870 */       i = (int)link[i];
/* 1871 */       prev = t;
/*      */     } 
/* 1873 */     this.link = newLink;
/* 1874 */     this.last = newPrev;
/* 1875 */     if (newPrev != -1)
/*      */     {
/* 1877 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1878 */     this.n = newN;
/* 1879 */     this.mask = mask;
/* 1880 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1881 */     this.key = newKey;
/* 1882 */     this.value = newValue;
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
/*      */   public Double2ByteLinkedOpenHashMap clone() {
/*      */     Double2ByteLinkedOpenHashMap c;
/*      */     try {
/* 1899 */       c = (Double2ByteLinkedOpenHashMap)super.clone();
/* 1900 */     } catch (CloneNotSupportedException cantHappen) {
/* 1901 */       throw new InternalError();
/*      */     } 
/* 1903 */     c.keys = null;
/* 1904 */     c.values = null;
/* 1905 */     c.entries = null;
/* 1906 */     c.containsNullKey = this.containsNullKey;
/* 1907 */     c.key = (double[])this.key.clone();
/* 1908 */     c.value = (byte[])this.value.clone();
/* 1909 */     c.link = (long[])this.link.clone();
/* 1910 */     return c;
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
/* 1923 */     int h = 0;
/* 1924 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1925 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1926 */         i++; 
/* 1927 */       t = HashCommon.double2int(this.key[i]);
/* 1928 */       t ^= this.value[i];
/* 1929 */       h += t;
/* 1930 */       i++;
/*      */     } 
/*      */     
/* 1933 */     if (this.containsNullKey)
/* 1934 */       h += this.value[this.n]; 
/* 1935 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1938 */     double[] key = this.key;
/* 1939 */     byte[] value = this.value;
/* 1940 */     MapIterator i = new MapIterator();
/* 1941 */     s.defaultWriteObject();
/* 1942 */     for (int j = this.size; j-- != 0; ) {
/* 1943 */       int e = i.nextEntry();
/* 1944 */       s.writeDouble(key[e]);
/* 1945 */       s.writeByte(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1950 */     s.defaultReadObject();
/* 1951 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1952 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1953 */     this.mask = this.n - 1;
/* 1954 */     double[] key = this.key = new double[this.n + 1];
/* 1955 */     byte[] value = this.value = new byte[this.n + 1];
/* 1956 */     long[] link = this.link = new long[this.n + 1];
/* 1957 */     int prev = -1;
/* 1958 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1961 */     for (int i = this.size; i-- != 0; ) {
/* 1962 */       int pos; double k = s.readDouble();
/* 1963 */       byte v = s.readByte();
/* 1964 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1965 */         pos = this.n;
/* 1966 */         this.containsNullKey = true;
/*      */       } else {
/* 1968 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/* 1969 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1970 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1972 */       key[pos] = k;
/* 1973 */       value[pos] = v;
/* 1974 */       if (this.first != -1) {
/* 1975 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1976 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1977 */         prev = pos; continue;
/*      */       } 
/* 1979 */       prev = this.first = pos;
/*      */       
/* 1981 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1984 */     this.last = prev;
/* 1985 */     if (prev != -1)
/*      */     {
/* 1987 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2ByteLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */