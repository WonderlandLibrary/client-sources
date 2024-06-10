/*      */ package it.unimi.dsi.fastutil.bytes;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
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
/*      */ public class Byte2DoubleAVLTreeMap
/*      */   extends AbstractByte2DoubleSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Byte2DoubleMap.Entry> entries;
/*      */   protected transient ByteSortedSet keys;
/*      */   protected transient DoubleCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Byte> storedComparator;
/*      */   protected transient ByteComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Byte2DoubleAVLTreeMap() {
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
/*   89 */     this.actualComparator = ByteComparators.asByteComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2DoubleAVLTreeMap(Comparator<? super Byte> c) {
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
/*      */   public Byte2DoubleAVLTreeMap(Map<? extends Byte, ? extends Double> m) {
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
/*      */   public Byte2DoubleAVLTreeMap(SortedMap<Byte, Double> m) {
/*  120 */     this(m.comparator());
/*  121 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2DoubleAVLTreeMap(Byte2DoubleMap m) {
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
/*      */   public Byte2DoubleAVLTreeMap(Byte2DoubleSortedMap m) {
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
/*      */   public Byte2DoubleAVLTreeMap(byte[] k, double[] v, Comparator<? super Byte> c) {
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
/*      */   public Byte2DoubleAVLTreeMap(byte[] k, double[] v) {
/*  176 */     this(k, v, (Comparator<? super Byte>)null);
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
/*  204 */     return (this.actualComparator == null) ? Byte.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry findKey(byte k) {
/*  216 */     Entry e = this.tree;
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
/*      */   final Entry locateKey(byte k) {
/*  232 */     Entry e = this.tree, last = this.tree;
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
/*      */   public double addTo(byte k, double incr) {
/*  265 */     Entry e = add(k);
/*  266 */     double oldValue = e.value;
/*  267 */     e.value += incr;
/*  268 */     return oldValue;
/*      */   }
/*      */   
/*      */   public double put(byte k, double v) {
/*  272 */     Entry e = add(k);
/*  273 */     double oldValue = e.value;
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
/*      */   private Entry add(byte k) {
/*  292 */     this.modified = false;
/*  293 */     Entry e = null;
/*  294 */     if (this.tree == null) {
/*  295 */       this.count++;
/*  296 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry(k, this.defRetValue);
/*  297 */       this.modified = true;
/*      */     } else {
/*  299 */       Entry p = this.tree, q = null, y = this.tree, z = null, w = null;
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
/*  313 */             e = new Entry(k, this.defRetValue);
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
/*  327 */           e = new Entry(k, this.defRetValue);
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
/*  350 */         Entry x = y.left;
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
/*  389 */         Entry x = y.right;
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
/*      */   private Entry parent(Entry e) {
/*  448 */     if (e == this.tree) {
/*  449 */       return null;
/*      */     }
/*  451 */     Entry y = e, x = y;
/*      */     while (true) {
/*  453 */       if (y.succ()) {
/*  454 */         Entry p = y.right;
/*  455 */         if (p == null || p.left != e) {
/*  456 */           while (!x.pred())
/*  457 */             x = x.left; 
/*  458 */           p = x.left;
/*      */         } 
/*  460 */         return p;
/*  461 */       }  if (x.pred()) {
/*  462 */         Entry p = x.left;
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
/*      */   public double remove(byte k) {
/*  481 */     this.modified = false;
/*  482 */     if (this.tree == null) {
/*  483 */       return this.defRetValue;
/*      */     }
/*  485 */     Entry p = this.tree, q = null;
/*  486 */     boolean dir = false;
/*  487 */     byte kk = k;
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
/*  525 */     else { Entry r = p.right;
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
/*      */         Entry s;
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
/*  574 */       Entry y = q;
/*  575 */       q = parent(y);
/*  576 */       if (!dir) {
/*  577 */         dir = (q != null && q.left != y);
/*  578 */         y.incBalance();
/*  579 */         if (y.balance() == 1)
/*      */           break; 
/*  581 */         if (y.balance() == 2) {
/*  582 */           Entry x = y.right;
/*  583 */           assert x != null;
/*  584 */           if (x.balance() == -1) {
/*      */             
/*  586 */             assert x.balance() == -1;
/*  587 */             Entry w = x.left;
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
/*  651 */         Entry x = y.left;
/*  652 */         assert x != null;
/*  653 */         if (x.balance() == 1) {
/*      */           
/*  655 */           assert x.balance() == 1;
/*  656 */           Entry w = x.right;
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
/*      */   public boolean containsValue(double v) {
/*  722 */     ValueIterator i = new ValueIterator();
/*      */     
/*  724 */     int j = this.count;
/*  725 */     while (j-- != 0) {
/*  726 */       double ev = i.nextDouble();
/*  727 */       if (Double.doubleToLongBits(ev) == Double.doubleToLongBits(v))
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
/*      */   private static final class Entry
/*      */     extends AbstractByte2DoubleMap.BasicEntry
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
/*      */     Entry left;
/*      */ 
/*      */     
/*      */     Entry right;
/*      */ 
/*      */     
/*      */     int info;
/*      */ 
/*      */ 
/*      */     
/*      */     Entry() {
/*  770 */       super((byte)0, 0.0D);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(byte k, double v) {
/*  781 */       super(k, v);
/*  782 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left() {
/*  790 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right() {
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
/*      */     void pred(Entry pred) {
/*  847 */       this.info |= 0x40000000;
/*  848 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry succ) {
/*  857 */       this.info |= Integer.MIN_VALUE;
/*  858 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry left) {
/*  867 */       this.info &= 0xBFFFFFFF;
/*  868 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry right) {
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
/*      */     Entry next() {
/*  912 */       Entry next = this.right;
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
/*      */     Entry prev() {
/*  924 */       Entry prev = this.left;
/*  925 */       if ((this.info & 0x40000000) == 0)
/*  926 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  927 */           prev = prev.right;  
/*  928 */       return prev;
/*      */     }
/*      */     
/*      */     public double setValue(double value) {
/*  932 */       double oldValue = this.value;
/*  933 */       this.value = value;
/*  934 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry clone() {
/*      */       Entry c;
/*      */       try {
/*  941 */         c = (Entry)super.clone();
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
/*  955 */       Map.Entry<Byte, Double> e = (Map.Entry<Byte, Double>)o;
/*  956 */       return (this.key == ((Byte)e.getKey()).byteValue() && 
/*  957 */         Double.doubleToLongBits(this.value) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  961 */       return this.key ^ HashCommon.double2int(this.value);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  965 */       return this.key + "=>" + this.value;
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
/*  986 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  990 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  994 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public double get(byte k) {
/*  999 */     Entry e = findKey(k);
/* 1000 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public byte firstByteKey() {
/* 1004 */     if (this.tree == null)
/* 1005 */       throw new NoSuchElementException(); 
/* 1006 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public byte lastByteKey() {
/* 1010 */     if (this.tree == null)
/* 1011 */       throw new NoSuchElementException(); 
/* 1012 */     return this.lastEntry.key;
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
/*      */     Byte2DoubleAVLTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Byte2DoubleAVLTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Byte2DoubleAVLTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1043 */     int index = 0;
/*      */     TreeIterator() {
/* 1045 */       this.next = Byte2DoubleAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(byte k) {
/* 1048 */       if ((this.next = Byte2DoubleAVLTreeMap.this.locateKey(k)) != null)
/* 1049 */         if (Byte2DoubleAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1050 */           this.prev = this.next;
/* 1051 */           this.next = this.next.next();
/*      */         } else {
/* 1053 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/* 1057 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1060 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1063 */       this.next = this.next.next();
/*      */     }
/*      */     Byte2DoubleAVLTreeMap.Entry nextEntry() {
/* 1066 */       if (!hasNext())
/* 1067 */         throw new NoSuchElementException(); 
/* 1068 */       this.curr = this.prev = this.next;
/* 1069 */       this.index++;
/* 1070 */       updateNext();
/* 1071 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1074 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Byte2DoubleAVLTreeMap.Entry previousEntry() {
/* 1077 */       if (!hasPrevious())
/* 1078 */         throw new NoSuchElementException(); 
/* 1079 */       this.curr = this.next = this.prev;
/* 1080 */       this.index--;
/* 1081 */       updatePrevious();
/* 1082 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1085 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1088 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1091 */       if (this.curr == null) {
/* 1092 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1097 */       if (this.curr == this.prev)
/* 1098 */         this.index--; 
/* 1099 */       this.next = this.prev = this.curr;
/* 1100 */       updatePrevious();
/* 1101 */       updateNext();
/* 1102 */       Byte2DoubleAVLTreeMap.this.remove(this.curr.key);
/* 1103 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1106 */       int i = n;
/* 1107 */       while (i-- != 0 && hasNext())
/* 1108 */         nextEntry(); 
/* 1109 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1112 */       int i = n;
/* 1113 */       while (i-- != 0 && hasPrevious())
/* 1114 */         previousEntry(); 
/* 1115 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Byte2DoubleMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(byte k) {
/* 1128 */       super(k);
/*      */     }
/*      */     
/*      */     public Byte2DoubleMap.Entry next() {
/* 1132 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Byte2DoubleMap.Entry previous() {
/* 1136 */       return previousEntry();
/*      */     }
/*      */     
/*      */     public void set(Byte2DoubleMap.Entry ok) {
/* 1140 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Byte2DoubleMap.Entry ok) {
/* 1144 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Byte2DoubleMap.Entry> byte2DoubleEntrySet() {
/* 1149 */     if (this.entries == null)
/* 1150 */       this.entries = (ObjectSortedSet<Byte2DoubleMap.Entry>)new AbstractObjectSortedSet<Byte2DoubleMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Byte2DoubleMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Byte2DoubleMap.Entry> comparator() {
/* 1155 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Byte2DoubleMap.Entry> iterator() {
/* 1159 */             return (ObjectBidirectionalIterator<Byte2DoubleMap.Entry>)new Byte2DoubleAVLTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Byte2DoubleMap.Entry> iterator(Byte2DoubleMap.Entry from) {
/* 1163 */             return (ObjectBidirectionalIterator<Byte2DoubleMap.Entry>)new Byte2DoubleAVLTreeMap.EntryIterator(from.getByteKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1168 */             if (!(o instanceof Map.Entry))
/* 1169 */               return false; 
/* 1170 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1171 */             if (e.getKey() == null || !(e.getKey() instanceof Byte))
/* 1172 */               return false; 
/* 1173 */             if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1174 */               return false; 
/* 1175 */             Byte2DoubleAVLTreeMap.Entry f = Byte2DoubleAVLTreeMap.this.findKey(((Byte)e.getKey()).byteValue());
/* 1176 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1181 */             if (!(o instanceof Map.Entry))
/* 1182 */               return false; 
/* 1183 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1184 */             if (e.getKey() == null || !(e.getKey() instanceof Byte))
/* 1185 */               return false; 
/* 1186 */             if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1187 */               return false; 
/* 1188 */             Byte2DoubleAVLTreeMap.Entry f = Byte2DoubleAVLTreeMap.this.findKey(((Byte)e.getKey()).byteValue());
/* 1189 */             if (f == null || Double.doubleToLongBits(f.getDoubleValue()) != 
/* 1190 */               Double.doubleToLongBits(((Double)e.getValue()).doubleValue()))
/* 1191 */               return false; 
/* 1192 */             Byte2DoubleAVLTreeMap.this.remove(f.key);
/* 1193 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1197 */             return Byte2DoubleAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1201 */             Byte2DoubleAVLTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Byte2DoubleMap.Entry first() {
/* 1205 */             return Byte2DoubleAVLTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Byte2DoubleMap.Entry last() {
/* 1209 */             return Byte2DoubleAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Byte2DoubleMap.Entry> subSet(Byte2DoubleMap.Entry from, Byte2DoubleMap.Entry to) {
/* 1214 */             return Byte2DoubleAVLTreeMap.this.subMap(from.getByteKey(), to.getByteKey()).byte2DoubleEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Byte2DoubleMap.Entry> headSet(Byte2DoubleMap.Entry to) {
/* 1218 */             return Byte2DoubleAVLTreeMap.this.headMap(to.getByteKey()).byte2DoubleEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Byte2DoubleMap.Entry> tailSet(Byte2DoubleMap.Entry from) {
/* 1222 */             return Byte2DoubleAVLTreeMap.this.tailMap(from.getByteKey()).byte2DoubleEntrySet();
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
/*      */     implements ByteListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(byte k) {
/* 1241 */       super(k);
/*      */     }
/*      */     
/*      */     public byte nextByte() {
/* 1245 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public byte previousByte() {
/* 1249 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractByte2DoubleSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ByteBidirectionalIterator iterator() {
/* 1256 */       return new Byte2DoubleAVLTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public ByteBidirectionalIterator iterator(byte from) {
/* 1260 */       return new Byte2DoubleAVLTreeMap.KeyIterator(from);
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
/*      */     implements DoubleListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1292 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public double previousDouble() {
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
/*      */   public DoubleCollection values() {
/* 1311 */     if (this.values == null)
/* 1312 */       this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1315 */             return (DoubleIterator)new Byte2DoubleAVLTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(double k) {
/* 1319 */             return Byte2DoubleAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1323 */             return Byte2DoubleAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1327 */             Byte2DoubleAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1330 */     return this.values;
/*      */   }
/*      */   
/*      */   public ByteComparator comparator() {
/* 1334 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Byte2DoubleSortedMap headMap(byte to) {
/* 1338 */     return new Submap((byte)0, true, to, false);
/*      */   }
/*      */   
/*      */   public Byte2DoubleSortedMap tailMap(byte from) {
/* 1342 */     return new Submap(from, false, (byte)0, true);
/*      */   }
/*      */   
/*      */   public Byte2DoubleSortedMap subMap(byte from, byte to) {
/* 1346 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractByte2DoubleSortedMap
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
/*      */     protected transient ObjectSortedSet<Byte2DoubleMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ByteSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient DoubleCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(byte from, boolean bottom, byte to, boolean top) {
/* 1390 */       if (!bottom && !top && Byte2DoubleAVLTreeMap.this.compare(from, to) > 0)
/* 1391 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1392 */       this.from = from;
/* 1393 */       this.bottom = bottom;
/* 1394 */       this.to = to;
/* 1395 */       this.top = top;
/* 1396 */       this.defRetValue = Byte2DoubleAVLTreeMap.this.defRetValue;
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
/*      */     final boolean in(byte k) {
/* 1414 */       return ((this.bottom || Byte2DoubleAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Byte2DoubleAVLTreeMap.this
/* 1415 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Byte2DoubleMap.Entry> byte2DoubleEntrySet() {
/* 1419 */       if (this.entries == null)
/* 1420 */         this.entries = (ObjectSortedSet<Byte2DoubleMap.Entry>)new AbstractObjectSortedSet<Byte2DoubleMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Byte2DoubleMap.Entry> iterator() {
/* 1423 */               return (ObjectBidirectionalIterator<Byte2DoubleMap.Entry>)new Byte2DoubleAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */             
/*      */             public ObjectBidirectionalIterator<Byte2DoubleMap.Entry> iterator(Byte2DoubleMap.Entry from) {
/* 1427 */               return (ObjectBidirectionalIterator<Byte2DoubleMap.Entry>)new Byte2DoubleAVLTreeMap.Submap.SubmapEntryIterator(from.getByteKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Byte2DoubleMap.Entry> comparator() {
/* 1431 */               return Byte2DoubleAVLTreeMap.this.byte2DoubleEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1436 */               if (!(o instanceof Map.Entry))
/* 1437 */                 return false; 
/* 1438 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1439 */               if (e.getKey() == null || !(e.getKey() instanceof Byte))
/* 1440 */                 return false; 
/* 1441 */               if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1442 */                 return false; 
/* 1443 */               Byte2DoubleAVLTreeMap.Entry f = Byte2DoubleAVLTreeMap.this.findKey(((Byte)e.getKey()).byteValue());
/* 1444 */               return (f != null && Byte2DoubleAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1449 */               if (!(o instanceof Map.Entry))
/* 1450 */                 return false; 
/* 1451 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1452 */               if (e.getKey() == null || !(e.getKey() instanceof Byte))
/* 1453 */                 return false; 
/* 1454 */               if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1455 */                 return false; 
/* 1456 */               Byte2DoubleAVLTreeMap.Entry f = Byte2DoubleAVLTreeMap.this.findKey(((Byte)e.getKey()).byteValue());
/* 1457 */               if (f != null && Byte2DoubleAVLTreeMap.Submap.this.in(f.key))
/* 1458 */                 Byte2DoubleAVLTreeMap.Submap.this.remove(f.key); 
/* 1459 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1463 */               int c = 0;
/* 1464 */               for (ObjectBidirectionalIterator<Byte2DoubleMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1465 */                 c++; 
/* 1466 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1470 */               return !(new Byte2DoubleAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1474 */               Byte2DoubleAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Byte2DoubleMap.Entry first() {
/* 1478 */               return Byte2DoubleAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Byte2DoubleMap.Entry last() {
/* 1482 */               return Byte2DoubleAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Byte2DoubleMap.Entry> subSet(Byte2DoubleMap.Entry from, Byte2DoubleMap.Entry to) {
/* 1487 */               return Byte2DoubleAVLTreeMap.Submap.this.subMap(from.getByteKey(), to.getByteKey()).byte2DoubleEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Byte2DoubleMap.Entry> headSet(Byte2DoubleMap.Entry to) {
/* 1491 */               return Byte2DoubleAVLTreeMap.Submap.this.headMap(to.getByteKey()).byte2DoubleEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Byte2DoubleMap.Entry> tailSet(Byte2DoubleMap.Entry from) {
/* 1495 */               return Byte2DoubleAVLTreeMap.Submap.this.tailMap(from.getByteKey()).byte2DoubleEntrySet();
/*      */             }
/*      */           }; 
/* 1498 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractByte2DoubleSortedMap.KeySet {
/*      */       public ByteBidirectionalIterator iterator() {
/* 1503 */         return new Byte2DoubleAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public ByteBidirectionalIterator iterator(byte from) {
/* 1507 */         return new Byte2DoubleAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public ByteSortedSet keySet() {
/* 1512 */       if (this.keys == null)
/* 1513 */         this.keys = new KeySet(); 
/* 1514 */       return this.keys;
/*      */     }
/*      */     
/*      */     public DoubleCollection values() {
/* 1518 */       if (this.values == null)
/* 1519 */         this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */           {
/*      */             public DoubleIterator iterator() {
/* 1522 */               return (DoubleIterator)new Byte2DoubleAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(double k) {
/* 1526 */               return Byte2DoubleAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1530 */               return Byte2DoubleAVLTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1534 */               Byte2DoubleAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1537 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(byte k) {
/* 1542 */       return (in(k) && Byte2DoubleAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(double v) {
/* 1546 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1548 */       while (i.hasNext()) {
/* 1549 */         double ev = (i.nextEntry()).value;
/* 1550 */         if (Double.doubleToLongBits(ev) == Double.doubleToLongBits(v))
/* 1551 */           return true; 
/*      */       } 
/* 1553 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public double get(byte k) {
/* 1559 */       byte kk = k; Byte2DoubleAVLTreeMap.Entry e;
/* 1560 */       return (in(kk) && (e = Byte2DoubleAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public double put(byte k, double v) {
/* 1564 */       Byte2DoubleAVLTreeMap.this.modified = false;
/* 1565 */       if (!in(k))
/* 1566 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1567 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1568 */       double oldValue = Byte2DoubleAVLTreeMap.this.put(k, v);
/* 1569 */       return Byte2DoubleAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public double remove(byte k) {
/* 1574 */       Byte2DoubleAVLTreeMap.this.modified = false;
/* 1575 */       if (!in(k))
/* 1576 */         return this.defRetValue; 
/* 1577 */       double oldValue = Byte2DoubleAVLTreeMap.this.remove(k);
/* 1578 */       return Byte2DoubleAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1582 */       SubmapIterator i = new SubmapIterator();
/* 1583 */       int n = 0;
/* 1584 */       while (i.hasNext()) {
/* 1585 */         n++;
/* 1586 */         i.nextEntry();
/*      */       } 
/* 1588 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1592 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public ByteComparator comparator() {
/* 1596 */       return Byte2DoubleAVLTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Byte2DoubleSortedMap headMap(byte to) {
/* 1600 */       if (this.top)
/* 1601 */         return new Submap(this.from, this.bottom, to, false); 
/* 1602 */       return (Byte2DoubleAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Byte2DoubleSortedMap tailMap(byte from) {
/* 1606 */       if (this.bottom)
/* 1607 */         return new Submap(from, false, this.to, this.top); 
/* 1608 */       return (Byte2DoubleAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Byte2DoubleSortedMap subMap(byte from, byte to) {
/* 1612 */       if (this.top && this.bottom)
/* 1613 */         return new Submap(from, false, to, false); 
/* 1614 */       if (!this.top)
/* 1615 */         to = (Byte2DoubleAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1616 */       if (!this.bottom)
/* 1617 */         from = (Byte2DoubleAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1618 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1619 */         return this; 
/* 1620 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Byte2DoubleAVLTreeMap.Entry firstEntry() {
/*      */       Byte2DoubleAVLTreeMap.Entry e;
/* 1629 */       if (Byte2DoubleAVLTreeMap.this.tree == null) {
/* 1630 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1634 */       if (this.bottom) {
/* 1635 */         e = Byte2DoubleAVLTreeMap.this.firstEntry;
/*      */       } else {
/* 1637 */         e = Byte2DoubleAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1639 */         if (Byte2DoubleAVLTreeMap.this.compare(e.key, this.from) < 0) {
/* 1640 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1644 */       if (e == null || (!this.top && Byte2DoubleAVLTreeMap.this.compare(e.key, this.to) >= 0))
/* 1645 */         return null; 
/* 1646 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Byte2DoubleAVLTreeMap.Entry lastEntry() {
/*      */       Byte2DoubleAVLTreeMap.Entry e;
/* 1655 */       if (Byte2DoubleAVLTreeMap.this.tree == null) {
/* 1656 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1660 */       if (this.top) {
/* 1661 */         e = Byte2DoubleAVLTreeMap.this.lastEntry;
/*      */       } else {
/* 1663 */         e = Byte2DoubleAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1665 */         if (Byte2DoubleAVLTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1666 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1670 */       if (e == null || (!this.bottom && Byte2DoubleAVLTreeMap.this.compare(e.key, this.from) < 0))
/* 1671 */         return null; 
/* 1672 */       return e;
/*      */     }
/*      */     
/*      */     public byte firstByteKey() {
/* 1676 */       Byte2DoubleAVLTreeMap.Entry e = firstEntry();
/* 1677 */       if (e == null)
/* 1678 */         throw new NoSuchElementException(); 
/* 1679 */       return e.key;
/*      */     }
/*      */     
/*      */     public byte lastByteKey() {
/* 1683 */       Byte2DoubleAVLTreeMap.Entry e = lastEntry();
/* 1684 */       if (e == null)
/* 1685 */         throw new NoSuchElementException(); 
/* 1686 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Byte2DoubleAVLTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1699 */         this.next = Byte2DoubleAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(byte k) {
/* 1702 */         this();
/* 1703 */         if (this.next != null)
/* 1704 */           if (!Byte2DoubleAVLTreeMap.Submap.this.bottom && Byte2DoubleAVLTreeMap.this.compare(k, this.next.key) < 0) {
/* 1705 */             this.prev = null;
/* 1706 */           } else if (!Byte2DoubleAVLTreeMap.Submap.this.top && Byte2DoubleAVLTreeMap.this.compare(k, (this.prev = Byte2DoubleAVLTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1707 */             this.next = null;
/*      */           } else {
/* 1709 */             this.next = Byte2DoubleAVLTreeMap.this.locateKey(k);
/* 1710 */             if (Byte2DoubleAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1711 */               this.prev = this.next;
/* 1712 */               this.next = this.next.next();
/*      */             } else {
/* 1714 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1720 */         this.prev = this.prev.prev();
/* 1721 */         if (!Byte2DoubleAVLTreeMap.Submap.this.bottom && this.prev != null && Byte2DoubleAVLTreeMap.this.compare(this.prev.key, Byte2DoubleAVLTreeMap.Submap.this.from) < 0)
/* 1722 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1726 */         this.next = this.next.next();
/* 1727 */         if (!Byte2DoubleAVLTreeMap.Submap.this.top && this.next != null && Byte2DoubleAVLTreeMap.this.compare(this.next.key, Byte2DoubleAVLTreeMap.Submap.this.to) >= 0)
/* 1728 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Byte2DoubleMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(byte k) {
/* 1735 */         super(k);
/*      */       }
/*      */       
/*      */       public Byte2DoubleMap.Entry next() {
/* 1739 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Byte2DoubleMap.Entry previous() {
/* 1743 */         return previousEntry();
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
/* 1761 */         super(from);
/*      */       }
/*      */       
/*      */       public byte nextByte() {
/* 1765 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public byte previousByte() {
/* 1769 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements DoubleListIterator
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public double nextDouble() {
/* 1785 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public double previousDouble() {
/* 1789 */         return (previousEntry()).value;
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
/*      */   public Byte2DoubleAVLTreeMap clone() {
/*      */     Byte2DoubleAVLTreeMap c;
/*      */     try {
/* 1808 */       c = (Byte2DoubleAVLTreeMap)super.clone();
/* 1809 */     } catch (CloneNotSupportedException cantHappen) {
/* 1810 */       throw new InternalError();
/*      */     } 
/* 1812 */     c.keys = null;
/* 1813 */     c.values = null;
/* 1814 */     c.entries = null;
/* 1815 */     c.allocatePaths();
/* 1816 */     if (this.count != 0) {
/*      */       
/* 1818 */       Entry rp = new Entry(), rq = new Entry();
/* 1819 */       Entry p = rp;
/* 1820 */       rp.left(this.tree);
/* 1821 */       Entry q = rq;
/* 1822 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1824 */         if (!p.pred()) {
/* 1825 */           Entry e = p.left.clone();
/* 1826 */           e.pred(q.left);
/* 1827 */           e.succ(q);
/* 1828 */           q.left(e);
/* 1829 */           p = p.left;
/* 1830 */           q = q.left;
/*      */         } else {
/* 1832 */           while (p.succ()) {
/* 1833 */             p = p.right;
/* 1834 */             if (p == null) {
/* 1835 */               q.right = null;
/* 1836 */               c.tree = rq.left;
/* 1837 */               c.firstEntry = c.tree;
/* 1838 */               while (c.firstEntry.left != null)
/* 1839 */                 c.firstEntry = c.firstEntry.left; 
/* 1840 */               c.lastEntry = c.tree;
/* 1841 */               while (c.lastEntry.right != null)
/* 1842 */                 c.lastEntry = c.lastEntry.right; 
/* 1843 */               return c;
/*      */             } 
/* 1845 */             q = q.right;
/*      */           } 
/* 1847 */           p = p.right;
/* 1848 */           q = q.right;
/*      */         } 
/* 1850 */         if (!p.succ()) {
/* 1851 */           Entry e = p.right.clone();
/* 1852 */           e.succ(q.right);
/* 1853 */           e.pred(q);
/* 1854 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1858 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1861 */     int n = this.count;
/* 1862 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1864 */     s.defaultWriteObject();
/* 1865 */     while (n-- != 0) {
/* 1866 */       Entry e = i.nextEntry();
/* 1867 */       s.writeByte(e.key);
/* 1868 */       s.writeDouble(e.value);
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
/*      */   private Entry readTree(ObjectInputStream s, int n, Entry pred, Entry succ) throws IOException, ClassNotFoundException {
/* 1889 */     if (n == 1) {
/* 1890 */       Entry entry = new Entry(s.readByte(), s.readDouble());
/* 1891 */       entry.pred(pred);
/* 1892 */       entry.succ(succ);
/* 1893 */       return entry;
/*      */     } 
/* 1895 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1900 */       Entry entry = new Entry(s.readByte(), s.readDouble());
/* 1901 */       entry.right(new Entry(s.readByte(), s.readDouble()));
/* 1902 */       entry.right.pred(entry);
/* 1903 */       entry.balance(1);
/* 1904 */       entry.pred(pred);
/* 1905 */       entry.right.succ(succ);
/* 1906 */       return entry;
/*      */     } 
/*      */     
/* 1909 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1910 */     Entry top = new Entry();
/* 1911 */     top.left(readTree(s, leftN, pred, top));
/* 1912 */     top.key = s.readByte();
/* 1913 */     top.value = s.readDouble();
/* 1914 */     top.right(readTree(s, rightN, top, succ));
/* 1915 */     if (n == (n & -n))
/* 1916 */       top.balance(1); 
/* 1917 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1920 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1925 */     setActualComparator();
/* 1926 */     allocatePaths();
/* 1927 */     if (this.count != 0) {
/* 1928 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1930 */       Entry e = this.tree;
/* 1931 */       while (e.left() != null)
/* 1932 */         e = e.left(); 
/* 1933 */       this.firstEntry = e;
/* 1934 */       e = this.tree;
/* 1935 */       while (e.right() != null)
/* 1936 */         e = e.right(); 
/* 1937 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2DoubleAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */