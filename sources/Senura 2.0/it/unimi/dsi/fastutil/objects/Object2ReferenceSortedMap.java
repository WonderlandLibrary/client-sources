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
/*     */ public interface Object2ReferenceSortedMap<K, V>
/*     */   extends Object2ReferenceMap<K, V>, SortedMap<K, V>
/*     */ {
/*     */   Comparator<? super K> comparator();
/*     */   
/*     */   ReferenceCollection<V> values();
/*     */   
/*     */   ObjectSortedSet<K> keySet();
/*     */   
/*     */   ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> object2ReferenceEntrySet();
/*     */   
/*     */   public static interface FastSortedEntrySet<K, V>
/*     */     extends ObjectSortedSet<Object2ReferenceMap.Entry<K, V>>, Object2ReferenceMap.FastEntrySet<K, V>
/*     */   {
/*     */     ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> fastIterator();
/*     */     
/*     */     ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> fastIterator(Object2ReferenceMap.Entry<K, V> param1Entry);
/*     */   }
/*     */   
/*     */   default ObjectSortedSet<Map.Entry<K, V>> entrySet() {
/* 115 */     return (ObjectSortedSet)object2ReferenceEntrySet();
/*     */   }
/*     */   
/*     */   Object2ReferenceSortedMap<K, V> tailMap(K paramK);
/*     */   
/*     */   Object2ReferenceSortedMap<K, V> headMap(K paramK);
/*     */   
/*     */   Object2ReferenceSortedMap<K, V> subMap(K paramK1, K paramK2);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ReferenceSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */