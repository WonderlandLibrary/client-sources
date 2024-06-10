/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
/*     */ import it.unimi.dsi.fastutil.BigList;
/*     */ import it.unimi.dsi.fastutil.BigListIterator;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
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
/*     */ public final class ObjectBigLists
/*     */ {
/*     */   public static <K> ObjectBigList<K> shuffle(ObjectBigList<K> l, Random random) {
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
/*     */     extends ObjectCollections.EmptyCollection<K>
/*     */     implements ObjectBigList<K>, Serializable, Cloneable
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
/*     */     public ObjectBigList<K> subList(long from, long to) {
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
/*     */     public int compareTo(BigList<? extends K> o) {
/* 149 */       if (o == this)
/* 150 */         return 0; 
/* 151 */       return o.isEmpty() ? 0 : -1;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 155 */       return ObjectBigLists.EMPTY_BIG_LIST;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 159 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 164 */       return (o instanceof BigList && ((BigList)o).isEmpty());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 168 */       return "[]";
/*     */     }
/*     */     private Object readResolve() {
/* 171 */       return ObjectBigLists.EMPTY_BIG_LIST;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 178 */   public static final EmptyBigList EMPTY_BIG_LIST = new EmptyBigList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ObjectBigList<K> emptyList() {
/* 189 */     return EMPTY_BIG_LIST;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends AbstractObjectBigList<K>
/*     */     implements Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     private final K element;
/*     */     
/*     */     protected Singleton(K element) {
/* 202 */       this.element = element;
/*     */     }
/*     */     
/*     */     public K get(long i) {
/* 206 */       if (i == 0L)
/* 207 */         return this.element; 
/* 208 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object k) {
/* 212 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public K remove(long i) {
/* 216 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/* 220 */       return Objects.equals(k, this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 225 */       Object[] a = new Object[1];
/* 226 */       a[0] = this.element;
/* 227 */       return a;
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator() {
/* 231 */       return ObjectBigListIterators.singleton(this.element);
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long i) {
/* 235 */       if (i > 1L || i < 0L)
/* 236 */         throw new IndexOutOfBoundsException(); 
/* 237 */       ObjectBigListIterator<K> l = listIterator();
/* 238 */       if (i == 1L)
/* 239 */         l.next(); 
/* 240 */       return l;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBigList<K> subList(long from, long to) {
/* 245 */       ensureIndex(from);
/* 246 */       ensureIndex(to);
/* 247 */       if (from > to) {
/* 248 */         throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */       }
/* 250 */       if (from != 0L || to != 1L)
/* 251 */         return ObjectBigLists.EMPTY_BIG_LIST; 
/* 252 */       return this;
/*     */     }
/*     */     
/*     */     public boolean addAll(long i, Collection<? extends K> c) {
/* 256 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends K> c) {
/* 260 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 264 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 268 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 272 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public long size64() {
/* 276 */       return 1L;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 280 */       return this;
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
/*     */   public static <K> ObjectBigList<K> singleton(K element) {
/* 292 */     return new Singleton<>(element);
/*     */   }
/*     */   
/*     */   public static class SynchronizedBigList<K>
/*     */     extends ObjectCollections.SynchronizedCollection<K>
/*     */     implements ObjectBigList<K>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ObjectBigList<K> list;
/*     */     
/*     */     protected SynchronizedBigList(ObjectBigList<K> l, Object sync) {
/* 303 */       super(l, sync);
/* 304 */       this.list = l;
/*     */     }
/*     */     protected SynchronizedBigList(ObjectBigList<K> l) {
/* 307 */       super(l);
/* 308 */       this.list = l;
/*     */     }
/*     */     
/*     */     public K get(long i) {
/* 312 */       synchronized (this.sync) {
/* 313 */         return (K)this.list.get(i);
/*     */       } 
/*     */     }
/*     */     
/*     */     public K set(long i, K k) {
/* 318 */       synchronized (this.sync) {
/* 319 */         return (K)this.list.set(i, k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void add(long i, K k) {
/* 324 */       synchronized (this.sync) {
/* 325 */         this.list.add(i, k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public K remove(long i) {
/* 330 */       synchronized (this.sync) {
/* 331 */         return (K)this.list.remove(i);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long indexOf(Object k) {
/* 336 */       synchronized (this.sync) {
/* 337 */         return this.list.indexOf(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long lastIndexOf(Object k) {
/* 342 */       synchronized (this.sync) {
/* 343 */         return this.list.lastIndexOf(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends K> c) {
/* 348 */       synchronized (this.sync) {
/* 349 */         return this.list.addAll(index, c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void getElements(long from, Object[][] a, long offset, long length) {
/* 354 */       synchronized (this.sync) {
/* 355 */         this.list.getElements(from, a, offset, length);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 360 */       synchronized (this.sync) {
/* 361 */         this.list.removeElements(from, to);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void addElements(long index, K[][] a, long offset, long length) {
/* 366 */       synchronized (this.sync) {
/* 367 */         this.list.addElements(index, a, offset, length);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void addElements(long index, K[][] a) {
/* 372 */       synchronized (this.sync) {
/* 373 */         this.list.addElements(index, a);
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
/* 384 */       synchronized (this.sync) {
/* 385 */         this.list.size(size);
/*     */       } 
/*     */     }
/*     */     
/*     */     public long size64() {
/* 390 */       synchronized (this.sync) {
/* 391 */         return this.list.size64();
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> iterator() {
/* 396 */       return this.list.listIterator();
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator() {
/* 400 */       return this.list.listIterator();
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long i) {
/* 404 */       return this.list.listIterator(i);
/*     */     }
/*     */     
/*     */     public ObjectBigList<K> subList(long from, long to) {
/* 408 */       synchronized (this.sync) {
/* 409 */         return ObjectBigLists.synchronize(this.list.subList(from, to), this.sync);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 414 */       if (o == this)
/* 415 */         return true; 
/* 416 */       synchronized (this.sync) {
/* 417 */         return this.list.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 422 */       synchronized (this.sync) {
/* 423 */         return this.list.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int compareTo(BigList<? extends K> o) {
/* 428 */       synchronized (this.sync) {
/* 429 */         return this.list.compareTo(o);
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
/*     */   public static <K> ObjectBigList<K> synchronize(ObjectBigList<K> l) {
/* 443 */     return new SynchronizedBigList<>(l);
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
/*     */   public static <K> ObjectBigList<K> synchronize(ObjectBigList<K> l, Object sync) {
/* 458 */     return new SynchronizedBigList<>(l, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableBigList<K>
/*     */     extends ObjectCollections.UnmodifiableCollection<K>
/*     */     implements ObjectBigList<K>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ObjectBigList<K> list;
/*     */     
/*     */     protected UnmodifiableBigList(ObjectBigList<K> l) {
/* 469 */       super(l);
/* 470 */       this.list = l;
/*     */     }
/*     */     
/*     */     public K get(long i) {
/* 474 */       return (K)this.list.get(i);
/*     */     }
/*     */     
/*     */     public K set(long i, K k) {
/* 478 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void add(long i, K k) {
/* 482 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public K remove(long i) {
/* 486 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public long indexOf(Object k) {
/* 490 */       return this.list.indexOf(k);
/*     */     }
/*     */     
/*     */     public long lastIndexOf(Object k) {
/* 494 */       return this.list.lastIndexOf(k);
/*     */     }
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends K> c) {
/* 498 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void getElements(long from, Object[][] a, long offset, long length) {
/* 502 */       this.list.getElements(from, a, offset, length);
/*     */     }
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 506 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void addElements(long index, K[][] a, long offset, long length) {
/* 510 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void addElements(long index, K[][] a) {
/* 514 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void size(long size) {
/* 524 */       this.list.size(size);
/*     */     }
/*     */     
/*     */     public long size64() {
/* 528 */       return this.list.size64();
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> iterator() {
/* 532 */       return listIterator();
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator() {
/* 536 */       return ObjectBigListIterators.unmodifiable(this.list.listIterator());
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long i) {
/* 540 */       return ObjectBigListIterators.unmodifiable(this.list.listIterator(i));
/*     */     }
/*     */     
/*     */     public ObjectBigList<K> subList(long from, long to) {
/* 544 */       return ObjectBigLists.unmodifiable(this.list.subList(from, to));
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 548 */       if (o == this)
/* 549 */         return true; 
/* 550 */       return this.list.equals(o);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 554 */       return this.list.hashCode();
/*     */     }
/*     */     
/*     */     public int compareTo(BigList<? extends K> o) {
/* 558 */       return this.list.compareTo(o);
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
/*     */   public static <K> ObjectBigList<K> unmodifiable(ObjectBigList<K> l) {
/* 571 */     return new UnmodifiableBigList<>(l);
/*     */   }
/*     */   
/*     */   public static class ListBigList<K> extends AbstractObjectBigList<K> implements Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*     */     private final ObjectList<K> list;
/*     */     
/*     */     protected ListBigList(ObjectList<K> list) {
/* 578 */       this.list = list;
/*     */     }
/*     */     private int intIndex(long index) {
/* 581 */       if (index >= 2147483647L)
/* 582 */         throw new IndexOutOfBoundsException("This big list is restricted to 32-bit indices"); 
/* 583 */       return (int)index;
/*     */     }
/*     */     
/*     */     public long size64() {
/* 587 */       return this.list.size();
/*     */     }
/*     */     
/*     */     public void size(long size) {
/* 591 */       this.list.size(intIndex(size));
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> iterator() {
/* 595 */       return ObjectBigListIterators.asBigListIterator(this.list.iterator());
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator() {
/* 599 */       return ObjectBigListIterators.asBigListIterator(this.list.listIterator());
/*     */     }
/*     */     
/*     */     public ObjectBigListIterator<K> listIterator(long index) {
/* 603 */       return ObjectBigListIterators.asBigListIterator(this.list.listIterator(intIndex(index)));
/*     */     }
/*     */     
/*     */     public boolean addAll(long index, Collection<? extends K> c) {
/* 607 */       return this.list.addAll(intIndex(index), c);
/*     */     }
/*     */     
/*     */     public ObjectBigList<K> subList(long from, long to) {
/* 611 */       return new ListBigList(this.list.subList(intIndex(from), intIndex(to)));
/*     */     }
/*     */     
/*     */     public boolean contains(Object key) {
/* 615 */       return this.list.contains(key);
/*     */     }
/*     */     
/*     */     public Object[] toArray() {
/* 619 */       return this.list.toArray();
/*     */     }
/*     */     
/*     */     public void removeElements(long from, long to) {
/* 623 */       this.list.removeElements(intIndex(from), intIndex(to));
/*     */     }
/*     */     
/*     */     public void add(long index, K key) {
/* 627 */       this.list.add(intIndex(index), key);
/*     */     }
/*     */     
/*     */     public boolean add(K key) {
/* 631 */       return this.list.add(key);
/*     */     }
/*     */     
/*     */     public K get(long index) {
/* 635 */       return this.list.get(intIndex(index));
/*     */     }
/*     */     
/*     */     public long indexOf(Object k) {
/* 639 */       return this.list.indexOf(k);
/*     */     }
/*     */     
/*     */     public long lastIndexOf(Object k) {
/* 643 */       return this.list.lastIndexOf(k);
/*     */     }
/*     */     
/*     */     public K remove(long index) {
/* 647 */       return this.list.remove(intIndex(index));
/*     */     }
/*     */     
/*     */     public K set(long index, K k) {
/* 651 */       return this.list.set(intIndex(index), k);
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 655 */       return this.list.isEmpty();
/*     */     }
/*     */     
/*     */     public <T> T[] toArray(T[] a) {
/* 659 */       return (T[])this.list.toArray((Object[])a);
/*     */     }
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 663 */       return this.list.containsAll(c);
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends K> c) {
/* 667 */       return this.list.addAll(c);
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 671 */       return this.list.removeAll(c);
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 675 */       return this.list.retainAll(c);
/*     */     }
/*     */     
/*     */     public void clear() {
/* 679 */       this.list.clear();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 683 */       return this.list.hashCode();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ObjectBigList<K> asBigList(ObjectList<K> list) {
/* 694 */     return new ListBigList<>(list);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectBigLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */