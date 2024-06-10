/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
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
/*      */ public class Double2ReferenceRBTreeMap<V>
/*      */   extends AbstractDouble2ReferenceSortedMap<V>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<V> tree;
/*      */   protected int count;
/*      */   protected transient Entry<V> firstEntry;
/*      */   protected transient Entry<V> lastEntry;
/*      */   protected transient ObjectSortedSet<Double2ReferenceMap.Entry<V>> entries;
/*      */   protected transient DoubleSortedSet keys;
/*      */   protected transient ReferenceCollection<V> values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Double> storedComparator;
/*      */   protected transient DoubleComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry<V>[] nodePath;
/*      */   
/*      */   public Double2ReferenceRBTreeMap() {
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
/*   93 */     this.actualComparator = DoubleComparators.asDoubleComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ReferenceRBTreeMap(Comparator<? super Double> c) {
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
/*      */   public Double2ReferenceRBTreeMap(Map<? extends Double, ? extends V> m) {
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
/*      */   public Double2ReferenceRBTreeMap(SortedMap<Double, V> m) {
/*  124 */     this(m.comparator());
/*  125 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ReferenceRBTreeMap(Double2ReferenceMap<? extends V> m) {
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
/*      */   public Double2ReferenceRBTreeMap(Double2ReferenceSortedMap<V> m) {
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
/*      */   public Double2ReferenceRBTreeMap(double[] k, V[] v, Comparator<? super Double> c) {
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
/*      */   public Double2ReferenceRBTreeMap(double[] k, V[] v) {
/*  180 */     this(k, v, (Comparator<? super Double>)null);
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
/*      */   final int compare(double k1, double k2) {
/*  208 */     return (this.actualComparator == null) ? Double.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry<V> findKey(double k) {
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
/*      */   final Entry<V> locateKey(double k) {
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
/*      */   public V put(double k, V v) {
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
/*      */   private Entry<V> add(double k) {
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
/*      */   public V remove(double k) {
/*  421 */     this.modified = false;
/*  422 */     if (this.tree == null)
/*  423 */       return this.defRetValue; 
/*  424 */     Entry<V> p = this.tree;
/*      */     
/*  426 */     int i = 0;
/*  427 */     double kk = k;
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
/*      */     extends AbstractDouble2ReferenceMap.BasicEntry<V>
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
/*  712 */       super(0.0D, (V)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(double k, V v) {
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
/*  891 */       Map.Entry<Double, V> e = (Map.Entry<Double, V>)o;
/*  892 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && this.value == e
/*  893 */         .getValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  897 */       return HashCommon.double2int(this.key) ^ (
/*  898 */         (this.value == null) ? 0 : System.identityHashCode(this.value));
/*      */     }
/*      */     
/*      */     public String toString() {
/*  902 */       return this.key + "=>" + this.value;
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
/*      */   public boolean containsKey(double k) {
/*  923 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  927 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  931 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(double k) {
/*  936 */     Entry<V> e = findKey(k);
/*  937 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public double firstDoubleKey() {
/*  941 */     if (this.tree == null)
/*  942 */       throw new NoSuchElementException(); 
/*  943 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public double lastDoubleKey() {
/*  947 */     if (this.tree == null)
/*  948 */       throw new NoSuchElementException(); 
/*  949 */     return this.lastEntry.key;
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
/*      */     Double2ReferenceRBTreeMap.Entry<V> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2ReferenceRBTreeMap.Entry<V> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2ReferenceRBTreeMap.Entry<V> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  980 */     int index = 0;
/*      */     TreeIterator() {
/*  982 */       this.next = Double2ReferenceRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(double k) {
/*  985 */       if ((this.next = Double2ReferenceRBTreeMap.this.locateKey(k)) != null)
/*  986 */         if (Double2ReferenceRBTreeMap.this.compare(this.next.key, k) <= 0) {
/*  987 */           this.prev = this.next;
/*  988 */           this.next = this.next.next();
/*      */         } else {
/*  990 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/*  994 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/*  997 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1000 */       this.next = this.next.next();
/*      */     }
/*      */     Double2ReferenceRBTreeMap.Entry<V> nextEntry() {
/* 1003 */       if (!hasNext())
/* 1004 */         throw new NoSuchElementException(); 
/* 1005 */       this.curr = this.prev = this.next;
/* 1006 */       this.index++;
/* 1007 */       updateNext();
/* 1008 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1011 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Double2ReferenceRBTreeMap.Entry<V> previousEntry() {
/* 1014 */       if (!hasPrevious())
/* 1015 */         throw new NoSuchElementException(); 
/* 1016 */       this.curr = this.next = this.prev;
/* 1017 */       this.index--;
/* 1018 */       updatePrevious();
/* 1019 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1022 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1025 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1028 */       if (this.curr == null) {
/* 1029 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1034 */       if (this.curr == this.prev)
/* 1035 */         this.index--; 
/* 1036 */       this.next = this.prev = this.curr;
/* 1037 */       updatePrevious();
/* 1038 */       updateNext();
/* 1039 */       Double2ReferenceRBTreeMap.this.remove(this.curr.key);
/* 1040 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1043 */       int i = n;
/* 1044 */       while (i-- != 0 && hasNext())
/* 1045 */         nextEntry(); 
/* 1046 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1049 */       int i = n;
/* 1050 */       while (i-- != 0 && hasPrevious())
/* 1051 */         previousEntry(); 
/* 1052 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Double2ReferenceMap.Entry<V>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(double k) {
/* 1065 */       super(k);
/*      */     }
/*      */     
/*      */     public Double2ReferenceMap.Entry<V> next() {
/* 1069 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Double2ReferenceMap.Entry<V> previous() {
/* 1073 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
/* 1078 */     if (this.entries == null)
/* 1079 */       this.entries = (ObjectSortedSet<Double2ReferenceMap.Entry<V>>)new AbstractObjectSortedSet<Double2ReferenceMap.Entry<V>>()
/*      */         {
/*      */           final Comparator<? super Double2ReferenceMap.Entry<V>> comparator;
/*      */ 
/*      */           
/*      */           public Comparator<? super Double2ReferenceMap.Entry<V>> comparator() {
/* 1085 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> iterator() {
/* 1089 */             return (ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>>)new Double2ReferenceRBTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> iterator(Double2ReferenceMap.Entry<V> from) {
/* 1094 */             return (ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>>)new Double2ReferenceRBTreeMap.EntryIterator(from.getDoubleKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1099 */             if (!(o instanceof Map.Entry))
/* 1100 */               return false; 
/* 1101 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1102 */             if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1103 */               return false; 
/* 1104 */             Double2ReferenceRBTreeMap.Entry<V> f = Double2ReferenceRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1105 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1110 */             if (!(o instanceof Map.Entry))
/* 1111 */               return false; 
/* 1112 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1113 */             if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1114 */               return false; 
/* 1115 */             Double2ReferenceRBTreeMap.Entry<V> f = Double2ReferenceRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1116 */             if (f == null || f.getValue() != e.getValue())
/* 1117 */               return false; 
/* 1118 */             Double2ReferenceRBTreeMap.this.remove(f.key);
/* 1119 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1123 */             return Double2ReferenceRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1127 */             Double2ReferenceRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Double2ReferenceMap.Entry<V> first() {
/* 1131 */             return Double2ReferenceRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Double2ReferenceMap.Entry<V> last() {
/* 1135 */             return Double2ReferenceRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Double2ReferenceMap.Entry<V>> subSet(Double2ReferenceMap.Entry<V> from, Double2ReferenceMap.Entry<V> to) {
/* 1140 */             return Double2ReferenceRBTreeMap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2ReferenceEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Double2ReferenceMap.Entry<V>> headSet(Double2ReferenceMap.Entry<V> to) {
/* 1144 */             return Double2ReferenceRBTreeMap.this.headMap(to.getDoubleKey()).double2ReferenceEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Double2ReferenceMap.Entry<V>> tailSet(Double2ReferenceMap.Entry<V> from) {
/* 1148 */             return Double2ReferenceRBTreeMap.this.tailMap(from.getDoubleKey()).double2ReferenceEntrySet();
/*      */           }
/*      */         }; 
/* 1151 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends TreeIterator
/*      */     implements DoubleListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(double k) {
/* 1167 */       super(k);
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/* 1171 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1175 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractDouble2ReferenceSortedMap<V>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public DoubleBidirectionalIterator iterator() {
/* 1182 */       return new Double2ReferenceRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public DoubleBidirectionalIterator iterator(double from) {
/* 1186 */       return new Double2ReferenceRBTreeMap.KeyIterator(from);
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
/*      */   public DoubleSortedSet keySet() {
/* 1201 */     if (this.keys == null)
/* 1202 */       this.keys = new KeySet(); 
/* 1203 */     return this.keys;
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
/* 1218 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public V previous() {
/* 1222 */       return (previousEntry()).value;
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
/* 1237 */     if (this.values == null)
/* 1238 */       this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1241 */             return (ObjectIterator<V>)new Double2ReferenceRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(Object k) {
/* 1245 */             return Double2ReferenceRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1249 */             return Double2ReferenceRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1253 */             Double2ReferenceRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1256 */     return this.values;
/*      */   }
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1260 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Double2ReferenceSortedMap<V> headMap(double to) {
/* 1264 */     return new Submap(0.0D, true, to, false);
/*      */   }
/*      */   
/*      */   public Double2ReferenceSortedMap<V> tailMap(double from) {
/* 1268 */     return new Submap(from, false, 0.0D, true);
/*      */   }
/*      */   
/*      */   public Double2ReferenceSortedMap<V> subMap(double from, double to) {
/* 1272 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractDouble2ReferenceSortedMap<V>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     double from;
/*      */ 
/*      */ 
/*      */     
/*      */     double to;
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
/*      */     protected transient ObjectSortedSet<Double2ReferenceMap.Entry<V>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient DoubleSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ReferenceCollection<V> values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(double from, boolean bottom, double to, boolean top) {
/* 1316 */       if (!bottom && !top && Double2ReferenceRBTreeMap.this.compare(from, to) > 0)
/* 1317 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1318 */       this.from = from;
/* 1319 */       this.bottom = bottom;
/* 1320 */       this.to = to;
/* 1321 */       this.top = top;
/* 1322 */       this.defRetValue = Double2ReferenceRBTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1326 */       SubmapIterator i = new SubmapIterator();
/* 1327 */       while (i.hasNext()) {
/* 1328 */         i.nextEntry();
/* 1329 */         i.remove();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean in(double k) {
/* 1340 */       return ((this.bottom || Double2ReferenceRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Double2ReferenceRBTreeMap.this
/* 1341 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
/* 1345 */       if (this.entries == null)
/* 1346 */         this.entries = (ObjectSortedSet<Double2ReferenceMap.Entry<V>>)new AbstractObjectSortedSet<Double2ReferenceMap.Entry<V>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> iterator() {
/* 1349 */               return (ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>>)new Double2ReferenceRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> iterator(Double2ReferenceMap.Entry<V> from) {
/* 1354 */               return (ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>>)new Double2ReferenceRBTreeMap.Submap.SubmapEntryIterator(from.getDoubleKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Double2ReferenceMap.Entry<V>> comparator() {
/* 1358 */               return Double2ReferenceRBTreeMap.this.double2ReferenceEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1363 */               if (!(o instanceof Map.Entry))
/* 1364 */                 return false; 
/* 1365 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1366 */               if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1367 */                 return false; 
/* 1368 */               Double2ReferenceRBTreeMap.Entry<V> f = Double2ReferenceRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1369 */               return (f != null && Double2ReferenceRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1374 */               if (!(o instanceof Map.Entry))
/* 1375 */                 return false; 
/* 1376 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1377 */               if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1378 */                 return false; 
/* 1379 */               Double2ReferenceRBTreeMap.Entry<V> f = Double2ReferenceRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1380 */               if (f != null && Double2ReferenceRBTreeMap.Submap.this.in(f.key))
/* 1381 */                 Double2ReferenceRBTreeMap.Submap.this.remove(f.key); 
/* 1382 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1386 */               int c = 0;
/* 1387 */               for (ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1388 */                 c++; 
/* 1389 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1393 */               return !(new Double2ReferenceRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1397 */               Double2ReferenceRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Double2ReferenceMap.Entry<V> first() {
/* 1401 */               return Double2ReferenceRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Double2ReferenceMap.Entry<V> last() {
/* 1405 */               return Double2ReferenceRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Double2ReferenceMap.Entry<V>> subSet(Double2ReferenceMap.Entry<V> from, Double2ReferenceMap.Entry<V> to) {
/* 1410 */               return Double2ReferenceRBTreeMap.Submap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2ReferenceEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Double2ReferenceMap.Entry<V>> headSet(Double2ReferenceMap.Entry<V> to) {
/* 1414 */               return Double2ReferenceRBTreeMap.Submap.this.headMap(to.getDoubleKey()).double2ReferenceEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Double2ReferenceMap.Entry<V>> tailSet(Double2ReferenceMap.Entry<V> from) {
/* 1418 */               return Double2ReferenceRBTreeMap.Submap.this.tailMap(from.getDoubleKey()).double2ReferenceEntrySet();
/*      */             }
/*      */           }; 
/* 1421 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractDouble2ReferenceSortedMap<V>.KeySet {
/*      */       public DoubleBidirectionalIterator iterator() {
/* 1426 */         return new Double2ReferenceRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public DoubleBidirectionalIterator iterator(double from) {
/* 1430 */         return new Double2ReferenceRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public DoubleSortedSet keySet() {
/* 1435 */       if (this.keys == null)
/* 1436 */         this.keys = new KeySet(); 
/* 1437 */       return this.keys;
/*      */     }
/*      */     
/*      */     public ReferenceCollection<V> values() {
/* 1441 */       if (this.values == null)
/* 1442 */         this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */           {
/*      */             public ObjectIterator<V> iterator() {
/* 1445 */               return (ObjectIterator<V>)new Double2ReferenceRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(Object k) {
/* 1449 */               return Double2ReferenceRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1453 */               return Double2ReferenceRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1457 */               Double2ReferenceRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1460 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(double k) {
/* 1465 */       return (in(k) && Double2ReferenceRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(Object v) {
/* 1469 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1471 */       while (i.hasNext()) {
/* 1472 */         Object ev = (i.nextEntry()).value;
/* 1473 */         if (ev == v)
/* 1474 */           return true; 
/*      */       } 
/* 1476 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(double k) {
/* 1482 */       double kk = k; Double2ReferenceRBTreeMap.Entry<V> e;
/* 1483 */       return (in(kk) && (e = Double2ReferenceRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public V put(double k, V v) {
/* 1487 */       Double2ReferenceRBTreeMap.this.modified = false;
/* 1488 */       if (!in(k))
/* 1489 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1490 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1491 */       V oldValue = Double2ReferenceRBTreeMap.this.put(k, v);
/* 1492 */       return Double2ReferenceRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public V remove(double k) {
/* 1497 */       Double2ReferenceRBTreeMap.this.modified = false;
/* 1498 */       if (!in(k))
/* 1499 */         return this.defRetValue; 
/* 1500 */       V oldValue = Double2ReferenceRBTreeMap.this.remove(k);
/* 1501 */       return Double2ReferenceRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1505 */       SubmapIterator i = new SubmapIterator();
/* 1506 */       int n = 0;
/* 1507 */       while (i.hasNext()) {
/* 1508 */         n++;
/* 1509 */         i.nextEntry();
/*      */       } 
/* 1511 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1515 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1519 */       return Double2ReferenceRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Double2ReferenceSortedMap<V> headMap(double to) {
/* 1523 */       if (this.top)
/* 1524 */         return new Submap(this.from, this.bottom, to, false); 
/* 1525 */       return (Double2ReferenceRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Double2ReferenceSortedMap<V> tailMap(double from) {
/* 1529 */       if (this.bottom)
/* 1530 */         return new Submap(from, false, this.to, this.top); 
/* 1531 */       return (Double2ReferenceRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Double2ReferenceSortedMap<V> subMap(double from, double to) {
/* 1535 */       if (this.top && this.bottom)
/* 1536 */         return new Submap(from, false, to, false); 
/* 1537 */       if (!this.top)
/* 1538 */         to = (Double2ReferenceRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1539 */       if (!this.bottom)
/* 1540 */         from = (Double2ReferenceRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1541 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1542 */         return this; 
/* 1543 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2ReferenceRBTreeMap.Entry<V> firstEntry() {
/*      */       Double2ReferenceRBTreeMap.Entry<V> e;
/* 1552 */       if (Double2ReferenceRBTreeMap.this.tree == null) {
/* 1553 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1557 */       if (this.bottom) {
/* 1558 */         e = Double2ReferenceRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1560 */         e = Double2ReferenceRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1562 */         if (Double2ReferenceRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1563 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1567 */       if (e == null || (!this.top && Double2ReferenceRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1568 */         return null; 
/* 1569 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2ReferenceRBTreeMap.Entry<V> lastEntry() {
/*      */       Double2ReferenceRBTreeMap.Entry<V> e;
/* 1578 */       if (Double2ReferenceRBTreeMap.this.tree == null) {
/* 1579 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1583 */       if (this.top) {
/* 1584 */         e = Double2ReferenceRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1586 */         e = Double2ReferenceRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1588 */         if (Double2ReferenceRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1589 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1593 */       if (e == null || (!this.bottom && Double2ReferenceRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1594 */         return null; 
/* 1595 */       return e;
/*      */     }
/*      */     
/*      */     public double firstDoubleKey() {
/* 1599 */       Double2ReferenceRBTreeMap.Entry<V> e = firstEntry();
/* 1600 */       if (e == null)
/* 1601 */         throw new NoSuchElementException(); 
/* 1602 */       return e.key;
/*      */     }
/*      */     
/*      */     public double lastDoubleKey() {
/* 1606 */       Double2ReferenceRBTreeMap.Entry<V> e = lastEntry();
/* 1607 */       if (e == null)
/* 1608 */         throw new NoSuchElementException(); 
/* 1609 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Double2ReferenceRBTreeMap<V>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1622 */         this.next = Double2ReferenceRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(double k) {
/* 1625 */         this();
/* 1626 */         if (this.next != null)
/* 1627 */           if (!Double2ReferenceRBTreeMap.Submap.this.bottom && Double2ReferenceRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1628 */             this.prev = null;
/* 1629 */           } else if (!Double2ReferenceRBTreeMap.Submap.this.top && Double2ReferenceRBTreeMap.this.compare(k, (this.prev = Double2ReferenceRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1630 */             this.next = null;
/*      */           } else {
/* 1632 */             this.next = Double2ReferenceRBTreeMap.this.locateKey(k);
/* 1633 */             if (Double2ReferenceRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1634 */               this.prev = this.next;
/* 1635 */               this.next = this.next.next();
/*      */             } else {
/* 1637 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1643 */         this.prev = this.prev.prev();
/* 1644 */         if (!Double2ReferenceRBTreeMap.Submap.this.bottom && this.prev != null && Double2ReferenceRBTreeMap.this.compare(this.prev.key, Double2ReferenceRBTreeMap.Submap.this.from) < 0)
/* 1645 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1649 */         this.next = this.next.next();
/* 1650 */         if (!Double2ReferenceRBTreeMap.Submap.this.top && this.next != null && Double2ReferenceRBTreeMap.this.compare(this.next.key, Double2ReferenceRBTreeMap.Submap.this.to) >= 0)
/* 1651 */           this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Double2ReferenceMap.Entry<V>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(double k) {
/* 1660 */         super(k);
/*      */       }
/*      */       
/*      */       public Double2ReferenceMap.Entry<V> next() {
/* 1664 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Double2ReferenceMap.Entry<V> previous() {
/* 1668 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements DoubleListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(double from) {
/* 1686 */         super(from);
/*      */       }
/*      */       
/*      */       public double nextDouble() {
/* 1690 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public double previousDouble() {
/* 1694 */         return (previousEntry()).key;
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
/* 1710 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public V previous() {
/* 1714 */         return (previousEntry()).value;
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
/*      */   public Double2ReferenceRBTreeMap<V> clone() {
/*      */     Double2ReferenceRBTreeMap<V> c;
/*      */     try {
/* 1733 */       c = (Double2ReferenceRBTreeMap<V>)super.clone();
/* 1734 */     } catch (CloneNotSupportedException cantHappen) {
/* 1735 */       throw new InternalError();
/*      */     } 
/* 1737 */     c.keys = null;
/* 1738 */     c.values = null;
/* 1739 */     c.entries = null;
/* 1740 */     c.allocatePaths();
/* 1741 */     if (this.count != 0) {
/*      */       
/* 1743 */       Entry<V> rp = new Entry<>(), rq = new Entry<>();
/* 1744 */       Entry<V> p = rp;
/* 1745 */       rp.left(this.tree);
/* 1746 */       Entry<V> q = rq;
/* 1747 */       rq.pred((Entry<V>)null);
/*      */       while (true) {
/* 1749 */         if (!p.pred()) {
/* 1750 */           Entry<V> e = p.left.clone();
/* 1751 */           e.pred(q.left);
/* 1752 */           e.succ(q);
/* 1753 */           q.left(e);
/* 1754 */           p = p.left;
/* 1755 */           q = q.left;
/*      */         } else {
/* 1757 */           while (p.succ()) {
/* 1758 */             p = p.right;
/* 1759 */             if (p == null) {
/* 1760 */               q.right = null;
/* 1761 */               c.tree = rq.left;
/* 1762 */               c.firstEntry = c.tree;
/* 1763 */               while (c.firstEntry.left != null)
/* 1764 */                 c.firstEntry = c.firstEntry.left; 
/* 1765 */               c.lastEntry = c.tree;
/* 1766 */               while (c.lastEntry.right != null)
/* 1767 */                 c.lastEntry = c.lastEntry.right; 
/* 1768 */               return c;
/*      */             } 
/* 1770 */             q = q.right;
/*      */           } 
/* 1772 */           p = p.right;
/* 1773 */           q = q.right;
/*      */         } 
/* 1775 */         if (!p.succ()) {
/* 1776 */           Entry<V> e = p.right.clone();
/* 1777 */           e.succ(q.right);
/* 1778 */           e.pred(q);
/* 1779 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1783 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1786 */     int n = this.count;
/* 1787 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1789 */     s.defaultWriteObject();
/* 1790 */     while (n-- != 0) {
/* 1791 */       Entry<V> e = i.nextEntry();
/* 1792 */       s.writeDouble(e.key);
/* 1793 */       s.writeObject(e.value);
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
/* 1814 */     if (n == 1) {
/* 1815 */       Entry<V> entry = new Entry<>(s.readDouble(), (V)s.readObject());
/* 1816 */       entry.pred(pred);
/* 1817 */       entry.succ(succ);
/* 1818 */       entry.black(true);
/* 1819 */       return entry;
/*      */     } 
/* 1821 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1826 */       Entry<V> entry = new Entry<>(s.readDouble(), (V)s.readObject());
/* 1827 */       entry.black(true);
/* 1828 */       entry.right(new Entry<>(s.readDouble(), (V)s.readObject()));
/* 1829 */       entry.right.pred(entry);
/* 1830 */       entry.pred(pred);
/* 1831 */       entry.right.succ(succ);
/* 1832 */       return entry;
/*      */     } 
/*      */     
/* 1835 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1836 */     Entry<V> top = new Entry<>();
/* 1837 */     top.left(readTree(s, leftN, pred, top));
/* 1838 */     top.key = s.readDouble();
/* 1839 */     top.value = (V)s.readObject();
/* 1840 */     top.black(true);
/* 1841 */     top.right(readTree(s, rightN, top, succ));
/* 1842 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1843 */       top.right.black(false); 
/* 1844 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1847 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1852 */     setActualComparator();
/* 1853 */     allocatePaths();
/* 1854 */     if (this.count != 0) {
/* 1855 */       this.tree = readTree(s, this.count, (Entry<V>)null, (Entry<V>)null);
/*      */       
/* 1857 */       Entry<V> e = this.tree;
/* 1858 */       while (e.left() != null)
/* 1859 */         e = e.left(); 
/* 1860 */       this.firstEntry = e;
/* 1861 */       e = this.tree;
/* 1862 */       while (e.right() != null)
/* 1863 */         e = e.right(); 
/* 1864 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2ReferenceRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */