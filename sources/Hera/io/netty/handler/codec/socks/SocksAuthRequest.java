/*    */ package io.netty.handler.codec.socks;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.util.CharsetUtil;
/*    */ import java.nio.charset.CharsetEncoder;
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
/*    */ public final class SocksAuthRequest
/*    */   extends SocksRequest
/*    */ {
/* 30 */   private static final CharsetEncoder asciiEncoder = CharsetUtil.getEncoder(CharsetUtil.US_ASCII);
/* 31 */   private static final SocksSubnegotiationVersion SUBNEGOTIATION_VERSION = SocksSubnegotiationVersion.AUTH_PASSWORD;
/*    */   private final String username;
/*    */   private final String password;
/*    */   
/*    */   public SocksAuthRequest(String username, String password) {
/* 36 */     super(SocksRequestType.AUTH);
/* 37 */     if (username == null) {
/* 38 */       throw new NullPointerException("username");
/*    */     }
/* 40 */     if (password == null) {
/* 41 */       throw new NullPointerException("username");
/*    */     }
/* 43 */     if (!asciiEncoder.canEncode(username) || !asciiEncoder.canEncode(password)) {
/* 44 */       throw new IllegalArgumentException(" username: " + username + " or password: " + password + " values should be in pure ascii");
/*    */     }
/*    */     
/* 47 */     if (username.length() > 255) {
/* 48 */       throw new IllegalArgumentException(username + " exceeds 255 char limit");
/*    */     }
/* 50 */     if (password.length() > 255) {
/* 51 */       throw new IllegalArgumentException(password + " exceeds 255 char limit");
/*    */     }
/* 53 */     this.username = username;
/* 54 */     this.password = password;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String username() {
/* 63 */     return this.username;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String password() {
/* 72 */     return this.password;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeAsByteBuf(ByteBuf byteBuf) {
/* 77 */     byteBuf.writeByte(SUBNEGOTIATION_VERSION.byteValue());
/* 78 */     byteBuf.writeByte(this.username.length());
/* 79 */     byteBuf.writeBytes(this.username.getBytes(CharsetUtil.US_ASCII));
/* 80 */     byteBuf.writeByte(this.password.length());
/* 81 */     byteBuf.writeBytes(this.password.getBytes(CharsetUtil.US_ASCII));
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\socks\SocksAuthRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */