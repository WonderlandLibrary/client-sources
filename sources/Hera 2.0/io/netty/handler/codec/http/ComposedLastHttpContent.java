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
/*    */ final class ComposedLastHttpContent
/*    */   implements LastHttpContent
/*    */ {
/*    */   private final HttpHeaders trailingHeaders;
/*    */   private DecoderResult result;
/*    */   
/*    */   ComposedLastHttpContent(HttpHeaders trailingHeaders) {
/* 28 */     this.trailingHeaders = trailingHeaders;
/*    */   }
/*    */   
/*    */   public HttpHeaders trailingHeaders() {
/* 32 */     return this.trailingHeaders;
/*    */   }
/*    */ 
/*    */   
/*    */   public LastHttpContent copy() {
/* 37 */     LastHttpContent content = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER);
/* 38 */     content.trailingHeaders().set(trailingHeaders());
/* 39 */     return content;
/*    */   }
/*    */ 
/*    */   
/*    */   public LastHttpContent retain(int increment) {
/* 44 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public LastHttpContent retain() {
/* 49 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpContent duplicate() {
/* 54 */     return copy();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuf content() {
/* 59 */     return Unpooled.EMPTY_BUFFER;
/*    */   }
/*    */ 
/*    */   
/*    */   public DecoderResult getDecoderResult() {
/* 64 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDecoderResult(DecoderResult result) {
/* 69 */     this.result = result;
/*    */   }
/*    */ 
/*    */   
/*    */   public int refCnt() {
/* 74 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean release() {
/* 79 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean release(int decrement) {
/* 84 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\ComposedLastHttpContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */