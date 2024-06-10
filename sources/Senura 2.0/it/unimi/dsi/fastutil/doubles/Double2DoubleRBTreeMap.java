/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
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
/*      */ public class Double2DoubleRBTreeMap
/*      */   extends AbstractDouble2DoubleSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Double2DoubleMap.Entry> entries;
/*      */   protected transient DoubleSortedSet keys;
/*      */   protected transient DoubleCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Double> storedComparator;
/*      */   protected transient DoubleComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public Double2DoubleRBTreeMap() {
/*   72 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   78 */     this.tree = null;
/*   79 */     this.count = 0;
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
/*   91 */     this.actualComparator = DoubleComparators.asDoubleComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleRBTreeMap(Comparator<? super Double> c) {
/*  100 */     this();
/*  101 */     this.storedComparator = c;
/*  102 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleRBTreeMap(Map<? extends Double, ? extends Double> m) {
/*  111 */     this();
/*  112 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleRBTreeMap(SortedMap<Double, Double> m) {
/*  122 */     this(m.comparator());
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleRBTreeMap(Double2DoubleMap m) {
/*  132 */     this();
/*  133 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleRBTreeMap(Double2DoubleSortedMap m) {
/*  143 */     this(m.comparator());
/*  144 */     putAll(m);
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
/*      */   public Double2DoubleRBTreeMap(double[] k, double[] v, Comparator<? super Double> c) {
/*  160 */     this(c);
/*  161 */     if (k.length != v.length) {
/*  162 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  164 */     for (int i = 0; i < k.length; i++) {
/*  165 */       put(k[i], v[i]);
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
/*      */   public Double2DoubleRBTreeMap(double[] k, double[] v) {
/*  178 */     this(k, v, (Comparator<? super Double>)null);
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
/*  206 */     return (this.actualComparator == null) ? Double.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry findKey(double k) {
/*  218 */     Entry e = this.tree;
/*      */     int cmp;
/*  220 */     while (e != null && (cmp = compare(k, e.key)) != 0)
/*  221 */       e = (cmp < 0) ? e.left() : e.right(); 
/*  222 */     return e;
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
/*      */   final Entry locateKey(double k) {
/*  234 */     Entry e = this.tree, last = this.tree;
/*  235 */     int cmp = 0;
/*  236 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  237 */       last = e;
/*  238 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  240 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  250 */     this.dirPath = new boolean[64];
/*  251 */     this.nodePath = new Entry[64];
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
/*      */   public double addTo(double k, double incr) {
/*  270 */     Entry e = add(k);
/*  271 */     double oldValue = e.value;
/*  272 */     e.value += incr;
/*  273 */     return oldValue;
/*      */   }
/*      */   
/*      */   public double put(double k, double v) {
/*  277 */     Entry e = add(k);
/*  278 */     double oldValue = e.value;
/*  279 */     e.value = v;
/*  280 */     return oldValue;
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
/*      */   private Entry add(double k) {
/*      */     Entry e;
/*  297 */     this.modified = false;
/*  298 */     int maxDepth = 0;
/*      */     
/*  300 */     if (this.tree == null) {
/*  301 */       this.count++;
/*  302 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry(k, this.defRetValue);
/*      */     } else {
/*  304 */       Entry p = this.tree;
/*  305 */       int i = 0; while (true) {
/*      */         int cmp;
/*  307 */         if ((cmp = compare(k, p.key)) == 0) {
/*      */           
/*  309 */           while (i-- != 0)
/*  310 */             this.nodePath[i] = null; 
/*  311 */           return p;
/*      */         } 
/*  313 */         this.nodePath[i] = p;
/*  314 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  315 */           if (p.succ()) {
/*  316 */             this.count++;
/*  317 */             e = new Entry(k, this.defRetValue);
/*  318 */             if (p.right == null)
/*  319 */               this.lastEntry = e; 
/*  320 */             e.left = p;
/*  321 */             e.right = p.right;
/*  322 */             p.right(e);
/*      */             break;
/*      */           } 
/*  325 */           p = p.right; continue;
/*      */         } 
/*  327 */         if (p.pred()) {
/*  328 */           this.count++;
/*  329 */           e = new Entry(k, this.defRetValue);
/*  330 */           if (p.left == null)
/*  331 */             this.firstEntry = e; 
/*  332 */           e.right = p;
/*  333 */           e.left = p.left;
/*  334 */           p.left(e);
/*      */           break;
/*      */         } 
/*  337 */         p = p.left;
/*      */       } 
/*      */       
/*  340 */       this.modified = true;
/*  341 */       maxDepth = i--;
/*  342 */       while (i > 0 && !this.nodePath[i].black()) {
/*  343 */         if (!this.dirPath[i - 1]) {
/*  344 */           Entry entry1 = (this.nodePath[i - 1]).right;
/*  345 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  346 */             this.nodePath[i].black(true);
/*  347 */             entry1.black(true);
/*  348 */             this.nodePath[i - 1].black(false);
/*  349 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  352 */           if (!this.dirPath[i]) {
/*  353 */             entry1 = this.nodePath[i];
/*      */           } else {
/*  355 */             Entry entry = this.nodePath[i];
/*  356 */             entry1 = entry.right;
/*  357 */             entry.right = entry1.left;
/*  358 */             entry1.left = entry;
/*  359 */             (this.nodePath[i - 1]).left = entry1;
/*  360 */             if (entry1.pred()) {
/*  361 */               entry1.pred(false);
/*  362 */               entry.succ(entry1);
/*      */             } 
/*      */           } 
/*  365 */           Entry entry2 = this.nodePath[i - 1];
/*  366 */           entry2.black(false);
/*  367 */           entry1.black(true);
/*  368 */           entry2.left = entry1.right;
/*  369 */           entry1.right = entry2;
/*  370 */           if (i < 2) {
/*  371 */             this.tree = entry1;
/*      */           }
/*  373 */           else if (this.dirPath[i - 2]) {
/*  374 */             (this.nodePath[i - 2]).right = entry1;
/*      */           } else {
/*  376 */             (this.nodePath[i - 2]).left = entry1;
/*      */           } 
/*  378 */           if (entry1.succ()) {
/*  379 */             entry1.succ(false);
/*  380 */             entry2.pred(entry1);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  385 */         Entry y = (this.nodePath[i - 1]).left;
/*  386 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  387 */           this.nodePath[i].black(true);
/*  388 */           y.black(true);
/*  389 */           this.nodePath[i - 1].black(false);
/*  390 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  393 */         if (this.dirPath[i]) {
/*  394 */           y = this.nodePath[i];
/*      */         } else {
/*  396 */           Entry entry = this.nodePath[i];
/*  397 */           y = entry.left;
/*  398 */           entry.left = y.right;
/*  399 */           y.right = entry;
/*  400 */           (this.nodePath[i - 1]).right = y;
/*  401 */           if (y.succ()) {
/*  402 */             y.succ(false);
/*  403 */             entry.pred(y);
/*      */           } 
/*      */         } 
/*  406 */         Entry x = this.nodePath[i - 1];
/*  407 */         x.black(false);
/*  408 */         y.black(true);
/*  409 */         x.right = y.left;
/*  410 */         y.left = x;
/*  411 */         if (i < 2) {
/*  412 */           this.tree = y;
/*      */         }
/*  414 */         else if (this.dirPath[i - 2]) {
/*  415 */           (this.nodePath[i - 2]).right = y;
/*      */         } else {
/*  417 */           (this.nodePath[i - 2]).left = y;
/*      */         } 
/*  419 */         if (y.pred()) {
/*  420 */           y.pred(false);
/*  421 */           x.succ(y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  428 */     this.tree.black(true);
/*      */     
/*  430 */     while (maxDepth-- != 0)
/*  431 */       this.nodePath[maxDepth] = null; 
/*  432 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double remove(double k) {
/*  441 */     this.modified = false;
/*  442 */     if (this.tree == null)
/*  443 */       return this.defRetValue; 
/*  444 */     Entry p = this.tree;
/*      */     
/*  446 */     int i = 0;
/*  447 */     double kk = k;
/*      */     int cmp;
/*  449 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  451 */       this.dirPath[i] = (cmp > 0);
/*  452 */       this.nodePath[i] = p;
/*  453 */       if (this.dirPath[i++]) {
/*  454 */         if ((p = p.right()) == null) {
/*      */           
/*  456 */           while (i-- != 0)
/*  457 */             this.nodePath[i] = null; 
/*  458 */           return this.defRetValue;
/*      */         }  continue;
/*      */       } 
/*  461 */       if ((p = p.left()) == null) {
/*      */         
/*  463 */         while (i-- != 0)
/*  464 */           this.nodePath[i] = null; 
/*  465 */         return this.defRetValue;
/*      */       } 
/*      */     } 
/*      */     
/*  469 */     if (p.left == null)
/*  470 */       this.firstEntry = p.next(); 
/*  471 */     if (p.right == null)
/*  472 */       this.lastEntry = p.prev(); 
/*  473 */     if (p.succ()) {
/*  474 */       if (p.pred()) {
/*  475 */         if (i == 0) {
/*  476 */           this.tree = p.left;
/*      */         }
/*  478 */         else if (this.dirPath[i - 1]) {
/*  479 */           this.nodePath[i - 1].succ(p.right);
/*      */         } else {
/*  481 */           this.nodePath[i - 1].pred(p.left);
/*      */         } 
/*      */       } else {
/*  484 */         (p.prev()).right = p.right;
/*  485 */         if (i == 0) {
/*  486 */           this.tree = p.left;
/*      */         }
/*  488 */         else if (this.dirPath[i - 1]) {
/*  489 */           (this.nodePath[i - 1]).right = p.left;
/*      */         } else {
/*  491 */           (this.nodePath[i - 1]).left = p.left;
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  496 */       Entry r = p.right;
/*  497 */       if (r.pred()) {
/*  498 */         r.left = p.left;
/*  499 */         r.pred(p.pred());
/*  500 */         if (!r.pred())
/*  501 */           (r.prev()).right = r; 
/*  502 */         if (i == 0) {
/*  503 */           this.tree = r;
/*      */         }
/*  505 */         else if (this.dirPath[i - 1]) {
/*  506 */           (this.nodePath[i - 1]).right = r;
/*      */         } else {
/*  508 */           (this.nodePath[i - 1]).left = r;
/*      */         } 
/*  510 */         boolean color = r.black();
/*  511 */         r.black(p.black());
/*  512 */         p.black(color);
/*  513 */         this.dirPath[i] = true;
/*  514 */         this.nodePath[i++] = r;
/*      */       } else {
/*      */         Entry s;
/*  517 */         int j = i++;
/*      */         while (true) {
/*  519 */           this.dirPath[i] = false;
/*  520 */           this.nodePath[i++] = r;
/*  521 */           s = r.left;
/*  522 */           if (s.pred())
/*      */             break; 
/*  524 */           r = s;
/*      */         } 
/*  526 */         this.dirPath[j] = true;
/*  527 */         this.nodePath[j] = s;
/*  528 */         if (s.succ()) {
/*  529 */           r.pred(s);
/*      */         } else {
/*  531 */           r.left = s.right;
/*  532 */         }  s.left = p.left;
/*  533 */         if (!p.pred()) {
/*  534 */           (p.prev()).right = s;
/*  535 */           s.pred(false);
/*      */         } 
/*  537 */         s.right(p.right);
/*  538 */         boolean color = s.black();
/*  539 */         s.black(p.black());
/*  540 */         p.black(color);
/*  541 */         if (j == 0) {
/*  542 */           this.tree = s;
/*      */         }
/*  544 */         else if (this.dirPath[j - 1]) {
/*  545 */           (this.nodePath[j - 1]).right = s;
/*      */         } else {
/*  547 */           (this.nodePath[j - 1]).left = s;
/*      */         } 
/*      */       } 
/*      */     } 
/*  551 */     int maxDepth = i;
/*  552 */     if (p.black()) {
/*  553 */       for (; i > 0; i--) {
/*  554 */         if ((this.dirPath[i - 1] && !this.nodePath[i - 1].succ()) || (!this.dirPath[i - 1] && !this.nodePath[i - 1].pred())) {
/*  555 */           Entry x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  556 */           if (!x.black()) {
/*  557 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  561 */         if (!this.dirPath[i - 1]) {
/*  562 */           Entry w = (this.nodePath[i - 1]).right;
/*  563 */           if (!w.black()) {
/*  564 */             w.black(true);
/*  565 */             this.nodePath[i - 1].black(false);
/*  566 */             (this.nodePath[i - 1]).right = w.left;
/*  567 */             w.left = this.nodePath[i - 1];
/*  568 */             if (i < 2) {
/*  569 */               this.tree = w;
/*      */             }
/*  571 */             else if (this.dirPath[i - 2]) {
/*  572 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  574 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  576 */             this.nodePath[i] = this.nodePath[i - 1];
/*  577 */             this.dirPath[i] = false;
/*  578 */             this.nodePath[i - 1] = w;
/*  579 */             if (maxDepth == i++)
/*  580 */               maxDepth++; 
/*  581 */             w = (this.nodePath[i - 1]).right;
/*      */           } 
/*  583 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  584 */             w.black(false);
/*      */           } else {
/*  586 */             if (w.succ() || w.right.black()) {
/*  587 */               Entry y = w.left;
/*  588 */               y.black(true);
/*  589 */               w.black(false);
/*  590 */               w.left = y.right;
/*  591 */               y.right = w;
/*  592 */               w = (this.nodePath[i - 1]).right = y;
/*  593 */               if (w.succ()) {
/*  594 */                 w.succ(false);
/*  595 */                 w.right.pred(w);
/*      */               } 
/*      */             } 
/*  598 */             w.black(this.nodePath[i - 1].black());
/*  599 */             this.nodePath[i - 1].black(true);
/*  600 */             w.right.black(true);
/*  601 */             (this.nodePath[i - 1]).right = w.left;
/*  602 */             w.left = this.nodePath[i - 1];
/*  603 */             if (i < 2) {
/*  604 */               this.tree = w;
/*      */             }
/*  606 */             else if (this.dirPath[i - 2]) {
/*  607 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  609 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  611 */             if (w.pred()) {
/*  612 */               w.pred(false);
/*  613 */               this.nodePath[i - 1].succ(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } else {
/*  618 */           Entry w = (this.nodePath[i - 1]).left;
/*  619 */           if (!w.black()) {
/*  620 */             w.black(true);
/*  621 */             this.nodePath[i - 1].black(false);
/*  622 */             (this.nodePath[i - 1]).left = w.right;
/*  623 */             w.right = this.nodePath[i - 1];
/*  624 */             if (i < 2) {
/*  625 */               this.tree = w;
/*      */             }
/*  627 */             else if (this.dirPath[i - 2]) {
/*  628 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  630 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  632 */             this.nodePath[i] = this.nodePath[i - 1];
/*  633 */             this.dirPath[i] = true;
/*  634 */             this.nodePath[i - 1] = w;
/*  635 */             if (maxDepth == i++)
/*  636 */               maxDepth++; 
/*  637 */             w = (this.nodePath[i - 1]).left;
/*      */           } 
/*  639 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  640 */             w.black(false);
/*      */           } else {
/*  642 */             if (w.pred() || w.left.black()) {
/*  643 */               Entry y = w.right;
/*  644 */               y.black(true);
/*  645 */               w.black(false);
/*  646 */               w.right = y.left;
/*  647 */               y.left = w;
/*  648 */               w = (this.nodePath[i - 1]).left = y;
/*  649 */               if (w.pred()) {
/*  650 */                 w.pred(false);
/*  651 */                 w.left.succ(w);
/*      */               } 
/*      */             } 
/*  654 */             w.black(this.nodePath[i - 1].black());
/*  655 */             this.nodePath[i - 1].black(true);
/*  656 */             w.left.black(true);
/*  657 */             (this.nodePath[i - 1]).left = w.right;
/*  658 */             w.right = this.nodePath[i - 1];
/*  659 */             if (i < 2) {
/*  660 */               this.tree = w;
/*      */             }
/*  662 */             else if (this.dirPath[i - 2]) {
/*  663 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  665 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  667 */             if (w.succ()) {
/*  668 */               w.succ(false);
/*  669 */               this.nodePath[i - 1].pred(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  675 */       if (this.tree != null)
/*  676 */         this.tree.black(true); 
/*      */     } 
/*  678 */     this.modified = true;
/*  679 */     this.count--;
/*      */     
/*  681 */     while (maxDepth-- != 0)
/*  682 */       this.nodePath[maxDepth] = null; 
/*  683 */     return p.value;
/*      */   }
/*      */   
/*      */   public boolean containsValue(double v) {
/*  687 */     ValueIterator i = new ValueIterator();
/*      */     
/*  689 */     int j = this.count;
/*  690 */     while (j-- != 0) {
/*  691 */       double ev = i.nextDouble();
/*  692 */       if (Double.doubleToLongBits(ev) == Double.doubleToLongBits(v))
/*  693 */         return true; 
/*      */     } 
/*  695 */     return false;
/*      */   }
/*      */   
/*      */   public void clear() {
/*  699 */     this.count = 0;
/*  700 */     this.tree = null;
/*  701 */     this.entries = null;
/*  702 */     this.values = null;
/*  703 */     this.keys = null;
/*  704 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry
/*      */     extends AbstractDouble2DoubleMap.BasicEntry
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
/*      */     Entry left;
/*      */ 
/*      */     
/*      */     Entry right;
/*      */ 
/*      */     
/*      */     int info;
/*      */ 
/*      */     
/*      */     Entry() {
/*  732 */       super(0.0D, 0.0D);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(double k, double v) {
/*  743 */       super(k, v);
/*  744 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left() {
/*  752 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right() {
/*  760 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  768 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  776 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  785 */       if (pred) {
/*  786 */         this.info |= 0x40000000;
/*      */       } else {
/*  788 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  797 */       if (succ) {
/*  798 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  800 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry pred) {
/*  809 */       this.info |= 0x40000000;
/*  810 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry succ) {
/*  819 */       this.info |= Integer.MIN_VALUE;
/*  820 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry left) {
/*  829 */       this.info &= 0xBFFFFFFF;
/*  830 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry right) {
/*  839 */       this.info &= Integer.MAX_VALUE;
/*  840 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean black() {
/*  848 */       return ((this.info & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void black(boolean black) {
/*  857 */       if (black) {
/*  858 */         this.info |= 0x1;
/*      */       } else {
/*  860 */         this.info &= 0xFFFFFFFE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry next() {
/*  868 */       Entry next = this.right;
/*  869 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  870 */         while ((next.info & 0x40000000) == 0)
/*  871 */           next = next.left;  
/*  872 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry prev() {
/*  880 */       Entry prev = this.left;
/*  881 */       if ((this.info & 0x40000000) == 0)
/*  882 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  883 */           prev = prev.right;  
/*  884 */       return prev;
/*      */     }
/*      */     
/*      */     public double setValue(double value) {
/*  888 */       double oldValue = this.value;
/*  889 */       this.value = value;
/*  890 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry clone() {
/*      */       Entry c;
/*      */       try {
/*  897 */         c = (Entry)super.clone();
/*  898 */       } catch (CloneNotSupportedException cantHappen) {
/*  899 */         throw new InternalError();
/*      */       } 
/*  901 */       c.key = this.key;
/*  902 */       c.value = this.value;
/*  903 */       c.info = this.info;
/*  904 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  909 */       if (!(o instanceof Map.Entry))
/*  910 */         return false; 
/*  911 */       Map.Entry<Double, Double> e = (Map.Entry<Double, Double>)o;
/*  912 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && 
/*  913 */         Double.doubleToLongBits(this.value) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  917 */       return HashCommon.double2int(this.key) ^ 
/*  918 */         HashCommon.double2int(this.value);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  922 */       return this.key + "=>" + this.value;
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
/*  943 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  947 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  951 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public double get(double k) {
/*  956 */     Entry e = findKey(k);
/*  957 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public double firstDoubleKey() {
/*  961 */     if (this.tree == null)
/*  962 */       throw new NoSuchElementException(); 
/*  963 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public double lastDoubleKey() {
/*  967 */     if (this.tree == null)
/*  968 */       throw new NoSuchElementException(); 
/*  969 */     return this.lastEntry.key;
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
/*      */     Double2DoubleRBTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2DoubleRBTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2DoubleRBTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1000 */     int index = 0;
/*      */     TreeIterator() {
/* 1002 */       this.next = Double2DoubleRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(double k) {
/* 1005 */       if ((this.next = Double2DoubleRBTreeMap.this.locateKey(k)) != null)
/* 1006 */         if (Double2DoubleRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1007 */           this.prev = this.next;
/* 1008 */           this.next = this.next.next();
/*      */         } else {
/* 1010 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/* 1014 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1017 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1020 */       this.next = this.next.next();
/*      */     }
/*      */     Double2DoubleRBTreeMap.Entry nextEntry() {
/* 1023 */       if (!hasNext())
/* 1024 */         throw new NoSuchElementException(); 
/* 1025 */       this.curr = this.prev = this.next;
/* 1026 */       this.index++;
/* 1027 */       updateNext();
/* 1028 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1031 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Double2DoubleRBTreeMap.Entry previousEntry() {
/* 1034 */       if (!hasPrevious())
/* 1035 */         throw new NoSuchElementException(); 
/* 1036 */       this.curr = this.next = this.prev;
/* 1037 */       this.index--;
/* 1038 */       updatePrevious();
/* 1039 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1042 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1045 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1048 */       if (this.curr == null) {
/* 1049 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1054 */       if (this.curr == this.prev)
/* 1055 */         this.index--; 
/* 1056 */       this.next = this.prev = this.curr;
/* 1057 */       updatePrevious();
/* 1058 */       updateNext();
/* 1059 */       Double2DoubleRBTreeMap.this.remove(this.curr.key);
/* 1060 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1063 */       int i = n;
/* 1064 */       while (i-- != 0 && hasNext())
/* 1065 */         nextEntry(); 
/* 1066 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1069 */       int i = n;
/* 1070 */       while (i-- != 0 && hasPrevious())
/* 1071 */         previousEntry(); 
/* 1072 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Double2DoubleMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(double k) {
/* 1085 */       super(k);
/*      */     }
/*      */     
/*      */     public Double2DoubleMap.Entry next() {
/* 1089 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Double2DoubleMap.Entry previous() {
/* 1093 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Double2DoubleMap.Entry> double2DoubleEntrySet() {
/* 1098 */     if (this.entries == null)
/* 1099 */       this.entries = (ObjectSortedSet<Double2DoubleMap.Entry>)new AbstractObjectSortedSet<Double2DoubleMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Double2DoubleMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Double2DoubleMap.Entry> comparator() {
/* 1104 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2DoubleMap.Entry> iterator() {
/* 1108 */             return (ObjectBidirectionalIterator<Double2DoubleMap.Entry>)new Double2DoubleRBTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2DoubleMap.Entry> iterator(Double2DoubleMap.Entry from) {
/* 1112 */             return (ObjectBidirectionalIterator<Double2DoubleMap.Entry>)new Double2DoubleRBTreeMap.EntryIterator(from.getDoubleKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1117 */             if (!(o instanceof Map.Entry))
/* 1118 */               return false; 
/* 1119 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1120 */             if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1121 */               return false; 
/* 1122 */             if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1123 */               return false; 
/* 1124 */             Double2DoubleRBTreeMap.Entry f = Double2DoubleRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1125 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1130 */             if (!(o instanceof Map.Entry))
/* 1131 */               return false; 
/* 1132 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1133 */             if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1134 */               return false; 
/* 1135 */             if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1136 */               return false; 
/* 1137 */             Double2DoubleRBTreeMap.Entry f = Double2DoubleRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1138 */             if (f == null || Double.doubleToLongBits(f.getDoubleValue()) != 
/* 1139 */               Double.doubleToLongBits(((Double)e.getValue()).doubleValue()))
/* 1140 */               return false; 
/* 1141 */             Double2DoubleRBTreeMap.this.remove(f.key);
/* 1142 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1146 */             return Double2DoubleRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1150 */             Double2DoubleRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Double2DoubleMap.Entry first() {
/* 1154 */             return Double2DoubleRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Double2DoubleMap.Entry last() {
/* 1158 */             return Double2DoubleRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Double2DoubleMap.Entry> subSet(Double2DoubleMap.Entry from, Double2DoubleMap.Entry to) {
/* 1163 */             return Double2DoubleRBTreeMap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2DoubleEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Double2DoubleMap.Entry> headSet(Double2DoubleMap.Entry to) {
/* 1167 */             return Double2DoubleRBTreeMap.this.headMap(to.getDoubleKey()).double2DoubleEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Double2DoubleMap.Entry> tailSet(Double2DoubleMap.Entry from) {
/* 1171 */             return Double2DoubleRBTreeMap.this.tailMap(from.getDoubleKey()).double2DoubleEntrySet();
/*      */           }
/*      */         }; 
/* 1174 */     return this.entries;
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
/* 1190 */       super(k);
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/* 1194 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1198 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractDouble2DoubleSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public DoubleBidirectionalIterator iterator() {
/* 1205 */       return new Double2DoubleRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public DoubleBidirectionalIterator iterator(double from) {
/* 1209 */       return new Double2DoubleRBTreeMap.KeyIterator(from);
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
/* 1224 */     if (this.keys == null)
/* 1225 */       this.keys = new KeySet(); 
/* 1226 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements DoubleListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1241 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1245 */       return (previousEntry()).value;
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
/*      */   public DoubleCollection values() {
/* 1260 */     if (this.values == null)
/* 1261 */       this.values = new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1264 */             return new Double2DoubleRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(double k) {
/* 1268 */             return Double2DoubleRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1272 */             return Double2DoubleRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1276 */             Double2DoubleRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1279 */     return this.values;
/*      */   }
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1283 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Double2DoubleSortedMap headMap(double to) {
/* 1287 */     return new Submap(0.0D, true, to, false);
/*      */   }
/*      */   
/*      */   public Double2DoubleSortedMap tailMap(double from) {
/* 1291 */     return new Submap(from, false, 0.0D, true);
/*      */   }
/*      */   
/*      */   public Double2DoubleSortedMap subMap(double from, double to) {
/* 1295 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractDouble2DoubleSortedMap
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
/*      */     protected transient ObjectSortedSet<Double2DoubleMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient DoubleSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient DoubleCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(double from, boolean bottom, double to, boolean top) {
/* 1339 */       if (!bottom && !top && Double2DoubleRBTreeMap.this.compare(from, to) > 0)
/* 1340 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1341 */       this.from = from;
/* 1342 */       this.bottom = bottom;
/* 1343 */       this.to = to;
/* 1344 */       this.top = top;
/* 1345 */       this.defRetValue = Double2DoubleRBTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1349 */       SubmapIterator i = new SubmapIterator();
/* 1350 */       while (i.hasNext()) {
/* 1351 */         i.nextEntry();
/* 1352 */         i.remove();
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
/* 1363 */       return ((this.bottom || Double2DoubleRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Double2DoubleRBTreeMap.this
/* 1364 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2DoubleMap.Entry> double2DoubleEntrySet() {
/* 1368 */       if (this.entries == null)
/* 1369 */         this.entries = (ObjectSortedSet<Double2DoubleMap.Entry>)new AbstractObjectSortedSet<Double2DoubleMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Double2DoubleMap.Entry> iterator() {
/* 1372 */               return (ObjectBidirectionalIterator<Double2DoubleMap.Entry>)new Double2DoubleRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Double2DoubleMap.Entry> iterator(Double2DoubleMap.Entry from) {
/* 1377 */               return (ObjectBidirectionalIterator<Double2DoubleMap.Entry>)new Double2DoubleRBTreeMap.Submap.SubmapEntryIterator(from.getDoubleKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Double2DoubleMap.Entry> comparator() {
/* 1381 */               return Double2DoubleRBTreeMap.this.double2DoubleEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1386 */               if (!(o instanceof Map.Entry))
/* 1387 */                 return false; 
/* 1388 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1389 */               if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1390 */                 return false; 
/* 1391 */               if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1392 */                 return false; 
/* 1393 */               Double2DoubleRBTreeMap.Entry f = Double2DoubleRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1394 */               return (f != null && Double2DoubleRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1399 */               if (!(o instanceof Map.Entry))
/* 1400 */                 return false; 
/* 1401 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1402 */               if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1403 */                 return false; 
/* 1404 */               if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1405 */                 return false; 
/* 1406 */               Double2DoubleRBTreeMap.Entry f = Double2DoubleRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1407 */               if (f != null && Double2DoubleRBTreeMap.Submap.this.in(f.key))
/* 1408 */                 Double2DoubleRBTreeMap.Submap.this.remove(f.key); 
/* 1409 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1413 */               int c = 0;
/* 1414 */               for (ObjectBidirectionalIterator<Double2DoubleMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1415 */                 c++; 
/* 1416 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1420 */               return !(new Double2DoubleRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1424 */               Double2DoubleRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Double2DoubleMap.Entry first() {
/* 1428 */               return Double2DoubleRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Double2DoubleMap.Entry last() {
/* 1432 */               return Double2DoubleRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Double2DoubleMap.Entry> subSet(Double2DoubleMap.Entry from, Double2DoubleMap.Entry to) {
/* 1437 */               return Double2DoubleRBTreeMap.Submap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2DoubleEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Double2DoubleMap.Entry> headSet(Double2DoubleMap.Entry to) {
/* 1441 */               return Double2DoubleRBTreeMap.Submap.this.headMap(to.getDoubleKey()).double2DoubleEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Double2DoubleMap.Entry> tailSet(Double2DoubleMap.Entry from) {
/* 1445 */               return Double2DoubleRBTreeMap.Submap.this.tailMap(from.getDoubleKey()).double2DoubleEntrySet();
/*      */             }
/*      */           }; 
/* 1448 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractDouble2DoubleSortedMap.KeySet {
/*      */       public DoubleBidirectionalIterator iterator() {
/* 1453 */         return new Double2DoubleRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public DoubleBidirectionalIterator iterator(double from) {
/* 1457 */         return new Double2DoubleRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public DoubleSortedSet keySet() {
/* 1462 */       if (this.keys == null)
/* 1463 */         this.keys = new KeySet(); 
/* 1464 */       return this.keys;
/*      */     }
/*      */     
/*      */     public DoubleCollection values() {
/* 1468 */       if (this.values == null)
/* 1469 */         this.values = new AbstractDoubleCollection()
/*      */           {
/*      */             public DoubleIterator iterator() {
/* 1472 */               return new Double2DoubleRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(double k) {
/* 1476 */               return Double2DoubleRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1480 */               return Double2DoubleRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1484 */               Double2DoubleRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1487 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(double k) {
/* 1492 */       return (in(k) && Double2DoubleRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(double v) {
/* 1496 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1498 */       while (i.hasNext()) {
/* 1499 */         double ev = (i.nextEntry()).value;
/* 1500 */         if (Double.doubleToLongBits(ev) == Double.doubleToLongBits(v))
/* 1501 */           return true; 
/*      */       } 
/* 1503 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public double get(double k) {
/* 1509 */       double kk = k; Double2DoubleRBTreeMap.Entry e;
/* 1510 */       return (in(kk) && (e = Double2DoubleRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public double put(double k, double v) {
/* 1514 */       Double2DoubleRBTreeMap.this.modified = false;
/* 1515 */       if (!in(k))
/* 1516 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1517 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1518 */       double oldValue = Double2DoubleRBTreeMap.this.put(k, v);
/* 1519 */       return Double2DoubleRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public double remove(double k) {
/* 1524 */       Double2DoubleRBTreeMap.this.modified = false;
/* 1525 */       if (!in(k))
/* 1526 */         return this.defRetValue; 
/* 1527 */       double oldValue = Double2DoubleRBTreeMap.this.remove(k);
/* 1528 */       return Double2DoubleRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1532 */       SubmapIterator i = new SubmapIterator();
/* 1533 */       int n = 0;
/* 1534 */       while (i.hasNext()) {
/* 1535 */         n++;
/* 1536 */         i.nextEntry();
/*      */       } 
/* 1538 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1542 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1546 */       return Double2DoubleRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Double2DoubleSortedMap headMap(double to) {
/* 1550 */       if (this.top)
/* 1551 */         return new Submap(this.from, this.bottom, to, false); 
/* 1552 */       return (Double2DoubleRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Double2DoubleSortedMap tailMap(double from) {
/* 1556 */       if (this.bottom)
/* 1557 */         return new Submap(from, false, this.to, this.top); 
/* 1558 */       return (Double2DoubleRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Double2DoubleSortedMap subMap(double from, double to) {
/* 1562 */       if (this.top && this.bottom)
/* 1563 */         return new Submap(from, false, to, false); 
/* 1564 */       if (!this.top)
/* 1565 */         to = (Double2DoubleRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1566 */       if (!this.bottom)
/* 1567 */         from = (Double2DoubleRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1568 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1569 */         return this; 
/* 1570 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2DoubleRBTreeMap.Entry firstEntry() {
/*      */       Double2DoubleRBTreeMap.Entry e;
/* 1579 */       if (Double2DoubleRBTreeMap.this.tree == null) {
/* 1580 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1584 */       if (this.bottom) {
/* 1585 */         e = Double2DoubleRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1587 */         e = Double2DoubleRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1589 */         if (Double2DoubleRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1590 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1594 */       if (e == null || (!this.top && Double2DoubleRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1595 */         return null; 
/* 1596 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2DoubleRBTreeMap.Entry lastEntry() {
/*      */       Double2DoubleRBTreeMap.Entry e;
/* 1605 */       if (Double2DoubleRBTreeMap.this.tree == null) {
/* 1606 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1610 */       if (this.top) {
/* 1611 */         e = Double2DoubleRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1613 */         e = Double2DoubleRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1615 */         if (Double2DoubleRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1616 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1620 */       if (e == null || (!this.bottom && Double2DoubleRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1621 */         return null; 
/* 1622 */       return e;
/*      */     }
/*      */     
/*      */     public double firstDoubleKey() {
/* 1626 */       Double2DoubleRBTreeMap.Entry e = firstEntry();
/* 1627 */       if (e == null)
/* 1628 */         throw new NoSuchElementException(); 
/* 1629 */       return e.key;
/*      */     }
/*      */     
/*      */     public double lastDoubleKey() {
/* 1633 */       Double2DoubleRBTreeMap.Entry e = lastEntry();
/* 1634 */       if (e == null)
/* 1635 */         throw new NoSuchElementException(); 
/* 1636 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Double2DoubleRBTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1649 */         this.next = Double2DoubleRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(double k) {
/* 1652 */         this();
/* 1653 */         if (this.next != null)
/* 1654 */           if (!Double2DoubleRBTreeMap.Submap.this.bottom && Double2DoubleRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1655 */             this.prev = null;
/* 1656 */           } else if (!Double2DoubleRBTreeMap.Submap.this.top && Double2DoubleRBTreeMap.this.compare(k, (this.prev = Double2DoubleRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1657 */             this.next = null;
/*      */           } else {
/* 1659 */             this.next = Double2DoubleRBTreeMap.this.locateKey(k);
/* 1660 */             if (Double2DoubleRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1661 */               this.prev = this.next;
/* 1662 */               this.next = this.next.next();
/*      */             } else {
/* 1664 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1670 */         this.prev = this.prev.prev();
/* 1671 */         if (!Double2DoubleRBTreeMap.Submap.this.bottom && this.prev != null && Double2DoubleRBTreeMap.this.compare(this.prev.key, Double2DoubleRBTreeMap.Submap.this.from) < 0)
/* 1672 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1676 */         this.next = this.next.next();
/* 1677 */         if (!Double2DoubleRBTreeMap.Submap.this.top && this.next != null && Double2DoubleRBTreeMap.this.compare(this.next.key, Double2DoubleRBTreeMap.Submap.this.to) >= 0)
/* 1678 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Double2DoubleMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(double k) {
/* 1685 */         super(k);
/*      */       }
/*      */       
/*      */       public Double2DoubleMap.Entry next() {
/* 1689 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Double2DoubleMap.Entry previous() {
/* 1693 */         return previousEntry();
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
/* 1711 */         super(from);
/*      */       }
/*      */       
/*      */       public double nextDouble() {
/* 1715 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public double previousDouble() {
/* 1719 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements DoubleListIterator
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public double nextDouble() {
/* 1735 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public double previousDouble() {
/* 1739 */         return (previousEntry()).value;
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
/*      */   public Double2DoubleRBTreeMap clone() {
/*      */     Double2DoubleRBTreeMap c;
/*      */     try {
/* 1758 */       c = (Double2DoubleRBTreeMap)super.clone();
/* 1759 */     } catch (CloneNotSupportedException cantHappen) {
/* 1760 */       throw new InternalError();
/*      */     } 
/* 1762 */     c.keys = null;
/* 1763 */     c.values = null;
/* 1764 */     c.entries = null;
/* 1765 */     c.allocatePaths();
/* 1766 */     if (this.count != 0) {
/*      */       
/* 1768 */       Entry rp = new Entry(), rq = new Entry();
/* 1769 */       Entry p = rp;
/* 1770 */       rp.left(this.tree);
/* 1771 */       Entry q = rq;
/* 1772 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1774 */         if (!p.pred()) {
/* 1775 */           Entry e = p.left.clone();
/* 1776 */           e.pred(q.left);
/* 1777 */           e.succ(q);
/* 1778 */           q.left(e);
/* 1779 */           p = p.left;
/* 1780 */           q = q.left;
/*      */         } else {
/* 1782 */           while (p.succ()) {
/* 1783 */             p = p.right;
/* 1784 */             if (p == null) {
/* 1785 */               q.right = null;
/* 1786 */               c.tree = rq.left;
/* 1787 */               c.firstEntry = c.tree;
/* 1788 */               while (c.firstEntry.left != null)
/* 1789 */                 c.firstEntry = c.firstEntry.left; 
/* 1790 */               c.lastEntry = c.tree;
/* 1791 */               while (c.lastEntry.right != null)
/* 1792 */                 c.lastEntry = c.lastEntry.right; 
/* 1793 */               return c;
/*      */             } 
/* 1795 */             q = q.right;
/*      */           } 
/* 1797 */           p = p.right;
/* 1798 */           q = q.right;
/*      */         } 
/* 1800 */         if (!p.succ()) {
/* 1801 */           Entry e = p.right.clone();
/* 1802 */           e.succ(q.right);
/* 1803 */           e.pred(q);
/* 1804 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1808 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1811 */     int n = this.count;
/* 1812 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1814 */     s.defaultWriteObject();
/* 1815 */     while (n-- != 0) {
/* 1816 */       Entry e = i.nextEntry();
/* 1817 */       s.writeDouble(e.key);
/* 1818 */       s.writeDouble(e.value);
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
/*      */   private Entry readTree(ObjectInputStream s, int n, Entry pred, Entry succ) throws IOException, ClassNotFoundException {
/* 1839 */     if (n == 1) {
/* 1840 */       Entry entry = new Entry(s.readDouble(), s.readDouble());
/* 1841 */       entry.pred(pred);
/* 1842 */       entry.succ(succ);
/* 1843 */       entry.black(true);
/* 1844 */       return entry;
/*      */     } 
/* 1846 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1851 */       Entry entry = new Entry(s.readDouble(), s.readDouble());
/* 1852 */       entry.black(true);
/* 1853 */       entry.right(new Entry(s.readDouble(), s.readDouble()));
/* 1854 */       entry.right.pred(entry);
/* 1855 */       entry.pred(pred);
/* 1856 */       entry.right.succ(succ);
/* 1857 */       return entry;
/*      */     } 
/*      */     
/* 1860 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1861 */     Entry top = new Entry();
/* 1862 */     top.left(readTree(s, leftN, pred, top));
/* 1863 */     top.key = s.readDouble();
/* 1864 */     top.value = s.readDouble();
/* 1865 */     top.black(true);
/* 1866 */     top.right(readTree(s, rightN, top, succ));
/* 1867 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1868 */       top.right.black(false); 
/* 1869 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1872 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1877 */     setActualComparator();
/* 1878 */     allocatePaths();
/* 1879 */     if (this.count != 0) {
/* 1880 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1882 */       Entry e = this.tree;
/* 1883 */       while (e.left() != null)
/* 1884 */         e = e.left(); 
/* 1885 */       this.firstEntry = e;
/* 1886 */       e = this.tree;
/* 1887 */       while (e.right() != null)
/* 1888 */         e = e.right(); 
/* 1889 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2DoubleRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */