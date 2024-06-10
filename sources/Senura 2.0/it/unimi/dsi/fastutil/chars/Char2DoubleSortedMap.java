/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
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
/*     */ public interface Char2DoubleSortedMap
/*     */   extends Char2DoubleMap, SortedMap<Character, Double>
/*     */ {
/*     */   @Deprecated
/*     */   default Char2DoubleSortedMap subMap(Character from, Character to) {
/*  91 */     return subMap(from.charValue(), to.charValue());
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
/*     */   default Char2DoubleSortedMap headMap(Character to) {
/* 104 */     return headMap(to.charValue());
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
/*     */   default Char2DoubleSortedMap tailMap(Character from) {
/* 117 */     return tailMap(from.charValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Character firstKey() {
/* 127 */     return Character.valueOf(firstCharKey());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Character lastKey() {
/* 137 */     return Character.valueOf(lastCharKey());
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
/*     */     extends ObjectSortedSet<Char2DoubleMap.Entry>, Char2DoubleMap.FastEntrySet
/*     */   {
/*     */     ObjectBidirectionalIterator<Char2DoubleMap.Entry> fastIterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ObjectBidirectionalIterator<Char2DoubleMap.Entry> fastIterator(Char2DoubleMap.Entry param1Entry);
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
/*     */   default ObjectSortedSet<Map.Entry<Character, Double>> entrySet() {
/* 184 */     return (ObjectSortedSet)char2DoubleEntrySet();
/*     */   }
/*     */   
/*     */   Char2DoubleSortedMap subMap(char paramChar1, char paramChar2);
/*     */   
/*     */   Char2DoubleSortedMap headMap(char paramChar);
/*     */   
/*     */   Char2DoubleSortedMap tailMap(char paramChar);
/*     */   
/*     */   char firstCharKey();
/*     */   
/*     */   char lastCharKey();
/*     */   
/*     */   ObjectSortedSet<Char2DoubleMap.Entry> char2DoubleEntrySet();
/*     */   
/*     */   CharSortedSet keySet();
/*     */   
/*     */   DoubleCollection values();
/*     */   
/*     */   CharComparator comparator();
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2DoubleSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */