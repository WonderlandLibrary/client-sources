/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public interface ShortList
/*     */   extends List<Short>, Comparable<List<? extends Short>>, ShortCollection
/*     */ {
/*     */   default void setElements(short[] a) {
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
/*     */   default void setElements(int index, short[] a) {
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
/*     */   default void setElements(int index, short[] a, int offset, int length) {
/* 192 */     if (index < 0)
/* 193 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/* 194 */     if (index > size())
/* 195 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + size() + ")"); 
/* 196 */     ShortArrays.ensureOffsetLength(a, offset, length);
/* 197 */     if (index + length > size())
/* 198 */       throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + 
/* 199 */           size() + ")"); 
/* 200 */     ShortListIterator iter = listIterator(index);
/* 201 */     int i = 0;
/* 202 */     while (i < length) {
/* 203 */       iter.nextShort();
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
/*     */   default void add(int index, Short key) {
/* 229 */     add(index, key.shortValue());
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
/*     */   default Short get(int index) {
/* 297 */     return Short.valueOf(getShort(index));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default int indexOf(Object o) {
/* 307 */     return indexOf(((Short)o).shortValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default int lastIndexOf(Object o) {
/* 317 */     return lastIndexOf(((Short)o).shortValue());
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
/*     */   default boolean add(Short k) {
/* 331 */     return add(k.shortValue());
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
/*     */   default Short remove(int index) {
/* 358 */     return Short.valueOf(removeShort(index));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Short set(int index, Short k) {
/* 368 */     return Short.valueOf(set(index, k.shortValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void sort(Comparator<? super Short> comparator) {
/* 378 */     sort(ShortComparators.asShortComparator(comparator));
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
/*     */   default void sort(ShortComparator comparator) {
/* 401 */     if (comparator == null) {
/*     */ 
/*     */ 
/*     */       
/* 405 */       unstableSort(comparator);
/*     */     } else {
/* 407 */       short[] elements = toShortArray();
/* 408 */       ShortArrays.stableSort(elements, comparator);
/* 409 */       setElements(elements);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void unstableSort(Comparator<? super Short> comparator) {
/* 419 */     unstableSort(ShortComparators.asShortComparator(comparator));
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
/*     */   default void unstableSort(ShortComparator comparator) {
/* 442 */     short[] elements = toShortArray();
/* 443 */     if (comparator == null) {
/* 444 */       ShortArrays.unstableSort(elements);
/*     */     } else {
/* 446 */       ShortArrays.unstableSort(elements, comparator);
/*     */     } 
/* 448 */     setElements(elements);
/*     */   }
/*     */   
/*     */   ShortListIterator iterator();
/*     */   
/*     */   ShortListIterator listIterator();
/*     */   
/*     */   ShortListIterator listIterator(int paramInt);
/*     */   
/*     */   ShortList subList(int paramInt1, int paramInt2);
/*     */   
/*     */   void size(int paramInt);
/*     */   
/*     */   void getElements(int paramInt1, short[] paramArrayOfshort, int paramInt2, int paramInt3);
/*     */   
/*     */   void removeElements(int paramInt1, int paramInt2);
/*     */   
/*     */   void addElements(int paramInt, short[] paramArrayOfshort);
/*     */   
/*     */   void addElements(int paramInt1, short[] paramArrayOfshort, int paramInt2, int paramInt3);
/*     */   
/*     */   boolean add(short paramShort);
/*     */   
/*     */   void add(int paramInt, short paramShort);
/*     */   
/*     */   boolean addAll(int paramInt, ShortCollection paramShortCollection);
/*     */   
/*     */   boolean addAll(int paramInt, ShortList paramShortList);
/*     */   
/*     */   boolean addAll(ShortList paramShortList);
/*     */   
/*     */   short set(int paramInt, short paramShort);
/*     */   
/*     */   short getShort(int paramInt);
/*     */   
/*     */   int indexOf(short paramShort);
/*     */   
/*     */   int lastIndexOf(short paramShort);
/*     */   
/*     */   short removeShort(int paramInt);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\ShortList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */