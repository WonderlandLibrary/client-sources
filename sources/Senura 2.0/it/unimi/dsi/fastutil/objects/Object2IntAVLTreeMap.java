/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntListIterator;
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
/*      */ public class Object2IntAVLTreeMap<K>
/*      */   extends AbstractObject2IntSortedMap<K>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<K> tree;
/*      */   protected int count;
/*      */   protected transient Entry<K> firstEntry;
/*      */   protected transient Entry<K> lastEntry;
/*      */   protected transient ObjectSortedSet<Object2IntMap.Entry<K>> entries;
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */   protected transient IntCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super K> storedComparator;
/*      */   protected transient Comparator<? super K> actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Object2IntAVLTreeMap() {
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
/*   89 */     this.actualComparator = this.storedComparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntAVLTreeMap(Comparator<? super K> c) {
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
/*      */   public Object2IntAVLTreeMap(Map<? extends K, ? extends Integer> m) {
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
/*      */   public Object2IntAVLTreeMap(SortedMap<K, Integer> m) {
/*  120 */     this(m.comparator());
/*  121 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntAVLTreeMap(Object2IntMap<? extends K> m) {
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
/*      */   public Object2IntAVLTreeMap(Object2IntSortedMap<K> m) {
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
/*      */   public Object2IntAVLTreeMap(K[] k, int[] v, Comparator<? super K> c) {
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
/*      */   public Object2IntAVLTreeMap(K[] k, int[] v) {
/*  176 */     this(k, v, (Comparator<? super K>)null);
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
/*  204 */     return (this.actualComparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.actualComparator.compare(k1, k2);
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
/*  216 */     Entry<K> e = this.tree;
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
/*      */   final Entry<K> locateKey(K k) {
/*  232 */     Entry<K> e = this.tree, last = this.tree;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int addTo(K k, int incr) {
/*  265 */     Entry<K> e = add(k);
/*  266 */     int oldValue = e.value;
/*  267 */     e.value += incr;
/*  268 */     return oldValue;
/*      */   }
/*      */   
/*      */   public int put(K k, int v) {
/*  272 */     Entry<K> e = add(k);
/*  273 */     int oldValue = e.value;
/*  274 */     e.value = v;
/*  275 */     return oldValue;
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
/*  292 */     this.modified = false;
/*  293 */     Entry<K> e = null;
/*  294 */     if (this.tree == null) {
/*  295 */       this.count++;
/*  296 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*  297 */       this.modified = true;
/*      */     } else {
/*  299 */       Entry<K> p = this.tree, q = null, y = this.tree, z = null, w = null;
/*  300 */       int i = 0; while (true) {
/*      */         int cmp;
/*  302 */         if ((cmp = compare(k, p.key)) == 0) {
/*  303 */           return p;
/*      */         }
/*  305 */         if (p.balance() != 0) {
/*  306 */           i = 0;
/*  307 */           z = q;
/*  308 */           y = p;
/*      */         } 
/*  310 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  311 */           if (p.succ()) {
/*  312 */             this.count++;
/*  313 */             e = new Entry<>(k, this.defRetValue);
/*  314 */             this.modified = true;
/*  315 */             if (p.right == null)
/*  316 */               this.lastEntry = e; 
/*  317 */             e.left = p;
/*  318 */             e.right = p.right;
/*  319 */             p.right(e);
/*      */             break;
/*      */           } 
/*  322 */           q = p;
/*  323 */           p = p.right; continue;
/*      */         } 
/*  325 */         if (p.pred()) {
/*  326 */           this.count++;
/*  327 */           e = new Entry<>(k, this.defRetValue);
/*  328 */           this.modified = true;
/*  329 */           if (p.left == null)
/*  330 */             this.firstEntry = e; 
/*  331 */           e.right = p;
/*  332 */           e.left = p.left;
/*  333 */           p.left(e);
/*      */           break;
/*      */         } 
/*  336 */         q = p;
/*  337 */         p = p.left;
/*      */       } 
/*      */       
/*  340 */       p = y;
/*  341 */       i = 0;
/*  342 */       while (p != e) {
/*  343 */         if (this.dirPath[i]) {
/*  344 */           p.incBalance();
/*      */         } else {
/*  346 */           p.decBalance();
/*  347 */         }  p = this.dirPath[i++] ? p.right : p.left;
/*      */       } 
/*  349 */       if (y.balance() == -2) {
/*  350 */         Entry<K> x = y.left;
/*  351 */         if (x.balance() == -1) {
/*  352 */           w = x;
/*  353 */           if (x.succ()) {
/*  354 */             x.succ(false);
/*  355 */             y.pred(x);
/*      */           } else {
/*  357 */             y.left = x.right;
/*  358 */           }  x.right = y;
/*  359 */           x.balance(0);
/*  360 */           y.balance(0);
/*      */         } else {
/*  362 */           assert x.balance() == 1;
/*  363 */           w = x.right;
/*  364 */           x.right = w.left;
/*  365 */           w.left = x;
/*  366 */           y.left = w.right;
/*  367 */           w.right = y;
/*  368 */           if (w.balance() == -1) {
/*  369 */             x.balance(0);
/*  370 */             y.balance(1);
/*  371 */           } else if (w.balance() == 0) {
/*  372 */             x.balance(0);
/*  373 */             y.balance(0);
/*      */           } else {
/*  375 */             x.balance(-1);
/*  376 */             y.balance(0);
/*      */           } 
/*  378 */           w.balance(0);
/*  379 */           if (w.pred()) {
/*  380 */             x.succ(w);
/*  381 */             w.pred(false);
/*      */           } 
/*  383 */           if (w.succ()) {
/*  384 */             y.pred(w);
/*  385 */             w.succ(false);
/*      */           } 
/*      */         } 
/*  388 */       } else if (y.balance() == 2) {
/*  389 */         Entry<K> x = y.right;
/*  390 */         if (x.balance() == 1) {
/*  391 */           w = x;
/*  392 */           if (x.pred()) {
/*  393 */             x.pred(false);
/*  394 */             y.succ(x);
/*      */           } else {
/*  396 */             y.right = x.left;
/*  397 */           }  x.left = y;
/*  398 */           x.balance(0);
/*  399 */           y.balance(0);
/*      */         } else {
/*  401 */           assert x.balance() == -1;
/*  402 */           w = x.left;
/*  403 */           x.left = w.right;
/*  404 */           w.right = x;
/*  405 */           y.right = w.left;
/*  406 */           w.left = y;
/*  407 */           if (w.balance() == 1) {
/*  408 */             x.balance(0);
/*  409 */             y.balance(-1);
/*  410 */           } else if (w.balance() == 0) {
/*  411 */             x.balance(0);
/*  412 */             y.balance(0);
/*      */           } else {
/*  414 */             x.balance(1);
/*  415 */             y.balance(0);
/*      */           } 
/*  417 */           w.balance(0);
/*  418 */           if (w.pred()) {
/*  419 */             y.succ(w);
/*  420 */             w.pred(false);
/*      */           } 
/*  422 */           if (w.succ()) {
/*  423 */             x.pred(w);
/*  424 */             w.succ(false);
/*      */           } 
/*      */         } 
/*      */       } else {
/*  428 */         return e;
/*  429 */       }  if (z == null) {
/*  430 */         this.tree = w;
/*      */       }
/*  432 */       else if (z.left == y) {
/*  433 */         z.left = w;
/*      */       } else {
/*  435 */         z.right = w;
/*      */       } 
/*      */     } 
/*  438 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry<K> parent(Entry<K> e) {
/*  448 */     if (e == this.tree) {
/*  449 */       return null;
/*      */     }
/*  451 */     Entry<K> y = e, x = y;
/*      */     while (true) {
/*  453 */       if (y.succ()) {
/*  454 */         Entry<K> p = y.right;
/*  455 */         if (p == null || p.left != e) {
/*  456 */           while (!x.pred())
/*  457 */             x = x.left; 
/*  458 */           p = x.left;
/*      */         } 
/*  460 */         return p;
/*  461 */       }  if (x.pred()) {
/*  462 */         Entry<K> p = x.left;
/*  463 */         if (p == null || p.right != e) {
/*  464 */           while (!y.succ())
/*  465 */             y = y.right; 
/*  466 */           p = y.right;
/*      */         } 
/*  468 */         return p;
/*      */       } 
/*  470 */       x = x.left;
/*  471 */       y = y.right;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int removeInt(Object k) {
/*  481 */     this.modified = false;
/*  482 */     if (this.tree == null) {
/*  483 */       return this.defRetValue;
/*      */     }
/*  485 */     Entry<K> p = this.tree, q = null;
/*  486 */     boolean dir = false;
/*  487 */     K kk = (K)k;
/*      */     int cmp;
/*  489 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  491 */       if (dir = (cmp > 0)) {
/*  492 */         q = p;
/*  493 */         if ((p = p.right()) == null)
/*  494 */           return this.defRetValue;  continue;
/*      */       } 
/*  496 */       q = p;
/*  497 */       if ((p = p.left()) == null) {
/*  498 */         return this.defRetValue;
/*      */       }
/*      */     } 
/*  501 */     if (p.left == null)
/*  502 */       this.firstEntry = p.next(); 
/*  503 */     if (p.right == null)
/*  504 */       this.lastEntry = p.prev(); 
/*  505 */     if (p.succ())
/*  506 */     { if (p.pred())
/*  507 */       { if (q != null)
/*  508 */         { if (dir) {
/*  509 */             q.succ(p.right);
/*      */           } else {
/*  511 */             q.pred(p.left);
/*      */           }  }
/*  513 */         else { this.tree = dir ? p.right : p.left; }
/*      */          }
/*  515 */       else { (p.prev()).right = p.right;
/*  516 */         if (q != null)
/*  517 */         { if (dir) {
/*  518 */             q.right = p.left;
/*      */           } else {
/*  520 */             q.left = p.left;
/*      */           }  }
/*  522 */         else { this.tree = p.left; }
/*      */          }
/*      */        }
/*  525 */     else { Entry<K> r = p.right;
/*  526 */       if (r.pred()) {
/*  527 */         r.left = p.left;
/*  528 */         r.pred(p.pred());
/*  529 */         if (!r.pred())
/*  530 */           (r.prev()).right = r; 
/*  531 */         if (q != null)
/*  532 */         { if (dir) {
/*  533 */             q.right = r;
/*      */           } else {
/*  535 */             q.left = r;
/*      */           }  }
/*  537 */         else { this.tree = r; }
/*  538 */          r.balance(p.balance());
/*  539 */         q = r;
/*  540 */         dir = true;
/*      */       } else {
/*      */         Entry<K> s;
/*      */         while (true) {
/*  544 */           s = r.left;
/*  545 */           if (s.pred())
/*      */             break; 
/*  547 */           r = s;
/*      */         } 
/*  549 */         if (s.succ()) {
/*  550 */           r.pred(s);
/*      */         } else {
/*  552 */           r.left = s.right;
/*  553 */         }  s.left = p.left;
/*  554 */         if (!p.pred()) {
/*  555 */           (p.prev()).right = s;
/*  556 */           s.pred(false);
/*      */         } 
/*  558 */         s.right = p.right;
/*  559 */         s.succ(false);
/*  560 */         if (q != null)
/*  561 */         { if (dir) {
/*  562 */             q.right = s;
/*      */           } else {
/*  564 */             q.left = s;
/*      */           }  }
/*  566 */         else { this.tree = s; }
/*  567 */          s.balance(p.balance());
/*  568 */         q = r;
/*  569 */         dir = false;
/*      */       }  }
/*      */ 
/*      */     
/*  573 */     while (q != null) {
/*  574 */       Entry<K> y = q;
/*  575 */       q = parent(y);
/*  576 */       if (!dir) {
/*  577 */         dir = (q != null && q.left != y);
/*  578 */         y.incBalance();
/*  579 */         if (y.balance() == 1)
/*      */           break; 
/*  581 */         if (y.balance() == 2) {
/*  582 */           Entry<K> x = y.right;
/*  583 */           assert x != null;
/*  584 */           if (x.balance() == -1) {
/*      */             
/*  586 */             assert x.balance() == -1;
/*  587 */             Entry<K> w = x.left;
/*  588 */             x.left = w.right;
/*  589 */             w.right = x;
/*  590 */             y.right = w.left;
/*  591 */             w.left = y;
/*  592 */             if (w.balance() == 1) {
/*  593 */               x.balance(0);
/*  594 */               y.balance(-1);
/*  595 */             } else if (w.balance() == 0) {
/*  596 */               x.balance(0);
/*  597 */               y.balance(0);
/*      */             } else {
/*  599 */               assert w.balance() == -1;
/*  600 */               x.balance(1);
/*  601 */               y.balance(0);
/*      */             } 
/*  603 */             w.balance(0);
/*  604 */             if (w.pred()) {
/*  605 */               y.succ(w);
/*  606 */               w.pred(false);
/*      */             } 
/*  608 */             if (w.succ()) {
/*  609 */               x.pred(w);
/*  610 */               w.succ(false);
/*      */             } 
/*  612 */             if (q != null) {
/*  613 */               if (dir) {
/*  614 */                 q.right = w; continue;
/*      */               } 
/*  616 */               q.left = w; continue;
/*      */             } 
/*  618 */             this.tree = w; continue;
/*      */           } 
/*  620 */           if (q != null)
/*  621 */           { if (dir) {
/*  622 */               q.right = x;
/*      */             } else {
/*  624 */               q.left = x;
/*      */             }  }
/*  626 */           else { this.tree = x; }
/*  627 */            if (x.balance() == 0) {
/*  628 */             y.right = x.left;
/*  629 */             x.left = y;
/*  630 */             x.balance(-1);
/*  631 */             y.balance(1);
/*      */             break;
/*      */           } 
/*  634 */           assert x.balance() == 1;
/*  635 */           if (x.pred()) {
/*  636 */             y.succ(true);
/*  637 */             x.pred(false);
/*      */           } else {
/*  639 */             y.right = x.left;
/*  640 */           }  x.left = y;
/*  641 */           y.balance(0);
/*  642 */           x.balance(0);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  646 */       dir = (q != null && q.left != y);
/*  647 */       y.decBalance();
/*  648 */       if (y.balance() == -1)
/*      */         break; 
/*  650 */       if (y.balance() == -2) {
/*  651 */         Entry<K> x = y.left;
/*  652 */         assert x != null;
/*  653 */         if (x.balance() == 1) {
/*      */           
/*  655 */           assert x.balance() == 1;
/*  656 */           Entry<K> w = x.right;
/*  657 */           x.right = w.left;
/*  658 */           w.left = x;
/*  659 */           y.left = w.right;
/*  660 */           w.right = y;
/*  661 */           if (w.balance() == -1) {
/*  662 */             x.balance(0);
/*  663 */             y.balance(1);
/*  664 */           } else if (w.balance() == 0) {
/*  665 */             x.balance(0);
/*  666 */             y.balance(0);
/*      */           } else {
/*  668 */             assert w.balance() == 1;
/*  669 */             x.balance(-1);
/*  670 */             y.balance(0);
/*      */           } 
/*  672 */           w.balance(0);
/*  673 */           if (w.pred()) {
/*  674 */             x.succ(w);
/*  675 */             w.pred(false);
/*      */           } 
/*  677 */           if (w.succ()) {
/*  678 */             y.pred(w);
/*  679 */             w.succ(false);
/*      */           } 
/*  681 */           if (q != null) {
/*  682 */             if (dir) {
/*  683 */               q.right = w; continue;
/*      */             } 
/*  685 */             q.left = w; continue;
/*      */           } 
/*  687 */           this.tree = w; continue;
/*      */         } 
/*  689 */         if (q != null)
/*  690 */         { if (dir) {
/*  691 */             q.right = x;
/*      */           } else {
/*  693 */             q.left = x;
/*      */           }  }
/*  695 */         else { this.tree = x; }
/*  696 */          if (x.balance() == 0) {
/*  697 */           y.left = x.right;
/*  698 */           x.right = y;
/*  699 */           x.balance(1);
/*  700 */           y.balance(-1);
/*      */           break;
/*      */         } 
/*  703 */         assert x.balance() == -1;
/*  704 */         if (x.succ()) {
/*  705 */           y.pred(true);
/*  706 */           x.succ(false);
/*      */         } else {
/*  708 */           y.left = x.right;
/*  709 */         }  x.right = y;
/*  710 */         y.balance(0);
/*  711 */         x.balance(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  716 */     this.modified = true;
/*  717 */     this.count--;
/*  718 */     return p.value;
/*      */   }
/*      */   
/*      */   public boolean containsValue(int v) {
/*  722 */     ValueIterator i = new ValueIterator();
/*      */     
/*  724 */     int j = this.count;
/*  725 */     while (j-- != 0) {
/*  726 */       int ev = i.nextInt();
/*  727 */       if (ev == v)
/*  728 */         return true; 
/*      */     } 
/*  730 */     return false;
/*      */   }
/*      */   
/*      */   public void clear() {
/*  734 */     this.count = 0;
/*  735 */     this.tree = null;
/*  736 */     this.entries = null;
/*  737 */     this.values = null;
/*  738 */     this.keys = null;
/*  739 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<K>
/*      */     extends AbstractObject2IntMap.BasicEntry<K>
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
/*  770 */       super((K)null, 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(K k, int v) {
/*  781 */       super(k, v);
/*  782 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> left() {
/*  790 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> right() {
/*  798 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  806 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  814 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  823 */       if (pred) {
/*  824 */         this.info |= 0x40000000;
/*      */       } else {
/*  826 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  835 */       if (succ) {
/*  836 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  838 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<K> pred) {
/*  847 */       this.info |= 0x40000000;
/*  848 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<K> succ) {
/*  857 */       this.info |= Integer.MIN_VALUE;
/*  858 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<K> left) {
/*  867 */       this.info &= 0xBFFFFFFF;
/*  868 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<K> right) {
/*  877 */       this.info &= Integer.MAX_VALUE;
/*  878 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int balance() {
/*  886 */       return (byte)this.info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void balance(int level) {
/*  895 */       this.info &= 0xFFFFFF00;
/*  896 */       this.info |= level & 0xFF;
/*      */     }
/*      */     
/*      */     void incBalance() {
/*  900 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
/*      */     }
/*      */     
/*      */     protected void decBalance() {
/*  904 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> next() {
/*  912 */       Entry<K> next = this.right;
/*  913 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  914 */         while ((next.info & 0x40000000) == 0)
/*  915 */           next = next.left;  
/*  916 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> prev() {
/*  924 */       Entry<K> prev = this.left;
/*  925 */       if ((this.info & 0x40000000) == 0)
/*  926 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  927 */           prev = prev.right;  
/*  928 */       return prev;
/*      */     }
/*      */     
/*      */     public int setValue(int value) {
/*  932 */       int oldValue = this.value;
/*  933 */       this.value = value;
/*  934 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry<K> clone() {
/*      */       Entry<K> c;
/*      */       try {
/*  941 */         c = (Entry<K>)super.clone();
/*  942 */       } catch (CloneNotSupportedException cantHappen) {
/*  943 */         throw new InternalError();
/*      */       } 
/*  945 */       c.key = this.key;
/*  946 */       c.value = this.value;
/*  947 */       c.info = this.info;
/*  948 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  953 */       if (!(o instanceof Map.Entry))
/*  954 */         return false; 
/*  955 */       Map.Entry<K, Integer> e = (Map.Entry<K, Integer>)o;
/*  956 */       return (Objects.equals(this.key, e.getKey()) && this.value == ((Integer)e.getValue()).intValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  960 */       return this.key.hashCode() ^ this.value;
/*      */     }
/*      */     
/*      */     public String toString() {
/*  964 */       return (new StringBuilder()).append(this.key).append("=>").append(this.value).toString();
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
/*  985 */     return (findKey((K)k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  989 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  993 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getInt(Object k) {
/*  998 */     Entry<K> e = findKey((K)k);
/*  999 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public K firstKey() {
/* 1003 */     if (this.tree == null)
/* 1004 */       throw new NoSuchElementException(); 
/* 1005 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public K lastKey() {
/* 1009 */     if (this.tree == null)
/* 1010 */       throw new NoSuchElementException(); 
/* 1011 */     return this.lastEntry.key;
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
/*      */     Object2IntAVLTreeMap.Entry<K> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2IntAVLTreeMap.Entry<K> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2IntAVLTreeMap.Entry<K> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1042 */     int index = 0;
/*      */     TreeIterator() {
/* 1044 */       this.next = Object2IntAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(K k) {
/* 1047 */       if ((this.next = Object2IntAVLTreeMap.this.locateKey(k)) != null)
/* 1048 */         if (Object2IntAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1049 */           this.prev = this.next;
/* 1050 */           this.next = this.next.next();
/*      */         } else {
/* 1052 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/* 1056 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1059 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1062 */       this.next = this.next.next();
/*      */     }
/*      */     Object2IntAVLTreeMap.Entry<K> nextEntry() {
/* 1065 */       if (!hasNext())
/* 1066 */         throw new NoSuchElementException(); 
/* 1067 */       this.curr = this.prev = this.next;
/* 1068 */       this.index++;
/* 1069 */       updateNext();
/* 1070 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1073 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Object2IntAVLTreeMap.Entry<K> previousEntry() {
/* 1076 */       if (!hasPrevious())
/* 1077 */         throw new NoSuchElementException(); 
/* 1078 */       this.curr = this.next = this.prev;
/* 1079 */       this.index--;
/* 1080 */       updatePrevious();
/* 1081 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1084 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1087 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1090 */       if (this.curr == null) {
/* 1091 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1096 */       if (this.curr == this.prev)
/* 1097 */         this.index--; 
/* 1098 */       this.next = this.prev = this.curr;
/* 1099 */       updatePrevious();
/* 1100 */       updateNext();
/* 1101 */       Object2IntAVLTreeMap.this.removeInt(this.curr.key);
/* 1102 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1105 */       int i = n;
/* 1106 */       while (i-- != 0 && hasNext())
/* 1107 */         nextEntry(); 
/* 1108 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1111 */       int i = n;
/* 1112 */       while (i-- != 0 && hasPrevious())
/* 1113 */         previousEntry(); 
/* 1114 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Object2IntMap.Entry<K>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(K k) {
/* 1127 */       super(k);
/*      */     }
/*      */     
/*      */     public Object2IntMap.Entry<K> next() {
/* 1131 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Object2IntMap.Entry<K> previous() {
/* 1135 */       return previousEntry();
/*      */     }
/*      */     
/*      */     public void set(Object2IntMap.Entry<K> ok) {
/* 1139 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Object2IntMap.Entry<K> ok) {
/* 1143 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
/* 1148 */     if (this.entries == null)
/* 1149 */       this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2IntMap.Entry<Object2IntMap.Entry<K>>>()
/*      */         {
/*      */           final Comparator<? super Object2IntMap.Entry<K>> comparator;
/*      */           
/*      */           public Comparator<? super Object2IntMap.Entry<K>> comparator() {
/* 1154 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2IntMap.Entry<K>> iterator() {
/* 1158 */             return new Object2IntAVLTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2IntMap.Entry<K>> iterator(Object2IntMap.Entry<K> from) {
/* 1162 */             return new Object2IntAVLTreeMap.EntryIterator(from.getKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1167 */             if (!(o instanceof Map.Entry))
/* 1168 */               return false; 
/* 1169 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1170 */             if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 1171 */               return false; 
/* 1172 */             Object2IntAVLTreeMap.Entry<K> f = Object2IntAVLTreeMap.this.findKey((K)e.getKey());
/* 1173 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1178 */             if (!(o instanceof Map.Entry))
/* 1179 */               return false; 
/* 1180 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1181 */             if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 1182 */               return false; 
/* 1183 */             Object2IntAVLTreeMap.Entry<K> f = Object2IntAVLTreeMap.this.findKey((K)e.getKey());
/* 1184 */             if (f == null || f.getIntValue() != ((Integer)e.getValue()).intValue())
/* 1185 */               return false; 
/* 1186 */             Object2IntAVLTreeMap.this.removeInt(f.key);
/* 1187 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1191 */             return Object2IntAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1195 */             Object2IntAVLTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Object2IntMap.Entry<K> first() {
/* 1199 */             return Object2IntAVLTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Object2IntMap.Entry<K> last() {
/* 1203 */             return Object2IntAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2IntMap.Entry<K>> subSet(Object2IntMap.Entry<K> from, Object2IntMap.Entry<K> to) {
/* 1208 */             return Object2IntAVLTreeMap.this.subMap(from.getKey(), to.getKey()).object2IntEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2IntMap.Entry<K>> headSet(Object2IntMap.Entry<K> to) {
/* 1212 */             return Object2IntAVLTreeMap.this.headMap(to.getKey()).object2IntEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2IntMap.Entry<K>> tailSet(Object2IntMap.Entry<K> from) {
/* 1216 */             return Object2IntAVLTreeMap.this.tailMap(from.getKey()).object2IntEntrySet();
/*      */           }
/*      */         }; 
/* 1219 */     return this.entries;
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
/* 1235 */       super(k);
/*      */     }
/*      */     
/*      */     public K next() {
/* 1239 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1243 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractObject2IntSortedMap<K>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1250 */       return new Object2IntAVLTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1254 */       return new Object2IntAVLTreeMap.KeyIterator(from);
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
/* 1269 */     if (this.keys == null)
/* 1270 */       this.keys = new KeySet(); 
/* 1271 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements IntListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1286 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public int previousInt() {
/* 1290 */       return (previousEntry()).value;
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
/*      */   public IntCollection values() {
/* 1305 */     if (this.values == null)
/* 1306 */       this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1309 */             return (IntIterator)new Object2IntAVLTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(int k) {
/* 1313 */             return Object2IntAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1317 */             return Object2IntAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1321 */             Object2IntAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1324 */     return this.values;
/*      */   }
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1328 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Object2IntSortedMap<K> headMap(K to) {
/* 1332 */     return new Submap(null, true, to, false);
/*      */   }
/*      */   
/*      */   public Object2IntSortedMap<K> tailMap(K from) {
/* 1336 */     return new Submap(from, false, null, true);
/*      */   }
/*      */   
/*      */   public Object2IntSortedMap<K> subMap(K from, K to) {
/* 1340 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractObject2IntSortedMap<K>
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
/*      */     protected transient ObjectSortedSet<Object2IntMap.Entry<K>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient IntCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(K from, boolean bottom, K to, boolean top) {
/* 1384 */       if (!bottom && !top && Object2IntAVLTreeMap.this.compare(from, to) > 0)
/* 1385 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1386 */       this.from = from;
/* 1387 */       this.bottom = bottom;
/* 1388 */       this.to = to;
/* 1389 */       this.top = top;
/* 1390 */       this.defRetValue = Object2IntAVLTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1394 */       SubmapIterator i = new SubmapIterator();
/* 1395 */       while (i.hasNext()) {
/* 1396 */         i.nextEntry();
/* 1397 */         i.remove();
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
/* 1408 */       return ((this.bottom || Object2IntAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Object2IntAVLTreeMap.this
/* 1409 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
/* 1413 */       if (this.entries == null)
/* 1414 */         this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2IntMap.Entry<Object2IntMap.Entry<K>>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Object2IntMap.Entry<K>> iterator() {
/* 1417 */               return new Object2IntAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Object2IntMap.Entry<K>> iterator(Object2IntMap.Entry<K> from) {
/* 1422 */               return new Object2IntAVLTreeMap.Submap.SubmapEntryIterator(from.getKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Object2IntMap.Entry<K>> comparator() {
/* 1426 */               return Object2IntAVLTreeMap.this.object2IntEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1431 */               if (!(o instanceof Map.Entry))
/* 1432 */                 return false; 
/* 1433 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1434 */               if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 1435 */                 return false; 
/* 1436 */               Object2IntAVLTreeMap.Entry<K> f = Object2IntAVLTreeMap.this.findKey((K)e.getKey());
/* 1437 */               return (f != null && Object2IntAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1442 */               if (!(o instanceof Map.Entry))
/* 1443 */                 return false; 
/* 1444 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1445 */               if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 1446 */                 return false; 
/* 1447 */               Object2IntAVLTreeMap.Entry<K> f = Object2IntAVLTreeMap.this.findKey((K)e.getKey());
/* 1448 */               if (f != null && Object2IntAVLTreeMap.Submap.this.in(f.key))
/* 1449 */                 Object2IntAVLTreeMap.Submap.this.removeInt(f.key); 
/* 1450 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1454 */               int c = 0;
/* 1455 */               for (Iterator<?> i = iterator(); i.hasNext(); i.next())
/* 1456 */                 c++; 
/* 1457 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1461 */               return !(new Object2IntAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1465 */               Object2IntAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Object2IntMap.Entry<K> first() {
/* 1469 */               return Object2IntAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Object2IntMap.Entry<K> last() {
/* 1473 */               return Object2IntAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2IntMap.Entry<K>> subSet(Object2IntMap.Entry<K> from, Object2IntMap.Entry<K> to) {
/* 1478 */               return Object2IntAVLTreeMap.Submap.this.subMap(from.getKey(), to.getKey()).object2IntEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2IntMap.Entry<K>> headSet(Object2IntMap.Entry<K> to) {
/* 1482 */               return Object2IntAVLTreeMap.Submap.this.headMap(to.getKey()).object2IntEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2IntMap.Entry<K>> tailSet(Object2IntMap.Entry<K> from) {
/* 1486 */               return Object2IntAVLTreeMap.Submap.this.tailMap(from.getKey()).object2IntEntrySet();
/*      */             }
/*      */           }; 
/* 1489 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractObject2IntSortedMap<K>.KeySet {
/*      */       public ObjectBidirectionalIterator<K> iterator() {
/* 1494 */         return new Object2IntAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1498 */         return new Object2IntAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> keySet() {
/* 1503 */       if (this.keys == null)
/* 1504 */         this.keys = new KeySet(); 
/* 1505 */       return this.keys;
/*      */     }
/*      */     
/*      */     public IntCollection values() {
/* 1509 */       if (this.values == null)
/* 1510 */         this.values = (IntCollection)new AbstractIntCollection()
/*      */           {
/*      */             public IntIterator iterator() {
/* 1513 */               return (IntIterator)new Object2IntAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(int k) {
/* 1517 */               return Object2IntAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1521 */               return Object2IntAVLTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1525 */               Object2IntAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1528 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object k) {
/* 1533 */       return (in((K)k) && Object2IntAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(int v) {
/* 1537 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1539 */       while (i.hasNext()) {
/* 1540 */         int ev = (i.nextEntry()).value;
/* 1541 */         if (ev == v)
/* 1542 */           return true; 
/*      */       } 
/* 1544 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getInt(Object k) {
/* 1550 */       K kk = (K)k; Object2IntAVLTreeMap.Entry<K> e;
/* 1551 */       return (in(kk) && (e = Object2IntAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int put(K k, int v) {
/* 1555 */       Object2IntAVLTreeMap.this.modified = false;
/* 1556 */       if (!in(k))
/* 1557 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1558 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1559 */       int oldValue = Object2IntAVLTreeMap.this.put(k, v);
/* 1560 */       return Object2IntAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public int removeInt(Object k) {
/* 1565 */       Object2IntAVLTreeMap.this.modified = false;
/* 1566 */       if (!in((K)k))
/* 1567 */         return this.defRetValue; 
/* 1568 */       int oldValue = Object2IntAVLTreeMap.this.removeInt(k);
/* 1569 */       return Object2IntAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1573 */       SubmapIterator i = new SubmapIterator();
/* 1574 */       int n = 0;
/* 1575 */       while (i.hasNext()) {
/* 1576 */         n++;
/* 1577 */         i.nextEntry();
/*      */       } 
/* 1579 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1583 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1587 */       return Object2IntAVLTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Object2IntSortedMap<K> headMap(K to) {
/* 1591 */       if (this.top)
/* 1592 */         return new Submap(this.from, this.bottom, to, false); 
/* 1593 */       return (Object2IntAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Object2IntSortedMap<K> tailMap(K from) {
/* 1597 */       if (this.bottom)
/* 1598 */         return new Submap(from, false, this.to, this.top); 
/* 1599 */       return (Object2IntAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Object2IntSortedMap<K> subMap(K from, K to) {
/* 1603 */       if (this.top && this.bottom)
/* 1604 */         return new Submap(from, false, to, false); 
/* 1605 */       if (!this.top)
/* 1606 */         to = (Object2IntAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1607 */       if (!this.bottom)
/* 1608 */         from = (Object2IntAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1609 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1610 */         return this; 
/* 1611 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2IntAVLTreeMap.Entry<K> firstEntry() {
/*      */       Object2IntAVLTreeMap.Entry<K> e;
/* 1620 */       if (Object2IntAVLTreeMap.this.tree == null) {
/* 1621 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1625 */       if (this.bottom) {
/* 1626 */         e = Object2IntAVLTreeMap.this.firstEntry;
/*      */       } else {
/* 1628 */         e = Object2IntAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1630 */         if (Object2IntAVLTreeMap.this.compare(e.key, this.from) < 0) {
/* 1631 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1635 */       if (e == null || (!this.top && Object2IntAVLTreeMap.this.compare(e.key, this.to) >= 0))
/* 1636 */         return null; 
/* 1637 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2IntAVLTreeMap.Entry<K> lastEntry() {
/*      */       Object2IntAVLTreeMap.Entry<K> e;
/* 1646 */       if (Object2IntAVLTreeMap.this.tree == null) {
/* 1647 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1651 */       if (this.top) {
/* 1652 */         e = Object2IntAVLTreeMap.this.lastEntry;
/*      */       } else {
/* 1654 */         e = Object2IntAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1656 */         if (Object2IntAVLTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1657 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1661 */       if (e == null || (!this.bottom && Object2IntAVLTreeMap.this.compare(e.key, this.from) < 0))
/* 1662 */         return null; 
/* 1663 */       return e;
/*      */     }
/*      */     
/*      */     public K firstKey() {
/* 1667 */       Object2IntAVLTreeMap.Entry<K> e = firstEntry();
/* 1668 */       if (e == null)
/* 1669 */         throw new NoSuchElementException(); 
/* 1670 */       return e.key;
/*      */     }
/*      */     
/*      */     public K lastKey() {
/* 1674 */       Object2IntAVLTreeMap.Entry<K> e = lastEntry();
/* 1675 */       if (e == null)
/* 1676 */         throw new NoSuchElementException(); 
/* 1677 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Object2IntAVLTreeMap<K>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1690 */         this.next = Object2IntAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(K k) {
/* 1693 */         this();
/* 1694 */         if (this.next != null)
/* 1695 */           if (!Object2IntAVLTreeMap.Submap.this.bottom && Object2IntAVLTreeMap.this.compare(k, this.next.key) < 0) {
/* 1696 */             this.prev = null;
/* 1697 */           } else if (!Object2IntAVLTreeMap.Submap.this.top && Object2IntAVLTreeMap.this.compare(k, (this.prev = Object2IntAVLTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1698 */             this.next = null;
/*      */           } else {
/* 1700 */             this.next = Object2IntAVLTreeMap.this.locateKey(k);
/* 1701 */             if (Object2IntAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1702 */               this.prev = this.next;
/* 1703 */               this.next = this.next.next();
/*      */             } else {
/* 1705 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1711 */         this.prev = this.prev.prev();
/* 1712 */         if (!Object2IntAVLTreeMap.Submap.this.bottom && this.prev != null && Object2IntAVLTreeMap.this.compare(this.prev.key, Object2IntAVLTreeMap.Submap.this.from) < 0)
/* 1713 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1717 */         this.next = this.next.next();
/* 1718 */         if (!Object2IntAVLTreeMap.Submap.this.top && this.next != null && Object2IntAVLTreeMap.this.compare(this.next.key, Object2IntAVLTreeMap.Submap.this.to) >= 0)
/* 1719 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Object2IntMap.Entry<K>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(K k) {
/* 1726 */         super(k);
/*      */       }
/*      */       
/*      */       public Object2IntMap.Entry<K> next() {
/* 1730 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Object2IntMap.Entry<K> previous() {
/* 1734 */         return previousEntry();
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
/* 1752 */         super(from);
/*      */       }
/*      */       
/*      */       public K next() {
/* 1756 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public K previous() {
/* 1760 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements IntListIterator
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public int nextInt() {
/* 1776 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public int previousInt() {
/* 1780 */         return (previousEntry()).value;
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
/*      */   public Object2IntAVLTreeMap<K> clone() {
/*      */     Object2IntAVLTreeMap<K> c;
/*      */     try {
/* 1799 */       c = (Object2IntAVLTreeMap<K>)super.clone();
/* 1800 */     } catch (CloneNotSupportedException cantHappen) {
/* 1801 */       throw new InternalError();
/*      */     } 
/* 1803 */     c.keys = null;
/* 1804 */     c.values = null;
/* 1805 */     c.entries = null;
/* 1806 */     c.allocatePaths();
/* 1807 */     if (this.count != 0) {
/*      */       
/* 1809 */       Entry<K> rp = new Entry<>(), rq = new Entry<>();
/* 1810 */       Entry<K> p = rp;
/* 1811 */       rp.left(this.tree);
/* 1812 */       Entry<K> q = rq;
/* 1813 */       rq.pred((Entry<K>)null);
/*      */       while (true) {
/* 1815 */         if (!p.pred()) {
/* 1816 */           Entry<K> e = p.left.clone();
/* 1817 */           e.pred(q.left);
/* 1818 */           e.succ(q);
/* 1819 */           q.left(e);
/* 1820 */           p = p.left;
/* 1821 */           q = q.left;
/*      */         } else {
/* 1823 */           while (p.succ()) {
/* 1824 */             p = p.right;
/* 1825 */             if (p == null) {
/* 1826 */               q.right = null;
/* 1827 */               c.tree = rq.left;
/* 1828 */               c.firstEntry = c.tree;
/* 1829 */               while (c.firstEntry.left != null)
/* 1830 */                 c.firstEntry = c.firstEntry.left; 
/* 1831 */               c.lastEntry = c.tree;
/* 1832 */               while (c.lastEntry.right != null)
/* 1833 */                 c.lastEntry = c.lastEntry.right; 
/* 1834 */               return c;
/*      */             } 
/* 1836 */             q = q.right;
/*      */           } 
/* 1838 */           p = p.right;
/* 1839 */           q = q.right;
/*      */         } 
/* 1841 */         if (!p.succ()) {
/* 1842 */           Entry<K> e = p.right.clone();
/* 1843 */           e.succ(q.right);
/* 1844 */           e.pred(q);
/* 1845 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1849 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1852 */     int n = this.count;
/* 1853 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1855 */     s.defaultWriteObject();
/* 1856 */     while (n-- != 0) {
/* 1857 */       Entry<K> e = i.nextEntry();
/* 1858 */       s.writeObject(e.key);
/* 1859 */       s.writeInt(e.value);
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
/* 1880 */     if (n == 1) {
/* 1881 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readInt());
/* 1882 */       entry.pred(pred);
/* 1883 */       entry.succ(succ);
/* 1884 */       return entry;
/*      */     } 
/* 1886 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1891 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readInt());
/* 1892 */       entry.right(new Entry<>((K)s.readObject(), s.readInt()));
/* 1893 */       entry.right.pred(entry);
/* 1894 */       entry.balance(1);
/* 1895 */       entry.pred(pred);
/* 1896 */       entry.right.succ(succ);
/* 1897 */       return entry;
/*      */     } 
/*      */     
/* 1900 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1901 */     Entry<K> top = new Entry<>();
/* 1902 */     top.left(readTree(s, leftN, pred, top));
/* 1903 */     top.key = (K)s.readObject();
/* 1904 */     top.value = s.readInt();
/* 1905 */     top.right(readTree(s, rightN, top, succ));
/* 1906 */     if (n == (n & -n))
/* 1907 */       top.balance(1); 
/* 1908 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1911 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1916 */     setActualComparator();
/* 1917 */     allocatePaths();
/* 1918 */     if (this.count != 0) {
/* 1919 */       this.tree = readTree(s, this.count, (Entry<K>)null, (Entry<K>)null);
/*      */       
/* 1921 */       Entry<K> e = this.tree;
/* 1922 */       while (e.left() != null)
/* 1923 */         e = e.left(); 
/* 1924 */       this.firstEntry = e;
/* 1925 */       e = this.tree;
/* 1926 */       while (e.right() != null)
/* 1927 */         e = e.right(); 
/* 1928 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2IntAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */