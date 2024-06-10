/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Object2LongSortedMap<K>
/*     */   extends Object2LongMap<K>, SortedMap<K, Long>
/*     */ {
/*     */   Comparator<? super K> comparator();
/*     */   
/*     */   LongCollection values();
/*     */   
/*     */   ObjectSortedSet<K> keySet();
/*     */   
/*     */   ObjectSortedSet<Object2LongMap.Entry<K>> object2LongEntrySet();
/*     */   
/*     */   public static interface FastSortedEntrySet<K>
/*     */     extends ObjectSortedSet<Object2LongMap.Entry<K>>, Object2LongMap.FastEntrySet<K>
/*     */   {
/*     */     ObjectBidirectionalIterator<Object2LongMap.Entry<K>> fastIterator();
/*     */     
/*     */     ObjectBidirectionalIterator<Object2LongMap.Entry<K>> fastIterator(Object2LongMap.Entry<K> param1Entry);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default ObjectSortedSet<Map.Entry<K, Long>> entrySet() {
/* 117 */     return (ObjectSortedSet)object2LongEntrySet();
/*     */   }
/*     */   
/*     */   Object2LongSortedMap<K> tailMap(K paramK);
/*     */   
/*     */   Object2LongSortedMap<K> headMap(K paramK);
/*     */   
/*     */   Object2LongSortedMap<K> subMap(K paramK1, K paramK2);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2LongSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */