/*      */ package it.unimi.dsi.fastutil.shorts;
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
/*      */ public class Short2BooleanAVLTreeMap
/*      */   extends AbstractShort2BooleanSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Short2BooleanMap.Entry> entries;
/*      */   protected transient ShortSortedSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Short> storedComparator;
/*      */   protected transient ShortComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Short2BooleanAVLTreeMap() {
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
/*   89 */     this.actualComparator = ShortComparators.asShortComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2BooleanAVLTreeMap(Comparator<? super Short> c) {
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
/*      */   public Short2BooleanAVLTreeMap(Map<? extends Short, ? extends Boolean> m) {
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
/*      */   public Short2BooleanAVLTreeMap(SortedMap<Short, Boolean> m) {
/*  120 */     this(m.comparator());
/*  121 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2BooleanAVLTreeMap(Short2BooleanMap m) {
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
/*      */   public Short2BooleanAVLTreeMap(Short2BooleanSortedMap m) {
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
/*      */   public Short2BooleanAVLTreeMap(short[] k, boolean[] v, Comparator<? super Short> c) {
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
/*      */   public Short2BooleanAVLTreeMap(short[] k, boolean[] v) {
/*  176 */     this(k, v, (Comparator<? super Short>)null);
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
/*      */   final int compare(short k1, short k2) {
/*  204 */     return (this.actualComparator == null) ? Short.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry findKey(short k) {
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
/*      */   final Entry locateKey(short k) {
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
/*      */   public boolean put(short k, boolean v) {
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
/*      */   private Entry add(short k) {
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
/*      */   public boolean remove(short k) {
/*  459 */     this.modified = false;
/*  460 */     if (this.tree == null) {
/*  461 */       return this.defRetValue;
/*      */     }
/*  463 */     Entry p = this.tree, q = null;
/*  464 */     boolean dir = false;
/*  465 */     short kk = k;
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
/*      */     extends AbstractShort2BooleanMap.BasicEntry
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
/*  748 */       super((short)0, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(short k, boolean v) {
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
/*  933 */       Map.Entry<Short, Boolean> e = (Map.Entry<Short, Boolean>)o;
/*  934 */       return (this.key == ((Short)e.getKey()).shortValue() && this.value == ((Boolean)e.getValue()).booleanValue());
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
/*      */   public boolean containsKey(short k) {
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
/*      */   public boolean get(short k) {
/*  976 */     Entry e = findKey(k);
/*  977 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public short firstShortKey() {
/*  981 */     if (this.tree == null)
/*  982 */       throw new NoSuchElementException(); 
/*  983 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public short lastShortKey() {
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
/*      */     Short2BooleanAVLTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Short2BooleanAVLTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Short2BooleanAVLTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1020 */     int index = 0;
/*      */     TreeIterator() {
/* 1022 */       this.next = Short2BooleanAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(short k) {
/* 1025 */       if ((this.next = Short2BooleanAVLTreeMap.this.locateKey(k)) != null)
/* 1026 */         if (Short2BooleanAVLTreeMap.this.compare(this.next.key, k) <= 0) {
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
/*      */     Short2BooleanAVLTreeMap.Entry nextEntry() {
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
/*      */     Short2BooleanAVLTreeMap.Entry previousEntry() {
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
/* 1079 */       Short2BooleanAVLTreeMap.this.remove(this.curr.key);
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
/*      */     implements ObjectListIterator<Short2BooleanMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(short k) {
/* 1105 */       super(k);
/*      */     }
/*      */     
/*      */     public Short2BooleanMap.Entry next() {
/* 1109 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Short2BooleanMap.Entry previous() {
/* 1113 */       return previousEntry();
/*      */     }
/*      */     
/*      */     public void set(Short2BooleanMap.Entry ok) {
/* 1117 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Short2BooleanMap.Entry ok) {
/* 1121 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Short2BooleanMap.Entry> short2BooleanEntrySet() {
/* 1126 */     if (this.entries == null)
/* 1127 */       this.entries = (ObjectSortedSet<Short2BooleanMap.Entry>)new AbstractObjectSortedSet<Short2BooleanMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Short2BooleanMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Short2BooleanMap.Entry> comparator() {
/* 1132 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Short2BooleanMap.Entry> iterator() {
/* 1136 */             return (ObjectBidirectionalIterator<Short2BooleanMap.Entry>)new Short2BooleanAVLTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Short2BooleanMap.Entry> iterator(Short2BooleanMap.Entry from) {
/* 1140 */             return (ObjectBidirectionalIterator<Short2BooleanMap.Entry>)new Short2BooleanAVLTreeMap.EntryIterator(from.getShortKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1145 */             if (!(o instanceof Map.Entry))
/* 1146 */               return false; 
/* 1147 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1148 */             if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1149 */               return false; 
/* 1150 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1151 */               return false; 
/* 1152 */             Short2BooleanAVLTreeMap.Entry f = Short2BooleanAVLTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1153 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1158 */             if (!(o instanceof Map.Entry))
/* 1159 */               return false; 
/* 1160 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1161 */             if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1162 */               return false; 
/* 1163 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1164 */               return false; 
/* 1165 */             Short2BooleanAVLTreeMap.Entry f = Short2BooleanAVLTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1166 */             if (f == null || f.getBooleanValue() != ((Boolean)e.getValue()).booleanValue())
/* 1167 */               return false; 
/* 1168 */             Short2BooleanAVLTreeMap.this.remove(f.key);
/* 1169 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1173 */             return Short2BooleanAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1177 */             Short2BooleanAVLTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Short2BooleanMap.Entry first() {
/* 1181 */             return Short2BooleanAVLTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Short2BooleanMap.Entry last() {
/* 1185 */             return Short2BooleanAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Short2BooleanMap.Entry> subSet(Short2BooleanMap.Entry from, Short2BooleanMap.Entry to) {
/* 1190 */             return Short2BooleanAVLTreeMap.this.subMap(from.getShortKey(), to.getShortKey()).short2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Short2BooleanMap.Entry> headSet(Short2BooleanMap.Entry to) {
/* 1194 */             return Short2BooleanAVLTreeMap.this.headMap(to.getShortKey()).short2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Short2BooleanMap.Entry> tailSet(Short2BooleanMap.Entry from) {
/* 1198 */             return Short2BooleanAVLTreeMap.this.tailMap(from.getShortKey()).short2BooleanEntrySet();
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
/*      */     implements ShortListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(short k) {
/* 1217 */       super(k);
/*      */     }
/*      */     
/*      */     public short nextShort() {
/* 1221 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public short previousShort() {
/* 1225 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractShort2BooleanSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ShortBidirectionalIterator iterator() {
/* 1232 */       return new Short2BooleanAVLTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public ShortBidirectionalIterator iterator(short from) {
/* 1236 */       return new Short2BooleanAVLTreeMap.KeyIterator(from);
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
/*      */   public ShortSortedSet keySet() {
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
/* 1291 */             return (BooleanIterator)new Short2BooleanAVLTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(boolean k) {
/* 1295 */             return Short2BooleanAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1299 */             return Short2BooleanAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1303 */             Short2BooleanAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1306 */     return this.values;
/*      */   }
/*      */   
/*      */   public ShortComparator comparator() {
/* 1310 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Short2BooleanSortedMap headMap(short to) {
/* 1314 */     return new Submap((short)0, true, to, false);
/*      */   }
/*      */   
/*      */   public Short2BooleanSortedMap tailMap(short from) {
/* 1318 */     return new Submap(from, false, (short)0, true);
/*      */   }
/*      */   
/*      */   public Short2BooleanSortedMap subMap(short from, short to) {
/* 1322 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractShort2BooleanSortedMap
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     short from;
/*      */ 
/*      */ 
/*      */     
/*      */     short to;
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
/*      */     protected transient ObjectSortedSet<Short2BooleanMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ShortSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(short from, boolean bottom, short to, boolean top) {
/* 1366 */       if (!bottom && !top && Short2BooleanAVLTreeMap.this.compare(from, to) > 0)
/* 1367 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1368 */       this.from = from;
/* 1369 */       this.bottom = bottom;
/* 1370 */       this.to = to;
/* 1371 */       this.top = top;
/* 1372 */       this.defRetValue = Short2BooleanAVLTreeMap.this.defRetValue;
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
/*      */     final boolean in(short k) {
/* 1390 */       return ((this.bottom || Short2BooleanAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Short2BooleanAVLTreeMap.this
/* 1391 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Short2BooleanMap.Entry> short2BooleanEntrySet() {
/* 1395 */       if (this.entries == null)
/* 1396 */         this.entries = (ObjectSortedSet<Short2BooleanMap.Entry>)new AbstractObjectSortedSet<Short2BooleanMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Short2BooleanMap.Entry> iterator() {
/* 1399 */               return (ObjectBidirectionalIterator<Short2BooleanMap.Entry>)new Short2BooleanAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Short2BooleanMap.Entry> iterator(Short2BooleanMap.Entry from) {
/* 1404 */               return (ObjectBidirectionalIterator<Short2BooleanMap.Entry>)new Short2BooleanAVLTreeMap.Submap.SubmapEntryIterator(from.getShortKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Short2BooleanMap.Entry> comparator() {
/* 1408 */               return Short2BooleanAVLTreeMap.this.short2BooleanEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1413 */               if (!(o instanceof Map.Entry))
/* 1414 */                 return false; 
/* 1415 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1416 */               if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1417 */                 return false; 
/* 1418 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1419 */                 return false; 
/* 1420 */               Short2BooleanAVLTreeMap.Entry f = Short2BooleanAVLTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1421 */               return (f != null && Short2BooleanAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1426 */               if (!(o instanceof Map.Entry))
/* 1427 */                 return false; 
/* 1428 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1429 */               if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1430 */                 return false; 
/* 1431 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1432 */                 return false; 
/* 1433 */               Short2BooleanAVLTreeMap.Entry f = Short2BooleanAVLTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1434 */               if (f != null && Short2BooleanAVLTreeMap.Submap.this.in(f.key))
/* 1435 */                 Short2BooleanAVLTreeMap.Submap.this.remove(f.key); 
/* 1436 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1440 */               int c = 0;
/* 1441 */               for (ObjectBidirectionalIterator<Short2BooleanMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1442 */                 c++; 
/* 1443 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1447 */               return !(new Short2BooleanAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1451 */               Short2BooleanAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Short2BooleanMap.Entry first() {
/* 1455 */               return Short2BooleanAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Short2BooleanMap.Entry last() {
/* 1459 */               return Short2BooleanAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Short2BooleanMap.Entry> subSet(Short2BooleanMap.Entry from, Short2BooleanMap.Entry to) {
/* 1464 */               return Short2BooleanAVLTreeMap.Submap.this.subMap(from.getShortKey(), to.getShortKey()).short2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Short2BooleanMap.Entry> headSet(Short2BooleanMap.Entry to) {
/* 1468 */               return Short2BooleanAVLTreeMap.Submap.this.headMap(to.getShortKey()).short2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Short2BooleanMap.Entry> tailSet(Short2BooleanMap.Entry from) {
/* 1472 */               return Short2BooleanAVLTreeMap.Submap.this.tailMap(from.getShortKey()).short2BooleanEntrySet();
/*      */             }
/*      */           }; 
/* 1475 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractShort2BooleanSortedMap.KeySet {
/*      */       public ShortBidirectionalIterator iterator() {
/* 1480 */         return new Short2BooleanAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public ShortBidirectionalIterator iterator(short from) {
/* 1484 */         return new Short2BooleanAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public ShortSortedSet keySet() {
/* 1489 */       if (this.keys == null)
/* 1490 */         this.keys = new KeySet(); 
/* 1491 */       return this.keys;
/*      */     }
/*      */     
/*      */     public BooleanCollection values() {
/* 1495 */       if (this.values == null)
/* 1496 */         this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */           {
/*      */             public BooleanIterator iterator() {
/* 1499 */               return (BooleanIterator)new Short2BooleanAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(boolean k) {
/* 1503 */               return Short2BooleanAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1507 */               return Short2BooleanAVLTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1511 */               Short2BooleanAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1514 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(short k) {
/* 1519 */       return (in(k) && Short2BooleanAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(boolean v) {
/* 1523 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1525 */       while (i.hasNext()) {
/* 1526 */         boolean ev = (i.nextEntry()).value;
/* 1527 */         if (ev == v)
/* 1528 */           return true; 
/*      */       } 
/* 1530 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean get(short k) {
/* 1536 */       short kk = k; Short2BooleanAVLTreeMap.Entry e;
/* 1537 */       return (in(kk) && (e = Short2BooleanAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public boolean put(short k, boolean v) {
/* 1541 */       Short2BooleanAVLTreeMap.this.modified = false;
/* 1542 */       if (!in(k))
/* 1543 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1544 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1545 */       boolean oldValue = Short2BooleanAVLTreeMap.this.put(k, v);
/* 1546 */       return Short2BooleanAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(short k) {
/* 1551 */       Short2BooleanAVLTreeMap.this.modified = false;
/* 1552 */       if (!in(k))
/* 1553 */         return this.defRetValue; 
/* 1554 */       boolean oldValue = Short2BooleanAVLTreeMap.this.remove(k);
/* 1555 */       return Short2BooleanAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1559 */       SubmapIterator i = new SubmapIterator();
/* 1560 */       int n = 0;
/* 1561 */       while (i.hasNext()) {
/* 1562 */         n++;
/* 1563 */         i.nextEntry();
/*      */       } 
/* 1565 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1569 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public ShortComparator comparator() {
/* 1573 */       return Short2BooleanAVLTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Short2BooleanSortedMap headMap(short to) {
/* 1577 */       if (this.top)
/* 1578 */         return new Submap(this.from, this.bottom, to, false); 
/* 1579 */       return (Short2BooleanAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Short2BooleanSortedMap tailMap(short from) {
/* 1583 */       if (this.bottom)
/* 1584 */         return new Submap(from, false, this.to, this.top); 
/* 1585 */       return (Short2BooleanAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Short2BooleanSortedMap subMap(short from, short to) {
/* 1589 */       if (this.top && this.bottom)
/* 1590 */         return new Submap(from, false, to, false); 
/* 1591 */       if (!this.top)
/* 1592 */         to = (Short2BooleanAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1593 */       if (!this.bottom)
/* 1594 */         from = (Short2BooleanAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1595 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1596 */         return this; 
/* 1597 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Short2BooleanAVLTreeMap.Entry firstEntry() {
/*      */       Short2BooleanAVLTreeMap.Entry e;
/* 1606 */       if (Short2BooleanAVLTreeMap.this.tree == null) {
/* 1607 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1611 */       if (this.bottom) {
/* 1612 */         e = Short2BooleanAVLTreeMap.this.firstEntry;
/*      */       } else {
/* 1614 */         e = Short2BooleanAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1616 */         if (Short2BooleanAVLTreeMap.this.compare(e.key, this.from) < 0) {
/* 1617 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1621 */       if (e == null || (!this.top && Short2BooleanAVLTreeMap.this.compare(e.key, this.to) >= 0))
/* 1622 */         return null; 
/* 1623 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Short2BooleanAVLTreeMap.Entry lastEntry() {
/*      */       Short2BooleanAVLTreeMap.Entry e;
/* 1632 */       if (Short2BooleanAVLTreeMap.this.tree == null) {
/* 1633 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1637 */       if (this.top) {
/* 1638 */         e = Short2BooleanAVLTreeMap.this.lastEntry;
/*      */       } else {
/* 1640 */         e = Short2BooleanAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1642 */         if (Short2BooleanAVLTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1643 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1647 */       if (e == null || (!this.bottom && Short2BooleanAVLTreeMap.this.compare(e.key, this.from) < 0))
/* 1648 */         return null; 
/* 1649 */       return e;
/*      */     }
/*      */     
/*      */     public short firstShortKey() {
/* 1653 */       Short2BooleanAVLTreeMap.Entry e = firstEntry();
/* 1654 */       if (e == null)
/* 1655 */         throw new NoSuchElementException(); 
/* 1656 */       return e.key;
/*      */     }
/*      */     
/*      */     public short lastShortKey() {
/* 1660 */       Short2BooleanAVLTreeMap.Entry e = lastEntry();
/* 1661 */       if (e == null)
/* 1662 */         throw new NoSuchElementException(); 
/* 1663 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Short2BooleanAVLTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1676 */         this.next = Short2BooleanAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(short k) {
/* 1679 */         this();
/* 1680 */         if (this.next != null)
/* 1681 */           if (!Short2BooleanAVLTreeMap.Submap.this.bottom && Short2BooleanAVLTreeMap.this.compare(k, this.next.key) < 0) {
/* 1682 */             this.prev = null;
/* 1683 */           } else if (!Short2BooleanAVLTreeMap.Submap.this.top && Short2BooleanAVLTreeMap.this.compare(k, (this.prev = Short2BooleanAVLTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1684 */             this.next = null;
/*      */           } else {
/* 1686 */             this.next = Short2BooleanAVLTreeMap.this.locateKey(k);
/* 1687 */             if (Short2BooleanAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1688 */               this.prev = this.next;
/* 1689 */               this.next = this.next.next();
/*      */             } else {
/* 1691 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1697 */         this.prev = this.prev.prev();
/* 1698 */         if (!Short2BooleanAVLTreeMap.Submap.this.bottom && this.prev != null && Short2BooleanAVLTreeMap.this.compare(this.prev.key, Short2BooleanAVLTreeMap.Submap.this.from) < 0)
/* 1699 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1703 */         this.next = this.next.next();
/* 1704 */         if (!Short2BooleanAVLTreeMap.Submap.this.top && this.next != null && Short2BooleanAVLTreeMap.this.compare(this.next.key, Short2BooleanAVLTreeMap.Submap.this.to) >= 0)
/* 1705 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Short2BooleanMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(short k) {
/* 1712 */         super(k);
/*      */       }
/*      */       
/*      */       public Short2BooleanMap.Entry next() {
/* 1716 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Short2BooleanMap.Entry previous() {
/* 1720 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements ShortListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(short from) {
/* 1738 */         super(from);
/*      */       }
/*      */       
/*      */       public short nextShort() {
/* 1742 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public short previousShort() {
/* 1746 */         return (previousEntry()).key;
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
/* 1762 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public boolean previousBoolean() {
/* 1766 */         return (previousEntry()).value;
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
/*      */   public Short2BooleanAVLTreeMap clone() {
/*      */     Short2BooleanAVLTreeMap c;
/*      */     try {
/* 1785 */       c = (Short2BooleanAVLTreeMap)super.clone();
/* 1786 */     } catch (CloneNotSupportedException cantHappen) {
/* 1787 */       throw new InternalError();
/*      */     } 
/* 1789 */     c.keys = null;
/* 1790 */     c.values = null;
/* 1791 */     c.entries = null;
/* 1792 */     c.allocatePaths();
/* 1793 */     if (this.count != 0) {
/*      */       
/* 1795 */       Entry rp = new Entry(), rq = new Entry();
/* 1796 */       Entry p = rp;
/* 1797 */       rp.left(this.tree);
/* 1798 */       Entry q = rq;
/* 1799 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1801 */         if (!p.pred()) {
/* 1802 */           Entry e = p.left.clone();
/* 1803 */           e.pred(q.left);
/* 1804 */           e.succ(q);
/* 1805 */           q.left(e);
/* 1806 */           p = p.left;
/* 1807 */           q = q.left;
/*      */         } else {
/* 1809 */           while (p.succ()) {
/* 1810 */             p = p.right;
/* 1811 */             if (p == null) {
/* 1812 */               q.right = null;
/* 1813 */               c.tree = rq.left;
/* 1814 */               c.firstEntry = c.tree;
/* 1815 */               while (c.firstEntry.left != null)
/* 1816 */                 c.firstEntry = c.firstEntry.left; 
/* 1817 */               c.lastEntry = c.tree;
/* 1818 */               while (c.lastEntry.right != null)
/* 1819 */                 c.lastEntry = c.lastEntry.right; 
/* 1820 */               return c;
/*      */             } 
/* 1822 */             q = q.right;
/*      */           } 
/* 1824 */           p = p.right;
/* 1825 */           q = q.right;
/*      */         } 
/* 1827 */         if (!p.succ()) {
/* 1828 */           Entry e = p.right.clone();
/* 1829 */           e.succ(q.right);
/* 1830 */           e.pred(q);
/* 1831 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1835 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1838 */     int n = this.count;
/* 1839 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1841 */     s.defaultWriteObject();
/* 1842 */     while (n-- != 0) {
/* 1843 */       Entry e = i.nextEntry();
/* 1844 */       s.writeShort(e.key);
/* 1845 */       s.writeBoolean(e.value);
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
/* 1866 */     if (n == 1) {
/* 1867 */       Entry entry = new Entry(s.readShort(), s.readBoolean());
/* 1868 */       entry.pred(pred);
/* 1869 */       entry.succ(succ);
/* 1870 */       return entry;
/*      */     } 
/* 1872 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1877 */       Entry entry = new Entry(s.readShort(), s.readBoolean());
/* 1878 */       entry.right(new Entry(s.readShort(), s.readBoolean()));
/* 1879 */       entry.right.pred(entry);
/* 1880 */       entry.balance(1);
/* 1881 */       entry.pred(pred);
/* 1882 */       entry.right.succ(succ);
/* 1883 */       return entry;
/*      */     } 
/*      */     
/* 1886 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1887 */     Entry top = new Entry();
/* 1888 */     top.left(readTree(s, leftN, pred, top));
/* 1889 */     top.key = s.readShort();
/* 1890 */     top.value = s.readBoolean();
/* 1891 */     top.right(readTree(s, rightN, top, succ));
/* 1892 */     if (n == (n & -n))
/* 1893 */       top.balance(1); 
/* 1894 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1897 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1902 */     setActualComparator();
/* 1903 */     allocatePaths();
/* 1904 */     if (this.count != 0) {
/* 1905 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1907 */       Entry e = this.tree;
/* 1908 */       while (e.left() != null)
/* 1909 */         e = e.left(); 
/* 1910 */       this.firstEntry = e;
/* 1911 */       e = this.tree;
/* 1912 */       while (e.right() != null)
/* 1913 */         e = e.right(); 
/* 1914 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2BooleanAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */