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
/*     */ public class SocksCmdResponseDecoder
/*     */   extends ReplayingDecoder<SocksCmdResponseDecoder.State>
/*     */ {
/*     */   private static final String name = "SOCKS_CMD_RESPONSE_DECODER";
/*     */   private SocksProtocolVersion version;
/*     */   private int fieldLength;
/*     */   private SocksCmdStatus cmdStatus;
/*     */   private SocksAddressType addressType;
/*     */   private byte reserved;
/*     */   private String host;
/*     */   private int port;
/*     */   
/*     */   @Deprecated
/*     */   public static String getName() {
/*  38 */     return "SOCKS_CMD_RESPONSE_DECODER";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   private SocksResponse msg = SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE;
/*     */   
/*     */   public SocksCmdResponseDecoder() {
/*  51 */     super(State.CHECK_PROTOCOL_VERSION);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
/*  56 */     switch ((State)state()) {
/*     */       case CHECK_PROTOCOL_VERSION:
/*  58 */         this.version = SocksProtocolVersion.valueOf(byteBuf.readByte());
/*  59 */         if (this.version != SocksProtocolVersion.SOCKS5) {
/*     */           break;
/*     */         }
/*  62 */         checkpoint(State.READ_CMD_HEADER);
/*     */       
/*     */       case READ_CMD_HEADER:
/*  65 */         this.cmdStatus = SocksCmdStatus.valueOf(byteBuf.readByte());
/*  66 */         this.reserved = byteBuf.readByte();
/*  67 */         this.addressType = SocksAddressType.valueOf(byteBuf.readByte());
/*  68 */         checkpoint(State.READ_CMD_ADDRESS);
/*     */       
/*     */       case READ_CMD_ADDRESS:
/*  71 */         switch (this.addressType) {
/*     */           case CHECK_PROTOCOL_VERSION:
/*  73 */             this.host = SocksCommonUtils.intToIp(byteBuf.readInt());
/*  74 */             this.port = byteBuf.readUnsignedShort();
/*  75 */             this.msg = new SocksCmdResponse(this.cmdStatus, this.addressType, this.host, this.port);
/*     */             break;
/*     */           
/*     */           case READ_CMD_HEADER:
/*  79 */             this.fieldLength = byteBuf.readByte();
/*  80 */             this.host = byteBuf.readBytes(this.fieldLength).toString(CharsetUtil.US_ASCII);
/*  81 */             this.port = byteBuf.readUnsignedShort();
/*  82 */             this.msg = new SocksCmdResponse(this.cmdStatus, this.addressType, this.host, this.port);
/*     */             break;
/*     */           
/*     */           case READ_CMD_ADDRESS:
/*  86 */             this.host = SocksCommonUtils.ipv6toStr(byteBuf.readBytes(16).array());
/*  87 */             this.port = byteBuf.readUnsignedShort();
/*  88 */             this.msg = new SocksCmdResponse(this.cmdStatus, this.addressType, this.host, this.port);
/*     */             break;
/*     */         } 
/*     */ 
/*     */         
/*     */         break;
/*     */     } 
/*     */     
/*  96 */     ctx.pipeline().remove((ChannelHandler)this);
/*  97 */     out.add(this.msg);
/*     */   }
/*     */   
/*     */   enum State {
/* 101 */     CHECK_PROTOCOL_VERSION,
/* 102 */     READ_CMD_HEADER,
/* 103 */     READ_CMD_ADDRESS;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\socks\SocksCmdResponseDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */