/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public final class IntComparators
/*     */ {
/*     */   protected static class NaturalImplicitComparator
/*     */     implements IntComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(int a, int b) {
/*  30 */       return Integer.compare(a, b);
/*     */     }
/*     */     
/*     */     public IntComparator reversed() {
/*  34 */       return IntComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  37 */       return IntComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  41 */   public static final IntComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
/*     */   
/*     */   protected static class OppositeImplicitComparator implements IntComparator, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(int a, int b) {
/*  47 */       return -Integer.compare(a, b);
/*     */     }
/*     */     
/*     */     public IntComparator reversed() {
/*  51 */       return IntComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  54 */       return IntComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  58 */   public static final IntComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
/*     */   
/*     */   protected static class OppositeComparator implements IntComparator, Serializable { private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected OppositeComparator(IntComparator c) {
/*  63 */       this.comparator = c;
/*     */     }
/*     */     final IntComparator comparator;
/*     */     public final int compare(int a, int b) {
/*  67 */       return this.comparator.compare(b, a);
/*     */     }
/*     */     
/*     */     public final IntComparator reversed() {
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
/*     */   public static IntComparator oppositeComparator(IntComparator c) {
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
/*     */   public static IntComparator asIntComparator(final Comparator<? super Integer> c) {
/*  95 */     if (c == null || c instanceof IntComparator)
/*  96 */       return (IntComparator)c; 
/*  97 */     return new IntComparator()
/*     */       {
/*     */         public int compare(int x, int y) {
/* 100 */           return c.compare(Integer.valueOf(x), Integer.valueOf(y));
/*     */         }
/*     */ 
/*     */         
/*     */         public int compare(Integer x, Integer y) {
/* 105 */           return c.compare(x, y);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntComparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */