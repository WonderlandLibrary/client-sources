/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharListIterator;
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
/*      */ public class Object2CharRBTreeMap<K>
/*      */   extends AbstractObject2CharSortedMap<K>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<K> tree;
/*      */   protected int count;
/*      */   protected transient Entry<K> firstEntry;
/*      */   protected transient Entry<K> lastEntry;
/*      */   protected transient ObjectSortedSet<Object2CharMap.Entry<K>> entries;
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */   protected transient CharCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super K> storedComparator;
/*      */   protected transient Comparator<? super K> actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry<K>[] nodePath;
/*      */   
/*      */   public Object2CharRBTreeMap() {
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
/*      */   public Object2CharRBTreeMap(Comparator<? super K> c) {
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
/*      */   public Object2CharRBTreeMap(Map<? extends K, ? extends Character> m) {
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
/*      */   public Object2CharRBTreeMap(SortedMap<K, Character> m) {
/*  125 */     this(m.comparator());
/*  126 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2CharRBTreeMap(Object2CharMap<? extends K> m) {
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
/*      */   public Object2CharRBTreeMap(Object2CharSortedMap<K> m) {
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
/*      */   public Object2CharRBTreeMap(K[] k, char[] v, Comparator<? super K> c) {
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
/*      */   public Object2CharRBTreeMap(K[] k, char[] v) {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char addTo(K k, char incr) {
/*  273 */     Entry<K> e = add(k);
/*  274 */     char oldValue = e.value;
/*  275 */     e.value = (char)(e.value + incr);
/*  276 */     return oldValue;
/*      */   }
/*      */   
/*      */   public char put(K k, char v) {
/*  280 */     Entry<K> e = add(k);
/*  281 */     char oldValue = e.value;
/*  282 */     e.value = v;
/*  283 */     return oldValue;
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
/*  300 */     this.modified = false;
/*  301 */     int maxDepth = 0;
/*      */     
/*  303 */     if (this.tree == null) {
/*  304 */       this.count++;
/*  305 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*      */     } else {
/*  307 */       Entry<K> p = this.tree;
/*  308 */       int i = 0; while (true) {
/*      */         int cmp;
/*  310 */         if ((cmp = compare(k, p.key)) == 0) {
/*      */           
/*  312 */           while (i-- != 0)
/*  313 */             this.nodePath[i] = null; 
/*  314 */           return p;
/*      */         } 
/*  316 */         this.nodePath[i] = p;
/*  317 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  318 */           if (p.succ()) {
/*  319 */             this.count++;
/*  320 */             e = new Entry<>(k, this.defRetValue);
/*  321 */             if (p.right == null)
/*  322 */               this.lastEntry = e; 
/*  323 */             e.left = p;
/*  324 */             e.right = p.right;
/*  325 */             p.right(e);
/*      */             break;
/*      */           } 
/*  328 */           p = p.right; continue;
/*      */         } 
/*  330 */         if (p.pred()) {
/*  331 */           this.count++;
/*  332 */           e = new Entry<>(k, this.defRetValue);
/*  333 */           if (p.left == null)
/*  334 */             this.firstEntry = e; 
/*  335 */           e.right = p;
/*  336 */           e.left = p.left;
/*  337 */           p.left(e);
/*      */           break;
/*      */         } 
/*  340 */         p = p.left;
/*      */       } 
/*      */       
/*  343 */       this.modified = true;
/*  344 */       maxDepth = i--;
/*  345 */       while (i > 0 && !this.nodePath[i].black()) {
/*  346 */         if (!this.dirPath[i - 1]) {
/*  347 */           Entry<K> entry1 = (this.nodePath[i - 1]).right;
/*  348 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  349 */             this.nodePath[i].black(true);
/*  350 */             entry1.black(true);
/*  351 */             this.nodePath[i - 1].black(false);
/*  352 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  355 */           if (!this.dirPath[i]) {
/*  356 */             entry1 = this.nodePath[i];
/*      */           } else {
/*  358 */             Entry<K> entry = this.nodePath[i];
/*  359 */             entry1 = entry.right;
/*  360 */             entry.right = entry1.left;
/*  361 */             entry1.left = entry;
/*  362 */             (this.nodePath[i - 1]).left = entry1;
/*  363 */             if (entry1.pred()) {
/*  364 */               entry1.pred(false);
/*  365 */               entry.succ(entry1);
/*      */             } 
/*      */           } 
/*  368 */           Entry<K> entry2 = this.nodePath[i - 1];
/*  369 */           entry2.black(false);
/*  370 */           entry1.black(true);
/*  371 */           entry2.left = entry1.right;
/*  372 */           entry1.right = entry2;
/*  373 */           if (i < 2) {
/*  374 */             this.tree = entry1;
/*      */           }
/*  376 */           else if (this.dirPath[i - 2]) {
/*  377 */             (this.nodePath[i - 2]).right = entry1;
/*      */           } else {
/*  379 */             (this.nodePath[i - 2]).left = entry1;
/*      */           } 
/*  381 */           if (entry1.succ()) {
/*  382 */             entry1.succ(false);
/*  383 */             entry2.pred(entry1);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  388 */         Entry<K> y = (this.nodePath[i - 1]).left;
/*  389 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  390 */           this.nodePath[i].black(true);
/*  391 */           y.black(true);
/*  392 */           this.nodePath[i - 1].black(false);
/*  393 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  396 */         if (this.dirPath[i]) {
/*  397 */           y = this.nodePath[i];
/*      */         } else {
/*  399 */           Entry<K> entry = this.nodePath[i];
/*  400 */           y = entry.left;
/*  401 */           entry.left = y.right;
/*  402 */           y.right = entry;
/*  403 */           (this.nodePath[i - 1]).right = y;
/*  404 */           if (y.succ()) {
/*  405 */             y.succ(false);
/*  406 */             entry.pred(y);
/*      */           } 
/*      */         } 
/*  409 */         Entry<K> x = this.nodePath[i - 1];
/*  410 */         x.black(false);
/*  411 */         y.black(true);
/*  412 */         x.right = y.left;
/*  413 */         y.left = x;
/*  414 */         if (i < 2) {
/*  415 */           this.tree = y;
/*      */         }
/*  417 */         else if (this.dirPath[i - 2]) {
/*  418 */           (this.nodePath[i - 2]).right = y;
/*      */         } else {
/*  420 */           (this.nodePath[i - 2]).left = y;
/*      */         } 
/*  422 */         if (y.pred()) {
/*  423 */           y.pred(false);
/*  424 */           x.succ(y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  431 */     this.tree.black(true);
/*      */     
/*  433 */     while (maxDepth-- != 0)
/*  434 */       this.nodePath[maxDepth] = null; 
/*  435 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char removeChar(Object k) {
/*  444 */     this.modified = false;
/*  445 */     if (this.tree == null)
/*  446 */       return this.defRetValue; 
/*  447 */     Entry<K> p = this.tree;
/*      */     
/*  449 */     int i = 0;
/*  450 */     K kk = (K)k;
/*      */     int cmp;
/*  452 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  454 */       this.dirPath[i] = (cmp > 0);
/*  455 */       this.nodePath[i] = p;
/*  456 */       if (this.dirPath[i++]) {
/*  457 */         if ((p = p.right()) == null) {
/*      */           
/*  459 */           while (i-- != 0)
/*  460 */             this.nodePath[i] = null; 
/*  461 */           return this.defRetValue;
/*      */         }  continue;
/*      */       } 
/*  464 */       if ((p = p.left()) == null) {
/*      */         
/*  466 */         while (i-- != 0)
/*  467 */           this.nodePath[i] = null; 
/*  468 */         return this.defRetValue;
/*      */       } 
/*      */     } 
/*      */     
/*  472 */     if (p.left == null)
/*  473 */       this.firstEntry = p.next(); 
/*  474 */     if (p.right == null)
/*  475 */       this.lastEntry = p.prev(); 
/*  476 */     if (p.succ()) {
/*  477 */       if (p.pred()) {
/*  478 */         if (i == 0) {
/*  479 */           this.tree = p.left;
/*      */         }
/*  481 */         else if (this.dirPath[i - 1]) {
/*  482 */           this.nodePath[i - 1].succ(p.right);
/*      */         } else {
/*  484 */           this.nodePath[i - 1].pred(p.left);
/*      */         } 
/*      */       } else {
/*  487 */         (p.prev()).right = p.right;
/*  488 */         if (i == 0) {
/*  489 */           this.tree = p.left;
/*      */         }
/*  491 */         else if (this.dirPath[i - 1]) {
/*  492 */           (this.nodePath[i - 1]).right = p.left;
/*      */         } else {
/*  494 */           (this.nodePath[i - 1]).left = p.left;
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  499 */       Entry<K> r = p.right;
/*  500 */       if (r.pred()) {
/*  501 */         r.left = p.left;
/*  502 */         r.pred(p.pred());
/*  503 */         if (!r.pred())
/*  504 */           (r.prev()).right = r; 
/*  505 */         if (i == 0) {
/*  506 */           this.tree = r;
/*      */         }
/*  508 */         else if (this.dirPath[i - 1]) {
/*  509 */           (this.nodePath[i - 1]).right = r;
/*      */         } else {
/*  511 */           (this.nodePath[i - 1]).left = r;
/*      */         } 
/*  513 */         boolean color = r.black();
/*  514 */         r.black(p.black());
/*  515 */         p.black(color);
/*  516 */         this.dirPath[i] = true;
/*  517 */         this.nodePath[i++] = r;
/*      */       } else {
/*      */         Entry<K> s;
/*  520 */         int j = i++;
/*      */         while (true) {
/*  522 */           this.dirPath[i] = false;
/*  523 */           this.nodePath[i++] = r;
/*  524 */           s = r.left;
/*  525 */           if (s.pred())
/*      */             break; 
/*  527 */           r = s;
/*      */         } 
/*  529 */         this.dirPath[j] = true;
/*  530 */         this.nodePath[j] = s;
/*  531 */         if (s.succ()) {
/*  532 */           r.pred(s);
/*      */         } else {
/*  534 */           r.left = s.right;
/*  535 */         }  s.left = p.left;
/*  536 */         if (!p.pred()) {
/*  537 */           (p.prev()).right = s;
/*  538 */           s.pred(false);
/*      */         } 
/*  540 */         s.right(p.right);
/*  541 */         boolean color = s.black();
/*  542 */         s.black(p.black());
/*  543 */         p.black(color);
/*  544 */         if (j == 0) {
/*  545 */           this.tree = s;
/*      */         }
/*  547 */         else if (this.dirPath[j - 1]) {
/*  548 */           (this.nodePath[j - 1]).right = s;
/*      */         } else {
/*  550 */           (this.nodePath[j - 1]).left = s;
/*      */         } 
/*      */       } 
/*      */     } 
/*  554 */     int maxDepth = i;
/*  555 */     if (p.black()) {
/*  556 */       for (; i > 0; i--) {
/*  557 */         if ((this.dirPath[i - 1] && !this.nodePath[i - 1].succ()) || (!this.dirPath[i - 1] && !this.nodePath[i - 1].pred())) {
/*  558 */           Entry<K> x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  559 */           if (!x.black()) {
/*  560 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  564 */         if (!this.dirPath[i - 1]) {
/*  565 */           Entry<K> w = (this.nodePath[i - 1]).right;
/*  566 */           if (!w.black()) {
/*  567 */             w.black(true);
/*  568 */             this.nodePath[i - 1].black(false);
/*  569 */             (this.nodePath[i - 1]).right = w.left;
/*  570 */             w.left = this.nodePath[i - 1];
/*  571 */             if (i < 2) {
/*  572 */               this.tree = w;
/*      */             }
/*  574 */             else if (this.dirPath[i - 2]) {
/*  575 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  577 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  579 */             this.nodePath[i] = this.nodePath[i - 1];
/*  580 */             this.dirPath[i] = false;
/*  581 */             this.nodePath[i - 1] = w;
/*  582 */             if (maxDepth == i++)
/*  583 */               maxDepth++; 
/*  584 */             w = (this.nodePath[i - 1]).right;
/*      */           } 
/*  586 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  587 */             w.black(false);
/*      */           } else {
/*  589 */             if (w.succ() || w.right.black()) {
/*  590 */               Entry<K> y = w.left;
/*  591 */               y.black(true);
/*  592 */               w.black(false);
/*  593 */               w.left = y.right;
/*  594 */               y.right = w;
/*  595 */               w = (this.nodePath[i - 1]).right = y;
/*  596 */               if (w.succ()) {
/*  597 */                 w.succ(false);
/*  598 */                 w.right.pred(w);
/*      */               } 
/*      */             } 
/*  601 */             w.black(this.nodePath[i - 1].black());
/*  602 */             this.nodePath[i - 1].black(true);
/*  603 */             w.right.black(true);
/*  604 */             (this.nodePath[i - 1]).right = w.left;
/*  605 */             w.left = this.nodePath[i - 1];
/*  606 */             if (i < 2) {
/*  607 */               this.tree = w;
/*      */             }
/*  609 */             else if (this.dirPath[i - 2]) {
/*  610 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  612 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  614 */             if (w.pred()) {
/*  615 */               w.pred(false);
/*  616 */               this.nodePath[i - 1].succ(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } else {
/*  621 */           Entry<K> w = (this.nodePath[i - 1]).left;
/*  622 */           if (!w.black()) {
/*  623 */             w.black(true);
/*  624 */             this.nodePath[i - 1].black(false);
/*  625 */             (this.nodePath[i - 1]).left = w.right;
/*  626 */             w.right = this.nodePath[i - 1];
/*  627 */             if (i < 2) {
/*  628 */               this.tree = w;
/*      */             }
/*  630 */             else if (this.dirPath[i - 2]) {
/*  631 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  633 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  635 */             this.nodePath[i] = this.nodePath[i - 1];
/*  636 */             this.dirPath[i] = true;
/*  637 */             this.nodePath[i - 1] = w;
/*  638 */             if (maxDepth == i++)
/*  639 */               maxDepth++; 
/*  640 */             w = (this.nodePath[i - 1]).left;
/*      */           } 
/*  642 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  643 */             w.black(false);
/*      */           } else {
/*  645 */             if (w.pred() || w.left.black()) {
/*  646 */               Entry<K> y = w.right;
/*  647 */               y.black(true);
/*  648 */               w.black(false);
/*  649 */               w.right = y.left;
/*  650 */               y.left = w;
/*  651 */               w = (this.nodePath[i - 1]).left = y;
/*  652 */               if (w.pred()) {
/*  653 */                 w.pred(false);
/*  654 */                 w.left.succ(w);
/*      */               } 
/*      */             } 
/*  657 */             w.black(this.nodePath[i - 1].black());
/*  658 */             this.nodePath[i - 1].black(true);
/*  659 */             w.left.black(true);
/*  660 */             (this.nodePath[i - 1]).left = w.right;
/*  661 */             w.right = this.nodePath[i - 1];
/*  662 */             if (i < 2) {
/*  663 */               this.tree = w;
/*      */             }
/*  665 */             else if (this.dirPath[i - 2]) {
/*  666 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  668 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  670 */             if (w.succ()) {
/*  671 */               w.succ(false);
/*  672 */               this.nodePath[i - 1].pred(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  678 */       if (this.tree != null)
/*  679 */         this.tree.black(true); 
/*      */     } 
/*  681 */     this.modified = true;
/*  682 */     this.count--;
/*      */     
/*  684 */     while (maxDepth-- != 0)
/*  685 */       this.nodePath[maxDepth] = null; 
/*  686 */     return p.value;
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  690 */     ValueIterator i = new ValueIterator();
/*      */     
/*  692 */     int j = this.count;
/*  693 */     while (j-- != 0) {
/*  694 */       char ev = i.nextChar();
/*  695 */       if (ev == v)
/*  696 */         return true; 
/*      */     } 
/*  698 */     return false;
/*      */   }
/*      */   
/*      */   public void clear() {
/*  702 */     this.count = 0;
/*  703 */     this.tree = null;
/*  704 */     this.entries = null;
/*  705 */     this.values = null;
/*  706 */     this.keys = null;
/*  707 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<K>
/*      */     extends AbstractObject2CharMap.BasicEntry<K>
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
/*  735 */       super((K)null, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(K k, char v) {
/*  746 */       super(k, v);
/*  747 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> left() {
/*  755 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> right() {
/*  763 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  771 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  779 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  788 */       if (pred) {
/*  789 */         this.info |= 0x40000000;
/*      */       } else {
/*  791 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  800 */       if (succ) {
/*  801 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  803 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<K> pred) {
/*  812 */       this.info |= 0x40000000;
/*  813 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<K> succ) {
/*  822 */       this.info |= Integer.MIN_VALUE;
/*  823 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<K> left) {
/*  832 */       this.info &= 0xBFFFFFFF;
/*  833 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<K> right) {
/*  842 */       this.info &= Integer.MAX_VALUE;
/*  843 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean black() {
/*  851 */       return ((this.info & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void black(boolean black) {
/*  860 */       if (black) {
/*  861 */         this.info |= 0x1;
/*      */       } else {
/*  863 */         this.info &= 0xFFFFFFFE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> next() {
/*  871 */       Entry<K> next = this.right;
/*  872 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  873 */         while ((next.info & 0x40000000) == 0)
/*  874 */           next = next.left;  
/*  875 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> prev() {
/*  883 */       Entry<K> prev = this.left;
/*  884 */       if ((this.info & 0x40000000) == 0)
/*  885 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  886 */           prev = prev.right;  
/*  887 */       return prev;
/*      */     }
/*      */     
/*      */     public char setValue(char value) {
/*  891 */       char oldValue = this.value;
/*  892 */       this.value = value;
/*  893 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry<K> clone() {
/*      */       Entry<K> c;
/*      */       try {
/*  900 */         c = (Entry<K>)super.clone();
/*  901 */       } catch (CloneNotSupportedException cantHappen) {
/*  902 */         throw new InternalError();
/*      */       } 
/*  904 */       c.key = this.key;
/*  905 */       c.value = this.value;
/*  906 */       c.info = this.info;
/*  907 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  912 */       if (!(o instanceof Map.Entry))
/*  913 */         return false; 
/*  914 */       Map.Entry<K, Character> e = (Map.Entry<K, Character>)o;
/*  915 */       return (Objects.equals(this.key, e.getKey()) && this.value == ((Character)e.getValue()).charValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  919 */       return this.key.hashCode() ^ this.value;
/*      */     }
/*      */     
/*      */     public String toString() {
/*  923 */       return (new StringBuilder()).append(this.key).append("=>").append(this.value).toString();
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
/*  944 */     return (findKey((K)k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  948 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  952 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public char getChar(Object k) {
/*  957 */     Entry<K> e = findKey((K)k);
/*  958 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public K firstKey() {
/*  962 */     if (this.tree == null)
/*  963 */       throw new NoSuchElementException(); 
/*  964 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public K lastKey() {
/*  968 */     if (this.tree == null)
/*  969 */       throw new NoSuchElementException(); 
/*  970 */     return this.lastEntry.key;
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
/*      */     Object2CharRBTreeMap.Entry<K> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2CharRBTreeMap.Entry<K> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2CharRBTreeMap.Entry<K> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1001 */     int index = 0;
/*      */     TreeIterator() {
/* 1003 */       this.next = Object2CharRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(K k) {
/* 1006 */       if ((this.next = Object2CharRBTreeMap.this.locateKey(k)) != null)
/* 1007 */         if (Object2CharRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1008 */           this.prev = this.next;
/* 1009 */           this.next = this.next.next();
/*      */         } else {
/* 1011 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/* 1015 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1018 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1021 */       this.next = this.next.next();
/*      */     }
/*      */     Object2CharRBTreeMap.Entry<K> nextEntry() {
/* 1024 */       if (!hasNext())
/* 1025 */         throw new NoSuchElementException(); 
/* 1026 */       this.curr = this.prev = this.next;
/* 1027 */       this.index++;
/* 1028 */       updateNext();
/* 1029 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1032 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Object2CharRBTreeMap.Entry<K> previousEntry() {
/* 1035 */       if (!hasPrevious())
/* 1036 */         throw new NoSuchElementException(); 
/* 1037 */       this.curr = this.next = this.prev;
/* 1038 */       this.index--;
/* 1039 */       updatePrevious();
/* 1040 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1043 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1046 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1049 */       if (this.curr == null) {
/* 1050 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1055 */       if (this.curr == this.prev)
/* 1056 */         this.index--; 
/* 1057 */       this.next = this.prev = this.curr;
/* 1058 */       updatePrevious();
/* 1059 */       updateNext();
/* 1060 */       Object2CharRBTreeMap.this.removeChar(this.curr.key);
/* 1061 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1064 */       int i = n;
/* 1065 */       while (i-- != 0 && hasNext())
/* 1066 */         nextEntry(); 
/* 1067 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1070 */       int i = n;
/* 1071 */       while (i-- != 0 && hasPrevious())
/* 1072 */         previousEntry(); 
/* 1073 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Object2CharMap.Entry<K>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(K k) {
/* 1086 */       super(k);
/*      */     }
/*      */     
/*      */     public Object2CharMap.Entry<K> next() {
/* 1090 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Object2CharMap.Entry<K> previous() {
/* 1094 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Object2CharMap.Entry<K>> object2CharEntrySet() {
/* 1099 */     if (this.entries == null)
/* 1100 */       this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2CharMap.Entry<Object2CharMap.Entry<K>>>()
/*      */         {
/*      */           final Comparator<? super Object2CharMap.Entry<K>> comparator;
/*      */           
/*      */           public Comparator<? super Object2CharMap.Entry<K>> comparator() {
/* 1105 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2CharMap.Entry<K>> iterator() {
/* 1109 */             return new Object2CharRBTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2CharMap.Entry<K>> iterator(Object2CharMap.Entry<K> from) {
/* 1114 */             return new Object2CharRBTreeMap.EntryIterator(from.getKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1119 */             if (!(o instanceof Map.Entry))
/* 1120 */               return false; 
/* 1121 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1122 */             if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 1123 */               return false; 
/* 1124 */             Object2CharRBTreeMap.Entry<K> f = Object2CharRBTreeMap.this.findKey((K)e.getKey());
/* 1125 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1130 */             if (!(o instanceof Map.Entry))
/* 1131 */               return false; 
/* 1132 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1133 */             if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 1134 */               return false; 
/* 1135 */             Object2CharRBTreeMap.Entry<K> f = Object2CharRBTreeMap.this.findKey((K)e.getKey());
/* 1136 */             if (f == null || f.getCharValue() != ((Character)e.getValue()).charValue())
/* 1137 */               return false; 
/* 1138 */             Object2CharRBTreeMap.this.removeChar(f.key);
/* 1139 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1143 */             return Object2CharRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1147 */             Object2CharRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Object2CharMap.Entry<K> first() {
/* 1151 */             return Object2CharRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Object2CharMap.Entry<K> last() {
/* 1155 */             return Object2CharRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2CharMap.Entry<K>> subSet(Object2CharMap.Entry<K> from, Object2CharMap.Entry<K> to) {
/* 1160 */             return Object2CharRBTreeMap.this.subMap(from.getKey(), to.getKey()).object2CharEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2CharMap.Entry<K>> headSet(Object2CharMap.Entry<K> to) {
/* 1164 */             return Object2CharRBTreeMap.this.headMap(to.getKey()).object2CharEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2CharMap.Entry<K>> tailSet(Object2CharMap.Entry<K> from) {
/* 1168 */             return Object2CharRBTreeMap.this.tailMap(from.getKey()).object2CharEntrySet();
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
/*      */     implements ObjectListIterator<K>
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(K k) {
/* 1187 */       super(k);
/*      */     }
/*      */     
/*      */     public K next() {
/* 1191 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1195 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractObject2CharSortedMap<K>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1202 */       return new Object2CharRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1206 */       return new Object2CharRBTreeMap.KeyIterator(from);
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
/*      */     implements CharListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1238 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public char previousChar() {
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
/*      */   public CharCollection values() {
/* 1257 */     if (this.values == null)
/* 1258 */       this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/* 1261 */             return (CharIterator)new Object2CharRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(char k) {
/* 1265 */             return Object2CharRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1269 */             return Object2CharRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1273 */             Object2CharRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1276 */     return this.values;
/*      */   }
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1280 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Object2CharSortedMap<K> headMap(K to) {
/* 1284 */     return new Submap(null, true, to, false);
/*      */   }
/*      */   
/*      */   public Object2CharSortedMap<K> tailMap(K from) {
/* 1288 */     return new Submap(from, false, null, true);
/*      */   }
/*      */   
/*      */   public Object2CharSortedMap<K> subMap(K from, K to) {
/* 1292 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractObject2CharSortedMap<K>
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
/*      */     protected transient ObjectSortedSet<Object2CharMap.Entry<K>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient CharCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(K from, boolean bottom, K to, boolean top) {
/* 1336 */       if (!bottom && !top && Object2CharRBTreeMap.this.compare(from, to) > 0)
/* 1337 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1338 */       this.from = from;
/* 1339 */       this.bottom = bottom;
/* 1340 */       this.to = to;
/* 1341 */       this.top = top;
/* 1342 */       this.defRetValue = Object2CharRBTreeMap.this.defRetValue;
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
/*      */     final boolean in(K k) {
/* 1360 */       return ((this.bottom || Object2CharRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Object2CharRBTreeMap.this
/* 1361 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2CharMap.Entry<K>> object2CharEntrySet() {
/* 1365 */       if (this.entries == null)
/* 1366 */         this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2CharMap.Entry<Object2CharMap.Entry<K>>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Object2CharMap.Entry<K>> iterator() {
/* 1369 */               return new Object2CharRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Object2CharMap.Entry<K>> iterator(Object2CharMap.Entry<K> from) {
/* 1374 */               return new Object2CharRBTreeMap.Submap.SubmapEntryIterator(from.getKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Object2CharMap.Entry<K>> comparator() {
/* 1378 */               return Object2CharRBTreeMap.this.object2CharEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1383 */               if (!(o instanceof Map.Entry))
/* 1384 */                 return false; 
/* 1385 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1386 */               if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 1387 */                 return false; 
/* 1388 */               Object2CharRBTreeMap.Entry<K> f = Object2CharRBTreeMap.this.findKey((K)e.getKey());
/* 1389 */               return (f != null && Object2CharRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1394 */               if (!(o instanceof Map.Entry))
/* 1395 */                 return false; 
/* 1396 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1397 */               if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 1398 */                 return false; 
/* 1399 */               Object2CharRBTreeMap.Entry<K> f = Object2CharRBTreeMap.this.findKey((K)e.getKey());
/* 1400 */               if (f != null && Object2CharRBTreeMap.Submap.this.in(f.key))
/* 1401 */                 Object2CharRBTreeMap.Submap.this.removeChar(f.key); 
/* 1402 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1406 */               int c = 0;
/* 1407 */               for (Iterator<?> i = iterator(); i.hasNext(); i.next())
/* 1408 */                 c++; 
/* 1409 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1413 */               return !(new Object2CharRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1417 */               Object2CharRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Object2CharMap.Entry<K> first() {
/* 1421 */               return Object2CharRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Object2CharMap.Entry<K> last() {
/* 1425 */               return Object2CharRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2CharMap.Entry<K>> subSet(Object2CharMap.Entry<K> from, Object2CharMap.Entry<K> to) {
/* 1430 */               return Object2CharRBTreeMap.Submap.this.subMap(from.getKey(), to.getKey()).object2CharEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2CharMap.Entry<K>> headSet(Object2CharMap.Entry<K> to) {
/* 1434 */               return Object2CharRBTreeMap.Submap.this.headMap(to.getKey()).object2CharEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2CharMap.Entry<K>> tailSet(Object2CharMap.Entry<K> from) {
/* 1438 */               return Object2CharRBTreeMap.Submap.this.tailMap(from.getKey()).object2CharEntrySet();
/*      */             }
/*      */           }; 
/* 1441 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractObject2CharSortedMap<K>.KeySet {
/*      */       public ObjectBidirectionalIterator<K> iterator() {
/* 1446 */         return new Object2CharRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1450 */         return new Object2CharRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> keySet() {
/* 1455 */       if (this.keys == null)
/* 1456 */         this.keys = new KeySet(); 
/* 1457 */       return this.keys;
/*      */     }
/*      */     
/*      */     public CharCollection values() {
/* 1461 */       if (this.values == null)
/* 1462 */         this.values = (CharCollection)new AbstractCharCollection()
/*      */           {
/*      */             public CharIterator iterator() {
/* 1465 */               return (CharIterator)new Object2CharRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(char k) {
/* 1469 */               return Object2CharRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1473 */               return Object2CharRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1477 */               Object2CharRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1480 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object k) {
/* 1485 */       return (in((K)k) && Object2CharRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(char v) {
/* 1489 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1491 */       while (i.hasNext()) {
/* 1492 */         char ev = (i.nextEntry()).value;
/* 1493 */         if (ev == v)
/* 1494 */           return true; 
/*      */       } 
/* 1496 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public char getChar(Object k) {
/* 1502 */       K kk = (K)k; Object2CharRBTreeMap.Entry<K> e;
/* 1503 */       return (in(kk) && (e = Object2CharRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public char put(K k, char v) {
/* 1507 */       Object2CharRBTreeMap.this.modified = false;
/* 1508 */       if (!in(k))
/* 1509 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1510 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1511 */       char oldValue = Object2CharRBTreeMap.this.put(k, v);
/* 1512 */       return Object2CharRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public char removeChar(Object k) {
/* 1517 */       Object2CharRBTreeMap.this.modified = false;
/* 1518 */       if (!in((K)k))
/* 1519 */         return this.defRetValue; 
/* 1520 */       char oldValue = Object2CharRBTreeMap.this.removeChar(k);
/* 1521 */       return Object2CharRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1525 */       SubmapIterator i = new SubmapIterator();
/* 1526 */       int n = 0;
/* 1527 */       while (i.hasNext()) {
/* 1528 */         n++;
/* 1529 */         i.nextEntry();
/*      */       } 
/* 1531 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1535 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1539 */       return Object2CharRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Object2CharSortedMap<K> headMap(K to) {
/* 1543 */       if (this.top)
/* 1544 */         return new Submap(this.from, this.bottom, to, false); 
/* 1545 */       return (Object2CharRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Object2CharSortedMap<K> tailMap(K from) {
/* 1549 */       if (this.bottom)
/* 1550 */         return new Submap(from, false, this.to, this.top); 
/* 1551 */       return (Object2CharRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Object2CharSortedMap<K> subMap(K from, K to) {
/* 1555 */       if (this.top && this.bottom)
/* 1556 */         return new Submap(from, false, to, false); 
/* 1557 */       if (!this.top)
/* 1558 */         to = (Object2CharRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1559 */       if (!this.bottom)
/* 1560 */         from = (Object2CharRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1561 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1562 */         return this; 
/* 1563 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2CharRBTreeMap.Entry<K> firstEntry() {
/*      */       Object2CharRBTreeMap.Entry<K> e;
/* 1572 */       if (Object2CharRBTreeMap.this.tree == null) {
/* 1573 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1577 */       if (this.bottom) {
/* 1578 */         e = Object2CharRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1580 */         e = Object2CharRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1582 */         if (Object2CharRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1583 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1587 */       if (e == null || (!this.top && Object2CharRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1588 */         return null; 
/* 1589 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2CharRBTreeMap.Entry<K> lastEntry() {
/*      */       Object2CharRBTreeMap.Entry<K> e;
/* 1598 */       if (Object2CharRBTreeMap.this.tree == null) {
/* 1599 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1603 */       if (this.top) {
/* 1604 */         e = Object2CharRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1606 */         e = Object2CharRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1608 */         if (Object2CharRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1609 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1613 */       if (e == null || (!this.bottom && Object2CharRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1614 */         return null; 
/* 1615 */       return e;
/*      */     }
/*      */     
/*      */     public K firstKey() {
/* 1619 */       Object2CharRBTreeMap.Entry<K> e = firstEntry();
/* 1620 */       if (e == null)
/* 1621 */         throw new NoSuchElementException(); 
/* 1622 */       return e.key;
/*      */     }
/*      */     
/*      */     public K lastKey() {
/* 1626 */       Object2CharRBTreeMap.Entry<K> e = lastEntry();
/* 1627 */       if (e == null)
/* 1628 */         throw new NoSuchElementException(); 
/* 1629 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Object2CharRBTreeMap<K>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1642 */         this.next = Object2CharRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(K k) {
/* 1645 */         this();
/* 1646 */         if (this.next != null)
/* 1647 */           if (!Object2CharRBTreeMap.Submap.this.bottom && Object2CharRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1648 */             this.prev = null;
/* 1649 */           } else if (!Object2CharRBTreeMap.Submap.this.top && Object2CharRBTreeMap.this.compare(k, (this.prev = Object2CharRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1650 */             this.next = null;
/*      */           } else {
/* 1652 */             this.next = Object2CharRBTreeMap.this.locateKey(k);
/* 1653 */             if (Object2CharRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1654 */               this.prev = this.next;
/* 1655 */               this.next = this.next.next();
/*      */             } else {
/* 1657 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1663 */         this.prev = this.prev.prev();
/* 1664 */         if (!Object2CharRBTreeMap.Submap.this.bottom && this.prev != null && Object2CharRBTreeMap.this.compare(this.prev.key, Object2CharRBTreeMap.Submap.this.from) < 0)
/* 1665 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1669 */         this.next = this.next.next();
/* 1670 */         if (!Object2CharRBTreeMap.Submap.this.top && this.next != null && Object2CharRBTreeMap.this.compare(this.next.key, Object2CharRBTreeMap.Submap.this.to) >= 0)
/* 1671 */           this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Object2CharMap.Entry<K>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(K k) {
/* 1680 */         super(k);
/*      */       }
/*      */       
/*      */       public Object2CharMap.Entry<K> next() {
/* 1684 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Object2CharMap.Entry<K> previous() {
/* 1688 */         return previousEntry();
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
/* 1706 */         super(from);
/*      */       }
/*      */       
/*      */       public K next() {
/* 1710 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public K previous() {
/* 1714 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements CharListIterator
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public char nextChar() {
/* 1730 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public char previousChar() {
/* 1734 */         return (previousEntry()).value;
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
/*      */   public Object2CharRBTreeMap<K> clone() {
/*      */     Object2CharRBTreeMap<K> c;
/*      */     try {
/* 1753 */       c = (Object2CharRBTreeMap<K>)super.clone();
/* 1754 */     } catch (CloneNotSupportedException cantHappen) {
/* 1755 */       throw new InternalError();
/*      */     } 
/* 1757 */     c.keys = null;
/* 1758 */     c.values = null;
/* 1759 */     c.entries = null;
/* 1760 */     c.allocatePaths();
/* 1761 */     if (this.count != 0) {
/*      */       
/* 1763 */       Entry<K> rp = new Entry<>(), rq = new Entry<>();
/* 1764 */       Entry<K> p = rp;
/* 1765 */       rp.left(this.tree);
/* 1766 */       Entry<K> q = rq;
/* 1767 */       rq.pred((Entry<K>)null);
/*      */       while (true) {
/* 1769 */         if (!p.pred()) {
/* 1770 */           Entry<K> e = p.left.clone();
/* 1771 */           e.pred(q.left);
/* 1772 */           e.succ(q);
/* 1773 */           q.left(e);
/* 1774 */           p = p.left;
/* 1775 */           q = q.left;
/*      */         } else {
/* 1777 */           while (p.succ()) {
/* 1778 */             p = p.right;
/* 1779 */             if (p == null) {
/* 1780 */               q.right = null;
/* 1781 */               c.tree = rq.left;
/* 1782 */               c.firstEntry = c.tree;
/* 1783 */               while (c.firstEntry.left != null)
/* 1784 */                 c.firstEntry = c.firstEntry.left; 
/* 1785 */               c.lastEntry = c.tree;
/* 1786 */               while (c.lastEntry.right != null)
/* 1787 */                 c.lastEntry = c.lastEntry.right; 
/* 1788 */               return c;
/*      */             } 
/* 1790 */             q = q.right;
/*      */           } 
/* 1792 */           p = p.right;
/* 1793 */           q = q.right;
/*      */         } 
/* 1795 */         if (!p.succ()) {
/* 1796 */           Entry<K> e = p.right.clone();
/* 1797 */           e.succ(q.right);
/* 1798 */           e.pred(q);
/* 1799 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1803 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1806 */     int n = this.count;
/* 1807 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1809 */     s.defaultWriteObject();
/* 1810 */     while (n-- != 0) {
/* 1811 */       Entry<K> e = i.nextEntry();
/* 1812 */       s.writeObject(e.key);
/* 1813 */       s.writeChar(e.value);
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
/* 1834 */     if (n == 1) {
/* 1835 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readChar());
/* 1836 */       entry.pred(pred);
/* 1837 */       entry.succ(succ);
/* 1838 */       entry.black(true);
/* 1839 */       return entry;
/*      */     } 
/* 1841 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1846 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readChar());
/* 1847 */       entry.black(true);
/* 1848 */       entry.right(new Entry<>((K)s.readObject(), s.readChar()));
/* 1849 */       entry.right.pred(entry);
/* 1850 */       entry.pred(pred);
/* 1851 */       entry.right.succ(succ);
/* 1852 */       return entry;
/*      */     } 
/*      */     
/* 1855 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1856 */     Entry<K> top = new Entry<>();
/* 1857 */     top.left(readTree(s, leftN, pred, top));
/* 1858 */     top.key = (K)s.readObject();
/* 1859 */     top.value = s.readChar();
/* 1860 */     top.black(true);
/* 1861 */     top.right(readTree(s, rightN, top, succ));
/* 1862 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1863 */       top.right.black(false); 
/* 1864 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1867 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1872 */     setActualComparator();
/* 1873 */     allocatePaths();
/* 1874 */     if (this.count != 0) {
/* 1875 */       this.tree = readTree(s, this.count, (Entry<K>)null, (Entry<K>)null);
/*      */       
/* 1877 */       Entry<K> e = this.tree;
/* 1878 */       while (e.left() != null)
/* 1879 */         e = e.left(); 
/* 1880 */       this.firstEntry = e;
/* 1881 */       e = this.tree;
/* 1882 */       while (e.right() != null)
/* 1883 */         e = e.right(); 
/* 1884 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2CharRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */