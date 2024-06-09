/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufHolder;
/*    */ import io.netty.util.ReferenceCounted;
/*    */ import io.netty.util.internal.StringUtil;
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
/*    */ public class DefaultHttpContent
/*    */   extends DefaultHttpObject
/*    */   implements HttpContent
/*    */ {
/*    */   private final ByteBuf content;
/*    */   
/*    */   public DefaultHttpContent(ByteBuf content) {
/* 32 */     if (content == null) {
/* 33 */       throw new NullPointerException("content");
/*    */     }
/* 35 */     this.content = content;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuf content() {
/* 40 */     return this.content;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpContent copy() {
/* 45 */     return new DefaultHttpContent(this.content.copy());
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpContent duplicate() {
/* 50 */     return new DefaultHttpContent(this.content.duplicate());
/*    */   }
/*    */ 
/*    */   
/*    */   public int refCnt() {
/* 55 */     return this.content.refCnt();
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpContent retain() {
/* 60 */     this.content.retain();
/* 61 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpContent retain(int increment) {
/* 66 */     this.content.retain(increment);
/* 67 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean release() {
/* 72 */     return this.content.release();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean release(int decrement) {
/* 77 */     return this.content.release(decrement);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 82 */     return StringUtil.simpleClassName(this) + "(data: " + content() + ", decoderResult: " + getDecoderResult() + ')';
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\DefaultHttpContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */