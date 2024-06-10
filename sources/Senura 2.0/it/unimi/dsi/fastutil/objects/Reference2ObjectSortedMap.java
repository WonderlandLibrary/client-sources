/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
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
/*     */ public interface Reference2ObjectSortedMap<K, V>
/*     */   extends Reference2ObjectMap<K, V>, SortedMap<K, V>
/*     */ {
/*     */   Comparator<? super K> comparator();
/*     */   
/*     */   ObjectCollection<V> values();
/*     */   
/*     */   ReferenceSortedSet<K> keySet();
/*     */   
/*     */   ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet();
/*     */   
/*     */   public static interface FastSortedEntrySet<K, V>
/*     */     extends ObjectSortedSet<Reference2ObjectMap.Entry<K, V>>, Reference2ObjectMap.FastEntrySet<K, V>
/*     */   {
/*     */     ObjectBidirectionalIterator<Reference2ObjectMap.Entry<K, V>> fastIterator();
/*     */     
/*     */     ObjectBidirectionalIterator<Reference2ObjectMap.Entry<K, V>> fastIterator(Reference2ObjectMap.Entry<K, V> param1Entry);
/*     */   }
/*     */   
/*     */   default ObjectSortedSet<Map.Entry<K, V>> entrySet() {
/* 115 */     return (ObjectSortedSet)reference2ObjectEntrySet();
/*     */   }
/*     */   
/*     */   Reference2ObjectSortedMap<K, V> tailMap(K paramK);
/*     */   
/*     */   Reference2ObjectSortedMap<K, V> headMap(K paramK);
/*     */   
/*     */   Reference2ObjectSortedMap<K, V> subMap(K paramK1, K paramK2);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2ObjectSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */