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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SocksCmdResponse
/*     */   extends SocksResponse
/*     */ {
/*     */   private final SocksCmdStatus cmdStatus;
/*     */   private final SocksAddressType addressType;
/*     */   private final String host;
/*     */   private final int port;
/*  38 */   private static final byte[] DOMAIN_ZEROED = new byte[] { 0 };
/*  39 */   private static final byte[] IPv4_HOSTNAME_ZEROED = new byte[] { 0, 0, 0, 0 };
/*  40 */   private static final byte[] IPv6_HOSTNAME_ZEROED = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocksCmdResponse(SocksCmdStatus cmdStatus, SocksAddressType addressType) {
/*  46 */     this(cmdStatus, addressType, null, 0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocksCmdResponse(SocksCmdStatus cmdStatus, SocksAddressType addressType, String host, int port) {
/*  63 */     super(SocksResponseType.CMD);
/*  64 */     if (cmdStatus == null) {
/*  65 */       throw new NullPointerException("cmdStatus");
/*     */     }
/*  67 */     if (addressType == null) {
/*  68 */       throw new NullPointerException("addressType");
/*     */     }
/*  70 */     if (host != null) {
/*  71 */       switch (addressType) {
/*     */         case IPv4:
/*  73 */           if (!NetUtil.isValidIpV4Address(host)) {
/*  74 */             throw new IllegalArgumentException(host + " is not a valid IPv4 address");
/*     */           }
/*     */           break;
/*     */         case DOMAIN:
/*  78 */           if (IDN.toASCII(host).length() > 255) {
/*  79 */             throw new IllegalArgumentException(host + " IDN: " + IDN.toASCII(host) + " exceeds 255 char limit");
/*     */           }
/*     */           break;
/*     */         
/*     */         case IPv6:
/*  84 */           if (!NetUtil.isValidIpV6Address(host)) {
/*  85 */             throw new IllegalArgumentException(host + " is not a valid IPv6 address");
/*     */           }
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/*  91 */       host = IDN.toASCII(host);
/*     */     } 
/*  93 */     if (port < 0 || port > 65535) {
/*  94 */       throw new IllegalArgumentException(port + " is not in bounds 0 <= x <= 65535");
/*     */     }
/*  96 */     this.cmdStatus = cmdStatus;
/*  97 */     this.addressType = addressType;
/*  98 */     this.host = host;
/*  99 */     this.port = port;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocksCmdStatus cmdStatus() {
/* 108 */     return this.cmdStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocksAddressType addressType() {
/* 117 */     return this.addressType;
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
/*     */   public String host() {
/* 129 */     if (this.host != null) {
/* 130 */       return IDN.toUnicode(this.host);
/*     */     }
/* 132 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int port() {
/* 143 */     return this.port;
/*     */   }
/*     */   
/*     */   public void encodeAsByteBuf(ByteBuf byteBuf) {
/*     */     byte[] hostContent;
/* 148 */     byteBuf.writeByte(protocolVersion().byteValue());
/* 149 */     byteBuf.writeByte(this.cmdStatus.byteValue());
/* 150 */     byteBuf.writeByte(0);
/* 151 */     byteBuf.writeByte(this.addressType.byteValue());
/* 152 */     switch (this.addressType) {
/*     */       case IPv4:
/* 154 */         hostContent = (this.host == null) ? IPv4_HOSTNAME_ZEROED : NetUtil.createByteArrayFromIpAddressString(this.host);
/*     */         
/* 156 */         byteBuf.writeBytes(hostContent);
/* 157 */         byteBuf.writeShort(this.port);
/*     */         break;
/*     */       
/*     */       case DOMAIN:
/* 161 */         hostContent = (this.host == null) ? DOMAIN_ZEROED : this.host.getBytes(CharsetUtil.US_ASCII);
/*     */         
/* 163 */         byteBuf.writeByte(hostContent.length);
/* 164 */         byteBuf.writeBytes(hostContent);
/* 165 */         byteBuf.writeShort(this.port);
/*     */         break;
/*     */       
/*     */       case IPv6:
/* 169 */         hostContent = (this.host == null) ? IPv6_HOSTNAME_ZEROED : NetUtil.createByteArrayFromIpAddressString(this.host);
/*     */         
/* 171 */         byteBuf.writeBytes(hostContent);
/* 172 */         byteBuf.writeShort(this.port);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\socks\SocksCmdResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */