/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteListIterator;
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
/*      */ public class Long2ByteAVLTreeMap
/*      */   extends AbstractLong2ByteSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Long2ByteMap.Entry> entries;
/*      */   protected transient LongSortedSet keys;
/*      */   protected transient ByteCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Long> storedComparator;
/*      */   protected transient LongComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Long2ByteAVLTreeMap() {
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
/*   89 */     this.actualComparator = LongComparators.asLongComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ByteAVLTreeMap(Comparator<? super Long> c) {
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
/*      */   public Long2ByteAVLTreeMap(Map<? extends Long, ? extends Byte> m) {
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
/*      */   public Long2ByteAVLTreeMap(SortedMap<Long, Byte> m) {
/*  120 */     this(m.comparator());
/*  121 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ByteAVLTreeMap(Long2ByteMap m) {
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
/*      */   public Long2ByteAVLTreeMap(Long2ByteSortedMap m) {
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
/*      */   public Long2ByteAVLTreeMap(long[] k, byte[] v, Comparator<? super Long> c) {
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
/*      */   public Long2ByteAVLTreeMap(long[] k, byte[] v) {
/*  176 */     this(k, v, (Comparator<? super Long>)null);
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
/*      */   final int compare(long k1, long k2) {
/*  204 */     return (this.actualComparator == null) ? Long.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry findKey(long k) {
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
/*      */   final Entry locateKey(long k) {
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
/*      */   public byte addTo(long k, byte incr) {
/*  265 */     Entry e = add(k);
/*  266 */     byte oldValue = e.value;
/*  267 */     e.value = (byte)(e.value + incr);
/*  268 */     return oldValue;
/*      */   }
/*      */   
/*      */   public byte put(long k, byte v) {
/*  272 */     Entry e = add(k);
/*  273 */     byte oldValue = e.value;
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
/*      */   private Entry add(long k) {
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
/*      */   public byte remove(long k) {
/*  481 */     this.modified = false;
/*  482 */     if (this.tree == null) {
/*  483 */       return this.defRetValue;
/*      */     }
/*  485 */     Entry p = this.tree, q = null;
/*  486 */     boolean dir = false;
/*  487 */     long kk = k;
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
/*      */   public boolean containsValue(byte v) {
/*  722 */     ValueIterator i = new ValueIterator();
/*      */     
/*  724 */     int j = this.count;
/*  725 */     while (j-- != 0) {
/*  726 */       byte ev = i.nextByte();
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
/*      */     extends AbstractLong2ByteMap.BasicEntry
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
/*  770 */       super(0L, (byte)0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(long k, byte v) {
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
/*      */     public byte setValue(byte value) {
/*  932 */       byte oldValue = this.value;
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
/*  955 */       Map.Entry<Long, Byte> e = (Map.Entry<Long, Byte>)o;
/*  956 */       return (this.key == ((Long)e.getKey()).longValue() && this.value == ((Byte)e.getValue()).byteValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  960 */       return HashCommon.long2int(this.key) ^ this.value;
/*      */     }
/*      */     
/*      */     public String toString() {
/*  964 */       return this.key + "=>" + this.value;
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
/*      */   public boolean containsKey(long k) {
/*  985 */     return (findKey(k) != null);
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
/*      */   public byte get(long k) {
/*  998 */     Entry e = findKey(k);
/*  999 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public long firstLongKey() {
/* 1003 */     if (this.tree == null)
/* 1004 */       throw new NoSuchElementException(); 
/* 1005 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public long lastLongKey() {
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
/*      */     Long2ByteAVLTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Long2ByteAVLTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Long2ByteAVLTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1042 */     int index = 0;
/*      */     TreeIterator() {
/* 1044 */       this.next = Long2ByteAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(long k) {
/* 1047 */       if ((this.next = Long2ByteAVLTreeMap.this.locateKey(k)) != null)
/* 1048 */         if (Long2ByteAVLTreeMap.this.compare(this.next.key, k) <= 0) {
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
/*      */     Long2ByteAVLTreeMap.Entry nextEntry() {
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
/*      */     Long2ByteAVLTreeMap.Entry previousEntry() {
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
/* 1101 */       Long2ByteAVLTreeMap.this.remove(this.curr.key);
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
/*      */     implements ObjectListIterator<Long2ByteMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(long k) {
/* 1127 */       super(k);
/*      */     }
/*      */     
/*      */     public Long2ByteMap.Entry next() {
/* 1131 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Long2ByteMap.Entry previous() {
/* 1135 */       return previousEntry();
/*      */     }
/*      */     
/*      */     public void set(Long2ByteMap.Entry ok) {
/* 1139 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Long2ByteMap.Entry ok) {
/* 1143 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Long2ByteMap.Entry> long2ByteEntrySet() {
/* 1148 */     if (this.entries == null)
/* 1149 */       this.entries = (ObjectSortedSet<Long2ByteMap.Entry>)new AbstractObjectSortedSet<Long2ByteMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Long2ByteMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Long2ByteMap.Entry> comparator() {
/* 1154 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Long2ByteMap.Entry> iterator() {
/* 1158 */             return (ObjectBidirectionalIterator<Long2ByteMap.Entry>)new Long2ByteAVLTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Long2ByteMap.Entry> iterator(Long2ByteMap.Entry from) {
/* 1162 */             return (ObjectBidirectionalIterator<Long2ByteMap.Entry>)new Long2ByteAVLTreeMap.EntryIterator(from.getLongKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1167 */             if (!(o instanceof Map.Entry))
/* 1168 */               return false; 
/* 1169 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1170 */             if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 1171 */               return false; 
/* 1172 */             if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1173 */               return false; 
/* 1174 */             Long2ByteAVLTreeMap.Entry f = Long2ByteAVLTreeMap.this.findKey(((Long)e.getKey()).longValue());
/* 1175 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1180 */             if (!(o instanceof Map.Entry))
/* 1181 */               return false; 
/* 1182 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1183 */             if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 1184 */               return false; 
/* 1185 */             if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1186 */               return false; 
/* 1187 */             Long2ByteAVLTreeMap.Entry f = Long2ByteAVLTreeMap.this.findKey(((Long)e.getKey()).longValue());
/* 1188 */             if (f == null || f.getByteValue() != ((Byte)e.getValue()).byteValue())
/* 1189 */               return false; 
/* 1190 */             Long2ByteAVLTreeMap.this.remove(f.key);
/* 1191 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1195 */             return Long2ByteAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1199 */             Long2ByteAVLTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Long2ByteMap.Entry first() {
/* 1203 */             return Long2ByteAVLTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Long2ByteMap.Entry last() {
/* 1207 */             return Long2ByteAVLTreeMap.this.lastEntry;
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Long2ByteMap.Entry> subSet(Long2ByteMap.Entry from, Long2ByteMap.Entry to) {
/* 1211 */             return Long2ByteAVLTreeMap.this.subMap(from.getLongKey(), to.getLongKey()).long2ByteEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Long2ByteMap.Entry> headSet(Long2ByteMap.Entry to) {
/* 1215 */             return Long2ByteAVLTreeMap.this.headMap(to.getLongKey()).long2ByteEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Long2ByteMap.Entry> tailSet(Long2ByteMap.Entry from) {
/* 1219 */             return Long2ByteAVLTreeMap.this.tailMap(from.getLongKey()).long2ByteEntrySet();
/*      */           }
/*      */         }; 
/* 1222 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends TreeIterator
/*      */     implements LongListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(long k) {
/* 1238 */       super(k);
/*      */     }
/*      */     
/*      */     public long nextLong() {
/* 1242 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public long previousLong() {
/* 1246 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractLong2ByteSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public LongBidirectionalIterator iterator() {
/* 1253 */       return new Long2ByteAVLTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public LongBidirectionalIterator iterator(long from) {
/* 1257 */       return new Long2ByteAVLTreeMap.KeyIterator(from);
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
/*      */   public LongSortedSet keySet() {
/* 1272 */     if (this.keys == null)
/* 1273 */       this.keys = new KeySet(); 
/* 1274 */     return this.keys;
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
/* 1289 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public byte previousByte() {
/* 1293 */       return (previousEntry()).value;
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
/* 1308 */     if (this.values == null)
/* 1309 */       this.values = (ByteCollection)new AbstractByteCollection()
/*      */         {
/*      */           public ByteIterator iterator() {
/* 1312 */             return (ByteIterator)new Long2ByteAVLTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(byte k) {
/* 1316 */             return Long2ByteAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1320 */             return Long2ByteAVLTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1324 */             Long2ByteAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1327 */     return this.values;
/*      */   }
/*      */   
/*      */   public LongComparator comparator() {
/* 1331 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Long2ByteSortedMap headMap(long to) {
/* 1335 */     return new Submap(0L, true, to, false);
/*      */   }
/*      */   
/*      */   public Long2ByteSortedMap tailMap(long from) {
/* 1339 */     return new Submap(from, false, 0L, true);
/*      */   }
/*      */   
/*      */   public Long2ByteSortedMap subMap(long from, long to) {
/* 1343 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractLong2ByteSortedMap
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     long from;
/*      */ 
/*      */ 
/*      */     
/*      */     long to;
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
/*      */     protected transient ObjectSortedSet<Long2ByteMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient LongSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ByteCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(long from, boolean bottom, long to, boolean top) {
/* 1387 */       if (!bottom && !top && Long2ByteAVLTreeMap.this.compare(from, to) > 0)
/* 1388 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1389 */       this.from = from;
/* 1390 */       this.bottom = bottom;
/* 1391 */       this.to = to;
/* 1392 */       this.top = top;
/* 1393 */       this.defRetValue = Long2ByteAVLTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1397 */       SubmapIterator i = new SubmapIterator();
/* 1398 */       while (i.hasNext()) {
/* 1399 */         i.nextEntry();
/* 1400 */         i.remove();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean in(long k) {
/* 1411 */       return ((this.bottom || Long2ByteAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Long2ByteAVLTreeMap.this
/* 1412 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Long2ByteMap.Entry> long2ByteEntrySet() {
/* 1416 */       if (this.entries == null)
/* 1417 */         this.entries = (ObjectSortedSet<Long2ByteMap.Entry>)new AbstractObjectSortedSet<Long2ByteMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Long2ByteMap.Entry> iterator() {
/* 1420 */               return (ObjectBidirectionalIterator<Long2ByteMap.Entry>)new Long2ByteAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */             
/*      */             public ObjectBidirectionalIterator<Long2ByteMap.Entry> iterator(Long2ByteMap.Entry from) {
/* 1424 */               return (ObjectBidirectionalIterator<Long2ByteMap.Entry>)new Long2ByteAVLTreeMap.Submap.SubmapEntryIterator(from.getLongKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Long2ByteMap.Entry> comparator() {
/* 1428 */               return Long2ByteAVLTreeMap.this.long2ByteEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1433 */               if (!(o instanceof Map.Entry))
/* 1434 */                 return false; 
/* 1435 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1436 */               if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 1437 */                 return false; 
/* 1438 */               if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1439 */                 return false; 
/* 1440 */               Long2ByteAVLTreeMap.Entry f = Long2ByteAVLTreeMap.this.findKey(((Long)e.getKey()).longValue());
/* 1441 */               return (f != null && Long2ByteAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1446 */               if (!(o instanceof Map.Entry))
/* 1447 */                 return false; 
/* 1448 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1449 */               if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 1450 */                 return false; 
/* 1451 */               if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1452 */                 return false; 
/* 1453 */               Long2ByteAVLTreeMap.Entry f = Long2ByteAVLTreeMap.this.findKey(((Long)e.getKey()).longValue());
/* 1454 */               if (f != null && Long2ByteAVLTreeMap.Submap.this.in(f.key))
/* 1455 */                 Long2ByteAVLTreeMap.Submap.this.remove(f.key); 
/* 1456 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1460 */               int c = 0;
/* 1461 */               for (ObjectBidirectionalIterator<Long2ByteMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1462 */                 c++; 
/* 1463 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1467 */               return !(new Long2ByteAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1471 */               Long2ByteAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Long2ByteMap.Entry first() {
/* 1475 */               return Long2ByteAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Long2ByteMap.Entry last() {
/* 1479 */               return Long2ByteAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Long2ByteMap.Entry> subSet(Long2ByteMap.Entry from, Long2ByteMap.Entry to) {
/* 1483 */               return Long2ByteAVLTreeMap.Submap.this.subMap(from.getLongKey(), to.getLongKey()).long2ByteEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Long2ByteMap.Entry> headSet(Long2ByteMap.Entry to) {
/* 1487 */               return Long2ByteAVLTreeMap.Submap.this.headMap(to.getLongKey()).long2ByteEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Long2ByteMap.Entry> tailSet(Long2ByteMap.Entry from) {
/* 1491 */               return Long2ByteAVLTreeMap.Submap.this.tailMap(from.getLongKey()).long2ByteEntrySet();
/*      */             }
/*      */           }; 
/* 1494 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractLong2ByteSortedMap.KeySet {
/*      */       public LongBidirectionalIterator iterator() {
/* 1499 */         return new Long2ByteAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public LongBidirectionalIterator iterator(long from) {
/* 1503 */         return new Long2ByteAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public LongSortedSet keySet() {
/* 1508 */       if (this.keys == null)
/* 1509 */         this.keys = new KeySet(); 
/* 1510 */       return this.keys;
/*      */     }
/*      */     
/*      */     public ByteCollection values() {
/* 1514 */       if (this.values == null)
/* 1515 */         this.values = (ByteCollection)new AbstractByteCollection()
/*      */           {
/*      */             public ByteIterator iterator() {
/* 1518 */               return (ByteIterator)new Long2ByteAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(byte k) {
/* 1522 */               return Long2ByteAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1526 */               return Long2ByteAVLTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1530 */               Long2ByteAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1533 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(long k) {
/* 1538 */       return (in(k) && Long2ByteAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(byte v) {
/* 1542 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1544 */       while (i.hasNext()) {
/* 1545 */         byte ev = (i.nextEntry()).value;
/* 1546 */         if (ev == v)
/* 1547 */           return true; 
/*      */       } 
/* 1549 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public byte get(long k) {
/* 1555 */       long kk = k; Long2ByteAVLTreeMap.Entry e;
/* 1556 */       return (in(kk) && (e = Long2ByteAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public byte put(long k, byte v) {
/* 1560 */       Long2ByteAVLTreeMap.this.modified = false;
/* 1561 */       if (!in(k))
/* 1562 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1563 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1564 */       byte oldValue = Long2ByteAVLTreeMap.this.put(k, v);
/* 1565 */       return Long2ByteAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte remove(long k) {
/* 1570 */       Long2ByteAVLTreeMap.this.modified = false;
/* 1571 */       if (!in(k))
/* 1572 */         return this.defRetValue; 
/* 1573 */       byte oldValue = Long2ByteAVLTreeMap.this.remove(k);
/* 1574 */       return Long2ByteAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1578 */       SubmapIterator i = new SubmapIterator();
/* 1579 */       int n = 0;
/* 1580 */       while (i.hasNext()) {
/* 1581 */         n++;
/* 1582 */         i.nextEntry();
/*      */       } 
/* 1584 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1588 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public LongComparator comparator() {
/* 1592 */       return Long2ByteAVLTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Long2ByteSortedMap headMap(long to) {
/* 1596 */       if (this.top)
/* 1597 */         return new Submap(this.from, this.bottom, to, false); 
/* 1598 */       return (Long2ByteAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Long2ByteSortedMap tailMap(long from) {
/* 1602 */       if (this.bottom)
/* 1603 */         return new Submap(from, false, this.to, this.top); 
/* 1604 */       return (Long2ByteAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Long2ByteSortedMap subMap(long from, long to) {
/* 1608 */       if (this.top && this.bottom)
/* 1609 */         return new Submap(from, false, to, false); 
/* 1610 */       if (!this.top)
/* 1611 */         to = (Long2ByteAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1612 */       if (!this.bottom)
/* 1613 */         from = (Long2ByteAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1614 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1615 */         return this; 
/* 1616 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Long2ByteAVLTreeMap.Entry firstEntry() {
/*      */       Long2ByteAVLTreeMap.Entry e;
/* 1625 */       if (Long2ByteAVLTreeMap.this.tree == null) {
/* 1626 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1630 */       if (this.bottom) {
/* 1631 */         e = Long2ByteAVLTreeMap.this.firstEntry;
/*      */       } else {
/* 1633 */         e = Long2ByteAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1635 */         if (Long2ByteAVLTreeMap.this.compare(e.key, this.from) < 0) {
/* 1636 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1640 */       if (e == null || (!this.top && Long2ByteAVLTreeMap.this.compare(e.key, this.to) >= 0))
/* 1641 */         return null; 
/* 1642 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Long2ByteAVLTreeMap.Entry lastEntry() {
/*      */       Long2ByteAVLTreeMap.Entry e;
/* 1651 */       if (Long2ByteAVLTreeMap.this.tree == null) {
/* 1652 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1656 */       if (this.top) {
/* 1657 */         e = Long2ByteAVLTreeMap.this.lastEntry;
/*      */       } else {
/* 1659 */         e = Long2ByteAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1661 */         if (Long2ByteAVLTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1662 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1666 */       if (e == null || (!this.bottom && Long2ByteAVLTreeMap.this.compare(e.key, this.from) < 0))
/* 1667 */         return null; 
/* 1668 */       return e;
/*      */     }
/*      */     
/*      */     public long firstLongKey() {
/* 1672 */       Long2ByteAVLTreeMap.Entry e = firstEntry();
/* 1673 */       if (e == null)
/* 1674 */         throw new NoSuchElementException(); 
/* 1675 */       return e.key;
/*      */     }
/*      */     
/*      */     public long lastLongKey() {
/* 1679 */       Long2ByteAVLTreeMap.Entry e = lastEntry();
/* 1680 */       if (e == null)
/* 1681 */         throw new NoSuchElementException(); 
/* 1682 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Long2ByteAVLTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1695 */         this.next = Long2ByteAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(long k) {
/* 1698 */         this();
/* 1699 */         if (this.next != null)
/* 1700 */           if (!Long2ByteAVLTreeMap.Submap.this.bottom && Long2ByteAVLTreeMap.this.compare(k, this.next.key) < 0) {
/* 1701 */             this.prev = null;
/* 1702 */           } else if (!Long2ByteAVLTreeMap.Submap.this.top && Long2ByteAVLTreeMap.this.compare(k, (this.prev = Long2ByteAVLTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1703 */             this.next = null;
/*      */           } else {
/* 1705 */             this.next = Long2ByteAVLTreeMap.this.locateKey(k);
/* 1706 */             if (Long2ByteAVLTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1707 */               this.prev = this.next;
/* 1708 */               this.next = this.next.next();
/*      */             } else {
/* 1710 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1716 */         this.prev = this.prev.prev();
/* 1717 */         if (!Long2ByteAVLTreeMap.Submap.this.bottom && this.prev != null && Long2ByteAVLTreeMap.this.compare(this.prev.key, Long2ByteAVLTreeMap.Submap.this.from) < 0)
/* 1718 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1722 */         this.next = this.next.next();
/* 1723 */         if (!Long2ByteAVLTreeMap.Submap.this.top && this.next != null && Long2ByteAVLTreeMap.this.compare(this.next.key, Long2ByteAVLTreeMap.Submap.this.to) >= 0)
/* 1724 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Long2ByteMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(long k) {
/* 1731 */         super(k);
/*      */       }
/*      */       
/*      */       public Long2ByteMap.Entry next() {
/* 1735 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Long2ByteMap.Entry previous() {
/* 1739 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements LongListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(long from) {
/* 1757 */         super(from);
/*      */       }
/*      */       
/*      */       public long nextLong() {
/* 1761 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public long previousLong() {
/* 1765 */         return (previousEntry()).key;
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
/* 1781 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public byte previousByte() {
/* 1785 */         return (previousEntry()).value;
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
/*      */   public Long2ByteAVLTreeMap clone() {
/*      */     Long2ByteAVLTreeMap c;
/*      */     try {
/* 1804 */       c = (Long2ByteAVLTreeMap)super.clone();
/* 1805 */     } catch (CloneNotSupportedException cantHappen) {
/* 1806 */       throw new InternalError();
/*      */     } 
/* 1808 */     c.keys = null;
/* 1809 */     c.values = null;
/* 1810 */     c.entries = null;
/* 1811 */     c.allocatePaths();
/* 1812 */     if (this.count != 0) {
/*      */       
/* 1814 */       Entry rp = new Entry(), rq = new Entry();
/* 1815 */       Entry p = rp;
/* 1816 */       rp.left(this.tree);
/* 1817 */       Entry q = rq;
/* 1818 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1820 */         if (!p.pred()) {
/* 1821 */           Entry e = p.left.clone();
/* 1822 */           e.pred(q.left);
/* 1823 */           e.succ(q);
/* 1824 */           q.left(e);
/* 1825 */           p = p.left;
/* 1826 */           q = q.left;
/*      */         } else {
/* 1828 */           while (p.succ()) {
/* 1829 */             p = p.right;
/* 1830 */             if (p == null) {
/* 1831 */               q.right = null;
/* 1832 */               c.tree = rq.left;
/* 1833 */               c.firstEntry = c.tree;
/* 1834 */               while (c.firstEntry.left != null)
/* 1835 */                 c.firstEntry = c.firstEntry.left; 
/* 1836 */               c.lastEntry = c.tree;
/* 1837 */               while (c.lastEntry.right != null)
/* 1838 */                 c.lastEntry = c.lastEntry.right; 
/* 1839 */               return c;
/*      */             } 
/* 1841 */             q = q.right;
/*      */           } 
/* 1843 */           p = p.right;
/* 1844 */           q = q.right;
/*      */         } 
/* 1846 */         if (!p.succ()) {
/* 1847 */           Entry e = p.right.clone();
/* 1848 */           e.succ(q.right);
/* 1849 */           e.pred(q);
/* 1850 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1854 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1857 */     int n = this.count;
/* 1858 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1860 */     s.defaultWriteObject();
/* 1861 */     while (n-- != 0) {
/* 1862 */       Entry e = i.nextEntry();
/* 1863 */       s.writeLong(e.key);
/* 1864 */       s.writeByte(e.value);
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
/* 1885 */     if (n == 1) {
/* 1886 */       Entry entry = new Entry(s.readLong(), s.readByte());
/* 1887 */       entry.pred(pred);
/* 1888 */       entry.succ(succ);
/* 1889 */       return entry;
/*      */     } 
/* 1891 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1896 */       Entry entry = new Entry(s.readLong(), s.readByte());
/* 1897 */       entry.right(new Entry(s.readLong(), s.readByte()));
/* 1898 */       entry.right.pred(entry);
/* 1899 */       entry.balance(1);
/* 1900 */       entry.pred(pred);
/* 1901 */       entry.right.succ(succ);
/* 1902 */       return entry;
/*      */     } 
/*      */     
/* 1905 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1906 */     Entry top = new Entry();
/* 1907 */     top.left(readTree(s, leftN, pred, top));
/* 1908 */     top.key = s.readLong();
/* 1909 */     top.value = s.readByte();
/* 1910 */     top.right(readTree(s, rightN, top, succ));
/* 1911 */     if (n == (n & -n))
/* 1912 */       top.balance(1); 
/* 1913 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1916 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1921 */     setActualComparator();
/* 1922 */     allocatePaths();
/* 1923 */     if (this.count != 0) {
/* 1924 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1926 */       Entry e = this.tree;
/* 1927 */       while (e.left() != null)
/* 1928 */         e = e.left(); 
/* 1929 */       this.firstEntry = e;
/* 1930 */       e = this.tree;
/* 1931 */       while (e.right() != null)
/* 1932 */         e = e.right(); 
/* 1933 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2ByteAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */