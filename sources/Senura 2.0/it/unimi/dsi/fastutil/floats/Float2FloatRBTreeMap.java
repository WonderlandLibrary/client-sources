/*      */ package it.unimi.dsi.fastutil.floats;
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
/*      */ public class Float2FloatRBTreeMap
/*      */   extends AbstractFloat2FloatSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Float2FloatMap.Entry> entries;
/*      */   protected transient FloatSortedSet keys;
/*      */   protected transient FloatCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Float> storedComparator;
/*      */   protected transient FloatComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public Float2FloatRBTreeMap() {
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
/*      */   public Float2FloatRBTreeMap(Comparator<? super Float> c) {
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
/*      */   public Float2FloatRBTreeMap(Map<? extends Float, ? extends Float> m) {
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
/*      */   public Float2FloatRBTreeMap(SortedMap<Float, Float> m) {
/*  122 */     this(m.comparator());
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2FloatRBTreeMap(Float2FloatMap m) {
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
/*      */   public Float2FloatRBTreeMap(Float2FloatSortedMap m) {
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
/*      */   public Float2FloatRBTreeMap(float[] k, float[] v, Comparator<? super Float> c) {
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
/*      */   public Float2FloatRBTreeMap(float[] k, float[] v) {
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
/*      */   public float addTo(float k, float incr) {
/*  270 */     Entry e = add(k);
/*  271 */     float oldValue = e.value;
/*  272 */     e.value += incr;
/*  273 */     return oldValue;
/*      */   }
/*      */   
/*      */   public float put(float k, float v) {
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
/*      */   public float remove(float k) {
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
/*      */     extends AbstractFloat2FloatMap.BasicEntry
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
/*  732 */       super(0.0F, 0.0F);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(float k, float v) {
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
/*  911 */       Map.Entry<Float, Float> e = (Map.Entry<Float, Float>)o;
/*  912 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && 
/*  913 */         Float.floatToIntBits(this.value) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  917 */       return HashCommon.float2int(this.key) ^ HashCommon.float2int(this.value);
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
/*      */   public float get(float k) {
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
/*      */     Float2FloatRBTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Float2FloatRBTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Float2FloatRBTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  999 */     int index = 0;
/*      */     TreeIterator() {
/* 1001 */       this.next = Float2FloatRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(float k) {
/* 1004 */       if ((this.next = Float2FloatRBTreeMap.this.locateKey(k)) != null)
/* 1005 */         if (Float2FloatRBTreeMap.this.compare(this.next.key, k) <= 0) {
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
/*      */     Float2FloatRBTreeMap.Entry nextEntry() {
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
/*      */     Float2FloatRBTreeMap.Entry previousEntry() {
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
/* 1058 */       Float2FloatRBTreeMap.this.remove(this.curr.key);
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
/*      */     implements ObjectListIterator<Float2FloatMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(float k) {
/* 1084 */       super(k);
/*      */     }
/*      */     
/*      */     public Float2FloatMap.Entry next() {
/* 1088 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Float2FloatMap.Entry previous() {
/* 1092 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Float2FloatMap.Entry> float2FloatEntrySet() {
/* 1097 */     if (this.entries == null)
/* 1098 */       this.entries = (ObjectSortedSet<Float2FloatMap.Entry>)new AbstractObjectSortedSet<Float2FloatMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Float2FloatMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Float2FloatMap.Entry> comparator() {
/* 1103 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Float2FloatMap.Entry> iterator() {
/* 1107 */             return (ObjectBidirectionalIterator<Float2FloatMap.Entry>)new Float2FloatRBTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Float2FloatMap.Entry> iterator(Float2FloatMap.Entry from) {
/* 1111 */             return (ObjectBidirectionalIterator<Float2FloatMap.Entry>)new Float2FloatRBTreeMap.EntryIterator(from.getFloatKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1116 */             if (!(o instanceof Map.Entry))
/* 1117 */               return false; 
/* 1118 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1119 */             if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1120 */               return false; 
/* 1121 */             if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1122 */               return false; 
/* 1123 */             Float2FloatRBTreeMap.Entry f = Float2FloatRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
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
/* 1134 */             if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1135 */               return false; 
/* 1136 */             Float2FloatRBTreeMap.Entry f = Float2FloatRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1137 */             if (f == null || Float.floatToIntBits(f.getFloatValue()) != 
/* 1138 */               Float.floatToIntBits(((Float)e.getValue()).floatValue()))
/* 1139 */               return false; 
/* 1140 */             Float2FloatRBTreeMap.this.remove(f.key);
/* 1141 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1145 */             return Float2FloatRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1149 */             Float2FloatRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Float2FloatMap.Entry first() {
/* 1153 */             return Float2FloatRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Float2FloatMap.Entry last() {
/* 1157 */             return Float2FloatRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Float2FloatMap.Entry> subSet(Float2FloatMap.Entry from, Float2FloatMap.Entry to) {
/* 1162 */             return Float2FloatRBTreeMap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2FloatEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Float2FloatMap.Entry> headSet(Float2FloatMap.Entry to) {
/* 1166 */             return Float2FloatRBTreeMap.this.headMap(to.getFloatKey()).float2FloatEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Float2FloatMap.Entry> tailSet(Float2FloatMap.Entry from) {
/* 1170 */             return Float2FloatRBTreeMap.this.tailMap(from.getFloatKey()).float2FloatEntrySet();
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
/*      */     implements FloatListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(float k) {
/* 1189 */       super(k);
/*      */     }
/*      */     
/*      */     public float nextFloat() {
/* 1193 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public float previousFloat() {
/* 1197 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractFloat2FloatSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public FloatBidirectionalIterator iterator() {
/* 1204 */       return new Float2FloatRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public FloatBidirectionalIterator iterator(float from) {
/* 1208 */       return new Float2FloatRBTreeMap.KeyIterator(from);
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
/* 1260 */       this.values = new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1263 */             return new Float2FloatRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(float k) {
/* 1267 */             return Float2FloatRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1271 */             return Float2FloatRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1275 */             Float2FloatRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1278 */     return this.values;
/*      */   }
/*      */   
/*      */   public FloatComparator comparator() {
/* 1282 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Float2FloatSortedMap headMap(float to) {
/* 1286 */     return new Submap(0.0F, true, to, false);
/*      */   }
/*      */   
/*      */   public Float2FloatSortedMap tailMap(float from) {
/* 1290 */     return new Submap(from, false, 0.0F, true);
/*      */   }
/*      */   
/*      */   public Float2FloatSortedMap subMap(float from, float to) {
/* 1294 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractFloat2FloatSortedMap
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
/*      */     protected transient ObjectSortedSet<Float2FloatMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient FloatSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient FloatCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(float from, boolean bottom, float to, boolean top) {
/* 1338 */       if (!bottom && !top && Float2FloatRBTreeMap.this.compare(from, to) > 0)
/* 1339 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1340 */       this.from = from;
/* 1341 */       this.bottom = bottom;
/* 1342 */       this.to = to;
/* 1343 */       this.top = top;
/* 1344 */       this.defRetValue = Float2FloatRBTreeMap.this.defRetValue;
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
/*      */     final boolean in(float k) {
/* 1362 */       return ((this.bottom || Float2FloatRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Float2FloatRBTreeMap.this
/* 1363 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Float2FloatMap.Entry> float2FloatEntrySet() {
/* 1367 */       if (this.entries == null)
/* 1368 */         this.entries = (ObjectSortedSet<Float2FloatMap.Entry>)new AbstractObjectSortedSet<Float2FloatMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Float2FloatMap.Entry> iterator() {
/* 1371 */               return (ObjectBidirectionalIterator<Float2FloatMap.Entry>)new Float2FloatRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */             
/*      */             public ObjectBidirectionalIterator<Float2FloatMap.Entry> iterator(Float2FloatMap.Entry from) {
/* 1375 */               return (ObjectBidirectionalIterator<Float2FloatMap.Entry>)new Float2FloatRBTreeMap.Submap.SubmapEntryIterator(from.getFloatKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Float2FloatMap.Entry> comparator() {
/* 1379 */               return Float2FloatRBTreeMap.this.float2FloatEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1384 */               if (!(o instanceof Map.Entry))
/* 1385 */                 return false; 
/* 1386 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1387 */               if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1388 */                 return false; 
/* 1389 */               if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1390 */                 return false; 
/* 1391 */               Float2FloatRBTreeMap.Entry f = Float2FloatRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1392 */               return (f != null && Float2FloatRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1397 */               if (!(o instanceof Map.Entry))
/* 1398 */                 return false; 
/* 1399 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1400 */               if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1401 */                 return false; 
/* 1402 */               if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1403 */                 return false; 
/* 1404 */               Float2FloatRBTreeMap.Entry f = Float2FloatRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1405 */               if (f != null && Float2FloatRBTreeMap.Submap.this.in(f.key))
/* 1406 */                 Float2FloatRBTreeMap.Submap.this.remove(f.key); 
/* 1407 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1411 */               int c = 0;
/* 1412 */               for (ObjectBidirectionalIterator<Float2FloatMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1413 */                 c++; 
/* 1414 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1418 */               return !(new Float2FloatRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1422 */               Float2FloatRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Float2FloatMap.Entry first() {
/* 1426 */               return Float2FloatRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Float2FloatMap.Entry last() {
/* 1430 */               return Float2FloatRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Float2FloatMap.Entry> subSet(Float2FloatMap.Entry from, Float2FloatMap.Entry to) {
/* 1435 */               return Float2FloatRBTreeMap.Submap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2FloatEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Float2FloatMap.Entry> headSet(Float2FloatMap.Entry to) {
/* 1439 */               return Float2FloatRBTreeMap.Submap.this.headMap(to.getFloatKey()).float2FloatEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Float2FloatMap.Entry> tailSet(Float2FloatMap.Entry from) {
/* 1443 */               return Float2FloatRBTreeMap.Submap.this.tailMap(from.getFloatKey()).float2FloatEntrySet();
/*      */             }
/*      */           }; 
/* 1446 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractFloat2FloatSortedMap.KeySet {
/*      */       public FloatBidirectionalIterator iterator() {
/* 1451 */         return new Float2FloatRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public FloatBidirectionalIterator iterator(float from) {
/* 1455 */         return new Float2FloatRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public FloatSortedSet keySet() {
/* 1460 */       if (this.keys == null)
/* 1461 */         this.keys = new KeySet(); 
/* 1462 */       return this.keys;
/*      */     }
/*      */     
/*      */     public FloatCollection values() {
/* 1466 */       if (this.values == null)
/* 1467 */         this.values = new AbstractFloatCollection()
/*      */           {
/*      */             public FloatIterator iterator() {
/* 1470 */               return new Float2FloatRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(float k) {
/* 1474 */               return Float2FloatRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1478 */               return Float2FloatRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1482 */               Float2FloatRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1485 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(float k) {
/* 1490 */       return (in(k) && Float2FloatRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(float v) {
/* 1494 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1496 */       while (i.hasNext()) {
/* 1497 */         float ev = (i.nextEntry()).value;
/* 1498 */         if (Float.floatToIntBits(ev) == Float.floatToIntBits(v))
/* 1499 */           return true; 
/*      */       } 
/* 1501 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public float get(float k) {
/* 1507 */       float kk = k; Float2FloatRBTreeMap.Entry e;
/* 1508 */       return (in(kk) && (e = Float2FloatRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public float put(float k, float v) {
/* 1512 */       Float2FloatRBTreeMap.this.modified = false;
/* 1513 */       if (!in(k))
/* 1514 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1515 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1516 */       float oldValue = Float2FloatRBTreeMap.this.put(k, v);
/* 1517 */       return Float2FloatRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public float remove(float k) {
/* 1522 */       Float2FloatRBTreeMap.this.modified = false;
/* 1523 */       if (!in(k))
/* 1524 */         return this.defRetValue; 
/* 1525 */       float oldValue = Float2FloatRBTreeMap.this.remove(k);
/* 1526 */       return Float2FloatRBTreeMap.this.modified ? oldValue : this.defRetValue;
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
/*      */     public FloatComparator comparator() {
/* 1544 */       return Float2FloatRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Float2FloatSortedMap headMap(float to) {
/* 1548 */       if (this.top)
/* 1549 */         return new Submap(this.from, this.bottom, to, false); 
/* 1550 */       return (Float2FloatRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Float2FloatSortedMap tailMap(float from) {
/* 1554 */       if (this.bottom)
/* 1555 */         return new Submap(from, false, this.to, this.top); 
/* 1556 */       return (Float2FloatRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Float2FloatSortedMap subMap(float from, float to) {
/* 1560 */       if (this.top && this.bottom)
/* 1561 */         return new Submap(from, false, to, false); 
/* 1562 */       if (!this.top)
/* 1563 */         to = (Float2FloatRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1564 */       if (!this.bottom)
/* 1565 */         from = (Float2FloatRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1566 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1567 */         return this; 
/* 1568 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Float2FloatRBTreeMap.Entry firstEntry() {
/*      */       Float2FloatRBTreeMap.Entry e;
/* 1577 */       if (Float2FloatRBTreeMap.this.tree == null) {
/* 1578 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1582 */       if (this.bottom) {
/* 1583 */         e = Float2FloatRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1585 */         e = Float2FloatRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1587 */         if (Float2FloatRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1588 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1592 */       if (e == null || (!this.top && Float2FloatRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1593 */         return null; 
/* 1594 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Float2FloatRBTreeMap.Entry lastEntry() {
/*      */       Float2FloatRBTreeMap.Entry e;
/* 1603 */       if (Float2FloatRBTreeMap.this.tree == null) {
/* 1604 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1608 */       if (this.top) {
/* 1609 */         e = Float2FloatRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1611 */         e = Float2FloatRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1613 */         if (Float2FloatRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1614 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1618 */       if (e == null || (!this.bottom && Float2FloatRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1619 */         return null; 
/* 1620 */       return e;
/*      */     }
/*      */     
/*      */     public float firstFloatKey() {
/* 1624 */       Float2FloatRBTreeMap.Entry e = firstEntry();
/* 1625 */       if (e == null)
/* 1626 */         throw new NoSuchElementException(); 
/* 1627 */       return e.key;
/*      */     }
/*      */     
/*      */     public float lastFloatKey() {
/* 1631 */       Float2FloatRBTreeMap.Entry e = lastEntry();
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
/*      */       extends Float2FloatRBTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1647 */         this.next = Float2FloatRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(float k) {
/* 1650 */         this();
/* 1651 */         if (this.next != null)
/* 1652 */           if (!Float2FloatRBTreeMap.Submap.this.bottom && Float2FloatRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1653 */             this.prev = null;
/* 1654 */           } else if (!Float2FloatRBTreeMap.Submap.this.top && Float2FloatRBTreeMap.this.compare(k, (this.prev = Float2FloatRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1655 */             this.next = null;
/*      */           } else {
/* 1657 */             this.next = Float2FloatRBTreeMap.this.locateKey(k);
/* 1658 */             if (Float2FloatRBTreeMap.this.compare(this.next.key, k) <= 0) {
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
/* 1669 */         if (!Float2FloatRBTreeMap.Submap.this.bottom && this.prev != null && Float2FloatRBTreeMap.this.compare(this.prev.key, Float2FloatRBTreeMap.Submap.this.from) < 0)
/* 1670 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1674 */         this.next = this.next.next();
/* 1675 */         if (!Float2FloatRBTreeMap.Submap.this.top && this.next != null && Float2FloatRBTreeMap.this.compare(this.next.key, Float2FloatRBTreeMap.Submap.this.to) >= 0)
/* 1676 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Float2FloatMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(float k) {
/* 1683 */         super(k);
/*      */       }
/*      */       
/*      */       public Float2FloatMap.Entry next() {
/* 1687 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Float2FloatMap.Entry previous() {
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
/*      */       implements FloatListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(float from) {
/* 1709 */         super(from);
/*      */       }
/*      */       
/*      */       public float nextFloat() {
/* 1713 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public float previousFloat() {
/* 1717 */         return (previousEntry()).key;
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
/* 1733 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public float previousFloat() {
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
/*      */   public Float2FloatRBTreeMap clone() {
/*      */     Float2FloatRBTreeMap c;
/*      */     try {
/* 1756 */       c = (Float2FloatRBTreeMap)super.clone();
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
/* 1815 */       s.writeFloat(e.key);
/* 1816 */       s.writeFloat(e.value);
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
/* 1838 */       Entry entry = new Entry(s.readFloat(), s.readFloat());
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
/* 1849 */       Entry entry = new Entry(s.readFloat(), s.readFloat());
/* 1850 */       entry.black(true);
/* 1851 */       entry.right(new Entry(s.readFloat(), s.readFloat()));
/* 1852 */       entry.right.pred(entry);
/* 1853 */       entry.pred(pred);
/* 1854 */       entry.right.succ(succ);
/* 1855 */       return entry;
/*      */     } 
/*      */     
/* 1858 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1859 */     Entry top = new Entry();
/* 1860 */     top.left(readTree(s, leftN, pred, top));
/* 1861 */     top.key = s.readFloat();
/* 1862 */     top.value = s.readFloat();
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2FloatRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */