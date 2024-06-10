/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
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
/*      */ public class Double2BooleanAVLTreeMap
/*      */   extends AbstractDouble2BooleanSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Double2BooleanMap.Entry> entries;
/*      */   protected transient DoubleSortedSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Double> storedComparator;
/*      */   protected transient DoubleComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Double2BooleanAVLTreeMap() {
/*   73 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   79 */     this.tree = null;
/*   80 */     this.count = 0;
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
/*   92 */     this.actualComparator = DoubleComparators.asDoubleComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanAVLTreeMap(Comparator<? super Double> c) {
/*  101 */     this();
/*  102 */     this.storedComparator = c;
/*  103 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanAVLTreeMap(Map<? extends Double, ? extends Boolean> m) {
/*  112 */     this();
/*  113 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanAVLTreeMap(SortedMap<Double, Boolean> m) {
/*  123 */     this(m.comparator());
/*  124 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanAVLTreeMap(Double2BooleanMap m) {
/*  133 */     this();
/*  134 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanAVLTreeMap(Double2BooleanSortedMap m) {
/*  144 */     this(m.comparator());
/*  145 */     putAll(m);
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
/*      */   public Double2BooleanAVLTreeMap(double[] k, boolean[] v, Comparator<? super Double> c) {
/*  161 */     this(c);
/*  162 */     if (k.length != v.length) {
/*  163 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  165 */     for (int i = 0; i < k.length; i++) {
/*  166 */       put(k[i], v[i]);
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
/*      */   public Double2BooleanAVLTreeMap(double[] k, boolean[] v) {
/*  179 */     this(k, v, (Comparator<? super Double>)null);
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
/*      */   final int compare(double k1, double k2) {
/*  207 */     return (this.actualComparator == null) ? Double.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry findKey(double k) {
/*  219 */     Entry e = this.tree;
/*      */     int cmp;
/*  221 */     while (e != null && (cmp = compare(k, e.key)) != 0)
/*  222 */       e = (cmp < 0) ? e.left() : e.right(); 
/*  223 */     return e;
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
/*  235 */     Entry e = this.tree, last = this.tree;
/*  236 */     int cmp = 0;
/*  237 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  238 */       last = e;
/*  239 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  241 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  249 */     this.dirPath = new boolean[48];
/*      */   }
/*      */   
/*      */   public boolean put(double k, boolean v) {
/*  253 */     Entry e = add(k);
/*  254 */     boolean oldValue = e.value;
/*  255 */     e.value = v;
/*  256 */     return oldValue;
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
/*      */   private Entry add(double k) {
/*  273 */     this.modified = false;
/*  274 */     Entry e = null;
/*  275 */     if (this.tree == null) {
/*  276 */       this.count++;
/*  277 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry(k, this.defRetValue);
/*  278 */       this.modified = true;
/*      */     } else {
/*  280 */       Entry p = this.tree, q = null, y = this.tree, z = null, w = null;
/*  281 */       int i = 0; while (true) {
/*      */         int cmp;
/*  283 */         if ((cmp = compare(k, p.key)) == 0) {
/*  284 */           return p;
/*      */         }
/*  286 */         if (p.balance() != 0) {
/*  287 */           i = 0;
/*  288 */           z = q;
/*  289 */           y = p;
/*      */         } 
/*  291 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  292 */           if (p.succ()) {
/*  293 */             this.count++;
/*  294 */             e = new Entry(k, this.defRetValue);
/*  295 */             this.modified = true;
/*  296 */             if (p.right == null)
/*  297 */               this.lastEntry = e; 
/*  298 */             e.left = p;
/*  299 */             e.right = p.right;
/*  300 */             p.right(e);
/*      */             break;
/*      */           } 
/*  303 */           q = p;
/*  304 */           p = p.right; continue;
/*      */         } 
/*  306 */         if (p.pred()) {
/*  307 */           this.count++;
/*  308 */           e = new Entry(k, this.defRetValue);
/*  309 */           this.modified = true;
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
/*  409 */         return e;
/*  410 */       }  if (z == null) {
/*  411 */         this.tree = w;
/*      */       }
/*  413 */       else if (z.left == y) {
/*  414 */         z.left = w;
/*      */       } else {
/*  416 */         z.right = w;
/*      */       } 
/*      */     } 
/*  419 */     return e;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k) {
/*  462 */     this.modified = false;
/*  463 */     if (this.tree == null) {
/*  464 */       return this.defRetValue;
/*      */     }
/*  466 */     Entry p = this.tree, q = null;
/*  467 */     boolean dir = false;
/*  468 */     double kk = k;
/*      */     int cmp;
/*  470 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  472 */       if (dir = (cmp > 0)) {
/*  473 */         q = p;
/*  474 */         if ((p = p.right()) == null)
/*  475 */           return this.defRetValue;  continue;
/*      */       } 
/*  477 */       q = p;
/*  478 */       if ((p = p.left()) == null) {
/*  479 */         return this.defRetValue;
/*      */       }
/*      */     } 
/*  482 */     if (p.left == null)
/*  483 */       this.firstEntry = p.next(); 
/*  484 */     if (p.right == null)
/*  485 */       this.lastEntry = p.prev(); 
/*  486 */     if (p.succ())
/*  487 */     { if (p.pred())
/*  488 */       { if (q != null)
/*  489 */         { if (dir) {
/*  490 */             q.succ(p.right);
/*      */           } else {
/*  492 */             q.pred(p.left);
/*      */           }  }
/*  494 */         else { this.tree = dir ? p.right : p.left; }
/*      */          }
/*  496 */       else { (p.prev()).right = p.right;
/*  497 */         if (q != null)
/*  498 */         { if (dir) {
/*  499 */             q.right = p.left;
/*      */           } else {
/*  501 */             q.left = p.left;
/*      */           }  }
/*  503 */         else { this.tree = p.left; }
/*      */          }
/*      */        }
/*  506 */     else { Entry r = p.right;
/*  507 */       if (r.pred()) {
/*  508 */         r.left = p.left;
/*  509 */         r.pred(p.pred());
/*  510 */         if (!r.pred())
/*  511 */           (r.prev()).right = r; 
/*  512 */         if (q != null)
/*  513 */         { if (dir) {
/*  514 */             q.right = r;
/*      */           } else {
/*  516 */             q.left = r;
/*      */           }  }
/*  518 */         else { this.tree = r; }
/*  519 */          r.balance(p.balance());
/*  520 */         q = r;
/*  521 */         dir = true;
/*      */       } else {
/*      */         Entry s;
/*      */         while (true) {
/*  525 */           s = r.left;
/*  526 */           if (s.pred())
/*      */             break; 
/*  528 */           r = s;
/*      */         } 
/*  530 */         if (s.succ()) {
/*  531 */           r.pred(s);
/*      */         } else {
/*  533 */           r.left = s.right;
/*  534 */         }  s.left = p.left;
/*  535 */         if (!p.pred()) {
/*  536 */           (p.prev()).right = s;
/*  537 */           s.pred(false);
/*      */         } 
/*  539 */         s.right = p.right;
/*  540 */         s.succ(false);
/*  541 */         if (q != null)
/*  542 */         { if (dir) {
/*  543 */             q.right = s;
/*      */           } else {
/*  545 */             q.left = s;
/*      */           }  }
/*  547 */         else { this.tree = s; }
/*  548 */          s.balance(p.balance());
/*  549 */         q = r;
/*  550 */         dir = false;
/*      */       }  }
/*      */ 
/*      */     
/*  554 */     while (q != null) {
/*  555 */       Entry y = q;
/*  556 */       q = parent(y);
/*  557 */       if (!dir) {
/*  558 */         dir = (q != null && q.left != y);
/*  559 */         y.incBalance();
/*  560 */         if (y.balance() == 1)
/*      */           break; 
/*  562 */         if (y.balance() == 2) {
/*  563 */           Entry x = y.right;
/*  564 */           assert x != null;
/*  565 */           if (x.balance() == -1) {
/*      */             
/*  567 */             assert x.balance() == -1;
/*  568 */             Entry w = x.left;
/*  569 */             x.left = w.right;
/*  570 */             w.right = x;
/*  571 */             y.right = w.left;
/*  572 */             w.left = y;
/*  573 */             if (w.balance() == 1) {
/*  574 */               x.balance(0);
/*  575 */               y.balance(-1);
/*  576 */             } else if (w.balance() == 0) {
/*  577 */               x.balance(0);
/*  578 */               y.balance(0);
/*      */             } else {
/*  580 */               assert w.balance() == -1;
/*  581 */               x.balance(1);
/*  582 */               y.balance(0);
/*      */             } 
/*  584 */             w.balance(0);
/*  585 */             if (w.pred()) {
/*  586 */               y.succ(w);
/*  587 */               w.pred(false);
/*      */             } 
/*  589 */             if (w.succ()) {
/*  590 */               x.pred(w);
/*  591 */               w.succ(false);
/*      */             } 
/*  593 */             if (q != null) {
/*  594 */               if (dir) {
/*  595 */                 q.right = w; continue;
/*      */               } 
/*  597 */               q.left = w; continue;
/*      */             } 
/*  599 */             this.tree = w; continue;
/*      */           } 
/*  601 */           if (q != null)
/*  602 */           { if (dir) {
/*  603 */               q.right = x;
/*      */             } else {
/*  605 */               q.left = x;
/*      */             }  }
/*  607 */           else { this.tree = x; }
/*  608 */            if (x.balance() == 0) {
/*  609 */             y.right = x.left;
/*  610 */             x.left = y;
/*  611 */             x.balance(-1);
/*  612 */             y.balance(1);
/*      */             break;
/*      */           } 
/*  615 */           assert x.balance() == 1;
/*  616 */           if (x.pred()) {
/*  617 */             y.succ(true);
/*  618 */             x.pred(false);
/*      */           } else {
/*  620 */             y.right = x.left;
/*  621 */           }  x.left = y;
/*  622 */           y.balance(0);
/*  623 */           x.balance(0);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  627 */       dir = (q != null && q.left != y);
/*  628 */       y.decBalance();
/*  629 */       if (y.balance() == -1)
/*      */         break; 
/*  631 */       if (y.balance() == -2) {
/*  632 */         Entry x = y.left;
/*  633 */         assert x != null;
/*  634 */         if (x.balance() == 1) {
/*      */           
/*  636 */           assert x.balance() == 1;
/*  637 */           Entry w = x.right;
/*  638 */           x.right = w.left;
/*  639 */           w.left = x;
/*  640 */           y.left = w.right;
/*  641 */           w.right = y;
/*  642 */           if (w.balance() == -1) {
/*  643 */             x.balance(0);
/*  644 */             y.balance(1);
/*  645 */           } else if (w.balance() == 0) {
/*  646 */             x.balance(0);
/*  647 */             y.balance(0);
/*      */           } else {
/*  649 */             assert w.balance() == 1;
/*  650 */             x.balance(-1);
/*  651 */             y.balance(0);
/*      */           } 
/*  653 */           w.balance(0);
/*  654 */           if (w.pred()) {
/*  655 */             x.succ(w);
/*  656 */             w.pred(false);
/*      */           } 
/*  658 */           if (w.succ()) {
/*  659 */             y.pred(w);
/*  660 */             w.succ(false);
/*      */           } 
/*  662 */           if (q != null) {
/*  663 */             if (dir) {
/*  664 */               q.right = w; continue;
/*      */             } 
/*  666 */             q.left = w; continue;
/*      */           } 
/*  668 */           this.tree = w; continue;
/*      */         } 
/*  670 */         if (q != null)
/*  671 */         { if (dir) {
/*  672 */             q.right = x;
/*      */           } else {
/*  674 */             q.left = x;
/*      */           }  }
/*  676 */         else { this.tree = x; }
/*  677 */          if (x.balance() == 0) {
/*  678 */           y.left = x.right;
/*  679 */           x.right = y;
/*  680 */           x.balance(1);
/*  681 */           y.balance(-1);
/*      */           break;
/*      */         } 
/*  684 */         assert x.balance() == -1;
/*  685 */         if (x.succ()) {
/*  686 */           y.pred(true);
/*  687 */           x.succ(false);
/*      */         } else {
/*  689 */           y.left = x.right;
/*  690 */         }  x.right = y;
/*  691 */         y.balance(0);
/*  692 */         x.balance(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  697 */     this.modified = true;
/*  698 */     this.count--;
/*  699 */     return p.value;
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  703 */     ValueIterator i = new ValueIterator();
/*      */     
/*  705 */     int j = this.count;
/*  706 */     while (j-- != 0) {
/*  707 */       boolean ev = i.nextBoolean();
/*  708 */       if (ev == v)
/*  709 */         return true; 
/*      */     } 
/*  711 */     return false;
/*      */   }
/*      */   
/*      */   public void clear() {
/*  715 */     this.count = 0;
/*  716 */     this.tree = null;
/*  717 */     this.entries = null;
/*  718 */     this.values = null;
/*  719 */     this.keys = null;
/*  720 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry
/*      */     extends AbstractDouble2BooleanMap.BasicEntry
/*      */     implements Cloneable
/*      */   {
/*      */     private static final int SUCC_MASK = -2147483648;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int PRED_MASK = 1073741824;
/*      */ 
/*      */     
/*      */     private static final int BALANCE_MASK = 255;
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
/*      */     Entry() {
/*  751 */       super(0.0D, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(double k, boolean v) {
/*  762 */       super(k, v);
/*  763 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left() {
/*  771 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right() {
/*  779 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  787 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  795 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  804 */       if (pred) {
/*  805 */         this.info |= 0x40000000;
/*      */       } else {
/*  807 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  816 */       if (succ) {
/*  817 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  819 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry pred) {
/*  828 */       this.info |= 0x40000000;
/*  829 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry succ) {
/*  838 */       this.info |= Integer.MIN_VALUE;
/*  839 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry left) {
/*  848 */       this.info &= 0xBFFFFFFF;
/*  849 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry right) {
/*  858 */       this.info &= Integer.MAX_VALUE;
/*  859 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int balance() {
/*  867 */       return (byte)this.info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void balance(int level) {
/*  876 */       this.info &= 0xFFFFFF00;
/*  877 */       this.info |= level & 0xFF;
/*      */     }
/*      */     
/*      */     void incBalance() {
/*  881 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
/*      */     }
/*      */     
/*      */     protected void decBalance() {
/*  885 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry next() {
/*  893 */       Entry next = this.right;
/*  894 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  895 */         while ((next.info & 0x40000000) == 0)
/*  896 */           next = next.left;  
/*  897 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry prev() {
/*  905 */       Entry prev = this.left;
/*  906 */       if ((this.info & 0x40000000) == 0)
/*  907 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  908 */           prev = prev.right;  
/*  909 */       return prev;
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean value) {
/*  913 */       boolean oldValue = this.value;
/*  914 */       this.value = value;
/*  915 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry clone() {
/*      */       Entry c;
/*      */       try {
/*  922 */         c = (Entry)super.clone();
/*  923 */       } catch (CloneNotSupportedException cantHappen) {
/*  924 */         throw new InternalError();
/*      */       } 
/*  926 */       c.key = this.key;
/*  927 */       c.value = this.value;
/*  928 */       c.info = this.info;
/*  929 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  934 */       if (!(o instanceof Map.Entry))
/*  935 */         return false; 
/*  936 */       Map.Entry<Double, Boolean> e = (Map.Entry<Double, Boolean>)o;
/*  937 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && this.value == ((Boolean)e
/*  938 */         .getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  942 */       return HashCommon.double2int(this.key) ^ (this.value ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  946 */       return this.key + "=>" + this.value;
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
/*      */   public boolean containsKey(double k) {
/*  967 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  971 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  975 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean get(double k) {
/*  980 */     Entry e = findKey(k);
/*  981 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public double firstDoubleKey() {
/*  985 */     if (this.tree == null)
/*  986 */       throw new NoSuchElementException(); 
/*  987 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public double lastDoubleKey() {
/*  991 */     if (this.tree == null)
/*  992 */       throw new NoSuchElementException(); 
/*  993 */     return this.lastEntry.key;
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
/*      */     Double2BooleanAVLTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2BooleanAVLTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2BooleanAVLTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1024 */     int index = 0;
/*      */     TreeIterator() {
/* 1026 */       this.next = Double2BooleanAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(double k) {
/* 1029 */       if ((this.next = Double2BooleanAVLTreeMap.this.locateKey(k)) != null)
/* 1030 */         if (Double2BooleanAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1031 */           this.prev = this.next;
/* 1032 */           this.next = this.next.next();
/*      */         } else {
/* 1034 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/* 1038 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1041 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1044 */       this.next = this.next.next();
/*      */     }
/*      */     Double2BooleanAVLTreeMap.Entry nextEntry() {
/* 1047 */       if (!hasNext())
/* 1048 */         throw new NoSuchElementException(); 
/* 1049 */       this.curr = this.prev = this.next;
/* 1050 */       this.index++;
/* 1051 */       updateNext();
/* 1052 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1055 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Double2BooleanAVLTreeMap.Entry previousEntry() {
/* 1058 */       if (!hasPrevious())
/* 1059 */         throw new NoSuchElementException(); 
/* 1060 */       this.curr = this.next = this.prev;
/* 1061 */       this.index--;
/* 1062 */       updatePrevious();
/* 1063 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1066 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1069 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1072 */       if (this.curr == null) {
/* 1073 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1078 */       if (this.curr == this.prev)
/* 1079 */         this.index--; 
/* 1080 */       this.next = this.prev = this.curr;
/* 1081 */       updatePrevious();
/* 1082 */       updateNext();
/* 1083 */       Double2BooleanAVLTreeMap.this.remove(this.curr.key);
/* 1084 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1087 */       int i = n;
/* 1088 */       while (i-- != 0 && hasNext())
/* 1089 */         nextEntry(); 
/* 1090 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1093 */       int i = n;
/* 1094 */       while (i-- != 0 && hasPrevious())
/* 1095 */         previousEntry(); 
/* 1096 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Double2BooleanMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(double k) {
/* 1109 */       super(k);
/*      */     }
/*      */     
/*      */     public Double2BooleanMap.Entry next() {
/* 1113 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Double2BooleanMap.Entry previous() {
/* 1117 */       return previousEntry();
/*      */     }
/*      */     
/*      */     public void set(Double2BooleanMap.Entry ok) {
/* 1121 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Double2BooleanMap.Entry ok) {
/* 1125 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Double2BooleanMap.Entry> double2BooleanEntrySet() {
/* 1130 */     if (this.entries == null)
/* 1131 */       this.entries = (ObjectSortedSet<Double2BooleanMap.Entry>)new AbstractObjectSortedSet<Double2BooleanMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Double2BooleanMap.Entry> comparator;
/*      */ 
/*      */           
/*      */           public Comparator<? super Double2BooleanMap.Entry> comparator() {
/* 1137 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2BooleanMap.Entry> iterator() {
/* 1141 */             return (ObjectBidirectionalIterator<Double2BooleanMap.Entry>)new Double2BooleanAVLTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2BooleanMap.Entry> iterator(Double2BooleanMap.Entry from) {
/* 1146 */             return (ObjectBidirectionalIterator<Double2BooleanMap.Entry>)new Double2BooleanAVLTreeMap.EntryIterator(from.getDoubleKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1151 */             if (!(o instanceof Map.Entry))
/* 1152 */               return false; 
/* 1153 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1154 */             if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1155 */               return false; 
/* 1156 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1157 */               return false; 
/* 1158 */             Double2BooleanAVLTreeMap.Entry f = Double2BooleanAVLTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1159 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1164 */             if (!(o instanceof Map.Entry))
/* 1165 */               return false; 
/* 1166 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1167 */             if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1168 */               return false; 
/* 1169 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1170 */               return false; 
/* 1171 */             Double2BooleanAVLTreeMap.Entry f = Double2BooleanAVLTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1172 */             if (f == null || f.getBooleanValue() != ((Boolean)e.getValue()).booleanValue())
/* 1173 */               return false; 
/* 1174 */             Double2BooleanAVLTreeMap.this.remove(f.key);
/* 1175 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1179 */             return Double2BooleanAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1183 */             Double2BooleanAVLTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Double2BooleanMap.Entry first() {
/* 1187 */             return Double2BooleanAVLTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Double2BooleanMap.Entry last() {
/* 1191 */             return Double2BooleanAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Double2BooleanMap.Entry> subSet(Double2BooleanMap.Entry from, Double2BooleanMap.Entry to) {
/* 1196 */             return Double2BooleanAVLTreeMap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Double2BooleanMap.Entry> headSet(Double2BooleanMap.Entry to) {
/* 1200 */             return Double2BooleanAVLTreeMap.this.headMap(to.getDoubleKey()).double2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Double2BooleanMap.Entry> tailSet(Double2BooleanMap.Entry from) {
/* 1204 */             return Double2BooleanAVLTreeMap.this.tailMap(from.getDoubleKey()).double2BooleanEntrySet();
/*      */           }
/*      */         }; 
/* 1207 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends TreeIterator
/*      */     implements DoubleListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(double k) {
/* 1223 */       super(k);
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/* 1227 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1231 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractDouble2BooleanSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public DoubleBidirectionalIterator iterator() {
/* 1238 */       return new Double2BooleanAVLTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public DoubleBidirectionalIterator iterator(double from) {
/* 1242 */       return new Double2BooleanAVLTreeMap.KeyIterator(from);
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
/*      */   public DoubleSortedSet keySet() {
/* 1257 */     if (this.keys == null)
/* 1258 */       this.keys = new KeySet(); 
/* 1259 */     return this.keys;
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
/* 1274 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public boolean previousBoolean() {
/* 1278 */       return (previousEntry()).value;
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
/* 1293 */     if (this.values == null)
/* 1294 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1297 */             return (BooleanIterator)new Double2BooleanAVLTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(boolean k) {
/* 1301 */             return Double2BooleanAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1305 */             return Double2BooleanAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1309 */             Double2BooleanAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1312 */     return this.values;
/*      */   }
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1316 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Double2BooleanSortedMap headMap(double to) {
/* 1320 */     return new Submap(0.0D, true, to, false);
/*      */   }
/*      */   
/*      */   public Double2BooleanSortedMap tailMap(double from) {
/* 1324 */     return new Submap(from, false, 0.0D, true);
/*      */   }
/*      */   
/*      */   public Double2BooleanSortedMap subMap(double from, double to) {
/* 1328 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractDouble2BooleanSortedMap
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     double from;
/*      */ 
/*      */ 
/*      */     
/*      */     double to;
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
/*      */     protected transient ObjectSortedSet<Double2BooleanMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient DoubleSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(double from, boolean bottom, double to, boolean top) {
/* 1372 */       if (!bottom && !top && Double2BooleanAVLTreeMap.this.compare(from, to) > 0)
/* 1373 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1374 */       this.from = from;
/* 1375 */       this.bottom = bottom;
/* 1376 */       this.to = to;
/* 1377 */       this.top = top;
/* 1378 */       this.defRetValue = Double2BooleanAVLTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1382 */       SubmapIterator i = new SubmapIterator();
/* 1383 */       while (i.hasNext()) {
/* 1384 */         i.nextEntry();
/* 1385 */         i.remove();
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
/* 1396 */       return ((this.bottom || Double2BooleanAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Double2BooleanAVLTreeMap.this
/* 1397 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2BooleanMap.Entry> double2BooleanEntrySet() {
/* 1401 */       if (this.entries == null)
/* 1402 */         this.entries = (ObjectSortedSet<Double2BooleanMap.Entry>)new AbstractObjectSortedSet<Double2BooleanMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Double2BooleanMap.Entry> iterator() {
/* 1405 */               return (ObjectBidirectionalIterator<Double2BooleanMap.Entry>)new Double2BooleanAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Double2BooleanMap.Entry> iterator(Double2BooleanMap.Entry from) {
/* 1410 */               return (ObjectBidirectionalIterator<Double2BooleanMap.Entry>)new Double2BooleanAVLTreeMap.Submap.SubmapEntryIterator(from.getDoubleKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Double2BooleanMap.Entry> comparator() {
/* 1414 */               return Double2BooleanAVLTreeMap.this.double2BooleanEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1419 */               if (!(o instanceof Map.Entry))
/* 1420 */                 return false; 
/* 1421 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1422 */               if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1423 */                 return false; 
/* 1424 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1425 */                 return false; 
/* 1426 */               Double2BooleanAVLTreeMap.Entry f = Double2BooleanAVLTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1427 */               return (f != null && Double2BooleanAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1432 */               if (!(o instanceof Map.Entry))
/* 1433 */                 return false; 
/* 1434 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1435 */               if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1436 */                 return false; 
/* 1437 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1438 */                 return false; 
/* 1439 */               Double2BooleanAVLTreeMap.Entry f = Double2BooleanAVLTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1440 */               if (f != null && Double2BooleanAVLTreeMap.Submap.this.in(f.key))
/* 1441 */                 Double2BooleanAVLTreeMap.Submap.this.remove(f.key); 
/* 1442 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1446 */               int c = 0;
/* 1447 */               for (ObjectBidirectionalIterator<Double2BooleanMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1448 */                 c++; 
/* 1449 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1453 */               return !(new Double2BooleanAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1457 */               Double2BooleanAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Double2BooleanMap.Entry first() {
/* 1461 */               return Double2BooleanAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Double2BooleanMap.Entry last() {
/* 1465 */               return Double2BooleanAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Double2BooleanMap.Entry> subSet(Double2BooleanMap.Entry from, Double2BooleanMap.Entry to) {
/* 1470 */               return Double2BooleanAVLTreeMap.Submap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Double2BooleanMap.Entry> headSet(Double2BooleanMap.Entry to) {
/* 1474 */               return Double2BooleanAVLTreeMap.Submap.this.headMap(to.getDoubleKey()).double2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Double2BooleanMap.Entry> tailSet(Double2BooleanMap.Entry from) {
/* 1478 */               return Double2BooleanAVLTreeMap.Submap.this.tailMap(from.getDoubleKey()).double2BooleanEntrySet();
/*      */             }
/*      */           }; 
/* 1481 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractDouble2BooleanSortedMap.KeySet {
/*      */       public DoubleBidirectionalIterator iterator() {
/* 1486 */         return new Double2BooleanAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public DoubleBidirectionalIterator iterator(double from) {
/* 1490 */         return new Double2BooleanAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public DoubleSortedSet keySet() {
/* 1495 */       if (this.keys == null)
/* 1496 */         this.keys = new KeySet(); 
/* 1497 */       return this.keys;
/*      */     }
/*      */     
/*      */     public BooleanCollection values() {
/* 1501 */       if (this.values == null)
/* 1502 */         this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */           {
/*      */             public BooleanIterator iterator() {
/* 1505 */               return (BooleanIterator)new Double2BooleanAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(boolean k) {
/* 1509 */               return Double2BooleanAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1513 */               return Double2BooleanAVLTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1517 */               Double2BooleanAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1520 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(double k) {
/* 1525 */       return (in(k) && Double2BooleanAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(boolean v) {
/* 1529 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1531 */       while (i.hasNext()) {
/* 1532 */         boolean ev = (i.nextEntry()).value;
/* 1533 */         if (ev == v)
/* 1534 */           return true; 
/*      */       } 
/* 1536 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean get(double k) {
/* 1542 */       double kk = k; Double2BooleanAVLTreeMap.Entry e;
/* 1543 */       return (in(kk) && (e = Double2BooleanAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public boolean put(double k, boolean v) {
/* 1547 */       Double2BooleanAVLTreeMap.this.modified = false;
/* 1548 */       if (!in(k))
/* 1549 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1550 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1551 */       boolean oldValue = Double2BooleanAVLTreeMap.this.put(k, v);
/* 1552 */       return Double2BooleanAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(double k) {
/* 1557 */       Double2BooleanAVLTreeMap.this.modified = false;
/* 1558 */       if (!in(k))
/* 1559 */         return this.defRetValue; 
/* 1560 */       boolean oldValue = Double2BooleanAVLTreeMap.this.remove(k);
/* 1561 */       return Double2BooleanAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1565 */       SubmapIterator i = new SubmapIterator();
/* 1566 */       int n = 0;
/* 1567 */       while (i.hasNext()) {
/* 1568 */         n++;
/* 1569 */         i.nextEntry();
/*      */       } 
/* 1571 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1575 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1579 */       return Double2BooleanAVLTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Double2BooleanSortedMap headMap(double to) {
/* 1583 */       if (this.top)
/* 1584 */         return new Submap(this.from, this.bottom, to, false); 
/* 1585 */       return (Double2BooleanAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Double2BooleanSortedMap tailMap(double from) {
/* 1589 */       if (this.bottom)
/* 1590 */         return new Submap(from, false, this.to, this.top); 
/* 1591 */       return (Double2BooleanAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Double2BooleanSortedMap subMap(double from, double to) {
/* 1595 */       if (this.top && this.bottom)
/* 1596 */         return new Submap(from, false, to, false); 
/* 1597 */       if (!this.top)
/* 1598 */         to = (Double2BooleanAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1599 */       if (!this.bottom)
/* 1600 */         from = (Double2BooleanAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1601 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1602 */         return this; 
/* 1603 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2BooleanAVLTreeMap.Entry firstEntry() {
/*      */       Double2BooleanAVLTreeMap.Entry e;
/* 1612 */       if (Double2BooleanAVLTreeMap.this.tree == null) {
/* 1613 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1617 */       if (this.bottom) {
/* 1618 */         e = Double2BooleanAVLTreeMap.this.firstEntry;
/*      */       } else {
/* 1620 */         e = Double2BooleanAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1622 */         if (Double2BooleanAVLTreeMap.this.compare(e.key, this.from) < 0) {
/* 1623 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1627 */       if (e == null || (!this.top && Double2BooleanAVLTreeMap.this.compare(e.key, this.to) >= 0))
/* 1628 */         return null; 
/* 1629 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2BooleanAVLTreeMap.Entry lastEntry() {
/*      */       Double2BooleanAVLTreeMap.Entry e;
/* 1638 */       if (Double2BooleanAVLTreeMap.this.tree == null) {
/* 1639 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1643 */       if (this.top) {
/* 1644 */         e = Double2BooleanAVLTreeMap.this.lastEntry;
/*      */       } else {
/* 1646 */         e = Double2BooleanAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1648 */         if (Double2BooleanAVLTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1649 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1653 */       if (e == null || (!this.bottom && Double2BooleanAVLTreeMap.this.compare(e.key, this.from) < 0))
/* 1654 */         return null; 
/* 1655 */       return e;
/*      */     }
/*      */     
/*      */     public double firstDoubleKey() {
/* 1659 */       Double2BooleanAVLTreeMap.Entry e = firstEntry();
/* 1660 */       if (e == null)
/* 1661 */         throw new NoSuchElementException(); 
/* 1662 */       return e.key;
/*      */     }
/*      */     
/*      */     public double lastDoubleKey() {
/* 1666 */       Double2BooleanAVLTreeMap.Entry e = lastEntry();
/* 1667 */       if (e == null)
/* 1668 */         throw new NoSuchElementException(); 
/* 1669 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Double2BooleanAVLTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1682 */         this.next = Double2BooleanAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(double k) {
/* 1685 */         this();
/* 1686 */         if (this.next != null)
/* 1687 */           if (!Double2BooleanAVLTreeMap.Submap.this.bottom && Double2BooleanAVLTreeMap.this.compare(k, this.next.key) < 0) {
/* 1688 */             this.prev = null;
/* 1689 */           } else if (!Double2BooleanAVLTreeMap.Submap.this.top && Double2BooleanAVLTreeMap.this.compare(k, (this.prev = Double2BooleanAVLTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1690 */             this.next = null;
/*      */           } else {
/* 1692 */             this.next = Double2BooleanAVLTreeMap.this.locateKey(k);
/* 1693 */             if (Double2BooleanAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1694 */               this.prev = this.next;
/* 1695 */               this.next = this.next.next();
/*      */             } else {
/* 1697 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1703 */         this.prev = this.prev.prev();
/* 1704 */         if (!Double2BooleanAVLTreeMap.Submap.this.bottom && this.prev != null && Double2BooleanAVLTreeMap.this.compare(this.prev.key, Double2BooleanAVLTreeMap.Submap.this.from) < 0)
/* 1705 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1709 */         this.next = this.next.next();
/* 1710 */         if (!Double2BooleanAVLTreeMap.Submap.this.top && this.next != null && Double2BooleanAVLTreeMap.this.compare(this.next.key, Double2BooleanAVLTreeMap.Submap.this.to) >= 0)
/* 1711 */           this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Double2BooleanMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(double k) {
/* 1720 */         super(k);
/*      */       }
/*      */       
/*      */       public Double2BooleanMap.Entry next() {
/* 1724 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Double2BooleanMap.Entry previous() {
/* 1728 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements DoubleListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(double from) {
/* 1746 */         super(from);
/*      */       }
/*      */       
/*      */       public double nextDouble() {
/* 1750 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public double previousDouble() {
/* 1754 */         return (previousEntry()).key;
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
/* 1770 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public boolean previousBoolean() {
/* 1774 */         return (previousEntry()).value;
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
/*      */   public Double2BooleanAVLTreeMap clone() {
/*      */     Double2BooleanAVLTreeMap c;
/*      */     try {
/* 1793 */       c = (Double2BooleanAVLTreeMap)super.clone();
/* 1794 */     } catch (CloneNotSupportedException cantHappen) {
/* 1795 */       throw new InternalError();
/*      */     } 
/* 1797 */     c.keys = null;
/* 1798 */     c.values = null;
/* 1799 */     c.entries = null;
/* 1800 */     c.allocatePaths();
/* 1801 */     if (this.count != 0) {
/*      */       
/* 1803 */       Entry rp = new Entry(), rq = new Entry();
/* 1804 */       Entry p = rp;
/* 1805 */       rp.left(this.tree);
/* 1806 */       Entry q = rq;
/* 1807 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1809 */         if (!p.pred()) {
/* 1810 */           Entry e = p.left.clone();
/* 1811 */           e.pred(q.left);
/* 1812 */           e.succ(q);
/* 1813 */           q.left(e);
/* 1814 */           p = p.left;
/* 1815 */           q = q.left;
/*      */         } else {
/* 1817 */           while (p.succ()) {
/* 1818 */             p = p.right;
/* 1819 */             if (p == null) {
/* 1820 */               q.right = null;
/* 1821 */               c.tree = rq.left;
/* 1822 */               c.firstEntry = c.tree;
/* 1823 */               while (c.firstEntry.left != null)
/* 1824 */                 c.firstEntry = c.firstEntry.left; 
/* 1825 */               c.lastEntry = c.tree;
/* 1826 */               while (c.lastEntry.right != null)
/* 1827 */                 c.lastEntry = c.lastEntry.right; 
/* 1828 */               return c;
/*      */             } 
/* 1830 */             q = q.right;
/*      */           } 
/* 1832 */           p = p.right;
/* 1833 */           q = q.right;
/*      */         } 
/* 1835 */         if (!p.succ()) {
/* 1836 */           Entry e = p.right.clone();
/* 1837 */           e.succ(q.right);
/* 1838 */           e.pred(q);
/* 1839 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1843 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1846 */     int n = this.count;
/* 1847 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1849 */     s.defaultWriteObject();
/* 1850 */     while (n-- != 0) {
/* 1851 */       Entry e = i.nextEntry();
/* 1852 */       s.writeDouble(e.key);
/* 1853 */       s.writeBoolean(e.value);
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
/*      */   private Entry readTree(ObjectInputStream s, int n, Entry pred, Entry succ) throws IOException, ClassNotFoundException {
/* 1874 */     if (n == 1) {
/* 1875 */       Entry entry = new Entry(s.readDouble(), s.readBoolean());
/* 1876 */       entry.pred(pred);
/* 1877 */       entry.succ(succ);
/* 1878 */       return entry;
/*      */     } 
/* 1880 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1885 */       Entry entry = new Entry(s.readDouble(), s.readBoolean());
/* 1886 */       entry.right(new Entry(s.readDouble(), s.readBoolean()));
/* 1887 */       entry.right.pred(entry);
/* 1888 */       entry.balance(1);
/* 1889 */       entry.pred(pred);
/* 1890 */       entry.right.succ(succ);
/* 1891 */       return entry;
/*      */     } 
/*      */     
/* 1894 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1895 */     Entry top = new Entry();
/* 1896 */     top.left(readTree(s, leftN, pred, top));
/* 1897 */     top.key = s.readDouble();
/* 1898 */     top.value = s.readBoolean();
/* 1899 */     top.right(readTree(s, rightN, top, succ));
/* 1900 */     if (n == (n & -n))
/* 1901 */       top.balance(1); 
/* 1902 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1905 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1910 */     setActualComparator();
/* 1911 */     allocatePaths();
/* 1912 */     if (this.count != 0) {
/* 1913 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1915 */       Entry e = this.tree;
/* 1916 */       while (e.left() != null)
/* 1917 */         e = e.left(); 
/* 1918 */       this.firstEntry = e;
/* 1919 */       e = this.tree;
/* 1920 */       while (e.right() != null)
/* 1921 */         e = e.right(); 
/* 1922 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2BooleanAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */