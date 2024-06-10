/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public interface ByteCollection
/*     */   extends Collection<Byte>, ByteIterable
/*     */ {
/*     */   @Deprecated
/*     */   default boolean add(Byte key) {
/*  76 */     return add(key.byteValue());
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
/*  88 */     return contains(((Byte)key).byteValue());
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
/* 100 */     return rem(((Byte)key).byteValue());
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
/*     */   default boolean removeIf(Predicate<? super Byte> filter) {
/* 181 */     return removeIf(key -> filter.test(Byte.valueOf(SafeMath.safeIntToByte(key))));
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
/* 196 */     ByteIterator each = iterator();
/* 197 */     while (each.hasNext()) {
/* 198 */       if (filter.test(each.nextByte())) {
/* 199 */         each.remove();
/* 200 */         removed = true;
/*     */       } 
/*     */     } 
/* 203 */     return removed;
/*     */   }
/*     */   
/*     */   ByteIterator iterator();
/*     */   
/*     */   boolean add(byte paramByte);
/*     */   
/*     */   boolean contains(byte paramByte);
/*     */   
/*     */   boolean rem(byte paramByte);
/*     */   
/*     */   byte[] toByteArray();
/*     */   
/*     */   @Deprecated
/*     */   byte[] toByteArray(byte[] paramArrayOfbyte);
/*     */   
/*     */   byte[] toArray(byte[] paramArrayOfbyte);
/*     */   
/*     */   boolean addAll(ByteCollection paramByteCollection);
/*     */   
/*     */   boolean containsAll(ByteCollection paramByteCollection);
/*     */   
/*     */   boolean removeAll(ByteCollection paramByteCollection);
/*     */   
/*     */   boolean retainAll(ByteCollection paramByteCollection);
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\ByteCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */