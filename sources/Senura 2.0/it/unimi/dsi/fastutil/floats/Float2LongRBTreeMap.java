/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*      */ import it.unimi.dsi.fastutil.longs.LongListIterator;
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
/*      */ public class Float2LongRBTreeMap
/*      */   extends AbstractFloat2LongSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Float2LongMap.Entry> entries;
/*      */   protected transient FloatSortedSet keys;
/*      */   protected transient LongCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Float> storedComparator;
/*      */   protected transient FloatComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public Float2LongRBTreeMap() {
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
/*      */   public Float2LongRBTreeMap(Comparator<? super Float> c) {
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
/*      */   public Float2LongRBTreeMap(Map<? extends Float, ? extends Long> m) {
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
/*      */   public Float2LongRBTreeMap(SortedMap<Float, Long> m) {
/*  122 */     this(m.comparator());
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongRBTreeMap(Float2LongMap m) {
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
/*      */   public Float2LongRBTreeMap(Float2LongSortedMap m) {
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
/*      */   public Float2LongRBTreeMap(float[] k, long[] v, Comparator<? super Float> c) {
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
/*      */   public Float2LongRBTreeMap(float[] k, long[] v) {
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
/*      */   public long addTo(float k, long incr) {
/*  270 */     Entry e = add(k);
/*  271 */     long oldValue = e.value;
/*  272 */     e.value += incr;
/*  273 */     return oldValue;
/*      */   }
/*      */   
/*      */   public long put(float k, long v) {
/*  277 */     Entry e = add(k);
/*  278 */     long oldValue = e.value;
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
/*      */   public long remove(float k) {
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
/*      */   public boolean containsValue(long v) {
/*  687 */     ValueIterator i = new ValueIterator();
/*      */     
/*  689 */     int j = this.count;
/*  690 */     while (j-- != 0) {
/*  691 */       long ev = i.nextLong();
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
/*      */     extends AbstractFloat2LongMap.BasicEntry
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
/*  732 */       super(0.0F, 0L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(float k, long v) {
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
/*      */     public long setValue(long value) {
/*  888 */       long oldValue = this.value;
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
/*  911 */       Map.Entry<Float, Long> e = (Map.Entry<Float, Long>)o;
/*  912 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && this.value == ((Long)e
/*  913 */         .getValue()).longValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  917 */       return HashCommon.float2int(this.key) ^ HashCommon.long2int(this.value);
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
/*      */   public long get(float k) {
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
/*      */     Float2LongRBTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Float2LongRBTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Float2LongRBTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  999 */     int index = 0;
/*      */     TreeIterator() {
/* 1001 */       this.next = Float2LongRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(float k) {
/* 1004 */       if ((this.next = Float2LongRBTreeMap.this.locateKey(k)) != null)
/* 1005 */         if (Float2LongRBTreeMap.this.compare(this.next.key, k) <= 0) {
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
/*      */     Float2LongRBTreeMap.Entry nextEntry() {
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
/*      */     Float2LongRBTreeMap.Entry previousEntry() {
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
/* 1058 */       Float2LongRBTreeMap.this.remove(this.curr.key);
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
/*      */     implements ObjectListIterator<Float2LongMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(float k) {
/* 1084 */       super(k);
/*      */     }
/*      */     
/*      */     public Float2LongMap.Entry next() {
/* 1088 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Float2LongMap.Entry previous() {
/* 1092 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Float2LongMap.Entry> float2LongEntrySet() {
/* 1097 */     if (this.entries == null)
/* 1098 */       this.entries = (ObjectSortedSet<Float2LongMap.Entry>)new AbstractObjectSortedSet<Float2LongMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Float2LongMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Float2LongMap.Entry> comparator() {
/* 1103 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Float2LongMap.Entry> iterator() {
/* 1107 */             return (ObjectBidirectionalIterator<Float2LongMap.Entry>)new Float2LongRBTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Float2LongMap.Entry> iterator(Float2LongMap.Entry from) {
/* 1111 */             return (ObjectBidirectionalIterator<Float2LongMap.Entry>)new Float2LongRBTreeMap.EntryIterator(from.getFloatKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1116 */             if (!(o instanceof Map.Entry))
/* 1117 */               return false; 
/* 1118 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1119 */             if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1120 */               return false; 
/* 1121 */             if (e.getValue() == null || !(e.getValue() instanceof Long))
/* 1122 */               return false; 
/* 1123 */             Float2LongRBTreeMap.Entry f = Float2LongRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
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
/* 1134 */             if (e.getValue() == null || !(e.getValue() instanceof Long))
/* 1135 */               return false; 
/* 1136 */             Float2LongRBTreeMap.Entry f = Float2LongRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1137 */             if (f == null || f.getLongValue() != ((Long)e.getValue()).longValue())
/* 1138 */               return false; 
/* 1139 */             Float2LongRBTreeMap.this.remove(f.key);
/* 1140 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1144 */             return Float2LongRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1148 */             Float2LongRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Float2LongMap.Entry first() {
/* 1152 */             return Float2LongRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Float2LongMap.Entry last() {
/* 1156 */             return Float2LongRBTreeMap.this.lastEntry;
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Float2LongMap.Entry> subSet(Float2LongMap.Entry from, Float2LongMap.Entry to) {
/* 1160 */             return Float2LongRBTreeMap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2LongEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Float2LongMap.Entry> headSet(Float2LongMap.Entry to) {
/* 1164 */             return Float2LongRBTreeMap.this.headMap(to.getFloatKey()).float2LongEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Float2LongMap.Entry> tailSet(Float2LongMap.Entry from) {
/* 1168 */             return Float2LongRBTreeMap.this.tailMap(from.getFloatKey()).float2LongEntrySet();
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
/*      */     implements FloatListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(float k) {
/* 1187 */       super(k);
/*      */     }
/*      */     
/*      */     public float nextFloat() {
/* 1191 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public float previousFloat() {
/* 1195 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractFloat2LongSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public FloatBidirectionalIterator iterator() {
/* 1202 */       return new Float2LongRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public FloatBidirectionalIterator iterator(float from) {
/* 1206 */       return new Float2LongRBTreeMap.KeyIterator(from);
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
/*      */     implements LongListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1238 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public long previousLong() {
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
/*      */   public LongCollection values() {
/* 1257 */     if (this.values == null)
/* 1258 */       this.values = (LongCollection)new AbstractLongCollection()
/*      */         {
/*      */           public LongIterator iterator() {
/* 1261 */             return (LongIterator)new Float2LongRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(long k) {
/* 1265 */             return Float2LongRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1269 */             return Float2LongRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1273 */             Float2LongRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1276 */     return this.values;
/*      */   }
/*      */   
/*      */   public FloatComparator comparator() {
/* 1280 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Float2LongSortedMap headMap(float to) {
/* 1284 */     return new Submap(0.0F, true, to, false);
/*      */   }
/*      */   
/*      */   public Float2LongSortedMap tailMap(float from) {
/* 1288 */     return new Submap(from, false, 0.0F, true);
/*      */   }
/*      */   
/*      */   public Float2LongSortedMap subMap(float from, float to) {
/* 1292 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractFloat2LongSortedMap
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
/*      */     protected transient ObjectSortedSet<Float2LongMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient FloatSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient LongCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(float from, boolean bottom, float to, boolean top) {
/* 1336 */       if (!bottom && !top && Float2LongRBTreeMap.this.compare(from, to) > 0)
/* 1337 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1338 */       this.from = from;
/* 1339 */       this.bottom = bottom;
/* 1340 */       this.to = to;
/* 1341 */       this.top = top;
/* 1342 */       this.defRetValue = Float2LongRBTreeMap.this.defRetValue;
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
/*      */     final boolean in(float k) {
/* 1360 */       return ((this.bottom || Float2LongRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Float2LongRBTreeMap.this
/* 1361 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Float2LongMap.Entry> float2LongEntrySet() {
/* 1365 */       if (this.entries == null)
/* 1366 */         this.entries = (ObjectSortedSet<Float2LongMap.Entry>)new AbstractObjectSortedSet<Float2LongMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Float2LongMap.Entry> iterator() {
/* 1369 */               return (ObjectBidirectionalIterator<Float2LongMap.Entry>)new Float2LongRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */             
/*      */             public ObjectBidirectionalIterator<Float2LongMap.Entry> iterator(Float2LongMap.Entry from) {
/* 1373 */               return (ObjectBidirectionalIterator<Float2LongMap.Entry>)new Float2LongRBTreeMap.Submap.SubmapEntryIterator(from.getFloatKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Float2LongMap.Entry> comparator() {
/* 1377 */               return Float2LongRBTreeMap.this.float2LongEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1382 */               if (!(o instanceof Map.Entry))
/* 1383 */                 return false; 
/* 1384 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1385 */               if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1386 */                 return false; 
/* 1387 */               if (e.getValue() == null || !(e.getValue() instanceof Long))
/* 1388 */                 return false; 
/* 1389 */               Float2LongRBTreeMap.Entry f = Float2LongRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1390 */               return (f != null && Float2LongRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1395 */               if (!(o instanceof Map.Entry))
/* 1396 */                 return false; 
/* 1397 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1398 */               if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1399 */                 return false; 
/* 1400 */               if (e.getValue() == null || !(e.getValue() instanceof Long))
/* 1401 */                 return false; 
/* 1402 */               Float2LongRBTreeMap.Entry f = Float2LongRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1403 */               if (f != null && Float2LongRBTreeMap.Submap.this.in(f.key))
/* 1404 */                 Float2LongRBTreeMap.Submap.this.remove(f.key); 
/* 1405 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1409 */               int c = 0;
/* 1410 */               for (ObjectBidirectionalIterator<Float2LongMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1411 */                 c++; 
/* 1412 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1416 */               return !(new Float2LongRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1420 */               Float2LongRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Float2LongMap.Entry first() {
/* 1424 */               return Float2LongRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Float2LongMap.Entry last() {
/* 1428 */               return Float2LongRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Float2LongMap.Entry> subSet(Float2LongMap.Entry from, Float2LongMap.Entry to) {
/* 1433 */               return Float2LongRBTreeMap.Submap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2LongEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Float2LongMap.Entry> headSet(Float2LongMap.Entry to) {
/* 1437 */               return Float2LongRBTreeMap.Submap.this.headMap(to.getFloatKey()).float2LongEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Float2LongMap.Entry> tailSet(Float2LongMap.Entry from) {
/* 1441 */               return Float2LongRBTreeMap.Submap.this.tailMap(from.getFloatKey()).float2LongEntrySet();
/*      */             }
/*      */           }; 
/* 1444 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractFloat2LongSortedMap.KeySet {
/*      */       public FloatBidirectionalIterator iterator() {
/* 1449 */         return new Float2LongRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public FloatBidirectionalIterator iterator(float from) {
/* 1453 */         return new Float2LongRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public FloatSortedSet keySet() {
/* 1458 */       if (this.keys == null)
/* 1459 */         this.keys = new KeySet(); 
/* 1460 */       return this.keys;
/*      */     }
/*      */     
/*      */     public LongCollection values() {
/* 1464 */       if (this.values == null)
/* 1465 */         this.values = (LongCollection)new AbstractLongCollection()
/*      */           {
/*      */             public LongIterator iterator() {
/* 1468 */               return (LongIterator)new Float2LongRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(long k) {
/* 1472 */               return Float2LongRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1476 */               return Float2LongRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1480 */               Float2LongRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1483 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(float k) {
/* 1488 */       return (in(k) && Float2LongRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(long v) {
/* 1492 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1494 */       while (i.hasNext()) {
/* 1495 */         long ev = (i.nextEntry()).value;
/* 1496 */         if (ev == v)
/* 1497 */           return true; 
/*      */       } 
/* 1499 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long get(float k) {
/* 1505 */       float kk = k; Float2LongRBTreeMap.Entry e;
/* 1506 */       return (in(kk) && (e = Float2LongRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public long put(float k, long v) {
/* 1510 */       Float2LongRBTreeMap.this.modified = false;
/* 1511 */       if (!in(k))
/* 1512 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1513 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1514 */       long oldValue = Float2LongRBTreeMap.this.put(k, v);
/* 1515 */       return Float2LongRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public long remove(float k) {
/* 1520 */       Float2LongRBTreeMap.this.modified = false;
/* 1521 */       if (!in(k))
/* 1522 */         return this.defRetValue; 
/* 1523 */       long oldValue = Float2LongRBTreeMap.this.remove(k);
/* 1524 */       return Float2LongRBTreeMap.this.modified ? oldValue : this.defRetValue;
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
/*      */     public FloatComparator comparator() {
/* 1542 */       return Float2LongRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Float2LongSortedMap headMap(float to) {
/* 1546 */       if (this.top)
/* 1547 */         return new Submap(this.from, this.bottom, to, false); 
/* 1548 */       return (Float2LongRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Float2LongSortedMap tailMap(float from) {
/* 1552 */       if (this.bottom)
/* 1553 */         return new Submap(from, false, this.to, this.top); 
/* 1554 */       return (Float2LongRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Float2LongSortedMap subMap(float from, float to) {
/* 1558 */       if (this.top && this.bottom)
/* 1559 */         return new Submap(from, false, to, false); 
/* 1560 */       if (!this.top)
/* 1561 */         to = (Float2LongRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1562 */       if (!this.bottom)
/* 1563 */         from = (Float2LongRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1564 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1565 */         return this; 
/* 1566 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Float2LongRBTreeMap.Entry firstEntry() {
/*      */       Float2LongRBTreeMap.Entry e;
/* 1575 */       if (Float2LongRBTreeMap.this.tree == null) {
/* 1576 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1580 */       if (this.bottom) {
/* 1581 */         e = Float2LongRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1583 */         e = Float2LongRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1585 */         if (Float2LongRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1586 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1590 */       if (e == null || (!this.top && Float2LongRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1591 */         return null; 
/* 1592 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Float2LongRBTreeMap.Entry lastEntry() {
/*      */       Float2LongRBTreeMap.Entry e;
/* 1601 */       if (Float2LongRBTreeMap.this.tree == null) {
/* 1602 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1606 */       if (this.top) {
/* 1607 */         e = Float2LongRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1609 */         e = Float2LongRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1611 */         if (Float2LongRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1612 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1616 */       if (e == null || (!this.bottom && Float2LongRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1617 */         return null; 
/* 1618 */       return e;
/*      */     }
/*      */     
/*      */     public float firstFloatKey() {
/* 1622 */       Float2LongRBTreeMap.Entry e = firstEntry();
/* 1623 */       if (e == null)
/* 1624 */         throw new NoSuchElementException(); 
/* 1625 */       return e.key;
/*      */     }
/*      */     
/*      */     public float lastFloatKey() {
/* 1629 */       Float2LongRBTreeMap.Entry e = lastEntry();
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
/*      */       extends Float2LongRBTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1645 */         this.next = Float2LongRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(float k) {
/* 1648 */         this();
/* 1649 */         if (this.next != null)
/* 1650 */           if (!Float2LongRBTreeMap.Submap.this.bottom && Float2LongRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1651 */             this.prev = null;
/* 1652 */           } else if (!Float2LongRBTreeMap.Submap.this.top && Float2LongRBTreeMap.this.compare(k, (this.prev = Float2LongRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1653 */             this.next = null;
/*      */           } else {
/* 1655 */             this.next = Float2LongRBTreeMap.this.locateKey(k);
/* 1656 */             if (Float2LongRBTreeMap.this.compare(this.next.key, k) <= 0) {
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
/* 1667 */         if (!Float2LongRBTreeMap.Submap.this.bottom && this.prev != null && Float2LongRBTreeMap.this.compare(this.prev.key, Float2LongRBTreeMap.Submap.this.from) < 0)
/* 1668 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1672 */         this.next = this.next.next();
/* 1673 */         if (!Float2LongRBTreeMap.Submap.this.top && this.next != null && Float2LongRBTreeMap.this.compare(this.next.key, Float2LongRBTreeMap.Submap.this.to) >= 0)
/* 1674 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Float2LongMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(float k) {
/* 1681 */         super(k);
/*      */       }
/*      */       
/*      */       public Float2LongMap.Entry next() {
/* 1685 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Float2LongMap.Entry previous() {
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
/*      */       implements FloatListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(float from) {
/* 1707 */         super(from);
/*      */       }
/*      */       
/*      */       public float nextFloat() {
/* 1711 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public float previousFloat() {
/* 1715 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements LongListIterator
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public long nextLong() {
/* 1731 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public long previousLong() {
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
/*      */   public Float2LongRBTreeMap clone() {
/*      */     Float2LongRBTreeMap c;
/*      */     try {
/* 1754 */       c = (Float2LongRBTreeMap)super.clone();
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
/* 1813 */       s.writeFloat(e.key);
/* 1814 */       s.writeLong(e.value);
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
/* 1836 */       Entry entry = new Entry(s.readFloat(), s.readLong());
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
/* 1847 */       Entry entry = new Entry(s.readFloat(), s.readLong());
/* 1848 */       entry.black(true);
/* 1849 */       entry.right(new Entry(s.readFloat(), s.readLong()));
/* 1850 */       entry.right.pred(entry);
/* 1851 */       entry.pred(pred);
/* 1852 */       entry.right.succ(succ);
/* 1853 */       return entry;
/*      */     } 
/*      */     
/* 1856 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1857 */     Entry top = new Entry();
/* 1858 */     top.left(readTree(s, leftN, pred, top));
/* 1859 */     top.key = s.readFloat();
/* 1860 */     top.value = s.readLong();
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2LongRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */