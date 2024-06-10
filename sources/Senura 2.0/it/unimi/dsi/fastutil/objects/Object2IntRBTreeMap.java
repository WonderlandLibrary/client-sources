/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntListIterator;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2IntRBTreeMap<K>
/*      */   extends AbstractObject2IntSortedMap<K>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<K> tree;
/*      */   protected int count;
/*      */   protected transient Entry<K> firstEntry;
/*      */   protected transient Entry<K> lastEntry;
/*      */   protected transient ObjectSortedSet<Object2IntMap.Entry<K>> entries;
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */   protected transient IntCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super K> storedComparator;
/*      */   protected transient Comparator<? super K> actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry<K>[] nodePath;
/*      */   
/*      */   public Object2IntRBTreeMap() {
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
/*   91 */     this.actualComparator = this.storedComparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntRBTreeMap(Comparator<? super K> c) {
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
/*      */   public Object2IntRBTreeMap(Map<? extends K, ? extends Integer> m) {
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
/*      */   public Object2IntRBTreeMap(SortedMap<K, Integer> m) {
/*  122 */     this(m.comparator());
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntRBTreeMap(Object2IntMap<? extends K> m) {
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
/*      */   public Object2IntRBTreeMap(Object2IntSortedMap<K> m) {
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
/*      */   public Object2IntRBTreeMap(K[] k, int[] v, Comparator<? super K> c) {
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
/*      */   public Object2IntRBTreeMap(K[] k, int[] v) {
/*  178 */     this(k, v, (Comparator<? super K>)null);
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
/*      */   final int compare(K k1, K k2) {
/*  206 */     return (this.actualComparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry<K> findKey(K k) {
/*  218 */     Entry<K> e = this.tree;
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
/*      */   final Entry<K> locateKey(K k) {
/*  234 */     Entry<K> e = this.tree, last = this.tree;
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
/*  251 */     this.nodePath = (Entry<K>[])new Entry[64];
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
/*      */   public int addTo(K k, int incr) {
/*  270 */     Entry<K> e = add(k);
/*  271 */     int oldValue = e.value;
/*  272 */     e.value += incr;
/*  273 */     return oldValue;
/*      */   }
/*      */   
/*      */   public int put(K k, int v) {
/*  277 */     Entry<K> e = add(k);
/*  278 */     int oldValue = e.value;
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
/*      */   private Entry<K> add(K k) {
/*      */     Entry<K> e;
/*  297 */     this.modified = false;
/*  298 */     int maxDepth = 0;
/*      */     
/*  300 */     if (this.tree == null) {
/*  301 */       this.count++;
/*  302 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*      */     } else {
/*  304 */       Entry<K> p = this.tree;
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
/*  317 */             e = new Entry<>(k, this.defRetValue);
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
/*  329 */           e = new Entry<>(k, this.defRetValue);
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
/*  344 */           Entry<K> entry1 = (this.nodePath[i - 1]).right;
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
/*  355 */             Entry<K> entry = this.nodePath[i];
/*  356 */             entry1 = entry.right;
/*  357 */             entry.right = entry1.left;
/*  358 */             entry1.left = entry;
/*  359 */             (this.nodePath[i - 1]).left = entry1;
/*  360 */             if (entry1.pred()) {
/*  361 */               entry1.pred(false);
/*  362 */               entry.succ(entry1);
/*      */             } 
/*      */           } 
/*  365 */           Entry<K> entry2 = this.nodePath[i - 1];
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
/*  385 */         Entry<K> y = (this.nodePath[i - 1]).left;
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
/*  396 */           Entry<K> entry = this.nodePath[i];
/*  397 */           y = entry.left;
/*  398 */           entry.left = y.right;
/*  399 */           y.right = entry;
/*  400 */           (this.nodePath[i - 1]).right = y;
/*  401 */           if (y.succ()) {
/*  402 */             y.succ(false);
/*  403 */             entry.pred(y);
/*      */           } 
/*      */         } 
/*  406 */         Entry<K> x = this.nodePath[i - 1];
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
/*      */   public int removeInt(Object k) {
/*  441 */     this.modified = false;
/*  442 */     if (this.tree == null)
/*  443 */       return this.defRetValue; 
/*  444 */     Entry<K> p = this.tree;
/*      */     
/*  446 */     int i = 0;
/*  447 */     K kk = (K)k;
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
/*  496 */       Entry<K> r = p.right;
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
/*      */         Entry<K> s;
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
/*  555 */           Entry<K> x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  556 */           if (!x.black()) {
/*  557 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  561 */         if (!this.dirPath[i - 1]) {
/*  562 */           Entry<K> w = (this.nodePath[i - 1]).right;
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
/*  587 */               Entry<K> y = w.left;
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
/*  618 */           Entry<K> w = (this.nodePath[i - 1]).left;
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
/*  643 */               Entry<K> y = w.right;
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
/*      */   public boolean containsValue(int v) {
/*  687 */     ValueIterator i = new ValueIterator();
/*      */     
/*  689 */     int j = this.count;
/*  690 */     while (j-- != 0) {
/*  691 */       int ev = i.nextInt();
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
/*      */   private static final class Entry<K>
/*      */     extends AbstractObject2IntMap.BasicEntry<K>
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
/*      */     Entry<K> left;
/*      */ 
/*      */     
/*      */     Entry<K> right;
/*      */ 
/*      */     
/*      */     int info;
/*      */ 
/*      */     
/*      */     Entry() {
/*  732 */       super((K)null, 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(K k, int v) {
/*  743 */       super(k, v);
/*  744 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> left() {
/*  752 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> right() {
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
/*      */     void pred(Entry<K> pred) {
/*  809 */       this.info |= 0x40000000;
/*  810 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<K> succ) {
/*  819 */       this.info |= Integer.MIN_VALUE;
/*  820 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<K> left) {
/*  829 */       this.info &= 0xBFFFFFFF;
/*  830 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<K> right) {
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
/*      */     Entry<K> next() {
/*  868 */       Entry<K> next = this.right;
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
/*      */     Entry<K> prev() {
/*  880 */       Entry<K> prev = this.left;
/*  881 */       if ((this.info & 0x40000000) == 0)
/*  882 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  883 */           prev = prev.right;  
/*  884 */       return prev;
/*      */     }
/*      */     
/*      */     public int setValue(int value) {
/*  888 */       int oldValue = this.value;
/*  889 */       this.value = value;
/*  890 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry<K> clone() {
/*      */       Entry<K> c;
/*      */       try {
/*  897 */         c = (Entry<K>)super.clone();
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
/*  911 */       Map.Entry<K, Integer> e = (Map.Entry<K, Integer>)o;
/*  912 */       return (Objects.equals(this.key, e.getKey()) && this.value == ((Integer)e.getValue()).intValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  916 */       return this.key.hashCode() ^ this.value;
/*      */     }
/*      */     
/*      */     public String toString() {
/*  920 */       return (new StringBuilder()).append(this.key).append("=>").append(this.value).toString();
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
/*      */   public boolean containsKey(Object k) {
/*  941 */     return (findKey((K)k) != null);
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
/*      */   public int getInt(Object k) {
/*  954 */     Entry<K> e = findKey((K)k);
/*  955 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public K firstKey() {
/*  959 */     if (this.tree == null)
/*  960 */       throw new NoSuchElementException(); 
/*  961 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public K lastKey() {
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
/*      */     Object2IntRBTreeMap.Entry<K> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2IntRBTreeMap.Entry<K> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2IntRBTreeMap.Entry<K> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  998 */     int index = 0;
/*      */     TreeIterator() {
/* 1000 */       this.next = Object2IntRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(K k) {
/* 1003 */       if ((this.next = Object2IntRBTreeMap.this.locateKey(k)) != null)
/* 1004 */         if (Object2IntRBTreeMap.this.compare(this.next.key, k) <= 0) {
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
/*      */     Object2IntRBTreeMap.Entry<K> nextEntry() {
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
/*      */     Object2IntRBTreeMap.Entry<K> previousEntry() {
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
/* 1057 */       Object2IntRBTreeMap.this.removeInt(this.curr.key);
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
/*      */     implements ObjectListIterator<Object2IntMap.Entry<K>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(K k) {
/* 1083 */       super(k);
/*      */     }
/*      */     
/*      */     public Object2IntMap.Entry<K> next() {
/* 1087 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Object2IntMap.Entry<K> previous() {
/* 1091 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
/* 1096 */     if (this.entries == null)
/* 1097 */       this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2IntMap.Entry<Object2IntMap.Entry<K>>>()
/*      */         {
/*      */           final Comparator<? super Object2IntMap.Entry<K>> comparator;
/*      */           
/*      */           public Comparator<? super Object2IntMap.Entry<K>> comparator() {
/* 1102 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2IntMap.Entry<K>> iterator() {
/* 1106 */             return new Object2IntRBTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2IntMap.Entry<K>> iterator(Object2IntMap.Entry<K> from) {
/* 1110 */             return new Object2IntRBTreeMap.EntryIterator(from.getKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1115 */             if (!(o instanceof Map.Entry))
/* 1116 */               return false; 
/* 1117 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1118 */             if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 1119 */               return false; 
/* 1120 */             Object2IntRBTreeMap.Entry<K> f = Object2IntRBTreeMap.this.findKey((K)e.getKey());
/* 1121 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1126 */             if (!(o instanceof Map.Entry))
/* 1127 */               return false; 
/* 1128 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1129 */             if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 1130 */               return false; 
/* 1131 */             Object2IntRBTreeMap.Entry<K> f = Object2IntRBTreeMap.this.findKey((K)e.getKey());
/* 1132 */             if (f == null || f.getIntValue() != ((Integer)e.getValue()).intValue())
/* 1133 */               return false; 
/* 1134 */             Object2IntRBTreeMap.this.removeInt(f.key);
/* 1135 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1139 */             return Object2IntRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1143 */             Object2IntRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Object2IntMap.Entry<K> first() {
/* 1147 */             return Object2IntRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Object2IntMap.Entry<K> last() {
/* 1151 */             return Object2IntRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2IntMap.Entry<K>> subSet(Object2IntMap.Entry<K> from, Object2IntMap.Entry<K> to) {
/* 1156 */             return Object2IntRBTreeMap.this.subMap(from.getKey(), to.getKey()).object2IntEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2IntMap.Entry<K>> headSet(Object2IntMap.Entry<K> to) {
/* 1160 */             return Object2IntRBTreeMap.this.headMap(to.getKey()).object2IntEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2IntMap.Entry<K>> tailSet(Object2IntMap.Entry<K> from) {
/* 1164 */             return Object2IntRBTreeMap.this.tailMap(from.getKey()).object2IntEntrySet();
/*      */           }
/*      */         }; 
/* 1167 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<K>
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(K k) {
/* 1183 */       super(k);
/*      */     }
/*      */     
/*      */     public K next() {
/* 1187 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1191 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractObject2IntSortedMap<K>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1198 */       return new Object2IntRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1202 */       return new Object2IntRBTreeMap.KeyIterator(from);
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
/*      */   public ObjectSortedSet<K> keySet() {
/* 1217 */     if (this.keys == null)
/* 1218 */       this.keys = new KeySet(); 
/* 1219 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements IntListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1234 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public int previousInt() {
/* 1238 */       return (previousEntry()).value;
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
/*      */   public IntCollection values() {
/* 1253 */     if (this.values == null)
/* 1254 */       this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1257 */             return (IntIterator)new Object2IntRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(int k) {
/* 1261 */             return Object2IntRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1265 */             return Object2IntRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1269 */             Object2IntRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1272 */     return this.values;
/*      */   }
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1276 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Object2IntSortedMap<K> headMap(K to) {
/* 1280 */     return new Submap(null, true, to, false);
/*      */   }
/*      */   
/*      */   public Object2IntSortedMap<K> tailMap(K from) {
/* 1284 */     return new Submap(from, false, null, true);
/*      */   }
/*      */   
/*      */   public Object2IntSortedMap<K> subMap(K from, K to) {
/* 1288 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractObject2IntSortedMap<K>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     K from;
/*      */ 
/*      */ 
/*      */     
/*      */     K to;
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
/*      */     protected transient ObjectSortedSet<Object2IntMap.Entry<K>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient IntCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(K from, boolean bottom, K to, boolean top) {
/* 1332 */       if (!bottom && !top && Object2IntRBTreeMap.this.compare(from, to) > 0)
/* 1333 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1334 */       this.from = from;
/* 1335 */       this.bottom = bottom;
/* 1336 */       this.to = to;
/* 1337 */       this.top = top;
/* 1338 */       this.defRetValue = Object2IntRBTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1342 */       SubmapIterator i = new SubmapIterator();
/* 1343 */       while (i.hasNext()) {
/* 1344 */         i.nextEntry();
/* 1345 */         i.remove();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean in(K k) {
/* 1356 */       return ((this.bottom || Object2IntRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Object2IntRBTreeMap.this
/* 1357 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
/* 1361 */       if (this.entries == null)
/* 1362 */         this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2IntMap.Entry<Object2IntMap.Entry<K>>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Object2IntMap.Entry<K>> iterator() {
/* 1365 */               return new Object2IntRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Object2IntMap.Entry<K>> iterator(Object2IntMap.Entry<K> from) {
/* 1370 */               return new Object2IntRBTreeMap.Submap.SubmapEntryIterator(from.getKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Object2IntMap.Entry<K>> comparator() {
/* 1374 */               return Object2IntRBTreeMap.this.object2IntEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1379 */               if (!(o instanceof Map.Entry))
/* 1380 */                 return false; 
/* 1381 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1382 */               if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 1383 */                 return false; 
/* 1384 */               Object2IntRBTreeMap.Entry<K> f = Object2IntRBTreeMap.this.findKey((K)e.getKey());
/* 1385 */               return (f != null && Object2IntRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1390 */               if (!(o instanceof Map.Entry))
/* 1391 */                 return false; 
/* 1392 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1393 */               if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 1394 */                 return false; 
/* 1395 */               Object2IntRBTreeMap.Entry<K> f = Object2IntRBTreeMap.this.findKey((K)e.getKey());
/* 1396 */               if (f != null && Object2IntRBTreeMap.Submap.this.in(f.key))
/* 1397 */                 Object2IntRBTreeMap.Submap.this.removeInt(f.key); 
/* 1398 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1402 */               int c = 0;
/* 1403 */               for (Iterator<?> i = iterator(); i.hasNext(); i.next())
/* 1404 */                 c++; 
/* 1405 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1409 */               return !(new Object2IntRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1413 */               Object2IntRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Object2IntMap.Entry<K> first() {
/* 1417 */               return Object2IntRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Object2IntMap.Entry<K> last() {
/* 1421 */               return Object2IntRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2IntMap.Entry<K>> subSet(Object2IntMap.Entry<K> from, Object2IntMap.Entry<K> to) {
/* 1426 */               return Object2IntRBTreeMap.Submap.this.subMap(from.getKey(), to.getKey()).object2IntEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2IntMap.Entry<K>> headSet(Object2IntMap.Entry<K> to) {
/* 1430 */               return Object2IntRBTreeMap.Submap.this.headMap(to.getKey()).object2IntEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2IntMap.Entry<K>> tailSet(Object2IntMap.Entry<K> from) {
/* 1434 */               return Object2IntRBTreeMap.Submap.this.tailMap(from.getKey()).object2IntEntrySet();
/*      */             }
/*      */           }; 
/* 1437 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractObject2IntSortedMap<K>.KeySet {
/*      */       public ObjectBidirectionalIterator<K> iterator() {
/* 1442 */         return new Object2IntRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1446 */         return new Object2IntRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> keySet() {
/* 1451 */       if (this.keys == null)
/* 1452 */         this.keys = new KeySet(); 
/* 1453 */       return this.keys;
/*      */     }
/*      */     
/*      */     public IntCollection values() {
/* 1457 */       if (this.values == null)
/* 1458 */         this.values = (IntCollection)new AbstractIntCollection()
/*      */           {
/*      */             public IntIterator iterator() {
/* 1461 */               return (IntIterator)new Object2IntRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(int k) {
/* 1465 */               return Object2IntRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1469 */               return Object2IntRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1473 */               Object2IntRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1476 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object k) {
/* 1481 */       return (in((K)k) && Object2IntRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(int v) {
/* 1485 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1487 */       while (i.hasNext()) {
/* 1488 */         int ev = (i.nextEntry()).value;
/* 1489 */         if (ev == v)
/* 1490 */           return true; 
/*      */       } 
/* 1492 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getInt(Object k) {
/* 1498 */       K kk = (K)k; Object2IntRBTreeMap.Entry<K> e;
/* 1499 */       return (in(kk) && (e = Object2IntRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int put(K k, int v) {
/* 1503 */       Object2IntRBTreeMap.this.modified = false;
/* 1504 */       if (!in(k))
/* 1505 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1506 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1507 */       int oldValue = Object2IntRBTreeMap.this.put(k, v);
/* 1508 */       return Object2IntRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public int removeInt(Object k) {
/* 1513 */       Object2IntRBTreeMap.this.modified = false;
/* 1514 */       if (!in((K)k))
/* 1515 */         return this.defRetValue; 
/* 1516 */       int oldValue = Object2IntRBTreeMap.this.removeInt(k);
/* 1517 */       return Object2IntRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1521 */       SubmapIterator i = new SubmapIterator();
/* 1522 */       int n = 0;
/* 1523 */       while (i.hasNext()) {
/* 1524 */         n++;
/* 1525 */         i.nextEntry();
/*      */       } 
/* 1527 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1531 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1535 */       return Object2IntRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Object2IntSortedMap<K> headMap(K to) {
/* 1539 */       if (this.top)
/* 1540 */         return new Submap(this.from, this.bottom, to, false); 
/* 1541 */       return (Object2IntRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Object2IntSortedMap<K> tailMap(K from) {
/* 1545 */       if (this.bottom)
/* 1546 */         return new Submap(from, false, this.to, this.top); 
/* 1547 */       return (Object2IntRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Object2IntSortedMap<K> subMap(K from, K to) {
/* 1551 */       if (this.top && this.bottom)
/* 1552 */         return new Submap(from, false, to, false); 
/* 1553 */       if (!this.top)
/* 1554 */         to = (Object2IntRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1555 */       if (!this.bottom)
/* 1556 */         from = (Object2IntRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1557 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1558 */         return this; 
/* 1559 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2IntRBTreeMap.Entry<K> firstEntry() {
/*      */       Object2IntRBTreeMap.Entry<K> e;
/* 1568 */       if (Object2IntRBTreeMap.this.tree == null) {
/* 1569 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1573 */       if (this.bottom) {
/* 1574 */         e = Object2IntRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1576 */         e = Object2IntRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1578 */         if (Object2IntRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1579 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1583 */       if (e == null || (!this.top && Object2IntRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1584 */         return null; 
/* 1585 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2IntRBTreeMap.Entry<K> lastEntry() {
/*      */       Object2IntRBTreeMap.Entry<K> e;
/* 1594 */       if (Object2IntRBTreeMap.this.tree == null) {
/* 1595 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1599 */       if (this.top) {
/* 1600 */         e = Object2IntRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1602 */         e = Object2IntRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1604 */         if (Object2IntRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1605 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1609 */       if (e == null || (!this.bottom && Object2IntRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1610 */         return null; 
/* 1611 */       return e;
/*      */     }
/*      */     
/*      */     public K firstKey() {
/* 1615 */       Object2IntRBTreeMap.Entry<K> e = firstEntry();
/* 1616 */       if (e == null)
/* 1617 */         throw new NoSuchElementException(); 
/* 1618 */       return e.key;
/*      */     }
/*      */     
/*      */     public K lastKey() {
/* 1622 */       Object2IntRBTreeMap.Entry<K> e = lastEntry();
/* 1623 */       if (e == null)
/* 1624 */         throw new NoSuchElementException(); 
/* 1625 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Object2IntRBTreeMap<K>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1638 */         this.next = Object2IntRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(K k) {
/* 1641 */         this();
/* 1642 */         if (this.next != null)
/* 1643 */           if (!Object2IntRBTreeMap.Submap.this.bottom && Object2IntRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1644 */             this.prev = null;
/* 1645 */           } else if (!Object2IntRBTreeMap.Submap.this.top && Object2IntRBTreeMap.this.compare(k, (this.prev = Object2IntRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1646 */             this.next = null;
/*      */           } else {
/* 1648 */             this.next = Object2IntRBTreeMap.this.locateKey(k);
/* 1649 */             if (Object2IntRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1650 */               this.prev = this.next;
/* 1651 */               this.next = this.next.next();
/*      */             } else {
/* 1653 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1659 */         this.prev = this.prev.prev();
/* 1660 */         if (!Object2IntRBTreeMap.Submap.this.bottom && this.prev != null && Object2IntRBTreeMap.this.compare(this.prev.key, Object2IntRBTreeMap.Submap.this.from) < 0)
/* 1661 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1665 */         this.next = this.next.next();
/* 1666 */         if (!Object2IntRBTreeMap.Submap.this.top && this.next != null && Object2IntRBTreeMap.this.compare(this.next.key, Object2IntRBTreeMap.Submap.this.to) >= 0)
/* 1667 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Object2IntMap.Entry<K>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(K k) {
/* 1674 */         super(k);
/*      */       }
/*      */       
/*      */       public Object2IntMap.Entry<K> next() {
/* 1678 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Object2IntMap.Entry<K> previous() {
/* 1682 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements ObjectListIterator<K>
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(K from) {
/* 1700 */         super(from);
/*      */       }
/*      */       
/*      */       public K next() {
/* 1704 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public K previous() {
/* 1708 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements IntListIterator
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public int nextInt() {
/* 1724 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public int previousInt() {
/* 1728 */         return (previousEntry()).value;
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
/*      */   public Object2IntRBTreeMap<K> clone() {
/*      */     Object2IntRBTreeMap<K> c;
/*      */     try {
/* 1747 */       c = (Object2IntRBTreeMap<K>)super.clone();
/* 1748 */     } catch (CloneNotSupportedException cantHappen) {
/* 1749 */       throw new InternalError();
/*      */     } 
/* 1751 */     c.keys = null;
/* 1752 */     c.values = null;
/* 1753 */     c.entries = null;
/* 1754 */     c.allocatePaths();
/* 1755 */     if (this.count != 0) {
/*      */       
/* 1757 */       Entry<K> rp = new Entry<>(), rq = new Entry<>();
/* 1758 */       Entry<K> p = rp;
/* 1759 */       rp.left(this.tree);
/* 1760 */       Entry<K> q = rq;
/* 1761 */       rq.pred((Entry<K>)null);
/*      */       while (true) {
/* 1763 */         if (!p.pred()) {
/* 1764 */           Entry<K> e = p.left.clone();
/* 1765 */           e.pred(q.left);
/* 1766 */           e.succ(q);
/* 1767 */           q.left(e);
/* 1768 */           p = p.left;
/* 1769 */           q = q.left;
/*      */         } else {
/* 1771 */           while (p.succ()) {
/* 1772 */             p = p.right;
/* 1773 */             if (p == null) {
/* 1774 */               q.right = null;
/* 1775 */               c.tree = rq.left;
/* 1776 */               c.firstEntry = c.tree;
/* 1777 */               while (c.firstEntry.left != null)
/* 1778 */                 c.firstEntry = c.firstEntry.left; 
/* 1779 */               c.lastEntry = c.tree;
/* 1780 */               while (c.lastEntry.right != null)
/* 1781 */                 c.lastEntry = c.lastEntry.right; 
/* 1782 */               return c;
/*      */             } 
/* 1784 */             q = q.right;
/*      */           } 
/* 1786 */           p = p.right;
/* 1787 */           q = q.right;
/*      */         } 
/* 1789 */         if (!p.succ()) {
/* 1790 */           Entry<K> e = p.right.clone();
/* 1791 */           e.succ(q.right);
/* 1792 */           e.pred(q);
/* 1793 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1797 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1800 */     int n = this.count;
/* 1801 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1803 */     s.defaultWriteObject();
/* 1804 */     while (n-- != 0) {
/* 1805 */       Entry<K> e = i.nextEntry();
/* 1806 */       s.writeObject(e.key);
/* 1807 */       s.writeInt(e.value);
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
/*      */   private Entry<K> readTree(ObjectInputStream s, int n, Entry<K> pred, Entry<K> succ) throws IOException, ClassNotFoundException {
/* 1828 */     if (n == 1) {
/* 1829 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readInt());
/* 1830 */       entry.pred(pred);
/* 1831 */       entry.succ(succ);
/* 1832 */       entry.black(true);
/* 1833 */       return entry;
/*      */     } 
/* 1835 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1840 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readInt());
/* 1841 */       entry.black(true);
/* 1842 */       entry.right(new Entry<>((K)s.readObject(), s.readInt()));
/* 1843 */       entry.right.pred(entry);
/* 1844 */       entry.pred(pred);
/* 1845 */       entry.right.succ(succ);
/* 1846 */       return entry;
/*      */     } 
/*      */     
/* 1849 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1850 */     Entry<K> top = new Entry<>();
/* 1851 */     top.left(readTree(s, leftN, pred, top));
/* 1852 */     top.key = (K)s.readObject();
/* 1853 */     top.value = s.readInt();
/* 1854 */     top.black(true);
/* 1855 */     top.right(readTree(s, rightN, top, succ));
/* 1856 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1857 */       top.right.black(false); 
/* 1858 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1861 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1866 */     setActualComparator();
/* 1867 */     allocatePaths();
/* 1868 */     if (this.count != 0) {
/* 1869 */       this.tree = readTree(s, this.count, (Entry<K>)null, (Entry<K>)null);
/*      */       
/* 1871 */       Entry<K> e = this.tree;
/* 1872 */       while (e.left() != null)
/* 1873 */         e = e.left(); 
/* 1874 */       this.firstEntry = e;
/* 1875 */       e = this.tree;
/* 1876 */       while (e.right() != null)
/* 1877 */         e = e.right(); 
/* 1878 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2IntRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */