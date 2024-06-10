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
/*      */ import java.util.Objects;
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
/*      */ public class ObjectLinkedOpenHashSet<K>
/*      */   extends AbstractObjectSortedSet<K>
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
/*      */   public ObjectLinkedOpenHashSet(int expected, float f) {
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
/*      */   public ObjectLinkedOpenHashSet(int expected) {
/*  158 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenHashSet() {
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
/*      */   public ObjectLinkedOpenHashSet(Collection<? extends K> c, float f) {
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
/*      */   public ObjectLinkedOpenHashSet(Collection<? extends K> c) {
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
/*      */   public ObjectLinkedOpenHashSet(ObjectCollection<? extends K> c, float f) {
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
/*      */   public ObjectLinkedOpenHashSet(ObjectCollection<? extends K> c) {
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
/*      */   public ObjectLinkedOpenHashSet(Iterator<? extends K> i, float f) {
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
/*      */   public ObjectLinkedOpenHashSet(Iterator<? extends K> i) {
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
/*      */   public ObjectLinkedOpenHashSet(K[] a, int offset, int length, float f) {
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
/*      */   public ObjectLinkedOpenHashSet(K[] a, int offset, int length) {
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
/*      */   public ObjectLinkedOpenHashSet(K[] a, float f) {
/*  276 */     this(a, 0, a.length, f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenHashSet(K[] a) {
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
/*  324 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  325 */         if (curr.equals(k))
/*  326 */           return false; 
/*  327 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  328 */           if (curr.equals(k))
/*  329 */             return false; 
/*      */         } 
/*  331 */       }  key[pos] = k;
/*      */     } 
/*  333 */     if (this.size == 0) {
/*  334 */       this.first = this.last = pos;
/*      */       
/*  336 */       this.link[pos] = -1L;
/*      */     } else {
/*  338 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  339 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  340 */       this.last = pos;
/*      */     } 
/*  342 */     if (this.size++ >= this.maxFill) {
/*  343 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  346 */     return true;
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
/*  364 */     if (k == null) {
/*  365 */       if (this.containsNull)
/*  366 */         return this.key[this.n]; 
/*  367 */       pos = this.n;
/*  368 */       this.containsNull = true;
/*      */     } else {
/*      */       
/*  371 */       K[] key = this.key;
/*      */       K curr;
/*  373 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  374 */         if (curr.equals(k))
/*  375 */           return curr; 
/*  376 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  377 */           if (curr.equals(k))
/*  378 */             return curr; 
/*      */         } 
/*  380 */       }  key[pos] = k;
/*      */     } 
/*  382 */     if (this.size == 0) {
/*  383 */       this.first = this.last = pos;
/*      */       
/*  385 */       this.link[pos] = -1L;
/*      */     } else {
/*  387 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  388 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  389 */       this.last = pos;
/*      */     } 
/*  391 */     if (this.size++ >= this.maxFill) {
/*  392 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  395 */     return k;
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
/*  408 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  410 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  412 */         if ((curr = key[pos]) == null) {
/*  413 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  416 */         int slot = HashCommon.mix(curr.hashCode()) & this.mask;
/*  417 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  419 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  421 */       key[last] = curr;
/*  422 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  426 */     this.size--;
/*  427 */     fixPointers(pos);
/*  428 */     shiftKeys(pos);
/*  429 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  430 */       rehash(this.n / 2); 
/*  431 */     return true;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  434 */     this.containsNull = false;
/*  435 */     this.key[this.n] = null;
/*  436 */     this.size--;
/*  437 */     fixPointers(this.n);
/*  438 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  439 */       rehash(this.n / 2); 
/*  440 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(Object k) {
/*  445 */     if (k == null) {
/*  446 */       if (this.containsNull)
/*  447 */         return removeNullEntry(); 
/*  448 */       return false;
/*      */     } 
/*      */     
/*  451 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  454 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  455 */       return false; 
/*  456 */     if (k.equals(curr))
/*  457 */       return removeEntry(pos); 
/*      */     while (true) {
/*  459 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  460 */         return false; 
/*  461 */       if (k.equals(curr)) {
/*  462 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean contains(Object k) {
/*  468 */     if (k == null) {
/*  469 */       return this.containsNull;
/*      */     }
/*  471 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  474 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  475 */       return false; 
/*  476 */     if (k.equals(curr))
/*  477 */       return true; 
/*      */     while (true) {
/*  479 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  480 */         return false; 
/*  481 */       if (k.equals(curr)) {
/*  482 */         return true;
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
/*  494 */     if (k == null) {
/*  495 */       return this.key[this.n];
/*      */     }
/*      */     
/*  498 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  501 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  502 */       return null; 
/*  503 */     if (k.equals(curr)) {
/*  504 */       return curr;
/*      */     }
/*      */     while (true) {
/*  507 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  508 */         return null; 
/*  509 */       if (k.equals(curr)) {
/*  510 */         return curr;
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
/*  521 */     if (this.size == 0)
/*  522 */       throw new NoSuchElementException(); 
/*  523 */     int pos = this.first;
/*      */     
/*  525 */     this.first = (int)this.link[pos];
/*  526 */     if (0 <= this.first)
/*      */     {
/*  528 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  530 */     K k = this.key[pos];
/*  531 */     this.size--;
/*  532 */     if (k == null) {
/*  533 */       this.containsNull = false;
/*  534 */       this.key[this.n] = null;
/*      */     } else {
/*  536 */       shiftKeys(pos);
/*  537 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  538 */       rehash(this.n / 2); 
/*  539 */     return k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K removeLast() {
/*  549 */     if (this.size == 0)
/*  550 */       throw new NoSuchElementException(); 
/*  551 */     int pos = this.last;
/*      */     
/*  553 */     this.last = (int)(this.link[pos] >>> 32L);
/*  554 */     if (0 <= this.last)
/*      */     {
/*  556 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  558 */     K k = this.key[pos];
/*  559 */     this.size--;
/*  560 */     if (k == null) {
/*  561 */       this.containsNull = false;
/*  562 */       this.key[this.n] = null;
/*      */     } else {
/*  564 */       shiftKeys(pos);
/*  565 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  566 */       rehash(this.n / 2); 
/*  567 */     return k;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  570 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  572 */     if (this.last == i) {
/*  573 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  575 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  577 */       long linki = this.link[i];
/*  578 */       int prev = (int)(linki >>> 32L);
/*  579 */       int next = (int)linki;
/*  580 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  581 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  583 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  584 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  585 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  588 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  590 */     if (this.first == i) {
/*  591 */       this.first = (int)this.link[i];
/*      */       
/*  593 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  595 */       long linki = this.link[i];
/*  596 */       int prev = (int)(linki >>> 32L);
/*  597 */       int next = (int)linki;
/*  598 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  599 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  601 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  602 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  603 */     this.last = i;
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
/*  615 */     if (k == null) {
/*  616 */       if (this.containsNull) {
/*  617 */         moveIndexToFirst(this.n);
/*  618 */         return false;
/*      */       } 
/*  620 */       this.containsNull = true;
/*  621 */       pos = this.n;
/*      */     } else {
/*      */       
/*  624 */       K[] key = this.key;
/*  625 */       pos = HashCommon.mix(k.hashCode()) & this.mask;
/*      */       
/*  627 */       while (key[pos] != null) {
/*  628 */         if (k.equals(key[pos])) {
/*  629 */           moveIndexToFirst(pos);
/*  630 */           return false;
/*      */         } 
/*  632 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  635 */     this.key[pos] = k;
/*  636 */     if (this.size == 0) {
/*  637 */       this.first = this.last = pos;
/*      */       
/*  639 */       this.link[pos] = -1L;
/*      */     } else {
/*  641 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  642 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  643 */       this.first = pos;
/*      */     } 
/*  645 */     if (this.size++ >= this.maxFill) {
/*  646 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  649 */     return true;
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
/*  661 */     if (k == null) {
/*  662 */       if (this.containsNull) {
/*  663 */         moveIndexToLast(this.n);
/*  664 */         return false;
/*      */       } 
/*  666 */       this.containsNull = true;
/*  667 */       pos = this.n;
/*      */     } else {
/*      */       
/*  670 */       K[] key = this.key;
/*  671 */       pos = HashCommon.mix(k.hashCode()) & this.mask;
/*      */       
/*  673 */       while (key[pos] != null) {
/*  674 */         if (k.equals(key[pos])) {
/*  675 */           moveIndexToLast(pos);
/*  676 */           return false;
/*      */         } 
/*  678 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  681 */     this.key[pos] = k;
/*  682 */     if (this.size == 0) {
/*  683 */       this.first = this.last = pos;
/*      */       
/*  685 */       this.link[pos] = -1L;
/*      */     } else {
/*  687 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  688 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  689 */       this.last = pos;
/*      */     } 
/*  691 */     if (this.size++ >= this.maxFill) {
/*  692 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  695 */     return true;
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
/*  706 */     if (this.size == 0)
/*      */       return; 
/*  708 */     this.size = 0;
/*  709 */     this.containsNull = false;
/*  710 */     Arrays.fill((Object[])this.key, (Object)null);
/*  711 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  715 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  719 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int i) {
/*  729 */     if (this.size == 0) {
/*  730 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  733 */     if (this.first == i) {
/*  734 */       this.first = (int)this.link[i];
/*  735 */       if (0 <= this.first)
/*      */       {
/*  737 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  741 */     if (this.last == i) {
/*  742 */       this.last = (int)(this.link[i] >>> 32L);
/*  743 */       if (0 <= this.last)
/*      */       {
/*  745 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  749 */     long linki = this.link[i];
/*  750 */     int prev = (int)(linki >>> 32L);
/*  751 */     int next = (int)linki;
/*  752 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  753 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*  765 */     if (this.size == 1) {
/*  766 */       this.first = this.last = d;
/*      */       
/*  768 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  771 */     if (this.first == s) {
/*  772 */       this.first = d;
/*  773 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  774 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  777 */     if (this.last == s) {
/*  778 */       this.last = d;
/*  779 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  780 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  783 */     long links = this.link[s];
/*  784 */     int prev = (int)(links >>> 32L);
/*  785 */     int next = (int)links;
/*  786 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  787 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  788 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K first() {
/*  797 */     if (this.size == 0)
/*  798 */       throw new NoSuchElementException(); 
/*  799 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K last() {
/*  808 */     if (this.size == 0)
/*  809 */       throw new NoSuchElementException(); 
/*  810 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> tailSet(K from) {
/*  819 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> headSet(K to) {
/*  828 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> subSet(K from, K to) {
/*  837 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/*  846 */     return null;
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
/*  861 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  867 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  872 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  877 */     int index = -1;
/*      */     SetIterator() {
/*  879 */       this.next = ObjectLinkedOpenHashSet.this.first;
/*  880 */       this.index = 0;
/*      */     }
/*      */     SetIterator(K from) {
/*  883 */       if (from == null) {
/*  884 */         if (ObjectLinkedOpenHashSet.this.containsNull) {
/*  885 */           this.next = (int)ObjectLinkedOpenHashSet.this.link[ObjectLinkedOpenHashSet.this.n];
/*  886 */           this.prev = ObjectLinkedOpenHashSet.this.n;
/*      */           return;
/*      */         } 
/*  889 */         throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */       } 
/*  891 */       if (Objects.equals(ObjectLinkedOpenHashSet.this.key[ObjectLinkedOpenHashSet.this.last], from)) {
/*  892 */         this.prev = ObjectLinkedOpenHashSet.this.last;
/*  893 */         this.index = ObjectLinkedOpenHashSet.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  897 */       K[] key = ObjectLinkedOpenHashSet.this.key;
/*  898 */       int pos = HashCommon.mix(from.hashCode()) & ObjectLinkedOpenHashSet.this.mask;
/*      */       
/*  900 */       while (key[pos] != null) {
/*  901 */         if (key[pos].equals(from)) {
/*      */           
/*  903 */           this.next = (int)ObjectLinkedOpenHashSet.this.link[pos];
/*  904 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  907 */         pos = pos + 1 & ObjectLinkedOpenHashSet.this.mask;
/*      */       } 
/*  909 */       throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  913 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  917 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     public K next() {
/*  921 */       if (!hasNext())
/*  922 */         throw new NoSuchElementException(); 
/*  923 */       this.curr = this.next;
/*  924 */       this.next = (int)ObjectLinkedOpenHashSet.this.link[this.curr];
/*  925 */       this.prev = this.curr;
/*  926 */       if (this.index >= 0) {
/*  927 */         this.index++;
/*      */       }
/*      */       
/*  930 */       return ObjectLinkedOpenHashSet.this.key[this.curr];
/*      */     }
/*      */     
/*      */     public K previous() {
/*  934 */       if (!hasPrevious())
/*  935 */         throw new NoSuchElementException(); 
/*  936 */       this.curr = this.prev;
/*  937 */       this.prev = (int)(ObjectLinkedOpenHashSet.this.link[this.curr] >>> 32L);
/*  938 */       this.next = this.curr;
/*  939 */       if (this.index >= 0)
/*  940 */         this.index--; 
/*  941 */       return ObjectLinkedOpenHashSet.this.key[this.curr];
/*      */     }
/*      */     private final void ensureIndexKnown() {
/*  944 */       if (this.index >= 0)
/*      */         return; 
/*  946 */       if (this.prev == -1) {
/*  947 */         this.index = 0;
/*      */         return;
/*      */       } 
/*  950 */       if (this.next == -1) {
/*  951 */         this.index = ObjectLinkedOpenHashSet.this.size;
/*      */         return;
/*      */       } 
/*  954 */       int pos = ObjectLinkedOpenHashSet.this.first;
/*  955 */       this.index = 1;
/*  956 */       while (pos != this.prev) {
/*  957 */         pos = (int)ObjectLinkedOpenHashSet.this.link[pos];
/*  958 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  963 */       ensureIndexKnown();
/*  964 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  968 */       ensureIndexKnown();
/*  969 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  973 */       ensureIndexKnown();
/*  974 */       if (this.curr == -1)
/*  975 */         throw new IllegalStateException(); 
/*  976 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  981 */         this.index--;
/*  982 */         this.prev = (int)(ObjectLinkedOpenHashSet.this.link[this.curr] >>> 32L);
/*      */       } else {
/*  984 */         this.next = (int)ObjectLinkedOpenHashSet.this.link[this.curr];
/*  985 */       }  ObjectLinkedOpenHashSet.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  990 */       if (this.prev == -1) {
/*  991 */         ObjectLinkedOpenHashSet.this.first = this.next;
/*      */       } else {
/*  993 */         ObjectLinkedOpenHashSet.this.link[this.prev] = ObjectLinkedOpenHashSet.this.link[this.prev] ^ (ObjectLinkedOpenHashSet.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  994 */       }  if (this.next == -1) {
/*  995 */         ObjectLinkedOpenHashSet.this.last = this.prev;
/*      */       } else {
/*  997 */         ObjectLinkedOpenHashSet.this.link[this.next] = ObjectLinkedOpenHashSet.this.link[this.next] ^ (ObjectLinkedOpenHashSet.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  998 */       }  int pos = this.curr;
/*  999 */       this.curr = -1;
/* 1000 */       if (pos == ObjectLinkedOpenHashSet.this.n) {
/* 1001 */         ObjectLinkedOpenHashSet.this.containsNull = false;
/* 1002 */         ObjectLinkedOpenHashSet.this.key[ObjectLinkedOpenHashSet.this.n] = null;
/*      */       } else {
/*      */         
/* 1005 */         K[] key = ObjectLinkedOpenHashSet.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1009 */           pos = (last = pos) + 1 & ObjectLinkedOpenHashSet.this.mask;
/*      */           while (true) {
/* 1011 */             if ((curr = key[pos]) == null) {
/* 1012 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1015 */             int slot = HashCommon.mix(curr.hashCode()) & ObjectLinkedOpenHashSet.this.mask;
/* 1016 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1018 */             pos = pos + 1 & ObjectLinkedOpenHashSet.this.mask;
/*      */           } 
/* 1020 */           key[last] = curr;
/* 1021 */           if (this.next == pos)
/* 1022 */             this.next = last; 
/* 1023 */           if (this.prev == pos)
/* 1024 */             this.prev = last; 
/* 1025 */           ObjectLinkedOpenHashSet.this.fixPointers(pos, last);
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
/* 1043 */     return new SetIterator(from);
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
/* 1054 */     return new SetIterator();
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
/* 1071 */     return trim(this.size);
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
/* 1095 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1096 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1097 */       return true; 
/*      */     try {
/* 1099 */       rehash(l);
/* 1100 */     } catch (OutOfMemoryError cantDoIt) {
/* 1101 */       return false;
/*      */     } 
/* 1103 */     return true;
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
/* 1119 */     K[] key = this.key;
/* 1120 */     int mask = newN - 1;
/* 1121 */     K[] newKey = (K[])new Object[newN + 1];
/* 1122 */     int i = this.first, prev = -1, newPrev = -1;
/* 1123 */     long[] link = this.link;
/* 1124 */     long[] newLink = new long[newN + 1];
/* 1125 */     this.first = -1;
/* 1126 */     for (int j = this.size; j-- != 0; ) {
/* 1127 */       int pos; if (key[i] == null) {
/* 1128 */         pos = newN;
/*      */       } else {
/* 1130 */         pos = HashCommon.mix(key[i].hashCode()) & mask;
/* 1131 */         while (newKey[pos] != null)
/* 1132 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1134 */       newKey[pos] = key[i];
/* 1135 */       if (prev != -1) {
/* 1136 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1137 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1138 */         newPrev = pos;
/*      */       } else {
/* 1140 */         newPrev = this.first = pos;
/*      */         
/* 1142 */         newLink[pos] = -1L;
/*      */       } 
/* 1144 */       int t = i;
/* 1145 */       i = (int)link[i];
/* 1146 */       prev = t;
/*      */     } 
/* 1148 */     this.link = newLink;
/* 1149 */     this.last = newPrev;
/* 1150 */     if (newPrev != -1)
/*      */     {
/* 1152 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1153 */     this.n = newN;
/* 1154 */     this.mask = mask;
/* 1155 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1156 */     this.key = newKey;
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
/*      */   public ObjectLinkedOpenHashSet<K> clone() {
/*      */     ObjectLinkedOpenHashSet<K> c;
/*      */     try {
/* 1173 */       c = (ObjectLinkedOpenHashSet<K>)super.clone();
/* 1174 */     } catch (CloneNotSupportedException cantHappen) {
/* 1175 */       throw new InternalError();
/*      */     } 
/* 1177 */     c.key = (K[])this.key.clone();
/* 1178 */     c.containsNull = this.containsNull;
/* 1179 */     c.link = (long[])this.link.clone();
/* 1180 */     return c;
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
/* 1193 */     int h = 0;
/* 1194 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 1195 */       while (this.key[i] == null)
/* 1196 */         i++; 
/* 1197 */       if (this != this.key[i])
/* 1198 */         h += this.key[i].hashCode(); 
/* 1199 */       i++;
/*      */     } 
/*      */     
/* 1202 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1205 */     ObjectIterator<K> i = iterator();
/* 1206 */     s.defaultWriteObject();
/* 1207 */     for (int j = this.size; j-- != 0;)
/* 1208 */       s.writeObject(i.next()); 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1212 */     s.defaultReadObject();
/* 1213 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1214 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1215 */     this.mask = this.n - 1;
/* 1216 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1217 */     long[] link = this.link = new long[this.n + 1];
/* 1218 */     int prev = -1;
/* 1219 */     this.first = this.last = -1;
/*      */     
/* 1221 */     for (int i = this.size; i-- != 0; ) {
/* 1222 */       int pos; K k = (K)s.readObject();
/* 1223 */       if (k == null) {
/* 1224 */         pos = this.n;
/* 1225 */         this.containsNull = true;
/*      */       }
/* 1227 */       else if (key[pos = HashCommon.mix(k.hashCode()) & this.mask] != null) {
/* 1228 */         while (key[pos = pos + 1 & this.mask] != null);
/*      */       } 
/* 1230 */       key[pos] = k;
/* 1231 */       if (this.first != -1) {
/* 1232 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1233 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1234 */         prev = pos; continue;
/*      */       } 
/* 1236 */       prev = this.first = pos;
/*      */       
/* 1238 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1241 */     this.last = prev;
/* 1242 */     if (prev != -1)
/*      */     {
/* 1244 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectLinkedOpenHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */