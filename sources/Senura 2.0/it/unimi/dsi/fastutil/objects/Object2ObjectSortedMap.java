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
/*     */ public interface Object2ObjectSortedMap<K, V>
/*     */   extends Object2ObjectMap<K, V>, SortedMap<K, V>
/*     */ {
/*     */   Comparator<? super K> comparator();
/*     */   
/*     */   ObjectCollection<V> values();
/*     */   
/*     */   ObjectSortedSet<K> keySet();
/*     */   
/*     */   ObjectSortedSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet();
/*     */   
/*     */   public static interface FastSortedEntrySet<K, V>
/*     */     extends ObjectSortedSet<Object2ObjectMap.Entry<K, V>>, Object2ObjectMap.FastEntrySet<K, V>
/*     */   {
/*     */     ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> fastIterator();
/*     */     
/*     */     ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> fastIterator(Object2ObjectMap.Entry<K, V> param1Entry);
/*     */   }
/*     */   
/*     */   default ObjectSortedSet<Map.Entry<K, V>> entrySet() {
/* 115 */     return (ObjectSortedSet)object2ObjectEntrySet();
/*     */   }
/*     */   
/*     */   Object2ObjectSortedMap<K, V> tailMap(K paramK);
/*     */   
/*     */   Object2ObjectSortedMap<K, V> headMap(K paramK);
/*     */   
/*     */   Object2ObjectSortedMap<K, V> subMap(K paramK1, K paramK2);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ObjectSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */