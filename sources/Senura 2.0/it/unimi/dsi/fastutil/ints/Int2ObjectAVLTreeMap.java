/*      */ package it.unimi.dsi.fastutil.ints;
/*      */ 
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
/*      */ public class Int2ObjectAVLTreeMap<V>
/*      */   extends AbstractInt2ObjectSortedMap<V>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<V> tree;
/*      */   protected int count;
/*      */   protected transient Entry<V> firstEntry;
/*      */   protected transient Entry<V> lastEntry;
/*      */   protected transient ObjectSortedSet<Int2ObjectMap.Entry<V>> entries;
/*      */   protected transient IntSortedSet keys;
/*      */   protected transient ObjectCollection<V> values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Integer> storedComparator;
/*      */   protected transient IntComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Int2ObjectAVLTreeMap() {
/*   69 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   75 */     this.tree = null;
/*   76 */     this.count = 0;
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
/*   88 */     this.actualComparator = IntComparators.asIntComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ObjectAVLTreeMap(Comparator<? super Integer> c) {
/*   97 */     this();
/*   98 */     this.storedComparator = c;
/*   99 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ObjectAVLTreeMap(Map<? extends Integer, ? extends V> m) {
/*  108 */     this();
/*  109 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ObjectAVLTreeMap(SortedMap<Integer, V> m) {
/*  119 */     this(m.comparator());
/*  120 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ObjectAVLTreeMap(Int2ObjectMap<? extends V> m) {
/*  129 */     this();
/*  130 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ObjectAVLTreeMap(Int2ObjectSortedMap<V> m) {
/*  140 */     this(m.comparator());
/*  141 */     putAll(m);
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
/*      */   public Int2ObjectAVLTreeMap(int[] k, V[] v, Comparator<? super Integer> c) {
/*  157 */     this(c);
/*  158 */     if (k.length != v.length) {
/*  159 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  161 */     for (int i = 0; i < k.length; i++) {
/*  162 */       put(k[i], v[i]);
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
/*      */   public Int2ObjectAVLTreeMap(int[] k, V[] v) {
/*  175 */     this(k, v, (Comparator<? super Integer>)null);
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
/*      */   final int compare(int k1, int k2) {
/*  203 */     return (this.actualComparator == null) ? Integer.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry<V> findKey(int k) {
/*  215 */     Entry<V> e = this.tree;
/*      */     int cmp;
/*  217 */     while (e != null && (cmp = compare(k, e.key)) != 0)
/*  218 */       e = (cmp < 0) ? e.left() : e.right(); 
/*  219 */     return e;
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
/*      */   final Entry<V> locateKey(int k) {
/*  231 */     Entry<V> e = this.tree, last = this.tree;
/*  232 */     int cmp = 0;
/*  233 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  234 */       last = e;
/*  235 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  237 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  245 */     this.dirPath = new boolean[48];
/*      */   }
/*      */   
/*      */   public V put(int k, V v) {
/*  249 */     Entry<V> e = add(k);
/*  250 */     V oldValue = e.value;
/*  251 */     e.value = v;
/*  252 */     return oldValue;
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
/*      */   private Entry<V> add(int k) {
/*  269 */     this.modified = false;
/*  270 */     Entry<V> e = null;
/*  271 */     if (this.tree == null) {
/*  272 */       this.count++;
/*  273 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*  274 */       this.modified = true;
/*      */     } else {
/*  276 */       Entry<V> p = this.tree, q = null, y = this.tree, z = null, w = null;
/*  277 */       int i = 0; while (true) {
/*      */         int cmp;
/*  279 */         if ((cmp = compare(k, p.key)) == 0) {
/*  280 */           return p;
/*      */         }
/*  282 */         if (p.balance() != 0) {
/*  283 */           i = 0;
/*  284 */           z = q;
/*  285 */           y = p;
/*      */         } 
/*  287 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  288 */           if (p.succ()) {
/*  289 */             this.count++;
/*  290 */             e = new Entry<>(k, this.defRetValue);
/*  291 */             this.modified = true;
/*  292 */             if (p.right == null)
/*  293 */               this.lastEntry = e; 
/*  294 */             e.left = p;
/*  295 */             e.right = p.right;
/*  296 */             p.right(e);
/*      */             break;
/*      */           } 
/*  299 */           q = p;
/*  300 */           p = p.right; continue;
/*      */         } 
/*  302 */         if (p.pred()) {
/*  303 */           this.count++;
/*  304 */           e = new Entry<>(k, this.defRetValue);
/*  305 */           this.modified = true;
/*  306 */           if (p.left == null)
/*  307 */             this.firstEntry = e; 
/*  308 */           e.right = p;
/*  309 */           e.left = p.left;
/*  310 */           p.left(e);
/*      */           break;
/*      */         } 
/*  313 */         q = p;
/*  314 */         p = p.left;
/*      */       } 
/*      */       
/*  317 */       p = y;
/*  318 */       i = 0;
/*  319 */       while (p != e) {
/*  320 */         if (this.dirPath[i]) {
/*  321 */           p.incBalance();
/*      */         } else {
/*  323 */           p.decBalance();
/*  324 */         }  p = this.dirPath[i++] ? p.right : p.left;
/*      */       } 
/*  326 */       if (y.balance() == -2) {
/*  327 */         Entry<V> x = y.left;
/*  328 */         if (x.balance() == -1) {
/*  329 */           w = x;
/*  330 */           if (x.succ()) {
/*  331 */             x.succ(false);
/*  332 */             y.pred(x);
/*      */           } else {
/*  334 */             y.left = x.right;
/*  335 */           }  x.right = y;
/*  336 */           x.balance(0);
/*  337 */           y.balance(0);
/*      */         } else {
/*  339 */           assert x.balance() == 1;
/*  340 */           w = x.right;
/*  341 */           x.right = w.left;
/*  342 */           w.left = x;
/*  343 */           y.left = w.right;
/*  344 */           w.right = y;
/*  345 */           if (w.balance() == -1) {
/*  346 */             x.balance(0);
/*  347 */             y.balance(1);
/*  348 */           } else if (w.balance() == 0) {
/*  349 */             x.balance(0);
/*  350 */             y.balance(0);
/*      */           } else {
/*  352 */             x.balance(-1);
/*  353 */             y.balance(0);
/*      */           } 
/*  355 */           w.balance(0);
/*  356 */           if (w.pred()) {
/*  357 */             x.succ(w);
/*  358 */             w.pred(false);
/*      */           } 
/*  360 */           if (w.succ()) {
/*  361 */             y.pred(w);
/*  362 */             w.succ(false);
/*      */           } 
/*      */         } 
/*  365 */       } else if (y.balance() == 2) {
/*  366 */         Entry<V> x = y.right;
/*  367 */         if (x.balance() == 1) {
/*  368 */           w = x;
/*  369 */           if (x.pred()) {
/*  370 */             x.pred(false);
/*  371 */             y.succ(x);
/*      */           } else {
/*  373 */             y.right = x.left;
/*  374 */           }  x.left = y;
/*  375 */           x.balance(0);
/*  376 */           y.balance(0);
/*      */         } else {
/*  378 */           assert x.balance() == -1;
/*  379 */           w = x.left;
/*  380 */           x.left = w.right;
/*  381 */           w.right = x;
/*  382 */           y.right = w.left;
/*  383 */           w.left = y;
/*  384 */           if (w.balance() == 1) {
/*  385 */             x.balance(0);
/*  386 */             y.balance(-1);
/*  387 */           } else if (w.balance() == 0) {
/*  388 */             x.balance(0);
/*  389 */             y.balance(0);
/*      */           } else {
/*  391 */             x.balance(1);
/*  392 */             y.balance(0);
/*      */           } 
/*  394 */           w.balance(0);
/*  395 */           if (w.pred()) {
/*  396 */             y.succ(w);
/*  397 */             w.pred(false);
/*      */           } 
/*  399 */           if (w.succ()) {
/*  400 */             x.pred(w);
/*  401 */             w.succ(false);
/*      */           } 
/*      */         } 
/*      */       } else {
/*  405 */         return e;
/*  406 */       }  if (z == null) {
/*  407 */         this.tree = w;
/*      */       }
/*  409 */       else if (z.left == y) {
/*  410 */         z.left = w;
/*      */       } else {
/*  412 */         z.right = w;
/*      */       } 
/*      */     } 
/*  415 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry<V> parent(Entry<V> e) {
/*  425 */     if (e == this.tree) {
/*  426 */       return null;
/*      */     }
/*  428 */     Entry<V> y = e, x = y;
/*      */     while (true) {
/*  430 */       if (y.succ()) {
/*  431 */         Entry<V> p = y.right;
/*  432 */         if (p == null || p.left != e) {
/*  433 */           while (!x.pred())
/*  434 */             x = x.left; 
/*  435 */           p = x.left;
/*      */         } 
/*  437 */         return p;
/*  438 */       }  if (x.pred()) {
/*  439 */         Entry<V> p = x.left;
/*  440 */         if (p == null || p.right != e) {
/*  441 */           while (!y.succ())
/*  442 */             y = y.right; 
/*  443 */           p = y.right;
/*      */         } 
/*  445 */         return p;
/*      */       } 
/*  447 */       x = x.left;
/*  448 */       y = y.right;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V remove(int k) {
/*  458 */     this.modified = false;
/*  459 */     if (this.tree == null) {
/*  460 */       return this.defRetValue;
/*      */     }
/*  462 */     Entry<V> p = this.tree, q = null;
/*  463 */     boolean dir = false;
/*  464 */     int kk = k;
/*      */     int cmp;
/*  466 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  468 */       if (dir = (cmp > 0)) {
/*  469 */         q = p;
/*  470 */         if ((p = p.right()) == null)
/*  471 */           return this.defRetValue;  continue;
/*      */       } 
/*  473 */       q = p;
/*  474 */       if ((p = p.left()) == null) {
/*  475 */         return this.defRetValue;
/*      */       }
/*      */     } 
/*  478 */     if (p.left == null)
/*  479 */       this.firstEntry = p.next(); 
/*  480 */     if (p.right == null)
/*  481 */       this.lastEntry = p.prev(); 
/*  482 */     if (p.succ())
/*  483 */     { if (p.pred())
/*  484 */       { if (q != null)
/*  485 */         { if (dir) {
/*  486 */             q.succ(p.right);
/*      */           } else {
/*  488 */             q.pred(p.left);
/*      */           }  }
/*  490 */         else { this.tree = dir ? p.right : p.left; }
/*      */          }
/*  492 */       else { (p.prev()).right = p.right;
/*  493 */         if (q != null)
/*  494 */         { if (dir) {
/*  495 */             q.right = p.left;
/*      */           } else {
/*  497 */             q.left = p.left;
/*      */           }  }
/*  499 */         else { this.tree = p.left; }
/*      */          }
/*      */        }
/*  502 */     else { Entry<V> r = p.right;
/*  503 */       if (r.pred()) {
/*  504 */         r.left = p.left;
/*  505 */         r.pred(p.pred());
/*  506 */         if (!r.pred())
/*  507 */           (r.prev()).right = r; 
/*  508 */         if (q != null)
/*  509 */         { if (dir) {
/*  510 */             q.right = r;
/*      */           } else {
/*  512 */             q.left = r;
/*      */           }  }
/*  514 */         else { this.tree = r; }
/*  515 */          r.balance(p.balance());
/*  516 */         q = r;
/*  517 */         dir = true;
/*      */       } else {
/*      */         Entry<V> s;
/*      */         while (true) {
/*  521 */           s = r.left;
/*  522 */           if (s.pred())
/*      */             break; 
/*  524 */           r = s;
/*      */         } 
/*  526 */         if (s.succ()) {
/*  527 */           r.pred(s);
/*      */         } else {
/*  529 */           r.left = s.right;
/*  530 */         }  s.left = p.left;
/*  531 */         if (!p.pred()) {
/*  532 */           (p.prev()).right = s;
/*  533 */           s.pred(false);
/*      */         } 
/*  535 */         s.right = p.right;
/*  536 */         s.succ(false);
/*  537 */         if (q != null)
/*  538 */         { if (dir) {
/*  539 */             q.right = s;
/*      */           } else {
/*  541 */             q.left = s;
/*      */           }  }
/*  543 */         else { this.tree = s; }
/*  544 */          s.balance(p.balance());
/*  545 */         q = r;
/*  546 */         dir = false;
/*      */       }  }
/*      */ 
/*      */     
/*  550 */     while (q != null) {
/*  551 */       Entry<V> y = q;
/*  552 */       q = parent(y);
/*  553 */       if (!dir) {
/*  554 */         dir = (q != null && q.left != y);
/*  555 */         y.incBalance();
/*  556 */         if (y.balance() == 1)
/*      */           break; 
/*  558 */         if (y.balance() == 2) {
/*  559 */           Entry<V> x = y.right;
/*  560 */           assert x != null;
/*  561 */           if (x.balance() == -1) {
/*      */             
/*  563 */             assert x.balance() == -1;
/*  564 */             Entry<V> w = x.left;
/*  565 */             x.left = w.right;
/*  566 */             w.right = x;
/*  567 */             y.right = w.left;
/*  568 */             w.left = y;
/*  569 */             if (w.balance() == 1) {
/*  570 */               x.balance(0);
/*  571 */               y.balance(-1);
/*  572 */             } else if (w.balance() == 0) {
/*  573 */               x.balance(0);
/*  574 */               y.balance(0);
/*      */             } else {
/*  576 */               assert w.balance() == -1;
/*  577 */               x.balance(1);
/*  578 */               y.balance(0);
/*      */             } 
/*  580 */             w.balance(0);
/*  581 */             if (w.pred()) {
/*  582 */               y.succ(w);
/*  583 */               w.pred(false);
/*      */             } 
/*  585 */             if (w.succ()) {
/*  586 */               x.pred(w);
/*  587 */               w.succ(false);
/*      */             } 
/*  589 */             if (q != null) {
/*  590 */               if (dir) {
/*  591 */                 q.right = w; continue;
/*      */               } 
/*  593 */               q.left = w; continue;
/*      */             } 
/*  595 */             this.tree = w; continue;
/*      */           } 
/*  597 */           if (q != null)
/*  598 */           { if (dir) {
/*  599 */               q.right = x;
/*      */             } else {
/*  601 */               q.left = x;
/*      */             }  }
/*  603 */           else { this.tree = x; }
/*  604 */            if (x.balance() == 0) {
/*  605 */             y.right = x.left;
/*  606 */             x.left = y;
/*  607 */             x.balance(-1);
/*  608 */             y.balance(1);
/*      */             break;
/*      */           } 
/*  611 */           assert x.balance() == 1;
/*  612 */           if (x.pred()) {
/*  613 */             y.succ(true);
/*  614 */             x.pred(false);
/*      */           } else {
/*  616 */             y.right = x.left;
/*  617 */           }  x.left = y;
/*  618 */           y.balance(0);
/*  619 */           x.balance(0);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  623 */       dir = (q != null && q.left != y);
/*  624 */       y.decBalance();
/*  625 */       if (y.balance() == -1)
/*      */         break; 
/*  627 */       if (y.balance() == -2) {
/*  628 */         Entry<V> x = y.left;
/*  629 */         assert x != null;
/*  630 */         if (x.balance() == 1) {
/*      */           
/*  632 */           assert x.balance() == 1;
/*  633 */           Entry<V> w = x.right;
/*  634 */           x.right = w.left;
/*  635 */           w.left = x;
/*  636 */           y.left = w.right;
/*  637 */           w.right = y;
/*  638 */           if (w.balance() == -1) {
/*  639 */             x.balance(0);
/*  640 */             y.balance(1);
/*  641 */           } else if (w.balance() == 0) {
/*  642 */             x.balance(0);
/*  643 */             y.balance(0);
/*      */           } else {
/*  645 */             assert w.balance() == 1;
/*  646 */             x.balance(-1);
/*  647 */             y.balance(0);
/*      */           } 
/*  649 */           w.balance(0);
/*  650 */           if (w.pred()) {
/*  651 */             x.succ(w);
/*  652 */             w.pred(false);
/*      */           } 
/*  654 */           if (w.succ()) {
/*  655 */             y.pred(w);
/*  656 */             w.succ(false);
/*      */           } 
/*  658 */           if (q != null) {
/*  659 */             if (dir) {
/*  660 */               q.right = w; continue;
/*      */             } 
/*  662 */             q.left = w; continue;
/*      */           } 
/*  664 */           this.tree = w; continue;
/*      */         } 
/*  666 */         if (q != null)
/*  667 */         { if (dir) {
/*  668 */             q.right = x;
/*      */           } else {
/*  670 */             q.left = x;
/*      */           }  }
/*  672 */         else { this.tree = x; }
/*  673 */          if (x.balance() == 0) {
/*  674 */           y.left = x.right;
/*  675 */           x.right = y;
/*  676 */           x.balance(1);
/*  677 */           y.balance(-1);
/*      */           break;
/*      */         } 
/*  680 */         assert x.balance() == -1;
/*  681 */         if (x.succ()) {
/*  682 */           y.pred(true);
/*  683 */           x.succ(false);
/*      */         } else {
/*  685 */           y.left = x.right;
/*  686 */         }  x.right = y;
/*  687 */         y.balance(0);
/*  688 */         x.balance(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  693 */     this.modified = true;
/*  694 */     this.count--;
/*  695 */     return p.value;
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  699 */     ValueIterator i = new ValueIterator();
/*      */     
/*  701 */     int j = this.count;
/*  702 */     while (j-- != 0) {
/*  703 */       V ev = i.next();
/*  704 */       if (Objects.equals(ev, v))
/*  705 */         return true; 
/*      */     } 
/*  707 */     return false;
/*      */   }
/*      */   
/*      */   public void clear() {
/*  711 */     this.count = 0;
/*  712 */     this.tree = null;
/*  713 */     this.entries = null;
/*  714 */     this.values = null;
/*  715 */     this.keys = null;
/*  716 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<V>
/*      */     extends AbstractInt2ObjectMap.BasicEntry<V>
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
/*  747 */       super(0, (V)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(int k, V v) {
/*  758 */       super(k, v);
/*  759 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> left() {
/*  767 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> right() {
/*  775 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  783 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  791 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  800 */       if (pred) {
/*  801 */         this.info |= 0x40000000;
/*      */       } else {
/*  803 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  812 */       if (succ) {
/*  813 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  815 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<V> pred) {
/*  824 */       this.info |= 0x40000000;
/*  825 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<V> succ) {
/*  834 */       this.info |= Integer.MIN_VALUE;
/*  835 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<V> left) {
/*  844 */       this.info &= 0xBFFFFFFF;
/*  845 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<V> right) {
/*  854 */       this.info &= Integer.MAX_VALUE;
/*  855 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int balance() {
/*  863 */       return (byte)this.info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void balance(int level) {
/*  872 */       this.info &= 0xFFFFFF00;
/*  873 */       this.info |= level & 0xFF;
/*      */     }
/*      */     
/*      */     void incBalance() {
/*  877 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
/*      */     }
/*      */     
/*      */     protected void decBalance() {
/*  881 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> next() {
/*  889 */       Entry<V> next = this.right;
/*  890 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  891 */         while ((next.info & 0x40000000) == 0)
/*  892 */           next = next.left;  
/*  893 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> prev() {
/*  901 */       Entry<V> prev = this.left;
/*  902 */       if ((this.info & 0x40000000) == 0)
/*  903 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  904 */           prev = prev.right;  
/*  905 */       return prev;
/*      */     }
/*      */     
/*      */     public V setValue(V value) {
/*  909 */       V oldValue = this.value;
/*  910 */       this.value = value;
/*  911 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry<V> clone() {
/*      */       Entry<V> c;
/*      */       try {
/*  918 */         c = (Entry<V>)super.clone();
/*  919 */       } catch (CloneNotSupportedException cantHappen) {
/*  920 */         throw new InternalError();
/*      */       } 
/*  922 */       c.key = this.key;
/*  923 */       c.value = this.value;
/*  924 */       c.info = this.info;
/*  925 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  930 */       if (!(o instanceof Map.Entry))
/*  931 */         return false; 
/*  932 */       Map.Entry<Integer, V> e = (Map.Entry<Integer, V>)o;
/*  933 */       return (this.key == ((Integer)e.getKey()).intValue() && Objects.equals(this.value, e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  937 */       return this.key ^ ((this.value == null) ? 0 : this.value.hashCode());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  941 */       return this.key + "=>" + this.value;
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
/*      */   public boolean containsKey(int k) {
/*  962 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  966 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  970 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(int k) {
/*  975 */     Entry<V> e = findKey(k);
/*  976 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public int firstIntKey() {
/*  980 */     if (this.tree == null)
/*  981 */       throw new NoSuchElementException(); 
/*  982 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public int lastIntKey() {
/*  986 */     if (this.tree == null)
/*  987 */       throw new NoSuchElementException(); 
/*  988 */     return this.lastEntry.key;
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
/*      */     Int2ObjectAVLTreeMap.Entry<V> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Int2ObjectAVLTreeMap.Entry<V> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Int2ObjectAVLTreeMap.Entry<V> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1019 */     int index = 0;
/*      */     TreeIterator() {
/* 1021 */       this.next = Int2ObjectAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(int k) {
/* 1024 */       if ((this.next = Int2ObjectAVLTreeMap.this.locateKey(k)) != null)
/* 1025 */         if (Int2ObjectAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1026 */           this.prev = this.next;
/* 1027 */           this.next = this.next.next();
/*      */         } else {
/* 1029 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/* 1033 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1036 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1039 */       this.next = this.next.next();
/*      */     }
/*      */     Int2ObjectAVLTreeMap.Entry<V> nextEntry() {
/* 1042 */       if (!hasNext())
/* 1043 */         throw new NoSuchElementException(); 
/* 1044 */       this.curr = this.prev = this.next;
/* 1045 */       this.index++;
/* 1046 */       updateNext();
/* 1047 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1050 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Int2ObjectAVLTreeMap.Entry<V> previousEntry() {
/* 1053 */       if (!hasPrevious())
/* 1054 */         throw new NoSuchElementException(); 
/* 1055 */       this.curr = this.next = this.prev;
/* 1056 */       this.index--;
/* 1057 */       updatePrevious();
/* 1058 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1061 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1064 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1067 */       if (this.curr == null) {
/* 1068 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1073 */       if (this.curr == this.prev)
/* 1074 */         this.index--; 
/* 1075 */       this.next = this.prev = this.curr;
/* 1076 */       updatePrevious();
/* 1077 */       updateNext();
/* 1078 */       Int2ObjectAVLTreeMap.this.remove(this.curr.key);
/* 1079 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1082 */       int i = n;
/* 1083 */       while (i-- != 0 && hasNext())
/* 1084 */         nextEntry(); 
/* 1085 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1088 */       int i = n;
/* 1089 */       while (i-- != 0 && hasPrevious())
/* 1090 */         previousEntry(); 
/* 1091 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Int2ObjectMap.Entry<V>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(int k) {
/* 1104 */       super(k);
/*      */     }
/*      */     
/*      */     public Int2ObjectMap.Entry<V> next() {
/* 1108 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Int2ObjectMap.Entry<V> previous() {
/* 1112 */       return previousEntry();
/*      */     }
/*      */     
/*      */     public void set(Int2ObjectMap.Entry<V> ok) {
/* 1116 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Int2ObjectMap.Entry<V> ok) {
/* 1120 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
/* 1125 */     if (this.entries == null)
/* 1126 */       this.entries = (ObjectSortedSet<Int2ObjectMap.Entry<V>>)new AbstractObjectSortedSet<Int2ObjectMap.Entry<V>>()
/*      */         {
/*      */           final Comparator<? super Int2ObjectMap.Entry<V>> comparator;
/*      */           
/*      */           public Comparator<? super Int2ObjectMap.Entry<V>> comparator() {
/* 1131 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> iterator() {
/* 1135 */             return (ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>>)new Int2ObjectAVLTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> iterator(Int2ObjectMap.Entry<V> from) {
/* 1139 */             return (ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>>)new Int2ObjectAVLTreeMap.EntryIterator(from.getIntKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1144 */             if (!(o instanceof Map.Entry))
/* 1145 */               return false; 
/* 1146 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1147 */             if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1148 */               return false; 
/* 1149 */             Int2ObjectAVLTreeMap.Entry<V> f = Int2ObjectAVLTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1150 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1155 */             if (!(o instanceof Map.Entry))
/* 1156 */               return false; 
/* 1157 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1158 */             if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1159 */               return false; 
/* 1160 */             Int2ObjectAVLTreeMap.Entry<V> f = Int2ObjectAVLTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1161 */             if (f == null || !Objects.equals(f.getValue(), e.getValue()))
/* 1162 */               return false; 
/* 1163 */             Int2ObjectAVLTreeMap.this.remove(f.key);
/* 1164 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1168 */             return Int2ObjectAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1172 */             Int2ObjectAVLTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Int2ObjectMap.Entry<V> first() {
/* 1176 */             return Int2ObjectAVLTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Int2ObjectMap.Entry<V> last() {
/* 1180 */             return Int2ObjectAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Int2ObjectMap.Entry<V>> subSet(Int2ObjectMap.Entry<V> from, Int2ObjectMap.Entry<V> to) {
/* 1185 */             return Int2ObjectAVLTreeMap.this.subMap(from.getIntKey(), to.getIntKey()).int2ObjectEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Int2ObjectMap.Entry<V>> headSet(Int2ObjectMap.Entry<V> to) {
/* 1189 */             return Int2ObjectAVLTreeMap.this.headMap(to.getIntKey()).int2ObjectEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Int2ObjectMap.Entry<V>> tailSet(Int2ObjectMap.Entry<V> from) {
/* 1193 */             return Int2ObjectAVLTreeMap.this.tailMap(from.getIntKey()).int2ObjectEntrySet();
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
/*      */     implements IntListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(int k) {
/* 1212 */       super(k);
/*      */     }
/*      */     
/*      */     public int nextInt() {
/* 1216 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public int previousInt() {
/* 1220 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractInt2ObjectSortedMap<V>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public IntBidirectionalIterator iterator() {
/* 1227 */       return new Int2ObjectAVLTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public IntBidirectionalIterator iterator(int from) {
/* 1231 */       return new Int2ObjectAVLTreeMap.KeyIterator(from);
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
/*      */   public IntSortedSet keySet() {
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
/* 1283 */       this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1286 */             return (ObjectIterator<V>)new Int2ObjectAVLTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(Object k) {
/* 1290 */             return Int2ObjectAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1294 */             return Int2ObjectAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1298 */             Int2ObjectAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1301 */     return this.values;
/*      */   }
/*      */   
/*      */   public IntComparator comparator() {
/* 1305 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Int2ObjectSortedMap<V> headMap(int to) {
/* 1309 */     return new Submap(0, true, to, false);
/*      */   }
/*      */   
/*      */   public Int2ObjectSortedMap<V> tailMap(int from) {
/* 1313 */     return new Submap(from, false, 0, true);
/*      */   }
/*      */   
/*      */   public Int2ObjectSortedMap<V> subMap(int from, int to) {
/* 1317 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractInt2ObjectSortedMap<V>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     int from;
/*      */ 
/*      */ 
/*      */     
/*      */     int to;
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
/*      */     protected transient ObjectSortedSet<Int2ObjectMap.Entry<V>> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient IntSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ObjectCollection<V> values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(int from, boolean bottom, int to, boolean top) {
/* 1361 */       if (!bottom && !top && Int2ObjectAVLTreeMap.this.compare(from, to) > 0)
/* 1362 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1363 */       this.from = from;
/* 1364 */       this.bottom = bottom;
/* 1365 */       this.to = to;
/* 1366 */       this.top = top;
/* 1367 */       this.defRetValue = Int2ObjectAVLTreeMap.this.defRetValue;
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
/*      */     final boolean in(int k) {
/* 1385 */       return ((this.bottom || Int2ObjectAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Int2ObjectAVLTreeMap.this
/* 1386 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
/* 1390 */       if (this.entries == null)
/* 1391 */         this.entries = (ObjectSortedSet<Int2ObjectMap.Entry<V>>)new AbstractObjectSortedSet<Int2ObjectMap.Entry<V>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> iterator() {
/* 1394 */               return (ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>>)new Int2ObjectAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> iterator(Int2ObjectMap.Entry<V> from) {
/* 1399 */               return (ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>>)new Int2ObjectAVLTreeMap.Submap.SubmapEntryIterator(from.getIntKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Int2ObjectMap.Entry<V>> comparator() {
/* 1403 */               return Int2ObjectAVLTreeMap.this.int2ObjectEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1408 */               if (!(o instanceof Map.Entry))
/* 1409 */                 return false; 
/* 1410 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1411 */               if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1412 */                 return false; 
/* 1413 */               Int2ObjectAVLTreeMap.Entry<V> f = Int2ObjectAVLTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1414 */               return (f != null && Int2ObjectAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1419 */               if (!(o instanceof Map.Entry))
/* 1420 */                 return false; 
/* 1421 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1422 */               if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1423 */                 return false; 
/* 1424 */               Int2ObjectAVLTreeMap.Entry<V> f = Int2ObjectAVLTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1425 */               if (f != null && Int2ObjectAVLTreeMap.Submap.this.in(f.key))
/* 1426 */                 Int2ObjectAVLTreeMap.Submap.this.remove(f.key); 
/* 1427 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1431 */               int c = 0;
/* 1432 */               for (ObjectBidirectionalIterator<Int2ObjectMap.Entry<V>> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1433 */                 c++; 
/* 1434 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1438 */               return !(new Int2ObjectAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1442 */               Int2ObjectAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Int2ObjectMap.Entry<V> first() {
/* 1446 */               return Int2ObjectAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Int2ObjectMap.Entry<V> last() {
/* 1450 */               return Int2ObjectAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Int2ObjectMap.Entry<V>> subSet(Int2ObjectMap.Entry<V> from, Int2ObjectMap.Entry<V> to) {
/* 1455 */               return Int2ObjectAVLTreeMap.Submap.this.subMap(from.getIntKey(), to.getIntKey()).int2ObjectEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Int2ObjectMap.Entry<V>> headSet(Int2ObjectMap.Entry<V> to) {
/* 1459 */               return Int2ObjectAVLTreeMap.Submap.this.headMap(to.getIntKey()).int2ObjectEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Int2ObjectMap.Entry<V>> tailSet(Int2ObjectMap.Entry<V> from) {
/* 1463 */               return Int2ObjectAVLTreeMap.Submap.this.tailMap(from.getIntKey()).int2ObjectEntrySet();
/*      */             }
/*      */           }; 
/* 1466 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractInt2ObjectSortedMap<V>.KeySet {
/*      */       public IntBidirectionalIterator iterator() {
/* 1471 */         return new Int2ObjectAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public IntBidirectionalIterator iterator(int from) {
/* 1475 */         return new Int2ObjectAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public IntSortedSet keySet() {
/* 1480 */       if (this.keys == null)
/* 1481 */         this.keys = new KeySet(); 
/* 1482 */       return this.keys;
/*      */     }
/*      */     
/*      */     public ObjectCollection<V> values() {
/* 1486 */       if (this.values == null)
/* 1487 */         this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */           {
/*      */             public ObjectIterator<V> iterator() {
/* 1490 */               return (ObjectIterator<V>)new Int2ObjectAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(Object k) {
/* 1494 */               return Int2ObjectAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1498 */               return Int2ObjectAVLTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1502 */               Int2ObjectAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1505 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(int k) {
/* 1510 */       return (in(k) && Int2ObjectAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(Object v) {
/* 1514 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1516 */       while (i.hasNext()) {
/* 1517 */         Object ev = (i.nextEntry()).value;
/* 1518 */         if (Objects.equals(ev, v))
/* 1519 */           return true; 
/*      */       } 
/* 1521 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(int k) {
/* 1527 */       int kk = k; Int2ObjectAVLTreeMap.Entry<V> e;
/* 1528 */       return (in(kk) && (e = Int2ObjectAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public V put(int k, V v) {
/* 1532 */       Int2ObjectAVLTreeMap.this.modified = false;
/* 1533 */       if (!in(k))
/* 1534 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1535 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1536 */       V oldValue = Int2ObjectAVLTreeMap.this.put(k, v);
/* 1537 */       return Int2ObjectAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public V remove(int k) {
/* 1542 */       Int2ObjectAVLTreeMap.this.modified = false;
/* 1543 */       if (!in(k))
/* 1544 */         return this.defRetValue; 
/* 1545 */       V oldValue = Int2ObjectAVLTreeMap.this.remove(k);
/* 1546 */       return Int2ObjectAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1550 */       SubmapIterator i = new SubmapIterator();
/* 1551 */       int n = 0;
/* 1552 */       while (i.hasNext()) {
/* 1553 */         n++;
/* 1554 */         i.nextEntry();
/*      */       } 
/* 1556 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1560 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public IntComparator comparator() {
/* 1564 */       return Int2ObjectAVLTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Int2ObjectSortedMap<V> headMap(int to) {
/* 1568 */       if (this.top)
/* 1569 */         return new Submap(this.from, this.bottom, to, false); 
/* 1570 */       return (Int2ObjectAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Int2ObjectSortedMap<V> tailMap(int from) {
/* 1574 */       if (this.bottom)
/* 1575 */         return new Submap(from, false, this.to, this.top); 
/* 1576 */       return (Int2ObjectAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Int2ObjectSortedMap<V> subMap(int from, int to) {
/* 1580 */       if (this.top && this.bottom)
/* 1581 */         return new Submap(from, false, to, false); 
/* 1582 */       if (!this.top)
/* 1583 */         to = (Int2ObjectAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1584 */       if (!this.bottom)
/* 1585 */         from = (Int2ObjectAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1586 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1587 */         return this; 
/* 1588 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Int2ObjectAVLTreeMap.Entry<V> firstEntry() {
/*      */       Int2ObjectAVLTreeMap.Entry<V> e;
/* 1597 */       if (Int2ObjectAVLTreeMap.this.tree == null) {
/* 1598 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1602 */       if (this.bottom) {
/* 1603 */         e = Int2ObjectAVLTreeMap.this.firstEntry;
/*      */       } else {
/* 1605 */         e = Int2ObjectAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1607 */         if (Int2ObjectAVLTreeMap.this.compare(e.key, this.from) < 0) {
/* 1608 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1612 */       if (e == null || (!this.top && Int2ObjectAVLTreeMap.this.compare(e.key, this.to) >= 0))
/* 1613 */         return null; 
/* 1614 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Int2ObjectAVLTreeMap.Entry<V> lastEntry() {
/*      */       Int2ObjectAVLTreeMap.Entry<V> e;
/* 1623 */       if (Int2ObjectAVLTreeMap.this.tree == null) {
/* 1624 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1628 */       if (this.top) {
/* 1629 */         e = Int2ObjectAVLTreeMap.this.lastEntry;
/*      */       } else {
/* 1631 */         e = Int2ObjectAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1633 */         if (Int2ObjectAVLTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1634 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1638 */       if (e == null || (!this.bottom && Int2ObjectAVLTreeMap.this.compare(e.key, this.from) < 0))
/* 1639 */         return null; 
/* 1640 */       return e;
/*      */     }
/*      */     
/*      */     public int firstIntKey() {
/* 1644 */       Int2ObjectAVLTreeMap.Entry<V> e = firstEntry();
/* 1645 */       if (e == null)
/* 1646 */         throw new NoSuchElementException(); 
/* 1647 */       return e.key;
/*      */     }
/*      */     
/*      */     public int lastIntKey() {
/* 1651 */       Int2ObjectAVLTreeMap.Entry<V> e = lastEntry();
/* 1652 */       if (e == null)
/* 1653 */         throw new NoSuchElementException(); 
/* 1654 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Int2ObjectAVLTreeMap<V>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1667 */         this.next = Int2ObjectAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(int k) {
/* 1670 */         this();
/* 1671 */         if (this.next != null)
/* 1672 */           if (!Int2ObjectAVLTreeMap.Submap.this.bottom && Int2ObjectAVLTreeMap.this.compare(k, this.next.key) < 0) {
/* 1673 */             this.prev = null;
/* 1674 */           } else if (!Int2ObjectAVLTreeMap.Submap.this.top && Int2ObjectAVLTreeMap.this.compare(k, (this.prev = Int2ObjectAVLTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1675 */             this.next = null;
/*      */           } else {
/* 1677 */             this.next = Int2ObjectAVLTreeMap.this.locateKey(k);
/* 1678 */             if (Int2ObjectAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1679 */               this.prev = this.next;
/* 1680 */               this.next = this.next.next();
/*      */             } else {
/* 1682 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1688 */         this.prev = this.prev.prev();
/* 1689 */         if (!Int2ObjectAVLTreeMap.Submap.this.bottom && this.prev != null && Int2ObjectAVLTreeMap.this.compare(this.prev.key, Int2ObjectAVLTreeMap.Submap.this.from) < 0)
/* 1690 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1694 */         this.next = this.next.next();
/* 1695 */         if (!Int2ObjectAVLTreeMap.Submap.this.top && this.next != null && Int2ObjectAVLTreeMap.this.compare(this.next.key, Int2ObjectAVLTreeMap.Submap.this.to) >= 0)
/* 1696 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Int2ObjectMap.Entry<V>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(int k) {
/* 1703 */         super(k);
/*      */       }
/*      */       
/*      */       public Int2ObjectMap.Entry<V> next() {
/* 1707 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Int2ObjectMap.Entry<V> previous() {
/* 1711 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements IntListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(int from) {
/* 1729 */         super(from);
/*      */       }
/*      */       
/*      */       public int nextInt() {
/* 1733 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public int previousInt() {
/* 1737 */         return (previousEntry()).key;
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
/* 1753 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public V previous() {
/* 1757 */         return (previousEntry()).value;
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
/*      */   public Int2ObjectAVLTreeMap<V> clone() {
/*      */     Int2ObjectAVLTreeMap<V> c;
/*      */     try {
/* 1776 */       c = (Int2ObjectAVLTreeMap<V>)super.clone();
/* 1777 */     } catch (CloneNotSupportedException cantHappen) {
/* 1778 */       throw new InternalError();
/*      */     } 
/* 1780 */     c.keys = null;
/* 1781 */     c.values = null;
/* 1782 */     c.entries = null;
/* 1783 */     c.allocatePaths();
/* 1784 */     if (this.count != 0) {
/*      */       
/* 1786 */       Entry<V> rp = new Entry<>(), rq = new Entry<>();
/* 1787 */       Entry<V> p = rp;
/* 1788 */       rp.left(this.tree);
/* 1789 */       Entry<V> q = rq;
/* 1790 */       rq.pred((Entry<V>)null);
/*      */       while (true) {
/* 1792 */         if (!p.pred()) {
/* 1793 */           Entry<V> e = p.left.clone();
/* 1794 */           e.pred(q.left);
/* 1795 */           e.succ(q);
/* 1796 */           q.left(e);
/* 1797 */           p = p.left;
/* 1798 */           q = q.left;
/*      */         } else {
/* 1800 */           while (p.succ()) {
/* 1801 */             p = p.right;
/* 1802 */             if (p == null) {
/* 1803 */               q.right = null;
/* 1804 */               c.tree = rq.left;
/* 1805 */               c.firstEntry = c.tree;
/* 1806 */               while (c.firstEntry.left != null)
/* 1807 */                 c.firstEntry = c.firstEntry.left; 
/* 1808 */               c.lastEntry = c.tree;
/* 1809 */               while (c.lastEntry.right != null)
/* 1810 */                 c.lastEntry = c.lastEntry.right; 
/* 1811 */               return c;
/*      */             } 
/* 1813 */             q = q.right;
/*      */           } 
/* 1815 */           p = p.right;
/* 1816 */           q = q.right;
/*      */         } 
/* 1818 */         if (!p.succ()) {
/* 1819 */           Entry<V> e = p.right.clone();
/* 1820 */           e.succ(q.right);
/* 1821 */           e.pred(q);
/* 1822 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1826 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1829 */     int n = this.count;
/* 1830 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1832 */     s.defaultWriteObject();
/* 1833 */     while (n-- != 0) {
/* 1834 */       Entry<V> e = i.nextEntry();
/* 1835 */       s.writeInt(e.key);
/* 1836 */       s.writeObject(e.value);
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
/* 1857 */     if (n == 1) {
/* 1858 */       Entry<V> entry = new Entry<>(s.readInt(), (V)s.readObject());
/* 1859 */       entry.pred(pred);
/* 1860 */       entry.succ(succ);
/* 1861 */       return entry;
/*      */     } 
/* 1863 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1868 */       Entry<V> entry = new Entry<>(s.readInt(), (V)s.readObject());
/* 1869 */       entry.right(new Entry<>(s.readInt(), (V)s.readObject()));
/* 1870 */       entry.right.pred(entry);
/* 1871 */       entry.balance(1);
/* 1872 */       entry.pred(pred);
/* 1873 */       entry.right.succ(succ);
/* 1874 */       return entry;
/*      */     } 
/*      */     
/* 1877 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1878 */     Entry<V> top = new Entry<>();
/* 1879 */     top.left(readTree(s, leftN, pred, top));
/* 1880 */     top.key = s.readInt();
/* 1881 */     top.value = (V)s.readObject();
/* 1882 */     top.right(readTree(s, rightN, top, succ));
/* 1883 */     if (n == (n & -n))
/* 1884 */       top.balance(1); 
/* 1885 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1888 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1893 */     setActualComparator();
/* 1894 */     allocatePaths();
/* 1895 */     if (this.count != 0) {
/* 1896 */       this.tree = readTree(s, this.count, (Entry<V>)null, (Entry<V>)null);
/*      */       
/* 1898 */       Entry<V> e = this.tree;
/* 1899 */       while (e.left() != null)
/* 1900 */         e = e.left(); 
/* 1901 */       this.firstEntry = e;
/* 1902 */       e = this.tree;
/* 1903 */       while (e.right() != null)
/* 1904 */         e = e.right(); 
/* 1905 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2ObjectAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */