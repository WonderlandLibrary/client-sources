/*      */ package it.unimi.dsi.fastutil.ints;
/*      */ 
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
/*      */ public class Int2BooleanAVLTreeMap
/*      */   extends AbstractInt2BooleanSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Int2BooleanMap.Entry> entries;
/*      */   protected transient IntSortedSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Integer> storedComparator;
/*      */   protected transient IntComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Int2BooleanAVLTreeMap() {
/*   70 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   76 */     this.tree = null;
/*   77 */     this.count = 0;
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
/*   89 */     this.actualComparator = IntComparators.asIntComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2BooleanAVLTreeMap(Comparator<? super Integer> c) {
/*   98 */     this();
/*   99 */     this.storedComparator = c;
/*  100 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2BooleanAVLTreeMap(Map<? extends Integer, ? extends Boolean> m) {
/*  109 */     this();
/*  110 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2BooleanAVLTreeMap(SortedMap<Integer, Boolean> m) {
/*  120 */     this(m.comparator());
/*  121 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2BooleanAVLTreeMap(Int2BooleanMap m) {
/*  130 */     this();
/*  131 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2BooleanAVLTreeMap(Int2BooleanSortedMap m) {
/*  141 */     this(m.comparator());
/*  142 */     putAll(m);
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
/*      */   public Int2BooleanAVLTreeMap(int[] k, boolean[] v, Comparator<? super Integer> c) {
/*  158 */     this(c);
/*  159 */     if (k.length != v.length) {
/*  160 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  162 */     for (int i = 0; i < k.length; i++) {
/*  163 */       put(k[i], v[i]);
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
/*      */   public Int2BooleanAVLTreeMap(int[] k, boolean[] v) {
/*  176 */     this(k, v, (Comparator<? super Integer>)null);
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
/*      */   final int compare(int k1, int k2) {
/*  204 */     return (this.actualComparator == null) ? Integer.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry findKey(int k) {
/*  216 */     Entry e = this.tree;
/*      */     int cmp;
/*  218 */     while (e != null && (cmp = compare(k, e.key)) != 0)
/*  219 */       e = (cmp < 0) ? e.left() : e.right(); 
/*  220 */     return e;
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
/*  232 */     Entry e = this.tree, last = this.tree;
/*  233 */     int cmp = 0;
/*  234 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  235 */       last = e;
/*  236 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  238 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  246 */     this.dirPath = new boolean[48];
/*      */   }
/*      */   
/*      */   public boolean put(int k, boolean v) {
/*  250 */     Entry e = add(k);
/*  251 */     boolean oldValue = e.value;
/*  252 */     e.value = v;
/*  253 */     return oldValue;
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
/*      */   private Entry add(int k) {
/*  270 */     this.modified = false;
/*  271 */     Entry e = null;
/*  272 */     if (this.tree == null) {
/*  273 */       this.count++;
/*  274 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry(k, this.defRetValue);
/*  275 */       this.modified = true;
/*      */     } else {
/*  277 */       Entry p = this.tree, q = null, y = this.tree, z = null, w = null;
/*  278 */       int i = 0; while (true) {
/*      */         int cmp;
/*  280 */         if ((cmp = compare(k, p.key)) == 0) {
/*  281 */           return p;
/*      */         }
/*  283 */         if (p.balance() != 0) {
/*  284 */           i = 0;
/*  285 */           z = q;
/*  286 */           y = p;
/*      */         } 
/*  288 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  289 */           if (p.succ()) {
/*  290 */             this.count++;
/*  291 */             e = new Entry(k, this.defRetValue);
/*  292 */             this.modified = true;
/*  293 */             if (p.right == null)
/*  294 */               this.lastEntry = e; 
/*  295 */             e.left = p;
/*  296 */             e.right = p.right;
/*  297 */             p.right(e);
/*      */             break;
/*      */           } 
/*  300 */           q = p;
/*  301 */           p = p.right; continue;
/*      */         } 
/*  303 */         if (p.pred()) {
/*  304 */           this.count++;
/*  305 */           e = new Entry(k, this.defRetValue);
/*  306 */           this.modified = true;
/*  307 */           if (p.left == null)
/*  308 */             this.firstEntry = e; 
/*  309 */           e.right = p;
/*  310 */           e.left = p.left;
/*  311 */           p.left(e);
/*      */           break;
/*      */         } 
/*  314 */         q = p;
/*  315 */         p = p.left;
/*      */       } 
/*      */       
/*  318 */       p = y;
/*  319 */       i = 0;
/*  320 */       while (p != e) {
/*  321 */         if (this.dirPath[i]) {
/*  322 */           p.incBalance();
/*      */         } else {
/*  324 */           p.decBalance();
/*  325 */         }  p = this.dirPath[i++] ? p.right : p.left;
/*      */       } 
/*  327 */       if (y.balance() == -2) {
/*  328 */         Entry x = y.left;
/*  329 */         if (x.balance() == -1) {
/*  330 */           w = x;
/*  331 */           if (x.succ()) {
/*  332 */             x.succ(false);
/*  333 */             y.pred(x);
/*      */           } else {
/*  335 */             y.left = x.right;
/*  336 */           }  x.right = y;
/*  337 */           x.balance(0);
/*  338 */           y.balance(0);
/*      */         } else {
/*  340 */           assert x.balance() == 1;
/*  341 */           w = x.right;
/*  342 */           x.right = w.left;
/*  343 */           w.left = x;
/*  344 */           y.left = w.right;
/*  345 */           w.right = y;
/*  346 */           if (w.balance() == -1) {
/*  347 */             x.balance(0);
/*  348 */             y.balance(1);
/*  349 */           } else if (w.balance() == 0) {
/*  350 */             x.balance(0);
/*  351 */             y.balance(0);
/*      */           } else {
/*  353 */             x.balance(-1);
/*  354 */             y.balance(0);
/*      */           } 
/*  356 */           w.balance(0);
/*  357 */           if (w.pred()) {
/*  358 */             x.succ(w);
/*  359 */             w.pred(false);
/*      */           } 
/*  361 */           if (w.succ()) {
/*  362 */             y.pred(w);
/*  363 */             w.succ(false);
/*      */           } 
/*      */         } 
/*  366 */       } else if (y.balance() == 2) {
/*  367 */         Entry x = y.right;
/*  368 */         if (x.balance() == 1) {
/*  369 */           w = x;
/*  370 */           if (x.pred()) {
/*  371 */             x.pred(false);
/*  372 */             y.succ(x);
/*      */           } else {
/*  374 */             y.right = x.left;
/*  375 */           }  x.left = y;
/*  376 */           x.balance(0);
/*  377 */           y.balance(0);
/*      */         } else {
/*  379 */           assert x.balance() == -1;
/*  380 */           w = x.left;
/*  381 */           x.left = w.right;
/*  382 */           w.right = x;
/*  383 */           y.right = w.left;
/*  384 */           w.left = y;
/*  385 */           if (w.balance() == 1) {
/*  386 */             x.balance(0);
/*  387 */             y.balance(-1);
/*  388 */           } else if (w.balance() == 0) {
/*  389 */             x.balance(0);
/*  390 */             y.balance(0);
/*      */           } else {
/*  392 */             x.balance(1);
/*  393 */             y.balance(0);
/*      */           } 
/*  395 */           w.balance(0);
/*  396 */           if (w.pred()) {
/*  397 */             y.succ(w);
/*  398 */             w.pred(false);
/*      */           } 
/*  400 */           if (w.succ()) {
/*  401 */             x.pred(w);
/*  402 */             w.succ(false);
/*      */           } 
/*      */         } 
/*      */       } else {
/*  406 */         return e;
/*  407 */       }  if (z == null) {
/*  408 */         this.tree = w;
/*      */       }
/*  410 */       else if (z.left == y) {
/*  411 */         z.left = w;
/*      */       } else {
/*  413 */         z.right = w;
/*      */       } 
/*      */     } 
/*  416 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry parent(Entry e) {
/*  426 */     if (e == this.tree) {
/*  427 */       return null;
/*      */     }
/*  429 */     Entry y = e, x = y;
/*      */     while (true) {
/*  431 */       if (y.succ()) {
/*  432 */         Entry p = y.right;
/*  433 */         if (p == null || p.left != e) {
/*  434 */           while (!x.pred())
/*  435 */             x = x.left; 
/*  436 */           p = x.left;
/*      */         } 
/*  438 */         return p;
/*  439 */       }  if (x.pred()) {
/*  440 */         Entry p = x.left;
/*  441 */         if (p == null || p.right != e) {
/*  442 */           while (!y.succ())
/*  443 */             y = y.right; 
/*  444 */           p = y.right;
/*      */         } 
/*  446 */         return p;
/*      */       } 
/*  448 */       x = x.left;
/*  449 */       y = y.right;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(int k) {
/*  459 */     this.modified = false;
/*  460 */     if (this.tree == null) {
/*  461 */       return this.defRetValue;
/*      */     }
/*  463 */     Entry p = this.tree, q = null;
/*  464 */     boolean dir = false;
/*  465 */     int kk = k;
/*      */     int cmp;
/*  467 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  469 */       if (dir = (cmp > 0)) {
/*  470 */         q = p;
/*  471 */         if ((p = p.right()) == null)
/*  472 */           return this.defRetValue;  continue;
/*      */       } 
/*  474 */       q = p;
/*  475 */       if ((p = p.left()) == null) {
/*  476 */         return this.defRetValue;
/*      */       }
/*      */     } 
/*  479 */     if (p.left == null)
/*  480 */       this.firstEntry = p.next(); 
/*  481 */     if (p.right == null)
/*  482 */       this.lastEntry = p.prev(); 
/*  483 */     if (p.succ())
/*  484 */     { if (p.pred())
/*  485 */       { if (q != null)
/*  486 */         { if (dir) {
/*  487 */             q.succ(p.right);
/*      */           } else {
/*  489 */             q.pred(p.left);
/*      */           }  }
/*  491 */         else { this.tree = dir ? p.right : p.left; }
/*      */          }
/*  493 */       else { (p.prev()).right = p.right;
/*  494 */         if (q != null)
/*  495 */         { if (dir) {
/*  496 */             q.right = p.left;
/*      */           } else {
/*  498 */             q.left = p.left;
/*      */           }  }
/*  500 */         else { this.tree = p.left; }
/*      */          }
/*      */        }
/*  503 */     else { Entry r = p.right;
/*  504 */       if (r.pred()) {
/*  505 */         r.left = p.left;
/*  506 */         r.pred(p.pred());
/*  507 */         if (!r.pred())
/*  508 */           (r.prev()).right = r; 
/*  509 */         if (q != null)
/*  510 */         { if (dir) {
/*  511 */             q.right = r;
/*      */           } else {
/*  513 */             q.left = r;
/*      */           }  }
/*  515 */         else { this.tree = r; }
/*  516 */          r.balance(p.balance());
/*  517 */         q = r;
/*  518 */         dir = true;
/*      */       } else {
/*      */         Entry s;
/*      */         while (true) {
/*  522 */           s = r.left;
/*  523 */           if (s.pred())
/*      */             break; 
/*  525 */           r = s;
/*      */         } 
/*  527 */         if (s.succ()) {
/*  528 */           r.pred(s);
/*      */         } else {
/*  530 */           r.left = s.right;
/*  531 */         }  s.left = p.left;
/*  532 */         if (!p.pred()) {
/*  533 */           (p.prev()).right = s;
/*  534 */           s.pred(false);
/*      */         } 
/*  536 */         s.right = p.right;
/*  537 */         s.succ(false);
/*  538 */         if (q != null)
/*  539 */         { if (dir) {
/*  540 */             q.right = s;
/*      */           } else {
/*  542 */             q.left = s;
/*      */           }  }
/*  544 */         else { this.tree = s; }
/*  545 */          s.balance(p.balance());
/*  546 */         q = r;
/*  547 */         dir = false;
/*      */       }  }
/*      */ 
/*      */     
/*  551 */     while (q != null) {
/*  552 */       Entry y = q;
/*  553 */       q = parent(y);
/*  554 */       if (!dir) {
/*  555 */         dir = (q != null && q.left != y);
/*  556 */         y.incBalance();
/*  557 */         if (y.balance() == 1)
/*      */           break; 
/*  559 */         if (y.balance() == 2) {
/*  560 */           Entry x = y.right;
/*  561 */           assert x != null;
/*  562 */           if (x.balance() == -1) {
/*      */             
/*  564 */             assert x.balance() == -1;
/*  565 */             Entry w = x.left;
/*  566 */             x.left = w.right;
/*  567 */             w.right = x;
/*  568 */             y.right = w.left;
/*  569 */             w.left = y;
/*  570 */             if (w.balance() == 1) {
/*  571 */               x.balance(0);
/*  572 */               y.balance(-1);
/*  573 */             } else if (w.balance() == 0) {
/*  574 */               x.balance(0);
/*  575 */               y.balance(0);
/*      */             } else {
/*  577 */               assert w.balance() == -1;
/*  578 */               x.balance(1);
/*  579 */               y.balance(0);
/*      */             } 
/*  581 */             w.balance(0);
/*  582 */             if (w.pred()) {
/*  583 */               y.succ(w);
/*  584 */               w.pred(false);
/*      */             } 
/*  586 */             if (w.succ()) {
/*  587 */               x.pred(w);
/*  588 */               w.succ(false);
/*      */             } 
/*  590 */             if (q != null) {
/*  591 */               if (dir) {
/*  592 */                 q.right = w; continue;
/*      */               } 
/*  594 */               q.left = w; continue;
/*      */             } 
/*  596 */             this.tree = w; continue;
/*      */           } 
/*  598 */           if (q != null)
/*  599 */           { if (dir) {
/*  600 */               q.right = x;
/*      */             } else {
/*  602 */               q.left = x;
/*      */             }  }
/*  604 */           else { this.tree = x; }
/*  605 */            if (x.balance() == 0) {
/*  606 */             y.right = x.left;
/*  607 */             x.left = y;
/*  608 */             x.balance(-1);
/*  609 */             y.balance(1);
/*      */             break;
/*      */           } 
/*  612 */           assert x.balance() == 1;
/*  613 */           if (x.pred()) {
/*  614 */             y.succ(true);
/*  615 */             x.pred(false);
/*      */           } else {
/*  617 */             y.right = x.left;
/*  618 */           }  x.left = y;
/*  619 */           y.balance(0);
/*  620 */           x.balance(0);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  624 */       dir = (q != null && q.left != y);
/*  625 */       y.decBalance();
/*  626 */       if (y.balance() == -1)
/*      */         break; 
/*  628 */       if (y.balance() == -2) {
/*  629 */         Entry x = y.left;
/*  630 */         assert x != null;
/*  631 */         if (x.balance() == 1) {
/*      */           
/*  633 */           assert x.balance() == 1;
/*  634 */           Entry w = x.right;
/*  635 */           x.right = w.left;
/*  636 */           w.left = x;
/*  637 */           y.left = w.right;
/*  638 */           w.right = y;
/*  639 */           if (w.balance() == -1) {
/*  640 */             x.balance(0);
/*  641 */             y.balance(1);
/*  642 */           } else if (w.balance() == 0) {
/*  643 */             x.balance(0);
/*  644 */             y.balance(0);
/*      */           } else {
/*  646 */             assert w.balance() == 1;
/*  647 */             x.balance(-1);
/*  648 */             y.balance(0);
/*      */           } 
/*  650 */           w.balance(0);
/*  651 */           if (w.pred()) {
/*  652 */             x.succ(w);
/*  653 */             w.pred(false);
/*      */           } 
/*  655 */           if (w.succ()) {
/*  656 */             y.pred(w);
/*  657 */             w.succ(false);
/*      */           } 
/*  659 */           if (q != null) {
/*  660 */             if (dir) {
/*  661 */               q.right = w; continue;
/*      */             } 
/*  663 */             q.left = w; continue;
/*      */           } 
/*  665 */           this.tree = w; continue;
/*      */         } 
/*  667 */         if (q != null)
/*  668 */         { if (dir) {
/*  669 */             q.right = x;
/*      */           } else {
/*  671 */             q.left = x;
/*      */           }  }
/*  673 */         else { this.tree = x; }
/*  674 */          if (x.balance() == 0) {
/*  675 */           y.left = x.right;
/*  676 */           x.right = y;
/*  677 */           x.balance(1);
/*  678 */           y.balance(-1);
/*      */           break;
/*      */         } 
/*  681 */         assert x.balance() == -1;
/*  682 */         if (x.succ()) {
/*  683 */           y.pred(true);
/*  684 */           x.succ(false);
/*      */         } else {
/*  686 */           y.left = x.right;
/*  687 */         }  x.right = y;
/*  688 */         y.balance(0);
/*  689 */         x.balance(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  694 */     this.modified = true;
/*  695 */     this.count--;
/*  696 */     return p.value;
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  700 */     ValueIterator i = new ValueIterator();
/*      */     
/*  702 */     int j = this.count;
/*  703 */     while (j-- != 0) {
/*  704 */       boolean ev = i.nextBoolean();
/*  705 */       if (ev == v)
/*  706 */         return true; 
/*      */     } 
/*  708 */     return false;
/*      */   }
/*      */   
/*      */   public void clear() {
/*  712 */     this.count = 0;
/*  713 */     this.tree = null;
/*  714 */     this.entries = null;
/*  715 */     this.values = null;
/*  716 */     this.keys = null;
/*  717 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry
/*      */     extends AbstractInt2BooleanMap.BasicEntry
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
/*  748 */       super(0, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(int k, boolean v) {
/*  759 */       super(k, v);
/*  760 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left() {
/*  768 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right() {
/*  776 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  784 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  792 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  801 */       if (pred) {
/*  802 */         this.info |= 0x40000000;
/*      */       } else {
/*  804 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  813 */       if (succ) {
/*  814 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  816 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry pred) {
/*  825 */       this.info |= 0x40000000;
/*  826 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry succ) {
/*  835 */       this.info |= Integer.MIN_VALUE;
/*  836 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry left) {
/*  845 */       this.info &= 0xBFFFFFFF;
/*  846 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry right) {
/*  855 */       this.info &= Integer.MAX_VALUE;
/*  856 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int balance() {
/*  864 */       return (byte)this.info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void balance(int level) {
/*  873 */       this.info &= 0xFFFFFF00;
/*  874 */       this.info |= level & 0xFF;
/*      */     }
/*      */     
/*      */     void incBalance() {
/*  878 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
/*      */     }
/*      */     
/*      */     protected void decBalance() {
/*  882 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry next() {
/*  890 */       Entry next = this.right;
/*  891 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  892 */         while ((next.info & 0x40000000) == 0)
/*  893 */           next = next.left;  
/*  894 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry prev() {
/*  902 */       Entry prev = this.left;
/*  903 */       if ((this.info & 0x40000000) == 0)
/*  904 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  905 */           prev = prev.right;  
/*  906 */       return prev;
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean value) {
/*  910 */       boolean oldValue = this.value;
/*  911 */       this.value = value;
/*  912 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry clone() {
/*      */       Entry c;
/*      */       try {
/*  919 */         c = (Entry)super.clone();
/*  920 */       } catch (CloneNotSupportedException cantHappen) {
/*  921 */         throw new InternalError();
/*      */       } 
/*  923 */       c.key = this.key;
/*  924 */       c.value = this.value;
/*  925 */       c.info = this.info;
/*  926 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  931 */       if (!(o instanceof Map.Entry))
/*  932 */         return false; 
/*  933 */       Map.Entry<Integer, Boolean> e = (Map.Entry<Integer, Boolean>)o;
/*  934 */       return (this.key == ((Integer)e.getKey()).intValue() && this.value == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  938 */       return this.key ^ (this.value ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  942 */       return this.key + "=>" + this.value;
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
/*      */   public boolean containsKey(int k) {
/*  963 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  967 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  971 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean get(int k) {
/*  976 */     Entry e = findKey(k);
/*  977 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public int firstIntKey() {
/*  981 */     if (this.tree == null)
/*  982 */       throw new NoSuchElementException(); 
/*  983 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public int lastIntKey() {
/*  987 */     if (this.tree == null)
/*  988 */       throw new NoSuchElementException(); 
/*  989 */     return this.lastEntry.key;
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
/*      */     Int2BooleanAVLTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Int2BooleanAVLTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Int2BooleanAVLTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1020 */     int index = 0;
/*      */     TreeIterator() {
/* 1022 */       this.next = Int2BooleanAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(int k) {
/* 1025 */       if ((this.next = Int2BooleanAVLTreeMap.this.locateKey(k)) != null)
/* 1026 */         if (Int2BooleanAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1027 */           this.prev = this.next;
/* 1028 */           this.next = this.next.next();
/*      */         } else {
/* 1030 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/* 1034 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1037 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1040 */       this.next = this.next.next();
/*      */     }
/*      */     Int2BooleanAVLTreeMap.Entry nextEntry() {
/* 1043 */       if (!hasNext())
/* 1044 */         throw new NoSuchElementException(); 
/* 1045 */       this.curr = this.prev = this.next;
/* 1046 */       this.index++;
/* 1047 */       updateNext();
/* 1048 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1051 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Int2BooleanAVLTreeMap.Entry previousEntry() {
/* 1054 */       if (!hasPrevious())
/* 1055 */         throw new NoSuchElementException(); 
/* 1056 */       this.curr = this.next = this.prev;
/* 1057 */       this.index--;
/* 1058 */       updatePrevious();
/* 1059 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1062 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1065 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1068 */       if (this.curr == null) {
/* 1069 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1074 */       if (this.curr == this.prev)
/* 1075 */         this.index--; 
/* 1076 */       this.next = this.prev = this.curr;
/* 1077 */       updatePrevious();
/* 1078 */       updateNext();
/* 1079 */       Int2BooleanAVLTreeMap.this.remove(this.curr.key);
/* 1080 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1083 */       int i = n;
/* 1084 */       while (i-- != 0 && hasNext())
/* 1085 */         nextEntry(); 
/* 1086 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1089 */       int i = n;
/* 1090 */       while (i-- != 0 && hasPrevious())
/* 1091 */         previousEntry(); 
/* 1092 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Int2BooleanMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(int k) {
/* 1105 */       super(k);
/*      */     }
/*      */     
/*      */     public Int2BooleanMap.Entry next() {
/* 1109 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Int2BooleanMap.Entry previous() {
/* 1113 */       return previousEntry();
/*      */     }
/*      */     
/*      */     public void set(Int2BooleanMap.Entry ok) {
/* 1117 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Int2BooleanMap.Entry ok) {
/* 1121 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Int2BooleanMap.Entry> int2BooleanEntrySet() {
/* 1126 */     if (this.entries == null)
/* 1127 */       this.entries = (ObjectSortedSet<Int2BooleanMap.Entry>)new AbstractObjectSortedSet<Int2BooleanMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Int2BooleanMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Int2BooleanMap.Entry> comparator() {
/* 1132 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Int2BooleanMap.Entry> iterator() {
/* 1136 */             return (ObjectBidirectionalIterator<Int2BooleanMap.Entry>)new Int2BooleanAVLTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Int2BooleanMap.Entry> iterator(Int2BooleanMap.Entry from) {
/* 1140 */             return (ObjectBidirectionalIterator<Int2BooleanMap.Entry>)new Int2BooleanAVLTreeMap.EntryIterator(from.getIntKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1145 */             if (!(o instanceof Map.Entry))
/* 1146 */               return false; 
/* 1147 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1148 */             if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1149 */               return false; 
/* 1150 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1151 */               return false; 
/* 1152 */             Int2BooleanAVLTreeMap.Entry f = Int2BooleanAVLTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1153 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1158 */             if (!(o instanceof Map.Entry))
/* 1159 */               return false; 
/* 1160 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1161 */             if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1162 */               return false; 
/* 1163 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1164 */               return false; 
/* 1165 */             Int2BooleanAVLTreeMap.Entry f = Int2BooleanAVLTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1166 */             if (f == null || f.getBooleanValue() != ((Boolean)e.getValue()).booleanValue())
/* 1167 */               return false; 
/* 1168 */             Int2BooleanAVLTreeMap.this.remove(f.key);
/* 1169 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1173 */             return Int2BooleanAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1177 */             Int2BooleanAVLTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Int2BooleanMap.Entry first() {
/* 1181 */             return Int2BooleanAVLTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Int2BooleanMap.Entry last() {
/* 1185 */             return Int2BooleanAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Int2BooleanMap.Entry> subSet(Int2BooleanMap.Entry from, Int2BooleanMap.Entry to) {
/* 1190 */             return Int2BooleanAVLTreeMap.this.subMap(from.getIntKey(), to.getIntKey()).int2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Int2BooleanMap.Entry> headSet(Int2BooleanMap.Entry to) {
/* 1194 */             return Int2BooleanAVLTreeMap.this.headMap(to.getIntKey()).int2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Int2BooleanMap.Entry> tailSet(Int2BooleanMap.Entry from) {
/* 1198 */             return Int2BooleanAVLTreeMap.this.tailMap(from.getIntKey()).int2BooleanEntrySet();
/*      */           }
/*      */         }; 
/* 1201 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends TreeIterator
/*      */     implements IntListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(int k) {
/* 1217 */       super(k);
/*      */     }
/*      */     
/*      */     public int nextInt() {
/* 1221 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public int previousInt() {
/* 1225 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractInt2BooleanSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public IntBidirectionalIterator iterator() {
/* 1232 */       return new Int2BooleanAVLTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public IntBidirectionalIterator iterator(int from) {
/* 1236 */       return new Int2BooleanAVLTreeMap.KeyIterator(from);
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
/*      */   public IntSortedSet keySet() {
/* 1251 */     if (this.keys == null)
/* 1252 */       this.keys = new KeySet(); 
/* 1253 */     return this.keys;
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
/* 1268 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public boolean previousBoolean() {
/* 1272 */       return (previousEntry()).value;
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
/* 1287 */     if (this.values == null)
/* 1288 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1291 */             return (BooleanIterator)new Int2BooleanAVLTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(boolean k) {
/* 1295 */             return Int2BooleanAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1299 */             return Int2BooleanAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1303 */             Int2BooleanAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1306 */     return this.values;
/*      */   }
/*      */   
/*      */   public IntComparator comparator() {
/* 1310 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Int2BooleanSortedMap headMap(int to) {
/* 1314 */     return new Submap(0, true, to, false);
/*      */   }
/*      */   
/*      */   public Int2BooleanSortedMap tailMap(int from) {
/* 1318 */     return new Submap(from, false, 0, true);
/*      */   }
/*      */   
/*      */   public Int2BooleanSortedMap subMap(int from, int to) {
/* 1322 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractInt2BooleanSortedMap
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     int from;
/*      */ 
/*      */ 
/*      */     
/*      */     int to;
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
/*      */     protected transient ObjectSortedSet<Int2BooleanMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient IntSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(int from, boolean bottom, int to, boolean top) {
/* 1366 */       if (!bottom && !top && Int2BooleanAVLTreeMap.this.compare(from, to) > 0)
/* 1367 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1368 */       this.from = from;
/* 1369 */       this.bottom = bottom;
/* 1370 */       this.to = to;
/* 1371 */       this.top = top;
/* 1372 */       this.defRetValue = Int2BooleanAVLTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1376 */       SubmapIterator i = new SubmapIterator();
/* 1377 */       while (i.hasNext()) {
/* 1378 */         i.nextEntry();
/* 1379 */         i.remove();
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
/* 1390 */       return ((this.bottom || Int2BooleanAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Int2BooleanAVLTreeMap.this
/* 1391 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Int2BooleanMap.Entry> int2BooleanEntrySet() {
/* 1395 */       if (this.entries == null)
/* 1396 */         this.entries = (ObjectSortedSet<Int2BooleanMap.Entry>)new AbstractObjectSortedSet<Int2BooleanMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Int2BooleanMap.Entry> iterator() {
/* 1399 */               return (ObjectBidirectionalIterator<Int2BooleanMap.Entry>)new Int2BooleanAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */             
/*      */             public ObjectBidirectionalIterator<Int2BooleanMap.Entry> iterator(Int2BooleanMap.Entry from) {
/* 1403 */               return (ObjectBidirectionalIterator<Int2BooleanMap.Entry>)new Int2BooleanAVLTreeMap.Submap.SubmapEntryIterator(from.getIntKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Int2BooleanMap.Entry> comparator() {
/* 1407 */               return Int2BooleanAVLTreeMap.this.int2BooleanEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1412 */               if (!(o instanceof Map.Entry))
/* 1413 */                 return false; 
/* 1414 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1415 */               if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1416 */                 return false; 
/* 1417 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1418 */                 return false; 
/* 1419 */               Int2BooleanAVLTreeMap.Entry f = Int2BooleanAVLTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1420 */               return (f != null && Int2BooleanAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1425 */               if (!(o instanceof Map.Entry))
/* 1426 */                 return false; 
/* 1427 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1428 */               if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1429 */                 return false; 
/* 1430 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1431 */                 return false; 
/* 1432 */               Int2BooleanAVLTreeMap.Entry f = Int2BooleanAVLTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1433 */               if (f != null && Int2BooleanAVLTreeMap.Submap.this.in(f.key))
/* 1434 */                 Int2BooleanAVLTreeMap.Submap.this.remove(f.key); 
/* 1435 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1439 */               int c = 0;
/* 1440 */               for (ObjectBidirectionalIterator<Int2BooleanMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1441 */                 c++; 
/* 1442 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1446 */               return !(new Int2BooleanAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1450 */               Int2BooleanAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Int2BooleanMap.Entry first() {
/* 1454 */               return Int2BooleanAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Int2BooleanMap.Entry last() {
/* 1458 */               return Int2BooleanAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Int2BooleanMap.Entry> subSet(Int2BooleanMap.Entry from, Int2BooleanMap.Entry to) {
/* 1463 */               return Int2BooleanAVLTreeMap.Submap.this.subMap(from.getIntKey(), to.getIntKey()).int2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Int2BooleanMap.Entry> headSet(Int2BooleanMap.Entry to) {
/* 1467 */               return Int2BooleanAVLTreeMap.Submap.this.headMap(to.getIntKey()).int2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Int2BooleanMap.Entry> tailSet(Int2BooleanMap.Entry from) {
/* 1471 */               return Int2BooleanAVLTreeMap.Submap.this.tailMap(from.getIntKey()).int2BooleanEntrySet();
/*      */             }
/*      */           }; 
/* 1474 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractInt2BooleanSortedMap.KeySet {
/*      */       public IntBidirectionalIterator iterator() {
/* 1479 */         return new Int2BooleanAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public IntBidirectionalIterator iterator(int from) {
/* 1483 */         return new Int2BooleanAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public IntSortedSet keySet() {
/* 1488 */       if (this.keys == null)
/* 1489 */         this.keys = new KeySet(); 
/* 1490 */       return this.keys;
/*      */     }
/*      */     
/*      */     public BooleanCollection values() {
/* 1494 */       if (this.values == null)
/* 1495 */         this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */           {
/*      */             public BooleanIterator iterator() {
/* 1498 */               return (BooleanIterator)new Int2BooleanAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(boolean k) {
/* 1502 */               return Int2BooleanAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1506 */               return Int2BooleanAVLTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1510 */               Int2BooleanAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1513 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(int k) {
/* 1518 */       return (in(k) && Int2BooleanAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(boolean v) {
/* 1522 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1524 */       while (i.hasNext()) {
/* 1525 */         boolean ev = (i.nextEntry()).value;
/* 1526 */         if (ev == v)
/* 1527 */           return true; 
/*      */       } 
/* 1529 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean get(int k) {
/* 1535 */       int kk = k; Int2BooleanAVLTreeMap.Entry e;
/* 1536 */       return (in(kk) && (e = Int2BooleanAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public boolean put(int k, boolean v) {
/* 1540 */       Int2BooleanAVLTreeMap.this.modified = false;
/* 1541 */       if (!in(k))
/* 1542 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1543 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1544 */       boolean oldValue = Int2BooleanAVLTreeMap.this.put(k, v);
/* 1545 */       return Int2BooleanAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(int k) {
/* 1550 */       Int2BooleanAVLTreeMap.this.modified = false;
/* 1551 */       if (!in(k))
/* 1552 */         return this.defRetValue; 
/* 1553 */       boolean oldValue = Int2BooleanAVLTreeMap.this.remove(k);
/* 1554 */       return Int2BooleanAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1558 */       SubmapIterator i = new SubmapIterator();
/* 1559 */       int n = 0;
/* 1560 */       while (i.hasNext()) {
/* 1561 */         n++;
/* 1562 */         i.nextEntry();
/*      */       } 
/* 1564 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1568 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public IntComparator comparator() {
/* 1572 */       return Int2BooleanAVLTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Int2BooleanSortedMap headMap(int to) {
/* 1576 */       if (this.top)
/* 1577 */         return new Submap(this.from, this.bottom, to, false); 
/* 1578 */       return (Int2BooleanAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Int2BooleanSortedMap tailMap(int from) {
/* 1582 */       if (this.bottom)
/* 1583 */         return new Submap(from, false, this.to, this.top); 
/* 1584 */       return (Int2BooleanAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Int2BooleanSortedMap subMap(int from, int to) {
/* 1588 */       if (this.top && this.bottom)
/* 1589 */         return new Submap(from, false, to, false); 
/* 1590 */       if (!this.top)
/* 1591 */         to = (Int2BooleanAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1592 */       if (!this.bottom)
/* 1593 */         from = (Int2BooleanAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1594 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1595 */         return this; 
/* 1596 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Int2BooleanAVLTreeMap.Entry firstEntry() {
/*      */       Int2BooleanAVLTreeMap.Entry e;
/* 1605 */       if (Int2BooleanAVLTreeMap.this.tree == null) {
/* 1606 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1610 */       if (this.bottom) {
/* 1611 */         e = Int2BooleanAVLTreeMap.this.firstEntry;
/*      */       } else {
/* 1613 */         e = Int2BooleanAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1615 */         if (Int2BooleanAVLTreeMap.this.compare(e.key, this.from) < 0) {
/* 1616 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1620 */       if (e == null || (!this.top && Int2BooleanAVLTreeMap.this.compare(e.key, this.to) >= 0))
/* 1621 */         return null; 
/* 1622 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Int2BooleanAVLTreeMap.Entry lastEntry() {
/*      */       Int2BooleanAVLTreeMap.Entry e;
/* 1631 */       if (Int2BooleanAVLTreeMap.this.tree == null) {
/* 1632 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1636 */       if (this.top) {
/* 1637 */         e = Int2BooleanAVLTreeMap.this.lastEntry;
/*      */       } else {
/* 1639 */         e = Int2BooleanAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1641 */         if (Int2BooleanAVLTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1642 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1646 */       if (e == null || (!this.bottom && Int2BooleanAVLTreeMap.this.compare(e.key, this.from) < 0))
/* 1647 */         return null; 
/* 1648 */       return e;
/*      */     }
/*      */     
/*      */     public int firstIntKey() {
/* 1652 */       Int2BooleanAVLTreeMap.Entry e = firstEntry();
/* 1653 */       if (e == null)
/* 1654 */         throw new NoSuchElementException(); 
/* 1655 */       return e.key;
/*      */     }
/*      */     
/*      */     public int lastIntKey() {
/* 1659 */       Int2BooleanAVLTreeMap.Entry e = lastEntry();
/* 1660 */       if (e == null)
/* 1661 */         throw new NoSuchElementException(); 
/* 1662 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Int2BooleanAVLTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1675 */         this.next = Int2BooleanAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(int k) {
/* 1678 */         this();
/* 1679 */         if (this.next != null)
/* 1680 */           if (!Int2BooleanAVLTreeMap.Submap.this.bottom && Int2BooleanAVLTreeMap.this.compare(k, this.next.key) < 0) {
/* 1681 */             this.prev = null;
/* 1682 */           } else if (!Int2BooleanAVLTreeMap.Submap.this.top && Int2BooleanAVLTreeMap.this.compare(k, (this.prev = Int2BooleanAVLTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1683 */             this.next = null;
/*      */           } else {
/* 1685 */             this.next = Int2BooleanAVLTreeMap.this.locateKey(k);
/* 1686 */             if (Int2BooleanAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1687 */               this.prev = this.next;
/* 1688 */               this.next = this.next.next();
/*      */             } else {
/* 1690 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1696 */         this.prev = this.prev.prev();
/* 1697 */         if (!Int2BooleanAVLTreeMap.Submap.this.bottom && this.prev != null && Int2BooleanAVLTreeMap.this.compare(this.prev.key, Int2BooleanAVLTreeMap.Submap.this.from) < 0)
/* 1698 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1702 */         this.next = this.next.next();
/* 1703 */         if (!Int2BooleanAVLTreeMap.Submap.this.top && this.next != null && Int2BooleanAVLTreeMap.this.compare(this.next.key, Int2BooleanAVLTreeMap.Submap.this.to) >= 0)
/* 1704 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Int2BooleanMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(int k) {
/* 1711 */         super(k);
/*      */       }
/*      */       
/*      */       public Int2BooleanMap.Entry next() {
/* 1715 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Int2BooleanMap.Entry previous() {
/* 1719 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements IntListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(int from) {
/* 1737 */         super(from);
/*      */       }
/*      */       
/*      */       public int nextInt() {
/* 1741 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public int previousInt() {
/* 1745 */         return (previousEntry()).key;
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
/* 1761 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public boolean previousBoolean() {
/* 1765 */         return (previousEntry()).value;
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
/*      */   public Int2BooleanAVLTreeMap clone() {
/*      */     Int2BooleanAVLTreeMap c;
/*      */     try {
/* 1784 */       c = (Int2BooleanAVLTreeMap)super.clone();
/* 1785 */     } catch (CloneNotSupportedException cantHappen) {
/* 1786 */       throw new InternalError();
/*      */     } 
/* 1788 */     c.keys = null;
/* 1789 */     c.values = null;
/* 1790 */     c.entries = null;
/* 1791 */     c.allocatePaths();
/* 1792 */     if (this.count != 0) {
/*      */       
/* 1794 */       Entry rp = new Entry(), rq = new Entry();
/* 1795 */       Entry p = rp;
/* 1796 */       rp.left(this.tree);
/* 1797 */       Entry q = rq;
/* 1798 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1800 */         if (!p.pred()) {
/* 1801 */           Entry e = p.left.clone();
/* 1802 */           e.pred(q.left);
/* 1803 */           e.succ(q);
/* 1804 */           q.left(e);
/* 1805 */           p = p.left;
/* 1806 */           q = q.left;
/*      */         } else {
/* 1808 */           while (p.succ()) {
/* 1809 */             p = p.right;
/* 1810 */             if (p == null) {
/* 1811 */               q.right = null;
/* 1812 */               c.tree = rq.left;
/* 1813 */               c.firstEntry = c.tree;
/* 1814 */               while (c.firstEntry.left != null)
/* 1815 */                 c.firstEntry = c.firstEntry.left; 
/* 1816 */               c.lastEntry = c.tree;
/* 1817 */               while (c.lastEntry.right != null)
/* 1818 */                 c.lastEntry = c.lastEntry.right; 
/* 1819 */               return c;
/*      */             } 
/* 1821 */             q = q.right;
/*      */           } 
/* 1823 */           p = p.right;
/* 1824 */           q = q.right;
/*      */         } 
/* 1826 */         if (!p.succ()) {
/* 1827 */           Entry e = p.right.clone();
/* 1828 */           e.succ(q.right);
/* 1829 */           e.pred(q);
/* 1830 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1834 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1837 */     int n = this.count;
/* 1838 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1840 */     s.defaultWriteObject();
/* 1841 */     while (n-- != 0) {
/* 1842 */       Entry e = i.nextEntry();
/* 1843 */       s.writeInt(e.key);
/* 1844 */       s.writeBoolean(e.value);
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
/* 1865 */     if (n == 1) {
/* 1866 */       Entry entry = new Entry(s.readInt(), s.readBoolean());
/* 1867 */       entry.pred(pred);
/* 1868 */       entry.succ(succ);
/* 1869 */       return entry;
/*      */     } 
/* 1871 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1876 */       Entry entry = new Entry(s.readInt(), s.readBoolean());
/* 1877 */       entry.right(new Entry(s.readInt(), s.readBoolean()));
/* 1878 */       entry.right.pred(entry);
/* 1879 */       entry.balance(1);
/* 1880 */       entry.pred(pred);
/* 1881 */       entry.right.succ(succ);
/* 1882 */       return entry;
/*      */     } 
/*      */     
/* 1885 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1886 */     Entry top = new Entry();
/* 1887 */     top.left(readTree(s, leftN, pred, top));
/* 1888 */     top.key = s.readInt();
/* 1889 */     top.value = s.readBoolean();
/* 1890 */     top.right(readTree(s, rightN, top, succ));
/* 1891 */     if (n == (n & -n))
/* 1892 */       top.balance(1); 
/* 1893 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1896 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1901 */     setActualComparator();
/* 1902 */     allocatePaths();
/* 1903 */     if (this.count != 0) {
/* 1904 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1906 */       Entry e = this.tree;
/* 1907 */       while (e.left() != null)
/* 1908 */         e = e.left(); 
/* 1909 */       this.firstEntry = e;
/* 1910 */       e = this.tree;
/* 1911 */       while (e.right() != null)
/* 1912 */         e = e.right(); 
/* 1913 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2BooleanAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */