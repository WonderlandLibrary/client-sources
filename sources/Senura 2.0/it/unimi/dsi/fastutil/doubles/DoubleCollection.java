/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.function.DoublePredicate;
/*     */ import java.util.function.Predicate;
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
/*     */ public interface DoubleCollection
/*     */   extends Collection<Double>, DoubleIterable
/*     */ {
/*     */   @Deprecated
/*     */   default boolean add(Double key) {
/*  76 */     return add(key.doubleValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean contains(Object key) {
/*  86 */     if (key == null)
/*  87 */       return false; 
/*  88 */     return contains(((Double)key).doubleValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean remove(Object key) {
/*  98 */     if (key == null)
/*  99 */       return false; 
/* 100 */     return rem(((Double)key).doubleValue());
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean removeIf(Predicate<? super Double> filter) {
/* 181 */     return removeIf(key -> filter.test(Double.valueOf(key)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean removeIf(DoublePredicate filter) {
/* 193 */     Objects.requireNonNull(filter);
/* 194 */     boolean removed = false;
/* 195 */     DoubleIterator each = iterator();
/* 196 */     while (each.hasNext()) {
/* 197 */       if (filter.test(each.nextDouble())) {
/* 198 */         each.remove();
/* 199 */         removed = true;
/*     */       } 
/*     */     } 
/* 202 */     return removed;
/*     */   }
/*     */   
/*     */   DoubleIterator iterator();
/*     */   
/*     */   boolean add(double paramDouble);
/*     */   
/*     */   boolean contains(double paramDouble);
/*     */   
/*     */   boolean rem(double paramDouble);
/*     */   
/*     */   double[] toDoubleArray();
/*     */   
/*     */   @Deprecated
/*     */   double[] toDoubleArray(double[] paramArrayOfdouble);
/*     */   
/*     */   double[] toArray(double[] paramArrayOfdouble);
/*     */   
/*     */   boolean addAll(DoubleCollection paramDoubleCollection);
/*     */   
/*     */   boolean containsAll(DoubleCollection paramDoubleCollection);
/*     */   
/*     */   boolean removeAll(DoubleCollection paramDoubleCollection);
/*     */   
/*     */   boolean retainAll(DoubleCollection paramDoubleCollection);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */