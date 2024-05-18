/*    */ package io.netty.handler.codec.socks;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.ReplayingDecoder;
/*    */ import java.util.List;
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
/*    */ public class SocksAuthResponseDecoder
/*    */   extends ReplayingDecoder<SocksAuthResponseDecoder.State>
/*    */ {
/*    */   private static final String name = "SOCKS_AUTH_RESPONSE_DECODER";
/*    */   private SocksSubnegotiationVersion version;
/*    */   private SocksAuthStatus authStatus;
/*    */   
/*    */   @Deprecated
/*    */   public static String getName() {
/* 37 */     return "SOCKS_AUTH_RESPONSE_DECODER";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 42 */   private SocksResponse msg = SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE;
/*    */   
/*    */   public SocksAuthResponseDecoder() {
/* 45 */     super(State.CHECK_PROTOCOL_VERSION);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {
/* 51 */     switch ((State)state()) {
/*    */       case CHECK_PROTOCOL_VERSION:
/* 53 */         this.version = SocksSubnegotiationVersion.valueOf(byteBuf.readByte());
/* 54 */         if (this.version != SocksSubnegotiationVersion.AUTH_PASSWORD) {
/*    */           break;
/*    */         }
/* 57 */         checkpoint(State.READ_AUTH_RESPONSE);
/*    */       
/*    */       case READ_AUTH_RESPONSE:
/* 60 */         this.authStatus = SocksAuthStatus.valueOf(byteBuf.readByte());
/* 61 */         this.msg = new SocksAuthResponse(this.authStatus);
/*    */         break;
/*    */     } 
/* 64 */     channelHandlerContext.pipeline().remove((ChannelHandler)this);
/* 65 */     out.add(this.msg);
/*    */   }
/*    */   
/*    */   enum State {
/* 69 */     CHECK_PROTOCOL_VERSION,
/* 70 */     READ_AUTH_RESPONSE;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\socks\SocksAuthResponseDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */