/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
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
/*      */ public class ObjectAVLTreeSet<K>
/*      */   extends AbstractObjectSortedSet<K>
/*      */   implements Serializable, Cloneable, ObjectSortedSet<K>
/*      */ {
/*      */   protected transient Entry<K> tree;
/*      */   protected int count;
/*      */   protected transient Entry<K> firstEntry;
/*      */   protected transient Entry<K> lastEntry;
/*      */   protected Comparator<? super K> storedComparator;
/*      */   protected transient Comparator<? super K> actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353130L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public ObjectAVLTreeSet() {
/*   54 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   60 */     this.tree = null;
/*   61 */     this.count = 0;
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
/*   73 */     this.actualComparator = this.storedComparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectAVLTreeSet(Comparator<? super K> c) {
/*   82 */     this();
/*   83 */     this.storedComparator = c;
/*   84 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectAVLTreeSet(Collection<? extends K> c) {
/*   93 */     this();
/*   94 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectAVLTreeSet(SortedSet<K> s) {
/*  104 */     this(s.comparator());
/*  105 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectAVLTreeSet(ObjectCollection<? extends K> c) {
/*  114 */     this();
/*  115 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectAVLTreeSet(ObjectSortedSet<K> s) {
/*  125 */     this(s.comparator());
/*  126 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectAVLTreeSet(Iterator<? extends K> i) {
/*      */     allocatePaths();
/*  135 */     while (i.hasNext()) {
/*  136 */       add(i.next());
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
/*      */   public ObjectAVLTreeSet(K[] a, int offset, int length, Comparator<? super K> c) {
/*  152 */     this(c);
/*  153 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/*  154 */     for (int i = 0; i < length; i++) {
/*  155 */       add(a[offset + i]);
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
/*      */   public ObjectAVLTreeSet(K[] a, int offset, int length) {
/*  168 */     this(a, offset, length, (Comparator<? super K>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectAVLTreeSet(K[] a) {
/*  177 */     this();
/*  178 */     int i = a.length;
/*  179 */     while (i-- != 0) {
/*  180 */       add(a[i]);
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
/*      */   public ObjectAVLTreeSet(K[] a, Comparator<? super K> c) {
/*  192 */     this(c);
/*  193 */     int i = a.length;
/*  194 */     while (i-- != 0) {
/*  195 */       add(a[i]);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int compare(K k1, K k2) {
/*  223 */     return (this.actualComparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.actualComparator.compare(k1, k2);
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
/*      */   private Entry<K> findKey(K k) {
/*  235 */     Entry<K> e = this.tree;
/*      */     int cmp;
/*  237 */     while (e != null && (cmp = compare(k, e.key)) != 0)
/*  238 */       e = (cmp < 0) ? e.left() : e.right(); 
/*  239 */     return e;
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
/*  251 */     Entry<K> e = this.tree, last = this.tree;
/*  252 */     int cmp = 0;
/*  253 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  254 */       last = e;
/*  255 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  257 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  265 */     this.dirPath = new boolean[48];
/*      */   }
/*      */   
/*      */   public boolean add(K k) {
/*  269 */     if (this.tree == null) {
/*  270 */       this.count++;
/*  271 */       this.tree = this.lastEntry = this.firstEntry = new Entry<>(k);
/*      */     } else {
/*  273 */       Entry<K> p = this.tree, q = null, y = this.tree, z = null, e = null, w = null;
/*  274 */       int i = 0; while (true) {
/*      */         int cmp;
/*  276 */         if ((cmp = compare(k, p.key)) == 0)
/*  277 */           return false; 
/*  278 */         if (p.balance() != 0) {
/*  279 */           i = 0;
/*  280 */           z = q;
/*  281 */           y = p;
/*      */         } 
/*  283 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  284 */           if (p.succ()) {
/*  285 */             this.count++;
/*  286 */             e = new Entry<>(k);
/*  287 */             if (p.right == null)
/*  288 */               this.lastEntry = e; 
/*  289 */             e.left = p;
/*  290 */             e.right = p.right;
/*  291 */             p.right(e);
/*      */             break;
/*      */           } 
/*  294 */           q = p;
/*  295 */           p = p.right; continue;
/*      */         } 
/*  297 */         if (p.pred()) {
/*  298 */           this.count++;
/*  299 */           e = new Entry<>(k);
/*  300 */           if (p.left == null)
/*  301 */             this.firstEntry = e; 
/*  302 */           e.right = p;
/*  303 */           e.left = p.left;
/*  304 */           p.left(e);
/*      */           break;
/*      */         } 
/*  307 */         q = p;
/*  308 */         p = p.left;
/*      */       } 
/*      */       
/*  311 */       p = y;
/*  312 */       i = 0;
/*  313 */       while (p != e) {
/*  314 */         if (this.dirPath[i]) {
/*  315 */           p.incBalance();
/*      */         } else {
/*  317 */           p.decBalance();
/*  318 */         }  p = this.dirPath[i++] ? p.right : p.left;
/*      */       } 
/*  320 */       if (y.balance() == -2) {
/*  321 */         Entry<K> x = y.left;
/*  322 */         if (x.balance() == -1) {
/*  323 */           w = x;
/*  324 */           if (x.succ()) {
/*  325 */             x.succ(false);
/*  326 */             y.pred(x);
/*      */           } else {
/*  328 */             y.left = x.right;
/*  329 */           }  x.right = y;
/*  330 */           x.balance(0);
/*  331 */           y.balance(0);
/*      */         } else {
/*  333 */           assert x.balance() == 1;
/*  334 */           w = x.right;
/*  335 */           x.right = w.left;
/*  336 */           w.left = x;
/*  337 */           y.left = w.right;
/*  338 */           w.right = y;
/*  339 */           if (w.balance() == -1) {
/*  340 */             x.balance(0);
/*  341 */             y.balance(1);
/*  342 */           } else if (w.balance() == 0) {
/*  343 */             x.balance(0);
/*  344 */             y.balance(0);
/*      */           } else {
/*  346 */             x.balance(-1);
/*  347 */             y.balance(0);
/*      */           } 
/*  349 */           w.balance(0);
/*  350 */           if (w.pred()) {
/*  351 */             x.succ(w);
/*  352 */             w.pred(false);
/*      */           } 
/*  354 */           if (w.succ()) {
/*  355 */             y.pred(w);
/*  356 */             w.succ(false);
/*      */           } 
/*      */         } 
/*  359 */       } else if (y.balance() == 2) {
/*  360 */         Entry<K> x = y.right;
/*  361 */         if (x.balance() == 1) {
/*  362 */           w = x;
/*  363 */           if (x.pred()) {
/*  364 */             x.pred(false);
/*  365 */             y.succ(x);
/*      */           } else {
/*  367 */             y.right = x.left;
/*  368 */           }  x.left = y;
/*  369 */           x.balance(0);
/*  370 */           y.balance(0);
/*      */         } else {
/*  372 */           assert x.balance() == -1;
/*  373 */           w = x.left;
/*  374 */           x.left = w.right;
/*  375 */           w.right = x;
/*  376 */           y.right = w.left;
/*  377 */           w.left = y;
/*  378 */           if (w.balance() == 1) {
/*  379 */             x.balance(0);
/*  380 */             y.balance(-1);
/*  381 */           } else if (w.balance() == 0) {
/*  382 */             x.balance(0);
/*  383 */             y.balance(0);
/*      */           } else {
/*  385 */             x.balance(1);
/*  386 */             y.balance(0);
/*      */           } 
/*  388 */           w.balance(0);
/*  389 */           if (w.pred()) {
/*  390 */             y.succ(w);
/*  391 */             w.pred(false);
/*      */           } 
/*  393 */           if (w.succ()) {
/*  394 */             x.pred(w);
/*  395 */             w.succ(false);
/*      */           } 
/*      */         } 
/*      */       } else {
/*  399 */         return true;
/*  400 */       }  if (z == null) {
/*  401 */         this.tree = w;
/*      */       }
/*  403 */       else if (z.left == y) {
/*  404 */         z.left = w;
/*      */       } else {
/*  406 */         z.right = w;
/*      */       } 
/*      */     } 
/*  409 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry<K> parent(Entry<K> e) {
/*  419 */     if (e == this.tree) {
/*  420 */       return null;
/*      */     }
/*  422 */     Entry<K> y = e, x = y;
/*      */     while (true) {
/*  424 */       if (y.succ()) {
/*  425 */         Entry<K> p = y.right;
/*  426 */         if (p == null || p.left != e) {
/*  427 */           while (!x.pred())
/*  428 */             x = x.left; 
/*  429 */           p = x.left;
/*      */         } 
/*  431 */         return p;
/*  432 */       }  if (x.pred()) {
/*  433 */         Entry<K> p = x.left;
/*  434 */         if (p == null || p.right != e) {
/*  435 */           while (!y.succ())
/*  436 */             y = y.right; 
/*  437 */           p = y.right;
/*      */         } 
/*  439 */         return p;
/*      */       } 
/*  441 */       x = x.left;
/*  442 */       y = y.right;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(Object k) {
/*  448 */     if (this.tree == null) {
/*  449 */       return false;
/*      */     }
/*  451 */     Entry<K> p = this.tree, q = null;
/*  452 */     boolean dir = false;
/*  453 */     K kk = (K)k;
/*      */     int cmp;
/*  455 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  457 */       if (dir = (cmp > 0)) {
/*  458 */         q = p;
/*  459 */         if ((p = p.right()) == null)
/*  460 */           return false;  continue;
/*      */       } 
/*  462 */       q = p;
/*  463 */       if ((p = p.left()) == null) {
/*  464 */         return false;
/*      */       }
/*      */     } 
/*  467 */     if (p.left == null)
/*  468 */       this.firstEntry = p.next(); 
/*  469 */     if (p.right == null)
/*  470 */       this.lastEntry = p.prev(); 
/*  471 */     if (p.succ())
/*  472 */     { if (p.pred())
/*  473 */       { if (q != null)
/*  474 */         { if (dir) {
/*  475 */             q.succ(p.right);
/*      */           } else {
/*  477 */             q.pred(p.left);
/*      */           }  }
/*  479 */         else { this.tree = dir ? p.right : p.left; }
/*      */          }
/*  481 */       else { (p.prev()).right = p.right;
/*  482 */         if (q != null)
/*  483 */         { if (dir) {
/*  484 */             q.right = p.left;
/*      */           } else {
/*  486 */             q.left = p.left;
/*      */           }  }
/*  488 */         else { this.tree = p.left; }
/*      */          }
/*      */        }
/*  491 */     else { Entry<K> r = p.right;
/*  492 */       if (r.pred()) {
/*  493 */         r.left = p.left;
/*  494 */         r.pred(p.pred());
/*  495 */         if (!r.pred())
/*  496 */           (r.prev()).right = r; 
/*  497 */         if (q != null)
/*  498 */         { if (dir) {
/*  499 */             q.right = r;
/*      */           } else {
/*  501 */             q.left = r;
/*      */           }  }
/*  503 */         else { this.tree = r; }
/*  504 */          r.balance(p.balance());
/*  505 */         q = r;
/*  506 */         dir = true;
/*      */       } else {
/*      */         Entry<K> s;
/*      */         while (true) {
/*  510 */           s = r.left;
/*  511 */           if (s.pred())
/*      */             break; 
/*  513 */           r = s;
/*      */         } 
/*  515 */         if (s.succ()) {
/*  516 */           r.pred(s);
/*      */         } else {
/*  518 */           r.left = s.right;
/*  519 */         }  s.left = p.left;
/*  520 */         if (!p.pred()) {
/*  521 */           (p.prev()).right = s;
/*  522 */           s.pred(false);
/*      */         } 
/*  524 */         s.right = p.right;
/*  525 */         s.succ(false);
/*  526 */         if (q != null)
/*  527 */         { if (dir) {
/*  528 */             q.right = s;
/*      */           } else {
/*  530 */             q.left = s;
/*      */           }  }
/*  532 */         else { this.tree = s; }
/*  533 */          s.balance(p.balance());
/*  534 */         q = r;
/*  535 */         dir = false;
/*      */       }  }
/*      */ 
/*      */     
/*  539 */     while (q != null) {
/*  540 */       Entry<K> y = q;
/*  541 */       q = parent(y);
/*  542 */       if (!dir) {
/*  543 */         dir = (q != null && q.left != y);
/*  544 */         y.incBalance();
/*  545 */         if (y.balance() == 1)
/*      */           break; 
/*  547 */         if (y.balance() == 2) {
/*  548 */           Entry<K> x = y.right;
/*  549 */           assert x != null;
/*  550 */           if (x.balance() == -1) {
/*      */             
/*  552 */             assert x.balance() == -1;
/*  553 */             Entry<K> w = x.left;
/*  554 */             x.left = w.right;
/*  555 */             w.right = x;
/*  556 */             y.right = w.left;
/*  557 */             w.left = y;
/*  558 */             if (w.balance() == 1) {
/*  559 */               x.balance(0);
/*  560 */               y.balance(-1);
/*  561 */             } else if (w.balance() == 0) {
/*  562 */               x.balance(0);
/*  563 */               y.balance(0);
/*      */             } else {
/*  565 */               assert w.balance() == -1;
/*  566 */               x.balance(1);
/*  567 */               y.balance(0);
/*      */             } 
/*  569 */             w.balance(0);
/*  570 */             if (w.pred()) {
/*  571 */               y.succ(w);
/*  572 */               w.pred(false);
/*      */             } 
/*  574 */             if (w.succ()) {
/*  575 */               x.pred(w);
/*  576 */               w.succ(false);
/*      */             } 
/*  578 */             if (q != null) {
/*  579 */               if (dir) {
/*  580 */                 q.right = w; continue;
/*      */               } 
/*  582 */               q.left = w; continue;
/*      */             } 
/*  584 */             this.tree = w; continue;
/*      */           } 
/*  586 */           if (q != null)
/*  587 */           { if (dir) {
/*  588 */               q.right = x;
/*      */             } else {
/*  590 */               q.left = x;
/*      */             }  }
/*  592 */           else { this.tree = x; }
/*  593 */            if (x.balance() == 0) {
/*  594 */             y.right = x.left;
/*  595 */             x.left = y;
/*  596 */             x.balance(-1);
/*  597 */             y.balance(1);
/*      */             break;
/*      */           } 
/*  600 */           assert x.balance() == 1;
/*  601 */           if (x.pred()) {
/*  602 */             y.succ(true);
/*  603 */             x.pred(false);
/*      */           } else {
/*  605 */             y.right = x.left;
/*  606 */           }  x.left = y;
/*  607 */           y.balance(0);
/*  608 */           x.balance(0);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  612 */       dir = (q != null && q.left != y);
/*  613 */       y.decBalance();
/*  614 */       if (y.balance() == -1)
/*      */         break; 
/*  616 */       if (y.balance() == -2) {
/*  617 */         Entry<K> x = y.left;
/*  618 */         assert x != null;
/*  619 */         if (x.balance() == 1) {
/*      */           
/*  621 */           assert x.balance() == 1;
/*  622 */           Entry<K> w = x.right;
/*  623 */           x.right = w.left;
/*  624 */           w.left = x;
/*  625 */           y.left = w.right;
/*  626 */           w.right = y;
/*  627 */           if (w.balance() == -1) {
/*  628 */             x.balance(0);
/*  629 */             y.balance(1);
/*  630 */           } else if (w.balance() == 0) {
/*  631 */             x.balance(0);
/*  632 */             y.balance(0);
/*      */           } else {
/*  634 */             assert w.balance() == 1;
/*  635 */             x.balance(-1);
/*  636 */             y.balance(0);
/*      */           } 
/*  638 */           w.balance(0);
/*  639 */           if (w.pred()) {
/*  640 */             x.succ(w);
/*  641 */             w.pred(false);
/*      */           } 
/*  643 */           if (w.succ()) {
/*  644 */             y.pred(w);
/*  645 */             w.succ(false);
/*      */           } 
/*  647 */           if (q != null) {
/*  648 */             if (dir) {
/*  649 */               q.right = w; continue;
/*      */             } 
/*  651 */             q.left = w; continue;
/*      */           } 
/*  653 */           this.tree = w; continue;
/*      */         } 
/*  655 */         if (q != null)
/*  656 */         { if (dir) {
/*  657 */             q.right = x;
/*      */           } else {
/*  659 */             q.left = x;
/*      */           }  }
/*  661 */         else { this.tree = x; }
/*  662 */          if (x.balance() == 0) {
/*  663 */           y.left = x.right;
/*  664 */           x.right = y;
/*  665 */           x.balance(1);
/*  666 */           y.balance(-1);
/*      */           break;
/*      */         } 
/*  669 */         assert x.balance() == -1;
/*  670 */         if (x.succ()) {
/*  671 */           y.pred(true);
/*  672 */           x.succ(false);
/*      */         } else {
/*  674 */           y.left = x.right;
/*  675 */         }  x.right = y;
/*  676 */         y.balance(0);
/*  677 */         x.balance(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  682 */     this.count--;
/*  683 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean contains(Object k) {
/*  688 */     return (findKey((K)k) != null);
/*      */   }
/*      */   
/*      */   public K get(Object k) {
/*  692 */     Entry<K> entry = findKey((K)k);
/*  693 */     return (entry == null) ? null : entry.key;
/*      */   }
/*      */   
/*      */   public void clear() {
/*  697 */     this.count = 0;
/*  698 */     this.tree = null;
/*  699 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<K>
/*      */     implements Cloneable
/*      */   {
/*      */     private static final int SUCC_MASK = -2147483648;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int PRED_MASK = 1073741824;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int BALANCE_MASK = 255;
/*      */ 
/*      */ 
/*      */     
/*      */     K key;
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> left;
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> right;
/*      */ 
/*      */     
/*      */     int info;
/*      */ 
/*      */ 
/*      */     
/*      */     Entry() {}
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(K k) {
/*  740 */       this.key = k;
/*  741 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> left() {
/*  749 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> right() {
/*  757 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  765 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  773 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  782 */       if (pred) {
/*  783 */         this.info |= 0x40000000;
/*      */       } else {
/*  785 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  794 */       if (succ) {
/*  795 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  797 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<K> pred) {
/*  806 */       this.info |= 0x40000000;
/*  807 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<K> succ) {
/*  816 */       this.info |= Integer.MIN_VALUE;
/*  817 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<K> left) {
/*  826 */       this.info &= 0xBFFFFFFF;
/*  827 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<K> right) {
/*  836 */       this.info &= Integer.MAX_VALUE;
/*  837 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int balance() {
/*  845 */       return (byte)this.info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void balance(int level) {
/*  854 */       this.info &= 0xFFFFFF00;
/*  855 */       this.info |= level & 0xFF;
/*      */     }
/*      */     
/*      */     void incBalance() {
/*  859 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
/*      */     }
/*      */     
/*      */     protected void decBalance() {
/*  863 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> next() {
/*  871 */       Entry<K> next = this.right;
/*  872 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  873 */         while ((next.info & 0x40000000) == 0)
/*  874 */           next = next.left;  
/*  875 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> prev() {
/*  883 */       Entry<K> prev = this.left;
/*  884 */       if ((this.info & 0x40000000) == 0)
/*  885 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  886 */           prev = prev.right;  
/*  887 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry<K> clone() {
/*      */       Entry<K> c;
/*      */       try {
/*  894 */         c = (Entry<K>)super.clone();
/*  895 */       } catch (CloneNotSupportedException cantHappen) {
/*  896 */         throw new InternalError();
/*      */       } 
/*  898 */       c.key = this.key;
/*  899 */       c.info = this.info;
/*  900 */       return c;
/*      */     }
/*      */     public boolean equals(Object o) {
/*  903 */       if (!(o instanceof Entry))
/*  904 */         return false; 
/*  905 */       Entry<?> e = (Entry)o;
/*  906 */       return Objects.equals(this.key, e.key);
/*      */     }
/*      */     public int hashCode() {
/*  909 */       return this.key.hashCode();
/*      */     }
/*      */     public String toString() {
/*  912 */       return String.valueOf(this.key);
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
/*      */   public int size() {
/*  932 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  936 */     return (this.count == 0);
/*      */   }
/*      */   
/*      */   public K first() {
/*  940 */     if (this.tree == null)
/*  941 */       throw new NoSuchElementException(); 
/*  942 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public K last() {
/*  946 */     if (this.tree == null)
/*  947 */       throw new NoSuchElementException(); 
/*  948 */     return this.lastEntry.key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class SetIterator
/*      */     implements ObjectListIterator<K>
/*      */   {
/*      */     ObjectAVLTreeSet.Entry<K> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ObjectAVLTreeSet.Entry<K> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ObjectAVLTreeSet.Entry<K> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  979 */     int index = 0;
/*      */     SetIterator() {
/*  981 */       this.next = ObjectAVLTreeSet.this.firstEntry;
/*      */     }
/*      */     SetIterator(K k) {
/*  984 */       if ((this.next = ObjectAVLTreeSet.this.locateKey(k)) != null)
/*  985 */         if (ObjectAVLTreeSet.this.compare(this.next.key, k) <= 0) {
/*  986 */           this.prev = this.next;
/*  987 */           this.next = this.next.next();
/*      */         } else {
/*  989 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  994 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  998 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1001 */       this.next = this.next.next();
/*      */     }
/*      */     ObjectAVLTreeSet.Entry<K> nextEntry() {
/* 1004 */       if (!hasNext())
/* 1005 */         throw new NoSuchElementException(); 
/* 1006 */       this.curr = this.prev = this.next;
/* 1007 */       this.index++;
/* 1008 */       updateNext();
/* 1009 */       return this.curr;
/*      */     }
/*      */     
/*      */     public K next() {
/* 1013 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1017 */       return (previousEntry()).key;
/*      */     }
/*      */     void updatePrevious() {
/* 1020 */       this.prev = this.prev.prev();
/*      */     }
/*      */     ObjectAVLTreeSet.Entry<K> previousEntry() {
/* 1023 */       if (!hasPrevious())
/* 1024 */         throw new NoSuchElementException(); 
/* 1025 */       this.curr = this.next = this.prev;
/* 1026 */       this.index--;
/* 1027 */       updatePrevious();
/* 1028 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1032 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1036 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1040 */       if (this.curr == null) {
/* 1041 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1046 */       if (this.curr == this.prev)
/* 1047 */         this.index--; 
/* 1048 */       this.next = this.prev = this.curr;
/* 1049 */       updatePrevious();
/* 1050 */       updateNext();
/* 1051 */       ObjectAVLTreeSet.this.remove(this.curr.key);
/* 1052 */       this.curr = null;
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectBidirectionalIterator<K> iterator() {
/* 1057 */     return new SetIterator();
/*      */   }
/*      */   
/*      */   public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1061 */     return new SetIterator(from);
/*      */   }
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1065 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<K> headSet(K to) {
/* 1069 */     return new Subset(null, true, to, false);
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<K> tailSet(K from) {
/* 1073 */     return new Subset(from, false, null, true);
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<K> subSet(K from, K to) {
/* 1077 */     return new Subset(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Subset
/*      */     extends AbstractObjectSortedSet<K>
/*      */     implements Serializable, ObjectSortedSet<K>
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     K from;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     K to;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean bottom;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean top;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Subset(K from, boolean bottom, K to, boolean top) {
/* 1115 */       if (!bottom && !top && ObjectAVLTreeSet.this.compare(from, to) > 0) {
/* 1116 */         throw new IllegalArgumentException("Start element (" + from + ") is larger than end element (" + to + ")");
/*      */       }
/* 1118 */       this.from = from;
/* 1119 */       this.bottom = bottom;
/* 1120 */       this.to = to;
/* 1121 */       this.top = top;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1125 */       SubsetIterator i = new SubsetIterator();
/* 1126 */       while (i.hasNext()) {
/* 1127 */         i.next();
/* 1128 */         i.remove();
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
/* 1139 */       return ((this.bottom || ObjectAVLTreeSet.this.compare(k, this.from) >= 0) && (this.top || ObjectAVLTreeSet.this
/* 1140 */         .compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1145 */       return (in((K)k) && ObjectAVLTreeSet.this.contains(k));
/*      */     }
/*      */     
/*      */     public boolean add(K k) {
/* 1149 */       if (!in(k))
/* 1150 */         throw new IllegalArgumentException("Element (" + k + ") out of range [" + (
/* 1151 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1152 */       return ObjectAVLTreeSet.this.add(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1157 */       if (!in((K)k))
/* 1158 */         return false; 
/* 1159 */       return ObjectAVLTreeSet.this.remove(k);
/*      */     }
/*      */     
/*      */     public int size() {
/* 1163 */       SubsetIterator i = new SubsetIterator();
/* 1164 */       int n = 0;
/* 1165 */       while (i.hasNext()) {
/* 1166 */         n++;
/* 1167 */         i.next();
/*      */       } 
/* 1169 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1173 */       return !(new SubsetIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1177 */       return ObjectAVLTreeSet.this.actualComparator;
/*      */     }
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1181 */       return new SubsetIterator();
/*      */     }
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1185 */       return new SubsetIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1189 */       if (this.top)
/* 1190 */         return new Subset(this.from, this.bottom, to, false); 
/* 1191 */       return (ObjectAVLTreeSet.this.compare(to, this.to) < 0) ? new Subset(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1195 */       if (this.bottom)
/* 1196 */         return new Subset(from, false, this.to, this.top); 
/* 1197 */       return (ObjectAVLTreeSet.this.compare(from, this.from) > 0) ? new Subset(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1201 */       if (this.top && this.bottom)
/* 1202 */         return new Subset(from, false, to, false); 
/* 1203 */       if (!this.top)
/* 1204 */         to = (ObjectAVLTreeSet.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1205 */       if (!this.bottom)
/* 1206 */         from = (ObjectAVLTreeSet.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1207 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1208 */         return this; 
/* 1209 */       return new Subset(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectAVLTreeSet.Entry<K> firstEntry() {
/*      */       ObjectAVLTreeSet.Entry<K> e;
/* 1218 */       if (ObjectAVLTreeSet.this.tree == null) {
/* 1219 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1223 */       if (this.bottom) {
/* 1224 */         e = ObjectAVLTreeSet.this.firstEntry;
/*      */       } else {
/* 1226 */         e = ObjectAVLTreeSet.this.locateKey(this.from);
/*      */         
/* 1228 */         if (ObjectAVLTreeSet.this.compare(e.key, this.from) < 0) {
/* 1229 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1233 */       if (e == null || (!this.top && ObjectAVLTreeSet.this.compare(e.key, this.to) >= 0))
/* 1234 */         return null; 
/* 1235 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectAVLTreeSet.Entry<K> lastEntry() {
/*      */       ObjectAVLTreeSet.Entry<K> e;
/* 1244 */       if (ObjectAVLTreeSet.this.tree == null) {
/* 1245 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1249 */       if (this.top) {
/* 1250 */         e = ObjectAVLTreeSet.this.lastEntry;
/*      */       } else {
/* 1252 */         e = ObjectAVLTreeSet.this.locateKey(this.to);
/*      */         
/* 1254 */         if (ObjectAVLTreeSet.this.compare(e.key, this.to) >= 0) {
/* 1255 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1259 */       if (e == null || (!this.bottom && ObjectAVLTreeSet.this.compare(e.key, this.from) < 0))
/* 1260 */         return null; 
/* 1261 */       return e;
/*      */     }
/*      */     
/*      */     public K first() {
/* 1265 */       ObjectAVLTreeSet.Entry<K> e = firstEntry();
/* 1266 */       if (e == null)
/* 1267 */         throw new NoSuchElementException(); 
/* 1268 */       return e.key;
/*      */     }
/*      */     
/*      */     public K last() {
/* 1272 */       ObjectAVLTreeSet.Entry<K> e = lastEntry();
/* 1273 */       if (e == null)
/* 1274 */         throw new NoSuchElementException(); 
/* 1275 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubsetIterator
/*      */       extends ObjectAVLTreeSet<K>.SetIterator
/*      */     {
/*      */       SubsetIterator() {
/* 1288 */         this.next = ObjectAVLTreeSet.Subset.this.firstEntry();
/*      */       }
/*      */       SubsetIterator(K k) {
/* 1291 */         this();
/* 1292 */         if (this.next != null)
/* 1293 */           if (!ObjectAVLTreeSet.Subset.this.bottom && ObjectAVLTreeSet.this.compare(k, this.next.key) < 0) {
/* 1294 */             this.prev = null;
/* 1295 */           } else if (!ObjectAVLTreeSet.Subset.this.top && ObjectAVLTreeSet.this.compare(k, (this.prev = ObjectAVLTreeSet.Subset.this.lastEntry()).key) >= 0) {
/* 1296 */             this.next = null;
/*      */           } else {
/* 1298 */             this.next = ObjectAVLTreeSet.this.locateKey(k);
/* 1299 */             if (ObjectAVLTreeSet.this.compare(this.next.key, k) <= 0) {
/* 1300 */               this.prev = this.next;
/* 1301 */               this.next = this.next.next();
/*      */             } else {
/* 1303 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1309 */         this.prev = this.prev.prev();
/* 1310 */         if (!ObjectAVLTreeSet.Subset.this.bottom && this.prev != null && ObjectAVLTreeSet.this.compare(this.prev.key, ObjectAVLTreeSet.Subset.this.from) < 0)
/* 1311 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1315 */         this.next = this.next.next();
/* 1316 */         if (!ObjectAVLTreeSet.Subset.this.top && this.next != null && ObjectAVLTreeSet.this.compare(this.next.key, ObjectAVLTreeSet.Subset.this.to) >= 0) {
/* 1317 */           this.next = null;
/*      */         }
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
/*      */   public Object clone() {
/*      */     ObjectAVLTreeSet<K> c;
/*      */     try {
/* 1336 */       c = (ObjectAVLTreeSet<K>)super.clone();
/* 1337 */     } catch (CloneNotSupportedException cantHappen) {
/* 1338 */       throw new InternalError();
/*      */     } 
/* 1340 */     c.allocatePaths();
/* 1341 */     if (this.count != 0) {
/*      */       
/* 1343 */       Entry<K> rp = new Entry<>(), rq = new Entry<>();
/* 1344 */       Entry<K> p = rp;
/* 1345 */       rp.left(this.tree);
/* 1346 */       Entry<K> q = rq;
/* 1347 */       rq.pred((Entry<K>)null);
/*      */       while (true) {
/* 1349 */         if (!p.pred()) {
/* 1350 */           Entry<K> e = p.left.clone();
/* 1351 */           e.pred(q.left);
/* 1352 */           e.succ(q);
/* 1353 */           q.left(e);
/* 1354 */           p = p.left;
/* 1355 */           q = q.left;
/*      */         } else {
/* 1357 */           while (p.succ()) {
/* 1358 */             p = p.right;
/* 1359 */             if (p == null) {
/* 1360 */               q.right = null;
/* 1361 */               c.tree = rq.left;
/* 1362 */               c.firstEntry = c.tree;
/* 1363 */               while (c.firstEntry.left != null)
/* 1364 */                 c.firstEntry = c.firstEntry.left; 
/* 1365 */               c.lastEntry = c.tree;
/* 1366 */               while (c.lastEntry.right != null)
/* 1367 */                 c.lastEntry = c.lastEntry.right; 
/* 1368 */               return c;
/*      */             } 
/* 1370 */             q = q.right;
/*      */           } 
/* 1372 */           p = p.right;
/* 1373 */           q = q.right;
/*      */         } 
/* 1375 */         if (!p.succ()) {
/* 1376 */           Entry<K> e = p.right.clone();
/* 1377 */           e.succ(q.right);
/* 1378 */           e.pred(q);
/* 1379 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1383 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1386 */     int n = this.count;
/* 1387 */     SetIterator i = new SetIterator();
/* 1388 */     s.defaultWriteObject();
/* 1389 */     while (n-- != 0) {
/* 1390 */       s.writeObject(i.next());
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
/*      */   private Entry<K> readTree(ObjectInputStream s, int n, Entry<K> pred, Entry<K> succ) throws IOException, ClassNotFoundException {
/* 1410 */     if (n == 1) {
/* 1411 */       Entry<K> entry = new Entry<>((K)s.readObject());
/* 1412 */       entry.pred(pred);
/* 1413 */       entry.succ(succ);
/* 1414 */       return entry;
/*      */     } 
/* 1416 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1421 */       Entry<K> entry = new Entry<>((K)s.readObject());
/* 1422 */       entry.right(new Entry<>((K)s.readObject()));
/* 1423 */       entry.right.pred(entry);
/* 1424 */       entry.balance(1);
/* 1425 */       entry.pred(pred);
/* 1426 */       entry.right.succ(succ);
/* 1427 */       return entry;
/*      */     } 
/*      */     
/* 1430 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1431 */     Entry<K> top = new Entry<>();
/* 1432 */     top.left(readTree(s, leftN, pred, top));
/* 1433 */     top.key = (K)s.readObject();
/* 1434 */     top.right(readTree(s, rightN, top, succ));
/* 1435 */     if (n == (n & -n))
/* 1436 */       top.balance(1); 
/* 1437 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1440 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1445 */     setActualComparator();
/* 1446 */     allocatePaths();
/* 1447 */     if (this.count != 0) {
/* 1448 */       this.tree = readTree(s, this.count, (Entry<K>)null, (Entry<K>)null);
/*      */       
/* 1450 */       Entry<K> e = this.tree;
/* 1451 */       while (e.left() != null)
/* 1452 */         e = e.left(); 
/* 1453 */       this.firstEntry = e;
/* 1454 */       e = this.tree;
/* 1455 */       while (e.right() != null)
/* 1456 */         e = e.right(); 
/* 1457 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectAVLTreeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */