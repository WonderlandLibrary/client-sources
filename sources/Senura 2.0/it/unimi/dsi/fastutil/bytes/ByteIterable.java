/*    */ package it.unimi.dsi.fastutil.bytes;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.IntConsumer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ByteIterable
/*    */   extends Iterable<Byte>
/*    */ {
/*    */   default void forEach(IntConsumer action) {
/* 72 */     Objects.requireNonNull(action);
/* 73 */     for (ByteIterator iterator = iterator(); iterator.hasNext();) {
/* 74 */       action.accept(iterator.nextByte());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default void forEach(final Consumer<? super Byte> action) {
/* 84 */     forEach(new IntConsumer() {
/*    */           public void accept(int key) {
/* 86 */             action.accept(Byte.valueOf((byte)key));
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   ByteIterator iterator();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\ByteIterable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */