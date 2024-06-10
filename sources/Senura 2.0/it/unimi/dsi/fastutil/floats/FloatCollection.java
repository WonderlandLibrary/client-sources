/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
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
/*     */ public interface FloatCollection
/*     */   extends Collection<Float>, FloatIterable
/*     */ {
/*     */   @Deprecated
/*     */   default boolean add(Float key) {
/*  76 */     return add(key.floatValue());
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
/*  88 */     return contains(((Float)key).floatValue());
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
/* 100 */     return rem(((Float)key).floatValue());
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
/*     */   default boolean removeIf(Predicate<? super Float> filter) {
/* 181 */     return removeIf(key -> filter.test(Float.valueOf(SafeMath.safeDoubleToFloat(key))));
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
/*     */   default boolean removeIf(DoublePredicate filter) {
/* 194 */     Objects.requireNonNull(filter);
/* 195 */     boolean removed = false;
/* 196 */     FloatIterator each = iterator();
/* 197 */     while (each.hasNext()) {
/* 198 */       if (filter.test(each.nextFloat())) {
/* 199 */         each.remove();
/* 200 */         removed = true;
/*     */       } 
/*     */     } 
/* 203 */     return removed;
/*     */   }
/*     */   
/*     */   FloatIterator iterator();
/*     */   
/*     */   boolean add(float paramFloat);
/*     */   
/*     */   boolean contains(float paramFloat);
/*     */   
/*     */   boolean rem(float paramFloat);
/*     */   
/*     */   float[] toFloatArray();
/*     */   
/*     */   @Deprecated
/*     */   float[] toFloatArray(float[] paramArrayOffloat);
/*     */   
/*     */   float[] toArray(float[] paramArrayOffloat);
/*     */   
/*     */   boolean addAll(FloatCollection paramFloatCollection);
/*     */   
/*     */   boolean containsAll(FloatCollection paramFloatCollection);
/*     */   
/*     */   boolean removeAll(FloatCollection paramFloatCollection);
/*     */   
/*     */   boolean retainAll(FloatCollection paramFloatCollection);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */