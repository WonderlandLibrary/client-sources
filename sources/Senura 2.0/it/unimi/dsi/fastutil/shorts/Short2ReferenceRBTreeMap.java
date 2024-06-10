/*      */ package it.unimi.dsi.fastutil.shorts;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Short2ReferenceRBTreeMap<V>
/*      */   extends AbstractShort2ReferenceSortedMap<V>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<V> tree;
/*      */   protected int count;
/*      */   protected transient Entry<V> firstEntry;
/*      */   protected transient Entry<V> lastEntry;
/*      */   protected transient ObjectSortedSet<Short2ReferenceMap.Entry<V>> entries;
/*      */   protected transient ShortSortedSet keys;
/*      */   protected transient ReferenceCollection<V> values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Short> storedComparator;
/*      */   protected transient ShortComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry<V>[] nodePath;
/*      */   
/*      */   public Short2ReferenceRBTreeMap() {
/*   74 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   80 */     this.tree = null;
/*   81 */     this.count = 0;
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
/*   93 */     this.actualComparator = ShortComparators.asShortComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ReferenceRBTreeMap(Comparator<? super Short> c) {
/*  102 */     this();
/*  103 */     this.storedComparator = c;
/*  104 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ReferenceRBTreeMap(Map<? extends Short, ? extends V> m) {
/*  113 */     this();
/*  114 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ReferenceRBTreeMap(SortedMap<Short, V> m) {
/*  124 */     this(m.comparator());
/*  125 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ReferenceRBTreeMap(Short2ReferenceMap<? extends V> m) {
/*  134 */     this();
/*  135 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ReferenceRBTreeMap(Short2ReferenceSortedMap<V> m) {
/*  145 */     this(m.comparator());
/*  146 */     putAll(m);
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
/*      */   public Short2ReferenceRBTreeMap(short[] k, V[] v, Comparator<? super Short> c) {
/*  162 */     this(c);
/*  163 */     if (k.length != v.length) {
/*  164 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  166 */     for (int i = 0; i < k.length; i++) {
/*  167 */       put(k[i], v[i]);
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
/*      */   public Short2ReferenceRBTreeMap(short[] k, V[] v) {
/*  180 */     this(k, v, (Comparator<? super Short>)null);
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
/*      */   final int compare(short k1, short k2) {
/*  208 */     return (this.actualComparator == null) ? Short.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry<V> findKey(short k) {
/*  220 */     Entry<V> e = this.tree;
/*      */     int cmp;
/*  222 */     while (e != null && (cmp = compare(k, e.key)) != 0)
/*  223 */       e = (cmp < 0) ? e.left() : e.right(); 
/*  224 */     return e;
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
/*      */   final Entry<V> locateKey(short k) {
/*  236 */     Entry<V> e = this.tree, last = this.tree;
/*  237 */     int cmp = 0;
/*  238 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  239 */       last = e;
/*  240 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  242 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  252 */     this.dirPath = new boolean[64];
/*  253 */     this.nodePath = (Entry<V>[])new Entry[64];
/*      */   }
/*      */   
/*      */   public V put(short k, V v) {
/*  257 */     Entry<V> e = add(k);
/*  258 */     V oldValue = e.value;
/*  259 */     e.value = v;
/*  260 */     return oldValue;
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
/*      */   private Entry<V> add(short k) {
/*      */     Entry<V> e;
/*  277 */     this.modified = false;
/*  278 */     int maxDepth = 0;
/*      */     
/*  280 */     if (this.tree == null) {
/*  281 */       this.count++;
/*  282 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*      */     } else {
/*  284 */       Entry<V> p = this.tree;
/*  285 */       int i = 0; while (true) {
/*      */         int cmp;
/*  287 */         if ((cmp = compare(k, p.key)) == 0) {
/*      */           
/*  289 */           while (i-- != 0)
/*  290 */             this.nodePath[i] = null; 
/*  291 */           return p;
/*      */         } 
/*  293 */         this.nodePath[i] = p;
/*  294 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  295 */           if (p.succ()) {
/*  296 */             this.count++;
/*  297 */             e = new Entry<>(k, this.defRetValue);
/*  298 */             if (p.right == null)
/*  299 */               this.lastEntry = e; 
/*  300 */             e.left = p;
/*  301 */             e.right = p.right;
/*  302 */             p.right(e);
/*      */             break;
/*      */           } 
/*  305 */           p = p.right; continue;
/*      */         } 
/*  307 */         if (p.pred()) {
/*  308 */           this.count++;
/*  309 */           e = new Entry<>(k, this.defRetValue);
/*  310 */           if (p.left == null)
/*  311 */             this.firstEntry = e; 
/*  312 */           e.right = p;
/*  313 */           e.left = p.left;
/*  314 */           p.left(e);
/*      */           break;
/*      */         } 
/*  317 */         p = p.left;
/*      */       } 
/*      */       
/*  320 */       this.modified = true;
/*  321 */       maxDepth = i--;
/*  322 */       while (i > 0 && !this.nodePath[i].black()) {
/*  323 */         if (!this.dirPath[i - 1]) {
/*  324 */           Entry<V> entry1 = (this.nodePath[i - 1]).right;
/*  325 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  326 */             this.nodePath[i].black(true);
/*  327 */             entry1.black(true);
/*  328 */             this.nodePath[i - 1].black(false);
/*  329 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  332 */           if (!this.dirPath[i]) {
/*  333 */             entry1 = this.nodePath[i];
/*      */           } else {
/*  335 */             Entry<V> entry = this.nodePath[i];
/*  336 */             entry1 = entry.right;
/*  337 */             entry.right = entry1.left;
/*  338 */             entry1.left = entry;
/*  339 */             (this.nodePath[i - 1]).left = entry1;
/*  340 */             if (entry1.pred()) {
/*  341 */               entry1.pred(false);
/*  342 */               entry.succ(entry1);
/*      */             } 
/*      */           } 
/*  345 */           Entry<V> entry2 = this.nodePath[i - 1];
/*  346 */           entry2.black(false);
/*  347 */           entry1.black(true);
/*  348 */           entry2.left = entry1.right;
/*  349 */           entry1.right = entry2;
/*  350 */           if (i < 2) {
/*  351 */             this.tree = entry1;
/*      */           }
/*  353 */           else if (this.dirPath[i - 2]) {
/*  354 */             (this.nodePath[i - 2]).right = entry1;
/*      */           } else {
/*  356 */             (this.nodePath[i - 2]).left = entry1;
/*      */           } 
/*  358 */           if (entry1.succ()) {
/*  359 */             entry1.succ(false);
/*  360 */             entry2.pred(entry1);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  365 */         Entry<V> y = (this.nodePath[i - 1]).left;
/*  366 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  367 */           this.nodePath[i].black(true);
/*  368 */           y.black(true);
/*  369 */           this.nodePath[i - 1].black(false);
/*  370 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  373 */         if (this.dirPath[i]) {
/*  374 */           y = this.nodePath[i];
/*      */         } else {
/*  376 */           Entry<V> entry = this.nodePath[i];
/*  377 */           y = entry.left;
/*  378 */           entry.left = y.right;
/*  379 */           y.right = entry;
/*  380 */           (this.nodePath[i - 1]).right = y;
/*  381 */           if (y.succ()) {
/*  382 */             y.succ(false);
/*  383 */             entry.pred(y);
/*      */           } 
/*      */         } 
/*  386 */         Entry<V> x = this.nodePath[i - 1];
/*  387 */         x.black(false);
/*  388 */         y.black(true);
/*  389 */         x.right = y.left;
/*  390 */         y.left = x;
/*  391 */         if (i < 2) {
/*  392 */           this.tree = y;
/*      */         }
/*  394 */         else if (this.dirPath[i - 2]) {
/*  395 */           (this.nodePath[i - 2]).right = y;
/*      */         } else {
/*  397 */           (this.nodePath[i - 2]).left = y;
/*      */         } 
/*  399 */         if (y.pred()) {
/*  400 */           y.pred(false);
/*  401 */           x.succ(y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  408 */     this.tree.black(true);
/*      */     
/*  410 */     while (maxDepth-- != 0)
/*  411 */       this.nodePath[maxDepth] = null; 
/*  412 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V remove(short k) {
/*  421 */     this.modified = false;
/*  422 */     if (this.tree == null)
/*  423 */       return this.defRetValue; 
/*  424 */     Entry<V> p = this.tree;
/*      */     
/*  426 */     int i = 0;
/*  427 */     short kk = k;
/*      */     int cmp;
/*  429 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  431 */       this.dirPath[i] = (cmp > 0);
/*  432 */       this.nodePath[i] = p;
/*  433 */       if (this.dirPath[i++]) {
/*  434 */         if ((p = p.right()) == null) {
/*      */           
/*  436 */           while (i-- != 0)
/*  437 */             this.nodePath[i] = null; 
/*  438 */           return this.defRetValue;
/*      */         }  continue;
/*      */       } 
/*  441 */       if ((p = p.left()) == null) {
/*      */         
/*  443 */         while (i-- != 0)
/*  444 */           this.nodePath[i] = null; 
/*  445 */         return this.defRetValue;
/*      */       } 
/*      */     } 
/*      */     
/*  449 */     if (p.left == null)
/*  450 */       this.firstEntry = p.next(); 
/*  451 */     if (p.right == null)
/*  452 */       this.lastEntry = p.prev(); 
/*  453 */     if (p.succ()) {
/*  454 */       if (p.pred()) {
/*  455 */         if (i == 0) {
/*  456 */           this.tree = p.left;
/*      */         }
/*  458 */         else if (this.dirPath[i - 1]) {
/*  459 */           this.nodePath[i - 1].succ(p.right);
/*      */         } else {
/*  461 */           this.nodePath[i - 1].pred(p.left);
/*      */         } 
/*      */       } else {
/*  464 */         (p.prev()).right = p.right;
/*  465 */         if (i == 0) {
/*  466 */           this.tree = p.left;
/*      */         }
/*  468 */         else if (this.dirPath[i - 1]) {
/*  469 */           (this.nodePath[i - 1]).right = p.left;
/*      */         } else {
/*  471 */           (this.nodePath[i - 1]).left = p.left;
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  476 */       Entry<V> r = p.right;
/*  477 */       if (r.pred()) {
/*  478 */         r.left = p.left;
/*  479 */         r.pred(p.pred());
/*  480 */         if (!r.pred())
/*  481 */           (r.prev()).right = r; 
/*  482 */         if (i == 0) {
/*  483 */           this.tree = r;
/*      */         }
/*  485 */         else if (this.dirPath[i - 1]) {
/*  486 */           (this.nodePath[i - 1]).right = r;
/*      */         } else {
/*  488 */           (this.nodePath[i - 1]).left = r;
/*      */         } 
/*  490 */         boolean color = r.black();
/*  491 */         r.black(p.black());
/*  492 */         p.black(color);
/*  493 */         this.dirPath[i] = true;
/*  494 */         this.nodePath[i++] = r;
/*      */       } else {
/*      */         Entry<V> s;
/*  497 */         int j = i++;
/*      */         while (true) {
/*  499 */           this.dirPath[i] = false;
/*  500 */           this.nodePath[i++] = r;
/*  501 */           s = r.left;
/*  502 */           if (s.pred())
/*      */             break; 
/*  504 */           r = s;
/*      */         } 
/*  506 */         this.dirPath[j] = true;
/*  507 */         this.nodePath[j] = s;
/*  508 */         if (s.succ()) {
/*  509 */           r.pred(s);
/*      */         } else {
/*  511 */           r.left = s.right;
/*  512 */         }  s.left = p.left;
/*  513 */         if (!p.pred()) {
/*  514 */           (p.prev()).right = s;
/*  515 */           s.pred(false);
/*      */         } 
/*  517 */         s.right(p.right);
/*  518 */         boolean color = s.black();
/*  519 */         s.black(p.black());
/*  520 */         p.black(color);
/*  521 */         if (j == 0) {
/*  522 */           this.tree = s;
/*      */         }
/*  524 */         else if (this.dirPath[j - 1]) {
/*  525 */           (this.nodePath[j - 1]).right = s;
/*      */         } else {
/*  527 */           (this.nodePath[j - 1]).left = s;
/*      */         } 
/*      */       } 
/*      */     } 
/*  531 */     int maxDepth = i;
/*  532 */     if (p.black()) {
/*  533 */       for (; i > 0; i--) {
/*  534 */         if ((this.dirPath[i - 1] && !this.nodePath[i - 1].succ()) || (!this.dirPath[i - 1] && !this.nodePath[i - 1].pred())) {
/*  535 */           Entry<V> x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  536 */           if (!x.black()) {
/*  537 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  541 */         if (!this.dirPath[i - 1]) {
/*  542 */           Entry<V> w = (this.nodePath[i - 1]).right;
/*  543 */           if (!w.black()) {
/*  544 */             w.black(true);
/*  545 */             this.nodePath[i - 1].black(false);
/*  546 */             (this.nodePath[i - 1]).right = w.left;
/*  547 */             w.left = this.nodePath[i - 1];
/*  548 */             if (i < 2) {
/*  549 */               this.tree = w;
/*      */             }
/*  551 */             else if (this.dirPath[i - 2]) {
/*  552 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  554 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  556 */             this.nodePath[i] = this.nodePath[i - 1];
/*  557 */             this.dirPath[i] = false;
/*  558 */             this.nodePath[i - 1] = w;
/*  559 */             if (maxDepth == i++)
/*  560 */               maxDepth++; 
/*  561 */             w = (this.nodePath[i - 1]).right;
/*      */           } 
/*  563 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  564 */             w.black(false);
/*      */           } else {
/*  566 */             if (w.succ() || w.right.black()) {
/*  567 */               Entry<V> y = w.left;
/*  568 */               y.black(true);
/*  569 */               w.black(false);
/*  570 */               w.left = y.right;
/*  571 */               y.right = w;
/*  572 */               w = (this.nodePath[i - 1]).right = y;
/*  573 */               if (w.succ()) {
/*  574 */                 w.succ(false);
/*  575 */                 w.right.pred(w);
/*      */               } 
/*      */             } 
/*  578 */             w.black(this.nodePath[i - 1].black());
/*  579 */             this.nodePath[i - 1].black(true);
/*  580 */             w.right.black(true);
/*  581 */             (this.nodePath[i - 1]).right = w.left;
/*  582 */             w.left = this.nodePath[i - 1];
/*  583 */             if (i < 2) {
/*  584 */               this.tree = w;
/*      */             }
/*  586 */             else if (this.dirPath[i - 2]) {
/*  587 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  589 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  591 */             if (w.pred()) {
/*  592 */               w.pred(false);
/*  593 */               this.nodePath[i - 1].succ(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } else {
/*  598 */           Entry<V> w = (this.nodePath[i - 1]).left;
/*  599 */           if (!w.black()) {
/*  600 */             w.black(true);
/*  601 */             this.nodePath[i - 1].black(false);
/*  602 */             (this.nodePath[i - 1]).left = w.right;
/*  603 */             w.right = this.nodePath[i - 1];
/*  604 */             if (i < 2) {
/*  605 */               this.tree = w;
/*      */             }
/*  607 */             else if (this.dirPath[i - 2]) {
/*  608 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  610 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  612 */             this.nodePath[i] = this.nodePath[i - 1];
/*  613 */             this.dirPath[i] = true;
/*  614 */             this.nodePath[i - 1] = w;
/*  615 */             if (maxDepth == i++)
/*  616 */               maxDepth++; 
/*  617 */             w = (this.nodePath[i - 1]).left;
/*      */           } 
/*  619 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  620 */             w.black(false);
/*      */           } else {
/*  622 */             if (w.pred() || w.left.black()) {
/*  623 */               Entry<V> y = w.right;
/*  624 */               y.black(true);
/*  625 */               w.black(false);
/*  626 */               w.right = y.left;
/*  627 */               y.left = w;
/*  628 */               w = (this.nodePath[i - 1]).left = y;
/*  629 */               if (w.pred()) {
/*  630 */                 w.pred(false);
/*  631 */                 w.left.succ(w);
/*      */               } 
/*      */             } 
/*  634 */             w.black(this.nodePath[i - 1].black());
/*  635 */             this.nodePath[i - 1].black(true);
/*  636 */             w.left.black(true);
/*  637 */             (this.nodePath[i - 1]).left = w.right;
/*  638 */             w.right = this.nodePath[i - 1];
/*  639 */             if (i < 2) {
/*  640 */               this.tree = w;
/*      */             }
/*  642 */             else if (this.dirPath[i - 2]) {
/*  643 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  645 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  647 */             if (w.succ()) {
/*  648 */               w.succ(false);
/*  649 */               this.nodePath[i - 1].pred(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  655 */       if (this.tree != null)
/*  656 */         this.tree.black(true); 
/*      */     } 
/*  658 */     this.modified = true;
/*  659 */     this.count--;
/*      */     
/*  661 */     while (maxDepth-- != 0)
/*  662 */       this.nodePath[maxDepth] = null; 
/*  663 */     return p.value;
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  667 */     ValueIterator i = new ValueIterator();
/*      */     
/*  669 */     int j = this.count;
/*  670 */     while (j-- != 0) {
/*  671 */       Object ev = i.next();
/*  672 */       if (ev == v)
/*  673 */         return true; 
/*      */     } 
/*  675 */     return false;
/*      */   }
/*      */   
/*      */   public void clear() {
/*  679 */     this.count = 0;
/*  680 */     this.tree = null;
/*  681 */     this.entries = null;
/*  682 */     this.values = null;
/*  683 */     this.keys = null;
/*  684 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<V>
/*      */     extends AbstractShort2ReferenceMap.BasicEntry<V>
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
/*  712 */       super((short)0, (V)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(short k, V v) {
/*  723 */       super(k, v);
/*  724 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> left() {
/*  732 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> right() {
/*  740 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  748 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  756 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  765 */       if (pred) {
/*  766 */         this.info |= 0x40000000;
/*      */       } else {
/*  768 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  777 */       if (succ) {
/*  778 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  780 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<V> pred) {
/*  789 */       this.info |= 0x40000000;
/*  790 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<V> succ) {
/*  799 */       this.info |= Integer.MIN_VALUE;
/*  800 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<V> left) {
/*  809 */       this.info &= 0xBFFFFFFF;
/*  810 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<V> right) {
/*  819 */       this.info &= Integer.MAX_VALUE;
/*  820 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean black() {
/*  828 */       return ((this.info & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void black(boolean black) {
/*  837 */       if (black) {
/*  838 */         this.info |= 0x1;
/*      */       } else {
/*  840 */         this.info &= 0xFFFFFFFE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> next() {
/*  848 */       Entry<V> next = this.right;
/*  849 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  850 */         while ((next.info & 0x40000000) == 0)
/*  851 */           next = next.left;  
/*  852 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> prev() {
/*  860 */       Entry<V> prev = this.left;
/*  861 */       if ((this.info & 0x40000000) == 0)
/*  862 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  863 */           prev = prev.right;  
/*  864 */       return prev;
/*      */     }
/*      */     
/*      */     public V setValue(V value) {
/*  868 */       V oldValue = this.value;
/*  869 */       this.value = value;
/*  870 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry<V> clone() {
/*      */       Entry<V> c;
/*      */       try {
/*  877 */         c = (Entry<V>)super.clone();
/*  878 */       } catch (CloneNotSupportedException cantHappen) {
/*  879 */         throw new InternalError();
/*      */       } 
/*  881 */       c.key = this.key;
/*  882 */       c.value = this.value;
/*  883 */       c.info = this.info;
/*  884 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  889 */       if (!(o instanceof Map.Entry))
/*  890 */         return false; 
/*  891 */       Map.Entry<Short, V> e = (Map.Entry<Short, V>)o;
/*  892 */       return (this.key == ((Short)e.getKey()).shortValue() && this.value == e.getValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  896 */       return this.key ^ ((this.value == null) ? 0 : System.identityHashCode(this.value));
/*      */     }
/*      */     
/*      */     public String toString() {
/*  900 */       return this.key + "=>" + this.value;
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
/*      */   public boolean containsKey(short k) {
/*  921 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  925 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  929 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(short k) {
/*  934 */     Entry<V> e = findKey(k);
/*  935 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public short firstShortKey() {
/*  939 */     if (this.tree == null)
/*  940 */       throw new NoSuchElementException(); 
/*  941 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public short lastShortKey() {
/*  945 */     if (this.tree == null)
/*  946 */       throw new NoSuchElementException(); 
/*  947 */     return this.lastEntry.key;
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
/*      */     Short2ReferenceRBTreeMap.Entry<V> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Short2ReferenceRBTreeMap.Entry<V> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Short2ReferenceRBTreeMap.Entry<V> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  978 */     int index = 0;
/*      */     TreeIterator() {
/*  980 */       this.next = Short2ReferenceRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(short k) {
/*  983 */       if ((this.next = Short2ReferenceRBTreeMap.this.locateKey(k)) != null)
/*  984 */         if (Short2ReferenceRBTreeMap.this.compare(this.next.key, k) <= 0) {
/*  985 */           this.prev = this.next;
/*  986 */           this.next = this.next.next();
/*      */         } else {
/*  988 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/*  992 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/*  995 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/*  998 */       this.next = this.next.next();
/*      */     }
/*      */     Short2ReferenceRBTreeMap.Entry<V> nextEntry() {
/* 1001 */       if (!hasNext())
/* 1002 */         throw new NoSuchElementException(); 
/* 1003 */       this.curr = this.prev = this.next;
/* 1004 */       this.index++;
/* 1005 */       updateNext();
/* 1006 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1009 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Short2ReferenceRBTreeMap.Entry<V> previousEntry() {
/* 1012 */       if (!hasPrevious())
/* 1013 */         throw new NoSuchElementException(); 
/* 1014 */       this.curr = this.next = this.prev;
/* 1015 */       this.index--;
/* 1016 */       updatePrevious();
/* 1017 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1020 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1023 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1026 */       if (this.curr == null) {
/* 1027 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1032 */       if (this.curr == this.prev)
/* 1033 */         this.index--; 
/* 1034 */       this.next = this.prev = this.curr;
/* 1035 */       updatePrevious();
/* 1036 */       updateNext();
/* 1037 */       Short2ReferenceRBTreeMap.this.remove(this.curr.key);
/* 1038 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1041 */       int i = n;
/* 1042 */       while (i-- != 0 && hasNext())
/* 1043 */         nextEntry(); 
/* 1044 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1047 */       int i = n;
/* 1048 */       while (i-- != 0 && hasPrevious())
/* 1049 */         previousEntry(); 
/* 1050 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Short2ReferenceMap.Entry<V>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(short k) {
/* 1063 */       super(k);
/*      */     }
/*      */     
/*      */     public Short2ReferenceMap.Entry<V> next() {
/* 1067 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Short2ReferenceMap.Entry<V> previous() {
/* 1071 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Short2ReferenceMap.Entry<V>> short2ReferenceEntrySet() {
/* 1076 */     if (this.entries == null)
/* 1077 */       this.entries = (ObjectSortedSet<Short2ReferenceMap.Entry<V>>)new AbstractObjectSortedSet<Short2ReferenceMap.Entry<V>>()
/*      */         {
/*      */           final Comparator<? super Short2ReferenceMap.Entry<V>> comparator;
/*      */ 
/*      */           
/*      */           public Comparator<? super Short2ReferenceMap.Entry<V>> comparator() {
/* 1083 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Short2ReferenceMap.Entry<V>> iterator() {
/* 1087 */             return (ObjectBidirectionalIterator<Short2ReferenceMap.Entry<V>>)new Short2ReferenceRBTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Short2ReferenceMap.Entry<V>> iterator(Short2ReferenceMap.Entry<V> from) {
/* 1092 */             return (ObjectBidirectionalIterator<Short2ReferenceMap.Entry<V>>)new Short2ReferenceRBTreeMap.EntryIterator(from.getShortKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1097 */             if (!(o instanceof Map.Entry))
/* 1098 */               return false; 
/* 1099 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1100 */             if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1101 */               return false; 
/* 1102 */             Short2ReferenceRBTreeMap.Entry<V> f = Short2ReferenceRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1103 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1108 */             if (!(o instanceof Map.Entry))
/* 1109 */               return false; 
/* 1110 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1111 */             if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1112 */               return false; 
/* 1113 */             Short2ReferenceRBTreeMap.Entry<V> f = Short2ReferenceRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1114 */             if (f == null || f.getValue() != e.getValue())
/* 1115 */               return false; 
/* 1116 */             Short2ReferenceRBTreeMap.this.remove(f.key);
/* 1117 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1121 */             return Short2ReferenceRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1125 */             Short2ReferenceRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Short2ReferenceMap.Entry<V> first() {
/* 1129 */             return Short2ReferenceRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Short2ReferenceMap.Entry<V> last() {
/* 1133 */             return Short2ReferenceRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Short2ReferenceMap.Entry<V>> subSet(Short2ReferenceMap.Entry<V> from, Short2ReferenceMap.Entry<V> to) {
/* 1138 */             return Short2ReferenceRBTreeMap.this.subMap(from.getShortKey(), to.getShortKey()).short2ReferenceEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Short2ReferenceMap.Entry<V>> headSet(Short2ReferenceMap.Entry<V> to) {
/* 1142 */             return Short2ReferenceRBTreeMap.this.headMap(to.getShortKey()).short2ReferenceEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Short2ReferenceMap.Entry<V>> tailSet(Short2ReferenceMap.Entry<V> from) {
/* 1146 */             return Short2ReferenceRBTreeMap.this.tailMap(from.getShortKey()).short2ReferenceEntrySet();
/*      */           }
/*      */         }; 
/* 1149 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends TreeIterator
/*      */     implements ShortListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(short k) {
/* 1165 */       super(k);
/*      */     }
/*      */     
/*      */     public short nextShort() {
/* 1169 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public short previousShort() {
/* 1173 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractShort2ReferenceSortedMap<V>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ShortBidirectionalIterator iterator() {
/* 1180 */       return new Short2ReferenceRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public ShortBidirectionalIterator iterator(short from) {
/* 1184 */       return new Short2ReferenceRBTreeMap.KeyIterator(from);
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
/*      */   public ShortSortedSet keySet() {
/* 1199 */     if (this.keys == null)
/* 1200 */       this.keys = new KeySet(); 
/* 1201 */     return this.keys;
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
/* 1216 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public V previous() {
/* 1220 */       return (previousEntry()).value;
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
/*      */   public ReferenceCollection<V> values() {
/* 1235 */     if (this.values == null)
/* 1236 */       this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1239 */             return (ObjectIterator<V>)new Short2ReferenceRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(Object k) {
/* 1243 */             return Short2ReferenceRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1247 */             return Short2ReferenceRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1251 */             Short2ReferenceRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1254 */     return this.values;
/*      */   }
/*      */   
/*      */   public ShortComparator comparator() {
/* 1258 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Short2ReferenceSortedMap<V> headMap(short to) {
/* 1262 */     return new Submap((short)0, true, to, false);
/*      */   }
/*      */   
/*      */   public Short2ReferenceSortedMap<V> tailMap(short from) {
/* 1266 */     return new Submap(from, false, (short)0, true);
/*      */   }
/*      */   
/*      */   public Short2ReferenceSortedMap<V> subMap(short from, short to) {
/* 1270 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractShort2ReferenceSortedMap<V>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     short from;
/*      */ 
/*      */ 
/*      */     
/*      */     short to;
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
/*      */     protected transient ObjectSortedSet<Short2ReferenceMap.Entry<V>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ShortSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ReferenceCollection<V> values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(short from, boolean bottom, short to, boolean top) {
/* 1314 */       if (!bottom && !top && Short2ReferenceRBTreeMap.this.compare(from, to) > 0)
/* 1315 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1316 */       this.from = from;
/* 1317 */       this.bottom = bottom;
/* 1318 */       this.to = to;
/* 1319 */       this.top = top;
/* 1320 */       this.defRetValue = Short2ReferenceRBTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1324 */       SubmapIterator i = new SubmapIterator();
/* 1325 */       while (i.hasNext()) {
/* 1326 */         i.nextEntry();
/* 1327 */         i.remove();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean in(short k) {
/* 1338 */       return ((this.bottom || Short2ReferenceRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Short2ReferenceRBTreeMap.this
/* 1339 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Short2ReferenceMap.Entry<V>> short2ReferenceEntrySet() {
/* 1343 */       if (this.entries == null)
/* 1344 */         this.entries = (ObjectSortedSet<Short2ReferenceMap.Entry<V>>)new AbstractObjectSortedSet<Short2ReferenceMap.Entry<V>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Short2ReferenceMap.Entry<V>> iterator() {
/* 1347 */               return (ObjectBidirectionalIterator<Short2ReferenceMap.Entry<V>>)new Short2ReferenceRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Short2ReferenceMap.Entry<V>> iterator(Short2ReferenceMap.Entry<V> from) {
/* 1352 */               return (ObjectBidirectionalIterator<Short2ReferenceMap.Entry<V>>)new Short2ReferenceRBTreeMap.Submap.SubmapEntryIterator(from.getShortKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Short2ReferenceMap.Entry<V>> comparator() {
/* 1356 */               return Short2ReferenceRBTreeMap.this.short2ReferenceEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1361 */               if (!(o instanceof Map.Entry))
/* 1362 */                 return false; 
/* 1363 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1364 */               if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1365 */                 return false; 
/* 1366 */               Short2ReferenceRBTreeMap.Entry<V> f = Short2ReferenceRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1367 */               return (f != null && Short2ReferenceRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1372 */               if (!(o instanceof Map.Entry))
/* 1373 */                 return false; 
/* 1374 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1375 */               if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1376 */                 return false; 
/* 1377 */               Short2ReferenceRBTreeMap.Entry<V> f = Short2ReferenceRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1378 */               if (f != null && Short2ReferenceRBTreeMap.Submap.this.in(f.key))
/* 1379 */                 Short2ReferenceRBTreeMap.Submap.this.remove(f.key); 
/* 1380 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1384 */               int c = 0;
/* 1385 */               for (ObjectBidirectionalIterator<Short2ReferenceMap.Entry<V>> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1386 */                 c++; 
/* 1387 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1391 */               return !(new Short2ReferenceRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1395 */               Short2ReferenceRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Short2ReferenceMap.Entry<V> first() {
/* 1399 */               return Short2ReferenceRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Short2ReferenceMap.Entry<V> last() {
/* 1403 */               return Short2ReferenceRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Short2ReferenceMap.Entry<V>> subSet(Short2ReferenceMap.Entry<V> from, Short2ReferenceMap.Entry<V> to) {
/* 1408 */               return Short2ReferenceRBTreeMap.Submap.this.subMap(from.getShortKey(), to.getShortKey()).short2ReferenceEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Short2ReferenceMap.Entry<V>> headSet(Short2ReferenceMap.Entry<V> to) {
/* 1412 */               return Short2ReferenceRBTreeMap.Submap.this.headMap(to.getShortKey()).short2ReferenceEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Short2ReferenceMap.Entry<V>> tailSet(Short2ReferenceMap.Entry<V> from) {
/* 1416 */               return Short2ReferenceRBTreeMap.Submap.this.tailMap(from.getShortKey()).short2ReferenceEntrySet();
/*      */             }
/*      */           }; 
/* 1419 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractShort2ReferenceSortedMap<V>.KeySet {
/*      */       public ShortBidirectionalIterator iterator() {
/* 1424 */         return new Short2ReferenceRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public ShortBidirectionalIterator iterator(short from) {
/* 1428 */         return new Short2ReferenceRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public ShortSortedSet keySet() {
/* 1433 */       if (this.keys == null)
/* 1434 */         this.keys = new KeySet(); 
/* 1435 */       return this.keys;
/*      */     }
/*      */     
/*      */     public ReferenceCollection<V> values() {
/* 1439 */       if (this.values == null)
/* 1440 */         this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */           {
/*      */             public ObjectIterator<V> iterator() {
/* 1443 */               return (ObjectIterator<V>)new Short2ReferenceRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(Object k) {
/* 1447 */               return Short2ReferenceRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1451 */               return Short2ReferenceRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1455 */               Short2ReferenceRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1458 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(short k) {
/* 1463 */       return (in(k) && Short2ReferenceRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(Object v) {
/* 1467 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1469 */       while (i.hasNext()) {
/* 1470 */         Object ev = (i.nextEntry()).value;
/* 1471 */         if (ev == v)
/* 1472 */           return true; 
/*      */       } 
/* 1474 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(short k) {
/* 1480 */       short kk = k; Short2ReferenceRBTreeMap.Entry<V> e;
/* 1481 */       return (in(kk) && (e = Short2ReferenceRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public V put(short k, V v) {
/* 1485 */       Short2ReferenceRBTreeMap.this.modified = false;
/* 1486 */       if (!in(k))
/* 1487 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1488 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1489 */       V oldValue = Short2ReferenceRBTreeMap.this.put(k, v);
/* 1490 */       return Short2ReferenceRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public V remove(short k) {
/* 1495 */       Short2ReferenceRBTreeMap.this.modified = false;
/* 1496 */       if (!in(k))
/* 1497 */         return this.defRetValue; 
/* 1498 */       V oldValue = Short2ReferenceRBTreeMap.this.remove(k);
/* 1499 */       return Short2ReferenceRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1503 */       SubmapIterator i = new SubmapIterator();
/* 1504 */       int n = 0;
/* 1505 */       while (i.hasNext()) {
/* 1506 */         n++;
/* 1507 */         i.nextEntry();
/*      */       } 
/* 1509 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1513 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public ShortComparator comparator() {
/* 1517 */       return Short2ReferenceRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Short2ReferenceSortedMap<V> headMap(short to) {
/* 1521 */       if (this.top)
/* 1522 */         return new Submap(this.from, this.bottom, to, false); 
/* 1523 */       return (Short2ReferenceRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Short2ReferenceSortedMap<V> tailMap(short from) {
/* 1527 */       if (this.bottom)
/* 1528 */         return new Submap(from, false, this.to, this.top); 
/* 1529 */       return (Short2ReferenceRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Short2ReferenceSortedMap<V> subMap(short from, short to) {
/* 1533 */       if (this.top && this.bottom)
/* 1534 */         return new Submap(from, false, to, false); 
/* 1535 */       if (!this.top)
/* 1536 */         to = (Short2ReferenceRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1537 */       if (!this.bottom)
/* 1538 */         from = (Short2ReferenceRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1539 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1540 */         return this; 
/* 1541 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Short2ReferenceRBTreeMap.Entry<V> firstEntry() {
/*      */       Short2ReferenceRBTreeMap.Entry<V> e;
/* 1550 */       if (Short2ReferenceRBTreeMap.this.tree == null) {
/* 1551 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1555 */       if (this.bottom) {
/* 1556 */         e = Short2ReferenceRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1558 */         e = Short2ReferenceRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1560 */         if (Short2ReferenceRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1561 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1565 */       if (e == null || (!this.top && Short2ReferenceRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1566 */         return null; 
/* 1567 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Short2ReferenceRBTreeMap.Entry<V> lastEntry() {
/*      */       Short2ReferenceRBTreeMap.Entry<V> e;
/* 1576 */       if (Short2ReferenceRBTreeMap.this.tree == null) {
/* 1577 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1581 */       if (this.top) {
/* 1582 */         e = Short2ReferenceRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1584 */         e = Short2ReferenceRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1586 */         if (Short2ReferenceRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1587 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1591 */       if (e == null || (!this.bottom && Short2ReferenceRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1592 */         return null; 
/* 1593 */       return e;
/*      */     }
/*      */     
/*      */     public short firstShortKey() {
/* 1597 */       Short2ReferenceRBTreeMap.Entry<V> e = firstEntry();
/* 1598 */       if (e == null)
/* 1599 */         throw new NoSuchElementException(); 
/* 1600 */       return e.key;
/*      */     }
/*      */     
/*      */     public short lastShortKey() {
/* 1604 */       Short2ReferenceRBTreeMap.Entry<V> e = lastEntry();
/* 1605 */       if (e == null)
/* 1606 */         throw new NoSuchElementException(); 
/* 1607 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Short2ReferenceRBTreeMap<V>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1620 */         this.next = Short2ReferenceRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(short k) {
/* 1623 */         this();
/* 1624 */         if (this.next != null)
/* 1625 */           if (!Short2ReferenceRBTreeMap.Submap.this.bottom && Short2ReferenceRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1626 */             this.prev = null;
/* 1627 */           } else if (!Short2ReferenceRBTreeMap.Submap.this.top && Short2ReferenceRBTreeMap.this.compare(k, (this.prev = Short2ReferenceRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1628 */             this.next = null;
/*      */           } else {
/* 1630 */             this.next = Short2ReferenceRBTreeMap.this.locateKey(k);
/* 1631 */             if (Short2ReferenceRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1632 */               this.prev = this.next;
/* 1633 */               this.next = this.next.next();
/*      */             } else {
/* 1635 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1641 */         this.prev = this.prev.prev();
/* 1642 */         if (!Short2ReferenceRBTreeMap.Submap.this.bottom && this.prev != null && Short2ReferenceRBTreeMap.this.compare(this.prev.key, Short2ReferenceRBTreeMap.Submap.this.from) < 0)
/* 1643 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1647 */         this.next = this.next.next();
/* 1648 */         if (!Short2ReferenceRBTreeMap.Submap.this.top && this.next != null && Short2ReferenceRBTreeMap.this.compare(this.next.key, Short2ReferenceRBTreeMap.Submap.this.to) >= 0)
/* 1649 */           this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Short2ReferenceMap.Entry<V>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(short k) {
/* 1658 */         super(k);
/*      */       }
/*      */       
/*      */       public Short2ReferenceMap.Entry<V> next() {
/* 1662 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Short2ReferenceMap.Entry<V> previous() {
/* 1666 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements ShortListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(short from) {
/* 1684 */         super(from);
/*      */       }
/*      */       
/*      */       public short nextShort() {
/* 1688 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public short previousShort() {
/* 1692 */         return (previousEntry()).key;
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
/* 1708 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public V previous() {
/* 1712 */         return (previousEntry()).value;
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
/*      */   public Short2ReferenceRBTreeMap<V> clone() {
/*      */     Short2ReferenceRBTreeMap<V> c;
/*      */     try {
/* 1731 */       c = (Short2ReferenceRBTreeMap<V>)super.clone();
/* 1732 */     } catch (CloneNotSupportedException cantHappen) {
/* 1733 */       throw new InternalError();
/*      */     } 
/* 1735 */     c.keys = null;
/* 1736 */     c.values = null;
/* 1737 */     c.entries = null;
/* 1738 */     c.allocatePaths();
/* 1739 */     if (this.count != 0) {
/*      */       
/* 1741 */       Entry<V> rp = new Entry<>(), rq = new Entry<>();
/* 1742 */       Entry<V> p = rp;
/* 1743 */       rp.left(this.tree);
/* 1744 */       Entry<V> q = rq;
/* 1745 */       rq.pred((Entry<V>)null);
/*      */       while (true) {
/* 1747 */         if (!p.pred()) {
/* 1748 */           Entry<V> e = p.left.clone();
/* 1749 */           e.pred(q.left);
/* 1750 */           e.succ(q);
/* 1751 */           q.left(e);
/* 1752 */           p = p.left;
/* 1753 */           q = q.left;
/*      */         } else {
/* 1755 */           while (p.succ()) {
/* 1756 */             p = p.right;
/* 1757 */             if (p == null) {
/* 1758 */               q.right = null;
/* 1759 */               c.tree = rq.left;
/* 1760 */               c.firstEntry = c.tree;
/* 1761 */               while (c.firstEntry.left != null)
/* 1762 */                 c.firstEntry = c.firstEntry.left; 
/* 1763 */               c.lastEntry = c.tree;
/* 1764 */               while (c.lastEntry.right != null)
/* 1765 */                 c.lastEntry = c.lastEntry.right; 
/* 1766 */               return c;
/*      */             } 
/* 1768 */             q = q.right;
/*      */           } 
/* 1770 */           p = p.right;
/* 1771 */           q = q.right;
/*      */         } 
/* 1773 */         if (!p.succ()) {
/* 1774 */           Entry<V> e = p.right.clone();
/* 1775 */           e.succ(q.right);
/* 1776 */           e.pred(q);
/* 1777 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1781 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1784 */     int n = this.count;
/* 1785 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1787 */     s.defaultWriteObject();
/* 1788 */     while (n-- != 0) {
/* 1789 */       Entry<V> e = i.nextEntry();
/* 1790 */       s.writeShort(e.key);
/* 1791 */       s.writeObject(e.value);
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
/* 1812 */     if (n == 1) {
/* 1813 */       Entry<V> entry = new Entry<>(s.readShort(), (V)s.readObject());
/* 1814 */       entry.pred(pred);
/* 1815 */       entry.succ(succ);
/* 1816 */       entry.black(true);
/* 1817 */       return entry;
/*      */     } 
/* 1819 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1824 */       Entry<V> entry = new Entry<>(s.readShort(), (V)s.readObject());
/* 1825 */       entry.black(true);
/* 1826 */       entry.right(new Entry<>(s.readShort(), (V)s.readObject()));
/* 1827 */       entry.right.pred(entry);
/* 1828 */       entry.pred(pred);
/* 1829 */       entry.right.succ(succ);
/* 1830 */       return entry;
/*      */     } 
/*      */     
/* 1833 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1834 */     Entry<V> top = new Entry<>();
/* 1835 */     top.left(readTree(s, leftN, pred, top));
/* 1836 */     top.key = s.readShort();
/* 1837 */     top.value = (V)s.readObject();
/* 1838 */     top.black(true);
/* 1839 */     top.right(readTree(s, rightN, top, succ));
/* 1840 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1841 */       top.right.black(false); 
/* 1842 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1845 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1850 */     setActualComparator();
/* 1851 */     allocatePaths();
/* 1852 */     if (this.count != 0) {
/* 1853 */       this.tree = readTree(s, this.count, (Entry<V>)null, (Entry<V>)null);
/*      */       
/* 1855 */       Entry<V> e = this.tree;
/* 1856 */       while (e.left() != null)
/* 1857 */         e = e.left(); 
/* 1858 */       this.firstEntry = e;
/* 1859 */       e = this.tree;
/* 1860 */       while (e.right() != null)
/* 1861 */         e = e.right(); 
/* 1862 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2ReferenceRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */