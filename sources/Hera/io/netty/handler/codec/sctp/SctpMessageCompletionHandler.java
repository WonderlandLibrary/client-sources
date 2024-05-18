/*    */ package io.netty.handler.codec.sctp;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.sctp.SctpMessage;
/*    */ import io.netty.handler.codec.MessageToMessageDecoder;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ public class SctpMessageCompletionHandler
/*    */   extends MessageToMessageDecoder<SctpMessage>
/*    */ {
/* 36 */   private final Map<Integer, ByteBuf> fragments = new HashMap<Integer, ByteBuf>();
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, SctpMessage msg, List<Object> out) throws Exception {
/* 40 */     ByteBuf frag, byteBuf = msg.content();
/* 41 */     int protocolIdentifier = msg.protocolIdentifier();
/* 42 */     int streamIdentifier = msg.streamIdentifier();
/* 43 */     boolean isComplete = msg.isComplete();
/*    */ 
/*    */     
/* 46 */     if (this.fragments.containsKey(Integer.valueOf(streamIdentifier))) {
/* 47 */       frag = this.fragments.remove(Integer.valueOf(streamIdentifier));
/*    */     } else {
/* 49 */       frag = Unpooled.EMPTY_BUFFER;
/*    */     } 
/*    */     
/* 52 */     if (isComplete && !frag.isReadable()) {
/*    */       
/* 54 */       out.add(msg);
/* 55 */     } else if (!isComplete && frag.isReadable()) {
/*    */       
/* 57 */       this.fragments.put(Integer.valueOf(streamIdentifier), Unpooled.wrappedBuffer(new ByteBuf[] { frag, byteBuf }));
/* 58 */     } else if (isComplete && frag.isReadable()) {
/*    */       
/* 60 */       this.fragments.remove(Integer.valueOf(streamIdentifier));
/* 61 */       SctpMessage assembledMsg = new SctpMessage(protocolIdentifier, streamIdentifier, Unpooled.wrappedBuffer(new ByteBuf[] { frag, byteBuf }));
/*    */ 
/*    */ 
/*    */       
/* 65 */       out.add(assembledMsg);
/*    */     } else {
/*    */       
/* 68 */       this.fragments.put(Integer.valueOf(streamIdentifier), byteBuf);
/*    */     } 
/* 70 */     byteBuf.retain();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\sctp\SctpMessageCompletionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */