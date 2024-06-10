/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.SortedSet;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ReferenceLinkedOpenHashSet<K>
/*      */   extends AbstractReferenceSortedSet<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNull;
/*   97 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  102 */   protected transient int last = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient long[] link;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient int n;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient int maxFill;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final transient int minN;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int size;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final float f;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceLinkedOpenHashSet(int expected, float f) {
/*  140 */     if (f <= 0.0F || f > 1.0F)
/*  141 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  142 */     if (expected < 0)
/*  143 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  144 */     this.f = f;
/*  145 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  146 */     this.mask = this.n - 1;
/*  147 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  148 */     this.key = (K[])new Object[this.n + 1];
/*  149 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceLinkedOpenHashSet(int expected) {
/*  158 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceLinkedOpenHashSet() {
/*  166 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceLinkedOpenHashSet(Collection<? extends K> c, float f) {
/*  177 */     this(c.size(), f);
/*  178 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceLinkedOpenHashSet(Collection<? extends K> c) {
/*  188 */     this(c, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceLinkedOpenHashSet(ReferenceCollection<? extends K> c, float f) {
/*  199 */     this(c.size(), f);
/*  200 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceLinkedOpenHashSet(ReferenceCollection<? extends K> c) {
/*  210 */     this(c, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceLinkedOpenHashSet(Iterator<? extends K> i, float f) {
/*  221 */     this(16, f);
/*  222 */     while (i.hasNext()) {
/*  223 */       add(i.next());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceLinkedOpenHashSet(Iterator<? extends K> i) {
/*  233 */     this(i, 0.75F);
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
/*      */   public ReferenceLinkedOpenHashSet(K[] a, int offset, int length, float f) {
/*  248 */     this((length < 0) ? 0 : length, f);
/*  249 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/*  250 */     for (int i = 0; i < length; i++) {
/*  251 */       add(a[offset + i]);
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
/*      */   public ReferenceLinkedOpenHashSet(K[] a, int offset, int length) {
/*  265 */     this(a, offset, length, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceLinkedOpenHashSet(K[] a, float f) {
/*  276 */     this(a, 0, a.length, f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceLinkedOpenHashSet(K[] a) {
/*  286 */     this(a, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  289 */     return this.containsNull ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  292 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  293 */     if (needed > this.n)
/*  294 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  297 */     int needed = (int)Math.min(1073741824L, 
/*  298 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  299 */     if (needed > this.n) {
/*  300 */       rehash(needed);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean addAll(Collection<? extends K> c) {
/*  305 */     if (this.f <= 0.5D) {
/*  306 */       ensureCapacity(c.size());
/*      */     } else {
/*  308 */       tryCapacity((size() + c.size()));
/*      */     } 
/*  310 */     return super.addAll(c);
/*      */   }
/*      */   
/*      */   public boolean add(K k) {
/*      */     int pos;
/*  315 */     if (k == null) {
/*  316 */       if (this.containsNull)
/*  317 */         return false; 
/*  318 */       pos = this.n;
/*  319 */       this.containsNull = true;
/*      */     } else {
/*      */       
/*  322 */       K[] key = this.key;
/*      */       K curr;
/*  324 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*      */         
/*  326 */         if (curr == k)
/*  327 */           return false; 
/*  328 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  329 */           if (curr == k)
/*  330 */             return false; 
/*      */         } 
/*  332 */       }  key[pos] = k;
/*      */     } 
/*  334 */     if (this.size == 0) {
/*  335 */       this.first = this.last = pos;
/*      */       
/*  337 */       this.link[pos] = -1L;
/*      */     } else {
/*  339 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  340 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  341 */       this.last = pos;
/*      */     } 
/*  343 */     if (this.size++ >= this.maxFill) {
/*  344 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  347 */     return true;
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
/*  360 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  362 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  364 */         if ((curr = key[pos]) == null) {
/*  365 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  368 */         int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
/*  369 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  371 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  373 */       key[last] = curr;
/*  374 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  378 */     this.size--;
/*  379 */     fixPointers(pos);
/*  380 */     shiftKeys(pos);
/*  381 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  382 */       rehash(this.n / 2); 
/*  383 */     return true;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  386 */     this.containsNull = false;
/*  387 */     this.key[this.n] = null;
/*  388 */     this.size--;
/*  389 */     fixPointers(this.n);
/*  390 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  391 */       rehash(this.n / 2); 
/*  392 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(Object k) {
/*  397 */     if (k == null) {
/*  398 */       if (this.containsNull)
/*  399 */         return removeNullEntry(); 
/*  400 */       return false;
/*      */     } 
/*      */     
/*  403 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  406 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  407 */       return false; 
/*  408 */     if (k == curr)
/*  409 */       return removeEntry(pos); 
/*      */     while (true) {
/*  411 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  412 */         return false; 
/*  413 */       if (k == curr) {
/*  414 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean contains(Object k) {
/*  420 */     if (k == null) {
/*  421 */       return this.containsNull;
/*      */     }
/*  423 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  426 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  427 */       return false; 
/*  428 */     if (k == curr)
/*  429 */       return true; 
/*      */     while (true) {
/*  431 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  432 */         return false; 
/*  433 */       if (k == curr) {
/*  434 */         return true;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K removeFirst() {
/*  445 */     if (this.size == 0)
/*  446 */       throw new NoSuchElementException(); 
/*  447 */     int pos = this.first;
/*      */     
/*  449 */     this.first = (int)this.link[pos];
/*  450 */     if (0 <= this.first)
/*      */     {
/*  452 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  454 */     K k = this.key[pos];
/*  455 */     this.size--;
/*  456 */     if (k == null) {
/*  457 */       this.containsNull = false;
/*  458 */       this.key[this.n] = null;
/*      */     } else {
/*  460 */       shiftKeys(pos);
/*  461 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  462 */       rehash(this.n / 2); 
/*  463 */     return k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K removeLast() {
/*  473 */     if (this.size == 0)
/*  474 */       throw new NoSuchElementException(); 
/*  475 */     int pos = this.last;
/*      */     
/*  477 */     this.last = (int)(this.link[pos] >>> 32L);
/*  478 */     if (0 <= this.last)
/*      */     {
/*  480 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  482 */     K k = this.key[pos];
/*  483 */     this.size--;
/*  484 */     if (k == null) {
/*  485 */       this.containsNull = false;
/*  486 */       this.key[this.n] = null;
/*      */     } else {
/*  488 */       shiftKeys(pos);
/*  489 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  490 */       rehash(this.n / 2); 
/*  491 */     return k;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  494 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  496 */     if (this.last == i) {
/*  497 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  499 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  501 */       long linki = this.link[i];
/*  502 */       int prev = (int)(linki >>> 32L);
/*  503 */       int next = (int)linki;
/*  504 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  505 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  507 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  508 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  509 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  512 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  514 */     if (this.first == i) {
/*  515 */       this.first = (int)this.link[i];
/*      */       
/*  517 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  519 */       long linki = this.link[i];
/*  520 */       int prev = (int)(linki >>> 32L);
/*  521 */       int next = (int)linki;
/*  522 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  523 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  525 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  526 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  527 */     this.last = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAndMoveToFirst(K k) {
/*      */     int pos;
/*  539 */     if (k == null) {
/*  540 */       if (this.containsNull) {
/*  541 */         moveIndexToFirst(this.n);
/*  542 */         return false;
/*      */       } 
/*  544 */       this.containsNull = true;
/*  545 */       pos = this.n;
/*      */     } else {
/*      */       
/*  548 */       K[] key = this.key;
/*  549 */       pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/*      */       
/*  551 */       while (key[pos] != null) {
/*  552 */         if (k == key[pos]) {
/*  553 */           moveIndexToFirst(pos);
/*  554 */           return false;
/*      */         } 
/*  556 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  559 */     this.key[pos] = k;
/*  560 */     if (this.size == 0) {
/*  561 */       this.first = this.last = pos;
/*      */       
/*  563 */       this.link[pos] = -1L;
/*      */     } else {
/*  565 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  566 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  567 */       this.first = pos;
/*      */     } 
/*  569 */     if (this.size++ >= this.maxFill) {
/*  570 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  573 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAndMoveToLast(K k) {
/*      */     int pos;
/*  585 */     if (k == null) {
/*  586 */       if (this.containsNull) {
/*  587 */         moveIndexToLast(this.n);
/*  588 */         return false;
/*      */       } 
/*  590 */       this.containsNull = true;
/*  591 */       pos = this.n;
/*      */     } else {
/*      */       
/*  594 */       K[] key = this.key;
/*  595 */       pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/*      */       
/*  597 */       while (key[pos] != null) {
/*  598 */         if (k == key[pos]) {
/*  599 */           moveIndexToLast(pos);
/*  600 */           return false;
/*      */         } 
/*  602 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  605 */     this.key[pos] = k;
/*  606 */     if (this.size == 0) {
/*  607 */       this.first = this.last = pos;
/*      */       
/*  609 */       this.link[pos] = -1L;
/*      */     } else {
/*  611 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  612 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  613 */       this.last = pos;
/*      */     } 
/*  615 */     if (this.size++ >= this.maxFill) {
/*  616 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  619 */     return true;
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
/*  630 */     if (this.size == 0)
/*      */       return; 
/*  632 */     this.size = 0;
/*  633 */     this.containsNull = false;
/*  634 */     Arrays.fill((Object[])this.key, (Object)null);
/*  635 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  639 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  643 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int i) {
/*  653 */     if (this.size == 0) {
/*  654 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  657 */     if (this.first == i) {
/*  658 */       this.first = (int)this.link[i];
/*  659 */       if (0 <= this.first)
/*      */       {
/*  661 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  665 */     if (this.last == i) {
/*  666 */       this.last = (int)(this.link[i] >>> 32L);
/*  667 */       if (0 <= this.last)
/*      */       {
/*  669 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  673 */     long linki = this.link[i];
/*  674 */     int prev = (int)(linki >>> 32L);
/*  675 */     int next = (int)linki;
/*  676 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  677 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*      */   protected void fixPointers(int s, int d) {
/*  689 */     if (this.size == 1) {
/*  690 */       this.first = this.last = d;
/*      */       
/*  692 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  695 */     if (this.first == s) {
/*  696 */       this.first = d;
/*  697 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  698 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  701 */     if (this.last == s) {
/*  702 */       this.last = d;
/*  703 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  704 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  707 */     long links = this.link[s];
/*  708 */     int prev = (int)(links >>> 32L);
/*  709 */     int next = (int)links;
/*  710 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  711 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  712 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K first() {
/*  721 */     if (this.size == 0)
/*  722 */       throw new NoSuchElementException(); 
/*  723 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K last() {
/*  732 */     if (this.size == 0)
/*  733 */       throw new NoSuchElementException(); 
/*  734 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceSortedSet<K> tailSet(K from) {
/*  743 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceSortedSet<K> headSet(K to) {
/*  752 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceSortedSet<K> subSet(K from, K to) {
/*  761 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/*  770 */     return null;
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
/*      */   private class SetIterator
/*      */     implements ObjectListIterator<K>
/*      */   {
/*  785 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  791 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  796 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  801 */     int index = -1;
/*      */     SetIterator() {
/*  803 */       this.next = ReferenceLinkedOpenHashSet.this.first;
/*  804 */       this.index = 0;
/*      */     }
/*      */     SetIterator(K from) {
/*  807 */       if (from == null) {
/*  808 */         if (ReferenceLinkedOpenHashSet.this.containsNull) {
/*  809 */           this.next = (int)ReferenceLinkedOpenHashSet.this.link[ReferenceLinkedOpenHashSet.this.n];
/*  810 */           this.prev = ReferenceLinkedOpenHashSet.this.n;
/*      */           return;
/*      */         } 
/*  813 */         throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */       } 
/*  815 */       if (ReferenceLinkedOpenHashSet.this.key[ReferenceLinkedOpenHashSet.this.last] == from) {
/*  816 */         this.prev = ReferenceLinkedOpenHashSet.this.last;
/*  817 */         this.index = ReferenceLinkedOpenHashSet.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  821 */       K[] key = ReferenceLinkedOpenHashSet.this.key;
/*  822 */       int pos = HashCommon.mix(System.identityHashCode(from)) & ReferenceLinkedOpenHashSet.this.mask;
/*      */       
/*  824 */       while (key[pos] != null) {
/*  825 */         if (key[pos] == from) {
/*      */           
/*  827 */           this.next = (int)ReferenceLinkedOpenHashSet.this.link[pos];
/*  828 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  831 */         pos = pos + 1 & ReferenceLinkedOpenHashSet.this.mask;
/*      */       } 
/*  833 */       throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  837 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  841 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     public K next() {
/*  845 */       if (!hasNext())
/*  846 */         throw new NoSuchElementException(); 
/*  847 */       this.curr = this.next;
/*  848 */       this.next = (int)ReferenceLinkedOpenHashSet.this.link[this.curr];
/*  849 */       this.prev = this.curr;
/*  850 */       if (this.index >= 0) {
/*  851 */         this.index++;
/*      */       }
/*      */       
/*  854 */       return ReferenceLinkedOpenHashSet.this.key[this.curr];
/*      */     }
/*      */     
/*      */     public K previous() {
/*  858 */       if (!hasPrevious())
/*  859 */         throw new NoSuchElementException(); 
/*  860 */       this.curr = this.prev;
/*  861 */       this.prev = (int)(ReferenceLinkedOpenHashSet.this.link[this.curr] >>> 32L);
/*  862 */       this.next = this.curr;
/*  863 */       if (this.index >= 0)
/*  864 */         this.index--; 
/*  865 */       return ReferenceLinkedOpenHashSet.this.key[this.curr];
/*      */     }
/*      */     private final void ensureIndexKnown() {
/*  868 */       if (this.index >= 0)
/*      */         return; 
/*  870 */       if (this.prev == -1) {
/*  871 */         this.index = 0;
/*      */         return;
/*      */       } 
/*  874 */       if (this.next == -1) {
/*  875 */         this.index = ReferenceLinkedOpenHashSet.this.size;
/*      */         return;
/*      */       } 
/*  878 */       int pos = ReferenceLinkedOpenHashSet.this.first;
/*  879 */       this.index = 1;
/*  880 */       while (pos != this.prev) {
/*  881 */         pos = (int)ReferenceLinkedOpenHashSet.this.link[pos];
/*  882 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  887 */       ensureIndexKnown();
/*  888 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  892 */       ensureIndexKnown();
/*  893 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  897 */       ensureIndexKnown();
/*  898 */       if (this.curr == -1)
/*  899 */         throw new IllegalStateException(); 
/*  900 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  905 */         this.index--;
/*  906 */         this.prev = (int)(ReferenceLinkedOpenHashSet.this.link[this.curr] >>> 32L);
/*      */       } else {
/*  908 */         this.next = (int)ReferenceLinkedOpenHashSet.this.link[this.curr];
/*  909 */       }  ReferenceLinkedOpenHashSet.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  914 */       if (this.prev == -1) {
/*  915 */         ReferenceLinkedOpenHashSet.this.first = this.next;
/*      */       } else {
/*  917 */         ReferenceLinkedOpenHashSet.this.link[this.prev] = ReferenceLinkedOpenHashSet.this.link[this.prev] ^ (ReferenceLinkedOpenHashSet.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  918 */       }  if (this.next == -1) {
/*  919 */         ReferenceLinkedOpenHashSet.this.last = this.prev;
/*      */       } else {
/*  921 */         ReferenceLinkedOpenHashSet.this.link[this.next] = ReferenceLinkedOpenHashSet.this.link[this.next] ^ (ReferenceLinkedOpenHashSet.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  922 */       }  int pos = this.curr;
/*  923 */       this.curr = -1;
/*  924 */       if (pos == ReferenceLinkedOpenHashSet.this.n) {
/*  925 */         ReferenceLinkedOpenHashSet.this.containsNull = false;
/*  926 */         ReferenceLinkedOpenHashSet.this.key[ReferenceLinkedOpenHashSet.this.n] = null;
/*      */       } else {
/*      */         
/*  929 */         K[] key = ReferenceLinkedOpenHashSet.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/*  933 */           pos = (last = pos) + 1 & ReferenceLinkedOpenHashSet.this.mask;
/*      */           while (true) {
/*  935 */             if ((curr = key[pos]) == null) {
/*  936 */               key[last] = null;
/*      */               return;
/*      */             } 
/*  939 */             int slot = HashCommon.mix(System.identityHashCode(curr)) & ReferenceLinkedOpenHashSet.this.mask;
/*  940 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/*  942 */             pos = pos + 1 & ReferenceLinkedOpenHashSet.this.mask;
/*      */           } 
/*  944 */           key[last] = curr;
/*  945 */           if (this.next == pos)
/*  946 */             this.next = last; 
/*  947 */           if (this.prev == pos)
/*  948 */             this.prev = last; 
/*  949 */           ReferenceLinkedOpenHashSet.this.fixPointers(pos, last);
/*      */         } 
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
/*      */   
/*      */   public ObjectListIterator<K> iterator(K from) {
/*  967 */     return new SetIterator(from);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectListIterator<K> iterator() {
/*  978 */     return new SetIterator();
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
/*  995 */     return trim(this.size);
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
/* 1019 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1020 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1021 */       return true; 
/*      */     try {
/* 1023 */       rehash(l);
/* 1024 */     } catch (OutOfMemoryError cantDoIt) {
/* 1025 */       return false;
/*      */     } 
/* 1027 */     return true;
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
/* 1043 */     K[] key = this.key;
/* 1044 */     int mask = newN - 1;
/* 1045 */     K[] newKey = (K[])new Object[newN + 1];
/* 1046 */     int i = this.first, prev = -1, newPrev = -1;
/* 1047 */     long[] link = this.link;
/* 1048 */     long[] newLink = new long[newN + 1];
/* 1049 */     this.first = -1;
/* 1050 */     for (int j = this.size; j-- != 0; ) {
/* 1051 */       int pos; if (key[i] == null) {
/* 1052 */         pos = newN;
/*      */       } else {
/* 1054 */         pos = HashCommon.mix(System.identityHashCode(key[i])) & mask;
/* 1055 */         while (newKey[pos] != null)
/* 1056 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1058 */       newKey[pos] = key[i];
/* 1059 */       if (prev != -1) {
/* 1060 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1061 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1062 */         newPrev = pos;
/*      */       } else {
/* 1064 */         newPrev = this.first = pos;
/*      */         
/* 1066 */         newLink[pos] = -1L;
/*      */       } 
/* 1068 */       int t = i;
/* 1069 */       i = (int)link[i];
/* 1070 */       prev = t;
/*      */     } 
/* 1072 */     this.link = newLink;
/* 1073 */     this.last = newPrev;
/* 1074 */     if (newPrev != -1)
/*      */     {
/* 1076 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1077 */     this.n = newN;
/* 1078 */     this.mask = mask;
/* 1079 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1080 */     this.key = newKey;
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
/*      */   public ReferenceLinkedOpenHashSet<K> clone() {
/*      */     ReferenceLinkedOpenHashSet<K> c;
/*      */     try {
/* 1097 */       c = (ReferenceLinkedOpenHashSet<K>)super.clone();
/* 1098 */     } catch (CloneNotSupportedException cantHappen) {
/* 1099 */       throw new InternalError();
/*      */     } 
/* 1101 */     c.key = (K[])this.key.clone();
/* 1102 */     c.containsNull = this.containsNull;
/* 1103 */     c.link = (long[])this.link.clone();
/* 1104 */     return c;
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
/* 1117 */     int h = 0;
/* 1118 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 1119 */       while (this.key[i] == null)
/* 1120 */         i++; 
/* 1121 */       if (this != this.key[i])
/* 1122 */         h += System.identityHashCode(this.key[i]); 
/* 1123 */       i++;
/*      */     } 
/*      */     
/* 1126 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1129 */     ObjectIterator<K> i = iterator();
/* 1130 */     s.defaultWriteObject();
/* 1131 */     for (int j = this.size; j-- != 0;)
/* 1132 */       s.writeObject(i.next()); 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1136 */     s.defaultReadObject();
/* 1137 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1138 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1139 */     this.mask = this.n - 1;
/* 1140 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1141 */     long[] link = this.link = new long[this.n + 1];
/* 1142 */     int prev = -1;
/* 1143 */     this.first = this.last = -1;
/*      */     
/* 1145 */     for (int i = this.size; i-- != 0; ) {
/* 1146 */       int pos; K k = (K)s.readObject();
/* 1147 */       if (k == null) {
/* 1148 */         pos = this.n;
/* 1149 */         this.containsNull = true;
/*      */       }
/* 1151 */       else if (key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask] != null) {
/* 1152 */         while (key[pos = pos + 1 & this.mask] != null);
/*      */       } 
/* 1154 */       key[pos] = k;
/* 1155 */       if (this.first != -1) {
/* 1156 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1157 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1158 */         prev = pos; continue;
/*      */       } 
/* 1160 */       prev = this.first = pos;
/*      */       
/* 1162 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1165 */     this.last = prev;
/* 1166 */     if (prev != -1)
/*      */     {
/* 1168 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ReferenceLinkedOpenHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */