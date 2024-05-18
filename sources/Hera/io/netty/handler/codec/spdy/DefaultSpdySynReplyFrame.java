/*    */ package io.netty.handler.codec.spdy;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultSpdySynReplyFrame
/*    */   extends DefaultSpdyHeadersFrame
/*    */   implements SpdySynReplyFrame
/*    */ {
/*    */   public DefaultSpdySynReplyFrame(int streamId) {
/* 32 */     super(streamId);
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdySynReplyFrame setStreamId(int streamId) {
/* 37 */     super.setStreamId(streamId);
/* 38 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdySynReplyFrame setLast(boolean last) {
/* 43 */     super.setLast(last);
/* 44 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdySynReplyFrame setInvalid() {
/* 49 */     super.setInvalid();
/* 50 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 55 */     StringBuilder buf = new StringBuilder();
/* 56 */     buf.append(StringUtil.simpleClassName(this));
/* 57 */     buf.append("(last: ");
/* 58 */     buf.append(isLast());
/* 59 */     buf.append(')');
/* 60 */     buf.append(StringUtil.NEWLINE);
/* 61 */     buf.append("--> Stream-ID = ");
/* 62 */     buf.append(streamId());
/* 63 */     buf.append(StringUtil.NEWLINE);
/* 64 */     buf.append("--> Headers:");
/* 65 */     buf.append(StringUtil.NEWLINE);
/* 66 */     appendHeaders(buf);
/*    */ 
/*    */     
/* 69 */     buf.setLength(buf.length() - StringUtil.NEWLINE.length());
/* 70 */     return buf.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\DefaultSpdySynReplyFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */