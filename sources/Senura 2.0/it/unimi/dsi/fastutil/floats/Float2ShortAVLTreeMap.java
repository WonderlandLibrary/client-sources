/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortListIterator;
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
/*      */ public class Float2ShortAVLTreeMap
/*      */   extends AbstractFloat2ShortSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Float2ShortMap.Entry> entries;
/*      */   protected transient FloatSortedSet keys;
/*      */   protected transient ShortCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Float> storedComparator;
/*      */   protected transient FloatComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Float2ShortAVLTreeMap() {
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
/*   89 */     this.actualComparator = FloatComparators.asFloatComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ShortAVLTreeMap(Comparator<? super Float> c) {
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
/*      */   public Float2ShortAVLTreeMap(Map<? extends Float, ? extends Short> m) {
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
/*      */   public Float2ShortAVLTreeMap(SortedMap<Float, Short> m) {
/*  120 */     this(m.comparator());
/*  121 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ShortAVLTreeMap(Float2ShortMap m) {
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
/*      */   public Float2ShortAVLTreeMap(Float2ShortSortedMap m) {
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
/*      */   public Float2ShortAVLTreeMap(float[] k, short[] v, Comparator<? super Float> c) {
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
/*      */   public Float2ShortAVLTreeMap(float[] k, short[] v) {
/*  176 */     this(k, v, (Comparator<? super Float>)null);
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
/*  204 */     return (this.actualComparator == null) ? Float.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry findKey(float k) {
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
/*      */   final Entry locateKey(float k) {
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
/*      */   public short addTo(float k, short incr) {
/*  265 */     Entry e = add(k);
/*  266 */     short oldValue = e.value;
/*  267 */     e.value = (short)(e.value + incr);
/*  268 */     return oldValue;
/*      */   }
/*      */   
/*      */   public short put(float k, short v) {
/*  272 */     Entry e = add(k);
/*  273 */     short oldValue = e.value;
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
/*      */   private Entry add(float k) {
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
/*      */   public short remove(float k) {
/*  481 */     this.modified = false;
/*  482 */     if (this.tree == null) {
/*  483 */       return this.defRetValue;
/*      */     }
/*  485 */     Entry p = this.tree, q = null;
/*  486 */     boolean dir = false;
/*  487 */     float kk = k;
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
/*      */   public boolean containsValue(short v) {
/*  722 */     ValueIterator i = new ValueIterator();
/*      */     
/*  724 */     int j = this.count;
/*  725 */     while (j-- != 0) {
/*  726 */       short ev = i.nextShort();
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
/*      */   private static final class Entry
/*      */     extends AbstractFloat2ShortMap.BasicEntry
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
/*  770 */       super(0.0F, (short)0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(float k, short v) {
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
/*      */     public short setValue(short value) {
/*  932 */       short oldValue = this.value;
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
/*  955 */       Map.Entry<Float, Short> e = (Map.Entry<Float, Short>)o;
/*  956 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && this.value == ((Short)e
/*  957 */         .getValue()).shortValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  961 */       return HashCommon.float2int(this.key) ^ this.value;
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
/*      */   public boolean containsKey(float k) {
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
/*      */   public short get(float k) {
/*  999 */     Entry e = findKey(k);
/* 1000 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public float firstFloatKey() {
/* 1004 */     if (this.tree == null)
/* 1005 */       throw new NoSuchElementException(); 
/* 1006 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public float lastFloatKey() {
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
/*      */     Float2ShortAVLTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Float2ShortAVLTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Float2ShortAVLTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1043 */     int index = 0;
/*      */     TreeIterator() {
/* 1045 */       this.next = Float2ShortAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(float k) {
/* 1048 */       if ((this.next = Float2ShortAVLTreeMap.this.locateKey(k)) != null)
/* 1049 */         if (Float2ShortAVLTreeMap.this.compare(this.next.key, k) <= 0) {
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
/*      */     Float2ShortAVLTreeMap.Entry nextEntry() {
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
/*      */     Float2ShortAVLTreeMap.Entry previousEntry() {
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
/* 1102 */       Float2ShortAVLTreeMap.this.remove(this.curr.key);
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
/*      */     implements ObjectListIterator<Float2ShortMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(float k) {
/* 1128 */       super(k);
/*      */     }
/*      */     
/*      */     public Float2ShortMap.Entry next() {
/* 1132 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Float2ShortMap.Entry previous() {
/* 1136 */       return previousEntry();
/*      */     }
/*      */     
/*      */     public void set(Float2ShortMap.Entry ok) {
/* 1140 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Float2ShortMap.Entry ok) {
/* 1144 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Float2ShortMap.Entry> float2ShortEntrySet() {
/* 1149 */     if (this.entries == null)
/* 1150 */       this.entries = (ObjectSortedSet<Float2ShortMap.Entry>)new AbstractObjectSortedSet<Float2ShortMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Float2ShortMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Float2ShortMap.Entry> comparator() {
/* 1155 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Float2ShortMap.Entry> iterator() {
/* 1159 */             return (ObjectBidirectionalIterator<Float2ShortMap.Entry>)new Float2ShortAVLTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Float2ShortMap.Entry> iterator(Float2ShortMap.Entry from) {
/* 1163 */             return (ObjectBidirectionalIterator<Float2ShortMap.Entry>)new Float2ShortAVLTreeMap.EntryIterator(from.getFloatKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1168 */             if (!(o instanceof Map.Entry))
/* 1169 */               return false; 
/* 1170 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1171 */             if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1172 */               return false; 
/* 1173 */             if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1174 */               return false; 
/* 1175 */             Float2ShortAVLTreeMap.Entry f = Float2ShortAVLTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1176 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1181 */             if (!(o instanceof Map.Entry))
/* 1182 */               return false; 
/* 1183 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1184 */             if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1185 */               return false; 
/* 1186 */             if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1187 */               return false; 
/* 1188 */             Float2ShortAVLTreeMap.Entry f = Float2ShortAVLTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1189 */             if (f == null || f.getShortValue() != ((Short)e.getValue()).shortValue())
/* 1190 */               return false; 
/* 1191 */             Float2ShortAVLTreeMap.this.remove(f.key);
/* 1192 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1196 */             return Float2ShortAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1200 */             Float2ShortAVLTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Float2ShortMap.Entry first() {
/* 1204 */             return Float2ShortAVLTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Float2ShortMap.Entry last() {
/* 1208 */             return Float2ShortAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Float2ShortMap.Entry> subSet(Float2ShortMap.Entry from, Float2ShortMap.Entry to) {
/* 1213 */             return Float2ShortAVLTreeMap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2ShortEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Float2ShortMap.Entry> headSet(Float2ShortMap.Entry to) {
/* 1217 */             return Float2ShortAVLTreeMap.this.headMap(to.getFloatKey()).float2ShortEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Float2ShortMap.Entry> tailSet(Float2ShortMap.Entry from) {
/* 1221 */             return Float2ShortAVLTreeMap.this.tailMap(from.getFloatKey()).float2ShortEntrySet();
/*      */           }
/*      */         }; 
/* 1224 */     return this.entries;
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
/* 1240 */       super(k);
/*      */     }
/*      */     
/*      */     public float nextFloat() {
/* 1244 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public float previousFloat() {
/* 1248 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractFloat2ShortSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public FloatBidirectionalIterator iterator() {
/* 1255 */       return new Float2ShortAVLTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public FloatBidirectionalIterator iterator(float from) {
/* 1259 */       return new Float2ShortAVLTreeMap.KeyIterator(from);
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
/* 1274 */     if (this.keys == null)
/* 1275 */       this.keys = new KeySet(); 
/* 1276 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements ShortListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public short nextShort() {
/* 1291 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public short previousShort() {
/* 1295 */       return (previousEntry()).value;
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
/*      */   public ShortCollection values() {
/* 1310 */     if (this.values == null)
/* 1311 */       this.values = (ShortCollection)new AbstractShortCollection()
/*      */         {
/*      */           public ShortIterator iterator() {
/* 1314 */             return (ShortIterator)new Float2ShortAVLTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(short k) {
/* 1318 */             return Float2ShortAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1322 */             return Float2ShortAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1326 */             Float2ShortAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1329 */     return this.values;
/*      */   }
/*      */   
/*      */   public FloatComparator comparator() {
/* 1333 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Float2ShortSortedMap headMap(float to) {
/* 1337 */     return new Submap(0.0F, true, to, false);
/*      */   }
/*      */   
/*      */   public Float2ShortSortedMap tailMap(float from) {
/* 1341 */     return new Submap(from, false, 0.0F, true);
/*      */   }
/*      */   
/*      */   public Float2ShortSortedMap subMap(float from, float to) {
/* 1345 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractFloat2ShortSortedMap
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
/*      */     protected transient ObjectSortedSet<Float2ShortMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient FloatSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ShortCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(float from, boolean bottom, float to, boolean top) {
/* 1389 */       if (!bottom && !top && Float2ShortAVLTreeMap.this.compare(from, to) > 0)
/* 1390 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1391 */       this.from = from;
/* 1392 */       this.bottom = bottom;
/* 1393 */       this.to = to;
/* 1394 */       this.top = top;
/* 1395 */       this.defRetValue = Float2ShortAVLTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1399 */       SubmapIterator i = new SubmapIterator();
/* 1400 */       while (i.hasNext()) {
/* 1401 */         i.nextEntry();
/* 1402 */         i.remove();
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
/* 1413 */       return ((this.bottom || Float2ShortAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Float2ShortAVLTreeMap.this
/* 1414 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Float2ShortMap.Entry> float2ShortEntrySet() {
/* 1418 */       if (this.entries == null)
/* 1419 */         this.entries = (ObjectSortedSet<Float2ShortMap.Entry>)new AbstractObjectSortedSet<Float2ShortMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Float2ShortMap.Entry> iterator() {
/* 1422 */               return (ObjectBidirectionalIterator<Float2ShortMap.Entry>)new Float2ShortAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */             
/*      */             public ObjectBidirectionalIterator<Float2ShortMap.Entry> iterator(Float2ShortMap.Entry from) {
/* 1426 */               return (ObjectBidirectionalIterator<Float2ShortMap.Entry>)new Float2ShortAVLTreeMap.Submap.SubmapEntryIterator(from.getFloatKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Float2ShortMap.Entry> comparator() {
/* 1430 */               return Float2ShortAVLTreeMap.this.float2ShortEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1435 */               if (!(o instanceof Map.Entry))
/* 1436 */                 return false; 
/* 1437 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1438 */               if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1439 */                 return false; 
/* 1440 */               if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1441 */                 return false; 
/* 1442 */               Float2ShortAVLTreeMap.Entry f = Float2ShortAVLTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1443 */               return (f != null && Float2ShortAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1448 */               if (!(o instanceof Map.Entry))
/* 1449 */                 return false; 
/* 1450 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1451 */               if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1452 */                 return false; 
/* 1453 */               if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1454 */                 return false; 
/* 1455 */               Float2ShortAVLTreeMap.Entry f = Float2ShortAVLTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1456 */               if (f != null && Float2ShortAVLTreeMap.Submap.this.in(f.key))
/* 1457 */                 Float2ShortAVLTreeMap.Submap.this.remove(f.key); 
/* 1458 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1462 */               int c = 0;
/* 1463 */               for (ObjectBidirectionalIterator<Float2ShortMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1464 */                 c++; 
/* 1465 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1469 */               return !(new Float2ShortAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1473 */               Float2ShortAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Float2ShortMap.Entry first() {
/* 1477 */               return Float2ShortAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Float2ShortMap.Entry last() {
/* 1481 */               return Float2ShortAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Float2ShortMap.Entry> subSet(Float2ShortMap.Entry from, Float2ShortMap.Entry to) {
/* 1486 */               return Float2ShortAVLTreeMap.Submap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2ShortEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Float2ShortMap.Entry> headSet(Float2ShortMap.Entry to) {
/* 1490 */               return Float2ShortAVLTreeMap.Submap.this.headMap(to.getFloatKey()).float2ShortEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Float2ShortMap.Entry> tailSet(Float2ShortMap.Entry from) {
/* 1494 */               return Float2ShortAVLTreeMap.Submap.this.tailMap(from.getFloatKey()).float2ShortEntrySet();
/*      */             }
/*      */           }; 
/* 1497 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractFloat2ShortSortedMap.KeySet {
/*      */       public FloatBidirectionalIterator iterator() {
/* 1502 */         return new Float2ShortAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public FloatBidirectionalIterator iterator(float from) {
/* 1506 */         return new Float2ShortAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public FloatSortedSet keySet() {
/* 1511 */       if (this.keys == null)
/* 1512 */         this.keys = new KeySet(); 
/* 1513 */       return this.keys;
/*      */     }
/*      */     
/*      */     public ShortCollection values() {
/* 1517 */       if (this.values == null)
/* 1518 */         this.values = (ShortCollection)new AbstractShortCollection()
/*      */           {
/*      */             public ShortIterator iterator() {
/* 1521 */               return (ShortIterator)new Float2ShortAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(short k) {
/* 1525 */               return Float2ShortAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1529 */               return Float2ShortAVLTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1533 */               Float2ShortAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1536 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(float k) {
/* 1541 */       return (in(k) && Float2ShortAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(short v) {
/* 1545 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1547 */       while (i.hasNext()) {
/* 1548 */         short ev = (i.nextEntry()).value;
/* 1549 */         if (ev == v)
/* 1550 */           return true; 
/*      */       } 
/* 1552 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public short get(float k) {
/* 1558 */       float kk = k; Float2ShortAVLTreeMap.Entry e;
/* 1559 */       return (in(kk) && (e = Float2ShortAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public short put(float k, short v) {
/* 1563 */       Float2ShortAVLTreeMap.this.modified = false;
/* 1564 */       if (!in(k))
/* 1565 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1566 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1567 */       short oldValue = Float2ShortAVLTreeMap.this.put(k, v);
/* 1568 */       return Float2ShortAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public short remove(float k) {
/* 1573 */       Float2ShortAVLTreeMap.this.modified = false;
/* 1574 */       if (!in(k))
/* 1575 */         return this.defRetValue; 
/* 1576 */       short oldValue = Float2ShortAVLTreeMap.this.remove(k);
/* 1577 */       return Float2ShortAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1581 */       SubmapIterator i = new SubmapIterator();
/* 1582 */       int n = 0;
/* 1583 */       while (i.hasNext()) {
/* 1584 */         n++;
/* 1585 */         i.nextEntry();
/*      */       } 
/* 1587 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1591 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public FloatComparator comparator() {
/* 1595 */       return Float2ShortAVLTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Float2ShortSortedMap headMap(float to) {
/* 1599 */       if (this.top)
/* 1600 */         return new Submap(this.from, this.bottom, to, false); 
/* 1601 */       return (Float2ShortAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Float2ShortSortedMap tailMap(float from) {
/* 1605 */       if (this.bottom)
/* 1606 */         return new Submap(from, false, this.to, this.top); 
/* 1607 */       return (Float2ShortAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Float2ShortSortedMap subMap(float from, float to) {
/* 1611 */       if (this.top && this.bottom)
/* 1612 */         return new Submap(from, false, to, false); 
/* 1613 */       if (!this.top)
/* 1614 */         to = (Float2ShortAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1615 */       if (!this.bottom)
/* 1616 */         from = (Float2ShortAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1617 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1618 */         return this; 
/* 1619 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Float2ShortAVLTreeMap.Entry firstEntry() {
/*      */       Float2ShortAVLTreeMap.Entry e;
/* 1628 */       if (Float2ShortAVLTreeMap.this.tree == null) {
/* 1629 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1633 */       if (this.bottom) {
/* 1634 */         e = Float2ShortAVLTreeMap.this.firstEntry;
/*      */       } else {
/* 1636 */         e = Float2ShortAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1638 */         if (Float2ShortAVLTreeMap.this.compare(e.key, this.from) < 0) {
/* 1639 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1643 */       if (e == null || (!this.top && Float2ShortAVLTreeMap.this.compare(e.key, this.to) >= 0))
/* 1644 */         return null; 
/* 1645 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Float2ShortAVLTreeMap.Entry lastEntry() {
/*      */       Float2ShortAVLTreeMap.Entry e;
/* 1654 */       if (Float2ShortAVLTreeMap.this.tree == null) {
/* 1655 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1659 */       if (this.top) {
/* 1660 */         e = Float2ShortAVLTreeMap.this.lastEntry;
/*      */       } else {
/* 1662 */         e = Float2ShortAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1664 */         if (Float2ShortAVLTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1665 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1669 */       if (e == null || (!this.bottom && Float2ShortAVLTreeMap.this.compare(e.key, this.from) < 0))
/* 1670 */         return null; 
/* 1671 */       return e;
/*      */     }
/*      */     
/*      */     public float firstFloatKey() {
/* 1675 */       Float2ShortAVLTreeMap.Entry e = firstEntry();
/* 1676 */       if (e == null)
/* 1677 */         throw new NoSuchElementException(); 
/* 1678 */       return e.key;
/*      */     }
/*      */     
/*      */     public float lastFloatKey() {
/* 1682 */       Float2ShortAVLTreeMap.Entry e = lastEntry();
/* 1683 */       if (e == null)
/* 1684 */         throw new NoSuchElementException(); 
/* 1685 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Float2ShortAVLTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1698 */         this.next = Float2ShortAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(float k) {
/* 1701 */         this();
/* 1702 */         if (this.next != null)
/* 1703 */           if (!Float2ShortAVLTreeMap.Submap.this.bottom && Float2ShortAVLTreeMap.this.compare(k, this.next.key) < 0) {
/* 1704 */             this.prev = null;
/* 1705 */           } else if (!Float2ShortAVLTreeMap.Submap.this.top && Float2ShortAVLTreeMap.this.compare(k, (this.prev = Float2ShortAVLTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1706 */             this.next = null;
/*      */           } else {
/* 1708 */             this.next = Float2ShortAVLTreeMap.this.locateKey(k);
/* 1709 */             if (Float2ShortAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1710 */               this.prev = this.next;
/* 1711 */               this.next = this.next.next();
/*      */             } else {
/* 1713 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1719 */         this.prev = this.prev.prev();
/* 1720 */         if (!Float2ShortAVLTreeMap.Submap.this.bottom && this.prev != null && Float2ShortAVLTreeMap.this.compare(this.prev.key, Float2ShortAVLTreeMap.Submap.this.from) < 0)
/* 1721 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1725 */         this.next = this.next.next();
/* 1726 */         if (!Float2ShortAVLTreeMap.Submap.this.top && this.next != null && Float2ShortAVLTreeMap.this.compare(this.next.key, Float2ShortAVLTreeMap.Submap.this.to) >= 0)
/* 1727 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Float2ShortMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(float k) {
/* 1734 */         super(k);
/*      */       }
/*      */       
/*      */       public Float2ShortMap.Entry next() {
/* 1738 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Float2ShortMap.Entry previous() {
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
/*      */       implements FloatListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(float from) {
/* 1760 */         super(from);
/*      */       }
/*      */       
/*      */       public float nextFloat() {
/* 1764 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public float previousFloat() {
/* 1768 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements ShortListIterator
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public short nextShort() {
/* 1784 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public short previousShort() {
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
/*      */   public Float2ShortAVLTreeMap clone() {
/*      */     Float2ShortAVLTreeMap c;
/*      */     try {
/* 1807 */       c = (Float2ShortAVLTreeMap)super.clone();
/* 1808 */     } catch (CloneNotSupportedException cantHappen) {
/* 1809 */       throw new InternalError();
/*      */     } 
/* 1811 */     c.keys = null;
/* 1812 */     c.values = null;
/* 1813 */     c.entries = null;
/* 1814 */     c.allocatePaths();
/* 1815 */     if (this.count != 0) {
/*      */       
/* 1817 */       Entry rp = new Entry(), rq = new Entry();
/* 1818 */       Entry p = rp;
/* 1819 */       rp.left(this.tree);
/* 1820 */       Entry q = rq;
/* 1821 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1823 */         if (!p.pred()) {
/* 1824 */           Entry e = p.left.clone();
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
/* 1850 */           Entry e = p.right.clone();
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
/* 1865 */       Entry e = i.nextEntry();
/* 1866 */       s.writeFloat(e.key);
/* 1867 */       s.writeShort(e.value);
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
/* 1888 */     if (n == 1) {
/* 1889 */       Entry entry = new Entry(s.readFloat(), s.readShort());
/* 1890 */       entry.pred(pred);
/* 1891 */       entry.succ(succ);
/* 1892 */       return entry;
/*      */     } 
/* 1894 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1899 */       Entry entry = new Entry(s.readFloat(), s.readShort());
/* 1900 */       entry.right(new Entry(s.readFloat(), s.readShort()));
/* 1901 */       entry.right.pred(entry);
/* 1902 */       entry.balance(1);
/* 1903 */       entry.pred(pred);
/* 1904 */       entry.right.succ(succ);
/* 1905 */       return entry;
/*      */     } 
/*      */     
/* 1908 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1909 */     Entry top = new Entry();
/* 1910 */     top.left(readTree(s, leftN, pred, top));
/* 1911 */     top.key = s.readFloat();
/* 1912 */     top.value = s.readShort();
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
/* 1927 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1929 */       Entry e = this.tree;
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2ShortAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */