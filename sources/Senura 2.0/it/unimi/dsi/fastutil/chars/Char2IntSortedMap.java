/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
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
/*     */ public interface Char2IntSortedMap
/*     */   extends Char2IntMap, SortedMap<Character, Integer>
/*     */ {
/*     */   @Deprecated
/*     */   default Char2IntSortedMap subMap(Character from, Character to) {
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
/*     */   default Char2IntSortedMap headMap(Character to) {
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
/*     */   default Char2IntSortedMap tailMap(Character from) {
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
/*     */     extends ObjectSortedSet<Char2IntMap.Entry>, Char2IntMap.FastEntrySet
/*     */   {
/*     */     ObjectBidirectionalIterator<Char2IntMap.Entry> fastIterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ObjectBidirectionalIterator<Char2IntMap.Entry> fastIterator(Char2IntMap.Entry param1Entry);
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
/*     */   default ObjectSortedSet<Map.Entry<Character, Integer>> entrySet() {
/* 184 */     return (ObjectSortedSet)char2IntEntrySet();
/*     */   }
/*     */   
/*     */   Char2IntSortedMap subMap(char paramChar1, char paramChar2);
/*     */   
/*     */   Char2IntSortedMap headMap(char paramChar);
/*     */   
/*     */   Char2IntSortedMap tailMap(char paramChar);
/*     */   
/*     */   char firstCharKey();
/*     */   
/*     */   char lastCharKey();
/*     */   
/*     */   ObjectSortedSet<Char2IntMap.Entry> char2IntEntrySet();
/*     */   
/*     */   CharSortedSet keySet();
/*     */   
/*     */   IntCollection values();
/*     */   
/*     */   CharComparator comparator();
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2IntSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */