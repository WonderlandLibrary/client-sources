/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
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
/*      */ public class Double2BooleanRBTreeMap
/*      */   extends AbstractDouble2BooleanSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Double2BooleanMap.Entry> entries;
/*      */   protected transient DoubleSortedSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Double> storedComparator;
/*      */   protected transient DoubleComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public Double2BooleanRBTreeMap() {
/*   75 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   81 */     this.tree = null;
/*   82 */     this.count = 0;
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
/*   94 */     this.actualComparator = DoubleComparators.asDoubleComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanRBTreeMap(Comparator<? super Double> c) {
/*  103 */     this();
/*  104 */     this.storedComparator = c;
/*  105 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanRBTreeMap(Map<? extends Double, ? extends Boolean> m) {
/*  114 */     this();
/*  115 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanRBTreeMap(SortedMap<Double, Boolean> m) {
/*  125 */     this(m.comparator());
/*  126 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanRBTreeMap(Double2BooleanMap m) {
/*  135 */     this();
/*  136 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanRBTreeMap(Double2BooleanSortedMap m) {
/*  146 */     this(m.comparator());
/*  147 */     putAll(m);
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
/*      */   public Double2BooleanRBTreeMap(double[] k, boolean[] v, Comparator<? super Double> c) {
/*  163 */     this(c);
/*  164 */     if (k.length != v.length) {
/*  165 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  167 */     for (int i = 0; i < k.length; i++) {
/*  168 */       put(k[i], v[i]);
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
/*      */   public Double2BooleanRBTreeMap(double[] k, boolean[] v) {
/*  181 */     this(k, v, (Comparator<? super Double>)null);
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
/*  209 */     return (this.actualComparator == null) ? Double.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*  221 */     Entry e = this.tree;
/*      */     int cmp;
/*  223 */     while (e != null && (cmp = compare(k, e.key)) != 0)
/*  224 */       e = (cmp < 0) ? e.left() : e.right(); 
/*  225 */     return e;
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
/*  237 */     Entry e = this.tree, last = this.tree;
/*  238 */     int cmp = 0;
/*  239 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  240 */       last = e;
/*  241 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  243 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  253 */     this.dirPath = new boolean[64];
/*  254 */     this.nodePath = new Entry[64];
/*      */   }
/*      */   
/*      */   public boolean put(double k, boolean v) {
/*  258 */     Entry e = add(k);
/*  259 */     boolean oldValue = e.value;
/*  260 */     e.value = v;
/*  261 */     return oldValue;
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
/*  278 */     this.modified = false;
/*  279 */     int maxDepth = 0;
/*      */     
/*  281 */     if (this.tree == null) {
/*  282 */       this.count++;
/*  283 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry(k, this.defRetValue);
/*      */     } else {
/*  285 */       Entry p = this.tree;
/*  286 */       int i = 0; while (true) {
/*      */         int cmp;
/*  288 */         if ((cmp = compare(k, p.key)) == 0) {
/*      */           
/*  290 */           while (i-- != 0)
/*  291 */             this.nodePath[i] = null; 
/*  292 */           return p;
/*      */         } 
/*  294 */         this.nodePath[i] = p;
/*  295 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  296 */           if (p.succ()) {
/*  297 */             this.count++;
/*  298 */             e = new Entry(k, this.defRetValue);
/*  299 */             if (p.right == null)
/*  300 */               this.lastEntry = e; 
/*  301 */             e.left = p;
/*  302 */             e.right = p.right;
/*  303 */             p.right(e);
/*      */             break;
/*      */           } 
/*  306 */           p = p.right; continue;
/*      */         } 
/*  308 */         if (p.pred()) {
/*  309 */           this.count++;
/*  310 */           e = new Entry(k, this.defRetValue);
/*  311 */           if (p.left == null)
/*  312 */             this.firstEntry = e; 
/*  313 */           e.right = p;
/*  314 */           e.left = p.left;
/*  315 */           p.left(e);
/*      */           break;
/*      */         } 
/*  318 */         p = p.left;
/*      */       } 
/*      */       
/*  321 */       this.modified = true;
/*  322 */       maxDepth = i--;
/*  323 */       while (i > 0 && !this.nodePath[i].black()) {
/*  324 */         if (!this.dirPath[i - 1]) {
/*  325 */           Entry entry1 = (this.nodePath[i - 1]).right;
/*  326 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  327 */             this.nodePath[i].black(true);
/*  328 */             entry1.black(true);
/*  329 */             this.nodePath[i - 1].black(false);
/*  330 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  333 */           if (!this.dirPath[i]) {
/*  334 */             entry1 = this.nodePath[i];
/*      */           } else {
/*  336 */             Entry entry = this.nodePath[i];
/*  337 */             entry1 = entry.right;
/*  338 */             entry.right = entry1.left;
/*  339 */             entry1.left = entry;
/*  340 */             (this.nodePath[i - 1]).left = entry1;
/*  341 */             if (entry1.pred()) {
/*  342 */               entry1.pred(false);
/*  343 */               entry.succ(entry1);
/*      */             } 
/*      */           } 
/*  346 */           Entry entry2 = this.nodePath[i - 1];
/*  347 */           entry2.black(false);
/*  348 */           entry1.black(true);
/*  349 */           entry2.left = entry1.right;
/*  350 */           entry1.right = entry2;
/*  351 */           if (i < 2) {
/*  352 */             this.tree = entry1;
/*      */           }
/*  354 */           else if (this.dirPath[i - 2]) {
/*  355 */             (this.nodePath[i - 2]).right = entry1;
/*      */           } else {
/*  357 */             (this.nodePath[i - 2]).left = entry1;
/*      */           } 
/*  359 */           if (entry1.succ()) {
/*  360 */             entry1.succ(false);
/*  361 */             entry2.pred(entry1);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  366 */         Entry y = (this.nodePath[i - 1]).left;
/*  367 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  368 */           this.nodePath[i].black(true);
/*  369 */           y.black(true);
/*  370 */           this.nodePath[i - 1].black(false);
/*  371 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  374 */         if (this.dirPath[i]) {
/*  375 */           y = this.nodePath[i];
/*      */         } else {
/*  377 */           Entry entry = this.nodePath[i];
/*  378 */           y = entry.left;
/*  379 */           entry.left = y.right;
/*  380 */           y.right = entry;
/*  381 */           (this.nodePath[i - 1]).right = y;
/*  382 */           if (y.succ()) {
/*  383 */             y.succ(false);
/*  384 */             entry.pred(y);
/*      */           } 
/*      */         } 
/*  387 */         Entry x = this.nodePath[i - 1];
/*  388 */         x.black(false);
/*  389 */         y.black(true);
/*  390 */         x.right = y.left;
/*  391 */         y.left = x;
/*  392 */         if (i < 2) {
/*  393 */           this.tree = y;
/*      */         }
/*  395 */         else if (this.dirPath[i - 2]) {
/*  396 */           (this.nodePath[i - 2]).right = y;
/*      */         } else {
/*  398 */           (this.nodePath[i - 2]).left = y;
/*      */         } 
/*  400 */         if (y.pred()) {
/*  401 */           y.pred(false);
/*  402 */           x.succ(y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  409 */     this.tree.black(true);
/*      */     
/*  411 */     while (maxDepth-- != 0)
/*  412 */       this.nodePath[maxDepth] = null; 
/*  413 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k) {
/*  422 */     this.modified = false;
/*  423 */     if (this.tree == null)
/*  424 */       return this.defRetValue; 
/*  425 */     Entry p = this.tree;
/*      */     
/*  427 */     int i = 0;
/*  428 */     double kk = k;
/*      */     int cmp;
/*  430 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  432 */       this.dirPath[i] = (cmp > 0);
/*  433 */       this.nodePath[i] = p;
/*  434 */       if (this.dirPath[i++]) {
/*  435 */         if ((p = p.right()) == null) {
/*      */           
/*  437 */           while (i-- != 0)
/*  438 */             this.nodePath[i] = null; 
/*  439 */           return this.defRetValue;
/*      */         }  continue;
/*      */       } 
/*  442 */       if ((p = p.left()) == null) {
/*      */         
/*  444 */         while (i-- != 0)
/*  445 */           this.nodePath[i] = null; 
/*  446 */         return this.defRetValue;
/*      */       } 
/*      */     } 
/*      */     
/*  450 */     if (p.left == null)
/*  451 */       this.firstEntry = p.next(); 
/*  452 */     if (p.right == null)
/*  453 */       this.lastEntry = p.prev(); 
/*  454 */     if (p.succ()) {
/*  455 */       if (p.pred()) {
/*  456 */         if (i == 0) {
/*  457 */           this.tree = p.left;
/*      */         }
/*  459 */         else if (this.dirPath[i - 1]) {
/*  460 */           this.nodePath[i - 1].succ(p.right);
/*      */         } else {
/*  462 */           this.nodePath[i - 1].pred(p.left);
/*      */         } 
/*      */       } else {
/*  465 */         (p.prev()).right = p.right;
/*  466 */         if (i == 0) {
/*  467 */           this.tree = p.left;
/*      */         }
/*  469 */         else if (this.dirPath[i - 1]) {
/*  470 */           (this.nodePath[i - 1]).right = p.left;
/*      */         } else {
/*  472 */           (this.nodePath[i - 1]).left = p.left;
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  477 */       Entry r = p.right;
/*  478 */       if (r.pred()) {
/*  479 */         r.left = p.left;
/*  480 */         r.pred(p.pred());
/*  481 */         if (!r.pred())
/*  482 */           (r.prev()).right = r; 
/*  483 */         if (i == 0) {
/*  484 */           this.tree = r;
/*      */         }
/*  486 */         else if (this.dirPath[i - 1]) {
/*  487 */           (this.nodePath[i - 1]).right = r;
/*      */         } else {
/*  489 */           (this.nodePath[i - 1]).left = r;
/*      */         } 
/*  491 */         boolean color = r.black();
/*  492 */         r.black(p.black());
/*  493 */         p.black(color);
/*  494 */         this.dirPath[i] = true;
/*  495 */         this.nodePath[i++] = r;
/*      */       } else {
/*      */         Entry s;
/*  498 */         int j = i++;
/*      */         while (true) {
/*  500 */           this.dirPath[i] = false;
/*  501 */           this.nodePath[i++] = r;
/*  502 */           s = r.left;
/*  503 */           if (s.pred())
/*      */             break; 
/*  505 */           r = s;
/*      */         } 
/*  507 */         this.dirPath[j] = true;
/*  508 */         this.nodePath[j] = s;
/*  509 */         if (s.succ()) {
/*  510 */           r.pred(s);
/*      */         } else {
/*  512 */           r.left = s.right;
/*  513 */         }  s.left = p.left;
/*  514 */         if (!p.pred()) {
/*  515 */           (p.prev()).right = s;
/*  516 */           s.pred(false);
/*      */         } 
/*  518 */         s.right(p.right);
/*  519 */         boolean color = s.black();
/*  520 */         s.black(p.black());
/*  521 */         p.black(color);
/*  522 */         if (j == 0) {
/*  523 */           this.tree = s;
/*      */         }
/*  525 */         else if (this.dirPath[j - 1]) {
/*  526 */           (this.nodePath[j - 1]).right = s;
/*      */         } else {
/*  528 */           (this.nodePath[j - 1]).left = s;
/*      */         } 
/*      */       } 
/*      */     } 
/*  532 */     int maxDepth = i;
/*  533 */     if (p.black()) {
/*  534 */       for (; i > 0; i--) {
/*  535 */         if ((this.dirPath[i - 1] && !this.nodePath[i - 1].succ()) || (!this.dirPath[i - 1] && !this.nodePath[i - 1].pred())) {
/*  536 */           Entry x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  537 */           if (!x.black()) {
/*  538 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  542 */         if (!this.dirPath[i - 1]) {
/*  543 */           Entry w = (this.nodePath[i - 1]).right;
/*  544 */           if (!w.black()) {
/*  545 */             w.black(true);
/*  546 */             this.nodePath[i - 1].black(false);
/*  547 */             (this.nodePath[i - 1]).right = w.left;
/*  548 */             w.left = this.nodePath[i - 1];
/*  549 */             if (i < 2) {
/*  550 */               this.tree = w;
/*      */             }
/*  552 */             else if (this.dirPath[i - 2]) {
/*  553 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  555 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  557 */             this.nodePath[i] = this.nodePath[i - 1];
/*  558 */             this.dirPath[i] = false;
/*  559 */             this.nodePath[i - 1] = w;
/*  560 */             if (maxDepth == i++)
/*  561 */               maxDepth++; 
/*  562 */             w = (this.nodePath[i - 1]).right;
/*      */           } 
/*  564 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  565 */             w.black(false);
/*      */           } else {
/*  567 */             if (w.succ() || w.right.black()) {
/*  568 */               Entry y = w.left;
/*  569 */               y.black(true);
/*  570 */               w.black(false);
/*  571 */               w.left = y.right;
/*  572 */               y.right = w;
/*  573 */               w = (this.nodePath[i - 1]).right = y;
/*  574 */               if (w.succ()) {
/*  575 */                 w.succ(false);
/*  576 */                 w.right.pred(w);
/*      */               } 
/*      */             } 
/*  579 */             w.black(this.nodePath[i - 1].black());
/*  580 */             this.nodePath[i - 1].black(true);
/*  581 */             w.right.black(true);
/*  582 */             (this.nodePath[i - 1]).right = w.left;
/*  583 */             w.left = this.nodePath[i - 1];
/*  584 */             if (i < 2) {
/*  585 */               this.tree = w;
/*      */             }
/*  587 */             else if (this.dirPath[i - 2]) {
/*  588 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  590 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  592 */             if (w.pred()) {
/*  593 */               w.pred(false);
/*  594 */               this.nodePath[i - 1].succ(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } else {
/*  599 */           Entry w = (this.nodePath[i - 1]).left;
/*  600 */           if (!w.black()) {
/*  601 */             w.black(true);
/*  602 */             this.nodePath[i - 1].black(false);
/*  603 */             (this.nodePath[i - 1]).left = w.right;
/*  604 */             w.right = this.nodePath[i - 1];
/*  605 */             if (i < 2) {
/*  606 */               this.tree = w;
/*      */             }
/*  608 */             else if (this.dirPath[i - 2]) {
/*  609 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  611 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  613 */             this.nodePath[i] = this.nodePath[i - 1];
/*  614 */             this.dirPath[i] = true;
/*  615 */             this.nodePath[i - 1] = w;
/*  616 */             if (maxDepth == i++)
/*  617 */               maxDepth++; 
/*  618 */             w = (this.nodePath[i - 1]).left;
/*      */           } 
/*  620 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  621 */             w.black(false);
/*      */           } else {
/*  623 */             if (w.pred() || w.left.black()) {
/*  624 */               Entry y = w.right;
/*  625 */               y.black(true);
/*  626 */               w.black(false);
/*  627 */               w.right = y.left;
/*  628 */               y.left = w;
/*  629 */               w = (this.nodePath[i - 1]).left = y;
/*  630 */               if (w.pred()) {
/*  631 */                 w.pred(false);
/*  632 */                 w.left.succ(w);
/*      */               } 
/*      */             } 
/*  635 */             w.black(this.nodePath[i - 1].black());
/*  636 */             this.nodePath[i - 1].black(true);
/*  637 */             w.left.black(true);
/*  638 */             (this.nodePath[i - 1]).left = w.right;
/*  639 */             w.right = this.nodePath[i - 1];
/*  640 */             if (i < 2) {
/*  641 */               this.tree = w;
/*      */             }
/*  643 */             else if (this.dirPath[i - 2]) {
/*  644 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  646 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  648 */             if (w.succ()) {
/*  649 */               w.succ(false);
/*  650 */               this.nodePath[i - 1].pred(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  656 */       if (this.tree != null)
/*  657 */         this.tree.black(true); 
/*      */     } 
/*  659 */     this.modified = true;
/*  660 */     this.count--;
/*      */     
/*  662 */     while (maxDepth-- != 0)
/*  663 */       this.nodePath[maxDepth] = null; 
/*  664 */     return p.value;
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  668 */     ValueIterator i = new ValueIterator();
/*      */     
/*  670 */     int j = this.count;
/*  671 */     while (j-- != 0) {
/*  672 */       boolean ev = i.nextBoolean();
/*  673 */       if (ev == v)
/*  674 */         return true; 
/*      */     } 
/*  676 */     return false;
/*      */   }
/*      */   
/*      */   public void clear() {
/*  680 */     this.count = 0;
/*  681 */     this.tree = null;
/*  682 */     this.entries = null;
/*  683 */     this.values = null;
/*  684 */     this.keys = null;
/*  685 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry
/*      */     extends AbstractDouble2BooleanMap.BasicEntry
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
/*  713 */       super(0.0D, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(double k, boolean v) {
/*  724 */       super(k, v);
/*  725 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left() {
/*  733 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right() {
/*  741 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  749 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  757 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  766 */       if (pred) {
/*  767 */         this.info |= 0x40000000;
/*      */       } else {
/*  769 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  778 */       if (succ) {
/*  779 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  781 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry pred) {
/*  790 */       this.info |= 0x40000000;
/*  791 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry succ) {
/*  800 */       this.info |= Integer.MIN_VALUE;
/*  801 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry left) {
/*  810 */       this.info &= 0xBFFFFFFF;
/*  811 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry right) {
/*  820 */       this.info &= Integer.MAX_VALUE;
/*  821 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean black() {
/*  829 */       return ((this.info & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void black(boolean black) {
/*  838 */       if (black) {
/*  839 */         this.info |= 0x1;
/*      */       } else {
/*  841 */         this.info &= 0xFFFFFFFE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry next() {
/*  849 */       Entry next = this.right;
/*  850 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  851 */         while ((next.info & 0x40000000) == 0)
/*  852 */           next = next.left;  
/*  853 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry prev() {
/*  861 */       Entry prev = this.left;
/*  862 */       if ((this.info & 0x40000000) == 0)
/*  863 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  864 */           prev = prev.right;  
/*  865 */       return prev;
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean value) {
/*  869 */       boolean oldValue = this.value;
/*  870 */       this.value = value;
/*  871 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry clone() {
/*      */       Entry c;
/*      */       try {
/*  878 */         c = (Entry)super.clone();
/*  879 */       } catch (CloneNotSupportedException cantHappen) {
/*  880 */         throw new InternalError();
/*      */       } 
/*  882 */       c.key = this.key;
/*  883 */       c.value = this.value;
/*  884 */       c.info = this.info;
/*  885 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  890 */       if (!(o instanceof Map.Entry))
/*  891 */         return false; 
/*  892 */       Map.Entry<Double, Boolean> e = (Map.Entry<Double, Boolean>)o;
/*  893 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && this.value == ((Boolean)e
/*  894 */         .getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  898 */       return HashCommon.double2int(this.key) ^ (this.value ? 1231 : 1237);
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
/*      */   public boolean get(double k) {
/*  936 */     Entry e = findKey(k);
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
/*      */     Double2BooleanRBTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2BooleanRBTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2BooleanRBTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  980 */     int index = 0;
/*      */     TreeIterator() {
/*  982 */       this.next = Double2BooleanRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(double k) {
/*  985 */       if ((this.next = Double2BooleanRBTreeMap.this.locateKey(k)) != null)
/*  986 */         if (Double2BooleanRBTreeMap.this.compare(this.next.key, k) <= 0) {
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
/*      */     Double2BooleanRBTreeMap.Entry nextEntry() {
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
/*      */     Double2BooleanRBTreeMap.Entry previousEntry() {
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
/* 1039 */       Double2BooleanRBTreeMap.this.remove(this.curr.key);
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
/*      */     implements ObjectListIterator<Double2BooleanMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(double k) {
/* 1065 */       super(k);
/*      */     }
/*      */     
/*      */     public Double2BooleanMap.Entry next() {
/* 1069 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Double2BooleanMap.Entry previous() {
/* 1073 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Double2BooleanMap.Entry> double2BooleanEntrySet() {
/* 1078 */     if (this.entries == null)
/* 1079 */       this.entries = (ObjectSortedSet<Double2BooleanMap.Entry>)new AbstractObjectSortedSet<Double2BooleanMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Double2BooleanMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Double2BooleanMap.Entry> comparator() {
/* 1084 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2BooleanMap.Entry> iterator() {
/* 1088 */             return (ObjectBidirectionalIterator<Double2BooleanMap.Entry>)new Double2BooleanRBTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2BooleanMap.Entry> iterator(Double2BooleanMap.Entry from) {
/* 1093 */             return (ObjectBidirectionalIterator<Double2BooleanMap.Entry>)new Double2BooleanRBTreeMap.EntryIterator(from.getDoubleKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1098 */             if (!(o instanceof Map.Entry))
/* 1099 */               return false; 
/* 1100 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1101 */             if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1102 */               return false; 
/* 1103 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1104 */               return false; 
/* 1105 */             Double2BooleanRBTreeMap.Entry f = Double2BooleanRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1106 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1111 */             if (!(o instanceof Map.Entry))
/* 1112 */               return false; 
/* 1113 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1114 */             if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1115 */               return false; 
/* 1116 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1117 */               return false; 
/* 1118 */             Double2BooleanRBTreeMap.Entry f = Double2BooleanRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1119 */             if (f == null || f.getBooleanValue() != ((Boolean)e.getValue()).booleanValue())
/* 1120 */               return false; 
/* 1121 */             Double2BooleanRBTreeMap.this.remove(f.key);
/* 1122 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1126 */             return Double2BooleanRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1130 */             Double2BooleanRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Double2BooleanMap.Entry first() {
/* 1134 */             return Double2BooleanRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Double2BooleanMap.Entry last() {
/* 1138 */             return Double2BooleanRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Double2BooleanMap.Entry> subSet(Double2BooleanMap.Entry from, Double2BooleanMap.Entry to) {
/* 1143 */             return Double2BooleanRBTreeMap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Double2BooleanMap.Entry> headSet(Double2BooleanMap.Entry to) {
/* 1147 */             return Double2BooleanRBTreeMap.this.headMap(to.getDoubleKey()).double2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Double2BooleanMap.Entry> tailSet(Double2BooleanMap.Entry from) {
/* 1151 */             return Double2BooleanRBTreeMap.this.tailMap(from.getDoubleKey()).double2BooleanEntrySet();
/*      */           }
/*      */         }; 
/* 1154 */     return this.entries;
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
/* 1170 */       super(k);
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/* 1174 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1178 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractDouble2BooleanSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public DoubleBidirectionalIterator iterator() {
/* 1185 */       return new Double2BooleanRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public DoubleBidirectionalIterator iterator(double from) {
/* 1189 */       return new Double2BooleanRBTreeMap.KeyIterator(from);
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
/* 1204 */     if (this.keys == null)
/* 1205 */       this.keys = new KeySet(); 
/* 1206 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements BooleanListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1221 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public boolean previousBoolean() {
/* 1225 */       return (previousEntry()).value;
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
/*      */   public BooleanCollection values() {
/* 1240 */     if (this.values == null)
/* 1241 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1244 */             return (BooleanIterator)new Double2BooleanRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(boolean k) {
/* 1248 */             return Double2BooleanRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1252 */             return Double2BooleanRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1256 */             Double2BooleanRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1259 */     return this.values;
/*      */   }
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1263 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Double2BooleanSortedMap headMap(double to) {
/* 1267 */     return new Submap(0.0D, true, to, false);
/*      */   }
/*      */   
/*      */   public Double2BooleanSortedMap tailMap(double from) {
/* 1271 */     return new Submap(from, false, 0.0D, true);
/*      */   }
/*      */   
/*      */   public Double2BooleanSortedMap subMap(double from, double to) {
/* 1275 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractDouble2BooleanSortedMap
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
/*      */     protected transient ObjectSortedSet<Double2BooleanMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient DoubleSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(double from, boolean bottom, double to, boolean top) {
/* 1319 */       if (!bottom && !top && Double2BooleanRBTreeMap.this.compare(from, to) > 0)
/* 1320 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1321 */       this.from = from;
/* 1322 */       this.bottom = bottom;
/* 1323 */       this.to = to;
/* 1324 */       this.top = top;
/* 1325 */       this.defRetValue = Double2BooleanRBTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1329 */       SubmapIterator i = new SubmapIterator();
/* 1330 */       while (i.hasNext()) {
/* 1331 */         i.nextEntry();
/* 1332 */         i.remove();
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
/* 1343 */       return ((this.bottom || Double2BooleanRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Double2BooleanRBTreeMap.this
/* 1344 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2BooleanMap.Entry> double2BooleanEntrySet() {
/* 1348 */       if (this.entries == null)
/* 1349 */         this.entries = (ObjectSortedSet<Double2BooleanMap.Entry>)new AbstractObjectSortedSet<Double2BooleanMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Double2BooleanMap.Entry> iterator() {
/* 1352 */               return (ObjectBidirectionalIterator<Double2BooleanMap.Entry>)new Double2BooleanRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Double2BooleanMap.Entry> iterator(Double2BooleanMap.Entry from) {
/* 1357 */               return (ObjectBidirectionalIterator<Double2BooleanMap.Entry>)new Double2BooleanRBTreeMap.Submap.SubmapEntryIterator(from.getDoubleKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Double2BooleanMap.Entry> comparator() {
/* 1361 */               return Double2BooleanRBTreeMap.this.double2BooleanEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1366 */               if (!(o instanceof Map.Entry))
/* 1367 */                 return false; 
/* 1368 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1369 */               if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1370 */                 return false; 
/* 1371 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1372 */                 return false; 
/* 1373 */               Double2BooleanRBTreeMap.Entry f = Double2BooleanRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1374 */               return (f != null && Double2BooleanRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1379 */               if (!(o instanceof Map.Entry))
/* 1380 */                 return false; 
/* 1381 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1382 */               if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1383 */                 return false; 
/* 1384 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1385 */                 return false; 
/* 1386 */               Double2BooleanRBTreeMap.Entry f = Double2BooleanRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1387 */               if (f != null && Double2BooleanRBTreeMap.Submap.this.in(f.key))
/* 1388 */                 Double2BooleanRBTreeMap.Submap.this.remove(f.key); 
/* 1389 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1393 */               int c = 0;
/* 1394 */               for (ObjectBidirectionalIterator<Double2BooleanMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1395 */                 c++; 
/* 1396 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1400 */               return !(new Double2BooleanRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1404 */               Double2BooleanRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Double2BooleanMap.Entry first() {
/* 1408 */               return Double2BooleanRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Double2BooleanMap.Entry last() {
/* 1412 */               return Double2BooleanRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Double2BooleanMap.Entry> subSet(Double2BooleanMap.Entry from, Double2BooleanMap.Entry to) {
/* 1417 */               return Double2BooleanRBTreeMap.Submap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Double2BooleanMap.Entry> headSet(Double2BooleanMap.Entry to) {
/* 1421 */               return Double2BooleanRBTreeMap.Submap.this.headMap(to.getDoubleKey()).double2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Double2BooleanMap.Entry> tailSet(Double2BooleanMap.Entry from) {
/* 1425 */               return Double2BooleanRBTreeMap.Submap.this.tailMap(from.getDoubleKey()).double2BooleanEntrySet();
/*      */             }
/*      */           }; 
/* 1428 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractDouble2BooleanSortedMap.KeySet {
/*      */       public DoubleBidirectionalIterator iterator() {
/* 1433 */         return new Double2BooleanRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public DoubleBidirectionalIterator iterator(double from) {
/* 1437 */         return new Double2BooleanRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public DoubleSortedSet keySet() {
/* 1442 */       if (this.keys == null)
/* 1443 */         this.keys = new KeySet(); 
/* 1444 */       return this.keys;
/*      */     }
/*      */     
/*      */     public BooleanCollection values() {
/* 1448 */       if (this.values == null)
/* 1449 */         this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */           {
/*      */             public BooleanIterator iterator() {
/* 1452 */               return (BooleanIterator)new Double2BooleanRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(boolean k) {
/* 1456 */               return Double2BooleanRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1460 */               return Double2BooleanRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1464 */               Double2BooleanRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1467 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(double k) {
/* 1472 */       return (in(k) && Double2BooleanRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(boolean v) {
/* 1476 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1478 */       while (i.hasNext()) {
/* 1479 */         boolean ev = (i.nextEntry()).value;
/* 1480 */         if (ev == v)
/* 1481 */           return true; 
/*      */       } 
/* 1483 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean get(double k) {
/* 1489 */       double kk = k; Double2BooleanRBTreeMap.Entry e;
/* 1490 */       return (in(kk) && (e = Double2BooleanRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public boolean put(double k, boolean v) {
/* 1494 */       Double2BooleanRBTreeMap.this.modified = false;
/* 1495 */       if (!in(k))
/* 1496 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1497 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1498 */       boolean oldValue = Double2BooleanRBTreeMap.this.put(k, v);
/* 1499 */       return Double2BooleanRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(double k) {
/* 1504 */       Double2BooleanRBTreeMap.this.modified = false;
/* 1505 */       if (!in(k))
/* 1506 */         return this.defRetValue; 
/* 1507 */       boolean oldValue = Double2BooleanRBTreeMap.this.remove(k);
/* 1508 */       return Double2BooleanRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1512 */       SubmapIterator i = new SubmapIterator();
/* 1513 */       int n = 0;
/* 1514 */       while (i.hasNext()) {
/* 1515 */         n++;
/* 1516 */         i.nextEntry();
/*      */       } 
/* 1518 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1522 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1526 */       return Double2BooleanRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Double2BooleanSortedMap headMap(double to) {
/* 1530 */       if (this.top)
/* 1531 */         return new Submap(this.from, this.bottom, to, false); 
/* 1532 */       return (Double2BooleanRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Double2BooleanSortedMap tailMap(double from) {
/* 1536 */       if (this.bottom)
/* 1537 */         return new Submap(from, false, this.to, this.top); 
/* 1538 */       return (Double2BooleanRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Double2BooleanSortedMap subMap(double from, double to) {
/* 1542 */       if (this.top && this.bottom)
/* 1543 */         return new Submap(from, false, to, false); 
/* 1544 */       if (!this.top)
/* 1545 */         to = (Double2BooleanRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1546 */       if (!this.bottom)
/* 1547 */         from = (Double2BooleanRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1548 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1549 */         return this; 
/* 1550 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2BooleanRBTreeMap.Entry firstEntry() {
/*      */       Double2BooleanRBTreeMap.Entry e;
/* 1559 */       if (Double2BooleanRBTreeMap.this.tree == null) {
/* 1560 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1564 */       if (this.bottom) {
/* 1565 */         e = Double2BooleanRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1567 */         e = Double2BooleanRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1569 */         if (Double2BooleanRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1570 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1574 */       if (e == null || (!this.top && Double2BooleanRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1575 */         return null; 
/* 1576 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2BooleanRBTreeMap.Entry lastEntry() {
/*      */       Double2BooleanRBTreeMap.Entry e;
/* 1585 */       if (Double2BooleanRBTreeMap.this.tree == null) {
/* 1586 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1590 */       if (this.top) {
/* 1591 */         e = Double2BooleanRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1593 */         e = Double2BooleanRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1595 */         if (Double2BooleanRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1596 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1600 */       if (e == null || (!this.bottom && Double2BooleanRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1601 */         return null; 
/* 1602 */       return e;
/*      */     }
/*      */     
/*      */     public double firstDoubleKey() {
/* 1606 */       Double2BooleanRBTreeMap.Entry e = firstEntry();
/* 1607 */       if (e == null)
/* 1608 */         throw new NoSuchElementException(); 
/* 1609 */       return e.key;
/*      */     }
/*      */     
/*      */     public double lastDoubleKey() {
/* 1613 */       Double2BooleanRBTreeMap.Entry e = lastEntry();
/* 1614 */       if (e == null)
/* 1615 */         throw new NoSuchElementException(); 
/* 1616 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Double2BooleanRBTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1629 */         this.next = Double2BooleanRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(double k) {
/* 1632 */         this();
/* 1633 */         if (this.next != null)
/* 1634 */           if (!Double2BooleanRBTreeMap.Submap.this.bottom && Double2BooleanRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1635 */             this.prev = null;
/* 1636 */           } else if (!Double2BooleanRBTreeMap.Submap.this.top && Double2BooleanRBTreeMap.this.compare(k, (this.prev = Double2BooleanRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1637 */             this.next = null;
/*      */           } else {
/* 1639 */             this.next = Double2BooleanRBTreeMap.this.locateKey(k);
/* 1640 */             if (Double2BooleanRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1641 */               this.prev = this.next;
/* 1642 */               this.next = this.next.next();
/*      */             } else {
/* 1644 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1650 */         this.prev = this.prev.prev();
/* 1651 */         if (!Double2BooleanRBTreeMap.Submap.this.bottom && this.prev != null && Double2BooleanRBTreeMap.this.compare(this.prev.key, Double2BooleanRBTreeMap.Submap.this.from) < 0)
/* 1652 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1656 */         this.next = this.next.next();
/* 1657 */         if (!Double2BooleanRBTreeMap.Submap.this.top && this.next != null && Double2BooleanRBTreeMap.this.compare(this.next.key, Double2BooleanRBTreeMap.Submap.this.to) >= 0)
/* 1658 */           this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Double2BooleanMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(double k) {
/* 1667 */         super(k);
/*      */       }
/*      */       
/*      */       public Double2BooleanMap.Entry next() {
/* 1671 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Double2BooleanMap.Entry previous() {
/* 1675 */         return previousEntry();
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
/* 1693 */         super(from);
/*      */       }
/*      */       
/*      */       public double nextDouble() {
/* 1697 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public double previousDouble() {
/* 1701 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements BooleanListIterator
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean nextBoolean() {
/* 1717 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public boolean previousBoolean() {
/* 1721 */         return (previousEntry()).value;
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
/*      */   public Double2BooleanRBTreeMap clone() {
/*      */     Double2BooleanRBTreeMap c;
/*      */     try {
/* 1740 */       c = (Double2BooleanRBTreeMap)super.clone();
/* 1741 */     } catch (CloneNotSupportedException cantHappen) {
/* 1742 */       throw new InternalError();
/*      */     } 
/* 1744 */     c.keys = null;
/* 1745 */     c.values = null;
/* 1746 */     c.entries = null;
/* 1747 */     c.allocatePaths();
/* 1748 */     if (this.count != 0) {
/*      */       
/* 1750 */       Entry rp = new Entry(), rq = new Entry();
/* 1751 */       Entry p = rp;
/* 1752 */       rp.left(this.tree);
/* 1753 */       Entry q = rq;
/* 1754 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1756 */         if (!p.pred()) {
/* 1757 */           Entry e = p.left.clone();
/* 1758 */           e.pred(q.left);
/* 1759 */           e.succ(q);
/* 1760 */           q.left(e);
/* 1761 */           p = p.left;
/* 1762 */           q = q.left;
/*      */         } else {
/* 1764 */           while (p.succ()) {
/* 1765 */             p = p.right;
/* 1766 */             if (p == null) {
/* 1767 */               q.right = null;
/* 1768 */               c.tree = rq.left;
/* 1769 */               c.firstEntry = c.tree;
/* 1770 */               while (c.firstEntry.left != null)
/* 1771 */                 c.firstEntry = c.firstEntry.left; 
/* 1772 */               c.lastEntry = c.tree;
/* 1773 */               while (c.lastEntry.right != null)
/* 1774 */                 c.lastEntry = c.lastEntry.right; 
/* 1775 */               return c;
/*      */             } 
/* 1777 */             q = q.right;
/*      */           } 
/* 1779 */           p = p.right;
/* 1780 */           q = q.right;
/*      */         } 
/* 1782 */         if (!p.succ()) {
/* 1783 */           Entry e = p.right.clone();
/* 1784 */           e.succ(q.right);
/* 1785 */           e.pred(q);
/* 1786 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1790 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1793 */     int n = this.count;
/* 1794 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1796 */     s.defaultWriteObject();
/* 1797 */     while (n-- != 0) {
/* 1798 */       Entry e = i.nextEntry();
/* 1799 */       s.writeDouble(e.key);
/* 1800 */       s.writeBoolean(e.value);
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
/* 1821 */     if (n == 1) {
/* 1822 */       Entry entry = new Entry(s.readDouble(), s.readBoolean());
/* 1823 */       entry.pred(pred);
/* 1824 */       entry.succ(succ);
/* 1825 */       entry.black(true);
/* 1826 */       return entry;
/*      */     } 
/* 1828 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1833 */       Entry entry = new Entry(s.readDouble(), s.readBoolean());
/* 1834 */       entry.black(true);
/* 1835 */       entry.right(new Entry(s.readDouble(), s.readBoolean()));
/* 1836 */       entry.right.pred(entry);
/* 1837 */       entry.pred(pred);
/* 1838 */       entry.right.succ(succ);
/* 1839 */       return entry;
/*      */     } 
/*      */     
/* 1842 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1843 */     Entry top = new Entry();
/* 1844 */     top.left(readTree(s, leftN, pred, top));
/* 1845 */     top.key = s.readDouble();
/* 1846 */     top.value = s.readBoolean();
/* 1847 */     top.black(true);
/* 1848 */     top.right(readTree(s, rightN, top, succ));
/* 1849 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1850 */       top.right.black(false); 
/* 1851 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1854 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1859 */     setActualComparator();
/* 1860 */     allocatePaths();
/* 1861 */     if (this.count != 0) {
/* 1862 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1864 */       Entry e = this.tree;
/* 1865 */       while (e.left() != null)
/* 1866 */         e = e.left(); 
/* 1867 */       this.firstEntry = e;
/* 1868 */       e = this.tree;
/* 1869 */       while (e.right() != null)
/* 1870 */         e = e.right(); 
/* 1871 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2BooleanRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */