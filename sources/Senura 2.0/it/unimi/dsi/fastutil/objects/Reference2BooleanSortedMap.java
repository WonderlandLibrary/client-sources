/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
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
/*     */ public interface Reference2BooleanSortedMap<K>
/*     */   extends Reference2BooleanMap<K>, SortedMap<K, Boolean>
/*     */ {
/*     */   Comparator<? super K> comparator();
/*     */   
/*     */   BooleanCollection values();
/*     */   
/*     */   ReferenceSortedSet<K> keySet();
/*     */   
/*     */   ObjectSortedSet<Reference2BooleanMap.Entry<K>> reference2BooleanEntrySet();
/*     */   
/*     */   public static interface FastSortedEntrySet<K>
/*     */     extends ObjectSortedSet<Reference2BooleanMap.Entry<K>>, Reference2BooleanMap.FastEntrySet<K>
/*     */   {
/*     */     ObjectBidirectionalIterator<Reference2BooleanMap.Entry<K>> fastIterator();
/*     */     
/*     */     ObjectBidirectionalIterator<Reference2BooleanMap.Entry<K>> fastIterator(Reference2BooleanMap.Entry<K> param1Entry);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default ObjectSortedSet<Map.Entry<K, Boolean>> entrySet() {
/* 117 */     return (ObjectSortedSet)reference2BooleanEntrySet();
/*     */   }
/*     */   
/*     */   Reference2BooleanSortedMap<K> tailMap(K paramK);
/*     */   
/*     */   Reference2BooleanSortedMap<K> headMap(K paramK);
/*     */   
/*     */   Reference2BooleanSortedMap<K> subMap(K paramK1, K paramK2);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2BooleanSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */