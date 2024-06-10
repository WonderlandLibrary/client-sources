/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
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
/*      */ public class DoubleRBTreeSet
/*      */   extends AbstractDoubleSortedSet
/*      */   implements Serializable, Cloneable, DoubleSortedSet
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected Comparator<? super Double> storedComparator;
/*      */   protected transient DoubleComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353130L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public DoubleRBTreeSet() {
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
/*   74 */     this.actualComparator = DoubleComparators.asDoubleComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleRBTreeSet(Comparator<? super Double> c) {
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
/*      */   public DoubleRBTreeSet(Collection<? extends Double> c) {
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
/*      */   public DoubleRBTreeSet(SortedSet<Double> s) {
/*  105 */     this(s.comparator());
/*  106 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleRBTreeSet(DoubleCollection c) {
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
/*      */   public DoubleRBTreeSet(DoubleSortedSet s) {
/*  126 */     this(s.comparator());
/*  127 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleRBTreeSet(DoubleIterator i) {
/*      */     allocatePaths();
/*  136 */     while (i.hasNext()) {
/*  137 */       add(i.nextDouble());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleRBTreeSet(Iterator<?> i) {
/*  147 */     this(DoubleIterators.asDoubleIterator(i));
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
/*      */   public DoubleRBTreeSet(double[] a, int offset, int length, Comparator<? super Double> c) {
/*  163 */     this(c);
/*  164 */     DoubleArrays.ensureOffsetLength(a, offset, length);
/*  165 */     for (int i = 0; i < length; i++) {
/*  166 */       add(a[offset + i]);
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
/*      */   public DoubleRBTreeSet(double[] a, int offset, int length) {
/*  179 */     this(a, offset, length, (Comparator<? super Double>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleRBTreeSet(double[] a) {
/*  188 */     this();
/*  189 */     int i = a.length;
/*  190 */     while (i-- != 0) {
/*  191 */       add(a[i]);
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
/*      */   public DoubleRBTreeSet(double[] a, Comparator<? super Double> c) {
/*  203 */     this(c);
/*  204 */     int i = a.length;
/*  205 */     while (i-- != 0) {
/*  206 */       add(a[i]);
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
/*      */   final int compare(double k1, double k2) {
/*  234 */     return (this.actualComparator == null) ? Double.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   private Entry findKey(double k) {
/*  246 */     Entry e = this.tree;
/*      */     int cmp;
/*  248 */     while (e != null && (cmp = compare(k, e.key)) != 0)
/*  249 */       e = (cmp < 0) ? e.left() : e.right(); 
/*  250 */     return e;
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
/*      */   final Entry locateKey(double k) {
/*  262 */     Entry e = this.tree, last = this.tree;
/*  263 */     int cmp = 0;
/*  264 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  265 */       last = e;
/*  266 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  268 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  278 */     this.dirPath = new boolean[64];
/*  279 */     this.nodePath = new Entry[64];
/*      */   }
/*      */   
/*      */   public boolean add(double k) {
/*  283 */     int maxDepth = 0;
/*  284 */     if (this.tree == null) {
/*  285 */       this.count++;
/*  286 */       this.tree = this.lastEntry = this.firstEntry = new Entry(k);
/*      */     } else {
/*  288 */       Entry p = this.tree;
/*  289 */       int i = 0; while (true) {
/*      */         int cmp;
/*  291 */         if ((cmp = compare(k, p.key)) == 0) {
/*      */           
/*  293 */           while (i-- != 0)
/*  294 */             this.nodePath[i] = null; 
/*  295 */           return false;
/*      */         } 
/*  297 */         this.nodePath[i] = p;
/*  298 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  299 */           if (p.succ()) {
/*  300 */             this.count++;
/*  301 */             Entry e = new Entry(k);
/*  302 */             if (p.right == null)
/*  303 */               this.lastEntry = e; 
/*  304 */             e.left = p;
/*  305 */             e.right = p.right;
/*  306 */             p.right(e);
/*      */             break;
/*      */           } 
/*  309 */           p = p.right; continue;
/*      */         } 
/*  311 */         if (p.pred()) {
/*  312 */           this.count++;
/*  313 */           Entry e = new Entry(k);
/*  314 */           if (p.left == null)
/*  315 */             this.firstEntry = e; 
/*  316 */           e.right = p;
/*  317 */           e.left = p.left;
/*  318 */           p.left(e);
/*      */           break;
/*      */         } 
/*  321 */         p = p.left;
/*      */       } 
/*      */       
/*  324 */       maxDepth = i--;
/*  325 */       while (i > 0 && !this.nodePath[i].black()) {
/*  326 */         if (!this.dirPath[i - 1]) {
/*  327 */           Entry entry1 = (this.nodePath[i - 1]).right;
/*  328 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  329 */             this.nodePath[i].black(true);
/*  330 */             entry1.black(true);
/*  331 */             this.nodePath[i - 1].black(false);
/*  332 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  335 */           if (!this.dirPath[i]) {
/*  336 */             entry1 = this.nodePath[i];
/*      */           } else {
/*  338 */             Entry entry = this.nodePath[i];
/*  339 */             entry1 = entry.right;
/*  340 */             entry.right = entry1.left;
/*  341 */             entry1.left = entry;
/*  342 */             (this.nodePath[i - 1]).left = entry1;
/*  343 */             if (entry1.pred()) {
/*  344 */               entry1.pred(false);
/*  345 */               entry.succ(entry1);
/*      */             } 
/*      */           } 
/*  348 */           Entry entry2 = this.nodePath[i - 1];
/*  349 */           entry2.black(false);
/*  350 */           entry1.black(true);
/*  351 */           entry2.left = entry1.right;
/*  352 */           entry1.right = entry2;
/*  353 */           if (i < 2) {
/*  354 */             this.tree = entry1;
/*      */           }
/*  356 */           else if (this.dirPath[i - 2]) {
/*  357 */             (this.nodePath[i - 2]).right = entry1;
/*      */           } else {
/*  359 */             (this.nodePath[i - 2]).left = entry1;
/*      */           } 
/*  361 */           if (entry1.succ()) {
/*  362 */             entry1.succ(false);
/*  363 */             entry2.pred(entry1);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  368 */         Entry y = (this.nodePath[i - 1]).left;
/*  369 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  370 */           this.nodePath[i].black(true);
/*  371 */           y.black(true);
/*  372 */           this.nodePath[i - 1].black(false);
/*  373 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  376 */         if (this.dirPath[i]) {
/*  377 */           y = this.nodePath[i];
/*      */         } else {
/*  379 */           Entry entry = this.nodePath[i];
/*  380 */           y = entry.left;
/*  381 */           entry.left = y.right;
/*  382 */           y.right = entry;
/*  383 */           (this.nodePath[i - 1]).right = y;
/*  384 */           if (y.succ()) {
/*  385 */             y.succ(false);
/*  386 */             entry.pred(y);
/*      */           } 
/*      */         } 
/*  389 */         Entry x = this.nodePath[i - 1];
/*  390 */         x.black(false);
/*  391 */         y.black(true);
/*  392 */         x.right = y.left;
/*  393 */         y.left = x;
/*  394 */         if (i < 2) {
/*  395 */           this.tree = y;
/*      */         }
/*  397 */         else if (this.dirPath[i - 2]) {
/*  398 */           (this.nodePath[i - 2]).right = y;
/*      */         } else {
/*  400 */           (this.nodePath[i - 2]).left = y;
/*      */         } 
/*  402 */         if (y.pred()) {
/*  403 */           y.pred(false);
/*  404 */           x.succ(y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  411 */     this.tree.black(true);
/*      */     
/*  413 */     while (maxDepth-- != 0)
/*  414 */       this.nodePath[maxDepth] = null; 
/*  415 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(double k) {
/*  420 */     if (this.tree == null)
/*  421 */       return false; 
/*  422 */     Entry p = this.tree;
/*      */     
/*  424 */     int i = 0;
/*  425 */     double kk = k;
/*      */     int cmp;
/*  427 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  429 */       this.dirPath[i] = (cmp > 0);
/*  430 */       this.nodePath[i] = p;
/*  431 */       if (this.dirPath[i++]) {
/*  432 */         if ((p = p.right()) == null) {
/*      */           
/*  434 */           while (i-- != 0)
/*  435 */             this.nodePath[i] = null; 
/*  436 */           return false;
/*      */         }  continue;
/*      */       } 
/*  439 */       if ((p = p.left()) == null) {
/*      */         
/*  441 */         while (i-- != 0)
/*  442 */           this.nodePath[i] = null; 
/*  443 */         return false;
/*      */       } 
/*      */     } 
/*      */     
/*  447 */     if (p.left == null)
/*  448 */       this.firstEntry = p.next(); 
/*  449 */     if (p.right == null)
/*  450 */       this.lastEntry = p.prev(); 
/*  451 */     if (p.succ()) {
/*  452 */       if (p.pred()) {
/*  453 */         if (i == 0) {
/*  454 */           this.tree = p.left;
/*      */         }
/*  456 */         else if (this.dirPath[i - 1]) {
/*  457 */           this.nodePath[i - 1].succ(p.right);
/*      */         } else {
/*  459 */           this.nodePath[i - 1].pred(p.left);
/*      */         } 
/*      */       } else {
/*  462 */         (p.prev()).right = p.right;
/*  463 */         if (i == 0) {
/*  464 */           this.tree = p.left;
/*      */         }
/*  466 */         else if (this.dirPath[i - 1]) {
/*  467 */           (this.nodePath[i - 1]).right = p.left;
/*      */         } else {
/*  469 */           (this.nodePath[i - 1]).left = p.left;
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  474 */       Entry r = p.right;
/*  475 */       if (r.pred()) {
/*  476 */         r.left = p.left;
/*  477 */         r.pred(p.pred());
/*  478 */         if (!r.pred())
/*  479 */           (r.prev()).right = r; 
/*  480 */         if (i == 0) {
/*  481 */           this.tree = r;
/*      */         }
/*  483 */         else if (this.dirPath[i - 1]) {
/*  484 */           (this.nodePath[i - 1]).right = r;
/*      */         } else {
/*  486 */           (this.nodePath[i - 1]).left = r;
/*      */         } 
/*  488 */         boolean color = r.black();
/*  489 */         r.black(p.black());
/*  490 */         p.black(color);
/*  491 */         this.dirPath[i] = true;
/*  492 */         this.nodePath[i++] = r;
/*      */       } else {
/*      */         Entry s;
/*  495 */         int j = i++;
/*      */         while (true) {
/*  497 */           this.dirPath[i] = false;
/*  498 */           this.nodePath[i++] = r;
/*  499 */           s = r.left;
/*  500 */           if (s.pred())
/*      */             break; 
/*  502 */           r = s;
/*      */         } 
/*  504 */         this.dirPath[j] = true;
/*  505 */         this.nodePath[j] = s;
/*  506 */         if (s.succ()) {
/*  507 */           r.pred(s);
/*      */         } else {
/*  509 */           r.left = s.right;
/*  510 */         }  s.left = p.left;
/*  511 */         if (!p.pred()) {
/*  512 */           (p.prev()).right = s;
/*  513 */           s.pred(false);
/*      */         } 
/*  515 */         s.right(p.right);
/*  516 */         boolean color = s.black();
/*  517 */         s.black(p.black());
/*  518 */         p.black(color);
/*  519 */         if (j == 0) {
/*  520 */           this.tree = s;
/*      */         }
/*  522 */         else if (this.dirPath[j - 1]) {
/*  523 */           (this.nodePath[j - 1]).right = s;
/*      */         } else {
/*  525 */           (this.nodePath[j - 1]).left = s;
/*      */         } 
/*      */       } 
/*      */     } 
/*  529 */     int maxDepth = i;
/*  530 */     if (p.black()) {
/*  531 */       for (; i > 0; i--) {
/*  532 */         if ((this.dirPath[i - 1] && !this.nodePath[i - 1].succ()) || (!this.dirPath[i - 1] && !this.nodePath[i - 1].pred())) {
/*  533 */           Entry x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  534 */           if (!x.black()) {
/*  535 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  539 */         if (!this.dirPath[i - 1]) {
/*  540 */           Entry w = (this.nodePath[i - 1]).right;
/*  541 */           if (!w.black()) {
/*  542 */             w.black(true);
/*  543 */             this.nodePath[i - 1].black(false);
/*  544 */             (this.nodePath[i - 1]).right = w.left;
/*  545 */             w.left = this.nodePath[i - 1];
/*  546 */             if (i < 2) {
/*  547 */               this.tree = w;
/*      */             }
/*  549 */             else if (this.dirPath[i - 2]) {
/*  550 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  552 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  554 */             this.nodePath[i] = this.nodePath[i - 1];
/*  555 */             this.dirPath[i] = false;
/*  556 */             this.nodePath[i - 1] = w;
/*  557 */             if (maxDepth == i++)
/*  558 */               maxDepth++; 
/*  559 */             w = (this.nodePath[i - 1]).right;
/*      */           } 
/*  561 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  562 */             w.black(false);
/*      */           } else {
/*  564 */             if (w.succ() || w.right.black()) {
/*  565 */               Entry y = w.left;
/*  566 */               y.black(true);
/*  567 */               w.black(false);
/*  568 */               w.left = y.right;
/*  569 */               y.right = w;
/*  570 */               w = (this.nodePath[i - 1]).right = y;
/*  571 */               if (w.succ()) {
/*  572 */                 w.succ(false);
/*  573 */                 w.right.pred(w);
/*      */               } 
/*      */             } 
/*  576 */             w.black(this.nodePath[i - 1].black());
/*  577 */             this.nodePath[i - 1].black(true);
/*  578 */             w.right.black(true);
/*  579 */             (this.nodePath[i - 1]).right = w.left;
/*  580 */             w.left = this.nodePath[i - 1];
/*  581 */             if (i < 2) {
/*  582 */               this.tree = w;
/*      */             }
/*  584 */             else if (this.dirPath[i - 2]) {
/*  585 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  587 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  589 */             if (w.pred()) {
/*  590 */               w.pred(false);
/*  591 */               this.nodePath[i - 1].succ(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } else {
/*  596 */           Entry w = (this.nodePath[i - 1]).left;
/*  597 */           if (!w.black()) {
/*  598 */             w.black(true);
/*  599 */             this.nodePath[i - 1].black(false);
/*  600 */             (this.nodePath[i - 1]).left = w.right;
/*  601 */             w.right = this.nodePath[i - 1];
/*  602 */             if (i < 2) {
/*  603 */               this.tree = w;
/*      */             }
/*  605 */             else if (this.dirPath[i - 2]) {
/*  606 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  608 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  610 */             this.nodePath[i] = this.nodePath[i - 1];
/*  611 */             this.dirPath[i] = true;
/*  612 */             this.nodePath[i - 1] = w;
/*  613 */             if (maxDepth == i++)
/*  614 */               maxDepth++; 
/*  615 */             w = (this.nodePath[i - 1]).left;
/*      */           } 
/*  617 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  618 */             w.black(false);
/*      */           } else {
/*  620 */             if (w.pred() || w.left.black()) {
/*  621 */               Entry y = w.right;
/*  622 */               y.black(true);
/*  623 */               w.black(false);
/*  624 */               w.right = y.left;
/*  625 */               y.left = w;
/*  626 */               w = (this.nodePath[i - 1]).left = y;
/*  627 */               if (w.pred()) {
/*  628 */                 w.pred(false);
/*  629 */                 w.left.succ(w);
/*      */               } 
/*      */             } 
/*  632 */             w.black(this.nodePath[i - 1].black());
/*  633 */             this.nodePath[i - 1].black(true);
/*  634 */             w.left.black(true);
/*  635 */             (this.nodePath[i - 1]).left = w.right;
/*  636 */             w.right = this.nodePath[i - 1];
/*  637 */             if (i < 2) {
/*  638 */               this.tree = w;
/*      */             }
/*  640 */             else if (this.dirPath[i - 2]) {
/*  641 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  643 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  645 */             if (w.succ()) {
/*  646 */               w.succ(false);
/*  647 */               this.nodePath[i - 1].pred(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  653 */       if (this.tree != null)
/*  654 */         this.tree.black(true); 
/*      */     } 
/*  656 */     this.count--;
/*      */     
/*  658 */     while (maxDepth-- != 0)
/*  659 */       this.nodePath[maxDepth] = null; 
/*  660 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean contains(double k) {
/*  665 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public void clear() {
/*  669 */     this.count = 0;
/*  670 */     this.tree = null;
/*  671 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry
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
/*      */     double key;
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
/*      */     
/*      */     Entry() {}
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(double k) {
/*  709 */       this.key = k;
/*  710 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left() {
/*  718 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right() {
/*  726 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  734 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  742 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  751 */       if (pred) {
/*  752 */         this.info |= 0x40000000;
/*      */       } else {
/*  754 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  763 */       if (succ) {
/*  764 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  766 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry pred) {
/*  775 */       this.info |= 0x40000000;
/*  776 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry succ) {
/*  785 */       this.info |= Integer.MIN_VALUE;
/*  786 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry left) {
/*  795 */       this.info &= 0xBFFFFFFF;
/*  796 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry right) {
/*  805 */       this.info &= Integer.MAX_VALUE;
/*  806 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean black() {
/*  814 */       return ((this.info & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void black(boolean black) {
/*  823 */       if (black) {
/*  824 */         this.info |= 0x1;
/*      */       } else {
/*  826 */         this.info &= 0xFFFFFFFE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry next() {
/*  834 */       Entry next = this.right;
/*  835 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  836 */         while ((next.info & 0x40000000) == 0)
/*  837 */           next = next.left;  
/*  838 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry prev() {
/*  846 */       Entry prev = this.left;
/*  847 */       if ((this.info & 0x40000000) == 0)
/*  848 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  849 */           prev = prev.right;  
/*  850 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry clone() {
/*      */       Entry c;
/*      */       try {
/*  857 */         c = (Entry)super.clone();
/*  858 */       } catch (CloneNotSupportedException cantHappen) {
/*  859 */         throw new InternalError();
/*      */       } 
/*  861 */       c.key = this.key;
/*  862 */       c.info = this.info;
/*  863 */       return c;
/*      */     }
/*      */     public boolean equals(Object o) {
/*  866 */       if (!(o instanceof Entry))
/*  867 */         return false; 
/*  868 */       Entry e = (Entry)o;
/*  869 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(e.key));
/*      */     }
/*      */     public int hashCode() {
/*  872 */       return HashCommon.double2int(this.key);
/*      */     }
/*      */     public String toString() {
/*  875 */       return String.valueOf(this.key);
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
/*  896 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  900 */     return (this.count == 0);
/*      */   }
/*      */   
/*      */   public double firstDouble() {
/*  904 */     if (this.tree == null)
/*  905 */       throw new NoSuchElementException(); 
/*  906 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public double lastDouble() {
/*  910 */     if (this.tree == null)
/*  911 */       throw new NoSuchElementException(); 
/*  912 */     return this.lastEntry.key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class SetIterator
/*      */     implements DoubleListIterator
/*      */   {
/*      */     DoubleRBTreeSet.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DoubleRBTreeSet.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DoubleRBTreeSet.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  943 */     int index = 0;
/*      */     SetIterator() {
/*  945 */       this.next = DoubleRBTreeSet.this.firstEntry;
/*      */     }
/*      */     SetIterator(double k) {
/*  948 */       if ((this.next = DoubleRBTreeSet.this.locateKey(k)) != null)
/*  949 */         if (DoubleRBTreeSet.this.compare(this.next.key, k) <= 0) {
/*  950 */           this.prev = this.next;
/*  951 */           this.next = this.next.next();
/*      */         } else {
/*  953 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  958 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  962 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/*  965 */       this.next = this.next.next();
/*      */     }
/*      */     void updatePrevious() {
/*  968 */       this.prev = this.prev.prev();
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/*  972 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/*  976 */       return (previousEntry()).key;
/*      */     }
/*      */     DoubleRBTreeSet.Entry nextEntry() {
/*  979 */       if (!hasNext())
/*  980 */         throw new NoSuchElementException(); 
/*  981 */       this.curr = this.prev = this.next;
/*  982 */       this.index++;
/*  983 */       updateNext();
/*  984 */       return this.curr;
/*      */     }
/*      */     DoubleRBTreeSet.Entry previousEntry() {
/*  987 */       if (!hasPrevious())
/*  988 */         throw new NoSuchElementException(); 
/*  989 */       this.curr = this.next = this.prev;
/*  990 */       this.index--;
/*  991 */       updatePrevious();
/*  992 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  996 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1000 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1004 */       if (this.curr == null) {
/* 1005 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1010 */       if (this.curr == this.prev)
/* 1011 */         this.index--; 
/* 1012 */       this.next = this.prev = this.curr;
/* 1013 */       updatePrevious();
/* 1014 */       updateNext();
/* 1015 */       DoubleRBTreeSet.this.remove(this.curr.key);
/* 1016 */       this.curr = null;
/*      */     }
/*      */   }
/*      */   
/*      */   public DoubleBidirectionalIterator iterator() {
/* 1021 */     return new SetIterator();
/*      */   }
/*      */   
/*      */   public DoubleBidirectionalIterator iterator(double from) {
/* 1025 */     return new SetIterator(from);
/*      */   }
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1029 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public DoubleSortedSet headSet(double to) {
/* 1033 */     return new Subset(0.0D, true, to, false);
/*      */   }
/*      */   
/*      */   public DoubleSortedSet tailSet(double from) {
/* 1037 */     return new Subset(from, false, 0.0D, true);
/*      */   }
/*      */   
/*      */   public DoubleSortedSet subSet(double from, double to) {
/* 1041 */     return new Subset(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Subset
/*      */     extends AbstractDoubleSortedSet
/*      */     implements Serializable, DoubleSortedSet
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     double from;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     double to;
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
/*      */     public Subset(double from, boolean bottom, double to, boolean top) {
/* 1079 */       if (!bottom && !top && DoubleRBTreeSet.this.compare(from, to) > 0) {
/* 1080 */         throw new IllegalArgumentException("Start element (" + from + ") is larger than end element (" + to + ")");
/*      */       }
/* 1082 */       this.from = from;
/* 1083 */       this.bottom = bottom;
/* 1084 */       this.to = to;
/* 1085 */       this.top = top;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1089 */       SubsetIterator i = new SubsetIterator();
/* 1090 */       while (i.hasNext()) {
/* 1091 */         i.nextDouble();
/* 1092 */         i.remove();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean in(double k) {
/* 1103 */       return ((this.bottom || DoubleRBTreeSet.this.compare(k, this.from) >= 0) && (this.top || DoubleRBTreeSet.this
/* 1104 */         .compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(double k) {
/* 1109 */       return (in(k) && DoubleRBTreeSet.this.contains(k));
/*      */     }
/*      */     
/*      */     public boolean add(double k) {
/* 1113 */       if (!in(k))
/* 1114 */         throw new IllegalArgumentException("Element (" + k + ") out of range [" + (
/* 1115 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1116 */       return DoubleRBTreeSet.this.add(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(double k) {
/* 1121 */       if (!in(k))
/* 1122 */         return false; 
/* 1123 */       return DoubleRBTreeSet.this.remove(k);
/*      */     }
/*      */     
/*      */     public int size() {
/* 1127 */       SubsetIterator i = new SubsetIterator();
/* 1128 */       int n = 0;
/* 1129 */       while (i.hasNext()) {
/* 1130 */         n++;
/* 1131 */         i.nextDouble();
/*      */       } 
/* 1133 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1137 */       return !(new SubsetIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1141 */       return DoubleRBTreeSet.this.actualComparator;
/*      */     }
/*      */     
/*      */     public DoubleBidirectionalIterator iterator() {
/* 1145 */       return new SubsetIterator();
/*      */     }
/*      */     
/*      */     public DoubleBidirectionalIterator iterator(double from) {
/* 1149 */       return new SubsetIterator(from);
/*      */     }
/*      */     
/*      */     public DoubleSortedSet headSet(double to) {
/* 1153 */       if (this.top)
/* 1154 */         return new Subset(this.from, this.bottom, to, false); 
/* 1155 */       return (DoubleRBTreeSet.this.compare(to, this.to) < 0) ? new Subset(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public DoubleSortedSet tailSet(double from) {
/* 1159 */       if (this.bottom)
/* 1160 */         return new Subset(from, false, this.to, this.top); 
/* 1161 */       return (DoubleRBTreeSet.this.compare(from, this.from) > 0) ? new Subset(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public DoubleSortedSet subSet(double from, double to) {
/* 1165 */       if (this.top && this.bottom)
/* 1166 */         return new Subset(from, false, to, false); 
/* 1167 */       if (!this.top)
/* 1168 */         to = (DoubleRBTreeSet.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1169 */       if (!this.bottom)
/* 1170 */         from = (DoubleRBTreeSet.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1171 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1172 */         return this; 
/* 1173 */       return new Subset(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DoubleRBTreeSet.Entry firstEntry() {
/*      */       DoubleRBTreeSet.Entry e;
/* 1182 */       if (DoubleRBTreeSet.this.tree == null) {
/* 1183 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1187 */       if (this.bottom) {
/* 1188 */         e = DoubleRBTreeSet.this.firstEntry;
/*      */       } else {
/* 1190 */         e = DoubleRBTreeSet.this.locateKey(this.from);
/*      */         
/* 1192 */         if (DoubleRBTreeSet.this.compare(e.key, this.from) < 0) {
/* 1193 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1197 */       if (e == null || (!this.top && DoubleRBTreeSet.this.compare(e.key, this.to) >= 0))
/* 1198 */         return null; 
/* 1199 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DoubleRBTreeSet.Entry lastEntry() {
/*      */       DoubleRBTreeSet.Entry e;
/* 1208 */       if (DoubleRBTreeSet.this.tree == null) {
/* 1209 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1213 */       if (this.top) {
/* 1214 */         e = DoubleRBTreeSet.this.lastEntry;
/*      */       } else {
/* 1216 */         e = DoubleRBTreeSet.this.locateKey(this.to);
/*      */         
/* 1218 */         if (DoubleRBTreeSet.this.compare(e.key, this.to) >= 0) {
/* 1219 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1223 */       if (e == null || (!this.bottom && DoubleRBTreeSet.this.compare(e.key, this.from) < 0))
/* 1224 */         return null; 
/* 1225 */       return e;
/*      */     }
/*      */     
/*      */     public double firstDouble() {
/* 1229 */       DoubleRBTreeSet.Entry e = firstEntry();
/* 1230 */       if (e == null)
/* 1231 */         throw new NoSuchElementException(); 
/* 1232 */       return e.key;
/*      */     }
/*      */     
/*      */     public double lastDouble() {
/* 1236 */       DoubleRBTreeSet.Entry e = lastEntry();
/* 1237 */       if (e == null)
/* 1238 */         throw new NoSuchElementException(); 
/* 1239 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubsetIterator
/*      */       extends DoubleRBTreeSet.SetIterator
/*      */     {
/*      */       SubsetIterator() {
/* 1252 */         this.next = DoubleRBTreeSet.Subset.this.firstEntry();
/*      */       }
/*      */       SubsetIterator(double k) {
/* 1255 */         this();
/* 1256 */         if (this.next != null)
/* 1257 */           if (!DoubleRBTreeSet.Subset.this.bottom && DoubleRBTreeSet.this.compare(k, this.next.key) < 0) {
/* 1258 */             this.prev = null;
/* 1259 */           } else if (!DoubleRBTreeSet.Subset.this.top && DoubleRBTreeSet.this.compare(k, (this.prev = DoubleRBTreeSet.Subset.this.lastEntry()).key) >= 0) {
/* 1260 */             this.next = null;
/*      */           } else {
/* 1262 */             this.next = DoubleRBTreeSet.this.locateKey(k);
/* 1263 */             if (DoubleRBTreeSet.this.compare(this.next.key, k) <= 0) {
/* 1264 */               this.prev = this.next;
/* 1265 */               this.next = this.next.next();
/*      */             } else {
/* 1267 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1273 */         this.prev = this.prev.prev();
/* 1274 */         if (!DoubleRBTreeSet.Subset.this.bottom && this.prev != null && DoubleRBTreeSet.this.compare(this.prev.key, DoubleRBTreeSet.Subset.this.from) < 0)
/* 1275 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1279 */         this.next = this.next.next();
/* 1280 */         if (!DoubleRBTreeSet.Subset.this.top && this.next != null && DoubleRBTreeSet.this.compare(this.next.key, DoubleRBTreeSet.Subset.this.to) >= 0) {
/* 1281 */           this.next = null;
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
/*      */     DoubleRBTreeSet c;
/*      */     try {
/* 1300 */       c = (DoubleRBTreeSet)super.clone();
/* 1301 */     } catch (CloneNotSupportedException cantHappen) {
/* 1302 */       throw new InternalError();
/*      */     } 
/* 1304 */     c.allocatePaths();
/* 1305 */     if (this.count != 0) {
/*      */       
/* 1307 */       Entry rp = new Entry(), rq = new Entry();
/* 1308 */       Entry p = rp;
/* 1309 */       rp.left(this.tree);
/* 1310 */       Entry q = rq;
/* 1311 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1313 */         if (!p.pred()) {
/* 1314 */           Entry e = p.left.clone();
/* 1315 */           e.pred(q.left);
/* 1316 */           e.succ(q);
/* 1317 */           q.left(e);
/* 1318 */           p = p.left;
/* 1319 */           q = q.left;
/*      */         } else {
/* 1321 */           while (p.succ()) {
/* 1322 */             p = p.right;
/* 1323 */             if (p == null) {
/* 1324 */               q.right = null;
/* 1325 */               c.tree = rq.left;
/* 1326 */               c.firstEntry = c.tree;
/* 1327 */               while (c.firstEntry.left != null)
/* 1328 */                 c.firstEntry = c.firstEntry.left; 
/* 1329 */               c.lastEntry = c.tree;
/* 1330 */               while (c.lastEntry.right != null)
/* 1331 */                 c.lastEntry = c.lastEntry.right; 
/* 1332 */               return c;
/*      */             } 
/* 1334 */             q = q.right;
/*      */           } 
/* 1336 */           p = p.right;
/* 1337 */           q = q.right;
/*      */         } 
/* 1339 */         if (!p.succ()) {
/* 1340 */           Entry e = p.right.clone();
/* 1341 */           e.succ(q.right);
/* 1342 */           e.pred(q);
/* 1343 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1347 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1350 */     int n = this.count;
/* 1351 */     SetIterator i = new SetIterator();
/* 1352 */     s.defaultWriteObject();
/* 1353 */     while (n-- != 0) {
/* 1354 */       s.writeDouble(i.nextDouble());
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
/*      */   private Entry readTree(ObjectInputStream s, int n, Entry pred, Entry succ) throws IOException, ClassNotFoundException {
/* 1374 */     if (n == 1) {
/* 1375 */       Entry entry = new Entry(s.readDouble());
/* 1376 */       entry.pred(pred);
/* 1377 */       entry.succ(succ);
/* 1378 */       entry.black(true);
/* 1379 */       return entry;
/*      */     } 
/* 1381 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1386 */       Entry entry = new Entry(s.readDouble());
/* 1387 */       entry.black(true);
/* 1388 */       entry.right(new Entry(s.readDouble()));
/* 1389 */       entry.right.pred(entry);
/* 1390 */       entry.pred(pred);
/* 1391 */       entry.right.succ(succ);
/* 1392 */       return entry;
/*      */     } 
/*      */     
/* 1395 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1396 */     Entry top = new Entry();
/* 1397 */     top.left(readTree(s, leftN, pred, top));
/* 1398 */     top.key = s.readDouble();
/* 1399 */     top.black(true);
/* 1400 */     top.right(readTree(s, rightN, top, succ));
/* 1401 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1402 */       top.right.black(false); 
/* 1403 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1406 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1411 */     setActualComparator();
/* 1412 */     allocatePaths();
/* 1413 */     if (this.count != 0) {
/* 1414 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1416 */       Entry e = this.tree;
/* 1417 */       while (e.left() != null)
/* 1418 */         e = e.left(); 
/* 1419 */       this.firstEntry = e;
/* 1420 */       e = this.tree;
/* 1421 */       while (e.right() != null)
/* 1422 */         e = e.right(); 
/* 1423 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleRBTreeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */