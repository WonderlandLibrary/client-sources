/*     */ package it.unimi.dsi.fastutil.objects;
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
/*     */ public interface ReferenceList<K>
/*     */   extends List<K>, ReferenceCollection<K>
/*     */ {
/*     */   default void setElements(K[] a) {
/* 142 */     setElements(0, a);
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
/*     */   default void setElements(int index, K[] a) {
/* 154 */     setElements(index, a, 0, a.length);
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
/*     */   default void setElements(int index, K[] a, int offset, int length) {
/* 188 */     if (index < 0)
/* 189 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/* 190 */     if (index > size())
/* 191 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + size() + ")"); 
/* 192 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/* 193 */     if (index + length > size())
/* 194 */       throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + 
/* 195 */           size() + ")"); 
/* 196 */     ObjectListIterator<K> iter = listIterator(index);
/* 197 */     int i = 0;
/* 198 */     while (i < length) {
/* 199 */       iter.next();
/* 200 */       iter.set(a[offset + i++]);
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
/*     */   default void unstableSort(Comparator<? super K> comparator) {
/* 220 */     K[] elements = (K[])toArray();
/* 221 */     if (comparator == null) {
/* 222 */       ObjectArrays.unstableSort(elements);
/*     */     } else {
/* 224 */       ObjectArrays.unstableSort(elements, (Comparator)comparator);
/*     */     } 
/* 226 */     setElements(elements);
/*     */   }
/*     */   
/*     */   ObjectListIterator<K> iterator();
/*     */   
/*     */   ObjectListIterator<K> listIterator();
/*     */   
/*     */   ObjectListIterator<K> listIterator(int paramInt);
/*     */   
/*     */   ReferenceList<K> subList(int paramInt1, int paramInt2);
/*     */   
/*     */   void size(int paramInt);
/*     */   
/*     */   void getElements(int paramInt1, Object[] paramArrayOfObject, int paramInt2, int paramInt3);
/*     */   
/*     */   void removeElements(int paramInt1, int paramInt2);
/*     */   
/*     */   void addElements(int paramInt, K[] paramArrayOfK);
/*     */   
/*     */   void addElements(int paramInt1, K[] paramArrayOfK, int paramInt2, int paramInt3);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ReferenceList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */