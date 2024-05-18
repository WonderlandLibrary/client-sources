/*    */ package io.netty.handler.codec.spdy;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import java.util.Set;
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
/*    */ public class SpdyHeaderBlockRawEncoder
/*    */   extends SpdyHeaderBlockEncoder
/*    */ {
/*    */   private final int version;
/*    */   
/*    */   public SpdyHeaderBlockRawEncoder(SpdyVersion version) {
/* 30 */     if (version == null) {
/* 31 */       throw new NullPointerException("version");
/*    */     }
/* 33 */     this.version = version.getVersion();
/*    */   }
/*    */   
/*    */   private static void setLengthField(ByteBuf buffer, int writerIndex, int length) {
/* 37 */     buffer.setInt(writerIndex, length);
/*    */   }
/*    */   
/*    */   private static void writeLengthField(ByteBuf buffer, int length) {
/* 41 */     buffer.writeInt(length);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuf encode(SpdyHeadersFrame frame) throws Exception {
/* 46 */     Set<String> names = frame.headers().names();
/* 47 */     int numHeaders = names.size();
/* 48 */     if (numHeaders == 0) {
/* 49 */       return Unpooled.EMPTY_BUFFER;
/*    */     }
/* 51 */     if (numHeaders > 65535) {
/* 52 */       throw new IllegalArgumentException("header block contains too many headers");
/*    */     }
/*    */     
/* 55 */     ByteBuf headerBlock = Unpooled.buffer();
/* 56 */     writeLengthField(headerBlock, numHeaders);
/* 57 */     for (String name : names) {
/* 58 */       byte[] nameBytes = name.getBytes("UTF-8");
/* 59 */       writeLengthField(headerBlock, nameBytes.length);
/* 60 */       headerBlock.writeBytes(nameBytes);
/* 61 */       int savedIndex = headerBlock.writerIndex();
/* 62 */       int valueLength = 0;
/* 63 */       writeLengthField(headerBlock, valueLength);
/* 64 */       for (String value : frame.headers().getAll(name)) {
/* 65 */         byte[] valueBytes = value.getBytes("UTF-8");
/* 66 */         if (valueBytes.length > 0) {
/* 67 */           headerBlock.writeBytes(valueBytes);
/* 68 */           headerBlock.writeByte(0);
/* 69 */           valueLength += valueBytes.length + 1;
/*    */         } 
/*    */       } 
/* 72 */       if (valueLength != 0) {
/* 73 */         valueLength--;
/*    */       }
/* 75 */       if (valueLength > 65535) {
/* 76 */         throw new IllegalArgumentException("header exceeds allowable length: " + name);
/*    */       }
/*    */       
/* 79 */       if (valueLength > 0) {
/* 80 */         setLengthField(headerBlock, savedIndex, valueLength);
/* 81 */         headerBlock.writerIndex(headerBlock.writerIndex() - 1);
/*    */       } 
/*    */     } 
/* 84 */     return headerBlock;
/*    */   }
/*    */   
/*    */   void end() {}
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\SpdyHeaderBlockRawEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */