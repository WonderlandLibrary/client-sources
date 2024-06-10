/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.SortedSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface FloatSortedSet
/*     */   extends FloatSet, SortedSet<Float>, FloatBidirectionalIterable
/*     */ {
/*     */   @Deprecated
/*     */   default FloatSortedSet subSet(Float from, Float to) {
/* 138 */     return subSet(from.floatValue(), to.floatValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default FloatSortedSet headSet(Float to) {
/* 148 */     return headSet(to.floatValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default FloatSortedSet tailSet(Float from) {
/* 158 */     return tailSet(from.floatValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Float first() {
/* 168 */     return Float.valueOf(firstFloat());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Float last() {
/* 178 */     return Float.valueOf(lastFloat());
/*     */   }
/*     */   
/*     */   FloatBidirectionalIterator iterator(float paramFloat);
/*     */   
/*     */   FloatBidirectionalIterator iterator();
/*     */   
/*     */   FloatSortedSet subSet(float paramFloat1, float paramFloat2);
/*     */   
/*     */   FloatSortedSet headSet(float paramFloat);
/*     */   
/*     */   FloatSortedSet tailSet(float paramFloat);
/*     */   
/*     */   FloatComparator comparator();
/*     */   
/*     */   float firstFloat();
/*     */   
/*     */   float lastFloat();
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatSortedSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */