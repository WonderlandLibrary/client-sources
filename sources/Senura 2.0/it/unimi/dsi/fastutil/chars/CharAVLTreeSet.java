/*      */ package it.unimi.dsi.fastutil.chars;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
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
/*      */ public class CharAVLTreeSet
/*      */   extends AbstractCharSortedSet
/*      */   implements Serializable, Cloneable, CharSortedSet
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected Comparator<? super Character> storedComparator;
/*      */   protected transient CharComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353130L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public CharAVLTreeSet() {
/*   50 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   56 */     this.tree = null;
/*   57 */     this.count = 0;
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
/*   69 */     this.actualComparator = CharComparators.asCharComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharAVLTreeSet(Comparator<? super Character> c) {
/*   78 */     this();
/*   79 */     this.storedComparator = c;
/*   80 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharAVLTreeSet(Collection<? extends Character> c) {
/*   89 */     this();
/*   90 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharAVLTreeSet(SortedSet<Character> s) {
/*  100 */     this(s.comparator());
/*  101 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharAVLTreeSet(CharCollection c) {
/*  110 */     this();
/*  111 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharAVLTreeSet(CharSortedSet s) {
/*  121 */     this(s.comparator());
/*  122 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharAVLTreeSet(CharIterator i) {
/*      */     allocatePaths();
/*  131 */     while (i.hasNext()) {
/*  132 */       add(i.nextChar());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharAVLTreeSet(Iterator<?> i) {
/*  142 */     this(CharIterators.asCharIterator(i));
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
/*      */   public CharAVLTreeSet(char[] a, int offset, int length, Comparator<? super Character> c) {
/*  158 */     this(c);
/*  159 */     CharArrays.ensureOffsetLength(a, offset, length);
/*  160 */     for (int i = 0; i < length; i++) {
/*  161 */       add(a[offset + i]);
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
/*      */   public CharAVLTreeSet(char[] a, int offset, int length) {
/*  174 */     this(a, offset, length, (Comparator<? super Character>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharAVLTreeSet(char[] a) {
/*  183 */     this();
/*  184 */     int i = a.length;
/*  185 */     while (i-- != 0) {
/*  186 */       add(a[i]);
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
/*      */   public CharAVLTreeSet(char[] a, Comparator<? super Character> c) {
/*  198 */     this(c);
/*  199 */     int i = a.length;
/*  200 */     while (i-- != 0) {
/*  201 */       add(a[i]);
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
/*      */   final int compare(char k1, char k2) {
/*  229 */     return (this.actualComparator == null) ? Character.compare(k1, k2) : this.actualComparator.compare(k1, k2);
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
/*      */   private Entry findKey(char k) {
/*  241 */     Entry e = this.tree;
/*      */     int cmp;
/*  243 */     while (e != null && (cmp = compare(k, e.key)) != 0)
/*  244 */       e = (cmp < 0) ? e.left() : e.right(); 
/*  245 */     return e;
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
/*  257 */     Entry e = this.tree, last = this.tree;
/*  258 */     int cmp = 0;
/*  259 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  260 */       last = e;
/*  261 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  263 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  271 */     this.dirPath = new boolean[48];
/*      */   }
/*      */   
/*      */   public boolean add(char k) {
/*  275 */     if (this.tree == null) {
/*  276 */       this.count++;
/*  277 */       this.tree = this.lastEntry = this.firstEntry = new Entry(k);
/*      */     } else {
/*  279 */       Entry p = this.tree, q = null, y = this.tree, z = null, e = null, w = null;
/*  280 */       int i = 0; while (true) {
/*      */         int cmp;
/*  282 */         if ((cmp = compare(k, p.key)) == 0)
/*  283 */           return false; 
/*  284 */         if (p.balance() != 0) {
/*  285 */           i = 0;
/*  286 */           z = q;
/*  287 */           y = p;
/*      */         } 
/*  289 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  290 */           if (p.succ()) {
/*  291 */             this.count++;
/*  292 */             e = new Entry(k);
/*  293 */             if (p.right == null)
/*  294 */               this.lastEntry = e; 
/*  295 */             e.left = p;
/*  296 */             e.right = p.right;
/*  297 */             p.right(e);
/*      */             break;
/*      */           } 
/*  300 */           q = p;
/*  301 */           p = p.right; continue;
/*      */         } 
/*  303 */         if (p.pred()) {
/*  304 */           this.count++;
/*  305 */           e = new Entry(k);
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
/*  327 */         Entry x = y.left;
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
/*  366 */         Entry x = y.right;
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
/*  405 */         return true;
/*  406 */       }  if (z == null) {
/*  407 */         this.tree = w;
/*      */       }
/*  409 */       else if (z.left == y) {
/*  410 */         z.left = w;
/*      */       } else {
/*  412 */         z.right = w;
/*      */       } 
/*      */     } 
/*  415 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry parent(Entry e) {
/*  425 */     if (e == this.tree) {
/*  426 */       return null;
/*      */     }
/*  428 */     Entry y = e, x = y;
/*      */     while (true) {
/*  430 */       if (y.succ()) {
/*  431 */         Entry p = y.right;
/*  432 */         if (p == null || p.left != e) {
/*  433 */           while (!x.pred())
/*  434 */             x = x.left; 
/*  435 */           p = x.left;
/*      */         } 
/*  437 */         return p;
/*  438 */       }  if (x.pred()) {
/*  439 */         Entry p = x.left;
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
/*      */   public boolean remove(char k) {
/*  454 */     if (this.tree == null) {
/*  455 */       return false;
/*      */     }
/*  457 */     Entry p = this.tree, q = null;
/*  458 */     boolean dir = false;
/*  459 */     char kk = k;
/*      */     int cmp;
/*  461 */     while ((cmp = compare(kk, p.key)) != 0) {
/*      */       
/*  463 */       if (dir = (cmp > 0)) {
/*  464 */         q = p;
/*  465 */         if ((p = p.right()) == null)
/*  466 */           return false;  continue;
/*      */       } 
/*  468 */       q = p;
/*  469 */       if ((p = p.left()) == null) {
/*  470 */         return false;
/*      */       }
/*      */     } 
/*  473 */     if (p.left == null)
/*  474 */       this.firstEntry = p.next(); 
/*  475 */     if (p.right == null)
/*  476 */       this.lastEntry = p.prev(); 
/*  477 */     if (p.succ())
/*  478 */     { if (p.pred())
/*  479 */       { if (q != null)
/*  480 */         { if (dir) {
/*  481 */             q.succ(p.right);
/*      */           } else {
/*  483 */             q.pred(p.left);
/*      */           }  }
/*  485 */         else { this.tree = dir ? p.right : p.left; }
/*      */          }
/*  487 */       else { (p.prev()).right = p.right;
/*  488 */         if (q != null)
/*  489 */         { if (dir) {
/*  490 */             q.right = p.left;
/*      */           } else {
/*  492 */             q.left = p.left;
/*      */           }  }
/*  494 */         else { this.tree = p.left; }
/*      */          }
/*      */        }
/*  497 */     else { Entry r = p.right;
/*  498 */       if (r.pred()) {
/*  499 */         r.left = p.left;
/*  500 */         r.pred(p.pred());
/*  501 */         if (!r.pred())
/*  502 */           (r.prev()).right = r; 
/*  503 */         if (q != null)
/*  504 */         { if (dir) {
/*  505 */             q.right = r;
/*      */           } else {
/*  507 */             q.left = r;
/*      */           }  }
/*  509 */         else { this.tree = r; }
/*  510 */          r.balance(p.balance());
/*  511 */         q = r;
/*  512 */         dir = true;
/*      */       } else {
/*      */         Entry s;
/*      */         while (true) {
/*  516 */           s = r.left;
/*  517 */           if (s.pred())
/*      */             break; 
/*  519 */           r = s;
/*      */         } 
/*  521 */         if (s.succ()) {
/*  522 */           r.pred(s);
/*      */         } else {
/*  524 */           r.left = s.right;
/*  525 */         }  s.left = p.left;
/*  526 */         if (!p.pred()) {
/*  527 */           (p.prev()).right = s;
/*  528 */           s.pred(false);
/*      */         } 
/*  530 */         s.right = p.right;
/*  531 */         s.succ(false);
/*  532 */         if (q != null)
/*  533 */         { if (dir) {
/*  534 */             q.right = s;
/*      */           } else {
/*  536 */             q.left = s;
/*      */           }  }
/*  538 */         else { this.tree = s; }
/*  539 */          s.balance(p.balance());
/*  540 */         q = r;
/*  541 */         dir = false;
/*      */       }  }
/*      */ 
/*      */     
/*  545 */     while (q != null) {
/*  546 */       Entry y = q;
/*  547 */       q = parent(y);
/*  548 */       if (!dir) {
/*  549 */         dir = (q != null && q.left != y);
/*  550 */         y.incBalance();
/*  551 */         if (y.balance() == 1)
/*      */           break; 
/*  553 */         if (y.balance() == 2) {
/*  554 */           Entry x = y.right;
/*  555 */           assert x != null;
/*  556 */           if (x.balance() == -1) {
/*      */             
/*  558 */             assert x.balance() == -1;
/*  559 */             Entry w = x.left;
/*  560 */             x.left = w.right;
/*  561 */             w.right = x;
/*  562 */             y.right = w.left;
/*  563 */             w.left = y;
/*  564 */             if (w.balance() == 1) {
/*  565 */               x.balance(0);
/*  566 */               y.balance(-1);
/*  567 */             } else if (w.balance() == 0) {
/*  568 */               x.balance(0);
/*  569 */               y.balance(0);
/*      */             } else {
/*  571 */               assert w.balance() == -1;
/*  572 */               x.balance(1);
/*  573 */               y.balance(0);
/*      */             } 
/*  575 */             w.balance(0);
/*  576 */             if (w.pred()) {
/*  577 */               y.succ(w);
/*  578 */               w.pred(false);
/*      */             } 
/*  580 */             if (w.succ()) {
/*  581 */               x.pred(w);
/*  582 */               w.succ(false);
/*      */             } 
/*  584 */             if (q != null) {
/*  585 */               if (dir) {
/*  586 */                 q.right = w; continue;
/*      */               } 
/*  588 */               q.left = w; continue;
/*      */             } 
/*  590 */             this.tree = w; continue;
/*      */           } 
/*  592 */           if (q != null)
/*  593 */           { if (dir) {
/*  594 */               q.right = x;
/*      */             } else {
/*  596 */               q.left = x;
/*      */             }  }
/*  598 */           else { this.tree = x; }
/*  599 */            if (x.balance() == 0) {
/*  600 */             y.right = x.left;
/*  601 */             x.left = y;
/*  602 */             x.balance(-1);
/*  603 */             y.balance(1);
/*      */             break;
/*      */           } 
/*  606 */           assert x.balance() == 1;
/*  607 */           if (x.pred()) {
/*  608 */             y.succ(true);
/*  609 */             x.pred(false);
/*      */           } else {
/*  611 */             y.right = x.left;
/*  612 */           }  x.left = y;
/*  613 */           y.balance(0);
/*  614 */           x.balance(0);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  618 */       dir = (q != null && q.left != y);
/*  619 */       y.decBalance();
/*  620 */       if (y.balance() == -1)
/*      */         break; 
/*  622 */       if (y.balance() == -2) {
/*  623 */         Entry x = y.left;
/*  624 */         assert x != null;
/*  625 */         if (x.balance() == 1) {
/*      */           
/*  627 */           assert x.balance() == 1;
/*  628 */           Entry w = x.right;
/*  629 */           x.right = w.left;
/*  630 */           w.left = x;
/*  631 */           y.left = w.right;
/*  632 */           w.right = y;
/*  633 */           if (w.balance() == -1) {
/*  634 */             x.balance(0);
/*  635 */             y.balance(1);
/*  636 */           } else if (w.balance() == 0) {
/*  637 */             x.balance(0);
/*  638 */             y.balance(0);
/*      */           } else {
/*  640 */             assert w.balance() == 1;
/*  641 */             x.balance(-1);
/*  642 */             y.balance(0);
/*      */           } 
/*  644 */           w.balance(0);
/*  645 */           if (w.pred()) {
/*  646 */             x.succ(w);
/*  647 */             w.pred(false);
/*      */           } 
/*  649 */           if (w.succ()) {
/*  650 */             y.pred(w);
/*  651 */             w.succ(false);
/*      */           } 
/*  653 */           if (q != null) {
/*  654 */             if (dir) {
/*  655 */               q.right = w; continue;
/*      */             } 
/*  657 */             q.left = w; continue;
/*      */           } 
/*  659 */           this.tree = w; continue;
/*      */         } 
/*  661 */         if (q != null)
/*  662 */         { if (dir) {
/*  663 */             q.right = x;
/*      */           } else {
/*  665 */             q.left = x;
/*      */           }  }
/*  667 */         else { this.tree = x; }
/*  668 */          if (x.balance() == 0) {
/*  669 */           y.left = x.right;
/*  670 */           x.right = y;
/*  671 */           x.balance(1);
/*  672 */           y.balance(-1);
/*      */           break;
/*      */         } 
/*  675 */         assert x.balance() == -1;
/*  676 */         if (x.succ()) {
/*  677 */           y.pred(true);
/*  678 */           x.succ(false);
/*      */         } else {
/*  680 */           y.left = x.right;
/*  681 */         }  x.right = y;
/*  682 */         y.balance(0);
/*  683 */         x.balance(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  688 */     this.count--;
/*  689 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean contains(char k) {
/*  694 */     return (findKey(k) != null);
/*      */   }
/*      */   
/*      */   public void clear() {
/*  698 */     this.count = 0;
/*  699 */     this.tree = null;
/*  700 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry
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
/*      */     char key;
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left;
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right;
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
/*      */     Entry(char k) {
/*  741 */       this.key = k;
/*  742 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left() {
/*  750 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right() {
/*  758 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  766 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  774 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  783 */       if (pred) {
/*  784 */         this.info |= 0x40000000;
/*      */       } else {
/*  786 */         this.info &= 0xBFFFFFFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  795 */       if (succ) {
/*  796 */         this.info |= Integer.MIN_VALUE;
/*      */       } else {
/*  798 */         this.info &= Integer.MAX_VALUE;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry pred) {
/*  807 */       this.info |= 0x40000000;
/*  808 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry succ) {
/*  817 */       this.info |= Integer.MIN_VALUE;
/*  818 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry left) {
/*  827 */       this.info &= 0xBFFFFFFF;
/*  828 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry right) {
/*  837 */       this.info &= Integer.MAX_VALUE;
/*  838 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int balance() {
/*  846 */       return (byte)this.info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void balance(int level) {
/*  855 */       this.info &= 0xFFFFFF00;
/*  856 */       this.info |= level & 0xFF;
/*      */     }
/*      */     
/*      */     void incBalance() {
/*  860 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
/*      */     }
/*      */     
/*      */     protected void decBalance() {
/*  864 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry next() {
/*  872 */       Entry next = this.right;
/*  873 */       if ((this.info & Integer.MIN_VALUE) == 0)
/*  874 */         while ((next.info & 0x40000000) == 0)
/*  875 */           next = next.left;  
/*  876 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry prev() {
/*  884 */       Entry prev = this.left;
/*  885 */       if ((this.info & 0x40000000) == 0)
/*  886 */         while ((prev.info & Integer.MIN_VALUE) == 0)
/*  887 */           prev = prev.right;  
/*  888 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public Entry clone() {
/*      */       Entry c;
/*      */       try {
/*  895 */         c = (Entry)super.clone();
/*  896 */       } catch (CloneNotSupportedException cantHappen) {
/*  897 */         throw new InternalError();
/*      */       } 
/*  899 */       c.key = this.key;
/*  900 */       c.info = this.info;
/*  901 */       return c;
/*      */     }
/*      */     public boolean equals(Object o) {
/*  904 */       if (!(o instanceof Entry))
/*  905 */         return false; 
/*  906 */       Entry e = (Entry)o;
/*  907 */       return (this.key == e.key);
/*      */     }
/*      */     public int hashCode() {
/*  910 */       return this.key;
/*      */     }
/*      */     public String toString() {
/*  913 */       return String.valueOf(this.key);
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
/*  933 */     return this.count;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  937 */     return (this.count == 0);
/*      */   }
/*      */   
/*      */   public char firstChar() {
/*  941 */     if (this.tree == null)
/*  942 */       throw new NoSuchElementException(); 
/*  943 */     return this.firstEntry.key;
/*      */   }
/*      */   
/*      */   public char lastChar() {
/*  947 */     if (this.tree == null)
/*  948 */       throw new NoSuchElementException(); 
/*  949 */     return this.lastEntry.key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class SetIterator
/*      */     implements CharListIterator
/*      */   {
/*      */     CharAVLTreeSet.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CharAVLTreeSet.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CharAVLTreeSet.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  980 */     int index = 0;
/*      */     SetIterator() {
/*  982 */       this.next = CharAVLTreeSet.this.firstEntry;
/*      */     }
/*      */     SetIterator(char k) {
/*  985 */       if ((this.next = CharAVLTreeSet.this.locateKey(k)) != null)
/*  986 */         if (CharAVLTreeSet.this.compare(this.next.key, k) <= 0) {
/*  987 */           this.prev = this.next;
/*  988 */           this.next = this.next.next();
/*      */         } else {
/*  990 */           this.prev = this.next.prev();
/*      */         }  
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  995 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  999 */       return (this.prev != null);
/*      */     }
/*      */     void updateNext() {
/* 1002 */       this.next = this.next.next();
/*      */     }
/*      */     CharAVLTreeSet.Entry nextEntry() {
/* 1005 */       if (!hasNext())
/* 1006 */         throw new NoSuchElementException(); 
/* 1007 */       this.curr = this.prev = this.next;
/* 1008 */       this.index++;
/* 1009 */       updateNext();
/* 1010 */       return this.curr;
/*      */     }
/*      */     
/*      */     public char nextChar() {
/* 1014 */       return (nextEntry()).key;
/*      */     }
/*      */     
/*      */     public char previousChar() {
/* 1018 */       return (previousEntry()).key;
/*      */     }
/*      */     void updatePrevious() {
/* 1021 */       this.prev = this.prev.prev();
/*      */     }
/*      */     CharAVLTreeSet.Entry previousEntry() {
/* 1024 */       if (!hasPrevious())
/* 1025 */         throw new NoSuchElementException(); 
/* 1026 */       this.curr = this.next = this.prev;
/* 1027 */       this.index--;
/* 1028 */       updatePrevious();
/* 1029 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1033 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1037 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1041 */       if (this.curr == null) {
/* 1042 */         throw new IllegalStateException();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1047 */       if (this.curr == this.prev)
/* 1048 */         this.index--; 
/* 1049 */       this.next = this.prev = this.curr;
/* 1050 */       updatePrevious();
/* 1051 */       updateNext();
/* 1052 */       CharAVLTreeSet.this.remove(this.curr.key);
/* 1053 */       this.curr = null;
/*      */     }
/*      */   }
/*      */   
/*      */   public CharBidirectionalIterator iterator() {
/* 1058 */     return new SetIterator();
/*      */   }
/*      */   
/*      */   public CharBidirectionalIterator iterator(char from) {
/* 1062 */     return new SetIterator(from);
/*      */   }
/*      */   
/*      */   public CharComparator comparator() {
/* 1066 */     return this.actualComparator;
/*      */   }
/*      */   
/*      */   public CharSortedSet headSet(char to) {
/* 1070 */     return new Subset(false, true, to, false);
/*      */   }
/*      */   
/*      */   public CharSortedSet tailSet(char from) {
/* 1074 */     return new Subset(from, false, false, true);
/*      */   }
/*      */   
/*      */   public CharSortedSet subSet(char from, char to) {
/* 1078 */     return new Subset(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Subset
/*      */     extends AbstractCharSortedSet
/*      */     implements Serializable, CharSortedSet
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     char from;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     char to;
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
/*      */     public Subset(char from, boolean bottom, char to, boolean top) {
/* 1116 */       if (!bottom && !top && CharAVLTreeSet.this.compare(from, to) > 0) {
/* 1117 */         throw new IllegalArgumentException("Start element (" + from + ") is larger than end element (" + to + ")");
/*      */       }
/* 1119 */       this.from = from;
/* 1120 */       this.bottom = bottom;
/* 1121 */       this.to = to;
/* 1122 */       this.top = top;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1126 */       SubsetIterator i = new SubsetIterator();
/* 1127 */       while (i.hasNext()) {
/* 1128 */         i.nextChar();
/* 1129 */         i.remove();
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
/* 1140 */       return ((this.bottom || CharAVLTreeSet.this.compare(k, this.from) >= 0) && (this.top || CharAVLTreeSet.this
/* 1141 */         .compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(char k) {
/* 1146 */       return (in(k) && CharAVLTreeSet.this.contains(k));
/*      */     }
/*      */     
/*      */     public boolean add(char k) {
/* 1150 */       if (!in(k))
/* 1151 */         throw new IllegalArgumentException("Element (" + k + ") out of range [" + (
/* 1152 */             this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1153 */       return CharAVLTreeSet.this.add(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(char k) {
/* 1158 */       if (!in(k))
/* 1159 */         return false; 
/* 1160 */       return CharAVLTreeSet.this.remove(k);
/*      */     }
/*      */     
/*      */     public int size() {
/* 1164 */       SubsetIterator i = new SubsetIterator();
/* 1165 */       int n = 0;
/* 1166 */       while (i.hasNext()) {
/* 1167 */         n++;
/* 1168 */         i.nextChar();
/*      */       } 
/* 1170 */       return n;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1174 */       return !(new SubsetIterator()).hasNext();
/*      */     }
/*      */     
/*      */     public CharComparator comparator() {
/* 1178 */       return CharAVLTreeSet.this.actualComparator;
/*      */     }
/*      */     
/*      */     public CharBidirectionalIterator iterator() {
/* 1182 */       return new SubsetIterator();
/*      */     }
/*      */     
/*      */     public CharBidirectionalIterator iterator(char from) {
/* 1186 */       return new SubsetIterator(from);
/*      */     }
/*      */     
/*      */     public CharSortedSet headSet(char to) {
/* 1190 */       if (this.top)
/* 1191 */         return new Subset(this.from, this.bottom, to, false); 
/* 1192 */       return (CharAVLTreeSet.this.compare(to, this.to) < 0) ? new Subset(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */     
/*      */     public CharSortedSet tailSet(char from) {
/* 1196 */       if (this.bottom)
/* 1197 */         return new Subset(from, false, this.to, this.top); 
/* 1198 */       return (CharAVLTreeSet.this.compare(from, this.from) > 0) ? new Subset(from, false, this.to, this.top) : this;
/*      */     }
/*      */     
/*      */     public CharSortedSet subSet(char from, char to) {
/* 1202 */       if (this.top && this.bottom)
/* 1203 */         return new Subset(from, false, to, false); 
/* 1204 */       if (!this.top)
/* 1205 */         to = (CharAVLTreeSet.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1206 */       if (!this.bottom)
/* 1207 */         from = (CharAVLTreeSet.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1208 */       if (!this.top && !this.bottom && from == this.from && to == this.to)
/* 1209 */         return this; 
/* 1210 */       return new Subset(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CharAVLTreeSet.Entry firstEntry() {
/*      */       CharAVLTreeSet.Entry e;
/* 1219 */       if (CharAVLTreeSet.this.tree == null) {
/* 1220 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1224 */       if (this.bottom) {
/* 1225 */         e = CharAVLTreeSet.this.firstEntry;
/*      */       } else {
/* 1227 */         e = CharAVLTreeSet.this.locateKey(this.from);
/*      */         
/* 1229 */         if (CharAVLTreeSet.this.compare(e.key, this.from) < 0) {
/* 1230 */           e = e.next();
/*      */         }
/*      */       } 
/*      */       
/* 1234 */       if (e == null || (!this.top && CharAVLTreeSet.this.compare(e.key, this.to) >= 0))
/* 1235 */         return null; 
/* 1236 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CharAVLTreeSet.Entry lastEntry() {
/*      */       CharAVLTreeSet.Entry e;
/* 1245 */       if (CharAVLTreeSet.this.tree == null) {
/* 1246 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1250 */       if (this.top) {
/* 1251 */         e = CharAVLTreeSet.this.lastEntry;
/*      */       } else {
/* 1253 */         e = CharAVLTreeSet.this.locateKey(this.to);
/*      */         
/* 1255 */         if (CharAVLTreeSet.this.compare(e.key, this.to) >= 0) {
/* 1256 */           e = e.prev();
/*      */         }
/*      */       } 
/*      */       
/* 1260 */       if (e == null || (!this.bottom && CharAVLTreeSet.this.compare(e.key, this.from) < 0))
/* 1261 */         return null; 
/* 1262 */       return e;
/*      */     }
/*      */     
/*      */     public char firstChar() {
/* 1266 */       CharAVLTreeSet.Entry e = firstEntry();
/* 1267 */       if (e == null)
/* 1268 */         throw new NoSuchElementException(); 
/* 1269 */       return e.key;
/*      */     }
/*      */     
/*      */     public char lastChar() {
/* 1273 */       CharAVLTreeSet.Entry e = lastEntry();
/* 1274 */       if (e == null)
/* 1275 */         throw new NoSuchElementException(); 
/* 1276 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubsetIterator
/*      */       extends CharAVLTreeSet.SetIterator
/*      */     {
/*      */       SubsetIterator() {
/* 1289 */         this.next = CharAVLTreeSet.Subset.this.firstEntry();
/*      */       }
/*      */       SubsetIterator(char k) {
/* 1292 */         this();
/* 1293 */         if (this.next != null)
/* 1294 */           if (!CharAVLTreeSet.Subset.this.bottom && CharAVLTreeSet.this.compare(k, this.next.key) < 0) {
/* 1295 */             this.prev = null;
/* 1296 */           } else if (!CharAVLTreeSet.Subset.this.top && CharAVLTreeSet.this.compare(k, (this.prev = CharAVLTreeSet.Subset.this.lastEntry()).key) >= 0) {
/* 1297 */             this.next = null;
/*      */           } else {
/* 1299 */             this.next = CharAVLTreeSet.this.locateKey(k);
/* 1300 */             if (CharAVLTreeSet.this.compare(this.next.key, k) <= 0) {
/* 1301 */               this.prev = this.next;
/* 1302 */               this.next = this.next.next();
/*      */             } else {
/* 1304 */               this.prev = this.next.prev();
/*      */             } 
/*      */           }  
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1310 */         this.prev = this.prev.prev();
/* 1311 */         if (!CharAVLTreeSet.Subset.this.bottom && this.prev != null && CharAVLTreeSet.this.compare(this.prev.key, CharAVLTreeSet.Subset.this.from) < 0)
/* 1312 */           this.prev = null; 
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1316 */         this.next = this.next.next();
/* 1317 */         if (!CharAVLTreeSet.Subset.this.top && this.next != null && CharAVLTreeSet.this.compare(this.next.key, CharAVLTreeSet.Subset.this.to) >= 0) {
/* 1318 */           this.next = null;
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
/*      */     CharAVLTreeSet c;
/*      */     try {
/* 1337 */       c = (CharAVLTreeSet)super.clone();
/* 1338 */     } catch (CloneNotSupportedException cantHappen) {
/* 1339 */       throw new InternalError();
/*      */     } 
/* 1341 */     c.allocatePaths();
/* 1342 */     if (this.count != 0) {
/*      */       
/* 1344 */       Entry rp = new Entry(), rq = new Entry();
/* 1345 */       Entry p = rp;
/* 1346 */       rp.left(this.tree);
/* 1347 */       Entry q = rq;
/* 1348 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1350 */         if (!p.pred()) {
/* 1351 */           Entry e = p.left.clone();
/* 1352 */           e.pred(q.left);
/* 1353 */           e.succ(q);
/* 1354 */           q.left(e);
/* 1355 */           p = p.left;
/* 1356 */           q = q.left;
/*      */         } else {
/* 1358 */           while (p.succ()) {
/* 1359 */             p = p.right;
/* 1360 */             if (p == null) {
/* 1361 */               q.right = null;
/* 1362 */               c.tree = rq.left;
/* 1363 */               c.firstEntry = c.tree;
/* 1364 */               while (c.firstEntry.left != null)
/* 1365 */                 c.firstEntry = c.firstEntry.left; 
/* 1366 */               c.lastEntry = c.tree;
/* 1367 */               while (c.lastEntry.right != null)
/* 1368 */                 c.lastEntry = c.lastEntry.right; 
/* 1369 */               return c;
/*      */             } 
/* 1371 */             q = q.right;
/*      */           } 
/* 1373 */           p = p.right;
/* 1374 */           q = q.right;
/*      */         } 
/* 1376 */         if (!p.succ()) {
/* 1377 */           Entry e = p.right.clone();
/* 1378 */           e.succ(q.right);
/* 1379 */           e.pred(q);
/* 1380 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1384 */     return c;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1387 */     int n = this.count;
/* 1388 */     SetIterator i = new SetIterator();
/* 1389 */     s.defaultWriteObject();
/* 1390 */     while (n-- != 0) {
/* 1391 */       s.writeChar(i.nextChar());
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
/*      */   private Entry readTree(ObjectInputStream s, int n, Entry pred, Entry succ) throws IOException, ClassNotFoundException {
/* 1411 */     if (n == 1) {
/* 1412 */       Entry entry = new Entry(s.readChar());
/* 1413 */       entry.pred(pred);
/* 1414 */       entry.succ(succ);
/* 1415 */       return entry;
/*      */     } 
/* 1417 */     if (n == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1422 */       Entry entry = new Entry(s.readChar());
/* 1423 */       entry.right(new Entry(s.readChar()));
/* 1424 */       entry.right.pred(entry);
/* 1425 */       entry.balance(1);
/* 1426 */       entry.pred(pred);
/* 1427 */       entry.right.succ(succ);
/* 1428 */       return entry;
/*      */     } 
/*      */     
/* 1431 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1432 */     Entry top = new Entry();
/* 1433 */     top.left(readTree(s, leftN, pred, top));
/* 1434 */     top.key = s.readChar();
/* 1435 */     top.right(readTree(s, rightN, top, succ));
/* 1436 */     if (n == (n & -n))
/* 1437 */       top.balance(1); 
/* 1438 */     return top;
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1441 */     s.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1446 */     setActualComparator();
/* 1447 */     allocatePaths();
/* 1448 */     if (this.count != 0) {
/* 1449 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1451 */       Entry e = this.tree;
/* 1452 */       while (e.left() != null)
/* 1453 */         e = e.left(); 
/* 1454 */       this.firstEntry = e;
/* 1455 */       e = this.tree;
/* 1456 */       while (e.right() != null)
/* 1457 */         e = e.right(); 
/* 1458 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\CharAVLTreeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */