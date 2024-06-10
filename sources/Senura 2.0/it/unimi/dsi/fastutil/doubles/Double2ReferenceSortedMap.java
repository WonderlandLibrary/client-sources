/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
/*     */ public interface Double2ReferenceSortedMap<V>
/*     */   extends Double2ReferenceMap<V>, SortedMap<Double, V>
/*     */ {
/*     */   @Deprecated
/*     */   default Double2ReferenceSortedMap<V> subMap(Double from, Double to) {
/*  91 */     return subMap(from.doubleValue(), to.doubleValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Double2ReferenceSortedMap<V> headMap(Double to) {
/* 104 */     return headMap(to.doubleValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Double2ReferenceSortedMap<V> tailMap(Double from) {
/* 117 */     return tailMap(from.doubleValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Double firstKey() {
/* 127 */     return Double.valueOf(firstDoubleKey());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Double lastKey() {
/* 137 */     return Double.valueOf(lastDoubleKey());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface FastSortedEntrySet<V>
/*     */     extends ObjectSortedSet<Double2ReferenceMap.Entry<V>>, Double2ReferenceMap.FastEntrySet<V>
/*     */   {
/*     */     ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> fastIterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> fastIterator(Double2ReferenceMap.Entry<V> param1Entry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default ObjectSortedSet<Map.Entry<Double, V>> entrySet() {
/* 184 */     return (ObjectSortedSet)double2ReferenceEntrySet();
/*     */   }
/*     */   
/*     */   Double2ReferenceSortedMap<V> subMap(double paramDouble1, double paramDouble2);
/*     */   
/*     */   Double2ReferenceSortedMap<V> headMap(double paramDouble);
/*     */   
/*     */   Double2ReferenceSortedMap<V> tailMap(double paramDouble);
/*     */   
/*     */   double firstDoubleKey();
/*     */   
/*     */   double lastDoubleKey();
/*     */   
/*     */   ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet();
/*     */   
/*     */   DoubleSortedSet keySet();
/*     */   
/*     */   ReferenceCollection<V> values();
/*     */   
/*     */   DoubleComparator comparator();
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2ReferenceSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */