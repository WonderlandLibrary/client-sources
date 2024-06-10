/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2ReferenceRBTreeMap<K, V>
/*      */   extends AbstractObject2ReferenceSortedMap<K, V>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<K, V> tree;
/*      */   protected int count;
/*      */   protected transient Entry<K, V> firstEntry;
/*      */   protected transient Entry<K, V> lastEntry;
/*      */   protected transient ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> entries;
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */   protected transient ReferenceCollection<V> values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super K> storedComparator;
/*      */   protected transient Comparator<? super K> actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry<K, V>[] nodePath;
/*      */   
/*      */   public Object2ReferenceRBTreeMap() {
/*   74 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   80 */     this.tree = null;
/*   81 */     this.count = 0;
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
/*   93 */     this.actualComparator = this.storedComparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceRBTreeMap(Comparator<? super K> c) {
/*  102 */     this();
/*  103 */     this.storedComparator = c;
/*  104 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceRBTreeMap(Map<? extends K, ? extends V> m) {
/*  113 */     this();
/*  114 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceRBTreeMap(SortedMap<K, V> m) {
/*  124 */     this(m.comparator());
/*  125 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceRBTreeMap(Object2ReferenceMap<? extends K, ? extends V> m) {
/*  134 */     this();
/*  135 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceRBTreeMap(Object2ReferenceSortedMap<K, V> m) {
/*  145 */     this(m.comparator());
/*  146 */     putAll(m);
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
/*      */   public Object2ReferenceRBTreeMap(K[] k, V[] v, Comparator<? super K> c) {
/*  162 */     this(c);
/*  163 */     if (k.length != v.length) {
/*  164 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  166 */     for (int i = 0; i < k.length; i++) {
/*  167 */       put(k[i], v[i]);
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
/*      */   public Object2ReferenceRBTreeMap(K[] k, V[] v) {
/*  180 */     this(k, v, (Comparator<? super K>)null);
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
/*  208 */     return (this.actualComparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry<K, V> findKey(K k) {
/*  220 */     Entry<K, V> e = this.tree;
/*      */     int cmp;
/*  222 */     while (e != null && (cmp = compare(k, e.key)) != 0)
/*  223 */       e = (cmp < 0) ? e.left() : e.right(); 
/*  224 */     return e;
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
/*      */   final Entry<K, V> locateKey(K k) {
/*  236 */     Entry<K, V> e = this.tree, last = this.tree;
/*  237 */     int cmp = 0;
/*  238 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  239 */       last = e;
/*  240 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  242 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  252 */     this.dirPath = new boolean[64];
/*  253 */     this.nodePath = (Entry<K, V>[])new Entry[64];
/*      */   }
/*      */   
/*      */   public V put(K k, V v) {
/*  257 */     Entry<K, V> e = add(k);
/*  258 */     V oldValue = e.value;
/*  259 */     e.value = v;
/*  260 */     return oldValue;
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
/*      */   private Entry<K, V> add(K k) {
/*      */     Entry<K, V> e;
/*  277 */     this.modified = false;
/*  278 */     int maxDepth = 0;
/*      */     
/*  280 */     if (this.tree == null) {
/*  281 */       this.count++;
/*  282 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*      */     } else {
/*  284 */       Entry<K, V> p = this.tree;
/*  285 */       int i = 0; while (true) {
/*      */         int cmp;
/*  287 */         if ((cmp = compare(k, p.key)) == 0) {
/*      */           
/*  289 */           while (i-- != 0)
/*  290 */             this.nodePath[i] = null; 
/*  291 */           return p;
/*      */         } 
/*  293 */         this.nodePath[i] = p;
/*  294 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  295 */           if (p.succ()) {
/*  296 */             this.count++;
/*  297 */             e = new Entry<>(k, this.defRetValue);
/*  298 */             if (p.right == null)
/*  299 */               this.lastEntry = e; 
/*  300 */             e.left = p;
/*  301 */             e.right = p.right;
/*  302 */             p.right(e);
/*      */             break;
/*      */           } 
/*  305 */           p = p.right; continue;
/*      */         } 
/*  307 */         if (p.pred()) {
/*  308 */           this.count++;
/*  309 */           e = new Entry<>(k, this.defRetValue);
/*  310 */           if (p.left == null)
/*  311 */             this.firstEntry = e; 
/*  312 */           e.right = p;
/*  313 */           e.left = p.left;
/*  314 */           p.left(e);
/*      */           break;
/*      */         } 
/*  317 */         p = p.left;
/*      */       } 
/*      */       
/*  320 */       this.modified = true;
/*  321 */       maxDepth = i--;
/*  322 */       while (i > 0 && !this.nodePath[i].black()) {
/*  323 */         if (!this.dirPath[i - 1]) {
/*  324 */           Entry<K, V> entry1 = (this.nodePath[i - 1]).right;
/*  325 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  326 */             this.nodePath[i].black(true);
/*  327 */             entry1.black(true);
/*  328 */             this.nodePath[i - 1].black(false);
/*  329 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  332 */           if (!this.dirPath[i]) {
/*  333 */             entry1 = this.nodePath[i];
/*      */           } else {
/*  335 */             Entry<K, V> entry = this.nodePath[i];
/*  336 */             entry1 = entry.right;
/*  337 */             entry.right = entry1.left;
/*  338 */             entry1.left = entry;
/*  339 */             (this.nodePath[i - 1]).left = entry1;
/*  340 */             if (entry1.pred()) {
/*  341 */               entry1.pred(false);
/*  342 */               entry.succ(entry1);
/*      */             } 
/*      */           } 
/*  345 */           Entry<K, V> entry2 = this.nodePath[i - 1];
/*  346 */           entry2.black(false);
/*  347 */           entry1.black(true);
/*  348 */           entry2.left = entry1.right;
/*  349 */           entry1.right = entry2;
/*  350 */           if (i < 2) {
/*  351 */             this.tree = entry1;
/*      */           }
/*  353 */           else if (this.dirPath[i - 2]) {
/*  354 */             (this.nodePath[i - 2]).right = entry1;
/*      */           } else {
/*  356 */             (this.nodePath[i - 2]).left = entry1;
/*      */           } 
/*  358 */           if (entry1.succ()) {
/*  359 */             entry1.succ(false);
/*  360 */             entry2.pred(entry1);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  365 */         Entry<K, V> y = (this.nodePath[i - 1]).left;
/*  366 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  367 */           this.nodePath[i].black(true);
/*  368 */           y.black(true);
/*  369 */           this.nodePath[i - 1].black(false);
/*  370 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  373 */         if (this.dirPath[i]) {
/*  374 */           y = this.nodePath[i];
/*      */         } else {
/*  376 */           Entry<K, V> entry = this.nodePath[i];
/*  377 */           y = entry.left;
/*  378 */           entry.left = y.right;
/*  379 */           y.right = entry;
/*  380 */           (this.nodePath[i - 1]).right = y;
/*  381 */           if (y.succ()) {
/*  382 */             y.succ(false);
/*  383 */             entry.pred(y);
/*      */           } 
/*      */         } 
/*  386 */         Entry<K, V> x = this.nodePath[i - 1];
/*  387 */         x.black(false);
/*  388 */         y.black(true);
/*  389 */         x.right = y.left;
/*  390 */         y.left = x;
/*  391 */         if (i < 2) {
/*  392 */           this.tree = y;
/*      */         }
/*  394 */         else if (this.dirPath[i - 2]) {
/*  395 */           (this.nodePath[i - 2]).right = y;
/*      */         } else {
/*  397 */           (this.nodePath[i - 2]).left = y;
/*      */         } 
/*  399 */         if (y.pred()) {
/*  400 */           y.pred(false);
/*  401 */           x.succ(y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  408 */     this.tree.black(true);
/*      */     
/*  410 */     while (maxDepth-- != 0)
/*  411 */       this.nodePath[maxDepth] = null; 
/*  412 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V remove(Object k) {
/*  421 */     this.modified = false;
/*  422 */     if (this.tree == null)
/*  423 */       return this.defRetValue; 
/*  424 */     Entry<K, V> p = this.tree;
/*      */     
/*  426 */     int i = 0;
/*  427 */     K kk = (K)k;
/*      */     int cmp;
/*  429 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  431 */       this.dirPath[i] = (cmp > 0);
/*  432 */       this.nodePath[i] = p;
/*  433 */       if (this.dirPath[i++]) {
/*  434 */         if ((p = p.right()) == null) {
/*      */           
/*  436 */           while (i-- != 0)
/*  437 */             this.nodePath[i] = null; 
/*  438 */           return this.defRetValue;
/*      */         }  continue;
/*      */       } 
/*  441 */       if ((p = p.left()) == null) {
/*      */         
/*  443 */         while (i-- != 0)
/*  444 */           this.nodePath[i] = null; 
/*  445 */         return this.defRetValue;
/*      */       } 
/*      */     } 
/*      */     
/*  449 */     if (p.left == null)
/*  450 */       this.firstEntry = p.next(); 
/*  451 */     if (p.right == null)
/*  452 */       this.lastEntry = p.prev(); 
/*  453 */     if (p.succ()) {
/*  454 */       if (p.pred()) {
/*  455 */         if (i == 0) {
/*  456 */           this.tree = p.left;
/*      */         }
/*  458 */         else if (this.dirPath[i - 1]) {
/*  459 */           this.nodePath[i - 1].succ(p.right);
/*      */         } else {
/*  461 */           this.nodePath[i - 1].pred(p.left);
/*      */         } 
/*      */       } else {
/*  464 */         (p.prev()).right = p.right;
/*  465 */         if (i == 0) {
/*  466 */           this.tree = p.left;
/*      */         }
/*  468 */         else if (this.dirPath[i - 1]) {
/*  469 */           (this.nodePath[i - 1]).right = p.left;
/*      */         } else {
/*  471 */           (this.nodePath[i - 1]).left = p.left;
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  476 */       Entry<K, V> r = p.right;
/*  477 */       if (r.pred()) {
/*  478 */         r.left = p.left;
/*  479 */         r.pred(p.pred());
/*  480 */         if (!r.pred())
/*  481 */           (r.prev()).right = r; 
/*  482 */         if (i == 0) {
/*  483 */           this.tree = r;
/*      */         }
/*  485 */         else if (this.dirPath[i - 1]) {
/*  486 */           (this.nodePath[i - 1]).right = r;
/*      */         } else {
/*  488 */           (this.nodePath[i - 1]).left = r;
/*      */         } 
/*  490 */         boolean color = r.black();
/*  491 */         r.black(p.black());
/*  492 */         p.black(color);
/*  493 */         this.dirPath[i] = true;
/*  494 */         this.nodePath[i++] = r;
/*      */       } else {
/*      */         Entry<K, V> s;
/*  497 */         int j = i++;
/*      */         while (true) {
/*  499 */           this.dirPath[i] = false;
/*  500 */           this.nodePath[i++] = r;
/*  501 */           s = r.left;
/*  502 */           if (s.pred())
/*      */             break; 
/*  504 */           r = s;
/*      */         } 
/*  506 */         this.dirPath[j] = true;
/*  507 */         this.nodePath[j] = s;
/*  508 */         if (s.succ()) {
/*  509 */           r.pred(s);
/*      */         } else {
/*  511 */           r.left = s.right;
/*  512 */         }  s.left = p.left;
/*  513 */         if (!p.pred()) {
/*  514 */           (p.prev()).right = s;
/*  515 */           s.pred(false);
/*      */         } 
/*  517 */         s.right(p.right);
/*  518 */         boolean color = s.black();
/*  519 */         s.black(p.black());
/*  520 */         p.black(color);
/*  521 */         if (j == 0) {
/*  522 */           this.tree = s;
/*      */         }
/*  524 */         else if (this.dirPath[j - 1]) {
/*  525 */           (this.nodePath[j - 1]).right = s;
/*      */         } else {
/*  527 */           (this.nodePath[j - 1]).left = s;
/*      */         } 
/*      */       } 
/*      */     } 
/*  531 */     int maxDepth = i;
/*  532 */     if (p.black()) {
/*  533 */       for (; i > 0; i--) {
/*  534 */         if ((this.dirPath[i - 1] && !this.nodePath[i - 1].succ()) || (!this.dirPath[i - 1] && !this.nodePath[i - 1].pred())) {
/*  535 */           Entry<K, V> x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  536 */           if (!x.black()) {
/*  537 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  541 */         if (!this.dirPath[i - 1]) {
/*  542 */           Entry<K, V> w = (this.nodePath[i - 1]).right;
/*  543 */           if (!w.black()) {
/*  544 */             w.black(true);
/*  545 */             this.nodePath[i - 1].black(false);
/*  546 */             (this.nodePath[i - 1]).right = w.left;
/*  547 */             w.left = this.nodePath[i - 1];
/*  548 */             if (i < 2) {
/*  549 */               this.tree = w;
/*      */             }
/*  551 */             else if (this.dirPath[i - 2]) {
/*  552 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  554 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  556 */             this.nodePath[i] = this.nodePath[i - 1];
/*  557 */             this.dirPath[i] = false;
/*  558 */             this.nodePath[i - 1] = w;
/*  559 */             if (maxDepth == i++)
/*  560 */               maxDepth++; 
/*  561 */             w = (this.nodePath[i - 1]).right;
/*      */           } 
/*  563 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  564 */             w.black(false);
/*      */           } else {
/*  566 */             if (w.succ() || w.right.black()) {
/*  567 */               Entry<K, V> y = w.left;
/*  568 */               y.black(true);
/*  569 */               w.black(false);
/*  570 */               w.left = y.right;
/*  571 */               y.right = w;
/*  572 */               w = (this.nodePath[i - 1]).right = y;
/*  573 */               if (w.succ()) {
/*  574 */                 w.succ(false);
/*  575 */                 w.right.pred(w);
/*      */               } 
/*      */             } 
/*  578 */             w.black(this.nodePath[i - 1].black());
/*  579 */             this.nodePath[i - 1].black(true);
/*  580 */             w.right.black(true);
/*  581 */             (this.nodePath[i - 1]).right = w.left;
/*  582 */             w.left = this.nodePath[i - 1];
/*  583 */             if (i < 2) {
/*  584 */               this.tree = w;
/*      */             }
/*  586 */             else if (this.dirPath[i - 2]) {
/*  587 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  589 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  591 */             if (w.pred()) {
/*  592 */               w.pred(false);
/*  593 */               this.nodePath[i - 1].succ(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } else {
/*  598 */           Entry<K, V> w = (this.nodePath[i - 1]).left;
/*  599 */           if (!w.black()) {
/*  600 */             w.black(true);
/*  601 */             this.nodePath[i - 1].black(false);
/*  602 */             (this.nodePath[i - 1]).left = w.right;
/*  603 */             w.right = this.nodePath[i - 1];
/*  604 */             if (i < 2) {
/*  605 */               this.tree = w;
/*      */             }
/*  607 */             else if (this.dirPath[i - 2]) {
/*  608 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  610 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  612 */             this.nodePath[i] = this.nodePath[i - 1];
/*  613 */             this.dirPath[i] = true;
/*  614 */             this.nodePath[i - 1] = w;
/*  615 */             if (maxDepth == i++)
/*  616 */               maxDepth++; 
/*  617 */             w = (this.nodePath[i - 1]).left;
/*      */           } 
/*  619 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  620 */             w.black(false);
/*      */           } else {
/*  622 */             if (w.pred() || w.left.black()) {
/*  623 */               Entry<K, V> y = w.right;
/*  624 */               y.black(true);
/*  625 */               w.black(false);
/*  626 */               w.right = y.left;
/*  627 */               y.left = w;
/*  628 */               w = (this.nodePath[i - 1]).left = y;
/*  629 */               if (w.pred()) {
/*  630 */                 w.pred(false);
/*  631 */                 w.left.succ(w);
/*      */               } 
/*      */             } 
/*  634 */             w.black(this.nodePath[i - 1].black());
/*  635 */             this.nodePath[i - 1].black(true);
/*  636 */             w.left.black(true);
/*  637 */             (this.nodePath[i - 1]).left = w.right;
/*  638 */             w.right = this.nodePath[i - 1];
/*  639 */             if (i < 2) {
/*  640 */               this.tree = w;
/*      */             }
/*  642 */             else if (this.dirPath[i - 2]) {
/*  643 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  645 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  647 */             if (w.succ()) {
/*  648 */               w.succ(false);
/*  649 */               this.nodePath[i - 1].pred(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  655 */       if (this.tree != null)
/*  656 */         this.tree.black(true); 
/*      */     } 
/*  658 */     this.modified = true;
/*  659 */     this.count--;
/*      */     
/*  661 */     while (maxDepth-- != 0)
/*  662 */       this.nodePath[maxDepth] = null; 
/*  663 */     return p.value;
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  667 */     ValueIterator i = new ValueIterator();
/*      */     
/*  669 */     int j = this.count;
/*  670 */     while (j-- != 0) {
/*  671 */       Object ev = i.next();
/*  672 */       if (ev == v)
/*  673 */         return true; 
/*      */     } 
/*  675 */     return false;
/*      */   }
/*      */   
/*      */   public void clear() {
/*  679 */     this.count = 0;
/*  680 */     this.tree = null;
/*  681 */     this.entries = null;
/*  682 */     this.values = null;
/*  683 */     this.keys = null;
/*  684 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<K, V>
/*      */     extends AbstractObject2ReferenceMap.BasicEntry<K, V>
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
/*      */     Entry<K, V> left;
/*      */ 
/*      */     
/*      */     Entry<K, V> right;
/*      */ 
/*      */     
/*      */     int info;
/*      */ 
/*      */     
/*      */     Entry() {
/*  712 */       super(null, null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(K k, V v) {
/*  723 */       super(k, v);
/*  724 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K, V> left() {
/*  732 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K, V> right() {
/*  740 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  748 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  756 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  765 */       if (pred) {
/*  766 */         this.info |= 0x40000000;
/*      */       } else {
/*  768 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  777 */       if (succ) {
/*  778 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  780 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<K, V> pred) {
/*  789 */       this.info |= 0x40000000;
/*  790 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<K, V> succ) {
/*  799 */       this.info |= Integer.MIN_VALUE;
/*  800 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<K, V> left) {
/*  809 */       this.info &= 0xBFFFFFFF;
/*  810 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<K, V> right) {
/*  819 */       this.info &= Integer.MAX_VALUE;
/*  820 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean black() {
/*  828 */       return ((this.info & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void black(boolean black) {
/*  837 */       if (black) {
/*  838 */         this.info |= 0x1;
/*      */       } else {
/*  840 */         this.info &= 0xFFFFFFFE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K, V> next() {
/*  848 */       Entry<K, V> next = this.right;
/*  849 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  850 */         while ((next.info & 0x40000000) == 0)
/*  851 */           next = next.left;  
/*  852 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K, V> prev() {
/*  860 */       Entry<K, V> prev = this.left;
/*  861 */       if ((this.info & 0x40000000) == 0)
/*  862 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  863 */           prev = prev.right;  
/*  864 */       return prev;
/*      */     }
/*      */     
/*      */     public V setValue(V value) {
/*  868 */       V oldValue = this.value;
/*  869 */       this.value = value;
/*  870 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry<K, V> clone() {
/*      */       Entry<K, V> c;
/*      */       try {
/*  877 */         c = (Entry<K, V>)super.clone();
/*  878 */       } catch (CloneNotSupportedException cantHappen) {
/*  879 */         throw new InternalError();
/*      */       } 
/*  881 */       c.key = this.key;
/*  882 */       c.value = this.value;
/*  883 */       c.info = this.info;
/*  884 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  889 */       if (!(o instanceof Map.Entry))
/*  890 */         return false; 
/*  891 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/*  892 */       return (Objects.equals(this.key, e.getKey()) && this.value == e.getValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  896 */       return this.key.hashCode() ^ ((this.value == null) ? 0 : System.identityHashCode(this.value));
/*      */     }
/*      */     
/*      */     public String toString() {
/*  900 */       return (new StringBuilder()).append(this.key).append("=>").append(this.value).toString();
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
/*  921 */     return (findKey((K)k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  925 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  929 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(Object k) {
/*  934 */     Entry<K, V> e = findKey((K)k);
/*  935 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public K firstKey() {
/*  939 */     if (this.tree == null)
/*  940 */       throw new NoSuchElementException(); 
/*  941 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public K lastKey() {
/*  945 */     if (this.tree == null)
/*  946 */       throw new NoSuchElementException(); 
/*  947 */     return this.lastEntry.key;
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
/*      */     Object2ReferenceRBTreeMap.Entry<K, V> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2ReferenceRBTreeMap.Entry<K, V> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2ReferenceRBTreeMap.Entry<K, V> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  978 */     int index = 0;
/*      */     TreeIterator() {
/*  980 */       this.next = Object2ReferenceRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(K k) {
/*  983 */       if ((this.next = Object2ReferenceRBTreeMap.this.locateKey(k)) != null)
/*  984 */         if (Object2ReferenceRBTreeMap.this.compare(this.next.key, k) <= 0) {
/*  985 */           this.prev = this.next;
/*  986 */           this.next = this.next.next();
/*      */         } else {
/*  988 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/*  992 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/*  995 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/*  998 */       this.next = this.next.next();
/*      */     }
/*      */     Object2ReferenceRBTreeMap.Entry<K, V> nextEntry() {
/* 1001 */       if (!hasNext())
/* 1002 */         throw new NoSuchElementException(); 
/* 1003 */       this.curr = this.prev = this.next;
/* 1004 */       this.index++;
/* 1005 */       updateNext();
/* 1006 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1009 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Object2ReferenceRBTreeMap.Entry<K, V> previousEntry() {
/* 1012 */       if (!hasPrevious())
/* 1013 */         throw new NoSuchElementException(); 
/* 1014 */       this.curr = this.next = this.prev;
/* 1015 */       this.index--;
/* 1016 */       updatePrevious();
/* 1017 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1020 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1023 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1026 */       if (this.curr == null) {
/* 1027 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1032 */       if (this.curr == this.prev)
/* 1033 */         this.index--; 
/* 1034 */       this.next = this.prev = this.curr;
/* 1035 */       updatePrevious();
/* 1036 */       updateNext();
/* 1037 */       Object2ReferenceRBTreeMap.this.remove(this.curr.key);
/* 1038 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1041 */       int i = n;
/* 1042 */       while (i-- != 0 && hasNext())
/* 1043 */         nextEntry(); 
/* 1044 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1047 */       int i = n;
/* 1048 */       while (i-- != 0 && hasPrevious())
/* 1049 */         previousEntry(); 
/* 1050 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Object2ReferenceMap.Entry<K, V>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(K k) {
/* 1063 */       super(k);
/*      */     }
/*      */     
/*      */     public Object2ReferenceMap.Entry<K, V> next() {
/* 1067 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Object2ReferenceMap.Entry<K, V> previous() {
/* 1071 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> object2ReferenceEntrySet() {
/* 1076 */     if (this.entries == null)
/* 1077 */       this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2ReferenceMap.Entry<Object2ReferenceMap.Entry<K, V>, V>>()
/*      */         {
/*      */           final Comparator<? super Object2ReferenceMap.Entry<K, V>> comparator;
/*      */           
/*      */           public Comparator<? super Object2ReferenceMap.Entry<K, V>> comparator() {
/* 1082 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> iterator() {
/* 1086 */             return new Object2ReferenceRBTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> iterator(Object2ReferenceMap.Entry<K, V> from) {
/* 1091 */             return new Object2ReferenceRBTreeMap.EntryIterator(from.getKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1096 */             if (!(o instanceof Map.Entry))
/* 1097 */               return false; 
/* 1098 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1099 */             Object2ReferenceRBTreeMap.Entry<K, V> f = Object2ReferenceRBTreeMap.this.findKey((K)e.getKey());
/* 1100 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1105 */             if (!(o instanceof Map.Entry))
/* 1106 */               return false; 
/* 1107 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1108 */             Object2ReferenceRBTreeMap.Entry<K, V> f = Object2ReferenceRBTreeMap.this.findKey((K)e.getKey());
/* 1109 */             if (f == null || f.getValue() != e.getValue())
/* 1110 */               return false; 
/* 1111 */             Object2ReferenceRBTreeMap.this.remove(f.key);
/* 1112 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1116 */             return Object2ReferenceRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1120 */             Object2ReferenceRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Object2ReferenceMap.Entry<K, V> first() {
/* 1124 */             return Object2ReferenceRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Object2ReferenceMap.Entry<K, V> last() {
/* 1128 */             return Object2ReferenceRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> subSet(Object2ReferenceMap.Entry<K, V> from, Object2ReferenceMap.Entry<K, V> to) {
/* 1133 */             return Object2ReferenceRBTreeMap.this.subMap(from.getKey(), to.getKey()).object2ReferenceEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> headSet(Object2ReferenceMap.Entry<K, V> to) {
/* 1137 */             return Object2ReferenceRBTreeMap.this.headMap(to.getKey()).object2ReferenceEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> tailSet(Object2ReferenceMap.Entry<K, V> from) {
/* 1141 */             return Object2ReferenceRBTreeMap.this.tailMap(from.getKey()).object2ReferenceEntrySet();
/*      */           }
/*      */         }; 
/* 1144 */     return this.entries;
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
/* 1160 */       super(k);
/*      */     }
/*      */     
/*      */     public K next() {
/* 1164 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1168 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractObject2ReferenceSortedMap<K, V>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1175 */       return new Object2ReferenceRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1179 */       return new Object2ReferenceRBTreeMap.KeyIterator(from);
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
/* 1194 */     if (this.keys == null)
/* 1195 */       this.keys = new KeySet(); 
/* 1196 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<V>
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public V next() {
/* 1211 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public V previous() {
/* 1215 */       return (previousEntry()).value;
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
/*      */   public ReferenceCollection<V> values() {
/* 1230 */     if (this.values == null)
/* 1231 */       this.values = new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1234 */             return new Object2ReferenceRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(Object k) {
/* 1238 */             return Object2ReferenceRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1242 */             return Object2ReferenceRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1246 */             Object2ReferenceRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1249 */     return this.values;
/*      */   }
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1253 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Object2ReferenceSortedMap<K, V> headMap(K to) {
/* 1257 */     return new Submap(null, true, to, false);
/*      */   }
/*      */   
/*      */   public Object2ReferenceSortedMap<K, V> tailMap(K from) {
/* 1261 */     return new Submap(from, false, null, true);
/*      */   }
/*      */   
/*      */   public Object2ReferenceSortedMap<K, V> subMap(K from, K to) {
/* 1265 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractObject2ReferenceSortedMap<K, V>
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
/*      */     protected transient ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ReferenceCollection<V> values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(K from, boolean bottom, K to, boolean top) {
/* 1309 */       if (!bottom && !top && Object2ReferenceRBTreeMap.this.compare(from, to) > 0)
/* 1310 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1311 */       this.from = from;
/* 1312 */       this.bottom = bottom;
/* 1313 */       this.to = to;
/* 1314 */       this.top = top;
/* 1315 */       this.defRetValue = Object2ReferenceRBTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1319 */       SubmapIterator i = new SubmapIterator();
/* 1320 */       while (i.hasNext()) {
/* 1321 */         i.nextEntry();
/* 1322 */         i.remove();
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
/* 1333 */       return ((this.bottom || Object2ReferenceRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Object2ReferenceRBTreeMap.this
/* 1334 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> object2ReferenceEntrySet() {
/* 1338 */       if (this.entries == null)
/* 1339 */         this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2ReferenceMap.Entry<Object2ReferenceMap.Entry<K, V>, V>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> iterator() {
/* 1342 */               return new Object2ReferenceRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> iterator(Object2ReferenceMap.Entry<K, V> from) {
/* 1347 */               return new Object2ReferenceRBTreeMap.Submap.SubmapEntryIterator(from.getKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Object2ReferenceMap.Entry<K, V>> comparator() {
/* 1351 */               return Object2ReferenceRBTreeMap.this.object2ReferenceEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1356 */               if (!(o instanceof Map.Entry))
/* 1357 */                 return false; 
/* 1358 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1359 */               Object2ReferenceRBTreeMap.Entry<K, V> f = Object2ReferenceRBTreeMap.this.findKey((K)e.getKey());
/* 1360 */               return (f != null && Object2ReferenceRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1365 */               if (!(o instanceof Map.Entry))
/* 1366 */                 return false; 
/* 1367 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1368 */               Object2ReferenceRBTreeMap.Entry<K, V> f = Object2ReferenceRBTreeMap.this.findKey((K)e.getKey());
/* 1369 */               if (f != null && Object2ReferenceRBTreeMap.Submap.this.in(f.key))
/* 1370 */                 Object2ReferenceRBTreeMap.Submap.this.remove(f.key); 
/* 1371 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1375 */               int c = 0;
/* 1376 */               for (Iterator<?> i = iterator(); i.hasNext(); i.next())
/* 1377 */                 c++; 
/* 1378 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1382 */               return !(new Object2ReferenceRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1386 */               Object2ReferenceRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Object2ReferenceMap.Entry<K, V> first() {
/* 1390 */               return Object2ReferenceRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Object2ReferenceMap.Entry<K, V> last() {
/* 1394 */               return Object2ReferenceRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> subSet(Object2ReferenceMap.Entry<K, V> from, Object2ReferenceMap.Entry<K, V> to) {
/* 1399 */               return Object2ReferenceRBTreeMap.Submap.this.subMap(from.getKey(), to.getKey()).object2ReferenceEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> headSet(Object2ReferenceMap.Entry<K, V> to) {
/* 1404 */               return Object2ReferenceRBTreeMap.Submap.this.headMap(to.getKey()).object2ReferenceEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> tailSet(Object2ReferenceMap.Entry<K, V> from) {
/* 1409 */               return Object2ReferenceRBTreeMap.Submap.this.tailMap(from.getKey()).object2ReferenceEntrySet();
/*      */             }
/*      */           }; 
/* 1412 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractObject2ReferenceSortedMap<K, V>.KeySet {
/*      */       public ObjectBidirectionalIterator<K> iterator() {
/* 1417 */         return new Object2ReferenceRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1421 */         return new Object2ReferenceRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> keySet() {
/* 1426 */       if (this.keys == null)
/* 1427 */         this.keys = new KeySet(); 
/* 1428 */       return this.keys;
/*      */     }
/*      */     
/*      */     public ReferenceCollection<V> values() {
/* 1432 */       if (this.values == null)
/* 1433 */         this.values = new AbstractReferenceCollection<V>()
/*      */           {
/*      */             public ObjectIterator<V> iterator() {
/* 1436 */               return new Object2ReferenceRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(Object k) {
/* 1440 */               return Object2ReferenceRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1444 */               return Object2ReferenceRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1448 */               Object2ReferenceRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1451 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object k) {
/* 1456 */       return (in((K)k) && Object2ReferenceRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(Object v) {
/* 1460 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1462 */       while (i.hasNext()) {
/* 1463 */         Object ev = (i.nextEntry()).value;
/* 1464 */         if (ev == v)
/* 1465 */           return true; 
/*      */       } 
/* 1467 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(Object k) {
/* 1473 */       K kk = (K)k; Object2ReferenceRBTreeMap.Entry<K, V> e;
/* 1474 */       return (in(kk) && (e = Object2ReferenceRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public V put(K k, V v) {
/* 1478 */       Object2ReferenceRBTreeMap.this.modified = false;
/* 1479 */       if (!in(k))
/* 1480 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1481 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1482 */       V oldValue = Object2ReferenceRBTreeMap.this.put(k, v);
/* 1483 */       return Object2ReferenceRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public V remove(Object k) {
/* 1488 */       Object2ReferenceRBTreeMap.this.modified = false;
/* 1489 */       if (!in((K)k))
/* 1490 */         return this.defRetValue; 
/* 1491 */       V oldValue = (V)Object2ReferenceRBTreeMap.this.remove(k);
/* 1492 */       return Object2ReferenceRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1496 */       SubmapIterator i = new SubmapIterator();
/* 1497 */       int n = 0;
/* 1498 */       while (i.hasNext()) {
/* 1499 */         n++;
/* 1500 */         i.nextEntry();
/*      */       } 
/* 1502 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1506 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1510 */       return Object2ReferenceRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Object2ReferenceSortedMap<K, V> headMap(K to) {
/* 1514 */       if (this.top)
/* 1515 */         return new Submap(this.from, this.bottom, to, false); 
/* 1516 */       return (Object2ReferenceRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Object2ReferenceSortedMap<K, V> tailMap(K from) {
/* 1520 */       if (this.bottom)
/* 1521 */         return new Submap(from, false, this.to, this.top); 
/* 1522 */       return (Object2ReferenceRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Object2ReferenceSortedMap<K, V> subMap(K from, K to) {
/* 1526 */       if (this.top && this.bottom)
/* 1527 */         return new Submap(from, false, to, false); 
/* 1528 */       if (!this.top)
/* 1529 */         to = (Object2ReferenceRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1530 */       if (!this.bottom)
/* 1531 */         from = (Object2ReferenceRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1532 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1533 */         return this; 
/* 1534 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2ReferenceRBTreeMap.Entry<K, V> firstEntry() {
/*      */       Object2ReferenceRBTreeMap.Entry<K, V> e;
/* 1543 */       if (Object2ReferenceRBTreeMap.this.tree == null) {
/* 1544 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1548 */       if (this.bottom) {
/* 1549 */         e = Object2ReferenceRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1551 */         e = Object2ReferenceRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1553 */         if (Object2ReferenceRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1554 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1558 */       if (e == null || (!this.top && Object2ReferenceRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1559 */         return null; 
/* 1560 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2ReferenceRBTreeMap.Entry<K, V> lastEntry() {
/*      */       Object2ReferenceRBTreeMap.Entry<K, V> e;
/* 1569 */       if (Object2ReferenceRBTreeMap.this.tree == null) {
/* 1570 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1574 */       if (this.top) {
/* 1575 */         e = Object2ReferenceRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1577 */         e = Object2ReferenceRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1579 */         if (Object2ReferenceRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1580 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1584 */       if (e == null || (!this.bottom && Object2ReferenceRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1585 */         return null; 
/* 1586 */       return e;
/*      */     }
/*      */     
/*      */     public K firstKey() {
/* 1590 */       Object2ReferenceRBTreeMap.Entry<K, V> e = firstEntry();
/* 1591 */       if (e == null)
/* 1592 */         throw new NoSuchElementException(); 
/* 1593 */       return e.key;
/*      */     }
/*      */     
/*      */     public K lastKey() {
/* 1597 */       Object2ReferenceRBTreeMap.Entry<K, V> e = lastEntry();
/* 1598 */       if (e == null)
/* 1599 */         throw new NoSuchElementException(); 
/* 1600 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Object2ReferenceRBTreeMap<K, V>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1613 */         this.next = Object2ReferenceRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(K k) {
/* 1616 */         this();
/* 1617 */         if (this.next != null)
/* 1618 */           if (!Object2ReferenceRBTreeMap.Submap.this.bottom && Object2ReferenceRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1619 */             this.prev = null;
/* 1620 */           } else if (!Object2ReferenceRBTreeMap.Submap.this.top && Object2ReferenceRBTreeMap.this.compare(k, (this.prev = Object2ReferenceRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1621 */             this.next = null;
/*      */           } else {
/* 1623 */             this.next = Object2ReferenceRBTreeMap.this.locateKey(k);
/* 1624 */             if (Object2ReferenceRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1625 */               this.prev = this.next;
/* 1626 */               this.next = this.next.next();
/*      */             } else {
/* 1628 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1634 */         this.prev = this.prev.prev();
/* 1635 */         if (!Object2ReferenceRBTreeMap.Submap.this.bottom && this.prev != null && Object2ReferenceRBTreeMap.this.compare(this.prev.key, Object2ReferenceRBTreeMap.Submap.this.from) < 0)
/* 1636 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1640 */         this.next = this.next.next();
/* 1641 */         if (!Object2ReferenceRBTreeMap.Submap.this.top && this.next != null && Object2ReferenceRBTreeMap.this.compare(this.next.key, Object2ReferenceRBTreeMap.Submap.this.to) >= 0)
/* 1642 */           this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Object2ReferenceMap.Entry<K, V>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(K k) {
/* 1651 */         super(k);
/*      */       }
/*      */       
/*      */       public Object2ReferenceMap.Entry<K, V> next() {
/* 1655 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Object2ReferenceMap.Entry<K, V> previous() {
/* 1659 */         return previousEntry();
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
/* 1677 */         super(from);
/*      */       }
/*      */       
/*      */       public K next() {
/* 1681 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public K previous() {
/* 1685 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements ObjectListIterator<V>
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public V next() {
/* 1701 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public V previous() {
/* 1705 */         return (previousEntry()).value;
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
/*      */   public Object2ReferenceRBTreeMap<K, V> clone() {
/*      */     Object2ReferenceRBTreeMap<K, V> c;
/*      */     try {
/* 1724 */       c = (Object2ReferenceRBTreeMap<K, V>)super.clone();
/* 1725 */     } catch (CloneNotSupportedException cantHappen) {
/* 1726 */       throw new InternalError();
/*      */     } 
/* 1728 */     c.keys = null;
/* 1729 */     c.values = null;
/* 1730 */     c.entries = null;
/* 1731 */     c.allocatePaths();
/* 1732 */     if (this.count != 0) {
/*      */       
/* 1734 */       Entry<K, V> rp = new Entry<>(), rq = new Entry<>();
/* 1735 */       Entry<K, V> p = rp;
/* 1736 */       rp.left(this.tree);
/* 1737 */       Entry<K, V> q = rq;
/* 1738 */       rq.pred((Entry<K, V>)null);
/*      */       while (true) {
/* 1740 */         if (!p.pred()) {
/* 1741 */           Entry<K, V> e = p.left.clone();
/* 1742 */           e.pred(q.left);
/* 1743 */           e.succ(q);
/* 1744 */           q.left(e);
/* 1745 */           p = p.left;
/* 1746 */           q = q.left;
/*      */         } else {
/* 1748 */           while (p.succ()) {
/* 1749 */             p = p.right;
/* 1750 */             if (p == null) {
/* 1751 */               q.right = null;
/* 1752 */               c.tree = rq.left;
/* 1753 */               c.firstEntry = c.tree;
/* 1754 */               while (c.firstEntry.left != null)
/* 1755 */                 c.firstEntry = c.firstEntry.left; 
/* 1756 */               c.lastEntry = c.tree;
/* 1757 */               while (c.lastEntry.right != null)
/* 1758 */                 c.lastEntry = c.lastEntry.right; 
/* 1759 */               return c;
/*      */             } 
/* 1761 */             q = q.right;
/*      */           } 
/* 1763 */           p = p.right;
/* 1764 */           q = q.right;
/*      */         } 
/* 1766 */         if (!p.succ()) {
/* 1767 */           Entry<K, V> e = p.right.clone();
/* 1768 */           e.succ(q.right);
/* 1769 */           e.pred(q);
/* 1770 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1774 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1777 */     int n = this.count;
/* 1778 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1780 */     s.defaultWriteObject();
/* 1781 */     while (n-- != 0) {
/* 1782 */       Entry<K, V> e = i.nextEntry();
/* 1783 */       s.writeObject(e.key);
/* 1784 */       s.writeObject(e.value);
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
/*      */   private Entry<K, V> readTree(ObjectInputStream s, int n, Entry<K, V> pred, Entry<K, V> succ) throws IOException, ClassNotFoundException {
/* 1805 */     if (n == 1) {
/* 1806 */       Entry<K, V> entry = new Entry<>((K)s.readObject(), (V)s.readObject());
/* 1807 */       entry.pred(pred);
/* 1808 */       entry.succ(succ);
/* 1809 */       entry.black(true);
/* 1810 */       return entry;
/*      */     } 
/* 1812 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1817 */       Entry<K, V> entry = new Entry<>((K)s.readObject(), (V)s.readObject());
/* 1818 */       entry.black(true);
/* 1819 */       entry.right(new Entry<>((K)s.readObject(), (V)s.readObject()));
/* 1820 */       entry.right.pred(entry);
/* 1821 */       entry.pred(pred);
/* 1822 */       entry.right.succ(succ);
/* 1823 */       return entry;
/*      */     } 
/*      */     
/* 1826 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1827 */     Entry<K, V> top = new Entry<>();
/* 1828 */     top.left(readTree(s, leftN, pred, top));
/* 1829 */     top.key = (K)s.readObject();
/* 1830 */     top.value = (V)s.readObject();
/* 1831 */     top.black(true);
/* 1832 */     top.right(readTree(s, rightN, top, succ));
/* 1833 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1834 */       top.right.black(false); 
/* 1835 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1838 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1843 */     setActualComparator();
/* 1844 */     allocatePaths();
/* 1845 */     if (this.count != 0) {
/* 1846 */       this.tree = readTree(s, this.count, (Entry<K, V>)null, (Entry<K, V>)null);
/*      */       
/* 1848 */       Entry<K, V> e = this.tree;
/* 1849 */       while (e.left() != null)
/* 1850 */         e = e.left(); 
/* 1851 */       this.firstEntry = e;
/* 1852 */       e = this.tree;
/* 1853 */       while (e.right() != null)
/* 1854 */         e = e.right(); 
/* 1855 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ReferenceRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */