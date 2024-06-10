/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
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
/*     */ public interface Float2ByteSortedMap
/*     */   extends Float2ByteMap, SortedMap<Float, Byte>
/*     */ {
/*     */   @Deprecated
/*     */   default Float2ByteSortedMap subMap(Float from, Float to) {
/*  91 */     return subMap(from.floatValue(), to.floatValue());
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
/*     */   default Float2ByteSortedMap headMap(Float to) {
/* 104 */     return headMap(to.floatValue());
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
/*     */   default Float2ByteSortedMap tailMap(Float from) {
/* 117 */     return tailMap(from.floatValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Float firstKey() {
/* 127 */     return Float.valueOf(firstFloatKey());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Float lastKey() {
/* 137 */     return Float.valueOf(lastFloatKey());
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
/*     */     extends ObjectSortedSet<Float2ByteMap.Entry>, Float2ByteMap.FastEntrySet
/*     */   {
/*     */     ObjectBidirectionalIterator<Float2ByteMap.Entry> fastIterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ObjectBidirectionalIterator<Float2ByteMap.Entry> fastIterator(Float2ByteMap.Entry param1Entry);
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
/*     */   default ObjectSortedSet<Map.Entry<Float, Byte>> entrySet() {
/* 184 */     return (ObjectSortedSet)float2ByteEntrySet();
/*     */   }
/*     */   
/*     */   Float2ByteSortedMap subMap(float paramFloat1, float paramFloat2);
/*     */   
/*     */   Float2ByteSortedMap headMap(float paramFloat);
/*     */   
/*     */   Float2ByteSortedMap tailMap(float paramFloat);
/*     */   
/*     */   float firstFloatKey();
/*     */   
/*     */   float lastFloatKey();
/*     */   
/*     */   ObjectSortedSet<Float2ByteMap.Entry> float2ByteEntrySet();
/*     */   
/*     */   FloatSortedSet keySet();
/*     */   
/*     */   ByteCollection values();
/*     */   
/*     */   FloatComparator comparator();
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2ByteSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */