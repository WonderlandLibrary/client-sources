/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CharComparators
/*     */ {
/*     */   protected static class NaturalImplicitComparator
/*     */     implements CharComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(char a, char b) {
/*  30 */       return Character.compare(a, b);
/*     */     }
/*     */     
/*     */     public CharComparator reversed() {
/*  34 */       return CharComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  37 */       return CharComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  41 */   public static final CharComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
/*     */   
/*     */   protected static class OppositeImplicitComparator implements CharComparator, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(char a, char b) {
/*  47 */       return -Character.compare(a, b);
/*     */     }
/*     */     
/*     */     public CharComparator reversed() {
/*  51 */       return CharComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  54 */       return CharComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  58 */   public static final CharComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
/*     */   
/*     */   protected static class OppositeComparator implements CharComparator, Serializable { private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected OppositeComparator(CharComparator c) {
/*  63 */       this.comparator = c;
/*     */     }
/*     */     final CharComparator comparator;
/*     */     public final int compare(char a, char b) {
/*  67 */       return this.comparator.compare(b, a);
/*     */     }
/*     */     
/*     */     public final CharComparator reversed() {
/*  71 */       return this.comparator;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharComparator oppositeComparator(CharComparator c) {
/*  82 */     if (c instanceof OppositeComparator)
/*  83 */       return ((OppositeComparator)c).comparator; 
/*  84 */     return new OppositeComparator(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharComparator asCharComparator(final Comparator<? super Character> c) {
/*  95 */     if (c == null || c instanceof CharComparator)
/*  96 */       return (CharComparator)c; 
/*  97 */     return new CharComparator()
/*     */       {
/*     */         public int compare(char x, char y) {
/* 100 */           return c.compare(Character.valueOf(x), Character.valueOf(y));
/*     */         }
/*     */ 
/*     */         
/*     */         public int compare(Character x, Character y) {
/* 105 */           return c.compare(x, y);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\CharComparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */