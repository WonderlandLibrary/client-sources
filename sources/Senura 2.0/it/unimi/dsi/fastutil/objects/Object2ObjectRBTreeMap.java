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
/*      */ public class Object2ObjectRBTreeMap<K, V>
/*      */   extends AbstractObject2ObjectSortedMap<K, V>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<K, V> tree;
/*      */   protected int count;
/*      */   protected transient Entry<K, V> firstEntry;
/*      */   protected transient Entry<K, V> lastEntry;
/*      */   protected transient ObjectSortedSet<Object2ObjectMap.Entry<K, V>> entries;
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */   protected transient ObjectCollection<V> values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super K> storedComparator;
/*      */   protected transient Comparator<? super K> actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry<K, V>[] nodePath;
/*      */   
/*      */   public Object2ObjectRBTreeMap() {
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
/*      */   public Object2ObjectRBTreeMap(Comparator<? super K> c) {
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
/*      */   public Object2ObjectRBTreeMap(Map<? extends K, ? extends V> m) {
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
/*      */   public Object2ObjectRBTreeMap(SortedMap<K, V> m) {
/*  124 */     this(m.comparator());
/*  125 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectRBTreeMap(Object2ObjectMap<? extends K, ? extends V> m) {
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
/*      */   public Object2ObjectRBTreeMap(Object2ObjectSortedMap<K, V> m) {
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
/*      */   public Object2ObjectRBTreeMap(K[] k, V[] v, Comparator<? super K> c) {
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
/*      */   public Object2ObjectRBTreeMap(K[] k, V[] v) {
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
/*  672 */       if (Objects.equals(ev, v))
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
/*      */     extends AbstractObject2ObjectMap.BasicEntry<K, V>
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
/*  892 */       return (Objects.equals(this.key, e.getKey()) && Objects.equals(this.value, e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  896 */       return this.key.hashCode() ^ ((this.value == null) ? 0 : this.value.hashCode());
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
/*      */     Object2ObjectRBTreeMap.Entry<K, V> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2ObjectRBTreeMap.Entry<K, V> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2ObjectRBTreeMap.Entry<K, V> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  978 */     int index = 0;
/*      */     TreeIterator() {
/*  980 */       this.next = Object2ObjectRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(K k) {
/*  983 */       if ((this.next = Object2ObjectRBTreeMap.this.locateKey(k)) != null)
/*  984 */         if (Object2ObjectRBTreeMap.this.compare(this.next.key, k) <= 0) {
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
/*      */     Object2ObjectRBTreeMap.Entry<K, V> nextEntry() {
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
/*      */     Object2ObjectRBTreeMap.Entry<K, V> previousEntry() {
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
/* 1037 */       Object2ObjectRBTreeMap.this.remove(this.curr.key);
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
/*      */     implements ObjectListIterator<Object2ObjectMap.Entry<K, V>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(K k) {
/* 1063 */       super(k);
/*      */     }
/*      */     
/*      */     public Object2ObjectMap.Entry<K, V> next() {
/* 1067 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Object2ObjectMap.Entry<K, V> previous() {
/* 1071 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
/* 1076 */     if (this.entries == null)
/* 1077 */       this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2ObjectMap.Entry<Object2ObjectMap.Entry<K, V>, V>>()
/*      */         {
/*      */           final Comparator<? super Object2ObjectMap.Entry<K, V>> comparator;
/*      */           
/*      */           public Comparator<? super Object2ObjectMap.Entry<K, V>> comparator() {
/* 1082 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> iterator() {
/* 1086 */             return new Object2ObjectRBTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> iterator(Object2ObjectMap.Entry<K, V> from) {
/* 1091 */             return new Object2ObjectRBTreeMap.EntryIterator(from.getKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1096 */             if (!(o instanceof Map.Entry))
/* 1097 */               return false; 
/* 1098 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1099 */             Object2ObjectRBTreeMap.Entry<K, V> f = Object2ObjectRBTreeMap.this.findKey((K)e.getKey());
/* 1100 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1105 */             if (!(o instanceof Map.Entry))
/* 1106 */               return false; 
/* 1107 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1108 */             Object2ObjectRBTreeMap.Entry<K, V> f = Object2ObjectRBTreeMap.this.findKey((K)e.getKey());
/* 1109 */             if (f == null || !Objects.equals(f.getValue(), e.getValue()))
/* 1110 */               return false; 
/* 1111 */             Object2ObjectRBTreeMap.this.remove(f.key);
/* 1112 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1116 */             return Object2ObjectRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1120 */             Object2ObjectRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Object2ObjectMap.Entry<K, V> first() {
/* 1124 */             return Object2ObjectRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Object2ObjectMap.Entry<K, V> last() {
/* 1128 */             return Object2ObjectRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> subSet(Object2ObjectMap.Entry<K, V> from, Object2ObjectMap.Entry<K, V> to) {
/* 1133 */             return Object2ObjectRBTreeMap.this.subMap(from.getKey(), to.getKey()).object2ObjectEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> headSet(Object2ObjectMap.Entry<K, V> to) {
/* 1137 */             return Object2ObjectRBTreeMap.this.headMap(to.getKey()).object2ObjectEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> tailSet(Object2ObjectMap.Entry<K, V> from) {
/* 1141 */             return Object2ObjectRBTreeMap.this.tailMap(from.getKey()).object2ObjectEntrySet();
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
/*      */   private class KeySet extends AbstractObject2ObjectSortedMap<K, V>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1175 */       return new Object2ObjectRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1179 */       return new Object2ObjectRBTreeMap.KeyIterator(from);
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
/*      */   public ObjectCollection<V> values() {
/* 1230 */     if (this.values == null)
/* 1231 */       this.values = new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1234 */             return new Object2ObjectRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(Object k) {
/* 1238 */             return Object2ObjectRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1242 */             return Object2ObjectRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1246 */             Object2ObjectRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1249 */     return this.values;
/*      */   }
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1253 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Object2ObjectSortedMap<K, V> headMap(K to) {
/* 1257 */     return new Submap(null, true, to, false);
/*      */   }
/*      */   
/*      */   public Object2ObjectSortedMap<K, V> tailMap(K from) {
/* 1261 */     return new Submap(from, false, null, true);
/*      */   }
/*      */   
/*      */   public Object2ObjectSortedMap<K, V> subMap(K from, K to) {
/* 1265 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractObject2ObjectSortedMap<K, V>
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
/*      */     protected transient ObjectSortedSet<Object2ObjectMap.Entry<K, V>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ObjectCollection<V> values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(K from, boolean bottom, K to, boolean top) {
/* 1309 */       if (!bottom && !top && Object2ObjectRBTreeMap.this.compare(from, to) > 0)
/* 1310 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1311 */       this.from = from;
/* 1312 */       this.bottom = bottom;
/* 1313 */       this.to = to;
/* 1314 */       this.top = top;
/* 1315 */       this.defRetValue = Object2ObjectRBTreeMap.this.defRetValue;
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
/* 1333 */       return ((this.bottom || Object2ObjectRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Object2ObjectRBTreeMap.this
/* 1334 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
/* 1338 */       if (this.entries == null)
/* 1339 */         this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2ObjectMap.Entry<Object2ObjectMap.Entry<K, V>, V>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> iterator() {
/* 1342 */               return new Object2ObjectRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> iterator(Object2ObjectMap.Entry<K, V> from) {
/* 1347 */               return new Object2ObjectRBTreeMap.Submap.SubmapEntryIterator(from.getKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Object2ObjectMap.Entry<K, V>> comparator() {
/* 1351 */               return Object2ObjectRBTreeMap.this.object2ObjectEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1356 */               if (!(o instanceof Map.Entry))
/* 1357 */                 return false; 
/* 1358 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1359 */               Object2ObjectRBTreeMap.Entry<K, V> f = Object2ObjectRBTreeMap.this.findKey((K)e.getKey());
/* 1360 */               return (f != null && Object2ObjectRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1365 */               if (!(o instanceof Map.Entry))
/* 1366 */                 return false; 
/* 1367 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1368 */               Object2ObjectRBTreeMap.Entry<K, V> f = Object2ObjectRBTreeMap.this.findKey((K)e.getKey());
/* 1369 */               if (f != null && Object2ObjectRBTreeMap.Submap.this.in(f.key))
/* 1370 */                 Object2ObjectRBTreeMap.Submap.this.remove(f.key); 
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
/* 1382 */               return !(new Object2ObjectRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1386 */               Object2ObjectRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Object2ObjectMap.Entry<K, V> first() {
/* 1390 */               return Object2ObjectRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Object2ObjectMap.Entry<K, V> last() {
/* 1394 */               return Object2ObjectRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> subSet(Object2ObjectMap.Entry<K, V> from, Object2ObjectMap.Entry<K, V> to) {
/* 1399 */               return Object2ObjectRBTreeMap.Submap.this.subMap(from.getKey(), to.getKey()).object2ObjectEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> headSet(Object2ObjectMap.Entry<K, V> to) {
/* 1403 */               return Object2ObjectRBTreeMap.Submap.this.headMap(to.getKey()).object2ObjectEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> tailSet(Object2ObjectMap.Entry<K, V> from) {
/* 1407 */               return Object2ObjectRBTreeMap.Submap.this.tailMap(from.getKey()).object2ObjectEntrySet();
/*      */             }
/*      */           }; 
/* 1410 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractObject2ObjectSortedMap<K, V>.KeySet {
/*      */       public ObjectBidirectionalIterator<K> iterator() {
/* 1415 */         return new Object2ObjectRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1419 */         return new Object2ObjectRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> keySet() {
/* 1424 */       if (this.keys == null)
/* 1425 */         this.keys = new KeySet(); 
/* 1426 */       return this.keys;
/*      */     }
/*      */     
/*      */     public ObjectCollection<V> values() {
/* 1430 */       if (this.values == null)
/* 1431 */         this.values = new AbstractObjectCollection<V>()
/*      */           {
/*      */             public ObjectIterator<V> iterator() {
/* 1434 */               return new Object2ObjectRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(Object k) {
/* 1438 */               return Object2ObjectRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1442 */               return Object2ObjectRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1446 */               Object2ObjectRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1449 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object k) {
/* 1454 */       return (in((K)k) && Object2ObjectRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(Object v) {
/* 1458 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1460 */       while (i.hasNext()) {
/* 1461 */         Object ev = (i.nextEntry()).value;
/* 1462 */         if (Objects.equals(ev, v))
/* 1463 */           return true; 
/*      */       } 
/* 1465 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(Object k) {
/* 1471 */       K kk = (K)k; Object2ObjectRBTreeMap.Entry<K, V> e;
/* 1472 */       return (in(kk) && (e = Object2ObjectRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public V put(K k, V v) {
/* 1476 */       Object2ObjectRBTreeMap.this.modified = false;
/* 1477 */       if (!in(k))
/* 1478 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1479 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1480 */       V oldValue = Object2ObjectRBTreeMap.this.put(k, v);
/* 1481 */       return Object2ObjectRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public V remove(Object k) {
/* 1486 */       Object2ObjectRBTreeMap.this.modified = false;
/* 1487 */       if (!in((K)k))
/* 1488 */         return this.defRetValue; 
/* 1489 */       V oldValue = (V)Object2ObjectRBTreeMap.this.remove(k);
/* 1490 */       return Object2ObjectRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1494 */       SubmapIterator i = new SubmapIterator();
/* 1495 */       int n = 0;
/* 1496 */       while (i.hasNext()) {
/* 1497 */         n++;
/* 1498 */         i.nextEntry();
/*      */       } 
/* 1500 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1504 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1508 */       return Object2ObjectRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Object2ObjectSortedMap<K, V> headMap(K to) {
/* 1512 */       if (this.top)
/* 1513 */         return new Submap(this.from, this.bottom, to, false); 
/* 1514 */       return (Object2ObjectRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Object2ObjectSortedMap<K, V> tailMap(K from) {
/* 1518 */       if (this.bottom)
/* 1519 */         return new Submap(from, false, this.to, this.top); 
/* 1520 */       return (Object2ObjectRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Object2ObjectSortedMap<K, V> subMap(K from, K to) {
/* 1524 */       if (this.top && this.bottom)
/* 1525 */         return new Submap(from, false, to, false); 
/* 1526 */       if (!this.top)
/* 1527 */         to = (Object2ObjectRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1528 */       if (!this.bottom)
/* 1529 */         from = (Object2ObjectRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1530 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1531 */         return this; 
/* 1532 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2ObjectRBTreeMap.Entry<K, V> firstEntry() {
/*      */       Object2ObjectRBTreeMap.Entry<K, V> e;
/* 1541 */       if (Object2ObjectRBTreeMap.this.tree == null) {
/* 1542 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1546 */       if (this.bottom) {
/* 1547 */         e = Object2ObjectRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1549 */         e = Object2ObjectRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1551 */         if (Object2ObjectRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1552 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1556 */       if (e == null || (!this.top && Object2ObjectRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1557 */         return null; 
/* 1558 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2ObjectRBTreeMap.Entry<K, V> lastEntry() {
/*      */       Object2ObjectRBTreeMap.Entry<K, V> e;
/* 1567 */       if (Object2ObjectRBTreeMap.this.tree == null) {
/* 1568 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1572 */       if (this.top) {
/* 1573 */         e = Object2ObjectRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1575 */         e = Object2ObjectRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1577 */         if (Object2ObjectRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1578 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1582 */       if (e == null || (!this.bottom && Object2ObjectRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1583 */         return null; 
/* 1584 */       return e;
/*      */     }
/*      */     
/*      */     public K firstKey() {
/* 1588 */       Object2ObjectRBTreeMap.Entry<K, V> e = firstEntry();
/* 1589 */       if (e == null)
/* 1590 */         throw new NoSuchElementException(); 
/* 1591 */       return e.key;
/*      */     }
/*      */     
/*      */     public K lastKey() {
/* 1595 */       Object2ObjectRBTreeMap.Entry<K, V> e = lastEntry();
/* 1596 */       if (e == null)
/* 1597 */         throw new NoSuchElementException(); 
/* 1598 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Object2ObjectRBTreeMap<K, V>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1611 */         this.next = Object2ObjectRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(K k) {
/* 1614 */         this();
/* 1615 */         if (this.next != null)
/* 1616 */           if (!Object2ObjectRBTreeMap.Submap.this.bottom && Object2ObjectRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1617 */             this.prev = null;
/* 1618 */           } else if (!Object2ObjectRBTreeMap.Submap.this.top && Object2ObjectRBTreeMap.this.compare(k, (this.prev = Object2ObjectRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1619 */             this.next = null;
/*      */           } else {
/* 1621 */             this.next = Object2ObjectRBTreeMap.this.locateKey(k);
/* 1622 */             if (Object2ObjectRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1623 */               this.prev = this.next;
/* 1624 */               this.next = this.next.next();
/*      */             } else {
/* 1626 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1632 */         this.prev = this.prev.prev();
/* 1633 */         if (!Object2ObjectRBTreeMap.Submap.this.bottom && this.prev != null && Object2ObjectRBTreeMap.this.compare(this.prev.key, Object2ObjectRBTreeMap.Submap.this.from) < 0)
/* 1634 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1638 */         this.next = this.next.next();
/* 1639 */         if (!Object2ObjectRBTreeMap.Submap.this.top && this.next != null && Object2ObjectRBTreeMap.this.compare(this.next.key, Object2ObjectRBTreeMap.Submap.this.to) >= 0)
/* 1640 */           this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Object2ObjectMap.Entry<K, V>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(K k) {
/* 1649 */         super(k);
/*      */       }
/*      */       
/*      */       public Object2ObjectMap.Entry<K, V> next() {
/* 1653 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Object2ObjectMap.Entry<K, V> previous() {
/* 1657 */         return previousEntry();
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
/* 1675 */         super(from);
/*      */       }
/*      */       
/*      */       public K next() {
/* 1679 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public K previous() {
/* 1683 */         return (previousEntry()).key;
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
/* 1699 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public V previous() {
/* 1703 */         return (previousEntry()).value;
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
/*      */   public Object2ObjectRBTreeMap<K, V> clone() {
/*      */     Object2ObjectRBTreeMap<K, V> c;
/*      */     try {
/* 1722 */       c = (Object2ObjectRBTreeMap<K, V>)super.clone();
/* 1723 */     } catch (CloneNotSupportedException cantHappen) {
/* 1724 */       throw new InternalError();
/*      */     } 
/* 1726 */     c.keys = null;
/* 1727 */     c.values = null;
/* 1728 */     c.entries = null;
/* 1729 */     c.allocatePaths();
/* 1730 */     if (this.count != 0) {
/*      */       
/* 1732 */       Entry<K, V> rp = new Entry<>(), rq = new Entry<>();
/* 1733 */       Entry<K, V> p = rp;
/* 1734 */       rp.left(this.tree);
/* 1735 */       Entry<K, V> q = rq;
/* 1736 */       rq.pred((Entry<K, V>)null);
/*      */       while (true) {
/* 1738 */         if (!p.pred()) {
/* 1739 */           Entry<K, V> e = p.left.clone();
/* 1740 */           e.pred(q.left);
/* 1741 */           e.succ(q);
/* 1742 */           q.left(e);
/* 1743 */           p = p.left;
/* 1744 */           q = q.left;
/*      */         } else {
/* 1746 */           while (p.succ()) {
/* 1747 */             p = p.right;
/* 1748 */             if (p == null) {
/* 1749 */               q.right = null;
/* 1750 */               c.tree = rq.left;
/* 1751 */               c.firstEntry = c.tree;
/* 1752 */               while (c.firstEntry.left != null)
/* 1753 */                 c.firstEntry = c.firstEntry.left; 
/* 1754 */               c.lastEntry = c.tree;
/* 1755 */               while (c.lastEntry.right != null)
/* 1756 */                 c.lastEntry = c.lastEntry.right; 
/* 1757 */               return c;
/*      */             } 
/* 1759 */             q = q.right;
/*      */           } 
/* 1761 */           p = p.right;
/* 1762 */           q = q.right;
/*      */         } 
/* 1764 */         if (!p.succ()) {
/* 1765 */           Entry<K, V> e = p.right.clone();
/* 1766 */           e.succ(q.right);
/* 1767 */           e.pred(q);
/* 1768 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1772 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1775 */     int n = this.count;
/* 1776 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1778 */     s.defaultWriteObject();
/* 1779 */     while (n-- != 0) {
/* 1780 */       Entry<K, V> e = i.nextEntry();
/* 1781 */       s.writeObject(e.key);
/* 1782 */       s.writeObject(e.value);
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
/* 1803 */     if (n == 1) {
/* 1804 */       Entry<K, V> entry = new Entry<>((K)s.readObject(), (V)s.readObject());
/* 1805 */       entry.pred(pred);
/* 1806 */       entry.succ(succ);
/* 1807 */       entry.black(true);
/* 1808 */       return entry;
/*      */     } 
/* 1810 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1815 */       Entry<K, V> entry = new Entry<>((K)s.readObject(), (V)s.readObject());
/* 1816 */       entry.black(true);
/* 1817 */       entry.right(new Entry<>((K)s.readObject(), (V)s.readObject()));
/* 1818 */       entry.right.pred(entry);
/* 1819 */       entry.pred(pred);
/* 1820 */       entry.right.succ(succ);
/* 1821 */       return entry;
/*      */     } 
/*      */     
/* 1824 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1825 */     Entry<K, V> top = new Entry<>();
/* 1826 */     top.left(readTree(s, leftN, pred, top));
/* 1827 */     top.key = (K)s.readObject();
/* 1828 */     top.value = (V)s.readObject();
/* 1829 */     top.black(true);
/* 1830 */     top.right(readTree(s, rightN, top, succ));
/* 1831 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1832 */       top.right.black(false); 
/* 1833 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1836 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1841 */     setActualComparator();
/* 1842 */     allocatePaths();
/* 1843 */     if (this.count != 0) {
/* 1844 */       this.tree = readTree(s, this.count, (Entry<K, V>)null, (Entry<K, V>)null);
/*      */       
/* 1846 */       Entry<K, V> e = this.tree;
/* 1847 */       while (e.left() != null)
/* 1848 */         e = e.left(); 
/* 1849 */       this.firstEntry = e;
/* 1850 */       e = this.tree;
/* 1851 */       while (e.right() != null)
/* 1852 */         e = e.right(); 
/* 1853 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ObjectRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */