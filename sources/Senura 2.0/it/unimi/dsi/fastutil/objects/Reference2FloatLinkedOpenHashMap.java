/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*      */ import it.unimi.dsi.fastutil.floats.FloatListIterator;
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
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.ToDoubleFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Reference2FloatLinkedOpenHashMap<K>
/*      */   extends AbstractReference2FloatSortedMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient float[] value;
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
/*      */   protected transient Reference2FloatSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ReferenceSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient FloatCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2FloatLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = (K[])new Object[this.n + 1];
/*  162 */     this.value = new float[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2FloatLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2FloatLinkedOpenHashMap() {
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
/*      */   public Reference2FloatLinkedOpenHashMap(Map<? extends K, ? extends Float> m, float f) {
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
/*      */   public Reference2FloatLinkedOpenHashMap(Map<? extends K, ? extends Float> m) {
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
/*      */   public Reference2FloatLinkedOpenHashMap(Reference2FloatMap<K> m, float f) {
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
/*      */   public Reference2FloatLinkedOpenHashMap(Reference2FloatMap<K> m) {
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
/*      */   public Reference2FloatLinkedOpenHashMap(K[] k, float[] v, float f) {
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
/*      */   public Reference2FloatLinkedOpenHashMap(K[] k, float[] v) {
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
/*      */   private float removeEntry(int pos) {
/*  275 */     float oldValue = this.value[pos];
/*  276 */     this.size--;
/*  277 */     fixPointers(pos);
/*  278 */     shiftKeys(pos);
/*  279 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  280 */       rehash(this.n / 2); 
/*  281 */     return oldValue;
/*      */   }
/*      */   private float removeNullEntry() {
/*  284 */     this.containsNullKey = false;
/*  285 */     this.key[this.n] = null;
/*  286 */     float oldValue = this.value[this.n];
/*  287 */     this.size--;
/*  288 */     fixPointers(this.n);
/*  289 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  290 */       rehash(this.n / 2); 
/*  291 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Float> m) {
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
/*      */   private void insert(int pos, K k, float v) {
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
/*      */   public float put(K k, float v) {
/*  343 */     int pos = find(k);
/*  344 */     if (pos < 0) {
/*  345 */       insert(-pos - 1, k, v);
/*  346 */       return this.defRetValue;
/*      */     } 
/*  348 */     float oldValue = this.value[pos];
/*  349 */     this.value[pos] = v;
/*  350 */     return oldValue;
/*      */   }
/*      */   private float addToValue(int pos, float incr) {
/*  353 */     float oldValue = this.value[pos];
/*  354 */     this.value[pos] = oldValue + incr;
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
/*      */   public float addTo(K k, float incr) {
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
/*  394 */     this.value[pos] = this.defRetValue + incr;
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
/*      */   public float removeFloat(Object k) {
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
/*      */   private float setValue(int pos, float v) {
/*  463 */     float oldValue = this.value[pos];
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
/*      */   public float removeFirstFloat() {
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
/*  486 */     float v = this.value[pos];
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
/*      */   public float removeLastFloat() {
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
/*  514 */     float v = this.value[pos];
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
/*      */   public float getAndMoveToFirst(K k) {
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
/*      */   public float getAndMoveToLast(K k) {
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
/*      */   public float putAndMoveToFirst(K k, float v) {
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
/*      */   public float putAndMoveToLast(K k, float v) {
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
/*      */   public float getFloat(Object k) {
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
/*      */   public boolean containsValue(float v) {
/*  786 */     float[] value = this.value;
/*  787 */     K[] key = this.key;
/*  788 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v))
/*  789 */       return true; 
/*  790 */     for (int i = this.n; i-- != 0;) {
/*  791 */       if (key[i] != null && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v))
/*  792 */         return true; 
/*  793 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(Object k, float defaultValue) {
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
/*      */   public float putIfAbsent(K k, float v) {
/*  820 */     int pos = find(k);
/*  821 */     if (pos >= 0)
/*  822 */       return this.value[pos]; 
/*  823 */     insert(-pos - 1, k, v);
/*  824 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, float v) {
/*  830 */     if (k == null) {
/*  831 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
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
/*  843 */     if (k == curr && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  844 */       removeEntry(pos);
/*  845 */       return true;
/*      */     } 
/*      */     while (true) {
/*  848 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  849 */         return false; 
/*  850 */       if (k == curr && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  851 */         removeEntry(pos);
/*  852 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, float oldValue, float v) {
/*  859 */     int pos = find(k);
/*  860 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos]))
/*  861 */       return false; 
/*  862 */     this.value[pos] = v;
/*  863 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public float replace(K k, float v) {
/*  868 */     int pos = find(k);
/*  869 */     if (pos < 0)
/*  870 */       return this.defRetValue; 
/*  871 */     float oldValue = this.value[pos];
/*  872 */     this.value[pos] = v;
/*  873 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public float computeFloatIfAbsent(K k, ToDoubleFunction<? super K> mappingFunction) {
/*  878 */     Objects.requireNonNull(mappingFunction);
/*  879 */     int pos = find(k);
/*  880 */     if (pos >= 0)
/*  881 */       return this.value[pos]; 
/*  882 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(k));
/*  883 */     insert(-pos - 1, k, newValue);
/*  884 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeFloatIfPresent(K k, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/*  890 */     Objects.requireNonNull(remappingFunction);
/*  891 */     int pos = find(k);
/*  892 */     if (pos < 0)
/*  893 */       return this.defRetValue; 
/*  894 */     Float newValue = remappingFunction.apply(k, Float.valueOf(this.value[pos]));
/*  895 */     if (newValue == null) {
/*  896 */       if (k == null) {
/*  897 */         removeNullEntry();
/*      */       } else {
/*  899 */         removeEntry(pos);
/*  900 */       }  return this.defRetValue;
/*      */     } 
/*  902 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeFloat(K k, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/*  908 */     Objects.requireNonNull(remappingFunction);
/*  909 */     int pos = find(k);
/*  910 */     Float newValue = remappingFunction.apply(k, (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  911 */     if (newValue == null) {
/*  912 */       if (pos >= 0)
/*  913 */         if (k == null) {
/*  914 */           removeNullEntry();
/*      */         } else {
/*  916 */           removeEntry(pos);
/*      */         }  
/*  918 */       return this.defRetValue;
/*      */     } 
/*  920 */     float newVal = newValue.floatValue();
/*  921 */     if (pos < 0) {
/*  922 */       insert(-pos - 1, k, newVal);
/*  923 */       return newVal;
/*      */     } 
/*  925 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float mergeFloat(K k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  931 */     Objects.requireNonNull(remappingFunction);
/*  932 */     int pos = find(k);
/*  933 */     if (pos < 0) {
/*  934 */       insert(-pos - 1, k, v);
/*  935 */       return v;
/*      */     } 
/*  937 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  938 */     if (newValue == null) {
/*  939 */       if (k == null) {
/*  940 */         removeNullEntry();
/*      */       } else {
/*  942 */         removeEntry(pos);
/*  943 */       }  return this.defRetValue;
/*      */     } 
/*  945 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
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
/*      */     implements Reference2FloatMap.Entry<K>, Map.Entry<K, Float>
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
/*  987 */       return Reference2FloatLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public float getFloatValue() {
/*  991 */       return Reference2FloatLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public float setValue(float v) {
/*  995 */       float oldValue = Reference2FloatLinkedOpenHashMap.this.value[this.index];
/*  996 */       Reference2FloatLinkedOpenHashMap.this.value[this.index] = v;
/*  997 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getValue() {
/* 1007 */       return Float.valueOf(Reference2FloatLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float setValue(Float v) {
/* 1017 */       return Float.valueOf(setValue(v.floatValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1022 */       if (!(o instanceof Map.Entry))
/* 1023 */         return false; 
/* 1024 */       Map.Entry<K, Float> e = (Map.Entry<K, Float>)o;
/* 1025 */       return (Reference2FloatLinkedOpenHashMap.this.key[this.index] == e.getKey() && 
/* 1026 */         Float.floatToIntBits(Reference2FloatLinkedOpenHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1030 */       return System.identityHashCode(Reference2FloatLinkedOpenHashMap.this.key[this.index]) ^ HashCommon.float2int(Reference2FloatLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1034 */       return (new StringBuilder()).append(Reference2FloatLinkedOpenHashMap.this.key[this.index]).append("=>").append(Reference2FloatLinkedOpenHashMap.this.value[this.index]).toString();
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
/* 1045 */     if (this.size == 0) {
/* 1046 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1049 */     if (this.first == i) {
/* 1050 */       this.first = (int)this.link[i];
/* 1051 */       if (0 <= this.first)
/*      */       {
/* 1053 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1057 */     if (this.last == i) {
/* 1058 */       this.last = (int)(this.link[i] >>> 32L);
/* 1059 */       if (0 <= this.last)
/*      */       {
/* 1061 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1065 */     long linki = this.link[i];
/* 1066 */     int prev = (int)(linki >>> 32L);
/* 1067 */     int next = (int)linki;
/* 1068 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1069 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1082 */     if (this.size == 1) {
/* 1083 */       this.first = this.last = d;
/*      */       
/* 1085 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1088 */     if (this.first == s) {
/* 1089 */       this.first = d;
/* 1090 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1091 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1094 */     if (this.last == s) {
/* 1095 */       this.last = d;
/* 1096 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1097 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1100 */     long links = this.link[s];
/* 1101 */     int prev = (int)(links >>> 32L);
/* 1102 */     int next = (int)links;
/* 1103 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1104 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1105 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1114 */     if (this.size == 0)
/* 1115 */       throw new NoSuchElementException(); 
/* 1116 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1125 */     if (this.size == 0)
/* 1126 */       throw new NoSuchElementException(); 
/* 1127 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2FloatSortedMap<K> tailMap(K from) {
/* 1136 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2FloatSortedMap<K> headMap(K to) {
/* 1145 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2FloatSortedMap<K> subMap(K from, K to) {
/* 1154 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1163 */     return null;
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
/* 1178 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1184 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1189 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1195 */     int index = -1;
/*      */     protected MapIterator() {
/* 1197 */       this.next = Reference2FloatLinkedOpenHashMap.this.first;
/* 1198 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/* 1201 */       if (from == null) {
/* 1202 */         if (Reference2FloatLinkedOpenHashMap.this.containsNullKey) {
/* 1203 */           this.next = (int)Reference2FloatLinkedOpenHashMap.this.link[Reference2FloatLinkedOpenHashMap.this.n];
/* 1204 */           this.prev = Reference2FloatLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1207 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1209 */       if (Reference2FloatLinkedOpenHashMap.this.key[Reference2FloatLinkedOpenHashMap.this.last] == from) {
/* 1210 */         this.prev = Reference2FloatLinkedOpenHashMap.this.last;
/* 1211 */         this.index = Reference2FloatLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1215 */       int pos = HashCommon.mix(System.identityHashCode(from)) & Reference2FloatLinkedOpenHashMap.this.mask;
/*      */       
/* 1217 */       while (Reference2FloatLinkedOpenHashMap.this.key[pos] != null) {
/* 1218 */         if (Reference2FloatLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1220 */           this.next = (int)Reference2FloatLinkedOpenHashMap.this.link[pos];
/* 1221 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1224 */         pos = pos + 1 & Reference2FloatLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1226 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1229 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1232 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1235 */       if (this.index >= 0)
/*      */         return; 
/* 1237 */       if (this.prev == -1) {
/* 1238 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1241 */       if (this.next == -1) {
/* 1242 */         this.index = Reference2FloatLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1245 */       int pos = Reference2FloatLinkedOpenHashMap.this.first;
/* 1246 */       this.index = 1;
/* 1247 */       while (pos != this.prev) {
/* 1248 */         pos = (int)Reference2FloatLinkedOpenHashMap.this.link[pos];
/* 1249 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1253 */       ensureIndexKnown();
/* 1254 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1257 */       ensureIndexKnown();
/* 1258 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1261 */       if (!hasNext())
/* 1262 */         throw new NoSuchElementException(); 
/* 1263 */       this.curr = this.next;
/* 1264 */       this.next = (int)Reference2FloatLinkedOpenHashMap.this.link[this.curr];
/* 1265 */       this.prev = this.curr;
/* 1266 */       if (this.index >= 0)
/* 1267 */         this.index++; 
/* 1268 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1271 */       if (!hasPrevious())
/* 1272 */         throw new NoSuchElementException(); 
/* 1273 */       this.curr = this.prev;
/* 1274 */       this.prev = (int)(Reference2FloatLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1275 */       this.next = this.curr;
/* 1276 */       if (this.index >= 0)
/* 1277 */         this.index--; 
/* 1278 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1281 */       ensureIndexKnown();
/* 1282 */       if (this.curr == -1)
/* 1283 */         throw new IllegalStateException(); 
/* 1284 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1289 */         this.index--;
/* 1290 */         this.prev = (int)(Reference2FloatLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1292 */         this.next = (int)Reference2FloatLinkedOpenHashMap.this.link[this.curr];
/* 1293 */       }  Reference2FloatLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1298 */       if (this.prev == -1) {
/* 1299 */         Reference2FloatLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1301 */         Reference2FloatLinkedOpenHashMap.this.link[this.prev] = Reference2FloatLinkedOpenHashMap.this.link[this.prev] ^ (Reference2FloatLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1302 */       }  if (this.next == -1) {
/* 1303 */         Reference2FloatLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1305 */         Reference2FloatLinkedOpenHashMap.this.link[this.next] = Reference2FloatLinkedOpenHashMap.this.link[this.next] ^ (Reference2FloatLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1306 */       }  int pos = this.curr;
/* 1307 */       this.curr = -1;
/* 1308 */       if (pos == Reference2FloatLinkedOpenHashMap.this.n) {
/* 1309 */         Reference2FloatLinkedOpenHashMap.this.containsNullKey = false;
/* 1310 */         Reference2FloatLinkedOpenHashMap.this.key[Reference2FloatLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1313 */         K[] key = Reference2FloatLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1317 */           pos = (last = pos) + 1 & Reference2FloatLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1319 */             if ((curr = key[pos]) == null) {
/* 1320 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1323 */             int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2FloatLinkedOpenHashMap.this.mask;
/* 1324 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1326 */             pos = pos + 1 & Reference2FloatLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1328 */           key[last] = curr;
/* 1329 */           Reference2FloatLinkedOpenHashMap.this.value[last] = Reference2FloatLinkedOpenHashMap.this.value[pos];
/* 1330 */           if (this.next == pos)
/* 1331 */             this.next = last; 
/* 1332 */           if (this.prev == pos)
/* 1333 */             this.prev = last; 
/* 1334 */           Reference2FloatLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1339 */       int i = n;
/* 1340 */       while (i-- != 0 && hasNext())
/* 1341 */         nextEntry(); 
/* 1342 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1345 */       int i = n;
/* 1346 */       while (i-- != 0 && hasPrevious())
/* 1347 */         previousEntry(); 
/* 1348 */       return n - i - 1;
/*      */     }
/*      */     public void set(Reference2FloatMap.Entry<K> ok) {
/* 1351 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Reference2FloatMap.Entry<K> ok) {
/* 1354 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Reference2FloatMap.Entry<K>> { private Reference2FloatLinkedOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1362 */       super(from);
/*      */     }
/*      */     
/*      */     public Reference2FloatLinkedOpenHashMap<K>.MapEntry next() {
/* 1366 */       return this.entry = new Reference2FloatLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Reference2FloatLinkedOpenHashMap<K>.MapEntry previous() {
/* 1370 */       return this.entry = new Reference2FloatLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1374 */       super.remove();
/* 1375 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1379 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Reference2FloatMap.Entry<K>> { final Reference2FloatLinkedOpenHashMap<K>.MapEntry entry = new Reference2FloatLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1383 */       super(from);
/*      */     }
/*      */     
/*      */     public Reference2FloatLinkedOpenHashMap<K>.MapEntry next() {
/* 1387 */       this.entry.index = nextEntry();
/* 1388 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Reference2FloatLinkedOpenHashMap<K>.MapEntry previous() {
/* 1392 */       this.entry.index = previousEntry();
/* 1393 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Reference2FloatMap.Entry<K>> implements Reference2FloatSortedMap.FastSortedEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Reference2FloatMap.Entry<K>> iterator() {
/* 1401 */       return new Reference2FloatLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Reference2FloatMap.Entry<K>> comparator() {
/* 1405 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Reference2FloatMap.Entry<K>> subSet(Reference2FloatMap.Entry<K> fromElement, Reference2FloatMap.Entry<K> toElement) {
/* 1410 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Reference2FloatMap.Entry<K>> headSet(Reference2FloatMap.Entry<K> toElement) {
/* 1414 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Reference2FloatMap.Entry<K>> tailSet(Reference2FloatMap.Entry<K> fromElement) {
/* 1418 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Reference2FloatMap.Entry<K> first() {
/* 1422 */       if (Reference2FloatLinkedOpenHashMap.this.size == 0)
/* 1423 */         throw new NoSuchElementException(); 
/* 1424 */       return new Reference2FloatLinkedOpenHashMap.MapEntry(Reference2FloatLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Reference2FloatMap.Entry<K> last() {
/* 1428 */       if (Reference2FloatLinkedOpenHashMap.this.size == 0)
/* 1429 */         throw new NoSuchElementException(); 
/* 1430 */       return new Reference2FloatLinkedOpenHashMap.MapEntry(Reference2FloatLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1435 */       if (!(o instanceof Map.Entry))
/* 1436 */         return false; 
/* 1437 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1438 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1439 */         return false; 
/* 1440 */       K k = (K)e.getKey();
/* 1441 */       float v = ((Float)e.getValue()).floatValue();
/* 1442 */       if (k == null) {
/* 1443 */         return (Reference2FloatLinkedOpenHashMap.this.containsNullKey && 
/* 1444 */           Float.floatToIntBits(Reference2FloatLinkedOpenHashMap.this.value[Reference2FloatLinkedOpenHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       }
/* 1446 */       K[] key = Reference2FloatLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1449 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2FloatLinkedOpenHashMap.this.mask]) == null)
/*      */       {
/* 1451 */         return false; } 
/* 1452 */       if (k == curr) {
/* 1453 */         return (Float.floatToIntBits(Reference2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       }
/*      */       while (true) {
/* 1456 */         if ((curr = key[pos = pos + 1 & Reference2FloatLinkedOpenHashMap.this.mask]) == null)
/* 1457 */           return false; 
/* 1458 */         if (k == curr) {
/* 1459 */           return (Float.floatToIntBits(Reference2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1465 */       if (!(o instanceof Map.Entry))
/* 1466 */         return false; 
/* 1467 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1468 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1469 */         return false; 
/* 1470 */       K k = (K)e.getKey();
/* 1471 */       float v = ((Float)e.getValue()).floatValue();
/* 1472 */       if (k == null) {
/* 1473 */         if (Reference2FloatLinkedOpenHashMap.this.containsNullKey && Float.floatToIntBits(Reference2FloatLinkedOpenHashMap.this.value[Reference2FloatLinkedOpenHashMap.this.n]) == Float.floatToIntBits(v)) {
/* 1474 */           Reference2FloatLinkedOpenHashMap.this.removeNullEntry();
/* 1475 */           return true;
/*      */         } 
/* 1477 */         return false;
/*      */       } 
/*      */       
/* 1480 */       K[] key = Reference2FloatLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1483 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2FloatLinkedOpenHashMap.this.mask]) == null)
/*      */       {
/* 1485 */         return false; } 
/* 1486 */       if (curr == k) {
/* 1487 */         if (Float.floatToIntBits(Reference2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1488 */           Reference2FloatLinkedOpenHashMap.this.removeEntry(pos);
/* 1489 */           return true;
/*      */         } 
/* 1491 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1494 */         if ((curr = key[pos = pos + 1 & Reference2FloatLinkedOpenHashMap.this.mask]) == null)
/* 1495 */           return false; 
/* 1496 */         if (curr == k && 
/* 1497 */           Float.floatToIntBits(Reference2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1498 */           Reference2FloatLinkedOpenHashMap.this.removeEntry(pos);
/* 1499 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1506 */       return Reference2FloatLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1510 */       Reference2FloatLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Reference2FloatMap.Entry<K>> iterator(Reference2FloatMap.Entry<K> from) {
/* 1525 */       return new Reference2FloatLinkedOpenHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Reference2FloatMap.Entry<K>> fastIterator() {
/* 1536 */       return new Reference2FloatLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Reference2FloatMap.Entry<K>> fastIterator(Reference2FloatMap.Entry<K> from) {
/* 1551 */       return new Reference2FloatLinkedOpenHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2FloatMap.Entry<K>> consumer) {
/* 1556 */       for (int i = Reference2FloatLinkedOpenHashMap.this.size, next = Reference2FloatLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1557 */         int curr = next;
/* 1558 */         next = (int)Reference2FloatLinkedOpenHashMap.this.link[curr];
/* 1559 */         consumer.accept(new AbstractReference2FloatMap.BasicEntry<>(Reference2FloatLinkedOpenHashMap.this.key[curr], Reference2FloatLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2FloatMap.Entry<K>> consumer) {
/* 1565 */       AbstractReference2FloatMap.BasicEntry<K> entry = new AbstractReference2FloatMap.BasicEntry<>();
/* 1566 */       for (int i = Reference2FloatLinkedOpenHashMap.this.size, next = Reference2FloatLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1567 */         int curr = next;
/* 1568 */         next = (int)Reference2FloatLinkedOpenHashMap.this.link[curr];
/* 1569 */         entry.key = Reference2FloatLinkedOpenHashMap.this.key[curr];
/* 1570 */         entry.value = Reference2FloatLinkedOpenHashMap.this.value[curr];
/* 1571 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Reference2FloatSortedMap.FastSortedEntrySet<K> reference2FloatEntrySet() {
/* 1577 */     if (this.entries == null)
/* 1578 */       this.entries = new MapEntrySet(); 
/* 1579 */     return this.entries;
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
/* 1592 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1596 */       return Reference2FloatLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1603 */       return Reference2FloatLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1609 */       return new Reference2FloatLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1613 */       return new Reference2FloatLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1618 */       if (Reference2FloatLinkedOpenHashMap.this.containsNullKey)
/* 1619 */         consumer.accept(Reference2FloatLinkedOpenHashMap.this.key[Reference2FloatLinkedOpenHashMap.this.n]); 
/* 1620 */       for (int pos = Reference2FloatLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1621 */         K k = Reference2FloatLinkedOpenHashMap.this.key[pos];
/* 1622 */         if (k != null)
/* 1623 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1628 */       return Reference2FloatLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1632 */       return Reference2FloatLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1636 */       int oldSize = Reference2FloatLinkedOpenHashMap.this.size;
/* 1637 */       Reference2FloatLinkedOpenHashMap.this.removeFloat(k);
/* 1638 */       return (Reference2FloatLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1642 */       Reference2FloatLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1646 */       if (Reference2FloatLinkedOpenHashMap.this.size == 0)
/* 1647 */         throw new NoSuchElementException(); 
/* 1648 */       return Reference2FloatLinkedOpenHashMap.this.key[Reference2FloatLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1652 */       if (Reference2FloatLinkedOpenHashMap.this.size == 0)
/* 1653 */         throw new NoSuchElementException(); 
/* 1654 */       return Reference2FloatLinkedOpenHashMap.this.key[Reference2FloatLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1658 */       return null;
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> tailSet(K from) {
/* 1662 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> headSet(K to) {
/* 1666 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> subSet(K from, K to) {
/* 1670 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ReferenceSortedSet<K> keySet() {
/* 1675 */     if (this.keys == null)
/* 1676 */       this.keys = new KeySet(); 
/* 1677 */     return this.keys;
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
/*      */     implements FloatListIterator
/*      */   {
/*      */     public float previousFloat() {
/* 1691 */       return Reference2FloatLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1698 */       return Reference2FloatLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public FloatCollection values() {
/* 1703 */     if (this.values == null)
/* 1704 */       this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1707 */             return (FloatIterator)new Reference2FloatLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1711 */             return Reference2FloatLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(float v) {
/* 1715 */             return Reference2FloatLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1719 */             Reference2FloatLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(DoubleConsumer consumer) {
/* 1724 */             if (Reference2FloatLinkedOpenHashMap.this.containsNullKey)
/* 1725 */               consumer.accept(Reference2FloatLinkedOpenHashMap.this.value[Reference2FloatLinkedOpenHashMap.this.n]); 
/* 1726 */             for (int pos = Reference2FloatLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1727 */               if (Reference2FloatLinkedOpenHashMap.this.key[pos] != null)
/* 1728 */                 consumer.accept(Reference2FloatLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1731 */     return this.values;
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
/* 1748 */     return trim(this.size);
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
/* 1772 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1773 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1774 */       return true; 
/*      */     try {
/* 1776 */       rehash(l);
/* 1777 */     } catch (OutOfMemoryError cantDoIt) {
/* 1778 */       return false;
/*      */     } 
/* 1780 */     return true;
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
/* 1796 */     K[] key = this.key;
/* 1797 */     float[] value = this.value;
/* 1798 */     int mask = newN - 1;
/* 1799 */     K[] newKey = (K[])new Object[newN + 1];
/* 1800 */     float[] newValue = new float[newN + 1];
/* 1801 */     int i = this.first, prev = -1, newPrev = -1;
/* 1802 */     long[] link = this.link;
/* 1803 */     long[] newLink = new long[newN + 1];
/* 1804 */     this.first = -1;
/* 1805 */     for (int j = this.size; j-- != 0; ) {
/* 1806 */       int pos; if (key[i] == null) {
/* 1807 */         pos = newN;
/*      */       } else {
/* 1809 */         pos = HashCommon.mix(System.identityHashCode(key[i])) & mask;
/* 1810 */         while (newKey[pos] != null)
/* 1811 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1813 */       newKey[pos] = key[i];
/* 1814 */       newValue[pos] = value[i];
/* 1815 */       if (prev != -1) {
/* 1816 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1817 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1818 */         newPrev = pos;
/*      */       } else {
/* 1820 */         newPrev = this.first = pos;
/*      */         
/* 1822 */         newLink[pos] = -1L;
/*      */       } 
/* 1824 */       int t = i;
/* 1825 */       i = (int)link[i];
/* 1826 */       prev = t;
/*      */     } 
/* 1828 */     this.link = newLink;
/* 1829 */     this.last = newPrev;
/* 1830 */     if (newPrev != -1)
/*      */     {
/* 1832 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1833 */     this.n = newN;
/* 1834 */     this.mask = mask;
/* 1835 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1836 */     this.key = newKey;
/* 1837 */     this.value = newValue;
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
/*      */   public Reference2FloatLinkedOpenHashMap<K> clone() {
/*      */     Reference2FloatLinkedOpenHashMap<K> c;
/*      */     try {
/* 1854 */       c = (Reference2FloatLinkedOpenHashMap<K>)super.clone();
/* 1855 */     } catch (CloneNotSupportedException cantHappen) {
/* 1856 */       throw new InternalError();
/*      */     } 
/* 1858 */     c.keys = null;
/* 1859 */     c.values = null;
/* 1860 */     c.entries = null;
/* 1861 */     c.containsNullKey = this.containsNullKey;
/* 1862 */     c.key = (K[])this.key.clone();
/* 1863 */     c.value = (float[])this.value.clone();
/* 1864 */     c.link = (long[])this.link.clone();
/* 1865 */     return c;
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
/* 1878 */     int h = 0;
/* 1879 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1880 */       while (this.key[i] == null)
/* 1881 */         i++; 
/* 1882 */       if (this != this.key[i])
/* 1883 */         t = System.identityHashCode(this.key[i]); 
/* 1884 */       t ^= HashCommon.float2int(this.value[i]);
/* 1885 */       h += t;
/* 1886 */       i++;
/*      */     } 
/*      */     
/* 1889 */     if (this.containsNullKey)
/* 1890 */       h += HashCommon.float2int(this.value[this.n]); 
/* 1891 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1894 */     K[] key = this.key;
/* 1895 */     float[] value = this.value;
/* 1896 */     MapIterator i = new MapIterator();
/* 1897 */     s.defaultWriteObject();
/* 1898 */     for (int j = this.size; j-- != 0; ) {
/* 1899 */       int e = i.nextEntry();
/* 1900 */       s.writeObject(key[e]);
/* 1901 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1906 */     s.defaultReadObject();
/* 1907 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1908 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1909 */     this.mask = this.n - 1;
/* 1910 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1911 */     float[] value = this.value = new float[this.n + 1];
/* 1912 */     long[] link = this.link = new long[this.n + 1];
/* 1913 */     int prev = -1;
/* 1914 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1917 */     for (int i = this.size; i-- != 0; ) {
/* 1918 */       int pos; K k = (K)s.readObject();
/* 1919 */       float v = s.readFloat();
/* 1920 */       if (k == null) {
/* 1921 */         pos = this.n;
/* 1922 */         this.containsNullKey = true;
/*      */       } else {
/* 1924 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 1925 */         while (key[pos] != null)
/* 1926 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1928 */       key[pos] = k;
/* 1929 */       value[pos] = v;
/* 1930 */       if (this.first != -1) {
/* 1931 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1932 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1933 */         prev = pos; continue;
/*      */       } 
/* 1935 */       prev = this.first = pos;
/*      */       
/* 1937 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1940 */     this.last = prev;
/* 1941 */     if (prev != -1)
/*      */     {
/* 1943 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2FloatLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */