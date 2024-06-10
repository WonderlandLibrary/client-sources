/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public final class ByteComparators
/*     */ {
/*     */   protected static class NaturalImplicitComparator
/*     */     implements ByteComparator, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(byte a, byte b) {
/*  30 */       return Byte.compare(a, b);
/*     */     }
/*     */     
/*     */     public ByteComparator reversed() {
/*  34 */       return ByteComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  37 */       return ByteComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  41 */   public static final ByteComparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
/*     */   
/*     */   protected static class OppositeImplicitComparator implements ByteComparator, Serializable {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public final int compare(byte a, byte b) {
/*  47 */       return -Byte.compare(a, b);
/*     */     }
/*     */     
/*     */     public ByteComparator reversed() {
/*  51 */       return ByteComparators.NATURAL_COMPARATOR;
/*     */     }
/*     */     private Object readResolve() {
/*  54 */       return ByteComparators.OPPOSITE_COMPARATOR;
/*     */     }
/*     */   }
/*     */   
/*  58 */   public static final ByteComparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
/*     */   
/*     */   protected static class OppositeComparator implements ByteComparator, Serializable { private static final long serialVersionUID = 1L;
/*     */     
/*     */     protected OppositeComparator(ByteComparator c) {
/*  63 */       this.comparator = c;
/*     */     }
/*     */     final ByteComparator comparator;
/*     */     public final int compare(byte a, byte b) {
/*  67 */       return this.comparator.compare(b, a);
/*     */     }
/*     */     
/*     */     public final ByteComparator reversed() {
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
/*     */   public static ByteComparator oppositeComparator(ByteComparator c) {
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
/*     */   public static ByteComparator asByteComparator(final Comparator<? super Byte> c) {
/*  95 */     if (c == null || c instanceof ByteComparator)
/*  96 */       return (ByteComparator)c; 
/*  97 */     return new ByteComparator()
/*     */       {
/*     */         public int compare(byte x, byte y) {
/* 100 */           return c.compare(Byte.valueOf(x), Byte.valueOf(y));
/*     */         }
/*     */ 
/*     */         
/*     */         public int compare(Byte x, Byte y) {
/* 105 */           return c.compare(x, y);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\ByteComparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */