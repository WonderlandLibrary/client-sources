/*      */ package it.unimi.dsi.fastutil.floats;
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
/*      */ public class Float2ShortRBTreeMap
/*      */   extends AbstractFloat2ShortSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Float2ShortMap.Entry> entries;
/*      */   protected transient FloatSortedSet keys;
/*      */   protected transient ShortCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Float> storedComparator;
/*      */   protected transient FloatComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public Float2ShortRBTreeMap() {
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
/*   91 */     this.actualComparator = FloatComparators.asFloatComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ShortRBTreeMap(Comparator<? super Float> c) {
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
/*      */   public Float2ShortRBTreeMap(Map<? extends Float, ? extends Short> m) {
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
/*      */   public Float2ShortRBTreeMap(SortedMap<Float, Short> m) {
/*  122 */     this(m.comparator());
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ShortRBTreeMap(Float2ShortMap m) {
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
/*      */   public Float2ShortRBTreeMap(Float2ShortSortedMap m) {
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
/*      */   public Float2ShortRBTreeMap(float[] k, short[] v, Comparator<? super Float> c) {
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
/*      */   public Float2ShortRBTreeMap(float[] k, short[] v) {
/*  178 */     this(k, v, (Comparator<? super Float>)null);
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
/*      */   final int compare(float k1, float k2) {
/*  206 */     return (this.actualComparator == null) ? Float.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry findKey(float k) {
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
/*      */   final Entry locateKey(float k) {
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
/*      */   public short addTo(float k, short incr) {
/*  270 */     Entry e = add(k);
/*  271 */     short oldValue = e.value;
/*  272 */     e.value = (short)(e.value + incr);
/*  273 */     return oldValue;
/*      */   }
/*      */   
/*      */   public short put(float k, short v) {
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
/*      */   private Entry add(float k) {
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
/*      */   public short remove(float k) {
/*  441 */     this.modified = false;
/*  442 */     if (this.tree == null)
/*  443 */       return this.defRetValue; 
/*  444 */     Entry p = this.tree;
/*      */     
/*  446 */     int i = 0;
/*  447 */     float kk = k;
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
/*      */     extends AbstractFloat2ShortMap.BasicEntry
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
/*  732 */       super(0.0F, (short)0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(float k, short v) {
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
/*  911 */       Map.Entry<Float, Short> e = (Map.Entry<Float, Short>)o;
/*  912 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && this.value == ((Short)e
/*  913 */         .getValue()).shortValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  917 */       return HashCommon.float2int(this.key) ^ this.value;
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
/*      */   public boolean containsKey(float k) {
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
/*      */   public short get(float k) {
/*  955 */     Entry e = findKey(k);
/*  956 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public float firstFloatKey() {
/*  960 */     if (this.tree == null)
/*  961 */       throw new NoSuchElementException(); 
/*  962 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public float lastFloatKey() {
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
/*      */     Float2ShortRBTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Float2ShortRBTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Float2ShortRBTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  999 */     int index = 0;
/*      */     TreeIterator() {
/* 1001 */       this.next = Float2ShortRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(float k) {
/* 1004 */       if ((this.next = Float2ShortRBTreeMap.this.locateKey(k)) != null)
/* 1005 */         if (Float2ShortRBTreeMap.this.compare(this.next.key, k) <= 0) {
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
/*      */     Float2ShortRBTreeMap.Entry nextEntry() {
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
/*      */     Float2ShortRBTreeMap.Entry previousEntry() {
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
/* 1058 */       Float2ShortRBTreeMap.this.remove(this.curr.key);
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
/*      */     implements ObjectListIterator<Float2ShortMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(float k) {
/* 1084 */       super(k);
/*      */     }
/*      */     
/*      */     public Float2ShortMap.Entry next() {
/* 1088 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Float2ShortMap.Entry previous() {
/* 1092 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Float2ShortMap.Entry> float2ShortEntrySet() {
/* 1097 */     if (this.entries == null)
/* 1098 */       this.entries = (ObjectSortedSet<Float2ShortMap.Entry>)new AbstractObjectSortedSet<Float2ShortMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Float2ShortMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Float2ShortMap.Entry> comparator() {
/* 1103 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Float2ShortMap.Entry> iterator() {
/* 1107 */             return (ObjectBidirectionalIterator<Float2ShortMap.Entry>)new Float2ShortRBTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Float2ShortMap.Entry> iterator(Float2ShortMap.Entry from) {
/* 1111 */             return (ObjectBidirectionalIterator<Float2ShortMap.Entry>)new Float2ShortRBTreeMap.EntryIterator(from.getFloatKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1116 */             if (!(o instanceof Map.Entry))
/* 1117 */               return false; 
/* 1118 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1119 */             if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1120 */               return false; 
/* 1121 */             if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1122 */               return false; 
/* 1123 */             Float2ShortRBTreeMap.Entry f = Float2ShortRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1124 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1129 */             if (!(o instanceof Map.Entry))
/* 1130 */               return false; 
/* 1131 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1132 */             if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1133 */               return false; 
/* 1134 */             if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1135 */               return false; 
/* 1136 */             Float2ShortRBTreeMap.Entry f = Float2ShortRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1137 */             if (f == null || f.getShortValue() != ((Short)e.getValue()).shortValue())
/* 1138 */               return false; 
/* 1139 */             Float2ShortRBTreeMap.this.remove(f.key);
/* 1140 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1144 */             return Float2ShortRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1148 */             Float2ShortRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Float2ShortMap.Entry first() {
/* 1152 */             return Float2ShortRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Float2ShortMap.Entry last() {
/* 1156 */             return Float2ShortRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Float2ShortMap.Entry> subSet(Float2ShortMap.Entry from, Float2ShortMap.Entry to) {
/* 1161 */             return Float2ShortRBTreeMap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2ShortEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Float2ShortMap.Entry> headSet(Float2ShortMap.Entry to) {
/* 1165 */             return Float2ShortRBTreeMap.this.headMap(to.getFloatKey()).float2ShortEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Float2ShortMap.Entry> tailSet(Float2ShortMap.Entry from) {
/* 1169 */             return Float2ShortRBTreeMap.this.tailMap(from.getFloatKey()).float2ShortEntrySet();
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
/*      */     implements FloatListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(float k) {
/* 1188 */       super(k);
/*      */     }
/*      */     
/*      */     public float nextFloat() {
/* 1192 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public float previousFloat() {
/* 1196 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractFloat2ShortSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public FloatBidirectionalIterator iterator() {
/* 1203 */       return new Float2ShortRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public FloatBidirectionalIterator iterator(float from) {
/* 1207 */       return new Float2ShortRBTreeMap.KeyIterator(from);
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
/*      */   public FloatSortedSet keySet() {
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
/* 1262 */             return (ShortIterator)new Float2ShortRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(short k) {
/* 1266 */             return Float2ShortRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1270 */             return Float2ShortRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1274 */             Float2ShortRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1277 */     return this.values;
/*      */   }
/*      */   
/*      */   public FloatComparator comparator() {
/* 1281 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Float2ShortSortedMap headMap(float to) {
/* 1285 */     return new Submap(0.0F, true, to, false);
/*      */   }
/*      */   
/*      */   public Float2ShortSortedMap tailMap(float from) {
/* 1289 */     return new Submap(from, false, 0.0F, true);
/*      */   }
/*      */   
/*      */   public Float2ShortSortedMap subMap(float from, float to) {
/* 1293 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractFloat2ShortSortedMap
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     float from;
/*      */ 
/*      */ 
/*      */     
/*      */     float to;
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
/*      */     protected transient ObjectSortedSet<Float2ShortMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient FloatSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ShortCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(float from, boolean bottom, float to, boolean top) {
/* 1337 */       if (!bottom && !top && Float2ShortRBTreeMap.this.compare(from, to) > 0)
/* 1338 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1339 */       this.from = from;
/* 1340 */       this.bottom = bottom;
/* 1341 */       this.to = to;
/* 1342 */       this.top = top;
/* 1343 */       this.defRetValue = Float2ShortRBTreeMap.this.defRetValue;
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
/*      */     final boolean in(float k) {
/* 1361 */       return ((this.bottom || Float2ShortRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Float2ShortRBTreeMap.this
/* 1362 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Float2ShortMap.Entry> float2ShortEntrySet() {
/* 1366 */       if (this.entries == null)
/* 1367 */         this.entries = (ObjectSortedSet<Float2ShortMap.Entry>)new AbstractObjectSortedSet<Float2ShortMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Float2ShortMap.Entry> iterator() {
/* 1370 */               return (ObjectBidirectionalIterator<Float2ShortMap.Entry>)new Float2ShortRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */             
/*      */             public ObjectBidirectionalIterator<Float2ShortMap.Entry> iterator(Float2ShortMap.Entry from) {
/* 1374 */               return (ObjectBidirectionalIterator<Float2ShortMap.Entry>)new Float2ShortRBTreeMap.Submap.SubmapEntryIterator(from.getFloatKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Float2ShortMap.Entry> comparator() {
/* 1378 */               return Float2ShortRBTreeMap.this.float2ShortEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1383 */               if (!(o instanceof Map.Entry))
/* 1384 */                 return false; 
/* 1385 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1386 */               if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1387 */                 return false; 
/* 1388 */               if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1389 */                 return false; 
/* 1390 */               Float2ShortRBTreeMap.Entry f = Float2ShortRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1391 */               return (f != null && Float2ShortRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1396 */               if (!(o instanceof Map.Entry))
/* 1397 */                 return false; 
/* 1398 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1399 */               if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1400 */                 return false; 
/* 1401 */               if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1402 */                 return false; 
/* 1403 */               Float2ShortRBTreeMap.Entry f = Float2ShortRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1404 */               if (f != null && Float2ShortRBTreeMap.Submap.this.in(f.key))
/* 1405 */                 Float2ShortRBTreeMap.Submap.this.remove(f.key); 
/* 1406 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1410 */               int c = 0;
/* 1411 */               for (ObjectBidirectionalIterator<Float2ShortMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1412 */                 c++; 
/* 1413 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1417 */               return !(new Float2ShortRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1421 */               Float2ShortRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Float2ShortMap.Entry first() {
/* 1425 */               return Float2ShortRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Float2ShortMap.Entry last() {
/* 1429 */               return Float2ShortRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Float2ShortMap.Entry> subSet(Float2ShortMap.Entry from, Float2ShortMap.Entry to) {
/* 1434 */               return Float2ShortRBTreeMap.Submap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2ShortEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Float2ShortMap.Entry> headSet(Float2ShortMap.Entry to) {
/* 1438 */               return Float2ShortRBTreeMap.Submap.this.headMap(to.getFloatKey()).float2ShortEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Float2ShortMap.Entry> tailSet(Float2ShortMap.Entry from) {
/* 1442 */               return Float2ShortRBTreeMap.Submap.this.tailMap(from.getFloatKey()).float2ShortEntrySet();
/*      */             }
/*      */           }; 
/* 1445 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractFloat2ShortSortedMap.KeySet {
/*      */       public FloatBidirectionalIterator iterator() {
/* 1450 */         return new Float2ShortRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public FloatBidirectionalIterator iterator(float from) {
/* 1454 */         return new Float2ShortRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public FloatSortedSet keySet() {
/* 1459 */       if (this.keys == null)
/* 1460 */         this.keys = new KeySet(); 
/* 1461 */       return this.keys;
/*      */     }
/*      */     
/*      */     public ShortCollection values() {
/* 1465 */       if (this.values == null)
/* 1466 */         this.values = (ShortCollection)new AbstractShortCollection()
/*      */           {
/*      */             public ShortIterator iterator() {
/* 1469 */               return (ShortIterator)new Float2ShortRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(short k) {
/* 1473 */               return Float2ShortRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1477 */               return Float2ShortRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1481 */               Float2ShortRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1484 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(float k) {
/* 1489 */       return (in(k) && Float2ShortRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(short v) {
/* 1493 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1495 */       while (i.hasNext()) {
/* 1496 */         short ev = (i.nextEntry()).value;
/* 1497 */         if (ev == v)
/* 1498 */           return true; 
/*      */       } 
/* 1500 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public short get(float k) {
/* 1506 */       float kk = k; Float2ShortRBTreeMap.Entry e;
/* 1507 */       return (in(kk) && (e = Float2ShortRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public short put(float k, short v) {
/* 1511 */       Float2ShortRBTreeMap.this.modified = false;
/* 1512 */       if (!in(k))
/* 1513 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1514 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1515 */       short oldValue = Float2ShortRBTreeMap.this.put(k, v);
/* 1516 */       return Float2ShortRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public short remove(float k) {
/* 1521 */       Float2ShortRBTreeMap.this.modified = false;
/* 1522 */       if (!in(k))
/* 1523 */         return this.defRetValue; 
/* 1524 */       short oldValue = Float2ShortRBTreeMap.this.remove(k);
/* 1525 */       return Float2ShortRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1529 */       SubmapIterator i = new SubmapIterator();
/* 1530 */       int n = 0;
/* 1531 */       while (i.hasNext()) {
/* 1532 */         n++;
/* 1533 */         i.nextEntry();
/*      */       } 
/* 1535 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1539 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public FloatComparator comparator() {
/* 1543 */       return Float2ShortRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Float2ShortSortedMap headMap(float to) {
/* 1547 */       if (this.top)
/* 1548 */         return new Submap(this.from, this.bottom, to, false); 
/* 1549 */       return (Float2ShortRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Float2ShortSortedMap tailMap(float from) {
/* 1553 */       if (this.bottom)
/* 1554 */         return new Submap(from, false, this.to, this.top); 
/* 1555 */       return (Float2ShortRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Float2ShortSortedMap subMap(float from, float to) {
/* 1559 */       if (this.top && this.bottom)
/* 1560 */         return new Submap(from, false, to, false); 
/* 1561 */       if (!this.top)
/* 1562 */         to = (Float2ShortRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1563 */       if (!this.bottom)
/* 1564 */         from = (Float2ShortRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1565 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1566 */         return this; 
/* 1567 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Float2ShortRBTreeMap.Entry firstEntry() {
/*      */       Float2ShortRBTreeMap.Entry e;
/* 1576 */       if (Float2ShortRBTreeMap.this.tree == null) {
/* 1577 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1581 */       if (this.bottom) {
/* 1582 */         e = Float2ShortRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1584 */         e = Float2ShortRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1586 */         if (Float2ShortRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1587 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1591 */       if (e == null || (!this.top && Float2ShortRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1592 */         return null; 
/* 1593 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Float2ShortRBTreeMap.Entry lastEntry() {
/*      */       Float2ShortRBTreeMap.Entry e;
/* 1602 */       if (Float2ShortRBTreeMap.this.tree == null) {
/* 1603 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1607 */       if (this.top) {
/* 1608 */         e = Float2ShortRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1610 */         e = Float2ShortRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1612 */         if (Float2ShortRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1613 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1617 */       if (e == null || (!this.bottom && Float2ShortRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1618 */         return null; 
/* 1619 */       return e;
/*      */     }
/*      */     
/*      */     public float firstFloatKey() {
/* 1623 */       Float2ShortRBTreeMap.Entry e = firstEntry();
/* 1624 */       if (e == null)
/* 1625 */         throw new NoSuchElementException(); 
/* 1626 */       return e.key;
/*      */     }
/*      */     
/*      */     public float lastFloatKey() {
/* 1630 */       Float2ShortRBTreeMap.Entry e = lastEntry();
/* 1631 */       if (e == null)
/* 1632 */         throw new NoSuchElementException(); 
/* 1633 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Float2ShortRBTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1646 */         this.next = Float2ShortRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(float k) {
/* 1649 */         this();
/* 1650 */         if (this.next != null)
/* 1651 */           if (!Float2ShortRBTreeMap.Submap.this.bottom && Float2ShortRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1652 */             this.prev = null;
/* 1653 */           } else if (!Float2ShortRBTreeMap.Submap.this.top && Float2ShortRBTreeMap.this.compare(k, (this.prev = Float2ShortRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1654 */             this.next = null;
/*      */           } else {
/* 1656 */             this.next = Float2ShortRBTreeMap.this.locateKey(k);
/* 1657 */             if (Float2ShortRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1658 */               this.prev = this.next;
/* 1659 */               this.next = this.next.next();
/*      */             } else {
/* 1661 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1667 */         this.prev = this.prev.prev();
/* 1668 */         if (!Float2ShortRBTreeMap.Submap.this.bottom && this.prev != null && Float2ShortRBTreeMap.this.compare(this.prev.key, Float2ShortRBTreeMap.Submap.this.from) < 0)
/* 1669 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1673 */         this.next = this.next.next();
/* 1674 */         if (!Float2ShortRBTreeMap.Submap.this.top && this.next != null && Float2ShortRBTreeMap.this.compare(this.next.key, Float2ShortRBTreeMap.Submap.this.to) >= 0)
/* 1675 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Float2ShortMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(float k) {
/* 1682 */         super(k);
/*      */       }
/*      */       
/*      */       public Float2ShortMap.Entry next() {
/* 1686 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Float2ShortMap.Entry previous() {
/* 1690 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements FloatListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(float from) {
/* 1708 */         super(from);
/*      */       }
/*      */       
/*      */       public float nextFloat() {
/* 1712 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public float previousFloat() {
/* 1716 */         return (previousEntry()).key;
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
/* 1732 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public short previousShort() {
/* 1736 */         return (previousEntry()).value;
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
/*      */   public Float2ShortRBTreeMap clone() {
/*      */     Float2ShortRBTreeMap c;
/*      */     try {
/* 1755 */       c = (Float2ShortRBTreeMap)super.clone();
/* 1756 */     } catch (CloneNotSupportedException cantHappen) {
/* 1757 */       throw new InternalError();
/*      */     } 
/* 1759 */     c.keys = null;
/* 1760 */     c.values = null;
/* 1761 */     c.entries = null;
/* 1762 */     c.allocatePaths();
/* 1763 */     if (this.count != 0) {
/*      */       
/* 1765 */       Entry rp = new Entry(), rq = new Entry();
/* 1766 */       Entry p = rp;
/* 1767 */       rp.left(this.tree);
/* 1768 */       Entry q = rq;
/* 1769 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1771 */         if (!p.pred()) {
/* 1772 */           Entry e = p.left.clone();
/* 1773 */           e.pred(q.left);
/* 1774 */           e.succ(q);
/* 1775 */           q.left(e);
/* 1776 */           p = p.left;
/* 1777 */           q = q.left;
/*      */         } else {
/* 1779 */           while (p.succ()) {
/* 1780 */             p = p.right;
/* 1781 */             if (p == null) {
/* 1782 */               q.right = null;
/* 1783 */               c.tree = rq.left;
/* 1784 */               c.firstEntry = c.tree;
/* 1785 */               while (c.firstEntry.left != null)
/* 1786 */                 c.firstEntry = c.firstEntry.left; 
/* 1787 */               c.lastEntry = c.tree;
/* 1788 */               while (c.lastEntry.right != null)
/* 1789 */                 c.lastEntry = c.lastEntry.right; 
/* 1790 */               return c;
/*      */             } 
/* 1792 */             q = q.right;
/*      */           } 
/* 1794 */           p = p.right;
/* 1795 */           q = q.right;
/*      */         } 
/* 1797 */         if (!p.succ()) {
/* 1798 */           Entry e = p.right.clone();
/* 1799 */           e.succ(q.right);
/* 1800 */           e.pred(q);
/* 1801 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1805 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1808 */     int n = this.count;
/* 1809 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1811 */     s.defaultWriteObject();
/* 1812 */     while (n-- != 0) {
/* 1813 */       Entry e = i.nextEntry();
/* 1814 */       s.writeFloat(e.key);
/* 1815 */       s.writeShort(e.value);
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
/* 1836 */     if (n == 1) {
/* 1837 */       Entry entry = new Entry(s.readFloat(), s.readShort());
/* 1838 */       entry.pred(pred);
/* 1839 */       entry.succ(succ);
/* 1840 */       entry.black(true);
/* 1841 */       return entry;
/*      */     } 
/* 1843 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1848 */       Entry entry = new Entry(s.readFloat(), s.readShort());
/* 1849 */       entry.black(true);
/* 1850 */       entry.right(new Entry(s.readFloat(), s.readShort()));
/* 1851 */       entry.right.pred(entry);
/* 1852 */       entry.pred(pred);
/* 1853 */       entry.right.succ(succ);
/* 1854 */       return entry;
/*      */     } 
/*      */     
/* 1857 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1858 */     Entry top = new Entry();
/* 1859 */     top.left(readTree(s, leftN, pred, top));
/* 1860 */     top.key = s.readFloat();
/* 1861 */     top.value = s.readShort();
/* 1862 */     top.black(true);
/* 1863 */     top.right(readTree(s, rightN, top, succ));
/* 1864 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1865 */       top.right.black(false); 
/* 1866 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1869 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1874 */     setActualComparator();
/* 1875 */     allocatePaths();
/* 1876 */     if (this.count != 0) {
/* 1877 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1879 */       Entry e = this.tree;
/* 1880 */       while (e.left() != null)
/* 1881 */         e = e.left(); 
/* 1882 */       this.firstEntry = e;
/* 1883 */       e = this.tree;
/* 1884 */       while (e.right() != null)
/* 1885 */         e = e.right(); 
/* 1886 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2ShortRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */