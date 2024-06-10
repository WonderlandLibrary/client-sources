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
/*     */ public interface Object2BooleanSortedMap<K>
/*     */   extends Object2BooleanMap<K>, SortedMap<K, Boolean>
/*     */ {
/*     */   Comparator<? super K> comparator();
/*     */   
/*     */   BooleanCollection values();
/*     */   
/*     */   ObjectSortedSet<K> keySet();
/*     */   
/*     */   ObjectSortedSet<Object2BooleanMap.Entry<K>> object2BooleanEntrySet();
/*     */   
/*     */   public static interface FastSortedEntrySet<K>
/*     */     extends ObjectSortedSet<Object2BooleanMap.Entry<K>>, Object2BooleanMap.FastEntrySet<K>
/*     */   {
/*     */     ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> fastIterator();
/*     */     
/*     */     ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> fastIterator(Object2BooleanMap.Entry<K> param1Entry);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default ObjectSortedSet<Map.Entry<K, Boolean>> entrySet() {
/* 117 */     return (ObjectSortedSet)object2BooleanEntrySet();
/*     */   }
/*     */   
/*     */   Object2BooleanSortedMap<K> tailMap(K paramK);
/*     */   
/*     */   Object2BooleanSortedMap<K> headMap(K paramK);
/*     */   
/*     */   Object2BooleanSortedMap<K> subMap(K paramK1, K paramK2);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2BooleanSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */