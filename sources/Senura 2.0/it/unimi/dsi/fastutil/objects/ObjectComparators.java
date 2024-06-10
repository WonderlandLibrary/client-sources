/*    */ package it.unimi.dsi.fastutil.objects;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public final class ObjectComparators
/*    */ {
/*    */   protected static class NaturalImplicitComparator
/*    */     implements Comparator, Serializable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     public final int compare(Object a, Object b) {
/* 31 */       return ((Comparable<Object>)a).compareTo(b);
/*    */     }
/*    */     
/*    */     public Comparator reversed() {
/* 35 */       return ObjectComparators.OPPOSITE_COMPARATOR;
/*    */     }
/*    */     private Object readResolve() {
/* 38 */       return ObjectComparators.NATURAL_COMPARATOR;
/*    */     }
/*    */   }
/*    */   
/* 42 */   public static final Comparator NATURAL_COMPARATOR = new NaturalImplicitComparator();
/*    */   
/*    */   protected static class OppositeImplicitComparator
/*    */     implements Comparator, Serializable {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     public final int compare(Object a, Object b) {
/* 49 */       return ((Comparable<Object>)b).compareTo(a);
/*    */     }
/*    */     
/*    */     public Comparator reversed() {
/* 53 */       return ObjectComparators.NATURAL_COMPARATOR;
/*    */     }
/*    */     private Object readResolve() {
/* 56 */       return ObjectComparators.OPPOSITE_COMPARATOR;
/*    */     }
/*    */   }
/*    */   
/* 60 */   public static final Comparator OPPOSITE_COMPARATOR = new OppositeImplicitComparator();
/*    */   
/*    */   protected static class OppositeComparator<K> implements Comparator<K>, Serializable { private static final long serialVersionUID = 1L;
/*    */     
/*    */     protected OppositeComparator(Comparator<K> c) {
/* 65 */       this.comparator = c;
/*    */     }
/*    */     final Comparator<K> comparator;
/*    */     public final int compare(K a, K b) {
/* 69 */       return this.comparator.compare(b, a);
/*    */     }
/*    */     
/*    */     public final Comparator<K> reversed() {
/* 73 */       return this.comparator;
/*    */     } }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <K> Comparator<K> oppositeComparator(Comparator<K> c) {
/* 84 */     if (c instanceof OppositeComparator)
/* 85 */       return ((OppositeComparator)c).comparator; 
/* 86 */     return new OppositeComparator<>(c);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectComparators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */