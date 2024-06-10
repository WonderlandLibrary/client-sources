/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.function.LongPredicate;
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
/*     */ public interface LongCollection
/*     */   extends Collection<Long>, LongIterable
/*     */ {
/*     */   @Deprecated
/*     */   default boolean add(Long key) {
/*  76 */     return add(key.longValue());
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
/*  88 */     return contains(((Long)key).longValue());
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
/* 100 */     return rem(((Long)key).longValue());
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
/*     */   default boolean removeIf(Predicate<? super Long> filter) {
/* 181 */     return removeIf(key -> filter.test(Long.valueOf(key)));
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
/*     */   default boolean removeIf(LongPredicate filter) {
/* 193 */     Objects.requireNonNull(filter);
/* 194 */     boolean removed = false;
/* 195 */     LongIterator each = iterator();
/* 196 */     while (each.hasNext()) {
/* 197 */       if (filter.test(each.nextLong())) {
/* 198 */         each.remove();
/* 199 */         removed = true;
/*     */       } 
/*     */     } 
/* 202 */     return removed;
/*     */   }
/*     */   
/*     */   LongIterator iterator();
/*     */   
/*     */   boolean add(long paramLong);
/*     */   
/*     */   boolean contains(long paramLong);
/*     */   
/*     */   boolean rem(long paramLong);
/*     */   
/*     */   long[] toLongArray();
/*     */   
/*     */   @Deprecated
/*     */   long[] toLongArray(long[] paramArrayOflong);
/*     */   
/*     */   long[] toArray(long[] paramArrayOflong);
/*     */   
/*     */   boolean addAll(LongCollection paramLongCollection);
/*     */   
/*     */   boolean containsAll(LongCollection paramLongCollection);
/*     */   
/*     */   boolean removeAll(LongCollection paramLongCollection);
/*     */   
/*     */   boolean retainAll(LongCollection paramLongCollection);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */