/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
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
/*     */ public interface Reference2IntSortedMap<K>
/*     */   extends Reference2IntMap<K>, SortedMap<K, Integer>
/*     */ {
/*     */   Comparator<? super K> comparator();
/*     */   
/*     */   IntCollection values();
/*     */   
/*     */   ReferenceSortedSet<K> keySet();
/*     */   
/*     */   ObjectSortedSet<Reference2IntMap.Entry<K>> reference2IntEntrySet();
/*     */   
/*     */   public static interface FastSortedEntrySet<K>
/*     */     extends ObjectSortedSet<Reference2IntMap.Entry<K>>, Reference2IntMap.FastEntrySet<K>
/*     */   {
/*     */     ObjectBidirectionalIterator<Reference2IntMap.Entry<K>> fastIterator();
/*     */     
/*     */     ObjectBidirectionalIterator<Reference2IntMap.Entry<K>> fastIterator(Reference2IntMap.Entry<K> param1Entry);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default ObjectSortedSet<Map.Entry<K, Integer>> entrySet() {
/* 117 */     return (ObjectSortedSet)reference2IntEntrySet();
/*     */   }
/*     */   
/*     */   Reference2IntSortedMap<K> tailMap(K paramK);
/*     */   
/*     */   Reference2IntSortedMap<K> headMap(K paramK);
/*     */   
/*     */   Reference2IntSortedMap<K> subMap(K paramK1, K paramK2);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2IntSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */