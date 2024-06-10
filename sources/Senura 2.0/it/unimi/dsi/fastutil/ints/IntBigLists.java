/*      */ package it.unimi.dsi.fastutil.ints;
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
/*      */ public final class IntBigLists
/*      */ {
/*      */   public static IntBigList shuffle(IntBigList l, Random random) {
/*   43 */     for (long i = l.size64(); i-- != 0L; ) {
/*   44 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/*   45 */       int t = l.getInt(i);
/*   46 */       l.set(i, l.getInt(p));
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
/*      */     extends IntCollections.EmptyCollection
/*      */     implements IntBigList, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getInt(long i) {
/*   68 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/*      */     public boolean rem(int k) {
/*   72 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public int removeInt(long i) {
/*   76 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(long index, int k) {
/*   80 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public int set(long index, int k) {
/*   84 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public long indexOf(int k) {
/*   88 */       return -1L;
/*      */     }
/*      */     
/*      */     public long lastIndexOf(int k) {
/*   92 */       return -1L;
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, Collection<? extends Integer> c) {
/*   96 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(IntCollection c) {
/*  100 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(IntBigList c) {
/*  104 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, IntCollection c) {
/*  108 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, IntBigList c) {
/*  112 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void add(long index, Integer k) {
/*  122 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean add(Integer k) {
/*  132 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer get(long i) {
/*  142 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer set(long index, Integer k) {
/*  152 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer remove(long k) {
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
/*      */     public IntBigListIterator listIterator() {
/*  187 */       return IntBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntBigListIterator iterator() {
/*  192 */       return IntBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntBigListIterator listIterator(long i) {
/*  197 */       if (i == 0L)
/*  198 */         return IntBigListIterators.EMPTY_BIG_LIST_ITERATOR; 
/*  199 */       throw new IndexOutOfBoundsException(String.valueOf(i));
/*      */     }
/*      */     
/*      */     public IntBigList subList(long from, long to) {
/*  203 */       if (from == 0L && to == 0L)
/*  204 */         return this; 
/*  205 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/*      */     public void getElements(long from, int[][] a, long offset, long length) {
/*  209 */       BigArrays.ensureOffsetLength(a, offset, length);
/*  210 */       if (from != 0L)
/*  211 */         throw new IndexOutOfBoundsException(); 
/*      */     }
/*      */     
/*      */     public void removeElements(long from, long to) {
/*  215 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(long index, int[][] a, long offset, long length) {
/*  219 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(long index, int[][] a) {
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
/*      */     public int compareTo(BigList<? extends Integer> o) {
/*  235 */       if (o == this)
/*  236 */         return 0; 
/*  237 */       return o.isEmpty() ? 0 : -1;
/*      */     }
/*      */     
/*      */     public Object clone() {
/*  241 */       return IntBigLists.EMPTY_BIG_LIST;
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
/*  257 */       return IntBigLists.EMPTY_BIG_LIST;
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
/*      */     extends AbstractIntBigList
/*      */     implements Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private final int element;
/*      */     
/*      */     protected Singleton(int element) {
/*  276 */       this.element = element;
/*      */     }
/*      */     
/*      */     public int getInt(long i) {
/*  280 */       if (i == 0L)
/*  281 */         return this.element; 
/*  282 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/*      */     public boolean rem(int k) {
/*  286 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public int removeInt(long i) {
/*  290 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean contains(int k) {
/*  294 */       return (k == this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public int[] toIntArray() {
/*  299 */       int[] a = new int[1];
/*  300 */       a[0] = this.element;
/*  301 */       return a;
/*      */     }
/*      */     
/*      */     public IntBigListIterator listIterator() {
/*  305 */       return IntBigListIterators.singleton(this.element);
/*      */     }
/*      */     
/*      */     public IntBigListIterator listIterator(long i) {
/*  309 */       if (i > 1L || i < 0L)
/*  310 */         throw new IndexOutOfBoundsException(); 
/*  311 */       IntBigListIterator l = listIterator();
/*  312 */       if (i == 1L)
/*  313 */         l.nextInt(); 
/*  314 */       return l;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntBigList subList(long from, long to) {
/*  319 */       ensureIndex(from);
/*  320 */       ensureIndex(to);
/*  321 */       if (from > to) {
/*  322 */         throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
/*      */       }
/*  324 */       if (from != 0L || to != 1L)
/*  325 */         return IntBigLists.EMPTY_BIG_LIST; 
/*  326 */       return this;
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, Collection<? extends Integer> c) {
/*  330 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(Collection<? extends Integer> c) {
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
/*      */     public boolean addAll(IntBigList c) {
/*  346 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, IntBigList c) {
/*  350 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, IntCollection c) {
/*  354 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(IntCollection c) {
/*  358 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean removeAll(IntCollection c) {
/*  362 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean retainAll(IntCollection c) {
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
/*      */   public static IntBigList singleton(int element) {
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
/*      */   public static IntBigList singleton(Object element) {
/*  401 */     return new Singleton(((Integer)element).intValue());
/*      */   }
/*      */   
/*      */   public static class SynchronizedBigList
/*      */     extends IntCollections.SynchronizedCollection
/*      */     implements IntBigList, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final IntBigList list;
/*      */     
/*      */     protected SynchronizedBigList(IntBigList l, Object sync) {
/*  412 */       super(l, sync);
/*  413 */       this.list = l;
/*      */     }
/*      */     protected SynchronizedBigList(IntBigList l) {
/*  416 */       super(l);
/*  417 */       this.list = l;
/*      */     }
/*      */     
/*      */     public int getInt(long i) {
/*  421 */       synchronized (this.sync) {
/*  422 */         return this.list.getInt(i);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int set(long i, int k) {
/*  427 */       synchronized (this.sync) {
/*  428 */         return this.list.set(i, k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void add(long i, int k) {
/*  433 */       synchronized (this.sync) {
/*  434 */         this.list.add(i, k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int removeInt(long i) {
/*  439 */       synchronized (this.sync) {
/*  440 */         return this.list.removeInt(i);
/*      */       } 
/*      */     }
/*      */     
/*      */     public long indexOf(int k) {
/*  445 */       synchronized (this.sync) {
/*  446 */         return this.list.indexOf(k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public long lastIndexOf(int k) {
/*  451 */       synchronized (this.sync) {
/*  452 */         return this.list.lastIndexOf(k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Integer> c) {
/*  457 */       synchronized (this.sync) {
/*  458 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void getElements(long from, int[][] a, long offset, long length) {
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
/*      */     public void addElements(long index, int[][] a, long offset, long length) {
/*  475 */       synchronized (this.sync) {
/*  476 */         this.list.addElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void addElements(long index, int[][] a) {
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
/*      */     public IntBigListIterator iterator() {
/*  505 */       return this.list.listIterator();
/*      */     }
/*      */     
/*      */     public IntBigListIterator listIterator() {
/*  509 */       return this.list.listIterator();
/*      */     }
/*      */     
/*      */     public IntBigListIterator listIterator(long i) {
/*  513 */       return this.list.listIterator(i);
/*      */     }
/*      */     
/*      */     public IntBigList subList(long from, long to) {
/*  517 */       synchronized (this.sync) {
/*  518 */         return IntBigLists.synchronize(this.list.subList(from, to), this.sync);
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
/*      */     public int compareTo(BigList<? extends Integer> o) {
/*  537 */       synchronized (this.sync) {
/*  538 */         return this.list.compareTo(o);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, IntCollection c) {
/*  543 */       synchronized (this.sync) {
/*  544 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, IntBigList l) {
/*  549 */       synchronized (this.sync) {
/*  550 */         return this.list.addAll(index, l);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(IntBigList l) {
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
/*      */     public void add(long i, Integer k) {
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
/*      */     public Integer get(long i) {
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
/*      */     public Integer set(long index, Integer k) {
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
/*      */     public Integer remove(long i) {
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
/*      */   public static IntBigList synchronize(IntBigList l) {
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
/*      */   public static IntBigList synchronize(IntBigList l, Object sync) {
/*  657 */     return new SynchronizedBigList(l, sync);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBigList
/*      */     extends IntCollections.UnmodifiableCollection
/*      */     implements IntBigList, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final IntBigList list;
/*      */     
/*      */     protected UnmodifiableBigList(IntBigList l) {
/*  668 */       super(l);
/*  669 */       this.list = l;
/*      */     }
/*      */     
/*      */     public int getInt(long i) {
/*  673 */       return this.list.getInt(i);
/*      */     }
/*      */     
/*      */     public int set(long i, int k) {
/*  677 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(long i, int k) {
/*  681 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public int removeInt(long i) {
/*  685 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public long indexOf(int k) {
/*  689 */       return this.list.indexOf(k);
/*      */     }
/*      */     
/*      */     public long lastIndexOf(int k) {
/*  693 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Integer> c) {
/*  697 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void getElements(long from, int[][] a, long offset, long length) {
/*  701 */       this.list.getElements(from, a, offset, length);
/*      */     }
/*      */     
/*      */     public void removeElements(long from, long to) {
/*  705 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(long index, int[][] a, long offset, long length) {
/*  709 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(long index, int[][] a) {
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
/*      */     public IntBigListIterator iterator() {
/*  731 */       return listIterator();
/*      */     }
/*      */     
/*      */     public IntBigListIterator listIterator() {
/*  735 */       return IntBigListIterators.unmodifiable(this.list.listIterator());
/*      */     }
/*      */     
/*      */     public IntBigListIterator listIterator(long i) {
/*  739 */       return IntBigListIterators.unmodifiable(this.list.listIterator(i));
/*      */     }
/*      */     
/*      */     public IntBigList subList(long from, long to) {
/*  743 */       return IntBigLists.unmodifiable(this.list.subList(from, to));
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
/*      */     public int compareTo(BigList<? extends Integer> o) {
/*  757 */       return this.list.compareTo(o);
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, IntCollection c) {
/*  761 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(IntBigList l) {
/*  765 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, IntBigList l) {
/*  769 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer get(long i) {
/*  779 */       return this.list.get(i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void add(long i, Integer k) {
/*  789 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer set(long index, Integer k) {
/*  799 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer remove(long i) {
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
/*      */   public static IntBigList unmodifiable(IntBigList l) {
/*  842 */     return new UnmodifiableBigList(l);
/*      */   }
/*      */   
/*      */   public static class ListBigList extends AbstractIntBigList implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*      */     private final IntList list;
/*      */     
/*      */     protected ListBigList(IntList list) {
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
/*      */     public IntBigListIterator iterator() {
/*  866 */       return IntBigListIterators.asBigListIterator(this.list.iterator());
/*      */     }
/*      */     
/*      */     public IntBigListIterator listIterator() {
/*  870 */       return IntBigListIterators.asBigListIterator(this.list.listIterator());
/*      */     }
/*      */     
/*      */     public IntBigListIterator listIterator(long index) {
/*  874 */       return IntBigListIterators.asBigListIterator(this.list.listIterator(intIndex(index)));
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Integer> c) {
/*  878 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */     
/*      */     public IntBigList subList(long from, long to) {
/*  882 */       return new ListBigList(this.list.subList(intIndex(from), intIndex(to)));
/*      */     }
/*      */     
/*      */     public boolean contains(int key) {
/*  886 */       return this.list.contains(key);
/*      */     }
/*      */     
/*      */     public int[] toIntArray() {
/*  890 */       return this.list.toIntArray();
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
/*      */     public int[] toIntArray(int[] a) {
/*  905 */       return this.list.toArray(a);
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, IntCollection c) {
/*  909 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */     
/*      */     public boolean addAll(IntCollection c) {
/*  913 */       return this.list.addAll(c);
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, IntBigList c) {
/*  917 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */     
/*      */     public boolean addAll(IntBigList c) {
/*  921 */       return this.list.addAll(c);
/*      */     }
/*      */     
/*      */     public boolean containsAll(IntCollection c) {
/*  925 */       return this.list.containsAll(c);
/*      */     }
/*      */     
/*      */     public boolean removeAll(IntCollection c) {
/*  929 */       return this.list.removeAll(c);
/*      */     }
/*      */     
/*      */     public boolean retainAll(IntCollection c) {
/*  933 */       return this.list.retainAll(c);
/*      */     }
/*      */     
/*      */     public void add(long index, int key) {
/*  937 */       this.list.add(intIndex(index), key);
/*      */     }
/*      */     
/*      */     public boolean add(int key) {
/*  941 */       return this.list.add(key);
/*      */     }
/*      */     
/*      */     public int getInt(long index) {
/*  945 */       return this.list.getInt(intIndex(index));
/*      */     }
/*      */     
/*      */     public long indexOf(int k) {
/*  949 */       return this.list.indexOf(k);
/*      */     }
/*      */     
/*      */     public long lastIndexOf(int k) {
/*  953 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */     
/*      */     public int removeInt(long index) {
/*  957 */       return this.list.removeInt(intIndex(index));
/*      */     }
/*      */     
/*      */     public int set(long index, int k) {
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
/*      */     public boolean addAll(Collection<? extends Integer> c) {
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
/*      */   public static IntBigList asBigList(IntList list) {
/* 1004 */     return new ListBigList(list);
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntBigLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */