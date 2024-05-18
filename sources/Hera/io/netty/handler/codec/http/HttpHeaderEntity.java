/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.util.CharsetUtil;
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
/*    */ final class HttpHeaderEntity
/*    */   implements CharSequence
/*    */ {
/*    */   private final String name;
/*    */   private final int hash;
/*    */   private final byte[] bytes;
/*    */   private final int separatorLen;
/*    */   
/*    */   public HttpHeaderEntity(String name) {
/* 29 */     this(name, null);
/*    */   }
/*    */   
/*    */   public HttpHeaderEntity(String name, byte[] separator) {
/* 33 */     this.name = name;
/* 34 */     this.hash = HttpHeaders.hash(name);
/* 35 */     byte[] nameBytes = name.getBytes(CharsetUtil.US_ASCII);
/* 36 */     if (separator == null) {
/* 37 */       this.bytes = nameBytes;
/* 38 */       this.separatorLen = 0;
/*    */     } else {
/* 40 */       this.separatorLen = separator.length;
/* 41 */       this.bytes = new byte[nameBytes.length + separator.length];
/* 42 */       System.arraycopy(nameBytes, 0, this.bytes, 0, nameBytes.length);
/* 43 */       System.arraycopy(separator, 0, this.bytes, nameBytes.length, separator.length);
/*    */     } 
/*    */   }
/*    */   
/*    */   int hash() {
/* 48 */     return this.hash;
/*    */   }
/*    */ 
/*    */   
/*    */   public int length() {
/* 53 */     return this.bytes.length - this.separatorLen;
/*    */   }
/*    */ 
/*    */   
/*    */   public char charAt(int index) {
/* 58 */     if (this.bytes.length - this.separatorLen <= index) {
/* 59 */       throw new IndexOutOfBoundsException();
/*    */     }
/* 61 */     return (char)this.bytes[index];
/*    */   }
/*    */ 
/*    */   
/*    */   public CharSequence subSequence(int start, int end) {
/* 66 */     return new HttpHeaderEntity(this.name.substring(start, end));
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 71 */     return this.name;
/*    */   }
/*    */   
/*    */   boolean encode(ByteBuf buf) {
/* 75 */     buf.writeBytes(this.bytes);
/* 76 */     return (this.separatorLen > 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\HttpHeaderEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */