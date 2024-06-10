/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.SortedSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface LongSortedSet
/*     */   extends LongSet, SortedSet<Long>, LongBidirectionalIterable
/*     */ {
/*     */   @Deprecated
/*     */   default LongSortedSet subSet(Long from, Long to) {
/* 138 */     return subSet(from.longValue(), to.longValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default LongSortedSet headSet(Long to) {
/* 148 */     return headSet(to.longValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default LongSortedSet tailSet(Long from) {
/* 158 */     return tailSet(from.longValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Long first() {
/* 168 */     return Long.valueOf(firstLong());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Long last() {
/* 178 */     return Long.valueOf(lastLong());
/*     */   }
/*     */   
/*     */   LongBidirectionalIterator iterator(long paramLong);
/*     */   
/*     */   LongBidirectionalIterator iterator();
/*     */   
/*     */   LongSortedSet subSet(long paramLong1, long paramLong2);
/*     */   
/*     */   LongSortedSet headSet(long paramLong);
/*     */   
/*     */   LongSortedSet tailSet(long paramLong);
/*     */   
/*     */   LongComparator comparator();
/*     */   
/*     */   long firstLong();
/*     */   
/*     */   long lastLong();
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongSortedSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */