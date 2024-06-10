/*      */ package it.unimi.dsi.fastutil.shorts;
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
/*      */ public final class ShortBigLists
/*      */ {
/*      */   public static ShortBigList shuffle(ShortBigList l, Random random) {
/*   43 */     for (long i = l.size64(); i-- != 0L; ) {
/*   44 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/*   45 */       short t = l.getShort(i);
/*   46 */       l.set(i, l.getShort(p));
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
/*      */     extends ShortCollections.EmptyCollection
/*      */     implements ShortBigList, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public short getShort(long i) {
/*   68 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/*      */     public boolean rem(short k) {
/*   72 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public short removeShort(long i) {
/*   76 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(long index, short k) {
/*   80 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public short set(long index, short k) {
/*   84 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public long indexOf(short k) {
/*   88 */       return -1L;
/*      */     }
/*      */     
/*      */     public long lastIndexOf(short k) {
/*   92 */       return -1L;
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, Collection<? extends Short> c) {
/*   96 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(ShortCollection c) {
/*  100 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(ShortBigList c) {
/*  104 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, ShortCollection c) {
/*  108 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, ShortBigList c) {
/*  112 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void add(long index, Short k) {
/*  122 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean add(Short k) {
/*  132 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short get(long i) {
/*  142 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short set(long index, Short k) {
/*  152 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short remove(long k) {
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
/*      */     public ShortBigListIterator listIterator() {
/*  187 */       return ShortBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public ShortBigListIterator iterator() {
/*  192 */       return ShortBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public ShortBigListIterator listIterator(long i) {
/*  197 */       if (i == 0L)
/*  198 */         return ShortBigListIterators.EMPTY_BIG_LIST_ITERATOR; 
/*  199 */       throw new IndexOutOfBoundsException(String.valueOf(i));
/*      */     }
/*      */     
/*      */     public ShortBigList subList(long from, long to) {
/*  203 */       if (from == 0L && to == 0L)
/*  204 */         return this; 
/*  205 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/*      */     public void getElements(long from, short[][] a, long offset, long length) {
/*  209 */       BigArrays.ensureOffsetLength(a, offset, length);
/*  210 */       if (from != 0L)
/*  211 */         throw new IndexOutOfBoundsException(); 
/*      */     }
/*      */     
/*      */     public void removeElements(long from, long to) {
/*  215 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(long index, short[][] a, long offset, long length) {
/*  219 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(long index, short[][] a) {
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
/*      */     public int compareTo(BigList<? extends Short> o) {
/*  235 */       if (o == this)
/*  236 */         return 0; 
/*  237 */       return o.isEmpty() ? 0 : -1;
/*      */     }
/*      */     
/*      */     public Object clone() {
/*  241 */       return ShortBigLists.EMPTY_BIG_LIST;
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
/*  257 */       return ShortBigLists.EMPTY_BIG_LIST;
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
/*      */     extends AbstractShortBigList
/*      */     implements Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private final short element;
/*      */     
/*      */     protected Singleton(short element) {
/*  276 */       this.element = element;
/*      */     }
/*      */     
/*      */     public short getShort(long i) {
/*  280 */       if (i == 0L)
/*  281 */         return this.element; 
/*  282 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/*      */     public boolean rem(short k) {
/*  286 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public short removeShort(long i) {
/*  290 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean contains(short k) {
/*  294 */       return (k == this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public short[] toShortArray() {
/*  299 */       short[] a = new short[1];
/*  300 */       a[0] = this.element;
/*  301 */       return a;
/*      */     }
/*      */     
/*      */     public ShortBigListIterator listIterator() {
/*  305 */       return ShortBigListIterators.singleton(this.element);
/*      */     }
/*      */     
/*      */     public ShortBigListIterator listIterator(long i) {
/*  309 */       if (i > 1L || i < 0L)
/*  310 */         throw new IndexOutOfBoundsException(); 
/*  311 */       ShortBigListIterator l = listIterator();
/*  312 */       if (i == 1L)
/*  313 */         l.nextShort(); 
/*  314 */       return l;
/*      */     }
/*      */ 
/*      */     
/*      */     public ShortBigList subList(long from, long to) {
/*  319 */       ensureIndex(from);
/*  320 */       ensureIndex(to);
/*  321 */       if (from > to) {
/*  322 */         throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
/*      */       }
/*  324 */       if (from != 0L || to != 1L)
/*  325 */         return ShortBigLists.EMPTY_BIG_LIST; 
/*  326 */       return this;
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, Collection<? extends Short> c) {
/*  330 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(Collection<? extends Short> c) {
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
/*      */     public boolean addAll(ShortBigList c) {
/*  346 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, ShortBigList c) {
/*  350 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long i, ShortCollection c) {
/*  354 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(ShortCollection c) {
/*  358 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean removeAll(ShortCollection c) {
/*  362 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean retainAll(ShortCollection c) {
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
/*      */   public static ShortBigList singleton(short element) {
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
/*      */   public static ShortBigList singleton(Object element) {
/*  401 */     return new Singleton(((Short)element).shortValue());
/*      */   }
/*      */   
/*      */   public static class SynchronizedBigList
/*      */     extends ShortCollections.SynchronizedCollection
/*      */     implements ShortBigList, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final ShortBigList list;
/*      */     
/*      */     protected SynchronizedBigList(ShortBigList l, Object sync) {
/*  412 */       super(l, sync);
/*  413 */       this.list = l;
/*      */     }
/*      */     protected SynchronizedBigList(ShortBigList l) {
/*  416 */       super(l);
/*  417 */       this.list = l;
/*      */     }
/*      */     
/*      */     public short getShort(long i) {
/*  421 */       synchronized (this.sync) {
/*  422 */         return this.list.getShort(i);
/*      */       } 
/*      */     }
/*      */     
/*      */     public short set(long i, short k) {
/*  427 */       synchronized (this.sync) {
/*  428 */         return this.list.set(i, k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void add(long i, short k) {
/*  433 */       synchronized (this.sync) {
/*  434 */         this.list.add(i, k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public short removeShort(long i) {
/*  439 */       synchronized (this.sync) {
/*  440 */         return this.list.removeShort(i);
/*      */       } 
/*      */     }
/*      */     
/*      */     public long indexOf(short k) {
/*  445 */       synchronized (this.sync) {
/*  446 */         return this.list.indexOf(k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public long lastIndexOf(short k) {
/*  451 */       synchronized (this.sync) {
/*  452 */         return this.list.lastIndexOf(k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Short> c) {
/*  457 */       synchronized (this.sync) {
/*  458 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void getElements(long from, short[][] a, long offset, long length) {
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
/*      */     public void addElements(long index, short[][] a, long offset, long length) {
/*  475 */       synchronized (this.sync) {
/*  476 */         this.list.addElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void addElements(long index, short[][] a) {
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
/*      */     public ShortBigListIterator iterator() {
/*  505 */       return this.list.listIterator();
/*      */     }
/*      */     
/*      */     public ShortBigListIterator listIterator() {
/*  509 */       return this.list.listIterator();
/*      */     }
/*      */     
/*      */     public ShortBigListIterator listIterator(long i) {
/*  513 */       return this.list.listIterator(i);
/*      */     }
/*      */     
/*      */     public ShortBigList subList(long from, long to) {
/*  517 */       synchronized (this.sync) {
/*  518 */         return ShortBigLists.synchronize(this.list.subList(from, to), this.sync);
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
/*      */     public int compareTo(BigList<? extends Short> o) {
/*  537 */       synchronized (this.sync) {
/*  538 */         return this.list.compareTo(o);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, ShortCollection c) {
/*  543 */       synchronized (this.sync) {
/*  544 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, ShortBigList l) {
/*  549 */       synchronized (this.sync) {
/*  550 */         return this.list.addAll(index, l);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(ShortBigList l) {
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
/*      */     public void add(long i, Short k) {
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
/*      */     public Short get(long i) {
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
/*      */     public Short set(long index, Short k) {
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
/*      */     public Short remove(long i) {
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
/*      */   public static ShortBigList synchronize(ShortBigList l) {
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
/*      */   public static ShortBigList synchronize(ShortBigList l, Object sync) {
/*  657 */     return new SynchronizedBigList(l, sync);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBigList
/*      */     extends ShortCollections.UnmodifiableCollection
/*      */     implements ShortBigList, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final ShortBigList list;
/*      */     
/*      */     protected UnmodifiableBigList(ShortBigList l) {
/*  668 */       super(l);
/*  669 */       this.list = l;
/*      */     }
/*      */     
/*      */     public short getShort(long i) {
/*  673 */       return this.list.getShort(i);
/*      */     }
/*      */     
/*      */     public short set(long i, short k) {
/*  677 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(long i, short k) {
/*  681 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public short removeShort(long i) {
/*  685 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public long indexOf(short k) {
/*  689 */       return this.list.indexOf(k);
/*      */     }
/*      */     
/*      */     public long lastIndexOf(short k) {
/*  693 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Short> c) {
/*  697 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void getElements(long from, short[][] a, long offset, long length) {
/*  701 */       this.list.getElements(from, a, offset, length);
/*      */     }
/*      */     
/*      */     public void removeElements(long from, long to) {
/*  705 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(long index, short[][] a, long offset, long length) {
/*  709 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(long index, short[][] a) {
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
/*      */     public ShortBigListIterator iterator() {
/*  731 */       return listIterator();
/*      */     }
/*      */     
/*      */     public ShortBigListIterator listIterator() {
/*  735 */       return ShortBigListIterators.unmodifiable(this.list.listIterator());
/*      */     }
/*      */     
/*      */     public ShortBigListIterator listIterator(long i) {
/*  739 */       return ShortBigListIterators.unmodifiable(this.list.listIterator(i));
/*      */     }
/*      */     
/*      */     public ShortBigList subList(long from, long to) {
/*  743 */       return ShortBigLists.unmodifiable(this.list.subList(from, to));
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
/*      */     public int compareTo(BigList<? extends Short> o) {
/*  757 */       return this.list.compareTo(o);
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, ShortCollection c) {
/*  761 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(ShortBigList l) {
/*  765 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, ShortBigList l) {
/*  769 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short get(long i) {
/*  779 */       return this.list.get(i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void add(long i, Short k) {
/*  789 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short set(long index, Short k) {
/*  799 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short remove(long i) {
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
/*      */   public static ShortBigList unmodifiable(ShortBigList l) {
/*  842 */     return new UnmodifiableBigList(l);
/*      */   }
/*      */   
/*      */   public static class ListBigList extends AbstractShortBigList implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*      */     private final ShortList list;
/*      */     
/*      */     protected ListBigList(ShortList list) {
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
/*      */     public ShortBigListIterator iterator() {
/*  866 */       return ShortBigListIterators.asBigListIterator(this.list.iterator());
/*      */     }
/*      */     
/*      */     public ShortBigListIterator listIterator() {
/*  870 */       return ShortBigListIterators.asBigListIterator(this.list.listIterator());
/*      */     }
/*      */     
/*      */     public ShortBigListIterator listIterator(long index) {
/*  874 */       return ShortBigListIterators.asBigListIterator(this.list.listIterator(intIndex(index)));
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, Collection<? extends Short> c) {
/*  878 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */     
/*      */     public ShortBigList subList(long from, long to) {
/*  882 */       return new ListBigList(this.list.subList(intIndex(from), intIndex(to)));
/*      */     }
/*      */     
/*      */     public boolean contains(short key) {
/*  886 */       return this.list.contains(key);
/*      */     }
/*      */     
/*      */     public short[] toShortArray() {
/*  890 */       return this.list.toShortArray();
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
/*      */     public short[] toShortArray(short[] a) {
/*  905 */       return this.list.toArray(a);
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, ShortCollection c) {
/*  909 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */     
/*      */     public boolean addAll(ShortCollection c) {
/*  913 */       return this.list.addAll(c);
/*      */     }
/*      */     
/*      */     public boolean addAll(long index, ShortBigList c) {
/*  917 */       return this.list.addAll(intIndex(index), c);
/*      */     }
/*      */     
/*      */     public boolean addAll(ShortBigList c) {
/*  921 */       return this.list.addAll(c);
/*      */     }
/*      */     
/*      */     public boolean containsAll(ShortCollection c) {
/*  925 */       return this.list.containsAll(c);
/*      */     }
/*      */     
/*      */     public boolean removeAll(ShortCollection c) {
/*  929 */       return this.list.removeAll(c);
/*      */     }
/*      */     
/*      */     public boolean retainAll(ShortCollection c) {
/*  933 */       return this.list.retainAll(c);
/*      */     }
/*      */     
/*      */     public void add(long index, short key) {
/*  937 */       this.list.add(intIndex(index), key);
/*      */     }
/*      */     
/*      */     public boolean add(short key) {
/*  941 */       return this.list.add(key);
/*      */     }
/*      */     
/*      */     public short getShort(long index) {
/*  945 */       return this.list.getShort(intIndex(index));
/*      */     }
/*      */     
/*      */     public long indexOf(short k) {
/*  949 */       return this.list.indexOf(k);
/*      */     }
/*      */     
/*      */     public long lastIndexOf(short k) {
/*  953 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */     
/*      */     public short removeShort(long index) {
/*  957 */       return this.list.removeShort(intIndex(index));
/*      */     }
/*      */     
/*      */     public short set(long index, short k) {
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
/*      */     public boolean addAll(Collection<? extends Short> c) {
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
/*      */   public static ShortBigList asBigList(ShortList list) {
/* 1004 */     return new ListBigList(list);
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\ShortBigLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */