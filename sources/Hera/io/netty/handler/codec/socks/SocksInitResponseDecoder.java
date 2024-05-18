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
/*    */ public class SocksInitResponseDecoder
/*    */   extends ReplayingDecoder<SocksInitResponseDecoder.State>
/*    */ {
/*    */   private static final String name = "SOCKS_INIT_RESPONSE_DECODER";
/*    */   private SocksProtocolVersion version;
/*    */   private SocksAuthScheme authScheme;
/*    */   
/*    */   @Deprecated
/*    */   public static String getName() {
/* 37 */     return "SOCKS_INIT_RESPONSE_DECODER";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   private SocksResponse msg = SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE;
/*    */   
/*    */   public SocksInitResponseDecoder() {
/* 46 */     super(State.CHECK_PROTOCOL_VERSION);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
/* 51 */     switch ((State)state()) {
/*    */       case CHECK_PROTOCOL_VERSION:
/* 53 */         this.version = SocksProtocolVersion.valueOf(byteBuf.readByte());
/* 54 */         if (this.version != SocksProtocolVersion.SOCKS5) {
/*    */           break;
/*    */         }
/* 57 */         checkpoint(State.READ_PREFFERED_AUTH_TYPE);
/*    */       
/*    */       case READ_PREFFERED_AUTH_TYPE:
/* 60 */         this.authScheme = SocksAuthScheme.valueOf(byteBuf.readByte());
/* 61 */         this.msg = new SocksInitResponse(this.authScheme);
/*    */         break;
/*    */     } 
/*    */     
/* 65 */     ctx.pipeline().remove((ChannelHandler)this);
/* 66 */     out.add(this.msg);
/*    */   }
/*    */   
/*    */   enum State {
/* 70 */     CHECK_PROTOCOL_VERSION,
/* 71 */     READ_PREFFERED_AUTH_TYPE;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\socks\SocksInitResponseDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */