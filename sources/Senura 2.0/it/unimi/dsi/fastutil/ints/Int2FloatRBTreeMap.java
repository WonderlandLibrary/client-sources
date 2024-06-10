/*      */ package it.unimi.dsi.fastutil.ints;
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
/*      */ public class Int2FloatRBTreeMap
/*      */   extends AbstractInt2FloatSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Int2FloatMap.Entry> entries;
/*      */   protected transient IntSortedSet keys;
/*      */   protected transient FloatCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Integer> storedComparator;
/*      */   protected transient IntComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public Int2FloatRBTreeMap() {
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
/*   91 */     this.actualComparator = IntComparators.asIntComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2FloatRBTreeMap(Comparator<? super Integer> c) {
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
/*      */   public Int2FloatRBTreeMap(Map<? extends Integer, ? extends Float> m) {
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
/*      */   public Int2FloatRBTreeMap(SortedMap<Integer, Float> m) {
/*  122 */     this(m.comparator());
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2FloatRBTreeMap(Int2FloatMap m) {
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
/*      */   public Int2FloatRBTreeMap(Int2FloatSortedMap m) {
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
/*      */   public Int2FloatRBTreeMap(int[] k, float[] v, Comparator<? super Integer> c) {
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
/*      */   public Int2FloatRBTreeMap(int[] k, float[] v) {
/*  178 */     this(k, v, (Comparator<? super Integer>)null);
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
/*  206 */     return (this.actualComparator == null) ? Integer.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry findKey(int k) {
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
/*      */   final Entry locateKey(int k) {
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
/*      */   public float addTo(int k, float incr) {
/*  270 */     Entry e = add(k);
/*  271 */     float oldValue = e.value;
/*  272 */     e.value += incr;
/*  273 */     return oldValue;
/*      */   }
/*      */   
/*      */   public float put(int k, float v) {
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
/*      */   private Entry add(int k) {
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
/*      */   public float remove(int k) {
/*  441 */     this.modified = false;
/*  442 */     if (this.tree == null)
/*  443 */       return this.defRetValue; 
/*  444 */     Entry p = this.tree;
/*      */     
/*  446 */     int i = 0;
/*  447 */     int kk = k;
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
/*      */     extends AbstractInt2FloatMap.BasicEntry
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
/*  732 */       super(0, 0.0F);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(int k, float v) {
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
/*  911 */       Map.Entry<Integer, Float> e = (Map.Entry<Integer, Float>)o;
/*  912 */       return (this.key == ((Integer)e.getKey()).intValue() && 
/*  913 */         Float.floatToIntBits(this.value) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  917 */       return this.key ^ HashCommon.float2int(this.value);
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
/*      */   public boolean containsKey(int k) {
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
/*      */   public float get(int k) {
/*  955 */     Entry e = findKey(k);
/*  956 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public int firstIntKey() {
/*  960 */     if (this.tree == null)
/*  961 */       throw new NoSuchElementException(); 
/*  962 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public int lastIntKey() {
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
/*      */     Int2FloatRBTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Int2FloatRBTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Int2FloatRBTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  999 */     int index = 0;
/*      */     TreeIterator() {
/* 1001 */       this.next = Int2FloatRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(int k) {
/* 1004 */       if ((this.next = Int2FloatRBTreeMap.this.locateKey(k)) != null)
/* 1005 */         if (Int2FloatRBTreeMap.this.compare(this.next.key, k) <= 0) {
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
/*      */     Int2FloatRBTreeMap.Entry nextEntry() {
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
/*      */     Int2FloatRBTreeMap.Entry previousEntry() {
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
/* 1058 */       Int2FloatRBTreeMap.this.remove(this.curr.key);
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
/*      */     implements ObjectListIterator<Int2FloatMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(int k) {
/* 1084 */       super(k);
/*      */     }
/*      */     
/*      */     public Int2FloatMap.Entry next() {
/* 1088 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Int2FloatMap.Entry previous() {
/* 1092 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Int2FloatMap.Entry> int2FloatEntrySet() {
/* 1097 */     if (this.entries == null)
/* 1098 */       this.entries = (ObjectSortedSet<Int2FloatMap.Entry>)new AbstractObjectSortedSet<Int2FloatMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Int2FloatMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Int2FloatMap.Entry> comparator() {
/* 1103 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Int2FloatMap.Entry> iterator() {
/* 1107 */             return (ObjectBidirectionalIterator<Int2FloatMap.Entry>)new Int2FloatRBTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Int2FloatMap.Entry> iterator(Int2FloatMap.Entry from) {
/* 1111 */             return (ObjectBidirectionalIterator<Int2FloatMap.Entry>)new Int2FloatRBTreeMap.EntryIterator(from.getIntKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1116 */             if (!(o instanceof Map.Entry))
/* 1117 */               return false; 
/* 1118 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1119 */             if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1120 */               return false; 
/* 1121 */             if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1122 */               return false; 
/* 1123 */             Int2FloatRBTreeMap.Entry f = Int2FloatRBTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1124 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1129 */             if (!(o instanceof Map.Entry))
/* 1130 */               return false; 
/* 1131 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1132 */             if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1133 */               return false; 
/* 1134 */             if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1135 */               return false; 
/* 1136 */             Int2FloatRBTreeMap.Entry f = Int2FloatRBTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1137 */             if (f == null || Float.floatToIntBits(f.getFloatValue()) != 
/* 1138 */               Float.floatToIntBits(((Float)e.getValue()).floatValue()))
/* 1139 */               return false; 
/* 1140 */             Int2FloatRBTreeMap.this.remove(f.key);
/* 1141 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1145 */             return Int2FloatRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1149 */             Int2FloatRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Int2FloatMap.Entry first() {
/* 1153 */             return Int2FloatRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Int2FloatMap.Entry last() {
/* 1157 */             return Int2FloatRBTreeMap.this.lastEntry;
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Int2FloatMap.Entry> subSet(Int2FloatMap.Entry from, Int2FloatMap.Entry to) {
/* 1161 */             return Int2FloatRBTreeMap.this.subMap(from.getIntKey(), to.getIntKey()).int2FloatEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Int2FloatMap.Entry> headSet(Int2FloatMap.Entry to) {
/* 1165 */             return Int2FloatRBTreeMap.this.headMap(to.getIntKey()).int2FloatEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Int2FloatMap.Entry> tailSet(Int2FloatMap.Entry from) {
/* 1169 */             return Int2FloatRBTreeMap.this.tailMap(from.getIntKey()).int2FloatEntrySet();
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
/*      */     implements IntListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(int k) {
/* 1188 */       super(k);
/*      */     }
/*      */     
/*      */     public int nextInt() {
/* 1192 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public int previousInt() {
/* 1196 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractInt2FloatSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public IntBidirectionalIterator iterator() {
/* 1203 */       return new Int2FloatRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public IntBidirectionalIterator iterator(int from) {
/* 1207 */       return new Int2FloatRBTreeMap.KeyIterator(from);
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
/*      */     implements FloatListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1239 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public float previousFloat() {
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
/*      */   public FloatCollection values() {
/* 1258 */     if (this.values == null)
/* 1259 */       this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1262 */             return (FloatIterator)new Int2FloatRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(float k) {
/* 1266 */             return Int2FloatRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1270 */             return Int2FloatRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1274 */             Int2FloatRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1277 */     return this.values;
/*      */   }
/*      */   
/*      */   public IntComparator comparator() {
/* 1281 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Int2FloatSortedMap headMap(int to) {
/* 1285 */     return new Submap(0, true, to, false);
/*      */   }
/*      */   
/*      */   public Int2FloatSortedMap tailMap(int from) {
/* 1289 */     return new Submap(from, false, 0, true);
/*      */   }
/*      */   
/*      */   public Int2FloatSortedMap subMap(int from, int to) {
/* 1293 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractInt2FloatSortedMap
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
/*      */     protected transient ObjectSortedSet<Int2FloatMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient IntSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient FloatCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(int from, boolean bottom, int to, boolean top) {
/* 1337 */       if (!bottom && !top && Int2FloatRBTreeMap.this.compare(from, to) > 0)
/* 1338 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1339 */       this.from = from;
/* 1340 */       this.bottom = bottom;
/* 1341 */       this.to = to;
/* 1342 */       this.top = top;
/* 1343 */       this.defRetValue = Int2FloatRBTreeMap.this.defRetValue;
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
/*      */     final boolean in(int k) {
/* 1361 */       return ((this.bottom || Int2FloatRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Int2FloatRBTreeMap.this
/* 1362 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Int2FloatMap.Entry> int2FloatEntrySet() {
/* 1366 */       if (this.entries == null)
/* 1367 */         this.entries = (ObjectSortedSet<Int2FloatMap.Entry>)new AbstractObjectSortedSet<Int2FloatMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Int2FloatMap.Entry> iterator() {
/* 1370 */               return (ObjectBidirectionalIterator<Int2FloatMap.Entry>)new Int2FloatRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */             
/*      */             public ObjectBidirectionalIterator<Int2FloatMap.Entry> iterator(Int2FloatMap.Entry from) {
/* 1374 */               return (ObjectBidirectionalIterator<Int2FloatMap.Entry>)new Int2FloatRBTreeMap.Submap.SubmapEntryIterator(from.getIntKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Int2FloatMap.Entry> comparator() {
/* 1378 */               return Int2FloatRBTreeMap.this.int2FloatEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1383 */               if (!(o instanceof Map.Entry))
/* 1384 */                 return false; 
/* 1385 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1386 */               if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1387 */                 return false; 
/* 1388 */               if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1389 */                 return false; 
/* 1390 */               Int2FloatRBTreeMap.Entry f = Int2FloatRBTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1391 */               return (f != null && Int2FloatRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1396 */               if (!(o instanceof Map.Entry))
/* 1397 */                 return false; 
/* 1398 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1399 */               if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1400 */                 return false; 
/* 1401 */               if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1402 */                 return false; 
/* 1403 */               Int2FloatRBTreeMap.Entry f = Int2FloatRBTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1404 */               if (f != null && Int2FloatRBTreeMap.Submap.this.in(f.key))
/* 1405 */                 Int2FloatRBTreeMap.Submap.this.remove(f.key); 
/* 1406 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1410 */               int c = 0;
/* 1411 */               for (ObjectBidirectionalIterator<Int2FloatMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1412 */                 c++; 
/* 1413 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1417 */               return !(new Int2FloatRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1421 */               Int2FloatRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Int2FloatMap.Entry first() {
/* 1425 */               return Int2FloatRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Int2FloatMap.Entry last() {
/* 1429 */               return Int2FloatRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Int2FloatMap.Entry> subSet(Int2FloatMap.Entry from, Int2FloatMap.Entry to) {
/* 1433 */               return Int2FloatRBTreeMap.Submap.this.subMap(from.getIntKey(), to.getIntKey()).int2FloatEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Int2FloatMap.Entry> headSet(Int2FloatMap.Entry to) {
/* 1437 */               return Int2FloatRBTreeMap.Submap.this.headMap(to.getIntKey()).int2FloatEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Int2FloatMap.Entry> tailSet(Int2FloatMap.Entry from) {
/* 1441 */               return Int2FloatRBTreeMap.Submap.this.tailMap(from.getIntKey()).int2FloatEntrySet();
/*      */             }
/*      */           }; 
/* 1444 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractInt2FloatSortedMap.KeySet {
/*      */       public IntBidirectionalIterator iterator() {
/* 1449 */         return new Int2FloatRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public IntBidirectionalIterator iterator(int from) {
/* 1453 */         return new Int2FloatRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public IntSortedSet keySet() {
/* 1458 */       if (this.keys == null)
/* 1459 */         this.keys = new KeySet(); 
/* 1460 */       return this.keys;
/*      */     }
/*      */     
/*      */     public FloatCollection values() {
/* 1464 */       if (this.values == null)
/* 1465 */         this.values = (FloatCollection)new AbstractFloatCollection()
/*      */           {
/*      */             public FloatIterator iterator() {
/* 1468 */               return (FloatIterator)new Int2FloatRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(float k) {
/* 1472 */               return Int2FloatRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1476 */               return Int2FloatRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1480 */               Int2FloatRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1483 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(int k) {
/* 1488 */       return (in(k) && Int2FloatRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(float v) {
/* 1492 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1494 */       while (i.hasNext()) {
/* 1495 */         float ev = (i.nextEntry()).value;
/* 1496 */         if (Float.floatToIntBits(ev) == Float.floatToIntBits(v))
/* 1497 */           return true; 
/*      */       } 
/* 1499 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public float get(int k) {
/* 1505 */       int kk = k; Int2FloatRBTreeMap.Entry e;
/* 1506 */       return (in(kk) && (e = Int2FloatRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public float put(int k, float v) {
/* 1510 */       Int2FloatRBTreeMap.this.modified = false;
/* 1511 */       if (!in(k))
/* 1512 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1513 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1514 */       float oldValue = Int2FloatRBTreeMap.this.put(k, v);
/* 1515 */       return Int2FloatRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public float remove(int k) {
/* 1520 */       Int2FloatRBTreeMap.this.modified = false;
/* 1521 */       if (!in(k))
/* 1522 */         return this.defRetValue; 
/* 1523 */       float oldValue = Int2FloatRBTreeMap.this.remove(k);
/* 1524 */       return Int2FloatRBTreeMap.this.modified ? oldValue : this.defRetValue;
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
/*      */     public IntComparator comparator() {
/* 1542 */       return Int2FloatRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Int2FloatSortedMap headMap(int to) {
/* 1546 */       if (this.top)
/* 1547 */         return new Submap(this.from, this.bottom, to, false); 
/* 1548 */       return (Int2FloatRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Int2FloatSortedMap tailMap(int from) {
/* 1552 */       if (this.bottom)
/* 1553 */         return new Submap(from, false, this.to, this.top); 
/* 1554 */       return (Int2FloatRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Int2FloatSortedMap subMap(int from, int to) {
/* 1558 */       if (this.top && this.bottom)
/* 1559 */         return new Submap(from, false, to, false); 
/* 1560 */       if (!this.top)
/* 1561 */         to = (Int2FloatRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1562 */       if (!this.bottom)
/* 1563 */         from = (Int2FloatRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1564 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1565 */         return this; 
/* 1566 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Int2FloatRBTreeMap.Entry firstEntry() {
/*      */       Int2FloatRBTreeMap.Entry e;
/* 1575 */       if (Int2FloatRBTreeMap.this.tree == null) {
/* 1576 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1580 */       if (this.bottom) {
/* 1581 */         e = Int2FloatRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1583 */         e = Int2FloatRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1585 */         if (Int2FloatRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1586 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1590 */       if (e == null || (!this.top && Int2FloatRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1591 */         return null; 
/* 1592 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Int2FloatRBTreeMap.Entry lastEntry() {
/*      */       Int2FloatRBTreeMap.Entry e;
/* 1601 */       if (Int2FloatRBTreeMap.this.tree == null) {
/* 1602 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1606 */       if (this.top) {
/* 1607 */         e = Int2FloatRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1609 */         e = Int2FloatRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1611 */         if (Int2FloatRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1612 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1616 */       if (e == null || (!this.bottom && Int2FloatRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1617 */         return null; 
/* 1618 */       return e;
/*      */     }
/*      */     
/*      */     public int firstIntKey() {
/* 1622 */       Int2FloatRBTreeMap.Entry e = firstEntry();
/* 1623 */       if (e == null)
/* 1624 */         throw new NoSuchElementException(); 
/* 1625 */       return e.key;
/*      */     }
/*      */     
/*      */     public int lastIntKey() {
/* 1629 */       Int2FloatRBTreeMap.Entry e = lastEntry();
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
/*      */       extends Int2FloatRBTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1645 */         this.next = Int2FloatRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(int k) {
/* 1648 */         this();
/* 1649 */         if (this.next != null)
/* 1650 */           if (!Int2FloatRBTreeMap.Submap.this.bottom && Int2FloatRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1651 */             this.prev = null;
/* 1652 */           } else if (!Int2FloatRBTreeMap.Submap.this.top && Int2FloatRBTreeMap.this.compare(k, (this.prev = Int2FloatRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1653 */             this.next = null;
/*      */           } else {
/* 1655 */             this.next = Int2FloatRBTreeMap.this.locateKey(k);
/* 1656 */             if (Int2FloatRBTreeMap.this.compare(this.next.key, k) <= 0) {
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
/* 1667 */         if (!Int2FloatRBTreeMap.Submap.this.bottom && this.prev != null && Int2FloatRBTreeMap.this.compare(this.prev.key, Int2FloatRBTreeMap.Submap.this.from) < 0)
/* 1668 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1672 */         this.next = this.next.next();
/* 1673 */         if (!Int2FloatRBTreeMap.Submap.this.top && this.next != null && Int2FloatRBTreeMap.this.compare(this.next.key, Int2FloatRBTreeMap.Submap.this.to) >= 0)
/* 1674 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Int2FloatMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(int k) {
/* 1681 */         super(k);
/*      */       }
/*      */       
/*      */       public Int2FloatMap.Entry next() {
/* 1685 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Int2FloatMap.Entry previous() {
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
/*      */       implements IntListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(int from) {
/* 1707 */         super(from);
/*      */       }
/*      */       
/*      */       public int nextInt() {
/* 1711 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public int previousInt() {
/* 1715 */         return (previousEntry()).key;
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
/* 1731 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public float previousFloat() {
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
/*      */   public Int2FloatRBTreeMap clone() {
/*      */     Int2FloatRBTreeMap c;
/*      */     try {
/* 1754 */       c = (Int2FloatRBTreeMap)super.clone();
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
/* 1813 */       s.writeInt(e.key);
/* 1814 */       s.writeFloat(e.value);
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
/* 1836 */       Entry entry = new Entry(s.readInt(), s.readFloat());
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
/* 1847 */       Entry entry = new Entry(s.readInt(), s.readFloat());
/* 1848 */       entry.black(true);
/* 1849 */       entry.right(new Entry(s.readInt(), s.readFloat()));
/* 1850 */       entry.right.pred(entry);
/* 1851 */       entry.pred(pred);
/* 1852 */       entry.right.succ(succ);
/* 1853 */       return entry;
/*      */     } 
/*      */     
/* 1856 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1857 */     Entry top = new Entry();
/* 1858 */     top.left(readTree(s, leftN, pred, top));
/* 1859 */     top.key = s.readInt();
/* 1860 */     top.value = s.readFloat();
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2FloatRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */