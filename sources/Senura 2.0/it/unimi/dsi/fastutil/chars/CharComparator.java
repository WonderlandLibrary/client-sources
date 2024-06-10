/*    */ package it.unimi.dsi.fastutil.chars;
/*    */ 
/*    */ import java.lang.invoke.SerializedLambda;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface CharComparator
/*    */   extends Comparator<Character>
/*    */ {
/*    */   default CharComparator reversed() {
/* 43 */     return CharComparators.oppositeComparator(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default int compare(Character ok1, Character ok2) {
/* 55 */     return compare(ok1.charValue(), ok2.charValue());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default CharComparator thenComparing(CharComparator second) {
/* 64 */     return (k1, k2) -> {
/*    */         int comp = compare(k1, k2);
/*    */         return (comp == 0) ? second.compare(k1, k2) : comp;
/*    */       };
/*    */   }
/*    */   
/*    */   default Comparator<Character> thenComparing(Comparator<? super Character> second) {
/* 71 */     if (second instanceof CharComparator)
/* 72 */       return thenComparing((CharComparator)second); 
/* 73 */     return super.thenComparing(second);
/*    */   }
/*    */   
/*    */   int compare(char paramChar1, char paramChar2);
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\CharComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */