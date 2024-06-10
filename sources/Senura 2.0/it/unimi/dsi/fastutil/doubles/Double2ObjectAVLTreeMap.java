/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
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
/*      */ public class Double2ObjectAVLTreeMap<V>
/*      */   extends AbstractDouble2ObjectSortedMap<V>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<V> tree;
/*      */   protected int count;
/*      */   protected transient Entry<V> firstEntry;
/*      */   protected transient Entry<V> lastEntry;
/*      */   protected transient ObjectSortedSet<Double2ObjectMap.Entry<V>> entries;
/*      */   protected transient DoubleSortedSet keys;
/*      */   protected transient ObjectCollection<V> values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Double> storedComparator;
/*      */   protected transient DoubleComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Double2ObjectAVLTreeMap() {
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
/*      */   public Double2ObjectAVLTreeMap(Comparator<? super Double> c) {
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
/*      */   public Double2ObjectAVLTreeMap(Map<? extends Double, ? extends V> m) {
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
/*      */   public Double2ObjectAVLTreeMap(SortedMap<Double, V> m) {
/*  122 */     this(m.comparator());
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ObjectAVLTreeMap(Double2ObjectMap<? extends V> m) {
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
/*      */   public Double2ObjectAVLTreeMap(Double2ObjectSortedMap<V> m) {
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
/*      */   public Double2ObjectAVLTreeMap(double[] k, V[] v, Comparator<? super Double> c) {
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
/*      */   public Double2ObjectAVLTreeMap(double[] k, V[] v) {
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
/*  707 */       if (Objects.equals(ev, v))
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
/*      */     extends AbstractDouble2ObjectMap.BasicEntry<V>
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
/*  936 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && 
/*  937 */         Objects.equals(this.value, e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  941 */       return HashCommon.double2int(this.key) ^ ((this.value == null) ? 0 : this.value.hashCode());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  945 */       return this.key + "=>" + this.value;
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
/*  966 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  970 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  974 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(double k) {
/*  979 */     Entry<V> e = findKey(k);
/*  980 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public double firstDoubleKey() {
/*  984 */     if (this.tree == null)
/*  985 */       throw new NoSuchElementException(); 
/*  986 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public double lastDoubleKey() {
/*  990 */     if (this.tree == null)
/*  991 */       throw new NoSuchElementException(); 
/*  992 */     return this.lastEntry.key;
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
/*      */     Double2ObjectAVLTreeMap.Entry<V> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2ObjectAVLTreeMap.Entry<V> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2ObjectAVLTreeMap.Entry<V> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1023 */     int index = 0;
/*      */     TreeIterator() {
/* 1025 */       this.next = Double2ObjectAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(double k) {
/* 1028 */       if ((this.next = Double2ObjectAVLTreeMap.this.locateKey(k)) != null)
/* 1029 */         if (Double2ObjectAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1030 */           this.prev = this.next;
/* 1031 */           this.next = this.next.next();
/*      */         } else {
/* 1033 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/* 1037 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1040 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1043 */       this.next = this.next.next();
/*      */     }
/*      */     Double2ObjectAVLTreeMap.Entry<V> nextEntry() {
/* 1046 */       if (!hasNext())
/* 1047 */         throw new NoSuchElementException(); 
/* 1048 */       this.curr = this.prev = this.next;
/* 1049 */       this.index++;
/* 1050 */       updateNext();
/* 1051 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1054 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Double2ObjectAVLTreeMap.Entry<V> previousEntry() {
/* 1057 */       if (!hasPrevious())
/* 1058 */         throw new NoSuchElementException(); 
/* 1059 */       this.curr = this.next = this.prev;
/* 1060 */       this.index--;
/* 1061 */       updatePrevious();
/* 1062 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1065 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1068 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1071 */       if (this.curr == null) {
/* 1072 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1077 */       if (this.curr == this.prev)
/* 1078 */         this.index--; 
/* 1079 */       this.next = this.prev = this.curr;
/* 1080 */       updatePrevious();
/* 1081 */       updateNext();
/* 1082 */       Double2ObjectAVLTreeMap.this.remove(this.curr.key);
/* 1083 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1086 */       int i = n;
/* 1087 */       while (i-- != 0 && hasNext())
/* 1088 */         nextEntry(); 
/* 1089 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1092 */       int i = n;
/* 1093 */       while (i-- != 0 && hasPrevious())
/* 1094 */         previousEntry(); 
/* 1095 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Double2ObjectMap.Entry<V>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(double k) {
/* 1108 */       super(k);
/*      */     }
/*      */     
/*      */     public Double2ObjectMap.Entry<V> next() {
/* 1112 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Double2ObjectMap.Entry<V> previous() {
/* 1116 */       return previousEntry();
/*      */     }
/*      */     
/*      */     public void set(Double2ObjectMap.Entry<V> ok) {
/* 1120 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Double2ObjectMap.Entry<V> ok) {
/* 1124 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
/* 1129 */     if (this.entries == null)
/* 1130 */       this.entries = (ObjectSortedSet<Double2ObjectMap.Entry<V>>)new AbstractObjectSortedSet<Double2ObjectMap.Entry<V>>()
/*      */         {
/*      */           final Comparator<? super Double2ObjectMap.Entry<V>> comparator;
/*      */ 
/*      */           
/*      */           public Comparator<? super Double2ObjectMap.Entry<V>> comparator() {
/* 1136 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>> iterator() {
/* 1140 */             return (ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>>)new Double2ObjectAVLTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>> iterator(Double2ObjectMap.Entry<V> from) {
/* 1145 */             return (ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>>)new Double2ObjectAVLTreeMap.EntryIterator(from.getDoubleKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1150 */             if (!(o instanceof Map.Entry))
/* 1151 */               return false; 
/* 1152 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1153 */             if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1154 */               return false; 
/* 1155 */             Double2ObjectAVLTreeMap.Entry<V> f = Double2ObjectAVLTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1156 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1161 */             if (!(o instanceof Map.Entry))
/* 1162 */               return false; 
/* 1163 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1164 */             if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1165 */               return false; 
/* 1166 */             Double2ObjectAVLTreeMap.Entry<V> f = Double2ObjectAVLTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1167 */             if (f == null || !Objects.equals(f.getValue(), e.getValue()))
/* 1168 */               return false; 
/* 1169 */             Double2ObjectAVLTreeMap.this.remove(f.key);
/* 1170 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1174 */             return Double2ObjectAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1178 */             Double2ObjectAVLTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Double2ObjectMap.Entry<V> first() {
/* 1182 */             return Double2ObjectAVLTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Double2ObjectMap.Entry<V> last() {
/* 1186 */             return Double2ObjectAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Double2ObjectMap.Entry<V>> subSet(Double2ObjectMap.Entry<V> from, Double2ObjectMap.Entry<V> to) {
/* 1191 */             return Double2ObjectAVLTreeMap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2ObjectEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Double2ObjectMap.Entry<V>> headSet(Double2ObjectMap.Entry<V> to) {
/* 1195 */             return Double2ObjectAVLTreeMap.this.headMap(to.getDoubleKey()).double2ObjectEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Double2ObjectMap.Entry<V>> tailSet(Double2ObjectMap.Entry<V> from) {
/* 1199 */             return Double2ObjectAVLTreeMap.this.tailMap(from.getDoubleKey()).double2ObjectEntrySet();
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
/*      */     implements DoubleListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(double k) {
/* 1218 */       super(k);
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/* 1222 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1226 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractDouble2ObjectSortedMap<V>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public DoubleBidirectionalIterator iterator() {
/* 1233 */       return new Double2ObjectAVLTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public DoubleBidirectionalIterator iterator(double from) {
/* 1237 */       return new Double2ObjectAVLTreeMap.KeyIterator(from);
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
/*      */     implements ObjectListIterator<V>
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public V next() {
/* 1269 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public V previous() {
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
/*      */   public ObjectCollection<V> values() {
/* 1288 */     if (this.values == null)
/* 1289 */       this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1292 */             return (ObjectIterator<V>)new Double2ObjectAVLTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(Object k) {
/* 1296 */             return Double2ObjectAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1300 */             return Double2ObjectAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1304 */             Double2ObjectAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1307 */     return this.values;
/*      */   }
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1311 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Double2ObjectSortedMap<V> headMap(double to) {
/* 1315 */     return new Submap(0.0D, true, to, false);
/*      */   }
/*      */   
/*      */   public Double2ObjectSortedMap<V> tailMap(double from) {
/* 1319 */     return new Submap(from, false, 0.0D, true);
/*      */   }
/*      */   
/*      */   public Double2ObjectSortedMap<V> subMap(double from, double to) {
/* 1323 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractDouble2ObjectSortedMap<V>
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
/*      */     protected transient ObjectSortedSet<Double2ObjectMap.Entry<V>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient DoubleSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ObjectCollection<V> values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(double from, boolean bottom, double to, boolean top) {
/* 1367 */       if (!bottom && !top && Double2ObjectAVLTreeMap.this.compare(from, to) > 0)
/* 1368 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1369 */       this.from = from;
/* 1370 */       this.bottom = bottom;
/* 1371 */       this.to = to;
/* 1372 */       this.top = top;
/* 1373 */       this.defRetValue = Double2ObjectAVLTreeMap.this.defRetValue;
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
/*      */     final boolean in(double k) {
/* 1391 */       return ((this.bottom || Double2ObjectAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Double2ObjectAVLTreeMap.this
/* 1392 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
/* 1396 */       if (this.entries == null)
/* 1397 */         this.entries = (ObjectSortedSet<Double2ObjectMap.Entry<V>>)new AbstractObjectSortedSet<Double2ObjectMap.Entry<V>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>> iterator() {
/* 1400 */               return (ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>>)new Double2ObjectAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>> iterator(Double2ObjectMap.Entry<V> from) {
/* 1405 */               return (ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>>)new Double2ObjectAVLTreeMap.Submap.SubmapEntryIterator(from.getDoubleKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Double2ObjectMap.Entry<V>> comparator() {
/* 1409 */               return Double2ObjectAVLTreeMap.this.double2ObjectEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1414 */               if (!(o instanceof Map.Entry))
/* 1415 */                 return false; 
/* 1416 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1417 */               if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1418 */                 return false; 
/* 1419 */               Double2ObjectAVLTreeMap.Entry<V> f = Double2ObjectAVLTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1420 */               return (f != null && Double2ObjectAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1425 */               if (!(o instanceof Map.Entry))
/* 1426 */                 return false; 
/* 1427 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1428 */               if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1429 */                 return false; 
/* 1430 */               Double2ObjectAVLTreeMap.Entry<V> f = Double2ObjectAVLTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1431 */               if (f != null && Double2ObjectAVLTreeMap.Submap.this.in(f.key))
/* 1432 */                 Double2ObjectAVLTreeMap.Submap.this.remove(f.key); 
/* 1433 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1437 */               int c = 0;
/* 1438 */               for (ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1439 */                 c++; 
/* 1440 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1444 */               return !(new Double2ObjectAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1448 */               Double2ObjectAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Double2ObjectMap.Entry<V> first() {
/* 1452 */               return Double2ObjectAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Double2ObjectMap.Entry<V> last() {
/* 1456 */               return Double2ObjectAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Double2ObjectMap.Entry<V>> subSet(Double2ObjectMap.Entry<V> from, Double2ObjectMap.Entry<V> to) {
/* 1461 */               return Double2ObjectAVLTreeMap.Submap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2ObjectEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Double2ObjectMap.Entry<V>> headSet(Double2ObjectMap.Entry<V> to) {
/* 1465 */               return Double2ObjectAVLTreeMap.Submap.this.headMap(to.getDoubleKey()).double2ObjectEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Double2ObjectMap.Entry<V>> tailSet(Double2ObjectMap.Entry<V> from) {
/* 1469 */               return Double2ObjectAVLTreeMap.Submap.this.tailMap(from.getDoubleKey()).double2ObjectEntrySet();
/*      */             }
/*      */           }; 
/* 1472 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractDouble2ObjectSortedMap<V>.KeySet {
/*      */       public DoubleBidirectionalIterator iterator() {
/* 1477 */         return new Double2ObjectAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public DoubleBidirectionalIterator iterator(double from) {
/* 1481 */         return new Double2ObjectAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public DoubleSortedSet keySet() {
/* 1486 */       if (this.keys == null)
/* 1487 */         this.keys = new KeySet(); 
/* 1488 */       return this.keys;
/*      */     }
/*      */     
/*      */     public ObjectCollection<V> values() {
/* 1492 */       if (this.values == null)
/* 1493 */         this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */           {
/*      */             public ObjectIterator<V> iterator() {
/* 1496 */               return (ObjectIterator<V>)new Double2ObjectAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(Object k) {
/* 1500 */               return Double2ObjectAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1504 */               return Double2ObjectAVLTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1508 */               Double2ObjectAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1511 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(double k) {
/* 1516 */       return (in(k) && Double2ObjectAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(Object v) {
/* 1520 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1522 */       while (i.hasNext()) {
/* 1523 */         Object ev = (i.nextEntry()).value;
/* 1524 */         if (Objects.equals(ev, v))
/* 1525 */           return true; 
/*      */       } 
/* 1527 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(double k) {
/* 1533 */       double kk = k; Double2ObjectAVLTreeMap.Entry<V> e;
/* 1534 */       return (in(kk) && (e = Double2ObjectAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public V put(double k, V v) {
/* 1538 */       Double2ObjectAVLTreeMap.this.modified = false;
/* 1539 */       if (!in(k))
/* 1540 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1541 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1542 */       V oldValue = Double2ObjectAVLTreeMap.this.put(k, v);
/* 1543 */       return Double2ObjectAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public V remove(double k) {
/* 1548 */       Double2ObjectAVLTreeMap.this.modified = false;
/* 1549 */       if (!in(k))
/* 1550 */         return this.defRetValue; 
/* 1551 */       V oldValue = Double2ObjectAVLTreeMap.this.remove(k);
/* 1552 */       return Double2ObjectAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1556 */       SubmapIterator i = new SubmapIterator();
/* 1557 */       int n = 0;
/* 1558 */       while (i.hasNext()) {
/* 1559 */         n++;
/* 1560 */         i.nextEntry();
/*      */       } 
/* 1562 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1566 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1570 */       return Double2ObjectAVLTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Double2ObjectSortedMap<V> headMap(double to) {
/* 1574 */       if (this.top)
/* 1575 */         return new Submap(this.from, this.bottom, to, false); 
/* 1576 */       return (Double2ObjectAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Double2ObjectSortedMap<V> tailMap(double from) {
/* 1580 */       if (this.bottom)
/* 1581 */         return new Submap(from, false, this.to, this.top); 
/* 1582 */       return (Double2ObjectAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Double2ObjectSortedMap<V> subMap(double from, double to) {
/* 1586 */       if (this.top && this.bottom)
/* 1587 */         return new Submap(from, false, to, false); 
/* 1588 */       if (!this.top)
/* 1589 */         to = (Double2ObjectAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1590 */       if (!this.bottom)
/* 1591 */         from = (Double2ObjectAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1592 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1593 */         return this; 
/* 1594 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2ObjectAVLTreeMap.Entry<V> firstEntry() {
/*      */       Double2ObjectAVLTreeMap.Entry<V> e;
/* 1603 */       if (Double2ObjectAVLTreeMap.this.tree == null) {
/* 1604 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1608 */       if (this.bottom) {
/* 1609 */         e = Double2ObjectAVLTreeMap.this.firstEntry;
/*      */       } else {
/* 1611 */         e = Double2ObjectAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1613 */         if (Double2ObjectAVLTreeMap.this.compare(e.key, this.from) < 0) {
/* 1614 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1618 */       if (e == null || (!this.top && Double2ObjectAVLTreeMap.this.compare(e.key, this.to) >= 0))
/* 1619 */         return null; 
/* 1620 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2ObjectAVLTreeMap.Entry<V> lastEntry() {
/*      */       Double2ObjectAVLTreeMap.Entry<V> e;
/* 1629 */       if (Double2ObjectAVLTreeMap.this.tree == null) {
/* 1630 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1634 */       if (this.top) {
/* 1635 */         e = Double2ObjectAVLTreeMap.this.lastEntry;
/*      */       } else {
/* 1637 */         e = Double2ObjectAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1639 */         if (Double2ObjectAVLTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1640 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1644 */       if (e == null || (!this.bottom && Double2ObjectAVLTreeMap.this.compare(e.key, this.from) < 0))
/* 1645 */         return null; 
/* 1646 */       return e;
/*      */     }
/*      */     
/*      */     public double firstDoubleKey() {
/* 1650 */       Double2ObjectAVLTreeMap.Entry<V> e = firstEntry();
/* 1651 */       if (e == null)
/* 1652 */         throw new NoSuchElementException(); 
/* 1653 */       return e.key;
/*      */     }
/*      */     
/*      */     public double lastDoubleKey() {
/* 1657 */       Double2ObjectAVLTreeMap.Entry<V> e = lastEntry();
/* 1658 */       if (e == null)
/* 1659 */         throw new NoSuchElementException(); 
/* 1660 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Double2ObjectAVLTreeMap<V>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1673 */         this.next = Double2ObjectAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(double k) {
/* 1676 */         this();
/* 1677 */         if (this.next != null)
/* 1678 */           if (!Double2ObjectAVLTreeMap.Submap.this.bottom && Double2ObjectAVLTreeMap.this.compare(k, this.next.key) < 0) {
/* 1679 */             this.prev = null;
/* 1680 */           } else if (!Double2ObjectAVLTreeMap.Submap.this.top && Double2ObjectAVLTreeMap.this.compare(k, (this.prev = Double2ObjectAVLTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1681 */             this.next = null;
/*      */           } else {
/* 1683 */             this.next = Double2ObjectAVLTreeMap.this.locateKey(k);
/* 1684 */             if (Double2ObjectAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1685 */               this.prev = this.next;
/* 1686 */               this.next = this.next.next();
/*      */             } else {
/* 1688 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1694 */         this.prev = this.prev.prev();
/* 1695 */         if (!Double2ObjectAVLTreeMap.Submap.this.bottom && this.prev != null && Double2ObjectAVLTreeMap.this.compare(this.prev.key, Double2ObjectAVLTreeMap.Submap.this.from) < 0)
/* 1696 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1700 */         this.next = this.next.next();
/* 1701 */         if (!Double2ObjectAVLTreeMap.Submap.this.top && this.next != null && Double2ObjectAVLTreeMap.this.compare(this.next.key, Double2ObjectAVLTreeMap.Submap.this.to) >= 0)
/* 1702 */           this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Double2ObjectMap.Entry<V>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(double k) {
/* 1711 */         super(k);
/*      */       }
/*      */       
/*      */       public Double2ObjectMap.Entry<V> next() {
/* 1715 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Double2ObjectMap.Entry<V> previous() {
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
/*      */       implements DoubleListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(double from) {
/* 1737 */         super(from);
/*      */       }
/*      */       
/*      */       public double nextDouble() {
/* 1741 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public double previousDouble() {
/* 1745 */         return (previousEntry()).key;
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
/* 1761 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public V previous() {
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
/*      */   public Double2ObjectAVLTreeMap<V> clone() {
/*      */     Double2ObjectAVLTreeMap<V> c;
/*      */     try {
/* 1784 */       c = (Double2ObjectAVLTreeMap<V>)super.clone();
/* 1785 */     } catch (CloneNotSupportedException cantHappen) {
/* 1786 */       throw new InternalError();
/*      */     } 
/* 1788 */     c.keys = null;
/* 1789 */     c.values = null;
/* 1790 */     c.entries = null;
/* 1791 */     c.allocatePaths();
/* 1792 */     if (this.count != 0) {
/*      */       
/* 1794 */       Entry<V> rp = new Entry<>(), rq = new Entry<>();
/* 1795 */       Entry<V> p = rp;
/* 1796 */       rp.left(this.tree);
/* 1797 */       Entry<V> q = rq;
/* 1798 */       rq.pred((Entry<V>)null);
/*      */       while (true) {
/* 1800 */         if (!p.pred()) {
/* 1801 */           Entry<V> e = p.left.clone();
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
/* 1827 */           Entry<V> e = p.right.clone();
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
/* 1842 */       Entry<V> e = i.nextEntry();
/* 1843 */       s.writeDouble(e.key);
/* 1844 */       s.writeObject(e.value);
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
/* 1865 */     if (n == 1) {
/* 1866 */       Entry<V> entry = new Entry<>(s.readDouble(), (V)s.readObject());
/* 1867 */       entry.pred(pred);
/* 1868 */       entry.succ(succ);
/* 1869 */       return entry;
/*      */     } 
/* 1871 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1876 */       Entry<V> entry = new Entry<>(s.readDouble(), (V)s.readObject());
/* 1877 */       entry.right(new Entry<>(s.readDouble(), (V)s.readObject()));
/* 1878 */       entry.right.pred(entry);
/* 1879 */       entry.balance(1);
/* 1880 */       entry.pred(pred);
/* 1881 */       entry.right.succ(succ);
/* 1882 */       return entry;
/*      */     } 
/*      */     
/* 1885 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1886 */     Entry<V> top = new Entry<>();
/* 1887 */     top.left(readTree(s, leftN, pred, top));
/* 1888 */     top.key = s.readDouble();
/* 1889 */     top.value = (V)s.readObject();
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
/* 1904 */       this.tree = readTree(s, this.count, (Entry<V>)null, (Entry<V>)null);
/*      */       
/* 1906 */       Entry<V> e = this.tree;
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2ObjectAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */