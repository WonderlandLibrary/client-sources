/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
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
/*      */ public class Object2BooleanRBTreeMap<K>
/*      */   extends AbstractObject2BooleanSortedMap<K>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<K> tree;
/*      */   protected int count;
/*      */   protected transient Entry<K> firstEntry;
/*      */   protected transient Entry<K> lastEntry;
/*      */   protected transient ObjectSortedSet<Object2BooleanMap.Entry<K>> entries;
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */   protected transient BooleanCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super K> storedComparator;
/*      */   protected transient Comparator<? super K> actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry<K>[] nodePath;
/*      */   
/*      */   public Object2BooleanRBTreeMap() {
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
/*   94 */     this.actualComparator = this.storedComparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanRBTreeMap(Comparator<? super K> c) {
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
/*      */   public Object2BooleanRBTreeMap(Map<? extends K, ? extends Boolean> m) {
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
/*      */   public Object2BooleanRBTreeMap(SortedMap<K, Boolean> m) {
/*  125 */     this(m.comparator());
/*  126 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanRBTreeMap(Object2BooleanMap<? extends K> m) {
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
/*      */   public Object2BooleanRBTreeMap(Object2BooleanSortedMap<K> m) {
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
/*      */   public Object2BooleanRBTreeMap(K[] k, boolean[] v, Comparator<? super K> c) {
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
/*      */   public Object2BooleanRBTreeMap(K[] k, boolean[] v) {
/*  181 */     this(k, v, (Comparator<? super K>)null);
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
/*  209 */     return (this.actualComparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.actualComparator.compare(k1, k2);
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
/*  221 */     Entry<K> e = this.tree;
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
/*      */   final Entry<K> locateKey(K k) {
/*  237 */     Entry<K> e = this.tree, last = this.tree;
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
/*  254 */     this.nodePath = (Entry<K>[])new Entry[64];
/*      */   }
/*      */   
/*      */   public boolean put(K k, boolean v) {
/*  258 */     Entry<K> e = add(k);
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
/*      */   private Entry<K> add(K k) {
/*      */     Entry<K> e;
/*  278 */     this.modified = false;
/*  279 */     int maxDepth = 0;
/*      */     
/*  281 */     if (this.tree == null) {
/*  282 */       this.count++;
/*  283 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*      */     } else {
/*  285 */       Entry<K> p = this.tree;
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
/*  298 */             e = new Entry<>(k, this.defRetValue);
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
/*  310 */           e = new Entry<>(k, this.defRetValue);
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
/*  325 */           Entry<K> entry1 = (this.nodePath[i - 1]).right;
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
/*  336 */             Entry<K> entry = this.nodePath[i];
/*  337 */             entry1 = entry.right;
/*  338 */             entry.right = entry1.left;
/*  339 */             entry1.left = entry;
/*  340 */             (this.nodePath[i - 1]).left = entry1;
/*  341 */             if (entry1.pred()) {
/*  342 */               entry1.pred(false);
/*  343 */               entry.succ(entry1);
/*      */             } 
/*      */           } 
/*  346 */           Entry<K> entry2 = this.nodePath[i - 1];
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
/*  366 */         Entry<K> y = (this.nodePath[i - 1]).left;
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
/*  377 */           Entry<K> entry = this.nodePath[i];
/*  378 */           y = entry.left;
/*  379 */           entry.left = y.right;
/*  380 */           y.right = entry;
/*  381 */           (this.nodePath[i - 1]).right = y;
/*  382 */           if (y.succ()) {
/*  383 */             y.succ(false);
/*  384 */             entry.pred(y);
/*      */           } 
/*      */         } 
/*  387 */         Entry<K> x = this.nodePath[i - 1];
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
/*      */   public boolean removeBoolean(Object k) {
/*  422 */     this.modified = false;
/*  423 */     if (this.tree == null)
/*  424 */       return this.defRetValue; 
/*  425 */     Entry<K> p = this.tree;
/*      */     
/*  427 */     int i = 0;
/*  428 */     K kk = (K)k;
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
/*  477 */       Entry<K> r = p.right;
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
/*      */         Entry<K> s;
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
/*  536 */           Entry<K> x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  537 */           if (!x.black()) {
/*  538 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  542 */         if (!this.dirPath[i - 1]) {
/*  543 */           Entry<K> w = (this.nodePath[i - 1]).right;
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
/*  568 */               Entry<K> y = w.left;
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
/*  599 */           Entry<K> w = (this.nodePath[i - 1]).left;
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
/*  624 */               Entry<K> y = w.right;
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
/*      */   private static final class Entry<K>
/*      */     extends AbstractObject2BooleanMap.BasicEntry<K>
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
/*  713 */       super((K)null, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(K k, boolean v) {
/*  724 */       super(k, v);
/*  725 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> left() {
/*  733 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> right() {
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
/*      */     void pred(Entry<K> pred) {
/*  790 */       this.info |= 0x40000000;
/*  791 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<K> succ) {
/*  800 */       this.info |= Integer.MIN_VALUE;
/*  801 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<K> left) {
/*  810 */       this.info &= 0xBFFFFFFF;
/*  811 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<K> right) {
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
/*      */     Entry<K> next() {
/*  849 */       Entry<K> next = this.right;
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
/*      */     Entry<K> prev() {
/*  861 */       Entry<K> prev = this.left;
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
/*      */     public Entry<K> clone() {
/*      */       Entry<K> c;
/*      */       try {
/*  878 */         c = (Entry<K>)super.clone();
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
/*  892 */       Map.Entry<K, Boolean> e = (Map.Entry<K, Boolean>)o;
/*  893 */       return (Objects.equals(this.key, e.getKey()) && this.value == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  897 */       return this.key.hashCode() ^ (this.value ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  901 */       return (new StringBuilder()).append(this.key).append("=>").append(this.value).toString();
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
/*  922 */     return (findKey((K)k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  926 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  930 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getBoolean(Object k) {
/*  935 */     Entry<K> e = findKey((K)k);
/*  936 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public K firstKey() {
/*  940 */     if (this.tree == null)
/*  941 */       throw new NoSuchElementException(); 
/*  942 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public K lastKey() {
/*  946 */     if (this.tree == null)
/*  947 */       throw new NoSuchElementException(); 
/*  948 */     return this.lastEntry.key;
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
/*      */     Object2BooleanRBTreeMap.Entry<K> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2BooleanRBTreeMap.Entry<K> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2BooleanRBTreeMap.Entry<K> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  979 */     int index = 0;
/*      */     TreeIterator() {
/*  981 */       this.next = Object2BooleanRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(K k) {
/*  984 */       if ((this.next = Object2BooleanRBTreeMap.this.locateKey(k)) != null)
/*  985 */         if (Object2BooleanRBTreeMap.this.compare(this.next.key, k) <= 0) {
/*  986 */           this.prev = this.next;
/*  987 */           this.next = this.next.next();
/*      */         } else {
/*  989 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/*  993 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/*  996 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/*  999 */       this.next = this.next.next();
/*      */     }
/*      */     Object2BooleanRBTreeMap.Entry<K> nextEntry() {
/* 1002 */       if (!hasNext())
/* 1003 */         throw new NoSuchElementException(); 
/* 1004 */       this.curr = this.prev = this.next;
/* 1005 */       this.index++;
/* 1006 */       updateNext();
/* 1007 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1010 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Object2BooleanRBTreeMap.Entry<K> previousEntry() {
/* 1013 */       if (!hasPrevious())
/* 1014 */         throw new NoSuchElementException(); 
/* 1015 */       this.curr = this.next = this.prev;
/* 1016 */       this.index--;
/* 1017 */       updatePrevious();
/* 1018 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1021 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1024 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1027 */       if (this.curr == null) {
/* 1028 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1033 */       if (this.curr == this.prev)
/* 1034 */         this.index--; 
/* 1035 */       this.next = this.prev = this.curr;
/* 1036 */       updatePrevious();
/* 1037 */       updateNext();
/* 1038 */       Object2BooleanRBTreeMap.this.removeBoolean(this.curr.key);
/* 1039 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1042 */       int i = n;
/* 1043 */       while (i-- != 0 && hasNext())
/* 1044 */         nextEntry(); 
/* 1045 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1048 */       int i = n;
/* 1049 */       while (i-- != 0 && hasPrevious())
/* 1050 */         previousEntry(); 
/* 1051 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Object2BooleanMap.Entry<K>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(K k) {
/* 1064 */       super(k);
/*      */     }
/*      */     
/*      */     public Object2BooleanMap.Entry<K> next() {
/* 1068 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Object2BooleanMap.Entry<K> previous() {
/* 1072 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Object2BooleanMap.Entry<K>> object2BooleanEntrySet() {
/* 1077 */     if (this.entries == null)
/* 1078 */       this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2BooleanMap.Entry<Object2BooleanMap.Entry<K>>>()
/*      */         {
/*      */           final Comparator<? super Object2BooleanMap.Entry<K>> comparator;
/*      */           
/*      */           public Comparator<? super Object2BooleanMap.Entry<K>> comparator() {
/* 1083 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> iterator() {
/* 1087 */             return new Object2BooleanRBTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> iterator(Object2BooleanMap.Entry<K> from) {
/* 1092 */             return new Object2BooleanRBTreeMap.EntryIterator(from.getKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1097 */             if (!(o instanceof Map.Entry))
/* 1098 */               return false; 
/* 1099 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1100 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1101 */               return false; 
/* 1102 */             Object2BooleanRBTreeMap.Entry<K> f = Object2BooleanRBTreeMap.this.findKey((K)e.getKey());
/* 1103 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1108 */             if (!(o instanceof Map.Entry))
/* 1109 */               return false; 
/* 1110 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1111 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1112 */               return false; 
/* 1113 */             Object2BooleanRBTreeMap.Entry<K> f = Object2BooleanRBTreeMap.this.findKey((K)e.getKey());
/* 1114 */             if (f == null || f.getBooleanValue() != ((Boolean)e.getValue()).booleanValue())
/* 1115 */               return false; 
/* 1116 */             Object2BooleanRBTreeMap.this.removeBoolean(f.key);
/* 1117 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1121 */             return Object2BooleanRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1125 */             Object2BooleanRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Object2BooleanMap.Entry<K> first() {
/* 1129 */             return Object2BooleanRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Object2BooleanMap.Entry<K> last() {
/* 1133 */             return Object2BooleanRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2BooleanMap.Entry<K>> subSet(Object2BooleanMap.Entry<K> from, Object2BooleanMap.Entry<K> to) {
/* 1138 */             return Object2BooleanRBTreeMap.this.subMap(from.getKey(), to.getKey()).object2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2BooleanMap.Entry<K>> headSet(Object2BooleanMap.Entry<K> to) {
/* 1142 */             return Object2BooleanRBTreeMap.this.headMap(to.getKey()).object2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2BooleanMap.Entry<K>> tailSet(Object2BooleanMap.Entry<K> from) {
/* 1146 */             return Object2BooleanRBTreeMap.this.tailMap(from.getKey()).object2BooleanEntrySet();
/*      */           }
/*      */         }; 
/* 1149 */     return this.entries;
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
/* 1165 */       super(k);
/*      */     }
/*      */     
/*      */     public K next() {
/* 1169 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1173 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractObject2BooleanSortedMap<K>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1180 */       return new Object2BooleanRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1184 */       return new Object2BooleanRBTreeMap.KeyIterator(from);
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
/* 1199 */     if (this.keys == null)
/* 1200 */       this.keys = new KeySet(); 
/* 1201 */     return this.keys;
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
/* 1216 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public boolean previousBoolean() {
/* 1220 */       return (previousEntry()).value;
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
/* 1235 */     if (this.values == null)
/* 1236 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1239 */             return (BooleanIterator)new Object2BooleanRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(boolean k) {
/* 1243 */             return Object2BooleanRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1247 */             return Object2BooleanRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1251 */             Object2BooleanRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1254 */     return this.values;
/*      */   }
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1258 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Object2BooleanSortedMap<K> headMap(K to) {
/* 1262 */     return new Submap(null, true, to, false);
/*      */   }
/*      */   
/*      */   public Object2BooleanSortedMap<K> tailMap(K from) {
/* 1266 */     return new Submap(from, false, null, true);
/*      */   }
/*      */   
/*      */   public Object2BooleanSortedMap<K> subMap(K from, K to) {
/* 1270 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractObject2BooleanSortedMap<K>
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
/*      */     protected transient ObjectSortedSet<Object2BooleanMap.Entry<K>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(K from, boolean bottom, K to, boolean top) {
/* 1314 */       if (!bottom && !top && Object2BooleanRBTreeMap.this.compare(from, to) > 0)
/* 1315 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1316 */       this.from = from;
/* 1317 */       this.bottom = bottom;
/* 1318 */       this.to = to;
/* 1319 */       this.top = top;
/* 1320 */       this.defRetValue = Object2BooleanRBTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1324 */       SubmapIterator i = new SubmapIterator();
/* 1325 */       while (i.hasNext()) {
/* 1326 */         i.nextEntry();
/* 1327 */         i.remove();
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
/* 1338 */       return ((this.bottom || Object2BooleanRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Object2BooleanRBTreeMap.this
/* 1339 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> object2BooleanEntrySet() {
/* 1343 */       if (this.entries == null)
/* 1344 */         this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2BooleanMap.Entry<Object2BooleanMap.Entry<K>>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> iterator() {
/* 1347 */               return new Object2BooleanRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> iterator(Object2BooleanMap.Entry<K> from) {
/* 1352 */               return new Object2BooleanRBTreeMap.Submap.SubmapEntryIterator(from.getKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Object2BooleanMap.Entry<K>> comparator() {
/* 1356 */               return Object2BooleanRBTreeMap.this.object2BooleanEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1361 */               if (!(o instanceof Map.Entry))
/* 1362 */                 return false; 
/* 1363 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1364 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1365 */                 return false; 
/* 1366 */               Object2BooleanRBTreeMap.Entry<K> f = Object2BooleanRBTreeMap.this.findKey((K)e.getKey());
/* 1367 */               return (f != null && Object2BooleanRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1372 */               if (!(o instanceof Map.Entry))
/* 1373 */                 return false; 
/* 1374 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1375 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1376 */                 return false; 
/* 1377 */               Object2BooleanRBTreeMap.Entry<K> f = Object2BooleanRBTreeMap.this.findKey((K)e.getKey());
/* 1378 */               if (f != null && Object2BooleanRBTreeMap.Submap.this.in(f.key))
/* 1379 */                 Object2BooleanRBTreeMap.Submap.this.removeBoolean(f.key); 
/* 1380 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1384 */               int c = 0;
/* 1385 */               for (Iterator<?> i = iterator(); i.hasNext(); i.next())
/* 1386 */                 c++; 
/* 1387 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1391 */               return !(new Object2BooleanRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1395 */               Object2BooleanRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Object2BooleanMap.Entry<K> first() {
/* 1399 */               return Object2BooleanRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Object2BooleanMap.Entry<K> last() {
/* 1403 */               return Object2BooleanRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2BooleanMap.Entry<K>> subSet(Object2BooleanMap.Entry<K> from, Object2BooleanMap.Entry<K> to) {
/* 1408 */               return Object2BooleanRBTreeMap.Submap.this.subMap(from.getKey(), to.getKey()).object2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2BooleanMap.Entry<K>> headSet(Object2BooleanMap.Entry<K> to) {
/* 1412 */               return Object2BooleanRBTreeMap.Submap.this.headMap(to.getKey()).object2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2BooleanMap.Entry<K>> tailSet(Object2BooleanMap.Entry<K> from) {
/* 1416 */               return Object2BooleanRBTreeMap.Submap.this.tailMap(from.getKey()).object2BooleanEntrySet();
/*      */             }
/*      */           }; 
/* 1419 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractObject2BooleanSortedMap<K>.KeySet {
/*      */       public ObjectBidirectionalIterator<K> iterator() {
/* 1424 */         return new Object2BooleanRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1428 */         return new Object2BooleanRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> keySet() {
/* 1433 */       if (this.keys == null)
/* 1434 */         this.keys = new KeySet(); 
/* 1435 */       return this.keys;
/*      */     }
/*      */     
/*      */     public BooleanCollection values() {
/* 1439 */       if (this.values == null)
/* 1440 */         this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */           {
/*      */             public BooleanIterator iterator() {
/* 1443 */               return (BooleanIterator)new Object2BooleanRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(boolean k) {
/* 1447 */               return Object2BooleanRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1451 */               return Object2BooleanRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1455 */               Object2BooleanRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1458 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object k) {
/* 1463 */       return (in((K)k) && Object2BooleanRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(boolean v) {
/* 1467 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1469 */       while (i.hasNext()) {
/* 1470 */         boolean ev = (i.nextEntry()).value;
/* 1471 */         if (ev == v)
/* 1472 */           return true; 
/*      */       } 
/* 1474 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean getBoolean(Object k) {
/* 1480 */       K kk = (K)k; Object2BooleanRBTreeMap.Entry<K> e;
/* 1481 */       return (in(kk) && (e = Object2BooleanRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public boolean put(K k, boolean v) {
/* 1485 */       Object2BooleanRBTreeMap.this.modified = false;
/* 1486 */       if (!in(k))
/* 1487 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1488 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1489 */       boolean oldValue = Object2BooleanRBTreeMap.this.put(k, v);
/* 1490 */       return Object2BooleanRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeBoolean(Object k) {
/* 1495 */       Object2BooleanRBTreeMap.this.modified = false;
/* 1496 */       if (!in((K)k))
/* 1497 */         return this.defRetValue; 
/* 1498 */       boolean oldValue = Object2BooleanRBTreeMap.this.removeBoolean(k);
/* 1499 */       return Object2BooleanRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1503 */       SubmapIterator i = new SubmapIterator();
/* 1504 */       int n = 0;
/* 1505 */       while (i.hasNext()) {
/* 1506 */         n++;
/* 1507 */         i.nextEntry();
/*      */       } 
/* 1509 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1513 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1517 */       return Object2BooleanRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Object2BooleanSortedMap<K> headMap(K to) {
/* 1521 */       if (this.top)
/* 1522 */         return new Submap(this.from, this.bottom, to, false); 
/* 1523 */       return (Object2BooleanRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Object2BooleanSortedMap<K> tailMap(K from) {
/* 1527 */       if (this.bottom)
/* 1528 */         return new Submap(from, false, this.to, this.top); 
/* 1529 */       return (Object2BooleanRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Object2BooleanSortedMap<K> subMap(K from, K to) {
/* 1533 */       if (this.top && this.bottom)
/* 1534 */         return new Submap(from, false, to, false); 
/* 1535 */       if (!this.top)
/* 1536 */         to = (Object2BooleanRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1537 */       if (!this.bottom)
/* 1538 */         from = (Object2BooleanRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1539 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1540 */         return this; 
/* 1541 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2BooleanRBTreeMap.Entry<K> firstEntry() {
/*      */       Object2BooleanRBTreeMap.Entry<K> e;
/* 1550 */       if (Object2BooleanRBTreeMap.this.tree == null) {
/* 1551 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1555 */       if (this.bottom) {
/* 1556 */         e = Object2BooleanRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1558 */         e = Object2BooleanRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1560 */         if (Object2BooleanRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1561 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1565 */       if (e == null || (!this.top && Object2BooleanRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1566 */         return null; 
/* 1567 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2BooleanRBTreeMap.Entry<K> lastEntry() {
/*      */       Object2BooleanRBTreeMap.Entry<K> e;
/* 1576 */       if (Object2BooleanRBTreeMap.this.tree == null) {
/* 1577 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1581 */       if (this.top) {
/* 1582 */         e = Object2BooleanRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1584 */         e = Object2BooleanRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1586 */         if (Object2BooleanRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1587 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1591 */       if (e == null || (!this.bottom && Object2BooleanRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1592 */         return null; 
/* 1593 */       return e;
/*      */     }
/*      */     
/*      */     public K firstKey() {
/* 1597 */       Object2BooleanRBTreeMap.Entry<K> e = firstEntry();
/* 1598 */       if (e == null)
/* 1599 */         throw new NoSuchElementException(); 
/* 1600 */       return e.key;
/*      */     }
/*      */     
/*      */     public K lastKey() {
/* 1604 */       Object2BooleanRBTreeMap.Entry<K> e = lastEntry();
/* 1605 */       if (e == null)
/* 1606 */         throw new NoSuchElementException(); 
/* 1607 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Object2BooleanRBTreeMap<K>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1620 */         this.next = Object2BooleanRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(K k) {
/* 1623 */         this();
/* 1624 */         if (this.next != null)
/* 1625 */           if (!Object2BooleanRBTreeMap.Submap.this.bottom && Object2BooleanRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1626 */             this.prev = null;
/* 1627 */           } else if (!Object2BooleanRBTreeMap.Submap.this.top && Object2BooleanRBTreeMap.this.compare(k, (this.prev = Object2BooleanRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1628 */             this.next = null;
/*      */           } else {
/* 1630 */             this.next = Object2BooleanRBTreeMap.this.locateKey(k);
/* 1631 */             if (Object2BooleanRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1632 */               this.prev = this.next;
/* 1633 */               this.next = this.next.next();
/*      */             } else {
/* 1635 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1641 */         this.prev = this.prev.prev();
/* 1642 */         if (!Object2BooleanRBTreeMap.Submap.this.bottom && this.prev != null && Object2BooleanRBTreeMap.this.compare(this.prev.key, Object2BooleanRBTreeMap.Submap.this.from) < 0)
/* 1643 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1647 */         this.next = this.next.next();
/* 1648 */         if (!Object2BooleanRBTreeMap.Submap.this.top && this.next != null && Object2BooleanRBTreeMap.this.compare(this.next.key, Object2BooleanRBTreeMap.Submap.this.to) >= 0)
/* 1649 */           this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Object2BooleanMap.Entry<K>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(K k) {
/* 1658 */         super(k);
/*      */       }
/*      */       
/*      */       public Object2BooleanMap.Entry<K> next() {
/* 1662 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Object2BooleanMap.Entry<K> previous() {
/* 1666 */         return previousEntry();
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
/* 1684 */         super(from);
/*      */       }
/*      */       
/*      */       public K next() {
/* 1688 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public K previous() {
/* 1692 */         return (previousEntry()).key;
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
/* 1708 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public boolean previousBoolean() {
/* 1712 */         return (previousEntry()).value;
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
/*      */   public Object2BooleanRBTreeMap<K> clone() {
/*      */     Object2BooleanRBTreeMap<K> c;
/*      */     try {
/* 1731 */       c = (Object2BooleanRBTreeMap<K>)super.clone();
/* 1732 */     } catch (CloneNotSupportedException cantHappen) {
/* 1733 */       throw new InternalError();
/*      */     } 
/* 1735 */     c.keys = null;
/* 1736 */     c.values = null;
/* 1737 */     c.entries = null;
/* 1738 */     c.allocatePaths();
/* 1739 */     if (this.count != 0) {
/*      */       
/* 1741 */       Entry<K> rp = new Entry<>(), rq = new Entry<>();
/* 1742 */       Entry<K> p = rp;
/* 1743 */       rp.left(this.tree);
/* 1744 */       Entry<K> q = rq;
/* 1745 */       rq.pred((Entry<K>)null);
/*      */       while (true) {
/* 1747 */         if (!p.pred()) {
/* 1748 */           Entry<K> e = p.left.clone();
/* 1749 */           e.pred(q.left);
/* 1750 */           e.succ(q);
/* 1751 */           q.left(e);
/* 1752 */           p = p.left;
/* 1753 */           q = q.left;
/*      */         } else {
/* 1755 */           while (p.succ()) {
/* 1756 */             p = p.right;
/* 1757 */             if (p == null) {
/* 1758 */               q.right = null;
/* 1759 */               c.tree = rq.left;
/* 1760 */               c.firstEntry = c.tree;
/* 1761 */               while (c.firstEntry.left != null)
/* 1762 */                 c.firstEntry = c.firstEntry.left; 
/* 1763 */               c.lastEntry = c.tree;
/* 1764 */               while (c.lastEntry.right != null)
/* 1765 */                 c.lastEntry = c.lastEntry.right; 
/* 1766 */               return c;
/*      */             } 
/* 1768 */             q = q.right;
/*      */           } 
/* 1770 */           p = p.right;
/* 1771 */           q = q.right;
/*      */         } 
/* 1773 */         if (!p.succ()) {
/* 1774 */           Entry<K> e = p.right.clone();
/* 1775 */           e.succ(q.right);
/* 1776 */           e.pred(q);
/* 1777 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1781 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1784 */     int n = this.count;
/* 1785 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1787 */     s.defaultWriteObject();
/* 1788 */     while (n-- != 0) {
/* 1789 */       Entry<K> e = i.nextEntry();
/* 1790 */       s.writeObject(e.key);
/* 1791 */       s.writeBoolean(e.value);
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
/* 1812 */     if (n == 1) {
/* 1813 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readBoolean());
/* 1814 */       entry.pred(pred);
/* 1815 */       entry.succ(succ);
/* 1816 */       entry.black(true);
/* 1817 */       return entry;
/*      */     } 
/* 1819 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1824 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readBoolean());
/* 1825 */       entry.black(true);
/* 1826 */       entry.right(new Entry<>((K)s.readObject(), s.readBoolean()));
/* 1827 */       entry.right.pred(entry);
/* 1828 */       entry.pred(pred);
/* 1829 */       entry.right.succ(succ);
/* 1830 */       return entry;
/*      */     } 
/*      */     
/* 1833 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1834 */     Entry<K> top = new Entry<>();
/* 1835 */     top.left(readTree(s, leftN, pred, top));
/* 1836 */     top.key = (K)s.readObject();
/* 1837 */     top.value = s.readBoolean();
/* 1838 */     top.black(true);
/* 1839 */     top.right(readTree(s, rightN, top, succ));
/* 1840 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1841 */       top.right.black(false); 
/* 1842 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1845 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1850 */     setActualComparator();
/* 1851 */     allocatePaths();
/* 1852 */     if (this.count != 0) {
/* 1853 */       this.tree = readTree(s, this.count, (Entry<K>)null, (Entry<K>)null);
/*      */       
/* 1855 */       Entry<K> e = this.tree;
/* 1856 */       while (e.left() != null)
/* 1857 */         e = e.left(); 
/* 1858 */       this.firstEntry = e;
/* 1859 */       e = this.tree;
/* 1860 */       while (e.right() != null)
/* 1861 */         e = e.right(); 
/* 1862 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2BooleanRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */