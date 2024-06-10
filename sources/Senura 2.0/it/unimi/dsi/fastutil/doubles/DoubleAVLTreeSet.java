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
/*      */ public class DoubleAVLTreeSet
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
/*      */   
/*      */   public DoubleAVLTreeSet() {
/*   54 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   60 */     this.tree = null;
/*   61 */     this.count = 0;
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
/*   73 */     this.actualComparator = DoubleComparators.asDoubleComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleAVLTreeSet(Comparator<? super Double> c) {
/*   82 */     this();
/*   83 */     this.storedComparator = c;
/*   84 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleAVLTreeSet(Collection<? extends Double> c) {
/*   93 */     this();
/*   94 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleAVLTreeSet(SortedSet<Double> s) {
/*  104 */     this(s.comparator());
/*  105 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleAVLTreeSet(DoubleCollection c) {
/*  114 */     this();
/*  115 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleAVLTreeSet(DoubleSortedSet s) {
/*  125 */     this(s.comparator());
/*  126 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleAVLTreeSet(DoubleIterator i) {
/*      */     allocatePaths();
/*  135 */     while (i.hasNext()) {
/*  136 */       add(i.nextDouble());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleAVLTreeSet(Iterator<?> i) {
/*  146 */     this(DoubleIterators.asDoubleIterator(i));
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
/*      */   public DoubleAVLTreeSet(double[] a, int offset, int length, Comparator<? super Double> c) {
/*  162 */     this(c);
/*  163 */     DoubleArrays.ensureOffsetLength(a, offset, length);
/*  164 */     for (int i = 0; i < length; i++) {
/*  165 */       add(a[offset + i]);
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
/*      */   public DoubleAVLTreeSet(double[] a, int offset, int length) {
/*  178 */     this(a, offset, length, (Comparator<? super Double>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleAVLTreeSet(double[] a) {
/*  187 */     this();
/*  188 */     int i = a.length;
/*  189 */     while (i-- != 0) {
/*  190 */       add(a[i]);
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
/*      */   public DoubleAVLTreeSet(double[] a, Comparator<? super Double> c) {
/*  202 */     this(c);
/*  203 */     int i = a.length;
/*  204 */     while (i-- != 0) {
/*  205 */       add(a[i]);
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
/*  233 */     return (this.actualComparator == null) ? Double.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*  245 */     Entry e = this.tree;
/*      */     int cmp;
/*  247 */     while (e != null && (cmp = compare(k, e.key)) != 0)
/*  248 */       e = (cmp < 0) ? e.left() : e.right(); 
/*  249 */     return e;
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
/*  261 */     Entry e = this.tree, last = this.tree;
/*  262 */     int cmp = 0;
/*  263 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  264 */       last = e;
/*  265 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  267 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  275 */     this.dirPath = new boolean[48];
/*      */   }
/*      */   
/*      */   public boolean add(double k) {
/*  279 */     if (this.tree == null) {
/*  280 */       this.count++;
/*  281 */       this.tree = this.lastEntry = this.firstEntry = new Entry(k);
/*      */     } else {
/*  283 */       Entry p = this.tree, q = null, y = this.tree, z = null, e = null, w = null;
/*  284 */       int i = 0; while (true) {
/*      */         int cmp;
/*  286 */         if ((cmp = compare(k, p.key)) == 0)
/*  287 */           return false; 
/*  288 */         if (p.balance() != 0) {
/*  289 */           i = 0;
/*  290 */           z = q;
/*  291 */           y = p;
/*      */         } 
/*  293 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  294 */           if (p.succ()) {
/*  295 */             this.count++;
/*  296 */             e = new Entry(k);
/*  297 */             if (p.right == null)
/*  298 */               this.lastEntry = e; 
/*  299 */             e.left = p;
/*  300 */             e.right = p.right;
/*  301 */             p.right(e);
/*      */             break;
/*      */           } 
/*  304 */           q = p;
/*  305 */           p = p.right; continue;
/*      */         } 
/*  307 */         if (p.pred()) {
/*  308 */           this.count++;
/*  309 */           e = new Entry(k);
/*  310 */           if (p.left == null)
/*  311 */             this.firstEntry = e; 
/*  312 */           e.right = p;
/*  313 */           e.left = p.left;
/*  314 */           p.left(e);
/*      */           break;
/*      */         } 
/*  317 */         q = p;
/*  318 */         p = p.left;
/*      */       } 
/*      */       
/*  321 */       p = y;
/*  322 */       i = 0;
/*  323 */       while (p != e) {
/*  324 */         if (this.dirPath[i]) {
/*  325 */           p.incBalance();
/*      */         } else {
/*  327 */           p.decBalance();
/*  328 */         }  p = this.dirPath[i++] ? p.right : p.left;
/*      */       } 
/*  330 */       if (y.balance() == -2) {
/*  331 */         Entry x = y.left;
/*  332 */         if (x.balance() == -1) {
/*  333 */           w = x;
/*  334 */           if (x.succ()) {
/*  335 */             x.succ(false);
/*  336 */             y.pred(x);
/*      */           } else {
/*  338 */             y.left = x.right;
/*  339 */           }  x.right = y;
/*  340 */           x.balance(0);
/*  341 */           y.balance(0);
/*      */         } else {
/*  343 */           assert x.balance() == 1;
/*  344 */           w = x.right;
/*  345 */           x.right = w.left;
/*  346 */           w.left = x;
/*  347 */           y.left = w.right;
/*  348 */           w.right = y;
/*  349 */           if (w.balance() == -1) {
/*  350 */             x.balance(0);
/*  351 */             y.balance(1);
/*  352 */           } else if (w.balance() == 0) {
/*  353 */             x.balance(0);
/*  354 */             y.balance(0);
/*      */           } else {
/*  356 */             x.balance(-1);
/*  357 */             y.balance(0);
/*      */           } 
/*  359 */           w.balance(0);
/*  360 */           if (w.pred()) {
/*  361 */             x.succ(w);
/*  362 */             w.pred(false);
/*      */           } 
/*  364 */           if (w.succ()) {
/*  365 */             y.pred(w);
/*  366 */             w.succ(false);
/*      */           } 
/*      */         } 
/*  369 */       } else if (y.balance() == 2) {
/*  370 */         Entry x = y.right;
/*  371 */         if (x.balance() == 1) {
/*  372 */           w = x;
/*  373 */           if (x.pred()) {
/*  374 */             x.pred(false);
/*  375 */             y.succ(x);
/*      */           } else {
/*  377 */             y.right = x.left;
/*  378 */           }  x.left = y;
/*  379 */           x.balance(0);
/*  380 */           y.balance(0);
/*      */         } else {
/*  382 */           assert x.balance() == -1;
/*  383 */           w = x.left;
/*  384 */           x.left = w.right;
/*  385 */           w.right = x;
/*  386 */           y.right = w.left;
/*  387 */           w.left = y;
/*  388 */           if (w.balance() == 1) {
/*  389 */             x.balance(0);
/*  390 */             y.balance(-1);
/*  391 */           } else if (w.balance() == 0) {
/*  392 */             x.balance(0);
/*  393 */             y.balance(0);
/*      */           } else {
/*  395 */             x.balance(1);
/*  396 */             y.balance(0);
/*      */           } 
/*  398 */           w.balance(0);
/*  399 */           if (w.pred()) {
/*  400 */             y.succ(w);
/*  401 */             w.pred(false);
/*      */           } 
/*  403 */           if (w.succ()) {
/*  404 */             x.pred(w);
/*  405 */             w.succ(false);
/*      */           } 
/*      */         } 
/*      */       } else {
/*  409 */         return true;
/*  410 */       }  if (z == null) {
/*  411 */         this.tree = w;
/*      */       }
/*  413 */       else if (z.left == y) {
/*  414 */         z.left = w;
/*      */       } else {
/*  416 */         z.right = w;
/*      */       } 
/*      */     } 
/*  419 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry parent(Entry e) {
/*  429 */     if (e == this.tree) {
/*  430 */       return null;
/*      */     }
/*  432 */     Entry y = e, x = y;
/*      */     while (true) {
/*  434 */       if (y.succ()) {
/*  435 */         Entry p = y.right;
/*  436 */         if (p == null || p.left != e) {
/*  437 */           while (!x.pred())
/*  438 */             x = x.left; 
/*  439 */           p = x.left;
/*      */         } 
/*  441 */         return p;
/*  442 */       }  if (x.pred()) {
/*  443 */         Entry p = x.left;
/*  444 */         if (p == null || p.right != e) {
/*  445 */           while (!y.succ())
/*  446 */             y = y.right; 
/*  447 */           p = y.right;
/*      */         } 
/*  449 */         return p;
/*      */       } 
/*  451 */       x = x.left;
/*  452 */       y = y.right;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(double k) {
/*  458 */     if (this.tree == null) {
/*  459 */       return false;
/*      */     }
/*  461 */     Entry p = this.tree, q = null;
/*  462 */     boolean dir = false;
/*  463 */     double kk = k;
/*      */     int cmp;
/*  465 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  467 */       if (dir = (cmp > 0)) {
/*  468 */         q = p;
/*  469 */         if ((p = p.right()) == null)
/*  470 */           return false;  continue;
/*      */       } 
/*  472 */       q = p;
/*  473 */       if ((p = p.left()) == null) {
/*  474 */         return false;
/*      */       }
/*      */     } 
/*  477 */     if (p.left == null)
/*  478 */       this.firstEntry = p.next(); 
/*  479 */     if (p.right == null)
/*  480 */       this.lastEntry = p.prev(); 
/*  481 */     if (p.succ())
/*  482 */     { if (p.pred())
/*  483 */       { if (q != null)
/*  484 */         { if (dir) {
/*  485 */             q.succ(p.right);
/*      */           } else {
/*  487 */             q.pred(p.left);
/*      */           }  }
/*  489 */         else { this.tree = dir ? p.right : p.left; }
/*      */          }
/*  491 */       else { (p.prev()).right = p.right;
/*  492 */         if (q != null)
/*  493 */         { if (dir) {
/*  494 */             q.right = p.left;
/*      */           } else {
/*  496 */             q.left = p.left;
/*      */           }  }
/*  498 */         else { this.tree = p.left; }
/*      */          }
/*      */        }
/*  501 */     else { Entry r = p.right;
/*  502 */       if (r.pred()) {
/*  503 */         r.left = p.left;
/*  504 */         r.pred(p.pred());
/*  505 */         if (!r.pred())
/*  506 */           (r.prev()).right = r; 
/*  507 */         if (q != null)
/*  508 */         { if (dir) {
/*  509 */             q.right = r;
/*      */           } else {
/*  511 */             q.left = r;
/*      */           }  }
/*  513 */         else { this.tree = r; }
/*  514 */          r.balance(p.balance());
/*  515 */         q = r;
/*  516 */         dir = true;
/*      */       } else {
/*      */         Entry s;
/*      */         while (true) {
/*  520 */           s = r.left;
/*  521 */           if (s.pred())
/*      */             break; 
/*  523 */           r = s;
/*      */         } 
/*  525 */         if (s.succ()) {
/*  526 */           r.pred(s);
/*      */         } else {
/*  528 */           r.left = s.right;
/*  529 */         }  s.left = p.left;
/*  530 */         if (!p.pred()) {
/*  531 */           (p.prev()).right = s;
/*  532 */           s.pred(false);
/*      */         } 
/*  534 */         s.right = p.right;
/*  535 */         s.succ(false);
/*  536 */         if (q != null)
/*  537 */         { if (dir) {
/*  538 */             q.right = s;
/*      */           } else {
/*  540 */             q.left = s;
/*      */           }  }
/*  542 */         else { this.tree = s; }
/*  543 */          s.balance(p.balance());
/*  544 */         q = r;
/*  545 */         dir = false;
/*      */       }  }
/*      */ 
/*      */     
/*  549 */     while (q != null) {
/*  550 */       Entry y = q;
/*  551 */       q = parent(y);
/*  552 */       if (!dir) {
/*  553 */         dir = (q != null && q.left != y);
/*  554 */         y.incBalance();
/*  555 */         if (y.balance() == 1)
/*      */           break; 
/*  557 */         if (y.balance() == 2) {
/*  558 */           Entry x = y.right;
/*  559 */           assert x != null;
/*  560 */           if (x.balance() == -1) {
/*      */             
/*  562 */             assert x.balance() == -1;
/*  563 */             Entry w = x.left;
/*  564 */             x.left = w.right;
/*  565 */             w.right = x;
/*  566 */             y.right = w.left;
/*  567 */             w.left = y;
/*  568 */             if (w.balance() == 1) {
/*  569 */               x.balance(0);
/*  570 */               y.balance(-1);
/*  571 */             } else if (w.balance() == 0) {
/*  572 */               x.balance(0);
/*  573 */               y.balance(0);
/*      */             } else {
/*  575 */               assert w.balance() == -1;
/*  576 */               x.balance(1);
/*  577 */               y.balance(0);
/*      */             } 
/*  579 */             w.balance(0);
/*  580 */             if (w.pred()) {
/*  581 */               y.succ(w);
/*  582 */               w.pred(false);
/*      */             } 
/*  584 */             if (w.succ()) {
/*  585 */               x.pred(w);
/*  586 */               w.succ(false);
/*      */             } 
/*  588 */             if (q != null) {
/*  589 */               if (dir) {
/*  590 */                 q.right = w; continue;
/*      */               } 
/*  592 */               q.left = w; continue;
/*      */             } 
/*  594 */             this.tree = w; continue;
/*      */           } 
/*  596 */           if (q != null)
/*  597 */           { if (dir) {
/*  598 */               q.right = x;
/*      */             } else {
/*  600 */               q.left = x;
/*      */             }  }
/*  602 */           else { this.tree = x; }
/*  603 */            if (x.balance() == 0) {
/*  604 */             y.right = x.left;
/*  605 */             x.left = y;
/*  606 */             x.balance(-1);
/*  607 */             y.balance(1);
/*      */             break;
/*      */           } 
/*  610 */           assert x.balance() == 1;
/*  611 */           if (x.pred()) {
/*  612 */             y.succ(true);
/*  613 */             x.pred(false);
/*      */           } else {
/*  615 */             y.right = x.left;
/*  616 */           }  x.left = y;
/*  617 */           y.balance(0);
/*  618 */           x.balance(0);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  622 */       dir = (q != null && q.left != y);
/*  623 */       y.decBalance();
/*  624 */       if (y.balance() == -1)
/*      */         break; 
/*  626 */       if (y.balance() == -2) {
/*  627 */         Entry x = y.left;
/*  628 */         assert x != null;
/*  629 */         if (x.balance() == 1) {
/*      */           
/*  631 */           assert x.balance() == 1;
/*  632 */           Entry w = x.right;
/*  633 */           x.right = w.left;
/*  634 */           w.left = x;
/*  635 */           y.left = w.right;
/*  636 */           w.right = y;
/*  637 */           if (w.balance() == -1) {
/*  638 */             x.balance(0);
/*  639 */             y.balance(1);
/*  640 */           } else if (w.balance() == 0) {
/*  641 */             x.balance(0);
/*  642 */             y.balance(0);
/*      */           } else {
/*  644 */             assert w.balance() == 1;
/*  645 */             x.balance(-1);
/*  646 */             y.balance(0);
/*      */           } 
/*  648 */           w.balance(0);
/*  649 */           if (w.pred()) {
/*  650 */             x.succ(w);
/*  651 */             w.pred(false);
/*      */           } 
/*  653 */           if (w.succ()) {
/*  654 */             y.pred(w);
/*  655 */             w.succ(false);
/*      */           } 
/*  657 */           if (q != null) {
/*  658 */             if (dir) {
/*  659 */               q.right = w; continue;
/*      */             } 
/*  661 */             q.left = w; continue;
/*      */           } 
/*  663 */           this.tree = w; continue;
/*      */         } 
/*  665 */         if (q != null)
/*  666 */         { if (dir) {
/*  667 */             q.right = x;
/*      */           } else {
/*  669 */             q.left = x;
/*      */           }  }
/*  671 */         else { this.tree = x; }
/*  672 */          if (x.balance() == 0) {
/*  673 */           y.left = x.right;
/*  674 */           x.right = y;
/*  675 */           x.balance(1);
/*  676 */           y.balance(-1);
/*      */           break;
/*      */         } 
/*  679 */         assert x.balance() == -1;
/*  680 */         if (x.succ()) {
/*  681 */           y.pred(true);
/*  682 */           x.succ(false);
/*      */         } else {
/*  684 */           y.left = x.right;
/*  685 */         }  x.right = y;
/*  686 */         y.balance(0);
/*  687 */         x.balance(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  692 */     this.count--;
/*  693 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean contains(double k) {
/*  698 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public void clear() {
/*  702 */     this.count = 0;
/*  703 */     this.tree = null;
/*  704 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry
/*      */     implements Cloneable
/*      */   {
/*      */     private static final int SUCC_MASK = -2147483648;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int PRED_MASK = 1073741824;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int BALANCE_MASK = 255;
/*      */ 
/*      */ 
/*      */     
/*      */     double key;
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left;
/*      */ 
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
/*  745 */       this.key = k;
/*  746 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left() {
/*  754 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right() {
/*  762 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  770 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  778 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  787 */       if (pred) {
/*  788 */         this.info |= 0x40000000;
/*      */       } else {
/*  790 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  799 */       if (succ) {
/*  800 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  802 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry pred) {
/*  811 */       this.info |= 0x40000000;
/*  812 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry succ) {
/*  821 */       this.info |= Integer.MIN_VALUE;
/*  822 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry left) {
/*  831 */       this.info &= 0xBFFFFFFF;
/*  832 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry right) {
/*  841 */       this.info &= Integer.MAX_VALUE;
/*  842 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int balance() {
/*  850 */       return (byte)this.info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void balance(int level) {
/*  859 */       this.info &= 0xFFFFFF00;
/*  860 */       this.info |= level & 0xFF;
/*      */     }
/*      */     
/*      */     void incBalance() {
/*  864 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
/*      */     }
/*      */     
/*      */     protected void decBalance() {
/*  868 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry next() {
/*  876 */       Entry next = this.right;
/*  877 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  878 */         while ((next.info & 0x40000000) == 0)
/*  879 */           next = next.left;  
/*  880 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry prev() {
/*  888 */       Entry prev = this.left;
/*  889 */       if ((this.info & 0x40000000) == 0)
/*  890 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  891 */           prev = prev.right;  
/*  892 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry clone() {
/*      */       Entry c;
/*      */       try {
/*  899 */         c = (Entry)super.clone();
/*  900 */       } catch (CloneNotSupportedException cantHappen) {
/*  901 */         throw new InternalError();
/*      */       } 
/*  903 */       c.key = this.key;
/*  904 */       c.info = this.info;
/*  905 */       return c;
/*      */     }
/*      */     public boolean equals(Object o) {
/*  908 */       if (!(o instanceof Entry))
/*  909 */         return false; 
/*  910 */       Entry e = (Entry)o;
/*  911 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(e.key));
/*      */     }
/*      */     public int hashCode() {
/*  914 */       return HashCommon.double2int(this.key);
/*      */     }
/*      */     public String toString() {
/*  917 */       return String.valueOf(this.key);
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
/*      */   public int size() {
/*  937 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  941 */     return (this.count == 0);
/*      */   }
/*      */   
/*      */   public double firstDouble() {
/*  945 */     if (this.tree == null)
/*  946 */       throw new NoSuchElementException(); 
/*  947 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public double lastDouble() {
/*  951 */     if (this.tree == null)
/*  952 */       throw new NoSuchElementException(); 
/*  953 */     return this.lastEntry.key;
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
/*      */     DoubleAVLTreeSet.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DoubleAVLTreeSet.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DoubleAVLTreeSet.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  984 */     int index = 0;
/*      */     SetIterator() {
/*  986 */       this.next = DoubleAVLTreeSet.this.firstEntry;
/*      */     }
/*      */     SetIterator(double k) {
/*  989 */       if ((this.next = DoubleAVLTreeSet.this.locateKey(k)) != null)
/*  990 */         if (DoubleAVLTreeSet.this.compare(this.next.key, k) <= 0) {
/*  991 */           this.prev = this.next;
/*  992 */           this.next = this.next.next();
/*      */         } else {
/*  994 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  999 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1003 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1006 */       this.next = this.next.next();
/*      */     }
/*      */     DoubleAVLTreeSet.Entry nextEntry() {
/* 1009 */       if (!hasNext())
/* 1010 */         throw new NoSuchElementException(); 
/* 1011 */       this.curr = this.prev = this.next;
/* 1012 */       this.index++;
/* 1013 */       updateNext();
/* 1014 */       return this.curr;
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/* 1018 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1022 */       return (previousEntry()).key;
/*      */     }
/*      */     void updatePrevious() {
/* 1025 */       this.prev = this.prev.prev();
/*      */     }
/*      */     DoubleAVLTreeSet.Entry previousEntry() {
/* 1028 */       if (!hasPrevious())
/* 1029 */         throw new NoSuchElementException(); 
/* 1030 */       this.curr = this.next = this.prev;
/* 1031 */       this.index--;
/* 1032 */       updatePrevious();
/* 1033 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1037 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1041 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1045 */       if (this.curr == null) {
/* 1046 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1051 */       if (this.curr == this.prev)
/* 1052 */         this.index--; 
/* 1053 */       this.next = this.prev = this.curr;
/* 1054 */       updatePrevious();
/* 1055 */       updateNext();
/* 1056 */       DoubleAVLTreeSet.this.remove(this.curr.key);
/* 1057 */       this.curr = null;
/*      */     }
/*      */   }
/*      */   
/*      */   public DoubleBidirectionalIterator iterator() {
/* 1062 */     return new SetIterator();
/*      */   }
/*      */   
/*      */   public DoubleBidirectionalIterator iterator(double from) {
/* 1066 */     return new SetIterator(from);
/*      */   }
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1070 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public DoubleSortedSet headSet(double to) {
/* 1074 */     return new Subset(0.0D, true, to, false);
/*      */   }
/*      */   
/*      */   public DoubleSortedSet tailSet(double from) {
/* 1078 */     return new Subset(from, false, 0.0D, true);
/*      */   }
/*      */   
/*      */   public DoubleSortedSet subSet(double from, double to) {
/* 1082 */     return new Subset(from, false, to, false);
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
/* 1120 */       if (!bottom && !top && DoubleAVLTreeSet.this.compare(from, to) > 0) {
/* 1121 */         throw new IllegalArgumentException("Start element (" + from + ") is larger than end element (" + to + ")");
/*      */       }
/* 1123 */       this.from = from;
/* 1124 */       this.bottom = bottom;
/* 1125 */       this.to = to;
/* 1126 */       this.top = top;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1130 */       SubsetIterator i = new SubsetIterator();
/* 1131 */       while (i.hasNext()) {
/* 1132 */         i.nextDouble();
/* 1133 */         i.remove();
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
/* 1144 */       return ((this.bottom || DoubleAVLTreeSet.this.compare(k, this.from) >= 0) && (this.top || DoubleAVLTreeSet.this
/* 1145 */         .compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(double k) {
/* 1150 */       return (in(k) && DoubleAVLTreeSet.this.contains(k));
/*      */     }
/*      */     
/*      */     public boolean add(double k) {
/* 1154 */       if (!in(k))
/* 1155 */         throw new IllegalArgumentException("Element (" + k + ") out of range [" + (
/* 1156 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1157 */       return DoubleAVLTreeSet.this.add(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(double k) {
/* 1162 */       if (!in(k))
/* 1163 */         return false; 
/* 1164 */       return DoubleAVLTreeSet.this.remove(k);
/*      */     }
/*      */     
/*      */     public int size() {
/* 1168 */       SubsetIterator i = new SubsetIterator();
/* 1169 */       int n = 0;
/* 1170 */       while (i.hasNext()) {
/* 1171 */         n++;
/* 1172 */         i.nextDouble();
/*      */       } 
/* 1174 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1178 */       return !(new SubsetIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1182 */       return DoubleAVLTreeSet.this.actualComparator;
/*      */     }
/*      */     
/*      */     public DoubleBidirectionalIterator iterator() {
/* 1186 */       return new SubsetIterator();
/*      */     }
/*      */     
/*      */     public DoubleBidirectionalIterator iterator(double from) {
/* 1190 */       return new SubsetIterator(from);
/*      */     }
/*      */     
/*      */     public DoubleSortedSet headSet(double to) {
/* 1194 */       if (this.top)
/* 1195 */         return new Subset(this.from, this.bottom, to, false); 
/* 1196 */       return (DoubleAVLTreeSet.this.compare(to, this.to) < 0) ? new Subset(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public DoubleSortedSet tailSet(double from) {
/* 1200 */       if (this.bottom)
/* 1201 */         return new Subset(from, false, this.to, this.top); 
/* 1202 */       return (DoubleAVLTreeSet.this.compare(from, this.from) > 0) ? new Subset(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public DoubleSortedSet subSet(double from, double to) {
/* 1206 */       if (this.top && this.bottom)
/* 1207 */         return new Subset(from, false, to, false); 
/* 1208 */       if (!this.top)
/* 1209 */         to = (DoubleAVLTreeSet.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1210 */       if (!this.bottom)
/* 1211 */         from = (DoubleAVLTreeSet.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1212 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1213 */         return this; 
/* 1214 */       return new Subset(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DoubleAVLTreeSet.Entry firstEntry() {
/*      */       DoubleAVLTreeSet.Entry e;
/* 1223 */       if (DoubleAVLTreeSet.this.tree == null) {
/* 1224 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1228 */       if (this.bottom) {
/* 1229 */         e = DoubleAVLTreeSet.this.firstEntry;
/*      */       } else {
/* 1231 */         e = DoubleAVLTreeSet.this.locateKey(this.from);
/*      */         
/* 1233 */         if (DoubleAVLTreeSet.this.compare(e.key, this.from) < 0) {
/* 1234 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1238 */       if (e == null || (!this.top && DoubleAVLTreeSet.this.compare(e.key, this.to) >= 0))
/* 1239 */         return null; 
/* 1240 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DoubleAVLTreeSet.Entry lastEntry() {
/*      */       DoubleAVLTreeSet.Entry e;
/* 1249 */       if (DoubleAVLTreeSet.this.tree == null) {
/* 1250 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1254 */       if (this.top) {
/* 1255 */         e = DoubleAVLTreeSet.this.lastEntry;
/*      */       } else {
/* 1257 */         e = DoubleAVLTreeSet.this.locateKey(this.to);
/*      */         
/* 1259 */         if (DoubleAVLTreeSet.this.compare(e.key, this.to) >= 0) {
/* 1260 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1264 */       if (e == null || (!this.bottom && DoubleAVLTreeSet.this.compare(e.key, this.from) < 0))
/* 1265 */         return null; 
/* 1266 */       return e;
/*      */     }
/*      */     
/*      */     public double firstDouble() {
/* 1270 */       DoubleAVLTreeSet.Entry e = firstEntry();
/* 1271 */       if (e == null)
/* 1272 */         throw new NoSuchElementException(); 
/* 1273 */       return e.key;
/*      */     }
/*      */     
/*      */     public double lastDouble() {
/* 1277 */       DoubleAVLTreeSet.Entry e = lastEntry();
/* 1278 */       if (e == null)
/* 1279 */         throw new NoSuchElementException(); 
/* 1280 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubsetIterator
/*      */       extends DoubleAVLTreeSet.SetIterator
/*      */     {
/*      */       SubsetIterator() {
/* 1293 */         this.next = DoubleAVLTreeSet.Subset.this.firstEntry();
/*      */       }
/*      */       SubsetIterator(double k) {
/* 1296 */         this();
/* 1297 */         if (this.next != null)
/* 1298 */           if (!DoubleAVLTreeSet.Subset.this.bottom && DoubleAVLTreeSet.this.compare(k, this.next.key) < 0) {
/* 1299 */             this.prev = null;
/* 1300 */           } else if (!DoubleAVLTreeSet.Subset.this.top && DoubleAVLTreeSet.this.compare(k, (this.prev = DoubleAVLTreeSet.Subset.this.lastEntry()).key) >= 0) {
/* 1301 */             this.next = null;
/*      */           } else {
/* 1303 */             this.next = DoubleAVLTreeSet.this.locateKey(k);
/* 1304 */             if (DoubleAVLTreeSet.this.compare(this.next.key, k) <= 0) {
/* 1305 */               this.prev = this.next;
/* 1306 */               this.next = this.next.next();
/*      */             } else {
/* 1308 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1314 */         this.prev = this.prev.prev();
/* 1315 */         if (!DoubleAVLTreeSet.Subset.this.bottom && this.prev != null && DoubleAVLTreeSet.this.compare(this.prev.key, DoubleAVLTreeSet.Subset.this.from) < 0)
/* 1316 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1320 */         this.next = this.next.next();
/* 1321 */         if (!DoubleAVLTreeSet.Subset.this.top && this.next != null && DoubleAVLTreeSet.this.compare(this.next.key, DoubleAVLTreeSet.Subset.this.to) >= 0) {
/* 1322 */           this.next = null;
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
/*      */     DoubleAVLTreeSet c;
/*      */     try {
/* 1341 */       c = (DoubleAVLTreeSet)super.clone();
/* 1342 */     } catch (CloneNotSupportedException cantHappen) {
/* 1343 */       throw new InternalError();
/*      */     } 
/* 1345 */     c.allocatePaths();
/* 1346 */     if (this.count != 0) {
/*      */       
/* 1348 */       Entry rp = new Entry(), rq = new Entry();
/* 1349 */       Entry p = rp;
/* 1350 */       rp.left(this.tree);
/* 1351 */       Entry q = rq;
/* 1352 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1354 */         if (!p.pred()) {
/* 1355 */           Entry e = p.left.clone();
/* 1356 */           e.pred(q.left);
/* 1357 */           e.succ(q);
/* 1358 */           q.left(e);
/* 1359 */           p = p.left;
/* 1360 */           q = q.left;
/*      */         } else {
/* 1362 */           while (p.succ()) {
/* 1363 */             p = p.right;
/* 1364 */             if (p == null) {
/* 1365 */               q.right = null;
/* 1366 */               c.tree = rq.left;
/* 1367 */               c.firstEntry = c.tree;
/* 1368 */               while (c.firstEntry.left != null)
/* 1369 */                 c.firstEntry = c.firstEntry.left; 
/* 1370 */               c.lastEntry = c.tree;
/* 1371 */               while (c.lastEntry.right != null)
/* 1372 */                 c.lastEntry = c.lastEntry.right; 
/* 1373 */               return c;
/*      */             } 
/* 1375 */             q = q.right;
/*      */           } 
/* 1377 */           p = p.right;
/* 1378 */           q = q.right;
/*      */         } 
/* 1380 */         if (!p.succ()) {
/* 1381 */           Entry e = p.right.clone();
/* 1382 */           e.succ(q.right);
/* 1383 */           e.pred(q);
/* 1384 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1388 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1391 */     int n = this.count;
/* 1392 */     SetIterator i = new SetIterator();
/* 1393 */     s.defaultWriteObject();
/* 1394 */     while (n-- != 0) {
/* 1395 */       s.writeDouble(i.nextDouble());
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
/* 1415 */     if (n == 1) {
/* 1416 */       Entry entry = new Entry(s.readDouble());
/* 1417 */       entry.pred(pred);
/* 1418 */       entry.succ(succ);
/* 1419 */       return entry;
/*      */     } 
/* 1421 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1426 */       Entry entry = new Entry(s.readDouble());
/* 1427 */       entry.right(new Entry(s.readDouble()));
/* 1428 */       entry.right.pred(entry);
/* 1429 */       entry.balance(1);
/* 1430 */       entry.pred(pred);
/* 1431 */       entry.right.succ(succ);
/* 1432 */       return entry;
/*      */     } 
/*      */     
/* 1435 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1436 */     Entry top = new Entry();
/* 1437 */     top.left(readTree(s, leftN, pred, top));
/* 1438 */     top.key = s.readDouble();
/* 1439 */     top.right(readTree(s, rightN, top, succ));
/* 1440 */     if (n == (n & -n))
/* 1441 */       top.balance(1); 
/* 1442 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1445 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1450 */     setActualComparator();
/* 1451 */     allocatePaths();
/* 1452 */     if (this.count != 0) {
/* 1453 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1455 */       Entry e = this.tree;
/* 1456 */       while (e.left() != null)
/* 1457 */         e = e.left(); 
/* 1458 */       this.firstEntry = e;
/* 1459 */       e = this.tree;
/* 1460 */       while (e.right() != null)
/* 1461 */         e = e.right(); 
/* 1462 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleAVLTreeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */