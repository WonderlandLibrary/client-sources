/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
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
/*     */ public interface Byte2ObjectSortedMap<V>
/*     */   extends Byte2ObjectMap<V>, SortedMap<Byte, V>
/*     */ {
/*     */   @Deprecated
/*     */   default Byte2ObjectSortedMap<V> subMap(Byte from, Byte to) {
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
/*     */   default Byte2ObjectSortedMap<V> headMap(Byte to) {
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
/*     */   default Byte2ObjectSortedMap<V> tailMap(Byte from) {
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
/*     */     extends ObjectSortedSet<Byte2ObjectMap.Entry<V>>, Byte2ObjectMap.FastEntrySet<V>
/*     */   {
/*     */     ObjectBidirectionalIterator<Byte2ObjectMap.Entry<V>> fastIterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ObjectBidirectionalIterator<Byte2ObjectMap.Entry<V>> fastIterator(Byte2ObjectMap.Entry<V> param1Entry);
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
/* 184 */     return (ObjectSortedSet)byte2ObjectEntrySet();
/*     */   }
/*     */   
/*     */   Byte2ObjectSortedMap<V> subMap(byte paramByte1, byte paramByte2);
/*     */   
/*     */   Byte2ObjectSortedMap<V> headMap(byte paramByte);
/*     */   
/*     */   Byte2ObjectSortedMap<V> tailMap(byte paramByte);
/*     */   
/*     */   byte firstByteKey();
/*     */   
/*     */   byte lastByteKey();
/*     */   
/*     */   ObjectSortedSet<Byte2ObjectMap.Entry<V>> byte2ObjectEntrySet();
/*     */   
/*     */   ByteSortedSet keySet();
/*     */   
/*     */   ObjectCollection<V> values();
/*     */   
/*     */   ByteComparator comparator();
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\Byte2ObjectSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */