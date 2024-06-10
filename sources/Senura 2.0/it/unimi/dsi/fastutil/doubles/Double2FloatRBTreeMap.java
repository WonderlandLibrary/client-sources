/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*      */ import it.unimi.dsi.fastutil.floats.FloatListIterator;
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
/*      */ public class Double2FloatRBTreeMap
/*      */   extends AbstractDouble2FloatSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Double2FloatMap.Entry> entries;
/*      */   protected transient DoubleSortedSet keys;
/*      */   protected transient FloatCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Double> storedComparator;
/*      */   protected transient DoubleComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public Double2FloatRBTreeMap() {
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
/*      */   public Double2FloatRBTreeMap(Comparator<? super Double> c) {
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
/*      */   public Double2FloatRBTreeMap(Map<? extends Double, ? extends Float> m) {
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
/*      */   public Double2FloatRBTreeMap(SortedMap<Double, Float> m) {
/*  122 */     this(m.comparator());
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2FloatRBTreeMap(Double2FloatMap m) {
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
/*      */   public Double2FloatRBTreeMap(Double2FloatSortedMap m) {
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
/*      */   public Double2FloatRBTreeMap(double[] k, float[] v, Comparator<? super Double> c) {
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
/*      */   public Double2FloatRBTreeMap(double[] k, float[] v) {
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
/*      */   public float addTo(double k, float incr) {
/*  270 */     Entry e = add(k);
/*  271 */     float oldValue = e.value;
/*  272 */     e.value += incr;
/*  273 */     return oldValue;
/*      */   }
/*      */   
/*      */   public float put(double k, float v) {
/*  277 */     Entry e = add(k);
/*  278 */     float oldValue = e.value;
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
/*      */   public float remove(double k) {
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
/*      */   public boolean containsValue(float v) {
/*  687 */     ValueIterator i = new ValueIterator();
/*      */     
/*  689 */     int j = this.count;
/*  690 */     while (j-- != 0) {
/*  691 */       float ev = i.nextFloat();
/*  692 */       if (Float.floatToIntBits(ev) == Float.floatToIntBits(v))
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
/*      */     extends AbstractDouble2FloatMap.BasicEntry
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
/*  732 */       super(0.0D, 0.0F);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(double k, float v) {
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
/*      */     public float setValue(float value) {
/*  888 */       float oldValue = this.value;
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
/*  911 */       Map.Entry<Double, Float> e = (Map.Entry<Double, Float>)o;
/*  912 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && 
/*  913 */         Float.floatToIntBits(this.value) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  917 */       return HashCommon.double2int(this.key) ^ HashCommon.float2int(this.value);
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
/*      */   public float get(double k) {
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
/*      */     Double2FloatRBTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2FloatRBTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2FloatRBTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  999 */     int index = 0;
/*      */     TreeIterator() {
/* 1001 */       this.next = Double2FloatRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(double k) {
/* 1004 */       if ((this.next = Double2FloatRBTreeMap.this.locateKey(k)) != null)
/* 1005 */         if (Double2FloatRBTreeMap.this.compare(this.next.key, k) <= 0) {
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
/*      */     Double2FloatRBTreeMap.Entry nextEntry() {
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
/*      */     Double2FloatRBTreeMap.Entry previousEntry() {
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
/* 1058 */       Double2FloatRBTreeMap.this.remove(this.curr.key);
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
/*      */     implements ObjectListIterator<Double2FloatMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(double k) {
/* 1084 */       super(k);
/*      */     }
/*      */     
/*      */     public Double2FloatMap.Entry next() {
/* 1088 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Double2FloatMap.Entry previous() {
/* 1092 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Double2FloatMap.Entry> double2FloatEntrySet() {
/* 1097 */     if (this.entries == null)
/* 1098 */       this.entries = (ObjectSortedSet<Double2FloatMap.Entry>)new AbstractObjectSortedSet<Double2FloatMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Double2FloatMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Double2FloatMap.Entry> comparator() {
/* 1103 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2FloatMap.Entry> iterator() {
/* 1107 */             return (ObjectBidirectionalIterator<Double2FloatMap.Entry>)new Double2FloatRBTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2FloatMap.Entry> iterator(Double2FloatMap.Entry from) {
/* 1111 */             return (ObjectBidirectionalIterator<Double2FloatMap.Entry>)new Double2FloatRBTreeMap.EntryIterator(from.getDoubleKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1116 */             if (!(o instanceof Map.Entry))
/* 1117 */               return false; 
/* 1118 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1119 */             if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1120 */               return false; 
/* 1121 */             if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1122 */               return false; 
/* 1123 */             Double2FloatRBTreeMap.Entry f = Double2FloatRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
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
/* 1134 */             if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1135 */               return false; 
/* 1136 */             Double2FloatRBTreeMap.Entry f = Double2FloatRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1137 */             if (f == null || Float.floatToIntBits(f.getFloatValue()) != 
/* 1138 */               Float.floatToIntBits(((Float)e.getValue()).floatValue()))
/* 1139 */               return false; 
/* 1140 */             Double2FloatRBTreeMap.this.remove(f.key);
/* 1141 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1145 */             return Double2FloatRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1149 */             Double2FloatRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Double2FloatMap.Entry first() {
/* 1153 */             return Double2FloatRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Double2FloatMap.Entry last() {
/* 1157 */             return Double2FloatRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Double2FloatMap.Entry> subSet(Double2FloatMap.Entry from, Double2FloatMap.Entry to) {
/* 1162 */             return Double2FloatRBTreeMap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2FloatEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Double2FloatMap.Entry> headSet(Double2FloatMap.Entry to) {
/* 1166 */             return Double2FloatRBTreeMap.this.headMap(to.getDoubleKey()).double2FloatEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Double2FloatMap.Entry> tailSet(Double2FloatMap.Entry from) {
/* 1170 */             return Double2FloatRBTreeMap.this.tailMap(from.getDoubleKey()).double2FloatEntrySet();
/*      */           }
/*      */         }; 
/* 1173 */     return this.entries;
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
/* 1189 */       super(k);
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/* 1193 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1197 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractDouble2FloatSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public DoubleBidirectionalIterator iterator() {
/* 1204 */       return new Double2FloatRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public DoubleBidirectionalIterator iterator(double from) {
/* 1208 */       return new Double2FloatRBTreeMap.KeyIterator(from);
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
/* 1223 */     if (this.keys == null)
/* 1224 */       this.keys = new KeySet(); 
/* 1225 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements FloatListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1240 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public float previousFloat() {
/* 1244 */       return (previousEntry()).value;
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
/*      */   public FloatCollection values() {
/* 1259 */     if (this.values == null)
/* 1260 */       this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1263 */             return (FloatIterator)new Double2FloatRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(float k) {
/* 1267 */             return Double2FloatRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1271 */             return Double2FloatRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1275 */             Double2FloatRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1278 */     return this.values;
/*      */   }
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1282 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Double2FloatSortedMap headMap(double to) {
/* 1286 */     return new Submap(0.0D, true, to, false);
/*      */   }
/*      */   
/*      */   public Double2FloatSortedMap tailMap(double from) {
/* 1290 */     return new Submap(from, false, 0.0D, true);
/*      */   }
/*      */   
/*      */   public Double2FloatSortedMap subMap(double from, double to) {
/* 1294 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractDouble2FloatSortedMap
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
/*      */     protected transient ObjectSortedSet<Double2FloatMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient DoubleSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient FloatCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(double from, boolean bottom, double to, boolean top) {
/* 1338 */       if (!bottom && !top && Double2FloatRBTreeMap.this.compare(from, to) > 0)
/* 1339 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1340 */       this.from = from;
/* 1341 */       this.bottom = bottom;
/* 1342 */       this.to = to;
/* 1343 */       this.top = top;
/* 1344 */       this.defRetValue = Double2FloatRBTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1348 */       SubmapIterator i = new SubmapIterator();
/* 1349 */       while (i.hasNext()) {
/* 1350 */         i.nextEntry();
/* 1351 */         i.remove();
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
/* 1362 */       return ((this.bottom || Double2FloatRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Double2FloatRBTreeMap.this
/* 1363 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2FloatMap.Entry> double2FloatEntrySet() {
/* 1367 */       if (this.entries == null)
/* 1368 */         this.entries = (ObjectSortedSet<Double2FloatMap.Entry>)new AbstractObjectSortedSet<Double2FloatMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Double2FloatMap.Entry> iterator() {
/* 1371 */               return (ObjectBidirectionalIterator<Double2FloatMap.Entry>)new Double2FloatRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Double2FloatMap.Entry> iterator(Double2FloatMap.Entry from) {
/* 1376 */               return (ObjectBidirectionalIterator<Double2FloatMap.Entry>)new Double2FloatRBTreeMap.Submap.SubmapEntryIterator(from.getDoubleKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Double2FloatMap.Entry> comparator() {
/* 1380 */               return Double2FloatRBTreeMap.this.double2FloatEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1385 */               if (!(o instanceof Map.Entry))
/* 1386 */                 return false; 
/* 1387 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1388 */               if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1389 */                 return false; 
/* 1390 */               if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1391 */                 return false; 
/* 1392 */               Double2FloatRBTreeMap.Entry f = Double2FloatRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1393 */               return (f != null && Double2FloatRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1398 */               if (!(o instanceof Map.Entry))
/* 1399 */                 return false; 
/* 1400 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1401 */               if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1402 */                 return false; 
/* 1403 */               if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1404 */                 return false; 
/* 1405 */               Double2FloatRBTreeMap.Entry f = Double2FloatRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1406 */               if (f != null && Double2FloatRBTreeMap.Submap.this.in(f.key))
/* 1407 */                 Double2FloatRBTreeMap.Submap.this.remove(f.key); 
/* 1408 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1412 */               int c = 0;
/* 1413 */               for (ObjectBidirectionalIterator<Double2FloatMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1414 */                 c++; 
/* 1415 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1419 */               return !(new Double2FloatRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1423 */               Double2FloatRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Double2FloatMap.Entry first() {
/* 1427 */               return Double2FloatRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Double2FloatMap.Entry last() {
/* 1431 */               return Double2FloatRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Double2FloatMap.Entry> subSet(Double2FloatMap.Entry from, Double2FloatMap.Entry to) {
/* 1436 */               return Double2FloatRBTreeMap.Submap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2FloatEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Double2FloatMap.Entry> headSet(Double2FloatMap.Entry to) {
/* 1440 */               return Double2FloatRBTreeMap.Submap.this.headMap(to.getDoubleKey()).double2FloatEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Double2FloatMap.Entry> tailSet(Double2FloatMap.Entry from) {
/* 1444 */               return Double2FloatRBTreeMap.Submap.this.tailMap(from.getDoubleKey()).double2FloatEntrySet();
/*      */             }
/*      */           }; 
/* 1447 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractDouble2FloatSortedMap.KeySet {
/*      */       public DoubleBidirectionalIterator iterator() {
/* 1452 */         return new Double2FloatRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public DoubleBidirectionalIterator iterator(double from) {
/* 1456 */         return new Double2FloatRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public DoubleSortedSet keySet() {
/* 1461 */       if (this.keys == null)
/* 1462 */         this.keys = new KeySet(); 
/* 1463 */       return this.keys;
/*      */     }
/*      */     
/*      */     public FloatCollection values() {
/* 1467 */       if (this.values == null)
/* 1468 */         this.values = (FloatCollection)new AbstractFloatCollection()
/*      */           {
/*      */             public FloatIterator iterator() {
/* 1471 */               return (FloatIterator)new Double2FloatRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(float k) {
/* 1475 */               return Double2FloatRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1479 */               return Double2FloatRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1483 */               Double2FloatRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1486 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(double k) {
/* 1491 */       return (in(k) && Double2FloatRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(float v) {
/* 1495 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1497 */       while (i.hasNext()) {
/* 1498 */         float ev = (i.nextEntry()).value;
/* 1499 */         if (Float.floatToIntBits(ev) == Float.floatToIntBits(v))
/* 1500 */           return true; 
/*      */       } 
/* 1502 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public float get(double k) {
/* 1508 */       double kk = k; Double2FloatRBTreeMap.Entry e;
/* 1509 */       return (in(kk) && (e = Double2FloatRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public float put(double k, float v) {
/* 1513 */       Double2FloatRBTreeMap.this.modified = false;
/* 1514 */       if (!in(k))
/* 1515 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1516 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1517 */       float oldValue = Double2FloatRBTreeMap.this.put(k, v);
/* 1518 */       return Double2FloatRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public float remove(double k) {
/* 1523 */       Double2FloatRBTreeMap.this.modified = false;
/* 1524 */       if (!in(k))
/* 1525 */         return this.defRetValue; 
/* 1526 */       float oldValue = Double2FloatRBTreeMap.this.remove(k);
/* 1527 */       return Double2FloatRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1531 */       SubmapIterator i = new SubmapIterator();
/* 1532 */       int n = 0;
/* 1533 */       while (i.hasNext()) {
/* 1534 */         n++;
/* 1535 */         i.nextEntry();
/*      */       } 
/* 1537 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1541 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1545 */       return Double2FloatRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Double2FloatSortedMap headMap(double to) {
/* 1549 */       if (this.top)
/* 1550 */         return new Submap(this.from, this.bottom, to, false); 
/* 1551 */       return (Double2FloatRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Double2FloatSortedMap tailMap(double from) {
/* 1555 */       if (this.bottom)
/* 1556 */         return new Submap(from, false, this.to, this.top); 
/* 1557 */       return (Double2FloatRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Double2FloatSortedMap subMap(double from, double to) {
/* 1561 */       if (this.top && this.bottom)
/* 1562 */         return new Submap(from, false, to, false); 
/* 1563 */       if (!this.top)
/* 1564 */         to = (Double2FloatRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1565 */       if (!this.bottom)
/* 1566 */         from = (Double2FloatRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1567 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1568 */         return this; 
/* 1569 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2FloatRBTreeMap.Entry firstEntry() {
/*      */       Double2FloatRBTreeMap.Entry e;
/* 1578 */       if (Double2FloatRBTreeMap.this.tree == null) {
/* 1579 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1583 */       if (this.bottom) {
/* 1584 */         e = Double2FloatRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1586 */         e = Double2FloatRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1588 */         if (Double2FloatRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1589 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1593 */       if (e == null || (!this.top && Double2FloatRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1594 */         return null; 
/* 1595 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2FloatRBTreeMap.Entry lastEntry() {
/*      */       Double2FloatRBTreeMap.Entry e;
/* 1604 */       if (Double2FloatRBTreeMap.this.tree == null) {
/* 1605 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1609 */       if (this.top) {
/* 1610 */         e = Double2FloatRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1612 */         e = Double2FloatRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1614 */         if (Double2FloatRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1615 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1619 */       if (e == null || (!this.bottom && Double2FloatRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1620 */         return null; 
/* 1621 */       return e;
/*      */     }
/*      */     
/*      */     public double firstDoubleKey() {
/* 1625 */       Double2FloatRBTreeMap.Entry e = firstEntry();
/* 1626 */       if (e == null)
/* 1627 */         throw new NoSuchElementException(); 
/* 1628 */       return e.key;
/*      */     }
/*      */     
/*      */     public double lastDoubleKey() {
/* 1632 */       Double2FloatRBTreeMap.Entry e = lastEntry();
/* 1633 */       if (e == null)
/* 1634 */         throw new NoSuchElementException(); 
/* 1635 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Double2FloatRBTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1648 */         this.next = Double2FloatRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(double k) {
/* 1651 */         this();
/* 1652 */         if (this.next != null)
/* 1653 */           if (!Double2FloatRBTreeMap.Submap.this.bottom && Double2FloatRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1654 */             this.prev = null;
/* 1655 */           } else if (!Double2FloatRBTreeMap.Submap.this.top && Double2FloatRBTreeMap.this.compare(k, (this.prev = Double2FloatRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1656 */             this.next = null;
/*      */           } else {
/* 1658 */             this.next = Double2FloatRBTreeMap.this.locateKey(k);
/* 1659 */             if (Double2FloatRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1660 */               this.prev = this.next;
/* 1661 */               this.next = this.next.next();
/*      */             } else {
/* 1663 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1669 */         this.prev = this.prev.prev();
/* 1670 */         if (!Double2FloatRBTreeMap.Submap.this.bottom && this.prev != null && Double2FloatRBTreeMap.this.compare(this.prev.key, Double2FloatRBTreeMap.Submap.this.from) < 0)
/* 1671 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1675 */         this.next = this.next.next();
/* 1676 */         if (!Double2FloatRBTreeMap.Submap.this.top && this.next != null && Double2FloatRBTreeMap.this.compare(this.next.key, Double2FloatRBTreeMap.Submap.this.to) >= 0)
/* 1677 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Double2FloatMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(double k) {
/* 1684 */         super(k);
/*      */       }
/*      */       
/*      */       public Double2FloatMap.Entry next() {
/* 1688 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Double2FloatMap.Entry previous() {
/* 1692 */         return previousEntry();
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
/* 1710 */         super(from);
/*      */       }
/*      */       
/*      */       public double nextDouble() {
/* 1714 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public double previousDouble() {
/* 1718 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements FloatListIterator
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public float nextFloat() {
/* 1734 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public float previousFloat() {
/* 1738 */         return (previousEntry()).value;
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
/*      */   public Double2FloatRBTreeMap clone() {
/*      */     Double2FloatRBTreeMap c;
/*      */     try {
/* 1757 */       c = (Double2FloatRBTreeMap)super.clone();
/* 1758 */     } catch (CloneNotSupportedException cantHappen) {
/* 1759 */       throw new InternalError();
/*      */     } 
/* 1761 */     c.keys = null;
/* 1762 */     c.values = null;
/* 1763 */     c.entries = null;
/* 1764 */     c.allocatePaths();
/* 1765 */     if (this.count != 0) {
/*      */       
/* 1767 */       Entry rp = new Entry(), rq = new Entry();
/* 1768 */       Entry p = rp;
/* 1769 */       rp.left(this.tree);
/* 1770 */       Entry q = rq;
/* 1771 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1773 */         if (!p.pred()) {
/* 1774 */           Entry e = p.left.clone();
/* 1775 */           e.pred(q.left);
/* 1776 */           e.succ(q);
/* 1777 */           q.left(e);
/* 1778 */           p = p.left;
/* 1779 */           q = q.left;
/*      */         } else {
/* 1781 */           while (p.succ()) {
/* 1782 */             p = p.right;
/* 1783 */             if (p == null) {
/* 1784 */               q.right = null;
/* 1785 */               c.tree = rq.left;
/* 1786 */               c.firstEntry = c.tree;
/* 1787 */               while (c.firstEntry.left != null)
/* 1788 */                 c.firstEntry = c.firstEntry.left; 
/* 1789 */               c.lastEntry = c.tree;
/* 1790 */               while (c.lastEntry.right != null)
/* 1791 */                 c.lastEntry = c.lastEntry.right; 
/* 1792 */               return c;
/*      */             } 
/* 1794 */             q = q.right;
/*      */           } 
/* 1796 */           p = p.right;
/* 1797 */           q = q.right;
/*      */         } 
/* 1799 */         if (!p.succ()) {
/* 1800 */           Entry e = p.right.clone();
/* 1801 */           e.succ(q.right);
/* 1802 */           e.pred(q);
/* 1803 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1807 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1810 */     int n = this.count;
/* 1811 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1813 */     s.defaultWriteObject();
/* 1814 */     while (n-- != 0) {
/* 1815 */       Entry e = i.nextEntry();
/* 1816 */       s.writeDouble(e.key);
/* 1817 */       s.writeFloat(e.value);
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
/* 1838 */     if (n == 1) {
/* 1839 */       Entry entry = new Entry(s.readDouble(), s.readFloat());
/* 1840 */       entry.pred(pred);
/* 1841 */       entry.succ(succ);
/* 1842 */       entry.black(true);
/* 1843 */       return entry;
/*      */     } 
/* 1845 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1850 */       Entry entry = new Entry(s.readDouble(), s.readFloat());
/* 1851 */       entry.black(true);
/* 1852 */       entry.right(new Entry(s.readDouble(), s.readFloat()));
/* 1853 */       entry.right.pred(entry);
/* 1854 */       entry.pred(pred);
/* 1855 */       entry.right.succ(succ);
/* 1856 */       return entry;
/*      */     } 
/*      */     
/* 1859 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1860 */     Entry top = new Entry();
/* 1861 */     top.left(readTree(s, leftN, pred, top));
/* 1862 */     top.key = s.readDouble();
/* 1863 */     top.value = s.readFloat();
/* 1864 */     top.black(true);
/* 1865 */     top.right(readTree(s, rightN, top, succ));
/* 1866 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1867 */       top.right.black(false); 
/* 1868 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1871 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1876 */     setActualComparator();
/* 1877 */     allocatePaths();
/* 1878 */     if (this.count != 0) {
/* 1879 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1881 */       Entry e = this.tree;
/* 1882 */       while (e.left() != null)
/* 1883 */         e = e.left(); 
/* 1884 */       this.firstEntry = e;
/* 1885 */       e = this.tree;
/* 1886 */       while (e.right() != null)
/* 1887 */         e = e.right(); 
/* 1888 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2FloatRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */