/*      */ package it.unimi.dsi.fastutil.floats;
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
/*      */ public class Float2BooleanRBTreeMap
/*      */   extends AbstractFloat2BooleanSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Float2BooleanMap.Entry> entries;
/*      */   protected transient FloatSortedSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Float> storedComparator;
/*      */   protected transient FloatComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public Float2BooleanRBTreeMap() {
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
/*   91 */     this.actualComparator = FloatComparators.asFloatComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2BooleanRBTreeMap(Comparator<? super Float> c) {
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
/*      */   public Float2BooleanRBTreeMap(Map<? extends Float, ? extends Boolean> m) {
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
/*      */   public Float2BooleanRBTreeMap(SortedMap<Float, Boolean> m) {
/*  122 */     this(m.comparator());
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2BooleanRBTreeMap(Float2BooleanMap m) {
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
/*      */   public Float2BooleanRBTreeMap(Float2BooleanSortedMap m) {
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
/*      */   public Float2BooleanRBTreeMap(float[] k, boolean[] v, Comparator<? super Float> c) {
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
/*      */   public Float2BooleanRBTreeMap(float[] k, boolean[] v) {
/*  178 */     this(k, v, (Comparator<? super Float>)null);
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
/*  206 */     return (this.actualComparator == null) ? Float.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry locateKey(float k) {
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
/*      */   public boolean put(float k, boolean v) {
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
/*      */   private Entry add(float k) {
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
/*      */   public boolean remove(float k) {
/*  419 */     this.modified = false;
/*  420 */     if (this.tree == null)
/*  421 */       return this.defRetValue; 
/*  422 */     Entry p = this.tree;
/*      */     
/*  424 */     int i = 0;
/*  425 */     float kk = k;
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
/*      */     extends AbstractFloat2BooleanMap.BasicEntry
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
/*  710 */       super(0.0F, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(float k, boolean v) {
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
/*  889 */       Map.Entry<Float, Boolean> e = (Map.Entry<Float, Boolean>)o;
/*  890 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && this.value == ((Boolean)e
/*  891 */         .getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  895 */       return HashCommon.float2int(this.key) ^ (this.value ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  899 */       return this.key + "=>" + this.value;
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
/*  920 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public int size() {
/*  924 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  928 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean get(float k) {
/*  933 */     Entry e = findKey(k);
/*  934 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public float firstFloatKey() {
/*  938 */     if (this.tree == null)
/*  939 */       throw new NoSuchElementException(); 
/*  940 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public float lastFloatKey() {
/*  944 */     if (this.tree == null)
/*  945 */       throw new NoSuchElementException(); 
/*  946 */     return this.lastEntry.key;
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
/*      */     Float2BooleanRBTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Float2BooleanRBTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Float2BooleanRBTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  977 */     int index = 0;
/*      */     TreeIterator() {
/*  979 */       this.next = Float2BooleanRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(float k) {
/*  982 */       if ((this.next = Float2BooleanRBTreeMap.this.locateKey(k)) != null)
/*  983 */         if (Float2BooleanRBTreeMap.this.compare(this.next.key, k) <= 0) {
/*  984 */           this.prev = this.next;
/*  985 */           this.next = this.next.next();
/*      */         } else {
/*  987 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     public boolean hasNext() {
/*  991 */       return (this.next != null);
/*      */     }
/*      */     public boolean hasPrevious() {
/*  994 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/*  997 */       this.next = this.next.next();
/*      */     }
/*      */     Float2BooleanRBTreeMap.Entry nextEntry() {
/* 1000 */       if (!hasNext())
/* 1001 */         throw new NoSuchElementException(); 
/* 1002 */       this.curr = this.prev = this.next;
/* 1003 */       this.index++;
/* 1004 */       updateNext();
/* 1005 */       return this.curr;
/*      */     }
/*      */     void updatePrevious() {
/* 1008 */       this.prev = this.prev.prev();
/*      */     }
/*      */     Float2BooleanRBTreeMap.Entry previousEntry() {
/* 1011 */       if (!hasPrevious())
/* 1012 */         throw new NoSuchElementException(); 
/* 1013 */       this.curr = this.next = this.prev;
/* 1014 */       this.index--;
/* 1015 */       updatePrevious();
/* 1016 */       return this.curr;
/*      */     }
/*      */     public int nextIndex() {
/* 1019 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1022 */       return this.index - 1;
/*      */     }
/*      */     public void remove() {
/* 1025 */       if (this.curr == null) {
/* 1026 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1031 */       if (this.curr == this.prev)
/* 1032 */         this.index--; 
/* 1033 */       this.next = this.prev = this.curr;
/* 1034 */       updatePrevious();
/* 1035 */       updateNext();
/* 1036 */       Float2BooleanRBTreeMap.this.remove(this.curr.key);
/* 1037 */       this.curr = null;
/*      */     }
/*      */     public int skip(int n) {
/* 1040 */       int i = n;
/* 1041 */       while (i-- != 0 && hasNext())
/* 1042 */         nextEntry(); 
/* 1043 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1046 */       int i = n;
/* 1047 */       while (i-- != 0 && hasPrevious())
/* 1048 */         previousEntry(); 
/* 1049 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Float2BooleanMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(float k) {
/* 1062 */       super(k);
/*      */     }
/*      */     
/*      */     public Float2BooleanMap.Entry next() {
/* 1066 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Float2BooleanMap.Entry previous() {
/* 1070 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Float2BooleanMap.Entry> float2BooleanEntrySet() {
/* 1075 */     if (this.entries == null)
/* 1076 */       this.entries = (ObjectSortedSet<Float2BooleanMap.Entry>)new AbstractObjectSortedSet<Float2BooleanMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Float2BooleanMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Float2BooleanMap.Entry> comparator() {
/* 1081 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Float2BooleanMap.Entry> iterator() {
/* 1085 */             return (ObjectBidirectionalIterator<Float2BooleanMap.Entry>)new Float2BooleanRBTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Float2BooleanMap.Entry> iterator(Float2BooleanMap.Entry from) {
/* 1089 */             return (ObjectBidirectionalIterator<Float2BooleanMap.Entry>)new Float2BooleanRBTreeMap.EntryIterator(from.getFloatKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1094 */             if (!(o instanceof Map.Entry))
/* 1095 */               return false; 
/* 1096 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1097 */             if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1098 */               return false; 
/* 1099 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1100 */               return false; 
/* 1101 */             Float2BooleanRBTreeMap.Entry f = Float2BooleanRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1102 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1107 */             if (!(o instanceof Map.Entry))
/* 1108 */               return false; 
/* 1109 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1110 */             if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1111 */               return false; 
/* 1112 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1113 */               return false; 
/* 1114 */             Float2BooleanRBTreeMap.Entry f = Float2BooleanRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1115 */             if (f == null || f.getBooleanValue() != ((Boolean)e.getValue()).booleanValue())
/* 1116 */               return false; 
/* 1117 */             Float2BooleanRBTreeMap.this.remove(f.key);
/* 1118 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1122 */             return Float2BooleanRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1126 */             Float2BooleanRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Float2BooleanMap.Entry first() {
/* 1130 */             return Float2BooleanRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Float2BooleanMap.Entry last() {
/* 1134 */             return Float2BooleanRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Float2BooleanMap.Entry> subSet(Float2BooleanMap.Entry from, Float2BooleanMap.Entry to) {
/* 1139 */             return Float2BooleanRBTreeMap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Float2BooleanMap.Entry> headSet(Float2BooleanMap.Entry to) {
/* 1143 */             return Float2BooleanRBTreeMap.this.headMap(to.getFloatKey()).float2BooleanEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Float2BooleanMap.Entry> tailSet(Float2BooleanMap.Entry from) {
/* 1147 */             return Float2BooleanRBTreeMap.this.tailMap(from.getFloatKey()).float2BooleanEntrySet();
/*      */           }
/*      */         }; 
/* 1150 */     return this.entries;
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
/* 1166 */       super(k);
/*      */     }
/*      */     
/*      */     public float nextFloat() {
/* 1170 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public float previousFloat() {
/* 1174 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractFloat2BooleanSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public FloatBidirectionalIterator iterator() {
/* 1181 */       return new Float2BooleanRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public FloatBidirectionalIterator iterator(float from) {
/* 1185 */       return new Float2BooleanRBTreeMap.KeyIterator(from);
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
/* 1200 */     if (this.keys == null)
/* 1201 */       this.keys = new KeySet(); 
/* 1202 */     return this.keys;
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
/* 1217 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public boolean previousBoolean() {
/* 1221 */       return (previousEntry()).value;
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
/* 1236 */     if (this.values == null)
/* 1237 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1240 */             return (BooleanIterator)new Float2BooleanRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(boolean k) {
/* 1244 */             return Float2BooleanRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1248 */             return Float2BooleanRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1252 */             Float2BooleanRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1255 */     return this.values;
/*      */   }
/*      */   
/*      */   public FloatComparator comparator() {
/* 1259 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Float2BooleanSortedMap headMap(float to) {
/* 1263 */     return new Submap(0.0F, true, to, false);
/*      */   }
/*      */   
/*      */   public Float2BooleanSortedMap tailMap(float from) {
/* 1267 */     return new Submap(from, false, 0.0F, true);
/*      */   }
/*      */   
/*      */   public Float2BooleanSortedMap subMap(float from, float to) {
/* 1271 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractFloat2BooleanSortedMap
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
/*      */     protected transient ObjectSortedSet<Float2BooleanMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient FloatSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(float from, boolean bottom, float to, boolean top) {
/* 1315 */       if (!bottom && !top && Float2BooleanRBTreeMap.this.compare(from, to) > 0)
/* 1316 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1317 */       this.from = from;
/* 1318 */       this.bottom = bottom;
/* 1319 */       this.to = to;
/* 1320 */       this.top = top;
/* 1321 */       this.defRetValue = Float2BooleanRBTreeMap.this.defRetValue;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1325 */       SubmapIterator i = new SubmapIterator();
/* 1326 */       while (i.hasNext()) {
/* 1327 */         i.nextEntry();
/* 1328 */         i.remove();
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
/* 1339 */       return ((this.bottom || Float2BooleanRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Float2BooleanRBTreeMap.this
/* 1340 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Float2BooleanMap.Entry> float2BooleanEntrySet() {
/* 1344 */       if (this.entries == null)
/* 1345 */         this.entries = (ObjectSortedSet<Float2BooleanMap.Entry>)new AbstractObjectSortedSet<Float2BooleanMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Float2BooleanMap.Entry> iterator() {
/* 1348 */               return (ObjectBidirectionalIterator<Float2BooleanMap.Entry>)new Float2BooleanRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Float2BooleanMap.Entry> iterator(Float2BooleanMap.Entry from) {
/* 1353 */               return (ObjectBidirectionalIterator<Float2BooleanMap.Entry>)new Float2BooleanRBTreeMap.Submap.SubmapEntryIterator(from.getFloatKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Float2BooleanMap.Entry> comparator() {
/* 1357 */               return Float2BooleanRBTreeMap.this.float2BooleanEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1362 */               if (!(o instanceof Map.Entry))
/* 1363 */                 return false; 
/* 1364 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1365 */               if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1366 */                 return false; 
/* 1367 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1368 */                 return false; 
/* 1369 */               Float2BooleanRBTreeMap.Entry f = Float2BooleanRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1370 */               return (f != null && Float2BooleanRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1375 */               if (!(o instanceof Map.Entry))
/* 1376 */                 return false; 
/* 1377 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1378 */               if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1379 */                 return false; 
/* 1380 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1381 */                 return false; 
/* 1382 */               Float2BooleanRBTreeMap.Entry f = Float2BooleanRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1383 */               if (f != null && Float2BooleanRBTreeMap.Submap.this.in(f.key))
/* 1384 */                 Float2BooleanRBTreeMap.Submap.this.remove(f.key); 
/* 1385 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1389 */               int c = 0;
/* 1390 */               for (ObjectBidirectionalIterator<Float2BooleanMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1391 */                 c++; 
/* 1392 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1396 */               return !(new Float2BooleanRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1400 */               Float2BooleanRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Float2BooleanMap.Entry first() {
/* 1404 */               return Float2BooleanRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Float2BooleanMap.Entry last() {
/* 1408 */               return Float2BooleanRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Float2BooleanMap.Entry> subSet(Float2BooleanMap.Entry from, Float2BooleanMap.Entry to) {
/* 1413 */               return Float2BooleanRBTreeMap.Submap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Float2BooleanMap.Entry> headSet(Float2BooleanMap.Entry to) {
/* 1417 */               return Float2BooleanRBTreeMap.Submap.this.headMap(to.getFloatKey()).float2BooleanEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Float2BooleanMap.Entry> tailSet(Float2BooleanMap.Entry from) {
/* 1421 */               return Float2BooleanRBTreeMap.Submap.this.tailMap(from.getFloatKey()).float2BooleanEntrySet();
/*      */             }
/*      */           }; 
/* 1424 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractFloat2BooleanSortedMap.KeySet {
/*      */       public FloatBidirectionalIterator iterator() {
/* 1429 */         return new Float2BooleanRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public FloatBidirectionalIterator iterator(float from) {
/* 1433 */         return new Float2BooleanRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public FloatSortedSet keySet() {
/* 1438 */       if (this.keys == null)
/* 1439 */         this.keys = new KeySet(); 
/* 1440 */       return this.keys;
/*      */     }
/*      */     
/*      */     public BooleanCollection values() {
/* 1444 */       if (this.values == null)
/* 1445 */         this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */           {
/*      */             public BooleanIterator iterator() {
/* 1448 */               return (BooleanIterator)new Float2BooleanRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(boolean k) {
/* 1452 */               return Float2BooleanRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1456 */               return Float2BooleanRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1460 */               Float2BooleanRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1463 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(float k) {
/* 1468 */       return (in(k) && Float2BooleanRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(boolean v) {
/* 1472 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1474 */       while (i.hasNext()) {
/* 1475 */         boolean ev = (i.nextEntry()).value;
/* 1476 */         if (ev == v)
/* 1477 */           return true; 
/*      */       } 
/* 1479 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean get(float k) {
/* 1485 */       float kk = k; Float2BooleanRBTreeMap.Entry e;
/* 1486 */       return (in(kk) && (e = Float2BooleanRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public boolean put(float k, boolean v) {
/* 1490 */       Float2BooleanRBTreeMap.this.modified = false;
/* 1491 */       if (!in(k))
/* 1492 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1493 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1494 */       boolean oldValue = Float2BooleanRBTreeMap.this.put(k, v);
/* 1495 */       return Float2BooleanRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(float k) {
/* 1500 */       Float2BooleanRBTreeMap.this.modified = false;
/* 1501 */       if (!in(k))
/* 1502 */         return this.defRetValue; 
/* 1503 */       boolean oldValue = Float2BooleanRBTreeMap.this.remove(k);
/* 1504 */       return Float2BooleanRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1508 */       SubmapIterator i = new SubmapIterator();
/* 1509 */       int n = 0;
/* 1510 */       while (i.hasNext()) {
/* 1511 */         n++;
/* 1512 */         i.nextEntry();
/*      */       } 
/* 1514 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1518 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public FloatComparator comparator() {
/* 1522 */       return Float2BooleanRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Float2BooleanSortedMap headMap(float to) {
/* 1526 */       if (this.top)
/* 1527 */         return new Submap(this.from, this.bottom, to, false); 
/* 1528 */       return (Float2BooleanRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Float2BooleanSortedMap tailMap(float from) {
/* 1532 */       if (this.bottom)
/* 1533 */         return new Submap(from, false, this.to, this.top); 
/* 1534 */       return (Float2BooleanRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Float2BooleanSortedMap subMap(float from, float to) {
/* 1538 */       if (this.top && this.bottom)
/* 1539 */         return new Submap(from, false, to, false); 
/* 1540 */       if (!this.top)
/* 1541 */         to = (Float2BooleanRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1542 */       if (!this.bottom)
/* 1543 */         from = (Float2BooleanRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1544 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1545 */         return this; 
/* 1546 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Float2BooleanRBTreeMap.Entry firstEntry() {
/*      */       Float2BooleanRBTreeMap.Entry e;
/* 1555 */       if (Float2BooleanRBTreeMap.this.tree == null) {
/* 1556 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1560 */       if (this.bottom) {
/* 1561 */         e = Float2BooleanRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1563 */         e = Float2BooleanRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1565 */         if (Float2BooleanRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1566 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1570 */       if (e == null || (!this.top && Float2BooleanRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1571 */         return null; 
/* 1572 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Float2BooleanRBTreeMap.Entry lastEntry() {
/*      */       Float2BooleanRBTreeMap.Entry e;
/* 1581 */       if (Float2BooleanRBTreeMap.this.tree == null) {
/* 1582 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1586 */       if (this.top) {
/* 1587 */         e = Float2BooleanRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1589 */         e = Float2BooleanRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1591 */         if (Float2BooleanRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1592 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1596 */       if (e == null || (!this.bottom && Float2BooleanRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1597 */         return null; 
/* 1598 */       return e;
/*      */     }
/*      */     
/*      */     public float firstFloatKey() {
/* 1602 */       Float2BooleanRBTreeMap.Entry e = firstEntry();
/* 1603 */       if (e == null)
/* 1604 */         throw new NoSuchElementException(); 
/* 1605 */       return e.key;
/*      */     }
/*      */     
/*      */     public float lastFloatKey() {
/* 1609 */       Float2BooleanRBTreeMap.Entry e = lastEntry();
/* 1610 */       if (e == null)
/* 1611 */         throw new NoSuchElementException(); 
/* 1612 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Float2BooleanRBTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1625 */         this.next = Float2BooleanRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(float k) {
/* 1628 */         this();
/* 1629 */         if (this.next != null)
/* 1630 */           if (!Float2BooleanRBTreeMap.Submap.this.bottom && Float2BooleanRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1631 */             this.prev = null;
/* 1632 */           } else if (!Float2BooleanRBTreeMap.Submap.this.top && Float2BooleanRBTreeMap.this.compare(k, (this.prev = Float2BooleanRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1633 */             this.next = null;
/*      */           } else {
/* 1635 */             this.next = Float2BooleanRBTreeMap.this.locateKey(k);
/* 1636 */             if (Float2BooleanRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1637 */               this.prev = this.next;
/* 1638 */               this.next = this.next.next();
/*      */             } else {
/* 1640 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1646 */         this.prev = this.prev.prev();
/* 1647 */         if (!Float2BooleanRBTreeMap.Submap.this.bottom && this.prev != null && Float2BooleanRBTreeMap.this.compare(this.prev.key, Float2BooleanRBTreeMap.Submap.this.from) < 0)
/* 1648 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1652 */         this.next = this.next.next();
/* 1653 */         if (!Float2BooleanRBTreeMap.Submap.this.top && this.next != null && Float2BooleanRBTreeMap.this.compare(this.next.key, Float2BooleanRBTreeMap.Submap.this.to) >= 0)
/* 1654 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Float2BooleanMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(float k) {
/* 1661 */         super(k);
/*      */       }
/*      */       
/*      */       public Float2BooleanMap.Entry next() {
/* 1665 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Float2BooleanMap.Entry previous() {
/* 1669 */         return previousEntry();
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
/* 1687 */         super(from);
/*      */       }
/*      */       
/*      */       public float nextFloat() {
/* 1691 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public float previousFloat() {
/* 1695 */         return (previousEntry()).key;
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
/* 1711 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public boolean previousBoolean() {
/* 1715 */         return (previousEntry()).value;
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
/*      */   public Float2BooleanRBTreeMap clone() {
/*      */     Float2BooleanRBTreeMap c;
/*      */     try {
/* 1734 */       c = (Float2BooleanRBTreeMap)super.clone();
/* 1735 */     } catch (CloneNotSupportedException cantHappen) {
/* 1736 */       throw new InternalError();
/*      */     } 
/* 1738 */     c.keys = null;
/* 1739 */     c.values = null;
/* 1740 */     c.entries = null;
/* 1741 */     c.allocatePaths();
/* 1742 */     if (this.count != 0) {
/*      */       
/* 1744 */       Entry rp = new Entry(), rq = new Entry();
/* 1745 */       Entry p = rp;
/* 1746 */       rp.left(this.tree);
/* 1747 */       Entry q = rq;
/* 1748 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1750 */         if (!p.pred()) {
/* 1751 */           Entry e = p.left.clone();
/* 1752 */           e.pred(q.left);
/* 1753 */           e.succ(q);
/* 1754 */           q.left(e);
/* 1755 */           p = p.left;
/* 1756 */           q = q.left;
/*      */         } else {
/* 1758 */           while (p.succ()) {
/* 1759 */             p = p.right;
/* 1760 */             if (p == null) {
/* 1761 */               q.right = null;
/* 1762 */               c.tree = rq.left;
/* 1763 */               c.firstEntry = c.tree;
/* 1764 */               while (c.firstEntry.left != null)
/* 1765 */                 c.firstEntry = c.firstEntry.left; 
/* 1766 */               c.lastEntry = c.tree;
/* 1767 */               while (c.lastEntry.right != null)
/* 1768 */                 c.lastEntry = c.lastEntry.right; 
/* 1769 */               return c;
/*      */             } 
/* 1771 */             q = q.right;
/*      */           } 
/* 1773 */           p = p.right;
/* 1774 */           q = q.right;
/*      */         } 
/* 1776 */         if (!p.succ()) {
/* 1777 */           Entry e = p.right.clone();
/* 1778 */           e.succ(q.right);
/* 1779 */           e.pred(q);
/* 1780 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1784 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1787 */     int n = this.count;
/* 1788 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1790 */     s.defaultWriteObject();
/* 1791 */     while (n-- != 0) {
/* 1792 */       Entry e = i.nextEntry();
/* 1793 */       s.writeFloat(e.key);
/* 1794 */       s.writeBoolean(e.value);
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
/* 1815 */     if (n == 1) {
/* 1816 */       Entry entry = new Entry(s.readFloat(), s.readBoolean());
/* 1817 */       entry.pred(pred);
/* 1818 */       entry.succ(succ);
/* 1819 */       entry.black(true);
/* 1820 */       return entry;
/*      */     } 
/* 1822 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1827 */       Entry entry = new Entry(s.readFloat(), s.readBoolean());
/* 1828 */       entry.black(true);
/* 1829 */       entry.right(new Entry(s.readFloat(), s.readBoolean()));
/* 1830 */       entry.right.pred(entry);
/* 1831 */       entry.pred(pred);
/* 1832 */       entry.right.succ(succ);
/* 1833 */       return entry;
/*      */     } 
/*      */     
/* 1836 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1837 */     Entry top = new Entry();
/* 1838 */     top.left(readTree(s, leftN, pred, top));
/* 1839 */     top.key = s.readFloat();
/* 1840 */     top.value = s.readBoolean();
/* 1841 */     top.black(true);
/* 1842 */     top.right(readTree(s, rightN, top, succ));
/* 1843 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1844 */       top.right.black(false); 
/* 1845 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1848 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1853 */     setActualComparator();
/* 1854 */     allocatePaths();
/* 1855 */     if (this.count != 0) {
/* 1856 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1858 */       Entry e = this.tree;
/* 1859 */       while (e.left() != null)
/* 1860 */         e = e.left(); 
/* 1861 */       this.firstEntry = e;
/* 1862 */       e = this.tree;
/* 1863 */       while (e.right() != null)
/* 1864 */         e = e.right(); 
/* 1865 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2BooleanRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */