/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
/*      */ 
/*      */ public class Double2ReferenceAVLTreeMap<V>
/*      */   extends AbstractDouble2ReferenceSortedMap<V>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<V> tree;
/*      */   protected int count;
/*      */   protected transient Entry<V> firstEntry;
/*      */   protected transient Entry<V> lastEntry;
/*      */   protected transient ObjectSortedSet<Double2ReferenceMap.Entry<V>> entries;
/*      */   protected transient DoubleSortedSet keys;
/*      */   protected transient ReferenceCollection<V> values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Double> storedComparator;
/*      */   protected transient DoubleComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Double2ReferenceAVLTreeMap() {
/*   72 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   78 */     this.tree = null;
/*   79 */     this.count = 0;
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
/*   91 */     this.actualComparator = DoubleComparators.asDoubleComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ReferenceAVLTreeMap(Comparator<? super Double> c) {
/*  100 */     this();
/*  101 */     this.storedComparator = c;
/*  102 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ReferenceAVLTreeMap(Map<? extends Double, ? extends V> m) {
/*  111 */     this();
/*  112 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ReferenceAVLTreeMap(SortedMap<Double, V> m) {
/*  122 */     this(m.comparator());
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ReferenceAVLTreeMap(Double2ReferenceMap<? extends V> m) {
/*  132 */     this();
/*  133 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ReferenceAVLTreeMap(Double2ReferenceSortedMap<V> m) {
/*  143 */     this(m.comparator());
/*  144 */     putAll(m);
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
/*      */   public Double2ReferenceAVLTreeMap(double[] k, V[] v, Comparator<? super Double> c) {
/*  160 */     this(c);
/*  161 */     if (k.length != v.length) {
/*  162 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  164 */     for (int i = 0; i < k.length; i++) {
/*  165 */       put(k[i], v[i]);
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
/*      */   public Double2ReferenceAVLTreeMap(double[] k, V[] v) {
/*  178 */     this(k, v, (Comparator<? super Double>)null);
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
/*  206 */     return (this.actualComparator == null) ? Double.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry<V> findKey(double k) {
/*  218 */     Entry<V> e = this.tree;
/*      */     int cmp;
/*  220 */     while (e != null && (cmp = compare(k, e.key)) != 0)
/*  221 */       e = (cmp < 0) ? e.left() : e.right(); 
/*  222 */     return e;
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
/*      */   final Entry<V> locateKey(double k) {
/*  234 */     Entry<V> e = this.tree, last = this.tree;
/*  235 */     int cmp = 0;
/*  236 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  237 */       last = e;
/*  238 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  240 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  248 */     this.dirPath = new boolean[48];
/*      */   }
/*      */   
/*      */   public V put(double k, V v) {
/*  252 */     Entry<V> e = add(k);
/*  253 */     V oldValue = e.value;
/*  254 */     e.value = v;
/*  255 */     return oldValue;
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
/*      */   private Entry<V> add(double k) {
/*  272 */     this.modified = false;
/*  273 */     Entry<V> e = null;
/*  274 */     if (this.tree == null) {
/*  275 */       this.count++;
/*  276 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*  277 */       this.modified = true;
/*      */     } else {
/*  279 */       Entry<V> p = this.tree, q = null, y = this.tree, z = null, w = null;
/*  280 */       int i = 0; while (true) {
/*      */         int cmp;
/*  282 */         if ((cmp = compare(k, p.key)) == 0) {
/*  283 */           return p;
/*      */         }
/*  285 */         if (p.balance() != 0) {
/*  286 */           i = 0;
/*  287 */           z = q;
/*  288 */           y = p;
/*      */         } 
/*  290 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  291 */           if (p.succ()) {
/*  292 */             this.count++;
/*  293 */             e = new Entry<>(k, this.defRetValue);
/*  294 */             this.modified = true;
/*  295 */             if (p.right == null)
/*  296 */               this.lastEntry = e; 
/*  297 */             e.left = p;
/*  298 */             e.right = p.right;
/*  299 */             p.right(e);
/*      */             break;
/*      */           } 
/*  302 */           q = p;
/*  303 */           p = p.right; continue;
/*      */         } 
/*  305 */         if (p.pred()) {
/*  306 */           this.count++;
/*  307 */           e = new Entry<>(k, this.defRetValue);
/*  308 */           this.modified = true;
/*  309 */           if (p.left == null)
/*  310 */             this.firstEntry = e; 
/*  311 */           e.right = p;
/*  312 */           e.left = p.left;
/*  313 */           p.left(e);
/*      */           break;
/*      */         } 
/*  316 */         q = p;
/*  317 */         p = p.left;
/*      */       } 
/*      */       
/*  320 */       p = y;
/*  321 */       i = 0;
/*  322 */       while (p != e) {
/*  323 */         if (this.dirPath[i]) {
/*  324 */           p.incBalance();
/*      */         } else {
/*  326 */           p.decBalance();
/*  327 */         }  p = this.dirPath[i++] ? p.right : p.left;
/*      */       } 
/*  329 */       if (y.balance() == -2) {
/*  330 */         Entry<V> x = y.left;
/*  331 */         if (x.balance() == -1) {
/*  332 */           w = x;
/*  333 */           if (x.succ()) {
/*  334 */             x.succ(false);
/*  335 */             y.pred(x);
/*      */           } else {
/*  337 */             y.left = x.right;
/*  338 */           }  x.right = y;
/*  339 */           x.balance(0);
/*  340 */           y.balance(0);
/*      */         } else {
/*  342 */           assert x.balance() == 1;
/*  343 */           w = x.right;
/*  344 */           x.right = w.left;
/*  345 */           w.left = x;
/*  346 */           y.left = w.right;
/*  347 */           w.right = y;
/*  348 */           if (w.balance() == -1) {
/*  349 */             x.balance(0);
/*  350 */             y.balance(1);
/*  351 */           } else if (w.balance() == 0) {
/*  352 */             x.balance(0);
/*  353 */             y.balance(0);
/*      */           } else {
/*  355 */             x.balance(-1);
/*  356 */             y.balance(0);
/*      */           } 
/*  358 */           w.balance(0);
/*  359 */           if (w.pred()) {
/*  360 */             x.succ(w);
/*  361 */             w.pred(false);
/*      */           } 
/*  363 */           if (w.succ()) {
/*  364 */             y.pred(w);
/*  365 */             w.succ(false);
/*      */           } 
/*      */         } 
/*  368 */       } else if (y.balance() == 2) {
/*  369 */         Entry<V> x = y.right;
/*  370 */         if (x.balance() == 1) {
/*  371 */           w = x;
/*  372 */           if (x.pred()) {
/*  373 */             x.pred(false);
/*  374 */             y.succ(x);
/*      */           } else {
/*  376 */             y.right = x.left;
/*  377 */           }  x.left = y;
/*  378 */           x.balance(0);
/*  379 */           y.balance(0);
/*      */         } else {
/*  381 */           assert x.balance() == -1;
/*  382 */           w = x.left;
/*  383 */           x.left = w.right;
/*  384 */           w.right = x;
/*  385 */           y.right = w.left;
/*  386 */           w.left = y;
/*  387 */           if (w.balance() == 1) {
/*  388 */             x.balance(0);
/*  389 */             y.balance(-1);
/*  390 */           } else if (w.balance() == 0) {
/*  391 */             x.balance(0);
/*  392 */             y.balance(0);
/*      */           } else {
/*  394 */             x.balance(1);
/*  395 */             y.balance(0);
/*      */           } 
/*  397 */           w.balance(0);
/*  398 */           if (w.pred()) {
/*  399 */             y.succ(w);
/*  400 */             w.pred(false);
/*      */           } 
/*  402 */           if (w.succ()) {
/*  403 */             x.pred(w);
/*  404 */             w.succ(false);
/*      */           } 
/*      */         } 
/*      */       } else {
/*  408 */         return e;
/*  409 */       }  if (z == null) {
/*  410 */         this.tree = w;
/*      */       }
/*  412 */       else if (z.left == y) {
/*  413 */         z.left = w;
/*      */       } else {
/*  415 */         z.right = w;
/*      */       } 
/*      */     } 
/*  418 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry<V> parent(Entry<V> e) {
/*  428 */     if (e == this.tree) {
/*  429 */       return null;
/*      */     }
/*  431 */     Entry<V> y = e, x = y;
/*      */     while (true) {
/*  433 */       if (y.succ()) {
/*  434 */         Entry<V> p = y.right;
/*  435 */         if (p == null || p.left != e) {
/*  436 */           while (!x.pred())
/*  437 */             x = x.left; 
/*  438 */           p = x.left;
/*      */         } 
/*  440 */         return p;
/*  441 */       }  if (x.pred()) {
/*  442 */         Entry<V> p = x.left;
/*  443 */         if (p == null || p.right != e) {
/*  444 */           while (!y.succ())
/*  445 */             y = y.right; 
/*  446 */           p = y.right;
/*      */         } 
/*  448 */         return p;
/*      */       } 
/*  450 */       x = x.left;
/*  451 */       y = y.right;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V remove(double k) {
/*  461 */     this.modified = false;
/*  462 */     if (this.tree == null) {
/*  463 */       return this.defRetValue;
/*      */     }
/*  465 */     Entry<V> p = this.tree, q = null;
/*  466 */     boolean dir = false;
/*  467 */     double kk = k;
/*      */     int cmp;
/*  469 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  471 */       if (dir = (cmp > 0)) {
/*  472 */         q = p;
/*  473 */         if ((p = p.right()) == null)
/*  474 */           return this.defRetValue;  continue;
/*      */       } 
/*  476 */       q = p;
/*  477 */       if ((p = p.left()) == null) {
/*  478 */         return this.defRetValue;
/*      */       }
/*      */     } 
/*  481 */     if (p.left == null)
/*  482 */       this.firstEntry = p.next(); 
/*  483 */     if (p.right == null)
/*  484 */       this.lastEntry = p.prev(); 
/*  485 */     if (p.succ())
/*  486 */     { if (p.pred())
/*  487 */       { if (q != null)
/*  488 */         { if (dir) {
/*  489 */             q.succ(p.right);
/*      */           } else {
/*  491 */             q.pred(p.left);
/*      */           }  }
/*  493 */         else { this.tree = dir ? p.right : p.left; }
/*      */          }
/*  495 */       else { (p.prev()).right = p.right;
/*  496 */         if (q != null)
/*  497 */         { if (dir) {
/*  498 */             q.right = p.left;
/*      */           } else {
/*  500 */             q.left = p.left;
/*      */           }  }
/*  502 */         else { this.tree = p.left; }
/*      */          }
/*      */        }
/*  505 */     else { Entry<V> r = p.right;
/*  506 */       if (r.pred()) {
/*  507 */         r.left = p.left;
/*  508 */         r.pred(p.pred());
/*  509 */         if (!r.pred())
/*  510 */           (r.prev()).right = r; 
/*  511 */         if (q != null)
/*  512 */         { if (dir) {
/*  513 */             q.right = r;
/*      */           } else {
/*  515 */             q.left = r;
/*      */           }  }
/*  517 */         else { this.tree = r; }
/*  518 */          r.balance(p.balance());
/*  519 */         q = r;
/*  520 */         dir = true;
/*      */       } else {
/*      */         Entry<V> s;
/*      */         while (true) {
/*  524 */           s = r.left;
/*  525 */           if (s.pred())
/*      */             break; 
/*  527 */           r = s;
/*      */         } 
/*  529 */         if (s.succ()) {
/*  530 */           r.pred(s);
/*      */         } else {
/*  532 */           r.left = s.right;
/*  533 */         }  s.left = p.left;
/*  534 */         if (!p.pred()) {
/*  535 */           (p.prev()).right = s;
/*  536 */           s.pred(false);
/*      */         } 
/*  538 */         s.right = p.right;
/*  539 */         s.succ(false);
/*  540 */         if (q != null)
/*  541 */         { if (dir) {
/*  542 */             q.right = s;
/*      */           } else {
/*  544 */             q.left = s;
/*      */           }  }
/*  546 */         else { this.tree = s; }
/*  547 */          s.balance(p.balance());
/*  548 */         q = r;
/*  549 */         dir = false;
/*      */       }  }
/*      */ 
/*      */     
/*  553 */     while (q != null) {
/*  554 */       Entry<V> y = q;
/*  555 */       q = parent(y);
/*  556 */       if (!dir) {
/*  557 */         dir = (q != null && q.left != y);
/*  558 */         y.incBalance();
/*  559 */         if (y.balance() == 1)
/*      */           break; 
/*  561 */         if (y.balance() == 2) {
/*  562 */           Entry<V> x = y.right;
/*  563 */           assert x != null;
/*  564 */           if (x.balance() == -1) {
/*      */             
/*  566 */             assert x.balance() == -1;
/*  567 */             Entry<V> w = x.left;
/*  568 */             x.left = w.right;
/*  569 */             w.right = x;
/*  570 */             y.right = w.left;
/*  571 */             w.left = y;
/*  572 */             if (w.balance() == 1) {
/*  573 */               x.balance(0);
/*  574 */               y.balance(-1);
/*  575 */             } else if (w.balance() == 0) {
/*  576 */               x.balance(0);
/*  577 */               y.balance(0);
/*      */             } else {
/*  579 */               assert w.balance() == -1;
/*  580 */               x.balance(1);
/*  581 */               y.balance(0);
/*      */             } 
/*  583 */             w.balance(0);
/*  584 */             if (w.pred()) {
/*  585 */               y.succ(w);
/*  586 */               w.pred(false);
/*      */             } 
/*  588 */             if (w.succ()) {
/*  589 */               x.pred(w);
/*  590 */               w.succ(false);
/*      */             } 
/*  592 */             if (q != null) {
/*  593 */               if (dir) {
/*  594 */                 q.right = w; continue;
/*      */               } 
/*  596 */               q.left = w; continue;
/*      */             } 
/*  598 */             this.tree = w; continue;
/*      */           } 
/*  600 */           if (q != null)
/*  601 */           { if (dir) {
/*  602 */               q.right = x;
/*      */             } else {
/*  604 */               q.left = x;
/*      */             }  }
/*  606 */           else { this.tree = x; }
/*  607 */            if (x.balance() == 0) {
/*  608 */             y.right = x.left;
/*  609 */             x.left = y;
/*  610 */             x.balance(-1);
/*  611 */             y.balance(1);
/*      */             break;
/*      */           } 
/*  614 */           assert x.balance() == 1;
/*  615 */           if (x.pred()) {
/*  616 */             y.succ(true);
/*  617 */             x.pred(false);
/*      */           } else {
/*  619 */             y.right = x.left;
/*  620 */           }  x.left = y;
/*  621 */           y.balance(0);
/*  622 */           x.balance(0);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  626 */       dir = (q != null && q.left != y);
/*  627 */       y.decBalance();
/*  628 */       if (y.balance() == -1)
/*      */         break; 
/*  630 */       if (y.balance() == -2) {
/*  631 */         Entry<V> x = y.left;
/*  632 */         assert x != null;
/*  633 */         if (x.balance() == 1) {
/*      */           
/*  635 */           assert x.balance() == 1;
/*  636 */           Entry<V> w = x.right;
/*  637 */           x.right = w.left;
/*  638 */           w.left = x;
/*  639 */           y.left = w.right;
/*  640 */           w.right = y;
/*  641 */           if (w.balance() == -1) {
/*  642 */             x.balance(0);
/*  643 */             y.balance(1);
/*  644 */           } else if (w.balance() == 0) {
/*  645 */             x.balance(0);
/*  646 */             y.balance(0);
/*      */           } else {
/*  648 */             assert w.balance() == 1;
/*  649 */             x.balance(-1);
/*  650 */             y.balance(0);
/*      */           } 
/*  652 */           w.balance(0);
/*  653 */           if (w.pred()) {
/*  654 */             x.succ(w);
/*  655 */             w.pred(false);
/*      */           } 
/*  657 */           if (w.succ()) {
/*  658 */             y.pred(w);
/*  659 */             w.succ(false);
/*      */           } 
/*  661 */           if (q != null) {
/*  662 */             if (dir) {
/*  663 */               q.right = w; continue;
/*      */             } 
/*  665 */             q.left = w; continue;
/*      */           } 
/*  667 */           this.tree = w; continue;
/*      */         } 
/*  669 */         if (q != null)
/*  670 */         { if (dir) {
/*  671 */             q.right = x;
/*      */           } else {
/*  673 */             q.left = x;
/*      */           }  }
/*  675 */         else { this.tree = x; }
/*  676 */          if (x.balance() == 0) {
/*  677 */           y.left = x.right;
/*  678 */           x.right = y;
/*  679 */           x.balance(1);
/*  680 */           y.balance(-1);
/*      */           break;
/*      */         } 
/*  683 */         assert x.balance() == -1;
/*  684 */         if (x.succ()) {
/*  685 */           y.pred(true);
/*  686 */           x.succ(false);
/*      */         } else {
/*  688 */           y.left = x.right;
/*  689 */         }  x.right = y;
/*  690 */         y.balance(0);
/*  691 */         x.balance(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  696 */     this.modified = true;
/*  697 */     this.count--;
/*  698 */     return p.value;
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  702 */     ValueIterator i = new ValueIterator();
/*      */     
/*  704 */     int j = this.count;
/*  705 */     while (j-- != 0) {
/*  706 */       V ev = i.next();
/*  707 */       if (ev == v)
/*  708 */         return true; 
/*      */     } 
/*  710 */     return false;
/*      */   }
/*      */   
/*      */   public void clear() {
/*  714 */     this.count = 0;
/*  715 */     this.tree = null;
/*  716 */     this.entries = null;
/*  717 */     this.values = null;
/*  718 */     this.keys = null;
/*  719 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<V>
/*      */     extends AbstractDouble2ReferenceMap.BasicEntry<V>
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
/*      */     Entry<V> left;
/*      */ 
/*      */     
/*      */     Entry<V> right;
/*      */ 
/*      */     
/*      */     int info;
/*      */ 
/*      */ 
/*      */     
/*      */     Entry() {
/*  750 */       super(0.0D, (V)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(double k, V v) {
/*  761 */       super(k, v);
/*  762 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> left() {
/*  770 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> right() {
/*  778 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  786 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  794 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  803 */       if (pred) {
/*  804 */         this.info |= 0x40000000;
/*      */       } else {
/*  806 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  815 */       if (succ) {
/*  816 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  818 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<V> pred) {
/*  827 */       this.info |= 0x40000000;
/*  828 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<V> succ) {
/*  837 */       this.info |= Integer.MIN_VALUE;
/*  838 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<V> left) {
/*  847 */       this.info &= 0xBFFFFFFF;
/*  848 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<V> right) {
/*  857 */       this.info &= Integer.MAX_VALUE;
/*  858 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int balance() {
/*  866 */       return (byte)this.info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void balance(int level) {
/*  875 */       this.info &= 0xFFFFFF00;
/*  876 */       this.info |= level & 0xFF;
/*      */     }
/*      */     
/*      */     void incBalance() {
/*  880 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
/*      */     }
/*      */     
/*      */     protected void decBalance() {
/*  884 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> next() {
/*  892 */       Entry<V> next = this.right;
/*  893 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  894 */         while ((next.info & 0x40000000) == 0)
/*  895 */           next = next.left;  
/*  896 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> prev() {
/*  904 */       Entry<V> prev = this.left;
/*  905 */       if ((this.info & 0x40000000) == 0)
/*  906 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  907 */           prev = prev.right;  
/*  908 */       return prev;
/*      */     }
/*      */     
/*      */     public V setValue(V value) {
/*  912 */       V oldValue = this.value;
/*  913 */       this.value = value;
/*  914 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry<V> clone() {
/*      */       Entry<V> c;
/*      */       try {
/*  921 */         c = (Entry<V>)super.clone();
/*  922 */       } catch (CloneNotSupportedException cantHappen) {
/*  923 */         throw new InternalError();
/*      */       } 
/*  925 */       c.key = this.key;
/*  926 */       c.value = this.value;
/*  927 */       c.info = this.info;
/*  928 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  933 */       if (!(o instanceof Map.Entry))
/*  934 */         return false; 
/*  935 */       Map.Entry<Double, V> e = (Map.Entry<Double, V>)o;
/*  936 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && this.value == e
/*  937 */         .getValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  941 */       return HashCommon.double2int(this.key) ^ (
/*  942 */         (this.value == null) ? 0 : System.identityHashCode(this.value));
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
/*      */   public V get(double k) {
/*  980 */     Entry<V> e = findKey(k);
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
/*      */     Double2ReferenceAVLTreeMap.Entry<V> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2ReferenceAVLTreeMap.Entry<V> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2ReferenceAVLTreeMap.Entry<V> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1024 */     int index = 0;
/*      */     TreeIterator() {
/* 1026 */       this.next = Double2ReferenceAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(double k) {
/* 1029 */       if ((this.next = Double2ReferenceAVLTreeMap.this.locateKey(k)) != null)
/* 1030 */         if (Double2ReferenceAVLTreeMap.this.compare(this.next.key, k) <= 0) {
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
/*      */     Double2ReferenceAVLTreeMap.Entry<V> nextEntry() {
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
/*      */     Double2ReferenceAVLTreeMap.Entry<V> previousEntry() {
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
/* 1083 */       Double2ReferenceAVLTreeMap.this.remove(this.curr.key);
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
/*      */     implements ObjectListIterator<Double2ReferenceMap.Entry<V>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(double k) {
/* 1109 */       super(k);
/*      */     }
/*      */     
/*      */     public Double2ReferenceMap.Entry<V> next() {
/* 1113 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Double2ReferenceMap.Entry<V> previous() {
/* 1117 */       return previousEntry();
/*      */     }
/*      */     
/*      */     public void set(Double2ReferenceMap.Entry<V> ok) {
/* 1121 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Double2ReferenceMap.Entry<V> ok) {
/* 1125 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
/* 1130 */     if (this.entries == null)
/* 1131 */       this.entries = (ObjectSortedSet<Double2ReferenceMap.Entry<V>>)new AbstractObjectSortedSet<Double2ReferenceMap.Entry<V>>()
/*      */         {
/*      */           final Comparator<? super Double2ReferenceMap.Entry<V>> comparator;
/*      */ 
/*      */           
/*      */           public Comparator<? super Double2ReferenceMap.Entry<V>> comparator() {
/* 1137 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> iterator() {
/* 1141 */             return (ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>>)new Double2ReferenceAVLTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> iterator(Double2ReferenceMap.Entry<V> from) {
/* 1146 */             return (ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>>)new Double2ReferenceAVLTreeMap.EntryIterator(from.getDoubleKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1151 */             if (!(o instanceof Map.Entry))
/* 1152 */               return false; 
/* 1153 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1154 */             if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1155 */               return false; 
/* 1156 */             Double2ReferenceAVLTreeMap.Entry<V> f = Double2ReferenceAVLTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1157 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1162 */             if (!(o instanceof Map.Entry))
/* 1163 */               return false; 
/* 1164 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1165 */             if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1166 */               return false; 
/* 1167 */             Double2ReferenceAVLTreeMap.Entry<V> f = Double2ReferenceAVLTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1168 */             if (f == null || f.getValue() != e.getValue())
/* 1169 */               return false; 
/* 1170 */             Double2ReferenceAVLTreeMap.this.remove(f.key);
/* 1171 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1175 */             return Double2ReferenceAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1179 */             Double2ReferenceAVLTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Double2ReferenceMap.Entry<V> first() {
/* 1183 */             return Double2ReferenceAVLTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Double2ReferenceMap.Entry<V> last() {
/* 1187 */             return Double2ReferenceAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Double2ReferenceMap.Entry<V>> subSet(Double2ReferenceMap.Entry<V> from, Double2ReferenceMap.Entry<V> to) {
/* 1192 */             return Double2ReferenceAVLTreeMap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2ReferenceEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Double2ReferenceMap.Entry<V>> headSet(Double2ReferenceMap.Entry<V> to) {
/* 1196 */             return Double2ReferenceAVLTreeMap.this.headMap(to.getDoubleKey()).double2ReferenceEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Double2ReferenceMap.Entry<V>> tailSet(Double2ReferenceMap.Entry<V> from) {
/* 1200 */             return Double2ReferenceAVLTreeMap.this.tailMap(from.getDoubleKey()).double2ReferenceEntrySet();
/*      */           }
/*      */         }; 
/* 1203 */     return this.entries;
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
/* 1219 */       super(k);
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/* 1223 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1227 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractDouble2ReferenceSortedMap<V>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public DoubleBidirectionalIterator iterator() {
/* 1234 */       return new Double2ReferenceAVLTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public DoubleBidirectionalIterator iterator(double from) {
/* 1238 */       return new Double2ReferenceAVLTreeMap.KeyIterator(from);
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
/* 1253 */     if (this.keys == null)
/* 1254 */       this.keys = new KeySet(); 
/* 1255 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<V>
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public V next() {
/* 1270 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public V previous() {
/* 1274 */       return (previousEntry()).value;
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
/*      */   public ReferenceCollection<V> values() {
/* 1289 */     if (this.values == null)
/* 1290 */       this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1293 */             return (ObjectIterator<V>)new Double2ReferenceAVLTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(Object k) {
/* 1297 */             return Double2ReferenceAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1301 */             return Double2ReferenceAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1305 */             Double2ReferenceAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1308 */     return this.values;
/*      */   }
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1312 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Double2ReferenceSortedMap<V> headMap(double to) {
/* 1316 */     return new Submap(0.0D, true, to, false);
/*      */   }
/*      */   
/*      */   public Double2ReferenceSortedMap<V> tailMap(double from) {
/* 1320 */     return new Submap(from, false, 0.0D, true);
/*      */   }
/*      */   
/*      */   public Double2ReferenceSortedMap<V> subMap(double from, double to) {
/* 1324 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractDouble2ReferenceSortedMap<V>
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
/*      */     protected transient ObjectSortedSet<Double2ReferenceMap.Entry<V>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient DoubleSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ReferenceCollection<V> values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(double from, boolean bottom, double to, boolean top) {
/* 1368 */       if (!bottom && !top && Double2ReferenceAVLTreeMap.this.compare(from, to) > 0)
/* 1369 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1370 */       this.from = from;
/* 1371 */       this.bottom = bottom;
/* 1372 */       this.to = to;
/* 1373 */       this.top = top;
/* 1374 */       this.defRetValue = Double2ReferenceAVLTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1378 */       SubmapIterator i = new SubmapIterator();
/* 1379 */       while (i.hasNext()) {
/* 1380 */         i.nextEntry();
/* 1381 */         i.remove();
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
/* 1392 */       return ((this.bottom || Double2ReferenceAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Double2ReferenceAVLTreeMap.this
/* 1393 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
/* 1397 */       if (this.entries == null)
/* 1398 */         this.entries = (ObjectSortedSet<Double2ReferenceMap.Entry<V>>)new AbstractObjectSortedSet<Double2ReferenceMap.Entry<V>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> iterator() {
/* 1401 */               return (ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>>)new Double2ReferenceAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> iterator(Double2ReferenceMap.Entry<V> from) {
/* 1406 */               return (ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>>)new Double2ReferenceAVLTreeMap.Submap.SubmapEntryIterator(from.getDoubleKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Double2ReferenceMap.Entry<V>> comparator() {
/* 1410 */               return Double2ReferenceAVLTreeMap.this.double2ReferenceEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1415 */               if (!(o instanceof Map.Entry))
/* 1416 */                 return false; 
/* 1417 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1418 */               if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1419 */                 return false; 
/* 1420 */               Double2ReferenceAVLTreeMap.Entry<V> f = Double2ReferenceAVLTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1421 */               return (f != null && Double2ReferenceAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1426 */               if (!(o instanceof Map.Entry))
/* 1427 */                 return false; 
/* 1428 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1429 */               if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1430 */                 return false; 
/* 1431 */               Double2ReferenceAVLTreeMap.Entry<V> f = Double2ReferenceAVLTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1432 */               if (f != null && Double2ReferenceAVLTreeMap.Submap.this.in(f.key))
/* 1433 */                 Double2ReferenceAVLTreeMap.Submap.this.remove(f.key); 
/* 1434 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1438 */               int c = 0;
/* 1439 */               for (ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1440 */                 c++; 
/* 1441 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1445 */               return !(new Double2ReferenceAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1449 */               Double2ReferenceAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Double2ReferenceMap.Entry<V> first() {
/* 1453 */               return Double2ReferenceAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Double2ReferenceMap.Entry<V> last() {
/* 1457 */               return Double2ReferenceAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Double2ReferenceMap.Entry<V>> subSet(Double2ReferenceMap.Entry<V> from, Double2ReferenceMap.Entry<V> to) {
/* 1462 */               return Double2ReferenceAVLTreeMap.Submap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2ReferenceEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Double2ReferenceMap.Entry<V>> headSet(Double2ReferenceMap.Entry<V> to) {
/* 1466 */               return Double2ReferenceAVLTreeMap.Submap.this.headMap(to.getDoubleKey()).double2ReferenceEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Double2ReferenceMap.Entry<V>> tailSet(Double2ReferenceMap.Entry<V> from) {
/* 1470 */               return Double2ReferenceAVLTreeMap.Submap.this.tailMap(from.getDoubleKey()).double2ReferenceEntrySet();
/*      */             }
/*      */           }; 
/* 1473 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractDouble2ReferenceSortedMap<V>.KeySet {
/*      */       public DoubleBidirectionalIterator iterator() {
/* 1478 */         return new Double2ReferenceAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public DoubleBidirectionalIterator iterator(double from) {
/* 1482 */         return new Double2ReferenceAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public DoubleSortedSet keySet() {
/* 1487 */       if (this.keys == null)
/* 1488 */         this.keys = new KeySet(); 
/* 1489 */       return this.keys;
/*      */     }
/*      */     
/*      */     public ReferenceCollection<V> values() {
/* 1493 */       if (this.values == null)
/* 1494 */         this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */           {
/*      */             public ObjectIterator<V> iterator() {
/* 1497 */               return (ObjectIterator<V>)new Double2ReferenceAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(Object k) {
/* 1501 */               return Double2ReferenceAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1505 */               return Double2ReferenceAVLTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1509 */               Double2ReferenceAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1512 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(double k) {
/* 1517 */       return (in(k) && Double2ReferenceAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(Object v) {
/* 1521 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1523 */       while (i.hasNext()) {
/* 1524 */         Object ev = (i.nextEntry()).value;
/* 1525 */         if (ev == v)
/* 1526 */           return true; 
/*      */       } 
/* 1528 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(double k) {
/* 1534 */       double kk = k; Double2ReferenceAVLTreeMap.Entry<V> e;
/* 1535 */       return (in(kk) && (e = Double2ReferenceAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public V put(double k, V v) {
/* 1539 */       Double2ReferenceAVLTreeMap.this.modified = false;
/* 1540 */       if (!in(k))
/* 1541 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1542 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1543 */       V oldValue = Double2ReferenceAVLTreeMap.this.put(k, v);
/* 1544 */       return Double2ReferenceAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public V remove(double k) {
/* 1549 */       Double2ReferenceAVLTreeMap.this.modified = false;
/* 1550 */       if (!in(k))
/* 1551 */         return this.defRetValue; 
/* 1552 */       V oldValue = Double2ReferenceAVLTreeMap.this.remove(k);
/* 1553 */       return Double2ReferenceAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1557 */       SubmapIterator i = new SubmapIterator();
/* 1558 */       int n = 0;
/* 1559 */       while (i.hasNext()) {
/* 1560 */         n++;
/* 1561 */         i.nextEntry();
/*      */       } 
/* 1563 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1567 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1571 */       return Double2ReferenceAVLTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Double2ReferenceSortedMap<V> headMap(double to) {
/* 1575 */       if (this.top)
/* 1576 */         return new Submap(this.from, this.bottom, to, false); 
/* 1577 */       return (Double2ReferenceAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Double2ReferenceSortedMap<V> tailMap(double from) {
/* 1581 */       if (this.bottom)
/* 1582 */         return new Submap(from, false, this.to, this.top); 
/* 1583 */       return (Double2ReferenceAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Double2ReferenceSortedMap<V> subMap(double from, double to) {
/* 1587 */       if (this.top && this.bottom)
/* 1588 */         return new Submap(from, false, to, false); 
/* 1589 */       if (!this.top)
/* 1590 */         to = (Double2ReferenceAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1591 */       if (!this.bottom)
/* 1592 */         from = (Double2ReferenceAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1593 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1594 */         return this; 
/* 1595 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2ReferenceAVLTreeMap.Entry<V> firstEntry() {
/*      */       Double2ReferenceAVLTreeMap.Entry<V> e;
/* 1604 */       if (Double2ReferenceAVLTreeMap.this.tree == null) {
/* 1605 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1609 */       if (this.bottom) {
/* 1610 */         e = Double2ReferenceAVLTreeMap.this.firstEntry;
/*      */       } else {
/* 1612 */         e = Double2ReferenceAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1614 */         if (Double2ReferenceAVLTreeMap.this.compare(e.key, this.from) < 0) {
/* 1615 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1619 */       if (e == null || (!this.top && Double2ReferenceAVLTreeMap.this.compare(e.key, this.to) >= 0))
/* 1620 */         return null; 
/* 1621 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2ReferenceAVLTreeMap.Entry<V> lastEntry() {
/*      */       Double2ReferenceAVLTreeMap.Entry<V> e;
/* 1630 */       if (Double2ReferenceAVLTreeMap.this.tree == null) {
/* 1631 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1635 */       if (this.top) {
/* 1636 */         e = Double2ReferenceAVLTreeMap.this.lastEntry;
/*      */       } else {
/* 1638 */         e = Double2ReferenceAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1640 */         if (Double2ReferenceAVLTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1641 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1645 */       if (e == null || (!this.bottom && Double2ReferenceAVLTreeMap.this.compare(e.key, this.from) < 0))
/* 1646 */         return null; 
/* 1647 */       return e;
/*      */     }
/*      */     
/*      */     public double firstDoubleKey() {
/* 1651 */       Double2ReferenceAVLTreeMap.Entry<V> e = firstEntry();
/* 1652 */       if (e == null)
/* 1653 */         throw new NoSuchElementException(); 
/* 1654 */       return e.key;
/*      */     }
/*      */     
/*      */     public double lastDoubleKey() {
/* 1658 */       Double2ReferenceAVLTreeMap.Entry<V> e = lastEntry();
/* 1659 */       if (e == null)
/* 1660 */         throw new NoSuchElementException(); 
/* 1661 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Double2ReferenceAVLTreeMap<V>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1674 */         this.next = Double2ReferenceAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(double k) {
/* 1677 */         this();
/* 1678 */         if (this.next != null)
/* 1679 */           if (!Double2ReferenceAVLTreeMap.Submap.this.bottom && Double2ReferenceAVLTreeMap.this.compare(k, this.next.key) < 0) {
/* 1680 */             this.prev = null;
/* 1681 */           } else if (!Double2ReferenceAVLTreeMap.Submap.this.top && Double2ReferenceAVLTreeMap.this.compare(k, (this.prev = Double2ReferenceAVLTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1682 */             this.next = null;
/*      */           } else {
/* 1684 */             this.next = Double2ReferenceAVLTreeMap.this.locateKey(k);
/* 1685 */             if (Double2ReferenceAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1686 */               this.prev = this.next;
/* 1687 */               this.next = this.next.next();
/*      */             } else {
/* 1689 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1695 */         this.prev = this.prev.prev();
/* 1696 */         if (!Double2ReferenceAVLTreeMap.Submap.this.bottom && this.prev != null && Double2ReferenceAVLTreeMap.this.compare(this.prev.key, Double2ReferenceAVLTreeMap.Submap.this.from) < 0)
/* 1697 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1701 */         this.next = this.next.next();
/* 1702 */         if (!Double2ReferenceAVLTreeMap.Submap.this.top && this.next != null && Double2ReferenceAVLTreeMap.this.compare(this.next.key, Double2ReferenceAVLTreeMap.Submap.this.to) >= 0)
/* 1703 */           this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Double2ReferenceMap.Entry<V>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(double k) {
/* 1712 */         super(k);
/*      */       }
/*      */       
/*      */       public Double2ReferenceMap.Entry<V> next() {
/* 1716 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Double2ReferenceMap.Entry<V> previous() {
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
/*      */       implements DoubleListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(double from) {
/* 1738 */         super(from);
/*      */       }
/*      */       
/*      */       public double nextDouble() {
/* 1742 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public double previousDouble() {
/* 1746 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements ObjectListIterator<V>
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public V next() {
/* 1762 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public V previous() {
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
/*      */   public Double2ReferenceAVLTreeMap<V> clone() {
/*      */     Double2ReferenceAVLTreeMap<V> c;
/*      */     try {
/* 1785 */       c = (Double2ReferenceAVLTreeMap<V>)super.clone();
/* 1786 */     } catch (CloneNotSupportedException cantHappen) {
/* 1787 */       throw new InternalError();
/*      */     } 
/* 1789 */     c.keys = null;
/* 1790 */     c.values = null;
/* 1791 */     c.entries = null;
/* 1792 */     c.allocatePaths();
/* 1793 */     if (this.count != 0) {
/*      */       
/* 1795 */       Entry<V> rp = new Entry<>(), rq = new Entry<>();
/* 1796 */       Entry<V> p = rp;
/* 1797 */       rp.left(this.tree);
/* 1798 */       Entry<V> q = rq;
/* 1799 */       rq.pred((Entry<V>)null);
/*      */       while (true) {
/* 1801 */         if (!p.pred()) {
/* 1802 */           Entry<V> e = p.left.clone();
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
/* 1828 */           Entry<V> e = p.right.clone();
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
/* 1843 */       Entry<V> e = i.nextEntry();
/* 1844 */       s.writeDouble(e.key);
/* 1845 */       s.writeObject(e.value);
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
/*      */   private Entry<V> readTree(ObjectInputStream s, int n, Entry<V> pred, Entry<V> succ) throws IOException, ClassNotFoundException {
/* 1866 */     if (n == 1) {
/* 1867 */       Entry<V> entry = new Entry<>(s.readDouble(), (V)s.readObject());
/* 1868 */       entry.pred(pred);
/* 1869 */       entry.succ(succ);
/* 1870 */       return entry;
/*      */     } 
/* 1872 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1877 */       Entry<V> entry = new Entry<>(s.readDouble(), (V)s.readObject());
/* 1878 */       entry.right(new Entry<>(s.readDouble(), (V)s.readObject()));
/* 1879 */       entry.right.pred(entry);
/* 1880 */       entry.balance(1);
/* 1881 */       entry.pred(pred);
/* 1882 */       entry.right.succ(succ);
/* 1883 */       return entry;
/*      */     } 
/*      */     
/* 1886 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1887 */     Entry<V> top = new Entry<>();
/* 1888 */     top.left(readTree(s, leftN, pred, top));
/* 1889 */     top.key = s.readDouble();
/* 1890 */     top.value = (V)s.readObject();
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
/* 1905 */       this.tree = readTree(s, this.count, (Entry<V>)null, (Entry<V>)null);
/*      */       
/* 1907 */       Entry<V> e = this.tree;
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2ReferenceAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */