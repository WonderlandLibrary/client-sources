/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufHolder;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import io.netty.handler.codec.DecoderResult;
/*    */ import io.netty.util.ReferenceCounted;
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
/*    */ public interface LastHttpContent
/*    */   extends HttpContent
/*    */ {
/* 30 */   public static final LastHttpContent EMPTY_LAST_CONTENT = new LastHttpContent()
/*    */     {
/*    */       public ByteBuf content()
/*    */       {
/* 34 */         return Unpooled.EMPTY_BUFFER;
/*    */       }
/*    */ 
/*    */       
/*    */       public LastHttpContent copy() {
/* 39 */         return EMPTY_LAST_CONTENT;
/*    */       }
/*    */ 
/*    */       
/*    */       public LastHttpContent duplicate() {
/* 44 */         return this;
/*    */       }
/*    */ 
/*    */       
/*    */       public HttpHeaders trailingHeaders() {
/* 49 */         return HttpHeaders.EMPTY_HEADERS;
/*    */       }
/*    */ 
/*    */       
/*    */       public DecoderResult getDecoderResult() {
/* 54 */         return DecoderResult.SUCCESS;
/*    */       }
/*    */ 
/*    */       
/*    */       public void setDecoderResult(DecoderResult result) {
/* 59 */         throw new UnsupportedOperationException("read only");
/*    */       }
/*    */ 
/*    */       
/*    */       public int refCnt() {
/* 64 */         return 1;
/*    */       }
/*    */ 
/*    */       
/*    */       public LastHttpContent retain() {
/* 69 */         return this;
/*    */       }
/*    */ 
/*    */       
/*    */       public LastHttpContent retain(int increment) {
/* 74 */         return this;
/*    */       }
/*    */ 
/*    */       
/*    */       public boolean release() {
/* 79 */         return false;
/*    */       }
/*    */ 
/*    */       
/*    */       public boolean release(int decrement) {
/* 84 */         return false;
/*    */       }
/*    */ 
/*    */       
/*    */       public String toString() {
/* 89 */         return "EmptyLastHttpContent";
/*    */       }
/*    */     };
/*    */   
/*    */   HttpHeaders trailingHeaders();
/*    */   
/*    */   LastHttpContent copy();
/*    */   
/*    */   LastHttpContent retain(int paramInt);
/*    */   
/*    */   LastHttpContent retain();
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\LastHttpContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */