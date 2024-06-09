/*    */ package io.netty.handler.codec.socks;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Collections;
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
/*    */ public final class SocksInitRequest
/*    */   extends SocksRequest
/*    */ {
/*    */   private final List<SocksAuthScheme> authSchemes;
/*    */   
/*    */   public SocksInitRequest(List<SocksAuthScheme> authSchemes) {
/* 33 */     super(SocksRequestType.INIT);
/* 34 */     if (authSchemes == null) {
/* 35 */       throw new NullPointerException("authSchemes");
/*    */     }
/* 37 */     this.authSchemes = authSchemes;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<SocksAuthScheme> authSchemes() {
/* 46 */     return Collections.unmodifiableList(this.authSchemes);
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeAsByteBuf(ByteBuf byteBuf) {
/* 51 */     byteBuf.writeByte(protocolVersion().byteValue());
/* 52 */     byteBuf.writeByte(this.authSchemes.size());
/* 53 */     for (SocksAuthScheme authScheme : this.authSchemes)
/* 54 */       byteBuf.writeByte(authScheme.byteValue()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\socks\SocksInitRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */