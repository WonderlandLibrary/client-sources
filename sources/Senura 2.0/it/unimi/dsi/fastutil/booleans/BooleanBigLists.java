/*      */ package it.unimi.dsi.fastutil.booleans;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.BigList;
/*      */ import it.unimi.dsi.fastutil.BigListIterator;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.Random;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class BooleanBigLists
/*      */ {
/*      */   public static BooleanBigList shuffle(BooleanBigList l, Random random) {
/*   43 */     for (long i = l.size64(); i-- != 0L; ) {
/*   44 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/*   45 */       boolean t = l.getBoolean(i);
/*   46 */       l.set(i, l.getBoolean(p));
/*   47 */       l.set(p, t);
/*      */     } 
/*   49 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class EmptyBigList
/*      */     extends BooleanCollections.EmptyCollection
/*      */     implements BooleanBigList, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean getBoolean(long i) {
/*   68 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/*      */     public boolean rem(boolean k) {
/*   72 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean removeBoolean(long i) {
/*   76 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(long index, boolean k) {
/*   80 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean set(long index, boolean k) {
/*   84 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public long indexOf(boolean k) {
/*   88 */       return -1L;
/*      */     }
/*      */     
/*      */     public long lastIndexOf(boolean k) {
/*   92 */       return -1L;
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, Collection<? extends Boolean> c) {
/*   96 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(BooleanCollection c) {
/*  100 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(BooleanBigList c) {
/*  104 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, BooleanCollection c) {
/*  108 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, BooleanBigList c) {
/*  112 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void add(long index, Boolean k) {
/*  122 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean add(Boolean k) {
/*  132 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean get(long i) {
/*  142 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean set(long index, Boolean k) {
/*  152 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean remove(long k) {
/*  162 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public long indexOf(Object k) {
/*  172 */       return -1L;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public long lastIndexOf(Object k) {
/*  182 */       return -1L;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanBigListIterator listIterator() {
/*  187 */       return BooleanBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanBigListIterator iterator() {
/*  192 */       return BooleanBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanBigListIterator listIterator(long i) {
/*  197 */       if (i == 0L)
/*  198 */         return BooleanBigListIterators.EMPTY_BIG_LIST_ITERATOR; 
/*  199 */       throw new IndexOutOfBoundsException(String.valueOf(i));
/*      */     }
/*      */     
/*      */     public BooleanBigList subList(long from, long to) {
/*  203 */       if (from == 0L && to == 0L)
/*  204 */         return this; 
/*  205 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/*      */     public void getElements(long from, boolean[][] a, long offset, long length) {
/*  209 */       BigArrays.ensureOffsetLength(a, offset, length);
/*  210 */       if (from != 0L)
/*  211 */         throw new IndexOutOfBoundsException(); 
/*      */     }
/*      */     
/*      */     public void removeElements(long from, long to) {
/*  215 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(long index, boolean[][] a, long offset, long length) {
/*  219 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(long index, boolean[][] a) {
/*  223 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void size(long s) {
/*  227 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public long size64() {
/*  231 */       return 0L;
/*      */     }
/*      */     
/*      */     public int compareTo(BigList<? extends Boolean> o) {
/*  235 */       if (o == this)
/*  236 */         return 0; 
/*  237 */       return o.isEmpty() ? 0 : -1;
/*      */     }
/*      */     
/*      */     public Object clone() {
/*  241 */       return BooleanBigLists.EMPTY_BIG_LIST;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  245 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  250 */       return (o instanceof BigList && ((BigList)o).isEmpty());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  254 */       return "[]";
/*      */     }
/*      */     private Object readResolve() {
/*  257 */       return BooleanBigLists.EMPTY_BIG_LIST;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  264 */   public static final EmptyBigList EMPTY_BIG_LIST = new EmptyBigList();
/*      */ 
/*      */   
/*      */   public static class Singleton
/*      */     extends AbstractBooleanBigList
/*      */     implements Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private final boolean element;
/*      */     
/*      */     protected Singleton(boolean element) {
/*  276 */       this.element = element;
/*      */     }
/*      */     
/*      */     public boolean getBoolean(long i) {
/*  280 */       if (i == 0L)
/*  281 */         return this.element; 
/*  282 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/*      */     public boolean rem(boolean k) {
/*  286 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean removeBoolean(long i) {
/*  290 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean contains(boolean k) {
/*  294 */       return (k == this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean[] toBooleanArray() {
/*  299 */       boolean[] a = new boolean[1];
/*  300 */       a[0] = this.element;
/*  301 */       return a;
/*      */     }
/*      */     
/*      */     public BooleanBigListIterator listIterator() {
/*  305 */       return BooleanBigListIterators.singleton(this.element);
/*      */     }
/*      */     
/*      */     public BooleanBigListIterator listIterator(long i) {
/*  309 */       if (i > 1L || i < 0L)
/*  310 */         throw new IndexOutOfBoundsException(); 
/*  311 */       BooleanBigListIterator l = listIterator();
/*  312 */       if (i == 1L)
/*  313 */         l.nextBoolean(); 
/*  314 */       return l;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanBigList subList(long from, long to) {
/*  319 */       ensureIndex(from);
/*  320 */       ensureIndex(to);
/*  321 */       if (from > to) {
/*  322 */         throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
/*      */       }
/*  324 */       if (from != 0L || to != 1L)
/*  325 */         return BooleanBigLists.EMPTY_BIG_LIST; 
/*  326 */       return this;
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, Collection<? extends Boolean> c) {
/*  330 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(Collection<? extends Boolean> c) {
/*  334 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean removeAll(Collection<?> c) {
/*  338 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean retainAll(Collection<?> c) {
/*  342 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(BooleanBigList c) {
/*  346 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, BooleanBigList c) {
/*  350 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, BooleanCollection c) {
/*  354 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(BooleanCollection c) {
/*  358 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean removeAll(BooleanCollection c) {
/*  362 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean retainAll(BooleanCollection c) {
/*  366 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void clear() {
/*  370 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public long size64() {
/*  374 */       return 1L;
/*      */     }
/*      */     
/*      */     public Object clone() {
/*  378 */       return this;
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
/*      */   public static BooleanBigList singleton(boolean element) {
/*  390 */     return new Singleton(element);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanBigList singleton(Object element) {
/*  401 */     return new Singleton(((Boolean)element).booleanValue());
/*      */   }
/*      */   
/*      */   public static class SynchronizedBigList
/*      */     extends BooleanCollections.SynchronizedCollection
/*      */     implements BooleanBigList, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final BooleanBigList list;
/*      */     
/*      */     protected SynchronizedBigList(BooleanBigList l, Object sync) {
/*  412 */       super(l, sync);
/*  413 */       this.list = l;
/*      */     }
/*      */     protected SynchronizedBigList(BooleanBigList l) {
/*  416 */       super(l);
/*  417 */       this.list = l;
/*      */     }
/*      */     
/*      */     public boolean getBoolean(long i) {
/*  421 */       synchronized (this.sync) {
/*  422 */         return this.list.getBoolean(i);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean set(long i, boolean k) {
/*  427 */       synchronized (this.sync) {
/*  428 */         return this.list.set(i, k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void add(long i, boolean k) {
/*  433 */       synchronized (this.sync) {
/*  434 */         this.list.add(i, k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean removeBoolean(long i) {
/*  439 */       synchronized (this.sync) {
/*  440 */         return this.list.removeBoolean(i);
/*      */       } 
/*      */     }
/*      */     
/*      */     public long indexOf(boolean k) {
/*  445 */       synchronized (this.sync) {
/*  446 */         return this.list.indexOf(k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public long lastIndexOf(boolean k) {
/*  451 */       synchronized (this.sync) {
/*  452 */         return this.list.lastIndexOf(k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Boolean> c) {
/*  457 */       synchronized (this.sync) {
/*  458 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void getElements(long from, boolean[][] a, long offset, long length) {
/*  463 */       synchronized (this.sync) {
/*  464 */         this.list.getElements(from, a, offset, length);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void removeElements(long from, long to) {
/*  469 */       synchronized (this.sync) {
/*  470 */         this.list.removeElements(from, to);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void addElements(long index, boolean[][] a, long offset, long length) {
/*  475 */       synchronized (this.sync) {
/*  476 */         this.list.addElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void addElements(long index, boolean[][] a) {
/*  481 */       synchronized (this.sync) {
/*  482 */         this.list.addElements(index, a);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void size(long size) {
/*  493 */       synchronized (this.sync) {
/*  494 */         this.list.size(size);
/*      */       } 
/*      */     }
/*      */     
/*      */     public long size64() {
/*  499 */       synchronized (this.sync) {
/*  500 */         return this.list.size64();
/*      */       } 
/*      */     }
/*      */     
/*      */     public BooleanBigListIterator iterator() {
/*  505 */       return this.list.listIterator();
/*      */     }
/*      */     
/*      */     public BooleanBigListIterator listIterator() {
/*  509 */       return this.list.listIterator();
/*      */     }
/*      */     
/*      */     public BooleanBigListIterator listIterator(long i) {
/*  513 */       return this.list.listIterator(i);
/*      */     }
/*      */     
/*      */     public BooleanBigList subList(long from, long to) {
/*  517 */       synchronized (this.sync) {
/*  518 */         return BooleanBigLists.synchronize(this.list.subList(from, to), this.sync);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean equals(Object o) {
/*  523 */       if (o == this)
/*  524 */         return true; 
/*  525 */       synchronized (this.sync) {
/*  526 */         return this.list.equals(o);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  531 */       synchronized (this.sync) {
/*  532 */         return this.list.hashCode();
/*      */       } 
/*      */     }
/*      */     
/*      */     public int compareTo(BigList<? extends Boolean> o) {
/*  537 */       synchronized (this.sync) {
/*  538 */         return this.list.compareTo(o);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, BooleanCollection c) {
/*  543 */       synchronized (this.sync) {
/*  544 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, BooleanBigList l) {
/*  549 */       synchronized (this.sync) {
/*  550 */         return this.list.addAll(index, l);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(BooleanBigList l) {
/*  555 */       synchronized (this.sync) {
/*  556 */         return this.list.addAll(l);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void add(long i, Boolean k) {
/*  567 */       synchronized (this.sync) {
/*  568 */         this.list.add(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean get(long i) {
/*  579 */       synchronized (this.sync) {
/*  580 */         return this.list.get(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean set(long index, Boolean k) {
/*  591 */       synchronized (this.sync) {
/*  592 */         return this.list.set(index, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean remove(long i) {
/*  603 */       synchronized (this.sync) {
/*  604 */         return this.list.remove(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public long indexOf(Object o) {
/*  615 */       synchronized (this.sync) {
/*  616 */         return this.list.indexOf(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public long lastIndexOf(Object o) {
/*  627 */       synchronized (this.sync) {
/*  628 */         return this.list.lastIndexOf(o);
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
/*      */   public static BooleanBigList synchronize(BooleanBigList l) {
/*  642 */     return new SynchronizedBigList(l);
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
/*      */   public static BooleanBigList synchronize(BooleanBigList l, Object sync) {
/*  657 */     return new SynchronizedBigList(l, sync);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBigList
/*      */     extends BooleanCollections.UnmodifiableCollection
/*      */     implements BooleanBigList, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final BooleanBigList list;
/*      */     
/*      */     protected UnmodifiableBigList(BooleanBigList l) {
/*  668 */       super(l);
/*  669 */       this.list = l;
/*      */     }
/*      */     
/*      */     public boolean getBoolean(long i) {
/*  673 */       return this.list.getBoolean(i);
/*      */     }
/*      */     
/*      */     public boolean set(long i, boolean k) {
/*  677 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(long i, boolean k) {
/*  681 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean removeBoolean(long i) {
/*  685 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public long indexOf(boolean k) {
/*  689 */       return this.list.indexOf(k);
/*      */     }
/*      */     
/*      */     public long lastIndexOf(boolean k) {
/*  693 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Boolean> c) {
/*  697 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void getElements(long from, boolean[][] a, long offset, long length) {
/*  701 */       this.list.getElements(from, a, offset, length);
/*      */     }
/*      */     
/*      */     public void removeElements(long from, long to) {
/*  705 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(long index, boolean[][] a, long offset, long length) {
/*  709 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(long index, boolean[][] a) {
/*  713 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void size(long size) {
/*  723 */       this.list.size(size);
/*      */     }
/*      */     
/*      */     public long size64() {
/*  727 */       return this.list.size64();
/*      */     }
/*      */     
/*      */     public BooleanBigListIterator iterator() {
/*  731 */       return listIterator();
/*      */     }
/*      */     
/*      */     public BooleanBigListIterator listIterator() {
/*  735 */       return BooleanBigListIterators.unmodifiable(this.list.listIterator());
/*      */     }
/*      */     
/*      */     public BooleanBigListIterator listIterator(long i) {
/*  739 */       return BooleanBigListIterators.unmodifiable(this.list.listIterator(i));
/*      */     }
/*      */     
/*      */     public BooleanBigList subList(long from, long to) {
/*  743 */       return BooleanBigLists.unmodifiable(this.list.subList(from, to));
/*      */     }
/*      */     
/*      */     public boolean equals(Object o) {
/*  747 */       if (o == this)
/*  748 */         return true; 
/*  749 */       return this.list.equals(o);
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  753 */       return this.list.hashCode();
/*      */     }
/*      */     
/*      */     public int compareTo(BigList<? extends Boolean> o) {
/*  757 */       return this.list.compareTo(o);
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, BooleanCollection c) {
/*  761 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(BooleanBigList l) {
/*  765 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, BooleanBigList l) {
/*  769 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean get(long i) {
/*  779 */       return this.list.get(i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void add(long i, Boolean k) {
/*  789 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean set(long index, Boolean k) {
/*  799 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean remove(long i) {
/*  809 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public long indexOf(Object o) {
/*  819 */       return this.list.indexOf(o);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public long lastIndexOf(Object o) {
/*  829 */       return this.list.lastIndexOf(o);
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
/*      */   public static BooleanBigList unmodifiable(BooleanBigList l) {
/*  842 */     return new UnmodifiableBigList(l);
/*      */   }
/*      */   
/*      */   public static class ListBigList extends AbstractBooleanBigList implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*      */     private final BooleanList list;
/*      */     
/*      */     protected ListBigList(BooleanList list) {
/*  849 */       this.list = list;
/*      */     }
/*      */     private int intIndex(long index) {
/*  852 */       if (index >= 2147483647L)
/*  853 */         throw new IndexOutOfBoundsException("This big list is restricted to 32-bit indices"); 
/*  854 */       return (int)index;
/*      */     }
/*      */     
/*      */     public long size64() {
/*  858 */       return this.list.size();
/*      */     }
/*      */     
/*      */     public void size(long size) {
/*  862 */       this.list.size(intIndex(size));
/*      */     }
/*      */     
/*      */     public BooleanBigListIterator iterator() {
/*  866 */       return BooleanBigListIterators.asBigListIterator(this.list.iterator());
/*      */     }
/*      */     
/*      */     public BooleanBigListIterator listIterator() {
/*  870 */       return BooleanBigListIterators.asBigListIterator(this.list.listIterator());
/*      */     }
/*      */     
/*      */     public BooleanBigListIterator listIterator(long index) {
/*  874 */       return BooleanBigListIterators.asBigListIterator(this.list.listIterator(intIndex(index)));
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Boolean> c) {
/*  878 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */     
/*      */     public BooleanBigList subList(long from, long to) {
/*  882 */       return new ListBigList(this.list.subList(intIndex(from), intIndex(to)));
/*      */     }
/*      */     
/*      */     public boolean contains(boolean key) {
/*  886 */       return this.list.contains(key);
/*      */     }
/*      */     
/*      */     public boolean[] toBooleanArray() {
/*  890 */       return this.list.toBooleanArray();
/*      */     }
/*      */     
/*      */     public void removeElements(long from, long to) {
/*  894 */       this.list.removeElements(intIndex(from), intIndex(to));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean[] toBooleanArray(boolean[] a) {
/*  905 */       return this.list.toArray(a);
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, BooleanCollection c) {
/*  909 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */     
/*      */     public boolean addAll(BooleanCollection c) {
/*  913 */       return this.list.addAll(c);
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, BooleanBigList c) {
/*  917 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */     
/*      */     public boolean addAll(BooleanBigList c) {
/*  921 */       return this.list.addAll(c);
/*      */     }
/*      */     
/*      */     public boolean containsAll(BooleanCollection c) {
/*  925 */       return this.list.containsAll(c);
/*      */     }
/*      */     
/*      */     public boolean removeAll(BooleanCollection c) {
/*  929 */       return this.list.removeAll(c);
/*      */     }
/*      */     
/*      */     public boolean retainAll(BooleanCollection c) {
/*  933 */       return this.list.retainAll(c);
/*      */     }
/*      */     
/*      */     public void add(long index, boolean key) {
/*  937 */       this.list.add(intIndex(index), key);
/*      */     }
/*      */     
/*      */     public boolean add(boolean key) {
/*  941 */       return this.list.add(key);
/*      */     }
/*      */     
/*      */     public boolean getBoolean(long index) {
/*  945 */       return this.list.getBoolean(intIndex(index));
/*      */     }
/*      */     
/*      */     public long indexOf(boolean k) {
/*  949 */       return this.list.indexOf(k);
/*      */     }
/*      */     
/*      */     public long lastIndexOf(boolean k) {
/*  953 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */     
/*      */     public boolean removeBoolean(long index) {
/*  957 */       return this.list.removeBoolean(intIndex(index));
/*      */     }
/*      */     
/*      */     public boolean set(long index, boolean k) {
/*  961 */       return this.list.set(intIndex(index), k);
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/*  965 */       return this.list.isEmpty();
/*      */     }
/*      */     
/*      */     public <T> T[] toArray(T[] a) {
/*  969 */       return (T[])this.list.toArray((Object[])a);
/*      */     }
/*      */     
/*      */     public boolean containsAll(Collection<?> c) {
/*  973 */       return this.list.containsAll(c);
/*      */     }
/*      */     
/*      */     public boolean addAll(Collection<? extends Boolean> c) {
/*  977 */       return this.list.addAll(c);
/*      */     }
/*      */     
/*      */     public boolean removeAll(Collection<?> c) {
/*  981 */       return this.list.removeAll(c);
/*      */     }
/*      */     
/*      */     public boolean retainAll(Collection<?> c) {
/*  985 */       return this.list.retainAll(c);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  989 */       this.list.clear();
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  993 */       return this.list.hashCode();
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanBigList asBigList(BooleanList list) {
/* 1004 */     return new ListBigList(list);
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\booleans\BooleanBigLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */