/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
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
/*     */ public interface Object2DoubleSortedMap<K>
/*     */   extends Object2DoubleMap<K>, SortedMap<K, Double>
/*     */ {
/*     */   Comparator<? super K> comparator();
/*     */   
/*     */   DoubleCollection values();
/*     */   
/*     */   ObjectSortedSet<K> keySet();
/*     */   
/*     */   ObjectSortedSet<Object2DoubleMap.Entry<K>> object2DoubleEntrySet();
/*     */   
/*     */   public static interface FastSortedEntrySet<K>
/*     */     extends ObjectSortedSet<Object2DoubleMap.Entry<K>>, Object2DoubleMap.FastEntrySet<K>
/*     */   {
/*     */     ObjectBidirectionalIterator<Object2DoubleMap.Entry<K>> fastIterator();
/*     */     
/*     */     ObjectBidirectionalIterator<Object2DoubleMap.Entry<K>> fastIterator(Object2DoubleMap.Entry<K> param1Entry);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default ObjectSortedSet<Map.Entry<K, Double>> entrySet() {
/* 117 */     return (ObjectSortedSet)object2DoubleEntrySet();
/*     */   }
/*     */   
/*     */   Object2DoubleSortedMap<K> tailMap(K paramK);
/*     */   
/*     */   Object2DoubleSortedMap<K> headMap(K paramK);
/*     */   
/*     */   Object2DoubleSortedMap<K> subMap(K paramK1, K paramK2);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2DoubleSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */