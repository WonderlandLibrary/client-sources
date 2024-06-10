/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2ObjectAVLTreeMap<K, V>
/*      */   extends AbstractObject2ObjectSortedMap<K, V>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<K, V> tree;
/*      */   protected int count;
/*      */   protected transient Entry<K, V> firstEntry;
/*      */   protected transient Entry<K, V> lastEntry;
/*      */   protected transient ObjectSortedSet<Object2ObjectMap.Entry<K, V>> entries;
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */   protected transient ObjectCollection<V> values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super K> storedComparator;
/*      */   protected transient Comparator<? super K> actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Object2ObjectAVLTreeMap() {
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
/*   91 */     this.actualComparator = this.storedComparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectAVLTreeMap(Comparator<? super K> c) {
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
/*      */   public Object2ObjectAVLTreeMap(Map<? extends K, ? extends V> m) {
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
/*      */   public Object2ObjectAVLTreeMap(SortedMap<K, V> m) {
/*  122 */     this(m.comparator());
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectAVLTreeMap(Object2ObjectMap<? extends K, ? extends V> m) {
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
/*      */   public Object2ObjectAVLTreeMap(Object2ObjectSortedMap<K, V> m) {
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
/*      */   public Object2ObjectAVLTreeMap(K[] k, V[] v, Comparator<? super K> c) {
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
/*      */   public Object2ObjectAVLTreeMap(K[] k, V[] v) {
/*  178 */     this(k, v, (Comparator<? super K>)null);
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
/*      */   final int compare(K k1, K k2) {
/*  206 */     return (this.actualComparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry<K, V> findKey(K k) {
/*  218 */     Entry<K, V> e = this.tree;
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
/*      */   final Entry<K, V> locateKey(K k) {
/*  234 */     Entry<K, V> e = this.tree, last = this.tree;
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
/*      */   public V put(K k, V v) {
/*  252 */     Entry<K, V> e = add(k);
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
/*      */   private Entry<K, V> add(K k) {
/*  272 */     this.modified = false;
/*  273 */     Entry<K, V> e = null;
/*  274 */     if (this.tree == null) {
/*  275 */       this.count++;
/*  276 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*  277 */       this.modified = true;
/*      */     } else {
/*  279 */       Entry<K, V> p = this.tree, q = null, y = this.tree, z = null, w = null;
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
/*  330 */         Entry<K, V> x = y.left;
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
/*  369 */         Entry<K, V> x = y.right;
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
/*      */   private Entry<K, V> parent(Entry<K, V> e) {
/*  428 */     if (e == this.tree) {
/*  429 */       return null;
/*      */     }
/*  431 */     Entry<K, V> y = e, x = y;
/*      */     while (true) {
/*  433 */       if (y.succ()) {
/*  434 */         Entry<K, V> p = y.right;
/*  435 */         if (p == null || p.left != e) {
/*  436 */           while (!x.pred())
/*  437 */             x = x.left; 
/*  438 */           p = x.left;
/*      */         } 
/*  440 */         return p;
/*  441 */       }  if (x.pred()) {
/*  442 */         Entry<K, V> p = x.left;
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
/*      */   public V remove(Object k) {
/*  461 */     this.modified = false;
/*  462 */     if (this.tree == null) {
/*  463 */       return this.defRetValue;
/*      */     }
/*  465 */     Entry<K, V> p = this.tree, q = null;
/*  466 */     boolean dir = false;
/*  467 */     K kk = (K)k;
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
/*  505 */     else { Entry<K, V> r = p.right;
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
/*      */         Entry<K, V> s;
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
/*  554 */       Entry<K, V> y = q;
/*  555 */       q = parent(y);
/*  556 */       if (!dir) {
/*  557 */         dir = (q != null && q.left != y);
/*  558 */         y.incBalance();
/*  559 */         if (y.balance() == 1)
/*      */           break; 
/*  561 */         if (y.balance() == 2) {
/*  562 */           Entry<K, V> x = y.right;
/*  563 */           assert x != null;
/*  564 */           if (x.balance() == -1) {
/*      */             
/*  566 */             assert x.balance() == -1;
/*  567 */             Entry<K, V> w = x.left;
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
/*  631 */         Entry<K, V> x = y.left;
/*  632 */         assert x != null;
/*  633 */         if (x.balance() == 1) {
/*      */           
/*  635 */           assert x.balance() == 1;
/*  636 */           Entry<K, V> w = x.right;
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
/*      */   private static final class Entry<K, V>
/*      */     extends AbstractObject2ObjectMap.BasicEntry<K, V>
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
/*      */     Entry<K, V> left;
/*      */ 
/*      */     
/*      */     Entry<K, V> right;
/*      */ 
/*      */     
/*      */     int info;
/*      */ 
/*      */ 
/*      */     
/*      */     Entry() {
/*  750 */       super(null, null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(K k, V v) {
/*  761 */       super(k, v);
/*  762 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K, V> left() {
/*  770 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K, V> right() {
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
/*      */     void pred(Entry<K, V> pred) {
/*  827 */       this.info |= 0x40000000;
/*  828 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<K, V> succ) {
/*  837 */       this.info |= Integer.MIN_VALUE;
/*  838 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<K, V> left) {
/*  847 */       this.info &= 0xBFFFFFFF;
/*  848 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<K, V> right) {
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
/*      */     Entry<K, V> next() {
/*  892 */       Entry<K, V> next = this.right;
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
/*      */     Entry<K, V> prev() {
/*  904 */       Entry<K, V> prev = this.left;
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
/*      */     public Entry<K, V> clone() {
/*      */       Entry<K, V> c;
/*      */       try {
/*  921 */         c = (Entry<K, V>)super.clone();
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
/*  935 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/*  936 */       return (Objects.equals(this.key, e.getKey()) && Objects.equals(this.value, e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  940 */       return this.key.hashCode() ^ ((this.value == null) ? 0 : this.value.hashCode());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  944 */       return (new StringBuilder()).append(this.key).append("=>").append(this.value).toString();
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
/*      */   public boolean containsKey(Object k) {
/*  965 */     return (findKey((K)k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  969 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  973 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(Object k) {
/*  978 */     Entry<K, V> e = findKey((K)k);
/*  979 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public K firstKey() {
/*  983 */     if (this.tree == null)
/*  984 */       throw new NoSuchElementException(); 
/*  985 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public K lastKey() {
/*  989 */     if (this.tree == null)
/*  990 */       throw new NoSuchElementException(); 
/*  991 */     return this.lastEntry.key;
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
/*      */     Object2ObjectAVLTreeMap.Entry<K, V> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2ObjectAVLTreeMap.Entry<K, V> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2ObjectAVLTreeMap.Entry<K, V> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1022 */     int index = 0;
/*      */     TreeIterator() {
/* 1024 */       this.next = Object2ObjectAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(K k) {
/* 1027 */       if ((this.next = Object2ObjectAVLTreeMap.this.locateKey(k)) != null)
/* 1028 */         if (Object2ObjectAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1029 */           this.prev = this.next;
/* 1030 */           this.next = this.next.next();
/*      */         } else {
/* 1032 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/* 1036 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1039 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1042 */       this.next = this.next.next();
/*      */     }
/*      */     Object2ObjectAVLTreeMap.Entry<K, V> nextEntry() {
/* 1045 */       if (!hasNext())
/* 1046 */         throw new NoSuchElementException(); 
/* 1047 */       this.curr = this.prev = this.next;
/* 1048 */       this.index++;
/* 1049 */       updateNext();
/* 1050 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1053 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Object2ObjectAVLTreeMap.Entry<K, V> previousEntry() {
/* 1056 */       if (!hasPrevious())
/* 1057 */         throw new NoSuchElementException(); 
/* 1058 */       this.curr = this.next = this.prev;
/* 1059 */       this.index--;
/* 1060 */       updatePrevious();
/* 1061 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1064 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1067 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1070 */       if (this.curr == null) {
/* 1071 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1076 */       if (this.curr == this.prev)
/* 1077 */         this.index--; 
/* 1078 */       this.next = this.prev = this.curr;
/* 1079 */       updatePrevious();
/* 1080 */       updateNext();
/* 1081 */       Object2ObjectAVLTreeMap.this.remove(this.curr.key);
/* 1082 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1085 */       int i = n;
/* 1086 */       while (i-- != 0 && hasNext())
/* 1087 */         nextEntry(); 
/* 1088 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1091 */       int i = n;
/* 1092 */       while (i-- != 0 && hasPrevious())
/* 1093 */         previousEntry(); 
/* 1094 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Object2ObjectMap.Entry<K, V>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(K k) {
/* 1107 */       super(k);
/*      */     }
/*      */     
/*      */     public Object2ObjectMap.Entry<K, V> next() {
/* 1111 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Object2ObjectMap.Entry<K, V> previous() {
/* 1115 */       return previousEntry();
/*      */     }
/*      */     
/*      */     public void set(Object2ObjectMap.Entry<K, V> ok) {
/* 1119 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Object2ObjectMap.Entry<K, V> ok) {
/* 1123 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
/* 1128 */     if (this.entries == null)
/* 1129 */       this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2ObjectMap.Entry<Object2ObjectMap.Entry<K, V>, V>>()
/*      */         {
/*      */           final Comparator<? super Object2ObjectMap.Entry<K, V>> comparator;
/*      */           
/*      */           public Comparator<? super Object2ObjectMap.Entry<K, V>> comparator() {
/* 1134 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> iterator() {
/* 1138 */             return new Object2ObjectAVLTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> iterator(Object2ObjectMap.Entry<K, V> from) {
/* 1143 */             return new Object2ObjectAVLTreeMap.EntryIterator(from.getKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1148 */             if (!(o instanceof Map.Entry))
/* 1149 */               return false; 
/* 1150 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1151 */             Object2ObjectAVLTreeMap.Entry<K, V> f = Object2ObjectAVLTreeMap.this.findKey((K)e.getKey());
/* 1152 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1157 */             if (!(o instanceof Map.Entry))
/* 1158 */               return false; 
/* 1159 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1160 */             Object2ObjectAVLTreeMap.Entry<K, V> f = Object2ObjectAVLTreeMap.this.findKey((K)e.getKey());
/* 1161 */             if (f == null || !Objects.equals(f.getValue(), e.getValue()))
/* 1162 */               return false; 
/* 1163 */             Object2ObjectAVLTreeMap.this.remove(f.key);
/* 1164 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1168 */             return Object2ObjectAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1172 */             Object2ObjectAVLTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Object2ObjectMap.Entry<K, V> first() {
/* 1176 */             return Object2ObjectAVLTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Object2ObjectMap.Entry<K, V> last() {
/* 1180 */             return Object2ObjectAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> subSet(Object2ObjectMap.Entry<K, V> from, Object2ObjectMap.Entry<K, V> to) {
/* 1185 */             return Object2ObjectAVLTreeMap.this.subMap(from.getKey(), to.getKey()).object2ObjectEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> headSet(Object2ObjectMap.Entry<K, V> to) {
/* 1189 */             return Object2ObjectAVLTreeMap.this.headMap(to.getKey()).object2ObjectEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> tailSet(Object2ObjectMap.Entry<K, V> from) {
/* 1193 */             return Object2ObjectAVLTreeMap.this.tailMap(from.getKey()).object2ObjectEntrySet();
/*      */           }
/*      */         }; 
/* 1196 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<K>
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(K k) {
/* 1212 */       super(k);
/*      */     }
/*      */     
/*      */     public K next() {
/* 1216 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1220 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractObject2ObjectSortedMap<K, V>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1227 */       return new Object2ObjectAVLTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1231 */       return new Object2ObjectAVLTreeMap.KeyIterator(from);
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
/*      */   public ObjectSortedSet<K> keySet() {
/* 1246 */     if (this.keys == null)
/* 1247 */       this.keys = new KeySet(); 
/* 1248 */     return this.keys;
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
/* 1263 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public V previous() {
/* 1267 */       return (previousEntry()).value;
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
/* 1282 */     if (this.values == null)
/* 1283 */       this.values = new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1286 */             return new Object2ObjectAVLTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(Object k) {
/* 1290 */             return Object2ObjectAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1294 */             return Object2ObjectAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1298 */             Object2ObjectAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1301 */     return this.values;
/*      */   }
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1305 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Object2ObjectSortedMap<K, V> headMap(K to) {
/* 1309 */     return new Submap(null, true, to, false);
/*      */   }
/*      */   
/*      */   public Object2ObjectSortedMap<K, V> tailMap(K from) {
/* 1313 */     return new Submap(from, false, null, true);
/*      */   }
/*      */   
/*      */   public Object2ObjectSortedMap<K, V> subMap(K from, K to) {
/* 1317 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractObject2ObjectSortedMap<K, V>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     K from;
/*      */ 
/*      */ 
/*      */     
/*      */     K to;
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
/*      */     protected transient ObjectSortedSet<Object2ObjectMap.Entry<K, V>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ObjectCollection<V> values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(K from, boolean bottom, K to, boolean top) {
/* 1361 */       if (!bottom && !top && Object2ObjectAVLTreeMap.this.compare(from, to) > 0)
/* 1362 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1363 */       this.from = from;
/* 1364 */       this.bottom = bottom;
/* 1365 */       this.to = to;
/* 1366 */       this.top = top;
/* 1367 */       this.defRetValue = Object2ObjectAVLTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1371 */       SubmapIterator i = new SubmapIterator();
/* 1372 */       while (i.hasNext()) {
/* 1373 */         i.nextEntry();
/* 1374 */         i.remove();
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
/* 1385 */       return ((this.bottom || Object2ObjectAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Object2ObjectAVLTreeMap.this
/* 1386 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
/* 1390 */       if (this.entries == null)
/* 1391 */         this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2ObjectMap.Entry<Object2ObjectMap.Entry<K, V>, V>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> iterator() {
/* 1394 */               return new Object2ObjectAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> iterator(Object2ObjectMap.Entry<K, V> from) {
/* 1399 */               return new Object2ObjectAVLTreeMap.Submap.SubmapEntryIterator(from.getKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Object2ObjectMap.Entry<K, V>> comparator() {
/* 1403 */               return Object2ObjectAVLTreeMap.this.object2ObjectEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1408 */               if (!(o instanceof Map.Entry))
/* 1409 */                 return false; 
/* 1410 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1411 */               Object2ObjectAVLTreeMap.Entry<K, V> f = Object2ObjectAVLTreeMap.this.findKey((K)e.getKey());
/* 1412 */               return (f != null && Object2ObjectAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1417 */               if (!(o instanceof Map.Entry))
/* 1418 */                 return false; 
/* 1419 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1420 */               Object2ObjectAVLTreeMap.Entry<K, V> f = Object2ObjectAVLTreeMap.this.findKey((K)e.getKey());
/* 1421 */               if (f != null && Object2ObjectAVLTreeMap.Submap.this.in(f.key))
/* 1422 */                 Object2ObjectAVLTreeMap.Submap.this.remove(f.key); 
/* 1423 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1427 */               int c = 0;
/* 1428 */               for (Iterator<?> i = iterator(); i.hasNext(); i.next())
/* 1429 */                 c++; 
/* 1430 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1434 */               return !(new Object2ObjectAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1438 */               Object2ObjectAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Object2ObjectMap.Entry<K, V> first() {
/* 1442 */               return Object2ObjectAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Object2ObjectMap.Entry<K, V> last() {
/* 1446 */               return Object2ObjectAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> subSet(Object2ObjectMap.Entry<K, V> from, Object2ObjectMap.Entry<K, V> to) {
/* 1451 */               return Object2ObjectAVLTreeMap.Submap.this.subMap(from.getKey(), to.getKey()).object2ObjectEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> headSet(Object2ObjectMap.Entry<K, V> to) {
/* 1455 */               return Object2ObjectAVLTreeMap.Submap.this.headMap(to.getKey()).object2ObjectEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> tailSet(Object2ObjectMap.Entry<K, V> from) {
/* 1459 */               return Object2ObjectAVLTreeMap.Submap.this.tailMap(from.getKey()).object2ObjectEntrySet();
/*      */             }
/*      */           }; 
/* 1462 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractObject2ObjectSortedMap<K, V>.KeySet {
/*      */       public ObjectBidirectionalIterator<K> iterator() {
/* 1467 */         return new Object2ObjectAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1471 */         return new Object2ObjectAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> keySet() {
/* 1476 */       if (this.keys == null)
/* 1477 */         this.keys = new KeySet(); 
/* 1478 */       return this.keys;
/*      */     }
/*      */     
/*      */     public ObjectCollection<V> values() {
/* 1482 */       if (this.values == null)
/* 1483 */         this.values = new AbstractObjectCollection<V>()
/*      */           {
/*      */             public ObjectIterator<V> iterator() {
/* 1486 */               return new Object2ObjectAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(Object k) {
/* 1490 */               return Object2ObjectAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1494 */               return Object2ObjectAVLTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1498 */               Object2ObjectAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1501 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object k) {
/* 1506 */       return (in((K)k) && Object2ObjectAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(Object v) {
/* 1510 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1512 */       while (i.hasNext()) {
/* 1513 */         Object ev = (i.nextEntry()).value;
/* 1514 */         if (Objects.equals(ev, v))
/* 1515 */           return true; 
/*      */       } 
/* 1517 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(Object k) {
/* 1523 */       K kk = (K)k; Object2ObjectAVLTreeMap.Entry<K, V> e;
/* 1524 */       return (in(kk) && (e = Object2ObjectAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public V put(K k, V v) {
/* 1528 */       Object2ObjectAVLTreeMap.this.modified = false;
/* 1529 */       if (!in(k))
/* 1530 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1531 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1532 */       V oldValue = Object2ObjectAVLTreeMap.this.put(k, v);
/* 1533 */       return Object2ObjectAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public V remove(Object k) {
/* 1538 */       Object2ObjectAVLTreeMap.this.modified = false;
/* 1539 */       if (!in((K)k))
/* 1540 */         return this.defRetValue; 
/* 1541 */       V oldValue = (V)Object2ObjectAVLTreeMap.this.remove(k);
/* 1542 */       return Object2ObjectAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1546 */       SubmapIterator i = new SubmapIterator();
/* 1547 */       int n = 0;
/* 1548 */       while (i.hasNext()) {
/* 1549 */         n++;
/* 1550 */         i.nextEntry();
/*      */       } 
/* 1552 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1556 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1560 */       return Object2ObjectAVLTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Object2ObjectSortedMap<K, V> headMap(K to) {
/* 1564 */       if (this.top)
/* 1565 */         return new Submap(this.from, this.bottom, to, false); 
/* 1566 */       return (Object2ObjectAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Object2ObjectSortedMap<K, V> tailMap(K from) {
/* 1570 */       if (this.bottom)
/* 1571 */         return new Submap(from, false, this.to, this.top); 
/* 1572 */       return (Object2ObjectAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Object2ObjectSortedMap<K, V> subMap(K from, K to) {
/* 1576 */       if (this.top && this.bottom)
/* 1577 */         return new Submap(from, false, to, false); 
/* 1578 */       if (!this.top)
/* 1579 */         to = (Object2ObjectAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1580 */       if (!this.bottom)
/* 1581 */         from = (Object2ObjectAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1582 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1583 */         return this; 
/* 1584 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2ObjectAVLTreeMap.Entry<K, V> firstEntry() {
/*      */       Object2ObjectAVLTreeMap.Entry<K, V> e;
/* 1593 */       if (Object2ObjectAVLTreeMap.this.tree == null) {
/* 1594 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1598 */       if (this.bottom) {
/* 1599 */         e = Object2ObjectAVLTreeMap.this.firstEntry;
/*      */       } else {
/* 1601 */         e = Object2ObjectAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1603 */         if (Object2ObjectAVLTreeMap.this.compare(e.key, this.from) < 0) {
/* 1604 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1608 */       if (e == null || (!this.top && Object2ObjectAVLTreeMap.this.compare(e.key, this.to) >= 0))
/* 1609 */         return null; 
/* 1610 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2ObjectAVLTreeMap.Entry<K, V> lastEntry() {
/*      */       Object2ObjectAVLTreeMap.Entry<K, V> e;
/* 1619 */       if (Object2ObjectAVLTreeMap.this.tree == null) {
/* 1620 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1624 */       if (this.top) {
/* 1625 */         e = Object2ObjectAVLTreeMap.this.lastEntry;
/*      */       } else {
/* 1627 */         e = Object2ObjectAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1629 */         if (Object2ObjectAVLTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1630 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1634 */       if (e == null || (!this.bottom && Object2ObjectAVLTreeMap.this.compare(e.key, this.from) < 0))
/* 1635 */         return null; 
/* 1636 */       return e;
/*      */     }
/*      */     
/*      */     public K firstKey() {
/* 1640 */       Object2ObjectAVLTreeMap.Entry<K, V> e = firstEntry();
/* 1641 */       if (e == null)
/* 1642 */         throw new NoSuchElementException(); 
/* 1643 */       return e.key;
/*      */     }
/*      */     
/*      */     public K lastKey() {
/* 1647 */       Object2ObjectAVLTreeMap.Entry<K, V> e = lastEntry();
/* 1648 */       if (e == null)
/* 1649 */         throw new NoSuchElementException(); 
/* 1650 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Object2ObjectAVLTreeMap<K, V>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1663 */         this.next = Object2ObjectAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(K k) {
/* 1666 */         this();
/* 1667 */         if (this.next != null)
/* 1668 */           if (!Object2ObjectAVLTreeMap.Submap.this.bottom && Object2ObjectAVLTreeMap.this.compare(k, this.next.key) < 0) {
/* 1669 */             this.prev = null;
/* 1670 */           } else if (!Object2ObjectAVLTreeMap.Submap.this.top && Object2ObjectAVLTreeMap.this.compare(k, (this.prev = Object2ObjectAVLTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1671 */             this.next = null;
/*      */           } else {
/* 1673 */             this.next = Object2ObjectAVLTreeMap.this.locateKey(k);
/* 1674 */             if (Object2ObjectAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1675 */               this.prev = this.next;
/* 1676 */               this.next = this.next.next();
/*      */             } else {
/* 1678 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1684 */         this.prev = this.prev.prev();
/* 1685 */         if (!Object2ObjectAVLTreeMap.Submap.this.bottom && this.prev != null && Object2ObjectAVLTreeMap.this.compare(this.prev.key, Object2ObjectAVLTreeMap.Submap.this.from) < 0)
/* 1686 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1690 */         this.next = this.next.next();
/* 1691 */         if (!Object2ObjectAVLTreeMap.Submap.this.top && this.next != null && Object2ObjectAVLTreeMap.this.compare(this.next.key, Object2ObjectAVLTreeMap.Submap.this.to) >= 0)
/* 1692 */           this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Object2ObjectMap.Entry<K, V>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(K k) {
/* 1701 */         super(k);
/*      */       }
/*      */       
/*      */       public Object2ObjectMap.Entry<K, V> next() {
/* 1705 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Object2ObjectMap.Entry<K, V> previous() {
/* 1709 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements ObjectListIterator<K>
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(K from) {
/* 1727 */         super(from);
/*      */       }
/*      */       
/*      */       public K next() {
/* 1731 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public K previous() {
/* 1735 */         return (previousEntry()).key;
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
/* 1751 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public V previous() {
/* 1755 */         return (previousEntry()).value;
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
/*      */   public Object2ObjectAVLTreeMap<K, V> clone() {
/*      */     Object2ObjectAVLTreeMap<K, V> c;
/*      */     try {
/* 1774 */       c = (Object2ObjectAVLTreeMap<K, V>)super.clone();
/* 1775 */     } catch (CloneNotSupportedException cantHappen) {
/* 1776 */       throw new InternalError();
/*      */     } 
/* 1778 */     c.keys = null;
/* 1779 */     c.values = null;
/* 1780 */     c.entries = null;
/* 1781 */     c.allocatePaths();
/* 1782 */     if (this.count != 0) {
/*      */       
/* 1784 */       Entry<K, V> rp = new Entry<>(), rq = new Entry<>();
/* 1785 */       Entry<K, V> p = rp;
/* 1786 */       rp.left(this.tree);
/* 1787 */       Entry<K, V> q = rq;
/* 1788 */       rq.pred((Entry<K, V>)null);
/*      */       while (true) {
/* 1790 */         if (!p.pred()) {
/* 1791 */           Entry<K, V> e = p.left.clone();
/* 1792 */           e.pred(q.left);
/* 1793 */           e.succ(q);
/* 1794 */           q.left(e);
/* 1795 */           p = p.left;
/* 1796 */           q = q.left;
/*      */         } else {
/* 1798 */           while (p.succ()) {
/* 1799 */             p = p.right;
/* 1800 */             if (p == null) {
/* 1801 */               q.right = null;
/* 1802 */               c.tree = rq.left;
/* 1803 */               c.firstEntry = c.tree;
/* 1804 */               while (c.firstEntry.left != null)
/* 1805 */                 c.firstEntry = c.firstEntry.left; 
/* 1806 */               c.lastEntry = c.tree;
/* 1807 */               while (c.lastEntry.right != null)
/* 1808 */                 c.lastEntry = c.lastEntry.right; 
/* 1809 */               return c;
/*      */             } 
/* 1811 */             q = q.right;
/*      */           } 
/* 1813 */           p = p.right;
/* 1814 */           q = q.right;
/*      */         } 
/* 1816 */         if (!p.succ()) {
/* 1817 */           Entry<K, V> e = p.right.clone();
/* 1818 */           e.succ(q.right);
/* 1819 */           e.pred(q);
/* 1820 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1824 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1827 */     int n = this.count;
/* 1828 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1830 */     s.defaultWriteObject();
/* 1831 */     while (n-- != 0) {
/* 1832 */       Entry<K, V> e = i.nextEntry();
/* 1833 */       s.writeObject(e.key);
/* 1834 */       s.writeObject(e.value);
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
/*      */   private Entry<K, V> readTree(ObjectInputStream s, int n, Entry<K, V> pred, Entry<K, V> succ) throws IOException, ClassNotFoundException {
/* 1855 */     if (n == 1) {
/* 1856 */       Entry<K, V> entry = new Entry<>((K)s.readObject(), (V)s.readObject());
/* 1857 */       entry.pred(pred);
/* 1858 */       entry.succ(succ);
/* 1859 */       return entry;
/*      */     } 
/* 1861 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1866 */       Entry<K, V> entry = new Entry<>((K)s.readObject(), (V)s.readObject());
/* 1867 */       entry.right(new Entry<>((K)s.readObject(), (V)s.readObject()));
/* 1868 */       entry.right.pred(entry);
/* 1869 */       entry.balance(1);
/* 1870 */       entry.pred(pred);
/* 1871 */       entry.right.succ(succ);
/* 1872 */       return entry;
/*      */     } 
/*      */     
/* 1875 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1876 */     Entry<K, V> top = new Entry<>();
/* 1877 */     top.left(readTree(s, leftN, pred, top));
/* 1878 */     top.key = (K)s.readObject();
/* 1879 */     top.value = (V)s.readObject();
/* 1880 */     top.right(readTree(s, rightN, top, succ));
/* 1881 */     if (n == (n & -n))
/* 1882 */       top.balance(1); 
/* 1883 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1886 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1891 */     setActualComparator();
/* 1892 */     allocatePaths();
/* 1893 */     if (this.count != 0) {
/* 1894 */       this.tree = readTree(s, this.count, (Entry<K, V>)null, (Entry<K, V>)null);
/*      */       
/* 1896 */       Entry<K, V> e = this.tree;
/* 1897 */       while (e.left() != null)
/* 1898 */         e = e.left(); 
/* 1899 */       this.firstEntry = e;
/* 1900 */       e = this.tree;
/* 1901 */       while (e.right() != null)
/* 1902 */         e = e.right(); 
/* 1903 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ObjectAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */