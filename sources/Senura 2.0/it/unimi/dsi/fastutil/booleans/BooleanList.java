/*     */ package it.unimi.dsi.fastutil.booleans;
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
/*     */ public interface BooleanList
/*     */   extends List<Boolean>, Comparable<List<? extends Boolean>>, BooleanCollection
/*     */ {
/*     */   default void setElements(boolean[] a) {
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
/*     */   default void setElements(int index, boolean[] a) {
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
/*     */   default void setElements(int index, boolean[] a, int offset, int length) {
/* 192 */     if (index < 0)
/* 193 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/* 194 */     if (index > size())
/* 195 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + size() + ")"); 
/* 196 */     BooleanArrays.ensureOffsetLength(a, offset, length);
/* 197 */     if (index + length > size())
/* 198 */       throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + 
/* 199 */           size() + ")"); 
/* 200 */     BooleanListIterator iter = listIterator(index);
/* 201 */     int i = 0;
/* 202 */     while (i < length) {
/* 203 */       iter.nextBoolean();
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
/*     */   default void add(int index, Boolean key) {
/* 229 */     add(index, key.booleanValue());
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
/*     */   default Boolean get(int index) {
/* 297 */     return Boolean.valueOf(getBoolean(index));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default int indexOf(Object o) {
/* 307 */     return indexOf(((Boolean)o).booleanValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default int lastIndexOf(Object o) {
/* 317 */     return lastIndexOf(((Boolean)o).booleanValue());
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
/*     */   default boolean add(Boolean k) {
/* 331 */     return add(k.booleanValue());
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
/*     */   default Boolean remove(int index) {
/* 358 */     return Boolean.valueOf(removeBoolean(index));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Boolean set(int index, Boolean k) {
/* 368 */     return Boolean.valueOf(set(index, k.booleanValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void sort(Comparator<? super Boolean> comparator) {
/* 378 */     sort(BooleanComparators.asBooleanComparator(comparator));
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
/*     */   default void sort(BooleanComparator comparator) {
/* 401 */     if (comparator == null) {
/*     */ 
/*     */ 
/*     */       
/* 405 */       unstableSort(comparator);
/*     */     } else {
/* 407 */       boolean[] elements = toBooleanArray();
/* 408 */       BooleanArrays.stableSort(elements, comparator);
/* 409 */       setElements(elements);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void unstableSort(Comparator<? super Boolean> comparator) {
/* 419 */     unstableSort(BooleanComparators.asBooleanComparator(comparator));
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
/*     */   default void unstableSort(BooleanComparator comparator) {
/* 442 */     boolean[] elements = toBooleanArray();
/* 443 */     if (comparator == null) {
/* 444 */       BooleanArrays.unstableSort(elements);
/*     */     } else {
/* 446 */       BooleanArrays.unstableSort(elements, comparator);
/*     */     } 
/* 448 */     setElements(elements);
/*     */   }
/*     */   
/*     */   BooleanListIterator iterator();
/*     */   
/*     */   BooleanListIterator listIterator();
/*     */   
/*     */   BooleanListIterator listIterator(int paramInt);
/*     */   
/*     */   BooleanList subList(int paramInt1, int paramInt2);
/*     */   
/*     */   void size(int paramInt);
/*     */   
/*     */   void getElements(int paramInt1, boolean[] paramArrayOfboolean, int paramInt2, int paramInt3);
/*     */   
/*     */   void removeElements(int paramInt1, int paramInt2);
/*     */   
/*     */   void addElements(int paramInt, boolean[] paramArrayOfboolean);
/*     */   
/*     */   void addElements(int paramInt1, boolean[] paramArrayOfboolean, int paramInt2, int paramInt3);
/*     */   
/*     */   boolean add(boolean paramBoolean);
/*     */   
/*     */   void add(int paramInt, boolean paramBoolean);
/*     */   
/*     */   boolean addAll(int paramInt, BooleanCollection paramBooleanCollection);
/*     */   
/*     */   boolean addAll(int paramInt, BooleanList paramBooleanList);
/*     */   
/*     */   boolean addAll(BooleanList paramBooleanList);
/*     */   
/*     */   boolean set(int paramInt, boolean paramBoolean);
/*     */   
/*     */   boolean getBoolean(int paramInt);
/*     */   
/*     */   int indexOf(boolean paramBoolean);
/*     */   
/*     */   int lastIndexOf(boolean paramBoolean);
/*     */   
/*     */   boolean removeBoolean(int paramInt);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\booleans\BooleanList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */