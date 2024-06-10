/*     */ package it.unimi.dsi.fastutil;
/*     */ 
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface BigList<K>
/*     */   extends Collection<K>, Size64
/*     */ {
/*     */   K get(long paramLong);
/*     */   
/*     */   K remove(long paramLong);
/*     */   
/*     */   K set(long paramLong, K paramK);
/*     */   
/*     */   void add(long paramLong, K paramK);
/*     */   
/*     */   void size(long paramLong);
/*     */   
/*     */   boolean addAll(long paramLong, Collection<? extends K> paramCollection);
/*     */   
/*     */   long indexOf(Object paramObject);
/*     */   
/*     */   long lastIndexOf(Object paramObject);
/*     */   
/*     */   BigListIterator<K> listIterator();
/*     */   
/*     */   BigListIterator<K> listIterator(long paramLong);
/*     */   
/*     */   BigList<K> subList(long paramLong1, long paramLong2);
/*     */   
/*     */   @Deprecated
/*     */   default int size() {
/* 142 */     return super.size();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\BigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */