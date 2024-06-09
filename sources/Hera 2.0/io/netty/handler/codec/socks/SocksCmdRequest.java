/*     */ package io.netty.handler.codec.socks;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.NetUtil;
/*     */ import java.net.IDN;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SocksCmdRequest
/*     */   extends SocksRequest
/*     */ {
/*     */   private final SocksCmdType cmdType;
/*     */   private final SocksAddressType addressType;
/*     */   private final String host;
/*     */   private final int port;
/*     */   
/*     */   public SocksCmdRequest(SocksCmdType cmdType, SocksAddressType addressType, String host, int port) {
/*  37 */     super(SocksRequestType.CMD);
/*  38 */     if (cmdType == null) {
/*  39 */       throw new NullPointerException("cmdType");
/*     */     }
/*  41 */     if (addressType == null) {
/*  42 */       throw new NullPointerException("addressType");
/*     */     }
/*  44 */     if (host == null) {
/*  45 */       throw new NullPointerException("host");
/*     */     }
/*  47 */     switch (addressType) {
/*     */       case IPv4:
/*  49 */         if (!NetUtil.isValidIpV4Address(host)) {
/*  50 */           throw new IllegalArgumentException(host + " is not a valid IPv4 address");
/*     */         }
/*     */         break;
/*     */       case DOMAIN:
/*  54 */         if (IDN.toASCII(host).length() > 255) {
/*  55 */           throw new IllegalArgumentException(host + " IDN: " + IDN.toASCII(host) + " exceeds 255 char limit");
/*     */         }
/*     */         break;
/*     */       case IPv6:
/*  59 */         if (!NetUtil.isValidIpV6Address(host)) {
/*  60 */           throw new IllegalArgumentException(host + " is not a valid IPv6 address");
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/*  66 */     if (port <= 0 || port >= 65536) {
/*  67 */       throw new IllegalArgumentException(port + " is not in bounds 0 < x < 65536");
/*     */     }
/*  69 */     this.cmdType = cmdType;
/*  70 */     this.addressType = addressType;
/*  71 */     this.host = IDN.toASCII(host);
/*  72 */     this.port = port;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocksCmdType cmdType() {
/*  81 */     return this.cmdType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocksAddressType addressType() {
/*  90 */     return this.addressType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String host() {
/*  99 */     return IDN.toUnicode(this.host);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int port() {
/* 108 */     return this.port;
/*     */   }
/*     */ 
/*     */   
/*     */   public void encodeAsByteBuf(ByteBuf byteBuf) {
/* 113 */     byteBuf.writeByte(protocolVersion().byteValue());
/* 114 */     byteBuf.writeByte(this.cmdType.byteValue());
/* 115 */     byteBuf.writeByte(0);
/* 116 */     byteBuf.writeByte(this.addressType.byteValue());
/* 117 */     switch (this.addressType) {
/*     */       case IPv4:
/* 119 */         byteBuf.writeBytes(NetUtil.createByteArrayFromIpAddressString(this.host));
/* 120 */         byteBuf.writeShort(this.port);
/*     */         break;
/*     */ 
/*     */       
/*     */       case DOMAIN:
/* 125 */         byteBuf.writeByte(this.host.length());
/* 126 */         byteBuf.writeBytes(this.host.getBytes(CharsetUtil.US_ASCII));
/* 127 */         byteBuf.writeShort(this.port);
/*     */         break;
/*     */ 
/*     */       
/*     */       case IPv6:
/* 132 */         byteBuf.writeBytes(NetUtil.createByteArrayFromIpAddressString(this.host));
/* 133 */         byteBuf.writeShort(this.port);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\socks\SocksCmdRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */