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
/*      */ 
/*      */ public class ObjectLinkedOpenCustomHashSet<K>
/*      */   extends AbstractObjectSortedSet<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNull;
/*      */   protected Hash.Strategy<? super K> strategy;
/*   99 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  104 */   protected transient int last = -1;
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
/*      */   
/*      */   protected transient int n;
/*      */ 
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
/*      */   public ObjectLinkedOpenCustomHashSet(int expected, float f, Hash.Strategy<? super K> strategy) {
/*  144 */     this.strategy = strategy;
/*  145 */     if (f <= 0.0F || f > 1.0F)
/*  146 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  147 */     if (expected < 0)
/*  148 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  149 */     this.f = f;
/*  150 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  151 */     this.mask = this.n - 1;
/*  152 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  153 */     this.key = (K[])new Object[this.n + 1];
/*  154 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenCustomHashSet(int expected, Hash.Strategy<? super K> strategy) {
/*  165 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenCustomHashSet(Hash.Strategy<? super K> strategy) {
/*  176 */     this(16, 0.75F, strategy);
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
/*      */   public ObjectLinkedOpenCustomHashSet(Collection<? extends K> c, float f, Hash.Strategy<? super K> strategy) {
/*  190 */     this(c.size(), f, strategy);
/*  191 */     addAll(c);
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
/*      */   public ObjectLinkedOpenCustomHashSet(Collection<? extends K> c, Hash.Strategy<? super K> strategy) {
/*  203 */     this(c, 0.75F, strategy);
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
/*      */   public ObjectLinkedOpenCustomHashSet(ObjectCollection<? extends K> c, float f, Hash.Strategy<? super K> strategy) {
/*  217 */     this(c.size(), f, strategy);
/*  218 */     addAll(c);
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
/*      */   public ObjectLinkedOpenCustomHashSet(ObjectCollection<? extends K> c, Hash.Strategy<? super K> strategy) {
/*  230 */     this(c, 0.75F, strategy);
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
/*      */   public ObjectLinkedOpenCustomHashSet(Iterator<? extends K> i, float f, Hash.Strategy<? super K> strategy) {
/*  244 */     this(16, f, strategy);
/*  245 */     while (i.hasNext()) {
/*  246 */       add(i.next());
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
/*      */   public ObjectLinkedOpenCustomHashSet(Iterator<? extends K> i, Hash.Strategy<? super K> strategy) {
/*  258 */     this(i, 0.75F, strategy);
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
/*      */   public ObjectLinkedOpenCustomHashSet(K[] a, int offset, int length, float f, Hash.Strategy<? super K> strategy) {
/*  276 */     this((length < 0) ? 0 : length, f, strategy);
/*  277 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/*  278 */     for (int i = 0; i < length; i++) {
/*  279 */       add(a[offset + i]);
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
/*      */   
/*      */   public ObjectLinkedOpenCustomHashSet(K[] a, int offset, int length, Hash.Strategy<? super K> strategy) {
/*  296 */     this(a, offset, length, 0.75F, strategy);
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
/*      */   public ObjectLinkedOpenCustomHashSet(K[] a, float f, Hash.Strategy<? super K> strategy) {
/*  309 */     this(a, 0, a.length, f, strategy);
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
/*      */   public ObjectLinkedOpenCustomHashSet(K[] a, Hash.Strategy<? super K> strategy) {
/*  321 */     this(a, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  329 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  332 */     return this.containsNull ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  335 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  336 */     if (needed > this.n)
/*  337 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  340 */     int needed = (int)Math.min(1073741824L, 
/*  341 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  342 */     if (needed > this.n) {
/*  343 */       rehash(needed);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean addAll(Collection<? extends K> c) {
/*  348 */     if (this.f <= 0.5D) {
/*  349 */       ensureCapacity(c.size());
/*      */     } else {
/*  351 */       tryCapacity((size() + c.size()));
/*      */     } 
/*  353 */     return super.addAll(c);
/*      */   }
/*      */   
/*      */   public boolean add(K k) {
/*      */     int pos;
/*  358 */     if (this.strategy.equals(k, null)) {
/*  359 */       if (this.containsNull)
/*  360 */         return false; 
/*  361 */       pos = this.n;
/*  362 */       this.containsNull = true;
/*  363 */       this.key[this.n] = k;
/*      */     } else {
/*      */       
/*  366 */       K[] key = this.key;
/*      */       K curr;
/*  368 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  369 */         if (this.strategy.equals(curr, k))
/*  370 */           return false; 
/*  371 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  372 */           if (this.strategy.equals(curr, k))
/*  373 */             return false; 
/*      */         } 
/*  375 */       }  key[pos] = k;
/*      */     } 
/*  377 */     if (this.size == 0) {
/*  378 */       this.first = this.last = pos;
/*      */       
/*  380 */       this.link[pos] = -1L;
/*      */     } else {
/*  382 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  383 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  384 */       this.last = pos;
/*      */     } 
/*  386 */     if (this.size++ >= this.maxFill) {
/*  387 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  390 */     return true;
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
/*      */   public K addOrGet(K k) {
/*      */     int pos;
/*  408 */     if (this.strategy.equals(k, null)) {
/*  409 */       if (this.containsNull)
/*  410 */         return this.key[this.n]; 
/*  411 */       pos = this.n;
/*  412 */       this.containsNull = true;
/*  413 */       this.key[this.n] = k;
/*      */     } else {
/*      */       
/*  416 */       K[] key = this.key;
/*      */       K curr;
/*  418 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  419 */         if (this.strategy.equals(curr, k))
/*  420 */           return curr; 
/*  421 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  422 */           if (this.strategy.equals(curr, k))
/*  423 */             return curr; 
/*      */         } 
/*  425 */       }  key[pos] = k;
/*      */     } 
/*  427 */     if (this.size == 0) {
/*  428 */       this.first = this.last = pos;
/*      */       
/*  430 */       this.link[pos] = -1L;
/*      */     } else {
/*  432 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  433 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  434 */       this.last = pos;
/*      */     } 
/*  436 */     if (this.size++ >= this.maxFill) {
/*  437 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  440 */     return k;
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
/*  453 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  455 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  457 */         if ((curr = key[pos]) == null) {
/*  458 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  461 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  462 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  464 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  466 */       key[last] = curr;
/*  467 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  471 */     this.size--;
/*  472 */     fixPointers(pos);
/*  473 */     shiftKeys(pos);
/*  474 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  475 */       rehash(this.n / 2); 
/*  476 */     return true;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  479 */     this.containsNull = false;
/*  480 */     this.key[this.n] = null;
/*  481 */     this.size--;
/*  482 */     fixPointers(this.n);
/*  483 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  484 */       rehash(this.n / 2); 
/*  485 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(Object k) {
/*  490 */     if (this.strategy.equals(k, null)) {
/*  491 */       if (this.containsNull)
/*  492 */         return removeNullEntry(); 
/*  493 */       return false;
/*      */     } 
/*      */     
/*  496 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  499 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  500 */       return false; 
/*  501 */     if (this.strategy.equals(k, curr))
/*  502 */       return removeEntry(pos); 
/*      */     while (true) {
/*  504 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  505 */         return false; 
/*  506 */       if (this.strategy.equals(k, curr)) {
/*  507 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean contains(Object k) {
/*  513 */     if (this.strategy.equals(k, null)) {
/*  514 */       return this.containsNull;
/*      */     }
/*  516 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  519 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  520 */       return false; 
/*  521 */     if (this.strategy.equals(k, curr))
/*  522 */       return true; 
/*      */     while (true) {
/*  524 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  525 */         return false; 
/*  526 */       if (this.strategy.equals(k, curr)) {
/*  527 */         return true;
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
/*      */   public K get(Object k) {
/*  539 */     if (this.strategy.equals(k, null)) {
/*  540 */       return this.key[this.n];
/*      */     }
/*      */     
/*  543 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  546 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  547 */       return null; 
/*  548 */     if (this.strategy.equals(k, curr)) {
/*  549 */       return curr;
/*      */     }
/*      */     while (true) {
/*  552 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  553 */         return null; 
/*  554 */       if (this.strategy.equals(k, curr)) {
/*  555 */         return curr;
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
/*  566 */     if (this.size == 0)
/*  567 */       throw new NoSuchElementException(); 
/*  568 */     int pos = this.first;
/*      */     
/*  570 */     this.first = (int)this.link[pos];
/*  571 */     if (0 <= this.first)
/*      */     {
/*  573 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  575 */     K k = this.key[pos];
/*  576 */     this.size--;
/*  577 */     if (this.strategy.equals(k, null)) {
/*  578 */       this.containsNull = false;
/*  579 */       this.key[this.n] = null;
/*      */     } else {
/*  581 */       shiftKeys(pos);
/*  582 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  583 */       rehash(this.n / 2); 
/*  584 */     return k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K removeLast() {
/*  594 */     if (this.size == 0)
/*  595 */       throw new NoSuchElementException(); 
/*  596 */     int pos = this.last;
/*      */     
/*  598 */     this.last = (int)(this.link[pos] >>> 32L);
/*  599 */     if (0 <= this.last)
/*      */     {
/*  601 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  603 */     K k = this.key[pos];
/*  604 */     this.size--;
/*  605 */     if (this.strategy.equals(k, null)) {
/*  606 */       this.containsNull = false;
/*  607 */       this.key[this.n] = null;
/*      */     } else {
/*  609 */       shiftKeys(pos);
/*  610 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  611 */       rehash(this.n / 2); 
/*  612 */     return k;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  615 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  617 */     if (this.last == i) {
/*  618 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  620 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  622 */       long linki = this.link[i];
/*  623 */       int prev = (int)(linki >>> 32L);
/*  624 */       int next = (int)linki;
/*  625 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  626 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  628 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  629 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  630 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  633 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  635 */     if (this.first == i) {
/*  636 */       this.first = (int)this.link[i];
/*      */       
/*  638 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  640 */       long linki = this.link[i];
/*  641 */       int prev = (int)(linki >>> 32L);
/*  642 */       int next = (int)linki;
/*  643 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  644 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  646 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  647 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  648 */     this.last = i;
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
/*  660 */     if (this.strategy.equals(k, null)) {
/*  661 */       if (this.containsNull) {
/*  662 */         moveIndexToFirst(this.n);
/*  663 */         return false;
/*      */       } 
/*  665 */       this.containsNull = true;
/*  666 */       pos = this.n;
/*      */     } else {
/*      */       
/*  669 */       K[] key = this.key;
/*  670 */       pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/*      */       
/*  672 */       while (key[pos] != null) {
/*  673 */         if (this.strategy.equals(k, key[pos])) {
/*  674 */           moveIndexToFirst(pos);
/*  675 */           return false;
/*      */         } 
/*  677 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  680 */     this.key[pos] = k;
/*  681 */     if (this.size == 0) {
/*  682 */       this.first = this.last = pos;
/*      */       
/*  684 */       this.link[pos] = -1L;
/*      */     } else {
/*  686 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  687 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  688 */       this.first = pos;
/*      */     } 
/*  690 */     if (this.size++ >= this.maxFill) {
/*  691 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  694 */     return true;
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
/*  706 */     if (this.strategy.equals(k, null)) {
/*  707 */       if (this.containsNull) {
/*  708 */         moveIndexToLast(this.n);
/*  709 */         return false;
/*      */       } 
/*  711 */       this.containsNull = true;
/*  712 */       pos = this.n;
/*      */     } else {
/*      */       
/*  715 */       K[] key = this.key;
/*  716 */       pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/*      */       
/*  718 */       while (key[pos] != null) {
/*  719 */         if (this.strategy.equals(k, key[pos])) {
/*  720 */           moveIndexToLast(pos);
/*  721 */           return false;
/*      */         } 
/*  723 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  726 */     this.key[pos] = k;
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
/*  740 */     return true;
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
/*  751 */     if (this.size == 0)
/*      */       return; 
/*  753 */     this.size = 0;
/*  754 */     this.containsNull = false;
/*  755 */     Arrays.fill((Object[])this.key, (Object)null);
/*  756 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  760 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  764 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int i) {
/*  774 */     if (this.size == 0) {
/*  775 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  778 */     if (this.first == i) {
/*  779 */       this.first = (int)this.link[i];
/*  780 */       if (0 <= this.first)
/*      */       {
/*  782 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  786 */     if (this.last == i) {
/*  787 */       this.last = (int)(this.link[i] >>> 32L);
/*  788 */       if (0 <= this.last)
/*      */       {
/*  790 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  794 */     long linki = this.link[i];
/*  795 */     int prev = (int)(linki >>> 32L);
/*  796 */     int next = (int)linki;
/*  797 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  798 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*  810 */     if (this.size == 1) {
/*  811 */       this.first = this.last = d;
/*      */       
/*  813 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  816 */     if (this.first == s) {
/*  817 */       this.first = d;
/*  818 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  819 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  822 */     if (this.last == s) {
/*  823 */       this.last = d;
/*  824 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  825 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  828 */     long links = this.link[s];
/*  829 */     int prev = (int)(links >>> 32L);
/*  830 */     int next = (int)links;
/*  831 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  832 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  833 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K first() {
/*  842 */     if (this.size == 0)
/*  843 */       throw new NoSuchElementException(); 
/*  844 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K last() {
/*  853 */     if (this.size == 0)
/*  854 */       throw new NoSuchElementException(); 
/*  855 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> tailSet(K from) {
/*  864 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> headSet(K to) {
/*  873 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> subSet(K from, K to) {
/*  882 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/*  891 */     return null;
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
/*  906 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  912 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  917 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  922 */     int index = -1;
/*      */     SetIterator() {
/*  924 */       this.next = ObjectLinkedOpenCustomHashSet.this.first;
/*  925 */       this.index = 0;
/*      */     }
/*      */     SetIterator(K from) {
/*  928 */       if (ObjectLinkedOpenCustomHashSet.this.strategy.equals(from, null)) {
/*  929 */         if (ObjectLinkedOpenCustomHashSet.this.containsNull) {
/*  930 */           this.next = (int)ObjectLinkedOpenCustomHashSet.this.link[ObjectLinkedOpenCustomHashSet.this.n];
/*  931 */           this.prev = ObjectLinkedOpenCustomHashSet.this.n;
/*      */           return;
/*      */         } 
/*  934 */         throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */       } 
/*  936 */       if (ObjectLinkedOpenCustomHashSet.this.strategy.equals(ObjectLinkedOpenCustomHashSet.this.key[ObjectLinkedOpenCustomHashSet.this.last], from)) {
/*  937 */         this.prev = ObjectLinkedOpenCustomHashSet.this.last;
/*  938 */         this.index = ObjectLinkedOpenCustomHashSet.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  942 */       K[] key = ObjectLinkedOpenCustomHashSet.this.key;
/*  943 */       int pos = HashCommon.mix(ObjectLinkedOpenCustomHashSet.this.strategy.hashCode(from)) & ObjectLinkedOpenCustomHashSet.this.mask;
/*      */       
/*  945 */       while (key[pos] != null) {
/*  946 */         if (ObjectLinkedOpenCustomHashSet.this.strategy.equals(key[pos], from)) {
/*      */           
/*  948 */           this.next = (int)ObjectLinkedOpenCustomHashSet.this.link[pos];
/*  949 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  952 */         pos = pos + 1 & ObjectLinkedOpenCustomHashSet.this.mask;
/*      */       } 
/*  954 */       throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  958 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  962 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     public K next() {
/*  966 */       if (!hasNext())
/*  967 */         throw new NoSuchElementException(); 
/*  968 */       this.curr = this.next;
/*  969 */       this.next = (int)ObjectLinkedOpenCustomHashSet.this.link[this.curr];
/*  970 */       this.prev = this.curr;
/*  971 */       if (this.index >= 0) {
/*  972 */         this.index++;
/*      */       }
/*      */       
/*  975 */       return ObjectLinkedOpenCustomHashSet.this.key[this.curr];
/*      */     }
/*      */     
/*      */     public K previous() {
/*  979 */       if (!hasPrevious())
/*  980 */         throw new NoSuchElementException(); 
/*  981 */       this.curr = this.prev;
/*  982 */       this.prev = (int)(ObjectLinkedOpenCustomHashSet.this.link[this.curr] >>> 32L);
/*  983 */       this.next = this.curr;
/*  984 */       if (this.index >= 0)
/*  985 */         this.index--; 
/*  986 */       return ObjectLinkedOpenCustomHashSet.this.key[this.curr];
/*      */     }
/*      */     private final void ensureIndexKnown() {
/*  989 */       if (this.index >= 0)
/*      */         return; 
/*  991 */       if (this.prev == -1) {
/*  992 */         this.index = 0;
/*      */         return;
/*      */       } 
/*  995 */       if (this.next == -1) {
/*  996 */         this.index = ObjectLinkedOpenCustomHashSet.this.size;
/*      */         return;
/*      */       } 
/*  999 */       int pos = ObjectLinkedOpenCustomHashSet.this.first;
/* 1000 */       this.index = 1;
/* 1001 */       while (pos != this.prev) {
/* 1002 */         pos = (int)ObjectLinkedOpenCustomHashSet.this.link[pos];
/* 1003 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1008 */       ensureIndexKnown();
/* 1009 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1013 */       ensureIndexKnown();
/* 1014 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1018 */       ensureIndexKnown();
/* 1019 */       if (this.curr == -1)
/* 1020 */         throw new IllegalStateException(); 
/* 1021 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1026 */         this.index--;
/* 1027 */         this.prev = (int)(ObjectLinkedOpenCustomHashSet.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1029 */         this.next = (int)ObjectLinkedOpenCustomHashSet.this.link[this.curr];
/* 1030 */       }  ObjectLinkedOpenCustomHashSet.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1035 */       if (this.prev == -1) {
/* 1036 */         ObjectLinkedOpenCustomHashSet.this.first = this.next;
/*      */       } else {
/* 1038 */         ObjectLinkedOpenCustomHashSet.this.link[this.prev] = ObjectLinkedOpenCustomHashSet.this.link[this.prev] ^ (ObjectLinkedOpenCustomHashSet.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1039 */       }  if (this.next == -1) {
/* 1040 */         ObjectLinkedOpenCustomHashSet.this.last = this.prev;
/*      */       } else {
/* 1042 */         ObjectLinkedOpenCustomHashSet.this.link[this.next] = ObjectLinkedOpenCustomHashSet.this.link[this.next] ^ (ObjectLinkedOpenCustomHashSet.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1043 */       }  int pos = this.curr;
/* 1044 */       this.curr = -1;
/* 1045 */       if (pos == ObjectLinkedOpenCustomHashSet.this.n) {
/* 1046 */         ObjectLinkedOpenCustomHashSet.this.containsNull = false;
/* 1047 */         ObjectLinkedOpenCustomHashSet.this.key[ObjectLinkedOpenCustomHashSet.this.n] = null;
/*      */       } else {
/*      */         
/* 1050 */         K[] key = ObjectLinkedOpenCustomHashSet.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1054 */           pos = (last = pos) + 1 & ObjectLinkedOpenCustomHashSet.this.mask;
/*      */           while (true) {
/* 1056 */             if ((curr = key[pos]) == null) {
/* 1057 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1060 */             int slot = HashCommon.mix(ObjectLinkedOpenCustomHashSet.this.strategy.hashCode(curr)) & ObjectLinkedOpenCustomHashSet.this.mask;
/* 1061 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1063 */             pos = pos + 1 & ObjectLinkedOpenCustomHashSet.this.mask;
/*      */           } 
/* 1065 */           key[last] = curr;
/* 1066 */           if (this.next == pos)
/* 1067 */             this.next = last; 
/* 1068 */           if (this.prev == pos)
/* 1069 */             this.prev = last; 
/* 1070 */           ObjectLinkedOpenCustomHashSet.this.fixPointers(pos, last);
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
/* 1088 */     return new SetIterator(from);
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
/* 1099 */     return new SetIterator();
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
/* 1116 */     return trim(this.size);
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
/* 1140 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1141 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1142 */       return true; 
/*      */     try {
/* 1144 */       rehash(l);
/* 1145 */     } catch (OutOfMemoryError cantDoIt) {
/* 1146 */       return false;
/*      */     } 
/* 1148 */     return true;
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
/* 1164 */     K[] key = this.key;
/* 1165 */     int mask = newN - 1;
/* 1166 */     K[] newKey = (K[])new Object[newN + 1];
/* 1167 */     int i = this.first, prev = -1, newPrev = -1;
/* 1168 */     long[] link = this.link;
/* 1169 */     long[] newLink = new long[newN + 1];
/* 1170 */     this.first = -1;
/* 1171 */     for (int j = this.size; j-- != 0; ) {
/* 1172 */       int pos; if (this.strategy.equals(key[i], null)) {
/* 1173 */         pos = newN;
/*      */       } else {
/* 1175 */         pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
/* 1176 */         while (newKey[pos] != null)
/* 1177 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1179 */       newKey[pos] = key[i];
/* 1180 */       if (prev != -1) {
/* 1181 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1182 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1183 */         newPrev = pos;
/*      */       } else {
/* 1185 */         newPrev = this.first = pos;
/*      */         
/* 1187 */         newLink[pos] = -1L;
/*      */       } 
/* 1189 */       int t = i;
/* 1190 */       i = (int)link[i];
/* 1191 */       prev = t;
/*      */     } 
/* 1193 */     this.link = newLink;
/* 1194 */     this.last = newPrev;
/* 1195 */     if (newPrev != -1)
/*      */     {
/* 1197 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1198 */     this.n = newN;
/* 1199 */     this.mask = mask;
/* 1200 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1201 */     this.key = newKey;
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
/*      */   public ObjectLinkedOpenCustomHashSet<K> clone() {
/*      */     ObjectLinkedOpenCustomHashSet<K> c;
/*      */     try {
/* 1218 */       c = (ObjectLinkedOpenCustomHashSet<K>)super.clone();
/* 1219 */     } catch (CloneNotSupportedException cantHappen) {
/* 1220 */       throw new InternalError();
/*      */     } 
/* 1222 */     c.key = (K[])this.key.clone();
/* 1223 */     c.containsNull = this.containsNull;
/* 1224 */     c.link = (long[])this.link.clone();
/* 1225 */     c.strategy = this.strategy;
/* 1226 */     return c;
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
/* 1239 */     int h = 0;
/* 1240 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 1241 */       while (this.key[i] == null)
/* 1242 */         i++; 
/* 1243 */       if (this != this.key[i])
/* 1244 */         h += this.strategy.hashCode(this.key[i]); 
/* 1245 */       i++;
/*      */     } 
/*      */     
/* 1248 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1251 */     ObjectIterator<K> i = iterator();
/* 1252 */     s.defaultWriteObject();
/* 1253 */     for (int j = this.size; j-- != 0;)
/* 1254 */       s.writeObject(i.next()); 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1258 */     s.defaultReadObject();
/* 1259 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1260 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1261 */     this.mask = this.n - 1;
/* 1262 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1263 */     long[] link = this.link = new long[this.n + 1];
/* 1264 */     int prev = -1;
/* 1265 */     this.first = this.last = -1;
/*      */     
/* 1267 */     for (int i = this.size; i-- != 0; ) {
/* 1268 */       int pos; K k = (K)s.readObject();
/* 1269 */       if (this.strategy.equals(k, null)) {
/* 1270 */         pos = this.n;
/* 1271 */         this.containsNull = true;
/*      */       }
/* 1273 */       else if (key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask] != null) {
/* 1274 */         while (key[pos = pos + 1 & this.mask] != null);
/*      */       } 
/* 1276 */       key[pos] = k;
/* 1277 */       if (this.first != -1) {
/* 1278 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1279 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1280 */         prev = pos; continue;
/*      */       } 
/* 1282 */       prev = this.first = pos;
/*      */       
/* 1284 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1287 */     this.last = prev;
/* 1288 */     if (prev != -1)
/*      */     {
/* 1290 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectLinkedOpenCustomHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */