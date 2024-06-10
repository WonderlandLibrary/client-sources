/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*      */ import it.unimi.dsi.fastutil.floats.FloatListIterator;
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
/*      */ public class Object2FloatRBTreeMap<K>
/*      */   extends AbstractObject2FloatSortedMap<K>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<K> tree;
/*      */   protected int count;
/*      */   protected transient Entry<K> firstEntry;
/*      */   protected transient Entry<K> lastEntry;
/*      */   protected transient ObjectSortedSet<Object2FloatMap.Entry<K>> entries;
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */   protected transient FloatCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super K> storedComparator;
/*      */   protected transient Comparator<? super K> actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry<K>[] nodePath;
/*      */   
/*      */   public Object2FloatRBTreeMap() {
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
/*      */   public Object2FloatRBTreeMap(Comparator<? super K> c) {
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
/*      */   public Object2FloatRBTreeMap(Map<? extends K, ? extends Float> m) {
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
/*      */   public Object2FloatRBTreeMap(SortedMap<K, Float> m) {
/*  125 */     this(m.comparator());
/*  126 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2FloatRBTreeMap(Object2FloatMap<? extends K> m) {
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
/*      */   public Object2FloatRBTreeMap(Object2FloatSortedMap<K> m) {
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
/*      */   public Object2FloatRBTreeMap(K[] k, float[] v, Comparator<? super K> c) {
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
/*      */   public Object2FloatRBTreeMap(K[] k, float[] v) {
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
/*      */   public float addTo(K k, float incr) {
/*  273 */     Entry<K> e = add(k);
/*  274 */     float oldValue = e.value;
/*  275 */     e.value += incr;
/*  276 */     return oldValue;
/*      */   }
/*      */   
/*      */   public float put(K k, float v) {
/*  280 */     Entry<K> e = add(k);
/*  281 */     float oldValue = e.value;
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
/*      */   public float removeFloat(Object k) {
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
/*      */   public boolean containsValue(float v) {
/*  690 */     ValueIterator i = new ValueIterator();
/*      */     
/*  692 */     int j = this.count;
/*  693 */     while (j-- != 0) {
/*  694 */       float ev = i.nextFloat();
/*  695 */       if (Float.floatToIntBits(ev) == Float.floatToIntBits(v))
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
/*      */     extends AbstractObject2FloatMap.BasicEntry<K>
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
/*  735 */       super((K)null, 0.0F);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(K k, float v) {
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
/*      */     public float setValue(float value) {
/*  891 */       float oldValue = this.value;
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
/*  914 */       Map.Entry<K, Float> e = (Map.Entry<K, Float>)o;
/*  915 */       return (Objects.equals(this.key, e.getKey()) && 
/*  916 */         Float.floatToIntBits(this.value) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  920 */       return this.key.hashCode() ^ HashCommon.float2int(this.value);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  924 */       return (new StringBuilder()).append(this.key).append("=>").append(this.value).toString();
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
/*  945 */     return (findKey((K)k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  949 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  953 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFloat(Object k) {
/*  958 */     Entry<K> e = findKey((K)k);
/*  959 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public K firstKey() {
/*  963 */     if (this.tree == null)
/*  964 */       throw new NoSuchElementException(); 
/*  965 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public K lastKey() {
/*  969 */     if (this.tree == null)
/*  970 */       throw new NoSuchElementException(); 
/*  971 */     return this.lastEntry.key;
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
/*      */     Object2FloatRBTreeMap.Entry<K> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2FloatRBTreeMap.Entry<K> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2FloatRBTreeMap.Entry<K> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1002 */     int index = 0;
/*      */     TreeIterator() {
/* 1004 */       this.next = Object2FloatRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(K k) {
/* 1007 */       if ((this.next = Object2FloatRBTreeMap.this.locateKey(k)) != null)
/* 1008 */         if (Object2FloatRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1009 */           this.prev = this.next;
/* 1010 */           this.next = this.next.next();
/*      */         } else {
/* 1012 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/* 1016 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1019 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1022 */       this.next = this.next.next();
/*      */     }
/*      */     Object2FloatRBTreeMap.Entry<K> nextEntry() {
/* 1025 */       if (!hasNext())
/* 1026 */         throw new NoSuchElementException(); 
/* 1027 */       this.curr = this.prev = this.next;
/* 1028 */       this.index++;
/* 1029 */       updateNext();
/* 1030 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1033 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Object2FloatRBTreeMap.Entry<K> previousEntry() {
/* 1036 */       if (!hasPrevious())
/* 1037 */         throw new NoSuchElementException(); 
/* 1038 */       this.curr = this.next = this.prev;
/* 1039 */       this.index--;
/* 1040 */       updatePrevious();
/* 1041 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1044 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1047 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1050 */       if (this.curr == null) {
/* 1051 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1056 */       if (this.curr == this.prev)
/* 1057 */         this.index--; 
/* 1058 */       this.next = this.prev = this.curr;
/* 1059 */       updatePrevious();
/* 1060 */       updateNext();
/* 1061 */       Object2FloatRBTreeMap.this.removeFloat(this.curr.key);
/* 1062 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1065 */       int i = n;
/* 1066 */       while (i-- != 0 && hasNext())
/* 1067 */         nextEntry(); 
/* 1068 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1071 */       int i = n;
/* 1072 */       while (i-- != 0 && hasPrevious())
/* 1073 */         previousEntry(); 
/* 1074 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Object2FloatMap.Entry<K>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(K k) {
/* 1087 */       super(k);
/*      */     }
/*      */     
/*      */     public Object2FloatMap.Entry<K> next() {
/* 1091 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Object2FloatMap.Entry<K> previous() {
/* 1095 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Object2FloatMap.Entry<K>> object2FloatEntrySet() {
/* 1100 */     if (this.entries == null)
/* 1101 */       this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2FloatMap.Entry<Object2FloatMap.Entry<K>>>()
/*      */         {
/*      */           final Comparator<? super Object2FloatMap.Entry<K>> comparator;
/*      */           
/*      */           public Comparator<? super Object2FloatMap.Entry<K>> comparator() {
/* 1106 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> iterator() {
/* 1110 */             return new Object2FloatRBTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> iterator(Object2FloatMap.Entry<K> from) {
/* 1115 */             return new Object2FloatRBTreeMap.EntryIterator(from.getKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1120 */             if (!(o instanceof Map.Entry))
/* 1121 */               return false; 
/* 1122 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1123 */             if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1124 */               return false; 
/* 1125 */             Object2FloatRBTreeMap.Entry<K> f = Object2FloatRBTreeMap.this.findKey((K)e.getKey());
/* 1126 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1131 */             if (!(o instanceof Map.Entry))
/* 1132 */               return false; 
/* 1133 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1134 */             if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1135 */               return false; 
/* 1136 */             Object2FloatRBTreeMap.Entry<K> f = Object2FloatRBTreeMap.this.findKey((K)e.getKey());
/* 1137 */             if (f == null || Float.floatToIntBits(f.getFloatValue()) != 
/* 1138 */               Float.floatToIntBits(((Float)e.getValue()).floatValue()))
/* 1139 */               return false; 
/* 1140 */             Object2FloatRBTreeMap.this.removeFloat(f.key);
/* 1141 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1145 */             return Object2FloatRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1149 */             Object2FloatRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Object2FloatMap.Entry<K> first() {
/* 1153 */             return Object2FloatRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Object2FloatMap.Entry<K> last() {
/* 1157 */             return Object2FloatRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2FloatMap.Entry<K>> subSet(Object2FloatMap.Entry<K> from, Object2FloatMap.Entry<K> to) {
/* 1162 */             return Object2FloatRBTreeMap.this.subMap(from.getKey(), to.getKey()).object2FloatEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2FloatMap.Entry<K>> headSet(Object2FloatMap.Entry<K> to) {
/* 1166 */             return Object2FloatRBTreeMap.this.headMap(to.getKey()).object2FloatEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2FloatMap.Entry<K>> tailSet(Object2FloatMap.Entry<K> from) {
/* 1170 */             return Object2FloatRBTreeMap.this.tailMap(from.getKey()).object2FloatEntrySet();
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
/*      */     implements ObjectListIterator<K>
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(K k) {
/* 1189 */       super(k);
/*      */     }
/*      */     
/*      */     public K next() {
/* 1193 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1197 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractObject2FloatSortedMap<K>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1204 */       return new Object2FloatRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1208 */       return new Object2FloatRBTreeMap.KeyIterator(from);
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
/* 1260 */       this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1263 */             return (FloatIterator)new Object2FloatRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(float k) {
/* 1267 */             return Object2FloatRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1271 */             return Object2FloatRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1275 */             Object2FloatRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1278 */     return this.values;
/*      */   }
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1282 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Object2FloatSortedMap<K> headMap(K to) {
/* 1286 */     return new Submap(null, true, to, false);
/*      */   }
/*      */   
/*      */   public Object2FloatSortedMap<K> tailMap(K from) {
/* 1290 */     return new Submap(from, false, null, true);
/*      */   }
/*      */   
/*      */   public Object2FloatSortedMap<K> subMap(K from, K to) {
/* 1294 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractObject2FloatSortedMap<K>
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
/*      */     protected transient ObjectSortedSet<Object2FloatMap.Entry<K>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient FloatCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(K from, boolean bottom, K to, boolean top) {
/* 1338 */       if (!bottom && !top && Object2FloatRBTreeMap.this.compare(from, to) > 0)
/* 1339 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1340 */       this.from = from;
/* 1341 */       this.bottom = bottom;
/* 1342 */       this.to = to;
/* 1343 */       this.top = top;
/* 1344 */       this.defRetValue = Object2FloatRBTreeMap.this.defRetValue;
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
/*      */     final boolean in(K k) {
/* 1362 */       return ((this.bottom || Object2FloatRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Object2FloatRBTreeMap.this
/* 1363 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2FloatMap.Entry<K>> object2FloatEntrySet() {
/* 1367 */       if (this.entries == null)
/* 1368 */         this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2FloatMap.Entry<Object2FloatMap.Entry<K>>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> iterator() {
/* 1371 */               return new Object2FloatRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> iterator(Object2FloatMap.Entry<K> from) {
/* 1376 */               return new Object2FloatRBTreeMap.Submap.SubmapEntryIterator(from.getKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Object2FloatMap.Entry<K>> comparator() {
/* 1380 */               return Object2FloatRBTreeMap.this.object2FloatEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1385 */               if (!(o instanceof Map.Entry))
/* 1386 */                 return false; 
/* 1387 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1388 */               if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1389 */                 return false; 
/* 1390 */               Object2FloatRBTreeMap.Entry<K> f = Object2FloatRBTreeMap.this.findKey((K)e.getKey());
/* 1391 */               return (f != null && Object2FloatRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1396 */               if (!(o instanceof Map.Entry))
/* 1397 */                 return false; 
/* 1398 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1399 */               if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1400 */                 return false; 
/* 1401 */               Object2FloatRBTreeMap.Entry<K> f = Object2FloatRBTreeMap.this.findKey((K)e.getKey());
/* 1402 */               if (f != null && Object2FloatRBTreeMap.Submap.this.in(f.key))
/* 1403 */                 Object2FloatRBTreeMap.Submap.this.removeFloat(f.key); 
/* 1404 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1408 */               int c = 0;
/* 1409 */               for (Iterator<?> i = iterator(); i.hasNext(); i.next())
/* 1410 */                 c++; 
/* 1411 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1415 */               return !(new Object2FloatRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1419 */               Object2FloatRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Object2FloatMap.Entry<K> first() {
/* 1423 */               return Object2FloatRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Object2FloatMap.Entry<K> last() {
/* 1427 */               return Object2FloatRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2FloatMap.Entry<K>> subSet(Object2FloatMap.Entry<K> from, Object2FloatMap.Entry<K> to) {
/* 1432 */               return Object2FloatRBTreeMap.Submap.this.subMap(from.getKey(), to.getKey()).object2FloatEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2FloatMap.Entry<K>> headSet(Object2FloatMap.Entry<K> to) {
/* 1436 */               return Object2FloatRBTreeMap.Submap.this.headMap(to.getKey()).object2FloatEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2FloatMap.Entry<K>> tailSet(Object2FloatMap.Entry<K> from) {
/* 1440 */               return Object2FloatRBTreeMap.Submap.this.tailMap(from.getKey()).object2FloatEntrySet();
/*      */             }
/*      */           }; 
/* 1443 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractObject2FloatSortedMap<K>.KeySet {
/*      */       public ObjectBidirectionalIterator<K> iterator() {
/* 1448 */         return new Object2FloatRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1452 */         return new Object2FloatRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> keySet() {
/* 1457 */       if (this.keys == null)
/* 1458 */         this.keys = new KeySet(); 
/* 1459 */       return this.keys;
/*      */     }
/*      */     
/*      */     public FloatCollection values() {
/* 1463 */       if (this.values == null)
/* 1464 */         this.values = (FloatCollection)new AbstractFloatCollection()
/*      */           {
/*      */             public FloatIterator iterator() {
/* 1467 */               return (FloatIterator)new Object2FloatRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(float k) {
/* 1471 */               return Object2FloatRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1475 */               return Object2FloatRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1479 */               Object2FloatRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1482 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object k) {
/* 1487 */       return (in((K)k) && Object2FloatRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(float v) {
/* 1491 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1493 */       while (i.hasNext()) {
/* 1494 */         float ev = (i.nextEntry()).value;
/* 1495 */         if (Float.floatToIntBits(ev) == Float.floatToIntBits(v))
/* 1496 */           return true; 
/*      */       } 
/* 1498 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public float getFloat(Object k) {
/* 1504 */       K kk = (K)k; Object2FloatRBTreeMap.Entry<K> e;
/* 1505 */       return (in(kk) && (e = Object2FloatRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public float put(K k, float v) {
/* 1509 */       Object2FloatRBTreeMap.this.modified = false;
/* 1510 */       if (!in(k))
/* 1511 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1512 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1513 */       float oldValue = Object2FloatRBTreeMap.this.put(k, v);
/* 1514 */       return Object2FloatRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public float removeFloat(Object k) {
/* 1519 */       Object2FloatRBTreeMap.this.modified = false;
/* 1520 */       if (!in((K)k))
/* 1521 */         return this.defRetValue; 
/* 1522 */       float oldValue = Object2FloatRBTreeMap.this.removeFloat(k);
/* 1523 */       return Object2FloatRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1527 */       SubmapIterator i = new SubmapIterator();
/* 1528 */       int n = 0;
/* 1529 */       while (i.hasNext()) {
/* 1530 */         n++;
/* 1531 */         i.nextEntry();
/*      */       } 
/* 1533 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1537 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1541 */       return Object2FloatRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Object2FloatSortedMap<K> headMap(K to) {
/* 1545 */       if (this.top)
/* 1546 */         return new Submap(this.from, this.bottom, to, false); 
/* 1547 */       return (Object2FloatRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Object2FloatSortedMap<K> tailMap(K from) {
/* 1551 */       if (this.bottom)
/* 1552 */         return new Submap(from, false, this.to, this.top); 
/* 1553 */       return (Object2FloatRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Object2FloatSortedMap<K> subMap(K from, K to) {
/* 1557 */       if (this.top && this.bottom)
/* 1558 */         return new Submap(from, false, to, false); 
/* 1559 */       if (!this.top)
/* 1560 */         to = (Object2FloatRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1561 */       if (!this.bottom)
/* 1562 */         from = (Object2FloatRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1563 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1564 */         return this; 
/* 1565 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2FloatRBTreeMap.Entry<K> firstEntry() {
/*      */       Object2FloatRBTreeMap.Entry<K> e;
/* 1574 */       if (Object2FloatRBTreeMap.this.tree == null) {
/* 1575 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1579 */       if (this.bottom) {
/* 1580 */         e = Object2FloatRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1582 */         e = Object2FloatRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1584 */         if (Object2FloatRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1585 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1589 */       if (e == null || (!this.top && Object2FloatRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1590 */         return null; 
/* 1591 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2FloatRBTreeMap.Entry<K> lastEntry() {
/*      */       Object2FloatRBTreeMap.Entry<K> e;
/* 1600 */       if (Object2FloatRBTreeMap.this.tree == null) {
/* 1601 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1605 */       if (this.top) {
/* 1606 */         e = Object2FloatRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1608 */         e = Object2FloatRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1610 */         if (Object2FloatRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1611 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1615 */       if (e == null || (!this.bottom && Object2FloatRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1616 */         return null; 
/* 1617 */       return e;
/*      */     }
/*      */     
/*      */     public K firstKey() {
/* 1621 */       Object2FloatRBTreeMap.Entry<K> e = firstEntry();
/* 1622 */       if (e == null)
/* 1623 */         throw new NoSuchElementException(); 
/* 1624 */       return e.key;
/*      */     }
/*      */     
/*      */     public K lastKey() {
/* 1628 */       Object2FloatRBTreeMap.Entry<K> e = lastEntry();
/* 1629 */       if (e == null)
/* 1630 */         throw new NoSuchElementException(); 
/* 1631 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Object2FloatRBTreeMap<K>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1644 */         this.next = Object2FloatRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(K k) {
/* 1647 */         this();
/* 1648 */         if (this.next != null)
/* 1649 */           if (!Object2FloatRBTreeMap.Submap.this.bottom && Object2FloatRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1650 */             this.prev = null;
/* 1651 */           } else if (!Object2FloatRBTreeMap.Submap.this.top && Object2FloatRBTreeMap.this.compare(k, (this.prev = Object2FloatRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1652 */             this.next = null;
/*      */           } else {
/* 1654 */             this.next = Object2FloatRBTreeMap.this.locateKey(k);
/* 1655 */             if (Object2FloatRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1656 */               this.prev = this.next;
/* 1657 */               this.next = this.next.next();
/*      */             } else {
/* 1659 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1665 */         this.prev = this.prev.prev();
/* 1666 */         if (!Object2FloatRBTreeMap.Submap.this.bottom && this.prev != null && Object2FloatRBTreeMap.this.compare(this.prev.key, Object2FloatRBTreeMap.Submap.this.from) < 0)
/* 1667 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1671 */         this.next = this.next.next();
/* 1672 */         if (!Object2FloatRBTreeMap.Submap.this.top && this.next != null && Object2FloatRBTreeMap.this.compare(this.next.key, Object2FloatRBTreeMap.Submap.this.to) >= 0)
/* 1673 */           this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Object2FloatMap.Entry<K>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(K k) {
/* 1682 */         super(k);
/*      */       }
/*      */       
/*      */       public Object2FloatMap.Entry<K> next() {
/* 1686 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Object2FloatMap.Entry<K> previous() {
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
/*      */       implements ObjectListIterator<K>
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(K from) {
/* 1708 */         super(from);
/*      */       }
/*      */       
/*      */       public K next() {
/* 1712 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public K previous() {
/* 1716 */         return (previousEntry()).key;
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
/* 1732 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public float previousFloat() {
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
/*      */   public Object2FloatRBTreeMap<K> clone() {
/*      */     Object2FloatRBTreeMap<K> c;
/*      */     try {
/* 1755 */       c = (Object2FloatRBTreeMap<K>)super.clone();
/* 1756 */     } catch (CloneNotSupportedException cantHappen) {
/* 1757 */       throw new InternalError();
/*      */     } 
/* 1759 */     c.keys = null;
/* 1760 */     c.values = null;
/* 1761 */     c.entries = null;
/* 1762 */     c.allocatePaths();
/* 1763 */     if (this.count != 0) {
/*      */       
/* 1765 */       Entry<K> rp = new Entry<>(), rq = new Entry<>();
/* 1766 */       Entry<K> p = rp;
/* 1767 */       rp.left(this.tree);
/* 1768 */       Entry<K> q = rq;
/* 1769 */       rq.pred((Entry<K>)null);
/*      */       while (true) {
/* 1771 */         if (!p.pred()) {
/* 1772 */           Entry<K> e = p.left.clone();
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
/* 1798 */           Entry<K> e = p.right.clone();
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
/* 1813 */       Entry<K> e = i.nextEntry();
/* 1814 */       s.writeObject(e.key);
/* 1815 */       s.writeFloat(e.value);
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
/* 1836 */     if (n == 1) {
/* 1837 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readFloat());
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
/* 1848 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readFloat());
/* 1849 */       entry.black(true);
/* 1850 */       entry.right(new Entry<>((K)s.readObject(), s.readFloat()));
/* 1851 */       entry.right.pred(entry);
/* 1852 */       entry.pred(pred);
/* 1853 */       entry.right.succ(succ);
/* 1854 */       return entry;
/*      */     } 
/*      */     
/* 1857 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1858 */     Entry<K> top = new Entry<>();
/* 1859 */     top.left(readTree(s, leftN, pred, top));
/* 1860 */     top.key = (K)s.readObject();
/* 1861 */     top.value = s.readFloat();
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
/* 1877 */       this.tree = readTree(s, this.count, (Entry<K>)null, (Entry<K>)null);
/*      */       
/* 1879 */       Entry<K> e = this.tree;
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2FloatRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */