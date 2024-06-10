/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortListIterator;
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
/*      */ public class Double2ShortRBTreeMap
/*      */   extends AbstractDouble2ShortSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Double2ShortMap.Entry> entries;
/*      */   protected transient DoubleSortedSet keys;
/*      */   protected transient ShortCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Double> storedComparator;
/*      */   protected transient DoubleComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public Double2ShortRBTreeMap() {
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
/*      */   public Double2ShortRBTreeMap(Comparator<? super Double> c) {
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
/*      */   public Double2ShortRBTreeMap(Map<? extends Double, ? extends Short> m) {
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
/*      */   public Double2ShortRBTreeMap(SortedMap<Double, Short> m) {
/*  122 */     this(m.comparator());
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ShortRBTreeMap(Double2ShortMap m) {
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
/*      */   public Double2ShortRBTreeMap(Double2ShortSortedMap m) {
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
/*      */   public Double2ShortRBTreeMap(double[] k, short[] v, Comparator<? super Double> c) {
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
/*      */   public Double2ShortRBTreeMap(double[] k, short[] v) {
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
/*      */   public short addTo(double k, short incr) {
/*  270 */     Entry e = add(k);
/*  271 */     short oldValue = e.value;
/*  272 */     e.value = (short)(e.value + incr);
/*  273 */     return oldValue;
/*      */   }
/*      */   
/*      */   public short put(double k, short v) {
/*  277 */     Entry e = add(k);
/*  278 */     short oldValue = e.value;
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
/*      */   public short remove(double k) {
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
/*      */   public boolean containsValue(short v) {
/*  687 */     ValueIterator i = new ValueIterator();
/*      */     
/*  689 */     int j = this.count;
/*  690 */     while (j-- != 0) {
/*  691 */       short ev = i.nextShort();
/*  692 */       if (ev == v)
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
/*      */     extends AbstractDouble2ShortMap.BasicEntry
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
/*  732 */       super(0.0D, (short)0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(double k, short v) {
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
/*      */     public short setValue(short value) {
/*  888 */       short oldValue = this.value;
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
/*  911 */       Map.Entry<Double, Short> e = (Map.Entry<Double, Short>)o;
/*  912 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && this.value == ((Short)e
/*  913 */         .getValue()).shortValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  917 */       return HashCommon.double2int(this.key) ^ this.value;
/*      */     }
/*      */     
/*      */     public String toString() {
/*  921 */       return this.key + "=>" + this.value;
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
/*  942 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  946 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  950 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public short get(double k) {
/*  955 */     Entry e = findKey(k);
/*  956 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public double firstDoubleKey() {
/*  960 */     if (this.tree == null)
/*  961 */       throw new NoSuchElementException(); 
/*  962 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public double lastDoubleKey() {
/*  966 */     if (this.tree == null)
/*  967 */       throw new NoSuchElementException(); 
/*  968 */     return this.lastEntry.key;
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
/*      */     Double2ShortRBTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2ShortRBTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2ShortRBTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  999 */     int index = 0;
/*      */     TreeIterator() {
/* 1001 */       this.next = Double2ShortRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(double k) {
/* 1004 */       if ((this.next = Double2ShortRBTreeMap.this.locateKey(k)) != null)
/* 1005 */         if (Double2ShortRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1006 */           this.prev = this.next;
/* 1007 */           this.next = this.next.next();
/*      */         } else {
/* 1009 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/* 1013 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1016 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1019 */       this.next = this.next.next();
/*      */     }
/*      */     Double2ShortRBTreeMap.Entry nextEntry() {
/* 1022 */       if (!hasNext())
/* 1023 */         throw new NoSuchElementException(); 
/* 1024 */       this.curr = this.prev = this.next;
/* 1025 */       this.index++;
/* 1026 */       updateNext();
/* 1027 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1030 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Double2ShortRBTreeMap.Entry previousEntry() {
/* 1033 */       if (!hasPrevious())
/* 1034 */         throw new NoSuchElementException(); 
/* 1035 */       this.curr = this.next = this.prev;
/* 1036 */       this.index--;
/* 1037 */       updatePrevious();
/* 1038 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1041 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1044 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1047 */       if (this.curr == null) {
/* 1048 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1053 */       if (this.curr == this.prev)
/* 1054 */         this.index--; 
/* 1055 */       this.next = this.prev = this.curr;
/* 1056 */       updatePrevious();
/* 1057 */       updateNext();
/* 1058 */       Double2ShortRBTreeMap.this.remove(this.curr.key);
/* 1059 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1062 */       int i = n;
/* 1063 */       while (i-- != 0 && hasNext())
/* 1064 */         nextEntry(); 
/* 1065 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1068 */       int i = n;
/* 1069 */       while (i-- != 0 && hasPrevious())
/* 1070 */         previousEntry(); 
/* 1071 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Double2ShortMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(double k) {
/* 1084 */       super(k);
/*      */     }
/*      */     
/*      */     public Double2ShortMap.Entry next() {
/* 1088 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Double2ShortMap.Entry previous() {
/* 1092 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Double2ShortMap.Entry> double2ShortEntrySet() {
/* 1097 */     if (this.entries == null)
/* 1098 */       this.entries = (ObjectSortedSet<Double2ShortMap.Entry>)new AbstractObjectSortedSet<Double2ShortMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Double2ShortMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Double2ShortMap.Entry> comparator() {
/* 1103 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2ShortMap.Entry> iterator() {
/* 1107 */             return (ObjectBidirectionalIterator<Double2ShortMap.Entry>)new Double2ShortRBTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2ShortMap.Entry> iterator(Double2ShortMap.Entry from) {
/* 1111 */             return (ObjectBidirectionalIterator<Double2ShortMap.Entry>)new Double2ShortRBTreeMap.EntryIterator(from.getDoubleKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1116 */             if (!(o instanceof Map.Entry))
/* 1117 */               return false; 
/* 1118 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1119 */             if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1120 */               return false; 
/* 1121 */             if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1122 */               return false; 
/* 1123 */             Double2ShortRBTreeMap.Entry f = Double2ShortRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1124 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1129 */             if (!(o instanceof Map.Entry))
/* 1130 */               return false; 
/* 1131 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1132 */             if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1133 */               return false; 
/* 1134 */             if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1135 */               return false; 
/* 1136 */             Double2ShortRBTreeMap.Entry f = Double2ShortRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1137 */             if (f == null || f.getShortValue() != ((Short)e.getValue()).shortValue())
/* 1138 */               return false; 
/* 1139 */             Double2ShortRBTreeMap.this.remove(f.key);
/* 1140 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1144 */             return Double2ShortRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1148 */             Double2ShortRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Double2ShortMap.Entry first() {
/* 1152 */             return Double2ShortRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Double2ShortMap.Entry last() {
/* 1156 */             return Double2ShortRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Double2ShortMap.Entry> subSet(Double2ShortMap.Entry from, Double2ShortMap.Entry to) {
/* 1161 */             return Double2ShortRBTreeMap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2ShortEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Double2ShortMap.Entry> headSet(Double2ShortMap.Entry to) {
/* 1165 */             return Double2ShortRBTreeMap.this.headMap(to.getDoubleKey()).double2ShortEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Double2ShortMap.Entry> tailSet(Double2ShortMap.Entry from) {
/* 1169 */             return Double2ShortRBTreeMap.this.tailMap(from.getDoubleKey()).double2ShortEntrySet();
/*      */           }
/*      */         }; 
/* 1172 */     return this.entries;
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
/* 1188 */       super(k);
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/* 1192 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1196 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractDouble2ShortSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public DoubleBidirectionalIterator iterator() {
/* 1203 */       return new Double2ShortRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public DoubleBidirectionalIterator iterator(double from) {
/* 1207 */       return new Double2ShortRBTreeMap.KeyIterator(from);
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
/* 1222 */     if (this.keys == null)
/* 1223 */       this.keys = new KeySet(); 
/* 1224 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements ShortListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public short nextShort() {
/* 1239 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public short previousShort() {
/* 1243 */       return (previousEntry()).value;
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
/*      */   public ShortCollection values() {
/* 1258 */     if (this.values == null)
/* 1259 */       this.values = (ShortCollection)new AbstractShortCollection()
/*      */         {
/*      */           public ShortIterator iterator() {
/* 1262 */             return (ShortIterator)new Double2ShortRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(short k) {
/* 1266 */             return Double2ShortRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1270 */             return Double2ShortRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1274 */             Double2ShortRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1277 */     return this.values;
/*      */   }
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1281 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Double2ShortSortedMap headMap(double to) {
/* 1285 */     return new Submap(0.0D, true, to, false);
/*      */   }
/*      */   
/*      */   public Double2ShortSortedMap tailMap(double from) {
/* 1289 */     return new Submap(from, false, 0.0D, true);
/*      */   }
/*      */   
/*      */   public Double2ShortSortedMap subMap(double from, double to) {
/* 1293 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractDouble2ShortSortedMap
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
/*      */     protected transient ObjectSortedSet<Double2ShortMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient DoubleSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ShortCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(double from, boolean bottom, double to, boolean top) {
/* 1337 */       if (!bottom && !top && Double2ShortRBTreeMap.this.compare(from, to) > 0)
/* 1338 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1339 */       this.from = from;
/* 1340 */       this.bottom = bottom;
/* 1341 */       this.to = to;
/* 1342 */       this.top = top;
/* 1343 */       this.defRetValue = Double2ShortRBTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1347 */       SubmapIterator i = new SubmapIterator();
/* 1348 */       while (i.hasNext()) {
/* 1349 */         i.nextEntry();
/* 1350 */         i.remove();
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
/* 1361 */       return ((this.bottom || Double2ShortRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Double2ShortRBTreeMap.this
/* 1362 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2ShortMap.Entry> double2ShortEntrySet() {
/* 1366 */       if (this.entries == null)
/* 1367 */         this.entries = (ObjectSortedSet<Double2ShortMap.Entry>)new AbstractObjectSortedSet<Double2ShortMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Double2ShortMap.Entry> iterator() {
/* 1370 */               return (ObjectBidirectionalIterator<Double2ShortMap.Entry>)new Double2ShortRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Double2ShortMap.Entry> iterator(Double2ShortMap.Entry from) {
/* 1375 */               return (ObjectBidirectionalIterator<Double2ShortMap.Entry>)new Double2ShortRBTreeMap.Submap.SubmapEntryIterator(from.getDoubleKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Double2ShortMap.Entry> comparator() {
/* 1379 */               return Double2ShortRBTreeMap.this.double2ShortEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1384 */               if (!(o instanceof Map.Entry))
/* 1385 */                 return false; 
/* 1386 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1387 */               if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1388 */                 return false; 
/* 1389 */               if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1390 */                 return false; 
/* 1391 */               Double2ShortRBTreeMap.Entry f = Double2ShortRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1392 */               return (f != null && Double2ShortRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1397 */               if (!(o instanceof Map.Entry))
/* 1398 */                 return false; 
/* 1399 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1400 */               if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1401 */                 return false; 
/* 1402 */               if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1403 */                 return false; 
/* 1404 */               Double2ShortRBTreeMap.Entry f = Double2ShortRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1405 */               if (f != null && Double2ShortRBTreeMap.Submap.this.in(f.key))
/* 1406 */                 Double2ShortRBTreeMap.Submap.this.remove(f.key); 
/* 1407 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1411 */               int c = 0;
/* 1412 */               for (ObjectBidirectionalIterator<Double2ShortMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1413 */                 c++; 
/* 1414 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1418 */               return !(new Double2ShortRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1422 */               Double2ShortRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Double2ShortMap.Entry first() {
/* 1426 */               return Double2ShortRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Double2ShortMap.Entry last() {
/* 1430 */               return Double2ShortRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Double2ShortMap.Entry> subSet(Double2ShortMap.Entry from, Double2ShortMap.Entry to) {
/* 1435 */               return Double2ShortRBTreeMap.Submap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2ShortEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Double2ShortMap.Entry> headSet(Double2ShortMap.Entry to) {
/* 1439 */               return Double2ShortRBTreeMap.Submap.this.headMap(to.getDoubleKey()).double2ShortEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Double2ShortMap.Entry> tailSet(Double2ShortMap.Entry from) {
/* 1443 */               return Double2ShortRBTreeMap.Submap.this.tailMap(from.getDoubleKey()).double2ShortEntrySet();
/*      */             }
/*      */           }; 
/* 1446 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractDouble2ShortSortedMap.KeySet {
/*      */       public DoubleBidirectionalIterator iterator() {
/* 1451 */         return new Double2ShortRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public DoubleBidirectionalIterator iterator(double from) {
/* 1455 */         return new Double2ShortRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public DoubleSortedSet keySet() {
/* 1460 */       if (this.keys == null)
/* 1461 */         this.keys = new KeySet(); 
/* 1462 */       return this.keys;
/*      */     }
/*      */     
/*      */     public ShortCollection values() {
/* 1466 */       if (this.values == null)
/* 1467 */         this.values = (ShortCollection)new AbstractShortCollection()
/*      */           {
/*      */             public ShortIterator iterator() {
/* 1470 */               return (ShortIterator)new Double2ShortRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(short k) {
/* 1474 */               return Double2ShortRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1478 */               return Double2ShortRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1482 */               Double2ShortRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1485 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(double k) {
/* 1490 */       return (in(k) && Double2ShortRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(short v) {
/* 1494 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1496 */       while (i.hasNext()) {
/* 1497 */         short ev = (i.nextEntry()).value;
/* 1498 */         if (ev == v)
/* 1499 */           return true; 
/*      */       } 
/* 1501 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public short get(double k) {
/* 1507 */       double kk = k; Double2ShortRBTreeMap.Entry e;
/* 1508 */       return (in(kk) && (e = Double2ShortRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public short put(double k, short v) {
/* 1512 */       Double2ShortRBTreeMap.this.modified = false;
/* 1513 */       if (!in(k))
/* 1514 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1515 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1516 */       short oldValue = Double2ShortRBTreeMap.this.put(k, v);
/* 1517 */       return Double2ShortRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public short remove(double k) {
/* 1522 */       Double2ShortRBTreeMap.this.modified = false;
/* 1523 */       if (!in(k))
/* 1524 */         return this.defRetValue; 
/* 1525 */       short oldValue = Double2ShortRBTreeMap.this.remove(k);
/* 1526 */       return Double2ShortRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1530 */       SubmapIterator i = new SubmapIterator();
/* 1531 */       int n = 0;
/* 1532 */       while (i.hasNext()) {
/* 1533 */         n++;
/* 1534 */         i.nextEntry();
/*      */       } 
/* 1536 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1540 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1544 */       return Double2ShortRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Double2ShortSortedMap headMap(double to) {
/* 1548 */       if (this.top)
/* 1549 */         return new Submap(this.from, this.bottom, to, false); 
/* 1550 */       return (Double2ShortRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Double2ShortSortedMap tailMap(double from) {
/* 1554 */       if (this.bottom)
/* 1555 */         return new Submap(from, false, this.to, this.top); 
/* 1556 */       return (Double2ShortRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Double2ShortSortedMap subMap(double from, double to) {
/* 1560 */       if (this.top && this.bottom)
/* 1561 */         return new Submap(from, false, to, false); 
/* 1562 */       if (!this.top)
/* 1563 */         to = (Double2ShortRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1564 */       if (!this.bottom)
/* 1565 */         from = (Double2ShortRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1566 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1567 */         return this; 
/* 1568 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2ShortRBTreeMap.Entry firstEntry() {
/*      */       Double2ShortRBTreeMap.Entry e;
/* 1577 */       if (Double2ShortRBTreeMap.this.tree == null) {
/* 1578 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1582 */       if (this.bottom) {
/* 1583 */         e = Double2ShortRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1585 */         e = Double2ShortRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1587 */         if (Double2ShortRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1588 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1592 */       if (e == null || (!this.top && Double2ShortRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1593 */         return null; 
/* 1594 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2ShortRBTreeMap.Entry lastEntry() {
/*      */       Double2ShortRBTreeMap.Entry e;
/* 1603 */       if (Double2ShortRBTreeMap.this.tree == null) {
/* 1604 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1608 */       if (this.top) {
/* 1609 */         e = Double2ShortRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1611 */         e = Double2ShortRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1613 */         if (Double2ShortRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1614 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1618 */       if (e == null || (!this.bottom && Double2ShortRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1619 */         return null; 
/* 1620 */       return e;
/*      */     }
/*      */     
/*      */     public double firstDoubleKey() {
/* 1624 */       Double2ShortRBTreeMap.Entry e = firstEntry();
/* 1625 */       if (e == null)
/* 1626 */         throw new NoSuchElementException(); 
/* 1627 */       return e.key;
/*      */     }
/*      */     
/*      */     public double lastDoubleKey() {
/* 1631 */       Double2ShortRBTreeMap.Entry e = lastEntry();
/* 1632 */       if (e == null)
/* 1633 */         throw new NoSuchElementException(); 
/* 1634 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Double2ShortRBTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1647 */         this.next = Double2ShortRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(double k) {
/* 1650 */         this();
/* 1651 */         if (this.next != null)
/* 1652 */           if (!Double2ShortRBTreeMap.Submap.this.bottom && Double2ShortRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1653 */             this.prev = null;
/* 1654 */           } else if (!Double2ShortRBTreeMap.Submap.this.top && Double2ShortRBTreeMap.this.compare(k, (this.prev = Double2ShortRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1655 */             this.next = null;
/*      */           } else {
/* 1657 */             this.next = Double2ShortRBTreeMap.this.locateKey(k);
/* 1658 */             if (Double2ShortRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1659 */               this.prev = this.next;
/* 1660 */               this.next = this.next.next();
/*      */             } else {
/* 1662 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1668 */         this.prev = this.prev.prev();
/* 1669 */         if (!Double2ShortRBTreeMap.Submap.this.bottom && this.prev != null && Double2ShortRBTreeMap.this.compare(this.prev.key, Double2ShortRBTreeMap.Submap.this.from) < 0)
/* 1670 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1674 */         this.next = this.next.next();
/* 1675 */         if (!Double2ShortRBTreeMap.Submap.this.top && this.next != null && Double2ShortRBTreeMap.this.compare(this.next.key, Double2ShortRBTreeMap.Submap.this.to) >= 0)
/* 1676 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Double2ShortMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(double k) {
/* 1683 */         super(k);
/*      */       }
/*      */       
/*      */       public Double2ShortMap.Entry next() {
/* 1687 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Double2ShortMap.Entry previous() {
/* 1691 */         return previousEntry();
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
/* 1709 */         super(from);
/*      */       }
/*      */       
/*      */       public double nextDouble() {
/* 1713 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public double previousDouble() {
/* 1717 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements ShortListIterator
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public short nextShort() {
/* 1733 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public short previousShort() {
/* 1737 */         return (previousEntry()).value;
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
/*      */   public Double2ShortRBTreeMap clone() {
/*      */     Double2ShortRBTreeMap c;
/*      */     try {
/* 1756 */       c = (Double2ShortRBTreeMap)super.clone();
/* 1757 */     } catch (CloneNotSupportedException cantHappen) {
/* 1758 */       throw new InternalError();
/*      */     } 
/* 1760 */     c.keys = null;
/* 1761 */     c.values = null;
/* 1762 */     c.entries = null;
/* 1763 */     c.allocatePaths();
/* 1764 */     if (this.count != 0) {
/*      */       
/* 1766 */       Entry rp = new Entry(), rq = new Entry();
/* 1767 */       Entry p = rp;
/* 1768 */       rp.left(this.tree);
/* 1769 */       Entry q = rq;
/* 1770 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1772 */         if (!p.pred()) {
/* 1773 */           Entry e = p.left.clone();
/* 1774 */           e.pred(q.left);
/* 1775 */           e.succ(q);
/* 1776 */           q.left(e);
/* 1777 */           p = p.left;
/* 1778 */           q = q.left;
/*      */         } else {
/* 1780 */           while (p.succ()) {
/* 1781 */             p = p.right;
/* 1782 */             if (p == null) {
/* 1783 */               q.right = null;
/* 1784 */               c.tree = rq.left;
/* 1785 */               c.firstEntry = c.tree;
/* 1786 */               while (c.firstEntry.left != null)
/* 1787 */                 c.firstEntry = c.firstEntry.left; 
/* 1788 */               c.lastEntry = c.tree;
/* 1789 */               while (c.lastEntry.right != null)
/* 1790 */                 c.lastEntry = c.lastEntry.right; 
/* 1791 */               return c;
/*      */             } 
/* 1793 */             q = q.right;
/*      */           } 
/* 1795 */           p = p.right;
/* 1796 */           q = q.right;
/*      */         } 
/* 1798 */         if (!p.succ()) {
/* 1799 */           Entry e = p.right.clone();
/* 1800 */           e.succ(q.right);
/* 1801 */           e.pred(q);
/* 1802 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1806 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1809 */     int n = this.count;
/* 1810 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1812 */     s.defaultWriteObject();
/* 1813 */     while (n-- != 0) {
/* 1814 */       Entry e = i.nextEntry();
/* 1815 */       s.writeDouble(e.key);
/* 1816 */       s.writeShort(e.value);
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
/* 1837 */     if (n == 1) {
/* 1838 */       Entry entry = new Entry(s.readDouble(), s.readShort());
/* 1839 */       entry.pred(pred);
/* 1840 */       entry.succ(succ);
/* 1841 */       entry.black(true);
/* 1842 */       return entry;
/*      */     } 
/* 1844 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1849 */       Entry entry = new Entry(s.readDouble(), s.readShort());
/* 1850 */       entry.black(true);
/* 1851 */       entry.right(new Entry(s.readDouble(), s.readShort()));
/* 1852 */       entry.right.pred(entry);
/* 1853 */       entry.pred(pred);
/* 1854 */       entry.right.succ(succ);
/* 1855 */       return entry;
/*      */     } 
/*      */     
/* 1858 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1859 */     Entry top = new Entry();
/* 1860 */     top.left(readTree(s, leftN, pred, top));
/* 1861 */     top.key = s.readDouble();
/* 1862 */     top.value = s.readShort();
/* 1863 */     top.black(true);
/* 1864 */     top.right(readTree(s, rightN, top, succ));
/* 1865 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1866 */       top.right.black(false); 
/* 1867 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1870 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1875 */     setActualComparator();
/* 1876 */     allocatePaths();
/* 1877 */     if (this.count != 0) {
/* 1878 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1880 */       Entry e = this.tree;
/* 1881 */       while (e.left() != null)
/* 1882 */         e = e.left(); 
/* 1883 */       this.firstEntry = e;
/* 1884 */       e = this.tree;
/* 1885 */       while (e.right() != null)
/* 1886 */         e = e.right(); 
/* 1887 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2ShortRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */