/*      */ package it.unimi.dsi.fastutil.ints;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.SortedMap;
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
/*      */ public class Int2ObjectRBTreeMap<V>
/*      */   extends AbstractInt2ObjectSortedMap<V>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<V> tree;
/*      */   protected int count;
/*      */   protected transient Entry<V> firstEntry;
/*      */   protected transient Entry<V> lastEntry;
/*      */   protected transient ObjectSortedSet<Int2ObjectMap.Entry<V>> entries;
/*      */   protected transient IntSortedSet keys;
/*      */   protected transient ObjectCollection<V> values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Integer> storedComparator;
/*      */   protected transient IntComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry<V>[] nodePath;
/*      */   
/*      */   public Int2ObjectRBTreeMap() {
/*   71 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   77 */     this.tree = null;
/*   78 */     this.count = 0;
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
/*      */   private void setActualComparator() {
/*   90 */     this.actualComparator = IntComparators.asIntComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ObjectRBTreeMap(Comparator<? super Integer> c) {
/*   99 */     this();
/*  100 */     this.storedComparator = c;
/*  101 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ObjectRBTreeMap(Map<? extends Integer, ? extends V> m) {
/*  110 */     this();
/*  111 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ObjectRBTreeMap(SortedMap<Integer, V> m) {
/*  121 */     this(m.comparator());
/*  122 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ObjectRBTreeMap(Int2ObjectMap<? extends V> m) {
/*  131 */     this();
/*  132 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ObjectRBTreeMap(Int2ObjectSortedMap<V> m) {
/*  142 */     this(m.comparator());
/*  143 */     putAll(m);
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
/*      */   public Int2ObjectRBTreeMap(int[] k, V[] v, Comparator<? super Integer> c) {
/*  159 */     this(c);
/*  160 */     if (k.length != v.length) {
/*  161 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  163 */     for (int i = 0; i < k.length; i++) {
/*  164 */       put(k[i], v[i]);
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
/*      */   public Int2ObjectRBTreeMap(int[] k, V[] v) {
/*  177 */     this(k, v, (Comparator<? super Integer>)null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int compare(int k1, int k2) {
/*  205 */     return (this.actualComparator == null) ? Integer.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry<V> findKey(int k) {
/*  217 */     Entry<V> e = this.tree;
/*      */     int cmp;
/*  219 */     while (e != null && (cmp = compare(k, e.key)) != 0)
/*  220 */       e = (cmp < 0) ? e.left() : e.right(); 
/*  221 */     return e;
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
/*      */   final Entry<V> locateKey(int k) {
/*  233 */     Entry<V> e = this.tree, last = this.tree;
/*  234 */     int cmp = 0;
/*  235 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  236 */       last = e;
/*  237 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  239 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  249 */     this.dirPath = new boolean[64];
/*  250 */     this.nodePath = (Entry<V>[])new Entry[64];
/*      */   }
/*      */   
/*      */   public V put(int k, V v) {
/*  254 */     Entry<V> e = add(k);
/*  255 */     V oldValue = e.value;
/*  256 */     e.value = v;
/*  257 */     return oldValue;
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
/*      */   private Entry<V> add(int k) {
/*      */     Entry<V> e;
/*  274 */     this.modified = false;
/*  275 */     int maxDepth = 0;
/*      */     
/*  277 */     if (this.tree == null) {
/*  278 */       this.count++;
/*  279 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*      */     } else {
/*  281 */       Entry<V> p = this.tree;
/*  282 */       int i = 0; while (true) {
/*      */         int cmp;
/*  284 */         if ((cmp = compare(k, p.key)) == 0) {
/*      */           
/*  286 */           while (i-- != 0)
/*  287 */             this.nodePath[i] = null; 
/*  288 */           return p;
/*      */         } 
/*  290 */         this.nodePath[i] = p;
/*  291 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  292 */           if (p.succ()) {
/*  293 */             this.count++;
/*  294 */             e = new Entry<>(k, this.defRetValue);
/*  295 */             if (p.right == null)
/*  296 */               this.lastEntry = e; 
/*  297 */             e.left = p;
/*  298 */             e.right = p.right;
/*  299 */             p.right(e);
/*      */             break;
/*      */           } 
/*  302 */           p = p.right; continue;
/*      */         } 
/*  304 */         if (p.pred()) {
/*  305 */           this.count++;
/*  306 */           e = new Entry<>(k, this.defRetValue);
/*  307 */           if (p.left == null)
/*  308 */             this.firstEntry = e; 
/*  309 */           e.right = p;
/*  310 */           e.left = p.left;
/*  311 */           p.left(e);
/*      */           break;
/*      */         } 
/*  314 */         p = p.left;
/*      */       } 
/*      */       
/*  317 */       this.modified = true;
/*  318 */       maxDepth = i--;
/*  319 */       while (i > 0 && !this.nodePath[i].black()) {
/*  320 */         if (!this.dirPath[i - 1]) {
/*  321 */           Entry<V> entry1 = (this.nodePath[i - 1]).right;
/*  322 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  323 */             this.nodePath[i].black(true);
/*  324 */             entry1.black(true);
/*  325 */             this.nodePath[i - 1].black(false);
/*  326 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  329 */           if (!this.dirPath[i]) {
/*  330 */             entry1 = this.nodePath[i];
/*      */           } else {
/*  332 */             Entry<V> entry = this.nodePath[i];
/*  333 */             entry1 = entry.right;
/*  334 */             entry.right = entry1.left;
/*  335 */             entry1.left = entry;
/*  336 */             (this.nodePath[i - 1]).left = entry1;
/*  337 */             if (entry1.pred()) {
/*  338 */               entry1.pred(false);
/*  339 */               entry.succ(entry1);
/*      */             } 
/*      */           } 
/*  342 */           Entry<V> entry2 = this.nodePath[i - 1];
/*  343 */           entry2.black(false);
/*  344 */           entry1.black(true);
/*  345 */           entry2.left = entry1.right;
/*  346 */           entry1.right = entry2;
/*  347 */           if (i < 2) {
/*  348 */             this.tree = entry1;
/*      */           }
/*  350 */           else if (this.dirPath[i - 2]) {
/*  351 */             (this.nodePath[i - 2]).right = entry1;
/*      */           } else {
/*  353 */             (this.nodePath[i - 2]).left = entry1;
/*      */           } 
/*  355 */           if (entry1.succ()) {
/*  356 */             entry1.succ(false);
/*  357 */             entry2.pred(entry1);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  362 */         Entry<V> y = (this.nodePath[i - 1]).left;
/*  363 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  364 */           this.nodePath[i].black(true);
/*  365 */           y.black(true);
/*  366 */           this.nodePath[i - 1].black(false);
/*  367 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  370 */         if (this.dirPath[i]) {
/*  371 */           y = this.nodePath[i];
/*      */         } else {
/*  373 */           Entry<V> entry = this.nodePath[i];
/*  374 */           y = entry.left;
/*  375 */           entry.left = y.right;
/*  376 */           y.right = entry;
/*  377 */           (this.nodePath[i - 1]).right = y;
/*  378 */           if (y.succ()) {
/*  379 */             y.succ(false);
/*  380 */             entry.pred(y);
/*      */           } 
/*      */         } 
/*  383 */         Entry<V> x = this.nodePath[i - 1];
/*  384 */         x.black(false);
/*  385 */         y.black(true);
/*  386 */         x.right = y.left;
/*  387 */         y.left = x;
/*  388 */         if (i < 2) {
/*  389 */           this.tree = y;
/*      */         }
/*  391 */         else if (this.dirPath[i - 2]) {
/*  392 */           (this.nodePath[i - 2]).right = y;
/*      */         } else {
/*  394 */           (this.nodePath[i - 2]).left = y;
/*      */         } 
/*  396 */         if (y.pred()) {
/*  397 */           y.pred(false);
/*  398 */           x.succ(y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  405 */     this.tree.black(true);
/*      */     
/*  407 */     while (maxDepth-- != 0)
/*  408 */       this.nodePath[maxDepth] = null; 
/*  409 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V remove(int k) {
/*  418 */     this.modified = false;
/*  419 */     if (this.tree == null)
/*  420 */       return this.defRetValue; 
/*  421 */     Entry<V> p = this.tree;
/*      */     
/*  423 */     int i = 0;
/*  424 */     int kk = k;
/*      */     int cmp;
/*  426 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  428 */       this.dirPath[i] = (cmp > 0);
/*  429 */       this.nodePath[i] = p;
/*  430 */       if (this.dirPath[i++]) {
/*  431 */         if ((p = p.right()) == null) {
/*      */           
/*  433 */           while (i-- != 0)
/*  434 */             this.nodePath[i] = null; 
/*  435 */           return this.defRetValue;
/*      */         }  continue;
/*      */       } 
/*  438 */       if ((p = p.left()) == null) {
/*      */         
/*  440 */         while (i-- != 0)
/*  441 */           this.nodePath[i] = null; 
/*  442 */         return this.defRetValue;
/*      */       } 
/*      */     } 
/*      */     
/*  446 */     if (p.left == null)
/*  447 */       this.firstEntry = p.next(); 
/*  448 */     if (p.right == null)
/*  449 */       this.lastEntry = p.prev(); 
/*  450 */     if (p.succ()) {
/*  451 */       if (p.pred()) {
/*  452 */         if (i == 0) {
/*  453 */           this.tree = p.left;
/*      */         }
/*  455 */         else if (this.dirPath[i - 1]) {
/*  456 */           this.nodePath[i - 1].succ(p.right);
/*      */         } else {
/*  458 */           this.nodePath[i - 1].pred(p.left);
/*      */         } 
/*      */       } else {
/*  461 */         (p.prev()).right = p.right;
/*  462 */         if (i == 0) {
/*  463 */           this.tree = p.left;
/*      */         }
/*  465 */         else if (this.dirPath[i - 1]) {
/*  466 */           (this.nodePath[i - 1]).right = p.left;
/*      */         } else {
/*  468 */           (this.nodePath[i - 1]).left = p.left;
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  473 */       Entry<V> r = p.right;
/*  474 */       if (r.pred()) {
/*  475 */         r.left = p.left;
/*  476 */         r.pred(p.pred());
/*  477 */         if (!r.pred())
/*  478 */           (r.prev()).right = r; 
/*  479 */         if (i == 0) {
/*  480 */           this.tree = r;
/*      */         }
/*  482 */         else if (this.dirPath[i - 1]) {
/*  483 */           (this.nodePath[i - 1]).right = r;
/*      */         } else {
/*  485 */           (this.nodePath[i - 1]).left = r;
/*      */         } 
/*  487 */         boolean color = r.black();
/*  488 */         r.black(p.black());
/*  489 */         p.black(color);
/*  490 */         this.dirPath[i] = true;
/*  491 */         this.nodePath[i++] = r;
/*      */       } else {
/*      */         Entry<V> s;
/*  494 */         int j = i++;
/*      */         while (true) {
/*  496 */           this.dirPath[i] = false;
/*  497 */           this.nodePath[i++] = r;
/*  498 */           s = r.left;
/*  499 */           if (s.pred())
/*      */             break; 
/*  501 */           r = s;
/*      */         } 
/*  503 */         this.dirPath[j] = true;
/*  504 */         this.nodePath[j] = s;
/*  505 */         if (s.succ()) {
/*  506 */           r.pred(s);
/*      */         } else {
/*  508 */           r.left = s.right;
/*  509 */         }  s.left = p.left;
/*  510 */         if (!p.pred()) {
/*  511 */           (p.prev()).right = s;
/*  512 */           s.pred(false);
/*      */         } 
/*  514 */         s.right(p.right);
/*  515 */         boolean color = s.black();
/*  516 */         s.black(p.black());
/*  517 */         p.black(color);
/*  518 */         if (j == 0) {
/*  519 */           this.tree = s;
/*      */         }
/*  521 */         else if (this.dirPath[j - 1]) {
/*  522 */           (this.nodePath[j - 1]).right = s;
/*      */         } else {
/*  524 */           (this.nodePath[j - 1]).left = s;
/*      */         } 
/*      */       } 
/*      */     } 
/*  528 */     int maxDepth = i;
/*  529 */     if (p.black()) {
/*  530 */       for (; i > 0; i--) {
/*  531 */         if ((this.dirPath[i - 1] && !this.nodePath[i - 1].succ()) || (!this.dirPath[i - 1] && !this.nodePath[i - 1].pred())) {
/*  532 */           Entry<V> x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  533 */           if (!x.black()) {
/*  534 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  538 */         if (!this.dirPath[i - 1]) {
/*  539 */           Entry<V> w = (this.nodePath[i - 1]).right;
/*  540 */           if (!w.black()) {
/*  541 */             w.black(true);
/*  542 */             this.nodePath[i - 1].black(false);
/*  543 */             (this.nodePath[i - 1]).right = w.left;
/*  544 */             w.left = this.nodePath[i - 1];
/*  545 */             if (i < 2) {
/*  546 */               this.tree = w;
/*      */             }
/*  548 */             else if (this.dirPath[i - 2]) {
/*  549 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  551 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  553 */             this.nodePath[i] = this.nodePath[i - 1];
/*  554 */             this.dirPath[i] = false;
/*  555 */             this.nodePath[i - 1] = w;
/*  556 */             if (maxDepth == i++)
/*  557 */               maxDepth++; 
/*  558 */             w = (this.nodePath[i - 1]).right;
/*      */           } 
/*  560 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  561 */             w.black(false);
/*      */           } else {
/*  563 */             if (w.succ() || w.right.black()) {
/*  564 */               Entry<V> y = w.left;
/*  565 */               y.black(true);
/*  566 */               w.black(false);
/*  567 */               w.left = y.right;
/*  568 */               y.right = w;
/*  569 */               w = (this.nodePath[i - 1]).right = y;
/*  570 */               if (w.succ()) {
/*  571 */                 w.succ(false);
/*  572 */                 w.right.pred(w);
/*      */               } 
/*      */             } 
/*  575 */             w.black(this.nodePath[i - 1].black());
/*  576 */             this.nodePath[i - 1].black(true);
/*  577 */             w.right.black(true);
/*  578 */             (this.nodePath[i - 1]).right = w.left;
/*  579 */             w.left = this.nodePath[i - 1];
/*  580 */             if (i < 2) {
/*  581 */               this.tree = w;
/*      */             }
/*  583 */             else if (this.dirPath[i - 2]) {
/*  584 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  586 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  588 */             if (w.pred()) {
/*  589 */               w.pred(false);
/*  590 */               this.nodePath[i - 1].succ(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } else {
/*  595 */           Entry<V> w = (this.nodePath[i - 1]).left;
/*  596 */           if (!w.black()) {
/*  597 */             w.black(true);
/*  598 */             this.nodePath[i - 1].black(false);
/*  599 */             (this.nodePath[i - 1]).left = w.right;
/*  600 */             w.right = this.nodePath[i - 1];
/*  601 */             if (i < 2) {
/*  602 */               this.tree = w;
/*      */             }
/*  604 */             else if (this.dirPath[i - 2]) {
/*  605 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  607 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  609 */             this.nodePath[i] = this.nodePath[i - 1];
/*  610 */             this.dirPath[i] = true;
/*  611 */             this.nodePath[i - 1] = w;
/*  612 */             if (maxDepth == i++)
/*  613 */               maxDepth++; 
/*  614 */             w = (this.nodePath[i - 1]).left;
/*      */           } 
/*  616 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  617 */             w.black(false);
/*      */           } else {
/*  619 */             if (w.pred() || w.left.black()) {
/*  620 */               Entry<V> y = w.right;
/*  621 */               y.black(true);
/*  622 */               w.black(false);
/*  623 */               w.right = y.left;
/*  624 */               y.left = w;
/*  625 */               w = (this.nodePath[i - 1]).left = y;
/*  626 */               if (w.pred()) {
/*  627 */                 w.pred(false);
/*  628 */                 w.left.succ(w);
/*      */               } 
/*      */             } 
/*  631 */             w.black(this.nodePath[i - 1].black());
/*  632 */             this.nodePath[i - 1].black(true);
/*  633 */             w.left.black(true);
/*  634 */             (this.nodePath[i - 1]).left = w.right;
/*  635 */             w.right = this.nodePath[i - 1];
/*  636 */             if (i < 2) {
/*  637 */               this.tree = w;
/*      */             }
/*  639 */             else if (this.dirPath[i - 2]) {
/*  640 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  642 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  644 */             if (w.succ()) {
/*  645 */               w.succ(false);
/*  646 */               this.nodePath[i - 1].pred(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  652 */       if (this.tree != null)
/*  653 */         this.tree.black(true); 
/*      */     } 
/*  655 */     this.modified = true;
/*  656 */     this.count--;
/*      */     
/*  658 */     while (maxDepth-- != 0)
/*  659 */       this.nodePath[maxDepth] = null; 
/*  660 */     return p.value;
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  664 */     ValueIterator i = new ValueIterator();
/*      */     
/*  666 */     int j = this.count;
/*  667 */     while (j-- != 0) {
/*  668 */       Object ev = i.next();
/*  669 */       if (Objects.equals(ev, v))
/*  670 */         return true; 
/*      */     } 
/*  672 */     return false;
/*      */   }
/*      */   
/*      */   public void clear() {
/*  676 */     this.count = 0;
/*  677 */     this.tree = null;
/*  678 */     this.entries = null;
/*  679 */     this.values = null;
/*  680 */     this.keys = null;
/*  681 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<V>
/*      */     extends AbstractInt2ObjectMap.BasicEntry<V>
/*      */     implements Cloneable
/*      */   {
/*      */     private static final int BLACK_MASK = 1;
/*      */ 
/*      */     
/*      */     private static final int SUCC_MASK = -2147483648;
/*      */ 
/*      */     
/*      */     private static final int PRED_MASK = 1073741824;
/*      */ 
/*      */     
/*      */     Entry<V> left;
/*      */ 
/*      */     
/*      */     Entry<V> right;
/*      */ 
/*      */     
/*      */     int info;
/*      */ 
/*      */     
/*      */     Entry() {
/*  709 */       super(0, (V)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(int k, V v) {
/*  720 */       super(k, v);
/*  721 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> left() {
/*  729 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> right() {
/*  737 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  745 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  753 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  762 */       if (pred) {
/*  763 */         this.info |= 0x40000000;
/*      */       } else {
/*  765 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  774 */       if (succ) {
/*  775 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  777 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<V> pred) {
/*  786 */       this.info |= 0x40000000;
/*  787 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<V> succ) {
/*  796 */       this.info |= Integer.MIN_VALUE;
/*  797 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<V> left) {
/*  806 */       this.info &= 0xBFFFFFFF;
/*  807 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<V> right) {
/*  816 */       this.info &= Integer.MAX_VALUE;
/*  817 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean black() {
/*  825 */       return ((this.info & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void black(boolean black) {
/*  834 */       if (black) {
/*  835 */         this.info |= 0x1;
/*      */       } else {
/*  837 */         this.info &= 0xFFFFFFFE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> next() {
/*  845 */       Entry<V> next = this.right;
/*  846 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  847 */         while ((next.info & 0x40000000) == 0)
/*  848 */           next = next.left;  
/*  849 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> prev() {
/*  857 */       Entry<V> prev = this.left;
/*  858 */       if ((this.info & 0x40000000) == 0)
/*  859 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  860 */           prev = prev.right;  
/*  861 */       return prev;
/*      */     }
/*      */     
/*      */     public V setValue(V value) {
/*  865 */       V oldValue = this.value;
/*  866 */       this.value = value;
/*  867 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry<V> clone() {
/*      */       Entry<V> c;
/*      */       try {
/*  874 */         c = (Entry<V>)super.clone();
/*  875 */       } catch (CloneNotSupportedException cantHappen) {
/*  876 */         throw new InternalError();
/*      */       } 
/*  878 */       c.key = this.key;
/*  879 */       c.value = this.value;
/*  880 */       c.info = this.info;
/*  881 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  886 */       if (!(o instanceof Map.Entry))
/*  887 */         return false; 
/*  888 */       Map.Entry<Integer, V> e = (Map.Entry<Integer, V>)o;
/*  889 */       return (this.key == ((Integer)e.getKey()).intValue() && Objects.equals(this.value, e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  893 */       return this.key ^ ((this.value == null) ? 0 : this.value.hashCode());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  897 */       return this.key + "=>" + this.value;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsKey(int k) {
/*  918 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  922 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  926 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(int k) {
/*  931 */     Entry<V> e = findKey(k);
/*  932 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public int firstIntKey() {
/*  936 */     if (this.tree == null)
/*  937 */       throw new NoSuchElementException(); 
/*  938 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public int lastIntKey() {
/*  942 */     if (this.tree == null)
/*  943 */       throw new NoSuchElementException(); 
/*  944 */     return this.lastEntry.key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class TreeIterator
/*      */   {
/*      */     Int2ObjectRBTreeMap.Entry<V> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Int2ObjectRBTreeMap.Entry<V> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Int2ObjectRBTreeMap.Entry<V> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  975 */     int index = 0;
/*      */     TreeIterator() {
/*  977 */       this.next = Int2ObjectRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(int k) {
/*  980 */       if ((this.next = Int2ObjectRBTreeMap.this.locateKey(k)) != null)
/*  981 */         if (Int2ObjectRBTreeMap.this.compare(this.next.key, k) <= 0) {
/*  982 */           this.prev = this.next;
/*  983 */           this.next = this.next.next();
/*      */         } else {
/*  985 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/*  989 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/*  992 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/*  995 */       this.next = this.next.next();
/*      */     }
/*      */     Int2ObjectRBTreeMap.Entry<V> nextEntry() {
/*  998 */       if (!hasNext())
/*  999 */         throw new NoSuchElementException(); 
/* 1000 */       this.curr = this.prev = this.next;
/* 1001 */       this.index++;
/* 1002 */       updateNext();
/* 1003 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1006 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Int2ObjectRBTreeMap.Entry<V> previousEntry() {
/* 1009 */       if (!hasPrevious())
/* 1010 */         throw new NoSuchElementException(); 
/* 1011 */       this.curr = this.next = this.prev;
/* 1012 */       this.index--;
/* 1013 */       updatePrevious();
/* 1014 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1017 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1020 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1023 */       if (this.curr == null) {
/* 1024 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1029 */       if (this.curr == this.prev)
/* 1030 */         this.index--; 
/* 1031 */       this.next = this.prev = this.curr;
/* 1032 */       updatePrevious();
/* 1033 */       updateNext();
/* 1034 */       Int2ObjectRBTreeMap.this.remove(this.curr.key);
/* 1035 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1038 */       int i = n;
/* 1039 */       while (i-- != 0 && hasNext())
/* 1040 */         nextEntry(); 
/* 1041 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1044 */       int i = n;
/* 1045 */       while (i-- != 0 && hasPrevious())
/* 1046 */         previousEntry(); 
/* 1047 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Int2ObjectMap.Entry<V>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(int k) {
/* 1060 */       super(k);
/*      */     }
/*      */     
/*      */     public Int2ObjectMap.Entry<V> next() {
/* 1064 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Int2ObjectMap.Entry<V> previous() {
/* 1068 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
/* 1073 */     if (this.entries == null)
/* 1074 */       this.entries = (ObjectSortedSet<Int2ObjectMap.Entry<V>>)new AbstractObjectSortedSet<Int2ObjectMap.Entry<V>>()
/*      */         {
/*      */           final Comparator<? super Int2ObjectMap.Entry<V>> comparator;
/*      */           
/*      */           public Comparator<? super Int2ObjectMap.Entry<V>> comparator() {
/* 1079 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> iterator() {
/* 1083 */             return (ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>>)new Int2ObjectRBTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> iterator(Int2ObjectMap.Entry<V> from) {
/* 1087 */             return (ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>>)new Int2ObjectRBTreeMap.EntryIterator(from.getIntKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1092 */             if (!(o instanceof Map.Entry))
/* 1093 */               return false; 
/* 1094 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1095 */             if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1096 */               return false; 
/* 1097 */             Int2ObjectRBTreeMap.Entry<V> f = Int2ObjectRBTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1098 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1103 */             if (!(o instanceof Map.Entry))
/* 1104 */               return false; 
/* 1105 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1106 */             if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1107 */               return false; 
/* 1108 */             Int2ObjectRBTreeMap.Entry<V> f = Int2ObjectRBTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1109 */             if (f == null || !Objects.equals(f.getValue(), e.getValue()))
/* 1110 */               return false; 
/* 1111 */             Int2ObjectRBTreeMap.this.remove(f.key);
/* 1112 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1116 */             return Int2ObjectRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1120 */             Int2ObjectRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Int2ObjectMap.Entry<V> first() {
/* 1124 */             return Int2ObjectRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Int2ObjectMap.Entry<V> last() {
/* 1128 */             return Int2ObjectRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Int2ObjectMap.Entry<V>> subSet(Int2ObjectMap.Entry<V> from, Int2ObjectMap.Entry<V> to) {
/* 1133 */             return Int2ObjectRBTreeMap.this.subMap(from.getIntKey(), to.getIntKey()).int2ObjectEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Int2ObjectMap.Entry<V>> headSet(Int2ObjectMap.Entry<V> to) {
/* 1137 */             return Int2ObjectRBTreeMap.this.headMap(to.getIntKey()).int2ObjectEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Int2ObjectMap.Entry<V>> tailSet(Int2ObjectMap.Entry<V> from) {
/* 1141 */             return Int2ObjectRBTreeMap.this.tailMap(from.getIntKey()).int2ObjectEntrySet();
/*      */           }
/*      */         }; 
/* 1144 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends TreeIterator
/*      */     implements IntListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(int k) {
/* 1160 */       super(k);
/*      */     }
/*      */     
/*      */     public int nextInt() {
/* 1164 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public int previousInt() {
/* 1168 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractInt2ObjectSortedMap<V>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public IntBidirectionalIterator iterator() {
/* 1175 */       return new Int2ObjectRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public IntBidirectionalIterator iterator(int from) {
/* 1179 */       return new Int2ObjectRBTreeMap.KeyIterator(from);
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
/*      */   public IntSortedSet keySet() {
/* 1194 */     if (this.keys == null)
/* 1195 */       this.keys = new KeySet(); 
/* 1196 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<V>
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public V next() {
/* 1211 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public V previous() {
/* 1215 */       return (previousEntry()).value;
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
/*      */   public ObjectCollection<V> values() {
/* 1230 */     if (this.values == null)
/* 1231 */       this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1234 */             return (ObjectIterator<V>)new Int2ObjectRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(Object k) {
/* 1238 */             return Int2ObjectRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1242 */             return Int2ObjectRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1246 */             Int2ObjectRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1249 */     return this.values;
/*      */   }
/*      */   
/*      */   public IntComparator comparator() {
/* 1253 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Int2ObjectSortedMap<V> headMap(int to) {
/* 1257 */     return new Submap(0, true, to, false);
/*      */   }
/*      */   
/*      */   public Int2ObjectSortedMap<V> tailMap(int from) {
/* 1261 */     return new Submap(from, false, 0, true);
/*      */   }
/*      */   
/*      */   public Int2ObjectSortedMap<V> subMap(int from, int to) {
/* 1265 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractInt2ObjectSortedMap<V>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     int from;
/*      */ 
/*      */ 
/*      */     
/*      */     int to;
/*      */ 
/*      */ 
/*      */     
/*      */     boolean bottom;
/*      */ 
/*      */ 
/*      */     
/*      */     boolean top;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<Int2ObjectMap.Entry<V>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient IntSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ObjectCollection<V> values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(int from, boolean bottom, int to, boolean top) {
/* 1309 */       if (!bottom && !top && Int2ObjectRBTreeMap.this.compare(from, to) > 0)
/* 1310 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1311 */       this.from = from;
/* 1312 */       this.bottom = bottom;
/* 1313 */       this.to = to;
/* 1314 */       this.top = top;
/* 1315 */       this.defRetValue = Int2ObjectRBTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1319 */       SubmapIterator i = new SubmapIterator();
/* 1320 */       while (i.hasNext()) {
/* 1321 */         i.nextEntry();
/* 1322 */         i.remove();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean in(int k) {
/* 1333 */       return ((this.bottom || Int2ObjectRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Int2ObjectRBTreeMap.this
/* 1334 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
/* 1338 */       if (this.entries == null)
/* 1339 */         this.entries = (ObjectSortedSet<Int2ObjectMap.Entry<V>>)new AbstractObjectSortedSet<Int2ObjectMap.Entry<V>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> iterator() {
/* 1342 */               return (ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>>)new Int2ObjectRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> iterator(Int2ObjectMap.Entry<V> from) {
/* 1347 */               return (ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>>)new Int2ObjectRBTreeMap.Submap.SubmapEntryIterator(from.getIntKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Int2ObjectMap.Entry<V>> comparator() {
/* 1351 */               return Int2ObjectRBTreeMap.this.int2ObjectEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1356 */               if (!(o instanceof Map.Entry))
/* 1357 */                 return false; 
/* 1358 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1359 */               if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1360 */                 return false; 
/* 1361 */               Int2ObjectRBTreeMap.Entry<V> f = Int2ObjectRBTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1362 */               return (f != null && Int2ObjectRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1367 */               if (!(o instanceof Map.Entry))
/* 1368 */                 return false; 
/* 1369 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1370 */               if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1371 */                 return false; 
/* 1372 */               Int2ObjectRBTreeMap.Entry<V> f = Int2ObjectRBTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1373 */               if (f != null && Int2ObjectRBTreeMap.Submap.this.in(f.key))
/* 1374 */                 Int2ObjectRBTreeMap.Submap.this.remove(f.key); 
/* 1375 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1379 */               int c = 0;
/* 1380 */               for (ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1381 */                 c++; 
/* 1382 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1386 */               return !(new Int2ObjectRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1390 */               Int2ObjectRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Int2ObjectMap.Entry<V> first() {
/* 1394 */               return Int2ObjectRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Int2ObjectMap.Entry<V> last() {
/* 1398 */               return Int2ObjectRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Int2ObjectMap.Entry<V>> subSet(Int2ObjectMap.Entry<V> from, Int2ObjectMap.Entry<V> to) {
/* 1403 */               return Int2ObjectRBTreeMap.Submap.this.subMap(from.getIntKey(), to.getIntKey()).int2ObjectEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Int2ObjectMap.Entry<V>> headSet(Int2ObjectMap.Entry<V> to) {
/* 1407 */               return Int2ObjectRBTreeMap.Submap.this.headMap(to.getIntKey()).int2ObjectEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Int2ObjectMap.Entry<V>> tailSet(Int2ObjectMap.Entry<V> from) {
/* 1411 */               return Int2ObjectRBTreeMap.Submap.this.tailMap(from.getIntKey()).int2ObjectEntrySet();
/*      */             }
/*      */           }; 
/* 1414 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractInt2ObjectSortedMap<V>.KeySet {
/*      */       public IntBidirectionalIterator iterator() {
/* 1419 */         return new Int2ObjectRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public IntBidirectionalIterator iterator(int from) {
/* 1423 */         return new Int2ObjectRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public IntSortedSet keySet() {
/* 1428 */       if (this.keys == null)
/* 1429 */         this.keys = new KeySet(); 
/* 1430 */       return this.keys;
/*      */     }
/*      */     
/*      */     public ObjectCollection<V> values() {
/* 1434 */       if (this.values == null)
/* 1435 */         this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */           {
/*      */             public ObjectIterator<V> iterator() {
/* 1438 */               return (ObjectIterator<V>)new Int2ObjectRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(Object k) {
/* 1442 */               return Int2ObjectRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1446 */               return Int2ObjectRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1450 */               Int2ObjectRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1453 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(int k) {
/* 1458 */       return (in(k) && Int2ObjectRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(Object v) {
/* 1462 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1464 */       while (i.hasNext()) {
/* 1465 */         Object ev = (i.nextEntry()).value;
/* 1466 */         if (Objects.equals(ev, v))
/* 1467 */           return true; 
/*      */       } 
/* 1469 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(int k) {
/* 1475 */       int kk = k; Int2ObjectRBTreeMap.Entry<V> e;
/* 1476 */       return (in(kk) && (e = Int2ObjectRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public V put(int k, V v) {
/* 1480 */       Int2ObjectRBTreeMap.this.modified = false;
/* 1481 */       if (!in(k))
/* 1482 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1483 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1484 */       V oldValue = Int2ObjectRBTreeMap.this.put(k, v);
/* 1485 */       return Int2ObjectRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public V remove(int k) {
/* 1490 */       Int2ObjectRBTreeMap.this.modified = false;
/* 1491 */       if (!in(k))
/* 1492 */         return this.defRetValue; 
/* 1493 */       V oldValue = Int2ObjectRBTreeMap.this.remove(k);
/* 1494 */       return Int2ObjectRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1498 */       SubmapIterator i = new SubmapIterator();
/* 1499 */       int n = 0;
/* 1500 */       while (i.hasNext()) {
/* 1501 */         n++;
/* 1502 */         i.nextEntry();
/*      */       } 
/* 1504 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1508 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public IntComparator comparator() {
/* 1512 */       return Int2ObjectRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Int2ObjectSortedMap<V> headMap(int to) {
/* 1516 */       if (this.top)
/* 1517 */         return new Submap(this.from, this.bottom, to, false); 
/* 1518 */       return (Int2ObjectRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Int2ObjectSortedMap<V> tailMap(int from) {
/* 1522 */       if (this.bottom)
/* 1523 */         return new Submap(from, false, this.to, this.top); 
/* 1524 */       return (Int2ObjectRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Int2ObjectSortedMap<V> subMap(int from, int to) {
/* 1528 */       if (this.top && this.bottom)
/* 1529 */         return new Submap(from, false, to, false); 
/* 1530 */       if (!this.top)
/* 1531 */         to = (Int2ObjectRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1532 */       if (!this.bottom)
/* 1533 */         from = (Int2ObjectRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1534 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1535 */         return this; 
/* 1536 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Int2ObjectRBTreeMap.Entry<V> firstEntry() {
/*      */       Int2ObjectRBTreeMap.Entry<V> e;
/* 1545 */       if (Int2ObjectRBTreeMap.this.tree == null) {
/* 1546 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1550 */       if (this.bottom) {
/* 1551 */         e = Int2ObjectRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1553 */         e = Int2ObjectRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1555 */         if (Int2ObjectRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1556 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1560 */       if (e == null || (!this.top && Int2ObjectRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1561 */         return null; 
/* 1562 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Int2ObjectRBTreeMap.Entry<V> lastEntry() {
/*      */       Int2ObjectRBTreeMap.Entry<V> e;
/* 1571 */       if (Int2ObjectRBTreeMap.this.tree == null) {
/* 1572 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1576 */       if (this.top) {
/* 1577 */         e = Int2ObjectRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1579 */         e = Int2ObjectRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1581 */         if (Int2ObjectRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1582 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1586 */       if (e == null || (!this.bottom && Int2ObjectRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1587 */         return null; 
/* 1588 */       return e;
/*      */     }
/*      */     
/*      */     public int firstIntKey() {
/* 1592 */       Int2ObjectRBTreeMap.Entry<V> e = firstEntry();
/* 1593 */       if (e == null)
/* 1594 */         throw new NoSuchElementException(); 
/* 1595 */       return e.key;
/*      */     }
/*      */     
/*      */     public int lastIntKey() {
/* 1599 */       Int2ObjectRBTreeMap.Entry<V> e = lastEntry();
/* 1600 */       if (e == null)
/* 1601 */         throw new NoSuchElementException(); 
/* 1602 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Int2ObjectRBTreeMap<V>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1615 */         this.next = Int2ObjectRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(int k) {
/* 1618 */         this();
/* 1619 */         if (this.next != null)
/* 1620 */           if (!Int2ObjectRBTreeMap.Submap.this.bottom && Int2ObjectRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1621 */             this.prev = null;
/* 1622 */           } else if (!Int2ObjectRBTreeMap.Submap.this.top && Int2ObjectRBTreeMap.this.compare(k, (this.prev = Int2ObjectRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1623 */             this.next = null;
/*      */           } else {
/* 1625 */             this.next = Int2ObjectRBTreeMap.this.locateKey(k);
/* 1626 */             if (Int2ObjectRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1627 */               this.prev = this.next;
/* 1628 */               this.next = this.next.next();
/*      */             } else {
/* 1630 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1636 */         this.prev = this.prev.prev();
/* 1637 */         if (!Int2ObjectRBTreeMap.Submap.this.bottom && this.prev != null && Int2ObjectRBTreeMap.this.compare(this.prev.key, Int2ObjectRBTreeMap.Submap.this.from) < 0)
/* 1638 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1642 */         this.next = this.next.next();
/* 1643 */         if (!Int2ObjectRBTreeMap.Submap.this.top && this.next != null && Int2ObjectRBTreeMap.this.compare(this.next.key, Int2ObjectRBTreeMap.Submap.this.to) >= 0)
/* 1644 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Int2ObjectMap.Entry<V>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(int k) {
/* 1651 */         super(k);
/*      */       }
/*      */       
/*      */       public Int2ObjectMap.Entry<V> next() {
/* 1655 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Int2ObjectMap.Entry<V> previous() {
/* 1659 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements IntListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(int from) {
/* 1677 */         super(from);
/*      */       }
/*      */       
/*      */       public int nextInt() {
/* 1681 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public int previousInt() {
/* 1685 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements ObjectListIterator<V>
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public V next() {
/* 1701 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public V previous() {
/* 1705 */         return (previousEntry()).value;
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
/*      */   public Int2ObjectRBTreeMap<V> clone() {
/*      */     Int2ObjectRBTreeMap<V> c;
/*      */     try {
/* 1724 */       c = (Int2ObjectRBTreeMap<V>)super.clone();
/* 1725 */     } catch (CloneNotSupportedException cantHappen) {
/* 1726 */       throw new InternalError();
/*      */     } 
/* 1728 */     c.keys = null;
/* 1729 */     c.values = null;
/* 1730 */     c.entries = null;
/* 1731 */     c.allocatePaths();
/* 1732 */     if (this.count != 0) {
/*      */       
/* 1734 */       Entry<V> rp = new Entry<>(), rq = new Entry<>();
/* 1735 */       Entry<V> p = rp;
/* 1736 */       rp.left(this.tree);
/* 1737 */       Entry<V> q = rq;
/* 1738 */       rq.pred((Entry<V>)null);
/*      */       while (true) {
/* 1740 */         if (!p.pred()) {
/* 1741 */           Entry<V> e = p.left.clone();
/* 1742 */           e.pred(q.left);
/* 1743 */           e.succ(q);
/* 1744 */           q.left(e);
/* 1745 */           p = p.left;
/* 1746 */           q = q.left;
/*      */         } else {
/* 1748 */           while (p.succ()) {
/* 1749 */             p = p.right;
/* 1750 */             if (p == null) {
/* 1751 */               q.right = null;
/* 1752 */               c.tree = rq.left;
/* 1753 */               c.firstEntry = c.tree;
/* 1754 */               while (c.firstEntry.left != null)
/* 1755 */                 c.firstEntry = c.firstEntry.left; 
/* 1756 */               c.lastEntry = c.tree;
/* 1757 */               while (c.lastEntry.right != null)
/* 1758 */                 c.lastEntry = c.lastEntry.right; 
/* 1759 */               return c;
/*      */             } 
/* 1761 */             q = q.right;
/*      */           } 
/* 1763 */           p = p.right;
/* 1764 */           q = q.right;
/*      */         } 
/* 1766 */         if (!p.succ()) {
/* 1767 */           Entry<V> e = p.right.clone();
/* 1768 */           e.succ(q.right);
/* 1769 */           e.pred(q);
/* 1770 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1774 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1777 */     int n = this.count;
/* 1778 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1780 */     s.defaultWriteObject();
/* 1781 */     while (n-- != 0) {
/* 1782 */       Entry<V> e = i.nextEntry();
/* 1783 */       s.writeInt(e.key);
/* 1784 */       s.writeObject(e.value);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry<V> readTree(ObjectInputStream s, int n, Entry<V> pred, Entry<V> succ) throws IOException, ClassNotFoundException {
/* 1805 */     if (n == 1) {
/* 1806 */       Entry<V> entry = new Entry<>(s.readInt(), (V)s.readObject());
/* 1807 */       entry.pred(pred);
/* 1808 */       entry.succ(succ);
/* 1809 */       entry.black(true);
/* 1810 */       return entry;
/*      */     } 
/* 1812 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1817 */       Entry<V> entry = new Entry<>(s.readInt(), (V)s.readObject());
/* 1818 */       entry.black(true);
/* 1819 */       entry.right(new Entry<>(s.readInt(), (V)s.readObject()));
/* 1820 */       entry.right.pred(entry);
/* 1821 */       entry.pred(pred);
/* 1822 */       entry.right.succ(succ);
/* 1823 */       return entry;
/*      */     } 
/*      */     
/* 1826 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1827 */     Entry<V> top = new Entry<>();
/* 1828 */     top.left(readTree(s, leftN, pred, top));
/* 1829 */     top.key = s.readInt();
/* 1830 */     top.value = (V)s.readObject();
/* 1831 */     top.black(true);
/* 1832 */     top.right(readTree(s, rightN, top, succ));
/* 1833 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1834 */       top.right.black(false); 
/* 1835 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1838 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1843 */     setActualComparator();
/* 1844 */     allocatePaths();
/* 1845 */     if (this.count != 0) {
/* 1846 */       this.tree = readTree(s, this.count, (Entry<V>)null, (Entry<V>)null);
/*      */       
/* 1848 */       Entry<V> e = this.tree;
/* 1849 */       while (e.left() != null)
/* 1850 */         e = e.left(); 
/* 1851 */       this.firstEntry = e;
/* 1852 */       e = this.tree;
/* 1853 */       while (e.right() != null)
/* 1854 */         e = e.right(); 
/* 1855 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2ObjectRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */