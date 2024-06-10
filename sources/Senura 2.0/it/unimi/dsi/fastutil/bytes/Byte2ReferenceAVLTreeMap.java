/*      */ package it.unimi.dsi.fastutil.bytes;
/*      */ 
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
/*      */ 
/*      */ public class Byte2ReferenceAVLTreeMap<V>
/*      */   extends AbstractByte2ReferenceSortedMap<V>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<V> tree;
/*      */   protected int count;
/*      */   protected transient Entry<V> firstEntry;
/*      */   protected transient Entry<V> lastEntry;
/*      */   protected transient ObjectSortedSet<Byte2ReferenceMap.Entry<V>> entries;
/*      */   protected transient ByteSortedSet keys;
/*      */   protected transient ReferenceCollection<V> values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Byte> storedComparator;
/*      */   protected transient ByteComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Byte2ReferenceAVLTreeMap() {
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
/*   91 */     this.actualComparator = ByteComparators.asByteComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ReferenceAVLTreeMap(Comparator<? super Byte> c) {
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
/*      */   public Byte2ReferenceAVLTreeMap(Map<? extends Byte, ? extends V> m) {
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
/*      */   public Byte2ReferenceAVLTreeMap(SortedMap<Byte, V> m) {
/*  122 */     this(m.comparator());
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ReferenceAVLTreeMap(Byte2ReferenceMap<? extends V> m) {
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
/*      */   public Byte2ReferenceAVLTreeMap(Byte2ReferenceSortedMap<V> m) {
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
/*      */   public Byte2ReferenceAVLTreeMap(byte[] k, V[] v, Comparator<? super Byte> c) {
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
/*      */   public Byte2ReferenceAVLTreeMap(byte[] k, V[] v) {
/*  178 */     this(k, v, (Comparator<? super Byte>)null);
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
/*      */   final int compare(byte k1, byte k2) {
/*  206 */     return (this.actualComparator == null) ? Byte.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry<V> findKey(byte k) {
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
/*      */   final Entry<V> locateKey(byte k) {
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
/*      */   public V put(byte k, V v) {
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
/*      */   private Entry<V> add(byte k) {
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
/*      */   public V remove(byte k) {
/*  461 */     this.modified = false;
/*  462 */     if (this.tree == null) {
/*  463 */       return this.defRetValue;
/*      */     }
/*  465 */     Entry<V> p = this.tree, q = null;
/*  466 */     boolean dir = false;
/*  467 */     byte kk = k;
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
/*      */     extends AbstractByte2ReferenceMap.BasicEntry<V>
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
/*  750 */       super((byte)0, (V)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(byte k, V v) {
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
/*  935 */       Map.Entry<Byte, V> e = (Map.Entry<Byte, V>)o;
/*  936 */       return (this.key == ((Byte)e.getKey()).byteValue() && this.value == e.getValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  940 */       return this.key ^ ((this.value == null) ? 0 : System.identityHashCode(this.value));
/*      */     }
/*      */     
/*      */     public String toString() {
/*  944 */       return this.key + "=>" + this.value;
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
/*      */   public boolean containsKey(byte k) {
/*  965 */     return (findKey(k) != null);
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
/*      */   public V get(byte k) {
/*  978 */     Entry<V> e = findKey(k);
/*  979 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public byte firstByteKey() {
/*  983 */     if (this.tree == null)
/*  984 */       throw new NoSuchElementException(); 
/*  985 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public byte lastByteKey() {
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
/*      */     Byte2ReferenceAVLTreeMap.Entry<V> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Byte2ReferenceAVLTreeMap.Entry<V> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Byte2ReferenceAVLTreeMap.Entry<V> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1022 */     int index = 0;
/*      */     TreeIterator() {
/* 1024 */       this.next = Byte2ReferenceAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(byte k) {
/* 1027 */       if ((this.next = Byte2ReferenceAVLTreeMap.this.locateKey(k)) != null)
/* 1028 */         if (Byte2ReferenceAVLTreeMap.this.compare(this.next.key, k) <= 0) {
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
/*      */     Byte2ReferenceAVLTreeMap.Entry<V> nextEntry() {
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
/*      */     Byte2ReferenceAVLTreeMap.Entry<V> previousEntry() {
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
/* 1081 */       Byte2ReferenceAVLTreeMap.this.remove(this.curr.key);
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
/*      */     implements ObjectListIterator<Byte2ReferenceMap.Entry<V>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(byte k) {
/* 1107 */       super(k);
/*      */     }
/*      */     
/*      */     public Byte2ReferenceMap.Entry<V> next() {
/* 1111 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Byte2ReferenceMap.Entry<V> previous() {
/* 1115 */       return previousEntry();
/*      */     }
/*      */     
/*      */     public void set(Byte2ReferenceMap.Entry<V> ok) {
/* 1119 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Byte2ReferenceMap.Entry<V> ok) {
/* 1123 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Byte2ReferenceMap.Entry<V>> byte2ReferenceEntrySet() {
/* 1128 */     if (this.entries == null)
/* 1129 */       this.entries = (ObjectSortedSet<Byte2ReferenceMap.Entry<V>>)new AbstractObjectSortedSet<Byte2ReferenceMap.Entry<V>>()
/*      */         {
/*      */           final Comparator<? super Byte2ReferenceMap.Entry<V>> comparator;
/*      */           
/*      */           public Comparator<? super Byte2ReferenceMap.Entry<V>> comparator() {
/* 1134 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>> iterator() {
/* 1138 */             return (ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>>)new Byte2ReferenceAVLTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>> iterator(Byte2ReferenceMap.Entry<V> from) {
/* 1143 */             return (ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>>)new Byte2ReferenceAVLTreeMap.EntryIterator(from.getByteKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1148 */             if (!(o instanceof Map.Entry))
/* 1149 */               return false; 
/* 1150 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1151 */             if (e.getKey() == null || !(e.getKey() instanceof Byte))
/* 1152 */               return false; 
/* 1153 */             Byte2ReferenceAVLTreeMap.Entry<V> f = Byte2ReferenceAVLTreeMap.this.findKey(((Byte)e.getKey()).byteValue());
/* 1154 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1159 */             if (!(o instanceof Map.Entry))
/* 1160 */               return false; 
/* 1161 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1162 */             if (e.getKey() == null || !(e.getKey() instanceof Byte))
/* 1163 */               return false; 
/* 1164 */             Byte2ReferenceAVLTreeMap.Entry<V> f = Byte2ReferenceAVLTreeMap.this.findKey(((Byte)e.getKey()).byteValue());
/* 1165 */             if (f == null || f.getValue() != e.getValue())
/* 1166 */               return false; 
/* 1167 */             Byte2ReferenceAVLTreeMap.this.remove(f.key);
/* 1168 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1172 */             return Byte2ReferenceAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1176 */             Byte2ReferenceAVLTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Byte2ReferenceMap.Entry<V> first() {
/* 1180 */             return Byte2ReferenceAVLTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Byte2ReferenceMap.Entry<V> last() {
/* 1184 */             return Byte2ReferenceAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Byte2ReferenceMap.Entry<V>> subSet(Byte2ReferenceMap.Entry<V> from, Byte2ReferenceMap.Entry<V> to) {
/* 1189 */             return Byte2ReferenceAVLTreeMap.this.subMap(from.getByteKey(), to.getByteKey()).byte2ReferenceEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Byte2ReferenceMap.Entry<V>> headSet(Byte2ReferenceMap.Entry<V> to) {
/* 1193 */             return Byte2ReferenceAVLTreeMap.this.headMap(to.getByteKey()).byte2ReferenceEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Byte2ReferenceMap.Entry<V>> tailSet(Byte2ReferenceMap.Entry<V> from) {
/* 1197 */             return Byte2ReferenceAVLTreeMap.this.tailMap(from.getByteKey()).byte2ReferenceEntrySet();
/*      */           }
/*      */         }; 
/* 1200 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends TreeIterator
/*      */     implements ByteListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(byte k) {
/* 1216 */       super(k);
/*      */     }
/*      */     
/*      */     public byte nextByte() {
/* 1220 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public byte previousByte() {
/* 1224 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractByte2ReferenceSortedMap<V>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ByteBidirectionalIterator iterator() {
/* 1231 */       return new Byte2ReferenceAVLTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public ByteBidirectionalIterator iterator(byte from) {
/* 1235 */       return new Byte2ReferenceAVLTreeMap.KeyIterator(from);
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
/*      */   public ByteSortedSet keySet() {
/* 1250 */     if (this.keys == null)
/* 1251 */       this.keys = new KeySet(); 
/* 1252 */     return this.keys;
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
/* 1267 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public V previous() {
/* 1271 */       return (previousEntry()).value;
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
/* 1286 */     if (this.values == null)
/* 1287 */       this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1290 */             return (ObjectIterator<V>)new Byte2ReferenceAVLTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(Object k) {
/* 1294 */             return Byte2ReferenceAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1298 */             return Byte2ReferenceAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1302 */             Byte2ReferenceAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1305 */     return this.values;
/*      */   }
/*      */   
/*      */   public ByteComparator comparator() {
/* 1309 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Byte2ReferenceSortedMap<V> headMap(byte to) {
/* 1313 */     return new Submap((byte)0, true, to, false);
/*      */   }
/*      */   
/*      */   public Byte2ReferenceSortedMap<V> tailMap(byte from) {
/* 1317 */     return new Submap(from, false, (byte)0, true);
/*      */   }
/*      */   
/*      */   public Byte2ReferenceSortedMap<V> subMap(byte from, byte to) {
/* 1321 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractByte2ReferenceSortedMap<V>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     byte from;
/*      */ 
/*      */ 
/*      */     
/*      */     byte to;
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
/*      */     protected transient ObjectSortedSet<Byte2ReferenceMap.Entry<V>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ByteSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ReferenceCollection<V> values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(byte from, boolean bottom, byte to, boolean top) {
/* 1365 */       if (!bottom && !top && Byte2ReferenceAVLTreeMap.this.compare(from, to) > 0)
/* 1366 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1367 */       this.from = from;
/* 1368 */       this.bottom = bottom;
/* 1369 */       this.to = to;
/* 1370 */       this.top = top;
/* 1371 */       this.defRetValue = Byte2ReferenceAVLTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1375 */       SubmapIterator i = new SubmapIterator();
/* 1376 */       while (i.hasNext()) {
/* 1377 */         i.nextEntry();
/* 1378 */         i.remove();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean in(byte k) {
/* 1389 */       return ((this.bottom || Byte2ReferenceAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Byte2ReferenceAVLTreeMap.this
/* 1390 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Byte2ReferenceMap.Entry<V>> byte2ReferenceEntrySet() {
/* 1394 */       if (this.entries == null)
/* 1395 */         this.entries = (ObjectSortedSet<Byte2ReferenceMap.Entry<V>>)new AbstractObjectSortedSet<Byte2ReferenceMap.Entry<V>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>> iterator() {
/* 1398 */               return (ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>>)new Byte2ReferenceAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>> iterator(Byte2ReferenceMap.Entry<V> from) {
/* 1403 */               return (ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>>)new Byte2ReferenceAVLTreeMap.Submap.SubmapEntryIterator(from.getByteKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Byte2ReferenceMap.Entry<V>> comparator() {
/* 1407 */               return Byte2ReferenceAVLTreeMap.this.byte2ReferenceEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1412 */               if (!(o instanceof Map.Entry))
/* 1413 */                 return false; 
/* 1414 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1415 */               if (e.getKey() == null || !(e.getKey() instanceof Byte))
/* 1416 */                 return false; 
/* 1417 */               Byte2ReferenceAVLTreeMap.Entry<V> f = Byte2ReferenceAVLTreeMap.this.findKey(((Byte)e.getKey()).byteValue());
/* 1418 */               return (f != null && Byte2ReferenceAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1423 */               if (!(o instanceof Map.Entry))
/* 1424 */                 return false; 
/* 1425 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1426 */               if (e.getKey() == null || !(e.getKey() instanceof Byte))
/* 1427 */                 return false; 
/* 1428 */               Byte2ReferenceAVLTreeMap.Entry<V> f = Byte2ReferenceAVLTreeMap.this.findKey(((Byte)e.getKey()).byteValue());
/* 1429 */               if (f != null && Byte2ReferenceAVLTreeMap.Submap.this.in(f.key))
/* 1430 */                 Byte2ReferenceAVLTreeMap.Submap.this.remove(f.key); 
/* 1431 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1435 */               int c = 0;
/* 1436 */               for (ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1437 */                 c++; 
/* 1438 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1442 */               return !(new Byte2ReferenceAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1446 */               Byte2ReferenceAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Byte2ReferenceMap.Entry<V> first() {
/* 1450 */               return Byte2ReferenceAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Byte2ReferenceMap.Entry<V> last() {
/* 1454 */               return Byte2ReferenceAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Byte2ReferenceMap.Entry<V>> subSet(Byte2ReferenceMap.Entry<V> from, Byte2ReferenceMap.Entry<V> to) {
/* 1459 */               return Byte2ReferenceAVLTreeMap.Submap.this.subMap(from.getByteKey(), to.getByteKey()).byte2ReferenceEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Byte2ReferenceMap.Entry<V>> headSet(Byte2ReferenceMap.Entry<V> to) {
/* 1463 */               return Byte2ReferenceAVLTreeMap.Submap.this.headMap(to.getByteKey()).byte2ReferenceEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Byte2ReferenceMap.Entry<V>> tailSet(Byte2ReferenceMap.Entry<V> from) {
/* 1467 */               return Byte2ReferenceAVLTreeMap.Submap.this.tailMap(from.getByteKey()).byte2ReferenceEntrySet();
/*      */             }
/*      */           }; 
/* 1470 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractByte2ReferenceSortedMap<V>.KeySet {
/*      */       public ByteBidirectionalIterator iterator() {
/* 1475 */         return new Byte2ReferenceAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public ByteBidirectionalIterator iterator(byte from) {
/* 1479 */         return new Byte2ReferenceAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public ByteSortedSet keySet() {
/* 1484 */       if (this.keys == null)
/* 1485 */         this.keys = new KeySet(); 
/* 1486 */       return this.keys;
/*      */     }
/*      */     
/*      */     public ReferenceCollection<V> values() {
/* 1490 */       if (this.values == null)
/* 1491 */         this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */           {
/*      */             public ObjectIterator<V> iterator() {
/* 1494 */               return (ObjectIterator<V>)new Byte2ReferenceAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(Object k) {
/* 1498 */               return Byte2ReferenceAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1502 */               return Byte2ReferenceAVLTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1506 */               Byte2ReferenceAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1509 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(byte k) {
/* 1514 */       return (in(k) && Byte2ReferenceAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(Object v) {
/* 1518 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1520 */       while (i.hasNext()) {
/* 1521 */         Object ev = (i.nextEntry()).value;
/* 1522 */         if (ev == v)
/* 1523 */           return true; 
/*      */       } 
/* 1525 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(byte k) {
/* 1531 */       byte kk = k; Byte2ReferenceAVLTreeMap.Entry<V> e;
/* 1532 */       return (in(kk) && (e = Byte2ReferenceAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public V put(byte k, V v) {
/* 1536 */       Byte2ReferenceAVLTreeMap.this.modified = false;
/* 1537 */       if (!in(k))
/* 1538 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1539 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1540 */       V oldValue = Byte2ReferenceAVLTreeMap.this.put(k, v);
/* 1541 */       return Byte2ReferenceAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public V remove(byte k) {
/* 1546 */       Byte2ReferenceAVLTreeMap.this.modified = false;
/* 1547 */       if (!in(k))
/* 1548 */         return this.defRetValue; 
/* 1549 */       V oldValue = Byte2ReferenceAVLTreeMap.this.remove(k);
/* 1550 */       return Byte2ReferenceAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1554 */       SubmapIterator i = new SubmapIterator();
/* 1555 */       int n = 0;
/* 1556 */       while (i.hasNext()) {
/* 1557 */         n++;
/* 1558 */         i.nextEntry();
/*      */       } 
/* 1560 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1564 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public ByteComparator comparator() {
/* 1568 */       return Byte2ReferenceAVLTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Byte2ReferenceSortedMap<V> headMap(byte to) {
/* 1572 */       if (this.top)
/* 1573 */         return new Submap(this.from, this.bottom, to, false); 
/* 1574 */       return (Byte2ReferenceAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Byte2ReferenceSortedMap<V> tailMap(byte from) {
/* 1578 */       if (this.bottom)
/* 1579 */         return new Submap(from, false, this.to, this.top); 
/* 1580 */       return (Byte2ReferenceAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Byte2ReferenceSortedMap<V> subMap(byte from, byte to) {
/* 1584 */       if (this.top && this.bottom)
/* 1585 */         return new Submap(from, false, to, false); 
/* 1586 */       if (!this.top)
/* 1587 */         to = (Byte2ReferenceAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1588 */       if (!this.bottom)
/* 1589 */         from = (Byte2ReferenceAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1590 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1591 */         return this; 
/* 1592 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Byte2ReferenceAVLTreeMap.Entry<V> firstEntry() {
/*      */       Byte2ReferenceAVLTreeMap.Entry<V> e;
/* 1601 */       if (Byte2ReferenceAVLTreeMap.this.tree == null) {
/* 1602 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1606 */       if (this.bottom) {
/* 1607 */         e = Byte2ReferenceAVLTreeMap.this.firstEntry;
/*      */       } else {
/* 1609 */         e = Byte2ReferenceAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1611 */         if (Byte2ReferenceAVLTreeMap.this.compare(e.key, this.from) < 0) {
/* 1612 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1616 */       if (e == null || (!this.top && Byte2ReferenceAVLTreeMap.this.compare(e.key, this.to) >= 0))
/* 1617 */         return null; 
/* 1618 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Byte2ReferenceAVLTreeMap.Entry<V> lastEntry() {
/*      */       Byte2ReferenceAVLTreeMap.Entry<V> e;
/* 1627 */       if (Byte2ReferenceAVLTreeMap.this.tree == null) {
/* 1628 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1632 */       if (this.top) {
/* 1633 */         e = Byte2ReferenceAVLTreeMap.this.lastEntry;
/*      */       } else {
/* 1635 */         e = Byte2ReferenceAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1637 */         if (Byte2ReferenceAVLTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1638 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1642 */       if (e == null || (!this.bottom && Byte2ReferenceAVLTreeMap.this.compare(e.key, this.from) < 0))
/* 1643 */         return null; 
/* 1644 */       return e;
/*      */     }
/*      */     
/*      */     public byte firstByteKey() {
/* 1648 */       Byte2ReferenceAVLTreeMap.Entry<V> e = firstEntry();
/* 1649 */       if (e == null)
/* 1650 */         throw new NoSuchElementException(); 
/* 1651 */       return e.key;
/*      */     }
/*      */     
/*      */     public byte lastByteKey() {
/* 1655 */       Byte2ReferenceAVLTreeMap.Entry<V> e = lastEntry();
/* 1656 */       if (e == null)
/* 1657 */         throw new NoSuchElementException(); 
/* 1658 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Byte2ReferenceAVLTreeMap<V>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1671 */         this.next = Byte2ReferenceAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(byte k) {
/* 1674 */         this();
/* 1675 */         if (this.next != null)
/* 1676 */           if (!Byte2ReferenceAVLTreeMap.Submap.this.bottom && Byte2ReferenceAVLTreeMap.this.compare(k, this.next.key) < 0) {
/* 1677 */             this.prev = null;
/* 1678 */           } else if (!Byte2ReferenceAVLTreeMap.Submap.this.top && Byte2ReferenceAVLTreeMap.this.compare(k, (this.prev = Byte2ReferenceAVLTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1679 */             this.next = null;
/*      */           } else {
/* 1681 */             this.next = Byte2ReferenceAVLTreeMap.this.locateKey(k);
/* 1682 */             if (Byte2ReferenceAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1683 */               this.prev = this.next;
/* 1684 */               this.next = this.next.next();
/*      */             } else {
/* 1686 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1692 */         this.prev = this.prev.prev();
/* 1693 */         if (!Byte2ReferenceAVLTreeMap.Submap.this.bottom && this.prev != null && Byte2ReferenceAVLTreeMap.this.compare(this.prev.key, Byte2ReferenceAVLTreeMap.Submap.this.from) < 0)
/* 1694 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1698 */         this.next = this.next.next();
/* 1699 */         if (!Byte2ReferenceAVLTreeMap.Submap.this.top && this.next != null && Byte2ReferenceAVLTreeMap.this.compare(this.next.key, Byte2ReferenceAVLTreeMap.Submap.this.to) >= 0)
/* 1700 */           this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Byte2ReferenceMap.Entry<V>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(byte k) {
/* 1709 */         super(k);
/*      */       }
/*      */       
/*      */       public Byte2ReferenceMap.Entry<V> next() {
/* 1713 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Byte2ReferenceMap.Entry<V> previous() {
/* 1717 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements ByteListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(byte from) {
/* 1735 */         super(from);
/*      */       }
/*      */       
/*      */       public byte nextByte() {
/* 1739 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public byte previousByte() {
/* 1743 */         return (previousEntry()).key;
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
/* 1759 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public V previous() {
/* 1763 */         return (previousEntry()).value;
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
/*      */   public Byte2ReferenceAVLTreeMap<V> clone() {
/*      */     Byte2ReferenceAVLTreeMap<V> c;
/*      */     try {
/* 1782 */       c = (Byte2ReferenceAVLTreeMap<V>)super.clone();
/* 1783 */     } catch (CloneNotSupportedException cantHappen) {
/* 1784 */       throw new InternalError();
/*      */     } 
/* 1786 */     c.keys = null;
/* 1787 */     c.values = null;
/* 1788 */     c.entries = null;
/* 1789 */     c.allocatePaths();
/* 1790 */     if (this.count != 0) {
/*      */       
/* 1792 */       Entry<V> rp = new Entry<>(), rq = new Entry<>();
/* 1793 */       Entry<V> p = rp;
/* 1794 */       rp.left(this.tree);
/* 1795 */       Entry<V> q = rq;
/* 1796 */       rq.pred((Entry<V>)null);
/*      */       while (true) {
/* 1798 */         if (!p.pred()) {
/* 1799 */           Entry<V> e = p.left.clone();
/* 1800 */           e.pred(q.left);
/* 1801 */           e.succ(q);
/* 1802 */           q.left(e);
/* 1803 */           p = p.left;
/* 1804 */           q = q.left;
/*      */         } else {
/* 1806 */           while (p.succ()) {
/* 1807 */             p = p.right;
/* 1808 */             if (p == null) {
/* 1809 */               q.right = null;
/* 1810 */               c.tree = rq.left;
/* 1811 */               c.firstEntry = c.tree;
/* 1812 */               while (c.firstEntry.left != null)
/* 1813 */                 c.firstEntry = c.firstEntry.left; 
/* 1814 */               c.lastEntry = c.tree;
/* 1815 */               while (c.lastEntry.right != null)
/* 1816 */                 c.lastEntry = c.lastEntry.right; 
/* 1817 */               return c;
/*      */             } 
/* 1819 */             q = q.right;
/*      */           } 
/* 1821 */           p = p.right;
/* 1822 */           q = q.right;
/*      */         } 
/* 1824 */         if (!p.succ()) {
/* 1825 */           Entry<V> e = p.right.clone();
/* 1826 */           e.succ(q.right);
/* 1827 */           e.pred(q);
/* 1828 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1832 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1835 */     int n = this.count;
/* 1836 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1838 */     s.defaultWriteObject();
/* 1839 */     while (n-- != 0) {
/* 1840 */       Entry<V> e = i.nextEntry();
/* 1841 */       s.writeByte(e.key);
/* 1842 */       s.writeObject(e.value);
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
/* 1863 */     if (n == 1) {
/* 1864 */       Entry<V> entry = new Entry<>(s.readByte(), (V)s.readObject());
/* 1865 */       entry.pred(pred);
/* 1866 */       entry.succ(succ);
/* 1867 */       return entry;
/*      */     } 
/* 1869 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1874 */       Entry<V> entry = new Entry<>(s.readByte(), (V)s.readObject());
/* 1875 */       entry.right(new Entry<>(s.readByte(), (V)s.readObject()));
/* 1876 */       entry.right.pred(entry);
/* 1877 */       entry.balance(1);
/* 1878 */       entry.pred(pred);
/* 1879 */       entry.right.succ(succ);
/* 1880 */       return entry;
/*      */     } 
/*      */     
/* 1883 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1884 */     Entry<V> top = new Entry<>();
/* 1885 */     top.left(readTree(s, leftN, pred, top));
/* 1886 */     top.key = s.readByte();
/* 1887 */     top.value = (V)s.readObject();
/* 1888 */     top.right(readTree(s, rightN, top, succ));
/* 1889 */     if (n == (n & -n))
/* 1890 */       top.balance(1); 
/* 1891 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1894 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1899 */     setActualComparator();
/* 1900 */     allocatePaths();
/* 1901 */     if (this.count != 0) {
/* 1902 */       this.tree = readTree(s, this.count, (Entry<V>)null, (Entry<V>)null);
/*      */       
/* 1904 */       Entry<V> e = this.tree;
/* 1905 */       while (e.left() != null)
/* 1906 */         e = e.left(); 
/* 1907 */       this.firstEntry = e;
/* 1908 */       e = this.tree;
/* 1909 */       while (e.right() != null)
/* 1910 */         e = e.right(); 
/* 1911 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2ReferenceAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */