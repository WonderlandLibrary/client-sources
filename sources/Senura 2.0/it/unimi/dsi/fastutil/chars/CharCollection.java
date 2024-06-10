/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public interface CharCollection
/*     */   extends Collection<Character>, CharIterable
/*     */ {
/*     */   @Deprecated
/*     */   default boolean add(Character key) {
/*  76 */     return add(key.charValue());
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
/*  88 */     return contains(((Character)key).charValue());
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
/* 100 */     return rem(((Character)key).charValue());
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
/*     */   default boolean removeIf(Predicate<? super Character> filter) {
/* 181 */     return removeIf(key -> filter.test(Character.valueOf(SafeMath.safeIntToChar(key))));
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
/* 196 */     CharIterator each = iterator();
/* 197 */     while (each.hasNext()) {
/* 198 */       if (filter.test(each.nextChar())) {
/* 199 */         each.remove();
/* 200 */         removed = true;
/*     */       } 
/*     */     } 
/* 203 */     return removed;
/*     */   }
/*     */   
/*     */   CharIterator iterator();
/*     */   
/*     */   boolean add(char paramChar);
/*     */   
/*     */   boolean contains(char paramChar);
/*     */   
/*     */   boolean rem(char paramChar);
/*     */   
/*     */   char[] toCharArray();
/*     */   
/*     */   @Deprecated
/*     */   char[] toCharArray(char[] paramArrayOfchar);
/*     */   
/*     */   char[] toArray(char[] paramArrayOfchar);
/*     */   
/*     */   boolean addAll(CharCollection paramCharCollection);
/*     */   
/*     */   boolean containsAll(CharCollection paramCharCollection);
/*     */   
/*     */   boolean removeAll(CharCollection paramCharCollection);
/*     */   
/*     */   boolean retainAll(CharCollection paramCharCollection);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\CharCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */