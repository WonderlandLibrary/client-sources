/*      */ package it.unimi.dsi.fastutil.chars;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Random;
/*      */ import java.util.RandomAccess;
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
/*      */ public final class CharLists
/*      */ {
/*      */   public static CharList shuffle(CharList l, Random random) {
/*   41 */     for (int i = l.size(); i-- != 0; ) {
/*   42 */       int p = random.nextInt(i + 1);
/*   43 */       char t = l.getChar(i);
/*   44 */       l.set(i, l.getChar(p));
/*   45 */       l.set(p, t);
/*      */     } 
/*   47 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class EmptyList
/*      */     extends CharCollections.EmptyCollection
/*      */     implements CharList, RandomAccess, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char getChar(int i) {
/*   67 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/*      */     public boolean rem(char k) {
/*   71 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public char removeChar(int i) {
/*   75 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(int index, char k) {
/*   79 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public char set(int index, char k) {
/*   83 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public int indexOf(char k) {
/*   87 */       return -1;
/*      */     }
/*      */     
/*      */     public int lastIndexOf(char k) {
/*   91 */       return -1;
/*      */     }
/*      */     
/*      */     public boolean addAll(int i, Collection<? extends Character> c) {
/*   95 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(CharList c) {
/*   99 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(int i, CharCollection c) {
/*  103 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(int i, CharList c) {
/*  107 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void add(int index, Character k) {
/*  118 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character get(int index) {
/*  129 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public boolean add(Character k) {
/*  140 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character set(int index, Character k) {
/*  151 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character remove(int k) {
/*  162 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public int indexOf(Object k) {
/*  173 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public int lastIndexOf(Object k) {
/*  184 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void sort(CharComparator comparator) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void unstableSort(CharComparator comparator) {}
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Character> comparator) {}
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Character> comparator) {}
/*      */ 
/*      */     
/*      */     public CharListIterator listIterator() {
/*  205 */       return CharIterators.EMPTY_ITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public CharListIterator iterator() {
/*  210 */       return CharIterators.EMPTY_ITERATOR;
/*      */     }
/*      */ 
/*      */     
/*      */     public CharListIterator listIterator(int i) {
/*  215 */       if (i == 0)
/*  216 */         return CharIterators.EMPTY_ITERATOR; 
/*  217 */       throw new IndexOutOfBoundsException(String.valueOf(i));
/*      */     }
/*      */     
/*      */     public CharList subList(int from, int to) {
/*  221 */       if (from == 0 && to == 0)
/*  222 */         return this; 
/*  223 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/*      */     public void getElements(int from, char[] a, int offset, int length) {
/*  227 */       if (from == 0 && length == 0 && offset >= 0 && offset <= a.length)
/*      */         return; 
/*  229 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/*      */     public void removeElements(int from, int to) {
/*  233 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(int index, char[] a, int offset, int length) {
/*  237 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(int index, char[] a) {
/*  241 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void setElements(char[] a) {
/*  245 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void setElements(int index, char[] a) {
/*  249 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void setElements(int index, char[] a, int offset, int length) {
/*  253 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void size(int s) {
/*  257 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public int compareTo(List<? extends Character> o) {
/*  261 */       if (o == this)
/*  262 */         return 0; 
/*  263 */       return o.isEmpty() ? 0 : -1;
/*      */     }
/*      */     
/*      */     public Object clone() {
/*  267 */       return CharLists.EMPTY_LIST;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  271 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  276 */       return (o instanceof List && ((List)o).isEmpty());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  280 */       return "[]";
/*      */     }
/*      */     private Object readResolve() {
/*  283 */       return CharLists.EMPTY_LIST;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  290 */   public static final EmptyList EMPTY_LIST = new EmptyList();
/*      */ 
/*      */   
/*      */   public static class Singleton
/*      */     extends AbstractCharList
/*      */     implements RandomAccess, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private final char element;
/*      */     
/*      */     protected Singleton(char element) {
/*  302 */       this.element = element;
/*      */     }
/*      */     
/*      */     public char getChar(int i) {
/*  306 */       if (i == 0)
/*  307 */         return this.element; 
/*  308 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/*      */     public boolean rem(char k) {
/*  312 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public char removeChar(int i) {
/*  316 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean contains(char k) {
/*  320 */       return (k == this.element);
/*      */     }
/*      */ 
/*      */     
/*      */     public char[] toCharArray() {
/*  325 */       char[] a = new char[1];
/*  326 */       a[0] = this.element;
/*  327 */       return a;
/*      */     }
/*      */     
/*      */     public CharListIterator listIterator() {
/*  331 */       return CharIterators.singleton(this.element);
/*      */     }
/*      */     
/*      */     public CharListIterator iterator() {
/*  335 */       return listIterator();
/*      */     }
/*      */     
/*      */     public CharListIterator listIterator(int i) {
/*  339 */       if (i > 1 || i < 0)
/*  340 */         throw new IndexOutOfBoundsException(); 
/*  341 */       CharListIterator l = listIterator();
/*  342 */       if (i == 1)
/*  343 */         l.nextChar(); 
/*  344 */       return l;
/*      */     }
/*      */ 
/*      */     
/*      */     public CharList subList(int from, int to) {
/*  349 */       ensureIndex(from);
/*  350 */       ensureIndex(to);
/*  351 */       if (from > to) {
/*  352 */         throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
/*      */       }
/*  354 */       if (from != 0 || to != 1)
/*  355 */         return CharLists.EMPTY_LIST; 
/*  356 */       return this;
/*      */     }
/*      */     
/*      */     public boolean addAll(int i, Collection<? extends Character> c) {
/*  360 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(Collection<? extends Character> c) {
/*  364 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean removeAll(Collection<?> c) {
/*  368 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean retainAll(Collection<?> c) {
/*  372 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(CharList c) {
/*  376 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(int i, CharList c) {
/*  380 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(int i, CharCollection c) {
/*  384 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(CharCollection c) {
/*  388 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean removeAll(CharCollection c) {
/*  392 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean retainAll(CharCollection c) {
/*  396 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void sort(CharComparator comparator) {}
/*      */ 
/*      */     
/*      */     public void unstableSort(CharComparator comparator) {}
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Character> comparator) {}
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Character> comparator) {}
/*      */ 
/*      */     
/*      */     public void removeElements(int from, int to) {
/*  416 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(int index, char[] a) {
/*  420 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(int index, char[] a, int offset, int length) {
/*  424 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void setElements(char[] a) {
/*  428 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void setElements(int index, char[] a) {
/*  432 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void setElements(int index, char[] a, int offset, int length) {
/*  436 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public int size() {
/*  440 */       return 1;
/*      */     }
/*      */     
/*      */     public void size(int size) {
/*  444 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void clear() {
/*  448 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Object clone() {
/*  452 */       return this;
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
/*      */   public static CharList singleton(char element) {
/*  464 */     return new Singleton(element);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharList singleton(Object element) {
/*  475 */     return new Singleton(((Character)element).charValue());
/*      */   }
/*      */   
/*      */   public static class SynchronizedList
/*      */     extends CharCollections.SynchronizedCollection
/*      */     implements CharList, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final CharList list;
/*      */     
/*      */     protected SynchronizedList(CharList l, Object sync) {
/*  486 */       super(l, sync);
/*  487 */       this.list = l;
/*      */     }
/*      */     protected SynchronizedList(CharList l) {
/*  490 */       super(l);
/*  491 */       this.list = l;
/*      */     }
/*      */     
/*      */     public char getChar(int i) {
/*  495 */       synchronized (this.sync) {
/*  496 */         return this.list.getChar(i);
/*      */       } 
/*      */     }
/*      */     
/*      */     public char set(int i, char k) {
/*  501 */       synchronized (this.sync) {
/*  502 */         return this.list.set(i, k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void add(int i, char k) {
/*  507 */       synchronized (this.sync) {
/*  508 */         this.list.add(i, k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public char removeChar(int i) {
/*  513 */       synchronized (this.sync) {
/*  514 */         return this.list.removeChar(i);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int indexOf(char k) {
/*  519 */       synchronized (this.sync) {
/*  520 */         return this.list.indexOf(k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int lastIndexOf(char k) {
/*  525 */       synchronized (this.sync) {
/*  526 */         return this.list.lastIndexOf(k);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(int index, Collection<? extends Character> c) {
/*  531 */       synchronized (this.sync) {
/*  532 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void getElements(int from, char[] a, int offset, int length) {
/*  537 */       synchronized (this.sync) {
/*  538 */         this.list.getElements(from, a, offset, length);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void removeElements(int from, int to) {
/*  543 */       synchronized (this.sync) {
/*  544 */         this.list.removeElements(from, to);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void addElements(int index, char[] a, int offset, int length) {
/*  549 */       synchronized (this.sync) {
/*  550 */         this.list.addElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void addElements(int index, char[] a) {
/*  555 */       synchronized (this.sync) {
/*  556 */         this.list.addElements(index, a);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void setElements(char[] a) {
/*  561 */       synchronized (this.sync) {
/*  562 */         this.list.setElements(a);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void setElements(int index, char[] a) {
/*  567 */       synchronized (this.sync) {
/*  568 */         this.list.setElements(index, a);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void setElements(int index, char[] a, int offset, int length) {
/*  573 */       synchronized (this.sync) {
/*  574 */         this.list.setElements(index, a, offset, length);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void size(int size) {
/*  579 */       synchronized (this.sync) {
/*  580 */         this.list.size(size);
/*      */       } 
/*      */     }
/*      */     
/*      */     public CharListIterator listIterator() {
/*  585 */       return this.list.listIterator();
/*      */     }
/*      */     
/*      */     public CharListIterator iterator() {
/*  589 */       return listIterator();
/*      */     }
/*      */     
/*      */     public CharListIterator listIterator(int i) {
/*  593 */       return this.list.listIterator(i);
/*      */     }
/*      */     
/*      */     public CharList subList(int from, int to) {
/*  597 */       synchronized (this.sync) {
/*  598 */         return new SynchronizedList(this.list.subList(from, to), this.sync);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean equals(Object o) {
/*  603 */       if (o == this)
/*  604 */         return true; 
/*  605 */       synchronized (this.sync) {
/*  606 */         return this.collection.equals(o);
/*      */       } 
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  611 */       synchronized (this.sync) {
/*  612 */         return this.collection.hashCode();
/*      */       } 
/*      */     }
/*      */     
/*      */     public int compareTo(List<? extends Character> o) {
/*  617 */       synchronized (this.sync) {
/*  618 */         return this.list.compareTo(o);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(int index, CharCollection c) {
/*  623 */       synchronized (this.sync) {
/*  624 */         return this.list.addAll(index, c);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(int index, CharList l) {
/*  629 */       synchronized (this.sync) {
/*  630 */         return this.list.addAll(index, l);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addAll(CharList l) {
/*  635 */       synchronized (this.sync) {
/*  636 */         return this.list.addAll(l);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character get(int i) {
/*  647 */       synchronized (this.sync) {
/*  648 */         return this.list.get(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void add(int i, Character k) {
/*  659 */       synchronized (this.sync) {
/*  660 */         this.list.add(i, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character set(int index, Character k) {
/*  671 */       synchronized (this.sync) {
/*  672 */         return this.list.set(index, k);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character remove(int i) {
/*  683 */       synchronized (this.sync) {
/*  684 */         return this.list.remove(i);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public int indexOf(Object o) {
/*  695 */       synchronized (this.sync) {
/*  696 */         return this.list.indexOf(o);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public int lastIndexOf(Object o) {
/*  707 */       synchronized (this.sync) {
/*  708 */         return this.list.lastIndexOf(o);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void sort(CharComparator comparator) {
/*  713 */       synchronized (this.sync) {
/*  714 */         this.list.sort(comparator);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void unstableSort(CharComparator comparator) {
/*  719 */       synchronized (this.sync) {
/*  720 */         this.list.unstableSort(comparator);
/*      */       } 
/*      */     }
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Character> comparator) {
/*  726 */       synchronized (this.sync) {
/*  727 */         this.list.sort(comparator);
/*      */       } 
/*      */     }
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Character> comparator) {
/*  733 */       synchronized (this.sync) {
/*  734 */         this.list.unstableSort(comparator);
/*      */       } 
/*      */     }
/*      */     private void writeObject(ObjectOutputStream s) throws IOException {
/*  738 */       synchronized (this.sync) {
/*  739 */         s.defaultWriteObject();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class SynchronizedRandomAccessList
/*      */     extends SynchronizedList
/*      */     implements RandomAccess, Serializable {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     protected SynchronizedRandomAccessList(CharList l, Object sync) {
/*  750 */       super(l, sync);
/*      */     }
/*      */     protected SynchronizedRandomAccessList(CharList l) {
/*  753 */       super(l);
/*      */     }
/*      */     
/*      */     public CharList subList(int from, int to) {
/*  757 */       synchronized (this.sync) {
/*  758 */         return new SynchronizedRandomAccessList(this.list.subList(from, to), this.sync);
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
/*      */   public static CharList synchronize(CharList l) {
/*  772 */     return (l instanceof RandomAccess) ? new SynchronizedRandomAccessList(l) : new SynchronizedList(l);
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
/*      */   public static CharList synchronize(CharList l, Object sync) {
/*  786 */     return (l instanceof RandomAccess) ? new SynchronizedRandomAccessList(l, sync) : new SynchronizedList(l, sync);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableList
/*      */     extends CharCollections.UnmodifiableCollection
/*      */     implements CharList, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     protected final CharList list;
/*      */     
/*      */     protected UnmodifiableList(CharList l) {
/*  797 */       super(l);
/*  798 */       this.list = l;
/*      */     }
/*      */     
/*      */     public char getChar(int i) {
/*  802 */       return this.list.getChar(i);
/*      */     }
/*      */     
/*      */     public char set(int i, char k) {
/*  806 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(int i, char k) {
/*  810 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public char removeChar(int i) {
/*  814 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public int indexOf(char k) {
/*  818 */       return this.list.indexOf(k);
/*      */     }
/*      */     
/*      */     public int lastIndexOf(char k) {
/*  822 */       return this.list.lastIndexOf(k);
/*      */     }
/*      */     
/*      */     public boolean addAll(int index, Collection<? extends Character> c) {
/*  826 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void getElements(int from, char[] a, int offset, int length) {
/*  830 */       this.list.getElements(from, a, offset, length);
/*      */     }
/*      */     
/*      */     public void removeElements(int from, int to) {
/*  834 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(int index, char[] a, int offset, int length) {
/*  838 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void addElements(int index, char[] a) {
/*  842 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void setElements(char[] a) {
/*  846 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void setElements(int index, char[] a) {
/*  850 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void setElements(int index, char[] a, int offset, int length) {
/*  854 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void size(int size) {
/*  858 */       this.list.size(size);
/*      */     }
/*      */     
/*      */     public CharListIterator listIterator() {
/*  862 */       return CharIterators.unmodifiable(this.list.listIterator());
/*      */     }
/*      */     
/*      */     public CharListIterator iterator() {
/*  866 */       return listIterator();
/*      */     }
/*      */     
/*      */     public CharListIterator listIterator(int i) {
/*  870 */       return CharIterators.unmodifiable(this.list.listIterator(i));
/*      */     }
/*      */     
/*      */     public CharList subList(int from, int to) {
/*  874 */       return new UnmodifiableList(this.list.subList(from, to));
/*      */     }
/*      */     
/*      */     public boolean equals(Object o) {
/*  878 */       if (o == this)
/*  879 */         return true; 
/*  880 */       return this.collection.equals(o);
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  884 */       return this.collection.hashCode();
/*      */     }
/*      */     
/*      */     public int compareTo(List<? extends Character> o) {
/*  888 */       return this.list.compareTo(o);
/*      */     }
/*      */     
/*      */     public boolean addAll(int index, CharCollection c) {
/*  892 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(CharList l) {
/*  896 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(int index, CharList l) {
/*  900 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character get(int i) {
/*  910 */       return this.list.get(i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void add(int i, Character k) {
/*  920 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character set(int index, Character k) {
/*  930 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character remove(int i) {
/*  940 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public int indexOf(Object o) {
/*  950 */       return this.list.indexOf(o);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public int lastIndexOf(Object o) {
/*  960 */       return this.list.lastIndexOf(o);
/*      */     }
/*      */     
/*      */     public void sort(CharComparator comparator) {
/*  964 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void unstableSort(CharComparator comparator) {
/*  968 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     @Deprecated
/*      */     public void sort(Comparator<? super Character> comparator) {
/*  973 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     @Deprecated
/*      */     public void unstableSort(Comparator<? super Character> comparator) {
/*  978 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class UnmodifiableRandomAccessList
/*      */     extends UnmodifiableList
/*      */     implements RandomAccess, Serializable {
/*      */     private static final long serialVersionUID = 0L;
/*      */     
/*      */     protected UnmodifiableRandomAccessList(CharList l) {
/*  988 */       super(l);
/*      */     }
/*      */     
/*      */     public CharList subList(int from, int to) {
/*  992 */       return new UnmodifiableRandomAccessList(this.list.subList(from, to));
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
/*      */   public static CharList unmodifiable(CharList l) {
/* 1005 */     return (l instanceof RandomAccess) ? new UnmodifiableRandomAccessList(l) : new UnmodifiableList(l);
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\CharLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */