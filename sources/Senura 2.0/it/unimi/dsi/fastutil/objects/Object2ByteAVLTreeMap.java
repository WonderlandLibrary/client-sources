/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteListIterator;
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
/*      */ public class Object2ByteAVLTreeMap<K>
/*      */   extends AbstractObject2ByteSortedMap<K>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<K> tree;
/*      */   protected int count;
/*      */   protected transient Entry<K> firstEntry;
/*      */   protected transient Entry<K> lastEntry;
/*      */   protected transient ObjectSortedSet<Object2ByteMap.Entry<K>> entries;
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */   protected transient ByteCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super K> storedComparator;
/*      */   protected transient Comparator<? super K> actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Object2ByteAVLTreeMap() {
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
/*      */   public Object2ByteAVLTreeMap(Comparator<? super K> c) {
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
/*      */   public Object2ByteAVLTreeMap(Map<? extends K, ? extends Byte> m) {
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
/*      */   public Object2ByteAVLTreeMap(SortedMap<K, Byte> m) {
/*  123 */     this(m.comparator());
/*  124 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ByteAVLTreeMap(Object2ByteMap<? extends K> m) {
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
/*      */   public Object2ByteAVLTreeMap(Object2ByteSortedMap<K> m) {
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
/*      */   public Object2ByteAVLTreeMap(K[] k, byte[] v, Comparator<? super K> c) {
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
/*      */   public Object2ByteAVLTreeMap(K[] k, byte[] v) {
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
/*      */   public byte addTo(K k, byte incr) {
/*  268 */     Entry<K> e = add(k);
/*  269 */     byte oldValue = e.value;
/*  270 */     e.value = (byte)(e.value + incr);
/*  271 */     return oldValue;
/*      */   }
/*      */   
/*      */   public byte put(K k, byte v) {
/*  275 */     Entry<K> e = add(k);
/*  276 */     byte oldValue = e.value;
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
/*      */   public byte removeByte(Object k) {
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
/*      */   public boolean containsValue(byte v) {
/*  725 */     ValueIterator i = new ValueIterator();
/*      */     
/*  727 */     int j = this.count;
/*  728 */     while (j-- != 0) {
/*  729 */       byte ev = i.nextByte();
/*  730 */       if (ev == v)
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
/*      */     extends AbstractObject2ByteMap.BasicEntry<K>
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
/*  773 */       super((K)null, (byte)0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(K k, byte v) {
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
/*      */     public byte setValue(byte value) {
/*  935 */       byte oldValue = this.value;
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
/*  958 */       Map.Entry<K, Byte> e = (Map.Entry<K, Byte>)o;
/*  959 */       return (Objects.equals(this.key, e.getKey()) && this.value == ((Byte)e.getValue()).byteValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  963 */       return this.key.hashCode() ^ this.value;
/*      */     }
/*      */     
/*      */     public String toString() {
/*  967 */       return (new StringBuilder()).append(this.key).append("=>").append(this.value).toString();
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
/*  988 */     return (findKey((K)k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  992 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  996 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public byte getByte(Object k) {
/* 1001 */     Entry<K> e = findKey((K)k);
/* 1002 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public K firstKey() {
/* 1006 */     if (this.tree == null)
/* 1007 */       throw new NoSuchElementException(); 
/* 1008 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public K lastKey() {
/* 1012 */     if (this.tree == null)
/* 1013 */       throw new NoSuchElementException(); 
/* 1014 */     return this.lastEntry.key;
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
/*      */     Object2ByteAVLTreeMap.Entry<K> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2ByteAVLTreeMap.Entry<K> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2ByteAVLTreeMap.Entry<K> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1045 */     int index = 0;
/*      */     TreeIterator() {
/* 1047 */       this.next = Object2ByteAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(K k) {
/* 1050 */       if ((this.next = Object2ByteAVLTreeMap.this.locateKey(k)) != null)
/* 1051 */         if (Object2ByteAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1052 */           this.prev = this.next;
/* 1053 */           this.next = this.next.next();
/*      */         } else {
/* 1055 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/* 1059 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1062 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1065 */       this.next = this.next.next();
/*      */     }
/*      */     Object2ByteAVLTreeMap.Entry<K> nextEntry() {
/* 1068 */       if (!hasNext())
/* 1069 */         throw new NoSuchElementException(); 
/* 1070 */       this.curr = this.prev = this.next;
/* 1071 */       this.index++;
/* 1072 */       updateNext();
/* 1073 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1076 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Object2ByteAVLTreeMap.Entry<K> previousEntry() {
/* 1079 */       if (!hasPrevious())
/* 1080 */         throw new NoSuchElementException(); 
/* 1081 */       this.curr = this.next = this.prev;
/* 1082 */       this.index--;
/* 1083 */       updatePrevious();
/* 1084 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1087 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1090 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1093 */       if (this.curr == null) {
/* 1094 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1099 */       if (this.curr == this.prev)
/* 1100 */         this.index--; 
/* 1101 */       this.next = this.prev = this.curr;
/* 1102 */       updatePrevious();
/* 1103 */       updateNext();
/* 1104 */       Object2ByteAVLTreeMap.this.removeByte(this.curr.key);
/* 1105 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1108 */       int i = n;
/* 1109 */       while (i-- != 0 && hasNext())
/* 1110 */         nextEntry(); 
/* 1111 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1114 */       int i = n;
/* 1115 */       while (i-- != 0 && hasPrevious())
/* 1116 */         previousEntry(); 
/* 1117 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Object2ByteMap.Entry<K>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(K k) {
/* 1130 */       super(k);
/*      */     }
/*      */     
/*      */     public Object2ByteMap.Entry<K> next() {
/* 1134 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Object2ByteMap.Entry<K> previous() {
/* 1138 */       return previousEntry();
/*      */     }
/*      */     
/*      */     public void set(Object2ByteMap.Entry<K> ok) {
/* 1142 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Object2ByteMap.Entry<K> ok) {
/* 1146 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Object2ByteMap.Entry<K>> object2ByteEntrySet() {
/* 1151 */     if (this.entries == null)
/* 1152 */       this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2ByteMap.Entry<Object2ByteMap.Entry<K>>>()
/*      */         {
/*      */           final Comparator<? super Object2ByteMap.Entry<K>> comparator;
/*      */           
/*      */           public Comparator<? super Object2ByteMap.Entry<K>> comparator() {
/* 1157 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2ByteMap.Entry<K>> iterator() {
/* 1161 */             return new Object2ByteAVLTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2ByteMap.Entry<K>> iterator(Object2ByteMap.Entry<K> from) {
/* 1166 */             return new Object2ByteAVLTreeMap.EntryIterator(from.getKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1171 */             if (!(o instanceof Map.Entry))
/* 1172 */               return false; 
/* 1173 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1174 */             if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1175 */               return false; 
/* 1176 */             Object2ByteAVLTreeMap.Entry<K> f = Object2ByteAVLTreeMap.this.findKey((K)e.getKey());
/* 1177 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1182 */             if (!(o instanceof Map.Entry))
/* 1183 */               return false; 
/* 1184 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1185 */             if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1186 */               return false; 
/* 1187 */             Object2ByteAVLTreeMap.Entry<K> f = Object2ByteAVLTreeMap.this.findKey((K)e.getKey());
/* 1188 */             if (f == null || f.getByteValue() != ((Byte)e.getValue()).byteValue())
/* 1189 */               return false; 
/* 1190 */             Object2ByteAVLTreeMap.this.removeByte(f.key);
/* 1191 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1195 */             return Object2ByteAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1199 */             Object2ByteAVLTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Object2ByteMap.Entry<K> first() {
/* 1203 */             return Object2ByteAVLTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Object2ByteMap.Entry<K> last() {
/* 1207 */             return Object2ByteAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2ByteMap.Entry<K>> subSet(Object2ByteMap.Entry<K> from, Object2ByteMap.Entry<K> to) {
/* 1212 */             return Object2ByteAVLTreeMap.this.subMap(from.getKey(), to.getKey()).object2ByteEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2ByteMap.Entry<K>> headSet(Object2ByteMap.Entry<K> to) {
/* 1216 */             return Object2ByteAVLTreeMap.this.headMap(to.getKey()).object2ByteEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2ByteMap.Entry<K>> tailSet(Object2ByteMap.Entry<K> from) {
/* 1220 */             return Object2ByteAVLTreeMap.this.tailMap(from.getKey()).object2ByteEntrySet();
/*      */           }
/*      */         }; 
/* 1223 */     return this.entries;
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
/* 1239 */       super(k);
/*      */     }
/*      */     
/*      */     public K next() {
/* 1243 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1247 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractObject2ByteSortedMap<K>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1254 */       return new Object2ByteAVLTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1258 */       return new Object2ByteAVLTreeMap.KeyIterator(from);
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
/* 1273 */     if (this.keys == null)
/* 1274 */       this.keys = new KeySet(); 
/* 1275 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements ByteListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1290 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public byte previousByte() {
/* 1294 */       return (previousEntry()).value;
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
/*      */   public ByteCollection values() {
/* 1309 */     if (this.values == null)
/* 1310 */       this.values = (ByteCollection)new AbstractByteCollection()
/*      */         {
/*      */           public ByteIterator iterator() {
/* 1313 */             return (ByteIterator)new Object2ByteAVLTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(byte k) {
/* 1317 */             return Object2ByteAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1321 */             return Object2ByteAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1325 */             Object2ByteAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1328 */     return this.values;
/*      */   }
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1332 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Object2ByteSortedMap<K> headMap(K to) {
/* 1336 */     return new Submap(null, true, to, false);
/*      */   }
/*      */   
/*      */   public Object2ByteSortedMap<K> tailMap(K from) {
/* 1340 */     return new Submap(from, false, null, true);
/*      */   }
/*      */   
/*      */   public Object2ByteSortedMap<K> subMap(K from, K to) {
/* 1344 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractObject2ByteSortedMap<K>
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
/*      */     protected transient ObjectSortedSet<Object2ByteMap.Entry<K>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ByteCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(K from, boolean bottom, K to, boolean top) {
/* 1388 */       if (!bottom && !top && Object2ByteAVLTreeMap.this.compare(from, to) > 0)
/* 1389 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1390 */       this.from = from;
/* 1391 */       this.bottom = bottom;
/* 1392 */       this.to = to;
/* 1393 */       this.top = top;
/* 1394 */       this.defRetValue = Object2ByteAVLTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1398 */       SubmapIterator i = new SubmapIterator();
/* 1399 */       while (i.hasNext()) {
/* 1400 */         i.nextEntry();
/* 1401 */         i.remove();
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
/* 1412 */       return ((this.bottom || Object2ByteAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Object2ByteAVLTreeMap.this
/* 1413 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2ByteMap.Entry<K>> object2ByteEntrySet() {
/* 1417 */       if (this.entries == null)
/* 1418 */         this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2ByteMap.Entry<Object2ByteMap.Entry<K>>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Object2ByteMap.Entry<K>> iterator() {
/* 1421 */               return new Object2ByteAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Object2ByteMap.Entry<K>> iterator(Object2ByteMap.Entry<K> from) {
/* 1426 */               return new Object2ByteAVLTreeMap.Submap.SubmapEntryIterator(from.getKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Object2ByteMap.Entry<K>> comparator() {
/* 1430 */               return Object2ByteAVLTreeMap.this.object2ByteEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1435 */               if (!(o instanceof Map.Entry))
/* 1436 */                 return false; 
/* 1437 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1438 */               if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1439 */                 return false; 
/* 1440 */               Object2ByteAVLTreeMap.Entry<K> f = Object2ByteAVLTreeMap.this.findKey((K)e.getKey());
/* 1441 */               return (f != null && Object2ByteAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1446 */               if (!(o instanceof Map.Entry))
/* 1447 */                 return false; 
/* 1448 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1449 */               if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1450 */                 return false; 
/* 1451 */               Object2ByteAVLTreeMap.Entry<K> f = Object2ByteAVLTreeMap.this.findKey((K)e.getKey());
/* 1452 */               if (f != null && Object2ByteAVLTreeMap.Submap.this.in(f.key))
/* 1453 */                 Object2ByteAVLTreeMap.Submap.this.removeByte(f.key); 
/* 1454 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1458 */               int c = 0;
/* 1459 */               for (Iterator<?> i = iterator(); i.hasNext(); i.next())
/* 1460 */                 c++; 
/* 1461 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1465 */               return !(new Object2ByteAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1469 */               Object2ByteAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Object2ByteMap.Entry<K> first() {
/* 1473 */               return Object2ByteAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Object2ByteMap.Entry<K> last() {
/* 1477 */               return Object2ByteAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2ByteMap.Entry<K>> subSet(Object2ByteMap.Entry<K> from, Object2ByteMap.Entry<K> to) {
/* 1482 */               return Object2ByteAVLTreeMap.Submap.this.subMap(from.getKey(), to.getKey()).object2ByteEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2ByteMap.Entry<K>> headSet(Object2ByteMap.Entry<K> to) {
/* 1486 */               return Object2ByteAVLTreeMap.Submap.this.headMap(to.getKey()).object2ByteEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2ByteMap.Entry<K>> tailSet(Object2ByteMap.Entry<K> from) {
/* 1490 */               return Object2ByteAVLTreeMap.Submap.this.tailMap(from.getKey()).object2ByteEntrySet();
/*      */             }
/*      */           }; 
/* 1493 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractObject2ByteSortedMap<K>.KeySet {
/*      */       public ObjectBidirectionalIterator<K> iterator() {
/* 1498 */         return new Object2ByteAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1502 */         return new Object2ByteAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> keySet() {
/* 1507 */       if (this.keys == null)
/* 1508 */         this.keys = new KeySet(); 
/* 1509 */       return this.keys;
/*      */     }
/*      */     
/*      */     public ByteCollection values() {
/* 1513 */       if (this.values == null)
/* 1514 */         this.values = (ByteCollection)new AbstractByteCollection()
/*      */           {
/*      */             public ByteIterator iterator() {
/* 1517 */               return (ByteIterator)new Object2ByteAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(byte k) {
/* 1521 */               return Object2ByteAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1525 */               return Object2ByteAVLTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1529 */               Object2ByteAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1532 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object k) {
/* 1537 */       return (in((K)k) && Object2ByteAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(byte v) {
/* 1541 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1543 */       while (i.hasNext()) {
/* 1544 */         byte ev = (i.nextEntry()).value;
/* 1545 */         if (ev == v)
/* 1546 */           return true; 
/*      */       } 
/* 1548 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public byte getByte(Object k) {
/* 1554 */       K kk = (K)k; Object2ByteAVLTreeMap.Entry<K> e;
/* 1555 */       return (in(kk) && (e = Object2ByteAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public byte put(K k, byte v) {
/* 1559 */       Object2ByteAVLTreeMap.this.modified = false;
/* 1560 */       if (!in(k))
/* 1561 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1562 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1563 */       byte oldValue = Object2ByteAVLTreeMap.this.put(k, v);
/* 1564 */       return Object2ByteAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte removeByte(Object k) {
/* 1569 */       Object2ByteAVLTreeMap.this.modified = false;
/* 1570 */       if (!in((K)k))
/* 1571 */         return this.defRetValue; 
/* 1572 */       byte oldValue = Object2ByteAVLTreeMap.this.removeByte(k);
/* 1573 */       return Object2ByteAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1577 */       SubmapIterator i = new SubmapIterator();
/* 1578 */       int n = 0;
/* 1579 */       while (i.hasNext()) {
/* 1580 */         n++;
/* 1581 */         i.nextEntry();
/*      */       } 
/* 1583 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1587 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1591 */       return Object2ByteAVLTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Object2ByteSortedMap<K> headMap(K to) {
/* 1595 */       if (this.top)
/* 1596 */         return new Submap(this.from, this.bottom, to, false); 
/* 1597 */       return (Object2ByteAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Object2ByteSortedMap<K> tailMap(K from) {
/* 1601 */       if (this.bottom)
/* 1602 */         return new Submap(from, false, this.to, this.top); 
/* 1603 */       return (Object2ByteAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Object2ByteSortedMap<K> subMap(K from, K to) {
/* 1607 */       if (this.top && this.bottom)
/* 1608 */         return new Submap(from, false, to, false); 
/* 1609 */       if (!this.top)
/* 1610 */         to = (Object2ByteAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1611 */       if (!this.bottom)
/* 1612 */         from = (Object2ByteAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1613 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1614 */         return this; 
/* 1615 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2ByteAVLTreeMap.Entry<K> firstEntry() {
/*      */       Object2ByteAVLTreeMap.Entry<K> e;
/* 1624 */       if (Object2ByteAVLTreeMap.this.tree == null) {
/* 1625 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1629 */       if (this.bottom) {
/* 1630 */         e = Object2ByteAVLTreeMap.this.firstEntry;
/*      */       } else {
/* 1632 */         e = Object2ByteAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1634 */         if (Object2ByteAVLTreeMap.this.compare(e.key, this.from) < 0) {
/* 1635 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1639 */       if (e == null || (!this.top && Object2ByteAVLTreeMap.this.compare(e.key, this.to) >= 0))
/* 1640 */         return null; 
/* 1641 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2ByteAVLTreeMap.Entry<K> lastEntry() {
/*      */       Object2ByteAVLTreeMap.Entry<K> e;
/* 1650 */       if (Object2ByteAVLTreeMap.this.tree == null) {
/* 1651 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1655 */       if (this.top) {
/* 1656 */         e = Object2ByteAVLTreeMap.this.lastEntry;
/*      */       } else {
/* 1658 */         e = Object2ByteAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1660 */         if (Object2ByteAVLTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1661 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1665 */       if (e == null || (!this.bottom && Object2ByteAVLTreeMap.this.compare(e.key, this.from) < 0))
/* 1666 */         return null; 
/* 1667 */       return e;
/*      */     }
/*      */     
/*      */     public K firstKey() {
/* 1671 */       Object2ByteAVLTreeMap.Entry<K> e = firstEntry();
/* 1672 */       if (e == null)
/* 1673 */         throw new NoSuchElementException(); 
/* 1674 */       return e.key;
/*      */     }
/*      */     
/*      */     public K lastKey() {
/* 1678 */       Object2ByteAVLTreeMap.Entry<K> e = lastEntry();
/* 1679 */       if (e == null)
/* 1680 */         throw new NoSuchElementException(); 
/* 1681 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Object2ByteAVLTreeMap<K>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1694 */         this.next = Object2ByteAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(K k) {
/* 1697 */         this();
/* 1698 */         if (this.next != null)
/* 1699 */           if (!Object2ByteAVLTreeMap.Submap.this.bottom && Object2ByteAVLTreeMap.this.compare(k, this.next.key) < 0) {
/* 1700 */             this.prev = null;
/* 1701 */           } else if (!Object2ByteAVLTreeMap.Submap.this.top && Object2ByteAVLTreeMap.this.compare(k, (this.prev = Object2ByteAVLTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1702 */             this.next = null;
/*      */           } else {
/* 1704 */             this.next = Object2ByteAVLTreeMap.this.locateKey(k);
/* 1705 */             if (Object2ByteAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1706 */               this.prev = this.next;
/* 1707 */               this.next = this.next.next();
/*      */             } else {
/* 1709 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1715 */         this.prev = this.prev.prev();
/* 1716 */         if (!Object2ByteAVLTreeMap.Submap.this.bottom && this.prev != null && Object2ByteAVLTreeMap.this.compare(this.prev.key, Object2ByteAVLTreeMap.Submap.this.from) < 0)
/* 1717 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1721 */         this.next = this.next.next();
/* 1722 */         if (!Object2ByteAVLTreeMap.Submap.this.top && this.next != null && Object2ByteAVLTreeMap.this.compare(this.next.key, Object2ByteAVLTreeMap.Submap.this.to) >= 0)
/* 1723 */           this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Object2ByteMap.Entry<K>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(K k) {
/* 1732 */         super(k);
/*      */       }
/*      */       
/*      */       public Object2ByteMap.Entry<K> next() {
/* 1736 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Object2ByteMap.Entry<K> previous() {
/* 1740 */         return previousEntry();
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
/* 1758 */         super(from);
/*      */       }
/*      */       
/*      */       public K next() {
/* 1762 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public K previous() {
/* 1766 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements ByteListIterator
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public byte nextByte() {
/* 1782 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public byte previousByte() {
/* 1786 */         return (previousEntry()).value;
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
/*      */   public Object2ByteAVLTreeMap<K> clone() {
/*      */     Object2ByteAVLTreeMap<K> c;
/*      */     try {
/* 1805 */       c = (Object2ByteAVLTreeMap<K>)super.clone();
/* 1806 */     } catch (CloneNotSupportedException cantHappen) {
/* 1807 */       throw new InternalError();
/*      */     } 
/* 1809 */     c.keys = null;
/* 1810 */     c.values = null;
/* 1811 */     c.entries = null;
/* 1812 */     c.allocatePaths();
/* 1813 */     if (this.count != 0) {
/*      */       
/* 1815 */       Entry<K> rp = new Entry<>(), rq = new Entry<>();
/* 1816 */       Entry<K> p = rp;
/* 1817 */       rp.left(this.tree);
/* 1818 */       Entry<K> q = rq;
/* 1819 */       rq.pred((Entry<K>)null);
/*      */       while (true) {
/* 1821 */         if (!p.pred()) {
/* 1822 */           Entry<K> e = p.left.clone();
/* 1823 */           e.pred(q.left);
/* 1824 */           e.succ(q);
/* 1825 */           q.left(e);
/* 1826 */           p = p.left;
/* 1827 */           q = q.left;
/*      */         } else {
/* 1829 */           while (p.succ()) {
/* 1830 */             p = p.right;
/* 1831 */             if (p == null) {
/* 1832 */               q.right = null;
/* 1833 */               c.tree = rq.left;
/* 1834 */               c.firstEntry = c.tree;
/* 1835 */               while (c.firstEntry.left != null)
/* 1836 */                 c.firstEntry = c.firstEntry.left; 
/* 1837 */               c.lastEntry = c.tree;
/* 1838 */               while (c.lastEntry.right != null)
/* 1839 */                 c.lastEntry = c.lastEntry.right; 
/* 1840 */               return c;
/*      */             } 
/* 1842 */             q = q.right;
/*      */           } 
/* 1844 */           p = p.right;
/* 1845 */           q = q.right;
/*      */         } 
/* 1847 */         if (!p.succ()) {
/* 1848 */           Entry<K> e = p.right.clone();
/* 1849 */           e.succ(q.right);
/* 1850 */           e.pred(q);
/* 1851 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1855 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1858 */     int n = this.count;
/* 1859 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1861 */     s.defaultWriteObject();
/* 1862 */     while (n-- != 0) {
/* 1863 */       Entry<K> e = i.nextEntry();
/* 1864 */       s.writeObject(e.key);
/* 1865 */       s.writeByte(e.value);
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
/* 1886 */     if (n == 1) {
/* 1887 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readByte());
/* 1888 */       entry.pred(pred);
/* 1889 */       entry.succ(succ);
/* 1890 */       return entry;
/*      */     } 
/* 1892 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1897 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readByte());
/* 1898 */       entry.right(new Entry<>((K)s.readObject(), s.readByte()));
/* 1899 */       entry.right.pred(entry);
/* 1900 */       entry.balance(1);
/* 1901 */       entry.pred(pred);
/* 1902 */       entry.right.succ(succ);
/* 1903 */       return entry;
/*      */     } 
/*      */     
/* 1906 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1907 */     Entry<K> top = new Entry<>();
/* 1908 */     top.left(readTree(s, leftN, pred, top));
/* 1909 */     top.key = (K)s.readObject();
/* 1910 */     top.value = s.readByte();
/* 1911 */     top.right(readTree(s, rightN, top, succ));
/* 1912 */     if (n == (n & -n))
/* 1913 */       top.balance(1); 
/* 1914 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1917 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1922 */     setActualComparator();
/* 1923 */     allocatePaths();
/* 1924 */     if (this.count != 0) {
/* 1925 */       this.tree = readTree(s, this.count, (Entry<K>)null, (Entry<K>)null);
/*      */       
/* 1927 */       Entry<K> e = this.tree;
/* 1928 */       while (e.left() != null)
/* 1929 */         e = e.left(); 
/* 1930 */       this.firstEntry = e;
/* 1931 */       e = this.tree;
/* 1932 */       while (e.right() != null)
/* 1933 */         e = e.right(); 
/* 1934 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ByteAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */