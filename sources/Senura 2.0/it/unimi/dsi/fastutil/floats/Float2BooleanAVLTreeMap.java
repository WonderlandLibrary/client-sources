/*      */ package it.unimi.dsi.fastutil.floats;
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
/*      */ public class Float2BooleanAVLTreeMap
/*      */   extends AbstractFloat2BooleanSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Float2BooleanMap.Entry> entries;
/*      */   protected transient FloatSortedSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Float> storedComparator;
/*      */   protected transient FloatComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Float2BooleanAVLTreeMap() {
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
/*   89 */     this.actualComparator = FloatComparators.asFloatComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2BooleanAVLTreeMap(Comparator<? super Float> c) {
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
/*      */   public Float2BooleanAVLTreeMap(Map<? extends Float, ? extends Boolean> m) {
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
/*      */   public Float2BooleanAVLTreeMap(SortedMap<Float, Boolean> m) {
/*  120 */     this(m.comparator());
/*  121 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2BooleanAVLTreeMap(Float2BooleanMap m) {
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
/*      */   public Float2BooleanAVLTreeMap(Float2BooleanSortedMap m) {
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
/*      */   public Float2BooleanAVLTreeMap(float[] k, boolean[] v, Comparator<? super Float> c) {
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
/*      */   public Float2BooleanAVLTreeMap(float[] k, boolean[] v) {
/*  176 */     this(k, v, (Comparator<? super Float>)null);
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
/*      */   final int compare(float k1, float k2) {
/*  204 */     return (this.actualComparator == null) ? Float.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry findKey(float k) {
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
/*      */   final Entry locateKey(float k) {
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
/*      */   public boolean put(float k, boolean v) {
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
/*      */   private Entry add(float k) {
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
/*      */   public boolean remove(float k) {
/*  459 */     this.modified = false;
/*  460 */     if (this.tree == null) {
/*  461 */       return this.defRetValue;
/*      */     }
/*  463 */     Entry p = this.tree, q = null;
/*  464 */     boolean dir = false;
/*  465 */     float kk = k;
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
/*      */     extends AbstractFloat2BooleanMap.BasicEntry
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
/*  748 */       super(0.0F, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(float k, boolean v) {
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
/*  933 */       Map.Entry<Float, Boolean> e = (Map.Entry<Float, Boolean>)o;
/*  934 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && this.value == ((Boolean)e
/*  935 */         .getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  939 */       return HashCommon.float2int(this.key) ^ (this.value ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  943 */       return this.key + "=>" + this.value;
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
/*      */   public boolean containsKey(float k) {
/*  964 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  968 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  972 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean get(float k) {
/*  977 */     Entry e = findKey(k);
/*  978 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public float firstFloatKey() {
/*  982 */     if (this.tree == null)
/*  983 */       throw new NoSuchElementException(); 
/*  984 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public float lastFloatKey() {
/*  988 */     if (this.tree == null)
/*  989 */       throw new NoSuchElementException(); 
/*  990 */     return this.lastEntry.key;
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
/*      */     Float2BooleanAVLTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Float2BooleanAVLTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Float2BooleanAVLTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1021 */     int index = 0;
/*      */     TreeIterator() {
/* 1023 */       this.next = Float2BooleanAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(float k) {
/* 1026 */       if ((this.next = Float2BooleanAVLTreeMap.this.locateKey(k)) != null)
/* 1027 */         if (Float2BooleanAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1028 */           this.prev = this.next;
/* 1029 */           this.next = this.next.next();
/*      */         } else {
/* 1031 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/* 1035 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1038 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1041 */       this.next = this.next.next();
/*      */     }
/*      */     Float2BooleanAVLTreeMap.Entry nextEntry() {
/* 1044 */       if (!hasNext())
/* 1045 */         throw new NoSuchElementException(); 
/* 1046 */       this.curr = this.prev = this.next;
/* 1047 */       this.index++;
/* 1048 */       updateNext();
/* 1049 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1052 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Float2BooleanAVLTreeMap.Entry previousEntry() {
/* 1055 */       if (!hasPrevious())
/* 1056 */         throw new NoSuchElementException(); 
/* 1057 */       this.curr = this.next = this.prev;
/* 1058 */       this.index--;
/* 1059 */       updatePrevious();
/* 1060 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1063 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1066 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1069 */       if (this.curr == null) {
/* 1070 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1075 */       if (this.curr == this.prev)
/* 1076 */         this.index--; 
/* 1077 */       this.next = this.prev = this.curr;
/* 1078 */       updatePrevious();
/* 1079 */       updateNext();
/* 1080 */       Float2BooleanAVLTreeMap.this.remove(this.curr.key);
/* 1081 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1084 */       int i = n;
/* 1085 */       while (i-- != 0 && hasNext())
/* 1086 */         nextEntry(); 
/* 1087 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1090 */       int i = n;
/* 1091 */       while (i-- != 0 && hasPrevious())
/* 1092 */         previousEntry(); 
/* 1093 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Float2BooleanMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(float k) {
/* 1106 */       super(k);
/*      */     }
/*      */     
/*      */     public Float2BooleanMap.Entry next() {
/* 1110 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Float2BooleanMap.Entry previous() {
/* 1114 */       return previousEntry();
/*      */     }
/*      */     
/*      */     public void set(Float2BooleanMap.Entry ok) {
/* 1118 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Float2BooleanMap.Entry ok) {
/* 1122 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Float2BooleanMap.Entry> float2BooleanEntrySet() {
/* 1127 */     if (this.entries == null)
/* 1128 */       this.entries = (ObjectSortedSet<Float2BooleanMap.Entry>)new AbstractObjectSortedSet<Float2BooleanMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Float2BooleanMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Float2BooleanMap.Entry> comparator() {
/* 1133 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Float2BooleanMap.Entry> iterator() {
/* 1137 */             return (ObjectBidirectionalIterator<Float2BooleanMap.Entry>)new Float2BooleanAVLTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Float2BooleanMap.Entry> iterator(Float2BooleanMap.Entry from) {
/* 1141 */             return (ObjectBidirectionalIterator<Float2BooleanMap.Entry>)new Float2BooleanAVLTreeMap.EntryIterator(from.getFloatKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1146 */             if (!(o instanceof Map.Entry))
/* 1147 */               return false; 
/* 1148 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1149 */             if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1150 */               return false; 
/* 1151 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1152 */               return false; 
/* 1153 */             Float2BooleanAVLTreeMap.Entry f = Float2BooleanAVLTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1154 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1159 */             if (!(o instanceof Map.Entry))
/* 1160 */               return false; 
/* 1161 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1162 */             if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1163 */               return false; 
/* 1164 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1165 */               return false; 
/* 1166 */             Float2BooleanAVLTreeMap.Entry f = Float2BooleanAVLTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1167 */             if (f == null || f.getBooleanValue() != ((Boolean)e.getValue()).booleanValue())
/* 1168 */               return false; 
/* 1169 */             Float2BooleanAVLTreeMap.this.remove(f.key);
/* 1170 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1174 */             return Float2BooleanAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1178 */             Float2BooleanAVLTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Float2BooleanMap.Entry first() {
/* 1182 */             return Float2BooleanAVLTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Float2BooleanMap.Entry last() {
/* 1186 */             return Float2BooleanAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Float2BooleanMap.Entry> subSet(Float2BooleanMap.Entry from, Float2BooleanMap.Entry to) {
/* 1191 */             return Float2BooleanAVLTreeMap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Float2BooleanMap.Entry> headSet(Float2BooleanMap.Entry to) {
/* 1195 */             return Float2BooleanAVLTreeMap.this.headMap(to.getFloatKey()).float2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Float2BooleanMap.Entry> tailSet(Float2BooleanMap.Entry from) {
/* 1199 */             return Float2BooleanAVLTreeMap.this.tailMap(from.getFloatKey()).float2BooleanEntrySet();
/*      */           }
/*      */         }; 
/* 1202 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends TreeIterator
/*      */     implements FloatListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(float k) {
/* 1218 */       super(k);
/*      */     }
/*      */     
/*      */     public float nextFloat() {
/* 1222 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public float previousFloat() {
/* 1226 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractFloat2BooleanSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public FloatBidirectionalIterator iterator() {
/* 1233 */       return new Float2BooleanAVLTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public FloatBidirectionalIterator iterator(float from) {
/* 1237 */       return new Float2BooleanAVLTreeMap.KeyIterator(from);
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
/*      */   public FloatSortedSet keySet() {
/* 1252 */     if (this.keys == null)
/* 1253 */       this.keys = new KeySet(); 
/* 1254 */     return this.keys;
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
/* 1269 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public boolean previousBoolean() {
/* 1273 */       return (previousEntry()).value;
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
/* 1288 */     if (this.values == null)
/* 1289 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1292 */             return (BooleanIterator)new Float2BooleanAVLTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(boolean k) {
/* 1296 */             return Float2BooleanAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1300 */             return Float2BooleanAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1304 */             Float2BooleanAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1307 */     return this.values;
/*      */   }
/*      */   
/*      */   public FloatComparator comparator() {
/* 1311 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Float2BooleanSortedMap headMap(float to) {
/* 1315 */     return new Submap(0.0F, true, to, false);
/*      */   }
/*      */   
/*      */   public Float2BooleanSortedMap tailMap(float from) {
/* 1319 */     return new Submap(from, false, 0.0F, true);
/*      */   }
/*      */   
/*      */   public Float2BooleanSortedMap subMap(float from, float to) {
/* 1323 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractFloat2BooleanSortedMap
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     float from;
/*      */ 
/*      */ 
/*      */     
/*      */     float to;
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
/*      */     protected transient ObjectSortedSet<Float2BooleanMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient FloatSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(float from, boolean bottom, float to, boolean top) {
/* 1367 */       if (!bottom && !top && Float2BooleanAVLTreeMap.this.compare(from, to) > 0)
/* 1368 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1369 */       this.from = from;
/* 1370 */       this.bottom = bottom;
/* 1371 */       this.to = to;
/* 1372 */       this.top = top;
/* 1373 */       this.defRetValue = Float2BooleanAVLTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1377 */       SubmapIterator i = new SubmapIterator();
/* 1378 */       while (i.hasNext()) {
/* 1379 */         i.nextEntry();
/* 1380 */         i.remove();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean in(float k) {
/* 1391 */       return ((this.bottom || Float2BooleanAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Float2BooleanAVLTreeMap.this
/* 1392 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Float2BooleanMap.Entry> float2BooleanEntrySet() {
/* 1396 */       if (this.entries == null)
/* 1397 */         this.entries = (ObjectSortedSet<Float2BooleanMap.Entry>)new AbstractObjectSortedSet<Float2BooleanMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Float2BooleanMap.Entry> iterator() {
/* 1400 */               return (ObjectBidirectionalIterator<Float2BooleanMap.Entry>)new Float2BooleanAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Float2BooleanMap.Entry> iterator(Float2BooleanMap.Entry from) {
/* 1405 */               return (ObjectBidirectionalIterator<Float2BooleanMap.Entry>)new Float2BooleanAVLTreeMap.Submap.SubmapEntryIterator(from.getFloatKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Float2BooleanMap.Entry> comparator() {
/* 1409 */               return Float2BooleanAVLTreeMap.this.float2BooleanEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1414 */               if (!(o instanceof Map.Entry))
/* 1415 */                 return false; 
/* 1416 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1417 */               if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1418 */                 return false; 
/* 1419 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1420 */                 return false; 
/* 1421 */               Float2BooleanAVLTreeMap.Entry f = Float2BooleanAVLTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1422 */               return (f != null && Float2BooleanAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1427 */               if (!(o instanceof Map.Entry))
/* 1428 */                 return false; 
/* 1429 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1430 */               if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1431 */                 return false; 
/* 1432 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1433 */                 return false; 
/* 1434 */               Float2BooleanAVLTreeMap.Entry f = Float2BooleanAVLTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1435 */               if (f != null && Float2BooleanAVLTreeMap.Submap.this.in(f.key))
/* 1436 */                 Float2BooleanAVLTreeMap.Submap.this.remove(f.key); 
/* 1437 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1441 */               int c = 0;
/* 1442 */               for (ObjectBidirectionalIterator<Float2BooleanMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1443 */                 c++; 
/* 1444 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1448 */               return !(new Float2BooleanAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1452 */               Float2BooleanAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Float2BooleanMap.Entry first() {
/* 1456 */               return Float2BooleanAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Float2BooleanMap.Entry last() {
/* 1460 */               return Float2BooleanAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Float2BooleanMap.Entry> subSet(Float2BooleanMap.Entry from, Float2BooleanMap.Entry to) {
/* 1465 */               return Float2BooleanAVLTreeMap.Submap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Float2BooleanMap.Entry> headSet(Float2BooleanMap.Entry to) {
/* 1469 */               return Float2BooleanAVLTreeMap.Submap.this.headMap(to.getFloatKey()).float2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Float2BooleanMap.Entry> tailSet(Float2BooleanMap.Entry from) {
/* 1473 */               return Float2BooleanAVLTreeMap.Submap.this.tailMap(from.getFloatKey()).float2BooleanEntrySet();
/*      */             }
/*      */           }; 
/* 1476 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractFloat2BooleanSortedMap.KeySet {
/*      */       public FloatBidirectionalIterator iterator() {
/* 1481 */         return new Float2BooleanAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public FloatBidirectionalIterator iterator(float from) {
/* 1485 */         return new Float2BooleanAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public FloatSortedSet keySet() {
/* 1490 */       if (this.keys == null)
/* 1491 */         this.keys = new KeySet(); 
/* 1492 */       return this.keys;
/*      */     }
/*      */     
/*      */     public BooleanCollection values() {
/* 1496 */       if (this.values == null)
/* 1497 */         this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */           {
/*      */             public BooleanIterator iterator() {
/* 1500 */               return (BooleanIterator)new Float2BooleanAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(boolean k) {
/* 1504 */               return Float2BooleanAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1508 */               return Float2BooleanAVLTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1512 */               Float2BooleanAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1515 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(float k) {
/* 1520 */       return (in(k) && Float2BooleanAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(boolean v) {
/* 1524 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1526 */       while (i.hasNext()) {
/* 1527 */         boolean ev = (i.nextEntry()).value;
/* 1528 */         if (ev == v)
/* 1529 */           return true; 
/*      */       } 
/* 1531 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean get(float k) {
/* 1537 */       float kk = k; Float2BooleanAVLTreeMap.Entry e;
/* 1538 */       return (in(kk) && (e = Float2BooleanAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public boolean put(float k, boolean v) {
/* 1542 */       Float2BooleanAVLTreeMap.this.modified = false;
/* 1543 */       if (!in(k))
/* 1544 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1545 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1546 */       boolean oldValue = Float2BooleanAVLTreeMap.this.put(k, v);
/* 1547 */       return Float2BooleanAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(float k) {
/* 1552 */       Float2BooleanAVLTreeMap.this.modified = false;
/* 1553 */       if (!in(k))
/* 1554 */         return this.defRetValue; 
/* 1555 */       boolean oldValue = Float2BooleanAVLTreeMap.this.remove(k);
/* 1556 */       return Float2BooleanAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1560 */       SubmapIterator i = new SubmapIterator();
/* 1561 */       int n = 0;
/* 1562 */       while (i.hasNext()) {
/* 1563 */         n++;
/* 1564 */         i.nextEntry();
/*      */       } 
/* 1566 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1570 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public FloatComparator comparator() {
/* 1574 */       return Float2BooleanAVLTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Float2BooleanSortedMap headMap(float to) {
/* 1578 */       if (this.top)
/* 1579 */         return new Submap(this.from, this.bottom, to, false); 
/* 1580 */       return (Float2BooleanAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Float2BooleanSortedMap tailMap(float from) {
/* 1584 */       if (this.bottom)
/* 1585 */         return new Submap(from, false, this.to, this.top); 
/* 1586 */       return (Float2BooleanAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Float2BooleanSortedMap subMap(float from, float to) {
/* 1590 */       if (this.top && this.bottom)
/* 1591 */         return new Submap(from, false, to, false); 
/* 1592 */       if (!this.top)
/* 1593 */         to = (Float2BooleanAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1594 */       if (!this.bottom)
/* 1595 */         from = (Float2BooleanAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1596 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1597 */         return this; 
/* 1598 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Float2BooleanAVLTreeMap.Entry firstEntry() {
/*      */       Float2BooleanAVLTreeMap.Entry e;
/* 1607 */       if (Float2BooleanAVLTreeMap.this.tree == null) {
/* 1608 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1612 */       if (this.bottom) {
/* 1613 */         e = Float2BooleanAVLTreeMap.this.firstEntry;
/*      */       } else {
/* 1615 */         e = Float2BooleanAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1617 */         if (Float2BooleanAVLTreeMap.this.compare(e.key, this.from) < 0) {
/* 1618 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1622 */       if (e == null || (!this.top && Float2BooleanAVLTreeMap.this.compare(e.key, this.to) >= 0))
/* 1623 */         return null; 
/* 1624 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Float2BooleanAVLTreeMap.Entry lastEntry() {
/*      */       Float2BooleanAVLTreeMap.Entry e;
/* 1633 */       if (Float2BooleanAVLTreeMap.this.tree == null) {
/* 1634 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1638 */       if (this.top) {
/* 1639 */         e = Float2BooleanAVLTreeMap.this.lastEntry;
/*      */       } else {
/* 1641 */         e = Float2BooleanAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1643 */         if (Float2BooleanAVLTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1644 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1648 */       if (e == null || (!this.bottom && Float2BooleanAVLTreeMap.this.compare(e.key, this.from) < 0))
/* 1649 */         return null; 
/* 1650 */       return e;
/*      */     }
/*      */     
/*      */     public float firstFloatKey() {
/* 1654 */       Float2BooleanAVLTreeMap.Entry e = firstEntry();
/* 1655 */       if (e == null)
/* 1656 */         throw new NoSuchElementException(); 
/* 1657 */       return e.key;
/*      */     }
/*      */     
/*      */     public float lastFloatKey() {
/* 1661 */       Float2BooleanAVLTreeMap.Entry e = lastEntry();
/* 1662 */       if (e == null)
/* 1663 */         throw new NoSuchElementException(); 
/* 1664 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Float2BooleanAVLTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1677 */         this.next = Float2BooleanAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(float k) {
/* 1680 */         this();
/* 1681 */         if (this.next != null)
/* 1682 */           if (!Float2BooleanAVLTreeMap.Submap.this.bottom && Float2BooleanAVLTreeMap.this.compare(k, this.next.key) < 0) {
/* 1683 */             this.prev = null;
/* 1684 */           } else if (!Float2BooleanAVLTreeMap.Submap.this.top && Float2BooleanAVLTreeMap.this.compare(k, (this.prev = Float2BooleanAVLTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1685 */             this.next = null;
/*      */           } else {
/* 1687 */             this.next = Float2BooleanAVLTreeMap.this.locateKey(k);
/* 1688 */             if (Float2BooleanAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1689 */               this.prev = this.next;
/* 1690 */               this.next = this.next.next();
/*      */             } else {
/* 1692 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1698 */         this.prev = this.prev.prev();
/* 1699 */         if (!Float2BooleanAVLTreeMap.Submap.this.bottom && this.prev != null && Float2BooleanAVLTreeMap.this.compare(this.prev.key, Float2BooleanAVLTreeMap.Submap.this.from) < 0)
/* 1700 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1704 */         this.next = this.next.next();
/* 1705 */         if (!Float2BooleanAVLTreeMap.Submap.this.top && this.next != null && Float2BooleanAVLTreeMap.this.compare(this.next.key, Float2BooleanAVLTreeMap.Submap.this.to) >= 0)
/* 1706 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Float2BooleanMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(float k) {
/* 1713 */         super(k);
/*      */       }
/*      */       
/*      */       public Float2BooleanMap.Entry next() {
/* 1717 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Float2BooleanMap.Entry previous() {
/* 1721 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements FloatListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(float from) {
/* 1739 */         super(from);
/*      */       }
/*      */       
/*      */       public float nextFloat() {
/* 1743 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public float previousFloat() {
/* 1747 */         return (previousEntry()).key;
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
/* 1763 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public boolean previousBoolean() {
/* 1767 */         return (previousEntry()).value;
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
/*      */   public Float2BooleanAVLTreeMap clone() {
/*      */     Float2BooleanAVLTreeMap c;
/*      */     try {
/* 1786 */       c = (Float2BooleanAVLTreeMap)super.clone();
/* 1787 */     } catch (CloneNotSupportedException cantHappen) {
/* 1788 */       throw new InternalError();
/*      */     } 
/* 1790 */     c.keys = null;
/* 1791 */     c.values = null;
/* 1792 */     c.entries = null;
/* 1793 */     c.allocatePaths();
/* 1794 */     if (this.count != 0) {
/*      */       
/* 1796 */       Entry rp = new Entry(), rq = new Entry();
/* 1797 */       Entry p = rp;
/* 1798 */       rp.left(this.tree);
/* 1799 */       Entry q = rq;
/* 1800 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1802 */         if (!p.pred()) {
/* 1803 */           Entry e = p.left.clone();
/* 1804 */           e.pred(q.left);
/* 1805 */           e.succ(q);
/* 1806 */           q.left(e);
/* 1807 */           p = p.left;
/* 1808 */           q = q.left;
/*      */         } else {
/* 1810 */           while (p.succ()) {
/* 1811 */             p = p.right;
/* 1812 */             if (p == null) {
/* 1813 */               q.right = null;
/* 1814 */               c.tree = rq.left;
/* 1815 */               c.firstEntry = c.tree;
/* 1816 */               while (c.firstEntry.left != null)
/* 1817 */                 c.firstEntry = c.firstEntry.left; 
/* 1818 */               c.lastEntry = c.tree;
/* 1819 */               while (c.lastEntry.right != null)
/* 1820 */                 c.lastEntry = c.lastEntry.right; 
/* 1821 */               return c;
/*      */             } 
/* 1823 */             q = q.right;
/*      */           } 
/* 1825 */           p = p.right;
/* 1826 */           q = q.right;
/*      */         } 
/* 1828 */         if (!p.succ()) {
/* 1829 */           Entry e = p.right.clone();
/* 1830 */           e.succ(q.right);
/* 1831 */           e.pred(q);
/* 1832 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1836 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1839 */     int n = this.count;
/* 1840 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1842 */     s.defaultWriteObject();
/* 1843 */     while (n-- != 0) {
/* 1844 */       Entry e = i.nextEntry();
/* 1845 */       s.writeFloat(e.key);
/* 1846 */       s.writeBoolean(e.value);
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
/* 1867 */     if (n == 1) {
/* 1868 */       Entry entry = new Entry(s.readFloat(), s.readBoolean());
/* 1869 */       entry.pred(pred);
/* 1870 */       entry.succ(succ);
/* 1871 */       return entry;
/*      */     } 
/* 1873 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1878 */       Entry entry = new Entry(s.readFloat(), s.readBoolean());
/* 1879 */       entry.right(new Entry(s.readFloat(), s.readBoolean()));
/* 1880 */       entry.right.pred(entry);
/* 1881 */       entry.balance(1);
/* 1882 */       entry.pred(pred);
/* 1883 */       entry.right.succ(succ);
/* 1884 */       return entry;
/*      */     } 
/*      */     
/* 1887 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1888 */     Entry top = new Entry();
/* 1889 */     top.left(readTree(s, leftN, pred, top));
/* 1890 */     top.key = s.readFloat();
/* 1891 */     top.value = s.readBoolean();
/* 1892 */     top.right(readTree(s, rightN, top, succ));
/* 1893 */     if (n == (n & -n))
/* 1894 */       top.balance(1); 
/* 1895 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1898 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1903 */     setActualComparator();
/* 1904 */     allocatePaths();
/* 1905 */     if (this.count != 0) {
/* 1906 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1908 */       Entry e = this.tree;
/* 1909 */       while (e.left() != null)
/* 1910 */         e = e.left(); 
/* 1911 */       this.firstEntry = e;
/* 1912 */       e = this.tree;
/* 1913 */       while (e.right() != null)
/* 1914 */         e = e.right(); 
/* 1915 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2BooleanAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */