/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
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
/*      */ 
/*      */ 
/*      */ public class Double2ObjectRBTreeMap<V>
/*      */   extends AbstractDouble2ObjectSortedMap<V>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<V> tree;
/*      */   protected int count;
/*      */   protected transient Entry<V> firstEntry;
/*      */   protected transient Entry<V> lastEntry;
/*      */   protected transient ObjectSortedSet<Double2ObjectMap.Entry<V>> entries;
/*      */   protected transient DoubleSortedSet keys;
/*      */   protected transient ObjectCollection<V> values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Double> storedComparator;
/*      */   protected transient DoubleComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry<V>[] nodePath;
/*      */   
/*      */   public Double2ObjectRBTreeMap() {
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
/*      */   public Double2ObjectRBTreeMap(Comparator<? super Double> c) {
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
/*      */   public Double2ObjectRBTreeMap(Map<? extends Double, ? extends V> m) {
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
/*      */   public Double2ObjectRBTreeMap(SortedMap<Double, V> m) {
/*  124 */     this(m.comparator());
/*  125 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ObjectRBTreeMap(Double2ObjectMap<? extends V> m) {
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
/*      */   public Double2ObjectRBTreeMap(Double2ObjectSortedMap<V> m) {
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
/*      */   public Double2ObjectRBTreeMap(double[] k, V[] v, Comparator<? super Double> c) {
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
/*      */   public Double2ObjectRBTreeMap(double[] k, V[] v) {
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
/*  672 */       if (Objects.equals(ev, v))
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
/*      */     extends AbstractDouble2ObjectMap.BasicEntry<V>
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
/*  892 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && 
/*  893 */         Objects.equals(this.value, e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  897 */       return HashCommon.double2int(this.key) ^ ((this.value == null) ? 0 : this.value.hashCode());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  901 */       return this.key + "=>" + this.value;
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
/*  922 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  926 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  930 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(double k) {
/*  935 */     Entry<V> e = findKey(k);
/*  936 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public double firstDoubleKey() {
/*  940 */     if (this.tree == null)
/*  941 */       throw new NoSuchElementException(); 
/*  942 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public double lastDoubleKey() {
/*  946 */     if (this.tree == null)
/*  947 */       throw new NoSuchElementException(); 
/*  948 */     return this.lastEntry.key;
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
/*      */     Double2ObjectRBTreeMap.Entry<V> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2ObjectRBTreeMap.Entry<V> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2ObjectRBTreeMap.Entry<V> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  979 */     int index = 0;
/*      */     TreeIterator() {
/*  981 */       this.next = Double2ObjectRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(double k) {
/*  984 */       if ((this.next = Double2ObjectRBTreeMap.this.locateKey(k)) != null)
/*  985 */         if (Double2ObjectRBTreeMap.this.compare(this.next.key, k) <= 0) {
/*  986 */           this.prev = this.next;
/*  987 */           this.next = this.next.next();
/*      */         } else {
/*  989 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/*  993 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/*  996 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/*  999 */       this.next = this.next.next();
/*      */     }
/*      */     Double2ObjectRBTreeMap.Entry<V> nextEntry() {
/* 1002 */       if (!hasNext())
/* 1003 */         throw new NoSuchElementException(); 
/* 1004 */       this.curr = this.prev = this.next;
/* 1005 */       this.index++;
/* 1006 */       updateNext();
/* 1007 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1010 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Double2ObjectRBTreeMap.Entry<V> previousEntry() {
/* 1013 */       if (!hasPrevious())
/* 1014 */         throw new NoSuchElementException(); 
/* 1015 */       this.curr = this.next = this.prev;
/* 1016 */       this.index--;
/* 1017 */       updatePrevious();
/* 1018 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1021 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1024 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1027 */       if (this.curr == null) {
/* 1028 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1033 */       if (this.curr == this.prev)
/* 1034 */         this.index--; 
/* 1035 */       this.next = this.prev = this.curr;
/* 1036 */       updatePrevious();
/* 1037 */       updateNext();
/* 1038 */       Double2ObjectRBTreeMap.this.remove(this.curr.key);
/* 1039 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1042 */       int i = n;
/* 1043 */       while (i-- != 0 && hasNext())
/* 1044 */         nextEntry(); 
/* 1045 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1048 */       int i = n;
/* 1049 */       while (i-- != 0 && hasPrevious())
/* 1050 */         previousEntry(); 
/* 1051 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Double2ObjectMap.Entry<V>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(double k) {
/* 1064 */       super(k);
/*      */     }
/*      */     
/*      */     public Double2ObjectMap.Entry<V> next() {
/* 1068 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Double2ObjectMap.Entry<V> previous() {
/* 1072 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
/* 1077 */     if (this.entries == null)
/* 1078 */       this.entries = (ObjectSortedSet<Double2ObjectMap.Entry<V>>)new AbstractObjectSortedSet<Double2ObjectMap.Entry<V>>()
/*      */         {
/*      */           final Comparator<? super Double2ObjectMap.Entry<V>> comparator;
/*      */ 
/*      */           
/*      */           public Comparator<? super Double2ObjectMap.Entry<V>> comparator() {
/* 1084 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>> iterator() {
/* 1088 */             return (ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>>)new Double2ObjectRBTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>> iterator(Double2ObjectMap.Entry<V> from) {
/* 1093 */             return (ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>>)new Double2ObjectRBTreeMap.EntryIterator(from.getDoubleKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1098 */             if (!(o instanceof Map.Entry))
/* 1099 */               return false; 
/* 1100 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1101 */             if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1102 */               return false; 
/* 1103 */             Double2ObjectRBTreeMap.Entry<V> f = Double2ObjectRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1104 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1109 */             if (!(o instanceof Map.Entry))
/* 1110 */               return false; 
/* 1111 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1112 */             if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1113 */               return false; 
/* 1114 */             Double2ObjectRBTreeMap.Entry<V> f = Double2ObjectRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1115 */             if (f == null || !Objects.equals(f.getValue(), e.getValue()))
/* 1116 */               return false; 
/* 1117 */             Double2ObjectRBTreeMap.this.remove(f.key);
/* 1118 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1122 */             return Double2ObjectRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1126 */             Double2ObjectRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Double2ObjectMap.Entry<V> first() {
/* 1130 */             return Double2ObjectRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Double2ObjectMap.Entry<V> last() {
/* 1134 */             return Double2ObjectRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Double2ObjectMap.Entry<V>> subSet(Double2ObjectMap.Entry<V> from, Double2ObjectMap.Entry<V> to) {
/* 1139 */             return Double2ObjectRBTreeMap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2ObjectEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Double2ObjectMap.Entry<V>> headSet(Double2ObjectMap.Entry<V> to) {
/* 1143 */             return Double2ObjectRBTreeMap.this.headMap(to.getDoubleKey()).double2ObjectEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Double2ObjectMap.Entry<V>> tailSet(Double2ObjectMap.Entry<V> from) {
/* 1147 */             return Double2ObjectRBTreeMap.this.tailMap(from.getDoubleKey()).double2ObjectEntrySet();
/*      */           }
/*      */         }; 
/* 1150 */     return this.entries;
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
/* 1166 */       super(k);
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/* 1170 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1174 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractDouble2ObjectSortedMap<V>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public DoubleBidirectionalIterator iterator() {
/* 1181 */       return new Double2ObjectRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public DoubleBidirectionalIterator iterator(double from) {
/* 1185 */       return new Double2ObjectRBTreeMap.KeyIterator(from);
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
/* 1200 */     if (this.keys == null)
/* 1201 */       this.keys = new KeySet(); 
/* 1202 */     return this.keys;
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
/* 1217 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public V previous() {
/* 1221 */       return (previousEntry()).value;
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
/* 1236 */     if (this.values == null)
/* 1237 */       this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1240 */             return (ObjectIterator<V>)new Double2ObjectRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(Object k) {
/* 1244 */             return Double2ObjectRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1248 */             return Double2ObjectRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1252 */             Double2ObjectRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1255 */     return this.values;
/*      */   }
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1259 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Double2ObjectSortedMap<V> headMap(double to) {
/* 1263 */     return new Submap(0.0D, true, to, false);
/*      */   }
/*      */   
/*      */   public Double2ObjectSortedMap<V> tailMap(double from) {
/* 1267 */     return new Submap(from, false, 0.0D, true);
/*      */   }
/*      */   
/*      */   public Double2ObjectSortedMap<V> subMap(double from, double to) {
/* 1271 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractDouble2ObjectSortedMap<V>
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
/*      */     protected transient ObjectSortedSet<Double2ObjectMap.Entry<V>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient DoubleSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ObjectCollection<V> values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(double from, boolean bottom, double to, boolean top) {
/* 1315 */       if (!bottom && !top && Double2ObjectRBTreeMap.this.compare(from, to) > 0)
/* 1316 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1317 */       this.from = from;
/* 1318 */       this.bottom = bottom;
/* 1319 */       this.to = to;
/* 1320 */       this.top = top;
/* 1321 */       this.defRetValue = Double2ObjectRBTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1325 */       SubmapIterator i = new SubmapIterator();
/* 1326 */       while (i.hasNext()) {
/* 1327 */         i.nextEntry();
/* 1328 */         i.remove();
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
/* 1339 */       return ((this.bottom || Double2ObjectRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Double2ObjectRBTreeMap.this
/* 1340 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
/* 1344 */       if (this.entries == null)
/* 1345 */         this.entries = (ObjectSortedSet<Double2ObjectMap.Entry<V>>)new AbstractObjectSortedSet<Double2ObjectMap.Entry<V>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>> iterator() {
/* 1348 */               return (ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>>)new Double2ObjectRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>> iterator(Double2ObjectMap.Entry<V> from) {
/* 1353 */               return (ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>>)new Double2ObjectRBTreeMap.Submap.SubmapEntryIterator(from.getDoubleKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Double2ObjectMap.Entry<V>> comparator() {
/* 1357 */               return Double2ObjectRBTreeMap.this.double2ObjectEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1362 */               if (!(o instanceof Map.Entry))
/* 1363 */                 return false; 
/* 1364 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1365 */               if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1366 */                 return false; 
/* 1367 */               Double2ObjectRBTreeMap.Entry<V> f = Double2ObjectRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1368 */               return (f != null && Double2ObjectRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1373 */               if (!(o instanceof Map.Entry))
/* 1374 */                 return false; 
/* 1375 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1376 */               if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1377 */                 return false; 
/* 1378 */               Double2ObjectRBTreeMap.Entry<V> f = Double2ObjectRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1379 */               if (f != null && Double2ObjectRBTreeMap.Submap.this.in(f.key))
/* 1380 */                 Double2ObjectRBTreeMap.Submap.this.remove(f.key); 
/* 1381 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1385 */               int c = 0;
/* 1386 */               for (ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1387 */                 c++; 
/* 1388 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1392 */               return !(new Double2ObjectRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1396 */               Double2ObjectRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Double2ObjectMap.Entry<V> first() {
/* 1400 */               return Double2ObjectRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Double2ObjectMap.Entry<V> last() {
/* 1404 */               return Double2ObjectRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Double2ObjectMap.Entry<V>> subSet(Double2ObjectMap.Entry<V> from, Double2ObjectMap.Entry<V> to) {
/* 1409 */               return Double2ObjectRBTreeMap.Submap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2ObjectEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Double2ObjectMap.Entry<V>> headSet(Double2ObjectMap.Entry<V> to) {
/* 1413 */               return Double2ObjectRBTreeMap.Submap.this.headMap(to.getDoubleKey()).double2ObjectEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Double2ObjectMap.Entry<V>> tailSet(Double2ObjectMap.Entry<V> from) {
/* 1417 */               return Double2ObjectRBTreeMap.Submap.this.tailMap(from.getDoubleKey()).double2ObjectEntrySet();
/*      */             }
/*      */           }; 
/* 1420 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractDouble2ObjectSortedMap<V>.KeySet {
/*      */       public DoubleBidirectionalIterator iterator() {
/* 1425 */         return new Double2ObjectRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public DoubleBidirectionalIterator iterator(double from) {
/* 1429 */         return new Double2ObjectRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public DoubleSortedSet keySet() {
/* 1434 */       if (this.keys == null)
/* 1435 */         this.keys = new KeySet(); 
/* 1436 */       return this.keys;
/*      */     }
/*      */     
/*      */     public ObjectCollection<V> values() {
/* 1440 */       if (this.values == null)
/* 1441 */         this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */           {
/*      */             public ObjectIterator<V> iterator() {
/* 1444 */               return (ObjectIterator<V>)new Double2ObjectRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(Object k) {
/* 1448 */               return Double2ObjectRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1452 */               return Double2ObjectRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1456 */               Double2ObjectRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1459 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(double k) {
/* 1464 */       return (in(k) && Double2ObjectRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(Object v) {
/* 1468 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1470 */       while (i.hasNext()) {
/* 1471 */         Object ev = (i.nextEntry()).value;
/* 1472 */         if (Objects.equals(ev, v))
/* 1473 */           return true; 
/*      */       } 
/* 1475 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(double k) {
/* 1481 */       double kk = k; Double2ObjectRBTreeMap.Entry<V> e;
/* 1482 */       return (in(kk) && (e = Double2ObjectRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public V put(double k, V v) {
/* 1486 */       Double2ObjectRBTreeMap.this.modified = false;
/* 1487 */       if (!in(k))
/* 1488 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1489 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1490 */       V oldValue = Double2ObjectRBTreeMap.this.put(k, v);
/* 1491 */       return Double2ObjectRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public V remove(double k) {
/* 1496 */       Double2ObjectRBTreeMap.this.modified = false;
/* 1497 */       if (!in(k))
/* 1498 */         return this.defRetValue; 
/* 1499 */       V oldValue = Double2ObjectRBTreeMap.this.remove(k);
/* 1500 */       return Double2ObjectRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1504 */       SubmapIterator i = new SubmapIterator();
/* 1505 */       int n = 0;
/* 1506 */       while (i.hasNext()) {
/* 1507 */         n++;
/* 1508 */         i.nextEntry();
/*      */       } 
/* 1510 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1514 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1518 */       return Double2ObjectRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Double2ObjectSortedMap<V> headMap(double to) {
/* 1522 */       if (this.top)
/* 1523 */         return new Submap(this.from, this.bottom, to, false); 
/* 1524 */       return (Double2ObjectRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Double2ObjectSortedMap<V> tailMap(double from) {
/* 1528 */       if (this.bottom)
/* 1529 */         return new Submap(from, false, this.to, this.top); 
/* 1530 */       return (Double2ObjectRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Double2ObjectSortedMap<V> subMap(double from, double to) {
/* 1534 */       if (this.top && this.bottom)
/* 1535 */         return new Submap(from, false, to, false); 
/* 1536 */       if (!this.top)
/* 1537 */         to = (Double2ObjectRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1538 */       if (!this.bottom)
/* 1539 */         from = (Double2ObjectRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1540 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1541 */         return this; 
/* 1542 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2ObjectRBTreeMap.Entry<V> firstEntry() {
/*      */       Double2ObjectRBTreeMap.Entry<V> e;
/* 1551 */       if (Double2ObjectRBTreeMap.this.tree == null) {
/* 1552 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1556 */       if (this.bottom) {
/* 1557 */         e = Double2ObjectRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1559 */         e = Double2ObjectRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1561 */         if (Double2ObjectRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1562 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1566 */       if (e == null || (!this.top && Double2ObjectRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1567 */         return null; 
/* 1568 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2ObjectRBTreeMap.Entry<V> lastEntry() {
/*      */       Double2ObjectRBTreeMap.Entry<V> e;
/* 1577 */       if (Double2ObjectRBTreeMap.this.tree == null) {
/* 1578 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1582 */       if (this.top) {
/* 1583 */         e = Double2ObjectRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1585 */         e = Double2ObjectRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1587 */         if (Double2ObjectRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1588 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1592 */       if (e == null || (!this.bottom && Double2ObjectRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1593 */         return null; 
/* 1594 */       return e;
/*      */     }
/*      */     
/*      */     public double firstDoubleKey() {
/* 1598 */       Double2ObjectRBTreeMap.Entry<V> e = firstEntry();
/* 1599 */       if (e == null)
/* 1600 */         throw new NoSuchElementException(); 
/* 1601 */       return e.key;
/*      */     }
/*      */     
/*      */     public double lastDoubleKey() {
/* 1605 */       Double2ObjectRBTreeMap.Entry<V> e = lastEntry();
/* 1606 */       if (e == null)
/* 1607 */         throw new NoSuchElementException(); 
/* 1608 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Double2ObjectRBTreeMap<V>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1621 */         this.next = Double2ObjectRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(double k) {
/* 1624 */         this();
/* 1625 */         if (this.next != null)
/* 1626 */           if (!Double2ObjectRBTreeMap.Submap.this.bottom && Double2ObjectRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1627 */             this.prev = null;
/* 1628 */           } else if (!Double2ObjectRBTreeMap.Submap.this.top && Double2ObjectRBTreeMap.this.compare(k, (this.prev = Double2ObjectRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1629 */             this.next = null;
/*      */           } else {
/* 1631 */             this.next = Double2ObjectRBTreeMap.this.locateKey(k);
/* 1632 */             if (Double2ObjectRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1633 */               this.prev = this.next;
/* 1634 */               this.next = this.next.next();
/*      */             } else {
/* 1636 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1642 */         this.prev = this.prev.prev();
/* 1643 */         if (!Double2ObjectRBTreeMap.Submap.this.bottom && this.prev != null && Double2ObjectRBTreeMap.this.compare(this.prev.key, Double2ObjectRBTreeMap.Submap.this.from) < 0)
/* 1644 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1648 */         this.next = this.next.next();
/* 1649 */         if (!Double2ObjectRBTreeMap.Submap.this.top && this.next != null && Double2ObjectRBTreeMap.this.compare(this.next.key, Double2ObjectRBTreeMap.Submap.this.to) >= 0)
/* 1650 */           this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Double2ObjectMap.Entry<V>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(double k) {
/* 1659 */         super(k);
/*      */       }
/*      */       
/*      */       public Double2ObjectMap.Entry<V> next() {
/* 1663 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Double2ObjectMap.Entry<V> previous() {
/* 1667 */         return previousEntry();
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
/* 1685 */         super(from);
/*      */       }
/*      */       
/*      */       public double nextDouble() {
/* 1689 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public double previousDouble() {
/* 1693 */         return (previousEntry()).key;
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
/* 1709 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public V previous() {
/* 1713 */         return (previousEntry()).value;
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
/*      */   public Double2ObjectRBTreeMap<V> clone() {
/*      */     Double2ObjectRBTreeMap<V> c;
/*      */     try {
/* 1732 */       c = (Double2ObjectRBTreeMap<V>)super.clone();
/* 1733 */     } catch (CloneNotSupportedException cantHappen) {
/* 1734 */       throw new InternalError();
/*      */     } 
/* 1736 */     c.keys = null;
/* 1737 */     c.values = null;
/* 1738 */     c.entries = null;
/* 1739 */     c.allocatePaths();
/* 1740 */     if (this.count != 0) {
/*      */       
/* 1742 */       Entry<V> rp = new Entry<>(), rq = new Entry<>();
/* 1743 */       Entry<V> p = rp;
/* 1744 */       rp.left(this.tree);
/* 1745 */       Entry<V> q = rq;
/* 1746 */       rq.pred((Entry<V>)null);
/*      */       while (true) {
/* 1748 */         if (!p.pred()) {
/* 1749 */           Entry<V> e = p.left.clone();
/* 1750 */           e.pred(q.left);
/* 1751 */           e.succ(q);
/* 1752 */           q.left(e);
/* 1753 */           p = p.left;
/* 1754 */           q = q.left;
/*      */         } else {
/* 1756 */           while (p.succ()) {
/* 1757 */             p = p.right;
/* 1758 */             if (p == null) {
/* 1759 */               q.right = null;
/* 1760 */               c.tree = rq.left;
/* 1761 */               c.firstEntry = c.tree;
/* 1762 */               while (c.firstEntry.left != null)
/* 1763 */                 c.firstEntry = c.firstEntry.left; 
/* 1764 */               c.lastEntry = c.tree;
/* 1765 */               while (c.lastEntry.right != null)
/* 1766 */                 c.lastEntry = c.lastEntry.right; 
/* 1767 */               return c;
/*      */             } 
/* 1769 */             q = q.right;
/*      */           } 
/* 1771 */           p = p.right;
/* 1772 */           q = q.right;
/*      */         } 
/* 1774 */         if (!p.succ()) {
/* 1775 */           Entry<V> e = p.right.clone();
/* 1776 */           e.succ(q.right);
/* 1777 */           e.pred(q);
/* 1778 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1782 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1785 */     int n = this.count;
/* 1786 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1788 */     s.defaultWriteObject();
/* 1789 */     while (n-- != 0) {
/* 1790 */       Entry<V> e = i.nextEntry();
/* 1791 */       s.writeDouble(e.key);
/* 1792 */       s.writeObject(e.value);
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
/* 1813 */     if (n == 1) {
/* 1814 */       Entry<V> entry = new Entry<>(s.readDouble(), (V)s.readObject());
/* 1815 */       entry.pred(pred);
/* 1816 */       entry.succ(succ);
/* 1817 */       entry.black(true);
/* 1818 */       return entry;
/*      */     } 
/* 1820 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1825 */       Entry<V> entry = new Entry<>(s.readDouble(), (V)s.readObject());
/* 1826 */       entry.black(true);
/* 1827 */       entry.right(new Entry<>(s.readDouble(), (V)s.readObject()));
/* 1828 */       entry.right.pred(entry);
/* 1829 */       entry.pred(pred);
/* 1830 */       entry.right.succ(succ);
/* 1831 */       return entry;
/*      */     } 
/*      */     
/* 1834 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1835 */     Entry<V> top = new Entry<>();
/* 1836 */     top.left(readTree(s, leftN, pred, top));
/* 1837 */     top.key = s.readDouble();
/* 1838 */     top.value = (V)s.readObject();
/* 1839 */     top.black(true);
/* 1840 */     top.right(readTree(s, rightN, top, succ));
/* 1841 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1842 */       top.right.black(false); 
/* 1843 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1846 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1851 */     setActualComparator();
/* 1852 */     allocatePaths();
/* 1853 */     if (this.count != 0) {
/* 1854 */       this.tree = readTree(s, this.count, (Entry<V>)null, (Entry<V>)null);
/*      */       
/* 1856 */       Entry<V> e = this.tree;
/* 1857 */       while (e.left() != null)
/* 1858 */         e = e.left(); 
/* 1859 */       this.firstEntry = e;
/* 1860 */       e = this.tree;
/* 1861 */       while (e.right() != null)
/* 1862 */         e = e.right(); 
/* 1863 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2ObjectRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */