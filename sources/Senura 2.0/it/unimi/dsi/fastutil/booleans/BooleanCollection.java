/*     */ package it.unimi.dsi.fastutil.booleans;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface BooleanCollection
/*     */   extends Collection<Boolean>, BooleanIterable
/*     */ {
/*     */   @Deprecated
/*     */   default boolean add(Boolean key) {
/*  76 */     return add(key.booleanValue());
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
/*  88 */     return contains(((Boolean)key).booleanValue());
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
/* 100 */     return rem(((Boolean)key).booleanValue());
/*     */   }
/*     */   
/*     */   BooleanIterator iterator();
/*     */   
/*     */   boolean add(boolean paramBoolean);
/*     */   
/*     */   boolean contains(boolean paramBoolean);
/*     */   
/*     */   boolean rem(boolean paramBoolean);
/*     */   
/*     */   boolean[] toBooleanArray();
/*     */   
/*     */   @Deprecated
/*     */   boolean[] toBooleanArray(boolean[] paramArrayOfboolean);
/*     */   
/*     */   boolean[] toArray(boolean[] paramArrayOfboolean);
/*     */   
/*     */   boolean addAll(BooleanCollection paramBooleanCollection);
/*     */   
/*     */   boolean containsAll(BooleanCollection paramBooleanCollection);
/*     */   
/*     */   boolean removeAll(BooleanCollection paramBooleanCollection);
/*     */   
/*     */   boolean retainAll(BooleanCollection paramBooleanCollection);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\booleans\BooleanCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */