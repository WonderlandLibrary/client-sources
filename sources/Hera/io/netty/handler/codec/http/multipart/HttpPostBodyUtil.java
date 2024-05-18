/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import java.nio.charset.Charset;
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
/*     */ final class HttpPostBodyUtil
/*     */ {
/*     */   public static final int chunkSize = 8096;
/*     */   public static final String CONTENT_DISPOSITION = "Content-Disposition";
/*     */   public static final String NAME = "name";
/*     */   public static final String FILENAME = "filename";
/*     */   public static final String FORM_DATA = "form-data";
/*     */   public static final String ATTACHMENT = "attachment";
/*     */   public static final String FILE = "file";
/*     */   public static final String MULTIPART_MIXED = "multipart/mixed";
/*  61 */   public static final Charset ISO_8859_1 = CharsetUtil.ISO_8859_1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   public static final Charset US_ASCII = CharsetUtil.US_ASCII;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String DEFAULT_BINARY_CONTENT_TYPE = "application/octet-stream";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String DEFAULT_TEXT_CONTENT_TYPE = "text/plain";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum TransferEncodingMechanism
/*     */   {
/*  90 */     BIT7("7bit"),
/*     */ 
/*     */ 
/*     */     
/*  94 */     BIT8("8bit"),
/*     */ 
/*     */ 
/*     */     
/*  98 */     BINARY("binary");
/*     */     
/*     */     private final String value;
/*     */     
/*     */     TransferEncodingMechanism(String value) {
/* 103 */       this.value = value;
/*     */     }
/*     */     
/*     */     TransferEncodingMechanism() {
/* 107 */       this.value = name();
/*     */     }
/*     */     
/*     */     public String value() {
/* 111 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 116 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class SeekAheadNoBackArrayException
/*     */     extends Exception
/*     */   {
/*     */     private static final long serialVersionUID = -630418804938699495L;
/*     */   }
/*     */ 
/*     */   
/*     */   static class SeekAheadOptimize
/*     */   {
/*     */     byte[] bytes;
/*     */     
/*     */     int readerIndex;
/*     */     
/*     */     int pos;
/*     */     
/*     */     int origPos;
/*     */     
/*     */     int limit;
/*     */     
/*     */     ByteBuf buffer;
/*     */     
/*     */     SeekAheadOptimize(ByteBuf buffer) throws HttpPostBodyUtil.SeekAheadNoBackArrayException {
/* 143 */       if (!buffer.hasArray()) {
/* 144 */         throw new HttpPostBodyUtil.SeekAheadNoBackArrayException();
/*     */       }
/* 146 */       this.buffer = buffer;
/* 147 */       this.bytes = buffer.array();
/* 148 */       this.readerIndex = buffer.readerIndex();
/* 149 */       this.origPos = this.pos = buffer.arrayOffset() + this.readerIndex;
/* 150 */       this.limit = buffer.arrayOffset() + buffer.writerIndex();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setReadPosition(int minus) {
/* 159 */       this.pos -= minus;
/* 160 */       this.readerIndex = getReadPosition(this.pos);
/* 161 */       this.buffer.readerIndex(this.readerIndex);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int getReadPosition(int index) {
/* 170 */       return index - this.origPos + this.readerIndex;
/*     */     }
/*     */     
/*     */     void clear() {
/* 174 */       this.buffer = null;
/* 175 */       this.bytes = null;
/* 176 */       this.limit = 0;
/* 177 */       this.pos = 0;
/* 178 */       this.readerIndex = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int findNonWhitespace(String sb, int offset) {
/*     */     int result;
/* 188 */     for (result = offset; result < sb.length() && 
/* 189 */       Character.isWhitespace(sb.charAt(result)); result++);
/*     */ 
/*     */ 
/*     */     
/* 193 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int findWhitespace(String sb, int offset) {
/*     */     int result;
/* 202 */     for (result = offset; result < sb.length() && 
/* 203 */       !Character.isWhitespace(sb.charAt(result)); result++);
/*     */ 
/*     */ 
/*     */     
/* 207 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int findEndOfString(String sb) {
/*     */     int result;
/* 216 */     for (result = sb.length(); result > 0 && 
/* 217 */       Character.isWhitespace(sb.charAt(result - 1)); result--);
/*     */ 
/*     */ 
/*     */     
/* 221 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\multipart\HttpPostBodyUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */