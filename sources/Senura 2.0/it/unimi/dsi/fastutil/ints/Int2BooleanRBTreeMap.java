/*      */ package it.unimi.dsi.fastutil.ints;
/*      */ 
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
/*      */ 
/*      */ public class Int2BooleanRBTreeMap
/*      */   extends AbstractInt2BooleanSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Int2BooleanMap.Entry> entries;
/*      */   protected transient IntSortedSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Integer> storedComparator;
/*      */   protected transient IntComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public Int2BooleanRBTreeMap() {
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
/*   91 */     this.actualComparator = IntComparators.asIntComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2BooleanRBTreeMap(Comparator<? super Integer> c) {
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
/*      */   public Int2BooleanRBTreeMap(Map<? extends Integer, ? extends Boolean> m) {
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
/*      */   public Int2BooleanRBTreeMap(SortedMap<Integer, Boolean> m) {
/*  122 */     this(m.comparator());
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2BooleanRBTreeMap(Int2BooleanMap m) {
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
/*      */   public Int2BooleanRBTreeMap(Int2BooleanSortedMap m) {
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
/*      */   public Int2BooleanRBTreeMap(int[] k, boolean[] v, Comparator<? super Integer> c) {
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
/*      */   public Int2BooleanRBTreeMap(int[] k, boolean[] v) {
/*  178 */     this(k, v, (Comparator<? super Integer>)null);
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
/*  206 */     return (this.actualComparator == null) ? Integer.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry findKey(int k) {
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
/*      */   final Entry locateKey(int k) {
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
/*      */   public boolean put(int k, boolean v) {
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
/*      */   private Entry add(int k) {
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
/*      */   public boolean remove(int k) {
/*  419 */     this.modified = false;
/*  420 */     if (this.tree == null)
/*  421 */       return this.defRetValue; 
/*  422 */     Entry p = this.tree;
/*      */     
/*  424 */     int i = 0;
/*  425 */     int kk = k;
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
/*      */     extends AbstractInt2BooleanMap.BasicEntry
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
/*  710 */       super(0, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(int k, boolean v) {
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
/*  889 */       Map.Entry<Integer, Boolean> e = (Map.Entry<Integer, Boolean>)o;
/*  890 */       return (this.key == ((Integer)e.getKey()).intValue() && this.value == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  894 */       return this.key ^ (this.value ? 1231 : 1237);
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
/*      */   public boolean containsKey(int k) {
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
/*      */   public boolean get(int k) {
/*  932 */     Entry e = findKey(k);
/*  933 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public int firstIntKey() {
/*  937 */     if (this.tree == null)
/*  938 */       throw new NoSuchElementException(); 
/*  939 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public int lastIntKey() {
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
/*      */     Int2BooleanRBTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Int2BooleanRBTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Int2BooleanRBTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  976 */     int index = 0;
/*      */     TreeIterator() {
/*  978 */       this.next = Int2BooleanRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(int k) {
/*  981 */       if ((this.next = Int2BooleanRBTreeMap.this.locateKey(k)) != null)
/*  982 */         if (Int2BooleanRBTreeMap.this.compare(this.next.key, k) <= 0) {
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
/*      */     Int2BooleanRBTreeMap.Entry nextEntry() {
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
/*      */     Int2BooleanRBTreeMap.Entry previousEntry() {
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
/* 1035 */       Int2BooleanRBTreeMap.this.remove(this.curr.key);
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
/*      */     implements ObjectListIterator<Int2BooleanMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(int k) {
/* 1061 */       super(k);
/*      */     }
/*      */     
/*      */     public Int2BooleanMap.Entry next() {
/* 1065 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Int2BooleanMap.Entry previous() {
/* 1069 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Int2BooleanMap.Entry> int2BooleanEntrySet() {
/* 1074 */     if (this.entries == null)
/* 1075 */       this.entries = (ObjectSortedSet<Int2BooleanMap.Entry>)new AbstractObjectSortedSet<Int2BooleanMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Int2BooleanMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Int2BooleanMap.Entry> comparator() {
/* 1080 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Int2BooleanMap.Entry> iterator() {
/* 1084 */             return (ObjectBidirectionalIterator<Int2BooleanMap.Entry>)new Int2BooleanRBTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Int2BooleanMap.Entry> iterator(Int2BooleanMap.Entry from) {
/* 1088 */             return (ObjectBidirectionalIterator<Int2BooleanMap.Entry>)new Int2BooleanRBTreeMap.EntryIterator(from.getIntKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1093 */             if (!(o instanceof Map.Entry))
/* 1094 */               return false; 
/* 1095 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1096 */             if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1097 */               return false; 
/* 1098 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1099 */               return false; 
/* 1100 */             Int2BooleanRBTreeMap.Entry f = Int2BooleanRBTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1101 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1106 */             if (!(o instanceof Map.Entry))
/* 1107 */               return false; 
/* 1108 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1109 */             if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1110 */               return false; 
/* 1111 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1112 */               return false; 
/* 1113 */             Int2BooleanRBTreeMap.Entry f = Int2BooleanRBTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1114 */             if (f == null || f.getBooleanValue() != ((Boolean)e.getValue()).booleanValue())
/* 1115 */               return false; 
/* 1116 */             Int2BooleanRBTreeMap.this.remove(f.key);
/* 1117 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1121 */             return Int2BooleanRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1125 */             Int2BooleanRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Int2BooleanMap.Entry first() {
/* 1129 */             return Int2BooleanRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Int2BooleanMap.Entry last() {
/* 1133 */             return Int2BooleanRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Int2BooleanMap.Entry> subSet(Int2BooleanMap.Entry from, Int2BooleanMap.Entry to) {
/* 1138 */             return Int2BooleanRBTreeMap.this.subMap(from.getIntKey(), to.getIntKey()).int2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Int2BooleanMap.Entry> headSet(Int2BooleanMap.Entry to) {
/* 1142 */             return Int2BooleanRBTreeMap.this.headMap(to.getIntKey()).int2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Int2BooleanMap.Entry> tailSet(Int2BooleanMap.Entry from) {
/* 1146 */             return Int2BooleanRBTreeMap.this.tailMap(from.getIntKey()).int2BooleanEntrySet();
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
/*      */     implements IntListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(int k) {
/* 1165 */       super(k);
/*      */     }
/*      */     
/*      */     public int nextInt() {
/* 1169 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public int previousInt() {
/* 1173 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractInt2BooleanSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public IntBidirectionalIterator iterator() {
/* 1180 */       return new Int2BooleanRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public IntBidirectionalIterator iterator(int from) {
/* 1184 */       return new Int2BooleanRBTreeMap.KeyIterator(from);
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
/* 1239 */             return (BooleanIterator)new Int2BooleanRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(boolean k) {
/* 1243 */             return Int2BooleanRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1247 */             return Int2BooleanRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1251 */             Int2BooleanRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1254 */     return this.values;
/*      */   }
/*      */   
/*      */   public IntComparator comparator() {
/* 1258 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Int2BooleanSortedMap headMap(int to) {
/* 1262 */     return new Submap(0, true, to, false);
/*      */   }
/*      */   
/*      */   public Int2BooleanSortedMap tailMap(int from) {
/* 1266 */     return new Submap(from, false, 0, true);
/*      */   }
/*      */   
/*      */   public Int2BooleanSortedMap subMap(int from, int to) {
/* 1270 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractInt2BooleanSortedMap
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
/*      */     protected transient ObjectSortedSet<Int2BooleanMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient IntSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(int from, boolean bottom, int to, boolean top) {
/* 1314 */       if (!bottom && !top && Int2BooleanRBTreeMap.this.compare(from, to) > 0)
/* 1315 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1316 */       this.from = from;
/* 1317 */       this.bottom = bottom;
/* 1318 */       this.to = to;
/* 1319 */       this.top = top;
/* 1320 */       this.defRetValue = Int2BooleanRBTreeMap.this.defRetValue;
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
/*      */     final boolean in(int k) {
/* 1338 */       return ((this.bottom || Int2BooleanRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Int2BooleanRBTreeMap.this
/* 1339 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Int2BooleanMap.Entry> int2BooleanEntrySet() {
/* 1343 */       if (this.entries == null)
/* 1344 */         this.entries = (ObjectSortedSet<Int2BooleanMap.Entry>)new AbstractObjectSortedSet<Int2BooleanMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Int2BooleanMap.Entry> iterator() {
/* 1347 */               return (ObjectBidirectionalIterator<Int2BooleanMap.Entry>)new Int2BooleanRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */             
/*      */             public ObjectBidirectionalIterator<Int2BooleanMap.Entry> iterator(Int2BooleanMap.Entry from) {
/* 1351 */               return (ObjectBidirectionalIterator<Int2BooleanMap.Entry>)new Int2BooleanRBTreeMap.Submap.SubmapEntryIterator(from.getIntKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Int2BooleanMap.Entry> comparator() {
/* 1355 */               return Int2BooleanRBTreeMap.this.int2BooleanEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1360 */               if (!(o instanceof Map.Entry))
/* 1361 */                 return false; 
/* 1362 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1363 */               if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1364 */                 return false; 
/* 1365 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1366 */                 return false; 
/* 1367 */               Int2BooleanRBTreeMap.Entry f = Int2BooleanRBTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1368 */               return (f != null && Int2BooleanRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1373 */               if (!(o instanceof Map.Entry))
/* 1374 */                 return false; 
/* 1375 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1376 */               if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1377 */                 return false; 
/* 1378 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1379 */                 return false; 
/* 1380 */               Int2BooleanRBTreeMap.Entry f = Int2BooleanRBTreeMap.this.findKey(((Integer)e.getKey()).intValue());
/* 1381 */               if (f != null && Int2BooleanRBTreeMap.Submap.this.in(f.key))
/* 1382 */                 Int2BooleanRBTreeMap.Submap.this.remove(f.key); 
/* 1383 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1387 */               int c = 0;
/* 1388 */               for (ObjectBidirectionalIterator<Int2BooleanMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1389 */                 c++; 
/* 1390 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1394 */               return !(new Int2BooleanRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1398 */               Int2BooleanRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Int2BooleanMap.Entry first() {
/* 1402 */               return Int2BooleanRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Int2BooleanMap.Entry last() {
/* 1406 */               return Int2BooleanRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Int2BooleanMap.Entry> subSet(Int2BooleanMap.Entry from, Int2BooleanMap.Entry to) {
/* 1411 */               return Int2BooleanRBTreeMap.Submap.this.subMap(from.getIntKey(), to.getIntKey()).int2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Int2BooleanMap.Entry> headSet(Int2BooleanMap.Entry to) {
/* 1415 */               return Int2BooleanRBTreeMap.Submap.this.headMap(to.getIntKey()).int2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Int2BooleanMap.Entry> tailSet(Int2BooleanMap.Entry from) {
/* 1419 */               return Int2BooleanRBTreeMap.Submap.this.tailMap(from.getIntKey()).int2BooleanEntrySet();
/*      */             }
/*      */           }; 
/* 1422 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractInt2BooleanSortedMap.KeySet {
/*      */       public IntBidirectionalIterator iterator() {
/* 1427 */         return new Int2BooleanRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public IntBidirectionalIterator iterator(int from) {
/* 1431 */         return new Int2BooleanRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public IntSortedSet keySet() {
/* 1436 */       if (this.keys == null)
/* 1437 */         this.keys = new KeySet(); 
/* 1438 */       return this.keys;
/*      */     }
/*      */     
/*      */     public BooleanCollection values() {
/* 1442 */       if (this.values == null)
/* 1443 */         this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */           {
/*      */             public BooleanIterator iterator() {
/* 1446 */               return (BooleanIterator)new Int2BooleanRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(boolean k) {
/* 1450 */               return Int2BooleanRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1454 */               return Int2BooleanRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1458 */               Int2BooleanRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1461 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(int k) {
/* 1466 */       return (in(k) && Int2BooleanRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(boolean v) {
/* 1470 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1472 */       while (i.hasNext()) {
/* 1473 */         boolean ev = (i.nextEntry()).value;
/* 1474 */         if (ev == v)
/* 1475 */           return true; 
/*      */       } 
/* 1477 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean get(int k) {
/* 1483 */       int kk = k; Int2BooleanRBTreeMap.Entry e;
/* 1484 */       return (in(kk) && (e = Int2BooleanRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public boolean put(int k, boolean v) {
/* 1488 */       Int2BooleanRBTreeMap.this.modified = false;
/* 1489 */       if (!in(k))
/* 1490 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1491 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1492 */       boolean oldValue = Int2BooleanRBTreeMap.this.put(k, v);
/* 1493 */       return Int2BooleanRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(int k) {
/* 1498 */       Int2BooleanRBTreeMap.this.modified = false;
/* 1499 */       if (!in(k))
/* 1500 */         return this.defRetValue; 
/* 1501 */       boolean oldValue = Int2BooleanRBTreeMap.this.remove(k);
/* 1502 */       return Int2BooleanRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1506 */       SubmapIterator i = new SubmapIterator();
/* 1507 */       int n = 0;
/* 1508 */       while (i.hasNext()) {
/* 1509 */         n++;
/* 1510 */         i.nextEntry();
/*      */       } 
/* 1512 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1516 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public IntComparator comparator() {
/* 1520 */       return Int2BooleanRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Int2BooleanSortedMap headMap(int to) {
/* 1524 */       if (this.top)
/* 1525 */         return new Submap(this.from, this.bottom, to, false); 
/* 1526 */       return (Int2BooleanRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Int2BooleanSortedMap tailMap(int from) {
/* 1530 */       if (this.bottom)
/* 1531 */         return new Submap(from, false, this.to, this.top); 
/* 1532 */       return (Int2BooleanRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Int2BooleanSortedMap subMap(int from, int to) {
/* 1536 */       if (this.top && this.bottom)
/* 1537 */         return new Submap(from, false, to, false); 
/* 1538 */       if (!this.top)
/* 1539 */         to = (Int2BooleanRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1540 */       if (!this.bottom)
/* 1541 */         from = (Int2BooleanRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1542 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1543 */         return this; 
/* 1544 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Int2BooleanRBTreeMap.Entry firstEntry() {
/*      */       Int2BooleanRBTreeMap.Entry e;
/* 1553 */       if (Int2BooleanRBTreeMap.this.tree == null) {
/* 1554 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1558 */       if (this.bottom) {
/* 1559 */         e = Int2BooleanRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1561 */         e = Int2BooleanRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1563 */         if (Int2BooleanRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1564 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1568 */       if (e == null || (!this.top && Int2BooleanRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1569 */         return null; 
/* 1570 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Int2BooleanRBTreeMap.Entry lastEntry() {
/*      */       Int2BooleanRBTreeMap.Entry e;
/* 1579 */       if (Int2BooleanRBTreeMap.this.tree == null) {
/* 1580 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1584 */       if (this.top) {
/* 1585 */         e = Int2BooleanRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1587 */         e = Int2BooleanRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1589 */         if (Int2BooleanRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1590 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1594 */       if (e == null || (!this.bottom && Int2BooleanRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1595 */         return null; 
/* 1596 */       return e;
/*      */     }
/*      */     
/*      */     public int firstIntKey() {
/* 1600 */       Int2BooleanRBTreeMap.Entry e = firstEntry();
/* 1601 */       if (e == null)
/* 1602 */         throw new NoSuchElementException(); 
/* 1603 */       return e.key;
/*      */     }
/*      */     
/*      */     public int lastIntKey() {
/* 1607 */       Int2BooleanRBTreeMap.Entry e = lastEntry();
/* 1608 */       if (e == null)
/* 1609 */         throw new NoSuchElementException(); 
/* 1610 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Int2BooleanRBTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1623 */         this.next = Int2BooleanRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(int k) {
/* 1626 */         this();
/* 1627 */         if (this.next != null)
/* 1628 */           if (!Int2BooleanRBTreeMap.Submap.this.bottom && Int2BooleanRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1629 */             this.prev = null;
/* 1630 */           } else if (!Int2BooleanRBTreeMap.Submap.this.top && Int2BooleanRBTreeMap.this.compare(k, (this.prev = Int2BooleanRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1631 */             this.next = null;
/*      */           } else {
/* 1633 */             this.next = Int2BooleanRBTreeMap.this.locateKey(k);
/* 1634 */             if (Int2BooleanRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1635 */               this.prev = this.next;
/* 1636 */               this.next = this.next.next();
/*      */             } else {
/* 1638 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1644 */         this.prev = this.prev.prev();
/* 1645 */         if (!Int2BooleanRBTreeMap.Submap.this.bottom && this.prev != null && Int2BooleanRBTreeMap.this.compare(this.prev.key, Int2BooleanRBTreeMap.Submap.this.from) < 0)
/* 1646 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1650 */         this.next = this.next.next();
/* 1651 */         if (!Int2BooleanRBTreeMap.Submap.this.top && this.next != null && Int2BooleanRBTreeMap.this.compare(this.next.key, Int2BooleanRBTreeMap.Submap.this.to) >= 0)
/* 1652 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Int2BooleanMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(int k) {
/* 1659 */         super(k);
/*      */       }
/*      */       
/*      */       public Int2BooleanMap.Entry next() {
/* 1663 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Int2BooleanMap.Entry previous() {
/* 1667 */         return previousEntry();
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
/* 1685 */         super(from);
/*      */       }
/*      */       
/*      */       public int nextInt() {
/* 1689 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public int previousInt() {
/* 1693 */         return (previousEntry()).key;
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
/* 1709 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public boolean previousBoolean() {
/* 1713 */         return (previousEntry()).value;
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
/*      */   public Int2BooleanRBTreeMap clone() {
/*      */     Int2BooleanRBTreeMap c;
/*      */     try {
/* 1732 */       c = (Int2BooleanRBTreeMap)super.clone();
/* 1733 */     } catch (CloneNotSupportedException cantHappen) {
/* 1734 */       throw new InternalError();
/*      */     } 
/* 1736 */     c.keys = null;
/* 1737 */     c.values = null;
/* 1738 */     c.entries = null;
/* 1739 */     c.allocatePaths();
/* 1740 */     if (this.count != 0) {
/*      */       
/* 1742 */       Entry rp = new Entry(), rq = new Entry();
/* 1743 */       Entry p = rp;
/* 1744 */       rp.left(this.tree);
/* 1745 */       Entry q = rq;
/* 1746 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1748 */         if (!p.pred()) {
/* 1749 */           Entry e = p.left.clone();
/* 1750 */           e.pred(q.left);
/* 1751 */           e.succ(q);
/* 1752 */           q.left(e);
/* 1753 */           p = p.left;
/* 1754 */           q = q.left;
/*      */         } else {
/* 1756 */           while (p.succ()) {
/* 1757 */             p = p.right;
/* 1758 */             if (p == null) {
/* 1759 */               q.right = null;
/* 1760 */               c.tree = rq.left;
/* 1761 */               c.firstEntry = c.tree;
/* 1762 */               while (c.firstEntry.left != null)
/* 1763 */                 c.firstEntry = c.firstEntry.left; 
/* 1764 */               c.lastEntry = c.tree;
/* 1765 */               while (c.lastEntry.right != null)
/* 1766 */                 c.lastEntry = c.lastEntry.right; 
/* 1767 */               return c;
/*      */             } 
/* 1769 */             q = q.right;
/*      */           } 
/* 1771 */           p = p.right;
/* 1772 */           q = q.right;
/*      */         } 
/* 1774 */         if (!p.succ()) {
/* 1775 */           Entry e = p.right.clone();
/* 1776 */           e.succ(q.right);
/* 1777 */           e.pred(q);
/* 1778 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1782 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1785 */     int n = this.count;
/* 1786 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1788 */     s.defaultWriteObject();
/* 1789 */     while (n-- != 0) {
/* 1790 */       Entry e = i.nextEntry();
/* 1791 */       s.writeInt(e.key);
/* 1792 */       s.writeBoolean(e.value);
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
/* 1813 */     if (n == 1) {
/* 1814 */       Entry entry = new Entry(s.readInt(), s.readBoolean());
/* 1815 */       entry.pred(pred);
/* 1816 */       entry.succ(succ);
/* 1817 */       entry.black(true);
/* 1818 */       return entry;
/*      */     } 
/* 1820 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1825 */       Entry entry = new Entry(s.readInt(), s.readBoolean());
/* 1826 */       entry.black(true);
/* 1827 */       entry.right(new Entry(s.readInt(), s.readBoolean()));
/* 1828 */       entry.right.pred(entry);
/* 1829 */       entry.pred(pred);
/* 1830 */       entry.right.succ(succ);
/* 1831 */       return entry;
/*      */     } 
/*      */     
/* 1834 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1835 */     Entry top = new Entry();
/* 1836 */     top.left(readTree(s, leftN, pred, top));
/* 1837 */     top.key = s.readInt();
/* 1838 */     top.value = s.readBoolean();
/* 1839 */     top.black(true);
/* 1840 */     top.right(readTree(s, rightN, top, succ));
/* 1841 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1842 */       top.right.black(false); 
/* 1843 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1846 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1851 */     setActualComparator();
/* 1852 */     allocatePaths();
/* 1853 */     if (this.count != 0) {
/* 1854 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1856 */       Entry e = this.tree;
/* 1857 */       while (e.left() != null)
/* 1858 */         e = e.left(); 
/* 1859 */       this.firstEntry = e;
/* 1860 */       e = this.tree;
/* 1861 */       while (e.right() != null)
/* 1862 */         e = e.right(); 
/* 1863 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2BooleanRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */