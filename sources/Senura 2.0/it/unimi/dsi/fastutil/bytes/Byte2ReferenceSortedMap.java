/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public interface Byte2ReferenceSortedMap<V>
/*     */   extends Byte2ReferenceMap<V>, SortedMap<Byte, V>
/*     */ {
/*     */   @Deprecated
/*     */   default Byte2ReferenceSortedMap<V> subMap(Byte from, Byte to) {
/*  91 */     return subMap(from.byteValue(), to.byteValue());
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
/*     */   default Byte2ReferenceSortedMap<V> headMap(Byte to) {
/* 104 */     return headMap(to.byteValue());
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
/*     */   default Byte2ReferenceSortedMap<V> tailMap(Byte from) {
/* 117 */     return tailMap(from.byteValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Byte firstKey() {
/* 127 */     return Byte.valueOf(firstByteKey());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Byte lastKey() {
/* 137 */     return Byte.valueOf(lastByteKey());
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
/*     */     extends ObjectSortedSet<Byte2ReferenceMap.Entry<V>>, Byte2ReferenceMap.FastEntrySet<V>
/*     */   {
/*     */     ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>> fastIterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ObjectBidirectionalIterator<Byte2ReferenceMap.Entry<V>> fastIterator(Byte2ReferenceMap.Entry<V> param1Entry);
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
/*     */   default ObjectSortedSet<Map.Entry<Byte, V>> entrySet() {
/* 184 */     return (ObjectSortedSet)byte2ReferenceEntrySet();
/*     */   }
/*     */   
/*     */   Byte2ReferenceSortedMap<V> subMap(byte paramByte1, byte paramByte2);
/*     */   
/*     */   Byte2ReferenceSortedMap<V> headMap(byte paramByte);
/*     */   
/*     */   Byte2ReferenceSortedMap<V> tailMap(byte paramByte);
/*     */   
/*     */   byte firstByteKey();
/*     */   
/*     */   byte lastByteKey();
/*     */   
/*     */   ObjectSortedSet<Byte2ReferenceMap.Entry<V>> byte2ReferenceEntrySet();
/*     */   
/*     */   ByteSortedSet keySet();
/*     */   
/*     */   ReferenceCollection<V> values();
/*     */   
/*     */   ByteComparator comparator();
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2ReferenceSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */