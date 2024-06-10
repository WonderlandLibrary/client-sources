/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Random;
/*     */ import java.util.RandomAccess;
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
/*     */ public final class ReferenceLists
/*     */ {
/*     */   public static <K> ReferenceList<K> shuffle(ReferenceList<K> l, Random random) {
/*  41 */     for (int i = l.size(); i-- != 0; ) {
/*  42 */       int p = random.nextInt(i + 1);
/*  43 */       K t = l.get(i);
/*  44 */       l.set(i, l.get(p));
/*  45 */       l.set(p, t);
/*     */     } 
/*  47 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyList<K>
/*     */     extends ReferenceCollections.EmptyCollection<K>
/*     */     implements ReferenceList<K>, RandomAccess, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public K get(int i) {
/*  67 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object k) {
/*  71 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public K remove(int i) {
/*  75 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void add(int index, K k) {
/*  79 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public K set(int index, K k) {
/*  83 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int indexOf(Object k) {
/*  87 */       return -1;
/*     */     }
/*     */     
/*     */     public int lastIndexOf(Object k) {
/*  91 */       return -1;
/*     */     }
/*     */     
/*     */     public boolean addAll(int i, Collection<? extends K> c) {
/*  95 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void sort(Comparator<? super K> comparator) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void unstableSort(Comparator<? super K> comparator) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectListIterator<K> listIterator() {
/* 109 */       return ObjectIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectListIterator<K> iterator() {
/* 114 */       return ObjectIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectListIterator<K> listIterator(int i) {
/* 119 */       if (i == 0)
/* 120 */         return ObjectIterators.EMPTY_ITERATOR; 
/* 121 */       throw new IndexOutOfBoundsException(String.valueOf(i));
/*     */     }
/*     */     
/*     */     public ReferenceList<K> subList(int from, int to) {
/* 125 */       if (from == 0 && to == 0)
/* 126 */         return this; 
/* 127 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */     
/*     */     public void getElements(int from, Object[] a, int offset, int length) {
/* 131 */       if (from == 0 && length == 0 && offset >= 0 && offset <= a.length)
/*     */         return; 
/* 133 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */     
/*     */     public void removeElements(int from, int to) {
/* 137 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void addElements(int index, K[] a, int offset, int length) {
/* 141 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void addElements(int index, K[] a) {
/* 145 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void setElements(K[] a) {
/* 149 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void setElements(int index, K[] a) {
/* 153 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void setElements(int index, K[] a, int offset, int length) {
/* 157 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void size(int s) {
/* 161 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 165 */       return ReferenceLists.EMPTY_LIST;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 169 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 174 */       return (o instanceof List && ((List)o).isEmpty());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 178 */       return "[]";
/*     */     }
/*     */     private Object readResolve() {
/* 181 */       return ReferenceLists.EMPTY_LIST;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 188 */   public static final EmptyList EMPTY_LIST = new EmptyList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceList<K> emptyList() {
/* 199 */     return EMPTY_LIST;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends AbstractReferenceList<K>
/*     */     implements RandomAccess, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */     
/*     */     private final K element;
/*     */ 
/*     */ 
/*     */     
/*     */     protected Singleton(K element) {
/* 216 */       this.element = element;
/*     */     }
/*     */     
/*     */     public K get(int i) {
/* 220 */       if (i == 0)
/* 221 */         return this.element; 
/* 222 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object k) {
/* 226 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public K remove(int i) {
/* 230 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/* 234 */       return (k == this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 239 */       Object[] a = new Object[1];
/* 240 */       a[0] = this.element;
/* 241 */       return a;
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> listIterator() {
/* 245 */       return ObjectIterators.singleton(this.element);
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> iterator() {
/* 249 */       return listIterator();
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> listIterator(int i) {
/* 253 */       if (i > 1 || i < 0)
/* 254 */         throw new IndexOutOfBoundsException(); 
/* 255 */       ObjectListIterator<K> l = listIterator();
/* 256 */       if (i == 1)
/* 257 */         l.next(); 
/* 258 */       return l;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceList<K> subList(int from, int to) {
/* 263 */       ensureIndex(from);
/* 264 */       ensureIndex(to);
/* 265 */       if (from > to) {
/* 266 */         throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */       }
/* 268 */       if (from != 0 || to != 1)
/* 269 */         return ReferenceLists.EMPTY_LIST; 
/* 270 */       return this;
/*     */     }
/*     */     
/*     */     public boolean addAll(int i, Collection<? extends K> c) {
/* 274 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends K> c) {
/* 278 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 282 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 286 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void sort(Comparator<? super K> comparator) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void unstableSort(Comparator<? super K> comparator) {}
/*     */ 
/*     */     
/*     */     public void removeElements(int from, int to) {
/* 299 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void addElements(int index, K[] a) {
/* 303 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void addElements(int index, K[] a, int offset, int length) {
/* 307 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void setElements(K[] a) {
/* 311 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void setElements(int index, K[] a) {
/* 315 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void setElements(int index, K[] a, int offset, int length) {
/* 319 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int size() {
/* 323 */       return 1;
/*     */     }
/*     */     
/*     */     public void size(int size) {
/* 327 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 331 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 335 */       return this;
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
/*     */   public static <K> ReferenceList<K> singleton(K element) {
/* 347 */     return new Singleton<>(element);
/*     */   }
/*     */   
/*     */   public static class SynchronizedList<K>
/*     */     extends ReferenceCollections.SynchronizedCollection<K>
/*     */     implements ReferenceList<K>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ReferenceList<K> list;
/*     */     
/*     */     protected SynchronizedList(ReferenceList<K> l, Object sync) {
/* 358 */       super(l, sync);
/* 359 */       this.list = l;
/*     */     }
/*     */     protected SynchronizedList(ReferenceList<K> l) {
/* 362 */       super(l);
/* 363 */       this.list = l;
/*     */     }
/*     */     
/*     */     public K get(int i) {
/* 367 */       synchronized (this.sync) {
/* 368 */         return this.list.get(i);
/*     */       } 
/*     */     }
/*     */     
/*     */     public K set(int i, K k) {
/* 373 */       synchronized (this.sync) {
/* 374 */         return this.list.set(i, k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void add(int i, K k) {
/* 379 */       synchronized (this.sync) {
/* 380 */         this.list.add(i, k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public K remove(int i) {
/* 385 */       synchronized (this.sync) {
/* 386 */         return this.list.remove(i);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int indexOf(Object k) {
/* 391 */       synchronized (this.sync) {
/* 392 */         return this.list.indexOf(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int lastIndexOf(Object k) {
/* 397 */       synchronized (this.sync) {
/* 398 */         return this.list.lastIndexOf(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean addAll(int index, Collection<? extends K> c) {
/* 403 */       synchronized (this.sync) {
/* 404 */         return this.list.addAll(index, c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void getElements(int from, Object[] a, int offset, int length) {
/* 409 */       synchronized (this.sync) {
/* 410 */         this.list.getElements(from, a, offset, length);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void removeElements(int from, int to) {
/* 415 */       synchronized (this.sync) {
/* 416 */         this.list.removeElements(from, to);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void addElements(int index, K[] a, int offset, int length) {
/* 421 */       synchronized (this.sync) {
/* 422 */         this.list.addElements(index, a, offset, length);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void addElements(int index, K[] a) {
/* 427 */       synchronized (this.sync) {
/* 428 */         this.list.addElements(index, a);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void setElements(K[] a) {
/* 433 */       synchronized (this.sync) {
/* 434 */         this.list.setElements(a);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void setElements(int index, K[] a) {
/* 439 */       synchronized (this.sync) {
/* 440 */         this.list.setElements(index, a);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void setElements(int index, K[] a, int offset, int length) {
/* 445 */       synchronized (this.sync) {
/* 446 */         this.list.setElements(index, a, offset, length);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void size(int size) {
/* 451 */       synchronized (this.sync) {
/* 452 */         this.list.size(size);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> listIterator() {
/* 457 */       return this.list.listIterator();
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> iterator() {
/* 461 */       return listIterator();
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> listIterator(int i) {
/* 465 */       return this.list.listIterator(i);
/*     */     }
/*     */     
/*     */     public ReferenceList<K> subList(int from, int to) {
/* 469 */       synchronized (this.sync) {
/* 470 */         return new SynchronizedList(this.list.subList(from, to), this.sync);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 475 */       if (o == this)
/* 476 */         return true; 
/* 477 */       synchronized (this.sync) {
/* 478 */         return this.collection.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 483 */       synchronized (this.sync) {
/* 484 */         return this.collection.hashCode();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void sort(Comparator<? super K> comparator) {
/* 490 */       synchronized (this.sync) {
/* 491 */         this.list.sort(comparator);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void unstableSort(Comparator<? super K> comparator) {
/* 497 */       synchronized (this.sync) {
/* 498 */         this.list.unstableSort(comparator);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 502 */       synchronized (this.sync) {
/* 503 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SynchronizedRandomAccessList<K>
/*     */     extends SynchronizedList<K>
/*     */     implements RandomAccess, Serializable {
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     protected SynchronizedRandomAccessList(ReferenceList<K> l, Object sync) {
/* 514 */       super(l, sync);
/*     */     }
/*     */     protected SynchronizedRandomAccessList(ReferenceList<K> l) {
/* 517 */       super(l);
/*     */     }
/*     */     
/*     */     public ReferenceList<K> subList(int from, int to) {
/* 521 */       synchronized (this.sync) {
/* 522 */         return new SynchronizedRandomAccessList(this.list.subList(from, to), this.sync);
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
/*     */   public static <K> ReferenceList<K> synchronize(ReferenceList<K> l) {
/* 536 */     return (l instanceof RandomAccess) ? new SynchronizedRandomAccessList<>(l) : new SynchronizedList<>(l);
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
/*     */   public static <K> ReferenceList<K> synchronize(ReferenceList<K> l, Object sync) {
/* 550 */     return (l instanceof RandomAccess) ? 
/* 551 */       new SynchronizedRandomAccessList<>(l, sync) : 
/* 552 */       new SynchronizedList<>(l, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableList<K>
/*     */     extends ReferenceCollections.UnmodifiableCollection<K>
/*     */     implements ReferenceList<K>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ReferenceList<K> list;
/*     */     
/*     */     protected UnmodifiableList(ReferenceList<K> l) {
/* 563 */       super(l);
/* 564 */       this.list = l;
/*     */     }
/*     */     
/*     */     public K get(int i) {
/* 568 */       return this.list.get(i);
/*     */     }
/*     */     
/*     */     public K set(int i, K k) {
/* 572 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void add(int i, K k) {
/* 576 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public K remove(int i) {
/* 580 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int indexOf(Object k) {
/* 584 */       return this.list.indexOf(k);
/*     */     }
/*     */     
/*     */     public int lastIndexOf(Object k) {
/* 588 */       return this.list.lastIndexOf(k);
/*     */     }
/*     */     
/*     */     public boolean addAll(int index, Collection<? extends K> c) {
/* 592 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void getElements(int from, Object[] a, int offset, int length) {
/* 596 */       this.list.getElements(from, a, offset, length);
/*     */     }
/*     */     
/*     */     public void removeElements(int from, int to) {
/* 600 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void addElements(int index, K[] a, int offset, int length) {
/* 604 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void addElements(int index, K[] a) {
/* 608 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void setElements(K[] a) {
/* 612 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void setElements(int index, K[] a) {
/* 616 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void setElements(int index, K[] a, int offset, int length) {
/* 620 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void size(int size) {
/* 624 */       this.list.size(size);
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> listIterator() {
/* 628 */       return ObjectIterators.unmodifiable(this.list.listIterator());
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> iterator() {
/* 632 */       return listIterator();
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> listIterator(int i) {
/* 636 */       return ObjectIterators.unmodifiable(this.list.listIterator(i));
/*     */     }
/*     */     
/*     */     public ReferenceList<K> subList(int from, int to) {
/* 640 */       return new UnmodifiableList(this.list.subList(from, to));
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 644 */       if (o == this)
/* 645 */         return true; 
/* 646 */       return this.collection.equals(o);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 650 */       return this.collection.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public void sort(Comparator<? super K> comparator) {
/* 655 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void unstableSort(Comparator<? super K> comparator) {
/* 660 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class UnmodifiableRandomAccessList<K>
/*     */     extends UnmodifiableList<K>
/*     */     implements RandomAccess, Serializable {
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     protected UnmodifiableRandomAccessList(ReferenceList<K> l) {
/* 670 */       super(l);
/*     */     }
/*     */     
/*     */     public ReferenceList<K> subList(int from, int to) {
/* 674 */       return new UnmodifiableRandomAccessList(this.list.subList(from, to));
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
/*     */   public static <K> ReferenceList<K> unmodifiable(ReferenceList<K> l) {
/* 687 */     return (l instanceof RandomAccess) ? new UnmodifiableRandomAccessList<>(l) : new UnmodifiableList<>(l);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ReferenceLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */