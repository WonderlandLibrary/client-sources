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
/*     */ public interface Object2FloatSortedMap<K>
/*     */   extends Object2FloatMap<K>, SortedMap<K, Float>
/*     */ {
/*     */   Comparator<? super K> comparator();
/*     */   
/*     */   FloatCollection values();
/*     */   
/*     */   ObjectSortedSet<K> keySet();
/*     */   
/*     */   ObjectSortedSet<Object2FloatMap.Entry<K>> object2FloatEntrySet();
/*     */   
/*     */   public static interface FastSortedEntrySet<K>
/*     */     extends ObjectSortedSet<Object2FloatMap.Entry<K>>, Object2FloatMap.FastEntrySet<K>
/*     */   {
/*     */     ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> fastIterator();
/*     */     
/*     */     ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> fastIterator(Object2FloatMap.Entry<K> param1Entry);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default ObjectSortedSet<Map.Entry<K, Float>> entrySet() {
/* 117 */     return (ObjectSortedSet)object2FloatEntrySet();
/*     */   }
/*     */   
/*     */   Object2FloatSortedMap<K> tailMap(K paramK);
/*     */   
/*     */   Object2FloatSortedMap<K> headMap(K paramK);
/*     */   
/*     */   Object2FloatSortedMap<K> subMap(K paramK1, K paramK2);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2FloatSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */