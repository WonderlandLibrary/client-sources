/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public final class ShortComparators
/*     */ {
/*     */   protected static class NaturalImplicitComparator
/*     */     implements ShortComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(short a, short b) {
/*  30 */       return Short.compare(a, b);
/*     */     }
/*     */     
/*     */     public ShortComparator reversed() {
/*  34 */       return ShortComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  37 */       return ShortComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  41 */   public static final ShortComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
/*     */   
/*     */   protected static class OppositeImplicitComparator implements ShortComparator, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(short a, short b) {
/*  47 */       return -Short.compare(a, b);
/*     */     }
/*     */     
/*     */     public ShortComparator reversed() {
/*  51 */       return ShortComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  54 */       return ShortComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  58 */   public static final ShortComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
/*     */   
/*     */   protected static class OppositeComparator implements ShortComparator, Serializable { private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected OppositeComparator(ShortComparator c) {
/*  63 */       this.comparator = c;
/*     */     }
/*     */     final ShortComparator comparator;
/*     */     public final int compare(short a, short b) {
/*  67 */       return this.comparator.compare(b, a);
/*     */     }
/*     */     
/*     */     public final ShortComparator reversed() {
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
/*     */   public static ShortComparator oppositeComparator(ShortComparator c) {
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
/*     */   public static ShortComparator asShortComparator(final Comparator<? super Short> c) {
/*  95 */     if (c == null || c instanceof ShortComparator)
/*  96 */       return (ShortComparator)c; 
/*  97 */     return new ShortComparator()
/*     */       {
/*     */         public int compare(short x, short y) {
/* 100 */           return c.compare(Short.valueOf(x), Short.valueOf(y));
/*     */         }
/*     */ 
/*     */         
/*     */         public int compare(Short x, Short y) {
/* 105 */           return c.compare(x, y);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\ShortComparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */