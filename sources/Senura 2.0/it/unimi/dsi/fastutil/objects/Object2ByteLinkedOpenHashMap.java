/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteListIterator;
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
/*      */ public class Object2ByteLinkedOpenHashMap<K>
/*      */   extends AbstractObject2ByteSortedMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
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
/*      */   protected transient Object2ByteSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ByteCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ByteLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = (K[])new Object[this.n + 1];
/*  162 */     this.value = new byte[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ByteLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ByteLinkedOpenHashMap() {
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
/*      */   public Object2ByteLinkedOpenHashMap(Map<? extends K, ? extends Byte> m, float f) {
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
/*      */   public Object2ByteLinkedOpenHashMap(Map<? extends K, ? extends Byte> m) {
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
/*      */   public Object2ByteLinkedOpenHashMap(Object2ByteMap<K> m, float f) {
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
/*      */   public Object2ByteLinkedOpenHashMap(Object2ByteMap<K> m) {
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
/*      */   public Object2ByteLinkedOpenHashMap(K[] k, byte[] v, float f) {
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
/*      */   public Object2ByteLinkedOpenHashMap(K[] k, byte[] v) {
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
/*  285 */     this.key[this.n] = null;
/*  286 */     byte oldValue = this.value[this.n];
/*  287 */     this.size--;
/*  288 */     fixPointers(this.n);
/*  289 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  290 */       rehash(this.n / 2); 
/*  291 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Byte> m) {
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
/*      */   private void insert(int pos, K k, byte v) {
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
/*      */   public byte put(K k, byte v) {
/*  343 */     int pos = find(k);
/*  344 */     if (pos < 0) {
/*  345 */       insert(-pos - 1, k, v);
/*  346 */       return this.defRetValue;
/*      */     } 
/*  348 */     byte oldValue = this.value[pos];
/*  349 */     this.value[pos] = v;
/*  350 */     return oldValue;
/*      */   }
/*      */   private byte addToValue(int pos, byte incr) {
/*  353 */     byte oldValue = this.value[pos];
/*  354 */     this.value[pos] = (byte)(oldValue + incr);
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
/*      */   public byte addTo(K k, byte incr) {
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
/*  384 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  385 */         if (curr.equals(k))
/*  386 */           return addToValue(pos, incr); 
/*  387 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  388 */           if (curr.equals(k))
/*  389 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  392 */     }  this.key[pos] = k;
/*  393 */     this.value[pos] = (byte)(this.defRetValue + incr);
/*  394 */     if (this.size == 0) {
/*  395 */       this.first = this.last = pos;
/*      */       
/*  397 */       this.link[pos] = -1L;
/*      */     } else {
/*  399 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  400 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  401 */       this.last = pos;
/*      */     } 
/*  403 */     if (this.size++ >= this.maxFill) {
/*  404 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  407 */     return this.defRetValue;
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
/*  420 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  422 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  424 */         if ((curr = key[pos]) == null) {
/*  425 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  428 */         int slot = HashCommon.mix(curr.hashCode()) & this.mask;
/*  429 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  431 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  433 */       key[last] = curr;
/*  434 */       this.value[last] = this.value[pos];
/*  435 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public byte removeByte(Object k) {
/*  441 */     if (k == null) {
/*  442 */       if (this.containsNullKey)
/*  443 */         return removeNullEntry(); 
/*  444 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  447 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  450 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  451 */       return this.defRetValue; 
/*  452 */     if (k.equals(curr))
/*  453 */       return removeEntry(pos); 
/*      */     while (true) {
/*  455 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  456 */         return this.defRetValue; 
/*  457 */       if (k.equals(curr))
/*  458 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private byte setValue(int pos, byte v) {
/*  462 */     byte oldValue = this.value[pos];
/*  463 */     this.value[pos] = v;
/*  464 */     return oldValue;
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
/*  475 */     if (this.size == 0)
/*  476 */       throw new NoSuchElementException(); 
/*  477 */     int pos = this.first;
/*      */     
/*  479 */     this.first = (int)this.link[pos];
/*  480 */     if (0 <= this.first)
/*      */     {
/*  482 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  484 */     this.size--;
/*  485 */     byte v = this.value[pos];
/*  486 */     if (pos == this.n) {
/*  487 */       this.containsNullKey = false;
/*  488 */       this.key[this.n] = null;
/*      */     } else {
/*  490 */       shiftKeys(pos);
/*  491 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  492 */       rehash(this.n / 2); 
/*  493 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte removeLastByte() {
/*  503 */     if (this.size == 0)
/*  504 */       throw new NoSuchElementException(); 
/*  505 */     int pos = this.last;
/*      */     
/*  507 */     this.last = (int)(this.link[pos] >>> 32L);
/*  508 */     if (0 <= this.last)
/*      */     {
/*  510 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  512 */     this.size--;
/*  513 */     byte v = this.value[pos];
/*  514 */     if (pos == this.n) {
/*  515 */       this.containsNullKey = false;
/*  516 */       this.key[this.n] = null;
/*      */     } else {
/*  518 */       shiftKeys(pos);
/*  519 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  520 */       rehash(this.n / 2); 
/*  521 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  524 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  526 */     if (this.last == i) {
/*  527 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  529 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  531 */       long linki = this.link[i];
/*  532 */       int prev = (int)(linki >>> 32L);
/*  533 */       int next = (int)linki;
/*  534 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  535 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  537 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  538 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  539 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  542 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  544 */     if (this.first == i) {
/*  545 */       this.first = (int)this.link[i];
/*      */       
/*  547 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  549 */       long linki = this.link[i];
/*  550 */       int prev = (int)(linki >>> 32L);
/*  551 */       int next = (int)linki;
/*  552 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  553 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  555 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  556 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  557 */     this.last = i;
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
/*      */   public byte getAndMoveToFirst(K k) {
/*  569 */     if (k == null) {
/*  570 */       if (this.containsNullKey) {
/*  571 */         moveIndexToFirst(this.n);
/*  572 */         return this.value[this.n];
/*      */       } 
/*  574 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  577 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  580 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  581 */       return this.defRetValue; 
/*  582 */     if (k.equals(curr)) {
/*  583 */       moveIndexToFirst(pos);
/*  584 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  588 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  589 */         return this.defRetValue; 
/*  590 */       if (k.equals(curr)) {
/*  591 */         moveIndexToFirst(pos);
/*  592 */         return this.value[pos];
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
/*      */   public byte getAndMoveToLast(K k) {
/*  606 */     if (k == null) {
/*  607 */       if (this.containsNullKey) {
/*  608 */         moveIndexToLast(this.n);
/*  609 */         return this.value[this.n];
/*      */       } 
/*  611 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  614 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  617 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  618 */       return this.defRetValue; 
/*  619 */     if (k.equals(curr)) {
/*  620 */       moveIndexToLast(pos);
/*  621 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  625 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  626 */         return this.defRetValue; 
/*  627 */       if (k.equals(curr)) {
/*  628 */         moveIndexToLast(pos);
/*  629 */         return this.value[pos];
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
/*      */   public byte putAndMoveToFirst(K k, byte v) {
/*      */     int pos;
/*  646 */     if (k == null) {
/*  647 */       if (this.containsNullKey) {
/*  648 */         moveIndexToFirst(this.n);
/*  649 */         return setValue(this.n, v);
/*      */       } 
/*  651 */       this.containsNullKey = true;
/*  652 */       pos = this.n;
/*      */     } else {
/*      */       
/*  655 */       K[] key = this.key;
/*      */       K curr;
/*  657 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  658 */         if (curr.equals(k)) {
/*  659 */           moveIndexToFirst(pos);
/*  660 */           return setValue(pos, v);
/*      */         } 
/*  662 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  663 */           if (curr.equals(k)) {
/*  664 */             moveIndexToFirst(pos);
/*  665 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  669 */     }  this.key[pos] = k;
/*  670 */     this.value[pos] = v;
/*  671 */     if (this.size == 0) {
/*  672 */       this.first = this.last = pos;
/*      */       
/*  674 */       this.link[pos] = -1L;
/*      */     } else {
/*  676 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  677 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  678 */       this.first = pos;
/*      */     } 
/*  680 */     if (this.size++ >= this.maxFill) {
/*  681 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  684 */     return this.defRetValue;
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
/*      */   public byte putAndMoveToLast(K k, byte v) {
/*      */     int pos;
/*  699 */     if (k == null) {
/*  700 */       if (this.containsNullKey) {
/*  701 */         moveIndexToLast(this.n);
/*  702 */         return setValue(this.n, v);
/*      */       } 
/*  704 */       this.containsNullKey = true;
/*  705 */       pos = this.n;
/*      */     } else {
/*      */       
/*  708 */       K[] key = this.key;
/*      */       K curr;
/*  710 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  711 */         if (curr.equals(k)) {
/*  712 */           moveIndexToLast(pos);
/*  713 */           return setValue(pos, v);
/*      */         } 
/*  715 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  716 */           if (curr.equals(k)) {
/*  717 */             moveIndexToLast(pos);
/*  718 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  722 */     }  this.key[pos] = k;
/*  723 */     this.value[pos] = v;
/*  724 */     if (this.size == 0) {
/*  725 */       this.first = this.last = pos;
/*      */       
/*  727 */       this.link[pos] = -1L;
/*      */     } else {
/*  729 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  730 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  731 */       this.last = pos;
/*      */     } 
/*  733 */     if (this.size++ >= this.maxFill) {
/*  734 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  737 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte getByte(Object k) {
/*  742 */     if (k == null) {
/*  743 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  745 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  748 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  749 */       return this.defRetValue; 
/*  750 */     if (k.equals(curr)) {
/*  751 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  754 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  755 */         return this.defRetValue; 
/*  756 */       if (k.equals(curr)) {
/*  757 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  763 */     if (k == null) {
/*  764 */       return this.containsNullKey;
/*      */     }
/*  766 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  769 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  770 */       return false; 
/*  771 */     if (k.equals(curr)) {
/*  772 */       return true;
/*      */     }
/*      */     while (true) {
/*  775 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  776 */         return false; 
/*  777 */       if (k.equals(curr))
/*  778 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(byte v) {
/*  783 */     byte[] value = this.value;
/*  784 */     K[] key = this.key;
/*  785 */     if (this.containsNullKey && value[this.n] == v)
/*  786 */       return true; 
/*  787 */     for (int i = this.n; i-- != 0;) {
/*  788 */       if (key[i] != null && value[i] == v)
/*  789 */         return true; 
/*  790 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getOrDefault(Object k, byte defaultValue) {
/*  796 */     if (k == null) {
/*  797 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  799 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  802 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  803 */       return defaultValue; 
/*  804 */     if (k.equals(curr)) {
/*  805 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  808 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  809 */         return defaultValue; 
/*  810 */       if (k.equals(curr)) {
/*  811 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public byte putIfAbsent(K k, byte v) {
/*  817 */     int pos = find(k);
/*  818 */     if (pos >= 0)
/*  819 */       return this.value[pos]; 
/*  820 */     insert(-pos - 1, k, v);
/*  821 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, byte v) {
/*  827 */     if (k == null) {
/*  828 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  829 */         removeNullEntry();
/*  830 */         return true;
/*      */       } 
/*  832 */       return false;
/*      */     } 
/*      */     
/*  835 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  838 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  839 */       return false; 
/*  840 */     if (k.equals(curr) && v == this.value[pos]) {
/*  841 */       removeEntry(pos);
/*  842 */       return true;
/*      */     } 
/*      */     while (true) {
/*  845 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  846 */         return false; 
/*  847 */       if (k.equals(curr) && v == this.value[pos]) {
/*  848 */         removeEntry(pos);
/*  849 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, byte oldValue, byte v) {
/*  856 */     int pos = find(k);
/*  857 */     if (pos < 0 || oldValue != this.value[pos])
/*  858 */       return false; 
/*  859 */     this.value[pos] = v;
/*  860 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte replace(K k, byte v) {
/*  865 */     int pos = find(k);
/*  866 */     if (pos < 0)
/*  867 */       return this.defRetValue; 
/*  868 */     byte oldValue = this.value[pos];
/*  869 */     this.value[pos] = v;
/*  870 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte computeByteIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
/*  875 */     Objects.requireNonNull(mappingFunction);
/*  876 */     int pos = find(k);
/*  877 */     if (pos >= 0)
/*  878 */       return this.value[pos]; 
/*  879 */     byte newValue = SafeMath.safeIntToByte(mappingFunction.applyAsInt(k));
/*  880 */     insert(-pos - 1, k, newValue);
/*  881 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeByteIfPresent(K k, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
/*  887 */     Objects.requireNonNull(remappingFunction);
/*  888 */     int pos = find(k);
/*  889 */     if (pos < 0)
/*  890 */       return this.defRetValue; 
/*  891 */     Byte newValue = remappingFunction.apply(k, Byte.valueOf(this.value[pos]));
/*  892 */     if (newValue == null) {
/*  893 */       if (k == null) {
/*  894 */         removeNullEntry();
/*      */       } else {
/*  896 */         removeEntry(pos);
/*  897 */       }  return this.defRetValue;
/*      */     } 
/*  899 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeByte(K k, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
/*  905 */     Objects.requireNonNull(remappingFunction);
/*  906 */     int pos = find(k);
/*  907 */     Byte newValue = remappingFunction.apply(k, (pos >= 0) ? Byte.valueOf(this.value[pos]) : null);
/*  908 */     if (newValue == null) {
/*  909 */       if (pos >= 0)
/*  910 */         if (k == null) {
/*  911 */           removeNullEntry();
/*      */         } else {
/*  913 */           removeEntry(pos);
/*      */         }  
/*  915 */       return this.defRetValue;
/*      */     } 
/*  917 */     byte newVal = newValue.byteValue();
/*  918 */     if (pos < 0) {
/*  919 */       insert(-pos - 1, k, newVal);
/*  920 */       return newVal;
/*      */     } 
/*  922 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte mergeByte(K k, byte v, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*  928 */     Objects.requireNonNull(remappingFunction);
/*  929 */     int pos = find(k);
/*  930 */     if (pos < 0) {
/*  931 */       insert(-pos - 1, k, v);
/*  932 */       return v;
/*      */     } 
/*  934 */     Byte newValue = remappingFunction.apply(Byte.valueOf(this.value[pos]), Byte.valueOf(v));
/*  935 */     if (newValue == null) {
/*  936 */       if (k == null) {
/*  937 */         removeNullEntry();
/*      */       } else {
/*  939 */         removeEntry(pos);
/*  940 */       }  return this.defRetValue;
/*      */     } 
/*  942 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
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
/*  953 */     if (this.size == 0)
/*      */       return; 
/*  955 */     this.size = 0;
/*  956 */     this.containsNullKey = false;
/*  957 */     Arrays.fill((Object[])this.key, (Object)null);
/*  958 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  962 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  966 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2ByteMap.Entry<K>, Map.Entry<K, Byte>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  978 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  984 */       return Object2ByteLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public byte getByteValue() {
/*  988 */       return Object2ByteLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public byte setValue(byte v) {
/*  992 */       byte oldValue = Object2ByteLinkedOpenHashMap.this.value[this.index];
/*  993 */       Object2ByteLinkedOpenHashMap.this.value[this.index] = v;
/*  994 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte getValue() {
/* 1004 */       return Byte.valueOf(Object2ByteLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte setValue(Byte v) {
/* 1014 */       return Byte.valueOf(setValue(v.byteValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1019 */       if (!(o instanceof Map.Entry))
/* 1020 */         return false; 
/* 1021 */       Map.Entry<K, Byte> e = (Map.Entry<K, Byte>)o;
/* 1022 */       return (Objects.equals(Object2ByteLinkedOpenHashMap.this.key[this.index], e.getKey()) && Object2ByteLinkedOpenHashMap.this.value[this.index] == ((Byte)e
/* 1023 */         .getValue()).byteValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1027 */       return ((Object2ByteLinkedOpenHashMap.this.key[this.index] == null) ? 0 : Object2ByteLinkedOpenHashMap.this.key[this.index].hashCode()) ^ Object2ByteLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1031 */       return (new StringBuilder()).append(Object2ByteLinkedOpenHashMap.this.key[this.index]).append("=>").append(Object2ByteLinkedOpenHashMap.this.value[this.index]).toString();
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
/* 1042 */     if (this.size == 0) {
/* 1043 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1046 */     if (this.first == i) {
/* 1047 */       this.first = (int)this.link[i];
/* 1048 */       if (0 <= this.first)
/*      */       {
/* 1050 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1054 */     if (this.last == i) {
/* 1055 */       this.last = (int)(this.link[i] >>> 32L);
/* 1056 */       if (0 <= this.last)
/*      */       {
/* 1058 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1062 */     long linki = this.link[i];
/* 1063 */     int prev = (int)(linki >>> 32L);
/* 1064 */     int next = (int)linki;
/* 1065 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1066 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1079 */     if (this.size == 1) {
/* 1080 */       this.first = this.last = d;
/*      */       
/* 1082 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1085 */     if (this.first == s) {
/* 1086 */       this.first = d;
/* 1087 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1088 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1091 */     if (this.last == s) {
/* 1092 */       this.last = d;
/* 1093 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1094 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1097 */     long links = this.link[s];
/* 1098 */     int prev = (int)(links >>> 32L);
/* 1099 */     int next = (int)links;
/* 1100 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1101 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1102 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1111 */     if (this.size == 0)
/* 1112 */       throw new NoSuchElementException(); 
/* 1113 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1122 */     if (this.size == 0)
/* 1123 */       throw new NoSuchElementException(); 
/* 1124 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ByteSortedMap<K> tailMap(K from) {
/* 1133 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ByteSortedMap<K> headMap(K to) {
/* 1142 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ByteSortedMap<K> subMap(K from, K to) {
/* 1151 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1160 */     return null;
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
/* 1175 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1181 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1186 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1192 */     int index = -1;
/*      */     protected MapIterator() {
/* 1194 */       this.next = Object2ByteLinkedOpenHashMap.this.first;
/* 1195 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/* 1198 */       if (from == null) {
/* 1199 */         if (Object2ByteLinkedOpenHashMap.this.containsNullKey) {
/* 1200 */           this.next = (int)Object2ByteLinkedOpenHashMap.this.link[Object2ByteLinkedOpenHashMap.this.n];
/* 1201 */           this.prev = Object2ByteLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1204 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1206 */       if (Objects.equals(Object2ByteLinkedOpenHashMap.this.key[Object2ByteLinkedOpenHashMap.this.last], from)) {
/* 1207 */         this.prev = Object2ByteLinkedOpenHashMap.this.last;
/* 1208 */         this.index = Object2ByteLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1212 */       int pos = HashCommon.mix(from.hashCode()) & Object2ByteLinkedOpenHashMap.this.mask;
/*      */       
/* 1214 */       while (Object2ByteLinkedOpenHashMap.this.key[pos] != null) {
/* 1215 */         if (Object2ByteLinkedOpenHashMap.this.key[pos].equals(from)) {
/*      */           
/* 1217 */           this.next = (int)Object2ByteLinkedOpenHashMap.this.link[pos];
/* 1218 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1221 */         pos = pos + 1 & Object2ByteLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1223 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1226 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1229 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1232 */       if (this.index >= 0)
/*      */         return; 
/* 1234 */       if (this.prev == -1) {
/* 1235 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1238 */       if (this.next == -1) {
/* 1239 */         this.index = Object2ByteLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1242 */       int pos = Object2ByteLinkedOpenHashMap.this.first;
/* 1243 */       this.index = 1;
/* 1244 */       while (pos != this.prev) {
/* 1245 */         pos = (int)Object2ByteLinkedOpenHashMap.this.link[pos];
/* 1246 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1250 */       ensureIndexKnown();
/* 1251 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1254 */       ensureIndexKnown();
/* 1255 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1258 */       if (!hasNext())
/* 1259 */         throw new NoSuchElementException(); 
/* 1260 */       this.curr = this.next;
/* 1261 */       this.next = (int)Object2ByteLinkedOpenHashMap.this.link[this.curr];
/* 1262 */       this.prev = this.curr;
/* 1263 */       if (this.index >= 0)
/* 1264 */         this.index++; 
/* 1265 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1268 */       if (!hasPrevious())
/* 1269 */         throw new NoSuchElementException(); 
/* 1270 */       this.curr = this.prev;
/* 1271 */       this.prev = (int)(Object2ByteLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1272 */       this.next = this.curr;
/* 1273 */       if (this.index >= 0)
/* 1274 */         this.index--; 
/* 1275 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1278 */       ensureIndexKnown();
/* 1279 */       if (this.curr == -1)
/* 1280 */         throw new IllegalStateException(); 
/* 1281 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1286 */         this.index--;
/* 1287 */         this.prev = (int)(Object2ByteLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1289 */         this.next = (int)Object2ByteLinkedOpenHashMap.this.link[this.curr];
/* 1290 */       }  Object2ByteLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1295 */       if (this.prev == -1) {
/* 1296 */         Object2ByteLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1298 */         Object2ByteLinkedOpenHashMap.this.link[this.prev] = Object2ByteLinkedOpenHashMap.this.link[this.prev] ^ (Object2ByteLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1299 */       }  if (this.next == -1) {
/* 1300 */         Object2ByteLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1302 */         Object2ByteLinkedOpenHashMap.this.link[this.next] = Object2ByteLinkedOpenHashMap.this.link[this.next] ^ (Object2ByteLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1303 */       }  int pos = this.curr;
/* 1304 */       this.curr = -1;
/* 1305 */       if (pos == Object2ByteLinkedOpenHashMap.this.n) {
/* 1306 */         Object2ByteLinkedOpenHashMap.this.containsNullKey = false;
/* 1307 */         Object2ByteLinkedOpenHashMap.this.key[Object2ByteLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1310 */         K[] key = Object2ByteLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1314 */           pos = (last = pos) + 1 & Object2ByteLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1316 */             if ((curr = key[pos]) == null) {
/* 1317 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1320 */             int slot = HashCommon.mix(curr.hashCode()) & Object2ByteLinkedOpenHashMap.this.mask;
/* 1321 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1323 */             pos = pos + 1 & Object2ByteLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1325 */           key[last] = curr;
/* 1326 */           Object2ByteLinkedOpenHashMap.this.value[last] = Object2ByteLinkedOpenHashMap.this.value[pos];
/* 1327 */           if (this.next == pos)
/* 1328 */             this.next = last; 
/* 1329 */           if (this.prev == pos)
/* 1330 */             this.prev = last; 
/* 1331 */           Object2ByteLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1336 */       int i = n;
/* 1337 */       while (i-- != 0 && hasNext())
/* 1338 */         nextEntry(); 
/* 1339 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1342 */       int i = n;
/* 1343 */       while (i-- != 0 && hasPrevious())
/* 1344 */         previousEntry(); 
/* 1345 */       return n - i - 1;
/*      */     }
/*      */     public void set(Object2ByteMap.Entry<K> ok) {
/* 1348 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Object2ByteMap.Entry<K> ok) {
/* 1351 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Object2ByteMap.Entry<K>> { private Object2ByteLinkedOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1359 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2ByteLinkedOpenHashMap<K>.MapEntry next() {
/* 1363 */       return this.entry = new Object2ByteLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Object2ByteLinkedOpenHashMap<K>.MapEntry previous() {
/* 1367 */       return this.entry = new Object2ByteLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1371 */       super.remove();
/* 1372 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1376 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Object2ByteMap.Entry<K>> { final Object2ByteLinkedOpenHashMap<K>.MapEntry entry = new Object2ByteLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1380 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2ByteLinkedOpenHashMap<K>.MapEntry next() {
/* 1384 */       this.entry.index = nextEntry();
/* 1385 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Object2ByteLinkedOpenHashMap<K>.MapEntry previous() {
/* 1389 */       this.entry.index = previousEntry();
/* 1390 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2ByteMap.Entry<K>> implements Object2ByteSortedMap.FastSortedEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2ByteMap.Entry<K>> iterator() {
/* 1398 */       return new Object2ByteLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Object2ByteMap.Entry<K>> comparator() {
/* 1402 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2ByteMap.Entry<K>> subSet(Object2ByteMap.Entry<K> fromElement, Object2ByteMap.Entry<K> toElement) {
/* 1407 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2ByteMap.Entry<K>> headSet(Object2ByteMap.Entry<K> toElement) {
/* 1411 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2ByteMap.Entry<K>> tailSet(Object2ByteMap.Entry<K> fromElement) {
/* 1415 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Object2ByteMap.Entry<K> first() {
/* 1419 */       if (Object2ByteLinkedOpenHashMap.this.size == 0)
/* 1420 */         throw new NoSuchElementException(); 
/* 1421 */       return new Object2ByteLinkedOpenHashMap.MapEntry(Object2ByteLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Object2ByteMap.Entry<K> last() {
/* 1425 */       if (Object2ByteLinkedOpenHashMap.this.size == 0)
/* 1426 */         throw new NoSuchElementException(); 
/* 1427 */       return new Object2ByteLinkedOpenHashMap.MapEntry(Object2ByteLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1432 */       if (!(o instanceof Map.Entry))
/* 1433 */         return false; 
/* 1434 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1435 */       if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1436 */         return false; 
/* 1437 */       K k = (K)e.getKey();
/* 1438 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1439 */       if (k == null) {
/* 1440 */         return (Object2ByteLinkedOpenHashMap.this.containsNullKey && Object2ByteLinkedOpenHashMap.this.value[Object2ByteLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1442 */       K[] key = Object2ByteLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1445 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ByteLinkedOpenHashMap.this.mask]) == null)
/* 1446 */         return false; 
/* 1447 */       if (k.equals(curr)) {
/* 1448 */         return (Object2ByteLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1451 */         if ((curr = key[pos = pos + 1 & Object2ByteLinkedOpenHashMap.this.mask]) == null)
/* 1452 */           return false; 
/* 1453 */         if (k.equals(curr)) {
/* 1454 */           return (Object2ByteLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1460 */       if (!(o instanceof Map.Entry))
/* 1461 */         return false; 
/* 1462 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1463 */       if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1464 */         return false; 
/* 1465 */       K k = (K)e.getKey();
/* 1466 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1467 */       if (k == null) {
/* 1468 */         if (Object2ByteLinkedOpenHashMap.this.containsNullKey && Object2ByteLinkedOpenHashMap.this.value[Object2ByteLinkedOpenHashMap.this.n] == v) {
/* 1469 */           Object2ByteLinkedOpenHashMap.this.removeNullEntry();
/* 1470 */           return true;
/*      */         } 
/* 1472 */         return false;
/*      */       } 
/*      */       
/* 1475 */       K[] key = Object2ByteLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1478 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ByteLinkedOpenHashMap.this.mask]) == null)
/* 1479 */         return false; 
/* 1480 */       if (curr.equals(k)) {
/* 1481 */         if (Object2ByteLinkedOpenHashMap.this.value[pos] == v) {
/* 1482 */           Object2ByteLinkedOpenHashMap.this.removeEntry(pos);
/* 1483 */           return true;
/*      */         } 
/* 1485 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1488 */         if ((curr = key[pos = pos + 1 & Object2ByteLinkedOpenHashMap.this.mask]) == null)
/* 1489 */           return false; 
/* 1490 */         if (curr.equals(k) && 
/* 1491 */           Object2ByteLinkedOpenHashMap.this.value[pos] == v) {
/* 1492 */           Object2ByteLinkedOpenHashMap.this.removeEntry(pos);
/* 1493 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1500 */       return Object2ByteLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1504 */       Object2ByteLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2ByteMap.Entry<K>> iterator(Object2ByteMap.Entry<K> from) {
/* 1519 */       return new Object2ByteLinkedOpenHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2ByteMap.Entry<K>> fastIterator() {
/* 1530 */       return new Object2ByteLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2ByteMap.Entry<K>> fastIterator(Object2ByteMap.Entry<K> from) {
/* 1545 */       return new Object2ByteLinkedOpenHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2ByteMap.Entry<K>> consumer) {
/* 1550 */       for (int i = Object2ByteLinkedOpenHashMap.this.size, next = Object2ByteLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1551 */         int curr = next;
/* 1552 */         next = (int)Object2ByteLinkedOpenHashMap.this.link[curr];
/* 1553 */         consumer.accept(new AbstractObject2ByteMap.BasicEntry<>(Object2ByteLinkedOpenHashMap.this.key[curr], Object2ByteLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2ByteMap.Entry<K>> consumer) {
/* 1559 */       AbstractObject2ByteMap.BasicEntry<K> entry = new AbstractObject2ByteMap.BasicEntry<>();
/* 1560 */       for (int i = Object2ByteLinkedOpenHashMap.this.size, next = Object2ByteLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1561 */         int curr = next;
/* 1562 */         next = (int)Object2ByteLinkedOpenHashMap.this.link[curr];
/* 1563 */         entry.key = Object2ByteLinkedOpenHashMap.this.key[curr];
/* 1564 */         entry.value = Object2ByteLinkedOpenHashMap.this.value[curr];
/* 1565 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Object2ByteSortedMap.FastSortedEntrySet<K> object2ByteEntrySet() {
/* 1571 */     if (this.entries == null)
/* 1572 */       this.entries = new MapEntrySet(); 
/* 1573 */     return this.entries;
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
/* 1586 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1590 */       return Object2ByteLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1597 */       return Object2ByteLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1603 */       return new Object2ByteLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1607 */       return new Object2ByteLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1612 */       if (Object2ByteLinkedOpenHashMap.this.containsNullKey)
/* 1613 */         consumer.accept(Object2ByteLinkedOpenHashMap.this.key[Object2ByteLinkedOpenHashMap.this.n]); 
/* 1614 */       for (int pos = Object2ByteLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1615 */         K k = Object2ByteLinkedOpenHashMap.this.key[pos];
/* 1616 */         if (k != null)
/* 1617 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1622 */       return Object2ByteLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1626 */       return Object2ByteLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1630 */       int oldSize = Object2ByteLinkedOpenHashMap.this.size;
/* 1631 */       Object2ByteLinkedOpenHashMap.this.removeByte(k);
/* 1632 */       return (Object2ByteLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1636 */       Object2ByteLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1640 */       if (Object2ByteLinkedOpenHashMap.this.size == 0)
/* 1641 */         throw new NoSuchElementException(); 
/* 1642 */       return Object2ByteLinkedOpenHashMap.this.key[Object2ByteLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1646 */       if (Object2ByteLinkedOpenHashMap.this.size == 0)
/* 1647 */         throw new NoSuchElementException(); 
/* 1648 */       return Object2ByteLinkedOpenHashMap.this.key[Object2ByteLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1652 */       return null;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1656 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1660 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1664 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1669 */     if (this.keys == null)
/* 1670 */       this.keys = new KeySet(); 
/* 1671 */     return this.keys;
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
/* 1685 */       return Object2ByteLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1692 */       return Object2ByteLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ByteCollection values() {
/* 1697 */     if (this.values == null)
/* 1698 */       this.values = (ByteCollection)new AbstractByteCollection()
/*      */         {
/*      */           public ByteIterator iterator() {
/* 1701 */             return (ByteIterator)new Object2ByteLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1705 */             return Object2ByteLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(byte v) {
/* 1709 */             return Object2ByteLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1713 */             Object2ByteLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(IntConsumer consumer) {
/* 1718 */             if (Object2ByteLinkedOpenHashMap.this.containsNullKey)
/* 1719 */               consumer.accept(Object2ByteLinkedOpenHashMap.this.value[Object2ByteLinkedOpenHashMap.this.n]); 
/* 1720 */             for (int pos = Object2ByteLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1721 */               if (Object2ByteLinkedOpenHashMap.this.key[pos] != null)
/* 1722 */                 consumer.accept(Object2ByteLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1725 */     return this.values;
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
/* 1742 */     return trim(this.size);
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
/* 1766 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1767 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1768 */       return true; 
/*      */     try {
/* 1770 */       rehash(l);
/* 1771 */     } catch (OutOfMemoryError cantDoIt) {
/* 1772 */       return false;
/*      */     } 
/* 1774 */     return true;
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
/* 1790 */     K[] key = this.key;
/* 1791 */     byte[] value = this.value;
/* 1792 */     int mask = newN - 1;
/* 1793 */     K[] newKey = (K[])new Object[newN + 1];
/* 1794 */     byte[] newValue = new byte[newN + 1];
/* 1795 */     int i = this.first, prev = -1, newPrev = -1;
/* 1796 */     long[] link = this.link;
/* 1797 */     long[] newLink = new long[newN + 1];
/* 1798 */     this.first = -1;
/* 1799 */     for (int j = this.size; j-- != 0; ) {
/* 1800 */       int pos; if (key[i] == null) {
/* 1801 */         pos = newN;
/*      */       } else {
/* 1803 */         pos = HashCommon.mix(key[i].hashCode()) & mask;
/* 1804 */         while (newKey[pos] != null)
/* 1805 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1807 */       newKey[pos] = key[i];
/* 1808 */       newValue[pos] = value[i];
/* 1809 */       if (prev != -1) {
/* 1810 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1811 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1812 */         newPrev = pos;
/*      */       } else {
/* 1814 */         newPrev = this.first = pos;
/*      */         
/* 1816 */         newLink[pos] = -1L;
/*      */       } 
/* 1818 */       int t = i;
/* 1819 */       i = (int)link[i];
/* 1820 */       prev = t;
/*      */     } 
/* 1822 */     this.link = newLink;
/* 1823 */     this.last = newPrev;
/* 1824 */     if (newPrev != -1)
/*      */     {
/* 1826 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1827 */     this.n = newN;
/* 1828 */     this.mask = mask;
/* 1829 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1830 */     this.key = newKey;
/* 1831 */     this.value = newValue;
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
/*      */   public Object2ByteLinkedOpenHashMap<K> clone() {
/*      */     Object2ByteLinkedOpenHashMap<K> c;
/*      */     try {
/* 1848 */       c = (Object2ByteLinkedOpenHashMap<K>)super.clone();
/* 1849 */     } catch (CloneNotSupportedException cantHappen) {
/* 1850 */       throw new InternalError();
/*      */     } 
/* 1852 */     c.keys = null;
/* 1853 */     c.values = null;
/* 1854 */     c.entries = null;
/* 1855 */     c.containsNullKey = this.containsNullKey;
/* 1856 */     c.key = (K[])this.key.clone();
/* 1857 */     c.value = (byte[])this.value.clone();
/* 1858 */     c.link = (long[])this.link.clone();
/* 1859 */     return c;
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
/* 1872 */     int h = 0;
/* 1873 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1874 */       while (this.key[i] == null)
/* 1875 */         i++; 
/* 1876 */       if (this != this.key[i])
/* 1877 */         t = this.key[i].hashCode(); 
/* 1878 */       t ^= this.value[i];
/* 1879 */       h += t;
/* 1880 */       i++;
/*      */     } 
/*      */     
/* 1883 */     if (this.containsNullKey)
/* 1884 */       h += this.value[this.n]; 
/* 1885 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1888 */     K[] key = this.key;
/* 1889 */     byte[] value = this.value;
/* 1890 */     MapIterator i = new MapIterator();
/* 1891 */     s.defaultWriteObject();
/* 1892 */     for (int j = this.size; j-- != 0; ) {
/* 1893 */       int e = i.nextEntry();
/* 1894 */       s.writeObject(key[e]);
/* 1895 */       s.writeByte(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1900 */     s.defaultReadObject();
/* 1901 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1902 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1903 */     this.mask = this.n - 1;
/* 1904 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1905 */     byte[] value = this.value = new byte[this.n + 1];
/* 1906 */     long[] link = this.link = new long[this.n + 1];
/* 1907 */     int prev = -1;
/* 1908 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1911 */     for (int i = this.size; i-- != 0; ) {
/* 1912 */       int pos; K k = (K)s.readObject();
/* 1913 */       byte v = s.readByte();
/* 1914 */       if (k == null) {
/* 1915 */         pos = this.n;
/* 1916 */         this.containsNullKey = true;
/*      */       } else {
/* 1918 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 1919 */         while (key[pos] != null)
/* 1920 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1922 */       key[pos] = k;
/* 1923 */       value[pos] = v;
/* 1924 */       if (this.first != -1) {
/* 1925 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1926 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1927 */         prev = pos; continue;
/*      */       } 
/* 1929 */       prev = this.first = pos;
/*      */       
/* 1931 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1934 */     this.last = prev;
/* 1935 */     if (prev != -1)
/*      */     {
/* 1937 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ByteLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */