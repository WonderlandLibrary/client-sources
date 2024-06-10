/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
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
/*      */ public class Long2BooleanRBTreeMap
/*      */   extends AbstractLong2BooleanSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Long2BooleanMap.Entry> entries;
/*      */   protected transient LongSortedSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Long> storedComparator;
/*      */   protected transient LongComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public Long2BooleanRBTreeMap() {
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
/*   91 */     this.actualComparator = LongComparators.asLongComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2BooleanRBTreeMap(Comparator<? super Long> c) {
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
/*      */   public Long2BooleanRBTreeMap(Map<? extends Long, ? extends Boolean> m) {
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
/*      */   public Long2BooleanRBTreeMap(SortedMap<Long, Boolean> m) {
/*  122 */     this(m.comparator());
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2BooleanRBTreeMap(Long2BooleanMap m) {
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
/*      */   public Long2BooleanRBTreeMap(Long2BooleanSortedMap m) {
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
/*      */   public Long2BooleanRBTreeMap(long[] k, boolean[] v, Comparator<? super Long> c) {
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
/*      */   public Long2BooleanRBTreeMap(long[] k, boolean[] v) {
/*  178 */     this(k, v, (Comparator<? super Long>)null);
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
/*  206 */     return (this.actualComparator == null) ? Long.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry locateKey(long k) {
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
/*      */   public boolean put(long k, boolean v) {
/*  255 */     Entry e = add(k);
/*  256 */     boolean oldValue = e.value;
/*  257 */     e.value = v;
/*  258 */     return oldValue;
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
/*      */   private Entry add(long k) {
/*      */     Entry e;
/*  275 */     this.modified = false;
/*  276 */     int maxDepth = 0;
/*      */     
/*  278 */     if (this.tree == null) {
/*  279 */       this.count++;
/*  280 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry(k, this.defRetValue);
/*      */     } else {
/*  282 */       Entry p = this.tree;
/*  283 */       int i = 0; while (true) {
/*      */         int cmp;
/*  285 */         if ((cmp = compare(k, p.key)) == 0) {
/*      */           
/*  287 */           while (i-- != 0)
/*  288 */             this.nodePath[i] = null; 
/*  289 */           return p;
/*      */         } 
/*  291 */         this.nodePath[i] = p;
/*  292 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  293 */           if (p.succ()) {
/*  294 */             this.count++;
/*  295 */             e = new Entry(k, this.defRetValue);
/*  296 */             if (p.right == null)
/*  297 */               this.lastEntry = e; 
/*  298 */             e.left = p;
/*  299 */             e.right = p.right;
/*  300 */             p.right(e);
/*      */             break;
/*      */           } 
/*  303 */           p = p.right; continue;
/*      */         } 
/*  305 */         if (p.pred()) {
/*  306 */           this.count++;
/*  307 */           e = new Entry(k, this.defRetValue);
/*  308 */           if (p.left == null)
/*  309 */             this.firstEntry = e; 
/*  310 */           e.right = p;
/*  311 */           e.left = p.left;
/*  312 */           p.left(e);
/*      */           break;
/*      */         } 
/*  315 */         p = p.left;
/*      */       } 
/*      */       
/*  318 */       this.modified = true;
/*  319 */       maxDepth = i--;
/*  320 */       while (i > 0 && !this.nodePath[i].black()) {
/*  321 */         if (!this.dirPath[i - 1]) {
/*  322 */           Entry entry1 = (this.nodePath[i - 1]).right;
/*  323 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  324 */             this.nodePath[i].black(true);
/*  325 */             entry1.black(true);
/*  326 */             this.nodePath[i - 1].black(false);
/*  327 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  330 */           if (!this.dirPath[i]) {
/*  331 */             entry1 = this.nodePath[i];
/*      */           } else {
/*  333 */             Entry entry = this.nodePath[i];
/*  334 */             entry1 = entry.right;
/*  335 */             entry.right = entry1.left;
/*  336 */             entry1.left = entry;
/*  337 */             (this.nodePath[i - 1]).left = entry1;
/*  338 */             if (entry1.pred()) {
/*  339 */               entry1.pred(false);
/*  340 */               entry.succ(entry1);
/*      */             } 
/*      */           } 
/*  343 */           Entry entry2 = this.nodePath[i - 1];
/*  344 */           entry2.black(false);
/*  345 */           entry1.black(true);
/*  346 */           entry2.left = entry1.right;
/*  347 */           entry1.right = entry2;
/*  348 */           if (i < 2) {
/*  349 */             this.tree = entry1;
/*      */           }
/*  351 */           else if (this.dirPath[i - 2]) {
/*  352 */             (this.nodePath[i - 2]).right = entry1;
/*      */           } else {
/*  354 */             (this.nodePath[i - 2]).left = entry1;
/*      */           } 
/*  356 */           if (entry1.succ()) {
/*  357 */             entry1.succ(false);
/*  358 */             entry2.pred(entry1);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  363 */         Entry y = (this.nodePath[i - 1]).left;
/*  364 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  365 */           this.nodePath[i].black(true);
/*  366 */           y.black(true);
/*  367 */           this.nodePath[i - 1].black(false);
/*  368 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  371 */         if (this.dirPath[i]) {
/*  372 */           y = this.nodePath[i];
/*      */         } else {
/*  374 */           Entry entry = this.nodePath[i];
/*  375 */           y = entry.left;
/*  376 */           entry.left = y.right;
/*  377 */           y.right = entry;
/*  378 */           (this.nodePath[i - 1]).right = y;
/*  379 */           if (y.succ()) {
/*  380 */             y.succ(false);
/*  381 */             entry.pred(y);
/*      */           } 
/*      */         } 
/*  384 */         Entry x = this.nodePath[i - 1];
/*  385 */         x.black(false);
/*  386 */         y.black(true);
/*  387 */         x.right = y.left;
/*  388 */         y.left = x;
/*  389 */         if (i < 2) {
/*  390 */           this.tree = y;
/*      */         }
/*  392 */         else if (this.dirPath[i - 2]) {
/*  393 */           (this.nodePath[i - 2]).right = y;
/*      */         } else {
/*  395 */           (this.nodePath[i - 2]).left = y;
/*      */         } 
/*  397 */         if (y.pred()) {
/*  398 */           y.pred(false);
/*  399 */           x.succ(y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  406 */     this.tree.black(true);
/*      */     
/*  408 */     while (maxDepth-- != 0)
/*  409 */       this.nodePath[maxDepth] = null; 
/*  410 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k) {
/*  419 */     this.modified = false;
/*  420 */     if (this.tree == null)
/*  421 */       return this.defRetValue; 
/*  422 */     Entry p = this.tree;
/*      */     
/*  424 */     int i = 0;
/*  425 */     long kk = k;
/*      */     int cmp;
/*  427 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  429 */       this.dirPath[i] = (cmp > 0);
/*  430 */       this.nodePath[i] = p;
/*  431 */       if (this.dirPath[i++]) {
/*  432 */         if ((p = p.right()) == null) {
/*      */           
/*  434 */           while (i-- != 0)
/*  435 */             this.nodePath[i] = null; 
/*  436 */           return this.defRetValue;
/*      */         }  continue;
/*      */       } 
/*  439 */       if ((p = p.left()) == null) {
/*      */         
/*  441 */         while (i-- != 0)
/*  442 */           this.nodePath[i] = null; 
/*  443 */         return this.defRetValue;
/*      */       } 
/*      */     } 
/*      */     
/*  447 */     if (p.left == null)
/*  448 */       this.firstEntry = p.next(); 
/*  449 */     if (p.right == null)
/*  450 */       this.lastEntry = p.prev(); 
/*  451 */     if (p.succ()) {
/*  452 */       if (p.pred()) {
/*  453 */         if (i == 0) {
/*  454 */           this.tree = p.left;
/*      */         }
/*  456 */         else if (this.dirPath[i - 1]) {
/*  457 */           this.nodePath[i - 1].succ(p.right);
/*      */         } else {
/*  459 */           this.nodePath[i - 1].pred(p.left);
/*      */         } 
/*      */       } else {
/*  462 */         (p.prev()).right = p.right;
/*  463 */         if (i == 0) {
/*  464 */           this.tree = p.left;
/*      */         }
/*  466 */         else if (this.dirPath[i - 1]) {
/*  467 */           (this.nodePath[i - 1]).right = p.left;
/*      */         } else {
/*  469 */           (this.nodePath[i - 1]).left = p.left;
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  474 */       Entry r = p.right;
/*  475 */       if (r.pred()) {
/*  476 */         r.left = p.left;
/*  477 */         r.pred(p.pred());
/*  478 */         if (!r.pred())
/*  479 */           (r.prev()).right = r; 
/*  480 */         if (i == 0) {
/*  481 */           this.tree = r;
/*      */         }
/*  483 */         else if (this.dirPath[i - 1]) {
/*  484 */           (this.nodePath[i - 1]).right = r;
/*      */         } else {
/*  486 */           (this.nodePath[i - 1]).left = r;
/*      */         } 
/*  488 */         boolean color = r.black();
/*  489 */         r.black(p.black());
/*  490 */         p.black(color);
/*  491 */         this.dirPath[i] = true;
/*  492 */         this.nodePath[i++] = r;
/*      */       } else {
/*      */         Entry s;
/*  495 */         int j = i++;
/*      */         while (true) {
/*  497 */           this.dirPath[i] = false;
/*  498 */           this.nodePath[i++] = r;
/*  499 */           s = r.left;
/*  500 */           if (s.pred())
/*      */             break; 
/*  502 */           r = s;
/*      */         } 
/*  504 */         this.dirPath[j] = true;
/*  505 */         this.nodePath[j] = s;
/*  506 */         if (s.succ()) {
/*  507 */           r.pred(s);
/*      */         } else {
/*  509 */           r.left = s.right;
/*  510 */         }  s.left = p.left;
/*  511 */         if (!p.pred()) {
/*  512 */           (p.prev()).right = s;
/*  513 */           s.pred(false);
/*      */         } 
/*  515 */         s.right(p.right);
/*  516 */         boolean color = s.black();
/*  517 */         s.black(p.black());
/*  518 */         p.black(color);
/*  519 */         if (j == 0) {
/*  520 */           this.tree = s;
/*      */         }
/*  522 */         else if (this.dirPath[j - 1]) {
/*  523 */           (this.nodePath[j - 1]).right = s;
/*      */         } else {
/*  525 */           (this.nodePath[j - 1]).left = s;
/*      */         } 
/*      */       } 
/*      */     } 
/*  529 */     int maxDepth = i;
/*  530 */     if (p.black()) {
/*  531 */       for (; i > 0; i--) {
/*  532 */         if ((this.dirPath[i - 1] && !this.nodePath[i - 1].succ()) || (!this.dirPath[i - 1] && !this.nodePath[i - 1].pred())) {
/*  533 */           Entry x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  534 */           if (!x.black()) {
/*  535 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  539 */         if (!this.dirPath[i - 1]) {
/*  540 */           Entry w = (this.nodePath[i - 1]).right;
/*  541 */           if (!w.black()) {
/*  542 */             w.black(true);
/*  543 */             this.nodePath[i - 1].black(false);
/*  544 */             (this.nodePath[i - 1]).right = w.left;
/*  545 */             w.left = this.nodePath[i - 1];
/*  546 */             if (i < 2) {
/*  547 */               this.tree = w;
/*      */             }
/*  549 */             else if (this.dirPath[i - 2]) {
/*  550 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  552 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  554 */             this.nodePath[i] = this.nodePath[i - 1];
/*  555 */             this.dirPath[i] = false;
/*  556 */             this.nodePath[i - 1] = w;
/*  557 */             if (maxDepth == i++)
/*  558 */               maxDepth++; 
/*  559 */             w = (this.nodePath[i - 1]).right;
/*      */           } 
/*  561 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  562 */             w.black(false);
/*      */           } else {
/*  564 */             if (w.succ() || w.right.black()) {
/*  565 */               Entry y = w.left;
/*  566 */               y.black(true);
/*  567 */               w.black(false);
/*  568 */               w.left = y.right;
/*  569 */               y.right = w;
/*  570 */               w = (this.nodePath[i - 1]).right = y;
/*  571 */               if (w.succ()) {
/*  572 */                 w.succ(false);
/*  573 */                 w.right.pred(w);
/*      */               } 
/*      */             } 
/*  576 */             w.black(this.nodePath[i - 1].black());
/*  577 */             this.nodePath[i - 1].black(true);
/*  578 */             w.right.black(true);
/*  579 */             (this.nodePath[i - 1]).right = w.left;
/*  580 */             w.left = this.nodePath[i - 1];
/*  581 */             if (i < 2) {
/*  582 */               this.tree = w;
/*      */             }
/*  584 */             else if (this.dirPath[i - 2]) {
/*  585 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  587 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  589 */             if (w.pred()) {
/*  590 */               w.pred(false);
/*  591 */               this.nodePath[i - 1].succ(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } else {
/*  596 */           Entry w = (this.nodePath[i - 1]).left;
/*  597 */           if (!w.black()) {
/*  598 */             w.black(true);
/*  599 */             this.nodePath[i - 1].black(false);
/*  600 */             (this.nodePath[i - 1]).left = w.right;
/*  601 */             w.right = this.nodePath[i - 1];
/*  602 */             if (i < 2) {
/*  603 */               this.tree = w;
/*      */             }
/*  605 */             else if (this.dirPath[i - 2]) {
/*  606 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  608 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  610 */             this.nodePath[i] = this.nodePath[i - 1];
/*  611 */             this.dirPath[i] = true;
/*  612 */             this.nodePath[i - 1] = w;
/*  613 */             if (maxDepth == i++)
/*  614 */               maxDepth++; 
/*  615 */             w = (this.nodePath[i - 1]).left;
/*      */           } 
/*  617 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  618 */             w.black(false);
/*      */           } else {
/*  620 */             if (w.pred() || w.left.black()) {
/*  621 */               Entry y = w.right;
/*  622 */               y.black(true);
/*  623 */               w.black(false);
/*  624 */               w.right = y.left;
/*  625 */               y.left = w;
/*  626 */               w = (this.nodePath[i - 1]).left = y;
/*  627 */               if (w.pred()) {
/*  628 */                 w.pred(false);
/*  629 */                 w.left.succ(w);
/*      */               } 
/*      */             } 
/*  632 */             w.black(this.nodePath[i - 1].black());
/*  633 */             this.nodePath[i - 1].black(true);
/*  634 */             w.left.black(true);
/*  635 */             (this.nodePath[i - 1]).left = w.right;
/*  636 */             w.right = this.nodePath[i - 1];
/*  637 */             if (i < 2) {
/*  638 */               this.tree = w;
/*      */             }
/*  640 */             else if (this.dirPath[i - 2]) {
/*  641 */               (this.nodePath[i - 2]).right = w;
/*      */             } else {
/*  643 */               (this.nodePath[i - 2]).left = w;
/*      */             } 
/*  645 */             if (w.succ()) {
/*  646 */               w.succ(false);
/*  647 */               this.nodePath[i - 1].pred(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  653 */       if (this.tree != null)
/*  654 */         this.tree.black(true); 
/*      */     } 
/*  656 */     this.modified = true;
/*  657 */     this.count--;
/*      */     
/*  659 */     while (maxDepth-- != 0)
/*  660 */       this.nodePath[maxDepth] = null; 
/*  661 */     return p.value;
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  665 */     ValueIterator i = new ValueIterator();
/*      */     
/*  667 */     int j = this.count;
/*  668 */     while (j-- != 0) {
/*  669 */       boolean ev = i.nextBoolean();
/*  670 */       if (ev == v)
/*  671 */         return true; 
/*      */     } 
/*  673 */     return false;
/*      */   }
/*      */   
/*      */   public void clear() {
/*  677 */     this.count = 0;
/*  678 */     this.tree = null;
/*  679 */     this.entries = null;
/*  680 */     this.values = null;
/*  681 */     this.keys = null;
/*  682 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry
/*      */     extends AbstractLong2BooleanMap.BasicEntry
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
/*  710 */       super(0L, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(long k, boolean v) {
/*  721 */       super(k, v);
/*  722 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left() {
/*  730 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right() {
/*  738 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  746 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  754 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  763 */       if (pred) {
/*  764 */         this.info |= 0x40000000;
/*      */       } else {
/*  766 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  775 */       if (succ) {
/*  776 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  778 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry pred) {
/*  787 */       this.info |= 0x40000000;
/*  788 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry succ) {
/*  797 */       this.info |= Integer.MIN_VALUE;
/*  798 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry left) {
/*  807 */       this.info &= 0xBFFFFFFF;
/*  808 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry right) {
/*  817 */       this.info &= Integer.MAX_VALUE;
/*  818 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean black() {
/*  826 */       return ((this.info & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void black(boolean black) {
/*  835 */       if (black) {
/*  836 */         this.info |= 0x1;
/*      */       } else {
/*  838 */         this.info &= 0xFFFFFFFE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry next() {
/*  846 */       Entry next = this.right;
/*  847 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  848 */         while ((next.info & 0x40000000) == 0)
/*  849 */           next = next.left;  
/*  850 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry prev() {
/*  858 */       Entry prev = this.left;
/*  859 */       if ((this.info & 0x40000000) == 0)
/*  860 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  861 */           prev = prev.right;  
/*  862 */       return prev;
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean value) {
/*  866 */       boolean oldValue = this.value;
/*  867 */       this.value = value;
/*  868 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry clone() {
/*      */       Entry c;
/*      */       try {
/*  875 */         c = (Entry)super.clone();
/*  876 */       } catch (CloneNotSupportedException cantHappen) {
/*  877 */         throw new InternalError();
/*      */       } 
/*  879 */       c.key = this.key;
/*  880 */       c.value = this.value;
/*  881 */       c.info = this.info;
/*  882 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  887 */       if (!(o instanceof Map.Entry))
/*  888 */         return false; 
/*  889 */       Map.Entry<Long, Boolean> e = (Map.Entry<Long, Boolean>)o;
/*  890 */       return (this.key == ((Long)e.getKey()).longValue() && this.value == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  894 */       return HashCommon.long2int(this.key) ^ (this.value ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  898 */       return this.key + "=>" + this.value;
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
/*  919 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  923 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  927 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean get(long k) {
/*  932 */     Entry e = findKey(k);
/*  933 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public long firstLongKey() {
/*  937 */     if (this.tree == null)
/*  938 */       throw new NoSuchElementException(); 
/*  939 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public long lastLongKey() {
/*  943 */     if (this.tree == null)
/*  944 */       throw new NoSuchElementException(); 
/*  945 */     return this.lastEntry.key;
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
/*      */     Long2BooleanRBTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Long2BooleanRBTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Long2BooleanRBTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  976 */     int index = 0;
/*      */     TreeIterator() {
/*  978 */       this.next = Long2BooleanRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(long k) {
/*  981 */       if ((this.next = Long2BooleanRBTreeMap.this.locateKey(k)) != null)
/*  982 */         if (Long2BooleanRBTreeMap.this.compare(this.next.key, k) <= 0) {
/*  983 */           this.prev = this.next;
/*  984 */           this.next = this.next.next();
/*      */         } else {
/*  986 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/*  990 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/*  993 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/*  996 */       this.next = this.next.next();
/*      */     }
/*      */     Long2BooleanRBTreeMap.Entry nextEntry() {
/*  999 */       if (!hasNext())
/* 1000 */         throw new NoSuchElementException(); 
/* 1001 */       this.curr = this.prev = this.next;
/* 1002 */       this.index++;
/* 1003 */       updateNext();
/* 1004 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1007 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Long2BooleanRBTreeMap.Entry previousEntry() {
/* 1010 */       if (!hasPrevious())
/* 1011 */         throw new NoSuchElementException(); 
/* 1012 */       this.curr = this.next = this.prev;
/* 1013 */       this.index--;
/* 1014 */       updatePrevious();
/* 1015 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1018 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1021 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1024 */       if (this.curr == null) {
/* 1025 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1030 */       if (this.curr == this.prev)
/* 1031 */         this.index--; 
/* 1032 */       this.next = this.prev = this.curr;
/* 1033 */       updatePrevious();
/* 1034 */       updateNext();
/* 1035 */       Long2BooleanRBTreeMap.this.remove(this.curr.key);
/* 1036 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1039 */       int i = n;
/* 1040 */       while (i-- != 0 && hasNext())
/* 1041 */         nextEntry(); 
/* 1042 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1045 */       int i = n;
/* 1046 */       while (i-- != 0 && hasPrevious())
/* 1047 */         previousEntry(); 
/* 1048 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Long2BooleanMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(long k) {
/* 1061 */       super(k);
/*      */     }
/*      */     
/*      */     public Long2BooleanMap.Entry next() {
/* 1065 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Long2BooleanMap.Entry previous() {
/* 1069 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Long2BooleanMap.Entry> long2BooleanEntrySet() {
/* 1074 */     if (this.entries == null)
/* 1075 */       this.entries = (ObjectSortedSet<Long2BooleanMap.Entry>)new AbstractObjectSortedSet<Long2BooleanMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Long2BooleanMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Long2BooleanMap.Entry> comparator() {
/* 1080 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Long2BooleanMap.Entry> iterator() {
/* 1084 */             return (ObjectBidirectionalIterator<Long2BooleanMap.Entry>)new Long2BooleanRBTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Long2BooleanMap.Entry> iterator(Long2BooleanMap.Entry from) {
/* 1088 */             return (ObjectBidirectionalIterator<Long2BooleanMap.Entry>)new Long2BooleanRBTreeMap.EntryIterator(from.getLongKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1093 */             if (!(o instanceof Map.Entry))
/* 1094 */               return false; 
/* 1095 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1096 */             if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 1097 */               return false; 
/* 1098 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1099 */               return false; 
/* 1100 */             Long2BooleanRBTreeMap.Entry f = Long2BooleanRBTreeMap.this.findKey(((Long)e.getKey()).longValue());
/* 1101 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1106 */             if (!(o instanceof Map.Entry))
/* 1107 */               return false; 
/* 1108 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1109 */             if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 1110 */               return false; 
/* 1111 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1112 */               return false; 
/* 1113 */             Long2BooleanRBTreeMap.Entry f = Long2BooleanRBTreeMap.this.findKey(((Long)e.getKey()).longValue());
/* 1114 */             if (f == null || f.getBooleanValue() != ((Boolean)e.getValue()).booleanValue())
/* 1115 */               return false; 
/* 1116 */             Long2BooleanRBTreeMap.this.remove(f.key);
/* 1117 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1121 */             return Long2BooleanRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1125 */             Long2BooleanRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Long2BooleanMap.Entry first() {
/* 1129 */             return Long2BooleanRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Long2BooleanMap.Entry last() {
/* 1133 */             return Long2BooleanRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Long2BooleanMap.Entry> subSet(Long2BooleanMap.Entry from, Long2BooleanMap.Entry to) {
/* 1138 */             return Long2BooleanRBTreeMap.this.subMap(from.getLongKey(), to.getLongKey()).long2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Long2BooleanMap.Entry> headSet(Long2BooleanMap.Entry to) {
/* 1142 */             return Long2BooleanRBTreeMap.this.headMap(to.getLongKey()).long2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Long2BooleanMap.Entry> tailSet(Long2BooleanMap.Entry from) {
/* 1146 */             return Long2BooleanRBTreeMap.this.tailMap(from.getLongKey()).long2BooleanEntrySet();
/*      */           }
/*      */         }; 
/* 1149 */     return this.entries;
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
/* 1165 */       super(k);
/*      */     }
/*      */     
/*      */     public long nextLong() {
/* 1169 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public long previousLong() {
/* 1173 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractLong2BooleanSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public LongBidirectionalIterator iterator() {
/* 1180 */       return new Long2BooleanRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public LongBidirectionalIterator iterator(long from) {
/* 1184 */       return new Long2BooleanRBTreeMap.KeyIterator(from);
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
/* 1199 */     if (this.keys == null)
/* 1200 */       this.keys = new KeySet(); 
/* 1201 */     return this.keys;
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
/* 1216 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public boolean previousBoolean() {
/* 1220 */       return (previousEntry()).value;
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
/* 1235 */     if (this.values == null)
/* 1236 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1239 */             return (BooleanIterator)new Long2BooleanRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(boolean k) {
/* 1243 */             return Long2BooleanRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1247 */             return Long2BooleanRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1251 */             Long2BooleanRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1254 */     return this.values;
/*      */   }
/*      */   
/*      */   public LongComparator comparator() {
/* 1258 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Long2BooleanSortedMap headMap(long to) {
/* 1262 */     return new Submap(0L, true, to, false);
/*      */   }
/*      */   
/*      */   public Long2BooleanSortedMap tailMap(long from) {
/* 1266 */     return new Submap(from, false, 0L, true);
/*      */   }
/*      */   
/*      */   public Long2BooleanSortedMap subMap(long from, long to) {
/* 1270 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractLong2BooleanSortedMap
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
/*      */     protected transient ObjectSortedSet<Long2BooleanMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient LongSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(long from, boolean bottom, long to, boolean top) {
/* 1314 */       if (!bottom && !top && Long2BooleanRBTreeMap.this.compare(from, to) > 0)
/* 1315 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1316 */       this.from = from;
/* 1317 */       this.bottom = bottom;
/* 1318 */       this.to = to;
/* 1319 */       this.top = top;
/* 1320 */       this.defRetValue = Long2BooleanRBTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1324 */       SubmapIterator i = new SubmapIterator();
/* 1325 */       while (i.hasNext()) {
/* 1326 */         i.nextEntry();
/* 1327 */         i.remove();
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
/* 1338 */       return ((this.bottom || Long2BooleanRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Long2BooleanRBTreeMap.this
/* 1339 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Long2BooleanMap.Entry> long2BooleanEntrySet() {
/* 1343 */       if (this.entries == null)
/* 1344 */         this.entries = (ObjectSortedSet<Long2BooleanMap.Entry>)new AbstractObjectSortedSet<Long2BooleanMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Long2BooleanMap.Entry> iterator() {
/* 1347 */               return (ObjectBidirectionalIterator<Long2BooleanMap.Entry>)new Long2BooleanRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Long2BooleanMap.Entry> iterator(Long2BooleanMap.Entry from) {
/* 1352 */               return (ObjectBidirectionalIterator<Long2BooleanMap.Entry>)new Long2BooleanRBTreeMap.Submap.SubmapEntryIterator(from.getLongKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Long2BooleanMap.Entry> comparator() {
/* 1356 */               return Long2BooleanRBTreeMap.this.long2BooleanEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1361 */               if (!(o instanceof Map.Entry))
/* 1362 */                 return false; 
/* 1363 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1364 */               if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 1365 */                 return false; 
/* 1366 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1367 */                 return false; 
/* 1368 */               Long2BooleanRBTreeMap.Entry f = Long2BooleanRBTreeMap.this.findKey(((Long)e.getKey()).longValue());
/* 1369 */               return (f != null && Long2BooleanRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1374 */               if (!(o instanceof Map.Entry))
/* 1375 */                 return false; 
/* 1376 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1377 */               if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 1378 */                 return false; 
/* 1379 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1380 */                 return false; 
/* 1381 */               Long2BooleanRBTreeMap.Entry f = Long2BooleanRBTreeMap.this.findKey(((Long)e.getKey()).longValue());
/* 1382 */               if (f != null && Long2BooleanRBTreeMap.Submap.this.in(f.key))
/* 1383 */                 Long2BooleanRBTreeMap.Submap.this.remove(f.key); 
/* 1384 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1388 */               int c = 0;
/* 1389 */               for (ObjectBidirectionalIterator<Long2BooleanMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1390 */                 c++; 
/* 1391 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1395 */               return !(new Long2BooleanRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1399 */               Long2BooleanRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Long2BooleanMap.Entry first() {
/* 1403 */               return Long2BooleanRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Long2BooleanMap.Entry last() {
/* 1407 */               return Long2BooleanRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Long2BooleanMap.Entry> subSet(Long2BooleanMap.Entry from, Long2BooleanMap.Entry to) {
/* 1412 */               return Long2BooleanRBTreeMap.Submap.this.subMap(from.getLongKey(), to.getLongKey()).long2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Long2BooleanMap.Entry> headSet(Long2BooleanMap.Entry to) {
/* 1416 */               return Long2BooleanRBTreeMap.Submap.this.headMap(to.getLongKey()).long2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Long2BooleanMap.Entry> tailSet(Long2BooleanMap.Entry from) {
/* 1420 */               return Long2BooleanRBTreeMap.Submap.this.tailMap(from.getLongKey()).long2BooleanEntrySet();
/*      */             }
/*      */           }; 
/* 1423 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractLong2BooleanSortedMap.KeySet {
/*      */       public LongBidirectionalIterator iterator() {
/* 1428 */         return new Long2BooleanRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public LongBidirectionalIterator iterator(long from) {
/* 1432 */         return new Long2BooleanRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public LongSortedSet keySet() {
/* 1437 */       if (this.keys == null)
/* 1438 */         this.keys = new KeySet(); 
/* 1439 */       return this.keys;
/*      */     }
/*      */     
/*      */     public BooleanCollection values() {
/* 1443 */       if (this.values == null)
/* 1444 */         this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */           {
/*      */             public BooleanIterator iterator() {
/* 1447 */               return (BooleanIterator)new Long2BooleanRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(boolean k) {
/* 1451 */               return Long2BooleanRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1455 */               return Long2BooleanRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1459 */               Long2BooleanRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1462 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(long k) {
/* 1467 */       return (in(k) && Long2BooleanRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(boolean v) {
/* 1471 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1473 */       while (i.hasNext()) {
/* 1474 */         boolean ev = (i.nextEntry()).value;
/* 1475 */         if (ev == v)
/* 1476 */           return true; 
/*      */       } 
/* 1478 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean get(long k) {
/* 1484 */       long kk = k; Long2BooleanRBTreeMap.Entry e;
/* 1485 */       return (in(kk) && (e = Long2BooleanRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public boolean put(long k, boolean v) {
/* 1489 */       Long2BooleanRBTreeMap.this.modified = false;
/* 1490 */       if (!in(k))
/* 1491 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1492 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1493 */       boolean oldValue = Long2BooleanRBTreeMap.this.put(k, v);
/* 1494 */       return Long2BooleanRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(long k) {
/* 1499 */       Long2BooleanRBTreeMap.this.modified = false;
/* 1500 */       if (!in(k))
/* 1501 */         return this.defRetValue; 
/* 1502 */       boolean oldValue = Long2BooleanRBTreeMap.this.remove(k);
/* 1503 */       return Long2BooleanRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1507 */       SubmapIterator i = new SubmapIterator();
/* 1508 */       int n = 0;
/* 1509 */       while (i.hasNext()) {
/* 1510 */         n++;
/* 1511 */         i.nextEntry();
/*      */       } 
/* 1513 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1517 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public LongComparator comparator() {
/* 1521 */       return Long2BooleanRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Long2BooleanSortedMap headMap(long to) {
/* 1525 */       if (this.top)
/* 1526 */         return new Submap(this.from, this.bottom, to, false); 
/* 1527 */       return (Long2BooleanRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Long2BooleanSortedMap tailMap(long from) {
/* 1531 */       if (this.bottom)
/* 1532 */         return new Submap(from, false, this.to, this.top); 
/* 1533 */       return (Long2BooleanRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Long2BooleanSortedMap subMap(long from, long to) {
/* 1537 */       if (this.top && this.bottom)
/* 1538 */         return new Submap(from, false, to, false); 
/* 1539 */       if (!this.top)
/* 1540 */         to = (Long2BooleanRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1541 */       if (!this.bottom)
/* 1542 */         from = (Long2BooleanRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1543 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1544 */         return this; 
/* 1545 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Long2BooleanRBTreeMap.Entry firstEntry() {
/*      */       Long2BooleanRBTreeMap.Entry e;
/* 1554 */       if (Long2BooleanRBTreeMap.this.tree == null) {
/* 1555 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1559 */       if (this.bottom) {
/* 1560 */         e = Long2BooleanRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1562 */         e = Long2BooleanRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1564 */         if (Long2BooleanRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1565 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1569 */       if (e == null || (!this.top && Long2BooleanRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1570 */         return null; 
/* 1571 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Long2BooleanRBTreeMap.Entry lastEntry() {
/*      */       Long2BooleanRBTreeMap.Entry e;
/* 1580 */       if (Long2BooleanRBTreeMap.this.tree == null) {
/* 1581 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1585 */       if (this.top) {
/* 1586 */         e = Long2BooleanRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1588 */         e = Long2BooleanRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1590 */         if (Long2BooleanRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1591 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1595 */       if (e == null || (!this.bottom && Long2BooleanRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1596 */         return null; 
/* 1597 */       return e;
/*      */     }
/*      */     
/*      */     public long firstLongKey() {
/* 1601 */       Long2BooleanRBTreeMap.Entry e = firstEntry();
/* 1602 */       if (e == null)
/* 1603 */         throw new NoSuchElementException(); 
/* 1604 */       return e.key;
/*      */     }
/*      */     
/*      */     public long lastLongKey() {
/* 1608 */       Long2BooleanRBTreeMap.Entry e = lastEntry();
/* 1609 */       if (e == null)
/* 1610 */         throw new NoSuchElementException(); 
/* 1611 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Long2BooleanRBTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1624 */         this.next = Long2BooleanRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(long k) {
/* 1627 */         this();
/* 1628 */         if (this.next != null)
/* 1629 */           if (!Long2BooleanRBTreeMap.Submap.this.bottom && Long2BooleanRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1630 */             this.prev = null;
/* 1631 */           } else if (!Long2BooleanRBTreeMap.Submap.this.top && Long2BooleanRBTreeMap.this.compare(k, (this.prev = Long2BooleanRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1632 */             this.next = null;
/*      */           } else {
/* 1634 */             this.next = Long2BooleanRBTreeMap.this.locateKey(k);
/* 1635 */             if (Long2BooleanRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1636 */               this.prev = this.next;
/* 1637 */               this.next = this.next.next();
/*      */             } else {
/* 1639 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1645 */         this.prev = this.prev.prev();
/* 1646 */         if (!Long2BooleanRBTreeMap.Submap.this.bottom && this.prev != null && Long2BooleanRBTreeMap.this.compare(this.prev.key, Long2BooleanRBTreeMap.Submap.this.from) < 0)
/* 1647 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1651 */         this.next = this.next.next();
/* 1652 */         if (!Long2BooleanRBTreeMap.Submap.this.top && this.next != null && Long2BooleanRBTreeMap.this.compare(this.next.key, Long2BooleanRBTreeMap.Submap.this.to) >= 0)
/* 1653 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Long2BooleanMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(long k) {
/* 1660 */         super(k);
/*      */       }
/*      */       
/*      */       public Long2BooleanMap.Entry next() {
/* 1664 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Long2BooleanMap.Entry previous() {
/* 1668 */         return previousEntry();
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
/* 1686 */         super(from);
/*      */       }
/*      */       
/*      */       public long nextLong() {
/* 1690 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public long previousLong() {
/* 1694 */         return (previousEntry()).key;
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
/* 1710 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public boolean previousBoolean() {
/* 1714 */         return (previousEntry()).value;
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
/*      */   public Long2BooleanRBTreeMap clone() {
/*      */     Long2BooleanRBTreeMap c;
/*      */     try {
/* 1733 */       c = (Long2BooleanRBTreeMap)super.clone();
/* 1734 */     } catch (CloneNotSupportedException cantHappen) {
/* 1735 */       throw new InternalError();
/*      */     } 
/* 1737 */     c.keys = null;
/* 1738 */     c.values = null;
/* 1739 */     c.entries = null;
/* 1740 */     c.allocatePaths();
/* 1741 */     if (this.count != 0) {
/*      */       
/* 1743 */       Entry rp = new Entry(), rq = new Entry();
/* 1744 */       Entry p = rp;
/* 1745 */       rp.left(this.tree);
/* 1746 */       Entry q = rq;
/* 1747 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1749 */         if (!p.pred()) {
/* 1750 */           Entry e = p.left.clone();
/* 1751 */           e.pred(q.left);
/* 1752 */           e.succ(q);
/* 1753 */           q.left(e);
/* 1754 */           p = p.left;
/* 1755 */           q = q.left;
/*      */         } else {
/* 1757 */           while (p.succ()) {
/* 1758 */             p = p.right;
/* 1759 */             if (p == null) {
/* 1760 */               q.right = null;
/* 1761 */               c.tree = rq.left;
/* 1762 */               c.firstEntry = c.tree;
/* 1763 */               while (c.firstEntry.left != null)
/* 1764 */                 c.firstEntry = c.firstEntry.left; 
/* 1765 */               c.lastEntry = c.tree;
/* 1766 */               while (c.lastEntry.right != null)
/* 1767 */                 c.lastEntry = c.lastEntry.right; 
/* 1768 */               return c;
/*      */             } 
/* 1770 */             q = q.right;
/*      */           } 
/* 1772 */           p = p.right;
/* 1773 */           q = q.right;
/*      */         } 
/* 1775 */         if (!p.succ()) {
/* 1776 */           Entry e = p.right.clone();
/* 1777 */           e.succ(q.right);
/* 1778 */           e.pred(q);
/* 1779 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1783 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1786 */     int n = this.count;
/* 1787 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1789 */     s.defaultWriteObject();
/* 1790 */     while (n-- != 0) {
/* 1791 */       Entry e = i.nextEntry();
/* 1792 */       s.writeLong(e.key);
/* 1793 */       s.writeBoolean(e.value);
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
/* 1814 */     if (n == 1) {
/* 1815 */       Entry entry = new Entry(s.readLong(), s.readBoolean());
/* 1816 */       entry.pred(pred);
/* 1817 */       entry.succ(succ);
/* 1818 */       entry.black(true);
/* 1819 */       return entry;
/*      */     } 
/* 1821 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1826 */       Entry entry = new Entry(s.readLong(), s.readBoolean());
/* 1827 */       entry.black(true);
/* 1828 */       entry.right(new Entry(s.readLong(), s.readBoolean()));
/* 1829 */       entry.right.pred(entry);
/* 1830 */       entry.pred(pred);
/* 1831 */       entry.right.succ(succ);
/* 1832 */       return entry;
/*      */     } 
/*      */     
/* 1835 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1836 */     Entry top = new Entry();
/* 1837 */     top.left(readTree(s, leftN, pred, top));
/* 1838 */     top.key = s.readLong();
/* 1839 */     top.value = s.readBoolean();
/* 1840 */     top.black(true);
/* 1841 */     top.right(readTree(s, rightN, top, succ));
/* 1842 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1843 */       top.right.black(false); 
/* 1844 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1847 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1852 */     setActualComparator();
/* 1853 */     allocatePaths();
/* 1854 */     if (this.count != 0) {
/* 1855 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1857 */       Entry e = this.tree;
/* 1858 */       while (e.left() != null)
/* 1859 */         e = e.left(); 
/* 1860 */       this.firstEntry = e;
/* 1861 */       e = this.tree;
/* 1862 */       while (e.right() != null)
/* 1863 */         e = e.right(); 
/* 1864 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2BooleanRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */