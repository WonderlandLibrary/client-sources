/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
/*     */ import it.unimi.dsi.fastutil.BigList;
/*     */ import it.unimi.dsi.fastutil.BigListIterator;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ReferenceBigLists
/*     */ {
/*     */   public static <K> ReferenceBigList<K> shuffle(ReferenceBigList<K> l, Random random) {
/*  43 */     for (long i = l.size64(); i-- != 0L; ) {
/*  44 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/*  45 */       K t = (K)l.get(i);
/*  46 */       l.set(i, l.get(p));
/*  47 */       l.set(p, t);
/*     */     } 
/*  49 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyBigList<K>
/*     */     extends ReferenceCollections.EmptyCollection<K>
/*     */     implements ReferenceBigList<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public K get(long i) {
/*  68 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object k) {
/*  72 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public K remove(long i) {
/*  76 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void add(long index, K k) {
/*  80 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public K set(long index, K k) {
/*  84 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public long indexOf(Object k) {
/*  88 */       return -1L;
/*     */     }
/*     */     
/*     */     public long lastIndexOf(Object k) {
/*  92 */       return -1L;
/*     */     }
/*     */     
/*     */     public boolean addAll(long i, Collection<? extends K> c) {
/*  96 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator() {
/* 101 */       return ObjectBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> iterator() {
/* 106 */       return ObjectBigListIterators.EMPTY_BIG_LIST_ITERATOR;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long i) {
/* 111 */       if (i == 0L)
/* 112 */         return ObjectBigListIterators.EMPTY_BIG_LIST_ITERATOR; 
/* 113 */       throw new IndexOutOfBoundsException(String.valueOf(i));
/*     */     }
/*     */     
/*     */     public ReferenceBigList<K> subList(long from, long to) {
/* 117 */       if (from == 0L && to == 0L)
/* 118 */         return this; 
/* 119 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */     
/*     */     public void getElements(long from, Object[][] a, long offset, long length) {
/* 123 */       BigArrays.ensureOffsetLength(a, offset, length);
/* 124 */       if (from != 0L)
/* 125 */         throw new IndexOutOfBoundsException(); 
/*     */     }
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 129 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void addElements(long index, K[][] a, long offset, long length) {
/* 133 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void addElements(long index, K[][] a) {
/* 137 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void size(long s) {
/* 141 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public long size64() {
/* 145 */       return 0L;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 149 */       return ReferenceBigLists.EMPTY_BIG_LIST;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 153 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 158 */       return (o instanceof BigList && ((BigList)o).isEmpty());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 162 */       return "[]";
/*     */     }
/*     */     private Object readResolve() {
/* 165 */       return ReferenceBigLists.EMPTY_BIG_LIST;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 172 */   public static final EmptyBigList EMPTY_BIG_LIST = new EmptyBigList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceBigList<K> emptyList() {
/* 183 */     return EMPTY_BIG_LIST;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends AbstractReferenceBigList<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     private final K element;
/*     */     
/*     */     protected Singleton(K element) {
/* 196 */       this.element = element;
/*     */     }
/*     */     
/*     */     public K get(long i) {
/* 200 */       if (i == 0L)
/* 201 */         return this.element; 
/* 202 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object k) {
/* 206 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public K remove(long i) {
/* 210 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/* 214 */       return (k == this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 219 */       Object[] a = new Object[1];
/* 220 */       a[0] = this.element;
/* 221 */       return a;
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator() {
/* 225 */       return ObjectBigListIterators.singleton(this.element);
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long i) {
/* 229 */       if (i > 1L || i < 0L)
/* 230 */         throw new IndexOutOfBoundsException(); 
/* 231 */       ObjectBigListIterator<K> l = listIterator();
/* 232 */       if (i == 1L)
/* 233 */         l.next(); 
/* 234 */       return l;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceBigList<K> subList(long from, long to) {
/* 239 */       ensureIndex(from);
/* 240 */       ensureIndex(to);
/* 241 */       if (from > to) {
/* 242 */         throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */       }
/* 244 */       if (from != 0L || to != 1L)
/* 245 */         return ReferenceBigLists.EMPTY_BIG_LIST; 
/* 246 */       return this;
/*     */     }
/*     */     
/*     */     public boolean addAll(long i, Collection<? extends K> c) {
/* 250 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends K> c) {
/* 254 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 258 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 262 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 266 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public long size64() {
/* 270 */       return 1L;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 274 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceBigList<K> singleton(K element) {
/* 286 */     return new Singleton<>(element);
/*     */   }
/*     */   
/*     */   public static class SynchronizedBigList<K>
/*     */     extends ReferenceCollections.SynchronizedCollection<K>
/*     */     implements ReferenceBigList<K>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ReferenceBigList<K> list;
/*     */     
/*     */     protected SynchronizedBigList(ReferenceBigList<K> l, Object sync) {
/* 297 */       super(l, sync);
/* 298 */       this.list = l;
/*     */     }
/*     */     protected SynchronizedBigList(ReferenceBigList<K> l) {
/* 301 */       super(l);
/* 302 */       this.list = l;
/*     */     }
/*     */     
/*     */     public K get(long i) {
/* 306 */       synchronized (this.sync) {
/* 307 */         return (K)this.list.get(i);
/*     */       } 
/*     */     }
/*     */     
/*     */     public K set(long i, K k) {
/* 312 */       synchronized (this.sync) {
/* 313 */         return (K)this.list.set(i, k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void add(long i, K k) {
/* 318 */       synchronized (this.sync) {
/* 319 */         this.list.add(i, k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public K remove(long i) {
/* 324 */       synchronized (this.sync) {
/* 325 */         return (K)this.list.remove(i);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long indexOf(Object k) {
/* 330 */       synchronized (this.sync) {
/* 331 */         return this.list.indexOf(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long lastIndexOf(Object k) {
/* 336 */       synchronized (this.sync) {
/* 337 */         return this.list.lastIndexOf(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends K> c) {
/* 342 */       synchronized (this.sync) {
/* 343 */         return this.list.addAll(index, c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void getElements(long from, Object[][] a, long offset, long length) {
/* 348 */       synchronized (this.sync) {
/* 349 */         this.list.getElements(from, a, offset, length);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 354 */       synchronized (this.sync) {
/* 355 */         this.list.removeElements(from, to);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void addElements(long index, K[][] a, long offset, long length) {
/* 360 */       synchronized (this.sync) {
/* 361 */         this.list.addElements(index, a, offset, length);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void addElements(long index, K[][] a) {
/* 366 */       synchronized (this.sync) {
/* 367 */         this.list.addElements(index, a);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void size(long size) {
/* 378 */       synchronized (this.sync) {
/* 379 */         this.list.size(size);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long size64() {
/* 384 */       synchronized (this.sync) {
/* 385 */         return this.list.size64();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> iterator() {
/* 390 */       return this.list.listIterator();
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator() {
/* 394 */       return this.list.listIterator();
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long i) {
/* 398 */       return this.list.listIterator(i);
/*     */     }
/*     */     
/*     */     public ReferenceBigList<K> subList(long from, long to) {
/* 402 */       synchronized (this.sync) {
/* 403 */         return ReferenceBigLists.synchronize(this.list.subList(from, to), this.sync);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 408 */       if (o == this)
/* 409 */         return true; 
/* 410 */       synchronized (this.sync) {
/* 411 */         return this.list.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 416 */       synchronized (this.sync) {
/* 417 */         return this.list.hashCode();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceBigList<K> synchronize(ReferenceBigList<K> l) {
/* 431 */     return new SynchronizedBigList<>(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceBigList<K> synchronize(ReferenceBigList<K> l, Object sync) {
/* 446 */     return new SynchronizedBigList<>(l, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableBigList<K>
/*     */     extends ReferenceCollections.UnmodifiableCollection<K>
/*     */     implements ReferenceBigList<K>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ReferenceBigList<K> list;
/*     */     
/*     */     protected UnmodifiableBigList(ReferenceBigList<K> l) {
/* 457 */       super(l);
/* 458 */       this.list = l;
/*     */     }
/*     */     
/*     */     public K get(long i) {
/* 462 */       return (K)this.list.get(i);
/*     */     }
/*     */     
/*     */     public K set(long i, K k) {
/* 466 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void add(long i, K k) {
/* 470 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public K remove(long i) {
/* 474 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public long indexOf(Object k) {
/* 478 */       return this.list.indexOf(k);
/*     */     }
/*     */     
/*     */     public long lastIndexOf(Object k) {
/* 482 */       return this.list.lastIndexOf(k);
/*     */     }
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends K> c) {
/* 486 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void getElements(long from, Object[][] a, long offset, long length) {
/* 490 */       this.list.getElements(from, a, offset, length);
/*     */     }
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 494 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void addElements(long index, K[][] a, long offset, long length) {
/* 498 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void addElements(long index, K[][] a) {
/* 502 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void size(long size) {
/* 512 */       this.list.size(size);
/*     */     }
/*     */     
/*     */     public long size64() {
/* 516 */       return this.list.size64();
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> iterator() {
/* 520 */       return listIterator();
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator() {
/* 524 */       return ObjectBigListIterators.unmodifiable(this.list.listIterator());
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long i) {
/* 528 */       return ObjectBigListIterators.unmodifiable(this.list.listIterator(i));
/*     */     }
/*     */     
/*     */     public ReferenceBigList<K> subList(long from, long to) {
/* 532 */       return ReferenceBigLists.unmodifiable(this.list.subList(from, to));
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 536 */       if (o == this)
/* 537 */         return true; 
/* 538 */       return this.list.equals(o);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 542 */       return this.list.hashCode();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceBigList<K> unmodifiable(ReferenceBigList<K> l) {
/* 555 */     return new UnmodifiableBigList<>(l);
/*     */   }
/*     */   
/*     */   public static class ListBigList<K> extends AbstractReferenceBigList<K> implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     private final ReferenceList<K> list;
/*     */     
/*     */     protected ListBigList(ReferenceList<K> list) {
/* 562 */       this.list = list;
/*     */     }
/*     */     private int intIndex(long index) {
/* 565 */       if (index >= 2147483647L)
/* 566 */         throw new IndexOutOfBoundsException("This big list is restricted to 32-bit indices"); 
/* 567 */       return (int)index;
/*     */     }
/*     */     
/*     */     public long size64() {
/* 571 */       return this.list.size();
/*     */     }
/*     */     
/*     */     public void size(long size) {
/* 575 */       this.list.size(intIndex(size));
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> iterator() {
/* 579 */       return ObjectBigListIterators.asBigListIterator(this.list.iterator());
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator() {
/* 583 */       return ObjectBigListIterators.asBigListIterator(this.list.listIterator());
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long index) {
/* 587 */       return ObjectBigListIterators.asBigListIterator(this.list.listIterator(intIndex(index)));
/*     */     }
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends K> c) {
/* 591 */       return this.list.addAll(intIndex(index), c);
/*     */     }
/*     */     
/*     */     public ReferenceBigList<K> subList(long from, long to) {
/* 595 */       return new ListBigList(this.list.subList(intIndex(from), intIndex(to)));
/*     */     }
/*     */     
/*     */     public boolean contains(Object key) {
/* 599 */       return this.list.contains(key);
/*     */     }
/*     */     
/*     */     public Object[] toArray() {
/* 603 */       return this.list.toArray();
/*     */     }
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 607 */       this.list.removeElements(intIndex(from), intIndex(to));
/*     */     }
/*     */     
/*     */     public void add(long index, K key) {
/* 611 */       this.list.add(intIndex(index), key);
/*     */     }
/*     */     
/*     */     public boolean add(K key) {
/* 615 */       return this.list.add(key);
/*     */     }
/*     */     
/*     */     public K get(long index) {
/* 619 */       return this.list.get(intIndex(index));
/*     */     }
/*     */     
/*     */     public long indexOf(Object k) {
/* 623 */       return this.list.indexOf(k);
/*     */     }
/*     */     
/*     */     public long lastIndexOf(Object k) {
/* 627 */       return this.list.lastIndexOf(k);
/*     */     }
/*     */     
/*     */     public K remove(long index) {
/* 631 */       return this.list.remove(intIndex(index));
/*     */     }
/*     */     
/*     */     public K set(long index, K k) {
/* 635 */       return this.list.set(intIndex(index), k);
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 639 */       return this.list.isEmpty();
/*     */     }
/*     */     
/*     */     public <T> T[] toArray(T[] a) {
/* 643 */       return (T[])this.list.toArray((Object[])a);
/*     */     }
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 647 */       return this.list.containsAll(c);
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends K> c) {
/* 651 */       return this.list.addAll(c);
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 655 */       return this.list.removeAll(c);
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 659 */       return this.list.retainAll(c);
/*     */     }
/*     */     
/*     */     public void clear() {
/* 663 */       this.list.clear();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 667 */       return this.list.hashCode();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceBigList<K> asBigList(ReferenceList<K> list) {
/* 678 */     return new ListBigList<>(list);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ReferenceBigLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */