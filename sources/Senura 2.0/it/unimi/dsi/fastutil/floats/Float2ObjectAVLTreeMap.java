/*      */ package it.unimi.dsi.fastutil.floats;
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
/*      */ public class Float2ObjectAVLTreeMap<V>
/*      */   extends AbstractFloat2ObjectSortedMap<V>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<V> tree;
/*      */   protected int count;
/*      */   protected transient Entry<V> firstEntry;
/*      */   protected transient Entry<V> lastEntry;
/*      */   protected transient ObjectSortedSet<Float2ObjectMap.Entry<V>> entries;
/*      */   protected transient FloatSortedSet keys;
/*      */   protected transient ObjectCollection<V> values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Float> storedComparator;
/*      */   protected transient FloatComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Float2ObjectAVLTreeMap() {
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
/*   91 */     this.actualComparator = FloatComparators.asFloatComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ObjectAVLTreeMap(Comparator<? super Float> c) {
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
/*      */   public Float2ObjectAVLTreeMap(Map<? extends Float, ? extends V> m) {
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
/*      */   public Float2ObjectAVLTreeMap(SortedMap<Float, V> m) {
/*  122 */     this(m.comparator());
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ObjectAVLTreeMap(Float2ObjectMap<? extends V> m) {
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
/*      */   public Float2ObjectAVLTreeMap(Float2ObjectSortedMap<V> m) {
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
/*      */   public Float2ObjectAVLTreeMap(float[] k, V[] v, Comparator<? super Float> c) {
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
/*      */   public Float2ObjectAVLTreeMap(float[] k, V[] v) {
/*  178 */     this(k, v, (Comparator<? super Float>)null);
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
/*  206 */     return (this.actualComparator == null) ? Float.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry<V> findKey(float k) {
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
/*      */   final Entry<V> locateKey(float k) {
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
/*      */   public V put(float k, V v) {
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
/*      */   private Entry<V> add(float k) {
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
/*      */   public V remove(float k) {
/*  461 */     this.modified = false;
/*  462 */     if (this.tree == null) {
/*  463 */       return this.defRetValue;
/*      */     }
/*  465 */     Entry<V> p = this.tree, q = null;
/*  466 */     boolean dir = false;
/*  467 */     float kk = k;
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
/*      */     extends AbstractFloat2ObjectMap.BasicEntry<V>
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
/*  750 */       super(0.0F, (V)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(float k, V v) {
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
/*  935 */       Map.Entry<Float, V> e = (Map.Entry<Float, V>)o;
/*  936 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && 
/*  937 */         Objects.equals(this.value, e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  941 */       return HashCommon.float2int(this.key) ^ ((this.value == null) ? 0 : this.value.hashCode());
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
/*      */   public boolean containsKey(float k) {
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
/*      */   public V get(float k) {
/*  979 */     Entry<V> e = findKey(k);
/*  980 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public float firstFloatKey() {
/*  984 */     if (this.tree == null)
/*  985 */       throw new NoSuchElementException(); 
/*  986 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public float lastFloatKey() {
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
/*      */     Float2ObjectAVLTreeMap.Entry<V> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Float2ObjectAVLTreeMap.Entry<V> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Float2ObjectAVLTreeMap.Entry<V> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1023 */     int index = 0;
/*      */     TreeIterator() {
/* 1025 */       this.next = Float2ObjectAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(float k) {
/* 1028 */       if ((this.next = Float2ObjectAVLTreeMap.this.locateKey(k)) != null)
/* 1029 */         if (Float2ObjectAVLTreeMap.this.compare(this.next.key, k) <= 0) {
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
/*      */     Float2ObjectAVLTreeMap.Entry<V> nextEntry() {
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
/*      */     Float2ObjectAVLTreeMap.Entry<V> previousEntry() {
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
/* 1082 */       Float2ObjectAVLTreeMap.this.remove(this.curr.key);
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
/*      */     implements ObjectListIterator<Float2ObjectMap.Entry<V>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(float k) {
/* 1108 */       super(k);
/*      */     }
/*      */     
/*      */     public Float2ObjectMap.Entry<V> next() {
/* 1112 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Float2ObjectMap.Entry<V> previous() {
/* 1116 */       return previousEntry();
/*      */     }
/*      */     
/*      */     public void set(Float2ObjectMap.Entry<V> ok) {
/* 1120 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Float2ObjectMap.Entry<V> ok) {
/* 1124 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet() {
/* 1129 */     if (this.entries == null)
/* 1130 */       this.entries = (ObjectSortedSet<Float2ObjectMap.Entry<V>>)new AbstractObjectSortedSet<Float2ObjectMap.Entry<V>>()
/*      */         {
/*      */           final Comparator<? super Float2ObjectMap.Entry<V>> comparator;
/*      */           
/*      */           public Comparator<? super Float2ObjectMap.Entry<V>> comparator() {
/* 1135 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> iterator() {
/* 1139 */             return (ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>>)new Float2ObjectAVLTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> iterator(Float2ObjectMap.Entry<V> from) {
/* 1144 */             return (ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>>)new Float2ObjectAVLTreeMap.EntryIterator(from.getFloatKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1149 */             if (!(o instanceof Map.Entry))
/* 1150 */               return false; 
/* 1151 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1152 */             if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1153 */               return false; 
/* 1154 */             Float2ObjectAVLTreeMap.Entry<V> f = Float2ObjectAVLTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1155 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1160 */             if (!(o instanceof Map.Entry))
/* 1161 */               return false; 
/* 1162 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1163 */             if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1164 */               return false; 
/* 1165 */             Float2ObjectAVLTreeMap.Entry<V> f = Float2ObjectAVLTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1166 */             if (f == null || !Objects.equals(f.getValue(), e.getValue()))
/* 1167 */               return false; 
/* 1168 */             Float2ObjectAVLTreeMap.this.remove(f.key);
/* 1169 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1173 */             return Float2ObjectAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1177 */             Float2ObjectAVLTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Float2ObjectMap.Entry<V> first() {
/* 1181 */             return Float2ObjectAVLTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Float2ObjectMap.Entry<V> last() {
/* 1185 */             return Float2ObjectAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Float2ObjectMap.Entry<V>> subSet(Float2ObjectMap.Entry<V> from, Float2ObjectMap.Entry<V> to) {
/* 1190 */             return Float2ObjectAVLTreeMap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2ObjectEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Float2ObjectMap.Entry<V>> headSet(Float2ObjectMap.Entry<V> to) {
/* 1194 */             return Float2ObjectAVLTreeMap.this.headMap(to.getFloatKey()).float2ObjectEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Float2ObjectMap.Entry<V>> tailSet(Float2ObjectMap.Entry<V> from) {
/* 1198 */             return Float2ObjectAVLTreeMap.this.tailMap(from.getFloatKey()).float2ObjectEntrySet();
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
/*      */     implements FloatListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(float k) {
/* 1217 */       super(k);
/*      */     }
/*      */     
/*      */     public float nextFloat() {
/* 1221 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public float previousFloat() {
/* 1225 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractFloat2ObjectSortedMap<V>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public FloatBidirectionalIterator iterator() {
/* 1232 */       return new Float2ObjectAVLTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public FloatBidirectionalIterator iterator(float from) {
/* 1236 */       return new Float2ObjectAVLTreeMap.KeyIterator(from);
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
/*      */     implements ObjectListIterator<V>
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public V next() {
/* 1268 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public V previous() {
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
/*      */   public ObjectCollection<V> values() {
/* 1287 */     if (this.values == null)
/* 1288 */       this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1291 */             return (ObjectIterator<V>)new Float2ObjectAVLTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(Object k) {
/* 1295 */             return Float2ObjectAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1299 */             return Float2ObjectAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1303 */             Float2ObjectAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1306 */     return this.values;
/*      */   }
/*      */   
/*      */   public FloatComparator comparator() {
/* 1310 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Float2ObjectSortedMap<V> headMap(float to) {
/* 1314 */     return new Submap(0.0F, true, to, false);
/*      */   }
/*      */   
/*      */   public Float2ObjectSortedMap<V> tailMap(float from) {
/* 1318 */     return new Submap(from, false, 0.0F, true);
/*      */   }
/*      */   
/*      */   public Float2ObjectSortedMap<V> subMap(float from, float to) {
/* 1322 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractFloat2ObjectSortedMap<V>
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
/*      */     protected transient ObjectSortedSet<Float2ObjectMap.Entry<V>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient FloatSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ObjectCollection<V> values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(float from, boolean bottom, float to, boolean top) {
/* 1366 */       if (!bottom && !top && Float2ObjectAVLTreeMap.this.compare(from, to) > 0)
/* 1367 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1368 */       this.from = from;
/* 1369 */       this.bottom = bottom;
/* 1370 */       this.to = to;
/* 1371 */       this.top = top;
/* 1372 */       this.defRetValue = Float2ObjectAVLTreeMap.this.defRetValue;
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
/*      */     final boolean in(float k) {
/* 1390 */       return ((this.bottom || Float2ObjectAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Float2ObjectAVLTreeMap.this
/* 1391 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet() {
/* 1395 */       if (this.entries == null)
/* 1396 */         this.entries = (ObjectSortedSet<Float2ObjectMap.Entry<V>>)new AbstractObjectSortedSet<Float2ObjectMap.Entry<V>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> iterator() {
/* 1399 */               return (ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>>)new Float2ObjectAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> iterator(Float2ObjectMap.Entry<V> from) {
/* 1404 */               return (ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>>)new Float2ObjectAVLTreeMap.Submap.SubmapEntryIterator(from.getFloatKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Float2ObjectMap.Entry<V>> comparator() {
/* 1408 */               return Float2ObjectAVLTreeMap.this.float2ObjectEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1413 */               if (!(o instanceof Map.Entry))
/* 1414 */                 return false; 
/* 1415 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1416 */               if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1417 */                 return false; 
/* 1418 */               Float2ObjectAVLTreeMap.Entry<V> f = Float2ObjectAVLTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1419 */               return (f != null && Float2ObjectAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1424 */               if (!(o instanceof Map.Entry))
/* 1425 */                 return false; 
/* 1426 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1427 */               if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1428 */                 return false; 
/* 1429 */               Float2ObjectAVLTreeMap.Entry<V> f = Float2ObjectAVLTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1430 */               if (f != null && Float2ObjectAVLTreeMap.Submap.this.in(f.key))
/* 1431 */                 Float2ObjectAVLTreeMap.Submap.this.remove(f.key); 
/* 1432 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1436 */               int c = 0;
/* 1437 */               for (ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1438 */                 c++; 
/* 1439 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1443 */               return !(new Float2ObjectAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1447 */               Float2ObjectAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Float2ObjectMap.Entry<V> first() {
/* 1451 */               return Float2ObjectAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Float2ObjectMap.Entry<V> last() {
/* 1455 */               return Float2ObjectAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Float2ObjectMap.Entry<V>> subSet(Float2ObjectMap.Entry<V> from, Float2ObjectMap.Entry<V> to) {
/* 1460 */               return Float2ObjectAVLTreeMap.Submap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2ObjectEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Float2ObjectMap.Entry<V>> headSet(Float2ObjectMap.Entry<V> to) {
/* 1464 */               return Float2ObjectAVLTreeMap.Submap.this.headMap(to.getFloatKey()).float2ObjectEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Float2ObjectMap.Entry<V>> tailSet(Float2ObjectMap.Entry<V> from) {
/* 1468 */               return Float2ObjectAVLTreeMap.Submap.this.tailMap(from.getFloatKey()).float2ObjectEntrySet();
/*      */             }
/*      */           }; 
/* 1471 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractFloat2ObjectSortedMap<V>.KeySet {
/*      */       public FloatBidirectionalIterator iterator() {
/* 1476 */         return new Float2ObjectAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public FloatBidirectionalIterator iterator(float from) {
/* 1480 */         return new Float2ObjectAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public FloatSortedSet keySet() {
/* 1485 */       if (this.keys == null)
/* 1486 */         this.keys = new KeySet(); 
/* 1487 */       return this.keys;
/*      */     }
/*      */     
/*      */     public ObjectCollection<V> values() {
/* 1491 */       if (this.values == null)
/* 1492 */         this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */           {
/*      */             public ObjectIterator<V> iterator() {
/* 1495 */               return (ObjectIterator<V>)new Float2ObjectAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(Object k) {
/* 1499 */               return Float2ObjectAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1503 */               return Float2ObjectAVLTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1507 */               Float2ObjectAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1510 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(float k) {
/* 1515 */       return (in(k) && Float2ObjectAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(Object v) {
/* 1519 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1521 */       while (i.hasNext()) {
/* 1522 */         Object ev = (i.nextEntry()).value;
/* 1523 */         if (Objects.equals(ev, v))
/* 1524 */           return true; 
/*      */       } 
/* 1526 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(float k) {
/* 1532 */       float kk = k; Float2ObjectAVLTreeMap.Entry<V> e;
/* 1533 */       return (in(kk) && (e = Float2ObjectAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public V put(float k, V v) {
/* 1537 */       Float2ObjectAVLTreeMap.this.modified = false;
/* 1538 */       if (!in(k))
/* 1539 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1540 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1541 */       V oldValue = Float2ObjectAVLTreeMap.this.put(k, v);
/* 1542 */       return Float2ObjectAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public V remove(float k) {
/* 1547 */       Float2ObjectAVLTreeMap.this.modified = false;
/* 1548 */       if (!in(k))
/* 1549 */         return this.defRetValue; 
/* 1550 */       V oldValue = Float2ObjectAVLTreeMap.this.remove(k);
/* 1551 */       return Float2ObjectAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1555 */       SubmapIterator i = new SubmapIterator();
/* 1556 */       int n = 0;
/* 1557 */       while (i.hasNext()) {
/* 1558 */         n++;
/* 1559 */         i.nextEntry();
/*      */       } 
/* 1561 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1565 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public FloatComparator comparator() {
/* 1569 */       return Float2ObjectAVLTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Float2ObjectSortedMap<V> headMap(float to) {
/* 1573 */       if (this.top)
/* 1574 */         return new Submap(this.from, this.bottom, to, false); 
/* 1575 */       return (Float2ObjectAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Float2ObjectSortedMap<V> tailMap(float from) {
/* 1579 */       if (this.bottom)
/* 1580 */         return new Submap(from, false, this.to, this.top); 
/* 1581 */       return (Float2ObjectAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Float2ObjectSortedMap<V> subMap(float from, float to) {
/* 1585 */       if (this.top && this.bottom)
/* 1586 */         return new Submap(from, false, to, false); 
/* 1587 */       if (!this.top)
/* 1588 */         to = (Float2ObjectAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1589 */       if (!this.bottom)
/* 1590 */         from = (Float2ObjectAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1591 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1592 */         return this; 
/* 1593 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Float2ObjectAVLTreeMap.Entry<V> firstEntry() {
/*      */       Float2ObjectAVLTreeMap.Entry<V> e;
/* 1602 */       if (Float2ObjectAVLTreeMap.this.tree == null) {
/* 1603 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1607 */       if (this.bottom) {
/* 1608 */         e = Float2ObjectAVLTreeMap.this.firstEntry;
/*      */       } else {
/* 1610 */         e = Float2ObjectAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1612 */         if (Float2ObjectAVLTreeMap.this.compare(e.key, this.from) < 0) {
/* 1613 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1617 */       if (e == null || (!this.top && Float2ObjectAVLTreeMap.this.compare(e.key, this.to) >= 0))
/* 1618 */         return null; 
/* 1619 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Float2ObjectAVLTreeMap.Entry<V> lastEntry() {
/*      */       Float2ObjectAVLTreeMap.Entry<V> e;
/* 1628 */       if (Float2ObjectAVLTreeMap.this.tree == null) {
/* 1629 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1633 */       if (this.top) {
/* 1634 */         e = Float2ObjectAVLTreeMap.this.lastEntry;
/*      */       } else {
/* 1636 */         e = Float2ObjectAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1638 */         if (Float2ObjectAVLTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1639 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1643 */       if (e == null || (!this.bottom && Float2ObjectAVLTreeMap.this.compare(e.key, this.from) < 0))
/* 1644 */         return null; 
/* 1645 */       return e;
/*      */     }
/*      */     
/*      */     public float firstFloatKey() {
/* 1649 */       Float2ObjectAVLTreeMap.Entry<V> e = firstEntry();
/* 1650 */       if (e == null)
/* 1651 */         throw new NoSuchElementException(); 
/* 1652 */       return e.key;
/*      */     }
/*      */     
/*      */     public float lastFloatKey() {
/* 1656 */       Float2ObjectAVLTreeMap.Entry<V> e = lastEntry();
/* 1657 */       if (e == null)
/* 1658 */         throw new NoSuchElementException(); 
/* 1659 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Float2ObjectAVLTreeMap<V>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1672 */         this.next = Float2ObjectAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(float k) {
/* 1675 */         this();
/* 1676 */         if (this.next != null)
/* 1677 */           if (!Float2ObjectAVLTreeMap.Submap.this.bottom && Float2ObjectAVLTreeMap.this.compare(k, this.next.key) < 0) {
/* 1678 */             this.prev = null;
/* 1679 */           } else if (!Float2ObjectAVLTreeMap.Submap.this.top && Float2ObjectAVLTreeMap.this.compare(k, (this.prev = Float2ObjectAVLTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1680 */             this.next = null;
/*      */           } else {
/* 1682 */             this.next = Float2ObjectAVLTreeMap.this.locateKey(k);
/* 1683 */             if (Float2ObjectAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1684 */               this.prev = this.next;
/* 1685 */               this.next = this.next.next();
/*      */             } else {
/* 1687 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1693 */         this.prev = this.prev.prev();
/* 1694 */         if (!Float2ObjectAVLTreeMap.Submap.this.bottom && this.prev != null && Float2ObjectAVLTreeMap.this.compare(this.prev.key, Float2ObjectAVLTreeMap.Submap.this.from) < 0)
/* 1695 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1699 */         this.next = this.next.next();
/* 1700 */         if (!Float2ObjectAVLTreeMap.Submap.this.top && this.next != null && Float2ObjectAVLTreeMap.this.compare(this.next.key, Float2ObjectAVLTreeMap.Submap.this.to) >= 0)
/* 1701 */           this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Float2ObjectMap.Entry<V>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(float k) {
/* 1710 */         super(k);
/*      */       }
/*      */       
/*      */       public Float2ObjectMap.Entry<V> next() {
/* 1714 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Float2ObjectMap.Entry<V> previous() {
/* 1718 */         return previousEntry();
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
/* 1736 */         super(from);
/*      */       }
/*      */       
/*      */       public float nextFloat() {
/* 1740 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public float previousFloat() {
/* 1744 */         return (previousEntry()).key;
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
/* 1760 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public V previous() {
/* 1764 */         return (previousEntry()).value;
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
/*      */   public Float2ObjectAVLTreeMap<V> clone() {
/*      */     Float2ObjectAVLTreeMap<V> c;
/*      */     try {
/* 1783 */       c = (Float2ObjectAVLTreeMap<V>)super.clone();
/* 1784 */     } catch (CloneNotSupportedException cantHappen) {
/* 1785 */       throw new InternalError();
/*      */     } 
/* 1787 */     c.keys = null;
/* 1788 */     c.values = null;
/* 1789 */     c.entries = null;
/* 1790 */     c.allocatePaths();
/* 1791 */     if (this.count != 0) {
/*      */       
/* 1793 */       Entry<V> rp = new Entry<>(), rq = new Entry<>();
/* 1794 */       Entry<V> p = rp;
/* 1795 */       rp.left(this.tree);
/* 1796 */       Entry<V> q = rq;
/* 1797 */       rq.pred((Entry<V>)null);
/*      */       while (true) {
/* 1799 */         if (!p.pred()) {
/* 1800 */           Entry<V> e = p.left.clone();
/* 1801 */           e.pred(q.left);
/* 1802 */           e.succ(q);
/* 1803 */           q.left(e);
/* 1804 */           p = p.left;
/* 1805 */           q = q.left;
/*      */         } else {
/* 1807 */           while (p.succ()) {
/* 1808 */             p = p.right;
/* 1809 */             if (p == null) {
/* 1810 */               q.right = null;
/* 1811 */               c.tree = rq.left;
/* 1812 */               c.firstEntry = c.tree;
/* 1813 */               while (c.firstEntry.left != null)
/* 1814 */                 c.firstEntry = c.firstEntry.left; 
/* 1815 */               c.lastEntry = c.tree;
/* 1816 */               while (c.lastEntry.right != null)
/* 1817 */                 c.lastEntry = c.lastEntry.right; 
/* 1818 */               return c;
/*      */             } 
/* 1820 */             q = q.right;
/*      */           } 
/* 1822 */           p = p.right;
/* 1823 */           q = q.right;
/*      */         } 
/* 1825 */         if (!p.succ()) {
/* 1826 */           Entry<V> e = p.right.clone();
/* 1827 */           e.succ(q.right);
/* 1828 */           e.pred(q);
/* 1829 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1833 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1836 */     int n = this.count;
/* 1837 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1839 */     s.defaultWriteObject();
/* 1840 */     while (n-- != 0) {
/* 1841 */       Entry<V> e = i.nextEntry();
/* 1842 */       s.writeFloat(e.key);
/* 1843 */       s.writeObject(e.value);
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
/* 1864 */     if (n == 1) {
/* 1865 */       Entry<V> entry = new Entry<>(s.readFloat(), (V)s.readObject());
/* 1866 */       entry.pred(pred);
/* 1867 */       entry.succ(succ);
/* 1868 */       return entry;
/*      */     } 
/* 1870 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1875 */       Entry<V> entry = new Entry<>(s.readFloat(), (V)s.readObject());
/* 1876 */       entry.right(new Entry<>(s.readFloat(), (V)s.readObject()));
/* 1877 */       entry.right.pred(entry);
/* 1878 */       entry.balance(1);
/* 1879 */       entry.pred(pred);
/* 1880 */       entry.right.succ(succ);
/* 1881 */       return entry;
/*      */     } 
/*      */     
/* 1884 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1885 */     Entry<V> top = new Entry<>();
/* 1886 */     top.left(readTree(s, leftN, pred, top));
/* 1887 */     top.key = s.readFloat();
/* 1888 */     top.value = (V)s.readObject();
/* 1889 */     top.right(readTree(s, rightN, top, succ));
/* 1890 */     if (n == (n & -n))
/* 1891 */       top.balance(1); 
/* 1892 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1895 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1900 */     setActualComparator();
/* 1901 */     allocatePaths();
/* 1902 */     if (this.count != 0) {
/* 1903 */       this.tree = readTree(s, this.count, (Entry<V>)null, (Entry<V>)null);
/*      */       
/* 1905 */       Entry<V> e = this.tree;
/* 1906 */       while (e.left() != null)
/* 1907 */         e = e.left(); 
/* 1908 */       this.firstEntry = e;
/* 1909 */       e = this.tree;
/* 1910 */       while (e.right() != null)
/* 1911 */         e = e.right(); 
/* 1912 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2ObjectAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */