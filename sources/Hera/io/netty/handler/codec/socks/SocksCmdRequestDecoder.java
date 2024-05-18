/*     */ package io.netty.handler.codec.socks;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.ReplayingDecoder;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SocksCmdRequestDecoder
/*     */   extends ReplayingDecoder<SocksCmdRequestDecoder.State>
/*     */ {
/*     */   private static final String name = "SOCKS_CMD_REQUEST_DECODER";
/*     */   private SocksProtocolVersion version;
/*     */   private int fieldLength;
/*     */   private SocksCmdType cmdType;
/*     */   private SocksAddressType addressType;
/*     */   private byte reserved;
/*     */   private String host;
/*     */   private int port;
/*     */   
/*     */   @Deprecated
/*     */   public static String getName() {
/*  38 */     return "SOCKS_CMD_REQUEST_DECODER";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   private SocksRequest msg = SocksCommonUtils.UNKNOWN_SOCKS_REQUEST;
/*     */   
/*     */   public SocksCmdRequestDecoder() {
/*  52 */     super(State.CHECK_PROTOCOL_VERSION);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
/*  57 */     switch ((State)state()) {
/*     */       case CHECK_PROTOCOL_VERSION:
/*  59 */         this.version = SocksProtocolVersion.valueOf(byteBuf.readByte());
/*  60 */         if (this.version != SocksProtocolVersion.SOCKS5) {
/*     */           break;
/*     */         }
/*  63 */         checkpoint(State.READ_CMD_HEADER);
/*     */       
/*     */       case READ_CMD_HEADER:
/*  66 */         this.cmdType = SocksCmdType.valueOf(byteBuf.readByte());
/*  67 */         this.reserved = byteBuf.readByte();
/*  68 */         this.addressType = SocksAddressType.valueOf(byteBuf.readByte());
/*  69 */         checkpoint(State.READ_CMD_ADDRESS);
/*     */       
/*     */       case READ_CMD_ADDRESS:
/*  72 */         switch (this.addressType) {
/*     */           case CHECK_PROTOCOL_VERSION:
/*  74 */             this.host = SocksCommonUtils.intToIp(byteBuf.readInt());
/*  75 */             this.port = byteBuf.readUnsignedShort();
/*  76 */             this.msg = new SocksCmdRequest(this.cmdType, this.addressType, this.host, this.port);
/*     */             break;
/*     */           
/*     */           case READ_CMD_HEADER:
/*  80 */             this.fieldLength = byteBuf.readByte();
/*  81 */             this.host = byteBuf.readBytes(this.fieldLength).toString(CharsetUtil.US_ASCII);
/*  82 */             this.port = byteBuf.readUnsignedShort();
/*  83 */             this.msg = new SocksCmdRequest(this.cmdType, this.addressType, this.host, this.port);
/*     */             break;
/*     */           
/*     */           case READ_CMD_ADDRESS:
/*  87 */             this.host = SocksCommonUtils.ipv6toStr(byteBuf.readBytes(16).array());
/*  88 */             this.port = byteBuf.readUnsignedShort();
/*  89 */             this.msg = new SocksCmdRequest(this.cmdType, this.addressType, this.host, this.port);
/*     */             break;
/*     */         } 
/*     */ 
/*     */         
/*     */         break;
/*     */     } 
/*     */     
/*  97 */     ctx.pipeline().remove((ChannelHandler)this);
/*  98 */     out.add(this.msg);
/*     */   }
/*     */   
/*     */   enum State {
/* 102 */     CHECK_PROTOCOL_VERSION,
/* 103 */     READ_CMD_HEADER,
/* 104 */     READ_CMD_ADDRESS;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\socks\SocksCmdRequestDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */