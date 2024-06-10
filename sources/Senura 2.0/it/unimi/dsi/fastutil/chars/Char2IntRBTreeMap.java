/*      */ package it.unimi.dsi.fastutil.chars;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntListIterator;
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
/*      */ public class Char2IntRBTreeMap
/*      */   extends AbstractChar2IntSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Char2IntMap.Entry> entries;
/*      */   protected transient CharSortedSet keys;
/*      */   protected transient IntCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Character> storedComparator;
/*      */   protected transient CharComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public Char2IntRBTreeMap() {
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
/*   91 */     this.actualComparator = CharComparators.asCharComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2IntRBTreeMap(Comparator<? super Character> c) {
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
/*      */   public Char2IntRBTreeMap(Map<? extends Character, ? extends Integer> m) {
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
/*      */   public Char2IntRBTreeMap(SortedMap<Character, Integer> m) {
/*  122 */     this(m.comparator());
/*  123 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2IntRBTreeMap(Char2IntMap m) {
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
/*      */   public Char2IntRBTreeMap(Char2IntSortedMap m) {
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
/*      */   public Char2IntRBTreeMap(char[] k, int[] v, Comparator<? super Character> c) {
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
/*      */   public Char2IntRBTreeMap(char[] k, int[] v) {
/*  178 */     this(k, v, (Comparator<? super Character>)null);
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
/*      */   final int compare(char k1, char k2) {
/*  206 */     return (this.actualComparator == null) ? Character.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   final Entry findKey(char k) {
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
/*      */   final Entry locateKey(char k) {
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
/*      */   public int addTo(char k, int incr) {
/*  270 */     Entry e = add(k);
/*  271 */     int oldValue = e.value;
/*  272 */     e.value += incr;
/*  273 */     return oldValue;
/*      */   }
/*      */   
/*      */   public int put(char k, int v) {
/*  277 */     Entry e = add(k);
/*  278 */     int oldValue = e.value;
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
/*      */   private Entry add(char k) {
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
/*      */   public int remove(char k) {
/*  441 */     this.modified = false;
/*  442 */     if (this.tree == null)
/*  443 */       return this.defRetValue; 
/*  444 */     Entry p = this.tree;
/*      */     
/*  446 */     int i = 0;
/*  447 */     char kk = k;
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
/*      */   public boolean containsValue(int v) {
/*  687 */     ValueIterator i = new ValueIterator();
/*      */     
/*  689 */     int j = this.count;
/*  690 */     while (j-- != 0) {
/*  691 */       int ev = i.nextInt();
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
/*      */     extends AbstractChar2IntMap.BasicEntry
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
/*  732 */       super(false, 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(char k, int v) {
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
/*      */     public int setValue(int value) {
/*  888 */       int oldValue = this.value;
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
/*  911 */       Map.Entry<Character, Integer> e = (Map.Entry<Character, Integer>)o;
/*  912 */       return (this.key == ((Character)e.getKey()).charValue() && this.value == ((Integer)e.getValue()).intValue());
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
/*      */   public boolean containsKey(char k) {
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
/*      */   public int get(char k) {
/*  954 */     Entry e = findKey(k);
/*  955 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */   
/*      */   public char firstCharKey() {
/*  959 */     if (this.tree == null)
/*  960 */       throw new NoSuchElementException(); 
/*  961 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public char lastCharKey() {
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
/*      */     Char2IntRBTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Char2IntRBTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Char2IntRBTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  998 */     int index = 0;
/*      */     TreeIterator() {
/* 1000 */       this.next = Char2IntRBTreeMap.this.firstEntry;
/*      */     }
/*      */     TreeIterator(char k) {
/* 1003 */       if ((this.next = Char2IntRBTreeMap.this.locateKey(k)) != null)
/* 1004 */         if (Char2IntRBTreeMap.this.compare(this.next.key, k) <= 0) {
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
/*      */     Char2IntRBTreeMap.Entry nextEntry() {
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
/*      */     Char2IntRBTreeMap.Entry previousEntry() {
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
/* 1057 */       Char2IntRBTreeMap.this.remove(this.curr.key);
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
/*      */     implements ObjectListIterator<Char2IntMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */     
/*      */     EntryIterator(char k) {
/* 1083 */       super(k);
/*      */     }
/*      */     
/*      */     public Char2IntMap.Entry next() {
/* 1087 */       return nextEntry();
/*      */     }
/*      */     
/*      */     public Char2IntMap.Entry previous() {
/* 1091 */       return previousEntry();
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectSortedSet<Char2IntMap.Entry> char2IntEntrySet() {
/* 1096 */     if (this.entries == null)
/* 1097 */       this.entries = (ObjectSortedSet<Char2IntMap.Entry>)new AbstractObjectSortedSet<Char2IntMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Char2IntMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Char2IntMap.Entry> comparator() {
/* 1102 */             return this.comparator;
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Char2IntMap.Entry> iterator() {
/* 1106 */             return (ObjectBidirectionalIterator<Char2IntMap.Entry>)new Char2IntRBTreeMap.EntryIterator();
/*      */           }
/*      */           
/*      */           public ObjectBidirectionalIterator<Char2IntMap.Entry> iterator(Char2IntMap.Entry from) {
/* 1110 */             return (ObjectBidirectionalIterator<Char2IntMap.Entry>)new Char2IntRBTreeMap.EntryIterator(from.getCharKey());
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1115 */             if (!(o instanceof Map.Entry))
/* 1116 */               return false; 
/* 1117 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1118 */             if (e.getKey() == null || !(e.getKey() instanceof Character))
/* 1119 */               return false; 
/* 1120 */             if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 1121 */               return false; 
/* 1122 */             Char2IntRBTreeMap.Entry f = Char2IntRBTreeMap.this.findKey(((Character)e.getKey()).charValue());
/* 1123 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1128 */             if (!(o instanceof Map.Entry))
/* 1129 */               return false; 
/* 1130 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1131 */             if (e.getKey() == null || !(e.getKey() instanceof Character))
/* 1132 */               return false; 
/* 1133 */             if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 1134 */               return false; 
/* 1135 */             Char2IntRBTreeMap.Entry f = Char2IntRBTreeMap.this.findKey(((Character)e.getKey()).charValue());
/* 1136 */             if (f == null || f.getIntValue() != ((Integer)e.getValue()).intValue())
/* 1137 */               return false; 
/* 1138 */             Char2IntRBTreeMap.this.remove(f.key);
/* 1139 */             return true;
/*      */           }
/*      */           
/*      */           public int size() {
/* 1143 */             return Char2IntRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1147 */             Char2IntRBTreeMap.this.clear();
/*      */           }
/*      */           
/*      */           public Char2IntMap.Entry first() {
/* 1151 */             return Char2IntRBTreeMap.this.firstEntry;
/*      */           }
/*      */           
/*      */           public Char2IntMap.Entry last() {
/* 1155 */             return Char2IntRBTreeMap.this.lastEntry;
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Char2IntMap.Entry> subSet(Char2IntMap.Entry from, Char2IntMap.Entry to) {
/* 1159 */             return Char2IntRBTreeMap.this.subMap(from.getCharKey(), to.getCharKey()).char2IntEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Char2IntMap.Entry> headSet(Char2IntMap.Entry to) {
/* 1163 */             return Char2IntRBTreeMap.this.headMap(to.getCharKey()).char2IntEntrySet();
/*      */           }
/*      */           
/*      */           public ObjectSortedSet<Char2IntMap.Entry> tailSet(Char2IntMap.Entry from) {
/* 1167 */             return Char2IntRBTreeMap.this.tailMap(from.getCharKey()).char2IntEntrySet();
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
/*      */     implements CharListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(char k) {
/* 1186 */       super(k);
/*      */     }
/*      */     
/*      */     public char nextChar() {
/* 1190 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public char previousChar() {
/* 1194 */       return (previousEntry()).key;
/*      */     } }
/*      */   
/*      */   private class KeySet extends AbstractChar2IntSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public CharBidirectionalIterator iterator() {
/* 1201 */       return new Char2IntRBTreeMap.KeyIterator();
/*      */     }
/*      */     
/*      */     public CharBidirectionalIterator iterator(char from) {
/* 1205 */       return new Char2IntRBTreeMap.KeyIterator(from);
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
/*      */   public CharSortedSet keySet() {
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
/*      */     implements IntListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1237 */       return (nextEntry()).value;
/*      */     }
/*      */     
/*      */     public int previousInt() {
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
/*      */   public IntCollection values() {
/* 1256 */     if (this.values == null)
/* 1257 */       this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1260 */             return (IntIterator)new Char2IntRBTreeMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public boolean contains(int k) {
/* 1264 */             return Char2IntRBTreeMap.this.containsValue(k);
/*      */           }
/*      */           
/*      */           public int size() {
/* 1268 */             return Char2IntRBTreeMap.this.count;
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1272 */             Char2IntRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1275 */     return this.values;
/*      */   }
/*      */   
/*      */   public CharComparator comparator() {
/* 1279 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public Char2IntSortedMap headMap(char to) {
/* 1283 */     return new Submap(false, true, to, false);
/*      */   }
/*      */   
/*      */   public Char2IntSortedMap tailMap(char from) {
/* 1287 */     return new Submap(from, false, false, true);
/*      */   }
/*      */   
/*      */   public Char2IntSortedMap subMap(char from, char to) {
/* 1291 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractChar2IntSortedMap
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     char from;
/*      */ 
/*      */ 
/*      */     
/*      */     char to;
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
/*      */     protected transient ObjectSortedSet<Char2IntMap.Entry> entries;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient CharSortedSet keys;
/*      */ 
/*      */ 
/*      */     
/*      */     protected transient IntCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(char from, boolean bottom, char to, boolean top) {
/* 1335 */       if (!bottom && !top && Char2IntRBTreeMap.this.compare(from, to) > 0)
/* 1336 */         throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1337 */       this.from = from;
/* 1338 */       this.bottom = bottom;
/* 1339 */       this.to = to;
/* 1340 */       this.top = top;
/* 1341 */       this.defRetValue = Char2IntRBTreeMap.this.defRetValue;
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
/*      */     final boolean in(char k) {
/* 1359 */       return ((this.bottom || Char2IntRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Char2IntRBTreeMap.this
/* 1360 */         .compare(k, this.to) < 0));
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Char2IntMap.Entry> char2IntEntrySet() {
/* 1364 */       if (this.entries == null)
/* 1365 */         this.entries = (ObjectSortedSet<Char2IntMap.Entry>)new AbstractObjectSortedSet<Char2IntMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Char2IntMap.Entry> iterator() {
/* 1368 */               return (ObjectBidirectionalIterator<Char2IntMap.Entry>)new Char2IntRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */             
/*      */             public ObjectBidirectionalIterator<Char2IntMap.Entry> iterator(Char2IntMap.Entry from) {
/* 1372 */               return (ObjectBidirectionalIterator<Char2IntMap.Entry>)new Char2IntRBTreeMap.Submap.SubmapEntryIterator(from.getCharKey());
/*      */             }
/*      */             
/*      */             public Comparator<? super Char2IntMap.Entry> comparator() {
/* 1376 */               return Char2IntRBTreeMap.this.char2IntEntrySet().comparator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1381 */               if (!(o instanceof Map.Entry))
/* 1382 */                 return false; 
/* 1383 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1384 */               if (e.getKey() == null || !(e.getKey() instanceof Character))
/* 1385 */                 return false; 
/* 1386 */               if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 1387 */                 return false; 
/* 1388 */               Char2IntRBTreeMap.Entry f = Char2IntRBTreeMap.this.findKey(((Character)e.getKey()).charValue());
/* 1389 */               return (f != null && Char2IntRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1394 */               if (!(o instanceof Map.Entry))
/* 1395 */                 return false; 
/* 1396 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1397 */               if (e.getKey() == null || !(e.getKey() instanceof Character))
/* 1398 */                 return false; 
/* 1399 */               if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 1400 */                 return false; 
/* 1401 */               Char2IntRBTreeMap.Entry f = Char2IntRBTreeMap.this.findKey(((Character)e.getKey()).charValue());
/* 1402 */               if (f != null && Char2IntRBTreeMap.Submap.this.in(f.key))
/* 1403 */                 Char2IntRBTreeMap.Submap.this.remove(f.key); 
/* 1404 */               return (f != null);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1408 */               int c = 0;
/* 1409 */               for (ObjectBidirectionalIterator<Char2IntMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); objectBidirectionalIterator.next())
/* 1410 */                 c++; 
/* 1411 */               return c;
/*      */             }
/*      */             
/*      */             public boolean isEmpty() {
/* 1415 */               return !(new Char2IntRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1419 */               Char2IntRBTreeMap.Submap.this.clear();
/*      */             }
/*      */             
/*      */             public Char2IntMap.Entry first() {
/* 1423 */               return Char2IntRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */             
/*      */             public Char2IntMap.Entry last() {
/* 1427 */               return Char2IntRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Char2IntMap.Entry> subSet(Char2IntMap.Entry from, Char2IntMap.Entry to) {
/* 1431 */               return Char2IntRBTreeMap.Submap.this.subMap(from.getCharKey(), to.getCharKey()).char2IntEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Char2IntMap.Entry> headSet(Char2IntMap.Entry to) {
/* 1435 */               return Char2IntRBTreeMap.Submap.this.headMap(to.getCharKey()).char2IntEntrySet();
/*      */             }
/*      */             
/*      */             public ObjectSortedSet<Char2IntMap.Entry> tailSet(Char2IntMap.Entry from) {
/* 1439 */               return Char2IntRBTreeMap.Submap.this.tailMap(from.getCharKey()).char2IntEntrySet();
/*      */             }
/*      */           }; 
/* 1442 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractChar2IntSortedMap.KeySet {
/*      */       public CharBidirectionalIterator iterator() {
/* 1447 */         return new Char2IntRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */       private KeySet() {}
/*      */       public CharBidirectionalIterator iterator(char from) {
/* 1451 */         return new Char2IntRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       }
/*      */     }
/*      */     
/*      */     public CharSortedSet keySet() {
/* 1456 */       if (this.keys == null)
/* 1457 */         this.keys = new KeySet(); 
/* 1458 */       return this.keys;
/*      */     }
/*      */     
/*      */     public IntCollection values() {
/* 1462 */       if (this.values == null)
/* 1463 */         this.values = (IntCollection)new AbstractIntCollection()
/*      */           {
/*      */             public IntIterator iterator() {
/* 1466 */               return (IntIterator)new Char2IntRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */             
/*      */             public boolean contains(int k) {
/* 1470 */               return Char2IntRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */             
/*      */             public int size() {
/* 1474 */               return Char2IntRBTreeMap.Submap.this.size();
/*      */             }
/*      */             
/*      */             public void clear() {
/* 1478 */               Char2IntRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1481 */       return this.values;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsKey(char k) {
/* 1486 */       return (in(k) && Char2IntRBTreeMap.this.containsKey(k));
/*      */     }
/*      */     
/*      */     public boolean containsValue(int v) {
/* 1490 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1492 */       while (i.hasNext()) {
/* 1493 */         int ev = (i.nextEntry()).value;
/* 1494 */         if (ev == v)
/* 1495 */           return true; 
/*      */       } 
/* 1497 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int get(char k) {
/* 1503 */       char kk = k; Char2IntRBTreeMap.Entry e;
/* 1504 */       return (in(kk) && (e = Char2IntRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int put(char k, int v) {
/* 1508 */       Char2IntRBTreeMap.this.modified = false;
/* 1509 */       if (!in(k))
/* 1510 */         throw new IllegalArgumentException("Key (" + k + ") out of range [" + (
/* 1511 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1512 */       int oldValue = Char2IntRBTreeMap.this.put(k, v);
/* 1513 */       return Char2IntRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public int remove(char k) {
/* 1518 */       Char2IntRBTreeMap.this.modified = false;
/* 1519 */       if (!in(k))
/* 1520 */         return this.defRetValue; 
/* 1521 */       int oldValue = Char2IntRBTreeMap.this.remove(k);
/* 1522 */       return Char2IntRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */     
/*      */     public int size() {
/* 1526 */       SubmapIterator i = new SubmapIterator();
/* 1527 */       int n = 0;
/* 1528 */       while (i.hasNext()) {
/* 1529 */         n++;
/* 1530 */         i.nextEntry();
/*      */       } 
/* 1532 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1536 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public CharComparator comparator() {
/* 1540 */       return Char2IntRBTreeMap.this.actualComparator;
/*      */     }
/*      */     
/*      */     public Char2IntSortedMap headMap(char to) {
/* 1544 */       if (this.top)
/* 1545 */         return new Submap(this.from, this.bottom, to, false); 
/* 1546 */       return (Char2IntRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public Char2IntSortedMap tailMap(char from) {
/* 1550 */       if (this.bottom)
/* 1551 */         return new Submap(from, false, this.to, this.top); 
/* 1552 */       return (Char2IntRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public Char2IntSortedMap subMap(char from, char to) {
/* 1556 */       if (this.top && this.bottom)
/* 1557 */         return new Submap(from, false, to, false); 
/* 1558 */       if (!this.top)
/* 1559 */         to = (Char2IntRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1560 */       if (!this.bottom)
/* 1561 */         from = (Char2IntRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1562 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1563 */         return this; 
/* 1564 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Char2IntRBTreeMap.Entry firstEntry() {
/*      */       Char2IntRBTreeMap.Entry e;
/* 1573 */       if (Char2IntRBTreeMap.this.tree == null) {
/* 1574 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1578 */       if (this.bottom) {
/* 1579 */         e = Char2IntRBTreeMap.this.firstEntry;
/*      */       } else {
/* 1581 */         e = Char2IntRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1583 */         if (Char2IntRBTreeMap.this.compare(e.key, this.from) < 0) {
/* 1584 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1588 */       if (e == null || (!this.top && Char2IntRBTreeMap.this.compare(e.key, this.to) >= 0))
/* 1589 */         return null; 
/* 1590 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Char2IntRBTreeMap.Entry lastEntry() {
/*      */       Char2IntRBTreeMap.Entry e;
/* 1599 */       if (Char2IntRBTreeMap.this.tree == null) {
/* 1600 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1604 */       if (this.top) {
/* 1605 */         e = Char2IntRBTreeMap.this.lastEntry;
/*      */       } else {
/* 1607 */         e = Char2IntRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1609 */         if (Char2IntRBTreeMap.this.compare(e.key, this.to) >= 0) {
/* 1610 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1614 */       if (e == null || (!this.bottom && Char2IntRBTreeMap.this.compare(e.key, this.from) < 0))
/* 1615 */         return null; 
/* 1616 */       return e;
/*      */     }
/*      */     
/*      */     public char firstCharKey() {
/* 1620 */       Char2IntRBTreeMap.Entry e = firstEntry();
/* 1621 */       if (e == null)
/* 1622 */         throw new NoSuchElementException(); 
/* 1623 */       return e.key;
/*      */     }
/*      */     
/*      */     public char lastCharKey() {
/* 1627 */       Char2IntRBTreeMap.Entry e = lastEntry();
/* 1628 */       if (e == null)
/* 1629 */         throw new NoSuchElementException(); 
/* 1630 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Char2IntRBTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1643 */         this.next = Char2IntRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       SubmapIterator(char k) {
/* 1646 */         this();
/* 1647 */         if (this.next != null)
/* 1648 */           if (!Char2IntRBTreeMap.Submap.this.bottom && Char2IntRBTreeMap.this.compare(k, this.next.key) < 0) {
/* 1649 */             this.prev = null;
/* 1650 */           } else if (!Char2IntRBTreeMap.Submap.this.top && Char2IntRBTreeMap.this.compare(k, (this.prev = Char2IntRBTreeMap.Submap.this.lastEntry()).key) >= 0) {
/* 1651 */             this.next = null;
/*      */           } else {
/* 1653 */             this.next = Char2IntRBTreeMap.this.locateKey(k);
/* 1654 */             if (Char2IntRBTreeMap.this.compare(this.next.key, k) <= 0) {
/* 1655 */               this.prev = this.next;
/* 1656 */               this.next = this.next.next();
/*      */             } else {
/* 1658 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1664 */         this.prev = this.prev.prev();
/* 1665 */         if (!Char2IntRBTreeMap.Submap.this.bottom && this.prev != null && Char2IntRBTreeMap.this.compare(this.prev.key, Char2IntRBTreeMap.Submap.this.from) < 0)
/* 1666 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1670 */         this.next = this.next.next();
/* 1671 */         if (!Char2IntRBTreeMap.Submap.this.top && this.next != null && Char2IntRBTreeMap.this.compare(this.next.key, Char2IntRBTreeMap.Submap.this.to) >= 0)
/* 1672 */           this.next = null; 
/*      */       } }
/*      */     
/*      */     private class SubmapEntryIterator extends SubmapIterator implements ObjectListIterator<Char2IntMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(char k) {
/* 1679 */         super(k);
/*      */       }
/*      */       
/*      */       public Char2IntMap.Entry next() {
/* 1683 */         return nextEntry();
/*      */       }
/*      */       
/*      */       public Char2IntMap.Entry previous() {
/* 1687 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements CharListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(char from) {
/* 1705 */         super(from);
/*      */       }
/*      */       
/*      */       public char nextChar() {
/* 1709 */         return (nextEntry()).key;
/*      */       }
/*      */       
/*      */       public char previousChar() {
/* 1713 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements IntListIterator
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public int nextInt() {
/* 1729 */         return (nextEntry()).value;
/*      */       }
/*      */       
/*      */       public int previousInt() {
/* 1733 */         return (previousEntry()).value;
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
/*      */   public Char2IntRBTreeMap clone() {
/*      */     Char2IntRBTreeMap c;
/*      */     try {
/* 1752 */       c = (Char2IntRBTreeMap)super.clone();
/* 1753 */     } catch (CloneNotSupportedException cantHappen) {
/* 1754 */       throw new InternalError();
/*      */     } 
/* 1756 */     c.keys = null;
/* 1757 */     c.values = null;
/* 1758 */     c.entries = null;
/* 1759 */     c.allocatePaths();
/* 1760 */     if (this.count != 0) {
/*      */       
/* 1762 */       Entry rp = new Entry(), rq = new Entry();
/* 1763 */       Entry p = rp;
/* 1764 */       rp.left(this.tree);
/* 1765 */       Entry q = rq;
/* 1766 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1768 */         if (!p.pred()) {
/* 1769 */           Entry e = p.left.clone();
/* 1770 */           e.pred(q.left);
/* 1771 */           e.succ(q);
/* 1772 */           q.left(e);
/* 1773 */           p = p.left;
/* 1774 */           q = q.left;
/*      */         } else {
/* 1776 */           while (p.succ()) {
/* 1777 */             p = p.right;
/* 1778 */             if (p == null) {
/* 1779 */               q.right = null;
/* 1780 */               c.tree = rq.left;
/* 1781 */               c.firstEntry = c.tree;
/* 1782 */               while (c.firstEntry.left != null)
/* 1783 */                 c.firstEntry = c.firstEntry.left; 
/* 1784 */               c.lastEntry = c.tree;
/* 1785 */               while (c.lastEntry.right != null)
/* 1786 */                 c.lastEntry = c.lastEntry.right; 
/* 1787 */               return c;
/*      */             } 
/* 1789 */             q = q.right;
/*      */           } 
/* 1791 */           p = p.right;
/* 1792 */           q = q.right;
/*      */         } 
/* 1794 */         if (!p.succ()) {
/* 1795 */           Entry e = p.right.clone();
/* 1796 */           e.succ(q.right);
/* 1797 */           e.pred(q);
/* 1798 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1802 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1805 */     int n = this.count;
/* 1806 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1808 */     s.defaultWriteObject();
/* 1809 */     while (n-- != 0) {
/* 1810 */       Entry e = i.nextEntry();
/* 1811 */       s.writeChar(e.key);
/* 1812 */       s.writeInt(e.value);
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
/* 1833 */     if (n == 1) {
/* 1834 */       Entry entry = new Entry(s.readChar(), s.readInt());
/* 1835 */       entry.pred(pred);
/* 1836 */       entry.succ(succ);
/* 1837 */       entry.black(true);
/* 1838 */       return entry;
/*      */     } 
/* 1840 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1845 */       Entry entry = new Entry(s.readChar(), s.readInt());
/* 1846 */       entry.black(true);
/* 1847 */       entry.right(new Entry(s.readChar(), s.readInt()));
/* 1848 */       entry.right.pred(entry);
/* 1849 */       entry.pred(pred);
/* 1850 */       entry.right.succ(succ);
/* 1851 */       return entry;
/*      */     } 
/*      */     
/* 1854 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1855 */     Entry top = new Entry();
/* 1856 */     top.left(readTree(s, leftN, pred, top));
/* 1857 */     top.key = s.readChar();
/* 1858 */     top.value = s.readInt();
/* 1859 */     top.black(true);
/* 1860 */     top.right(readTree(s, rightN, top, succ));
/* 1861 */     if (n + 2 == (n + 2 & -(n + 2)))
/* 1862 */       top.right.black(false); 
/* 1863 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1866 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1871 */     setActualComparator();
/* 1872 */     allocatePaths();
/* 1873 */     if (this.count != 0) {
/* 1874 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1876 */       Entry e = this.tree;
/* 1877 */       while (e.left() != null)
/* 1878 */         e = e.left(); 
/* 1879 */       this.firstEntry = e;
/* 1880 */       e = this.tree;
/* 1881 */       while (e.right() != null)
/* 1882 */         e = e.right(); 
/* 1883 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2IntRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */