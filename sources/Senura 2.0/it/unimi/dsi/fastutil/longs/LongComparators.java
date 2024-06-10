/*     */ package it.unimi.dsi.fastutil.longs;
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
/*     */ public final class LongComparators
/*     */ {
/*     */   protected static class NaturalImplicitComparator
/*     */     implements LongComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(long a, long b) {
/*  30 */       return Long.compare(a, b);
/*     */     }
/*     */     
/*     */     public LongComparator reversed() {
/*  34 */       return LongComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  37 */       return LongComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  41 */   public static final LongComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
/*     */   
/*     */   protected static class OppositeImplicitComparator implements LongComparator, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(long a, long b) {
/*  47 */       return -Long.compare(a, b);
/*     */     }
/*     */     
/*     */     public LongComparator reversed() {
/*  51 */       return LongComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  54 */       return LongComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  58 */   public static final LongComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
/*     */   
/*     */   protected static class OppositeComparator implements LongComparator, Serializable { private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected OppositeComparator(LongComparator c) {
/*  63 */       this.comparator = c;
/*     */     }
/*     */     final LongComparator comparator;
/*     */     public final int compare(long a, long b) {
/*  67 */       return this.comparator.compare(b, a);
/*     */     }
/*     */     
/*     */     public final LongComparator reversed() {
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
/*     */   public static LongComparator oppositeComparator(LongComparator c) {
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
/*     */   public static LongComparator asLongComparator(final Comparator<? super Long> c) {
/*  95 */     if (c == null || c instanceof LongComparator)
/*  96 */       return (LongComparator)c; 
/*  97 */     return new LongComparator()
/*     */       {
/*     */         public int compare(long x, long y) {
/* 100 */           return c.compare(Long.valueOf(x), Long.valueOf(y));
/*     */         }
/*     */ 
/*     */         
/*     */         public int compare(Long x, Long y) {
/* 105 */           return c.compare(x, y);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongComparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */