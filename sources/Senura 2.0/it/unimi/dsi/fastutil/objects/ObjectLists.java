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
/*     */ import java.util.Objects;
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
/*     */ public final class ObjectLists
/*     */ {
/*     */   public static <K> ObjectList<K> shuffle(ObjectList<K> l, Random random) {
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
/*     */     extends ObjectCollections.EmptyCollection<K>
/*     */     implements ObjectList<K>, RandomAccess, Serializable, Cloneable
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
/*     */     public ObjectList<K> subList(int from, int to) {
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
/*     */     public int compareTo(List<? extends K> o) {
/* 165 */       if (o == this)
/* 166 */         return 0; 
/* 167 */       return o.isEmpty() ? 0 : -1;
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 171 */       return ObjectLists.EMPTY_LIST;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 175 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 180 */       return (o instanceof List && ((List)o).isEmpty());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 184 */       return "[]";
/*     */     }
/*     */     private Object readResolve() {
/* 187 */       return ObjectLists.EMPTY_LIST;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 194 */   public static final EmptyList EMPTY_LIST = new EmptyList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ObjectList<K> emptyList() {
/* 205 */     return EMPTY_LIST;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends AbstractObjectList<K>
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
/* 222 */       this.element = element;
/*     */     }
/*     */     
/*     */     public K get(int i) {
/* 226 */       if (i == 0)
/* 227 */         return this.element; 
/* 228 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */     
/*     */     public boolean remove(Object k) {
/* 232 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public K remove(int i) {
/* 236 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean contains(Object k) {
/* 240 */       return Objects.equals(k, this.element);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 245 */       Object[] a = new Object[1];
/* 246 */       a[0] = this.element;
/* 247 */       return a;
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> listIterator() {
/* 251 */       return ObjectIterators.singleton(this.element);
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> iterator() {
/* 255 */       return listIterator();
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> listIterator(int i) {
/* 259 */       if (i > 1 || i < 0)
/* 260 */         throw new IndexOutOfBoundsException(); 
/* 261 */       ObjectListIterator<K> l = listIterator();
/* 262 */       if (i == 1)
/* 263 */         l.next(); 
/* 264 */       return l;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectList<K> subList(int from, int to) {
/* 269 */       ensureIndex(from);
/* 270 */       ensureIndex(to);
/* 271 */       if (from > to) {
/* 272 */         throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */       }
/* 274 */       if (from != 0 || to != 1)
/* 275 */         return ObjectLists.EMPTY_LIST; 
/* 276 */       return this;
/*     */     }
/*     */     
/*     */     public boolean addAll(int i, Collection<? extends K> c) {
/* 280 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends K> c) {
/* 284 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 288 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 292 */       throw new UnsupportedOperationException();
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
/* 305 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void addElements(int index, K[] a) {
/* 309 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void addElements(int index, K[] a, int offset, int length) {
/* 313 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void setElements(K[] a) {
/* 317 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void setElements(int index, K[] a) {
/* 321 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void setElements(int index, K[] a, int offset, int length) {
/* 325 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int size() {
/* 329 */       return 1;
/*     */     }
/*     */     
/*     */     public void size(int size) {
/* 333 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 337 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public Object clone() {
/* 341 */       return this;
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
/*     */   public static <K> ObjectList<K> singleton(K element) {
/* 353 */     return new Singleton<>(element);
/*     */   }
/*     */   
/*     */   public static class SynchronizedList<K>
/*     */     extends ObjectCollections.SynchronizedCollection<K>
/*     */     implements ObjectList<K>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ObjectList<K> list;
/*     */     
/*     */     protected SynchronizedList(ObjectList<K> l, Object sync) {
/* 364 */       super(l, sync);
/* 365 */       this.list = l;
/*     */     }
/*     */     protected SynchronizedList(ObjectList<K> l) {
/* 368 */       super(l);
/* 369 */       this.list = l;
/*     */     }
/*     */     
/*     */     public K get(int i) {
/* 373 */       synchronized (this.sync) {
/* 374 */         return this.list.get(i);
/*     */       } 
/*     */     }
/*     */     
/*     */     public K set(int i, K k) {
/* 379 */       synchronized (this.sync) {
/* 380 */         return this.list.set(i, k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void add(int i, K k) {
/* 385 */       synchronized (this.sync) {
/* 386 */         this.list.add(i, k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public K remove(int i) {
/* 391 */       synchronized (this.sync) {
/* 392 */         return this.list.remove(i);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int indexOf(Object k) {
/* 397 */       synchronized (this.sync) {
/* 398 */         return this.list.indexOf(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int lastIndexOf(Object k) {
/* 403 */       synchronized (this.sync) {
/* 404 */         return this.list.lastIndexOf(k);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean addAll(int index, Collection<? extends K> c) {
/* 409 */       synchronized (this.sync) {
/* 410 */         return this.list.addAll(index, c);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void getElements(int from, Object[] a, int offset, int length) {
/* 415 */       synchronized (this.sync) {
/* 416 */         this.list.getElements(from, a, offset, length);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void removeElements(int from, int to) {
/* 421 */       synchronized (this.sync) {
/* 422 */         this.list.removeElements(from, to);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void addElements(int index, K[] a, int offset, int length) {
/* 427 */       synchronized (this.sync) {
/* 428 */         this.list.addElements(index, a, offset, length);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void addElements(int index, K[] a) {
/* 433 */       synchronized (this.sync) {
/* 434 */         this.list.addElements(index, a);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void setElements(K[] a) {
/* 439 */       synchronized (this.sync) {
/* 440 */         this.list.setElements(a);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void setElements(int index, K[] a) {
/* 445 */       synchronized (this.sync) {
/* 446 */         this.list.setElements(index, a);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void setElements(int index, K[] a, int offset, int length) {
/* 451 */       synchronized (this.sync) {
/* 452 */         this.list.setElements(index, a, offset, length);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void size(int size) {
/* 457 */       synchronized (this.sync) {
/* 458 */         this.list.size(size);
/*     */       } 
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> listIterator() {
/* 463 */       return this.list.listIterator();
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> iterator() {
/* 467 */       return listIterator();
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> listIterator(int i) {
/* 471 */       return this.list.listIterator(i);
/*     */     }
/*     */     
/*     */     public ObjectList<K> subList(int from, int to) {
/* 475 */       synchronized (this.sync) {
/* 476 */         return new SynchronizedList(this.list.subList(from, to), this.sync);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 481 */       if (o == this)
/* 482 */         return true; 
/* 483 */       synchronized (this.sync) {
/* 484 */         return this.collection.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 489 */       synchronized (this.sync) {
/* 490 */         return this.collection.hashCode();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int compareTo(List<? extends K> o) {
/* 495 */       synchronized (this.sync) {
/* 496 */         return this.list.compareTo(o);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void sort(Comparator<? super K> comparator) {
/* 502 */       synchronized (this.sync) {
/* 503 */         this.list.sort(comparator);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void unstableSort(Comparator<? super K> comparator) {
/* 509 */       synchronized (this.sync) {
/* 510 */         this.list.unstableSort(comparator);
/*     */       } 
/*     */     }
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 514 */       synchronized (this.sync) {
/* 515 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SynchronizedRandomAccessList<K>
/*     */     extends SynchronizedList<K>
/*     */     implements RandomAccess, Serializable {
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     protected SynchronizedRandomAccessList(ObjectList<K> l, Object sync) {
/* 526 */       super(l, sync);
/*     */     }
/*     */     protected SynchronizedRandomAccessList(ObjectList<K> l) {
/* 529 */       super(l);
/*     */     }
/*     */     
/*     */     public ObjectList<K> subList(int from, int to) {
/* 533 */       synchronized (this.sync) {
/* 534 */         return new SynchronizedRandomAccessList(this.list.subList(from, to), this.sync);
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
/*     */   public static <K> ObjectList<K> synchronize(ObjectList<K> l) {
/* 548 */     return (l instanceof RandomAccess) ? new SynchronizedRandomAccessList<>(l) : new SynchronizedList<>(l);
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
/*     */   public static <K> ObjectList<K> synchronize(ObjectList<K> l, Object sync) {
/* 562 */     return (l instanceof RandomAccess) ? 
/* 563 */       new SynchronizedRandomAccessList<>(l, sync) : 
/* 564 */       new SynchronizedList<>(l, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableList<K>
/*     */     extends ObjectCollections.UnmodifiableCollection<K>
/*     */     implements ObjectList<K>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ObjectList<K> list;
/*     */     
/*     */     protected UnmodifiableList(ObjectList<K> l) {
/* 575 */       super(l);
/* 576 */       this.list = l;
/*     */     }
/*     */     
/*     */     public K get(int i) {
/* 580 */       return this.list.get(i);
/*     */     }
/*     */     
/*     */     public K set(int i, K k) {
/* 584 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void add(int i, K k) {
/* 588 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public K remove(int i) {
/* 592 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int indexOf(Object k) {
/* 596 */       return this.list.indexOf(k);
/*     */     }
/*     */     
/*     */     public int lastIndexOf(Object k) {
/* 600 */       return this.list.lastIndexOf(k);
/*     */     }
/*     */     
/*     */     public boolean addAll(int index, Collection<? extends K> c) {
/* 604 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void getElements(int from, Object[] a, int offset, int length) {
/* 608 */       this.list.getElements(from, a, offset, length);
/*     */     }
/*     */     
/*     */     public void removeElements(int from, int to) {
/* 612 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void addElements(int index, K[] a, int offset, int length) {
/* 616 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void addElements(int index, K[] a) {
/* 620 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void setElements(K[] a) {
/* 624 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void setElements(int index, K[] a) {
/* 628 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void setElements(int index, K[] a, int offset, int length) {
/* 632 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public void size(int size) {
/* 636 */       this.list.size(size);
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> listIterator() {
/* 640 */       return ObjectIterators.unmodifiable(this.list.listIterator());
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> iterator() {
/* 644 */       return listIterator();
/*     */     }
/*     */     
/*     */     public ObjectListIterator<K> listIterator(int i) {
/* 648 */       return ObjectIterators.unmodifiable(this.list.listIterator(i));
/*     */     }
/*     */     
/*     */     public ObjectList<K> subList(int from, int to) {
/* 652 */       return new UnmodifiableList(this.list.subList(from, to));
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 656 */       if (o == this)
/* 657 */         return true; 
/* 658 */       return this.collection.equals(o);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 662 */       return this.collection.hashCode();
/*     */     }
/*     */     
/*     */     public int compareTo(List<? extends K> o) {
/* 666 */       return this.list.compareTo(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public void sort(Comparator<? super K> comparator) {
/* 671 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void unstableSort(Comparator<? super K> comparator) {
/* 676 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class UnmodifiableRandomAccessList<K>
/*     */     extends UnmodifiableList<K>
/*     */     implements RandomAccess, Serializable {
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     protected UnmodifiableRandomAccessList(ObjectList<K> l) {
/* 686 */       super(l);
/*     */     }
/*     */     
/*     */     public ObjectList<K> subList(int from, int to) {
/* 690 */       return new UnmodifiableRandomAccessList(this.list.subList(from, to));
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
/*     */   public static <K> ObjectList<K> unmodifiable(ObjectList<K> l) {
/* 703 */     return (l instanceof RandomAccess) ? new UnmodifiableRandomAccessList<>(l) : new UnmodifiableList<>(l);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */