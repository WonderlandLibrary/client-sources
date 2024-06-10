/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
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
/*     */ public interface Int2LongSortedMap
/*     */   extends Int2LongMap, SortedMap<Integer, Long>
/*     */ {
/*     */   @Deprecated
/*     */   default Int2LongSortedMap subMap(Integer from, Integer to) {
/*  91 */     return subMap(from.intValue(), to.intValue());
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
/*     */   default Int2LongSortedMap headMap(Integer to) {
/* 104 */     return headMap(to.intValue());
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
/*     */   default Int2LongSortedMap tailMap(Integer from) {
/* 117 */     return tailMap(from.intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Integer firstKey() {
/* 127 */     return Integer.valueOf(firstIntKey());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Integer lastKey() {
/* 137 */     return Integer.valueOf(lastIntKey());
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
/*     */   public static interface FastSortedEntrySet
/*     */     extends ObjectSortedSet<Int2LongMap.Entry>, Int2LongMap.FastEntrySet
/*     */   {
/*     */     ObjectBidirectionalIterator<Int2LongMap.Entry> fastIterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ObjectBidirectionalIterator<Int2LongMap.Entry> fastIterator(Int2LongMap.Entry param1Entry);
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
/*     */   default ObjectSortedSet<Map.Entry<Integer, Long>> entrySet() {
/* 184 */     return (ObjectSortedSet)int2LongEntrySet();
/*     */   }
/*     */   
/*     */   Int2LongSortedMap subMap(int paramInt1, int paramInt2);
/*     */   
/*     */   Int2LongSortedMap headMap(int paramInt);
/*     */   
/*     */   Int2LongSortedMap tailMap(int paramInt);
/*     */   
/*     */   int firstIntKey();
/*     */   
/*     */   int lastIntKey();
/*     */   
/*     */   ObjectSortedSet<Int2LongMap.Entry> int2LongEntrySet();
/*     */   
/*     */   IntSortedSet keySet();
/*     */   
/*     */   LongCollection values();
/*     */   
/*     */   IntComparator comparator();
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2LongSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */