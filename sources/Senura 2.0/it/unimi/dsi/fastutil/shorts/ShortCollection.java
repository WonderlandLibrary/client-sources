/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.function.IntPredicate;
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
/*     */ public interface ShortCollection
/*     */   extends Collection<Short>, ShortIterable
/*     */ {
/*     */   @Deprecated
/*     */   default boolean add(Short key) {
/*  76 */     return add(key.shortValue());
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
/*  88 */     return contains(((Short)key).shortValue());
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
/* 100 */     return rem(((Short)key).shortValue());
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
/*     */   default boolean removeIf(Predicate<? super Short> filter) {
/* 181 */     return removeIf(key -> filter.test(Short.valueOf(SafeMath.safeIntToShort(key))));
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
/*     */   default boolean removeIf(IntPredicate filter) {
/* 194 */     Objects.requireNonNull(filter);
/* 195 */     boolean removed = false;
/* 196 */     ShortIterator each = iterator();
/* 197 */     while (each.hasNext()) {
/* 198 */       if (filter.test(each.nextShort())) {
/* 199 */         each.remove();
/* 200 */         removed = true;
/*     */       } 
/*     */     } 
/* 203 */     return removed;
/*     */   }
/*     */   
/*     */   ShortIterator iterator();
/*     */   
/*     */   boolean add(short paramShort);
/*     */   
/*     */   boolean contains(short paramShort);
/*     */   
/*     */   boolean rem(short paramShort);
/*     */   
/*     */   short[] toShortArray();
/*     */   
/*     */   @Deprecated
/*     */   short[] toShortArray(short[] paramArrayOfshort);
/*     */   
/*     */   short[] toArray(short[] paramArrayOfshort);
/*     */   
/*     */   boolean addAll(ShortCollection paramShortCollection);
/*     */   
/*     */   boolean containsAll(ShortCollection paramShortCollection);
/*     */   
/*     */   boolean removeAll(ShortCollection paramShortCollection);
/*     */   
/*     */   boolean retainAll(ShortCollection paramShortCollection);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\ShortCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */