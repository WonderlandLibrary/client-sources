/*      */ package it.unimi.dsi.fastutil.shorts;
/*      */ 
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
/*      */ 
/*      */ public class Short2ShortRBTreeMap
/*      */   extends AbstractShort2ShortSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Short2ShortMap.Entry> entries;
/*      */   protected transient ShortSortedSet keys;
/*      */   protected transient ShortCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Short> storedComparator;
/*      */   protected transient ShortComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public Short2ShortRBTreeMap() {
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
/*   91 */     this.actualComparator = ShortComparators.asShortComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ShortRBTreeMap(Comparator<? super Short> c) {
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
/*      */   public Short2ShortRBTreeMap(Map<? extends Short, ? extends Short> m) {
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
/*      */   public Short2ShortRBTreeMap(SortedMap<Short, Short> m) {
/*  122 */     this(m.comparator());
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ShortRBTreeMap(Short2ShortMap m) {
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
/*      */   public Short2ShortRBTreeMap(Short2ShortSortedMap m) {
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
/*      */   public Short2ShortRBTreeMap(short[] k, short[] v, Comparator<? super Short> c) {
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
/*      */   public Short2ShortRBTreeMap(short[] k, short[] v) {
/*  178 */     this(k, v, (Comparator<? super Short>)null);
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
/*  206 */     return (this.actualComparator == null) ? Short.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry findKey(short k) {
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
/*      */   final Entry locateKey(short k) {
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
/*      */   public short addTo(short k, short incr) {
/*  270 */     Entry e = add(k);
/*  271 */     short oldValue = e.value;
/*  272 */     e.value = (short)(e.value + incr);
/*  273 */     return oldValue;
/*      */   }
/*      */   
/*      */   public short put(short k, short v) {
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
/*      */   private Entry add(short k) {
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
/*      */   public short remove(short k) {
/*  441 */     this.modified = false;
/*  442 */     if (this.tree == null)
/*  443 */       return this.defRetValue; 
/*  444 */     Entry p = this.tree;
/*      */     
/*  446 */     int i = 0;
/*  447 */     short kk = k;
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
/*      */     extends AbstractShort2ShortMap.BasicEntry
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
/*  732 */       super((short)0, (short)0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(short k, short v) {
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
/*  911 */       Map.Entry<Short, Short> e = (Map.Entry<Short, Short>)o;
/*  912 */       return (this.key == ((Short)e.getKey()).shortValue() && this.value == ((Short)e.getValue()).shortValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  916 */       return this.key ^ this.value;
/*      */     }
/*      */     
/*      */     public String toString() {
/*  920 */       return this.key + "=>" + this.value;
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
/*  941 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  945 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  949 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public short get(short k) {
/*  954 */     Entry e = findKey(k);
/*  955 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public short firstShortKey() {
/*  959 */     if (this.tree == null)
/*  960 */       throw new NoSuchElementException(); 
/*  961 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public short lastShortKey() {
/*  965 */     if (this.tree == null)
/*  966 */       throw new NoSuchElementException(); 
/*  967 */     return this.lastEntry.key;
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
/*      */     Short2ShortRBTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Short2ShortRBTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Short2ShortRBTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  998 */     int index = 0;
/*      */     TreeIterator() {
/* 1000 */       this.next = Short2ShortRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(short k) {
/* 1003 */       if ((this.next = Short2ShortRBTreeMap.this.locateKey(k)) != null)
/* 1004 */         if (Short2ShortRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1005 */           this.prev = this.next;
/* 1006 */           this.next = this.next.next();
/*      */         } else {
/* 1008 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/* 1012 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1015 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1018 */       this.next = this.next.next();
/*      */     }
/*      */     Short2ShortRBTreeMap.Entry nextEntry() {
/* 1021 */       if (!hasNext())
/* 1022 */         throw new NoSuchElementException(); 
/* 1023 */       this.curr = this.prev = this.next;
/* 1024 */       this.index++;
/* 1025 */       updateNext();
/* 1026 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1029 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Short2ShortRBTreeMap.Entry previousEntry() {
/* 1032 */       if (!hasPrevious())
/* 1033 */         throw new NoSuchElementException(); 
/* 1034 */       this.curr = this.next = this.prev;
/* 1035 */       this.index--;
/* 1036 */       updatePrevious();
/* 1037 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1040 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1043 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1046 */       if (this.curr == null) {
/* 1047 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1052 */       if (this.curr == this.prev)
/* 1053 */         this.index--; 
/* 1054 */       this.next = this.prev = this.curr;
/* 1055 */       updatePrevious();
/* 1056 */       updateNext();
/* 1057 */       Short2ShortRBTreeMap.this.remove(this.curr.key);
/* 1058 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1061 */       int i = n;
/* 1062 */       while (i-- != 0 && hasNext())
/* 1063 */         nextEntry(); 
/* 1064 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1067 */       int i = n;
/* 1068 */       while (i-- != 0 && hasPrevious())
/* 1069 */         previousEntry(); 
/* 1070 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Short2ShortMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(short k) {
/* 1083 */       super(k);
/*      */     }
/*      */     
/*      */     public Short2ShortMap.Entry next() {
/* 1087 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Short2ShortMap.Entry previous() {
/* 1091 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Short2ShortMap.Entry> short2ShortEntrySet() {
/* 1096 */     if (this.entries == null)
/* 1097 */       this.entries = (ObjectSortedSet<Short2ShortMap.Entry>)new AbstractObjectSortedSet<Short2ShortMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Short2ShortMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Short2ShortMap.Entry> comparator() {
/* 1102 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Short2ShortMap.Entry> iterator() {
/* 1106 */             return (ObjectBidirectionalIterator<Short2ShortMap.Entry>)new Short2ShortRBTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Short2ShortMap.Entry> iterator(Short2ShortMap.Entry from) {
/* 1110 */             return (ObjectBidirectionalIterator<Short2ShortMap.Entry>)new Short2ShortRBTreeMap.EntryIterator(from.getShortKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1115 */             if (!(o instanceof Map.Entry))
/* 1116 */               return false; 
/* 1117 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1118 */             if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1119 */               return false; 
/* 1120 */             if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1121 */               return false; 
/* 1122 */             Short2ShortRBTreeMap.Entry f = Short2ShortRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1123 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1128 */             if (!(o instanceof Map.Entry))
/* 1129 */               return false; 
/* 1130 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1131 */             if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1132 */               return false; 
/* 1133 */             if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1134 */               return false; 
/* 1135 */             Short2ShortRBTreeMap.Entry f = Short2ShortRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1136 */             if (f == null || f.getShortValue() != ((Short)e.getValue()).shortValue())
/* 1137 */               return false; 
/* 1138 */             Short2ShortRBTreeMap.this.remove(f.key);
/* 1139 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1143 */             return Short2ShortRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1147 */             Short2ShortRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Short2ShortMap.Entry first() {
/* 1151 */             return Short2ShortRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Short2ShortMap.Entry last() {
/* 1155 */             return Short2ShortRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Short2ShortMap.Entry> subSet(Short2ShortMap.Entry from, Short2ShortMap.Entry to) {
/* 1160 */             return Short2ShortRBTreeMap.this.subMap(from.getShortKey(), to.getShortKey()).short2ShortEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Short2ShortMap.Entry> headSet(Short2ShortMap.Entry to) {
/* 1164 */             return Short2ShortRBTreeMap.this.headMap(to.getShortKey()).short2ShortEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Short2ShortMap.Entry> tailSet(Short2ShortMap.Entry from) {
/* 1168 */             return Short2ShortRBTreeMap.this.tailMap(from.getShortKey()).short2ShortEntrySet();
/*      */           }
/*      */         }; 
/* 1171 */     return this.entries;
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
/* 1187 */       super(k);
/*      */     }
/*      */     
/*      */     public short nextShort() {
/* 1191 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public short previousShort() {
/* 1195 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractShort2ShortSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ShortBidirectionalIterator iterator() {
/* 1202 */       return new Short2ShortRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public ShortBidirectionalIterator iterator(short from) {
/* 1206 */       return new Short2ShortRBTreeMap.KeyIterator(from);
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
/* 1221 */     if (this.keys == null)
/* 1222 */       this.keys = new KeySet(); 
/* 1223 */     return this.keys;
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
/* 1238 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public short previousShort() {
/* 1242 */       return (previousEntry()).value;
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
/* 1257 */     if (this.values == null)
/* 1258 */       this.values = new AbstractShortCollection()
/*      */         {
/*      */           public ShortIterator iterator() {
/* 1261 */             return new Short2ShortRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(short k) {
/* 1265 */             return Short2ShortRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1269 */             return Short2ShortRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1273 */             Short2ShortRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1276 */     return this.values;
/*      */   }
/*      */   
/*      */   public ShortComparator comparator() {
/* 1280 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Short2ShortSortedMap headMap(short to) {
/* 1284 */     return new Submap((short)0, true, to, false);
/*      */   }
/*      */   
/*      */   public Short2ShortSortedMap tailMap(short from) {
/* 1288 */     return new Submap(from, false, (short)0, true);
/*      */   }
/*      */   
/*      */   public Short2ShortSortedMap subMap(short from, short to) {
/* 1292 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractShort2ShortSortedMap
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
/*      */     protected transient ObjectSortedSet<Short2ShortMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ShortSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ShortCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(short from, boolean bottom, short to, boolean top) {
/* 1336 */       if (!bottom && !top && Short2ShortRBTreeMap.this.compare(from, to) > 0)
/* 1337 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1338 */       this.from = from;
/* 1339 */       this.bottom = bottom;
/* 1340 */       this.to = to;
/* 1341 */       this.top = top;
/* 1342 */       this.defRetValue = Short2ShortRBTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1346 */       SubmapIterator i = new SubmapIterator();
/* 1347 */       while (i.hasNext()) {
/* 1348 */         i.nextEntry();
/* 1349 */         i.remove();
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
/* 1360 */       return ((this.bottom || Short2ShortRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Short2ShortRBTreeMap.this
/* 1361 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Short2ShortMap.Entry> short2ShortEntrySet() {
/* 1365 */       if (this.entries == null)
/* 1366 */         this.entries = (ObjectSortedSet<Short2ShortMap.Entry>)new AbstractObjectSortedSet<Short2ShortMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Short2ShortMap.Entry> iterator() {
/* 1369 */               return (ObjectBidirectionalIterator<Short2ShortMap.Entry>)new Short2ShortRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */             
/*      */             public ObjectBidirectionalIterator<Short2ShortMap.Entry> iterator(Short2ShortMap.Entry from) {
/* 1373 */               return (ObjectBidirectionalIterator<Short2ShortMap.Entry>)new Short2ShortRBTreeMap.Submap.SubmapEntryIterator(from.getShortKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Short2ShortMap.Entry> comparator() {
/* 1377 */               return Short2ShortRBTreeMap.this.short2ShortEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1382 */               if (!(o instanceof Map.Entry))
/* 1383 */                 return false; 
/* 1384 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1385 */               if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1386 */                 return false; 
/* 1387 */               if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1388 */                 return false; 
/* 1389 */               Short2ShortRBTreeMap.Entry f = Short2ShortRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1390 */               return (f != null && Short2ShortRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1395 */               if (!(o instanceof Map.Entry))
/* 1396 */                 return false; 
/* 1397 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1398 */               if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1399 */                 return false; 
/* 1400 */               if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1401 */                 return false; 
/* 1402 */               Short2ShortRBTreeMap.Entry f = Short2ShortRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1403 */               if (f != null && Short2ShortRBTreeMap.Submap.this.in(f.key))
/* 1404 */                 Short2ShortRBTreeMap.Submap.this.remove(f.key); 
/* 1405 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1409 */               int c = 0;
/* 1410 */               for (ObjectBidirectionalIterator<Short2ShortMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1411 */                 c++; 
/* 1412 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1416 */               return !(new Short2ShortRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1420 */               Short2ShortRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Short2ShortMap.Entry first() {
/* 1424 */               return Short2ShortRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Short2ShortMap.Entry last() {
/* 1428 */               return Short2ShortRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Short2ShortMap.Entry> subSet(Short2ShortMap.Entry from, Short2ShortMap.Entry to) {
/* 1433 */               return Short2ShortRBTreeMap.Submap.this.subMap(from.getShortKey(), to.getShortKey()).short2ShortEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Short2ShortMap.Entry> headSet(Short2ShortMap.Entry to) {
/* 1437 */               return Short2ShortRBTreeMap.Submap.this.headMap(to.getShortKey()).short2ShortEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Short2ShortMap.Entry> tailSet(Short2ShortMap.Entry from) {
/* 1441 */               return Short2ShortRBTreeMap.Submap.this.tailMap(from.getShortKey()).short2ShortEntrySet();
/*      */             }
/*      */           }; 
/* 1444 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractShort2ShortSortedMap.KeySet {
/*      */       public ShortBidirectionalIterator iterator() {
/* 1449 */         return new Short2ShortRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public ShortBidirectionalIterator iterator(short from) {
/* 1453 */         return new Short2ShortRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public ShortSortedSet keySet() {
/* 1458 */       if (this.keys == null)
/* 1459 */         this.keys = new KeySet(); 
/* 1460 */       return this.keys;
/*      */     }
/*      */     
/*      */     public ShortCollection values() {
/* 1464 */       if (this.values == null)
/* 1465 */         this.values = new AbstractShortCollection()
/*      */           {
/*      */             public ShortIterator iterator() {
/* 1468 */               return new Short2ShortRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(short k) {
/* 1472 */               return Short2ShortRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1476 */               return Short2ShortRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1480 */               Short2ShortRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1483 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(short k) {
/* 1488 */       return (in(k) && Short2ShortRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(short v) {
/* 1492 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1494 */       while (i.hasNext()) {
/* 1495 */         short ev = (i.nextEntry()).value;
/* 1496 */         if (ev == v)
/* 1497 */           return true; 
/*      */       } 
/* 1499 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public short get(short k) {
/* 1505 */       short kk = k; Short2ShortRBTreeMap.Entry e;
/* 1506 */       return (in(kk) && (e = Short2ShortRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public short put(short k, short v) {
/* 1510 */       Short2ShortRBTreeMap.this.modified = false;
/* 1511 */       if (!in(k))
/* 1512 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1513 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1514 */       short oldValue = Short2ShortRBTreeMap.this.put(k, v);
/* 1515 */       return Short2ShortRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public short remove(short k) {
/* 1520 */       Short2ShortRBTreeMap.this.modified = false;
/* 1521 */       if (!in(k))
/* 1522 */         return this.defRetValue; 
/* 1523 */       short oldValue = Short2ShortRBTreeMap.this.remove(k);
/* 1524 */       return Short2ShortRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1528 */       SubmapIterator i = new SubmapIterator();
/* 1529 */       int n = 0;
/* 1530 */       while (i.hasNext()) {
/* 1531 */         n++;
/* 1532 */         i.nextEntry();
/*      */       } 
/* 1534 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1538 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public ShortComparator comparator() {
/* 1542 */       return Short2ShortRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Short2ShortSortedMap headMap(short to) {
/* 1546 */       if (this.top)
/* 1547 */         return new Submap(this.from, this.bottom, to, false); 
/* 1548 */       return (Short2ShortRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Short2ShortSortedMap tailMap(short from) {
/* 1552 */       if (this.bottom)
/* 1553 */         return new Submap(from, false, this.to, this.top); 
/* 1554 */       return (Short2ShortRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Short2ShortSortedMap subMap(short from, short to) {
/* 1558 */       if (this.top && this.bottom)
/* 1559 */         return new Submap(from, false, to, false); 
/* 1560 */       if (!this.top)
/* 1561 */         to = (Short2ShortRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1562 */       if (!this.bottom)
/* 1563 */         from = (Short2ShortRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1564 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1565 */         return this; 
/* 1566 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Short2ShortRBTreeMap.Entry firstEntry() {
/*      */       Short2ShortRBTreeMap.Entry e;
/* 1575 */       if (Short2ShortRBTreeMap.this.tree == null) {
/* 1576 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1580 */       if (this.bottom) {
/* 1581 */         e = Short2ShortRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1583 */         e = Short2ShortRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1585 */         if (Short2ShortRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1586 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1590 */       if (e == null || (!this.top && Short2ShortRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1591 */         return null; 
/* 1592 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Short2ShortRBTreeMap.Entry lastEntry() {
/*      */       Short2ShortRBTreeMap.Entry e;
/* 1601 */       if (Short2ShortRBTreeMap.this.tree == null) {
/* 1602 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1606 */       if (this.top) {
/* 1607 */         e = Short2ShortRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1609 */         e = Short2ShortRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1611 */         if (Short2ShortRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1612 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1616 */       if (e == null || (!this.bottom && Short2ShortRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1617 */         return null; 
/* 1618 */       return e;
/*      */     }
/*      */     
/*      */     public short firstShortKey() {
/* 1622 */       Short2ShortRBTreeMap.Entry e = firstEntry();
/* 1623 */       if (e == null)
/* 1624 */         throw new NoSuchElementException(); 
/* 1625 */       return e.key;
/*      */     }
/*      */     
/*      */     public short lastShortKey() {
/* 1629 */       Short2ShortRBTreeMap.Entry e = lastEntry();
/* 1630 */       if (e == null)
/* 1631 */         throw new NoSuchElementException(); 
/* 1632 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Short2ShortRBTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1645 */         this.next = Short2ShortRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(short k) {
/* 1648 */         this();
/* 1649 */         if (this.next != null)
/* 1650 */           if (!Short2ShortRBTreeMap.Submap.this.bottom && Short2ShortRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1651 */             this.prev = null;
/* 1652 */           } else if (!Short2ShortRBTreeMap.Submap.this.top && Short2ShortRBTreeMap.this.compare(k, (this.prev = Short2ShortRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1653 */             this.next = null;
/*      */           } else {
/* 1655 */             this.next = Short2ShortRBTreeMap.this.locateKey(k);
/* 1656 */             if (Short2ShortRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1657 */               this.prev = this.next;
/* 1658 */               this.next = this.next.next();
/*      */             } else {
/* 1660 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1666 */         this.prev = this.prev.prev();
/* 1667 */         if (!Short2ShortRBTreeMap.Submap.this.bottom && this.prev != null && Short2ShortRBTreeMap.this.compare(this.prev.key, Short2ShortRBTreeMap.Submap.this.from) < 0)
/* 1668 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1672 */         this.next = this.next.next();
/* 1673 */         if (!Short2ShortRBTreeMap.Submap.this.top && this.next != null && Short2ShortRBTreeMap.this.compare(this.next.key, Short2ShortRBTreeMap.Submap.this.to) >= 0)
/* 1674 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Short2ShortMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(short k) {
/* 1681 */         super(k);
/*      */       }
/*      */       
/*      */       public Short2ShortMap.Entry next() {
/* 1685 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Short2ShortMap.Entry previous() {
/* 1689 */         return previousEntry();
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
/* 1707 */         super(from);
/*      */       }
/*      */       
/*      */       public short nextShort() {
/* 1711 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public short previousShort() {
/* 1715 */         return (previousEntry()).key;
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
/* 1731 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public short previousShort() {
/* 1735 */         return (previousEntry()).value;
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
/*      */   public Short2ShortRBTreeMap clone() {
/*      */     Short2ShortRBTreeMap c;
/*      */     try {
/* 1754 */       c = (Short2ShortRBTreeMap)super.clone();
/* 1755 */     } catch (CloneNotSupportedException cantHappen) {
/* 1756 */       throw new InternalError();
/*      */     } 
/* 1758 */     c.keys = null;
/* 1759 */     c.values = null;
/* 1760 */     c.entries = null;
/* 1761 */     c.allocatePaths();
/* 1762 */     if (this.count != 0) {
/*      */       
/* 1764 */       Entry rp = new Entry(), rq = new Entry();
/* 1765 */       Entry p = rp;
/* 1766 */       rp.left(this.tree);
/* 1767 */       Entry q = rq;
/* 1768 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1770 */         if (!p.pred()) {
/* 1771 */           Entry e = p.left.clone();
/* 1772 */           e.pred(q.left);
/* 1773 */           e.succ(q);
/* 1774 */           q.left(e);
/* 1775 */           p = p.left;
/* 1776 */           q = q.left;
/*      */         } else {
/* 1778 */           while (p.succ()) {
/* 1779 */             p = p.right;
/* 1780 */             if (p == null) {
/* 1781 */               q.right = null;
/* 1782 */               c.tree = rq.left;
/* 1783 */               c.firstEntry = c.tree;
/* 1784 */               while (c.firstEntry.left != null)
/* 1785 */                 c.firstEntry = c.firstEntry.left; 
/* 1786 */               c.lastEntry = c.tree;
/* 1787 */               while (c.lastEntry.right != null)
/* 1788 */                 c.lastEntry = c.lastEntry.right; 
/* 1789 */               return c;
/*      */             } 
/* 1791 */             q = q.right;
/*      */           } 
/* 1793 */           p = p.right;
/* 1794 */           q = q.right;
/*      */         } 
/* 1796 */         if (!p.succ()) {
/* 1797 */           Entry e = p.right.clone();
/* 1798 */           e.succ(q.right);
/* 1799 */           e.pred(q);
/* 1800 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1804 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1807 */     int n = this.count;
/* 1808 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1810 */     s.defaultWriteObject();
/* 1811 */     while (n-- != 0) {
/* 1812 */       Entry e = i.nextEntry();
/* 1813 */       s.writeShort(e.key);
/* 1814 */       s.writeShort(e.value);
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
/* 1835 */     if (n == 1) {
/* 1836 */       Entry entry = new Entry(s.readShort(), s.readShort());
/* 1837 */       entry.pred(pred);
/* 1838 */       entry.succ(succ);
/* 1839 */       entry.black(true);
/* 1840 */       return entry;
/*      */     } 
/* 1842 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1847 */       Entry entry = new Entry(s.readShort(), s.readShort());
/* 1848 */       entry.black(true);
/* 1849 */       entry.right(new Entry(s.readShort(), s.readShort()));
/* 1850 */       entry.right.pred(entry);
/* 1851 */       entry.pred(pred);
/* 1852 */       entry.right.succ(succ);
/* 1853 */       return entry;
/*      */     } 
/*      */     
/* 1856 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1857 */     Entry top = new Entry();
/* 1858 */     top.left(readTree(s, leftN, pred, top));
/* 1859 */     top.key = s.readShort();
/* 1860 */     top.value = s.readShort();
/* 1861 */     top.black(true);
/* 1862 */     top.right(readTree(s, rightN, top, succ));
/* 1863 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1864 */       top.right.black(false); 
/* 1865 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1868 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1873 */     setActualComparator();
/* 1874 */     allocatePaths();
/* 1875 */     if (this.count != 0) {
/* 1876 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1878 */       Entry e = this.tree;
/* 1879 */       while (e.left() != null)
/* 1880 */         e = e.left(); 
/* 1881 */       this.firstEntry = e;
/* 1882 */       e = this.tree;
/* 1883 */       while (e.right() != null)
/* 1884 */         e = e.right(); 
/* 1885 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2ShortRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */