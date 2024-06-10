/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public final class FloatComparators
/*     */ {
/*     */   protected static class NaturalImplicitComparator
/*     */     implements FloatComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(float a, float b) {
/*  30 */       return Float.compare(a, b);
/*     */     }
/*     */     
/*     */     public FloatComparator reversed() {
/*  34 */       return FloatComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  37 */       return FloatComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  41 */   public static final FloatComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
/*     */   
/*     */   protected static class OppositeImplicitComparator implements FloatComparator, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(float a, float b) {
/*  47 */       return -Float.compare(a, b);
/*     */     }
/*     */     
/*     */     public FloatComparator reversed() {
/*  51 */       return FloatComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  54 */       return FloatComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  58 */   public static final FloatComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
/*     */   
/*     */   protected static class OppositeComparator implements FloatComparator, Serializable { private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected OppositeComparator(FloatComparator c) {
/*  63 */       this.comparator = c;
/*     */     }
/*     */     final FloatComparator comparator;
/*     */     public final int compare(float a, float b) {
/*  67 */       return this.comparator.compare(b, a);
/*     */     }
/*     */     
/*     */     public final FloatComparator reversed() {
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
/*     */   public static FloatComparator oppositeComparator(FloatComparator c) {
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
/*     */   public static FloatComparator asFloatComparator(final Comparator<? super Float> c) {
/*  95 */     if (c == null || c instanceof FloatComparator)
/*  96 */       return (FloatComparator)c; 
/*  97 */     return new FloatComparator()
/*     */       {
/*     */         public int compare(float x, float y) {
/* 100 */           return c.compare(Float.valueOf(x), Float.valueOf(y));
/*     */         }
/*     */ 
/*     */         
/*     */         public int compare(Float x, Float y) {
/* 105 */           return c.compare(x, y);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatComparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */