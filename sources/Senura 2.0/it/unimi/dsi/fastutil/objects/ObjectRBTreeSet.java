/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
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
/*      */ public class ObjectRBTreeSet<K>
/*      */   extends AbstractObjectSortedSet<K>
/*      */   implements Serializable, Cloneable, ObjectSortedSet<K>
/*      */ {
/*      */   protected transient Entry<K> tree;
/*      */   protected int count;
/*      */   protected transient Entry<K> firstEntry;
/*      */   protected transient Entry<K> lastEntry;
/*      */   protected Comparator<? super K> storedComparator;
/*      */   protected transient Comparator<? super K> actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353130L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry<K>[] nodePath;
/*      */   
/*      */   public ObjectRBTreeSet() {
/*   55 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   61 */     this.tree = null;
/*   62 */     this.count = 0;
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
/*   74 */     this.actualComparator = this.storedComparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectRBTreeSet(Comparator<? super K> c) {
/*   83 */     this();
/*   84 */     this.storedComparator = c;
/*   85 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectRBTreeSet(Collection<? extends K> c) {
/*   94 */     this();
/*   95 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectRBTreeSet(SortedSet<K> s) {
/*  105 */     this(s.comparator());
/*  106 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectRBTreeSet(ObjectCollection<? extends K> c) {
/*  115 */     this();
/*  116 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectRBTreeSet(ObjectSortedSet<K> s) {
/*  126 */     this(s.comparator());
/*  127 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectRBTreeSet(Iterator<? extends K> i) {
/*      */     allocatePaths();
/*  136 */     while (i.hasNext()) {
/*  137 */       add(i.next());
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
/*      */   public ObjectRBTreeSet(K[] a, int offset, int length, Comparator<? super K> c) {
/*  153 */     this(c);
/*  154 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/*  155 */     for (int i = 0; i < length; i++) {
/*  156 */       add(a[offset + i]);
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
/*      */   public ObjectRBTreeSet(K[] a, int offset, int length) {
/*  169 */     this(a, offset, length, (Comparator<? super K>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectRBTreeSet(K[] a) {
/*  178 */     this();
/*  179 */     int i = a.length;
/*  180 */     while (i-- != 0) {
/*  181 */       add(a[i]);
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
/*      */   public ObjectRBTreeSet(K[] a, Comparator<? super K> c) {
/*  193 */     this(c);
/*  194 */     int i = a.length;
/*  195 */     while (i-- != 0) {
/*  196 */       add(a[i]);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int compare(K k1, K k2) {
/*  224 */     return (this.actualComparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.actualComparator.compare(k1, k2);
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
/*      */   private Entry<K> findKey(K k) {
/*  236 */     Entry<K> e = this.tree;
/*      */     int cmp;
/*  238 */     while (e != null && (cmp = compare(k, e.key)) != 0)
/*  239 */       e = (cmp < 0) ? e.left() : e.right(); 
/*  240 */     return e;
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
/*  252 */     Entry<K> e = this.tree, last = this.tree;
/*  253 */     int cmp = 0;
/*  254 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  255 */       last = e;
/*  256 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  258 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  268 */     this.dirPath = new boolean[64];
/*  269 */     this.nodePath = (Entry<K>[])new Entry[64];
/*      */   }
/*      */   
/*      */   public boolean add(K k) {
/*  273 */     int maxDepth = 0;
/*  274 */     if (this.tree == null) {
/*  275 */       this.count++;
/*  276 */       this.tree = this.lastEntry = this.firstEntry = new Entry<>(k);
/*      */     } else {
/*  278 */       Entry<K> p = this.tree;
/*  279 */       int i = 0; while (true) {
/*      */         int cmp;
/*  281 */         if ((cmp = compare(k, p.key)) == 0) {
/*      */           
/*  283 */           while (i-- != 0)
/*  284 */             this.nodePath[i] = null; 
/*  285 */           return false;
/*      */         } 
/*  287 */         this.nodePath[i] = p;
/*  288 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  289 */           if (p.succ()) {
/*  290 */             this.count++;
/*  291 */             Entry<K> e = new Entry<>(k);
/*  292 */             if (p.right == null)
/*  293 */               this.lastEntry = e; 
/*  294 */             e.left = p;
/*  295 */             e.right = p.right;
/*  296 */             p.right(e);
/*      */             break;
/*      */           } 
/*  299 */           p = p.right; continue;
/*      */         } 
/*  301 */         if (p.pred()) {
/*  302 */           this.count++;
/*  303 */           Entry<K> e = new Entry<>(k);
/*  304 */           if (p.left == null)
/*  305 */             this.firstEntry = e; 
/*  306 */           e.right = p;
/*  307 */           e.left = p.left;
/*  308 */           p.left(e);
/*      */           break;
/*      */         } 
/*  311 */         p = p.left;
/*      */       } 
/*      */       
/*  314 */       maxDepth = i--;
/*  315 */       while (i > 0 && !this.nodePath[i].black()) {
/*  316 */         if (!this.dirPath[i - 1]) {
/*  317 */           Entry<K> entry1 = (this.nodePath[i - 1]).right;
/*  318 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  319 */             this.nodePath[i].black(true);
/*  320 */             entry1.black(true);
/*  321 */             this.nodePath[i - 1].black(false);
/*  322 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  325 */           if (!this.dirPath[i]) {
/*  326 */             entry1 = this.nodePath[i];
/*      */           } else {
/*  328 */             Entry<K> entry = this.nodePath[i];
/*  329 */             entry1 = entry.right;
/*  330 */             entry.right = entry1.left;
/*  331 */             entry1.left = entry;
/*  332 */             (this.nodePath[i - 1]).left = entry1;
/*  333 */             if (entry1.pred()) {
/*  334 */               entry1.pred(false);
/*  335 */               entry.succ(entry1);
/*      */             } 
/*      */           } 
/*  338 */           Entry<K> entry2 = this.nodePath[i - 1];
/*  339 */           entry2.black(false);
/*  340 */           entry1.black(true);
/*  341 */           entry2.left = entry1.right;
/*  342 */           entry1.right = entry2;
/*  343 */           if (i < 2) {
/*  344 */             this.tree = entry1;
/*      */           }
/*  346 */           else if (this.dirPath[i - 2]) {
/*  347 */             (this.nodePath[i - 2]).right = entry1;
/*      */           } else {
/*  349 */             (this.nodePath[i - 2]).left = entry1;
/*      */           } 
/*  351 */           if (entry1.succ()) {
/*  352 */             entry1.succ(false);
/*  353 */             entry2.pred(entry1);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  358 */         Entry<K> y = (this.nodePath[i - 1]).left;
/*  359 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  360 */           this.nodePath[i].black(true);
/*  361 */           y.black(true);
/*  362 */           this.nodePath[i - 1].black(false);
/*  363 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  366 */         if (this.dirPath[i]) {
/*  367 */           y = this.nodePath[i];
/*      */         } else {
/*  369 */           Entry<K> entry = this.nodePath[i];
/*  370 */           y = entry.left;
/*  371 */           entry.left = y.right;
/*  372 */           y.right = entry;
/*  373 */           (this.nodePath[i - 1]).right = y;
/*  374 */           if (y.succ()) {
/*  375 */             y.succ(false);
/*  376 */             entry.pred(y);
/*      */           } 
/*      */         } 
/*  379 */         Entry<K> x = this.nodePath[i - 1];
/*  380 */         x.black(false);
/*  381 */         y.black(true);
/*  382 */         x.right = y.left;
/*  383 */         y.left = x;
/*  384 */         if (i < 2) {
/*  385 */           this.tree = y;
/*      */         }
/*  387 */         else if (this.dirPath[i - 2]) {
/*  388 */           (this.nodePath[i - 2]).right = y;
/*      */         } else {
/*  390 */           (this.nodePath[i - 2]).left = y;
/*      */         } 
/*  392 */         if (y.pred()) {
/*  393 */           y.pred(false);
/*  394 */           x.succ(y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  401 */     this.tree.black(true);
/*      */     
/*  403 */     while (maxDepth-- != 0)
/*  404 */       this.nodePath[maxDepth] = null; 
/*  405 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(Object k) {
/*  410 */     if (this.tree == null)
/*  411 */       return false; 
/*  412 */     Entry<K> p = this.tree;
/*      */     
/*  414 */     int i = 0;
/*  415 */     K kk = (K)k;
/*      */     int cmp;
/*  417 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  419 */       this.dirPath[i] = (cmp > 0);
/*  420 */       this.nodePath[i] = p;
/*  421 */       if (this.dirPath[i++]) {
/*  422 */         if ((p = p.right()) == null) {
/*      */           
/*  424 */           while (i-- != 0)
/*  425 */             this.nodePath[i] = null; 
/*  426 */           return false;
/*      */         }  continue;
/*      */       } 
/*  429 */       if ((p = p.left()) == null) {
/*      */         
/*  431 */         while (i-- != 0)
/*  432 */           this.nodePath[i] = null; 
/*  433 */         return false;
/*      */       } 
/*      */     } 
/*      */     
/*  437 */     if (p.left == null)
/*  438 */       this.firstEntry = p.next(); 
/*  439 */     if (p.right == null)
/*  440 */       this.lastEntry = p.prev(); 
/*  441 */     if (p.succ()) {
/*  442 */       if (p.pred()) {
/*  443 */         if (i == 0) {
/*  444 */           this.tree = p.left;
/*      */         }
/*  446 */         else if (this.dirPath[i - 1]) {
/*  447 */           this.nodePath[i - 1].succ(p.right);
/*      */         } else {
/*  449 */           this.nodePath[i - 1].pred(p.left);
/*      */         } 
/*      */       } else {
/*  452 */         (p.prev()).right = p.right;
/*  453 */         if (i == 0) {
/*  454 */           this.tree = p.left;
/*      */         }
/*  456 */         else if (this.dirPath[i - 1]) {
/*  457 */           (this.nodePath[i - 1]).right = p.left;
/*      */         } else {
/*  459 */           (this.nodePath[i - 1]).left = p.left;
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  464 */       Entry<K> r = p.right;
/*  465 */       if (r.pred()) {
/*  466 */         r.left = p.left;
/*  467 */         r.pred(p.pred());
/*  468 */         if (!r.pred())
/*  469 */           (r.prev()).right = r; 
/*  470 */         if (i == 0) {
/*  471 */           this.tree = r;
/*      */         }
/*  473 */         else if (this.dirPath[i - 1]) {
/*  474 */           (this.nodePath[i - 1]).right = r;
/*      */         } else {
/*  476 */           (this.nodePath[i - 1]).left = r;
/*      */         } 
/*  478 */         boolean color = r.black();
/*  479 */         r.black(p.black());
/*  480 */         p.black(color);
/*  481 */         this.dirPath[i] = true;
/*  482 */         this.nodePath[i++] = r;
/*      */       } else {
/*      */         Entry<K> s;
/*  485 */         int j = i++;
/*      */         while (true) {
/*  487 */           this.dirPath[i] = false;
/*  488 */           this.nodePath[i++] = r;
/*  489 */           s = r.left;
/*  490 */           if (s.pred())
/*      */             break; 
/*  492 */           r = s;
/*      */         } 
/*  494 */         this.dirPath[j] = true;
/*  495 */         this.nodePath[j] = s;
/*  496 */         if (s.succ()) {
/*  497 */           r.pred(s);
/*      */         } else {
/*  499 */           r.left = s.right;
/*  500 */         }  s.left = p.left;
/*  501 */         if (!p.pred()) {
/*  502 */           (p.prev()).right = s;
/*  503 */           s.pred(false);
/*      */         } 
/*  505 */         s.right(p.right);
/*  506 */         boolean color = s.black();
/*  507 */         s.black(p.black());
/*  508 */         p.black(color);
/*  509 */         if (j == 0) {
/*  510 */           this.tree = s;
/*      */         }
/*  512 */         else if (this.dirPath[j - 1]) {
/*  513 */           (this.nodePath[j - 1]).right = s;
/*      */         } else {
/*  515 */           (this.nodePath[j - 1]).left = s;
/*      */         } 
/*      */       } 
/*      */     } 
/*  519 */     int maxDepth = i;
/*  520 */     if (p.black()) {
/*  521 */       for (; i > 0; i--) {
/*  522 */         if ((this.dirPath[i - 1] && !this.nodePath[i - 1].succ()) || (!this.dirPath[i - 1] && !this.nodePath[i - 1].pred())) {
/*  523 */           Entry<K> x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  524 */           if (!x.black()) {
/*  525 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  529 */         if (!this.dirPath[i - 1]) {
/*  530 */           Entry<K> w = (this.nodePath[i - 1]).right;
/*  531 */           if (!w.black()) {
/*  532 */             w.black(true);
/*  533 */             this.nodePath[i - 1].black(false);
/*  534 */             (this.nodePath[i - 1]).right = w.left;
/*  535 */             w.left = this.nodePath[i - 1];
/*  536 */             if (i < 2) {
/*  537 */               this.tree = w;
/*      */             }
/*  539 */             else if (this.dirPath[i - 2]) {
/*  540 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  542 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  544 */             this.nodePath[i] = this.nodePath[i - 1];
/*  545 */             this.dirPath[i] = false;
/*  546 */             this.nodePath[i - 1] = w;
/*  547 */             if (maxDepth == i++)
/*  548 */               maxDepth++; 
/*  549 */             w = (this.nodePath[i - 1]).right;
/*      */           } 
/*  551 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  552 */             w.black(false);
/*      */           } else {
/*  554 */             if (w.succ() || w.right.black()) {
/*  555 */               Entry<K> y = w.left;
/*  556 */               y.black(true);
/*  557 */               w.black(false);
/*  558 */               w.left = y.right;
/*  559 */               y.right = w;
/*  560 */               w = (this.nodePath[i - 1]).right = y;
/*  561 */               if (w.succ()) {
/*  562 */                 w.succ(false);
/*  563 */                 w.right.pred(w);
/*      */               } 
/*      */             } 
/*  566 */             w.black(this.nodePath[i - 1].black());
/*  567 */             this.nodePath[i - 1].black(true);
/*  568 */             w.right.black(true);
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
/*  579 */             if (w.pred()) {
/*  580 */               w.pred(false);
/*  581 */               this.nodePath[i - 1].succ(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } else {
/*  586 */           Entry<K> w = (this.nodePath[i - 1]).left;
/*  587 */           if (!w.black()) {
/*  588 */             w.black(true);
/*  589 */             this.nodePath[i - 1].black(false);
/*  590 */             (this.nodePath[i - 1]).left = w.right;
/*  591 */             w.right = this.nodePath[i - 1];
/*  592 */             if (i < 2) {
/*  593 */               this.tree = w;
/*      */             }
/*  595 */             else if (this.dirPath[i - 2]) {
/*  596 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  598 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  600 */             this.nodePath[i] = this.nodePath[i - 1];
/*  601 */             this.dirPath[i] = true;
/*  602 */             this.nodePath[i - 1] = w;
/*  603 */             if (maxDepth == i++)
/*  604 */               maxDepth++; 
/*  605 */             w = (this.nodePath[i - 1]).left;
/*      */           } 
/*  607 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  608 */             w.black(false);
/*      */           } else {
/*  610 */             if (w.pred() || w.left.black()) {
/*  611 */               Entry<K> y = w.right;
/*  612 */               y.black(true);
/*  613 */               w.black(false);
/*  614 */               w.right = y.left;
/*  615 */               y.left = w;
/*  616 */               w = (this.nodePath[i - 1]).left = y;
/*  617 */               if (w.pred()) {
/*  618 */                 w.pred(false);
/*  619 */                 w.left.succ(w);
/*      */               } 
/*      */             } 
/*  622 */             w.black(this.nodePath[i - 1].black());
/*  623 */             this.nodePath[i - 1].black(true);
/*  624 */             w.left.black(true);
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
/*  635 */             if (w.succ()) {
/*  636 */               w.succ(false);
/*  637 */               this.nodePath[i - 1].pred(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  643 */       if (this.tree != null)
/*  644 */         this.tree.black(true); 
/*      */     } 
/*  646 */     this.count--;
/*      */     
/*  648 */     while (maxDepth-- != 0)
/*  649 */       this.nodePath[maxDepth] = null; 
/*  650 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean contains(Object k) {
/*  655 */     return (findKey((K)k) != null);
/*      */   }
/*      */   
/*      */   public K get(Object k) {
/*  659 */     Entry<K> entry = findKey((K)k);
/*  660 */     return (entry == null) ? null : entry.key;
/*      */   }
/*      */   
/*      */   public void clear() {
/*  664 */     this.count = 0;
/*  665 */     this.tree = null;
/*  666 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<K>
/*      */     implements Cloneable
/*      */   {
/*      */     private static final int BLACK_MASK = 1;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int SUCC_MASK = -2147483648;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int PRED_MASK = 1073741824;
/*      */ 
/*      */     
/*      */     K key;
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
/*      */     
/*      */     Entry() {}
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(K k) {
/*  704 */       this.key = k;
/*  705 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> left() {
/*  713 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> right() {
/*  721 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  729 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  737 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  746 */       if (pred) {
/*  747 */         this.info |= 0x40000000;
/*      */       } else {
/*  749 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  758 */       if (succ) {
/*  759 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  761 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<K> pred) {
/*  770 */       this.info |= 0x40000000;
/*  771 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<K> succ) {
/*  780 */       this.info |= Integer.MIN_VALUE;
/*  781 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<K> left) {
/*  790 */       this.info &= 0xBFFFFFFF;
/*  791 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<K> right) {
/*  800 */       this.info &= Integer.MAX_VALUE;
/*  801 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean black() {
/*  809 */       return ((this.info & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void black(boolean black) {
/*  818 */       if (black) {
/*  819 */         this.info |= 0x1;
/*      */       } else {
/*  821 */         this.info &= 0xFFFFFFFE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> next() {
/*  829 */       Entry<K> next = this.right;
/*  830 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  831 */         while ((next.info & 0x40000000) == 0)
/*  832 */           next = next.left;  
/*  833 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> prev() {
/*  841 */       Entry<K> prev = this.left;
/*  842 */       if ((this.info & 0x40000000) == 0)
/*  843 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  844 */           prev = prev.right;  
/*  845 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry<K> clone() {
/*      */       Entry<K> c;
/*      */       try {
/*  852 */         c = (Entry<K>)super.clone();
/*  853 */       } catch (CloneNotSupportedException cantHappen) {
/*  854 */         throw new InternalError();
/*      */       } 
/*  856 */       c.key = this.key;
/*  857 */       c.info = this.info;
/*  858 */       return c;
/*      */     }
/*      */     public boolean equals(Object o) {
/*  861 */       if (!(o instanceof Entry))
/*  862 */         return false; 
/*  863 */       Entry<?> e = (Entry)o;
/*  864 */       return Objects.equals(this.key, e.key);
/*      */     }
/*      */     public int hashCode() {
/*  867 */       return this.key.hashCode();
/*      */     }
/*      */     public String toString() {
/*  870 */       return String.valueOf(this.key);
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
/*      */   public int size() {
/*  891 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  895 */     return (this.count == 0);
/*      */   }
/*      */   
/*      */   public K first() {
/*  899 */     if (this.tree == null)
/*  900 */       throw new NoSuchElementException(); 
/*  901 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public K last() {
/*  905 */     if (this.tree == null)
/*  906 */       throw new NoSuchElementException(); 
/*  907 */     return this.lastEntry.key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class SetIterator
/*      */     implements ObjectListIterator<K>
/*      */   {
/*      */     ObjectRBTreeSet.Entry<K> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ObjectRBTreeSet.Entry<K> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ObjectRBTreeSet.Entry<K> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  938 */     int index = 0;
/*      */     SetIterator() {
/*  940 */       this.next = ObjectRBTreeSet.this.firstEntry;
/*      */     }
/*      */     SetIterator(K k) {
/*  943 */       if ((this.next = ObjectRBTreeSet.this.locateKey(k)) != null)
/*  944 */         if (ObjectRBTreeSet.this.compare(this.next.key, k) <= 0) {
/*  945 */           this.prev = this.next;
/*  946 */           this.next = this.next.next();
/*      */         } else {
/*  948 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  953 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  957 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/*  960 */       this.next = this.next.next();
/*      */     }
/*      */     void updatePrevious() {
/*  963 */       this.prev = this.prev.prev();
/*      */     }
/*      */     
/*      */     public K next() {
/*  967 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public K previous() {
/*  971 */       return (previousEntry()).key;
/*      */     }
/*      */     ObjectRBTreeSet.Entry<K> nextEntry() {
/*  974 */       if (!hasNext())
/*  975 */         throw new NoSuchElementException(); 
/*  976 */       this.curr = this.prev = this.next;
/*  977 */       this.index++;
/*  978 */       updateNext();
/*  979 */       return this.curr;
/*      */     }
/*      */     ObjectRBTreeSet.Entry<K> previousEntry() {
/*  982 */       if (!hasPrevious())
/*  983 */         throw new NoSuchElementException(); 
/*  984 */       this.curr = this.next = this.prev;
/*  985 */       this.index--;
/*  986 */       updatePrevious();
/*  987 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  991 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  995 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  999 */       if (this.curr == null) {
/* 1000 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1005 */       if (this.curr == this.prev)
/* 1006 */         this.index--; 
/* 1007 */       this.next = this.prev = this.curr;
/* 1008 */       updatePrevious();
/* 1009 */       updateNext();
/* 1010 */       ObjectRBTreeSet.this.remove(this.curr.key);
/* 1011 */       this.curr = null;
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectBidirectionalIterator<K> iterator() {
/* 1016 */     return new SetIterator();
/*      */   }
/*      */   
/*      */   public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1020 */     return new SetIterator(from);
/*      */   }
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1024 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<K> headSet(K to) {
/* 1028 */     return new Subset(null, true, to, false);
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<K> tailSet(K from) {
/* 1032 */     return new Subset(from, false, null, true);
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<K> subSet(K from, K to) {
/* 1036 */     return new Subset(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Subset
/*      */     extends AbstractObjectSortedSet<K>
/*      */     implements Serializable, ObjectSortedSet<K>
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     K from;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     K to;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean bottom;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean top;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Subset(K from, boolean bottom, K to, boolean top) {
/* 1074 */       if (!bottom && !top && ObjectRBTreeSet.this.compare(from, to) > 0) {
/* 1075 */         throw new IllegalArgumentException("Start element (" + from + ") is larger than end element (" + to + ")");
/*      */       }
/* 1077 */       this.from = from;
/* 1078 */       this.bottom = bottom;
/* 1079 */       this.to = to;
/* 1080 */       this.top = top;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1084 */       SubsetIterator i = new SubsetIterator();
/* 1085 */       while (i.hasNext()) {
/* 1086 */         i.next();
/* 1087 */         i.remove();
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
/* 1098 */       return ((this.bottom || ObjectRBTreeSet.this.compare(k, this.from) >= 0) && (this.top || ObjectRBTreeSet.this
/* 1099 */         .compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1104 */       return (in((K)k) && ObjectRBTreeSet.this.contains(k));
/*      */     }
/*      */     
/*      */     public boolean add(K k) {
/* 1108 */       if (!in(k))
/* 1109 */         throw new IllegalArgumentException("Element (" + k + ") out of range [" + (
/* 1110 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1111 */       return ObjectRBTreeSet.this.add(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1116 */       if (!in((K)k))
/* 1117 */         return false; 
/* 1118 */       return ObjectRBTreeSet.this.remove(k);
/*      */     }
/*      */     
/*      */     public int size() {
/* 1122 */       SubsetIterator i = new SubsetIterator();
/* 1123 */       int n = 0;
/* 1124 */       while (i.hasNext()) {
/* 1125 */         n++;
/* 1126 */         i.next();
/*      */       } 
/* 1128 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1132 */       return !(new SubsetIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1136 */       return ObjectRBTreeSet.this.actualComparator;
/*      */     }
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1140 */       return new SubsetIterator();
/*      */     }
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1144 */       return new SubsetIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1148 */       if (this.top)
/* 1149 */         return new Subset(this.from, this.bottom, to, false); 
/* 1150 */       return (ObjectRBTreeSet.this.compare(to, this.to) < 0) ? new Subset(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1154 */       if (this.bottom)
/* 1155 */         return new Subset(from, false, this.to, this.top); 
/* 1156 */       return (ObjectRBTreeSet.this.compare(from, this.from) > 0) ? new Subset(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1160 */       if (this.top && this.bottom)
/* 1161 */         return new Subset(from, false, to, false); 
/* 1162 */       if (!this.top)
/* 1163 */         to = (ObjectRBTreeSet.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1164 */       if (!this.bottom)
/* 1165 */         from = (ObjectRBTreeSet.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1166 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1167 */         return this; 
/* 1168 */       return new Subset(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectRBTreeSet.Entry<K> firstEntry() {
/*      */       ObjectRBTreeSet.Entry<K> e;
/* 1177 */       if (ObjectRBTreeSet.this.tree == null) {
/* 1178 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1182 */       if (this.bottom) {
/* 1183 */         e = ObjectRBTreeSet.this.firstEntry;
/*      */       } else {
/* 1185 */         e = ObjectRBTreeSet.this.locateKey(this.from);
/*      */         
/* 1187 */         if (ObjectRBTreeSet.this.compare(e.key, this.from) < 0) {
/* 1188 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1192 */       if (e == null || (!this.top && ObjectRBTreeSet.this.compare(e.key, this.to) >= 0))
/* 1193 */         return null; 
/* 1194 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectRBTreeSet.Entry<K> lastEntry() {
/*      */       ObjectRBTreeSet.Entry<K> e;
/* 1203 */       if (ObjectRBTreeSet.this.tree == null) {
/* 1204 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1208 */       if (this.top) {
/* 1209 */         e = ObjectRBTreeSet.this.lastEntry;
/*      */       } else {
/* 1211 */         e = ObjectRBTreeSet.this.locateKey(this.to);
/*      */         
/* 1213 */         if (ObjectRBTreeSet.this.compare(e.key, this.to) >= 0) {
/* 1214 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1218 */       if (e == null || (!this.bottom && ObjectRBTreeSet.this.compare(e.key, this.from) < 0))
/* 1219 */         return null; 
/* 1220 */       return e;
/*      */     }
/*      */     
/*      */     public K first() {
/* 1224 */       ObjectRBTreeSet.Entry<K> e = firstEntry();
/* 1225 */       if (e == null)
/* 1226 */         throw new NoSuchElementException(); 
/* 1227 */       return e.key;
/*      */     }
/*      */     
/*      */     public K last() {
/* 1231 */       ObjectRBTreeSet.Entry<K> e = lastEntry();
/* 1232 */       if (e == null)
/* 1233 */         throw new NoSuchElementException(); 
/* 1234 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubsetIterator
/*      */       extends ObjectRBTreeSet<K>.SetIterator
/*      */     {
/*      */       SubsetIterator() {
/* 1247 */         this.next = ObjectRBTreeSet.Subset.this.firstEntry();
/*      */       }
/*      */       SubsetIterator(K k) {
/* 1250 */         this();
/* 1251 */         if (this.next != null)
/* 1252 */           if (!ObjectRBTreeSet.Subset.this.bottom && ObjectRBTreeSet.this.compare(k, this.next.key) < 0) {
/* 1253 */             this.prev = null;
/* 1254 */           } else if (!ObjectRBTreeSet.Subset.this.top && ObjectRBTreeSet.this.compare(k, (this.prev = ObjectRBTreeSet.Subset.this.lastEntry()).key) >= 0) {
/* 1255 */             this.next = null;
/*      */           } else {
/* 1257 */             this.next = ObjectRBTreeSet.this.locateKey(k);
/* 1258 */             if (ObjectRBTreeSet.this.compare(this.next.key, k) <= 0) {
/* 1259 */               this.prev = this.next;
/* 1260 */               this.next = this.next.next();
/*      */             } else {
/* 1262 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1268 */         this.prev = this.prev.prev();
/* 1269 */         if (!ObjectRBTreeSet.Subset.this.bottom && this.prev != null && ObjectRBTreeSet.this.compare(this.prev.key, ObjectRBTreeSet.Subset.this.from) < 0)
/* 1270 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1274 */         this.next = this.next.next();
/* 1275 */         if (!ObjectRBTreeSet.Subset.this.top && this.next != null && ObjectRBTreeSet.this.compare(this.next.key, ObjectRBTreeSet.Subset.this.to) >= 0) {
/* 1276 */           this.next = null;
/*      */         }
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
/*      */   public Object clone() {
/*      */     ObjectRBTreeSet<K> c;
/*      */     try {
/* 1295 */       c = (ObjectRBTreeSet<K>)super.clone();
/* 1296 */     } catch (CloneNotSupportedException cantHappen) {
/* 1297 */       throw new InternalError();
/*      */     } 
/* 1299 */     c.allocatePaths();
/* 1300 */     if (this.count != 0) {
/*      */       
/* 1302 */       Entry<K> rp = new Entry<>(), rq = new Entry<>();
/* 1303 */       Entry<K> p = rp;
/* 1304 */       rp.left(this.tree);
/* 1305 */       Entry<K> q = rq;
/* 1306 */       rq.pred((Entry<K>)null);
/*      */       while (true) {
/* 1308 */         if (!p.pred()) {
/* 1309 */           Entry<K> e = p.left.clone();
/* 1310 */           e.pred(q.left);
/* 1311 */           e.succ(q);
/* 1312 */           q.left(e);
/* 1313 */           p = p.left;
/* 1314 */           q = q.left;
/*      */         } else {
/* 1316 */           while (p.succ()) {
/* 1317 */             p = p.right;
/* 1318 */             if (p == null) {
/* 1319 */               q.right = null;
/* 1320 */               c.tree = rq.left;
/* 1321 */               c.firstEntry = c.tree;
/* 1322 */               while (c.firstEntry.left != null)
/* 1323 */                 c.firstEntry = c.firstEntry.left; 
/* 1324 */               c.lastEntry = c.tree;
/* 1325 */               while (c.lastEntry.right != null)
/* 1326 */                 c.lastEntry = c.lastEntry.right; 
/* 1327 */               return c;
/*      */             } 
/* 1329 */             q = q.right;
/*      */           } 
/* 1331 */           p = p.right;
/* 1332 */           q = q.right;
/*      */         } 
/* 1334 */         if (!p.succ()) {
/* 1335 */           Entry<K> e = p.right.clone();
/* 1336 */           e.succ(q.right);
/* 1337 */           e.pred(q);
/* 1338 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1342 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1345 */     int n = this.count;
/* 1346 */     SetIterator i = new SetIterator();
/* 1347 */     s.defaultWriteObject();
/* 1348 */     while (n-- != 0) {
/* 1349 */       s.writeObject(i.next());
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
/*      */   private Entry<K> readTree(ObjectInputStream s, int n, Entry<K> pred, Entry<K> succ) throws IOException, ClassNotFoundException {
/* 1369 */     if (n == 1) {
/* 1370 */       Entry<K> entry = new Entry<>((K)s.readObject());
/* 1371 */       entry.pred(pred);
/* 1372 */       entry.succ(succ);
/* 1373 */       entry.black(true);
/* 1374 */       return entry;
/*      */     } 
/* 1376 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1381 */       Entry<K> entry = new Entry<>((K)s.readObject());
/* 1382 */       entry.black(true);
/* 1383 */       entry.right(new Entry<>((K)s.readObject()));
/* 1384 */       entry.right.pred(entry);
/* 1385 */       entry.pred(pred);
/* 1386 */       entry.right.succ(succ);
/* 1387 */       return entry;
/*      */     } 
/*      */     
/* 1390 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1391 */     Entry<K> top = new Entry<>();
/* 1392 */     top.left(readTree(s, leftN, pred, top));
/* 1393 */     top.key = (K)s.readObject();
/* 1394 */     top.black(true);
/* 1395 */     top.right(readTree(s, rightN, top, succ));
/* 1396 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1397 */       top.right.black(false); 
/* 1398 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1401 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1406 */     setActualComparator();
/* 1407 */     allocatePaths();
/* 1408 */     if (this.count != 0) {
/* 1409 */       this.tree = readTree(s, this.count, (Entry<K>)null, (Entry<K>)null);
/*      */       
/* 1411 */       Entry<K> e = this.tree;
/* 1412 */       while (e.left() != null)
/* 1413 */         e = e.left(); 
/* 1414 */       this.firstEntry = e;
/* 1415 */       e = this.tree;
/* 1416 */       while (e.right() != null)
/* 1417 */         e = e.right(); 
/* 1418 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectRBTreeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */