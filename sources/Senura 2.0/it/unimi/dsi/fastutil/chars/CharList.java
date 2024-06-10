/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public interface CharList
/*     */   extends List<Character>, Comparable<List<? extends Character>>, CharCollection
/*     */ {
/*     */   default void setElements(char[] a) {
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
/*     */   default void setElements(int index, char[] a) {
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
/*     */   default void setElements(int index, char[] a, int offset, int length) {
/* 192 */     if (index < 0)
/* 193 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/* 194 */     if (index > size())
/* 195 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + size() + ")"); 
/* 196 */     CharArrays.ensureOffsetLength(a, offset, length);
/* 197 */     if (index + length > size())
/* 198 */       throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + 
/* 199 */           size() + ")"); 
/* 200 */     CharListIterator iter = listIterator(index);
/* 201 */     int i = 0;
/* 202 */     while (i < length) {
/* 203 */       iter.nextChar();
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
/*     */   default void add(int index, Character key) {
/* 229 */     add(index, key.charValue());
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
/*     */   default Character get(int index) {
/* 297 */     return Character.valueOf(getChar(index));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default int indexOf(Object o) {
/* 307 */     return indexOf(((Character)o).charValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default int lastIndexOf(Object o) {
/* 317 */     return lastIndexOf(((Character)o).charValue());
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
/*     */   default boolean add(Character k) {
/* 331 */     return add(k.charValue());
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
/*     */   default Character remove(int index) {
/* 358 */     return Character.valueOf(removeChar(index));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Character set(int index, Character k) {
/* 368 */     return Character.valueOf(set(index, k.charValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void sort(Comparator<? super Character> comparator) {
/* 378 */     sort(CharComparators.asCharComparator(comparator));
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
/*     */   default void sort(CharComparator comparator) {
/* 401 */     if (comparator == null) {
/*     */ 
/*     */ 
/*     */       
/* 405 */       unstableSort(comparator);
/*     */     } else {
/* 407 */       char[] elements = toCharArray();
/* 408 */       CharArrays.stableSort(elements, comparator);
/* 409 */       setElements(elements);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void unstableSort(Comparator<? super Character> comparator) {
/* 419 */     unstableSort(CharComparators.asCharComparator(comparator));
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
/*     */   default void unstableSort(CharComparator comparator) {
/* 442 */     char[] elements = toCharArray();
/* 443 */     if (comparator == null) {
/* 444 */       CharArrays.unstableSort(elements);
/*     */     } else {
/* 446 */       CharArrays.unstableSort(elements, comparator);
/*     */     } 
/* 448 */     setElements(elements);
/*     */   }
/*     */   
/*     */   CharListIterator iterator();
/*     */   
/*     */   CharListIterator listIterator();
/*     */   
/*     */   CharListIterator listIterator(int paramInt);
/*     */   
/*     */   CharList subList(int paramInt1, int paramInt2);
/*     */   
/*     */   void size(int paramInt);
/*     */   
/*     */   void getElements(int paramInt1, char[] paramArrayOfchar, int paramInt2, int paramInt3);
/*     */   
/*     */   void removeElements(int paramInt1, int paramInt2);
/*     */   
/*     */   void addElements(int paramInt, char[] paramArrayOfchar);
/*     */   
/*     */   void addElements(int paramInt1, char[] paramArrayOfchar, int paramInt2, int paramInt3);
/*     */   
/*     */   boolean add(char paramChar);
/*     */   
/*     */   void add(int paramInt, char paramChar);
/*     */   
/*     */   boolean addAll(int paramInt, CharCollection paramCharCollection);
/*     */   
/*     */   boolean addAll(int paramInt, CharList paramCharList);
/*     */   
/*     */   boolean addAll(CharList paramCharList);
/*     */   
/*     */   char set(int paramInt, char paramChar);
/*     */   
/*     */   char getChar(int paramInt);
/*     */   
/*     */   int indexOf(char paramChar);
/*     */   
/*     */   int lastIndexOf(char paramChar);
/*     */   
/*     */   char removeChar(int paramInt);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\CharList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */