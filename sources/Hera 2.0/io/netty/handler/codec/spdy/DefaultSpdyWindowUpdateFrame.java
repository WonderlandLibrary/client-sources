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
/*    */ 
/*    */ public class DefaultSpdyWindowUpdateFrame
/*    */   implements SpdyWindowUpdateFrame
/*    */ {
/*    */   private int streamId;
/*    */   private int deltaWindowSize;
/*    */   
/*    */   public DefaultSpdyWindowUpdateFrame(int streamId, int deltaWindowSize) {
/* 35 */     setStreamId(streamId);
/* 36 */     setDeltaWindowSize(deltaWindowSize);
/*    */   }
/*    */ 
/*    */   
/*    */   public int streamId() {
/* 41 */     return this.streamId;
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdyWindowUpdateFrame setStreamId(int streamId) {
/* 46 */     if (streamId < 0) {
/* 47 */       throw new IllegalArgumentException("Stream-ID cannot be negative: " + streamId);
/*    */     }
/*    */     
/* 50 */     this.streamId = streamId;
/* 51 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public int deltaWindowSize() {
/* 56 */     return this.deltaWindowSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdyWindowUpdateFrame setDeltaWindowSize(int deltaWindowSize) {
/* 61 */     if (deltaWindowSize <= 0) {
/* 62 */       throw new IllegalArgumentException("Delta-Window-Size must be positive: " + deltaWindowSize);
/*    */     }
/*    */ 
/*    */     
/* 66 */     this.deltaWindowSize = deltaWindowSize;
/* 67 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 72 */     StringBuilder buf = new StringBuilder();
/* 73 */     buf.append(StringUtil.simpleClassName(this));
/* 74 */     buf.append(StringUtil.NEWLINE);
/* 75 */     buf.append("--> Stream-ID = ");
/* 76 */     buf.append(streamId());
/* 77 */     buf.append(StringUtil.NEWLINE);
/* 78 */     buf.append("--> Delta-Window-Size = ");
/* 79 */     buf.append(deltaWindowSize());
/* 80 */     return buf.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\DefaultSpdyWindowUpdateFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */