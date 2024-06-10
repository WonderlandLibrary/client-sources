/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ public final class DoubleComparators
/*     */ {
/*     */   protected static class NaturalImplicitComparator
/*     */     implements DoubleComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(double a, double b) {
/*  30 */       return Double.compare(a, b);
/*     */     }
/*     */     
/*     */     public DoubleComparator reversed() {
/*  34 */       return DoubleComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  37 */       return DoubleComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  41 */   public static final DoubleComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
/*     */   
/*     */   protected static class OppositeImplicitComparator implements DoubleComparator, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(double a, double b) {
/*  47 */       return -Double.compare(a, b);
/*     */     }
/*     */     
/*     */     public DoubleComparator reversed() {
/*  51 */       return DoubleComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  54 */       return DoubleComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  58 */   public static final DoubleComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
/*     */   
/*     */   protected static class OppositeComparator implements DoubleComparator, Serializable { private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected OppositeComparator(DoubleComparator c) {
/*  63 */       this.comparator = c;
/*     */     }
/*     */     final DoubleComparator comparator;
/*     */     public final int compare(double a, double b) {
/*  67 */       return this.comparator.compare(b, a);
/*     */     }
/*     */     
/*     */     public final DoubleComparator reversed() {
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
/*     */   public static DoubleComparator oppositeComparator(DoubleComparator c) {
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
/*     */   public static DoubleComparator asDoubleComparator(final Comparator<? super Double> c) {
/*  95 */     if (c == null || c instanceof DoubleComparator)
/*  96 */       return (DoubleComparator)c; 
/*  97 */     return new DoubleComparator()
/*     */       {
/*     */         public int compare(double x, double y) {
/* 100 */           return c.compare(Double.valueOf(x), Double.valueOf(y));
/*     */         }
/*     */ 
/*     */         
/*     */         public int compare(Double x, Double y) {
/* 105 */           return c.compare(x, y);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleComparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */