/*    */ package io.netty.handler.codec.socks;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.ReplayingDecoder;
/*    */ import io.netty.util.CharsetUtil;
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
/*    */ public class SocksAuthRequestDecoder
/*    */   extends ReplayingDecoder<SocksAuthRequestDecoder.State>
/*    */ {
/*    */   private static final String name = "SOCKS_AUTH_REQUEST_DECODER";
/*    */   private SocksSubnegotiationVersion version;
/*    */   private int fieldLength;
/*    */   private String username;
/*    */   private String password;
/*    */   
/*    */   @Deprecated
/*    */   public static String getName() {
/* 38 */     return "SOCKS_AUTH_REQUEST_DECODER";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   private SocksRequest msg = SocksCommonUtils.UNKNOWN_SOCKS_REQUEST;
/*    */   
/*    */   public SocksAuthRequestDecoder() {
/* 48 */     super(State.CHECK_PROTOCOL_VERSION);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
/* 53 */     switch ((State)state()) {
/*    */       case CHECK_PROTOCOL_VERSION:
/* 55 */         this.version = SocksSubnegotiationVersion.valueOf(byteBuf.readByte());
/* 56 */         if (this.version != SocksSubnegotiationVersion.AUTH_PASSWORD) {
/*    */           break;
/*    */         }
/* 59 */         checkpoint(State.READ_USERNAME);
/*    */       
/*    */       case READ_USERNAME:
/* 62 */         this.fieldLength = byteBuf.readByte();
/* 63 */         this.username = byteBuf.readBytes(this.fieldLength).toString(CharsetUtil.US_ASCII);
/* 64 */         checkpoint(State.READ_PASSWORD);
/*    */       
/*    */       case READ_PASSWORD:
/* 67 */         this.fieldLength = byteBuf.readByte();
/* 68 */         this.password = byteBuf.readBytes(this.fieldLength).toString(CharsetUtil.US_ASCII);
/* 69 */         this.msg = new SocksAuthRequest(this.username, this.password);
/*    */         break;
/*    */     } 
/* 72 */     ctx.pipeline().remove((ChannelHandler)this);
/* 73 */     out.add(this.msg);
/*    */   }
/*    */   
/*    */   enum State {
/* 77 */     CHECK_PROTOCOL_VERSION,
/* 78 */     READ_USERNAME,
/* 79 */     READ_PASSWORD;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\socks\SocksAuthRequestDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */