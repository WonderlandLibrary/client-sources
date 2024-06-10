/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
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
/*     */ public interface Long2ObjectSortedMap<V>
/*     */   extends Long2ObjectMap<V>, SortedMap<Long, V>
/*     */ {
/*     */   @Deprecated
/*     */   default Long2ObjectSortedMap<V> subMap(Long from, Long to) {
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
/*     */   default Long2ObjectSortedMap<V> headMap(Long to) {
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
/*     */   default Long2ObjectSortedMap<V> tailMap(Long from) {
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
/*     */   public static interface FastSortedEntrySet<V>
/*     */     extends ObjectSortedSet<Long2ObjectMap.Entry<V>>, Long2ObjectMap.FastEntrySet<V>
/*     */   {
/*     */     ObjectBidirectionalIterator<Long2ObjectMap.Entry<V>> fastIterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ObjectBidirectionalIterator<Long2ObjectMap.Entry<V>> fastIterator(Long2ObjectMap.Entry<V> param1Entry);
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
/*     */   default ObjectSortedSet<Map.Entry<Long, V>> entrySet() {
/* 184 */     return (ObjectSortedSet)long2ObjectEntrySet();
/*     */   }
/*     */   
/*     */   Long2ObjectSortedMap<V> subMap(long paramLong1, long paramLong2);
/*     */   
/*     */   Long2ObjectSortedMap<V> headMap(long paramLong);
/*     */   
/*     */   Long2ObjectSortedMap<V> tailMap(long paramLong);
/*     */   
/*     */   long firstLongKey();
/*     */   
/*     */   long lastLongKey();
/*     */   
/*     */   ObjectSortedSet<Long2ObjectMap.Entry<V>> long2ObjectEntrySet();
/*     */   
/*     */   LongSortedSet keySet();
/*     */   
/*     */   ObjectCollection<V> values();
/*     */   
/*     */   LongComparator comparator();
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2ObjectSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */