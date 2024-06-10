/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.function.IntPredicate;
/*     */ import java.util.function.Predicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface IntCollection
/*     */   extends Collection<Integer>, IntIterable
/*     */ {
/*     */   @Deprecated
/*     */   default boolean add(Integer key) {
/*  76 */     return add(key.intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean contains(Object key) {
/*  86 */     if (key == null)
/*  87 */       return false; 
/*  88 */     return contains(((Integer)key).intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean remove(Object key) {
/*  98 */     if (key == null)
/*  99 */       return false; 
/* 100 */     return rem(((Integer)key).intValue());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   default boolean removeIf(Predicate<? super Integer> filter) {
/* 181 */     return removeIf(key -> filter.test(Integer.valueOf(key)));
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
/*     */   default boolean removeIf(IntPredicate filter) {
/* 193 */     Objects.requireNonNull(filter);
/* 194 */     boolean removed = false;
/* 195 */     IntIterator each = iterator();
/* 196 */     while (each.hasNext()) {
/* 197 */       if (filter.test(each.nextInt())) {
/* 198 */         each.remove();
/* 199 */         removed = true;
/*     */       } 
/*     */     } 
/* 202 */     return removed;
/*     */   }
/*     */   
/*     */   IntIterator iterator();
/*     */   
/*     */   boolean add(int paramInt);
/*     */   
/*     */   boolean contains(int paramInt);
/*     */   
/*     */   boolean rem(int paramInt);
/*     */   
/*     */   int[] toIntArray();
/*     */   
/*     */   @Deprecated
/*     */   int[] toIntArray(int[] paramArrayOfint);
/*     */   
/*     */   int[] toArray(int[] paramArrayOfint);
/*     */   
/*     */   boolean addAll(IntCollection paramIntCollection);
/*     */   
/*     */   boolean containsAll(IntCollection paramIntCollection);
/*     */   
/*     */   boolean removeAll(IntCollection paramIntCollection);
/*     */   
/*     */   boolean retainAll(IntCollection paramIntCollection);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */