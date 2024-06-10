/*     */ package it.unimi.dsi.fastutil.booleans;
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
/*     */ public final class BooleanComparators
/*     */ {
/*     */   protected static class NaturalImplicitComparator
/*     */     implements BooleanComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(boolean a, boolean b) {
/*  30 */       return Boolean.compare(a, b);
/*     */     }
/*     */     
/*     */     public BooleanComparator reversed() {
/*  34 */       return BooleanComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  37 */       return BooleanComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  41 */   public static final BooleanComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
/*     */   
/*     */   protected static class OppositeImplicitComparator implements BooleanComparator, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(boolean a, boolean b) {
/*  47 */       return -Boolean.compare(a, b);
/*     */     }
/*     */     
/*     */     public BooleanComparator reversed() {
/*  51 */       return BooleanComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  54 */       return BooleanComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  58 */   public static final BooleanComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
/*     */   
/*     */   protected static class OppositeComparator implements BooleanComparator, Serializable { private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected OppositeComparator(BooleanComparator c) {
/*  63 */       this.comparator = c;
/*     */     }
/*     */     final BooleanComparator comparator;
/*     */     public final int compare(boolean a, boolean b) {
/*  67 */       return this.comparator.compare(b, a);
/*     */     }
/*     */     
/*     */     public final BooleanComparator reversed() {
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
/*     */   public static BooleanComparator oppositeComparator(BooleanComparator c) {
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
/*     */   public static BooleanComparator asBooleanComparator(final Comparator<? super Boolean> c) {
/*  95 */     if (c == null || c instanceof BooleanComparator)
/*  96 */       return (BooleanComparator)c; 
/*  97 */     return new BooleanComparator()
/*     */       {
/*     */         public int compare(boolean x, boolean y) {
/* 100 */           return c.compare(Boolean.valueOf(x), Boolean.valueOf(y));
/*     */         }
/*     */ 
/*     */         
/*     */         public int compare(Boolean x, Boolean y) {
/* 105 */           return c.compare(x, y);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\booleans\BooleanComparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */