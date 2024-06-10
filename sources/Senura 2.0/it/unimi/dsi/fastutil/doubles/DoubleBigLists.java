/*      */ package it.unimi.dsi.fastutil.doubles;
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
/*      */ public final class DoubleBigLists
/*      */ {
/*      */   public static DoubleBigList shuffle(DoubleBigList l, Random random) {
/*   43 */     for (long i = l.size64(); i-- != 0L; ) {
/*   44 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/*   45 */       double t = l.getDouble(i);
/*   46 */       l.set(i, l.getDouble(p));
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
/*      */     extends DoubleCollections.EmptyCollection
/*      */     implements DoubleBigList, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getDouble(long i) {
/*   68 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/*      */     public boolean rem(double k) {
/*   72 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public double removeDouble(long i) {
/*   76 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(long index, double k) {
/*   80 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public double set(long index, double k) {
/*   84 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public long indexOf(double k) {
/*   88 */       return -1L;
/*      */     }
/*      */     
/*      */     public long lastIndexOf(double k) {
/*   92 */       return -1L;
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, Collection<? extends Double> c) {
/*   96 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(DoubleCollection c) {
/*  100 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(DoubleBigList c) {
/*  104 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, DoubleCollection c) {
/*  108 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, DoubleBigList c) {
/*  112 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void add(long index, Double k) {
/*  122 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean add(Double k) {
/*  132 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double get(long i) {
/*  142 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double set(long index, Double k) {
/*  152 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double remove(long k) {
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
/*      */     public DoubleBigListIterator listIterator() {
/*  187 */       return DoubleBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBigListIterator iterator() {
/*  192 */       return DoubleBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBigListIterator listIterator(long i) {
/*  197 */       if (i == 0L)
/*  198 */         return DoubleBigListIterators.EMPTY_BIG_LIST_ITERATOR; 
/*  199 */       throw new IndexOutOfBoundsException(String.valueOf(i));
/*      */     }
/*      */     
/*      */     public DoubleBigList subList(long from, long to) {
/*  203 */       if (from == 0L && to == 0L)
/*  204 */         return this; 
/*  205 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/*      */     public void getElements(long from, double[][] a, long offset, long length) {
/*  209 */       BigArrays.ensureOffsetLength(a, offset, length);
/*  210 */       if (from != 0L)
/*  211 */         throw new IndexOutOfBoundsException(); 
/*      */     }
/*      */     
/*      */     public void removeElements(long from, long to) {
/*  215 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(long index, double[][] a, long offset, long length) {
/*  219 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(long index, double[][] a) {
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
/*      */     public int compareTo(BigList<? extends Double> o) {
/*  235 */       if (o == this)
/*  236 */         return 0; 
/*  237 */       return o.isEmpty() ? 0 : -1;
/*      */     }
/*      */     
/*      */     public Object clone() {
/*  241 */       return DoubleBigLists.EMPTY_BIG_LIST;
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
/*  257 */       return DoubleBigLists.EMPTY_BIG_LIST;
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
/*      */     extends AbstractDoubleBigList
/*      */     implements Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private final double element;
/*      */     
/*      */     protected Singleton(double element) {
/*  276 */       this.element = element;
/*      */     }
/*      */     
/*      */     public double getDouble(long i) {
/*  280 */       if (i == 0L)
/*  281 */         return this.element; 
/*  282 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/*      */     public boolean rem(double k) {
/*  286 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public double removeDouble(long i) {
/*  290 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/*  294 */       return (Double.doubleToLongBits(k) == Double.doubleToLongBits(this.element));
/*      */     }
/*      */ 
/*      */     
/*      */     public double[] toDoubleArray() {
/*  299 */       double[] a = new double[1];
/*  300 */       a[0] = this.element;
/*  301 */       return a;
/*      */     }
/*      */     
/*      */     public DoubleBigListIterator listIterator() {
/*  305 */       return DoubleBigListIterators.singleton(this.element);
/*      */     }
/*      */     
/*      */     public DoubleBigListIterator listIterator(long i) {
/*  309 */       if (i > 1L || i < 0L)
/*  310 */         throw new IndexOutOfBoundsException(); 
/*  311 */       DoubleBigListIterator l = listIterator();
/*  312 */       if (i == 1L)
/*  313 */         l.nextDouble(); 
/*  314 */       return l;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBigList subList(long from, long to) {
/*  319 */       ensureIndex(from);
/*  320 */       ensureIndex(to);
/*  321 */       if (from > to) {
/*  322 */         throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
/*      */       }
/*  324 */       if (from != 0L || to != 1L)
/*  325 */         return DoubleBigLists.EMPTY_BIG_LIST; 
/*  326 */       return this;
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, Collection<? extends Double> c) {
/*  330 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(Collection<? extends Double> c) {
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
/*      */     public boolean addAll(DoubleBigList c) {
/*  346 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, DoubleBigList c) {
/*  350 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, DoubleCollection c) {
/*  354 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(DoubleCollection c) {
/*  358 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean removeAll(DoubleCollection c) {
/*  362 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean retainAll(DoubleCollection c) {
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
/*      */   public static DoubleBigList singleton(double element) {
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
/*      */   public static DoubleBigList singleton(Object element) {
/*  401 */     return new Singleton(((Double)element).doubleValue());
/*      */   }
/*      */   
/*      */   public static class SynchronizedBigList
/*      */     extends DoubleCollections.SynchronizedCollection
/*      */     implements DoubleBigList, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final DoubleBigList list;
/*      */     
/*      */     protected SynchronizedBigList(DoubleBigList l, Object sync) {
/*  412 */       super(l, sync);
/*  413 */       this.list = l;
/*      */     }
/*      */     protected SynchronizedBigList(DoubleBigList l) {
/*  416 */       super(l);
/*  417 */       this.list = l;
/*      */     }
/*      */     
/*      */     public double getDouble(long i) {
/*  421 */       synchronized (this.sync) {
/*  422 */         return this.list.getDouble(i);
/*      */       } 
/*      */     }
/*      */     
/*      */     public double set(long i, double k) {
/*  427 */       synchronized (this.sync) {
/*  428 */         return this.list.set(i, k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void add(long i, double k) {
/*  433 */       synchronized (this.sync) {
/*  434 */         this.list.add(i, k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public double removeDouble(long i) {
/*  439 */       synchronized (this.sync) {
/*  440 */         return this.list.removeDouble(i);
/*      */       } 
/*      */     }
/*      */     
/*      */     public long indexOf(double k) {
/*  445 */       synchronized (this.sync) {
/*  446 */         return this.list.indexOf(k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public long lastIndexOf(double k) {
/*  451 */       synchronized (this.sync) {
/*  452 */         return this.list.lastIndexOf(k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Double> c) {
/*  457 */       synchronized (this.sync) {
/*  458 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void getElements(long from, double[][] a, long offset, long length) {
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
/*      */     public void addElements(long index, double[][] a, long offset, long length) {
/*  475 */       synchronized (this.sync) {
/*  476 */         this.list.addElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void addElements(long index, double[][] a) {
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
/*      */     public DoubleBigListIterator iterator() {
/*  505 */       return this.list.listIterator();
/*      */     }
/*      */     
/*      */     public DoubleBigListIterator listIterator() {
/*  509 */       return this.list.listIterator();
/*      */     }
/*      */     
/*      */     public DoubleBigListIterator listIterator(long i) {
/*  513 */       return this.list.listIterator(i);
/*      */     }
/*      */     
/*      */     public DoubleBigList subList(long from, long to) {
/*  517 */       synchronized (this.sync) {
/*  518 */         return DoubleBigLists.synchronize(this.list.subList(from, to), this.sync);
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
/*      */     public int compareTo(BigList<? extends Double> o) {
/*  537 */       synchronized (this.sync) {
/*  538 */         return this.list.compareTo(o);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, DoubleCollection c) {
/*  543 */       synchronized (this.sync) {
/*  544 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, DoubleBigList l) {
/*  549 */       synchronized (this.sync) {
/*  550 */         return this.list.addAll(index, l);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(DoubleBigList l) {
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
/*      */     public void add(long i, Double k) {
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
/*      */     public Double get(long i) {
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
/*      */     public Double set(long index, Double k) {
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
/*      */     public Double remove(long i) {
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
/*      */   public static DoubleBigList synchronize(DoubleBigList l) {
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
/*      */   public static DoubleBigList synchronize(DoubleBigList l, Object sync) {
/*  657 */     return new SynchronizedBigList(l, sync);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBigList
/*      */     extends DoubleCollections.UnmodifiableCollection
/*      */     implements DoubleBigList, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final DoubleBigList list;
/*      */     
/*      */     protected UnmodifiableBigList(DoubleBigList l) {
/*  668 */       super(l);
/*  669 */       this.list = l;
/*      */     }
/*      */     
/*      */     public double getDouble(long i) {
/*  673 */       return this.list.getDouble(i);
/*      */     }
/*      */     
/*      */     public double set(long i, double k) {
/*  677 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(long i, double k) {
/*  681 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public double removeDouble(long i) {
/*  685 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public long indexOf(double k) {
/*  689 */       return this.list.indexOf(k);
/*      */     }
/*      */     
/*      */     public long lastIndexOf(double k) {
/*  693 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Double> c) {
/*  697 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void getElements(long from, double[][] a, long offset, long length) {
/*  701 */       this.list.getElements(from, a, offset, length);
/*      */     }
/*      */     
/*      */     public void removeElements(long from, long to) {
/*  705 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(long index, double[][] a, long offset, long length) {
/*  709 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(long index, double[][] a) {
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
/*      */     public DoubleBigListIterator iterator() {
/*  731 */       return listIterator();
/*      */     }
/*      */     
/*      */     public DoubleBigListIterator listIterator() {
/*  735 */       return DoubleBigListIterators.unmodifiable(this.list.listIterator());
/*      */     }
/*      */     
/*      */     public DoubleBigListIterator listIterator(long i) {
/*  739 */       return DoubleBigListIterators.unmodifiable(this.list.listIterator(i));
/*      */     }
/*      */     
/*      */     public DoubleBigList subList(long from, long to) {
/*  743 */       return DoubleBigLists.unmodifiable(this.list.subList(from, to));
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
/*      */     public int compareTo(BigList<? extends Double> o) {
/*  757 */       return this.list.compareTo(o);
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, DoubleCollection c) {
/*  761 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(DoubleBigList l) {
/*  765 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, DoubleBigList l) {
/*  769 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double get(long i) {
/*  779 */       return this.list.get(i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void add(long i, Double k) {
/*  789 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double set(long index, Double k) {
/*  799 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double remove(long i) {
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
/*      */   public static DoubleBigList unmodifiable(DoubleBigList l) {
/*  842 */     return new UnmodifiableBigList(l);
/*      */   }
/*      */   
/*      */   public static class ListBigList extends AbstractDoubleBigList implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*      */     private final DoubleList list;
/*      */     
/*      */     protected ListBigList(DoubleList list) {
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
/*      */     public DoubleBigListIterator iterator() {
/*  866 */       return DoubleBigListIterators.asBigListIterator(this.list.iterator());
/*      */     }
/*      */     
/*      */     public DoubleBigListIterator listIterator() {
/*  870 */       return DoubleBigListIterators.asBigListIterator(this.list.listIterator());
/*      */     }
/*      */     
/*      */     public DoubleBigListIterator listIterator(long index) {
/*  874 */       return DoubleBigListIterators.asBigListIterator(this.list.listIterator(intIndex(index)));
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Double> c) {
/*  878 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */     
/*      */     public DoubleBigList subList(long from, long to) {
/*  882 */       return new ListBigList(this.list.subList(intIndex(from), intIndex(to)));
/*      */     }
/*      */     
/*      */     public boolean contains(double key) {
/*  886 */       return this.list.contains(key);
/*      */     }
/*      */     
/*      */     public double[] toDoubleArray() {
/*  890 */       return this.list.toDoubleArray();
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
/*      */     public double[] toDoubleArray(double[] a) {
/*  905 */       return this.list.toArray(a);
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, DoubleCollection c) {
/*  909 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */     
/*      */     public boolean addAll(DoubleCollection c) {
/*  913 */       return this.list.addAll(c);
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, DoubleBigList c) {
/*  917 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */     
/*      */     public boolean addAll(DoubleBigList c) {
/*  921 */       return this.list.addAll(c);
/*      */     }
/*      */     
/*      */     public boolean containsAll(DoubleCollection c) {
/*  925 */       return this.list.containsAll(c);
/*      */     }
/*      */     
/*      */     public boolean removeAll(DoubleCollection c) {
/*  929 */       return this.list.removeAll(c);
/*      */     }
/*      */     
/*      */     public boolean retainAll(DoubleCollection c) {
/*  933 */       return this.list.retainAll(c);
/*      */     }
/*      */     
/*      */     public void add(long index, double key) {
/*  937 */       this.list.add(intIndex(index), key);
/*      */     }
/*      */     
/*      */     public boolean add(double key) {
/*  941 */       return this.list.add(key);
/*      */     }
/*      */     
/*      */     public double getDouble(long index) {
/*  945 */       return this.list.getDouble(intIndex(index));
/*      */     }
/*      */     
/*      */     public long indexOf(double k) {
/*  949 */       return this.list.indexOf(k);
/*      */     }
/*      */     
/*      */     public long lastIndexOf(double k) {
/*  953 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */     
/*      */     public double removeDouble(long index) {
/*  957 */       return this.list.removeDouble(intIndex(index));
/*      */     }
/*      */     
/*      */     public double set(long index, double k) {
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
/*      */     public boolean addAll(Collection<? extends Double> c) {
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
/*      */   public static DoubleBigList asBigList(DoubleList list) {
/* 1004 */     return new ListBigList(list);
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleBigLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */