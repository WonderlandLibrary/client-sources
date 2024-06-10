/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
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
/*      */ public class Object2BooleanAVLTreeMap<K>
/*      */   extends AbstractObject2BooleanSortedMap<K>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<K> tree;
/*      */   protected int count;
/*      */   protected transient Entry<K> firstEntry;
/*      */   protected transient Entry<K> lastEntry;
/*      */   protected transient ObjectSortedSet<Object2BooleanMap.Entry<K>> entries;
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */   protected transient BooleanCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super K> storedComparator;
/*      */   protected transient Comparator<? super K> actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Object2BooleanAVLTreeMap() {
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
/*      */   public Object2BooleanAVLTreeMap(Comparator<? super K> c) {
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
/*      */   public Object2BooleanAVLTreeMap(Map<? extends K, ? extends Boolean> m) {
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
/*      */   public Object2BooleanAVLTreeMap(SortedMap<K, Boolean> m) {
/*  123 */     this(m.comparator());
/*  124 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanAVLTreeMap(Object2BooleanMap<? extends K> m) {
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
/*      */   public Object2BooleanAVLTreeMap(Object2BooleanSortedMap<K> m) {
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
/*      */   public Object2BooleanAVLTreeMap(K[] k, boolean[] v, Comparator<? super K> c) {
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
/*      */   public Object2BooleanAVLTreeMap(K[] k, boolean[] v) {
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
/*      */   public boolean put(K k, boolean v) {
/*  253 */     Entry<K> e = add(k);
/*  254 */     boolean oldValue = e.value;
/*  255 */     e.value = v;
/*  256 */     return oldValue;
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
/*  273 */     this.modified = false;
/*  274 */     Entry<K> e = null;
/*  275 */     if (this.tree == null) {
/*  276 */       this.count++;
/*  277 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*  278 */       this.modified = true;
/*      */     } else {
/*  280 */       Entry<K> p = this.tree, q = null, y = this.tree, z = null, w = null;
/*  281 */       int i = 0; while (true) {
/*      */         int cmp;
/*  283 */         if ((cmp = compare(k, p.key)) == 0) {
/*  284 */           return p;
/*      */         }
/*  286 */         if (p.balance() != 0) {
/*  287 */           i = 0;
/*  288 */           z = q;
/*  289 */           y = p;
/*      */         } 
/*  291 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  292 */           if (p.succ()) {
/*  293 */             this.count++;
/*  294 */             e = new Entry<>(k, this.defRetValue);
/*  295 */             this.modified = true;
/*  296 */             if (p.right == null)
/*  297 */               this.lastEntry = e; 
/*  298 */             e.left = p;
/*  299 */             e.right = p.right;
/*  300 */             p.right(e);
/*      */             break;
/*      */           } 
/*  303 */           q = p;
/*  304 */           p = p.right; continue;
/*      */         } 
/*  306 */         if (p.pred()) {
/*  307 */           this.count++;
/*  308 */           e = new Entry<>(k, this.defRetValue);
/*  309 */           this.modified = true;
/*  310 */           if (p.left == null)
/*  311 */             this.firstEntry = e; 
/*  312 */           e.right = p;
/*  313 */           e.left = p.left;
/*  314 */           p.left(e);
/*      */           break;
/*      */         } 
/*  317 */         q = p;
/*  318 */         p = p.left;
/*      */       } 
/*      */       
/*  321 */       p = y;
/*  322 */       i = 0;
/*  323 */       while (p != e) {
/*  324 */         if (this.dirPath[i]) {
/*  325 */           p.incBalance();
/*      */         } else {
/*  327 */           p.decBalance();
/*  328 */         }  p = this.dirPath[i++] ? p.right : p.left;
/*      */       } 
/*  330 */       if (y.balance() == -2) {
/*  331 */         Entry<K> x = y.left;
/*  332 */         if (x.balance() == -1) {
/*  333 */           w = x;
/*  334 */           if (x.succ()) {
/*  335 */             x.succ(false);
/*  336 */             y.pred(x);
/*      */           } else {
/*  338 */             y.left = x.right;
/*  339 */           }  x.right = y;
/*  340 */           x.balance(0);
/*  341 */           y.balance(0);
/*      */         } else {
/*  343 */           assert x.balance() == 1;
/*  344 */           w = x.right;
/*  345 */           x.right = w.left;
/*  346 */           w.left = x;
/*  347 */           y.left = w.right;
/*  348 */           w.right = y;
/*  349 */           if (w.balance() == -1) {
/*  350 */             x.balance(0);
/*  351 */             y.balance(1);
/*  352 */           } else if (w.balance() == 0) {
/*  353 */             x.balance(0);
/*  354 */             y.balance(0);
/*      */           } else {
/*  356 */             x.balance(-1);
/*  357 */             y.balance(0);
/*      */           } 
/*  359 */           w.balance(0);
/*  360 */           if (w.pred()) {
/*  361 */             x.succ(w);
/*  362 */             w.pred(false);
/*      */           } 
/*  364 */           if (w.succ()) {
/*  365 */             y.pred(w);
/*  366 */             w.succ(false);
/*      */           } 
/*      */         } 
/*  369 */       } else if (y.balance() == 2) {
/*  370 */         Entry<K> x = y.right;
/*  371 */         if (x.balance() == 1) {
/*  372 */           w = x;
/*  373 */           if (x.pred()) {
/*  374 */             x.pred(false);
/*  375 */             y.succ(x);
/*      */           } else {
/*  377 */             y.right = x.left;
/*  378 */           }  x.left = y;
/*  379 */           x.balance(0);
/*  380 */           y.balance(0);
/*      */         } else {
/*  382 */           assert x.balance() == -1;
/*  383 */           w = x.left;
/*  384 */           x.left = w.right;
/*  385 */           w.right = x;
/*  386 */           y.right = w.left;
/*  387 */           w.left = y;
/*  388 */           if (w.balance() == 1) {
/*  389 */             x.balance(0);
/*  390 */             y.balance(-1);
/*  391 */           } else if (w.balance() == 0) {
/*  392 */             x.balance(0);
/*  393 */             y.balance(0);
/*      */           } else {
/*  395 */             x.balance(1);
/*  396 */             y.balance(0);
/*      */           } 
/*  398 */           w.balance(0);
/*  399 */           if (w.pred()) {
/*  400 */             y.succ(w);
/*  401 */             w.pred(false);
/*      */           } 
/*  403 */           if (w.succ()) {
/*  404 */             x.pred(w);
/*  405 */             w.succ(false);
/*      */           } 
/*      */         } 
/*      */       } else {
/*  409 */         return e;
/*  410 */       }  if (z == null) {
/*  411 */         this.tree = w;
/*      */       }
/*  413 */       else if (z.left == y) {
/*  414 */         z.left = w;
/*      */       } else {
/*  416 */         z.right = w;
/*      */       } 
/*      */     } 
/*  419 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry<K> parent(Entry<K> e) {
/*  429 */     if (e == this.tree) {
/*  430 */       return null;
/*      */     }
/*  432 */     Entry<K> y = e, x = y;
/*      */     while (true) {
/*  434 */       if (y.succ()) {
/*  435 */         Entry<K> p = y.right;
/*  436 */         if (p == null || p.left != e) {
/*  437 */           while (!x.pred())
/*  438 */             x = x.left; 
/*  439 */           p = x.left;
/*      */         } 
/*  441 */         return p;
/*  442 */       }  if (x.pred()) {
/*  443 */         Entry<K> p = x.left;
/*  444 */         if (p == null || p.right != e) {
/*  445 */           while (!y.succ())
/*  446 */             y = y.right; 
/*  447 */           p = y.right;
/*      */         } 
/*  449 */         return p;
/*      */       } 
/*  451 */       x = x.left;
/*  452 */       y = y.right;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeBoolean(Object k) {
/*  462 */     this.modified = false;
/*  463 */     if (this.tree == null) {
/*  464 */       return this.defRetValue;
/*      */     }
/*  466 */     Entry<K> p = this.tree, q = null;
/*  467 */     boolean dir = false;
/*  468 */     K kk = (K)k;
/*      */     int cmp;
/*  470 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  472 */       if (dir = (cmp > 0)) {
/*  473 */         q = p;
/*  474 */         if ((p = p.right()) == null)
/*  475 */           return this.defRetValue;  continue;
/*      */       } 
/*  477 */       q = p;
/*  478 */       if ((p = p.left()) == null) {
/*  479 */         return this.defRetValue;
/*      */       }
/*      */     } 
/*  482 */     if (p.left == null)
/*  483 */       this.firstEntry = p.next(); 
/*  484 */     if (p.right == null)
/*  485 */       this.lastEntry = p.prev(); 
/*  486 */     if (p.succ())
/*  487 */     { if (p.pred())
/*  488 */       { if (q != null)
/*  489 */         { if (dir) {
/*  490 */             q.succ(p.right);
/*      */           } else {
/*  492 */             q.pred(p.left);
/*      */           }  }
/*  494 */         else { this.tree = dir ? p.right : p.left; }
/*      */          }
/*  496 */       else { (p.prev()).right = p.right;
/*  497 */         if (q != null)
/*  498 */         { if (dir) {
/*  499 */             q.right = p.left;
/*      */           } else {
/*  501 */             q.left = p.left;
/*      */           }  }
/*  503 */         else { this.tree = p.left; }
/*      */          }
/*      */        }
/*  506 */     else { Entry<K> r = p.right;
/*  507 */       if (r.pred()) {
/*  508 */         r.left = p.left;
/*  509 */         r.pred(p.pred());
/*  510 */         if (!r.pred())
/*  511 */           (r.prev()).right = r; 
/*  512 */         if (q != null)
/*  513 */         { if (dir) {
/*  514 */             q.right = r;
/*      */           } else {
/*  516 */             q.left = r;
/*      */           }  }
/*  518 */         else { this.tree = r; }
/*  519 */          r.balance(p.balance());
/*  520 */         q = r;
/*  521 */         dir = true;
/*      */       } else {
/*      */         Entry<K> s;
/*      */         while (true) {
/*  525 */           s = r.left;
/*  526 */           if (s.pred())
/*      */             break; 
/*  528 */           r = s;
/*      */         } 
/*  530 */         if (s.succ()) {
/*  531 */           r.pred(s);
/*      */         } else {
/*  533 */           r.left = s.right;
/*  534 */         }  s.left = p.left;
/*  535 */         if (!p.pred()) {
/*  536 */           (p.prev()).right = s;
/*  537 */           s.pred(false);
/*      */         } 
/*  539 */         s.right = p.right;
/*  540 */         s.succ(false);
/*  541 */         if (q != null)
/*  542 */         { if (dir) {
/*  543 */             q.right = s;
/*      */           } else {
/*  545 */             q.left = s;
/*      */           }  }
/*  547 */         else { this.tree = s; }
/*  548 */          s.balance(p.balance());
/*  549 */         q = r;
/*  550 */         dir = false;
/*      */       }  }
/*      */ 
/*      */     
/*  554 */     while (q != null) {
/*  555 */       Entry<K> y = q;
/*  556 */       q = parent(y);
/*  557 */       if (!dir) {
/*  558 */         dir = (q != null && q.left != y);
/*  559 */         y.incBalance();
/*  560 */         if (y.balance() == 1)
/*      */           break; 
/*  562 */         if (y.balance() == 2) {
/*  563 */           Entry<K> x = y.right;
/*  564 */           assert x != null;
/*  565 */           if (x.balance() == -1) {
/*      */             
/*  567 */             assert x.balance() == -1;
/*  568 */             Entry<K> w = x.left;
/*  569 */             x.left = w.right;
/*  570 */             w.right = x;
/*  571 */             y.right = w.left;
/*  572 */             w.left = y;
/*  573 */             if (w.balance() == 1) {
/*  574 */               x.balance(0);
/*  575 */               y.balance(-1);
/*  576 */             } else if (w.balance() == 0) {
/*  577 */               x.balance(0);
/*  578 */               y.balance(0);
/*      */             } else {
/*  580 */               assert w.balance() == -1;
/*  581 */               x.balance(1);
/*  582 */               y.balance(0);
/*      */             } 
/*  584 */             w.balance(0);
/*  585 */             if (w.pred()) {
/*  586 */               y.succ(w);
/*  587 */               w.pred(false);
/*      */             } 
/*  589 */             if (w.succ()) {
/*  590 */               x.pred(w);
/*  591 */               w.succ(false);
/*      */             } 
/*  593 */             if (q != null) {
/*  594 */               if (dir) {
/*  595 */                 q.right = w; continue;
/*      */               } 
/*  597 */               q.left = w; continue;
/*      */             } 
/*  599 */             this.tree = w; continue;
/*      */           } 
/*  601 */           if (q != null)
/*  602 */           { if (dir) {
/*  603 */               q.right = x;
/*      */             } else {
/*  605 */               q.left = x;
/*      */             }  }
/*  607 */           else { this.tree = x; }
/*  608 */            if (x.balance() == 0) {
/*  609 */             y.right = x.left;
/*  610 */             x.left = y;
/*  611 */             x.balance(-1);
/*  612 */             y.balance(1);
/*      */             break;
/*      */           } 
/*  615 */           assert x.balance() == 1;
/*  616 */           if (x.pred()) {
/*  617 */             y.succ(true);
/*  618 */             x.pred(false);
/*      */           } else {
/*  620 */             y.right = x.left;
/*  621 */           }  x.left = y;
/*  622 */           y.balance(0);
/*  623 */           x.balance(0);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  627 */       dir = (q != null && q.left != y);
/*  628 */       y.decBalance();
/*  629 */       if (y.balance() == -1)
/*      */         break; 
/*  631 */       if (y.balance() == -2) {
/*  632 */         Entry<K> x = y.left;
/*  633 */         assert x != null;
/*  634 */         if (x.balance() == 1) {
/*      */           
/*  636 */           assert x.balance() == 1;
/*  637 */           Entry<K> w = x.right;
/*  638 */           x.right = w.left;
/*  639 */           w.left = x;
/*  640 */           y.left = w.right;
/*  641 */           w.right = y;
/*  642 */           if (w.balance() == -1) {
/*  643 */             x.balance(0);
/*  644 */             y.balance(1);
/*  645 */           } else if (w.balance() == 0) {
/*  646 */             x.balance(0);
/*  647 */             y.balance(0);
/*      */           } else {
/*  649 */             assert w.balance() == 1;
/*  650 */             x.balance(-1);
/*  651 */             y.balance(0);
/*      */           } 
/*  653 */           w.balance(0);
/*  654 */           if (w.pred()) {
/*  655 */             x.succ(w);
/*  656 */             w.pred(false);
/*      */           } 
/*  658 */           if (w.succ()) {
/*  659 */             y.pred(w);
/*  660 */             w.succ(false);
/*      */           } 
/*  662 */           if (q != null) {
/*  663 */             if (dir) {
/*  664 */               q.right = w; continue;
/*      */             } 
/*  666 */             q.left = w; continue;
/*      */           } 
/*  668 */           this.tree = w; continue;
/*      */         } 
/*  670 */         if (q != null)
/*  671 */         { if (dir) {
/*  672 */             q.right = x;
/*      */           } else {
/*  674 */             q.left = x;
/*      */           }  }
/*  676 */         else { this.tree = x; }
/*  677 */          if (x.balance() == 0) {
/*  678 */           y.left = x.right;
/*  679 */           x.right = y;
/*  680 */           x.balance(1);
/*  681 */           y.balance(-1);
/*      */           break;
/*      */         } 
/*  684 */         assert x.balance() == -1;
/*  685 */         if (x.succ()) {
/*  686 */           y.pred(true);
/*  687 */           x.succ(false);
/*      */         } else {
/*  689 */           y.left = x.right;
/*  690 */         }  x.right = y;
/*  691 */         y.balance(0);
/*  692 */         x.balance(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  697 */     this.modified = true;
/*  698 */     this.count--;
/*  699 */     return p.value;
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  703 */     ValueIterator i = new ValueIterator();
/*      */     
/*  705 */     int j = this.count;
/*  706 */     while (j-- != 0) {
/*  707 */       boolean ev = i.nextBoolean();
/*  708 */       if (ev == v)
/*  709 */         return true; 
/*      */     } 
/*  711 */     return false;
/*      */   }
/*      */   
/*      */   public void clear() {
/*  715 */     this.count = 0;
/*  716 */     this.tree = null;
/*  717 */     this.entries = null;
/*  718 */     this.values = null;
/*  719 */     this.keys = null;
/*  720 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<K>
/*      */     extends AbstractObject2BooleanMap.BasicEntry<K>
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
/*  751 */       super((K)null, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(K k, boolean v) {
/*  762 */       super(k, v);
/*  763 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> left() {
/*  771 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> right() {
/*  779 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  787 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  795 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  804 */       if (pred) {
/*  805 */         this.info |= 0x40000000;
/*      */       } else {
/*  807 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  816 */       if (succ) {
/*  817 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  819 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<K> pred) {
/*  828 */       this.info |= 0x40000000;
/*  829 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<K> succ) {
/*  838 */       this.info |= Integer.MIN_VALUE;
/*  839 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<K> left) {
/*  848 */       this.info &= 0xBFFFFFFF;
/*  849 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<K> right) {
/*  858 */       this.info &= Integer.MAX_VALUE;
/*  859 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int balance() {
/*  867 */       return (byte)this.info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void balance(int level) {
/*  876 */       this.info &= 0xFFFFFF00;
/*  877 */       this.info |= level & 0xFF;
/*      */     }
/*      */     
/*      */     void incBalance() {
/*  881 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
/*      */     }
/*      */     
/*      */     protected void decBalance() {
/*  885 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> next() {
/*  893 */       Entry<K> next = this.right;
/*  894 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  895 */         while ((next.info & 0x40000000) == 0)
/*  896 */           next = next.left;  
/*  897 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> prev() {
/*  905 */       Entry<K> prev = this.left;
/*  906 */       if ((this.info & 0x40000000) == 0)
/*  907 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  908 */           prev = prev.right;  
/*  909 */       return prev;
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean value) {
/*  913 */       boolean oldValue = this.value;
/*  914 */       this.value = value;
/*  915 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry<K> clone() {
/*      */       Entry<K> c;
/*      */       try {
/*  922 */         c = (Entry<K>)super.clone();
/*  923 */       } catch (CloneNotSupportedException cantHappen) {
/*  924 */         throw new InternalError();
/*      */       } 
/*  926 */       c.key = this.key;
/*  927 */       c.value = this.value;
/*  928 */       c.info = this.info;
/*  929 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  934 */       if (!(o instanceof Map.Entry))
/*  935 */         return false; 
/*  936 */       Map.Entry<K, Boolean> e = (Map.Entry<K, Boolean>)o;
/*  937 */       return (Objects.equals(this.key, e.getKey()) && this.value == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  941 */       return this.key.hashCode() ^ (this.value ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  945 */       return (new StringBuilder()).append(this.key).append("=>").append(this.value).toString();
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
/*  966 */     return (findKey((K)k) != null);
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
/*      */   public boolean getBoolean(Object k) {
/*  979 */     Entry<K> e = findKey((K)k);
/*  980 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public K firstKey() {
/*  984 */     if (this.tree == null)
/*  985 */       throw new NoSuchElementException(); 
/*  986 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public K lastKey() {
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
/*      */     Object2BooleanAVLTreeMap.Entry<K> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2BooleanAVLTreeMap.Entry<K> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2BooleanAVLTreeMap.Entry<K> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1023 */     int index = 0;
/*      */     TreeIterator() {
/* 1025 */       this.next = Object2BooleanAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(K k) {
/* 1028 */       if ((this.next = Object2BooleanAVLTreeMap.this.locateKey(k)) != null)
/* 1029 */         if (Object2BooleanAVLTreeMap.this.compare(this.next.key, k) <= 0) {
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
/*      */     Object2BooleanAVLTreeMap.Entry<K> nextEntry() {
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
/*      */     Object2BooleanAVLTreeMap.Entry<K> previousEntry() {
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
/* 1082 */       Object2BooleanAVLTreeMap.this.removeBoolean(this.curr.key);
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
/*      */     implements ObjectListIterator<Object2BooleanMap.Entry<K>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(K k) {
/* 1108 */       super(k);
/*      */     }
/*      */     
/*      */     public Object2BooleanMap.Entry<K> next() {
/* 1112 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Object2BooleanMap.Entry<K> previous() {
/* 1116 */       return previousEntry();
/*      */     }
/*      */     
/*      */     public void set(Object2BooleanMap.Entry<K> ok) {
/* 1120 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Object2BooleanMap.Entry<K> ok) {
/* 1124 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Object2BooleanMap.Entry<K>> object2BooleanEntrySet() {
/* 1129 */     if (this.entries == null)
/* 1130 */       this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2BooleanMap.Entry<Object2BooleanMap.Entry<K>>>()
/*      */         {
/*      */           final Comparator<? super Object2BooleanMap.Entry<K>> comparator;
/*      */           
/*      */           public Comparator<? super Object2BooleanMap.Entry<K>> comparator() {
/* 1135 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> iterator() {
/* 1139 */             return new Object2BooleanAVLTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> iterator(Object2BooleanMap.Entry<K> from) {
/* 1144 */             return new Object2BooleanAVLTreeMap.EntryIterator(from.getKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1149 */             if (!(o instanceof Map.Entry))
/* 1150 */               return false; 
/* 1151 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1152 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1153 */               return false; 
/* 1154 */             Object2BooleanAVLTreeMap.Entry<K> f = Object2BooleanAVLTreeMap.this.findKey((K)e.getKey());
/* 1155 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1160 */             if (!(o instanceof Map.Entry))
/* 1161 */               return false; 
/* 1162 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1163 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1164 */               return false; 
/* 1165 */             Object2BooleanAVLTreeMap.Entry<K> f = Object2BooleanAVLTreeMap.this.findKey((K)e.getKey());
/* 1166 */             if (f == null || f.getBooleanValue() != ((Boolean)e.getValue()).booleanValue())
/* 1167 */               return false; 
/* 1168 */             Object2BooleanAVLTreeMap.this.removeBoolean(f.key);
/* 1169 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1173 */             return Object2BooleanAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1177 */             Object2BooleanAVLTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Object2BooleanMap.Entry<K> first() {
/* 1181 */             return Object2BooleanAVLTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Object2BooleanMap.Entry<K> last() {
/* 1185 */             return Object2BooleanAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2BooleanMap.Entry<K>> subSet(Object2BooleanMap.Entry<K> from, Object2BooleanMap.Entry<K> to) {
/* 1190 */             return Object2BooleanAVLTreeMap.this.subMap(from.getKey(), to.getKey()).object2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2BooleanMap.Entry<K>> headSet(Object2BooleanMap.Entry<K> to) {
/* 1194 */             return Object2BooleanAVLTreeMap.this.headMap(to.getKey()).object2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Object2BooleanMap.Entry<K>> tailSet(Object2BooleanMap.Entry<K> from) {
/* 1198 */             return Object2BooleanAVLTreeMap.this.tailMap(from.getKey()).object2BooleanEntrySet();
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
/*      */     implements ObjectListIterator<K>
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(K k) {
/* 1217 */       super(k);
/*      */     }
/*      */     
/*      */     public K next() {
/* 1221 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1225 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractObject2BooleanSortedMap<K>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1232 */       return new Object2BooleanAVLTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1236 */       return new Object2BooleanAVLTreeMap.KeyIterator(from);
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
/* 1291 */             return (BooleanIterator)new Object2BooleanAVLTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(boolean k) {
/* 1295 */             return Object2BooleanAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1299 */             return Object2BooleanAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1303 */             Object2BooleanAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1306 */     return this.values;
/*      */   }
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1310 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Object2BooleanSortedMap<K> headMap(K to) {
/* 1314 */     return new Submap(null, true, to, false);
/*      */   }
/*      */   
/*      */   public Object2BooleanSortedMap<K> tailMap(K from) {
/* 1318 */     return new Submap(from, false, null, true);
/*      */   }
/*      */   
/*      */   public Object2BooleanSortedMap<K> subMap(K from, K to) {
/* 1322 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractObject2BooleanSortedMap<K>
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
/*      */     protected transient ObjectSortedSet<Object2BooleanMap.Entry<K>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(K from, boolean bottom, K to, boolean top) {
/* 1366 */       if (!bottom && !top && Object2BooleanAVLTreeMap.this.compare(from, to) > 0)
/* 1367 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1368 */       this.from = from;
/* 1369 */       this.bottom = bottom;
/* 1370 */       this.to = to;
/* 1371 */       this.top = top;
/* 1372 */       this.defRetValue = Object2BooleanAVLTreeMap.this.defRetValue;
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
/*      */     final boolean in(K k) {
/* 1390 */       return ((this.bottom || Object2BooleanAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Object2BooleanAVLTreeMap.this
/* 1391 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> object2BooleanEntrySet() {
/* 1395 */       if (this.entries == null)
/* 1396 */         this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2BooleanMap.Entry<Object2BooleanMap.Entry<K>>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> iterator() {
/* 1399 */               return new Object2BooleanAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> iterator(Object2BooleanMap.Entry<K> from) {
/* 1404 */               return new Object2BooleanAVLTreeMap.Submap.SubmapEntryIterator(from.getKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Object2BooleanMap.Entry<K>> comparator() {
/* 1408 */               return Object2BooleanAVLTreeMap.this.object2BooleanEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1413 */               if (!(o instanceof Map.Entry))
/* 1414 */                 return false; 
/* 1415 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1416 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1417 */                 return false; 
/* 1418 */               Object2BooleanAVLTreeMap.Entry<K> f = Object2BooleanAVLTreeMap.this.findKey((K)e.getKey());
/* 1419 */               return (f != null && Object2BooleanAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1424 */               if (!(o instanceof Map.Entry))
/* 1425 */                 return false; 
/* 1426 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1427 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1428 */                 return false; 
/* 1429 */               Object2BooleanAVLTreeMap.Entry<K> f = Object2BooleanAVLTreeMap.this.findKey((K)e.getKey());
/* 1430 */               if (f != null && Object2BooleanAVLTreeMap.Submap.this.in(f.key))
/* 1431 */                 Object2BooleanAVLTreeMap.Submap.this.removeBoolean(f.key); 
/* 1432 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1436 */               int c = 0;
/* 1437 */               for (Iterator<?> i = iterator(); i.hasNext(); i.next())
/* 1438 */                 c++; 
/* 1439 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1443 */               return !(new Object2BooleanAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1447 */               Object2BooleanAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Object2BooleanMap.Entry<K> first() {
/* 1451 */               return Object2BooleanAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Object2BooleanMap.Entry<K> last() {
/* 1455 */               return Object2BooleanAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2BooleanMap.Entry<K>> subSet(Object2BooleanMap.Entry<K> from, Object2BooleanMap.Entry<K> to) {
/* 1460 */               return Object2BooleanAVLTreeMap.Submap.this.subMap(from.getKey(), to.getKey()).object2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2BooleanMap.Entry<K>> headSet(Object2BooleanMap.Entry<K> to) {
/* 1464 */               return Object2BooleanAVLTreeMap.Submap.this.headMap(to.getKey()).object2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Object2BooleanMap.Entry<K>> tailSet(Object2BooleanMap.Entry<K> from) {
/* 1468 */               return Object2BooleanAVLTreeMap.Submap.this.tailMap(from.getKey()).object2BooleanEntrySet();
/*      */             }
/*      */           }; 
/* 1471 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractObject2BooleanSortedMap<K>.KeySet {
/*      */       public ObjectBidirectionalIterator<K> iterator() {
/* 1476 */         return new Object2BooleanAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1480 */         return new Object2BooleanAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> keySet() {
/* 1485 */       if (this.keys == null)
/* 1486 */         this.keys = new KeySet(); 
/* 1487 */       return this.keys;
/*      */     }
/*      */     
/*      */     public BooleanCollection values() {
/* 1491 */       if (this.values == null)
/* 1492 */         this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */           {
/*      */             public BooleanIterator iterator() {
/* 1495 */               return (BooleanIterator)new Object2BooleanAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(boolean k) {
/* 1499 */               return Object2BooleanAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1503 */               return Object2BooleanAVLTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1507 */               Object2BooleanAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1510 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object k) {
/* 1515 */       return (in((K)k) && Object2BooleanAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(boolean v) {
/* 1519 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1521 */       while (i.hasNext()) {
/* 1522 */         boolean ev = (i.nextEntry()).value;
/* 1523 */         if (ev == v)
/* 1524 */           return true; 
/*      */       } 
/* 1526 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean getBoolean(Object k) {
/* 1532 */       K kk = (K)k; Object2BooleanAVLTreeMap.Entry<K> e;
/* 1533 */       return (in(kk) && (e = Object2BooleanAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public boolean put(K k, boolean v) {
/* 1537 */       Object2BooleanAVLTreeMap.this.modified = false;
/* 1538 */       if (!in(k))
/* 1539 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1540 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1541 */       boolean oldValue = Object2BooleanAVLTreeMap.this.put(k, v);
/* 1542 */       return Object2BooleanAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean removeBoolean(Object k) {
/* 1547 */       Object2BooleanAVLTreeMap.this.modified = false;
/* 1548 */       if (!in((K)k))
/* 1549 */         return this.defRetValue; 
/* 1550 */       boolean oldValue = Object2BooleanAVLTreeMap.this.removeBoolean(k);
/* 1551 */       return Object2BooleanAVLTreeMap.this.modified ? oldValue : this.defRetValue;
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
/*      */     public Comparator<? super K> comparator() {
/* 1569 */       return Object2BooleanAVLTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Object2BooleanSortedMap<K> headMap(K to) {
/* 1573 */       if (this.top)
/* 1574 */         return new Submap(this.from, this.bottom, to, false); 
/* 1575 */       return (Object2BooleanAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Object2BooleanSortedMap<K> tailMap(K from) {
/* 1579 */       if (this.bottom)
/* 1580 */         return new Submap(from, false, this.to, this.top); 
/* 1581 */       return (Object2BooleanAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Object2BooleanSortedMap<K> subMap(K from, K to) {
/* 1585 */       if (this.top && this.bottom)
/* 1586 */         return new Submap(from, false, to, false); 
/* 1587 */       if (!this.top)
/* 1588 */         to = (Object2BooleanAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1589 */       if (!this.bottom)
/* 1590 */         from = (Object2BooleanAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1591 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1592 */         return this; 
/* 1593 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2BooleanAVLTreeMap.Entry<K> firstEntry() {
/*      */       Object2BooleanAVLTreeMap.Entry<K> e;
/* 1602 */       if (Object2BooleanAVLTreeMap.this.tree == null) {
/* 1603 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1607 */       if (this.bottom) {
/* 1608 */         e = Object2BooleanAVLTreeMap.this.firstEntry;
/*      */       } else {
/* 1610 */         e = Object2BooleanAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1612 */         if (Object2BooleanAVLTreeMap.this.compare(e.key, this.from) < 0) {
/* 1613 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1617 */       if (e == null || (!this.top && Object2BooleanAVLTreeMap.this.compare(e.key, this.to) >= 0))
/* 1618 */         return null; 
/* 1619 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2BooleanAVLTreeMap.Entry<K> lastEntry() {
/*      */       Object2BooleanAVLTreeMap.Entry<K> e;
/* 1628 */       if (Object2BooleanAVLTreeMap.this.tree == null) {
/* 1629 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1633 */       if (this.top) {
/* 1634 */         e = Object2BooleanAVLTreeMap.this.lastEntry;
/*      */       } else {
/* 1636 */         e = Object2BooleanAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1638 */         if (Object2BooleanAVLTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1639 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1643 */       if (e == null || (!this.bottom && Object2BooleanAVLTreeMap.this.compare(e.key, this.from) < 0))
/* 1644 */         return null; 
/* 1645 */       return e;
/*      */     }
/*      */     
/*      */     public K firstKey() {
/* 1649 */       Object2BooleanAVLTreeMap.Entry<K> e = firstEntry();
/* 1650 */       if (e == null)
/* 1651 */         throw new NoSuchElementException(); 
/* 1652 */       return e.key;
/*      */     }
/*      */     
/*      */     public K lastKey() {
/* 1656 */       Object2BooleanAVLTreeMap.Entry<K> e = lastEntry();
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
/*      */       extends Object2BooleanAVLTreeMap<K>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1672 */         this.next = Object2BooleanAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(K k) {
/* 1675 */         this();
/* 1676 */         if (this.next != null)
/* 1677 */           if (!Object2BooleanAVLTreeMap.Submap.this.bottom && Object2BooleanAVLTreeMap.this.compare(k, this.next.key) < 0) {
/* 1678 */             this.prev = null;
/* 1679 */           } else if (!Object2BooleanAVLTreeMap.Submap.this.top && Object2BooleanAVLTreeMap.this.compare(k, (this.prev = Object2BooleanAVLTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1680 */             this.next = null;
/*      */           } else {
/* 1682 */             this.next = Object2BooleanAVLTreeMap.this.locateKey(k);
/* 1683 */             if (Object2BooleanAVLTreeMap.this.compare(this.next.key, k) <= 0) {
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
/* 1694 */         if (!Object2BooleanAVLTreeMap.Submap.this.bottom && this.prev != null && Object2BooleanAVLTreeMap.this.compare(this.prev.key, Object2BooleanAVLTreeMap.Submap.this.from) < 0)
/* 1695 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1699 */         this.next = this.next.next();
/* 1700 */         if (!Object2BooleanAVLTreeMap.Submap.this.top && this.next != null && Object2BooleanAVLTreeMap.this.compare(this.next.key, Object2BooleanAVLTreeMap.Submap.this.to) >= 0)
/* 1701 */           this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Object2BooleanMap.Entry<K>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(K k) {
/* 1710 */         super(k);
/*      */       }
/*      */       
/*      */       public Object2BooleanMap.Entry<K> next() {
/* 1714 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Object2BooleanMap.Entry<K> previous() {
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
/*      */       implements ObjectListIterator<K>
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(K from) {
/* 1736 */         super(from);
/*      */       }
/*      */       
/*      */       public K next() {
/* 1740 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public K previous() {
/* 1744 */         return (previousEntry()).key;
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
/* 1760 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public boolean previousBoolean() {
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
/*      */   public Object2BooleanAVLTreeMap<K> clone() {
/*      */     Object2BooleanAVLTreeMap<K> c;
/*      */     try {
/* 1783 */       c = (Object2BooleanAVLTreeMap<K>)super.clone();
/* 1784 */     } catch (CloneNotSupportedException cantHappen) {
/* 1785 */       throw new InternalError();
/*      */     } 
/* 1787 */     c.keys = null;
/* 1788 */     c.values = null;
/* 1789 */     c.entries = null;
/* 1790 */     c.allocatePaths();
/* 1791 */     if (this.count != 0) {
/*      */       
/* 1793 */       Entry<K> rp = new Entry<>(), rq = new Entry<>();
/* 1794 */       Entry<K> p = rp;
/* 1795 */       rp.left(this.tree);
/* 1796 */       Entry<K> q = rq;
/* 1797 */       rq.pred((Entry<K>)null);
/*      */       while (true) {
/* 1799 */         if (!p.pred()) {
/* 1800 */           Entry<K> e = p.left.clone();
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
/* 1826 */           Entry<K> e = p.right.clone();
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
/* 1841 */       Entry<K> e = i.nextEntry();
/* 1842 */       s.writeObject(e.key);
/* 1843 */       s.writeBoolean(e.value);
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
/* 1864 */     if (n == 1) {
/* 1865 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readBoolean());
/* 1866 */       entry.pred(pred);
/* 1867 */       entry.succ(succ);
/* 1868 */       return entry;
/*      */     } 
/* 1870 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1875 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readBoolean());
/* 1876 */       entry.right(new Entry<>((K)s.readObject(), s.readBoolean()));
/* 1877 */       entry.right.pred(entry);
/* 1878 */       entry.balance(1);
/* 1879 */       entry.pred(pred);
/* 1880 */       entry.right.succ(succ);
/* 1881 */       return entry;
/*      */     } 
/*      */     
/* 1884 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1885 */     Entry<K> top = new Entry<>();
/* 1886 */     top.left(readTree(s, leftN, pred, top));
/* 1887 */     top.key = (K)s.readObject();
/* 1888 */     top.value = s.readBoolean();
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
/* 1903 */       this.tree = readTree(s, this.count, (Entry<K>)null, (Entry<K>)null);
/*      */       
/* 1905 */       Entry<K> e = this.tree;
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2BooleanAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */