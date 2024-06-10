/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Long2ByteSortedMap
/*     */   extends Long2ByteMap, SortedMap<Long, Byte>
/*     */ {
/*     */   @Deprecated
/*     */   default Long2ByteSortedMap subMap(Long from, Long to) {
/*  91 */     return subMap(from.longValue(), to.longValue());
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
/*     */   @Deprecated
/*     */   default Long2ByteSortedMap headMap(Long to) {
/* 104 */     return headMap(to.longValue());
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
/*     */   @Deprecated
/*     */   default Long2ByteSortedMap tailMap(Long from) {
/* 117 */     return tailMap(from.longValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Long firstKey() {
/* 127 */     return Long.valueOf(firstLongKey());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Long lastKey() {
/* 137 */     return Long.valueOf(lastLongKey());
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
/*     */   public static interface FastSortedEntrySet
/*     */     extends ObjectSortedSet<Long2ByteMap.Entry>, Long2ByteMap.FastEntrySet
/*     */   {
/*     */     ObjectBidirectionalIterator<Long2ByteMap.Entry> fastIterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ObjectBidirectionalIterator<Long2ByteMap.Entry> fastIterator(Long2ByteMap.Entry param1Entry);
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
/*     */   @Deprecated
/*     */   default ObjectSortedSet<Map.Entry<Long, Byte>> entrySet() {
/* 184 */     return (ObjectSortedSet)long2ByteEntrySet();
/*     */   }
/*     */   
/*     */   Long2ByteSortedMap subMap(long paramLong1, long paramLong2);
/*     */   
/*     */   Long2ByteSortedMap headMap(long paramLong);
/*     */   
/*     */   Long2ByteSortedMap tailMap(long paramLong);
/*     */   
/*     */   long firstLongKey();
/*     */   
/*     */   long lastLongKey();
/*     */   
/*     */   ObjectSortedSet<Long2ByteMap.Entry> long2ByteEntrySet();
/*     */   
/*     */   LongSortedSet keySet();
/*     */   
/*     */   ByteCollection values();
/*     */   
/*     */   LongComparator comparator();
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2ByteSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */