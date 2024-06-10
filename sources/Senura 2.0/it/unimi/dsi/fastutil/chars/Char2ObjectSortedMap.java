/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public interface Char2ObjectSortedMap<V>
/*     */   extends Char2ObjectMap<V>, SortedMap<Character, V>
/*     */ {
/*     */   @Deprecated
/*     */   default Char2ObjectSortedMap<V> subMap(Character from, Character to) {
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
/*     */   default Char2ObjectSortedMap<V> headMap(Character to) {
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
/*     */   default Char2ObjectSortedMap<V> tailMap(Character from) {
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
/*     */   public static interface FastSortedEntrySet<V>
/*     */     extends ObjectSortedSet<Char2ObjectMap.Entry<V>>, Char2ObjectMap.FastEntrySet<V>
/*     */   {
/*     */     ObjectBidirectionalIterator<Char2ObjectMap.Entry<V>> fastIterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ObjectBidirectionalIterator<Char2ObjectMap.Entry<V>> fastIterator(Char2ObjectMap.Entry<V> param1Entry);
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
/*     */   default ObjectSortedSet<Map.Entry<Character, V>> entrySet() {
/* 184 */     return (ObjectSortedSet)char2ObjectEntrySet();
/*     */   }
/*     */   
/*     */   Char2ObjectSortedMap<V> subMap(char paramChar1, char paramChar2);
/*     */   
/*     */   Char2ObjectSortedMap<V> headMap(char paramChar);
/*     */   
/*     */   Char2ObjectSortedMap<V> tailMap(char paramChar);
/*     */   
/*     */   char firstCharKey();
/*     */   
/*     */   char lastCharKey();
/*     */   
/*     */   ObjectSortedSet<Char2ObjectMap.Entry<V>> char2ObjectEntrySet();
/*     */   
/*     */   CharSortedSet keySet();
/*     */   
/*     */   ObjectCollection<V> values();
/*     */   
/*     */   CharComparator comparator();
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\Char2ObjectSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */