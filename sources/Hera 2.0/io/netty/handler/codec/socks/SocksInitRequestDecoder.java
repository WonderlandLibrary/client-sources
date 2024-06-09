/*    */ package io.netty.handler.codec.socks;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.ReplayingDecoder;
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ 
/*    */ public class SocksInitRequestDecoder
/*    */   extends ReplayingDecoder<SocksInitRequestDecoder.State>
/*    */ {
/*    */   private static final String name = "SOCKS_INIT_REQUEST_DECODER";
/*    */   
/*    */   @Deprecated
/*    */   public static String getName() {
/* 38 */     return "SOCKS_INIT_REQUEST_DECODER";
/*    */   }
/*    */   
/* 41 */   private final List<SocksAuthScheme> authSchemes = new ArrayList<SocksAuthScheme>();
/*    */   private SocksProtocolVersion version;
/*    */   private byte authSchemeNum;
/* 44 */   private SocksRequest msg = SocksCommonUtils.UNKNOWN_SOCKS_REQUEST;
/*    */   
/*    */   public SocksInitRequestDecoder() {
/* 47 */     super(State.CHECK_PROTOCOL_VERSION);
/*    */   }
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
/*    */     int i;
/* 52 */     switch ((State)state()) {
/*    */       case CHECK_PROTOCOL_VERSION:
/* 54 */         this.version = SocksProtocolVersion.valueOf(byteBuf.readByte());
/* 55 */         if (this.version != SocksProtocolVersion.SOCKS5) {
/*    */           break;
/*    */         }
/* 58 */         checkpoint(State.READ_AUTH_SCHEMES);
/*    */       
/*    */       case READ_AUTH_SCHEMES:
/* 61 */         this.authSchemes.clear();
/* 62 */         this.authSchemeNum = byteBuf.readByte();
/* 63 */         for (i = 0; i < this.authSchemeNum; i++) {
/* 64 */           this.authSchemes.add(SocksAuthScheme.valueOf(byteBuf.readByte()));
/*    */         }
/* 66 */         this.msg = new SocksInitRequest(this.authSchemes);
/*    */         break;
/*    */     } 
/*    */     
/* 70 */     ctx.pipeline().remove((ChannelHandler)this);
/* 71 */     out.add(this.msg);
/*    */   }
/*    */   
/*    */   enum State {
/* 75 */     CHECK_PROTOCOL_VERSION,
/* 76 */     READ_AUTH_SCHEMES;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\socks\SocksInitRequestDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */