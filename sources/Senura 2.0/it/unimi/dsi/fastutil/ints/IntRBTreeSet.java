/*      */ package it.unimi.dsi.fastutil.ints;
/*      */ 
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
/*      */ public class IntRBTreeSet
/*      */   extends AbstractIntSortedSet
/*      */   implements Serializable, Cloneable, IntSortedSet
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected Comparator<? super Integer> storedComparator;
/*      */   protected transient IntComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353130L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public IntRBTreeSet() {
/*   51 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   57 */     this.tree = null;
/*   58 */     this.count = 0;
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
/*   70 */     this.actualComparator = IntComparators.asIntComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntRBTreeSet(Comparator<? super Integer> c) {
/*   79 */     this();
/*   80 */     this.storedComparator = c;
/*   81 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntRBTreeSet(Collection<? extends Integer> c) {
/*   90 */     this();
/*   91 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntRBTreeSet(SortedSet<Integer> s) {
/*  101 */     this(s.comparator());
/*  102 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntRBTreeSet(IntCollection c) {
/*  111 */     this();
/*  112 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntRBTreeSet(IntSortedSet s) {
/*  122 */     this(s.comparator());
/*  123 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntRBTreeSet(IntIterator i) {
/*      */     allocatePaths();
/*  132 */     while (i.hasNext()) {
/*  133 */       add(i.nextInt());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntRBTreeSet(Iterator<?> i) {
/*  143 */     this(IntIterators.asIntIterator(i));
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
/*      */   public IntRBTreeSet(int[] a, int offset, int length, Comparator<? super Integer> c) {
/*  159 */     this(c);
/*  160 */     IntArrays.ensureOffsetLength(a, offset, length);
/*  161 */     for (int i = 0; i < length; i++) {
/*  162 */       add(a[offset + i]);
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
/*      */   public IntRBTreeSet(int[] a, int offset, int length) {
/*  175 */     this(a, offset, length, (Comparator<? super Integer>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntRBTreeSet(int[] a) {
/*  184 */     this();
/*  185 */     int i = a.length;
/*  186 */     while (i-- != 0) {
/*  187 */       add(a[i]);
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
/*      */   public IntRBTreeSet(int[] a, Comparator<? super Integer> c) {
/*  199 */     this(c);
/*  200 */     int i = a.length;
/*  201 */     while (i-- != 0) {
/*  202 */       add(a[i]);
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
/*      */   final int compare(int k1, int k2) {
/*  230 */     return (this.actualComparator == null) ? Integer.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   private Entry findKey(int k) {
/*  242 */     Entry e = this.tree;
/*      */     int cmp;
/*  244 */     while (e != null && (cmp = compare(k, e.key)) != 0)
/*  245 */       e = (cmp < 0) ? e.left() : e.right(); 
/*  246 */     return e;
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
/*      */   final Entry locateKey(int k) {
/*  258 */     Entry e = this.tree, last = this.tree;
/*  259 */     int cmp = 0;
/*  260 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  261 */       last = e;
/*  262 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  264 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  274 */     this.dirPath = new boolean[64];
/*  275 */     this.nodePath = new Entry[64];
/*      */   }
/*      */   
/*      */   public boolean add(int k) {
/*  279 */     int maxDepth = 0;
/*  280 */     if (this.tree == null) {
/*  281 */       this.count++;
/*  282 */       this.tree = this.lastEntry = this.firstEntry = new Entry(k);
/*      */     } else {
/*  284 */       Entry p = this.tree;
/*  285 */       int i = 0; while (true) {
/*      */         int cmp;
/*  287 */         if ((cmp = compare(k, p.key)) == 0) {
/*      */           
/*  289 */           while (i-- != 0)
/*  290 */             this.nodePath[i] = null; 
/*  291 */           return false;
/*      */         } 
/*  293 */         this.nodePath[i] = p;
/*  294 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  295 */           if (p.succ()) {
/*  296 */             this.count++;
/*  297 */             Entry e = new Entry(k);
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
/*  309 */           Entry e = new Entry(k);
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
/*  320 */       maxDepth = i--;
/*  321 */       while (i > 0 && !this.nodePath[i].black()) {
/*  322 */         if (!this.dirPath[i - 1]) {
/*  323 */           Entry entry1 = (this.nodePath[i - 1]).right;
/*  324 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  325 */             this.nodePath[i].black(true);
/*  326 */             entry1.black(true);
/*  327 */             this.nodePath[i - 1].black(false);
/*  328 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  331 */           if (!this.dirPath[i]) {
/*  332 */             entry1 = this.nodePath[i];
/*      */           } else {
/*  334 */             Entry entry = this.nodePath[i];
/*  335 */             entry1 = entry.right;
/*  336 */             entry.right = entry1.left;
/*  337 */             entry1.left = entry;
/*  338 */             (this.nodePath[i - 1]).left = entry1;
/*  339 */             if (entry1.pred()) {
/*  340 */               entry1.pred(false);
/*  341 */               entry.succ(entry1);
/*      */             } 
/*      */           } 
/*  344 */           Entry entry2 = this.nodePath[i - 1];
/*  345 */           entry2.black(false);
/*  346 */           entry1.black(true);
/*  347 */           entry2.left = entry1.right;
/*  348 */           entry1.right = entry2;
/*  349 */           if (i < 2) {
/*  350 */             this.tree = entry1;
/*      */           }
/*  352 */           else if (this.dirPath[i - 2]) {
/*  353 */             (this.nodePath[i - 2]).right = entry1;
/*      */           } else {
/*  355 */             (this.nodePath[i - 2]).left = entry1;
/*      */           } 
/*  357 */           if (entry1.succ()) {
/*  358 */             entry1.succ(false);
/*  359 */             entry2.pred(entry1);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  364 */         Entry y = (this.nodePath[i - 1]).left;
/*  365 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  366 */           this.nodePath[i].black(true);
/*  367 */           y.black(true);
/*  368 */           this.nodePath[i - 1].black(false);
/*  369 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  372 */         if (this.dirPath[i]) {
/*  373 */           y = this.nodePath[i];
/*      */         } else {
/*  375 */           Entry entry = this.nodePath[i];
/*  376 */           y = entry.left;
/*  377 */           entry.left = y.right;
/*  378 */           y.right = entry;
/*  379 */           (this.nodePath[i - 1]).right = y;
/*  380 */           if (y.succ()) {
/*  381 */             y.succ(false);
/*  382 */             entry.pred(y);
/*      */           } 
/*      */         } 
/*  385 */         Entry x = this.nodePath[i - 1];
/*  386 */         x.black(false);
/*  387 */         y.black(true);
/*  388 */         x.right = y.left;
/*  389 */         y.left = x;
/*  390 */         if (i < 2) {
/*  391 */           this.tree = y;
/*      */         }
/*  393 */         else if (this.dirPath[i - 2]) {
/*  394 */           (this.nodePath[i - 2]).right = y;
/*      */         } else {
/*  396 */           (this.nodePath[i - 2]).left = y;
/*      */         } 
/*  398 */         if (y.pred()) {
/*  399 */           y.pred(false);
/*  400 */           x.succ(y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  407 */     this.tree.black(true);
/*      */     
/*  409 */     while (maxDepth-- != 0)
/*  410 */       this.nodePath[maxDepth] = null; 
/*  411 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(int k) {
/*  416 */     if (this.tree == null)
/*  417 */       return false; 
/*  418 */     Entry p = this.tree;
/*      */     
/*  420 */     int i = 0;
/*  421 */     int kk = k;
/*      */     int cmp;
/*  423 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  425 */       this.dirPath[i] = (cmp > 0);
/*  426 */       this.nodePath[i] = p;
/*  427 */       if (this.dirPath[i++]) {
/*  428 */         if ((p = p.right()) == null) {
/*      */           
/*  430 */           while (i-- != 0)
/*  431 */             this.nodePath[i] = null; 
/*  432 */           return false;
/*      */         }  continue;
/*      */       } 
/*  435 */       if ((p = p.left()) == null) {
/*      */         
/*  437 */         while (i-- != 0)
/*  438 */           this.nodePath[i] = null; 
/*  439 */         return false;
/*      */       } 
/*      */     } 
/*      */     
/*  443 */     if (p.left == null)
/*  444 */       this.firstEntry = p.next(); 
/*  445 */     if (p.right == null)
/*  446 */       this.lastEntry = p.prev(); 
/*  447 */     if (p.succ()) {
/*  448 */       if (p.pred()) {
/*  449 */         if (i == 0) {
/*  450 */           this.tree = p.left;
/*      */         }
/*  452 */         else if (this.dirPath[i - 1]) {
/*  453 */           this.nodePath[i - 1].succ(p.right);
/*      */         } else {
/*  455 */           this.nodePath[i - 1].pred(p.left);
/*      */         } 
/*      */       } else {
/*  458 */         (p.prev()).right = p.right;
/*  459 */         if (i == 0) {
/*  460 */           this.tree = p.left;
/*      */         }
/*  462 */         else if (this.dirPath[i - 1]) {
/*  463 */           (this.nodePath[i - 1]).right = p.left;
/*      */         } else {
/*  465 */           (this.nodePath[i - 1]).left = p.left;
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  470 */       Entry r = p.right;
/*  471 */       if (r.pred()) {
/*  472 */         r.left = p.left;
/*  473 */         r.pred(p.pred());
/*  474 */         if (!r.pred())
/*  475 */           (r.prev()).right = r; 
/*  476 */         if (i == 0) {
/*  477 */           this.tree = r;
/*      */         }
/*  479 */         else if (this.dirPath[i - 1]) {
/*  480 */           (this.nodePath[i - 1]).right = r;
/*      */         } else {
/*  482 */           (this.nodePath[i - 1]).left = r;
/*      */         } 
/*  484 */         boolean color = r.black();
/*  485 */         r.black(p.black());
/*  486 */         p.black(color);
/*  487 */         this.dirPath[i] = true;
/*  488 */         this.nodePath[i++] = r;
/*      */       } else {
/*      */         Entry s;
/*  491 */         int j = i++;
/*      */         while (true) {
/*  493 */           this.dirPath[i] = false;
/*  494 */           this.nodePath[i++] = r;
/*  495 */           s = r.left;
/*  496 */           if (s.pred())
/*      */             break; 
/*  498 */           r = s;
/*      */         } 
/*  500 */         this.dirPath[j] = true;
/*  501 */         this.nodePath[j] = s;
/*  502 */         if (s.succ()) {
/*  503 */           r.pred(s);
/*      */         } else {
/*  505 */           r.left = s.right;
/*  506 */         }  s.left = p.left;
/*  507 */         if (!p.pred()) {
/*  508 */           (p.prev()).right = s;
/*  509 */           s.pred(false);
/*      */         } 
/*  511 */         s.right(p.right);
/*  512 */         boolean color = s.black();
/*  513 */         s.black(p.black());
/*  514 */         p.black(color);
/*  515 */         if (j == 0) {
/*  516 */           this.tree = s;
/*      */         }
/*  518 */         else if (this.dirPath[j - 1]) {
/*  519 */           (this.nodePath[j - 1]).right = s;
/*      */         } else {
/*  521 */           (this.nodePath[j - 1]).left = s;
/*      */         } 
/*      */       } 
/*      */     } 
/*  525 */     int maxDepth = i;
/*  526 */     if (p.black()) {
/*  527 */       for (; i > 0; i--) {
/*  528 */         if ((this.dirPath[i - 1] && !this.nodePath[i - 1].succ()) || (!this.dirPath[i - 1] && !this.nodePath[i - 1].pred())) {
/*  529 */           Entry x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  530 */           if (!x.black()) {
/*  531 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  535 */         if (!this.dirPath[i - 1]) {
/*  536 */           Entry w = (this.nodePath[i - 1]).right;
/*  537 */           if (!w.black()) {
/*  538 */             w.black(true);
/*  539 */             this.nodePath[i - 1].black(false);
/*  540 */             (this.nodePath[i - 1]).right = w.left;
/*  541 */             w.left = this.nodePath[i - 1];
/*  542 */             if (i < 2) {
/*  543 */               this.tree = w;
/*      */             }
/*  545 */             else if (this.dirPath[i - 2]) {
/*  546 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  548 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  550 */             this.nodePath[i] = this.nodePath[i - 1];
/*  551 */             this.dirPath[i] = false;
/*  552 */             this.nodePath[i - 1] = w;
/*  553 */             if (maxDepth == i++)
/*  554 */               maxDepth++; 
/*  555 */             w = (this.nodePath[i - 1]).right;
/*      */           } 
/*  557 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  558 */             w.black(false);
/*      */           } else {
/*  560 */             if (w.succ() || w.right.black()) {
/*  561 */               Entry y = w.left;
/*  562 */               y.black(true);
/*  563 */               w.black(false);
/*  564 */               w.left = y.right;
/*  565 */               y.right = w;
/*  566 */               w = (this.nodePath[i - 1]).right = y;
/*  567 */               if (w.succ()) {
/*  568 */                 w.succ(false);
/*  569 */                 w.right.pred(w);
/*      */               } 
/*      */             } 
/*  572 */             w.black(this.nodePath[i - 1].black());
/*  573 */             this.nodePath[i - 1].black(true);
/*  574 */             w.right.black(true);
/*  575 */             (this.nodePath[i - 1]).right = w.left;
/*  576 */             w.left = this.nodePath[i - 1];
/*  577 */             if (i < 2) {
/*  578 */               this.tree = w;
/*      */             }
/*  580 */             else if (this.dirPath[i - 2]) {
/*  581 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  583 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  585 */             if (w.pred()) {
/*  586 */               w.pred(false);
/*  587 */               this.nodePath[i - 1].succ(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } else {
/*  592 */           Entry w = (this.nodePath[i - 1]).left;
/*  593 */           if (!w.black()) {
/*  594 */             w.black(true);
/*  595 */             this.nodePath[i - 1].black(false);
/*  596 */             (this.nodePath[i - 1]).left = w.right;
/*  597 */             w.right = this.nodePath[i - 1];
/*  598 */             if (i < 2) {
/*  599 */               this.tree = w;
/*      */             }
/*  601 */             else if (this.dirPath[i - 2]) {
/*  602 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  604 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  606 */             this.nodePath[i] = this.nodePath[i - 1];
/*  607 */             this.dirPath[i] = true;
/*  608 */             this.nodePath[i - 1] = w;
/*  609 */             if (maxDepth == i++)
/*  610 */               maxDepth++; 
/*  611 */             w = (this.nodePath[i - 1]).left;
/*      */           } 
/*  613 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  614 */             w.black(false);
/*      */           } else {
/*  616 */             if (w.pred() || w.left.black()) {
/*  617 */               Entry y = w.right;
/*  618 */               y.black(true);
/*  619 */               w.black(false);
/*  620 */               w.right = y.left;
/*  621 */               y.left = w;
/*  622 */               w = (this.nodePath[i - 1]).left = y;
/*  623 */               if (w.pred()) {
/*  624 */                 w.pred(false);
/*  625 */                 w.left.succ(w);
/*      */               } 
/*      */             } 
/*  628 */             w.black(this.nodePath[i - 1].black());
/*  629 */             this.nodePath[i - 1].black(true);
/*  630 */             w.left.black(true);
/*  631 */             (this.nodePath[i - 1]).left = w.right;
/*  632 */             w.right = this.nodePath[i - 1];
/*  633 */             if (i < 2) {
/*  634 */               this.tree = w;
/*      */             }
/*  636 */             else if (this.dirPath[i - 2]) {
/*  637 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  639 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  641 */             if (w.succ()) {
/*  642 */               w.succ(false);
/*  643 */               this.nodePath[i - 1].pred(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  649 */       if (this.tree != null)
/*  650 */         this.tree.black(true); 
/*      */     } 
/*  652 */     this.count--;
/*      */     
/*  654 */     while (maxDepth-- != 0)
/*  655 */       this.nodePath[maxDepth] = null; 
/*  656 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean contains(int k) {
/*  661 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public void clear() {
/*  665 */     this.count = 0;
/*  666 */     this.tree = null;
/*  667 */     this.firstEntry = this.lastEntry = null;
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
/*      */     int key;
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
/*      */     Entry(int k) {
/*  705 */       this.key = k;
/*  706 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left() {
/*  714 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right() {
/*  722 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  730 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  738 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  747 */       if (pred) {
/*  748 */         this.info |= 0x40000000;
/*      */       } else {
/*  750 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  759 */       if (succ) {
/*  760 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  762 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry pred) {
/*  771 */       this.info |= 0x40000000;
/*  772 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry succ) {
/*  781 */       this.info |= Integer.MIN_VALUE;
/*  782 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry left) {
/*  791 */       this.info &= 0xBFFFFFFF;
/*  792 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry right) {
/*  801 */       this.info &= Integer.MAX_VALUE;
/*  802 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean black() {
/*  810 */       return ((this.info & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void black(boolean black) {
/*  819 */       if (black) {
/*  820 */         this.info |= 0x1;
/*      */       } else {
/*  822 */         this.info &= 0xFFFFFFFE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry next() {
/*  830 */       Entry next = this.right;
/*  831 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  832 */         while ((next.info & 0x40000000) == 0)
/*  833 */           next = next.left;  
/*  834 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry prev() {
/*  842 */       Entry prev = this.left;
/*  843 */       if ((this.info & 0x40000000) == 0)
/*  844 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  845 */           prev = prev.right;  
/*  846 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry clone() {
/*      */       Entry c;
/*      */       try {
/*  853 */         c = (Entry)super.clone();
/*  854 */       } catch (CloneNotSupportedException cantHappen) {
/*  855 */         throw new InternalError();
/*      */       } 
/*  857 */       c.key = this.key;
/*  858 */       c.info = this.info;
/*  859 */       return c;
/*      */     }
/*      */     public boolean equals(Object o) {
/*  862 */       if (!(o instanceof Entry))
/*  863 */         return false; 
/*  864 */       Entry e = (Entry)o;
/*  865 */       return (this.key == e.key);
/*      */     }
/*      */     public int hashCode() {
/*  868 */       return this.key;
/*      */     }
/*      */     public String toString() {
/*  871 */       return String.valueOf(this.key);
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
/*  892 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  896 */     return (this.count == 0);
/*      */   }
/*      */   
/*      */   public int firstInt() {
/*  900 */     if (this.tree == null)
/*  901 */       throw new NoSuchElementException(); 
/*  902 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public int lastInt() {
/*  906 */     if (this.tree == null)
/*  907 */       throw new NoSuchElementException(); 
/*  908 */     return this.lastEntry.key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class SetIterator
/*      */     implements IntListIterator
/*      */   {
/*      */     IntRBTreeSet.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     IntRBTreeSet.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     IntRBTreeSet.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  939 */     int index = 0;
/*      */     SetIterator() {
/*  941 */       this.next = IntRBTreeSet.this.firstEntry;
/*      */     }
/*      */     SetIterator(int k) {
/*  944 */       if ((this.next = IntRBTreeSet.this.locateKey(k)) != null)
/*  945 */         if (IntRBTreeSet.this.compare(this.next.key, k) <= 0) {
/*  946 */           this.prev = this.next;
/*  947 */           this.next = this.next.next();
/*      */         } else {
/*  949 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  954 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  958 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/*  961 */       this.next = this.next.next();
/*      */     }
/*      */     void updatePrevious() {
/*  964 */       this.prev = this.prev.prev();
/*      */     }
/*      */     
/*      */     public int nextInt() {
/*  968 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public int previousInt() {
/*  972 */       return (previousEntry()).key;
/*      */     }
/*      */     IntRBTreeSet.Entry nextEntry() {
/*  975 */       if (!hasNext())
/*  976 */         throw new NoSuchElementException(); 
/*  977 */       this.curr = this.prev = this.next;
/*  978 */       this.index++;
/*  979 */       updateNext();
/*  980 */       return this.curr;
/*      */     }
/*      */     IntRBTreeSet.Entry previousEntry() {
/*  983 */       if (!hasPrevious())
/*  984 */         throw new NoSuchElementException(); 
/*  985 */       this.curr = this.next = this.prev;
/*  986 */       this.index--;
/*  987 */       updatePrevious();
/*  988 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  992 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  996 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1000 */       if (this.curr == null) {
/* 1001 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1006 */       if (this.curr == this.prev)
/* 1007 */         this.index--; 
/* 1008 */       this.next = this.prev = this.curr;
/* 1009 */       updatePrevious();
/* 1010 */       updateNext();
/* 1011 */       IntRBTreeSet.this.remove(this.curr.key);
/* 1012 */       this.curr = null;
/*      */     }
/*      */   }
/*      */   
/*      */   public IntBidirectionalIterator iterator() {
/* 1017 */     return new SetIterator();
/*      */   }
/*      */   
/*      */   public IntBidirectionalIterator iterator(int from) {
/* 1021 */     return new SetIterator(from);
/*      */   }
/*      */   
/*      */   public IntComparator comparator() {
/* 1025 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public IntSortedSet headSet(int to) {
/* 1029 */     return new Subset(0, true, to, false);
/*      */   }
/*      */   
/*      */   public IntSortedSet tailSet(int from) {
/* 1033 */     return new Subset(from, false, 0, true);
/*      */   }
/*      */   
/*      */   public IntSortedSet subSet(int from, int to) {
/* 1037 */     return new Subset(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Subset
/*      */     extends AbstractIntSortedSet
/*      */     implements Serializable, IntSortedSet
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int from;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int to;
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
/*      */     public Subset(int from, boolean bottom, int to, boolean top) {
/* 1075 */       if (!bottom && !top && IntRBTreeSet.this.compare(from, to) > 0) {
/* 1076 */         throw new IllegalArgumentException("Start element (" + from + ") is larger than end element (" + to + ")");
/*      */       }
/* 1078 */       this.from = from;
/* 1079 */       this.bottom = bottom;
/* 1080 */       this.to = to;
/* 1081 */       this.top = top;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1085 */       SubsetIterator i = new SubsetIterator();
/* 1086 */       while (i.hasNext()) {
/* 1087 */         i.nextInt();
/* 1088 */         i.remove();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean in(int k) {
/* 1099 */       return ((this.bottom || IntRBTreeSet.this.compare(k, this.from) >= 0) && (this.top || IntRBTreeSet.this.compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(int k) {
/* 1104 */       return (in(k) && IntRBTreeSet.this.contains(k));
/*      */     }
/*      */     
/*      */     public boolean add(int k) {
/* 1108 */       if (!in(k))
/* 1109 */         throw new IllegalArgumentException("Element (" + k + ") out of range [" + (
/* 1110 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1111 */       return IntRBTreeSet.this.add(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(int k) {
/* 1116 */       if (!in(k))
/* 1117 */         return false; 
/* 1118 */       return IntRBTreeSet.this.remove(k);
/*      */     }
/*      */     
/*      */     public int size() {
/* 1122 */       SubsetIterator i = new SubsetIterator();
/* 1123 */       int n = 0;
/* 1124 */       while (i.hasNext()) {
/* 1125 */         n++;
/* 1126 */         i.nextInt();
/*      */       } 
/* 1128 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1132 */       return !(new SubsetIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public IntComparator comparator() {
/* 1136 */       return IntRBTreeSet.this.actualComparator;
/*      */     }
/*      */     
/*      */     public IntBidirectionalIterator iterator() {
/* 1140 */       return new SubsetIterator();
/*      */     }
/*      */     
/*      */     public IntBidirectionalIterator iterator(int from) {
/* 1144 */       return new SubsetIterator(from);
/*      */     }
/*      */     
/*      */     public IntSortedSet headSet(int to) {
/* 1148 */       if (this.top)
/* 1149 */         return new Subset(this.from, this.bottom, to, false); 
/* 1150 */       return (IntRBTreeSet.this.compare(to, this.to) < 0) ? new Subset(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public IntSortedSet tailSet(int from) {
/* 1154 */       if (this.bottom)
/* 1155 */         return new Subset(from, false, this.to, this.top); 
/* 1156 */       return (IntRBTreeSet.this.compare(from, this.from) > 0) ? new Subset(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public IntSortedSet subSet(int from, int to) {
/* 1160 */       if (this.top && this.bottom)
/* 1161 */         return new Subset(from, false, to, false); 
/* 1162 */       if (!this.top)
/* 1163 */         to = (IntRBTreeSet.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1164 */       if (!this.bottom)
/* 1165 */         from = (IntRBTreeSet.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1166 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1167 */         return this; 
/* 1168 */       return new Subset(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public IntRBTreeSet.Entry firstEntry() {
/*      */       IntRBTreeSet.Entry e;
/* 1177 */       if (IntRBTreeSet.this.tree == null) {
/* 1178 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1182 */       if (this.bottom) {
/* 1183 */         e = IntRBTreeSet.this.firstEntry;
/*      */       } else {
/* 1185 */         e = IntRBTreeSet.this.locateKey(this.from);
/*      */         
/* 1187 */         if (IntRBTreeSet.this.compare(e.key, this.from) < 0) {
/* 1188 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1192 */       if (e == null || (!this.top && IntRBTreeSet.this.compare(e.key, this.to) >= 0))
/* 1193 */         return null; 
/* 1194 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public IntRBTreeSet.Entry lastEntry() {
/*      */       IntRBTreeSet.Entry e;
/* 1203 */       if (IntRBTreeSet.this.tree == null) {
/* 1204 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1208 */       if (this.top) {
/* 1209 */         e = IntRBTreeSet.this.lastEntry;
/*      */       } else {
/* 1211 */         e = IntRBTreeSet.this.locateKey(this.to);
/*      */         
/* 1213 */         if (IntRBTreeSet.this.compare(e.key, this.to) >= 0) {
/* 1214 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1218 */       if (e == null || (!this.bottom && IntRBTreeSet.this.compare(e.key, this.from) < 0))
/* 1219 */         return null; 
/* 1220 */       return e;
/*      */     }
/*      */     
/*      */     public int firstInt() {
/* 1224 */       IntRBTreeSet.Entry e = firstEntry();
/* 1225 */       if (e == null)
/* 1226 */         throw new NoSuchElementException(); 
/* 1227 */       return e.key;
/*      */     }
/*      */     
/*      */     public int lastInt() {
/* 1231 */       IntRBTreeSet.Entry e = lastEntry();
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
/*      */       extends IntRBTreeSet.SetIterator
/*      */     {
/*      */       SubsetIterator() {
/* 1247 */         this.next = IntRBTreeSet.Subset.this.firstEntry();
/*      */       }
/*      */       SubsetIterator(int k) {
/* 1250 */         this();
/* 1251 */         if (this.next != null)
/* 1252 */           if (!IntRBTreeSet.Subset.this.bottom && IntRBTreeSet.this.compare(k, this.next.key) < 0) {
/* 1253 */             this.prev = null;
/* 1254 */           } else if (!IntRBTreeSet.Subset.this.top && IntRBTreeSet.this.compare(k, (this.prev = IntRBTreeSet.Subset.this.lastEntry()).key) >= 0) {
/* 1255 */             this.next = null;
/*      */           } else {
/* 1257 */             this.next = IntRBTreeSet.this.locateKey(k);
/* 1258 */             if (IntRBTreeSet.this.compare(this.next.key, k) <= 0) {
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
/* 1269 */         if (!IntRBTreeSet.Subset.this.bottom && this.prev != null && IntRBTreeSet.this.compare(this.prev.key, IntRBTreeSet.Subset.this.from) < 0)
/* 1270 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1274 */         this.next = this.next.next();
/* 1275 */         if (!IntRBTreeSet.Subset.this.top && this.next != null && IntRBTreeSet.this.compare(this.next.key, IntRBTreeSet.Subset.this.to) >= 0) {
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
/*      */     IntRBTreeSet c;
/*      */     try {
/* 1295 */       c = (IntRBTreeSet)super.clone();
/* 1296 */     } catch (CloneNotSupportedException cantHappen) {
/* 1297 */       throw new InternalError();
/*      */     } 
/* 1299 */     c.allocatePaths();
/* 1300 */     if (this.count != 0) {
/*      */       
/* 1302 */       Entry rp = new Entry(), rq = new Entry();
/* 1303 */       Entry p = rp;
/* 1304 */       rp.left(this.tree);
/* 1305 */       Entry q = rq;
/* 1306 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1308 */         if (!p.pred()) {
/* 1309 */           Entry e = p.left.clone();
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
/* 1335 */           Entry e = p.right.clone();
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
/* 1349 */       s.writeInt(i.nextInt());
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
/* 1369 */     if (n == 1) {
/* 1370 */       Entry entry = new Entry(s.readInt());
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
/* 1381 */       Entry entry = new Entry(s.readInt());
/* 1382 */       entry.black(true);
/* 1383 */       entry.right(new Entry(s.readInt()));
/* 1384 */       entry.right.pred(entry);
/* 1385 */       entry.pred(pred);
/* 1386 */       entry.right.succ(succ);
/* 1387 */       return entry;
/*      */     } 
/*      */     
/* 1390 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1391 */     Entry top = new Entry();
/* 1392 */     top.left(readTree(s, leftN, pred, top));
/* 1393 */     top.key = s.readInt();
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
/* 1409 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1411 */       Entry e = this.tree;
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntRBTreeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */