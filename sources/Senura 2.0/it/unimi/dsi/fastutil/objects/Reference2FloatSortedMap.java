/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
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
/*     */ public interface Reference2FloatSortedMap<K>
/*     */   extends Reference2FloatMap<K>, SortedMap<K, Float>
/*     */ {
/*     */   Comparator<? super K> comparator();
/*     */   
/*     */   FloatCollection values();
/*     */   
/*     */   ReferenceSortedSet<K> keySet();
/*     */   
/*     */   ObjectSortedSet<Reference2FloatMap.Entry<K>> reference2FloatEntrySet();
/*     */   
/*     */   public static interface FastSortedEntrySet<K>
/*     */     extends ObjectSortedSet<Reference2FloatMap.Entry<K>>, Reference2FloatMap.FastEntrySet<K>
/*     */   {
/*     */     ObjectBidirectionalIterator<Reference2FloatMap.Entry<K>> fastIterator();
/*     */     
/*     */     ObjectBidirectionalIterator<Reference2FloatMap.Entry<K>> fastIterator(Reference2FloatMap.Entry<K> param1Entry);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default ObjectSortedSet<Map.Entry<K, Float>> entrySet() {
/* 117 */     return (ObjectSortedSet)reference2FloatEntrySet();
/*     */   }
/*     */   
/*     */   Reference2FloatSortedMap<K> tailMap(K paramK);
/*     */   
/*     */   Reference2FloatSortedMap<K> headMap(K paramK);
/*     */   
/*     */   Reference2FloatSortedMap<K> subMap(K paramK1, K paramK2);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2FloatSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */