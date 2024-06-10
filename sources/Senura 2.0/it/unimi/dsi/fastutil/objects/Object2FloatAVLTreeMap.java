/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*      */ import it.unimi.dsi.fastutil.floats.FloatListIterator;
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
/*      */ public class Object2FloatAVLTreeMap<K>
/*      */   extends AbstractObject2FloatSortedMap<K>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<K> tree;
/*      */   protected int count;
/*      */   protected transient Entry<K> firstEntry;
/*      */   protected transient Entry<K> lastEntry;
/*      */   protected transient ObjectSortedSet<Object2FloatMap.Entry<K>> entries;
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */   protected transient FloatCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super K> storedComparator;
/*      */   protected transient Comparator<? super K> actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Object2FloatAVLTreeMap() {
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
/*   92 */     this.actualComparator = this.storedComparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2FloatAVLTreeMap(Comparator<? super K> c) {
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
/*      */   public Object2FloatAVLTreeMap(Map<? extends K, ? extends Float> m) {
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
/*      */   public Object2FloatAVLTreeMap(SortedMap<K, Float> m) {
/*  123 */     this(m.comparator());
/*  124 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2FloatAVLTreeMap(Object2FloatMap<? extends K> m) {
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
/*      */   public Object2FloatAVLTreeMap(Object2FloatSortedMap<K> m) {
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
/*      */   public Object2FloatAVLTreeMap(K[] k, float[] v, Comparator<? super K> c) {
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
/*      */   public Object2FloatAVLTreeMap(K[] k, float[] v) {
/*  179 */     this(k, v, (Comparator<? super K>)null);
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
/*  207 */     return (this.actualComparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry<K> findKey(K k) {
/*  219 */     Entry<K> e = this.tree;
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
/*      */   final Entry<K> locateKey(K k) {
/*  235 */     Entry<K> e = this.tree, last = this.tree;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float addTo(K k, float incr) {
/*  268 */     Entry<K> e = add(k);
/*  269 */     float oldValue = e.value;
/*  270 */     e.value += incr;
/*  271 */     return oldValue;
/*      */   }
/*      */   
/*      */   public float put(K k, float v) {
/*  275 */     Entry<K> e = add(k);
/*  276 */     float oldValue = e.value;
/*  277 */     e.value = v;
/*  278 */     return oldValue;
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
/*      */   private Entry<K> add(K k) {
/*  295 */     this.modified = false;
/*  296 */     Entry<K> e = null;
/*  297 */     if (this.tree == null) {
/*  298 */       this.count++;
/*  299 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*  300 */       this.modified = true;
/*      */     } else {
/*  302 */       Entry<K> p = this.tree, q = null, y = this.tree, z = null, w = null;
/*  303 */       int i = 0; while (true) {
/*      */         int cmp;
/*  305 */         if ((cmp = compare(k, p.key)) == 0) {
/*  306 */           return p;
/*      */         }
/*  308 */         if (p.balance() != 0) {
/*  309 */           i = 0;
/*  310 */           z = q;
/*  311 */           y = p;
/*      */         } 
/*  313 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  314 */           if (p.succ()) {
/*  315 */             this.count++;
/*  316 */             e = new Entry<>(k, this.defRetValue);
/*  317 */             this.modified = true;
/*  318 */             if (p.right == null)
/*  319 */               this.lastEntry = e; 
/*  320 */             e.left = p;
/*  321 */             e.right = p.right;
/*  322 */             p.right(e);
/*      */             break;
/*      */           } 
/*  325 */           q = p;
/*  326 */           p = p.right; continue;
/*      */         } 
/*  328 */         if (p.pred()) {
/*  329 */           this.count++;
/*  330 */           e = new Entry<>(k, this.defRetValue);
/*  331 */           this.modified = true;
/*  332 */           if (p.left == null)
/*  333 */             this.firstEntry = e; 
/*  334 */           e.right = p;
/*  335 */           e.left = p.left;
/*  336 */           p.left(e);
/*      */           break;
/*      */         } 
/*  339 */         q = p;
/*  340 */         p = p.left;
/*      */       } 
/*      */       
/*  343 */       p = y;
/*  344 */       i = 0;
/*  345 */       while (p != e) {
/*  346 */         if (this.dirPath[i]) {
/*  347 */           p.incBalance();
/*      */         } else {
/*  349 */           p.decBalance();
/*  350 */         }  p = this.dirPath[i++] ? p.right : p.left;
/*      */       } 
/*  352 */       if (y.balance() == -2) {
/*  353 */         Entry<K> x = y.left;
/*  354 */         if (x.balance() == -1) {
/*  355 */           w = x;
/*  356 */           if (x.succ()) {
/*  357 */             x.succ(false);
/*  358 */             y.pred(x);
/*      */           } else {
/*  360 */             y.left = x.right;
/*  361 */           }  x.right = y;
/*  362 */           x.balance(0);
/*  363 */           y.balance(0);
/*      */         } else {
/*  365 */           assert x.balance() == 1;
/*  366 */           w = x.right;
/*  367 */           x.right = w.left;
/*  368 */           w.left = x;
/*  369 */           y.left = w.right;
/*  370 */           w.right = y;
/*  371 */           if (w.balance() == -1) {
/*  372 */             x.balance(0);
/*  373 */             y.balance(1);
/*  374 */           } else if (w.balance() == 0) {
/*  375 */             x.balance(0);
/*  376 */             y.balance(0);
/*      */           } else {
/*  378 */             x.balance(-1);
/*  379 */             y.balance(0);
/*      */           } 
/*  381 */           w.balance(0);
/*  382 */           if (w.pred()) {
/*  383 */             x.succ(w);
/*  384 */             w.pred(false);
/*      */           } 
/*  386 */           if (w.succ()) {
/*  387 */             y.pred(w);
/*  388 */             w.succ(false);
/*      */           } 
/*      */         } 
/*  391 */       } else if (y.balance() == 2) {
/*  392 */         Entry<K> x = y.right;
/*  393 */         if (x.balance() == 1) {
/*  394 */           w = x;
/*  395 */           if (x.pred()) {
/*  396 */             x.pred(false);
/*  397 */             y.succ(x);
/*      */           } else {
/*  399 */             y.right = x.left;
/*  400 */           }  x.left = y;
/*  401 */           x.balance(0);
/*  402 */           y.balance(0);
/*      */         } else {
/*  404 */           assert x.balance() == -1;
/*  405 */           w = x.left;
/*  406 */           x.left = w.right;
/*  407 */           w.right = x;
/*  408 */           y.right = w.left;
/*  409 */           w.left = y;
/*  410 */           if (w.balance() == 1) {
/*  411 */             x.balance(0);
/*  412 */             y.balance(-1);
/*  413 */           } else if (w.balance() == 0) {
/*  414 */             x.balance(0);
/*  415 */             y.balance(0);
/*      */           } else {
/*  417 */             x.balance(1);
/*  418 */             y.balance(0);
/*      */           } 
/*  420 */           w.balance(0);
/*  421 */           if (w.pred()) {
/*  422 */             y.succ(w);
/*  423 */             w.pred(false);
/*      */           } 
/*  425 */           if (w.succ()) {
/*  426 */             x.pred(w);
/*  427 */             w.succ(false);
/*      */           } 
/*      */         } 
/*      */       } else {
/*  431 */         return e;
/*  432 */       }  if (z == null) {
/*  433 */         this.tree = w;
/*      */       }
/*  435 */       else if (z.left == y) {
/*  436 */         z.left = w;
/*      */       } else {
/*  438 */         z.right = w;
/*      */       } 
/*      */     } 
/*  441 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry<K> parent(Entry<K> e) {
/*  451 */     if (e == this.tree) {
/*  452 */       return null;
/*      */     }
/*  454 */     Entry<K> y = e, x = y;
/*      */     while (true) {
/*  456 */       if (y.succ()) {
/*  457 */         Entry<K> p = y.right;
/*  458 */         if (p == null || p.left != e) {
/*  459 */           while (!x.pred())
/*  460 */             x = x.left; 
/*  461 */           p = x.left;
/*      */         } 
/*  463 */         return p;
/*  464 */       }  if (x.pred()) {
/*  465 */         Entry<K> p = x.left;
/*  466 */         if (p == null || p.right != e) {
/*  467 */           while (!y.succ())
/*  468 */             y = y.right; 
/*  469 */           p = y.right;
/*      */         } 
/*  471 */         return p;
/*      */       } 
/*  473 */       x = x.left;
/*  474 */       y = y.right;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float removeFloat(Object k) {
/*  484 */     this.modified = false;
/*  485 */     if (this.tree == null) {
/*  486 */       return this.defRetValue;
/*      */     }
/*  488 */     Entry<K> p = this.tree, q = null;
/*  489 */     boolean dir = false;
/*  490 */     K kk = (K)k;
/*      */     int cmp;
/*  492 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  494 */       if (dir = (cmp > 0)) {
/*  495 */         q = p;
/*  496 */         if ((p = p.right()) == null)
/*  497 */           return this.defRetValue;  continue;
/*      */       } 
/*  499 */       q = p;
/*  500 */       if ((p = p.left()) == null) {
/*  501 */         return this.defRetValue;
/*      */       }
/*      */     } 
/*  504 */     if (p.left == null)
/*  505 */       this.firstEntry = p.next(); 
/*  506 */     if (p.right == null)
/*  507 */       this.lastEntry = p.prev(); 
/*  508 */     if (p.succ())
/*  509 */     { if (p.pred())
/*  510 */       { if (q != null)
/*  511 */         { if (dir) {
/*  512 */             q.succ(p.right);
/*      */           } else {
/*  514 */             q.pred(p.left);
/*      */           }  }
/*  516 */         else { this.tree = dir ? p.right : p.left; }
/*      */          }
/*  518 */       else { (p.prev()).right = p.right;
/*  519 */         if (q != null)
/*  520 */         { if (dir) {
/*  521 */             q.right = p.left;
/*      */           } else {
/*  523 */             q.left = p.left;
/*      */           }  }
/*  525 */         else { this.tree = p.left; }
/*      */          }
/*      */        }
/*  528 */     else { Entry<K> r = p.right;
/*  529 */       if (r.pred()) {
/*  530 */         r.left = p.left;
/*  531 */         r.pred(p.pred());
/*  532 */         if (!r.pred())
/*  533 */           (r.prev()).right = r; 
/*  534 */         if (q != null)
/*  535 */         { if (dir) {
/*  536 */             q.right = r;
/*      */           } else {
/*  538 */             q.left = r;
/*      */           }  }
/*  540 */         else { this.tree = r; }
/*  541 */          r.balance(p.balance());
/*  542 */         q = r;
/*  543 */         dir = true;
/*      */       } else {
/*      */         Entry<K> s;
/*      */         while (true) {
/*  547 */           s = r.left;
/*  548 */           if (s.pred())
/*      */             break; 
/*  550 */           r = s;
/*      */         } 
/*  552 */         if (s.succ()) {
/*  553 */           r.pred(s);
/*      */         } else {
/*  555 */           r.left = s.right;
/*  556 */         }  s.left = p.left;
/*  557 */         if (!p.pred()) {
/*  558 */           (p.prev()).right = s;
/*  559 */           s.pred(false);
/*      */         } 
/*  561 */         s.right = p.right;
/*  562 */         s.succ(false);
/*  563 */         if (q != null)
/*  564 */         { if (dir) {
/*  565 */             q.right = s;
/*      */           } else {
/*  567 */             q.left = s;
/*      */           }  }
/*  569 */         else { this.tree = s; }
/*  570 */          s.balance(p.balance());
/*  571 */         q = r;
/*  572 */         dir = false;
/*      */       }  }
/*      */ 
/*      */     
/*  576 */     while (q != null) {
/*  577 */       Entry<K> y = q;
/*  578 */       q = parent(y);
/*  579 */       if (!dir) {
/*  580 */         dir = (q != null && q.left != y);
/*  581 */         y.incBalance();
/*  582 */         if (y.balance() == 1)
/*      */           break; 
/*  584 */         if (y.balance() == 2) {
/*  585 */           Entry<K> x = y.right;
/*  586 */           assert x != null;
/*  587 */           if (x.balance() == -1) {
/*      */             
/*  589 */             assert x.balance() == -1;
/*  590 */             Entry<K> w = x.left;
/*  591 */             x.left = w.right;
/*  592 */             w.right = x;
/*  593 */             y.right = w.left;
/*  594 */             w.left = y;
/*  595 */             if (w.balance() == 1) {
/*  596 */               x.balance(0);
/*  597 */               y.balance(-1);
/*  598 */             } else if (w.balance() == 0) {
/*  599 */               x.balance(0);
/*  600 */               y.balance(0);
/*      */             } else {
/*  602 */               assert w.balance() == -1;
/*  603 */               x.balance(1);
/*  604 */               y.balance(0);
/*      */             } 
/*  606 */             w.balance(0);
/*  607 */             if (w.pred()) {
/*  608 */               y.succ(w);
/*  609 */               w.pred(false);
/*      */             } 
/*  611 */             if (w.succ()) {
/*  612 */               x.pred(w);
/*  613 */               w.succ(false);
/*      */             } 
/*  615 */             if (q != null) {
/*  616 */               if (dir) {
/*  617 */                 q.right = w; continue;
/*      */               } 
/*  619 */               q.left = w; continue;
/*      */             } 
/*  621 */             this.tree = w; continue;
/*      */           } 
/*  623 */           if (q != null)
/*  624 */           { if (dir) {
/*  625 */               q.right = x;
/*      */             } else {
/*  627 */               q.left = x;
/*      */             }  }
/*  629 */           else { this.tree = x; }
/*  630 */            if (x.balance() == 0) {
/*  631 */             y.right = x.left;
/*  632 */             x.left = y;
/*  633 */             x.balance(-1);
/*  634 */             y.balance(1);
/*      */             break;
/*      */           } 
/*  637 */           assert x.balance() == 1;
/*  638 */           if (x.pred()) {
/*  639 */             y.succ(true);
/*  640 */             x.pred(false);
/*      */           } else {
/*  642 */             y.right = x.left;
/*  643 */           }  x.left = y;
/*  644 */           y.balance(0);
/*  645 */           x.balance(0);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  649 */       dir = (q != null && q.left != y);
/*  650 */       y.decBalance();
/*  651 */       if (y.balance() == -1)
/*      */         break; 
/*  653 */       if (y.balance() == -2) {
/*  654 */         Entry<K> x = y.left;
/*  655 */         assert x != null;
/*  656 */         if (x.balance() == 1) {
/*      */           
/*  658 */           assert x.balance() == 1;
/*  659 */           Entry<K> w = x.right;
/*  660 */           x.right = w.left;
/*  661 */           w.left = x;
/*  662 */           y.left = w.right;
/*  663 */           w.right = y;
/*  664 */           if (w.balance() == -1) {
/*  665 */             x.balance(0);
/*  666 */             y.balance(1);
/*  667 */           } else if (w.balance() == 0) {
/*  668 */             x.balance(0);
/*  669 */             y.balance(0);
/*      */           } else {
/*  671 */             assert w.balance() == 1;
/*  672 */             x.balance(-1);
/*  673 */             y.balance(0);
/*      */           } 
/*  675 */           w.balance(0);
/*  676 */           if (w.pred()) {
/*  677 */             x.succ(w);
/*  678 */             w.pred(false);
/*      */           } 
/*  680 */           if (w.succ()) {
/*  681 */             y.pred(w);
/*  682 */             w.succ(false);
/*      */           } 
/*  684 */           if (q != null) {
/*  685 */             if (dir) {
/*  686 */               q.right = w; continue;
/*      */             } 
/*  688 */             q.left = w; continue;
/*      */           } 
/*  690 */           this.tree = w; continue;
/*      */         } 
/*  692 */         if (q != null)
/*  693 */         { if (dir) {
/*  694 */             q.right = x;
/*      */           } else {
/*  696 */             q.left = x;
/*      */           }  }
/*  698 */         else { this.tree = x; }
/*  699 */          if (x.balance() == 0) {
/*  700 */           y.left = x.right;
/*  701 */           x.right = y;
/*  702 */           x.balance(1);
/*  703 */           y.balance(-1);
/*      */           break;
/*      */         } 
/*  706 */         assert x.balance() == -1;
/*  707 */         if (x.succ()) {
/*  708 */           y.pred(true);
/*  709 */           x.succ(false);
/*      */         } else {
/*  711 */           y.left = x.right;
/*  712 */         }  x.right = y;
/*  713 */         y.balance(0);
/*  714 */         x.balance(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  719 */     this.modified = true;
/*  720 */     this.count--;
/*  721 */     return p.value;
/*      */   }
/*      */   
/*      */   public boolean containsValue(float v) {
/*  725 */     ValueIterator i = new ValueIterator();
/*      */     
/*  727 */     int j = this.count;
/*  728 */     while (j-- != 0) {
/*  729 */       float ev = i.nextFloat();
/*  730 */       if (Float.floatToIntBits(ev) == Float.floatToIntBits(v))
/*  731 */         return true; 
/*      */     } 
/*  733 */     return false;
/*      */   }
/*      */   
/*      */   public void clear() {
/*  737 */     this.count = 0;
/*  738 */     this.tree = null;
/*  739 */     this.entries = null;
/*  740 */     this.values = null;
/*  741 */     this.keys = null;
/*  742 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<K>
/*      */     extends AbstractObject2FloatMap.BasicEntry<K>
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
/*      */     Entry<K> left;
/*      */ 
/*      */     
/*      */     Entry<K> right;
/*      */ 
/*      */     
/*      */     int info;
/*      */ 
/*      */ 
/*      */     
/*      */     Entry() {
/*  773 */       super((K)null, 0.0F);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(K k, float v) {
/*  784 */       super(k, v);
/*  785 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> left() {
/*  793 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> right() {
/*  801 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  809 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  817 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  826 */       if (pred) {
/*  827 */         this.info |= 0x40000000;
/*      */       } else {
/*  829 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  838 */       if (succ) {
/*  839 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  841 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<K> pred) {
/*  850 */       this.info |= 0x40000000;
/*  851 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<K> succ) {
/*  860 */       this.info |= Integer.MIN_VALUE;
/*  861 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<K> left) {
/*  870 */       this.info &= 0xBFFFFFFF;
/*  871 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<K> right) {
/*  880 */       this.info &= Integer.MAX_VALUE;
/*  881 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int balance() {
/*  889 */       return (byte)this.info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void balance(int level) {
/*  898 */       this.info &= 0xFFFFFF00;
/*  899 */       this.info |= level & 0xFF;
/*      */     }
/*      */     
/*      */     void incBalance() {
/*  903 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
/*      */     }
/*      */     
/*      */     protected void decBalance() {
/*  907 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> next() {
/*  915 */       Entry<K> next = this.right;
/*  916 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  917 */         while ((next.info & 0x40000000) == 0)
/*  918 */           next = next.left;  
/*  919 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> prev() {
/*  927 */       Entry<K> prev = this.left;
/*  928 */       if ((this.info & 0x40000000) == 0)
/*  929 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  930 */           prev = prev.right;  
/*  931 */       return prev;
/*      */     }
/*      */     
/*      */     public float setValue(float value) {
/*  935 */       float oldValue = this.value;
/*  936 */       this.value = value;
/*  937 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry<K> clone() {
/*      */       Entry<K> c;
/*      */       try {
/*  944 */         c = (Entry<K>)super.clone();
/*  945 */       } catch (CloneNotSupportedException cantHappen) {
/*  946 */         throw new InternalError();
/*      */       } 
/*  948 */       c.key = this.key;
/*  949 */       c.value = this.value;
/*  950 */       c.info = this.info;
/*  951 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  956 */       if (!(o instanceof Map.Entry))
/*  957 */         return false; 
/*  958 */       Map.Entry<K, Float> e = (Map.Entry<K, Float>)o;
/*  959 */       return (Objects.equals(this.key, e.getKey()) && 
/*  960 */         Float.floatToIntBits(this.value) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  964 */       return this.key.hashCode() ^ HashCommon.float2int(this.value);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  968 */       return (new StringBuilder()).append(this.key).append("=>").append(this.value).toString();
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
/*  989 */     return (findKey((K)k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  993 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  997 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFloat(Object k) {
/* 1002 */     Entry<K> e = findKey((K)k);
/* 1003 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public K firstKey() {
/* 1007 */     if (this.tree == null)
/* 1008 */       throw new NoSuchElementException(); 
/* 1009 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public K lastKey() {
/* 1013 */     if (this.tree == null)
/* 1014 */       throw new NoSuchElementException(); 
/* 1015 */     return this.lastEntry.key;
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
/*      */     Object2FloatAVLTreeMap.Entry<K> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2FloatAVLTreeMap.Entry<K> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2FloatAVLTreeMap.Entry<K> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1046 */     int index = 0;
/*      */     TreeIterator() {
/* 1048 */       this.next = Object2FloatAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(K k) {
/* 1051 */       if ((this.next = Object2FloatAVLTreeMap.this.locateKey(k)) != null)
/* 1052 */         if (Object2FloatAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1053 */           this.prev = this.next;
/* 1054 */           this.next = this.next.next();
/*      */         } else {
/* 1056 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/* 1060 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1063 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1066 */       this.next = this.next.next();
/*      */     }
/*      */     Object2FloatAVLTreeMap.Entry<K> nextEntry() {
/* 1069 */       if (!hasNext())
/* 1070 */         throw new NoSuchElementException(); 
/* 1071 */       this.curr = this.prev = this.next;
/* 1072 */       this.index++;
/* 1073 */       updateNext();
/* 1074 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1077 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Object2FloatAVLTreeMap.Entry<K> previousEntry() {
/* 1080 */       if (!hasPrevious())
/* 1081 */         throw new NoSuchElementException(); 
/* 1082 */       this.curr = this.next = this.prev;
/* 1083 */       this.index--;
/* 1084 */       updatePrevious();
/* 1085 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1088 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1091 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1094 */       if (this.curr == null) {
/* 1095 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1100 */       if (this.curr == this.prev)
/* 1101 */         this.index--; 
/* 1102 */       this.next = this.prev = this.curr;
/* 1103 */       updatePrevious();
/* 1104 */       updateNext();
/* 1105 */       Object2FloatAVLTreeMap.this.removeFloat(this.curr.key);
/* 1106 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1109 */       int i = n;
/* 1110 */       while (i-- != 0 && hasNext())
/* 1111 */         nextEntry(); 
/* 1112 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1115 */       int i = n;
/* 1116 */       while (i-- != 0 && hasPrevious())
/* 1117 */         previousEntry(); 
/* 1118 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Object2FloatMap.Entry<K>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(K k) {
/* 1131 */       super(k);
/*      */     }
/*      */     
/*      */     public Object2FloatMap.Entry<K> next() {
/* 1135 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Object2FloatMap.Entry<K> previous() {
/* 1139 */       return previousEntry();
/*      */     }
/*      */     
/*      */     public void set(Object2FloatMap.Entry<K> ok) {
/* 1143 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Object2FloatMap.Entry<K> ok) {
/* 1147 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Object2FloatMap.Entry<K>> object2FloatEntrySet() {
/* 1152 */     if (this.entries == null)
/* 1153 */       this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2FloatMap.Entry<Object2FloatMap.Entry<K>>>()
/*      */         {
/*      */           final Comparator<? super Object2FloatMap.Entry<K>> comparator;
/*      */           
/*      */           public Comparator<? super Object2FloatMap.Entry<K>> comparator() {
/* 1158 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> iterator() {
/* 1162 */             return new Object2FloatAVLTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> iterator(Object2FloatMap.Entry<K> from) {
/* 1167 */             return new Object2FloatAVLTreeMap.EntryIterator(from.getKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1172 */             if (!(o instanceof Map.Entry))
/* 1173 */               return false; 
/* 1174 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1175 */             if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1176 */               return false; 
/* 1177 */             Object2FloatAVLTreeMap.Entry<K> f = Object2FloatAVLTreeMap.this.findKey((K)e.getKey());
/* 1178 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1183 */             if (!(o instanceof Map.Entry))
/* 1184 */               return false; 
/* 1185 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1186 */             if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1187 */               return false; 
/* 1188 */             Object2FloatAVLTreeMap.Entry<K> f = Object2FloatAVLTreeMap.this.findKey((K)e.getKey());
/* 1189 */             if (f == null || Float.floatToIntBits(f.getFloatValue()) != 
/* 1190 */               Float.floatToIntBits(((Float)e.getValue()).floatValue()))
/* 1191 */               return false; 
/* 1192 */             Object2FloatAVLTreeMap.this.removeFloat(f.key);
/* 1193 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1197 */             return Object2FloatAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1201 */             Object2FloatAVLTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Object2FloatMap.Entry<K> first() {
/* 1205 */             return Object2FloatAVLTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Object2FloatMap.Entry<K> last() {
/* 1209 */             return Object2FloatAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2FloatMap.Entry<K>> subSet(Object2FloatMap.Entry<K> from, Object2FloatMap.Entry<K> to) {
/* 1214 */             return Object2FloatAVLTreeMap.this.subMap(from.getKey(), to.getKey()).object2FloatEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2FloatMap.Entry<K>> headSet(Object2FloatMap.Entry<K> to) {
/* 1218 */             return Object2FloatAVLTreeMap.this.headMap(to.getKey()).object2FloatEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2FloatMap.Entry<K>> tailSet(Object2FloatMap.Entry<K> from) {
/* 1222 */             return Object2FloatAVLTreeMap.this.tailMap(from.getKey()).object2FloatEntrySet();
/*      */           }
/*      */         }; 
/* 1225 */     return this.entries;
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
/* 1241 */       super(k);
/*      */     }
/*      */     
/*      */     public K next() {
/* 1245 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1249 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractObject2FloatSortedMap<K>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1256 */       return new Object2FloatAVLTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1260 */       return new Object2FloatAVLTreeMap.KeyIterator(from);
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
/* 1275 */     if (this.keys == null)
/* 1276 */       this.keys = new KeySet(); 
/* 1277 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements FloatListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1292 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public float previousFloat() {
/* 1296 */       return (previousEntry()).value;
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
/*      */   public FloatCollection values() {
/* 1311 */     if (this.values == null)
/* 1312 */       this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1315 */             return (FloatIterator)new Object2FloatAVLTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(float k) {
/* 1319 */             return Object2FloatAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1323 */             return Object2FloatAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1327 */             Object2FloatAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1330 */     return this.values;
/*      */   }
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1334 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Object2FloatSortedMap<K> headMap(K to) {
/* 1338 */     return new Submap(null, true, to, false);
/*      */   }
/*      */   
/*      */   public Object2FloatSortedMap<K> tailMap(K from) {
/* 1342 */     return new Submap(from, false, null, true);
/*      */   }
/*      */   
/*      */   public Object2FloatSortedMap<K> subMap(K from, K to) {
/* 1346 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractObject2FloatSortedMap<K>
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
/*      */     protected transient ObjectSortedSet<Object2FloatMap.Entry<K>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient FloatCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(K from, boolean bottom, K to, boolean top) {
/* 1390 */       if (!bottom && !top && Object2FloatAVLTreeMap.this.compare(from, to) > 0)
/* 1391 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1392 */       this.from = from;
/* 1393 */       this.bottom = bottom;
/* 1394 */       this.to = to;
/* 1395 */       this.top = top;
/* 1396 */       this.defRetValue = Object2FloatAVLTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1400 */       SubmapIterator i = new SubmapIterator();
/* 1401 */       while (i.hasNext()) {
/* 1402 */         i.nextEntry();
/* 1403 */         i.remove();
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
/* 1414 */       return ((this.bottom || Object2FloatAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Object2FloatAVLTreeMap.this
/* 1415 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2FloatMap.Entry<K>> object2FloatEntrySet() {
/* 1419 */       if (this.entries == null)
/* 1420 */         this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2FloatMap.Entry<Object2FloatMap.Entry<K>>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> iterator() {
/* 1423 */               return new Object2FloatAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> iterator(Object2FloatMap.Entry<K> from) {
/* 1428 */               return new Object2FloatAVLTreeMap.Submap.SubmapEntryIterator(from.getKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Object2FloatMap.Entry<K>> comparator() {
/* 1432 */               return Object2FloatAVLTreeMap.this.object2FloatEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1437 */               if (!(o instanceof Map.Entry))
/* 1438 */                 return false; 
/* 1439 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1440 */               if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1441 */                 return false; 
/* 1442 */               Object2FloatAVLTreeMap.Entry<K> f = Object2FloatAVLTreeMap.this.findKey((K)e.getKey());
/* 1443 */               return (f != null && Object2FloatAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1448 */               if (!(o instanceof Map.Entry))
/* 1449 */                 return false; 
/* 1450 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1451 */               if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1452 */                 return false; 
/* 1453 */               Object2FloatAVLTreeMap.Entry<K> f = Object2FloatAVLTreeMap.this.findKey((K)e.getKey());
/* 1454 */               if (f != null && Object2FloatAVLTreeMap.Submap.this.in(f.key))
/* 1455 */                 Object2FloatAVLTreeMap.Submap.this.removeFloat(f.key); 
/* 1456 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1460 */               int c = 0;
/* 1461 */               for (Iterator<?> i = iterator(); i.hasNext(); i.next())
/* 1462 */                 c++; 
/* 1463 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1467 */               return !(new Object2FloatAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1471 */               Object2FloatAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Object2FloatMap.Entry<K> first() {
/* 1475 */               return Object2FloatAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Object2FloatMap.Entry<K> last() {
/* 1479 */               return Object2FloatAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2FloatMap.Entry<K>> subSet(Object2FloatMap.Entry<K> from, Object2FloatMap.Entry<K> to) {
/* 1484 */               return Object2FloatAVLTreeMap.Submap.this.subMap(from.getKey(), to.getKey()).object2FloatEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2FloatMap.Entry<K>> headSet(Object2FloatMap.Entry<K> to) {
/* 1488 */               return Object2FloatAVLTreeMap.Submap.this.headMap(to.getKey()).object2FloatEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2FloatMap.Entry<K>> tailSet(Object2FloatMap.Entry<K> from) {
/* 1492 */               return Object2FloatAVLTreeMap.Submap.this.tailMap(from.getKey()).object2FloatEntrySet();
/*      */             }
/*      */           }; 
/* 1495 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractObject2FloatSortedMap<K>.KeySet {
/*      */       public ObjectBidirectionalIterator<K> iterator() {
/* 1500 */         return new Object2FloatAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1504 */         return new Object2FloatAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> keySet() {
/* 1509 */       if (this.keys == null)
/* 1510 */         this.keys = new KeySet(); 
/* 1511 */       return this.keys;
/*      */     }
/*      */     
/*      */     public FloatCollection values() {
/* 1515 */       if (this.values == null)
/* 1516 */         this.values = (FloatCollection)new AbstractFloatCollection()
/*      */           {
/*      */             public FloatIterator iterator() {
/* 1519 */               return (FloatIterator)new Object2FloatAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(float k) {
/* 1523 */               return Object2FloatAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1527 */               return Object2FloatAVLTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1531 */               Object2FloatAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1534 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object k) {
/* 1539 */       return (in((K)k) && Object2FloatAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(float v) {
/* 1543 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1545 */       while (i.hasNext()) {
/* 1546 */         float ev = (i.nextEntry()).value;
/* 1547 */         if (Float.floatToIntBits(ev) == Float.floatToIntBits(v))
/* 1548 */           return true; 
/*      */       } 
/* 1550 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public float getFloat(Object k) {
/* 1556 */       K kk = (K)k; Object2FloatAVLTreeMap.Entry<K> e;
/* 1557 */       return (in(kk) && (e = Object2FloatAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public float put(K k, float v) {
/* 1561 */       Object2FloatAVLTreeMap.this.modified = false;
/* 1562 */       if (!in(k))
/* 1563 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1564 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1565 */       float oldValue = Object2FloatAVLTreeMap.this.put(k, v);
/* 1566 */       return Object2FloatAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public float removeFloat(Object k) {
/* 1571 */       Object2FloatAVLTreeMap.this.modified = false;
/* 1572 */       if (!in((K)k))
/* 1573 */         return this.defRetValue; 
/* 1574 */       float oldValue = Object2FloatAVLTreeMap.this.removeFloat(k);
/* 1575 */       return Object2FloatAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1579 */       SubmapIterator i = new SubmapIterator();
/* 1580 */       int n = 0;
/* 1581 */       while (i.hasNext()) {
/* 1582 */         n++;
/* 1583 */         i.nextEntry();
/*      */       } 
/* 1585 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1589 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1593 */       return Object2FloatAVLTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Object2FloatSortedMap<K> headMap(K to) {
/* 1597 */       if (this.top)
/* 1598 */         return new Submap(this.from, this.bottom, to, false); 
/* 1599 */       return (Object2FloatAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Object2FloatSortedMap<K> tailMap(K from) {
/* 1603 */       if (this.bottom)
/* 1604 */         return new Submap(from, false, this.to, this.top); 
/* 1605 */       return (Object2FloatAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Object2FloatSortedMap<K> subMap(K from, K to) {
/* 1609 */       if (this.top && this.bottom)
/* 1610 */         return new Submap(from, false, to, false); 
/* 1611 */       if (!this.top)
/* 1612 */         to = (Object2FloatAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1613 */       if (!this.bottom)
/* 1614 */         from = (Object2FloatAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1615 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1616 */         return this; 
/* 1617 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2FloatAVLTreeMap.Entry<K> firstEntry() {
/*      */       Object2FloatAVLTreeMap.Entry<K> e;
/* 1626 */       if (Object2FloatAVLTreeMap.this.tree == null) {
/* 1627 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1631 */       if (this.bottom) {
/* 1632 */         e = Object2FloatAVLTreeMap.this.firstEntry;
/*      */       } else {
/* 1634 */         e = Object2FloatAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1636 */         if (Object2FloatAVLTreeMap.this.compare(e.key, this.from) < 0) {
/* 1637 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1641 */       if (e == null || (!this.top && Object2FloatAVLTreeMap.this.compare(e.key, this.to) >= 0))
/* 1642 */         return null; 
/* 1643 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2FloatAVLTreeMap.Entry<K> lastEntry() {
/*      */       Object2FloatAVLTreeMap.Entry<K> e;
/* 1652 */       if (Object2FloatAVLTreeMap.this.tree == null) {
/* 1653 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1657 */       if (this.top) {
/* 1658 */         e = Object2FloatAVLTreeMap.this.lastEntry;
/*      */       } else {
/* 1660 */         e = Object2FloatAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1662 */         if (Object2FloatAVLTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1663 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1667 */       if (e == null || (!this.bottom && Object2FloatAVLTreeMap.this.compare(e.key, this.from) < 0))
/* 1668 */         return null; 
/* 1669 */       return e;
/*      */     }
/*      */     
/*      */     public K firstKey() {
/* 1673 */       Object2FloatAVLTreeMap.Entry<K> e = firstEntry();
/* 1674 */       if (e == null)
/* 1675 */         throw new NoSuchElementException(); 
/* 1676 */       return e.key;
/*      */     }
/*      */     
/*      */     public K lastKey() {
/* 1680 */       Object2FloatAVLTreeMap.Entry<K> e = lastEntry();
/* 1681 */       if (e == null)
/* 1682 */         throw new NoSuchElementException(); 
/* 1683 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Object2FloatAVLTreeMap<K>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1696 */         this.next = Object2FloatAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(K k) {
/* 1699 */         this();
/* 1700 */         if (this.next != null)
/* 1701 */           if (!Object2FloatAVLTreeMap.Submap.this.bottom && Object2FloatAVLTreeMap.this.compare(k, this.next.key) < 0) {
/* 1702 */             this.prev = null;
/* 1703 */           } else if (!Object2FloatAVLTreeMap.Submap.this.top && Object2FloatAVLTreeMap.this.compare(k, (this.prev = Object2FloatAVLTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1704 */             this.next = null;
/*      */           } else {
/* 1706 */             this.next = Object2FloatAVLTreeMap.this.locateKey(k);
/* 1707 */             if (Object2FloatAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1708 */               this.prev = this.next;
/* 1709 */               this.next = this.next.next();
/*      */             } else {
/* 1711 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1717 */         this.prev = this.prev.prev();
/* 1718 */         if (!Object2FloatAVLTreeMap.Submap.this.bottom && this.prev != null && Object2FloatAVLTreeMap.this.compare(this.prev.key, Object2FloatAVLTreeMap.Submap.this.from) < 0)
/* 1719 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1723 */         this.next = this.next.next();
/* 1724 */         if (!Object2FloatAVLTreeMap.Submap.this.top && this.next != null && Object2FloatAVLTreeMap.this.compare(this.next.key, Object2FloatAVLTreeMap.Submap.this.to) >= 0)
/* 1725 */           this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Object2FloatMap.Entry<K>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(K k) {
/* 1734 */         super(k);
/*      */       }
/*      */       
/*      */       public Object2FloatMap.Entry<K> next() {
/* 1738 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Object2FloatMap.Entry<K> previous() {
/* 1742 */         return previousEntry();
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
/* 1760 */         super(from);
/*      */       }
/*      */       
/*      */       public K next() {
/* 1764 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public K previous() {
/* 1768 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements FloatListIterator
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public float nextFloat() {
/* 1784 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public float previousFloat() {
/* 1788 */         return (previousEntry()).value;
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
/*      */   public Object2FloatAVLTreeMap<K> clone() {
/*      */     Object2FloatAVLTreeMap<K> c;
/*      */     try {
/* 1807 */       c = (Object2FloatAVLTreeMap<K>)super.clone();
/* 1808 */     } catch (CloneNotSupportedException cantHappen) {
/* 1809 */       throw new InternalError();
/*      */     } 
/* 1811 */     c.keys = null;
/* 1812 */     c.values = null;
/* 1813 */     c.entries = null;
/* 1814 */     c.allocatePaths();
/* 1815 */     if (this.count != 0) {
/*      */       
/* 1817 */       Entry<K> rp = new Entry<>(), rq = new Entry<>();
/* 1818 */       Entry<K> p = rp;
/* 1819 */       rp.left(this.tree);
/* 1820 */       Entry<K> q = rq;
/* 1821 */       rq.pred((Entry<K>)null);
/*      */       while (true) {
/* 1823 */         if (!p.pred()) {
/* 1824 */           Entry<K> e = p.left.clone();
/* 1825 */           e.pred(q.left);
/* 1826 */           e.succ(q);
/* 1827 */           q.left(e);
/* 1828 */           p = p.left;
/* 1829 */           q = q.left;
/*      */         } else {
/* 1831 */           while (p.succ()) {
/* 1832 */             p = p.right;
/* 1833 */             if (p == null) {
/* 1834 */               q.right = null;
/* 1835 */               c.tree = rq.left;
/* 1836 */               c.firstEntry = c.tree;
/* 1837 */               while (c.firstEntry.left != null)
/* 1838 */                 c.firstEntry = c.firstEntry.left; 
/* 1839 */               c.lastEntry = c.tree;
/* 1840 */               while (c.lastEntry.right != null)
/* 1841 */                 c.lastEntry = c.lastEntry.right; 
/* 1842 */               return c;
/*      */             } 
/* 1844 */             q = q.right;
/*      */           } 
/* 1846 */           p = p.right;
/* 1847 */           q = q.right;
/*      */         } 
/* 1849 */         if (!p.succ()) {
/* 1850 */           Entry<K> e = p.right.clone();
/* 1851 */           e.succ(q.right);
/* 1852 */           e.pred(q);
/* 1853 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1857 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1860 */     int n = this.count;
/* 1861 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1863 */     s.defaultWriteObject();
/* 1864 */     while (n-- != 0) {
/* 1865 */       Entry<K> e = i.nextEntry();
/* 1866 */       s.writeObject(e.key);
/* 1867 */       s.writeFloat(e.value);
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
/*      */   private Entry<K> readTree(ObjectInputStream s, int n, Entry<K> pred, Entry<K> succ) throws IOException, ClassNotFoundException {
/* 1888 */     if (n == 1) {
/* 1889 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readFloat());
/* 1890 */       entry.pred(pred);
/* 1891 */       entry.succ(succ);
/* 1892 */       return entry;
/*      */     } 
/* 1894 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1899 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readFloat());
/* 1900 */       entry.right(new Entry<>((K)s.readObject(), s.readFloat()));
/* 1901 */       entry.right.pred(entry);
/* 1902 */       entry.balance(1);
/* 1903 */       entry.pred(pred);
/* 1904 */       entry.right.succ(succ);
/* 1905 */       return entry;
/*      */     } 
/*      */     
/* 1908 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1909 */     Entry<K> top = new Entry<>();
/* 1910 */     top.left(readTree(s, leftN, pred, top));
/* 1911 */     top.key = (K)s.readObject();
/* 1912 */     top.value = s.readFloat();
/* 1913 */     top.right(readTree(s, rightN, top, succ));
/* 1914 */     if (n == (n & -n))
/* 1915 */       top.balance(1); 
/* 1916 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1919 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1924 */     setActualComparator();
/* 1925 */     allocatePaths();
/* 1926 */     if (this.count != 0) {
/* 1927 */       this.tree = readTree(s, this.count, (Entry<K>)null, (Entry<K>)null);
/*      */       
/* 1929 */       Entry<K> e = this.tree;
/* 1930 */       while (e.left() != null)
/* 1931 */         e = e.left(); 
/* 1932 */       this.firstEntry = e;
/* 1933 */       e = this.tree;
/* 1934 */       while (e.right() != null)
/* 1935 */         e = e.right(); 
/* 1936 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2FloatAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */