/*    */ package io.netty.handler.codec.spdy;
/*    */ 
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageCodec;
/*    */ import io.netty.handler.codec.http.HttpMessage;
/*    */ import io.netty.util.ReferenceCountUtil;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Queue;
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
/*    */ public class SpdyHttpResponseStreamIdHandler
/*    */   extends MessageToMessageCodec<Object, HttpMessage>
/*    */ {
/* 34 */   private static final Integer NO_ID = Integer.valueOf(-1);
/* 35 */   private final Queue<Integer> ids = new LinkedList<Integer>();
/*    */ 
/*    */   
/*    */   public boolean acceptInboundMessage(Object msg) throws Exception {
/* 39 */     return (msg instanceof HttpMessage || msg instanceof SpdyRstStreamFrame);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, HttpMessage msg, List<Object> out) throws Exception {
/* 44 */     Integer id = this.ids.poll();
/* 45 */     if (id != null && id.intValue() != NO_ID.intValue() && !msg.headers().contains("X-SPDY-Stream-ID")) {
/* 46 */       SpdyHttpHeaders.setStreamId(msg, id.intValue());
/*    */     }
/*    */     
/* 49 */     out.add(ReferenceCountUtil.retain(msg));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
/* 54 */     if (msg instanceof HttpMessage) {
/* 55 */       boolean contains = ((HttpMessage)msg).headers().contains("X-SPDY-Stream-ID");
/* 56 */       if (!contains) {
/* 57 */         this.ids.add(NO_ID);
/*    */       } else {
/* 59 */         this.ids.add(Integer.valueOf(SpdyHttpHeaders.getStreamId((HttpMessage)msg)));
/*    */       } 
/* 61 */     } else if (msg instanceof SpdyRstStreamFrame) {
/* 62 */       this.ids.remove(Integer.valueOf(((SpdyRstStreamFrame)msg).streamId()));
/*    */     } 
/*    */     
/* 65 */     out.add(ReferenceCountUtil.retain(msg));
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\SpdyHttpResponseStreamIdHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */