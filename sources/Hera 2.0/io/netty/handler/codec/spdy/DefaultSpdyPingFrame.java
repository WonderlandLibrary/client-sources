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
/*    */ public class DefaultSpdyPingFrame
/*    */   implements SpdyPingFrame
/*    */ {
/*    */   private int id;
/*    */   
/*    */   public DefaultSpdyPingFrame(int id) {
/* 33 */     setId(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public int id() {
/* 38 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public SpdyPingFrame setId(int id) {
/* 43 */     this.id = id;
/* 44 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 49 */     StringBuilder buf = new StringBuilder();
/* 50 */     buf.append(StringUtil.simpleClassName(this));
/* 51 */     buf.append(StringUtil.NEWLINE);
/* 52 */     buf.append("--> ID = ");
/* 53 */     buf.append(id());
/* 54 */     return buf.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\DefaultSpdyPingFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */