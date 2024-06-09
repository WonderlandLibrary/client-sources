/*    */ package io.netty.buffer;
/*    */ 
/*    */ import io.netty.util.ResourceLeak;
/*    */ import java.nio.ByteOrder;
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
/*    */ final class SimpleLeakAwareByteBuf
/*    */   extends WrappedByteBuf
/*    */ {
/*    */   private final ResourceLeak leak;
/*    */   
/*    */   SimpleLeakAwareByteBuf(ByteBuf buf, ResourceLeak leak) {
/* 28 */     super(buf);
/* 29 */     this.leak = leak;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean release() {
/* 34 */     boolean deallocated = super.release();
/* 35 */     if (deallocated) {
/* 36 */       this.leak.close();
/*    */     }
/* 38 */     return deallocated;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean release(int decrement) {
/* 43 */     boolean deallocated = super.release(decrement);
/* 44 */     if (deallocated) {
/* 45 */       this.leak.close();
/*    */     }
/* 47 */     return deallocated;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuf order(ByteOrder endianness) {
/* 52 */     this.leak.record();
/* 53 */     if (order() == endianness) {
/* 54 */       return this;
/*    */     }
/* 56 */     return new SimpleLeakAwareByteBuf(super.order(endianness), this.leak);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuf slice() {
/* 62 */     return new SimpleLeakAwareByteBuf(super.slice(), this.leak);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuf slice(int index, int length) {
/* 67 */     return new SimpleLeakAwareByteBuf(super.slice(index, length), this.leak);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuf duplicate() {
/* 72 */     return new SimpleLeakAwareByteBuf(super.duplicate(), this.leak);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuf readSlice(int length) {
/* 77 */     return new SimpleLeakAwareByteBuf(super.readSlice(length), this.leak);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\buffer\SimpleLeakAwareByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */