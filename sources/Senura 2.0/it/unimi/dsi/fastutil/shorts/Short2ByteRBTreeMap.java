/*      */ package it.unimi.dsi.fastutil.shorts;
/*      */ 
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
/*      */ 
/*      */ 
/*      */ public class Short2ByteRBTreeMap
/*      */   extends AbstractShort2ByteSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Short2ByteMap.Entry> entries;
/*      */   protected transient ShortSortedSet keys;
/*      */   protected transient ByteCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Short> storedComparator;
/*      */   protected transient ShortComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public Short2ByteRBTreeMap() {
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
/*   91 */     this.actualComparator = ShortComparators.asShortComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ByteRBTreeMap(Comparator<? super Short> c) {
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
/*      */   public Short2ByteRBTreeMap(Map<? extends Short, ? extends Byte> m) {
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
/*      */   public Short2ByteRBTreeMap(SortedMap<Short, Byte> m) {
/*  122 */     this(m.comparator());
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ByteRBTreeMap(Short2ByteMap m) {
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
/*      */   public Short2ByteRBTreeMap(Short2ByteSortedMap m) {
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
/*      */   public Short2ByteRBTreeMap(short[] k, byte[] v, Comparator<? super Short> c) {
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
/*      */   public Short2ByteRBTreeMap(short[] k, byte[] v) {
/*  178 */     this(k, v, (Comparator<? super Short>)null);
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
/*      */   final int compare(short k1, short k2) {
/*  206 */     return (this.actualComparator == null) ? Short.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry findKey(short k) {
/*  218 */     Entry e = this.tree;
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
/*      */   final Entry locateKey(short k) {
/*  234 */     Entry e = this.tree, last = this.tree;
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
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  250 */     this.dirPath = new boolean[64];
/*  251 */     this.nodePath = new Entry[64];
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
/*      */   public byte addTo(short k, byte incr) {
/*  270 */     Entry e = add(k);
/*  271 */     byte oldValue = e.value;
/*  272 */     e.value = (byte)(e.value + incr);
/*  273 */     return oldValue;
/*      */   }
/*      */   
/*      */   public byte put(short k, byte v) {
/*  277 */     Entry e = add(k);
/*  278 */     byte oldValue = e.value;
/*  279 */     e.value = v;
/*  280 */     return oldValue;
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
/*      */   private Entry add(short k) {
/*      */     Entry e;
/*  297 */     this.modified = false;
/*  298 */     int maxDepth = 0;
/*      */     
/*  300 */     if (this.tree == null) {
/*  301 */       this.count++;
/*  302 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry(k, this.defRetValue);
/*      */     } else {
/*  304 */       Entry p = this.tree;
/*  305 */       int i = 0; while (true) {
/*      */         int cmp;
/*  307 */         if ((cmp = compare(k, p.key)) == 0) {
/*      */           
/*  309 */           while (i-- != 0)
/*  310 */             this.nodePath[i] = null; 
/*  311 */           return p;
/*      */         } 
/*  313 */         this.nodePath[i] = p;
/*  314 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  315 */           if (p.succ()) {
/*  316 */             this.count++;
/*  317 */             e = new Entry(k, this.defRetValue);
/*  318 */             if (p.right == null)
/*  319 */               this.lastEntry = e; 
/*  320 */             e.left = p;
/*  321 */             e.right = p.right;
/*  322 */             p.right(e);
/*      */             break;
/*      */           } 
/*  325 */           p = p.right; continue;
/*      */         } 
/*  327 */         if (p.pred()) {
/*  328 */           this.count++;
/*  329 */           e = new Entry(k, this.defRetValue);
/*  330 */           if (p.left == null)
/*  331 */             this.firstEntry = e; 
/*  332 */           e.right = p;
/*  333 */           e.left = p.left;
/*  334 */           p.left(e);
/*      */           break;
/*      */         } 
/*  337 */         p = p.left;
/*      */       } 
/*      */       
/*  340 */       this.modified = true;
/*  341 */       maxDepth = i--;
/*  342 */       while (i > 0 && !this.nodePath[i].black()) {
/*  343 */         if (!this.dirPath[i - 1]) {
/*  344 */           Entry entry1 = (this.nodePath[i - 1]).right;
/*  345 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  346 */             this.nodePath[i].black(true);
/*  347 */             entry1.black(true);
/*  348 */             this.nodePath[i - 1].black(false);
/*  349 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  352 */           if (!this.dirPath[i]) {
/*  353 */             entry1 = this.nodePath[i];
/*      */           } else {
/*  355 */             Entry entry = this.nodePath[i];
/*  356 */             entry1 = entry.right;
/*  357 */             entry.right = entry1.left;
/*  358 */             entry1.left = entry;
/*  359 */             (this.nodePath[i - 1]).left = entry1;
/*  360 */             if (entry1.pred()) {
/*  361 */               entry1.pred(false);
/*  362 */               entry.succ(entry1);
/*      */             } 
/*      */           } 
/*  365 */           Entry entry2 = this.nodePath[i - 1];
/*  366 */           entry2.black(false);
/*  367 */           entry1.black(true);
/*  368 */           entry2.left = entry1.right;
/*  369 */           entry1.right = entry2;
/*  370 */           if (i < 2) {
/*  371 */             this.tree = entry1;
/*      */           }
/*  373 */           else if (this.dirPath[i - 2]) {
/*  374 */             (this.nodePath[i - 2]).right = entry1;
/*      */           } else {
/*  376 */             (this.nodePath[i - 2]).left = entry1;
/*      */           } 
/*  378 */           if (entry1.succ()) {
/*  379 */             entry1.succ(false);
/*  380 */             entry2.pred(entry1);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  385 */         Entry y = (this.nodePath[i - 1]).left;
/*  386 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  387 */           this.nodePath[i].black(true);
/*  388 */           y.black(true);
/*  389 */           this.nodePath[i - 1].black(false);
/*  390 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  393 */         if (this.dirPath[i]) {
/*  394 */           y = this.nodePath[i];
/*      */         } else {
/*  396 */           Entry entry = this.nodePath[i];
/*  397 */           y = entry.left;
/*  398 */           entry.left = y.right;
/*  399 */           y.right = entry;
/*  400 */           (this.nodePath[i - 1]).right = y;
/*  401 */           if (y.succ()) {
/*  402 */             y.succ(false);
/*  403 */             entry.pred(y);
/*      */           } 
/*      */         } 
/*  406 */         Entry x = this.nodePath[i - 1];
/*  407 */         x.black(false);
/*  408 */         y.black(true);
/*  409 */         x.right = y.left;
/*  410 */         y.left = x;
/*  411 */         if (i < 2) {
/*  412 */           this.tree = y;
/*      */         }
/*  414 */         else if (this.dirPath[i - 2]) {
/*  415 */           (this.nodePath[i - 2]).right = y;
/*      */         } else {
/*  417 */           (this.nodePath[i - 2]).left = y;
/*      */         } 
/*  419 */         if (y.pred()) {
/*  420 */           y.pred(false);
/*  421 */           x.succ(y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  428 */     this.tree.black(true);
/*      */     
/*  430 */     while (maxDepth-- != 0)
/*  431 */       this.nodePath[maxDepth] = null; 
/*  432 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte remove(short k) {
/*  441 */     this.modified = false;
/*  442 */     if (this.tree == null)
/*  443 */       return this.defRetValue; 
/*  444 */     Entry p = this.tree;
/*      */     
/*  446 */     int i = 0;
/*  447 */     short kk = k;
/*      */     int cmp;
/*  449 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  451 */       this.dirPath[i] = (cmp > 0);
/*  452 */       this.nodePath[i] = p;
/*  453 */       if (this.dirPath[i++]) {
/*  454 */         if ((p = p.right()) == null) {
/*      */           
/*  456 */           while (i-- != 0)
/*  457 */             this.nodePath[i] = null; 
/*  458 */           return this.defRetValue;
/*      */         }  continue;
/*      */       } 
/*  461 */       if ((p = p.left()) == null) {
/*      */         
/*  463 */         while (i-- != 0)
/*  464 */           this.nodePath[i] = null; 
/*  465 */         return this.defRetValue;
/*      */       } 
/*      */     } 
/*      */     
/*  469 */     if (p.left == null)
/*  470 */       this.firstEntry = p.next(); 
/*  471 */     if (p.right == null)
/*  472 */       this.lastEntry = p.prev(); 
/*  473 */     if (p.succ()) {
/*  474 */       if (p.pred()) {
/*  475 */         if (i == 0) {
/*  476 */           this.tree = p.left;
/*      */         }
/*  478 */         else if (this.dirPath[i - 1]) {
/*  479 */           this.nodePath[i - 1].succ(p.right);
/*      */         } else {
/*  481 */           this.nodePath[i - 1].pred(p.left);
/*      */         } 
/*      */       } else {
/*  484 */         (p.prev()).right = p.right;
/*  485 */         if (i == 0) {
/*  486 */           this.tree = p.left;
/*      */         }
/*  488 */         else if (this.dirPath[i - 1]) {
/*  489 */           (this.nodePath[i - 1]).right = p.left;
/*      */         } else {
/*  491 */           (this.nodePath[i - 1]).left = p.left;
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  496 */       Entry r = p.right;
/*  497 */       if (r.pred()) {
/*  498 */         r.left = p.left;
/*  499 */         r.pred(p.pred());
/*  500 */         if (!r.pred())
/*  501 */           (r.prev()).right = r; 
/*  502 */         if (i == 0) {
/*  503 */           this.tree = r;
/*      */         }
/*  505 */         else if (this.dirPath[i - 1]) {
/*  506 */           (this.nodePath[i - 1]).right = r;
/*      */         } else {
/*  508 */           (this.nodePath[i - 1]).left = r;
/*      */         } 
/*  510 */         boolean color = r.black();
/*  511 */         r.black(p.black());
/*  512 */         p.black(color);
/*  513 */         this.dirPath[i] = true;
/*  514 */         this.nodePath[i++] = r;
/*      */       } else {
/*      */         Entry s;
/*  517 */         int j = i++;
/*      */         while (true) {
/*  519 */           this.dirPath[i] = false;
/*  520 */           this.nodePath[i++] = r;
/*  521 */           s = r.left;
/*  522 */           if (s.pred())
/*      */             break; 
/*  524 */           r = s;
/*      */         } 
/*  526 */         this.dirPath[j] = true;
/*  527 */         this.nodePath[j] = s;
/*  528 */         if (s.succ()) {
/*  529 */           r.pred(s);
/*      */         } else {
/*  531 */           r.left = s.right;
/*  532 */         }  s.left = p.left;
/*  533 */         if (!p.pred()) {
/*  534 */           (p.prev()).right = s;
/*  535 */           s.pred(false);
/*      */         } 
/*  537 */         s.right(p.right);
/*  538 */         boolean color = s.black();
/*  539 */         s.black(p.black());
/*  540 */         p.black(color);
/*  541 */         if (j == 0) {
/*  542 */           this.tree = s;
/*      */         }
/*  544 */         else if (this.dirPath[j - 1]) {
/*  545 */           (this.nodePath[j - 1]).right = s;
/*      */         } else {
/*  547 */           (this.nodePath[j - 1]).left = s;
/*      */         } 
/*      */       } 
/*      */     } 
/*  551 */     int maxDepth = i;
/*  552 */     if (p.black()) {
/*  553 */       for (; i > 0; i--) {
/*  554 */         if ((this.dirPath[i - 1] && !this.nodePath[i - 1].succ()) || (!this.dirPath[i - 1] && !this.nodePath[i - 1].pred())) {
/*  555 */           Entry x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  556 */           if (!x.black()) {
/*  557 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  561 */         if (!this.dirPath[i - 1]) {
/*  562 */           Entry w = (this.nodePath[i - 1]).right;
/*  563 */           if (!w.black()) {
/*  564 */             w.black(true);
/*  565 */             this.nodePath[i - 1].black(false);
/*  566 */             (this.nodePath[i - 1]).right = w.left;
/*  567 */             w.left = this.nodePath[i - 1];
/*  568 */             if (i < 2) {
/*  569 */               this.tree = w;
/*      */             }
/*  571 */             else if (this.dirPath[i - 2]) {
/*  572 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  574 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  576 */             this.nodePath[i] = this.nodePath[i - 1];
/*  577 */             this.dirPath[i] = false;
/*  578 */             this.nodePath[i - 1] = w;
/*  579 */             if (maxDepth == i++)
/*  580 */               maxDepth++; 
/*  581 */             w = (this.nodePath[i - 1]).right;
/*      */           } 
/*  583 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  584 */             w.black(false);
/*      */           } else {
/*  586 */             if (w.succ() || w.right.black()) {
/*  587 */               Entry y = w.left;
/*  588 */               y.black(true);
/*  589 */               w.black(false);
/*  590 */               w.left = y.right;
/*  591 */               y.right = w;
/*  592 */               w = (this.nodePath[i - 1]).right = y;
/*  593 */               if (w.succ()) {
/*  594 */                 w.succ(false);
/*  595 */                 w.right.pred(w);
/*      */               } 
/*      */             } 
/*  598 */             w.black(this.nodePath[i - 1].black());
/*  599 */             this.nodePath[i - 1].black(true);
/*  600 */             w.right.black(true);
/*  601 */             (this.nodePath[i - 1]).right = w.left;
/*  602 */             w.left = this.nodePath[i - 1];
/*  603 */             if (i < 2) {
/*  604 */               this.tree = w;
/*      */             }
/*  606 */             else if (this.dirPath[i - 2]) {
/*  607 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  609 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  611 */             if (w.pred()) {
/*  612 */               w.pred(false);
/*  613 */               this.nodePath[i - 1].succ(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } else {
/*  618 */           Entry w = (this.nodePath[i - 1]).left;
/*  619 */           if (!w.black()) {
/*  620 */             w.black(true);
/*  621 */             this.nodePath[i - 1].black(false);
/*  622 */             (this.nodePath[i - 1]).left = w.right;
/*  623 */             w.right = this.nodePath[i - 1];
/*  624 */             if (i < 2) {
/*  625 */               this.tree = w;
/*      */             }
/*  627 */             else if (this.dirPath[i - 2]) {
/*  628 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  630 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  632 */             this.nodePath[i] = this.nodePath[i - 1];
/*  633 */             this.dirPath[i] = true;
/*  634 */             this.nodePath[i - 1] = w;
/*  635 */             if (maxDepth == i++)
/*  636 */               maxDepth++; 
/*  637 */             w = (this.nodePath[i - 1]).left;
/*      */           } 
/*  639 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  640 */             w.black(false);
/*      */           } else {
/*  642 */             if (w.pred() || w.left.black()) {
/*  643 */               Entry y = w.right;
/*  644 */               y.black(true);
/*  645 */               w.black(false);
/*  646 */               w.right = y.left;
/*  647 */               y.left = w;
/*  648 */               w = (this.nodePath[i - 1]).left = y;
/*  649 */               if (w.pred()) {
/*  650 */                 w.pred(false);
/*  651 */                 w.left.succ(w);
/*      */               } 
/*      */             } 
/*  654 */             w.black(this.nodePath[i - 1].black());
/*  655 */             this.nodePath[i - 1].black(true);
/*  656 */             w.left.black(true);
/*  657 */             (this.nodePath[i - 1]).left = w.right;
/*  658 */             w.right = this.nodePath[i - 1];
/*  659 */             if (i < 2) {
/*  660 */               this.tree = w;
/*      */             }
/*  662 */             else if (this.dirPath[i - 2]) {
/*  663 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  665 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  667 */             if (w.succ()) {
/*  668 */               w.succ(false);
/*  669 */               this.nodePath[i - 1].pred(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  675 */       if (this.tree != null)
/*  676 */         this.tree.black(true); 
/*      */     } 
/*  678 */     this.modified = true;
/*  679 */     this.count--;
/*      */     
/*  681 */     while (maxDepth-- != 0)
/*  682 */       this.nodePath[maxDepth] = null; 
/*  683 */     return p.value;
/*      */   }
/*      */   
/*      */   public boolean containsValue(byte v) {
/*  687 */     ValueIterator i = new ValueIterator();
/*      */     
/*  689 */     int j = this.count;
/*  690 */     while (j-- != 0) {
/*  691 */       byte ev = i.nextByte();
/*  692 */       if (ev == v)
/*  693 */         return true; 
/*      */     } 
/*  695 */     return false;
/*      */   }
/*      */   
/*      */   public void clear() {
/*  699 */     this.count = 0;
/*  700 */     this.tree = null;
/*  701 */     this.entries = null;
/*  702 */     this.values = null;
/*  703 */     this.keys = null;
/*  704 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry
/*      */     extends AbstractShort2ByteMap.BasicEntry
/*      */     implements Cloneable
/*      */   {
/*      */     private static final int BLACK_MASK = 1;
/*      */ 
/*      */     
/*      */     private static final int SUCC_MASK = -2147483648;
/*      */ 
/*      */     
/*      */     private static final int PRED_MASK = 1073741824;
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
/*      */     Entry() {
/*  732 */       super((short)0, (byte)0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(short k, byte v) {
/*  743 */       super(k, v);
/*  744 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left() {
/*  752 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right() {
/*  760 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  768 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  776 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  785 */       if (pred) {
/*  786 */         this.info |= 0x40000000;
/*      */       } else {
/*  788 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  797 */       if (succ) {
/*  798 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  800 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry pred) {
/*  809 */       this.info |= 0x40000000;
/*  810 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry succ) {
/*  819 */       this.info |= Integer.MIN_VALUE;
/*  820 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry left) {
/*  829 */       this.info &= 0xBFFFFFFF;
/*  830 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry right) {
/*  839 */       this.info &= Integer.MAX_VALUE;
/*  840 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean black() {
/*  848 */       return ((this.info & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void black(boolean black) {
/*  857 */       if (black) {
/*  858 */         this.info |= 0x1;
/*      */       } else {
/*  860 */         this.info &= 0xFFFFFFFE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry next() {
/*  868 */       Entry next = this.right;
/*  869 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  870 */         while ((next.info & 0x40000000) == 0)
/*  871 */           next = next.left;  
/*  872 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry prev() {
/*  880 */       Entry prev = this.left;
/*  881 */       if ((this.info & 0x40000000) == 0)
/*  882 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  883 */           prev = prev.right;  
/*  884 */       return prev;
/*      */     }
/*      */     
/*      */     public byte setValue(byte value) {
/*  888 */       byte oldValue = this.value;
/*  889 */       this.value = value;
/*  890 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry clone() {
/*      */       Entry c;
/*      */       try {
/*  897 */         c = (Entry)super.clone();
/*  898 */       } catch (CloneNotSupportedException cantHappen) {
/*  899 */         throw new InternalError();
/*      */       } 
/*  901 */       c.key = this.key;
/*  902 */       c.value = this.value;
/*  903 */       c.info = this.info;
/*  904 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  909 */       if (!(o instanceof Map.Entry))
/*  910 */         return false; 
/*  911 */       Map.Entry<Short, Byte> e = (Map.Entry<Short, Byte>)o;
/*  912 */       return (this.key == ((Short)e.getKey()).shortValue() && this.value == ((Byte)e.getValue()).byteValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  916 */       return this.key ^ this.value;
/*      */     }
/*      */     
/*      */     public String toString() {
/*  920 */       return this.key + "=>" + this.value;
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
/*      */   public boolean containsKey(short k) {
/*  941 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  945 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  949 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public byte get(short k) {
/*  954 */     Entry e = findKey(k);
/*  955 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public short firstShortKey() {
/*  959 */     if (this.tree == null)
/*  960 */       throw new NoSuchElementException(); 
/*  961 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public short lastShortKey() {
/*  965 */     if (this.tree == null)
/*  966 */       throw new NoSuchElementException(); 
/*  967 */     return this.lastEntry.key;
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
/*      */     Short2ByteRBTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Short2ByteRBTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Short2ByteRBTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  998 */     int index = 0;
/*      */     TreeIterator() {
/* 1000 */       this.next = Short2ByteRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(short k) {
/* 1003 */       if ((this.next = Short2ByteRBTreeMap.this.locateKey(k)) != null)
/* 1004 */         if (Short2ByteRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1005 */           this.prev = this.next;
/* 1006 */           this.next = this.next.next();
/*      */         } else {
/* 1008 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/* 1012 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1015 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1018 */       this.next = this.next.next();
/*      */     }
/*      */     Short2ByteRBTreeMap.Entry nextEntry() {
/* 1021 */       if (!hasNext())
/* 1022 */         throw new NoSuchElementException(); 
/* 1023 */       this.curr = this.prev = this.next;
/* 1024 */       this.index++;
/* 1025 */       updateNext();
/* 1026 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1029 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Short2ByteRBTreeMap.Entry previousEntry() {
/* 1032 */       if (!hasPrevious())
/* 1033 */         throw new NoSuchElementException(); 
/* 1034 */       this.curr = this.next = this.prev;
/* 1035 */       this.index--;
/* 1036 */       updatePrevious();
/* 1037 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1040 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1043 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1046 */       if (this.curr == null) {
/* 1047 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1052 */       if (this.curr == this.prev)
/* 1053 */         this.index--; 
/* 1054 */       this.next = this.prev = this.curr;
/* 1055 */       updatePrevious();
/* 1056 */       updateNext();
/* 1057 */       Short2ByteRBTreeMap.this.remove(this.curr.key);
/* 1058 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1061 */       int i = n;
/* 1062 */       while (i-- != 0 && hasNext())
/* 1063 */         nextEntry(); 
/* 1064 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1067 */       int i = n;
/* 1068 */       while (i-- != 0 && hasPrevious())
/* 1069 */         previousEntry(); 
/* 1070 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Short2ByteMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(short k) {
/* 1083 */       super(k);
/*      */     }
/*      */     
/*      */     public Short2ByteMap.Entry next() {
/* 1087 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Short2ByteMap.Entry previous() {
/* 1091 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Short2ByteMap.Entry> short2ByteEntrySet() {
/* 1096 */     if (this.entries == null)
/* 1097 */       this.entries = (ObjectSortedSet<Short2ByteMap.Entry>)new AbstractObjectSortedSet<Short2ByteMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Short2ByteMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Short2ByteMap.Entry> comparator() {
/* 1102 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Short2ByteMap.Entry> iterator() {
/* 1106 */             return (ObjectBidirectionalIterator<Short2ByteMap.Entry>)new Short2ByteRBTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Short2ByteMap.Entry> iterator(Short2ByteMap.Entry from) {
/* 1110 */             return (ObjectBidirectionalIterator<Short2ByteMap.Entry>)new Short2ByteRBTreeMap.EntryIterator(from.getShortKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1115 */             if (!(o instanceof Map.Entry))
/* 1116 */               return false; 
/* 1117 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1118 */             if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1119 */               return false; 
/* 1120 */             if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1121 */               return false; 
/* 1122 */             Short2ByteRBTreeMap.Entry f = Short2ByteRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1123 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1128 */             if (!(o instanceof Map.Entry))
/* 1129 */               return false; 
/* 1130 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1131 */             if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1132 */               return false; 
/* 1133 */             if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1134 */               return false; 
/* 1135 */             Short2ByteRBTreeMap.Entry f = Short2ByteRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1136 */             if (f == null || f.getByteValue() != ((Byte)e.getValue()).byteValue())
/* 1137 */               return false; 
/* 1138 */             Short2ByteRBTreeMap.this.remove(f.key);
/* 1139 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1143 */             return Short2ByteRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1147 */             Short2ByteRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Short2ByteMap.Entry first() {
/* 1151 */             return Short2ByteRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Short2ByteMap.Entry last() {
/* 1155 */             return Short2ByteRBTreeMap.this.lastEntry;
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Short2ByteMap.Entry> subSet(Short2ByteMap.Entry from, Short2ByteMap.Entry to) {
/* 1159 */             return Short2ByteRBTreeMap.this.subMap(from.getShortKey(), to.getShortKey()).short2ByteEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Short2ByteMap.Entry> headSet(Short2ByteMap.Entry to) {
/* 1163 */             return Short2ByteRBTreeMap.this.headMap(to.getShortKey()).short2ByteEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Short2ByteMap.Entry> tailSet(Short2ByteMap.Entry from) {
/* 1167 */             return Short2ByteRBTreeMap.this.tailMap(from.getShortKey()).short2ByteEntrySet();
/*      */           }
/*      */         }; 
/* 1170 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends TreeIterator
/*      */     implements ShortListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(short k) {
/* 1186 */       super(k);
/*      */     }
/*      */     
/*      */     public short nextShort() {
/* 1190 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public short previousShort() {
/* 1194 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractShort2ByteSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ShortBidirectionalIterator iterator() {
/* 1201 */       return new Short2ByteRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public ShortBidirectionalIterator iterator(short from) {
/* 1205 */       return new Short2ByteRBTreeMap.KeyIterator(from);
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
/*      */   public ShortSortedSet keySet() {
/* 1220 */     if (this.keys == null)
/* 1221 */       this.keys = new KeySet(); 
/* 1222 */     return this.keys;
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
/* 1237 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public byte previousByte() {
/* 1241 */       return (previousEntry()).value;
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
/* 1256 */     if (this.values == null)
/* 1257 */       this.values = (ByteCollection)new AbstractByteCollection()
/*      */         {
/*      */           public ByteIterator iterator() {
/* 1260 */             return (ByteIterator)new Short2ByteRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(byte k) {
/* 1264 */             return Short2ByteRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1268 */             return Short2ByteRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1272 */             Short2ByteRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1275 */     return this.values;
/*      */   }
/*      */   
/*      */   public ShortComparator comparator() {
/* 1279 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Short2ByteSortedMap headMap(short to) {
/* 1283 */     return new Submap((short)0, true, to, false);
/*      */   }
/*      */   
/*      */   public Short2ByteSortedMap tailMap(short from) {
/* 1287 */     return new Submap(from, false, (short)0, true);
/*      */   }
/*      */   
/*      */   public Short2ByteSortedMap subMap(short from, short to) {
/* 1291 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractShort2ByteSortedMap
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     short from;
/*      */ 
/*      */ 
/*      */     
/*      */     short to;
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
/*      */     protected transient ObjectSortedSet<Short2ByteMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ShortSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient ByteCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(short from, boolean bottom, short to, boolean top) {
/* 1335 */       if (!bottom && !top && Short2ByteRBTreeMap.this.compare(from, to) > 0)
/* 1336 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1337 */       this.from = from;
/* 1338 */       this.bottom = bottom;
/* 1339 */       this.to = to;
/* 1340 */       this.top = top;
/* 1341 */       this.defRetValue = Short2ByteRBTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1345 */       SubmapIterator i = new SubmapIterator();
/* 1346 */       while (i.hasNext()) {
/* 1347 */         i.nextEntry();
/* 1348 */         i.remove();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean in(short k) {
/* 1359 */       return ((this.bottom || Short2ByteRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Short2ByteRBTreeMap.this
/* 1360 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Short2ByteMap.Entry> short2ByteEntrySet() {
/* 1364 */       if (this.entries == null)
/* 1365 */         this.entries = (ObjectSortedSet<Short2ByteMap.Entry>)new AbstractObjectSortedSet<Short2ByteMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Short2ByteMap.Entry> iterator() {
/* 1368 */               return (ObjectBidirectionalIterator<Short2ByteMap.Entry>)new Short2ByteRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */             
/*      */             public ObjectBidirectionalIterator<Short2ByteMap.Entry> iterator(Short2ByteMap.Entry from) {
/* 1372 */               return (ObjectBidirectionalIterator<Short2ByteMap.Entry>)new Short2ByteRBTreeMap.Submap.SubmapEntryIterator(from.getShortKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Short2ByteMap.Entry> comparator() {
/* 1376 */               return Short2ByteRBTreeMap.this.short2ByteEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1381 */               if (!(o instanceof Map.Entry))
/* 1382 */                 return false; 
/* 1383 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1384 */               if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1385 */                 return false; 
/* 1386 */               if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1387 */                 return false; 
/* 1388 */               Short2ByteRBTreeMap.Entry f = Short2ByteRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1389 */               return (f != null && Short2ByteRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1394 */               if (!(o instanceof Map.Entry))
/* 1395 */                 return false; 
/* 1396 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1397 */               if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1398 */                 return false; 
/* 1399 */               if (e.getValue() == null || !(e.getValue() instanceof Byte))
/* 1400 */                 return false; 
/* 1401 */               Short2ByteRBTreeMap.Entry f = Short2ByteRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1402 */               if (f != null && Short2ByteRBTreeMap.Submap.this.in(f.key))
/* 1403 */                 Short2ByteRBTreeMap.Submap.this.remove(f.key); 
/* 1404 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1408 */               int c = 0;
/* 1409 */               for (ObjectBidirectionalIterator<Short2ByteMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1410 */                 c++; 
/* 1411 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1415 */               return !(new Short2ByteRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1419 */               Short2ByteRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Short2ByteMap.Entry first() {
/* 1423 */               return Short2ByteRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Short2ByteMap.Entry last() {
/* 1427 */               return Short2ByteRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Short2ByteMap.Entry> subSet(Short2ByteMap.Entry from, Short2ByteMap.Entry to) {
/* 1432 */               return Short2ByteRBTreeMap.Submap.this.subMap(from.getShortKey(), to.getShortKey()).short2ByteEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Short2ByteMap.Entry> headSet(Short2ByteMap.Entry to) {
/* 1436 */               return Short2ByteRBTreeMap.Submap.this.headMap(to.getShortKey()).short2ByteEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Short2ByteMap.Entry> tailSet(Short2ByteMap.Entry from) {
/* 1440 */               return Short2ByteRBTreeMap.Submap.this.tailMap(from.getShortKey()).short2ByteEntrySet();
/*      */             }
/*      */           }; 
/* 1443 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractShort2ByteSortedMap.KeySet {
/*      */       public ShortBidirectionalIterator iterator() {
/* 1448 */         return new Short2ByteRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public ShortBidirectionalIterator iterator(short from) {
/* 1452 */         return new Short2ByteRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public ShortSortedSet keySet() {
/* 1457 */       if (this.keys == null)
/* 1458 */         this.keys = new KeySet(); 
/* 1459 */       return this.keys;
/*      */     }
/*      */     
/*      */     public ByteCollection values() {
/* 1463 */       if (this.values == null)
/* 1464 */         this.values = (ByteCollection)new AbstractByteCollection()
/*      */           {
/*      */             public ByteIterator iterator() {
/* 1467 */               return (ByteIterator)new Short2ByteRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(byte k) {
/* 1471 */               return Short2ByteRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1475 */               return Short2ByteRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1479 */               Short2ByteRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1482 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(short k) {
/* 1487 */       return (in(k) && Short2ByteRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(byte v) {
/* 1491 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1493 */       while (i.hasNext()) {
/* 1494 */         byte ev = (i.nextEntry()).value;
/* 1495 */         if (ev == v)
/* 1496 */           return true; 
/*      */       } 
/* 1498 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public byte get(short k) {
/* 1504 */       short kk = k; Short2ByteRBTreeMap.Entry e;
/* 1505 */       return (in(kk) && (e = Short2ByteRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public byte put(short k, byte v) {
/* 1509 */       Short2ByteRBTreeMap.this.modified = false;
/* 1510 */       if (!in(k))
/* 1511 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1512 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1513 */       byte oldValue = Short2ByteRBTreeMap.this.put(k, v);
/* 1514 */       return Short2ByteRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte remove(short k) {
/* 1519 */       Short2ByteRBTreeMap.this.modified = false;
/* 1520 */       if (!in(k))
/* 1521 */         return this.defRetValue; 
/* 1522 */       byte oldValue = Short2ByteRBTreeMap.this.remove(k);
/* 1523 */       return Short2ByteRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1527 */       SubmapIterator i = new SubmapIterator();
/* 1528 */       int n = 0;
/* 1529 */       while (i.hasNext()) {
/* 1530 */         n++;
/* 1531 */         i.nextEntry();
/*      */       } 
/* 1533 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1537 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public ShortComparator comparator() {
/* 1541 */       return Short2ByteRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Short2ByteSortedMap headMap(short to) {
/* 1545 */       if (this.top)
/* 1546 */         return new Submap(this.from, this.bottom, to, false); 
/* 1547 */       return (Short2ByteRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Short2ByteSortedMap tailMap(short from) {
/* 1551 */       if (this.bottom)
/* 1552 */         return new Submap(from, false, this.to, this.top); 
/* 1553 */       return (Short2ByteRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Short2ByteSortedMap subMap(short from, short to) {
/* 1557 */       if (this.top && this.bottom)
/* 1558 */         return new Submap(from, false, to, false); 
/* 1559 */       if (!this.top)
/* 1560 */         to = (Short2ByteRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1561 */       if (!this.bottom)
/* 1562 */         from = (Short2ByteRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1563 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1564 */         return this; 
/* 1565 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Short2ByteRBTreeMap.Entry firstEntry() {
/*      */       Short2ByteRBTreeMap.Entry e;
/* 1574 */       if (Short2ByteRBTreeMap.this.tree == null) {
/* 1575 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1579 */       if (this.bottom) {
/* 1580 */         e = Short2ByteRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1582 */         e = Short2ByteRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1584 */         if (Short2ByteRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1585 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1589 */       if (e == null || (!this.top && Short2ByteRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1590 */         return null; 
/* 1591 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Short2ByteRBTreeMap.Entry lastEntry() {
/*      */       Short2ByteRBTreeMap.Entry e;
/* 1600 */       if (Short2ByteRBTreeMap.this.tree == null) {
/* 1601 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1605 */       if (this.top) {
/* 1606 */         e = Short2ByteRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1608 */         e = Short2ByteRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1610 */         if (Short2ByteRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1611 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1615 */       if (e == null || (!this.bottom && Short2ByteRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1616 */         return null; 
/* 1617 */       return e;
/*      */     }
/*      */     
/*      */     public short firstShortKey() {
/* 1621 */       Short2ByteRBTreeMap.Entry e = firstEntry();
/* 1622 */       if (e == null)
/* 1623 */         throw new NoSuchElementException(); 
/* 1624 */       return e.key;
/*      */     }
/*      */     
/*      */     public short lastShortKey() {
/* 1628 */       Short2ByteRBTreeMap.Entry e = lastEntry();
/* 1629 */       if (e == null)
/* 1630 */         throw new NoSuchElementException(); 
/* 1631 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Short2ByteRBTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1644 */         this.next = Short2ByteRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(short k) {
/* 1647 */         this();
/* 1648 */         if (this.next != null)
/* 1649 */           if (!Short2ByteRBTreeMap.Submap.this.bottom && Short2ByteRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1650 */             this.prev = null;
/* 1651 */           } else if (!Short2ByteRBTreeMap.Submap.this.top && Short2ByteRBTreeMap.this.compare(k, (this.prev = Short2ByteRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1652 */             this.next = null;
/*      */           } else {
/* 1654 */             this.next = Short2ByteRBTreeMap.this.locateKey(k);
/* 1655 */             if (Short2ByteRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1656 */               this.prev = this.next;
/* 1657 */               this.next = this.next.next();
/*      */             } else {
/* 1659 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1665 */         this.prev = this.prev.prev();
/* 1666 */         if (!Short2ByteRBTreeMap.Submap.this.bottom && this.prev != null && Short2ByteRBTreeMap.this.compare(this.prev.key, Short2ByteRBTreeMap.Submap.this.from) < 0)
/* 1667 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1671 */         this.next = this.next.next();
/* 1672 */         if (!Short2ByteRBTreeMap.Submap.this.top && this.next != null && Short2ByteRBTreeMap.this.compare(this.next.key, Short2ByteRBTreeMap.Submap.this.to) >= 0)
/* 1673 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Short2ByteMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(short k) {
/* 1680 */         super(k);
/*      */       }
/*      */       
/*      */       public Short2ByteMap.Entry next() {
/* 1684 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Short2ByteMap.Entry previous() {
/* 1688 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements ShortListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(short from) {
/* 1706 */         super(from);
/*      */       }
/*      */       
/*      */       public short nextShort() {
/* 1710 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public short previousShort() {
/* 1714 */         return (previousEntry()).key;
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
/* 1730 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public byte previousByte() {
/* 1734 */         return (previousEntry()).value;
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
/*      */   public Short2ByteRBTreeMap clone() {
/*      */     Short2ByteRBTreeMap c;
/*      */     try {
/* 1753 */       c = (Short2ByteRBTreeMap)super.clone();
/* 1754 */     } catch (CloneNotSupportedException cantHappen) {
/* 1755 */       throw new InternalError();
/*      */     } 
/* 1757 */     c.keys = null;
/* 1758 */     c.values = null;
/* 1759 */     c.entries = null;
/* 1760 */     c.allocatePaths();
/* 1761 */     if (this.count != 0) {
/*      */       
/* 1763 */       Entry rp = new Entry(), rq = new Entry();
/* 1764 */       Entry p = rp;
/* 1765 */       rp.left(this.tree);
/* 1766 */       Entry q = rq;
/* 1767 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1769 */         if (!p.pred()) {
/* 1770 */           Entry e = p.left.clone();
/* 1771 */           e.pred(q.left);
/* 1772 */           e.succ(q);
/* 1773 */           q.left(e);
/* 1774 */           p = p.left;
/* 1775 */           q = q.left;
/*      */         } else {
/* 1777 */           while (p.succ()) {
/* 1778 */             p = p.right;
/* 1779 */             if (p == null) {
/* 1780 */               q.right = null;
/* 1781 */               c.tree = rq.left;
/* 1782 */               c.firstEntry = c.tree;
/* 1783 */               while (c.firstEntry.left != null)
/* 1784 */                 c.firstEntry = c.firstEntry.left; 
/* 1785 */               c.lastEntry = c.tree;
/* 1786 */               while (c.lastEntry.right != null)
/* 1787 */                 c.lastEntry = c.lastEntry.right; 
/* 1788 */               return c;
/*      */             } 
/* 1790 */             q = q.right;
/*      */           } 
/* 1792 */           p = p.right;
/* 1793 */           q = q.right;
/*      */         } 
/* 1795 */         if (!p.succ()) {
/* 1796 */           Entry e = p.right.clone();
/* 1797 */           e.succ(q.right);
/* 1798 */           e.pred(q);
/* 1799 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1803 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1806 */     int n = this.count;
/* 1807 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1809 */     s.defaultWriteObject();
/* 1810 */     while (n-- != 0) {
/* 1811 */       Entry e = i.nextEntry();
/* 1812 */       s.writeShort(e.key);
/* 1813 */       s.writeByte(e.value);
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
/* 1834 */     if (n == 1) {
/* 1835 */       Entry entry = new Entry(s.readShort(), s.readByte());
/* 1836 */       entry.pred(pred);
/* 1837 */       entry.succ(succ);
/* 1838 */       entry.black(true);
/* 1839 */       return entry;
/*      */     } 
/* 1841 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1846 */       Entry entry = new Entry(s.readShort(), s.readByte());
/* 1847 */       entry.black(true);
/* 1848 */       entry.right(new Entry(s.readShort(), s.readByte()));
/* 1849 */       entry.right.pred(entry);
/* 1850 */       entry.pred(pred);
/* 1851 */       entry.right.succ(succ);
/* 1852 */       return entry;
/*      */     } 
/*      */     
/* 1855 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1856 */     Entry top = new Entry();
/* 1857 */     top.left(readTree(s, leftN, pred, top));
/* 1858 */     top.key = s.readShort();
/* 1859 */     top.value = s.readByte();
/* 1860 */     top.black(true);
/* 1861 */     top.right(readTree(s, rightN, top, succ));
/* 1862 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1863 */       top.right.black(false); 
/* 1864 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1867 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1872 */     setActualComparator();
/* 1873 */     allocatePaths();
/* 1874 */     if (this.count != 0) {
/* 1875 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1877 */       Entry e = this.tree;
/* 1878 */       while (e.left() != null)
/* 1879 */         e = e.left(); 
/* 1880 */       this.firstEntry = e;
/* 1881 */       e = this.tree;
/* 1882 */       while (e.right() != null)
/* 1883 */         e = e.right(); 
/* 1884 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2ByteRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */