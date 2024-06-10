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
/*     */ 
/*     */ public interface Reference2ReferenceSortedMap<K, V>
/*     */   extends Reference2ReferenceMap<K, V>, SortedMap<K, V>
/*     */ {
/*     */   Comparator<? super K> comparator();
/*     */   
/*     */   ReferenceCollection<V> values();
/*     */   
/*     */   ReferenceSortedSet<K> keySet();
/*     */   
/*     */   ObjectSortedSet<Reference2ReferenceMap.Entry<K, V>> reference2ReferenceEntrySet();
/*     */   
/*     */   public static interface FastSortedEntrySet<K, V>
/*     */     extends ObjectSortedSet<Reference2ReferenceMap.Entry<K, V>>, Reference2ReferenceMap.FastEntrySet<K, V>
/*     */   {
/*     */     ObjectBidirectionalIterator<Reference2ReferenceMap.Entry<K, V>> fastIterator();
/*     */     
/*     */     ObjectBidirectionalIterator<Reference2ReferenceMap.Entry<K, V>> fastIterator(Reference2ReferenceMap.Entry<K, V> param1Entry);
/*     */   }
/*     */   
/*     */   default ObjectSortedSet<Map.Entry<K, V>> entrySet() {
/* 116 */     return (ObjectSortedSet)reference2ReferenceEntrySet();
/*     */   }
/*     */   
/*     */   Reference2ReferenceSortedMap<K, V> tailMap(K paramK);
/*     */   
/*     */   Reference2ReferenceSortedMap<K, V> headMap(K paramK);
/*     */   
/*     */   Reference2ReferenceSortedMap<K, V> subMap(K paramK1, K paramK2);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2ReferenceSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */