/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
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
/*     */ public interface IntList
/*     */   extends List<Integer>, Comparable<List<? extends Integer>>, IntCollection
/*     */ {
/*     */   default void setElements(int[] a) {
/* 146 */     setElements(0, a);
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
/*     */   default void setElements(int index, int[] a) {
/* 158 */     setElements(index, a, 0, a.length);
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
/*     */   default void setElements(int index, int[] a, int offset, int length) {
/* 192 */     if (index < 0)
/* 193 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/* 194 */     if (index > size())
/* 195 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + size() + ")"); 
/* 196 */     IntArrays.ensureOffsetLength(a, offset, length);
/* 197 */     if (index + length > size())
/* 198 */       throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + 
/* 199 */           size() + ")"); 
/* 200 */     IntListIterator iter = listIterator(index);
/* 201 */     int i = 0;
/* 202 */     while (i < length) {
/* 203 */       iter.nextInt();
/* 204 */       iter.set(a[offset + i++]);
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
/*     */   @Deprecated
/*     */   default void add(int index, Integer key) {
/* 229 */     add(index, key.intValue());
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
/*     */   @Deprecated
/*     */   default boolean contains(Object key) {
/* 287 */     return super.contains(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Integer get(int index) {
/* 297 */     return Integer.valueOf(getInt(index));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default int indexOf(Object o) {
/* 307 */     return indexOf(((Integer)o).intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default int lastIndexOf(Object o) {
/* 317 */     return lastIndexOf(((Integer)o).intValue());
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
/*     */   @Deprecated
/*     */   default boolean add(Integer k) {
/* 331 */     return add(k.intValue());
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
/*     */   
/*     */   @Deprecated
/*     */   default boolean remove(Object key) {
/* 348 */     return super.remove(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Integer remove(int index) {
/* 358 */     return Integer.valueOf(removeInt(index));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Integer set(int index, Integer k) {
/* 368 */     return Integer.valueOf(set(index, k.intValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void sort(Comparator<? super Integer> comparator) {
/* 378 */     sort(IntComparators.asIntComparator(comparator));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void sort(IntComparator comparator) {
/* 401 */     if (comparator == null) {
/*     */ 
/*     */ 
/*     */       
/* 405 */       unstableSort(comparator);
/*     */     } else {
/* 407 */       int[] elements = toIntArray();
/* 408 */       IntArrays.stableSort(elements, comparator);
/* 409 */       setElements(elements);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void unstableSort(Comparator<? super Integer> comparator) {
/* 419 */     unstableSort(IntComparators.asIntComparator(comparator));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void unstableSort(IntComparator comparator) {
/* 442 */     int[] elements = toIntArray();
/* 443 */     if (comparator == null) {
/* 444 */       IntArrays.unstableSort(elements);
/*     */     } else {
/* 446 */       IntArrays.unstableSort(elements, comparator);
/*     */     } 
/* 448 */     setElements(elements);
/*     */   }
/*     */   
/*     */   IntListIterator iterator();
/*     */   
/*     */   IntListIterator listIterator();
/*     */   
/*     */   IntListIterator listIterator(int paramInt);
/*     */   
/*     */   IntList subList(int paramInt1, int paramInt2);
/*     */   
/*     */   void size(int paramInt);
/*     */   
/*     */   void getElements(int paramInt1, int[] paramArrayOfint, int paramInt2, int paramInt3);
/*     */   
/*     */   void removeElements(int paramInt1, int paramInt2);
/*     */   
/*     */   void addElements(int paramInt, int[] paramArrayOfint);
/*     */   
/*     */   void addElements(int paramInt1, int[] paramArrayOfint, int paramInt2, int paramInt3);
/*     */   
/*     */   boolean add(int paramInt);
/*     */   
/*     */   void add(int paramInt1, int paramInt2);
/*     */   
/*     */   boolean addAll(int paramInt, IntCollection paramIntCollection);
/*     */   
/*     */   boolean addAll(int paramInt, IntList paramIntList);
/*     */   
/*     */   boolean addAll(IntList paramIntList);
/*     */   
/*     */   int set(int paramInt1, int paramInt2);
/*     */   
/*     */   int getInt(int paramInt);
/*     */   
/*     */   int indexOf(int paramInt);
/*     */   
/*     */   int lastIndexOf(int paramInt);
/*     */   
/*     */   int removeInt(int paramInt);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */